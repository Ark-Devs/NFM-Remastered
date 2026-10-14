/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;

public class Record {
    Medium m;
    int caught = 0;
    boolean hcaught = false;
    boolean prepit = true;
    ContO[] ocar = new ContO[8];
    int cntf = 50;
    ContO[][] car = new ContO[6][8];
    int[][] squash = new int[6][8];
    int[] fix = new int[8];
    int[] dest = new int[8];
    int[][] x = new int[300][8];
    int[][] y = new int[300][8];
    int[][] z = new int[300][8];
    int[][] xy = new int[300][8];
    int[][] zy = new int[300][8];
    int[][] xz = new int[300][8];
    int[][] wxz = new int[300][8];
    int[][] wzy = new int[300][8];
    int[][] ns = new int[8][20];
    int[][][] sspark = new int[8][20][30];
    int[][][] sx = new int[8][20][30];
    int[][][] sy = new int[8][20][30];
    int[][][] sz = new int[8][20][30];
    float[][][] smag = new float[8][20][30];
    int[][][] scx = new int[8][20][30];
    int[][][] scz = new int[8][20][30];
    int[] nr = new int[8];
    int[][] rspark = new int[8][200];
    int[][] sprk = new int[8][200];
    int[][] srx = new int[8][200];
    int[][] sry = new int[8][200];
    int[][] srz = new int[8][200];
    float[][] rcx = new float[8][200];
    float[][] rcy = new float[8][200];
    float[][] rcz = new float[8][200];
    int[][] nry = new int[8][4];
    int[][][] ry = new int[8][4][7];
    int[][][] magy = new int[8][4][7];
    boolean[][] mtouch = new boolean[8][7];
    int[][] nrx = new int[8][4];
    int[][][] rx = new int[8][4][7];
    int[][][] magx = new int[8][4][7];
    int[][] nrz = new int[8][4];
    int[][][] rz = new int[8][4][7];
    int[][][] magz = new int[8][4][7];
    int[] checkpoint = new int[300];
    boolean[] lastcheck = new boolean[300];
    int wasted = 0;
    int whenwasted = 0;
    int powered = 0;
    int closefinish = 0;
    ContO[] starcar = new ContO[8];
    int[] hsquash = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    int[] hfix = new int[]{-1, -1, -1, -1, -1, -1, -1, -1};
    int[] hdest = new int[]{-1, -1, -1, -1, -1, -1, -1, -1};
    int[][] hx = new int[300][8];
    int[][] hy = new int[300][8];
    int[][] hz = new int[300][8];
    int[][] hxy = new int[300][8];
    int[][] hzy = new int[300][8];
    int[][] hxz = new int[300][8];
    int[][] hwxz = new int[300][8];
    int[][] hwzy = new int[300][8];
    int[][][] hsspark = new int[8][20][30];
    int[][][] hsx = new int[8][20][30];
    int[][][] hsy = new int[8][20][30];
    int[][][] hsz = new int[8][20][30];
    float[][][] hsmag = new float[8][20][30];
    int[][][] hscx = new int[8][20][30];
    int[][][] hscz = new int[8][20][30];
    int[][] hrspark = new int[8][200];
    int[][] hsprk = new int[8][200];
    int[][] hsrx = new int[8][200];
    int[][] hsry = new int[8][200];
    int[][] hsrz = new int[8][200];
    float[][] hrcx = new float[8][200];
    float[][] hrcy = new float[8][200];
    float[][] hrcz = new float[8][200];
    int[][][] hry = new int[8][4][7];
    int[][][] hmagy = new int[8][4][7];
    int[][][] hrx = new int[8][4][7];
    int[][][] hmagx = new int[8][4][7];
    int[][][] hrz = new int[8][4][7];
    int[][][] hmagz = new int[8][4][7];
    boolean[][] hmtouch = new boolean[8][7];
    int[] hcheckpoint = new int[300];
    boolean[] hlastcheck = new boolean[300];
    int[] cntdest = new int[8];
    int lastfr = 0;

    public Record(Medium medium) {
        this.m = medium;
        this.caught = 0;
        this.cotchinow(0);
    }

