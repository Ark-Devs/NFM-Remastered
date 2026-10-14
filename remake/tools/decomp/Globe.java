/*
 * Decompiled with CFR 0.152.
 */
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Calendar;

public class Globe
implements Runnable {
    MediaTracker mt;
    Graphics2D rd;
    xtGraphics xt;
    FontMetrics ftm;
    ImageObserver ob;
    GameSparker gs;
    Login lg;
    CarDefine cd;
    Medium m;
    Graphics2D rdo;
    Image gImage;
    Thread connector;
    boolean domon = false;
    Socket socket;
    BufferedReader din;
    PrintWriter dout;
    int fase = 0;
    int open = 0;
    boolean upo = false;
    int tab = 3;
    boolean onp = false;
    int ptab = 0;
    int spos = 0;
    int lspos = 0;
    int mscro = 825;
    int spos2 = 0;
    int lspos2 = 0;
    int mscro2 = 825;
    int spos3 = 0;
    int lspos3 = 0;
    int mscro3 = 825;
    int spos4 = 208;
    int lspos4 = 0;
    int mscro4 = 825;
    int spos5 = 0;
    int lspos5 = 0;
    int mscro5 = 825;
    int overit = 0;
    int flk = 0;
    int flko = 0;
    boolean flg = false;
    int curs = 0;
    int waitlink = 0;
    int addstage = 0;
    boolean darker = false;
    int npo = -1;
    String[] pname = new String[900];
    int[] proom = new int[900];
    int[] pserver = new int[900];
    int[][] roomf = new int[3][5];
    int npf = -1;
    String[] fname = new String[900];
    String[] cnfname = new String[10];
    int ncnf = 0;
    int freq = 0;
    int sfreq = 0;
    String freqname = "";
    String sfreqname = "";
    int cntf = 0;
    String[] cnames = new String[21];
    String[] sentn = new String[21];
    String[] ctime = new String[21];
    long[] nctime = new long[21];
    int updatec = -1;
    String proname = "";
    boolean loadedp = false;
    int edit = 0;
    int upload = 0;
    int perc = 0;
    int playt = 0;
    int uploadt = 0;
    String filename = "";
    String msg = "";
    String trackname = "";
    boolean refresh = false;
    boolean logol = false;
    boolean avatarl = false;
    int sentchange = 0;
    boolean badlang = false;
    String[] aboutxt = new String[3];
    int nab = 0;
    Image clanlogo;
    Image avatar;
    int racing = 50;
    int wasting = 150;
    String themesong = "";
    String sentance = "";
    int trackvol = 0;
    int comesoon = 0;
    String proclan = "";
    int protab = 0;
    int loadpst = 0;
    String loadpstage = "";
    boolean loadedpstage = false;
    int nlg = 0;
    String[] logos = new String[200];
    boolean[] logon = new boolean[200];
    Image[] logoi = new Image[200];
    int loadmsgs = -1;
    String hasmsgs = "";
    String lastsub = "";
    int nm = 0;
    String[] mname = new String[200];
    String[] mconvo = new String[200];
    String[] msub = new String[200];
    int[] mtyp = new int[200];
    String[] mtime = new String[200];
    long[] mctime = new long[200];
    int openc = 0;
    int opy = 0;
    int addopy = 0;
    int oph = 0;
    int itemsel = 0;
    int loaditem = 0;
    String[] mline = new String[1000];
    int[] mlinetyp = new int[1000];
    long[] mctimes = new long[1000];
    String[] mtimes = new String[1000];
    int nml = 0;
    int readmsg = 0;
    String opname = "";
    String blockname = "";
    String unblockname = "";
    int sendmsg = 0;
    String[] wday = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String[] month = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    int itab = 0;
    int litab = -1;
    int cadmin = 0;
    String[] cmline = new String[1000];
    int[] cmlinetyp = new int[1000];
    long[] cmctimes = new long[1000];
    String[] cmtimes = new String[1000];
    int cnml = 0;
    int readclan = 0;
    int sendcmsg = 0;
    int loadinter = -1;
    int ni = 0;
    String[] iclan = new String[200];
    String[] iconvo = new String[200];
    String[] isub = new String[200];
    String[] icheck = new String[200];
    String[] itime = new String[200];
    long[] ictime = new long[200];
    String[] istat = new String[200];
    String[] itcar = new String[200];
    String[] igcar = new String[200];
    String[] iwarn = new String[200];
    int openi = 0;
    int readint = 0;
    String intclan = "";
    String lastint = "";
    int dispi = 0;
    String dwarn = "";
    String dtcar = "";
    String dgcar = "";
    int nil = 0;
    String[] iline = new String[1000];
    int[] ilinetyp = new int[1000];
    long[] ictimes = new long[1000];
    String[] itimes = new String[1000];
    int intsel = 0;
    int isel = 0;
    int ifas = 0;
    int leader = -1;
    int sendint = 0;
    boolean inishsel = false;
    boolean redif = false;
    String imsg = "";
    int wag = 0;
    int iflk = 0;
    String itake = "";
    String igive = "";
    String viewcar = "";
    String warnum = "";
    boolean sendwarnum = false;
    String[] wstages = new String[5];
    int[] wlaps = new int[5];
    int[] wcars = new int[5];
    int[] wclass = new int[5];
    int[] wfix = new int[5];
    int nvgames1 = 0;
    int nvgames2 = 0;
    int viewgame1 = 0;
    int viewgame2 = 0;
    String viewwar1 = "";
    String viewwar2 = "";
    String xclan = "";
    String sendwar = "";
    boolean ichlng = false;
    String[] vwstages1 = new String[10];
    int[] vwlaps1 = new int[10];
    int[] vwcars1 = new int[10];
    int[] vwclass1 = new int[10];
    int[] vwfix1 = new int[10];
    String[] vwstages2 = new String[10];
    int[] vwlaps2 = new int[10];
    int[] vwcars2 = new int[10];
    int[] vwclass2 = new int[10];
    int[] vwfix2 = new int[10];
    String[] vwinner = new String[10];
    int vwscorex = 0;
    int vwscorei = 0;
    Image intclanbg = null;
    String intclanlo = "";
    boolean intclanbgloaded = false;
    Image myclanbg = null;
    int loadedmyclanbg = 0;
    int cfase = 0;
    boolean notclan = false;
    String claname = "EvilOnes";
    boolean loadedc = false;
    boolean clanbgl = false;
    Image clanbg;
    int editc = 0;
    int em = 0;
    int ctab = 0;
    int nmb = 0;
    String lccnam = "";
    String[] member = new String[20];
    int[] mlevel = new int[20];
    String[] mrank = new String[20];
    int nrmb = 0;
    String[] rmember = new String[100];
    boolean showreqs = false;
    int blocknote = 0;
    int blockb = 0;
    boolean loadedcm = false;
    int ncln = 0;
    String[] clname = new String[20];
    String smsg = "";
    String sltit = "";
    boolean attachetoclan = false;
    boolean loadedlink = false;
    int loadedcars = -1;
    int loadedcar = 0;
    String ltit = "";
    String ldes = "";
    String lurl = "";
    boolean forcsel = false;
    String selcar = "";
    String selstage = "";
    String perry = "";
    int mrot = 0;
    int loadedstages = -1;
    int loadedstage = 0;
    CheckPoints cp;
    ContO[] bco;
    ContO[] co;
    int mouson = -1;
    int nclns = 0;
    String[] clanlo = new String[20];
    int ntab = 0;
    int loadednews = 0;
    int spos6 = 0;
    int lspos6 = 0;
    String[] newplayers = new String[]{"", "", "", "", ""};
    int[] nwarbs = new int[]{-1, -1, -1, -1, -1};
    String[] nwclan = new String[5];
    String[] nlclan = new String[5];
    String[] nwinob = new String[5];
    int[] nwinp = new int[5];
    int[] nlosp = new int[5];
    String[] nwtime = new String[5];
    int il = 0;
    String[] nttime = new String[300];
    String[] text = new String[300];
    int[] nln = new int[300];
    String[][][] link = new String[300][4][2];
    int maxclans = 1000;
    int loadwstat = 0;
    int ncc = 0;
    int champ = -1;
    int leadsby = 0;
    String[] conclan;
    int[] totp;
    int[] ord;
    int[] nvc;
    int[][] points;
    String[][] verclan;
    int eng = -1;
    int engo = 0;
    boolean frkl = false;
    int underc = 0;
    float bgf = 0.0f;
    boolean bgup = false;
    int[] bgx = new int[]{0, 670, 1340};
    int flkn = 0;
    int cur = 0;
    int sdist = 0;
    int scro = 0;
    boolean donewc = false;
    boolean dosrch = false;
    boolean dorank = false;
    boolean doweb1 = false;
    boolean doweb2 = false;
    boolean dommsg = false;
    boolean donemsg = false;
    int doi = 0;
    int ados = 0;
    int lspos6w = 0;
    long ntime = 0L;
    int loadwbgames = 0;
    int warb = 0;
    int gameturn = -1;
    String warbnum = "";
    String vclan = "";
    String[] wbstages = new String[10];
    int[] wbstage = new int[10];
    int[] wblaps = new int[10];
    int[] wbcars = new int[10];
    int[] wbclass = new int[10];
    int[] wbfix = new int[10];
    String gameturndisp = "";
    int ascore = 0;
    int vscore = 0;
    String lwbwinner = "";
    boolean canredo = false;

    public Globe(Graphics2D graphics2D, xtGraphics xtGraphics2, Medium medium, Login login, CarDefine carDefine, CheckPoints checkPoints, ContO[] contOArray, ContO[] contOArray2, GameSparker gameSparker) {
        int n;
        this.rd = graphics2D;
        this.xt = xtGraphics2;
        this.m = medium;
        this.gs = gameSparker;
        this.lg = login;
        this.cd = carDefine;
        this.cp = checkPoints;
        this.bco = contOArray;
        this.co = contOArray2;
        this.gImage = this.gs.createImage(560, 300);
        this.rdo = (Graphics2D)this.gImage.getGraphics();
        this.rdo.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.rdo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (n = 0; n < 21; ++n) {
            this.ctime[n] = "";
            this.cnames[n] = "";
            this.sentn[n] = "";
            this.nctime[n] = 0L;
        }
        for (n = 0; n < 900; ++n) {
            this.pname[n] = "";
        }
        for (n = 0; n < 200; ++n) {
            this.logos[n] = "";
            this.logon[n] = false;
        }
        this.mt = new MediaTracker(this.gs);
        this.gs.sendtyp.setBackground(this.colorb2k(false, 210, 210, 210));
        this.gs.sendtyp.setForeground(new Color(0, 0, 0));
        this.gs.sendtyp.removeAll();
        this.gs.sendtyp.add(this.rd, "Write a Message");
        this.gs.sendtyp.add(this.rd, "Share a Custom Car");
        this.gs.sendtyp.add(this.rd, "Share a Custom Stage");
        this.gs.sendtyp.add(this.rd, "Send a Clan Invitation");
        this.gs.sendtyp.add(this.rd, "Share a Relative Date");
        this.gs.senditem.setBackground(this.colorb2k(false, 230, 230, 230));
        this.gs.senditem.setForeground(new Color(0, 0, 0));
        this.gs.proitem.setBackground(new Color(206, 171, 98));
        this.gs.proitem.setForeground(new Color(0, 0, 0));
        this.gs.datat.setBackground(this.colorb2k(false, 230, 230, 230));
        this.gs.datat.setForeground(new Color(0, 0, 0));
        this.gs.mmsg.setBackground(this.colorb2k(false, 240, 240, 240));
        this.gs.mmsg.setForeground(new Color(0, 0, 0));
        this.gs.clanlev.setBackground(this.colorb2k(false, 230, 230, 230));
        this.gs.clanlev.setForeground(new Color(0, 0, 0));
        this.gs.clcars.setBackground(new Color(0, 0, 0));
        this.gs.clcars.setForeground(new Color(255, 255, 255));
        this.gs.ilaps.setBackground(this.colorb2k(false, 220, 220, 220));
        this.gs.ilaps.setForeground(new Color(0, 0, 0));
        this.gs.icars.setBackground(this.colorb2k(false, 220, 220, 220));
        this.gs.icars.setForeground(new Color(0, 0, 0));
        if (!this.xt.clan.equals("")) {
            this.itab = 2;
        }
    }

    public void dome(int n, int n2, int n3, boolean bl, Control control) {
        Object object;
        Object object2;
        boolean bl2 = false;
        boolean bl3 = false;
        this.dommsg = false;
        this.dorank = false;
        this.donewc = false;
        this.dosrch = false;
        this.doweb1 = false;
        this.doweb2 = false;
        if (this.open == 0) {
            boolean bl4 = false;
            if (this.lg.nmsgs == 0 && this.lg.nfreq == 0 && this.lg.nconf == 0 && this.lg.ncreq == 0 && this.lg.clanapv.equals("")) {
                if (n3 > 425 && n3 < 450 && n2 < 568 && n2 > 232) {
                    bl4 = true;
                }
                object2 = new int[]{0, 9, 232, 250, 550, 568, 791, 800};
                int[] nArray = new int[]{452, 443, 443, 425, 425, 443, 443, 452};
                if (bl4) {
                    this.rd.setColor(this.color2k(255, 255, 255));
                } else {
                    this.rd.setColor(new Color(207, 177, 110));
                }
                this.rd.fillPolygon((int[])object2, nArray, 8);
                this.rd.setColor(new Color(103, 88, 55));
                this.rd.drawPolygon((int[])object2, nArray, 8);
                this.rd.drawImage(this.xt.dome, 311, 430, null);
            } else {
                Object object3;
                object2 = "You have ";
                if (!this.lg.clanapv.equals("")) {
                    object2 = (String)object2 + "been approved for a clan membership";
                    if (this.lg.nmsgs != 0 || this.lg.nfreq != 0 || this.lg.nconf != 0) {
                        object2 = (String)object2 + "! + You have ";
                    }
                }
                if (this.lg.ncreq != 0) {
                    object2 = (String)object2 + "" + this.lg.ncreq + " clan membership requests";
                    if (this.lg.nmsgs != 0 || this.lg.nfreq != 0 || this.lg.nconf != 0) {
                        object2 = (String)object2 + "! + You have ";
                    }
                }
                if (this.lg.nmsgs != 0) {
                    object2 = (String)object2 + "new interactions";
                    if (this.lg.fclan > 0 && this.lg.fplayer > 0) {
                        String string = "";
                        object3 = "";
                        if (this.lg.fplayer > 1) {
                            string = "s";
                        }
                        if (this.lg.fclan > 1) {
                            object3 = "s";
                        }
                        object2 = (String)object2 + " from " + this.lg.fplayer + " player" + string + " & " + this.lg.fclan + " clan" + (String)object3 + "";
                    } else {
                        if (this.lg.fclan == 1) {
                            object2 = (String)object2 + " from 1 clan";
                        }
                        if (this.lg.fclan > 1) {
                            object2 = (String)object2 + " from " + this.lg.fclan + " clans";
                        }
                        if (this.lg.fplayer == 1) {
                            object2 = (String)object2 + " from 1 player";
                        }
                        if (this.lg.fplayer > 1) {
                            object2 = (String)object2 + " from " + this.lg.fplayer + " players";
                        }
                        if (this.lg.fclan == 0 && this.lg.fplayer == 0) {
                            object2 = (String)object2 + " from your clan's discussion";
                        }
                    }
                    if (this.lg.nfreq != 0 && this.lg.nconf != 0) {
                        object2 = (String)object2 + ", ";
                    } else {
                        if (this.lg.nfreq != 0) {
                            object2 = (String)object2 + " and ";
                        }
                        if (this.lg.nconf != 0) {
                            object2 = (String)object2 + " and ";
                        }
                    }
                }
                if (this.lg.nfreq != 0) {
                    object2 = (String)object2 + "" + this.lg.nfreq + " friend request";
                    if (this.lg.nfreq > 1) {
                        object2 = (String)object2 + "s";
                    }
                    if (this.lg.nconf != 0) {
                        object2 = (String)object2 + " and ";
                    }
                }
                if (this.lg.nconf != 0) {
                    object2 = (String)object2 + "" + this.lg.nconf + " friend confirmation";
                    if (this.lg.nconf > 1) {
                        object2 = (String)object2 + "s";
                    }
                }
                object2 = (String)object2 + "!";
                this.rd.setFont(new Font("Arial", 1, 12));
                this.ftm = this.rd.getFontMetrics();
                int n4 = 0;
                if (this.ftm.stringWidth((String)object2) > 280) {
                    n4 = (this.ftm.stringWidth((String)object2) - 280) / 2;
                }
                if (n3 > 425 && n3 < 450 && n2 < 568 + n4 && n2 > 232 - n4) {
                    bl4 = true;
                }
                object3 = new int[]{0, 9, 232 - n4, 250 - n4, 550 + n4, 568 + n4, 791, 800};
                object = new int[]{452, 443, 443, 425, 425, 443, 443, 452};
                if (bl4) {
                    this.rd.setColor(this.color2k(255, 255, 255));
                } else {
                    this.rd.setColor(new Color(207, 177, 110));
                }
                this.rd.fillPolygon((int[])object3, (int[])object, 8);
                this.rd.setColor(new Color(103, 88, 55));
                this.rd.drawPolygon((int[])object3, (int[])object, 8);
                if (this.flkn % 3 == 0) {
                    this.rd.setColor(new Color(60, 30, 0));
                } else {
                    this.rd.setColor(new Color(0, 100, 0));
                }
                this.rd.drawString((String)object2, 400 - this.ftm.stringWidth((String)object2) / 2, 442);
                if (this.flkn < 33) {
                    ++this.flkn;
                }
            }
            if (bl4 && bl && n < 2) {
                this.open = 2;
                this.upo = true;
                if (this.lg.nmsgs != 0) {
                    this.tab = 2;
                    this.itab = this.lg.fplayer > 0 ? 0 : (this.lg.fclan > 0 ? 1 : 2);
                    this.litab = -1;
                }
                if (!this.lg.clanapv.equals("")) {
                    this.claname = this.lg.clanapv;
                    this.loadedc = false;
                    this.spos5 = 0;
                    this.lspos5 = 0;
                    this.cfase = 3;
                    this.tab = 3;
                    this.ctab = 0;
                }
                if (this.lg.ncreq != 0) {
                    this.claname = this.xt.clan;
                    this.loadedc = false;
                    this.spos5 = 0;
                    this.lspos5 = 0;
                    this.cfase = 3;
                    this.tab = 3;
                    this.ctab = 0;
                }
                if (this.lg.nfreq != 0 || this.lg.nconf != 0) {
                    this.ptab = 1;
                }
            }
            if (this.xt.onviewpro) {
                this.proname = this.cd.viewname;
                this.open = 2;
                this.upo = true;
                this.tab = 1;
                this.xt.onviewpro = false;
            }
        } else if (this.flkn != 0) {
            this.flkn = 0;
        }
        if (this.open >= 2 && this.open < 452) {
            int n5;
            int n6;
            int[] nArray = new int[]{0, 0, 9, 232, 250, 550, 568, 791, 800, 800};
            object2 = new int[]{902 - this.open, 452 - this.open, 443 - this.open, 443 - this.open, 425 - this.open, 425 - this.open, 443 - this.open, 443 - this.open, 452 - this.open, 902 - this.open};
            float f = ((float)this.open - 2.0f) / 450.0f;
            int n7 = (int)(255.0f * (1.0f - f) + 216.0f * f);
            if (n7 > 255) {
                n7 = 255;
            }
            if (n7 < 0) {
                n7 = 0;
            }
            if ((n6 = (int)(243.0f * (1.0f - f) + 179.0f * f)) > 255) {
                n6 = 255;
            }
            if (n6 < 0) {
                n6 = 0;
            }
            if ((n5 = (int)(179.0f * (1.0f - f) + 100.0f * f)) > 255) {
                n5 = 255;
            }
            if (n5 < 0) {
                n5 = 0;
            }
            this.rd.setColor(new Color(n7, n6, n5));
            this.rd.fillPolygon(nArray, (int[])object2, 10);
            this.rd.drawImage(this.xt.dome, 311, 430 - this.open, null);
            this.open = this.upo ? (this.open += 45) : (this.open -= 45);
            this.gs.hidefields();
            if (this.open == 452) {
                this.gs.setCursor(new Cursor(0));
                this.npo = -1;
                this.updatec = -1;
                this.domon = true;
                this.connector = new Thread(this);
                this.connector.start();
            }
        }
        if (this.open == 452) {
            if (this.xt.warning == 0 || this.xt.warning == 210) {
                int n8;
                this.cur = 0;
                int n9 = (int)(255.0f * this.bgf + 191.0f * (1.0f - this.bgf));
                int n10 = (int)(176.0f * this.bgf + 184.0f * (1.0f - this.bgf));
                int n11 = (int)(67.0f * this.bgf + 124.0f * (1.0f - this.bgf));
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
                this.rd.setColor(new Color(n9, n10, n11));
                this.rd.fillRect(0, 0, 800, 450);
                this.rd.setComposite(AlphaComposite.getInstance(3, 0.6f));
                this.rd.drawImage(this.xt.bgmain, this.bgx[0], 0, null);
                this.rd.drawImage(this.xt.bgmain, this.bgx[1], 0, null);
                this.rd.drawImage(this.xt.bgmain, this.bgx[2], 0, null);
                this.rd.drawImage(this.xt.bgmain, this.bgx[0], 400, null);
                this.rd.drawImage(this.xt.bgmain, this.bgx[1], 400, null);
                this.rd.drawImage(this.xt.bgmain, this.bgx[2], 400, null);
                this.rd.setComposite(AlphaComposite.getInstance(3, 0.1f));
                this.rd.drawImage(this.xt.bggo, 0, 0, null);
                this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                for (int i = 0; i < 3; ++i) {
                    int n12 = i;
                    this.bgx[n12] = this.bgx[n12] - 5;
                    if (this.bgx[i] > -670) continue;
                    this.bgx[i] = 1340;
                }
                if (this.drawbutton(this.xt.exit, 755, 17, n2, n3, bl) || n >= 2) {
                    this.open = 450;
                    this.upo = false;
                    this.domon = false;
                    this.onexit();
                }
                this.sdist = 0;
                this.scro = 0;
                if (this.domon) {
                    int n13;
                    int n14;
                    int n15;
                    Object[] objectArray;
                    if (this.tab == 0) {
                        this.rd.setColor(this.color2k(230, 230, 230));
                        this.rd.fillRoundRect(197, 40, 597, 404, 20, 20);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawRoundRect(197, 40, 597, 404, 20, 20);
                        if (this.updatec != -1) {
                            int n16;
                            String[] stringArray = new String[42];
                            object = new String[42];
                            String[] stringArray2 = new String[42];
                            objectArray = new boolean[42];
                            for (n15 = 0; n15 < 42; ++n15) {
                                stringArray[n15] = "";
                                object[n15] = (int)"";
                                stringArray2[n15] = "";
                                objectArray[n15] = false;
                            }
                            n15 = 0;
                            this.rd.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rd.getFontMetrics();
                            for (int i = 0; i < 21; ++i) {
                                stringArray[n15] = "";
                                object[n15] = (int)this.cnames[i];
                                stringArray2[n15] = this.ctime[i];
                                n14 = 0;
                                n13 = 0;
                                int n17 = 0;
                                int n18 = 0;
                                for (n16 = 0; n16 < this.sentn[i].length(); ++n16) {
                                    String string = "" + this.sentn[i].charAt(n16);
                                    if (string.equals(" ")) {
                                        n14 = n13;
                                        n17 = n16;
                                        ++n18;
                                    } else {
                                        n18 = 0;
                                    }
                                    if (n18 > 1) continue;
                                    int n19 = n15;
                                    stringArray[n19] = stringArray[n19] + string;
                                    ++n13;
                                    if (this.ftm.stringWidth(stringArray[n15]) <= 469) continue;
                                    if (n14 != 0) {
                                        stringArray[n15] = stringArray[n15].substring(0, n14);
                                        if (n15 == 41) {
                                            for (int j = 0; j < 41; ++j) {
                                                stringArray[j] = stringArray[j + 1];
                                                object[j] = object[j + 1];
                                                stringArray2[j] = stringArray2[j + 1];
                                                objectArray[j] = objectArray[j + 1];
                                            }
                                            stringArray[n15] = "";
                                            objectArray[n15] = true;
                                        } else {
                                            object[++n15] = (int)this.cnames[i];
                                            stringArray2[n15] = this.ctime[i];
                                        }
                                        n16 = n17;
                                        n13 = 0;
                                        n14 = 0;
                                        continue;
                                    }
                                    stringArray[n15] = "";
                                    n13 = 0;
                                }
                                if (n15 == 41 && i != 20) {
                                    for (int j = 0; j < 41; ++j) {
                                        stringArray[j] = stringArray[j + 1];
                                        object[j] = object[j + 1];
                                        stringArray2[j] = stringArray2[j + 1];
                                        objectArray[j] = objectArray[j + 1];
                                    }
                                    continue;
                                }
                                ++n15;
                            }
                            Object object4 = "";
                            n16 = n15;
                            for (n14 = 0; n14 < n15; ++n14) {
                                if (object4.equals(object[n14])) continue;
                                if (n14 != 0) {
                                    ++n16;
                                }
                                object4 = object[n14];
                            }
                            this.sdist = (int)(((float)n16 - 21.5f) * 15.0f);
                            if (this.sdist < 0) {
                                this.sdist = 0;
                            }
                            this.scro = (int)((float)this.spos2 / 275.0f * (float)this.sdist);
                            n16 = 0;
                            object4 = "";
                            for (n14 = 0; n14 <= n15; ++n14) {
                                if (n14 != n15) {
                                    if (!object4.equals(object[n14])) {
                                        if (n14 != 0) {
                                            if (n16 * 15 - this.scro > -20 && n16 * 15 - this.scro < 345) {
                                                this.rd.setFont(new Font("Tahoma", 0, 11));
                                                this.rd.setColor(this.color2k(125, 125, 125));
                                                this.rd.drawString(stringArray2[n14 - 1], 297, 82 + n16 * 15 - this.scro);
                                            }
                                            ++n16;
                                        }
                                        if (n16 * 15 - this.scro > -20 && n16 * 15 - this.scro < 345) {
                                            this.rd.setFont(new Font("Tahoma", 1, 11));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.drawString((String)object[n14] + ":", 292 - this.ftm.stringWidth((String)object[n14] + ":"), 82 + n16 * 15 - this.scro);
                                        }
                                        object4 = object[n14];
                                    }
                                    if (objectArray[n14] && n14 == 0 && stringArray[n14].indexOf(" ") != -1) {
                                        stringArray[n14] = "..." + stringArray[n14].substring(stringArray[n14].indexOf(" "), stringArray[n14].length()) + "";
                                    }
                                    if (n16 * 15 - this.scro > -20 && n16 * 15 - this.scro < 345) {
                                        this.rd.setFont(new Font("Tahoma", 0, 11));
                                        this.rd.setColor(new Color(0, 0, 0));
                                        this.rd.drawString(stringArray[n14], 297, 82 + n16 * 15 - this.scro);
                                    }
                                    ++n16;
                                    continue;
                                }
                                if (n16 * 15 - this.scro <= -20 || n16 * 15 - this.scro >= 345) continue;
                                this.rd.setFont(new Font("Tahoma", 0, 11));
                                this.rd.setColor(this.color2k(125, 125, 125));
                                this.rd.drawString(stringArray2[n14 - 1], 297, 82 + n16 * 15 - this.scro);
                            }
                        } else {
                            this.sdist = 0;
                            this.scro = 0;
                            this.spos2 = 275;
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.setFont(new Font("Tahoma", 1, 11));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString("Loading chat...", 498 - this.ftm.stringWidth("Loading chat...") / 2, 220);
                        }
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(207, 46, 582, 25);
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.rd.setColor(this.color2k(40, 40, 40));
                        this.rd.drawString("Global Chat", 213, 62);
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawLine(207, 68, 770, 68);
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(207, 411, 582, 28);
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawLine(207, 413, 770, 413);
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(772, 88, 17, 306);
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(203, 46, 4, 393);
                        bl2 = true;
                        if ((this.stringbutton(this.rd, "Send Message", 731, 430, 3, n2, n3, bl, 0, 0) || control.enter) && !this.gs.cmsg.getText().equals("Type here...") && !this.gs.cmsg.getText().equals("") && this.xt.acexp != -3) {
                            String string = this.gs.cmsg.getText().replace('|', ':');
                            if (string.toLowerCase().indexOf(this.gs.tpass.getText().toLowerCase()) != -1) {
                                string = " ";
                            }
                            if (!this.xt.msgcheck(string) && this.updatec > -12) {
                                for (int i = 0; i < 20; ++i) {
                                    this.sentn[i] = this.sentn[i + 1];
                                    this.cnames[i] = this.cnames[i + 1];
                                    this.ctime[i] = this.ctime[i + 1];
                                }
                                this.sentn[20] = string;
                                this.cnames[20] = this.xt.nickname;
                                this.ctime[20] = "- just now";
                                this.updatec = this.updatec > -11 ? -11 : --this.updatec;
                                this.spos2 = 275;
                            } else {
                                ++this.xt.warning;
                            }
                            this.gs.cmsg.setText("");
                            control.enter = false;
                        }
                        if (this.mscro2 == 831 || this.sdist == 0) {
                            if (this.sdist == 0) {
                                this.rd.setColor(this.color2k(205, 205, 205));
                            } else {
                                this.rd.setColor(this.color2k(215, 215, 215));
                            }
                            this.rd.fillRect(772, 71, 17, 17);
                        } else {
                            this.rd.setColor(this.color2k(220, 220, 220));
                            this.rd.fill3DRect(772, 71, 17, 17, true);
                        }
                        if (this.sdist != 0) {
                            this.rd.drawImage(this.xt.asu, 777, 77, null);
                        }
                        if (this.mscro2 == 832 || this.sdist == 0) {
                            if (this.sdist == 0) {
                                this.rd.setColor(this.color2k(205, 205, 205));
                            } else {
                                this.rd.setColor(this.color2k(215, 215, 215));
                            }
                            this.rd.fillRect(772, 394, 17, 17);
                        } else {
                            this.rd.setColor(this.color2k(220, 220, 220));
                            this.rd.fill3DRect(772, 394, 17, 17, true);
                        }
                        if (this.sdist != 0) {
                            this.rd.drawImage(this.xt.asd, 777, 401, null);
                        }
                        if (this.sdist != 0) {
                            if (this.lspos2 != this.spos2) {
                                this.rd.setColor(this.color2k(215, 215, 215));
                                this.rd.fillRect(772, 88 + this.spos2, 17, 31);
                            } else {
                                if (this.mscro2 == 831) {
                                    this.rd.setColor(this.color2k(215, 215, 215));
                                }
                                this.rd.fill3DRect(772, 88 + this.spos2, 17, 31, true);
                            }
                            this.rd.setColor(this.color2k(150, 150, 150));
                            this.rd.drawLine(777, 101 + this.spos2, 783, 101 + this.spos2);
                            this.rd.drawLine(777, 103 + this.spos2, 783, 103 + this.spos2);
                            this.rd.drawLine(777, 105 + this.spos2, 783, 105 + this.spos2);
                            if (this.mscro2 > 800 && this.lspos2 != this.spos2) {
                                this.lspos2 = this.spos2;
                            }
                            if (bl) {
                                int n20;
                                if (this.mscro2 == 825 && n2 > 772 && n2 < 789 && n3 > 88 + this.spos2 && n3 < this.spos2 + 119) {
                                    this.mscro2 = n3 - this.spos2;
                                }
                                if (this.mscro2 == 825 && n2 > 770 && n2 < 791 && n3 > 69 && n3 < 90) {
                                    this.mscro2 = 831;
                                }
                                if (this.mscro2 == 825 && n2 > 770 && n2 < 791 && n3 > 392 && n3 < 413) {
                                    this.mscro2 = 832;
                                }
                                if (this.mscro2 == 825 && n2 > 772 && n2 < 789 && n3 > 88 && n3 < 394) {
                                    this.mscro2 = 103;
                                    this.spos2 = n3 - this.mscro2;
                                }
                                if ((n20 = 2670 / this.sdist) < 1) {
                                    n20 = 1;
                                }
                                if (this.mscro2 == 831) {
                                    this.spos2 -= n20;
                                    if (this.spos2 > 275) {
                                        this.spos2 = 275;
                                    }
                                    if (this.spos2 < 0) {
                                        this.spos2 = 0;
                                    }
                                    this.lspos2 = this.spos2;
                                }
                                if (this.mscro2 == 832) {
                                    this.spos2 += n20;
                                    if (this.spos2 > 275) {
                                        this.spos2 = 275;
                                    }
                                    if (this.spos2 < 0) {
                                        this.spos2 = 0;
                                    }
                                    this.lspos2 = this.spos2;
                                }
                                if (this.mscro2 < 800) {
                                    this.spos2 = n3 - this.mscro2;
                                    if (this.spos2 > 275) {
                                        this.spos2 = 275;
                                    }
                                    if (this.spos2 < 0) {
                                        this.spos2 = 0;
                                    }
                                }
                                if (this.mscro2 == 825) {
                                    this.mscro2 = 925;
                                }
                            } else if (this.mscro2 != 825) {
                                this.mscro2 = 825;
                            }
                        }
                    }
                    if (this.tab == 1) {
                        this.rd.setComposite(AlphaComposite.getInstance(3, 0.4f));
                        this.rd.setColor(new Color(255, 255, 255));
                        this.rd.fillRoundRect(207, 45, 577, 394, 20, 20);
                        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawRoundRect(207, 45, 577, 394, 20, 20);
                        this.flk = !this.flg ? (this.flk += 5) : (this.flk -= 5);
                        if (this.flk >= 100) {
                            this.flk = 100;
                            this.flg = true;
                        }
                        if (this.flk <= 60) {
                            this.flk = 60;
                            this.flg = false;
                        }
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.setComposite(AlphaComposite.getInstance(3, (float)this.flk / 100.0f));
                        boolean bl5 = false;
                        if (this.protab == 2) {
                            if (this.proname.equals(this.xt.nickname)) {
                                this.rd.drawString("Your Stages", 227, 67);
                                bl5 = true;
                            } else {
                                this.rd.drawString("" + this.proname + "'s", 227, 67);
                                this.rd.drawString("Stages", 227, 84);
                            }
                            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                            this.gs.proitem.move(496 - this.gs.proitem.getWidth() / 2, 60);
                            this.gs.proitem.show = true;
                            if (this.stringbutton(this.rd, "< Back to Profile", 715, 76, 1, n2, n3, bl, 0, 0)) {
                                this.protab = 0;
                                this.gs.proitem.show = false;
                                this.addstage = 0;
                            }
                            if (this.gs.proitem.sel != 0) {
                                if (this.gs.proitem.getSelectedItem().equals(this.loadpstage)) {
                                    if (this.loadedpstage) {
                                        this.m.trk = 4;
                                        this.m.ih = -10;
                                        this.m.iw = -10;
                                        this.m.h = 320;
                                        this.m.w = 580;
                                        this.m.cx = 280;
                                        this.m.cy = 150;
                                        this.m.aroundtrack(this.cp);
                                        this.rdo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                                        this.m.d(this.rdo);
                                        int n21 = 0;
                                        int[] nArray = new int[1000];
                                        for (int i = 0; i < this.gs.nob; ++i) {
                                            if (this.co[i].dist != 0) {
                                                nArray[n21] = i;
                                                ++n21;
                                                continue;
                                            }
                                            this.co[i].d(this.rdo);
                                        }
                                        objectArray = new int[n21];
                                        for (n15 = 0; n15 < n21; ++n15) {
                                            objectArray[n15] = false;
                                        }
                                        for (n15 = 0; n15 < n21; ++n15) {
                                            for (int i = n15 + 1; i < n21; ++i) {
                                                if (this.co[nArray[n15]].dist != this.co[nArray[i]].dist) {
                                                    if (this.co[nArray[n15]].dist < this.co[nArray[i]].dist) {
                                                        int n22 = n15;
                                                        objectArray[n22] = objectArray[n22] + 1;
                                                        continue;
                                                    }
                                                    int n23 = i;
                                                    objectArray[n23] = objectArray[n23] + 1;
                                                    continue;
                                                }
                                                if (i > n15) {
                                                    int n24 = n15;
                                                    objectArray[n24] = objectArray[n24] + 1;
                                                    continue;
                                                }
                                                int n25 = i;
                                                objectArray[n25] = objectArray[n25] + 1;
                                            }
                                        }
                                        for (n15 = 0; n15 < n21; ++n15) {
                                            for (int i = 0; i < n21; ++i) {
                                                if (objectArray[i] != n15) continue;
                                                this.co[nArray[i]].d(this.rdo);
                                            }
                                        }
                                        this.rdo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                        this.m.trk = 0;
                                        this.m.h = 450;
                                        this.m.w = 800;
                                        this.m.cx = 400;
                                        this.m.cy = 225;
                                        this.rdo.setComposite(AlphaComposite.getInstance(3, 0.5f));
                                        this.rdo.setColor(new Color(255, 255, 255));
                                        this.rdo.fillRoundRect(9, 44, 136, 39, 20, 20);
                                        this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                                        this.rdo.setFont(new Font("Arial", 1, 12));
                                        this.ftm = this.rdo.getFontMetrics();
                                        this.rdo.setColor(new Color(0, 0, 0));
                                        this.rdo.drawString("Created/Published by", 17, 59);
                                        n15 = 17 + this.ftm.stringWidth("Created/Published by") / 2 - this.ftm.stringWidth(this.cp.maker) / 2;
                                        int n26 = n15 + this.ftm.stringWidth(this.cp.maker);
                                        this.rdo.drawString(this.cp.maker, n15, 74);
                                        this.rdo.drawLine(n15, 76, n26, 76);
                                        if (n2 > n15 + 216 && n2 < n26 + 216 && n3 > 154 && n3 < 168) {
                                            this.cur = 12;
                                            if (bl) {
                                                this.tab = 1;
                                                if (!this.proname.equals(this.cp.maker)) {
                                                    this.proname = this.cp.maker;
                                                    this.loadedp = false;
                                                    this.onexitpro();
                                                }
                                            }
                                        }
                                        if (this.gs.proitem.getSelectedIndex() != this.gs.proitem.no - 1 && this.stringbutton(this.rdo, " Next > ", 510, 200, -3, n2, n3, bl && !this.gs.openm, 216, 92)) {
                                            ++this.gs.proitem.sel;
                                        }
                                        if (this.gs.proitem.getSelectedIndex() != 1 && this.stringbutton(this.rdo, " < Prev ", 50, 200, -3, n2, n3, bl && !this.gs.openm, 216, 92)) {
                                            --this.gs.proitem.sel;
                                        }
                                        if (this.cp.pubt > 0) {
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.setColor(new Color(0, 0, 0));
                                            if (this.addstage == 2) {
                                                this.rd.drawString("Adding Stage...", 496 - this.ftm.stringWidth("Adding Stage...") / 2, 419);
                                                if (this.cd.staction == 0) {
                                                    this.addstage = 3;
                                                }
                                                if (this.cd.staction == -2) {
                                                    this.addstage = 4;
                                                }
                                                if (this.cd.staction == -3) {
                                                    this.addstage = 5;
                                                }
                                                if (this.cd.staction == -1) {
                                                    this.addstage = 6;
                                                }
                                            }
                                            if (this.addstage == 3) {
                                                this.rd.drawString("[" + this.cd.onstage + "] has been added to your stages!", 496 - this.ftm.stringWidth("[" + this.cd.onstage + "] has been added to your stages!") / 2, 419);
                                            }
                                            if (this.addstage == 4) {
                                                this.rd.drawString("You already have this stage.", 496 - this.ftm.stringWidth("You already have this stage.") / 2, 419);
                                            }
                                            if (this.addstage == 5) {
                                                this.rd.drawString("Cannot add more then 20 stages to your account!", 496 - this.ftm.stringWidth("Cannot add more then 20 stages to your account!") / 2, 419);
                                            }
                                            if (this.addstage == 6) {
                                                this.rd.drawString("Failed to add stage!  Unknown error, please try again later.", 496 - this.ftm.stringWidth("Failed to add stage!  Unknown error, please try again later.") / 2, 419);
                                            }
                                            if (this.addstage == 1) {
                                                String string = "Upgrade to a full account to add custom stages!";
                                                n14 = 496 - this.ftm.stringWidth(string) / 2;
                                                n13 = n14 + this.ftm.stringWidth(string);
                                                this.rd.drawString(string, n14, 419);
                                                if (this.waitlink != -1) {
                                                    this.rd.drawLine(n14, 435, n13, 435);
                                                }
                                                if (n2 > n14 && n2 < n13 && n3 > 421 && n3 < 435) {
                                                    if (this.waitlink != -1) {
                                                        this.cur = 12;
                                                    }
                                                    if (bl && this.waitlink == 0) {
                                                        this.gs.editlink(this.xt.nickname, true);
                                                        this.waitlink = -1;
                                                    }
                                                }
                                                if (this.waitlink > 0) {
                                                    --this.waitlink;
                                                }
                                            }
                                            if (this.addstage == 0 && this.xt.drawcarb(true, null, " Add to My Stages ", 437, 401, n2, n3, bl && this.blocknote == 0)) {
                                                if (this.xt.logged) {
                                                    this.cd.onstage = this.loadpstage;
                                                    this.cd.staction = 2;
                                                    this.cd.sparkstageaction();
                                                    this.addstage = 2;
                                                } else {
                                                    this.addstage = 1;
                                                    this.waitlink = 20;
                                                }
                                            }
                                        } else {
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.drawString("Private Stage", 496 - this.ftm.stringWidth("Private Stage") / 2, 419);
                                        }
                                    } else {
                                        this.rdo.setColor(new Color(206, 171, 98));
                                        this.rdo.fillRect(0, 0, 560, 300);
                                        this.rdo.setColor(new Color(0, 0, 0));
                                        this.rdo.setFont(new Font("Arial", 1, 12));
                                        this.ftm = this.rdo.getFontMetrics();
                                        this.rdo.drawString("Failed to load stage, try again later or try another stage...", 280 - this.ftm.stringWidth("Failed to load stage, try again later or try another stage...") / 2, 140);
                                    }
                                } else {
                                    this.rdo.setColor(new Color(206, 171, 98));
                                    this.rdo.fillRect(0, 0, 560, 300);
                                    this.rdo.setColor(new Color(0, 0, 0));
                                    this.rdo.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rdo.getFontMetrics();
                                    this.rdo.drawString("Loading stage, please wait...", 280 - this.ftm.stringWidth("Loading stage, please wait...") / 2, 140);
                                }
                            } else {
                                this.rdo.setColor(new Color(206, 171, 98));
                                this.rdo.fillRect(0, 0, 560, 300);
                            }
                            this.rd.drawImage(this.gImage, 216, 92, null);
                        }
                        if (this.protab == 0) {
                            if (this.proname.equals(this.xt.nickname)) {
                                this.rd.drawString("Your Profile", 232, 67);
                                bl5 = true;
                            } else {
                                this.rd.drawString("" + this.proname + "'s Profile", 232, 67);
                            }
                            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                            if (this.loadedp) {
                                if (!bl5 && this.stringbutton(this.rd, "   My Profile   ", 715, 73, 1, n2, n3, bl, 0, 0)) {
                                    this.proname = this.xt.nickname;
                                    this.loadedp = false;
                                    this.onexitpro();
                                }
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.setFont(new Font("Arial", 0, 11));
                                this.ftm = this.rd.getFontMetrics();
                                if (this.logol) {
                                    this.drawl(this.rd, this.proname, 236, 101, true);
                                } else {
                                    this.rd.drawString("No logo available", 296 - this.ftm.stringWidth("No logo available") / 2, 121);
                                }
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                object = "Logo";
                                if (n2 > 232 && n2 < 359 && n3 > 84 && n3 < 134 && this.edit == 0 && bl5) {
                                    object = "Edit Logo";
                                    this.rd.drawLine(238, 98, 238 + this.ftm.stringWidth((String)object), 98);
                                    if (n2 > 238 && n2 < 238 + this.ftm.stringWidth((String)object) && n3 > 85 && n3 < 100) {
                                        this.cur = 12;
                                        if (bl) {
                                            if (this.xt.logged) {
                                                this.edit = 1;
                                                this.msg = "Edit your Nickname's logo";
                                                this.flko = 0;
                                            } else {
                                                this.edit = 5;
                                            }
                                        }
                                    }
                                }
                                this.rd.drawString((String)object, 238, 97);
                                this.rd.drawLine(232, 84, 232, 134);
                                this.rd.drawLine(232, 84, 238 + this.ftm.stringWidth((String)object) + 2, 84);
                                this.rd.drawLine(238 + this.ftm.stringWidth((String)object) + 2, 84, 238 + this.ftm.stringWidth((String)object) + 15, 97);
                                this.rd.drawLine(238 + this.ftm.stringWidth((String)object) + 15, 97, 359, 97);
                                this.rd.drawLine(359, 97, 359, 134);
                                this.rd.drawLine(232, 134, 359, 134);
                                if (bl5 && !this.xt.clan.equals(this.proclan)) {
                                    this.proclan = this.xt.clan;
                                }
                                if (!this.proclan.equals("")) {
                                    if (!this.drawl(this.rd, "#" + this.proclan + "#", 406, 101, true)) {
                                        this.rd.setFont(new Font("Arial", 1, 13));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.drawString("" + this.proclan + "", 581 - this.ftm.stringWidth("" + this.proclan + "") / 2, 121);
                                    }
                                } else {
                                    this.rd.setFont(new Font("Arial", 0, 11));
                                    this.ftm = this.rd.getFontMetrics();
                                    if (bl5) {
                                        this.rd.drawString("You have not joined a clan yet!", 416, 121);
                                        if (this.stringbutton(this.rd, "   Find a clan to join   ", 663, 121, 1, n2, n3, bl, 0, 0)) {
                                            this.tab = 3;
                                            this.cfase = 2;
                                            this.em = 1;
                                            this.msg = "Clan Search";
                                            this.smsg = "Listing clans with recent activity...";
                                            this.nclns = 0;
                                            this.spos5 = 0;
                                            this.lspos5 = 0;
                                            this.flko = 0;
                                        }
                                    } else if (this.xt.clan.equals("")) {
                                        this.rd.drawString("Has not joined a clan yet", 581 - this.ftm.stringWidth("Has not joined a clan yet") / 2, 121);
                                    } else {
                                        this.rd.drawString("Has not joined a clan yet", 430, 121);
                                        if (this.stringbutton(this.rd, " Invite to join your clan ", 657, 121, 1, n2, n3, bl, 0, 0)) {
                                            this.tab = 2;
                                            this.itab = 0;
                                            this.litab = -1;
                                            this.openc = 10;
                                            if (!this.opname.equals(this.proname)) {
                                                this.opname = this.proname;
                                                this.lastsub = "";
                                                this.readmsg = 1;
                                            }
                                            this.itemsel = 3;
                                            this.forcsel = true;
                                        }
                                    }
                                }
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                object = "Clan";
                                if (n2 > 402 && n2 < 759 && n3 > 84 && n3 < 134 && !this.proclan.equals("") && this.edit == 0) {
                                    object = "Clan :  " + this.proclan + "";
                                    this.rd.drawLine(408, 98, 408 + this.ftm.stringWidth((String)object), 98);
                                    if (n2 > 408 && n2 < 408 + this.ftm.stringWidth((String)object) && n3 > 85 && n3 < 100 || n2 > 406 && n2 < 756 && n3 > 101 && n3 < 131) {
                                        this.cur = 12;
                                        if (bl) {
                                            if (!this.claname.equals(this.proclan)) {
                                                this.claname = this.proclan;
                                                this.loadedc = false;
                                            }
                                            this.spos5 = 0;
                                            this.lspos5 = 0;
                                            this.cfase = 3;
                                            this.tab = 3;
                                            this.ctab = 0;
                                        }
                                    }
                                }
                                this.rd.drawString((String)object, 408, 97);
                                this.rd.drawLine(402, 84, 402, 134);
                                this.rd.drawLine(402, 84, 408 + this.ftm.stringWidth((String)object) + 2, 84);
                                this.rd.drawLine(408 + this.ftm.stringWidth((String)object) + 2, 84, 408 + this.ftm.stringWidth((String)object) + 15, 97);
                                this.rd.drawLine(408 + this.ftm.stringWidth((String)object) + 15, 97, 759, 97);
                                this.rd.drawLine(759, 97, 759, 134);
                                this.rd.drawLine(402, 134, 759, 134);
                                this.rd.setFont(new Font("Arial", 0, 11));
                                this.ftm = this.rd.getFontMetrics();
                                if (this.avatarl) {
                                    this.rd.drawImage(this.avatar, 236, 161, null);
                                } else {
                                    this.rd.drawString("No avatar available", 336 - this.ftm.stringWidth("No avatar available") / 2, 255);
                                }
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                object = "Avatar";
                                if (n2 > 232 && n2 < 439 && n3 > 144 && n3 < 364 && this.edit == 0 && bl5) {
                                    object = "Edit Avatar";
                                    this.rd.drawLine(238, 158, 238 + this.ftm.stringWidth((String)object), 158);
                                    if (n2 > 238 && n2 < 238 + this.ftm.stringWidth((String)object) && n3 > 145 && n3 < 160) {
                                        this.cur = 12;
                                        if (bl) {
                                            if (this.xt.logged) {
                                                this.edit = 2;
                                                this.msg = "Edit your proflie avatar";
                                                this.flko = 0;
                                            } else {
                                                this.edit = 5;
                                            }
                                        }
                                    }
                                }
                                this.rd.drawString((String)object, 238, 157);
                                this.rd.drawLine(232, 144, 232, 364);
                                this.rd.drawLine(232, 144, 238 + this.ftm.stringWidth((String)object) + 2, 144);
                                this.rd.drawLine(238 + this.ftm.stringWidth((String)object) + 2, 144, 238 + this.ftm.stringWidth((String)object) + 15, 157);
                                this.rd.drawLine(238 + this.ftm.stringWidth((String)object) + 15, 157, 439, 157);
                                this.rd.drawLine(439, 157, 439, 364);
                                this.rd.drawLine(232, 364, 439, 364);
                                object = "About";
                                if (n2 > 459 && n2 < 759 && n3 > 144 && n3 < 364 && this.edit == 0 && bl5) {
                                    object = "Edit About";
                                    this.rd.drawLine(465, 158, 465 + this.ftm.stringWidth((String)object), 158);
                                    if (n2 > 465 && n2 < 465 + this.ftm.stringWidth((String)object) && n3 > 145 && n3 < 160) {
                                        this.cur = 12;
                                        if (bl) {
                                            if (this.xt.logged) {
                                                this.edit = 3;
                                                this.msg = "";
                                                this.flko = 0;
                                                this.sentchange = 0;
                                                this.badlang = false;
                                            } else {
                                                this.edit = 5;
                                            }
                                        }
                                    }
                                }
                                this.rd.drawString((String)object, 465, 157);
                                this.rd.drawLine(459, 144, 459, 364);
                                this.rd.drawLine(459, 144, 465 + this.ftm.stringWidth((String)object) + 2, 144);
                                this.rd.drawLine(465 + this.ftm.stringWidth((String)object) + 2, 144, 465 + this.ftm.stringWidth((String)object) + 15, 157);
                                this.rd.drawLine(465 + this.ftm.stringWidth((String)object) + 15, 157, 759, 157);
                                this.rd.drawLine(759, 157, 759, 364);
                                this.rd.drawLine(459, 364, 759, 364);
                                if (this.nab != 0) {
                                    this.rd.setFont(new Font("Tahoma", 1, 11));
                                    int n27 = 200;
                                    if (this.nab == 2) {
                                        n27 = 192;
                                    }
                                    if (this.nab == 3) {
                                        n27 = 185;
                                    }
                                    for (int i = 0; i < this.nab; ++i) {
                                        this.rd.drawString(this.aboutxt[i], 469, n27 + i * 15);
                                    }
                                } else {
                                    this.rd.setFont(new Font("Arial", 0, 11));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.drawString("No description available", 609 - this.ftm.stringWidth("No description available") / 2, 200);
                                }
                                this.rd.drawLine(489, 230, 729, 230);
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("Racing", 532 - this.ftm.stringWidth("Racing") / 2, 253);
                                this.rd.drawString("VS", 532 - this.ftm.stringWidth("VS") / 2, 270);
                                this.rd.drawString("Wasting", 532 - this.ftm.stringWidth("Wasting") / 2, 287);
                                float f = this.racing;
                                float f2 = this.wasting;
                                if (this.racing > 10 && this.racing > this.wasting) {
                                    f = 10.0f;
                                    f2 = (float)this.wasting / ((float)this.racing / 10.0f);
                                }
                                if (this.wasting > 10 && this.wasting >= this.racing) {
                                    f2 = 10.0f;
                                    f = (float)this.racing / ((float)this.wasting / 10.0f);
                                }
                                f *= 14.0f;
                                f2 *= 14.0f;
                                for (n15 = 0; n15 < 5; ++n15) {
                                    if (f != 0.0f) {
                                        this.rd.setColor(new Color(0, n15 * 50, 255));
                                        this.rd.drawLine(569, 245 + n15, (int)(569.0f + f), 245 + n15);
                                        this.rd.drawLine(569, 254 - n15, (int)(569.0f + f), 254 - n15);
                                    }
                                    if (f2 == 0.0f) continue;
                                    this.rd.setColor(new Color(255, n15 * 50, 0));
                                    this.rd.drawLine(569, 279 + n15, (int)(569.0f + f2), 279 + n15);
                                    this.rd.drawLine(569, 288 - n15, (int)(569.0f + f2), 288 - n15);
                                }
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawRect(569, 244, 140, 11);
                                this.rd.drawRect(569, 278, 140, 11);
                                this.rd.drawLine(489, 304, 729, 304);
                                if (!this.themesong.equals("") && this.trackvol != 0) {
                                    if (this.playt == 1) {
                                        this.rd.drawString("Loading theme song, please wait...", 609 - this.ftm.stringWidth("Loading theme song, please wait...") / 2, 340);
                                    }
                                    if (this.playt == 0 && this.stringbutton(this.rd, " Play Theme Song ", 609, 340, 1, n2, n3, bl, 0, 0) && this.edit == 0) {
                                        this.playt = 1;
                                    }
                                    if (this.playt == 2) {
                                        this.rd.drawString("Theme song playing...", 609 - this.ftm.stringWidth("Theme song playing...") / 2, 325);
                                        if (this.stringbutton(this.rd, " Stop ", 609, 350, 2, n2, n3, bl, 0, 0)) {
                                            this.xt.strack.unload();
                                            this.playt = 0;
                                        }
                                    }
                                } else {
                                    this.rd.setFont(new Font("Arial", 0, 11));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.drawString("No theme song available", 609 - this.ftm.stringWidth("No theme song available") / 2, 340);
                                }
                                if (!bl5) {
                                    if (this.sfreq == 0) {
                                        this.rd.drawRect(232, 378, 527, 50);
                                        n15 = 0;
                                        for (int i = 0; i < this.npf; ++i) {
                                            if (!this.proname.toLowerCase().equals(this.fname[i].toLowerCase())) continue;
                                            n15 = 1;
                                            break;
                                        }
                                        if (n15 != 0) {
                                            if (this.stringbutton(this.rd, "    Un-friend    ", 313, 408, 1, n2, n3, bl, 0, 0)) {
                                                this.sfreq = 4;
                                            }
                                        } else if (this.stringbutton(this.rd, "   Add Friend   ", 313, 408, 1, n2, n3, bl, 0, 0)) {
                                            this.sfreq = 1;
                                        }
                                        if (this.stringbutton(this.rd, "   Send Message   ", 436, 408, 1, n2, n3, bl, 0, 0)) {
                                            this.tab = 2;
                                            this.openc = 10;
                                            this.itab = 0;
                                            this.litab = -1;
                                            if (!this.opname.equals(this.proname)) {
                                                this.opname = this.proname;
                                                this.lastsub = "";
                                                this.readmsg = 1;
                                            }
                                        }
                                        if (this.stringbutton(this.rd, "   View Cars   ", 558, 408, 1, n2, n3, bl, 0, 0)) {
                                            this.cd.viewname = this.proname;
                                            this.onexitpro();
                                            this.cd.action = 100;
                                            this.xt.cfase = 100;
                                            this.xt.onviewpro = true;
                                            this.xt.fase = 23;
                                        }
                                        if (this.stringbutton(this.rd, "   View Stages   ", 673, 408, 1, n2, n3, bl, 0, 0)) {
                                            this.protab = 2;
                                            this.loadpst = 0;
                                            this.gs.proitem.removeAll();
                                            this.gs.proitem.add(this.gs.rd, "Loading stages, please wait...");
                                        }
                                    } else {
                                        this.rd.setColor(new Color(236, 215, 140));
                                        this.rd.fillRect(232, 378, 527, 50);
                                        this.rd.setColor(new Color(0, 0, 0));
                                        this.rd.drawRect(232, 378, 527, 50);
                                        this.rd.setFont(new Font("Arial", 1, 12));
                                        this.ftm = this.rd.getFontMetrics();
                                        if (this.sfreq == 1) {
                                            this.rd.drawString("Sending a friend request to " + this.proname + ", please wait...", 495 - this.ftm.stringWidth("Sending a friend request to " + this.proname + ", please wait...") / 2, 408);
                                        }
                                        if (this.sfreq == 2) {
                                            this.rd.drawString("Friend request sent, waiting for " + this.proname + "'s approval.", 495 - this.ftm.stringWidth("Friend request sent, waiting for " + this.proname + "'s approval.") / 2, 408);
                                            if (this.stringbutton(this.rd, "  OK  ", 690, 408, 1, n2, n3, bl, 0, 0)) {
                                                this.sfreq = 0;
                                            }
                                        }
                                        if (this.sfreq == 3) {
                                            this.rd.drawString("Failed to send friend request, please try again later.", 495 - this.ftm.stringWidth("Failed to send friend request, please try again later.") / 2, 408);
                                            if (this.stringbutton(this.rd, "  OK  ", 690, 408, 1, n2, n3, bl, 0, 0)) {
                                                this.sfreq = 0;
                                            }
                                        }
                                        if (this.sfreq == 4) {
                                            this.rd.drawString("Removing " + this.proname + " from firends, please wait...", 495 - this.ftm.stringWidth("Removing " + this.proname + " from firends, please wait...") / 2, 408);
                                        }
                                        if (this.sfreq == 5) {
                                            this.rd.drawString("You are no longer friends with " + this.proname + ".", 495 - this.ftm.stringWidth("You are no longer friends with " + this.proname + ".") / 2, 408);
                                            if (this.stringbutton(this.rd, "  OK  ", 690, 408, 1, n2, n3, bl, 0, 0)) {
                                                this.sfreq = 0;
                                            }
                                        }
                                        if (this.sfreq == 6) {
                                            this.rd.drawString("Failed to remove friend, please try again later.", 495 - this.ftm.stringWidth("Failed to remove friend, please try again later.") / 2, 408);
                                            if (this.stringbutton(this.rd, "  OK  ", 690, 408, 1, n2, n3, bl, 0, 0)) {
                                                this.sfreq = 0;
                                            }
                                        }
                                    }
                                }
                                if (this.edit == 1 || this.edit == 2) {
                                    this.rd.setColor(new Color(244, 232, 204));
                                    this.rd.fillRoundRect(265, 92, 460, 220, 20, 20);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawRoundRect(265, 92, 460, 220, 20, 20);
                                    String[] stringArray = new String[]{"logo", "120x30", "4 : 1"};
                                    if (this.edit == 2) {
                                        stringArray[0] = "avatar";
                                        stringArray[1] = "200x200";
                                        stringArray[2] = "1 : 1";
                                    }
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    if (this.flko % 4 != 0 || this.flko == 0) {
                                        this.rd.drawString(this.msg, 495 - this.ftm.stringWidth(this.msg) / 2, 115);
                                    }
                                    if (this.flko != 0) {
                                        --this.flko;
                                    }
                                    this.rd.setFont(new Font("Arial", 0, 12));
                                    this.rd.drawString("The " + stringArray[0] + " image is " + stringArray[1] + " pixels.", 275, 140);
                                    this.rd.drawString("Any image uploaded will be resized to that width and height. For the best results", 275, 160);
                                    this.rd.drawString("try to upload an image that is bigger or equal to " + stringArray[1] + " and has the scale of", 275, 180);
                                    this.rd.drawString("[ " + stringArray[2] + " ]  in  [ Width : Height ].", 275, 200);
                                    this.rd.drawString("Image uploaded must be less than 1MB and in the format of JPEG, GIF or PNG.", 275, 220);
                                    if (this.upload == 0) {
                                        if (this.stringbutton(this.rd, "  Upload Image  ", 495, 250, 0, n2, n3, bl, 0, 0)) {
                                            FileDialog fileDialog = new FileDialog(new Frame(), "Upload Image");
                                            fileDialog.setMode(0);
                                            fileDialog.setVisible(true);
                                            this.filename = "" + fileDialog.getDirectory() + "" + fileDialog.getFile() + "";
                                            if (!this.filename.equals("nullnull")) {
                                                this.upload = 1;
                                            }
                                        }
                                    } else {
                                        this.rd.setFont(new Font("Arial", 1, 12));
                                        this.ftm = this.rd.getFontMetrics();
                                        if (this.upload == 1) {
                                            this.rd.drawString("Checking image...", 495 - this.ftm.stringWidth("Checking image...") / 2, 250);
                                        }
                                        if (this.upload == 2) {
                                            this.rd.drawString("Authenticating...", 495 - this.ftm.stringWidth("Authenticating...") / 2, 250);
                                        }
                                        if (this.upload == 3) {
                                            this.rd.drawString("Uploading image :  " + this.perc + " %", 495 - this.ftm.stringWidth("Uploading image :  80 %") / 2, 250);
                                        }
                                        if (this.upload == 4) {
                                            this.rd.drawString("Creating image online...", 495 - this.ftm.stringWidth("Creating image online...") / 2, 250);
                                        }
                                        if (this.upload == 5) {
                                            this.rd.drawString("Done", 495 - this.ftm.stringWidth("Done") / 2, 250);
                                        }
                                    }
                                    if (this.stringbutton(this.rd, " Cancel ", 495, 290, 2, n2, n3, bl, 0, 0)) {
                                        if (this.upload == 0) {
                                            this.edit = 0;
                                        } else {
                                            this.upload = 0;
                                        }
                                    }
                                }
                                if (this.edit == 3) {
                                    this.rd.setColor(new Color(244, 232, 204));
                                    this.rd.fillRoundRect(265, 38, 460, 390, 20, 20);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawRoundRect(265, 38, 460, 390, 20, 20);
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.drawString("Edit your about section", 495 - this.ftm.stringWidth("Edit your about section") / 2, 61);
                                    this.rd.setFont(new Font("Arial", 0, 12));
                                    if (!this.badlang) {
                                        this.rd.drawString("Type in a sentence that best describes you and your playing style in the game :", 275, 86);
                                    } else {
                                        this.rd.setFont(new Font("Arial", 1, 12));
                                        this.rd.drawString("The sentence must not contain bad language!", 275, 86);
                                        this.rd.setFont(new Font("Arial", 0, 12));
                                    }
                                    bl3 = true;
                                    this.rd.drawLine(315, 123, 675, 123);
                                    this.rd.drawString("The ( Racing VS Wasting ) is comparison between your multiplayer wins by", 275, 146);
                                    this.rd.drawString("racing versus wasting.", 275, 166);
                                    this.rd.drawString("It does not in anyway signify if you are better or worse than another player!", 275, 186);
                                    this.rd.drawString("It simply shows whether you have a tendency to win games by racing or by", 275, 206);
                                    this.rd.drawString("wasting, it shows what you are better at.", 275, 226);
                                    this.rd.drawLine(315, 241, 675, 241);
                                    this.rd.drawString("Upload your very own theme song!", 275, 264);
                                    this.rd.drawString("The theme song must be a Module Track that is in a zip file and less than 700KB.", 275, 284);
                                    this.rd.drawString("You can find lots of Module Tracks at modarchive.org.", 275, 304);
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    if (this.uploadt == 0) {
                                        if (this.msg.equals("")) {
                                            if (!this.themesong.equals("") && this.trackvol != 0) {
                                                this.rd.drawString("Current Track : " + this.themesong + "", 495 - this.ftm.stringWidth("Current Track : " + this.themesong + "") / 2, 324);
                                            } else {
                                                this.rd.setFont(new Font("Arial", 0, 12));
                                                this.rd.drawString("[ No theme song uploaded... ]", 495 - this.ftm.stringWidth("[ No theme song uploaded... ]") / 2, 324);
                                            }
                                        } else {
                                            if (this.flko % 4 != 0 || this.flko == 0) {
                                                this.rd.drawString(this.msg, 495 - this.ftm.stringWidth(this.msg) / 2, 324);
                                            }
                                            if (this.flko != 0) {
                                                --this.flko;
                                            }
                                        }
                                        if (this.stringbutton(this.rd, "  Upload Track  ", 495, 354, 0, n2, n3, bl, 0, 0)) {
                                            FileDialog fileDialog = new FileDialog(new Frame(), "Upload Track");
                                            fileDialog.setMode(0);
                                            fileDialog.setFile("*.zip");
                                            fileDialog.setVisible(true);
                                            this.filename = "" + fileDialog.getDirectory() + "" + fileDialog.getFile() + "";
                                            if (!this.filename.equals("nullnull")) {
                                                this.trackname = fileDialog.getFile().substring(0, fileDialog.getFile().length() - 4);
                                                this.uploadt = 1;
                                            }
                                        }
                                    } else {
                                        if (this.uploadt == 1) {
                                            this.rd.drawString("Checking MOD Track...", 495 - this.ftm.stringWidth("Checking MOD Track...") / 2, 354);
                                        }
                                        if (this.uploadt == 2) {
                                            this.rd.drawString("Authenticating...", 495 - this.ftm.stringWidth("Authenticating...") / 2, 354);
                                        }
                                        if (this.uploadt == 3) {
                                            this.rd.drawString("Uploading track, please wait...", 495 - this.ftm.stringWidth("Uploading track, please wait...") / 2, 354);
                                        }
                                        if (this.uploadt == 4) {
                                            this.rd.drawString("Adding track to your profile...", 495 - this.ftm.stringWidth("Adding track to your profile...") / 2, 354);
                                        }
                                        if (this.uploadt == 5) {
                                            this.rd.drawString("Done", 495 - this.ftm.stringWidth("Done") / 2, 354);
                                        }
                                    }
                                    this.rd.drawLine(315, 376, 675, 376);
                                    if (this.stringbutton(this.rd, "        Done        ", 495, 407, 1, n2, n3, bl, 0, 0)) {
                                        this.edit = 0;
                                        if (this.sentchange == 1) {
                                            if (this.xt.msgcheck(this.sentance) || this.sentance.toLowerCase().indexOf(this.gs.tpass.getText().toLowerCase()) != -1 || this.xt.acexp == -3) {
                                                this.edit = 3;
                                                this.sentchange = 0;
                                                this.sentance = "";
                                                this.gs.cmsg.setText(this.sentance);
                                                this.badlang = true;
                                            } else {
                                                this.sentchange = 2;
                                            }
                                            this.trunsent();
                                        }
                                    }
                                }
                                if (this.edit == 5) {
                                    this.rd.setColor(new Color(244, 232, 204));
                                    this.rd.fillRoundRect(265, 187, 460, 125, 20, 20);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawRoundRect(265, 187, 460, 125, 20, 20);
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.drawString("You need to upgrade your account to a full account to have a profile!", 495 - this.ftm.stringWidth("You need to upgrade your account to a full account to have a profile!") / 2, 209);
                                    this.rd.setColor(new Color(206, 171, 98));
                                    this.rd.fillRoundRect(405, 222, 180, 50, 20, 20);
                                    if (this.drawbutton(this.xt.upgrade, 495, 247, n2, n3, bl)) {
                                        this.gs.editlink(this.xt.nickname, true);
                                    }
                                    if (this.stringbutton(this.rd, "  Cancel  ", 495, 297, 2, n2, n3, bl, 0, 0)) {
                                        this.edit = 0;
                                    }
                                }
                            } else {
                                this.rd.drawString("Loading profile, please wait...", 495 - this.ftm.stringWidth("Loading profile, please wait...") / 2, 222);
                            }
                        }
                    } else {
                        this.edit = 0;
                        this.uploadt = 0;
                        this.sentchange = 0;
                        this.underc = 0;
                        this.protab = 0;
                        if (this.gs.proitem.show) {
                            this.gs.proitem.show = false;
                            this.addstage = 0;
                        }
                    }
                    if (this.tab == 2) {
                        this.dotab2(n2, n3, bl);
                    } else {
                        if (this.gs.sendtyp.isShowing()) {
                            this.gs.sendtyp.hide();
                        }
                        if (this.gs.senditem.isShowing()) {
                            this.gs.senditem.hide();
                        }
                        if (this.gs.datat.isShowing()) {
                            this.gs.datat.hide();
                        }
                        this.gs.ilaps.hide();
                        this.gs.icars.hide();
                        this.gs.sclass.hide();
                        this.gs.sfix.hide();
                    }
                    if (this.tab == 3) {
                        this.dotab3(n2, n3, bl);
                    } else {
                        if (this.gs.clcars.isShowing()) {
                            this.gs.clcars.hide();
                        }
                        if (this.editc != 0) {
                            this.editc = 0;
                            if (this.gs.clanlev.isShowing()) {
                                this.gs.clanlev.hide();
                            }
                        }
                        if (this.cfase == 1) {
                            this.cfase = 0;
                        }
                    }
                    if (this.ptab == 0) {
                        if (this.npo != -1) {
                            this.sdist = (this.npo - 7) * 50;
                            if (this.sdist < 0) {
                                this.sdist = 0;
                            }
                            this.scro = (int)((float)this.spos / 345.0f * (float)this.sdist);
                            for (int i = 0; i < this.npo; ++i) {
                                if (57 + 50 * i - this.scro <= 0 || 57 + 50 * (i - 1) - this.scro >= 438) continue;
                                boolean bl6 = false;
                                if (n2 > 26 && n2 < 146 && n3 > 38 + 50 * i - this.scro && n3 < 68 + 50 * i - this.scro && !this.onp && this.overit == 0) {
                                    bl6 = true;
                                    this.cur = 12;
                                    if (bl) {
                                        this.tab = 1;
                                        if (!this.proname.equals(this.pname[i])) {
                                            this.proname = this.pname[i];
                                            this.loadedp = false;
                                            this.onexitpro();
                                        }
                                    }
                                }
                                boolean bl7 = this.drawl(this.rd, this.pname[i], 26, 38 + 50 * i - this.scro, bl6);
                                if (!bl6 || !bl7) {
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawString(this.pname[i], 86 - this.ftm.stringWidth(this.pname[i]) / 2, 49 + 50 * i - this.scro);
                                    this.rd.setFont(new Font("Arial", 1, 11));
                                    this.ftm = this.rd.getFontMetrics();
                                    String string = "Not in any room...";
                                    if (this.pserver[i] >= 0 && this.pserver[i] <= 2 && this.proom[i] >= 0 && this.proom[i] <= 4) {
                                        string = "" + this.lg.snames[this.pserver[i]] + " :: Room " + (this.proom[i] + 1) + "";
                                        this.rd.setColor(new Color(49, 79, 0));
                                    }
                                    this.rd.drawString(string, 86 - this.ftm.stringWidth(string) / 2, 65 + 50 * i - this.scro);
                                }
                                this.rd.setColor(this.color2k(150, 150, 150));
                                this.rd.drawLine(5, 77 + 50 * i - this.scro, 167, 77 + 50 * i - this.scro);
                            }
                        } else {
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString("Loading players...", 86 - this.ftm.stringWidth("Loading players...") / 2, 200);
                        }
                    }
                    if (this.ptab == 1) {
                        if (this.npf >= 0) {
                            this.sdist = (this.npf - 7) * 50;
                            if (this.sdist < 0) {
                                this.sdist = 0;
                            }
                            this.scro = (int)((float)this.spos / 345.0f * (float)this.sdist);
                            int n28 = 0;
                            if (this.npf != 0) {
                                int n29;
                                for (n29 = 0; n29 < this.npf; ++n29) {
                                    int n30;
                                    int n31 = -1;
                                    for (n30 = 0; n30 < this.npo; n30 += 1) {
                                        if (!this.pname[n30].toLowerCase().equals(this.fname[n29].toLowerCase())) continue;
                                        n31 = n30;
                                        break;
                                    }
                                    if (n31 == -1) continue;
                                    if (57 + 50 * n28 - this.scro > 0 && 57 + 50 * (n28 - 1) - this.scro < 438) {
                                        n30 = 0;
                                        if (n2 > 26 && n2 < 146 && n3 > 38 + 50 * n28 - this.scro && n3 < 68 + 50 * n28 - this.scro && !this.onp && this.overit == 0 && this.freq <= 0) {
                                            n30 = 1;
                                            this.cur = 12;
                                            if (bl) {
                                                this.tab = 1;
                                                if (!this.proname.equals(this.fname[n29])) {
                                                    this.proname = this.fname[n29];
                                                    this.loadedp = false;
                                                    this.onexitpro();
                                                }
                                            }
                                        }
                                        boolean bl8 = this.drawl(this.rd, this.fname[n29], 26, 38 + 50 * n28 - this.scro, n30 != 0);
                                        if (!n30 || !bl8) {
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.drawString(this.fname[n29], 86 - this.ftm.stringWidth(this.fname[n29]) / 2, 49 + 50 * n28 - this.scro);
                                            this.rd.setFont(new Font("Arial", 1, 11));
                                            this.ftm = this.rd.getFontMetrics();
                                            String string = "Not in any room...";
                                            if (this.pserver[n31] >= 0 && this.pserver[n31] <= 2 && this.proom[n31] >= 0 && this.proom[n31] <= 4) {
                                                string = "" + this.lg.snames[this.pserver[n31]] + " :: Room " + (this.proom[n31] + 1) + "";
                                                this.rd.setColor(new Color(49, 79, 0));
                                            }
                                            this.rd.drawString(string, 86 - this.ftm.stringWidth(string) / 2, 65 + 50 * n28 - this.scro);
                                        }
                                        this.rd.setColor(this.color2k(150, 150, 150));
                                        this.rd.drawLine(5, 77 + 50 * n28 - this.scro, 167, 77 + 50 * n28 - this.scro);
                                    }
                                    ++n28;
                                }
                                for (n29 = 0; n29 < this.npf; ++n29) {
                                    int n32;
                                    int n33 = -1;
                                    for (n32 = 0; n32 < this.npo; n32 += 1) {
                                        if (!this.pname[n32].toLowerCase().equals(this.fname[n29].toLowerCase())) continue;
                                        n33 = n32;
                                        break;
                                    }
                                    if (n33 != -1) continue;
                                    if (57 + 50 * n28 - this.scro > 0 && 57 + 50 * (n28 - 1) - this.scro < 438) {
                                        n32 = 0;
                                        if (n2 > 26 && n2 < 146 && n3 > 38 + 50 * n28 - this.scro && n3 < 68 + 50 * n28 - this.scro && !this.onp && this.overit == 0 && this.freq <= 0) {
                                            n32 = 1;
                                            this.cur = 12;
                                            if (bl) {
                                                this.tab = 1;
                                                if (!this.proname.equals(this.fname[n29])) {
                                                    this.proname = this.fname[n29];
                                                    this.loadedp = false;
                                                    this.onexitpro();
                                                }
                                            }
                                        }
                                        boolean bl9 = this.drawl(this.rd, this.fname[n29], 26, 38 + 50 * n28 - this.scro, n32 != 0);
                                        if (!n32 || !bl9) {
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.drawString(this.fname[n29], 86 - this.ftm.stringWidth(this.fname[n29]) / 2, 49 + 50 * n28 - this.scro);
                                            this.rd.setFont(new Font("Arial", 0, 11));
                                            this.ftm = this.rd.getFontMetrics();
                                            String string = "Player Offline";
                                            this.rd.drawString(string, 86 - this.ftm.stringWidth(string) / 2, 65 + 50 * n28 - this.scro);
                                        }
                                        this.rd.setColor(this.color2k(150, 150, 150));
                                        this.rd.drawLine(5, 77 + 50 * n28 - this.scro, 167, 77 + 50 * n28 - this.scro);
                                    }
                                    ++n28;
                                }
                            } else {
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("No friends added yet.", 86 - this.ftm.stringWidth("No friends added yet.") / 2, 200);
                            }
                            if (this.freq == 1) {
                                this.rd.setColor(new Color(236, 215, 140));
                                this.rd.fillRect(-10, 28, 200, 130);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawRect(-10, 28, 200, 130);
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("Friend request from:", 86 - this.ftm.stringWidth("Friend request from:") / 2, 45);
                                this.rd.setColor(new Color(240, 222, 164));
                                this.rd.fillRect(26, 51, 119, 29);
                                if (!this.drawl(this.rd, this.freqname, 26, 51, true)) {
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawString(this.freqname, 86 - this.ftm.stringWidth(this.freqname) / 2, 70);
                                    this.rd.setColor(this.color2k(150, 150, 150));
                                    this.rd.drawRect(26, 51, 119, 29);
                                }
                                if (n2 > 26 && n2 < 146 && n3 > 51 && n3 < 81) {
                                    this.cur = 12;
                                    if (bl) {
                                        this.tab = 1;
                                        if (!this.proname.equals(this.freqname)) {
                                            this.proname = this.freqname;
                                            this.loadedp = false;
                                            this.onexitpro();
                                        }
                                    }
                                }
                                if (this.stringbutton(this.rd, "    Confirm    ", 86, 107, 0, n2, n3, bl, 0, 0)) {
                                    this.freq = 2;
                                }
                                if (this.stringbutton(this.rd, "Cancel", 86, 140, 2, n2, n3, bl, 0, 0)) {
                                    this.freq = 3;
                                }
                            }
                            if (this.freq == -1) {
                                this.rd.setColor(new Color(236, 215, 140));
                                this.rd.fillRect(-10, 28, 200, 25);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawRect(-10, 28, 200, 25);
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("Failed to confirm friend!", 86 - this.ftm.stringWidth("Failed to confirm friend!") / 2, 45);
                                --this.cntf;
                                if (this.cntf <= 0) {
                                    this.freq = 0;
                                }
                            }
                            if (this.freq == -2) {
                                this.rd.setColor(new Color(236, 215, 140));
                                this.rd.fillRect(-10, 28, 200, 25);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawRect(-10, 28, 200, 25);
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("Failed to cancel request!", 86 - this.ftm.stringWidth("Failed to cancel request!") / 2, 45);
                                --this.cntf;
                                if (this.cntf <= 0) {
                                    this.freq = 0;
                                }
                            }
                            if (this.freq == 2) {
                                this.rd.setColor(new Color(236, 215, 140));
                                this.rd.fillRect(-10, 28, 200, 25);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawRect(-10, 28, 200, 25);
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("Confirming friend...", 86 - this.ftm.stringWidth("Confirming friend...") / 2, 45);
                            }
                            if (this.freq == 3) {
                                this.rd.setColor(new Color(236, 215, 140));
                                this.rd.fillRect(-10, 28, 200, 25);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawRect(-10, 28, 200, 25);
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("Canceling...", 86 - this.ftm.stringWidth("Canceling...") / 2, 45);
                            }
                            if (this.freq == 6) {
                                this.rd.setColor(new Color(236, 215, 140));
                                this.rd.fillRect(-10, 28, 200, 61 + this.ncnf * 35);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawRect(-10, 28, 200, 61 + this.ncnf * 35);
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("Friend Confirmation(s):", 86 - this.ftm.stringWidth("Friend Confirmation(s):") / 2, 45);
                                for (int i = 0; i < this.ncnf; ++i) {
                                    this.rd.setColor(new Color(240, 222, 164));
                                    this.rd.fillRect(26, 51 + 35 * i, 119, 29);
                                    if (!this.drawl(this.rd, this.cnfname[i], 26, 51 + 35 * i, true)) {
                                        this.rd.setColor(new Color(0, 0, 0));
                                        this.rd.drawString(this.cnfname[i], 86 - this.ftm.stringWidth(this.cnfname[i]) / 2, 70 + 35 * i);
                                        this.rd.setColor(this.color2k(150, 150, 150));
                                        this.rd.drawRect(26, 51 + 35 * i, 119, 29);
                                    }
                                    if (n2 <= 26 || n2 >= 146 || n3 <= 51 + 35 * i || n3 >= 81 + 35 * i) continue;
                                    this.cur = 12;
                                    if (!bl) continue;
                                    this.tab = 1;
                                    if (this.proname.equals(this.cnfname[i])) continue;
                                    this.proname = this.cnfname[i];
                                    this.loadedp = false;
                                    this.onexitpro();
                                }
                                if (this.stringbutton(this.rd, "  OK  ", 86, 107 + 35 * (this.ncnf - 1), 0, n2, n3, bl, 0, 0)) {
                                    this.freq = -6;
                                }
                            }
                        } else {
                            if (this.npf == -1) {
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("Loading friends...", 86 - this.ftm.stringWidth("Loading friends...") / 2, 200);
                            }
                            if (this.npf == -2) {
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("Failed to load friends!", 86 - this.ftm.stringWidth("Failed to load friends!") / 2, 200);
                            }
                        }
                    }
                    if (this.ptab == 2) {
                        if (this.xt.clan.equals("")) {
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString("Not in a Clan", 86 - this.ftm.stringWidth("Not in a Clan") / 2, 200);
                            this.rd.setFont(new Font("Arial", 0, 11));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString("You haven't joined and clan yet.", 86 - this.ftm.stringWidth("You haven't joined and clan yet.") / 2, 220);
                        } else if (this.loadedcm) {
                            int n34;
                            this.sdist = (this.ncln - 7) * 50;
                            if (this.sdist < 0) {
                                this.sdist = 0;
                            }
                            this.scro = (int)((float)this.spos / 345.0f * (float)this.sdist);
                            int n35 = 0;
                            for (n34 = 0; n34 < this.ncln; ++n34) {
                                int n36;
                                int n37 = -1;
                                for (n36 = 0; n36 < this.npo; n36 += 1) {
                                    if (!this.pname[n36].toLowerCase().equals(this.clname[n34].toLowerCase())) continue;
                                    n37 = n36;
                                    break;
                                }
                                if (n37 == -1) continue;
                                if (57 + 50 * n35 - this.scro > 0 && 57 + 50 * (n35 - 1) - this.scro < 438) {
                                    n36 = 0;
                                    if (n2 > 26 && n2 < 146 && n3 > 38 + 50 * n35 - this.scro && n3 < 68 + 50 * n35 - this.scro && !this.onp && this.overit == 0 && this.freq <= 0) {
                                        n36 = 1;
                                        this.cur = 12;
                                        if (bl) {
                                            this.tab = 1;
                                            if (!this.proname.equals(this.clname[n34])) {
                                                this.proname = this.clname[n34];
                                                this.loadedp = false;
                                                this.onexitpro();
                                            }
                                        }
                                    }
                                    boolean bl10 = this.drawl(this.rd, this.clname[n34], 26, 38 + 50 * n35 - this.scro, n36 != 0);
                                    if (!n36 || !bl10) {
                                        this.rd.setFont(new Font("Arial", 1, 12));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.setColor(new Color(0, 0, 0));
                                        this.rd.drawString(this.clname[n34], 86 - this.ftm.stringWidth(this.clname[n34]) / 2, 49 + 50 * n35 - this.scro);
                                        this.rd.setFont(new Font("Arial", 1, 11));
                                        this.ftm = this.rd.getFontMetrics();
                                        String string = "Not in any room...";
                                        if (this.pserver[n37] >= 0 && this.pserver[n37] <= 2 && this.proom[n37] >= 0 && this.proom[n37] <= 4) {
                                            string = "" + this.lg.snames[this.pserver[n37]] + " :: Room " + (this.proom[n37] + 1) + "";
                                            this.rd.setColor(new Color(49, 79, 0));
                                        }
                                        this.rd.drawString(string, 86 - this.ftm.stringWidth(string) / 2, 65 + 50 * n35 - this.scro);
                                    }
                                    this.rd.setColor(this.color2k(150, 150, 150));
                                    this.rd.drawLine(5, 77 + 50 * n35 - this.scro, 167, 77 + 50 * n35 - this.scro);
                                }
                                ++n35;
                            }
                            for (n34 = 0; n34 < this.ncln; ++n34) {
                                int n38;
                                int n39 = -1;
                                for (n38 = 0; n38 < this.npo; n38 += 1) {
                                    if (!this.pname[n38].toLowerCase().equals(this.clname[n34].toLowerCase())) continue;
                                    n39 = n38;
                                    break;
                                }
                                if (n39 != -1) continue;
                                if (57 + 50 * n35 - this.scro > 0 && 57 + 50 * (n35 - 1) - this.scro < 438) {
                                    n38 = 0;
                                    if (n2 > 26 && n2 < 146 && n3 > 38 + 50 * n35 - this.scro && n3 < 68 + 50 * n35 - this.scro && !this.onp && this.overit == 0 && this.freq <= 0) {
                                        n38 = 1;
                                        this.cur = 12;
                                        if (bl) {
                                            this.tab = 1;
                                            if (!this.proname.equals(this.clname[n34])) {
                                                this.proname = this.clname[n34];
                                                this.loadedp = false;
                                                this.onexitpro();
                                            }
                                        }
                                    }
                                    boolean bl11 = this.drawl(this.rd, this.clname[n34], 26, 38 + 50 * n35 - this.scro, n38 != 0);
                                    if (!n38 || !bl11) {
                                        this.rd.setFont(new Font("Arial", 1, 12));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.setColor(new Color(0, 0, 0));
                                        this.rd.drawString(this.clname[n34], 86 - this.ftm.stringWidth(this.clname[n34]) / 2, 49 + 50 * n35 - this.scro);
                                        this.rd.setFont(new Font("Arial", 0, 11));
                                        this.ftm = this.rd.getFontMetrics();
                                        String string = "Player Offline";
                                        this.rd.drawString(string, 86 - this.ftm.stringWidth(string) / 2, 65 + 50 * n35 - this.scro);
                                    }
                                    this.rd.setColor(this.color2k(150, 150, 150));
                                    this.rd.drawLine(5, 77 + 50 * n35 - this.scro, 167, 77 + 50 * n35 - this.scro);
                                }
                                ++n35;
                            }
                        } else {
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString("Loading clan mates...", 86 - this.ftm.stringWidth("Loading clan mates...") / 2, 200);
                        }
                    }
                } else if (this.open == 452) {
                    this.rd.setColor(this.color2k(230, 230, 230));
                    this.rd.fillRoundRect(240, 170, 511, 90, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(240, 170, 511, 90, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("Failed to connect to server at this time, please exit and try again later.", 495 - this.ftm.stringWidth("Failed to connect to server at this time, please exit and try again later.") / 2, 200);
                    if (this.stringbutton(this.rd, "  Exit  ", 495, 230, 1, n2, n3, bl, 0, 0)) {
                        this.open = 450;
                        this.upo = false;
                        this.domon = false;
                    }
                }
                int[] nArray = new int[]{193, 193, 295, 318};
                int[] nArray2 = new int[]{33, 10, 10, 33};
                for (n8 = 0; n8 < 4; ++n8) {
                    boolean bl12 = false;
                    if (this.tab == 3 && n8 == 0) {
                        bl12 = true;
                    }
                    if (this.tab == 1 && n8 == 1) {
                        bl12 = true;
                    }
                    if (this.tab == 2 && n8 == 2) {
                        bl12 = true;
                    }
                    if (this.tab == 0 && n8 == 3) {
                        bl12 = true;
                    }
                    this.rd.setColor(new Color(255, 255, 255));
                    if (bl12) {
                        this.rd.setComposite(AlphaComposite.getInstance(3, 0.6f));
                    } else {
                        this.rd.setComposite(AlphaComposite.getInstance(3, 0.2f));
                    }
                    if (n3 >= 12 && n3 <= 32 && n2 > nArray[0] && n2 < nArray[3]) {
                        this.rd.setComposite(AlphaComposite.getInstance(3, 0.4f));
                        if (bl && !this.gs.openm) {
                            if (n8 == 0) {
                                this.tab = 3;
                            }
                            if (n8 == 1 || n8 == 2) {
                                this.tab = n8;
                            }
                            if (n8 == 3) {
                                this.tab = 0;
                            }
                            if (this.tab == 1 && this.proname.equals("")) {
                                this.proname = this.xt.nickname;
                                this.loadedp = false;
                                this.onexitpro();
                            }
                        }
                    }
                    this.rd.fillPolygon(nArray, nArray2, 4);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawPolygon(nArray, nArray2, 4);
                    this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    if (n8 == 0) {
                        nArray2[1] = 13;
                        nArray2[2] = 13;
                        nArray[2] = 298;
                    }
                    int n40 = 0;
                    while (n40 < 4) {
                        int n41 = n40++;
                        nArray[n41] = nArray[n41] + 125;
                    }
                }
                this.rd.drawImage(this.xt.cnmc, 224, 15, null);
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawLine(191, 34, 800, 34);
                this.rd.setColor(this.color2k(200, 200, 200));
                this.rd.fillRect(0, 0, 190, 28);
                this.rd.setColor(this.color2k(150, 150, 150));
                this.rd.drawLine(0, 25, 170, 25);
                this.rd.setColor(this.color2k(200, 200, 200));
                this.rd.fillRect(0, 438, 190, 12);
                this.rd.setColor(this.color2k(150, 150, 150));
                this.rd.drawLine(0, 440, 170, 440);
                this.rd.setColor(this.color2k(200, 200, 200));
                this.rd.fillRect(173, 28, 17, 410);
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawLine(191, 0, 191, 450);
                if (n2 > 0 && n2 < 171 && n3 > 2 && n3 < 23) {
                    if (!this.onp) {
                        this.rd.setColor(this.color2k(220, 220, 220));
                        this.rd.fillRect(2, 2, 146, 21);
                    }
                    this.rd.setColor(this.color2k(255, 255, 255));
                    if (bl && this.overit == 0 && !this.onp) {
                        this.onp = true;
                        this.overit = 5;
                    }
                } else {
                    this.rd.setColor(this.color2k(235, 235, 235));
                }
                if (this.overit < 0) {
                    ++this.overit;
                }
                this.rd.fillRect(150, 2, 20, 20);
                this.rd.setColor(this.color2k(150, 150, 150));
                this.rd.drawRect(150, 2, 20, 20);
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawLine(157, 10, 157, 11);
                this.rd.drawLine(158, 11, 158, 12);
                this.rd.drawLine(159, 12, 159, 13);
                this.rd.drawLine(160, 13, 160, 14);
                this.rd.drawLine(161, 12, 161, 13);
                this.rd.drawLine(162, 11, 162, 12);
                this.rd.drawLine(163, 10, 163, 11);
                if (this.ptab == 0) {
                    this.rd.drawImage(this.xt.players, 7, 5, null);
                }
                if (this.ptab == 1) {
                    this.rd.drawImage(this.xt.myfr, 21, 4, null);
                }
                if (this.ptab == 2) {
                    this.rd.drawImage(this.xt.mycl, 34, 4, null);
                }
                if (this.onp) {
                    this.rd.setColor(this.color2k(200, 200, 200));
                    this.rd.fillRect(0, 25, 170, 67);
                    this.rd.setColor(this.color2k(150, 150, 150));
                    this.rd.drawRect(0, 25, 170, 67);
                    if (n2 > 0 && n2 < 171 && n3 >= 26 && n3 < 48) {
                        this.rd.setColor(this.color2k(235, 235, 235));
                        this.rd.fillRect(1, 26, 169, 22);
                        if (bl) {
                            this.ptab = 0;
                        }
                    }
                    if (n2 > 0 && n2 < 171 && n3 >= 48 && n3 < 70) {
                        this.rd.setColor(this.color2k(235, 235, 235));
                        this.rd.fillRect(1, 48, 169, 22);
                        if (bl) {
                            this.ptab = 1;
                            this.npf = -1;
                        }
                    }
                    if (n2 > 0 && n2 < 171 && n3 >= 70 && n3 < 92) {
                        this.rd.setColor(this.color2k(235, 235, 235));
                        this.rd.fillRect(1, 70, 169, 22);
                        if (bl) {
                            this.ptab = 2;
                        }
                    }
                    this.rd.drawImage(this.xt.players, 7, 30, null);
                    this.rd.drawImage(this.xt.myfr, 21, 51, null);
                    this.rd.drawImage(this.xt.mycl, 34, 73, null);
                    if (bl && this.overit == 0) {
                        this.onp = false;
                        this.overit = -5;
                    }
                    if (this.overit > 0) {
                        --this.overit;
                    }
                }
                if (this.mscro == 831 || this.sdist == 0) {
                    if (this.sdist == 0) {
                        this.rd.setColor(this.color2k(205, 205, 205));
                    } else {
                        this.rd.setColor(this.color2k(215, 215, 215));
                    }
                    this.rd.fillRect(173, 28, 17, 17);
                } else {
                    this.rd.setColor(this.color2k(220, 220, 220));
                    this.rd.fill3DRect(173, 28, 17, 17, true);
                }
                if (this.sdist != 0) {
                    this.rd.drawImage(this.xt.asu, 178, 34, null);
                }
                if (this.mscro == 832 || this.sdist == 0) {
                    if (this.sdist == 0) {
                        this.rd.setColor(this.color2k(205, 205, 205));
                    } else {
                        this.rd.setColor(this.color2k(215, 215, 215));
                    }
                    this.rd.fillRect(173, 421, 17, 17);
                } else {
                    this.rd.setColor(this.color2k(220, 220, 220));
                    this.rd.fill3DRect(173, 421, 17, 17, true);
                }
                if (this.sdist != 0) {
                    this.rd.drawImage(this.xt.asd, 178, 428, null);
                }
                if (this.sdist != 0) {
                    if (this.lspos != this.spos) {
                        this.rd.setColor(this.color2k(215, 215, 215));
                        this.rd.fillRect(173, 45 + this.spos, 17, 31);
                    } else {
                        if (this.mscro == 831) {
                            this.rd.setColor(this.color2k(215, 215, 215));
                        }
                        this.rd.fill3DRect(173, 45 + this.spos, 17, 31, true);
                    }
                    this.rd.setColor(this.color2k(150, 150, 150));
                    this.rd.drawLine(178, 58 + this.spos, 184, 58 + this.spos);
                    this.rd.drawLine(178, 60 + this.spos, 184, 60 + this.spos);
                    this.rd.drawLine(178, 62 + this.spos, 184, 62 + this.spos);
                    if (this.mscro > 800 && this.lspos != this.spos) {
                        this.lspos = this.spos;
                    }
                    if (bl) {
                        if (this.mscro == 825 && n2 > 173 && n2 < 190 && n3 > 45 + this.spos && n3 < this.spos + 76) {
                            this.mscro = n3 - this.spos;
                        }
                        if (this.mscro == 825 && n2 > 171 && n2 < 192 && n3 > 26 && n3 < 47) {
                            this.mscro = 831;
                        }
                        if (this.mscro == 825 && n2 > 171 && n2 < 192 && n3 > 419 && n3 < 440) {
                            this.mscro = 832;
                        }
                        if (this.mscro == 825 && n2 > 173 && n2 < 190 && n3 > 45 && n3 < 421) {
                            this.mscro = 60;
                            this.spos = n3 - this.mscro;
                        }
                        if ((n8 = 2670 / this.sdist) < 1) {
                            n8 = 1;
                        }
                        if (this.mscro == 831) {
                            this.spos -= n8;
                            if (this.spos > 345) {
                                this.spos = 345;
                            }
                            if (this.spos < 0) {
                                this.spos = 0;
                            }
                            this.lspos = this.spos;
                        }
                        if (this.mscro == 832) {
                            this.spos += n8;
                            if (this.spos > 345) {
                                this.spos = 345;
                            }
                            if (this.spos < 0) {
                                this.spos = 0;
                            }
                            this.lspos = this.spos;
                        }
                        if (this.mscro < 800) {
                            this.spos = n3 - this.mscro;
                            if (this.spos > 345) {
                                this.spos = 345;
                            }
                            if (this.spos < 0) {
                                this.spos = 0;
                            }
                        }
                        if (this.mscro == 825) {
                            this.mscro = 925;
                        }
                    } else if (this.mscro != 825) {
                        this.mscro = 825;
                    }
                }
                if (this.cur != this.curs) {
                    this.gs.setCursor(new Cursor(this.cur));
                    this.curs = this.cur;
                }
            } else {
                this.xt.drawWarning();
                if (this.gs.cmsg.isShowing()) {
                    this.gs.cmsg.hide();
                    this.gs.requestFocus();
                }
                if (this.xt.warning > 220) {
                    n = 0;
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
                ++this.xt.warning;
            }
        }
        if (bl2) {
            this.gs.movefieldd(this.gs.cmsg, 207, 414, 462, 22, true);
            if (this.gs.cmsg.getText().equals("Type here...") && n2 > 197 && n2 < 679 && n3 > 404 && n3 < 446) {
                this.gs.cmsg.setText("");
            }
            if (this.gs.cmsg.getText().length() > 200) {
                this.gs.cmsg.setText(this.gs.cmsg.getText().substring(0, 200));
                this.gs.cmsg.select(200, 200);
            }
        }
        if (bl3) {
            if (!this.gs.cmsg.isShowing()) {
                this.gs.cmsg.show();
                this.gs.cmsg.setText(this.sentance);
                this.gs.cmsg.requestFocus();
            }
            this.gs.movefield(this.gs.cmsg, 275, 91, 440, 22);
            if (!this.sentance.equals(this.gs.cmsg.getText())) {
                this.sentchange = 1;
                this.rd.setFont(new Font("Tahoma", 1, 11));
                this.ftm = this.rd.getFontMetrics();
                if (this.ftm.stringWidth(this.gs.cmsg.getText()) > 800) {
                    this.gs.cmsg.setText(this.sentance);
                } else {
                    this.sentance = this.gs.cmsg.getText();
                }
            }
        }
        if (this.dorank) {
            if (!this.gs.cmsg.isShowing()) {
                this.gs.cmsg.show();
                this.gs.cmsg.setText(this.mrank[this.em]);
                this.gs.cmsg.requestFocus();
            }
            this.gs.movefield(this.gs.cmsg, 402, 131, 300, 22);
            this.rd.setFont(new Font("Arial", 1, 11));
            this.ftm = this.rd.getFontMetrics();
            if (this.ftm.stringWidth(this.gs.cmsg.getText()) > 270) {
                int n42 = this.gs.cmsg.getText().length() - 1;
                if (n42 < 0) {
                    n42 = 0;
                }
                this.gs.cmsg.setText(this.gs.cmsg.getText().substring(0, n42));
                this.gs.cmsg.select(n42, n42);
            }
        }
        if (this.donewc) {
            if (!this.gs.temail.isShowing()) {
                this.gs.temail.show();
                this.gs.temail.setText("");
                this.gs.temail.requestFocus();
            }
            this.gs.movefield(this.gs.temail, 473, 141, 150, 22);
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            if (this.ftm.stringWidth(this.gs.temail.getText()) > 150) {
                int n43 = this.gs.temail.getText().length() - 1;
                if (n43 < 0) {
                    n43 = 0;
                }
                this.gs.temail.setText(this.gs.temail.getText().substring(0, n43));
                this.gs.temail.select(n43, n43);
            }
            if (!this.gs.temail.getText().equals(this.lccnam)) {
                this.lg.fixtext(this.gs.temail);
                this.lccnam = this.gs.temail.getText();
            }
            if (this.xt.msgcheck(this.gs.temail.getText())) {
                this.gs.temail.setText("");
            }
        }
        if (this.dosrch) {
            if (!this.gs.temail.isShowing()) {
                this.gs.temail.show();
                this.gs.temail.setText("");
                this.gs.temail.requestFocus();
            }
            this.gs.movefield(this.gs.temail, 371, 88, 150, 22);
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            if (this.ftm.stringWidth(this.gs.temail.getText()) > 150) {
                int n44 = this.gs.temail.getText().length() - 1;
                if (n44 < 0) {
                    n44 = 0;
                }
                this.gs.temail.setText(this.gs.temail.getText().substring(0, n44));
                this.gs.temail.select(n44, n44);
            }
            if (!this.gs.temail.getText().equals(this.lccnam)) {
                this.lg.fixtext(this.gs.temail);
                this.lccnam = this.gs.temail.getText();
            }
            if (this.xt.msgcheck(this.gs.temail.getText())) {
                this.gs.temail.setText("");
            }
        }
        if (this.doweb1) {
            if (!this.gs.temail.isShowing()) {
                this.gs.temail.show();
                this.gs.temail.setText(this.ltit);
                this.gs.temail.requestFocus();
            }
            this.gs.movefield(this.gs.temail, 411, 174, 150, 22);
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            if (this.ftm.stringWidth(this.gs.temail.getText()) > 200) {
                int n45 = this.gs.temail.getText().length() - 1;
                if (n45 < 0) {
                    n45 = 0;
                }
                this.gs.temail.setText(this.gs.temail.getText().substring(0, n45));
                this.gs.temail.select(n45, n45);
            }
            if (this.xt.msgcheck(this.gs.temail.getText())) {
                this.gs.temail.setText("");
            }
            if (!this.gs.cmsg.isShowing()) {
                this.gs.cmsg.show();
                this.gs.cmsg.setText(this.ldes);
            }
            this.gs.movefield(this.gs.cmsg, 411, 204, 300, 22);
            this.rd.setFont(new Font("Arial", 0, 12));
            this.ftm = this.rd.getFontMetrics();
            if (this.ftm.stringWidth(this.gs.cmsg.getText()) > 400) {
                int n46 = this.gs.cmsg.getText().length() - 1;
                if (n46 < 0) {
                    n46 = 0;
                }
                this.gs.cmsg.setText(this.gs.cmsg.getText().substring(0, n46));
                this.gs.cmsg.select(n46, n46);
            }
            if (this.xt.msgcheck(this.gs.cmsg.getText())) {
                this.gs.cmsg.setText("");
            }
        }
        if (this.doweb2) {
            if (!this.gs.temail.isShowing()) {
                this.gs.temail.show();
                this.gs.temail.requestFocus();
            }
            this.gs.movefield(this.gs.temail, 354, 134, 350, 22);
        }
        if (this.dommsg) {
            if (!this.donemsg) {
                this.gs.mmsg.setText(" ");
                if (!this.gs.applejava) {
                    this.gs.mmsg.show();
                    this.gs.mmsg.requestFocus();
                }
                this.donemsg = true;
            }
            this.gs.movefielda(this.gs.mmsg, 207, 389, 450, 50);
        } else {
            if (this.gs.mmsg.isShowing()) {
                this.gs.mmsg.hide();
            }
            if (this.donemsg) {
                this.donemsg = false;
            }
        }
        if (!(this.dosrch || this.donewc || this.doweb1 || this.doweb2 || !this.gs.temail.isShowing())) {
            this.gs.temail.hide();
        }
        if (!(bl2 || bl3 || this.dorank || this.doweb1 || this.open != 452 || !this.gs.cmsg.isShowing())) {
            this.gs.cmsg.hide();
        }
    }

    /*
     * Opcode count of 17438 triggered aggressive code reduction.  Override with --aggressivesizethreshold.
     */
    public void dotab3(int n, int n2, boolean bl) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        if (this.cfase == 0) {
            int n10;
            int n11;
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawRect(214, 44, 160, 50);
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            n9 = 239;
            n8 = this.ftm.stringWidth("Search for a clan");
            this.rd.fillRect(225, 57, 4, 4);
            this.rd.drawString("Search for a clan", n9, 63);
            if (n > n9 && n < n9 + n8 && n2 > 46 && n2 < 65 && this.editc == 0) {
                this.rd.drawLine(n9, 65, n9 + n8, 65);
                this.cur = 12;
                if (bl) {
                    this.cfase = 2;
                    this.em = 1;
                    this.msg = "Clan Search";
                    this.smsg = "Listing clans with recent activity...";
                    this.nclns = 0;
                    this.spos5 = 0;
                    this.lspos5 = 0;
                    this.flko = 0;
                }
            }
            n9 = 239;
            n8 = this.ftm.stringWidth("Create a new clan");
            this.rd.fillRect(225, 77, 4, 4);
            this.rd.drawString("Create a new clan", n9, 83);
            if (n > n9 && n < n9 + n8 && n2 > 66 && n2 < 85 && this.editc == 0) {
                this.rd.drawLine(n9, 85, n9 + n8, 85);
                this.cur = 12;
                if (bl) {
                    this.cfase = 1;
                    this.em = 0;
                    this.msg = "Start a new Need for Madness clan,";
                    this.flko = 0;
                }
            }
            if (!this.xt.clan.equals("")) {
                n7 = -40;
                n6 = 19;
                if (!this.drawl(this.rd, "#" + this.xt.clan + "#", 406 + n6, 101 + n7, true)) {
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("" + this.xt.clan + "", 581 + n6 - this.ftm.stringWidth("" + this.xt.clan + "") / 2, 121 + n7);
                }
                this.rd.setFont(new Font("Arial", 1, 12));
                this.ftm = this.rd.getFontMetrics();
                String string = "Your Clan";
                if (n > 402 + n6 && n < 759 + n6 && n2 > 84 + n7 && n2 < 134 + n7) {
                    string = "Clan :  " + this.xt.clan + "";
                    this.rd.drawLine(408 + n6, 98 + n7, 408 + n6 + this.ftm.stringWidth(string), 98 + n7);
                    if (n > 408 + n6 && n < 408 + n6 + this.ftm.stringWidth(string) && n2 > 85 + n7 && n2 < 100 + n7 || n > 406 + n6 && n < 756 + n6 && n2 > 101 + n7 && n2 < 131 + n7) {
                        this.cur = 12;
                        if (bl) {
                            if (!this.claname.equals(this.xt.clan)) {
                                this.claname = this.xt.clan;
                                this.loadedc = false;
                            }
                            this.spos5 = 0;
                            this.lspos5 = 0;
                            this.cfase = 3;
                            this.ctab = 0;
                            this.blocknote = 10;
                        }
                    }
                }
                this.rd.drawString(string, 408 + n6, 97 + n7);
                this.rd.drawLine(402 + n6, 84 + n7, 402 + n6, 134 + n7);
                this.rd.drawLine(402 + n6, 84 + n7, 408 + n6 + this.ftm.stringWidth(string) + 2, 84 + n7);
                this.rd.drawLine(408 + n6 + this.ftm.stringWidth(string) + 2, 84 + n7, 408 + n6 + this.ftm.stringWidth(string) + 15, 97 + n7);
                this.rd.drawLine(408 + n6 + this.ftm.stringWidth(string) + 15, 97 + n7, 759 + n6, 97 + n7);
                this.rd.drawLine(759 + n6, 97 + n7, 759 + n6, 134 + n7);
                this.rd.drawLine(402 + n6, 134 + n7, 759 + n6, 134 + n7);
            }
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            if (this.ntab == 0) {
                int[] nArray = new int[]{214, 225 + this.ftm.stringWidth("Game News"), 225 + this.ftm.stringWidth("Game News") + 23, 778, 778, 214};
                int[] nArray2 = new int[]{112, 112, 135, 135, 443, 443};
                this.rd.setColor(new Color(206, 171, 98));
                this.rd.fillPolygon(nArray, nArray2, 6);
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawPolygon(nArray, nArray2, 6);
            }
            if (this.ntab == 1) {
                int[] nArray = new int[]{214, 225 + this.ftm.stringWidth("Game News") + 23, 225 + this.ftm.stringWidth("Game News") + 23, 236 + this.ftm.stringWidth("Game News") + 23 + this.ftm.stringWidth("Wars World Championship"), 236 + this.ftm.stringWidth("Game News") + 23 + this.ftm.stringWidth("Wars World Championship") + 23, 778, 778, 214};
                int[] nArray3 = new int[]{135, 135, 112, 112, 135, 135, 443, 443};
                this.rd.setColor(new Color(206, 171, 98));
                this.rd.fillPolygon(nArray, nArray3, 8);
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawPolygon(nArray, nArray3, 8);
            }
            n7 = 223;
            n6 = this.ftm.stringWidth("Game News");
            n5 = 23;
            this.rd.drawString("Game News", n7, 107 + n5);
            if (n > n7 && n < n7 + n6 && n2 > 90 + n5 && n2 < 109 + n5) {
                this.rd.drawLine(n7, 109 + n5, n7 + n6, 109 + n5);
                this.cur = 12;
                if (bl) {
                    this.ntab = 0;
                    this.spos6 = 0;
                }
            }
            n7 += n6 + 35;
            n6 = this.ftm.stringWidth("Wars World Championship");
            this.rd.drawString("Wars World Championship", n7, 107 + n5);
            if (n > n7 && n < n7 + n6 && n2 > 90 + n5 && n2 < 109 + n5) {
                this.rd.drawLine(n7, 109 + n5, n7 + n6, 109 + n5);
                this.cur = 12;
                if (bl) {
                    this.ntab = 1;
                    this.spos6 = 0;
                }
            }
            this.rdo.setColor(new Color(206, 171, 98));
            this.rdo.fillRect(0, 0, 560, 300);
            this.darker = true;
            if (this.stringbutton(this.rd, "  Refresh  ", 738, 125, 3, n, n2, bl, 0, 0)) {
                if (this.ntab == 0) {
                    this.loadednews = 0;
                }
                if (this.ntab == 1) {
                    this.loadwstat = 0;
                }
            }
            this.darker = false;
            if (this.ntab == 0) {
                if (this.loadednews == 1) {
                    n11 = 0;
                    for (n4 = 0; n4 < 5; ++n4) {
                        if (this.nwarbs[n4] <= 0) continue;
                        ++n11;
                    }
                    if (n11 == 0) {
                        n11 = 1;
                    }
                    this.sdist = 100 + 35 * n11 + this.doi * 16 - 200;
                    if (this.sdist < 0) {
                        this.sdist = 0;
                    }
                    this.scro = (int)((float)this.spos6 / 229.0f * (float)this.sdist);
                    if (this.scro < 55) {
                        this.rdo.setFont(new Font("Tahoma", 1, 11));
                        this.rdo.setColor(new Color(0, 0, 0));
                        this.rdo.drawString("A big welcome to the latest players to join the game with full accounts!", 18, 15 - this.scro);
                        for (n4 = 0; n4 < 4; ++n4) {
                            n3 = 0;
                            boolean bl2 = false;
                            if (n > 234 + 128 * n4 && n < 354 + 128 * n4 && n2 > 159 - this.scro && n2 < 189 - this.scro && n2 > 139 && n2 < 439) {
                                bl2 = true;
                                this.cur = 12;
                                if (bl) {
                                    this.tab = 1;
                                    if (!this.proname.equals(this.newplayers[n4])) {
                                        this.proname = this.newplayers[n4];
                                        this.loadedp = false;
                                        this.onexitpro();
                                    }
                                }
                            }
                            if (!bl2) {
                                n3 = this.drawl(this.rdo, this.newplayers[n4], 18 + 128 * n4, 20 - this.scro, true);
                            } else {
                                this.drawl(this.rdo, this.newplayers[n4], 18 + 128 * n4, 20 - this.scro, false);
                            }
                            if (n3 != 0) continue;
                            this.rdo.setComposite(AlphaComposite.getInstance(3, 0.2f));
                            this.rdo.setColor(new Color(255, 255, 255));
                            this.rdo.fillRect(18 + 128 * n4, 20 - this.scro, 119, 29);
                            this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                            this.rdo.setColor(new Color(0, 0, 0));
                            this.rdo.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.drawRect(18 + 128 * n4, 20 - this.scro, 119, 29);
                            this.rdo.drawString(this.newplayers[n4], 78 + 128 * n4 - this.ftm.stringWidth(this.newplayers[n4]) / 2, 39 - this.scro);
                        }
                    }
                    if (75 + 35 * n11 - this.scro > 0) {
                        this.rdo.setFont(new Font("Tahoma", 1, 11));
                        this.rdo.setColor(new Color(0, 0, 0));
                        this.rdo.drawString("Recent clan wars & battles:", 18, 70 - this.scro);
                        n11 = 0;
                        for (n4 = 0; n4 < 5; ++n4) {
                            String string;
                            if (this.nwarbs[n4] <= 0) continue;
                            this.rdo.setComposite(AlphaComposite.getInstance(3, 0.2f));
                            n3 = 0;
                            n3 = this.drawl(this.rdo, "#" + this.nwclan[n4] + "#", 18, 75 - this.scro + 35 * n11, true);
                            if (n3 == 0) {
                                this.rdo.setColor(new Color(255, 255, 255));
                                this.rdo.fillRect(18, 75 - this.scro + 35 * n11, 350, 30);
                            }
                            this.rdo.setComposite(AlphaComposite.getInstance(3, 0.1f));
                            this.rdo.setColor(new Color(255, 255, 255));
                            this.rdo.fillRect(368, 75 - this.scro + 35 * n11, 154, 30);
                            this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                            this.rdo.setColor(new Color(0, 0, 0));
                            this.rdo.setFont(new Font("Tahoma", 1, 11));
                            String string2 = "war";
                            if (this.nwarbs[n4] == 2) {
                                string2 = "car battle";
                            }
                            if (this.nwarbs[n4] == 3) {
                                string2 = "stage battle";
                            }
                            String string3 = "" + this.nwclan[n4] + " defeated " + this.nlclan[n4] + " in a " + string2 + "!";
                            this.rdo.drawString(string3, 22, 87 - this.scro + 35 * n11);
                            this.tlink(this.rdo, 22, 87 - this.scro + 35 * n11, string3, this.nwclan[n4], n, n2, bl, 216, 139, 1, this.nwclan[n4], "");
                            this.tlink(this.rdo, 22, 87 - this.scro + 35 * n11, string3, this.nlclan[n4], n, n2, bl, 216, 139, 1, this.nlclan[n4], "");
                            if (this.nwarbs[n4] == 1 && this.nwinp[n4] != -1 && this.nlosp[n4] != -1) {
                                if (this.nwinob[n4].equals("champ")) {
                                    string = "" + this.nwclan[n4] + " has taken the clan wars world championship title!";
                                    this.rdo.setFont(new Font("Tahoma", 0, 11));
                                    this.rdo.drawString(string, 22, 100 - this.scro + 35 * n11);
                                    this.tlink(this.rdo, 22, 100 - this.scro + 35 * n11, string, "clan wars world championship title", n, n2, bl, 216, 139, 5, "", "");
                                } else if (this.nwinob[n4].equals("re-champ")) {
                                    string = "" + this.nwclan[n4] + " has defended and re-claimed the clan wars world championship title!";
                                    this.rdo.setFont(new Font("Tahoma", 0, 11));
                                    this.rdo.drawString(string, 22, 100 - this.scro + 35 * n11);
                                    this.tlink(this.rdo, 22, 100 - this.scro + 35 * n11, string, "clan wars world championship title", n, n2, bl, 216, 139, 5, "", "");
                                } else {
                                    string = "" + this.nwclan[n4] + " won: [ " + this.nwinp[n4] + " points ]  & " + this.nlclan[n4] + " lost: [ " + this.nlosp[n4] + " points ]";
                                    this.rdo.setFont(new Font("Tahoma", 0, 11));
                                    this.rdo.drawString(string, 22, 100 - this.scro + 35 * n11);
                                    this.tlink(this.rdo, 22, 100 - this.scro + 35 * n11, string, "" + this.nwinp[n4] + " points", n, n2, bl, 216, 139, 5, "", "");
                                    this.tlink(this.rdo, 22, 100 - this.scro + 35 * n11, string, "" + this.nlosp[n4] + " points", n, n2, bl, 216, 139, 5, "", "");
                                }
                            }
                            if (this.nwarbs[n4] == 2) {
                                string = "" + this.nwclan[n4] + " took car [" + this.nwinob[n4] + "] from " + this.nlclan[n4] + ".";
                                this.rdo.setFont(new Font("Tahoma", 0, 11));
                                this.rdo.drawString(string, 22, 100 - this.scro + 35 * n11);
                                this.tlink(this.rdo, 22, 100 - this.scro + 35 * n11, string, this.nwinob[n4], n, n2, bl, 216, 139, 3, this.nwinob[n4], this.nwclan[n4]);
                            }
                            if (this.nwarbs[n4] == 3) {
                                string = this.nwinob[n4];
                                if (string.length() > 20) {
                                    string = "" + string.substring(0, 20) + "...";
                                }
                                String string4 = "" + this.nwclan[n4] + " took stage [" + string + "] from " + this.nlclan[n4] + ".";
                                this.rdo.setFont(new Font("Tahoma", 0, 11));
                                this.rdo.drawString(string4, 22, 100 - this.scro + 35 * n11);
                                this.tlink(this.rdo, 22, 100 - this.scro + 35 * n11, string4, string, n, n2, bl, 216, 139, 4, this.nwinob[n4], this.nwclan[n4]);
                            }
                            this.rdo.setColor(new Color(98, 76, 29));
                            this.rdo.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.drawString(this.nwtime[n4], 518 - this.ftm.stringWidth(this.nwtime[n4]), 87 - this.scro + 35 * n11);
                            ++n11;
                        }
                        if (n11 == 0) {
                            this.rdo.setColor(new Color(0, 0, 0));
                            this.rdo.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.drawString("(No recent clan wars or battles have been played yet...)", 280 - this.ftm.stringWidth("(No recent clan wars or battles have been played yet...)") / 2, 91 - this.scro);
                            n11 = 1;
                        }
                    }
                    if ((n4 = 70 + 35 * n11 - this.scro) + 25 > 0) {
                        this.rdo.setFont(new Font("Tahoma", 1, 11));
                        this.rdo.setColor(new Color(0, 0, 0));
                        this.rdo.drawString("Recent clan activity:", 18, n4 + 20);
                    }
                    n3 = 0;
                    String string = "";
                    this.doi = 0;
                    for (n10 = 0; n10 < this.il; ++n10) {
                        if (this.text[n10].equals(string)) continue;
                        n3 = n3 == 0 ? 1 : 0;
                        if (n4 + this.doi * 16 + 38 > 0 && n4 + this.doi * 16 + 18 < 300) {
                            if (n3 != 0) {
                                this.rdo.setComposite(AlphaComposite.getInstance(3, 0.1f));
                                this.rdo.setColor(new Color(255, 255, 255));
                                this.rdo.fillRect(18, n4 + this.doi * 16 + 24, 504, 16);
                                this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                            }
                            this.rdo.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rdo.getFontMetrics();
                            if (this.text[n10].startsWith("Battle")) {
                                this.rdo.setFont(new Font("Tahoma", 1, 11));
                            } else {
                                this.rdo.setColor(new Color(98, 76, 29));
                                this.rdo.drawString(this.nttime[n10], 518 - this.ftm.stringWidth(this.nttime[n10]), n4 + this.doi * 16 + 36);
                            }
                            if (this.text[n10].indexOf("started") != -1) {
                                this.rdo.setFont(new Font("Tahoma", 1, 11));
                                if (this.text[n10].indexOf("war") == -1) {
                                    n3 = n3 == 0 ? 1 : 0;
                                }
                            }
                            if (this.text[n10].indexOf("clan wars world champion") != -1) {
                                this.rdo.setFont(new Font("Tahoma", 1, 11));
                                n3 = n3 == 0 ? 1 : 0;
                            }
                            this.rdo.setColor(new Color(0, 0, 0));
                            this.rdo.drawString(this.text[n10], 22, n4 + this.doi * 16 + 36);
                            for (int i = 0; i < this.nln[n10]; ++i) {
                                this.tlink(this.rdo, 22, n4 + this.doi * 16 + 36, this.text[n10], this.link[n10][i][0], n, n2, bl, 216, 139, this.getvalue(this.link[n10][i][1], 0), this.getSvalue(this.link[n10][i][1], 1), this.getSvalue(this.link[n10][i][1], 2));
                            }
                        }
                        string = this.text[n10];
                        ++this.doi;
                    }
                } else {
                    this.sdist = 0;
                }
                if (this.loadednews == 0) {
                    this.rdo.setFont(new Font("Tahoma", 1, 11));
                    this.ftm = this.rdo.getFontMetrics();
                    this.rdo.setColor(new Color(0, 0, 0));
                    this.rdo.drawString("Loading game news, please wait...", 280 - this.ftm.stringWidth("Loading game news, please wait...") / 2, 140);
                }
                if (this.loadednews == -1) {
                    this.rdo.setFont(new Font("Tahoma", 1, 11));
                    this.ftm = this.rdo.getFontMetrics();
                    this.rdo.setColor(new Color(0, 0, 0));
                    this.rdo.drawString("Failed to load game news, please try again later...", 280 - this.ftm.stringWidth("Failed to load game news, please try again later...") / 2, 140);
                }
            }
            if (this.ntab == 1) {
                this.darker = true;
                if (this.stringbutton(this.rd, "  About Championship  ", 617, 125, 3, n, n2, bl, 0, 0)) {
                    this.ntab = 2;
                }
                this.darker = false;
                if (this.loadwstat == 1) {
                    if (this.eng == -1) {
                        n11 = this.ncc;
                        if (this.champ >= 0) {
                            --n11;
                        }
                        if (n11 < 0) {
                            n11 = 0;
                        }
                        this.sdist = 154 + n11 * 45 - 260;
                        if (this.sdist < 0) {
                            this.sdist = 0;
                        }
                        this.scro = (int)((float)this.spos6 / 229.0f * (float)this.sdist);
                        this.rdo.setFont(new Font("Tahoma", 1, 11));
                        this.ftm = this.rdo.getFontMetrics();
                        n4 = this.ftm.stringWidth("Engagement Stats");
                        this.rdo.setColor(new Color(0, 0, 0));
                        this.rdo.drawString("Current World Champion", 10, 20 - this.scro);
                        this.rdo.setColor(new Color(228, 177, 31));
                        this.rdo.fillRoundRect(10, 28 - this.scro, 520, 70, 20, 20);
                        this.rdo.setColor(new Color(199, 154, 63));
                        this.rdo.fillRoundRect(13, 33 - this.scro, 514, 60, 20, 20);
                        this.rdo.setColor(new Color(255, 198, 0));
                        this.rdo.drawRoundRect(13, 33 - this.scro, 514, 60, 20, 20);
                        this.rdo.setColor(new Color(0, 0, 0));
                        this.rdo.drawRoundRect(10, 28 - this.scro, 520, 70, 20, 20);
                        if (this.champ == -1) {
                            this.rdo.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.drawString("No current world champion because no contender has attained or surpassed 3 points yet!", 270 - this.ftm.stringWidth("No current world champion because no contender has attained or surpassed 3 points yet!") / 2, 65 - this.scro);
                        }
                        if (this.champ == -2) {
                            this.rdo.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.drawString("No current world champion at this moment because the top contenders are tied in points!", 270 - this.ftm.stringWidth("No current world champion at this moment because the top contenders are tied in points!") / 2, 65 - this.scro);
                        }
                        if (this.champ >= 0) {
                            if (!this.drawl(this.rdo, "#" + this.conclan[this.champ] + "#", 21, 40 - this.scro, true)) {
                                this.rdo.setColor(new Color(100, 77, 31));
                                this.rdo.drawRect(21, 40 - this.scro, 349, 29);
                                this.rdo.setFont(new Font("Arial", 1, 13));
                                this.ftm = this.rdo.getFontMetrics();
                                this.rdo.drawString(this.conclan[this.champ], 196 - this.ftm.stringWidth(this.conclan[this.champ]) / 2, 59 - this.scro);
                            }
                            if (n > 237 && n < 587 && n2 > 179 - this.scro && n2 < 209 - this.scro && n2 > 139 && n2 < 439) {
                                this.cur = 12;
                                if (bl) {
                                    if (!this.claname.equals(this.conclan[this.champ])) {
                                        this.claname = this.conclan[this.champ];
                                        this.loadedc = false;
                                    }
                                    this.spos5 = 0;
                                    this.lspos5 = 0;
                                    this.cfase = 3;
                                    this.ctab = 0;
                                }
                            }
                            this.rdo.setFont(new Font("Tahoma", 0, 11));
                            this.rdo.setColor(new Color(0, 0, 0));
                            this.rdo.drawString("" + this.conclan[this.champ] + " is leading the championship by " + this.leadsby + " points difference from the first contender!", 22, 85 - this.scro);
                            this.rdo.setFont(new Font("Tahoma", 1, 11));
                            this.rdo.drawString("Attained points:  [ " + this.totp[this.champ] + " ]", 383, 51 - this.scro);
                            this.rdo.drawString("Engagement Stats", 383, 66 - this.scro);
                            this.rdo.drawLine(383, 68 - this.scro, 383 + n4, 68 - this.scro);
                            if (n > 599 && n < 383 + n4 + 216 && n2 > 195 - this.scro && n2 < 208 - this.scro && n2 > 139 && n2 < 439) {
                                this.cur = 12;
                                if (bl) {
                                    this.eng = this.champ;
                                    this.engo = 40 - this.scro;
                                    this.lspos6w = this.spos6;
                                    this.spos6 = 0;
                                }
                            }
                        }
                        this.rdo.setColor(new Color(0, 0, 0));
                        this.rdo.setFont(new Font("Tahoma", 1, 11));
                        this.ftm = this.rdo.getFontMetrics();
                        this.rdo.drawString("Contenders", 10, 125 - this.scro);
                        n11 = n11 > 3 ? 160 + 45 * (n11 - 3) : 160;
                        this.rdo.drawRoundRect(10, 133 - this.scro, 520, n11, 20, 20);
                        if (this.ncc == 0 || this.ncc == 1 && this.champ == 0) {
                            this.rdo.drawString("No contenders...", 270 - this.ftm.stringWidth("No contenders...") / 2, 160 - this.scro);
                            this.rdo.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.setColor(new Color(0, 0, 0));
                            this.rdo.drawString("To become a contender, a clan must get points by winning a war against any other clan.", 270 - this.ftm.stringWidth("To become a contender, a clan must get points by winning a war against any other clan.") / 2, 185 - this.scro);
                        } else {
                            this.rdo.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.setColor(new Color(0, 0, 0));
                            this.rdo.drawString("To become a contender, a clan must get points by winning a war against any other clan.", 270 - this.ftm.stringWidth("To become a contender, a clan must get points by winning a war against any other clan.") / 2, 147 - this.scro);
                            n3 = 0;
                            for (int i = 0; i < this.ncc; ++i) {
                                if (this.ord[i] == this.champ) continue;
                                this.rdo.setColor(new Color(199, 154, 63));
                                this.rdo.fillRect(11, 154 + n3 * 45 - this.scro, 519, 40);
                                if (!this.drawl(this.rdo, "#" + this.conclan[this.ord[i]] + "#", 21, 159 + n3 * 45 - this.scro, true)) {
                                    this.rdo.setColor(new Color(100, 77, 31));
                                    this.rdo.drawRect(21, 159 + n3 * 45 - this.scro, 349, 29);
                                    this.rdo.setFont(new Font("Arial", 1, 13));
                                    this.ftm = this.rdo.getFontMetrics();
                                    this.rdo.drawString(this.conclan[this.ord[i]], 196 - this.ftm.stringWidth(this.conclan[this.ord[i]]) / 2, 178 + n3 * 45 - this.scro);
                                }
                                if (n > 237 && n < 587 && n2 > 159 + n3 * 45 + 139 - this.scro && n2 < 189 + n3 * 45 + 139 - this.scro && n2 > 139 && n2 < 439) {
                                    this.cur = 12;
                                    if (bl) {
                                        if (!this.claname.equals(this.conclan[this.ord[i]])) {
                                            this.claname = this.conclan[this.ord[i]];
                                            this.loadedc = false;
                                        }
                                        this.spos5 = 0;
                                        this.lspos5 = 0;
                                        this.cfase = 3;
                                        this.ctab = 0;
                                    }
                                }
                                this.rdo.setColor(new Color(0, 0, 0));
                                this.rdo.setFont(new Font("Tahoma", 1, 11));
                                this.rdo.drawString("Attained points:  [ " + this.totp[this.ord[i]] + " ]", 383, 170 + n3 * 45 - this.scro);
                                this.rdo.drawString("Engagement Stats", 383, 185 + n3 * 45 - this.scro);
                                this.rdo.drawLine(383, 187 + n3 * 45 - this.scro, 383 + n4, 187 + n3 * 45 - this.scro);
                                if (n > 599 && n < 383 + n4 + 216 && n2 > 175 + n3 * 45 + 139 - this.scro && n2 < 188 + n3 * 45 + 139 - this.scro && n2 > 139 && n2 < 439) {
                                    this.cur = 12;
                                    if (bl) {
                                        this.eng = this.ord[i];
                                        this.engo = 159 + n3 * 45 - this.scro;
                                        this.lspos6w = this.spos6;
                                        this.spos6 = 0;
                                    }
                                }
                                ++n3;
                            }
                        }
                    } else {
                        if (this.engo == 15) {
                            this.sdist = 87 + this.ados + this.nvc[this.eng] * 17 - 260;
                            if (this.sdist < 0) {
                                this.sdist = 0;
                            }
                            this.scro = (int)((float)this.spos6 / 229.0f * (float)this.sdist);
                            this.ados = 0;
                            this.rdo.setFont(new Font("Tahoma", 1, 11));
                            this.rdo.setColor(new Color(0, 0, 0));
                            this.rdo.drawString("Engagement Stats", 385, 40 - this.scro);
                            String string = "s";
                            if (!this.xt.clan.equals("") && !this.xt.clan.toLowerCase().equals(this.conclan[this.eng].toLowerCase())) {
                                int n12;
                                this.ados = 116;
                                n4 = -1;
                                n3 = 0;
                                for (n12 = 0; n12 < this.ncc; ++n12) {
                                    if (!this.xt.clan.toLowerCase().equals(this.conclan[n12].toLowerCase())) continue;
                                    n3 = this.totp[n12];
                                    n4 = n12;
                                    break;
                                }
                                n12 = this.totp[this.eng] + 1;
                                n10 = n3 + 1;
                                if (n10 > this.totp[this.eng]) {
                                    n10 = this.totp[this.eng];
                                }
                                if (n4 != -1) {
                                    for (int i = 0; i < this.nvc[n4]; ++i) {
                                        if (!this.conclan[this.eng].toLowerCase().equals(this.verclan[n4][i].toLowerCase())) continue;
                                        if ((n12 -= this.points[n4][i]) >= 0) break;
                                        n12 = 0;
                                        break;
                                    }
                                }
                                this.rdo.setFont(new Font("Tahoma", 1, 11));
                                this.rdo.setColor(new Color(0, 0, 0));
                                this.rdo.drawString("If your clan " + this.xt.clan + " engages & defeats " + this.conclan[this.eng] + " in a war:", 27, 70 - this.scro);
                                this.rdo.setFont(new Font("Tahoma", 0, 11));
                                string = "s";
                                if (n12 == 1) {
                                    string = "";
                                }
                                this.rdo.drawString("- Your clan will get:  [ " + n12 + " point" + string + " ]", 47, 87 - this.scro);
                                string = "s";
                                if (n10 == 1) {
                                    string = "";
                                }
                                this.rdo.drawString("- " + this.conclan[this.eng] + " will lose:  [ " + n10 + " point" + string + " ]", 47, 104 - this.scro);
                                if (n12 + n3 > 3 && n12 + n3 > this.totp[this.ord[0]] && !this.xt.clan.toLowerCase().equals(this.conclan[this.ord[0]].toLowerCase())) {
                                    if (this.frkl) {
                                        this.rdo.setColor(new Color(0, 0, 0));
                                        this.frkl = false;
                                    } else {
                                        this.rdo.setColor(new Color(106, 80, 0));
                                        this.frkl = true;
                                    }
                                    if (this.champ >= 0) {
                                        this.rdo.drawString("- Your clan would take the championship title from " + this.conclan[this.champ] + " !", 47, 121 - this.scro);
                                    } else {
                                        this.rdo.drawString("- Your clan would take the champion ship title!", 42, 121 - this.scro);
                                    }
                                    this.ados += 17;
                                }
                                this.rdo.setColor(new Color(0, 0, 0));
                                n12 = n3 + 1;
                                n10 = this.totp[this.eng] + 1;
                                if (n10 > n3) {
                                    n10 = n3;
                                }
                                for (int i = 0; i < this.nvc[this.eng]; ++i) {
                                    if (!this.xt.clan.toLowerCase().equals(this.verclan[this.eng][i].toLowerCase())) continue;
                                    if ((n12 -= this.points[this.eng][i]) >= 0) break;
                                    n12 = 0;
                                    break;
                                }
                                this.rdo.setFont(new Font("Tahoma", 1, 11));
                                this.rdo.setColor(new Color(0, 0, 0));
                                this.rdo.drawString("If your clan loses a war against " + this.conclan[this.eng] + ":", 27, 12 + this.ados - this.scro);
                                this.rdo.setFont(new Font("Tahoma", 0, 11));
                                string = "s";
                                if (n10 == 1) {
                                    string = "";
                                }
                                this.rdo.drawString("- Your clan will lose:  [ " + n10 + " point" + string + " ]", 47, 29 + this.ados - this.scro);
                                string = "s";
                                if (n12 == 1) {
                                    string = "";
                                }
                                this.rdo.drawString("- " + this.conclan[this.eng] + " will get:  [ " + n12 + " point" + string + " ]", 47, 46 + this.ados - this.scro);
                            }
                            this.rdo.setFont(new Font("Tahoma", 1, 11));
                            this.rdo.setColor(new Color(0, 0, 0));
                            this.rdo.drawString("" + this.conclan[this.eng] + " has attained its points from clan wars:", 27, 70 + this.ados - this.scro);
                            this.rdo.setFont(new Font("Tahoma", 0, 11));
                            for (n4 = 0; n4 < this.nvc[this.eng]; ++n4) {
                                string = "s";
                                if (this.points[this.eng][n4] == 1) {
                                    string = "";
                                }
                                this.rdo.drawString("Versus clan " + this.verclan[this.eng][n4] + ":  [ " + this.points[this.eng][n4] + " point" + string + " ]", 47, 87 + this.ados + n4 * 17 - this.scro);
                            }
                            this.rdo.setFont(new Font("Tahoma", 1, 11));
                            this.rdo.setColor(new Color(0, 0, 0));
                            this.rdo.drawString("Total attained points:  [ " + this.totp[this.eng] + " ]", 47, 87 + this.ados + this.nvc[this.eng] * 17 - this.scro);
                        } else {
                            this.engo = Math.abs(this.engo - 15) < 20 ? 15 : (this.engo < 15 ? (this.engo += 20) : (this.engo -= 20));
                        }
                        if (!this.drawl(this.rdo, "#" + this.conclan[this.eng] + "#", 21, this.engo - this.scro, true)) {
                            this.rdo.setColor(new Color(100, 77, 31));
                            this.rdo.drawRect(21, this.engo - this.scro, 349, 29);
                            this.rdo.setFont(new Font("Arial", 1, 13));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.drawString(this.conclan[this.champ], 196 - this.ftm.stringWidth(this.conclan[this.champ]) / 2, this.engo + 19 - this.scro);
                        }
                        if (this.engo == 15) {
                            this.darker = true;
                            if (!this.xt.clan.equals("") && !this.xt.clan.toLowerCase().equals(this.conclan[this.eng].toLowerCase()) && this.stringbutton(this.rdo, "   Declare War  >   ", 459, 176, 1, n, n2, bl, 216, 139)) {
                                this.tab = 2;
                                this.itab = 1;
                                this.litab = -1;
                                this.openi = 10;
                                this.viewgame2 = 0;
                                if (!this.intclan.equals(this.conclan[this.eng])) {
                                    this.intclan = this.conclan[this.eng];
                                    this.dispi = 0;
                                    this.nil = 0;
                                    this.lastint = "";
                                    this.readint = 1;
                                }
                                this.redif = true;
                                this.intsel = 4;
                                this.eng = -1;
                                this.spos6 = this.lspos6w;
                            }
                            if (this.stringbutton(this.rdo, "   <  Back   ", 480, 110, 1, n, n2, bl, 216, 139)) {
                                this.eng = -1;
                                this.spos6 = this.lspos6w;
                            }
                            this.darker = false;
                        }
                    }
                } else {
                    this.sdist = 0;
                }
                if (this.loadwstat == 0) {
                    this.rdo.setFont(new Font("Tahoma", 1, 11));
                    this.ftm = this.rdo.getFontMetrics();
                    this.rdo.setColor(new Color(0, 0, 0));
                    this.rdo.drawString("Loading championship, please wait...", 280 - this.ftm.stringWidth("Loading championship, please wait...") / 2, 140);
                }
                if (this.loadwstat == -1) {
                    this.rdo.setFont(new Font("Tahoma", 1, 11));
                    this.ftm = this.rdo.getFontMetrics();
                    this.rdo.setColor(new Color(0, 0, 0));
                    this.rdo.drawString("Failed to load championship, please try again later...", 280 - this.ftm.stringWidth("Failed to load championship, please try again later...") / 2, 140);
                }
            }
            if (this.ntab == 2) {
                this.rdo.setFont(new Font("Tahoma", 1, 11));
                this.ftm = this.rdo.getFontMetrics();
                this.rdo.setColor(new Color(0, 0, 0));
                this.rdo.drawString("About the Championship", 280 - this.ftm.stringWidth("About the Championship") / 2, 40);
                this.rdo.setFont(new Font("Tahoma", 0, 11));
                this.rdo.drawString("The clan wars world championship is ongoing championship that does not end!", 7, 70);
                this.rdo.drawString("Every clan always has a chance to claim & re-claim the championship title from the current winner.", 7, 85);
                this.rdo.drawString("The champion clan is the clan that is at the current moment attaining the most points.", 7, 115);
                this.rdo.drawString("Clans get points by defeating other clans in wars, but not every clan you defeat earns you the same amount of", 7, 130);
                this.rdo.drawString("points. It depends on how much points that clan has and from which wars where these points attained.", 7, 145);
                this.rdo.drawString("The points system is designed to deliver a fair & balanced championship that is also fun, exiting and never ending!", 7, 160);
                this.rdo.setFont(new Font("Tahoma", 1, 11));
                this.rdo.drawString("Currently there are no rewards in the game for claiming or re-claiming the championship title, but", 7, 190);
                this.rdo.drawString("in the coming updates there will be rewards that can be used to 'super power' clan cars!", 7, 205);
                this.rdo.setFont(new Font("Tahoma", 0, 11));
                this.rdo.drawString("Stay tuned for the rewards system to come!", 7, 220);
                this.rdo.setFont(new Font("Tahoma", 1, 11));
                this.ftm = this.rdo.getFontMetrics();
                this.rdo.drawString("Good Luck!", 280 - this.ftm.stringWidth("Good Luck!") / 2, 250);
                this.darker = true;
                if (this.stringbutton(this.rdo, "   <  Back   ", 280, 280, 1, n, n2, bl, 216, 139)) {
                    this.ntab = 1;
                }
                this.darker = false;
            }
            if (this.sdist != 0) {
                int n13 = 27;
                this.rdo.setColor(this.color2k(200, 200, 200));
                this.rdo.fillRect(540, 20, 17, 260);
                if (this.mscro5 == 831) {
                    this.rdo.setColor(this.color2k(215, 215, 215));
                    this.rdo.fillRect(540, 3, 17, 17);
                } else {
                    this.rdo.setColor(this.color2k(220, 220, 220));
                    this.rdo.fill3DRect(540, 3, 17, 17, true);
                }
                this.rdo.drawImage(this.xt.asu, 545, 9, null);
                if (this.mscro5 == 832) {
                    this.rdo.setColor(this.color2k(215, 215, 215));
                    this.rdo.fillRect(540, 280, 17, 17);
                } else {
                    this.rdo.setColor(this.color2k(220, 220, 220));
                    this.rdo.fill3DRect(540, 280, 17, 17, true);
                }
                this.rdo.drawImage(this.xt.asd, 545, 287, null);
                if (this.lspos6 != this.spos6) {
                    this.rdo.setColor(this.color2k(215, 215, 215));
                    this.rdo.fillRect(540, 20 + this.spos6, 17, 31);
                } else {
                    if (this.mscro5 == 831) {
                        this.rdo.setColor(this.color2k(215, 215, 215));
                    }
                    this.rdo.fill3DRect(540, 20 + this.spos6, 17, 31, true);
                }
                this.rdo.setColor(this.color2k(150, 150, 150));
                this.rdo.drawLine(545, 33 + this.spos6, 551, 33 + this.spos6);
                this.rdo.drawLine(545, 35 + this.spos6, 551, 35 + this.spos6);
                this.rdo.drawLine(545, 37 + this.spos6, 551, 37 + this.spos6);
                if (this.mscro5 > 800 && this.lspos6 != this.spos6) {
                    this.lspos6 = this.spos6;
                }
                if (bl) {
                    if (this.mscro5 == 825 && n > 756 && n < 773 && n2 > 132 + n13 + this.spos6 && n2 < this.spos6 + n13 + 163) {
                        this.mscro5 = n2 - this.spos6;
                    }
                    if (this.mscro5 == 825 && n > 754 && n < 775 && n2 > 26 + n13 && n2 < 134 + n13) {
                        this.mscro5 = 831;
                    }
                    if (this.mscro5 == 825 && n > 754 && n < 775 && n2 > 390 + n13 && n2 < 411 + n13) {
                        this.mscro5 = 832;
                    }
                    if (this.mscro5 == 825 && n > 756 && n < 773 && n2 > 132 + n13 && n2 < 392 + n13) {
                        this.mscro5 = 152 + n13;
                        this.spos6 = n2 - this.mscro5;
                    }
                    if ((n4 = 2670 / this.sdist) < 1) {
                        n4 = 1;
                    }
                    if (this.mscro5 == 831) {
                        this.spos6 -= n4;
                        if (this.spos6 > 229) {
                            this.spos6 = 229;
                        }
                        if (this.spos6 < 0) {
                            this.spos6 = 0;
                        }
                        this.lspos6 = this.spos6;
                    }
                    if (this.mscro5 == 832) {
                        this.spos6 += n4;
                        if (this.spos6 > 229) {
                            this.spos6 = 229;
                        }
                        if (this.spos6 < 0) {
                            this.spos6 = 0;
                        }
                        this.lspos6 = this.spos6;
                    }
                    if (this.mscro5 < 800) {
                        this.spos6 = n2 - this.mscro5;
                        if (this.spos6 > 229) {
                            this.spos6 = 229;
                        }
                        if (this.spos6 < 0) {
                            this.spos6 = 0;
                        }
                    }
                    if (this.mscro5 == 825) {
                        this.mscro5 = 925;
                    }
                } else if (this.mscro5 != 825) {
                    this.mscro5 = 825;
                }
            }
            this.rd.drawImage(this.gImage, 216, 139, null);
        }
        if (this.cfase == 1) {
            if (this.xt.logged) {
                if (this.xt.clan.equals("")) {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 0.4f));
                    this.rd.setColor(new Color(255, 255, 255));
                    this.rd.fillRoundRect(232, 90, 527, 176, 20, 20);
                    this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(232, 90, 527, 176, 20, 20);
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    if (this.flko % 4 != 0 || this.flko == 0) {
                        this.rd.drawString(this.msg, 495 - this.ftm.stringWidth(this.msg) / 2, 120);
                    }
                    if (this.flko != 0) {
                        --this.flko;
                    }
                    this.rd.drawString("Clan name :", 462 - this.ftm.stringWidth("Clan name :"), 156);
                    this.donewc = true;
                    if (this.em != 1) {
                        if (this.stringbutton(this.rd, "     Create     ", 495, 204, 0, n, n2, bl, 0, 0)) {
                            if (!this.gs.temail.getText().equals("")) {
                                this.em = 1;
                            } else {
                                this.msg = "Please enter a clan name!";
                                this.flko = 45;
                            }
                        }
                        if (this.stringbutton(this.rd, " Cancel ", 495, 244, 2, n, n2, bl, 0, 0)) {
                            this.cfase = 0;
                        }
                    } else {
                        this.rd.drawString("Creating, please wait...", 495 - this.ftm.stringWidth("Creating, please wait...") / 2, 224);
                    }
                } else {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 0.4f));
                    this.rd.setColor(new Color(255, 255, 255));
                    this.rd.fillRoundRect(232, 90, 527, 136, 20, 20);
                    this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(232, 90, 527, 136, 20, 20);
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("You are already a member of a clan (" + this.xt.clan + ").", 495 - this.ftm.stringWidth("You are already a member of a clan (" + this.xt.clan + ").") / 2, 120);
                    this.rd.drawString("You need to leave your clan first in order to create a new one.", 495 - this.ftm.stringWidth("You need to leave your clan first in order to create a new one.") / 2, 140);
                    if (this.stringbutton(this.rd, "   OK   ", 495, 204, 0, n, n2, bl, 0, 0)) {
                        this.cfase = 0;
                    }
                }
            } else {
                this.rd.setComposite(AlphaComposite.getInstance(3, 0.4f));
                this.rd.setColor(new Color(255, 255, 255));
                this.rd.fillRoundRect(232, 90, 527, 176, 20, 20);
                this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawRoundRect(232, 90, 527, 176, 20, 20);
                this.rd.setFont(new Font("Arial", 1, 13));
                this.ftm = this.rd.getFontMetrics();
                this.rd.drawString("You are currently using a trial account.", 495 - this.ftm.stringWidth("You are currently using a trial account.") / 2, 120);
                this.rd.drawString("You need to upgrade to be able participate in any NFM clan activity.", 495 - this.ftm.stringWidth("You need to upgrade to be able participate in any NFM clan activity.") / 2, 140);
                this.rd.setColor(new Color(206, 171, 98));
                this.rd.fillRoundRect(405, 163, 180, 50, 20, 20);
                if (this.drawbutton(this.xt.upgrade, 495, 188, n, n2, bl)) {
                    this.gs.editlink(this.xt.nickname, true);
                }
                if (this.stringbutton(this.rd, " Cancel ", 495, 244, 2, n, n2, bl, 0, 0)) {
                    this.cfase = 0;
                }
            }
        }
        if (this.cfase == 2) {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            if (this.flko % 4 != 0 || this.flko == 0) {
                this.rd.drawString(this.msg, 495 - this.ftm.stringWidth(this.msg) / 2, 60);
            }
            if (this.flko != 0) {
                --this.flko;
            }
            this.rd.setComposite(AlphaComposite.getInstance(3, 0.2f));
            this.rd.setColor(new Color(255, 255, 255));
            this.rd.fillRoundRect(197, 73, 597, 371, 20, 20);
            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawRoundRect(197, 73, 597, 371, 20, 20);
            this.dosrch = true;
            this.darker = true;
            if (this.stringbutton(this.rd, "   Search   ", 579, 104, 2, n, n2, bl, 0, 0)) {
                if (!this.gs.temail.getText().equals("")) {
                    this.em = 2;
                    this.smsg = "Searching for '" + this.gs.temail.getText() + "' in clans...";
                    this.nclns = 0;
                    this.spos5 = 0;
                    this.lspos5 = 0;
                } else {
                    this.msg = "Please enter a search phrase!";
                    this.flko = 45;
                }
            }
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.setFont(new Font("Arial", 1, 13));
            this.rd.drawString(this.smsg, 218, 135);
            if (this.stringbutton(this.rd, " <   Back to Main  ", 725, 60, 2, n, n2, bl, 0, 0)) {
                this.cfase = 0;
            }
            this.darker = false;
            this.rdo.setColor(new Color(206, 171, 98));
            this.rdo.fillRect(0, 0, 560, 300);
            this.sdist = (int)(((float)this.nclns - 4.5f) * 55.0f);
            if (this.sdist < 0 || this.nclns == 0) {
                this.sdist = 0;
            }
            this.scro = (int)((float)this.spos5 / 229.0f * (float)this.sdist);
            this.rdo.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            for (n9 = 0; n9 < this.nclns; ++n9) {
                if (60 + n9 * 55 - this.scro <= 0 || 20 + n9 * 55 - this.scro >= 300) continue;
                n8 = 1;
                if (n > 308 && n < 683 && n2 > 159 + n9 * 55 - this.scro && n2 < 199 + n9 * 55 - this.scro) {
                    this.cur = 12;
                    n8 = 0;
                    if (bl) {
                        if (!this.claname.equals(this.clanlo[n9])) {
                            this.claname = this.clanlo[n9];
                            this.loadedc = false;
                        }
                        this.spos5 = 0;
                        this.lspos5 = 0;
                        this.cfase = 3;
                        this.ctab = 0;
                    }
                }
                this.rdo.setColor(new Color(0, 0, 0));
                this.rdo.drawRoundRect(92, 20 + n9 * 55 - this.scro, 375, 40, 20, 20);
                if (this.drawl(this.rdo, "#" + this.clanlo[n9] + "#", 105, 25 + n9 * 55 - this.scro, n8 != 0) && n8 != 0) continue;
                this.rdo.drawString("" + this.clanlo[n9] + "", 280 - this.ftm.stringWidth("" + this.clanlo[n9] + "") / 2, 45 + n9 * 55 - this.scro);
            }
            if (this.sdist != 0) {
                n9 = 27;
                this.rdo.setColor(this.color2k(200, 200, 200));
                this.rdo.fillRect(540, 20, 17, 260);
                if (this.mscro5 == 831) {
                    this.rdo.setColor(this.color2k(215, 215, 215));
                    this.rdo.fillRect(540, 3, 17, 17);
                } else {
                    this.rdo.setColor(this.color2k(220, 220, 220));
                    this.rdo.fill3DRect(540, 3, 17, 17, true);
                }
                this.rdo.drawImage(this.xt.asu, 545, 9, null);
                if (this.mscro5 == 832) {
                    this.rdo.setColor(this.color2k(215, 215, 215));
                    this.rdo.fillRect(540, 280, 17, 17);
                } else {
                    this.rdo.setColor(this.color2k(220, 220, 220));
                    this.rdo.fill3DRect(540, 280, 17, 17, true);
                }
                this.rdo.drawImage(this.xt.asd, 545, 287, null);
                if (this.lspos5 != this.spos5) {
                    this.rdo.setColor(this.color2k(215, 215, 215));
                    this.rdo.fillRect(540, 20 + this.spos5, 17, 31);
                } else {
                    if (this.mscro5 == 831) {
                        this.rdo.setColor(this.color2k(215, 215, 215));
                    }
                    this.rdo.fill3DRect(540, 20 + this.spos5, 17, 31, true);
                }
                this.rdo.setColor(this.color2k(150, 150, 150));
                this.rdo.drawLine(545, 33 + this.spos5, 551, 33 + this.spos5);
                this.rdo.drawLine(545, 35 + this.spos5, 551, 35 + this.spos5);
                this.rdo.drawLine(545, 37 + this.spos5, 551, 37 + this.spos5);
                if (this.mscro5 > 800 && this.lspos5 != this.spos5) {
                    this.lspos5 = this.spos5;
                }
                if (bl) {
                    if (this.mscro5 == 825 && n > 756 && n < 773 && n2 > 132 + n9 + this.spos5 && n2 < this.spos5 + n9 + 163) {
                        this.mscro5 = n2 - this.spos5;
                    }
                    if (this.mscro5 == 825 && n > 754 && n < 775 && n2 > 26 + n9 && n2 < 134 + n9) {
                        this.mscro5 = 831;
                    }
                    if (this.mscro5 == 825 && n > 754 && n < 775 && n2 > 390 + n9 && n2 < 411 + n9) {
                        this.mscro5 = 832;
                    }
                    if (this.mscro5 == 825 && n > 756 && n < 773 && n2 > 132 + n9 && n2 < 392 + n9) {
                        this.mscro5 = 152 + n9;
                        this.spos5 = n2 - this.mscro5;
                    }
                    if ((n8 = 2670 / this.sdist) < 1) {
                        n8 = 1;
                    }
                    if (this.mscro5 == 831) {
                        this.spos5 -= n8;
                        if (this.spos5 > 229) {
                            this.spos5 = 229;
                        }
                        if (this.spos5 < 0) {
                            this.spos5 = 0;
                        }
                        this.lspos5 = this.spos5;
                    }
                    if (this.mscro5 == 832) {
                        this.spos5 += n8;
                        if (this.spos5 > 229) {
                            this.spos5 = 229;
                        }
                        if (this.spos5 < 0) {
                            this.spos5 = 0;
                        }
                        this.lspos5 = this.spos5;
                    }
                    if (this.mscro5 < 800) {
                        this.spos5 = n2 - this.mscro5;
                        if (this.spos5 > 229) {
                            this.spos5 = 229;
                        }
                        if (this.spos5 < 0) {
                            this.spos5 = 0;
                        }
                    }
                    if (this.mscro5 == 825) {
                        this.mscro5 = 925;
                    }
                } else if (this.mscro5 != 825) {
                    this.mscro5 = 825;
                }
            }
            this.rd.drawImage(this.gImage, 216, 139, null);
        }
        if (this.cfase == 3) {
            this.rd.setComposite(AlphaComposite.getInstance(3, 0.4f));
            this.rd.setColor(new Color(255, 255, 255));
            this.rd.fillRoundRect(197, 40, 597, 404, 20, 20);
            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawRoundRect(197, 40, 597, 404, 20, 20);
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            if (this.loadedc) {
                Object object;
                n9 = -1;
                for (n8 = 0; n8 < this.nmb; ++n8) {
                    if (!this.member[n8].toLowerCase().equals(this.xt.nickname.toLowerCase()) || this.mlevel[n8] != 7 && n8 != 0) continue;
                    n9 = n8;
                }
                n8 = 0;
                if (n > 197 && n < 563 && n2 > 40 && n2 < 83 && this.editc == 0) {
                    n8 = 1;
                }
                if ((n7 = (int)(this.drawl(this.rd, "#" + this.claname + "#", 206, 47, n8 == 0) ? 1 : 0)) == 0 || n8 != 0) {
                    this.rd.drawString("Clan :  " + this.claname + "", 381 - this.ftm.stringWidth("Clan :  " + this.claname + "") / 2, 67);
                    if (n9 != -1) {
                        this.rd.setFont(new Font("Arial", 1, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Edit Logo", 505, 74);
                        this.rd.drawLine(505, 76, 505 + this.ftm.stringWidth("Edit Logo"), 76);
                        if (n > 505 && n < 505 + this.ftm.stringWidth("Edit Logo") && n2 > 62 && n2 < 77 && this.editc == 0) {
                            this.cur = 12;
                            if (bl) {
                                this.editc = 1;
                                this.msg = "Edit Clan's Logo Image";
                                this.flko = 0;
                            }
                        }
                    }
                }
                this.rd.drawLine(563, 40, 563, 83);
                this.rd.drawLine(563, 83, 197, 83);
                if (this.stringbutton(this.rd, "   <   Back to Main  ", 688, 69, 1, n, n2, bl, 0, 0)) {
                    this.cfase = 0;
                }
                this.rd.setFont(new Font("Arial", 1, 13));
                this.ftm = this.rd.getFontMetrics();
                n6 = 220;
                n5 = this.ftm.stringWidth("Members");
                if (this.ctab != 0) {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 0.6f));
                }
                this.rd.drawString("Members", n6, 107);
                if (this.ctab != 0) {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                }
                if (n > n6 && n < n6 + n5 && n2 > 90 && n2 < 109 && this.editc == 0) {
                    this.rd.drawLine(n6, 109, n6 + n5, 109);
                    this.cur = 12;
                    if (bl) {
                        this.ctab = 0;
                    }
                }
                n6 += n5 + 20;
                n5 = this.ftm.stringWidth("Cars");
                if (this.ctab != 2) {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 0.6f));
                }
                this.rd.drawString("Cars", n6, 107);
                if (this.ctab != 2) {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                }
                if (n > n6 && n < n6 + n5 && n2 > 90 && n2 < 109 && this.editc == 0 && !this.notclan) {
                    this.rd.drawLine(n6, 109, n6 + n5, 109);
                    this.cur = 12;
                    if (bl) {
                        this.ctab = 2;
                        this.loadedcars = -1;
                    }
                }
                n6 += n5 + 20;
                n5 = this.ftm.stringWidth("Stages");
                if (this.ctab != 3) {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 0.6f));
                }
                this.rd.drawString("Stages", n6, 107);
                if (this.ctab != 3) {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                }
                if (n > n6 && n < n6 + n5 && n2 > 90 && n2 < 109 && this.editc == 0 && !this.notclan) {
                    this.rd.drawLine(n6, 109, n6 + n5, 109);
                    this.cur = 12;
                    if (bl) {
                        this.ctab = 3;
                        this.loadedstages = -1;
                    }
                }
                n6 += n5 + 20;
                n5 = this.ftm.stringWidth("Interact");
                if (this.ctab != 1) {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 0.6f));
                }
                this.rd.drawString("Interact", n6, 107);
                if (this.ctab != 1) {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                }
                if (n > n6 && n < n6 + n5 && n2 > 90 && n2 < 109 && this.editc == 0 && !this.notclan) {
                    this.rd.drawLine(n6, 109, n6 + n5, 109);
                    this.cur = 12;
                    if (bl) {
                        this.ctab = 1;
                    }
                }
                n6 += n5 + 20;
                n5 = this.ftm.stringWidth("Web Presence");
                if (this.ctab != 4) {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 0.6f));
                }
                this.rd.drawString("Web Presence", n6, 107);
                if (this.ctab != 4) {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                }
                if (n > n6 && n < n6 + n5 && n2 > 90 && n2 < 109 && this.editc == 0 && !this.notclan) {
                    this.rd.drawLine(n6, 109, n6 + n5, 109);
                    this.cur = 12;
                    if (bl) {
                        this.ctab = 4;
                        this.loadedlink = false;
                    }
                }
                this.rdo.setColor(new Color(206, 171, 98));
                this.rdo.fillRect(0, 0, 560, 300);
                if (this.clanbgl) {
                    this.rdo.drawImage(this.clanbg, 0, 0, null);
                }
                if (this.notclan) {
                    this.rdo.setColor(new Color(0, 0, 0));
                    this.rdo.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rdo.getFontMetrics();
                    this.rdo.drawString("[  Clan Removed  ]", 280 - this.ftm.stringWidth("[  Clan Removed  ]") / 2, 40);
                    this.rdo.drawString("This clan has been abandoned by its members and no longer exists...", 280 - this.ftm.stringWidth("This clan has been abandoned by its members and no longer exists...") / 2, 140);
                }
                if (n9 != -1 && n > 216 && n < 776 && n2 > 92 && n2 < 412 && this.editc == 0) {
                    this.rd.setFont(new Font("Arial", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("Edit Background", 688, 107);
                    this.rd.drawLine(688, 109, 688 + this.ftm.stringWidth("Edit Background"), 109);
                    if (n > 688 && n < 688 + this.ftm.stringWidth("Edit Background") && n2 > 95 && n2 < 110 && this.editc == 0) {
                        this.cur = 12;
                        if (bl) {
                            this.editc = 2;
                            this.msg = "Edit Clan's Background Display Image";
                            this.flko = 0;
                        }
                    }
                }
                if (this.ctab == 0) {
                    if (!this.showreqs) {
                        this.sdist = (int)(((float)this.nmb - 4.0f) * 55.0f);
                        if (this.sdist < 0 || this.nmb < 5) {
                            this.sdist = 0;
                        }
                    } else {
                        this.sdist = (int)(((float)this.nrmb - 4.0f) * 40.0f);
                        if (this.sdist < 0) {
                            this.sdist = 0;
                        }
                    }
                }
                if (this.ctab == 1 || this.ctab == 2 || this.ctab == 3 || this.ctab == 4) {
                    this.sdist = 0;
                    if (this.sdist < 0) {
                        this.sdist = 0;
                    }
                }
                this.scro = (int)((float)this.spos5 / 229.0f * (float)this.sdist);
                if (this.ctab == 0) {
                    if (!this.showreqs) {
                        int n14 = 0;
                        for (n4 = 0; n4 < this.nmb; ++n4) {
                            int n15;
                            n3 = 0;
                            for (n15 = 0; n15 < this.npo; ++n15) {
                                if (!this.member[n4].toLowerCase().equals(this.pname[n15].toLowerCase())) continue;
                                n3 = 1;
                                ++n14;
                            }
                            if (60 + n4 * 55 - this.scro <= 0 || 20 + n4 * 55 - this.scro >= 300) continue;
                            n15 = 0;
                            float f = 0.5f;
                            if (n > 266 && n < 726 && n2 > 132 + n4 * 55 - this.scro && n2 < 172 + n4 * 55 - this.scro && this.editc == 0) {
                                f = 0.8f;
                                if (n4 >= n9 && n9 != -1) {
                                    n15 = 1;
                                }
                                if (this.xt.nickname.toLowerCase().equals(this.member[n4].toLowerCase()) && n15 == 0) {
                                    n15 = 2;
                                }
                            }
                            this.rdo.setComposite(AlphaComposite.getInstance(3, f));
                            this.rdo.setColor(new Color(255, 255, 255));
                            this.rdo.fillRoundRect(50, 20 + n4 * 55 - this.scro, 460, 40, 20, 20);
                            this.rdo.setColor(new Color(0, 0, 0));
                            if (n3 != 0) {
                                this.rdo.setColor(new Color(123, 200, 0));
                            }
                            this.rdo.drawRoundRect(50, 20 + n4 * 55 - this.scro, 460, 40, 20, 20);
                            this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                            boolean bl3 = false;
                            boolean bl4 = false;
                            if (n > 276 && n < 396 && n2 > 137 + n4 * 55 - this.scro && n2 < 167 + n4 * 55 - this.scro && n2 > 112 && n2 < 412 && this.editc == 0) {
                                bl4 = true;
                                this.cur = 12;
                                if (bl) {
                                    this.tab = 1;
                                    if (!this.proname.equals(this.member[n4])) {
                                        this.proname = this.member[n4];
                                        this.loadedp = false;
                                        this.onexitpro();
                                    }
                                }
                            }
                            if (!bl4) {
                                bl3 = this.drawl(this.rdo, this.member[n4], 60, 25 + n4 * 55 - this.scro, true);
                            } else {
                                this.drawl(this.rdo, this.member[n4], 60, 25 + n4 * 55 - this.scro, false);
                            }
                            if (!bl3) {
                                this.rdo.setComposite(AlphaComposite.getInstance(3, 0.5f));
                                this.rdo.setColor(new Color(255, 255, 255));
                                this.rdo.fillRect(60, 25 + n4 * 55 - this.scro, 119, 29);
                                this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                                this.rdo.setColor(new Color(0, 0, 0));
                                this.rdo.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rdo.getFontMetrics();
                                this.rdo.drawRect(60, 25 + n4 * 55 - this.scro, 119, 29);
                                this.rdo.drawString(this.member[n4], 120 - this.ftm.stringWidth(this.member[n4]) / 2, 44 + n4 * 55 - this.scro);
                            }
                            this.rdo.setColor(new Color(0, 0, 0));
                            this.rdo.setFont(new Font("Arial", 1, 11));
                            this.ftm = this.rdo.getFontMetrics();
                            String string = "";
                            if (n4 == 0) {
                                string = "  ( Clan Leader / Admin )";
                            } else if (this.mlevel[n4] == 7) {
                                string = "  ( Admin )";
                            }
                            this.rdo.drawString("Level :  " + this.mlevel[n4] + "" + string + "", 190, 36 + n4 * 55 - this.scro);
                            this.rdo.drawString("Rank :  " + this.mrank[n4] + "", 190, 52 + n4 * 55 - this.scro);
                            if (n15 != 0) {
                                if (n15 == 1) {
                                    this.rdo.drawString("Edit", 424, 36 + n4 * 55 - this.scro);
                                    this.rdo.drawLine(424, 38 + n4 * 55 - this.scro, 424 + this.ftm.stringWidth("Edit"), 38 + n4 * 55 - this.scro);
                                    if (n > 640 && n < 640 + this.ftm.stringWidth("Edit") && n2 > 136 + n4 * 55 - this.scro && n2 < 151 + n4 * 55 - this.scro && this.editc == 0) {
                                        this.cur = 12;
                                        if (bl) {
                                            this.em = n4;
                                            this.editc = 3;
                                        }
                                    }
                                }
                                object = "Remove";
                                if (this.xt.nickname.toLowerCase().equals(this.member[n4].toLowerCase())) {
                                    object = "Leave";
                                }
                                this.rdo.drawString((String)object, 454, 36 + n4 * 55 - this.scro);
                                this.rdo.drawLine(454, 38 + n4 * 55 - this.scro, 454 + this.ftm.stringWidth((String)object), 38 + n4 * 55 - this.scro);
                                if (n <= 670 || n >= 670 + this.ftm.stringWidth((String)object) || n2 <= 136 + n4 * 55 - this.scro || n2 >= 151 + n4 * 55 - this.scro || this.editc != 0) continue;
                                this.cur = 12;
                                if (!bl) continue;
                                this.em = n4;
                                this.editc = 4;
                                continue;
                            }
                            if (n3 == 0) continue;
                            this.rdo.setColor(new Color(49, 79, 0));
                            this.rdo.drawString("Online", 454, 36 + n4 * 55 - this.scro);
                        }
                        if (this.nmb == 1 && n9 != -1) {
                            float f = 0.5f;
                            if (n > 266 && n < 726 && n2 > 187 - this.scro && n2 < 287 - this.scro && this.editc == 0) {
                                f = 0.8f;
                            }
                            this.rdo.setComposite(AlphaComposite.getInstance(3, f));
                            this.rdo.setColor(new Color(255, 255, 255));
                            this.rdo.fillRoundRect(50, 75 - this.scro, 460, 100, 20, 20);
                            this.rdo.setColor(new Color(0, 0, 0));
                            this.rdo.drawRoundRect(50, 75 - this.scro, 460, 100, 20, 20);
                            this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                            this.rdo.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.drawString("Welcome to your clan " + this.claname + "!", 60, 91 - this.scro);
                            this.rdo.drawString("Now you will need to invite other players to join this clan.", 60, 121 - this.scro);
                            this.rdo.drawString("To invite, visit a player's profile and in the clan area click 'Invite to Join...'.", 60, 136 - this.scro);
                            this.rdo.drawString("(That is if the player is not already a member of another clan).", 60, 151 - this.scro);
                            this.rdo.drawString("The maximum number of members a clan can have is 20.", 60, 166 - this.scro);
                        } else if (this.nmb > 1) {
                            this.rdo.setComposite(AlphaComposite.getInstance(3, 0.8f));
                            this.rdo.setColor(new Color(255, 255, 255));
                            this.rdo.fillRoundRect(212, 20 + this.nmb * 55 - this.scro, 136, 27, 20, 20);
                            this.rdo.setColor(new Color(0, 0, 0));
                            this.rdo.drawRoundRect(212, 20 + this.nmb * 55 - this.scro, 136, 27, 20, 20);
                            this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                            this.rdo.setFont(new Font("Arial", 1, 11));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.drawString("" + n14 + " player(s) online", 280 - this.ftm.stringWidth("" + n14 + " player(s) online") / 2, 37 + this.nmb * 55 - this.scro);
                        }
                    } else {
                        this.rdo.setComposite(AlphaComposite.getInstance(3, 0.8f));
                        this.rdo.setColor(new Color(244, 232, 204));
                        this.rdo.fillRoundRect(70, 20 - this.scro, 420, 80 + this.nrmb * 40, 20, 20);
                        this.rdo.setColor(new Color(0, 0, 0));
                        this.rdo.drawRoundRect(70, 20 - this.scro, 420, 80 + this.nrmb * 40, 20, 20);
                        this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                        this.rdo.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rdo.getFontMetrics();
                        this.rdo.drawString("Membership Requests", 280 - this.ftm.stringWidth("Membership Requests") / 2, 45 - this.scro);
                        this.darker = true;
                        for (int i = 0; i < this.nrmb; ++i) {
                            n4 = 0;
                            n3 = 0;
                            if (n > 356 && n < 476 && n2 > 172 + i * 40 - this.scro && n2 < 202 + i * 55 - this.scro && n2 > 112 && n2 < 412 && this.editc == 0) {
                                n3 = 1;
                                this.cur = 12;
                                if (bl) {
                                    this.tab = 1;
                                    if (!this.proname.equals(this.rmember[i])) {
                                        this.proname = this.rmember[i];
                                        this.loadedp = false;
                                        this.onexitpro();
                                    }
                                }
                            }
                            if (n3 == 0) {
                                n4 = this.drawl(this.rdo, this.rmember[i], 140, 60 + i * 40 - this.scro, true) ? 1 : 0;
                            } else {
                                this.drawl(this.rdo, this.rmember[i], 140, 60 + i * 40 - this.scro, false);
                            }
                            if (n4 == 0) {
                                this.rdo.setComposite(AlphaComposite.getInstance(3, 0.5f));
                                this.rdo.setColor(new Color(255, 255, 255));
                                this.rdo.fillRect(140, 60 + i * 40 - this.scro, 119, 29);
                                this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                                this.rdo.setColor(new Color(0, 0, 0));
                                this.rdo.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rdo.getFontMetrics();
                                this.rdo.drawRect(140, 60 + i * 40 - this.scro, 119, 29);
                                this.rdo.drawString(this.rmember[i], 200 - this.ftm.stringWidth(this.rmember[i]) / 2, 79 + i * 40 - this.scro);
                            }
                            if (this.stringbutton(this.rdo, "  Approve  ", 310, 79 + i * 40 - this.scro, 3, n, n2, bl && this.editc == 0, 216, 112)) {
                                this.em = i;
                                this.editc = 66;
                            }
                            if (!this.stringbutton(this.rdo, "  Deny  ", 391, 79 + i * 40 - this.scro, 3, n, n2, bl && this.editc == 0, 216, 112)) continue;
                            this.em = i;
                            this.editc = 77;
                        }
                        if (this.stringbutton(this.rdo, "    Decide Later    ", 280, 80 + this.nrmb * 40 - this.scro, 1, n, n2, bl && this.editc == 0, 216, 112)) {
                            this.spos5 = 0;
                            this.lspos5 = 0;
                            this.showreqs = false;
                        }
                        this.darker = false;
                    }
                }
                if (this.ctab == 1) {
                    this.rdo.setComposite(AlphaComposite.getInstance(3, 0.25f));
                    this.rdo.setColor(new Color(255, 255, 255));
                    this.rdo.fillRoundRect(20, 246, 520, 39, 20, 20);
                    this.rdo.setColor(new Color(0, 0, 0));
                    this.rdo.drawRoundRect(20, 246, 520, 39, 20, 20);
                    this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    if (this.xt.clan.toLowerCase().equals(this.claname.toLowerCase())) {
                        if (this.stringbutton(this.rdo, "       Open your clan's discussion       ", 280, 270, -1, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.tab = 2;
                            this.itab = 2;
                            this.litab = -1;
                        }
                    } else {
                        if (this.stringbutton(this.rdo, "       Declare war on " + this.claname + "       ", 162, 270, -1, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.tab = 2;
                            this.itab = 1;
                            this.litab = -1;
                            this.openi = 10;
                            this.viewgame2 = 0;
                            if (!this.intclan.equals(this.claname)) {
                                this.intclan = this.claname;
                                this.dispi = 0;
                                this.nil = 0;
                                this.lastint = "";
                                this.readint = 1;
                            }
                            this.redif = true;
                            this.intsel = 4;
                        }
                        if (this.stringbutton(this.rdo, "    Talk with " + this.claname + "    ", 422, 270, -1, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.tab = 2;
                            this.itab = 1;
                            this.litab = -1;
                            this.openi = 10;
                            this.viewgame2 = 0;
                            if (!this.intclan.equals(this.claname)) {
                                this.intclan = this.claname;
                                this.dispi = 0;
                                this.nil = 0;
                                this.lastint = "";
                                this.readint = 1;
                            }
                        }
                    }
                }
                if (this.ctab == 3) {
                    if (this.loadedstages != 1 && this.loadedstages != 5) {
                        this.rdo.setComposite(AlphaComposite.getInstance(3, 0.7f));
                        this.rdo.setColor(new Color(255, 255, 255));
                        this.rdo.fillRoundRect(60, 70, 440, 100, 20, 20);
                        this.rdo.setColor(new Color(0, 0, 0));
                        this.rdo.drawRoundRect(60, 70, 440, 100, 20, 20);
                        this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    }
                    this.rdo.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rdo.getFontMetrics();
                    if (this.loadedstages == -2) {
                        this.rdo.drawString("Failed to load clan stages, connection error, try again later...", 280 - this.ftm.stringWidth("Failed to load clan stages, connection error, try again later...") / 2, 125);
                    }
                    if (this.loadedstages == -1) {
                        this.rdo.drawString("Loading clan stages, please wait...", 280 - this.ftm.stringWidth("Loading clan stages, please wait...") / 2, 125);
                    }
                    if (this.loadedstages == 0) {
                        this.rdo.drawString("No custom clan stages have been added yet!", 280 - this.ftm.stringWidth("No custom clan stages have been added yet!") / 2, 125);
                    }
                    if (this.loadedstages == 2) {
                        this.rdo.drawString("Searching for and loading stages created by you,  " + this.perry + "  ...", 280 - this.ftm.stringWidth("Searching for and loading stages created by you,  " + this.perry + "  ...") / 2, 125);
                    }
                    if (this.loadedstages == 3) {
                        this.rdo.drawString("Found no stages that can be added!", 280 - this.ftm.stringWidth("Found no stages that can be added!") / 2, 95);
                        this.rdo.drawString("Found no stages created by you that also do not already belong to a clan.", 280 - this.ftm.stringWidth("Found no stages created by you that also do not already belong to a clan.") / 2, 115);
                        if (this.stringbutton(this.rdo, " OK ", 280, 155, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedstages = -1;
                        }
                    }
                    if (this.loadedstages == 4) {
                        this.rdo.drawString("Failed to load stages created by you, connection error, try again later...", 280 - this.ftm.stringWidth("Failed to load stages created by you, connection error, try again later...") / 2, 110);
                        if (this.stringbutton(this.rdo, " OK ", 280, 140, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedstages = -1;
                        }
                    }
                    if (this.loadedstages == 6) {
                        this.rdo.drawString("Adding stage to your clan's stages, please wait...", 280 - this.ftm.stringWidth("Adding stage to your clan's stages, please wait...") / 2, 125);
                    }
                    if (this.loadedstages == 7) {
                        this.rdo.drawString("Failed to add stage, server error, try again later...", 280 - this.ftm.stringWidth("Failed to add stage, server error, try again later...") / 2, 110);
                        if (this.stringbutton(this.rdo, " OK ", 280, 140, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedstages = -1;
                        }
                    }
                    if (this.loadedstages == 8) {
                        this.rdo.drawString("Removing stage from your clan's cars, please wait...", 280 - this.ftm.stringWidth("Removing stage from your clan's cars, please wait...") / 2, 125);
                    }
                    if (this.loadedstages == 9) {
                        this.rdo.drawString("Failed to remove stage, server error, try again later...", 280 - this.ftm.stringWidth("Failed to remove stage, server error, try again later...") / 2, 110);
                        if (this.stringbutton(this.rdo, " OK ", 280, 140, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedstages = -1;
                        }
                    }
                    if (this.loadedstages == 10) {
                        this.rdo.drawString("Are you sure you want to remove this stage from your clan's stages?", 280 - this.ftm.stringWidth("Are you sure you want to remove this stage from your clan's stages?") / 2, 93);
                        if (this.stringbutton(this.rdo, "    Yes    ", 280, 128, -2, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedstages = 8;
                        }
                        if (this.stringbutton(this.rdo, "  No  ", 280, 158, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedstages = 1;
                        }
                    }
                    if (this.loadedstages == 5) {
                        this.rdo.setComposite(AlphaComposite.getInstance(3, 0.7f));
                        this.rdo.setColor(new Color(255, 255, 255));
                        this.rdo.fillRoundRect(60, 58, 440, 162, 20, 20);
                        this.rdo.setColor(new Color(0, 0, 0));
                        this.rdo.drawRoundRect(60, 58, 440, 162, 20, 20);
                        this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                        this.rdo.drawString("Select a stage to add.", 280 - this.ftm.stringWidth("Select a stage to add.") / 2, 80);
                        this.gs.clcars.move(496 - this.gs.clcars.getWidth() / 2, 206);
                        if (this.editc == 0) {
                            if (!this.gs.clcars.isShowing()) {
                                this.gs.clcars.show();
                                this.gs.clcars.select(0);
                            }
                        } else {
                            this.gs.clcars.hide();
                        }
                        if (!this.gs.clcars.getSelectedItem().equals(this.selstage)) {
                            this.selstage = this.gs.clcars.getSelectedItem();
                        }
                        if (this.gs.clcars.open) {
                            this.blocknote = 20;
                        }
                        if (this.stringbutton(this.rdo, "     Add Stage     ", 280, 150, -2, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedstages = 6;
                        }
                        if (this.stringbutton(this.rdo, "  Cancel  ", 280, 200, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedstages = -1;
                        }
                    }
                    if (this.loadedstages == 1) {
                        if (this.editc == 0) {
                            if (!this.gs.clcars.isShowing()) {
                                this.gs.clcars.show();
                                this.selstage = this.gs.clcars.getSelectedItem();
                            }
                        } else {
                            this.gs.clcars.hide();
                        }
                        this.gs.clcars.move(496 - this.gs.clcars.getWidth() / 2, 122);
                        if (!this.gs.clcars.getSelectedItem().equals(this.selstage)) {
                            this.selstage = this.gs.clcars.getSelectedItem();
                            this.loadedstage = 0;
                            this.addstage = 0;
                        }
                        if (this.gs.clcars.open) {
                            this.blocknote = 20;
                        }
                        if (!this.selstage.equals("Select Stage")) {
                            if (this.loadedstage > 0) {
                                int n16;
                                this.m.trk = 3;
                                this.m.ih = 0;
                                this.m.iw = 0;
                                this.m.h = 300;
                                this.m.w = 560;
                                this.m.cx = 280;
                                this.m.cy = 150;
                                this.m.aroundtrack(this.cp);
                                this.rdo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                                int n17 = 0;
                                int[] nArray = new int[200];
                                for (n3 = 0; n3 < this.gs.nob; ++n3) {
                                    if (this.co[n3].dist != 0) {
                                        nArray[n17] = n3;
                                        ++n17;
                                        continue;
                                    }
                                    this.co[n3].d(this.rdo);
                                }
                                int[] nArray4 = new int[n17];
                                for (n16 = 0; n16 < n17; ++n16) {
                                    nArray4[n16] = 0;
                                }
                                for (n16 = 0; n16 < n17; ++n16) {
                                    for (int i = n16 + 1; i < n17; ++i) {
                                        if (this.co[nArray[n16]].dist != this.co[nArray[i]].dist) {
                                            if (this.co[nArray[n16]].dist < this.co[nArray[i]].dist) {
                                                int n18 = n16;
                                                nArray4[n18] = nArray4[n18] + 1;
                                                continue;
                                            }
                                            int n19 = i;
                                            nArray4[n19] = nArray4[n19] + 1;
                                            continue;
                                        }
                                        if (i > n16) {
                                            int n20 = n16;
                                            nArray4[n20] = nArray4[n20] + 1;
                                            continue;
                                        }
                                        int n21 = i;
                                        nArray4[n21] = nArray4[n21] + 1;
                                    }
                                }
                                for (n16 = 0; n16 < n17; ++n16) {
                                    for (int i = 0; i < n17; ++i) {
                                        if (nArray4[i] != n16) continue;
                                        this.co[nArray[i]].d(this.rdo);
                                    }
                                }
                                this.rdo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                this.m.trk = 0;
                                this.m.h = 450;
                                this.m.w = 800;
                                this.m.cx = 400;
                                this.m.cy = 225;
                                this.rdo.setComposite(AlphaComposite.getInstance(3, 0.5f));
                                this.rdo.setColor(new Color(255, 255, 255));
                                this.rdo.fillRoundRect(9, 44, 136, 39, 20, 20);
                                this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                                this.rdo.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rdo.getFontMetrics();
                                this.rdo.setColor(new Color(0, 0, 0));
                                this.rdo.drawString("Created/Published by", 17, 59);
                                n16 = 17 + this.ftm.stringWidth("Created/Published by") / 2 - this.ftm.stringWidth(this.cp.maker) / 2;
                                int n22 = n16 + this.ftm.stringWidth(this.cp.maker);
                                this.rdo.drawString(this.cp.maker, n16, 74);
                                this.rdo.drawLine(n16, 76, n22, 76);
                                if (n > n16 + 216 && n < n22 + 216 && n2 > 174 && n2 < 188) {
                                    this.cur = 12;
                                    if (bl) {
                                        this.tab = 1;
                                        if (!this.proname.equals(this.cp.maker)) {
                                            this.proname = this.cp.maker;
                                            this.loadedp = false;
                                            this.onexitpro();
                                        }
                                    }
                                }
                                if ((n9 != -1 || this.cp.maker.toLowerCase().equals(this.xt.nickname.toLowerCase())) && this.stringbutton(this.rdo, " Remove X ", 510, 27, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                                    this.loadedstages = 10;
                                }
                                if (this.gs.clcars.getSelectedIndex() != this.gs.clcars.no - 1 && this.stringbutton(this.rdo, " Next > ", 520, 137, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                                    ++this.gs.clcars.sel;
                                }
                                if (this.gs.clcars.getSelectedIndex() != 1 && this.stringbutton(this.rdo, " < Prev ", 40, 137, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                                    --this.gs.clcars.sel;
                                }
                                if (this.cp.pubt > 0) {
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.setColor(new Color(0, 0, 0));
                                    if (this.addstage == 2) {
                                        this.rd.drawString("Adding Stage...", 496 - this.ftm.stringWidth("Adding Stage...") / 2, 432);
                                        if (this.cd.staction == 0) {
                                            this.addstage = 3;
                                        }
                                        if (this.cd.staction == -2) {
                                            this.addstage = 4;
                                        }
                                        if (this.cd.staction == -3) {
                                            this.addstage = 5;
                                        }
                                        if (this.cd.staction == -1) {
                                            this.addstage = 6;
                                        }
                                    }
                                    if (this.addstage == 3) {
                                        this.rd.drawString("[" + this.cd.onstage + "] has been added to your stages!", 496 - this.ftm.stringWidth("[" + this.cd.onstage + "] has been added to your stages!") / 2, 432);
                                    }
                                    if (this.addstage == 4) {
                                        this.rd.drawString("You already have this stage.", 496 - this.ftm.stringWidth("You already have this stage.") / 2, 432);
                                    }
                                    if (this.addstage == 5) {
                                        this.rd.drawString("Cannot add more then 20 stages to your account!", 496 - this.ftm.stringWidth("Cannot add more then 20 stages to your account!") / 2, 432);
                                    }
                                    if (this.addstage == 6) {
                                        this.rd.drawString("Failed to add stage!  Unknown error, please try again later.", 496 - this.ftm.stringWidth("Failed to add stage!  Unknown error, please try again later.") / 2, 432);
                                    }
                                    if (this.addstage == 1) {
                                        String string = "Upgrade to a full account to add custom stages!";
                                        int n23 = 496 - this.ftm.stringWidth(string) / 2;
                                        int n24 = n23 + this.ftm.stringWidth(string);
                                        this.rd.drawString(string, n23, 432);
                                        if (this.waitlink != -1) {
                                            this.rd.drawLine(n23, 435, n24, 435);
                                        }
                                        if (n > n23 && n < n24 && n2 > 421 && n2 < 435) {
                                            if (this.waitlink != -1) {
                                                this.cur = 12;
                                            }
                                            if (bl && this.waitlink == 0) {
                                                this.gs.editlink(this.xt.nickname, true);
                                                this.waitlink = -1;
                                            }
                                        }
                                        if (this.waitlink > 0) {
                                            --this.waitlink;
                                        }
                                    }
                                    if (this.addstage == 0 && this.xt.drawcarb(true, null, " Add to My Stages ", 437, 414, n, n2, bl && this.blocknote == 0)) {
                                        if (this.xt.logged) {
                                            this.cd.onstage = this.selstage;
                                            this.cd.staction = 2;
                                            this.cd.sparkstageaction();
                                            this.addstage = 2;
                                        } else {
                                            this.addstage = 1;
                                            this.waitlink = 20;
                                        }
                                    }
                                } else {
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawString("Private Stage", 496 - this.ftm.stringWidth("Private Stage") / 2, 432);
                                }
                            }
                            if (this.loadedstage == 0) {
                                this.rdo.setComposite(AlphaComposite.getInstance(3, 0.7f));
                                this.rdo.setColor(new Color(255, 255, 255));
                                this.rdo.fillRoundRect(150, 100, 260, 40, 20, 20);
                                this.rdo.setColor(new Color(0, 0, 0));
                                this.rdo.drawRoundRect(150, 100, 260, 40, 20, 20);
                                this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                                this.rdo.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rdo.getFontMetrics();
                                this.rdo.setColor(new Color(0, 0, 0));
                                this.rdo.drawString("Loading...", 280 - this.ftm.stringWidth("Loading...") / 2, 125);
                            }
                            if (this.loadedstage == -1) {
                                this.rdo.setComposite(AlphaComposite.getInstance(3, 0.7f));
                                this.rdo.setColor(new Color(255, 255, 255));
                                this.rdo.fillRoundRect(50, 100, 460, 40, 20, 20);
                                this.rdo.setColor(new Color(0, 0, 0));
                                this.rdo.drawRoundRect(50, 100, 460, 40, 20, 20);
                                this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                                this.rdo.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rdo.getFontMetrics();
                                this.rdo.setColor(new Color(0, 0, 0));
                                this.rdo.drawString("Error loading stage, try again later...", 280 - this.ftm.stringWidth("Error loading stage, try again later...") / 2, 125);
                            }
                        }
                    }
                    if (this.loadedstages != 1 && this.loadedstages != 5 && this.gs.clcars.isShowing()) {
                        this.gs.clcars.hide();
                    }
                    if (this.xt.clan.toLowerCase().equals(this.claname.toLowerCase())) {
                        if (this.loadedstages >= 0 && this.loadedstages < 2 && this.stringbutton(this.rdo, "     Add a stage of yours to the clan's stages     ", 280, 286, -2, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedstages = 2;
                            this.perry = "0 %";
                        }
                    } else if (this.loadedstages == 1 && !this.selstage.equals("Select Stage") && this.loadedstage > 0 && this.stringbutton(this.rdo, "     Battle with clan over this stage!     ", 280, 286, -2, n, n2, bl && !this.gs.openm, 216, 112)) {
                        this.tab = 2;
                        this.itab = 1;
                        this.litab = -1;
                        this.openi = 10;
                        if (!this.intclan.equals(this.claname)) {
                            this.intclan = this.claname;
                            this.dispi = 0;
                            this.nil = 0;
                            this.lastint = "";
                            this.readint = 1;
                        }
                        this.redif = true;
                        this.intsel = 2;
                    }
                }
                if (this.ctab == 2) {
                    float f = 0.7f;
                    if (this.loadedcars == 1) {
                        f = 0.5f;
                    }
                    this.rdo.setComposite(AlphaComposite.getInstance(3, f));
                    this.rdo.setColor(new Color(255, 255, 255));
                    this.rdo.fillRoundRect(40, 20, 480, 245, 20, 20);
                    this.rdo.setColor(new Color(0, 0, 0));
                    this.rdo.drawRoundRect(40, 20, 480, 245, 20, 20);
                    this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    this.rdo.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rdo.getFontMetrics();
                    if (this.loadedcars == -2) {
                        this.rdo.drawString("Failed to load clan cars, connection error, try again later...", 280 - this.ftm.stringWidth("Failed to load clan cars, connection error, try again later...") / 2, 125);
                    }
                    if (this.loadedcars == -1) {
                        this.rdo.drawString("Loading clan cars, please wait...", 280 - this.ftm.stringWidth("Loading clan cars, please wait...") / 2, 125);
                    }
                    if (this.loadedcars == 0) {
                        this.rdo.drawString("No custom clan cars have been added yet!", 280 - this.ftm.stringWidth("No custom clan cars have been added yet!") / 2, 125);
                    }
                    if (this.loadedcars == 2) {
                        this.rdo.drawString("Searching for and loading cars created by you,  " + this.perry + "  ...", 280 - this.ftm.stringWidth("Searching for and loading cars created by you,  " + this.perry + "  ...") / 2, 125);
                    }
                    if (this.loadedcars == 3) {
                        this.rdo.drawString("Found no cars that can be added!", 280 - this.ftm.stringWidth("Found no cars that can be added!") / 2, 105);
                        this.rdo.drawString("Found no cars created by you that also do not already belong to a clan.", 280 - this.ftm.stringWidth("Found no cars created by you that also do not already belong to a clan.") / 2, 125);
                        if (this.stringbutton(this.rdo, " OK ", 280, 155, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedcars = -1;
                        }
                    }
                    if (this.loadedcars == 4) {
                        this.rdo.drawString("Failed to load cars created by you, connection error, try again later...", 280 - this.ftm.stringWidth("Failed to load cars created by you, connection error, try again later...") / 2, 125);
                        if (this.stringbutton(this.rdo, " OK ", 280, 155, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedcars = -1;
                        }
                    }
                    if (this.loadedcars == 6) {
                        this.rdo.drawString("Adding " + this.selcar + " to your clan's cars, please wait...", 280 - this.ftm.stringWidth("Adding " + this.selcar + " to your clan's cars, please wait...") / 2, 125);
                    }
                    if (this.loadedcars == 7) {
                        this.rdo.drawString("Failed to add car, server error, try again later...", 280 - this.ftm.stringWidth("Failed to add car, server error, try again later...") / 2, 125);
                        if (this.stringbutton(this.rdo, " OK ", 280, 155, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedcars = -1;
                        }
                    }
                    if (this.loadedcars == 8) {
                        this.rdo.drawString("Removing " + this.selcar + " from your clan's cars, please wait...", 280 - this.ftm.stringWidth("Removing " + this.selcar + " from your clan's cars, please wait...") / 2, 125);
                    }
                    if (this.loadedcars == 9) {
                        this.rdo.drawString("Failed to remove car, server error, try again later...", 280 - this.ftm.stringWidth("Failed to remove car, server error, try again later...") / 2, 125);
                        if (this.stringbutton(this.rdo, " OK ", 280, 155, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedcars = -1;
                        }
                    }
                    if (this.loadedcars == 10) {
                        this.rdo.drawString("Are you sure you want to remove " + this.selcar + " from your clan's cars?", 280 - this.ftm.stringWidth("Are you sure you want to remove " + this.selcar + " from your clan's cars?") / 2, 100);
                        if (this.stringbutton(this.rdo, "    Yes    ", 280, 135, -2, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedcars = 8;
                        }
                        if (this.stringbutton(this.rdo, "  No  ", 280, 165, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedcars = 1;
                        }
                    }
                    if (this.loadedcars == 5) {
                        this.rdo.drawString("Select a car to add.", 280 - this.ftm.stringWidth("Select a car to add.") / 2, 80);
                        this.gs.clcars.move(496 - this.gs.clcars.getWidth() / 2, 206);
                        if (this.editc == 0) {
                            if (!this.gs.clcars.isShowing()) {
                                this.gs.clcars.show();
                                this.gs.clcars.select(0);
                            }
                        } else {
                            this.gs.clcars.hide();
                        }
                        if (!this.gs.clcars.getSelectedItem().equals(this.selcar)) {
                            this.selcar = this.gs.clcars.getSelectedItem();
                        }
                        if (this.gs.clcars.open) {
                            this.blocknote = 20;
                        }
                        if (this.stringbutton(this.rdo, "     Add Car     ", 280, 150, -2, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedcars = 6;
                        }
                        if (this.stringbutton(this.rdo, "  Cancel  ", 280, 200, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedcars = -1;
                        }
                    }
                    if (this.loadedcars == 1) {
                        if (this.editc == 0) {
                            if (!this.gs.clcars.isShowing()) {
                                this.gs.clcars.show();
                                this.selcar = this.gs.clcars.getSelectedItem();
                            }
                        } else {
                            this.gs.clcars.hide();
                        }
                        this.gs.clcars.move(496 - this.gs.clcars.getWidth() / 2, 140);
                        if (!this.gs.clcars.getSelectedItem().equals(this.selcar)) {
                            this.selcar = this.gs.clcars.getSelectedItem();
                            this.loadedcar = 0;
                            this.cd.action = 0;
                        }
                        if (this.gs.clcars.open) {
                            this.blocknote = 20;
                        }
                        if (!this.selcar.equals("Select Car")) {
                            if (this.loadedcar > 0) {
                                int n25;
                                int n26;
                                this.rdo.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rdo.getFontMetrics();
                                this.rdo.setColor(new Color(0, 0, 0));
                                this.rdo.drawString("Created/Published by", 63, 37);
                                int n27 = 63 + this.ftm.stringWidth("Created/Published by") / 2 - this.ftm.stringWidth(this.cd.createdby[19 + this.cd.haltload]) / 2;
                                int n28 = n27 + this.ftm.stringWidth(this.cd.createdby[19 + this.cd.haltload]);
                                this.rdo.drawString(this.cd.createdby[19 + this.cd.haltload], n27, 52);
                                this.rdo.drawLine(n27, 54, n28, 54);
                                if (n > n27 + 216 && n < n28 + 216 && n2 > 152 && n2 < 166) {
                                    this.cur = 12;
                                    if (bl) {
                                        this.tab = 1;
                                        if (!this.proname.equals(this.cd.createdby[19 + this.cd.haltload])) {
                                            this.proname = this.cd.createdby[19 + this.cd.haltload];
                                            this.loadedp = false;
                                            this.onexitpro();
                                        }
                                    }
                                }
                                String string = "Class C";
                                if (this.cd.cclass[35 + this.cd.haltload] == 1) {
                                    string = "Class B&C";
                                }
                                if (this.cd.cclass[35 + this.cd.haltload] == 2) {
                                    string = "Class B";
                                }
                                if (this.cd.cclass[35 + this.cd.haltload] == 3) {
                                    string = "Class A&B";
                                }
                                if (this.cd.cclass[35 + this.cd.haltload] == 4) {
                                    string = "Class A";
                                }
                                this.rdo.drawString(string, 389 - this.ftm.stringWidth(string) / 2, 45);
                                if ((n9 != -1 || this.cd.createdby[19 + this.cd.haltload].toLowerCase().equals(this.xt.nickname.toLowerCase())) && this.stringbutton(this.rdo, " Remove X ", 466, 45, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                                    this.loadedcars = 10;
                                }
                                this.m.crs = true;
                                this.m.focus_point = 400;
                                this.m.x = -335;
                                this.m.y = 0;
                                this.m.z = -50;
                                this.m.xz = 0;
                                this.m.zy = 20;
                                this.m.ground = -2000;
                                this.m.cx = 280;
                                this.m.cy = 150;
                                this.bco[35 + this.cd.haltload].z = 1000;
                                this.bco[35 + this.cd.haltload].y = 480 - this.bco[35 + this.cd.haltload].grat;
                                this.bco[35 + this.cd.haltload].x = -52;
                                this.bco[35 + this.cd.haltload].zy = 0;
                                this.bco[35 + this.cd.haltload].xz = this.mrot;
                                this.mrot -= 5;
                                if (this.mrot < -360) {
                                    this.mrot += 360;
                                }
                                this.bco[35 + this.cd.haltload].xy = 0;
                                this.bco[35 + this.cd.haltload].wzy -= 10;
                                if (this.bco[35 + this.cd.haltload].wzy < -45) {
                                    this.bco[35 + this.cd.haltload].wzy += 45;
                                }
                                this.rdo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                                this.bco[35 + this.cd.haltload].d(this.rdo);
                                this.rdo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                this.m.cx = 400;
                                this.m.cy = 225;
                                int n29 = 137;
                                if (this.xt.sc[0] == 35 + this.cd.haltload) {
                                    n29 = 255;
                                }
                                if (this.gs.clcars.getSelectedIndex() != this.gs.clcars.no - 1 && this.stringbutton(this.rdo, " Next > ", 476, n29, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                                    ++this.gs.clcars.sel;
                                }
                                if (this.gs.clcars.getSelectedIndex() != 1 && this.stringbutton(this.rdo, " < Prev ", 84, n29, -3, n, n2, bl && !this.gs.openm, 216, 112)) {
                                    --this.gs.clcars.sel;
                                }
                                if (this.xt.sc[0] != 35 + this.cd.haltload || n < 256 || n > 736 || n2 < 132 || n2 > 377) {
                                    this.rdo.setFont(new Font("Arial", 1, 11));
                                    this.ftm = this.rdo.getFontMetrics();
                                    this.rdo.setColor(new Color(0, 0, 0));
                                    n26 = -36;
                                    n25 = -181;
                                    int n30 = -155;
                                    this.rdo.drawString("Top Speed:", 98 + n26, 343 + n30);
                                    this.rdo.drawImage(this.xt.statb, 162 + n26, 337 + n30, null);
                                    this.rdo.drawString("Acceleration:", 88 + n26, 358 + n30);
                                    this.rdo.drawImage(this.xt.statb, 162 + n26, 352 + n30, null);
                                    this.rdo.drawString("Handling:", 110 + n26, 373 + n30);
                                    this.rdo.drawImage(this.xt.statb, 162 + n26, 367 + n30, null);
                                    this.rdo.drawString("Stunts:", 495 + n25, 343 + n30);
                                    this.rdo.drawImage(this.xt.statb, 536 + n25, 337 + n30, null);
                                    this.rdo.drawString("Strength:", 483 + n25, 358 + n30);
                                    this.rdo.drawImage(this.xt.statb, 536 + n25, 352 + n30, null);
                                    this.rdo.drawString("Endurance:", 473 + n25, 373 + n30);
                                    this.rdo.drawImage(this.xt.statb, 536 + n25, 367 + n30, null);
                                    this.rdo.setColor(new Color(0, 0, 0));
                                    float f2 = (float)(this.cd.swits[35 + this.cd.haltload][2] - 220) / 90.0f;
                                    if ((double)f2 < 0.2) {
                                        f2 = 0.2f;
                                    }
                                    this.rdo.fillRect((int)(162.0f + 156.0f * f2) + n26, 337 + n30, (int)(156.0f * (1.0f - f2) + 1.0f), 7);
                                    f2 = this.cd.acelf[35 + this.cd.haltload][1] * this.cd.acelf[35 + this.cd.haltload][0] * this.cd.acelf[35 + this.cd.haltload][2] * this.cd.grip[35 + this.cd.haltload] / 7700.0f;
                                    if (f2 > 1.0f) {
                                        f2 = 1.0f;
                                    }
                                    this.rdo.fillRect((int)(162.0f + 156.0f * f2) + n26, 352 + n30, (int)(156.0f * (1.0f - f2) + 1.0f), 7);
                                    f2 = this.cd.dishandle[35 + this.cd.haltload];
                                    this.rdo.fillRect((int)(162.0f + 156.0f * f2) + n26, 367 + n30, (int)(156.0f * (1.0f - f2) + 1.0f), 7);
                                    f2 = ((float)this.cd.airc[35 + this.cd.haltload] * this.cd.airs[35 + this.cd.haltload] * this.cd.bounce[35 + this.cd.haltload] + 28.0f) / 139.0f;
                                    if (f2 > 1.0f) {
                                        f2 = 1.0f;
                                    }
                                    this.rdo.fillRect((int)(536.0f + 156.0f * f2) + n25, 337 + n30, (int)(156.0f * (1.0f - f2) + 1.0f), 7);
                                    float f3 = 0.5f;
                                    f2 = (this.cd.moment[35 + this.cd.haltload] + f3) / 2.6f;
                                    if (f2 > 1.0f) {
                                        f2 = 1.0f;
                                    }
                                    this.rdo.fillRect((int)(536.0f + 156.0f * f2) + n25, 352 + n30, (int)(156.0f * (1.0f - f2) + 1.0f), 7);
                                    f2 = this.cd.outdam[35 + this.cd.haltload];
                                    this.rdo.fillRect((int)(536.0f + 156.0f * f2) + n25, 367 + n30, (int)(156.0f * (1.0f - f2) + 1.0f), 7);
                                    this.rdo.drawImage(this.xt.statbo, 162 + n26, 337 + n30, null);
                                    this.rdo.drawImage(this.xt.statbo, 162 + n26, 352 + n30, null);
                                    this.rdo.drawImage(this.xt.statbo, 162 + n26, 367 + n30, null);
                                    this.rdo.drawImage(this.xt.statbo, 536 + n25, 337 + n30, null);
                                    this.rdo.drawImage(this.xt.statbo, 536 + n25, 352 + n30, null);
                                    this.rdo.drawImage(this.xt.statbo, 536 + n25, 367 + n30, null);
                                } else {
                                    int n31;
                                    n26 = -20;
                                    n25 = -220;
                                    this.rdo.setFont(new Font("Arial", 1, 10));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rdo.drawString("Hue  | ", 97 + n26, 70);
                                    this.rdo.drawImage(this.xt.brt, 137 + n26, 63, null);
                                    this.rdo.drawString("Intensity", 121 + n26, 219);
                                    this.rdo.drawString("Hue  | ", 647 + n25, 70);
                                    this.rdo.drawImage(this.xt.brt, 687 + n25, 63, null);
                                    this.rdo.drawString("Intensity", 671 + n25, 219);
                                    for (n31 = 0; n31 < 161; ++n31) {
                                        this.rdo.setColor(Color.getHSBColor((float)((double)n31 * 0.00625), 1.0f, 1.0f));
                                        this.rdo.drawLine(102 + n26, 75 + n31, 110 + n26, 75 + n31);
                                        if (n31 <= 128) {
                                            this.rdo.setColor(Color.getHSBColor(1.0f, 0.0f, (float)(1.0 - (double)n31 * 0.00625)));
                                            this.rdo.drawLine(137 + n26, 75 + n31, 145 + n26, 75 + n31);
                                        }
                                        this.rdo.setColor(Color.getHSBColor((float)((double)n31 * 0.00625), 1.0f, 1.0f));
                                        this.rdo.drawLine(652 + n25, 75 + n31, 660 + n25, 75 + n31);
                                        if (n31 > 128) continue;
                                        this.rdo.setColor(Color.getHSBColor(1.0f, 0.0f, (float)(1.0 - (double)n31 * 0.00625)));
                                        this.rdo.drawLine(687 + n25, 75 + n31, 695 + n25, 75 + n31);
                                    }
                                    for (n31 = 0; n31 < 40; ++n31) {
                                        this.rdo.setColor(Color.getHSBColor(this.xt.arnp[0], (float)((double)n31 * 0.025), 1.0f - this.xt.arnp[2]));
                                        this.rdo.drawLine(121 + n31 + n26, 224, 121 + n31 + n26, 230);
                                        this.rdo.setColor(Color.getHSBColor(this.xt.arnp[3], (float)((double)n31 * 0.025), 1.0f - this.xt.arnp[5]));
                                        this.rdo.drawLine(671 + n31 + n25, 224, 671 + n31 + n25, 230);
                                    }
                                    this.rdo.drawImage(this.xt.arn, 110 + n26, 71 + (int)(this.xt.arnp[0] * 160.0f), null);
                                    this.rdo.drawImage(this.xt.arn, 145 + n26, 71 + (int)(this.xt.arnp[2] * 160.0f), null);
                                    this.rdo.drawImage(this.xt.arn, 660 + n25, 71 + (int)(this.xt.arnp[3] * 160.0f), null);
                                    this.rdo.drawImage(this.xt.arn, 695 + n25, 71 + (int)(this.xt.arnp[5] * 160.0f), null);
                                    this.rdo.setColor(new Color(0, 0, 0));
                                    this.rdo.fillRect(120 + n26 + (int)(this.xt.arnp[1] * 40.0f), 222, 3, 3);
                                    this.rdo.drawLine(121 + n26 + (int)(this.xt.arnp[1] * 40.0f), 224, 121 + n26 + (int)(this.xt.arnp[1] * 40.0f), 230);
                                    this.rdo.fillRect(120 + n26 + (int)(this.xt.arnp[1] * 40.0f), 230, 3, 3);
                                    this.rdo.fillRect(670 + n25 + (int)(this.xt.arnp[4] * 40.0f), 222, 3, 3);
                                    this.rdo.drawLine(671 + n25 + (int)(this.xt.arnp[4] * 40.0f), 224, 671 + n25 + (int)(this.xt.arnp[4] * 40.0f), 230);
                                    this.rdo.fillRect(670 + n25 + (int)(this.xt.arnp[4] * 40.0f), 230, 3, 3);
                                    if (bl) {
                                        if (this.mouson == -1) {
                                            this.mouson = -2;
                                            if (n > 335 + n26 && n < 379 + n26 && n2 > 334 && n2 < 344) {
                                                this.mouson = 1;
                                            }
                                            if (n > 885 + n25 && n < 929 + n25 && n2 > 334 && n2 < 344) {
                                                this.mouson = 4;
                                            }
                                            if (n > 314 + n26 && n < 338 + n26 && n2 > 181 && n2 < 353 && this.mouson == -2) {
                                                this.mouson = 0;
                                            }
                                            if (n > 349 + n26 && n < 373 + n26 && n2 > 181 && n2 < 321 && this.mouson == -2) {
                                                this.mouson = 2;
                                            }
                                            if (n > 864 + n25 && n < 888 + n25 && n2 > 181 && n2 < 353 && this.mouson == -2) {
                                                this.mouson = 3;
                                            }
                                            if (n > 899 + n25 && n < 923 + n25 && n2 > 181 && n2 < 321 && this.mouson == -2) {
                                                this.mouson = 5;
                                            }
                                        }
                                    } else if (this.mouson != -1) {
                                        this.mouson = -1;
                                    }
                                    if (this.mouson >= 0 && this.mouson <= 5) {
                                        this.blocknote = 20;
                                    }
                                    if (this.mouson == 0 || this.mouson == 2 || this.mouson == 3 || this.mouson == 5) {
                                        this.xt.arnp[this.mouson] = (float)((double)((float)n2 - 187.0f) * 0.00625);
                                        if (this.mouson == 2 || this.mouson == 5) {
                                            if ((double)this.xt.arnp[this.mouson] > 0.8) {
                                                this.xt.arnp[this.mouson] = 0.8f;
                                            }
                                        } else if (this.xt.arnp[this.mouson] > 1.0f) {
                                            this.xt.arnp[this.mouson] = 1.0f;
                                        }
                                        if (this.xt.arnp[this.mouson] < 0.0f) {
                                            this.xt.arnp[this.mouson] = 0.0f;
                                        }
                                    }
                                    if (this.mouson == 1) {
                                        this.xt.arnp[this.mouson] = (float)((double)((float)n - (float)(337 + n26)) * 0.025);
                                        if (this.xt.arnp[this.mouson] > 1.0f) {
                                            this.xt.arnp[this.mouson] = 1.0f;
                                        }
                                        if (this.xt.arnp[this.mouson] < 0.0f) {
                                            this.xt.arnp[this.mouson] = 0.0f;
                                        }
                                    }
                                    if (this.mouson == 4) {
                                        this.xt.arnp[this.mouson] = (float)((double)((float)n - (float)(887 + n25)) * 0.025);
                                        if (this.xt.arnp[this.mouson] > 1.0f) {
                                            this.xt.arnp[this.mouson] = 1.0f;
                                        }
                                        if (this.xt.arnp[this.mouson] < 0.0f) {
                                            this.xt.arnp[this.mouson] = 0.0f;
                                        }
                                    }
                                    Color color = Color.getHSBColor(this.xt.arnp[0], this.xt.arnp[1], 1.0f - this.xt.arnp[2]);
                                    object = Color.getHSBColor(this.xt.arnp[3], this.xt.arnp[4], 1.0f - this.xt.arnp[5]);
                                    for (int i = 0; i < this.bco[36].npl; ++i) {
                                        if (this.bco[36].p[i].colnum == 1) {
                                            this.bco[36].p[i].hsb[0] = this.xt.arnp[0];
                                            this.bco[36].p[i].hsb[1] = this.xt.arnp[1];
                                            this.bco[36].p[i].hsb[2] = 1.0f - this.xt.arnp[2];
                                            this.bco[36].p[i].c[0] = color.getRed();
                                            this.bco[36].p[i].c[1] = color.getGreen();
                                            this.bco[36].p[i].c[2] = color.getBlue();
                                            this.bco[36].p[i].oc[0] = color.getRed();
                                            this.bco[36].p[i].oc[1] = color.getGreen();
                                            this.bco[36].p[i].oc[2] = color.getBlue();
                                        }
                                        if (this.bco[36].p[i].colnum != 2) continue;
                                        this.bco[36].p[i].hsb[0] = this.xt.arnp[3];
                                        this.bco[36].p[i].hsb[1] = this.xt.arnp[4];
                                        this.bco[36].p[i].hsb[2] = 1.0f - this.xt.arnp[5];
                                        this.bco[36].p[i].c[0] = ((Color)object).getRed();
                                        this.bco[36].p[i].c[1] = ((Color)object).getGreen();
                                        this.bco[36].p[i].c[2] = ((Color)object).getBlue();
                                        this.bco[36].p[i].oc[0] = ((Color)object).getRed();
                                        this.bco[36].p[i].oc[1] = ((Color)object).getGreen();
                                        this.bco[36].p[i].oc[2] = ((Color)object).getBlue();
                                    }
                                    if (this.stringbutton(this.rdo, "    Play >    ", 280, 220, -1, n, n2, bl && !this.gs.openm, 216, 112)) {
                                        this.open = 450;
                                        this.upo = false;
                                        this.domon = false;
                                        this.onexit();
                                    }
                                }
                                if (this.xt.clan.toLowerCase().equals(this.claname.toLowerCase())) {
                                    if (this.xt.sc[0] != 36) {
                                        if (this.stringbutton(this.rdo, "     Switch to using clan cars     ", 280, 250, -2, n, n2, bl && !this.gs.openm, 216, 112)) {
                                            this.xt.sc[0] = 36;
                                            n26 = 0;
                                            for (n25 = 0; n25 < this.bco[36].npl && n26 == 0; ++n25) {
                                                if (this.bco[36].p[n25].colnum != 1) continue;
                                                float[] fArray = new float[3];
                                                Color.RGBtoHSB(this.bco[36].p[n25].c[0], this.bco[36].p[n25].c[1], this.bco[36].p[n25].c[2], fArray);
                                                this.xt.arnp[0] = fArray[0];
                                                this.xt.arnp[1] = fArray[1];
                                                this.xt.arnp[2] = 1.0f - fArray[2];
                                                n26 = 1;
                                            }
                                            n26 = 0;
                                            for (n25 = 0; n25 < this.bco[36].npl && n26 == 0; ++n25) {
                                                if (this.bco[36].p[n25].colnum != 2) continue;
                                                float[] fArray = new float[3];
                                                Color.RGBtoHSB(this.bco[36].p[n25].c[0], this.bco[36].p[n25].c[1], this.bco[36].p[n25].c[2], fArray);
                                                this.xt.arnp[3] = fArray[0];
                                                this.xt.arnp[4] = fArray[1];
                                                this.xt.arnp[5] = 1.0f - fArray[2];
                                                n26 = 1;
                                            }
                                        }
                                    } else {
                                        this.rdo.setFont(new Font("Arial", 1, 12));
                                        this.ftm = this.rdo.getFontMetrics();
                                        this.rdo.setColor(new Color(0, 0, 0));
                                        this.rdo.drawString("You are currently using your clan's cars.", 280 - this.ftm.stringWidth("You are currently using your clan's cars.") / 2, 250);
                                    }
                                }
                                if (this.cd.publish[19 + this.cd.haltload] > 0) {
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.setColor(new Color(0, 0, 0));
                                    if (this.cd.action == -9) {
                                        this.rd.drawString("Failed to add car!  Unknown error, please try again later.", 496 - this.ftm.stringWidth("Failed to add car!  Unknown error, please try again later.") / 2, 432);
                                    }
                                    if (this.cd.action == -8) {
                                        this.rd.drawString("Cannot add more then 20 cars to your account!", 496 - this.ftm.stringWidth("Cannot add more then 20 cars to your account!") / 2, 432);
                                    }
                                    if (this.cd.action == 7) {
                                        this.rd.drawString("[" + this.cd.acname + "] has been added to your cars!", 496 - this.ftm.stringWidth("[" + this.cd.acname + "] has been added to your cars!") / 2, 432);
                                    }
                                    if (this.cd.action == -7) {
                                        this.rd.drawString("You already have this car.", 496 - this.ftm.stringWidth("You already have this car.") / 2, 432);
                                    }
                                    if (this.cd.action == 6) {
                                        this.rd.drawString("Adding Car...", 496 - this.ftm.stringWidth("Adding Car...") / 2, 432);
                                    }
                                    if (this.cd.action == -6) {
                                        String string5 = "Upgrade to a full account to add custom cars!";
                                        n25 = 496 - this.ftm.stringWidth(string5) / 2;
                                        int n32 = n25 + this.ftm.stringWidth(string5);
                                        this.rd.drawString(string5, n25, 432);
                                        if (this.waitlink != -1) {
                                            this.rd.drawLine(n25, 435, n32, 435);
                                        }
                                        if (n > n25 && n < n32 && n2 > 421 && n2 < 435) {
                                            if (this.waitlink != -1) {
                                                this.cur = 12;
                                            }
                                            if (bl && this.waitlink == 0) {
                                                this.gs.editlink(this.xt.nickname, true);
                                                this.waitlink = -1;
                                            }
                                        }
                                        if (this.waitlink > 0) {
                                            --this.waitlink;
                                        }
                                    }
                                    if (this.cd.action == 0 && this.xt.drawcarb(true, null, " Add to My Cars ", 437, 414, n, n2, bl && this.blocknote == 0)) {
                                        if (this.xt.logged) {
                                            this.cd.action = 6;
                                            this.cd.ac = -1;
                                            this.cd.acname = this.selcar;
                                            this.cd.sparkactionloader();
                                        } else {
                                            this.cd.action = -6;
                                            this.waitlink = 20;
                                        }
                                    }
                                } else {
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawString("Private Car", 496 - this.ftm.stringWidth("Private Car") / 2, 432);
                                }
                            }
                            if (this.loadedcar == 0) {
                                this.rdo.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rdo.getFontMetrics();
                                this.rdo.setColor(new Color(0, 0, 0));
                                this.rdo.drawString("Loading...", 280 - this.ftm.stringWidth("Loading...") / 2, 125);
                            }
                            if (this.loadedcar == -1) {
                                this.rdo.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rdo.getFontMetrics();
                                this.rdo.setColor(new Color(0, 0, 0));
                                this.rdo.drawString("Error loading car, try again later...", 280 - this.ftm.stringWidth("Error loading car, try again later...") / 2, 125);
                            }
                        }
                    }
                    if (this.loadedcars != 1 && this.loadedcars != 5 && this.gs.clcars.isShowing()) {
                        this.gs.clcars.hide();
                    }
                    if (this.xt.clan.toLowerCase().equals(this.claname.toLowerCase())) {
                        if (this.loadedcars >= 0 && this.loadedcars < 2 && this.stringbutton(this.rdo, "     Add a car of yours to the clan's cars     ", 280, 286, -2, n, n2, bl && !this.gs.openm, 216, 112)) {
                            this.loadedcars = 2;
                            this.perry = "0 %";
                        }
                    } else if (this.loadedcars == 1 && !this.selcar.equals("Select Car") && this.loadedcar > 0 && this.stringbutton(this.rdo, "     Battle with clan over this car!     ", 280, 286, -2, n, n2, bl && !this.gs.openm, 216, 112)) {
                        this.tab = 2;
                        this.itab = 1;
                        this.litab = -1;
                        this.openi = 10;
                        if (!this.intclan.equals(this.claname)) {
                            this.intclan = this.claname;
                            this.dispi = 0;
                            this.nil = 0;
                            this.lastint = "";
                            this.readint = 1;
                        }
                        this.redif = true;
                        this.intsel = 3;
                    }
                }
                if (this.ctab != 2 && this.ctab != 3 && this.gs.clcars.isShowing()) {
                    this.gs.clcars.hide();
                }
                if (this.ctab == 4) {
                    boolean bl5 = false;
                    if (n > 266 && n < 726 && n2 > 162 && n2 < 223 && this.editc == 0) {
                        bl5 = true;
                    }
                    this.rdo.setComposite(AlphaComposite.getInstance(3, 0.7f));
                    this.rdo.setColor(new Color(255, 255, 255));
                    this.rdo.fillRoundRect(50, 50, 460, 61, 20, 20);
                    this.rdo.setColor(new Color(0, 0, 0));
                    this.rdo.drawRoundRect(50, 50, 460, 61, 20, 20);
                    this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    if (this.loadedlink) {
                        if (n9 != -1 && bl5) {
                            this.rdo.setFont(new Font("Arial", 1, 11));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.drawString("Edit", 480, 66);
                            this.rdo.drawLine(480, 68, 480 + this.ftm.stringWidth("Edit"), 68);
                            if (n > 696 && n < 696 + this.ftm.stringWidth("Edit") && n2 > 166 && n2 < 181 && this.editc == 0) {
                                this.cur = 12;
                                if (bl) {
                                    this.editc = 6;
                                    this.msg = "Edit Clan's Web Presence";
                                    this.flko = 0;
                                    bl = false;
                                }
                            }
                        }
                        if (!this.ltit.equals("") && !this.lurl.equals("")) {
                            this.rdo.setFont(new Font("Arial", 1, 13));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.drawString(this.ltit, 70, 74);
                            if (bl5) {
                                this.rdo.drawLine(70, 76, 70 + this.ftm.stringWidth(this.ltit), 76);
                            }
                            this.rdo.setFont(new Font("Arial", 0, 12));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.drawString(this.ldes, 70, 94);
                            if (bl5 && this.editc == 0) {
                                this.cur = 12;
                                if (bl) {
                                    this.gs.openurl(this.lurl);
                                }
                            }
                        } else {
                            this.rdo.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rdo.getFontMetrics();
                            this.rdo.drawString("" + this.claname + " has no external online presence.", 280 - this.ftm.stringWidth("" + this.claname + " has no external online presence.") / 2, 85);
                        }
                    } else {
                        this.rdo.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rdo.getFontMetrics();
                        this.rdo.drawString("Loading...", 280 - this.ftm.stringWidth("Loading...") / 2, 85);
                    }
                }
                if (this.sdist != 0) {
                    this.rdo.setColor(this.color2k(200, 200, 200));
                    this.rdo.fillRect(540, 20, 17, 260);
                    if (this.mscro5 == 831) {
                        this.rdo.setColor(this.color2k(215, 215, 215));
                        this.rdo.fillRect(540, 3, 17, 17);
                    } else {
                        this.rdo.setColor(this.color2k(220, 220, 220));
                        this.rdo.fill3DRect(540, 3, 17, 17, true);
                    }
                    this.rdo.drawImage(this.xt.asu, 545, 9, null);
                    if (this.mscro5 == 832) {
                        this.rdo.setColor(this.color2k(215, 215, 215));
                        this.rdo.fillRect(540, 280, 17, 17);
                    } else {
                        this.rdo.setColor(this.color2k(220, 220, 220));
                        this.rdo.fill3DRect(540, 280, 17, 17, true);
                    }
                    this.rdo.drawImage(this.xt.asd, 545, 287, null);
                    if (this.lspos5 != this.spos5) {
                        this.rdo.setColor(this.color2k(215, 215, 215));
                        this.rdo.fillRect(540, 20 + this.spos5, 17, 31);
                    } else {
                        if (this.mscro5 == 831) {
                            this.rdo.setColor(this.color2k(215, 215, 215));
                        }
                        this.rdo.fill3DRect(540, 20 + this.spos5, 17, 31, true);
                    }
                    this.rdo.setColor(this.color2k(150, 150, 150));
                    this.rdo.drawLine(545, 33 + this.spos5, 551, 33 + this.spos5);
                    this.rdo.drawLine(545, 35 + this.spos5, 551, 35 + this.spos5);
                    this.rdo.drawLine(545, 37 + this.spos5, 551, 37 + this.spos5);
                    if (this.mscro5 > 800 && this.lspos5 != this.spos5) {
                        this.lspos5 = this.spos5;
                    }
                    if (bl) {
                        int n33;
                        if (this.mscro5 == 825 && n > 756 && n < 773 && n2 > 132 + this.spos5 && n2 < this.spos5 + 163) {
                            this.mscro5 = n2 - this.spos5;
                        }
                        if (this.mscro5 == 825 && n > 754 && n < 775 && n2 > 26 && n2 < 134) {
                            this.mscro5 = 831;
                        }
                        if (this.mscro5 == 825 && n > 754 && n < 775 && n2 > 390 && n2 < 411) {
                            this.mscro5 = 832;
                        }
                        if (this.mscro5 == 825 && n > 756 && n < 773 && n2 > 132 && n2 < 392) {
                            this.mscro5 = 152;
                            this.spos5 = n2 - this.mscro5;
                        }
                        if ((n33 = 2670 / this.sdist) < 1) {
                            n33 = 1;
                        }
                        if (this.mscro5 == 831) {
                            this.spos5 -= n33;
                            if (this.spos5 > 229) {
                                this.spos5 = 229;
                            }
                            if (this.spos5 < 0) {
                                this.spos5 = 0;
                            }
                            this.lspos5 = this.spos5;
                        }
                        if (this.mscro5 == 832) {
                            this.spos5 += n33;
                            if (this.spos5 > 229) {
                                this.spos5 = 229;
                            }
                            if (this.spos5 < 0) {
                                this.spos5 = 0;
                            }
                            this.lspos5 = this.spos5;
                        }
                        if (this.mscro5 < 800) {
                            this.spos5 = n2 - this.mscro5;
                            if (this.spos5 > 229) {
                                this.spos5 = 229;
                            }
                            if (this.spos5 < 0) {
                                this.spos5 = 0;
                            }
                        }
                        if (this.mscro5 == 825) {
                            this.mscro5 = 925;
                        }
                    } else if (this.mscro5 != 825) {
                        this.mscro5 = 825;
                    }
                }
                this.rd.drawImage(this.gImage, 216, 112, null);
                if (this.ctab != 2 && this.ctab != 3 && this.xt.clan.equals("")) {
                    int n34;
                    boolean bl6 = false;
                    for (n34 = 0; n34 < this.nmb; ++n34) {
                        if (!this.xt.nickname.toLowerCase().equals(this.member[n34].toLowerCase())) continue;
                        bl6 = true;
                        break;
                    }
                    if (!bl6) {
                        int n35;
                        n34 = 0;
                        for (n35 = 0; n35 < this.nrmb; ++n35) {
                            if (!this.xt.nickname.toLowerCase().equals(this.rmember[n35].toLowerCase())) continue;
                            n34 = 1;
                            break;
                        }
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        if (n34 == 0) {
                            if (this.stringbutton(this.rd, "      Request to Join this Clan      ", 496, 432, 3, n, n2, bl && !this.gs.openm, 0, 0)) {
                                this.editc = this.xt.logged ? 99 : 101;
                            }
                            n35 = this.ftm.stringWidth("      Request to Join this Clan      ");
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawRoundRect(496 - n35 / 2 - 20, 415, n35 + 40, 24, 20, 20);
                        } else {
                            n35 = this.ftm.stringWidth("You have requested to join this clan, waiting for admin to approve your membership.");
                            this.rd.drawString("You have requested to join this clan, waiting for admin to approve your membership.", 496 - n35 / 2, 432);
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawRoundRect(496 - n35 / 2 - 20, 415, n35 + 40, 24, 20, 20);
                        }
                    }
                }
                if (this.editc == 1 || this.editc == 2) {
                    this.rd.setColor(new Color(244, 232, 204));
                    this.rd.fillRoundRect(265, 92, 460, 220, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(265, 92, 460, 220, 20, 20);
                    String[] stringArray = new String[]{"logo", "350x30", "35 : 3"};
                    if (this.editc == 2) {
                        stringArray[0] = "background";
                        stringArray[1] = "560x300";
                        stringArray[2] = "56 : 30";
                    }
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    if (this.flko % 4 != 0 || this.flko == 0) {
                        this.rd.drawString(this.msg, 495 - this.ftm.stringWidth(this.msg) / 2, 115);
                    }
                    if (this.flko != 0) {
                        --this.flko;
                    }
                    this.rd.setFont(new Font("Arial", 0, 12));
                    this.rd.drawString("The " + stringArray[0] + " image is " + stringArray[1] + " pixels.", 275, 140);
                    this.rd.drawString("Any image uploaded will be resized to that width and height. For the best results", 275, 160);
                    this.rd.drawString("try to upload an image that is bigger or equal to " + stringArray[1] + " and has the scale of", 275, 180);
                    this.rd.drawString("[ " + stringArray[2] + " ]  in  [ Width : Height ].", 275, 200);
                    this.rd.drawString("Image uploaded must be less than 1MB and in the format of JPEG, GIF or PNG.", 275, 220);
                    if (this.upload == 0) {
                        if (this.stringbutton(this.rd, "  Upload Image  ", 495, 250, 0, n, n2, bl, 0, 0)) {
                            FileDialog fileDialog = new FileDialog(new Frame(), "Upload Image");
                            fileDialog.setMode(0);
                            fileDialog.setVisible(true);
                            this.filename = "" + fileDialog.getDirectory() + "" + fileDialog.getFile() + "";
                            if (!this.filename.equals("nullnull")) {
                                this.upload = 1;
                            }
                        }
                    } else {
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        if (this.upload == 1) {
                            this.rd.drawString("Checking image...", 495 - this.ftm.stringWidth("Checking image...") / 2, 250);
                        }
                        if (this.upload == 2) {
                            this.rd.drawString("Authenticating...", 495 - this.ftm.stringWidth("Authenticating...") / 2, 250);
                        }
                        if (this.upload == 3) {
                            this.rd.drawString("Uploading image :  " + this.perc + " %", 495 - this.ftm.stringWidth("Uploading image :  80 %") / 2, 250);
                        }
                        if (this.upload == 4) {
                            this.rd.drawString("Creating image online...", 495 - this.ftm.stringWidth("Creating image online...") / 2, 250);
                        }
                        if (this.upload == 5) {
                            this.rd.drawString("Done", 495 - this.ftm.stringWidth("Done") / 2, 250);
                        }
                    }
                    if (this.stringbutton(this.rd, " Cancel ", 495, 290, 2, n, n2, bl, 0, 0)) {
                        if (this.upload == 0) {
                            this.editc = 0;
                        } else {
                            this.upload = 0;
                        }
                    }
                }
                if (this.editc == 3) {
                    this.rd.setColor(new Color(244, 232, 204));
                    this.rd.fillRoundRect(245, 92, 500, 190, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(245, 92, 500, 190, 20, 20);
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    String string = "" + this.member[this.em] + "'s";
                    if (this.xt.nickname.toLowerCase().equals(this.member[this.em].toLowerCase())) {
                        string = "Your";
                    }
                    this.rd.drawString("Edit " + string + " Clan Membership", 495 - this.ftm.stringWidth("Edit " + string + "'s Clan Membership") / 2, 115);
                    this.rd.drawString("Rank Description :", 399 - this.ftm.stringWidth("Membership Level :"), 146);
                    this.dorank = true;
                    this.rd.drawString("Membership Level :", 503 - this.ftm.stringWidth("Membership Level :"), 176);
                    this.gs.clanlev.move(513, 159);
                    if (!this.gs.clanlev.isShowing()) {
                        this.gs.clanlev.select(this.mlevel[this.em] - 1);
                        if (this.em == 0 && this.mlevel[this.em] == 7) {
                            this.gs.clanlev.disable();
                        } else {
                            this.gs.clanlev.enable();
                        }
                        this.gs.clanlev.show();
                    }
                    if (this.stringbutton(this.rd, "     Save     ", 495, 220, 0, n, n2, bl, 0, 0)) {
                        this.gs.clanlev.hide();
                        this.editc = 33;
                    }
                    if (this.stringbutton(this.rd, " Cancel ", 495, 260, 2, n, n2, bl, 0, 0)) {
                        this.editc = 0;
                        this.gs.clanlev.hide();
                    }
                }
                if (this.editc == 4) {
                    this.rd.setColor(new Color(244, 232, 204));
                    this.rd.fillRoundRect(220, 92, 550, 155, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(220, 92, 550, 155, 20, 20);
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    String string = "" + this.member[this.em] + "";
                    if (this.xt.nickname.toLowerCase().equals(this.member[this.em].toLowerCase())) {
                        string = "yourself";
                    }
                    this.rd.drawString("Are you sure you want to remove " + string + " from the clan?", 495 - this.ftm.stringWidth("Are you sure you want to remove " + string + " from the clan?") / 2, 120);
                    if (this.xt.nickname.toLowerCase().equals(this.member[this.em].toLowerCase()) && this.em == 0) {
                        this.rd.setFont(new Font("Arial", 0, 12));
                        this.ftm = this.rd.getFontMetrics();
                        if (this.nmb > 1) {
                            this.rd.drawString("Note: This will result in the second high ranking player (" + this.member[1] + ") becoming the new Clan Leader!", 495 - this.ftm.stringWidth("Note: This will result in the second high ranking player (" + this.member[1] + ") becoming the new Clan Leader!") / 2, 140);
                        } else {
                            this.rd.drawString("Note: This will result in the deletion of this clan since you are the only player in it.", 495 - this.ftm.stringWidth("Note: This will result in the deletion of this clan since you are the only player in it.") / 2, 140);
                        }
                    }
                    if (this.stringbutton(this.rd, "     Yes     ", 495, 185, 0, n, n2, bl, 0, 0)) {
                        this.editc = 44;
                    }
                    if (this.stringbutton(this.rd, " No, Cancel ", 495, 225, 2, n, n2, bl, 0, 0)) {
                        this.editc = 0;
                    }
                }
                if (this.editc == 6) {
                    this.rd.setColor(new Color(244, 232, 204));
                    this.rd.fillRoundRect(245, 92, 500, 225, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(245, 92, 500, 225, 20, 20);
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    if (this.flko % 4 != 0 || this.flko == 0) {
                        this.rd.drawString(this.msg, 495 - this.ftm.stringWidth(this.msg) / 2, 115);
                    }
                    if (this.flko != 0) {
                        --this.flko;
                    }
                    this.rd.setFont(new Font("Arial", 0, 12));
                    this.rd.drawString("Does your clan have an online forum, a Facebook group, a website or any online", 255, 140);
                    this.rd.drawString("presence at all beyond the game?  If so, you can link to it from here!", 255, 160);
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("Link Title :", 400 - this.ftm.stringWidth("Link Title :"), 190);
                    this.rd.drawString("Link Description :", 400 - this.ftm.stringWidth("Link Description :"), 220);
                    this.doweb1 = true;
                    if (this.stringbutton(this.rd, "     Next >     ", 495, 255, 0, n, n2, bl, 0, 0)) {
                        if (this.gs.temail.getText().equals("") || this.gs.cmsg.getText().equals("")) {
                            this.msg = "Please enter a link title and a link description!";
                            this.flko = 45;
                        } else {
                            this.sltit = this.gs.temail.getText();
                            if (!this.lurl.equals("") && this.lurl.toLowerCase().startsWith("http")) {
                                this.gs.temail.setText(this.lurl);
                            } else {
                                this.gs.temail.setText("http://");
                            }
                            this.msg = "Edit Clan's Web Presence";
                            this.flko = 0;
                            this.editc = 7;
                        }
                    }
                    if (this.stringbutton(this.rd, " Cancel ", 495, 295, 2, n, n2, bl, 0, 0)) {
                        this.editc = 0;
                    }
                }
                if (this.editc == 7) {
                    this.rd.setColor(new Color(244, 232, 204));
                    this.rd.fillRoundRect(245, 92, 500, 225, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(245, 92, 500, 225, 20, 20);
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    if (this.flko % 4 != 0 || this.flko == 0) {
                        this.rd.drawString(this.msg, 495 - this.ftm.stringWidth(this.msg) / 2, 115);
                    }
                    if (this.flko != 0) {
                        --this.flko;
                    }
                    this.rd.drawString("Link URL :", 343 - this.ftm.stringWidth("Link URL :"), 150);
                    this.doweb2 = true;
                    this.rd.drawString("WARNING :", 255, 180);
                    this.rd.setFont(new Font("Arial", 0, 12));
                    this.rd.drawString("Any link placed that contains inappropriate, spam or unrelated content will result in", 255, 200);
                    this.rd.drawString("instant clan deletion and permanent account banning!", 255, 220);
                    if (this.stringbutton(this.rd, "     Save     ", 495, 255, 0, n, n2, bl, 0, 0)) {
                        if (this.gs.temail.getText().equals("") || this.gs.temail.getText().equals("http://")) {
                            this.msg = "Please enter a link URL!";
                            this.flko = 45;
                        } else {
                            this.editc = 55;
                        }
                    }
                    if (this.stringbutton(this.rd, " Cancel ", 495, 295, 2, n, n2, bl, 0, 0)) {
                        this.editc = 0;
                    }
                }
                if (this.editc == 33 || this.editc == 44 || this.editc == 66 || this.editc == 77 || this.editc == 99 || this.editc == 55) {
                    this.rd.setColor(new Color(244, 232, 204));
                    this.rd.fillRoundRect(345, 92, 300, 40, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(345, 92, 300, 40, 20, 20);
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("One moment...", 495 - this.ftm.stringWidth("One moment...") / 2, 117);
                }
                if (this.editc == 5) {
                    this.rd.setColor(new Color(244, 232, 204));
                    this.rd.fillRoundRect(265, 92, 460, 115, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(265, 92, 460, 115, 20, 20);
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("Server error occurred or was unable to authorize this action.", 495 - this.ftm.stringWidth("Server error occurred or was unable to authorize this action.") / 2, 120);
                    this.rd.drawString("Please try again later.", 495 - this.ftm.stringWidth("Please try again later.") / 2, 150);
                    if (this.stringbutton(this.rd, "     OK     ", 495, 185, 2, n, n2, bl, 0, 0)) {
                        this.editc = 0;
                    }
                }
                if (this.editc == 101) {
                    this.rd.setColor(new Color(244, 232, 204));
                    this.rd.fillRoundRect(232, 90, 527, 176, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(232, 90, 527, 176, 20, 20);
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("You are currently using a trial account.", 495 - this.ftm.stringWidth("You are currently using a trial account.") / 2, 120);
                    this.rd.drawString("You need to upgrade to be able participate in any NFM clan activity.", 495 - this.ftm.stringWidth("You need to upgrade to be able participate in any NFM clan activity.") / 2, 140);
                    this.rd.setColor(new Color(206, 171, 98));
                    this.rd.fillRoundRect(405, 163, 180, 50, 20, 20);
                    if (this.drawbutton(this.xt.upgrade, 495, 188, n, n2, bl)) {
                        this.gs.editlink(this.xt.nickname, true);
                    }
                    if (this.stringbutton(this.rd, " Cancel ", 495, 244, 2, n, n2, bl, 0, 0)) {
                        this.editc = 0;
                    }
                }
            } else {
                this.rd.drawString("Loading clan: " + this.claname + ", please wait...", 495 - this.ftm.stringWidth("Loading clan: " + this.claname + ", please wait...") / 2, 222);
            }
        } else {
            if (this.gs.clcars.isShowing()) {
                this.gs.clcars.hide();
            }
            if (this.editc != 0) {
                this.editc = 0;
                if (this.gs.clanlev.isShowing()) {
                    this.gs.clanlev.hide();
                }
            }
        }
    }

    /*
     * Opcode count of 21505 triggered aggressive code reduction.  Override with --aggressivesizethreshold.
     */
    public void dotab2(int n, int n2, boolean bl) {
        int n3;
        if (this.itab == 0) {
            if (this.litab != this.itab) {
                this.spos3 = 0;
                this.gs.senditem.hide();
                this.gs.datat.hide();
                this.gs.sendtyp.removeAll();
                this.gs.sendtyp.add(this.rd, "Write a Message");
                this.gs.sendtyp.add(this.rd, "Share a Custom Car");
                this.gs.sendtyp.add(this.rd, "Share a Custom Stage");
                this.gs.sendtyp.add(this.rd, "Send a Clan Invitation");
                this.gs.sendtyp.add(this.rd, "Share a Relative Date");
                if (!this.forcsel) {
                    this.gs.sendtyp.select(0);
                } else {
                    this.gs.sendtyp.select(this.itemsel);
                }
                this.forcsel = false;
                this.flko = 0;
                this.itemsel = 0;
                this.flko = 0;
                this.litab = this.itab;
            }
            if (this.openc != 10) {
                int n4;
                int[] nArray;
                Object[] objectArray;
                this.rd.setColor(this.color2k(230, 230, 230));
                this.rd.fillRoundRect(197, 40, 597, 404, 20, 20);
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawRoundRect(197, 40, 597, 404, 20, 20);
                if (this.loadmsgs != 0 && this.loadmsgs != -2 && this.loadmsgs != -1) {
                    boolean bl2;
                    boolean bl3;
                    int n5;
                    this.sdist = (this.nm - 10) * 31;
                    if (this.sdist < 0) {
                        this.sdist = 0;
                    }
                    this.scro = (int)((float)this.spos3 / 268.0f * (float)this.sdist);
                    n3 = 0;
                    for (n5 = 0; n5 < this.nm; ++n5) {
                        if (this.mtyp[n5] == 3) continue;
                        if (76 + 31 * n3 - this.scro < 408 && 107 + 31 * n3 - this.scro > 76) {
                            int n6;
                            bl3 = false;
                            if (n > 207 && n < 770 && n2 > 76 + 31 * n3 - this.scro && n2 < 106 + 31 * n3 - this.scro) {
                                bl3 = true;
                                this.cur = 12;
                                if (bl && this.openc == 0) {
                                    this.opy = 70 + 31 * n3 - this.scro;
                                    this.addopy = (40 - this.opy) / 10;
                                    this.oph = 44;
                                    this.openc = 1;
                                    if (!this.opname.equals(this.mname[n5])) {
                                        this.opname = this.mname[n5];
                                        this.lastsub = "";
                                        this.readmsg = 1;
                                    }
                                }
                            }
                            if (this.mtyp[n5] == 1) {
                                this.rd.setColor(this.color2k(240, 240, 240));
                                this.rd.fillRect(207, 77 + 31 * n3 - this.scro, 564, 30);
                            }
                            if (bl3) {
                                this.rd.setColor(this.color2k(250, 250, 250));
                                this.rd.fillRect(207, 77 + 31 * n3 - this.scro, 564, 30);
                            }
                            bl2 = this.drawl(this.rd, this.mname[n5], 207, 77 + 31 * n3 - this.scro, bl3);
                            if (!bl3 || !bl2) {
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawString(this.mname[n5], 267 - this.ftm.stringWidth(this.mname[n5]) / 2, 96 + 31 * n3 - this.scro);
                            }
                            int[] nArray2 = new int[]{0, 5, 5, 14, 14, 5, 5};
                            int[] nArray3 = new int[]{0, -5, -2, -2, 3, 3, 5};
                            if (this.mtyp[n5] != 2) {
                                n6 = 0;
                                while (n6 < 7) {
                                    int n7 = n6;
                                    nArray2[n7] = nArray2[n7] + 335;
                                    int n8 = n6++;
                                    nArray3[n8] = nArray3[n8] + (98 + 31 * n3 - this.scro);
                                }
                                this.rd.setColor(new Color(0, 128, 0));
                            } else {
                                n6 = 0;
                                while (n6 < 7) {
                                    nArray2[n6] = 349 - nArray2[n6];
                                    int n9 = n6++;
                                    nArray3[n9] = nArray3[n9] + (98 + 31 * n3 - this.scro);
                                }
                                this.rd.setColor(new Color(0, 0, 128));
                            }
                            this.rd.fillPolygon(nArray2, nArray3, 7);
                            this.rd.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(this.color2k(125, 125, 125));
                            this.rd.drawString(this.mtime[n5], 760 - this.ftm.stringWidth(this.mtime[n5]), 102 + 31 * n3 - this.scro);
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString(this.msub[n5], 335, 89 + 31 * n3 - this.scro);
                            this.rd.setColor(this.color2k(150, 150, 150));
                            this.rd.drawLine(207, 107 + 31 * n3 - this.scro, 770, 107 + 31 * n3 - this.scro);
                        }
                        ++n3;
                    }
                    for (n5 = 0; n5 < this.nm; ++n5) {
                        if (this.mtyp[n5] != 3) continue;
                        if (76 + 31 * n3 - this.scro < 408 && 107 + 31 * n3 - this.scro > 76) {
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            bl3 = false;
                            if (n > 207 && n < 770 && n2 > 76 + 31 * n3 - this.scro && n2 < 106 + 31 * n3 - this.scro) {
                                bl3 = true;
                            }
                            bl2 = this.drawl(this.rd, this.mname[n5], 207, 77 + 31 * n3 - this.scro, bl3);
                            if (!bl3 || !bl2) {
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawString(this.mname[n5], 267 - this.ftm.stringWidth(this.mname[n5]) / 2, 96 + 31 * n3 - this.scro);
                            }
                            this.rd.setColor(this.color2k(100, 100, 100));
                            this.rd.fillRect(327, 77 + 31 * n3 - this.scro, 444, 30);
                            this.rd.setColor(this.color2k(200, 200, 200));
                            if (this.unblockname.equals("")) {
                                this.rd.drawString("Blocked", 337, 96 + 31 * n3 - this.scro);
                            } else {
                                this.rd.drawString("Unblocking...", 337, 96 + 31 * n3 - this.scro);
                            }
                            if (bl3 && this.stringbutton(this.rd, "   Unblock  ", 724, 96 + 31 * n3 - this.scro, 3, n, n2, bl, 0, 0) && this.unblockname.equals("")) {
                                this.unblockname = this.mname[n5];
                            }
                            this.rd.setColor(this.color2k(150, 150, 150));
                            this.rd.drawLine(207, 107 + 31 * n3 - this.scro, 770, 107 + 31 * n3 - this.scro);
                        }
                        ++n3;
                    }
                    this.rd.setColor(this.color2k(205, 205, 205));
                    this.rd.fillRect(207, 46, 582, 30);
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    objectArray = new String[]{"Player Interaction", "Clan Interaction", "Your Clan's Discussion"};
                    nArray = new int[]{207, 390, 368, 207};
                    int[] nArray4 = new int[]{73, 73, 51, 51};
                    for (n4 = 0; n4 < 3; ++n4) {
                        if (this.itab == n4) {
                            this.rd.setColor(this.color2k(230, 230, 230));
                            this.rd.fillPolygon(nArray, nArray4, 4);
                        } else if (n > nArray[0] && n < nArray[2] && n2 > 51 && n2 < 73) {
                            this.rd.setColor(this.color2k(217, 217, 217));
                            this.rd.fillPolygon(nArray, nArray4, 4);
                            if (bl) {
                                this.itab = n4;
                            }
                        }
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawPolygon(nArray, nArray4, 4);
                        this.rd.setColor(this.color2k(40, 40, 40));
                        this.rd.drawString((String)objectArray[n4], nArray[0] + 80 - this.ftm.stringWidth((String)objectArray[n4]) / 2, 67);
                        int n10 = 0;
                        while (n10 < 4) {
                            int n11 = n10++;
                            nArray[n11] = nArray[n11] + 183;
                        }
                    }
                    this.rd.setColor(this.color2k(150, 150, 150));
                    this.rd.drawLine(207, 73, 770, 73);
                    this.rd.setColor(this.color2k(205, 205, 205));
                    this.rd.fillRect(207, 409, 582, 30);
                    this.rd.setColor(this.color2k(150, 150, 150));
                    this.rd.drawLine(207, 411, 770, 411);
                    this.rd.setColor(this.color2k(205, 205, 205));
                    this.rd.fillRect(772, 93, 17, 299);
                    this.rd.setColor(this.color2k(205, 205, 205));
                    this.rd.fillRect(203, 46, 4, 393);
                    if (this.mscro3 == 831 || this.sdist == 0) {
                        if (this.sdist == 0) {
                            this.rd.setColor(this.color2k(205, 205, 205));
                        } else {
                            this.rd.setColor(this.color2k(215, 215, 215));
                        }
                        this.rd.fillRect(772, 76, 17, 17);
                    } else {
                        this.rd.setColor(this.color2k(220, 220, 220));
                        this.rd.fill3DRect(772, 76, 17, 17, true);
                    }
                    if (this.sdist != 0) {
                        this.rd.drawImage(this.xt.asu, 777, 82, null);
                    }
                    if (this.mscro3 == 832 || this.sdist == 0) {
                        if (this.sdist == 0) {
                            this.rd.setColor(this.color2k(205, 205, 205));
                        } else {
                            this.rd.setColor(this.color2k(215, 215, 215));
                        }
                        this.rd.fillRect(772, 392, 17, 17);
                    } else {
                        this.rd.setColor(this.color2k(220, 220, 220));
                        this.rd.fill3DRect(772, 392, 17, 17, true);
                    }
                    if (this.sdist != 0) {
                        this.rd.drawImage(this.xt.asd, 777, 399, null);
                    }
                    if (this.sdist != 0) {
                        if (this.lspos3 != this.spos3) {
                            this.rd.setColor(this.color2k(215, 215, 215));
                            this.rd.fillRect(772, 93 + this.spos3, 17, 31);
                        } else {
                            if (this.mscro3 == 831) {
                                this.rd.setColor(this.color2k(215, 215, 215));
                            }
                            this.rd.fill3DRect(772, 93 + this.spos3, 17, 31, true);
                        }
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawLine(777, 106 + this.spos3, 783, 106 + this.spos3);
                        this.rd.drawLine(777, 108 + this.spos3, 783, 108 + this.spos3);
                        this.rd.drawLine(777, 110 + this.spos3, 783, 110 + this.spos3);
                        if (this.mscro3 > 800 && this.lspos3 != this.spos3) {
                            this.lspos3 = this.spos3;
                        }
                        if (bl && this.openc == 0) {
                            if (this.mscro3 == 825 && n > 772 && n < 789 && n2 > 93 + this.spos3 && n2 < this.spos3 + 124) {
                                this.mscro3 = n2 - this.spos3;
                            }
                            if (this.mscro3 == 825 && n > 770 && n < 791 && n2 > 74 && n2 < 95) {
                                this.mscro3 = 831;
                            }
                            if (this.mscro3 == 825 && n > 770 && n < 791 && n2 > 390 && n2 < 411) {
                                this.mscro3 = 832;
                            }
                            if (this.mscro3 == 825 && n > 772 && n < 789 && n2 > 93 && n2 < 392) {
                                this.mscro3 = 108;
                                this.spos3 = n2 - this.mscro3;
                            }
                            if ((n4 = 2670 / this.sdist) < 1) {
                                n4 = 1;
                            }
                            if (this.mscro3 == 831) {
                                this.spos3 -= n4;
                                if (this.spos3 > 268) {
                                    this.spos3 = 268;
                                }
                                if (this.spos3 < 0) {
                                    this.spos3 = 0;
                                }
                                this.lspos3 = this.spos3;
                            }
                            if (this.mscro3 == 832) {
                                this.spos3 += n4;
                                if (this.spos3 > 268) {
                                    this.spos3 = 268;
                                }
                                if (this.spos3 < 0) {
                                    this.spos3 = 0;
                                }
                                this.lspos3 = this.spos3;
                            }
                            if (this.mscro3 < 800) {
                                this.spos3 = n2 - this.mscro3;
                                if (this.spos3 > 268) {
                                    this.spos3 = 268;
                                }
                                if (this.spos3 < 0) {
                                    this.spos3 = 0;
                                }
                            }
                            if (this.mscro3 == 825) {
                                this.mscro3 = 925;
                            }
                        } else if (this.mscro3 != 825) {
                            this.mscro3 = 825;
                        }
                    }
                } else {
                    this.rd.setColor(this.color2k(205, 205, 205));
                    this.rd.fillRect(207, 46, 582, 30);
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    String[] stringArray = new String[]{"Player Interaction", "Clan Interaction", "Your Clan's Discussion"};
                    objectArray = new int[]{207, 390, 368, 207};
                    nArray = new int[]{73, 73, 51, 51};
                    for (int i = 0; i < 3; ++i) {
                        if (this.itab == i) {
                            this.rd.setColor(this.color2k(230, 230, 230));
                            this.rd.fillPolygon((int[])objectArray, nArray, 4);
                        } else if (n > objectArray[0] && n < objectArray[2] && n2 > 51 && n2 < 73) {
                            this.rd.setColor(this.color2k(217, 217, 217));
                            this.rd.fillPolygon((int[])objectArray, nArray, 4);
                            if (bl) {
                                this.itab = i;
                            }
                        }
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawPolygon((int[])objectArray, nArray, 4);
                        this.rd.setColor(this.color2k(40, 40, 40));
                        this.rd.drawString(stringArray[i], objectArray[0] + 80 - this.ftm.stringWidth(stringArray[i]) / 2, 67);
                        n4 = 0;
                        while (n4 < 4) {
                            int n12 = n4++;
                            objectArray[n12] = objectArray[n12] + 183;
                        }
                    }
                    this.rd.setColor(this.color2k(150, 150, 150));
                    this.rd.drawLine(207, 73, 770, 73);
                    this.rd.setColor(this.color2k(205, 205, 205));
                    this.rd.fillRect(207, 409, 582, 30);
                    this.rd.setColor(this.color2k(150, 150, 150));
                    this.rd.drawLine(207, 411, 770, 411);
                    this.rd.setColor(this.color2k(205, 205, 205));
                    this.rd.fillRect(772, 76, 17, 333);
                    this.rd.setColor(this.color2k(205, 205, 205));
                    this.rd.fillRect(203, 46, 4, 393);
                    if (this.loadmsgs == 0) {
                        this.rd.setFont(new Font("Arial", 1, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("You have not started any conversations yet.", 487 - this.ftm.stringWidth("You have not started any conversations yet.") / 2, 200);
                    }
                    if (this.loadmsgs == -2) {
                        this.rd.setFont(new Font("Arial", 1, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Failed to load conversations, will try again now...", 487 - this.ftm.stringWidth("Failed to load conversations, will try again now...") / 2, 200);
                    }
                    if (this.loadmsgs == -1) {
                        this.rd.setFont(new Font("Arial", 1, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Loading conversations, please wait...", 487 - this.ftm.stringWidth("Loading conversation, please wait...") / 2, 200);
                    }
                }
                if (this.gs.sendtyp.isShowing()) {
                    this.gs.sendtyp.hide();
                    this.gs.sendtyp.select(0);
                    this.flko = 0;
                }
                if (this.gs.senditem.isShowing()) {
                    this.gs.senditem.hide();
                }
                if (this.gs.datat.isShowing()) {
                    this.gs.datat.hide();
                }
            } else {
                int n13;
                int n14;
                this.rd.setColor(this.color2k(240, 240, 240));
                this.rd.fillRoundRect(197, 40, 597, 404, 20, 20);
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawRoundRect(197, 40, 597, 404, 20, 20);
                this.rd.setColor(this.color2k(250, 250, 250));
                this.rd.fillRect(207, 86, 577, 274);
                this.sdist = (int)(((float)this.nml - 14.75f) * 17.0f);
                if (this.sdist < 0) {
                    this.sdist = 0;
                }
                this.scro = (int)((float)this.spos4 / 208.0f * (float)this.sdist);
                if (this.readmsg == 2) {
                    if (this.gs.openm) {
                        this.blockb = 10;
                    } else if (this.blockb != 0) {
                        --this.blockb;
                    }
                    for (n14 = 0; n14 < this.nml; ++n14) {
                        int n15;
                        if (86 + 17 * n14 - this.scro >= 360 || 125 + 17 * n14 - this.scro <= 86 || this.mlinetyp[n14] == 167) continue;
                        this.rd.setColor(new Color(0, 0, 0));
                        if (this.mlinetyp[n14] != 10 && this.mlinetyp[n14] != 20 && this.mlinetyp[n14] != 30) {
                            if (this.mlinetyp[n14] == 0 || this.mlinetyp[n14] == 1 || this.mlinetyp[n14] == 2 || this.mlinetyp[n14] == 3 || this.mlinetyp[n14] == 4) {
                                this.rd.setFont(new Font("Tahoma", 1, 11));
                            } else {
                                this.rd.setFont(new Font("Tahoma", 0, 11));
                            }
                            this.rd.drawString(this.mline[n14], 217, 103 + 17 * n14 - this.scro);
                            if (this.mlinetyp[n14] != 0 && this.mlinetyp[n14] != 1 && this.mlinetyp[n14] != 2 && this.mlinetyp[n14] != 3 && this.mlinetyp[n14] != 4) continue;
                            this.rd.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(this.color2k(125, 125, 125));
                            this.rd.drawString(this.mtimes[n14], 757 - this.ftm.stringWidth(this.mtimes[n14]), 103 + 17 * n14 - this.scro);
                            continue;
                        }
                        if (this.mlinetyp[n14] == 30) {
                            boolean bl4 = true;
                            if (n > 217 && n < 567 && n2 > 93 + n14 * 17 - this.scro && n2 < 123 + n14 * 17 - this.scro && this.blockb == 0) {
                                this.cur = 12;
                                bl4 = false;
                                if (bl) {
                                    if (!this.claname.equals(this.mline[n14])) {
                                        this.claname = this.mline[n14];
                                        this.loadedc = false;
                                    }
                                    this.tab = 3;
                                    this.spos5 = 0;
                                    this.lspos5 = 0;
                                    this.cfase = 3;
                                    this.ctab = 0;
                                }
                            }
                            if (!this.drawl(this.rd, "#" + this.mline[n14] + "#", 217, 93 + n14 * 17 - this.scro, bl4) || !bl4) {
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawRect(217, 93 + n14 * 17 - this.scro, 349, 29);
                                this.rd.setFont(new Font("Arial", 1, 13));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("" + this.mline[n14] + "", 392 - this.ftm.stringWidth("" + this.mline[n14] + "") / 2, 113 + n14 * 17 - this.scro);
                            }
                        }
                        if (this.mlinetyp[n14] == 10) {
                            if (this.cd.acname.equals(this.mline[n14])) {
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                if (this.cd.action == -9) {
                                    this.rd.drawString("Failed to add car!  Unknown error, please try again later.", 217, 109 + 17 * n14 - this.scro);
                                }
                                if (this.cd.action == -8) {
                                    this.rd.drawString("Cannot add more then 20 cars to your account!", 217, 109 + 17 * n14 - this.scro);
                                }
                                if (this.cd.action == 7) {
                                    this.rd.setColor(new Color(94, 170, 0));
                                    this.rd.drawString("[" + this.mline[n14] + "] has been added to your cars!", 217, 109 + 17 * n14 - this.scro);
                                }
                                if (this.cd.action == -7) {
                                    this.rd.drawString("You already have this car.", 217, 109 + 17 * n14 - this.scro);
                                }
                                if (this.cd.action == 6) {
                                    this.rd.drawString("Adding Car...", 217, 109 + 17 * n14 - this.scro);
                                }
                                if (this.cd.action == -6) {
                                    this.rd.setColor(new Color(193, 106, 0));
                                    String string = "Upgrade to a full account to add custom cars!";
                                    n13 = 217;
                                    n15 = n13 + this.ftm.stringWidth(string);
                                    this.rd.drawString(string, n13, 109 + 17 * n14 - this.scro);
                                    if (this.waitlink != -1) {
                                        this.rd.drawLine(n13, 111 + 17 * n14 - this.scro, n15, 111 + 17 * n14 - this.scro);
                                    }
                                    if (n > n13 && n < n15 && n2 > 98 + 17 * n14 - this.scro && n2 < 111 + 17 * n14 - this.scro) {
                                        if (this.waitlink != -1) {
                                            this.cur = 12;
                                        }
                                        if (bl && this.waitlink == 0) {
                                            this.gs.editlink(this.xt.nickname, true);
                                            this.waitlink = -1;
                                        }
                                    }
                                    if (this.waitlink > 0) {
                                        --this.waitlink;
                                    }
                                }
                            }
                            if (this.cd.action == 0 || !this.cd.acname.equals(this.mline[n14])) {
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.rd.drawString("[  " + this.mline[n14] + "  ]", 340, 109 + 17 * n14 - this.scro);
                                if (this.xt.drawcarb(true, null, " Add to My Cars ", 217, 90 + 17 * n14 - this.scro, n, n2, bl && this.blockb == 0)) {
                                    if (this.xt.logged) {
                                        this.cd.action = 6;
                                        this.cd.ac = -1;
                                        this.cd.acname = this.mline[n14];
                                        this.cd.sparkactionloader();
                                    } else {
                                        this.cd.acname = this.mline[n14];
                                        this.cd.action = -6;
                                        this.waitlink = 20;
                                    }
                                }
                            }
                        }
                        if (this.mlinetyp[n14] != 20) continue;
                        if (this.cd.onstage.equals(this.mline[n14])) {
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            if (this.addstage == 2) {
                                this.rd.drawString("Adding stage...", 217, 109 + 17 * n14 - this.scro);
                                if (this.cd.staction == 0) {
                                    this.addstage = 3;
                                }
                                if (this.cd.staction == -2) {
                                    this.addstage = 4;
                                }
                                if (this.cd.staction == -3) {
                                    this.addstage = 5;
                                }
                                if (this.cd.staction == -1) {
                                    this.addstage = 6;
                                }
                            }
                            if (this.addstage == 3) {
                                this.rd.setColor(new Color(94, 170, 0));
                                this.rd.drawString("[" + this.mline[n14] + "] has been added to your stages!", 217, 109 + 17 * n14 - this.scro);
                            }
                            if (this.addstage == 4) {
                                this.rd.drawString("You already have this stage.", 217, 109 + 17 * n14 - this.scro);
                            }
                            if (this.addstage == 5) {
                                this.rd.drawString("Cannot add more then 20 stages to your account!", 217, 109 + 17 * n14 - this.scro);
                            }
                            if (this.addstage == 6) {
                                this.rd.drawString("Failed to add stage!  Unknown error, please try again later.", 217, 109 + 17 * n14 - this.scro);
                            }
                            if (this.addstage == 1) {
                                this.rd.setColor(new Color(193, 106, 0));
                                String string = "Upgrade to a full account to add custom stages!";
                                n13 = 217;
                                n15 = n13 + this.ftm.stringWidth(string);
                                this.rd.drawString(string, n13, 109 + 17 * n14 - this.scro);
                                if (this.waitlink != -1) {
                                    this.rd.drawLine(n13, 111 + 17 * n14 - this.scro, n15, 111 + 17 * n14 - this.scro);
                                }
                                if (n > n13 && n < n15 && n2 > 98 + 17 * n14 - this.scro && n2 < 111 + 17 * n14 - this.scro) {
                                    if (this.waitlink != -1) {
                                        this.cur = 12;
                                    }
                                    if (bl && this.waitlink == 0) {
                                        this.gs.editlink(this.xt.nickname, true);
                                        this.waitlink = -1;
                                    }
                                }
                                if (this.waitlink > 0) {
                                    --this.waitlink;
                                }
                            }
                        }
                        if (this.addstage != 0 && this.cd.onstage.equals(this.mline[n14])) continue;
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.rd.drawString("[  " + this.mline[n14] + "  ]", 355, 109 + 17 * n14 - this.scro);
                        if (!this.xt.drawcarb(true, null, " Add to My Stages ", 217, 90 + 17 * n14 - this.scro, n, n2, bl && this.blockb == 0)) continue;
                        if (this.xt.logged) {
                            this.cd.onstage = this.mline[n14];
                            this.cd.staction = 2;
                            this.cd.sparkstageaction();
                            this.addstage = 2;
                            continue;
                        }
                        this.cd.onstage = this.mline[n14];
                        this.addstage = 1;
                        this.waitlink = 20;
                    }
                }
                if (this.readmsg == 1) {
                    this.rd.setFont(new Font("Tahoma", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Reading...", 487 - this.ftm.stringWidth("Reading...") / 2, 200);
                }
                if (this.readmsg == 3) {
                    this.rd.setFont(new Font("Tahoma", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Failed to fetch and load conversation.", 487 - this.ftm.stringWidth("Failed to fetch and load conversation.") / 2, 200);
                }
                if (this.readmsg == 4) {
                    this.rd.setFont(new Font("Tahoma", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Failed to load conversation, server error, please try again later.", 487 - this.ftm.stringWidth("Failed to load conversation, please try again later.") / 2, 200);
                }
                if (this.readmsg == 5) {
                    this.rd.setFont(new Font("Tahoma", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Failed to send message, server error, please try again later.", 487 - this.ftm.stringWidth("Failed to send message, server error, please try again later.") / 2, 200);
                }
                this.rd.setColor(this.color2k(240, 240, 240));
                this.rd.fillRect(207, 47, 577, 39);
                this.rd.fillRect(207, 360, 577, 39);
                this.rd.setColor(this.color2k(205, 205, 205));
                this.rd.drawLine(207, 86, 783, 86);
                this.rd.drawLine(207, 86, 207, 360);
                this.rd.drawLine(207, 360, 783, 360);
                this.rd.fillRect(767, 104, 17, 239);
                if (this.mscro4 == 831 || this.sdist == 0) {
                    if (this.sdist == 0) {
                        this.rd.setColor(this.color2k(205, 205, 205));
                    } else {
                        this.rd.setColor(this.color2k(215, 215, 215));
                    }
                    this.rd.fillRect(767, 87, 17, 17);
                } else {
                    this.rd.setColor(this.color2k(220, 220, 220));
                    this.rd.fill3DRect(767, 87, 17, 17, true);
                }
                if (this.sdist != 0) {
                    this.rd.drawImage(this.xt.asu, 772, 93, null);
                }
                if (this.mscro4 == 832 || this.sdist == 0) {
                    if (this.sdist == 0) {
                        this.rd.setColor(this.color2k(205, 205, 205));
                    } else {
                        this.rd.setColor(this.color2k(215, 215, 215));
                    }
                    this.rd.fillRect(767, 343, 17, 17);
                } else {
                    this.rd.setColor(this.color2k(220, 220, 220));
                    this.rd.fill3DRect(767, 343, 17, 17, true);
                }
                if (this.sdist != 0) {
                    this.rd.drawImage(this.xt.asd, 772, 350, null);
                }
                if (this.sdist != 0) {
                    if (this.lspos4 != this.spos4) {
                        this.rd.setColor(this.color2k(215, 215, 215));
                        this.rd.fillRect(767, 104 + this.spos4, 17, 31);
                    } else {
                        if (this.mscro4 == 831) {
                            this.rd.setColor(this.color2k(215, 215, 215));
                        }
                        this.rd.fill3DRect(767, 104 + this.spos4, 17, 31, true);
                    }
                    this.rd.setColor(this.color2k(150, 150, 150));
                    this.rd.drawLine(772, 117 + this.spos4, 778, 117 + this.spos4);
                    this.rd.drawLine(772, 119 + this.spos4, 778, 119 + this.spos4);
                    this.rd.drawLine(772, 121 + this.spos4, 778, 121 + this.spos4);
                    if (this.mscro4 > 800 && this.lspos4 != this.spos4) {
                        this.lspos4 = this.spos4;
                    }
                    if (bl) {
                        if (this.mscro4 == 825 && n > 767 && n < 784 && n2 > 104 + this.spos4 && n2 < this.spos4 + 135) {
                            this.mscro4 = n2 - this.spos4;
                        }
                        if (this.mscro4 == 825 && n > 765 && n < 786 && n2 > 85 && n2 < 106) {
                            this.mscro4 = 831;
                        }
                        if (this.mscro4 == 825 && n > 765 && n < 786 && n2 > 341 && n2 < 362) {
                            this.mscro4 = 832;
                        }
                        if (this.mscro4 == 825 && n > 767 && n < 784 && n2 > 104 && n2 < 343) {
                            this.mscro4 = 119;
                            this.spos4 = n2 - this.mscro4;
                        }
                        if ((n14 = 2670 / this.sdist) < 1) {
                            n14 = 1;
                        }
                        if (this.mscro4 == 831) {
                            this.spos4 -= n14;
                            if (this.spos4 > 208) {
                                this.spos4 = 208;
                            }
                            if (this.spos4 < 0) {
                                this.spos4 = 0;
                            }
                            this.lspos4 = this.spos4;
                        }
                        if (this.mscro4 == 832) {
                            this.spos4 += n14;
                            if (this.spos4 > 208) {
                                this.spos4 = 208;
                            }
                            if (this.spos4 < 0) {
                                this.spos4 = 0;
                            }
                            this.lspos4 = this.spos4;
                        }
                        if (this.mscro4 < 800) {
                            this.spos4 = n2 - this.mscro4;
                            if (this.spos4 > 208) {
                                this.spos4 = 208;
                            }
                            if (this.spos4 < 0) {
                                this.spos4 = 0;
                            }
                        }
                        if (this.mscro4 == 825) {
                            this.mscro4 = 925;
                        }
                    } else if (this.mscro4 != 825) {
                        this.mscro4 = 825;
                    }
                }
                this.rd.setFont(new Font("Arial", 1, 12));
                this.ftm = this.rd.getFontMetrics();
                if (!this.drawl(this.rd, this.opname, 207, 47, true)) {
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString(this.opname, 267 - this.ftm.stringWidth(this.opname) / 2, 66);
                    this.rd.setColor(this.color2k(150, 150, 150));
                    this.rd.drawRect(207, 47, 119, 29);
                }
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawString("::  Conversation with " + this.opname + "", 336, 72);
                if (n > 207 && n < 327 && n2 > 47 && n2 < 77) {
                    this.cur = 12;
                    if (bl) {
                        this.tab = 1;
                        if (!this.proname.equals(this.opname)) {
                            this.proname = this.opname;
                            this.loadedp = false;
                            this.onexitpro();
                        }
                    }
                }
                if (this.stringbutton(this.rd, "Block / Ignore", 665, 66, 0, n, n2, bl, 0, 0)) {
                    this.openc = 0;
                    this.blockname = this.opname;
                }
                if (this.stringbutton(this.rd, "Close X", 752, 66, 0, n, n2, bl, 0, 0)) {
                    this.openc = 0;
                    this.readmsg = 0;
                }
                if (!this.gs.sendtyp.isShowing()) {
                    this.gs.sendtyp.show();
                }
                this.gs.sendtyp.move(207, 365);
                if (this.sendmsg != 0) {
                    this.gs.sendtyp.disable();
                } else {
                    this.gs.sendtyp.enable();
                }
                String string = "";
                if (this.gs.sendtyp.getSelectedIndex() == 0) {
                    this.dommsg = true;
                    if (this.loaditem != 0) {
                        this.loaditem = 0;
                    }
                }
                if (this.gs.sendtyp.getSelectedIndex() == 1) {
                    string = "car";
                    this.rd.setFont(new Font("Arial", 0, 12));
                    this.rd.drawString("Send a public car you have or a private car that belongs to you.", 376, 382);
                }
                if (this.gs.sendtyp.getSelectedIndex() == 2) {
                    string = "stage";
                    this.rd.setFont(new Font("Arial", 0, 12));
                    this.rd.drawString("Send a public stage you have or a private stage that belongs to you.", 376, 382);
                }
                if (this.gs.sendtyp.getSelectedIndex() == 3) {
                    this.rd.setFont(new Font("Arial", 0, 12));
                    this.rd.drawString("Send an invitation to " + this.opname + " to join your clan.", 376, 382);
                }
                if (this.gs.sendtyp.getSelectedIndex() == 4) {
                    this.rd.setFont(new Font("Arial", 0, 12));
                    this.rd.drawString("A date that gets converted to the local time of the person previewing it.", 376, 382);
                }
                if (this.itemsel != this.gs.sendtyp.getSelectedIndex()) {
                    this.gs.senditem.hide();
                    this.gs.datat.hide();
                    this.itemsel = this.gs.sendtyp.getSelectedIndex();
                }
                if (this.gs.sendtyp.getSelectedIndex() == 0) {
                    if (this.sendmsg == 0) {
                        if (this.stringbutton(this.rd, "   Send  >  ", 723, 408, 0, n, n2, bl, 0, 0) && !this.gs.mmsg.getText().trim().equals("") && this.gs.mmsg.getText().toLowerCase().indexOf(this.gs.tpass.getText().toLowerCase()) == -1 && this.xt.acexp != -3) {
                            if (!this.xt.msgcheck(this.gs.mmsg.getText())) {
                                this.sendmsg = 1;
                            } else {
                                this.gs.sendtyp.hide();
                                ++this.xt.warning;
                            }
                        }
                    } else {
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Sending...", 723 - this.ftm.stringWidth("Sending...") / 2, 408);
                    }
                }
                if (this.gs.sendtyp.getSelectedIndex() == 1 || this.gs.sendtyp.getSelectedIndex() == 2) {
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("Select " + string + " to share:", 207, 420);
                    if (!this.gs.senditem.isShowing()) {
                        this.gs.senditem.removeAll();
                        if (this.xt.logged) {
                            this.gs.senditem.add(this.rd, "Loading your " + string + " list, please wait...");
                            this.loaditem = this.gs.sendtyp.getSelectedIndex();
                        } else {
                            this.gs.senditem.add(this.rd, "You need to upgrade to have custom " + string + "s!");
                        }
                        this.gs.senditem.select(0);
                        this.gs.senditem.show();
                    }
                    this.gs.senditem.move(207 + this.ftm.stringWidth("Select " + string + " to share:") + 11, 403);
                    if (this.loaditem == 10 && this.gs.sendtyp.getSelectedIndex() == 1 || this.loaditem == 20 && this.gs.sendtyp.getSelectedIndex() == 2 || !this.xt.logged) {
                        if (this.xt.logged) {
                            if (this.sendmsg == 0) {
                                if (this.stringbutton(this.rd, "   Send  >  ", 723, 420, 0, n, n2, bl, 0, 0)) {
                                    this.sendmsg = 1;
                                }
                            } else {
                                this.rd.drawString("Sending...", 723 - this.ftm.stringWidth("Sending...") / 2, 420);
                            }
                        } else {
                            this.rd.setColor(new Color(206, 171, 98));
                            this.rd.fillRoundRect(651, 391, 136, 46, 20, 20);
                            if (this.drawbutton(this.xt.upgrade, 719, 414, n, n2, bl)) {
                                this.gs.editlink(this.xt.nickname, true);
                            }
                        }
                    }
                }
                if (this.gs.sendtyp.getSelectedIndex() == 3) {
                    if (!this.xt.clan.equals("")) {
                        int n16 = 306;
                        n13 = -195;
                        if (!this.drawl(this.rd, "#" + this.xt.clan + "#", 406 + n13, 101 + n16, true)) {
                            this.rd.setFont(new Font("Arial", 1, 13));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString("" + this.xt.clan + "", 581 + n13 - this.ftm.stringWidth("" + this.xt.clan + "") / 2, 121 + n16);
                        }
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        String string2 = "Your Clan";
                        if (n > 402 + n13 && n < 759 + n13 && n2 > 84 + n16 && n2 < 134 + n16) {
                            string2 = "Clan :  " + this.xt.clan + "";
                            this.rd.drawLine(408 + n13, 98 + n16, 408 + n13 + this.ftm.stringWidth(string2), 98 + n16);
                            if (n > 408 + n13 && n < 408 + n13 + this.ftm.stringWidth(string2) && n2 > 85 + n16 && n2 < 100 + n16 || n > 406 + n13 && n < 756 + n13 && n2 > 101 + n16 && n2 < 131 + n16) {
                                this.cur = 12;
                                if (bl && this.sendmsg == 0) {
                                    if (!this.claname.equals(this.xt.clan)) {
                                        this.claname = this.xt.clan;
                                        this.loadedc = false;
                                    }
                                    this.tab = 3;
                                    this.spos5 = 0;
                                    this.lspos5 = 0;
                                    this.cfase = 3;
                                    this.ctab = 0;
                                }
                            }
                        }
                        this.rd.drawString(string2, 408 + n13, 97 + n16);
                        this.rd.drawLine(402 + n13, 84 + n16, 402 + n13, 134 + n16);
                        this.rd.drawLine(402 + n13, 84 + n16, 408 + n13 + this.ftm.stringWidth(string2) + 2, 84 + n16);
                        this.rd.drawLine(408 + n13 + this.ftm.stringWidth(string2) + 2, 84 + n16, 408 + n13 + this.ftm.stringWidth(string2) + 15, 97 + n16);
                        this.rd.drawLine(408 + n13 + this.ftm.stringWidth(string2) + 15, 97 + n16, 759 + n13, 97 + n16);
                        this.rd.drawLine(759 + n13, 97 + n16, 759 + n13, 134 + n16);
                        this.rd.drawLine(402 + n13, 134 + n16, 759 + n13, 134 + n16);
                    } else if (this.xt.logged) {
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        if (this.flko % 4 != 0 || this.flko == 0) {
                            this.rd.drawString("You are not a member of any clan yet!", 376 - this.ftm.stringWidth("You are not a member of any clan yet!") / 2, 417);
                        }
                        if (this.flko != 0) {
                            --this.flko;
                        }
                    } else {
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("You need to upgrade to a full account to participate in NFM clan's activities.", 207, 420);
                        this.rd.setColor(new Color(206, 171, 98));
                        this.rd.fillRoundRect(651, 391, 136, 46, 20, 20);
                        if (this.drawbutton(this.xt.upgrade, 719, 414, n, n2, bl)) {
                            this.gs.editlink(this.xt.nickname, true);
                        }
                    }
                    if (this.xt.logged) {
                        if (this.sendmsg == 0) {
                            if (this.stringbutton(this.rd, "   Send  >  ", 723, 408, 0, n, n2, bl, 0, 0)) {
                                if (!this.xt.clan.equals("")) {
                                    this.sendmsg = 1;
                                } else {
                                    this.flko = 45;
                                }
                            }
                        } else {
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString("Sending...", 723 - this.ftm.stringWidth("Sending...") / 2, 408);
                        }
                    }
                }
                if (this.gs.sendtyp.getSelectedIndex() == 4) {
                    if (!this.gs.senditem.isShowing()) {
                        this.gs.senditem.removeAll();
                        Calendar calendar = Calendar.getInstance();
                        n13 = 0;
                        for (int i = 0; i < 20; ++i) {
                            String string3 = this.wday[calendar.get(7) - 1];
                            if (n13 == 0) {
                                string3 = "Today";
                                n13 = 1;
                            }
                            this.gs.senditem.add(this.rd, "" + string3 + "  -  " + this.month[calendar.get(2)] + " " + calendar.get(5) + "");
                            calendar.roll(5, true);
                        }
                        this.gs.senditem.select(0);
                        this.gs.senditem.show();
                    }
                    if (!this.gs.datat.isShowing()) {
                        this.gs.datat.removeAll();
                        int n17 = 12;
                        String string4 = "PM";
                        for (int i = 0; i < 24; ++i) {
                            this.gs.datat.add(this.rd, "" + n17 + " " + string4 + "");
                            if (++n17 == 12) {
                                string4 = "AM";
                            }
                            if (n17 != 13) continue;
                            n17 = 1;
                        }
                        this.gs.datat.select(0);
                        this.gs.datat.show();
                    }
                    this.gs.senditem.move(300, 395);
                    this.gs.datat.move(491, 395);
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("Date is displayed based on your computer calendar's date/time, please make sure it is correct.", 207, 435);
                    if (this.sendmsg == 0) {
                        if (this.stringbutton(this.rd, "   Send  >  ", 723, 408, 0, n, n2, bl, 0, 0)) {
                            this.sendmsg = 1;
                        }
                    } else {
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Sending...", 723 - this.ftm.stringWidth("Sending...") / 2, 408);
                    }
                }
            }
            if (this.openc >= 1 && this.openc < 10) {
                this.rd.setColor(this.color2k(240, 240, 230));
                this.rd.fillRoundRect(197, this.opy, 597, this.oph, 20, 20);
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawRoundRect(197, this.opy, 597, this.oph, 20, 20);
                if (!this.drawl(this.rd, this.opname, 207, this.opy + 7, true)) {
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString(this.opname, 267 - this.ftm.stringWidth(this.opname) / 2, this.opy + 26);
                    this.rd.setColor(this.color2k(150, 150, 150));
                    this.rd.drawRect(207, this.opy + 7, 119, 29);
                }
                this.opy += this.addopy;
                this.oph += 36;
                ++this.openc;
            }
        }
        if (this.itab == 1) {
            if (this.litab != this.itab) {
                this.spos3 = 0;
                this.gs.senditem.hide();
                this.gs.datat.hide();
                this.gs.sendtyp.removeAll();
                this.gs.sendtyp.add(this.rd, "Write a Message");
                this.gs.sendtyp.add(this.rd, "Share a Relative Date");
                this.gs.sendtyp.add(this.rd, "Battle over Stage");
                this.gs.sendtyp.add(this.rd, "Battle over Car");
                this.gs.sendtyp.add(this.rd, "Declare War");
                if (!this.redif) {
                    this.gs.sendtyp.select(0);
                } else {
                    this.gs.sendtyp.select(this.intsel);
                    if (this.intsel == 4) {
                        this.redif = false;
                    }
                }
                if (this.sendwarnum) {
                    this.gs.sendtyp.sel = this.intsel;
                }
                this.intsel = 0;
                this.litab = this.itab;
            }
            if (!this.xt.clan.equals("")) {
                if (this.openi != 10) {
                    int[] nArray;
                    Object[] objectArray;
                    this.rd.setColor(this.color2k(230, 230, 230));
                    this.rd.fillRoundRect(197, 40, 597, 404, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(197, 40, 597, 404, 20, 20);
                    if (this.loadinter != 0 && this.loadinter != -2 && this.loadinter != -1) {
                        int n18;
                        this.sdist = (this.ni - 10) * 31;
                        if (this.sdist < 0) {
                            this.sdist = 0;
                        }
                        this.scro = (int)((float)this.spos3 / 268.0f * (float)this.sdist);
                        n3 = 0;
                        for (int i = 0; i < this.ni; ++i) {
                            if (76 + 31 * n3 - this.scro < 408 && 107 + 31 * n3 - this.scro > 76) {
                                boolean bl5 = false;
                                if (n > 207 && n < 770 && n2 > 76 + 31 * n3 - this.scro && n2 < 106 + 31 * n3 - this.scro) {
                                    bl5 = true;
                                    this.cur = 12;
                                    if (bl && this.openc == 0) {
                                        this.opy = 70 + 31 * n3 - this.scro;
                                        this.addopy = (40 - this.opy) / 10;
                                        this.oph = 44;
                                        this.openi = 1;
                                        if (!this.intclan.equals(this.iclan[i])) {
                                            this.intclan = this.iclan[i];
                                            this.dispi = 0;
                                            this.nil = 0;
                                            this.lastint = "";
                                            this.readint = 1;
                                        }
                                    }
                                }
                                if (this.icheck[i].toLowerCase().indexOf(this.xt.nickname.toLowerCase()) == -1) {
                                    this.rd.setColor(this.color2k(240, 240, 240));
                                    this.rd.fillRect(207, 77 + 31 * n3 - this.scro, 564, 30);
                                }
                                if (bl5) {
                                    this.rd.setColor(this.color2k(250, 250, 250));
                                    this.rd.fillRect(207, 77 + 31 * n3 - this.scro, 564, 30);
                                }
                                boolean bl6 = this.drawl(this.rd, "#" + this.iclan[i] + "#", 207, 77 + 31 * n3 - this.scro, bl5);
                                if (!bl5 || !bl6) {
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawString(this.iclan[i], 382 - this.ftm.stringWidth(this.iclan[i]) / 2, 96 + 31 * n3 - this.scro);
                                }
                                this.rd.setFont(new Font("Tahoma", 0, 11));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.setColor(this.color2k(125, 125, 125));
                                this.rd.drawString(this.itime[i], 760 - this.ftm.stringWidth(this.itime[i]), 102 + 31 * n3 - this.scro);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawString(this.isub[i], 565, 89 + 31 * n3 - this.scro);
                                this.rd.setFont(new Font("Arial", 1, 11));
                                this.rd.setColor(new Color(117, 67, 0));
                                this.rd.drawString(this.istat[i], 565, 102 + 31 * n3 - this.scro);
                                this.rd.setColor(this.color2k(150, 150, 150));
                                this.rd.drawLine(207, 107 + 31 * n3 - this.scro, 770, 107 + 31 * n3 - this.scro);
                            }
                            ++n3;
                        }
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(207, 46, 582, 30);
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        objectArray = new String[]{"Player Interaction", "Clan Interaction", "Your Clan's Discussion"};
                        nArray = new int[]{207, 390, 368, 207};
                        int[] nArray5 = new int[]{73, 73, 51, 51};
                        for (n18 = 0; n18 < 3; ++n18) {
                            if (this.itab == n18) {
                                this.rd.setColor(this.color2k(230, 230, 230));
                                this.rd.fillPolygon(nArray, nArray5, 4);
                            } else if (n > nArray[0] && n < nArray[2] && n2 > 51 && n2 < 73) {
                                this.rd.setColor(this.color2k(217, 217, 217));
                                this.rd.fillPolygon(nArray, nArray5, 4);
                                if (bl) {
                                    this.itab = n18;
                                }
                            }
                            this.rd.setColor(this.color2k(150, 150, 150));
                            this.rd.drawPolygon(nArray, nArray5, 4);
                            this.rd.setColor(this.color2k(40, 40, 40));
                            this.rd.drawString((String)objectArray[n18], nArray[0] + 80 - this.ftm.stringWidth((String)objectArray[n18]) / 2, 67);
                            int n19 = 0;
                            while (n19 < 4) {
                                int n20 = n19++;
                                nArray[n20] = nArray[n20] + 183;
                            }
                        }
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawLine(207, 73, 770, 73);
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(207, 409, 582, 30);
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawLine(207, 411, 770, 411);
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(772, 93, 17, 299);
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(203, 46, 4, 393);
                        if (this.mscro3 == 831 || this.sdist == 0) {
                            if (this.sdist == 0) {
                                this.rd.setColor(this.color2k(205, 205, 205));
                            } else {
                                this.rd.setColor(this.color2k(215, 215, 215));
                            }
                            this.rd.fillRect(772, 76, 17, 17);
                        } else {
                            this.rd.setColor(this.color2k(220, 220, 220));
                            this.rd.fill3DRect(772, 76, 17, 17, true);
                        }
                        if (this.sdist != 0) {
                            this.rd.drawImage(this.xt.asu, 777, 82, null);
                        }
                        if (this.mscro3 == 832 || this.sdist == 0) {
                            if (this.sdist == 0) {
                                this.rd.setColor(this.color2k(205, 205, 205));
                            } else {
                                this.rd.setColor(this.color2k(215, 215, 215));
                            }
                            this.rd.fillRect(772, 392, 17, 17);
                        } else {
                            this.rd.setColor(this.color2k(220, 220, 220));
                            this.rd.fill3DRect(772, 392, 17, 17, true);
                        }
                        if (this.sdist != 0) {
                            this.rd.drawImage(this.xt.asd, 777, 399, null);
                        }
                        if (this.sdist != 0) {
                            if (this.lspos3 != this.spos3) {
                                this.rd.setColor(this.color2k(215, 215, 215));
                                this.rd.fillRect(772, 93 + this.spos3, 17, 31);
                            } else {
                                if (this.mscro3 == 831) {
                                    this.rd.setColor(this.color2k(215, 215, 215));
                                }
                                this.rd.fill3DRect(772, 93 + this.spos3, 17, 31, true);
                            }
                            this.rd.setColor(this.color2k(150, 150, 150));
                            this.rd.drawLine(777, 106 + this.spos3, 783, 106 + this.spos3);
                            this.rd.drawLine(777, 108 + this.spos3, 783, 108 + this.spos3);
                            this.rd.drawLine(777, 110 + this.spos3, 783, 110 + this.spos3);
                            if (this.mscro3 > 800 && this.lspos3 != this.spos3) {
                                this.lspos3 = this.spos3;
                            }
                            if (bl && this.openc == 0) {
                                if (this.mscro3 == 825 && n > 772 && n < 789 && n2 > 93 + this.spos3 && n2 < this.spos3 + 124) {
                                    this.mscro3 = n2 - this.spos3;
                                }
                                if (this.mscro3 == 825 && n > 770 && n < 791 && n2 > 74 && n2 < 95) {
                                    this.mscro3 = 831;
                                }
                                if (this.mscro3 == 825 && n > 770 && n < 791 && n2 > 390 && n2 < 411) {
                                    this.mscro3 = 832;
                                }
                                if (this.mscro3 == 825 && n > 772 && n < 789 && n2 > 93 && n2 < 392) {
                                    this.mscro3 = 108;
                                    this.spos3 = n2 - this.mscro3;
                                }
                                if ((n18 = 2670 / this.sdist) < 1) {
                                    n18 = 1;
                                }
                                if (this.mscro3 == 831) {
                                    this.spos3 -= n18;
                                    if (this.spos3 > 268) {
                                        this.spos3 = 268;
                                    }
                                    if (this.spos3 < 0) {
                                        this.spos3 = 0;
                                    }
                                    this.lspos3 = this.spos3;
                                }
                                if (this.mscro3 == 832) {
                                    this.spos3 += n18;
                                    if (this.spos3 > 268) {
                                        this.spos3 = 268;
                                    }
                                    if (this.spos3 < 0) {
                                        this.spos3 = 0;
                                    }
                                    this.lspos3 = this.spos3;
                                }
                                if (this.mscro3 < 800) {
                                    this.spos3 = n2 - this.mscro3;
                                    if (this.spos3 > 268) {
                                        this.spos3 = 268;
                                    }
                                    if (this.spos3 < 0) {
                                        this.spos3 = 0;
                                    }
                                }
                                if (this.mscro3 == 825) {
                                    this.mscro3 = 925;
                                }
                            } else if (this.mscro3 != 825) {
                                this.mscro3 = 825;
                            }
                        }
                    } else {
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(207, 46, 582, 30);
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        String[] stringArray = new String[]{"Player Interaction", "Clan Interaction", "Your Clan's Discussion"};
                        objectArray = new int[]{207, 390, 368, 207};
                        nArray = new int[]{73, 73, 51, 51};
                        for (int i = 0; i < 3; ++i) {
                            if (this.itab == i) {
                                this.rd.setColor(this.color2k(230, 230, 230));
                                this.rd.fillPolygon((int[])objectArray, nArray, 4);
                            } else if (n > objectArray[0] && n < objectArray[2] && n2 > 51 && n2 < 73) {
                                this.rd.setColor(this.color2k(217, 217, 217));
                                this.rd.fillPolygon((int[])objectArray, nArray, 4);
                                if (bl) {
                                    this.itab = i;
                                }
                            }
                            this.rd.setColor(this.color2k(150, 150, 150));
                            this.rd.drawPolygon((int[])objectArray, nArray, 4);
                            this.rd.setColor(this.color2k(40, 40, 40));
                            this.rd.drawString(stringArray[i], objectArray[0] + 80 - this.ftm.stringWidth(stringArray[i]) / 2, 67);
                            int n21 = 0;
                            while (n21 < 4) {
                                int n22 = n21++;
                                objectArray[n22] = objectArray[n22] + 183;
                            }
                        }
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawLine(207, 73, 770, 73);
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(207, 409, 582, 30);
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawLine(207, 411, 770, 411);
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(772, 76, 17, 333);
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(203, 46, 4, 393);
                        if (this.loadinter == 0) {
                            this.rd.setFont(new Font("Arial", 1, 11));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString("You have not started any interactions with other clans yet.", 487 - this.ftm.stringWidth("You have not started any interactions with other clans yet.") / 2, 200);
                        }
                        if (this.loadinter == -2) {
                            this.rd.setFont(new Font("Arial", 1, 11));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString("Failed to load interactions, will try again now...", 487 - this.ftm.stringWidth("Failed to load interactions, will try again now...") / 2, 200);
                        }
                        if (this.loadinter == -1) {
                            this.rd.setFont(new Font("Arial", 1, 11));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString("Loading interactions, please wait...", 487 - this.ftm.stringWidth("Loading interactions, please wait...") / 2, 200);
                        }
                    }
                    if (this.gs.sendtyp.isShowing()) {
                        this.gs.sendtyp.hide();
                    }
                    if (this.gs.senditem.isShowing()) {
                        this.gs.senditem.hide();
                    }
                    if (this.gs.datat.isShowing()) {
                        this.gs.datat.hide();
                    }
                    this.gs.ilaps.hide();
                    this.gs.icars.hide();
                    this.gs.sclass.hide();
                    this.gs.sfix.hide();
                    if (this.gs.sendtyp.getSelectedIndex() != 0) {
                        this.gs.sendtyp.select(0);
                        this.intsel = 0;
                    }
                } else {
                    this.rd.setColor(this.color2k(240, 240, 240));
                    this.rd.fillRoundRect(197, 40, 597, 404, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(197, 40, 597, 404, 20, 20);
                    this.rd.setColor(this.color2k(250, 250, 250));
                    this.rd.fillRect(207, 86, 577, 274);
                    if (this.intclanlo.equals(this.intclan) && this.intclanbgloaded) {
                        this.rd.setComposite(AlphaComposite.getInstance(3, 0.1f));
                        this.rd.drawImage(this.intclanbg, 207, 86, null);
                        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    }
                    this.sdist = 0;
                    if ((this.readint == 2 || this.readint == 1) && this.viewgame2 == 0) {
                        this.sdist = (int)(((float)this.nil - 14.75f) * 17.0f);
                        if (this.sdist < 0) {
                            this.sdist = 0;
                        }
                        this.scro = (int)((float)this.spos4 / 208.0f * (float)this.sdist);
                        if (this.gs.openm) {
                            this.blockb = 10;
                        } else if (this.blockb != 0) {
                            --this.blockb;
                        }
                        for (n3 = 0; n3 < this.nil; ++n3) {
                            if (86 + 17 * n3 - this.scro >= 360 || 125 + 17 * n3 - this.scro <= 86 || this.ilinetyp[n3] == 167) continue;
                            this.rd.setColor(new Color(0, 0, 0));
                            if (this.ilinetyp[n3] != 20 && this.ilinetyp[n3] != 30 && this.ilinetyp[n3] != 40 && this.ilinetyp[n3] != 80 && this.ilinetyp[n3] != 90 && this.ilinetyp[n3] != 100) {
                                if (this.ilinetyp[n3] >= 0) {
                                    this.rd.setFont(new Font("Tahoma", 1, 11));
                                } else {
                                    this.rd.setFont(new Font("Tahoma", 0, 11));
                                }
                                this.rd.drawString(this.iline[n3], 217, 103 + 17 * n3 - this.scro);
                                if (this.ilinetyp[n3] < 0) continue;
                                this.rd.setFont(new Font("Tahoma", 0, 11));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.setColor(this.color2k(125, 125, 125));
                                this.rd.drawString(this.itimes[n3], 757 - this.ftm.stringWidth(this.itimes[n3]), 103 + 17 * n3 - this.scro);
                                continue;
                            }
                            if (this.ilinetyp[n3] == 40) {
                                if (this.stringbutton(this.rd, "  View War Declaration  ", 300, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                    this.viewgame2 = 1;
                                    this.nvgames2 = 4;
                                    this.viewwar2 = this.getSvalue(this.iline[n3], 1);
                                    this.ichlng = this.iline[n3].startsWith("I|");
                                }
                                if (!this.iline[n3].endsWith("|out|")) {
                                    if (this.iline[n3].startsWith("Y|") && this.stringbutton(this.rd, "  Accept War  ", 441, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                        this.gs.sendtyp.sel = 7;
                                        this.sendwar = this.getSvalue(this.iline[n3], 1);
                                    }
                                } else {
                                    this.rd.setColor(this.color2k(170, 170, 170));
                                    this.rd.drawString("[ Accepted or interaction replaced. ]", 490 - this.ftm.stringWidth("[ Accepted or interaction replaced. ]") / 2, 112 + 17 * n3 - this.scro);
                                }
                            }
                            if (this.ilinetyp[n3] == 30) {
                                if (this.stringbutton(this.rd, "  View Car Battle  ", 282, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                    this.viewgame2 = 1;
                                    this.nvgames2 = 2;
                                    this.viewwar2 = this.getSvalue(this.iline[n3], 3);
                                    this.ichlng = this.iline[n3].startsWith("I|");
                                }
                                if (!this.iline[n3].endsWith("|out|")) {
                                    if (this.iline[n3].startsWith("Y|") && this.stringbutton(this.rd, "  Accept Battle  ", 410, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                        this.gs.sendtyp.sel = 6;
                                        this.itake = this.getSvalue(this.iline[n3], 1);
                                        this.igive = this.getSvalue(this.iline[n3], 2);
                                        this.sendwar = this.getSvalue(this.iline[n3], 3);
                                    }
                                } else {
                                    this.rd.setColor(this.color2k(170, 170, 170));
                                    this.rd.drawString("[ Accepted or interaction replaced. ]", 454 - this.ftm.stringWidth("[ Accepted or interaction replaced. ]") / 2, 112 + 17 * n3 - this.scro);
                                }
                                this.rd.setFont(new Font("Tahoma", 0, 11));
                                this.ftm = this.rd.getFontMetrics();
                                if (this.stringbutton(this.rd, "  View Car  ", 217 + this.ftm.stringWidth(this.iline[n3 + 2]) + 47, 137 + 17 * n3 - this.scro, 6, n, n2, bl, 0, 0)) {
                                    this.viewcar = this.getSvalue(this.iline[n3], 1);
                                    if (!this.claname.equals(this.intclan)) {
                                        this.claname = this.intclan;
                                        this.loadedc = false;
                                    }
                                    this.spos5 = 0;
                                    this.lspos5 = 0;
                                    this.cfase = 3;
                                    this.loadedcars = -1;
                                    this.loadedcar = 0;
                                    this.ctab = 2;
                                    this.tab = 3;
                                }
                                this.rd.setFont(new Font("Tahoma", 0, 11));
                                this.ftm = this.rd.getFontMetrics();
                                if (this.stringbutton(this.rd, "  View Car  ", 217 + this.ftm.stringWidth(this.iline[n3 + 3]) + 47, 154 + 17 * n3 - this.scro, 6, n, n2, bl, 0, 0)) {
                                    this.viewcar = this.getSvalue(this.iline[n3], 2);
                                    if (!this.claname.equals(this.xt.clan)) {
                                        this.claname = this.xt.clan;
                                        this.loadedc = false;
                                    }
                                    this.spos5 = 0;
                                    this.lspos5 = 0;
                                    this.cfase = 3;
                                    this.loadedcars = -1;
                                    this.loadedcar = 0;
                                    this.ctab = 2;
                                    this.tab = 3;
                                }
                            }
                            if (this.ilinetyp[n3] == 20) {
                                if (this.stringbutton(this.rd, "  View Stage Battle  ", 289, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                    this.viewgame2 = 1;
                                    this.nvgames2 = 2;
                                    this.viewwar2 = this.getSvalue(this.iline[n3], 3);
                                    this.ichlng = this.iline[n3].startsWith("I|");
                                }
                                if (!this.iline[n3].endsWith("|out|")) {
                                    if (this.iline[n3].startsWith("Y|") && this.stringbutton(this.rd, "  Accept Battle  ", 424, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                        this.gs.sendtyp.sel = 5;
                                        this.itake = this.getSvalue(this.iline[n3], 1);
                                        this.igive = this.getSvalue(this.iline[n3], 2);
                                        this.sendwar = this.getSvalue(this.iline[n3], 3);
                                    }
                                } else {
                                    this.rd.setColor(this.color2k(170, 170, 170));
                                    this.rd.drawString("[ Accepted or interaction replaced. ]", 468 - this.ftm.stringWidth("[ Accepted or interaction replaced. ]") / 2, 112 + 17 * n3 - this.scro);
                                }
                                this.rd.setFont(new Font("Tahoma", 0, 11));
                                this.ftm = this.rd.getFontMetrics();
                                if (this.stringbutton(this.rd, "  View Stage  ", 217 + this.ftm.stringWidth(this.iline[n3 + 2]) + 54, 137 + 17 * n3 - this.scro, 6, n, n2, bl, 0, 0)) {
                                    this.viewcar = this.getSvalue(this.iline[n3], 1);
                                    if (!this.claname.equals(this.intclan)) {
                                        this.claname = this.intclan;
                                        this.loadedc = false;
                                    }
                                    this.spos5 = 0;
                                    this.lspos5 = 0;
                                    this.cfase = 3;
                                    this.loadedstages = -1;
                                    this.loadedstage = 0;
                                    this.ctab = 3;
                                    this.tab = 3;
                                }
                                this.rd.setFont(new Font("Tahoma", 0, 11));
                                this.ftm = this.rd.getFontMetrics();
                                if (this.stringbutton(this.rd, "  View Stage  ", 217 + this.ftm.stringWidth(this.iline[n3 + 3]) + 54, 154 + 17 * n3 - this.scro, 6, n, n2, bl, 0, 0)) {
                                    this.viewcar = this.getSvalue(this.iline[n3], 2);
                                    if (!this.claname.equals(this.xt.clan)) {
                                        this.claname = this.xt.clan;
                                        this.loadedc = false;
                                    }
                                    this.spos5 = 0;
                                    this.lspos5 = 0;
                                    this.cfase = 3;
                                    this.loadedstages = -1;
                                    this.loadedstage = 0;
                                    this.ctab = 3;
                                    this.tab = 3;
                                }
                            }
                            if (this.ilinetyp[n3] == 80) {
                                if (this.stringbutton(this.rd, "        View War        ", 284, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                    this.viewgame2 = 1;
                                    this.nvgames2 = 9;
                                    this.viewwar2 = this.getSvalue(this.iline[n3], 0);
                                }
                                if (this.stringbutton(this.rd, "  View Championship  ", 432, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                    this.cfase = 0;
                                    this.ntab = 1;
                                    this.loadwstat = 0;
                                    this.tab = 3;
                                }
                            }
                            if (this.ilinetyp[n3] == 90) {
                                if (this.stringbutton(this.rd, "        View Battle        ", 284, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                    this.viewgame2 = 1;
                                    this.nvgames2 = 5;
                                    this.viewwar2 = this.getSvalue(this.iline[n3], 0);
                                }
                                if (this.stringbutton(this.rd, "        View Car        ", 424, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                    this.viewcar = this.getSvalue(this.iline[n3], 1);
                                    String string = this.getSvalue(this.iline[n3], 2);
                                    if (!this.claname.equals(string)) {
                                        this.claname = string;
                                        this.loadedc = false;
                                    }
                                    this.spos5 = 0;
                                    this.lspos5 = 0;
                                    this.cfase = 3;
                                    this.loadedcars = -1;
                                    this.loadedcar = 0;
                                    this.ctab = 2;
                                    this.tab = 3;
                                }
                            }
                            if (this.ilinetyp[n3] != 100) continue;
                            if (this.stringbutton(this.rd, "        View Battle        ", 284, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                this.viewgame2 = 1;
                                this.nvgames2 = 5;
                                this.viewwar2 = this.getSvalue(this.iline[n3], 0);
                            }
                            if (!this.stringbutton(this.rd, "        View Stage        ", 431, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) continue;
                            this.viewcar = this.getSvalue(this.iline[n3], 1);
                            String string = this.getSvalue(this.iline[n3], 2);
                            if (!this.claname.equals(string)) {
                                this.claname = string;
                                this.loadedc = false;
                            }
                            this.spos5 = 0;
                            this.lspos5 = 0;
                            this.cfase = 3;
                            this.loadedstages = -1;
                            this.loadedstage = 0;
                            this.ctab = 3;
                            this.tab = 3;
                        }
                    }
                    if (this.readint == 1) {
                        this.rd.setColor(this.color2k(240, 240, 240));
                        this.rd.fillRoundRect(387, 140, 200, 30, 20, 20);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawRoundRect(387, 140, 200, 30, 20, 20);
                        this.rd.setFont(new Font("Tahoma", 1, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Reading...", 487 - this.ftm.stringWidth("Reading...") / 2, 160);
                    }
                    if (this.readint == 3) {
                        this.rd.setFont(new Font("Tahoma", 1, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Failed to fetch and load interaction.", 487 - this.ftm.stringWidth("Failed to fetch and load interaction.") / 2, 200);
                    }
                    if (this.readint == 4) {
                        this.rd.setFont(new Font("Tahoma", 1, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Failed to load interaction, server error, please try again later.", 487 - this.ftm.stringWidth("Failed to load interaction, server error, please try again later.") / 2, 200);
                    }
                    if (this.readint == 5) {
                        this.rd.setFont(new Font("Tahoma", 1, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Failed to send interaction, please try again later.", 487 - this.ftm.stringWidth("Failed to send interaction, please try again later.") / 2, 200);
                    }
                    if (this.readint == 6) {
                        this.rd.setFont(new Font("Tahoma", 1, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("The war or battle you are trying to engage has expired or was not found...", 487 - this.ftm.stringWidth("The war or battle you are trying to engage has expired or was not found...") / 2, 200);
                    }
                    this.rd.setColor(this.color2k(240, 240, 240));
                    this.rd.fillRect(207, 47, 577, 39);
                    this.rd.fillRect(207, 360, 577, 70);
                    this.rd.setColor(this.color2k(205, 205, 205));
                    this.rd.drawLine(207, 86, 783, 86);
                    this.rd.drawLine(207, 86, 207, 360);
                    this.rd.drawLine(207, 360, 783, 360);
                    this.rd.fillRect(767, 104, 17, 239);
                    if (this.mscro4 == 831 || this.sdist == 0) {
                        if (this.sdist == 0) {
                            this.rd.setColor(this.color2k(205, 205, 205));
                        } else {
                            this.rd.setColor(this.color2k(215, 215, 215));
                        }
                        this.rd.fillRect(767, 87, 17, 17);
                    } else {
                        this.rd.setColor(this.color2k(220, 220, 220));
                        this.rd.fill3DRect(767, 87, 17, 17, true);
                    }
                    if (this.sdist != 0) {
                        this.rd.drawImage(this.xt.asu, 772, 93, null);
                    }
                    if (this.mscro4 == 832 || this.sdist == 0) {
                        if (this.sdist == 0) {
                            this.rd.setColor(this.color2k(205, 205, 205));
                        } else {
                            this.rd.setColor(this.color2k(215, 215, 215));
                        }
                        this.rd.fillRect(767, 343, 17, 17);
                    } else {
                        this.rd.setColor(this.color2k(220, 220, 220));
                        this.rd.fill3DRect(767, 343, 17, 17, true);
                    }
                    if (this.sdist != 0) {
                        this.rd.drawImage(this.xt.asd, 772, 350, null);
                    }
                    if (this.sdist != 0) {
                        if (this.lspos4 != this.spos4) {
                            this.rd.setColor(this.color2k(215, 215, 215));
                            this.rd.fillRect(767, 104 + this.spos4, 17, 31);
                        } else {
                            if (this.mscro4 == 831) {
                                this.rd.setColor(this.color2k(215, 215, 215));
                            }
                            this.rd.fill3DRect(767, 104 + this.spos4, 17, 31, true);
                        }
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawLine(772, 117 + this.spos4, 778, 117 + this.spos4);
                        this.rd.drawLine(772, 119 + this.spos4, 778, 119 + this.spos4);
                        this.rd.drawLine(772, 121 + this.spos4, 778, 121 + this.spos4);
                        if (this.mscro4 > 800 && this.lspos4 != this.spos4) {
                            this.lspos4 = this.spos4;
                        }
                        if (bl) {
                            if (this.mscro4 == 825 && n > 767 && n < 784 && n2 > 104 + this.spos4 && n2 < this.spos4 + 135) {
                                this.mscro4 = n2 - this.spos4;
                            }
                            if (this.mscro4 == 825 && n > 765 && n < 786 && n2 > 85 && n2 < 106) {
                                this.mscro4 = 831;
                            }
                            if (this.mscro4 == 825 && n > 765 && n < 786 && n2 > 341 && n2 < 362) {
                                this.mscro4 = 832;
                            }
                            if (this.mscro4 == 825 && n > 767 && n < 784 && n2 > 104 && n2 < 343) {
                                this.mscro4 = 119;
                                this.spos4 = n2 - this.mscro4;
                            }
                            if ((n3 = 2670 / this.sdist) < 1) {
                                n3 = 1;
                            }
                            if (this.mscro4 == 831) {
                                this.spos4 -= n3;
                                if (this.spos4 > 208) {
                                    this.spos4 = 208;
                                }
                                if (this.spos4 < 0) {
                                    this.spos4 = 0;
                                }
                                this.lspos4 = this.spos4;
                            }
                            if (this.mscro4 == 832) {
                                this.spos4 += n3;
                                if (this.spos4 > 208) {
                                    this.spos4 = 208;
                                }
                                if (this.spos4 < 0) {
                                    this.spos4 = 0;
                                }
                                this.lspos4 = this.spos4;
                            }
                            if (this.mscro4 < 800) {
                                this.spos4 = n2 - this.mscro4;
                                if (this.spos4 > 208) {
                                    this.spos4 = 208;
                                }
                                if (this.spos4 < 0) {
                                    this.spos4 = 0;
                                }
                            }
                            if (this.mscro4 == 825) {
                                this.mscro4 = 925;
                            }
                        } else if (this.mscro4 != 825) {
                            this.mscro4 = 825;
                        }
                    }
                    if (this.dispi != 0) {
                        n3 = 558;
                        if (this.viewgame2 != 0) {
                            n3 = 577;
                        }
                        this.rd.setColor(this.color2k(220, 220, 220));
                        this.rd.fillRect(207, 86, n3, 41);
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawRect(207, 86, n3, 41);
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.rd.setColor(new Color(0, 0, 0));
                        if (this.dispi == 1) {
                            this.rd.drawString("Car battle with " + this.intclan + " is on!", 215, 101);
                            this.rd.setFont(new Font("Arial", 0, 11));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString("You win: [ " + this.dtcar + " ]", 215, 118);
                            int n23 = 215 + this.ftm.stringWidth("You win: [ " + this.dtcar + " ]") + 44;
                            if (this.stringbutton(this.rd, "View Car", n23, 119, 6, n, n2, bl, 0, 0)) {
                                this.viewcar = this.dtcar;
                                if (!this.claname.equals(this.intclan)) {
                                    this.claname = this.intclan;
                                    this.loadedc = false;
                                }
                                this.spos5 = 0;
                                this.lspos5 = 0;
                                this.cfase = 3;
                                this.loadedcars = -1;
                                this.loadedcar = 0;
                                this.ctab = 2;
                                this.tab = 3;
                            }
                            this.rd.setFont(new Font("Arial", 0, 11));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString("You lose: [ " + this.dgcar + " ]", n23 += 44, 118);
                            if (this.stringbutton(this.rd, "View Car", n23 += this.ftm.stringWidth("You lose: [ " + this.dgcar + " ]") + 44, 119, 6, n, n2, bl, 0, 0)) {
                                this.viewcar = this.dgcar;
                                if (!this.claname.equals(this.xt.clan)) {
                                    this.claname = this.xt.clan;
                                    this.loadedc = false;
                                }
                                this.spos5 = 0;
                                this.lspos5 = 0;
                                this.cfase = 3;
                                this.loadedcars = -1;
                                this.loadedcar = 0;
                                this.ctab = 2;
                                this.tab = 3;
                            }
                            if (this.stringbutton(this.rd, "View Battle", 714, 111, 0, n, n2, bl, 0, 0)) {
                                this.viewgame2 = 1;
                                this.nvgames2 = 5;
                                this.viewwar2 = this.dwarn;
                            }
                        }
                        if (this.dispi == 2) {
                            this.rd.drawString("Stage battle with " + this.intclan + " is on!", 215, 101);
                            this.rd.setFont(new Font("Arial", 0, 11));
                            this.ftm = this.rd.getFontMetrics();
                            String string = this.dtcar;
                            if (string.length() > 10) {
                                string = "" + string.substring(0, 10) + "...";
                            }
                            this.rd.drawString("You win: [ " + string + " ]", 215, 118);
                            int n24 = 215 + this.ftm.stringWidth("You win: [ " + string + " ]") + 51;
                            if (this.stringbutton(this.rd, "View Stage", n24, 119, 6, n, n2, bl, 0, 0)) {
                                this.viewcar = this.dtcar;
                                if (!this.claname.equals(this.intclan)) {
                                    this.claname = this.intclan;
                                    this.loadedc = false;
                                }
                                this.spos5 = 0;
                                this.lspos5 = 0;
                                this.cfase = 3;
                                this.loadedstages = -1;
                                this.loadedstage = 0;
                                this.ctab = 3;
                                this.tab = 3;
                            }
                            n24 += 51;
                            this.rd.setFont(new Font("Arial", 0, 11));
                            this.ftm = this.rd.getFontMetrics();
                            string = this.dgcar;
                            if (string.length() > 10) {
                                string = "" + string.substring(0, 10) + "...";
                            }
                            this.rd.drawString("You lose: [ " + string + " ]", n24, 118);
                            if (this.stringbutton(this.rd, "View Stage", n24 += this.ftm.stringWidth("You lose: [ " + string + " ]") + 51, 119, 6, n, n2, bl, 0, 0)) {
                                this.viewcar = this.dgcar;
                                if (!this.claname.equals(this.xt.clan)) {
                                    this.claname = this.xt.clan;
                                    this.loadedc = false;
                                }
                                this.spos5 = 0;
                                this.lspos5 = 0;
                                this.cfase = 3;
                                this.loadedstages = -1;
                                this.loadedstage = 0;
                                this.ctab = 3;
                                this.tab = 3;
                            }
                            if (this.stringbutton(this.rd, "View Battle", 714, 111, 0, n, n2, bl, 0, 0)) {
                                this.viewgame2 = 1;
                                this.nvgames2 = 5;
                                this.viewwar2 = this.dwarn;
                            }
                        }
                        if (this.dispi == 3) {
                            this.rd.drawString("War between your clan and " + this.intclan + " has started!", 227, 111);
                            if (this.stringbutton(this.rd, "         View War         ", 670, 111, 0, n, n2, bl, 0, 0)) {
                                this.viewgame2 = 1;
                                this.nvgames2 = 9;
                                this.viewwar2 = this.dwarn;
                            }
                        }
                    }
                    if (this.viewgame2 != 0) {
                        this.rd.setColor(this.color2k(210, 210, 210));
                        this.rd.fillRoundRect(204, 127, 583, 230, 20, 20);
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawRoundRect(204, 127, 583, 230, 20, 20);
                        this.rd.setFont(new Font("Tahoma", 1, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        if (this.nvgames2 == 4) {
                            this.rd.drawString("War declaration, your clan " + this.xt.clan + " versus " + this.intclan + ".", 215, 145);
                            if (this.viewgame2 == 2) {
                                if (this.ichlng) {
                                    this.rd.drawString("" + this.intclan + " would create 5 more games and the first clan to win 5 games wins the war!", 215, 210 + this.nvgames2 * 18);
                                } else {
                                    this.rd.drawString("Your clan would create 5 more games and the first clan to win 5 games wins the war!", 215, 210 + this.nvgames2 * 18);
                                }
                            }
                        }
                        if (this.nvgames2 == 2) {
                            this.rd.drawString("Battle, your clan " + this.xt.clan + " versus " + this.intclan + ".", 215, 145);
                            if (this.viewgame2 == 2) {
                                if (this.ichlng) {
                                    this.rd.drawString("" + this.intclan + " would create 3 more games and the first clan to win 3 games wins the battle!", 215, 210 + this.nvgames2 * 18);
                                } else {
                                    this.rd.drawString("Your clan would create 3 more games and the first clan to win 3 games wins the battle!", 215, 210 + this.nvgames2 * 18);
                                }
                            }
                        }
                        if ((this.nvgames2 == 9 || this.nvgames2 == 5) && this.viewgame2 == 2) {
                            this.rd.drawString("" + this.xt.clan + "  " + this.vwscorex + "           |           " + this.intclan + "  " + this.vwscorei + "", 505 - this.ftm.stringWidth("" + this.xt.clan + "  " + this.vwscorex + "           |           " + this.intclan + "  " + this.vwscorei + "") / 2, 145);
                            this.rd.drawRect(320, 131, 370, 19);
                        }
                        if (this.stringbutton(this.rd, "Close X", 749, 148, 3, n, n2, bl, 0, 0)) {
                            this.viewgame2 = 0;
                        }
                        this.rd.setFont(new Font("Tahoma", 1, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        if (this.viewgame2 == 2) {
                            int n25;
                            this.rd.drawString("Game", 246 - this.ftm.stringWidth("Game") / 2, 175);
                            this.rd.drawString("Stage", 412 - this.ftm.stringWidth("Stage") / 2, 175);
                            this.rd.drawString("Laps", 564 - this.ftm.stringWidth("Laps") / 2, 175);
                            this.rd.drawString("Type of Cars", 653 - this.ftm.stringWidth("Type of Cars") / 2, 175);
                            this.rd.drawString("Fixing", 751 - this.ftm.stringWidth("Fixing") / 2, 175);
                            n3 = 1;
                            int n26 = 1;
                            if (this.nvgames2 == 4 || this.nvgames2 == 2) {
                                n3 = 2;
                                n26 = 2;
                            }
                            for (n25 = 0; n25 < this.nvgames2; ++n25) {
                                if (!this.vwinner[n25].equals("")) {
                                    this.rd.setColor(this.color2k(220, 220, 220));
                                    this.rd.fillRect(213, 180 + n25 * 18, 565, 18);
                                    this.rd.setColor(new Color(0, 0, 0));
                                }
                                this.rd.setFont(new Font("Tahoma", 0, 11));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("# " + n3 + "", 246 - this.ftm.stringWidth("# " + n3 + "") / 2, 193 + n25 * 18);
                                n3 += n26;
                                this.rd.drawString(this.vwstages2[n25], 412 - this.ftm.stringWidth(this.vwstages2[n25]) / 2, 193 + n25 * 18);
                                this.rd.drawString("" + this.vwlaps2[n25] + "", 564 - this.ftm.stringWidth("" + this.vwlaps2[n25] + "") / 2, 193 + n25 * 18);
                                String string = "All Cars";
                                if (this.vwcars2[n25] == 2) {
                                    string = "Clan Cars";
                                }
                                if (this.vwcars2[n25] == 3) {
                                    string = "Game Cars";
                                }
                                if (this.vwclass2[n25] == 0) {
                                    string = string + ", All Classes";
                                }
                                if (this.vwclass2[n25] == 1) {
                                    string = string + ", Class C";
                                }
                                if (this.vwclass2[n25] == 2) {
                                    string = string + ", Class B & C";
                                }
                                if (this.vwclass2[n25] == 3) {
                                    string = string + ", Class B";
                                }
                                if (this.vwclass2[n25] == 4) {
                                    string = string + ", Class A & B";
                                }
                                if (this.vwclass2[n25] == 5) {
                                    string = string + ", Class A";
                                }
                                this.rd.drawString(string, 653 - this.ftm.stringWidth(string) / 2, 193 + n25 * 18);
                                String string5 = "Infinite";
                                if (this.vwfix2[n25] == 1) {
                                    string5 = "4 Fixes";
                                }
                                if (this.vwfix2[n25] == 2) {
                                    string5 = "3 Fixes";
                                }
                                if (this.vwfix2[n25] == 3) {
                                    string5 = "2 Fixes";
                                }
                                if (this.vwfix2[n25] == 4) {
                                    string5 = "1 Fix";
                                }
                                if (this.vwfix2[n25] == 5) {
                                    string5 = "No Fixing";
                                }
                                this.rd.drawString(string5, 751 - this.ftm.stringWidth(string5) / 2, 193 + n25 * 18);
                                this.rd.drawRect(213, 180 + n25 * 18, 565, 18);
                            }
                            this.rd.drawLine(213, 162, 213, 180 + this.nvgames2 * 18);
                            this.rd.drawLine(279, 162, 279, 180 + this.nvgames2 * 18);
                            this.rd.drawLine(546, 162, 546, 180 + this.nvgames2 * 18);
                            this.rd.drawLine(583, 162, 583, 180 + this.nvgames2 * 18);
                            this.rd.drawLine(723, 162, 723, 180 + this.nvgames2 * 18);
                            this.rd.drawLine(778, 162, 778, 180 + this.nvgames2 * 18);
                            for (n25 = 0; n25 < this.nvgames2; ++n25) {
                                if (n <= 213 || n >= 778 || n2 <= 180 + n25 * 18 || n2 >= 198 + n25 * 18 || this.vwinner[n25].equals("")) continue;
                                this.rd.setColor(this.color2k(230, 230, 230));
                                this.rd.fillRect(213, 180 + n25 * 18, 565, 18);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.setFont(new Font("Tahoma", 1, 11));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("" + this.vwinner[n25] + "  Wins!", 495 - this.ftm.stringWidth("" + this.vwinner[n25] + "  Wins!") / 2, 193 + n25 * 18);
                                this.rd.drawRect(213, 180 + n25 * 18, 565, 18);
                            }
                        }
                        if (this.viewgame2 == 1) {
                            this.rd.drawString("Loading...", 495 - this.ftm.stringWidth("Loading...") / 2, 242);
                        }
                        if (this.viewgame2 == 3) {
                            if (this.nvgames2 == 4 || this.nvgames2 == 9) {
                                this.rd.drawString("This war has expired and no longer exists.", 495 - this.ftm.stringWidth("This war has expired and no longer exists.") / 2, 232);
                            }
                            if (this.nvgames2 == 2 || this.nvgames2 == 5) {
                                this.rd.drawString("This battle has expired and no longer exists.", 495 - this.ftm.stringWidth("This battle has expired and no longer exists.") / 2, 232);
                            }
                            if (this.nvgames2 == 9 || this.nvgames2 == 5) {
                                this.rd.drawString("(Started/finished wars and battles expire after 180 days.)", 495 - this.ftm.stringWidth("(Started/finished wars and battles expire after 180 days.)") / 2, 252);
                            } else {
                                this.rd.drawString("(Suggestions expire after 90 days.)", 495 - this.ftm.stringWidth("(Suggestions expire after 90 days.)") / 2, 252);
                            }
                        }
                        if (this.viewgame2 == 4) {
                            this.rd.drawString("Error loading games, please try again later...", 495 - this.ftm.stringWidth("Error loading games, please try again later...") / 2, 242);
                        }
                    }
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    if (!this.drawl(this.rd, "#" + this.intclan + "#", 207, 47, true)) {
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString(this.intclan, 382 - this.ftm.stringWidth(this.intclan) / 2, 66);
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawRect(207, 47, 349, 29);
                    }
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("::  Versus your clan", 566, 72);
                    if (n > 207 && n < 557 && n2 > 47 && n2 < 77) {
                        this.cur = 12;
                        if (bl) {
                            if (!this.claname.equals(this.intclan)) {
                                this.claname = this.intclan;
                                this.loadedc = false;
                            }
                            this.spos5 = 0;
                            this.lspos5 = 0;
                            this.cfase = 3;
                            this.ctab = 0;
                            this.tab = 3;
                        }
                    }
                    if (this.stringbutton(this.rd, "Close X", 752, 66, 0, n, n2, bl, 0, 0)) {
                        this.openi = 0;
                        this.readint = 0;
                        this.viewgame2 = 0;
                    }
                    if (this.gs.sendtyp.getSelectedIndex() < 5) {
                        if (!this.gs.sendtyp.isShowing()) {
                            this.gs.sendtyp.show();
                        }
                        this.gs.sendtyp.move(207, 365);
                    } else {
                        this.gs.sendtyp.hide();
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.rd.setColor(new Color(0, 0, 0));
                        if (this.gs.sendtyp.getSelectedIndex() == 5) {
                            this.rd.drawString("::  Accept Stage Battle", 207, 382);
                        }
                        if (this.gs.sendtyp.getSelectedIndex() == 6) {
                            this.rd.drawString("::  Accept Car Battle", 207, 382);
                        }
                        if (this.gs.sendtyp.getSelectedIndex() == 7) {
                            this.rd.drawString("::  Accept War", 207, 382);
                        }
                        if (this.stringbutton(this.rd, "  Cancel  ", 742, 382, 3, n, n2, bl, 0, 0)) {
                            this.gs.sendtyp.select(0);
                        }
                    }
                    if (this.sendint != 0) {
                        this.gs.sendtyp.disable();
                    } else {
                        this.gs.sendtyp.enable();
                    }
                    if (this.intsel != this.gs.sendtyp.getSelectedIndex()) {
                        this.gs.senditem.hide();
                        this.gs.datat.hide();
                        this.gs.ilaps.hide();
                        this.gs.icars.hide();
                        this.gs.sclass.hide();
                        this.gs.sfix.hide();
                        this.gs.senditem.enable();
                        this.gs.datat.enable();
                        this.gs.ilaps.enable();
                        this.gs.icars.enable();
                        this.gs.sclass.enable();
                        this.gs.sfix.enable();
                        this.intsel = this.gs.sendtyp.getSelectedIndex();
                        this.inishsel = true;
                    }
                    if (this.gs.sendtyp.getSelectedIndex() == 0) {
                        this.dommsg = true;
                        if (this.sendint == 0) {
                            if (this.stringbutton(this.rd, "   Send  >  ", 723, 408, 0, n, n2, bl, 0, 0) && !this.gs.mmsg.getText().trim().equals("") && this.gs.mmsg.getText().toLowerCase().indexOf(this.gs.tpass.getText().toLowerCase()) == -1 && this.xt.acexp != -3) {
                                if (!this.xt.msgcheck(this.gs.mmsg.getText())) {
                                    this.sendint = 1;
                                } else {
                                    this.gs.sendtyp.hide();
                                    ++this.xt.warning;
                                }
                            }
                        } else {
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString("Sending...", 723 - this.ftm.stringWidth("Sending...") / 2, 408);
                        }
                    }
                    if (this.gs.sendtyp.getSelectedIndex() == 1) {
                        this.rd.setFont(new Font("Arial", 0, 12));
                        this.rd.drawString("A date that gets converted to the local time of any person previewing it.", 376, 382);
                        if (!this.gs.senditem.isShowing()) {
                            this.gs.senditem.removeAll();
                            Calendar calendar = Calendar.getInstance();
                            boolean bl7 = false;
                            for (int i = 0; i < 20; ++i) {
                                String string = this.wday[calendar.get(7) - 1];
                                if (!bl7) {
                                    string = "Today";
                                    bl7 = true;
                                }
                                this.gs.senditem.add(this.rd, "" + string + "  -  " + this.month[calendar.get(2)] + " " + calendar.get(5) + "");
                                calendar.roll(5, true);
                            }
                            this.gs.senditem.select(0);
                            this.gs.senditem.show();
                        }
                        if (!this.gs.datat.isShowing()) {
                            this.gs.datat.removeAll();
                            int n27 = 12;
                            String string = "PM";
                            for (int i = 0; i < 24; ++i) {
                                this.gs.datat.add(this.rd, "" + n27 + " " + string + "");
                                if (++n27 == 12) {
                                    string = "AM";
                                }
                                if (n27 != 13) continue;
                                n27 = 1;
                            }
                            this.gs.datat.select(0);
                            this.gs.datat.show();
                        }
                        this.gs.senditem.move(300, 395);
                        this.gs.datat.move(491, 395);
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Date is displayed based on your computer calendar's date/time, please make sure it is correct.", 207, 435);
                        if (this.sendint == 0) {
                            if (this.stringbutton(this.rd, "   Send  >  ", 723, 408, 0, n, n2, bl, 0, 0)) {
                                this.sendint = 1;
                            }
                        } else {
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString("Sending...", 723 - this.ftm.stringWidth("Sending...") / 2, 408);
                        }
                    }
                    if (this.gs.sendtyp.getSelectedIndex() == 2) {
                        if (this.inishsel) {
                            if (this.redif) {
                                this.ifas = 1;
                                this.gs.datat.removeAll();
                                for (int i = 0; i < this.gs.clcars.getItemCount(); ++i) {
                                    this.gs.datat.add(this.rd, this.gs.clcars.getItem(i));
                                }
                                this.gs.datat.select(this.gs.clcars.getSelectedIndex());
                                this.redif = false;
                            } else {
                                this.ifas = 0;
                                this.gs.datat.removeAll();
                                this.gs.datat.add(this.rd, "Loading " + this.intclan + "'s stages, please wait...");
                            }
                            this.imsg = "Battle over a stage that belongs to " + this.intclan + " to take it.";
                            this.iflk = 0;
                            if (this.sendwarnum) {
                                this.ifas = 4;
                                this.sendint = 1;
                                this.gs.senditem.disable();
                                this.gs.datat.disable();
                                this.gs.ilaps.disable();
                                this.gs.icars.disable();
                                this.gs.sclass.disable();
                                this.gs.sfix.disable();
                            }
                        }
                        this.rd.setFont(new Font("Arial", 0, 12));
                        if (this.iflk % 3 != 0 || this.iflk == 0) {
                            this.rd.drawString(this.imsg, 376, 382);
                        }
                        if (this.iflk != 0) {
                            --this.iflk;
                        }
                        if (this.ifas == 0 || this.ifas == 1) {
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.rd.drawString("Choose the stage of " + this.intclan + " you want to take to your clan, if you win!", 207, 402);
                            if (!this.gs.datat.isShowing()) {
                                this.gs.datat.show();
                            }
                            this.gs.datat.move(495 - this.gs.datat.getWidth() / 2, 410);
                        }
                        if (this.ifas == 2 || this.ifas == 3) {
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.rd.drawString("Choose a stage of your clan that you would give to " + this.intclan + ", if you lose!", 207, 402);
                            if (!this.gs.datat.isShowing()) {
                                this.gs.datat.show();
                            }
                            this.gs.datat.move(495 - this.gs.datat.getWidth() / 2, 410);
                        }
                        if (this.ifas == 4 || this.ifas == 5) {
                            if (this.ifas == 4) {
                                this.isel = 0;
                                this.gs.senditem.removeAll();
                                this.gs.senditem.add(this.rd, " NFM Multiplayer ");
                                this.gs.senditem.add(this.rd, " NFM 2     ");
                                this.gs.senditem.add(this.rd, " NFM 1     ");
                                this.gs.senditem.add(this.rd, " Clan Stages ");
                                this.gs.senditem.select(0);
                                this.gs.datat.removeAll();
                                this.gs.datat.add(this.rd, "Select Stage");
                                for (int i = 0; i < 5; ++i) {
                                    this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                                }
                                this.gs.datat.select(0);
                                this.gs.ilaps.hide();
                                this.gs.icars.hide();
                                this.gs.sclass.hide();
                                this.gs.sfix.hide();
                                this.ifas = 5;
                            }
                            this.rd.setFont(new Font("Arial", 0, 12));
                            if (this.iflk % 3 != 0 || this.iflk == 0) {
                                this.rd.drawString(this.imsg, 376, 382);
                            }
                            if (this.iflk != 0) {
                                --this.iflk;
                            }
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.rd.drawString("Game #" + (this.wag * 2 + 2) + " :", 207, 407);
                            if (!this.gs.senditem.isShowing()) {
                                this.gs.senditem.show();
                            }
                            this.gs.senditem.move(280, 390);
                            if (!this.gs.datat.isShowing()) {
                                this.gs.datat.show();
                            }
                            this.gs.datat.move(286 + this.gs.senditem.getWidth(), 390);
                            int n28 = 207;
                            if (!this.gs.ilaps.isShowing()) {
                                this.gs.ilaps.show();
                                this.gs.ilaps.select(0);
                            }
                            this.gs.ilaps.move(n28, 415);
                            n28 += 6 + this.gs.ilaps.getWidth();
                            if (!this.gs.icars.isShowing()) {
                                this.gs.icars.show();
                                this.gs.icars.select(0);
                            }
                            this.gs.icars.move(n28, 415);
                            n28 += 6 + this.gs.icars.getWidth();
                            if (!this.gs.sclass.isShowing()) {
                                this.gs.sclass.show();
                                this.gs.sclass.select(0);
                            }
                            this.gs.sclass.move(n28, 415);
                            this.gs.sclass.revup = true;
                            n28 += 6 + this.gs.sclass.getWidth();
                            if (!this.gs.sfix.isShowing()) {
                                this.gs.sfix.show();
                                this.gs.sfix.select(0);
                            }
                            this.gs.sfix.move(n28, 415);
                            this.gs.sfix.revup = true;
                            this.gs.datat.setSize(n28 + this.gs.sfix.getWidth() - 286 - this.gs.senditem.getWidth(), 22);
                            if (this.gs.senditem.getSelectedIndex() == 0 && this.isel != 0) {
                                this.gs.datat.removeAll();
                                this.gs.datat.add(this.rd, "Select Stage");
                                for (int i = 0; i < 5; ++i) {
                                    this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                                }
                                this.gs.datat.select(0);
                                this.isel = 0;
                            }
                            if (this.gs.senditem.getSelectedIndex() == 1 && this.isel != 1) {
                                this.gs.datat.removeAll();
                                this.gs.datat.add(this.rd, "Select Stage");
                                for (int i = 0; i < 17; ++i) {
                                    this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                                }
                                this.gs.datat.select(0);
                                this.isel = 1;
                            }
                            if (this.gs.senditem.getSelectedIndex() == 2 && this.isel != 2) {
                                this.gs.datat.removeAll();
                                this.gs.datat.add(this.rd, "Select Stage");
                                for (int i = 0; i < 10; ++i) {
                                    this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                                }
                                this.gs.datat.select(0);
                                this.isel = 2;
                            }
                            if (this.gs.senditem.getSelectedIndex() == 3 && this.isel < 3) {
                                this.gs.datat.removeAll();
                                this.gs.datat.add(this.rd, "Loading stages, please wait...");
                                this.gs.datat.select(0);
                                this.isel = 3;
                            }
                        }
                        if (this.sendint == 0) {
                            String string = "  Next >  ";
                            if (this.ifas == 5 && this.wag == 1) {
                                string = "   Done   ";
                            }
                            if (this.stringbutton(this.rd, string, 742, 417, 0, n, n2, bl, 0, 0)) {
                                if (this.ifas == 4 || this.ifas == 5) {
                                    if (this.gs.datat.getSelectedIndex() != 0) {
                                        if (this.gs.ilaps.getSelectedIndex() != 0) {
                                            if (this.gs.icars.getSelectedIndex() != 0) {
                                                if (this.gs.senditem.getSelectedIndex() == 0) {
                                                    this.wstages[this.wag] = "#" + (this.gs.datat.getSelectedIndex() + 27) + "";
                                                }
                                                if (this.gs.senditem.getSelectedIndex() == 1) {
                                                    this.wstages[this.wag] = "#" + (this.gs.datat.getSelectedIndex() + 10) + "";
                                                }
                                                if (this.gs.senditem.getSelectedIndex() == 2) {
                                                    this.wstages[this.wag] = "#" + this.gs.datat.getSelectedIndex() + "";
                                                }
                                                if (this.gs.senditem.getSelectedIndex() == 3) {
                                                    this.wstages[this.wag] = "" + this.gs.datat.getSelectedItem() + "";
                                                }
                                                this.wlaps[this.wag] = this.gs.ilaps.getSelectedIndex();
                                                this.wcars[this.wag] = this.gs.icars.getSelectedIndex();
                                                this.wclass[this.wag] = this.gs.sclass.getSelectedIndex();
                                                this.wfix[this.wag] = this.gs.sfix.getSelectedIndex();
                                                ++this.wag;
                                                if (this.wag < 2) {
                                                    this.ifas = 4;
                                                    this.imsg = "Create second game.";
                                                } else {
                                                    --this.wag;
                                                    this.sendint = 1;
                                                    this.gs.senditem.disable();
                                                    this.gs.datat.disable();
                                                    this.gs.ilaps.disable();
                                                    this.gs.icars.disable();
                                                    this.gs.sclass.disable();
                                                    this.gs.sfix.disable();
                                                }
                                            } else {
                                                this.imsg = "Please choose a type of cars for this game!";
                                                this.iflk = 40;
                                            }
                                        } else {
                                            this.imsg = "Please select a number of laps!";
                                            this.iflk = 40;
                                        }
                                    } else {
                                        this.imsg = "Please select a stage!";
                                        this.iflk = 40;
                                    }
                                }
                                if (this.ifas == 2 || this.ifas == 3) {
                                    if (this.gs.datat.getSelectedIndex() == 0) {
                                        this.imsg = "Please choose a stage to give to " + this.intclan + " if you lose!";
                                        this.iflk = 40;
                                    } else {
                                        this.imsg = "Create 2 games and " + this.intclan + " will create another 3.";
                                        this.iflk = 0;
                                        this.igive = this.gs.datat.getSelectedItem();
                                        this.ifas = 4;
                                        this.wag = 0;
                                    }
                                }
                                if (this.ifas == 0 || this.ifas == 1) {
                                    if (this.gs.datat.getSelectedIndex() == 0) {
                                        this.imsg = "Please choose a stage to battle over!";
                                        this.iflk = 40;
                                    } else {
                                        this.imsg = "Battle over a stage that belongs to " + this.intclan + " to take it.";
                                        this.iflk = 0;
                                        this.itake = this.gs.datat.getSelectedItem();
                                        this.gs.datat.removeAll();
                                        this.gs.datat.add(this.rd, "Loading your clan's stages, please wait...");
                                        this.ifas = 2;
                                    }
                                }
                            }
                        } else {
                            this.imsg = "Sending stage battle, pleas wait...";
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString("Sending...", 742 - this.ftm.stringWidth("Sending...") / 2, 417);
                        }
                    }
                    if (this.gs.sendtyp.getSelectedIndex() == 3) {
                        if (this.inishsel) {
                            if (this.redif) {
                                this.ifas = 1;
                                this.gs.datat.removeAll();
                                for (int i = 0; i < this.gs.clcars.getItemCount(); ++i) {
                                    this.gs.datat.add(this.rd, this.gs.clcars.getItem(i));
                                }
                                this.gs.datat.select(this.gs.clcars.getSelectedIndex());
                                this.redif = false;
                            } else {
                                this.ifas = 0;
                                this.gs.datat.removeAll();
                                this.gs.datat.add(this.rd, "Loading " + this.intclan + "'s cars, please wait...");
                            }
                            this.imsg = "Battle over a car that belongs to " + this.intclan + " to take it.";
                            this.iflk = 0;
                            if (this.sendwarnum) {
                                this.ifas = 4;
                                this.sendint = 1;
                                this.gs.senditem.disable();
                                this.gs.datat.disable();
                                this.gs.ilaps.disable();
                                this.gs.icars.disable();
                                this.gs.sclass.disable();
                                this.gs.sfix.disable();
                            }
                        }
                        this.rd.setFont(new Font("Arial", 0, 12));
                        if (this.iflk % 3 != 0 || this.iflk == 0) {
                            this.rd.drawString(this.imsg, 376, 382);
                        }
                        if (this.iflk != 0) {
                            --this.iflk;
                        }
                        if (this.ifas == 0 || this.ifas == 1) {
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.rd.drawString("Choose the car of " + this.intclan + " you want to take to your clan, if you win!", 207, 402);
                            if (!this.gs.datat.isShowing()) {
                                this.gs.datat.show();
                            }
                            this.gs.datat.move(495 - this.gs.datat.getWidth() / 2, 410);
                        }
                        if (this.ifas == 2 || this.ifas == 3) {
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.rd.drawString("Choose a car of your clan that you would give to " + this.intclan + ", if you lose!", 207, 402);
                            if (!this.gs.datat.isShowing()) {
                                this.gs.datat.show();
                            }
                            this.gs.datat.move(495 - this.gs.datat.getWidth() / 2, 410);
                        }
                        if (this.ifas == 4 || this.ifas == 5) {
                            if (this.ifas == 4) {
                                this.isel = 0;
                                this.gs.senditem.removeAll();
                                this.gs.senditem.add(this.rd, " NFM Multiplayer ");
                                this.gs.senditem.add(this.rd, " NFM 2     ");
                                this.gs.senditem.add(this.rd, " NFM 1     ");
                                this.gs.senditem.add(this.rd, " Clan Stages ");
                                this.gs.senditem.select(0);
                                this.gs.datat.removeAll();
                                this.gs.datat.add(this.rd, "Select Stage");
                                for (int i = 0; i < 5; ++i) {
                                    this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                                }
                                this.gs.datat.select(0);
                                this.gs.ilaps.hide();
                                this.gs.icars.hide();
                                this.gs.sclass.hide();
                                this.gs.sfix.hide();
                                this.ifas = 5;
                            }
                            this.rd.setFont(new Font("Arial", 0, 12));
                            if (this.iflk % 3 != 0 || this.iflk == 0) {
                                this.rd.drawString(this.imsg, 376, 382);
                            }
                            if (this.iflk != 0) {
                                --this.iflk;
                            }
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.rd.drawString("Game #" + (this.wag * 2 + 2) + " :", 207, 407);
                            if (!this.gs.senditem.isShowing()) {
                                this.gs.senditem.show();
                            }
                            this.gs.senditem.move(280, 390);
                            if (!this.gs.datat.isShowing()) {
                                this.gs.datat.show();
                            }
                            this.gs.datat.move(286 + this.gs.senditem.getWidth(), 390);
                            int n29 = 207;
                            if (!this.gs.ilaps.isShowing()) {
                                this.gs.ilaps.show();
                                this.gs.ilaps.select(0);
                            }
                            this.gs.ilaps.move(n29, 415);
                            n29 += 6 + this.gs.ilaps.getWidth();
                            if (!this.gs.icars.isShowing()) {
                                this.gs.icars.show();
                                this.gs.icars.select(0);
                            }
                            this.gs.icars.move(n29, 415);
                            n29 += 6 + this.gs.icars.getWidth();
                            if (!this.gs.sclass.isShowing()) {
                                this.gs.sclass.show();
                                this.gs.sclass.select(0);
                            }
                            this.gs.sclass.move(n29, 415);
                            this.gs.sclass.revup = true;
                            n29 += 6 + this.gs.sclass.getWidth();
                            if (!this.gs.sfix.isShowing()) {
                                this.gs.sfix.show();
                                this.gs.sfix.select(0);
                            }
                            this.gs.sfix.move(n29, 415);
                            this.gs.sfix.revup = true;
                            this.gs.datat.setSize(n29 + this.gs.sfix.getWidth() - 286 - this.gs.senditem.getWidth(), 22);
                            if (this.gs.senditem.getSelectedIndex() == 0 && this.isel != 0) {
                                this.gs.datat.removeAll();
                                this.gs.datat.add(this.rd, "Select Stage");
                                for (int i = 0; i < 5; ++i) {
                                    this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                                }
                                this.gs.datat.select(0);
                                this.isel = 0;
                            }
                            if (this.gs.senditem.getSelectedIndex() == 1 && this.isel != 1) {
                                this.gs.datat.removeAll();
                                this.gs.datat.add(this.rd, "Select Stage");
                                for (int i = 0; i < 17; ++i) {
                                    this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                                }
                                this.gs.datat.select(0);
                                this.isel = 1;
                            }
                            if (this.gs.senditem.getSelectedIndex() == 2 && this.isel != 2) {
                                this.gs.datat.removeAll();
                                this.gs.datat.add(this.rd, "Select Stage");
                                for (int i = 0; i < 10; ++i) {
                                    this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                                }
                                this.gs.datat.select(0);
                                this.isel = 2;
                            }
                            if (this.gs.senditem.getSelectedIndex() == 3 && this.isel < 3) {
                                this.gs.datat.removeAll();
                                this.gs.datat.add(this.rd, "Loading stages, please wait...");
                                this.gs.datat.select(0);
                                this.isel = 3;
                            }
                        }
                        if (this.sendint == 0) {
                            String string = "  Next >  ";
                            if (this.ifas == 5 && this.wag == 1) {
                                string = "   Done   ";
                            }
                            if (this.stringbutton(this.rd, string, 742, 417, 0, n, n2, bl, 0, 0)) {
                                if (this.ifas == 4 || this.ifas == 5) {
                                    if (this.gs.datat.getSelectedIndex() != 0) {
                                        if (this.gs.ilaps.getSelectedIndex() != 0) {
                                            if (this.gs.icars.getSelectedIndex() != 0) {
                                                if (this.gs.senditem.getSelectedIndex() == 0) {
                                                    this.wstages[this.wag] = "#" + (this.gs.datat.getSelectedIndex() + 27) + "";
                                                }
                                                if (this.gs.senditem.getSelectedIndex() == 1) {
                                                    this.wstages[this.wag] = "#" + (this.gs.datat.getSelectedIndex() + 10) + "";
                                                }
                                                if (this.gs.senditem.getSelectedIndex() == 2) {
                                                    this.wstages[this.wag] = "#" + this.gs.datat.getSelectedIndex() + "";
                                                }
                                                if (this.gs.senditem.getSelectedIndex() == 3) {
                                                    this.wstages[this.wag] = "" + this.gs.datat.getSelectedItem() + "";
                                                }
                                                this.wlaps[this.wag] = this.gs.ilaps.getSelectedIndex();
                                                this.wcars[this.wag] = this.gs.icars.getSelectedIndex();
                                                this.wclass[this.wag] = this.gs.sclass.getSelectedIndex();
                                                this.wfix[this.wag] = this.gs.sfix.getSelectedIndex();
                                                ++this.wag;
                                                if (this.wag < 2) {
                                                    this.ifas = 4;
                                                    this.imsg = "Create second game.";
                                                } else {
                                                    --this.wag;
                                                    this.sendint = 1;
                                                    this.gs.senditem.disable();
                                                    this.gs.datat.disable();
                                                    this.gs.ilaps.disable();
                                                    this.gs.icars.disable();
                                                    this.gs.sclass.disable();
                                                    this.gs.sfix.disable();
                                                }
                                            } else {
                                                this.imsg = "Please choose a type of cars for this game!";
                                                this.iflk = 40;
                                            }
                                        } else {
                                            this.imsg = "Please select a number of laps!";
                                            this.iflk = 40;
                                        }
                                    } else {
                                        this.imsg = "Please select a stage!";
                                        this.iflk = 40;
                                    }
                                }
                                if (this.ifas == 2 || this.ifas == 3) {
                                    if (this.gs.datat.getSelectedIndex() == 0) {
                                        this.imsg = "Please choose a car to give to " + this.intclan + " if you lose!";
                                        this.iflk = 40;
                                    } else {
                                        this.imsg = "Create 2 games and " + this.intclan + " will create another 3.";
                                        this.iflk = 0;
                                        this.igive = this.gs.datat.getSelectedItem();
                                        this.ifas = 4;
                                        this.wag = 0;
                                    }
                                }
                                if (this.ifas == 0 || this.ifas == 1) {
                                    if (this.gs.datat.getSelectedIndex() == 0) {
                                        this.imsg = "Please choose a car to battle over!";
                                        this.iflk = 40;
                                    } else {
                                        this.imsg = "Battle over a car that belongs to " + this.intclan + " to take it.";
                                        this.iflk = 0;
                                        this.itake = this.gs.datat.getSelectedItem();
                                        this.gs.datat.removeAll();
                                        this.gs.datat.add(this.rd, "Loading your clan's cars, please wait...");
                                        this.ifas = 2;
                                    }
                                }
                            }
                        } else {
                            this.imsg = "Sending car battle, pleas wait...";
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString("Sending...", 742 - this.ftm.stringWidth("Sending...") / 2, 417);
                        }
                    }
                    if (this.gs.sendtyp.getSelectedIndex() == 4) {
                        if (this.inishsel || this.redif) {
                            this.isel = 0;
                            this.gs.senditem.removeAll();
                            this.gs.senditem.add(this.rd, " NFM Multiplayer ");
                            this.gs.senditem.add(this.rd, " NFM 2     ");
                            this.gs.senditem.add(this.rd, " NFM 1     ");
                            this.gs.senditem.add(this.rd, " Clan Stages ");
                            this.gs.senditem.select(0);
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Select Stage");
                            for (int i = 0; i < 5; ++i) {
                                this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                            }
                            this.gs.datat.select(0);
                            if (!this.redif) {
                                this.wag = 0;
                                this.imsg = "Create 4 games and " + this.intclan + " will create another 5.";
                                this.iflk = 0;
                            } else {
                                this.imsg = "Create next game.";
                                if (this.wag == 3) {
                                    this.imsg = "Create last game.";
                                }
                                this.iflk = 0;
                                this.gs.ilaps.hide();
                                this.gs.icars.hide();
                                this.gs.sclass.hide();
                                this.gs.sfix.hide();
                            }
                            if (this.sendwarnum) {
                                this.sendint = 1;
                                this.gs.senditem.disable();
                                this.gs.datat.disable();
                                this.gs.ilaps.disable();
                                this.gs.icars.disable();
                                this.gs.sclass.disable();
                                this.gs.sfix.disable();
                            }
                            this.redif = false;
                        }
                        this.rd.setFont(new Font("Arial", 0, 12));
                        if (this.iflk % 3 != 0 || this.iflk == 0) {
                            this.rd.drawString(this.imsg, 376, 382);
                        }
                        if (this.iflk != 0) {
                            --this.iflk;
                        }
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.rd.drawString("Game #" + (this.wag * 2 + 2) + " :", 207, 407);
                        if (!this.gs.senditem.isShowing()) {
                            this.gs.senditem.show();
                        }
                        this.gs.senditem.move(280, 390);
                        if (!this.gs.datat.isShowing()) {
                            this.gs.datat.show();
                        }
                        this.gs.datat.move(286 + this.gs.senditem.getWidth(), 390);
                        int n30 = 207;
                        if (!this.gs.ilaps.isShowing()) {
                            this.gs.ilaps.show();
                            this.gs.ilaps.select(0);
                        }
                        this.gs.ilaps.move(n30, 415);
                        n30 += 6 + this.gs.ilaps.getWidth();
                        if (!this.gs.icars.isShowing()) {
                            this.gs.icars.show();
                            this.gs.icars.select(0);
                        }
                        this.gs.icars.move(n30, 415);
                        n30 += 6 + this.gs.icars.getWidth();
                        if (!this.gs.sclass.isShowing()) {
                            this.gs.sclass.show();
                            this.gs.sclass.select(0);
                        }
                        this.gs.sclass.move(n30, 415);
                        this.gs.sclass.revup = true;
                        n30 += 6 + this.gs.sclass.getWidth();
                        if (!this.gs.sfix.isShowing()) {
                            this.gs.sfix.show();
                            this.gs.sfix.select(0);
                        }
                        this.gs.sfix.move(n30, 415);
                        this.gs.sfix.revup = true;
                        this.gs.datat.setSize(n30 + this.gs.sfix.getWidth() - 286 - this.gs.senditem.getWidth(), 22);
                        if (this.gs.senditem.getSelectedIndex() == 0 && this.isel != 0) {
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Select Stage");
                            for (int i = 0; i < 5; ++i) {
                                this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                            }
                            this.gs.datat.select(0);
                            this.isel = 0;
                        }
                        if (this.gs.senditem.getSelectedIndex() == 1 && this.isel != 1) {
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Select Stage");
                            for (int i = 0; i < 17; ++i) {
                                this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                            }
                            this.gs.datat.select(0);
                            this.isel = 1;
                        }
                        if (this.gs.senditem.getSelectedIndex() == 2 && this.isel != 2) {
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Select Stage");
                            for (int i = 0; i < 10; ++i) {
                                this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                            }
                            this.gs.datat.select(0);
                            this.isel = 2;
                        }
                        if (this.gs.senditem.getSelectedIndex() == 3 && this.isel < 3) {
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Loading stages, please wait...");
                            this.gs.datat.select(0);
                            this.isel = 3;
                        }
                        if (this.sendint == 0) {
                            String string = "  Next >  ";
                            if (this.wag == 3) {
                                string = "   Done   ";
                            }
                            if (this.stringbutton(this.rd, string, 742, 417, 0, n, n2, bl, 0, 0)) {
                                if (this.gs.datat.getSelectedIndex() != 0) {
                                    if (this.gs.ilaps.getSelectedIndex() != 0) {
                                        if (this.gs.icars.getSelectedIndex() != 0) {
                                            if (this.gs.senditem.getSelectedIndex() == 0) {
                                                this.wstages[this.wag] = "#" + (this.gs.datat.getSelectedIndex() + 27) + "";
                                            }
                                            if (this.gs.senditem.getSelectedIndex() == 1) {
                                                this.wstages[this.wag] = "#" + (this.gs.datat.getSelectedIndex() + 10) + "";
                                            }
                                            if (this.gs.senditem.getSelectedIndex() == 2) {
                                                this.wstages[this.wag] = "#" + this.gs.datat.getSelectedIndex() + "";
                                            }
                                            if (this.gs.senditem.getSelectedIndex() == 3) {
                                                this.wstages[this.wag] = "" + this.gs.datat.getSelectedItem() + "";
                                            }
                                            this.wlaps[this.wag] = this.gs.ilaps.getSelectedIndex();
                                            this.wcars[this.wag] = this.gs.icars.getSelectedIndex();
                                            this.wclass[this.wag] = this.gs.sclass.getSelectedIndex();
                                            this.wfix[this.wag] = this.gs.sfix.getSelectedIndex();
                                            ++this.wag;
                                            if (this.wag < 4) {
                                                this.redif = true;
                                            } else {
                                                --this.wag;
                                                this.sendint = 1;
                                                this.gs.senditem.disable();
                                                this.gs.datat.disable();
                                                this.gs.ilaps.disable();
                                                this.gs.icars.disable();
                                                this.gs.sclass.disable();
                                                this.gs.sfix.disable();
                                            }
                                        } else {
                                            this.imsg = "Please choose a type of cars for this game!";
                                            this.iflk = 40;
                                        }
                                    } else {
                                        this.imsg = "Please select a number of laps!";
                                        this.iflk = 40;
                                    }
                                } else {
                                    this.imsg = "Please select a stage!";
                                    this.iflk = 40;
                                }
                            }
                        } else {
                            this.imsg = "Sending war declaration, pleas wait...";
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString("Sending...", 742 - this.ftm.stringWidth("Sending...") / 2, 417);
                        }
                    }
                    if (this.gs.sendtyp.getSelectedIndex() == 5 || this.gs.sendtyp.getSelectedIndex() == 6) {
                        if (this.inishsel || this.redif) {
                            this.isel = 0;
                            this.gs.senditem.removeAll();
                            this.gs.senditem.add(this.rd, " NFM Multiplayer ");
                            this.gs.senditem.add(this.rd, " NFM 2     ");
                            this.gs.senditem.add(this.rd, " NFM 1     ");
                            this.gs.senditem.add(this.rd, " Clan Stages ");
                            this.gs.senditem.select(0);
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Select Stage");
                            for (int i = 0; i < 5; ++i) {
                                this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                            }
                            this.gs.datat.select(0);
                            if (!this.redif) {
                                this.wag = 0;
                                this.imsg = "Create 3 games to be added to the battle.";
                                this.iflk = 0;
                            } else {
                                this.imsg = "Create next game.";
                                if (this.wag == 2) {
                                    this.imsg = "Create last game.";
                                }
                                this.iflk = 0;
                                this.gs.ilaps.hide();
                                this.gs.icars.hide();
                                this.gs.sclass.hide();
                                this.gs.sfix.hide();
                            }
                            if (this.sendwarnum) {
                                this.sendint = 1;
                                this.gs.senditem.disable();
                                this.gs.datat.disable();
                                this.gs.ilaps.disable();
                                this.gs.icars.disable();
                                this.gs.sclass.disable();
                                this.gs.sfix.disable();
                            }
                            this.redif = false;
                        }
                        this.rd.setFont(new Font("Arial", 0, 12));
                        if (this.iflk % 3 != 0 || this.iflk == 0) {
                            this.rd.drawString(this.imsg, 350, 382);
                        }
                        if (this.iflk != 0) {
                            --this.iflk;
                        }
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.rd.drawString("Game #" + (this.wag * 2 + 1) + " :", 207, 407);
                        if (!this.gs.senditem.isShowing()) {
                            this.gs.senditem.show();
                        }
                        this.gs.senditem.move(280, 390);
                        if (!this.gs.datat.isShowing()) {
                            this.gs.datat.show();
                        }
                        this.gs.datat.move(286 + this.gs.senditem.getWidth(), 390);
                        int n31 = 207;
                        if (!this.gs.ilaps.isShowing()) {
                            this.gs.ilaps.show();
                            this.gs.ilaps.select(0);
                        }
                        this.gs.ilaps.move(n31, 415);
                        n31 += 6 + this.gs.ilaps.getWidth();
                        if (!this.gs.icars.isShowing()) {
                            this.gs.icars.show();
                            this.gs.icars.select(0);
                        }
                        this.gs.icars.move(n31, 415);
                        n31 += 6 + this.gs.icars.getWidth();
                        if (!this.gs.sclass.isShowing()) {
                            this.gs.sclass.show();
                            this.gs.sclass.select(0);
                        }
                        this.gs.sclass.move(n31, 415);
                        this.gs.sclass.revup = true;
                        n31 += 6 + this.gs.sclass.getWidth();
                        if (!this.gs.sfix.isShowing()) {
                            this.gs.sfix.show();
                            this.gs.sfix.select(0);
                        }
                        this.gs.sfix.move(n31, 415);
                        this.gs.sfix.revup = true;
                        this.gs.datat.setSize(n31 + this.gs.sfix.getWidth() - 286 - this.gs.senditem.getWidth(), 22);
                        if (this.gs.senditem.getSelectedIndex() == 0 && this.isel != 0) {
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Select Stage");
                            for (int i = 0; i < 5; ++i) {
                                this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                            }
                            this.gs.datat.select(0);
                            this.isel = 0;
                        }
                        if (this.gs.senditem.getSelectedIndex() == 1 && this.isel != 1) {
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Select Stage");
                            for (int i = 0; i < 17; ++i) {
                                this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                            }
                            this.gs.datat.select(0);
                            this.isel = 1;
                        }
                        if (this.gs.senditem.getSelectedIndex() == 2 && this.isel != 2) {
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Select Stage");
                            for (int i = 0; i < 10; ++i) {
                                this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                            }
                            this.gs.datat.select(0);
                            this.isel = 2;
                        }
                        if (this.gs.senditem.getSelectedIndex() == 3 && this.isel < 3) {
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Loading stages, please wait...");
                            this.gs.datat.select(0);
                            this.isel = 3;
                        }
                        if (this.sendint == 0) {
                            String string = "  Next >  ";
                            if (this.wag == 2) {
                                string = "   Done   ";
                            }
                            if (this.stringbutton(this.rd, string, 742, 417, 0, n, n2, bl, 0, 0)) {
                                if (this.gs.datat.getSelectedIndex() != 0) {
                                    if (this.gs.ilaps.getSelectedIndex() != 0) {
                                        if (this.gs.icars.getSelectedIndex() != 0) {
                                            if (this.gs.senditem.getSelectedIndex() == 0) {
                                                this.wstages[this.wag] = "#" + (this.gs.datat.getSelectedIndex() + 27) + "";
                                            }
                                            if (this.gs.senditem.getSelectedIndex() == 1) {
                                                this.wstages[this.wag] = "#" + (this.gs.datat.getSelectedIndex() + 10) + "";
                                            }
                                            if (this.gs.senditem.getSelectedIndex() == 2) {
                                                this.wstages[this.wag] = "#" + this.gs.datat.getSelectedIndex() + "";
                                            }
                                            if (this.gs.senditem.getSelectedIndex() == 3) {
                                                this.wstages[this.wag] = "" + this.gs.datat.getSelectedItem() + "";
                                            }
                                            this.wlaps[this.wag] = this.gs.ilaps.getSelectedIndex();
                                            this.wcars[this.wag] = this.gs.icars.getSelectedIndex();
                                            this.wclass[this.wag] = this.gs.sclass.getSelectedIndex();
                                            this.wfix[this.wag] = this.gs.sfix.getSelectedIndex();
                                            ++this.wag;
                                            if (this.wag < 3) {
                                                this.redif = true;
                                            } else {
                                                --this.wag;
                                                this.sendint = 1;
                                                this.gs.senditem.disable();
                                                this.gs.datat.disable();
                                                this.gs.ilaps.disable();
                                                this.gs.icars.disable();
                                                this.gs.sclass.disable();
                                                this.gs.sfix.disable();
                                            }
                                        } else {
                                            this.imsg = "Please choose a type of cars for this game!";
                                            this.iflk = 40;
                                        }
                                    } else {
                                        this.imsg = "Please select a number of laps!";
                                        this.iflk = 40;
                                    }
                                } else {
                                    this.imsg = "Please select a stage!";
                                    this.iflk = 40;
                                }
                            }
                        } else {
                            this.imsg = "Sending war declaration, pleas wait...";
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString("Sending...", 742 - this.ftm.stringWidth("Sending...") / 2, 417);
                        }
                    }
                    if (this.gs.sendtyp.getSelectedIndex() == 7) {
                        if (this.inishsel || this.redif) {
                            this.isel = 0;
                            this.gs.senditem.removeAll();
                            this.gs.senditem.add(this.rd, " NFM Multiplayer ");
                            this.gs.senditem.add(this.rd, " NFM 2     ");
                            this.gs.senditem.add(this.rd, " NFM 1     ");
                            this.gs.senditem.add(this.rd, " Clan Stages ");
                            this.gs.senditem.select(0);
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Select Stage");
                            for (int i = 0; i < 5; ++i) {
                                this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                            }
                            this.gs.datat.select(0);
                            if (!this.redif) {
                                this.wag = 0;
                                this.imsg = "Create 5 games to be added to the war.";
                                this.iflk = 0;
                            } else {
                                this.imsg = "Create next game.";
                                if (this.wag == 4) {
                                    this.imsg = "Create last game.";
                                }
                                this.iflk = 0;
                                this.gs.ilaps.hide();
                                this.gs.icars.hide();
                                this.gs.sclass.hide();
                                this.gs.sfix.hide();
                            }
                            if (this.sendwarnum) {
                                this.sendint = 1;
                                this.gs.senditem.disable();
                                this.gs.datat.disable();
                                this.gs.ilaps.disable();
                                this.gs.icars.disable();
                                this.gs.sclass.disable();
                                this.gs.sfix.disable();
                            }
                            this.redif = false;
                        }
                        this.rd.setFont(new Font("Arial", 0, 12));
                        if (this.iflk % 3 != 0 || this.iflk == 0) {
                            this.rd.drawString(this.imsg, 350, 382);
                        }
                        if (this.iflk != 0) {
                            --this.iflk;
                        }
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.rd.drawString("Game #" + (this.wag * 2 + 1) + " :", 207, 407);
                        if (!this.gs.senditem.isShowing()) {
                            this.gs.senditem.show();
                        }
                        this.gs.senditem.move(280, 390);
                        if (!this.gs.datat.isShowing()) {
                            this.gs.datat.show();
                        }
                        this.gs.datat.move(286 + this.gs.senditem.getWidth(), 390);
                        int n32 = 207;
                        if (!this.gs.ilaps.isShowing()) {
                            this.gs.ilaps.show();
                            this.gs.ilaps.select(0);
                        }
                        this.gs.ilaps.move(n32, 415);
                        n32 += 6 + this.gs.ilaps.getWidth();
                        if (!this.gs.icars.isShowing()) {
                            this.gs.icars.show();
                            this.gs.icars.select(0);
                        }
                        this.gs.icars.move(n32, 415);
                        n32 += 6 + this.gs.icars.getWidth();
                        if (!this.gs.sclass.isShowing()) {
                            this.gs.sclass.show();
                            this.gs.sclass.select(0);
                        }
                        this.gs.sclass.move(n32, 415);
                        this.gs.sclass.revup = true;
                        n32 += 6 + this.gs.sclass.getWidth();
                        if (!this.gs.sfix.isShowing()) {
                            this.gs.sfix.show();
                            this.gs.sfix.select(0);
                        }
                        this.gs.sfix.move(n32, 415);
                        this.gs.sfix.revup = true;
                        this.gs.datat.setSize(n32 + this.gs.sfix.getWidth() - 286 - this.gs.senditem.getWidth(), 22);
                        if (this.gs.senditem.getSelectedIndex() == 0 && this.isel != 0) {
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Select Stage");
                            for (int i = 0; i < 5; ++i) {
                                this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                            }
                            this.gs.datat.select(0);
                            this.isel = 0;
                        }
                        if (this.gs.senditem.getSelectedIndex() == 1 && this.isel != 1) {
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Select Stage");
                            for (int i = 0; i < 17; ++i) {
                                this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                            }
                            this.gs.datat.select(0);
                            this.isel = 1;
                        }
                        if (this.gs.senditem.getSelectedIndex() == 2 && this.isel != 2) {
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Select Stage");
                            for (int i = 0; i < 10; ++i) {
                                this.gs.datat.add(this.rd, " Stage " + (i + 1) + "");
                            }
                            this.gs.datat.select(0);
                            this.isel = 2;
                        }
                        if (this.gs.senditem.getSelectedIndex() == 3 && this.isel < 3) {
                            this.gs.datat.removeAll();
                            this.gs.datat.add(this.rd, "Loading stages, please wait...");
                            this.gs.datat.select(0);
                            this.isel = 3;
                        }
                        if (this.sendint == 0) {
                            String string = "  Next >  ";
                            if (this.wag == 4) {
                                string = "   Done   ";
                            }
                            if (this.stringbutton(this.rd, string, 742, 417, 0, n, n2, bl, 0, 0)) {
                                if (this.gs.datat.getSelectedIndex() != 0) {
                                    if (this.gs.ilaps.getSelectedIndex() != 0) {
                                        if (this.gs.icars.getSelectedIndex() != 0) {
                                            if (this.gs.senditem.getSelectedIndex() == 0) {
                                                this.wstages[this.wag] = "#" + (this.gs.datat.getSelectedIndex() + 27) + "";
                                            }
                                            if (this.gs.senditem.getSelectedIndex() == 1) {
                                                this.wstages[this.wag] = "#" + (this.gs.datat.getSelectedIndex() + 10) + "";
                                            }
                                            if (this.gs.senditem.getSelectedIndex() == 2) {
                                                this.wstages[this.wag] = "#" + this.gs.datat.getSelectedIndex() + "";
                                            }
                                            if (this.gs.senditem.getSelectedIndex() == 3) {
                                                this.wstages[this.wag] = "" + this.gs.datat.getSelectedItem() + "";
                                            }
                                            this.wlaps[this.wag] = this.gs.ilaps.getSelectedIndex();
                                            this.wcars[this.wag] = this.gs.icars.getSelectedIndex();
                                            this.wclass[this.wag] = this.gs.sclass.getSelectedIndex();
                                            this.wfix[this.wag] = this.gs.sfix.getSelectedIndex();
                                            ++this.wag;
                                            if (this.wag < 5) {
                                                this.redif = true;
                                            } else {
                                                --this.wag;
                                                this.sendint = 1;
                                                this.gs.senditem.disable();
                                                this.gs.datat.disable();
                                                this.gs.ilaps.disable();
                                                this.gs.icars.disable();
                                                this.gs.sclass.disable();
                                                this.gs.sfix.disable();
                                            }
                                        } else {
                                            this.imsg = "Please choose a type of cars for this game!";
                                            this.iflk = 40;
                                        }
                                    } else {
                                        this.imsg = "Please select a number of laps!";
                                        this.iflk = 40;
                                    }
                                } else {
                                    this.imsg = "Please select a stage!";
                                    this.iflk = 40;
                                }
                            }
                        } else {
                            this.imsg = "Sending war declaration, pleas wait...";
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString("Sending...", 742 - this.ftm.stringWidth("Sending...") / 2, 417);
                        }
                    }
                    if (this.inishsel) {
                        this.inishsel = false;
                    }
                }
                if (this.openi >= 1 && this.openi < 10) {
                    this.rd.setColor(this.color2k(240, 240, 230));
                    this.rd.fillRoundRect(197, this.opy, 597, this.oph, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(197, this.opy, 597, this.oph, 20, 20);
                    if (!this.drawl(this.rd, "#" + this.intclan + "#", 207, this.opy + 7, true)) {
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString(this.intclan, 382 - this.ftm.stringWidth(this.intclan) / 2, this.opy + 26);
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawRect(207, this.opy + 7, 349, 29);
                    }
                    this.opy += this.addopy;
                    this.oph += 36;
                    ++this.openi;
                }
            } else {
                this.rd.setColor(this.color2k(230, 230, 230));
                this.rd.fillRoundRect(197, 40, 597, 404, 20, 20);
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawRoundRect(197, 40, 597, 404, 20, 20);
                if (this.xt.logged) {
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("You are not a member of any clan.  You need to join a clan first to have access to this.", 487 - this.ftm.stringWidth("You are not a member of any clan.  You need to join a clan first to have access to this.") / 2, 200);
                    if (this.stringbutton(this.rd, "   Find a clan to join   ", 487, 230, 1, n, n2, bl, 0, 0)) {
                        this.tab = 3;
                        this.cfase = 2;
                        this.em = 1;
                        this.msg = "Clan Search";
                        this.smsg = "Listing clans with recent activity...";
                        this.nclns = 0;
                        this.spos5 = 0;
                        this.lspos5 = 0;
                        this.flko = 0;
                    }
                } else {
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("You are currently using a trial account.", 495 - this.ftm.stringWidth("You are currently using a trial account.") / 2, 180);
                    this.rd.drawString("You need to upgrade to be able participate in NFM clan's activities.", 495 - this.ftm.stringWidth("You need to upgrade to be able participate in NFM clan's activities.") / 2, 200);
                    this.rd.setColor(new Color(206, 171, 98));
                    this.rd.fillRoundRect(405, 223, 180, 50, 20, 20);
                    if (this.drawbutton(this.xt.upgrade, 495, 248, n, n2, bl)) {
                        this.gs.editlink(this.xt.nickname, true);
                    }
                }
                this.rd.setColor(this.color2k(205, 205, 205));
                this.rd.fillRect(207, 46, 582, 30);
                this.rd.setFont(new Font("Arial", 1, 12));
                this.ftm = this.rd.getFontMetrics();
                String[] stringArray = new String[]{"Player Interaction", "Clan Interaction", "Your Clan's Discussion"};
                int[] nArray = new int[]{207, 390, 368, 207};
                int[] nArray6 = new int[]{73, 73, 51, 51};
                for (int i = 0; i < 3; ++i) {
                    if (this.itab == i) {
                        this.rd.setColor(this.color2k(230, 230, 230));
                        this.rd.fillPolygon(nArray, nArray6, 4);
                    } else if (n > nArray[0] && n < nArray[2] && n2 > 51 && n2 < 73) {
                        this.rd.setColor(this.color2k(217, 217, 217));
                        this.rd.fillPolygon(nArray, nArray6, 4);
                        if (bl) {
                            this.itab = i;
                        }
                    }
                    this.rd.setColor(this.color2k(150, 150, 150));
                    this.rd.drawPolygon(nArray, nArray6, 4);
                    this.rd.setColor(this.color2k(40, 40, 40));
                    this.rd.drawString(stringArray[i], nArray[0] + 80 - this.ftm.stringWidth(stringArray[i]) / 2, 67);
                    int n33 = 0;
                    while (n33 < 4) {
                        int n34 = n33++;
                        nArray[n34] = nArray[n34] + 183;
                    }
                }
                this.rd.setColor(this.color2k(150, 150, 150));
                this.rd.drawLine(207, 73, 770, 73);
                this.rd.setColor(this.color2k(205, 205, 205));
                this.rd.fillRect(207, 409, 582, 30);
                this.rd.setColor(this.color2k(150, 150, 150));
                this.rd.drawLine(207, 411, 770, 411);
                this.rd.setColor(this.color2k(205, 205, 205));
                this.rd.fillRect(772, 76, 17, 333);
                this.rd.setColor(this.color2k(205, 205, 205));
                this.rd.fillRect(203, 46, 4, 393);
            }
        }
        if (this.itab == 2) {
            int n35;
            if (this.litab != this.itab) {
                if (this.readclan > 0) {
                    this.spos3 = 219;
                }
                this.gs.senditem.hide();
                this.gs.datat.hide();
                this.gs.ilaps.hide();
                this.gs.icars.hide();
                this.gs.sclass.hide();
                this.gs.sfix.hide();
                this.gs.senditem.enable();
                this.gs.datat.enable();
                this.gs.ilaps.enable();
                this.gs.icars.enable();
                this.gs.sclass.enable();
                this.gs.sfix.enable();
                this.gs.sendtyp.removeAll();
                this.gs.sendtyp.add(this.rd, "Write a Message");
                this.gs.sendtyp.add(this.rd, "Share a Relative Date");
                this.gs.sendtyp.select(0);
                this.litab = this.itab;
            }
            this.rd.setColor(this.color2k(230, 230, 230));
            this.rd.fillRoundRect(197, 40, 597, 404, 20, 20);
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawRoundRect(197, 40, 597, 404, 20, 20);
            if (!this.xt.clan.equals("")) {
                this.rd.setColor(this.color2k(250, 250, 250));
                this.rd.fillRect(207, 76, 565, 300);
            }
            if (this.loadedmyclanbg == 1) {
                this.rd.setComposite(AlphaComposite.getInstance(3, 0.1f));
                this.rd.drawImage(this.myclanbg, 207, 76, 565, 300, null);
                this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
            }
            this.rd.setColor(new Color(0, 0, 0));
            this.sdist = 0;
            if (!this.xt.clan.equals("")) {
                if (this.gs.openm) {
                    this.blockb = 10;
                } else if (this.blockb != 0) {
                    --this.blockb;
                }
                if ((this.readclan > 0 || this.readclan == -3) && this.viewgame1 == 0) {
                    this.sdist = (int)(((float)this.cnml - 14.75f) * 17.0f);
                    if (this.sdist < 0) {
                        this.sdist = 0;
                    }
                    this.scro = (int)((float)this.spos3 / 219.0f * (float)this.sdist);
                    for (n3 = 0; n3 < this.cnml; ++n3) {
                        if (86 + 17 * n3 - this.scro >= 360 || 125 + 17 * n3 - this.scro <= 76 || this.cmlinetyp[n3] == 167) continue;
                        if (this.cmlinetyp[n3] != 20 && this.cmlinetyp[n3] != 30 && this.cmlinetyp[n3] != 40 && this.cmlinetyp[n3] != 50 && this.cmlinetyp[n3] != 60 && this.cmlinetyp[n3] != 70 && this.cmlinetyp[n3] != 80) {
                            this.rd.setColor(new Color(0, 0, 0));
                            if (this.cmlinetyp[n3] >= 0) {
                                this.rd.setFont(new Font("Tahoma", 1, 11));
                            } else {
                                this.rd.setFont(new Font("Tahoma", 0, 11));
                            }
                            this.rd.drawString(this.cmline[n3], 217, 103 + 17 * n3 - this.scro);
                            if (this.cmlinetyp[n3] < 0) continue;
                            this.rd.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.setColor(this.color2k(125, 125, 125));
                            this.rd.drawString(this.cmtimes[n3], 757 - this.ftm.stringWidth(this.cmtimes[n3]), 103 + 17 * n3 - this.scro);
                            continue;
                        }
                        if (this.cmlinetyp[n3] == 20 || this.cmlinetyp[n3] == 50) {
                            if (this.stringbutton(this.rd, "  View Clan  ", 267, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                String string = this.getSvalue(this.cmline[n3], 0);
                                if (!this.claname.equals(string)) {
                                    this.claname = string;
                                    this.loadedc = false;
                                }
                                this.spos5 = 0;
                                this.lspos5 = 0;
                                this.cfase = 3;
                                this.ctab = 0;
                                this.tab = 3;
                            }
                            if (this.stringbutton(this.rd, "  View War Suggestion  ", 403, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                this.viewgame1 = 1;
                                this.nvgames1 = this.cmlinetyp[n3] == 20 ? 4 : 9;
                                this.xclan = this.getSvalue(this.cmline[n3], 0);
                                this.viewwar1 = this.getSvalue(this.cmline[n3], 1);
                            }
                            if (!this.cmline[n3].endsWith("|out|")) {
                                if (this.cadmin == 1 && this.stringbutton(this.rd, "  Approve War  ", 548, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                    this.tab = 2;
                                    this.itab = 1;
                                    this.litab = -1;
                                    this.openi = 10;
                                    String string = this.getSvalue(this.cmline[n3], 0);
                                    if (!this.intclan.equals(string)) {
                                        this.intclan = string;
                                        this.dispi = 0;
                                        this.nil = 0;
                                        this.lastint = "";
                                        this.readint = 1;
                                    }
                                    this.warnum = this.getSvalue(this.cmline[n3], 1);
                                    this.sendwarnum = true;
                                    this.intsel = this.cmlinetyp[n3] == 20 ? 4 : 7;
                                }
                            } else {
                                this.rd.setColor(this.color2k(170, 170, 170));
                                this.rd.drawString("[ Approved or interaction replaced. ]", 597 - this.ftm.stringWidth("[ Approved or interaction replaced. ]") / 2, 112 + 17 * n3 - this.scro);
                            }
                        }
                        if (this.cmlinetyp[n3] == 30 || this.cmlinetyp[n3] == 60) {
                            if (this.stringbutton(this.rd, "  View Clan  ", 267, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                String string = this.getSvalue(this.cmline[n3], 0);
                                if (!this.claname.equals(string)) {
                                    this.claname = string;
                                    this.loadedc = false;
                                }
                                this.spos5 = 0;
                                this.lspos5 = 0;
                                this.cfase = 3;
                                this.ctab = 0;
                                this.tab = 3;
                            }
                            if (this.stringbutton(this.rd, "  View Battle Suggestion  ", 407, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                this.viewgame1 = 1;
                                this.nvgames1 = this.cmlinetyp[n3] == 30 ? 2 : 5;
                                this.xclan = this.getSvalue(this.cmline[n3], 0);
                                this.viewwar1 = this.getSvalue(this.cmline[n3], 3);
                            }
                            if (!this.cmline[n3].endsWith("|out|")) {
                                if (this.cadmin == 1 && this.stringbutton(this.rd, "  Approve Battle  ", 560, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                    this.tab = 2;
                                    this.itab = 1;
                                    this.litab = -1;
                                    this.openi = 10;
                                    String string = this.getSvalue(this.cmline[n3], 0);
                                    if (!this.intclan.equals(string)) {
                                        this.intclan = string;
                                        this.dispi = 0;
                                        this.nil = 0;
                                        this.lastint = "";
                                        this.readint = 1;
                                    }
                                    this.itake = this.getSvalue(this.cmline[n3], 1);
                                    this.igive = this.getSvalue(this.cmline[n3], 2);
                                    this.warnum = this.getSvalue(this.cmline[n3], 3);
                                    this.sendwarnum = true;
                                    this.intsel = this.cmlinetyp[n3] == 30 ? 3 : 6;
                                }
                            } else {
                                this.rd.setColor(this.color2k(170, 170, 170));
                                this.rd.drawString("[ Approved or interaction replaced. ]", 604 - this.ftm.stringWidth("[ Approved or interaction replaced. ]") / 2, 112 + 17 * n3 - this.scro);
                            }
                            this.rd.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rd.getFontMetrics();
                            if (this.stringbutton(this.rd, "  View Car  ", 217 + this.ftm.stringWidth(this.cmline[n3 + 2]) + 47, 137 + 17 * n3 - this.scro, 6, n, n2, bl, 0, 0)) {
                                this.viewcar = this.getSvalue(this.cmline[n3], 1);
                                String string = this.getSvalue(this.cmline[n3], 0);
                                if (!this.claname.equals(string)) {
                                    this.claname = string;
                                    this.loadedc = false;
                                }
                                this.spos5 = 0;
                                this.lspos5 = 0;
                                this.cfase = 3;
                                this.loadedcars = -1;
                                this.loadedcar = 0;
                                this.ctab = 2;
                                this.tab = 3;
                            }
                            this.rd.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rd.getFontMetrics();
                            if (this.stringbutton(this.rd, "  View Car  ", 217 + this.ftm.stringWidth(this.cmline[n3 + 3]) + 47, 154 + 17 * n3 - this.scro, 6, n, n2, bl, 0, 0)) {
                                this.viewcar = this.getSvalue(this.cmline[n3], 2);
                                if (!this.claname.equals(this.xt.clan)) {
                                    this.claname = this.xt.clan;
                                    this.loadedc = false;
                                }
                                this.spos5 = 0;
                                this.lspos5 = 0;
                                this.cfase = 3;
                                this.loadedcars = -1;
                                this.loadedcar = 0;
                                this.ctab = 2;
                                this.tab = 3;
                            }
                        }
                        if (this.cmlinetyp[n3] == 40 || this.cmlinetyp[n3] == 70) {
                            if (this.stringbutton(this.rd, "  View Clan  ", 267, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                String string = this.getSvalue(this.cmline[n3], 0);
                                if (!this.claname.equals(string)) {
                                    this.claname = string;
                                    this.loadedc = false;
                                }
                                this.spos5 = 0;
                                this.lspos5 = 0;
                                this.cfase = 3;
                                this.ctab = 0;
                                this.tab = 3;
                            }
                            if (this.stringbutton(this.rd, "  View Battle Suggestion  ", 407, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                this.viewgame1 = 1;
                                this.nvgames1 = this.cmlinetyp[n3] == 40 ? 2 : 5;
                                this.xclan = this.getSvalue(this.cmline[n3], 0);
                                this.viewwar1 = this.getSvalue(this.cmline[n3], 3);
                            }
                            if (!this.cmline[n3].endsWith("|out|")) {
                                if (this.cadmin == 1 && this.stringbutton(this.rd, "  Approve Battle  ", 560, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) {
                                    this.tab = 2;
                                    this.itab = 1;
                                    this.litab = -1;
                                    this.openi = 10;
                                    String string = this.getSvalue(this.cmline[n3], 0);
                                    if (!this.intclan.equals(string)) {
                                        this.intclan = string;
                                        this.dispi = 0;
                                        this.nil = 0;
                                        this.lastint = "";
                                        this.readint = 1;
                                    }
                                    this.itake = this.getSvalue(this.cmline[n3], 1);
                                    this.igive = this.getSvalue(this.cmline[n3], 2);
                                    this.warnum = this.getSvalue(this.cmline[n3], 3);
                                    this.sendwarnum = true;
                                    this.intsel = this.cmlinetyp[n3] == 40 ? 2 : 5;
                                }
                            } else {
                                this.rd.setColor(this.color2k(170, 170, 170));
                                this.rd.drawString("[ Approved or interaction replaced. ]", 604 - this.ftm.stringWidth("[ Approved or interaction replaced. ]") / 2, 112 + 17 * n3 - this.scro);
                            }
                            this.rd.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rd.getFontMetrics();
                            if (this.stringbutton(this.rd, "  View Stage  ", 217 + this.ftm.stringWidth(this.cmline[n3 + 2]) + 54, 137 + 17 * n3 - this.scro, 6, n, n2, bl, 0, 0)) {
                                this.viewcar = this.getSvalue(this.cmline[n3], 1);
                                String string = this.getSvalue(this.cmline[n3], 0);
                                if (!this.claname.equals(string)) {
                                    this.claname = string;
                                    this.loadedc = false;
                                }
                                this.spos5 = 0;
                                this.lspos5 = 0;
                                this.cfase = 3;
                                this.loadedstages = -1;
                                this.loadedstage = 0;
                                this.ctab = 3;
                                this.tab = 3;
                            }
                            this.rd.setFont(new Font("Tahoma", 0, 11));
                            this.ftm = this.rd.getFontMetrics();
                            if (this.stringbutton(this.rd, "  View Stage  ", 217 + this.ftm.stringWidth(this.cmline[n3 + 3]) + 54, 154 + 17 * n3 - this.scro, 6, n, n2, bl, 0, 0)) {
                                this.viewcar = this.getSvalue(this.cmline[n3], 2);
                                if (!this.claname.equals(this.xt.clan)) {
                                    this.claname = this.xt.clan;
                                    this.loadedc = false;
                                }
                                this.spos5 = 0;
                                this.lspos5 = 0;
                                this.cfase = 3;
                                this.loadedstages = -1;
                                this.loadedstage = 0;
                                this.ctab = 3;
                                this.tab = 3;
                            }
                        }
                        if (this.cmlinetyp[n3] != 80 || !this.stringbutton(this.rd, "  View Championship  ", 295, 112 + 17 * n3 - this.scro, 0, n, n2, bl, 0, 0)) continue;
                        this.cfase = 0;
                        this.ntab = 1;
                        this.loadwstat = 0;
                        this.tab = 3;
                    }
                }
                if (this.readclan == -3) {
                    this.rd.setColor(this.color2k(240, 240, 240));
                    this.rd.fillRoundRect(387, 140, 200, 30, 20, 20);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRoundRect(387, 140, 200, 30, 20, 20);
                    this.rd.setFont(new Font("Tahoma", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("Reading...", 487 - this.ftm.stringWidth("Reading...") / 2, 160);
                }
                if (this.readclan == -1) {
                    this.rd.setFont(new Font("Arial", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Failed to load clan's conversation, please try again later...", 487 - this.ftm.stringWidth("Failed to load clan's conversation, please try again later...") / 2, 200);
                }
                if (this.readclan == -2) {
                    this.rd.setFont(new Font("Arial", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Failed to send data, please try again later...", 487 - this.ftm.stringWidth("Failed to send data, please try again later...") / 2, 200);
                }
                this.rd.setColor(this.color2k(205, 205, 205));
                this.rd.fillRect(207, 76, 357, 36);
                if (!this.drawl(this.rd, "#" + this.xt.clan + "#", 209, 78, true)) {
                    this.rd.drawRect(209, 78, 349, 29);
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("" + this.xt.clan + "", 384 - this.ftm.stringWidth("" + this.xt.clan + "") / 2, 98);
                }
                if (n > 209 && n < 559 && n2 > 78 && n2 < 108 && this.blockb == 0) {
                    this.cur = 12;
                    if (bl) {
                        if (!this.claname.equals(this.xt.clan)) {
                            this.claname = this.xt.clan;
                            this.loadedc = false;
                        }
                        this.spos5 = 0;
                        this.lspos5 = 0;
                        this.cfase = 3;
                        this.ctab = 0;
                        this.tab = 3;
                    }
                }
            } else if (this.xt.logged) {
                this.rd.setFont(new Font("Arial", 1, 12));
                this.ftm = this.rd.getFontMetrics();
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawString("You are not a member of any clan.  You need to join a clan first to have access to this.", 487 - this.ftm.stringWidth("You are not a member of any clan.  You need to join a clan first to have access to this.") / 2, 170);
                if (this.stringbutton(this.rd, "   Find a clan to join   ", 487, 200, 1, n, n2, bl, 0, 0)) {
                    this.tab = 3;
                    this.cfase = 2;
                    this.em = 1;
                    this.msg = "Clan Search";
                    this.smsg = "Listing clans with recent activity...";
                    this.nclns = 0;
                    this.spos5 = 0;
                    this.lspos5 = 0;
                    this.flko = 0;
                }
            } else {
                this.rd.setFont(new Font("Arial", 1, 13));
                this.ftm = this.rd.getFontMetrics();
                this.rd.drawString("You are currently using a trial account.", 495 - this.ftm.stringWidth("You are currently using a trial account.") / 2, 150);
                this.rd.drawString("You need to upgrade to be able participate in NFM clan's activities.", 495 - this.ftm.stringWidth("You need to upgrade to be able participate in NFM clan's activities.") / 2, 170);
                this.rd.setColor(new Color(206, 171, 98));
                this.rd.fillRoundRect(405, 193, 180, 50, 20, 20);
                if (this.drawbutton(this.xt.upgrade, 495, 218, n, n2, bl)) {
                    this.gs.editlink(this.xt.nickname, true);
                }
            }
            this.rd.setColor(this.color2k(205, 205, 205));
            this.rd.fillRect(207, 46, 582, 30);
            this.rd.setFont(new Font("Arial", 1, 12));
            this.ftm = this.rd.getFontMetrics();
            String[] stringArray = new String[]{"Player Interaction", "Clan Interaction", "Your Clan's Discussion"};
            int[] nArray = new int[]{207, 390, 368, 207};
            int[] nArray7 = new int[]{73, 73, 51, 51};
            for (n35 = 0; n35 < 3; ++n35) {
                if (this.itab == n35) {
                    this.rd.setColor(this.color2k(230, 230, 230));
                    this.rd.fillPolygon(nArray, nArray7, 4);
                } else if (n > nArray[0] && n < nArray[2] && n2 > 51 && n2 < 73) {
                    this.rd.setColor(this.color2k(217, 217, 217));
                    this.rd.fillPolygon(nArray, nArray7, 4);
                    if (bl) {
                        this.itab = n35;
                    }
                }
                this.rd.setColor(this.color2k(150, 150, 150));
                this.rd.drawPolygon(nArray, nArray7, 4);
                this.rd.setColor(this.color2k(40, 40, 40));
                this.rd.drawString(stringArray[n35], nArray[0] + 80 - this.ftm.stringWidth(stringArray[n35]) / 2, 67);
                int n36 = 0;
                while (n36 < 4) {
                    int n37 = n36++;
                    nArray[n37] = nArray[n37] + 183;
                }
            }
            this.rd.setColor(this.color2k(150, 150, 150));
            this.rd.drawLine(207, 73, 770, 73);
            this.rd.setColor(this.color2k(205, 205, 205));
            this.rd.fillRect(207, 360, 582, 79);
            this.rd.setColor(this.color2k(150, 150, 150));
            this.rd.drawLine(207, 362, 770, 362);
            this.rd.setColor(this.color2k(205, 205, 205));
            this.rd.fillRect(772, 76, 17, 333);
            this.rd.setColor(this.color2k(205, 205, 205));
            this.rd.fillRect(203, 46, 4, 393);
            if (this.mscro3 == 831 || this.sdist == 0) {
                if (this.sdist == 0) {
                    this.rd.setColor(this.color2k(205, 205, 205));
                } else {
                    this.rd.setColor(this.color2k(215, 215, 215));
                }
                this.rd.fillRect(772, 76, 17, 17);
            } else {
                this.rd.setColor(this.color2k(220, 220, 220));
                this.rd.fill3DRect(772, 76, 17, 17, true);
            }
            if (this.sdist != 0) {
                this.rd.drawImage(this.xt.asu, 777, 82, null);
            }
            if (this.mscro3 == 832 || this.sdist == 0) {
                if (this.sdist == 0) {
                    this.rd.setColor(this.color2k(205, 205, 205));
                } else {
                    this.rd.setColor(this.color2k(215, 215, 215));
                }
                this.rd.fillRect(772, 343, 17, 17);
            } else {
                this.rd.setColor(this.color2k(220, 220, 220));
                this.rd.fill3DRect(772, 343, 17, 17, true);
            }
            if (this.sdist != 0) {
                this.rd.drawImage(this.xt.asd, 777, 350, null);
            }
            if (this.sdist != 0) {
                if (this.lspos3 != this.spos3) {
                    this.rd.setColor(this.color2k(215, 215, 215));
                    this.rd.fillRect(772, 93 + this.spos3, 17, 31);
                } else {
                    if (this.mscro3 == 831) {
                        this.rd.setColor(this.color2k(215, 215, 215));
                    }
                    this.rd.fill3DRect(772, 93 + this.spos3, 17, 31, true);
                }
                this.rd.setColor(this.color2k(150, 150, 150));
                this.rd.drawLine(777, 106 + this.spos3, 783, 106 + this.spos3);
                this.rd.drawLine(777, 108 + this.spos3, 783, 108 + this.spos3);
                this.rd.drawLine(777, 110 + this.spos3, 783, 110 + this.spos3);
                if (this.mscro3 > 800 && this.lspos3 != this.spos3) {
                    this.lspos3 = this.spos3;
                }
                if (bl && this.openc == 0) {
                    if (this.mscro3 == 825 && n > 772 && n < 789 && n2 > 93 + this.spos3 && n2 < this.spos3 + 124) {
                        this.mscro3 = n2 - this.spos3;
                    }
                    if (this.mscro3 == 825 && n > 770 && n < 791 && n2 > 74 && n2 < 95) {
                        this.mscro3 = 831;
                    }
                    if (this.mscro3 == 825 && n > 770 && n < 791 && n2 > 341 && n2 < 362) {
                        this.mscro3 = 832;
                    }
                    if (this.mscro3 == 825 && n > 772 && n < 789 && n2 > 93 && n2 < 343) {
                        this.mscro3 = 108;
                        this.spos3 = n2 - this.mscro3;
                    }
                    if ((n35 = 2670 / this.sdist) < 1) {
                        n35 = 1;
                    }
                    if (this.mscro3 == 831) {
                        this.spos3 -= n35;
                        if (this.spos3 > 219) {
                            this.spos3 = 219;
                        }
                        if (this.spos3 < 0) {
                            this.spos3 = 0;
                        }
                        this.lspos3 = this.spos3;
                    }
                    if (this.mscro3 == 832) {
                        this.spos3 += n35;
                        if (this.spos3 > 219) {
                            this.spos3 = 219;
                        }
                        if (this.spos3 < 0) {
                            this.spos3 = 0;
                        }
                        this.lspos3 = this.spos3;
                    }
                    if (this.mscro3 < 800) {
                        this.spos3 = n2 - this.mscro3;
                        if (this.spos3 > 219) {
                            this.spos3 = 219;
                        }
                        if (this.spos3 < 0) {
                            this.spos3 = 0;
                        }
                    }
                    if (this.mscro3 == 825) {
                        this.mscro3 = 925;
                    }
                } else if (this.mscro3 != 825) {
                    this.mscro3 = 825;
                }
            }
            if (this.viewgame1 != 0) {
                this.rd.setColor(this.color2k(210, 210, 210));
                this.rd.fillRoundRect(204, 127, 583, 230, 20, 20);
                this.rd.setColor(this.color2k(150, 150, 150));
                this.rd.drawRoundRect(204, 127, 583, 230, 20, 20);
                this.rd.setFont(new Font("Tahoma", 1, 11));
                this.ftm = this.rd.getFontMetrics();
                this.rd.setColor(new Color(0, 0, 0));
                if (this.nvgames1 == 4) {
                    this.rd.drawString("War declaration, your clan " + this.xt.clan + " versus " + this.xclan + ".", 215, 145);
                    if (this.viewgame1 == 2) {
                        this.rd.drawString("" + this.xclan + " would create 5 more games and the first clan to win 5 games wins the war!", 215, 210 + this.nvgames1 * 18);
                    }
                }
                if (this.nvgames1 == 2) {
                    this.rd.drawString("Battle, your clan " + this.xt.clan + " versus " + this.xclan + ".", 215, 145);
                    if (this.viewgame1 == 2) {
                        this.rd.drawString("" + this.xclan + " would create 3 more games and the first clan to win 3 games wins the battle!", 215, 210 + this.nvgames1 * 18);
                    }
                }
                if (this.nvgames1 == 9) {
                    this.rd.drawString("Suggestion to accept war, your clan " + this.xt.clan + " versus " + this.xclan + ".", 215, 145);
                }
                if (this.nvgames1 == 5) {
                    this.rd.drawString("Suggestion to accept to battle, your clan " + this.xt.clan + " versus " + this.xclan + ".", 215, 145);
                }
                if (this.stringbutton(this.rd, "Close X", 749, 148, 3, n, n2, bl, 0, 0)) {
                    this.viewgame1 = 0;
                }
                this.rd.setFont(new Font("Tahoma", 1, 11));
                this.ftm = this.rd.getFontMetrics();
                this.rd.setColor(new Color(0, 0, 0));
                if (this.viewgame1 == 2) {
                    this.rd.drawString("Game", 246 - this.ftm.stringWidth("Game") / 2, 175);
                    this.rd.drawString("Stage", 412 - this.ftm.stringWidth("Stage") / 2, 175);
                    this.rd.drawString("Laps", 564 - this.ftm.stringWidth("Laps") / 2, 175);
                    this.rd.drawString("Type of Cars", 653 - this.ftm.stringWidth("Type of Cars") / 2, 175);
                    this.rd.drawString("Fixing", 751 - this.ftm.stringWidth("Fixing") / 2, 175);
                    n35 = 1;
                    int n38 = 1;
                    if (this.nvgames1 == 4 || this.nvgames1 == 2) {
                        n35 = 2;
                        n38 = 2;
                    }
                    for (int i = 0; i < this.nvgames1; ++i) {
                        this.rd.setFont(new Font("Tahoma", 0, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("# " + n35 + "", 246 - this.ftm.stringWidth("# " + n35 + "") / 2, 193 + i * 18);
                        n35 += n38;
                        this.rd.drawString(this.vwstages1[i], 412 - this.ftm.stringWidth(this.vwstages1[i]) / 2, 193 + i * 18);
                        this.rd.drawString("" + this.vwlaps1[i] + "", 564 - this.ftm.stringWidth("" + this.vwlaps1[i] + "") / 2, 193 + i * 18);
                        String string = "All Cars";
                        if (this.vwcars1[i] == 2) {
                            string = "Clan Cars";
                        }
                        if (this.vwcars1[i] == 3) {
                            string = "Game Cars";
                        }
                        if (this.vwclass1[i] == 0) {
                            string = string + ", All Classes";
                        }
                        if (this.vwclass1[i] == 1) {
                            string = string + ", Class C";
                        }
                        if (this.vwclass1[i] == 2) {
                            string = string + ", Class B & C";
                        }
                        if (this.vwclass1[i] == 3) {
                            string = string + ", Class B";
                        }
                        if (this.vwclass1[i] == 4) {
                            string = string + ", Class A & B";
                        }
                        if (this.vwclass1[i] == 5) {
                            string = string + ", Class A";
                        }
                        this.rd.drawString(string, 653 - this.ftm.stringWidth(string) / 2, 193 + i * 18);
                        String string6 = "Infinite";
                        if (this.vwfix1[i] == 1) {
                            string6 = "4 Fixes";
                        }
                        if (this.vwfix1[i] == 2) {
                            string6 = "3 Fixes";
                        }
                        if (this.vwfix1[i] == 3) {
                            string6 = "2 Fixes";
                        }
                        if (this.vwfix1[i] == 4) {
                            string6 = "1 Fix";
                        }
                        if (this.vwfix1[i] == 5) {
                            string6 = "No Fixing";
                        }
                        this.rd.drawString(string6, 751 - this.ftm.stringWidth(string6) / 2, 193 + i * 18);
                        this.rd.drawRect(213, 180 + i * 18, 565, 18);
                    }
                    this.rd.drawLine(213, 162, 213, 180 + this.nvgames1 * 18);
                    this.rd.drawLine(279, 162, 279, 180 + this.nvgames1 * 18);
                    this.rd.drawLine(546, 162, 546, 180 + this.nvgames1 * 18);
                    this.rd.drawLine(583, 162, 583, 180 + this.nvgames1 * 18);
                    this.rd.drawLine(723, 162, 723, 180 + this.nvgames1 * 18);
                    this.rd.drawLine(778, 162, 778, 180 + this.nvgames1 * 18);
                }
                if (this.viewgame1 == 1) {
                    this.rd.drawString("Loading...", 495 - this.ftm.stringWidth("Loading...") / 2, 242);
                }
                if (this.viewgame1 == 3) {
                    if (this.nvgames1 == 4 || this.nvgames1 == 9) {
                        this.rd.drawString("This war suggestion has expired and no longer exists.", 495 - this.ftm.stringWidth("This war suggestion has expired and no longer exists.") / 2, 232);
                    }
                    if (this.nvgames1 == 2 || this.nvgames1 == 5) {
                        this.rd.drawString("This battle suggestion has expired and no longer exists.", 495 - this.ftm.stringWidth("This battle suggestion has expired and no longer exists.") / 2, 232);
                    }
                    this.rd.drawString("(Suggestions expire after 90 days.)", 495 - this.ftm.stringWidth("(Suggestions expire after 90 days.)") / 2, 252);
                }
                if (this.viewgame1 == 4) {
                    this.rd.drawString("Error loading suggestion, please try again later...", 495 - this.ftm.stringWidth("Error loading suggestion, please try again later...") / 2, 242);
                }
            }
            if (!this.xt.clan.equals("")) {
                if (!this.gs.sendtyp.isShowing()) {
                    this.gs.sendtyp.show();
                    this.gs.sendtyp.select(0);
                }
                this.gs.sendtyp.move(207, 365);
                if (this.sendcmsg != 0) {
                    this.gs.sendtyp.disable();
                } else {
                    this.gs.sendtyp.enable();
                }
                this.darker = true;
                if (this.gs.sendtyp.getSelectedIndex() == 0) {
                    this.dommsg = true;
                    if (this.sendcmsg == 0) {
                        if (this.stringbutton(this.rd, "   Send  >  ", 723, 408, 0, n, n2, bl, 0, 0) && !this.gs.mmsg.getText().trim().equals("") && this.gs.mmsg.getText().toLowerCase().indexOf(this.gs.tpass.getText().toLowerCase()) == -1 && this.xt.acexp != -3) {
                            if (!this.xt.msgcheck(this.gs.mmsg.getText())) {
                                this.sendcmsg = 1;
                                this.viewgame1 = 0;
                            } else {
                                this.gs.sendtyp.hide();
                                ++this.xt.warning;
                            }
                        }
                    } else {
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Sending...", 723 - this.ftm.stringWidth("Sending...") / 2, 408);
                    }
                }
                if (this.gs.sendtyp.getSelectedIndex() == 1) {
                    this.rd.setFont(new Font("Arial", 0, 12));
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("A date that gets converted to the local time of any person previewing it.", 376, 382);
                    if (!this.gs.senditem.isShowing()) {
                        this.gs.senditem.removeAll();
                        Calendar calendar = Calendar.getInstance();
                        boolean bl8 = false;
                        for (int i = 0; i < 20; ++i) {
                            String string = this.wday[calendar.get(7) - 1];
                            if (!bl8) {
                                string = "Today";
                                bl8 = true;
                            }
                            this.gs.senditem.add(this.rd, "" + string + "  -  " + this.month[calendar.get(2)] + " " + calendar.get(5) + "");
                            calendar.roll(5, true);
                        }
                        this.gs.senditem.select(0);
                        this.gs.senditem.show();
                    }
                    if (!this.gs.datat.isShowing()) {
                        this.gs.datat.removeAll();
                        int n39 = 12;
                        String string = "PM";
                        for (int i = 0; i < 24; ++i) {
                            this.gs.datat.add(this.rd, "" + n39 + " " + string + "");
                            if (++n39 == 12) {
                                string = "AM";
                            }
                            if (n39 != 13) continue;
                            n39 = 1;
                        }
                        this.gs.datat.select(0);
                        this.gs.datat.show();
                    }
                    this.gs.senditem.move(300, 395);
                    this.gs.datat.move(491, 395);
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("Date is displayed based on your computer calendar's date/time, please make sure it is correct.", 207, 435);
                    if (this.sendcmsg == 0) {
                        if (this.stringbutton(this.rd, "   Send  >  ", 723, 408, 0, n, n2, bl, 0, 0)) {
                            this.sendcmsg = 1;
                            this.viewgame1 = 0;
                        }
                    } else {
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Sending...", 723 - this.ftm.stringWidth("Sending...") / 2, 408);
                    }
                } else {
                    if (this.gs.senditem.isShowing()) {
                        this.gs.senditem.hide();
                    }
                    if (this.gs.datat.isShowing()) {
                        this.gs.datat.hide();
                    }
                }
                this.darker = false;
            }
        }
    }

    /*
     * Opcode count of 13428 triggered aggressive code reduction.  Override with --aggressivesizethreshold.
     */
    @Override
    public void run() {
        try {
            this.socket = new Socket(this.lg.servers[0], 7061);
            this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.dout = new PrintWriter(this.socket.getOutputStream(), true);
        }
        catch (Exception exception) {
            this.domon = false;
        }
        while (this.domon) {
            block633: {
                String string;
                String string2;
                block632: {
                    int n;
                    Object object;
                    string2 = "";
                    string = "";
                    string2 = "101|0|" + this.xt.nickname + ":" + this.xt.nickey + "|";
                    try {
                        this.dout.println(string2);
                        string = this.din.readLine();
                        if (string == null) {
                            this.domon = false;
                        }
                    }
                    catch (Exception exception) {
                        this.domon = false;
                    }
                    if (this.domon) {
                        int n2;
                        int n3;
                        int n4;
                        this.ntime = this.getLvalue(string, 0);
                        this.maxclans = this.getvalue(string, 1);
                        for (n4 = 0; n4 < 3; ++n4) {
                            for (n3 = 0; n3 < 5; ++n3) {
                                this.roomf[n4][n3] = 0;
                            }
                        }
                        n4 = 0;
                        n3 = 2;
                        boolean bl = false;
                        while (!bl) {
                            object = this.getSvalue(string, n3);
                            if (!((String)object).equals("")) {
                                n = this.getvalue(string, n3 + 1);
                                n2 = this.getvalue(string, n3 + 2);
                                if ((n == -1 || n2 == -1) && n4 < 900) {
                                    this.pname[n4] = object;
                                    this.proom[n4] = n;
                                    this.pserver[n4] = n2;
                                    ++n4;
                                }
                            } else {
                                bl = true;
                            }
                            n3 += 3;
                        }
                        n3 = 2;
                        bl = false;
                        while (!bl) {
                            object = this.getSvalue(string, n3);
                            if (!((String)object).equals("")) {
                                n = this.getvalue(string, n3 + 1);
                                n2 = this.getvalue(string, n3 + 2);
                                if (n >= 0 && n <= 4 && n2 >= 0 && n2 <= 2) {
                                    int[] nArray = this.roomf[n2];
                                    int n5 = n;
                                    nArray[n5] = nArray[n5] + 1;
                                    if (n4 < 900) {
                                        this.pname[n4] = object;
                                        this.proom[n4] = n;
                                        this.pserver[n4] = n2;
                                        ++n4;
                                    }
                                }
                            } else {
                                bl = true;
                            }
                            n3 += 3;
                        }
                        this.npo = n4;
                    }
                    if (this.ptab == 1) {
                        if (this.freq == 2) {
                            string2 = "101|14|" + this.xt.nickname + "|" + this.xt.nickey + "|" + this.freqname + "|";
                            try {
                                this.dout.println(string2);
                                string = this.din.readLine();
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            if (string.equals("OK")) {
                                this.freq = 0;
                                this.npf = -1;
                            } else {
                                this.freq = -1;
                                this.cntf = 40;
                            }
                        }
                        if (this.freq == 3) {
                            string2 = "101|15|" + this.xt.nickname + "|" + this.xt.nickey + "|" + this.freqname + "|";
                            try {
                                this.dout.println(string2);
                                string = this.din.readLine();
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            if (string.equals("OK")) {
                                this.freq = 0;
                                this.npf = -1;
                            } else {
                                this.freq = -2;
                                this.cntf = 40;
                            }
                        }
                        if (this.freq == -6) {
                            string2 = "101|18|" + this.xt.nickname + "|" + this.xt.nickey + "|";
                            try {
                                this.dout.println(string2);
                                string = this.din.readLine();
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            this.freq = 0;
                        }
                        this.loadfriends();
                    }
                    if (this.sfreq == 1) {
                        string2 = "101|16|" + this.xt.nickname + "|" + this.xt.nickey + "|" + this.proname + "|";
                        try {
                            this.dout.println(string2);
                            string = this.din.readLine();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        this.sfreq = string.equals("OK") ? 2 : 3;
                    }
                    if (this.sfreq == 4) {
                        string2 = "101|17|" + this.xt.nickname + "|" + this.xt.nickey + "|" + this.proname + "|";
                        try {
                            this.dout.println(string2);
                            string = this.din.readLine();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        if (string.equals("OK")) {
                            this.sfreq = 5;
                            this.npf = -1;
                        } else {
                            this.sfreq = 6;
                        }
                    }
                    if (this.sentchange == 2 && this.domon) {
                        string2 = "101|5|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.sentance + "|";
                        try {
                            this.dout.println(string2);
                            string = this.din.readLine();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        this.sentchange = 0;
                    }
                    if (this.tab == 2 && this.domon) {
                        Object object2;
                        if (this.itab == 0) {
                            if (!this.blockname.equals("")) {
                                for (int i = 0; i < this.nm; ++i) {
                                    if (!this.mname[i].equals(this.blockname)) continue;
                                    this.mtyp[i] = 3;
                                    break;
                                }
                                try {
                                    string2 = "101|11|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.blockname + "|";
                                    this.dout.println(string2);
                                    string = this.din.readLine();
                                }
                                catch (Exception exception) {
                                    // empty catch block
                                }
                                this.blockname = "";
                            }
                            if (!this.unblockname.equals("")) {
                                for (int i = 0; i < this.nm; ++i) {
                                    if (!this.mname[i].equals(this.unblockname)) continue;
                                    this.mtyp[i] = 0;
                                    break;
                                }
                                try {
                                    string2 = "101|12|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.unblockname + "|";
                                    this.dout.println(string2);
                                    string = this.din.readLine();
                                }
                                catch (Exception exception) {
                                    // empty catch block
                                }
                                this.unblockname = "";
                            }
                            try {
                                string2 = "101|13|" + this.xt.nickname + "|" + this.xt.nickey + "|" + this.loadmsgs + "|";
                                this.dout.println(string2);
                                string = this.din.readLine();
                                if (string.startsWith("MSGS")) {
                                    this.loadmsgs = this.getvalue(string, 1);
                                    DataInputStream dataInputStream = new DataInputStream(this.socket.getInputStream());
                                    byte[] byArray = new byte[this.loadmsgs];
                                    dataInputStream.readFully(byArray);
                                    string = this.din.readLine();
                                    dataInputStream = new DataInputStream(new ByteArrayInputStream(byArray));
                                    String string3 = "";
                                    this.nm = 0;
                                    while ((string3 = dataInputStream.readLine()) != null && this.nm < 200) {
                                        this.mname[this.nm] = this.getSvalue(string3, 0);
                                        this.mtyp[this.nm] = this.getvalue(string3, 1);
                                        this.mconvo[this.nm] = this.getSvalue(string3, 2);
                                        this.msub[this.nm] = this.getSvalue(string3, 3);
                                        this.mctime[this.nm] = this.getLvalue(string3, 4);
                                        this.mtime[this.nm] = "";
                                        ++this.nm;
                                    }
                                } else if (string.equals("NOMSGS")) {
                                    this.loadmsgs = 0;
                                }
                            }
                            catch (Exception exception) {
                                this.loadmsgs = -2;
                            }
                            if (this.loadmsgs > 0) {
                                for (int i = 0; i < this.nm; ++i) {
                                    if (this.mctime[i] > 0L) {
                                        try {
                                            String string4;
                                            long l = this.ntime - this.mctime[i];
                                            object = "Received";
                                            if (this.mtyp[i] == 2) {
                                                object = "Sent";
                                            }
                                            if (l >= 1000L && l < 60000L) {
                                                this.mtime[i] = "" + (String)object + " seconds ago";
                                            }
                                            if (l >= 60000L && l < 3600000L) {
                                                n = (int)(l / 60000L);
                                                string4 = "s";
                                                if (n == 1) {
                                                    string4 = "";
                                                }
                                                this.mtime[i] = "" + (String)object + " " + n + " minute" + string4 + " ago";
                                            }
                                            if (l >= 3600000L && l < 86400000L) {
                                                n = (int)(l / 3600000L);
                                                string4 = "s";
                                                if (n == 1) {
                                                    string4 = "";
                                                }
                                                this.mtime[i] = "" + (String)object + " " + n + " hour" + string4 + " ago";
                                            }
                                            if (l < 86400000L) continue;
                                            n = (int)(l / 86400000L);
                                            string4 = "s";
                                            if (n == 1) {
                                                string4 = "";
                                            }
                                            this.mtime[i] = "" + (String)object + " " + n + " day" + string4 + " ago";
                                        }
                                        catch (Exception exception) {
                                            this.mtime[i] = "";
                                        }
                                        continue;
                                    }
                                    this.mtime[i] = "";
                                }
                            }
                            if (this.openc != 0) {
                                block627: {
                                    int n6;
                                    boolean bl = false;
                                    int n7 = -1;
                                    for (n6 = 0; n6 < this.nm; ++n6) {
                                        if (!this.mname[n6].equals(this.opname)) continue;
                                        n7 = n6;
                                        break;
                                    }
                                    if (n7 != -1 && this.readmsg != 3 && this.readmsg != 4 && this.readmsg != 5) {
                                        if (!this.lastsub.equals("" + this.mctime[n7])) {
                                            bl = true;
                                            this.readmsg = 1;
                                        } else {
                                            this.readmsg = 2;
                                        }
                                    } else {
                                        this.lastsub = "";
                                        if (this.readmsg == 1) {
                                            this.readmsg = 0;
                                            this.nml = 0;
                                        }
                                    }
                                    if (bl) {
                                        try {
                                            string2 = "101|8|" + this.xt.nickname + "|" + this.xt.nickey + "|" + this.mconvo[n7] + "";
                                            this.dout.println(string2);
                                            string = this.din.readLine();
                                            if (string.startsWith("RECIVE")) {
                                                for (n6 = 0; n6 < this.nml; ++n6) {
                                                    this.mline[n6] = null;
                                                    this.mlinetyp[n6] = 0;
                                                    this.mctimes[n6] = 0L;
                                                    this.mtimes[n6] = null;
                                                }
                                                this.nml = 0;
                                                this.cd.acname = "";
                                                this.cd.action = 0;
                                                this.cd.onstage = "";
                                                this.addstage = 0;
                                                this.nclns = 0;
                                                n6 = this.getvalue(string, 1);
                                                object = new DataInputStream(this.socket.getInputStream());
                                                byte[] byArray = new byte[n6];
                                                ((DataInputStream)object).readFully(byArray);
                                                string = this.din.readLine();
                                                object = new DataInputStream(new ByteArrayInputStream(byArray));
                                                String string5 = "";
                                                while ((string5 = ((DataInputStream)object).readLine()) != null) {
                                                    if (string5.startsWith("|")) {
                                                        String string6;
                                                        if (this.nml != 0) {
                                                            this.mline[this.nml] = "";
                                                            this.mlinetyp[this.nml] = 167;
                                                            ++this.nml;
                                                        }
                                                        if ((string6 = this.getSvalue(string5, 1)).toLowerCase().equals(this.xt.nickname.toLowerCase())) {
                                                            string6 = "You";
                                                        }
                                                        this.mlinetyp[this.nml] = this.getvalue(string5, 2);
                                                        int n8 = this.mlinetyp[this.nml];
                                                        if (n8 == 0) {
                                                            this.mline[this.nml] = "" + string6 + " wrote:";
                                                            this.mctimes[this.nml] = this.getLvalue(string5, 3);
                                                            this.mtimes[this.nml] = "";
                                                            ++this.nml;
                                                        }
                                                        if (n8 == 1) {
                                                            this.mline[this.nml] = "" + string6 + " shared a car:";
                                                            this.mctimes[this.nml] = this.getLvalue(string5, 3);
                                                            this.mtimes[this.nml] = "";
                                                            ++this.nml;
                                                            this.mline[this.nml] = this.getSvalue(string5, 4);
                                                            this.mlinetyp[this.nml] = 10;
                                                            ++this.nml;
                                                            this.mline[this.nml] = "";
                                                            this.mlinetyp[this.nml] = 167;
                                                            ++this.nml;
                                                        }
                                                        if (n8 == 2) {
                                                            this.mline[this.nml] = "" + string6 + " shared a stage:";
                                                            this.mctimes[this.nml] = this.getLvalue(string5, 3);
                                                            this.mtimes[this.nml] = "";
                                                            ++this.nml;
                                                            this.mline[this.nml] = this.getSvalue(string5, 4);
                                                            this.mlinetyp[this.nml] = 20;
                                                            ++this.nml;
                                                            this.mline[this.nml] = "";
                                                            this.mlinetyp[this.nml] = 167;
                                                            ++this.nml;
                                                        }
                                                        if (n8 == 3) {
                                                            this.mline[this.nml] = string6.equals("You") ? "You have invited " + this.mname[n7] + " to join your clan:" : "" + string6 + " has invited you to join clan:";
                                                            this.mctimes[this.nml] = this.getLvalue(string5, 3);
                                                            this.mtimes[this.nml] = "";
                                                            ++this.nml;
                                                            this.mline[this.nml] = this.getSvalue(string5, 4);
                                                            if (this.nclns < 20) {
                                                                this.clanlo[this.nclns] = this.mline[this.nml];
                                                                ++this.nclns;
                                                            }
                                                            this.mlinetyp[this.nml] = 30;
                                                            ++this.nml;
                                                            this.mline[this.nml] = "";
                                                            this.mlinetyp[this.nml] = 167;
                                                            ++this.nml;
                                                            if (!string6.equals("You")) {
                                                                this.mline[this.nml] = this.xt.clan.equals("") ? "(If you would like join this clan, visit that clan's page and click 'Request to Join..'.)" : "(You will need to leave your clan " + this.xt.clan + " first before being able to join...)";
                                                                this.mlinetyp[this.nml] = -1;
                                                                ++this.nml;
                                                            }
                                                        }
                                                        if (n8 != 4) continue;
                                                        this.mline[this.nml] = string6.equals("You") ? "You have shared the following date:" : "" + string6 + " has shared the following date:";
                                                        this.mctimes[this.nml] = this.getLvalue(string5, 3);
                                                        this.mtimes[this.nml] = "";
                                                        object2 = Calendar.getInstance();
                                                        long l = ((Calendar)object2).getTimeInMillis() - (this.ntime - this.mctimes[this.nml]) + this.getLvalue(string5, 4);
                                                        if (l > 0L) {
                                                            ((Calendar)object2).setTimeInMillis(l);
                                                        }
                                                        ++this.nml;
                                                        int n9 = ((Calendar)object2).get(11);
                                                        String string7 = "AM";
                                                        if (((Calendar)object2).get(12) > 30 && ++n9 == 24) {
                                                            n9 -= 24;
                                                        }
                                                        if (n9 >= 12) {
                                                            string7 = "PM";
                                                        }
                                                        if (n9 > 12) {
                                                            n9 -= 12;
                                                        }
                                                        if (n9 == 0) {
                                                            n9 = 12;
                                                        }
                                                        try {
                                                            this.mline[this.nml] = "[  " + this.wday[((Calendar)object2).get(7) - 1] + "  -  " + this.month[((Calendar)object2).get(2)] + " " + ((Calendar)object2).get(5) + ",  " + n9 + " " + string7 + "  ]";
                                                        }
                                                        catch (Exception exception) {
                                                            this.mline[this.nml] = "Error occurred while calculating this date.";
                                                        }
                                                        this.mlinetyp[this.nml] = -1;
                                                        ++this.nml;
                                                        this.mline[this.nml] = "(Please make sure your computer's calendar/clock is adjusted correctly, to read this date in your local time.)";
                                                        this.mlinetyp[this.nml] = -1;
                                                        ++this.nml;
                                                        continue;
                                                    }
                                                    this.mline[this.nml] = string5;
                                                    try {
                                                        this.rd.setFont(new Font("Tahoma", 0, 11));
                                                        this.ftm = this.rd.getFontMetrics();
                                                        String string8 = "";
                                                        for (int i = 0; i < string5.length(); ++i) {
                                                            if (this.ftm.stringWidth(string8 = string8 + string5.charAt(i)) <= 540) continue;
                                                            if (string8.lastIndexOf(" ") != -1) {
                                                                this.mline[this.nml] = string8.substring(0, string8.lastIndexOf(" "));
                                                                string8 = string8.substring(string8.lastIndexOf(" ") + 1, string8.length());
                                                            } else {
                                                                this.mline[this.nml] = string8;
                                                                string8 = "";
                                                            }
                                                            this.mlinetyp[this.nml] = -1;
                                                            ++this.nml;
                                                        }
                                                        this.mline[this.nml] = string8;
                                                    }
                                                    catch (Exception exception) {
                                                        // empty catch block
                                                    }
                                                    this.mlinetyp[this.nml] = -1;
                                                    ++this.nml;
                                                }
                                                this.readmsg = 2;
                                                this.lastsub = "" + this.mctime[n7];
                                                if (this.mtyp[n7] == 1) {
                                                    this.mtyp[n7] = 0;
                                                    try {
                                                        this.dout.println("101|10|" + this.xt.nickname + "|" + this.opname + "|");
                                                        string = this.din.readLine();
                                                    }
                                                    catch (Exception exception) {
                                                        // empty catch block
                                                    }
                                                }
                                                this.spos4 = 208;
                                                break block627;
                                            }
                                            this.readmsg = 3;
                                        }
                                        catch (Exception exception) {
                                            this.readmsg = 4;
                                        }
                                    }
                                }
                                if (this.readmsg == 2) {
                                    for (int i = 0; i < this.nml; ++i) {
                                        if ((this.mlinetyp[i] == 0 || this.mlinetyp[i] == 1 || this.mlinetyp[i] == 2 || this.mlinetyp[i] == 3 || this.mlinetyp[i] == 4) && this.mctimes[i] > 0L) {
                                            try {
                                                String string9;
                                                long l = this.ntime - this.mctimes[i];
                                                if (l >= 1000L && l < 60000L) {
                                                    this.mtimes[i] = "seconds ago";
                                                }
                                                if (l >= 60000L && l < 3600000L) {
                                                    int n10 = (int)(l / 60000L);
                                                    string9 = "s";
                                                    if (n10 == 1) {
                                                        string9 = "";
                                                    }
                                                    this.mtimes[i] = "" + n10 + " minute" + string9 + " ago";
                                                }
                                                if (l >= 3600000L && l < 86400000L) {
                                                    int n11 = (int)(l / 3600000L);
                                                    string9 = "s";
                                                    if (n11 == 1) {
                                                        string9 = "";
                                                    }
                                                    this.mtimes[i] = "" + n11 + " hour" + string9 + " ago";
                                                }
                                                if (l < 86400000L) continue;
                                                int n12 = (int)(l / 86400000L);
                                                string9 = "s";
                                                if (n12 == 1) {
                                                    string9 = "";
                                                }
                                                this.mtimes[i] = "" + n12 + " day" + string9 + " ago";
                                            }
                                            catch (Exception exception) {
                                                this.mtimes[i] = "";
                                            }
                                            continue;
                                        }
                                        this.mtimes[i] = "";
                                    }
                                }
                            }
                            if (this.sendmsg == 2) {
                                this.gs.mmsg.setText(" ");
                                this.sendmsg = 0;
                            }
                            if (this.openc == 10) {
                                int n13;
                                if (this.loaditem == 1) {
                                    String string10;
                                    String[] stringArray;
                                    int n14 = 0;
                                    String[] stringArray2 = new String[700];
                                    try {
                                        stringArray = new URL("http://multiplayer.needformadness.com/cars/lists/" + this.gs.tnick.getText() + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "");
                                        stringArray.openConnection().setConnectTimeout(2000);
                                        string10 = "";
                                        DataInputStream dataInputStream = new DataInputStream(stringArray.openStream());
                                        while ((string10 = dataInputStream.readLine()) != null) {
                                            string10 = "" + string10.trim();
                                            if (!string10.startsWith("mycars")) continue;
                                            boolean bl = true;
                                            while (bl && n14 < 700) {
                                                stringArray2[n14] = this.getfuncSvalue("mycars", string10, n14);
                                                if (stringArray2[n14].equals("")) {
                                                    bl = false;
                                                    continue;
                                                }
                                                ++n14;
                                            }
                                        }
                                        dataInputStream.close();
                                    }
                                    catch (Exception exception) {
                                        string10 = "" + exception;
                                        n14 = string10.indexOf("FileNotFound") != -1 ? 0 : -1;
                                    }
                                    if (n14 == -1) {
                                        this.gs.senditem.removeAll();
                                        this.gs.senditem.add(this.rd, "Failed to load your cars, please try again later.");
                                        this.loaditem = 0;
                                    }
                                    if (n14 == 0) {
                                        this.gs.senditem.removeAll();
                                        this.gs.senditem.add(this.rd, "You have no added or published cars to load.");
                                        this.loaditem = 0;
                                    }
                                    if (n14 > 0) {
                                        int n15;
                                        stringArray = new String[700];
                                        int n16 = 0;
                                        for (n15 = 0; n15 < n14; ++n15) {
                                            this.gs.senditem.removeAll();
                                            this.gs.senditem.add(this.rd, "Loading shareable cars,  " + (int)((float)n15 / (float)n14 * 100.0f) + " %");
                                            try {
                                                String string11 = ("http://multiplayer.needformadness.com/cars/" + stringArray2[n15] + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "").replace(' ', '_');
                                                URL uRL = new URL(string11);
                                                uRL.openConnection().setConnectTimeout(2000);
                                                String string12 = "";
                                                object2 = new DataInputStream(uRL.openStream());
                                                while ((string12 = ((DataInputStream)object2).readLine()) != null) {
                                                    string12 = "" + string12.trim();
                                                    if (!string12.startsWith("details")) continue;
                                                    String string13 = this.getfuncSvalue("details", string12, 0);
                                                    n13 = this.getfuncvalue("details", string12, 1);
                                                    if (n13 <= 0 && !string13.toLowerCase().equals(this.gs.tnick.getText().toLowerCase())) continue;
                                                    stringArray[n16] = stringArray2[n15];
                                                    ++n16;
                                                }
                                                ((FilterInputStream)object2).close();
                                                continue;
                                            }
                                            catch (Exception exception) {
                                                // empty catch block
                                            }
                                        }
                                        this.gs.senditem.removeAll();
                                        if (n16 > 0) {
                                            for (n15 = 0; n15 < n16; ++n15) {
                                                this.gs.senditem.add(this.rd, stringArray[n15]);
                                            }
                                            this.loaditem = 10;
                                        } else {
                                            this.gs.senditem.add(this.rd, "You have no cars that can be shared.");
                                            this.loaditem = 0;
                                        }
                                    }
                                }
                                if (this.loaditem == 2) {
                                    String string14;
                                    String[] stringArray;
                                    int n17 = 0;
                                    String[] stringArray3 = new String[700];
                                    try {
                                        stringArray = new URL("http://multiplayer.needformadness.com/tracks/lists/" + this.gs.tnick.getText() + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "");
                                        stringArray.openConnection().setConnectTimeout(2000);
                                        string14 = "";
                                        DataInputStream dataInputStream = new DataInputStream(stringArray.openStream());
                                        while ((string14 = dataInputStream.readLine()) != null) {
                                            string14 = "" + string14.trim();
                                            if (!string14.startsWith("mystages")) continue;
                                            boolean bl = true;
                                            while (bl && n17 < 700) {
                                                stringArray3[n17] = this.getfuncSvalue("mystages", string14, n17);
                                                if (stringArray3[n17].equals("")) {
                                                    bl = false;
                                                    continue;
                                                }
                                                ++n17;
                                            }
                                        }
                                        dataInputStream.close();
                                    }
                                    catch (Exception exception) {
                                        string14 = "" + exception;
                                        n17 = string14.indexOf("FileNotFound") != -1 ? 0 : -1;
                                    }
                                    if (n17 == -1) {
                                        this.gs.senditem.removeAll();
                                        this.gs.senditem.add(this.rd, "Failed to load your stages, please try again later.");
                                        this.loaditem = 0;
                                    }
                                    if (n17 == 0) {
                                        this.gs.senditem.removeAll();
                                        this.gs.senditem.add(this.rd, "You have no added or published stages to load.");
                                        this.loaditem = 0;
                                    }
                                    if (n17 > 0) {
                                        int n18;
                                        stringArray = new String[700];
                                        int n19 = 0;
                                        for (n18 = 0; n18 < n17; ++n18) {
                                            this.gs.senditem.removeAll();
                                            this.gs.senditem.add(this.rd, "Loading shareable stages,  " + (int)((float)n18 / (float)n17 * 100.0f) + " %");
                                            try {
                                                String string15 = ("http://multiplayer.needformadness.com/tracks/" + stringArray3[n18] + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "").replace(' ', '_');
                                                URL uRL = new URL(string15);
                                                uRL.openConnection().setConnectTimeout(2000);
                                                String string16 = "";
                                                object2 = new DataInputStream(uRL.openStream());
                                                while ((string16 = ((DataInputStream)object2).readLine()) != null) {
                                                    string16 = "" + string16.trim();
                                                    if (!string16.startsWith("details")) continue;
                                                    String string17 = this.getfuncSvalue("details", string16, 0);
                                                    n13 = this.getfuncvalue("details", string16, 1);
                                                    if (n13 <= 0 && !string17.toLowerCase().equals(this.gs.tnick.getText().toLowerCase())) continue;
                                                    stringArray[n19] = stringArray3[n18];
                                                    ++n19;
                                                }
                                                ((FilterInputStream)object2).close();
                                                continue;
                                            }
                                            catch (Exception exception) {
                                                // empty catch block
                                            }
                                        }
                                        this.gs.senditem.removeAll();
                                        if (n19 > 0) {
                                            for (n18 = 0; n18 < n19; ++n18) {
                                                this.gs.senditem.add(this.rd, stringArray[n18]);
                                            }
                                            this.loaditem = 20;
                                        } else {
                                            this.gs.senditem.add(this.rd, "You have no stages that can be shared.");
                                            this.loaditem = 0;
                                        }
                                    }
                                }
                                if (this.gs.sendtyp.getSelectedIndex() == 3 && !this.xt.clan.equals("")) {
                                    this.clanlogopng(this.xt.clan);
                                }
                                if (this.sendmsg == 1) {
                                    try {
                                        String string18 = "#nada#";
                                        for (int i = 0; i < this.nm; ++i) {
                                            if (!this.mname[i].equals(this.opname)) continue;
                                            string18 = this.mconvo[i];
                                            break;
                                        }
                                        string2 = "101|9|" + this.xt.nickname + "|" + this.xt.nickey + "|" + this.opname + "|" + string18 + "|" + this.gs.sendtyp.getSelectedIndex() + "|";
                                        if (this.gs.sendtyp.getSelectedIndex() == 0) {
                                            String string19 = this.gs.mmsg.getText().replace("|", ":").replaceAll("[\\t\\n\\r]", "|");
                                            String string20 = "";
                                            int n20 = 0;
                                            for (int i = 0; i < string19.length(); ++i) {
                                                String string21 = "" + string19.charAt(i);
                                                n20 = string21.equals("|") ? ++n20 : 0;
                                                if (n20 > 1) continue;
                                                string20 = string20 + string21;
                                            }
                                            string2 = string2 + "" + string20 + "||";
                                        }
                                        if (this.gs.sendtyp.getSelectedIndex() == 1 || this.gs.sendtyp.getSelectedIndex() == 2) {
                                            string2 = string2 + "" + this.gs.senditem.getSelectedItem() + "|";
                                        }
                                        if (this.gs.sendtyp.getSelectedIndex() == 3) {
                                            string2 = string2 + "" + this.xt.clan + "|";
                                        }
                                        if (this.gs.sendtyp.getSelectedIndex() == 4) {
                                            Calendar calendar = Calendar.getInstance();
                                            long l = calendar.getTimeInMillis();
                                            calendar.roll(5, this.gs.senditem.getSelectedIndex());
                                            int n21 = this.gs.datat.getSelectedIndex() + 12;
                                            if (n21 >= 24) {
                                                n21 -= 24;
                                            }
                                            calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), n21, 0);
                                            l = calendar.getTimeInMillis() - l;
                                            string2 = string2 + "" + l + "|";
                                        }
                                        this.dout.println(string2);
                                        string = this.din.readLine();
                                        if (string.equals("OK")) {
                                            this.sendmsg = 2;
                                        } else {
                                            this.readmsg = 5;
                                            this.sendmsg = 0;
                                        }
                                    }
                                    catch (Exception exception) {
                                        this.readmsg = 5;
                                        this.sendmsg = 0;
                                    }
                                }
                            }
                        }
                        if (this.itab == 1 && !this.xt.clan.equals("")) {
                            try {
                                string2 = "101|38|" + this.xt.clan + "|" + this.xt.clankey + "|" + this.loadinter + "|";
                                this.dout.println(string2);
                                string = this.din.readLine();
                                if (string.startsWith("INTER")) {
                                    this.loadinter = this.getvalue(string, 1);
                                    DataInputStream dataInputStream = new DataInputStream(this.socket.getInputStream());
                                    byte[] byArray = new byte[this.loadinter];
                                    dataInputStream.readFully(byArray);
                                    string = this.din.readLine();
                                    dataInputStream = new DataInputStream(new ByteArrayInputStream(byArray));
                                    String string22 = "";
                                    this.ni = 0;
                                    while ((string22 = dataInputStream.readLine()) != null && this.ni < 200) {
                                        this.iclan[this.ni] = this.getSvalue(string22, 0);
                                        this.icheck[this.ni] = this.getSvalue(string22, 1);
                                        this.iconvo[this.ni] = this.getSvalue(string22, 2);
                                        this.isub[this.ni] = this.getSvalue(string22, 3);
                                        this.ictime[this.ni] = this.getLvalue(string22, 4);
                                        this.itime[this.ni] = "";
                                        this.istat[this.ni] = this.getSvalue(string22, 5);
                                        if (this.istat[this.ni].equals("War")) {
                                            this.iwarn[this.ni] = this.getSvalue(string22, 6);
                                        }
                                        if (this.istat[this.ni].equals("Car Battle") || this.istat[this.ni].equals("Stage Battle")) {
                                            this.iwarn[this.ni] = this.getSvalue(string22, 6);
                                            this.itcar[this.ni] = this.getSvalue(string22, 7);
                                            this.igcar[this.ni] = this.getSvalue(string22, 8);
                                        }
                                        ++this.ni;
                                    }
                                } else if (string.equals("NOINTER")) {
                                    this.loadinter = 0;
                                }
                            }
                            catch (Exception exception) {
                                this.loadmsgs = -2;
                            }
                            if (this.loadinter > 0) {
                                for (int i = 0; i < this.ni; ++i) {
                                    if (this.ictime[i] > 0L) {
                                        try {
                                            String string23;
                                            int n22;
                                            long l = this.ntime - this.ictime[i];
                                            if (l >= 1000L && l < 60000L) {
                                                this.itime[i] = "Seconds ago";
                                            }
                                            if (l >= 60000L && l < 3600000L) {
                                                n22 = (int)(l / 60000L);
                                                string23 = "s";
                                                if (n22 == 1) {
                                                    string23 = "";
                                                }
                                                this.itime[i] = "" + n22 + " minute" + string23 + " ago";
                                            }
                                            if (l >= 3600000L && l < 86400000L) {
                                                n22 = (int)(l / 3600000L);
                                                string23 = "s";
                                                if (n22 == 1) {
                                                    string23 = "";
                                                }
                                                this.itime[i] = "" + n22 + " hour" + string23 + " ago";
                                            }
                                            if (l < 86400000L) continue;
                                            n22 = (int)(l / 86400000L);
                                            string23 = "s";
                                            if (n22 == 1) {
                                                string23 = "";
                                            }
                                            this.itime[i] = "" + n22 + " day" + string23 + " ago";
                                        }
                                        catch (Exception exception) {
                                            this.itime[i] = "";
                                        }
                                        continue;
                                    }
                                    this.itime[i] = "";
                                }
                            }
                            if (this.loadwstat == 0) {
                                this.loadchamps();
                            }
                            if (this.openi != 0) {
                                block628: {
                                    boolean bl = false;
                                    int n23 = -1;
                                    for (int i = 0; i < this.ni; ++i) {
                                        if (!this.iclan[i].equals(this.intclan)) continue;
                                        n23 = i;
                                        break;
                                    }
                                    if (this.readint == 6) {
                                        try {
                                            Thread.sleep(2000L);
                                        }
                                        catch (InterruptedException interruptedException) {
                                            // empty catch block
                                        }
                                    }
                                    if (n23 != -1 && this.readint != 3 && this.readint != 4 && this.readint != 5) {
                                        if (!this.lastint.equals("" + this.ictime[n23])) {
                                            bl = true;
                                            this.readint = 1;
                                        } else {
                                            this.readint = 2;
                                        }
                                    } else {
                                        this.lastint = "";
                                        if (this.readint == 1) {
                                            this.readint = 0;
                                            this.nil = 0;
                                        }
                                    }
                                    if (bl && this.sendint != 1) {
                                        try {
                                            string2 = "101|40|" + this.xt.clan + "|" + this.xt.clankey + "|" + this.iconvo[n23] + "|";
                                            this.dout.println(string2);
                                            string = this.din.readLine();
                                            if (string.startsWith("RECIVE")) {
                                                if (this.istat[n23].equals("Car Battle")) {
                                                    this.dispi = 1;
                                                    this.dwarn = this.iwarn[n23];
                                                    this.dtcar = this.itcar[n23];
                                                    this.dgcar = this.igcar[n23];
                                                }
                                                if (this.istat[n23].equals("Stage Battle")) {
                                                    this.dispi = 2;
                                                    this.dwarn = this.iwarn[n23];
                                                    this.dtcar = this.itcar[n23];
                                                    this.dgcar = this.igcar[n23];
                                                }
                                                if (this.istat[n23].equals("War")) {
                                                    this.dispi = 3;
                                                    this.dwarn = this.iwarn[n23];
                                                }
                                                int n24 = 0;
                                                String[] stringArray = new String[1000];
                                                int[] nArray = new int[1000];
                                                long[] lArray = new long[1000];
                                                String[] stringArray4 = new String[1000];
                                                if (this.dispi != 0) {
                                                    stringArray[n24] = "";
                                                    nArray[n24] = 167;
                                                    stringArray[++n24] = "";
                                                    nArray[n24] = 167;
                                                    ++n24;
                                                }
                                                int n25 = this.getvalue(string, 1);
                                                object2 = new DataInputStream(this.socket.getInputStream());
                                                byte[] byArray = new byte[n25];
                                                ((DataInputStream)object2).readFully(byArray);
                                                string = this.din.readLine();
                                                object2 = new DataInputStream(new ByteArrayInputStream(byArray));
                                                String string24 = "";
                                                while ((string24 = ((DataInputStream)object2).readLine()) != null) {
                                                    if (string24.startsWith("|")) {
                                                        String string25;
                                                        int n26;
                                                        int n27;
                                                        int n28;
                                                        String string26;
                                                        if (n24 != 0) {
                                                            stringArray[n24] = "";
                                                            nArray[n24] = 167;
                                                            ++n24;
                                                        }
                                                        if ((string26 = this.getSvalue(string24, 1)).toLowerCase().equals(this.xt.nickname.toLowerCase())) {
                                                            string26 = "You";
                                                        }
                                                        nArray[n24] = this.getvalue(string24, 2);
                                                        int n29 = nArray[n24];
                                                        if (n29 == 0) {
                                                            stringArray[n24] = "" + string26 + " wrote:";
                                                            lArray[n24] = this.getLvalue(string24, 3);
                                                            stringArray4[n24] = "";
                                                            ++n24;
                                                        }
                                                        if (n29 == 1) {
                                                            stringArray[n24] = string26.equals("You") ? "You have shared the following date:" : "" + string26 + " has shared the following date:";
                                                            lArray[n24] = this.getLvalue(string24, 3);
                                                            stringArray4[n24] = "";
                                                            Calendar calendar = Calendar.getInstance();
                                                            long l = calendar.getTimeInMillis() - (this.ntime - lArray[n24]) + this.getLvalue(string24, 4);
                                                            if (l > 0L) {
                                                                calendar.setTimeInMillis(l);
                                                            }
                                                            ++n24;
                                                            n28 = calendar.get(11);
                                                            String string27 = "AM";
                                                            if (calendar.get(12) > 30 && ++n28 == 24) {
                                                                n28 -= 24;
                                                            }
                                                            if (n28 >= 12) {
                                                                string27 = "PM";
                                                            }
                                                            if (n28 > 12) {
                                                                n28 -= 12;
                                                            }
                                                            if (n28 == 0) {
                                                                n28 = 12;
                                                            }
                                                            try {
                                                                stringArray[n24] = "[  " + this.wday[calendar.get(7) - 1] + "  -  " + this.month[calendar.get(2)] + " " + calendar.get(5) + ",  " + n28 + " " + string27 + "  ]";
                                                            }
                                                            catch (Exception exception) {
                                                                stringArray[n24] = "Error occurred while calculating this date.";
                                                            }
                                                            nArray[n24] = -1;
                                                            stringArray[++n24] = "(Please make sure your computer's calendar/clock is adjusted correctly, to read this date in your local time.)";
                                                            nArray[n24] = -1;
                                                            ++n24;
                                                        }
                                                        if (n29 == 4) {
                                                            if (string26.toLowerCase().equals(this.xt.clan.toLowerCase())) {
                                                                stringArray[n24] = "Your clan has declared war on " + this.intclan + ":";
                                                                lArray[n24] = this.getLvalue(string24, 3);
                                                                stringArray4[n24] = "";
                                                                stringArray[++n24] = "I|" + this.getSvalue(string24, 4) + "|" + this.getSvalue(string24, 5) + "|";
                                                                nArray[n24] = 40;
                                                                stringArray[++n24] = "";
                                                                nArray[n24] = 167;
                                                                ++n24;
                                                                if (this.loadwstat == 1 && !string24.endsWith("|out|")) {
                                                                    int n30;
                                                                    int n31 = -1;
                                                                    int n32 = 0;
                                                                    n27 = -1;
                                                                    n28 = 0;
                                                                    for (n30 = 0; n30 < this.ncc; ++n30) {
                                                                        if (this.xt.clan.toLowerCase().equals(this.conclan[n30].toLowerCase())) {
                                                                            n32 = this.totp[n30];
                                                                            n31 = n30;
                                                                        }
                                                                        if (!this.intclan.toLowerCase().equals(this.conclan[n30].toLowerCase())) continue;
                                                                        n28 = this.totp[n30];
                                                                        n27 = n30;
                                                                    }
                                                                    n30 = n28 + 1;
                                                                    int n33 = n32 + 1;
                                                                    if (n33 > n28) {
                                                                        n33 = n28;
                                                                    }
                                                                    if (n31 != -1) {
                                                                        for (n26 = 0; n26 < this.nvc[n31]; ++n26) {
                                                                            if (!this.intclan.toLowerCase().equals(this.verclan[n31][n26].toLowerCase())) continue;
                                                                            if ((n30 -= this.points[n31][n26]) >= 0) break;
                                                                            n30 = 0;
                                                                            break;
                                                                        }
                                                                    }
                                                                    stringArray[n24] = "If you win this war, your clan would get:  [ " + n30 + " points ]   &  " + this.intclan + " would lose:  [ " + n33 + " points ]";
                                                                    nArray[n24] = -1;
                                                                    ++n24;
                                                                    n30 = n32 + 1;
                                                                    n33 = n28 + 1;
                                                                    if (n33 > n32) {
                                                                        n33 = n32;
                                                                    }
                                                                    if (n27 != -1) {
                                                                        for (n26 = 0; n26 < this.nvc[n27]; ++n26) {
                                                                            if (!this.xt.clan.toLowerCase().equals(this.verclan[n27][n26].toLowerCase())) continue;
                                                                            if ((n30 -= this.points[n27][n26]) >= 0) break;
                                                                            n30 = 0;
                                                                            break;
                                                                        }
                                                                    }
                                                                    stringArray[n24] = "If you lose this war, your clan would lose:  [ " + n33 + " points ]   &  " + this.intclan + " would get:  [ " + n30 + " points ]";
                                                                    nArray[n24] = -1;
                                                                    ++n24;
                                                                }
                                                                if (!string24.endsWith("|out|")) {
                                                                    stringArray[n24] = "(Waiting for " + this.intclan + " to accept this war declaration and create 5 more games.)";
                                                                    nArray[n24] = -1;
                                                                    ++n24;
                                                                }
                                                            } else {
                                                                stringArray[n24] = "" + this.intclan + " has declaring war on your clan:";
                                                                lArray[n24] = this.getLvalue(string24, 3);
                                                                stringArray4[n24] = "";
                                                                stringArray[++n24] = "Y|" + this.getSvalue(string24, 4) + "|" + this.getSvalue(string24, 5) + "|";
                                                                nArray[n24] = 40;
                                                                stringArray[++n24] = "";
                                                                nArray[n24] = 167;
                                                                ++n24;
                                                                if (this.loadwstat == 1 && !string24.endsWith("|out|")) {
                                                                    int n34;
                                                                    int n35 = -1;
                                                                    int n36 = 0;
                                                                    n27 = -1;
                                                                    n28 = 0;
                                                                    for (n34 = 0; n34 < this.ncc; ++n34) {
                                                                        if (this.xt.clan.toLowerCase().equals(this.conclan[n34].toLowerCase())) {
                                                                            n36 = this.totp[n34];
                                                                            n35 = n34;
                                                                        }
                                                                        if (!this.intclan.toLowerCase().equals(this.conclan[n34].toLowerCase())) continue;
                                                                        n28 = this.totp[n34];
                                                                        n27 = n34;
                                                                    }
                                                                    n34 = n28 + 1;
                                                                    int n37 = n36 + 1;
                                                                    if (n37 > n28) {
                                                                        n37 = n28;
                                                                    }
                                                                    if (n35 != -1) {
                                                                        for (n26 = 0; n26 < this.nvc[n35]; ++n26) {
                                                                            if (!this.intclan.toLowerCase().equals(this.verclan[n35][n26].toLowerCase())) continue;
                                                                            if ((n34 -= this.points[n35][n26]) >= 0) break;
                                                                            n34 = 0;
                                                                            break;
                                                                        }
                                                                    }
                                                                    stringArray[n24] = "If you win this war, your clan would get:  [ " + n34 + " points ]   &  " + this.intclan + " would lose:  [ " + n37 + " points ]";
                                                                    nArray[n24] = -1;
                                                                    ++n24;
                                                                    n34 = n36 + 1;
                                                                    n37 = n28 + 1;
                                                                    if (n37 > n36) {
                                                                        n37 = n36;
                                                                    }
                                                                    if (n27 != -1) {
                                                                        for (n26 = 0; n26 < this.nvc[n27]; ++n26) {
                                                                            if (!this.xt.clan.toLowerCase().equals(this.verclan[n27][n26].toLowerCase())) continue;
                                                                            if ((n34 -= this.points[n27][n26]) >= 0) break;
                                                                            n34 = 0;
                                                                            break;
                                                                        }
                                                                    }
                                                                    stringArray[n24] = "If you lose this war, your clan would lose:  [ " + n37 + " points ]   &  " + this.intclan + " would get:  [ " + n34 + " points ]";
                                                                    nArray[n24] = -1;
                                                                    ++n24;
                                                                }
                                                                if (!string24.endsWith("|out|")) {
                                                                    stringArray[n24] = "(You accept this war declaration by creating 5 more games to be added to it.)";
                                                                    nArray[n24] = -1;
                                                                    ++n24;
                                                                }
                                                            }
                                                        }
                                                        if (n29 == 3) {
                                                            if (string26.toLowerCase().equals(this.xt.clan.toLowerCase())) {
                                                                stringArray[n24] = "Your clan has challenged " + this.intclan + " to a car battle:";
                                                                lArray[n24] = this.getLvalue(string24, 3);
                                                                stringArray4[n24] = "";
                                                                stringArray[++n24] = "I|" + this.getSvalue(string24, 4) + "|" + this.getSvalue(string24, 5) + "|" + this.getSvalue(string24, 6) + "|" + this.getSvalue(string24, 7) + "|";
                                                                nArray[n24] = 30;
                                                                stringArray[++n24] = "";
                                                                nArray[n24] = 167;
                                                                stringArray[++n24] = "If you win you will take " + this.intclan + "'s car :  [ " + this.getSvalue(string24, 4) + " ]";
                                                                nArray[n24] = -1;
                                                                stringArray[++n24] = "If you lose you will give " + this.intclan + " your clan's car :  [ " + this.getSvalue(string24, 5) + " ]";
                                                                nArray[n24] = -1;
                                                                ++n24;
                                                                if (!string24.endsWith("|out|")) {
                                                                    stringArray[n24] = "(Waiting for " + this.intclan + " to accept this car battle and create 3 more games.)";
                                                                    nArray[n24] = -1;
                                                                    ++n24;
                                                                }
                                                            } else {
                                                                stringArray[n24] = "" + this.intclan + " has challenged your clan to a car battle:";
                                                                lArray[n24] = this.getLvalue(string24, 3);
                                                                stringArray4[n24] = "";
                                                                stringArray[++n24] = "Y|" + this.getSvalue(string24, 5) + "|" + this.getSvalue(string24, 4) + "|" + this.getSvalue(string24, 6) + "|" + this.getSvalue(string24, 7) + "|";
                                                                nArray[n24] = 30;
                                                                stringArray[++n24] = "";
                                                                nArray[n24] = 167;
                                                                stringArray[++n24] = "If you win you will take " + this.intclan + "'s car :  [ " + this.getSvalue(string24, 5) + " ]";
                                                                nArray[n24] = -1;
                                                                stringArray[++n24] = "If you lose you will give " + this.intclan + " your clan's car :  [ " + this.getSvalue(string24, 4) + " ]";
                                                                nArray[n24] = -1;
                                                                ++n24;
                                                                if (!string24.endsWith("|out|")) {
                                                                    stringArray[n24] = "(You accept this car battle by creating 3 more games to be added to it.)";
                                                                    nArray[n24] = -1;
                                                                    ++n24;
                                                                }
                                                            }
                                                        }
                                                        if (n29 == 2) {
                                                            if (string26.toLowerCase().equals(this.xt.clan.toLowerCase())) {
                                                                stringArray[n24] = "Your clan has challenged " + this.intclan + " to a stage battle:";
                                                                lArray[n24] = this.getLvalue(string24, 3);
                                                                stringArray4[n24] = "";
                                                                stringArray[++n24] = "I|" + this.getSvalue(string24, 4) + "|" + this.getSvalue(string24, 5) + "|" + this.getSvalue(string24, 6) + "|" + this.getSvalue(string24, 7) + "|";
                                                                nArray[n24] = 20;
                                                                stringArray[++n24] = "";
                                                                nArray[n24] = 167;
                                                                ++n24;
                                                                String string28 = this.getSvalue(string24, 4);
                                                                if (string28.length() > 20) {
                                                                    string28 = "" + string28.substring(0, 20) + "...";
                                                                }
                                                                stringArray[n24] = "If you win you will take " + this.intclan + "'s stage :  [ " + string28 + " ]";
                                                                nArray[n24] = -1;
                                                                ++n24;
                                                                string28 = this.getSvalue(string24, 5);
                                                                if (string28.length() > 20) {
                                                                    string28 = "" + string28.substring(0, 20) + "...";
                                                                }
                                                                stringArray[n24] = "If you lose you will give " + this.intclan + " your clan's stage :  [ " + string28 + " ]";
                                                                nArray[n24] = -1;
                                                                ++n24;
                                                                if (!string24.endsWith("|out|")) {
                                                                    stringArray[n24] = "(Waiting for " + this.intclan + " to accept this stage battle and create 3 more games.)";
                                                                    nArray[n24] = -1;
                                                                    ++n24;
                                                                }
                                                            } else {
                                                                stringArray[n24] = "" + this.intclan + " has challenged your clan to a stage battle:";
                                                                lArray[n24] = this.getLvalue(string24, 3);
                                                                stringArray4[n24] = "";
                                                                stringArray[++n24] = "Y|" + this.getSvalue(string24, 5) + "|" + this.getSvalue(string24, 4) + "|" + this.getSvalue(string24, 6) + "|" + this.getSvalue(string24, 7) + "|";
                                                                nArray[n24] = 20;
                                                                stringArray[++n24] = "";
                                                                nArray[n24] = 167;
                                                                ++n24;
                                                                String string29 = this.getSvalue(string24, 5);
                                                                if (string29.length() > 20) {
                                                                    string29 = "" + string29.substring(0, 20) + "...";
                                                                }
                                                                stringArray[n24] = "If you win you will take " + this.intclan + "'s stage :  [ " + string29 + " ]";
                                                                nArray[n24] = -1;
                                                                ++n24;
                                                                string29 = this.getSvalue(string24, 4);
                                                                if (string29.length() > 20) {
                                                                    string29 = "" + string29.substring(0, 20) + "...";
                                                                }
                                                                stringArray[n24] = "If you lose you will give " + this.intclan + " your clan's stage :  [ " + string29 + " ]";
                                                                nArray[n24] = -1;
                                                                ++n24;
                                                                if (!string24.endsWith("|out|")) {
                                                                    stringArray[n24] = "(You accept this stage battle by creating 3 more games to be added to it.)";
                                                                    nArray[n24] = -1;
                                                                    ++n24;
                                                                }
                                                            }
                                                        }
                                                        if (n29 == 5) {
                                                            stringArray[n24] = "A stage battle has now started between your clan and " + this.intclan + " !";
                                                            lArray[n24] = this.getLvalue(string24, 3);
                                                            stringArray4[n24] = "";
                                                            stringArray[++n24] = "(See the bar at the top of the page for more details.)";
                                                            nArray[n24] = -1;
                                                            stringArray[++n24] = "Arrange to meet " + this.intclan + " at a chosen room in a server on a specific date to play the battle.";
                                                            nArray[n24] = -1;
                                                            stringArray[++n24] = "Use the 'Share a Relative Date' option to help you organize a time that is suitable for all.";
                                                            nArray[n24] = -1;
                                                            ++n24;
                                                        }
                                                        if (n29 == 6) {
                                                            stringArray[n24] = "A car battle has now started between your clan and " + this.intclan + " !";
                                                            lArray[n24] = this.getLvalue(string24, 3);
                                                            stringArray4[n24] = "";
                                                            stringArray[++n24] = "(See the bar at the top of the page for more details.)";
                                                            nArray[n24] = -1;
                                                            stringArray[++n24] = "Arrange to meet " + this.intclan + " at a chosen room in a server on a specific date to play the battle.";
                                                            nArray[n24] = -1;
                                                            stringArray[++n24] = "Use the 'Share a Relative Date' option to help you organize a time that is suitable for all.";
                                                            nArray[n24] = -1;
                                                            ++n24;
                                                        }
                                                        if (n29 == 7) {
                                                            stringArray[n24] = "A war has now started between your clan and " + this.intclan + " !";
                                                            lArray[n24] = this.getLvalue(string24, 3);
                                                            stringArray4[n24] = "";
                                                            ++n24;
                                                            if (this.loadwstat == 1) {
                                                                int n38;
                                                                int n39 = -1;
                                                                int n40 = 0;
                                                                n27 = -1;
                                                                n28 = 0;
                                                                for (n38 = 0; n38 < this.ncc; ++n38) {
                                                                    if (this.xt.clan.toLowerCase().equals(this.conclan[n38].toLowerCase())) {
                                                                        n40 = this.totp[n38];
                                                                        n39 = n38;
                                                                    }
                                                                    if (!this.intclan.toLowerCase().equals(this.conclan[n38].toLowerCase())) continue;
                                                                    n28 = this.totp[n38];
                                                                    n27 = n38;
                                                                }
                                                                n38 = n28 + 1;
                                                                int n41 = n40 + 1;
                                                                if (n41 > n28) {
                                                                    n41 = n28;
                                                                }
                                                                if (n39 != -1) {
                                                                    for (n26 = 0; n26 < this.nvc[n39]; ++n26) {
                                                                        if (!this.intclan.toLowerCase().equals(this.verclan[n39][n26].toLowerCase())) continue;
                                                                        if ((n38 -= this.points[n39][n26]) >= 0) break;
                                                                        n38 = 0;
                                                                        break;
                                                                    }
                                                                }
                                                                stringArray[n24] = "If you win this war, your clan would get:  [ " + n38 + " points ]   &  " + this.intclan + " would lose:  [ " + n41 + " points ]";
                                                                nArray[n24] = -1;
                                                                ++n24;
                                                                n38 = n40 + 1;
                                                                n41 = n28 + 1;
                                                                if (n41 > n40) {
                                                                    n41 = n40;
                                                                }
                                                                if (n27 != -1) {
                                                                    for (n26 = 0; n26 < this.nvc[n27]; ++n26) {
                                                                        if (!this.xt.clan.toLowerCase().equals(this.verclan[n27][n26].toLowerCase())) continue;
                                                                        if ((n38 -= this.points[n27][n26]) >= 0) break;
                                                                        n38 = 0;
                                                                        break;
                                                                    }
                                                                }
                                                                stringArray[n24] = "If you lose this war, your clan would lose:  [ " + n41 + " points ]   &  " + this.intclan + " would get:  [ " + n38 + " points ]";
                                                                nArray[n24] = -1;
                                                                ++n24;
                                                            }
                                                            stringArray[n24] = "(See the bar at the top of the page for more details.)";
                                                            nArray[n24] = -1;
                                                            stringArray[++n24] = "Arrange to meet " + this.intclan + " at a chosen room in a server on a specific date to play the war.";
                                                            nArray[n24] = -1;
                                                            stringArray[++n24] = "Use the 'Share a Relative Date' option to help you organize a time that is suitable for all.";
                                                            nArray[n24] = -1;
                                                            ++n24;
                                                        }
                                                        if (n29 == 8) {
                                                            stringArray[n24] = string26.toLowerCase().equals(this.xt.clan.toLowerCase()) ? "Your clan has defeated " + this.intclan + " in the war, congratulations!" : "Your clan has lost the war against " + this.intclan + ".";
                                                            lArray[n24] = this.getLvalue(string24, 3);
                                                            stringArray4[n24] = "";
                                                            stringArray[++n24] = string26.toLowerCase().equals(this.xt.clan.toLowerCase()) ? "Your clan won:  [ " + this.getSvalue(string24, 5) + " points ]   &  " + this.intclan + " lost:  [ " + this.getSvalue(string24, 6) + " points ]" : "Your clan lost:  [ " + this.getSvalue(string24, 6) + " points ]   &  " + this.intclan + " won:  [ " + this.getSvalue(string24, 5) + " points ]";
                                                            nArray[n24] = -1;
                                                            stringArray[++n24] = "" + this.getSvalue(string24, 4) + "|";
                                                            nArray[n24] = 80;
                                                            stringArray[++n24] = "";
                                                            nArray[n24] = 167;
                                                            ++n24;
                                                        }
                                                        if (n29 == 9) {
                                                            String string30 = "";
                                                            if (string26.toLowerCase().equals(this.xt.clan.toLowerCase())) {
                                                                stringArray[n24] = "Your clan has defeated " + this.intclan + " in the car battle, congratulations!";
                                                                lArray[n24] = this.getLvalue(string24, 3);
                                                                stringArray4[n24] = "";
                                                                stringArray[++n24] = "You took " + this.intclan + "'s car :  [ " + this.getSvalue(string24, 5) + " ] !";
                                                                nArray[n24] = -1;
                                                                ++n24;
                                                                string30 = this.xt.clan;
                                                            } else {
                                                                stringArray[n24] = "Your clan has lost the car battle against " + this.intclan + ".";
                                                                lArray[n24] = this.getLvalue(string24, 3);
                                                                stringArray4[n24] = "";
                                                                stringArray[++n24] = "" + this.intclan + " took your car :  [ " + this.getSvalue(string24, 5) + " ] !";
                                                                nArray[n24] = -1;
                                                                ++n24;
                                                                string30 = this.intclan;
                                                            }
                                                            stringArray[n24] = "" + this.getSvalue(string24, 4) + "|" + this.getSvalue(string24, 5) + "|" + string30 + "|";
                                                            nArray[n24] = 90;
                                                            stringArray[++n24] = "";
                                                            nArray[n24] = 167;
                                                            ++n24;
                                                        }
                                                        if (n29 != 10) continue;
                                                        String string31 = "";
                                                        if (string26.toLowerCase().equals(this.xt.clan.toLowerCase())) {
                                                            stringArray[n24] = "Your clan has defeated " + this.intclan + " in the stage battle, congratulations!";
                                                            lArray[n24] = this.getLvalue(string24, 3);
                                                            stringArray4[n24] = "";
                                                            ++n24;
                                                            string25 = this.getSvalue(string24, 5);
                                                            if (string25.length() > 20) {
                                                                string25 = "" + string25.substring(0, 20) + "...";
                                                            }
                                                            stringArray[n24] = "You took " + this.intclan + "'s stage :  [ " + string25 + " ] !";
                                                            nArray[n24] = -1;
                                                            ++n24;
                                                            string31 = this.xt.clan;
                                                        } else {
                                                            stringArray[n24] = "Your clan has lost the stage battle against " + this.intclan + ".";
                                                            lArray[n24] = this.getLvalue(string24, 3);
                                                            stringArray4[n24] = "";
                                                            ++n24;
                                                            string25 = this.getSvalue(string24, 5);
                                                            if (string25.length() > 20) {
                                                                string25 = "" + string25.substring(0, 20) + "...";
                                                            }
                                                            stringArray[n24] = "" + this.intclan + " took your stage :  [ " + string25 + " ] !";
                                                            nArray[n24] = -1;
                                                            ++n24;
                                                            string31 = this.intclan;
                                                        }
                                                        stringArray[n24] = "" + this.getSvalue(string24, 4) + "|" + this.getSvalue(string24, 5) + "|" + string31 + "|";
                                                        nArray[n24] = 100;
                                                        stringArray[++n24] = "";
                                                        nArray[n24] = 167;
                                                        ++n24;
                                                        continue;
                                                    }
                                                    stringArray[n24] = string24;
                                                    try {
                                                        this.rd.setFont(new Font("Tahoma", 0, 11));
                                                        this.ftm = this.rd.getFontMetrics();
                                                        String string32 = "";
                                                        for (int i = 0; i < string24.length(); ++i) {
                                                            if (this.ftm.stringWidth(string32 = string32 + string24.charAt(i)) <= 540) continue;
                                                            if (string32.lastIndexOf(" ") != -1) {
                                                                stringArray[n24] = string32.substring(0, string32.lastIndexOf(" "));
                                                                string32 = string32.substring(string32.lastIndexOf(" ") + 1, string32.length());
                                                            } else {
                                                                stringArray[n24] = string32;
                                                                string32 = "";
                                                            }
                                                            nArray[n24] = -1;
                                                            ++n24;
                                                        }
                                                        stringArray[n24] = string32;
                                                    }
                                                    catch (Exception exception) {
                                                        // empty catch block
                                                    }
                                                    nArray[n24] = -1;
                                                    ++n24;
                                                }
                                                for (int i = 0; i < n24; ++i) {
                                                    this.iline[i] = stringArray[i];
                                                    this.ilinetyp[i] = nArray[i];
                                                    this.ictimes[i] = lArray[i];
                                                    this.itimes[i] = stringArray4[i];
                                                }
                                                this.nil = n24;
                                                this.readint = 2;
                                                this.lastint = "" + this.ictime[n23];
                                                if (this.icheck[n23].toLowerCase().indexOf(this.xt.nickname.toLowerCase()) == -1) {
                                                    int n42 = n23;
                                                    this.icheck[n42] = this.icheck[n42] + "#" + this.xt.nickname + "#";
                                                    try {
                                                        this.dout.println("101|41|" + this.xt.nickname + "|" + this.xt.clan + "|" + this.intclan + "|");
                                                        string = this.din.readLine();
                                                    }
                                                    catch (Exception exception) {
                                                        // empty catch block
                                                    }
                                                }
                                                this.spos4 = 208;
                                                break block628;
                                            }
                                            this.readint = 3;
                                        }
                                        catch (Exception exception) {
                                            this.readint = 4;
                                        }
                                    }
                                }
                                if (this.readint == 2) {
                                    for (int i = 0; i < this.nil; ++i) {
                                        if (this.ilinetyp[i] >= 0 && this.ilinetyp[i] != 167 && this.ictimes[i] > 0L) {
                                            try {
                                                String string33;
                                                long l = this.ntime - this.ictimes[i];
                                                if (l >= 1000L && l < 60000L) {
                                                    this.itimes[i] = "seconds ago";
                                                }
                                                if (l >= 60000L && l < 3600000L) {
                                                    int n43 = (int)(l / 60000L);
                                                    string33 = "s";
                                                    if (n43 == 1) {
                                                        string33 = "";
                                                    }
                                                    this.itimes[i] = "" + n43 + " minute" + string33 + " ago";
                                                }
                                                if (l >= 3600000L && l < 86400000L) {
                                                    int n44 = (int)(l / 3600000L);
                                                    string33 = "s";
                                                    if (n44 == 1) {
                                                        string33 = "";
                                                    }
                                                    this.itimes[i] = "" + n44 + " hour" + string33 + " ago";
                                                }
                                                if (l < 86400000L) continue;
                                                int n45 = (int)(l / 86400000L);
                                                string33 = "s";
                                                if (n45 == 1) {
                                                    string33 = "";
                                                }
                                                this.itimes[i] = "" + n45 + " day" + string33 + " ago";
                                            }
                                            catch (Exception exception) {
                                                this.itimes[i] = "";
                                            }
                                            continue;
                                        }
                                        this.itimes[i] = "";
                                    }
                                }
                                this.intclanbgpng(this.intclan);
                            }
                            if (this.sendint == 2) {
                                this.gs.mmsg.setText(" ");
                                this.sendint = 0;
                                if (this.gs.sendtyp.getSelectedIndex() > 1) {
                                    this.gs.sendtyp.select(0);
                                }
                            }
                            if (this.openi == 10) {
                                block629: {
                                    if (this.viewgame2 == 1) {
                                        this.vwscorex = 0;
                                        this.vwscorei = 0;
                                        String string34 = "pending_war";
                                        if (this.nvgames2 == 2) {
                                            string34 = "pending_battle";
                                        }
                                        if (this.nvgames2 == 9) {
                                            string34 = "war";
                                        }
                                        if (this.nvgames2 == 5) {
                                            string34 = "battle";
                                        }
                                        try {
                                            URL uRL = new URL("http://multiplayer.needformadness.com/interact/" + string34 + "/" + this.viewwar2 + ".txt");
                                            uRL.openConnection().setConnectTimeout(2000);
                                            String string35 = uRL.openConnection().getContentType();
                                            if (string35.equals("text/plain")) {
                                                int n46;
                                                DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                                                String string36 = "";
                                                for (n46 = 0; (string36 = dataInputStream.readLine()) != null && n46 < this.nvgames2; ++n46) {
                                                    String string37 = this.getSvalue(string36, 0);
                                                    if (string37.startsWith("#")) {
                                                        int n47 = 1;
                                                        try {
                                                            n47 = Integer.valueOf(string37.substring(1));
                                                        }
                                                        catch (Exception exception) {
                                                            n47 = 1;
                                                        }
                                                        string37 = "NFM 1  -  Stage " + n47 + "";
                                                        if (n47 > 10) {
                                                            string37 = "NFM 2  -  Stage " + (n47 - 10) + "";
                                                        }
                                                        if (n47 > 27) {
                                                            string37 = "NFM Multiplayer  -  Stage " + (n47 - 27) + "";
                                                        }
                                                    }
                                                    this.vwstages2[n46] = string37;
                                                    this.vwlaps2[n46] = this.getvalue(string36, 1);
                                                    this.vwcars2[n46] = this.getvalue(string36, 2);
                                                    this.vwclass2[n46] = this.getvalue(string36, 3);
                                                    this.vwfix2[n46] = this.getvalue(string36, 4);
                                                    this.vwinner[n46] = this.getSvalue(string36, 5);
                                                    if (this.vwinner[n46].toLowerCase().equals(this.xt.clan.toLowerCase())) {
                                                        ++this.vwscorex;
                                                    }
                                                    if (!this.vwinner[n46].toLowerCase().equals(this.intclan.toLowerCase())) continue;
                                                    ++this.vwscorei;
                                                }
                                                dataInputStream.close();
                                                this.viewgame2 = n46 != 0 ? 2 : 4;
                                                break block629;
                                            }
                                            this.viewgame2 = 3;
                                        }
                                        catch (Exception exception) {
                                            this.viewgame2 = 4;
                                        }
                                    }
                                }
                                if ((this.gs.sendtyp.getSelectedIndex() == 4 || this.gs.sendtyp.getSelectedIndex() == 5 || this.gs.sendtyp.getSelectedIndex() == 6 || this.gs.sendtyp.getSelectedIndex() == 7) && this.gs.senditem.getSelectedIndex() == 3 && this.isel == 3) {
                                    this.loadiclanstages(this.xt.clan);
                                    this.isel = 4;
                                }
                                if (this.gs.sendtyp.getSelectedIndex() == 3 && this.ifas == 0) {
                                    this.loadiclancars(this.intclan);
                                    this.ifas = 1;
                                }
                                if (this.gs.sendtyp.getSelectedIndex() == 3 && this.ifas == 2) {
                                    this.loadiclancars(this.xt.clan);
                                    this.ifas = 3;
                                }
                                if (this.gs.sendtyp.getSelectedIndex() == 3 && this.gs.senditem.getSelectedIndex() == 3 && this.ifas == 5 && this.isel == 3) {
                                    this.loadiclanstages(this.xt.clan);
                                    this.isel = 4;
                                }
                                if (this.gs.sendtyp.getSelectedIndex() == 2 && this.ifas == 0) {
                                    this.loadiclanstages(this.intclan);
                                    this.ifas = 1;
                                }
                                if (this.gs.sendtyp.getSelectedIndex() == 2 && this.ifas == 2) {
                                    this.loadiclanstages(this.xt.clan);
                                    this.ifas = 3;
                                }
                                if (this.gs.sendtyp.getSelectedIndex() == 2 && this.gs.senditem.getSelectedIndex() == 3 && this.ifas == 5 && this.isel == 3) {
                                    this.loadiclanstages(this.xt.clan);
                                    this.isel = 4;
                                }
                                if (this.sendint == 1) {
                                    try {
                                        String string38 = "#nada#";
                                        for (int i = 0; i < this.ni; ++i) {
                                            if (!this.iclan[i].equals(this.intclan)) continue;
                                            string38 = this.iconvo[i];
                                            break;
                                        }
                                        string2 = "101|39|" + this.xt.nickname + "|" + this.xt.nickey + "|" + this.xt.clan + "|" + this.xt.clankey + "|" + this.intclan + "|" + string38 + "|" + this.gs.sendtyp.getSelectedIndex() + "|";
                                        if (this.gs.sendtyp.getSelectedIndex() == 0) {
                                            String string39 = this.gs.mmsg.getText().replace("|", ":").replaceAll("[\\t\\n\\r]", "|");
                                            String string40 = "";
                                            int n48 = 0;
                                            for (int i = 0; i < string39.length(); ++i) {
                                                String string41 = "" + string39.charAt(i);
                                                n48 = string41.equals("|") ? ++n48 : 0;
                                                if (n48 > 1) continue;
                                                string40 = string40 + string41;
                                            }
                                            string2 = string2 + "" + string40 + "||";
                                        }
                                        if (this.gs.sendtyp.getSelectedIndex() == 1) {
                                            Calendar calendar = Calendar.getInstance();
                                            long l = calendar.getTimeInMillis();
                                            calendar.roll(5, this.gs.senditem.getSelectedIndex());
                                            int n49 = this.gs.datat.getSelectedIndex() + 12;
                                            if (n49 >= 24) {
                                                n49 -= 24;
                                            }
                                            calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), n49, 0);
                                            l = calendar.getTimeInMillis() - l;
                                            string2 = string2 + "" + l + "|";
                                        }
                                        if (this.gs.sendtyp.getSelectedIndex() == 2 || this.gs.sendtyp.getSelectedIndex() == 3) {
                                            string2 = string2 + "" + this.itake + "|" + this.igive + "|";
                                            if (!this.sendwarnum) {
                                                for (int i = 0; i < 2; ++i) {
                                                    string2 = string2 + "" + this.wstages[i] + "|" + this.wlaps[i] + "|" + this.wcars[i] + "|" + this.wclass[i] + "|" + this.wfix[i] + "|";
                                                }
                                            } else {
                                                string2 = string2 + "warnum#|" + this.warnum + "|";
                                                this.sendwarnum = false;
                                            }
                                        }
                                        if (this.gs.sendtyp.getSelectedIndex() == 4) {
                                            if (!this.sendwarnum) {
                                                for (int i = 0; i < 4; ++i) {
                                                    string2 = string2 + "" + this.wstages[i] + "|" + this.wlaps[i] + "|" + this.wcars[i] + "|" + this.wclass[i] + "|" + this.wfix[i] + "|";
                                                }
                                            } else {
                                                string2 = string2 + "warnum#|" + this.warnum + "|";
                                                this.sendwarnum = false;
                                            }
                                        }
                                        if (this.gs.sendtyp.getSelectedIndex() == 5 || this.gs.sendtyp.getSelectedIndex() == 6) {
                                            string2 = string2 + "" + this.itake + "|" + this.igive + "|";
                                            if (!this.sendwarnum) {
                                                string2 = string2 + "" + this.sendwar + "|";
                                                for (int i = 0; i < 3; ++i) {
                                                    string2 = string2 + "" + this.wstages[i] + "|" + this.wlaps[i] + "|" + this.wcars[i] + "|" + this.wclass[i] + "|" + this.wfix[i] + "|";
                                                }
                                            } else {
                                                string2 = string2 + "warnum#|" + this.warnum + "|";
                                                this.sendwarnum = false;
                                            }
                                        }
                                        if (this.gs.sendtyp.getSelectedIndex() == 7) {
                                            if (!this.sendwarnum) {
                                                string2 = string2 + "" + this.sendwar + "|";
                                                for (int i = 0; i < 5; ++i) {
                                                    string2 = string2 + "" + this.wstages[i] + "|" + this.wlaps[i] + "|" + this.wcars[i] + "|" + this.wclass[i] + "|" + this.wfix[i] + "|";
                                                }
                                            } else {
                                                string2 = string2 + "warnum#|" + this.warnum + "|";
                                                this.sendwarnum = false;
                                            }
                                        }
                                        this.dout.println(string2);
                                        string = this.din.readLine();
                                        if (string.equals("OK")) {
                                            this.sendint = 2;
                                        } else if (string.equals("SUJ")) {
                                            this.itab = 2;
                                            this.sendint = 0;
                                            this.openi = 0;
                                            this.readint = 0;
                                        } else if (string.equals("failfile")) {
                                            this.readint = 6;
                                            this.sendint = 0;
                                            this.gs.mmsg.setText(" ");
                                            this.gs.sendtyp.select(0);
                                        } else {
                                            this.readint = 5;
                                            this.sendint = 0;
                                        }
                                    }
                                    catch (Exception exception) {
                                        this.readint = 5;
                                        this.sendint = 0;
                                    }
                                }
                            }
                        }
                        if (this.itab == 2 && !this.xt.clan.equals("")) {
                            block631: {
                                block630: {
                                    try {
                                        string2 = "101|36|" + this.xt.clan + "|" + this.xt.clankey + "|" + this.readclan + "|" + this.xt.nickname + "|";
                                        this.dout.println(string2);
                                        string = this.din.readLine();
                                        if (!string.startsWith("RECIVE")) break block630;
                                        this.readclan = -3;
                                        if (this.loadwstat == 0) {
                                            this.loadchamps();
                                        }
                                        int n50 = this.getvalue(string, 1);
                                        this.cadmin = this.getvalue(string, 2);
                                        int n51 = 0;
                                        String[] stringArray = new String[1000];
                                        int[] nArray = new int[1000];
                                        long[] lArray = new long[1000];
                                        String[] stringArray5 = new String[1000];
                                        stringArray[n51] = "";
                                        nArray[n51] = 167;
                                        ++n51;
                                        DataInputStream dataInputStream = new DataInputStream(this.socket.getInputStream());
                                        byte[] byArray = new byte[n50];
                                        dataInputStream.readFully(byArray);
                                        string = this.din.readLine();
                                        dataInputStream = new DataInputStream(new ByteArrayInputStream(byArray));
                                        object2 = "";
                                        while ((object2 = dataInputStream.readLine()) != null) {
                                            if (((String)object2).startsWith("|")) {
                                                int n52;
                                                int n53;
                                                int n54;
                                                int n55;
                                                stringArray[n51] = "";
                                                nArray[n51] = 167;
                                                ++n51;
                                                String string42 = this.getSvalue((String)object2, 1);
                                                if (string42.toLowerCase().equals(this.xt.nickname.toLowerCase())) {
                                                    string42 = "You";
                                                }
                                                nArray[n51] = this.getvalue((String)object2, 2);
                                                int n56 = nArray[n51];
                                                if (n56 == 0) {
                                                    stringArray[n51] = "" + string42 + " wrote:";
                                                    lArray[n51] = this.getLvalue((String)object2, 3);
                                                    stringArray5[n51] = "";
                                                    ++n51;
                                                }
                                                if (n56 == 1) {
                                                    stringArray[n51] = string42.equals("You") ? "You have shared the following date:" : "" + string42 + " has shared the following date:";
                                                    lArray[n51] = this.getLvalue((String)object2, 3);
                                                    stringArray5[n51] = "";
                                                    Calendar calendar = Calendar.getInstance();
                                                    long l = calendar.getTimeInMillis() - (this.ntime - lArray[n51]) + this.getLvalue((String)object2, 4);
                                                    if (l > 0L) {
                                                        calendar.setTimeInMillis(l);
                                                    }
                                                    ++n51;
                                                    n55 = calendar.get(11);
                                                    String string43 = "AM";
                                                    if (calendar.get(12) > 30 && ++n55 == 24) {
                                                        n55 -= 24;
                                                    }
                                                    if (n55 >= 12) {
                                                        string43 = "PM";
                                                    }
                                                    if (n55 > 12) {
                                                        n55 -= 12;
                                                    }
                                                    if (n55 == 0) {
                                                        n55 = 12;
                                                    }
                                                    try {
                                                        stringArray[n51] = "[  " + this.wday[calendar.get(7) - 1] + "  -  " + this.month[calendar.get(2)] + " " + calendar.get(5) + ",  " + n55 + " " + string43 + "  ]";
                                                    }
                                                    catch (Exception exception) {
                                                        stringArray[n51] = "Error occurred while calculating this date.";
                                                    }
                                                    nArray[n51] = -1;
                                                    stringArray[++n51] = "(Please make sure your computer's calendar/clock is adjusted correctly, to read this date in your local time.)";
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                }
                                                if (n56 == 2) {
                                                    String string44 = this.getSvalue((String)object2, 4);
                                                    stringArray[n51] = string42.equals("You") ? "You have suggested declaring war on [ " + string44 + " ] :" : "" + string42 + " suggested declaring war on [ " + string44 + " ] :";
                                                    lArray[n51] = this.getLvalue((String)object2, 3);
                                                    stringArray5[n51] = "";
                                                    stringArray[++n51] = "" + string44 + "|" + this.getSvalue((String)object2, 5) + "|" + this.getSvalue((String)object2, 6) + "|";
                                                    nArray[n51] = 20;
                                                    stringArray[++n51] = "";
                                                    nArray[n51] = 167;
                                                    ++n51;
                                                    if (this.loadwstat == 1 && !((String)object2).endsWith("|out|")) {
                                                        int n57;
                                                        int n58 = -1;
                                                        n54 = 0;
                                                        n55 = -1;
                                                        int n59 = 0;
                                                        for (n57 = 0; n57 < this.ncc; ++n57) {
                                                            if (this.xt.clan.toLowerCase().equals(this.conclan[n57].toLowerCase())) {
                                                                n54 = this.totp[n57];
                                                                n58 = n57;
                                                            }
                                                            if (!string44.toLowerCase().equals(this.conclan[n57].toLowerCase())) continue;
                                                            n59 = this.totp[n57];
                                                            n55 = n57;
                                                        }
                                                        n57 = n59 + 1;
                                                        n53 = n54 + 1;
                                                        if (n53 > n59) {
                                                            n53 = n59;
                                                        }
                                                        if (n58 != -1) {
                                                            for (n52 = 0; n52 < this.nvc[n58]; ++n52) {
                                                                if (!string44.toLowerCase().equals(this.verclan[n58][n52].toLowerCase())) continue;
                                                                if ((n57 -= this.points[n58][n52]) >= 0) break;
                                                                n57 = 0;
                                                                break;
                                                            }
                                                        }
                                                        stringArray[n51] = "If you win this war, your clan would get:  [ " + n57 + " points ]   &  " + string44 + " would lose:  [ " + n53 + " points ]";
                                                        nArray[n51] = -1;
                                                        ++n51;
                                                        n57 = n54 + 1;
                                                        n53 = n59 + 1;
                                                        if (n53 > n54) {
                                                            n53 = n54;
                                                        }
                                                        if (n55 != -1) {
                                                            for (n52 = 0; n52 < this.nvc[n55]; ++n52) {
                                                                if (!this.xt.clan.toLowerCase().equals(this.verclan[n55][n52].toLowerCase())) continue;
                                                                if ((n57 -= this.points[n55][n52]) >= 0) break;
                                                                n57 = 0;
                                                                break;
                                                            }
                                                        }
                                                        stringArray[n51] = "If you lose this war, your clan would lose:  [ " + n53 + " points ]   &  " + string44 + " would get:  [ " + n57 + " points ]";
                                                        nArray[n51] = -1;
                                                        ++n51;
                                                    }
                                                    if (!((String)object2).endsWith("|out|")) {
                                                        stringArray[n51] = "(This needs to be approved by the Clan Leader or an Admin to be declared on " + string44 + ".)";
                                                        nArray[n51] = -1;
                                                        ++n51;
                                                    }
                                                }
                                                if (n56 == 3) {
                                                    String string45 = this.getSvalue((String)object2, 4);
                                                    stringArray[n51] = string42.equals("You") ? "You have suggested to car battle with [ " + string45 + " ] :" : "" + string42 + " suggested to car battle with [ " + string45 + " ] :";
                                                    lArray[n51] = this.getLvalue((String)object2, 3);
                                                    stringArray5[n51] = "";
                                                    stringArray[++n51] = "" + string45 + "|" + this.getSvalue((String)object2, 5) + "|" + this.getSvalue((String)object2, 6) + "|" + this.getSvalue((String)object2, 7) + "|" + this.getSvalue((String)object2, 8) + "|";
                                                    nArray[n51] = 30;
                                                    stringArray[++n51] = "";
                                                    nArray[n51] = 167;
                                                    stringArray[++n51] = "If you win you will take " + string45 + "'s car :  [ " + this.getSvalue((String)object2, 5) + " ]";
                                                    nArray[n51] = -1;
                                                    stringArray[++n51] = "If you lose you will give " + string45 + " your clan's car :  [ " + this.getSvalue((String)object2, 6) + " ]";
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                    if (!((String)object2).endsWith("|out|")) {
                                                        stringArray[n51] = "(This needs to be approved by the Clan Leader or an Admin to be sent to " + string45 + ".)";
                                                        nArray[n51] = -1;
                                                        ++n51;
                                                    }
                                                }
                                                if (n56 == 4) {
                                                    String string46 = this.getSvalue((String)object2, 4);
                                                    stringArray[n51] = string42.equals("You") ? "You have suggested to stage battle with [ " + string46 + " ] :" : "" + string42 + " suggested to stage battle with [ " + string46 + " ] :";
                                                    lArray[n51] = this.getLvalue((String)object2, 3);
                                                    stringArray5[n51] = "";
                                                    stringArray[++n51] = "" + string46 + "|" + this.getSvalue((String)object2, 5) + "|" + this.getSvalue((String)object2, 6) + "|" + this.getSvalue((String)object2, 7) + "|" + this.getSvalue((String)object2, 8) + "|";
                                                    nArray[n51] = 40;
                                                    stringArray[++n51] = "";
                                                    nArray[n51] = 167;
                                                    ++n51;
                                                    String string47 = this.getSvalue((String)object2, 5);
                                                    if (string47.length() > 20) {
                                                        string47 = "" + string47.substring(0, 20) + "...";
                                                    }
                                                    stringArray[n51] = "If you win you will take " + string46 + "'s stage :  [ " + string47 + " ]";
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                    string47 = this.getSvalue((String)object2, 6);
                                                    if (string47.length() > 20) {
                                                        string47 = "" + string47.substring(0, 20) + "...";
                                                    }
                                                    stringArray[n51] = "If you lose you will give " + string46 + " your clan's stage :  [ " + string47 + " ]";
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                    if (!((String)object2).endsWith("|out|")) {
                                                        stringArray[n51] = "(This needs to be approved by the Clan Leader or an Admin to be sent to " + string46 + ".)";
                                                        nArray[n51] = -1;
                                                        ++n51;
                                                    }
                                                }
                                                if (n56 == 5) {
                                                    String string48 = this.getSvalue((String)object2, 4);
                                                    stringArray[n51] = string42.equals("You") ? "You have suggested accepting to go to war with [ " + string48 + " ] :" : "" + string42 + " suggested accepting to go to war with [ " + string48 + " ] :";
                                                    lArray[n51] = this.getLvalue((String)object2, 3);
                                                    stringArray5[n51] = "";
                                                    stringArray[++n51] = "" + string48 + "|" + this.getSvalue((String)object2, 5) + "|" + this.getSvalue((String)object2, 6) + "|";
                                                    nArray[n51] = 50;
                                                    stringArray[++n51] = "";
                                                    nArray[n51] = 167;
                                                    ++n51;
                                                    if (this.loadwstat == 1 && !((String)object2).endsWith("|out|")) {
                                                        int n60;
                                                        int n61 = -1;
                                                        n54 = 0;
                                                        n55 = -1;
                                                        int n62 = 0;
                                                        for (n60 = 0; n60 < this.ncc; ++n60) {
                                                            if (this.xt.clan.toLowerCase().equals(this.conclan[n60].toLowerCase())) {
                                                                n54 = this.totp[n60];
                                                                n61 = n60;
                                                            }
                                                            if (!string48.toLowerCase().equals(this.conclan[n60].toLowerCase())) continue;
                                                            n62 = this.totp[n60];
                                                            n55 = n60;
                                                        }
                                                        n60 = n62 + 1;
                                                        n53 = n54 + 1;
                                                        if (n53 > n62) {
                                                            n53 = n62;
                                                        }
                                                        if (n61 != -1) {
                                                            for (n52 = 0; n52 < this.nvc[n61]; ++n52) {
                                                                if (!string48.toLowerCase().equals(this.verclan[n61][n52].toLowerCase())) continue;
                                                                if ((n60 -= this.points[n61][n52]) >= 0) break;
                                                                n60 = 0;
                                                                break;
                                                            }
                                                        }
                                                        stringArray[n51] = "If you win this war, your clan would get:  [ " + n60 + " points ]   &  " + string48 + " would lose:  [ " + n53 + " points ]";
                                                        nArray[n51] = -1;
                                                        ++n51;
                                                        n60 = n54 + 1;
                                                        n53 = n62 + 1;
                                                        if (n53 > n54) {
                                                            n53 = n54;
                                                        }
                                                        if (n55 != -1) {
                                                            for (n52 = 0; n52 < this.nvc[n55]; ++n52) {
                                                                if (!this.xt.clan.toLowerCase().equals(this.verclan[n55][n52].toLowerCase())) continue;
                                                                if ((n60 -= this.points[n55][n52]) >= 0) break;
                                                                n60 = 0;
                                                                break;
                                                            }
                                                        }
                                                        stringArray[n51] = "If you lose this war, your clan would lose:  [ " + n53 + " points ]   &  " + string48 + " would get:  [ " + n60 + " points ]";
                                                        nArray[n51] = -1;
                                                        ++n51;
                                                    }
                                                    if (!((String)object2).endsWith("|out|")) {
                                                        stringArray[n51] = "(This needs to be approved by the Clan Leader or an Admin to be declared on " + string48 + ".)";
                                                        nArray[n51] = -1;
                                                        ++n51;
                                                    }
                                                }
                                                if (n56 == 6) {
                                                    String string49 = this.getSvalue((String)object2, 4);
                                                    stringArray[n51] = string42.equals("You") ? "You have suggested accepting to car battle with [ " + string49 + " ] :" : "" + string42 + " suggested accepting to car battle with [ " + string49 + " ] :";
                                                    lArray[n51] = this.getLvalue((String)object2, 3);
                                                    stringArray5[n51] = "";
                                                    stringArray[++n51] = "" + string49 + "|" + this.getSvalue((String)object2, 5) + "|" + this.getSvalue((String)object2, 6) + "|" + this.getSvalue((String)object2, 7) + "|" + this.getSvalue((String)object2, 8) + "|";
                                                    nArray[n51] = 60;
                                                    stringArray[++n51] = "";
                                                    nArray[n51] = 167;
                                                    stringArray[++n51] = "If you win you will take " + string49 + "'s car :  [ " + this.getSvalue((String)object2, 5) + " ]";
                                                    nArray[n51] = -1;
                                                    stringArray[++n51] = "If you lose you will give " + string49 + " your clan's car :  [ " + this.getSvalue((String)object2, 6) + " ]";
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                    if (!((String)object2).endsWith("|out|")) {
                                                        stringArray[n51] = "(This needs to be approved by the Clan Leader or an Admin to be sent to " + string49 + ".)";
                                                        nArray[n51] = -1;
                                                        ++n51;
                                                    }
                                                }
                                                if (n56 == 7) {
                                                    String string50 = this.getSvalue((String)object2, 4);
                                                    stringArray[n51] = string42.equals("You") ? "You have suggested accepting to stage battle with [ " + string50 + " ] :" : "" + string42 + " suggested accepting to stage battle with [ " + string50 + " ] :";
                                                    lArray[n51] = this.getLvalue((String)object2, 3);
                                                    stringArray5[n51] = "";
                                                    stringArray[++n51] = "" + string50 + "|" + this.getSvalue((String)object2, 5) + "|" + this.getSvalue((String)object2, 6) + "|" + this.getSvalue((String)object2, 7) + "|" + this.getSvalue((String)object2, 8) + "|";
                                                    nArray[n51] = 70;
                                                    stringArray[++n51] = "";
                                                    nArray[n51] = 167;
                                                    ++n51;
                                                    String string51 = this.getSvalue((String)object2, 5);
                                                    if (string51.length() > 20) {
                                                        string51 = "" + string51.substring(0, 20) + "...";
                                                    }
                                                    stringArray[n51] = "If you win you will take " + string50 + "'s stage :  [ " + string51 + " ]";
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                    string51 = this.getSvalue((String)object2, 6);
                                                    if (string51.length() > 20) {
                                                        string51 = "" + string51.substring(0, 20) + "...";
                                                    }
                                                    stringArray[n51] = "If you lose you will give " + string50 + " your clan's stage :  [ " + string51 + " ]";
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                    if (!((String)object2).endsWith("|out|")) {
                                                        stringArray[n51] = "(This needs to be approved by the Clan Leader or an Admin to be sent to " + string50 + ".)";
                                                        nArray[n51] = -1;
                                                        ++n51;
                                                    }
                                                }
                                                if (n56 != 8) continue;
                                                int n63 = this.getvalue((String)object2, 4);
                                                String string52 = "taken";
                                                if (n63 == 2) {
                                                    string52 = "re-claimed";
                                                }
                                                stringArray[n51] = "Congratulations!!  Your clan has " + string52 + " the clan wars world championship title!";
                                                lArray[n51] = this.getLvalue((String)object2, 3);
                                                stringArray5[n51] = "";
                                                ++n51;
                                                if (n63 == 1) {
                                                    stringArray[n51] = "Your recent win in the war against " + this.getSvalue((String)object2, 6) + " has given you the championship title!";
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                }
                                                if (n63 == 2) {
                                                    stringArray[n51] = "You have successfully defended your championship title against " + this.getSvalue((String)object2, 6) + "!";
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                }
                                                if (n63 == 3) {
                                                    stringArray[n51] = "A recent war between " + this.getSvalue((String)object2, 5) + " and " + this.getSvalue((String)object2, 6) + " has resulted in a change of points!";
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                }
                                                if (n63 == 4) {
                                                    stringArray[n51] = "Clan " + this.getSvalue((String)object2, 5) + " removed itself from the game which resulted in a change of points, giving you the title!";
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                }
                                                if (n63 != 2) {
                                                    stringArray[n51] = "" + this.xt.clan + " is now the new champion of the world in Need for Madness!";
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                } else {
                                                    stringArray[n51] = "" + this.xt.clan + " still remains the champion of the world in Need for Madness!";
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                }
                                                nArray[n51] = 80;
                                                stringArray[++n51] = "";
                                                nArray[n51] = 167;
                                                ++n51;
                                                continue;
                                            }
                                            stringArray[n51] = object2;
                                            try {
                                                this.rd.setFont(new Font("Tahoma", 0, 11));
                                                this.ftm = this.rd.getFontMetrics();
                                                String string53 = "";
                                                for (int i = 0; i < ((String)object2).length(); ++i) {
                                                    if (this.ftm.stringWidth(string53 = string53 + ((String)object2).charAt(i)) <= 540) continue;
                                                    if (string53.lastIndexOf(" ") != -1) {
                                                        stringArray[n51] = string53.substring(0, string53.lastIndexOf(" "));
                                                        string53 = string53.substring(string53.lastIndexOf(" ") + 1, string53.length());
                                                    } else {
                                                        stringArray[n51] = string53;
                                                        string53 = "";
                                                    }
                                                    nArray[n51] = -1;
                                                    ++n51;
                                                }
                                                stringArray[n51] = string53;
                                            }
                                            catch (Exception exception) {
                                                // empty catch block
                                            }
                                            nArray[n51] = -1;
                                            ++n51;
                                        }
                                        for (int i = 0; i < n51; ++i) {
                                            this.cmline[i] = stringArray[i];
                                            this.cmlinetyp[i] = nArray[i];
                                            this.cmctimes[i] = lArray[i];
                                            this.cmtimes[i] = stringArray5[i];
                                        }
                                        this.cnml = n51;
                                        this.readclan = n50;
                                        this.spos3 = 219;
                                    }
                                    catch (Exception exception) {
                                        this.readclan = -1;
                                    }
                                }
                                if (this.readclan > 0) {
                                    for (int i = 0; i < this.cnml; ++i) {
                                        if (this.cmlinetyp[i] >= 0 && this.cmlinetyp[i] != 167 && this.cmctimes[i] > 0L) {
                                            try {
                                                String string54;
                                                long l = this.ntime - this.cmctimes[i];
                                                if (l >= 1000L && l < 60000L) {
                                                    this.cmtimes[i] = "seconds ago";
                                                }
                                                if (l >= 60000L && l < 3600000L) {
                                                    int n64 = (int)(l / 60000L);
                                                    string54 = "s";
                                                    if (n64 == 1) {
                                                        string54 = "";
                                                    }
                                                    this.cmtimes[i] = "" + n64 + " minute" + string54 + " ago";
                                                }
                                                if (l >= 3600000L && l < 86400000L) {
                                                    int n65 = (int)(l / 3600000L);
                                                    string54 = "s";
                                                    if (n65 == 1) {
                                                        string54 = "";
                                                    }
                                                    this.cmtimes[i] = "" + n65 + " hour" + string54 + " ago";
                                                }
                                                if (l < 86400000L) continue;
                                                int n66 = (int)(l / 86400000L);
                                                string54 = "s";
                                                if (n66 == 1) {
                                                    string54 = "";
                                                }
                                                this.cmtimes[i] = "" + n66 + " day" + string54 + " ago";
                                            }
                                            catch (Exception exception) {
                                                this.cmtimes[i] = "";
                                            }
                                            continue;
                                        }
                                        this.cmtimes[i] = "";
                                    }
                                }
                                this.clanlogopng(this.xt.clan);
                                if (this.sendcmsg == 2) {
                                    this.gs.mmsg.setText(" ");
                                    this.sendcmsg = 0;
                                }
                                if (this.viewgame1 == 1) {
                                    try {
                                        String string55 = "pending_war";
                                        if (this.nvgames1 == 2 || this.nvgames1 == 5) {
                                            string55 = "pending_battle";
                                        }
                                        URL uRL = new URL("http://multiplayer.needformadness.com/interact/" + string55 + "/" + this.viewwar1 + ".txt");
                                        uRL.openConnection().setConnectTimeout(2000);
                                        String string56 = uRL.openConnection().getContentType();
                                        if (string56.equals("text/plain")) {
                                            int n67;
                                            DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                                            String string57 = "";
                                            for (n67 = 0; (string57 = dataInputStream.readLine()) != null && n67 < this.nvgames1; ++n67) {
                                                String string58 = this.getSvalue(string57, 0);
                                                if (string58.startsWith("#")) {
                                                    int n68 = 1;
                                                    try {
                                                        n68 = Integer.valueOf(string58.substring(1));
                                                    }
                                                    catch (Exception exception) {
                                                        n68 = 1;
                                                    }
                                                    string58 = "NFM 1  -  Stage " + n68 + "";
                                                    if (n68 > 10) {
                                                        string58 = "NFM 2  -  Stage " + (n68 - 10) + "";
                                                    }
                                                    if (n68 > 27) {
                                                        string58 = "NFM Multiplayer  -  Stage " + (n68 - 27) + "";
                                                    }
                                                }
                                                this.vwstages1[n67] = string58;
                                                this.vwlaps1[n67] = this.getvalue(string57, 1);
                                                this.vwcars1[n67] = this.getvalue(string57, 2);
                                                this.vwclass1[n67] = this.getvalue(string57, 3);
                                                this.vwfix1[n67] = this.getvalue(string57, 4);
                                            }
                                            dataInputStream.close();
                                            this.viewgame1 = n67 != 0 ? 2 : 4;
                                            break block631;
                                        }
                                        this.viewgame1 = 3;
                                    }
                                    catch (Exception exception) {
                                        this.viewgame1 = 4;
                                    }
                                }
                            }
                            if (this.sendcmsg == 1) {
                                try {
                                    string2 = "101|37|" + this.xt.nickname + "|" + this.xt.nickey + "|" + this.xt.clan + "|" + this.xt.clankey + "|" + this.gs.sendtyp.getSelectedIndex() + "|";
                                    if (this.gs.sendtyp.getSelectedIndex() == 0) {
                                        String string59 = this.gs.mmsg.getText().replace("|", ":").replaceAll("[\\t\\n\\r]", "|");
                                        String string60 = "";
                                        int n69 = 0;
                                        for (int i = 0; i < string59.length(); ++i) {
                                            String string61 = "" + string59.charAt(i);
                                            n69 = string61.equals("|") ? ++n69 : 0;
                                            if (n69 > 1) continue;
                                            string60 = string60 + string61;
                                        }
                                        string2 = string2 + "" + string60 + "||";
                                    }
                                    if (this.gs.sendtyp.getSelectedIndex() == 1) {
                                        Calendar calendar = Calendar.getInstance();
                                        long l = calendar.getTimeInMillis();
                                        calendar.roll(5, this.gs.senditem.getSelectedIndex());
                                        int n70 = this.gs.datat.getSelectedIndex() + 12;
                                        if (n70 >= 24) {
                                            n70 -= 24;
                                        }
                                        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), n70, 0);
                                        l = calendar.getTimeInMillis() - l;
                                        string2 = string2 + "" + l + "|";
                                    }
                                    this.dout.println(string2);
                                    string = this.din.readLine();
                                    if (string.equals("OK")) {
                                        this.sendcmsg = 2;
                                    } else {
                                        this.readclan = -2;
                                        this.sendcmsg = 0;
                                    }
                                }
                                catch (Exception exception) {
                                    this.readclan = -2;
                                    this.sendcmsg = 0;
                                }
                            }
                            this.loadmyclanbg();
                        }
                    }
                    if (this.lg.nmsgs != 0 || this.lg.nfreq != 0 || this.lg.nconf != 0 || this.lg.ncreq != 0 || !this.lg.clanapv.equals("")) {
                        string2 = "101|19|" + this.xt.nickname + "|" + this.xt.nickey + "|";
                        try {
                            this.dout.println(string2);
                            string = this.din.readLine();
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        this.lg.nmsgs = 0;
                        this.lg.nfreq = 0;
                        this.lg.nconf = 0;
                        this.lg.ncreq = 0;
                        this.lg.clanapv = "";
                    }
                    if (this.tab == 0 && this.domon) {
                        string2 = "101|101|" + this.updatec + "|";
                        if (this.updatec <= -11) {
                            for (int i = 0; i < -this.updatec - 10; ++i) {
                                string2 = string2 + "" + this.cnames[20 - i] + "|" + this.sentn[20 - i] + "|";
                            }
                            this.updatec = -2;
                        }
                        try {
                            this.dout.println(string2);
                            string = this.din.readLine();
                            if (string == null) {
                                this.domon = false;
                            }
                        }
                        catch (Exception exception) {
                            this.domon = false;
                        }
                        if (this.domon) {
                            int n71 = this.getvalue(string, 0);
                            if (this.updatec != n71 && this.updatec >= -2) {
                                for (int i = 0; i < 21; ++i) {
                                    this.cnames[i] = this.getSvalue(string, 1 + i * 3);
                                    this.sentn[i] = this.getSvalue(string, 2 + i * 3);
                                    this.nctime[i] = this.getLvalue(string, 3 + i * 3);
                                }
                                this.updatec = n71;
                            }
                            for (int i = 0; i < 21; ++i) {
                                if (this.nctime[i] > 0L) {
                                    String string62;
                                    int n72;
                                    long l = this.ntime - this.nctime[i];
                                    if (l < 1000L) {
                                        this.ctime[i] = "- just now";
                                    }
                                    if (l >= 1000L && l < 60000L) {
                                        this.ctime[i] = "- seconds ago";
                                    }
                                    if (l >= 60000L && l < 3600000L) {
                                        n72 = (int)(l / 60000L);
                                        string62 = "s";
                                        if (n72 == 1) {
                                            string62 = "";
                                        }
                                        this.ctime[i] = "- " + n72 + " minute" + string62 + " ago";
                                    }
                                    if (l >= 3600000L && l < 86400000L) {
                                        n72 = (int)(l / 3600000L);
                                        string62 = "s";
                                        if (n72 == 1) {
                                            string62 = "";
                                        }
                                        this.ctime[i] = "- " + n72 + " hour" + string62 + " ago";
                                    }
                                    if (l < 86400000L) continue;
                                    n72 = (int)(l / 86400000L);
                                    string62 = "s";
                                    if (n72 == 1) {
                                        string62 = "";
                                    }
                                    this.ctime[i] = "- " + n72 + " day" + string62 + " ago";
                                    continue;
                                }
                                this.ctime[i] = "";
                            }
                        }
                    }
                    if (this.tab == 1) {
                        if (this.upload == 5) {
                            this.upload = 0;
                            this.loadedp = false;
                            this.edit = 0;
                            this.refresh = true;
                        }
                        if (!this.loadedp) {
                            if (!this.refresh) {
                                this.loadproinfo();
                                this.trunsent();
                            }
                            this.logopng();
                            this.avatarpng();
                            this.clanlogopng(this.proclan);
                            this.refresh = false;
                            this.protab = 0;
                            this.loadedp = true;
                        }
                        if (this.protab == 2) {
                            if (this.loadpst == 0) {
                                this.loadprostages();
                            }
                            if (this.loadpst == 1 && !this.gs.proitem.getSelectedItem().equals(this.loadpstage) && this.gs.proitem.sel != 0) {
                                this.addstage = 0;
                                if (this.gs.loadstagePreview(-2, this.gs.proitem.getSelectedItem(), this.co, this.bco, this.m, this.cp)) {
                                    this.loadedpstage = true;
                                    this.m.hit = 20000;
                                    this.m.fallen = 0;
                                } else {
                                    this.loadedpstage = false;
                                }
                                this.loadpstage = this.gs.proitem.getSelectedItem();
                            }
                        }
                    }
                    if (this.ptab == 2 && !this.xt.clan.equals("") && !this.loadedcm) {
                        this.loadfclan();
                        this.loadedcm = true;
                    }
                    if (this.tab == 3) {
                        if (this.cfase == 0) {
                            if (!this.xt.clan.equals("")) {
                                this.clanlogopng(this.xt.clan);
                            }
                            if (this.ntab == 0 && this.loadednews == 0) {
                                this.loadnews();
                            }
                            if (this.ntab == 1 && this.loadwstat == 0) {
                                this.loadchamps();
                            }
                        }
                        if (this.cfase == 1 && this.em == 1) {
                            String string63 = this.gs.temail.getText();
                            string2 = "101|26|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + string63 + "|";
                            try {
                                this.dout.println(string2);
                                string = this.din.readLine();
                            }
                            catch (Exception exception) {
                                string = "fail";
                            }
                            if (string.startsWith("OK")) {
                                this.xt.clan = string63;
                                this.xt.clankey = this.getSvalue(string, 1);
                                this.spos5 = 0;
                                this.lspos5 = 0;
                                this.cfase = 3;
                                this.claname = string63;
                                this.loadedc = false;
                                this.ctab = 0;
                                this.em = 0;
                            } else if (string.equals("FOUND")) {
                                this.msg = "The name '" + string63 + "' is already used by another clan!";
                                this.flko = 45;
                                this.em = 0;
                            } else {
                                this.msg = "Server error authorizing clan creation, please try again later...";
                                this.flko = 45;
                                this.em = 0;
                            }
                        }
                        if (this.cfase == 2) {
                            if (this.em == 1) {
                                string2 = "101|27|";
                                try {
                                    this.dout.println(string2);
                                    string = this.din.readLine();
                                }
                                catch (Exception exception) {
                                    string = "";
                                }
                                this.nclns = 0;
                                String string64 = this.getSvalue(string, this.nclns);
                                while (!string64.equals("") && this.nclns < 20) {
                                    this.clanlo[this.nclns] = string64;
                                    ++this.nclns;
                                    string64 = this.getSvalue(string, this.nclns);
                                }
                                this.smsg = this.nclns != 0 ? "Clans with recent activity:" : "Found no clans with recent activity.";
                                this.em = 0;
                            }
                            if (this.em == 2) {
                                string2 = "101|28|" + this.gs.temail.getText() + "";
                                try {
                                    this.dout.println(string2);
                                    string = this.din.readLine();
                                }
                                catch (Exception exception) {
                                    string = "";
                                }
                                this.nclns = 0;
                                String string65 = this.getSvalue(string, this.nclns);
                                while (!string65.equals("") && this.nclns < 20) {
                                    this.clanlo[this.nclns] = string65;
                                    ++this.nclns;
                                    string65 = this.getSvalue(string, this.nclns);
                                }
                                this.smsg = this.nclns != 0 ? "Search result for '" + this.gs.temail.getText() + "' in clans:" : "Found no clans with the phrase '" + this.gs.temail.getText() + "' in them.";
                                this.em = 0;
                            }
                        }
                        if (this.cfase == 3) {
                            if (this.editc == 33) {
                                string2 = "101|24|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.claname + "|" + this.member[this.em] + "|" + (this.gs.clanlev.getSelectedIndex() + 1) + "|" + this.gs.cmsg.getText() + "|";
                                try {
                                    this.dout.println(string2);
                                    string = this.din.readLine();
                                }
                                catch (Exception exception) {
                                    string = "fail";
                                }
                                if (string.equals("OK")) {
                                    this.editc = 0;
                                    this.loadedc = false;
                                } else {
                                    this.editc = 5;
                                }
                            }
                            if (this.editc == 66) {
                                string2 = "101|24|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.claname + "|" + this.rmember[this.em] + "|1|New member - just approved.|";
                                try {
                                    this.dout.println(string2);
                                    string = this.din.readLine();
                                }
                                catch (Exception exception) {
                                    string = "fail";
                                }
                                if (string.equals("OK")) {
                                    this.editc = 0;
                                    if (this.nrmb == 1) {
                                        this.spos5 = 0;
                                        this.lspos5 = 0;
                                    }
                                    this.loadedc = false;
                                } else {
                                    this.editc = 5;
                                }
                            }
                            if (this.editc == 44) {
                                string2 = "101|25|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.claname + "|" + this.member[this.em] + "|";
                                try {
                                    this.dout.println(string2);
                                    string = this.din.readLine();
                                }
                                catch (Exception exception) {
                                    string = "fail";
                                }
                                if (string.equals("OK")) {
                                    this.editc = 0;
                                    this.loadedc = false;
                                    if (this.member[this.em].toLowerCase().equals(this.xt.nickname.toLowerCase())) {
                                        if (this.proname.equals(this.xt.nickname)) {
                                            this.proclan = "";
                                        }
                                        this.xt.clan = "";
                                        this.xt.clankey = "";
                                        if (this.nmb == 1) {
                                            this.cfase = 0;
                                        }
                                    }
                                } else {
                                    this.editc = 5;
                                }
                            }
                            if (this.editc == 77) {
                                string2 = "101|25|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.claname + "|" + this.rmember[this.em] + "|";
                                try {
                                    this.dout.println(string2);
                                    string = this.din.readLine();
                                }
                                catch (Exception exception) {
                                    string = "fail";
                                }
                                if (string.equals("OK")) {
                                    this.editc = 0;
                                    if (this.nrmb == 1) {
                                        this.spos5 = 0;
                                        this.lspos5 = 0;
                                    }
                                    this.loadedc = false;
                                } else {
                                    this.editc = 5;
                                }
                            }
                            if (this.editc == 99) {
                                string2 = "101|30|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.claname + "|";
                                try {
                                    this.dout.println(string2);
                                    string = this.din.readLine();
                                }
                                catch (Exception exception) {
                                    string = "fail";
                                }
                                if (string.equals("OK")) {
                                    this.editc = 0;
                                    this.loadedc = false;
                                } else {
                                    this.editc = 5;
                                }
                            }
                            if (this.upload == 5) {
                                this.upload = 0;
                                this.loadedc = false;
                                if (this.editc == 2) {
                                    this.loadedmyclanbg = -1;
                                }
                                this.editc = 0;
                                this.refresh = true;
                            }
                            if (!this.loadedc) {
                                if (!this.refresh) {
                                    this.loadclan();
                                }
                                this.clanlogopng(this.claname);
                                this.clanbgpng();
                                this.refresh = false;
                                this.loadedc = true;
                            }
                            if (this.attachetoclan) {
                                string2 = "101|29|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.claname + "|";
                                try {
                                    this.dout.println(string2);
                                    string = this.din.readLine();
                                }
                                catch (Exception exception) {
                                    string = "fail";
                                }
                                if (string.indexOf("|") != -1) {
                                    this.xt.clan = this.getSvalue(string, 0);
                                    this.xt.clankey = this.getSvalue(string, 1);
                                }
                                this.attachetoclan = false;
                            }
                            if (this.editc == 55) {
                                string2 = "101|31|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.claname + "|" + this.sltit + "|" + this.gs.cmsg.getText() + "|" + this.gs.temail.getText() + "|";
                                try {
                                    this.dout.println(string2);
                                    string = this.din.readLine();
                                }
                                catch (Exception exception) {
                                    string = "fail";
                                }
                                if (string.equals("OK")) {
                                    this.editc = 0;
                                    this.loadedlink = false;
                                } else {
                                    this.editc = 5;
                                }
                            }
                            if (this.ctab == 2) {
                                if (this.loadedcars == 6) {
                                    string2 = "101|32|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.claname + "|" + this.selcar + "|";
                                    try {
                                        this.dout.println(string2);
                                        string = this.din.readLine();
                                    }
                                    catch (Exception exception) {
                                        string = "fail";
                                    }
                                    this.loadedcars = string.equals("OK") ? -1 : 7;
                                }
                                if (this.loadedcars == 8) {
                                    string2 = "101|33|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.claname + "|" + this.selcar + "|";
                                    try {
                                        this.dout.println(string2);
                                        string = this.din.readLine();
                                    }
                                    catch (Exception exception) {
                                        string = "fail";
                                    }
                                    this.loadedcars = string.equals("OK") ? -1 : 9;
                                }
                                if (this.loadedcars == -1) {
                                    this.loadedcars = this.loadclancars();
                                }
                                if (this.loadedcars == 1 && !this.selcar.equals("Select Car") && this.loadedcar == 0) {
                                    this.cd.haltload = this.xt.sc[0] != 36 || this.xt.clan.toLowerCase().equals(this.claname.toLowerCase()) ? 1 : 2;
                                    while (this.cd.haltload == this.cd.onloadingcar) {
                                    }
                                    this.loadedcar = this.cd.loadonlinecar(this.selcar, 35 + this.cd.haltload);
                                    if (this.xt.sc[0] == 36 && this.cd.haltload == 1 && this.loadedcar > 0) {
                                        int n73;
                                        boolean bl = false;
                                        for (n73 = 0; n73 < this.bco[36].npl && !bl; ++n73) {
                                            if (this.bco[36].p[n73].colnum != 1) continue;
                                            float[] fArray = new float[3];
                                            Color.RGBtoHSB(this.bco[36].p[n73].c[0], this.bco[36].p[n73].c[1], this.bco[36].p[n73].c[2], fArray);
                                            this.xt.arnp[0] = fArray[0];
                                            this.xt.arnp[1] = fArray[1];
                                            this.xt.arnp[2] = 1.0f - fArray[2];
                                            bl = true;
                                        }
                                        bl = false;
                                        for (n73 = 0; n73 < this.bco[36].npl && !bl; ++n73) {
                                            if (this.bco[36].p[n73].colnum != 2) continue;
                                            float[] fArray = new float[3];
                                            Color.RGBtoHSB(this.bco[36].p[n73].c[0], this.bco[36].p[n73].c[1], this.bco[36].p[n73].c[2], fArray);
                                            this.xt.arnp[3] = fArray[0];
                                            this.xt.arnp[4] = fArray[1];
                                            this.xt.arnp[5] = 1.0f - fArray[2];
                                            bl = true;
                                        }
                                    }
                                }
                                if (this.loadedcars == 2) {
                                    this.loadedcars = this.loadaddcars();
                                }
                            }
                            if (this.ctab == 3) {
                                if (this.loadedstages == 6) {
                                    string2 = "101|34|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.claname + "|" + this.selstage + "|";
                                    try {
                                        this.dout.println(string2);
                                        string = this.din.readLine();
                                    }
                                    catch (Exception exception) {
                                        string = "fail";
                                    }
                                    this.loadedstages = string.equals("OK") ? -1 : 7;
                                }
                                if (this.loadedstages == 8) {
                                    string2 = "101|35|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.claname + "|" + this.selstage + "|";
                                    try {
                                        this.dout.println(string2);
                                        string = this.din.readLine();
                                    }
                                    catch (Exception exception) {
                                        string = "fail";
                                    }
                                    this.loadedstages = string.equals("OK") ? -1 : 9;
                                }
                                if (this.loadedstages == -1) {
                                    this.loadedstages = this.loadclanstages();
                                }
                                if (this.loadedstages == 1 && !this.selstage.equals("Select Stage") && this.loadedstage == 0) {
                                    this.cd.t.nt = 0;
                                    if (this.gs.loadstagePreview(-2, this.selstage, this.co, this.bco, this.m, this.cp)) {
                                        this.loadedstage = 1;
                                        this.m.ptr = 0;
                                        this.m.ptcnt = -10;
                                        this.m.hit = 45000;
                                        this.m.fallen = 0;
                                        this.m.nrnd = 0;
                                    } else {
                                        this.loadedstage = -1;
                                    }
                                }
                                if (this.loadedstages == 2) {
                                    this.loadedstages = this.loadaddstages();
                                }
                            }
                            if (this.ctab == 4 && !this.loadedlink) {
                                this.loadclanlink();
                                this.loadedlink = true;
                            }
                        }
                    }
                    if (this.upload != 0) {
                        if (this.filename.toLowerCase().endsWith(".gif") || this.filename.toLowerCase().endsWith(".jpg") || this.filename.toLowerCase().endsWith(".jpeg") || this.filename.toLowerCase().endsWith(".png")) {
                            File file = new File(this.filename);
                            if (file.exists()) {
                                int n74 = (int)file.length();
                                if (n74 < 0x100000) {
                                    if (this.upload != 0) {
                                        this.upload = 2;
                                        try {
                                            int n75 = 2;
                                            if (this.tab == 1 && this.edit == 2) {
                                                n75 = 3;
                                            }
                                            String string66 = "";
                                            if (this.tab == 3) {
                                                if (this.editc == 1) {
                                                    n75 = 22;
                                                }
                                                if (this.editc == 2) {
                                                    n75 = 23;
                                                }
                                                string66 = "" + this.claname + "|";
                                            }
                                            string2 = "101|" + n75 + "|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + n74 + "|" + string66 + "";
                                            this.dout.println(string2);
                                            string = this.din.readLine();
                                            if (string.equals("OK")) {
                                                FileInputStream fileInputStream = new FileInputStream(file);
                                                byte[] byArray = new byte[n74];
                                                fileInputStream.read(byArray);
                                                fileInputStream.close();
                                                DataOutputStream dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
                                                if (this.upload != 0) {
                                                    this.upload = 3;
                                                }
                                                this.perc = 0;
                                                int n76 = 0;
                                                while (n76 < n74 && this.upload != 0) {
                                                    int n77 = 10240;
                                                    if (n76 + n77 > n74) {
                                                        n77 = n74 - n76;
                                                    }
                                                    dataOutputStream.write(byArray, n76, n77);
                                                    this.perc = (int)((float)(n76 += n77) / (float)n74 * 100.0f);
                                                }
                                                if (this.upload != 0) {
                                                    string = this.din.readLine();
                                                    if (string.equals("CR")) {
                                                        this.upload = 4;
                                                    } else {
                                                        this.msg = "Failed to create image online, server error!";
                                                        this.flko = 45;
                                                        this.upload = 0;
                                                    }
                                                    string = this.din.readLine();
                                                    if (string.equals("OK")) {
                                                        this.upload = 5;
                                                    }
                                                    break block632;
                                                }
                                                try {
                                                    this.socket.close();
                                                    this.socket = null;
                                                    this.din.close();
                                                    this.din = null;
                                                    this.dout.close();
                                                    this.dout = null;
                                                    this.connector = null;
                                                }
                                                catch (Exception exception) {
                                                    // empty catch block
                                                }
                                                try {
                                                    this.socket = new Socket(this.lg.servers[0], 7061);
                                                    this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                                                    this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                                                }
                                                catch (Exception exception) {}
                                                break block632;
                                            }
                                            this.msg = "Failed to authenticate to start an uploading connection!";
                                            this.flko = 45;
                                            this.upload = 0;
                                        }
                                        catch (Exception exception) {
                                            this.msg = "Failed to upload image, unknown error!";
                                            this.flko = 45;
                                            this.upload = 0;
                                            try {
                                                this.socket.close();
                                                this.socket = null;
                                                this.din.close();
                                                this.din = null;
                                                this.dout.close();
                                                this.dout = null;
                                                this.connector = null;
                                            }
                                            catch (Exception exception2) {
                                                // empty catch block
                                            }
                                            try {
                                                this.socket = new Socket(this.lg.servers[0], 7061);
                                                this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                                                this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                                            }
                                            catch (Exception exception3) {}
                                        }
                                    }
                                } else {
                                    this.msg = "Uploaded image must be less than 1MB in size!";
                                    this.flko = 45;
                                    this.upload = 0;
                                }
                            } else {
                                this.msg = "The file chosen is invalid or does not exist!";
                                this.flko = 45;
                                this.upload = 0;
                            }
                        } else {
                            this.msg = "Uploaded image must be JPEG, GIF or PNG!";
                            this.flko = 45;
                            this.upload = 0;
                        }
                    }
                }
                if (this.uploadt == 5) {
                    this.uploadt = 0;
                    this.msg = "";
                }
                if (this.uploadt != 0) {
                    File file = new File(this.filename);
                    if (file.exists()) {
                        int n = (int)file.length();
                        if (n < 716800) {
                            this.xt.strack = new RadicalMod(this.filename, true);
                            if (this.xt.strack.loaded == 2) {
                                this.trackvol = (int)(220.0f / ((float)this.xt.strack.rvol / 3750.0f));
                                this.xt.strack.unload();
                                if (this.uploadt != 0) {
                                    this.uploadt = 2;
                                    try {
                                        string2 = "101|4|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|" + this.trackname + "|" + this.trackvol + "|" + n + "|";
                                        this.dout.println(string2);
                                        string = this.din.readLine();
                                        if (string.equals("OK")) {
                                            string = this.din.readLine();
                                            if (this.uploadt != 0) {
                                                if (string.equals("UPLOAD")) {
                                                    this.uploadt = 3;
                                                    FileInputStream fileInputStream = new FileInputStream(file);
                                                    byte[] byArray = new byte[n];
                                                    fileInputStream.read(byArray);
                                                    fileInputStream.close();
                                                    DataOutputStream dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
                                                    dataOutputStream.write(byArray, 0, n);
                                                    this.uploadt = 4;
                                                    string = this.din.readLine();
                                                }
                                                if (string.equals("FOUND")) {
                                                    this.uploadt = 4;
                                                    string = this.din.readLine();
                                                }
                                                if (string.equals("OK")) {
                                                    this.themesong = this.trackname;
                                                    this.uploadt = 5;
                                                } else if (string.equals("EXIST")) {
                                                    this.msg = "Another track with the same name already exists, please rename you file!";
                                                    this.flko = 45;
                                                    this.uploadt = 0;
                                                } else {
                                                    this.msg = "Failed to add MOD Track to your profile, unknown error!";
                                                    this.flko = 45;
                                                    this.uploadt = 0;
                                                }
                                                break block633;
                                            }
                                            try {
                                                this.socket.close();
                                                this.socket = null;
                                                this.din.close();
                                                this.din = null;
                                                this.dout.close();
                                                this.dout = null;
                                                this.connector = null;
                                            }
                                            catch (Exception exception) {
                                                // empty catch block
                                            }
                                            try {
                                                this.socket = new Socket(this.lg.servers[0], 7061);
                                                this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                                                this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                                            }
                                            catch (Exception exception) {}
                                            break block633;
                                        }
                                        this.msg = "Failed to authenticate to start an uploading connection!";
                                        this.flko = 45;
                                        this.uploadt = 0;
                                    }
                                    catch (Exception exception) {
                                        this.msg = "Failed to upload track, unknown error!";
                                        this.flko = 45;
                                        this.uploadt = 0;
                                        try {
                                            this.socket.close();
                                            this.socket = null;
                                            this.din.close();
                                            this.din = null;
                                            this.dout.close();
                                            this.dout = null;
                                            this.connector = null;
                                        }
                                        catch (Exception exception4) {
                                            // empty catch block
                                        }
                                        try {
                                            this.socket = new Socket(this.lg.servers[0], 7061);
                                            this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                                            this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                                        }
                                        catch (Exception exception5) {}
                                    }
                                }
                            } else {
                                this.xt.strack.unload();
                                this.msg = "The file choosen is not a valid MOD Track!";
                                this.flko = 45;
                                this.uploadt = 0;
                            }
                        } else {
                            this.msg = "Uploaded file must be less than 250KB in size!";
                            this.flko = 45;
                            this.uploadt = 0;
                        }
                    } else {
                        this.msg = "The file chosen is invalid or does not exist!";
                        this.flko = 45;
                        this.uploadt = 0;
                    }
                }
            }
            if (this.playt == 1) {
                this.xt.strack = new RadicalMod(this.themesong, this.trackvol, 8000, 125, false, true);
                this.xt.strack.play();
                this.playt = 2;
            }
            this.domelogos();
            try {
                Thread.sleep(600L);
            }
            catch (InterruptedException interruptedException) {}
        }
        this.onexit();
    }

    public void onexit() {
        this.onexitpro();
        this.gs.hidefields();
        this.cd.acname = "";
        this.cd.action = 0;
        this.cd.staction = 0;
        this.cd.onstage = "";
        this.addstage = 0;
        this.npf = -1;
        this.editc = 0;
        this.openc = 0;
        this.readmsg = 0;
        this.loadmsgs = -1;
        this.readclan = 0;
        if (this.cd.haltload == 2) {
            this.cd.haltload = 1;
            this.cd.lcardate[1] = 0;
        }
        if (this.cd.haltload == 1) {
            if (this.xt.sc[0] == 36) {
                if (!this.xt.clan.toLowerCase().equals(this.claname.toLowerCase())) {
                    this.loadedcars = -1;
                }
            } else {
                this.cd.haltload = 0;
                this.cd.lcardate[0] = 0;
            }
        }
        this.m.crs = true;
        this.m.focus_point = 400;
        this.m.x = -335;
        this.m.y = 0;
        this.m.z = -50;
        this.m.xz = 0;
        this.m.zy = 20;
        this.m.ground = -2000;
        try {
            this.socket.close();
            this.socket = null;
            this.din.close();
            this.din = null;
            this.dout.close();
            this.dout = null;
            this.connector = null;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void onexitpro() {
        this.edit = 0;
        this.upload = 0;
        this.uploadt = 0;
        this.sfreq = 0;
        if (this.playt == 2) {
            this.xt.strack.unload();
            this.playt = 0;
        }
        this.protab = 0;
        this.gs.proitem.show = false;
        this.addstage = 0;
    }

    public void stopallnow() {
        if (this.connector != null) {
            this.connector.stop();
            this.connector = null;
        }
        try {
            this.socket.close();
            this.socket = null;
            this.din.close();
            this.din = null;
            this.dout.close();
            this.dout = null;
            this.connector = null;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void trunsent() {
        int n;
        for (n = 0; n < 3; ++n) {
            this.aboutxt[n] = "";
        }
        if (!this.sentance.equals("")) {
            this.rd.setFont(new Font("Tahoma", 1, 11));
            this.ftm = this.rd.getFontMetrics();
            n = 0;
            int n2 = 0;
            int n3 = 0;
            boolean bl = false;
            for (int i = 0; i < this.sentance.length(); ++i) {
                String string = "" + this.sentance.charAt(i);
                if (string.equals(" ")) {
                    n2 = i;
                }
                if (n < 3) {
                    int n4 = n;
                    this.aboutxt[n4] = this.aboutxt[n4] + string;
                    if (this.ftm.stringWidth(this.aboutxt[n]) <= 276) continue;
                    if (n2 != n3) {
                        this.aboutxt[n] = this.sentance.substring(n3, n2);
                        n3 = i = n2;
                    } else if (n == 2) {
                        bl = true;
                    }
                    ++n;
                    continue;
                }
                if (bl) {
                    this.aboutxt[2] = this.aboutxt[2].substring(0, this.aboutxt[2].length() - 3);
                }
                this.aboutxt[2] = this.aboutxt[2] + "...";
                i = this.sentance.length();
            }
        }
        this.nab = 0;
        for (n = 0; n < 3 && !this.aboutxt[n].equals(""); ++n) {
            this.aboutxt[n] = this.aboutxt[n].trim();
            ++this.nab;
        }
    }

    public void roomlogos(String[] stringArray, int n) {
        for (int i = 0; i < 2; ++i) {
            boolean bl = true;
            String string = "";
            for (int j = 0; j < n; ++j) {
                bl = false;
                for (int k = 0; k < this.nlg; ++k) {
                    if (!stringArray[j].toLowerCase().equals(this.logos[k].toLowerCase())) continue;
                    bl = true;
                    break;
                }
                if (bl) continue;
                string = stringArray[j].toLowerCase();
                break;
            }
            if (bl) break;
            this.logos[this.nlg] = string;
            this.logon[this.nlg] = false;
            try {
                URL uRL = new URL("http://multiplayer.needformadness.com/profiles/" + this.logos[this.nlg] + "/logo.png");
                uRL.openConnection().setConnectTimeout(2000);
                String string2 = uRL.openConnection().getContentType();
                if (string2.equals("image/png")) {
                    this.logoi[this.nlg] = Toolkit.getDefaultToolkit().createImage(uRL);
                    this.mt.addImage(this.logoi[this.nlg], this.nlg);
                    this.logon[this.nlg] = true;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++this.nlg;
            if (this.nlg != 200) continue;
            this.nlg = 0;
        }
    }

    public void domelogos() {
        for (int i = 0; i < 5; ++i) {
            int n;
            int n2;
            int n3;
            boolean bl = true;
            boolean bl2 = false;
            String string = "";
            String string2 = "";
            if (this.freq == 1) {
                bl = false;
                for (n3 = 0; n3 < this.nlg; ++n3) {
                    if (!this.freqname.toLowerCase().equals(this.logos[n3].toLowerCase())) continue;
                    bl = true;
                    break;
                }
                if (!bl) {
                    string = this.freqname.toLowerCase();
                }
            }
            if (bl && this.loadednews == 1) {
                for (n3 = 0; n3 < 4; ++n3) {
                    if (this.newplayers[n3].equals("")) continue;
                    bl = false;
                    for (n2 = 0; n2 < this.nlg; ++n2) {
                        if (!this.newplayers[n3].toLowerCase().equals(this.logos[n2].toLowerCase())) continue;
                        bl = true;
                        break;
                    }
                    if (bl) continue;
                    string = this.newplayers[n3].toLowerCase();
                    break;
                }
            }
            if (bl && this.loadednews == 1) {
                for (n3 = 0; n3 < 5; ++n3) {
                    if (this.nwarbs[n3] <= 0) continue;
                    bl = false;
                    String string3 = "#" + this.nwclan[n3] + "#";
                    for (n = 0; n < this.nlg; ++n) {
                        if (!string3.toLowerCase().equals(this.logos[n].toLowerCase())) continue;
                        bl = true;
                        break;
                    }
                    if (bl) continue;
                    string = string3.toLowerCase();
                    bl2 = true;
                    string2 = this.nwclan[n3];
                    break;
                }
            }
            if (bl && this.loadwstat == 1) {
                for (n3 = 0; n3 < 5; ++n3) {
                    if (this.ncc <= 0) continue;
                    bl = false;
                    String string4 = "#" + this.conclan[n3] + "#";
                    for (n = 0; n < this.nlg; ++n) {
                        if (!string4.toLowerCase().equals(this.logos[n].toLowerCase())) continue;
                        bl = true;
                        break;
                    }
                    if (bl) continue;
                    string = string4.toLowerCase();
                    bl2 = true;
                    string2 = this.conclan[n3];
                    break;
                }
            }
            if (bl && this.ni > 0) {
                for (n3 = 0; n3 < this.ni; ++n3) {
                    bl = false;
                    String string5 = "#" + this.iclan[n3] + "#";
                    for (n = 0; n < this.nlg; ++n) {
                        if (!string5.toLowerCase().equals(this.logos[n].toLowerCase())) continue;
                        bl = true;
                        break;
                    }
                    if (bl) continue;
                    string = string5.toLowerCase();
                    bl2 = true;
                    string2 = this.iclan[n3];
                    break;
                }
            }
            if (bl && this.nclns > 0) {
                for (n3 = 0; n3 < this.nclns; ++n3) {
                    bl = false;
                    String string6 = "#" + this.clanlo[n3] + "#";
                    for (n = 0; n < this.nlg; ++n) {
                        if (!string6.toLowerCase().equals(this.logos[n].toLowerCase())) continue;
                        bl = true;
                        break;
                    }
                    if (bl) continue;
                    string = string6.toLowerCase();
                    bl2 = true;
                    string2 = this.clanlo[n3];
                    break;
                }
            }
            if (bl && this.nrmb > 0 && this.showreqs) {
                for (n3 = 0; n3 < this.nrmb; ++n3) {
                    bl = false;
                    for (n2 = 0; n2 < this.nlg; ++n2) {
                        if (!this.rmember[n3].toLowerCase().equals(this.logos[n2].toLowerCase())) continue;
                        bl = true;
                        break;
                    }
                    if (bl) continue;
                    string = this.rmember[n3].toLowerCase();
                    break;
                }
            }
            if (bl && this.nmb > 0) {
                for (n3 = 0; n3 < this.nmb; ++n3) {
                    bl = false;
                    for (n2 = 0; n2 < this.nlg; ++n2) {
                        if (!this.member[n3].toLowerCase().equals(this.logos[n2].toLowerCase())) continue;
                        bl = true;
                        break;
                    }
                    if (bl) continue;
                    string = this.member[n3].toLowerCase();
                    break;
                }
            }
            if (bl && this.nclns > 0) {
                for (n3 = 0; n3 < this.ncln; ++n3) {
                    bl = false;
                    for (n2 = 0; n2 < this.nlg; ++n2) {
                        if (!this.clname[n3].toLowerCase().equals(this.logos[n2].toLowerCase())) continue;
                        bl = true;
                        break;
                    }
                    if (bl) continue;
                    string = this.clname[n3].toLowerCase();
                    break;
                }
            }
            if (bl && this.npf > 0) {
                for (n3 = 0; n3 < this.npf; ++n3) {
                    bl = false;
                    for (n2 = 0; n2 < this.nlg; ++n2) {
                        if (!this.fname[n3].toLowerCase().equals(this.logos[n2].toLowerCase())) continue;
                        bl = true;
                        break;
                    }
                    if (bl) continue;
                    string = this.fname[n3].toLowerCase();
                    break;
                }
            }
            if (bl && this.nm > 0) {
                for (n3 = 0; n3 < this.nm; ++n3) {
                    bl = false;
                    for (n2 = 0; n2 < this.nlg; ++n2) {
                        if (!this.mname[n3].toLowerCase().equals(this.logos[n2].toLowerCase())) continue;
                        bl = true;
                        break;
                    }
                    if (bl) continue;
                    string = this.mname[n3].toLowerCase();
                    break;
                }
            }
            if (bl) {
                for (n3 = 0; n3 < this.npo; ++n3) {
                    bl = false;
                    for (n2 = 0; n2 < this.nlg; ++n2) {
                        if (!this.pname[n3].toLowerCase().equals(this.logos[n2].toLowerCase())) continue;
                        bl = true;
                        break;
                    }
                    if (bl) continue;
                    string = this.pname[n3].toLowerCase();
                    break;
                }
            }
            if (bl) break;
            this.logos[this.nlg] = string;
            this.logon[this.nlg] = false;
            try {
                String string7 = "http://multiplayer.needformadness.com/profiles/" + this.logos[this.nlg] + "/logo.png";
                if (bl2) {
                    string7 = "http://multiplayer.needformadness.com/clans/" + string2 + "/logo.png";
                }
                URL uRL = new URL(string7);
                uRL.openConnection().setConnectTimeout(2000);
                String string8 = uRL.openConnection().getContentType();
                if (string8.equals("image/png")) {
                    this.logoi[this.nlg] = Toolkit.getDefaultToolkit().createImage(uRL);
                    this.mt.addImage(this.logoi[this.nlg], this.nlg);
                    this.logon[this.nlg] = true;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++this.nlg;
            if (this.nlg != 200) continue;
            this.nlg = 0;
        }
    }

    public boolean drawl(Graphics2D graphics2D, String string, int n, int n2, boolean bl) {
        boolean bl2 = false;
        int n3 = -1;
        for (int i = 0; i < this.nlg; ++i) {
            if (!string.toLowerCase().equals(this.logos[i].toLowerCase())) continue;
            n3 = i;
            break;
        }
        if (n3 != -1 && this.logon[n3]) {
            if (!bl) {
                graphics2D.setComposite(AlphaComposite.getInstance(3, 0.1f));
            }
            graphics2D.drawImage(this.logoi[n3], n, n2, null);
            bl2 = this.mt.checkID(n3);
            if (!bl) {
                graphics2D.setComposite(AlphaComposite.getInstance(3, 1.0f));
            }
        }
        return bl2;
    }

    public void logopng() {
        int n = -1;
        boolean bl = false;
        for (int i = 0; i < this.nlg; ++i) {
            if (!this.proname.toLowerCase().equals(this.logos[i].toLowerCase())) continue;
            n = i;
            if (!this.logon[n] || this.refresh) break;
            bl = true;
            this.logol = true;
            break;
        }
        if (!bl) {
            if (n == -1) {
                n = this.nlg++;
                if (this.nlg == 200) {
                    this.nlg = 0;
                }
            }
            this.logos[n] = this.proname.toLowerCase();
            try {
                String string = "";
                if (this.refresh) {
                    string = "?req=" + (int)(Math.random() * 1000.0) + "";
                }
                URL uRL = new URL("http://multiplayer.needformadness.com/profiles/" + this.proname + "/logo.png" + string + "");
                uRL.openConnection().setConnectTimeout(2000);
                String string2 = uRL.openConnection().getContentType();
                if (string2.equals("image/png")) {
                    this.logoi[n] = Toolkit.getDefaultToolkit().createImage(uRL);
                    this.mt.addImage(this.logoi[n], n);
                    this.logon[n] = true;
                } else {
                    this.logon[n] = false;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.logol = this.logon[n];
        }
    }

    public void clanlogopng(String string) {
        int n = -1;
        boolean bl = false;
        String string2 = "#" + string.toLowerCase() + "#";
        for (int i = 0; i < this.nlg; ++i) {
            if (!string2.equals(this.logos[i])) continue;
            n = i;
            if (!this.logon[n] || this.refresh) break;
            bl = true;
            break;
        }
        if (!bl) {
            if (n == -1) {
                n = this.nlg++;
                if (this.nlg == 200) {
                    this.nlg = 0;
                }
            }
            this.logos[n] = string2;
            try {
                String string3 = "";
                if (this.refresh) {
                    string3 = "?req=" + (int)(Math.random() * 1000.0) + "";
                }
                URL uRL = new URL("http://multiplayer.needformadness.com/clans/" + string + "/logo.png" + string3 + "");
                uRL.openConnection().setConnectTimeout(2000);
                String string4 = uRL.openConnection().getContentType();
                if (string4.equals("image/png")) {
                    this.logoi[n] = Toolkit.getDefaultToolkit().createImage(uRL);
                    this.mt.addImage(this.logoi[n], n);
                    this.logon[n] = true;
                } else {
                    this.logon[n] = false;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void avatarpng() {
        this.avatarl = false;
        String string = "";
        if (this.refresh) {
            string = "?req=" + (int)(Math.random() * 1000.0) + "";
        }
        try {
            URL uRL = new URL("http://multiplayer.needformadness.com/profiles/" + this.proname + "/avatar.png" + string + "");
            uRL.openConnection().setConnectTimeout(2000);
            String string2 = uRL.openConnection().getContentType();
            if (string2.equals("image/png")) {
                this.avatar = Toolkit.getDefaultToolkit().createImage(uRL);
                this.avatarl = true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void clanbgpng() {
        this.clanbgl = false;
        String string = "";
        if (this.refresh) {
            string = "?req=" + (int)(Math.random() * 1000.0) + "";
        }
        try {
            URL uRL = new URL("http://multiplayer.needformadness.com/clans/" + this.claname + "/bg.jpg" + string + "");
            uRL.openConnection().setConnectTimeout(2000);
            String string2 = uRL.openConnection().getContentType();
            if (string2.equals("image/jpeg")) {
                this.clanbg = Toolkit.getDefaultToolkit().createImage(uRL);
                this.clanbgl = true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void intclanbgpng(String string) {
        if (!this.intclanlo.equals(string)) {
            this.intclanbgloaded = false;
            try {
                URL uRL = new URL("http://multiplayer.needformadness.com/clans/" + string + "/bg.jpg");
                uRL.openConnection().setConnectTimeout(2000);
                String string2 = uRL.openConnection().getContentType();
                if (string2.equals("image/jpeg")) {
                    this.intclanbg = Toolkit.getDefaultToolkit().createImage(uRL);
                    this.intclanbgloaded = true;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.intclanlo = string;
        }
    }

    public void loadmyclanbg() {
        if (this.loadedmyclanbg <= 0) {
            String string = "";
            if (this.loadedmyclanbg == -1) {
                string = "?req=" + (int)(Math.random() * 1000.0) + "";
            }
            this.loadedmyclanbg = 2;
            try {
                URL uRL = new URL("http://multiplayer.needformadness.com/clans/" + this.xt.clan + "/bg.jpg" + string + "");
                uRL.openConnection().setConnectTimeout(2000);
                String string2 = uRL.openConnection().getContentType();
                if (string2.equals("image/jpeg")) {
                    this.myclanbg = Toolkit.getDefaultToolkit().createImage(uRL);
                    this.loadedmyclanbg = 1;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void loadclan() {
        this.notclan = false;
        int n = 0;
        String[] stringArray = new String[20];
        int[] nArray = new int[20];
        String[] stringArray2 = new String[20];
        this.showreqs = false;
        this.nrmb = 0;
        try {
            URL uRL = new URL("http://multiplayer.needformadness.com/clans/" + this.claname + "/members.txt?req=" + (int)(Math.random() * 1000.0) + "");
            uRL.openConnection().setConnectTimeout(2000);
            String string = uRL.openConnection().getContentType();
            if (string.equals("text/plain")) {
                DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                String string2 = "";
                while ((string2 = dataInputStream.readLine()) != null && n < 20) {
                    String string3 = this.getSvalue(string2 = string2.trim(), 0);
                    if (string3.equals("")) continue;
                    int n2 = this.getvalue(string2, 1);
                    if (n2 != 0) {
                        stringArray[n] = string3;
                        nArray[n] = n2;
                        stringArray2[n] = this.getSvalue(string2, 2);
                        ++n;
                        continue;
                    }
                    if (this.nrmb >= 100) continue;
                    this.rmember[this.nrmb] = string3;
                    ++this.nrmb;
                }
                dataInputStream.close();
            } else {
                this.notclan = true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.nmb = 0;
        if (!this.notclan) {
            int n3;
            for (n3 = 7; n3 > 0; --n3) {
                for (int i = 0; i < n; ++i) {
                    if (nArray[i] != n3) continue;
                    this.member[this.nmb] = stringArray[i];
                    this.mrank[this.nmb] = stringArray2[i];
                    this.mlevel[this.nmb] = nArray[i];
                    ++this.nmb;
                }
            }
            for (n3 = 0; n3 < this.nmb; ++n3) {
                if (!this.xt.nickname.toLowerCase().equals(this.member[n3].toLowerCase())) continue;
                if ((this.mlevel[n3] == 7 || n3 == 0) && this.nrmb != 0) {
                    this.showreqs = true;
                }
                if (this.xt.clan.toLowerCase().equals(this.claname.toLowerCase())) continue;
                this.attachetoclan = true;
            }
            if (this.xt.clan.toLowerCase().equals(this.claname.toLowerCase())) {
                for (n3 = 0; n3 < n; ++n3) {
                    this.clname[n3] = stringArray[n3];
                }
                this.ncln = n;
                this.loadedcm = true;
            }
        }
    }

    public void loadclanlink() {
        this.ltit = "";
        this.ldes = "";
        this.lurl = "";
        try {
            URL uRL = new URL("http://multiplayer.needformadness.com/clans/" + this.claname + "/link.txt?req=" + (int)(Math.random() * 1000.0) + "");
            uRL.openConnection().setConnectTimeout(2000);
            String string = uRL.openConnection().getContentType();
            if (string.equals("text/plain")) {
                DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                String string2 = "";
                for (int i = 0; (string2 = dataInputStream.readLine()) != null && i < 3; ++i) {
                    string2 = string2.trim();
                    if (i == 0) {
                        this.ltit = string2;
                    }
                    if (i == 1) {
                        this.ldes = string2;
                    }
                    if (i != 2) continue;
                    this.lurl = string2;
                }
                dataInputStream.close();
            }
        }
        catch (Exception exception) {
            this.ltit = "";
            this.ldes = "";
            this.lurl = "";
        }
    }

    public void loadfclan() {
        this.ncln = 0;
        try {
            URL uRL = new URL("http://multiplayer.needformadness.com/clans/" + this.xt.clan + "/members.txt?req=" + (int)(Math.random() * 1000.0) + "");
            uRL.openConnection().setConnectTimeout(2000);
            DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
            String string = "";
            while ((string = dataInputStream.readLine()) != null && this.ncln < 20) {
                String string2 = this.getSvalue(string = string.trim(), 0);
                if (string2.equals("") || this.getvalue(string, 1) == 0) continue;
                this.clname[this.ncln] = string2;
                ++this.ncln;
            }
            dataInputStream.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public int loadclancars() {
        this.m.csky[0] = 170;
        this.m.csky[1] = 220;
        this.m.csky[2] = 255;
        this.m.cfade[0] = 255;
        this.m.cfade[1] = 220;
        this.m.cfade[2] = 220;
        this.m.snap[0] = 0;
        this.m.snap[1] = 0;
        this.m.snap[2] = 0;
        int n = 0;
        this.gs.clcars.removeAll();
        this.gs.clcars.add(this.rd, "Select Car");
        try {
            URL uRL = new URL("http://multiplayer.needformadness.com/clans/" + this.claname + "/cars.txt?req=" + (int)(Math.random() * 1000.0) + "");
            uRL.openConnection().setConnectTimeout(2000);
            String string = uRL.openConnection().getContentType();
            if (string.equals("text/plain")) {
                DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                String string2 = "";
                for (int i = 0; (string2 = dataInputStream.readLine()) != null && i < 700; ++i) {
                    this.gs.clcars.add(this.rd, string2);
                    if (n == 1) continue;
                    n = 1;
                }
                dataInputStream.close();
            }
        }
        catch (Exception exception) {
            n = -2;
        }
        if (n == 1) {
            if (this.viewcar.equals("")) {
                this.gs.clcars.select(0);
            } else {
                this.gs.clcars.select(this.viewcar);
                this.viewcar = "";
            }
            this.selcar = this.gs.clcars.getSelectedItem();
        }
        return n;
    }

    public int loadaddcars() {
        Object object;
        String[] stringArray;
        int n = 3;
        int n2 = 0;
        String[] stringArray2 = new String[700];
        try {
            stringArray = new URL("http://multiplayer.needformadness.com/cars/lists/" + this.gs.tnick.getText() + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "");
            stringArray.openConnection().setConnectTimeout(2000);
            object = new DataInputStream(stringArray.openStream());
            String string = "";
            while ((string = ((DataInputStream)object).readLine()) != null) {
                string = "" + string.trim();
                if (!string.startsWith("mycars")) continue;
                boolean bl = true;
                while (bl && n2 < 700) {
                    stringArray2[n2] = this.getfuncSvalue("mycars", string, n2);
                    if (stringArray2[n2].equals("")) {
                        bl = false;
                        continue;
                    }
                    ++n2;
                }
            }
            ((FilterInputStream)object).close();
        }
        catch (Exception exception) {
            object = "" + exception;
            if (((String)object).indexOf("FileNotFound") != -1) {
                n2 = 0;
                n = 3;
            }
            n2 = -1;
            n = 4;
        }
        if (n2 > 0) {
            int n3;
            stringArray = new String[700];
            int n4 = 0;
            for (n3 = 0; n3 < n2; ++n3) {
                this.perry = "" + (int)((float)n3 / (float)n2 * 100.0f) + " %";
                try {
                    String string = "http://multiplayer.needformadness.com/cars/" + stringArray2[n3] + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "";
                    string = string.replace(' ', '_');
                    URL uRL = new URL(string);
                    uRL.openConnection().setConnectTimeout(2000);
                    DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                    String string2 = "";
                    while ((string2 = dataInputStream.readLine()) != null) {
                        String string3;
                        string2 = "" + string2.trim();
                        if (!string2.startsWith("details") || !(string3 = this.getfuncSvalue("details", string2, 0)).toLowerCase().equals(this.gs.tnick.getText().toLowerCase()) || string2.indexOf("Clan#") != -1) continue;
                        stringArray[n4] = stringArray2[n3];
                        ++n4;
                    }
                    dataInputStream.close();
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (n4 > 0) {
                this.gs.clcars.removeAll();
                for (n3 = 0; n3 < n4; ++n3) {
                    this.gs.clcars.add(this.rd, stringArray[n3]);
                }
                n = 5;
            } else {
                n = 3;
            }
        }
        return n;
    }

    public void loadiclancars(String string) {
        try {
            URL uRL = new URL("http://multiplayer.needformadness.com/clans/" + string + "/cars.txt");
            uRL.openConnection().setConnectTimeout(2000);
            String string2 = uRL.openConnection().getContentType();
            if (string2.equals("text/plain")) {
                this.gs.datat.removeAll();
                this.gs.datat.add(this.rd, "Select Car");
                DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                String string3 = "";
                for (int i = 0; (string3 = dataInputStream.readLine()) != null && i < 700; ++i) {
                    this.gs.datat.add(this.rd, string3);
                }
                dataInputStream.close();
            } else {
                this.gs.datat.removeAll();
                this.gs.datat.add(this.rd, "No clan cars where found.");
            }
        }
        catch (Exception exception) {
            this.gs.datat.removeAll();
            this.gs.datat.add(this.rd, "Failed to load cars, try again later...");
        }
        this.gs.datat.select(0);
    }

    public int loadclanstages() {
        int n = 0;
        this.gs.clcars.removeAll();
        this.gs.clcars.add(this.rd, "Select Stage");
        try {
            URL uRL = new URL("http://multiplayer.needformadness.com/clans/" + this.claname + "/stages.txt?req=" + (int)(Math.random() * 1000.0) + "");
            uRL.openConnection().setConnectTimeout(2000);
            String string = uRL.openConnection().getContentType();
            if (string.equals("text/plain")) {
                DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                String string2 = "";
                for (int i = 0; (string2 = dataInputStream.readLine()) != null && i < 700; ++i) {
                    this.gs.clcars.add(this.rd, string2);
                    if (n == 1) continue;
                    n = 1;
                }
                dataInputStream.close();
            }
        }
        catch (Exception exception) {
            n = -2;
        }
        if (n == 1) {
            if (this.viewcar.equals("")) {
                this.gs.clcars.select(0);
            } else {
                this.gs.clcars.select(this.viewcar);
                this.viewcar = "";
            }
            this.selstage = this.gs.clcars.getSelectedItem();
        }
        return n;
    }

    public int loadaddstages() {
        Object object;
        String[] stringArray;
        int n = 3;
        int n2 = 0;
        String[] stringArray2 = new String[700];
        try {
            stringArray = new URL("http://multiplayer.needformadness.com/tracks/lists/" + this.gs.tnick.getText() + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "");
            stringArray.openConnection().setConnectTimeout(2000);
            object = new DataInputStream(stringArray.openStream());
            String string = "";
            while ((string = ((DataInputStream)object).readLine()) != null) {
                string = "" + string.trim();
                if (!string.startsWith("mystages")) continue;
                boolean bl = true;
                while (bl && n2 < 700) {
                    stringArray2[n2] = this.getfuncSvalue("mystages", string, n2);
                    if (stringArray2[n2].equals("")) {
                        bl = false;
                        continue;
                    }
                    ++n2;
                }
            }
            ((FilterInputStream)object).close();
        }
        catch (Exception exception) {
            object = "" + exception;
            if (((String)object).indexOf("FileNotFound") != -1) {
                n2 = 0;
                n = 3;
            }
            n2 = -1;
            n = 4;
        }
        if (n2 > 0) {
            int n3;
            stringArray = new String[700];
            int n4 = 0;
            for (n3 = 0; n3 < n2; ++n3) {
                this.perry = "" + (int)((float)n3 / (float)n2 * 100.0f) + " %";
                try {
                    String string = "http://multiplayer.needformadness.com/tracks/" + stringArray2[n3] + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "";
                    string = string.replace(' ', '_');
                    URL uRL = new URL(string);
                    uRL.openConnection().setConnectTimeout(2000);
                    DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                    String string2 = "";
                    while ((string2 = dataInputStream.readLine()) != null) {
                        String string3;
                        string2 = "" + string2.trim();
                        if (!string2.startsWith("details") || !(string3 = this.getfuncSvalue("details", string2, 0)).toLowerCase().equals(this.gs.tnick.getText().toLowerCase()) || string2.indexOf("Clan#") != -1) continue;
                        stringArray[n4] = stringArray2[n3];
                        ++n4;
                    }
                    dataInputStream.close();
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (n4 > 0) {
                this.gs.clcars.removeAll();
                for (n3 = 0; n3 < n4; ++n3) {
                    this.gs.clcars.add(this.rd, stringArray[n3]);
                }
                n = 5;
            } else {
                n = 3;
            }
        }
        return n;
    }

    public void loadiclanstages(String string) {
        try {
            URL uRL = new URL("http://multiplayer.needformadness.com/clans/" + string + "/stages.txt");
            uRL.openConnection().setConnectTimeout(2000);
            String string2 = uRL.openConnection().getContentType();
            if (string2.equals("text/plain")) {
                this.gs.datat.removeAll();
                this.gs.datat.add(this.rd, "Select Stage");
                DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                String string3 = "";
                for (int i = 0; (string3 = dataInputStream.readLine()) != null && i < 700; ++i) {
                    this.gs.datat.add(this.rd, string3);
                }
                dataInputStream.close();
            } else {
                this.gs.datat.removeAll();
                this.gs.datat.add(this.rd, "No clan stages where found.");
            }
        }
        catch (Exception exception) {
            this.gs.datat.removeAll();
            this.gs.datat.add(this.rd, "Failed to load stages, try again later...");
        }
        this.gs.datat.select(0);
    }

    public void loadproinfo() {
        block16: {
            if (!this.proname.equals(this.xt.nickname) && this.npf == -1) {
                this.loadfriends();
            }
            this.racing = 0;
            this.wasting = 0;
            this.themesong = "";
            this.trackvol = 0;
            this.sentance = "";
            this.proclan = "";
            try {
                String string = "";
                if (this.proname.equals(this.xt.nickname)) {
                    string = "?req=" + (int)(Math.random() * 1000.0) + "";
                }
                URL uRL = new URL("http://multiplayer.needformadness.com/profiles/" + this.proname + "/info.txt" + string + "");
                uRL.openConnection().setConnectTimeout(2000);
                String string2 = uRL.openConnection().getContentType();
                if (!string2.equals("text/plain")) break block16;
                DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                String string3 = "";
                for (int i = 0; (string3 = dataInputStream.readLine()) != null && i < 9; ++i) {
                    int n;
                    string3 = string3.trim();
                    if (i == 0) {
                        this.themesong = string3;
                    }
                    if (i == 1) {
                        n = 0;
                        try {
                            n = Integer.valueOf(string3);
                        }
                        catch (Exception exception) {
                            n = 0;
                        }
                        this.trackvol = n;
                    }
                    if (i == 2) {
                        n = 0;
                        try {
                            n = Integer.valueOf(string3);
                        }
                        catch (Exception exception) {
                            n = 0;
                        }
                        this.racing = n;
                    }
                    if (i == 3) {
                        n = 0;
                        try {
                            n = Integer.valueOf(string3);
                        }
                        catch (Exception exception) {
                            n = 0;
                        }
                        this.wasting = n;
                    }
                    if (i == 4) {
                        this.proclan = string3;
                    }
                    if (i != 8) continue;
                    this.sentance = string3;
                }
                dataInputStream.close();
            }
            catch (Exception exception) {
                this.sentance = "Failed to load profile info, server error!";
            }
        }
    }

    public void loadprostages() {
        Object object;
        String[] stringArray = new String[700];
        int n = 0;
        String string = "";
        try {
            URL uRL = new URL("http://multiplayer.needformadness.com/tracks/lists/" + this.proname + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "");
            object = new DataInputStream(uRL.openStream());
            while ((string = ((DataInputStream)object).readLine()) != null) {
                string = "" + string.trim();
                if (!string.startsWith("mystages")) continue;
                boolean bl = true;
                while (bl && n < 700) {
                    stringArray[n] = this.getfuncSvalue("mystages", string, n);
                    if (stringArray[n].equals("")) {
                        bl = false;
                        continue;
                    }
                    ++n;
                }
            }
            this.loadpst = n > 0 ? 1 : -2;
            ((FilterInputStream)object).close();
        }
        catch (Exception exception) {
            object = "" + exception;
            this.loadpst = ((String)object).indexOf("FileNotFound") != -1 ? -2 : -1;
        }
        if (this.loadpst == 1) {
            this.gs.proitem.removeAll();
            this.gs.proitem.add(this.gs.rd, "Select Stage");
            for (int i = 0; i < n; ++i) {
                this.gs.proitem.add(this.gs.rd, stringArray[i]);
            }
            this.gs.proitem.select(0);
            this.loadpstage = "Select Stage";
            this.gs.proitem.show();
        }
        if (this.loadpst == -2) {
            this.gs.proitem.removeAll();
            this.gs.proitem.add(this.gs.rd, "No published or added stages found...");
            this.gs.proitem.select(0);
            this.gs.proitem.show();
        }
        if (this.loadpst == -1) {
            this.gs.proitem.removeAll();
            this.gs.proitem.add(this.gs.rd, "Failed to load stages, please try again later.");
            this.gs.proitem.select(0);
            this.gs.proitem.show();
        }
        System.gc();
    }

    public void loadfriends() {
        if (this.npf == -1) {
            this.freq = 0;
            try {
                URL uRL = new URL("http://multiplayer.needformadness.com/profiles/" + this.xt.nickname + "/friends.txt?req=" + (int)(Math.random() * 1000.0) + "");
                uRL.openConnection().setConnectTimeout(2000);
                String string = uRL.openConnection().getContentType();
                if (string.equals("text/plain")) {
                    int n = 0;
                    DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                    String string2 = "";
                    for (int i = 0; (string2 = dataInputStream.readLine()) != null && i < 3; ++i) {
                        String string3;
                        string2 = string2.trim();
                        if (i == 0) {
                            string3 = this.getSvalue(string2, n);
                            while (!string3.equals("") && this.npf < 900) {
                                this.fname[n] = string3;
                                string3 = this.getSvalue(string2, ++n);
                            }
                        }
                        if (i == 1) {
                            this.freqname = this.getSvalue(string2, 0);
                            if (!this.freqname.equals("")) {
                                this.freq = 1;
                            }
                        }
                        if (i != 2 || this.freq == 1) continue;
                        this.ncnf = 0;
                        string3 = this.getSvalue(string2, this.ncnf);
                        while (!string3.equals("") && this.ncnf < 10) {
                            this.cnfname[this.ncnf] = string3;
                            ++this.ncnf;
                            string3 = this.getSvalue(string2, this.ncnf);
                        }
                        if (this.ncnf == 0) continue;
                        this.freq = 6;
                    }
                    dataInputStream.close();
                    this.npf = n;
                } else {
                    this.npf = 0;
                }
            }
            catch (Exception exception) {
                this.npf = -2;
            }
        }
    }

    public void loadnews() {
        try {
            URL uRL = new URL("http://multiplayer.needformadness.com/interact/news.txt?req=" + (int)(Math.random() * 1000.0) + "");
            uRL.openConnection().setConnectTimeout(2000);
            DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
            String string = "";
            String string2 = "";
            this.il = 0;
            for (int i = 0; (string = dataInputStream.readLine()) != null && i < 300; ++i) {
                String string3;
                int n;
                string = string.trim();
                if (i == 0) {
                    for (n = 0; n < 4; ++n) {
                        this.newplayers[n] = this.getSvalue(string, n);
                    }
                }
                if (i >= 1 && i <= 5) {
                    this.nwtime[i - 1] = this.contime(this.getLvalue(string, 0));
                    this.nwarbs[i - 1] = this.getvalue(string, 1);
                    this.nwclan[i - 1] = this.getSvalue(string, 2);
                    this.nlclan[i - 1] = this.getSvalue(string, 3);
                    this.nwinob[i - 1] = this.getSvalue(string, 4);
                    this.nwinp[i - 1] = this.getvalue(string, 5);
                    this.nlosp[i - 1] = this.getvalue(string, 6);
                }
                if (i < 6 || this.il >= 300) continue;
                this.nttime[this.il] = this.contime(this.getLvalue(string, 0));
                n = this.getvalue(string, 1);
                if (n == 4) {
                    int n2 = this.getvalue(string, 4);
                    if (n2 <= 0) {
                        this.text[this.il] = "" + this.getSvalue(string, 2) + " has joined clan " + this.getSvalue(string, 3) + ".";
                    } else {
                        int n3 = this.getvalue(string, 5);
                        if (n3 == n2) {
                            this.text[this.il] = "" + this.getSvalue(string, 2) + " got a new rank in clan " + this.getSvalue(string, 3) + ".";
                        } else {
                            string3 = "promoted";
                            if (n3 - n2 < 0) {
                                string3 = "demoted";
                            }
                            this.text[this.il] = n3 != 7 ? "" + this.getSvalue(string, 2) + " has been " + string3 + " in clan " + this.getSvalue(string, 3) + " to a level " + n3 + " member." : "" + this.getSvalue(string, 2) + " has been " + string3 + " in clan " + this.getSvalue(string, 3) + " to Clan Admin!";
                        }
                    }
                    this.nln[this.il] = 2;
                    this.link[this.il][0][0] = this.getSvalue(string, 2);
                    this.link[this.il][0][1] = "0|" + this.getSvalue(string, 2) + "|";
                    this.link[this.il][1][0] = this.getSvalue(string, 3);
                    this.link[this.il][1][1] = "1|" + this.getSvalue(string, 3) + "|";
                    ++this.il;
                }
                if (n == 5) {
                    String string4 = this.getSvalue(string, 2);
                    String string5 = this.getSvalue(string, 4);
                    string3 = "left";
                    if (!string4.toLowerCase().equals(string5.toLowerCase())) {
                        string3 = "been removed from";
                    }
                    this.text[this.il] = "" + string4 + " has " + string3 + " clan " + this.getSvalue(string, 3) + ".";
                    this.nln[this.il] = 2;
                    this.link[this.il][0][0] = this.getSvalue(string, 2);
                    this.link[this.il][0][1] = "0|" + this.getSvalue(string, 2) + "|";
                    this.link[this.il][1][0] = this.getSvalue(string, 3);
                    this.link[this.il][1][1] = "1|" + this.getSvalue(string, 3) + "|";
                    ++this.il;
                }
                if (n == 6) {
                    this.text[this.il] = "" + this.getSvalue(string, 2) + " has updated clan " + this.getSvalue(string, 3) + "'s web presence.";
                    this.nln[this.il] = 3;
                    this.link[this.il][0][0] = this.getSvalue(string, 2);
                    this.link[this.il][0][1] = "0|" + this.getSvalue(string, 2) + "|";
                    this.link[this.il][1][0] = this.getSvalue(string, 3);
                    this.link[this.il][1][1] = "1|" + this.getSvalue(string, 3) + "|";
                    this.link[this.il][2][0] = "web presence";
                    this.link[this.il][2][1] = "2|" + this.getSvalue(string, 3) + "|";
                    ++this.il;
                }
                if (n == 7 && string2.indexOf("#" + this.getSvalue(string, 4) + "#") == -1) {
                    this.text[this.il] = "" + this.getSvalue(string, 2) + " has added car " + this.getSvalue(string, 4) + " to clan " + this.getSvalue(string, 3) + ".";
                    this.nln[this.il] = 3;
                    this.link[this.il][0][0] = this.getSvalue(string, 2);
                    this.link[this.il][0][1] = "0|" + this.getSvalue(string, 2) + "|";
                    this.link[this.il][1][0] = this.getSvalue(string, 3);
                    this.link[this.il][1][1] = "1|" + this.getSvalue(string, 3) + "|";
                    this.link[this.il][2][0] = this.getSvalue(string, 4);
                    this.link[this.il][2][1] = "3|" + this.getSvalue(string, 4) + "|" + this.getSvalue(string, 3) + "|";
                    string2 = string2 + "#" + this.getSvalue(string, 4) + "#";
                    ++this.il;
                }
                if (n == 8 && string2.indexOf("#" + this.getSvalue(string, 4) + "#") == -1) {
                    String string6 = this.getSvalue(string, 4);
                    if (string6.length() > 20) {
                        string6 = "" + string6.substring(0, 20) + "...";
                    }
                    this.text[this.il] = "" + this.getSvalue(string, 2) + " has added stage " + string6 + " to clan " + this.getSvalue(string, 3) + ".";
                    this.nln[this.il] = 3;
                    this.link[this.il][0][0] = this.getSvalue(string, 2);
                    this.link[this.il][0][1] = "0|" + this.getSvalue(string, 2) + "|";
                    this.link[this.il][1][0] = this.getSvalue(string, 3);
                    this.link[this.il][1][1] = "1|" + this.getSvalue(string, 3) + "|";
                    this.link[this.il][2][0] = string6;
                    this.link[this.il][2][1] = "4|" + this.getSvalue(string, 4) + "|" + this.getSvalue(string, 3) + "|";
                    string2 = string2 + "#" + this.getSvalue(string, 4) + "#";
                    ++this.il;
                }
                if (n == 9) {
                    String string7 = this.getSvalue(string, 2);
                    if (string7.startsWith("War")) {
                        this.text[this.il] = "Clans " + this.getSvalue(string, 7) + " & " + this.getSvalue(string, 8) + " have now started a war!";
                        this.nln[this.il] = 2;
                        this.link[this.il][0][0] = this.getSvalue(string, 7);
                        this.link[this.il][0][1] = "1|" + this.getSvalue(string, 7) + "|";
                        this.link[this.il][1][0] = this.getSvalue(string, 8);
                        this.link[this.il][1][1] = "1|" + this.getSvalue(string, 8) + "|";
                        ++this.il;
                    }
                    if (string7.startsWith("Car")) {
                        this.text[this.il] = "Clans " + this.getSvalue(string, 7) + " & " + this.getSvalue(string, 8) + " have started a car battle!";
                        this.nln[this.il] = 2;
                        this.link[this.il][0][0] = this.getSvalue(string, 7);
                        this.link[this.il][0][1] = "1|" + this.getSvalue(string, 7) + "|";
                        this.link[this.il][1][0] = this.getSvalue(string, 8);
                        this.link[this.il][1][1] = "1|" + this.getSvalue(string, 8) + "|";
                        ++this.il;
                        if (this.il < 300) {
                            this.text[this.il] = "Battle over cars: [" + this.getSvalue(string, 4) + "] & [" + this.getSvalue(string, 5) + "]";
                            this.nln[this.il] = 2;
                            this.link[this.il][0][0] = this.getSvalue(string, 4);
                            this.link[this.il][0][1] = "3|" + this.getSvalue(string, 4) + "|" + this.getSvalue(string, 8) + "|";
                            this.link[this.il][1][0] = this.getSvalue(string, 5);
                            this.link[this.il][1][1] = "3|" + this.getSvalue(string, 5) + "|" + this.getSvalue(string, 7) + "|";
                            this.nttime[this.il] = "";
                            ++this.il;
                        }
                    }
                    if (string7.startsWith("Stage")) {
                        String string8 = this.getSvalue(string, 4);
                        if (string8.length() > 20) {
                            string8 = "" + string8.substring(0, 20) + "...";
                        }
                        if ((string3 = this.getSvalue(string, 5)).length() > 20) {
                            string3 = "" + string3.substring(0, 20) + "...";
                        }
                        this.text[this.il] = "Clans " + this.getSvalue(string, 7) + " & " + this.getSvalue(string, 8) + " have started a stage battle!";
                        this.nln[this.il] = 2;
                        this.link[this.il][0][0] = this.getSvalue(string, 7);
                        this.link[this.il][0][1] = "1|" + this.getSvalue(string, 7) + "|";
                        this.link[this.il][1][0] = this.getSvalue(string, 8);
                        this.link[this.il][1][1] = "1|" + this.getSvalue(string, 8) + "|";
                        ++this.il;
                        if (this.il < 300) {
                            this.text[this.il] = "Battle over stages: [" + string8 + "] & [" + string3 + "]";
                            this.nln[this.il] = 2;
                            this.link[this.il][0][0] = string8;
                            this.link[this.il][0][1] = "4|" + this.getSvalue(string, 4) + "|" + this.getSvalue(string, 8) + "|";
                            this.link[this.il][1][0] = string3;
                            this.link[this.il][1][1] = "4|" + this.getSvalue(string, 5) + "|" + this.getSvalue(string, 7) + "|";
                            this.nttime[this.il] = "";
                            ++this.il;
                        }
                    }
                }
                if (n != 10 && n != 11 && n != 12 && n != 13) continue;
                this.text[this.il] = n == 11 ? "" + this.getSvalue(string, 2) + " has re-claimed its title as clan wars world champion!" : "" + this.getSvalue(string, 2) + " has now become the new clan wars world champion!";
                this.nln[this.il] = 2;
                this.link[this.il][0][0] = this.getSvalue(string, 2);
                this.link[this.il][0][1] = "1|" + this.getSvalue(string, 2) + "|";
                this.link[this.il][1][0] = "clan wars world champion";
                this.link[this.il][1][1] = "5|championship|";
                if (this.getSvalue(string, 2).equals("")) {
                    this.text[this.il] = "No one is currently the clan wars world champion!";
                    this.nln[this.il] = 1;
                    this.link[this.il][0][0] = "clan wars world champion";
                    this.link[this.il][0][1] = "5|championship|";
                }
                ++this.il;
                if (n == 10) {
                    this.text[this.il] = "" + this.getSvalue(string, 2) + " recent win against " + this.getSvalue(string, 4) + " has given it the championship title!";
                }
                if (n == 11) {
                    this.text[this.il] = "" + this.getSvalue(string, 2) + " has successfully defended its championship title against " + this.getSvalue(string, 4) + "!";
                }
                if (n == 12) {
                    this.text[this.il] = "A recent war between " + this.getSvalue(string, 3) + " and " + this.getSvalue(string, 4) + " has resulted in a change of points!";
                }
                if (n == 13) {
                    this.text[this.il] = "Clan " + this.getSvalue(string, 3) + " removed itself from the game which resulted in a change of points!";
                }
                this.nttime[this.il] = "";
                this.nln[this.il] = 0;
                ++this.il;
            }
            dataInputStream.close();
            this.spos6 = 0;
            this.loadednews = 1;
        }
        catch (Exception exception) {
            this.loadednews = -1;
        }
    }

    public void loadchamps() {
        this.eng = -1;
        this.engo = 0;
        if (this.maxclans <= 0) {
            this.maxclans = 1000;
        }
        this.ncc = 0;
        this.champ = -1;
        int n = 0;
        this.conclan = new String[this.maxclans];
        this.totp = new int[this.maxclans];
        this.nvc = new int[this.maxclans];
        this.points = new int[this.maxclans][this.maxclans];
        this.verclan = new String[this.maxclans][this.maxclans];
        try {
            int n2;
            URL uRL = new URL("http://multiplayer.needformadness.com/interact/clanstat.txt?req=" + (int)(Math.random() * 1000.0) + "");
            uRL.openConnection().setConnectTimeout(2000);
            DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
            String string = "";
            while ((string = dataInputStream.readLine()) != null && this.ncc < this.maxclans) {
                this.conclan[this.ncc] = this.getSvalue(string, 0);
                if (this.conclan[this.ncc].equals("")) continue;
                this.totp[this.ncc] = 0;
                this.nvc[this.ncc] = 0;
                int n3 = this.getpvalue(string, 1);
                while (n3 != 0 && this.nvc[this.ncc] < this.maxclans) {
                    int n4 = this.ncc;
                    this.totp[n4] = this.totp[n4] + n3;
                    this.points[this.ncc][this.nvc[this.ncc]] = n3;
                    this.verclan[this.ncc][this.nvc[this.ncc]] = this.getSvalue(string, 2 + this.nvc[this.ncc] * 2);
                    int n5 = this.ncc;
                    this.nvc[n5] = this.nvc[n5] + 1;
                    n3 = this.getpvalue(string, 1 + this.nvc[this.ncc] * 2);
                }
                if (this.totp[this.ncc] >= n && this.totp[this.ncc] >= 3) {
                    if (this.totp[this.ncc] == n) {
                        this.champ = -2;
                    } else {
                        n = this.totp[this.ncc];
                        this.champ = this.ncc;
                    }
                }
                ++this.ncc;
            }
            dataInputStream.close();
            this.ord = new int[this.ncc];
            int[] nArray = new int[this.ncc];
            this.leadsby = n;
            for (n2 = 0; n2 < this.ncc; ++n2) {
                if (n2 != this.champ && n - this.totp[n2] < this.leadsby) {
                    this.leadsby = n - this.totp[n2];
                }
                nArray[n2] = 0;
            }
            for (n2 = 0; n2 < this.ncc; ++n2) {
                for (int i = n2 + 1; i < this.ncc; ++i) {
                    if (this.totp[n2] < this.totp[i]) {
                        int n6 = n2;
                        nArray[n6] = nArray[n6] + 1;
                        continue;
                    }
                    int n7 = i;
                    nArray[n7] = nArray[n7] + 1;
                }
                this.ord[nArray[n2]] = n2;
            }
            this.spos6 = 0;
            this.loadwstat = 1;
        }
        catch (Exception exception) {
            String string = "" + exception;
            this.loadwstat = string.indexOf("java.io.FileNotFoundException") != -1 ? 1 : -1;
        }
    }

    public String contime(long l) {
        String string = "";
        if (l != -1L) {
            try {
                String string2;
                int n;
                long l2 = this.ntime - l;
                if (l2 >= 1000L && l2 < 60000L) {
                    string = "seconds ago";
                }
                if (l2 >= 60000L && l2 < 3600000L) {
                    n = (int)(l2 / 60000L);
                    string2 = "s";
                    if (n == 1) {
                        string2 = "";
                    }
                    string = "" + n + " minute" + string2 + " ago";
                }
                if (l2 >= 3600000L && l2 < 86400000L) {
                    n = (int)(l2 / 3600000L);
                    string2 = "s";
                    if (n == 1) {
                        string2 = "";
                    }
                    string = "" + n + " hour" + string2 + " ago";
                }
                if (l2 >= 86400000L) {
                    n = (int)(l2 / 86400000L);
                    string2 = "s";
                    if (n == 1) {
                        string2 = "";
                    }
                    string = "" + n + " day" + string2 + " ago";
                }
            }
            catch (Exception exception) {
                string = "";
            }
        }
        return string;
    }

    public void tlink(Graphics2D graphics2D, int n, int n2, String string, String string2, int n3, int n4, boolean bl, int n5, int n6, int n7, String string3, String string4) {
        this.ftm = this.rdo.getFontMetrics();
        int n8 = 0;
        int n9 = 0;
        int n10 = string.indexOf(string2);
        if (n10 != -1) {
            n8 = this.ftm.stringWidth(string.substring(0, n10)) + n;
            n9 = n8 + this.ftm.stringWidth(string2) - 2;
        }
        this.rdo.drawLine(n8, n2 + 1, n9, n2 + 1);
        if (n3 > n8 + n5 && n3 < n9 + n5 && n4 > n2 - 11 + n6 && n4 < n2 + 1 + n6) {
            this.cur = 12;
            if (bl) {
                if (n7 == 0) {
                    this.tab = 1;
                    if (!this.proname.equals(string3)) {
                        this.proname = string3;
                        this.loadedp = false;
                        this.onexitpro();
                    }
                }
                if (n7 == 1) {
                    if (!this.claname.equals(string3)) {
                        this.claname = string3;
                        this.loadedc = false;
                    }
                    this.spos5 = 0;
                    this.lspos5 = 0;
                    this.cfase = 3;
                    this.ctab = 0;
                    this.tab = 3;
                }
                if (n7 == 2) {
                    if (!this.claname.equals(string3)) {
                        this.claname = string3;
                        this.loadedc = false;
                    }
                    this.spos5 = 0;
                    this.lspos5 = 0;
                    this.cfase = 3;
                    this.loadedlink = false;
                    this.ctab = 4;
                    this.tab = 3;
                }
                if (n7 == 3) {
                    this.viewcar = string3;
                    if (!this.claname.equals(string4)) {
                        this.claname = string4;
                        this.loadedc = false;
                    }
                    this.spos5 = 0;
                    this.lspos5 = 0;
                    this.cfase = 3;
                    this.loadedcars = -1;
                    this.loadedcar = 0;
                    this.ctab = 2;
                    this.tab = 3;
                }
                if (n7 == 4) {
                    this.viewcar = string3;
                    if (!this.claname.equals(string4)) {
                        this.claname = string4;
                        this.loadedc = false;
                    }
                    this.spos5 = 0;
                    this.lspos5 = 0;
                    this.cfase = 3;
                    this.loadedstages = -1;
                    this.loadedstage = 0;
                    this.ctab = 3;
                    this.tab = 3;
                }
                if (n7 == 5) {
                    this.loadwstat = 0;
                    this.ntab = 1;
                }
            }
        }
    }

    public void loadwarb() {
        try {
            URL uRL = new URL("http://multiplayer.needformadness.com/clans/" + this.xt.clan + "/inter.txt?req=" + (int)(Math.random() * 1000.0) + "");
            uRL.openConnection().setConnectTimeout(2000);
            String string = uRL.openConnection().getContentType();
            this.gs.warb.removeAll();
            this.gs.warb.add(this.rd, " Select  War / Battle");
            if (string.equals("text/plain")) {
                DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                String string2 = "";
                boolean bl = false;
                for (int i = 0; (string2 = dataInputStream.readLine()) != null && i < 100; ++i) {
                    String string3 = this.getSvalue(string2 = string2.trim(), 5);
                    if (string3.equals("War")) {
                        this.gs.warb.addw(" War with " + this.getSvalue(string2, 0) + "", "" + this.getSvalue(string2, 6) + "|" + this.getSvalue(string2, 0) + "|");
                        bl = true;
                    }
                    if (string3.equals("Car Battle")) {
                        this.gs.warb.addw(" Car battle with " + this.getSvalue(string2, 0) + "", "" + this.getSvalue(string2, 6) + "|" + this.getSvalue(string2, 0) + "|");
                        bl = true;
                    }
                    if (!string3.equals("Stage Battle")) continue;
                    this.gs.warb.addw(" Stage battle with " + this.getSvalue(string2, 0) + "", "" + this.getSvalue(string2, 6) + "|" + this.getSvalue(string2, 0) + "|");
                    bl = true;
                }
                dataInputStream.close();
                if (!bl) {
                    this.gs.warb.removeAll();
                    this.gs.warb.add(this.rd, "Your clan has not started any new wars or battles.");
                }
            } else {
                this.gs.warb.removeAll();
                this.gs.warb.add(this.rd, "Your clan has not started any wars or battles yet.");
            }
        }
        catch (Exception exception) {
            this.gs.warb.removeAll();
            this.gs.warb.add(this.rd, "Error occurred while loading, please try again later.");
        }
        this.gs.warb.select(0);
    }

    public void loadwgames() {
        block31: {
            this.canredo = false;
            this.gameturn = -1;
            this.lwbwinner = "";
            this.ascore = 0;
            this.vscore = 0;
            this.gs.pgame.removeAll();
            this.gs.pgame.add(this.rd, " Select Game");
            int n = 5;
            String string = "battle";
            this.warb = 2;
            if (this.gs.warb.sopts[this.gs.warb.sel].startsWith(" War with")) {
                n = 9;
                string = "war";
                this.warb = 1;
            }
            if (this.gs.warb.sopts[this.gs.warb.sel].startsWith(" Stage battle with")) {
                this.warb = 3;
            }
            this.warbnum = this.getSvalue(this.gs.warb.opts[this.gs.warb.sel], 0);
            this.vclan = this.getSvalue(this.gs.warb.opts[this.gs.warb.sel], 1);
            try {
                URL uRL = new URL("http://multiplayer.needformadness.com/interact/" + string + "/" + this.warbnum + ".txt?req=" + (int)(Math.random() * 1000.0) + "");
                uRL.openConnection().setConnectTimeout(2000);
                String string2 = uRL.openConnection().getContentType();
                if (string2.equals("text/plain")) {
                    int n2;
                    DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                    String string3 = "";
                    for (n2 = 0; (string3 = dataInputStream.readLine()) != null && n2 < n; ++n2) {
                        String string4 = this.getSvalue(string3, 0);
                        if (string4.startsWith("#")) {
                            int n3 = 1;
                            try {
                                n3 = Integer.valueOf(string4.substring(1));
                            }
                            catch (Exception exception) {
                                n3 = 1;
                            }
                            this.wbstage[n2] = n3;
                            string4 = "NFM 1 - Stage " + n3 + "";
                            if (n3 > 10) {
                                string4 = "NFM 2 - Stage " + (n3 - 10) + "";
                            }
                            if (n3 > 27) {
                                string4 = "NFM Multiplayer - Stage " + (n3 - 27) + "";
                            }
                        } else {
                            this.wbstage[n2] = 101;
                        }
                        this.wbstages[n2] = string4;
                        this.wblaps[n2] = this.getvalue(string3, 1);
                        this.wbcars[n2] = this.getvalue(string3, 2);
                        this.wbclass[n2] = this.getvalue(string3, 3);
                        this.wbfix[n2] = this.getvalue(string3, 4);
                        String string5 = this.getSvalue(string3, 5);
                        String string6 = "";
                        if (this.wbcars[n2] == 2) {
                            string6 = ",  Clan Cars";
                        }
                        if (this.wbcars[n2] == 3) {
                            string6 = ",  Game Cars";
                        }
                        if (this.wbclass[n2] == 1) {
                            string6 = string6 + ",  Class C";
                        }
                        if (this.wbclass[n2] == 2) {
                            string6 = string6 + ",  Class B & C";
                        }
                        if (this.wbclass[n2] == 3) {
                            string6 = string6 + ",  Class B";
                        }
                        if (this.wbclass[n2] == 4) {
                            string6 = string6 + ",  Class A & B";
                        }
                        if (this.wbclass[n2] == 5) {
                            string6 = string6 + ",  Class A";
                        }
                        String string7 = "";
                        if (this.wbfix[n2] == 1) {
                            string7 = ",  4 Fixes";
                        }
                        if (this.wbfix[n2] == 2) {
                            string7 = ",  3 Fixes";
                        }
                        if (this.wbfix[n2] == 3) {
                            string7 = ",  2 Fixes";
                        }
                        if (this.wbfix[n2] == 4) {
                            string7 = ",  1 Fix";
                        }
                        if (this.wbfix[n2] == 5) {
                            string7 = ",  No Fixing";
                        }
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        String string8 = this.wbstages[n2];
                        String string9 = "Game #" + (n2 + 1) + ":  " + string8 + "" + string6 + "" + string7 + ",  " + this.wblaps[n2] + " Laps";
                        while (this.ftm.stringWidth(string9) > 400) {
                            string8 = string8.substring(0, string8.length() - 1);
                            string9 = "Game #" + (n2 + 1) + ":  " + string8 + "..." + string6 + "" + string7 + ",  " + this.wblaps[n2] + " Laps";
                        }
                        if (this.gameturn == -1) {
                            if (string5.equals("") || string5.equals("#redo#")) {
                                if (!string5.equals("#redo#") && n2 > 0) {
                                    this.canredo = true;
                                }
                                this.gameturn = n2;
                                this.gameturndisp = string9;
                            } else {
                                if (string5.toLowerCase().equals(this.xt.clan.toLowerCase())) {
                                    ++this.ascore;
                                }
                                if (string5.toLowerCase().equals(this.vclan.toLowerCase())) {
                                    ++this.vscore;
                                }
                                this.lwbwinner = string5;
                            }
                        }
                        this.gs.pgame.add(this.rd, " " + string9 + "");
                    }
                    dataInputStream.close();
                    this.loadwbgames = n2 != 0 ? 2 : 3;
                    break block31;
                }
                this.loadwbgames = 4;
            }
            catch (Exception exception) {
                this.loadwbgames = 3;
            }
        }
    }

    public void redogame() {
        try {
            this.socket = new Socket(this.lg.servers[0], 7061);
            this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.dout = new PrintWriter(this.socket.getOutputStream(), true);
            String string = "101|43|" + this.warb + "|" + this.warbnum + "|";
            this.dout.println(string);
            String string2 = this.din.readLine();
            if (!string2.equals("OK")) {
                this.loadwbgames = 6;
            }
            this.socket.close();
            this.socket = null;
            this.din.close();
            this.din = null;
            this.dout.close();
            this.dout = null;
        }
        catch (Exception exception) {
            this.loadwbgames = 6;
        }
        if (this.loadwbgames != 6) {
            this.loadwgames();
        }
    }

    public boolean drawbutton(Image image, int n, int n2, int n3, int n4, boolean bl) {
        boolean bl2 = false;
        boolean bl3 = false;
        int n5 = image.getWidth(this.ob);
        if (Math.abs(n3 - n) < n5 / 2 + 12 && Math.abs(n4 - n2) < 14 && bl) {
            bl3 = true;
        }
        if (Math.abs(n3 - n) < n5 / 2 + 12 && Math.abs(n4 - n2) < 14 && this.gs.mouses <= -1) {
            bl2 = true;
            this.gs.mouses = 0;
        }
        if (!bl3) {
            this.rd.drawImage(image, n - n5 / 2, n2 - image.getHeight(this.ob) / 2, null);
            this.rd.drawImage(this.xt.bols, n - n5 / 2 - 15, n2 - 13, null);
            this.rd.drawImage(this.xt.bors, n + n5 / 2 + 9, n2 - 13, null);
            this.rd.drawImage(this.xt.bot, n - n5 / 2 - 9, n2 - 13, n5 + 18, 3, null);
            this.rd.drawImage(this.xt.bob, n - n5 / 2 - 9, n2 + 10, n5 + 18, 3, null);
        } else {
            this.rd.drawImage(image, n - n5 / 2 + 1, n2 - image.getHeight(this.ob) / 2 + 1, null);
            this.rd.drawImage(this.xt.bolps, n - n5 / 2 - 15, n2 - 13, null);
            this.rd.drawImage(this.xt.borps, n + n5 / 2 + 9, n2 - 13, null);
            this.rd.drawImage(this.xt.bob, n - n5 / 2 - 9, n2 - 13, n5 + 18, 3, null);
            this.rd.drawImage(this.xt.bot, n - n5 / 2 - 9, n2 + 10, n5 + 18, 3, null);
        }
        return bl2;
    }

    public boolean stringbutton(Graphics2D graphics2D, String string, int n, int n2, int n3, int n4, int n5, boolean bl, int n6, int n7) {
        boolean bl2 = false;
        boolean bl3 = false;
        graphics2D.setFont(new Font("Arial", 1, 12));
        this.ftm = graphics2D.getFontMetrics();
        if (n3 == 6) {
            graphics2D.setFont(new Font("Arial", 1, 11));
            this.ftm = graphics2D.getFontMetrics();
        }
        int n8 = this.ftm.stringWidth(string);
        if (Math.abs(n4 - n6 - n) < n8 / 2 + 12 && Math.abs(n5 - n7 - n2) < 14 && bl) {
            bl3 = true;
        }
        if (!(Math.abs(n4 - n6 - n) >= n8 / 2 + 12 || Math.abs(n5 - n7 - n2) >= 14 || this.gs.mouses > -1 || this.blocknote != 0 || this.blockb != 0 || this.gs.openm || this.editc != 0 && n6 != 0)) {
            bl2 = true;
            this.gs.mouses = 0;
        }
        if (this.blocknote != 0) {
            --this.blocknote;
        }
        boolean bl4 = false;
        if (n3 < 0) {
            n3 *= -1;
            bl4 = true;
        }
        if (bl4) {
            this.rdo.setComposite(AlphaComposite.getInstance(3, 0.7f));
        }
        if (!bl3) {
            graphics2D.setColor(this.colorb2k(bl4, 220, 220, 220));
            graphics2D.fillRect(n - n8 / 2 - 10, n2 - (17 - n3), n8 + 20, 25 - n3 * 2);
            graphics2D.setColor(this.colorb2k(bl4, 240, 240, 240));
            graphics2D.drawLine(n - n8 / 2 - 10, n2 - (17 - n3), n + n8 / 2 + 10, n2 - (17 - n3));
            graphics2D.drawLine(n - n8 / 2 - 10, n2 - (18 - n3), n + n8 / 2 + 10, n2 - (18 - n3));
            graphics2D.drawLine(n - n8 / 2 - 9, n2 - (19 - n3), n + n8 / 2 + 9, n2 - (19 - n3));
            graphics2D.setColor(this.colorb2k(bl4, 200, 200, 200));
            graphics2D.drawLine(n + n8 / 2 + 10, n2 - (17 - n3), n + n8 / 2 + 10, n2 + (7 - n3));
            graphics2D.drawLine(n + n8 / 2 + 11, n2 - (17 - n3), n + n8 / 2 + 11, n2 + (7 - n3));
            graphics2D.drawLine(n + n8 / 2 + 12, n2 - (16 - n3), n + n8 / 2 + 12, n2 + (6 - n3));
            graphics2D.drawLine(n - n8 / 2 - 10, n2 + (7 - n3), n + n8 / 2 + 10, n2 + (7 - n3));
            graphics2D.drawLine(n - n8 / 2 - 10, n2 + (8 - n3), n + n8 / 2 + 10, n2 + (8 - n3));
            graphics2D.drawLine(n - n8 / 2 - 9, n2 + (9 - n3), n + n8 / 2 + 9, n2 + (9 - n3));
            graphics2D.setColor(this.colorb2k(bl4, 240, 240, 240));
            graphics2D.drawLine(n - n8 / 2 - 10, n2 - (17 - n3), n - n8 / 2 - 10, n2 + (7 - n3));
            graphics2D.drawLine(n - n8 / 2 - 11, n2 - (17 - n3), n - n8 / 2 - 11, n2 + (7 - n3));
            graphics2D.drawLine(n - n8 / 2 - 12, n2 - (16 - n3), n - n8 / 2 - 12, n2 + (6 - n3));
            if (bl4) {
                this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
            }
            graphics2D.setColor(new Color(0, 0, 0));
            graphics2D.drawString(string, n - n8 / 2, n2);
        } else {
            graphics2D.setColor(this.colorb2k(bl4, 210, 210, 210));
            graphics2D.fillRect(n - n8 / 2 - 10, n2 - (17 - n3), n8 + 20, 25 - n3 * 2);
            graphics2D.setColor(this.colorb2k(bl4, 200, 200, 200));
            graphics2D.drawLine(n - n8 / 2 - 10, n2 - (17 - n3), n + n8 / 2 + 10, n2 - (17 - n3));
            graphics2D.drawLine(n - n8 / 2 - 10, n2 - (18 - n3), n + n8 / 2 + 10, n2 - (18 - n3));
            graphics2D.drawLine(n - n8 / 2 - 9, n2 - (19 - n3), n + n8 / 2 + 9, n2 - (19 - n3));
            graphics2D.drawLine(n + n8 / 2 + 10, n2 - (17 - n3), n + n8 / 2 + 10, n2 + (7 - n3));
            graphics2D.drawLine(n + n8 / 2 + 11, n2 - (17 - n3), n + n8 / 2 + 11, n2 + (7 - n3));
            graphics2D.drawLine(n + n8 / 2 + 12, n2 - (16 - n3), n + n8 / 2 + 12, n2 + (6 - n3));
            graphics2D.drawLine(n - n8 / 2 - 10, n2 + (7 - n3), n + n8 / 2 + 10, n2 + (7 - n3));
            graphics2D.drawLine(n - n8 / 2 - 10, n2 + (8 - n3), n + n8 / 2 + 10, n2 + (8 - n3));
            graphics2D.drawLine(n - n8 / 2 - 9, n2 + (9 - n3), n + n8 / 2 + 9, n2 + (9 - n3));
            graphics2D.drawLine(n - n8 / 2 - 10, n2 - (17 - n3), n - n8 / 2 - 10, n2 + (7 - n3));
            graphics2D.drawLine(n - n8 / 2 - 11, n2 - (17 - n3), n - n8 / 2 - 11, n2 + (7 - n3));
            graphics2D.drawLine(n - n8 / 2 - 12, n2 - (16 - n3), n - n8 / 2 - 12, n2 + (6 - n3));
            if (bl4) {
                this.rdo.setComposite(AlphaComposite.getInstance(3, 1.0f));
            }
            graphics2D.setColor(new Color(0, 0, 0));
            graphics2D.drawString(string, n - n8 / 2 + 1, n2);
        }
        return bl2;
    }

    public Color color2k(int n, int n2, int n3) {
        Color color = new Color(n, n2, n3);
        float[] fArray = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), fArray);
        fArray[0] = 0.13f;
        fArray[1] = 0.35f;
        return Color.getHSBColor(fArray[0], fArray[1], fArray[2]);
    }

    public Color colorb2k(boolean bl, int n, int n2, int n3) {
        Color color = new Color(n, n2, n3);
        if (!bl) {
            float[] fArray = new float[3];
            Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), fArray);
            fArray[0] = 0.13f;
            fArray[1] = 0.35f;
            if (this.darker) {
                fArray[2] = fArray[2] * 0.9f;
            }
            color = Color.getHSBColor(fArray[0], fArray[1], fArray[2]);
        } else {
            color = new Color((int)((float)n * 0.9f), (int)((float)n2 * 0.9f), (int)((float)n3 * 0.9f));
        }
        return color;
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

    public long getLvalue(String string, int n) {
        long l = -1L;
        try {
            int n2 = 0;
            int n3 = 0;
            String string2 = "";
            String string3 = "";
            for (int i = 0; i < string.length() && n3 != 2; ++i) {
                string2 = "" + string.charAt(i);
                if (string2.equals("|")) {
                    if (n3 != 1 && ++n2 <= n) continue;
                    n3 = 2;
                    continue;
                }
                if (n2 != n) continue;
                string3 = string3 + string2;
                n3 = 1;
            }
            if (string3.equals("")) {
                string3 = "-1";
            }
            l = Long.valueOf(string3);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return l;
    }

    public int getpvalue(String string, int n) {
        int n2 = 0;
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
                string3 = "0";
            }
            n2 = Integer.valueOf(string3);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return n2;
    }

    public int getfuncvalue(String string, String string2, int n) {
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

    public String getfuncSvalue(String string, String string2, int n) {
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
}

