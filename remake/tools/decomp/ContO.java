/*
 * Decompiled with CFR 0.152.
 */
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Random;

public class ContO {
    Medium m;
    Trackers t;
    Plane[] p;
    int npl;
    int x;
    int y;
    int z;
    int xz;
    int xy;
    int zy;
    int wxz;
    int wzy;
    int dist;
    int maxR;
    int disp;
    int disline;
    boolean shadow;
    boolean noline;
    boolean decor;
    float grounded;
    int grat;
    int[] keyx;
    int[] keyz;
    int sprkat;
    int[] txy;
    int[] tzy;
    int[][] tc;
    int[] tradx;
    int[] tradz;
    int[] trady;
    int[] tx;
    int[] ty;
    int[] tz;
    int[] skd;
    int[] dam;
    boolean[] notwall;
    int tnt;
    int[] stg;
    int[] sx;
    int[] sy;
    int[] sz;
    int[] scx;
    int[] scz;
    float[] osmag;
    int[] sav;
    float[][] smag;
    int[][] srgb;
    float[] sbln;
    int ust;
    int srx;
    int sry;
    int srz;
    float rcx;
    float rcy;
    float rcz;
    int sprk;
    int[] rtg;
    boolean[] rbef;
    int[] rx;
    int[] ry;
    int[] rz;
    float[] vrx;
    float[] vry;
    float[] vrz;
    boolean elec;
    boolean roted;
    int[] edl;
    int[] edr;
    int[] elc;
    boolean fix;
    int fcnt;
    int checkpoint;
    int[] fcol;
    int[] scol;
    int colok;
    boolean errd;
    String err;
    int roofat;
    int wh;