    public void reset(ContO[] contOArray) {
        int n;
        int n2;
        this.caught = 0;
        this.hcaught = false;
        this.wasted = 0;
        this.whenwasted = 0;
        this.closefinish = 0;
        this.powered = 0;
        for (n2 = 0; n2 < 8; ++n2) {
            if (this.prepit) {
                this.starcar[n2] = new ContO(contOArray[n2], 0, 0, 0, 0);
            }
            this.fix[n2] = -1;
            this.dest[n2] = -1;
            this.cntdest[n2] = 0;
        }
        for (n2 = 0; n2 < 6; ++n2) {
            for (n = 0; n < 8; ++n) {
                this.car[n2][n] = new ContO(contOArray[n], 0, 0, 0, 0);
                this.squash[n2][n] = 0;
            }
        }
        for (n2 = 0; n2 < 8; ++n2) {
            int n3;
            this.nr[n2] = 0;
            for (n = 0; n < 200; ++n) {
                this.rspark[n2][n] = -1;
            }
            for (n = 0; n < 20; ++n) {
                this.ns[n2][n] = 0;
                for (n3 = 0; n3 < 30; ++n3) {
                    this.sspark[n2][n][n3] = -1;
                }
            }
            for (n = 0; n < 4; ++n) {
                this.nry[n2][n] = 0;
                this.nrx[n2][n] = 0;
                this.nrz[n2][n] = 0;
                for (n3 = 0; n3 < 7; ++n3) {
                    this.ry[n2][n][n3] = -1;
                    this.rx[n2][n][n3] = -1;
                    this.rz[n2][n][n3] = -1;
                }
            }
        }
        this.prepit = false;
    }

    public void cotchinow(int n) {
        if (this.caught >= 300) {
            int n2;
            int n3;
            int n4;
            this.wasted = n;
            for (n4 = 0; n4 < 8; ++n4) {
                this.starcar[n4] = new ContO(this.car[0][n4], 0, 0, 0, 0);
                this.hsquash[n4] = this.squash[0][n4];
                this.hfix[n4] = this.fix[n4];
                this.hdest[n4] = this.dest[n4];
            }
            for (n4 = 0; n4 < 300; ++n4) {
                for (n3 = 0; n3 < 8; ++n3) {
                    this.hx[n4][n3] = this.x[n4][n3];
                    this.hy[n4][n3] = this.y[n4][n3];
                    this.hz[n4][n3] = this.z[n4][n3];
                    this.hxy[n4][n3] = this.xy[n4][n3];
                    this.hzy[n4][n3] = this.zy[n4][n3];
                    this.hxz[n4][n3] = this.xz[n4][n3];
                    this.hwxz[n4][n3] = this.wxz[n4][n3];
                    this.hwzy[n4][n3] = this.wzy[n4][n3];
                }
                this.hcheckpoint[n4] = this.checkpoint[n4];
                this.hlastcheck[n4] = this.lastcheck[n4];
            }
            for (n4 = 0; n4 < 8; ++n4) {
                for (n3 = 0; n3 < 20; ++n3) {
                    for (n2 = 0; n2 < 30; ++n2) {
                        this.hsspark[n4][n3][n2] = this.sspark[n4][n3][n2];
                        this.hsx[n4][n3][n2] = this.sx[n4][n3][n2];
                        this.hsy[n4][n3][n2] = this.sy[n4][n3][n2];
                        this.hsz[n4][n3][n2] = this.sz[n4][n3][n2];
                        this.hsmag[n4][n3][n2] = this.smag[n4][n3][n2];
                        this.hscx[n4][n3][n2] = this.scx[n4][n3][n2];
                        this.hscz[n4][n3][n2] = this.scz[n4][n3][n2];
                    }
                }
                for (n3 = 0; n3 < 200; ++n3) {
                    this.hrspark[n4][n3] = this.rspark[n4][n3];
                    this.hsprk[n4][n3] = this.sprk[n4][n3];
                    this.hsrx[n4][n3] = this.srx[n4][n3];
                    this.hsry[n4][n3] = this.sry[n4][n3];
                    this.hsrz[n4][n3] = this.srz[n4][n3];
                    this.hrcx[n4][n3] = this.rcx[n4][n3];
                    this.hrcy[n4][n3] = this.rcy[n4][n3];
                    this.hrcz[n4][n3] = this.rcz[n4][n3];
                }
            }
            for (n4 = 0; n4 < 8; ++n4) {
                for (n3 = 0; n3 < 4; ++n3) {
                    for (n2 = 0; n2 < 7; ++n2) {
                        this.hry[n4][n3][n2] = this.ry[n4][n3][n2];
                        this.hmagy[n4][n3][n2] = this.magy[n4][n3][n2];
                        this.hrx[n4][n3][n2] = this.rx[n4][n3][n2];
                        this.hmagx[n4][n3][n2] = this.magx[n4][n3][n2];
                        this.hrz[n4][n3][n2] = this.rz[n4][n3][n2];
                        this.hmagz[n4][n3][n2] = this.magz[n4][n3][n2];
                    }
                }
            }
            for (n4 = 0; n4 < 8; ++n4) {
                for (n3 = 0; n3 < 7; ++n3) {
                    this.hmtouch[n4][n3] = this.mtouch[n4][n3];
                }
            }
            this.hcaught = true;
        }
    }

