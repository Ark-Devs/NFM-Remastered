/*
 * Decompiled with CFR 0.152.
 */
package ibxm;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class Data {
    private int bufLen;
    private byte[] buffer;
    private InputStream stream;

    public Data(InputStream inputStream) throws IOException {
        this.bufLen = 65536;
        this.buffer = new byte[this.bufLen];
        this.stream = inputStream;
        Data.readFully(this.stream, this.buffer, 0, this.bufLen);
    }

    public Data(byte[] data) {
        this.bufLen = data.length;
        this.buffer = data;
    }

    public byte sByte(int offset) throws IOException {
        this.load(offset, 1);
        return this.buffer[offset];
    }

    public int uByte(int offset) throws IOException {
        this.load(offset, 1);
        return this.buffer[offset] & 0xFF;
    }

    public int ubeShort(int offset) throws IOException {
        this.load(offset, 2);
        return (this.buffer[offset] & 0xFF) << 8 | this.buffer[offset + 1] & 0xFF;
    }

    public int uleShort(int offset) throws IOException {
        this.load(offset, 2);
        return this.buffer[offset] & 0xFF | (this.buffer[offset + 1] & 0xFF) << 8;
    }

    public int uleInt(int offset) throws IOException {
        this.load(offset, 4);
        int value = this.buffer[offset] & 0xFF;
        value |= (this.buffer[offset + 1] & 0xFF) << 8;
        value |= (this.buffer[offset + 2] & 0xFF) << 16;
        return value |= (this.buffer[offset + 3] & 0x7F) << 24;
    }

    public String strLatin1(int offset, int length) throws IOException {
        this.load(offset, length);
        char[] str = new char[length];
        for (int idx = 0; idx < length; ++idx) {
            int chr = this.buffer[offset + idx] & 0xFF;
            str[idx] = (char)(chr < 32 ? 32 : (char)chr);
        }
        return new String(str);
    }

    public String strCp850(int offset, int length) throws IOException {
        this.load(offset, length);
        try {
            char[] str = new String(this.buffer, offset, length, "Cp850").toCharArray();
            for (int idx = 0; idx < str.length; ++idx) {
                str[idx] = str[idx] < ' ' ? 32 : str[idx];
            }
            return new String(str);
        }
        catch (UnsupportedEncodingException e) {
            return this.strLatin1(offset, length);
        }
    }

    public short[] samS8(int offset, int length) throws IOException {
        this.load(offset, length);
        short[] sampleData = new short[length];
        for (int idx = 0; idx < length; ++idx) {
            sampleData[idx] = (short)(this.buffer[offset + idx] << 8);
        }
        return sampleData;
    }

    public short[] samS8D(int offset, int length) throws IOException {
        this.load(offset, length);
        short[] sampleData = new short[length];
        int sam = 0;
        for (int idx = 0; idx < length; ++idx) {
            sampleData[idx] = (short)((sam += this.buffer[offset + idx]) << 8);
        }
        return sampleData;
    }

    public short[] samU8(int offset, int length) throws IOException {
        this.load(offset, length);
        short[] sampleData = new short[length];
        for (int idx = 0; idx < length; ++idx) {
            sampleData[idx] = (short)((this.buffer[offset + idx] & 0xFF) - 128 << 8);
        }
        return sampleData;
    }

    public short[] samS16(int offset, int samples) throws IOException {
        this.load(offset, samples * 2);
        short[] sampleData = new short[samples];
        for (int idx = 0; idx < samples; ++idx) {
            sampleData[idx] = (short)(this.buffer[offset + idx * 2] & 0xFF | this.buffer[offset + idx * 2 + 1] << 8);
        }
        return sampleData;
    }

    public short[] samS16D(int offset, int samples) throws IOException {
        this.load(offset, samples * 2);
        short[] sampleData = new short[samples];
        int sam = 0;
        for (int idx = 0; idx < samples; ++idx) {
            sampleData[idx] = (short)(sam += this.buffer[offset + idx * 2] & 0xFF | this.buffer[offset + idx * 2 + 1] << 8);
        }
        return sampleData;
    }

    public short[] samU16(int offset, int samples) throws IOException {
        this.load(offset, samples * 2);
        short[] sampleData = new short[samples];
        for (int idx = 0; idx < samples; ++idx) {
            int sam = this.buffer[offset + idx * 2] & 0xFF | (this.buffer[offset + idx * 2 + 1] & 0xFF) << 8;
            sampleData[idx] = (short)(sam - 32768);
        }
        return sampleData;
    }

    private void load(int offset, int length) throws IOException {
        while (offset + length > this.bufLen) {
            int newBufLen = this.bufLen << 1;
            byte[] newBuf = new byte[newBufLen];
            System.arraycopy(this.buffer, 0, newBuf, 0, this.bufLen);
            if (this.stream != null) {
                Data.readFully(this.stream, newBuf, this.bufLen, newBufLen - this.bufLen);
            }
            this.bufLen = newBufLen;
            this.buffer = newBuf;
        }
    }

    private static void readFully(InputStream inputStream, byte[] buffer, int offset, int length) throws IOException {
        int read = 1;
        int end = offset + length;
        while (read > 0) {
            read = inputStream.read(buffer, offset, end - offset);
            offset += read;
        }
    }
}

