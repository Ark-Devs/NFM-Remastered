/*
 * Decompiled with CFR 0.152.
 */
public class CheckPoints {
    int[] x = new int[140];
    int[] z = new int[140];
    int[] y = new int[140];
    int[] typ = new int[140];
    int pcs = 0;
    int nsp = 0;
    int n = 0;
    int[] fx = new int[5];
    int[] fz = new int[5];
    int[] fy = new int[5];
    boolean[] roted = new boolean[5];
    boolean[] special = new boolean[5];
    int fn = 0;
    int stage = (int)(Math.random() * 27.0) + 1;
    int nlaps = 0;
    int nfix = 0;
    boolean notb = false;
    String name = "hogan rewish";
    String maker = "";
    int pubt = 0;
    String trackname = "";
    int trackvol = 200;
    int top20 = 0;
    int nto = 0;
    int[] pos = new int[]{7, 7, 7, 7, 7, 7, 7, 7};
    int[] clear = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    int[] dested = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    float[] magperc = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    int wasted = 0;
    boolean haltall = false;
    int pcleared = 0;
    int[] opx = new int[8];
    int[] opz = new int[8];
    int[] onscreen = new int[8];
    int[] omxz = new int[8];
    int catchfin = 0;
    int postwo = 0;
    float prox = 0.0f;

    public void checkstat(Mad[] madArray, ContO[] contOArray, Record record, int n, int n2, int n3) {
        int n4;
        if (!this.haltall) {
            int n5;
            this.pcleared = madArray[n2].pcleared;
            for (n4 = 0; n4 < n; ++n4) {
                this.magperc[n4] = (float)madArray[n4].hitmag / (float)madArray[n4].cd.maxmag[madArray[n4].cn];
                if (this.magperc[n4] > 1.0f) {
                    this.magperc[n4] = 1.0f;
                }
                this.pos[n4] = 0;
                this.onscreen[n4] = contOArray[n4].dist;
                this.opx[n4] = contOArray[n4].x;
                this.opz[n4] = contOArray[n4].z;
                this.omxz[n4] = madArray[n4].mxz;
                this.clear[n4] = this.dested[n4] == 0 ? madArray[n4].clear : -1;
                madArray[n4].outshakedam = madArray[n4].shakedam;
                madArray[n4].shakedam = 0;
            }
            for (n4 = 0; n4 < n; ++n4) {
                for (n5 = n4 + 1; n5 < n; ++n5) {
                    if (this.clear[n4] != this.clear[n5]) {
                        if (this.clear[n4] < this.clear[n5]) {
                            int n6 = n4;
                            this.pos[n6] = this.pos[n6] + 1;
                            continue;
                        }
                        int n7 = n5;
                        this.pos[n7] = this.pos[n7] + 1;
                        continue;
                    }
                    int n8 = madArray[n4].pcleared + 1;
                    if (n8 >= this.n) {
                        n8 = 0;
                    }
                    while (this.typ[n8] <= 0) {
                        if (++n8 < this.n) continue;
                        n8 = 0;
                    }
                    if (this.py(contOArray[n4].x / 100, this.x[n8] / 100, contOArray[n4].z / 100, this.z[n8] / 100) > this.py(contOArray[n5].x / 100, this.x[n8] / 100, contOArray[n5].z / 100, this.z[n8] / 100)) {
                        int n9 = n4;
                        this.pos[n9] = this.pos[n9] + 1;
                        continue;
                    }
                    int n10 = n5;
                    this.pos[n10] = this.pos[n10] + 1;
                }
            }
            if (this.stage > 2) {
                for (n4 = 0; n4 < n; ++n4) {
                    if (this.clear[n4] != this.nlaps * this.nsp || this.pos[n4] != 0) continue;
                    if (n4 == n2) {
                        for (n5 = 0; n5 < n; ++n5) {
                            if (this.pos[n5] != 1) continue;
                            this.postwo = n5;
                        }
                        if (this.py(this.opx[n2] / 100, this.opx[this.postwo] / 100, this.opz[n2] / 100, this.opz[this.postwo] / 100) >= 14000 || this.clear[n2] - this.clear[this.postwo] != 1) continue;
                        this.catchfin = 30;
                        continue;
                    }
                    if (this.pos[n2] != 1 || this.py(this.opx[n2] / 100, this.opx[n4] / 100, this.opz[n2] / 100, this.opz[n4] / 100) >= 14000 || this.clear[n4] - this.clear[n2] != 1) continue;
                    this.catchfin = 30;
                    this.postwo = n4;
                }
            }
        }
        this.wasted = 0;
        for (n4 = 0; n4 < n; ++n4) {
            if (n2 == n4 && n3 < 2 || !madArray[n4].dest) continue;
            ++this.wasted;
        }
        if (this.catchfin != 0 && n3 < 2) {
            --this.catchfin;
            if (this.catchfin == 0) {
                record.cotchinow(this.postwo);
                record.closefinish = this.pos[n2] + 1;
            }
        }
    }

    public void calprox() {
        int n = 0;
        for (int i = 0; i < this.n - 1; ++i) {
            for (int j = i + 1; j < this.n; ++j) {
                if (Math.abs(this.x[i] - this.x[j]) > n) {
                    n = Math.abs(this.x[i] - this.x[j]);
                }
                if (Math.abs(this.z[i] - this.z[j]) <= n) continue;
                n = Math.abs(this.z[i] - this.z[j]);
            }
        }
        this.prox = (float)n / 90.0f;
    }

    public int py(int n, int n2, int n3, int n4) {
        return (n - n2) * (n - n2) + (n3 - n4) * (n3 - n4);
    }
}