    public void rec(ContO contO, int n, int n2, int n3, int n4, int n5) {
        int n6;
        int n7;
        if (n == n5) {
            ++this.caught;
        }
        if (this.cntf == 50) {
            for (n7 = 0; n7 < 5; ++n7) {
                this.car[n7][n] = new ContO(this.car[n7 + 1][n], 0, 0, 0, 0);
                this.squash[n7][n] = this.squash[n7 + 1][n];
            }
            this.car[5][n] = new ContO(contO, 0, 0, 0, 0);
            this.squash[5][n] = n2;
            this.cntf = 0;
        } else {
            ++this.cntf;
        }
        int n8 = n;
        this.fix[n8] = this.fix[n8] - 1;
        if (n4 != 0) {
            int n9 = n;
            this.dest[n9] = this.dest[n9] - 1;
        }
        if (this.dest[n] == 230) {
            if (n == n5) {
                this.cotchinow(n5);
                this.whenwasted = 229;
            } else if (n3 != 0) {
                this.cotchinow(n);
                this.whenwasted = 165 + n3;
            }
        }
        for (n7 = 0; n7 < 299; ++n7) {
            this.x[n7][n] = this.x[n7 + 1][n];
            this.y[n7][n] = this.y[n7 + 1][n];
            this.z[n7][n] = this.z[n7 + 1][n];
            this.zy[n7][n] = this.zy[n7 + 1][n];
            this.xy[n7][n] = this.xy[n7 + 1][n];
            this.xz[n7][n] = this.xz[n7 + 1][n];
            this.wxz[n7][n] = this.wxz[n7 + 1][n];
            this.wzy[n7][n] = this.wzy[n7 + 1][n];
        }
        this.x[299][n] = contO.x;
        this.y[299][n] = contO.y;
        this.z[299][n] = contO.z;
        this.xy[299][n] = contO.xy;
        this.zy[299][n] = contO.zy;
        this.xz[299][n] = contO.xz;
        this.wxz[299][n] = contO.wxz;
        this.wzy[299][n] = contO.wzy;
        if (n == n5) {
            for (n7 = 0; n7 < 299; ++n7) {
                this.checkpoint[n7] = this.checkpoint[n7 + 1];
                this.lastcheck[n7] = this.lastcheck[n7 + 1];
            }
            this.checkpoint[299] = contO.m.checkpoint;
            this.lastcheck[299] = contO.m.lastcheck;
        }
        for (n7 = 0; n7 < 20; ++n7) {
            if (contO.stg[n7] == 1) {
                this.sspark[n][n7][this.ns[n][n7]] = 300;
                this.sx[n][n7][this.ns[n][n7]] = contO.sx[n7];
                this.sy[n][n7][this.ns[n][n7]] = contO.sy[n7];
                this.sz[n][n7][this.ns[n][n7]] = contO.sz[n7];
                this.smag[n][n7][this.ns[n][n7]] = contO.osmag[n7];
                this.scx[n][n7][this.ns[n][n7]] = contO.scx[n7];
                this.scz[n][n7][this.ns[n][n7]] = contO.scz[n7];
                int[] nArray = this.ns[n];
                int n10 = n7;
                nArray[n10] = nArray[n10] + 1;
                if (this.ns[n][n7] == 30) {
                    this.ns[n][n7] = 0;
                }
            }
            n6 = 0;
            while (n6 < 30) {
                int[] nArray = this.sspark[n][n7];
                int n11 = n6++;
                nArray[n11] = nArray[n11] - 1;
            }
        }
        if (contO.sprk != 0) {
            this.rspark[n][this.nr[n]] = 300;
            this.sprk[n][this.nr[n]] = contO.sprk;
            this.srx[n][this.nr[n]] = contO.srx;
            this.sry[n][this.nr[n]] = contO.sry;
            this.srz[n][this.nr[n]] = contO.srz;
            this.rcx[n][this.nr[n]] = contO.rcx;
            this.rcy[n][this.nr[n]] = contO.rcy;
            this.rcz[n][this.nr[n]] = contO.rcz;
            int n12 = n;
            this.nr[n12] = this.nr[n12] + 1;
            if (this.nr[n] == 200) {
                this.nr[n] = 0;
            }
        }
        n7 = 0;
        while (n7 < 200) {
            int[] nArray = this.rspark[n];
            int n13 = n7++;
            nArray[n13] = nArray[n13] - 1;
        }
        for (n7 = 0; n7 < 4; ++n7) {
            n6 = 0;
            while (n6 < 7) {
                int[] nArray = this.ry[n][n7];
                int n14 = n6;
                nArray[n14] = nArray[n14] - 1;
                int[] nArray2 = this.rx[n][n7];
                int n15 = n6;
                nArray2[n15] = nArray2[n15] - 1;
                int[] nArray3 = this.rz[n][n7];
                int n16 = n6++;
                nArray3[n16] = nArray3[n16] - 1;
            }
        }
    }

