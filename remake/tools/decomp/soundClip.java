/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class soundClip {
    Clip clip = null;
    AudioInputStream sound;
    boolean loaded = false;
    int lfrpo = -1;
    int cntcheck = 0;

    public soundClip(byte[] byArray) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
            this.sound = AudioSystem.getAudioInputStream(byteArrayInputStream);
            this.sound.mark(byArray.length);
            DataLine.Info info = new DataLine.Info(Clip.class, this.sound.getFormat());
            this.clip = (Clip)AudioSystem.getLine(info);
            this.loaded = true;
        }
        catch (Exception exception) {
            System.out.println("Loading Clip error: " + exception);
            this.loaded = false;
        }
    }

    public void play() {
        if (this.loaded) {
            try {
                if (!this.clip.isOpen()) {
                    try {
                        this.clip.open(this.sound);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    this.clip.loop(0);
                } else {
                    this.clip.loop(1);
                }
                this.lfrpo = -1;
                this.cntcheck = 5;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void loop() {
        if (this.loaded) {
            try {
                if (!this.clip.isOpen()) {
                    try {
                        this.clip.open(this.sound);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                this.clip.loop(70);
                this.lfrpo = -2;
                this.cntcheck = 0;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void stop() {
        if (this.loaded) {
            try {
                this.clip.stop();
                this.lfrpo = -1;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void checkopen() {
        if (this.loaded && this.clip.isOpen() && this.lfrpo != -2) {
            if (this.cntcheck == 0) {
                int n = this.clip.getFramePosition();
                if (this.lfrpo == n && !this.clip.isRunning()) {
                    try {
                        this.clip.close();
                        this.sound.reset();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    this.lfrpo = -1;
                } else {
                    this.lfrpo = n;
                }
            } else {
                --this.cntcheck;
            }
        }
    }
}

