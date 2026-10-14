/*
 * Decompiled with CFR 0.152.
 */
import ds.nfm.Module;
import ds.nfm.ModuleLoader;
import ds.nfm.ModuleSlayer;
import java.net.URL;

public class RadicalMod {
    SuperClip sClip;
    boolean playing = false;
    int loaded = 0;
    int rvol = 0;
    String imod = "";
    String pmod = "";

    public RadicalMod() {
        this.loaded = 0;
        System.gc();
    }

    public RadicalMod(String string, int n, int n2, int n3, boolean bl, boolean bl2) {
        int n4 = 22000;
        n2 = (int)((float)n2 / 8000.0f * 2.0f * (float)n4);
        n = (int)((float)n * 0.8f);
        try {
            Object object;
            Module module = null;
            if (!bl2) {
                module = ModuleLoader.loadMod("" + Madness.fpath + "" + string + "");
            } else {
                string = string.replace(' ', '_');
                object = new URL("http://multiplayer.needformadness.com/tracks/music/" + string + ".zip");
                module = ModuleLoader.loadMod((URL)object);
            }
            if (module.isLoaded()) {
                object = ModuleLoader.prepareSlayer(module, n2, n, n3);
                byte[] byArray = ((ModuleSlayer)object).turnbytesNorm(bl);
                if (bl) {
                    this.rvol = ((ModuleSlayer)object).olav;
                }
                this.sClip = new SuperClip(byArray, ((ModuleSlayer)object).oln, n4);
                this.sClip.rollBackPos = ((ModuleSlayer)object).rollBackPos;
                this.sClip.rollBackTrig = ((ModuleSlayer)object).oln - ((ModuleSlayer)object).rollBackTrig;
                byArray = null;
                module = null;
                object = null;
                this.loaded = 2;
            }
        }
        catch (Exception exception) {
            System.out.println("Error downloading and making Mod: " + exception.toString());
            this.loaded = 0;
        }
        System.runFinalization();
        System.gc();
    }

    public RadicalMod(String string) {
        this.loaded = 1;
        this.imod = "" + Madness.fpath + "" + string + "";
    }

    public void loadimod(boolean bl) {
        if (this.loaded == 1) {
            int n = 44000;
            int n2 = 160;
            if (bl) {
                n2 = 300;
            }
            int n3 = 125;
            try {
                Module module = ModuleLoader.loadMod(this.imod);
                if (module.isLoaded()) {
                    ModuleSlayer moduleSlayer = ModuleLoader.prepareSlayer(module, n, n2, n3);
                    byte[] byArray = moduleSlayer.turnbytesNorm(bl);
                    if (bl) {
                        this.rvol = moduleSlayer.olav;
                    }
                    this.sClip = new SuperClip(byArray, moduleSlayer.oln, 22000);
                    this.sClip.rollBackPos = moduleSlayer.rollBackPos;
                    this.sClip.rollBackTrig = moduleSlayer.oln - moduleSlayer.rollBackTrig;
                    byArray = null;
                    module = null;
                    moduleSlayer = null;
                    this.loaded = 2;
                }
            }
            catch (Exception exception) {
                System.out.println("Error making a imod: " + exception.toString());
                this.loaded = 0;
            }
            System.runFinalization();
            System.gc();
        }
    }

    public void loadpmod(boolean bl) {
        if (this.loaded == 1) {
            int n = 44000;
            int n2 = 160;
            if (bl) {
                n2 = 300;
            }
            int n3 = 125;
            try {
                Module module = ModuleLoader.loadMod(this.pmod);
                if (module.isLoaded()) {
                    ModuleSlayer moduleSlayer = ModuleLoader.prepareSlayer(module, n, n2, n3);
                    byte[] byArray = moduleSlayer.turnbytesNorm(bl);
                    if (bl) {
                        this.rvol = moduleSlayer.olav;
                    }
                    this.sClip = new SuperClip(byArray, moduleSlayer.oln, 22000);
                    this.sClip.rollBackPos = moduleSlayer.rollBackPos;
                    this.sClip.rollBackTrig = moduleSlayer.oln - moduleSlayer.rollBackTrig;
                    byArray = null;
                    module = null;
                    moduleSlayer = null;
                    this.loaded = 2;
                }
            }
            catch (Exception exception) {
                System.out.println("Error making a imod: " + exception.toString());
                this.loaded = 0;
            }
            System.runFinalization();
            System.gc();
        }
    }

    public RadicalMod(String string, boolean bl) {
        this.loaded = 1;
        this.pmod = string;
        this.loadpmod(true);
    }

    public void play() {
        if (!this.playing && this.loaded == 2) {
            this.sClip.play();
            if (this.sClip.stoped == 0) {
                this.playing = true;
            }
        }
    }

    public void resume() {
        if (!this.playing && this.loaded == 2) {
            this.sClip.resume();
            if (this.sClip.stoped == 0) {
                this.playing = true;
            }
        }
    }

    public void stop() {
        if (this.playing && this.loaded == 2) {
            this.sClip.stop();
            this.playing = false;
        }
    }

    protected void unloadimod() {
        if (this.loaded == 2) {
            if (this.playing) {
                this.sClip.stop();
                this.playing = false;
            }
            try {
                this.sClip.close();
                this.sClip = null;
            }
            catch (Exception exception) {
                // empty catch block
            }
            System.gc();
            this.loaded = 1;
        }
    }

    protected void unload() {
        if (this.playing && this.loaded == 2) {
            this.sClip.stop();
            this.playing = false;
        }
        try {
            this.sClip.close();
            this.sClip = null;
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            this.imod = null;
        }
        catch (Exception exception) {
            // empty catch block
        }
        System.gc();
        this.loaded = 0;
    }
}

