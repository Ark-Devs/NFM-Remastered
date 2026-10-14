/*
 * Decompiled with CFR 0.152.
 */
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class xtGraphics
extends Panel
implements Runnable {
    Graphics2D rd;
    Medium m;
    CarDefine cd;
    FontMetrics ftm;
    ImageObserver ob;
    GameSparker app;
    int fase;
    int oldfase;
    int starcnt;
    boolean mtop;
    int opselect;
    int dropf;
    int cfase;
    boolean firstime;
    boolean shaded;
    int flipo;
    int nextc;
    int multion;
    int gmode;
    int[] unlocked;
    int[] scm;
    int looped;
    int warning;
    boolean newparts;
    boolean logged;
    boolean gotlog;
    boolean autolog;
    boolean nofull;
    int nfreeplays;
    int ndisco;
    int hours;
    boolean onviewpro;
    int playingame;
    int onjoin;
    int ontyp;
    boolean lan;
    float[] arnp;
    String nickname;
    String clan;
    String nickey;
    String clankey;
    String backlog;
    String server;
    String localserver;
    String servername;
    int servport;
    int gameport;
    int acexp;
    int discon;
    int cntptrys;
    int[] delays;
    int nplayers;
    int im;
    String[] plnames;
    int osc;
    int minsl;
    int maxsl;
    int[] sc;
    int[] xstart;
    int[] zstart;
    float[][] allrnp;
    boolean[] isbot;
    int clangame;
    boolean clanchat;
    String[] pclan;
    String gaclan;
    int lcarx;
    int lcary;
    int lcarz;
    int[] dcrashes;
    int beststunt;
    int laptime;
    int fastestlap;
    int sendstat;
    int testdrive;
    boolean holdit;
    int holdcnt;
    boolean winner;
    int[] flexpix;
    int[] smokey;
    Image fleximg;
    int flatrstart;
    Thread runner;
    int runtyp;
    int forstart;
    int exitm;
    Image odmg;
    Image opwr;
    Image opos;
    Image osped;
    Image owas;
    Image olap;
    Image oyourwasted;
    Image odisco;
    Image ogamefinished;
    Image oyoulost;
    Image oyouwon;
    Image oyouwastedem;
    Image ogameh;
    Image owgame;
    Image oloadingmusic;
    Image oflaot;
    Image oexitgame;
    Image mload;
    Image dmg;
    Image pwr;
    Image pos;
    Image sped;
    Image was;
    Image lap;
    Image br;
    Image select;
    Image loadingmusic;
    Image yourwasted;
    Image disco;
    Image gamefinished;
    Image youlost;
    Image youwon;
    Image youwastedem;
    Image gameh;
    Image wgame;
    Image congrd;
    Image gameov;
    Image carsbg;
    Image carsbgc;
    Image selectcar;
    Image statb;
    Image statbo;
    Image mdness;
    Image paused;
    Image radicalplay;
    Image logocars;
    Image logomadnes;
    Image logomadbg;
    Image byrd;
    Image bggo;
    Image opback;
    Image nfmcoms;
    Image opti;
    Image opti2;
    Image bgmain;
    Image rpro;
    Image nfmcom;
    Image flaot;
    Image brt;
    Image arn;
    Image exitgame;
    Image pgate;
    Image fixhoop;
    Image sarrow;
    Image stunts;
    Image racing;
    Image wasting;
    Image plus;
    Image space;
    Image arrows;
    Image chil;
    Image ory;
    Image kz;
    Image kx;
    Image kv;
    Image km;
    Image kn;
    Image ks;
    Image kenter;
    Image nfm;
    Image login;
    Image register;
    Image play;
    Image sdets;
    Image cancel;
    Image bob;
    Image bot;
    Image bol;
    Image bolp;
    Image bor;
    Image borp;
    Image logout;
    Image change;
    Image pln;
    Image pon;
    Image dome;
    Image upgrade;
    Image bols;
    Image bolps;
    Image bors;
    Image borps;
    Image games;
    Image exit;
    Image chat;
    Image players;
    Image cgame;
    Image ccar;
    Image lanm;
    Image asu;
    Image asd;
    Image pls;
    Image sts;
    Image gmc;
    Image stg;
    Image crd;
    Image roomp;
    Image myfr;
    Image mycl;
    Image cnmc;
    Image redy;
    Image ntrg;
    Image[] bcl;
    Image[] bcr;
    Image[] bc;
    Image cmc;
    Image myc;
    Image gac;
    Image yac;
    Image ycmc;
    Image top20s;
    Image[] trackbg;
    Image[] dude;
    int duds;
    int dudo;
    Image[] next;
    Image[] back;
    Image[] contin;
    Image[] ostar;
    Image[] star;
    int pcontin;
    int pnext;
    int pback;
    int pstar;
    Image[] orank;
    Image[] rank;
    Image[] ocntdn;
    Image[] cntdn;
    int gocnt;
    soundClip[][] engs;
    boolean[] pengs;
    soundClip[] air;
    boolean aird;
    boolean grrd;
    soundClip[] crash;
    soundClip[] lowcrash;
    soundClip tires;
    soundClip checkpoint;
    soundClip carfixed;
    soundClip powerup;
    soundClip three;
    soundClip two;
    soundClip one;
    soundClip go;
    soundClip wastd;
    soundClip firewasted;
    boolean pwastd;
    soundClip[] skid;
    soundClip[] dustskid;
    soundClip[] scrape;
    boolean mutes;
    RadicalMod intertrack;
    RadicalMod strack;
    boolean loadedt;
    boolean mutem;
    boolean badmac;
    boolean arrace;
    int alocked;
    int lalocked;
    int cntflock;
    boolean onlock;
    int ana;
    int cntan;
    int cntovn;
    boolean flk;
    int tcnt;
    boolean tflk;
    String say;
    boolean wasay;
    int clear;
    int posit;
    int wasted;
    int laps;
    int[] dested;
    int dmcnt;
    boolean dmflk;
    int pwcnt;
    boolean pwflk;
    String[][] adj;
    String[] exlm;
    String loop;
    String spin;
    String asay;
    int auscnt;
    boolean aflk;
    int[] sndsize;
    Image hello;
    Image sign;
    Image loadbar;
    int kbload;
    int dnload;
    float shload;
    Socket socket;
    BufferedReader din;
    PrintWriter dout;
    int radpx;
    int pin;
    int[] trkx;
    int trkl;
    int trklim;
    int lmode;
    int[] bgmy;
    float bgf;
    boolean bgup;
    int[] ovx;
    int[] ovy;
    int[] ovw;
    int[] ovh;
    int[] ovsx;
    int removeds;
    int nfmtab;
    boolean justwon1;
    boolean justwon2;
    int lfrom;
    int lockcnt;
    boolean showtf;
    int ransay;
    String[][] cnames;
    String[][] sentn;
    int[] updatec;
    int[] movepos;
    int[] pointc;
    int[] floater;
    int[] cntchatp;
    int[] msgflk;
    String[] lcmsg;
    int flkat;
    int movly;
    int gxdu;
    int gydu;
    int muhi;
    int lsc;
    int mouson;
    int onmsc;
    boolean remi;
    int basefase;
    boolean noclass;
    int gatey;
    int[] pgatx;
    int[] pgaty;
    int[] pgady;
    boolean[] pgas;
    int waitlink;
    int lxm;
    int lym;
    int pwait;
    int stopcnt;
    int cntwis;
    int lcn;
    int crshturn;
    int bfcrash;
    int bfskid;
    boolean crashup;
    boolean skidup;
    int skflg;
    int dskflg;
    int bfscrape;
    int sturn0;
    int sturn1;
    int bfsc1;
    int bfsc2;
    int flatr;
    int flyr;
    int flyrdest;
    int flang;

    public xtGraphics(Medium medium, CarDefine carDefine, Graphics2D graphics2D, GameSparker gameSparker) {
        block4: {
            this.fase = 111;
            this.oldfase = 0;
            this.starcnt = 0;
            this.mtop = false;
            this.opselect = 0;
            this.dropf = 0;
            this.cfase = 0;
            this.firstime = true;
            this.shaded = false;
            this.flipo = 0;
            this.nextc = 0;
            this.multion = 0;
            this.gmode = 0;
            this.unlocked = new int[]{1, 1};
            this.scm = new int[]{0, 0};
            this.looped = 1;
            this.warning = 0;
            this.newparts = false;
            this.logged = false;
            this.gotlog = false;
            this.autolog = false;
            this.nofull = false;
            this.nfreeplays = 0;
            this.ndisco = 0;
            this.hours = 8;
            this.onviewpro = false;
            this.playingame = -1;
            this.onjoin = -1;
            this.ontyp = 0;
            this.lan = false;
            this.arnp = new float[]{0.5f, 0.0f, 0.0f, 1.0f, 0.5f, 0.0f};
            this.nickname = "";
            this.clan = "";
            this.nickey = "";
            this.clankey = "";
            this.backlog = "";
            this.server = "multiplayer.needformadness.com";
            this.localserver = "";
            this.servername = "Madness";
            this.servport = 7071;
            this.gameport = 7001;
            this.acexp = 0;
            this.discon = 0;
            this.cntptrys = 5;
            this.delays = new int[]{600, 600, 600};
            this.nplayers = 7;
            this.im = 0;
            this.plnames = new String[]{"", "", "", "", "", "", "", ""};
            this.osc = 10;
            this.minsl = 0;
            this.maxsl = 15;
            this.sc = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
            this.xstart = new int[]{0, -350, 350, 0, -350, 350, 0, 0};
            this.zstart = new int[]{-760, -380, -380, 0, 380, 380, 760, 0};
            this.allrnp = new float[8][6];
            this.isbot = new boolean[8];
            this.clangame = 0;
            this.clanchat = false;
            this.pclan = new String[]{"", "", "", "", "", "", "", ""};
            this.gaclan = "";
            this.lcarx = 0;
            this.lcary = 0;
            this.lcarz = 0;
            this.dcrashes = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
            this.beststunt = 0;
            this.laptime = 0;
            this.fastestlap = 0;
            this.sendstat = 0;
            this.testdrive = 0;
            this.holdit = false;
            this.holdcnt = 0;
            this.winner = true;
            this.flexpix = null;
            this.smokey = new int[94132];
            this.flatrstart = 0;
            this.runtyp = 0;
            this.forstart = 0;
            this.exitm = 0;
            this.bcl = new Image[2];
            this.bcr = new Image[2];
            this.bc = new Image[2];
            this.trackbg = new Image[2];
            this.dude = new Image[3];
            this.duds = 0;
            this.dudo = 0;
            this.next = new Image[2];
            this.back = new Image[2];
            this.contin = new Image[2];
            this.ostar = new Image[2];
            this.star = new Image[3];
            this.pcontin = 0;
            this.pnext = 0;
            this.pback = 0;
            this.pstar = 0;
            this.orank = new Image[8];
            this.rank = new Image[8];
            this.ocntdn = new Image[4];
            this.cntdn = new Image[4];
            this.gocnt = 0;
            this.engs = new soundClip[5][5];
            this.pengs = new boolean[5];
            this.air = new soundClip[6];
            this.aird = false;
            this.grrd = false;
            this.crash = new soundClip[3];
            this.lowcrash = new soundClip[3];
            this.pwastd = false;
            this.skid = new soundClip[3];
            this.dustskid = new soundClip[3];
            this.scrape = new soundClip[4];
            this.mutes = false;
            this.loadedt = false;
            this.mutem = false;
            this.badmac = false;
            this.arrace = false;
            this.alocked = -1;
            this.lalocked = -1;
            this.cntflock = 0;
            this.onlock = false;
            this.ana = 0;
            this.cntan = 0;
            this.cntovn = 0;
            this.flk = false;
            this.tcnt = 30;
            this.tflk = false;
            this.say = "";
            this.wasay = false;
            this.clear = 0;
            this.posit = 0;
            this.wasted = 0;
            this.laps = 0;
            this.dested = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
            this.dmcnt = 0;
            this.dmflk = false;
            this.pwcnt = 0;
            this.pwflk = false;
            this.adj = new String[][]{{"Cool", "Alright", "Nice"}, {"Wicked", "Amazing", "Super"}, {"Awesome", "Ripping", "Radical"}, {"What the...?", "You're a super star!!!!", "Who are you again...?"}, {"surf style", "off the lip", "bounce back"}};
            this.exlm = new String[]{"!", "!!", "!!!"};
            this.loop = "";
            this.spin = "";
            this.asay = "";
            this.auscnt = 45;
            this.aflk = false;
            this.sndsize = new int[]{39, 128, 23, 58, 106, 140, 81, 135, 38, 141, 106, 76, 56, 116, 92, 208, 70, 80, 152, 102, 27, 65, 52, 30, 151, 129, 80, 44, 57, 123, 202, 210, 111};
            this.kbload = 0;
            this.dnload = 0;
            this.shload = 0.0f;
            this.radpx = 212;
            this.pin = 60;
            this.trkx = new int[]{65, 735};
            this.trkl = 0;
            this.trklim = (int)(Math.random() * 40.0);
            this.lmode = 0;
            this.bgmy = new int[]{0, -400};
            this.bgf = 0.0f;
            this.bgup = false;
            this.ovx = new int[]{0, 0, 0, 0};
            this.ovy = new int[]{0, 0, 0, 0};
            this.ovw = new int[]{0, 0, 0, 0};
            this.ovh = new int[]{0, 0, 0, 0};
            this.ovsx = new int[]{0, 0, 0, 0};
            this.removeds = 0;
            this.nfmtab = 0;
            this.justwon1 = false;
            this.justwon2 = false;
            this.lfrom = 0;
            this.lockcnt = 0;
            this.showtf = false;
            this.ransay = 0;
            this.cnames = new String[][]{{"", "", "", "", "", "", "Game Chat  "}, {"", "", "", "", "", "", "Your Clan's Chat  "}};
            this.sentn = new String[][]{{"", "", "", "", "", "", ""}, {"", "", "", "", "", "", ""}};
            this.updatec = new int[]{-1, -1};
            this.movepos = new int[]{0, 0};
            this.pointc = new int[]{6, 6};
            this.floater = new int[]{0, 0};
            this.cntchatp = new int[]{0, 0};
            this.msgflk = new int[]{0, 0};
            this.lcmsg = new String[]{"", ""};
            this.flkat = 0;
            this.movly = 0;
            this.gxdu = 0;
            this.gydu = 0;
            this.muhi = 0;
            this.lsc = -1;
            this.mouson = -1;
            this.onmsc = -1;
            this.remi = false;
            this.basefase = 0;
            this.noclass = false;
            this.gatey = 300;
            this.pgatx = new int[]{211, 240, 280, 332, 399, 466, 517, 558, 586};
            this.pgaty = new int[]{193, 213, 226, 237, 244, 239, 228, 214, 196};
            this.pgady = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
            this.pgas = new boolean[]{false, false, false, false, false, false, false, false, false};
            this.waitlink = 0;
            this.lxm = -10;
            this.lym = -10;
            this.pwait = 7;
            this.stopcnt = 0;
            this.cntwis = 0;
            this.lcn = 0;
            this.crshturn = 0;
            this.bfcrash = 0;
            this.bfskid = 0;
            this.crashup = false;
            this.skidup = false;
            this.skflg = 0;
            this.dskflg = 0;
            this.bfscrape = 0;
            this.sturn0 = 0;
            this.sturn1 = 0;
            this.bfsc1 = 0;
            this.bfsc2 = 0;
            this.flatr = 0;
            this.flyr = 0;
            this.flyrdest = 0;
            this.flang = 0;
            this.m = medium;
            this.cd = carDefine;
            this.app = gameSparker;
            this.rd = graphics2D;
            this.hello = this.getImage("data/hello.gif");
            this.sign = this.getImage("data/sign.gif");
            this.loadbar = this.getImage("data/loadbar.gif");
            for (int i = 0; i < 5; ++i) {
                this.pengs[i] = false;
            }
            this.nofull = false;
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                try {
                    securityManager.checkConnect("needformadness.com", -1);
                }
                catch (Exception exception) {
                    String string = "" + exception;
                    if (string.indexOf("access denied") == -1) break block4;
                    this.nofull = true;
                }
            }
        }
        this.badmac = false;
    }

    public void loaddata() {
        this.kbload = 637;
        this.runtyp = 176;
        this.runner = new Thread(this);
        this.runner.start();
        this.loadimages();
        this.intertrack = new RadicalMod("music/interface.zip");
        this.dnload += 44;
        this.loadsounds();
    }

    public void loadsounds() {
        this.dnload += 3;
        try {
            File file = new File("" + Madness.fpath + "data/sounds.zip");
            FileInputStream fileInputStream = new FileInputStream(file);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                int n;
                int n2;
                int n3;
                String string = zipEntry.getName();
                byte[] byArray = new byte[n3];
                int n4 = 0;
                for (n3 = (int)zipEntry.getSize(); n3 > 0; n3 -= n2) {
                    n2 = zipInputStream.read(byArray, n4, n3);
                    n4 += n2;
                }
                for (n = 0; n < 5; ++n) {
                    for (int i = 0; i < 5; ++i) {
                        if (!string.equals("" + i + "" + n + ".wav")) continue;
                        this.engs[i][n] = new soundClip(byArray);
                    }
                }
                for (n = 0; n < 6; ++n) {
                    if (!string.equals("air" + n + ".wav")) continue;
                    this.air[n] = new soundClip(byArray);
                }
                for (n = 0; n < 3; ++n) {
                    if (!string.equals("crash" + (n + 1) + ".wav")) continue;
                    this.crash[n] = new soundClip(byArray);
                }
                for (n = 0; n < 3; ++n) {
                    if (!string.equals("lowcrash" + (n + 1) + ".wav")) continue;
                    this.lowcrash[n] = new soundClip(byArray);
                }
                for (n = 0; n < 3; ++n) {
                    if (!string.equals("skid" + (n + 1) + ".wav")) continue;
                    this.skid[n] = new soundClip(byArray);
                }
                for (n = 0; n < 3; ++n) {
                    if (!string.equals("dustskid" + (n + 1) + ".wav")) continue;
                    this.dustskid[n] = new soundClip(byArray);
                }
                for (n = 0; n < 3; ++n) {
                    if (!string.equals("scrape" + (n + 1) + ".wav")) continue;
                    this.scrape[n] = new soundClip(byArray);
                    if (n != 2) continue;
                    this.scrape[3] = new soundClip(byArray);
                }
                if (string.equals("powerup.wav")) {
                    this.powerup = new soundClip(byArray);
                }
                if (string.equals("tires.wav")) {
                    this.tires = new soundClip(byArray);
                }
                if (string.equals("checkpoint.wav")) {
                    this.checkpoint = new soundClip(byArray);
                }
                if (string.equals("carfixed.wav")) {
                    this.carfixed = new soundClip(byArray);
                }
                if (string.equals("three.wav")) {
                    this.three = new soundClip(byArray);
                }
                if (string.equals("two.wav")) {
                    this.two = new soundClip(byArray);
                }
                if (string.equals("one.wav")) {
                    this.one = new soundClip(byArray);
                }
                if (string.equals("go.wav")) {
                    this.go = new soundClip(byArray);
                }
                if (string.equals("wasted.wav")) {
                    this.wastd = new soundClip(byArray);
                }
                if (string.equals("firewasted.wav")) {
                    this.firewasted = new soundClip(byArray);
                }
                this.dnload += 5;
                zipEntry = zipInputStream.getNextEntry();
            }
            fileInputStream.close();
            zipInputStream.close();
        }
        catch (Exception exception) {
            System.out.println("Error Loading Sounds: " + exception);
        }
        System.gc();
    }

    public void loadimages() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker mediaTracker = new MediaTracker(this.app);
        Image image = null;
        Image image2 = null;
        this.dnload += 8;
        try {
            File file = new File("" + Madness.fpath + "data/images.zip");
            FileInputStream fileInputStream = new FileInputStream(file);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                int n;
                int n2;
                String string = zipEntry.getName();
                byte[] byArray = new byte[n2];
                int n3 = 0;
                for (n2 = (int)zipEntry.getSize(); n2 > 0; n2 -= n) {
                    n = zipInputStream.read(byArray, n3, n2);
                    n3 += n;
                }
                if (string.equals("cars.gif")) {
                    this.carsbg = this.loadBimage(byArray, mediaTracker, toolkit, 1);
                }
                if (string.equals("color.gif")) {
                    image = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("class.gif")) {
                    image2 = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("smokey.gif")) {
                    this.smokeypix(byArray, mediaTracker, toolkit);
                }
                if (string.equals("1.gif")) {
                    this.orank[0] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("gameh.gif")) {
                    this.ogameh = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("wgame.gif")) {
                    this.owgame = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("gameov.gif")) {
                    this.gameov = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("lap.gif")) {
                    this.olap = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("paused.gif")) {
                    this.paused = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("select.gif")) {
                    this.select = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("yourwasted.gif")) {
                    this.oyourwasted = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("disco.gif")) {
                    this.odisco = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("youwastedem.gif")) {
                    this.oyouwastedem = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("gamefinished.gif")) {
                    this.ogamefinished = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("exitgame.gif")) {
                    this.oexitgame = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("pgate.gif")) {
                    this.pgate = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("d1.png")) {
                    this.dude[0] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("d2.png")) {
                    this.dude[1] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("d3.png")) {
                    this.dude[2] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("float.gif")) {
                    this.oflaot = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("1c.gif")) {
                    this.ocntdn[1] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("2c.gif")) {
                    this.ocntdn[2] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("3c.gif")) {
                    this.ocntdn[3] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("2.gif")) {
                    this.orank[1] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("3.gif")) {
                    this.orank[2] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("4.gif")) {
                    this.orank[3] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("5.gif")) {
                    this.orank[4] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("6.gif")) {
                    this.orank[5] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("7.gif")) {
                    this.orank[6] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("8.gif")) {
                    this.orank[7] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("bgmain.jpg")) {
                    this.bgmain = this.loadBimage(byArray, mediaTracker, toolkit, 2);
                }
                if (string.equals("br.png")) {
                    this.br = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("loadingmusic.gif")) {
                    this.oloadingmusic = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("radicalplay.gif")) {
                    this.radicalplay = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("back.gif")) {
                    this.back[0] = this.loadimage(byArray, mediaTracker, toolkit);
                    this.back[1] = this.bressed(this.back[0]);
                }
                if (string.equals("continue.gif")) {
                    this.contin[0] = this.loadimage(byArray, mediaTracker, toolkit);
                    this.contin[1] = this.bressed(this.contin[0]);
                }
                if (string.equals("next.gif")) {
                    this.next[0] = this.loadimage(byArray, mediaTracker, toolkit);
                    this.next[1] = this.bressed(this.next[0]);
                }
                if (string.equals("rpro.gif")) {
                    this.rpro = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("selectcar.gif")) {
                    this.selectcar = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("track.jpg")) {
                    this.trackbg[0] = this.loadBimage(byArray, mediaTracker, toolkit, 3);
                    this.trackbg[1] = this.dodgen(this.trackbg[0]);
                }
                if (string.equals("youlost.gif")) {
                    this.oyoulost = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("youwon.gif")) {
                    this.oyouwon = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("0c.gif")) {
                    this.ocntdn[0] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("damage.gif")) {
                    this.odmg = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("power.gif")) {
                    this.opwr = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("position.gif")) {
                    this.opos = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("speed.gif")) {
                    this.osped = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("wasted.gif")) {
                    this.owas = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("start1.gif")) {
                    this.ostar[0] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("start2.gif")) {
                    this.ostar[1] = this.loadimage(byArray, mediaTracker, toolkit);
                    this.star[2] = this.pressed(this.ostar[1]);
                }
                if (string.equals("congrad.gif")) {
                    this.congrd = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("statb.gif")) {
                    this.statb = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("statbo.gif")) {
                    this.statbo = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("madness.gif")) {
                    this.mdness = this.loadude(byArray, mediaTracker, toolkit);
                }
                if (string.equals("fixhoop.png")) {
                    this.fixhoop = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("arrow.gif")) {
                    this.sarrow = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("stunts.png")) {
                    this.stunts = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("racing.gif")) {
                    this.racing = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("wasting.gif")) {
                    this.wasting = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("plus.gif")) {
                    this.plus = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("space.gif")) {
                    this.space = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("arrows.gif")) {
                    this.arrows = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("chil.gif")) {
                    this.chil = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("ory.gif")) {
                    this.ory = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("kz.gif")) {
                    this.kz = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("kx.gif")) {
                    this.kx = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("kv.gif")) {
                    this.kv = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("km.gif")) {
                    this.km = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("kn.gif")) {
                    this.kn = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("ks.gif")) {
                    this.ks = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("kenter.gif")) {
                    this.kenter = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("nfm.gif")) {
                    this.nfm = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("options.png")) {
                    this.opti = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("options2.png")) {
                    this.opti2 = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("opback.png")) {
                    this.opback = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("logocars.png")) {
                    this.logocars = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("logomad.png")) {
                    this.logomadnes = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("logomadbg.jpg")) {
                    this.logomadbg = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("byrd.png")) {
                    this.byrd = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("bggo.jpg")) {
                    this.bggo = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("nfmcoms.png")) {
                    this.nfmcoms = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("nfmcom.gif")) {
                    this.nfmcom = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("brit.gif")) {
                    this.brt = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("arn.gif")) {
                    this.arn = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("mload.gif")) {
                    this.mload = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("login.gif")) {
                    this.login = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("play.gif")) {
                    this.play = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("cancel.gif")) {
                    this.cancel = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("dome.gif")) {
                    this.dome = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("register.gif")) {
                    this.register = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("upgrade.gif")) {
                    this.upgrade = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("sdets.gif")) {
                    this.sdets = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("bob.gif")) {
                    this.bob = this.loadBimage(byArray, mediaTracker, toolkit, 1);
                }
                if (string.equals("bot.gif")) {
                    this.bot = this.loadBimage(byArray, mediaTracker, toolkit, 1);
                }
                if (string.equals("bol.gif")) {
                    this.bol = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("bolp.gif")) {
                    this.bolp = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("bor.gif")) {
                    this.bor = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("borp.gif")) {
                    this.borp = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("logout.gif")) {
                    this.logout = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("change.gif")) {
                    this.change = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("pln.gif")) {
                    this.pln = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("pon.gif")) {
                    this.pon = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("bols.gif")) {
                    this.bols = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("bolps.gif")) {
                    this.bolps = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("bors.gif")) {
                    this.bors = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("borps.gif")) {
                    this.borps = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("games.gif")) {
                    this.games = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("exit.gif")) {
                    this.exit = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("chat.gif")) {
                    this.chat = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("players.gif")) {
                    this.players = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("roomp.gif")) {
                    this.roomp = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("myfr.gif")) {
                    this.myfr = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("mycl.gif")) {
                    this.mycl = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("cnmc.gif")) {
                    this.cnmc = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("ready.gif")) {
                    this.redy = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("notreg.gif")) {
                    this.ntrg = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("cgame.gif")) {
                    this.cgame = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("ccar.gif")) {
                    this.ccar = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("lanm.gif")) {
                    this.lanm = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("asu.gif")) {
                    this.asu = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("asd.gif")) {
                    this.asd = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("pls.gif")) {
                    this.pls = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("sts.gif")) {
                    this.sts = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("gmc.gif")) {
                    this.gmc = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("stg.gif")) {
                    this.stg = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("crd.gif")) {
                    this.crd = this.loadBimage(byArray, mediaTracker, toolkit, 0);
                }
                if (string.equals("bcl.gif")) {
                    this.bcl[0] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("bcr.gif")) {
                    this.bcr[0] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("bc.gif")) {
                    this.bc[0] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("pbcl.gif")) {
                    this.bcl[1] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("pbcr.gif")) {
                    this.bcr[1] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("pbc.gif")) {
                    this.bc[1] = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("cmc.gif")) {
                    this.cmc = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("myc.gif")) {
                    this.myc = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("gac.gif")) {
                    this.gac = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("yac.gif")) {
                    this.yac = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("ycmc.gif")) {
                    this.ycmc = this.loadimage(byArray, mediaTracker, toolkit);
                }
                if (string.equals("top20s.gif")) {
                    this.top20s = this.loadimage(byArray, mediaTracker, toolkit);
                }
                this.dnload += 2;
                zipEntry = zipInputStream.getNextEntry();
            }
            fileInputStream.close();
            zipInputStream.close();
        }
        catch (Exception exception) {
            System.out.println("Error Loading Images: " + exception);
        }
        this.makecarsbgc(image, image2);
        System.gc();
    }

    @Override
    public void run() {
        String string;
        boolean bl = false;
        while (this.runtyp > 0) {
            if (this.runtyp >= 1 && this.runtyp <= 140) {
                this.hipnoload(this.runtyp, false);
            }
            if (this.runtyp == 176) {
                this.loading();
                bl = true;
            }
            this.app.repaint();
            try {
                Thread.sleep(20L);
            }
            catch (InterruptedException interruptedException) {}
        }
        if (bl) {
            this.pingstat();
            bl = false;
        }
        boolean[] blArray = new boolean[]{true, true};
        while (!(this.runtyp != -101 && this.sendstat != 1 || this.lan)) {
            int n;
            string = "3|" + this.playingame + "|" + this.updatec[0] + "|";
            string = this.clanchat ? string + "" + this.updatec[1] + "|" + this.clan + "|" + this.clankey + "|" : string + "0|||";
            if (this.updatec[0] <= -11) {
                for (n = 0; n < -this.updatec[0] - 10; ++n) {
                    string = string + "" + this.cnames[0][6 - n] + "|" + this.sentn[0][6 - n] + "|";
                }
                this.updatec[0] = -2;
            }
            if (this.clanchat && this.updatec[1] <= -11) {
                for (n = 0; n < -this.updatec[1] - 10; ++n) {
                    string = string + "" + this.cnames[1][6 - n] + "|" + this.sentn[1][6 - n] + "|";
                }
                this.updatec[1] = -2;
            }
            if (this.sendstat == 1) {
                string = "5|" + this.playingame + "|" + this.im + "|" + this.beststunt + "|" + this.fastestlap + "|";
                for (n = 0; n < this.nplayers; ++n) {
                    string = string + "" + this.dcrashes[n] + "|";
                }
                this.sendstat = 2;
            }
            n = 0;
            String string2 = "";
            try {
                this.dout.println(string);
                string2 = this.din.readLine();
                if (string2 == null) {
                    n = 1;
                }
            }
            catch (Exception exception) {
                n = 1;
            }
            if (n != 0) {
                try {
                    this.socket.close();
                    this.socket = null;
                    this.din.close();
                    this.din = null;
                    this.dout.close();
                    this.dout = null;
                }
                catch (Exception exception) {
                    // empty catch block
                }
                try {
                    this.socket = new Socket(this.server, this.servport);
                    this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                    this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                    this.dout.println(string);
                    string2 = this.din.readLine();
                    if (string2 != null) {
                        n = 0;
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (n != 0) {
                try {
                    this.socket.close();
                    this.socket = null;
                }
                catch (Exception exception) {
                    // empty catch block
                }
                this.runtyp = 0;
                if (this.app.cmsg.isShowing()) {
                    this.app.cmsg.hide();
                    this.app.requestFocus();
                }
                this.runner.stop();
            }
            if (this.sendstat != 2) {
                int n2 = 2;
                int n3 = 1;
                if (this.clanchat) {
                    n3 = 2;
                }
                for (int i = 0; i < n3; ++i) {
                    int n4 = this.getvalue(string2, i);
                    if (this.updatec[i] == n4 || this.updatec[i] < -2 || this.pointc[i] != 6) continue;
                    for (int j = 0; j < 7; ++j) {
                        this.cnames[i][j] = this.getSvalue(string2, n2);
                        this.sentn[i][j] = this.getSvalue(string2, ++n2);
                        ++n2;
                    }
                    if (this.cnames[i][6].equals("")) {
                        this.cnames[i][6] = i == 0 ? "Game Chat  " : "" + this.clan + "'s Chat  ";
                    }
                    if (this.updatec[i] != -2) {
                        this.floater[i] = 1;
                        if (blArray[i]) {
                            this.msgflk[i] = 67;
                            blArray[i] = false;
                        } else {
                            this.msgflk[i] = 110;
                        }
                    }
                    this.updatec[i] = n4;
                }
            } else {
                this.sendstat = 3;
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {}
        }
        if (this.runtyp == -167 || this.runtyp == -168) {
            try {
                this.socket = new Socket("multiplayer.needformadness.com", 7061);
                this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                this.dout.println("101|" + (this.runtyp + 174) + "|" + this.app.tnick.getText() + "|" + this.app.tpass.getText() + "|");
                string = this.din.readLine();
                this.socket.close();
                this.socket = null;
                this.din.close();
                this.din = null;
                this.dout.close();
                this.dout = null;
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.runtyp = 0;
        }
        if (this.runtyp == -166 || this.runtyp == -167 || this.runtyp == -168) {
            this.pingstat();
        }
    }

    public void stopchat() {
        this.clanchat = false;
        this.clangame = 0;
        if (this.runtyp == -101) {
            this.runtyp = 0;
            try {
                this.socket.close();
                this.socket = null;
                this.din.close();
                this.din = null;
                this.dout.close();
                this.dout = null;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void sendwin(CheckPoints checkPoints) {
        this.runtyp = this.logged && this.multion == 1 && this.winner ? (checkPoints.wasted == this.nplayers - 1 ? -167 : -168) : -166;
        this.runner = new Thread(this);
        this.runner.start();
    }

    public void loading() {
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.fillRect(0, 0, 800, 450);
        this.rd.drawImage(this.sign, 362, 35, this);
        this.rd.drawImage(this.hello, 125, 105, this);
        this.rd.setColor(new Color(198, 214, 255));
        this.rd.fillRoundRect(250, 340, 300, 80, 30, 70);
        this.rd.setColor(new Color(128, 167, 255));
        this.rd.drawRoundRect(250, 340, 300, 80, 30, 70);
        this.rd.drawImage(this.loadbar, 281, 365, this);
        this.rd.setFont(new Font("Arial", 1, 11));
        this.ftm = this.rd.getFontMetrics();
        this.drawcs(358, "Loading game, please wait.", 0, 0, 0, 3);
        this.rd.setColor(new Color(255, 255, 255));
        this.rd.fillRect(295, 398, 210, 17);
        this.shload += ((float)this.dnload + 10.0f - this.shload) / 100.0f;
        if (this.shload > (float)this.kbload) {
            this.shload = this.kbload;
        }
        if (this.dnload == this.kbload) {
            this.shload = this.kbload;
        }
        this.drawcs(410, "" + (int)((26.0f + this.shload / (float)this.kbload * 200.0f) / 226.0f * 100.0f) + " % loaded    |    " + (this.kbload - (int)this.shload) + " KB remaining", 32, 64, 128, 3);
        this.rd.setColor(new Color(32, 64, 128));
        this.rd.fillRect(287, 371, 26 + (int)(this.shload / (float)this.kbload * 200.0f), 10);
    }

    public void stoploading() {
        this.loading();
        this.app.repaint();
        this.runtyp = 0;
    }

    public void clicknow() {
        this.rd.setColor(new Color(198, 214, 255));
        this.rd.fillRoundRect(250, 340, 300, 80, 30, 70);
        this.rd.setColor(new Color(128, 167, 255));
        this.rd.drawRoundRect(250, 340, 300, 80, 30, 70);
        if (this.aflk) {
            this.drawcs(380, "Click here to Start", 0, 0, 0, 3);
            this.aflk = false;
        } else {
            this.drawcs(380, "Click here to Start", 0, 67, 200, 3);
            this.aflk = true;
        }
    }

    public void stopallnow() {
        int n;
        if (this.runner != null) {
            this.runner.stop();
            this.runner = null;
        }
        this.runtyp = 0;
        if (this.loadedt) {
            this.strack.unload();
            this.strack = null;
            this.loadedt = false;
        }
        for (n = 0; n < 5; ++n) {
            for (int i = 0; i < 5; ++i) {
                this.engs[n][i].stop();
                this.engs[n][i] = null;
            }
        }
        for (n = 0; n < 6; ++n) {
            this.air[n].stop();
            this.air[n] = null;
        }
        this.wastd.stop();
        this.intertrack.unload();
        this.intertrack = null;
    }

    public void resetstat(int n) {
        int n2;
        this.arrace = false;
        this.alocked = -1;
        this.lalocked = -1;
        this.cntflock = 90;
        this.onlock = false;
        this.ana = 0;
        this.cntan = 0;
        this.cntovn = 0;
        this.tcnt = 30;
        this.wasay = false;
        this.clear = 0;
        this.dmcnt = 0;
        this.pwcnt = 0;
        this.auscnt = 45;
        this.pnext = 0;
        this.pback = 0;
        this.starcnt = 130;
        this.gocnt = 3;
        this.grrd = true;
        this.aird = true;
        this.bfcrash = 0;
        this.bfscrape = 0;
        this.cntwis = 0;
        this.bfskid = 0;
        this.pwait = 7;
        this.forstart = 200;
        this.exitm = 0;
        this.holdcnt = 0;
        this.holdit = false;
        this.winner = false;
        this.wasted = 0;
        for (n2 = 0; n2 < 8; ++n2) {
            this.dested[n2] = 0;
            this.isbot[n2] = false;
            this.dcrashes[n2] = 0;
        }
        this.runtyp = 0;
        this.discon = 0;
        this.dnload = 0;
        this.beststunt = 0;
        this.laptime = 0;
        this.fastestlap = 0;
        this.sendstat = 0;
        if (this.fase == 2 || this.fase == -22) {
            this.sortcars(n);
        }
        if (this.fase == 22) {
            for (n2 = 0; n2 < 2; ++n2) {
                for (int i = 0; i < 7; ++i) {
                    this.cnames[n2][i] = "";
                    this.sentn[n2][i] = "";
                }
                this.cnames[n2][6] = n2 == 0 ? "Game Chat  " : "" + this.clan + "'s Chat  ";
                this.updatec[n2] = -1;
                this.movepos[n2] = 0;
                this.pointc[n2] = 6;
                this.floater[n2] = 0;
                this.cntchatp[n2] = 0;
                this.msgflk[n2] = 0;
                this.lcmsg[n2] = "";
            }
            if (this.multion == 3) {
                this.ransay = 4;
            } else if (this.ransay == 0) {
                this.ransay = 1 + (int)(Math.random() * 3.0);
            } else {
                ++this.ransay;
                if (this.ransay > 3) {
                    this.ransay = 1;
                }
            }
        }
    }

    public void setbots(boolean[] blArray, int[][] nArray) {
        for (int i = 0; i < this.nplayers; ++i) {
            if (this.plnames[i].indexOf("MadBot") == -1) continue;
            blArray[i] = true;
            this.isbot[i] = true;
        }
    }

    public void rad(int n) {
        if (n == 0) {
            this.powerup.play();
            this.radpx = 212;
            this.pin = 0;
        }
        this.trackbg(false);
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.fillRect(65, 135, 670, 59);
        if (this.pin != 0) {
            this.rd.drawImage(this.radicalplay, this.radpx + (int)(8.0 * Math.random() - 4.0), 135, null);
        } else {
            this.rd.drawImage(this.radicalplay, 212, 135, null);
        }
        if (this.radpx != 212) {
            this.radpx += 40;
            if (this.radpx > 735) {
                this.radpx = -388;
            }
        } else if (this.pin != 0) {
            --this.pin;
        }
        if (n == 40) {
            this.radpx = 213;
            this.pin = 7;
        }
        if (this.radpx == 212) {
            this.rd.setFont(new Font("Arial", 1, 11));
            this.ftm = this.rd.getFontMetrics();
            this.drawcs(185 + (int)(5.0f * this.m.random()), "Radicalplay.com", 112, 120, 143, 3);
        }
        this.rd.setFont(new Font("Arial", 1, 11));
        this.ftm = this.rd.getFontMetrics();
        if (this.aflk) {
            this.drawcs(215, "And we are never going to find the new unless we get a little crazy...", 112, 120, 143, 3);
            this.aflk = false;
        } else {
            this.drawcs(217, "And we are never going to find the new unless we get a little crazy...", 150, 150, 150, 3);
            this.aflk = true;
        }
        this.rd.drawImage(this.rpro, 275, 265, null);
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.fillRect(0, 0, 65, 450);
        this.rd.fillRect(735, 0, 65, 450);
        this.rd.fillRect(65, 0, 670, 25);
        this.rd.fillRect(65, 425, 670, 25);
    }

    public void credits(Control control, int n, int n2, int n3) {
        if (this.flipo == 0) {
            this.powerup.play();
            this.flipo = 1;
        }
        if (this.flipo >= 1 && this.flipo <= 100) {
            this.rad(this.flipo);
            ++this.flipo;
            if (this.flipo == 100) {
                this.flipo = 1;
            }
        }
        if (this.flipo == 101) {
            this.mainbg(-1);
            this.rd.drawImage(this.mdness, 283, 32, null);
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            this.drawcs(90, "At Radicalplay.com", 0, 0, 0, 3);
            this.drawcs(165, "Cartoon 3D Engine, Game Programming, 3D Models, Graphics and Sound Effects", 0, 0, 0, 3);
            this.drawcs(185, "By Omar Waly", 40, 60, 0, 3);
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            this.drawcs(225, "Special Thanks!", 0, 0, 0, 3);
            this.rd.setFont(new Font("Arial", 1, 11));
            this.ftm = this.rd.getFontMetrics();
            this.drawcs(245, "Thanks to Dany Fernandez Diaz (DragShot) for imporving the game\u2019s music player to play more mod formats & effects!", 66, 98, 0, 3);
            this.drawcs(260, "Thanks to Badie El Zaman (Kingofspeed) for helping make the trees & cactus 3D models.", 66, 98, 0, 3);
            this.drawcs(275, "Thanks to Timothy Audrain Hardin (Legnak) for making hazard designs on stage parts & the new fence 3D model.", 66, 98, 0, 3);
            this.drawcs(290, "Thanks to Alex Miles (A-Mile) & Jaroslav Beleren (Phyrexian) for making trailer videos for the game.", 66, 98, 0, 3);
            this.drawcs(305, "A big thank you to everyone playing the game for sending their feedback, supporting the game and helping it improve!", 66, 98, 0, 3);
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            this.drawcs(345, "Music from ModArchive.org", 0, 0, 0, 3);
            this.rd.setFont(new Font("Arial", 1, 11));
            this.ftm = this.rd.getFontMetrics();
            this.drawcs(365, "Most of the tracks where remixed by Omar Waly to match the game.", 66, 98, 0, 3);
            this.drawcs(380, "More details about the tracks and their original composers at:", 66, 98, 0, 3);
            this.drawcs(395, "http://multiplayer.needformadness.com/music.html", 33, 49, 0, 3);
            this.rd.drawLine(400 - this.ftm.stringWidth("http://multiplayer.needformadness.com/music.html") / 2, 396, this.ftm.stringWidth("http://multiplayer.needformadness.com/music.html") / 2 + 400, 396);
            if (n > 258 && n < 542 && n2 > 385 && n2 < 399) {
                this.app.setCursor(new Cursor(12));
                if (n3 == 2) {
                    this.app.musiclink();
                }
            } else {
                this.app.setCursor(new Cursor(0));
            }
        }
        if (this.flipo == 102) {
            this.mainbg(0);
            this.rd.drawImage(this.nfmcom, 190, 195, null);
            if (n > 190 && n < 609 && n2 > 195 && n2 < 216) {
                this.app.setCursor(new Cursor(12));
                if (n3 == 2) {
                    this.app.madlink();
                }
            } else {
                this.app.setCursor(new Cursor(0));
            }
        }
        this.rd.drawImage(this.next[this.pnext], 665, 395, null);
        if (control.enter || control.handb || control.right) {
            if (this.flipo >= 1 && this.flipo <= 100) {
                this.flipo = 101;
                this.app.setCursor(new Cursor(0));
            } else {
                ++this.flipo;
            }
            if (this.flipo == 103) {
                this.flipo = 0;
                this.fase = 10;
            }
            control.enter = false;
            control.handb = false;
            control.right = false;
        }
    }

    public void trackbg(boolean bl) {
        int n = 0;
        ++this.trkl;
        if (this.trkl > this.trklim) {
            n = 1;
            this.trklim = (int)(Math.random() * 40.0);
            this.trkl = 0;
        }
        if (bl) {
            n = 0;
        }
        for (int i = 0; i < 2; ++i) {
            this.rd.drawImage(this.trackbg[n], this.trkx[i], 25, null);
            int n2 = i;
            this.trkx[n2] = this.trkx[n2] - 10;
            if (this.trkx[i] > -605) continue;
            this.trkx[i] = 735;
        }
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.fillRect(0, 0, 65, 450);
        this.rd.fillRect(735, 0, 65, 450);
        this.rd.fillRect(65, 0, 670, 25);
        this.rd.fillRect(65, 425, 670, 25);
    }

    public void mainbg(int n) {
        int n2;
        int n3;
        int n4;
        int n5 = 2;
        this.rd.setColor(new Color(191, 184, 124));
        if (n == -1) {
            if (n != this.lmode) {
                this.bgmy[0] = 0;
                this.bgmy[1] = -400;
                this.bgup = false;
                this.bgf = 0.0f;
                this.lmode = n;
            }
            this.rd.setColor(new Color(144, 222, 9));
            n5 = 8;
        }
        if (n == 0) {
            if (n != this.lmode) {
                this.bgmy[0] = 0;
                this.bgmy[1] = -400;
                this.bgup = false;
                this.bgf = 0.0f;
                this.lmode = n;
            }
            n4 = (int)(255.0f * this.bgf + 191.0f * (1.0f - this.bgf));
            n3 = (int)(176.0f * this.bgf + 184.0f * (1.0f - this.bgf));
            n2 = (int)(67.0f * this.bgf + 124.0f * (1.0f - this.bgf));
            if (!this.bgup) {
                this.bgf += 0.02f;
                if (this.bgf > 0.9f) {
                    this.bgf = 0.9f;
                    this.bgup = true;
                }
            } else {
                this.bgf -= 0.02f;
                if (this.bgf < 0.2f) {
                    this.bgf = 0.2f;
                    this.bgup = false;
                }
            }
            this.rd.setColor(new Color(n4, n3, n2));
            n5 = 4;
        }
        if (n == 1) {
            if (n != this.lmode) {
                this.bgmy[0] = 0;
                this.bgmy[1] = -400;
                this.lmode = n;
            }
            this.rd.setColor(new Color(255, 176, 67));
            n5 = 8;
        }
        if (n == 2) {
            if (n != this.lmode) {
                this.bgmy[0] = 0;
                this.bgmy[1] = -400;
                this.lmode = n;
                this.bgf = 0.2f;
            }
            this.rd.setColor(new Color(188, 170, 122));
            if (this.flipo == 16) {
                n4 = (int)(176.0f * this.bgf + 191.0f * (1.0f - this.bgf));
                n3 = (int)(202.0f * this.bgf + 184.0f * (1.0f - this.bgf));
                n2 = (int)(255.0f * this.bgf + 124.0f * (1.0f - this.bgf));
                this.rd.setColor(new Color(n4, n3, n2));
                this.bgf += 0.025f;
                if (this.bgf > 0.85f) {
                    this.bgf = 0.85f;
                }
            } else {
                this.bgf = 0.2f;
            }
            n5 = 2;
        }
        if (n == 3) {
            if (n != this.lmode) {
                this.bgmy[0] = 0;
                this.bgmy[1] = -400;
                this.bgup = false;
                this.bgf = 0.0f;
                this.lmode = n;
            }
            n4 = (int)(255.0f * this.bgf + 191.0f * (1.0f - this.bgf));
            n3 = (int)(176.0f * this.bgf + 184.0f * (1.0f - this.bgf));
            n2 = (int)(67.0f * this.bgf + 124.0f * (1.0f - this.bgf));
            if (!this.bgup) {
                this.bgf += 0.02f;
                if (this.bgf > 0.9f) {
                    this.bgf = 0.9f;
                    this.bgup = true;
                }
            } else {
                this.bgf -= 0.02f;
                if (this.bgf < 0.2f) {
                    this.bgf = 0.2f;
                    this.bgup = false;
                }
            }
            this.rd.setColor(new Color(n4, n3, n2));
            n5 = 2;
        }
        if (n != -101) {
            if (n == 4) {
                this.rd.setColor(new Color(216, 177, 100));
                this.rd.fillRect(65, 0, 670, 425);
            } else {
                this.rd.fillRect(65, 25, 670, 400);
            }
        }
        if (n == 4) {
            if (n != this.lmode) {
                this.bgmy[0] = 0;
                this.bgmy[1] = 400;
                for (n4 = 0; n4 < 4; ++n4) {
                    this.ovw[n4] = (int)(50.0 + 150.0 * Math.random());
                    this.ovh[n4] = (int)(50.0 + 150.0 * Math.random());
                    this.ovy[n4] = (int)(400.0 * Math.random());
                    this.ovx[n4] = (int)(Math.random() * 670.0);
                    this.ovsx[n4] = (int)(5.0 + Math.random() * 10.0);
                }
                this.lmode = n;
            }
            for (n4 = 0; n4 < 4; ++n4) {
                this.rd.setColor(new Color(235, 176, 84));
                this.rd.fillOval((int)((double)(65 + this.ovx[n4]) - (double)this.ovw[n4] * 1.5 / 2.0), (int)((double)(25 + this.ovy[n4]) - (double)this.ovh[n4] * 1.5 / 2.0), (int)((double)this.ovw[n4] * 1.5), (int)((double)this.ovh[n4] * 1.5));
                this.rd.setColor(new Color(255, 176, 67));
                this.rd.fillOval(65 + this.ovx[n4] - this.ovh[n4] / 2, 25 + this.ovy[n4] - this.ovh[n4] / 2, this.ovw[n4], this.ovh[n4]);
                int n6 = n4;
                this.ovx[n6] = this.ovx[n6] - this.ovsx[n4];
                if (!((double)this.ovx[n4] + (double)this.ovw[n4] * 1.5 / 2.0 < 0.0)) continue;
                this.ovw[n4] = (int)(50.0 + 150.0 * Math.random());
                this.ovh[n4] = (int)(50.0 + 150.0 * Math.random());
                this.ovy[n4] = (int)(400.0 * Math.random());
                this.ovx[n4] = (int)(670.0 + (double)this.ovw[n4] * 1.5 / 2.0);
                this.ovsx[n4] = (int)(5.0 + Math.random() * 10.0);
            }
        }
        if (n != -101 && n != 4) {
            for (n4 = 0; n4 < 2; ++n4) {
                if (n != 2 || this.flipo != 16) {
                    this.rd.drawImage(this.bgmain, 65, 25 + this.bgmy[n4], null);
                }
                int n7 = n4;
                this.bgmy[n7] = this.bgmy[n7] + n5;
                if (this.bgmy[n4] < 400) continue;
                this.bgmy[n4] = -400;
            }
        }
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.fillRect(0, 0, 65, 450);
        this.rd.fillRect(735, 0, 65, 450);
        if (n != 4) {
            this.rd.fillRect(65, 0, 670, 25);
        }
        this.rd.fillRect(65, 425, 670, 25);
    }

    public void inishstageselect(CheckPoints checkPoints) {
        if (!(checkPoints.stage != -2 || this.cd.msloaded == 1 && this.logged)) {
            checkPoints.stage = (int)(Math.random() * 27.0) + 1;
            checkPoints.top20 = 0;
        }
        if (checkPoints.stage > 27) {
            checkPoints.stage = (int)(Math.random() * 27.0) + 1;
        }
        if (checkPoints.stage == -2) {
            boolean bl = false;
            for (int i = 1; i < this.app.mstgs.getItemCount(); ++i) {
                if (!this.app.mstgs.getItem(i).equals(checkPoints.name)) continue;
                bl = true;
            }
            if (!bl) {
                checkPoints.stage = (int)(Math.random() * 27.0) + 1;
            }
        }
        if (this.gmode == 1) {
            if (this.unlocked[0] != 11 || this.justwon1) {
                checkPoints.stage = this.unlocked[0];
            } else if (this.winner || checkPoints.stage > 11) {
                checkPoints.stage = (int)(Math.random() * 11.0) + 1;
            }
            if (checkPoints.stage == 11) {
                checkPoints.stage = 27;
            }
        }
        if (this.gmode == 2) {
            if (this.unlocked[0] != 17 || this.justwon2) {
                checkPoints.stage = this.unlocked[1] + 10;
            } else if (this.winner || checkPoints.stage < 11) {
                checkPoints.stage = (int)(Math.random() * 17.0) + 11;
            }
        }
        this.app.sgame.setBackground(new Color(0, 0, 0));
        this.app.sgame.setForeground(new Color(47, 179, 255));
        this.app.snfm1.setBackground(new Color(0, 0, 0));
        this.app.snfm1.setForeground(new Color(47, 179, 255));
        this.app.snfm2.setBackground(new Color(0, 0, 0));
        this.app.snfm2.setForeground(new Color(47, 179, 255));
        this.app.mstgs.setBackground(new Color(0, 0, 0));
        this.app.mstgs.setForeground(new Color(47, 179, 255));
        this.app.gmode.setBackground(new Color(49, 49, 0));
        this.app.gmode.setForeground(new Color(148, 167, 0));
        this.app.sgame.removeAll();
        this.app.sgame.add(this.rd, " NFM 1     ");
        this.app.sgame.add(this.rd, " NFM 2     ");
        this.app.sgame.add(this.rd, " My Stages ");
        this.app.sgame.add(this.rd, " Weekly Top20 ");
        this.app.sgame.add(this.rd, " Monthly Top20 ");
        this.app.sgame.add(this.rd, " Stage Maker ");
        if (checkPoints.stage > 0 && checkPoints.stage <= 10) {
            this.app.sgame.select(0);
            this.nfmtab = 0;
        }
        if (checkPoints.stage > 10) {
            this.app.sgame.select(1);
            this.nfmtab = 1;
        }
        if (checkPoints.stage == -2) {
            this.app.sgame.select(2);
            this.nfmtab = 2;
        }
        if (checkPoints.stage == -1) {
            this.app.sgame.select(5);
            this.nfmtab = 5;
        }
        this.removeds = 0;
        this.lfrom = 0;
        this.cd.staction = 0;
        this.fase = 2;
    }

    public void loadingstage(int n, boolean bl) {
        this.trackbg(true);
        this.rd.drawImage(this.br, 65, 25, null);
        this.rd.setColor(new Color(212, 214, 138));
        this.rd.fillRoundRect(265, 201, 270, 26, 20, 40);
        this.rd.setColor(new Color(57, 64, 8));
        this.rd.drawRoundRect(265, 201, 270, 26, 20, 40);
        this.rd.setFont(new Font("Arial", 1, 12));
        this.ftm = this.rd.getFontMetrics();
        this.drawcs(219, "Loading, please wait...", 58, 61, 17, 3);
        if (bl) {
            this.rd.drawImage(this.select, 338, 35, null);
        }
        this.app.repaint();
        if (this.cd.staction != 0) {
            this.app.tnick.hide();
            this.app.tpass.hide();
            this.cd.staction = 0;
        }
        this.removeds = 0;
    }

    public void cantgo(Control control) {
        this.pnext = 0;
        this.trackbg(false);
        this.rd.drawImage(this.br, 65, 25, null);
        this.rd.drawImage(this.select, 338, 35, null);
        this.rd.setFont(new Font("Arial", 1, 13));
        this.ftm = this.rd.getFontMetrics();
        this.drawcs(130, "This stage will be unlocked when stage " + this.unlocked[this.gmode - 1] + " is complete!", 177, 177, 177, 3);
        for (int i = 0; i < 9; ++i) {
            this.rd.drawImage(this.pgate, 277 + i * 30, 215, null);
        }
        this.rd.setFont(new Font("Arial", 1, 12));
        this.ftm = this.rd.getFontMetrics();
        if (this.aflk) {
            this.drawcs(185, "[ Stage " + (this.unlocked[this.gmode - 1] + 1) + " Locked ]", 255, 128, 0, 3);
            this.aflk = false;
        } else {
            this.drawcs(185, "[ Stage " + (this.unlocked[this.gmode - 1] + 1) + " Locked ]", 255, 0, 0, 3);
            this.aflk = true;
        }
        this.rd.drawImage(this.back[this.pback], 370, 345, null);
        --this.lockcnt;
        if (this.lockcnt == 0 || control.enter || control.handb || control.left) {
            control.left = false;
            control.handb = false;
            control.enter = false;
            this.fase = 1;
        }
    }

    public void stageselect(CheckPoints checkPoints, Control control, int n, int n2, boolean bl) {
        this.rd.drawImage(this.br, 65, 25, null);
        this.rd.drawImage(this.select, 338, 35, null);
        if (this.testdrive != 3 && this.testdrive != 4) {
            if (checkPoints.stage > 0 && this.cd.staction == 0) {
                if (checkPoints.stage != 1 && (checkPoints.stage != 11 || this.gmode != 2)) {
                    this.rd.drawImage(this.back[this.pback], 115, 135, null);
                }
                if (checkPoints.stage != 27) {
                    this.rd.drawImage(this.next[this.pnext], 625, 135, null);
                }
            }
            if (this.gmode == 0) {
                String string;
                boolean bl2 = false;
                int n3 = 0;
                if (this.nfmtab != this.app.sgame.getSelectedIndex()) {
                    this.nfmtab = this.app.sgame.getSelectedIndex();
                    this.app.snfm1.select(0);
                    this.app.snfm2.select(0);
                    this.app.mstgs.select(0);
                    this.app.requestFocus();
                    bl2 = true;
                }
                if (this.cd.staction == 5) {
                    if (this.lfrom == 0) {
                        this.cd.staction = 0;
                        this.removeds = 1;
                        bl2 = true;
                    } else {
                        this.cd.onstage = checkPoints.name;
                        this.cd.staction = 2;
                        this.dnload = 2;
                    }
                    this.backlog = this.nickname = this.app.tnick.getText();
                    this.nickey = this.cd.tnickey;
                    this.clan = this.cd.tclan;
                    this.clankey = this.cd.tclankey;
                    this.app.setloggedcookie();
                    this.logged = true;
                    this.gotlog = true;
                    if (this.cd.reco == 0) {
                        this.acexp = 0;
                    }
                    if (this.cd.reco > 10) {
                        this.acexp = this.cd.reco - 10;
                    }
                    if (this.cd.reco == 3) {
                        this.acexp = -1;
                    }
                    if (this.cd.reco == 111) {
                        this.acexp = !this.backlog.toLowerCase().equals(this.nickname.toLowerCase()) ? -3 : 0;
                    }
                }
                if (this.nfmtab == 2 && this.cd.staction == 0 && this.removeds == 1) {
                    checkPoints.stage = -3;
                }
                if (this.app.openm && this.cd.staction == 3) {
                    this.app.tnick.hide();
                    this.app.tpass.hide();
                    this.cd.staction = 0;
                }
                int n4 = 0;
                this.app.sgame.setSize(131, 22);
                if (this.app.sgame.getSelectedIndex() == 0) {
                    n4 = 400 - (this.app.sgame.getWidth() + 6 + this.app.snfm1.getWidth()) / 2;
                }
                if (this.app.sgame.getSelectedIndex() == 1) {
                    n4 = 400 - (this.app.sgame.getWidth() + 6 + this.app.snfm2.getWidth()) / 2;
                }
                if (this.app.sgame.getSelectedIndex() == 2) {
                    this.app.mstgs.setSize(338, 22);
                    if (bl2) {
                        if (this.logged) {
                            if (this.cd.msloaded != 1) {
                                this.app.mstgs.removeAll();
                                this.app.mstgs.add(this.rd, "Loading your stages now, please wait...");
                                this.app.mstgs.select(0);
                                n3 = 1;
                            }
                        } else {
                            this.app.mstgs.removeAll();
                            this.app.mstgs.add(this.rd, "Please login first to load your stages...");
                            this.app.mstgs.select(0);
                            this.cd.msloaded = 0;
                            this.lfrom = 0;
                            this.cd.staction = 3;
                            this.showtf = false;
                            this.tcnt = 0;
                            this.cntflock = 0;
                            this.cd.reco = -2;
                        }
                    }
                    n4 = 400 - (this.app.sgame.getWidth() + 6 + this.app.mstgs.getWidth()) / 2;
                }
                if (this.app.sgame.getSelectedIndex() == 3) {
                    this.app.mstgs.setSize(338, 22);
                    if (bl2 && this.cd.msloaded != 3) {
                        this.app.mstgs.removeAll();
                        this.app.mstgs.add(this.rd, "Loading Top20 list, please wait...");
                        this.app.mstgs.select(0);
                        n3 = 3;
                    }
                    n4 = 400 - (this.app.sgame.getWidth() + 6 + this.app.mstgs.getWidth()) / 2;
                }
                if (this.app.sgame.getSelectedIndex() == 4) {
                    this.app.mstgs.setSize(338, 22);
                    if (bl2 && this.cd.msloaded != 4) {
                        this.app.mstgs.removeAll();
                        this.app.mstgs.add(this.rd, "Loading Top20 list, please wait...");
                        this.app.mstgs.select(0);
                        n3 = 4;
                    }
                    n4 = 400 - (this.app.sgame.getWidth() + 6 + this.app.mstgs.getWidth()) / 2;
                }
                if (this.app.sgame.getSelectedIndex() == 5) {
                    if (this.cd.staction != 0) {
                        this.app.tnick.hide();
                        this.app.tpass.hide();
                        this.cd.staction = 0;
                    }
                    this.app.mstgs.setSize(338, 22);
                    if (bl2 && this.cd.msloaded != 2) {
                        this.app.mstgs.removeAll();
                        this.app.mstgs.add(this.rd, "Loading Stage Maker stages, please wait...");
                        this.app.mstgs.select(0);
                        n3 = 2;
                    }
                    n4 = 400 - (this.app.sgame.getWidth() + 6 + this.app.mstgs.getWidth()) / 2;
                }
                if (!this.app.sgame.isShowing()) {
                    this.app.sgame.show();
                }
                this.app.sgame.move(n4, 62);
                n4 += this.app.sgame.getWidth() + 6;
                if (this.nfmtab == 0) {
                    if (!this.app.snfm1.isShowing()) {
                        this.app.snfm1.show();
                        if (!bl2 && checkPoints.stage > 0) {
                            this.app.snfm1.select(checkPoints.stage);
                        }
                    }
                    this.app.snfm1.move(n4, 62);
                    if (this.app.snfm2.isShowing()) {
                        this.app.snfm2.hide();
                    }
                    if (this.app.mstgs.isShowing()) {
                        this.app.mstgs.hide();
                    }
                }
                if (this.nfmtab == 1) {
                    if (!this.app.snfm2.isShowing()) {
                        this.app.snfm2.show();
                        if (!bl2 && checkPoints.stage > 10) {
                            this.app.snfm2.select(checkPoints.stage - 10);
                        }
                    }
                    this.app.snfm2.move(n4, 62);
                    if (this.app.snfm1.isShowing()) {
                        this.app.snfm1.hide();
                    }
                    if (this.app.mstgs.isShowing()) {
                        this.app.mstgs.hide();
                    }
                }
                if (this.nfmtab == 2 || this.nfmtab == 3 || this.nfmtab == 4 || this.nfmtab == 5) {
                    if (!this.app.mstgs.isShowing()) {
                        this.app.mstgs.show();
                        if (!bl2) {
                            this.app.mstgs.select(checkPoints.name);
                        }
                    }
                    this.app.mstgs.move(n4, 62);
                    if (this.app.snfm1.isShowing()) {
                        this.app.snfm1.hide();
                    }
                    if (this.app.snfm2.isShowing()) {
                        this.app.snfm2.hide();
                    }
                }
                this.rd.setFont(new Font("Arial", 1, 13));
                this.ftm = this.rd.getFontMetrics();
                if (this.cd.staction == 0 || this.cd.staction == 6) {
                    if (checkPoints.stage != -3) {
                        string = "";
                        if (checkPoints.top20 >= 3) {
                            string = "N#" + checkPoints.nto + "  ";
                        }
                        if (this.aflk) {
                            this.drawcs(132, string + checkPoints.name, 240, 240, 240, 3);
                            this.aflk = false;
                        } else {
                            this.drawcs(132, string + checkPoints.name, 176, 176, 176, 3);
                            this.aflk = true;
                        }
                        if (checkPoints.stage == -2 && this.cd.staction == 0) {
                            this.rd.setFont(new Font("Arial", 1, 11));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(new Color(255, 176, 85));
                            if (checkPoints.maker.equals(this.nickname)) {
                                this.rd.drawString("Created by You", 70, 115);
                            } else {
                                this.rd.drawString("Created by :  " + checkPoints.maker + "", 70, 115);
                            }
                            if (checkPoints.top20 >= 3) {
                                this.rd.drawString("Added by :  " + this.cd.top20adds[checkPoints.nto - 1] + " Players", 70, 135);
                            }
                        }
                    } else if (this.removeds != 1) {
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.drawcs(132, "Failed to load stage...", 255, 138, 0, 3);
                        this.rd.setFont(new Font("Arial", 1, 11));
                        this.ftm = this.rd.getFontMetrics();
                        if (this.nfmtab == 5) {
                            this.drawcs(155, "Please Test Drive this stage in the Stage Maker to make sure it can be loaded!", 255, 138, 0, 3);
                        }
                        if (this.nfmtab == 2 || this.nfmtab == 3 || this.nfmtab == 4) {
                            this.drawcs(155, "It could be a connection error, please try again later.", 255, 138, 0, 3);
                        }
                        if (this.nfmtab == 1 || this.nfmtab == 0) {
                            this.drawcs(155, "Will try to load another stage...", 255, 138, 0, 3);
                            this.app.repaint();
                            try {
                                Thread.sleep(5000L);
                            }
                            catch (InterruptedException interruptedException) {
                                // empty catch block
                            }
                            if (this.nfmtab == 0) {
                                this.app.snfm1.select(1 + (int)(Math.random() * 10.0));
                            }
                            if (this.nfmtab == 1) {
                                this.app.snfm2.select(1 + (int)(Math.random() * 17.0));
                            }
                        }
                    }
                }
                if (this.cd.staction == 3) {
                    this.drawdprom(145, 170);
                    if (this.cd.reco == -2) {
                        if (this.lfrom == 0) {
                            this.drawcs(171, "Login to Retrieve your Account Stages", 0, 0, 0, 3);
                        } else {
                            this.drawcs(171, "Login to add this stage to your account.", 0, 0, 0, 3);
                        }
                    }
                    if (this.cd.reco == -1) {
                        this.drawcs(171, "Unable to connect to server, try again later!", 0, 8, 0, 3);
                    }
                    if (this.cd.reco == 1) {
                        this.drawcs(171, "Sorry.  The Nickname you have entered is incorrect.", 0, 0, 0, 3);
                    }
                    if (this.cd.reco == 2) {
                        this.drawcs(171, "Sorry.  The Password you have entered is incorrect.", 0, 0, 0, 3);
                    }
                    if (this.cd.reco == -167 || this.cd.reco == -177) {
                        if (this.cd.reco == -167) {
                            this.backlog = this.nickname = this.app.tnick.getText();
                            this.cd.reco = -177;
                        }
                        this.drawcs(171, "You are currently using a trial account.", 0, 0, 0, 3);
                    }
                    if (this.cd.reco == -3 && (this.tcnt % 3 != 0 || this.tcnt > 20)) {
                        this.drawcs(171, "Please enter your Nickname!", 0, 0, 0, 3);
                    }
                    if (this.cd.reco == -4 && (this.tcnt % 3 != 0 || this.tcnt > 20)) {
                        this.drawcs(171, "Please enter your Password!", 0, 0, 0, 3);
                    }
                    if (!this.showtf) {
                        this.app.tnick.setBackground(new Color(206, 237, 255));
                        if (this.cd.reco != 1) {
                            if (this.cd.reco != 2) {
                                this.app.tnick.setText(this.nickname);
                            }
                            this.app.tnick.setForeground(new Color(0, 0, 0));
                        } else {
                            this.app.tnick.setForeground(new Color(255, 0, 0));
                        }
                        this.app.tnick.requestFocus();
                        this.app.tpass.setBackground(new Color(206, 237, 255));
                        if (this.cd.reco != 2) {
                            if (!this.autolog) {
                                this.app.tpass.setText("");
                            }
                            this.app.tpass.setForeground(new Color(0, 0, 0));
                        } else {
                            this.app.tpass.setForeground(new Color(255, 0, 0));
                        }
                        if (!this.app.tnick.getText().equals("") && this.cd.reco != 1) {
                            this.app.tpass.requestFocus();
                        }
                        this.showtf = true;
                    }
                    this.rd.drawString("Nickname:", 376 - this.ftm.stringWidth("Nickname:") - 14, 201);
                    this.rd.drawString("Password:", 376 - this.ftm.stringWidth("Password:") - 14, 231);
                    this.app.movefieldd(this.app.tnick, 376, 185, 129, 23, true);
                    this.app.movefieldd(this.app.tpass, 376, 215, 129, 23, true);
                    if (this.tcnt < 30) {
                        ++this.tcnt;
                        if (this.tcnt == 30) {
                            if (this.cd.reco == 2) {
                                this.app.tpass.setText("");
                            }
                            this.app.tnick.setForeground(new Color(0, 0, 0));
                            this.app.tpass.setForeground(new Color(0, 0, 0));
                        }
                    }
                    if (this.cd.reco != -177) {
                        if ((this.drawcarb(true, null, "       Login       ", 347, 247, n, n2, bl) || control.handb || control.enter) && this.tcnt > 5) {
                            this.tcnt = 0;
                            if (!this.app.tnick.getText().equals("") && !this.app.tpass.getText().equals("")) {
                                this.autolog = false;
                                this.app.tnick.hide();
                                this.app.tpass.hide();
                                this.app.requestFocus();
                                this.cd.staction = 4;
                                this.cd.sparkstageaction();
                            } else {
                                if (this.app.tpass.getText().equals("")) {
                                    this.cd.reco = -4;
                                }
                                if (this.app.tnick.getText().equals("")) {
                                    this.cd.reco = -3;
                                }
                            }
                        }
                    } else if (this.drawcarb(true, null, "  Upgrade to have your own stages!  ", 277, 247, n, n2, bl) && this.cntflock == 0) {
                        this.app.editlink(this.nickname, true);
                        this.cntflock = 100;
                    }
                    if (this.drawcarb(true, null, "  Cancel  ", 409, 282, n, n2, bl)) {
                        this.app.tnick.hide();
                        this.app.tpass.hide();
                        this.app.requestFocus();
                        this.cd.staction = 0;
                    }
                    if (this.drawcarb(true, null, "  Register!  ", 316, 282, n, n2, bl)) {
                        if (this.cntflock == 0) {
                            this.app.reglink();
                            this.cntflock = 100;
                        }
                    } else if (this.cntflock != 0) {
                        --this.cntflock;
                    }
                }
                if (this.cd.staction == 4) {
                    this.drawdprom(145, 170);
                    this.drawcs(195, "Logging in to your account...", 0, 0, 0, 3);
                }
                if (checkPoints.stage == -2 && this.cd.msloaded == 1 && checkPoints.top20 < 3 && !this.app.openm && this.drawcarb(true, null, "X", 609, 113, n, n2, bl)) {
                    this.cd.staction = 6;
                }
                if (this.cd.staction == -1 && checkPoints.top20 < 3) {
                    this.removeds = 0;
                    this.drawdprom(145, 95);
                    this.drawcs(175, "Failed to remove stage from your account, try again later.", 0, 0, 0, 3);
                    if (this.drawcarb(true, null, " OK ", 379, 195, n, n2, bl)) {
                        this.cd.staction = 0;
                    }
                }
                if (this.cd.staction == 1) {
                    this.drawdprom(145, 95);
                    this.drawcs(195, "Removing stage from your account...", 0, 0, 0, 3);
                    this.removeds = 1;
                }
                if (this.cd.staction == 6) {
                    this.drawdprom(145, 95);
                    this.drawcs(175, "Remove this stage from your account?", 0, 0, 0, 3);
                    if (this.drawcarb(true, null, " Yes ", 354, 195, n, n2, bl)) {
                        this.cd.onstage = this.app.mstgs.getSelectedItem();
                        this.cd.staction = 1;
                        this.cd.sparkstageaction();
                    }
                    if (this.drawcarb(true, null, " No ", 408, 195, n, n2, bl)) {
                        this.cd.staction = 0;
                    }
                }
                if (n3 == 1) {
                    this.app.drawms();
                    this.app.repaint();
                    this.cd.loadmystages(checkPoints);
                }
                if (n3 >= 3) {
                    this.app.drawms();
                    this.app.repaint();
                    this.cd.loadtop20(n3);
                }
                if (n3 == 2) {
                    this.app.drawms();
                    this.app.repaint();
                    this.cd.loadstagemaker();
                }
                if (checkPoints.stage != -3 && this.cd.staction == 0 && checkPoints.top20 < 3) {
                    this.rd.drawImage(this.contin[this.pcontin], 355, 360, null);
                } else {
                    this.pcontin = 0;
                }
                if (checkPoints.top20 >= 3 && this.cd.staction != 3 && this.cd.staction != 4) {
                    this.rd.setFont(new Font("Arial", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                    if (this.dnload == 0 && this.drawcarb(true, null, " Add to My Stages ", 334, 355, n, n2, bl)) {
                        if (this.logged) {
                            this.cd.onstage = checkPoints.name;
                            this.cd.staction = 2;
                            this.cd.sparkstageaction();
                            this.dnload = 2;
                        } else {
                            this.lfrom = 1;
                            this.cd.staction = 3;
                            this.showtf = false;
                            this.tcnt = 0;
                            this.cntflock = 0;
                            this.cd.reco = -2;
                        }
                    }
                    if (this.dnload == 2) {
                        this.drawcs(370, "Adding stage please wait...", 193, 106, 0, 3);
                        if (this.cd.staction == 0) {
                            this.dnload = 3;
                        }
                        if (this.cd.staction == -2) {
                            this.dnload = 4;
                        }
                        if (this.cd.staction == -3) {
                            this.dnload = 5;
                        }
                        if (this.cd.staction == -1) {
                            this.dnload = 6;
                        }
                        if (this.dnload != 2) {
                            this.cd.staction = 0;
                        }
                    }
                    if (this.dnload == 3) {
                        this.drawcs(370, "Stage has been successfully added to your stages!", 193, 106, 0, 3);
                    }
                    if (this.dnload == 4) {
                        this.drawcs(370, "You already have this stage!", 193, 106, 0, 3);
                    }
                    if (this.dnload == 5) {
                        this.drawcs(370, "Cannot add more then 20 stages to your account!", 193, 106, 0, 3);
                    }
                    if (this.dnload == 6) {
                        this.drawcs(370, "Failed to add stage, unknown error, please try again later.", 193, 106, 0, 3);
                    }
                }
                if (this.testdrive == 0 && checkPoints.top20 < 3) {
                    if (!this.app.gmode.isShowing()) {
                        this.app.gmode.select(0);
                        this.app.gmode.show();
                    }
                    this.app.gmode.move(400 - this.app.gmode.getWidth() / 2, 395);
                    if (this.app.gmode.getSelectedIndex() == 0 && this.nplayers != 7) {
                        this.nplayers = 7;
                        this.fase = 2;
                        this.app.requestFocus();
                    }
                    if (this.app.gmode.getSelectedIndex() == 1 && this.nplayers != 1) {
                        this.nplayers = 1;
                        this.fase = 2;
                        this.app.requestFocus();
                    }
                } else if (this.app.gmode.isShowing()) {
                    this.app.gmode.hide();
                }
                if (this.nfmtab == 0 && this.app.snfm1.getSelectedIndex() != checkPoints.stage && this.app.snfm1.getSelectedIndex() != 0) {
                    checkPoints.stage = this.app.snfm1.getSelectedIndex();
                    checkPoints.top20 = 0;
                    checkPoints.nto = 0;
                    this.hidos();
                    this.fase = 2;
                    this.app.requestFocus();
                }
                if (this.nfmtab == 1 && this.app.snfm2.getSelectedIndex() != checkPoints.stage - 10 && this.app.snfm2.getSelectedIndex() != 0) {
                    checkPoints.stage = this.app.snfm2.getSelectedIndex() + 10;
                    checkPoints.top20 = 0;
                    checkPoints.nto = 0;
                    this.hidos();
                    this.fase = 2;
                    this.app.requestFocus();
                }
                if (!(this.nfmtab != 2 && this.nfmtab != 5 || this.app.mstgs.getSelectedItem().equals(checkPoints.name) || this.app.mstgs.getSelectedIndex() == 0)) {
                    checkPoints.stage = this.nfmtab == 2 ? -2 : -1;
                    checkPoints.name = this.app.mstgs.getSelectedItem();
                    checkPoints.top20 = 0;
                    checkPoints.nto = 0;
                    this.hidos();
                    this.fase = 2;
                    this.app.requestFocus();
                }
                if (this.nfmtab == 3 || this.nfmtab == 4) {
                    string = "";
                    int n5 = this.app.mstgs.getSelectedItem().indexOf(" ") + 1;
                    if (n5 > 0) {
                        string = this.app.mstgs.getSelectedItem().substring(n5);
                    }
                    if (!string.equals("") && !string.equals(checkPoints.name) && this.app.mstgs.getSelectedIndex() != 0) {
                        checkPoints.stage = -2;
                        checkPoints.name = string;
                        checkPoints.top20 = -this.cd.msloaded;
                        checkPoints.nto = this.app.mstgs.getSelectedIndex();
                        this.hidos();
                        this.fase = 2;
                        this.app.requestFocus();
                    }
                }
            } else {
                this.rd.setFont(new Font("SansSerif", 1, 13));
                this.ftm = this.rd.getFontMetrics();
                if (checkPoints.stage != 27) {
                    int n6 = checkPoints.stage;
                    if (n6 > 10) {
                        n6 -= 10;
                    }
                    this.drawcs(80, "Stage " + n6 + "  >", 255, 128, 0, 3);
                } else {
                    this.drawcs(80, "Final Party Stage  >", 255, 128, 0, 3);
                }
                if (this.aflk) {
                    this.drawcs(100, "| " + checkPoints.name + " |", 240, 240, 240, 3);
                    this.aflk = false;
                } else {
                    this.drawcs(100, "| " + checkPoints.name + " |", 176, 176, 176, 3);
                    this.aflk = true;
                }
                if (checkPoints.stage != -3) {
                    this.rd.drawImage(this.contin[this.pcontin], 355, 360, null);
                } else {
                    this.pcontin = 0;
                }
            }
            if (this.cd.staction == 0) {
                if ((control.handb || control.enter) && checkPoints.stage != -3 && checkPoints.top20 < 3) {
                    this.app.gmode.hide();
                    this.hidos();
                    this.dudo = 150;
                    this.fase = 5;
                    control.handb = false;
                    control.enter = false;
                    this.intertrack.stop();
                    this.intertrack.unloadimod();
                }
                if (checkPoints.stage > 0) {
                    if (control.right) {
                        if (this.gmode == 0 || this.gmode == 1 && checkPoints.stage != this.unlocked[0] || this.gmode == 2 && checkPoints.stage != this.unlocked[1] + 10 || checkPoints.stage == 27) {
                            if (checkPoints.stage != 27) {
                                this.hidos();
                                ++checkPoints.stage;
                                if (this.gmode == 1 && checkPoints.stage == 11) {
                                    checkPoints.stage = 27;
                                }
                                if (checkPoints.stage > 10) {
                                    this.app.sgame.select(1);
                                    this.nfmtab = 1;
                                } else {
                                    this.app.sgame.select(0);
                                    this.nfmtab = 0;
                                }
                                this.fase = 2;
                            }
                        } else {
                            this.fase = 4;
                            this.lockcnt = 100;
                        }
                        control.right = false;
                    }
                    if (control.left && checkPoints.stage != 1 && (checkPoints.stage != 11 || this.gmode != 2)) {
                        this.hidos();
                        --checkPoints.stage;
                        if (this.gmode == 1 && checkPoints.stage == 26) {
                            checkPoints.stage = 10;
                        }
                        if (checkPoints.stage > 10) {
                            this.app.sgame.select(1);
                            this.nfmtab = 1;
                        } else {
                            this.app.sgame.select(0);
                            this.nfmtab = 0;
                        }
                        this.fase = 2;
                        control.left = false;
                    }
                }
            }
        } else {
            if (this.aflk) {
                this.drawcs(132, checkPoints.name, 240, 240, 240, 3);
                this.aflk = false;
            } else {
                this.drawcs(132, checkPoints.name, 176, 176, 176, 3);
                this.aflk = true;
            }
            this.rd.drawImage(this.contin[this.pcontin], 355, 360, null);
            if (control.handb || control.enter) {
                this.dudo = 150;
                this.fase = 5;
                control.handb = false;
                control.enter = false;
                this.intertrack.stop();
                this.intertrack.unloadimod();
            }
        }
        if (this.drawcarb(true, null, " Exit X ", 670, 30, n, n2, bl)) {
            this.fase = 102;
            if (this.gmode == 0) {
                this.opselect = 3;
            }
            if (this.gmode == 1) {
                this.opselect = 0;
            }
            if (this.gmode == 2) {
                this.opselect = 1;
            }
            this.app.gmode.hide();
            this.hidos();
            this.app.tnick.hide();
            this.app.tpass.hide();
            this.intertrack.stop();
        }
    }

    public void hidos() {
        this.app.sgame.hide();
        this.app.snfm1.hide();
        this.app.snfm2.hide();
        this.app.mstgs.hide();
    }

    public void hipnoload(int n, boolean bl) {
        int n2;
        int n3;
        int n4;
        int[] nArray = new int[]{this.m.snap[0], this.m.snap[1], this.m.snap[2]};
        while (nArray[0] + nArray[1] + nArray[2] < -30) {
            for (n4 = 0; n4 < 3; ++n4) {
                if (nArray[n4] >= 50) continue;
                int n5 = n4;
                nArray[n5] = nArray[n5] + 1;
            }
        }
        n4 = (int)(230.0f - 230.0f * ((float)nArray[0] / 100.0f));
        if (n4 > 255) {
            n4 = 255;
        }
        if (n4 < 0) {
            n4 = 0;
        }
        if ((n3 = (int)(230.0f - 230.0f * ((float)nArray[1] / 100.0f))) > 255) {
            n3 = 255;
        }
        if (n3 < 0) {
            n3 = 0;
        }
        if ((n2 = (int)(230.0f - 230.0f * ((float)nArray[2] / 100.0f))) > 255) {
            n2 = 255;
        }
        if (n2 < 0) {
            n2 = 0;
        }
        this.rd.setColor(new Color(n4, n3, n2));
        this.rd.fillRect(65, 25, 670, 400);
        this.rd.setComposite(AlphaComposite.getInstance(3, 0.3f));
        this.rd.drawImage(this.bggo, 0, -25, null);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.fillRect(0, 0, 65, 450);
        this.rd.fillRect(735, 0, 65, 450);
        this.rd.fillRect(65, 0, 670, 25);
        this.rd.fillRect(65, 425, 670, 25);
        this.rd.setFont(new Font("Arial", 1, 13));
        this.ftm = this.rd.getFontMetrics();
        this.drawcs(50, this.asay, 0, 0, 0, 3);
        int n6 = -90;
        if (this.multion == 0) {
            if (n == 1 || n == 2 || n == 3 || n == 4 || n == 5 || n == 10) {
                n6 = 0;
            }
            if (n == 11 || n == 12 || n == 13 || n == 14 || n == 17 || n == 18 || n == 19 || n == 20 || n == 22 || n == 23 || n == 26) {
                n6 = 0;
            }
            if (n < 0 && this.nplayers != 1 && this.newparts) {
                n6 = 0;
            }
        } else if (this.ransay == 1 || this.ransay == 2 || this.ransay == 3 || this.ransay == 4 || n == 10) {
            n6 = 0;
        }
        if (n6 == 0) {
            if (this.dudo > 0) {
                if (this.aflk) {
                    this.duds = Math.random() > Math.random() ? (int)(Math.random() * 3.0) : (int)(Math.random() * 2.0);
                    this.aflk = false;
                } else {
                    this.aflk = true;
                }
                --this.dudo;
            } else {
                this.duds = 0;
            }
            this.rd.setComposite(AlphaComposite.getInstance(3, 0.3f));
            this.rd.drawImage(this.dude[this.duds], 95, 35, null);
            this.rd.setComposite(AlphaComposite.getInstance(3, 0.7f));
            this.rd.drawImage(this.flaot, 192, 67, null);
            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
            n4 = (int)(80.0f - 80.0f * ((float)nArray[0] / 100.0f));
            if (n4 > 255) {
                n4 = 255;
            }
            if (n4 < 0) {
                n4 = 0;
            }
            if ((n3 = (int)(80.0f - 80.0f * ((float)nArray[1] / 100.0f))) > 255) {
                n3 = 255;
            }
            if (n3 < 0) {
                n3 = 0;
            }
            if ((n2 = (int)(80.0f - 80.0f * ((float)nArray[2] / 100.0f))) > 255) {
                n2 = 255;
            }
            if (n2 < 0) {
                n2 = 0;
            }
            this.rd.setColor(new Color(n4, n3, n2));
            this.rd.setFont(new Font("Arial", 1, 13));
            if (this.multion != 0) {
                if (this.ransay == 1 && n != 10) {
                    this.rd.drawString("Multiplayer Tip:  Press [ C ] to access chat quickly during the game!", 262, 92);
                }
                if (this.ransay == 2 && n != 10) {
                    this.rd.drawString("Multiplayer Tip:  Press [ A ] to make Guidance Arrow point to cars and", 262, 92);
                    this.rd.drawString("click any of the players listed on the right to lock the Arrow on!", 262, 112);
                }
                if (this.ransay == 3 && n != 10) {
                    this.rd.drawString("Multiplayer Tip:  When wasting in multiplayer it's better to aim slightly", 262, 92);
                    this.rd.drawString("ahead of the other player's car to compensate for internet delay.", 262, 112);
                }
                if (this.ransay == 4) {
                    this.rd.drawString("When watching a game, click any player listed on the right of the", 262, 92);
                    this.rd.drawString("screen to follow and watch.", 262, 112);
                    this.rd.drawString("Press [ V ] to change the viewing mode!", 262, 132);
                }
                if (n == 10 && this.ransay != 4) {
                    if (this.tflk) {
                        this.rd.setColor(new Color(200, n3, n2));
                        this.tflk = false;
                    } else {
                        this.tflk = true;
                    }
                    this.rd.drawString("NOTE: Guidance Arrow and opponent status is disabled in this stage!", 262, 92);
                }
            } else {
                if (n < 0 && this.nplayers != 1 && this.newparts) {
                    this.rd.drawString("Please note, the computer car's AI has not yet been trained to handle", 262, 92);
                    this.rd.drawString("some of the new stage parts such as the 'Rollercoaster Road' and the", 262, 112);
                    this.rd.drawString("'Tunnel Side Ramp'.", 262, 132);
                    this.rd.drawString("(Those new parts where mostly designed for the multiplayer game.)", 262, 152);
                    this.rd.drawString("The AI will be trained and ready in the future releases of the game!", 262, 172);
                }
                if (n == 1 || n == 11) {
                    this.rd.drawString("Hey!  Don't forget, to complete a lap you must pass through", 262, 92);
                    this.rd.drawString("all checkpoints in the track!", 262, 112);
                }
                if (n == 2 || n == 12) {
                    this.rd.drawString("Remember, the more power you have the faster your car will be!", 262, 92);
                }
                if (n == 3) {
                    this.rd.drawString("> Hint: its easier to waste the other cars then to race in this stage!", 262, 92);
                    this.rd.drawString("Press [ A ] to make the guidance arrow point to cars instead of to", 262, 112);
                    this.rd.drawString("the track.", 262, 132);
                }
                if (n == 4) {
                    this.rd.drawString("Remember, the better the stunt you perform the more power you get!", 262, 92);
                }
                if (n == 5) {
                    this.rd.drawString("Remember, the more power you have the stronger your car is!", 262, 92);
                }
                if (n == 10) {
                    if (this.tflk) {
                        this.rd.setColor(new Color(200, n3, n2));
                        this.tflk = false;
                    } else {
                        this.tflk = true;
                    }
                    this.rd.drawString("NOTE: Guidance Arrow is disabled in this stage!", 262, 92);
                }
                if (n == 13) {
                    this.rd.drawString("Watch out!  Look out!  The policeman might be out to get you!", 262, 92);
                    this.rd.drawString("Don't upset him or you'll be arrested!", 262, 112);
                    this.rd.drawString("Better run, run, run.", 262, 152);
                }
                if (n == 14) {
                    this.rd.drawString("Don't waste your time.  Waste them instead!", 262, 92);
                    this.rd.drawString("Try a taste of sweet revenge here (if you can)!", 262, 112);
                    this.rd.drawString("Press [ A ] to make the guidance arrow point to cars instead of to", 262, 152);
                    this.rd.drawString("the track.", 262, 172);
                }
                if (n == 17) {
                    this.rd.drawString("Welcome to the realm of the king...", 262, 92);
                    this.rd.drawString("The key word here is 'POWER'.  The more you have of it the faster", 262, 132);
                    this.rd.drawString("and STRONGER you car will be!", 262, 152);
                }
                if (n == 18) {
                    this.rd.drawString("Watch out, EL KING is out to get you now!", 262, 92);
                    this.rd.drawString("He seems to be seeking revenge?", 262, 112);
                    this.rd.drawString("(To fly longer distances in the air try drifting your car on the ramp", 262, 152);
                    this.rd.drawString("before take off).", 262, 172);
                }
                if (n == 19) {
                    this.rd.drawString("It\u2019s good to be the king!", 262, 92);
                }
                if (n == 20) {
                    this.rd.drawString("Remember, forward loops give your car a push forwards in the air", 262, 92);
                    this.rd.drawString("and help in racing.", 262, 112);
                    this.rd.drawString("(You may need to do more forward loops here.  Also try keeping", 262, 152);
                    this.rd.drawString("your power at maximum at all times.  Try not to miss a ramp).", 262, 172);
                }
                if (n == 22) {
                    this.rd.drawString("Watch out!  Beware!  Take care!", 262, 92);
                    this.rd.drawString("MASHEEN is hiding out there some where, don't get mashed now!", 262, 112);
                }
                if (n == 23) {
                    this.rd.drawString("Anyone for a game of Digger?!", 262, 92);
                    this.rd.drawString("You can have fun using MASHEEN here!", 262, 112);
                }
                if (n == 26) {
                    this.rd.drawString("This is it!  This is the toughest stage in the game!", 262, 92);
                    this.rd.drawString("This track is actually a 4D object projected onto the 3D world.", 262, 132);
                    this.rd.drawString("It's been broken down, separated and, in many ways, it is also a", 262, 152);
                    this.rd.drawString("maze!  GOOD LUCK!", 262, 172);
                }
            }
        }
        this.rd.setComposite(AlphaComposite.getInstance(3, 0.8f));
        this.rd.drawImage(this.loadingmusic, 289, 205 + n6, null);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        this.rd.setFont(new Font("Arial", 1, 11));
        this.ftm = this.rd.getFontMetrics();
        int n7 = n - 1;
        if (n7 < 0) {
            n7 = 32;
        }
        if (!bl) {
            this.drawcs(340 + n6, "" + this.sndsize[n7] + " KB", 0, 0, 0, 3);
            this.drawcs(375 + n6, " Please Wait...", 0, 0, 0, 3);
        } else {
            this.drawcs(365 + n6, "Loading complete!  Press Start to begin...", 0, 0, 0, 3);
            this.rd.setComposite(AlphaComposite.getInstance(3, 0.5f));
            this.rd.drawImage(this.star[this.pstar], 359, 385 + n6, null);
            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
            if (this.pstar != 2) {
                this.pstar = this.pstar == 0 ? 1 : 0;
            }
            if (this.multion != 0) {
                this.drawcs(380 + n6, "" + this.forstart / 20, 0, 0, 0, 3);
            }
        }
    }

    public void loadmusic(int n, String string, int n2) {
        this.hipnoload(n, false);
        this.app.setCursor(new Cursor(3));
        this.app.repaint();
        boolean bl = false;
        if (this.multion == 0) {
            if (n == 1 || n == 2 || n == 3 || n == 4 || n == 5 || n == 10) {
                bl = true;
            }
            if (n == 11 || n == 12 || n == 13 || n == 14 || n == 17 || n == 18 || n == 19 || n == 20 || n == 22 || n == 23 || n == 26) {
                bl = true;
            }
            if (n < 0 && this.nplayers != 1 && this.newparts) {
                bl = true;
            }
        } else if (this.ransay == 1 || this.ransay == 2 || this.ransay == 3 || this.ransay == 4 || n == 10) {
            bl = true;
        }
        if (bl) {
            this.runtyp = n;
            this.runner = new Thread(this);
            this.runner.start();
        }
        this.loadstrack(n, string, n2);
        if (bl) {
            this.runner.stop();
            this.runner = null;
            this.runtyp = 0;
        }
        System.gc();
        if (this.multion == 0 && this.app.applejava) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        if (!this.lan) {
            this.strack.play();
        } else if (this.im != 0) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        this.app.setCursor(new Cursor(0));
        this.pcontin = 0;
        this.mutem = false;
        this.mutes = false;
        this.fase = 6;
    }

    public void loadstrack(int n, String string, int n2) {
        if (n == 1) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 240, 8400, 135, false, false);
        }
        if (n == 2) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 190, 9000, 145, false, false);
        }
        if (n == 3) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 170, 8500, 145, false, false);
        }
        if (n == 4) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 205, 7500, 125, false, false);
        }
        if (n == 5) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 170, 7900, 125, false, false);
        }
        if (n == 6) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 370, 7900, 125, false, false);
        }
        if (n == 7) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 205, 7500, 125, false, false);
        }
        if (n == 8) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 230, 7900, 125, false, false);
        }
        if (n == 9) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 180, 7900, 125, false, false);
        }
        if (n == 10) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 280, 8100, 145, false, false);
        }
        if (n == 11) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 120, 8000, 125, false, false);
        }
        if (n == 12) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 260, 7200, 125, false, false);
        }
        if (n == 13) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 270, 8000, 125, false, false);
        }
        if (n == 14) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 190, 8000, 125, false, false);
        }
        if (n == 15) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 162, 7800, 125, false, false);
        }
        if (n == 16) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 220, 7600, 125, false, false);
        }
        if (n == 17) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 300, 7500, 125, false, false);
        }
        if (n == 18) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 200, 7900, 125, false, false);
        }
        if (n == 19) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 200, 7900, 125, false, false);
        }
        if (n == 20) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 232, 7300, 125, false, false);
        }
        if (n == 21) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 370, 7900, 125, false, false);
        }
        if (n == 22) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 290, 7900, 125, false, false);
        }
        if (n == 23) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 222, 7600, 125, false, false);
        }
        if (n == 24) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 230, 8000, 125, false, false);
        }
        if (n == 25) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 220, 8000, 125, false, false);
        }
        if (n == 26) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 261, 8000, 125, false, false);
        }
        if (n == 27) {
            this.strack = this.gmode == 2 ? new RadicalMod("music/party.zip", 400, 7600, 125, false, false) : new RadicalMod("music/stage" + n + ".zip", 276, 8800, 145, false, false);
        }
        if (n == 28) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 182, 8000, 125, false, false);
        }
        if (n == 29) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 220, 8000, 125, false, false);
        }
        if (n == 30) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 200, 8000, 125, false, false);
        }
        if (n == 31) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 350, 7900, 125, false, false);
        }
        if (n == 32) {
            this.strack = new RadicalMod("music/stage" + n + ".zip", 310, 8000, 125, false, false);
        }
        if (n < 0) {
            this.strack = !string.equals("") ? (n != -2 ? new RadicalMod("mystages/mymusic/" + string + ".zip", n2, 8000, 125, false, false) : new RadicalMod(string, n2, 8000, 125, false, true)) : new RadicalMod();
        }
        this.loadedt = true;
    }

    public void musicomp(int n, Control control) {
        this.hipnoload(n, true);
        if (this.multion != 0) {
            --this.forstart;
            if (this.lan && this.im == 0) {
                this.forstart = 0;
            }
        }
        if (control.handb || control.enter || this.forstart == 0) {
            System.gc();
            this.m.trk = 0;
            this.m.crs = false;
            this.m.ih = 0;
            this.m.iw = 0;
            this.m.h = 450;
            this.m.w = 800;
            this.m.focus_point = 400;
            this.m.cx = 400;
            this.m.cy = 225;
            this.m.cz = 50;
            this.rd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            if (this.multion == 0) {
                this.fase = 0;
            } else {
                this.fase = 7001;
                this.forstart = 0;
                if (!this.lan) {
                    try {
                        this.socket = new Socket(this.server, this.servport);
                        this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                        this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                        this.runtyp = -101;
                        this.runner = new Thread(this);
                        this.runner.start();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
            this.dudo = Math.random() > Math.random() ? 250 : 428;
            control.handb = false;
            control.enter = false;
        }
    }

    public void waitenter() {
        if (this.forstart < 690) {
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            this.drawcs(70, "Waiting for all players to finish loading!", 0, 0, 0, 0);
            if (this.forstart <= 640) {
                this.drawcs(90, "" + (640 - this.forstart) / 32 + "", 0, 0, 0, 0);
            } else {
                this.drawcs(90, "Your connection to game may have been lost...", 0, 0, 0, 0);
            }
            this.rd.setFont(new Font("Arial", 1, 11));
            this.ftm = this.rd.getFontMetrics();
            if (this.tflk) {
                this.drawcs(125, "Get Ready!", 0, 0, 0, 0);
                this.tflk = false;
            } else {
                this.drawcs(125, "Get Ready!", 0, 128, 255, 0);
                this.tflk = true;
            }
        }
        ++this.forstart;
        if (this.forstart == 700) {
            this.fase = -2;
            this.winner = false;
        }
    }

    public void multistat(Control control, CheckPoints checkPoints, int n, int n2, boolean bl, UDPMistro uDPMistro) {
        int n3 = -1;
        if (this.fase != -2) {
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            float[] fArray;
            if (this.exitm != 0 && !this.holdit) {
                if (!this.lan || this.im != 0) {
                    if (bl) {
                        if (n > 357 && n < 396 && n2 > 162 && n2 < 179) {
                            this.exitm = 2;
                            if (this.multion == 1 && !this.lan && this.sendstat == 0) {
                                this.sendstat = 1;
                                if (this.runtyp != -101) {
                                    if (this.runner != null) {
                                        this.runner.stop();
                                    }
                                    this.runner = new Thread(this);
                                    this.runner.start();
                                }
                            }
                        } else {
                            this.exitm = 0;
                        }
                    }
                    fArray = new float[3];
                    Color.RGBtoHSB(this.m.cgrnd[0], this.m.cgrnd[1], this.m.cgrnd[2], fArray);
                    fArray[1] = (float)((double)fArray[1] - 0.15);
                    if (fArray[1] < 0.0f) {
                        fArray[1] = 0.0f;
                    }
                    fArray[2] = (float)((double)fArray[2] + 0.15);
                    if (fArray[2] > 1.0f) {
                        fArray[2] = 1.0f;
                    }
                    this.rd.setColor(Color.getHSBColor(fArray[0], fArray[1], fArray[2]));
                    this.rd.fillRect(357, 169, 39, 10);
                    this.rd.fillRect(403, 169, 39, 10);
                    fArray[1] = (float)((double)fArray[1] - 0.07);
                    if (fArray[1] < 0.0f) {
                        fArray[1] = 0.0f;
                    }
                    fArray[2] = (float)((double)fArray[2] + 0.07);
                    if (fArray[2] > 1.0f) {
                        fArray[2] = 1.0f;
                    }
                    this.rd.setColor(Color.getHSBColor(fArray[0], fArray[1], fArray[2]));
                    this.rd.fillRect(357, 162, 39, 7);
                    this.rd.fillRect(403, 162, 39, 7);
                    this.drawhi(this.exitgame, 116);
                    if (n > 357 && n < 396 && n2 > 162 && n2 < 179) {
                        this.rd.setColor(new Color(this.m.csky[0], this.m.csky[1], this.m.csky[2]));
                        this.rd.fillRect(357, 162, 39, 17);
                    }
                    if (n > 403 && n < 442 && n2 > 162 && n2 < 179) {
                        this.rd.setColor(new Color(this.m.csky[0], this.m.csky[1], this.m.csky[2]));
                        this.rd.fillRect(403, 162, 39, 17);
                    }
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Yes", 366, 175);
                    this.rd.drawString("No", 416, 175);
                    this.rd.setColor(new Color(this.m.csky[0] / 2, this.m.csky[1] / 2, this.m.csky[2] / 2));
                    this.rd.drawRect(403, 162, 39, 17);
                    this.rd.drawRect(357, 162, 39, 17);
                } else {
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.drawcs(125, "You cannot exit game.  Your computer is the LAN server!", 0, 0, 0, 0);
                    this.msgflk[0] = this.msgflk[0] + 1;
                    if (this.msgflk[0] == 67 || bl) {
                        this.msgflk[0] = 0;
                        this.exitm = 0;
                    }
                    this.rd.setFont(new Font("Arial", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                }
            } else if (this.exitm == 4) {
                if (bl) {
                    if (n > 357 && n < 396 && n2 > 362 && n2 < 379) {
                        this.alocked = -1;
                        this.lalocked = -1;
                        control.multion = this.multion = 2;
                        this.holdit = false;
                        this.exitm = 0;
                        control.chatup = 0;
                    }
                    if (!(this.lan && this.im == 0 || n <= 403 || n >= 442 || n2 <= 362 || n2 >= 379)) {
                        this.holdcnt = 600;
                        this.exitm = 0;
                        control.chatup = 0;
                    }
                }
                fArray = new float[3];
                Color.RGBtoHSB(this.m.cgrnd[0], this.m.cgrnd[1], this.m.cgrnd[2], fArray);
                fArray[1] = (float)((double)fArray[1] - 0.15);
                if (fArray[1] < 0.0f) {
                    fArray[1] = 0.0f;
                }
                fArray[2] = (float)((double)fArray[2] + 0.15);
                if (fArray[2] > 1.0f) {
                    fArray[2] = 1.0f;
                }
                this.rd.setColor(Color.getHSBColor(fArray[0], fArray[1], fArray[2]));
                this.rd.fillRect(357, 369, 39, 10);
                if (!this.lan || this.im != 0) {
                    this.rd.fillRect(403, 369, 39, 10);
                }
                fArray[1] = (float)((double)fArray[1] - 0.07);
                if (fArray[1] < 0.0f) {
                    fArray[1] = 0.0f;
                }
                fArray[2] = (float)((double)fArray[2] + 0.07);
                if (fArray[2] > 1.0f) {
                    fArray[2] = 1.0f;
                }
                this.rd.setColor(Color.getHSBColor(fArray[0], fArray[1], fArray[2]));
                this.rd.fillRect(357, 362, 39, 7);
                if (!this.lan || this.im != 0) {
                    this.rd.fillRect(403, 362, 39, 7);
                }
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.setFont(new Font("Arial", 1, 13));
                this.ftm = this.rd.getFontMetrics();
                if (this.lan && this.im == 0) {
                    this.drawcs(140, "( You cannot exit game.  Your computer is the LAN server... )", 0, 0, 0, 0);
                }
                this.rd.drawString("Continue watching this game?", 155, 375);
                if (n > 357 && n < 396 && n2 > 362 && n2 < 379) {
                    this.rd.setColor(new Color(this.m.csky[0], this.m.csky[1], this.m.csky[2]));
                    this.rd.fillRect(357, 362, 39, 17);
                }
                if (!(this.lan && this.im == 0 || n <= 403 || n >= 442 || n2 <= 362 || n2 >= 379)) {
                    this.rd.setColor(new Color(this.m.csky[0], this.m.csky[1], this.m.csky[2]));
                    this.rd.fillRect(403, 362, 39, 17);
                }
                this.rd.setFont(new Font("Arial", 1, 11));
                this.ftm = this.rd.getFontMetrics();
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawString("Yes", 366, 375);
                if (!this.lan || this.im != 0) {
                    this.rd.drawString("No", 416, 375);
                }
                this.rd.setColor(new Color(this.m.csky[0] / 2, this.m.csky[1] / 2, this.m.csky[2] / 2));
                if (!this.lan || this.im != 0) {
                    this.rd.drawRoundRect(147, 357, 301, 27, 7, 20);
                } else {
                    this.rd.drawRoundRect(147, 357, 262, 27, 7, 20);
                }
                this.rd.drawRect(357, 362, 39, 17);
                if (!this.lan || this.im != 0) {
                    this.rd.drawRect(403, 362, 39, 17);
                }
            }
            if (this.runtyp == -101 && !this.lan) {
                if (this.warning == 0 || this.warning == 210) {
                    int n10 = 0;
                    n9 = 0;
                    if (this.clanchat) {
                        n10 = 1;
                        n9 = -23;
                    } else if (control.chatup == 2) {
                        control.chatup = 1;
                    }
                    for (n8 = n10; n8 >= 0; --n8) {
                        Object object;
                        n7 = 0;
                        if (n > 5 && n < 33 && n2 > 423 + n9 && n2 < 446 + n9) {
                            n7 = 1;
                            if (control.chatup != 0) {
                                control.chatup = 0;
                            }
                        } else if (this.pointc[n8] != 6) {
                            this.pointc[n8] = 6;
                            this.floater[n8] = 1;
                        }
                        if (n > 33 && n < 666 && n2 > 423 + n9 && n2 < 446 + n9 && this.lxm != n && n2 != this.lym && this.lxm != -100) {
                            control.chatup = n8 + 1;
                            this.cntchatp[n8] = 0;
                        }
                        if (n8 == 0) {
                            this.lxm = n;
                            this.lym = n2;
                        }
                        if (this.exitm != 0 && this.exitm != 4) {
                            control.chatup = 0;
                        }
                        n6 = 0;
                        if (control.enter && control.chatup == n8 + 1) {
                            n6 = 1;
                            control.chatup = 0;
                            control.enter = false;
                            this.lxm = -100;
                        }
                        if (bl) {
                            if (this.mouson == 0) {
                                if (n > 676 && n < 785 && n2 > 426 + n9 && n2 < 443 + n9 && control.chatup == n8 + 1) {
                                    n6 = 1;
                                    control.chatup = 0;
                                }
                                if (n7 != 0 && this.pointc[n8] > 0) {
                                    int n11 = n8;
                                    this.pointc[n11] = this.pointc[n11] - 1;
                                    this.floater[n8] = 1;
                                }
                                if (n8 == 0) {
                                    this.mouson = 1;
                                }
                            }
                            if (n8 == 0) {
                                control.chatup = 0;
                            }
                        } else if (n8 == 0 && this.mouson != 0) {
                            this.mouson = 0;
                        }
                        if (n6 != 0) {
                            object = "";
                            n5 = 1;
                            for (n4 = 0; n4 < this.lcmsg[n8].length(); ++n4) {
                                String string = "" + this.lcmsg[n8].charAt(n4);
                                n5 = string.equals(" ") ? ++n5 : 0;
                                if (n5 >= 2) continue;
                                object = (String)object + string;
                            }
                            if (!((String)object).equals("")) {
                                if (((String)(object = ((String)object).replace('|', ':'))).toLowerCase().indexOf(this.app.tpass.getText().toLowerCase()) != -1) {
                                    object = " ";
                                }
                                if (!this.msgcheck((String)object) && this.updatec[n8] > -12) {
                                    if (this.cnames[n8][6].equals("Game Chat  ") || this.cnames[n8][6].equals("" + this.clan + "'s Chat  ")) {
                                        this.cnames[n8][6] = "";
                                    }
                                    for (int i = 0; i < 6; ++i) {
                                        this.sentn[n8][i] = this.sentn[n8][i + 1];
                                        this.cnames[n8][i] = this.cnames[n8][i + 1];
                                    }
                                    this.sentn[n8][6] = object;
                                    this.cnames[n8][6] = this.nickname;
                                    if (this.pointc[n8] != 6) {
                                        this.pointc[n8] = 6;
                                        this.floater[n8] = 1;
                                    }
                                    this.msgflk[n8] = 110;
                                    if (this.updatec[n8] > -11) {
                                        this.updatec[n8] = -11;
                                    } else {
                                        int n12 = n8;
                                        this.updatec[n12] = this.updatec[n12] - 1;
                                    }
                                } else {
                                    ++this.warning;
                                }
                            }
                        }
                        if (n7 != 0 || this.floater[n8] != 0 || control.chatup == n8 + 1 || this.msgflk[n8] != 0) {
                            object = new float[3];
                            Color.RGBtoHSB(this.m.cgrnd[0], this.m.cgrnd[1], this.m.cgrnd[2], (float[])object);
                            Object object2 = object;
                            object2[1] = (float)((double)object2[1] - 0.15);
                            if (object[1] < 0.0f) {
                                object[1] = 0.0f;
                            }
                            Object object3 = object;
                            object3[2] = (float)((double)object3[2] + 0.15);
                            if (object[2] > 1.0f) {
                                object[2] = 1.0f;
                            }
                            this.rd.setColor(Color.getHSBColor((float)object[0], (float)object[1], (float)object[2]));
                            this.rd.fillRect(33, 423 + n9, 761, 23);
                        }
                        if (control.chatup == 0 && this.app.cmsg.isShowing()) {
                            this.app.cmsg.hide();
                            this.app.requestFocus();
                        }
                        if (control.chatup != n8 + 1) {
                            int n13;
                            int n14 = 0;
                            n4 = (int)(48.0f + 48.0f * ((float)this.m.snap[1] / 100.0f));
                            if (n4 > 255) {
                                n4 = 255;
                            }
                            if (n4 < 0) {
                                n4 = 0;
                            }
                            if ((n5 = (int)(96.0f + 96.0f * ((float)this.m.snap[2] / 100.0f))) > 255) {
                                n5 = 255;
                            }
                            if (n5 < 0) {
                                n5 = 0;
                            }
                            if (this.floater[n8] != 0) {
                                for (n13 = 6; n13 >= 0; --n13) {
                                    if (this.pointc[n8] == n13) {
                                        if (Math.abs(n14 + this.movepos[n8]) > 10) {
                                            this.floater[n8] = (this.movepos[n8] + n14) / 4;
                                            if (this.floater[n8] > -5 && this.floater[n8] < 0) {
                                                this.floater[n8] = -5;
                                            }
                                            if (this.floater[n8] < 10 && this.floater[n8] > 0) {
                                                this.floater[n8] = 10;
                                            }
                                            int n15 = n8;
                                            this.movepos[n15] = this.movepos[n15] - this.floater[n8];
                                        } else {
                                            this.movepos[n8] = -n14;
                                            this.floater[n8] = 0;
                                        }
                                    }
                                    if (this.pointc[n8] >= n13) {
                                        this.rd.setColor(new Color(0, n4, n5));
                                        this.rd.setFont(new Font("Tahoma", 1, 11));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.drawString(this.cnames[n8][n13] + ": ", 39 + n14 + this.movepos[n8], 439 + n9);
                                        this.rd.setColor(new Color(0, 0, 0));
                                        this.rd.setFont(new Font("Tahoma", 0, 11));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.drawString(this.sentn[n8][n13] + "   ", 39 + (n14 += this.ftm.stringWidth(this.cnames[n8][n13] + ": ")) + this.movepos[n8], 439 + n9);
                                        n14 += this.ftm.stringWidth(this.sentn[n8][n13] + "   ");
                                        continue;
                                    }
                                    n14 += this.ftm.stringWidth(this.cnames[n8][n13] + ": ");
                                    n14 += this.ftm.stringWidth(this.sentn[n8][n13] + "   ");
                                }
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.fillRect(0, 423 + n9, 5, 24);
                                this.rd.fillRect(794, 423 + n9, 6, 24);
                            } else {
                                for (n13 = this.pointc[n8]; n13 >= 0; --n13) {
                                    int n16;
                                    String string;
                                    if (n13 == 6 && this.msgflk[n8] != 0) {
                                        int n17 = n8;
                                        this.msgflk[n17] = this.msgflk[n17] - 1;
                                    }
                                    this.rd.setColor(new Color(0, n4, n5));
                                    this.rd.setFont(new Font("Tahoma", 1, 11));
                                    this.ftm = this.rd.getFontMetrics();
                                    if (this.ftm.stringWidth(this.cnames[n8][n13] + ": ") + 39 + n14 >= 775) {
                                        string = "";
                                        for (n16 = 0; this.ftm.stringWidth(string) + 39 + n14 < 775 && n16 < this.cnames[n8][n13].length(); ++n16) {
                                            string = string + this.cnames[n8][n13].charAt(n16);
                                        }
                                        string = string + "...";
                                        if (n13 != 6 || this.msgflk[n8] < 67 || this.msgflk[n8] % 3 != 0) {
                                            this.rd.drawString(string, 39 + n14, 439 + n9);
                                        }
                                    } else {
                                        if (n13 != 6 || this.msgflk[n8] < 67 || this.msgflk[n8] % 3 != 0) {
                                            this.rd.drawString(this.cnames[n8][n13] + ": ", 39 + n14, 439 + n9);
                                        }
                                        this.rd.setColor(new Color(0, 0, 0));
                                        this.rd.setFont(new Font("Tahoma", 0, 11));
                                        this.ftm = this.rd.getFontMetrics();
                                        if (this.ftm.stringWidth(this.sentn[n8][n13]) + 39 + (n14 += this.ftm.stringWidth(this.cnames[n8][n13] + ": ")) < 775) {
                                            if (n13 != 6 || this.msgflk[n8] < 67 || this.msgflk[n8] % 3 != 0) {
                                                this.rd.drawString(this.sentn[n8][n13] + "   ", 39 + n14, 439 + n9);
                                            }
                                            n14 += this.ftm.stringWidth(this.sentn[n8][n13] + "   ");
                                            continue;
                                        }
                                        string = "";
                                        for (n16 = 0; this.ftm.stringWidth(string) + 39 + n14 < 775 && n16 < this.sentn[n8][n13].length(); ++n16) {
                                            string = string + this.sentn[n8][n13].charAt(n16);
                                        }
                                        string = string + "...";
                                        if (n13 != 6 || this.msgflk[n8] < 67 || this.msgflk[n8] % 3 != 0) {
                                            this.rd.drawString(string, 39 + n14, 439 + n9);
                                        }
                                    }
                                    break;
                                }
                            }
                        } else {
                            this.msgflk[n8] = 0;
                            n3 = n8;
                        }
                        if (n7 != 0 || this.floater[n8] != 0) {
                            float[] fArray2 = new float[3];
                            Color.RGBtoHSB(this.m.cgrnd[0], this.m.cgrnd[1], this.m.cgrnd[2], fArray2);
                            fArray2[1] = (float)((double)fArray2[1] - 0.076);
                            if (fArray2[1] < 0.0f) {
                                fArray2[1] = 0.0f;
                            }
                            fArray2[2] = (float)((double)fArray2[2] + 0.076);
                            if (fArray2[2] > 1.0f) {
                                fArray2[2] = 1.0f;
                            }
                            this.rd.setColor(Color.getHSBColor(fArray2[0], fArray2[1], fArray2[2]));
                            this.rd.fillRect(5, 423 + n9, 28, 23);
                        }
                        if (n7 != 0) {
                            this.rd.setColor(new Color(0, 0, 0));
                        } else {
                            this.rd.setColor(new Color((int)((float)this.m.cgrnd[0] / 2.0f), (int)((float)this.m.cgrnd[1] / 2.0f), (int)((float)this.m.cgrnd[2] / 2.0f)));
                        }
                        this.rd.setFont(new Font("Tahoma", 1, 11));
                        this.rd.drawString("<<", 10, 439 + n9);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawRect(5, 423 + n9, 789, 23);
                        this.rd.drawLine(33, 423 + n9, 33, 446 + n9);
                        n9 += 23;
                    }
                    if (n > 775 && n < 794 && n2 > 409 - n10 * 23 && n2 < 423 - n10 * 23) {
                        this.rd.drawRect(775, 409 - n10 * 23, 19, 14);
                        this.rd.setColor(new Color(200, 0, 0));
                        if (bl) {
                            control.chatup = 0;
                            if (this.app.cmsg.isShowing()) {
                                this.app.cmsg.hide();
                                this.app.requestFocus();
                            }
                            this.runtyp = 0;
                            try {
                                this.socket.close();
                                this.socket = null;
                                this.din.close();
                                this.din = null;
                                this.dout.close();
                                this.dout = null;
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                        }
                    }
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.rd.drawString("x", 782, 420 - n10 * 23);
                } else {
                    this.drawWarning();
                    if (this.app.cmsg.isShowing()) {
                        this.app.cmsg.hide();
                        this.app.requestFocus();
                    }
                    ++this.warning;
                }
                this.rd.setFont(new Font("Arial", 1, 11));
                this.ftm = this.rd.getFontMetrics();
            } else if (control.chatup != 0) {
                control.chatup = 0;
                if (!this.lan) {
                    this.runtyp = -101;
                    if (this.runner != null) {
                        this.runner.stop();
                    }
                    this.runner = new Thread(this);
                    this.runner.start();
                }
            }
            if (this.holdit && this.multion == 1 && !this.lan && this.sendstat == 0) {
                this.sendstat = 1;
                if (this.runtyp != -101) {
                    if (this.runner != null) {
                        this.runner.stop();
                    }
                    this.runner = new Thread(this);
                    this.runner.start();
                }
            }
            if (control.arrace && this.starcnt < 38 && !this.holdit && checkPoints.stage != 10 || this.multion >= 2) {
                if (this.alocked != -1 && checkPoints.dested[this.alocked] != 0) {
                    this.alocked = -1;
                    this.lalocked = -1;
                }
                if (this.multion >= 2) {
                    if (this.alocked == -1 || this.holdit) {
                        if (this.cntflock == 100) {
                            for (int i = 0; i < this.nplayers; ++i) {
                                if (this.holdit) {
                                    if (checkPoints.pos[i] != 0) continue;
                                    this.alocked = i;
                                    this.im = i;
                                    continue;
                                }
                                if (checkPoints.dested[i] != 0) continue;
                                this.alocked = i;
                                this.im = i;
                            }
                        }
                        ++this.cntflock;
                    } else {
                        this.cntflock = 0;
                    }
                    if (this.lan) {
                        boolean bl2 = true;
                        for (n9 = 0; n9 < this.nplayers; ++n9) {
                            if (this.dested[n9] != 0 || this.plnames[n9].indexOf("MadBot") != -1) continue;
                            bl2 = false;
                        }
                        if (bl2) {
                            this.exitm = 2;
                        }
                    }
                }
                int n18 = this.nplayers;
                for (n9 = 0; n9 < n18; ++n9) {
                    n8 = 0;
                    for (n7 = 0; n7 < this.nplayers; ++n7) {
                        if (checkPoints.pos[n7] != n9 || checkPoints.dested[n7] != 0 || n8 != 0) continue;
                        n6 = 0;
                        int n19 = 0;
                        n4 = 0;
                        n4 = (int)(100.0f + 100.0f * ((float)this.m.snap[2] / 100.0f));
                        if (n4 > 255) {
                            n4 = 255;
                        }
                        if (n4 < 0) {
                            n4 = 0;
                        }
                        this.rd.setColor(new Color(0, 0, n4));
                        if (n9 == 0) {
                            this.rd.drawString("1st", 673, 76 + 30 * n9);
                        }
                        if (n9 == 1) {
                            this.rd.drawString("2nd", 671, 76 + 30 * n9);
                        }
                        if (n9 == 2) {
                            this.rd.drawString("3rd", 671, 76 + 30 * n9);
                        }
                        if (n9 >= 3) {
                            this.rd.drawString("" + (n9 + 1) + "th", 671, 76 + 30 * n9);
                        }
                        if (this.clangame != 0) {
                            if (this.pclan[n7].toLowerCase().equals(this.gaclan.toLowerCase())) {
                                n6 = 255;
                                n19 = 128;
                                n4 = 0;
                            } else {
                                n6 = 0;
                                n19 = 128;
                                n4 = 255;
                            }
                            n6 = (int)((float)n6 + (float)n6 * ((float)this.m.snap[0] / 100.0f));
                            if (n6 > 255) {
                                n6 = 255;
                            }
                            if (n6 < 0) {
                                n6 = 0;
                            }
                            if ((n19 = (int)((float)n19 + (float)n19 * ((float)this.m.snap[1] / 100.0f))) > 255) {
                                n19 = 255;
                            }
                            if (n19 < 0) {
                                n19 = 0;
                            }
                            if ((n4 = (int)((float)n4 + (float)n4 * ((float)this.m.snap[2] / 100.0f))) > 255) {
                                n4 = 255;
                            }
                            if (n4 < 0) {
                                n4 = 0;
                            }
                            this.rd.setColor(new Color(n6, n19, n4));
                            this.rd.drawString(this.plnames[n7], 731 - this.ftm.stringWidth(this.plnames[n7]) / 2, 70 + 30 * n9);
                        }
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString(this.plnames[n7], 730 - this.ftm.stringWidth(this.plnames[n7]) / 2, 70 + 30 * n9);
                        n5 = (int)(60.0f * checkPoints.magperc[n7]);
                        n6 = 244;
                        n19 = 244;
                        n4 = 11;
                        if (n5 > 20) {
                            n19 = (int)(244.0f - 233.0f * ((float)(n5 - 20) / 40.0f));
                        }
                        if ((n6 = (int)((float)n6 + (float)n6 * ((float)this.m.snap[0] / 100.0f))) > 255) {
                            n6 = 255;
                        }
                        if (n6 < 0) {
                            n6 = 0;
                        }
                        if ((n19 = (int)((float)n19 + (float)n19 * ((float)this.m.snap[1] / 100.0f))) > 255) {
                            n19 = 255;
                        }
                        if (n19 < 0) {
                            n19 = 0;
                        }
                        if ((n4 = (int)((float)n4 + (float)n4 * ((float)this.m.snap[2] / 100.0f))) > 255) {
                            n4 = 255;
                        }
                        if (n4 < 0) {
                            n4 = 0;
                        }
                        this.rd.setColor(new Color(n6, n19, n4));
                        this.rd.fillRect(700, 74 + 30 * n9, n5, 5);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawRect(700, 74 + 30 * n9, 60, 5);
                        boolean bl3 = false;
                        if ((this.im != n7 || this.multion >= 2) && n > 661 && n < 775 && n2 > 58 + 30 * n9 && n2 < 83 + 30 * n9) {
                            bl3 = true;
                            if (bl) {
                                if (!this.onlock) {
                                    if (this.alocked != n7 || this.multion >= 2) {
                                        this.alocked = n7;
                                        if (this.multion >= 2) {
                                            this.im = n7;
                                        }
                                    } else {
                                        this.alocked = -1;
                                    }
                                }
                                this.onlock = true;
                            } else if (this.onlock) {
                                this.onlock = false;
                            }
                        }
                        if (this.alocked == n7) {
                            n6 = (int)(159.0f + 159.0f * ((float)this.m.snap[0] / 100.0f));
                            if (n6 > 255) {
                                n6 = 255;
                            }
                            if (n6 < 0) {
                                n6 = 0;
                            }
                            if ((n19 = (int)(207.0f + 207.0f * ((float)this.m.snap[1] / 100.0f))) > 255) {
                                n19 = 255;
                            }
                            if (n19 < 0) {
                                n19 = 0;
                            }
                            if ((n4 = (int)(255.0f + 255.0f * ((float)this.m.snap[2] / 100.0f))) > 255) {
                                n4 = 255;
                            }
                            if (n4 < 0) {
                                n4 = 0;
                            }
                            this.rd.setColor(new Color(n6, n19, n4));
                            this.rd.drawRect(661, 58 + 30 * n9, 114, 25);
                            this.rd.drawRect(662, 59 + 30 * n9, 112, 23);
                        }
                        if (bl3 && !this.onlock) {
                            if (this.alocked == n7) {
                                n6 = (int)(120.0f + 120.0f * ((float)this.m.snap[0] / 100.0f));
                                if (n6 > 255) {
                                    n6 = 255;
                                }
                                if (n6 < 0) {
                                    n6 = 0;
                                }
                                if ((n19 = (int)(114.0f + 114.0f * ((float)this.m.snap[1] / 100.0f))) > 255) {
                                    n19 = 255;
                                }
                                if (n19 < 0) {
                                    n19 = 0;
                                }
                                if ((n4 = (int)(255.0f + 255.0f * ((float)this.m.snap[2] / 100.0f))) > 255) {
                                    n4 = 255;
                                }
                                if (n4 < 0) {
                                    n4 = 0;
                                }
                            } else {
                                n6 = (int)(140.0f + 140.0f * ((float)this.m.snap[0] / 100.0f));
                                if (n6 > 255) {
                                    n6 = 255;
                                }
                                if (n6 < 0) {
                                    n6 = 0;
                                }
                                if ((n19 = (int)(160.0f + 160.0f * ((float)this.m.snap[1] / 100.0f))) > 255) {
                                    n19 = 255;
                                }
                                if (n19 < 0) {
                                    n19 = 0;
                                }
                                if ((n4 = (int)(255.0f + 255.0f * ((float)this.m.snap[2] / 100.0f))) > 255) {
                                    n4 = 255;
                                }
                                if (n4 < 0) {
                                    n4 = 0;
                                }
                            }
                            this.rd.setColor(new Color(n6, n19, n4));
                            this.rd.drawRect(660, 57 + 30 * n9, 116, 27);
                        }
                        n8 = 1;
                    }
                }
            }
            if (uDPMistro.go && uDPMistro.runon == 1 && !this.holdit) {
                int n20 = 0;
                n9 = 0;
                for (n8 = 0; n8 < this.nplayers; ++n8) {
                    if (n8 == uDPMistro.im) continue;
                    ++n9;
                    if (uDPMistro.lframe[n8] == uDPMistro.lcframe[n8] || uDPMistro.force[n8] == 7) {
                        ++n20;
                        continue;
                    }
                    uDPMistro.lcframe[n8] = uDPMistro.lframe[n8];
                }
                if (n20 == n9) {
                    ++this.discon;
                } else if (this.discon != 0) {
                    this.discon = 0;
                }
                if (this.discon == 240) {
                    uDPMistro.runon = 2;
                }
            }
        }
        if (n3 != -1) {
            float[] fArray = new float[3];
            Color.RGBtoHSB(this.m.cgrnd[0], this.m.cgrnd[1], this.m.cgrnd[2], fArray);
            fArray[1] = (float)((double)fArray[1] - 0.22);
            if (fArray[1] < 0.0f) {
                fArray[1] = 0.0f;
            }
            fArray[2] = (float)((double)fArray[2] + 0.22);
            if (fArray[2] > 1.0f) {
                fArray[2] = 1.0f;
            }
            Color color = Color.getHSBColor(fArray[0], fArray[1], fArray[2]);
            this.rd.setColor(color);
            this.rd.fillRect(676, 426 - n3 * 23, 109, 7);
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.setFont(new Font("Tahoma", 1, 11));
            this.rd.drawString("Send Message  >", 684, 439 - n3 * 23);
            this.rd.setColor(new Color((int)((float)this.m.cgrnd[0] / 1.2f), (int)((float)this.m.cgrnd[1] / 1.2f), (int)((float)this.m.cgrnd[2] / 1.2f)));
            this.rd.drawRect(676, 426 - n3 * 23, 109, 17);
            if (!this.app.cmsg.isShowing()) {
                this.app.cmsg.show();
                this.app.cmsg.requestFocus();
                this.lcmsg[n3] = "";
                this.app.cmsg.setText("");
                this.app.cmsg.setBackground(color);
            }
            this.app.movefield(this.app.cmsg, 34, 424 - n3 * 23, 633, 22);
            if (this.app.cmsg.getText().equals(this.lcmsg[n3])) {
                int n21 = n3;
                this.cntchatp[n21] = this.cntchatp[n21] + 1;
            } else {
                this.cntchatp[n3] = -200;
            }
            this.lcmsg[n3] = "" + this.app.cmsg.getText();
            if (this.cntchatp[n3] == 67) {
                control.chatup = 0;
            }
            if (this.app.cmsg.getText().length() > 100) {
                this.app.cmsg.setText(this.app.cmsg.getText().substring(0, 100));
                this.app.cmsg.select(100, 100);
            }
            this.rd.setFont(new Font("Arial", 1, 11));
            this.ftm = this.rd.getFontMetrics();
        }
    }

    public void levelhigh(int n, int n2, int n3, int n4, int n5) {
        this.rd.drawImage(this.gameh, 301, 20, null);
        int n6 = 16;
        int n7 = 48;
        int n8 = 96;
        if (n4 < 50) {
            if (this.aflk) {
                n6 = 106;
                n7 = 176;
                n8 = 255;
                this.aflk = false;
            } else {
                this.aflk = true;
            }
        }
        if (n != this.im) {
            if (n3 == 0) {
                this.drawcs(60, "You Wasted 'em!", n6, n7, n8, 0);
            } else if (n3 == 1) {
                this.drawcs(60, "Close Finish!", n6, n7, n8, 0);
            } else {
                this.drawcs(60, "Close Finish!  Almost got it!", n6, n7, n8, 0);
            }
        } else if (n2 == 229) {
            if (this.discon != 240) {
                this.drawcs(60, "Wasted!", n6, n7, n8, 0);
            } else {
                this.drawcs(60, "Disconnected!", n6, n7, n8, 0);
            }
        } else if (n5 > 2 || n5 < 0) {
            this.drawcs(60, "Stunts!", n6, n7, n8, 0);
        } else {
            this.drawcs(60, "Best Stunt!", n6, n7, n8, 0);
        }
        this.drawcs(380, "Press  [ Enter ]  to continue", 0, 0, 0, 0);
    }

    public void inst(Control control) {
        if (this.flipo == 0) {
            this.flipo = 1;
        }
        if (this.flipo == 2) {
            this.flipo = 3;
            this.dudo = 200;
        }
        if (this.flipo == 4) {
            this.flipo = 5;
            this.dudo = 250;
        }
        if (this.flipo == 6) {
            this.flipo = 7;
            this.dudo = 200;
        }
        if (this.flipo == 8) {
            this.flipo = 9;
            this.dudo = 250;
        }
        if (this.flipo == 10) {
            this.flipo = 11;
            this.dudo = 200;
        }
        if (this.flipo == 12) {
            this.flipo = 13;
            this.dudo = 200;
        }
        if (this.flipo == 14) {
            this.flipo = 15;
            this.dudo = 100;
        }
        this.mainbg(2);
        this.rd.setComposite(AlphaComposite.getInstance(3, 0.3f));
        this.rd.drawImage(this.bggo, 65, 25, null);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.fillRect(735, 0, 65, 450);
        this.rd.fillRect(65, 425, 670, 25);
        this.aflk = !this.aflk;
        if (this.flipo != 1 && this.flipo != 16) {
            if (this.dudo > 0) {
                if (this.aflk) {
                    this.duds = Math.random() > Math.random() ? (int)(Math.random() * 3.0) : (int)(Math.random() * 2.0);
                }
                --this.dudo;
            } else {
                this.duds = 0;
            }
            this.rd.setComposite(AlphaComposite.getInstance(3, 0.4f));
            this.rd.drawImage(this.dude[this.duds], 95, 15, null);
            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
            this.rd.drawImage(this.oflaot, 192, 42, null);
        }
        this.rd.setColor(new Color(0, 64, 128));
        this.rd.setFont(new Font("Arial", 1, 13));
        if (this.flipo == 3 || this.flipo == 5) {
            if (this.flipo == 3) {
                this.rd.drawString("Hello!  Welcome to the world of", 262, 67);
                this.rd.drawString("!", 657, 67);
                this.rd.drawImage(this.nfm, 469, 55, null);
                this.rd.drawString("In this game there are two ways to complete a stage.", 262, 107);
                this.rd.drawString("One is by racing and finishing in first place, the other is by", 262, 127);
                this.rd.drawString("wasting and crashing all the other cars in the stage!", 262, 147);
            } else {
                this.rd.setColor(new Color(0, 128, 255));
                this.rd.drawString("While racing, you will need to focus on going fast and passing", 262, 67);
                this.rd.drawString("through all the checkpoints in the track. To complete a lap, you", 262, 87);
                this.rd.drawString("must not miss a checkpoint.", 262, 107);
                this.rd.drawString("While wasting, you will just need to chase the other cars and", 262, 127);
                this.rd.drawString("crash into them (without worrying about track and checkpoints).", 262, 147);
            }
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawImage(this.racing, 165, 185, null);
            this.rd.drawImage(this.ory, 429, 235, null);
            this.rd.drawImage(this.wasting, 492, 185, null);
            this.rd.setFont(new Font("Arial", 1, 11));
            this.rd.drawString("Checkpoint", 392, 189);
            this.rd.setFont(new Font("Arial", 1, 13));
            this.rd.drawString("Drive your car using the Arrow Keys and Spacebar", 125, 320);
            this.rd.drawImage(this.space, 171, 355, null);
            this.rd.drawImage(this.arrows, 505, 323, null);
            this.rd.setFont(new Font("Arial", 1, 11));
            this.rd.drawString("(When your car is on the ground Spacebar is for Handbrake)", 125, 341);
            this.rd.drawString("Accelerate", 515, 319);
            this.rd.drawString("Brake/Reverse", 506, 397);
            this.rd.drawString("Turn left", 454, 375);
            this.rd.drawString("Turn right", 590, 375);
            this.rd.drawString("Handbrake", 247, 374);
        }
        if (this.flipo == 7 || this.flipo == 9) {
            if (this.flipo == 7) {
                this.rd.drawString("Whether you are racing or wasting the other cars you will need", 262, 67);
                this.rd.drawString("to power up your car.", 262, 87);
                this.rd.drawString("=> More 'Power' makes your car become faster and stronger!", 262, 107);
                this.rd.drawString("To power up your car (and keep it powered up) you will need to", 262, 127);
                this.rd.drawString("perform stunts!", 262, 147);
                this.rd.drawImage(this.chil, 167, 295, null);
            } else {
                this.rd.drawString("The better the stunt the more power you get!", 262, 67);
                this.rd.setColor(new Color(0, 128, 255));
                this.rd.drawString("Forward looping pushes your car forwards in the air and helps", 262, 87);
                this.rd.drawString("when racing. Backward looping pushes your car upwards giving it", 262, 107);
                this.rd.drawString("more hang time in the air making it easier to control its landing.", 262, 127);
                this.rd.drawString("Left and right rolls shift your car in the air left and right slightly.", 262, 147);
                if (this.aflk || this.dudo < 150) {
                    this.rd.drawImage(this.chil, 167, 295, null);
                }
            }
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawImage(this.stunts, 105, 175, null);
            this.rd.drawImage(this.opwr, 540, 253, null);
            this.rd.setFont(new Font("Arial", 1, 13));
            this.rd.drawString("To perform stunts. When your car is in the AIR:", 125, 310);
            this.rd.drawString("Press combo Spacebar + Arrow Keys", 125, 330);
            this.rd.drawImage(this.space, 185, 355, null);
            this.rd.drawImage(this.plus, 405, 358, null);
            this.rd.drawImage(this.arrows, 491, 323, null);
            this.rd.setFont(new Font("Arial", 1, 11));
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawString("Forward Loop", 492, 319);
            this.rd.drawString("Backward Loop", 490, 397);
            this.rd.drawString("Left Roll", 443, 375);
            this.rd.drawString("Right Roll", 576, 375);
            this.rd.drawString("Spacebar", 266, 374);
            this.rd.setColor(new Color(140, 243, 244));
            this.rd.fillRect(602, 257, 76, 9);
        }
        if (this.flipo == 11 || this.flipo == 13) {
            if (this.flipo == 11) {
                this.rd.drawString("When wasting cars, to help you find the other cars in the stage,", 262, 67);
                this.rd.drawString("press [ A ] to toggle the guidance arrow from pointing to the track", 262, 87);
                this.rd.drawString("to pointing to the cars.", 262, 107);
                this.rd.drawString("When your car is damaged. You fix it (and reset its 'Damage') by", 262, 127);
                this.rd.drawString("jumping through the electrified hoop.", 262, 147);
            } else {
                this.rd.setColor(new Color(0, 128, 255));
                this.rd.drawString("You will find that in some stages it's easier to waste the other cars", 262, 67);
                this.rd.drawString("and in some others it's easier to race and finish in first place.", 262, 87);
                this.rd.drawString("It is up to you to decide when to waste and when to race.", 262, 107);
                this.rd.drawString("And remember, 'Power' is an important factor in the game. You", 262, 127);
                this.rd.drawString("will need it whether you are racing or wasting!", 262, 147);
            }
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawImage(this.fixhoop, 185, 218, null);
            this.rd.drawImage(this.sarrow, 385, 228, null);
            this.rd.setFont(new Font("Arial", 1, 11));
            this.rd.drawString("The Electrified Hoop", 192, 216);
            this.rd.drawString("Jumping through it fixes your car.", 158, 338);
            this.rd.drawString("Make guidance arrow point to cars.", 385, 216);
        }
        if (this.flipo == 15) {
            this.rd.drawString("And if you don\u2019t know who I am,", 262, 67);
            this.rd.drawString("I am Coach Insano, I am the coach and narrator of this game!", 262, 87);
            this.rd.drawString("I recommended starting with NFM 1 if it\u2019s your first time to play.", 262, 127);
            this.rd.drawString("Good Luck & Have Fun!", 262, 147);
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawString("Other Controls :", 155, 205);
            this.rd.setFont(new Font("Arial", 1, 11));
            this.rd.drawImage(this.kz, 169, 229, null);
            this.rd.drawString("OR", 206, 251);
            this.rd.drawImage(this.kx, 229, 229, null);
            this.rd.drawString("To look behind you while driving.", 267, 251);
            this.rd.drawImage(this.kv, 169, 279, null);
            this.rd.drawString("Change Views", 207, 301);
            this.rd.drawImage(this.kenter, 169, 329, null);
            this.rd.drawString("Navigate & Pause Game", 275, 351);
            this.rd.drawImage(this.km, 489, 229, null);
            this.rd.drawString("Mute Music", 527, 251);
            this.rd.drawImage(this.kn, 489, 279, null);
            this.rd.drawString("Mute Sound Effects", 527, 301);
            this.rd.drawImage(this.ks, 489, 329, null);
            this.rd.drawString("Toggle radar / map", 527, 351);
        }
        if (this.flipo == 1 || this.flipo == 16) {
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            this.rd.setColor(new Color(0, 0, 0));
            if (this.flipo == 16) {
                this.rd.drawString("M A I N    C O N T R O L S   -   once again!", 400 - this.ftm.stringWidth("M A I N    C O N T R O L S   -   once again!") / 2, 49);
            } else {
                this.rd.drawString("M A I N    C O N T R O L S", 400 - this.ftm.stringWidth("M A I N    C O N T R O L S") / 2, 49);
            }
            this.rd.drawString("Drive your car using the Arrow Keys:", 125, 80);
            this.rd.drawString("On the GROUND Spacebar is for Handbrake", 125, 101);
            this.rd.drawImage(this.space, 171, 115, null);
            this.rd.drawImage(this.arrows, 505, 83, null);
            this.rd.setFont(new Font("Arial", 1, 11));
            this.ftm = this.rd.getFontMetrics();
            this.rd.drawString("Accelerate", 515, 79);
            this.rd.drawString("Brake/Reverse", 506, 157);
            this.rd.drawString("Turn left", 454, 135);
            this.rd.drawString("Turn right", 590, 135);
            this.rd.drawString("Handbrake", 247, 134);
            this.drawcs(175, "----------------------------------------------------------------------------------------------------------------------------------------------------", 0, 64, 128, 3);
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            this.rd.drawString("To perform STUNTS:", 125, 200);
            this.rd.drawString("In the AIR press combo Spacebar + Arrow Keys", 125, 220);
            this.rd.drawImage(this.space, 185, 245, null);
            this.rd.drawImage(this.plus, 405, 248, null);
            this.rd.drawImage(this.arrows, 491, 213, null);
            this.rd.setFont(new Font("Arial", 1, 11));
            this.ftm = this.rd.getFontMetrics();
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawString("Forward Loop", 492, 209);
            this.rd.drawString("Backward Loop", 490, 287);
            this.rd.drawString("Left Roll", 443, 265);
            this.rd.drawString("Right Roll", 576, 265);
            this.rd.drawString("Spacebar", 266, 264);
            this.rd.drawImage(this.stunts, 125, 285, null);
        }
        if (this.flipo >= 1 && this.flipo <= 15) {
            this.rd.drawImage(this.next[this.pnext], 665, 395, null);
        }
        if (this.flipo >= 3 && this.flipo <= 16) {
            this.rd.drawImage(this.back[this.pback], 75, 395, null);
        }
        if (this.flipo == 16) {
            this.rd.drawImage(this.contin[this.pcontin], 565, 395, null);
        }
        if (control.enter || control.right) {
            if (control.enter && this.flipo == 16) {
                this.flipo = 0;
                this.fase = this.oldfase;
                this.rd.setFont(new Font("Arial", 1, 11));
                this.ftm = this.rd.getFontMetrics();
            }
            control.enter = false;
            control.right = false;
            if (this.flipo >= 1 && this.flipo <= 15) {
                ++this.flipo;
            }
        }
        if (control.left) {
            if (this.flipo >= 3 && this.flipo <= 15) {
                this.flipo -= 3;
            }
            if (this.flipo == 16) {
                --this.flipo;
            }
            control.left = false;
        }
    }

    public void maini(Control control) {
        if (this.flipo == 0) {
            this.app.setCursor(new Cursor(0));
            ++this.flipo;
        }
        this.mainbg(1);
        this.rd.setComposite(AlphaComposite.getInstance(3, 0.6f));
        this.rd.drawImage(this.logomadbg, 65, 25, null);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        this.rd.drawImage(this.logomadnes, 233, 186, null);
        float f = (float)this.flkat / 800.0f;
        if ((double)f > 0.2) {
            f = 0.2f;
        }
        if (this.flkat > 200 && (f = (float)(400 - this.flkat) / 1000.0f) < 0.0f) {
            f = 0.0f;
        }
        ++this.flkat;
        if (this.flkat == 400) {
            this.flkat = 0;
        }
        this.rd.setComposite(AlphaComposite.getInstance(3, f));
        this.rd.drawImage(this.dude[0], 351 + this.gxdu, 28 + this.gydu, null);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        if (this.movly == 0) {
            this.gxdu = (int)(5.0 - 11.0 * Math.random());
            this.gydu = (int)(5.0 - 11.0 * Math.random());
        }
        ++this.movly;
        if (this.movly == 2) {
            this.movly = 0;
        }
        this.rd.drawImage(this.logocars, 66, 33, null);
        this.rd.drawImage(this.opback, 247, 237, null);
        if (this.muhi < 0) {
            this.rd.setColor(new Color(140, 70, 0));
            this.rd.fillRoundRect(335, 293, 114, 19, 7, 20);
        }
        --this.muhi;
        if (this.muhi < -5) {
            this.muhi = 50;
        }
        if (control.up) {
            --this.opselect;
            if (this.opselect == -1) {
                this.opselect = 3;
            }
            control.up = false;
        }
        if (control.down) {
            ++this.opselect;
            if (this.opselect == 4) {
                this.opselect = 0;
            }
            control.down = false;
        }
        if (this.opselect == 0) {
            if (this.shaded) {
                this.rd.setColor(new Color(140, 70, 0));
                this.rd.fillRect(343, 261, 110, 22);
                this.aflk = false;
            }
            if (this.aflk) {
                this.rd.setColor(new Color(200, 200, 0));
                this.aflk = false;
            } else {
                this.rd.setColor(new Color(255, 128, 0));
                this.aflk = true;
            }
            this.rd.drawRoundRect(343, 261, 110, 22, 7, 20);
        } else {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawRoundRect(343, 261, 110, 22, 7, 20);
        }
        if (this.opselect == 1) {
            if (this.shaded) {
                this.rd.setColor(new Color(140, 70, 0));
                this.rd.fillRect(288, 291, 221, 22);
                this.aflk = false;
            }
            if (this.aflk) {
                this.rd.setColor(new Color(200, 191, 0));
                this.aflk = false;
            } else {
                this.rd.setColor(new Color(255, 95, 0));
                this.aflk = true;
            }
            this.rd.drawRoundRect(288, 291, 221, 22, 7, 20);
        } else {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawRoundRect(288, 291, 221, 22, 7, 20);
        }
        if (this.opselect == 2) {
            if (this.shaded) {
                this.rd.setColor(new Color(140, 70, 0));
                this.rd.fillRect(301, 321, 196, 22);
                this.aflk = false;
            }
            if (this.aflk) {
                this.rd.setColor(new Color(200, 128, 0));
                this.aflk = false;
            } else {
                this.rd.setColor(new Color(255, 128, 0));
                this.aflk = true;
            }
            this.rd.drawRoundRect(301, 321, 196, 22, 7, 20);
        } else {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawRoundRect(301, 321, 196, 22, 7, 20);
        }
        if (this.opselect == 3) {
            if (this.shaded) {
                this.rd.setColor(new Color(140, 70, 0));
                this.rd.fillRect(357, 351, 85, 22);
                this.aflk = false;
            }
            if (this.aflk) {
                this.rd.setColor(new Color(200, 0, 0));
                this.aflk = false;
            } else {
                this.rd.setColor(new Color(255, 128, 0));
                this.aflk = true;
            }
            this.rd.drawRoundRect(357, 351, 85, 22, 7, 20);
        } else {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawRoundRect(357, 351, 85, 22, 7, 20);
        }
        this.rd.drawImage(this.opti, 294, 265, null);
        if (control.enter || control.handb) {
            if (this.opselect == 1) {
                this.mtop = true;
                this.multion = 1;
                this.gmode = 0;
                if (this.firstime) {
                    this.oldfase = -9;
                    this.fase = 11;
                    this.firstime = false;
                } else {
                    this.fase = -9;
                }
            }
            if (this.opselect == 2) {
                this.oldfase = 10;
                this.fase = 11;
                this.firstime = false;
            }
            if (this.opselect == 3) {
                this.fase = 8;
            }
            if (this.opselect == 0) {
                if (this.unlocked[0] == 11) {
                    this.opselect = this.unlocked[1] != 17 ? 1 : 2;
                }
                if (this.firstime) {
                    this.oldfase = 102;
                    this.fase = 11;
                    this.firstime = false;
                } else {
                    this.fase = 102;
                }
            }
            this.flipo = 0;
            control.enter = false;
            control.handb = false;
        }
        this.rd.drawImage(this.byrd, 72, 410, null);
        this.rd.drawImage(this.nfmcoms, 567, 410, null);
        if (this.shaded) {
            this.app.repaint();
            try {
                Thread.sleep(200L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

    public void maini2(Control control, int n, int n2, int n3) {
        this.mainbg(1);
        this.rd.setComposite(AlphaComposite.getInstance(3, 0.6f));
        this.rd.drawImage(this.logomadbg, 65, 25, null);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        this.rd.drawImage(this.logomadnes, 233, 186, null);
        float f = (float)this.flkat / 800.0f;
        if ((double)f > 0.2) {
            f = 0.2f;
        }
        if (this.flkat > 200 && (f = (float)(400 - this.flkat) / 1000.0f) < 0.0f) {
            f = 0.0f;
        }
        ++this.flkat;
        if (this.flkat == 400) {
            this.flkat = 0;
        }
        this.rd.setComposite(AlphaComposite.getInstance(3, f));
        this.rd.drawImage(this.dude[0], 351 + this.gxdu, 28 + this.gydu, null);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        if (this.movly == 0) {
            this.gxdu = (int)(5.0 - 11.0 * Math.random());
            this.gydu = (int)(5.0 - 11.0 * Math.random());
        }
        ++this.movly;
        if (this.movly == 2) {
            this.movly = 0;
        }
        this.rd.drawImage(this.logocars, 66, 33, null);
        this.rd.drawImage(this.opback, 247, 237, null);
        if (control.up) {
            --this.opselect;
            if (this.opselect == -1) {
                this.opselect = 3 - this.dropf / 15;
            }
            control.up = false;
        }
        if (control.down) {
            ++this.opselect;
            if (this.opselect == 4 - this.dropf / 15) {
                this.opselect = 0;
            }
            control.down = false;
        }
        if (this.opselect == 0) {
            if (this.shaded) {
                this.rd.setColor(new Color(140, 70, 0));
                this.rd.fillRect(358, 262 + this.dropf, 82, 22);
                this.aflk = false;
            }
            if (this.aflk) {
                this.rd.setColor(new Color(200, 64, 0));
                this.aflk = false;
            } else {
                this.rd.setColor(new Color(255, 128, 0));
                this.aflk = true;
            }
            this.rd.drawRoundRect(358, 262 + this.dropf, 82, 22, 7, 20);
        } else {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawRoundRect(358, 262 + this.dropf, 82, 22, 7, 20);
        }
        if (this.opselect == 1) {
            if (this.shaded) {
                this.rd.setColor(new Color(140, 70, 0));
                this.rd.fillRect(358, 290 + this.dropf, 82, 22);
                this.aflk = false;
            }
            if (this.aflk) {
                this.rd.setColor(new Color(200, 64, 0));
                this.aflk = false;
            } else {
                this.rd.setColor(new Color(255, 95, 0));
                this.aflk = true;
            }
            this.rd.drawRoundRect(358, 290 + this.dropf, 82, 22, 7, 20);
        } else {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawRoundRect(358, 290 + this.dropf, 82, 22, 7, 20);
        }
        if (this.opselect == 2) {
            if (this.shaded) {
                this.rd.setColor(new Color(140, 70, 0));
                this.rd.fillRect(333, 318 + this.dropf, 132, 22);
                this.aflk = false;
            }
            if (this.aflk) {
                this.rd.setColor(new Color(200, 255, 0));
                this.aflk = false;
            } else {
                this.rd.setColor(new Color(255, 128, 0));
                this.aflk = true;
            }
            this.rd.drawRoundRect(333, 318 + this.dropf, 132, 22, 7, 20);
        } else {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawRoundRect(333, 318 + this.dropf, 132, 22, 7, 20);
        }
        if (this.dropf == 0) {
            if (this.opselect == 3) {
                if (this.shaded) {
                    this.rd.setColor(new Color(140, 70, 0));
                    this.rd.fillRect(348, 346, 102, 22);
                    this.aflk = false;
                }
                if (this.aflk) {
                    this.rd.setColor(new Color(200, 64, 0));
                    this.aflk = false;
                } else {
                    this.rd.setColor(new Color(255, 128, 0));
                    this.aflk = true;
                }
                this.rd.drawRoundRect(348, 346, 102, 22, 7, 20);
            } else {
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawRoundRect(348, 346, 102, 22, 7, 20);
            }
        }
        this.rd.drawImage(this.opti2, 346, 265 + this.dropf, null);
        if (this.dropf != 0) {
            this.rd.setColor(new Color(58, 30, 8));
            this.rd.fillRect(357, 365, 87, 15);
        }
        if (control.enter || control.handb) {
            this.mtop = false;
            if (this.opselect == 0) {
                this.multion = 0;
                this.clangame = 0;
                this.gmode = 1;
                this.fase = -9;
            }
            if (this.opselect == 1) {
                this.multion = 0;
                this.clangame = 0;
                this.gmode = 2;
                this.fase = -9;
                this.opselect = 0;
            }
            if (this.dropf == 0 && this.opselect == 3) {
                this.multion = 0;
                this.clangame = 0;
                this.gmode = 0;
                this.fase = -9;
                this.opselect = 0;
            }
            if (this.opselect == 2) {
                this.multion = 1;
                this.gmode = 0;
                if (this.firstime) {
                    this.oldfase = -9;
                    this.fase = 11;
                    this.firstime = false;
                } else {
                    this.fase = -9;
                }
            }
            this.flipo = 0;
            control.enter = false;
            control.handb = false;
        }
        this.rd.drawImage(this.byrd, 72, 410, null);
        this.rd.drawImage(this.nfmcoms, 567, 410, null);
        boolean bl = false;
        if (n3 == 2) {
            bl = true;
        }
        if (this.drawcarb(true, null, "   < Back   ", 161, 313, n, n2, bl)) {
            this.opselect = 0;
            this.fase = 10;
        }
        if (this.shaded) {
            this.app.repaint();
            try {
                Thread.sleep(200L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

    public void pausedgame(int n, Control control, Record record) {
        if (!this.badmac) {
            this.rd.drawImage(this.fleximg, 0, 0, null);
        } else {
            this.rd.setColor(new Color(30, 67, 110));
            this.rd.fillRect(281, 8, 237, 188);
        }
        if (control.up) {
            --this.opselect;
            if (this.opselect == -1) {
                this.opselect = 3;
            }
            control.up = false;
        }
        if (control.down) {
            ++this.opselect;
            if (this.opselect == 4) {
                this.opselect = 0;
            }
            control.down = false;
        }
        if (this.opselect == 0) {
            this.rd.setColor(new Color(64, 143, 223));
            this.rd.fillRoundRect(329, 45, 137, 22, 7, 20);
            if (this.shaded) {
                this.rd.setColor(new Color(225, 200, 255));
            } else {
                this.rd.setColor(new Color(0, 89, 223));
            }
            this.rd.drawRoundRect(329, 45, 137, 22, 7, 20);
        }
        if (this.opselect == 1) {
            this.rd.setColor(new Color(64, 143, 223));
            this.rd.fillRoundRect(320, 73, 155, 22, 7, 20);
            if (this.shaded) {
                this.rd.setColor(new Color(225, 200, 255));
            } else {
                this.rd.setColor(new Color(0, 89, 223));
            }
            this.rd.drawRoundRect(320, 73, 155, 22, 7, 20);
        }
        if (this.opselect == 2) {
            this.rd.setColor(new Color(64, 143, 223));
            this.rd.fillRoundRect(303, 99, 190, 22, 7, 20);
            if (this.shaded) {
                this.rd.setColor(new Color(225, 200, 255));
            } else {
                this.rd.setColor(new Color(0, 89, 223));
            }
            this.rd.drawRoundRect(303, 99, 190, 22, 7, 20);
        }
        if (this.opselect == 3) {
            this.rd.setColor(new Color(64, 143, 223));
            this.rd.fillRoundRect(341, 125, 109, 22, 7, 20);
            if (this.shaded) {
                this.rd.setColor(new Color(225, 200, 255));
            } else {
                this.rd.setColor(new Color(0, 89, 223));
            }
            this.rd.drawRoundRect(341, 125, 109, 22, 7, 20);
        }
        this.rd.drawImage(this.paused, 281, 8, null);
        if (control.enter || control.handb) {
            if (this.opselect == 0) {
                if (this.loadedt && !this.mutem) {
                    this.strack.resume();
                }
                this.fase = 0;
            }
            if (this.opselect == 1) {
                if (record.caught >= 300) {
                    if (this.loadedt && !this.mutem) {
                        this.strack.resume();
                    }
                    this.fase = -1;
                } else {
                    this.fase = -8;
                }
            }
            if (this.opselect == 2) {
                if (this.loadedt) {
                    this.strack.stop();
                }
                this.oldfase = -7;
                this.fase = 11;
            }
            if (this.opselect == 3) {
                if (this.loadedt) {
                    this.strack.unload();
                }
                this.fase = 102;
                if (this.gmode == 0) {
                    this.opselect = 3;
                }
                if (this.gmode == 1) {
                    this.opselect = 0;
                }
                if (this.gmode == 2) {
                    this.opselect = 1;
                }
                this.rd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }
            control.enter = false;
            control.handb = false;
        }
    }

    public void replyn() {
        if (this.aflk) {
            this.drawcs(30, "Replay  > ", 0, 0, 0, 0);
            this.aflk = false;
        } else {
            this.drawcs(30, "Replay  >>", 0, 128, 255, 0);
            this.aflk = true;
        }
    }

    public void cantreply() {
        this.rd.setColor(new Color(64, 143, 223));
        this.rd.fillRoundRect(200, 73, 400, 23, 7, 20);
        this.rd.setColor(new Color(0, 89, 223));
        this.rd.drawRoundRect(200, 73, 400, 23, 7, 20);
        this.drawcs(89, "Sorry not enough replay data to play available, please try again later.", 255, 255, 255, 1);
    }

    public void nofocus() {
        this.rd.setColor(new Color(255, 255, 255));
        this.rd.fillRect(0, 0, 800, 20);
        this.rd.fillRect(0, 0, 20, 450);
        this.rd.fillRect(0, 430, 800, 20);
        this.rd.fillRect(780, 0, 20, 450);
        this.rd.setColor(new Color(192, 192, 192));
        this.rd.drawRect(20, 20, 760, 410);
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.drawRect(22, 22, 756, 406);
        this.rd.setFont(new Font("Arial", 1, 11));
        this.ftm = this.rd.getFontMetrics();
        this.drawcs(14, "Game lost its focus.   Click screen with mouse to continue.", 100, 100, 100, 3);
        this.drawcs(445, "Game lost its focus.   Click screen with mouse to continue.", 100, 100, 100, 3);
    }

    public void inishcarselect(ContO[] contOArray) {
        int n;
        int n2;
        this.nplayers = 7;
        this.im = 0;
        this.xstart[0] = 0;
        this.xstart[1] = -350;
        this.xstart[2] = 350;
        this.xstart[3] = 0;
        this.xstart[4] = -350;
        this.xstart[5] = 350;
        this.xstart[6] = 0;
        this.zstart[0] = -760;
        this.zstart[1] = -380;
        this.zstart[2] = -380;
        this.zstart[3] = 0;
        this.zstart[4] = 380;
        this.zstart[5] = 380;
        this.zstart[6] = 760;
        this.onmsc = -1;
        this.remi = false;
        this.basefase = 0;
        this.noclass = false;
        if (this.testdrive != 1 && this.testdrive != 2) {
            if (this.gmode != 0) {
                this.cfase = 0;
                this.sc[0] = this.scm[this.gmode - 1];
            }
            if (this.gmode == 0) {
                this.sc[0] = this.osc;
            }
            if (this.cd.lastload != 1 || this.cfase != 3) {
                this.onmsc = this.sc[0];
            }
            if (this.cfase == 0 && this.sc[0] > 15) {
                this.sc[0] = 15;
                if (this.multion != 0) {
                    this.cfase = -1;
                }
            }
            if (this.onjoin != -1 && this.multion != 0) {
                if (this.ontyp <= -2) {
                    this.cfase = 0;
                }
                if (this.ontyp >= 20) {
                    this.ontyp -= 20;
                    this.cfase = 0;
                }
                if (this.ontyp >= 10) {
                    this.ontyp -= 10;
                    if (this.cd.lastload != 2) {
                        this.cfase = -1;
                        this.onjoin = -1;
                    } else {
                        this.cfase = 3;
                    }
                }
            }
            if (this.cfase == 11 || this.cfase == 101) {
                this.cfase = this.sc[0] >= 16 && this.cd.lastload == 2 && this.sc[0] < 36 ? 3 : 0;
            }
            if (this.cfase == 3) {
                if (this.multion != 0 && this.cd.lastload == 1) {
                    this.sc[0] = 15;
                    this.minsl = 0;
                    this.maxsl = 15;
                    this.cfase = 0;
                }
                if (this.cd.lastload == 0) {
                    this.sc[0] = 15;
                    this.minsl = 0;
                    this.maxsl = 15;
                    this.cfase = 0;
                }
                if (this.cd.lastload == 2) {
                    this.minsl = 16;
                    this.maxsl = this.cd.nlocars - 1;
                    if (this.sc[0] < this.minsl) {
                        this.sc[0] = this.minsl;
                    }
                    if (this.sc[0] > this.maxsl) {
                        this.sc[0] = this.maxsl;
                    }
                    if (this.onjoin != -1 && this.multion != 0 && this.ontyp > 0 && this.ontyp <= 5) {
                        n2 = 0;
                        for (n = 16; n < this.cd.nlocars; ++n) {
                            if (Math.abs(this.cd.cclass[n] - (this.ontyp - 1)) > 1) continue;
                            if (n2 == 0) {
                                this.minsl = n;
                                n2 = 1;
                            }
                            if (n2 == 0) continue;
                            this.maxsl = n;
                        }
                        if (n2 == 0) {
                            this.onjoin = -1;
                            this.noclass = true;
                        } else {
                            if (this.sc[0] < this.minsl) {
                                this.sc[0] = this.minsl;
                            }
                            if (this.sc[0] > this.maxsl) {
                                this.sc[0] = this.maxsl;
                            }
                            if (Math.abs(this.cd.cclass[this.sc[0]] - (this.ontyp - 1)) > 1) {
                                this.sc[0] = this.minsl;
                            }
                        }
                    }
                }
                if (this.cd.lastload == -2 && this.logged) {
                    this.cfase = 5;
                    this.showtf = false;
                    this.cd.action = 3;
                    this.cd.sparkactionloader();
                }
            }
            if (this.cfase == 0) {
                this.minsl = 0;
                this.maxsl = 15;
                if (this.onjoin != -1 && this.multion != 0) {
                    if (this.ontyp == 1) {
                        this.minsl = 0;
                        this.maxsl = 5;
                    }
                    if (this.ontyp == 2) {
                        this.minsl = 0;
                        this.maxsl = 9;
                    }
                    if (this.ontyp == 3) {
                        this.minsl = 5;
                        this.maxsl = 10;
                    }
                    if (this.ontyp == 4) {
                        this.minsl = 6;
                        this.maxsl = 15;
                    }
                    if (this.ontyp == 5) {
                        this.minsl = 10;
                        this.maxsl = 15;
                    }
                    if (this.ontyp <= -2) {
                        this.minsl = Math.abs(this.ontyp + 2);
                        this.maxsl = Math.abs(this.ontyp + 2);
                    }
                }
                if (this.sc[0] < this.minsl) {
                    this.sc[0] = this.minsl;
                }
                if (this.sc[0] > this.maxsl) {
                    this.sc[0] = this.maxsl;
                }
            }
        } else {
            this.minsl = this.sc[0];
            this.maxsl = this.sc[0];
        }
        this.app.mcars.setBackground(new Color(0, 0, 0));
        this.app.mcars.setForeground(new Color(47, 179, 255));
        this.app.mcars.alphad = true;
        this.app.mcars.carsel = true;
        this.carsbginflex();
        this.flatrstart = 0;
        this.m.lightson = false;
        this.pnext = 0;
        this.pback = 0;
        this.lsc = -1;
        this.mouson = -1;
        if (this.multion == 0) {
            this.app.mycar.setLabel(" Include in this game.");
            this.app.mycar.setBackground(new Color(198, 179, 129));
            this.app.mycar.setForeground(new Color(0, 0, 0));
            n2 = 16;
            if (this.cd.lastload == 2) {
                n2 = this.cd.nlocars;
            }
            for (n = 0; n < n2; ++n) {
                int n3;
                float[] fArray = new float[3];
                Color.RGBtoHSB(contOArray[n].fcol[0], contOArray[n].fcol[1], contOArray[n].fcol[2], fArray);
                for (n3 = 0; n3 < contOArray[n].npl; ++n3) {
                    if (contOArray[n].p[n3].colnum != 1) continue;
                    contOArray[n].p[n3].hsb[0] = fArray[0];
                    contOArray[n].p[n3].hsb[1] = fArray[1];
                    contOArray[n].p[n3].hsb[2] = fArray[2];
                    contOArray[n].p[n3].oc[0] = contOArray[n].fcol[0];
                    contOArray[n].p[n3].oc[1] = contOArray[n].fcol[1];
                    contOArray[n].p[n3].oc[2] = contOArray[n].fcol[2];
                }
                Color.RGBtoHSB(contOArray[n].scol[0], contOArray[n].scol[1], contOArray[n].scol[2], fArray);
                for (n3 = 0; n3 < contOArray[n].npl; ++n3) {
                    if (contOArray[n].p[n3].colnum != 2) continue;
                    contOArray[n].p[n3].hsb[0] = fArray[0];
                    contOArray[n].p[n3].hsb[1] = fArray[1];
                    contOArray[n].p[n3].hsb[2] = fArray[2];
                    contOArray[n].p[n3].oc[0] = contOArray[n].scol[0];
                    contOArray[n].p[n3].oc[1] = contOArray[n].scol[1];
                    contOArray[n].p[n3].oc[2] = contOArray[n].scol[2];
                }
                contOArray[n].xy = 0;
            }
            for (n = 0; n < 6; ++n) {
                this.arnp[n] = -1.0f;
            }
        }
        this.m.trk = 0;
        this.m.crs = true;
        this.m.x = -400;
        this.m.y = -525;
        this.m.z = -50;
        this.m.xz = 0;
        this.m.zy = 10;
        this.m.ground = 495;
        this.m.ih = 0;
        this.m.iw = 0;
        this.m.h = 450;
        this.m.w = 800;
        this.m.focus_point = 400;
        this.m.cx = 400;
        this.m.cy = 225;
        this.m.cz = 50;
        if (this.multion == 0) {
            this.intertrack.loadimod(false);
            this.intertrack.play();
        }
    }

    public void carselect(Control control, ContO[] contOArray, Mad mad, int n, int n2, boolean bl) {
        int n3;
        Object object;
        int n4;
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.fillRect(0, 0, 65, 450);
        this.rd.fillRect(735, 0, 65, 450);
        this.rd.fillRect(65, 0, 670, 25);
        this.rd.fillRect(65, 425, 670, 25);
        if (this.flatrstart == 6) {
            if (this.multion != 0 || this.testdrive == 1 || this.testdrive == 2) {
                this.rd.drawImage(this.carsbgc, 65, 25, null);
            } else {
                this.rd.drawImage(this.carsbg, 65, 25, null);
            }
        } else if (this.flatrstart <= 1) {
            this.drawSmokeCarsbg();
        } else {
            this.rd.setColor(new Color(255, 255, 255));
            this.rd.fillRect(65, 25, 670, 400);
            this.carsbginflex();
            this.flatrstart = 6;
        }
        this.rd.drawImage(this.selectcar, 321, 37, null);
        if (this.cfase == 3 || this.cfase == 7 || this.remi) {
            if (this.cd.lastload == 1) {
                this.rd.drawImage(this.ycmc, 337, 58, null);
            }
            if (this.cd.lastload == 2) {
                this.rd.drawImage(this.yac, 323, 58, null);
            }
        }
        if (this.cfase == 11) {
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            String string = "Top 20 Cars";
            n4 = this.cd.loadlist;
            object = "Weekly";
            while (n4 > 6) {
                n4 -= 6;
                if (((String)object).equals("Semi-Annual")) {
                    object = "Annual";
                }
                if (((String)object).equals("Monthly")) {
                    object = "Semi-Annual";
                }
                if (!((String)object).equals("Weekly")) continue;
                object = "Monthly";
            }
            if (n4 == 1) {
                string = "" + (String)object + " Top 20 Cars";
            }
            if (n4 == 2) {
                string = "" + (String)object + " Top 20 Class A Cars";
            }
            if (n4 == 3) {
                string = "" + (String)object + " Top 20 Class A & B Cars";
            }
            if (n4 == 4) {
                string = "" + (String)object + " Top 20 Class B Cars";
            }
            if (n4 == 5) {
                string = "" + (String)object + " Top 20 Class B & C Cars";
            }
            if (n4 == 6) {
                string = "" + (String)object + " Top 20 Class C Cars";
            }
            this.drawcs(69, string, 120, 176, 255, 3);
        }
        if (this.cfase == 101) {
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            this.drawcs(69, "" + this.cd.viewname + "'s account cars!", 220, 112, 33, 3);
        }
        if (!this.remi) {
            this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            contOArray[this.sc[0]].d(this.rd);
            this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        if (this.cfase == 8) {
            this.drawprom(150, 85);
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            this.drawcs(195, "Removing Car...", 0, 0, 0, 3);
            if (this.cd.action != 10) {
                if (this.cd.action != -10) {
                    this.cfase = 5;
                    this.showtf = false;
                } else {
                    this.cfase = 9;
                }
            }
        }
        if ((this.multion != 0 || this.testdrive == 1 || this.testdrive == 2) && this.lsc != this.sc[0]) {
            if (contOArray[this.sc[0]].xy != 0) {
                contOArray[this.sc[0]].xy = 0;
            }
            boolean bl2 = false;
            for (n4 = 0; n4 < contOArray[this.sc[0]].npl && !bl2; ++n4) {
                if (contOArray[this.sc[0]].p[n4].colnum != 1) continue;
                object = new float[3];
                Color.RGBtoHSB(contOArray[this.sc[0]].p[n4].c[0], contOArray[this.sc[0]].p[n4].c[1], contOArray[this.sc[0]].p[n4].c[2], (float[])object);
                this.arnp[0] = (float)object[0];
                this.arnp[1] = (float)object[1];
                this.arnp[2] = 1.0f - object[2];
                bl2 = true;
            }
            bl2 = false;
            for (n4 = 0; n4 < contOArray[this.sc[0]].npl && !bl2; ++n4) {
                if (contOArray[this.sc[0]].p[n4].colnum != 2) continue;
                object = new float[3];
                Color.RGBtoHSB(contOArray[this.sc[0]].p[n4].c[0], contOArray[this.sc[0]].p[n4].c[1], contOArray[this.sc[0]].p[n4].c[2], (float[])object);
                this.arnp[3] = (float)object[0];
                this.arnp[4] = (float)object[1];
                this.arnp[5] = 1.0f - object[2];
                bl2 = true;
            }
            object = Color.getHSBColor(this.arnp[0], this.arnp[1], 1.0f - this.arnp[2]);
            Color color = Color.getHSBColor(this.arnp[3], this.arnp[4], 1.0f - this.arnp[5]);
            for (n3 = 0; n3 < contOArray[this.sc[0]].npl; ++n3) {
                if (contOArray[this.sc[0]].p[n3].colnum == 1) {
                    contOArray[this.sc[0]].p[n3].hsb[0] = this.arnp[0];
                    contOArray[this.sc[0]].p[n3].hsb[1] = this.arnp[1];
                    contOArray[this.sc[0]].p[n3].hsb[2] = 1.0f - this.arnp[2];
                    contOArray[this.sc[0]].p[n3].c[0] = ((Color)object).getRed();
                    contOArray[this.sc[0]].p[n3].c[1] = ((Color)object).getGreen();
                    contOArray[this.sc[0]].p[n3].c[2] = ((Color)object).getBlue();
                    contOArray[this.sc[0]].p[n3].oc[0] = ((Color)object).getRed();
                    contOArray[this.sc[0]].p[n3].oc[1] = ((Color)object).getGreen();
                    contOArray[this.sc[0]].p[n3].oc[2] = ((Color)object).getBlue();
                }
                if (contOArray[this.sc[0]].p[n3].colnum != 2) continue;
                contOArray[this.sc[0]].p[n3].hsb[0] = this.arnp[3];
                contOArray[this.sc[0]].p[n3].hsb[1] = this.arnp[4];
                contOArray[this.sc[0]].p[n3].hsb[2] = 1.0f - this.arnp[5];
                contOArray[this.sc[0]].p[n3].c[0] = color.getRed();
                contOArray[this.sc[0]].p[n3].c[1] = color.getGreen();
                contOArray[this.sc[0]].p[n3].c[2] = color.getBlue();
                contOArray[this.sc[0]].p[n3].oc[0] = color.getRed();
                contOArray[this.sc[0]].p[n3].oc[1] = color.getGreen();
                contOArray[this.sc[0]].p[n3].oc[2] = color.getBlue();
            }
            this.lsc = this.sc[0];
        }
        int n5 = -1;
        n4 = 0;
        boolean bl3 = false;
        if (this.flipo == 0) {
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            int n6 = 0;
            if (this.flatrstart < 6) {
                n6 = 2;
            }
            if (!this.remi && (this.cfase != 10 || this.cd.action != 0 && this.cd.action < 14)) {
                if (this.cfase == 3 && this.cd.lastload == 2) {
                    this.app.mcars.move(400 - this.app.mcars.w / 2, 78);
                    this.app.mcars.show = true;
                    if (!this.app.mcars.getSelectedItem().equals(this.cd.names[this.sc[0]])) {
                        for (n3 = 16; n3 < this.cd.nlocars; ++n3) {
                            if (!this.cd.names[n3].equals(this.app.mcars.getSelectedItem())) continue;
                            n5 = n3;
                        }
                        if (n5 == -1) {
                            this.cfase = 5;
                            this.cd.action = 4;
                            this.cd.sparkactionloader();
                        }
                    }
                } else {
                    this.app.mcars.show = false;
                    String string = "";
                    if (this.cfase == 11) {
                        string = "N#" + (this.sc[0] - 35) + "  ";
                    }
                    if (this.aflk) {
                        this.drawcs(95 + n6, string + this.cd.names[this.sc[0]], 240, 240, 240, 3);
                        this.aflk = false;
                    } else {
                        this.drawcs(95, string + this.cd.names[this.sc[0]], 176, 176, 176, 3);
                        this.aflk = true;
                    }
                }
            } else {
                this.app.mcars.show = false;
            }
            contOArray[this.sc[0]].z = 950;
            if (this.sc[0] == 13) {
                contOArray[this.sc[0]].z = 1000;
            }
            contOArray[this.sc[0]].y = -34 - contOArray[this.sc[0]].grat;
            contOArray[this.sc[0]].x = 0;
            contOArray[this.sc[0]].xz = this.mouson >= 0 && this.mouson <= 3 ? (contOArray[this.sc[0]].xz += 2) : (contOArray[this.sc[0]].xz += 5);
            if (contOArray[this.sc[0]].xz > 360) {
                contOArray[this.sc[0]].xz -= 360;
            }
            contOArray[this.sc[0]].zy = 0;
            contOArray[this.sc[0]].wzy -= 10;
            if (contOArray[this.sc[0]].wzy < -30) {
                contOArray[this.sc[0]].wzy += 30;
            }
            if (!this.remi) {
                if (this.sc[0] != this.minsl) {
                    this.rd.drawImage(this.back[this.pback], 95, 275, null);
                }
                if (this.sc[0] != this.maxsl) {
                    this.rd.drawImage(this.next[this.pnext], 645, 275, null);
                }
            }
            if (this.gmode == 1) {
                if (this.sc[0] == 5 && this.unlocked[0] <= 2) {
                    n4 = 2;
                }
                if (this.sc[0] == 6 && this.unlocked[0] <= 4) {
                    n4 = 4;
                }
                if (this.sc[0] == 11 && this.unlocked[0] <= 6) {
                    n4 = 6;
                }
                if (this.sc[0] == 14 && this.unlocked[0] <= 8) {
                    n4 = 8;
                }
                if (this.sc[0] == 15 && this.unlocked[0] <= 10) {
                    n4 = 10;
                }
            }
            if (this.gmode == 2 && this.sc[0] >= 8 && this.unlocked[1] <= (this.sc[0] - 7) * 2) {
                n4 = (this.sc[0] - 7) * 2;
            }
            if (n4 != 0) {
                if (this.gatey == 300) {
                    for (int i = 0; i < 9; ++i) {
                        this.pgas[i] = false;
                        this.pgady[i] = 0;
                    }
                    this.pgas[0] = true;
                }
                for (int i = 0; i < 9; ++i) {
                    this.rd.drawImage(this.pgate, this.pgatx[i], this.pgaty[i] + this.pgady[i] - this.gatey, null);
                    if (this.flatrstart != 6) continue;
                    if (this.pgas[i]) {
                        int n7 = i;
                        this.pgady[n7] = this.pgady[n7] - (80 + 100 / (i + 1) - Math.abs(this.pgady[i])) / 3;
                        if (this.pgady[i] >= -(70 + 100 / (i + 1))) continue;
                        this.pgas[i] = false;
                        if (i == 8) continue;
                        this.pgas[i + 1] = true;
                        continue;
                    }
                    int n8 = i;
                    this.pgady[n8] = this.pgady[n8] + (80 + 100 / (i + 1) - Math.abs(this.pgady[i])) / 3;
                    if (this.pgady[i] <= 0) continue;
                    this.pgady[i] = 0;
                }
                if (this.gatey != 0) {
                    this.gatey -= 100;
                }
                if (this.flatrstart == 6) {
                    this.drawcs(355, "[ Car Locked ]", 210, 210, 210, 3);
                    this.drawcs(375, "This car unlocks when stage " + n4 + " is completed...", 255, 96, 0, 3);
                }
            } else {
                if (this.flatrstart == 6) {
                    String string;
                    if (this.cfase == 10) {
                        if (this.cd.action == 13) {
                            this.minsl = 36;
                            this.maxsl = this.cd.xnlocars - 1;
                            n5 = 36;
                            this.cd.action = 0;
                            this.cfase = 11;
                        }
                        if (this.cd.action == 12) {
                            int n9 = this.cd.loadlist;
                            String string2 = "Top 20 Cars";
                            string = "Weekly";
                            while (n9 > 6) {
                                n9 -= 6;
                                if (string.equals("Semi-Annual")) {
                                    string = "Annual";
                                }
                                if (string.equals("Monthly")) {
                                    string = "Semi-Annual";
                                }
                                if (!string.equals("Weekly")) continue;
                                string = "Monthly";
                            }
                            if (n9 == 1) {
                                string2 = "" + string + " Top 20 Cars";
                            }
                            if (n9 == 2) {
                                string2 = "" + string + " Top 20 Class A Cars";
                            }
                            if (n9 == 3) {
                                string2 = "" + string + " Top 20 Class A & B Cars";
                            }
                            if (n9 == 4) {
                                string2 = "" + string + " Top 20 Class B Cars";
                            }
                            if (n9 == 5) {
                                string2 = "" + string + " Top 20 Class B & C Cars";
                            }
                            if (n9 == 6) {
                                string2 = "" + string + " Top 20 Class C Cars";
                            }
                            this.drawprom(145, 170);
                            this.drawcs(195, "[  Loading " + string2 + "  ]", 0, 0, 0, 3);
                            if (this.cd.nl > 0 && this.cd.nl <= 20) {
                                this.drawcs(235, "Loading :  " + this.cd.loadnames[this.cd.nl - 1] + "", 0, 0, 0, 3);
                            }
                        }
                        if (this.cd.action == 11) {
                            this.drawprom(145, 170);
                            this.drawcs(195, "Loading List, Please Wait...", 0, 0, 0, 3);
                        }
                        if (this.cd.action == -1) {
                            this.drawprom(145, 170);
                            this.drawcs(195, "Failed to Load List.", 0, 0, 0, 3);
                            this.drawcs(225, "Unknown Error.  Please try again later.", 0, 0, 0, 3);
                            if (this.drawcarb(true, null, "   OK   ", 371, 255, n, n2, bl)) {
                                this.cd.action = 0;
                                this.cfase = this.basefase;
                            }
                        }
                        if (this.cd.action == 0 || this.cd.action == 14 || this.cd.action == 15 || this.cd.action == 16 || this.cd.action == 17) {
                            this.drawprom(65, 250);
                            if (this.drawcarb(true, null, " X ", 557, 70, n, n2, bl)) {
                                this.cd.action = 0;
                                this.cfase = this.basefase;
                            }
                            this.drawcs(305, "The lists get updated every 24 hours!", 0, 0, 0, 3);
                            if (this.cd.action == 14 || this.cd.action == 15 || this.cd.action == 16 || this.cd.action == 17) {
                                if (!bl && this.cntflock == 20) {
                                    this.cntflock = 0;
                                }
                                if (this.cd.action == 14) {
                                    this.drawcs(91, "Weekly Top 20 Cars", 0, 0, 0, 3);
                                }
                                if (this.cd.action == 15) {
                                    this.drawcs(91, "Monthly Top 20 Cars", 0, 0, 0, 3);
                                }
                                if (this.cd.action == 16) {
                                    this.drawcs(91, "Semi-Annual Top 20 Cars", 0, 0, 0, 3);
                                }
                                if (this.cd.action == 17) {
                                    this.drawcs(91, "Annual Top 20 Cars", 0, 0, 0, 3);
                                }
                                if (this.drawcarb(true, null, "   All Cars, All Classes   ", 318, 105, n, n2, bl) && this.cntflock == 0) {
                                    this.cd.loadlist = 1 + (this.cd.action - 14) * 6;
                                    this.cd.action = 11;
                                    this.cd.sparkactionloader();
                                }
                                if (this.drawcarb(true, null, "Class A Cars", 337, 135, n, n2, bl) && this.cntflock == 0) {
                                    this.cd.loadlist = 2 + (this.cd.action - 14) * 6;
                                    this.cd.action = 11;
                                    this.cd.sparkactionloader();
                                }
                                if (this.drawcarb(true, null, "Class A & B Cars", 337, 165, n, n2, bl) && this.cntflock == 0) {
                                    this.cd.loadlist = 3 + (this.cd.action - 14) * 6;
                                    this.cd.action = 11;
                                    this.cd.sparkactionloader();
                                }
                                if (this.drawcarb(true, null, "Class B Cars", 337, 195, n, n2, bl) && this.cntflock == 0) {
                                    this.cd.loadlist = 4 + (this.cd.action - 14) * 6;
                                    this.cd.action = 11;
                                    this.cd.sparkactionloader();
                                }
                                if (this.drawcarb(true, null, "Class B & C Cars", 337, 225, n, n2, bl) && this.cntflock == 0) {
                                    this.cd.loadlist = 5 + (this.cd.action - 14) * 6;
                                    this.cd.action = 11;
                                    this.cd.sparkactionloader();
                                }
                                if (this.drawcarb(true, null, "Class C Cars", 337, 255, n, n2, bl) && this.cntflock == 0) {
                                    this.cd.loadlist = 6 + (this.cd.action - 14) * 6;
                                    this.cd.action = 11;
                                    this.cd.sparkactionloader();
                                }
                            }
                            if (this.cd.action == 0) {
                                this.drawcs(91, "Top 20 Most Added Public Custom Cars", 0, 0, 0, 3);
                                if (this.drawcarb(true, null, "  Weekly Top 20  ", 338, 125, n, n2, bl)) {
                                    this.cd.action = 14;
                                }
                                if (this.drawcarb(true, null, "  Monthly Top 20  ", 337, 165, n, n2, bl)) {
                                    this.cd.action = 15;
                                }
                                if (this.drawcarb(true, null, "  Semi-Annual Top 20  ", 321, 205, n, n2, bl)) {
                                    this.cd.action = 16;
                                }
                                if (this.drawcarb(true, null, "  Annual Top 20  ", 339, 245, n, n2, bl)) {
                                    this.cd.action = 17;
                                }
                                if (this.cntflock != 20) {
                                    this.cntflock = 20;
                                }
                            }
                        }
                    }
                    if (this.cfase == 100) {
                        if (this.cd.action == -1) {
                            this.drawprom(145, 170);
                            this.drawcs(195, "Failed to Load List.", 0, 0, 0, 3);
                            this.drawcs(225, "Unknown Error.  Please try again later.", 0, 0, 0, 3);
                            if (this.drawcarb(true, null, "   OK   ", 371, 255, n, n2, bl)) {
                                this.cfase = this.sc[0] >= 16 && this.cd.lastload == 2 && this.sc[0] < 36 ? 3 : 0;
                            }
                        }
                        if (this.cd.action == -2) {
                            this.drawprom(145, 170);
                            this.drawcs(195, "No account cars found.", 0, 0, 0, 3);
                            this.drawcs(225, "" + this.cd.viewname + " does not have any published or added cars.", 0, 0, 0, 3);
                            if (this.drawcarb(true, null, "   OK   ", 371, 255, n, n2, bl)) {
                                this.cfase = this.sc[0] >= 16 && this.cd.lastload == 2 && this.sc[0] < 36 ? 3 : 0;
                            }
                        }
                        if (this.cd.action == 100) {
                            this.cd.action = 101;
                            this.cd.sparkactionloader();
                        }
                        if (this.cd.action == 101) {
                            this.drawprom(145, 170);
                            this.drawcs(195, "Loading " + this.cd.viewname + "'s account cars, please wait...", 0, 0, 0, 3);
                        }
                        if (this.cd.action == 102) {
                            this.drawprom(145, 170);
                            this.drawcs(195, "Loading " + this.cd.viewname + "'s account cars, please wait...", 0, 0, 0, 3);
                            if (this.cd.nl > 0 && this.cd.nl <= 20) {
                                this.drawcs(235, "Loading :  " + this.cd.loadnames[this.cd.nl - 1] + "", 0, 0, 0, 3);
                            }
                        }
                        if (this.cd.action == 103) {
                            this.minsl = 36;
                            this.maxsl = this.cd.xnlocars - 1;
                            n5 = 36;
                            this.cd.action = 0;
                            this.cfase = 101;
                        }
                    }
                    if (this.cfase == 0 && this.testdrive != 1 && this.testdrive != 2 && this.gmode == 0) {
                        int n10 = 95;
                        int n11 = 5;
                        if (this.multion != 0) {
                            n10 = 185;
                            n11 = 0;
                        }
                        if (this.multion == 0 && this.drawcarb(false, this.cmc, "", 95, 70, n, n2, bl)) {
                            if (this.cd.lastload != 1) {
                                this.cfase = 1;
                            } else {
                                this.minsl = 16;
                                this.maxsl = this.cd.nlcars - 1;
                                n5 = 16;
                                this.cfase = 3;
                            }
                        }
                        if (this.drawcarb(false, this.myc, "", n10, 105 + n11, n, n2, bl)) {
                            if (this.cd.lastload != 2) {
                                this.cfase = 5;
                                this.showtf = false;
                                if (!this.logged) {
                                    this.cd.action = 0;
                                    this.cd.reco = -2;
                                    this.tcnt = 5;
                                    this.cntflock = 0;
                                } else {
                                    this.cd.action = 3;
                                    this.cd.sparkactionloader();
                                }
                            } else {
                                this.minsl = 16;
                                this.maxsl = this.cd.nlocars - 1;
                                n5 = this.onmsc >= this.minsl && this.onmsc <= this.maxsl ? this.onmsc : 16;
                                this.cfase = 3;
                            }
                        }
                        if ((this.multion == 0 || this.onjoin == -1) && this.drawcarb(false, this.top20s, "", n10, (n10 - 95) / 7 + 25 + n11, n, n2, bl)) {
                            this.cd.action = 0;
                            this.cfase = 10;
                        }
                        if (this.remi) {
                            this.remi = false;
                        }
                    }
                    if (this.cfase == -1) {
                        if (this.autolog) {
                            this.autolog = false;
                            this.cfase = 5;
                            this.cd.action = 1;
                            this.cd.sparkactionloader();
                        } else if (this.cd.lastload != 2) {
                            this.cfase = 5;
                            this.showtf = false;
                            if (!this.logged) {
                                this.cd.action = 0;
                                this.cd.reco = -2;
                                this.tcnt = 5;
                                this.cntflock = 0;
                            } else {
                                this.cd.action = 3;
                                this.cd.sparkactionloader();
                            }
                        } else {
                            this.minsl = 16;
                            this.maxsl = this.cd.nlocars - 1;
                            n5 = this.onmsc >= this.minsl && this.onmsc <= this.maxsl ? this.onmsc : 16;
                            this.cfase = 3;
                        }
                    }
                    if (this.cfase == 9) {
                        this.drawprom(145, 95);
                        this.drawcs(175, "Failed to remove car.  Unkown Error.  Try again laster.", 0, 0, 0, 3);
                        if (this.drawcarb(true, null, "   OK   ", 371, 195, n, n2, bl)) {
                            this.minsl = 16;
                            this.maxsl = this.cd.nlocars - 1;
                            n5 = this.onmsc >= this.minsl && this.onmsc <= this.maxsl ? this.onmsc : 16;
                            this.cfase = 3;
                        }
                    }
                    if (this.cfase == 7) {
                        if (this.app.mycar.isShowing()) {
                            this.app.mycar.hide();
                        }
                        this.drawprom(145, 95);
                        this.drawcs(175, "Remove this car from your account?", 0, 0, 0, 3);
                        if (this.drawcarb(true, null, " Yes ", 354, 195, n, n2, bl)) {
                            this.remi = true;
                            this.minsl = 0;
                            this.maxsl = 15;
                            n5 = 15;
                            this.cfase = 8;
                            this.onmsc = this.sc[0];
                            this.cd.ac = this.sc[0];
                            this.cd.action = 10;
                            this.cd.sparkactionloader();
                        }
                        if (this.drawcarb(true, null, " No ", 408, 195, n, n2, bl)) {
                            this.cfase = 3;
                        }
                    }
                    if (this.cfase == 3 && n5 == -1) {
                        int n12 = 95;
                        int n13 = 5;
                        if (this.multion != 0) {
                            n12 = 185;
                            n13 = 0;
                        }
                        if (this.drawcarb(false, this.gac, "", n12, 105 + n13, n, n2, bl)) {
                            this.minsl = 0;
                            this.maxsl = 15;
                            n5 = this.onmsc >= this.minsl && this.onmsc <= this.maxsl ? this.onmsc : 15;
                            this.cfase = 0;
                        }
                        if (this.multion == 0) {
                            if (!this.app.openm) {
                                if (!this.app.mycar.isShowing()) {
                                    this.app.mycar.show();
                                    this.app.mycar.setState(this.cd.include[this.sc[0] - 16]);
                                }
                            } else {
                                this.app.mycar.hide();
                            }
                            this.rd.setColor(new Color(198, 179, 129));
                            this.rd.fillRoundRect(305, 302, 190, 24, 7, 20);
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawRoundRect(305, 302, 190, 24, 7, 20);
                            this.app.movefield(this.app.mycar, 334, 306, 150, 17);
                            if (this.app.mycar.getState() != this.cd.include[this.sc[0] - 16]) {
                                this.cd.include[this.sc[0] - 16] = this.app.mycar.getState();
                                this.app.requestFocus();
                            }
                        }
                        if ((this.multion == 0 || this.onjoin == -1) && this.drawcarb(false, this.top20s, "", n12, (n12 - 95) / 7 + 25 + n13, n, n2, bl)) {
                            this.cd.action = 0;
                            this.cfase = 10;
                            if (this.app.mycar.isShowing()) {
                                this.app.mycar.hide();
                            }
                        }
                        if (this.cd.lastload == 2) {
                            if (this.drawcarb(true, null, "X", 567, 135, n, n2, bl)) {
                                this.cfase = 7;
                            }
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(new Color(0, 0, 0));
                            if (!this.cd.createdby[this.sc[0] - 16].equals(this.nickname)) {
                                bl3 = this.clink(this.cd.createdby[this.sc[0] - 16], n, n2, bl);
                            } else {
                                this.rd.drawString("Created by You", 241, 160);
                            }
                        }
                        if (this.remi) {
                            this.remi = false;
                        }
                        if (this.noclass) {
                            this.drawprom(200, 95);
                            this.rd.setFont(new Font("Arial", 1, 13));
                            this.ftm = this.rd.getFontMetrics();
                            string = "Class C";
                            if (this.ontyp == 2) {
                                string = "Class B or C";
                            }
                            if (this.ontyp == 3) {
                                string = "Class B";
                            }
                            if (this.ontyp == 4) {
                                string = "Class A or B";
                            }
                            if (this.ontyp == 5) {
                                string = "Class A";
                            }
                            this.drawcs(230, "You do not have a " + string + " car in your account cars.", 0, 0, 0, 3);
                            if (this.drawcarb(true, null, "   OK   ", 371, 250, n, n2, bl)) {
                                this.noclass = false;
                            }
                        }
                    }
                    if ((this.cfase == 11 || this.cfase == 101) && n5 == -1) {
                        if (this.cd.action == -9) {
                            this.drawprom(145, 95);
                            this.drawcs(175, "Unknown error!  Failed to add car.  Try again later.", 0, 0, 0, 3);
                            if (this.drawcarb(true, null, " OK ", 379, 195, n, n2, bl)) {
                                this.cd.action = 0;
                            }
                        }
                        if (this.cd.action == -8) {
                            this.drawprom(145, 95);
                            this.drawcs(175, "Failed.  You already have 20 cars in your account!", 0, 0, 0, 3);
                            if (this.drawcarb(true, null, " OK ", 379, 195, n, n2, bl)) {
                                this.cd.action = 0;
                            }
                        }
                        if (this.cd.action == -7) {
                            this.drawprom(145, 95);
                            this.drawcs(175, "You already have this car!", 0, 0, 0, 3);
                            if (this.drawcarb(true, null, " OK ", 379, 195, n, n2, bl)) {
                                this.cd.action = 0;
                            }
                        }
                        if (this.cd.action == 7) {
                            this.drawprom(145, 95);
                            this.drawcs(175, "" + this.cd.names[this.cd.ac] + " has been successfully added to your cars!", 0, 0, 0, 3);
                            if (this.drawcarb(true, null, " OK ", 379, 195, n, n2, bl)) {
                                this.cd.action = 0;
                            }
                        }
                        if (this.cd.action == 6) {
                            this.drawprom(145, 95);
                            this.drawcs(195, "Adding " + this.cd.names[this.cd.ac] + " to your cars...", 0, 0, 0, 3);
                        }
                        int n14 = 95;
                        int n15 = 5;
                        if (this.multion != 0) {
                            n14 = 185;
                            n15 = 0;
                        }
                        if (this.onmsc >= 16 && (this.cd.lastload == 2 || this.cd.lastload == -2)) {
                            if (this.drawcarb(false, this.myc, "", n14, 105 + n15, n, n2, bl)) {
                                if (this.cd.lastload != 2) {
                                    this.cfase = 5;
                                    this.showtf = false;
                                    if (!this.logged) {
                                        this.cd.action = 0;
                                        this.cd.reco = -2;
                                        this.tcnt = 5;
                                        this.cntflock = 0;
                                    } else {
                                        this.cd.action = 3;
                                        this.cd.sparkactionloader();
                                    }
                                } else {
                                    this.cd.action = 0;
                                    this.minsl = 16;
                                    this.maxsl = this.cd.nlocars - 1;
                                    n5 = this.onmsc >= this.minsl && this.onmsc <= this.maxsl ? this.onmsc : 16;
                                    this.cfase = 3;
                                }
                                this.app.moused = false;
                            }
                        } else if (this.drawcarb(false, this.gac, "", n14, 105 + n15, n, n2, bl)) {
                            this.cd.action = 0;
                            this.minsl = 0;
                            this.maxsl = 15;
                            n5 = this.onmsc >= this.minsl && this.onmsc <= this.maxsl ? this.onmsc : 15;
                            this.cfase = 0;
                            this.app.moused = false;
                        }
                        if (this.drawcarb(false, this.top20s, "", n14, (n14 - 95) / 7 + 25 + n15, n, n2, bl)) {
                            this.cd.action = 0;
                            this.cfase = 10;
                        }
                        if (this.cd.action == 0) {
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(new Color(0, 0, 0));
                            if (!this.cd.createdby[this.sc[0] - 16].equals(this.nickname)) {
                                bl3 = this.clink(this.cd.createdby[this.sc[0] - 16], n, n2, bl);
                            } else {
                                this.rd.drawString("Created by You", 241, 160);
                            }
                            if (this.cfase != 101) {
                                this.rd.setFont(new Font("Arial", 1, 11));
                                this.rd.drawString("Added by :  " + this.cd.adds[this.sc[0] - 36] + " Players", 241, 180);
                            }
                        }
                    }
                    if (this.cfase == 5) {
                        this.drawprom(145, 170);
                        if (this.cd.action == 5) {
                            this.minsl = 16;
                            this.maxsl = this.cd.nlocars - 1;
                            if (this.cd.inslot != -1) {
                                this.onmsc = this.cd.inslot;
                                this.cd.inslot = -1;
                            }
                            n5 = this.onmsc >= this.minsl && this.onmsc <= this.maxsl ? this.onmsc : 16;
                            this.cfase = 3;
                        }
                        if (this.cd.action == 4) {
                            this.drawcs(195, "[  Loading Car  ]", 0, 0, 0, 3);
                            this.drawcs(235, "Loading :  " + this.app.mcars.getSelectedItem() + "", 0, 0, 0, 3);
                        }
                        if (this.cd.action == -2) {
                            this.drawcs(195, "Unknown Connection Error", 0, 0, 0, 3);
                            this.drawcs(225, "Failed to connect to server, try again later!", 0, 0, 0, 3);
                            if (this.drawcarb(true, null, "   OK   ", 371, 255, n, n2, bl)) {
                                this.cfase = 0;
                            }
                        }
                        if (this.cd.action == -1) {
                            this.drawcs(195, "No published cars found...", 0, 0, 0, 3);
                            this.drawcs(225, "You have no added cars to your account yet!", 0, 0, 0, 3);
                            if (this.drawcarb(true, null, "   OK   ", 371, 255, n, n2, bl)) {
                                this.cfase = 0;
                            }
                        }
                        if (this.cd.action == 2 || this.cd.action == 3) {
                            this.drawcs(195, "Loading your Account Cars list...", 0, 0, 0, 3);
                            if (this.cd.action == 2) {
                                this.backlog = this.nickname = this.app.tnick.getText();
                                this.nickey = this.cd.tnickey;
                                this.clan = this.cd.tclan;
                                this.clankey = this.cd.tclankey;
                                this.app.setloggedcookie();
                                this.logged = true;
                                this.gotlog = true;
                                if (this.cd.reco == 0) {
                                    this.acexp = 0;
                                }
                                if (this.cd.reco > 10) {
                                    this.acexp = this.cd.reco - 10;
                                }
                                if (this.cd.reco == 3) {
                                    this.acexp = -1;
                                }
                                if (this.cd.reco == 111) {
                                    this.acexp = !this.backlog.toLowerCase().equals(this.nickname.toLowerCase()) ? -3 : 0;
                                }
                                if (this.basefase == 0) {
                                    this.cd.action = 3;
                                }
                                if (this.basefase == 11) {
                                    this.cd.action = 6;
                                    this.cfase = 11;
                                }
                                if (this.basefase == 101) {
                                    this.cd.action = 6;
                                    this.cfase = 101;
                                }
                            }
                        }
                        if (this.cd.action == 1) {
                            this.drawcs(195, "Logging in to your account...", 0, 0, 0, 3);
                        }
                        if (this.cd.action == 0) {
                            if (this.cd.reco == -5) {
                                this.drawcs(171, "Login to Add this Car to your Account", 0, 0, 0, 3);
                            }
                            if (this.cd.reco == -2) {
                                this.drawcs(171, "Login to Retrieve your Account Cars", 0, 0, 0, 3);
                            }
                            if (this.cd.reco == -1) {
                                this.drawcs(171, "Unable to connect to server, try again later!", 0, 8, 0, 3);
                            }
                            if (this.cd.reco == 1) {
                                this.drawcs(171, "Sorry.  The Nickname you have entered is incorrect.", 0, 0, 0, 3);
                            }
                            if (this.cd.reco == 2) {
                                this.drawcs(171, "Sorry.  The Password you have entered is incorrect.", 0, 0, 0, 3);
                            }
                            if (this.cd.reco == -167 || this.cd.reco == -177) {
                                if (this.cd.reco == -167) {
                                    this.backlog = this.nickname = this.app.tnick.getText();
                                    this.cd.reco = -177;
                                }
                                this.drawcs(171, "You are currently using a trial account.", 0, 0, 0, 3);
                            }
                            if (this.cd.reco == -3 && (this.tcnt % 3 != 0 || this.tcnt > 20)) {
                                this.drawcs(171, "Please enter your Nickname!", 0, 0, 0, 3);
                            }
                            if (this.cd.reco == -4 && (this.tcnt % 3 != 0 || this.tcnt > 20)) {
                                this.drawcs(171, "Please enter your Password!", 0, 0, 0, 3);
                            }
                            if (!this.showtf) {
                                this.app.tnick.show();
                                this.app.tnick.setBackground(new Color(206, 237, 255));
                                if (this.cd.reco != 1) {
                                    if (this.cd.reco != 2) {
                                        this.app.tnick.setText(this.nickname);
                                    }
                                    this.app.tnick.setForeground(new Color(0, 0, 0));
                                } else {
                                    this.app.tnick.setForeground(new Color(255, 0, 0));
                                }
                                this.app.tnick.requestFocus();
                                this.app.tpass.show();
                                this.app.tpass.setBackground(new Color(206, 237, 255));
                                if (this.cd.reco != 2) {
                                    if (!this.autolog) {
                                        this.app.tpass.setText("");
                                    }
                                    this.app.tpass.setForeground(new Color(0, 0, 0));
                                } else {
                                    this.app.tpass.setForeground(new Color(255, 0, 0));
                                }
                                if (!this.app.tnick.getText().equals("") && this.cd.reco != 1) {
                                    this.app.tpass.requestFocus();
                                }
                                this.showtf = true;
                            }
                            this.rd.drawString("Nickname:", 376 - this.ftm.stringWidth("Nickname:") - 14, 201);
                            this.rd.drawString("Password:", 376 - this.ftm.stringWidth("Password:") - 14, 231);
                            this.app.movefieldd(this.app.tnick, 376, 185, 129, 23, true);
                            this.app.movefieldd(this.app.tpass, 376, 215, 129, 23, true);
                            if (this.tcnt < 30) {
                                ++this.tcnt;
                                if (this.tcnt == 30) {
                                    if (this.cd.reco == 2) {
                                        this.app.tpass.setText("");
                                    }
                                    this.app.tnick.setForeground(new Color(0, 0, 0));
                                    this.app.tpass.setForeground(new Color(0, 0, 0));
                                }
                            }
                            if (this.cd.reco != -177) {
                                if (this.drawcarb(true, null, "       Login       ", 347, 247, n, n2, bl) && this.tcnt > 5) {
                                    this.tcnt = 0;
                                    if (!this.app.tnick.getText().equals("") && !this.app.tpass.getText().equals("")) {
                                        this.autolog = false;
                                        this.app.tnick.hide();
                                        this.app.tpass.hide();
                                        this.app.requestFocus();
                                        this.cd.action = 1;
                                        this.cd.sparkactionloader();
                                    } else {
                                        if (this.app.tpass.getText().equals("")) {
                                            this.cd.reco = -4;
                                        }
                                        if (this.app.tnick.getText().equals("")) {
                                            this.cd.reco = -3;
                                        }
                                    }
                                }
                            } else if (this.drawcarb(true, null, "  Upgrade to have your own cars!  ", 284, 247, n, n2, bl) && this.cntflock == 0) {
                                this.app.editlink(this.nickname, true);
                                this.cntflock = 100;
                            }
                            if (this.drawcarb(true, null, "  Cancel  ", 409, 282, n, n2, bl)) {
                                this.app.tnick.hide();
                                this.app.tpass.hide();
                                this.app.requestFocus();
                                this.cfase = this.basefase;
                            }
                            if (this.drawcarb(true, null, "  Register!  ", 316, 282, n, n2, bl)) {
                                if (this.cntflock == 0) {
                                    this.app.reglink();
                                    this.cntflock = 100;
                                }
                            } else if (this.cntflock != 0) {
                                --this.cntflock;
                            }
                        }
                    }
                    if (this.cfase == 4) {
                        this.drawprom(145, 150);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Failed to find any ready car in your \u2018mycars\u2019 folder!", 215, 175);
                        this.rd.drawString("Please \u2018Test Drive\u2019 your cars in the Car Maker to make", 215, 215);
                        this.rd.drawString("sure they are ready.", 215, 235);
                        if (this.drawcarb(true, null, "   OK   ", 371, 255, n, n2, bl)) {
                            this.cfase = 0;
                        }
                    }
                    if (this.cfase == 2) {
                        this.drawprom(165, 70);
                        this.drawcs(205, "Loading Car Maker Cars...", 0, 0, 0, 3);
                        this.app.repaint();
                        this.cd.loadcarmaker();
                        if (this.cd.nlcars > 16) {
                            this.minsl = 16;
                            this.maxsl = this.cd.nlcars - 1;
                            n5 = 16;
                            this.cfase = 3;
                        } else {
                            this.cfase = 4;
                        }
                    }
                    if (this.cfase == 1) {
                        this.drawprom(145, 170);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("The game will now load all the cars that can be loaded", 215, 170);
                        this.rd.drawString("from your \u2018mycars\u2019 folder.", 215, 190);
                        this.rd.drawString("If a car is not loaded, then it is not ready (not finished).", 215, 210);
                        this.rd.drawString("Perform a \u2018Test Drive\u2019 on any car to see if it is ready or not.", 215, 230);
                        this.rd.drawString("The maximum number of cars that can be loaded is  40 !", 215, 260);
                        if (this.drawcarb(true, null, "   OK   ", 371, 275, n, n2, bl)) {
                            this.cfase = 2;
                        }
                    }
                    this.rd.setFont(new Font("Arial", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(new Color(181, 120, 40));
                    this.rd.drawString("Top Speed:", 98, 343);
                    this.rd.drawImage(this.statb, 162, 337, null);
                    this.rd.drawString("Acceleration:", 88, 358);
                    this.rd.drawImage(this.statb, 162, 352, null);
                    this.rd.drawString("Handling:", 110, 373);
                    this.rd.drawImage(this.statb, 162, 367, null);
                    this.rd.drawString("Stunts:", 495, 343);
                    this.rd.drawImage(this.statb, 536, 337, null);
                    this.rd.drawString("Strength:", 483, 358);
                    this.rd.drawImage(this.statb, 536, 352, null);
                    this.rd.drawString("Endurance:", 473, 373);
                    this.rd.drawImage(this.statb, 536, 367, null);
                    this.rd.setColor(new Color(0, 0, 0));
                    float f = (float)(this.cd.swits[this.sc[0]][2] - 220) / 90.0f;
                    if ((double)f < 0.2) {
                        f = 0.2f;
                    }
                    this.rd.fillRect((int)(162.0f + 156.0f * f), 337, (int)(156.0f * (1.0f - f) + 1.0f), 7);
                    f = this.cd.acelf[this.sc[0]][1] * this.cd.acelf[this.sc[0]][0] * this.cd.acelf[this.sc[0]][2] * this.cd.grip[this.sc[0]] / 7700.0f;
                    if (f > 1.0f) {
                        f = 1.0f;
                    }
                    this.rd.fillRect((int)(162.0f + 156.0f * f), 352, (int)(156.0f * (1.0f - f) + 1.0f), 7);
                    f = this.cd.dishandle[this.sc[0]];
                    this.rd.fillRect((int)(162.0f + 156.0f * f), 367, (int)(156.0f * (1.0f - f) + 1.0f), 7);
                    f = ((float)this.cd.airc[this.sc[0]] * this.cd.airs[this.sc[0]] * this.cd.bounce[this.sc[0]] + 28.0f) / 139.0f;
                    if (f > 1.0f) {
                        f = 1.0f;
                    }
                    this.rd.fillRect((int)(536.0f + 156.0f * f), 337, (int)(156.0f * (1.0f - f) + 1.0f), 7);
                    float f2 = 0.5f;
                    f = (this.cd.moment[this.sc[0]] + f2) / 2.6f;
                    if (f > 1.0f) {
                        f = 1.0f;
                    }
                    this.rd.fillRect((int)(536.0f + 156.0f * f), 352, (int)(156.0f * (1.0f - f) + 1.0f), 7);
                    f = this.cd.outdam[this.sc[0]];
                    this.rd.fillRect((int)(536.0f + 156.0f * f), 367, (int)(156.0f * (1.0f - f) + 1.0f), 7);
                    this.rd.drawImage(this.statbo, 162, 337, null);
                    this.rd.drawImage(this.statbo, 162, 352, null);
                    this.rd.drawImage(this.statbo, 162, 367, null);
                    this.rd.drawImage(this.statbo, 536, 337, null);
                    this.rd.drawImage(this.statbo, 536, 352, null);
                    this.rd.drawImage(this.statbo, 536, 367, null);
                    if (this.multion != 0 || this.testdrive == 1 || this.testdrive == 2) {
                        int n16;
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        string = "Class C";
                        if (this.cd.cclass[this.sc[0]] == 1) {
                            string = "Class B & C";
                        }
                        if (this.cd.cclass[this.sc[0]] == 2) {
                            string = "Class B";
                        }
                        if (this.cd.cclass[this.sc[0]] == 3) {
                            string = "Class A & B";
                        }
                        if (this.cd.cclass[this.sc[0]] == 4) {
                            string = "Class A";
                        }
                        if (this.kbload < 7) {
                            this.rd.setColor(new Color(0, 0, 0));
                            ++this.kbload;
                        } else {
                            this.rd.setColor(new Color(176, 41, 0));
                            this.kbload = 0;
                        }
                        if (this.cfase != 10 || this.cd.action != 0 && this.cd.action < 14) {
                            this.rd.drawString(string, 549 - this.ftm.stringWidth(string) / 2, 95);
                        }
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("1st Color", 100, 55);
                        this.rd.drawString("2nd Color", 649, 55);
                        this.rd.setFont(new Font("Arial", 1, 10));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Hue  | ", 97, 70);
                        this.rd.drawImage(this.brt, 137, 63, null);
                        this.rd.drawString("Hue  | ", 647, 70);
                        this.rd.drawImage(this.brt, 687, 63, null);
                        this.rd.drawString("Intensity", 121, 219);
                        this.rd.drawString("Intensity", 671, 219);
                        this.rd.drawString("Reset", 110, 257);
                        this.rd.drawString("Reset", 660, 257);
                        for (n16 = 0; n16 < 161; ++n16) {
                            this.rd.setColor(Color.getHSBColor((float)((double)n16 * 0.00625), 1.0f, 1.0f));
                            this.rd.drawLine(102, 75 + n16, 110, 75 + n16);
                            if (n16 <= 128) {
                                this.rd.setColor(Color.getHSBColor(1.0f, 0.0f, (float)(1.0 - (double)n16 * 0.00625)));
                                this.rd.drawLine(137, 75 + n16, 145, 75 + n16);
                            }
                            this.rd.setColor(Color.getHSBColor((float)((double)n16 * 0.00625), 1.0f, 1.0f));
                            this.rd.drawLine(652, 75 + n16, 660, 75 + n16);
                            if (n16 > 128) continue;
                            this.rd.setColor(Color.getHSBColor(1.0f, 0.0f, (float)(1.0 - (double)n16 * 0.00625)));
                            this.rd.drawLine(687, 75 + n16, 695, 75 + n16);
                        }
                        for (n16 = 0; n16 < 40; ++n16) {
                            this.rd.setColor(Color.getHSBColor(this.arnp[0], (float)((double)n16 * 0.025), 1.0f - this.arnp[2]));
                            this.rd.drawLine(121 + n16, 224, 121 + n16, 230);
                            this.rd.setColor(Color.getHSBColor(this.arnp[3], (float)((double)n16 * 0.025), 1.0f - this.arnp[5]));
                            this.rd.drawLine(671 + n16, 224, 671 + n16, 230);
                        }
                        this.rd.drawImage(this.arn, 110, 71 + (int)(this.arnp[0] * 160.0f), null);
                        this.rd.drawImage(this.arn, 145, 71 + (int)(this.arnp[2] * 160.0f), null);
                        this.rd.drawImage(this.arn, 660, 71 + (int)(this.arnp[3] * 160.0f), null);
                        this.rd.drawImage(this.arn, 695, 71 + (int)(this.arnp[5] * 160.0f), null);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.fillRect(120 + (int)(this.arnp[1] * 40.0f), 222, 3, 3);
                        this.rd.drawLine(121 + (int)(this.arnp[1] * 40.0f), 224, 121 + (int)(this.arnp[1] * 40.0f), 230);
                        this.rd.fillRect(120 + (int)(this.arnp[1] * 40.0f), 230, 3, 3);
                        this.rd.fillRect(670 + (int)(this.arnp[4] * 40.0f), 222, 3, 3);
                        this.rd.drawLine(671 + (int)(this.arnp[4] * 40.0f), 224, 671 + (int)(this.arnp[4] * 40.0f), 230);
                        this.rd.fillRect(670 + (int)(this.arnp[4] * 40.0f), 230, 3, 3);
                        if (bl) {
                            if (this.mouson == -1) {
                                if (n > 96 && n < 152 && n2 > 248 && n2 < 258) {
                                    float[] fArray = new float[3];
                                    Color.RGBtoHSB(contOArray[this.sc[0]].fcol[0], contOArray[this.sc[0]].fcol[1], contOArray[this.sc[0]].fcol[2], fArray);
                                    this.arnp[0] = fArray[0];
                                    this.arnp[1] = fArray[1];
                                    this.arnp[2] = 1.0f - fArray[2];
                                }
                                if (n > 646 && n < 702 && n2 > 248 && n2 < 258) {
                                    float[] fArray = new float[3];
                                    Color.RGBtoHSB(contOArray[this.sc[0]].scol[0], contOArray[this.sc[0]].scol[1], contOArray[this.sc[0]].scol[2], fArray);
                                    this.arnp[3] = fArray[0];
                                    this.arnp[4] = fArray[1];
                                    this.arnp[5] = 1.0f - fArray[2];
                                }
                                this.mouson = -2;
                                if (n > 119 && n < 163 && n2 > 222 && n2 < 232) {
                                    this.mouson = 1;
                                }
                                if (n > 669 && n < 713 && n2 > 222 && n2 < 232) {
                                    this.mouson = 4;
                                }
                                if (n > 98 && n < 122 && n2 > 69 && n2 < 241 && this.mouson == -2) {
                                    this.mouson = 0;
                                }
                                if (n > 133 && n < 157 && n2 > 69 && n2 < 209 && this.mouson == -2) {
                                    this.mouson = 2;
                                }
                                if (n > 648 && n < 672 && n2 > 69 && n2 < 241 && this.mouson == -2) {
                                    this.mouson = 3;
                                }
                                if (n > 683 && n < 707 && n2 > 69 && n2 < 209 && this.mouson == -2) {
                                    this.mouson = 5;
                                }
                            }
                        } else if (this.mouson != -1) {
                            this.mouson = -1;
                        }
                        if (this.mouson == 0 || this.mouson == 2 || this.mouson == 3 || this.mouson == 5) {
                            this.arnp[this.mouson] = (float)((double)((float)n2 - 75.0f) * 0.00625);
                            if (this.mouson == 2 || this.mouson == 5) {
                                if ((double)this.arnp[this.mouson] > 0.8) {
                                    this.arnp[this.mouson] = 0.8f;
                                }
                            } else if (this.arnp[this.mouson] > 1.0f) {
                                this.arnp[this.mouson] = 1.0f;
                            }
                            if (this.arnp[this.mouson] < 0.0f) {
                                this.arnp[this.mouson] = 0.0f;
                            }
                        }
                        if (this.mouson == 1) {
                            this.arnp[this.mouson] = (float)((double)((float)n - 121.0f) * 0.025);
                            if (this.arnp[this.mouson] > 1.0f) {
                                this.arnp[this.mouson] = 1.0f;
                            }
                            if (this.arnp[this.mouson] < 0.0f) {
                                this.arnp[this.mouson] = 0.0f;
                            }
                        }
                        if (this.mouson == 4) {
                            this.arnp[this.mouson] = (float)((double)((float)n - 671.0f) * 0.025);
                            if (this.arnp[this.mouson] > 1.0f) {
                                this.arnp[this.mouson] = 1.0f;
                            }
                            if (this.arnp[this.mouson] < 0.0f) {
                                this.arnp[this.mouson] = 0.0f;
                            }
                        }
                        if (this.cfase != 10 && this.cfase != 5 && n5 == -1) {
                            Color color = Color.getHSBColor(this.arnp[0], this.arnp[1], 1.0f - this.arnp[2]);
                            Color color2 = Color.getHSBColor(this.arnp[3], this.arnp[4], 1.0f - this.arnp[5]);
                            for (int i = 0; i < contOArray[this.sc[0]].npl; ++i) {
                                if (contOArray[this.sc[0]].p[i].colnum == 1) {
                                    contOArray[this.sc[0]].p[i].hsb[0] = this.arnp[0];
                                    contOArray[this.sc[0]].p[i].hsb[1] = this.arnp[1];
                                    contOArray[this.sc[0]].p[i].hsb[2] = 1.0f - this.arnp[2];
                                    contOArray[this.sc[0]].p[i].c[0] = color.getRed();
                                    contOArray[this.sc[0]].p[i].c[1] = color.getGreen();
                                    contOArray[this.sc[0]].p[i].c[2] = color.getBlue();
                                    contOArray[this.sc[0]].p[i].oc[0] = color.getRed();
                                    contOArray[this.sc[0]].p[i].oc[1] = color.getGreen();
                                    contOArray[this.sc[0]].p[i].oc[2] = color.getBlue();
                                }
                                if (contOArray[this.sc[0]].p[i].colnum != 2) continue;
                                contOArray[this.sc[0]].p[i].hsb[0] = this.arnp[3];
                                contOArray[this.sc[0]].p[i].hsb[1] = this.arnp[4];
                                contOArray[this.sc[0]].p[i].hsb[2] = 1.0f - this.arnp[5];
                                contOArray[this.sc[0]].p[i].c[0] = color2.getRed();
                                contOArray[this.sc[0]].p[i].c[1] = color2.getGreen();
                                contOArray[this.sc[0]].p[i].c[2] = color2.getBlue();
                                contOArray[this.sc[0]].p[i].oc[0] = color2.getRed();
                                contOArray[this.sc[0]].p[i].oc[1] = color2.getGreen();
                                contOArray[this.sc[0]].p[i].oc[2] = color2.getBlue();
                            }
                        }
                    }
                }
                if (!this.remi && this.cfase != 10 && this.cfase != 11 && this.cfase != 100 && this.cfase != 101) {
                    this.rd.drawImage(this.contin[this.pcontin], 355, 385, null);
                } else {
                    if (this.cfase == 11 && this.drawcarb(true, null, "Add to My Cars", 345, 385, n, n2, bl) && this.cd.action == 0) {
                        this.cd.ac = this.sc[0];
                        if (this.logged) {
                            this.cd.action = 6;
                            this.cd.sparkactionloader();
                        } else {
                            this.cd.reco = -5;
                            this.cfase = 5;
                            this.showtf = false;
                        }
                    }
                    if (this.cfase == 101 && n5 == -1) {
                        if (this.cd.publish[this.sc[0] - 16] == 1 || this.cd.publish[this.sc[0] - 16] == 2) {
                            if (this.drawcarb(true, null, "Add to My Cars", 345, 385, n, n2, bl) && this.cd.action == 0) {
                                this.cd.ac = this.sc[0];
                                if (this.logged) {
                                    this.cd.action = 6;
                                    this.cd.sparkactionloader();
                                } else {
                                    this.cd.reco = -5;
                                    this.cfase = 5;
                                    this.showtf = false;
                                }
                            }
                        } else {
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.drawcs(405, "Private Car", 193, 106, 0, 3);
                        }
                    }
                }
            }
        } else {
            if (this.cfase == 11 || this.cfase == 101) {
                this.cd.action = 0;
            }
            if (this.app.mycar.isShowing()) {
                this.app.mycar.hide();
            }
            this.pback = 0;
            this.pnext = 0;
            this.gatey = 300;
            if (this.flipo > 10) {
                contOArray[this.sc[0]].y -= 100;
                if (this.nextc == 1) {
                    contOArray[this.sc[0]].zy += 20;
                }
                if (this.nextc == -1) {
                    contOArray[this.sc[0]].zy -= 20;
                }
            } else {
                if (this.flipo == 10) {
                    if (this.nextc >= 20) {
                        this.sc[0] = this.nextc - 20;
                        this.lsc = -1;
                    }
                    if (this.nextc == 1) {
                        this.sc[0] = this.sc[0] + 1;
                        if (this.gmode == 1) {
                            if (this.sc[0] == 7) {
                                this.sc[0] = 11;
                            }
                            if (this.sc[0] == 12) {
                                this.sc[0] = 14;
                            }
                        }
                        if (this.multion != 0 && this.onjoin != -1 && this.ontyp > 0 && this.ontyp <= 5) {
                            while (this.sc[0] < this.maxsl && Math.abs(this.cd.cclass[this.sc[0]] - (this.ontyp - 1)) > 1) {
                                this.sc[0] = this.sc[0] + 1;
                            }
                        }
                    }
                    if (this.nextc == -1) {
                        this.sc[0] = this.sc[0] - 1;
                        if (this.gmode == 1) {
                            if (this.sc[0] == 13) {
                                this.sc[0] = 11;
                            }
                            if (this.sc[0] == 10) {
                                this.sc[0] = 6;
                            }
                        }
                        if (this.multion != 0 && this.onjoin != -1 && this.ontyp > 0 && this.ontyp <= 5) {
                            while (this.sc[0] > this.minsl && Math.abs(this.cd.cclass[this.sc[0]] - (this.ontyp - 1)) > 1) {
                                this.sc[0] = this.sc[0] - 1;
                            }
                        }
                    }
                    if (this.cfase == 3 && this.cd.lastload == 2) {
                        this.app.mcars.select(this.cd.names[this.sc[0]]);
                    }
                    contOArray[this.sc[0]].z = 950;
                    contOArray[this.sc[0]].y = -34 - contOArray[this.sc[0]].grat - 1100;
                    contOArray[this.sc[0]].x = 0;
                    contOArray[this.sc[0]].zy = 0;
                }
                contOArray[this.sc[0]].y += 100;
            }
            --this.flipo;
        }
        if (this.cfase == 0 || this.cfase == 3 || this.cfase == 11 || this.cfase == 101) {
            this.basefase = this.cfase;
            if (n5 != -1) {
                if (this.flatrstart > 1) {
                    this.flatrstart = 0;
                }
                this.nextc = n5 + 20;
                this.flipo = 20;
            }
            if (control.right) {
                control.right = false;
                if (this.sc[0] != this.maxsl && this.flipo == 0) {
                    if (this.flatrstart > 1) {
                        this.flatrstart = 0;
                    }
                    this.nextc = 1;
                    this.flipo = 20;
                }
            }
            if (control.left) {
                control.left = false;
                if (this.sc[0] != this.minsl && this.flipo == 0) {
                    if (this.flatrstart > 1) {
                        this.flatrstart = 0;
                    }
                    this.nextc = -1;
                    this.flipo = 20;
                }
            }
            if (this.cfase != 11 && this.cfase != 101 && n4 == 0 && this.flipo < 10 && (control.handb || control.enter)) {
                this.m.crs = false;
                this.app.mcars.show = false;
                if (this.multion != 0) {
                    this.fase = 1177;
                    this.intertrack.stop();
                } else {
                    this.fase = this.testdrive != 3 && this.testdrive != 4 ? 3 : -22;
                }
                if (this.sc[0] < 16 || this.cd.lastload == 2) {
                    this.app.setcarcookie(this.sc[0], this.cd.names[this.sc[0]], this.arnp, this.gmode, this.unlocked, this.mtop);
                }
                if (this.cd.haltload != 0) {
                    if (this.cd.haltload == 2) {
                        this.cd.lcardate[1] = 0;
                    }
                    this.cd.lcardate[0] = 0;
                    this.cd.haltload = 0;
                }
                if (this.gmode == 0) {
                    this.osc = this.sc[0];
                }
                if (this.gmode == 1) {
                    this.scm[0] = this.sc[0];
                }
                if (this.gmode == 2) {
                    this.scm[1] = this.sc[0];
                }
                if (this.app.mycar.isShowing()) {
                    this.app.mycar.hide();
                }
                this.flexpix = null;
                control.handb = false;
                control.enter = false;
            }
        } else {
            this.pback = 0;
            this.pnext = 0;
            this.pcontin = 0;
            if (this.cfase == 8 && n5 != -1) {
                if (this.flatrstart > 1) {
                    this.flatrstart = 0;
                }
                this.nextc = n5 + 20;
                this.flipo = 20;
            }
            if (this.cfase == 5 && this.cd.action == 0 && control.enter) {
                this.tcnt = 0;
                if (!this.app.tnick.getText().equals("") && !this.app.tpass.getText().equals("")) {
                    this.app.tnick.hide();
                    this.app.tpass.hide();
                    this.app.requestFocus();
                    this.cd.action = 1;
                    this.cd.sparkactionloader();
                } else {
                    if (this.app.tpass.getText().equals("")) {
                        this.cd.reco = -4;
                    }
                    if (this.app.tnick.getText().equals("")) {
                        this.cd.reco = -3;
                    }
                }
                control.enter = false;
            }
        }
        if (control.handb || control.enter) {
            control.handb = false;
            control.enter = false;
        }
        if (bl3) {
            this.app.mouses = 0;
            this.onviewpro = true;
            this.cd.viewname = this.cd.createdby[this.sc[0] - 16];
            this.m.crs = false;
            this.fase = 1177;
            this.intertrack.stop();
            this.sc[0] = this.onmsc;
            if (this.sc[0] >= 16 && this.cd.lastload != 2 || this.sc[0] >= 36) {
                this.sc[0] = 15;
            }
            this.osc = this.sc[0];
            this.multion = 1;
            this.gmode = 0;
            if (this.app.mycar.isShowing()) {
                this.app.mycar.hide();
            }
            this.flexpix = null;
            control.handb = false;
            control.enter = false;
        }
    }

    public void colorCar(ContO contO, int n) {
        if (this.plnames[n].indexOf("MadBot") == -1) {
            for (int i = 0; i < contO.npl; ++i) {
                Color color;
                if (contO.p[i].colnum == 1) {
                    color = Color.getHSBColor(this.allrnp[n][0], this.allrnp[n][1], 1.0f - this.allrnp[n][2]);
                    contO.p[i].oc[0] = color.getRed();
                    contO.p[i].oc[1] = color.getGreen();
                    contO.p[i].oc[2] = color.getBlue();
                }
                if (contO.p[i].colnum != 2) continue;
                color = Color.getHSBColor(this.allrnp[n][3], this.allrnp[n][4], 1.0f - this.allrnp[n][5]);
                contO.p[i].oc[0] = color.getRed();
                contO.p[i].oc[1] = color.getGreen();
                contO.p[i].oc[2] = color.getBlue();
            }
        } else {
            for (int i = 0; i < contO.npl; ++i) {
                if (contO.p[i].colnum == 1) {
                    contO.p[i].oc[0] = contO.fcol[0];
                    contO.p[i].oc[1] = contO.fcol[1];
                    contO.p[i].oc[2] = contO.fcol[2];
                }
                if (contO.p[i].colnum != 2) continue;
                contO.p[i].oc[0] = contO.scol[0];
                contO.p[i].oc[1] = contO.scol[1];
                contO.p[i].oc[2] = contO.scol[2];
            }
        }
    }

    public boolean clink(String string, int n, int n2, boolean bl) {
        boolean bl2 = false;
        this.rd.drawString("Created by :  " + string + "", 241, 160);
        int n3 = this.ftm.stringWidth(string);
        int n4 = 241 + this.ftm.stringWidth("Created by :  " + string + "") - n3;
        this.rd.drawLine(n4, 162, n4 + n3 - 2, 162);
        if (n > n4 - 2 && n < n4 + n3 && n2 > 147 && n2 < 164) {
            if (bl) {
                bl2 = true;
            }
            if (this.waitlink != 1) {
                this.app.setCursor(new Cursor(12));
                this.waitlink = 1;
            }
        } else if (this.waitlink != 0) {
            this.app.setCursor(new Cursor(0));
            this.waitlink = 0;
        }
        return bl2;
    }

    public void drawprom(int n, int n2) {
        this.rd.setComposite(AlphaComposite.getInstance(3, 0.76f));
        this.rd.setColor(new Color(129, 203, 237));
        this.rd.fillRoundRect(205, n, 390, n2, 30, 30);
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.drawRoundRect(205, n, 390, n2, 30, 30);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
    }

    public void drawdprom(int n, int n2) {
        this.rd.setComposite(AlphaComposite.getInstance(3, 0.9f));
        this.rd.setColor(new Color(129, 203, 237));
        this.rd.fillRoundRect(205, n, 390, n2, 30, 30);
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.drawRoundRect(205, n, 390, n2, 30, 30);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
    }

    public void drawlprom(int n, int n2) {
        this.rd.setComposite(AlphaComposite.getInstance(3, 0.5f));
        this.rd.setColor(new Color(129, 203, 237));
        this.rd.fillRoundRect(277, n, 390, n2, 30, 30);
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.drawRoundRect(277, n, 390, n2, 30, 30);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
    }

    public void finish(CheckPoints checkPoints, ContO[] contOArray, Control control, int n, int n2, boolean bl) {
        int n3;
        int n4;
        if (!this.badmac) {
            this.rd.drawImage(this.fleximg, 0, 0, null);
        } else {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.setComposite(AlphaComposite.getInstance(3, 0.1f));
            this.rd.fillRect(0, 0, 800, 450);
            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        }
        this.rd.setFont(new Font("Arial", 1, 11));
        this.ftm = this.rd.getFontMetrics();
        int n5 = 0;
        String string = ":";
        if (checkPoints.stage > 0) {
            n4 = checkPoints.stage;
            if (n4 > 10) {
                n4 -= 10;
            }
            string = " " + n4 + "!";
        }
        if (this.multion < 3) {
            if (this.winner) {
                this.rd.drawImage(this.congrd, 265, 87, null);
                this.drawcs(137, "You Won!  At Stage" + string + "", 255, 161, 85, 3);
                this.drawcs(154, "" + checkPoints.name + "", 255, 115, 0, 3);
                n5 = 154;
            } else {
                this.rd.drawImage(this.gameov, 315, 117, null);
                if (this.multion != 0 && (this.forstart == 700 || this.discon == 240)) {
                    this.drawcs(167, "Sorry, You where Disconnected from Game!", 255, 161, 85, 3);
                    this.drawcs(184, "Please check your connection!", 255, 115, 0, 3);
                } else {
                    this.drawcs(167, "You Lost!  At Stage" + string + "", 255, 161, 85, 3);
                    this.drawcs(184, "" + checkPoints.name + "", 255, 115, 0, 3);
                    n5 = 184;
                }
            }
        } else {
            this.rd.drawImage(this.gameov, 315, 117, null);
            this.drawcs(167, "Finished Watching Game!  At Stage" + string + "", 255, 161, 85, 3);
            this.drawcs(184, "" + checkPoints.name + "", 255, 115, 0, 3);
            n5 = 184;
        }
        if (this.winner && this.multion == 0 && this.gmode != 0 && (checkPoints.stage == this.unlocked[this.gmode - 1] + (this.gmode - 1) * 10 || checkPoints.stage == 27)) {
            n4 = 0;
            n3 = 0;
            this.pin = 60;
            if (this.gmode == 1) {
                if (checkPoints.stage == 2) {
                    n4 = 5;
                    n3 = 365;
                    this.pin = -20;
                    this.scm[0] = 5;
                }
                if (checkPoints.stage == 4) {
                    n4 = 6;
                    n3 = 320;
                    this.pin = -20;
                    this.scm[0] = 6;
                }
                if (checkPoints.stage == 6) {
                    n4 = 11;
                    n3 = 326;
                    this.pin = -20;
                    this.scm[0] = 11;
                }
                if (checkPoints.stage == 8) {
                    n4 = 14;
                    n3 = 350;
                    this.pin = -20;
                    this.scm[0] = 14;
                }
                if (checkPoints.stage == 10) {
                    n4 = 15;
                    n3 = 370;
                    this.pin = -20;
                    this.scm[0] = 15;
                }
            }
            if (this.gmode == 2) {
                if (checkPoints.stage == 12) {
                    n4 = 8;
                    n3 = 365;
                    this.pin = -20;
                    this.scm[1] = 8;
                }
                if (checkPoints.stage == 14) {
                    n4 = 9;
                    n3 = 320;
                    this.pin = -20;
                    this.scm[1] = 9;
                }
                if (checkPoints.stage == 16) {
                    n4 = 10;
                    n3 = 370;
                    this.pin = -20;
                    this.scm[1] = 10;
                }
                if (checkPoints.stage == 18) {
                    n4 = 11;
                    n3 = 326;
                    this.pin = -20;
                    this.scm[1] = 11;
                }
                if (checkPoints.stage == 20) {
                    n4 = 12;
                    n3 = 310;
                    this.pin = -20;
                    this.scm[1] = 12;
                }
                if (checkPoints.stage == 22) {
                    n4 = 13;
                    n3 = 310;
                    this.pin = -20;
                    this.scm[1] = 13;
                }
                if (checkPoints.stage == 24) {
                    n4 = 14;
                    n3 = 350;
                    this.pin = -20;
                    this.scm[1] = 14;
                }
                if (checkPoints.stage == 26) {
                    n4 = 15;
                    n3 = 370;
                    this.pin = -20;
                    this.scm[1] = 15;
                }
            }
            if (checkPoints.stage != 27) {
                this.rd.setFont(new Font("Arial", 1, 13));
                this.ftm = this.rd.getFontMetrics();
                if (this.aflk) {
                    this.drawcs(200 + this.pin, "Stage " + (checkPoints.stage + 1 - (this.gmode - 1) * 10) + " is now unlocked!", 196, 176, 0, 3);
                } else {
                    this.drawcs(200 + this.pin, "Stage " + (checkPoints.stage + 1 - (this.gmode - 1) * 10) + " is now unlocked!", 255, 247, 165, 3);
                }
                if (n4 != 0) {
                    if (this.aflk) {
                        this.drawcs(200, "And:", 196, 176, 0, 3);
                    } else {
                        this.drawcs(200, "And:", 255, 247, 165, 3);
                    }
                    this.rd.setColor(new Color(236, 226, 202));
                    if (Math.random() > 0.5) {
                        this.rd.setComposite(AlphaComposite.getInstance(3, 0.5f));
                        this.rd.fillRect(226, 211, 344, 125);
                        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    }
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.fillRect(226, 211, 348, 4);
                    this.rd.fillRect(226, 211, 4, 125);
                    this.rd.fillRect(226, 332, 348, 4);
                    this.rd.fillRect(570, 211, 4, 125);
                    contOArray[n4].y = n3;
                    this.m.crs = true;
                    this.m.x = -400;
                    this.m.y = 0;
                    this.m.z = -50;
                    this.m.xz = 0;
                    this.m.zy = 0;
                    this.m.ground = 2470;
                    contOArray[n4].z = 1000;
                    contOArray[n4].x = 0;
                    contOArray[n4].xz += 5;
                    contOArray[n4].zy = 0;
                    contOArray[n4].wzy -= 10;
                    this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                    contOArray[n4].d(this.rd);
                    this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    if (Math.random() < 0.5) {
                        this.rd.setComposite(AlphaComposite.getInstance(3, 0.4f));
                        this.rd.setColor(new Color(236, 226, 202));
                        for (int i = 0; i < 30; ++i) {
                            this.rd.drawLine(230, 215 + 4 * i, 569, 215 + 4 * i);
                        }
                        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    }
                    String string2 = "";
                    if (n4 == 13) {
                        string2 = " ";
                    }
                    if (this.aflk) {
                        this.drawcs(320, "" + this.cd.names[n4] + "" + string2 + " has been unlocked!", 196, 176, 0, 3);
                    } else {
                        this.drawcs(320, "" + this.cd.names[n4] + "" + string2 + " has been unlocked!", 255, 247, 165, 3);
                    }
                    this.pin = 140;
                }
                this.rd.setFont(new Font("Arial", 1, 11));
                this.ftm = this.rd.getFontMetrics();
                this.drawcs(220 + this.pin, "GAME SAVED", 230, 167, 0, 3);
                this.pin = this.pin == 60 ? 30 : 0;
            } else {
                this.rd.setFont(new Font("Arial", 1, 13));
                this.ftm = this.rd.getFontMetrics();
                if (this.aflk) {
                    this.drawcs(180, "Woohoooo you finished NFM" + this.gmode + " !!!", 144, 167, 255, 3);
                } else {
                    this.drawcs(180, "Woohoooo you finished NFM" + this.gmode + " !!!", 228, 240, 255, 3);
                }
                if (this.aflk) {
                    this.drawcs(210, "You're Awesome!", 144, 167, 255, 3);
                } else {
                    this.drawcs(212, "You're Awesome!", 228, 240, 255, 3);
                }
                if (this.aflk) {
                    this.drawcs(240, "You're truly a RADICAL GAMER!", 144, 167, 255, 3);
                } else {
                    this.drawcs(240, "You're truly a RADICAL GAMER!", 255, 100, 100, 3);
                }
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.fillRect(0, 255, 800, 62);
                this.rd.drawImage(this.radicalplay, this.radpx + (int)(8.0 * Math.random() - 4.0), 255, null);
                if (this.radpx != 212) {
                    this.radpx += 40;
                    if (this.radpx > 800) {
                        this.radpx = -468;
                    }
                }
                if (this.flipo == 40) {
                    this.radpx = 213;
                }
                ++this.flipo;
                if (this.flipo == 70) {
                    this.flipo = 0;
                }
                if (this.radpx == 212) {
                    this.rd.setFont(new Font("Arial", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                    if (this.aflk) {
                        this.drawcs(309, "A Game by Radicalplay.com", 144, 167, 255, 3);
                    } else {
                        this.drawcs(309, "A Game by Radicalplay.com", 228, 240, 255, 3);
                    }
                }
                if (this.aflk) {
                    this.drawcs(350, "Now get up and dance!", 144, 167, 255, 3);
                } else {
                    this.drawcs(350, "Now get up and dance!", 228, 240, 255, 3);
                }
                this.pin = 0;
            }
            this.aflk = !this.aflk;
        }
        if (this.multion != 0 && checkPoints.stage == -2 && n5 != 0) {
            this.drawcs(n5 + 17, "Created by: " + checkPoints.maker + "", 255, 161, 85, 3);
            if (checkPoints.pubt > 0) {
                if (checkPoints.pubt == 2) {
                    this.drawcs(310, "Super Public Stage", 41, 177, 255, 3);
                } else {
                    this.drawcs(310, "Public Stage", 41, 177, 255, 3);
                }
                if (this.dnload == 0 && this.drawcarb(true, null, " Add to My Stages ", 334, 317, n, n2, bl)) {
                    if (this.logged) {
                        this.cd.onstage = checkPoints.name;
                        this.cd.staction = 2;
                        this.cd.sparkstageaction();
                        this.dnload = 2;
                    } else {
                        this.dnload = 1;
                        this.waitlink = 20;
                    }
                }
                if (this.dnload == 1) {
                    this.rd.setColor(new Color(193, 106, 0));
                    String string3 = "Upgrade to a full account to add custom stages!";
                    n3 = 400 - this.ftm.stringWidth(string3) / 2;
                    int n6 = n3 + this.ftm.stringWidth(string3);
                    this.rd.drawString(string3, n3, 332);
                    if (this.waitlink != -1) {
                        this.rd.drawLine(n3, 334, n6, 334);
                    }
                    if (n > n3 && n < n6 && n2 > 321 && n2 < 334) {
                        if (this.waitlink != -1) {
                            this.app.setCursor(new Cursor(12));
                        }
                        if (bl && this.waitlink == 0) {
                            this.app.editlink(this.nickname, true);
                            this.waitlink = -1;
                        }
                    } else {
                        this.app.setCursor(new Cursor(0));
                    }
                    if (this.waitlink > 0) {
                        --this.waitlink;
                    }
                }
                if (this.dnload == 2) {
                    this.drawcs(332, "Adding stage please wait...", 193, 106, 0, 3);
                    if (this.cd.staction == 0) {
                        this.dnload = 3;
                    }
                    if (this.cd.staction == -2) {
                        this.dnload = 4;
                    }
                    if (this.cd.staction == -3) {
                        this.dnload = 5;
                    }
                    if (this.cd.staction == -1) {
                        this.dnload = 6;
                    }
                }
                if (this.dnload == 3) {
                    this.drawcs(332, "Stager has been successfully added to your stages!", 193, 106, 0, 3);
                }
                if (this.dnload == 4) {
                    this.drawcs(332, "You already have this stage!", 193, 106, 0, 3);
                }
                if (this.dnload == 5) {
                    this.drawcs(332, "Cannot add more then 20 stages to your account!", 193, 106, 0, 3);
                }
                if (this.dnload == 6) {
                    this.drawcs(332, "Failed to add stage, unknown error, please try again later.", 193, 106, 0, 3);
                }
            } else {
                this.drawcs(342, "Private Stage", 193, 106, 0, 3);
            }
        }
        this.rd.drawImage(this.contin[this.pcontin], 355, 380, null);
        if (control.enter || control.handb) {
            if (this.loadedt) {
                this.strack.unload();
            }
            if (this.multion == 0) {
                this.opselect = 3;
                if (this.gmode == 1) {
                    this.opselect = 0;
                    if (this.winner && checkPoints.stage == this.unlocked[this.gmode - 1] + (this.gmode - 1) * 10 && checkPoints.stage != 27) {
                        int n7 = this.gmode - 1;
                        this.unlocked[n7] = this.unlocked[n7] + 1;
                        this.justwon1 = true;
                    } else {
                        this.justwon1 = false;
                    }
                }
                if (this.gmode == 2) {
                    this.opselect = 1;
                    if (this.winner && checkPoints.stage == this.unlocked[this.gmode - 1] + (this.gmode - 1) * 10 && checkPoints.stage != 27) {
                        int n8 = this.gmode - 1;
                        this.unlocked[n8] = this.unlocked[n8] + 1;
                        this.justwon2 = true;
                    } else {
                        this.justwon2 = false;
                    }
                }
                if (checkPoints.stage == 27 && this.gmode == 0) {
                    checkPoints.stage = (int)(Math.random() * 27.0) + 1;
                }
                this.fase = 102;
            } else if (this.cd.haltload == 1) {
                this.sc[0] = 36;
                this.fase = 1177;
            } else if (!this.mtop || this.nfreeplays >= 5 && !this.logged) {
                this.opselect = 2;
                this.fase = 102;
            } else {
                this.fase = -9;
            }
            if (this.multion == 0 && this.winner && checkPoints.stage != 27 && checkPoints.stage > 0) {
                ++checkPoints.stage;
            }
            if (!(this.winner || this.multion == 0 || this.forstart != 700 && this.discon != 240 || this.ndisco >= 5)) {
                ++this.ndisco;
            }
            this.flipo = 0;
            control.enter = false;
            control.handb = false;
        }
    }

    public void sortcars(int n) {
        if (n != 0) {
            int n2;
            int n3;
            for (int i = 1; i < 7; ++i) {
                this.sc[i] = -1;
            }
            boolean[] blArray = new boolean[7];
            if (n < 0) {
                n = 27;
            }
            int n4 = 7;
            if (this.gmode == 1) {
                n4 = 5;
            }
            boolean bl = false;
            if (n <= 10) {
                n3 = 6;
                if (this.gmode == 1) {
                    n3 = 4;
                }
                if ((n == 1 || n == 2) && this.sc[0] != 5) {
                    this.sc[n3] = 5;
                    n4 = n3;
                }
                if ((n == 3 || n == 4) && this.sc[0] != 6) {
                    this.sc[n3] = 6;
                    n4 = n3;
                }
                if ((n == 5 || n == 6) && this.sc[0] != 11) {
                    this.sc[n3] = 11;
                    n4 = n3;
                }
                if ((n == 7 || n == 8) && this.sc[0] != 14) {
                    this.sc[n3] = 14;
                    n4 = n3;
                }
                if ((n == 9 || n == 10) && this.sc[0] != 15) {
                    this.sc[n3] = 15;
                    n4 = n3;
                }
            } else {
                bl = true;
                if (this.sc[0] != 7 + ((n -= 10) + 1) / 2 && n != 17) {
                    this.sc[6] = 7 + (n + 1) / 2;
                    n4 = 6;
                }
            }
            n3 = 16;
            int n5 = 1;
            int n6 = 2;
            for (n2 = 1; n2 < n4; ++n2) {
                blArray[n2] = false;
                while (!blArray[n2]) {
                    float f;
                    float f2 = 10.0f;
                    if (bl) {
                        f2 = 17.0f;
                    }
                    this.sc[n2] = (int)(Math.random() * (double)(24.0f + 8.0f * ((float)n / f2)));
                    if (this.sc[n2] >= 16) {
                        int n7 = n2;
                        this.sc[n7] = this.sc[n7] - 16;
                    }
                    blArray[n2] = true;
                    for (int i = 0; i < 7; ++i) {
                        if (n2 == i || this.sc[n2] != this.sc[i]) continue;
                        blArray[n2] = false;
                    }
                    if (bl) {
                        f2 = 16.0f;
                    }
                    if ((double)(f = (float)(15 - this.sc[n2]) / 15.0f * ((float)n / f2)) > 0.8) {
                        f = 0.8f;
                    }
                    if (n == 17 && (double)f > 0.5) {
                        f = 0.5f;
                    }
                    if ((double)f > Math.random()) {
                        blArray[n2] = false;
                    }
                    if (this.gmode == 1) {
                        if (this.sc[n2] >= 7 && this.sc[n2] <= 10) {
                            blArray[n2] = false;
                        }
                        if (this.sc[n2] == 12 || this.sc[n2] == 13) {
                            blArray[n2] = false;
                        }
                        if (this.sc[n2] > 5 && this.unlocked[0] <= 2) {
                            blArray[n2] = false;
                        }
                        if (this.sc[n2] > 6 && this.unlocked[0] <= 4) {
                            blArray[n2] = false;
                        }
                        if (this.sc[n2] > 11 && this.unlocked[0] <= 6) {
                            blArray[n2] = false;
                        }
                        if (this.sc[n2] > 14 && this.unlocked[0] <= 8) {
                            blArray[n2] = false;
                        }
                    }
                    if (this.gmode != 2) continue;
                    if ((this.sc[n2] - 7) * 2 > this.unlocked[1]) {
                        blArray[n2] = false;
                    }
                    if (n != 16 || this.unlocked[1] != 16 || this.sc[n2] >= 9) continue;
                    blArray[n2] = false;
                }
                if (this.sc[n2] >= n3) continue;
                n3 = this.sc[n2];
                if (n5 == n2) continue;
                n6 = n5;
                n5 = n2;
            }
            if (!bl && n == 10) {
                int n8;
                n2 = 0;
                for (n8 = 0; n8 < 7; ++n8) {
                    if (this.sc[n8] != 11) continue;
                    n2 = 1;
                }
                if (n2 == 0 && (Math.random() > Math.random() || this.gmode != 0)) {
                    this.sc[n5] = 11;
                }
                n2 = 0;
                for (n8 = 0; n8 < 7; ++n8) {
                    if (this.sc[n8] != 14) continue;
                    n2 = 1;
                }
                if (n2 == 0 && (Math.random() > Math.random() || this.gmode != 0)) {
                    this.sc[n6] = 14;
                }
            }
            if (n == 12) {
                n2 = 0;
                for (int i = 0; i < 7; ++i) {
                    if (this.sc[i] != 11) continue;
                    n2 = 1;
                }
                if (n2 == 0) {
                    this.sc[n5] = 11;
                }
            }
            if (n == 14) {
                int n9;
                n2 = 0;
                for (n9 = 0; n9 < 7; ++n9) {
                    if (this.sc[n9] != 12) continue;
                    n2 = 1;
                }
                if (n2 == 0 && (Math.random() > Math.random() || this.gmode != 0)) {
                    this.sc[n5] = 12;
                }
                n2 = 0;
                for (n9 = 0; n9 < 7; ++n9) {
                    if (this.sc[n9] != 10) continue;
                    n2 = 1;
                }
                if (n2 == 0 && (Math.random() > Math.random() || this.gmode != 0)) {
                    this.sc[n6] = 10;
                }
            }
            if (n == 15) {
                int n10;
                n2 = 0;
                for (n10 = 0; n10 < 7; ++n10) {
                    if (this.sc[n10] != 11) continue;
                    n2 = 1;
                }
                if (n2 == 0 && (Math.random() > Math.random() || this.gmode != 0)) {
                    this.sc[n5] = 11;
                }
                n2 = 0;
                for (n10 = 0; n10 < 7; ++n10) {
                    if (this.sc[n10] != 13) continue;
                    n2 = 1;
                }
                if (n2 == 0 && (Math.random() > Math.random() || this.gmode != 0)) {
                    this.sc[n6] = 13;
                }
            }
            if (n == 16) {
                int n11;
                n2 = 0;
                for (n11 = 0; n11 < 7; ++n11) {
                    if (this.sc[n11] != 13) continue;
                    n2 = 1;
                }
                if (n2 == 0 && (Math.random() > Math.random() || this.gmode != 0)) {
                    this.sc[n5] = 13;
                }
                n2 = 0;
                for (n11 = 0; n11 < 7; ++n11) {
                    if (this.sc[n11] != 12) continue;
                    n2 = 1;
                }
                if (n2 == 0 && (Math.random() > Math.random() || this.gmode != 0)) {
                    this.sc[n6] = 12;
                }
            }
            if (this.cd.lastload == 1) {
                n2 = 0;
                for (int i = 0; i < this.cd.nlcars - 16; ++i) {
                    int n12;
                    if (n2 == 0) {
                        for (n12 = 1; n12 < n4; ++n12) {
                            blArray[n12] = false;
                        }
                    }
                    if (!this.cd.include[i] || this.sc[0] == i + 16) continue;
                    n12 = (int)(1.0 + Math.random() * (double)(n4 - 1));
                    while (blArray[n12]) {
                        n12 = (int)(1.0 + Math.random() * (double)(n4 - 1));
                    }
                    blArray[n12] = true;
                    this.sc[n12] = i + 16;
                    if (++n2 != n4 - 1) continue;
                    n2 = 0;
                }
            }
            if (this.cd.lastload == 2) {
                n2 = 0;
                for (int i = 0; i < this.cd.nlocars - 16; ++i) {
                    int n13;
                    if (n2 == 0) {
                        for (n13 = 1; n13 < n4; ++n13) {
                            blArray[n13] = false;
                        }
                    }
                    if (!this.cd.include[i] || this.sc[0] == i + 16) continue;
                    n13 = (int)(1.0 + Math.random() * (double)(n4 - 1));
                    while (blArray[n13]) {
                        n13 = (int)(1.0 + Math.random() * (double)(n4 - 1));
                    }
                    blArray[n13] = true;
                    this.sc[n13] = i + 16;
                    if (++n2 != n4 - 1) continue;
                    n2 = 0;
                }
            }
        }
    }

    public void ctachm(int n, int n2, int n3, Control control) {
        if (this.fase == 1) {
            if (n3 == 1) {
                if (this.over(this.next[0], n, n2, 625, 135)) {
                    this.pnext = 1;
                }
                if (this.over(this.back[0], n, n2, 115, 135)) {
                    this.pback = 1;
                }
                if (this.over(this.contin[0], n, n2, 355, 360)) {
                    this.pcontin = 1;
                }
            }
            if (n3 == 2) {
                if (this.pnext == 1) {
                    control.right = true;
                }
                if (this.pback == 1) {
                    control.left = true;
                }
                if (this.pcontin == 1) {
                    control.enter = true;
                }
            }
        }
        if (this.fase == 3) {
            if (n3 == 1 && this.over(this.contin[0], n, n2, 355, 350)) {
                this.pcontin = 1;
            }
            if (n3 == 2 && this.pcontin == 1) {
                control.enter = true;
                this.pcontin = 0;
            }
        }
        if (this.fase == 4) {
            if (n3 == 1 && this.over(this.back[0], n, n2, 370, 345)) {
                this.pback = 1;
            }
            if (n3 == 2 && this.pback == 1) {
                control.enter = true;
                this.pback = 0;
            }
        }
        if (this.fase == 6) {
            if (n3 == 1 && (this.over(this.star[0], n, n2, 359, 385) || this.over(this.star[0], n, n2, 359, 295))) {
                this.pstar = 2;
            }
            if (n3 == 2 && this.pstar == 2) {
                control.enter = true;
                this.pstar = 1;
            }
        }
        if (this.fase == 7) {
            if (n3 == 1) {
                if (this.over(this.next[0], n, n2, 645, 275)) {
                    this.pnext = 1;
                }
                if (this.over(this.back[0], n, n2, 95, 275)) {
                    this.pback = 1;
                }
                if (this.over(this.contin[0], n, n2, 355, 385) && !this.app.openm) {
                    this.pcontin = 1;
                }
            }
            if (n3 == 2) {
                if (this.pnext == 1) {
                    control.right = true;
                }
                if (this.pback == 1) {
                    control.left = true;
                }
                if (this.pcontin == 1) {
                    control.enter = true;
                    this.pcontin = 0;
                }
            }
        }
        if (this.fase == -5) {
            this.lxm = n;
            this.lym = n2;
            if (n3 == 1 && this.over(this.contin[0], n, n2, 355, 380)) {
                this.pcontin = 1;
            }
            if (n3 == 2 && this.pcontin == 1) {
                control.enter = true;
                this.pcontin = 0;
            }
        }
        if (this.fase == -7) {
            if (n3 == 1) {
                if (this.overon(329, 45, 137, 22, n, n2)) {
                    this.opselect = 0;
                    this.shaded = true;
                }
                if (this.overon(320, 73, 155, 22, n, n2)) {
                    this.opselect = 1;
                    this.shaded = true;
                }
                if (this.overon(303, 99, 190, 22, n, n2)) {
                    this.opselect = 2;
                    this.shaded = true;
                }
                if (this.overon(341, 125, 109, 22, n, n2)) {
                    this.opselect = 3;
                    this.shaded = true;
                }
            }
            if (n3 == 2 && this.shaded) {
                control.enter = true;
                this.shaded = false;
            }
            if (n3 == 0 && (n != this.lxm || n2 != this.lym)) {
                if (this.overon(329, 45, 137, 22, n, n2)) {
                    this.opselect = 0;
                }
                if (this.overon(320, 73, 155, 22, n, n2)) {
                    this.opselect = 1;
                }
                if (this.overon(303, 99, 190, 22, n, n2)) {
                    this.opselect = 2;
                }
                if (this.overon(341, 125, 109, 22, n, n2)) {
                    this.opselect = 3;
                }
                this.lxm = n;
                this.lym = n2;
            }
        }
        if (this.fase == 10) {
            if (n3 == 1) {
                if (this.overon(343, 261, 110, 22, n, n2)) {
                    this.opselect = 0;
                    this.shaded = true;
                }
                if (this.overon(288, 291, 221, 22, n, n2)) {
                    this.opselect = 1;
                    this.shaded = true;
                }
                if (this.overon(301, 321, 196, 22, n, n2)) {
                    this.opselect = 2;
                    this.shaded = true;
                }
                if (this.overon(357, 351, 85, 22, n, n2)) {
                    this.opselect = 3;
                    this.shaded = true;
                }
            }
            if (n3 == 2 && this.shaded) {
                control.enter = true;
                this.shaded = false;
            }
            if (n3 == 0 && (n != this.lxm || n2 != this.lym)) {
                if (this.overon(343, 261, 110, 22, n, n2)) {
                    this.opselect = 0;
                }
                if (this.overon(288, 291, 221, 22, n, n2)) {
                    this.opselect = 1;
                }
                if (this.overon(301, 321, 196, 22, n, n2)) {
                    this.opselect = 2;
                }
                if (this.overon(357, 351, 85, 22, n, n2)) {
                    this.opselect = 3;
                }
                this.lxm = n;
                this.lym = n2;
            }
        }
        if (this.fase == 102) {
            if (n3 == 1) {
                if (this.overon(358, 262 + this.dropf, 82, 22, n, n2)) {
                    this.opselect = 0;
                    this.shaded = true;
                }
                if (this.overon(358, 290 + this.dropf, 82, 22, n, n2)) {
                    this.opselect = 1;
                    this.shaded = true;
                }
                if (this.overon(333, 318 + this.dropf, 132, 22, n, n2)) {
                    this.opselect = 2;
                    this.shaded = true;
                }
                if (this.dropf == 0 && this.overon(348, 346, 102, 22, n, n2)) {
                    this.opselect = 3;
                    this.shaded = true;
                }
            }
            if (n3 == 2 && this.shaded) {
                control.enter = true;
                this.shaded = false;
            }
            if (n3 == 0 && (n != this.lxm || n2 != this.lym)) {
                if (this.overon(358, 262 + this.dropf, 82, 22, n, n2)) {
                    this.opselect = 0;
                }
                if (this.overon(358, 290 + this.dropf, 82, 22, n, n2)) {
                    this.opselect = 1;
                }
                if (this.overon(333, 318 + this.dropf, 132, 22, n, n2)) {
                    this.opselect = 2;
                }
                if (this.dropf == 0 && this.overon(348, 346, 102, 22, n, n2)) {
                    this.opselect = 3;
                }
                this.lxm = n;
                this.lym = n2;
            }
        }
        if (this.fase == 11) {
            if (this.flipo >= 1 && this.flipo <= 15) {
                if (n3 == 1 && this.over(this.next[0], n, n2, 665, 395)) {
                    this.pnext = 1;
                }
                if (n3 == 2 && this.pnext == 1) {
                    control.right = true;
                    this.pnext = 0;
                }
            }
            if (this.flipo >= 3 && this.flipo <= 16) {
                if (n3 == 1 && this.over(this.back[0], n, n2, 75, 395)) {
                    this.pback = 1;
                }
                if (n3 == 2 && this.pback == 1) {
                    control.left = true;
                    this.pback = 0;
                }
            }
            if (this.flipo == 16) {
                if (n3 == 1 && this.over(this.contin[0], n, n2, 565, 395)) {
                    this.pcontin = 1;
                }
                if (n3 == 2 && this.pcontin == 1) {
                    control.enter = true;
                    this.pcontin = 0;
                }
            }
        }
        if (this.fase == 8) {
            if (n3 == 1 && this.over(this.next[0], n, n2, 665, 395)) {
                this.pnext = 1;
            }
            if (n3 == 2 && this.pnext == 1) {
                control.enter = true;
                this.pnext = 0;
            }
        }
    }

    public void stat(Mad mad, ContO contO, CheckPoints checkPoints, Control control, boolean bl) {
        int n;
        if (this.holdit) {
            n = 250;
            if (this.fase == 7001) {
                if (this.exitm != 4) {
                    this.exitm = 0;
                    n = 600;
                } else {
                    n = 1200;
                }
            }
            if (this.exitm != 4 || !this.lan || this.im != 0) {
                ++this.holdcnt;
                if (!(!control.enter && this.holdcnt <= n || control.chatup != 0 && this.fase == 7001)) {
                    this.fase = -2;
                    control.enter = false;
                }
            } else if (control.enter) {
                control.enter = false;
            }
        } else {
            if (this.holdcnt != 0) {
                this.holdcnt = 0;
            }
            if (control.enter || control.exit) {
                if (this.fase == 0) {
                    if (this.loadedt) {
                        this.strack.stop();
                    }
                    this.fase = -6;
                } else if (!(this.starcnt != 0 || control.chatup != 0 || this.multion >= 2 && this.lan)) {
                    this.exitm = this.exitm == 0 ? 1 : 0;
                }
                if (control.chatup == 0 || this.fase != 7001) {
                    control.enter = false;
                }
                control.exit = false;
            }
        }
        if (this.exitm == 2) {
            this.fase = -2;
            this.winner = false;
        }
        if (this.fase != -2) {
            int n2;
            int n3;
            this.holdit = false;
            if (checkPoints.haltall) {
                checkPoints.haltall = false;
            }
            n = 0;
            String string = "";
            String string2 = "";
            if (!(this.clangame == 0 || mad.dest && this.multion < 2)) {
                n = 1;
                for (n3 = 0; n3 < this.nplayers; ++n3) {
                    if (checkPoints.dested[n3] != 0) continue;
                    if (string.equals("")) {
                        string = this.pclan[n3];
                        continue;
                    }
                    if (string.toLowerCase().equals(this.pclan[n3].toLowerCase())) continue;
                    n = 0;
                    break;
                }
            }
            if (this.clangame > 1) {
                n3 = 0;
                String string3 = "";
                if (n != 0) {
                    for (n2 = 0; n2 < this.nplayers; ++n2) {
                        if (string.toLowerCase().equals(this.pclan[n2].toLowerCase())) continue;
                        string2 = this.pclan[n2];
                        break;
                    }
                    if (this.clangame == 2) {
                        n3 = 1;
                        string3 = "Clan " + string2 + " wasted, nobody won becuase this is a racing only game!";
                    }
                    if (this.clangame == 4 && !string.toLowerCase().equals(this.gaclan.toLowerCase())) {
                        n3 = 1;
                        string3 = "Clan " + string2 + " wasted, nobody won becuase " + string + " should have raced in this racing vs wasting game!";
                    }
                    if (this.clangame == 5 && string.toLowerCase().equals(this.gaclan.toLowerCase())) {
                        n3 = 1;
                        string3 = "Clan " + string2 + " wasted, nobody won becuase " + string + " should have raced in this racing vs wasting game!";
                    }
                }
                for (n2 = 0; n2 < this.nplayers; ++n2) {
                    if (checkPoints.clear[n2] != checkPoints.nlaps * checkPoints.nsp || checkPoints.pos[n2] != 0) continue;
                    if (this.clangame == 3) {
                        n3 = 1;
                        string3 = "" + this.plnames[n2] + " of clan " + this.pclan[n2] + " finished first, nobody won becuase this is a wasting only game!";
                    }
                    if (this.clangame == 4 && this.pclan[n2].toLowerCase().equals(this.gaclan.toLowerCase())) {
                        n3 = 1;
                        string3 = "" + this.plnames[n2] + " of clan " + this.pclan[n2] + " finished first, nobody won becuase " + this.pclan[n2] + " should have wasted in this racing vs wasting game!";
                    }
                    if (this.clangame != 5 || this.pclan[n2].toLowerCase().equals(this.gaclan.toLowerCase())) continue;
                    n3 = 1;
                    string3 = "" + this.plnames[n2] + " of clan " + this.pclan[n2] + " finished first, nobody won becuase " + this.pclan[n2] + " should have wasted in this racing vs wasting game!";
                }
                if (n3 != 0) {
                    this.drawhi(this.gamefinished, 70);
                    if (this.aflk) {
                        this.drawcs(120, string3, 0, 0, 0, 0);
                        this.aflk = false;
                    } else {
                        this.drawcs(120, string3, 0, 128, 255, 0);
                        this.aflk = true;
                    }
                    this.drawcs(350, "Press  [ Enter ]  to continue", 0, 0, 0, 0);
                    checkPoints.haltall = true;
                    this.holdit = true;
                    this.winner = false;
                }
            }
            if (this.multion < 2) {
                if (!this.holdit && (checkPoints.wasted == this.nplayers - 1 && this.nplayers != 1 || n != 0)) {
                    this.drawhi(this.youwastedem, 70);
                    if (n == 0) {
                        if (this.aflk) {
                            this.drawcs(120, "You Won, all cars have been wasted!", 0, 0, 0, 0);
                            this.aflk = false;
                        } else {
                            this.drawcs(120, "You Won, all cars have been wasted!", 0, 128, 255, 0);
                            this.aflk = true;
                        }
                    } else if (this.aflk) {
                        this.drawcs(120, "Your clan " + string + " has wasted all the cars!", 0, 0, 0, 0);
                        this.aflk = false;
                    } else {
                        this.drawcs(120, "Your clan " + string + " has wasted all the cars!", 0, 128, 255, 0);
                        this.aflk = true;
                    }
                    this.drawcs(350, "Press  [ Enter ]  to continue", 0, 0, 0, 0);
                    checkPoints.haltall = true;
                    this.holdit = true;
                    this.winner = true;
                }
                if (!this.holdit && mad.dest && this.cntwis == 8) {
                    if (this.discon != 240) {
                        this.drawhi(this.yourwasted, 70);
                    } else {
                        this.drawhi(this.disco, 70);
                        this.stopchat();
                    }
                    n3 = 0;
                    if (this.lan) {
                        n3 = 1;
                        for (int i = 0; i < this.nplayers; ++i) {
                            if (i == this.im || this.dested[i] != 0 || this.plnames[i].indexOf("MadBot") != -1) continue;
                            n3 = 0;
                        }
                    }
                    if (this.fase == 7001 && this.nplayers - (checkPoints.wasted + 1) >= 2 && this.discon != 240 && n3 == 0) {
                        this.exitm = 4;
                    } else {
                        if (this.exitm == 4) {
                            this.exitm = 0;
                        }
                        this.drawcs(350, "Press  [ Enter ]  to continue", 0, 0, 0, 0);
                    }
                    this.holdit = true;
                    this.winner = false;
                }
                if (!this.holdit) {
                    for (n3 = 0; n3 < this.nplayers; ++n3) {
                        if (checkPoints.clear[n3] != checkPoints.nlaps * checkPoints.nsp || checkPoints.pos[n3] != 0) continue;
                        if (this.clangame == 0) {
                            if (n3 == this.im) {
                                this.drawhi(this.youwon, 70);
                                if (this.aflk) {
                                    this.drawcs(120, "You finished first, nice job!", 0, 0, 0, 0);
                                    this.aflk = false;
                                } else {
                                    this.drawcs(120, "You finished first, nice job!", 0, 128, 255, 0);
                                    this.aflk = true;
                                }
                                this.winner = true;
                            } else {
                                this.drawhi(this.youlost, 70);
                                if (this.fase != 7001) {
                                    if (this.aflk) {
                                        this.drawcs(120, "" + this.cd.names[this.sc[n3]] + " finished first, race over!", 0, 0, 0, 0);
                                        this.aflk = false;
                                    } else {
                                        this.drawcs(120, "" + this.cd.names[this.sc[n3]] + " finished first, race over!", 0, 128, 255, 0);
                                        this.aflk = true;
                                    }
                                } else if (this.aflk) {
                                    this.drawcs(120, "" + this.plnames[n3] + " finished first, race over!", 0, 0, 0, 0);
                                    this.aflk = false;
                                } else {
                                    this.drawcs(120, "" + this.plnames[n3] + " finished first, race over!", 0, 128, 255, 0);
                                    this.aflk = true;
                                }
                                this.winner = false;
                            }
                        } else if (this.pclan[n3].toLowerCase().equals(this.pclan[this.im].toLowerCase())) {
                            this.drawhi(this.youwon, 70);
                            if (this.aflk) {
                                this.drawcs(120, "Your clan " + this.pclan[this.im] + " finished first, nice job!", 0, 0, 0, 0);
                                this.aflk = false;
                            } else {
                                this.drawcs(120, "Your clan " + this.pclan[this.im] + " finished first, nice job!", 0, 128, 255, 0);
                                this.aflk = true;
                            }
                            this.winner = true;
                        } else {
                            this.drawhi(this.youlost, 70);
                            if (this.aflk) {
                                this.drawcs(120, "" + this.plnames[n3] + " of clan " + this.pclan[n3] + " finished first, race over!", 0, 0, 0, 0);
                                this.aflk = false;
                            } else {
                                this.drawcs(120, "" + this.plnames[n3] + " of clan " + this.pclan[n3] + " finished first, race over!", 0, 128, 255, 0);
                                this.aflk = true;
                            }
                            this.winner = false;
                        }
                        this.drawcs(350, "Press  [ Enter ]  to continue", 0, 0, 0, 0);
                        checkPoints.haltall = true;
                        this.holdit = true;
                    }
                }
            } else {
                if (!(this.holdit || checkPoints.wasted < this.nplayers - 1 && n == 0)) {
                    String string4 = "Someone";
                    if (n == 0) {
                        for (int i = 0; i < this.nplayers; ++i) {
                            if (checkPoints.dested[i] != 0) continue;
                            string4 = this.plnames[i];
                        }
                    } else {
                        string4 = "Clan " + string + "";
                    }
                    this.drawhi(this.gamefinished, 70);
                    if (this.aflk) {
                        this.drawcs(120, "" + string4 + " has wasted all the cars!", 0, 0, 0, 0);
                        this.aflk = false;
                    } else {
                        this.drawcs(120, "" + string4 + " has wasted all the cars!", 0, 128, 255, 0);
                        this.aflk = true;
                    }
                    this.drawcs(350, "Press  [ Enter ]  to continue", 0, 0, 0, 0);
                    checkPoints.haltall = true;
                    this.holdit = true;
                    this.winner = false;
                }
                if (!this.holdit) {
                    for (int i = 0; i < this.nplayers; ++i) {
                        if (checkPoints.clear[i] != checkPoints.nlaps * checkPoints.nsp || checkPoints.pos[i] != 0) continue;
                        this.drawhi(this.gamefinished, 70);
                        if (this.clangame == 0) {
                            if (this.aflk) {
                                this.drawcs(120, "" + this.plnames[i] + " finished first, race over!", 0, 0, 0, 0);
                                this.aflk = false;
                            } else {
                                this.drawcs(120, "" + this.plnames[i] + " finished first, race over!", 0, 128, 255, 0);
                                this.aflk = true;
                            }
                        } else if (this.aflk) {
                            this.drawcs(120, "Clan " + this.pclan[i] + " finished first, race over!", 0, 0, 0, 0);
                            this.aflk = false;
                        } else {
                            this.drawcs(120, "Clan " + this.pclan[i] + " finished first, race over!", 0, 128, 255, 0);
                            this.aflk = true;
                        }
                        this.drawcs(350, "Press  [ Enter ]  to continue", 0, 0, 0, 0);
                        checkPoints.haltall = true;
                        this.holdit = true;
                        this.winner = false;
                    }
                }
                if (!this.holdit && this.discon == 240) {
                    this.drawhi(this.gamefinished, 70);
                    if (this.aflk) {
                        this.drawcs(120, "Game got disconnected!", 0, 0, 0, 0);
                        this.aflk = false;
                    } else {
                        this.drawcs(120, "Game got disconnected!", 0, 128, 255, 0);
                        this.aflk = true;
                    }
                    this.drawcs(350, "Press  [ Enter ]  to continue", 0, 0, 0, 0);
                    checkPoints.haltall = true;
                    this.holdit = true;
                    this.winner = false;
                }
                if (!this.holdit) {
                    this.rd.drawImage(this.wgame, 311, 20, null);
                    if (!this.clanchat) {
                        this.drawcs(397, "Click any player on the right to follow!", 0, 0, 0, 0);
                        if (!this.lan) {
                            this.drawcs(412, "Press [V] to change view.  Press [Enter] to exit.", 0, 0, 0, 0);
                        } else {
                            this.drawcs(412, "Press [V] to change view.", 0, 0, 0, 0);
                        }
                    }
                }
            }
            if (bl) {
                if (checkPoints.stage != 10 && this.multion < 2 && this.nplayers != 1 && this.arrace != control.arrace) {
                    this.arrace = control.arrace;
                    if (this.multion == 1 && this.arrace) {
                        control.radar = true;
                    }
                    if (this.arrace) {
                        this.wasay = true;
                        this.say = " Arrow now pointing at >  CARS";
                        if (this.multion == 1) {
                            this.say = this.say + "    Press [S] to toggle Radar!";
                        }
                        this.tcnt = -5;
                    }
                    if (!this.arrace) {
                        this.wasay = false;
                        this.say = " Arrow now pointing at >  TRACK";
                        if (this.multion == 1) {
                            this.say = this.say + "    Press [S] to toggle Radar!";
                        }
                        this.tcnt = -5;
                        this.cntan = 20;
                        this.alocked = -1;
                        this.alocked = -1;
                    }
                }
                if (!this.holdit && this.fase != -6 && this.starcnt == 0 && this.multion < 2 && checkPoints.stage != 10) {
                    this.arrow(mad.point, mad.missedcp, checkPoints, this.arrace);
                    if (!this.arrace) {
                        if (this.auscnt == 45 && mad.capcnt == 0 && this.exitm == 0) {
                            if (mad.missedcp > 0) {
                                if (mad.missedcp > 15 && mad.missedcp < 50) {
                                    if (this.flk) {
                                        this.drawcs(70, "Checkpoint Missed!", 255, 0, 0, 0);
                                    } else {
                                        this.drawcs(70, "Checkpoint Missed!", 255, 150, 0, 2);
                                    }
                                }
                                ++mad.missedcp;
                                if (mad.missedcp == 70) {
                                    mad.missedcp = -2;
                                }
                            } else if (mad.mtouch && this.cntovn < 70) {
                                if (Math.abs(this.ana) > 100) {
                                    ++this.cntan;
                                } else if (this.cntan != 0) {
                                    --this.cntan;
                                }
                                if (this.cntan > 40) {
                                    ++this.cntovn;
                                    this.cntan = 40;
                                    if (this.flk) {
                                        this.drawcs(70, "Wrong Way!", 255, 150, 0, 0);
                                        this.flk = false;
                                    } else {
                                        this.drawcs(70, "Wrong Way!", 255, 0, 0, 2);
                                        this.flk = true;
                                    }
                                }
                            }
                        }
                    } else if (this.alocked != this.lalocked) {
                        if (this.alocked != -1) {
                            this.wasay = true;
                            this.say = " Arrow Locked on >  " + this.plnames[this.alocked] + "";
                            this.tcnt = -5;
                        } else {
                            this.wasay = true;
                            this.say = "Arrow Unlocked!";
                            this.tcnt = 10;
                        }
                        this.lalocked = this.alocked;
                    }
                }
                if (this.m.darksky) {
                    Color color = new Color(this.m.csky[0], this.m.csky[1], this.m.csky[2]);
                    float[] fArray = new float[3];
                    Color.RGBtoHSB(this.m.csky[0], this.m.csky[1], this.m.csky[2], fArray);
                    fArray[2] = 0.6f;
                    color = Color.getHSBColor(fArray[0], fArray[1], fArray[2]);
                    this.rd.setColor(color);
                    this.rd.fillRect(602, 9, 54, 14);
                    this.rd.drawLine(601, 10, 601, 21);
                    this.rd.drawLine(600, 12, 600, 19);
                    this.rd.fillRect(607, 29, 49, 14);
                    this.rd.drawLine(606, 30, 606, 41);
                    this.rd.drawLine(605, 32, 605, 39);
                    this.rd.fillRect(18, 6, 155, 14);
                    this.rd.drawLine(17, 7, 17, 18);
                    this.rd.drawLine(16, 9, 16, 16);
                    this.rd.drawLine(173, 7, 173, 18);
                    this.rd.drawLine(174, 9, 174, 16);
                    this.rd.fillRect(40, 26, 107, 21);
                    this.rd.drawLine(39, 27, 39, 45);
                    this.rd.drawLine(38, 29, 38, 43);
                    this.rd.drawLine(147, 27, 147, 45);
                    this.rd.drawLine(148, 29, 148, 43);
                }
                this.rd.drawImage(this.dmg, 600, 7, null);
                this.rd.drawImage(this.pwr, 600, 27, null);
                this.rd.drawImage(this.lap, 19, 7, null);
                this.rd.setColor(new Color(0, 0, 100));
                this.rd.drawString("" + (mad.nlaps + 1) + " / " + checkPoints.nlaps + "", 51, 18);
                this.rd.drawImage(this.was, 92, 7, null);
                this.rd.setColor(new Color(0, 0, 100));
                this.rd.drawString("" + checkPoints.wasted + " / " + (this.nplayers - 1) + "", 150, 18);
                this.rd.drawImage(this.pos, 42, 27, null);
                this.rd.drawImage(this.rank[checkPoints.pos[mad.im]], 110, 28, null);
                this.drawstat(this.cd.maxmag[mad.cn], mad.hitmag, mad.newcar, mad.power);
                if (control.radar && checkPoints.stage != 10) {
                    this.radarstat(mad, contO, checkPoints);
                }
            }
            if (!this.holdit) {
                if (this.starcnt != 0 && this.starcnt <= 35) {
                    if (this.starcnt == 35 && !this.mutes) {
                        this.three.play();
                    }
                    if (this.starcnt == 24) {
                        this.gocnt = 2;
                        if (!this.mutes) {
                            this.two.play();
                        }
                    }
                    if (this.starcnt == 13) {
                        this.gocnt = 1;
                        if (!this.mutes) {
                            this.one.play();
                        }
                    }
                    if (this.starcnt == 2) {
                        this.gocnt = 0;
                        if (!this.mutes) {
                            this.go.play();
                        }
                    }
                    this.duds = 0;
                    if (this.starcnt <= 37 && this.starcnt > 32) {
                        this.duds = 1;
                    }
                    if (this.starcnt <= 26 && this.starcnt > 21) {
                        this.duds = 1;
                    }
                    if (this.starcnt <= 15 && this.starcnt > 10) {
                        this.duds = 1;
                    }
                    if (this.starcnt <= 4) {
                        this.duds = 2;
                    }
                    if (this.dudo != -1) {
                        this.rd.setComposite(AlphaComposite.getInstance(3, 0.3f));
                        this.rd.drawImage(this.dude[this.duds], this.dudo, 0, null);
                        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    }
                    if (this.gocnt != 0) {
                        this.rd.drawImage(this.cntdn[this.gocnt], 385, 50, null);
                    } else {
                        this.rd.drawImage(this.cntdn[this.gocnt], 363, 50, null);
                    }
                }
                if (this.looped != 0 && mad.loop == 2) {
                    this.looped = 0;
                }
                if (mad.power < 45.0f) {
                    if (this.tcnt == 30 && this.auscnt == 45 && mad.mtouch && mad.capcnt == 0 && this.exitm == 0) {
                        if (this.looped != 2) {
                            if (this.pwcnt < 70 || this.pwcnt < 100 && this.looped != 0) {
                                if (this.pwflk) {
                                    this.drawcs(110, "Power low, perform stunt!", 0, 0, 200, 0);
                                    this.pwflk = false;
                                } else {
                                    this.drawcs(110, "Power low, perform stunt!", 255, 100, 0, 0);
                                    this.pwflk = true;
                                }
                            }
                        } else if (this.pwcnt < 100) {
                            String string5 = "";
                            if (this.multion == 0) {
                                string5 = "  (Press Enter)";
                            }
                            if (this.pwflk) {
                                this.drawcs(110, "Please read the Game Instructions!" + string5 + "", 0, 0, 200, 0);
                                this.pwflk = false;
                            } else {
                                this.drawcs(110, "Please read the Game Instructions!" + string5 + "", 255, 100, 0, 0);
                                this.pwflk = true;
                            }
                        }
                        ++this.pwcnt;
                        if (this.pwcnt == 300) {
                            this.pwcnt = 0;
                            if (this.looped != 0) {
                                ++this.looped;
                                if (this.looped == 4) {
                                    this.looped = 2;
                                }
                            }
                        }
                    }
                } else if (this.pwcnt != 0) {
                    this.pwcnt = 0;
                }
                if (mad.capcnt == 0) {
                    if (this.tcnt < 30) {
                        if (this.exitm == 0) {
                            if (this.tflk) {
                                if (!this.wasay) {
                                    this.drawcs(105, this.say, 0, 0, 0, 0);
                                } else {
                                    this.drawcs(105, this.say, 0, 0, 0, 0);
                                }
                                this.tflk = false;
                            } else {
                                if (!this.wasay) {
                                    this.drawcs(105, this.say, 0, 128, 255, 0);
                                } else {
                                    this.drawcs(105, this.say, 255, 128, 0, 0);
                                }
                                this.tflk = true;
                            }
                        }
                        ++this.tcnt;
                    } else if (this.wasay) {
                        this.wasay = false;
                    }
                    if (this.auscnt < 45) {
                        if (this.exitm == 0) {
                            if (this.aflk) {
                                this.drawcs(85, this.asay, 98, 176, 255, 0);
                                this.aflk = false;
                            } else {
                                this.drawcs(85, this.asay, 0, 128, 255, 0);
                                this.aflk = true;
                            }
                        }
                        ++this.auscnt;
                    }
                } else if (this.exitm == 0) {
                    if (this.tflk) {
                        this.drawcs(110, "Bad Landing!", 0, 0, 200, 0);
                        this.tflk = false;
                    } else {
                        this.drawcs(110, "Bad Landing!", 255, 100, 0, 0);
                        this.tflk = true;
                    }
                }
                if (mad.trcnt == 10) {
                    this.loop = "";
                    this.spin = "";
                    this.asay = "";
                    int n4 = 0;
                    while (mad.travzy > 225) {
                        mad.travzy -= 360;
                        ++n4;
                    }
                    while (mad.travzy < -225) {
                        mad.travzy += 360;
                        --n4;
                    }
                    if (n4 == 1) {
                        this.loop = "Forward loop";
                    }
                    if (n4 == 2) {
                        this.loop = "double Forward";
                    }
                    if (n4 == 3) {
                        this.loop = "triple Forward";
                    }
                    if (n4 >= 4) {
                        this.loop = "massive Forward looping";
                    }
                    if (n4 == -1) {
                        this.loop = "Backloop";
                    }
                    if (n4 == -2) {
                        this.loop = "double Back";
                    }
                    if (n4 == -3) {
                        this.loop = "triple Back";
                    }
                    if (n4 <= -4) {
                        this.loop = "massive Back looping";
                    }
                    if (n4 == 0) {
                        if (mad.ftab && mad.btab) {
                            this.loop = "Tabletop and reversed Tabletop";
                        } else if (mad.ftab || mad.btab) {
                            this.loop = "Tabletop";
                        }
                    }
                    if (n4 > 0 && mad.btab) {
                        this.loop = "Hanged " + this.loop;
                    }
                    if (n4 < 0 && mad.ftab) {
                        this.loop = "Hanged " + this.loop;
                    }
                    if (this.loop != "") {
                        this.asay = this.asay + " " + this.loop;
                    }
                    n4 = 0;
                    mad.travxy = Math.abs(mad.travxy);
                    while (mad.travxy > 270) {
                        mad.travxy -= 360;
                        ++n4;
                    }
                    if (n4 == 0 && mad.rtab) {
                        this.spin = this.loop == "" ? "Tabletop" : "Flipside";
                    }
                    if (n4 == 1) {
                        this.spin = "Rollspin";
                    }
                    if (n4 == 2) {
                        this.spin = "double Rollspin";
                    }
                    if (n4 == 3) {
                        this.spin = "triple Rollspin";
                    }
                    if (n4 >= 4) {
                        this.spin = "massive Roll spinning";
                    }
                    n4 = 0;
                    boolean bl2 = false;
                    mad.travxz = Math.abs(mad.travxz);
                    while (mad.travxz > 90) {
                        mad.travxz -= 180;
                        if ((n4 += 180) <= 900) continue;
                        n4 = 900;
                        bl2 = true;
                    }
                    if (n4 != 0) {
                        if (this.loop == "" && this.spin == "") {
                            this.asay = this.asay + " " + n4;
                            if (bl2) {
                                this.asay = this.asay + " and beyond";
                            }
                        } else {
                            if (this.spin != "") {
                                this.asay = this.loop == "" ? this.asay + " " + this.spin : this.asay + " with " + this.spin;
                            }
                            this.asay = this.asay + " by " + n4;
                            if (bl2) {
                                this.asay = this.asay + " and beyond";
                            }
                        }
                    } else if (this.spin != "") {
                        this.asay = this.loop == "" ? this.asay + " " + this.spin : this.asay + " by " + this.spin;
                    }
                    if (this.asay != "") {
                        this.auscnt -= 15;
                    }
                    if (this.loop != "") {
                        this.auscnt -= 25;
                    }
                    if (this.spin != "") {
                        this.auscnt -= 25;
                    }
                    if (n4 != 0) {
                        this.auscnt -= 25;
                    }
                    if (this.auscnt < 45) {
                        if (!this.mutes) {
                            this.powerup.play();
                        }
                        if (this.auscnt < -20) {
                            this.auscnt = -20;
                        }
                        n2 = 0;
                        if (mad.powerup > 20.0f) {
                            n2 = 1;
                        }
                        if (mad.powerup > 40.0f) {
                            n2 = 2;
                        }
                        if (mad.powerup > 150.0f) {
                            n2 = 3;
                        }
                        if (mad.surfer) {
                            this.asay = " " + this.adj[4][(int)(this.m.random() * 3.0f)] + this.asay;
                        }
                        this.asay = n2 != 3 ? this.adj[n2][(int)(this.m.random() * 3.0f)] + this.asay + this.exlm[n2] : this.adj[n2][(int)(this.m.random() * 3.0f)];
                        if (!this.wasay) {
                            this.tcnt = this.auscnt;
                            this.say = mad.power != 98.0f ? "Power Up " + (int)(100.0f * mad.powerup / 98.0f) + "%" : "Power To The MAX";
                            this.skidup = !this.skidup;
                        }
                    }
                }
                if (mad.newcar) {
                    if (!this.wasay) {
                        this.say = "Car Fixed";
                        this.tcnt = 0;
                    }
                    this.crashup = !this.crashup;
                }
                for (int i = 0; i < this.nplayers; ++i) {
                    if (this.dested[i] == checkPoints.dested[i] || i == this.im) continue;
                    this.dested[i] = checkPoints.dested[i];
                    if (this.fase != 7001) {
                        if (this.dested[i] == 1) {
                            this.wasay = true;
                            this.say = "" + this.cd.names[this.sc[i]] + " has been wasted!";
                            this.tcnt = -15;
                        }
                        if (this.dested[i] != 2) continue;
                        this.wasay = true;
                        this.say = "You wasted " + this.cd.names[this.sc[i]] + "!";
                        this.tcnt = -15;
                        continue;
                    }
                    if (this.dested[i] == 1) {
                        this.wasay = true;
                        this.say = "" + this.plnames[i] + " has been wasted!";
                        this.tcnt = -15;
                    }
                    if (this.dested[i] == 2) {
                        this.wasay = true;
                        this.say = this.multion < 2 ? "You wasted " + this.plnames[i] + "!" : "" + this.plnames[this.im] + " wasted " + this.plnames[i] + "!";
                        this.tcnt = -15;
                    }
                    if (this.dested[i] != 3) continue;
                    this.wasay = true;
                    this.say = "" + this.plnames[i] + " has been wasted! (Disconnected)";
                    this.tcnt = -15;
                }
                if (this.multion >= 2 && this.alocked != this.lalocked) {
                    if (this.alocked != -1) {
                        this.wasay = false;
                        this.say = "Now following " + this.plnames[this.alocked] + "!";
                        this.tcnt = -15;
                    }
                    this.lalocked = this.alocked;
                    this.clear = mad.clear;
                }
                if (this.clear != mad.clear && mad.clear != 0) {
                    if (!this.wasay) {
                        this.say = "Checkpoint!";
                        this.tcnt = 15;
                    }
                    this.clear = mad.clear;
                    if (!this.mutes) {
                        this.checkpoint.play();
                    }
                    this.cntovn = 0;
                    if (this.cntan != 0) {
                        this.cntan = 0;
                    }
                }
            }
        }
        if (this.m.lightn != -1) {
            n = this.strack.sClip.stream.available();
            this.m.lton = false;
            if (n <= 6380001 && n > 5368001) {
                this.m.lton = true;
            }
            if (n <= 2992001 && n > 0x142441) {
                this.m.lton = true;
            }
        }
    }

    public void drawstat(int n, int n2, boolean bl, float f) {
        int[] nArray = new int[4];
        int[] nArray2 = new int[4];
        if (n2 > n) {
            n2 = n;
        }
        int n3 = (int)(98.0f * ((float)n2 / (float)n));
        nArray[0] = 662;
        nArray2[0] = 11;
        nArray[1] = 662;
        nArray2[1] = 20;
        nArray[2] = 662 + n3;
        nArray2[2] = 20;
        nArray[3] = 662 + n3;
        nArray2[3] = 11;
        int n4 = 244;
        int n5 = 244;
        int n6 = 11;
        if (n3 > 33) {
            n5 = (int)(244.0f - 233.0f * ((float)(n3 - 33) / 65.0f));
        }
        if (n3 > 70) {
            if (this.dmcnt < 10) {
                if (this.dmflk) {
                    n5 = 170;
                    this.dmflk = false;
                } else {
                    this.dmflk = true;
                }
            }
            ++this.dmcnt;
            if ((double)this.dmcnt > 167.0 - (double)n3 * 1.5) {
                this.dmcnt = 0;
            }
        }
        if ((n4 = (int)((float)n4 + (float)n4 * ((float)this.m.snap[0] / 100.0f))) > 255) {
            n4 = 255;
        }
        if (n4 < 0) {
            n4 = 0;
        }
        if ((n5 = (int)((float)n5 + (float)n5 * ((float)this.m.snap[1] / 100.0f))) > 255) {
            n5 = 255;
        }
        if (n5 < 0) {
            n5 = 0;
        }
        if ((n6 = (int)((float)n6 + (float)n6 * ((float)this.m.snap[2] / 100.0f))) > 255) {
            n6 = 255;
        }
        if (n6 < 0) {
            n6 = 0;
        }
        this.rd.setColor(new Color(n4, n5, n6));
        this.rd.fillPolygon(nArray, nArray2, 4);
        nArray[0] = 662;
        nArray2[0] = 31;
        nArray[1] = 662;
        nArray2[1] = 40;
        nArray[2] = (int)(662.0f + f);
        nArray2[2] = 40;
        nArray[3] = (int)(662.0f + f);
        nArray2[3] = 31;
        n4 = 128;
        if (f == 98.0f) {
            n4 = 64;
        }
        n5 = (int)(190.0 + (double)f * 0.37);
        n6 = 244;
        if (this.auscnt < 45 && this.aflk) {
            n4 = 128;
            n5 = 244;
            n6 = 244;
        }
        if ((n4 = (int)((float)n4 + (float)n4 * ((float)this.m.snap[0] / 100.0f))) > 255) {
            n4 = 255;
        }
        if (n4 < 0) {
            n4 = 0;
        }
        if ((n5 = (int)((float)n5 + (float)n5 * ((float)this.m.snap[1] / 100.0f))) > 255) {
            n5 = 255;
        }
        if (n5 < 0) {
            n5 = 0;
        }
        if ((n6 = (int)((float)n6 + (float)n6 * ((float)this.m.snap[2] / 100.0f))) > 255) {
            n6 = 255;
        }
        if (n6 < 0) {
            n6 = 0;
        }
        this.rd.setColor(new Color(n4, n5, n6));
        this.rd.fillPolygon(nArray, nArray2, 4);
    }

    public void drawhi(Image image, int n) {
        if (this.m.darksky) {
            Color color = new Color(this.m.csky[0], this.m.csky[1], this.m.csky[2]);
            float[] fArray = new float[3];
            Color.RGBtoHSB(this.m.csky[0], this.m.csky[1], this.m.csky[2], fArray);
            fArray[2] = 0.6f;
            color = Color.getHSBColor(fArray[0], fArray[1], fArray[2]);
            this.rd.setColor(color);
            this.rd.fillRoundRect(390 - image.getWidth(this.ob) / 2, n - 2, image.getWidth(this.ob) + 20, image.getHeight(this.ob) + 2, 7, 20);
            this.rd.setColor(new Color((int)((double)color.getRed() / 1.1), (int)((double)color.getGreen() / 1.1), (int)((double)color.getBlue() / 1.1)));
            this.rd.drawRoundRect(390 - image.getWidth(this.ob) / 2, n - 2, image.getWidth(this.ob) + 20, image.getHeight(this.ob) + 2, 7, 20);
        }
        this.rd.drawImage(image, 400 - image.getWidth(this.ob) / 2, n, null);
    }

    public void drawcs(int n, String string, int n2, int n3, int n4, int n5) {
        if (n5 != 3 && n5 != 4 && n5 != 5) {
            if ((n2 = (int)((float)n2 + (float)n2 * ((float)this.m.snap[0] / 100.0f))) > 255) {
                n2 = 255;
            }
            if (n2 < 0) {
                n2 = 0;
            }
            if ((n3 = (int)((float)n3 + (float)n3 * ((float)this.m.snap[1] / 100.0f))) > 255) {
                n3 = 255;
            }
            if (n3 < 0) {
                n3 = 0;
            }
            if ((n4 = (int)((float)n4 + (float)n4 * ((float)this.m.snap[2] / 100.0f))) > 255) {
                n4 = 255;
            }
            if (n4 < 0) {
                n4 = 0;
            }
        }
        if (n5 == 4) {
            if ((n2 = (int)((float)n2 - (float)n2 * ((float)this.m.snap[0] / 100.0f))) > 255) {
                n2 = 255;
            }
            if (n2 < 0) {
                n2 = 0;
            }
            if ((n3 = (int)((float)n3 - (float)n3 * ((float)this.m.snap[1] / 100.0f))) > 255) {
                n3 = 255;
            }
            if (n3 < 0) {
                n3 = 0;
            }
            if ((n4 = (int)((float)n4 - (float)n4 * ((float)this.m.snap[2] / 100.0f))) > 255) {
                n4 = 255;
            }
            if (n4 < 0) {
                n4 = 0;
            }
        }
        if (n5 == 1) {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawString(string, 400 - this.ftm.stringWidth(string) / 2 + 1, n + 1);
        }
        if (n5 == 2) {
            if ((n2 = (n2 * 2 + this.m.csky[0] * 1) / 3) > 255) {
                n2 = 255;
            }
            if (n2 < 0) {
                n2 = 0;
            }
            if ((n3 = (n3 * 2 + this.m.csky[1] * 1) / 3) > 255) {
                n3 = 255;
            }
            if (n3 < 0) {
                n3 = 0;
            }
            if ((n4 = (n4 * 2 + this.m.csky[2] * 1) / 3) > 255) {
                n4 = 255;
            }
            if (n4 < 0) {
                n4 = 0;
            }
        }
        if (n5 == 5) {
            this.rd.setColor(new Color(this.m.csky[0] / 2, this.m.csky[1] / 2, this.m.csky[2] / 2));
            this.rd.drawString(string, 400 - this.ftm.stringWidth(string) / 2 + 1, n + 1);
        }
        this.rd.setColor(new Color(n2, n3, n4));
        this.rd.drawString(string, 400 - this.ftm.stringWidth(string) / 2, n);
    }

    public void arrow(int n, int n2, CheckPoints checkPoints, boolean bl) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int[] nArray = new int[7];
        int[] nArray2 = new int[7];
        int[] nArray3 = new int[7];
        int n8 = 400;
        int n9 = -90;
        int n10 = 700;
        for (n7 = 0; n7 < 7; ++n7) {
            nArray2[n7] = n9;
        }
        nArray[0] = n8;
        nArray3[0] = n10 + 110;
        nArray[1] = n8 - 35;
        nArray3[1] = n10 + 50;
        nArray[2] = n8 - 15;
        nArray3[2] = n10 + 50;
        nArray[3] = n8 - 15;
        nArray3[3] = n10 - 50;
        nArray[4] = n8 + 15;
        nArray3[4] = n10 - 50;
        nArray[5] = n8 + 15;
        nArray3[5] = n10 + 50;
        nArray[6] = n8 + 35;
        nArray3[6] = n10 + 50;
        n7 = 0;
        if (!bl) {
            n6 = 0;
            if (checkPoints.x[n] - checkPoints.opx[this.im] >= 0) {
                n6 = 180;
            }
            n7 = (int)((double)(90 + n6) + Math.atan((double)(checkPoints.z[n] - checkPoints.opz[this.im]) / (double)(checkPoints.x[n] - checkPoints.opx[this.im])) / (Math.PI / 180));
        } else {
            n6 = 0;
            if (this.multion == 0 || this.alocked == -1) {
                n5 = -1;
                n4 = 0;
                for (n3 = 0; n3 < this.nplayers; ++n3) {
                    if (n3 == this.im || this.py(checkPoints.opx[this.im] / 100, checkPoints.opx[n3] / 100, checkPoints.opz[this.im] / 100, checkPoints.opz[n3] / 100) >= n5 && n5 != -1 || n4 != 0 && checkPoints.onscreen[n3] == 0 || checkPoints.dested[n3] != 0) continue;
                    n6 = n3;
                    n5 = this.py(checkPoints.opx[this.im] / 100, checkPoints.opx[n3] / 100, checkPoints.opz[this.im] / 100, checkPoints.opz[n3] / 100);
                    if (checkPoints.onscreen[n3] == 0) continue;
                    n4 = 1;
                }
            } else {
                n6 = this.alocked;
            }
            n5 = 0;
            if (checkPoints.opx[n6] - checkPoints.opx[this.im] >= 0) {
                n5 = 180;
            }
            n7 = (int)((double)(90 + n5) + Math.atan((double)(checkPoints.opz[n6] - checkPoints.opz[this.im]) / (double)(checkPoints.opx[n6] - checkPoints.opx[this.im])) / (Math.PI / 180));
            if (this.multion == 0) {
                this.drawcs(13, "[                                ]", 76, 67, 240, 0);
                this.drawcs(13, this.cd.names[this.sc[n6]], 0, 0, 0, 0);
            } else {
                this.rd.setFont(new Font("Arial", 1, 12));
                this.ftm = this.rd.getFontMetrics();
                this.drawcs(17, "[                                ]", 76, 67, 240, 0);
                this.drawcs(12, this.plnames[n6], 0, 0, 0, 0);
                this.rd.setFont(new Font("Arial", 0, 10));
                this.ftm = this.rd.getFontMetrics();
                this.drawcs(24, this.cd.names[this.sc[n6]], 0, 0, 0, 0);
                this.rd.setFont(new Font("Arial", 1, 11));
                this.ftm = this.rd.getFontMetrics();
            }
        }
        n7 += this.m.xz;
        while (n7 < 0) {
            n7 += 360;
        }
        while (n7 > 180) {
            n7 -= 360;
        }
        if (!bl) {
            if (n7 > 130) {
                n7 = 130;
            }
            if (n7 < -130) {
                n7 = -130;
            }
        } else {
            if (n7 > 100) {
                n7 = 100;
            }
            if (n7 < -100) {
                n7 = -100;
            }
        }
        if (Math.abs(this.ana - n7) < 180) {
            this.ana = Math.abs(this.ana - n7) < 10 ? n7 : (this.ana < n7 ? (this.ana += 10) : (this.ana -= 10));
        } else {
            if (n7 < 0) {
                this.ana += 15;
                if (this.ana > 180) {
                    this.ana -= 360;
                }
            }
            if (n7 > 0) {
                this.ana -= 15;
                if (this.ana < -180) {
                    this.ana += 360;
                }
            }
        }
        this.rot(nArray, nArray3, n8, n10, this.ana, 7);
        n7 = Math.abs(this.ana);
        this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (!bl) {
            if (n7 > 7 || n2 > 0 || n2 == -2 || this.cntan != 0) {
                for (n6 = 0; n6 < 7; ++n6) {
                    nArray[n6] = this.xs(nArray[n6], nArray3[n6]);
                    nArray2[n6] = this.ys(nArray2[n6], nArray3[n6]);
                }
                n6 = (int)(190.0f + 190.0f * ((float)this.m.snap[0] / 100.0f));
                if (n6 > 255) {
                    n6 = 255;
                }
                if (n6 < 0) {
                    n6 = 0;
                }
                if ((n5 = (int)(255.0f + 255.0f * ((float)this.m.snap[1] / 100.0f))) > 255) {
                    n5 = 255;
                }
                if (n5 < 0) {
                    n5 = 0;
                }
                n4 = 0;
                if (n2 <= 0) {
                    if (n7 <= 45 && n2 != -2 && this.cntan == 0) {
                        n6 = (n6 * n7 + this.m.csky[0] * (45 - n7)) / 45;
                        n5 = (n5 * n7 + this.m.csky[1] * (45 - n7)) / 45;
                        n4 = (n4 * n7 + this.m.csky[2] * (45 - n7)) / 45;
                    }
                    if (n7 >= 90) {
                        n3 = (int)(255.0f + 255.0f * ((float)this.m.snap[0] / 100.0f));
                        if (n3 > 255) {
                            n3 = 255;
                        }
                        if (n3 < 0) {
                            n3 = 0;
                        }
                        if ((n6 = (n6 * (140 - n7) + n3 * (n7 - 90)) / 50) > 255) {
                            n6 = 255;
                        }
                    }
                } else if (this.flk) {
                    n6 = (int)(255.0f + 255.0f * ((float)this.m.snap[0] / 100.0f));
                    if (n6 > 255) {
                        n6 = 255;
                    }
                    if (n6 < 0) {
                        n6 = 0;
                    }
                    this.flk = false;
                } else {
                    n6 = (int)(255.0f + 255.0f * ((float)this.m.snap[0] / 100.0f));
                    if (n6 > 255) {
                        n6 = 255;
                    }
                    if (n6 < 0) {
                        n6 = 0;
                    }
                    if ((n5 = (int)(220.0f + 220.0f * ((float)this.m.snap[1] / 100.0f))) > 255) {
                        n5 = 255;
                    }
                    if (n5 < 0) {
                        n5 = 0;
                    }
                    this.flk = true;
                }
                this.rd.setColor(new Color(n6, n5, n4));
                this.rd.fillPolygon(nArray, nArray2, 7);
                n6 = (int)(115.0f + 115.0f * ((float)this.m.snap[0] / 100.0f));
                if (n6 > 255) {
                    n6 = 255;
                }
                if (n6 < 0) {
                    n6 = 0;
                }
                if ((n5 = (int)(170.0f + 170.0f * ((float)this.m.snap[1] / 100.0f))) > 255) {
                    n5 = 255;
                }
                if (n5 < 0) {
                    n5 = 0;
                }
                n4 = 0;
                if (n2 <= 0) {
                    if (n7 <= 45 && n2 != -2 && this.cntan == 0) {
                        n6 = (n6 * n7 + this.m.csky[0] * (45 - n7)) / 45;
                        n5 = (n5 * n7 + this.m.csky[1] * (45 - n7)) / 45;
                        n4 = (n4 * n7 + this.m.csky[2] * (45 - n7)) / 45;
                    }
                } else if (this.flk) {
                    n6 = (int)(255.0f + 255.0f * ((float)this.m.snap[0] / 100.0f));
                    if (n6 > 255) {
                        n6 = 255;
                    }
                    if (n6 < 0) {
                        n6 = 0;
                    }
                    n5 = 0;
                }
                this.rd.setColor(new Color(n6, n5, n4));
                this.rd.drawPolygon(nArray, nArray2, 7);
            }
        } else {
            n6 = 0;
            if (this.multion != 0) {
                n6 = 8;
            }
            for (n5 = 0; n5 < 7; ++n5) {
                nArray[n5] = this.xs(nArray[n5], nArray3[n5]);
                nArray2[n5] = this.ys(nArray2[n5], nArray3[n5]) + n6;
            }
            n5 = (int)(159.0f + 159.0f * ((float)this.m.snap[0] / 100.0f));
            if (n5 > 255) {
                n5 = 255;
            }
            if (n5 < 0) {
                n5 = 0;
            }
            if ((n4 = (int)(207.0f + 207.0f * ((float)this.m.snap[1] / 100.0f))) > 255) {
                n4 = 255;
            }
            if (n4 < 0) {
                n4 = 0;
            }
            if ((n3 = (int)(255.0f + 255.0f * ((float)this.m.snap[2] / 100.0f))) > 255) {
                n3 = 255;
            }
            if (n3 < 0) {
                n3 = 0;
            }
            this.rd.setColor(new Color(n5, n4, n3));
            this.rd.fillPolygon(nArray, nArray2, 7);
            n5 = (int)(120.0f + 120.0f * ((float)this.m.snap[0] / 100.0f));
            if (n5 > 255) {
                n5 = 255;
            }
            if (n5 < 0) {
                n5 = 0;
            }
            if ((n4 = (int)(114.0f + 114.0f * ((float)this.m.snap[1] / 100.0f))) > 255) {
                n4 = 255;
            }
            if (n4 < 0) {
                n4 = 0;
            }
            if ((n3 = (int)(255.0f + 255.0f * ((float)this.m.snap[2] / 100.0f))) > 255) {
                n3 = 255;
            }
            if (n3 < 0) {
                n3 = 0;
            }
            this.rd.setColor(new Color(n5, n4, n3));
            this.rd.drawPolygon(nArray, nArray2, 7);
        }
        this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    public void radarstat(Mad mad, ContO contO, CheckPoints checkPoints) {
        int n;
        Object[] objectArray;
        Object object;
        int n2;
        int n3;
        int n4;
        this.rd.setComposite(AlphaComposite.getInstance(3, 0.5f));
        this.rd.setColor(new Color(this.m.csky[0], this.m.csky[1], this.m.csky[2]));
        this.rd.fillRoundRect(10, 55, 172, 172, 30, 30);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.rd.setColor(new Color(this.m.csky[0] / 2, this.m.csky[1] / 2, this.m.csky[2] / 2));
        for (n4 = 0; n4 < checkPoints.n; ++n4) {
            n3 = n4 + 1;
            if (n4 == checkPoints.n - 1) {
                n3 = 0;
            }
            n2 = 0;
            if (checkPoints.typ[n3] == -3) {
                n3 = 0;
                n2 = 1;
            }
            object = new int[]{(int)(96.0f - (float)(checkPoints.opx[this.im] - checkPoints.x[n4]) / checkPoints.prox), (int)(96.0f - (float)(checkPoints.opx[this.im] - checkPoints.x[n3]) / checkPoints.prox)};
            objectArray = new int[]{(int)(141.0f - (float)(checkPoints.z[n4] - checkPoints.opz[this.im]) / checkPoints.prox), (int)(141.0f - (float)(checkPoints.z[n3] - checkPoints.opz[this.im]) / checkPoints.prox)};
            this.rot((int[])object, (int[])objectArray, 96, 141, mad.cxz, 2);
            this.rd.drawLine(object[0], objectArray[0], object[1], objectArray[1]);
            if (n2 != 0) break;
        }
        n4 = 0;
        n3 = 0;
        n2 = 0;
        if (this.arrace || this.multion > 1) {
            object = new int[this.nplayers];
            objectArray = new int[this.nplayers];
            for (n = 0; n < this.nplayers; ++n) {
                object[n] = (int)(96.0f - (float)(checkPoints.opx[this.im] - checkPoints.opx[n]) / checkPoints.prox);
                objectArray[n] = (int)(141.0f - (float)(checkPoints.opz[n] - checkPoints.opz[this.im]) / checkPoints.prox);
            }
            this.rot((int[])object, (int[])objectArray, 96, 141, mad.cxz, this.nplayers);
            n4 = 0;
            n3 = (int)(80.0f + 80.0f * ((float)this.m.snap[1] / 100.0f));
            if (n3 > 255) {
                n3 = 255;
            }
            if (n3 < 0) {
                n3 = 0;
            }
            if ((n2 = (int)(159.0f + 159.0f * ((float)this.m.snap[2] / 100.0f))) > 255) {
                n2 = 255;
            }
            if (n2 < 0) {
                n2 = 0;
            }
            for (n = 0; n < this.nplayers; ++n) {
                if (n == this.im || checkPoints.dested[n] != 0) continue;
                if (this.clangame != 0) {
                    if (this.pclan[n].toLowerCase().equals(this.gaclan.toLowerCase())) {
                        n4 = 159;
                        n3 = 80;
                        n2 = 0;
                    } else {
                        n4 = 0;
                        n3 = 80;
                        n2 = 159;
                    }
                    n4 = (int)((float)n4 + (float)n4 * ((float)this.m.snap[0] / 100.0f));
                    if (n4 > 255) {
                        n4 = 255;
                    }
                    if (n4 < 0) {
                        n4 = 0;
                    }
                    if ((n3 = (int)((float)n3 + (float)n3 * ((float)this.m.snap[1] / 100.0f))) > 255) {
                        n3 = 255;
                    }
                    if (n3 < 0) {
                        n3 = 0;
                    }
                    if ((n2 = (int)((float)n2 + (float)n2 * ((float)this.m.snap[2] / 100.0f))) > 255) {
                        n2 = 255;
                    }
                    if (n2 < 0) {
                        n2 = 0;
                    }
                }
                int n5 = 2;
                if (this.alocked == n) {
                    n5 = 3;
                    this.rd.setColor(new Color(n4, n3, n2));
                } else {
                    this.rd.setColor(new Color((n4 + this.m.csky[0]) / 2, (this.m.csky[1] + n3) / 2, (n2 + this.m.csky[2]) / 2));
                }
                this.rd.drawLine(object[n] - n5, objectArray[n], object[n] + n5, objectArray[n]);
                this.rd.drawLine(object[n], objectArray[n] + n5, object[n], objectArray[n] - n5);
                this.rd.setColor(new Color(n4, n3, n2));
                this.rd.fillRect(object[n] - 1, objectArray[n] - 1, 3, 3);
            }
        }
        if ((n4 = (int)(159.0f + 159.0f * ((float)this.m.snap[0] / 100.0f))) > 255) {
            n4 = 255;
        }
        if (n4 < 0) {
            n4 = 0;
        }
        n3 = 0;
        n2 = 0;
        if (this.clangame != 0) {
            if (this.pclan[this.im].toLowerCase().equals(this.gaclan.toLowerCase())) {
                n4 = 159;
                n3 = 80;
                n2 = 0;
            } else {
                n4 = 0;
                n3 = 80;
                n2 = 159;
            }
            n4 = (int)((float)n4 + (float)n4 * ((float)this.m.snap[0] / 100.0f));
            if (n4 > 255) {
                n4 = 255;
            }
            if (n4 < 0) {
                n4 = 0;
            }
            if ((n3 = (int)((float)n3 + (float)n3 * ((float)this.m.snap[1] / 100.0f))) > 255) {
                n3 = 255;
            }
            if (n3 < 0) {
                n3 = 0;
            }
            if ((n2 = (int)((float)n2 + (float)n2 * ((float)this.m.snap[2] / 100.0f))) > 255) {
                n2 = 255;
            }
            if (n2 < 0) {
                n2 = 0;
            }
        }
        this.rd.setColor(new Color((n4 + this.m.csky[0]) / 2, (this.m.csky[1] + n3) / 2, (n2 + this.m.csky[2]) / 2));
        this.rd.drawLine(96, 139, 96, 143);
        this.rd.drawLine(94, 141, 98, 141);
        this.rd.setColor(new Color(n4, n3, n2));
        this.rd.fillRect(95, 140, 3, 3);
        this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        if (this.m.darksky) {
            object = new Color(this.m.csky[0], this.m.csky[1], this.m.csky[2]);
            objectArray = new float[3];
            Color.RGBtoHSB(this.m.csky[0], this.m.csky[1], this.m.csky[2], objectArray);
            objectArray[2] = (int)0.6f;
            object = Color.getHSBColor(objectArray[0], objectArray[1], objectArray[2]);
            this.rd.setColor((Color)object);
            this.rd.fillRect(5, 232, 181, 17);
            this.rd.drawLine(4, 233, 4, 247);
            this.rd.drawLine(3, 235, 3, 245);
            this.rd.drawLine(186, 233, 186, 247);
            this.rd.drawLine(187, 235, 187, 245);
        }
        this.rd.drawImage(this.sped, 7, 234, null);
        int n6 = contO.x - this.lcarx;
        this.lcarx = contO.x;
        int n7 = contO.y - this.lcary;
        this.lcary = contO.y;
        n = contO.z - this.lcarz;
        this.lcarz = contO.z;
        float f = (float)Math.sqrt(n6 * n6 + n * n);
        float f2 = f * 1.4f * 21.0f * 60.0f * 60.0f / 100000.0f;
        float f3 = f2 * 0.621371f;
        this.rd.setColor(new Color(0, 0, 100));
        this.rd.drawString("" + (int)f2, 62, 245);
        this.rd.drawString("" + (int)f3, 132, 245);
    }

    public void playsounds(Mad mad, Control control, int n) {
        if (!(this.fase != 0 && this.fase != 7001 || this.starcnt >= 35 || this.cntwis == 8 || this.mutes)) {
            boolean bl = control.up && mad.speed > 0.0f || control.down && mad.speed < 10.0f;
            boolean bl2 = mad.skid == 1 && control.handb || Math.abs(mad.scz[0] - (mad.scz[1] + mad.scz[0] + mad.scz[2] + mad.scz[3]) / 4.0f) > 1.0f || Math.abs(mad.scx[0] - (mad.scx[1] + mad.scx[0] + mad.scx[2] + mad.scx[3]) / 4.0f) > 1.0f;
            boolean bl3 = false;
            if (control.up && mad.speed < 10.0f) {
                bl2 = true;
                bl = true;
                bl3 = true;
            }
            if (bl && mad.mtouch) {
                if (!mad.capsized) {
                    if (!bl2) {
                        if (mad.power != 98.0f) {
                            int n2;
                            if (Math.abs(mad.speed) > 0.0f && Math.abs(mad.speed) <= (float)this.cd.swits[mad.cn][0]) {
                                n2 = (int)(3.0f * Math.abs(mad.speed) / (float)this.cd.swits[mad.cn][0]);
                                if (n2 == 2) {
                                    if (this.pwait == 0) {
                                        n2 = 0;
                                    } else {
                                        --this.pwait;
                                    }
                                } else {
                                    this.pwait = 7;
                                }
                                this.sparkeng(n2, mad.cn);
                            }
                            if (Math.abs(mad.speed) > (float)this.cd.swits[mad.cn][0] && Math.abs(mad.speed) <= (float)this.cd.swits[mad.cn][1]) {
                                n2 = (int)(3.0f * (Math.abs(mad.speed) - (float)this.cd.swits[mad.cn][0]) / (float)(this.cd.swits[mad.cn][1] - this.cd.swits[mad.cn][0]));
                                if (n2 == 2) {
                                    if (this.pwait == 0) {
                                        n2 = 0;
                                    } else {
                                        --this.pwait;
                                    }
                                } else {
                                    this.pwait = 7;
                                }
                                this.sparkeng(n2, mad.cn);
                            }
                            if (Math.abs(mad.speed) > (float)this.cd.swits[mad.cn][1] && Math.abs(mad.speed) <= (float)this.cd.swits[mad.cn][2]) {
                                n2 = (int)(3.0f * (Math.abs(mad.speed) - (float)this.cd.swits[mad.cn][1]) / (float)(this.cd.swits[mad.cn][2] - this.cd.swits[mad.cn][1]));
                                this.sparkeng(n2, mad.cn);
                            }
                        } else {
                            int n3 = 2;
                            if (this.pwait == 0) {
                                if (Math.abs(mad.speed) > (float)this.cd.swits[mad.cn][1]) {
                                    n3 = 3;
                                }
                            } else {
                                --this.pwait;
                            }
                            this.sparkeng(n3, mad.cn);
                        }
                    } else {
                        this.sparkeng(-1, mad.cn);
                        if (bl3) {
                            if (this.stopcnt <= 0) {
                                this.air[5].loop();
                                this.stopcnt = 10;
                            }
                        } else if (this.stopcnt <= -2) {
                            this.air[2 + (int)(this.m.random() * 3.0f)].loop();
                            this.stopcnt = 7;
                        }
                    }
                } else {
                    this.sparkeng(3, mad.cn);
                }
                this.grrd = false;
                this.aird = false;
            } else {
                this.pwait = 15;
                if (!mad.mtouch && !this.grrd && (double)this.m.random() > 0.4) {
                    this.air[(int)(this.m.random() * 4.0f)].loop();
                    this.stopcnt = 5;
                    this.grrd = true;
                }
                if (!mad.wtouch && !this.aird) {
                    this.stopairs();
                    this.air[(int)(this.m.random() * 4.0f)].loop();
                    this.stopcnt = 10;
                    this.aird = true;
                }
                this.sparkeng(-1, mad.cn);
            }
            if (mad.cntdest != 0 && this.cntwis < 7) {
                if (!this.pwastd) {
                    this.wastd.loop();
                    this.pwastd = true;
                }
            } else {
                if (this.pwastd) {
                    this.wastd.stop();
                    this.pwastd = false;
                }
                if (this.cntwis == 7 && !this.mutes) {
                    this.firewasted.play();
                }
            }
        } else {
            this.sparkeng(-2, mad.cn);
            if (this.pwastd) {
                this.wastd.stop();
                this.pwastd = false;
            }
        }
        if (this.stopcnt != -20) {
            if (this.stopcnt == 1) {
                this.stopairs();
            }
            --this.stopcnt;
        }
        if (this.bfcrash != 0) {
            --this.bfcrash;
        }
        if (this.bfscrape != 0) {
            --this.bfscrape;
        }
        if (this.bfsc1 != 0) {
            --this.bfsc1;
        }
        if (this.bfsc2 != 0) {
            --this.bfsc2;
        }
        if (this.bfskid != 0) {
            --this.bfskid;
        }
        if (mad.newcar) {
            this.cntwis = 0;
        }
        if (this.fase == 0 || this.fase == 7001 || this.fase == 6 || this.fase == -1 || this.fase == -2 || this.fase == -3 || this.fase == -4 || this.fase == -5) {
            if (this.mutes != control.mutes) {
                this.mutes = control.mutes;
            }
            if (control.mutem != this.mutem) {
                this.mutem = control.mutem;
                if (this.mutem) {
                    if (this.loadedt) {
                        this.strack.stop();
                    }
                } else if (this.loadedt) {
                    this.strack.resume();
                }
            }
        }
        if (mad.cntdest != 0 && this.cntwis < 7) {
            if (mad.dest) {
                ++this.cntwis;
            }
        } else {
            if (mad.cntdest == 0) {
                this.cntwis = 0;
            }
            if (this.cntwis == 7) {
                this.cntwis = 8;
            }
        }
        if (this.app.applejava) {
            this.closesounds();
        }
    }

    public void stopairs() {
        for (int i = 0; i < 6; ++i) {
            this.air[i].stop();
        }
    }

    public void sparkeng(int n, int n2) {
        int n3;
        if (this.lcn != n2) {
            for (n3 = 0; n3 < 5; ++n3) {
                if (!this.pengs[n3]) continue;
                this.engs[this.cd.enginsignature[this.lcn]][n3].stop();
                this.pengs[n3] = false;
            }
            this.lcn = n2;
        }
        ++n;
        for (n3 = 0; n3 < 5; ++n3) {
            if (n == n3) {
                if (this.pengs[n3]) continue;
                this.engs[this.cd.enginsignature[n2]][n3].loop();
                this.pengs[n3] = true;
                continue;
            }
            if (!this.pengs[n3]) continue;
            this.engs[this.cd.enginsignature[n2]][n3].stop();
            this.pengs[n3] = false;
        }
    }

    public void crash(float f, int n) {
        if (this.bfcrash == 0) {
            if (n == 0) {
                if (Math.abs(f) > 25.0f && Math.abs(f) < 170.0f) {
                    if (!this.mutes) {
                        this.lowcrash[this.crshturn].play();
                    }
                    this.bfcrash = 2;
                }
                if (Math.abs(f) >= 170.0f) {
                    if (!this.mutes) {
                        this.crash[this.crshturn].play();
                    }
                    this.bfcrash = 2;
                }
                if (Math.abs(f) > 25.0f) {
                    this.crshturn = this.crashup ? --this.crshturn : ++this.crshturn;
                    if (this.crshturn == -1) {
                        this.crshturn = 2;
                    }
                    if (this.crshturn == 3) {
                        this.crshturn = 0;
                    }
                }
            }
            if (n == -1) {
                if (Math.abs(f) > 25.0f && Math.abs(f) < 170.0f) {
                    if (!this.mutes) {
                        this.lowcrash[2].play();
                    }
                    this.bfcrash = 2;
                }
                if (Math.abs(f) > 170.0f) {
                    if (!this.mutes) {
                        this.crash[2].play();
                    }
                    this.bfcrash = 2;
                }
            }
            if (n == 1) {
                if (!this.mutes) {
                    this.tires.play();
                }
                this.bfcrash = 3;
            }
        }
    }

    public void skid(int n, float f) {
        if (this.bfcrash == 0 && this.bfskid == 0 && f > 150.0f) {
            if (n == 0) {
                if (!this.mutes) {
                    this.skid[this.skflg].play();
                }
                this.skflg = this.skidup ? ++this.skflg : --this.skflg;
                if (this.skflg == 3) {
                    this.skflg = 0;
                }
                if (this.skflg == -1) {
                    this.skflg = 2;
                }
            } else {
                if (!this.mutes) {
                    this.dustskid[this.dskflg].play();
                }
                this.dskflg = this.skidup ? ++this.dskflg : --this.dskflg;
                if (this.dskflg == 3) {
                    this.dskflg = 0;
                }
                if (this.dskflg == -1) {
                    this.dskflg = 2;
                }
            }
            this.bfskid = 35;
        }
    }

    public void scrape(int n, int n2, int n3) {
        if (this.bfscrape == 0 && Math.sqrt(n * n + n2 * n2 + n3 * n3) / 10.0 > 10.0) {
            int n4 = 0;
            if (this.m.random() > this.m.random()) {
                n4 = 1;
            }
            if (n4 == 0) {
                this.sturn1 = 0;
                ++this.sturn0;
                if (this.sturn0 == 3) {
                    n4 = 1;
                    this.sturn1 = 1;
                    this.sturn0 = 0;
                }
            } else {
                this.sturn0 = 0;
                ++this.sturn1;
                if (this.sturn1 == 3) {
                    n4 = 0;
                    this.sturn0 = 1;
                    this.sturn1 = 0;
                }
            }
            if (!this.mutes) {
                this.scrape[n4].play();
            }
            this.bfscrape = 5;
        }
    }

    public void gscrape(int n, int n2, int n3) {
        if ((this.bfsc1 == 0 || this.bfsc2 == 0) && Math.sqrt(n * n + n2 * n2 + n3 * n3) / 10.0 > 15.0) {
            if (this.bfsc1 == 0) {
                if (!this.mutes) {
                    this.scrape[2].stop();
                    this.scrape[2].play();
                }
                this.bfsc1 = 12;
                this.bfsc2 = 6;
            } else {
                if (!this.mutes) {
                    this.scrape[3].stop();
                    this.scrape[3].play();
                }
                this.bfsc2 = 12;
                this.bfsc1 = 6;
            }
        }
    }

    public void closesounds() {
        int n;
        for (n = 0; n < 5; ++n) {
            for (int i = 0; i < 5; ++i) {
                this.engs[n][i].checkopen();
            }
        }
        for (n = 0; n < 6; ++n) {
            this.air[n].checkopen();
        }
        this.tires.checkopen();
        this.checkpoint.checkopen();
        this.carfixed.checkopen();
        this.powerup.checkopen();
        this.three.checkopen();
        this.two.checkopen();
        this.one.checkopen();
        this.go.checkopen();
        this.wastd.checkopen();
        this.firewasted.checkopen();
        for (n = 0; n < 3; ++n) {
            this.skid[n].checkopen();
            this.dustskid[n].checkopen();
            this.crash[n].checkopen();
            this.lowcrash[n].checkopen();
            this.scrape[n].checkopen();
        }
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

    public int py(int n, int n2, int n3, int n4) {
        return (n - n2) * (n - n2) + (n3 - n4) * (n3 - n4);
    }

    public float pys(int n, int n2, int n3, int n4) {
        return (float)Math.sqrt((n - n2) * (n - n2) + (n3 - n4) * (n3 - n4));
    }

    public void snap(int n) {
        int n2;
        this.dmg = this.loadsnap(this.odmg);
        this.pwr = this.loadsnap(this.opwr);
        this.was = this.loadsnap(this.owas);
        this.lap = this.loadsnap(this.olap);
        this.pos = this.loadsnap(this.opos);
        this.sped = this.loadsnap(this.osped);
        for (n2 = 0; n2 < 8; ++n2) {
            this.rank[n2] = this.loadsnap(this.orank[n2]);
        }
        for (n2 = 0; n2 < 4; ++n2) {
            this.cntdn[n2] = this.loadsnap(this.ocntdn[n2]);
        }
        if (this.multion != 0) {
            this.wgame = this.loadsnap(this.owgame);
            this.exitgame = this.loadsnap(this.oexitgame);
            this.gamefinished = this.loadsnap(this.ogamefinished);
            this.disco = this.loadsnap(this.odisco);
        }
        this.yourwasted = this.loadsnap(this.oyourwasted);
        this.youlost = this.loadsnap(this.oyoulost);
        this.youwon = this.loadsnap(this.oyouwon);
        this.youwastedem = this.loadsnap(this.oyouwastedem);
        this.gameh = this.loadsnap(this.ogameh);
        this.loadingmusic = this.loadopsnap(this.oloadingmusic, n, 76);
        this.star[0] = this.loadopsnap(this.ostar[0], n, 0);
        this.star[1] = this.loadopsnap(this.ostar[1], n, 0);
        this.flaot = this.loadopsnap(this.oflaot, n, 1);
    }

    public boolean overon(int n, int n2, int n3, int n4, int n5, int n6) {
        return n5 > n && n5 < n + n3 && n6 > n2 && n6 < n2 + n4;
    }

    public boolean over(Image image, int n, int n2, int n3, int n4) {
        int n5 = image.getHeight(this.ob);
        int n6 = image.getWidth(this.ob);
        return n > n3 - 5 && n < n3 + n6 + 5 && n2 > n4 - 5 && n2 < n4 + n5 + 5;
    }

    public void fleximage(Image image, int n, int n2) {
        if (!this.badmac) {
            if (n == 0) {
                this.flexpix = new int[360000];
                PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, 800, 450, this.flexpix, 0, 800);
                try {
                    pixelGrabber.grabPixels();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = (int)(Math.random() * 128.0);
            int n8 = (int)(5.0 + Math.random() * 15.0);
            for (int i = 0; i < 360000; ++i) {
                Color color = new Color(this.flexpix[i]);
                int n9 = 0;
                int n10 = 0;
                int n11 = 0;
                if (n3 == 0) {
                    n4 = n9 = color.getRed();
                    n5 = n10 = color.getGreen();
                    n6 = n11 = color.getBlue();
                } else {
                    n4 = n9 = (int)(((float)color.getRed() + (float)n4 * 0.38f * (float)n) / (1.0f + 0.38f * (float)n));
                    n5 = n10 = (int)(((float)color.getGreen() + (float)n5 * 0.38f * (float)n) / (1.0f + 0.38f * (float)n));
                    n6 = n11 = (int)(((float)color.getBlue() + (float)n6 * 0.38f * (float)n) / (1.0f + 0.38f * (float)n));
                }
                if (++n3 == 800) {
                    n3 = 0;
                }
                int n12 = (int)((float)(n9 * 17 + n10 + n11 + n7) / 21.0f);
                int n13 = (int)((float)(n10 * 17 + n9 + n11 + n7) / 22.0f);
                int n14 = (int)((float)(n11 * 17 + n9 + n10 + n7) / 24.0f);
                if (--n8 == 0) {
                    n7 = (int)(Math.random() * 128.0);
                    n8 = (int)(5.0 + Math.random() * 15.0);
                }
                Color color2 = new Color(n12, n13, n14);
                this.flexpix[i] = color2.getRGB();
            }
            this.fleximg = this.createImage(new MemoryImageSource(800, 450, this.flexpix, 0, 800));
            this.rd.drawImage(this.fleximg, 0, 0, null);
        } else {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.setComposite(AlphaComposite.getInstance(3, 0.1f));
            this.rd.fillRect(0, 0, 800, 450);
            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        }
    }

    private Image loadsnap(Image image) {
        int n = image.getHeight(this.ob);
        int n2 = image.getWidth(this.ob);
        int[] nArray = new int[n2 * n];
        PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, n2, n, nArray, 0, n2);
        try {
            pixelGrabber.grabPixels();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        for (int i = 0; i < n2 * n; ++i) {
            int n3;
            Color color = new Color(nArray[n2 * n - 1]);
            Color color2 = new Color(nArray[i]);
            if (color2.getRed() != color2.getGreen() && color2.getGreen() != color2.getBlue()) {
                int n4;
                int n5;
                n3 = (int)((float)color2.getRed() + (float)color2.getRed() * ((float)this.m.snap[0] / 100.0f));
                if (n3 > 255) {
                    n3 = 255;
                }
                if (n3 < 0) {
                    n3 = 0;
                }
                if ((n5 = (int)((float)color2.getGreen() + (float)color2.getGreen() * ((float)this.m.snap[1] / 100.0f))) > 255) {
                    n5 = 255;
                }
                if (n5 < 0) {
                    n5 = 0;
                }
                if ((n4 = (int)((float)color2.getBlue() + (float)color2.getBlue() * ((float)this.m.snap[2] / 100.0f))) > 255) {
                    n4 = 255;
                }
                if (n4 < 0) {
                    n4 = 0;
                }
                nArray[i] = 0xFF000000 | n3 << 16 | n5 << 8 | n4;
                continue;
            }
            n3 = (int)((float)(color.getRed() - color2.getRed()) / (float)color.getRed() * 255.0f);
            if (n3 > 255) {
                n3 = 255;
            }
            if (n3 < 0) {
                n3 = 0;
            }
            nArray[i] = n3 << 24 | 0 | 0 | 0;
        }
        BufferedImage bufferedImage = new BufferedImage(n2, n, 2);
        bufferedImage.setRGB(0, 0, n2, n, nArray, 0, n2);
        return bufferedImage;
    }

    private Image loadopsnap(Image image, int n, int n2) {
        int n3;
        int n4 = image.getHeight(this.ob);
        int n5 = image.getWidth(this.ob);
        int[] nArray = new int[n5 * n4];
        PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, n5, n4, nArray, 0, n5);
        try {
            pixelGrabber.grabPixels();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        if (n < 0) {
            n = 33;
        }
        int n6 = 0;
        if (n2 == 1) {
            n6 = nArray[61993];
        }
        int[] nArray2 = new int[]{this.m.snap[0], this.m.snap[1], this.m.snap[2]};
        while (nArray2[0] + nArray2[1] + nArray2[2] < -30) {
            for (n3 = 0; n3 < 3; ++n3) {
                if (nArray2[n3] >= 50) continue;
                int n7 = n3;
                nArray2[n7] = nArray2[n7] + 1;
            }
        }
        for (n3 = 0; n3 < n5 * n4; ++n3) {
            if (nArray[n3] == nArray[n2]) continue;
            Color color = new Color(nArray[n3]);
            int n8 = 0;
            int n9 = 0;
            int n10 = 0;
            if (n2 == 1 && nArray[n3] == n6) {
                n8 = (int)(237.0f - 237.0f * ((float)nArray2[0] / 150.0f));
                if (n8 > 255) {
                    n8 = 255;
                }
                if (n8 < 0) {
                    n8 = 0;
                }
                if ((n9 = (int)(237.0f - 237.0f * ((float)nArray2[1] / 150.0f))) > 255) {
                    n9 = 255;
                }
                if (n9 < 0) {
                    n9 = 0;
                }
                if ((n10 = (int)(237.0f - 237.0f * ((float)nArray2[2] / 150.0f))) > 255) {
                    n10 = 255;
                }
                if (n10 < 0) {
                    n10 = 0;
                }
                if (n == 11) {
                    n8 = 250;
                    n9 = 250;
                    n10 = 250;
                }
            } else {
                n8 = (int)((float)color.getRed() - (float)color.getRed() * ((float)nArray2[0] / 100.0f));
                if (n8 > 255) {
                    n8 = 255;
                }
                if (n8 < 0) {
                    n8 = 0;
                }
                if ((n9 = (int)((float)color.getGreen() - (float)color.getGreen() * ((float)nArray2[1] / 100.0f))) > 255) {
                    n9 = 255;
                }
                if (n9 < 0) {
                    n9 = 0;
                }
                if ((n10 = (int)((float)color.getBlue() - (float)color.getBlue() * ((float)nArray2[2] / 100.0f))) > 255) {
                    n10 = 255;
                }
                if (n10 < 0) {
                    n10 = 0;
                }
            }
            Color color2 = new Color(n8, n9, n10);
            nArray[n3] = color2.getRGB();
        }
        Image image2 = this.createImage(new MemoryImageSource(n5, n4, nArray, 0, n5));
        return image2;
    }

    private Image pressed(Image image) {
        int n = image.getHeight(this.ob);
        int n2 = image.getWidth(this.ob);
        int[] nArray = new int[n2 * n];
        PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, n2, n, nArray, 0, n2);
        try {
            pixelGrabber.grabPixels();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        for (int i = 0; i < n2 * n; ++i) {
            if (nArray[i] == nArray[n2 * n - 1]) continue;
            nArray[i] = -16777216;
        }
        Image image2 = this.createImage(new MemoryImageSource(n2, n, nArray, 0, n2));
        return image2;
    }

    private Image bressed(Image image) {
        int n = image.getHeight(this.ob);
        int n2 = image.getWidth(this.ob);
        int[] nArray = new int[n2 * n];
        PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, n2, n, nArray, 0, n2);
        try {
            pixelGrabber.grabPixels();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        Color color = new Color(247, 255, 165);
        for (int i = 0; i < n2 * n; ++i) {
            if (nArray[i] == nArray[n2 * n - 1]) continue;
            nArray[i] = color.getRGB();
        }
        Image image2 = this.createImage(new MemoryImageSource(n2, n, nArray, 0, n2));
        return image2;
    }

    public void pauseimage(Image image) {
        if (!this.badmac) {
            int[] nArray = new int[360000];
            PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, 800, 450, nArray, 0, 800);
            try {
                pixelGrabber.grabPixels();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            for (int i = 0; i < 360000; ++i) {
                Color color = new Color(nArray[i]);
                int n5 = 0;
                n3 = n4 == 0 ? (n5 = (color.getRed() + color.getGreen() + color.getBlue()) / 3) : (n5 = (color.getRed() + color.getGreen() + color.getBlue() + n3 * 30) / 33);
                if (++n4 == 800) {
                    n4 = 0;
                }
                if (i > 800 * (8 + n2) + 281 && n2 < 188) {
                    int n6 = (n5 + 60) / 3;
                    int n7 = (n5 + 135) / 3;
                    int n8 = (n5 + 220) / 3;
                    if (++n == 237) {
                        ++n2;
                        n = 0;
                    }
                    Color color2 = new Color(n6, n7, n8);
                    nArray[i] = color2.getRGB();
                    continue;
                }
                Color color3 = new Color(n5, n5, n5);
                nArray[i] = color3.getRGB();
            }
            this.fleximg = this.createImage(new MemoryImageSource(800, 450, nArray, 0, 800));
            this.rd.drawImage(this.fleximg, 0, 0, null);
        } else {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.setComposite(AlphaComposite.getInstance(3, 0.5f));
            this.rd.fillRect(0, 0, 800, 450);
            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        }
    }

    public void jflexo() {
        if (!this.badmac) {
            int[] nArray = new int[360000];
            PixelGrabber pixelGrabber = new PixelGrabber(this.app.offImage, 0, 0, 800, 450, nArray, 0, 800);
            try {
                pixelGrabber.grabPixels();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            for (int i = 0; i < 360000; ++i) {
                Color color = new Color(nArray[i]);
                int n5 = 0;
                int n6 = 0;
                int n7 = 0;
                if (n4 == 0) {
                    n = n5 = color.getRed();
                    n3 = n6 = color.getGreen();
                    n2 = n7 = color.getBlue();
                } else {
                    n = n5 = (color.getRed() + n * 10) / 11;
                    n3 = n6 = (color.getGreen() + n3 * 10) / 11;
                    n2 = n7 = (color.getBlue() + n2 * 10) / 11;
                }
                if (++n4 == 800) {
                    n4 = 0;
                }
                Color color2 = new Color(n5, n6, n7);
                nArray[i] = color2.getRGB();
            }
            Image image = this.createImage(new MemoryImageSource(800, 450, nArray, 0, 800));
            this.rd.drawImage(image, 0, 0, null);
        } else {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.setComposite(AlphaComposite.getInstance(3, 0.5f));
            this.rd.fillRect(0, 0, 800, 450);
            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        }
    }

    public void pingstat() {
        int n = (int)(100.0 * Math.random());
        try {
            URL uRL = new URL("http://c.statcounter.com/9994681/0/14bb645e/1/?reco=" + n + "");
            uRL.openConnection().setConnectTimeout(5000);
            Image image = Toolkit.getDefaultToolkit().createImage(uRL);
            MediaTracker mediaTracker = new MediaTracker(this.app);
            mediaTracker.addImage(image, 0);
            mediaTracker.waitForID(0);
            mediaTracker.removeImage(image, 0);
            mediaTracker = null;
            image = null;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public Image getImage(String string) {
        Image image = Toolkit.getDefaultToolkit().createImage("" + Madness.fpath + "" + string + "");
        MediaTracker mediaTracker = new MediaTracker(this.app);
        mediaTracker.addImage(image, 0);
        try {
            mediaTracker.waitForID(0);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return image;
    }

    private Image loadimage(byte[] byArray, MediaTracker mediaTracker, Toolkit toolkit) {
        Image image = toolkit.createImage(byArray);
        mediaTracker.addImage(image, 0);
        try {
            mediaTracker.waitForID(0);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return image;
    }

    private Image loadude(byte[] byArray, MediaTracker mediaTracker, Toolkit toolkit) {
        Image image = toolkit.createImage(byArray);
        mediaTracker.addImage(image, 0);
        try {
            mediaTracker.waitForID(0);
        }
        catch (Exception exception) {
            // empty catch block
        }
        int n = image.getHeight(this.ob);
        int n2 = image.getWidth(this.ob);
        int[] nArray = new int[n2 * n];
        PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, n2, n, nArray, 0, n2);
        try {
            pixelGrabber.grabPixels();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        for (int i = 0; i < n2 * n; ++i) {
            Color color = new Color(nArray[i]);
            if (color.getGreen() <= color.getRed() + 5 || color.getGreen() <= color.getBlue() + 5) continue;
            int n3 = (int)(255.0f - (float)(color.getGreen() - (color.getRed() + color.getBlue()) / 2) * 1.5f);
            if (n3 > 255) {
                n3 = 255;
            }
            if (n3 < 0) {
                n3 = 0;
            }
            nArray[i] = n3 << 24 | 0 | 0 | 0;
        }
        BufferedImage bufferedImage = new BufferedImage(n2, n, 2);
        bufferedImage.setRGB(0, 0, n2, n, nArray, 0, n2);
        image = null;
        return bufferedImage;
    }

    private Image loadBimage(byte[] byArray, MediaTracker mediaTracker, Toolkit toolkit, int n) {
        Image image = toolkit.createImage(byArray);
        mediaTracker.addImage(image, 0);
        try {
            mediaTracker.waitForID(0);
        }
        catch (Exception exception) {
            // empty catch block
        }
        int n2 = image.getHeight(this.ob);
        int n3 = image.getWidth(this.ob);
        int[] nArray = new int[n3 * n2];
        PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, n3, n2, nArray, 0, n3);
        try {
            pixelGrabber.grabPixels();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        for (int i = 0; i < n3 * n2; ++i) {
            if (nArray[i] == nArray[0] && n == 0) continue;
            Color color = new Color(nArray[i]);
            float[] fArray = new float[3];
            Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), fArray);
            fArray[0] = 0.12f;
            fArray[1] = 0.45f;
            if (n == 3) {
                fArray[0] = 0.13f;
                fArray[1] = 0.45f;
            }
            Color color2 = Color.getHSBColor(fArray[0], fArray[1], fArray[2]);
            nArray[i] = color2.getRGB();
        }
        if (n == 2) {
            Color color = new Color(nArray[0]);
            int n4 = 0x40000000 | color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
            color = new Color(nArray[1]);
            int n5 = Integer.MIN_VALUE | color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
            for (int i = 2; i < n3 * n2; ++i) {
                if (nArray[i] == nArray[0]) {
                    nArray[i] = n4;
                }
                if (nArray[i] != nArray[1]) continue;
                nArray[i] = n5;
            }
            nArray[0] = n4;
            nArray[1] = n5;
        }
        Image image2 = null;
        if (n == 2) {
            BufferedImage bufferedImage = new BufferedImage(n3, n2, 2);
            bufferedImage.setRGB(0, 0, n3, n2, nArray, 0, n3);
            image2 = bufferedImage;
        } else {
            image2 = this.createImage(new MemoryImageSource(n3, n2, nArray, 0, n3));
        }
        image = null;
        return image2;
    }

    private Image dodgen(Image image) {
        int n = image.getHeight(this.ob);
        int n2 = image.getWidth(this.ob);
        int[] nArray = new int[n2 * n];
        PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, n2, n, nArray, 0, n2);
        try {
            pixelGrabber.grabPixels();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        for (int i = 0; i < n2 * n; ++i) {
            int n3;
            int n4;
            Color color = new Color(nArray[i]);
            int n5 = color.getRed() * 4 + 90;
            if (n5 > 255) {
                n5 = 255;
            }
            if (n5 < 0) {
                n5 = 0;
            }
            if ((n4 = color.getGreen() * 4 + 90) > 255) {
                n4 = 255;
            }
            if (n4 < 0) {
                n4 = 0;
            }
            if ((n3 = color.getBlue() * 4 + 90) > 255) {
                n3 = 255;
            }
            if (n3 < 0) {
                n3 = 0;
            }
            Color color2 = new Color(n5, n4, n3);
            nArray[i] = color2.getRGB();
        }
        Image image2 = this.createImage(new MemoryImageSource(n2, n, nArray, 0, n2));
        return image2;
    }

    private void smokeypix(byte[] byArray, MediaTracker mediaTracker, Toolkit toolkit) {
        Image image = toolkit.createImage(byArray);
        mediaTracker.addImage(image, 0);
        try {
            mediaTracker.waitForID(0);
        }
        catch (Exception exception) {
            // empty catch block
        }
        PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, 466, 202, this.smokey, 0, 466);
        try {
            pixelGrabber.grabPixels();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        for (int i = 0; i < 94132; ++i) {
            if (this.smokey[i] == this.smokey[0]) continue;
            Color color = new Color(this.smokey[i]);
            float[] fArray = new float[3];
            Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), fArray);
            fArray[0] = 0.11f;
            fArray[1] = 0.45f;
            Color color2 = Color.getHSBColor(fArray[0], fArray[1], fArray[2]);
            this.smokey[i] = color2.getRGB();
        }
    }

    private void makecarsbgc(Image image, Image image2) {
        int[] nArray = new int[268000];
        PixelGrabber pixelGrabber = new PixelGrabber(this.carsbg, 0, 0, 670, 400, nArray, 0, 670);
        try {
            pixelGrabber.grabPixels();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        int[] nArray2 = new int[20700];
        PixelGrabber pixelGrabber2 = new PixelGrabber(image, 0, 0, 92, 225, nArray2, 0, 92);
        try {
            pixelGrabber2.grabPixels();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        int[] nArray3 = new int[2112];
        PixelGrabber pixelGrabber3 = new PixelGrabber(image2, 0, 0, 88, 24, nArray3, 0, 88);
        try {
            pixelGrabber3.grabPixels();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        for (int i = 0; i < 670; ++i) {
            for (int j = 0; j < 400; ++j) {
                Color color;
                int n;
                int n2;
                int n3;
                Color color2;
                Color color3;
                if (i > 14 && i < 106 && j > 11 && j < 236 && nArray2[i - 14 + (j - 11) * 92] != nArray2[0]) {
                    color3 = new Color(nArray[i + j * 670]);
                    color2 = new Color(nArray2[i - 14 + (j - 11) * 92]);
                    n3 = (int)((double)color3.getRed() * 0.33 + (double)color2.getRed() * 0.67);
                    if (n3 > 255) {
                        n3 = 255;
                    }
                    if (n3 < 0) {
                        n3 = 0;
                    }
                    if ((n2 = (int)((double)color3.getGreen() * 0.33 + (double)color2.getGreen() * 0.67)) > 255) {
                        n2 = 255;
                    }
                    if (n2 < 0) {
                        n2 = 0;
                    }
                    if ((n = (int)((double)color3.getBlue() * 0.33 + (double)color2.getBlue() * 0.67)) > 255) {
                        n = 255;
                    }
                    if (n < 0) {
                        n = 0;
                    }
                    color = new Color(n3, n2, n);
                    nArray[i + j * 670] = color.getRGB();
                }
                if (i > 564 && i < 656 && j > 11 && j < 236 && nArray2[i - 564 + (j - 11) * 92] != nArray2[0]) {
                    color3 = new Color(nArray[i + j * 670]);
                    color2 = new Color(nArray2[i - 564 + (j - 11) * 92]);
                    n3 = (int)((double)color3.getRed() * 0.33 + (double)color2.getRed() * 0.67);
                    if (n3 > 255) {
                        n3 = 255;
                    }
                    if (n3 < 0) {
                        n3 = 0;
                    }
                    if ((n2 = (int)((double)color3.getGreen() * 0.33 + (double)color2.getGreen() * 0.67)) > 255) {
                        n2 = 255;
                    }
                    if (n2 < 0) {
                        n2 = 0;
                    }
                    if ((n = (int)((double)color3.getBlue() * 0.33 + (double)color2.getBlue() * 0.67)) > 255) {
                        n = 255;
                    }
                    if (n < 0) {
                        n = 0;
                    }
                    color = new Color(n3, n2, n);
                    nArray[i + j * 670] = color.getRGB();
                }
                if (i <= 440 || i >= 528 || j <= 53 || j >= 77 || nArray3[i - 440 + (j - 53) * 88] == nArray3[0]) continue;
                color3 = new Color(nArray[i + j * 670]);
                color2 = new Color(nArray3[i - 440 + (j - 53) * 88]);
                n3 = (int)((double)color3.getRed() * 0.33 + (double)color2.getRed() * 0.67);
                if (n3 > 255) {
                    n3 = 255;
                }
                if (n3 < 0) {
                    n3 = 0;
                }
                if ((n2 = (int)((double)color3.getGreen() * 0.33 + (double)color2.getGreen() * 0.67)) > 255) {
                    n2 = 255;
                }
                if (n2 < 0) {
                    n2 = 0;
                }
                if ((n = (int)((double)color3.getBlue() * 0.33 + (double)color2.getBlue() * 0.67)) > 255) {
                    n = 255;
                }
                if (n < 0) {
                    n = 0;
                }
                color = new Color(n3, n2, n);
                nArray[i + j * 670] = color.getRGB();
            }
        }
        this.carsbgc = this.createImage(new MemoryImageSource(670, 400, nArray, 0, 670));
    }

    public void carsbginflex() {
        if (!this.badmac) {
            this.flatr = 0;
            this.flyr = (int)(this.m.random() * 160.0f - 80.0f);
            this.flyrdest = (int)((float)this.flyr + this.m.random() * 160.0f - 80.0f);
            this.flang = 1;
            this.flexpix = new int[268000];
            PixelGrabber pixelGrabber = new PixelGrabber(this.carsbg, 0, 0, 670, 400, this.flexpix, 0, 670);
            try {
                pixelGrabber.grabPixels();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
    }

    public void drawSmokeCarsbg() {
        if (!this.badmac) {
            if (Math.abs(this.flyr - this.flyrdest) > 20) {
                this.flyr = this.flyr > this.flyrdest ? (this.flyr -= 20) : (this.flyr += 20);
            } else {
                this.flyr = this.flyrdest;
                this.flyrdest = (int)((float)this.flyr + this.m.random() * 160.0f - 80.0f);
            }
            if (this.flyr > 160) {
                this.flyr = 160;
            }
            if (this.flatr > 170) {
                ++this.flatrstart;
                this.flatr = this.flatrstart * 3;
                this.flyr = (int)(this.m.random() * 160.0f - 80.0f);
                this.flyrdest = (int)((float)this.flyr + this.m.random() * 160.0f - 80.0f);
                this.flang = 1;
            }
            for (int i = 0; i < 466; ++i) {
                for (int j = 0; j < 202; ++j) {
                    if (this.smokey[i + j * 466] == this.smokey[0]) continue;
                    float f = this.pys(i, 233, j, this.flyr);
                    int n = (int)((float)(i - 233) / f * (float)this.flatr);
                    int n2 = (int)((float)(j - this.flyr) / f * (float)this.flatr);
                    int n3 = i + n + 100 + (j + n2 + 110) * 670;
                    if (i + n + 100 >= 670 || i + n + 100 <= 0 || j + n2 + 110 >= 400 || j + n2 + 110 <= 0 || n3 >= 268000 || n3 < 0) continue;
                    Color color = new Color(this.flexpix[n3]);
                    Color color2 = new Color(this.smokey[i + j * 466]);
                    float f2 = (255.0f - (float)color2.getRed()) / 255.0f;
                    float f3 = (255.0f - (float)color2.getGreen()) / 255.0f;
                    float f4 = (255.0f - (float)color2.getBlue()) / 255.0f;
                    int n4 = (int)(((float)color.getRed() * ((float)this.flang * f2) + (float)color2.getRed() * (1.0f - f2)) / ((float)this.flang * f2 + (1.0f - f2)));
                    int n5 = (int)(((float)color.getGreen() * ((float)this.flang * f3) + (float)color2.getGreen() * (1.0f - f3)) / ((float)this.flang * f3 + (1.0f - f3)));
                    int n6 = (int)(((float)color.getBlue() * ((float)this.flang * f4) + (float)color2.getBlue() * (1.0f - f4)) / ((float)this.flang * f4 + (1.0f - f4)));
                    if (n4 > 255) {
                        n4 = 255;
                    }
                    if (n4 < 0) {
                        n4 = 0;
                    }
                    if (n5 > 255) {
                        n5 = 255;
                    }
                    if (n5 < 0) {
                        n5 = 0;
                    }
                    if (n6 > 255) {
                        n6 = 255;
                    }
                    if (n6 < 0) {
                        n6 = 0;
                    }
                    Color color3 = new Color(n4, n5, n6);
                    this.flexpix[n3] = color3.getRGB();
                }
            }
            this.flang += 2;
            this.flatr += 10 + this.flatrstart * 2;
            Image image = this.createImage(new MemoryImageSource(670, 400, this.flexpix, 0, 670));
            this.rd.drawImage(image, 65, 25, null);
        } else {
            this.rd.drawImage(this.carsbg, 65, 25, null);
            ++this.flatrstart;
        }
    }

    public boolean msgcheck(String string) {
        int n;
        int n2;
        boolean bl = false;
        string = string.toLowerCase();
        String[] stringArray = new String[]{"fu ", " rape", "slut ", "screw ", "redtube", "fuck", "fuk", "f*ck", "fu*k", "f**k", "ass hole", "asshole", "dick", "dik", "cock", "cok ", "shit", "damn", "sex", "anal", "whore", "bitch", "biatch", "bich", " ass", "bastard", "cunt", "dildo", "fag", "homo", "mothaf", "motherf", "negro", "nigga", "nigger", "pussy", "gay", "homo", "you punk", "i will kill you"};
        for (int i = 0; i < stringArray.length; ++i) {
            if (string.indexOf(stringArray[i]) == -1) continue;
            bl = true;
        }
        if (string.startsWith("ass ")) {
            bl = true;
        }
        if (string.equals("ass")) {
            bl = true;
        }
        if (string.equals("rape")) {
            bl = true;
        }
        if (string.equals("fu")) {
            bl = true;
        }
        String string2 = "";
        String string3 = "";
        int n3 = 0;
        boolean bl2 = false;
        for (n2 = 0; n2 < string.length() && !bl2; ++n2) {
            if (n3 == 0) {
                string2 = string2 + "" + string.charAt(n2);
                n3 = 1;
                continue;
            }
            n3 = 0;
            if (!string3.equals("") && !string3.equals("" + string.charAt(n2))) {
                bl2 = true;
            }
            string3 = "" + string.charAt(n2);
        }
        if (!bl2) {
            for (n = 0; n < stringArray.length; ++n) {
                if (string2.indexOf(stringArray[n]) == -1) continue;
                bl = true;
            }
        }
        string2 = "";
        string3 = "";
        n3 = 1;
        bl2 = false;
        for (n2 = 0; n2 < string.length() && !bl2; ++n2) {
            if (n3 == 0) {
                string2 = string2 + "" + string.charAt(n2);
                n3 = 1;
                continue;
            }
            n3 = 0;
            if (!string3.equals("") && !string3.equals("" + string.charAt(n2))) {
                bl2 = true;
            }
            string3 = "" + string.charAt(n2);
        }
        if (!bl2) {
            for (n = 0; n < stringArray.length; ++n) {
                if (string2.indexOf(stringArray[n]) == -1) continue;
                bl = true;
            }
        }
        string2 = "";
        string3 = "";
        n3 = 0;
        bl2 = false;
        for (n2 = 0; n2 < string.length() && !bl2; ++n2) {
            if (n3 == 0) {
                string2 = string2 + "" + string.charAt(n2);
                n3 = 2;
                continue;
            }
            --n3;
            if (!string3.equals("") && !string3.equals("" + string.charAt(n2))) {
                bl2 = true;
            }
            string3 = "" + string.charAt(n2);
        }
        if (!bl2) {
            for (n = 0; n < stringArray.length; ++n) {
                if (string2.indexOf(stringArray[n]) == -1) continue;
                bl = true;
            }
        }
        string2 = "";
        string3 = "";
        n3 = 1;
        bl2 = false;
        for (n2 = 0; n2 < string.length() && !bl2; ++n2) {
            if (n3 == 0) {
                string2 = string2 + "" + string.charAt(n2);
                n3 = 2;
                continue;
            }
            --n3;
            if (!string3.equals("") && !string3.equals("" + string.charAt(n2))) {
                bl2 = true;
            }
            string3 = "" + string.charAt(n2);
        }
        if (!bl2) {
            for (n = 0; n < stringArray.length; ++n) {
                if (string2.indexOf(stringArray[n]) == -1) continue;
                bl = true;
            }
        }
        string2 = "";
        string3 = "";
        n3 = 2;
        bl2 = false;
        for (n2 = 0; n2 < string.length() && !bl2; ++n2) {
            if (n3 == 0) {
                string2 = string2 + "" + string.charAt(n2);
                n3 = 2;
                continue;
            }
            --n3;
            if (!string3.equals("") && !string3.equals("" + string.charAt(n2))) {
                bl2 = true;
            }
            string3 = "" + string.charAt(n2);
        }
        if (!bl2) {
            for (n = 0; n < stringArray.length; ++n) {
                if (string2.indexOf(stringArray[n]) == -1) continue;
                bl = true;
            }
        }
        return bl;
    }

    public boolean drawcarb(boolean bl, Image image, String string, int n, int n2, int n3, int n4, boolean bl2) {
        boolean bl3 = false;
        int n5 = 0;
        this.rd.setFont(new Font("Arial", 1, 13));
        this.ftm = this.rd.getFontMetrics();
        if (bl) {
            n5 = this.ftm.stringWidth(string);
            if (string.startsWith("Class")) {
                n5 = 112;
            }
        } else {
            n5 = image.getWidth(this.ob);
        }
        int n6 = 0;
        if (n3 > n && n3 < n + n5 + 14 && n4 > n2 && n4 < n2 + 28) {
            n6 = 1;
            if (bl2) {
                bl3 = true;
            }
        }
        this.rd.drawImage(this.bcl[n6], n, n2, null);
        this.rd.drawImage(this.bc[n6], n + 4, n2, n5 + 6, 28, null);
        this.rd.drawImage(this.bcr[n6], n + n5 + 10, n2, null);
        if (!bl && n5 == 73) {
            --n2;
        }
        if (bl) {
            if (string.equals("X") && n6 == 1) {
                this.rd.setColor(new Color(255, 0, 0));
            } else {
                this.rd.setColor(new Color(0, 0, 0));
            }
            if (string.startsWith("Class")) {
                this.rd.drawString(string, 400 - this.ftm.stringWidth(string) / 2, n2 + 19);
            } else {
                this.rd.drawString(string, n + 7, n2 + 19);
            }
        } else {
            this.rd.drawImage(image, n + 7, n2 + 7, null);
        }
        return bl3;
    }

    public void drawWarning() {
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.fillRect(0, 0, 800, 450);
        this.rd.setFont(new Font("Arial", 1, 22));
        this.ftm = this.rd.getFontMetrics();
        this.drawcs(100, "Warning!", 255, 0, 0, 3);
        this.rd.setFont(new Font("Arial", 1, 18));
        this.ftm = this.rd.getFontMetrics();
        this.drawcs(150, "Bad language and flooding is strictly prohibited in this game!", 255, 255, 255, 3);
        this.rd.setFont(new Font("Arial", 1, 13));
        this.ftm = this.rd.getFontMetrics();
        if (this.warning < 210) {
            this.drawcs(200, "If you continue typing bad language or flooding your game will shut down.", 200, 200, 200, 3);
        }
        if (this.warning > 210) {
            this.drawcs(200, "Sorry. This was your second warring your game has shut down.", 200, 200, 200, 3);
        }
        if (this.warning > 250) {
            this.stopallnow();
            this.runtyp = 0;
            this.app.repaint();
            this.app.gamer.stop();
        }
    }

    public int getvalue(String string, int n) {
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

    public String getSvalue(String string, int n) {
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
}

