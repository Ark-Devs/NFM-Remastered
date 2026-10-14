/*
 * Decompiled with CFR 0.152.
 */
public class Trackers {
    int[] x = new int[6700];
    int[] y = new int[6700];
    int[] z = new int[6700];
    int[] xy = new int[6700];
    int[] zy = new int[6700];
    int[] skd = new int[6700];
    int[] dam = new int[6700];
    boolean[] notwall = new boolean[6700];
    boolean[] decor = new boolean[6700];
    int[][] c = new int[6700][3];
    int[] radx = new int[6700];
    int[] radz = new int[6700];
    int[] rady = new int[6700];
    int nt = 0;
    int sx = 0;
    int sz = 0;
    int ncx = 0;
    int ncz = 0;
    int[][][] sect = null;

    public void devidetrackers(int n, int n2, int n3, int n4) {
        int n5;
        this.sect = null;
        this.sx = n;
        this.sz = n3;
        this.ncx = n2 / 3000;
        if (this.ncx <= 0) {
            this.ncx = 1;
        }
        this.ncz = n4 / 3000;
        if (this.ncz <= 0) {
            this.ncz = 1;
        }
        this.sect = new int[this.ncx][this.ncz][];
        for (n5 = 0; n5 < this.ncx; ++n5) {
            for (int i = 0; i < this.ncz; ++i) {
                int n6;
                int n7 = this.sx + n5 * 3000 + 1500;
                int n8 = this.sz + i * 3000 + 1500;
                int[] nArray = new int[6700];
                int n9 = 0;
                for (n6 = 0; n6 < this.nt; ++n6) {
                    int n10 = this.py(n7, this.x[n6], n8, this.z[n6]);
                    if (n10 >= 20250000 || n10 <= 0 || this.dam[n6] == 167) continue;
                    nArray[n9] = n6;
                    ++n9;
                }
                if (n5 == 0 || i == 0 || n5 == this.ncx - 1 || i == this.ncz - 1) {
                    for (n6 = 0; n6 < this.nt; ++n6) {
                        if (this.dam[n6] != 167) continue;
                        nArray[n9] = n6;
                        ++n9;
                    }
                }
                if (n9 == 0) {
                    nArray[n9] = 0;
                    ++n9;
                }
                this.sect[n5][i] = new int[n9];
                for (n6 = 0; n6 < n9; ++n6) {
                    this.sect[n5][i][n6] = nArray[n6];
                }
            }
        }
        for (n5 = 0; n5 < this.nt; ++n5) {
            if (this.dam[n5] != 167) continue;
            this.dam[n5] = 1;
        }
        --this.ncx;
        --this.ncz;
    }

    public int py(int n, int n2, int n3, int n4) {
        return (n - n2) * (n - n2) + (n3 - n4) * (n3 - n4);
    }
}

