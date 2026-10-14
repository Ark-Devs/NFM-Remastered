/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FilterInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CarDefine
implements Runnable {
    Trackers t;
    GameSparker gs;
    ContO[] bco;
    Medium m;
    Thread carloader;
    Thread actionloader;
    Thread stageaction;
    int[][] swits = new int[][]{{50, 185, 282}, {100, 200, 310}, {60, 180, 275}, {76, 195, 298}, {70, 170, 275}, {70, 202, 293}, {60, 170, 289}, {70, 206, 291}, {90, 210, 295}, {90, 190, 276}, {70, 200, 295}, {50, 160, 270}, {90, 200, 305}, {50, 130, 210}, {80, 200, 300}, {70, 210, 290}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    float[][] acelf = new float[][]{{11.0f, 5.0f, 3.0f}, {14.0f, 7.0f, 5.0f}, {10.0f, 5.0f, 3.5f}, {11.0f, 6.0f, 3.5f}, {10.0f, 5.0f, 3.5f}, {12.0f, 6.0f, 3.0f}, {7.0f, 9.0f, 4.0f}, {11.0f, 5.0f, 3.0f}, {12.0f, 7.0f, 4.0f}, {12.0f, 7.0f, 3.5f}, {11.5f, 6.5f, 3.5f}, {9.0f, 5.0f, 3.0f}, {13.0f, 7.0f, 4.5f}, {7.5f, 3.5f, 3.0f}, {11.0f, 7.5f, 4.0f}, {12.0f, 6.0f, 3.5f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}};
    int[] handb = new int[]{7, 10, 7, 15, 12, 8, 9, 10, 5, 7, 8, 10, 8, 12, 7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    float[] airs = new float[]{1.0f, 1.2f, 0.95f, 1.0f, 2.2f, 1.0f, 0.9f, 0.8f, 1.0f, 0.9f, 1.15f, 0.8f, 1.0f, 0.3f, 1.3f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    int[] airc = new int[]{70, 30, 40, 40, 30, 50, 40, 90, 40, 50, 75, 10, 50, 0, 100, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] turn = new int[]{6, 9, 5, 7, 8, 7, 5, 5, 9, 7, 7, 4, 6, 5, 7, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    float[] grip = new float[]{20.0f, 27.0f, 18.0f, 22.0f, 19.0f, 20.0f, 25.0f, 20.0f, 19.0f, 24.0f, 22.5f, 25.0f, 30.0f, 27.0f, 25.0f, 27.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    float[] bounce = new float[]{1.2f, 1.05f, 1.3f, 1.15f, 1.3f, 1.2f, 1.15f, 1.1f, 1.2f, 1.1f, 1.15f, 0.8f, 1.05f, 0.8f, 1.1f, 1.15f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    float[] simag = new float[]{0.9f, 0.85f, 1.05f, 0.9f, 0.85f, 0.9f, 1.05f, 0.9f, 1.0f, 1.05f, 0.9f, 1.1f, 0.9f, 1.3f, 0.9f, 1.15f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    float[] moment = new float[]{1.3f, 0.75f, 1.4f, 1.2f, 1.1f, 1.38f, 1.43f, 1.48f, 1.35f, 1.7f, 1.42f, 2.0f, 1.26f, 3.0f, 1.5f, 2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    float[] comprad = new float[]{0.5f, 0.4f, 0.8f, 0.5f, 0.4f, 0.5f, 0.5f, 0.5f, 0.5f, 0.8f, 0.5f, 1.5f, 0.5f, 0.8f, 0.5f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    int[] push = new int[]{2, 2, 3, 3, 2, 2, 2, 4, 2, 2, 2, 4, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] revpush = new int[]{2, 3, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] lift = new int[]{0, 30, 0, 20, 0, 30, 0, 0, 20, 0, 0, 0, 10, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] revlift = new int[]{0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] powerloss = new int[]{2500000, 2500000, 3500000, 2500000, 4000000, 2500000, 3200000, 3200000, 2750000, 5500000, 2750000, 4500000, 3500000, 16700000, 3000000, 5500000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] flipy = new int[]{-50, -60, -92, -44, -60, -57, -54, -60, -77, -57, -82, -85, -28, -100, -63, -127, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] msquash = new int[]{7, 4, 7, 2, 8, 4, 6, 4, 3, 8, 4, 10, 3, 20, 3, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] clrad = new int[]{3300, 1700, 4700, 3000, 2000, 4500, 3500, 5000, 10000, 15000, 4000, 7000, 10000, 15000, 5500, 5000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    float[] dammult = new float[]{0.75f, 0.8f, 0.45f, 0.8f, 0.42f, 0.7f, 0.72f, 0.6f, 0.58f, 0.41f, 0.67f, 0.45f, 0.61f, 0.25f, 0.38f, 0.52f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    int[] maxmag = new int[]{7600, 4200, 7200, 6000, 6000, 15000, 17200, 17000, 18000, 11000, 19000, 10700, 13000, 45000, 5800, 18000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    float[] dishandle = new float[]{0.65f, 0.6f, 0.55f, 0.77f, 0.62f, 0.9f, 0.6f, 0.72f, 0.45f, 0.8f, 0.95f, 0.4f, 0.87f, 0.42f, 1.0f, 0.95f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    float[] outdam = new float[]{0.68f, 0.35f, 0.8f, 0.5f, 0.42f, 0.76f, 0.82f, 0.76f, 0.72f, 0.62f, 0.79f, 0.95f, 0.77f, 1.0f, 0.85f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    int[] cclass = new int[]{0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 3, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    String[] names = new String[]{"Tornado Shark", "Formula 7", "Wow Caninaro", "La Vita Crab", "Nimi", "MAX Revenge", "Lead Oxide", "Kool Kat", "Drifter X", "Sword of Justice", "High Rider", "EL KING", "Mighty Eight", "M A S H E E N", "Radical One", "DR Monstaa", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    int[] enginsignature = new int[]{0, 1, 2, 1, 0, 3, 2, 2, 1, 0, 3, 4, 1, 4, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int lastload = 0;
    int nlcars = 0;
    int nlocars = 0;
    int xnlocars = 0;
    boolean[] include = new boolean[40];
    String[] createdby = new String[40];
    int[] publish = new int[40];
    String[] loadnames = new String[20];
    int nl = 0;
    int action = 0;
    boolean carlon = false;
    int reco = -2;
    int[] lcardate = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int haltload = 0;
    int onloadingcar = 0;
    int ac = -1;
    String acname = "Radical One";
    String fails = "";
    String tnickey = "";
    String tclan = "";
    String tclankey = "";
    int loadlist = 0;
    int[] adds = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    String viewname = "";
    int staction = 0;
    String onstage = "";
    int inslot = -1;
    int roundslot = 0;
    String lastcar = "";
    int msloaded = 0;
    int[] top20adds = new int[20];

    public CarDefine(ContO[] contOArray, Medium medium, Trackers trackers, GameSparker gameSparker) {
        this.bco = contOArray;
        this.m = medium;
        this.t = trackers;
        this.gs = gameSparker;
    }

    public void loadstat(byte[] byArray, String string, int n, int n2, int n3, int n4) {
        this.names[n4] = string;
        boolean bl = false;
        boolean bl2 = false;
        String string2 = "";
        int[] nArray = new int[]{128, 128, 128, 128, 128};
        int n5 = 640;
        int[] nArray2 = new int[]{50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
        int[] nArray3 = new int[]{50, 50, 50};
        this.enginsignature[n4] = 0;
        float f = 0.0f;
        this.publish[n4 - 16] = 0;
        this.createdby[n4 - 16] = "Unkown User";
        try {
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(byArray));
            while ((string2 = dataInputStream.readLine()) != null) {
                int n6;
                if ((string2 = string2.trim()).startsWith("stat(")) {
                    try {
                        n5 = 0;
                        for (n6 = 0; n6 < 5; ++n6) {
                            nArray[n6] = this.getvalue("stat", string2, n6);
                            if (nArray[n6] > 200) {
                                nArray[n6] = 200;
                            }
                            if (nArray[n6] < 16) {
                                nArray[n6] = 16;
                            }
                            n5 += nArray[n6];
                        }
                        bl = true;
                    }
                    catch (Exception exception) {
                        bl = false;
                    }
                }
                if (string2.startsWith("physics(")) {
                    try {
                        for (n6 = 0; n6 < 11; ++n6) {
                            nArray2[n6] = this.getvalue("physics", string2, n6);
                            if (nArray2[n6] > 100) {
                                nArray2[n6] = 100;
                            }
                            if (nArray2[n6] >= 0) continue;
                            nArray2[n6] = 0;
                        }
                        for (n6 = 0; n6 < 3; ++n6) {
                            nArray3[n6] = this.getvalue("physics", string2, n6 + 11);
                            if (n6 != 0 && nArray3[n6] > 100) {
                                nArray3[n6] = 100;
                            }
                            if (nArray3[n6] >= 0) continue;
                            nArray3[n6] = 0;
                        }
                        this.enginsignature[n4] = this.getvalue("physics", string2, 14);
                        if (this.enginsignature[n4] > 4) {
                            this.enginsignature[n4] = 0;
                        }
                        if (this.enginsignature[n4] < 0) {
                            this.enginsignature[n4] = 0;
                        }
                        if ((f = (float)this.getvalue("physics", string2, 15)) > 0.0f) {
                            bl2 = true;
                        }
                    }
                    catch (Exception exception) {
                        bl2 = false;
                    }
                }
                if (string2.startsWith("handling(")) {
                    try {
                        int n7 = this.getvalue("handling", string2, 0);
                        if (n7 > 200) {
                            n7 = 200;
                        }
                        if (n7 < 50) {
                            n7 = 50;
                        }
                        this.dishandle[n4] = (float)n7 / 200.0f;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (string2.startsWith("carmaker(")) {
                    this.createdby[n4 - 16] = this.getSvalue("carmaker", string2, 0);
                }
                if (!string2.startsWith("publish(")) continue;
                this.publish[n4 - 16] = this.getvalue("publish", string2, 0);
            }
            dataInputStream.close();
        }
        catch (Exception exception) {
            System.out.println("Error Loading Car Stat: " + exception);
        }
        if (bl && bl2) {
            float f2;
            int n8;
            int n9 = 0;
            if (n5 > 680) {
                n9 = 680 - n5;
            }
            if (n5 > 640 && n5 < 680) {
                n9 = 640 - n5;
            }
            if (n5 > 600 && n5 < 640) {
                n9 = 600 - n5;
            }
            if (n5 > 560 && n5 < 600) {
                n9 = 560 - n5;
            }
            if (n5 > 520 && n5 < 560) {
                n9 = 520 - n5;
            }
            if (n5 < 520) {
                n9 = 520 - n5;
            }
            while (n9 != 0) {
                for (int i = 0; i < 5; ++i) {
                    if (n9 > 0 && nArray[i] < 200) {
                        int n10 = i;
                        nArray[n10] = nArray[n10] + 1;
                        --n9;
                    }
                    if (n9 >= 0 || nArray[i] <= 16) continue;
                    int n11 = i;
                    nArray[n11] = nArray[n11] - 1;
                    ++n9;
                }
            }
            n5 = 0;
            for (n8 = 0; n8 < 5; ++n8) {
                n5 += nArray[n8];
            }
            if (n5 == 520) {
                this.cclass[n4] = 0;
            }
            if (n5 == 560) {
                this.cclass[n4] = 1;
            }
            if (n5 == 600) {
                this.cclass[n4] = 2;
            }
            if (n5 == 640) {
                this.cclass[n4] = 3;
            }
            if (n5 == 680) {
                this.cclass[n4] = 4;
            }
            n8 = 0;
            int n12 = 0;
            float f3 = 0.5f;
            if (nArray[0] == 200) {
                n8 = 1;
                n12 = 1;
            }
            if (nArray[0] > 192 && nArray[0] < 200) {
                n8 = 12;
                n12 = 1;
                f3 = (float)(nArray[0] - 192) / 8.0f;
            }
            if (nArray[0] == 192) {
                n8 = 12;
                n12 = 12;
            }
            if (nArray[0] > 148 && nArray[0] < 192) {
                n8 = 14;
                n12 = 12;
                f3 = (float)(nArray[0] - 148) / 44.0f;
            }
            if (nArray[0] == 148) {
                n8 = 14;
                n12 = 14;
            }
            if (nArray[0] > 133 && nArray[0] < 148) {
                n8 = 10;
                n12 = 14;
                f3 = (float)(nArray[0] - 133) / 15.0f;
            }
            if (nArray[0] == 133) {
                n8 = 10;
                n12 = 10;
            }
            if (nArray[0] > 112 && nArray[0] < 133) {
                n8 = 15;
                n12 = 10;
                f3 = (float)(nArray[0] - 112) / 21.0f;
            }
            if (nArray[0] == 112) {
                n8 = 15;
                n12 = 15;
            }
            if (nArray[0] > 107 && nArray[0] < 112) {
                n8 = 11;
                n12 = 15;
                f3 = (float)(nArray[0] - 107) / 5.0f;
            }
            if (nArray[0] == 107) {
                n8 = 11;
                n12 = 11;
            }
            if (nArray[0] > 88 && nArray[0] < 107) {
                n8 = 13;
                n12 = 11;
                f3 = (float)(nArray[0] - 88) / 19.0f;
            }
            if (nArray[0] == 88) {
                n8 = 13;
                n12 = 13;
            }
            if (nArray[0] > 88) {
                this.swits[n4][0] = (int)((float)(this.swits[n12][0] - this.swits[n8][0]) * f3 + (float)this.swits[n8][0]);
                this.swits[n4][1] = (int)((float)(this.swits[n12][1] - this.swits[n8][1]) * f3 + (float)this.swits[n8][1]);
                this.swits[n4][2] = (int)((float)(this.swits[n12][2] - this.swits[n8][2]) * f3 + (float)this.swits[n8][2]);
            } else {
                f3 = (float)nArray[0] / 88.0f;
                if ((double)f3 < 0.76) {
                    f3 = 0.76f;
                }
                this.swits[n4][0] = (int)(50.0f * f3);
                this.swits[n4][1] = (int)(130.0f * f3);
                this.swits[n4][2] = (int)(210.0f * f3);
            }
            n8 = 0;
            n12 = 0;
            f3 = 0.5f;
            if (nArray[1] == 200) {
                n8 = 1;
                n12 = 1;
            }
            if (nArray[1] > 150 && nArray[1] < 200) {
                n8 = 14;
                n12 = 1;
                f3 = (float)(nArray[1] - 150) / 50.0f;
            }
            if (nArray[1] == 150) {
                n8 = 14;
                n12 = 14;
            }
            if (nArray[1] > 144 && nArray[1] < 150) {
                n8 = 9;
                n12 = 14;
                f3 = (float)(nArray[1] - 144) / 6.0f;
            }
            if (nArray[1] == 144) {
                n8 = 9;
                n12 = 9;
            }
            if (nArray[1] > 139 && nArray[1] < 144) {
                n8 = 6;
                n12 = 9;
                f3 = (float)(nArray[1] - 139) / 5.0f;
            }
            if (nArray[1] == 139) {
                n8 = 6;
                n12 = 6;
            }
            if (nArray[1] > 128 && nArray[1] < 139) {
                n8 = 15;
                n12 = 6;
                f3 = (float)(nArray[1] - 128) / 11.0f;
            }
            if (nArray[1] == 128) {
                n8 = 15;
                n12 = 15;
            }
            if (nArray[1] > 122 && nArray[1] < 128) {
                n8 = 10;
                n12 = 15;
                f3 = (float)(nArray[1] - 122) / 6.0f;
            }
            if (nArray[1] == 122) {
                n8 = 10;
                n12 = 10;
            }
            if (nArray[1] > 119 && nArray[1] < 122) {
                n8 = 3;
                n12 = 10;
                f3 = (float)(nArray[1] - 119) / 3.0f;
            }
            if (nArray[1] == 119) {
                n8 = 3;
                n12 = 3;
            }
            if (nArray[1] > 98 && nArray[1] < 119) {
                n8 = 5;
                n12 = 3;
                f3 = (float)(nArray[1] - 98) / 21.0f;
            }
            if (nArray[1] == 98) {
                n8 = 5;
                n12 = 5;
            }
            if (nArray[1] > 81 && nArray[1] < 98) {
                n8 = 0;
                n12 = 5;
                f3 = (float)(nArray[1] - 81) / 17.0f;
            }
            if (nArray[1] == 81) {
                n8 = 0;
                n12 = 0;
            }
            if (nArray[1] <= 80) {
                n8 = 2;
                n12 = 2;
            }
            if (nArray[0] <= 88) {
                n8 = 13;
                n12 = 13;
            }
            this.acelf[n4][0] = (this.acelf[n12][0] - this.acelf[n8][0]) * f3 + this.acelf[n8][0];
            this.acelf[n4][1] = (this.acelf[n12][1] - this.acelf[n8][1]) * f3 + this.acelf[n8][1];
            this.acelf[n4][2] = (this.acelf[n12][2] - this.acelf[n8][2]) * f3 + this.acelf[n8][2];
            if (nArray[1] <= 70 && nArray[0] > 88) {
                this.acelf[n4][0] = 9.0f;
                this.acelf[n4][1] = 4.0f;
                this.acelf[n4][2] = 3.0f;
            }
            if ((f3 = (float)(nArray[2] - 88) / 109.0f) > 1.0f) {
                f3 = 1.0f;
            }
            if ((double)f3 < -0.55) {
                f3 = -0.55f;
            }
            this.airs[n4] = 0.55f + 0.45f * f3 + 0.4f * ((float)nArray2[9] / 100.0f);
            if ((double)this.airs[n4] < 0.3) {
                this.airs[n4] = 0.3f;
            }
            this.airc[n4] = (int)(10.0f + 70.0f * f3 + 30.0f * ((float)nArray2[10] / 100.0f));
            if (this.airc[n4] < 0) {
                this.airc[n4] = 0;
            }
            int n13 = (int)(670.0f - (float)(nArray2[9] + nArray2[10]) / 200.0f * 420.0f);
            if (nArray[0] <= 88) {
                n13 = (int)(1670.0f - (float)(nArray2[9] + nArray2[10]) / 200.0f * 1420.0f);
            }
            if (nArray[2] > 190 && n13 < 300) {
                n13 = 300;
            }
            this.powerloss[n4] = n13 * 10000;
            this.moment[n4] = 0.7f + (float)(nArray[3] - 16) / 184.0f * 1.0f;
            if (nArray[0] < 110) {
                this.moment[n4] = 0.75f + (float)(nArray[3] - 16) / 184.0f * 1.25f;
            }
            if (nArray[3] == 200 && nArray[4] == 200 && nArray[0] <= 88) {
                this.moment[n4] = 3.0f;
            }
            if ((double)(f2 = 0.9f + (float)(nArray[4] - 90) * 0.01f) < 0.6) {
                f2 = 0.6f;
            }
            if (nArray[4] == 200 && nArray[0] <= 88) {
                f2 = 3.0f;
            }
            this.maxmag[n4] = (int)(f * f2);
            this.outdam[n4] = 0.35f + (f2 - 0.6f) * 0.5f;
            if ((double)this.outdam[n4] < 0.35) {
                this.outdam[n4] = 0.35f;
            }
            if (this.outdam[n4] > 1.0f) {
                this.outdam[n4] = 1.0f;
            }
            this.clrad[n4] = (int)((double)(nArray3[0] * nArray3[0]) * 1.5);
            if (this.clrad[n4] < 1000) {
                this.clrad[n4] = 1000;
            }
            this.dammult[n4] = 0.3f + (float)nArray3[1] * 0.005f;
            this.msquash[n4] = (int)(2.0 + (double)nArray3[2] / 7.6);
            this.flipy[n4] = n2;
            this.handb[n4] = (int)(7.0f + (float)nArray2[0] / 100.0f * 8.0f);
            this.turn[n4] = (int)(4.0f + (float)nArray2[1] / 100.0f * 6.0f);
            this.grip[n4] = 16.0f + (float)nArray2[2] / 100.0f * 14.0f;
            if (this.grip[n4] < 21.0f) {
                int[] nArray4 = this.swits[n4];
                nArray4[0] = (int)((float)nArray4[0] + 40.0f * ((21.0f - this.grip[n4]) / 5.0f));
                if (this.swits[n4][0] > 100) {
                    this.swits[n4][0] = 100;
                }
            }
            this.bounce[n4] = 0.8f + (float)nArray2[3] / 100.0f * 0.6f;
            if (nArray2[3] > 67) {
                int n14 = n4;
                this.airs[n14] = this.airs[n14] * (0.76f + (1.0f - (float)nArray2[3] / 100.0f) * 0.24f);
                int n15 = n4;
                this.airc[n15] = (int)((float)this.airc[n15] * (0.76f + (1.0f - (float)nArray2[3] / 100.0f) * 0.24f));
            }
            this.lift[n4] = (int)((float)nArray2[5] * (float)nArray2[5] / 10000.0f * 30.0f);
            this.revlift[n4] = (int)((float)nArray2[6] / 100.0f * 32.0f);
            this.push[n4] = (int)(2.0f + (float)nArray2[7] / 100.0f * 2.0f * (float)((30 - this.lift[n4]) / 30));
            this.revpush[n4] = (int)(1.0f + (float)nArray2[8] / 100.0f * 2.0f);
            this.comprad[n4] = (float)n / 400.0f + (float)(nArray[3] - 16) / 184.0f * 0.2f;
            if ((double)this.comprad[n4] < 0.4) {
                this.comprad[n4] = 0.4f;
            }
            this.simag[n4] = (float)(n3 - 17) * 0.0167f + 0.85f;
        } else {
            this.names[n4] = "";
        }
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

    public String getSvalue(String string, String string2, int n) {
        String string3 = "";
        int n2 = 0;
        for (int i = string.length() + 1; i < string2.length() && n2 <= n; ++i) {
            String string4 = "" + string2.charAt(i);
            if (string4.equals(",") || string4.equals(")")) {
                ++n2;
                continue;
            }
            if (n2 != n) continue;
            string3 = string3 + string4;
        }
        return string3;
    }

    public int servervalue(String string, int n) {
        int n2 = -1;
        try {
            int n3 = 0;
            int n4 = 0;
            String string2 = "";
            String string3 = "";
            for (int i = 0; i < string.length() && n4 != 2; ++i) {
                string2 = "" + string.charAt(i);
                if (string2.equals("|")) {
                    if (n4 != 1 && ++n3 <= n) continue;
                    n4 = 2;
                    continue;
                }
                if (n3 != n) continue;
                string3 = string3 + string2;
                n4 = 1;
            }
            if (string3.equals("")) {
                string3 = "-1";
            }
            n2 = Integer.valueOf(string3);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return n2;
    }

    public String serverSvalue(String string, int n) {
        String string2 = "";
        try {
            int n2 = 0;
            int n3 = 0;
            String string3 = "";
            String string4 = "";
            for (int i = 0; i < string.length() && n3 != 2; ++i) {
                string3 = "" + string.charAt(i);
                if (string3.equals("|")) {
                    if (n3 != 1 && ++n2 <= n) continue;
                    n3 = 2;
                    continue;
                }
                if (n2 != n) continue;
                string4 = string4 + string3;
                n3 = 1;
            }
            string2 = string4;
        }
        catch (Exception exception) {
            // empty catch block
        }
        return string2;
    }

    public void loadready() {
        this.m.csky[0] = 170;
        this.m.csky[1] = 220;
        this.m.csky[2] = 255;
        this.m.cfade[0] = 255;
        this.m.cfade[1] = 220;
        this.m.cfade[2] = 220;
        this.m.snap[0] = 0;
        this.m.snap[1] = 0;
        this.m.snap[2] = 0;
        this.fails = "";
        for (int i = 0; i < 20; ++i) {
            this.loadnames[i] = "";
        }
        this.nl = 0;
        this.action = 0;
    }

    public void sparkactionloader() {
        this.actionloader = new Thread(this);
        this.actionloader.start();
    }

    public void sparkcarloader() {
        if (!this.carlon) {
            this.carloader = new Thread(this);
            this.carloader.start();
            this.carlon = true;
        }
    }

    public void sparkstageaction() {
        this.stageaction = new Thread(this);
        this.stageaction.start();
    }

    public void stopallnow() {
        this.staction = 0;
        this.action = 0;
        if (this.carloader != null) {
            this.carloader.stop();
            this.carloader = null;
        }
        if (this.actionloader != null) {
            this.actionloader.stop();
            this.actionloader = null;
        }
        if (this.stageaction != null) {
            this.stageaction.stop();
            this.stageaction = null;
        }
    }

    @Override
    public void run() {
        String string;
        Object object;
        Object object2;
        if (Thread.currentThread() == this.actionloader) {
            Object object3;
            if (this.action == 10) {
                int n = -1;
                try {
                    object2 = new Socket("multiplayer.needformadness.com", 7061);
                    object3 = new BufferedReader(new InputStreamReader(((Socket)object2).getInputStream()));
                    object = new PrintWriter(((Socket)object2).getOutputStream(), true);
                    ((PrintWriter)object).println("9|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.names[this.ac] + "|");
                    string = ((BufferedReader)object3).readLine();
                    if (string != null) {
                        n = this.servervalue(string, 0);
                    }
                    ((Socket)object2).close();
                    ((BufferedReader)object3).close();
                    ((PrintWriter)object).close();
                }
                catch (Exception exception) {
                    n = -1;
                }
                this.action = n == 0 ? 3 : -10;
                System.gc();
            }
            if (this.action == 1) {
                this.reco = -1;
                try {
                    Socket socket = new Socket("multiplayer.needformadness.com", 7061);
                    object2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    object3 = new PrintWriter(socket.getOutputStream(), true);
                    ((PrintWriter)object3).println("1|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|");
                    object = ((BufferedReader)object2).readLine();
                    if (object != null) {
                        this.reco = this.servervalue((String)object, 0);
                        if (this.reco == 0 || this.reco == 3 || this.reco > 10) {
                            this.tnickey = this.serverSvalue((String)object, 1);
                            if (this.reco != -167) {
                                this.tclan = this.serverSvalue((String)object, 2);
                                this.tclankey = this.serverSvalue((String)object, 3);
                            } else {
                                this.tclan = "";
                                this.tclankey = "";
                            }
                        }
                    }
                    socket.close();
                    ((BufferedReader)object2).close();
                    ((PrintWriter)object3).close();
                }
                catch (Exception exception) {
                    this.reco = -1;
                }
                this.action = this.reco == 0 || this.reco == 3 || this.reco > 10 ? 2 : 0;
                System.gc();
            }
            while (this.action == 2) {
            }
            if (this.action == 3) {
                String[] stringArray = new String[700];
                this.nl = 0;
                object2 = "";
                try {
                    object3 = new URL("http://multiplayer.needformadness.com/cars/lists/" + this.gs.tnick.getText() + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "");
                    object = new DataInputStream(((URL)object3).openStream());
                    while ((object2 = ((DataInputStream)object).readLine()) != null) {
                        object2 = "" + ((String)object2).trim();
                        if (!((String)object2).startsWith("mycars")) continue;
                        boolean bl = true;
                        while (bl && this.nl < 700) {
                            stringArray[this.nl] = this.getSvalue("mycars", (String)object2, this.nl);
                            if (stringArray[this.nl].equals("")) {
                                bl = false;
                                continue;
                            }
                            ++this.nl;
                        }
                    }
                    this.action = this.nl > 0 ? 4 : -1;
                    ((FilterInputStream)object).close();
                }
                catch (Exception exception) {
                    object = "" + exception;
                    this.action = ((String)object).indexOf("FileNotFound") != -1 ? -1 : -2;
                }
                if (this.action == 4) {
                    int n;
                    this.gs.mcars.hide();
                    this.gs.mcars.removeAll();
                    for (n = 0; n < this.nl; ++n) {
                        this.gs.mcars.add(this.gs.rd, stringArray[n]);
                    }
                    if (this.lastcar.equals("")) {
                        this.gs.mcars.select(0);
                    } else {
                        this.gs.mcars.select(this.lastcar);
                        this.lastcar = "";
                    }
                    for (n = 0; n < 40; ++n) {
                        this.include[n] = false;
                    }
                    this.roundslot = 16;
                    this.nlocars = 16;
                }
            }
            if (this.action == 4) {
                this.m.csky[0] = 170;
                this.m.csky[1] = 220;
                this.m.csky[2] = 255;
                this.m.cfade[0] = 255;
                this.m.cfade[1] = 220;
                this.m.cfade[2] = 220;
                this.m.snap[0] = 0;
                this.m.snap[1] = 0;
                this.m.snap[2] = 0;
                if (this.loadonlinecar(this.gs.mcars.getSelectedItem(), this.roundslot) == this.roundslot) {
                    this.inslot = this.roundslot++;
                    if (this.roundslot == 36) {
                        this.roundslot = 16;
                    }
                    if (this.nlocars < 36) {
                        ++this.nlocars;
                    }
                    this.lastload = 2;
                    this.action = 5;
                } else {
                    if (this.lastload == 2) {
                        this.lastload = 0;
                    }
                    this.action = -1;
                }
                System.gc();
            }
            if (this.action == 6) {
                int n = -1;
                try {
                    if (this.ac != -1) {
                        this.acname = this.names[this.ac];
                    }
                    object2 = new Socket("multiplayer.needformadness.com", 7061);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(((Socket)object2).getInputStream()));
                    object = new PrintWriter(((Socket)object2).getOutputStream(), true);
                    ((PrintWriter)object).println("8|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.acname + "|");
                    string = bufferedReader.readLine();
                    if (string != null) {
                        n = this.servervalue(string, 0);
                    }
                    ((Socket)object2).close();
                    bufferedReader.close();
                    ((PrintWriter)object).close();
                }
                catch (Exception exception) {
                    n = -1;
                }
                if (n == 0) {
                    if (this.lastload == 2) {
                        this.lastload = -2;
                        this.lastcar = this.gs.mcars.getSelectedItem();
                    }
                    this.action = 7;
                }
                if (n == 3) {
                    this.action = -7;
                }
                if (n == 4) {
                    this.action = -8;
                }
                if (this.action == 6) {
                    this.action = -9;
                }
                System.gc();
            }
            if (this.action == 11) {
                this.nl = 0;
                String string2 = "";
                try {
                    object2 = "all";
                    if (this.loadlist == 1) {
                        object2 = "Wall";
                    }
                    if (this.loadlist == 2) {
                        object2 = "WA";
                    }
                    if (this.loadlist == 3) {
                        object2 = "WAB";
                    }
                    if (this.loadlist == 4) {
                        object2 = "WB";
                    }
                    if (this.loadlist == 5) {
                        object2 = "WBC";
                    }
                    if (this.loadlist == 6) {
                        object2 = "WC";
                    }
                    if (this.loadlist == 7) {
                        object2 = "Mall";
                    }
                    if (this.loadlist == 8) {
                        object2 = "MA";
                    }
                    if (this.loadlist == 9) {
                        object2 = "MAB";
                    }
                    if (this.loadlist == 10) {
                        object2 = "MB";
                    }
                    if (this.loadlist == 11) {
                        object2 = "MBC";
                    }
                    if (this.loadlist == 12) {
                        object2 = "MC";
                    }
                    if (this.loadlist == 13) {
                        object2 = "Sall";
                    }
                    if (this.loadlist == 14) {
                        object2 = "SA";
                    }
                    if (this.loadlist == 15) {
                        object2 = "SAB";
                    }
                    if (this.loadlist == 16) {
                        object2 = "SB";
                    }
                    if (this.loadlist == 17) {
                        object2 = "SBC";
                    }
                    if (this.loadlist == 18) {
                        object2 = "SC";
                    }
                    if (this.loadlist == 19) {
                        object2 = "Aall";
                    }
                    if (this.loadlist == 20) {
                        object2 = "AA";
                    }
                    if (this.loadlist == 21) {
                        object2 = "AAB";
                    }
                    if (this.loadlist == 22) {
                        object2 = "AB";
                    }
                    if (this.loadlist == 23) {
                        object2 = "ABC";
                    }
                    if (this.loadlist == 24) {
                        object2 = "AC";
                    }
                    URL uRL = new URL("http://multiplayer.needformadness.com/cars/top20/" + (String)object2 + ".txt");
                    object = new DataInputStream(uRL.openStream());
                    while ((string2 = ((DataInputStream)object).readLine()) != null) {
                        int n;
                        string2 = "" + string2.trim();
                        if (string2.startsWith("cars")) {
                            n = 1;
                            while (n != 0 && this.nl < 20) {
                                this.loadnames[this.nl] = this.getSvalue("cars", string2, this.nl);
                                if (this.loadnames[this.nl].equals("")) {
                                    n = 0;
                                    continue;
                                }
                                ++this.nl;
                            }
                        }
                        if (!string2.startsWith("adds")) continue;
                        for (n = 0; n < this.nl; ++n) {
                            this.adds[n] = this.getvalue("adds", string2, n);
                        }
                    }
                    this.action = this.nl > 0 ? 12 : -1;
                    ((FilterInputStream)object).close();
                }
                catch (Exception exception) {
                    this.action = -1;
                }
                System.gc();
            }
            if (this.action == 12) {
                this.m.csky[0] = 170;
                this.m.csky[1] = 220;
                this.m.csky[2] = 255;
                this.m.cfade[0] = 255;
                this.m.cfade[1] = 220;
                this.m.cfade[2] = 220;
                this.m.snap[0] = 0;
                this.m.snap[1] = 0;
                this.m.snap[2] = 0;
                this.xnlocars = 36;
                int n = this.nl;
                this.nl = 0;
                while (this.nl < n) {
                    if (this.xnlocars < 56 && this.loadonlinecar(this.loadnames[this.nl], this.xnlocars) == this.xnlocars) {
                        ++this.xnlocars;
                    }
                    ++this.nl;
                }
                this.nl = 0;
                this.action = this.xnlocars > 36 ? 13 : -1;
                System.gc();
            }
            if (this.action == 101) {
                Object object4;
                this.nl = 0;
                String string3 = "";
                try {
                    object2 = new URL("http://multiplayer.needformadness.com/cars/lists/" + this.viewname + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "");
                    object4 = new DataInputStream(((URL)object2).openStream());
                    while ((string3 = ((DataInputStream)object4).readLine()) != null) {
                        string3 = "" + string3.trim();
                        if (!string3.startsWith("mycars")) continue;
                        boolean bl = true;
                        while (bl && this.nl < 20) {
                            this.loadnames[this.nl] = this.getSvalue("mycars", string3, this.nl);
                            if (this.loadnames[this.nl].equals("")) {
                                bl = false;
                                continue;
                            }
                            ++this.nl;
                        }
                    }
                    this.action = this.nl > 0 ? 102 : -2;
                    ((FilterInputStream)object4).close();
                }
                catch (Exception exception) {
                    object4 = "" + exception;
                    this.action = ((String)object4).indexOf("FileNotFound") != -1 ? -2 : -1;
                }
                System.gc();
            }
            if (this.action == 102) {
                int n;
                this.m.csky[0] = 170;
                this.m.csky[1] = 220;
                this.m.csky[2] = 255;
                this.m.cfade[0] = 255;
                this.m.cfade[1] = 220;
                this.m.cfade[2] = 220;
                this.m.snap[0] = 0;
                this.m.snap[1] = 0;
                this.m.snap[2] = 0;
                for (n = 0; n < 40; ++n) {
                    this.include[n] = false;
                }
                this.xnlocars = 36;
                n = this.nl;
                this.nl = 0;
                while (this.nl < n) {
                    if (this.xnlocars < 56 && this.loadonlinecar(this.loadnames[this.nl], this.xnlocars) == this.xnlocars) {
                        ++this.xnlocars;
                    }
                    ++this.nl;
                }
                this.nl = 0;
                this.action = this.xnlocars > 36 ? 103 : -1;
                System.gc();
            }
            this.actionloader = null;
        }
        if (Thread.currentThread() == this.carloader) {
            while (this.nl > 0) {
                int n;
                int n2 = 0;
                for (n = 16; n < 56; ++n) {
                    if (!this.loadnames[this.nl - 1].equals(this.names[n])) continue;
                    n2 = -1;
                }
                if (this.fails.indexOf("|" + this.loadnames[this.nl - 1] + "|") != -1) {
                    n2 = -1;
                }
                if (n2 != -1) {
                    n = this.lcardate[0];
                    int n3 = 36;
                    if (this.haltload > 0) {
                        n3 = 36 + this.haltload;
                        n = this.lcardate[this.haltload];
                    }
                    n2 = n3;
                    for (int i = n3; i < 56; ++i) {
                        if (this.lcardate[i - 36] >= n) continue;
                        n = this.lcardate[i - 36];
                        n2 = i;
                    }
                    this.onloadingcar = n2 - 35;
                    if (this.loadonlinecar(this.loadnames[this.nl - 1], n2) == -1) {
                        this.fails = this.fails + "|" + this.loadnames[this.nl - 1] + "|";
                    } else {
                        int n4 = n2 - 36;
                        this.lcardate[n4] = this.lcardate[n4] + 1;
                    }
                    this.onloadingcar = 0;
                }
                --this.nl;
                try {
                    Thread.sleep(20L);
                }
                catch (InterruptedException interruptedException) {}
            }
            this.carlon = false;
            this.carloader = null;
        }
        if (Thread.currentThread() == this.stageaction) {
            if (this.staction == 1) {
                int n = -1;
                try {
                    object2 = new Socket("multiplayer.needformadness.com", 7061);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(((Socket)object2).getInputStream()));
                    object = new PrintWriter(((Socket)object2).getOutputStream(), true);
                    ((PrintWriter)object).println("19|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.onstage + "|");
                    string = bufferedReader.readLine();
                    if (string != null) {
                        n = this.servervalue(string, 0);
                    }
                    ((Socket)object2).close();
                    bufferedReader.close();
                    ((PrintWriter)object).close();
                }
                catch (Exception exception) {
                    n = -1;
                }
                if (n == 0) {
                    try {
                        this.gs.mstgs.remove(this.onstage);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    this.gs.mstgs.select(0);
                    this.staction = 0;
                } else {
                    this.staction = -1;
                }
            }
            if (this.staction == 4) {
                this.reco = -1;
                try {
                    Socket socket = new Socket("multiplayer.needformadness.com", 7061);
                    object2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                    printWriter.println("1|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|");
                    object = ((BufferedReader)object2).readLine();
                    if (object != null) {
                        this.reco = this.servervalue((String)object, 0);
                        if (this.reco == 0 || this.reco == 3 || this.reco > 10) {
                            this.tnickey = this.serverSvalue((String)object, 1);
                            if (this.reco != -167) {
                                this.tclan = this.serverSvalue((String)object, 2);
                                this.tclankey = this.serverSvalue((String)object, 3);
                            } else {
                                this.tclan = "";
                                this.tclankey = "";
                            }
                        }
                    }
                    socket.close();
                    ((BufferedReader)object2).close();
                    printWriter.close();
                }
                catch (Exception exception) {
                    this.reco = -1;
                }
                this.staction = this.reco == 0 || this.reco == 3 || this.reco > 10 ? 5 : 3;
                System.gc();
                while (this.staction == 5) {
                }
            }
            if (this.staction == 2) {
                int n = -1;
                if (this.msloaded == 1) {
                    for (int i = 1; i < this.gs.mstgs.getItemCount(); ++i) {
                        if (!this.gs.mstgs.getItem(i).equals(this.onstage)) continue;
                        n = 3;
                    }
                }
                if (n == -1) {
                    try {
                        Socket socket = new Socket("multiplayer.needformadness.com", 7061);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        object = new PrintWriter(socket.getOutputStream(), true);
                        ((PrintWriter)object).println("18|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.onstage + "|");
                        string = bufferedReader.readLine();
                        if (string != null) {
                            n = this.servervalue(string, 0);
                        }
                        socket.close();
                        bufferedReader.close();
                        ((PrintWriter)object).close();
                    }
                    catch (Exception exception) {
                        n = -1;
                    }
                }
                this.staction = -1;
                if (n == 0) {
                    this.staction = 0;
                    if (this.msloaded == 1) {
                        this.gs.mstgs.addstg(this.onstage);
                    }
                }
                if (n == 3) {
                    this.staction = -2;
                }
                if (n == 4) {
                    this.staction = -3;
                }
            }
            this.stageaction = null;
        }
    }

    public int loadonlinecar(String string, int n) {
        try {
            int n2;
            Object object;
            ZipInputStream zipInputStream;
            String string2 = "http://multiplayer.needformadness.com/cars/" + string + ".radq";
            string2 = string2.replace(' ', '_');
            URL uRL = new URL(string2);
            int n3 = uRL.openConnection().getContentLength();
            DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
            byte[] byArray = new byte[n3];
            dataInputStream.readFully(byArray);
            if (byArray[0] == 80 && byArray[1] == 75 && byArray[2] == 3) {
                zipInputStream = new ZipInputStream(new ByteArrayInputStream(byArray));
            } else {
                object = new byte[n3 - 40];
                for (n2 = 0; n2 < n3 - 40; ++n2) {
                    int n4 = 20;
                    if (n2 >= 500) {
                        n4 = 40;
                    }
                    object[n2] = byArray[n2 + n4];
                }
                zipInputStream = new ZipInputStream(new ByteArrayInputStream((byte[])object));
            }
            object = zipInputStream.getNextEntry();
            if (object != null) {
                int n5;
                byte[] byArray2 = new byte[n2];
                int n6 = 0;
                for (n2 = Integer.valueOf(((ZipEntry)object).getName()).intValue(); n2 > 0; n2 -= n5) {
                    n5 = zipInputStream.read(byArray2, n6, n2);
                    n6 += n5;
                }
                this.m.loadnew = true;
                this.bco[n] = new ContO(byArray2, this.m, this.t);
                if (this.bco[n].errd || this.bco[n].npl <= 60 || this.bco[n].maxR < 120) {
                    n = -1;
                }
                if (n != -1) {
                    this.bco[n].shadow = true;
                    this.bco[n].noline = false;
                    this.bco[n].decor = false;
                    this.bco[n].tnt = 0;
                    this.bco[n].disp = 0;
                    this.bco[n].disline = 7;
                    this.bco[n].grounded = 1.0f;
                    boolean bl = true;
                    if (this.bco[n].keyz[0] < 0 || this.bco[n].keyx[0] > 0) {
                        bl = false;
                    }
                    if (this.bco[n].keyz[1] < 0 || this.bco[n].keyx[1] < 0) {
                        bl = false;
                    }
                    if (this.bco[n].keyz[2] > 0 || this.bco[n].keyx[2] > 0) {
                        bl = false;
                    }
                    if (this.bco[n].keyz[3] > 0 || this.bco[n].keyx[3] < 0) {
                        bl = false;
                    }
                    if (!bl) {
                        n = -1;
                    }
                }
                if (n != -1) {
                    this.loadstat(byArray2, string, this.bco[n].maxR, this.bco[n].roofat, this.bco[n].wh, n);
                    if (this.names[n].equals("")) {
                        n = -1;
                    }
                }
                this.m.loadnew = false;
                dataInputStream.close();
                zipInputStream.close();
            } else {
                n = -1;
            }
        }
        catch (Exception exception) {
            n = -1;
        }
        System.gc();
        return n;
    }

    public void loadmystages(CheckPoints checkPoints) {
        Object object;
        String[] stringArray = new String[700];
        int n = 0;
        String string = "";
        try {
            URL uRL = new URL("http://multiplayer.needformadness.com/tracks/lists/" + this.gs.tnick.getText() + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "");
            object = new DataInputStream(uRL.openStream());
            while ((string = ((DataInputStream)object).readLine()) != null) {
                string = "" + string.trim();
                if (!string.startsWith("mystages")) continue;
                boolean bl = true;
                while (bl && n < 700) {
                    stringArray[n] = this.getSvalue("mystages", string, n);
                    if (stringArray[n].equals("")) {
                        bl = false;
                        continue;
                    }
                    ++n;
                }
            }
            this.msloaded = n > 0 ? 1 : -2;
            ((FilterInputStream)object).close();
        }
        catch (Exception exception) {
            object = "" + exception;
            this.msloaded = ((String)object).indexOf("FileNotFound") != -1 ? -2 : -1;
        }
        if (this.msloaded == 1) {
            this.gs.mstgs.hide();
            this.gs.mstgs.removeAll();
            this.gs.mstgs.add(this.gs.rd, "Select Stage");
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                this.gs.mstgs.add(this.gs.rd, stringArray[i]);
                if (!checkPoints.name.equals(stringArray[i])) continue;
                n2 = i + 1;
                checkPoints.top20 = 0;
            }
            this.gs.mstgs.select(n2);
            this.gs.mstgs.show();
        }
        if (this.msloaded == -2) {
            this.gs.mstgs.hide();
            this.gs.mstgs.removeAll();
            this.gs.mstgs.add(this.gs.rd, "You have not published or added any stages...");
            this.gs.mstgs.select(0);
            this.gs.mstgs.show();
        }
        if (this.msloaded == -1) {
            this.gs.mstgs.hide();
            this.gs.mstgs.removeAll();
            this.gs.mstgs.add(this.gs.rd, "Failed to load stages, please try again later.");
            this.gs.mstgs.select(0);
            this.gs.mstgs.show();
        }
        System.gc();
    }

    public void loadtop20(int n) {
        Object object;
        String[] stringArray = new String[20];
        int n2 = 0;
        String string = "";
        try {
            String string2 = "A";
            if (n == 3) {
                string2 = "W";
            }
            if (n == 4) {
                string2 = "M";
            }
            object = new URL("http://multiplayer.needformadness.com/tracks/top20/" + string2 + ".txt");
            DataInputStream dataInputStream = new DataInputStream(((URL)object).openStream());
            while ((string = dataInputStream.readLine()) != null) {
                int n3;
                string = "" + string.trim();
                if (string.startsWith("stages")) {
                    n3 = 1;
                    while (n3 != 0 && n2 < 20) {
                        stringArray[n2] = this.getSvalue("stages", string, n2);
                        if (stringArray[n2].equals("")) {
                            n3 = 0;
                            continue;
                        }
                        ++n2;
                    }
                }
                if (!string.startsWith("adds")) continue;
                for (n3 = 0; n3 < n2; ++n3) {
                    this.top20adds[n3] = this.getvalue("adds", string, n3);
                }
            }
            this.msloaded = n2 > 0 ? n : -2;
            dataInputStream.close();
        }
        catch (Exception exception) {
            object = "" + exception;
            this.msloaded = ((String)object).indexOf("FileNotFound") != -1 ? -2 : -1;
        }
        if (this.msloaded == n) {
            this.gs.mstgs.hide();
            this.gs.mstgs.removeAll();
            this.gs.mstgs.add(this.gs.rd, "Select Stage");
            for (int i = 0; i < n2; ++i) {
                this.gs.mstgs.add(this.gs.rd, "N#" + (i + 1) + " " + stringArray[i]);
            }
            this.gs.mstgs.select(0);
            this.gs.mstgs.show();
        }
        if (this.msloaded == -1 || this.msloaded == -2) {
            this.gs.mstgs.hide();
            this.gs.mstgs.removeAll();
            this.gs.mstgs.add(this.gs.rd, "Failed to load Top20 list, please try again later.");
            this.gs.mstgs.select(0);
            this.gs.mstgs.show();
        }
        System.gc();
    }

    public void loadclanstages(String string) {
        if (!string.equals("")) {
            int n;
            Object object;
            String[] stringArray = new String[700];
            String string2 = "";
            try {
                URL uRL = new URL("http://multiplayer.needformadness.com/clans/" + string + "/stages.txt");
                object = new DataInputStream(uRL.openStream());
                for (n = 0; (string2 = ((DataInputStream)object).readLine()) != null && n < 700; ++n) {
                    stringArray[n] = string2;
                }
                this.msloaded = n > 0 ? 7 : -2;
                ((FilterInputStream)object).close();
            }
            catch (Exception exception) {
                object = "" + exception;
                this.msloaded = ((String)object).indexOf("FileNotFound") != -1 ? -2 : -1;
            }
            if (this.msloaded == 7) {
                this.gs.mstgs.hide();
                this.gs.mstgs.removeAll();
                this.gs.mstgs.add(this.gs.rd, "Select Stage");
                for (int i = 0; i < n; ++i) {
                    this.gs.mstgs.add(this.gs.rd, stringArray[i]);
                }
                this.gs.mstgs.select(0);
                this.gs.mstgs.show();
            }
            if (this.msloaded == -1) {
                this.gs.mstgs.hide();
                this.gs.mstgs.removeAll();
                this.gs.mstgs.add(this.gs.rd, "Failed to load clan stages, please try again later.");
                this.gs.mstgs.select(0);
                this.gs.mstgs.show();
            }
            if (this.msloaded == -2) {
                this.gs.mstgs.hide();
                this.gs.mstgs.removeAll();
                this.gs.mstgs.add(this.gs.rd, "No stages have been added to your clan yet.");
                this.gs.mstgs.select(0);
                this.gs.mstgs.show();
            }
            System.gc();
        } else {
            this.msloaded = -2;
            this.gs.mstgs.hide();
            this.gs.mstgs.removeAll();
            this.gs.mstgs.add(this.gs.rd, "You are not a member of any clan yet.");
            this.gs.mstgs.select(0);
            this.gs.mstgs.show();
        }
    }

    public void loadstagemaker() {
        this.gs.mstgs.hide();
        this.gs.mstgs.removeAll();
        this.gs.mstgs.add(this.gs.rd, "Select Stage");
        int n = 0;
        File file = new File("" + Madness.fpath + "mystages/");
        if (file.exists()) {
            String[] stringArray = new File("" + Madness.fpath + "mystages/").list();
            for (int i = 0; i < stringArray.length; ++i) {
                if (!stringArray[i].toLowerCase().endsWith(".txt") || n >= 200) continue;
                this.gs.mstgs.add(this.gs.rd, stringArray[i].substring(0, stringArray[i].length() - 4));
                ++n;
            }
        }
        if (n != 0) {
            this.msloaded = 2;
        } else {
            this.gs.mstgs.add(this.gs.rd, "No stages where found in your 'mystages' folder.");
            this.msloaded = -1;
        }
        this.gs.mstgs.select(0);
        this.gs.mstgs.show();
    }

    public void loadcarmaker() {
        this.m.csky[0] = 170;
        this.m.csky[1] = 220;
        this.m.csky[2] = 255;
        this.m.cfade[0] = 255;
        this.m.cfade[1] = 220;
        this.m.cfade[2] = 220;
        this.m.snap[0] = 0;
        this.m.snap[1] = 0;
        this.m.snap[2] = 0;
        for (int i = 0; i < 40; ++i) {
            this.include[i] = false;
        }
        this.nlcars = 16;
        File file = new File("" + Madness.fpath + "mycars/");
        if (file.exists()) {
            String[] stringArray = new File("" + Madness.fpath + "mycars/").list();
            for (int i = 0; i < stringArray.length; ++i) {
                if (!stringArray[i].toLowerCase().endsWith(".rad") || this.nlcars >= 56 || this.loadcar(stringArray[i].substring(0, stringArray[i].length() - 4), this.nlcars) != this.nlcars) continue;
                ++this.nlcars;
            }
        }
        System.gc();
        if (this.nlcars > 16) {
            this.lastload = 1;
        }
    }

    public int loadcar(String string, int n) {
        try {
            File file = new File("" + Madness.fpath + "mycars/" + string + ".rad");
            if (file.exists()) {
                String string2 = "";
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String string3 = null;
                while ((string3 = bufferedReader.readLine()) != null) {
                    string2 = string2 + "" + string3 + "\n";
                }
                bufferedReader.close();
                bufferedReader = null;
                this.m.loadnew = true;
                this.bco[n] = new ContO(string2.getBytes(), this.m, this.t);
                if (this.bco[n].errd || this.bco[n].npl <= 60) {
                    n = -1;
                }
                if (n != -1) {
                    this.bco[n].shadow = true;
                    this.bco[n].noline = false;
                    this.bco[n].decor = false;
                    this.bco[n].tnt = 0;
                    this.bco[n].disp = 0;
                    this.bco[n].disline = 7;
                    this.bco[n].grounded = 1.0f;
                    boolean bl = true;
                    if (this.bco[n].keyz[0] < 0 || this.bco[n].keyx[0] > 0) {
                        bl = false;
                    }
                    if (this.bco[n].keyz[1] < 0 || this.bco[n].keyx[1] < 0) {
                        bl = false;
                    }
                    if (this.bco[n].keyz[2] > 0 || this.bco[n].keyx[2] > 0) {
                        bl = false;
                    }
                    if (this.bco[n].keyz[3] > 0 || this.bco[n].keyx[3] < 0) {
                        bl = false;
                    }
                    if (!bl) {
                        n = -1;
                    }
                }
                if (n != -1) {
                    this.loadstat(string2.getBytes(), string, this.bco[n].maxR, this.bco[n].roofat, this.bco[n].wh, n);
                    if (this.names[n].equals("")) {
                        n = -1;
                    }
                }
                this.m.loadnew = false;
            } else {
                n = -1;
            }
        }
        catch (Exception exception) {
            n = -1;
            System.out.println("Error Loading Car: " + exception);
        }
        System.gc();
        return n;
    }
}

