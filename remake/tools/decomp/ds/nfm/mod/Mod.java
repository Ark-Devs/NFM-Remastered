/*
 * Decompiled with CFR 0.152.
 */
package ds.nfm.mod;

import ds.nfm.Module;
import ds.nfm.mod.ModInstrument;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class Mod
extends Module {
    int numtracks;
    int track_shift;
    int numpatterns;
    byte[][] patterns;
    ModInstrument[] insts;
    byte[] positions;
    int song_length_patterns;
    int song_repeat_patterns;
    boolean s3m;
    static final int voice_mk = Mod.FOURCC("M.K.");
    static final int voice_mk2 = Mod.FOURCC("M!K!");
    static final int voice_mk3 = Mod.FOURCC("M&K!");
    static final int voice_flt4 = Mod.FOURCC("FLT4");
    static final int voice_flt8 = Mod.FOURCC("FLT8");
    static final int voice_fltoct = Mod.FOURCC("OCTA");
    static final int voice_6chn = Mod.FOURCC("6CHN");
    static final int voice_8chn = Mod.FOURCC("8CHN");
    static final int voice_10ch = Mod.FOURCC("10CH");
    static final int voice_12ch = Mod.FOURCC("12CH");
    static final int voice_14ch = Mod.FOURCC("14CH");
    static final int voice_16ch = Mod.FOURCC("16CH");
    static final int voice_18ch = Mod.FOURCC("18CH");
    static final int voice_20ch = Mod.FOURCC("20CH");
    static final int voice_22ch = Mod.FOURCC("22CH");
    static final int voice_24ch = Mod.FOURCC("24CH");
    static final int voice_26ch = Mod.FOURCC("26CH");
    static final int voice_28ch = Mod.FOURCC("28CH");
    static final int voice_30ch = Mod.FOURCC("30CH");
    static final int voice_32ch = Mod.FOURCC("32CH");
    static final int voice_11ch = Mod.FOURCC("11CH");
    static final int voice_13ch = Mod.FOURCC("13CH");
    static final int voice_15ch = Mod.FOURCC("15CH");
    static final int voice_tdz1 = Mod.FOURCC("TDZ1");
    static final int voice_tdz2 = Mod.FOURCC("TDZ2");
    static final int voice_tdz3 = Mod.FOURCC("TDZ3");
    static final int voice_5chn = Mod.FOURCC("5CHN");
    static final int voice_7chn = Mod.FOURCC("7CHN");
    static final int voice_9chn = Mod.FOURCC("9CHN");
    static final int[] voice_31_list = new int[]{voice_mk, voice_mk2, voice_mk3, voice_flt4, voice_flt8, voice_8chn, voice_6chn, voice_10ch, voice_12ch, voice_14ch, voice_16ch, voice_18ch, voice_20ch, voice_22ch, voice_24ch, voice_26ch, voice_28ch, voice_30ch, voice_32ch, voice_11ch, voice_13ch, voice_15ch, voice_tdz1, voice_tdz2, voice_tdz3, voice_5chn, voice_7chn, voice_9chn};

    public Mod(byte[] modf) {
        try {
            this.loadMod(new ByteArrayInputStream(modf));
            this.loaded = true;
        }
        catch (Exception e) {
            this.loaded = false;
        }
    }

    static final int FOURCC(String s) {
        return s.charAt(3) & 0xFF | (s.charAt(2) & 0xFF) << 8 | (s.charAt(1) & 0xFF) << 16 | (s.charAt(0) & 0xFF) << 24;
    }

    @Override
    public void loadMod(InputStream inputstream) throws IOException {
        DataInputStream datainputstream = new DataInputStream(inputstream);
        int samples = 15;
        this.numtracks = 4;
        this.name = Mod.readText(datainputstream, 20);
        if (this.name.trim().isEmpty()) {
            this.name = "Untitled";
        }
        datainputstream.mark(1068);
        datainputstream.skip(1060L);
        int signature = datainputstream.readInt();
        datainputstream.reset();
        for (int i = 0; i < voice_31_list.length; ++i) {
            if (signature != voice_31_list[i]) continue;
            samples = 31;
            break;
        }
        if (samples == 31) {
            if (signature == voice_8chn) {
                this.numtracks = 8;
            } else if (signature == voice_6chn) {
                this.numtracks = 6;
            } else if (signature == voice_10ch) {
                this.numtracks = 10;
            } else if (signature == voice_28ch) {
                this.numtracks = 28;
            } else if (signature == voice_12ch) {
                this.numtracks = 12;
            } else if (signature == voice_14ch) {
                this.numtracks = 14;
            } else if (signature == voice_16ch) {
                this.numtracks = 16;
            } else if (signature == voice_18ch) {
                this.numtracks = 18;
            } else if (signature == voice_20ch) {
                this.numtracks = 20;
            } else if (signature == voice_22ch) {
                this.numtracks = 22;
            } else if (signature == voice_24ch) {
                this.numtracks = 24;
            } else if (signature == voice_26ch) {
                this.numtracks = 26;
            } else if (signature == voice_30ch) {
                this.numtracks = 30;
            } else if (signature == voice_32ch) {
                this.numtracks = 32;
            } else if (signature == voice_tdz1) {
                this.numtracks = 1;
            } else if (signature == voice_tdz2) {
                this.numtracks = 2;
            } else if (signature == voice_tdz3) {
                this.numtracks = 3;
            } else if (signature == voice_5chn) {
                this.numtracks = 5;
            } else if (signature == voice_7chn) {
                this.numtracks = 7;
            } else if (signature == voice_9chn) {
                this.numtracks = 9;
            } else if (signature == voice_11ch) {
                this.numtracks = 11;
            } else if (signature == voice_13ch) {
                this.numtracks = 13;
            } else if (signature == voice_15ch) {
                this.numtracks = 15;
            }
        } else {
            System.out.print("Format unknown. Checking bytes... ");
            datainputstream.mark(0);
            datainputstream.reset();
            datainputstream.skip(471L);
            byte readByte = datainputstream.readByte();
            if (readByte >= 32 && readByte <= 126) {
                samples = 31;
            }
            System.out.println(readByte);
            datainputstream.mark(1068);
            datainputstream.reset();
            datainputstream.skip(1064L);
        }
        this.insts = new ModInstrument[samples];
        for (int j = 0; j < samples; ++j) {
            this.insts[j] = Mod.readInstrument(datainputstream);
        }
        this.readSequence(datainputstream);
        datainputstream.skipBytes(4);
        this.readPatterns(datainputstream);
        try {
            for (int k = 0; k < samples; ++k) {
                Mod.readSampleData(datainputstream, this.insts[k]);
            }
        }
        catch (EOFException _ex) {
            System.out.println("Warning: EOF on MOD file");
        }
        datainputstream.close();
        inputstream.close();
    }

    public int getNumPatterns() {
        return this.numpatterns;
    }

    public int getNumTracks() {
        return this.numtracks;
    }

    static ModInstrument readInstrument(DataInputStream datainputstream) throws IOException {
        ModInstrument modinstrument = new ModInstrument();
        modinstrument.name = Mod.readText(datainputstream, 22);
        modinstrument.sample_length = Mod.readu16(datainputstream) << 1;
        modinstrument.samples = new byte[modinstrument.sample_length + 8];
        int fine = Mod.readu8(datainputstream) & 0xF;
        fine = fine > 7 ? fine - 16 : fine;
        modinstrument.finetune_value = (byte)(fine << 4);
        modinstrument.volume = Mod.readu8(datainputstream);
        modinstrument.repeat_point = Mod.readu16(datainputstream) << 1;
        modinstrument.repeat_length = Mod.readu16(datainputstream) << 1;
        if (modinstrument.repeat_point > modinstrument.sample_length) {
            modinstrument.repeat_point = modinstrument.sample_length - 1;
        }
        if (modinstrument.repeat_point + modinstrument.repeat_length > modinstrument.sample_length) {
            modinstrument.repeat_length = modinstrument.sample_length - modinstrument.repeat_point;
        }
        return modinstrument;
    }

    void readPatterns(DataInputStream datainputstream) throws IOException {
        int i = this.numtracks * 4 * 64;
        this.patterns = new byte[this.numpatterns][];
        for (int j = 0; j < this.numpatterns; ++j) {
            this.patterns[j] = new byte[i];
            datainputstream.readFully(this.patterns[j], 0, i);
        }
    }

    static void readSampleData(DataInputStream datainputstream, ModInstrument modinstrument) throws IOException {
        datainputstream.readFully(modinstrument.samples, 0, modinstrument.sample_length);
        if (modinstrument.repeat_length > 3) {
            System.arraycopy(modinstrument.samples, modinstrument.repeat_point, modinstrument.samples, modinstrument.sample_length, 8);
        }
    }

    void readSequence(DataInputStream datainputstream) throws IOException {
        this.positions = new byte[128];
        this.song_length_patterns = Mod.readu8(datainputstream);
        this.song_repeat_patterns = Mod.readu8(datainputstream);
        datainputstream.readFully(this.positions, 0, 128);
        if (this.song_repeat_patterns > this.song_length_patterns) {
            this.song_repeat_patterns = this.song_length_patterns;
        }
        this.numpatterns = 0;
        for (int i = 0; i < this.positions.length; ++i) {
            if (this.positions[i] <= this.numpatterns) continue;
            this.numpatterns = this.positions[i];
        }
        ++this.numpatterns;
    }

    static final String readText(DataInputStream datainputstream, int i) throws IOException {
        byte[] abyte0 = new byte[i];
        datainputstream.readFully(abyte0, 0, i);
        for (int j = i - 1; j >= 0; --j) {
            if (abyte0[j] == 0) continue;
            return new String(abyte0, 0, 0, j + 1);
        }
        return "";
    }

    static final int readu16(DataInputStream datainputstream) throws IOException {
        return datainputstream.readShort() & 0xFFFF;
    }

    static final int readu8(DataInputStream datainputstream) throws IOException {
        return datainputstream.readByte() & 0xFF;
    }

    public String toString() {
        return this.name + " (" + this.numtracks + " tracks, " + this.numpatterns + " patterns, " + this.insts.length + " samples)";
    }
}

