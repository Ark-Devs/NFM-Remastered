/*
 * Decompiled with CFR 0.152.
 */
public class Control {
    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;
    boolean handb = false;
    int lookback = 0;
    boolean enter = false;
    boolean exit = false;
    boolean arrace = false;
    boolean mutem = false;
    boolean mutes = false;
    boolean radar = false;
    int chatup = 0;
    int multion = 0;
    Medium m;
    int pan = 0;
    int attack = 0;
    int acr = 0;
    boolean afta = false;
    int[] fpnt = new int[5];
    int trfix = 0;
    boolean forget = false;
    boolean bulistc = false;
    int runbul = 0;
    int acuracy = 0;
    int upwait = 0;
    boolean agressed = false;
    float skiplev = 1.0f;
    int clrnce = 5;
    int rampp = 0;
    int turntyp = 0;
    float aim = 0.0f;
    int saftey = 30;
    boolean perfection = false;
    float mustland = 0.5f;
    boolean usebounce = false;
    float trickprf = 0.5f;
    int stuntf = 0;
    boolean zyinv = false;
    boolean lastl = false;
    boolean wlastl = false;
    int hold = 0;
    int wall = -1;
    int lwall = -1;
    int stcnt = 0;
    int statusque = 0;
    int turncnt = 0;
    int randtcnt = 0;
    int upcnt = 0;
    int trickfase = 0;
    int swat = 0;
    boolean udcomp = false;
    boolean lrcomp = false;
    boolean udbare = false;
    boolean lrbare = false;
    boolean onceu = false;
    boolean onced = false;
    boolean oncel = false;
    boolean oncer = false;
    int lrdirect = 0;
    int uddirect = 0;
    int lrstart = 0;
    int udstart = 0;
    int oxy = 0;
    int ozy = 0;
    int flycnt = 0;
    boolean lrswt = false;
    boolean udswt = false;
    boolean gowait = false;
    int actwait = 0;
    int cntrn = 0;
    int revstart = 0;
    int oupnt = 0;
    int wtz = 0;
    int wtx = 0;
    int frx = 0;
    int frz = 0;
    int frad = 0;
    int apunch = 0;
    boolean exitattack = false;
    int avoidnlev = 0;

    public Control(Medium medium) {
        this.m = medium;
    }

    public void falseo(int n) {
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        this.handb = false;
        this.lookback = 0;
        this.enter = false;
        this.exit = false;
        if (n != 1) {
            this.radar = false;
            this.arrace = false;
            this.chatup = 0;
            if (n != 2) {
                this.multion = 0;
            }
            if (n != 3) {
                this.mutem = false;
                this.mutes = false;
            }
        }
    }

    public void reset(CheckPoints checkPoints, int n) {
        this.pan = 0;
        this.attack = 0;
        this.acr = 0;
        this.afta = false;
        this.trfix = 0;
        this.acuracy = 0;
        this.upwait = 0;
        this.forget = false;
        this.bulistc = false;
        this.runbul = 0;
        this.revstart = 0;
        this.oupnt = 0;
        this.gowait = false;
        this.apunch = 0;
        this.exitattack = false;
        if (checkPoints.stage == 16 || checkPoints.stage == 18) {
            this.hold = 50;
        }
        if (checkPoints.stage == 17) {
            this.hold = 10;
        }
        if (checkPoints.stage == 20) {
            this.hold = 30;
        }
        if (checkPoints.stage == 21) {
            if (n != 13) {
                this.hold = 35;
                this.revstart = 25;
            } else {
                this.hold = 5;
            }
            this.statusque = 0;
        }
        if (checkPoints.stage == 22) {
            if (n != 13) {
                this.hold = (int)(20.0f + 10.0f * this.m.random());
                this.revstart = (int)(10.0f + 10.0f * this.m.random());
            } else {
                this.hold = 5;
            }
            this.statusque = 0;
        }
        if (checkPoints.stage == 24) {
            this.hold = 30;
            this.statusque = 0;
            if (n != 14) {
                this.revstart = 1;
            }
        }
        if (checkPoints.stage == 25) {
            this.hold = 40;
        }
        if (checkPoints.stage == 26) {
            this.hold = 20;
        }
        if (checkPoints.stage != 19 && checkPoints.stage != 26) {
            int n2;
            for (n2 = 0; n2 < checkPoints.fn; ++n2) {
                int n3 = -10;
                for (int i = 0; i < checkPoints.n; ++i) {
                    if (this.py(checkPoints.fx[n2] / 100, checkPoints.x[i] / 100, checkPoints.fz[n2] / 100, checkPoints.z[i] / 100) >= n3 && n3 != -10) continue;
                    n3 = this.py(checkPoints.fx[n2] / 100, checkPoints.x[i] / 100, checkPoints.fz[n2] / 100, checkPoints.z[i] / 100);
                    this.fpnt[n2] = i;
                }
            }
            for (n2 = 0; n2 < checkPoints.fn; ++n2) {
                int n4 = n2;
                this.fpnt[n4] = this.fpnt[n4] - 4;
                if (this.fpnt[n2] >= 0) continue;
                int n5 = n2;
                this.fpnt[n5] = this.fpnt[n5] + checkPoints.nsp;
            }
        } else {
            if (checkPoints.stage == 19) {
                this.fpnt[0] = 14;
                this.fpnt[1] = 36;
            }
            if (checkPoints.stage == 26) {
                this.fpnt[3] = 39;
            }
        }
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        this.handb = false;
        this.lookback = 0;
        this.arrace = false;
        this.mutem = false;
        this.mutes = false;
    }