    public ContO(byte[] byArray, Medium medium, Trackers trackers) {
        int n;
        int n2;
        Wheels wheels;
        int[] nArray;
        block157: {
            int n3;
            this.npl = 0;
            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.xz = 0;
            this.xy = 0;
            this.zy = 0;
            this.wxz = 0;
            this.wzy = 0;
            this.dist = 0;
            this.maxR = 0;
            this.disp = 0;
            this.disline = 14;
            this.shadow = false;
            this.noline = false;
            this.decor = false;
            this.grounded = 1.0f;
            this.grat = 0;
            this.keyx = new int[4];
            this.keyz = new int[4];
            this.sprkat = 0;
            this.tnt = 0;
            this.ust = 0;
            this.srx = 0;
            this.sry = 0;
            this.srz = 0;
            this.rcx = 0.0f;
            this.rcy = 0.0f;
            this.rcz = 0.0f;
            this.sprk = 0;
            this.elec = false;
            this.roted = false;
            this.edl = new int[4];
            this.edr = new int[4];
            this.elc = new int[]{0, 0, 0, 0};
            this.fix = false;
            this.fcnt = 0;
            this.checkpoint = 0;
            this.fcol = new int[]{0, 0, 0};
            this.scol = new int[]{0, 0, 0};
            this.colok = 0;
            this.errd = false;
            this.err = "";
            this.roofat = 0;
            this.wh = 0;
            this.m = medium;
            this.t = trackers;
            this.p = new Plane[286];
            nArray = new int[286];
            for (n3 = 0; n3 < 286; ++n3) {
                nArray[n3] = 0;
            }
            if (this.m.loadnew) {
                for (n3 = 0; n3 < 4; ++n3) {
                    this.keyz[n3] = 0;
                }
                this.shadow = true;
            }
            String string = "";
            boolean bl = false;
            boolean bl2 = false;
            int n4 = 0;
            float f = 1.0f;
            float f2 = 1.0f;
            float[] fArray = new float[]{1.0f, 1.0f, 1.0f};
            int[] nArray2 = new int[100];
            int[] nArray3 = new int[100];
            int[] nArray4 = new int[100];
            int[] nArray5 = new int[]{0, 0, 0};
            boolean bl3 = false;
            wheels = new Wheels();
            boolean bl4 = false;
            n2 = 0;
            int n5 = 1;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            int n9 = 0;
            boolean bl5 = false;
            boolean bl6 = false;
            try {
                String string2;
                DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(byArray));
                while ((string2 = dataInputStream.readLine()) != null) {
                    string = "" + string2.trim();
                    if (this.npl < 210) {
                        if (string.startsWith("<p>")) {
                            bl = true;
                            n4 = 0;
                            n5 = 0;
                            n6 = 0;
                            n8 = 0;
                            nArray[this.npl] = 1;
                            if (!bl6) {
                                bl5 = false;
                            }
                        }
                        if (bl) {
                            if (string.startsWith("gr(")) {
                                n5 = this.getvalue("gr", string, 0);
                            }
                            if (string.startsWith("fs(")) {
                                n6 = this.getvalue("fs", string, 0);
                                nArray[this.npl] = 2;
                            }
                            if (string.startsWith("c(")) {
                                n9 = 0;
                                nArray5[0] = this.getvalue("c", string, 0);
                                nArray5[1] = this.getvalue("c", string, 1);
                                nArray5[2] = this.getvalue("c", string, 2);
                            }
                            if (string.startsWith("glass")) {
                                n9 = 1;
                            }
                            if (string.startsWith("gshadow")) {
                                n9 = 2;
                            }
                            if (string.startsWith("lightF")) {
                                n8 = 1;
                            }
                            if (string.startsWith("light")) {
                                n8 = 1;
                            }
                            if (string.startsWith("lightB")) {
                                n8 = 2;
                            }
                            if (string.startsWith("noOutline")) {
                                bl5 = true;
                            }
                            if (string.startsWith("p(")) {
                                nArray2[n4] = (int)((float)this.getvalue("p", string, 0) * f * f2 * fArray[0]);
                                nArray3[n4] = (int)((float)this.getvalue("p", string, 1) * f * fArray[1]);
                                nArray4[n4] = (int)((float)this.getvalue("p", string, 2) * f * fArray[2]);
                                n = (int)Math.sqrt(nArray2[n4] * nArray2[n4] + nArray3[n4] * nArray3[n4] + nArray4[n4] * nArray4[n4]);
                                if (n > this.maxR) {
                                    this.maxR = n;
                                }
                                ++n4;
                            }
                        }
                        if (string.startsWith("</p>")) {
                            this.p[this.npl] = new Plane(this.m, this.t, nArray2, nArray4, nArray3, n4, nArray5, n9, n5, n6, 0, 0, 0, this.disline, 0, bl3, n8, bl5);
                            if (nArray5[0] == this.fcol[0] && nArray5[1] == this.fcol[1] && nArray5[2] == this.fcol[2] && n9 == 0) {
                                this.p[this.npl].colnum = 1;
                            }
                            if (nArray5[0] == this.scol[0] && nArray5[1] == this.scol[1] && nArray5[2] == this.scol[2] && n9 == 0) {
                                this.p[this.npl].colnum = 2;
                            }
                            ++this.npl;
                            bl = false;
                        }
                    }
                    if (string.startsWith("rims(")) {
                        wheels.setrims(this.getvalue("rims", string, 0), this.getvalue("rims", string, 1), this.getvalue("rims", string, 2), this.getvalue("rims", string, 3), this.getvalue("rims", string, 4));
                    }
                    if (string.startsWith("w(") && n2 < 4) {
                        this.keyx[n2] = (int)((float)this.getvalue("w", string, 0) * f * fArray[0]);
                        this.keyz[n2] = (int)((float)this.getvalue("w", string, 2) * f * fArray[2]);
                        wheels.make(this.m, this.t, this.p, this.npl, (int)((float)this.getvalue("w", string, 0) * f * f2 * fArray[0]), (int)((float)this.getvalue("w", string, 1) * f * fArray[1]), (int)((float)this.getvalue("w", string, 2) * f * fArray[2]), this.getvalue("w", string, 3), (int)((float)this.getvalue("w", string, 4) * f * f2), (int)((float)this.getvalue("w", string, 5) * f), n7);
                        this.npl += 19;
                        if (this.m.loadnew) {
                            this.wh += (int)((float)this.getvalue("w", string, 5) * f);
                            if (wheels.ground > 140) {
                                String string3 = "FRONT";
                                if (this.keyz[n2] < 0) {
                                    string3 = "BACK";
                                }
                                this.err = "Wheels Error:\n" + string3 + " Wheels floor is too far below the center of Y Axis of the car!    \n\nPlease decrease the Y value of the " + string3 + " Wheels or decrease its height.     \n \n";
                                this.errd = true;
                                this.keyz[n2] = 0;
                                this.keyx[n2] = 0;
                            }
                            if (wheels.ground < -100) {
                                String string4 = "FRONT";
                                if (this.keyz[n2] < 0) {
                                    string4 = "BACK";
                                }
                                this.err = "Wheels Error:\n" + string4 + " Wheels floor is too far above the center of Y Axis of the car!    \n\nPlease increase the Y value of the " + string4 + " Wheels or increase its height.     \n \n";
                                this.errd = true;
                                this.keyz[n2] = 0;
                                this.keyx[n2] = 0;
                            }
                            if (Math.abs(this.keyx[n2]) > 400) {
                                String string5 = "FRONT";
                                if (this.keyz[n2] < 0) {
                                    string5 = "BACK";
                                }
                                this.err = "Wheels Error:\n" + string5 + " Wheels are too far apart!    \n\nPlease decrease the \u00b1X value of the " + string5 + " Wheels.     \n \n";
                                this.errd = true;
                                this.keyz[n2] = 0;
                                this.keyx[n2] = 0;
                            }
                            if (Math.abs(this.keyz[n2]) > 700) {
                                this.err = this.keyz[n2] < 0 ? "Wheels Error:\nBACK Wheels are too far backwards from the center of the Z Axis!    \n\nPlease increase the -Z value of the BACK Wheels.     \n \n" : "Wheels Error:\nFRONT Wheels are too far forwards from the center of the Z Axis!    \n\nPlease decrease the +Z value of the FRONT Wheels.     \n \n";
                                this.errd = true;
                                this.keyz[n2] = 0;
                                this.keyx[n2] = 0;
                            }
                            if ((int)((float)this.getvalue("w", string, 4) * f * f2) > 300) {
                                String string6 = "FRONT";
                                if (this.keyz[n2] < 0) {
                                    string6 = "BACK";
                                }
                                this.err = "Wheels Error:\nWidth of the " + string6 + " Wheels is too large!    \n\nPlease decrease the width of the " + string6 + " Wheels.     \n \n";
                                this.errd = true;
                                this.keyz[n2] = 0;
                                this.keyx[n2] = 0;
                            }
                        }
                        ++n2;
                    }
                    if (string.startsWith("tracks")) {
                        n = this.getvalue("tracks", string, 0);
                        this.txy = new int[n];
                        this.tzy = new int[n];
                        this.tc = new int[n][3];
                        this.tradx = new int[n];
                        this.tradz = new int[n];
                        this.trady = new int[n];
                        this.tx = new int[n];
                        this.ty = new int[n];
                        this.tz = new int[n];
                        this.skd = new int[n];
                        this.dam = new int[n];
                        this.notwall = new boolean[n];
                        bl4 = true;
                    }
                    if (bl4) {
                        if (string.startsWith("<track>")) {
                            bl2 = true;
                            this.notwall[this.tnt] = false;
                            this.dam[this.tnt] = 1;
                            this.skd[this.tnt] = 0;
                            this.ty[this.tnt] = 0;
                            this.tx[this.tnt] = 0;
                            this.tz[this.tnt] = 0;
                            this.txy[this.tnt] = 0;
                            this.tzy[this.tnt] = 0;
                            this.trady[this.tnt] = 0;
                            this.tradx[this.tnt] = 0;
                            this.tradz[this.tnt] = 0;
                            this.tc[this.tnt][0] = 0;
                            this.tc[this.tnt][1] = 0;
                            this.tc[this.tnt][2] = 0;
                        }
                        if (bl2) {
                            if (string.startsWith("c")) {
                                this.tc[this.tnt][0] = this.getvalue("c", string, 0);
                                this.tc[this.tnt][1] = this.getvalue("c", string, 1);
                                this.tc[this.tnt][2] = this.getvalue("c", string, 2);
                            }
                            if (string.startsWith("xy")) {
                                this.txy[this.tnt] = this.getvalue("xy", string, 0);
                            }
                            if (string.startsWith("zy")) {
                                this.tzy[this.tnt] = this.getvalue("zy", string, 0);
                            }
                            if (string.startsWith("radx")) {
                                this.tradx[this.tnt] = (int)((float)this.getvalue("radx", string, 0) * f);
                            }
                            if (string.startsWith("rady")) {
                                this.trady[this.tnt] = (int)((float)this.getvalue("rady", string, 0) * f);
                            }
                            if (string.startsWith("radz")) {
                                this.tradz[this.tnt] = (int)((float)this.getvalue("radz", string, 0) * f);
                            }
                            if (string.startsWith("ty")) {
                                this.ty[this.tnt] = (int)((float)this.getvalue("ty", string, 0) * f);
                            }
                            if (string.startsWith("tx")) {
                                this.tx[this.tnt] = (int)((float)this.getvalue("tx", string, 0) * f);
                            }
                            if (string.startsWith("tz")) {
                                this.tz[this.tnt] = (int)((float)this.getvalue("tz", string, 0) * f);
                            }
                            if (string.startsWith("skid")) {
                                this.skd[this.tnt] = this.getvalue("skid", string, 0);
                            }
                            if (string.startsWith("dam")) {
                                this.dam[this.tnt] = 3;
                            }
                            if (string.startsWith("notwall")) {
                                this.notwall[this.tnt] = true;
                            }
                        }
                        if (string.startsWith("</track>")) {
                            bl2 = false;
                            ++this.tnt;
                        }
                    }
                    if (string.startsWith("disp(")) {
                        this.disp = this.getvalue("disp", string, 0);
                    }
                    if (string.startsWith("disline(")) {
                        this.disline = this.getvalue("disline", string, 0) * 2;
                    }
                    if (string.startsWith("shadow")) {
                        this.shadow = true;
                    }
                    if (string.startsWith("stonecold")) {
                        this.noline = true;
                    }
                    if (string.startsWith("newstone")) {
                        this.noline = true;
                        bl5 = true;
                        bl6 = true;
                    }
                    if (string.startsWith("decorative")) {
                        this.decor = true;
                    }
                    if (string.startsWith("road")) {
                        bl3 = true;
                    }
                    if (string.startsWith("notroad")) {
                        bl3 = false;
                    }
                    if (string.startsWith("grounded(")) {
                        this.grounded = (float)this.getvalue("grounded", string, 0) / 100.0f;
                    }
                    if (string.startsWith("div(")) {
                        f = (float)this.getvalue("div", string, 0) / 10.0f;
                    }
                    if (string.startsWith("idiv(")) {
                        f = (float)this.getvalue("idiv", string, 0) / 100.0f;
                    }
                    if (string.startsWith("iwid(")) {
                        f2 = (float)this.getvalue("iwid", string, 0) / 100.0f;
                    }
                    if (string.startsWith("ScaleX(")) {
                        fArray[0] = (float)this.getvalue("ScaleX", string, 0) / 100.0f;
                    }
                    if (string.startsWith("ScaleY(")) {
                        fArray[1] = (float)this.getvalue("ScaleY", string, 0) / 100.0f;
                    }
                    if (string.startsWith("ScaleZ(")) {
                        fArray[2] = (float)this.getvalue("ScaleZ", string, 0) / 100.0f;
                    }
                    if (string.startsWith("gwgr(")) {
                        n7 = this.getvalue("gwgr", string, 0);
                        if (this.m.loadnew) {
                            if (n7 > 40) {
                                n7 = 40;
                            }
                            if (n7 < 0 && n7 >= -15) {
                                n7 = -16;
                            }
                            if (n7 < -40) {
                                n7 = -40;
                            }
                        }
                    }
                    if (string.startsWith("1stColor(")) {
                        this.fcol[0] = this.getvalue("1stColor", string, 0);
                        this.fcol[1] = this.getvalue("1stColor", string, 1);
                        this.fcol[2] = this.getvalue("1stColor", string, 2);
                        ++this.colok;
                    }
                    if (!string.startsWith("2ndColor(")) continue;
                    this.scol[0] = this.getvalue("2ndColor", string, 0);
                    this.scol[1] = this.getvalue("2ndColor", string, 1);
                    this.scol[2] = this.getvalue("2ndColor", string, 2);
                    ++this.colok;
                }
                dataInputStream.close();
            }
            catch (Exception exception) {
                if (this.errd) break block157;
                this.err = "Error While Loading 3D Model\n\nLine:     " + string + "\n\nError Detail:\n" + exception + "           \n \n";
                System.out.println(this.err);
                this.errd = true;
            }
        }
        this.grat = wheels.ground;
        this.sprkat = wheels.sparkat;
        if (this.shadow) {
            int n10;
            this.stg = new int[20];
            this.rtg = new int[100];
            for (n10 = 0; n10 < 20; ++n10) {
                this.stg[n10] = 0;
            }
            for (n10 = 0; n10 < 100; ++n10) {
                this.rtg[n10] = 0;
            }
        }
        if (this.m.loadnew) {
            if (n2 != 0) {
                this.wh /= n2;
            }
            boolean bl = false;
            for (n = 0; n < this.npl; ++n) {
                int n11;
                int n12;
                int n13;
                int n14;
                int n15;
                int n16;
                int n17;
                int n18;
                int n19;
                int n20;
                int n21 = 0;
                int n22 = this.p[n].ox[0];
                int n23 = this.p[n].ox[0];
                int n24 = this.p[n].oy[0];
                int n25 = this.p[n].oy[0];
                int n26 = this.p[n].oz[0];
                int n27 = this.p[n].oz[0];
                for (n20 = 0; n20 < this.p[n].n; ++n20) {
                    if (this.p[n].ox[n20] > n22) {
                        n22 = this.p[n].ox[n20];
                    }
                    if (this.p[n].ox[n20] < n23) {
                        n23 = this.p[n].ox[n20];
                    }
                    if (this.p[n].oy[n20] > n24) {
                        n24 = this.p[n].oy[n20];
                    }
                    if (this.p[n].oy[n20] < n25) {
                        n25 = this.p[n].oy[n20];
                    }
                    if (this.p[n].oz[n20] > n26) {
                        n26 = this.p[n].oz[n20];
                    }
                    if (this.p[n].oz[n20] >= n27) continue;
                    n27 = this.p[n].oz[n20];
                }
                if (Math.abs(n22 - n23) <= Math.abs(n24 - n25) && Math.abs(n22 - n23) <= Math.abs(n26 - n27)) {
                    n21 = 1;
                }
                if (Math.abs(n24 - n25) <= Math.abs(n22 - n23) && Math.abs(n24 - n25) <= Math.abs(n26 - n27)) {
                    n21 = 2;
                }
                if (Math.abs(n26 - n27) <= Math.abs(n22 - n23) && Math.abs(n26 - n27) <= Math.abs(n24 - n25)) {
                    n21 = 3;
                }
                if (!(n21 != 2 || bl && (n24 + n25) / 2 >= this.roofat)) {
                    this.roofat = (n24 + n25) / 2;
                    bl = true;
                }
                if (nArray[n] != 1) continue;
                n20 = 1000;
                int n28 = 0;
                for (n19 = 0; n19 < this.p[n].n; ++n19) {
                    n18 = n19 + 1;
                    if (n18 >= this.p[n].n) {
                        n18 -= this.p[n].n;
                    }
                    if ((n17 = n19 + 2) >= this.p[n].n) {
                        n17 -= this.p[n].n;
                    }
                    if (n21 == 1) {
                        n16 = Math.abs((int)(Math.atan((double)(this.p[n].oz[n19] - this.p[n].oz[n18]) / (double)(this.p[n].oy[n19] - this.p[n].oy[n18])) / (Math.PI / 180)));
                        n15 = Math.abs((int)(Math.atan((double)(this.p[n].oz[n17] - this.p[n].oz[n18]) / (double)(this.p[n].oy[n17] - this.p[n].oy[n18])) / (Math.PI / 180)));
                        if (n16 > 45) {
                            n16 = 90 - n16;
                        } else {
                            n15 = 90 - n15;
                        }
                        if (n16 + n15 < n20) {
                            n20 = n16 + n15;
                            n28 = n19;
                        }
                    }
                    if (n21 == 2) {
                        n16 = Math.abs((int)(Math.atan((double)(this.p[n].oz[n19] - this.p[n].oz[n18]) / (double)(this.p[n].ox[n19] - this.p[n].ox[n18])) / (Math.PI / 180)));
                        n15 = Math.abs((int)(Math.atan((double)(this.p[n].oz[n17] - this.p[n].oz[n18]) / (double)(this.p[n].ox[n17] - this.p[n].ox[n18])) / (Math.PI / 180)));
                        if (n16 > 45) {
                            n16 = 90 - n16;
                        } else {
                            n15 = 90 - n15;
                        }
                        if (n16 + n15 < n20) {
                            n20 = n16 + n15;
                            n28 = n19;
                        }
                    }
                    if (n21 != 3) continue;
                    n16 = Math.abs((int)(Math.atan((double)(this.p[n].oy[n19] - this.p[n].oy[n18]) / (double)(this.p[n].ox[n19] - this.p[n].ox[n18])) / (Math.PI / 180)));
                    n15 = Math.abs((int)(Math.atan((double)(this.p[n].oy[n17] - this.p[n].oy[n18]) / (double)(this.p[n].ox[n17] - this.p[n].ox[n18])) / (Math.PI / 180)));
                    if (n16 > 45) {
                        n16 = 90 - n16;
                    } else {
                        n15 = 90 - n15;
                    }
                    if (n16 + n15 >= n20) continue;
                    n20 = n16 + n15;
                    n28 = n19;
                }
                if (n28 != 0) {
                    int[] nArray6 = new int[this.p[n].n];
                    int[] nArray7 = new int[this.p[n].n];
                    int[] nArray8 = new int[this.p[n].n];
                    for (n16 = 0; n16 < this.p[n].n; ++n16) {
                        nArray6[n16] = this.p[n].ox[n16];
                        nArray7[n16] = this.p[n].oy[n16];
                        nArray8[n16] = this.p[n].oz[n16];
                    }
                    for (n16 = 0; n16 < this.p[n].n; ++n16) {
                        n15 = n16 + n28;
                        if (n15 >= this.p[n].n) {
                            n15 -= this.p[n].n;
                        }
                        this.p[n].ox[n16] = nArray6[n15];
                        this.p[n].oy[n16] = nArray7[n15];
                        this.p[n].oz[n16] = nArray8[n15];
                    }
                }
                if (n21 == 1) {
                    this.p[n].fs = Math.abs(this.p[n].oz[0] - this.p[n].oz[1]) > Math.abs(this.p[n].oy[0] - this.p[n].oy[1]) ? (this.p[n].oz[0] > this.p[n].oz[1] ? (this.p[n].oy[1] > this.p[n].oy[2] ? 1 : -1) : (this.p[n].oy[1] > this.p[n].oy[2] ? -1 : 1)) : (this.p[n].oy[0] > this.p[n].oy[1] ? (this.p[n].oz[1] > this.p[n].oz[2] ? -1 : 1) : (this.p[n].oz[1] > this.p[n].oz[2] ? 1 : -1));
                }
                if (n21 == 2) {
                    this.p[n].fs = Math.abs(this.p[n].oz[0] - this.p[n].oz[1]) > Math.abs(this.p[n].ox[0] - this.p[n].ox[1]) ? (this.p[n].oz[0] > this.p[n].oz[1] ? (this.p[n].ox[1] > this.p[n].ox[2] ? -1 : 1) : (this.p[n].ox[1] > this.p[n].ox[2] ? 1 : -1)) : (this.p[n].ox[0] > this.p[n].ox[1] ? (this.p[n].oz[1] > this.p[n].oz[2] ? 1 : -1) : (this.p[n].oz[1] > this.p[n].oz[2] ? -1 : 1));
                }
                if (n21 == 3) {
                    this.p[n].fs = Math.abs(this.p[n].oy[0] - this.p[n].oy[1]) > Math.abs(this.p[n].ox[0] - this.p[n].ox[1]) ? (this.p[n].oy[0] > this.p[n].oy[1] ? (this.p[n].ox[1] > this.p[n].ox[2] ? 1 : -1) : (this.p[n].ox[1] > this.p[n].ox[2] ? -1 : 1)) : (this.p[n].ox[0] > this.p[n].ox[1] ? (this.p[n].oy[1] > this.p[n].oy[2] ? -1 : 1) : (this.p[n].oy[1] > this.p[n].oy[2] ? 1 : -1));
                }
                n19 = 0;
                n18 = 0;
                for (n17 = 0; n17 < this.npl; ++n17) {
                    if (n17 != n && nArray[n17] != 0) {
                        int n29;
                        n16 = 0;
                        n15 = this.p[n17].ox[0];
                        n14 = this.p[n17].ox[0];
                        n13 = this.p[n17].oy[0];
                        int n30 = this.p[n17].oy[0];
                        n12 = this.p[n17].oz[0];
                        n11 = this.p[n17].oz[0];
                        for (n29 = 0; n29 < this.p[n17].n; ++n29) {
                            if (this.p[n17].ox[n29] > n15) {
                                n15 = this.p[n17].ox[n29];
                            }
                            if (this.p[n17].ox[n29] < n14) {
                                n14 = this.p[n17].ox[n29];
                            }
                            if (this.p[n17].oy[n29] > n13) {
                                n13 = this.p[n17].oy[n29];
                            }
                            if (this.p[n17].oy[n29] < n30) {
                                n30 = this.p[n17].oy[n29];
                            }
                            if (this.p[n17].oz[n29] > n12) {
                                n12 = this.p[n17].oz[n29];
                            }
                            if (this.p[n17].oz[n29] >= n11) continue;
                            n11 = this.p[n17].oz[n29];
                        }
                        n29 = (n15 + n14) / 2;
                        int n31 = (n13 + n30) / 2;
                        int n32 = (n12 + n11) / 2;
                        int n33 = (n22 + n23) / 2;
                        int n34 = (n24 + n25) / 2;
                        int n35 = (n26 + n27) / 2;
                        if (n21 == 1 && (n31 <= n24 && n31 >= n25 && n32 <= n26 && n32 >= n27 || n34 <= n13 && n34 >= n30 && n35 <= n12 && n35 >= n11)) {
                            if (n15 < n23) {
                                n19 = 1;
                            }
                            if (n14 > n22) {
                                n18 = 1;
                            }
                        }
                        if (n21 == 2 && (n29 <= n22 && n29 >= n23 && n32 <= n26 && n32 >= n27 || n33 <= n15 && n33 >= n14 && n35 <= n12 && n35 >= n11)) {
                            if (n13 < n25) {
                                n19 = 1;
                            }
                            if (n30 > n24) {
                                n18 = 1;
                            }
                        }
                        if (n21 == 3 && (n29 <= n22 && n29 >= n23 && n31 <= n24 && n31 >= n25 || n33 <= n15 && n33 >= n14 && n34 <= n13 && n34 >= n30)) {
                            if (n12 < n27) {
                                n19 = 1;
                            }
                            if (n11 > n26) {
                                n18 = 1;
                            }
                        }
                    }
                    if (n19 != 0 && n18 != 0) break;
                }
                n17 = 0;
                if (n19 != 0 && n18 == 0) {
                    n17 = 1;
                }
                if (n18 != 0 && n19 == 0) {
                    this.p[n].fs *= -1;
                    n17 = 1;
                }
                if (n19 != 0 && n18 != 0) {
                    this.p[n].fs = 0;
                    this.p[n].gr = 40;
                    n17 = 1;
                }
                if (n17 == 0) {
                    n16 = 0;
                    n15 = 0;
                    if (n21 == 1) {
                        n15 = n16 = (n22 + n23) / 2;
                    }
                    if (n21 == 2) {
                        n15 = n16 = (n24 + n25) / 2;
                    }
                    if (n21 == 3) {
                        n15 = n16 = (n26 + n27) / 2;
                    }
                    for (n14 = 0; n14 < this.npl; ++n14) {
                        if (n14 == n) continue;
                        n13 = 0;
                        boolean[] blArray = new boolean[this.p[n14].n];
                        for (n12 = 0; n12 < this.p[n14].n; ++n12) {
                            blArray[n12] = false;
                            for (n11 = 0; n11 < this.p[n].n; ++n11) {
                                if (this.p[n].ox[n11] != this.p[n14].ox[n12] || this.p[n].oy[n11] != this.p[n14].oy[n12] || this.p[n].oz[n11] != this.p[n14].oz[n12]) continue;
                                blArray[n12] = true;
                                n13 = 1;
                            }
                        }
                        if (n13 == 0) continue;
                        for (n12 = 0; n12 < this.p[n14].n; ++n12) {
                            if (blArray[n12]) continue;
                            if (n21 == 1) {
                                if (this.p[n14].ox[n12] > n16) {
                                    n16 = this.p[n14].ox[n12];
                                }
                                if (this.p[n14].ox[n12] < n15) {
                                    n15 = this.p[n14].ox[n12];
                                }
                            }
                            if (n21 == 2) {
                                if (this.p[n14].oy[n12] > n16) {
                                    n16 = this.p[n14].oy[n12];
                                }
                                if (this.p[n14].oy[n12] < n15) {
                                    n15 = this.p[n14].oy[n12];
                                }
                            }
                            if (n21 != 3) continue;
                            if (this.p[n14].oz[n12] > n16) {
                                n16 = this.p[n14].oz[n12];
                            }
                            if (this.p[n14].oz[n12] >= n15) continue;
                            n15 = this.p[n14].oz[n12];
                        }
                    }
                    if (n21 == 1) {
                        if ((n16 + n15) / 2 > (n22 + n23) / 2) {
                            this.p[n].fs *= -1;
                        } else if ((n16 + n15) / 2 == (n22 + n23) / 2 && (n22 + n23) / 2 < 0) {
                            this.p[n].fs *= -1;
                        }
                    }
                    if (n21 == 2) {
                        if ((n16 + n15) / 2 > (n24 + n25) / 2) {
                            this.p[n].fs *= -1;
                        } else if ((n16 + n15) / 2 == (n24 + n25) / 2 && (n24 + n25) / 2 < 0) {
                            this.p[n].fs *= -1;
                        }
                    }
                    if (n21 == 3) {
                        if ((n16 + n15) / 2 > (n26 + n27) / 2) {
                            this.p[n].fs *= -1;
                        } else if ((n16 + n15) / 2 == (n26 + n27) / 2 && (n26 + n27) / 2 < 0) {
                            this.p[n].fs *= -1;
                        }
                    }
                }
                this.p[n].deltafntyp();
            }
        }
    }

    public ContO(ContO contO, int n, int n2, int n3, int n4) {
        int n5;
        this.npl = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.xz = 0;
        this.xy = 0;
        this.zy = 0;
        this.wxz = 0;
        this.wzy = 0;
        this.dist = 0;
        this.maxR = 0;
        this.disp = 0;
        this.disline = 14;
        this.shadow = false;
        this.noline = false;
        this.decor = false;
        this.grounded = 1.0f;
        this.grat = 0;
        this.keyx = new int[4];
        this.keyz = new int[4];
        this.sprkat = 0;
        this.tnt = 0;
        this.ust = 0;
        this.srx = 0;
        this.sry = 0;
        this.srz = 0;
        this.rcx = 0.0f;
        this.rcy = 0.0f;
        this.rcz = 0.0f;
        this.sprk = 0;
        this.elec = false;
        this.roted = false;
        this.edl = new int[4];
        this.edr = new int[4];
        this.elc = new int[]{0, 0, 0, 0};
        this.fix = false;
        this.fcnt = 0;
        this.checkpoint = 0;
        this.fcol = new int[]{0, 0, 0};
        this.scol = new int[]{0, 0, 0};
        this.colok = 0;
        this.errd = false;
        this.err = "";
        this.roofat = 0;
        this.wh = 0;
        this.m = contO.m;
        this.t = contO.t;
        this.npl = contO.npl;
        this.maxR = contO.maxR;
        this.disp = contO.disp;
        this.disline = contO.disline;
        this.noline = contO.noline;
        this.shadow = contO.shadow;
        this.grounded = contO.grounded;
        this.decor = contO.decor;
        if (this.m.loadnew && (n4 == 90 || n4 == -90)) {
            this.grounded += 10000.0f;
        }
        this.grat = contO.grat;
        this.sprkat = contO.sprkat;
        this.p = new Plane[contO.npl];
        for (n5 = 0; n5 < this.npl; ++n5) {
            if (contO.p[n5].master == 1) {
                contO.p[n5].n = 20;
            }
            this.p[n5] = new Plane(this.m, this.t, contO.p[n5].ox, contO.p[n5].oz, contO.p[n5].oy, contO.p[n5].n, contO.p[n5].oc, contO.p[n5].glass, contO.p[n5].gr, contO.p[n5].fs, contO.p[n5].wx, contO.p[n5].wy, contO.p[n5].wz, contO.disline, contO.p[n5].bfase, contO.p[n5].road, contO.p[n5].light, contO.p[n5].solo);
        }
        this.x = n;
        this.y = n2;
        this.z = n3;
        this.xz = 0;
        this.xy = 0;
        this.zy = 0;
        for (n5 = 0; n5 < this.npl; ++n5) {
            this.p[n5].colnum = contO.p[n5].colnum;
            this.p[n5].master = contO.p[n5].master;
            this.p[n5].rot(this.p[n5].ox, this.p[n5].oz, 0, 0, n4, this.p[n5].n);
            this.p[n5].loadprojf();
        }
        if (contO.tnt != 0) {
            for (n5 = 0; n5 < contO.tnt; ++n5) {
                int n6;
                this.t.xy[this.t.nt] = (int)((float)contO.txy[n5] * this.m.cos(n4) - (float)contO.tzy[n5] * this.m.sin(n4));
                this.t.zy[this.t.nt] = (int)((float)contO.tzy[n5] * this.m.cos(n4) + (float)contO.txy[n5] * this.m.sin(n4));
                for (n6 = 0; n6 < 3; ++n6) {
                    this.t.c[this.t.nt][n6] = (int)((float)contO.tc[n5][n6] + (float)contO.tc[n5][n6] * ((float)this.m.snap[n6] / 100.0f));
                    if (this.t.c[this.t.nt][n6] > 255) {
                        this.t.c[this.t.nt][n6] = 255;
                    }
                    if (this.t.c[this.t.nt][n6] >= 0) continue;
                    this.t.c[this.t.nt][n6] = 0;
                }
                this.t.x[this.t.nt] = (int)((float)this.x + (float)contO.tx[n5] * this.m.cos(n4) - (float)contO.tz[n5] * this.m.sin(n4));
                this.t.z[this.t.nt] = (int)((float)this.z + (float)contO.tz[n5] * this.m.cos(n4) + (float)contO.tx[n5] * this.m.sin(n4));
                this.t.y[this.t.nt] = this.y + contO.ty[n5];
                this.t.skd[this.t.nt] = contO.skd[n5];
                this.t.dam[this.t.nt] = contO.dam[n5];
                this.t.notwall[this.t.nt] = contO.notwall[n5];
                this.t.decor[this.t.nt] = this.decor;
                n6 = Math.abs(n4);
                if (n6 == 180) {
                    n6 = 0;
                }
                this.t.radx[this.t.nt] = (int)Math.abs((float)contO.tradx[n5] * this.m.cos(n6) + (float)contO.tradz[n5] * this.m.sin(n6));
                this.t.radz[this.t.nt] = (int)Math.abs((float)contO.tradx[n5] * this.m.sin(n6) + (float)contO.tradz[n5] * this.m.cos(n6));
                this.t.rady[this.t.nt] = contO.trady[n5];
                ++this.t.nt;
            }
        }
        for (n5 = 0; n5 < 4; ++n5) {
            this.keyx[n5] = contO.keyx[n5];
            this.keyz[n5] = contO.keyz[n5];
        }
        if (this.shadow) {
            this.stg = new int[20];
            this.sx = new int[20];
            this.sy = new int[20];
            this.sz = new int[20];
            this.scx = new int[20];
            this.scz = new int[20];
            this.osmag = new float[20];
            this.sav = new int[20];
            this.smag = new float[20][8];
            this.srgb = new int[20][3];
            this.sbln = new float[20];
            this.ust = 0;
            for (n5 = 0; n5 < 20; ++n5) {
                this.stg[n5] = 0;
            }
            this.rtg = new int[100];
            this.rbef = new boolean[100];
            this.rx = new int[100];
            this.ry = new int[100];
            this.rz = new int[100];
            this.vrx = new float[100];
            this.vry = new float[100];
            this.vrz = new float[100];
            for (n5 = 0; n5 < 100; ++n5) {
                this.rtg[n5] = 0;
            }
        }
    }

    public ContO(int n, int n2, int n3, Medium medium, Trackers trackers, int n4, int n5, int n6) {
        int n7;
        int n8;
        int n9;
        this.npl = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.xz = 0;
        this.xy = 0;
        this.zy = 0;
        this.wxz = 0;
        this.wzy = 0;
        this.dist = 0;
        this.maxR = 0;
        this.disp = 0;
        this.disline = 14;
        this.shadow = false;
        this.noline = false;
        this.decor = false;
        this.grounded = 1.0f;
        this.grat = 0;
        this.keyx = new int[4];
        this.keyz = new int[4];
        this.sprkat = 0;
        this.tnt = 0;
        this.ust = 0;
        this.srx = 0;
        this.sry = 0;
        this.srz = 0;
        this.rcx = 0.0f;
        this.rcy = 0.0f;
        this.rcz = 0.0f;
        this.sprk = 0;
        this.elec = false;
        this.roted = false;
        this.edl = new int[4];
        this.edr = new int[4];
        this.elc = new int[]{0, 0, 0, 0};
        this.fix = false;
        this.fcnt = 0;
        this.checkpoint = 0;
        this.fcol = new int[]{0, 0, 0};
        this.scol = new int[]{0, 0, 0};
        this.colok = 0;
        this.errd = false;
        this.err = "";
        this.roofat = 0;
        this.wh = 0;
        this.m = medium;
        this.t = trackers;
        this.x = n4;
        this.z = n5;
        this.y = n6;
        this.xz = 0;
        this.xy = 0;
        this.zy = 0;
        this.grat = 0;
        this.sprkat = 0;
        this.disline = 4;
        this.noline = true;
        this.shadow = false;
        this.grounded = 115.0f;
        this.decor = true;
        this.npl = 5;
        this.p = new Plane[5];
        Random random = new Random(n);
        int[] nArray = new int[8];
        int[] nArray2 = new int[8];
        int[] nArray3 = new int[8];
        int[] nArray4 = new int[8];
        int[] nArray5 = new int[8];
        float f = n2;
        float f2 = n3;
        if (f2 < 2.0f) {
            f2 = 2.0f;
        }
        if (f2 > 6.0f) {
            f2 = 6.0f;
        }
        if (f < 2.0f) {
            f = 2.0f;
        }
        if (f > 6.0f) {
            f = 6.0f;
        }
        f2 /= 1.5f;
        f2 *= 1.0f + ((f /= 1.5f) - 2.0f) * 0.1786f;
        float f3 = (float)(50.0 + 100.0 * random.nextDouble());
        nArray[0] = -((int)(f3 * f * 0.7071f));
        nArray2[0] = (int)(f3 * f * 0.7071f);
        f3 = (float)(50.0 + 100.0 * random.nextDouble());
        nArray[1] = 0;
        nArray2[1] = (int)(f3 * f);
        f3 = (float)(50.0 + 100.0 * random.nextDouble());
        nArray[2] = (int)((double)(f3 * f) * 0.7071);
        nArray2[2] = (int)((double)(f3 * f) * 0.7071);
        f3 = (float)(50.0 + 100.0 * random.nextDouble());
        nArray[3] = (int)(f3 * f);
        nArray2[3] = 0;
        f3 = (float)(50.0 + 100.0 * random.nextDouble());
        nArray[4] = (int)((double)(f3 * f) * 0.7071);
        nArray2[4] = -((int)((double)(f3 * f) * 0.7071));
        f3 = (float)(50.0 + 100.0 * random.nextDouble());
        nArray[5] = 0;
        nArray2[5] = -((int)(f3 * f));
        f3 = (float)(50.0 + 100.0 * random.nextDouble());
        nArray[6] = -((int)((double)(f3 * f) * 0.7071));
        nArray2[6] = -((int)((double)(f3 * f) * 0.7071));
        f3 = (float)(50.0 + 100.0 * random.nextDouble());
        nArray[7] = -((int)(f3 * f));
        nArray2[7] = 0;
        for (n9 = 0; n9 < 8; ++n9) {
            nArray3[n9] = (int)((double)nArray[n9] * (0.2 + 0.4 * random.nextDouble()));
            nArray4[n9] = (int)((double)nArray2[n9] * (0.2 + 0.4 * random.nextDouble()));
            nArray5[n9] = -((int)((10.0 + 15.0 * random.nextDouble()) * (double)f2));
        }
        this.maxR = 0;
        for (n9 = 0; n9 < 8; ++n9) {
            int n10;
            int n11 = n9 - 1;
            if (n11 == -1) {
                n11 = 7;
            }
            if ((n10 = n9 + 1) == 8) {
                n10 = 0;
            }
            nArray[n9] = ((nArray[n11] + nArray[n10]) / 2 + nArray[n9]) / 2;
            nArray2[n9] = ((nArray2[n11] + nArray2[n10]) / 2 + nArray2[n9]) / 2;
            nArray3[n9] = ((nArray3[n11] + nArray3[n10]) / 2 + nArray3[n9]) / 2;
            nArray4[n9] = ((nArray4[n11] + nArray4[n10]) / 2 + nArray4[n9]) / 2;
            nArray5[n9] = ((nArray5[n11] + nArray5[n10]) / 2 + nArray5[n9]) / 2;
            n8 = (int)Math.sqrt(nArray[n9] * nArray[n9] + nArray2[n9] * nArray2[n9]);
            if (n8 > this.maxR) {
                this.maxR = n8;
            }
            if ((n8 = (int)Math.sqrt(nArray3[n9] * nArray3[n9] + nArray5[n9] * nArray5[n9] + nArray4[n9] * nArray4[n9])) <= this.maxR) continue;
            this.maxR = n8;
        }
        this.disp = this.maxR / 17;
        int[] nArray6 = new int[3];
        float f4 = -1.0f;
        float f5 = (f / f2 - 0.33f) / 33.4f;
        if ((double)f5 < 0.005) {
            f5 = 0.0f;
        }
        if ((double)f5 > 0.057) {
            f5 = 0.057f;
        }
        for (n8 = 0; n8 < 4; ++n8) {
            int n12 = n8 * 2;
            n7 = n12 + 2;
            if (n7 == 8) {
                n7 = 0;
            }
            int[] nArray7 = new int[6];
            int[] nArray8 = new int[6];
            int[] nArray9 = new int[6];
            nArray7[0] = nArray[n12];
            nArray7[1] = nArray[n12 + 1];
            nArray7[2] = nArray[n7];
            nArray7[5] = nArray3[n12];
            nArray7[4] = nArray3[n12 + 1];
            nArray7[3] = nArray3[n7];
            nArray9[0] = nArray2[n12];
            nArray9[1] = nArray2[n12 + 1];
            nArray9[2] = nArray2[n7];
            nArray9[5] = nArray4[n12];
            nArray9[4] = nArray4[n12 + 1];
            nArray9[3] = nArray4[n7];
            nArray8[0] = 0;
            nArray8[1] = 0;
            nArray8[2] = 0;
            nArray8[5] = nArray5[n12];
            nArray8[4] = nArray5[n12 + 1];
            nArray8[3] = nArray5[n7];
            f3 = (float)((0.17 - (double)f5) * random.nextDouble());
            while ((double)Math.abs(f4 - f3) < 0.03 - (double)(f5 * 0.176f)) {
                f3 = (float)((0.17 - (double)f5) * random.nextDouble());
            }
            f4 = f3;
            for (int i = 0; i < 3; ++i) {
                nArray6[i] = this.m.trk == 2 ? (int)(390.0f / (2.2f + f3 - f5)) : (int)((float)(this.m.cpol[i] + this.m.cgrnd[i]) / (2.2f + f3 - f5));
            }
            this.p[n8] = new Plane(this.m, this.t, nArray7, nArray9, nArray8, 6, nArray6, 3, -8, 0, 0, 0, 0, this.disline, 0, true, 0, false);
        }
        f3 = (float)(0.02 * random.nextDouble());
        for (n8 = 0; n8 < 3; ++n8) {
            nArray6[n8] = this.m.trk == 2 ? (int)(390.0f / (2.15f + f3)) : (int)((float)(this.m.cpol[n8] + this.m.cgrnd[n8]) / (2.15f + f3));
        }
        this.p[4] = new Plane(this.m, this.t, nArray3, nArray4, nArray5, 8, nArray6, 3, -8, 0, 0, 0, 0, this.disline, 0, true, 0, false);
        int[] nArray10 = new int[2];
        int[] nArray11 = new int[2];
        for (n7 = 0; n7 < 4; ++n7) {
            int n13 = n7 * 2 + 1;
            this.t.y[this.t.nt] = nArray5[n13] / 2;
            this.t.rady[this.t.nt] = Math.abs(nArray5[n13] / 2);
            if (n7 == 0 || n7 == 2) {
                this.t.z[this.t.nt] = (nArray2[n13] + nArray4[n13]) / 2;
                this.t.radz[this.t.nt] = Math.abs(this.t.z[this.t.nt] - nArray2[n13]);
                n13 = n7 * 2 + 2;
                if (n13 == 8) {
                    n13 = 0;
                }
                this.t.x[this.t.nt] = (nArray[n7 * 2] + nArray[n13]) / 2;
                this.t.radx[this.t.nt] = Math.abs(this.t.x[this.t.nt] - nArray[n7 * 2]);
            } else {
                this.t.x[this.t.nt] = (nArray[n13] + nArray3[n13]) / 2;
                this.t.radx[this.t.nt] = Math.abs(this.t.x[this.t.nt] - nArray[n13]);
                n13 = n7 * 2 + 2;
                if (n13 == 8) {
                    n13 = 0;
                }
                this.t.z[this.t.nt] = (nArray2[n7 * 2] + nArray2[n13]) / 2;
                this.t.radz[this.t.nt] = Math.abs(this.t.z[this.t.nt] - nArray2[n7 * 2]);
            }
            if (n7 == 0) {
                nArray11[0] = this.t.z[this.t.nt] - this.t.radz[this.t.nt];
                this.t.zy[this.t.nt] = (int)(Math.atan((double)this.t.rady[this.t.nt] / (double)this.t.radz[this.t.nt]) / (Math.PI / 180));
                if (this.t.zy[this.t.nt] > 40) {
                    this.t.zy[this.t.nt] = 40;
                }
                this.t.xy[this.t.nt] = 0;
            }
            if (n7 == 1) {
                nArray10[0] = this.t.x[this.t.nt] - this.t.radx[this.t.nt];
                this.t.xy[this.t.nt] = (int)(Math.atan((double)this.t.rady[this.t.nt] / (double)this.t.radx[this.t.nt]) / (Math.PI / 180));
                if (this.t.xy[this.t.nt] > 40) {
                    this.t.xy[this.t.nt] = 40;
                }
                this.t.zy[this.t.nt] = 0;
            }
            if (n7 == 2) {
                nArray11[1] = this.t.z[this.t.nt] + this.t.radz[this.t.nt];
                this.t.zy[this.t.nt] = -((int)(Math.atan((double)this.t.rady[this.t.nt] / (double)this.t.radz[this.t.nt]) / (Math.PI / 180)));
                if (this.t.zy[this.t.nt] < -40) {
                    this.t.zy[this.t.nt] = -40;
                }
                this.t.xy[this.t.nt] = 0;
            }
            if (n7 == 3) {
                nArray10[1] = this.t.x[this.t.nt] + this.t.radx[this.t.nt];
                this.t.xy[this.t.nt] = -((int)(Math.atan((double)this.t.rady[this.t.nt] / (double)this.t.radx[this.t.nt]) / (Math.PI / 180)));
                if (this.t.xy[this.t.nt] < -40) {
                    this.t.xy[this.t.nt] = -40;
                }
                this.t.zy[this.t.nt] = 0;
            }
            int n14 = this.t.nt;
            this.t.x[n14] = this.t.x[n14] + this.x;
            int n15 = this.t.nt;
            this.t.z[n15] = this.t.z[n15] + this.z;
            int n16 = this.t.nt;
            this.t.y[n16] = this.t.y[n16] + this.y;
            for (int i = 0; i < 3; ++i) {
                this.t.c[this.t.nt][i] = this.p[n7].oc[i];
            }
            this.t.skd[this.t.nt] = 2;
            this.t.dam[this.t.nt] = 1;
            this.t.notwall[this.t.nt] = false;
            this.t.decor[this.t.nt] = true;
            int n17 = this.t.nt++;
            this.t.rady[n17] = this.t.rady[n17] + 10;
        }
        this.t.y[this.t.nt] = 0;
        for (n7 = 0; n7 < 8; ++n7) {
            int n18 = this.t.nt;
            this.t.y[n18] = this.t.y[n18] + nArray5[n7];
        }
        this.t.y[this.t.nt] = this.t.y[this.t.nt] / 8;
        int n19 = this.t.nt;
        this.t.y[n19] = this.t.y[n19] + this.y;
        this.t.rady[this.t.nt] = 200;
        this.t.radx[this.t.nt] = nArray10[0] - nArray10[1];
        this.t.radz[this.t.nt] = nArray11[0] - nArray11[1];
        this.t.x[this.t.nt] = (nArray10[0] + nArray10[1]) / 2 + this.x;
        this.t.z[this.t.nt] = (nArray11[0] + nArray11[1]) / 2 + this.z;
        this.t.zy[this.t.nt] = 0;
        this.t.xy[this.t.nt] = 0;
        for (n7 = 0; n7 < 3; ++n7) {
            this.t.c[this.t.nt][n7] = this.p[4].oc[n7];
        }
        this.t.skd[this.t.nt] = 4;
        this.t.dam[this.t.nt] = 1;
        this.t.notwall[this.t.nt] = false;
        this.t.decor[this.t.nt] = true;
        ++this.t.nt;
    }

    public void d(Graphics2D graphics2D) {
        int n;
        if (this.dist != 0) {
            this.dist = 0;
        }
        int n2 = this.m.cx + (int)((float)(this.x - this.m.x - this.m.cx) * this.m.cos(this.m.xz) - (float)(this.z - this.m.z - this.m.cz) * this.m.sin(this.m.xz));
        int n3 = this.m.cz + (int)((float)(this.x - this.m.x - this.m.cx) * this.m.sin(this.m.xz) + (float)(this.z - this.m.z - this.m.cz) * this.m.cos(this.m.xz));
        int n4 = this.m.cz + (int)((float)(this.y - this.m.y - this.m.cy) * this.m.sin(this.m.zy) + (float)(n3 - this.m.cz) * this.m.cos(this.m.zy));
        int n5 = this.xs(n2 + this.maxR, n4) - this.xs(n2 - this.maxR, n4);
        if (!(this.xs(n2 + this.maxR * 2, n4) <= this.m.iw || this.xs(n2 - this.maxR * 2, n4) >= this.m.w || n4 <= -this.maxR || n4 >= this.m.fade[this.disline] + this.maxR && this.m.trk == 0 || n5 <= this.disp && this.m.trk == 0 || this.decor && (this.m.resdown == 2 || this.m.trk == 1))) {
            int n6;
            int n7;
            int n8;
            if (this.shadow) {
                if (!this.m.crs) {
                    if (n4 < 2000) {
                        int n9;
                        n = 0;
                        if (this.t.ncx != 0 || this.t.ncz != 0) {
                            n8 = (this.x - this.t.sx) / 3000;
                            if (n8 > this.t.ncx) {
                                n8 = this.t.ncx;
                            }
                            if (n8 < 0) {
                                n8 = 0;
                            }
                            if ((n9 = (this.z - this.t.sz) / 3000) > this.t.ncz) {
                                n9 = this.t.ncz;
                            }
                            if (n9 < 0) {
                                n9 = 0;
                            }
                            for (n7 = this.t.sect[n8][n9].length - 1; n7 >= 0; --n7) {
                                n6 = this.t.sect[n8][n9][n7];
                                if (Math.abs(this.t.zy[n6]) == 90 || Math.abs(this.t.xy[n6]) == 90 || Math.abs(this.x - this.t.x[n6]) >= this.t.radx[n6] + this.maxR || Math.abs(this.z - this.t.z[n6]) >= this.t.radz[n6] + this.maxR || this.t.decor[n6] && this.m.resdown == 2) continue;
                                n = 1;
                                break;
                            }
                        }
                        if (n != 0) {
                            for (n8 = 0; n8 < this.npl; ++n8) {
                                this.p[n8].s(graphics2D, this.x - this.m.x, this.y - this.m.y, this.z - this.m.z, this.xz, this.xy, this.zy, 0);
                            }
                        } else {
                            n8 = this.m.cy + (int)((float)(this.m.ground - this.m.cy) * this.m.cos(this.m.zy) - (float)(n3 - this.m.cz) * this.m.sin(this.m.zy));
                            if (this.ys(n8 + this.maxR, n9 = this.m.cz + (int)((float)(this.m.ground - this.m.cy) * this.m.sin(this.m.zy) + (float)(n3 - this.m.cz) * this.m.cos(this.m.zy))) > 0 && this.ys(n8 - this.maxR, n9) < this.m.h) {
                                for (n7 = 0; n7 < this.npl; ++n7) {
                                    this.p[n7].s(graphics2D, this.x - this.m.x, this.y - this.m.y, this.z - this.m.z, this.xz, this.xy, this.zy, 1);
                                }
                            }
                        }
                        this.m.addsp(this.x - this.m.x, this.z - this.m.z, (int)((double)this.maxR * 0.8));
                    } else {
                        this.lowshadow(graphics2D, n4);
                    }
                } else {
                    for (n = 0; n < this.npl; ++n) {
                        this.p[n].s(graphics2D, this.x - this.m.x, this.y - this.m.y, this.z - this.m.z, this.xz, this.xy, this.zy, 2);
                    }
                }
            }
            if (this.ys((n = this.m.cy + (int)((float)(this.y - this.m.y - this.m.cy) * this.m.cos(this.m.zy) - (float)(n3 - this.m.cz) * this.m.sin(this.m.zy))) + this.maxR, n4) > this.m.ih && this.ys(n - this.maxR, n4) < this.m.h) {
                if (this.elec && this.m.noelec == 0) {
                    this.electrify(graphics2D);
                }
                if (this.fix) {
                    this.fixit(graphics2D);
                }
                if (this.checkpoint != 0 && this.checkpoint - 1 == this.m.checkpoint) {
                    n5 = -1;
                }
                if (this.shadow) {
                    this.dist = (int)Math.sqrt((this.m.x + this.m.cx - this.x) * (this.m.x + this.m.cx - this.x) + (this.m.z - this.z) * (this.m.z - this.z) + (this.m.y + this.m.cy - this.y) * (this.m.y + this.m.cy - this.y));
                    for (n8 = 0; n8 < 20; ++n8) {
                        if (this.stg[n8] == 0) continue;
                        this.pdust(n8, graphics2D, true);
                    }
                    this.dsprk(graphics2D, true);
                }
                int[] nArray = new int[this.npl];
                int[] nArray2 = new int[this.npl];
                for (n7 = 0; n7 < this.npl; ++n7) {
                    nArray[n7] = 0;
                }
                for (n7 = 0; n7 < this.npl; ++n7) {
                    for (n6 = n7 + 1; n6 < this.npl; ++n6) {
                        if (this.p[n7].av != this.p[n6].av) {
                            if (this.p[n7].av < this.p[n6].av) {
                                int n10 = n7;
                                nArray[n10] = nArray[n10] + 1;
                                continue;
                            }
                            int n11 = n6;
                            nArray[n11] = nArray[n11] + 1;
                            continue;
                        }
                        if (n7 > n6) {
                            int n12 = n7;
                            nArray[n12] = nArray[n12] + 1;
                            continue;
                        }
                        int n13 = n6;
                        nArray[n13] = nArray[n13] + 1;
                    }
                    nArray2[nArray[n7]] = n7;
                }
                for (n7 = 0; n7 < this.npl; ++n7) {
                    this.p[nArray2[n7]].d(graphics2D, this.x - this.m.x, this.y - this.m.y, this.z - this.m.z, this.xz, this.xy, this.zy, this.wxz, this.wzy, this.noline, n5);
                }
                if (this.shadow) {
                    for (n7 = 0; n7 < 20; ++n7) {
                        if (this.stg[n7] == 0) continue;
                        this.pdust(n7, graphics2D, false);
                    }
                    this.dsprk(graphics2D, false);
                }
                this.dist = (int)(Math.sqrt((int)Math.sqrt((this.m.x + this.m.cx - this.x) * (this.m.x + this.m.cx - this.x) + (this.m.z - this.z) * (this.m.z - this.z) + (this.m.y + this.m.cy - this.y) * (this.m.y + this.m.cy - this.y))) * (double)this.grounded);
            }
        }
        if (this.shadow && this.dist == 0) {
            for (n = 0; n < 20; ++n) {
                if (this.stg[n] == 0) continue;
                this.stg[n] = 0;
            }
            for (n = 0; n < 100; ++n) {
                if (this.rtg[n] == 0) continue;
                this.rtg[n] = 0;
            }
            if (this.sprk != 0) {
                this.sprk = 0;
            }
        }
    }

    public void lowshadow(Graphics2D graphics2D, int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int[] nArray = new int[4];
        int[] nArray2 = new int[4];
        int[] nArray3 = new int[4];
        int n9 = 1;
        for (n8 = Math.abs(this.zy); n8 > 270; n8 -= 360) {
        }
        if ((n8 = Math.abs(n8)) > 90) {
            n9 = -1;
        }
        nArray[0] = (int)((double)this.keyx[0] * 1.2 + (double)this.x - (double)this.m.x);
        nArray3[0] = (int)((double)((this.keyz[0] + 30) * n9) * 1.2 + (double)this.z - (double)this.m.z);
        nArray[1] = (int)((double)this.keyx[1] * 1.2 + (double)this.x - (double)this.m.x);
        nArray3[1] = (int)((double)((this.keyz[1] + 30) * n9) * 1.2 + (double)this.z - (double)this.m.z);
        nArray[2] = (int)((double)this.keyx[3] * 1.2 + (double)this.x - (double)this.m.x);
        nArray3[2] = (int)((double)((this.keyz[3] - 30) * n9) * 1.2 + (double)this.z - (double)this.m.z);
        nArray[3] = (int)((double)this.keyx[2] * 1.2 + (double)this.x - (double)this.m.x);
        nArray3[3] = (int)((double)((this.keyz[2] - 30) * n9) * 1.2 + (double)this.z - (double)this.m.z);
        this.rot(nArray, nArray3, this.x - this.m.x, this.z - this.m.z, this.xz, 4);
        int n10 = (int)((double)this.m.crgrnd[0] / 1.5);
        int n11 = (int)((double)this.m.crgrnd[1] / 1.5);
        int n12 = (int)((double)this.m.crgrnd[2] / 1.5);
        for (n7 = 0; n7 < 4; ++n7) {
            nArray2[n7] = this.m.ground;
        }
        if (this.t.ncx != 0 || this.t.ncz != 0) {
            n7 = (this.x - this.t.sx) / 3000;
            if (n7 > this.t.ncx) {
                n7 = this.t.ncx;
            }
            if (n7 < 0) {
                n7 = 0;
            }
            if ((n6 = (this.z - this.t.sz) / 3000) > this.t.ncz) {
                n6 = this.t.ncz;
            }
            if (n6 < 0) {
                n6 = 0;
            }
            for (n5 = this.t.sect[n7][n6].length - 1; n5 >= 0; --n5) {
                n4 = this.t.sect[n7][n6][n5];
                n3 = 0;
                for (n2 = 0; n2 < 4; ++n2) {
                    if (Math.abs(this.t.zy[n4]) == 90 || Math.abs(this.t.xy[n4]) == 90 || this.t.rady[n4] == 801 || Math.abs(nArray[n2] - (this.t.x[n4] - this.m.x)) >= this.t.radx[n4] || Math.abs(nArray3[n2] - (this.t.z[n4] - this.m.z)) >= this.t.radz[n4] || this.t.decor[n4] && this.m.resdown == 2) continue;
                    ++n3;
                }
                if (n3 <= 2) continue;
                for (n2 = 0; n2 < 4; ++n2) {
                    nArray2[n2] = this.t.y[n4] - this.m.y;
                    if (this.t.zy[n4] != 0) {
                        int n13 = n2;
                        nArray2[n13] = (int)((float)nArray2[n13] + ((float)(nArray3[n2] - (this.t.z[n4] - this.m.z - this.t.radz[n4])) * this.m.sin(this.t.zy[n4]) / this.m.sin(90 - this.t.zy[n4]) - (float)this.t.radz[n4] * this.m.sin(this.t.zy[n4]) / this.m.sin(90 - this.t.zy[n4])));
                    }
                    if (this.t.xy[n4] == 0) continue;
                    int n14 = n2;
                    nArray2[n14] = (int)((float)nArray2[n14] + ((float)(nArray[n2] - (this.t.x[n4] - this.m.x - this.t.radx[n4])) * this.m.sin(this.t.xy[n4]) / this.m.sin(90 - this.t.xy[n4]) - (float)this.t.radx[n4] * this.m.sin(this.t.xy[n4]) / this.m.sin(90 - this.t.xy[n4])));
                }
                n10 = (int)((double)this.t.c[n4][0] / 1.5);
                n11 = (int)((double)this.t.c[n4][1] / 1.5);
                n12 = (int)((double)this.t.c[n4][2] / 1.5);
                break;
            }
        }
        this.rot(nArray, nArray3, this.m.cx, this.m.cz, this.m.xz, 4);
        this.rot(nArray2, nArray3, this.m.cy, this.m.cz, this.m.zy, 4);
        n7 = 1;
        n6 = 0;
        n5 = 0;
        n4 = 0;
        n3 = 0;
        for (n2 = 0; n2 < 4; ++n2) {
            nArray[n2] = this.xs(nArray[n2], nArray3[n2]);
            nArray2[n2] = this.ys(nArray2[n2], nArray3[n2]);
            if (nArray2[n2] < this.m.ih || nArray3[n2] < 10) {
                ++n6;
            }
            if (nArray2[n2] > this.m.h || nArray3[n2] < 10) {
                ++n5;
            }
            if (nArray[n2] < this.m.iw || nArray3[n2] < 10) {
                ++n4;
            }
            if (nArray[n2] <= this.m.w && nArray3[n2] >= 10) continue;
            ++n3;
        }
        if (n4 == 4 || n6 == 4 || n5 == 4 || n3 == 4) {
            n7 = 0;
        }
        if (n7 != 0) {
            for (n2 = 0; n2 < 16; ++n2) {
                if (n <= this.m.fade[n2]) continue;
                n10 = (n10 * this.m.fogd + this.m.cfade[0]) / (this.m.fogd + 1);
                n11 = (n11 * this.m.fogd + this.m.cfade[1]) / (this.m.fogd + 1);
                n12 = (n12 * this.m.fogd + this.m.cfade[2]) / (this.m.fogd + 1);
            }
            graphics2D.setColor(new Color(n10, n11, n12));
            graphics2D.fillPolygon(nArray, nArray2, 4);
        }
    }

    public void fixit(Graphics2D graphics2D) {
        int n;
        int n2;
        Object object;
        int n3;
        if (this.fcnt == 1) {
            for (n3 = 0; n3 < this.npl; ++n3) {
                this.p[n3].hsb[0] = 0.57f;
                this.p[n3].hsb[2] = 0.8f;
                this.p[n3].hsb[1] = 0.8f;
                object = Color.getHSBColor(this.p[n3].hsb[0], this.p[n3].hsb[1], this.p[n3].hsb[2]);
                int n4 = (int)((float)((Color)object).getRed() + (float)((Color)object).getRed() * ((float)this.m.snap[0] / 100.0f));
                if (n4 > 255) {
                    n4 = 255;
                }
                if (n4 < 0) {
                    n4 = 0;
                }
                if ((n2 = (int)((float)((Color)object).getGreen() + (float)((Color)object).getGreen() * ((float)this.m.snap[1] / 100.0f))) > 255) {
                    n2 = 255;
                }
                if (n2 < 0) {
                    n2 = 0;
                }
                if ((n = (int)((float)((Color)object).getBlue() + (float)((Color)object).getBlue() * ((float)this.m.snap[2] / 100.0f))) > 255) {
                    n = 255;
                }
                if (n < 0) {
                    n = 0;
                }
                Color.RGBtoHSB(n4, n2, n, this.p[n3].hsb);
                this.p[n3].flx = 1;
            }
        }
        if (this.fcnt == 2) {
            for (n3 = 0; n3 < this.npl; ++n3) {
                this.p[n3].flx = 1;
            }
        }
        if (this.fcnt == 4) {
            for (n3 = 0; n3 < this.npl; ++n3) {
                this.p[n3].flx = 3;
            }
        }
        if ((this.fcnt == 1 || this.fcnt > 2) && this.fcnt != 9) {
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            int[] nArray = new int[8];
            object = new int[8];
            int[] nArray2 = new int[4];
            for (n2 = 0; n2 < 4; ++n2) {
                nArray[n2] = this.keyx[n2] + this.x - this.m.x;
                object[n2] = this.grat + this.y - this.m.y;
                nArray2[n2] = this.keyz[n2] + this.z - this.m.z;
            }
            this.rot(nArray, (int[])object, this.x - this.m.x, this.y - this.m.y, this.xy, 4);
            this.rot((int[])object, nArray2, this.y - this.m.y, this.z - this.m.y, this.zy, 4);
            this.rot(nArray, nArray2, this.x - this.m.x, this.z - this.m.z, this.xz, 4);
            this.rot(nArray, nArray2, this.m.cx, this.m.cz, this.m.xz, 4);
            this.rot((int[])object, nArray2, this.m.cy, this.m.cz, this.m.zy, 4);
            n2 = 0;
            n = 0;
            int n10 = 0;
            for (n9 = 0; n9 < 4; ++n9) {
                for (n8 = 0; n8 < 4; ++n8) {
                    if (Math.abs(nArray[n9] - nArray[n8]) > n2) {
                        n2 = Math.abs(nArray[n9] - nArray[n8]);
                    }
                    if (Math.abs((int)(object[n9] - object[n8])) > n) {
                        n = Math.abs((int)(object[n9] - object[n8]));
                    }
                    if (this.py(nArray[n9], nArray[n8], (int)object[n9], (int)object[n8]) <= n10) continue;
                    n10 = this.py(nArray[n9], nArray[n8], (int)object[n9], (int)object[n8]);
                }
            }
            if (n2 < (n10 = (int)(Math.sqrt(n10) / 1.5))) {
                n2 = n10;
            }
            if (n < n10) {
                n = n10;
            }
            n9 = this.m.cx + (int)((float)(this.x - this.m.x - this.m.cx) * this.m.cos(this.m.xz) - (float)(this.z - this.m.z - this.m.cz) * this.m.sin(this.m.xz));
            n8 = this.m.cz + (int)((float)(this.x - this.m.x - this.m.cx) * this.m.sin(this.m.xz) + (float)(this.z - this.m.z - this.m.cz) * this.m.cos(this.m.xz));
            int n11 = this.m.cy + (int)((float)(this.y - this.m.y - this.m.cy) * this.m.cos(this.m.zy) - (float)(n8 - this.m.cz) * this.m.sin(this.m.zy));
            n8 = this.m.cz + (int)((float)(this.y - this.m.y - this.m.cy) * this.m.sin(this.m.zy) + (float)(n8 - this.m.cz) * this.m.cos(this.m.zy));
            nArray[0] = this.xs((int)((double)n9 - (double)n2 / 0.8 - (double)this.m.random() * ((double)n2 / 2.4)), n8);
            object[0] = this.ys((int)((double)n11 - (double)n / 1.92 - (double)this.m.random() * ((double)n / 5.67)), n8);
            nArray[1] = this.xs((int)((double)n9 - (double)n2 / 0.8 - (double)this.m.random() * ((double)n2 / 2.4)), n8);
            object[1] = this.ys((int)((double)n11 + (double)n / 1.92 + (double)this.m.random() * ((double)n / 5.67)), n8);
            nArray[2] = this.xs((int)((double)n9 - (double)n2 / 1.92 - (double)this.m.random() * ((double)n2 / 5.67)), n8);
            object[2] = this.ys((int)((double)n11 + (double)n / 0.8 + (double)this.m.random() * ((double)n / 2.4)), n8);
            nArray[3] = this.xs((int)((double)n9 + (double)n2 / 1.92 + (double)this.m.random() * ((double)n2 / 5.67)), n8);
            object[3] = this.ys((int)((double)n11 + (double)n / 0.8 + (double)this.m.random() * ((double)n / 2.4)), n8);
            nArray[4] = this.xs((int)((double)n9 + (double)n2 / 0.8 + (double)this.m.random() * ((double)n2 / 2.4)), n8);
            object[4] = this.ys((int)((double)n11 + (double)n / 1.92 + (double)this.m.random() * ((double)n / 5.67)), n8);
            nArray[5] = this.xs((int)((double)n9 + (double)n2 / 0.8 + (double)this.m.random() * ((double)n2 / 2.4)), n8);
            object[5] = this.ys((int)((double)n11 - (double)n / 1.92 - (double)this.m.random() * ((double)n / 5.67)), n8);
            nArray[6] = this.xs((int)((double)n9 + (double)n2 / 1.92 + (double)this.m.random() * ((double)n2 / 5.67)), n8);
            object[6] = this.ys((int)((double)n11 - (double)n / 0.8 - (double)this.m.random() * ((double)n / 2.4)), n8);
            nArray[7] = this.xs((int)((double)n9 - (double)n2 / 1.92 - (double)this.m.random() * ((double)n2 / 5.67)), n8);
            object[7] = this.ys((int)((double)n11 - (double)n / 0.8 - (double)this.m.random() * ((double)n / 2.4)), n8);
            if (this.fcnt == 3) {
                this.rot(nArray, (int[])object, this.xs(n9, n8), this.ys(n11, n8), 22, 8);
            }
            if (this.fcnt == 4) {
                this.rot(nArray, (int[])object, this.xs(n9, n8), this.ys(n11, n8), 22, 8);
            }
            if (this.fcnt == 5) {
                this.rot(nArray, (int[])object, this.xs(n9, n8), this.ys(n11, n8), 0, 8);
            }
            if (this.fcnt == 6) {
                this.rot(nArray, (int[])object, this.xs(n9, n8), this.ys(n11, n8), -22, 8);
            }
            if (this.fcnt == 7) {
                this.rot(nArray, (int[])object, this.xs(n9, n8), this.ys(n11, n8), -22, 8);
            }
            if ((n7 = (int)(191.0f + 191.0f * ((float)this.m.snap[0] / 350.0f))) > 255) {
                n7 = 255;
            }
            if (n7 < 0) {
                n7 = 0;
            }
            if ((n6 = (int)(232.0f + 232.0f * ((float)this.m.snap[1] / 350.0f))) > 255) {
                n6 = 255;
            }
            if (n6 < 0) {
                n6 = 0;
            }
            if ((n5 = (int)(255.0f + 255.0f * ((float)this.m.snap[2] / 350.0f))) > 255) {
                n5 = 255;
            }
            if (n5 < 0) {
                n5 = 0;
            }
            graphics2D.setColor(new Color(n7, n6, n5));
            graphics2D.fillPolygon(nArray, (int[])object, 8);
            nArray[0] = this.xs((int)((float)(n9 - n2) - this.m.random() * (float)(n2 / 4)), n8);
            object[0] = this.ys((int)((double)n11 - (double)n / 2.4 - (double)this.m.random() * ((double)n / 9.6)), n8);
            nArray[1] = this.xs((int)((float)(n9 - n2) - this.m.random() * (float)(n2 / 4)), n8);
            object[1] = this.ys((int)((double)n11 + (double)n / 2.4 + (double)this.m.random() * ((double)n / 9.6)), n8);
            nArray[2] = this.xs((int)((double)n9 - (double)n2 / 2.4 - (double)this.m.random() * ((double)n2 / 9.6)), n8);
            object[2] = this.ys((int)((float)(n11 + n) + this.m.random() * (float)(n / 4)), n8);
            nArray[3] = this.xs((int)((double)n9 + (double)n2 / 2.4 + (double)this.m.random() * ((double)n2 / 9.6)), n8);
            object[3] = this.ys((int)((float)(n11 + n) + this.m.random() * (float)(n / 4)), n8);
            nArray[4] = this.xs((int)((float)(n9 + n2) + this.m.random() * (float)(n2 / 4)), n8);
            object[4] = this.ys((int)((double)n11 + (double)n / 2.4 + (double)this.m.random() * ((double)n / 9.6)), n8);
            nArray[5] = this.xs((int)((float)(n9 + n2) + this.m.random() * (float)(n2 / 4)), n8);
            object[5] = this.ys((int)((double)n11 - (double)n / 2.4 - (double)this.m.random() * ((double)n / 9.6)), n8);
            nArray[6] = this.xs((int)((double)n9 + (double)n2 / 2.4 + (double)this.m.random() * ((double)n2 / 9.6)), n8);
            object[6] = this.ys((int)((float)(n11 - n) - this.m.random() * (float)(n / 4)), n8);
            nArray[7] = this.xs((int)((double)n9 - (double)n2 / 2.4 - (double)this.m.random() * ((double)n2 / 9.6)), n8);
            object[7] = this.ys((int)((float)(n11 - n) - this.m.random() * (float)(n / 4)), n8);
            n7 = (int)(213.0f + 213.0f * ((float)this.m.snap[0] / 350.0f));
            if (n7 > 255) {
                n7 = 255;
            }
            if (n7 < 0) {
                n7 = 0;
            }
            if ((n6 = (int)(239.0f + 239.0f * ((float)this.m.snap[1] / 350.0f))) > 255) {
                n6 = 255;
            }
            if (n6 < 0) {
                n6 = 0;
            }
            if ((n5 = (int)(255.0f + 255.0f * ((float)this.m.snap[2] / 350.0f))) > 255) {
                n5 = 255;
            }
            if (n5 < 0) {
                n5 = 0;
            }
            graphics2D.setColor(new Color(n7, n6, n5));
            graphics2D.fillPolygon(nArray, (int[])object, 8);
        }
        if (this.fcnt > 7) {
            this.fcnt = 0;
            this.fix = false;
        } else {
            ++this.fcnt;
        }
    }

    public void electrify(Graphics2D graphics2D) {
        for (int i = 0; i < 4; ++i) {
            int n;
            int n2;
            if (this.elc[i] == 0) {
                this.edl[i] = (int)(380.0f - this.m.random() * 760.0f);
                this.edr[i] = (int)(380.0f - this.m.random() * 760.0f);
                this.elc[i] = 1;
            }
            int n3 = (int)((float)this.edl[i] + (190.0f - this.m.random() * 380.0f));
            int n4 = (int)((float)this.edr[i] + (190.0f - this.m.random() * 380.0f));
            int n5 = (int)(this.m.random() * 126.0f);
            int n6 = (int)(this.m.random() * 126.0f);
            int[] nArray = new int[8];
            int[] nArray2 = new int[8];
            int[] nArray3 = new int[8];
            for (n2 = 0; n2 < 8; ++n2) {
                nArray3[n2] = this.z - this.m.z;
            }
            nArray[0] = this.x - this.m.x - 504;
            nArray2[0] = this.y - this.m.y - this.edl[i] - 5 - (int)(this.m.random() * 5.0f);
            nArray[1] = this.x - this.m.x - 252 + n6;
            nArray2[1] = this.y - this.m.y - n3 - 5 - (int)(this.m.random() * 5.0f);
            nArray[2] = this.x - this.m.x + 252 - n5;
            nArray2[2] = this.y - this.m.y - n4 - 5 - (int)(this.m.random() * 5.0f);
            nArray[3] = this.x - this.m.x + 504;
            nArray2[3] = this.y - this.m.y - this.edr[i] - 5 - (int)(this.m.random() * 5.0f);
            nArray[4] = this.x - this.m.x + 504;
            nArray2[4] = this.y - this.m.y - this.edr[i] + 5 + (int)(this.m.random() * 5.0f);
            nArray[5] = this.x - this.m.x + 252 - n5;
            nArray2[5] = this.y - this.m.y - n4 + 5 + (int)(this.m.random() * 5.0f);
            nArray[6] = this.x - this.m.x - 252 + n6;
            nArray2[6] = this.y - this.m.y - n3 + 5 + (int)(this.m.random() * 5.0f);
            nArray[7] = this.x - this.m.x - 504;
            nArray2[7] = this.y - this.m.y - this.edl[i] + 5 + (int)(this.m.random() * 5.0f);
            if (this.roted) {
                this.rot(nArray, nArray3, this.x - this.m.x, this.z - this.m.z, 90, 8);
            }
            this.rot(nArray, nArray3, this.m.cx, this.m.cz, this.m.xz, 8);
            this.rot(nArray2, nArray3, this.m.cy, this.m.cz, this.m.zy, 8);
            n2 = 1;
            int n7 = 0;
            int n8 = 0;
            int n9 = 0;
            int n10 = 0;
            int[] nArray4 = new int[8];
            int[] nArray5 = new int[8];
            for (n = 0; n < 8; ++n) {
                nArray4[n] = this.xs(nArray[n], nArray3[n]);
                nArray5[n] = this.ys(nArray2[n], nArray3[n]);
                if (nArray5[n] < this.m.ih || nArray3[n] < 10) {
                    ++n7;
                }
                if (nArray5[n] > this.m.h || nArray3[n] < 10) {
                    ++n8;
                }
                if (nArray4[n] < this.m.iw || nArray3[n] < 10) {
                    ++n9;
                }
                if (nArray4[n] <= this.m.w && nArray3[n] >= 10) continue;
                ++n10;
            }
            if (n9 == 8 || n7 == 8 || n8 == 8 || n10 == 8) {
                n2 = 0;
            }
            if (n2 != 0) {
                int n11;
                int n12;
                n = (int)(160.0f + 160.0f * ((float)this.m.snap[0] / 500.0f));
                if (n > 255) {
                    n = 255;
                }
                if (n < 0) {
                    n = 0;
                }
                if ((n12 = (int)(238.0f + 238.0f * ((float)this.m.snap[1] / 500.0f))) > 255) {
                    n12 = 255;
                }
                if (n12 < 0) {
                    n12 = 0;
                }
                if ((n11 = (int)(255.0f + 255.0f * ((float)this.m.snap[2] / 500.0f))) > 255) {
                    n11 = 255;
                }
                if (n11 < 0) {
                    n11 = 0;
                }
                n = (n * 2 + 214 * (this.elc[i] - 1)) / (this.elc[i] + 1);
                n12 = (n12 * 2 + 236 * (this.elc[i] - 1)) / (this.elc[i] + 1);
                if (this.m.trk == 1) {
                    n = 255;
                    n12 = 128;
                    n11 = 0;
                }
                graphics2D.setColor(new Color(n, n12, n11));
                graphics2D.fillPolygon(nArray4, nArray5, 8);
                if (nArray3[0] < 4000) {
                    n = (int)(150.0f + 150.0f * ((float)this.m.snap[0] / 500.0f));
                    if (n > 255) {
                        n = 255;
                    }
                    if (n < 0) {
                        n = 0;
                    }
                    if ((n12 = (int)(227.0f + 227.0f * ((float)this.m.snap[1] / 500.0f))) > 255) {
                        n12 = 255;
                    }
                    if (n12 < 0) {
                        n12 = 0;
                    }
                    if ((n11 = (int)(255.0f + 255.0f * ((float)this.m.snap[2] / 500.0f))) > 255) {
                        n11 = 255;
                    }
                    if (n11 < 0) {
                        n11 = 0;
                    }
                    graphics2D.setColor(new Color(n, n12, n11));
                    graphics2D.drawPolygon(nArray4, nArray5, 8);
                }
            }
            if ((float)this.elc[i] > this.m.random() * 60.0f) {
                this.elc[i] = 0;
                continue;
            }
            int n13 = i;
            this.elc[n13] = this.elc[n13] + 1;
        }
        if (!this.roted || this.xz != 0) {
            this.xy += 11;
            if (this.xy > 360) {
                this.xy -= 360;
            }
        } else {
            this.zy += 11;
            if (this.zy > 360) {
                this.zy -= 360;
            }
        }
    }

    public void dust(int n, float f, float f2, float f3, int n2, int n3, float f4, int n4, boolean bl) {
        float f5;
        boolean bl2 = false;
        if (n4 > 5 && (n == 0 || n == 2)) {
            bl2 = true;
        }
        if (n4 < -5 && (n == 1 || n == 3)) {
            bl2 = true;
        }
        if ((f5 = (float)((Math.sqrt(n2 * n2 + n3 * n3) - 40.0) / 160.0)) > 1.0f) {
            f5 = 1.0f;
        }
        if ((double)f5 > 0.2 && !bl2) {
            ++this.ust;
            if (this.ust == 20) {
                this.ust = 0;
            }
            if (!bl) {
                float f6 = this.m.random();
                this.sx[this.ust] = (int)((f + (float)this.x * f6) / (1.0f + f6));
                this.sz[this.ust] = (int)((f3 + (float)this.z * f6) / (1.0f + f6));
                this.sy[this.ust] = (int)((f2 + (float)this.y * f6) / (1.0f + f6));
            } else {
                this.sx[this.ust] = (int)((f + (float)(this.x + n2)) / 2.0f);
                this.sz[this.ust] = (int)((f3 + (float)(this.z + n3)) / 2.0f);
                this.sy[this.ust] = (int)f2;
            }
            if (this.sy[n] > 250) {
                this.sy[n] = 250;
            }
            this.osmag[this.ust] = f4 * f5;
            this.scx[this.ust] = n2;
            this.scz[this.ust] = n3;
            this.stg[this.ust] = 1;
        }
    }

    public void pdust(int n, Graphics2D graphics2D, boolean bl) {
        if (bl) {
            this.sav[n] = (int)Math.sqrt((this.m.x + this.m.cx - this.sx[n]) * (this.m.x + this.m.cx - this.sx[n]) + (this.m.y + this.m.cy - this.sy[n]) * (this.m.y + this.m.cy - this.sy[n]) + (this.m.z - this.sz[n]) * (this.m.z - this.sz[n]));
        }
        if (bl && this.sav[n] > this.dist || !bl && this.sav[n] <= this.dist) {
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            if (this.stg[n] == 1) {
                float f;
                int n8;
                int n9;
                this.sbln[n] = 0.6f;
                n7 = 0;
                int[] nArray = new int[3];
                for (n6 = 0; n6 < 3; ++n6) {
                    nArray[n6] = (int)(255.0f + 255.0f * ((float)this.m.snap[n6] / 100.0f));
                    if (nArray[n6] > 255) {
                        nArray[n6] = 255;
                    }
                    if (nArray[n6] >= 0) continue;
                    nArray[n6] = 0;
                }
                n6 = (this.x - this.t.sx) / 3000;
                if (n6 > this.t.ncx) {
                    n6 = this.t.ncx;
                }
                if (n6 < 0) {
                    n6 = 0;
                }
                if ((n9 = (this.z - this.t.sz) / 3000) > this.t.ncz) {
                    n9 = this.t.ncz;
                }
                if (n9 < 0) {
                    n9 = 0;
                }
                for (n8 = 0; n8 < this.t.sect[n6][n9].length; ++n8) {
                    n5 = this.t.sect[n6][n9][n8];
                    if (Math.abs(this.t.zy[n5]) == 90 || Math.abs(this.t.xy[n5]) == 90 || Math.abs(this.sx[n] - this.t.x[n5]) >= this.t.radx[n5] || Math.abs(this.sz[n] - this.t.z[n5]) >= this.t.radz[n5]) continue;
                    if (this.t.skd[n5] == 0) {
                        this.sbln[n] = 0.2f;
                    }
                    if (this.t.skd[n5] == 1) {
                        this.sbln[n] = 0.4f;
                    }
                    if (this.t.skd[n5] == 2) {
                        this.sbln[n] = 0.45f;
                    }
                    for (n4 = 0; n4 < 3; ++n4) {
                        this.srgb[n][n4] = (this.t.c[n5][n4] + nArray[n4]) / 2;
                    }
                    n7 = 1;
                }
                if (n7 == 0) {
                    for (n8 = 0; n8 < 3; ++n8) {
                        this.srgb[n][n8] = (this.m.crgrnd[n8] + nArray[n8]) / 2;
                    }
                }
                if ((f = (float)(0.1 + (double)this.m.random())) > 1.0f) {
                    f = 1.0f;
                }
                this.scx[n] = (int)((float)this.scx[n] * f);
                this.scz[n] = (int)((float)this.scx[n] * f);
                for (n5 = 0; n5 < 8; ++n5) {
                    this.smag[n][n5] = this.osmag[n] * this.m.random() * 50.0f;
                }
                for (n5 = 0; n5 < 8; ++n5) {
                    n4 = n5 - 1;
                    if (n4 == -1) {
                        n4 = 7;
                    }
                    if ((n3 = n5 + 1) == 8) {
                        n3 = 0;
                    }
                    this.smag[n][n5] = ((this.smag[n][n4] + this.smag[n][n3]) / 2.0f + this.smag[n][n5]) / 2.0f;
                }
                this.smag[n][6] = this.smag[n][7];
            }
            n7 = this.m.cx + (int)((float)(this.sx[n] - this.m.x - this.m.cx) * this.m.cos(this.m.xz) - (float)(this.sz[n] - this.m.z - this.m.cz) * this.m.sin(this.m.xz));
            int n10 = this.m.cz + (int)((float)(this.sx[n] - this.m.x - this.m.cx) * this.m.sin(this.m.xz) + (float)(this.sz[n] - this.m.z - this.m.cz) * this.m.cos(this.m.xz));
            n6 = this.m.cy + (int)(((float)(this.sy[n] - this.m.y - this.m.cy) - this.smag[n][7]) * this.m.cos(this.m.zy) - (float)(n10 - this.m.cz) * this.m.sin(this.m.zy));
            n10 = this.m.cz + (int)(((float)(this.sy[n] - this.m.y - this.m.cy) - this.smag[n][7]) * this.m.sin(this.m.zy) + (float)(n10 - this.m.cz) * this.m.cos(this.m.zy));
            int n11 = n;
            this.sx[n11] = this.sx[n11] + this.scx[n] / (this.stg[n] + 1);
            int n12 = n;
            this.sz[n12] = this.sz[n12] + this.scz[n] / (this.stg[n] + 1);
            int[] nArray = new int[8];
            int[] nArray2 = new int[8];
            nArray[0] = this.xs((int)((float)n7 + this.smag[n][0] * 0.9238f * 1.5f), n10);
            nArray2[0] = this.ys((int)((float)n6 + this.smag[n][0] * 0.3826f * 1.5f), n10);
            nArray[1] = this.xs((int)((float)n7 + this.smag[n][1] * 0.9238f * 1.5f), n10);
            nArray2[1] = this.ys((int)((float)n6 - this.smag[n][1] * 0.3826f * 1.5f), n10);
            nArray[2] = this.xs((int)((float)n7 + this.smag[n][2] * 0.3826f), n10);
            nArray2[2] = this.ys((int)((float)n6 - this.smag[n][2] * 0.9238f), n10);
            nArray[3] = this.xs((int)((float)n7 - this.smag[n][3] * 0.3826f), n10);
            nArray2[3] = this.ys((int)((float)n6 - this.smag[n][3] * 0.9238f), n10);
            nArray[4] = this.xs((int)((float)n7 - this.smag[n][4] * 0.9238f * 1.5f), n10);
            nArray2[4] = this.ys((int)((float)n6 - this.smag[n][4] * 0.3826f * 1.5f), n10);
            nArray[5] = this.xs((int)((float)n7 - this.smag[n][5] * 0.9238f * 1.5f), n10);
            nArray2[5] = this.ys((int)((float)n6 + this.smag[n][5] * 0.3826f * 1.5f), n10);
            nArray[6] = this.xs((int)((float)n7 - this.smag[n][6] * 0.3826f * 1.7f), n10);
            nArray2[6] = this.ys((int)((float)n6 + this.smag[n][6] * 0.9238f), n10);
            nArray[7] = this.xs((int)((float)n7 + this.smag[n][7] * 0.3826f * 1.7f), n10);
            nArray2[7] = this.ys((int)((float)n6 + this.smag[n][7] * 0.9238f), n10);
            n5 = 0;
            while (n5 < 7) {
                float[] fArray = this.smag[n];
                int n13 = n5++;
                fArray[n13] = fArray[n13] + (5.0f + this.m.random() * 15.0f);
            }
            this.smag[n][7] = this.smag[n][6];
            n5 = 1;
            n4 = 0;
            n3 = 0;
            int n14 = 0;
            int n15 = 0;
            for (n2 = 0; n2 < 8; ++n2) {
                if (nArray2[n2] < this.m.ih || n10 < 10) {
                    ++n4;
                }
                if (nArray2[n2] > this.m.h || n10 < 10) {
                    ++n3;
                }
                if (nArray[n2] < this.m.iw || n10 < 10) {
                    ++n14;
                }
                if (nArray[n2] <= this.m.w && n10 >= 10) continue;
                ++n15;
            }
            if (n14 == 4 || n4 == 4 || n3 == 4 || n15 == 4) {
                n5 = 0;
            }
            if (n5 != 0) {
                n2 = this.srgb[n][0];
                int n16 = this.srgb[n][1];
                int n17 = this.srgb[n][2];
                for (int i = 0; i < 16; ++i) {
                    if (this.sav[n] <= this.m.fade[i]) continue;
                    n2 = (n2 * this.m.fogd + this.m.cfade[0]) / (this.m.fogd + 1);
                    n16 = (n16 * this.m.fogd + this.m.cfade[1]) / (this.m.fogd + 1);
                    n17 = (n17 * this.m.fogd + this.m.cfade[2]) / (this.m.fogd + 1);
                }
                graphics2D.setColor(new Color(n2, n16, n17));
                float f = this.sbln[n] - (float)this.stg[n] * (this.sbln[n] / 8.0f);
                graphics2D.setComposite(AlphaComposite.getInstance(3, f));
                graphics2D.fillPolygon(nArray, nArray2, 8);
                graphics2D.setComposite(AlphaComposite.getInstance(3, 1.0f));
            }
            if (this.stg[n] == 7) {
                this.stg[n] = 0;
            } else {
                int n18 = n;
                this.stg[n18] = this.stg[n18] + 1;
            }
        }
    }

    public void sprk(float f, float f2, float f3, float f4, float f5, float f6, int n) {
        if (n != 1) {
            this.srx = (int)(f - (float)this.sprkat * this.m.sin(this.xz));
            this.sry = (int)(f2 - (float)this.sprkat * this.m.cos(this.zy) * this.m.cos(this.xy));
            this.srz = (int)(f3 + (float)this.sprkat * this.m.cos(this.xz));
            this.sprk = 1;
        } else {
            ++this.sprk;
            if (this.sprk == 4) {
                this.srx = (int)((float)this.x + f4);
                this.sry = (int)f2;
                this.srz = (int)((float)this.z + f6);
                this.sprk = 5;
            } else {
                this.srx = (int)f;
                this.sry = (int)f2;
                this.srz = (int)f3;
            }
        }
        if (n == 2) {
            this.sprk = 6;
        }
        this.rcx = f4;
        this.rcy = f5;
        this.rcz = f6;
    }

    public void dsprk(Graphics2D graphics2D, boolean bl) {
        int n;
        int n2;
        int n3;
        int n4;
        if (bl && this.sprk != 0 && (n4 = (int)(Math.sqrt(this.rcx * this.rcx + this.rcy * this.rcy + this.rcz * this.rcz) / 10.0)) > 5) {
            n3 = 0;
            if ((double)this.dist < Math.sqrt((this.m.x + this.m.cx - this.srx) * (this.m.x + this.m.cx - this.srx) + (this.m.y + this.m.cy - this.sry) * (this.m.y + this.m.cy - this.sry) + (this.m.z - this.srz) * (this.m.z - this.srz))) {
                n3 = 1;
            }
            if (n4 > 33) {
                n4 = 33;
            }
            n2 = 0;
            for (n = 0; n < 100; ++n) {
                if (this.rtg[n] == 0) {
                    this.rtg[n] = 1;
                    this.rbef[n] = n3;
                    ++n2;
                }
                if (n2 == n4) break;
            }
        }
        for (n4 = 0; n4 < 100; ++n4) {
            if (this.rtg[n4] == 0 || (!this.rbef[n4] || !bl) && (this.rbef[n4] || bl)) continue;
            if (this.rtg[n4] == 1) {
                if (this.sprk < 5) {
                    this.rx[n4] = this.srx + 3 - (int)((double)this.m.random() * 6.7);
                    this.ry[n4] = this.sry + 3 - (int)((double)this.m.random() * 6.7);
                    this.rz[n4] = this.srz + 3 - (int)((double)this.m.random() * 6.7);
                } else {
                    this.rx[n4] = this.srx + 10 - (int)(this.m.random() * 20.0f);
                    this.ry[n4] = this.sry - (int)(this.m.random() * 4.0f);
                    this.rz[n4] = this.srz + 10 - (int)(this.m.random() * 20.0f);
                }
                n3 = (int)Math.sqrt(this.rcx * this.rcx + this.rcy * this.rcy + this.rcz * this.rcz);
                float f = 0.2f + 0.4f * this.m.random();
                float f2 = this.m.random() * this.m.random() * this.m.random();
                float f3 = 1.0f;
                if (this.m.random() > this.m.random()) {
                    if (this.m.random() > this.m.random()) {
                        f3 *= -1.0f;
                    }
                    this.vrx[n4] = -((this.rcx + (float)n3 * (1.0f - this.rcx / (float)n3) * f2 * f3) * f);
                }
                if (this.m.random() > this.m.random()) {
                    if (this.m.random() > this.m.random()) {
                        f3 *= -1.0f;
                    }
                    if (this.sprk == 5) {
                        f3 = 1.0f;
                    }
                    this.vry[n4] = -((this.rcy + (float)n3 * (1.0f - this.rcy / (float)n3) * f2 * f3) * f);
                }
                if (this.m.random() > this.m.random()) {
                    if (this.m.random() > this.m.random()) {
                        f3 *= -1.0f;
                    }
                    this.vrz[n4] = -((this.rcz + (float)n3 * (1.0f - this.rcz / (float)n3) * f2 * f3) * f);
                }
            }
            int n5 = n4;
            this.rx[n5] = (int)((float)this.rx[n5] + this.vrx[n4]);
            int n6 = n4;
            this.ry[n6] = (int)((float)this.ry[n6] + this.vry[n4]);
            int n7 = n4;
            this.rz[n7] = (int)((float)this.rz[n7] + this.vrz[n4]);
            n3 = this.m.cx + (int)((float)(this.rx[n4] - this.m.x - this.m.cx) * this.m.cos(this.m.xz) - (float)(this.rz[n4] - this.m.z - this.m.cz) * this.m.sin(this.m.xz));
            n2 = this.m.cz + (int)((float)(this.rx[n4] - this.m.x - this.m.cx) * this.m.sin(this.m.xz) + (float)(this.rz[n4] - this.m.z - this.m.cz) * this.m.cos(this.m.xz));
            n = this.m.cy + (int)((float)(this.ry[n4] - this.m.y - this.m.cy) * this.m.cos(this.m.zy) - (float)(n2 - this.m.cz) * this.m.sin(this.m.zy));
            n2 = this.m.cz + (int)((float)(this.ry[n4] - this.m.y - this.m.cy) * this.m.sin(this.m.zy) + (float)(n2 - this.m.cz) * this.m.cos(this.m.zy));
            int n8 = this.m.cx + (int)(((float)(this.rx[n4] - this.m.x - this.m.cx) + this.vrx[n4]) * this.m.cos(this.m.xz) - ((float)(this.rz[n4] - this.m.z - this.m.cz) + this.vrz[n4]) * this.m.sin(this.m.xz));
            int n9 = this.m.cz + (int)(((float)(this.rx[n4] - this.m.x - this.m.cx) + this.vrx[n4]) * this.m.sin(this.m.xz) + ((float)(this.rz[n4] - this.m.z - this.m.cz) + this.vrz[n4]) * this.m.cos(this.m.xz));
            int n10 = this.m.cy + (int)(((float)(this.ry[n4] - this.m.y - this.m.cy) + this.vry[n4]) * this.m.cos(this.m.zy) - (float)(n9 - this.m.cz) * this.m.sin(this.m.zy));
            n9 = this.m.cz + (int)(((float)(this.ry[n4] - this.m.y - this.m.cy) + this.vry[n4]) * this.m.sin(this.m.zy) + (float)(n9 - this.m.cz) * this.m.cos(this.m.zy));
            int n11 = this.xs(n3, n2);
            int n12 = this.ys(n, n2);
            int n13 = this.xs(n8, n9);
            int n14 = this.ys(n10, n9);
            if (n11 < this.m.iw && n13 < this.m.iw) {
                this.rtg[n4] = 0;
            }
            if (n11 > this.m.w && n13 > this.m.w) {
                this.rtg[n4] = 0;
            }
            if (n12 < this.m.ih && n14 < this.m.ih) {
                this.rtg[n4] = 0;
            }
            if (n12 > this.m.h && n14 > this.m.h) {
                this.rtg[n4] = 0;
            }
            if (this.ry[n4] > 250) {
                this.rtg[n4] = 0;
            }
            if (this.rtg[n4] == 0) continue;
            int n15 = 255;
            int n16 = 197 - 30 * this.rtg[n4];
            int n17 = 0;
            for (int i = 0; i < 16; ++i) {
                if (n2 <= this.m.fade[i]) continue;
                n15 = (n15 * this.m.fogd + this.m.cfade[0]) / (this.m.fogd + 1);
                n16 = (n16 * this.m.fogd + this.m.cfade[1]) / (this.m.fogd + 1);
                n17 = (n17 * this.m.fogd + this.m.cfade[2]) / (this.m.fogd + 1);
            }
            graphics2D.setColor(new Color(n15, n16, n17));
            graphics2D.drawLine(n11, n12, n13, n14);
            this.vrx[n4] = this.vrx[n4] * 0.8f;
            this.vry[n4] = this.vry[n4] * 0.8f;
            this.vrz[n4] = this.vrz[n4] * 0.8f;
            if (this.rtg[n4] == 3) {
                this.rtg[n4] = 0;
                continue;
            }
            int n18 = n4;
            this.rtg[n18] = this.rtg[n18] + 1;
        }
        if (this.sprk != 0) {
            this.sprk = 0;
        }
    }

    public int xs(int n, int n2) {
        if (n2 < 50) {
            n2 = 50;
        }
        return (n2 - this.m.focus_point) * (this.m.cx - n) / n2 + n;
    }

    public int ys(int n, int n2) {
        if (n2 < 50) {
            n2 = 50;
        }
        return (n2 - this.m.focus_point) * (this.m.cy - n) / n2 + n;
    }

    public int getvalue(String string, String string2, int n) {
        int n2 = 0;
        String string3 = "";
        for (int i = string.length() + 1; i < string2.length(); ++i) {
            String string4 = "" + string2.charAt(i);
            if (string4.equals(",") || string4.equals(")")) {
                ++n2;
                ++i;
            }
            if (n2 != n) continue;
            string3 = string3 + string2.charAt(i);
        }
        return Float.valueOf(string3).intValue();
    }

    public int getpy(int n, int n2, int n3) {
        return (n - this.x) / 10 * ((n - this.x) / 10) + (n2 - this.y) / 10 * ((n2 - this.y) / 10) + (n3 - this.z) / 10 * ((n3 - this.z) / 10);
    }

    public int py(int n, int n2, int n3, int n4) {
        return (n - n2) * (n - n2) + (n3 - n4) * (n3 - n4);
    }

    public void rot(int[] nArray, int[] nArray2, int n, int n2, int n3, int n4) {
        if (n3 != 0) {
            for (int i = 0; i < n4; ++i) {
                int n5 = nArray[i];
                int n6 = nArray2[i];
                nArray[i] = n + (int)((float)(n5 - n) * this.m.cos(n3) - (float)(n6 - n2) * this.m.sin(n3));
                nArray2[i] = n2 + (int)((float)(n5 - n) * this.m.sin(n3) + (float)(n6 - n2) * this.m.cos(n3));
            }
        }
    }
}