    public void play(ContO contO, Mad mad, int n, int n2) {
        int n3;
        int n4;
        contO.x = this.x[n2][n];
        contO.y = this.y[n2][n];
        contO.z = this.z[n2][n];
        contO.zy = this.zy[n2][n];
        contO.xy = this.xy[n2][n];
        contO.xz = this.xz[n2][n];
        contO.wxz = this.wxz[n2][n];
        contO.wzy = this.wzy[n2][n];
        if (n == 0) {
            contO.m.checkpoint = this.checkpoint[n2];
            contO.m.lastcheck = this.lastcheck[n2];
        }
        if (n2 == 0) {
            this.cntdest[n] = 0;
        }
        if (this.dest[n] == n2) {
            this.cntdest[n] = 7;
        }
        if (n2 == 0 && this.dest[n] < -1) {
            for (n4 = 0; n4 < contO.npl; ++n4) {
                if (contO.p[n4].wz != 0 && contO.p[n4].gr != -17 && contO.p[n4].gr != -16) continue;
                contO.p[n4].embos = 13;
            }
        }
        if (this.cntdest[n] != 0) {
            for (n4 = 0; n4 < contO.npl; ++n4) {
                if (contO.p[n4].wz != 0 && contO.p[n4].gr != -17 && contO.p[n4].gr != -16) continue;
                contO.p[n4].embos = 1;
            }
            int n5 = n;
            this.cntdest[n5] = this.cntdest[n5] - 1;
        }
        for (n4 = 0; n4 < 20; ++n4) {
            for (n3 = 0; n3 < 30; ++n3) {
                if (this.sspark[n][n4][n3] != n2) continue;
                contO.stg[n4] = 1;
                contO.sx[n4] = this.sx[n][n4][n3];
                contO.sy[n4] = this.sy[n][n4][n3];
                contO.sz[n4] = this.sz[n][n4][n3];
                contO.osmag[n4] = this.smag[n][n4][n3];
                contO.scx[n4] = this.scx[n][n4][n3];
                contO.scz[n4] = this.scz[n][n4][n3];
            }
        }
        for (n4 = 0; n4 < 200; ++n4) {
            if (this.rspark[n][n4] != n2) continue;
            contO.sprk = this.sprk[n][n4];
            contO.srx = this.srx[n][n4];
            contO.sry = this.sry[n][n4];
            contO.srz = this.srz[n][n4];
            contO.rcx = this.rcx[n][n4];
            contO.rcy = this.rcy[n][n4];
            contO.rcz = this.rcz[n][n4];
        }
        for (n4 = 0; n4 < 4; ++n4) {
            for (n3 = 0; n3 < 7; ++n3) {
                if (this.ry[n][n4][n3] == n2) {
                    this.regy(n4, this.magy[n][n4][n3], this.mtouch[n][n3], contO, mad);
                }
                if (this.rx[n][n4][n3] == n2) {
                    this.regx(n4, this.magx[n][n4][n3], contO, mad);
                }
                if (this.rz[n][n4][n3] != n2) continue;
                this.regz(n4, this.magz[n][n4][n3], contO, mad);
            }
        }
    }

