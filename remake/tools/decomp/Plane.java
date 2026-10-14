/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Plane {
    Medium m;
    Trackers t;
    int[] ox;
    int[] oy;
    int[] oz;
    int n;
    int[] c = new int[3];
    int[] oc = new int[3];
    float[] hsb = new float[3];
    int glass = 0;
    int gr = 0;
    int fs = 0;
    int disline = 7;
    boolean road = false;
    boolean solo = false;
    int light = 0;
    int master = 0;
    int wx = 0;
    int wz = 0;
    int wy = 0;
    float deltaf = 1.0f;
    float projf = 1.0f;
    int av = 0;
    int bfase = 0;
    boolean nocol = false;
    int chip = 0;
    float ctmag = 0.0f;
    int cxz = 0;
    int cxy = 0;
    int czy = 0;
    int[] cox = new int[3];
    int[] coz = new int[3];
    int[] coy = new int[3];
    int dx = 0;
    int dy = 0;
    int dz = 0;
    int vx = 0;
    int vy = 0;
    int vz = 0;
    int embos = 0;
    int typ = 0;
    int pa = 0;
    int pb = 0;
    int flx = 0;
    int colnum = 0;

    public Plane(Medium medium, Trackers trackers, int[] nArray, int[] nArray2, int[] nArray3, int n, int[] nArray4, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, boolean bl, int n10, boolean bl2) {
        int n11;
        this.m = medium;
        this.t = trackers;
        this.n = n;
        this.ox = new int[this.n];
        this.oz = new int[this.n];
        this.oy = new int[this.n];
        for (n11 = 0; n11 < this.n; ++n11) {
            this.ox[n11] = nArray[n11];
            this.oy[n11] = nArray3[n11];
            this.oz[n11] = nArray2[n11];
        }
        for (n11 = 0; n11 < 3; ++n11) {
            this.oc[n11] = nArray4[n11];
        }
        if (n3 == -15) {
            int n12;
            if (nArray4[0] == 211) {
                n11 = (int)(Math.random() * 40.0 - 20.0);
                n12 = (int)(Math.random() * 40.0 - 20.0);
                int n13 = 0;
                while (n13 < this.n) {
                    int n14 = n13;
                    this.ox[n14] = this.ox[n14] + n11;
                    int n15 = n13++;
                    this.oz[n15] = this.oz[n15] + n12;
                }
            }
            n11 = (int)(185.0 + Math.random() * 20.0);
            nArray4[0] = (217 + n11) / 2;
            if (nArray4[0] == 211) {
                nArray4[0] = 210;
            }
            nArray4[1] = (189 + n11) / 2;
            nArray4[2] = (132 + n11) / 2;
            for (n12 = 0; n12 < this.n; ++n12) {
                if (Math.random() > Math.random()) {
                    int n16 = n12;
                    this.ox[n16] = this.ox[n16] + (int)(8.0 * Math.random() - 4.0);
                }
                if (Math.random() > Math.random()) {
                    int n17 = n12;
                    this.oy[n17] = this.oy[n17] + (int)(8.0 * Math.random() - 4.0);
                }
                if (!(Math.random() > Math.random())) continue;
                int n18 = n12;
                this.oz[n18] = this.oz[n18] + (int)(8.0 * Math.random() - 4.0);
            }
        }
        if (nArray4[0] == nArray4[1] && nArray4[1] == nArray4[2]) {
            this.nocol = true;
        }
        if (n2 == 0) {
            for (n11 = 0; n11 < 3; ++n11) {
                this.c[n11] = (int)((float)nArray4[n11] + (float)nArray4[n11] * ((float)this.m.snap[n11] / 100.0f));
                if (this.c[n11] > 255) {
                    this.c[n11] = 255;
                }
                if (this.c[n11] >= 0) continue;
                this.c[n11] = 0;
            }
        }
        if (n2 == 1) {
            for (n11 = 0; n11 < 3; ++n11) {
                this.c[n11] = (this.m.csky[n11] * this.m.fade[0] * 2 + this.m.cfade[n11] * 3000) / (this.m.fade[0] * 2 + 3000);
            }
        }
        if (n2 == 2) {
            for (n11 = 0; n11 < 3; ++n11) {
                this.c[n11] = (int)((float)this.m.crgrnd[n11] * 0.925f);
            }
        }
        if (n2 == 3) {
            for (n11 = 0; n11 < 3; ++n11) {
                this.c[n11] = nArray4[n11];
            }
        }
        this.disline = n8;
        this.bfase = n9;
        this.glass = n2;
        Color.RGBtoHSB(this.c[0], this.c[1], this.c[2], this.hsb);
        if (n2 == 3 && this.m.trk != 2) {
            this.hsb[1] = this.hsb[1] + 0.05f;
            if (this.hsb[1] > 1.0f) {
                this.hsb[1] = 1.0f;
            }
        }
        if (!this.nocol && this.glass != 1) {
            if (this.bfase > 20 && (double)this.hsb[1] > 0.25) {
                this.hsb[1] = 0.25f;
            }
            if (this.bfase > 25 && (double)this.hsb[2] > 0.7) {
                this.hsb[2] = 0.7f;
            }
            if (this.bfase > 30 && (double)this.hsb[1] > 0.15) {
                this.hsb[1] = 0.15f;
            }
            if (this.bfase > 35 && (double)this.hsb[2] > 0.6) {
                this.hsb[2] = 0.6f;
            }
            if (this.bfase > 40) {
                this.hsb[0] = 0.075f;
            }
            if (this.bfase > 50 && (double)this.hsb[2] > 0.5) {
                this.hsb[2] = 0.5f;
            }
            if (this.bfase > 60) {
                this.hsb[0] = 0.05f;
            }
        }
        this.road = bl;
        this.light = n10;
        this.solo = bl2;
        this.gr = n3;
        this.fs = n4;
        this.wx = n5;
        this.wy = n6;
        this.wz = n7;
        this.deltafntyp();
    }

    public void deltafntyp() {
        int n = Math.abs(this.ox[2] - this.ox[1]);
        int n2 = Math.abs(this.oy[2] - this.oy[1]);
        int n3 = Math.abs(this.oz[2] - this.oz[1]);
        if (n2 <= n && n2 <= n3) {
            this.typ = 2;
        }
        if (n <= n2 && n <= n3) {
            this.typ = 1;
        }
        if (n3 <= n && n3 <= n2) {
            this.typ = 3;
        }
        this.deltaf = 1.0f;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (j == i) continue;
                this.deltaf *= (float)(Math.sqrt((this.ox[j] - this.ox[i]) * (this.ox[j] - this.ox[i]) + (this.oy[j] - this.oy[i]) * (this.oy[j] - this.oy[i]) + (this.oz[j] - this.oz[i]) * (this.oz[j] - this.oz[i])) / 100.0);
            }
        }
        this.deltaf /= 3.0f;
    }

    public void loadprojf() {
        this.projf = 1.0f;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (j == i) continue;
                this.projf *= (float)(Math.sqrt((this.ox[i] - this.ox[j]) * (this.ox[i] - this.ox[j]) + (this.oz[i] - this.oz[j]) * (this.oz[i] - this.oz[j])) / 100.0);
            }
        }
        this.projf /= 3.0f;
    }

    public void d(Graphics2D graphics2D, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl, int n9) {
        int n10;
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
        int n21;
        int n22;
        int n23;
        int n24;
        if (this.master == 1) {
            this.n = this.av > 1500 && !this.m.crs ? 12 : 20;
        }
        int[] nArray = new int[this.n];
        int[] nArray2 = new int[this.n];
        int[] nArray3 = new int[this.n];
        if (this.embos == 0) {
            int n25;
            for (n25 = 0; n25 < this.n; ++n25) {
                nArray[n25] = this.ox[n25] + n;
                nArray3[n25] = this.oy[n25] + n2;
                nArray2[n25] = this.oz[n25] + n3;
            }
            if ((this.gr == -11 || this.gr == -12 || this.gr == -13) && this.m.lastmaf == 1) {
                for (n25 = 0; n25 < this.n; ++n25) {
                    nArray[n25] = -this.ox[n25] + n;
                    nArray3[n25] = this.oy[n25] + n2;
                    nArray2[n25] = -this.oz[n25] + n3;
                }
            }
        } else {
            int n26;
            if (this.embos <= 11 && (double)this.m.random() > 0.5 && this.glass != 1) {
                for (int i = 0; i < this.n; ++i) {
                    nArray[i] = (int)((float)(this.ox[i] + n) + (15.0f - this.m.random() * 30.0f));
                    nArray3[i] = (int)((float)(this.oy[i] + n2) + (15.0f - this.m.random() * 30.0f));
                    nArray2[i] = (int)((float)(this.oz[i] + n3) + (15.0f - this.m.random() * 30.0f));
                }
                this.rot(nArray, nArray3, n, n2, n5, this.n);
                this.rot(nArray3, nArray2, n2, n3, n6, this.n);
                this.rot(nArray, nArray2, n, n3, n4, this.n);
                this.rot(nArray, nArray2, this.m.cx, this.m.cz, this.m.xz, this.n);
                this.rot(nArray3, nArray2, this.m.cy, this.m.cz, this.m.zy, this.n);
                int[] nArray4 = new int[this.n];
                int[] nArray5 = new int[this.n];
                for (n26 = 0; n26 < this.n; ++n26) {
                    nArray4[n26] = this.xs(nArray[n26], nArray2[n26]);
                    nArray5[n26] = this.ys(nArray3[n26], nArray2[n26]);
                }
                graphics2D.setColor(new Color(230, 230, 230));
                graphics2D.fillPolygon(nArray4, nArray5, this.n);
            }
            float f = 1.0f;
            if (this.embos <= 4) {
                f = 1.0f + this.m.random() / 5.0f;
            }
            if (this.embos > 4 && this.embos <= 7) {
                f = 1.0f + this.m.random() / 4.0f;
            }
            if (this.embos > 7 && this.embos <= 9) {
                f = 1.0f + this.m.random() / 3.0f;
                if ((double)this.hsb[2] > 0.7) {
                    this.hsb[2] = 0.7f;
                }
            }
            if (this.embos > 9 && this.embos <= 10) {
                f = 1.0f + this.m.random() / 2.0f;
                if ((double)this.hsb[2] > 0.6) {
                    this.hsb[2] = 0.6f;
                }
            }
            if (this.embos > 10 && this.embos <= 12) {
                f = 1.0f + this.m.random() / 1.0f;
                if ((double)this.hsb[2] > 0.5) {
                    this.hsb[2] = 0.5f;
                }
            }
            if (this.embos == 12) {
                this.chip = 1;
                this.ctmag = 2.0f;
                this.bfase = -7;
            }
            if (this.embos == 13) {
                this.hsb[1] = 0.2f;
                this.hsb[2] = 0.4f;
            }
            if (this.embos == 16) {
                this.pa = (int)(this.m.random() * (float)this.n);
                this.pb = (int)(this.m.random() * (float)this.n);
                while (this.pa == this.pb) {
                    this.pb = (int)(this.m.random() * (float)this.n);
                }
            }
            if (this.embos >= 16) {
                int n27;
                int n28 = 1;
                n26 = 1;
                for (n24 = Math.abs(n6); n24 > 270; n24 -= 360) {
                }
                if ((n24 = Math.abs(n24)) > 90) {
                    n28 = -1;
                }
                for (n27 = Math.abs(n5); n27 > 270; n27 -= 360) {
                }
                if ((n27 = Math.abs(n27)) > 90) {
                    n26 = -1;
                }
                int[] nArray6 = new int[3];
                int[] nArray7 = new int[3];
                nArray[0] = this.ox[this.pa] + n;
                nArray3[0] = this.oy[this.pa] + n2;
                nArray2[0] = this.oz[this.pa] + n3;
                nArray[1] = this.ox[this.pb] + n;
                nArray3[1] = this.oy[this.pb] + n2;
                nArray2[1] = this.oz[this.pb] + n3;
                while (Math.abs(nArray[0] - nArray[1]) > 100) {
                    if (nArray[1] > nArray[0]) {
                        nArray[1] = nArray[1] - 30;
                        continue;
                    }
                    nArray[1] = nArray[1] + 30;
                }
                while (Math.abs(nArray2[0] - nArray2[1]) > 100) {
                    if (nArray2[1] > nArray2[0]) {
                        nArray2[1] = nArray2[1] - 30;
                        continue;
                    }
                    nArray2[1] = nArray2[1] + 30;
                }
                n23 = (int)((double)(Math.abs(nArray[0] - nArray[1]) / 3) * (0.5 - (double)this.m.random()));
                int n29 = (int)((double)(Math.abs(nArray2[0] - nArray2[1]) / 3) * (0.5 - (double)this.m.random()));
                nArray[2] = (nArray[0] + nArray[1]) / 2 + n23;
                nArray2[2] = (nArray2[0] + nArray2[1]) / 2 + n29;
                n22 = (int)((double)(Math.abs(nArray[0] - nArray[1]) + Math.abs(nArray2[0] - nArray2[1])) / 1.5 * ((double)(this.m.random() / 2.0f) + 0.5));
                nArray3[2] = (nArray3[0] + nArray3[1]) / 2 - n28 * n26 * n22;
                this.rot(nArray, nArray3, n, n2, n5, 3);
                this.rot(nArray3, nArray2, n2, n3, n6, 3);
                this.rot(nArray, nArray2, n, n3, n4, 3);
                this.rot(nArray, nArray2, this.m.cx, this.m.cz, this.m.xz, 3);
                this.rot(nArray3, nArray2, this.m.cy, this.m.cz, this.m.zy, 3);
                for (n21 = 0; n21 < 3; ++n21) {
                    nArray6[n21] = this.xs(nArray[n21], nArray2[n21]);
                    nArray7[n21] = this.ys(nArray3[n21], nArray2[n21]);
                }
                n21 = (int)(255.0f + 255.0f * ((float)this.m.snap[0] / 400.0f));
                if (n21 > 255) {
                    n21 = 255;
                }
                if (n21 < 0) {
                    n21 = 0;
                }
                if ((n20 = (int)(169.0f + 169.0f * ((float)this.m.snap[1] / 300.0f))) > 255) {
                    n20 = 255;
                }
                if (n20 < 0) {
                    n20 = 0;
                }
                if ((n19 = (int)(89.0f + 89.0f * ((float)this.m.snap[2] / 200.0f))) > 255) {
                    n19 = 255;
                }
                if (n19 < 0) {
                    n19 = 0;
                }
                graphics2D.setColor(new Color(n21, n20, n19));
                graphics2D.fillPolygon(nArray6, nArray7, 3);
                nArray[0] = this.ox[this.pa] + n;
                nArray3[0] = this.oy[this.pa] + n2;
                nArray2[0] = this.oz[this.pa] + n3;
                nArray[1] = this.ox[this.pb] + n;
                nArray3[1] = this.oy[this.pb] + n2;
                nArray2[1] = this.oz[this.pb] + n3;
                while (Math.abs(nArray[0] - nArray[1]) > 100) {
                    if (nArray[1] > nArray[0]) {
                        nArray[1] = nArray[1] - 30;
                        continue;
                    }
                    nArray[1] = nArray[1] + 30;
                }
                while (Math.abs(nArray2[0] - nArray2[1]) > 100) {
                    if (nArray2[1] > nArray2[0]) {
                        nArray2[1] = nArray2[1] - 30;
                        continue;
                    }
                    nArray2[1] = nArray2[1] + 30;
                }
                nArray[2] = (nArray[0] + nArray[1]) / 2 + n23;
                nArray2[2] = (nArray2[0] + nArray2[1]) / 2 + n29;
                n22 = (int)((double)n22 * 0.8);
                nArray3[2] = (nArray3[0] + nArray3[1]) / 2 - n28 * n26 * n22;
                this.rot(nArray, nArray3, n, n2, n5, 3);
                this.rot(nArray3, nArray2, n2, n3, n6, 3);
                this.rot(nArray, nArray2, n, n3, n4, 3);
                this.rot(nArray, nArray2, this.m.cx, this.m.cz, this.m.xz, 3);
                this.rot(nArray3, nArray2, this.m.cy, this.m.cz, this.m.zy, 3);
                for (n18 = 0; n18 < 3; ++n18) {
                    nArray6[n18] = this.xs(nArray[n18], nArray2[n18]);
                    nArray7[n18] = this.ys(nArray3[n18], nArray2[n18]);
                }
                n21 = (int)(255.0f + 255.0f * ((float)this.m.snap[0] / 400.0f));
                if (n21 > 255) {
                    n21 = 255;
                }
                if (n21 < 0) {
                    n21 = 0;
                }
                if ((n20 = (int)(207.0f + 207.0f * ((float)this.m.snap[1] / 300.0f))) > 255) {
                    n20 = 255;
                }
                if (n20 < 0) {
                    n20 = 0;
                }
                if ((n19 = (int)(136.0f + 136.0f * ((float)this.m.snap[2] / 200.0f))) > 255) {
                    n19 = 255;
                }
                if (n19 < 0) {
                    n19 = 0;
                }
                graphics2D.setColor(new Color(n21, n20, n19));
                graphics2D.fillPolygon(nArray6, nArray7, 3);
            }
            for (int i = 0; i < this.n; ++i) {
                nArray[i] = this.typ == 1 ? (int)((float)this.ox[i] * f + (float)n) : this.ox[i] + n;
                nArray3[i] = this.typ == 2 ? (int)((float)this.oy[i] * f + (float)n2) : this.oy[i] + n2;
                nArray2[i] = this.typ == 3 ? (int)((float)this.oz[i] * f + (float)n3) : this.oz[i] + n3;
            }
            this.embos = this.embos != 70 ? ++this.embos : 16;
        }
        if (this.wz != 0) {
            this.rot(nArray3, nArray2, this.wy + n2, this.wz + n3, n8, this.n);
        }
        if (this.wx != 0) {
            this.rot(nArray, nArray2, this.wx + n, this.wz + n3, n7, this.n);
        }
        if (this.chip == 1 && ((double)this.m.random() > 0.6 || this.bfase == 0)) {
            this.chip = 0;
            if (this.bfase == 0 && this.nocol) {
                this.bfase = 1;
            }
        }
        if (this.chip != 0) {
            int n30;
            if (this.chip == 1) {
                this.cxz = n4;
                this.cxy = n5;
                this.czy = n6;
                int n31 = (int)(this.m.random() * (float)this.n);
                this.cox[0] = this.ox[n31];
                this.coz[0] = this.oz[n31];
                this.coy[0] = this.oy[n31];
                if (this.ctmag > 3.0f) {
                    this.ctmag = 3.0f;
                }
                if (this.ctmag < -3.0f) {
                    this.ctmag = -3.0f;
                }
                this.cox[1] = (int)((float)this.cox[0] + this.ctmag * (10.0f - this.m.random() * 20.0f));
                this.cox[2] = (int)((float)this.cox[0] + this.ctmag * (10.0f - this.m.random() * 20.0f));
                this.coy[1] = (int)((float)this.coy[0] + this.ctmag * (10.0f - this.m.random() * 20.0f));
                this.coy[2] = (int)((float)this.coy[0] + this.ctmag * (10.0f - this.m.random() * 20.0f));
                this.coz[1] = (int)((float)this.coz[0] + this.ctmag * (10.0f - this.m.random() * 20.0f));
                this.coz[2] = (int)((float)this.coz[0] + this.ctmag * (10.0f - this.m.random() * 20.0f));
                this.dx = 0;
                this.dy = 0;
                this.dz = 0;
                if (this.bfase != -7) {
                    this.vx = (int)(this.ctmag * (30.0f - this.m.random() * 60.0f));
                    this.vz = (int)(this.ctmag * (30.0f - this.m.random() * 60.0f));
                    this.vy = (int)(this.ctmag * (30.0f - this.m.random() * 60.0f));
                } else {
                    this.vx = (int)(this.ctmag * (10.0f - this.m.random() * 20.0f));
                    this.vz = (int)(this.ctmag * (10.0f - this.m.random() * 20.0f));
                    this.vy = (int)(this.ctmag * (10.0f - this.m.random() * 20.0f));
                }
                this.chip = 2;
            }
            int[] nArray8 = new int[3];
            int[] nArray9 = new int[3];
            int[] nArray10 = new int[3];
            for (n24 = 0; n24 < 3; ++n24) {
                nArray8[n24] = this.cox[n24] + n;
                nArray10[n24] = this.coy[n24] + n2;
                nArray9[n24] = this.coz[n24] + n3;
            }
            this.rot(nArray8, nArray10, n, n2, this.cxy, 3);
            this.rot(nArray10, nArray9, n2, n3, this.czy, 3);
            this.rot(nArray8, nArray9, n, n3, this.cxz, 3);
            n24 = 0;
            while (n24 < 3) {
                int n32 = n24;
                nArray8[n32] = nArray8[n32] + this.dx;
                int n33 = n24;
                nArray10[n33] = nArray10[n33] + this.dy;
                int n34 = n24++;
                nArray9[n34] = nArray9[n34] + this.dz;
            }
            this.dx += this.vx;
            this.dz += this.vz;
            this.dy += this.vy;
            this.vy += 7;
            if (nArray10[0] > this.m.ground) {
                this.chip = 19;
            }
            this.rot(nArray8, nArray9, this.m.cx, this.m.cz, this.m.xz, 3);
            this.rot(nArray10, nArray9, this.m.cy, this.m.cz, this.m.zy, 3);
            int[] nArray11 = new int[3];
            int[] nArray12 = new int[3];
            for (n30 = 0; n30 < 3; ++n30) {
                nArray11[n30] = this.xs(nArray8[n30], nArray9[n30]);
                nArray12[n30] = this.ys(nArray10[n30], nArray9[n30]);
            }
            n30 = (int)(this.m.random() * 3.0f);
            if (this.bfase != -7) {
                if (n30 == 0) {
                    graphics2D.setColor(new Color(this.c[0], this.c[1], this.c[2]).darker());
                }
                if (n30 == 1) {
                    graphics2D.setColor(new Color(this.c[0], this.c[1], this.c[2]));
                }
                if (n30 == 2) {
                    graphics2D.setColor(new Color(this.c[0], this.c[1], this.c[2]).brighter());
                }
            } else {
                graphics2D.setColor(Color.getHSBColor(this.hsb[0], this.hsb[1], this.hsb[2]));
            }
            graphics2D.fillPolygon(nArray11, nArray12, 3);
            ++this.chip;
            if (this.chip == 20) {
                this.chip = 0;
            }
        }
        this.rot(nArray, nArray3, n, n2, n5, this.n);
        this.rot(nArray3, nArray2, n2, n3, n6, this.n);
        this.rot(nArray, nArray2, n, n3, n4, this.n);
        if ((n5 != 0 || n6 != 0 || n4 != 0) && this.m.trk != 2) {
            this.projf = 1.0f;
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    if (j == i) continue;
                    this.projf *= (float)(Math.sqrt((nArray[i] - nArray[j]) * (nArray[i] - nArray[j]) + (nArray2[i] - nArray2[j]) * (nArray2[i] - nArray2[j])) / 100.0);
                }
            }
            this.projf /= 3.0f;
        }
        this.rot(nArray, nArray2, this.m.cx, this.m.cz, this.m.xz, this.n);
        boolean bl2 = false;
        int[] nArray13 = new int[this.n];
        int[] nArray14 = new int[this.n];
        int n35 = 500;
        for (n17 = 0; n17 < this.n; ++n17) {
            nArray13[n17] = this.xs(nArray[n17], nArray2[n17]);
            nArray14[n17] = this.ys(nArray3[n17], nArray2[n17]);
        }
        n17 = 0;
        int n36 = 1;
        for (n16 = 0; n16 < this.n; ++n16) {
            for (n23 = n16; n23 < this.n; ++n23) {
                if (n16 == n23 || Math.abs(nArray13[n16] - nArray13[n23]) - Math.abs(nArray14[n16] - nArray14[n23]) >= n35) continue;
                n36 = n16;
                n17 = n23;
                n35 = Math.abs(nArray13[n16] - nArray13[n23]) - Math.abs(nArray14[n16] - nArray14[n23]);
            }
        }
        if (nArray14[n17] < nArray14[n36]) {
            n16 = n17;
            n17 = n36;
            n36 = n16;
        }
        if (this.spy(nArray[n17], nArray2[n17]) > this.spy(nArray[n36], nArray2[n36])) {
            bl2 = true;
            n16 = 0;
            for (n23 = 0; n23 < this.n; ++n23) {
                if (nArray2[n23] < 50 && nArray3[n23] > this.m.cy) {
                    bl2 = false;
                    continue;
                }
                if (nArray3[n23] != nArray3[0]) continue;
                ++n16;
            }
            if (n16 == this.n && nArray3[0] > this.m.cy) {
                bl2 = false;
            }
        }
        this.rot(nArray3, nArray2, this.m.cy, this.m.cz, this.m.zy, this.n);
        n16 = 1;
        int[] nArray15 = new int[this.n];
        int[] nArray16 = new int[this.n];
        n22 = 0;
        n21 = 0;
        n20 = 0;
        n19 = 0;
        n18 = 0;
        for (n15 = 0; n15 < this.n; ++n15) {
            nArray15[n15] = this.xs(nArray[n15], nArray2[n15]);
            nArray16[n15] = this.ys(nArray3[n15], nArray2[n15]);
            if (nArray16[n15] < this.m.ih || nArray2[n15] < 10) {
                ++n22;
            }
            if (nArray16[n15] > this.m.h || nArray2[n15] < 10) {
                ++n21;
            }
            if (nArray15[n15] < this.m.iw || nArray2[n15] < 10) {
                ++n20;
            }
            if (nArray15[n15] > this.m.w || nArray2[n15] < 10) {
                ++n19;
            }
            if (nArray2[n15] >= 10) continue;
            ++n18;
        }
        if (n20 == this.n || n22 == this.n || n21 == this.n || n19 == this.n) {
            n16 = 0;
        }
        if (!(this.m.trk != 1 && this.m.trk != 4 || n20 == 0 && n22 == 0 && n21 == 0 && n19 == 0)) {
            n16 = 0;
        }
        if (this.m.trk == 3 && n18 != 0) {
            n16 = 0;
        }
        if (n18 != 0) {
            bl = true;
        }
        if (n16 != 0 && n9 != -1) {
            n15 = 0;
            n14 = 0;
            for (n13 = 0; n13 < this.n; ++n13) {
                for (n12 = n13; n12 < this.n; ++n12) {
                    if (n13 == n12) continue;
                    if (Math.abs(nArray15[n13] - nArray15[n12]) > n15) {
                        n15 = Math.abs(nArray15[n13] - nArray15[n12]);
                    }
                    if (Math.abs(nArray16[n13] - nArray16[n12]) <= n14) continue;
                    n14 = Math.abs(nArray16[n13] - nArray16[n12]);
                }
            }
            if (n15 == 0 || n14 == 0) {
                n16 = 0;
            } else if (n15 < 3 && n14 < 3 && (n9 / n15 > 15 && n9 / n14 > 15 || bl) && (!this.m.lightson || this.light == 0)) {
                n16 = 0;
            }
        }
        if (n16 != 0) {
            int n37;
            n15 = 1;
            n14 = this.gr;
            if (n14 < 0 && n14 >= -15) {
                n14 = 0;
            }
            if (this.gr == -11) {
                n14 = -90;
            }
            if (this.gr == -12) {
                n14 = -75;
            }
            if (this.gr == -14 || this.gr == -15) {
                n14 = -50;
            }
            if (this.glass == 2) {
                n14 = 200;
            }
            if (this.fs != 0) {
                n13 = 1;
                n12 = 1;
                if (Math.abs(nArray16[0] - nArray16[1]) > Math.abs(nArray16[2] - nArray16[1])) {
                    n13 = 0;
                    n12 = 2;
                } else {
                    n13 = 2;
                    n12 = 0;
                    n15 *= -1;
                }
                if (nArray16[1] > nArray16[n13]) {
                    n15 *= -1;
                }
                if (nArray15[1] > nArray15[n12]) {
                    n15 *= -1;
                }
                if (this.fs != 22 && (n15 *= this.fs) == -1) {
                    n14 += 40;
                    n15 = -111;
                }
            }
            if (this.m.lightson && this.light == 2) {
                n14 -= 40;
            }
            n13 = nArray3[0];
            n12 = nArray3[0];
            n11 = nArray[0];
            n10 = nArray[0];
            int n38 = nArray2[0];
            int n39 = nArray2[0];
            for (n37 = 0; n37 < this.n; ++n37) {
                if (nArray3[n37] > n13) {
                    n13 = nArray3[n37];
                }
                if (nArray3[n37] < n12) {
                    n12 = nArray3[n37];
                }
                if (nArray[n37] > n11) {
                    n11 = nArray[n37];
                }
                if (nArray[n37] < n10) {
                    n10 = nArray[n37];
                }
                if (nArray2[n37] > n38) {
                    n38 = nArray2[n37];
                }
                if (nArray2[n37] >= n39) continue;
                n39 = nArray2[n37];
            }
            n37 = (n13 + n12) / 2;
            int n40 = (n11 + n10) / 2;
            int n41 = (n38 + n39) / 2;
            this.av = (int)Math.sqrt((this.m.cy - n37) * (this.m.cy - n37) + (this.m.cx - n40) * (this.m.cx - n40) + n41 * n41 + n14 * n14 * n14);
            if (this.m.trk == 0 && (this.av > this.m.fade[this.disline] || this.av == 0)) {
                n16 = 0;
            }
            if (n15 == -111 && this.av > 4500 && !this.road) {
                n16 = 0;
            }
            if (n15 == -111 && this.av > 1500) {
                bl = true;
            }
            if (this.av > 3000 && this.m.adv <= 900) {
                bl = true;
            }
            if (this.fs == 22 && this.av < 11200) {
                this.m.lastmaf = n15;
            }
            if (!(this.gr != -13 || this.m.lastcheck && n9 == -1)) {
                n16 = 0;
            }
            if (this.master == 2 && this.av > 1500 && !this.m.crs) {
                n16 = 0;
            }
            if (!(this.gr != -14 && this.gr != -15 && this.gr != -12 || this.av <= 11000 && !bl2 && n15 != -111 && this.m.resdown != 2 || this.m.trk == 2 || this.m.trk == 3)) {
                n16 = 0;
            }
            if (this.gr == -11 && this.av > 11000 && this.m.trk != 2 && this.m.trk != 3) {
                n16 = 0;
            }
            if (this.glass == 2 && (this.m.trk != 0 || this.av > 6700)) {
                n16 = 0;
            }
            if (this.flx != 0 && (double)this.m.random() > 0.3 && this.flx != 77) {
                n16 = 0;
            }
        }
        if (n16 != 0) {
            float f = (float)((double)(this.projf / this.deltaf) + 0.3);
            if (bl && !this.solo) {
                n14 = 0;
                if (f > 1.0f) {
                    if ((double)f >= 1.27) {
                        n14 = 1;
                    }
                    f = 1.0f;
                }
                if ((double)(f = n14 != 0 ? (float)((double)f * 0.89) : (float)((double)f * 0.86)) < 0.37) {
                    f = 0.37f;
                }
                if (this.gr == -9) {
                    f = 0.7f;
                }
                if (this.gr == -4) {
                    f = 0.74f;
                }
                if (this.gr != -7 && this.m.trk == 0 && bl2) {
                    f = 0.32f;
                }
                if (this.gr == -8 || this.gr == -14 || this.gr == -15) {
                    f = 1.0f;
                }
                if (this.gr == -11 || this.gr == -12) {
                    f = 0.6f;
                    if (n9 == -1) {
                        f = this.m.cpflik || this.m.nochekflk && !this.m.lastcheck ? 1.0f : 0.76f;
                    }
                }
                if (this.gr == -13 && n9 == -1) {
                    f = this.m.cpflik ? 0.0f : 0.76f;
                }
                if (this.gr == -6) {
                    f = 0.62f;
                }
                if (this.gr == -5) {
                    f = 0.55f;
                }
            } else {
                if (f > 1.0f) {
                    f = 1.0f;
                }
                if ((double)f < 0.6 || bl2) {
                    f = 0.6f;
                }
            }
            Color color = Color.getHSBColor(this.hsb[0], this.hsb[1], this.hsb[2] * f);
            if (this.m.trk == 1) {
                float[] fArray = new float[3];
                Color.RGBtoHSB(this.oc[0], this.oc[1], this.oc[2], fArray);
                fArray[0] = 0.15f;
                fArray[1] = 0.3f;
                color = Color.getHSBColor(fArray[0], fArray[1], fArray[2] * f + 0.0f);
            }
            if (this.m.trk == 3) {
                float[] fArray = new float[3];
                Color.RGBtoHSB(this.oc[0], this.oc[1], this.oc[2], fArray);
                fArray[0] = 0.6f;
                fArray[1] = 0.14f;
                color = Color.getHSBColor(fArray[0], fArray[1], fArray[2] * f + 0.0f);
            }
            int n42 = color.getRed();
            n12 = color.getGreen();
            n11 = color.getBlue();
            if (this.m.lightson && (this.light != 0 || (this.gr == -11 || this.gr == -12) && n9 == -1)) {
                n42 = this.oc[0];
                if (n42 > 255) {
                    n42 = 255;
                }
                if (n42 < 0) {
                    n42 = 0;
                }
                if ((n12 = this.oc[1]) > 255) {
                    n12 = 255;
                }
                if (n12 < 0) {
                    n12 = 0;
                }
                if ((n11 = this.oc[2]) > 255) {
                    n11 = 255;
                }
                if (n11 < 0) {
                    n11 = 0;
                }
            }
            if (this.m.trk == 0) {
                for (n10 = 0; n10 < 16; ++n10) {
                    if (this.av <= this.m.fade[n10]) continue;
                    n42 = (n42 * this.m.fogd + this.m.cfade[0]) / (this.m.fogd + 1);
                    n12 = (n12 * this.m.fogd + this.m.cfade[1]) / (this.m.fogd + 1);
                    n11 = (n11 * this.m.fogd + this.m.cfade[2]) / (this.m.fogd + 1);
                }
            }
            graphics2D.setColor(new Color(n42, n12, n11));
            graphics2D.fillPolygon(nArray15, nArray16, this.n);
            if (this.m.trk != 0 && this.gr == -10) {
                bl = false;
            }
            if (!bl) {
                if (this.flx == 0) {
                    if (!this.solo) {
                        n42 = 0;
                        n12 = 0;
                        n11 = 0;
                        if (this.m.lightson && this.light != 0) {
                            n42 = this.oc[0] / 2;
                            if (n42 > 255) {
                                n42 = 255;
                            }
                            if (n42 < 0) {
                                n42 = 0;
                            }
                            if ((n12 = this.oc[1] / 2) > 255) {
                                n12 = 255;
                            }
                            if (n12 < 0) {
                                n12 = 0;
                            }
                            if ((n11 = this.oc[2] / 2) > 255) {
                                n11 = 255;
                            }
                            if (n11 < 0) {
                                n11 = 0;
                            }
                        }
                        if (Madness.anti == 1) {
                            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        }
                        graphics2D.setColor(new Color(n42, n12, n11));
                        graphics2D.drawPolygon(nArray15, nArray16, this.n);
                        if (Madness.anti == 1) {
                            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                        }
                    }
                } else {
                    if (this.flx == 2) {
                        graphics2D.setColor(new Color(0, 0, 0));
                        graphics2D.drawPolygon(nArray15, nArray16, this.n);
                    }
                    if (this.flx == 1) {
                        n42 = 0;
                        n12 = (int)(223.0f + 223.0f * ((float)this.m.snap[1] / 100.0f));
                        if (n12 > 255) {
                            n12 = 255;
                        }
                        if (n12 < 0) {
                            n12 = 0;
                        }
                        if ((n11 = (int)(255.0f + 255.0f * ((float)this.m.snap[2] / 100.0f))) > 255) {
                            n11 = 255;
                        }
                        if (n11 < 0) {
                            n11 = 0;
                        }
                        graphics2D.setColor(new Color(n42, n12, n11));
                        graphics2D.drawPolygon(nArray15, nArray16, this.n);
                        this.flx = 2;
                    }
                    if (this.flx == 3) {
                        n42 = 0;
                        n12 = (int)(255.0f + 255.0f * ((float)this.m.snap[1] / 100.0f));
                        if (n12 > 255) {
                            n12 = 255;
                        }
                        if (n12 < 0) {
                            n12 = 0;
                        }
                        if ((n11 = (int)(223.0f + 223.0f * ((float)this.m.snap[2] / 100.0f))) > 255) {
                            n11 = 255;
                        }
                        if (n11 < 0) {
                            n11 = 0;
                        }
                        graphics2D.setColor(new Color(n42, n12, n11));
                        graphics2D.drawPolygon(nArray15, nArray16, this.n);
                        this.flx = 2;
                    }
                    if (this.flx == 77) {
                        graphics2D.setColor(new Color(16, 198, 255));
                        graphics2D.drawPolygon(nArray15, nArray16, this.n);
                        this.flx = 0;
                    }
                }
            } else if (this.road && this.av <= 3000 && this.m.trk == 0 && this.m.fade[0] > 4000) {
                if ((n42 -= 10) < 0) {
                    n42 = 0;
                }
                if ((n12 -= 10) < 0) {
                    n12 = 0;
                }
                if ((n11 -= 10) < 0) {
                    n11 = 0;
                }
                graphics2D.setColor(new Color(n42, n12, n11));
                graphics2D.drawPolygon(nArray15, nArray16, this.n);
            }
            if (this.gr == -10) {
                if (this.m.trk == 0) {
                    n42 = this.c[0];
                    n12 = this.c[1];
                    n11 = this.c[2];
                    if (n9 == -1 && this.m.cpflik) {
                        if ((n42 = (int)((double)n42 * 1.6)) > 255) {
                            n42 = 255;
                        }
                        if ((n12 = (int)((double)n12 * 1.6)) > 255) {
                            n12 = 255;
                        }
                        if ((n11 = (int)((double)n11 * 1.6)) > 255) {
                            n11 = 255;
                        }
                    }
                    for (n10 = 0; n10 < 16; ++n10) {
                        if (this.av <= this.m.fade[n10]) continue;
                        n42 = (n42 * this.m.fogd + this.m.cfade[0]) / (this.m.fogd + 1);
                        n12 = (n12 * this.m.fogd + this.m.cfade[1]) / (this.m.fogd + 1);
                        n11 = (n11 * this.m.fogd + this.m.cfade[2]) / (this.m.fogd + 1);
                    }
                    graphics2D.setColor(new Color(n42, n12, n11));
                    graphics2D.drawPolygon(nArray15, nArray16, this.n);
                } else if (this.m.cpflik && this.m.hit == 5000) {
                    n12 = (int)(Math.random() * 115.0);
                    n42 = n12 * 2 - 54;
                    if (n42 < 0) {
                        n42 = 0;
                    }
                    if (n42 > 255) {
                        n42 = 255;
                    }
                    if ((n11 = 202 + n12 * 2) < 0) {
                        n11 = 0;
                    }
                    if (n11 > 255) {
                        n11 = 255;
                    }
                    if ((n12 += 101) < 0) {
                        n12 = 0;
                    }
                    if (n12 > 255) {
                        n12 = 255;
                    }
                    graphics2D.setColor(new Color(n42, n12, n11));
                    graphics2D.drawPolygon(nArray15, nArray16, this.n);
                }
            }
            if (this.gr == -18 && this.m.trk == 0) {
                n42 = this.c[0];
                n12 = this.c[1];
                n11 = this.c[2];
                if (this.m.cpflik && this.m.elecr >= 0.0f) {
                    n42 = (int)(25.5f * this.m.elecr);
                    if (n42 > 255) {
                        n42 = 255;
                    }
                    if ((n12 = (int)(128.0f + 12.8f * this.m.elecr)) > 255) {
                        n12 = 255;
                    }
                    n11 = 255;
                }
                for (n10 = 0; n10 < 16; ++n10) {
                    if (this.av <= this.m.fade[n10]) continue;
                    n42 = (n42 * this.m.fogd + this.m.cfade[0]) / (this.m.fogd + 1);
                    n12 = (n12 * this.m.fogd + this.m.cfade[1]) / (this.m.fogd + 1);
                    n11 = (n11 * this.m.fogd + this.m.cfade[2]) / (this.m.fogd + 1);
                }
                graphics2D.setColor(new Color(n42, n12, n11));
                graphics2D.drawPolygon(nArray15, nArray16, this.n);
            }
        }
    }

    public void s(Graphics2D graphics2D, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        int n14;
        int[] nArray = new int[this.n];
        int[] nArray2 = new int[this.n];
        int[] nArray3 = new int[this.n];
        for (n14 = 0; n14 < this.n; ++n14) {
            nArray[n14] = this.ox[n14] + n;
            nArray3[n14] = this.oy[n14] + n2;
            nArray2[n14] = this.oz[n14] + n3;
        }
        this.rot(nArray, nArray3, n, n2, n5, this.n);
        this.rot(nArray3, nArray2, n2, n3, n6, this.n);
        this.rot(nArray, nArray2, n, n3, n4, this.n);
        n14 = (int)((double)this.m.crgrnd[0] / 1.5);
        int n15 = (int)((double)this.m.crgrnd[1] / 1.5);
        int n16 = (int)((double)this.m.crgrnd[2] / 1.5);
        for (n13 = 0; n13 < this.n; ++n13) {
            nArray3[n13] = this.m.ground;
        }
        if (n7 == 0) {
            int n17;
            int n18;
            n13 = 0;
            int n19 = 0;
            int n20 = 0;
            n12 = 0;
            for (n11 = 0; n11 < this.n; ++n11) {
                n10 = 0;
                n9 = 0;
                n8 = 0;
                n18 = 0;
                for (n17 = 0; n17 < this.n; ++n17) {
                    if (nArray[n11] >= nArray[n17]) {
                        ++n10;
                    }
                    if (nArray[n11] <= nArray[n17]) {
                        ++n9;
                    }
                    if (nArray2[n11] >= nArray2[n17]) {
                        ++n8;
                    }
                    if (nArray2[n11] > nArray2[n17]) continue;
                    ++n18;
                }
                if (n10 == this.n) {
                    n13 = nArray[n11];
                }
                if (n9 == this.n) {
                    n19 = nArray[n11];
                }
                if (n8 == this.n) {
                    n20 = nArray2[n11];
                }
                if (n18 != this.n) continue;
                n12 = nArray2[n11];
            }
            n11 = (n13 + n19) / 2;
            n10 = (n20 + n12) / 2;
            n9 = (n11 - this.t.sx + this.m.x) / 3000;
            if (n9 > this.t.ncx) {
                n9 = this.t.ncx;
            }
            if (n9 < 0) {
                n9 = 0;
            }
            if ((n8 = (n10 - this.t.sz + this.m.z) / 3000) > this.t.ncz) {
                n8 = this.t.ncz;
            }
            if (n8 < 0) {
                n8 = 0;
            }
            for (n18 = this.t.sect[n9][n8].length - 1; n18 >= 0; --n18) {
                n17 = this.t.sect[n9][n8][n18];
                int n21 = 0;
                if (!(Math.abs(this.t.zy[n17]) == 90 || Math.abs(this.t.xy[n17]) == 90 || this.t.rady[n17] == 801 || Math.abs(n11 - (this.t.x[n17] - this.m.x)) >= this.t.radx[n17] || Math.abs(n10 - (this.t.z[n17] - this.m.z)) >= this.t.radz[n17] || this.t.decor[n17] && this.m.resdown == 2)) {
                    ++n21;
                }
                if (n21 == 0) continue;
                for (int i = 0; i < this.n; ++i) {
                    nArray3[i] = this.t.y[n17] - this.m.y;
                    if (this.t.zy[n17] != 0) {
                        int n22 = i;
                        nArray3[n22] = (int)((float)nArray3[n22] + ((float)(nArray2[i] - (this.t.z[n17] - this.m.z - this.t.radz[n17])) * this.m.sin(this.t.zy[n17]) / this.m.sin(90 - this.t.zy[n17]) - (float)this.t.radz[n17] * this.m.sin(this.t.zy[n17]) / this.m.sin(90 - this.t.zy[n17])));
                    }
                    if (this.t.xy[n17] == 0) continue;
                    int n23 = i;
                    nArray3[n23] = (int)((float)nArray3[n23] + ((float)(nArray[i] - (this.t.x[n17] - this.m.x - this.t.radx[n17])) * this.m.sin(this.t.xy[n17]) / this.m.sin(90 - this.t.xy[n17]) - (float)this.t.radx[n17] * this.m.sin(this.t.xy[n17]) / this.m.sin(90 - this.t.xy[n17])));
                }
                n14 = (int)((double)this.t.c[n17][0] / 1.5);
                n15 = (int)((double)this.t.c[n17][1] / 1.5);
                n16 = (int)((double)this.t.c[n17][2] / 1.5);
                break;
            }
        }
        n13 = 1;
        int[] nArray4 = new int[this.n];
        int[] nArray5 = new int[this.n];
        if (n7 == 2) {
            n14 = 87;
            n15 = 85;
            n16 = 57;
        } else {
            for (n12 = 0; n12 < this.m.nsp; ++n12) {
                for (n11 = 0; n11 < this.n; ++n11) {
                    if (Math.abs(nArray[n11] - this.m.spx[n12]) >= this.m.sprad[n12] || Math.abs(nArray2[n11] - this.m.spz[n12]) >= this.m.sprad[n12]) continue;
                    n13 = 0;
                }
            }
        }
        if (n13 != 0) {
            this.rot(nArray, nArray2, this.m.cx, this.m.cz, this.m.xz, this.n);
            this.rot(nArray3, nArray2, this.m.cy, this.m.cz, this.m.zy, this.n);
            n12 = 0;
            n11 = 0;
            n10 = 0;
            n9 = 0;
            for (n8 = 0; n8 < this.n; ++n8) {
                nArray4[n8] = this.xs(nArray[n8], nArray2[n8]);
                nArray5[n8] = this.ys(nArray3[n8], nArray2[n8]);
                if (nArray5[n8] < this.m.ih || nArray2[n8] < 10) {
                    ++n12;
                }
                if (nArray5[n8] > this.m.h || nArray2[n8] < 10) {
                    ++n11;
                }
                if (nArray4[n8] < this.m.iw || nArray2[n8] < 10) {
                    ++n10;
                }
                if (nArray4[n8] <= this.m.w && nArray2[n8] >= 10) continue;
                ++n9;
            }
            if (n10 == this.n || n12 == this.n || n11 == this.n || n9 == this.n) {
                n13 = 0;
            }
        }
        if (n13 != 0) {
            for (n12 = 0; n12 < 16; ++n12) {
                if (this.av <= this.m.fade[n12]) continue;
                n14 = (n14 * this.m.fogd + this.m.cfade[0]) / (this.m.fogd + 1);
                n15 = (n15 * this.m.fogd + this.m.cfade[1]) / (this.m.fogd + 1);
                n16 = (n16 * this.m.fogd + this.m.cfade[2]) / (this.m.fogd + 1);
            }
            graphics2D.setColor(new Color(n14, n15, n16));
            graphics2D.fillPolygon(nArray4, nArray5, this.n);
        }
    }

    public int xs(int n, int n2) {
        if (n2 < this.m.cz) {
            n2 = this.m.cz;
        }
        return (n2 - this.m.focus_point) * (this.m.cx - n) / n2 + n;
    }

    public int ys(int n, int n2) {
        if (n2 < this.m.cz) {
            n2 = this.m.cz;
        }
        return (n2 - this.m.focus_point) * (this.m.cy - n) / n2 + n;
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

    public int spy(int n, int n2) {
        return (int)Math.sqrt((n - this.m.cx) * (n - this.m.cx) + n2 * n2);
    }
}

