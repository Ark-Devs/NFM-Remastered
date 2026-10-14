/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Medium {
    int focus_point = 400;
    int ground = 250;
    int skyline = -300;
    int[] fade = new int[]{3000, 4500, 6000, 7500, 9000, 10500, 12000, 13500, 15000, 16500, 18000, 19500, 21000, 22500, 24000, 25500};
    int[] cldd = new int[]{210, 210, 210, 1, -1000};
    int[] clds = new int[]{210, 210, 210};
    int[] osky = new int[]{170, 220, 255};
    int[] csky = new int[]{170, 220, 255};
    int[] ogrnd = new int[]{205, 200, 200};
    int[] cgrnd = new int[]{205, 200, 200};
    int[] texture = new int[]{0, 0, 0, 50};
    int[] cpol = new int[]{215, 210, 210};
    int[] crgrnd = new int[]{205, 200, 200};
    int[] cfade = new int[]{255, 220, 220};
    int[] snap = new int[]{0, 0, 0};
    int fogd = 7;
    int mgen = (int)(Math.random() * 100000.0);
    boolean loadnew = false;
    boolean lightson = false;
    boolean darksky = false;
    int lightn = -1;
    int lilo = 217;
    boolean lton = false;
    int noelec = 0;
    int trk = 0;
    boolean crs = false;
    int cx = 400;
    int cy = 225;
    int cz = 50;
    int xz = 0;
    int zy = 0;
    int x = 0;
    int y = 0;
    int z = 0;
    int iw = 0;
    int ih = 0;
    int w = 800;
    int h = 450;
    int nsp = 0;
    int[] spx = new int[7];
    int[] spz = new int[7];
    int[] sprad = new int[7];
    boolean td = false;
    int bcxz = 0;
    boolean bt = false;
    int vxz = 180;
    int adv = 500;
    boolean vert = false;
    float[] tcos = new float[360];
    float[] tsin = new float[360];
    int lastmaf = 0;
    int checkpoint = -1;
    boolean lastcheck = false;
    float elecr = 0.0f;
    boolean cpflik = false;
    boolean nochekflk = false;
    int cntrn = 0;
    boolean[] diup = new boolean[]{false, false, false};
    int[] rand = new int[]{0, 0, 0};
    int trn = 0;
    int hit = 45000;
    int ptr = 0;
    int ptcnt = -10;
    int nrnd = 0;
    long trx = 0L;
    long trz = 0L;
    long atrx = 0L;
    long atrz = 0L;
    int fallen = 0;
    float fo = 1.0f;
    float gofo = (float)((double)0.33f + Math.random() * 1.34);
    int fvect = 200;
    int[][] ogpx = null;
    int[][] ogpz = null;
    float[][] pvr = null;
    int[] cgpx = null;
    int[] cgpz = null;
    int[] pmx = null;
    float[] pcv = null;
    int sgpx = 0;
    int sgpz = 0;
    int nrw = 0;
    int ncl = 0;
    int noc = 0;
    int[] clx = null;
    int[] clz = null;
    int[] cmx = null;
    int[][][] clax = null;
    int[][][] clay = null;
    int[][][] claz = null;
    int[][][][] clc = null;
    int nmt = 0;
    int[] mrd = null;
    int[] nmv = null;
    int[][] mtx = null;
    int[][] mty = null;
    int[][] mtz = null;
    int[][][] mtc = null;
    int nst = 0;
    int[] stx = null;
    int[] stz = null;
    int[][][] stc = null;
    boolean[] bst = null;
    int[] twn = null;
    int resdown = 0;
    int rescnt = 5;

    public Medium() {
        int n;
        for (n = 0; n < 360; ++n) {
            this.tcos[n] = (float)Math.cos((double)n * (Math.PI / 180));
        }
        for (n = 0; n < 360; ++n) {
            this.tsin[n] = (float)Math.sin((double)n * (Math.PI / 180));
        }
    }

    public float random() {
        int n;
        if (this.cntrn == 0) {
            for (n = 0; n < 3; ++n) {
                this.rand[n] = (int)(10.0 * Math.random());
                this.diup[n] = !(Math.random() > Math.random());
            }
            this.cntrn = 20;
        } else {
            --this.cntrn;
        }
        for (n = 0; n < 3; ++n) {
            if (this.diup[n]) {
                int n2 = n;
                this.rand[n2] = this.rand[n2] + 1;
                if (this.rand[n] != 10) continue;
                this.rand[n] = 0;
                continue;
            }
            int n3 = n;
            this.rand[n3] = this.rand[n3] - 1;
            if (this.rand[n] != -1) continue;
            this.rand[n] = 9;
        }
        ++this.trn;
        if (this.trn == 3) {
            this.trn = 0;
        }
        return (float)this.rand[this.trn] / 10.0f;
    }

    public void watch(ContO contO, int n) {
        if (this.td) {
            this.y = (int)((float)(contO.y - 300) - 1100.0f * this.random());
            this.x = contO.x + (int)((float)(contO.x + 400 - contO.x) * this.cos(n) - (float)(contO.z + 5000 - contO.z) * this.sin(n));
            this.z = contO.z + (int)((float)(contO.x + 400 - contO.x) * this.sin(n) + (float)(contO.z + 5000 - contO.z) * this.cos(n));
            this.td = false;
        }
        int n2 = 0;
        if (contO.x - this.x - this.cx > 0) {
            n2 = 180;
        }
        int n3 = -((int)((double)(90 + n2) + Math.atan((double)(contO.z - this.z) / (double)(contO.x - this.x - this.cx)) / (Math.PI / 180)));
        n2 = 0;
        if (contO.y - this.y - this.cy < 0) {
            n2 = -180;
        }
        int n4 = (int)Math.sqrt((contO.z - this.z) * (contO.z - this.z) + (contO.x - this.x - this.cx) * (contO.x - this.x - this.cx));
        int n5 = (int)((double)(90 + n2) - Math.atan((double)n4 / (double)(contO.y - this.y - this.cy)) / (Math.PI / 180));
        while (n3 < 0) {
            n3 += 360;
        }
        while (n3 > 360) {
            n3 -= 360;
        }
        this.xz = n3;
        this.zy += (n5 - this.zy) / 5;
        if ((int)Math.sqrt((contO.z - this.z) * (contO.z - this.z) + (contO.x - this.x - this.cx) * (contO.x - this.x - this.cx) + (contO.y - this.y - this.cy) * (contO.y - this.y - this.cy)) > 6000) {
            this.td = true;
        }
    }

    public void aroundtrack(CheckPoints checkPoints) {
        this.y = -this.hit;
        this.x = this.cx + (int)this.trx + (int)(17000.0f * this.cos(this.vxz));
        this.z = (int)this.trz + (int)(17000.0f * this.sin(this.vxz));
        if (this.hit > 5000) {
            if (this.hit == 45000) {
                this.fo = 1.0f;
                this.zy = 67;
                this.atrx = ((long)checkPoints.x[0] - this.trx) / 116L;
                this.atrz = ((long)checkPoints.z[0] - this.trz) / 116L;
                this.focus_point = 400;
            }
            if (this.hit == 20000) {
                this.fallen = 500;
                this.fo = 1.0f;
                this.zy = 67;
                this.atrx = ((long)checkPoints.x[0] - this.trx) / 116L;
                this.atrz = ((long)checkPoints.z[0] - this.trz) / 116L;
                this.focus_point = 400;
            }
            this.hit -= this.fallen;
            this.fallen += 7;
            this.trx += this.atrx;
            this.trz += this.atrz;
            if (this.hit < 17600) {
                this.zy -= 2;
            }
            if (this.fallen > 500) {
                this.fallen = 500;
            }
            if (this.hit <= 5000) {
                this.hit = 5000;
                this.fallen = 0;
            }
            this.vxz += 3;
        } else {
            this.focus_point = (int)(400.0f * this.fo);
            if ((double)Math.abs(this.fo - this.gofo) > 0.005) {
                this.fo = this.fo < this.gofo ? (this.fo += 0.005f) : (this.fo -= 0.005f);
            } else {
                this.gofo = (float)((double)0.35f + Math.random() * 1.3);
            }
            ++this.vxz;
            this.trx -= (this.trx - (long)checkPoints.x[this.ptr]) / 10L;
            this.trz -= (this.trz - (long)checkPoints.z[this.ptr]) / 10L;
            if (this.ptcnt == 7) {
                ++this.ptr;
                if (this.ptr == checkPoints.n) {
                    this.ptr = 0;
                    ++this.nrnd;
                }
                this.ptcnt = 0;
            } else {
                ++this.ptcnt;
            }
        }
        if (this.vxz > 360) {
            this.vxz -= 360;
        }
        this.xz = -this.vxz - 90;
        int n = 0;
        if (-this.y - this.cy < 0) {
            n = -180;
        }
        int n2 = (int)Math.sqrt((this.trz - (long)this.z + (long)this.cz) * (this.trz - (long)this.z + (long)this.cz) + (this.trx - (long)this.x - (long)this.cx) * (this.trx - (long)this.x - (long)this.cx));
        this.cpflik = !this.cpflik;
    }

    public void around(ContO contO, boolean bl) {
        if (!bl) {
            this.adv = !this.vert ? (this.adv += 2) : (this.adv -= 2);
            if (this.adv > 900) {
                this.vert = true;
            }
            if (this.adv < -500) {
                this.vert = false;
            }
        } else {
            this.adv -= 14;
            if (this.adv < 617) {
                this.adv = 617;
            }
        }
        int n = 500 + this.adv;
        if (bl && n < 1300) {
            n = 1300;
        }
        if (n < 1000) {
            n = 1000;
        }
        this.y = contO.y - this.adv;
        if (this.y > 10) {
            this.vert = false;
        }
        this.x = contO.x + (int)((float)(contO.x - n - contO.x) * this.cos(this.vxz));
        this.z = contO.z + (int)((float)(contO.x - n - contO.x) * this.sin(this.vxz));
        this.vxz = !bl ? (this.vxz += 2) : (this.vxz += 4);
        int n2 = 0;
        int n3 = this.y;
        if (n3 > 0) {
            n3 = 0;
        }
        if (contO.y - n3 - this.cy < 0) {
            n2 = -180;
        }
        int n4 = (int)Math.sqrt((contO.z - this.z + this.cz) * (contO.z - this.z + this.cz) + (contO.x - this.x - this.cx) * (contO.x - this.x - this.cx));
        int n5 = (int)((double)(90 + n2) - Math.atan((double)n4 / (double)(contO.y - n3 - this.cy)) / (Math.PI / 180));
        this.xz = -this.vxz + 90;
        if (bl) {
            n5 -= 15;
        }
        this.zy += (n5 - this.zy) / 10;
    }

    public void getaround(ContO contO) {
        int n;
        this.adv = !this.vert ? (this.adv += 2) : (this.adv -= 2);
        if (this.adv > 1700) {
            this.vert = true;
        }
        if (this.adv < -500) {
            this.vert = false;
        }
        if (contO.y - this.adv > 10) {
            this.vert = false;
        }
        if ((n = 500 + this.adv) < 1000) {
            n = 1000;
        }
        int n2 = contO.y - this.adv;
        int n3 = contO.x + (int)((float)(contO.x - n - contO.x) * this.cos(this.vxz));
        int n4 = contO.z + (int)((float)(contO.x - n - contO.x) * this.sin(this.vxz));
        int n5 = 0;
        if (Math.abs(n2 - this.y) > this.fvect) {
            this.y = this.y < n2 ? (this.y += this.fvect) : (this.y -= this.fvect);
        } else {
            this.y = n2;
            ++n5;
        }
        if (Math.abs(n3 - this.x) > this.fvect) {
            this.x = this.x < n3 ? (this.x += this.fvect) : (this.x -= this.fvect);
        } else {
            this.x = n3;
            ++n5;
        }
        if (Math.abs(n4 - this.z) > this.fvect) {
            this.z = this.z < n4 ? (this.z += this.fvect) : (this.z -= this.fvect);
        } else {
            this.z = n4;
            ++n5;
        }
        this.fvect = n5 == 3 ? 200 : (this.fvect += 2);
        this.vxz += 2;
        while (this.vxz > 360) {
            this.vxz -= 360;
        }
        int n6 = -this.vxz + 90;
        int n7 = 0;
        if (contO.x - this.x - this.cx > 0) {
            n7 = 180;
        }
        int n8 = -((int)((double)(90 + n7) + Math.atan((double)(contO.z - this.z) / (double)(contO.x - this.x - this.cx)) / (Math.PI / 180)));
        int n9 = this.y;
        n7 = 0;
        if (n9 > 0) {
            n9 = 0;
        }
        if (contO.y - n9 - this.cy < 0) {
            n7 = -180;
        }
        int n10 = (int)Math.sqrt((contO.z - this.z + this.cz) * (contO.z - this.z + this.cz) + (contO.x - this.x - this.cx) * (contO.x - this.x - this.cx));
        int n11 = 25;
        if (n10 != 0) {
            n11 = (int)((double)(90 + n7) - Math.atan((double)n10 / (double)(contO.y - n9 - this.cy)) / (Math.PI / 180));
        }
        while (n6 < 0) {
            n6 += 360;
        }
        while (n6 > 360) {
            n6 -= 360;
        }
        while (n8 < 0) {
            n8 += 360;
        }
        while (n8 > 360) {
            n8 -= 360;
        }
        this.xz = (Math.abs(n6 - n8) < 30 || Math.abs(n6 - n8) > 330) && n5 == 3 ? (Math.abs(n6 - this.xz) > 7 && Math.abs(n6 - this.xz) < 353 ? (Math.abs(n6 - this.xz) > 180 ? (this.xz > n6 ? (this.xz += 7) : (this.xz -= 7)) : (this.xz < n6 ? (this.xz += 7) : (this.xz -= 7))) : n6) : (Math.abs(n8 - this.xz) > 6 && Math.abs(n8 - this.xz) < 354 ? (Math.abs(n8 - this.xz) > 180 ? (this.xz > n8 ? (this.xz += 3) : (this.xz -= 3)) : (this.xz < n8 ? (this.xz += 3) : (this.xz -= 3))) : n8);
        this.zy += (n11 - this.zy) / 10;
    }

    public void transaround(ContO contO, ContO contO2, int n) {
        int n2;
        int n3 = (contO.x * (20 - n) + contO2.x * n) / 20;
        int n4 = (contO.y * (20 - n) + contO2.y * n) / 20;
        int n5 = (contO.z * (20 - n) + contO2.z * n) / 20;
        this.adv = !this.vert ? (this.adv += 2) : (this.adv -= 2);
        if (this.adv > 900) {
            this.vert = true;
        }
        if (this.adv < -500) {
            this.vert = false;
        }
        if ((n2 = 500 + this.adv) < 1000) {
            n2 = 1000;
        }
        this.y = n4 - this.adv;
        if (this.y > 10) {
            this.vert = false;
        }
        this.x = n3 + (int)((float)(n3 - n2 - n3) * this.cos(this.vxz));
        this.z = n5 + (int)((float)(n3 - n2 - n3) * this.sin(this.vxz));
        this.vxz += 2;
        int n6 = 0;
        int n7 = this.y;
        if (n7 > 0) {
            n7 = 0;
        }
        if (n4 - n7 - this.cy < 0) {
            n6 = -180;
        }
        int n8 = (int)Math.sqrt((n5 - this.z + this.cz) * (n5 - this.z + this.cz) + (n3 - this.x - this.cx) * (n3 - this.x - this.cx));
        int n9 = (int)((double)(90 + n6) - Math.atan((double)n8 / (double)(n4 - n7 - this.cy)) / (Math.PI / 180));
        this.xz = -this.vxz + 90;
        this.zy += (n9 - this.zy) / 10;
    }

    public void follow(ContO contO, int n, int n2) {
        this.zy = 10;
        int n3 = 2 + Math.abs(this.bcxz) / 4;
        if (n3 > 20) {
            n3 = 20;
        }
        if (n2 != 0) {
            if (n2 == 1) {
                if (this.bcxz < 180) {
                    this.bcxz += n3;
                }
                if (this.bcxz > 180) {
                    this.bcxz = 180;
                }
            }
            if (n2 == -1) {
                if (this.bcxz > -180) {
                    this.bcxz -= n3;
                }
                if (this.bcxz < -180) {
                    this.bcxz = -180;
                }
            }
        } else if (Math.abs(this.bcxz) > n3) {
            this.bcxz = this.bcxz > 0 ? (this.bcxz -= n3) : (this.bcxz += n3);
        } else if (this.bcxz != 0) {
            this.bcxz = 0;
        }
        this.xz = -(n += this.bcxz);
        this.x = contO.x - this.cx + (int)((float)(-(contO.z - 800 - contO.z)) * this.sin(n));
        this.z = contO.z - this.cz + (int)((float)(contO.z - 800 - contO.z) * this.cos(n));
        this.y = contO.y - 250 - this.cy;
    }

    public void getfollow(ContO contO, int n, int n2) {
        this.zy = 10;
        int n3 = 2 + Math.abs(this.bcxz) / 4;
        if (n3 > 20) {
            n3 = 20;
        }
        if (n2 != 0) {
            if (n2 == 1) {
                if (this.bcxz < 180) {
                    this.bcxz += n3;
                }
                if (this.bcxz > 180) {
                    this.bcxz = 180;
                }
            }
            if (n2 == -1) {
                if (this.bcxz > -180) {
                    this.bcxz -= n3;
                }
                if (this.bcxz < -180) {
                    this.bcxz = -180;
                }
            }
        } else if (Math.abs(this.bcxz) > n3) {
            this.bcxz = this.bcxz > 0 ? (this.bcxz -= n3) : (this.bcxz += n3);
        } else if (this.bcxz != 0) {
            this.bcxz = 0;
        }
        this.xz = -(n += this.bcxz);
        int n4 = contO.x - this.cx + (int)((float)(-(contO.z - 800 - contO.z)) * this.sin(n));
        int n5 = contO.z - this.cz + (int)((float)(contO.z - 800 - contO.z) * this.cos(n));
        int n6 = contO.y - 250 - this.cy;
        int n7 = 0;
        if (Math.abs(n6 - this.y) > this.fvect) {
            this.y = this.y < n6 ? (this.y += this.fvect) : (this.y -= this.fvect);
        } else {
            this.y = n6;
            ++n7;
        }
        if (Math.abs(n4 - this.x) > this.fvect) {
            this.x = this.x < n4 ? (this.x += this.fvect) : (this.x -= this.fvect);
        } else {
            this.x = n4;
            ++n7;
        }
        if (Math.abs(n5 - this.z) > this.fvect) {
            this.z = this.z < n5 ? (this.z += this.fvect) : (this.z -= this.fvect);
        } else {
            this.z = n5;
            ++n7;
        }
        this.fvect = n7 == 3 ? 200 : (this.fvect += 2);
    }

    public void newpolys(int n, int n2, int n3, int n4, Trackers trackers, int n5) {
        int n6;
        Random random = new Random((n5 + this.cgrnd[0] + this.cgrnd[1] + this.cgrnd[2]) * 1671);
        this.nrw = n2 / 1200 + 9;
        this.ncl = n4 / 1200 + 9;
        this.sgpx = n - 4800;
        this.sgpz = n3 - 4800;
        this.ogpx = null;
        this.ogpz = null;
        this.pvr = null;
        this.cgpx = null;
        this.cgpz = null;
        this.pmx = null;
        this.pcv = null;
        this.ogpx = new int[this.nrw * this.ncl][8];
        this.ogpz = new int[this.nrw * this.ncl][8];
        this.pvr = new float[this.nrw * this.ncl][8];
        this.cgpx = new int[this.nrw * this.ncl];
        this.cgpz = new int[this.nrw * this.ncl];
        this.pmx = new int[this.nrw * this.ncl];
        this.pcv = new float[this.nrw * this.ncl];
        int n7 = 0;
        int n8 = 0;
        for (n6 = 0; n6 < this.nrw * this.ncl; ++n6) {
            this.cgpx[n6] = this.sgpx + n7 * 1200 + (int)(random.nextDouble() * 1000.0 - 500.0);
            this.cgpz[n6] = this.sgpz + n8 * 1200 + (int)(random.nextDouble() * 1000.0 - 500.0);
            if (trackers != null) {
                for (int i = 0; i < trackers.nt; ++i) {
                    if (trackers.zy[i] != 0 || trackers.xy[i] != 0) continue;
                    if (trackers.radx[i] < trackers.radz[i] && Math.abs(this.cgpz[n6] - trackers.z[i]) < trackers.radz[i]) {
                        while (Math.abs(this.cgpx[n6] - trackers.x[i]) < trackers.radx[i]) {
                            int n9 = n6;
                            this.cgpx[n9] = (int)((double)this.cgpx[n9] + (random.nextDouble() * (double)trackers.radx[i] * 2.0 - (double)trackers.radx[i]));
                        }
                    }
                    if (trackers.radz[i] >= trackers.radx[i] || Math.abs(this.cgpx[n6] - trackers.x[i]) >= trackers.radx[i]) continue;
                    while (Math.abs(this.cgpz[n6] - trackers.z[i]) < trackers.radz[i]) {
                        int n10 = n6;
                        this.cgpz[n10] = (int)((double)this.cgpz[n10] + (random.nextDouble() * (double)trackers.radz[i] * 2.0 - (double)trackers.radz[i]));
                    }
                }
            }
            if (++n7 != this.nrw) continue;
            n7 = 0;
            ++n8;
        }
        for (n6 = 0; n6 < this.nrw * this.ncl; ++n6) {
            float f = (float)(0.3 + 1.6 * random.nextDouble());
            this.ogpx[n6][0] = 0;
            this.ogpz[n6][0] = (int)((100.0 + random.nextDouble() * 760.0) * (double)f);
            this.ogpx[n6][1] = (int)((100.0 + random.nextDouble() * 760.0) * 0.7071 * (double)f);
            this.ogpz[n6][1] = this.ogpx[n6][1];
            this.ogpx[n6][2] = (int)((100.0 + random.nextDouble() * 760.0) * (double)f);
            this.ogpz[n6][2] = 0;
            this.ogpx[n6][3] = (int)((100.0 + random.nextDouble() * 760.0) * 0.7071 * (double)f);
            this.ogpz[n6][3] = -this.ogpx[n6][3];
            this.ogpx[n6][4] = 0;
            this.ogpz[n6][4] = -((int)((100.0 + random.nextDouble() * 760.0) * (double)f));
            this.ogpx[n6][5] = -((int)((100.0 + random.nextDouble() * 760.0) * 0.7071 * (double)f));
            this.ogpz[n6][5] = this.ogpx[n6][5];
            this.ogpx[n6][6] = -((int)((100.0 + random.nextDouble() * 760.0) * (double)f));
            this.ogpz[n6][6] = 0;
            this.ogpx[n6][7] = -((int)((100.0 + random.nextDouble() * 760.0) * 0.7071 * (double)f));
            this.ogpz[n6][7] = -this.ogpx[n6][7];
            for (int i = 0; i < 8; ++i) {
                int n11;
                int n12 = i - 1;
                if (n12 == -1) {
                    n12 = 7;
                }
                if ((n11 = i + 1) == 8) {
                    n11 = 0;
                }
                this.ogpx[n6][i] = ((this.ogpx[n6][n12] + this.ogpx[n6][n11]) / 2 + this.ogpx[n6][i]) / 2;
                this.ogpz[n6][i] = ((this.ogpz[n6][n12] + this.ogpz[n6][n11]) / 2 + this.ogpz[n6][i]) / 2;
                this.pvr[n6][i] = (float)(1.1 + random.nextDouble() * 0.8);
                int n13 = (int)Math.sqrt((int)((float)(this.ogpx[n6][i] * this.ogpx[n6][i]) * this.pvr[n6][i] * this.pvr[n6][i] + (float)(this.ogpz[n6][i] * this.ogpz[n6][i]) * this.pvr[n6][i] * this.pvr[n6][i]));
                if (n13 <= this.pmx[n6]) continue;
                this.pmx[n6] = n13;
            }
            this.pcv[n6] = (float)(0.97 + random.nextDouble() * 0.03);
            if (this.pcv[n6] > 1.0f) {
                this.pcv[n6] = 1.0f;
            }
            if (!(random.nextDouble() > random.nextDouble())) continue;
            this.pcv[n6] = 1.0f;
        }
    }

    public void groundpolys(Graphics2D graphics2D) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int[] nArray;
        int[] nArray2;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12 = (this.x - this.sgpx) / 1200 - 12;
        if (n12 < 0) {
            n12 = 0;
        }
        if ((n11 = n12 + 25) > this.nrw) {
            n11 = this.nrw;
        }
        if (n11 < n12) {
            n11 = n12;
        }
        if ((n10 = (this.z - this.sgpz) / 1200 - 12) < 0) {
            n10 = 0;
        }
        if ((n9 = n10 + 25) > this.ncl) {
            n9 = this.ncl;
        }
        if (n9 < n10) {
            n9 = n10;
        }
        int[][] nArray3 = new int[n11 - n12][n9 - n10];
        for (n8 = n12; n8 < n11; ++n8) {
            for (n7 = n10; n7 < n9; ++n7) {
                int n13;
                nArray3[n8 - n12][n7 - n10] = 0;
                n6 = n8 + n7 * this.nrw;
                if (this.resdown >= 2 && n6 % 2 != 0) continue;
                int n14 = this.cx + (int)((float)(this.cgpx[n6] - this.x - this.cx) * this.cos(this.xz) - (float)(this.cgpz[n6] - this.z - this.cz) * this.sin(this.xz));
                int n15 = this.cz + (int)((float)(this.cgpx[n6] - this.x - this.cx) * this.sin(this.xz) + (float)(this.cgpz[n6] - this.z - this.cz) * this.cos(this.xz));
                int n16 = this.cz + (int)((float)(250 - this.y - this.cy) * this.sin(this.zy) + (float)(n15 - this.cz) * this.cos(this.zy));
                if (this.xs(n14 + this.pmx[n6], n16) <= 0 || this.xs(n14 - this.pmx[n6], n16) >= this.w || n16 <= -this.pmx[n6] || n16 >= this.fade[2]) continue;
                nArray3[n8 - n12][n7 - n10] = n16;
                nArray2 = new int[8];
                nArray = new int[8];
                int[] nArray4 = new int[8];
                for (int i = 0; i < 8; ++i) {
                    nArray2[i] = (int)((float)this.ogpx[n6][i] * this.pvr[n6][i] + (float)this.cgpx[n6] - (float)this.x);
                    nArray[i] = (int)((float)this.ogpz[n6][i] * this.pvr[n6][i] + (float)this.cgpz[n6] - (float)this.z);
                    nArray4[i] = this.ground;
                }
                this.rot(nArray2, nArray, this.cx, this.cz, this.xz, 8);
                this.rot(nArray4, nArray, this.cy, this.cz, this.zy, 8);
                int[] nArray5 = new int[8];
                int[] nArray6 = new int[8];
                n5 = 0;
                n4 = 0;
                n3 = 0;
                n2 = 0;
                n = 1;
                for (n13 = 0; n13 < 8; ++n13) {
                    nArray5[n13] = this.xs(nArray2[n13], nArray[n13]);
                    nArray6[n13] = this.ys(nArray4[n13], nArray[n13]);
                    if (nArray6[n13] < 0 || nArray[n13] < 10) {
                        ++n5;
                    }
                    if (nArray6[n13] > this.h || nArray[n13] < 10) {
                        ++n4;
                    }
                    if (nArray5[n13] < 0 || nArray[n13] < 10) {
                        ++n3;
                    }
                    if (nArray5[n13] <= this.w && nArray[n13] >= 10) continue;
                    ++n2;
                }
                if (n3 == 8 || n5 == 8 || n4 == 8 || n2 == 8) {
                    n = 0;
                }
                if (n == 0) continue;
                n13 = (int)(((float)this.cpol[0] * this.pcv[n6] + (float)this.cgrnd[0]) / 2.0f);
                int n17 = (int)(((float)this.cpol[1] * this.pcv[n6] + (float)this.cgrnd[1]) / 2.0f);
                int n18 = (int)(((float)this.cpol[2] * this.pcv[n6] + (float)this.cgrnd[2]) / 2.0f);
                if (n16 - this.pmx[n6] > this.fade[0]) {
                    n13 = (n13 * 7 + this.cfade[0]) / 8;
                    n17 = (n17 * 7 + this.cfade[1]) / 8;
                    n18 = (n18 * 7 + this.cfade[2]) / 8;
                }
                if (n16 - this.pmx[n6] > this.fade[1]) {
                    n13 = (n13 * 7 + this.cfade[0]) / 8;
                    n17 = (n17 * 7 + this.cfade[1]) / 8;
                    n18 = (n18 * 7 + this.cfade[2]) / 8;
                }
                graphics2D.setColor(new Color(n13, n17, n18));
                graphics2D.fillPolygon(nArray5, nArray6, 8);
            }
        }
        for (n8 = n12; n8 < n11; ++n8) {
            for (n7 = n10; n7 < n9; ++n7) {
                if (nArray3[n8 - n12][n7 - n10] == 0) continue;
                n6 = n8 + n7 * this.nrw;
                int[] nArray7 = new int[8];
                int[] nArray8 = new int[8];
                int[] nArray9 = new int[8];
                for (int i = 0; i < 8; ++i) {
                    nArray7[i] = this.ogpx[n6][i] + this.cgpx[n6] - this.x;
                    nArray8[i] = this.ogpz[n6][i] + this.cgpz[n6] - this.z;
                    nArray9[i] = this.ground;
                }
                this.rot(nArray7, nArray8, this.cx, this.cz, this.xz, 8);
                this.rot(nArray9, nArray8, this.cy, this.cz, this.zy, 8);
                nArray2 = new int[8];
                nArray = new int[8];
                int n19 = 0;
                int n20 = 0;
                int n21 = 0;
                n5 = 0;
                n4 = 1;
                for (n3 = 0; n3 < 8; ++n3) {
                    nArray2[n3] = this.xs(nArray7[n3], nArray8[n3]);
                    nArray[n3] = this.ys(nArray9[n3], nArray8[n3]);
                    if (nArray[n3] < 0 || nArray8[n3] < 10) {
                        ++n19;
                    }
                    if (nArray[n3] > this.h || nArray8[n3] < 10) {
                        ++n20;
                    }
                    if (nArray2[n3] < 0 || nArray8[n3] < 10) {
                        ++n21;
                    }
                    if (nArray2[n3] <= this.w && nArray8[n3] >= 10) continue;
                    ++n5;
                }
                if (n21 == 8 || n19 == 8 || n20 == 8 || n5 == 8) {
                    n4 = 0;
                }
                if (n4 == 0) continue;
                n3 = (int)((float)this.cpol[0] * this.pcv[n6]);
                n2 = (int)((float)this.cpol[1] * this.pcv[n6]);
                n = (int)((float)this.cpol[2] * this.pcv[n6]);
                if (nArray3[n8 - n12][n7 - n10] - this.pmx[n6] > this.fade[0]) {
                    n3 = (n3 * 7 + this.cfade[0]) / 8;
                    n2 = (n2 * 7 + this.cfade[1]) / 8;
                    n = (n * 7 + this.cfade[2]) / 8;
                }
                if (nArray3[n8 - n12][n7 - n10] - this.pmx[n6] > this.fade[1]) {
                    n3 = (n3 * 7 + this.cfade[0]) / 8;
                    n2 = (n2 * 7 + this.cfade[1]) / 8;
                    n = (n * 7 + this.cfade[2]) / 8;
                }
                graphics2D.setColor(new Color(n3, n2, n));
                graphics2D.fillPolygon(nArray2, nArray, 8);
            }
        }
    }

    public void newclouds(int n, int n2, int n3, int n4) {
        this.clx = null;
        this.clz = null;
        this.cmx = null;
        this.clax = null;
        this.clay = null;
        this.claz = null;
        this.clc = null;
        n = n / 20 - 10000;
        n2 = n2 / 20 + 10000;
        n3 = n3 / 20 - 10000;
        n4 = n4 / 20 + 10000;
        this.noc = (n2 - n) * (n4 - n3) / 16666667;
        this.clx = new int[this.noc];
        this.clz = new int[this.noc];
        this.cmx = new int[this.noc];
        this.clax = new int[this.noc][3][12];
        this.clay = new int[this.noc][3][12];
        this.claz = new int[this.noc][3][12];
        this.clc = new int[this.noc][2][6][3];
        for (int i = 0; i < this.noc; ++i) {
            int n5;
            int n6;
            int n7;
            this.clx[i] = (int)((double)n + (double)(n2 - n) * Math.random());
            this.clz[i] = (int)((double)n3 + (double)(n4 - n3) * Math.random());
            float f = (float)(0.25 + Math.random() * 1.25);
            float f2 = (float)((200.0 + Math.random() * 700.0) * (double)f);
            this.clax[i][0][0] = (int)((double)f2 * 0.3826);
            this.claz[i][0][0] = (int)((double)f2 * 0.9238);
            this.clay[i][0][0] = (int)((25.0 - Math.random() * 50.0) * (double)f);
            f2 = (float)((200.0 + Math.random() * 700.0) * (double)f);
            this.clax[i][0][1] = (int)((double)f2 * 0.7071);
            this.claz[i][0][1] = (int)((double)f2 * 0.7071);
            this.clay[i][0][1] = (int)((25.0 - Math.random() * 50.0) * (double)f);
            f2 = (float)((200.0 + Math.random() * 700.0) * (double)f);
            this.clax[i][0][2] = (int)((double)f2 * 0.9238);
            this.claz[i][0][2] = (int)((double)f2 * 0.3826);
            this.clay[i][0][2] = (int)((25.0 - Math.random() * 50.0) * (double)f);
            f2 = (float)((200.0 + Math.random() * 700.0) * (double)f);
            this.clax[i][0][3] = (int)((double)f2 * 0.9238);
            this.claz[i][0][3] = -((int)((double)f2 * 0.3826));
            this.clay[i][0][3] = (int)((25.0 - Math.random() * 50.0) * (double)f);
            f2 = (float)((200.0 + Math.random() * 700.0) * (double)f);
            this.clax[i][0][4] = (int)((double)f2 * 0.7071);
            this.claz[i][0][4] = -((int)((double)f2 * 0.7071));
            this.clay[i][0][4] = (int)((25.0 - Math.random() * 50.0) * (double)f);
            f2 = (float)((200.0 + Math.random() * 700.0) * (double)f);
            this.clax[i][0][5] = (int)((double)f2 * 0.3826);
            this.claz[i][0][5] = -((int)((double)f2 * 0.9238));
            this.clay[i][0][5] = (int)((25.0 - Math.random() * 50.0) * (double)f);
            f2 = (float)((200.0 + Math.random() * 700.0) * (double)f);
            this.clax[i][0][6] = -((int)((double)f2 * 0.3826));
            this.claz[i][0][6] = -((int)((double)f2 * 0.9238));
            this.clay[i][0][6] = (int)((25.0 - Math.random() * 50.0) * (double)f);
            f2 = (float)((200.0 + Math.random() * 700.0) * (double)f);
            this.clax[i][0][7] = -((int)((double)f2 * 0.7071));
            this.claz[i][0][7] = -((int)((double)f2 * 0.7071));
            this.clay[i][0][7] = (int)((25.0 - Math.random() * 50.0) * (double)f);
            f2 = (float)((200.0 + Math.random() * 700.0) * (double)f);
            this.clax[i][0][8] = -((int)((double)f2 * 0.9238));
            this.claz[i][0][8] = -((int)((double)f2 * 0.3826));
            this.clay[i][0][8] = (int)((25.0 - Math.random() * 50.0) * (double)f);
            f2 = (float)((200.0 + Math.random() * 700.0) * (double)f);
            this.clax[i][0][9] = -((int)((double)f2 * 0.9238));
            this.claz[i][0][9] = (int)((double)f2 * 0.3826);
            this.clay[i][0][9] = (int)((25.0 - Math.random() * 50.0) * (double)f);
            f2 = (float)((200.0 + Math.random() * 700.0) * (double)f);
            this.clax[i][0][10] = -((int)((double)f2 * 0.7071));
            this.claz[i][0][10] = (int)((double)f2 * 0.7071);
            this.clay[i][0][10] = (int)((25.0 - Math.random() * 50.0) * (double)f);
            f2 = (float)((200.0 + Math.random() * 700.0) * (double)f);
            this.clax[i][0][11] = -((int)((double)f2 * 0.3826));
            this.claz[i][0][11] = (int)((double)f2 * 0.9238);
            this.clay[i][0][11] = (int)((25.0 - Math.random() * 50.0) * (double)f);
            for (n7 = 0; n7 < 12; ++n7) {
                n6 = n7 - 1;
                if (n6 == -1) {
                    n6 = 11;
                }
                if ((n5 = n7 + 1) == 12) {
                    n5 = 0;
                }
                this.clax[i][0][n7] = ((this.clax[i][0][n6] + this.clax[i][0][n5]) / 2 + this.clax[i][0][n7]) / 2;
                this.clay[i][0][n7] = ((this.clay[i][0][n6] + this.clay[i][0][n5]) / 2 + this.clay[i][0][n7]) / 2;
                this.claz[i][0][n7] = ((this.claz[i][0][n6] + this.claz[i][0][n5]) / 2 + this.claz[i][0][n7]) / 2;
            }
            for (n7 = 0; n7 < 12; ++n7) {
                f2 = (float)(1.2 + 0.6 * Math.random());
                this.clax[i][1][n7] = (int)((float)this.clax[i][0][n7] * f2);
                this.claz[i][1][n7] = (int)((float)this.claz[i][0][n7] * f2);
                this.clay[i][1][n7] = (int)((double)this.clay[i][0][n7] - 100.0 * Math.random());
                f2 = (float)(1.1 + 0.3 * Math.random());
                this.clax[i][2][n7] = (int)((float)this.clax[i][1][n7] * f2);
                this.claz[i][2][n7] = (int)((float)this.claz[i][1][n7] * f2);
                this.clay[i][2][n7] = (int)((double)this.clay[i][1][n7] - 240.0 * Math.random());
            }
            this.cmx[i] = 0;
            for (n7 = 0; n7 < 12; ++n7) {
                n6 = n7 - 1;
                if (n6 == -1) {
                    n6 = 11;
                }
                if ((n5 = n7 + 1) == 12) {
                    n5 = 0;
                }
                this.clay[i][1][n7] = ((this.clay[i][1][n6] + this.clay[i][1][n5]) / 2 + this.clay[i][1][n7]) / 2;
                this.clay[i][2][n7] = ((this.clay[i][2][n6] + this.clay[i][2][n5]) / 2 + this.clay[i][2][n7]) / 2;
                int n8 = (int)Math.sqrt(this.clax[i][2][n7] * this.clax[i][2][n7] + this.claz[i][2][n7] * this.claz[i][2][n7]);
                if (n8 <= this.cmx[i]) continue;
                this.cmx[i] = n8;
            }
            for (n7 = 0; n7 < 6; ++n7) {
                double d = Math.random();
                double d2 = Math.random();
                for (int j = 0; j < 3; ++j) {
                    f2 = (float)this.clds[j] * 1.05f - (float)this.clds[j];
                    this.clc[i][0][n7][j] = (int)((double)this.clds[j] + (double)f2 * d);
                    if (this.clc[i][0][n7][j] > 255) {
                        this.clc[i][0][n7][j] = 255;
                    }
                    if (this.clc[i][0][n7][j] < 0) {
                        this.clc[i][0][n7][j] = 0;
                    }
                    this.clc[i][1][n7][j] = (int)((double)((float)this.clds[j] * 1.05f) + (double)f2 * d2);
                    if (this.clc[i][1][n7][j] > 255) {
                        this.clc[i][1][n7][j] = 255;
                    }
                    if (this.clc[i][1][n7][j] >= 0) continue;
                    this.clc[i][1][n7][j] = 0;
                }
            }
        }
    }

    public void drawclouds(Graphics2D graphics2D) {
        for (int i = 0; i < this.noc; ++i) {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7 = this.cx + (int)((float)(this.clx[i] - this.x / 20 - this.cx) * this.cos(this.xz) - (float)(this.clz[i] - this.z / 20 - this.cz) * this.sin(this.xz));
            int n8 = this.cz + (int)((float)(this.clx[i] - this.x / 20 - this.cx) * this.sin(this.xz) + (float)(this.clz[i] - this.z / 20 - this.cz) * this.cos(this.xz));
            int n9 = this.cz + (int)((float)(this.cldd[4] - this.y / 20 - this.cy) * this.sin(this.zy) + (float)(n8 - this.cz) * this.cos(this.zy));
            int n10 = this.xs(n7 + this.cmx[i], n9);
            int n11 = this.xs(n7 - this.cmx[i], n9);
            if (n10 <= 0 || n11 >= this.w || n9 <= -this.cmx[i] || n10 - n11 <= 20) continue;
            int[][] nArray = new int[3][12];
            int[][] nArray2 = new int[3][12];
            int[][] nArray3 = new int[3][12];
            int[] nArray4 = new int[12];
            int[] nArray5 = new int[12];
            int n12 = 0;
            int n13 = 0;
            int n14 = 0;
            int n15 = 0;
            boolean bl = true;
            int n16 = 0;
            int n17 = 0;
            int n18 = 0;
            for (n6 = 0; n6 < 3; ++n6) {
                for (n5 = 0; n5 < 12; ++n5) {
                    nArray[n6][n5] = this.clax[i][n6][n5] + this.clx[i] - this.x / 20;
                    nArray3[n6][n5] = this.claz[i][n6][n5] + this.clz[i] - this.z / 20;
                    nArray2[n6][n5] = this.clay[i][n6][n5] + this.cldd[4] - this.y / 20;
                }
                this.rot(nArray[n6], nArray3[n6], this.cx, this.cz, this.xz, 12);
                this.rot(nArray2[n6], nArray3[n6], this.cy, this.cz, this.zy, 12);
            }
            for (n6 = 0; n6 < 12; n6 += 2) {
                n12 = 0;
                n13 = 0;
                n14 = 0;
                n15 = 0;
                bl = true;
                n16 = 0;
                n17 = 0;
                n18 = 0;
                for (n5 = 0; n5 < 6; ++n5) {
                    n4 = 0;
                    n3 = 1;
                    if (n5 == 0) {
                        n4 = n6;
                    }
                    if (n5 == 1 && (n4 = n6 + 1) >= 12) {
                        n4 -= 12;
                    }
                    if (n5 == 2 && (n4 = n6 + 2) >= 12) {
                        n4 -= 12;
                    }
                    if (n5 == 3) {
                        n4 = n6 + 2;
                        if (n4 >= 12) {
                            n4 -= 12;
                        }
                        n3 = 2;
                    }
                    if (n5 == 4) {
                        n4 = n6 + 1;
                        if (n4 >= 12) {
                            n4 -= 12;
                        }
                        n3 = 2;
                    }
                    if (n5 == 5) {
                        n4 = n6;
                        n3 = 2;
                    }
                    nArray4[n5] = this.xs(nArray[n3][n4], nArray3[n3][n4]);
                    nArray5[n5] = this.ys(nArray2[n3][n4], nArray3[n3][n4]);
                    n17 += nArray[n3][n4];
                    n16 += nArray2[n3][n4];
                    n18 += nArray3[n3][n4];
                    if (nArray5[n5] < 0 || nArray3[0][n5] < 10) {
                        ++n12;
                    }
                    if (nArray5[n5] > this.h || nArray3[0][n5] < 10) {
                        ++n13;
                    }
                    if (nArray4[n5] < 0 || nArray3[0][n5] < 10) {
                        ++n14;
                    }
                    if (nArray4[n5] <= this.w && nArray3[0][n5] >= 10) continue;
                    ++n15;
                }
                if (n14 == 6 || n12 == 6 || n13 == 6 || n15 == 6) {
                    bl = false;
                }
                if (!bl || (n5 = (int)Math.sqrt((this.cy - (n16 /= 6)) * (this.cy - n16) + (this.cx - (n17 /= 6)) * (this.cx - n17) + (n18 /= 6) * n18)) >= this.fade[7]) continue;
                n4 = this.clc[i][1][n6 / 2][0];
                n3 = this.clc[i][1][n6 / 2][1];
                n2 = this.clc[i][1][n6 / 2][2];
                for (n = 0; n < 16; ++n) {
                    if (n5 <= this.fade[n]) continue;
                    n4 = (n4 * this.fogd + this.cfade[0]) / (this.fogd + 1);
                    n3 = (n3 * this.fogd + this.cfade[1]) / (this.fogd + 1);
                    n2 = (n2 * this.fogd + this.cfade[2]) / (this.fogd + 1);
                }
                graphics2D.setColor(new Color(n4, n3, n2));
                graphics2D.fillPolygon(nArray4, nArray5, 6);
            }
            for (n6 = 0; n6 < 12; n6 += 2) {
                n12 = 0;
                n13 = 0;
                n14 = 0;
                n15 = 0;
                bl = true;
                n16 = 0;
                n17 = 0;
                n18 = 0;
                for (n5 = 0; n5 < 6; ++n5) {
                    n4 = 0;
                    n3 = 0;
                    if (n5 == 0) {
                        n4 = n6;
                    }
                    if (n5 == 1 && (n4 = n6 + 1) >= 12) {
                        n4 -= 12;
                    }
                    if (n5 == 2 && (n4 = n6 + 2) >= 12) {
                        n4 -= 12;
                    }
                    if (n5 == 3) {
                        n4 = n6 + 2;
                        if (n4 >= 12) {
                            n4 -= 12;
                        }
                        n3 = 1;
                    }
                    if (n5 == 4) {
                        n4 = n6 + 1;
                        if (n4 >= 12) {
                            n4 -= 12;
                        }
                        n3 = 1;
                    }
                    if (n5 == 5) {
                        n4 = n6;
                        n3 = 1;
                    }
                    nArray4[n5] = this.xs(nArray[n3][n4], nArray3[n3][n4]);
                    nArray5[n5] = this.ys(nArray2[n3][n4], nArray3[n3][n4]);
                    n17 += nArray[n3][n4];
                    n16 += nArray2[n3][n4];
                    n18 += nArray3[n3][n4];
                    if (nArray5[n5] < 0 || nArray3[0][n5] < 10) {
                        ++n12;
                    }
                    if (nArray5[n5] > this.h || nArray3[0][n5] < 10) {
                        ++n13;
                    }
                    if (nArray4[n5] < 0 || nArray3[0][n5] < 10) {
                        ++n14;
                    }
                    if (nArray4[n5] <= this.w && nArray3[0][n5] >= 10) continue;
                    ++n15;
                }
                if (n14 == 6 || n12 == 6 || n13 == 6 || n15 == 6) {
                    bl = false;
                }
                if (!bl || (n5 = (int)Math.sqrt((this.cy - (n16 /= 6)) * (this.cy - n16) + (this.cx - (n17 /= 6)) * (this.cx - n17) + (n18 /= 6) * n18)) >= this.fade[7]) continue;
                n4 = this.clc[i][0][n6 / 2][0];
                n3 = this.clc[i][0][n6 / 2][1];
                n2 = this.clc[i][0][n6 / 2][2];
                for (n = 0; n < 16; ++n) {
                    if (n5 <= this.fade[n]) continue;
                    n4 = (n4 * this.fogd + this.cfade[0]) / (this.fogd + 1);
                    n3 = (n3 * this.fogd + this.cfade[1]) / (this.fogd + 1);
                    n2 = (n2 * this.fogd + this.cfade[2]) / (this.fogd + 1);
                }
                graphics2D.setColor(new Color(n4, n3, n2));
                graphics2D.fillPolygon(nArray4, nArray5, 6);
            }
            n12 = 0;
            n13 = 0;
            n14 = 0;
            n15 = 0;
            bl = true;
            n16 = 0;
            n17 = 0;
            n18 = 0;
            for (n6 = 0; n6 < 12; ++n6) {
                nArray4[n6] = this.xs(nArray[0][n6], nArray3[0][n6]);
                nArray5[n6] = this.ys(nArray2[0][n6], nArray3[0][n6]);
                n17 += nArray[0][n6];
                n16 += nArray2[0][n6];
                n18 += nArray3[0][n6];
                if (nArray5[n6] < 0 || nArray3[0][n6] < 10) {
                    ++n12;
                }
                if (nArray5[n6] > this.h || nArray3[0][n6] < 10) {
                    ++n13;
                }
                if (nArray4[n6] < 0 || nArray3[0][n6] < 10) {
                    ++n14;
                }
                if (nArray4[n6] <= this.w && nArray3[0][n6] >= 10) continue;
                ++n15;
            }
            if (n14 == 12 || n12 == 12 || n13 == 12 || n15 == 12) {
                bl = false;
            }
            if (!bl || (n6 = (int)Math.sqrt((this.cy - (n16 /= 12)) * (this.cy - n16) + (this.cx - (n17 /= 12)) * (this.cx - n17) + (n18 /= 12) * n18)) >= this.fade[7]) continue;
            n5 = this.clds[0];
            n4 = this.clds[1];
            n3 = this.clds[2];
            for (n2 = 0; n2 < 16; ++n2) {
                if (n6 <= this.fade[n2]) continue;
                n5 = (n5 * this.fogd + this.cfade[0]) / (this.fogd + 1);
                n4 = (n4 * this.fogd + this.cfade[1]) / (this.fogd + 1);
                n3 = (n3 * this.fogd + this.cfade[2]) / (this.fogd + 1);
            }
            graphics2D.setColor(new Color(n5, n4, n3));
            graphics2D.fillPolygon(nArray4, nArray5, 12);
        }
    }

    public void newmountains(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        Random random = new Random(this.mgen);
        this.nmt = (int)(20.0 + 10.0 * random.nextDouble());
        int n7 = (n + n2) / 60;
        int n8 = (n3 + n4) / 60;
        int n9 = Math.max(n2 - n, n4 - n3) / 60;
        this.mrd = null;
        this.nmv = null;
        this.mtx = null;
        this.mty = null;
        this.mtz = null;
        this.mtc = null;
        this.mrd = new int[this.nmt];
        this.nmv = new int[this.nmt];
        this.mtx = new int[this.nmt][];
        this.mty = new int[this.nmt][];
        this.mtz = new int[this.nmt][];
        this.mtc = new int[this.nmt][][];
        int[] nArray = new int[this.nmt];
        int[] nArray2 = new int[this.nmt];
        for (n6 = 0; n6 < this.nmt; ++n6) {
            int n10;
            n5 = 85;
            float f = 0.5f;
            float f2 = 0.5f;
            nArray[n6] = (int)(10000.0 + random.nextDouble() * 10000.0);
            int n11 = (int)(random.nextDouble() * 360.0);
            if (random.nextDouble() > random.nextDouble()) {
                f = (float)(0.2 + random.nextDouble() * 0.35);
                f2 = (float)(0.2 + random.nextDouble() * 0.35);
                this.nmv[n6] = (int)((double)f * (24.0 + 16.0 * random.nextDouble()));
                n5 = (int)(85.0 + 10.0 * random.nextDouble());
            } else {
                f = (float)(0.3 + random.nextDouble() * 1.1);
                f2 = (float)(0.2 + random.nextDouble() * 0.35);
                this.nmv[n6] = (int)((double)f * (12.0 + 8.0 * random.nextDouble()));
                n5 = (int)(104.0 - 10.0 * random.nextDouble());
            }
            this.mtx[n6] = new int[this.nmv[n6] * 2];
            this.mty[n6] = new int[this.nmv[n6] * 2];
            this.mtz[n6] = new int[this.nmv[n6] * 2];
            this.mtc[n6] = new int[this.nmv[n6]][3];
            for (n10 = 0; n10 < this.nmv[n6]; ++n10) {
                this.mtx[n6][n10] = (int)(((double)(n10 * 500) + (random.nextDouble() * 800.0 - 400.0) - (double)(250 * (this.nmv[n6] - 1))) * (double)f);
                this.mtx[n6][n10 + this.nmv[n6]] = (int)(((double)(n10 * 500) + (random.nextDouble() * 800.0 - 400.0) - (double)(250 * (this.nmv[n6] - 1))) * (double)f);
                this.mtx[n6][this.nmv[n6]] = (int)((double)this.mtx[n6][0] - (100.0 + random.nextDouble() * 600.0) * (double)f);
                this.mtx[n6][this.nmv[n6] * 2 - 1] = (int)((double)this.mtx[n6][this.nmv[n6] - 1] + (100.0 + random.nextDouble() * 600.0) * (double)f);
                if (n10 == 0 || n10 == this.nmv[n6] - 1) {
                    this.mty[n6][n10] = (int)((-400.0 - 1200.0 * random.nextDouble()) * (double)f2 + (double)this.ground);
                }
                if (n10 == 1 || n10 == this.nmv[n6] - 2) {
                    this.mty[n6][n10] = (int)((-1000.0 - 1450.0 * random.nextDouble()) * (double)f2 + (double)this.ground);
                }
                if (n10 > 1 && n10 < this.nmv[n6] - 2) {
                    this.mty[n6][n10] = (int)((-1600.0 - 1700.0 * random.nextDouble()) * (double)f2 + (double)this.ground);
                }
                this.mty[n6][n10 + this.nmv[n6]] = this.ground - 70;
                this.mtz[n6][n10] = n8 + n9 + nArray[n6];
                this.mtz[n6][n10 + this.nmv[n6]] = n8 + n9 + nArray[n6];
                float f3 = (float)(0.5 + random.nextDouble() * 0.5);
                this.mtc[n6][n10][0] = (int)(170.0f * f3 + 170.0f * f3 * ((float)this.snap[0] / 100.0f));
                if (this.mtc[n6][n10][0] > 255) {
                    this.mtc[n6][n10][0] = 255;
                }
                if (this.mtc[n6][n10][0] < 0) {
                    this.mtc[n6][n10][0] = 0;
                }
                this.mtc[n6][n10][1] = (int)((float)n5 * f3 + 85.0f * f3 * ((float)this.snap[1] / 100.0f));
                if (this.mtc[n6][n10][1] > 255) {
                    this.mtc[n6][n10][1] = 255;
                }
                if (this.mtc[n6][n10][1] < 1) {
                    this.mtc[n6][n10][1] = 0;
                }
                this.mtc[n6][n10][2] = 0;
            }
            for (n10 = 1; n10 < this.nmv[n6] - 1; ++n10) {
                int n12 = n10 - 1;
                int n13 = n10 + 1;
                this.mty[n6][n10] = ((this.mty[n6][n12] + this.mty[n6][n13]) / 2 + this.mty[n6][n10]) / 2;
            }
            this.rot(this.mtx[n6], this.mtz[n6], n7, n8, n11, this.nmv[n6] * 2);
            nArray2[n6] = 0;
        }
        for (n6 = 0; n6 < this.nmt; ++n6) {
            for (n5 = n6 + 1; n5 < this.nmt; ++n5) {
                if (nArray[n6] < nArray[n5]) {
                    int n14 = n6;
                    nArray2[n14] = nArray2[n14] + 1;
                    continue;
                }
                int n15 = n5;
                nArray2[n15] = nArray2[n15] + 1;
            }
            this.mrd[nArray2[n6]] = n6;
        }
    }

    public void drawmountains(Graphics2D graphics2D) {
        for (int i = 0; i < this.nmt; ++i) {
            int n;
            int n2 = this.mrd[i];
            int n3 = this.cx + (int)((float)(this.mtx[n2][0] - this.x / 30 - this.cx) * this.cos(this.xz) - (float)(this.mtz[n2][0] - this.z / 30 - this.cz) * this.sin(this.xz));
            int n4 = this.cz + (int)((float)(this.mtx[n2][0] - this.x / 30 - this.cx) * this.sin(this.xz) + (float)(this.mtz[n2][0] - this.z / 30 - this.cz) * this.cos(this.xz));
            int n5 = this.cz + (int)((float)(this.mty[n2][0] - this.y / 30 - this.cy) * this.sin(this.zy) + (float)(n4 - this.cz) * this.cos(this.zy));
            int n6 = this.cx + (int)((float)(this.mtx[n2][this.nmv[n2] - 1] - this.x / 30 - this.cx) * this.cos(this.xz) - (float)(this.mtz[n2][this.nmv[n2] - 1] - this.z / 30 - this.cz) * this.sin(this.xz));
            int n7 = this.cz + (int)((float)(this.mtx[n2][this.nmv[n2] - 1] - this.x / 30 - this.cx) * this.sin(this.xz) + (float)(this.mtz[n2][this.nmv[n2] - 1] - this.z / 30 - this.cz) * this.cos(this.xz));
            int n8 = this.cz + (int)((float)(this.mty[n2][this.nmv[n2] - 1] - this.y / 30 - this.cy) * this.sin(this.zy) + (float)(n7 - this.cz) * this.cos(this.zy));
            if (this.xs(n6, n8) <= 0 || this.xs(n3, n5) >= this.w) continue;
            int[] nArray = new int[this.nmv[n2] * 2];
            int[] nArray2 = new int[this.nmv[n2] * 2];
            int[] nArray3 = new int[this.nmv[n2] * 2];
            for (n = 0; n < this.nmv[n2] * 2; ++n) {
                nArray[n] = this.mtx[n2][n] - this.x / 30;
                nArray2[n] = this.mty[n2][n] - this.y / 30;
                nArray3[n] = this.mtz[n2][n] - this.z / 30;
            }
            n = (int)Math.sqrt(nArray[this.nmv[n2] / 4] * nArray[this.nmv[n2] / 4] + nArray3[this.nmv[n2] / 4] * nArray3[this.nmv[n2] / 4]);
            this.rot(nArray, nArray3, this.cx, this.cz, this.xz, this.nmv[n2] * 2);
            this.rot(nArray2, nArray3, this.cy, this.cz, this.zy, this.nmv[n2] * 2);
            int[] nArray4 = new int[4];
            int[] nArray5 = new int[4];
            int n9 = 0;
            int n10 = 0;
            int n11 = 0;
            int n12 = 0;
            boolean bl = true;
            for (int j = 0; j < this.nmv[n2] - 1; ++j) {
                float f;
                int n13;
                n9 = 0;
                n10 = 0;
                n11 = 0;
                n12 = 0;
                bl = true;
                for (int k = 0; k < 4; ++k) {
                    n13 = k + j;
                    if (k == 2) {
                        n13 = j + this.nmv[n2] + 1;
                    }
                    if (k == 3) {
                        n13 = j + this.nmv[n2];
                    }
                    nArray4[k] = this.xs(nArray[n13], nArray3[n13]);
                    nArray5[k] = this.ys(nArray2[n13], nArray3[n13]);
                    if (nArray5[k] < 0 || nArray3[n13] < 10) {
                        ++n9;
                    }
                    if (nArray5[k] > this.h || nArray3[n13] < 10) {
                        ++n10;
                    }
                    if (nArray4[k] < 0 || nArray3[n13] < 10) {
                        ++n11;
                    }
                    if (nArray4[k] <= this.w && nArray3[n13] >= 10) continue;
                    ++n12;
                }
                if (n11 == 4 || n9 == 4 || n10 == 4 || n12 == 4) {
                    bl = false;
                }
                if (!bl || !((f = (float)n / 2500.0f + (8000.0f - (float)this.fade[0]) / 1000.0f - 2.0f - ((float)Math.abs(this.y) - 250.0f) / 5000.0f) > 0.0f) || !(f < 10.0f)) continue;
                if ((double)f < 3.5) {
                    f = 3.5f;
                }
                n13 = (int)(((float)(this.mtc[n2][j][0] + this.cgrnd[0]) + (float)this.csky[0] * f + (float)this.cfade[0] * f) / (2.0f + f * 2.0f));
                int n14 = (int)(((float)(this.mtc[n2][j][1] + this.cgrnd[1]) + (float)this.csky[1] * f + (float)this.cfade[1] * f) / (2.0f + f * 2.0f));
                int n15 = (int)(((float)(this.mtc[n2][j][2] + this.cgrnd[2]) + (float)this.csky[2] * f + (float)this.cfade[2] * f) / (2.0f + f * 2.0f));
                graphics2D.setColor(new Color(n13, n14, n15));
                graphics2D.fillPolygon(nArray4, nArray5, 4);
            }
        }
    }

    public void newstars() {
        this.stx = null;
        this.stz = null;
        this.stc = null;
        this.bst = null;
        this.twn = null;
        this.nst = 0;
        if (this.lightson) {
            Random random = new Random((long)(Math.random() * 100000.0));
            this.nst = 40;
            this.stx = new int[this.nst];
            this.stz = new int[this.nst];
            this.stc = new int[this.nst][2][3];
            this.bst = new boolean[this.nst];
            this.twn = new int[this.nst];
            for (int i = 0; i < this.nst; ++i) {
                this.stx[i] = (int)(2000.0 * random.nextDouble() - 1000.0);
                this.stz[i] = (int)(2000.0 * random.nextDouble() - 1000.0);
                int n = (int)(3.0 * random.nextDouble());
                if (n >= 3) {
                    n = 0;
                }
                if (n <= -1) {
                    n = 2;
                }
                int n2 = n + 1;
                if (random.nextDouble() > random.nextDouble()) {
                    n2 = n - 1;
                }
                if (n2 == 3) {
                    n2 = 0;
                }
                if (n2 == -1) {
                    n2 = 2;
                }
                for (int j = 0; j < 3; ++j) {
                    this.stc[i][0][j] = 200;
                    if (n == j) {
                        int[] nArray = this.stc[i][0];
                        int n3 = j;
                        nArray[n3] = nArray[n3] + (int)(55.0 * random.nextDouble());
                    }
                    if (n2 == j) {
                        int[] nArray = this.stc[i][0];
                        int n4 = j;
                        nArray[n4] = nArray[n4] + 55;
                    }
                    this.stc[i][0][j] = (this.stc[i][0][j] * 2 + this.csky[j]) / 3;
                    this.stc[i][1][j] = (this.stc[i][0][j] + this.csky[j]) / 2;
                }
                this.twn[i] = (int)(4.0 * random.nextDouble());
                this.bst[i] = random.nextDouble() > 0.8;
            }
        }
    }

    public void drawstars(Graphics2D graphics2D) {
        for (int i = 0; i < this.nst; ++i) {
            int n;
            int n2 = this.cx + (int)((float)this.stx[i] * this.cos(this.xz) - (float)this.stz[i] * this.sin(this.xz));
            int n3 = this.cz + (int)((float)this.stx[i] * this.sin(this.xz) + (float)this.stz[i] * this.cos(this.xz));
            int n4 = this.cy + (int)(-200.0f * this.cos(this.zy) - (float)n3 * this.sin(this.zy));
            int n5 = this.cz + (int)(-200.0f * this.sin(this.zy) + (float)n3 * this.cos(this.zy));
            n2 = this.xs(n2, n5);
            n4 = this.ys(n4, n5);
            if (n2 - 1 <= this.iw || n2 + 3 >= this.w || n4 - 1 <= this.ih || n4 + 3 >= this.h) continue;
            if (this.twn[i] == 0) {
                n = (int)(3.0 * Math.random());
                if (n >= 3) {
                    n = 0;
                }
                if (n <= -1) {
                    n = 2;
                }
                int n6 = n + 1;
                if (Math.random() > Math.random()) {
                    n6 = n - 1;
                }
                if (n6 == 3) {
                    n6 = 0;
                }
                if (n6 == -1) {
                    n6 = 2;
                }
                for (int j = 0; j < 3; ++j) {
                    this.stc[i][0][j] = 200;
                    if (n == j) {
                        int[] nArray = this.stc[i][0];
                        int n7 = j;
                        nArray[n7] = nArray[n7] + (int)(55.0 * Math.random());
                    }
                    if (n6 == j) {
                        int[] nArray = this.stc[i][0];
                        int n8 = j;
                        nArray[n8] = nArray[n8] + 55;
                    }
                    this.stc[i][0][j] = (this.stc[i][0][j] * 2 + this.csky[j]) / 3;
                    this.stc[i][1][j] = (this.stc[i][0][j] + this.csky[j]) / 2;
                }
                this.twn[i] = 3;
            } else {
                int n9 = i;
                this.twn[n9] = this.twn[n9] - 1;
            }
            n = 0;
            if (this.bst[i]) {
                n = 1;
            }
            graphics2D.setColor(new Color(this.stc[i][1][0], this.stc[i][1][1], this.stc[i][1][2]));
            graphics2D.fillRect(n2 - 1, n4, 3 + n, 1 + n);
            graphics2D.fillRect(n2, n4 - 1, 1 + n, 3 + n);
            graphics2D.setColor(new Color(this.stc[i][0][0], this.stc[i][0][1], this.stc[i][0][2]));
            graphics2D.fillRect(n2, n4, 1 + n, 1 + n);
        }
    }

    public void d(Graphics2D graphics2D) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        this.nsp = 0;
        if (this.zy > 90) {
            this.zy = 90;
        }
        if (this.zy < -90) {
            this.zy = -90;
        }
        if (this.xz > 360) {
            this.xz -= 360;
        }
        if (this.xz < 0) {
            this.xz += 360;
        }
        if (this.y > 0) {
            this.y = 0;
        }
        this.ground = 250 - this.y;
        int[] nArray = new int[4];
        int[] nArray2 = new int[4];
        int n7 = this.cgrnd[0];
        int n8 = this.cgrnd[1];
        int n9 = this.cgrnd[2];
        int n10 = this.crgrnd[0];
        int n11 = this.crgrnd[1];
        int n12 = this.crgrnd[2];
        int n13 = this.h;
        for (n6 = 0; n6 < 16; ++n6) {
            n5 = this.fade[n6];
            n4 = this.ground;
            if (this.zy != 0) {
                n4 = this.cy + (int)((float)(this.ground - this.cy) * this.cos(this.zy) - (float)(this.fade[n6] - this.cz) * this.sin(this.zy));
                n5 = this.cz + (int)((float)(this.ground - this.cy) * this.sin(this.zy) + (float)(this.fade[n6] - this.cz) * this.cos(this.zy));
            }
            nArray[0] = this.iw;
            nArray2[0] = this.ys(n4, n5);
            if (nArray2[0] < this.ih) {
                nArray2[0] = this.ih;
            }
            if (nArray2[0] > this.h) {
                nArray2[0] = this.h;
            }
            nArray[1] = this.iw;
            nArray2[1] = n13;
            nArray[2] = this.w;
            nArray2[2] = n13;
            nArray[3] = this.w;
            nArray2[3] = nArray2[0];
            n13 = nArray2[0];
            if (n6 > 0) {
                n10 = (n10 * 7 + this.cfade[0]) / 8;
                n11 = (n11 * 7 + this.cfade[1]) / 8;
                n12 = (n12 * 7 + this.cfade[2]) / 8;
                if (n6 < 3) {
                    n7 = (n7 * 7 + this.cfade[0]) / 8;
                    n8 = (n8 * 7 + this.cfade[1]) / 8;
                    n9 = (n9 * 7 + this.cfade[2]) / 8;
                } else {
                    n7 = n10;
                    n8 = n11;
                    n9 = n12;
                }
            }
            if (nArray2[0] >= this.h || nArray2[1] <= this.ih) continue;
            graphics2D.setColor(new Color(n7, n8, n9));
            graphics2D.fillPolygon(nArray, nArray2, 4);
        }
        if (this.lightn != -1 && this.lton) {
            if (this.lightn < 16) {
                if (this.lilo > this.lightn + 217) {
                    this.lilo -= 3;
                } else {
                    this.lightn = (int)(16.0f + 16.0f * this.random());
                }
            } else if (this.lilo < this.lightn + 217) {
                this.lilo += 7;
            } else {
                this.lightn = (int)(16.0f * this.random());
            }
            this.csky[0] = (int)((float)this.lilo + (float)this.lilo * ((float)this.snap[0] / 100.0f));
            if (this.csky[0] > 255) {
                this.csky[0] = 255;
            }
            if (this.csky[0] < 0) {
                this.csky[0] = 0;
            }
            this.csky[1] = (int)((float)this.lilo + (float)this.lilo * ((float)this.snap[1] / 100.0f));
            if (this.csky[1] > 255) {
                this.csky[1] = 255;
            }
            if (this.csky[1] < 0) {
                this.csky[1] = 0;
            }
            this.csky[2] = (int)((float)this.lilo + (float)this.lilo * ((float)this.snap[2] / 100.0f));
            if (this.csky[2] > 255) {
                this.csky[2] = 255;
            }
            if (this.csky[2] < 0) {
                this.csky[2] = 0;
            }
        }
        n7 = this.csky[0];
        n8 = this.csky[1];
        n9 = this.csky[2];
        n6 = n7;
        n5 = n8;
        n4 = n9;
        int n14 = this.cy + (int)((float)(this.skyline - 700 - this.cy) * this.cos(this.zy) - (float)(7000 - this.cz) * this.sin(this.zy));
        int n15 = this.cz + (int)((float)(this.skyline - 700 - this.cy) * this.sin(this.zy) + (float)(7000 - this.cz) * this.cos(this.zy));
        n14 = this.ys(n14, n15);
        int n16 = this.ih;
        for (n3 = 0; n3 < 16; ++n3) {
            n2 = this.fade[n3];
            n = this.skyline;
            if (this.zy != 0) {
                n = this.cy + (int)((float)(this.skyline - this.cy) * this.cos(this.zy) - (float)(this.fade[n3] - this.cz) * this.sin(this.zy));
                n2 = this.cz + (int)((float)(this.skyline - this.cy) * this.sin(this.zy) + (float)(this.fade[n3] - this.cz) * this.cos(this.zy));
            }
            nArray[0] = this.iw;
            nArray2[0] = this.ys(n, n2);
            if (nArray2[0] > this.h) {
                nArray2[0] = this.h;
            }
            if (nArray2[0] < this.ih) {
                nArray2[0] = this.ih;
            }
            nArray[1] = this.iw;
            nArray2[1] = n16;
            nArray[2] = this.w;
            nArray2[2] = n16;
            nArray[3] = this.w;
            nArray2[3] = nArray2[0];
            n16 = nArray2[0];
            if (n3 > 0) {
                n7 = (n7 * 7 + this.cfade[0]) / 8;
                n8 = (n8 * 7 + this.cfade[1]) / 8;
                n9 = (n9 * 7 + this.cfade[2]) / 8;
            }
            if (nArray2[1] < n14) {
                n6 = n7;
                n5 = n8;
                n4 = n9;
            }
            if (nArray2[0] <= this.ih || nArray2[1] >= this.h) continue;
            graphics2D.setColor(new Color(n7, n8, n9));
            graphics2D.fillPolygon(nArray, nArray2, 4);
        }
        nArray[0] = this.iw;
        nArray2[0] = n16;
        nArray[1] = this.iw;
        nArray2[1] = n13;
        nArray[2] = this.w;
        nArray2[2] = n13;
        nArray[3] = this.w;
        nArray2[3] = n16;
        if (nArray2[0] < this.h && nArray2[1] > this.ih) {
            float f = ((float)Math.abs(this.y) - 250.0f) / (float)(this.fade[0] * 2);
            if (f < 0.0f) {
                f = 0.0f;
            }
            if (f > 1.0f) {
                f = 1.0f;
            }
            n7 = (int)(((float)n7 * (1.0f - f) + (float)n10 * (1.0f + f)) / 2.0f);
            n8 = (int)(((float)n8 * (1.0f - f) + (float)n11 * (1.0f + f)) / 2.0f);
            n9 = (int)(((float)n9 * (1.0f - f) + (float)n12 * (1.0f + f)) / 2.0f);
            graphics2D.setColor(new Color(n7, n8, n9));
            graphics2D.fillPolygon(nArray, nArray2, 4);
        }
        if (this.resdown != 2) {
            for (n3 = 1; n3 < 20; ++n3) {
                n2 = 7000;
                n = this.skyline - 700 - n3 * 70;
                if (this.zy != 0 && n3 != 19) {
                    n = this.cy + (int)((float)(this.skyline - 700 - n3 * 70 - this.cy) * this.cos(this.zy) - (float)(7000 - this.cz) * this.sin(this.zy));
                    n2 = this.cz + (int)((float)(this.skyline - 700 - n3 * 70 - this.cy) * this.sin(this.zy) + (float)(7000 - this.cz) * this.cos(this.zy));
                }
                nArray[0] = this.iw;
                if (n3 != 19) {
                    nArray2[0] = this.ys(n, n2);
                    if (nArray2[0] > this.h) {
                        nArray2[0] = this.h;
                    }
                    if (nArray2[0] < this.ih) {
                        nArray2[0] = this.ih;
                    }
                } else {
                    nArray2[0] = this.ih;
                }
                nArray[1] = this.iw;
                nArray2[1] = n14;
                nArray[2] = this.w;
                nArray2[2] = n14;
                nArray[3] = this.w;
                nArray2[3] = nArray2[0];
                n14 = nArray2[0];
                n6 = (int)((double)n6 * 0.991);
                n5 = (int)((double)n5 * 0.991);
                n4 = (int)((double)n4 * 0.998);
                if (nArray2[1] <= this.ih || nArray2[0] >= this.h) continue;
                graphics2D.setColor(new Color(n6, n5, n4));
                graphics2D.fillPolygon(nArray, nArray2, 4);
            }
            if (this.lightson) {
                this.drawstars(graphics2D);
            }
            this.drawmountains(graphics2D);
            this.drawclouds(graphics2D);
        }
        this.groundpolys(graphics2D);
        if (this.noelec != 0) {
            --this.noelec;
        }
        if (this.cpflik) {
            this.cpflik = false;
        } else {
            this.cpflik = true;
            this.elecr = this.random() * 15.0f - 6.0f;
        }
    }

    public void addsp(int n, int n2, int n3) {
        if (this.nsp != 7) {
            this.spx[this.nsp] = n;
            this.spz[this.nsp] = n2;
            this.sprad[this.nsp] = n3;
            ++this.nsp;
        }
    }

    public void setsnap(int n, int n2, int n3) {
        this.snap[0] = n;
        this.snap[1] = n2;
        this.snap[2] = n3;
    }

    public void setsky(int n, int n2, int n3) {
        this.osky[0] = n;
        this.osky[1] = n2;
        this.osky[2] = n3;
        for (int i = 0; i < 3; ++i) {
            this.clds[i] = (this.osky[i] * this.cldd[3] + this.cldd[i]) / (this.cldd[3] + 1);
            this.clds[i] = (int)((float)this.clds[i] + (float)this.clds[i] * ((float)this.snap[i] / 100.0f));
            if (this.clds[i] > 255) {
                this.clds[i] = 255;
            }
            if (this.clds[i] >= 0) continue;
            this.clds[i] = 0;
        }
        this.csky[0] = (int)((float)n + (float)n * ((float)this.snap[0] / 100.0f));
        if (this.csky[0] > 255) {
            this.csky[0] = 255;
        }
        if (this.csky[0] < 0) {
            this.csky[0] = 0;
        }
        this.csky[1] = (int)((float)n2 + (float)n2 * ((float)this.snap[1] / 100.0f));
        if (this.csky[1] > 255) {
            this.csky[1] = 255;
        }
        if (this.csky[1] < 0) {
            this.csky[1] = 0;
        }
        this.csky[2] = (int)((float)n3 + (float)n3 * ((float)this.snap[2] / 100.0f));
        if (this.csky[2] > 255) {
            this.csky[2] = 255;
        }
        if (this.csky[2] < 0) {
            this.csky[2] = 0;
        }
        float[] fArray = new float[3];
        Color.RGBtoHSB(this.csky[0], this.csky[1], this.csky[2], fArray);
        this.darksky = (double)fArray[2] < 0.6;
    }

    public void setcloads(int n, int n2, int n3, int n4, int n5) {
        if (n4 < 0) {
            n4 = 0;
        }
        if (n4 > 10) {
            n4 = 10;
        }
        if (n5 < -1500) {
            n5 = -1500;
        }
        if (n5 > -500) {
            n5 = -500;
        }
        this.cldd[0] = n;
        this.cldd[1] = n2;
        this.cldd[2] = n3;
        this.cldd[3] = n4;
        this.cldd[4] = n5;
        for (int i = 0; i < 3; ++i) {
            this.clds[i] = (this.osky[i] * this.cldd[3] + this.cldd[i]) / (this.cldd[3] + 1);
            this.clds[i] = (int)((float)this.clds[i] + (float)this.clds[i] * ((float)this.snap[i] / 100.0f));
            if (this.clds[i] > 255) {
                this.clds[i] = 255;
            }
            if (this.clds[i] >= 0) continue;
            this.clds[i] = 0;
        }
    }

    public void setgrnd(int n, int n2, int n3) {
        int n4;
        this.ogrnd[0] = n;
        this.ogrnd[1] = n2;
        this.ogrnd[2] = n3;
        for (n4 = 0; n4 < 3; ++n4) {
            this.cpol[n4] = (this.ogrnd[n4] * this.texture[3] + this.texture[n4]) / (1 + this.texture[3]);
            this.cpol[n4] = (int)((float)this.cpol[n4] + (float)this.cpol[n4] * ((float)this.snap[n4] / 100.0f));
            if (this.cpol[n4] > 255) {
                this.cpol[n4] = 255;
            }
            if (this.cpol[n4] >= 0) continue;
            this.cpol[n4] = 0;
        }
        this.cgrnd[0] = (int)((float)n + (float)n * ((float)this.snap[0] / 100.0f));
        if (this.cgrnd[0] > 255) {
            this.cgrnd[0] = 255;
        }
        if (this.cgrnd[0] < 0) {
            this.cgrnd[0] = 0;
        }
        this.cgrnd[1] = (int)((float)n2 + (float)n2 * ((float)this.snap[1] / 100.0f));
        if (this.cgrnd[1] > 255) {
            this.cgrnd[1] = 255;
        }
        if (this.cgrnd[1] < 0) {
            this.cgrnd[1] = 0;
        }
        this.cgrnd[2] = (int)((float)n3 + (float)n3 * ((float)this.snap[2] / 100.0f));
        if (this.cgrnd[2] > 255) {
            this.cgrnd[2] = 255;
        }
        if (this.cgrnd[2] < 0) {
            this.cgrnd[2] = 0;
        }
        for (n4 = 0; n4 < 3; ++n4) {
            this.crgrnd[n4] = (int)(((double)this.cpol[n4] * 0.99 + (double)this.cgrnd[n4]) / 2.0);
        }
    }

    public void setexture(int n, int n2, int n3, int n4) {
        if (n4 < 20) {
            n4 = 20;
        }
        if (n4 > 60) {
            n4 = 60;
        }
        this.texture[0] = n;
        this.texture[1] = n2;
        this.texture[2] = n3;
        this.texture[3] = n4;
        n = (this.ogrnd[0] * n4 + n) / (1 + n4);
        n2 = (this.ogrnd[1] * n4 + n2) / (1 + n4);
        n3 = (this.ogrnd[2] * n4 + n3) / (1 + n4);
        this.cpol[0] = (int)((float)n + (float)n * ((float)this.snap[0] / 100.0f));
        if (this.cpol[0] > 255) {
            this.cpol[0] = 255;
        }
        if (this.cpol[0] < 0) {
            this.cpol[0] = 0;
        }
        this.cpol[1] = (int)((float)n2 + (float)n2 * ((float)this.snap[1] / 100.0f));
        if (this.cpol[1] > 255) {
            this.cpol[1] = 255;
        }
        if (this.cpol[1] < 0) {
            this.cpol[1] = 0;
        }
        this.cpol[2] = (int)((float)n3 + (float)n3 * ((float)this.snap[2] / 100.0f));
        if (this.cpol[2] > 255) {
            this.cpol[2] = 255;
        }
        if (this.cpol[2] < 0) {
            this.cpol[2] = 0;
        }
        for (int i = 0; i < 3; ++i) {
            this.crgrnd[i] = (int)(((double)this.cpol[i] * 0.99 + (double)this.cgrnd[i]) / 2.0);
        }
    }

    public void setpolys(int n, int n2, int n3) {
        this.cpol[0] = (int)((float)n + (float)n * ((float)this.snap[0] / 100.0f));
        if (this.cpol[0] > 255) {
            this.cpol[0] = 255;
        }
        if (this.cpol[0] < 0) {
            this.cpol[0] = 0;
        }
        this.cpol[1] = (int)((float)n2 + (float)n2 * ((float)this.snap[1] / 100.0f));
        if (this.cpol[1] > 255) {
            this.cpol[1] = 255;
        }
        if (this.cpol[1] < 0) {
            this.cpol[1] = 0;
        }
        this.cpol[2] = (int)((float)n3 + (float)n3 * ((float)this.snap[2] / 100.0f));
        if (this.cpol[2] > 255) {
            this.cpol[2] = 255;
        }
        if (this.cpol[2] < 0) {
            this.cpol[2] = 0;
        }
        for (int i = 0; i < 3; ++i) {
            this.crgrnd[i] = (int)(((double)this.cpol[i] * 0.99 + (double)this.cgrnd[i]) / 2.0);
        }
    }

    public void setfade(int n, int n2, int n3) {
        this.cfade[0] = (int)((float)n + (float)n * ((float)this.snap[0] / 100.0f));
        if (this.cfade[0] > 255) {
            this.cfade[0] = 255;
        }
        if (this.cfade[0] < 0) {
            this.cfade[0] = 0;
        }
        this.cfade[1] = (int)((float)n2 + (float)n2 * ((float)this.snap[1] / 100.0f));
        if (this.cfade[1] > 255) {
            this.cfade[1] = 255;
        }
        if (this.cfade[1] < 0) {
            this.cfade[1] = 0;
        }
        this.cfade[2] = (int)((float)n3 + (float)n3 * ((float)this.snap[2] / 100.0f));
        if (this.cfade[2] > 255) {
            this.cfade[2] = 255;
        }
        if (this.cfade[2] < 0) {
            this.cfade[2] = 0;
        }
    }

    public void fadfrom(int n) {
        if (n > 8000) {
            n = 8000;
        }
        for (int i = 1; i < 17; ++i) {
            this.fade[i - 1] = n / 2 * (i + 1);
        }
    }

    public void adjstfade(float f, float f2, int n, GameSparker gameSparker) {
        if (this.resdown != 2) {
            if (f == 5.0f) {
                if (this.resdown == 0 && this.rescnt == 0) {
                    gameSparker.moto = 0;
                    Madness.anti = 0;
                    this.fade[0] = 3000;
                    this.fadfrom(this.fade[0]);
                    this.resdown = 1;
                    this.rescnt = 10;
                }
                if (this.resdown == 1 && this.rescnt == 0) {
                    this.resdown = 2;
                }
                if ((n == 0 || this.resdown == 0) && f2 <= -20.0f) {
                    --this.rescnt;
                }
            } else {
                this.rescnt = this.resdown == 0 ? 5 : 10;
            }
        }
    }

    public int xs(int n, int n2) {
        if (n2 < this.cz) {
            n2 = this.cz;
        }
        return (n2 - this.focus_point) * (this.cx - n) / n2 + n;
    }

    public int ys(int n, int n2) {
        if (n2 < 10) {
            n2 = 10;
        }
        return (n2 - this.focus_point) * (this.cy - n) / n2 + n;
    }

    public float cos(int n) {
        while (n >= 360) {
            n -= 360;
        }
        while (n < 0) {
            n += 360;
        }
        return this.tcos[n];
    }

    public float sin(int n) {
        while (n >= 360) {
            n -= 360;
        }
        while (n < 0) {
            n += 360;
        }
        return this.tsin[n];
    }

    public void rot(int[] nArray, int[] nArray2, int n, int n2, int n3, int n4) {
        if (n3 != 0) {
            for (int i = 0; i < n4; ++i) {
                int n5 = nArray[i];
                int n6 = nArray2[i];
                nArray[i] = n + (int)((float)(n5 - n) * this.cos(n3) - (float)(n6 - n2) * this.sin(n3));
                nArray2[i] = n2 + (int)((float)(n5 - n) * this.sin(n3) + (float)(n6 - n2) * this.cos(n3));
            }
        }
    }
}