    public void playh(ContO contO, Mad mad, int n, int n2, int n3) {
        int n4;
        int n5;
        contO.x = this.hx[n2][n];
        contO.y = this.hy[n2][n];
        contO.z = this.hz[n2][n];
        contO.zy = this.hzy[n2][n];
        contO.xy = this.hxy[n2][n];
        contO.xz = this.hxz[n2][n];
        contO.wxz = this.hwxz[n2][n];
        contO.wzy = this.hwzy[n2][n];
        if (n == n3) {
            contO.m.checkpoint = this.hcheckpoint[n2];
            contO.m.lastcheck = this.hlastcheck[n2];
        }
        if (n2 == 0) {
            this.cntdest[n] = 0;
        }
        if (this.hdest[n] == n2) {
            this.cntdest[n] = 7;
        }
        if (n2 == 0 && this.hdest[n] < -1) {
            for (n5 = 0; n5 < contO.npl; ++n5) {
                if (contO.p[n5].wz != 0 && contO.p[n5].gr != -17 && contO.p[n5].gr != -16) continue;
                contO.p[n5].embos = 13;
            }
        }
        if (this.cntdest[n] != 0) {
            for (n5 = 0; n5 < contO.npl; ++n5) {
                if (contO.p[n5].wz != 0 && contO.p[n5].gr != -17 && contO.p[n5].gr != -16) continue;
                contO.p[n5].embos = 1;
            }
            int n6 = n;
            this.cntdest[n6] = this.cntdest[n6] - 1;
        }
        for (n5 = 0; n5 < 20; ++n5) {
            for (n4 = 0; n4 < 30; ++n4) {
                if (this.hsspark[n][n5][n4] != n2) continue;
                contO.stg[n5] = 1;
                contO.sx[n5] = this.hsx[n][n5][n4];
                contO.sy[n5] = this.hsy[n][n5][n4];
                contO.sz[n5] = this.hsz[n][n5][n4];
                contO.osmag[n5] = this.hsmag[n][n5][n4];
                contO.scx[n5] = this.hscx[n][n5][n4];
                contO.scz[n5] = this.hscz[n][n5][n4];
            }
        }
        for (n5 = 0; n5 < 200; ++n5) {
            if (this.hrspark[n][n5] != n2) continue;
            contO.sprk = this.hsprk[n][n5];
            contO.srx = this.hsrx[n][n5];
            contO.sry = this.hsry[n][n5];
            contO.srz = this.hsrz[n][n5];
            contO.rcx = this.hrcx[n][n5];
            contO.rcy = this.hrcy[n][n5];
            contO.rcz = this.hrcz[n][n5];
        }
        for (n5 = 0; n5 < 4; ++n5) {
            for (n4 = 0; n4 < 7; ++n4) {
                if (this.hry[n][n5][n4] == n2 && this.lastfr != n2) {
                    this.regy(n5, this.hmagy[n][n5][n4], this.hmtouch[n][n4], contO, mad);
                }
                if (this.hrx[n][n5][n4] == n2) {
                    if (this.lastfr != n2) {
                        this.regx(n5, this.hmagx[n][n5][n4], contO, mad);
                    } else {
                        this.chipx(n5, this.hmagx[n][n5][n4], contO, mad);
                    }
                }
                if (this.hrz[n][n5][n4] != n2) continue;
                if (this.lastfr != n2) {
                    this.regz(n5, this.hmagz[n][n5][n4], contO, mad);
                    continue;
                }
                this.chipz(n5, this.hmagz[n][n5][n4], contO, mad);
            }
        }
        this.lastfr = n2;
    }

    public void recy(int n, float f, boolean bl, int n2) {
        this.ry[n2][n][this.nry[n2][n]] = 300;
        this.magy[n2][n][this.nry[n2][n]] = (int)f;
        this.mtouch[n2][this.nry[n2][n]] = bl;
        int[] nArray = this.nry[n2];
        int n3 = n;
        nArray[n3] = nArray[n3] + 1;
        if (this.nry[n2][n] == 7) {
            this.nry[n2][n] = 0;
        }
    }

