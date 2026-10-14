/*
 * Decompiled with CFR 0.152.
 */
package ibxm;

public class Sample {
    public static final int FP_SHIFT = 15;
    public static final int FP_ONE = 32768;
    public static final int FP_MASK = Short.MAX_VALUE;
    public static final int C2_PAL = 8287;
    public static final int C2_NTSC = 8363;
    public String name = "";
    public int volume = 0;
    public int panning = -1;
    public int relNote = 0;
    public int fineTune = 0;
    public int c2Rate = 8363;
    private int loopStart = 0;
    private int loopLength = 0;
    private short[] sampleData;
    private static final int LOG2_FILTER_TAPS = 4;
    private static final int FILTER_TAPS = 16;
    private static final int DELAY = 8;
    private static final int LOG2_TABLE_ACCURACY = 4;
    private static final int TABLE_ACCURACY = 16;
    private static final int TABLE_INTERP_SHIFT = 11;
    private static final int TABLE_INTERP_ONE = 2048;
    private static final int TABLE_INTERP_MASK = 2047;
    private static final int LOG2_NUM_TABLES = 3;
    private static final int NUM_TABLES = 8;
    private static final short[][] SINC_TABLES = Sample.calculateSincTables();

    private static short[][] calculateSincTables() {
        short[][] sincTables = new short[8][];
        for (int tableIdx = 0; tableIdx < 8; ++tableIdx) {
            sincTables[tableIdx] = Sample.calculateSincTable(1.0 / (double)(tableIdx + 1));
        }
        return sincTables;
    }

    private static short[] calculateSincTable(double lowpass) {
        short[] sincTable = new short[272];
        double windDT = -0.39269908169872414;
        double sincDT = -Math.PI;
        int tableIdx = 0;
        for (int tableY = 0; tableY <= 16; ++tableY) {
            double fracT = (double)tableY / 16.0;
            double sincT = Math.PI * (7.0 + fracT);
            double windT = Math.PI + sincT * 2.0 / 16.0;
            for (int tableX = 0; tableX < 16; ++tableX) {
                double sincY = lowpass;
                if (sincT != 0.0) {
                    sincY = Math.sin(lowpass * sincT) / sincT;
                }
                double windY = 0.35875;
                windY -= 0.48829 * Math.cos(windT);
                windY += 0.14128 * Math.cos(windT * 2.0);
                sincTable[tableIdx++] = (short)Math.round(sincY * (windY -= 0.01168 * Math.cos(windT * 3.0)) * 32767.0);
                sincT += sincDT;
                windT += windDT;
            }
        }
        return sincTable;
    }

    public void setSampleData(short[] sampleData, int loopStart, int loopLength, boolean pingPong) {
        int idx;
        int sampleLength = sampleData.length;
        if (loopStart < 0 || loopStart > sampleLength) {
            loopStart = sampleLength;
        }
        if (loopLength < 0 || loopStart + loopLength > sampleLength) {
            loopLength = sampleLength - loopStart;
        }
        sampleLength = loopStart + loopLength;
        loopStart += 8;
        int newSampleLength = 8 + sampleLength + (pingPong ? loopLength : 0) + 16;
        short[] newSampleData = new short[newSampleLength];
        System.arraycopy(sampleData, 0, newSampleData, 8, sampleLength);
        sampleData = newSampleData;
        if (pingPong) {
            int loopEnd = loopStart + loopLength;
            for (int idx2 = 0; idx2 < loopLength; ++idx2) {
                sampleData[loopEnd + idx2] = sampleData[loopEnd - idx2 - 1];
            }
            loopLength *= 2;
        }
        int end = idx + 16;
        for (idx = loopStart + loopLength; idx < end; ++idx) {
            sampleData[idx] = sampleData[idx - loopLength];
        }
        this.sampleData = sampleData;
        this.loopStart = loopStart;
        this.loopLength = loopLength;
    }

    public void resampleNearest(int sampleIdx, int sampleFrac, int step, int leftGain, int rightGain, int[] mixBuffer, int offset, int length) {
        int loopLen = this.loopLength;
        int loopEnd = this.loopStart + loopLen;
        if ((sampleIdx += 8) >= loopEnd) {
            sampleIdx = this.normaliseSampleIdx(sampleIdx);
        }
        short[] data = this.sampleData;
        int outIdx = offset << 1;
        int outEnd = offset + length << 1;
        while (outIdx < outEnd) {
            if (sampleIdx >= loopEnd) {
                if (loopLen < 2) break;
                while (sampleIdx >= loopEnd) {
                    sampleIdx -= loopLen;
                }
            }
            short y = data[sampleIdx];
            int n = outIdx++;
            mixBuffer[n] = mixBuffer[n] + (y * leftGain >> 15);
            int n2 = outIdx++;
            mixBuffer[n2] = mixBuffer[n2] + (y * rightGain >> 15);
            sampleIdx += (sampleFrac += step) >> 15;
            sampleFrac &= Short.MAX_VALUE;
        }
    }