    public void preform(Mad mad, ContO contO, CheckPoints checkPoints, Trackers trackers) {
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        this.handb = false;
        if (!mad.dest) {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            if (mad.mtouch) {
                if (this.stcnt > this.statusque) {
                    n7 = checkPoints.stage;
                    if (n7 > 10) {
                        n7 -= 10;
                    }
                    this.acuracy = (7 - checkPoints.pos[mad.im]) * checkPoints.pos[0] * (6 - n7 * 2);
                    if (this.acuracy < 0 || checkPoints.stage == -1) {
                        this.acuracy = 0;
                    }
                    this.clrnce = 5;
                    if (checkPoints.stage == 16 || checkPoints.stage == 21) {
                        this.clrnce = 2;
                    }
                    if (checkPoints.stage == 22 && (mad.pcleared == 27 || mad.pcleared == 17)) {
                        this.clrnce = 3;
                    }
                    if (checkPoints.stage == 26 && mad.pcleared == 33) {
                        this.clrnce = 3;
                    }
                    float f = 0.0f;
                    if (checkPoints.stage == 1) {
                        f = 2.0f;
                    }
                    if (checkPoints.stage == 2) {
                        f = 1.5f;
                    }
                    if (checkPoints.stage == 3 && mad.cn != 6) {
                        f = 0.5f;
                    }
                    if (checkPoints.stage == 4) {
                        f = 0.5f;
                    }
                    if (checkPoints.stage == 11) {
                        f = 2.0f;
                    }
                    if (checkPoints.stage == 12) {
                        f = 1.5f;
                    }
                    if (checkPoints.stage == 13 && mad.cn != 9) {
                        f = 0.5f;
                    }
                    if (checkPoints.stage == 14) {
                        f = 0.5f;
                    }
                    this.upwait = (int)((float)((checkPoints.pos[0] - checkPoints.pos[mad.im]) * (checkPoints.pos[0] - checkPoints.pos[mad.im]) * (checkPoints.pos[0] - checkPoints.pos[mad.im])) * f);
                    if (this.upwait > 80) {
                        this.upwait = 80;
                    }
                    if ((checkPoints.stage == 11 || checkPoints.stage == 1) && this.upwait < 20) {
                        this.upwait = 20;
                    }
                    f = 0.0f;
                    if (checkPoints.stage == 1 || checkPoints.stage == 2) {
                        f = 1.0f;
                    }
                    if (checkPoints.stage == 4) {
                        f = 0.5f;
                    }
                    if (checkPoints.stage == 7) {
                        f = 0.5f;
                    }
                    if (checkPoints.stage == 10) {
                        f = 0.5f;
                    }
                    if (checkPoints.stage == 11 || checkPoints.stage == 12) {
                        f = 1.0f;
                    }
                    if (checkPoints.stage == 13) {
                        f = 0.5f;
                    }
                    if (checkPoints.stage == 14) {
                        f = 0.5f;
                    }
                    if (checkPoints.stage == 15) {
                        f = 0.2f;
                    }
                    if (checkPoints.pos[mad.im] - checkPoints.pos[0] >= -1) {
                        this.skiplev = (float)((double)this.skiplev - 0.1);
                        if (this.skiplev < 0.0f) {
                            this.skiplev = 0.0f;
                        }
                    } else {
                        this.skiplev = (float)((double)this.skiplev + 0.2);
                        if (this.skiplev > f) {
                            this.skiplev = f;
                        }
                    }
                    if (checkPoints.stage == 18) {
                        this.skiplev = mad.pcleared >= 10 && mad.pcleared <= 24 ? 1.0f : 0.0f;
                    }
                    if (checkPoints.stage == 21) {
                        this.skiplev = 0.0f;
                        if (mad.pcleared == 5) {
                            this.skiplev = 1.0f;
                        }
                        if (mad.pcleared == 28 || mad.pcleared == 35) {
                            this.skiplev = 0.5f;
                        }
                    }
                    if (checkPoints.stage == 23) {
                        this.skiplev = 0.5f;
                    }
                    if (checkPoints.stage == 24 || checkPoints.stage == 22) {
                        this.skiplev = 1.0f;
                    }
                    if (checkPoints.stage == 26 || checkPoints.stage == 25 || checkPoints.stage == 20) {
                        this.skiplev = 0.0f;
                    }
                    this.rampp = (int)(this.m.random() * 4.0f - 2.0f);
                    if (mad.power == 98.0f) {
                        this.rampp = -1;
                    }
                    if (mad.power < 75.0f && this.rampp == -1) {
                        this.rampp = 0;
                    }
                    if (mad.power < 60.0f) {
                        this.rampp = 1;
                    }
                    if (checkPoints.stage == 6) {
                        this.rampp = 2;
                    }
                    if (checkPoints.stage == 18 && mad.pcleared >= 45) {
                        this.rampp = 2;
                    }
                    if (checkPoints.stage == 22 && mad.pcleared == 17) {
                        this.rampp = 2;
                    }
                    if (checkPoints.stage == 25 || checkPoints.stage == 26) {
                        this.rampp = 0;
                    }
                    if (this.cntrn == 0) {
                        this.agressed = false;
                        this.turntyp = (int)(this.m.random() * 4.0f);
                        if (checkPoints.stage == 3 && mad.cn == 6) {
                            this.turntyp = 1;
                            if (this.attack == 0) {
                                this.agressed = true;
                            }
                        }
                        if (checkPoints.stage == 9 && mad.cn == 15) {
                            this.turntyp = 1;
                            if (this.attack == 0) {
                                this.agressed = true;
                            }
                        }
                        if (checkPoints.stage == 13 && mad.cn == 9) {
                            this.turntyp = 1;
                            if (this.attack == 0) {
                                this.agressed = true;
                            }
                        }
                        if (checkPoints.pos[0] - checkPoints.pos[mad.im] < 0) {
                            this.turntyp = (int)(this.m.random() * 2.0f);
                        }
                        if (checkPoints.stage == 10) {
                            this.turntyp = 2;
                        }
                        if (checkPoints.stage == 18) {
                            this.turntyp = 2;
                        }
                        if (checkPoints.stage == 20) {
                            this.turntyp = 0;
                        }
                        if (checkPoints.stage == 23) {
                            this.turntyp = 1;
                        }
                        if (checkPoints.stage == 24) {
                            this.turntyp = 0;
                        }
                        if (this.attack != 0) {
                            this.turntyp = 2;
                            if (checkPoints.stage == 9 || checkPoints.stage == 10 || checkPoints.stage == 19 || checkPoints.stage == 21 || checkPoints.stage == 23 || checkPoints.stage == 27) {
                                this.turntyp = (int)(this.m.random() * 3.0f);
                            }
                            if (checkPoints.stage == 26 && checkPoints.clear[mad.im] - checkPoints.clear[0] >= 5) {
                                this.turntyp = 0;
                            }
                        }
                        if (checkPoints.stage == 6) {
                            this.turntyp = 1;
                            this.agressed = true;
                        }
                        if (checkPoints.stage == 7 || checkPoints.stage == 9 || checkPoints.stage == 10 || checkPoints.stage == 16 || checkPoints.stage == 17 || checkPoints.stage == 19 || checkPoints.stage == 20 || checkPoints.stage == 21 || checkPoints.stage == 22 || checkPoints.stage == 24 || checkPoints.stage == 26 || checkPoints.stage == 27) {
                            this.agressed = true;
                        }
                        if (checkPoints.stage == -1) {
                            this.agressed = this.m.random() > this.m.random();
                        }
                        this.cntrn = 5;
                    } else {
                        --this.cntrn;
                    }
                    this.saftey = (int)((double)((98.0f - mad.power) / 2.0f) * ((double)(this.m.random() / 2.0f) + 0.5));
                    if (this.saftey > 20) {
                        this.saftey = 20;
                    }
                    f = 0.0f;
                    if (checkPoints.stage == 1 || checkPoints.stage == 11) {
                        f = 0.9f;
                    }
                    if (checkPoints.stage == 2 || checkPoints.stage == 12) {
                        f = 0.7f;
                    }
                    if (checkPoints.stage == 4 || checkPoints.stage == 13) {
                        f = 0.4f;
                    }
                    this.mustland = f + (float)((double)(this.m.random() / 2.0f) - 0.25);
                    f = 1.0f;
                    if (checkPoints.stage == 1 || checkPoints.stage == 11) {
                        f = 5.0f;
                    }
                    if (checkPoints.stage == 2 || checkPoints.stage == 12) {
                        f = 2.0f;
                    }
                    if (checkPoints.stage == 4 || checkPoints.stage == 13) {
                        f = 1.5f;
                    }
                    if (mad.power > 50.0f) {
                        if (checkPoints.pos[0] - checkPoints.pos[mad.im] > 0) {
                            this.saftey = (int)((float)this.saftey * f);
                        } else {
                            this.mustland = 0.0f;
                        }
                    } else {
                        this.mustland -= 0.5f;
                    }
                    if (checkPoints.stage == 18 || checkPoints.stage == 20 || checkPoints.stage == 22 || checkPoints.stage == 24) {
                        this.mustland = 0.0f;
                    }
                    this.stuntf = 0;
                    if (checkPoints.stage == 8) {
                        this.stuntf = 17;
                    }
                    if (checkPoints.stage == 18 && mad.pcleared == 57) {
                        this.stuntf = 1;
                    }
                    if (checkPoints.stage == 19 && mad.pcleared == 3) {
                        this.stuntf = 2;
                    }
                    if (checkPoints.stage == 20) {
                        if (checkPoints.pos[0] < checkPoints.pos[mad.im] || Math.abs(checkPoints.clear[0] - mad.clear) >= 2 || mad.clear < 2) {
                            this.stuntf = 4;
                            this.saftey = 10;
                        } else {
                            this.stuntf = 3;
                        }
                    }
                    if (checkPoints.stage == 21 && mad.pcleared == 21) {
                        this.stuntf = 1;
                    }
                    if (checkPoints.stage == 24) {
                        this.saftey = 10;
                        if (mad.pcleared >= 4 && mad.pcleared < 70) {
                            this.stuntf = 4;
                        } else if (mad.cn == 12 || mad.cn == 8) {
                            this.stuntf = 2;
                        }
                        if (mad.cn == 14) {
                            this.stuntf = 6;
                        }
                    }
                    if (checkPoints.stage == 26) {
                        this.mustland = 0.0f;
                        this.saftey = 10;
                        if (!(mad.pcleared != 15 && mad.pcleared != 51 || !((double)this.m.random() > 0.4) && this.trfix == 0)) {
                            this.stuntf = 7;
                        }
                        if (mad.pcleared == 42) {
                            this.stuntf = 1;
                        }
                        if (mad.pcleared == 77) {
                            this.stuntf = 7;
                        }
                        this.avoidnlev = (int)(2700.0f * this.m.random());
                    }
                    this.trickprf = (mad.power - 38.0f) / 50.0f - this.m.random() / 2.0f;
                    if (mad.power < 60.0f) {
                        this.trickprf = -1.0f;
                    }
                    if (checkPoints.stage == 6 && (double)this.trickprf > 0.5) {
                        this.trickprf = 0.5f;
                    }
                    if (checkPoints.stage == 3 && mad.cn == 6 && (double)this.trickprf > 0.7) {
                        this.trickprf = 0.7f;
                    }
                    if (checkPoints.stage == 13 && mad.cn == 9 && (double)this.trickprf > 0.7) {
                        this.trickprf = 0.7f;
                    }
                    if (checkPoints.stage == 16 && (double)this.trickprf > 0.3) {
                        this.trickprf = 0.3f;
                    }
                    if (checkPoints.stage == 18 && (double)this.trickprf > 0.2) {
                        this.trickprf = 0.2f;
                    }
                    if (checkPoints.stage == 19) {
                        if ((double)this.trickprf > 0.5) {
                            this.trickprf = 0.5f;
                        }
                        if ((mad.im == 6 || mad.im == 5) && (double)this.trickprf > 0.3) {
                            this.trickprf = 0.3f;
                        }
                    }
                    if (checkPoints.stage == 21 && this.trickprf != -1.0f) {
                        this.trickprf *= 0.75f;
                    }
                    if (checkPoints.stage == 22 && (mad.pcleared == 55 || mad.pcleared == 7)) {
                        this.trickprf = -1.0f;
                        this.stuntf = 5;
                    }
                    if (checkPoints.stage == 23 && (double)this.trickprf > 0.4) {
                        this.trickprf = 0.4f;
                    }
                    if (checkPoints.stage == 24 && (double)this.trickprf > 0.5) {
                        this.trickprf = 0.5f;
                    }
                    if (checkPoints.stage == 27) {
                        this.trickprf = -1.0f;
                    }
                    this.usebounce = this.m.random() > mad.power / 100.0f;
                    if (checkPoints.stage == 9) {
                        this.usebounce = false;
                    }
                    if (checkPoints.stage == 14 || checkPoints.stage == 16) {
                        this.usebounce = true;
                    }
                    if (checkPoints.stage == 20 || checkPoints.stage == 24) {
                        this.usebounce = false;
                    }
                    this.perfection = !(this.m.random() > (float)mad.hitmag / (float)mad.cd.maxmag[mad.cn]);
                    if (100.0f * (float)mad.hitmag / (float)mad.cd.maxmag[mad.cn] > 60.0f) {
                        this.perfection = true;
                    }
                    if (checkPoints.stage == 3 && mad.cn == 6) {
                        this.perfection = true;
                    }
                    if (checkPoints.stage == 6 || checkPoints.stage == 8 || checkPoints.stage == 9 || checkPoints.stage == 10 || checkPoints.stage == 16 || checkPoints.stage == 18 || checkPoints.stage == 19 || checkPoints.stage == 20 || checkPoints.stage == 21 || checkPoints.stage == 22 || checkPoints.stage == 24 || checkPoints.stage == 26 || checkPoints.stage == 27) {
                        this.perfection = true;
                    }
                    if (this.attack == 0) {
                        n6 = 1;
                        if (checkPoints.stage == 3 || checkPoints.stage == 1 || checkPoints.stage == 4 || checkPoints.stage == 9 || checkPoints.stage == 13 || checkPoints.stage == 11 || checkPoints.stage == 14 || checkPoints.stage == 19 || checkPoints.stage == 23 || checkPoints.stage == 26) {
                            n6 = this.afta ? 1 : 0;
                        }
                        if (checkPoints.stage == 8 || checkPoints.stage == 6 || checkPoints.stage == 18 || checkPoints.stage == 16 || checkPoints.stage == 20 || checkPoints.stage == 24) {
                            n6 = 0;
                        }
                        if (checkPoints.stage == 3 && mad.cn == 6) {
                            n6 = 0;
                        }
                        if (checkPoints.stage == -1 && this.m.random() > this.m.random()) {
                            n6 = 0;
                        }
                        n5 = 0;
                        if (checkPoints.stage == 13 && mad.cn == 9) {
                            n5 = 1;
                        }
                        if (checkPoints.stage == 18 && mad.cn == 11) {
                            n5 = 1;
                        }
                        if (checkPoints.stage == 19 && checkPoints.clear[0] >= 20) {
                            n5 = 1;
                        }
                        if (checkPoints.stage == 4 || checkPoints.stage == 10 || checkPoints.stage == 21 || checkPoints.stage == 22 || checkPoints.stage == 23 || checkPoints.stage == 25 || checkPoints.stage == 26) {
                            n5 = 1;
                        }
                        if (checkPoints.stage == 3 && mad.cn == 6) {
                            n5 = 1;
                        }
                        n4 = 60;
                        if (checkPoints.stage == 5) {
                            n4 = 40;
                        }
                        if (checkPoints.stage == 6 && this.bulistc) {
                            n4 = 40;
                        }
                        if (checkPoints.stage == 9 && this.bulistc) {
                            n4 = 30;
                        }
                        if (checkPoints.stage == 3 || checkPoints.stage == 13 || checkPoints.stage == 21 || checkPoints.stage == 27 || checkPoints.stage == 20 || checkPoints.stage == 18) {
                            n4 = 30;
                        }
                        if ((checkPoints.stage == 12 || checkPoints.stage == 23) && mad.cn == 13) {
                            n4 = 50;
                        }
                        if (checkPoints.stage == 14) {
                            n4 = 20;
                        }
                        if (checkPoints.stage == 15 && mad.im != 6) {
                            n4 = 40;
                        }
                        if (checkPoints.stage == 17) {
                            n4 = 40;
                        }
                        if (checkPoints.stage == 18 && mad.cn == 11) {
                            n4 = 40;
                        }
                        if (checkPoints.stage == 19 && n5 != 0) {
                            n4 = 30;
                        }
                        if (checkPoints.stage == 21 && this.bulistc) {
                            n4 = 30;
                        }
                        if (checkPoints.stage == 22) {
                            n4 = 50;
                        }
                        if (checkPoints.stage == 25 && this.bulistc) {
                            n4 = 40;
                        }
                        if (checkPoints.stage == 26) {
                            if (mad.cn == 11 && checkPoints.clear[0] == 27) {
                                n4 = 0;
                            }
                            if (mad.cn == 15 || mad.cn == 9) {
                                n4 = 50;
                            }
                            if (mad.cn == 11) {
                                n4 = 40;
                            }
                            if (checkPoints.pos[0] > checkPoints.pos[mad.im]) {
                                n4 = 80;
                            }
                        }
                        for (n3 = 0; n3 < 7; ++n3) {
                            int n8;
                            if (n3 == mad.im || checkPoints.clear[n3] == -1) continue;
                            n2 = contO.xz;
                            if (this.zyinv) {
                                n2 += 180;
                            }
                            while (n2 < 0) {
                                n2 += 360;
                            }
                            while (n2 > 180) {
                                n2 -= 360;
                            }
                            n = 0;
                            if (checkPoints.opx[n3] - contO.x >= 0) {
                                n = 180;
                            }
                            for (n8 = (int)((double)(90 + n) + Math.atan((double)(checkPoints.opz[n3] - contO.z) / (double)(checkPoints.opx[n3] - contO.x)) / (Math.PI / 180)); n8 < 0; n8 += 360) {
                            }
                            while (n8 > 180) {
                                n8 -= 360;
                            }
                            int n9 = Math.abs(n2 - n8);
                            if (n9 > 180) {
                                n9 = Math.abs(n9 - 360);
                            }
                            int n10 = 2000 * (Math.abs(checkPoints.clear[n3] - mad.clear) + 1);
                            if ((checkPoints.stage == 6 || checkPoints.stage == 9) && this.bulistc) {
                                n10 = 6000;
                            }
                            if (checkPoints.stage == 3 && mad.cn == 6 && checkPoints.wasted < 2 && n10 > 4000) {
                                n10 = 4000;
                            }
                            if (checkPoints.stage == 13 && mad.cn == 9 && n10 < 12000) {
                                n10 = 12000;
                            }
                            if (checkPoints.stage == 14 && n10 < 4000) {
                                n10 = 4000;
                            }
                            if (checkPoints.stage == 18 && mad.cn == 11) {
                                if (n10 < 12000) {
                                    n10 = 12000;
                                }
                                n9 = 10;
                            }
                            if (checkPoints.stage == 19 && (mad.pcleared == 13 || mad.pcleared == 33 || n5 != 0) && n10 < 12000) {
                                n10 = 12000;
                            }
                            if (checkPoints.stage == 21) {
                                if (this.bulistc) {
                                    n10 = 8000;
                                    n9 = 10;
                                    this.afta = true;
                                } else if (n10 < 6000) {
                                    n10 = 6000;
                                }
                            }
                            if (checkPoints.stage == 22 && this.bulistc) {
                                n10 = 6000;
                                n9 = 10;
                            }
                            if (checkPoints.stage == 23) {
                                n10 = 21000;
                            }
                            if (checkPoints.stage == 25) {
                                n10 *= Math.abs(checkPoints.clear[n3] - mad.clear) + 1;
                                if (this.bulistc) {
                                    n10 = 4000 * (Math.abs(checkPoints.clear[n3] - mad.clear) + 1);
                                    n9 = 10;
                                }
                            }
                            if (checkPoints.stage == 20) {
                                n10 = 16000;
                            }
                            if (checkPoints.stage == 26) {
                                if (mad.cn == 13 && this.bulistc) {
                                    if (this.oupnt == 33) {
                                        n10 = 17000;
                                    }
                                    if (this.oupnt == 51) {
                                        n10 = 30000;
                                    }
                                    if (this.oupnt == 15 && checkPoints.clear[0] >= 14) {
                                        n10 = 60000;
                                    }
                                    n9 = 10;
                                }
                                if (mad.cn == 15 || mad.cn == 9) {
                                    n10 *= Math.abs(checkPoints.clear[n3] - mad.clear) + 1;
                                }
                                if (mad.cn == 11) {
                                    n10 = 4000 * (Math.abs(checkPoints.clear[n3] - mad.clear) + 1);
                                }
                            }
                            int n11 = 85 + 15 * (Math.abs(checkPoints.clear[n3] - mad.clear) + 1);
                            if (checkPoints.stage == 23) {
                                n11 = 45;
                            }
                            if (checkPoints.stage == 26 && (mad.cn == 15 || mad.cn == 9 || mad.cn == 11 || mad.cn == 14)) {
                                n11 = 50 + 70 * Math.abs(checkPoints.clear[n3] - mad.clear);
                            }
                            if (n9 < n11 && this.py(contO.x / 100, checkPoints.opx[n3] / 100, contO.z / 100, checkPoints.opz[n3] / 100) < n10 && this.afta && mad.power > (float)n4) {
                                float f2 = 35 - Math.abs(checkPoints.clear[n3] - mad.clear) * 10;
                                if (f2 < 1.0f) {
                                    f2 = 1.0f;
                                }
                                float f3 = (float)((checkPoints.pos[mad.im] + 1) * (5 - checkPoints.pos[n3])) / f2;
                                if (checkPoints.stage != 27 && (double)f3 > 0.7) {
                                    f3 = 0.7f;
                                }
                                if (n3 != 0 && checkPoints.pos[0] < checkPoints.pos[mad.im]) {
                                    f3 = 0.0f;
                                }
                                if (n3 != 0 && n5 != 0) {
                                    f3 = 0.0f;
                                }
                                if (n5 != 0 && checkPoints.stage == 3 && n3 == 0) {
                                    f3 = checkPoints.wasted >= 2 ? (f3 *= 0.5f) : 0.0f;
                                }
                                if ((checkPoints.stage == 3 || checkPoints.stage == 9) && n3 == 4) {
                                    f3 = 0.0f;
                                }
                                if (checkPoints.stage == 6) {
                                    f3 = 0.0f;
                                    if (this.bulistc && n3 == 0) {
                                        f3 = 1.0f;
                                    }
                                }
                                if (checkPoints.stage == 8) {
                                    f3 = 0.0f;
                                    if (this.bulistc && mad.cn != 11 && mad.cn != 13) {
                                        f3 = 1.0f;
                                    }
                                }
                                if (checkPoints.stage == 9 && mad.cn == 15) {
                                    f3 = 0.0f;
                                }
                                if (checkPoints.stage == 9 && this.bulistc) {
                                    f3 = n3 == 0 ? 1.0f : 0.0f;
                                }
                                if (checkPoints.stage == 9 && (checkPoints.pos[n3] == 4 || checkPoints.pos[n3] == 3)) {
                                    f3 = 0.0f;
                                }
                                if (checkPoints.stage == 13) {
                                    f3 = mad.cn == 9 || mad.cn == 13 && this.bulistc ? (f3 *= 2.0f) : (f3 *= 0.5f);
                                }
                                if (checkPoints.stage == 16) {
                                    f3 = 0.0f;
                                }
                                if (checkPoints.stage == 17 && mad.im == 6 && n3 == 0) {
                                    f3 = (float)((double)f3 * 1.5);
                                }
                                if (checkPoints.stage == 18) {
                                    f3 = mad.cn == 11 || mad.cn == 13 && this.bulistc ? (f3 *= 1.5f) : 0.0f;
                                }
                                if (checkPoints.stage == 19) {
                                    if (n3 != 0) {
                                        f3 = (float)((double)f3 * 0.5);
                                    }
                                    if (mad.pcleared != 13 && mad.pcleared != 33 && n5 == 0) {
                                        f3 *= 0.5f;
                                    }
                                    if ((mad.im == 6 || mad.im == 5) && n3 != 0) {
                                        f3 = 0.0f;
                                    }
                                }
                                if (checkPoints.stage == 20) {
                                    f3 = 0.0f;
                                    if (this.bulistc && mad.cn != 11 && mad.cn != 13) {
                                        f3 = 1.0f;
                                    }
                                }
                                if (checkPoints.stage == 21 && this.bulistc && n3 == 0) {
                                    f3 = 1.0f;
                                }
                                if (checkPoints.stage == 22) {
                                    if (mad.cn != 11 && mad.cn != 13) {
                                        f3 = 0.0f;
                                    }
                                    if (mad.cn == 13 && n3 == 0) {
                                        f3 = 1.0f;
                                    }
                                }
                                if (checkPoints.stage == 24) {
                                    f3 = 0.0f;
                                }
                                if (checkPoints.stage == 25) {
                                    if (checkPoints.pos[mad.im] == 0) {
                                        f3 = (float)((double)f3 * 0.5);
                                    }
                                    if (checkPoints.pos[0] < checkPoints.pos[mad.im]) {
                                        f3 *= 2.0f;
                                    }
                                    if (this.bulistc && n3 == 0) {
                                        f3 = 1.0f;
                                    }
                                }
                                if (checkPoints.stage == 26) {
                                    if (mad.cn != 14) {
                                        if (checkPoints.pos[0] < checkPoints.pos[mad.im] && checkPoints.clear[0] - checkPoints.clear[mad.im] != 1) {
                                            f3 *= 2.0f;
                                        }
                                    } else {
                                        f3 = (float)((double)f3 * 0.5);
                                    }
                                    if (mad.cn == 13 && n3 == 0) {
                                        f3 = 1.0f;
                                    }
                                    if (checkPoints.pos[mad.im] == 0 || checkPoints.pos[mad.im] == 1 && checkPoints.pos[0] == 0) {
                                        f3 = 0.0f;
                                    }
                                    if (checkPoints.clear[mad.im] - checkPoints.clear[0] >= 5 && n3 == 0) {
                                        f3 = 1.0f;
                                    }
                                    if (mad.cn == 10 || mad.cn == 12) {
                                        f3 = 0.0f;
                                    }
                                }
                                if (this.m.random() < f3) {
                                    this.attack = 40 * (Math.abs(checkPoints.clear[n3] - mad.clear) + 1);
                                    if (this.attack > 500) {
                                        this.attack = 500;
                                    }
                                    this.aim = 0.0f;
                                    if (checkPoints.stage == 13 && mad.cn == 9 && this.m.random() > this.m.random()) {
                                        this.aim = 1.0f;
                                    }
                                    if (checkPoints.stage == 14) {
                                        this.aim = n3 == 0 && checkPoints.pos[0] < checkPoints.pos[mad.im] ? 1.5f : this.m.random();
                                    }
                                    if (checkPoints.stage == 15) {
                                        this.aim = this.m.random() * 1.5f;
                                    }
                                    if (checkPoints.stage == 17 && mad.im != 6 && (this.m.random() > this.m.random() || checkPoints.pos[0] < checkPoints.pos[mad.im])) {
                                        this.aim = 1.0f;
                                    }
                                    if (checkPoints.stage == 18 && mad.cn == 11 && this.m.random() > this.m.random()) {
                                        this.aim = 0.76f + this.m.random() * 0.76f;
                                    }
                                    if (checkPoints.stage == 19 && (mad.pcleared == 13 || mad.pcleared == 33)) {
                                        this.aim = 1.0f;
                                    }
                                    if (checkPoints.stage == 21) {
                                        if (this.bulistc) {
                                            this.aim = 0.7f;
                                            if (this.attack > 150) {
                                                this.attack = 150;
                                            }
                                        } else {
                                            this.aim = this.m.random();
                                        }
                                    }
                                    if (checkPoints.stage == 22) {
                                        if (this.m.random() > this.m.random()) {
                                            this.aim = 0.7f;
                                        }
                                        if (this.bulistc && this.attack > 150) {
                                            this.attack = 150;
                                        }
                                    }
                                    if (checkPoints.stage == 23 && this.attack > 60) {
                                        this.attack = 60;
                                    }
                                    if (checkPoints.stage == 25) {
                                        this.aim = this.m.random() * 1.5f;
                                        this.attack /= 2;
                                        this.exitattack = this.m.random() > this.m.random();
                                    }
                                    if (checkPoints.stage == 26) {
                                        if (mad.cn == 13) {
                                            this.aim = 0.76f;
                                            this.attack = 150;
                                        } else {
                                            this.aim = this.m.random() * 1.5f;
                                            if (Math.abs(checkPoints.clear[n3] - mad.clear) <= 2 || mad.cn == 14) {
                                                this.attack /= 3;
                                            }
                                        }
                                    }
                                    if (checkPoints.stage == -1 && this.m.random() > this.m.random()) {
                                        this.aim = this.m.random() * 1.5f;
                                    }
                                    this.acr = n3;
                                    this.turntyp = (int)(1.0f + this.m.random() * 2.0f);
                                }
                            }
                            if (n6 == 0 || n9 <= 100 || this.py(contO.x / 100, checkPoints.opx[n3] / 100, contO.z / 100, checkPoints.opz[n3] / 100) >= 300 || !((double)this.m.random() > 0.6 - (double)((float)checkPoints.pos[mad.im] / 10.0f))) continue;
                            this.clrnce = 0;
                            this.acuracy = 0;
                        }
                    }
                    n6 = 0;
                    if (checkPoints.stage == 6 || checkPoints.stage == 8) {
                        n6 = 1;
                    }
                    if (checkPoints.stage == 9 && mad.cn == 15) {
                        n6 = 1;
                    }
                    if (checkPoints.stage == 16 || checkPoints.stage == 20 || checkPoints.stage == 21 || checkPoints.stage == 27) {
                        n6 = 1;
                    }
                    if (checkPoints.stage == 18 && mad.pcleared != 73) {
                        n6 = 1;
                    }
                    if (checkPoints.stage == -1 && this.m.random() > this.m.random()) {
                        n6 = 1;
                    }
                    if (this.trfix != 3) {
                        this.trfix = 0;
                        n5 = 50;
                        if (checkPoints.stage == 26) {
                            n5 = 40;
                        }
                        if (100.0f * (float)mad.hitmag / (float)mad.cd.maxmag[mad.cn] > (float)n5) {
                            this.trfix = 1;
                        }
                        if (n6 == 0) {
                            n4 = 80;
                            if (checkPoints.stage == 18 && mad.cn != 11) {
                                n4 = 50;
                            }
                            if (checkPoints.stage == 19) {
                                n4 = 70;
                            }
                            if (checkPoints.stage == 25 && mad.pcleared == 91) {
                                n4 = 50;
                            }
                            if (checkPoints.stage == 26 && checkPoints.clear[mad.im] - checkPoints.clear[0] >= 5 && mad.cn != 10 && mad.cn != 12) {
                                n4 = 50;
                            }
                            if (100.0f * (float)mad.hitmag / (float)mad.cd.maxmag[mad.cn] > (float)n4) {
                                this.trfix = 2;
                            }
                        }
                    } else {
                        this.upwait = 0;
                        this.acuracy = 0;
                        this.skiplev = 1.0f;
                        this.clrnce = 2;
                    }
                    if (!this.bulistc) {
                        if (checkPoints.stage == 18 && mad.cn == 11 && mad.pcleared == 35) {
                            mad.pcleared = 73;
                            mad.clear = 0;
                            this.bulistc = true;
                            this.runbul = (int)(100.0f * this.m.random());
                        }
                        if (checkPoints.stage == 21 && mad.cn == 13) {
                            this.bulistc = true;
                        }
                        if (checkPoints.stage == 22 && mad.cn == 13) {
                            this.bulistc = true;
                        }
                        if (checkPoints.stage == 25 && checkPoints.clear[0] - mad.clear >= 3 && this.trfix == 0) {
                            this.bulistc = true;
                            this.oupnt = -1;
                        }
                        if (checkPoints.stage == 26) {
                            if (mad.cn == 13 && checkPoints.pcleared == 8) {
                                this.bulistc = true;
                                this.attack = 0;
                            }
                            if (mad.cn == 11 && checkPoints.clear[0] - mad.clear >= 2 && this.trfix == 0) {
                                this.bulistc = true;
                                this.oupnt = -1;
                            }
                        }
                        if ((checkPoints.stage == 6 || checkPoints.stage == 8 || checkPoints.stage == 12 || checkPoints.stage == 13 || checkPoints.stage == 14 || checkPoints.stage == 15 || checkPoints.stage == 18 || checkPoints.stage == 20 || checkPoints.stage == 23) && mad.cn == 13 && Math.abs(checkPoints.clear[0] - mad.clear) >= 2) {
                            this.bulistc = true;
                        }
                        if ((checkPoints.stage == 8 || checkPoints.stage == 20) && mad.cn == 11 && Math.abs(checkPoints.clear[0] - mad.clear) >= 1) {
                            this.bulistc = true;
                        }
                        if (checkPoints.stage == 6 && mad.cn == 11) {
                            this.bulistc = true;
                        }
                        if (checkPoints.stage == 9 && this.afta && (checkPoints.pos[mad.im] == 4 || checkPoints.pos[mad.im] == 3) && mad.cn != 15 && this.trfix != 0) {
                            this.bulistc = true;
                        }
                    } else if (checkPoints.stage == 18) {
                        --this.runbul;
                        if (mad.pcleared == 10) {
                            this.runbul = 0;
                        }
                        if (this.runbul <= 0) {
                            this.bulistc = false;
                        }
                    }
                    this.stcnt = 0;
                    this.statusque = (int)(20.0f * this.m.random());
                } else {
                    ++this.stcnt;
                }
            }
            n7 = 0;
            n7 = this.usebounce ? (int)(mad.wtouch ? 1 : 0) : (int)(mad.mtouch ? 1 : 0);
            if (n7 != 0) {
                int n12;
                if (this.trickfase != 0) {
                    this.trickfase = 0;
                }
                if (this.trfix == 2 || this.trfix == 3) {
                    this.attack = 0;
                }
                if (this.attack == 0) {
                    if (this.upcnt < 30) {
                        if (this.revstart <= 0) {
                            this.up = true;
                        } else {
                            this.down = true;
                            --this.revstart;
                        }
                    }
                    if (this.upcnt < 25 + this.actwait) {
                        ++this.upcnt;
                    } else {
                        this.upcnt = 0;
                        this.actwait = this.upwait;
                    }
                    n12 = mad.point;
                    n6 = 50;
                    if (checkPoints.stage == 9) {
                        n6 = 20;
                    }
                    if (checkPoints.stage == 18) {
                        n6 = 20;
                    }
                    if (checkPoints.stage == 25) {
                        n6 = 40;
                    }
                    if (checkPoints.stage == 26) {
                        n6 = 20;
                    }
                    if (!this.bulistc || this.trfix == 2 || this.trfix == 3 || this.trfix == 4 || mad.power < (float)n6) {
                        if (this.rampp == 1 && checkPoints.typ[n12] <= 0) {
                            n5 = n12 + 1;
                            if (n5 >= checkPoints.n) {
                                n5 = 0;
                            }
                            if (checkPoints.typ[n5] == -2) {
                                n12 = n5;
                            }
                        }
                        if (this.rampp == -1 && checkPoints.typ[n12] == -2 && ++n12 >= checkPoints.n) {
                            n12 = 0;
                        }
                        if (this.m.random() > this.skiplev) {
                            n5 = n12;
                            n4 = 0;
                            if (checkPoints.typ[n5] > 0) {
                                n3 = 0;
                                for (n2 = 0; n2 < checkPoints.n; ++n2) {
                                    if (checkPoints.typ[n2] <= 0 || n2 >= n5) continue;
                                    ++n3;
                                }
                                int n13 = n4 = mad.clear != n3 + mad.nlaps * checkPoints.nsp ? 1 : 0;
                            }
                            while (checkPoints.typ[n5] == 0 || checkPoints.typ[n5] == -1 || checkPoints.typ[n5] == -3 || n4 != 0) {
                                n12 = n5++;
                                if (n5 >= checkPoints.n) {
                                    n5 = 0;
                                }
                                n4 = 0;
                                if (checkPoints.typ[n5] <= 0) continue;
                                n3 = 0;
                                for (n2 = 0; n2 < checkPoints.n; ++n2) {
                                    if (checkPoints.typ[n2] <= 0 || n2 >= n5) continue;
                                    ++n3;
                                }
                                n4 = mad.clear != n3 + mad.nlaps * checkPoints.nsp ? 1 : 0;
                            }
                        } else if (this.m.random() > this.skiplev) {
                            while (checkPoints.typ[n12] == -1) {
                                if (++n12 < checkPoints.n) continue;
                                n12 = 0;
                            }
                        }
                        if (checkPoints.stage == 18 && mad.pcleared == 73 && this.trfix == 0 && mad.clear != 0) {
                            n12 = 10;
                        }
                        if (checkPoints.stage == 19 && mad.pcleared == 18 && this.trfix == 0) {
                            n12 = 27;
                        }
                        if (checkPoints.stage == 21) {
                            if (mad.pcleared == 5 && this.trfix == 0 && mad.power < 70.0f) {
                                n12 = n12 <= 16 ? 16 : 21;
                            }
                            if (mad.pcleared == 50) {
                                n12 = 57;
                            }
                        }
                        if (checkPoints.stage == 22 && (mad.pcleared == 27 || mad.pcleared == 37)) {
                            while (checkPoints.typ[n12] == -1) {
                                if (++n12 < checkPoints.n) continue;
                                n12 = 0;
                            }
                        }
                        if (checkPoints.stage == 23) {
                            while (checkPoints.typ[n12] == -1) {
                                if (++n12 < checkPoints.n) continue;
                                n12 = 0;
                            }
                        }
                        if (checkPoints.stage == 24) {
                            while (checkPoints.typ[n12] == -1) {
                                if (++n12 < checkPoints.n) continue;
                                n12 = 0;
                            }
                            if (!mad.gtouch) {
                                while (checkPoints.typ[n12] == -2) {
                                    if (++n12 < checkPoints.n) continue;
                                    n12 = 0;
                                }
                            }
                            if (this.oupnt >= 68) {
                                n12 = 70;
                            } else {
                                this.oupnt = n12;
                            }
                        }
                        if (checkPoints.stage == 25) {
                            if (mad.pcleared != 91 && checkPoints.pos[0] < checkPoints.pos[mad.im] && mad.cn != 13 || checkPoints.pos[mad.im] == 0 && (mad.clear == 12 || mad.clear == 20)) {
                                while (checkPoints.typ[n12] == -4) {
                                    if (++n12 < checkPoints.n) continue;
                                    n12 = 0;
                                }
                            }
                            if (mad.pcleared == 9) {
                                if (this.py(contO.x / 100, 297, contO.z / 100, 347) < 400) {
                                    this.oupnt = 1;
                                }
                                if (this.oupnt == 1 && n12 < 22) {
                                    n12 = 22;
                                }
                            }
                            if (mad.pcleared == 67) {
                                if (this.py(contO.x / 100, 28, contO.z / 100, 494) < 4000) {
                                    this.oupnt = 2;
                                }
                                if (this.oupnt == 2) {
                                    n12 = 76;
                                }
                            }
                            if (mad.pcleared == 76) {
                                if (this.py(contO.x / 100, -50, contO.z / 100, 0) < 2000) {
                                    this.oupnt = 3;
                                }
                                n12 = this.oupnt == 3 ? 91 : 89;
                            }
                        }
                        if (checkPoints.stage == 26) {
                            if (mad.pcleared == 128) {
                                if (this.py(contO.x / 100, 0, contO.z / 100, 229) < 1500 || contO.z > 23000) {
                                    this.oupnt = 128;
                                }
                                if (this.oupnt != 128) {
                                    n12 = 3;
                                }
                            }
                            if (mad.pcleared == 8) {
                                if (this.py(contO.x / 100, -207, contO.z / 100, 549) < 1500 || contO.x < -20700) {
                                    this.oupnt = 8;
                                }
                                if (this.oupnt != 8) {
                                    n12 = 12;
                                }
                            }
                            if (mad.pcleared == 33) {
                                if (this.py(contO.x / 100, -60, contO.z / 100, 168) < 250 || contO.z > 17000) {
                                    this.oupnt = 331;
                                }
                                if (this.py(contO.x / 100, -112, contO.z / 100, 414) < 10000 || contO.z > 40000) {
                                    this.oupnt = 332;
                                }
                                if (this.oupnt != 331 && this.oupnt != 332) {
                                    n12 = this.trfix != 1 ? 38 : 39;
                                }
                                if (this.oupnt == 331) {
                                    n12 = 71;
                                }
                            }
                            if (mad.pcleared == 42) {
                                if (this.py(contO.x / 100, -269, contO.z / 100, 493) < 100 || contO.x < -27000) {
                                    this.oupnt = 142;
                                }
                                if (this.oupnt != 142) {
                                    n12 = 47;
                                }
                            }
                            if (mad.pcleared == 51) {
                                if (this.py(contO.x / 100, -352, contO.z / 100, 260) < 100 || contO.z < 25000) {
                                    this.oupnt = 511;
                                }
                                if (this.py(contO.x / 100, -325, contO.z / 100, 10) < 2000 || contO.x > -32000) {
                                    this.oupnt = 512;
                                }
                                if (this.oupnt != 511 && this.oupnt != 512) {
                                    n12 = 80;
                                }
                                if (this.oupnt == 511) {
                                    n12 = 61;
                                }
                            }
                            if (mad.pcleared == 77) {
                                if (this.py(contO.x / 100, -371, contO.z / 100, 319) < 100 || contO.z < 31000) {
                                    this.oupnt = 77;
                                }
                                if (this.oupnt != 77) {
                                    n12 = 78;
                                    mad.nofocus = true;
                                }
                            }
                            if (mad.pcleared == 105) {
                                if (this.py(contO.x / 100, -179, contO.z / 100, 10) < 2300 || contO.z < 1050) {
                                    this.oupnt = 105;
                                }
                                n12 = this.oupnt != 105 ? 65 : 125;
                            }
                            if (this.trfix == 3) {
                                if (this.py(contO.x / 100, -52, contO.z / 100, 448) < 100 || contO.z > 45000) {
                                    this.oupnt = 176;
                                }
                                n12 = this.oupnt != 176 ? 41 : 43;
                            }
                            if (checkPoints.clear[mad.im] - checkPoints.clear[0] >= 2 && this.py(contO.x / 100, checkPoints.opx[0] / 100, contO.z / 100, checkPoints.opz[0] / 100) < 1000 + this.avoidnlev) {
                                n4 = contO.xz;
                                if (this.zyinv) {
                                    n4 += 180;
                                }
                                while (n4 < 0) {
                                    n4 += 360;
                                }
                                while (n4 > 180) {
                                    n4 -= 360;
                                }
                                n3 = 0;
                                if (checkPoints.opx[0] - contO.x >= 0) {
                                    n3 = 180;
                                }
                                for (n2 = (int)((double)(90 + n3) + Math.atan((double)(checkPoints.opz[0] - contO.z) / (double)(checkPoints.opx[0] - contO.x)) / (Math.PI / 180)); n2 < 0; n2 += 360) {
                                }
                                while (n2 > 180) {
                                    n2 -= 360;
                                }
                                n = Math.abs(n4 - n2);
                                if (n > 180) {
                                    n = Math.abs(n - 360);
                                }
                                if (n < 90) {
                                    this.wall = 0;
                                }
                            }
                        }
                        if (this.rampp == 2) {
                            n5 = n12 + 1;
                            if (n5 >= checkPoints.n) {
                                n5 = 0;
                            }
                            if (checkPoints.typ[n5] == -2 && n12 != mad.point && --n12 < 0) {
                                n12 += checkPoints.n;
                            }
                        }
                        if (this.bulistc) {
                            mad.nofocus = true;
                            if (this.gowait) {
                                this.gowait = false;
                            }
                        }
                    } else {
                        if (checkPoints.stage != 25 && checkPoints.stage != 26 || this.runbul == 0) {
                            if ((n12 -= 2) < 0) {
                                n12 += checkPoints.n;
                            }
                            if (checkPoints.stage == 9 && n12 > 76) {
                                n12 = 76;
                            }
                            while (checkPoints.typ[n12] == -4) {
                                if (--n12 >= 0) continue;
                                n12 += checkPoints.n;
                            }
                        }
                        if (checkPoints.stage == 21) {
                            if (n12 >= 14 && n12 <= 19) {
                                n12 = 13;
                            }
                            if (this.oupnt == 72 && n12 != 56) {
                                n12 = 57;
                            } else if (this.oupnt == 54 && n12 != 52) {
                                n12 = 53;
                            } else if (this.oupnt == 39 && n12 != 37) {
                                n12 = 38;
                            } else {
                                this.oupnt = n12;
                            }
                        }
                        if (checkPoints.stage == 22) {
                            if (!this.gowait) {
                                if (checkPoints.clear[0] == 0) {
                                    this.wtx = -3500;
                                    this.wtz = 19000;
                                    this.frx = -3500;
                                    this.frz = 39000;
                                    this.frad = 12000;
                                    this.oupnt = 37;
                                    this.gowait = true;
                                    this.afta = false;
                                }
                                if (checkPoints.clear[0] == 7) {
                                    this.wtx = -44800;
                                    this.wtz = 40320;
                                    this.frx = -44800;
                                    this.frz = 34720;
                                    this.frad = 30000;
                                    this.oupnt = 27;
                                    this.gowait = true;
                                    this.afta = false;
                                }
                                if (checkPoints.clear[0] == 10) {
                                    this.wtx = 0;
                                    this.wtz = 48739;
                                    this.frx = 0;
                                    this.frz = 38589;
                                    this.frad = 90000;
                                    this.oupnt = 55;
                                    this.gowait = true;
                                    this.afta = false;
                                }
                                if (checkPoints.clear[0] == 14) {
                                    this.wtx = -3500;
                                    this.wtz = 19000;
                                    this.frx = -14700;
                                    this.frz = 39000;
                                    this.frad = 45000;
                                    this.oupnt = 37;
                                    this.gowait = true;
                                    this.afta = false;
                                }
                                if (checkPoints.clear[0] == 18) {
                                    this.wtx = -48300;
                                    this.wtz = -4550;
                                    this.frx = -48300;
                                    this.frz = 5600;
                                    this.frad = 90000;
                                    this.oupnt = 17;
                                    this.gowait = true;
                                    this.afta = false;
                                }
                            }
                            if (this.gowait) {
                                if (this.py(contO.x / 100, this.wtx / 100, contO.z / 100, this.wtz / 100) < 10000 && mad.speed > 50.0f) {
                                    this.up = false;
                                }
                                if (this.py(contO.x / 100, this.wtx / 100, contO.z / 100, this.wtz / 100) < 200) {
                                    this.up = false;
                                    this.handb = true;
                                }
                                if (checkPoints.pcleared == this.oupnt && this.py(checkPoints.opx[0] / 100, this.frx / 100, checkPoints.opz[0] / 100, this.frz / 100) < this.frad) {
                                    this.afta = true;
                                    this.gowait = false;
                                }
                                if (this.py(contO.x / 100, checkPoints.opx[0] / 100, contO.z / 100, checkPoints.opz[0] / 100) < 25) {
                                    this.afta = true;
                                    this.gowait = false;
                                    this.attack = 200;
                                    this.acr = 0;
                                }
                            }
                        }
                        if (checkPoints.stage == 25) {
                            if (this.oupnt == -1) {
                                n5 = -10;
                                for (n4 = 0; n4 < checkPoints.n; ++n4) {
                                    if (checkPoints.typ[n4] != -2 && checkPoints.typ[n4] != -4 || n4 >= 50 && n4 <= 54 || this.py(contO.x / 100, checkPoints.x[n4] / 100, contO.z / 100, checkPoints.z[n4] / 100) >= n5 && n5 != -10) continue;
                                    n5 = this.py(contO.x / 100, checkPoints.x[n4] / 100, contO.z / 100, checkPoints.z[n4] / 100);
                                    this.oupnt = n4;
                                }
                                --this.oupnt;
                                if (n12 < 0) {
                                    this.oupnt += checkPoints.n;
                                }
                            }
                            if (this.oupnt >= 0 && this.oupnt < checkPoints.n && this.py(contO.x / 100, checkPoints.x[n12 = this.oupnt] / 100, contO.z / 100, checkPoints.z[n12] / 100) < 800) {
                                this.oupnt = -((int)(75.0f + this.m.random() * 200.0f));
                                this.runbul = (int)(50.0f + this.m.random() * 100.0f);
                            }
                            if (this.oupnt < -1) {
                                ++this.oupnt;
                            }
                            if (this.runbul != 0) {
                                --this.runbul;
                            }
                        }
                        if (checkPoints.stage == 26) {
                            n5 = 0;
                            if (mad.cn == 13) {
                                if (!this.gowait) {
                                    if (checkPoints.clear[0] == 1) {
                                        if ((double)this.m.random() > 0.5) {
                                            this.wtx = -14000;
                                            this.wtz = 48000;
                                            this.frx = -5600;
                                            this.frz = 47600;
                                            this.frad = 88000;
                                            this.oupnt = 33;
                                        } else {
                                            this.wtx = -5600;
                                            this.wtz = 8000;
                                            this.frx = -7350;
                                            this.frz = -4550;
                                            this.frad = 22000;
                                            this.oupnt = 15;
                                        }
                                        this.gowait = true;
                                        this.afta = false;
                                    }
                                    if (checkPoints.clear[0] == 4) {
                                        this.wtx = -12700;
                                        this.wtz = 14000;
                                        this.frx = -31000;
                                        this.frz = 1050;
                                        this.frad = 11000;
                                        this.oupnt = 51;
                                        this.gowait = true;
                                        this.afta = false;
                                    }
                                    if (checkPoints.clear[0] == 14) {
                                        this.wtx = -35350;
                                        this.wtz = 6650;
                                        this.frx = -48300;
                                        this.frz = 54950;
                                        this.frad = 11000;
                                        this.oupnt = 15;
                                        this.gowait = true;
                                        this.afta = false;
                                    }
                                    if (checkPoints.clear[0] == 17) {
                                        this.wtx = -42700;
                                        this.wtz = 41000;
                                        this.frx = -40950;
                                        this.frz = 49350;
                                        this.frad = 7000;
                                        this.oupnt = 42;
                                        this.gowait = true;
                                        this.afta = false;
                                    }
                                    if (checkPoints.clear[0] == 21) {
                                        this.wtx = -1750;
                                        this.wtz = -15750;
                                        this.frx = -25900;
                                        this.frz = -14000;
                                        this.frad = 11000;
                                        this.oupnt = 125;
                                        this.gowait = true;
                                        this.afta = false;
                                    }
                                }
                                if (this.gowait) {
                                    if (this.py(contO.x / 100, this.wtx / 100, contO.z / 100, this.wtz / 100) < 10000 && mad.speed > 50.0f) {
                                        this.up = false;
                                    }
                                    if (this.py(contO.x / 100, this.wtx / 100, contO.z / 100, this.wtz / 100) < 200) {
                                        this.up = false;
                                        this.handb = true;
                                    }
                                    if (checkPoints.pcleared == this.oupnt && this.py(checkPoints.opx[0] / 100, this.frx / 100, checkPoints.opz[0] / 100, this.frz / 100) < this.frad) {
                                        this.runbul = 0;
                                        this.afta = true;
                                        this.gowait = false;
                                    }
                                    if (this.py(contO.x / 100, checkPoints.opx[0] / 100, contO.z / 100, checkPoints.opz[0] / 100) < 25) {
                                        this.afta = true;
                                        this.gowait = false;
                                        this.attack = 200;
                                        this.acr = 0;
                                    }
                                    if (checkPoints.clear[0] == 21 && this.oupnt != 125) {
                                        this.gowait = false;
                                    }
                                }
                                if (checkPoints.clear[0] >= 11 && !this.gowait || mad.power < 60.0f && checkPoints.clear[0] < 21) {
                                    n5 = 1;
                                    if (!this.exitattack) {
                                        this.oupnt = -1;
                                        this.exitattack = true;
                                    }
                                } else if (this.exitattack) {
                                    this.exitattack = false;
                                }
                            }
                            if (mad.cn == 11) {
                                n5 = 1;
                            }
                            if (n5 != 0) {
                                if (this.oupnt == -1) {
                                    n4 = -10;
                                    for (n3 = 0; n3 < checkPoints.n; ++n3) {
                                        if (checkPoints.typ[n3] != -4 || !(this.py(contO.x / 100, checkPoints.x[n3] / 100, contO.z / 100, checkPoints.z[n3] / 100) < n4 && (double)this.m.random() > 0.6) && n4 != -10) continue;
                                        n4 = this.py(contO.x / 100, checkPoints.x[n3] / 100, contO.z / 100, checkPoints.z[n3] / 100);
                                        this.oupnt = n3;
                                    }
                                    --this.oupnt;
                                    if (n12 < 0) {
                                        this.oupnt += checkPoints.n;
                                    }
                                }
                                if (this.oupnt >= 0 && this.oupnt < checkPoints.n && this.py(contO.x / 100, checkPoints.x[n12 = this.oupnt] / 100, contO.z / 100, checkPoints.z[n12] / 100) < 800) {
                                    this.oupnt = -((int)(75.0f + this.m.random() * 200.0f));
                                    this.runbul = (int)(50.0f + this.m.random() * 100.0f);
                                }
                                if (this.oupnt < -1) {
                                    ++this.oupnt;
                                }
                                if (this.runbul != 0) {
                                    --this.runbul;
                                }
                            }
                        }
                        mad.nofocus = true;
                    }
                    if (checkPoints.stage != 27) {
                        if (checkPoints.stage == 10 || checkPoints.stage == 19 || checkPoints.stage == 18 && mad.pcleared == 73 || checkPoints.stage == 26) {
                            this.forget = true;
                        }
                        if ((mad.missedcp == 0 || this.forget || this.trfix == 4) && this.trfix != 0) {
                            n5 = 0;
                            if (checkPoints.stage == 25 || checkPoints.stage == 26) {
                                n5 = 3;
                            }
                            if (this.trfix == 2) {
                                n4 = -10;
                                n3 = 0;
                                for (n2 = n5; n2 < checkPoints.fn; ++n2) {
                                    if (this.py(contO.x / 100, checkPoints.x[this.fpnt[n2]] / 100, contO.z / 100, checkPoints.z[this.fpnt[n2]] / 100) >= n4 && n4 != -10) continue;
                                    n4 = this.py(contO.x / 100, checkPoints.x[this.fpnt[n2]] / 100, contO.z / 100, checkPoints.z[this.fpnt[n2]] / 100);
                                    n3 = n2;
                                }
                                if (checkPoints.stage == 18 || checkPoints.stage == 22) {
                                    n3 = 1;
                                }
                                n12 = this.fpnt[n3];
                                this.forget = checkPoints.special[n3];
                            }
                            for (n4 = n5; n4 < checkPoints.fn; ++n4) {
                                if (this.py(contO.x / 100, checkPoints.x[this.fpnt[n4]] / 100, contO.z / 100, checkPoints.z[this.fpnt[n4]] / 100) >= 2000) continue;
                                this.forget = false;
                                this.actwait = 0;
                                this.upwait = 0;
                                this.turntyp = 2;
                                this.randtcnt = -1;
                                this.acuracy = 0;
                                this.rampp = 0;
                                this.trfix = 3;
                            }
                            if (this.trfix == 3) {
                                mad.nofocus = true;
                            }
                        }
                    }
                    if (this.turncnt > this.randtcnt) {
                        if (!this.gowait) {
                            n5 = 0;
                            if (checkPoints.x[n12] - contO.x >= 0) {
                                n5 = 180;
                            }
                            this.pan = (int)((double)(90 + n5) + Math.atan((double)(checkPoints.z[n12] - contO.z) / (double)(checkPoints.x[n12] - contO.x)) / (Math.PI / 180));
                        } else {
                            n5 = 0;
                            if (this.wtx - contO.x >= 0) {
                                n5 = 180;
                            }
                            this.pan = (int)((double)(90 + n5) + Math.atan((double)(this.wtz - contO.z) / (double)(this.wtx - contO.x)) / (Math.PI / 180));
                        }
                        this.turncnt = 0;
                        this.randtcnt = (int)((float)this.acuracy * this.m.random());
                    } else {
                        ++this.turncnt;
                    }
                } else {
                    this.up = true;
                    n12 = 0;
                    n6 = (int)((float)this.pys(contO.x, checkPoints.opx[this.acr], contO.z, checkPoints.opz[this.acr]) / 2.0f * this.aim);
                    n5 = (int)((float)checkPoints.opx[this.acr] - (float)n6 * this.m.sin(checkPoints.omxz[this.acr]));
                    n4 = (int)((float)checkPoints.opz[this.acr] + (float)n6 * this.m.cos(checkPoints.omxz[this.acr]));
                    if (n5 - contO.x >= 0) {
                        n12 = 180;
                    }
                    this.pan = (int)((double)(90 + n12) + Math.atan((double)(n4 - contO.z) / (double)(n5 - contO.x)) / (Math.PI / 180));
                    --this.attack;
                    if (this.attack <= 0) {
                        this.attack = 0;
                    }
                    if (checkPoints.stage == 25 && this.exitattack && !this.bulistc && mad.missedcp != 0) {
                        this.attack = 0;
                    }
                    if (checkPoints.stage == 26 && mad.cn == 13 && (checkPoints.clear[0] == 4 || checkPoints.clear[0] == 13 || checkPoints.clear[0] == 21)) {
                        this.attack = 0;
                    }
                    if (checkPoints.stage == 26 && mad.missedcp != 0 && (checkPoints.pos[mad.im] == 0 || checkPoints.pos[mad.im] == 1 && checkPoints.pos[0] == 0)) {
                        this.attack = 0;
                    }
                    if (checkPoints.stage == 26 && checkPoints.pos[0] > checkPoints.pos[mad.im] && mad.power < 80.0f) {
                        this.attack = 0;
                    }
                }
                n12 = contO.xz;
                if (this.zyinv) {
                    n12 += 180;
                }
                while (n12 < 0) {
                    n12 += 360;
                }
                while (n12 > 180) {
                    n12 -= 360;
                }
                while (this.pan < 0) {
                    this.pan += 360;
                }
                while (this.pan > 180) {
                    this.pan -= 360;
                }
                if (this.wall != -1 && this.hold == 0) {
                    this.clrnce = 0;
                }
                if (this.hold == 0) {
                    if (Math.abs(n12 - this.pan) < 180) {
                        if (Math.abs(n12 - this.pan) > this.clrnce) {
                            if (n12 < this.pan) {
                                this.left = true;
                                this.lastl = true;
                            } else {
                                this.right = true;
                                this.lastl = false;
                            }
                            if (Math.abs(n12 - this.pan) > 50 && mad.speed > (float)mad.cd.swits[mad.cn][0] && this.turntyp != 0) {
                                if (this.turntyp == 1) {
                                    this.down = true;
                                }
                                if (this.turntyp == 2) {
                                    this.handb = true;
                                }
                                if (!this.agressed) {
                                    this.up = false;
                                }
                            }
                        }
                    } else if (Math.abs(n12 - this.pan) < 360 - this.clrnce) {
                        if (n12 < this.pan) {
                            this.right = true;
                            this.lastl = false;
                        } else {
                            this.left = true;
                            this.lastl = true;
                        }
                        if (Math.abs(n12 - this.pan) < 310 && mad.speed > (float)mad.cd.swits[mad.cn][0] && this.turntyp != 0) {
                            if (this.turntyp == 1) {
                                this.down = true;
                            }
                            if (this.turntyp == 2) {
                                this.handb = true;
                            }
                            if (!this.agressed) {
                                this.up = false;
                            }
                        }
                    }
                }
                if (checkPoints.stage == 24 && this.wall != -1) {
                    if (trackers.dam[this.wall] == 0 || mad.pcleared == 45) {
                        this.wall = -1;
                    }
                    if (mad.pcleared == 58 && checkPoints.opz[mad.im] < 36700) {
                        this.wall = -1;
                        this.hold = 0;
                    }
                }
                if (this.wall != -1) {
                    if (this.lwall != this.wall) {
                        if (this.lastl) {
                            this.left = true;
                        } else {
                            this.right = true;
                        }
                        this.wlastl = this.lastl;
                        this.lwall = this.wall;
                    } else if (this.wlastl) {
                        this.left = true;
                    } else {
                        this.right = true;
                    }
                    if (trackers.dam[this.wall] != 0) {
                        n6 = 1;
                        if (trackers.skd[this.wall] == 1) {
                            n6 = 3;
                        }
                        this.hold += n6;
                        if (this.hold > 10 * n6) {
                            this.hold = 10 * n6;
                        }
                    } else {
                        this.hold = 1;
                    }
                    this.wall = -1;
                } else if (this.hold != 0) {
                    --this.hold;
                }
            } else {
                if (this.trickfase == 0) {
                    int n14 = (int)((mad.scy[0] + mad.scy[1] + mad.scy[2] + mad.scy[3]) * (float)(contO.y - 300) / 4000.0f);
                    n6 = 3;
                    if (checkPoints.stage == 25) {
                        n6 = 10;
                    }
                    if (n14 > 7 && (this.m.random() > this.trickprf / (float)n6 || this.stuntf == 4 || this.stuntf == 3 || this.stuntf == 5 || this.stuntf == 6 || checkPoints.stage == 26)) {
                        this.oxy = mad.pxy;
                        this.ozy = mad.pzy;
                        this.flycnt = 0;
                        this.uddirect = 0;
                        this.lrdirect = 0;
                        this.udswt = false;
                        this.lrswt = false;
                        this.trickfase = 1;
                        if (n14 < 16) {
                            if (this.stuntf != 6) {
                                this.uddirect = -1;
                                this.udstart = 0;
                                this.udswt = false;
                            } else if (this.oupnt != 70) {
                                this.uddirect = 1;
                                this.udstart = 0;
                                this.udswt = false;
                            }
                        } else if (this.m.random() > this.m.random() && this.stuntf != 1 || this.stuntf == 4 || this.stuntf == 6 || this.stuntf == 7 || this.stuntf == 17) {
                            this.uddirect = (this.m.random() > this.m.random() || this.stuntf == 2 || this.stuntf == 7) && this.stuntf != 4 && this.stuntf != 6 ? -1 : 1;
                            this.udstart = (int)(10.0f * this.m.random() * this.trickprf);
                            if (this.stuntf == 6) {
                                this.udstart = 0;
                            }
                            if (checkPoints.stage == 26) {
                                this.udstart = 0;
                            }
                            if (checkPoints.stage == 24 && (this.oupnt == 68 || this.oupnt == 69)) {
                                this.apunch = 20;
                                this.oupnt = 70;
                            }
                            if ((double)this.m.random() > 0.85 && this.stuntf != 4 && this.stuntf != 3 && this.stuntf != 6 && this.stuntf != 17 && checkPoints.stage != 26) {
                                this.udswt = true;
                            }
                            if (this.m.random() > this.trickprf + 0.3f && this.stuntf != 4 && this.stuntf != 6) {
                                this.lrdirect = this.m.random() > this.m.random() ? -1 : 1;
                                this.lrstart = (int)(30.0f * this.m.random());
                                if ((double)this.m.random() > 0.75) {
                                    this.lrswt = true;
                                }
                            }
                        } else {
                            this.lrdirect = this.m.random() > this.m.random() ? -1 : 1;
                            this.lrstart = (int)(10.0f * this.m.random() * this.trickprf);
                            if ((double)this.m.random() > 0.75 && checkPoints.stage != 26) {
                                this.lrswt = true;
                            }
                            if (this.m.random() > this.trickprf + 0.3f) {
                                this.uddirect = this.m.random() > this.m.random() ? -1 : 1;
                                this.udstart = (int)(30.0f * this.m.random());
                                if ((double)this.m.random() > 0.85) {
                                    this.udswt = true;
                                }
                            }
                        }
                        if (this.trfix == 3 || this.trfix == 4) {
                            if (checkPoints.stage != 18 && checkPoints.stage != 8) {
                                if (checkPoints.stage != 25 && this.lrdirect == -1) {
                                    this.uddirect = checkPoints.stage != 19 ? -1 : 1;
                                }
                                this.lrdirect = 0;
                                if ((checkPoints.stage == 19 || checkPoints.stage == 25) && this.uddirect == -1) {
                                    this.uddirect = 1;
                                }
                                if (mad.power < 60.0f) {
                                    this.uddirect = -1;
                                }
                            } else {
                                if (this.uddirect != 0) {
                                    this.uddirect = -1;
                                }
                                this.lrdirect = 0;
                            }
                            if (checkPoints.stage == 20) {
                                this.uddirect = 1;
                                this.lrdirect = 0;
                            }
                            if (checkPoints.stage == 26) {
                                this.uddirect = -1;
                                this.lrdirect = 0;
                                if (mad.cn != 11 && mad.cn != 13) {
                                    this.udstart = 7;
                                    if (mad.cn == 14 && mad.power > 30.0f) {
                                        this.udstart = 14;
                                    }
                                } else {
                                    this.udstart = 0;
                                }
                                if (mad.cn == 11) {
                                    this.lrdirect = -1;
                                    this.lrstart = 0;
                                }
                            }
                        }
                    } else {
                        this.trickfase = -1;
                    }
                    if (!this.afta) {
                        this.afta = true;
                    }
                    if (this.trfix == 3) {
                        this.trfix = 4;
                        this.statusque += 30;
                    }
                }
                if (this.trickfase == 1) {
                    ++this.flycnt;
                    if (this.lrdirect != 0 && this.flycnt > this.lrstart) {
                        if (this.lrswt && Math.abs(mad.pxy - this.oxy) > 180) {
                            this.lrdirect = this.lrdirect == -1 ? 1 : -1;
                            this.lrswt = false;
                        }
                        if (this.lrdirect == -1) {
                            this.handb = true;
                            this.left = true;
                        } else {
                            this.handb = true;
                            this.right = true;
                        }
                    }
                    if (this.uddirect != 0 && this.flycnt > this.udstart) {
                        if (this.udswt && Math.abs(mad.pzy - this.ozy) > 180) {
                            this.uddirect = this.uddirect == -1 ? 1 : -1;
                            this.udswt = false;
                        }
                        if (this.uddirect == -1) {
                            this.handb = true;
                            this.down = true;
                        } else {
                            this.handb = true;
                            this.up = true;
                            if (this.apunch > 0) {
                                this.down = true;
                                --this.apunch;
                            }
                        }
                    }
                    if ((mad.scy[0] + mad.scy[1] + mad.scy[2] + mad.scy[3]) * 100.0f / (float)(contO.y - 300) < (float)(-this.saftey)) {
                        this.onceu = false;
                        this.onced = false;
                        this.oncel = false;
                        this.oncer = false;
                        this.lrcomp = false;
                        this.udcomp = false;
                        this.udbare = false;
                        this.lrbare = false;
                        this.trickfase = 2;
                        this.swat = 0;
                    }
                }
                if (this.trickfase == 2) {
                    if (this.swat == 0) {
                        if (mad.dcomp != 0.0f || mad.ucomp != 0.0f) {
                            this.udbare = true;
                        }
                        if (mad.lcomp != 0.0f || mad.rcomp != 0.0f) {
                            this.lrbare = true;
                        }
                        this.swat = 1;
                    }
                    if (mad.wtouch) {
                        if (this.swat == 1) {
                            this.swat = 2;
                        }
                    } else if (this.swat == 2) {
                        if (mad.capsized && this.m.random() > this.mustland) {
                            if (this.udbare) {
                                this.lrbare = true;
                                this.udbare = false;
                            } else if (this.lrbare) {
                                this.udbare = true;
                                this.lrbare = false;
                            }
                        }
                        this.swat = 3;
                    }
                    if (this.udbare) {
                        int n15;
                        for (n15 = mad.pzy + 90; n15 < 0; n15 += 360) {
                        }
                        while (n15 > 180) {
                            n15 -= 360;
                        }
                        n15 = Math.abs(n15);
                        if (mad.lcomp - mad.rcomp < 5.0f && (this.onced || this.onceu)) {
                            this.udcomp = true;
                        }
                        if (mad.dcomp > mad.ucomp) {
                            if (mad.capsized) {
                                if (this.udcomp) {
                                    if (n15 > 90) {
                                        this.up = true;
                                    } else {
                                        this.down = true;
                                    }
                                } else if (!this.onced) {
                                    this.down = true;
                                }
                            } else {
                                if (this.udcomp) {
                                    if (this.perfection && Math.abs(n15 - 90) > 30) {
                                        if (n15 > 90) {
                                            this.up = true;
                                        } else {
                                            this.down = true;
                                        }
                                    }
                                } else if (this.m.random() > this.mustland) {
                                    this.up = true;
                                }
                                this.onced = true;
                            }
                        } else if (mad.capsized) {
                            if (this.udcomp) {
                                if (n15 > 90) {
                                    this.up = true;
                                } else {
                                    this.down = true;
                                }
                            } else if (!this.onceu) {
                                this.up = true;
                            }
                        } else {
                            if (this.udcomp) {
                                if (this.perfection && Math.abs(n15 - 90) > 30) {
                                    if (n15 > 90) {
                                        this.up = true;
                                    } else {
                                        this.down = true;
                                    }
                                }
                            } else if (this.m.random() > this.mustland) {
                                this.down = true;
                            }
                            this.onceu = true;
                        }
                    }
                    if (this.lrbare) {
                        int n16 = mad.pxy + 90;
                        if (this.zyinv) {
                            n16 += 180;
                        }
                        while (n16 < 0) {
                            n16 += 360;
                        }
                        while (n16 > 180) {
                            n16 -= 360;
                        }
                        n16 = Math.abs(n16);
                        if (mad.lcomp - mad.rcomp < 10.0f && (this.oncel || this.oncer)) {
                            this.lrcomp = true;
                        }
                        if (mad.lcomp > mad.rcomp) {
                            if (mad.capsized) {
                                if (this.lrcomp) {
                                    if (n16 > 90) {
                                        this.left = true;
                                    } else {
                                        this.right = true;
                                    }
                                } else if (!this.oncel) {
                                    this.left = true;
                                }
                            } else {
                                if (this.lrcomp) {
                                    if (this.perfection && Math.abs(n16 - 90) > 30) {
                                        if (n16 > 90) {
                                            this.left = true;
                                        } else {
                                            this.right = true;
                                        }
                                    }
                                } else if (this.m.random() > this.mustland) {
                                    this.right = true;
                                }
                                this.oncel = true;
                            }
                        } else if (mad.capsized) {
                            if (this.lrcomp) {
                                if (n16 > 90) {
                                    this.left = true;
                                } else {
                                    this.right = true;
                                }
                            } else if (!this.oncer) {
                                this.right = true;
                            }
                        } else {
                            if (this.lrcomp) {
                                if (this.perfection && Math.abs(n16 - 90) > 30) {
                                    if (n16 > 90) {
                                        this.left = true;
                                    } else {
                                        this.right = true;
                                    }
                                }
                            } else if (this.m.random() > this.mustland) {
                                this.left = true;
                            }
                            this.oncer = true;
                        }
                    }
                }
            }
        }
    }

    public int py(int n, int n2, int n3, int n4) {
        return (n - n2) * (n - n2) + (n3 - n4) * (n3 - n4);
    }

    public int pys(int n, int n2, int n3, int n4) {
        return (int)Math.sqrt((n - n2) * (n - n2) + (n3 - n4) * (n3 - n4));
    }
}