    public void recx(int n, float f, int n2) {
        this.rx[n2][n][this.nry[n2][n]] = 300;
        this.magx[n2][n][this.nry[n2][n]] = (int)f;
        int[] nArray = this.nrx[n2];
        int n3 = n;
        nArray[n3] = nArray[n3] + 1;
        if (this.nrx[n2][n] == 7) {
            this.nrx[n2][n] = 0;
        }
    }

    public void recz(int n, float f, int n2) {
        this.rz[n2][n][this.nry[n2][n]] = 300;
        this.magz[n2][n][this.nry[n2][n]] = (int)f;
        int[] nArray = this.nrz[n2];
        int n3 = n;
        nArray[n3] = nArray[n3] + 1;
        if (this.nrz[n2][n] == 7) {
            this.nrz[n2][n] = 0;
        }
    }

    public void regy(int n, float f, boolean bl, ContO contO, Mad mad) {
        if (f > 100.0f) {
            int n2;
            int n3;
            int n4;
            f -= 100.0f;
            int n5 = 0;
            int n6 = 0;
            int n7 = contO.xy;
            for (n4 = contO.zy; n4 < 360; n4 += 360) {
            }
            while (n4 > 360) {
                n4 -= 360;
            }
            if (n4 < 210 && n4 > 150) {
                n5 = -1;
            }
            if (n4 > 330 || n4 < 30) {
                n5 = 1;
            }
            while (n7 < 360) {
                n7 += 360;
            }
            while (n7 > 360) {
                n7 -= 360;
            }
            if (n7 < 210 && n7 > 150) {
                n6 = -1;
            }
            if (n7 > 330 || n7 < 30) {
                n6 = 1;
            }
            if (n6 * n5 == 0 || bl) {
                for (n3 = 0; n3 < contO.npl; ++n3) {
                    float f2 = 0.0f;
                    for (n2 = 0; n2 < contO.p[n3].n; ++n2) {
                        if (contO.p[n3].wz != 0 || this.py(contO.keyx[n], contO.p[n3].ox[n2], contO.keyz[n], contO.p[n3].oz[n2]) >= mad.cd.clrad[mad.cn]) continue;
                        f2 = f / 20.0f * this.m.random();
                        int n8 = n2;
                        contO.p[n3].oz[n8] = (int)((float)contO.p[n3].oz[n8] + f2 * this.m.sin(n4));
                        int n9 = n2;
                        contO.p[n3].ox[n9] = (int)((float)contO.p[n3].ox[n9] - f2 * this.m.sin(n7));
                    }
                    if (f2 == 0.0f) continue;
                    if (Math.abs(f2) >= 1.0f) {
                        contO.p[n3].chip = 1;
                        contO.p[n3].ctmag = f2;
                    }
                    if (!contO.p[n3].nocol && contO.p[n3].glass != 1) {
                        if (contO.p[n3].bfase > 20 && (double)contO.p[n3].hsb[1] > 0.2) {
                            contO.p[n3].hsb[1] = 0.2f;
                        }
                        if (contO.p[n3].bfase > 30) {
                            if ((double)contO.p[n3].hsb[2] < 0.5) {
                                contO.p[n3].hsb[2] = 0.5f;
                            }
                            if ((double)contO.p[n3].hsb[1] > 0.1) {
                                contO.p[n3].hsb[1] = 0.1f;
                            }
                        }
                        if (contO.p[n3].bfase > 40) {
                            contO.p[n3].hsb[1] = 0.05f;
                        }
                        if (contO.p[n3].bfase > 50) {
                            if ((double)contO.p[n3].hsb[2] > 0.8) {
                                contO.p[n3].hsb[2] = 0.8f;
                            }
                            contO.p[n3].hsb[0] = 0.075f;
                            contO.p[n3].hsb[1] = 0.05f;
                        }
                        if (contO.p[n3].bfase > 60) {
                            contO.p[n3].hsb[0] = 0.05f;
                        }
                        contO.p[n3].bfase = (int)((float)contO.p[n3].bfase + f2);
                        new Color(contO.p[n3].c[0], contO.p[n3].c[1], contO.p[n3].c[2]);
                        Color color = Color.getHSBColor(contO.p[n3].hsb[0], contO.p[n3].hsb[1], contO.p[n3].hsb[2]);
                        contO.p[n3].c[0] = color.getRed();
                        contO.p[n3].c[1] = color.getGreen();
                        contO.p[n3].c[2] = color.getBlue();
                    }
                    if (contO.p[n3].glass != 1) continue;
                    contO.p[n3].gr = (int)((double)contO.p[n3].gr + Math.abs((double)f2 * 1.5));
                }
            }
            if (n6 * n5 == -1) {
                n3 = 0;
                int n10 = 1;
                for (n2 = 0; n2 < contO.npl; ++n2) {
                    float f3 = 0.0f;
                    for (int i = 0; i < contO.p[n2].n; ++i) {
                        if (contO.p[n2].wz != 0) continue;
                        f3 = f / 15.0f * this.m.random();
                        if (Math.abs(contO.p[n2].oy[i] - mad.cd.flipy[mad.cn] - this.squash[0][mad.im]) >= mad.cd.msquash[mad.cn] * 3 && contO.p[n2].oy[i] >= mad.cd.flipy[mad.cn] + this.squash[0][mad.im] || this.squash[0][mad.im] >= mad.cd.msquash[mad.cn]) continue;
                        int n11 = i;
                        contO.p[n2].oy[n11] = (int)((float)contO.p[n2].oy[n11] + f3);
                        n3 = (int)((float)n3 + f3);
                        ++n10;
                    }
                    if (contO.p[n2].glass == 1) {
                        contO.p[n2].gr += 5;
                    } else if (f3 != 0.0f) {
                        contO.p[n2].bfase = (int)((float)contO.p[n2].bfase + f3);
                    }
                    if (!(Math.abs(f3) >= 1.0f)) continue;
                    contO.p[n2].chip = 1;
                    contO.p[n2].ctmag = f3;
                }
                int[] nArray = this.squash[0];
                int n12 = mad.im;
                nArray[n12] = nArray[n12] + n3 / n10;
            }
        }
    }