    public void resampleLinear(int sampleIdx, int sampleFrac, int step, int leftGain, int rightGain, int[] mixBuffer, int offset, int length) {
        int loopLen = this.loopLength;
        int loopEnd = this.loopStart + loopLen;
        if ((sampleIdx += 8) >= loopEnd) {
            sampleIdx = this.normaliseSampleIdx(sampleIdx);
        }
        short[] data = this.sampleData;
        int outIdx = offset << 1;
        int outEnd = offset + length << 1;
        while (outIdx < outEnd) {
            if (sampleIdx >= loopEnd) {
                if (loopLen < 2) break;
                while (sampleIdx >= loopEnd) {
                    sampleIdx -= loopLen;
                }
            }
            short c = data[sampleIdx];
            int m = data[sampleIdx + 1] - c;
            int y = (m * sampleFrac >> 15) + c;
            int n = outIdx++;
            mixBuffer[n] = mixBuffer[n] + (y * leftGain >> 15);
            int n2 = outIdx++;
            mixBuffer[n2] = mixBuffer[n2] + (y * rightGain >> 15);
            sampleIdx += (sampleFrac += step) >> 15;
            sampleFrac &= Short.MAX_VALUE;
        }
    }

    public void resampleSinc(int sampleIdx, int sampleFrac, int step, int leftGain, int rightGain, int[] mixBuffer, int offset, int length) {
        int tableIdx = 0;
        if (step > 32768 && (tableIdx = (step >> 15) - 1) >= 8) {
            tableIdx = 7;
        }
        short[] sincTable = SINC_TABLES[tableIdx];
        int loopLen = this.loopLength;
        int loopEnd = this.loopStart + loopLen;
        if (sampleIdx >= loopEnd) {
            sampleIdx = this.normaliseSampleIdx(sampleIdx);
        }
        short[] data = this.sampleData;
        int outIdx = offset << 1;
        int outEnd = offset + length << 1;
        while (outIdx < outEnd) {
            if (sampleIdx >= loopEnd) {
                if (loopLen < 2) break;
                while (sampleIdx >= loopEnd) {
                    sampleIdx -= loopLen;
                }
            }
            int tableIdx1 = sampleFrac >> 11 << 4;
            int tableIdx2 = tableIdx1 + 16;
            int a1 = 0;
            int a2 = 0;
            for (int tap = 0; tap < 16; ++tap) {
                a1 += sincTable[tableIdx1 + tap] * data[sampleIdx + tap];
                a2 += sincTable[tableIdx2 + tap] * data[sampleIdx + tap];
            }
            int y = (a1 >>= 15) + (((a2 >>= 15) - a1) * (sampleFrac & 0x7FF) >> 11);
            int n = outIdx++;
            mixBuffer[n] = mixBuffer[n] + (y * leftGain >> 15);
            int n2 = outIdx++;
            mixBuffer[n2] = mixBuffer[n2] + (y * rightGain >> 15);
            sampleIdx += (sampleFrac += step) >> 15;
            sampleFrac &= Short.MAX_VALUE;
        }
    }

    public int normaliseSampleIdx(int sampleIdx) {
        int loopOffset = sampleIdx - this.loopStart;
        if (loopOffset > 0) {
            sampleIdx = this.loopStart;
            if (this.loopLength > 1) {
                sampleIdx += loopOffset % this.loopLength;
            }
        }
        return sampleIdx;
    }

    public boolean looped() {
        return this.loopLength > 1;
    }

    public void toStringBuffer(StringBuffer out) {
        out.append("Name: " + this.name + '\n');
        out.append("Volume: " + this.volume + '\n');
        out.append("Panning: " + this.panning + '\n');
        out.append("Relative Note: " + this.relNote + '\n');
        out.append("Fine Tune: " + this.fineTune + '\n');
        out.append("Loop Start: " + this.loopStart + '\n');
        out.append("Loop Length: " + this.loopLength + '\n');
    }
}

