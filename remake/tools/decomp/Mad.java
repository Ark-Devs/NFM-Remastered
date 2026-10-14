/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;

public class Mad {
    Medium m;
    Record rpd;
    xtGraphics xt;
    int cn = 0;
    int im = 0;
    int mxz = 0;
    int cxz = 0;
    CarDefine cd;
    boolean[] dominate = new boolean[8];
    boolean[] caught = new boolean[8];
    int pzy = 0;
    int pxy = 0;
    float speed = 0.0f;
    float forca = 0.0f;
    float[] scy = new float[4];
    float[] scz = new float[4];
    float[] scx = new float[4];
    float drag = 0.5f;
    boolean mtouch = false;
    boolean wtouch = false;
    int cntouch = 0;
    boolean capsized = false;
    int txz = 0;
    int fxz = 0;
    int pmlt = 1;
    int nmlt = 1;
    int dcnt = 0;
    int skid = 0;
    boolean pushed = false;
    boolean gtouch = false;
    boolean pl = false;
    boolean pr = false;
    boolean pd = false;
    boolean pu = false;
    int loop = 0;
    float ucomp = 0.0f;
    float dcomp = 0.0f;
    float lcomp = 0.0f;
    float rcomp = 0.0f;
    int lxz = 0;
    int travxy = 0;
    int travzy = 0;
    int travxz = 0;
    int trcnt = 0;
    int capcnt = 0;
    int srfcnt = 0;
    boolean rtab = false;
    boolean ftab = false;
    boolean btab = false;
    boolean surfer = false;
    float powerup = 0.0f;
    int xtpower = 0;
    float tilt = 0.0f;
    int[][] crank = new int[4][4];
    int[][] lcrank = new int[4][4];
    int squash = 0;
    int nbsq = 0;
    int hitmag = 0;
    int cntdest = 0;
    boolean dest = false;
    boolean newcar = false;
    int pan = 0;
    int pcleared = 0;
    int clear = 0;
    int nlaps = 0;
    int focus = -1;
    float power = 75.0f;
    int missedcp = 0;
    int lastcolido = 0;
    int point = 0;
    boolean nofocus = false;
    int rpdcatch = 0;
    int newedcar = 0;
    int fixes = -1;
    int shakedam = 0;
    int outshakedam = 0;
    boolean colidim = false;

    public Mad(CarDefine carDefine, Medium medium, Record record, xtGraphics xtGraphics2, int n) {
        this.cd = carDefine;
        this.m = medium;
        this.rpd = record;
        this.xt = xtGraphics2;
        this.im = n;
    }

    public void reseto(int n, ContO contO, CheckPoints checkPoints) {
        int n2;
        this.cn = n;
        for (n2 = 0; n2 < 8; ++n2) {
            this.dominate[n2] = false;
            this.caught[n2] = false;
        }
        this.mxz = 0;
        this.cxz = 0;
        this.pzy = 0;
        this.pxy = 0;
        this.speed = 0.0f;
        for (n2 = 0; n2 < 4; ++n2) {
            this.scy[n2] = 0.0f;
            this.scx[n2] = 0.0f;
            this.scz[n2] = 0.0f;
        }
        this.forca = ((float)Math.sqrt(contO.keyz[0] * contO.keyz[0] + contO.keyx[0] * contO.keyx[0]) + (float)Math.sqrt(contO.keyz[1] * contO.keyz[1] + contO.keyx[1] * contO.keyx[1]) + (float)Math.sqrt(contO.keyz[2] * contO.keyz[2] + contO.keyx[2] * contO.keyx[2]) + (float)Math.sqrt(contO.keyz[3] * contO.keyz[3] + contO.keyx[3] * contO.keyx[3])) / 10000.0f * (float)((double)this.cd.bounce[this.cn] - 0.3);
        this.mtouch = false;
        this.wtouch = false;
        this.txz = 0;
        this.fxz = 0;
        this.pmlt = 1;
        this.nmlt = 1;
        this.dcnt = 0;
        this.skid = 0;
        this.pushed = false;
        this.gtouch = false;
        this.pl = false;
        this.pr = false;
        this.pd = false;
        this.pu = false;
        this.loop = 0;
        this.ucomp = 0.0f;
        this.dcomp = 0.0f;
        this.lcomp = 0.0f;
        this.rcomp = 0.0f;
        this.lxz = 0;
        this.travxy = 0;
        this.travzy = 0;
        this.travxz = 0;
        this.rtab = false;
        this.ftab = false;
        this.btab = false;
        this.powerup = 0.0f;
        this.xtpower = 0;
        this.trcnt = 0;
        this.capcnt = 0;
        this.tilt = 0.0f;
        for (n2 = 0; n2 < 4; ++n2) {
            for (int i = 0; i < 4; ++i) {
                this.crank[n2][i] = 0;
                this.lcrank[n2][i] = 0;
            }
        }
        this.pan = 0;
        this.pcleared = checkPoints.pcs;
        this.clear = 0;
        this.nlaps = 0;
        this.focus = -1;
        this.missedcp = 0;
        this.nofocus = false;
        this.power = 98.0f;
        this.lastcolido = 0;
        checkPoints.dested[this.im] = 0;
        this.squash = 0;
        this.nbsq = 0;
        this.hitmag = 0;
        this.cntdest = 0;
        this.dest = false;
        this.newcar = false;
        if (this.im == this.xt.im) {
            this.m.checkpoint = -1;
            this.m.lastcheck = false;
        }
        this.rpdcatch = 0;
        this.newedcar = 0;
        this.fixes = -1;
        if (checkPoints.nfix == 1) {
            this.fixes = 4;
        }
        if (checkPoints.nfix == 2) {
            this.fixes = 3;
        }
        if (checkPoints.nfix == 3) {
            this.fixes = 2;
        }
        if (checkPoints.nfix == 4) {
            this.fixes = 1;
        }
    }