    public void regx(int n, float f, ContO contO, Mad mad) {
        if (Math.abs(f) > 100.0f) {
            if (f > 100.0f) {
                f -= 100.0f;
            }
            if (f < -100.0f) {
                f += 100.0f;
            }
            for (int i = 0; i < contO.npl; ++i) {
                float f2 = 0.0f;
                for (int j = 0; j < contO.p[i].n; ++j) {
                    if (contO.p[i].wz != 0 || this.py(contO.keyx[n], contO.p[i].ox[j], contO.keyz[n], contO.p[i].oz[j]) >= mad.cd.clrad[mad.cn]) continue;
                    f2 = f / 20.0f * this.m.random();
                    int n2 = j;
                    contO.p[i].oz[n2] = (int)((float)contO.p[i].oz[n2] - f2 * this.m.sin(contO.xz) * this.m.cos(contO.zy));
                    int n3 = j;
                    contO.p[i].ox[n3] = (int)((float)contO.p[i].ox[n3] + f2 * this.m.cos(contO.xz) * this.m.cos(contO.xy));
                }
                if (f2 == 0.0f) continue;
                if (Math.abs(f2) >= 1.0f) {
                    contO.p[i].chip = 1;
                    contO.p[i].ctmag = f2;
                }
                if (!contO.p[i].nocol && contO.p[i].glass != 1) {
                    if (contO.p[i].bfase > 20 && (double)contO.p[i].hsb[1] > 0.2) {
                        contO.p[i].hsb[1] = 0.2f;
                    }
                    if (contO.p[i].bfase > 30) {
                        if ((double)contO.p[i].hsb[2] < 0.5) {
                            contO.p[i].hsb[2] = 0.5f;
                        }
                        if ((double)contO.p[i].hsb[1] > 0.1) {
                            contO.p[i].hsb[1] = 0.1f;
                        }
                    }
                    if (contO.p[i].bfase > 40) {
                        contO.p[i].hsb[1] = 0.05f;
                    }
                    if (contO.p[i].bfase > 50) {
                        if ((double)contO.p[i].hsb[2] > 0.8) {
                            contO.p[i].hsb[2] = 0.8f;
                        }
                        contO.p[i].hsb[0] = 0.075f;
                        contO.p[i].hsb[1] = 0.05f;
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
    }

    public void regz(int n, float f, ContO contO, Mad mad) {
        if (Math.abs(f) > 100.0f) {
            if (f > 100.0f) {
                f -= 100.0f;
            }
            if (f < -100.0f) {
                f += 100.0f;
            }
            for (int i = 0; i < contO.npl; ++i) {
                float f2 = 0.0f;
                for (int j = 0; j < contO.p[i].n; ++j) {
                    if (contO.p[i].wz != 0 || this.py(contO.keyx[n], contO.p[i].ox[j], contO.keyz[n], contO.p[i].oz[j]) >= mad.cd.clrad[mad.cn]) continue;
                    f2 = f / 20.0f * this.m.random();
                    int n2 = j;
                    contO.p[i].oz[n2] = (int)((float)contO.p[i].oz[n2] + f2 * this.m.cos(contO.xz) * this.m.cos(contO.zy));
                    int n3 = j;
                    contO.p[i].ox[n3] = (int)((float)contO.p[i].ox[n3] + f2 * this.m.sin(contO.xz) * this.m.cos(contO.xy));
                }
                if (f2 == 0.0f) continue;
                if (Math.abs(f2) >= 1.0f) {
                    contO.p[i].chip = 1;
                    contO.p[i].ctmag = f2;
                }
                if (!contO.p[i].nocol && contO.p[i].glass != 1) {
                    if (contO.p[i].bfase > 20 && (double)contO.p[i].hsb[1] > 0.2) {
                        contO.p[i].hsb[1] = 0.2f;
                    }
                    if (contO.p[i].bfase > 30) {
                        if ((double)contO.p[i].hsb[2] < 0.5) {
                            contO.p[i].hsb[2] = 0.5f;
                        }
                        if ((double)contO.p[i].hsb[1] > 0.1) {
                            contO.p[i].hsb[1] = 0.1f;
                        }
                    }
                    if (contO.p[i].bfase > 40) {
                        contO.p[i].hsb[1] = 0.05f;
                    }
                    if (contO.p[i].bfase > 50) {
                        if ((double)contO.p[i].hsb[2] > 0.8) {
                            contO.p[i].hsb[2] = 0.8f;
                        }
                        contO.p[i].hsb[0] = 0.075f;
                        contO.p[i].hsb[1] = 0.05f;
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
    }

    public void chipx(int n, float f, ContO contO, Mad mad) {
        if (Math.abs(f) > 100.0f) {
            if (f > 100.0f) {
                f -= 100.0f;
            }
            if (f < -100.0f) {
                f += 100.0f;
            }
            for (int i = 0; i < contO.npl; ++i) {
                float f2 = 0.0f;
                for (int j = 0; j < contO.p[i].n; ++j) {
                    if (contO.p[i].wz != 0 || this.py(contO.keyx[n], contO.p[i].ox[j], contO.keyz[n], contO.p[i].oz[j]) >= mad.cd.clrad[mad.cn]) continue;
                    f2 = f / 20.0f * this.m.random();
                }
                if (f2 == 0.0f || !(Math.abs(f2) >= 1.0f)) continue;
                contO.p[i].chip = 1;
                contO.p[i].ctmag = f2;
            }
        }
    }

    public void chipz(int n, float f, ContO contO, Mad mad) {
        if (Math.abs(f) > 100.0f) {
            if (f > 100.0f) {
                f -= 100.0f;
            }
            if (f < -100.0f) {
                f += 100.0f;
            }
            for (int i = 0; i < contO.npl; ++i) {
                float f2 = 0.0f;
                for (int j = 0; j < contO.p[i].n; ++j) {
                    if (contO.p[i].wz != 0 || this.py(contO.keyx[n], contO.p[i].ox[j], contO.keyz[n], contO.p[i].oz[j]) >= mad.cd.clrad[mad.cn]) continue;
                    f2 = f / 20.0f * this.m.random();
                }
                if (f2 == 0.0f || !(Math.abs(f2) >= 1.0f)) continue;
                contO.p[i].chip = 1;
                contO.p[i].ctmag = f2;
            }
        }
    }

    public int py(int n, int n2, int n3, int n4) {
        return (n - n2) * (n - n2) + (n3 - n4) * (n3 - n4);
    }
}

