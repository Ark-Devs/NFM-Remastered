/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class SuperClip
implements Runnable {
    int skiprate = 0;
    Thread cliper;
    int stoped = 2;
    SourceDataLine source = null;
    ByteArrayInputStream stream;
    int rollBackPos = 0;
    int rollBackTrig = 0;
    boolean changeGain = false;

    public SuperClip(byte[] byArray, int n, int n2) {
        this.skiprate = n2;
        this.stream = new ByteArrayInputStream(byArray, 0, n);
    }

    @Override
    public void run() {
        try {
            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, this.skiprate, 16, 1, 2, this.skiprate, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            this.source = (SourceDataLine)AudioSystem.getLine(info);
            this.source.open(audioFormat);
            this.source.start();
        }
        catch (Exception exception) {
            this.stoped = 1;
        }
        boolean bl = false;
        while (this.stoped == 0) {
            try {
                byte[] byArray;
                int n;
                int n2 = this.skiprate;
                int n3 = this.stream.available();
                if (n3 % 2 != 0) {
                    ++n3;
                }
                if ((n = this.stream.read(byArray = new byte[n3 > n2 ? n2 : n3], 0, byArray.length)) == -1 || this.rollBackPos != 0 && n3 < this.rollBackTrig) {
                    bl = true;
                }
                if (bl) {
                    if (n != -1) {
                        this.source.write(byArray, 0, byArray.length);
                    }
                    this.stream.reset();
                    if (this.rollBackPos != 0) {
                        this.stream.skip(this.rollBackPos);
                    }
                    if ((n3 = this.stream.available()) % 2 != 0) {
                        ++n3;
                    }
                    byArray = new byte[n3 > n2 ? n2 : n3];
                    this.stream.read(byArray, 0, byArray.length);
                    bl = false;
                }
                this.source.write(byArray, 0, byArray.length);
            }
            catch (Exception exception) {
                System.out.println("Play error: " + exception);
                this.stoped = 1;
            }
            try {
                Thread.sleep(200L);
            }
            catch (InterruptedException interruptedException) {}
        }
        this.source.stop();
        this.source.close();
        this.source = null;
        this.stoped = 2;
    }

    public void play() {
        if (this.stoped == 2) {
            this.stoped = 0;
            try {
                this.stream.reset();
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.cliper = new Thread(this);
            this.cliper.start();
        }
    }

    public void resume() {
        if (this.stoped == 2) {
            this.stoped = 0;
            this.cliper = new Thread(this);
            this.cliper.start();
        }
    }

    public void stop() {
        if (this.stoped == 0) {
            this.stoped = 1;
            if (this.source != null) {
                this.source.stop();
            }
        }
    }

    public void close() {
        try {
            this.stream.close();
            this.stream = null;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