    public void drive(Control control, ContO contO, Trackers trackers, CheckPoints checkPoints) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        float f;
        int n7;
        int n8;
        int n9;
        int n10;
        float f2;
        int n11;
        int n12;
        int n13 = 1;
        int n14 = 1;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        this.capsized = false;
        for (n12 = Math.abs(this.pzy); n12 > 270; n12 -= 360) {
        }
        if ((n12 = Math.abs(n12)) > 90) {
            bl = true;
        }
        boolean bl4 = false;
        for (n11 = Math.abs(this.pxy); n11 > 270; n11 -= 360) {
        }
        if ((n11 = Math.abs(n11)) > 90) {
            bl4 = true;
            n14 = -1;
        }
        int n15 = contO.grat;
        if (bl) {
            if (bl4) {
                bl4 = false;
                bl2 = true;
            } else {
                bl4 = true;
                this.capsized = true;
            }
            n13 = -1;
        } else if (bl4) {
            this.capsized = true;
        }
        if (this.capsized) {
            n15 = this.cd.flipy[this.cn] + this.squash;
        }
        control.zyinv = bl;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        if (this.mtouch) {
            this.loop = 0;
        }
        if (this.wtouch) {
            if (this.loop == 2 || this.loop == -1) {
                this.loop = -1;
                if (control.left) {
                    this.pl = true;
                }
                if (control.right) {
                    this.pr = true;
                }
                if (control.up) {
                    this.pu = true;
                }
                if (control.down) {
                    this.pd = true;
                }
            }
            this.ucomp = 0.0f;
            this.dcomp = 0.0f;
            this.lcomp = 0.0f;
            this.rcomp = 0.0f;
        }
        if (control.handb) {
            if (!this.pushed) {
                if (!this.wtouch) {
                    if (this.loop == 0) {
                        this.loop = 1;
                    }
                } else if (this.gtouch) {
                    this.pushed = true;
                }
            }
        } else {
            this.pushed = false;
        }
        if (this.loop == 1) {
            f2 = (this.scy[0] + this.scy[1] + this.scy[2] + this.scy[3]) / 4.0f;
            for (n10 = 0; n10 < 4; ++n10) {
                this.scy[n10] = f2;
            }
            this.loop = 2;
        }
        if (!this.dest) {
            if (this.loop == 2) {
                if (control.up) {
                    if (this.ucomp == 0.0f) {
                        this.ucomp = 10.0f + (this.scy[0] + 50.0f) / 20.0f;
                        if (this.ucomp < 5.0f) {
                            this.ucomp = 5.0f;
                        }
                        if (this.ucomp > 10.0f) {
                            this.ucomp = 10.0f;
                        }
                        this.ucomp *= this.cd.airs[this.cn];
                    }
                    if (this.ucomp < 20.0f) {
                        this.ucomp = (float)((double)this.ucomp + 0.5 * (double)this.cd.airs[this.cn]);
                    }
                    f3 = (float)(-this.cd.airc[this.cn]) * this.m.sin(contO.xz) * (float)n14;
                    f4 = (float)this.cd.airc[this.cn] * this.m.cos(contO.xz) * (float)n14;
                } else if (this.ucomp != 0.0f && this.ucomp > -2.0f) {
                    this.ucomp = (float)((double)this.ucomp - 0.5 * (double)this.cd.airs[this.cn]);
                }
                if (control.down) {
                    if (this.dcomp == 0.0f) {
                        this.dcomp = 10.0f + (this.scy[0] + 50.0f) / 20.0f;
                        if (this.dcomp < 5.0f) {
                            this.dcomp = 5.0f;
                        }
                        if (this.dcomp > 10.0f) {
                            this.dcomp = 10.0f;
                        }
                        this.dcomp *= this.cd.airs[this.cn];
                    }
                    if (this.dcomp < 20.0f) {
                        this.dcomp = (float)((double)this.dcomp + 0.5 * (double)this.cd.airs[this.cn]);
                    }
                    f5 = -this.cd.airc[this.cn];
                } else if (this.dcomp != 0.0f && this.ucomp > -2.0f) {
                    this.dcomp = (float)((double)this.dcomp - 0.5 * (double)this.cd.airs[this.cn]);
                }
                if (control.left) {
                    if (this.lcomp == 0.0f) {
                        this.lcomp = 5.0f;
                    }
                    if (this.lcomp < 20.0f) {
                        this.lcomp += 2.0f * this.cd.airs[this.cn];
                    }
                    f3 = (float)(-this.cd.airc[this.cn]) * this.m.cos(contO.xz) * (float)n13;
                    f4 = (float)(-this.cd.airc[this.cn]) * this.m.sin(contO.xz) * (float)n13;
                } else if (this.lcomp > 0.0f) {
                    this.lcomp -= 2.0f * this.cd.airs[this.cn];
                }
                if (control.right) {
                    if (this.rcomp == 0.0f) {
                        this.rcomp = 5.0f;
                    }
                    if (this.rcomp < 20.0f) {
                        this.rcomp += 2.0f * this.cd.airs[this.cn];
                    }
                    f3 = (float)this.cd.airc[this.cn] * this.m.cos(contO.xz) * (float)n13;
                    f4 = (float)this.cd.airc[this.cn] * this.m.sin(contO.xz) * (float)n13;
                } else if (this.rcomp > 0.0f) {
                    this.rcomp -= 2.0f * this.cd.airs[this.cn];
                }
                this.pzy = (int)((float)this.pzy + (this.dcomp - this.ucomp) * this.m.cos(this.pxy));
                contO.xz = bl ? (int)((float)contO.xz + (this.dcomp - this.ucomp) * this.m.sin(this.pxy)) : (int)((float)contO.xz - (this.dcomp - this.ucomp) * this.m.sin(this.pxy));
                this.pxy = (int)((float)this.pxy + (this.rcomp - this.lcomp));
            } else {
                int n16;
                f2 = this.power;
                if (f2 < 40.0f) {
                    f2 = 40.0f;
                }
                if (control.down) {
                    if (this.speed > 0.0f) {
                        this.speed -= (float)(this.cd.handb[this.cn] / 2);
                    } else {
                        n10 = 0;
                        for (n16 = 0; n16 < 2; ++n16) {
                            if (!(this.speed <= -((float)(this.cd.swits[this.cn][n16] / 2) + f2 * (float)this.cd.swits[this.cn][n16] / 196.0f))) continue;
                            ++n10;
                        }
                        this.speed = n10 != 2 ? (this.speed -= this.cd.acelf[this.cn][n10] / 2.0f + f2 * this.cd.acelf[this.cn][n10] / 196.0f) : -((float)(this.cd.swits[this.cn][1] / 2) + f2 * (float)this.cd.swits[this.cn][1] / 196.0f);
                    }
                }
                if (control.up) {
                    if (this.speed < 0.0f) {
                        this.speed += (float)this.cd.handb[this.cn];
                    } else {
                        n10 = 0;
                        for (n16 = 0; n16 < 3; ++n16) {
                            if (!(this.speed >= (float)(this.cd.swits[this.cn][n16] / 2) + f2 * (float)this.cd.swits[this.cn][n16] / 196.0f)) continue;
                            ++n10;
                        }
                        this.speed = n10 != 3 ? (this.speed += this.cd.acelf[this.cn][n10] / 2.0f + f2 * this.cd.acelf[this.cn][n10] / 196.0f) : (float)(this.cd.swits[this.cn][2] / 2) + f2 * (float)this.cd.swits[this.cn][2] / 196.0f;
                    }
                }
                if (control.handb && Math.abs(this.speed) > (float)this.cd.handb[this.cn]) {
                    this.speed = this.speed < 0.0f ? (this.speed += (float)this.cd.handb[this.cn]) : (this.speed -= (float)this.cd.handb[this.cn]);
                }
                if (this.loop == -1 && contO.y < 100) {
                    if (control.left) {
                        if (!this.pl) {
                            if (this.lcomp == 0.0f) {
                                this.lcomp = 5.0f * this.cd.airs[this.cn];
                            }
                            if (this.lcomp < 20.0f) {
                                this.lcomp += 2.0f * this.cd.airs[this.cn];
                            }
                        }
                    } else {
                        if (this.lcomp > 0.0f) {
                            this.lcomp -= 2.0f * this.cd.airs[this.cn];
                        }
                        this.pl = false;
                    }
                    if (control.right) {
                        if (!this.pr) {
                            if (this.rcomp == 0.0f) {
                                this.rcomp = 5.0f * this.cd.airs[this.cn];
                            }
                            if (this.rcomp < 20.0f) {
                                this.rcomp += 2.0f * this.cd.airs[this.cn];
                            }
                        }
                    } else {
                        if (this.rcomp > 0.0f) {
                            this.rcomp -= 2.0f * this.cd.airs[this.cn];
                        }
                        this.pr = false;
                    }
                    if (control.up) {
                        if (!this.pu) {
                            if (this.ucomp == 0.0f) {
                                this.ucomp = 5.0f * this.cd.airs[this.cn];
                            }
                            if (this.ucomp < 20.0f) {
                                this.ucomp += 2.0f * this.cd.airs[this.cn];
                            }
                        }
                    } else {
                        if (this.ucomp > 0.0f) {
                            this.ucomp -= 2.0f * this.cd.airs[this.cn];
                        }
                        this.pu = false;
                    }
                    if (control.down) {
                        if (!this.pd) {
                            if (this.dcomp == 0.0f) {
                                this.dcomp = 5.0f * this.cd.airs[this.cn];
                            }
                            if (this.dcomp < 20.0f) {
                                this.dcomp += 2.0f * this.cd.airs[this.cn];
                            }
                        }
                    } else {
                        if (this.dcomp > 0.0f) {
                            this.dcomp -= 2.0f * this.cd.airs[this.cn];
                        }
                        this.pd = false;
                    }
                    this.pzy = (int)((float)this.pzy + (this.dcomp - this.ucomp) * this.m.cos(this.pxy));
                    contO.xz = bl ? (int)((float)contO.xz + (this.dcomp - this.ucomp) * this.m.sin(this.pxy)) : (int)((float)contO.xz - (this.dcomp - this.ucomp) * this.m.sin(this.pxy));
                    this.pxy = (int)((float)this.pxy + (this.rcomp - this.lcomp));
                }
            }
        }
        if ((f2 = 20.0f * this.speed / (154.0f * this.cd.simag[this.cn])) > 20.0f) {
            f2 = 20.0f;
        }
        contO.wzy = (int)((float)contO.wzy - f2);
        if (contO.wzy < -30) {
            contO.wzy += 30;
        }
        if (contO.wzy > 30) {
            contO.wzy -= 30;
        }
        if (control.right) {
            contO.wxz -= this.cd.turn[this.cn];
            if (contO.wxz < -36) {
                contO.wxz = -36;
            }
        }
        if (control.left) {
            contO.wxz += this.cd.turn[this.cn];
            if (contO.wxz > 36) {
                contO.wxz = 36;
            }
        }
        if (contO.wxz != 0 && !control.left && !control.right) {
            if (Math.abs(this.speed) < 10.0f) {
                if (Math.abs(contO.wxz) == 1) {
                    contO.wxz = 0;
                }
                if (contO.wxz > 0) {
                    --contO.wxz;
                }
                if (contO.wxz < 0) {
                    ++contO.wxz;
                }
            } else {
                if (Math.abs(contO.wxz) < this.cd.turn[this.cn] * 2) {
                    contO.wxz = 0;
                }
                if (contO.wxz > 0) {
                    contO.wxz -= this.cd.turn[this.cn] * 2;
                }
                if (contO.wxz < 0) {
                    contO.wxz += this.cd.turn[this.cn] * 2;
                }
            }
        }
        if ((n10 = (int)(3600.0f / (this.speed * this.speed))) < 5) {
            n10 = 5;
        }
        if (this.speed < 0.0f) {
            n10 = -n10;
        }
        if (this.wtouch) {
            if (!this.capsized) {
                this.fxz = !control.handb ? contO.wxz / (n10 * 3) : contO.wxz / n10;
                contO.xz += contO.wxz / n10;
            }
            this.wtouch = false;
            this.gtouch = false;
        } else {
            contO.xz += this.fxz;
        }
        if (this.speed > 30.0f || this.speed < -100.0f) {
            while (Math.abs(this.mxz - this.cxz) > 180) {
                if (this.cxz > this.mxz) {
                    this.cxz -= 360;
                    continue;
                }
                if (this.cxz >= this.mxz) continue;
                this.cxz += 360;
            }
            if (Math.abs(this.mxz - this.cxz) < 30) {
                this.cxz = (int)((float)this.cxz + (float)(this.mxz - this.cxz) / 4.0f);
            } else {
                if (this.cxz > this.mxz) {
                    this.cxz -= 10;
                }
                if (this.cxz < this.mxz) {
                    this.cxz += 10;
                }
            }
        }
        float[] fArray = new float[4];
        float[] fArray2 = new float[4];
        float[] fArray3 = new float[4];
        int n17 = 0;
        while (n17 < 4) {
            fArray[n17] = contO.keyx[n17] + contO.x;
            fArray3[n17] = n15 + contO.y;
            fArray2[n17] = contO.z + contO.keyz[n17];
            int n18 = n17++;
            this.scy[n18] = this.scy[n18] + 7.0f;
        }
        this.rot(fArray, fArray3, contO.x, contO.y, this.pxy, 4);
        this.rot(fArray3, fArray2, contO.y, contO.z, this.pzy, 4);
        this.rot(fArray, fArray2, contO.x, contO.z, contO.xz, 4);
        n17 = 0;
        double d = 0.0;
        int n19 = (int)((this.scx[0] + this.scx[1] + this.scx[2] + this.scx[3]) / 4.0f);
        int n20 = (int)((this.scz[0] + this.scz[1] + this.scz[2] + this.scz[3]) / 4.0f);
        for (n9 = 0; n9 < 4; ++n9) {
            if (this.scx[n9] - (float)n19 > 200.0f) {
                this.scx[n9] = 200 + n19;
            }
            if (this.scx[n9] - (float)n19 < -200.0f) {
                this.scx[n9] = n19 - 200;
            }
            if (this.scz[n9] - (float)n20 > 200.0f) {
                this.scz[n9] = 200 + n20;
            }
            if (!(this.scz[n9] - (float)n20 < -200.0f)) continue;
            this.scz[n9] = n20 - 200;
        }
        n9 = 0;
        while (n9 < 4) {
            int n21 = n9;
            fArray3[n21] = fArray3[n21] + this.scy[n9];
            int n22 = n9;
            fArray[n22] = fArray[n22] + (this.scx[0] + this.scx[1] + this.scx[2] + this.scx[3]) / 4.0f;
            int n23 = n9++;
            fArray2[n23] = fArray2[n23] + (this.scz[0] + this.scz[1] + this.scz[2] + this.scz[3]) / 4.0f;
        }
        n9 = (contO.x - trackers.sx) / 3000;
        if (n9 > trackers.ncx) {
            n9 = trackers.ncx;
        }
        if (n9 < 0) {
            n9 = 0;
        }
        if ((n8 = (contO.z - trackers.sz) / 3000) > trackers.ncz) {
            n8 = trackers.ncz;
        }
        if (n8 < 0) {
            n8 = 0;
        }
        int n24 = 1;
        for (int i = 0; i < trackers.sect[n9][n8].length; ++i) {
            n7 = trackers.sect[n9][n8][i];
            if (Math.abs(trackers.zy[n7]) == 90 || Math.abs(trackers.xy[n7]) == 90 || Math.abs(contO.x - trackers.x[n7]) >= trackers.radx[n7] || Math.abs(contO.z - trackers.z[n7]) >= trackers.radz[n7] || trackers.decor[n7] && this.m.resdown == 2 && this.xt.multion == 0) continue;
            n24 = trackers.skd[n7];
        }
        if (this.mtouch) {
            float f6 = this.cd.grip[this.cn];
            f6 -= (float)Math.abs(this.txz - contO.xz) * this.speed / 250.0f;
            if (control.handb) {
                f6 -= (float)(Math.abs(this.txz - contO.xz) * 4);
            }
            if (f6 < this.cd.grip[this.cn]) {
                if (this.skid != 2) {
                    this.skid = 1;
                }
                this.speed -= this.speed / 100.0f;
            } else if (this.skid == 1) {
                this.skid = 2;
            }
            if (n24 == 1) {
                f6 = (float)((double)f6 * 0.75);
            }
            if (n24 == 2) {
                f6 = (float)((double)f6 * 0.55);
            }
            n7 = -((int)(this.speed * this.m.sin(contO.xz) * this.m.cos(this.pzy)));
            int n25 = (int)(this.speed * this.m.cos(contO.xz) * this.m.cos(this.pzy));
            int n26 = -((int)(this.speed * this.m.sin(this.pzy)));
            if (this.capsized || this.dest || checkPoints.haltall) {
                n7 = 0;
                n25 = 0;
                n26 = 0;
                f6 = this.cd.grip[this.cn] / 5.0f;
                this.speed = this.speed > 0.0f ? (this.speed -= 2.0f) : (this.speed += 2.0f);
            }
            this.speed = Math.abs(this.speed) > this.drag ? (this.speed > 0.0f ? (this.speed -= this.drag) : (this.speed += this.drag)) : 0.0f;
            if (this.cn == 8 && f6 < 5.0f) {
                f6 = 5.0f;
            }
            if (f6 < 1.0f) {
                f6 = 1.0f;
            }
            f = 0.0f;
            float f7 = 0.0f;
            for (n6 = 0; n6 < 4; ++n6) {
                if (Math.abs(this.scx[n6] - (float)n7) > f6) {
                    if (this.scx[n6] < (float)n7) {
                        int n27 = n6;
                        this.scx[n27] = this.scx[n27] + f6;
                    } else {
                        int n28 = n6;
                        this.scx[n28] = this.scx[n28] - f6;
                    }
                } else {
                    this.scx[n6] = n7;
                }
                if (Math.abs(this.scz[n6] - (float)n25) > f6) {
                    if (this.scz[n6] < (float)n25) {
                        int n29 = n6;
                        this.scz[n29] = this.scz[n29] + f6;
                    } else {
                        int n30 = n6;
                        this.scz[n30] = this.scz[n30] - f6;
                    }
                } else {
                    this.scz[n6] = n25;
                }
                if (Math.abs(this.scy[n6] - (float)n26) > f6) {
                    if (this.scy[n6] < (float)n26) {
                        int n31 = n6;
                        this.scy[n31] = this.scy[n31] + f6;
                    } else {
                        int n32 = n6;
                        this.scy[n32] = this.scy[n32] - f6;
                    }
                } else {
                    this.scy[n6] = n26;
                }
                if (f6 < this.cd.grip[this.cn]) {
                    if (this.txz != contO.xz) {
                        ++this.dcnt;
                    } else if (this.dcnt != 0) {
                        this.dcnt = 0;
                    }
                    if ((float)this.dcnt > 40.0f * f6 / this.cd.grip[this.cn] || this.capsized) {
                        float f8 = 1.0f;
                        if (n24 != 0) {
                            f8 = 1.2f;
                        }
                        if ((double)this.m.random() > 0.65) {
                            contO.dust(n6, fArray[n6], fArray3[n6], fArray2[n6], (int)this.scx[n6], (int)this.scz[n6], f8 * this.cd.simag[this.cn], (int)this.tilt, this.capsized && this.mtouch);
                            if (this.im == this.xt.im && !this.capsized) {
                                this.xt.skid(n24, (float)Math.sqrt(this.scx[n6] * this.scx[n6] + this.scz[n6] * this.scz[n6]));
                            }
                        }
                    } else {
                        if (n24 == 1 && (double)this.m.random() > 0.8) {
                            contO.dust(n6, fArray[n6], fArray3[n6], fArray2[n6], (int)this.scx[n6], (int)this.scz[n6], 1.1f * this.cd.simag[this.cn], (int)this.tilt, this.capsized && this.mtouch);
                        }
                        if ((n24 == 2 || n24 == 3) && (double)this.m.random() > 0.6) {
                            contO.dust(n6, fArray[n6], fArray3[n6], fArray2[n6], (int)this.scx[n6], (int)this.scz[n6], 1.15f * this.cd.simag[this.cn], (int)this.tilt, this.capsized && this.mtouch);
                        }
                    }
                } else if (this.dcnt != 0) {
                    this.dcnt -= 2;
                    if (this.dcnt < 0) {
                        this.dcnt = 0;
                    }
                }
                if (n24 == 3) {
                    int n33 = (int)(this.m.random() * 4.0f);
                    this.scy[n33] = (float)((double)(-100.0f * this.m.random() * (this.speed / (float)this.cd.swits[this.cn][2])) * ((double)this.cd.bounce[this.cn] - 0.3));
                }
                if (n24 == 4) {
                    int n34 = (int)(this.m.random() * 4.0f);
                    this.scy[n34] = (float)((double)(-150.0f * this.m.random() * (this.speed / (float)this.cd.swits[this.cn][2])) * ((double)this.cd.bounce[this.cn] - 0.3));
                }
                f += this.scx[n6];
                f7 += this.scz[n6];
            }
            this.txz = contO.xz;
            n13 = f > 0.0f ? -1 : 1;
            d = (double)f7 / Math.sqrt(f * f + f7 * f7);
            this.mxz = (int)(Math.acos(d) / (Math.PI / 180) * (double)n13);
            if (this.skid == 2) {
                if (!this.capsized) {
                    f /= 4.0f;
                    f7 /= 4.0f;
                    this.speed = bl2 ? -((float)Math.sqrt(f * f + f7 * f7) * this.m.cos(this.mxz - contO.xz)) : (float)Math.sqrt(f * f + f7 * f7) * this.m.cos(this.mxz - contO.xz);
                }
                this.skid = 0;
            }
            if (this.capsized && f == 0.0f && f7 == 0.0f) {
                n24 = 0;
            }
            this.mtouch = false;
            n17 = 1;
        } else if (this.skid != 2) {
            this.skid = 2;
        }
        int n35 = 0;
        boolean[] blArray = new boolean[4];
        boolean[] blArray2 = new boolean[4];
        boolean[] blArray3 = new boolean[4];
        f = 0.0f;
        for (n5 = 0; n5 < 4; ++n5) {
            blArray2[n5] = false;
            blArray3[n5] = false;
            if (fArray3[n5] > 245.0f) {
                ++n35;
                this.wtouch = true;
                this.gtouch = true;
                if (n17 == 0 && this.scy[n5] != 7.0f) {
                    float f9 = this.scy[n5] / 333.33f;
                    if ((double)f9 > 0.3) {
                        f9 = 0.3f;
                    }
                    f9 = n24 == 0 ? (float)((double)f9 + 1.1) : (float)((double)f9 + 1.2);
                    contO.dust(n5, fArray[n5], fArray3[n5], fArray2[n5], (int)this.scx[n5], (int)this.scz[n5], f9 * this.cd.simag[this.cn], 0, this.capsized && this.mtouch);
                }
                fArray3[n5] = 250.0f;
                blArray3[n5] = true;
                f += fArray3[n5] - 250.0f;
                float f10 = Math.abs(this.m.sin(this.pxy)) + Math.abs(this.m.sin(this.pzy));
                if ((double)(f10 /= 3.0f) > 0.4) {
                    f10 = 0.4f;
                }
                if ((double)(f10 += this.cd.bounce[this.cn]) < 1.1) {
                    f10 = 1.1f;
                }
                this.regy(n5, Math.abs(this.scy[n5] * f10), contO);
                if (this.scy[n5] > 0.0f) {
                    int n36 = n5;
                    this.scy[n36] = this.scy[n36] - Math.abs(this.scy[n5] * f10);
                }
                if (this.capsized) {
                    blArray2[n5] = true;
                }
            }
            blArray[n5] = false;
        }
        if (n35 != 0) {
            f /= (float)n35;
            for (n5 = 0; n5 < 4; ++n5) {
                if (blArray3[n5]) continue;
                int n37 = n5;
                fArray3[n37] = fArray3[n37] - f;
            }
        }
        n5 = 0;
        for (n6 = 0; n6 < trackers.sect[n9][n8].length; ++n6) {
            int n38 = trackers.sect[n9][n8][n6];
            n4 = 0;
            n3 = 0;
            for (n2 = 0; n2 < 4; ++n2) {
                float f11;
                float f12;
                float f13;
                float f14;
                if (blArray2[n2] && (trackers.skd[n38] == 0 || trackers.skd[n38] == 1) && fArray[n2] > (float)(trackers.x[n38] - trackers.radx[n38]) && fArray[n2] < (float)(trackers.x[n38] + trackers.radx[n38]) && fArray2[n2] > (float)(trackers.z[n38] - trackers.radz[n38]) && fArray2[n2] < (float)(trackers.z[n38] + trackers.radz[n38])) {
                    contO.sprk(fArray[n2], fArray3[n2], fArray2[n2], this.scx[n2], this.scy[n2], this.scz[n2], 1);
                    if (this.im == this.xt.im) {
                        this.xt.gscrape((int)this.scx[n2], (int)this.scy[n2], (int)this.scz[n2]);
                    }
                }
                if (blArray[n2] || !(fArray[n2] > (float)(trackers.x[n38] - trackers.radx[n38])) || !(fArray[n2] < (float)(trackers.x[n38] + trackers.radx[n38])) || !(fArray2[n2] > (float)(trackers.z[n38] - trackers.radz[n38])) || !(fArray2[n2] < (float)(trackers.z[n38] + trackers.radz[n38])) || !(fArray3[n2] > (float)(trackers.y[n38] - trackers.rady[n38])) || !(fArray3[n2] < (float)(trackers.y[n38] + trackers.rady[n38])) || trackers.decor[n38] && this.m.resdown == 2 && this.xt.multion == 0) continue;
                if (trackers.xy[n38] == 0 && trackers.zy[n38] == 0 && trackers.y[n38] != 250 && fArray3[n2] > (float)(trackers.y[n38] - 5)) {
                    float f15;
                    ++n3;
                    this.wtouch = true;
                    this.gtouch = true;
                    if (n17 == 0 && this.scy[n2] != 7.0f) {
                        f15 = this.scy[n2] / 333.33f;
                        if ((double)f15 > 0.3) {
                            f15 = 0.3f;
                        }
                        f15 = n24 == 0 ? (float)((double)f15 + 1.1) : (float)((double)f15 + 1.2);
                        contO.dust(n2, fArray[n2], fArray3[n2], fArray2[n2], (int)this.scx[n2], (int)this.scz[n2], f15 * this.cd.simag[this.cn], 0, this.capsized && this.mtouch);
                    }
                    fArray3[n2] = trackers.y[n38];
                    if (this.capsized && (trackers.skd[n38] == 0 || trackers.skd[n38] == 1)) {
                        contO.sprk(fArray[n2], fArray3[n2], fArray2[n2], this.scx[n2], this.scy[n2], this.scz[n2], 1);
                        if (this.im == this.xt.im) {
                            this.xt.gscrape((int)this.scx[n2], (int)this.scy[n2], (int)this.scz[n2]);
                        }
                    }
                    f15 = Math.abs(this.m.sin(this.pxy)) + Math.abs(this.m.sin(this.pzy));
                    if ((double)(f15 /= 3.0f) > 0.4) {
                        f15 = 0.4f;
                    }
                    if ((double)(f15 += this.cd.bounce[this.cn]) < 1.1) {
                        f15 = 1.1f;
                    }
                    this.regy(n2, Math.abs(this.scy[n2] * f15), contO);
                    if (this.scy[n2] > 0.0f) {
                        int n39 = n2;
                        this.scy[n39] = this.scy[n39] - Math.abs(this.scy[n2] * f15);
                    }
                    blArray[n2] = true;
                }
                if (trackers.zy[n38] == -90 && fArray2[n2] < (float)(trackers.z[n38] + trackers.radz[n38]) && (this.scz[n2] < 0.0f || trackers.radz[n38] == 287)) {
                    for (int i = 0; i < 4; ++i) {
                        if (n2 == i || !(fArray2[i] >= (float)(trackers.z[n38] + trackers.radz[n38]))) continue;
                        int n40 = i;
                        fArray2[n40] = fArray2[n40] - (fArray2[n2] - (float)(trackers.z[n38] + trackers.radz[n38]));
                    }
                    fArray2[n2] = trackers.z[n38] + trackers.radz[n38];
                    if (trackers.skd[n38] != 2) {
                        int[] nArray = this.crank[0];
                        int n41 = n2;
                        nArray[n41] = nArray[n41] + 1;
                    }
                    if (trackers.skd[n38] == 5 && this.m.random() > this.m.random()) {
                        int[] nArray = this.crank[0];
                        int n42 = n2;
                        nArray[n42] = nArray[n42] + 1;
                    }
                    if (this.crank[0][n2] > 1) {
                        contO.sprk(fArray[n2], fArray3[n2], fArray2[n2], this.scx[n2], this.scy[n2], this.scz[n2], 0);
                        if (this.im == this.xt.im) {
                            this.xt.scrape((int)this.scx[n2], (int)this.scy[n2], (int)this.scz[n2]);
                        }
                    }
                    float f16 = Math.abs(this.m.cos(this.pxy)) + Math.abs(this.m.cos(this.pzy));
                    if ((double)(f16 /= 4.0f) > 0.3) {
                        f16 = 0.3f;
                    }
                    if (n17 != 0) {
                        f16 = 0.0f;
                    }
                    if ((double)(f16 = (float)((double)f16 + ((double)this.cd.bounce[this.cn] - 0.2))) < 1.1) {
                        f16 = 1.1f;
                    }
                    this.regz(n2, Math.abs(this.scz[n2] * f16 * (float)trackers.dam[n38]), contO);
                    int n43 = n2;
                    this.scz[n43] = this.scz[n43] + Math.abs(this.scz[n2] * f16);
                    this.skid = 2;
                    bl3 = true;
                    blArray[n2] = true;
                    if (!trackers.notwall[n38]) {
                        control.wall = n38;
                    }
                }
                if (trackers.zy[n38] == 90 && fArray2[n2] > (float)(trackers.z[n38] - trackers.radz[n38]) && (this.scz[n2] > 0.0f || trackers.radz[n38] == 287)) {
                    for (int i = 0; i < 4; ++i) {
                        if (n2 == i || !(fArray2[i] <= (float)(trackers.z[n38] - trackers.radz[n38]))) continue;
                        int n44 = i;
                        fArray2[n44] = fArray2[n44] - (fArray2[n2] - (float)(trackers.z[n38] - trackers.radz[n38]));
                    }
                    fArray2[n2] = trackers.z[n38] - trackers.radz[n38];
                    if (trackers.skd[n38] != 2) {
                        int[] nArray = this.crank[1];
                        int n45 = n2;
                        nArray[n45] = nArray[n45] + 1;
                    }
                    if (trackers.skd[n38] == 5 && this.m.random() > this.m.random()) {
                        int[] nArray = this.crank[1];
                        int n46 = n2;
                        nArray[n46] = nArray[n46] + 1;
                    }
                    if (this.crank[1][n2] > 1) {
                        contO.sprk(fArray[n2], fArray3[n2], fArray2[n2], this.scx[n2], this.scy[n2], this.scz[n2], 0);
                        if (this.im == this.xt.im) {
                            this.xt.scrape((int)this.scx[n2], (int)this.scy[n2], (int)this.scz[n2]);
                        }
                    }
                    float f17 = Math.abs(this.m.cos(this.pxy)) + Math.abs(this.m.cos(this.pzy));
                    if ((double)(f17 /= 4.0f) > 0.3) {
                        f17 = 0.3f;
                    }
                    if (n17 != 0) {
                        f17 = 0.0f;
                    }
                    if ((double)(f17 = (float)((double)f17 + ((double)this.cd.bounce[this.cn] - 0.2))) < 1.1) {
                        f17 = 1.1f;
                    }
                    this.regz(n2, -Math.abs(this.scz[n2] * f17 * (float)trackers.dam[n38]), contO);
                    int n47 = n2;
                    this.scz[n47] = this.scz[n47] - Math.abs(this.scz[n2] * f17);
                    this.skid = 2;
                    bl3 = true;
                    blArray[n2] = true;
                    if (!trackers.notwall[n38]) {
                        control.wall = n38;
                    }
                }
                if (trackers.xy[n38] == -90 && fArray[n2] < (float)(trackers.x[n38] + trackers.radx[n38]) && (this.scx[n2] < 0.0f || trackers.radx[n38] == 287)) {
                    for (int i = 0; i < 4; ++i) {
                        if (n2 == i || !(fArray[i] >= (float)(trackers.x[n38] + trackers.radx[n38]))) continue;
                        int n48 = i;
                        fArray[n48] = fArray[n48] - (fArray[n2] - (float)(trackers.x[n38] + trackers.radx[n38]));
                    }
                    fArray[n2] = trackers.x[n38] + trackers.radx[n38];
                    if (trackers.skd[n38] != 2) {
                        int[] nArray = this.crank[2];
                        int n49 = n2;
                        nArray[n49] = nArray[n49] + 1;
                    }
                    if (trackers.skd[n38] == 5 && this.m.random() > this.m.random()) {
                        int[] nArray = this.crank[2];
                        int n50 = n2;
                        nArray[n50] = nArray[n50] + 1;
                    }
                    if (this.crank[2][n2] > 1) {
                        contO.sprk(fArray[n2], fArray3[n2], fArray2[n2], this.scx[n2], this.scy[n2], this.scz[n2], 0);
                        if (this.im == this.xt.im) {
                            this.xt.scrape((int)this.scx[n2], (int)this.scy[n2], (int)this.scz[n2]);
                        }
                    }
                    float f18 = Math.abs(this.m.cos(this.pxy)) + Math.abs(this.m.cos(this.pzy));
                    if ((double)(f18 /= 4.0f) > 0.3) {
                        f18 = 0.3f;
                    }
                    if (n17 != 0) {
                        f18 = 0.0f;
                    }
                    if ((double)(f18 = (float)((double)f18 + ((double)this.cd.bounce[this.cn] - 0.2))) < 1.1) {
                        f18 = 1.1f;
                    }
                    this.regx(n2, Math.abs(this.scx[n2] * f18 * (float)trackers.dam[n38]), contO);
                    int n51 = n2;
                    this.scx[n51] = this.scx[n51] + Math.abs(this.scx[n2] * f18);
                    this.skid = 2;
                    bl3 = true;
                    blArray[n2] = true;
                    if (!trackers.notwall[n38]) {
                        control.wall = n38;
                    }
                }
                if (trackers.xy[n38] == 90 && fArray[n2] > (float)(trackers.x[n38] - trackers.radx[n38]) && (this.scx[n2] > 0.0f || trackers.radx[n38] == 287)) {
                    for (int i = 0; i < 4; ++i) {
                        if (n2 == i || !(fArray[i] <= (float)(trackers.x[n38] - trackers.radx[n38]))) continue;
                        int n52 = i;
                        fArray[n52] = fArray[n52] - (fArray[n2] - (float)(trackers.x[n38] - trackers.radx[n38]));
                    }
                    fArray[n2] = trackers.x[n38] - trackers.radx[n38];
                    if (trackers.skd[n38] != 2) {
                        int[] nArray = this.crank[3];
                        int n53 = n2;
                        nArray[n53] = nArray[n53] + 1;
                    }
                    if (trackers.skd[n38] == 5 && this.m.random() > this.m.random()) {
                        int[] nArray = this.crank[3];
                        int n54 = n2;
                        nArray[n54] = nArray[n54] + 1;
                    }
                    if (this.crank[3][n2] > 1) {
                        contO.sprk(fArray[n2], fArray3[n2], fArray2[n2], this.scx[n2], this.scy[n2], this.scz[n2], 0);
                        if (this.im == this.xt.im) {
                            this.xt.scrape((int)this.scx[n2], (int)this.scy[n2], (int)this.scz[n2]);
                        }
                    }
                    float f19 = Math.abs(this.m.cos(this.pxy)) + Math.abs(this.m.cos(this.pzy));
                    if ((double)(f19 /= 4.0f) > 0.3) {
                        f19 = 0.3f;
                    }
                    if (n17 != 0) {
                        f19 = 0.0f;
                    }
                    if ((double)(f19 = (float)((double)f19 + ((double)this.cd.bounce[this.cn] - 0.2))) < 1.1) {
                        f19 = 1.1f;
                    }
                    this.regx(n2, -Math.abs(this.scx[n2] * f19 * (float)trackers.dam[n38]), contO);
                    int n55 = n2;
                    this.scx[n55] = this.scx[n55] - Math.abs(this.scx[n2] * f19);
                    this.skid = 2;
                    bl3 = true;
                    blArray[n2] = true;
                    if (!trackers.notwall[n38]) {
                        control.wall = n38;
                    }
                }
                if (trackers.zy[n38] != 0 && trackers.zy[n38] != 90 && trackers.zy[n38] != -90) {
                    int n56 = 90 + trackers.zy[n38];
                    f14 = 1.0f + (float)(50 - Math.abs(trackers.zy[n38])) / 30.0f;
                    if (f14 < 1.0f) {
                        f14 = 1.0f;
                    }
                    f13 = (float)trackers.y[n38] + ((fArray3[n2] - (float)trackers.y[n38]) * this.m.cos(n56) - (fArray2[n2] - (float)trackers.z[n38]) * this.m.sin(n56));
                    f12 = (float)trackers.z[n38] + ((fArray3[n2] - (float)trackers.y[n38]) * this.m.sin(n56) + (fArray2[n2] - (float)trackers.z[n38]) * this.m.cos(n56));
                    if (f12 > (float)trackers.z[n38] && f12 < (float)(trackers.z[n38] + 200)) {
                        int n57 = n2;
                        this.scy[n57] = this.scy[n57] - (f12 - (float)trackers.z[n38]) / f14;
                        f12 = trackers.z[n38];
                    }
                    if (f12 > (float)(trackers.z[n38] - 30)) {
                        if (trackers.skd[n38] == 2) {
                            ++n4;
                        } else {
                            ++n5;
                        }
                        this.wtouch = true;
                        this.gtouch = false;
                        if (this.capsized && (trackers.skd[n38] == 0 || trackers.skd[n38] == 1)) {
                            contO.sprk(fArray[n2], fArray3[n2], fArray2[n2], this.scx[n2], this.scy[n2], this.scz[n2], 1);
                            if (this.im == this.xt.im) {
                                this.xt.gscrape((int)this.scx[n2], (int)this.scy[n2], (int)this.scz[n2]);
                            }
                        }
                        if (n17 == 0 && n24 != 0) {
                            f11 = 1.4f;
                            contO.dust(n2, fArray[n2], fArray3[n2], fArray2[n2], (int)this.scx[n2], (int)this.scz[n2], f11 * this.cd.simag[this.cn], 0, this.capsized && this.mtouch);
                        }
                    }
                    fArray3[n2] = (float)trackers.y[n38] + ((f13 - (float)trackers.y[n38]) * this.m.cos(-n56) - (f12 - (float)trackers.z[n38]) * this.m.sin(-n56));
                    fArray2[n2] = (float)trackers.z[n38] + ((f13 - (float)trackers.y[n38]) * this.m.sin(-n56) + (f12 - (float)trackers.z[n38]) * this.m.cos(-n56));
                    blArray[n2] = true;
                }
                if (trackers.xy[n38] == 0 || trackers.xy[n38] == 90 || trackers.xy[n38] == -90) continue;
                int n58 = 90 + trackers.xy[n38];
                f14 = 1.0f + (float)(50 - Math.abs(trackers.xy[n38])) / 30.0f;
                if (f14 < 1.0f) {
                    f14 = 1.0f;
                }
                f13 = (float)trackers.y[n38] + ((fArray3[n2] - (float)trackers.y[n38]) * this.m.cos(n58) - (fArray[n2] - (float)trackers.x[n38]) * this.m.sin(n58));
                f12 = (float)trackers.x[n38] + ((fArray3[n2] - (float)trackers.y[n38]) * this.m.sin(n58) + (fArray[n2] - (float)trackers.x[n38]) * this.m.cos(n58));
                if (f12 > (float)trackers.x[n38] && f12 < (float)(trackers.x[n38] + 200)) {
                    int n59 = n2;
                    this.scy[n59] = this.scy[n59] - (f12 - (float)trackers.x[n38]) / f14;
                    f12 = trackers.x[n38];
                }
                if (f12 > (float)(trackers.x[n38] - 30)) {
                    if (trackers.skd[n38] == 2) {
                        ++n4;
                    } else {
                        ++n5;
                    }
                    this.wtouch = true;
                    this.gtouch = false;
                    if (this.capsized && (trackers.skd[n38] == 0 || trackers.skd[n38] == 1)) {
                        contO.sprk(fArray[n2], fArray3[n2], fArray2[n2], this.scx[n2], this.scy[n2], this.scz[n2], 1);
                        if (this.im == this.xt.im) {
                            this.xt.gscrape((int)this.scx[n2], (int)this.scy[n2], (int)this.scz[n2]);
                        }
                    }
                    if (n17 == 0 && n24 != 0) {
                        f11 = 1.4f;
                        contO.dust(n2, fArray[n2], fArray3[n2], fArray2[n2], (int)this.scx[n2], (int)this.scz[n2], f11 * this.cd.simag[this.cn], 0, this.capsized && this.mtouch);
                    }
                }
                fArray3[n2] = (float)trackers.y[n38] + ((f13 - (float)trackers.y[n38]) * this.m.cos(-n58) - (f12 - (float)trackers.x[n38]) * this.m.sin(-n58));
                fArray[n2] = (float)trackers.x[n38] + ((f13 - (float)trackers.y[n38]) * this.m.sin(-n58) + (f12 - (float)trackers.x[n38]) * this.m.cos(-n58));
                blArray[n2] = true;
            }
            if (n4 == 4) {
                this.mtouch = true;
            }
            if (n3 != 4) continue;
            n35 = 4;
        }
        if (n5 == 4) {
            this.mtouch = true;
        }
        for (n6 = 0; n6 < 4; ++n6) {
            for (int i = 0; i < 4; ++i) {
                if (this.crank[n6][i] == this.lcrank[n6][i]) {
                    this.crank[n6][i] = 0;
                }
                this.lcrank[n6][i] = this.crank[n6][i];
            }
        }
        n6 = 0;
        int n60 = 0;
        n4 = 0;
        n3 = 0;
        if (this.scy[2] != this.scy[0]) {
            n13 = this.scy[2] < this.scy[0] ? -1 : 1;
            d = Math.sqrt((fArray2[0] - fArray2[2]) * (fArray2[0] - fArray2[2]) + (fArray3[0] - fArray3[2]) * (fArray3[0] - fArray3[2]) + (fArray[0] - fArray[2]) * (fArray[0] - fArray[2])) / (double)(Math.abs(contO.keyz[0]) + Math.abs(contO.keyz[2]));
            n6 = d >= 0.9998 ? n13 : (int)(Math.acos(d) / (Math.PI / 180) * (double)n13);
        }
        if (this.scy[3] != this.scy[1]) {
            n13 = this.scy[3] < this.scy[1] ? -1 : 1;
            d = Math.sqrt((fArray2[1] - fArray2[3]) * (fArray2[1] - fArray2[3]) + (fArray3[1] - fArray3[3]) * (fArray3[1] - fArray3[3]) + (fArray[1] - fArray[3]) * (fArray[1] - fArray[3])) / (double)(Math.abs(contO.keyz[1]) + Math.abs(contO.keyz[3]));
            n60 = d >= 0.9998 ? n13 : (int)(Math.acos(d) / (Math.PI / 180) * (double)n13);
        }
        if (this.scy[1] != this.scy[0]) {
            n13 = this.scy[1] < this.scy[0] ? -1 : 1;
            d = Math.sqrt((fArray2[0] - fArray2[1]) * (fArray2[0] - fArray2[1]) + (fArray3[0] - fArray3[1]) * (fArray3[0] - fArray3[1]) + (fArray[0] - fArray[1]) * (fArray[0] - fArray[1])) / (double)(Math.abs(contO.keyx[0]) + Math.abs(contO.keyx[1]));
            n4 = d >= 0.9998 ? n13 : (int)(Math.acos(d) / (Math.PI / 180) * (double)n13);
        }
        if (this.scy[3] != this.scy[2]) {
            n13 = this.scy[3] < this.scy[2] ? -1 : 1;
            d = Math.sqrt((fArray2[2] - fArray2[3]) * (fArray2[2] - fArray2[3]) + (fArray3[2] - fArray3[3]) * (fArray3[2] - fArray3[3]) + (fArray[2] - fArray[3]) * (fArray[2] - fArray[3])) / (double)(Math.abs(contO.keyx[2]) + Math.abs(contO.keyx[3]));
            n3 = d >= 0.9998 ? n13 : (int)(Math.acos(d) / (Math.PI / 180) * (double)n13);
        }
        if (bl3) {
            for (n2 = Math.abs(contO.xz + 45); n2 > 180; n2 -= 360) {
            }
            this.pmlt = Math.abs(n2) > 90 ? 1 : -1;
            for (n2 = Math.abs(contO.xz - 45); n2 > 180; n2 -= 360) {
            }
            this.nmlt = Math.abs(n2) > 90 ? 1 : -1;
        }
        contO.xz = (int)((float)contO.xz + this.forca * (this.scz[0] * (float)this.nmlt - this.scz[1] * (float)this.pmlt + this.scz[2] * (float)this.pmlt - this.scz[3] * (float)this.nmlt + this.scx[0] * (float)this.pmlt + this.scx[1] * (float)this.nmlt - this.scx[2] * (float)this.nmlt - this.scx[3] * (float)this.pmlt));
        if (Math.abs(n60) > Math.abs(n6)) {
            n6 = n60;
        }
        if (Math.abs(n3) > Math.abs(n4)) {
            n4 = n3;
        }
        this.pzy = !bl ? (this.pzy += n6) : (this.pzy -= n6);
        this.pxy = !bl4 ? (this.pxy += n4) : (this.pxy -= n4);
        if (n35 == 4) {
            n2 = 0;
            while (this.pzy < 360) {
                this.pzy += 360;
                contO.zy += 360;
            }
            while (this.pzy > 360) {
                this.pzy -= 360;
                contO.zy -= 360;
            }
            if (this.pzy < 190 && this.pzy > 170) {
                this.pzy = 180;
                contO.zy = 180;
                ++n2;
            }
            if (this.pzy > 350 || this.pzy < 10) {
                this.pzy = 0;
                contO.zy = 0;
                ++n2;
            }
            while (this.pxy < 360) {
                this.pxy += 360;
                contO.xy += 360;
            }
            while (this.pxy > 360) {
                this.pxy -= 360;
                contO.xy -= 360;
            }
            if (this.pxy < 190 && this.pxy > 170) {
                this.pxy = 180;
                contO.xy = 180;
                ++n2;
            }
            if (this.pxy > 350 || this.pxy < 10) {
                this.pxy = 0;
                contO.xy = 0;
                ++n2;
            }
            if (n2 == 2) {
                this.mtouch = true;
            }
        }
        if (!this.mtouch && this.wtouch) {
            if (this.cntouch == 10) {
                this.mtouch = true;
            } else {
                ++this.cntouch;
            }
        } else {
            this.cntouch = 0;
        }
        contO.y = (int)((fArray3[0] + fArray3[1] + fArray3[2] + fArray3[3]) / 4.0f - (float)n15 * this.m.cos(this.pzy) * this.m.cos(this.pxy) + f5);
        n13 = bl ? -1 : 1;
        contO.x = (int)((fArray[0] - (float)contO.keyx[0] * this.m.cos(contO.xz) + (float)(n13 * contO.keyz[0]) * this.m.sin(contO.xz) + fArray[1] - (float)contO.keyx[1] * this.m.cos(contO.xz) + (float)(n13 * contO.keyz[1]) * this.m.sin(contO.xz) + fArray[2] - (float)contO.keyx[2] * this.m.cos(contO.xz) + (float)(n13 * contO.keyz[2]) * this.m.sin(contO.xz) + fArray[3] - (float)contO.keyx[3] * this.m.cos(contO.xz) + (float)(n13 * contO.keyz[3]) * this.m.sin(contO.xz)) / 4.0f + (float)n15 * this.m.sin(this.pxy) * this.m.cos(contO.xz) - (float)n15 * this.m.sin(this.pzy) * this.m.sin(contO.xz) + f3);
        contO.z = (int)((fArray2[0] - (float)(n13 * contO.keyz[0]) * this.m.cos(contO.xz) - (float)contO.keyx[0] * this.m.sin(contO.xz) + fArray2[1] - (float)(n13 * contO.keyz[1]) * this.m.cos(contO.xz) - (float)contO.keyx[1] * this.m.sin(contO.xz) + fArray2[2] - (float)(n13 * contO.keyz[2]) * this.m.cos(contO.xz) - (float)contO.keyx[2] * this.m.sin(contO.xz) + fArray2[3] - (float)(n13 * contO.keyz[3]) * this.m.cos(contO.xz) - (float)contO.keyx[3] * this.m.sin(contO.xz)) / 4.0f + (float)n15 * this.m.sin(this.pxy) * this.m.sin(contO.xz) - (float)n15 * this.m.sin(this.pzy) * this.m.cos(contO.xz) + f4);
        if (Math.abs(this.speed) > 10.0f || !this.mtouch) {
            contO.xy = Math.abs(this.pxy - contO.xy) >= 4 ? (this.pxy > contO.xy ? (contO.xy += 2 + (this.pxy - contO.xy) / 2) : (contO.xy -= 2 + (contO.xy - this.pxy) / 2)) : this.pxy;
            contO.zy = Math.abs(this.pzy - contO.zy) >= 4 ? (this.pzy > contO.zy ? (contO.zy += 2 + (this.pzy - contO.zy) / 2) : (contO.zy -= 2 + (contO.zy - this.pzy) / 2)) : this.pzy;
        }
        if (this.wtouch && !this.capsized) {
            float f20 = (float)((double)(this.speed / (float)this.cd.swits[this.cn][2] * 14.0f) * ((double)this.cd.bounce[this.cn] - 0.4));
            this.tilt = control.left && this.tilt < f20 && this.tilt >= 0.0f ? (float)((double)this.tilt + 0.4) : (control.right && this.tilt > -f20 && this.tilt <= 0.0f ? (float)((double)this.tilt - 0.4) : ((double)Math.abs(this.tilt) > 3.0 * ((double)this.cd.bounce[this.cn] - 0.4) ? (this.tilt > 0.0f ? (float)((double)this.tilt - 3.0 * ((double)this.cd.bounce[this.cn] - 0.3)) : (float)((double)this.tilt + 3.0 * ((double)this.cd.bounce[this.cn] - 0.3))) : 0.0f));
            contO.xy = (int)((float)contO.xy + this.tilt);
            if (this.gtouch) {
                contO.y = (int)((double)contO.y - (double)this.tilt / 1.5);
            }
        } else if (this.tilt != 0.0f) {
            this.tilt = 0.0f;
        }
        if (this.wtouch && n24 == 2) {
            contO.zy += (int)((double)(this.m.random() * 6.0f * this.speed / (float)this.cd.swits[this.cn][2] - 3.0f * this.speed / (float)this.cd.swits[this.cn][2]) * ((double)this.cd.bounce[this.cn] - 0.3));
            contO.xy += (int)((double)(this.m.random() * 6.0f * this.speed / (float)this.cd.swits[this.cn][2] - 3.0f * this.speed / (float)this.cd.swits[this.cn][2]) * ((double)this.cd.bounce[this.cn] - 0.3));
        }
        if (this.wtouch && n24 == 1) {
            contO.zy += (int)((double)(this.m.random() * 4.0f * this.speed / (float)this.cd.swits[this.cn][2] - 2.0f * this.speed / (float)this.cd.swits[this.cn][2]) * ((double)this.cd.bounce[this.cn] - 0.3));
            contO.xy += (int)((double)(this.m.random() * 4.0f * this.speed / (float)this.cd.swits[this.cn][2] - 2.0f * this.speed / (float)this.cd.swits[this.cn][2]) * ((double)this.cd.bounce[this.cn] - 0.3));
        }
        if (this.hitmag >= this.cd.maxmag[this.cn] && !this.dest) {
            this.distruct(contO);
            if (this.cntdest == 7) {
                this.dest = true;
            } else {
                ++this.cntdest;
            }
            if (this.cntdest == 1) {
                this.rpd.dest[this.im] = 300;
            }
        }
        if (contO.dist == 0) {
            for (int i = 0; i < contO.npl; ++i) {
                if (contO.p[i].chip != 0) {
                    contO.p[i].chip = 0;
                }
                if (contO.p[i].embos == 0) continue;
                contO.p[i].embos = 13;
            }
        }
        int n61 = 0;
        int n62 = 0;
        int n63 = 0;
        n14 = this.nofocus ? 1 : 7;
        for (n = 0; n < checkPoints.n; ++n) {
            if (checkPoints.typ[n] > 0) {
                ++n63;
                if (checkPoints.typ[n] == 1) {
                    if (this.clear == n63 + this.nlaps * checkPoints.nsp) {
                        n14 = 1;
                    }
                    if ((float)Math.abs(contO.z - checkPoints.z[n]) < 60.0f + Math.abs(this.scz[0] + this.scz[1] + this.scz[2] + this.scz[3]) / 4.0f && Math.abs(contO.x - checkPoints.x[n]) < 700 && Math.abs(contO.y - checkPoints.y[n] + 350) < 450 && this.clear == n63 + this.nlaps * checkPoints.nsp - 1) {
                        this.clear = n63 + this.nlaps * checkPoints.nsp;
                        this.pcleared = n;
                        this.focus = -1;
                    }
                }
                if (checkPoints.typ[n] == 2) {
                    if (this.clear == n63 + this.nlaps * checkPoints.nsp) {
                        n14 = 1;
                    }
                    if ((float)Math.abs(contO.x - checkPoints.x[n]) < 60.0f + Math.abs(this.scx[0] + this.scx[1] + this.scx[2] + this.scx[3]) / 4.0f && Math.abs(contO.z - checkPoints.z[n]) < 700 && Math.abs(contO.y - checkPoints.y[n] + 350) < 450 && this.clear == n63 + this.nlaps * checkPoints.nsp - 1) {
                        this.clear = n63 + this.nlaps * checkPoints.nsp;
                        this.pcleared = n;
                        this.focus = -1;
                    }
                }
            }
            if (this.py(contO.x / 100, checkPoints.x[n] / 100, contO.z / 100, checkPoints.z[n] / 100) * n14 >= n62 && n62 != 0) continue;
            n61 = n;
            n62 = this.py(contO.x / 100, checkPoints.x[n] / 100, contO.z / 100, checkPoints.z[n] / 100) * n14;
        }
        if (this.clear == n63 + this.nlaps * checkPoints.nsp) {
            ++this.nlaps;
            if (this.xt.multion == 1 && this.im == this.xt.im) {
                if (this.xt.laptime < this.xt.fastestlap || this.xt.fastestlap == 0) {
                    this.xt.fastestlap = this.xt.laptime;
                }
                this.xt.laptime = 0;
            }
        }
        if (this.im == this.xt.im) {
            if (this.xt.multion == 1 && this.xt.starcnt == 0) {
                ++this.xt.laptime;
            }
            this.m.checkpoint = this.clear;
            while (this.m.checkpoint >= checkPoints.nsp) {
                this.m.checkpoint -= checkPoints.nsp;
            }
            if (this.clear == checkPoints.nlaps * checkPoints.nsp - 1) {
                this.m.lastcheck = true;
            }
            if (checkPoints.haltall) {
                this.m.lastcheck = false;
            }
        }
        if (this.focus == -1) {
            n61 = this.im == this.xt.im ? (n61 += 2) : ++n61;
            if (!this.nofocus) {
                n63 = this.pcleared + 1;
                if (n63 >= checkPoints.n) {
                    n63 = 0;
                }
                while (checkPoints.typ[n63] <= 0) {
                    if (++n63 < checkPoints.n) continue;
                    n63 = 0;
                }
                if (n61 > n63 && (this.clear != this.nlaps * checkPoints.nsp || n61 < this.pcleared)) {
                    this.focus = n61 = n63;
                }
            }
            if (n61 >= checkPoints.n) {
                n61 -= checkPoints.n;
            }
            if (checkPoints.typ[n61] == -3) {
                n61 = 0;
            }
            if (this.im == this.xt.im) {
                if (this.missedcp != -1) {
                    this.missedcp = -1;
                }
            } else if (this.missedcp != 0) {
                this.missedcp = 0;
            }
        } else {
            n61 = this.focus;
            if (this.im == this.xt.im) {
                if (this.missedcp == 0 && this.mtouch && Math.sqrt(this.py(contO.x / 10, checkPoints.x[this.focus] / 10, contO.z / 10, checkPoints.z[this.focus] / 10)) > 800.0) {
                    this.missedcp = 1;
                }
                if (this.missedcp == -2 && Math.sqrt(this.py(contO.x / 10, checkPoints.x[this.focus] / 10, contO.z / 10, checkPoints.z[this.focus] / 10)) < 400.0) {
                    this.missedcp = 0;
                }
                if (this.missedcp != 0 && this.mtouch && Math.sqrt(this.py(contO.x / 10, checkPoints.x[this.focus] / 10, contO.z / 10, checkPoints.z[this.focus] / 10)) < 250.0) {
                    this.missedcp = 68;
                }
            } else {
                this.missedcp = 1;
            }
            if (this.nofocus) {
                this.focus = -1;
                this.missedcp = 0;
            }
        }
        if (this.nofocus) {
            this.nofocus = false;
        }
        this.point = n61;
        if (this.fixes != 0) {
            if (this.m.noelec == 0) {
                for (n = 0; n < checkPoints.fn; ++n) {
                    if (!checkPoints.roted[n]) {
                        if (Math.abs(contO.z - checkPoints.fz[n]) >= 200 || this.py(contO.x / 100, checkPoints.fx[n] / 100, contO.y / 100, checkPoints.fy[n] / 100) >= 30) continue;
                        if (contO.dist == 0) {
                            contO.fcnt = 8;
                        } else {
                            if (this.im == this.xt.im && !contO.fix && !this.xt.mutes) {
                                this.xt.carfixed.play();
                            }
                            contO.fix = true;
                        }
                        this.rpd.fix[this.im] = 300;
                        continue;
                    }
                    if (Math.abs(contO.x - checkPoints.fx[n]) >= 200 || this.py(contO.z / 100, checkPoints.fz[n] / 100, contO.y / 100, checkPoints.fy[n] / 100) >= 30) continue;
                    if (contO.dist == 0) {
                        contO.fcnt = 8;
                    } else {
                        if (this.im == this.xt.im && !contO.fix && !this.xt.mutes) {
                            this.xt.carfixed.play();
                        }
                        contO.fix = true;
                    }
                    this.rpd.fix[this.im] = 300;
                }
            }
        } else {
            for (n = 0; n < checkPoints.fn; ++n) {
                if (this.rpy(contO.x / 100, checkPoints.fx[n] / 100, contO.y / 100, checkPoints.fy[n] / 100, contO.z / 100, checkPoints.fz[n] / 100) >= 760) continue;
                this.m.noelec = 2;
            }
        }
        if (contO.fcnt == 7 || contO.fcnt == 8) {
            this.squash = 0;
            this.nbsq = 0;
            this.hitmag = 0;
            this.cntdest = 0;
            this.dest = false;
            this.newcar = true;
            contO.fcnt = 9;
            if (this.fixes > 0) {
                --this.fixes;
            }
        }
        if (this.newedcar != 0) {
            --this.newedcar;
            if (this.newedcar == 10) {
                this.newcar = false;
            }
        }
        if (!this.mtouch) {
            if (this.trcnt != 1) {
                this.trcnt = 1;
                this.lxz = contO.xz;
            }
            if (this.loop == 2 || this.loop == -1) {
                this.travxy = (int)((float)this.travxy + (this.rcomp - this.lcomp));
                if (Math.abs(this.travxy) > 135) {
                    this.rtab = true;
                }
                this.travzy = (int)((float)this.travzy + (this.ucomp - this.dcomp));
                if (this.travzy > 135) {
                    this.ftab = true;
                }
                if (this.travzy < -135) {
                    this.btab = true;
                }
            }
            if (this.lxz != contO.xz) {
                this.travxz += this.lxz - contO.xz;
                this.lxz = contO.xz;
            }
            if (this.srfcnt < 10) {
                if (control.wall != -1) {
                    this.surfer = true;
                }
                ++this.srfcnt;
            }
        } else if (!this.dest) {
            if (!this.capsized) {
                if (this.capcnt != 0) {
                    this.capcnt = 0;
                }
                if (this.gtouch && this.trcnt != 0) {
                    if (this.trcnt == 9) {
                        this.powerup = 0.0f;
                        if (Math.abs(this.travxy) > 90) {
                            this.powerup += (float)Math.abs(this.travxy) / 24.0f;
                        } else if (this.rtab) {
                            this.powerup += 30.0f;
                        }
                        if (Math.abs(this.travzy) > 90) {
                            this.powerup += (float)Math.abs(this.travzy) / 18.0f;
                        } else {
                            if (this.ftab) {
                                this.powerup += 40.0f;
                            }
                            if (this.btab) {
                                this.powerup += 40.0f;
                            }
                        }
                        if (Math.abs(this.travxz) > 90) {
                            this.powerup += (float)Math.abs(this.travxz) / 18.0f;
                        }
                        if (this.surfer) {
                            this.powerup += 30.0f;
                        }
                        this.power += this.powerup;
                        if (this.im == this.xt.im && (int)this.powerup > this.rpd.powered && this.rpd.wasted == 0 && (this.powerup > 60.0f || checkPoints.stage == 1 || checkPoints.stage == 2)) {
                            this.rpdcatch = 30;
                            if (this.rpd.hcaught) {
                                this.rpd.powered = (int)this.powerup;
                            }
                            if (this.xt.multion == 1 && this.powerup > (float)this.xt.beststunt) {
                                this.xt.beststunt = (int)this.powerup;
                            }
                        }
                        if (this.power > 98.0f) {
                            this.power = 98.0f;
                            this.xtpower = this.powerup > 150.0f ? 200 : 100;
                        }
                    }
                    if (this.trcnt == 10) {
                        this.travxy = 0;
                        this.travzy = 0;
                        this.travxz = 0;
                        this.ftab = false;
                        this.rtab = false;
                        this.btab = false;
                        this.trcnt = 0;
                        this.srfcnt = 0;
                        this.surfer = false;
                    } else {
                        ++this.trcnt;
                    }
                }
            } else {
                if (this.trcnt != 0) {
                    this.travxy = 0;
                    this.travzy = 0;
                    this.travxz = 0;
                    this.ftab = false;
                    this.rtab = false;
                    this.btab = false;
                    this.trcnt = 0;
                    this.srfcnt = 0;
                    this.surfer = false;
                }
                if (this.capcnt == 0) {
                    n = 0;
                    for (int i = 0; i < 4; ++i) {
                        if (!(Math.abs(this.scz[i]) < 70.0f) || !(Math.abs(this.scx[i]) < 70.0f)) continue;
                        ++n;
                    }
                    if (n == 4) {
                        this.capcnt = 1;
                    }
                } else {
                    ++this.capcnt;
                    if (this.capcnt == 30) {
                        this.speed = 0.0f;
                        contO.y += this.cd.flipy[this.cn];
                        this.pxy += 180;
                        contO.xy += 180;
                        this.capcnt = 0;
                    }
                }
            }
            if (this.trcnt == 0 && this.speed != 0.0f) {
                if (this.xtpower == 0) {
                    this.power = this.power > 0.0f ? (this.power -= this.power * this.power * this.power / (float)this.cd.powerloss[this.cn]) : 0.0f;
                } else {
                    --this.xtpower;
                }
            }
        }
        if (this.im == this.xt.im) {
            if (control.wall != -1) {
                control.wall = -1;
            }
        } else if (this.lastcolido != 0 && !this.dest) {
            --this.lastcolido;
        }
        if (this.dest) {
            if (checkPoints.dested[this.im] == 0) {
                checkPoints.dested[this.im] = this.lastcolido == 0 ? 1 : 2;
            }
        } else if (checkPoints.dested[this.im] != 0 && checkPoints.dested[this.im] != 3) {
            checkPoints.dested[this.im] = 0;
        }
        if (this.im == this.xt.im && this.rpd.wasted == 0 && this.rpdcatch != 0) {
            --this.rpdcatch;
            if (this.rpdcatch == 0) {
                this.rpd.cotchinow(this.im);
                if (this.rpd.hcaught) {
                    this.rpd.whenwasted = (int)(185.0f + this.m.random() * 20.0f);
                }
            }
        }
    }

    public void distruct(ContO contO) {
        for (int i = 0; i < contO.npl; ++i) {
            if (contO.p[i].wz != 0 && contO.p[i].gr != -17 && contO.p[i].gr != -16) continue;
            contO.p[i].embos = 1;
        }
    }

    public int regy(int n, float f, ContO contO) {
        int n2 = 0;
        boolean bl = true;
        if (this.xt.multion == 1 && this.xt.im != this.im) {
            bl = false;
        }
        if (this.xt.multion >= 2) {
            bl = false;
        }
        if (this.xt.lan && this.xt.multion >= 1 && this.xt.isbot[this.im]) {
            bl = true;
        }
        if ((f *= this.cd.dammult[this.cn]) > 100.0f) {
            int n3;
            int n4;
            int n5;
            this.rpd.recy(n, f, this.mtouch, this.im);
            f -= 100.0f;
            int n6 = 0;
            int n7 = 0;
            int n8 = contO.xy;
            for (n5 = contO.zy; n5 < 360; n5 += 360) {
            }
            while (n5 > 360) {
                n5 -= 360;
            }
            if (n5 < 210 && n5 > 150) {
                n6 = -1;
            }
            if (n5 > 330 || n5 < 30) {
                n6 = 1;
            }
            while (n8 < 360) {
                n8 += 360;
            }
            while (n8 > 360) {
                n8 -= 360;
            }
            if (n8 < 210 && n8 > 150) {
                n7 = -1;
            }
            if (n8 > 330 || n8 < 30) {
                n7 = 1;
            }
            if (n7 * n6 == 0) {
                this.shakedam = (int)((Math.abs(f) + (float)this.shakedam) / 2.0f);
            }
            if (this.im == this.xt.im || this.colidim) {
                this.xt.crash(f, n7 * n6);
            }
            if (n7 * n6 == 0 || this.mtouch) {
                for (n4 = 0; n4 < contO.npl; ++n4) {
                    float f2 = 0.0f;
                    for (n3 = 0; n3 < contO.p[n4].n; ++n3) {
                        if (contO.p[n4].wz != 0 || this.py(contO.keyx[n], contO.p[n4].ox[n3], contO.keyz[n], contO.p[n4].oz[n3]) >= this.cd.clrad[this.cn]) continue;
                        f2 = f / 20.0f * this.m.random();
                        int n9 = n3;
                        contO.p[n4].oz[n9] = (int)((float)contO.p[n4].oz[n9] + f2 * this.m.sin(n5));
                        int n10 = n3;
                        contO.p[n4].ox[n10] = (int)((float)contO.p[n4].ox[n10] - f2 * this.m.sin(n8));
                        if (!bl) continue;
                        this.hitmag = (int)((float)this.hitmag + Math.abs(f2));
                        n2 = (int)((float)n2 + Math.abs(f2));
                    }
                    if (f2 == 0.0f) continue;
                    if (Math.abs(f2) >= 1.0f) {
                        contO.p[n4].chip = 1;
                        contO.p[n4].ctmag = f2;
                    }
                    if (!contO.p[n4].nocol && contO.p[n4].glass != 1) {
                        if (contO.p[n4].bfase > 20 && (double)contO.p[n4].hsb[1] > 0.25) {
                            contO.p[n4].hsb[1] = 0.25f;
                        }
                        if (contO.p[n4].bfase > 25 && (double)contO.p[n4].hsb[2] > 0.7) {
                            contO.p[n4].hsb[2] = 0.7f;
                        }
                        if (contO.p[n4].bfase > 30 && (double)contO.p[n4].hsb[1] > 0.15) {
                            contO.p[n4].hsb[1] = 0.15f;
                        }
                        if (contO.p[n4].bfase > 35 && (double)contO.p[n4].hsb[2] > 0.6) {
                            contO.p[n4].hsb[2] = 0.6f;
                        }
                        if (contO.p[n4].bfase > 40) {
                            contO.p[n4].hsb[0] = 0.075f;
                        }
                        if (contO.p[n4].bfase > 50 && (double)contO.p[n4].hsb[2] > 0.5) {
                            contO.p[n4].hsb[2] = 0.5f;
                        }
                        if (contO.p[n4].bfase > 60) {
                            contO.p[n4].hsb[0] = 0.05f;
                        }
                        contO.p[n4].bfase = (int)((float)contO.p[n4].bfase + f2);
                        new Color(contO.p[n4].c[0], contO.p[n4].c[1], contO.p[n4].c[2]);
                        Color color = Color.getHSBColor(contO.p[n4].hsb[0], contO.p[n4].hsb[1], contO.p[n4].hsb[2]);
                        contO.p[n4].c[0] = color.getRed();
                        contO.p[n4].c[1] = color.getGreen();
                        contO.p[n4].c[2] = color.getBlue();
                    }
                    if (contO.p[n4].glass != 1) continue;
                    contO.p[n4].gr = (int)((double)contO.p[n4].gr + Math.abs((double)f2 * 1.5));
                }
            }
            if (n7 * n6 == -1) {
                if (this.nbsq > 0) {
                    n4 = 0;
                    int n11 = 1;
                    for (n3 = 0; n3 < contO.npl; ++n3) {
                        float f3 = 0.0f;
                        for (int i = 0; i < contO.p[n3].n; ++i) {
                            if (contO.p[n3].wz != 0) continue;
                            f3 = f / 15.0f * this.m.random();
                            if (Math.abs(contO.p[n3].oy[i] - this.cd.flipy[this.cn] - this.squash) >= this.cd.msquash[this.cn] * 3 && contO.p[n3].oy[i] >= this.cd.flipy[this.cn] + this.squash || this.squash >= this.cd.msquash[this.cn]) continue;
                            int n12 = i;
                            contO.p[n3].oy[n12] = (int)((float)contO.p[n3].oy[n12] + f3);
                            n4 = (int)((float)n4 + f3);
                            ++n11;
                            if (!bl) continue;
                            this.hitmag = (int)((float)this.hitmag + Math.abs(f3));
                            n2 = (int)((float)n2 + Math.abs(f3));
                        }
                        if (contO.p[n3].glass == 1) {
                            contO.p[n3].gr += 5;
                        } else if (f3 != 0.0f) {
                            contO.p[n3].bfase = (int)((float)contO.p[n3].bfase + f3);
                        }
                        if (!(Math.abs(f3) >= 1.0f)) continue;
                        contO.p[n3].chip = 1;
                        contO.p[n3].ctmag = f3;
                    }
                    this.squash += n4 / n11;
                    this.nbsq = 0;
                } else {
                    ++this.nbsq;
                }
            }
        }
        return n2;
    }

    public int regx(int n, float f, ContO contO) {
        int n2 = 0;
        boolean bl = true;
        if (this.xt.multion == 1 && this.xt.im != this.im) {
            bl = false;
        }
        if (this.xt.multion >= 2) {
            bl = false;
        }
        if (this.xt.lan && this.xt.multion >= 1 && this.xt.isbot[this.im]) {
            bl = true;
        }
        if (Math.abs(f *= this.cd.dammult[this.cn]) > 100.0f) {
            this.rpd.recx(n, f, this.im);
            if (f > 100.0f) {
                f -= 100.0f;
            }
            if (f < -100.0f) {
                f += 100.0f;
            }
            this.shakedam = (int)((Math.abs(f) + (float)this.shakedam) / 2.0f);
            if (this.im == this.xt.im || this.colidim) {
                this.xt.crash(f, 0);
            }
            for (int i = 0; i < contO.npl; ++i) {
                float f2 = 0.0f;
                for (int j = 0; j < contO.p[i].n; ++j) {
                    if (contO.p[i].wz != 0 || this.py(contO.keyx[n], contO.p[i].ox[j], contO.keyz[n], contO.p[i].oz[j]) >= this.cd.clrad[this.cn]) continue;
                    f2 = f / 20.0f * this.m.random();
                    int n3 = j;
                    contO.p[i].oz[n3] = (int)((float)contO.p[i].oz[n3] - f2 * this.m.sin(contO.xz) * this.m.cos(contO.zy));
                    int n4 = j;
                    contO.p[i].ox[n4] = (int)((float)contO.p[i].ox[n4] + f2 * this.m.cos(contO.xz) * this.m.cos(contO.xy));
                    if (!bl) continue;
                    this.hitmag = (int)((float)this.hitmag + Math.abs(f2));
                    n2 = (int)((float)n2 + Math.abs(f2));
                }
                if (f2 == 0.0f) continue;
                if (Math.abs(f2) >= 1.0f) {
                    contO.p[i].chip = 1;
                    contO.p[i].ctmag = f2;
                }
                if (!contO.p[i].nocol && contO.p[i].glass != 1) {
                    if (contO.p[i].bfase > 20 && (double)contO.p[i].hsb[1] > 0.25) {
                        contO.p[i].hsb[1] = 0.25f;
                    }
                    if (contO.p[i].bfase > 25 && (double)contO.p[i].hsb[2] > 0.7) {
                        contO.p[i].hsb[2] = 0.7f;
                    }
                    if (contO.p[i].bfase > 30 && (double)contO.p[i].hsb[1] > 0.15) {
                        contO.p[i].hsb[1] = 0.15f;
                    }
                    if (contO.p[i].bfase > 35 && (double)contO.p[i].hsb[2] > 0.6) {
                        contO.p[i].hsb[2] = 0.6f;
                    }
                    if (contO.p[i].bfase > 40) {
                        contO.p[i].hsb[0] = 0.075f;
                    }
                    if (contO.p[i].bfase > 50 && (double)contO.p[i].hsb[2] > 0.5) {
                        contO.p[i].hsb[2] = 0.5f;
                    }
                    if (contO.p[i].bfase > 60) {
                        contO.p[i].hsb[0] = 0.05f;
                    }
                    contO.p[i].bfase = (int)((float)contO.p[i].bfase + Math.abs(f2));
                    new Color(contO.p[i].c[0], contO.p[i].c[1], contO.p[i].c[2]);
                    Color color = Color.getHSBColor(contO.p[i].hsb[0], contO.p[i].hsb[1], contO.p[i].hsb[2]);
                    contO.p[i].c[0] = color.getRed();
                    contO.p[i].c[1] = color.getGreen();
                    contO.p[i].c[2] = color.getBlue();
                }
                if (contO.p[i].glass != 1) continue;
                contO.p[i].gr = (int)((double)contO.p[i].gr + Math.abs((double)f2 * 1.5));
            }
        }
        return n2;
    }

    public int regz(int n, float f, ContO contO) {
        int n2 = 0;
        boolean bl = true;
        if (this.xt.multion == 1 && this.xt.im != this.im) {
            bl = false;
        }
        if (this.xt.multion >= 2) {
            bl = false;
        }
        if (this.xt.lan && this.xt.multion >= 1 && this.xt.isbot[this.im]) {
            bl = true;
        }
        if (Math.abs(f *= this.cd.dammult[this.cn]) > 100.0f) {
            this.rpd.recz(n, f, this.im);
            if (f > 100.0f) {
                f -= 100.0f;
            }
            if (f < -100.0f) {
                f += 100.0f;
            }
            this.shakedam = (int)((Math.abs(f) + (float)this.shakedam) / 2.0f);
            if (this.im == this.xt.im || this.colidim) {
                this.xt.crash(f, 0);
            }
            for (int i = 0; i < contO.npl; ++i) {
                float f2 = 0.0f;
                for (int j = 0; j < contO.p[i].n; ++j) {
                    if (contO.p[i].wz != 0 || this.py(contO.keyx[n], contO.p[i].ox[j], contO.keyz[n], contO.p[i].oz[j]) >= this.cd.clrad[this.cn]) continue;
                    f2 = f / 20.0f * this.m.random();
                    int n3 = j;
                    contO.p[i].oz[n3] = (int)((float)contO.p[i].oz[n3] + f2 * this.m.cos(contO.xz) * this.m.cos(contO.zy));
                    int n4 = j;
                    contO.p[i].ox[n4] = (int)((float)contO.p[i].ox[n4] + f2 * this.m.sin(contO.xz) * this.m.cos(contO.xy));
                    if (!bl) continue;
                    this.hitmag = (int)((float)this.hitmag + Math.abs(f2));
                    n2 = (int)((float)n2 + Math.abs(f2));
                }
                if (f2 == 0.0f) continue;
                if (Math.abs(f2) >= 1.0f) {
                    contO.p[i].chip = 1;
                    contO.p[i].ctmag = f2;
                }
                if (!contO.p[i].nocol && contO.p[i].glass != 1) {
                    if (contO.p[i].bfase > 20 && (double)contO.p[i].hsb[1] > 0.25) {
                        contO.p[i].hsb[1] = 0.25f;
                    }
                    if (contO.p[i].bfase > 25 && (double)contO.p[i].hsb[2] > 0.7) {
                        contO.p[i].hsb[2] = 0.7f;
                    }
                    if (contO.p[i].bfase > 30 && (double)contO.p[i].hsb[1] > 0.15) {
                        contO.p[i].hsb[1] = 0.15f;
                    }
                    if (contO.p[i].bfase > 35 && (double)contO.p[i].hsb[2] > 0.6) {
                        contO.p[i].hsb[2] = 0.6f;
                    }
                    if (contO.p[i].bfase > 40) {
                        contO.p[i].hsb[0] = 0.075f;
                    }
                    if (contO.p[i].bfase > 50 && (double)contO.p[i].hsb[2] > 0.5) {
                        contO.p[i].hsb[2] = 0.5f;
                    }
                    if (contO.p[i].bfase > 60) {
                        contO.p[i].hsb[0] = 0.05f;
                    }
                    contO.p[i].bfase = (int)((float)contO.p[i].bfase + Math.abs(f2));
                    new Color(contO.p[i].c[0], contO.p[i].c[1], contO.p[i].c[2]);
                    Color color = Color.getHSBColor(contO.p[i].hsb[0], contO.p[i].hsb[1], contO.p[i].hsb[2]);
                    contO.p[i].c[0] = color.getRed();
                    contO.p[i].c[1] = color.getGreen();
                    contO.p[i].c[2] = color.getBlue();
                }
                if (contO.p[i].glass != 1) continue;
                contO.p[i].gr = (int)((double)contO.p[i].gr + Math.abs((double)f2 * 1.5));
            }
        }
        return n2;
    }

    public void colide(ContO contO, Mad mad, ContO contO2) {
        int n;
        float[] fArray = new float[4];
        float[] fArray2 = new float[4];
        float[] fArray3 = new float[4];
        float[] fArray4 = new float[4];
        float[] fArray5 = new float[4];
        float[] fArray6 = new float[4];
        for (n = 0; n < 4; ++n) {
            fArray[n] = contO.x + contO.keyx[n];
            fArray2[n] = this.capsized ? (float)(contO.y + this.cd.flipy[this.cn] + this.squash) : (float)(contO.y + contO.grat);
            fArray3[n] = contO.z + contO.keyz[n];
            fArray4[n] = contO2.x + contO2.keyx[n];
            fArray5[n] = this.capsized ? (float)(contO2.y + this.cd.flipy[mad.cn] + mad.squash) : (float)(contO2.y + contO2.grat);
            fArray6[n] = contO2.z + contO2.keyz[n];
        }
        this.rot(fArray, fArray2, contO.x, contO.y, contO.xy, 4);
        this.rot(fArray2, fArray3, contO.y, contO.z, contO.zy, 4);
        this.rot(fArray, fArray3, contO.x, contO.z, contO.xz, 4);
        this.rot(fArray4, fArray5, contO2.x, contO2.y, contO2.xy, 4);
        this.rot(fArray5, fArray6, contO2.y, contO2.z, contO2.zy, 4);
        this.rot(fArray4, fArray6, contO2.x, contO2.z, contO2.xz, 4);
        if ((double)this.rpy(contO.x, contO2.x, contO.y, contO2.y, contO.z, contO2.z) < (double)(contO.maxR * contO.maxR + contO2.maxR * contO2.maxR) * 1.5) {
            if (!(this.caught[mad.im] || this.speed == 0.0f && mad.speed == 0.0f)) {
                this.dominate[mad.im] = Math.abs(this.power * this.speed * this.cd.moment[this.cn]) != Math.abs(mad.power * mad.speed * this.cd.moment[mad.cn]) ? Math.abs(this.power * this.speed * this.cd.moment[this.cn]) > Math.abs(mad.power * mad.speed * this.cd.moment[mad.cn]) : this.cd.moment[this.cn] > this.cd.moment[mad.cn];
                this.caught[mad.im] = true;
            }
        } else if (this.caught[mad.im]) {
            this.caught[mad.im] = false;
        }
        n = 0;
        int n2 = 0;
        if (this.dominate[mad.im]) {
            int n3 = (int)(((this.scz[0] - mad.scz[0] + this.scz[1] - mad.scz[1] + this.scz[2] - mad.scz[2] + this.scz[3] - mad.scz[3]) * (this.scz[0] - mad.scz[0] + this.scz[1] - mad.scz[1] + this.scz[2] - mad.scz[2] + this.scz[3] - mad.scz[3]) + (this.scx[0] - mad.scx[0] + this.scx[1] - mad.scx[1] + this.scx[2] - mad.scx[2] + this.scx[3] - mad.scx[3]) * (this.scx[0] - mad.scx[0] + this.scx[1] - mad.scx[1] + this.scx[2] - mad.scx[2] + this.scx[3] - mad.scx[3])) / 16.0f);
            int n4 = 7000;
            float f = 1.0f;
            if (this.xt.multion != 0) {
                n4 = 28000;
                f = 1.27f;
            }
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    float f2;
                    float f3;
                    if (!((float)this.rpy(fArray[i], fArray4[j], fArray2[i], fArray5[j], fArray3[i], fArray6[j]) < (float)(n3 + n4) * (this.cd.comprad[mad.cn] + this.cd.comprad[this.cn]))) continue;
                    if (Math.abs(this.scx[i] * this.cd.moment[this.cn]) > Math.abs(mad.scx[j] * this.cd.moment[mad.cn])) {
                        f3 = mad.scx[j] * (float)this.cd.revpush[this.cn];
                        if (f3 > 300.0f) {
                            f3 = 300.0f;
                        }
                        if (f3 < -300.0f) {
                            f3 = -300.0f;
                        }
                        if ((f2 = this.scx[i] * (float)this.cd.push[this.cn]) > 300.0f) {
                            f2 = 300.0f;
                        }
                        if (f2 < -300.0f) {
                            f2 = -300.0f;
                        }
                        int n5 = j;
                        mad.scx[n5] = mad.scx[n5] + f2;
                        if (this.im == this.xt.im) {
                            mad.colidim = true;
                        }
                        n += mad.regx(j, f2 * this.cd.moment[this.cn] * f, contO2);
                        if (mad.colidim) {
                            mad.colidim = false;
                        }
                        int n6 = i;
                        this.scx[n6] = this.scx[n6] - f3;
                        n2 += this.regx(i, -f3 * this.cd.moment[this.cn] * f, contO);
                        int n7 = i;
                        this.scy[n7] = this.scy[n7] - (float)this.cd.revlift[this.cn];
                        if (this.im == this.xt.im) {
                            mad.colidim = true;
                        }
                        n += mad.regy(j, this.cd.revlift[this.cn] * 7, contO2);
                        if (mad.colidim) {
                            mad.colidim = false;
                        }
                        if (this.m.random() > this.m.random()) {
                            contO2.sprk((fArray[i] + fArray4[j]) / 2.0f, (fArray2[i] + fArray5[j]) / 2.0f, (fArray3[i] + fArray6[j]) / 2.0f, (mad.scx[j] + this.scx[i]) / 4.0f, (mad.scy[j] + this.scy[i]) / 4.0f, (mad.scz[j] + this.scz[i]) / 4.0f, 2);
                        }
                    }
                    if (Math.abs(this.scz[i] * this.cd.moment[this.cn]) > Math.abs(mad.scz[j] * this.cd.moment[mad.cn])) {
                        f3 = mad.scz[j] * (float)this.cd.revpush[this.cn];
                        if (f3 > 300.0f) {
                            f3 = 300.0f;
                        }
                        if (f3 < -300.0f) {
                            f3 = -300.0f;
                        }
                        if ((f2 = this.scz[i] * (float)this.cd.push[this.cn]) > 300.0f) {
                            f2 = 300.0f;
                        }
                        if (f2 < -300.0f) {
                            f2 = -300.0f;
                        }
                        int n8 = j;
                        mad.scz[n8] = mad.scz[n8] + f2;
                        if (this.im == this.xt.im) {
                            mad.colidim = true;
                        }
                        n += mad.regz(j, f2 * this.cd.moment[this.cn] * f, contO2);
                        if (mad.colidim) {
                            mad.colidim = false;
                        }
                        int n9 = i;
                        this.scz[n9] = this.scz[n9] - f3;
                        n2 += this.regz(i, -f3 * this.cd.moment[this.cn] * f, contO);
                        int n10 = i;
                        this.scy[n10] = this.scy[n10] - (float)this.cd.revlift[this.cn];
                        if (this.im == this.xt.im) {
                            mad.colidim = true;
                        }
                        n += mad.regy(j, this.cd.revlift[this.cn] * 7, contO2);
                        if (mad.colidim) {
                            mad.colidim = false;
                        }
                        if (this.m.random() > this.m.random()) {
                            contO2.sprk((fArray[i] + fArray4[j]) / 2.0f, (fArray2[i] + fArray5[j]) / 2.0f, (fArray3[i] + fArray6[j]) / 2.0f, (mad.scx[j] + this.scx[i]) / 4.0f, (mad.scy[j] + this.scy[i]) / 4.0f, (mad.scz[j] + this.scz[i]) / 4.0f, 2);
                        }
                    }
                    if (this.im == this.xt.im) {
                        mad.lastcolido = 70;
                    }
                    if (mad.im == this.xt.im) {
                        this.lastcolido = 70;
                    }
                    int n11 = j;
                    mad.scy[n11] = mad.scy[n11] - (float)this.cd.lift[this.cn];
                }
            }
        }
        if (this.xt.multion == 1) {
            if (mad.im == this.xt.im && n != 0) {
                int n12 = this.im;
                this.xt.dcrashes[n12] = this.xt.dcrashes[n12] + n;
            }
            if (this.im == this.xt.im && n2 != 0) {
                int n13 = mad.im;
                this.xt.dcrashes[n13] = this.xt.dcrashes[n13] + n2;
            }
        }
    }

    public void rot(float[] fArray, float[] fArray2, int n, int n2, int n3, int n4) {
        if (n3 != 0) {
            for (int i = 0; i < n4; ++i) {
                float f = fArray[i];
                float f2 = fArray2[i];
                fArray[i] = (float)n + ((f - (float)n) * this.m.cos(n3) - (f2 - (float)n2) * this.m.sin(n3));
                fArray2[i] = (float)n2 + ((f - (float)n) * this.m.sin(n3) + (f2 - (float)n2) * this.m.cos(n3));
            }
        }
    }

    public int rpy(float f, float f2, float f3, float f4, float f5, float f6) {
        return (int)((f - f2) * (f - f2) + (f3 - f4) * (f3 - f4) + (f5 - f6) * (f5 - f6));
    }

    public int py(int n, int n2, int n3, int n4) {
        return (n - n2) * (n - n2) + (n3 - n4) * (n3 - n4);
    }
}

