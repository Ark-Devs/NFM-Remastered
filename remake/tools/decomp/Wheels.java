/*
 * Decompiled with CFR 0.152.
 */
public class Wheels {
    int ground = 0;
    int mast = 0;
    int sparkat = 0;
    int[] rc = new int[]{120, 120, 120};
    float size = 2.0f;
    float depth = 3.0f;

    public void setrims(int n, int n2, int n3, int n4, int n5) {
        this.rc[0] = n;
        this.rc[1] = n2;
        this.rc[2] = n3;
        this.size = (float)n4 / 10.0f;
        if (this.size < 0.0f) {
            this.size = 0.0f;
        }
        this.depth = (float)n5 / 10.0f;
        if (this.depth / this.size > 41.0f) {
            this.depth = this.size * 41.0f;
        }
        if (this.depth / this.size < -25.0f) {
            this.depth = -(this.size * 25.0f);
        }
    }

    public void make(Medium medium, Trackers trackers, Plane[] planeArray, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9;
        int[] nArray = new int[20];
        int[] nArray2 = new int[20];
        int[] nArray3 = new int[20];
        int[] nArray4 = new int[]{45, 45, 45};
        int n10 = 0;
        float f = (float)n6 / 10.0f;
        float f2 = (float)n7 / 10.0f;
        if (n5 == 11) {
            n10 = (int)((float)n2 + 4.0f * f);
        }
        this.sparkat = (int)(f2 * 24.0f);
        this.ground = (int)((float)n3 + 13.0f * f2);
        int n11 = -1;
        if (n2 < 0) {
            n11 = 1;
        }
        for (n9 = 0; n9 < 20; ++n9) {
            nArray[n9] = (int)((float)n2 - 4.0f * f);
        }
        nArray2[0] = (int)((float)n3 - 9.1923f * f2);
        nArray3[0] = (int)((float)n4 + 9.1923f * f2);
        nArray2[1] = (int)((float)n3 - 12.557f * f2);
        nArray3[1] = (int)((float)n4 + 3.3646f * f2);
        nArray2[2] = (int)((float)n3 - 12.557f * f2);
        nArray3[2] = (int)((float)n4 - 3.3646f * f2);
        nArray2[3] = (int)((float)n3 - 9.1923f * f2);
        nArray3[3] = (int)((float)n4 - 9.1923f * f2);
        nArray2[4] = (int)((float)n3 - 3.3646f * f2);
        nArray3[4] = (int)((float)n4 - 12.557f * f2);
        nArray2[5] = (int)((float)n3 + 3.3646f * f2);
        nArray3[5] = (int)((float)n4 - 12.557f * f2);
        nArray2[6] = (int)((float)n3 + 9.1923f * f2);
        nArray3[6] = (int)((float)n4 - 9.1923f * f2);
        nArray2[7] = (int)((float)n3 + 12.557f * f2);
        nArray3[7] = (int)((float)n4 - 3.3646f * f2);
        nArray2[8] = (int)((float)n3 + 12.557f * f2);
        nArray3[8] = (int)((float)n4 + 3.3646f * f2);
        nArray2[9] = (int)((float)n3 + 9.1923f * f2);
        nArray3[9] = (int)((float)n4 + 9.1923f * f2);
        nArray2[10] = (int)((float)n3 + 3.3646f * f2);
        nArray3[10] = (int)((float)n4 + 12.557f * f2);
        nArray2[11] = (int)((float)n3 - 3.3646f * f2);
        nArray3[11] = (int)((float)n4 + 12.557f * f2);
        nArray2[12] = n3;
        nArray3[12] = (int)((float)n4 + 10.0f * this.size);
        nArray2[13] = (int)((double)n3 + 8.66 * (double)this.size);
        nArray3[13] = (int)((float)n4 + 5.0f * this.size);
        nArray2[14] = (int)((double)n3 + 8.66 * (double)this.size);
        nArray3[14] = (int)((float)n4 - 5.0f * this.size);
        nArray2[15] = n3;
        nArray3[15] = (int)((float)n4 - 10.0f * this.size);
        nArray2[16] = (int)((double)n3 - 8.66 * (double)this.size);
        nArray3[16] = (int)((float)n4 - 5.0f * this.size);
        nArray2[17] = (int)((double)n3 - 8.66 * (double)this.size);
        nArray3[17] = (int)((float)n4 + 5.0f * this.size);
        nArray2[18] = n3;
        nArray3[18] = (int)((float)n4 + 10.0f * this.size);
        nArray2[19] = (int)((float)n3 - 3.3646f * f2);
        nArray3[19] = (int)((float)n4 + 12.557f * f2);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 20, nArray4, 0, n8, 0, n10, n3, n4, 7, 0, false, 0, false);
        planeArray[n].master = 1;
        ++n;
        nArray[2] = (int)((float)n2 - this.depth * f);
        nArray2[2] = n3;
        nArray3[2] = n4;
        n9 = (int)((float)n8 - this.depth / this.size * 4.0f);
        if (n9 < -16) {
            n9 = -16;
        }
        nArray2[0] = n3;
        nArray3[0] = (int)((float)n4 + 10.0f * this.size);
        nArray2[1] = (int)((double)n3 + 8.66 * (double)this.size);
        nArray3[1] = (int)((float)n4 + 5.0f * this.size);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 3, this.rc, 0, n9, 0, n10, n3, n4, 7, 0, false, 0, false);
        if (this.depth / this.size < 7.0f) {
            planeArray[n].master = 2;
        }
        nArray2[0] = (int)((double)n3 + 8.66 * (double)this.size);
        nArray3[0] = (int)((float)n4 + 5.0f * this.size);
        nArray2[1] = (int)((double)n3 + 8.66 * (double)this.size);
        nArray3[1] = (int)((float)n4 - 5.0f * this.size);
        planeArray[++n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 3, this.rc, 0, n9, 0, n10, n3, n4, 7, 0, false, 0, false);
        if (this.depth / this.size < 7.0f) {
            planeArray[n].master = 2;
        }
        nArray2[0] = (int)((double)n3 + 8.66 * (double)this.size);
        nArray3[0] = (int)((float)n4 - 5.0f * this.size);
        nArray2[1] = n3;
        nArray3[1] = (int)((float)n4 - 10.0f * this.size);
        planeArray[++n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 3, this.rc, 0, n9, 0, n10, n3, n4, 7, 0, false, 0, false);
        if (this.depth / this.size < 7.0f) {
            planeArray[n].master = 2;
        }
        nArray2[0] = n3;
        nArray3[0] = (int)((float)n4 - 10.0f * this.size);
        nArray2[1] = (int)((double)n3 - 8.66 * (double)this.size);
        nArray3[1] = (int)((float)n4 - 5.0f * this.size);
        planeArray[++n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 3, this.rc, 0, n9, 0, n10, n3, n4, 7, 0, false, 0, false);
        if (this.depth / this.size < 7.0f) {
            planeArray[n].master = 2;
        }
        nArray2[0] = (int)((double)n3 - 8.66 * (double)this.size);
        nArray3[0] = (int)((float)n4 - 5.0f * this.size);
        nArray2[1] = (int)((double)n3 - 8.66 * (double)this.size);
        nArray3[1] = (int)((float)n4 + 5.0f * this.size);
        planeArray[++n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 3, this.rc, 0, n9, 0, n10, n3, n4, 7, 0, false, 0, false);
        if (this.depth / this.size < 7.0f) {
            planeArray[n].master = 2;
        }
        nArray2[0] = (int)((double)n3 - 8.66 * (double)this.size);
        nArray3[0] = (int)((float)n4 + 5.0f * this.size);
        nArray2[1] = n3;
        nArray3[1] = (int)((float)n4 + 10.0f * this.size);
        planeArray[++n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 3, this.rc, 0, n9, 0, n10, n3, n4, 7, 0, false, 0, false);
        if (this.depth / this.size < 7.0f) {
            planeArray[n].master = 2;
        }
        ++n;
        nArray[0] = (int)((float)n2 - 4.0f * f);
        nArray2[0] = (int)((float)n3 - 12.557f * f2);
        nArray3[0] = (int)((float)n4 + 3.3646f * f2);
        nArray[1] = (int)((float)n2 - 4.0f * f);
        nArray2[1] = (int)((float)n3 - 12.557f * f2);
        nArray3[1] = (int)((float)n4 - 3.3646f * f2);
        nArray[2] = (int)((float)n2 + 4.0f * f);
        nArray2[2] = (int)((float)n3 - 12.557f * f2);
        nArray3[2] = (int)((float)n4 - 3.3646f * f2);
        nArray[3] = (int)((float)n2 + 4.0f * f);
        nArray2[3] = (int)((float)n3 - 12.557f * f2);
        nArray3[3] = (int)((float)n4 + 3.3646f * f2);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 4, nArray4, 0, n8, -1 * n11, n10, n3, n4, 7, 0, false, 0, true);
        ++n;
        nArray[0] = (int)((float)n2 - 4.0f * f);
        nArray2[0] = (int)((float)n3 - 9.1923f * f2);
        nArray3[0] = (int)((float)n4 - 9.1923f * f2);
        nArray[1] = (int)((float)n2 - 4.0f * f);
        nArray2[1] = (int)((float)n3 - 12.557f * f2);
        nArray3[1] = (int)((float)n4 - 3.3646f * f2);
        nArray[2] = (int)((float)n2 + 4.0f * f);
        nArray2[2] = (int)((float)n3 - 12.557f * f2);
        nArray3[2] = (int)((float)n4 - 3.3646f * f2);
        nArray[3] = (int)((float)n2 + 4.0f * f);
        nArray2[3] = (int)((float)n3 - 9.1923f * f2);
        nArray3[3] = (int)((float)n4 - 9.1923f * f2);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 4, nArray4, 0, n8, 1 * n11, n10, n3, n4, 7, 0, false, 0, true);
        ++n;
        nArray[0] = (int)((float)n2 - 4.0f * f);
        nArray2[0] = (int)((float)n3 - 9.1923f * f2);
        nArray3[0] = (int)((float)n4 - 9.1923f * f2);
        nArray[1] = (int)((float)n2 - 4.0f * f);
        nArray2[1] = (int)((float)n3 - 3.3646f * f2);
        nArray3[1] = (int)((float)n4 - 12.557f * f2);
        nArray[2] = (int)((float)n2 + 4.0f * f);
        nArray2[2] = (int)((float)n3 - 3.3646f * f2);
        nArray3[2] = (int)((float)n4 - 12.557f * f2);
        nArray[3] = (int)((float)n2 + 4.0f * f);
        nArray2[3] = (int)((float)n3 - 9.1923f * f2);
        nArray3[3] = (int)((float)n4 - 9.1923f * f2);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 4, nArray4, 0, n8, 1 * n11, n10, n3, n4, 7, 0, false, 0, true);
        ++n;
        nArray[0] = (int)((float)n2 - 4.0f * f);
        nArray2[0] = (int)((float)n3 - 3.3646f * f2);
        nArray3[0] = (int)((float)n4 - 12.557f * f2);
        nArray[1] = (int)((float)n2 - 4.0f * f);
        nArray2[1] = (int)((float)n3 + 3.3646f * f2);
        nArray3[1] = (int)((float)n4 - 12.557f * f2);
        nArray[2] = (int)((float)n2 + 4.0f * f);
        nArray2[2] = (int)((float)n3 + 3.3646f * f2);
        nArray3[2] = (int)((float)n4 - 12.557f * f2);
        nArray[3] = (int)((float)n2 + 4.0f * f);
        nArray2[3] = (int)((float)n3 - 3.3646f * f2);
        nArray3[3] = (int)((float)n4 - 12.557f * f2);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 4, nArray4, 0, n8, -1 * n11, n10, n3, n4, 7, 0, false, 0, true);
        ++n;
        nArray[0] = (int)((float)n2 - 4.0f * f);
        nArray2[0] = (int)((float)n3 + 9.1923f * f2);
        nArray3[0] = (int)((float)n4 - 9.1923f * f2);
        nArray[1] = (int)((float)n2 - 4.0f * f);
        nArray2[1] = (int)((float)n3 + 3.3646f * f2);
        nArray3[1] = (int)((float)n4 - 12.557f * f2);
        nArray[2] = (int)((float)n2 + 4.0f * f);
        nArray2[2] = (int)((float)n3 + 3.3646f * f2);
        nArray3[2] = (int)((float)n4 - 12.557f * f2);
        nArray[3] = (int)((float)n2 + 4.0f * f);
        nArray2[3] = (int)((float)n3 + 9.1923f * f2);
        nArray3[3] = (int)((float)n4 - 9.1923f * f2);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 4, nArray4, 0, n8, 1 * n11, n10, n3, n4, 7, 0, false, 0, true);
        ++n;
        nArray[0] = (int)((float)n2 - 4.0f * f);
        nArray2[0] = (int)((float)n3 + 9.1923f * f2);
        nArray3[0] = (int)((float)n4 - 9.1923f * f2);
        nArray[1] = (int)((float)n2 - 4.0f * f);
        nArray2[1] = (int)((float)n3 + 12.557f * f2);
        nArray3[1] = (int)((float)n4 - 3.3646f * f2);
        nArray[2] = (int)((float)n2 + 4.0f * f);
        nArray2[2] = (int)((float)n3 + 12.557f * f2);
        nArray3[2] = (int)((float)n4 - 3.3646f * f2);
        nArray[3] = (int)((float)n2 + 4.0f * f);
        nArray2[3] = (int)((float)n3 + 9.1923f * f2);
        nArray3[3] = (int)((float)n4 - 9.1923f * f2);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 4, nArray4, 0, n8, 1 * n11, n10, n3, n4, 7, 0, false, 0, true);
        ++n;
        nArray[0] = (int)((float)n2 - 4.0f * f);
        nArray2[0] = (int)((float)n3 + 12.557f * f2);
        nArray3[0] = (int)((float)n4 - 3.3646f * f2);
        nArray[1] = (int)((float)n2 - 4.0f * f);
        nArray2[1] = (int)((float)n3 + 12.557f * f2);
        nArray3[1] = (int)((float)n4 + 3.3646f * f2);
        nArray[2] = (int)((float)n2 + 4.0f * f);
        nArray2[2] = (int)((float)n3 + 12.557f * f2);
        nArray3[2] = (int)((float)n4 + 3.3646f * f2);
        nArray[3] = (int)((float)n2 + 4.0f * f);
        nArray2[3] = (int)((float)n3 + 12.557f * f2);
        nArray3[3] = (int)((float)n4 - 3.3646f * f2);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 4, nArray4, 0, n8, -1 * n11, n10, n3, n4, 7, 0, false, 0, true);
        ++n;
        nArray[0] = (int)((float)n2 - 4.0f * f);
        nArray2[0] = (int)((float)n3 + 9.1923f * f2);
        nArray3[0] = (int)((float)n4 + 9.1923f * f2);
        nArray[1] = (int)((float)n2 - 4.0f * f);
        nArray2[1] = (int)((float)n3 + 12.557f * f2);
        nArray3[1] = (int)((float)n4 + 3.3646f * f2);
        nArray[2] = (int)((float)n2 + 4.0f * f);
        nArray2[2] = (int)((float)n3 + 12.557f * f2);
        nArray3[2] = (int)((float)n4 + 3.3646f * f2);
        nArray[3] = (int)((float)n2 + 4.0f * f);
        nArray2[3] = (int)((float)n3 + 9.1923f * f2);
        nArray3[3] = (int)((float)n4 + 9.1923f * f2);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 4, nArray4, 0, n8, 1 * n11, n10, n3, n4, 7, 0, false, 0, true);
        ++n;
        nArray[0] = (int)((float)n2 - 4.0f * f);
        nArray2[0] = (int)((float)n3 + 9.1923f * f2);
        nArray3[0] = (int)((float)n4 + 9.1923f * f2);
        nArray[1] = (int)((float)n2 - 4.0f * f);
        nArray2[1] = (int)((float)n3 + 3.3646f * f2);
        nArray3[1] = (int)((float)n4 + 12.557f * f2);
        nArray[2] = (int)((float)n2 + 4.0f * f);
        nArray2[2] = (int)((float)n3 + 3.3646f * f2);
        nArray3[2] = (int)((float)n4 + 12.557f * f2);
        nArray[3] = (int)((float)n2 + 4.0f * f);
        nArray2[3] = (int)((float)n3 + 9.1923f * f2);
        nArray3[3] = (int)((float)n4 + 9.1923f * f2);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 4, nArray4, 0, n8, 1 * n11, n10, n3, n4, 7, 0, false, 0, true);
        ++n;
        nArray[0] = (int)((float)n2 - 4.0f * f);
        nArray2[0] = (int)((float)n3 + 3.3646f * f2);
        nArray3[0] = (int)((float)n4 + 12.557f * f2);
        nArray[1] = (int)((float)n2 - 4.0f * f);
        nArray2[1] = (int)((float)n3 - 3.3646f * f2);
        nArray3[1] = (int)((float)n4 + 12.557f * f2);
        nArray[2] = (int)((float)n2 + 4.0f * f);
        nArray2[2] = (int)((float)n3 - 3.3646f * f2);
        nArray3[2] = (int)((float)n4 + 12.557f * f2);
        nArray[3] = (int)((float)n2 + 4.0f * f);
        nArray2[3] = (int)((float)n3 + 3.3646f * f2);
        nArray3[3] = (int)((float)n4 + 12.557f * f2);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 4, nArray4, 0, n8, -1 * n11, n10, n3, n4, 7, 0, false, 0, true);
        ++n;
        nArray[0] = (int)((float)n2 - 4.0f * f);
        nArray2[0] = (int)((float)n3 - 9.1923f * f2);
        nArray3[0] = (int)((float)n4 + 9.1923f * f2);
        nArray[1] = (int)((float)n2 - 4.0f * f);
        nArray2[1] = (int)((float)n3 - 3.3646f * f2);
        nArray3[1] = (int)((float)n4 + 12.557f * f2);
        nArray[2] = (int)((float)n2 + 4.0f * f);
        nArray2[2] = (int)((float)n3 - 3.3646f * f2);
        nArray3[2] = (int)((float)n4 + 12.557f * f2);
        nArray[3] = (int)((float)n2 + 4.0f * f);
        nArray2[3] = (int)((float)n3 - 9.1923f * f2);
        nArray3[3] = (int)((float)n4 + 9.1923f * f2);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 4, nArray4, 0, n8, 1 * n11, n10, n3, n4, 7, 0, false, 0, true);
        ++n;
        nArray[0] = (int)((float)n2 - 4.0f * f);
        nArray2[0] = (int)((float)n3 - 9.1923f * f2);
        nArray3[0] = (int)((float)n4 + 9.1923f * f2);
        nArray[1] = (int)((float)n2 - 4.0f * f);
        nArray2[1] = (int)((float)n3 - 12.557f * f2);
        nArray3[1] = (int)((float)n4 + 3.3646f * f2);
        nArray[2] = (int)((float)n2 + 4.0f * f);
        nArray2[2] = (int)((float)n3 - 12.557f * f2);
        nArray3[2] = (int)((float)n4 + 3.3646f * f2);
        nArray[3] = (int)((float)n2 + 4.0f * f);
        nArray2[3] = (int)((float)n3 - 9.1923f * f2);
        nArray3[3] = (int)((float)n4 + 9.1923f * f2);
        planeArray[n] = new Plane(medium, trackers, nArray, nArray3, nArray2, 4, nArray4, 0, n8, 1 * n11, n10, n3, n4, 7, 0, false, 0, true);
        ++n;
    }
}

