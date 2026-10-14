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
import java.awt.RenderingHints;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

public class Lobby
implements Runnable {
    Graphics2D rd;
    Login lg;
    Globe gb;
    xtGraphics xt;
    CarDefine cd;
    Medium m;
    FontMetrics ftm;
    ImageObserver ob;
    GameSparker gs;
    Thread connector;
    int conon = 0;
    boolean regnow = false;
    boolean lanlogged = false;
    int fase = 0;
    int stage = 0;
    int laps = 3;
    String stagename = "";
    int nfix = 0;
    boolean notb = false;
    boolean[] pessd = new boolean[]{false, false, false, false, false, false, false, false};
    int[] bx = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    int[] by = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    int[] bw = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    int btn = 0;
    int pbtn = 0;
    int nflk = 0;
    int ncnt = 0;
    int rerr = 0;
    int pback = 0;
    int cflk = 0;
    int sflk = 0;
    String msg = "";
    String lmsg = "| Searching/Waiting for other LAN Players |";
    int opselect = 0;
    boolean lloaded = false;
    int npo = 0;
    String[] pnames = new String[200];
    int[] pcars = new int[200];
    String[] pcarnames = new String[200];
    String[] pclan = new String[200];
    int[] pgames = new int[200];
    float[][] pcols = new float[200][6];
    int prnpo = 0;
    String[] prnames = new String[]{"", "", "", "", "", "", "", ""};
    int[] ppos = new int[]{6, 6, 6, 6, 6, 6, 6, 6};
    int[] plap = new int[]{6, 6, 6, 6, 6, 6, 6, 6};
    int[] ppow = new int[]{50, 50, 50, 50, 50, 50, 50, 50};
    int[] pdam = new int[]{50, 50, 50, 50, 50, 50, 50, 50};
    int prevloaded = -1;
    String stuntname = "";
    String lapsname = "";
    String wastename = "";
    int ngm = 0;
    int[] gnum = new int[500];
    int[] gstgn = new int[500];
    String[] gstages = new String[500];
    int[] gnlaps = new int[500];
    int[] mnpls = new int[500];
    int[] mnbts = new int[500];
    int[] wait = new int[500];
    int[] gcrs = new int[500];
    int[] gclss = new int[500];
    int[] gfx = new int[500];
    int[] gntb = new int[500];
    String[] gplyrs = new String[500];
    int[] gwarb = new int[500];
    String[] gwarbnum = new String[500];
    int[] gameturn = new int[500];
    String[] gaclan = new String[500];
    String[] gvclan = new String[500];
    int[] gascore = new int[500];
    int[] gvscore = new int[500];
    int[] gwtyp = new int[500];
    String[] gmaker = new String[500];
    int[] npls = new int[500];
    int ongame = -1;
    int join = -1;
    int chalngd = -1;
    int im = 0;
    int longame = -1;
    int onjoin = -1;
    int ontyp = 0;
    int dispcar = -1;
    int forcar = -1;
    int addstage = 0;
    ContO dispco = null;
    boolean fstart = false;
    boolean jflexo = false;
    String chalby = "";
    int ctime = 0;
    boolean invo = false;
    String[] invos = new String[]{"", "", "", "", "", "", "", ""};
    String[] dinvi = new String[]{"", "", "", "", "", "", "", ""};
    String[] cnames = new String[]{"", "", "", "", "", "", ""};
    String[] sentn = new String[]{"", "", "", "", "", "", ""};
    int updatec = -1;
    int loadstage = 0;
    int gstage = 0;
    int gstagelaps = 0;
    int gnpls = 8;
    int gwait = 0;
    int gnbts = 0;
    int gcars;
    int gclass = 0;
    int gfix = 0;
    int gnotp = 0;
    int remstage = 0;
    int msload = 0;
    int sgflag = 0;
    String gstagename = "";
    String gplayers = "";
    boolean inwab = false;
    boolean loadwarb = false;
    int warbsel = 0;
    int cancreate = 0;
    int pgamesel = 0;
    int cntchkn = 0;
    Socket socket;
    BufferedReader din;
    PrintWriter dout;
    int spos = 0;
    int spos2 = 0;
    int spos3 = 28;
    int mscro = 125;
    int lspos = 0;
    int mscro2 = 145;
    int lspos2 = 0;
    int mscro3 = 345;
    int lspos3 = 0;
    int clicked = -1;
    int opengame = 0;
    int britchl = 0;
    boolean zeromsw = false;
    int mousonp = -1;
    int cmonp = -1;
    long ptime = 0L;
    int pcurs = 0;
    boolean grprsd = false;
    int pend = 0;
    int mrot = 0;
    boolean pendb = false;
    int[] cac = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
    int[] cax = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] cay = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
    boolean mousedout = false;
    int flks = 0;
    int waitlink = 0;
    boolean pre1 = false;
    boolean pre2 = false;
    int prereq = 0;
    int plsndt = 0;
    int lxm = 0;
    int lym = 0;

    public Lobby(Medium medium, Graphics2D graphics2D, Login login, Globe globe, xtGraphics xtGraphics2, CarDefine carDefine, GameSparker gameSparker) {
        this.m = medium;
        this.rd = graphics2D;
        this.xt = xtGraphics2;
        this.cd = carDefine;
        this.gs = gameSparker;
        this.lg = login;
        this.gb = globe;
        this.gs.cmsg.setBackground(this.color2k(240, 240, 240));
        this.gs.swait.setBackground(this.color2k(220, 220, 220));
        this.gs.snpls.setBackground(this.color2k(220, 220, 220));
        this.gs.snbts.setBackground(this.color2k(220, 220, 220));
        this.gs.sgame.setBackground(this.color2k(200, 200, 200));
        this.gs.wgame.setBackground(this.color2k(200, 200, 200));
        this.gs.pgame.setBackground(this.color2k(200, 200, 200));
        this.gs.vnpls.setBackground(this.color2k(200, 200, 200));
        this.gs.vtyp.setBackground(this.color2k(200, 200, 200));
        this.gs.warb.setBackground(this.color2k(200, 200, 200));
        this.gs.snfmm.setBackground(this.color2k(200, 200, 200));
        this.gs.snfm1.setBackground(this.color2k(200, 200, 200));
        this.gs.snfm2.setBackground(this.color2k(200, 200, 200));
        this.gs.mstgs.setBackground(this.color2k(230, 230, 230));
        this.gs.slaps.setBackground(this.color2k(200, 200, 200));
        this.gs.sclass.setBackground(this.color2k(220, 220, 220));
        this.gs.scars.setBackground(this.color2k(220, 220, 220));
        this.gs.sfix.setBackground(this.color2k(220, 220, 220));
        this.gs.mycar.setBackground(this.color2k(255, 255, 255));
        this.gs.notp.setBackground(this.color2k(255, 255, 255));
        this.gs.rooms.setBackground(this.color2k(170, 170, 170));
        this.gs.swait.setForeground(new Color(0, 0, 0));
        this.gs.snpls.setForeground(new Color(0, 0, 0));
        this.gs.snbts.setForeground(new Color(0, 0, 0));
        this.gs.sgame.setForeground(new Color(0, 0, 0));
        this.gs.wgame.setForeground(new Color(0, 0, 0));
        this.gs.pgame.setForeground(new Color(0, 0, 0));
        this.gs.vnpls.setForeground(new Color(0, 0, 0));
        this.gs.vtyp.setForeground(new Color(0, 0, 0));
        this.gs.warb.setForeground(new Color(0, 0, 0));
        this.gs.snfmm.setForeground(new Color(0, 0, 0));
        this.gs.snfm1.setForeground(new Color(0, 0, 0));
        this.gs.slaps.setForeground(new Color(0, 0, 0));
        this.gs.snfm2.setForeground(new Color(0, 0, 0));
        this.gs.mstgs.setForeground(new Color(0, 0, 0));
        this.gs.sclass.setForeground(new Color(0, 0, 0));
        this.gs.scars.setForeground(new Color(0, 0, 0));
        this.gs.sfix.setForeground(new Color(0, 0, 0));
        this.gs.mycar.setForeground(new Color(0, 0, 0));
        this.gs.notp.setForeground(new Color(0, 0, 0));
        this.gs.rooms.setForeground(new Color(0, 0, 0));
        this.gs.sgame.removeAll();
        this.gs.sgame.add(this.rd, " NFM Multiplayer ");
        this.gs.sgame.add(this.rd, " NFM 2     ");
        this.gs.sgame.add(this.rd, " NFM 1     ");
        this.gs.sgame.add(this.rd, " My Stages ");
        this.gs.sgame.add(this.rd, " My Clan Stages ");
        this.gs.sgame.add(this.rd, " Weekly Top 20 ");
        this.gs.sgame.add(this.rd, " Monthly Top 20 ");
    }

    public void inishlobby() {
        int n;
        this.gs.tnick.hide();
        this.gs.tpass.hide();
        this.gs.temail.hide();
        this.hideinputs();
        this.gs.mycar.setBackground(this.color2k(255, 255, 255));
        this.gs.mycar.setForeground(new Color(0, 0, 0));
        this.gs.rooms.removeAll();
        this.gs.rooms.add(this.rd, "" + this.xt.servername + " :: " + (this.xt.servport - 7070) + "");
        this.gs.rooms.select(0);
        this.gs.requestFocus();
        this.cd.loadready();
        for (n = 0; n < 500; ++n) {
            this.gnum[n] = -2;
            this.gstgn[n] = 0;
            this.gstages[n] = "";
            this.gnlaps[n] = 0;
            this.mnpls[n] = 0;
            this.mnbts[n] = 0;
            this.wait[n] = 0;
            this.gmaker[n] = "";
            this.gcrs[n] = 0;
            this.gclss[n] = 0;
            this.gfx[n] = 0;
            this.gntb[n] = 0;
            this.gplyrs[n] = "";
            this.npls[n] = 0;
            this.gwarb[n] = 0;
            this.gwarbnum[n] = "";
            this.gameturn[n] = 0;
            this.gaclan[n] = "";
            this.gvclan[n] = "";
            this.gascore[n] = 0;
            this.gvscore[n] = 0;
            this.gwtyp[n] = 0;
        }
        for (n = 0; n < 200; ++n) {
            this.pnames[n] = "";
            this.pcars[n] = 0;
            this.pcarnames[n] = "";
            this.pgames[n] = -1;
            this.pclan[n] = "";
            for (int i = 0; i < 6; ++i) {
                this.pcols[n][i] = 0.0f;
            }
        }
        this.ongame = -1;
        this.join = -1;
        this.onjoin = -1;
        this.chalngd = -1;
        this.dispcar = -1;
        this.forcar = -1;
        this.chalby = "";
        this.im = 0;
        this.fstart = false;
        this.updatec = -1;
        this.prevloaded = -1;
        this.spos = 0;
        this.spos2 = 0;
        this.spos3 = 0;
        this.ngm = 0;
        this.npo = 0;
        this.fase = 1;
        this.lloaded = false;
        this.lg.gamec = -1;
        try {
            this.socket = new Socket(this.xt.server, this.xt.servport);
            this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.dout = new PrintWriter(this.socket.getOutputStream(), true);
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.conon = 1;
        this.connector = new Thread(this);
        this.connector.start();
    }

    @Override
    public void run() {
        String string;
        int n;
        int n2;
        int n3;
        int n4;
        int n5 = -1;
        while (this.conon == 1) {
            int n6;
            int n7;
            int n8;
            int n9;
            String string2;
            int n10;
            int n11;
            Date date = new Date();
            long l = date.getTime();
            if (!this.lloaded) {
                this.gs.setCursor(new Cursor(3));
            }
            if (!this.xt.logged && !this.xt.lan && this.xt.nfreeplays - this.xt.ndisco >= 5) {
                if (this.join >= 0) {
                    this.join = -1;
                    this.regnow = true;
                }
                if (this.pgames[this.im] != -1) {
                    this.join = -2;
                    this.regnow = true;
                }
            }
            if (!(this.join < 0 && this.pgames[this.im] == -1 || this.regnow)) {
                n4 = -1;
                for (n11 = 0; n11 < this.ngm; ++n11) {
                    if (this.join != this.gnum[n11] && this.pgames[this.im] != this.gnum[n11]) continue;
                    n4 = n11;
                }
                if (n4 != -1) {
                    n11 = 0;
                    this.ontyp = 0;
                    if (this.gcrs[n4] == 1 && this.pcars[this.im] < 16) {
                        n11 = 1;
                        this.ontyp = 10;
                        if (this.gclss[n4] > 0 && this.gclss[n4] <= 5) {
                            this.ontyp += this.gclss[n4];
                        }
                    }
                    if (this.gcrs[n4] == 2 && this.pcars[this.im] >= 16) {
                        n11 = 1;
                        this.ontyp = 20;
                        if (this.gclss[n4] > 0 && this.gclss[n4] <= 5) {
                            this.ontyp += this.gclss[n4];
                        }
                    }
                    if (this.gcrs[n4] == 1 && this.gwarb[n4] != 0 && this.xt.sc[0] != 36) {
                        n11 = 1;
                        this.ontyp = 30;
                        if (this.gclss[n4] > 0 && this.gclss[n4] <= 5) {
                            this.ontyp += this.gclss[n4];
                        }
                    }
                    if (this.gclss[n4] > 0 && this.gclss[n4] <= 5 && Math.abs(this.cd.cclass[this.pcars[this.im]] - (this.gclss[n4] - 1)) > 1) {
                        n11 = 1;
                        if (this.gcrs[n4] == 1) {
                            this.ontyp = this.gwarb[n4] == 0 ? 10 : 30;
                        }
                        if (this.gcrs[n4] == 2) {
                            this.ontyp = 20;
                        }
                        this.ontyp += this.gclss[n4];
                    }
                    if (this.gclss[n4] <= -2 && this.pcars[this.im] != Math.abs(this.gclss[n4] + 2)) {
                        n11 = 1;
                        this.ontyp = this.gclss[n4];
                    }
                    if (this.gstgn[n4] == -2 && !this.xt.logged) {
                        n11 = 1;
                        this.ontyp = 76;
                    }
                    if (n11 != 0) {
                        this.onjoin = this.gnum[n4];
                        this.jflexo = false;
                        if (this.join >= 0) {
                            this.join = -1;
                        }
                        if (this.pgames[this.im] != -1) {
                            this.join = -2;
                        }
                    }
                }
            }
            if (this.xt.onjoin != -1) {
                this.join = this.xt.onjoin;
                this.ongame = this.xt.onjoin;
                this.xt.onjoin = -1;
            }
            if (this.updatec < -17) {
                this.updatec = -17;
            }
            n4 = 0;
            if (this.lloaded && (n5 = this.pgames[this.im]) != -1) {
                for (n11 = 0; n11 < this.ngm; ++n11) {
                    if (n5 != this.gnum[n11]) continue;
                    this.laps = this.gnlaps[n11];
                    this.stage = this.gstgn[n11];
                    this.stagename = this.gstages[n11];
                    this.nfix = this.gfx[n11];
                    this.notb = this.gntb[n11] == 1;
                }
            }
            String string3 = "" + this.xt.sc[0] + "";
            if (this.xt.sc[0] >= 16) {
                string3 = "C" + this.cd.names[this.xt.sc[0]] + "";
            }
            String string4 = "1|" + this.xt.nickname + ":" + this.xt.nickey + "|" + this.xt.clan + "|" + this.xt.clankey + "|" + string3 + "|" + this.join + "|" + (int)(this.xt.arnp[0] * 100.0f) + "|" + (int)(this.xt.arnp[1] * 100.0f) + "|" + (int)(this.xt.arnp[2] * 100.0f) + "|" + (int)(this.xt.arnp[3] * 100.0f) + "|" + (int)(this.xt.arnp[4] * 100.0f) + "|" + (int)(this.xt.arnp[5] * 100.0f) + "|" + this.ongame + "|";
            if (!this.xt.lan) {
                string4 = string4 + "" + this.updatec + "|";
                if (this.updatec <= -11) {
                    for (n10 = 0; n10 < -this.updatec - 10; ++n10) {
                        string4 = string4 + "" + this.cnames[6 - n10] + "|" + this.sentn[6 - n10] + "|";
                    }
                    this.updatec = -2;
                }
            } else {
                string2 = "Nonex";
                try {
                    string2 = InetAddress.getLocalHost().getHostName();
                    if (string2.indexOf("|") != -1) {
                        string2 = InetAddress.getLocalHost().getHostAddress();
                    }
                }
                catch (Exception exception) {
                    string2 = "Nonex";
                }
                n9 = 0;
                if (this.xt.logged) {
                    n9 = 1;
                }
                string4 = string4 + "" + string2 + "|" + n9 + "|";
            }
            if (this.fstart) {
                string4 = string4 + "3|";
                n4 = 1;
            }
            if (this.chalngd == -5 && !this.fstart) {
                string4 = string4 + "11|" + this.gstage + "|" + this.gstagename + "|" + this.gstagelaps + "|" + this.gnpls + "|" + this.gwait + "|" + this.pnames[this.im] + "|" + this.gcars + "|" + this.gclass + "|" + this.gfix + "|" + this.gnotp + "|" + this.gplayers + "|";
                if (this.xt.lan) {
                    string4 = string4 + "" + this.gnbts + "|";
                }
            }
            if (this.ongame != -1 && this.chalngd != -5 && !this.fstart) {
                n10 = 0;
                for (n9 = 0; n9 < 7; ++n9) {
                    if (this.invos[n9].equals("") || this.dinvi[n9].equals(this.invos[n9])) continue;
                    if (n10 == 0) {
                        string4 = string4 + "2|" + this.ongame + "|";
                        n10 = 1;
                    }
                    string4 = string4 + "" + this.invos[n9] + "|";
                    this.dinvi[n9] = this.invos[n9];
                }
            }
            string2 = "";
            n9 = 0;
            try {
                this.dout.println(string4);
                string2 = this.din.readLine();
                if (string2 == null) {
                    n9 = 1;
                }
            }
            catch (Exception exception) {
                n9 = 1;
            }
            if (n9 != 0) {
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
                    this.socket = new Socket(this.xt.server, this.xt.servport);
                    this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                    this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                    this.dout.println(string4);
                    string2 = this.din.readLine();
                    if (string2 != null) {
                        n9 = 0;
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (n9 != 0) {
                try {
                    this.socket.close();
                    this.socket = null;
                }
                catch (Exception exception) {
                    // empty catch block
                }
                this.conon = 0;
                this.lg.exitfromlobby();
                this.hideinputs();
                this.connector.stop();
            }
            if (this.regnow && this.join == -2) {
                this.join = -1;
            }
            this.npo = this.getvalue(string2, 0);
            if (this.npo < 0) {
                this.npo = 0;
            }
            this.im = this.getvalue(string2, 1);
            if (this.im < 0) {
                this.im = 0;
            }
            for (n3 = 0; n3 < this.npo; ++n3) {
                String string5;
                this.pnames[n3] = this.getSvalue(string2, 2 + n3 * 10);
                if (this.pnames[n3].equals("")) {
                    this.pnames[n3] = "Unknown";
                }
                if ((string5 = this.getSvalue(string2, 3 + n3 * 10)).startsWith("C")) {
                    this.pcarnames[n3] = string5.substring(1);
                    if (!this.pcarnames[n3].equals("")) {
                        n2 = 0;
                        for (n8 = 16; n8 < 56; ++n8) {
                            if (!this.pcarnames[n3].equals(this.cd.names[n8])) continue;
                            n2 = n8;
                            break;
                        }
                        if (n2 == 0) {
                            this.pcars[n3] = -1;
                            n8 = 0;
                            for (n7 = 0; n7 < this.cd.nl; ++n7) {
                                if (!this.pcarnames[n3].equals(this.cd.loadnames[n7])) continue;
                                n8 = 1;
                            }
                            if (n8 == 0 && this.cd.nl < 20 && this.cd.nl >= 0) {
                                this.cd.loadnames[this.cd.nl] = this.pcarnames[n3];
                                ++this.cd.nl;
                            }
                            this.cd.sparkcarloader();
                        } else {
                            this.pcars[n3] = n2;
                        }
                    } else {
                        this.pcars[n3] = 0;
                        this.pcarnames[n3] = this.cd.names[this.pcars[n3]];
                    }
                } else {
                    this.pcars[n3] = this.getvalue(string2, 3 + n3 * 10);
                    if (this.pcars[n3] == -1) {
                        this.pcars[n3] = 0;
                    }
                    this.pcarnames[n3] = this.cd.names[this.pcars[n3]];
                }
                this.pclan[n3] = this.getSvalue(string2, 4 + n3 * 10);
                this.pgames[n3] = this.getvalue(string2, 5 + n3 * 10);
                this.pcols[n3][0] = (float)this.getvalue(string2, 6 + n3 * 10) / 100.0f;
                if (this.pcols[n3][0] == -1.0f) {
                    this.pcols[n3][0] = 0.0f;
                }
                this.pcols[n3][1] = (float)this.getvalue(string2, 7 + n3 * 10) / 100.0f;
                if (this.pcols[n3][1] == -1.0f) {
                    this.pcols[n3][1] = 0.0f;
                }
                this.pcols[n3][2] = (float)this.getvalue(string2, 8 + n3 * 10) / 100.0f;
                if (this.pcols[n3][2] == -1.0f) {
                    this.pcols[n3][2] = 0.0f;
                }
                this.pcols[n3][3] = (float)this.getvalue(string2, 9 + n3 * 10) / 100.0f;
                if (this.pcols[n3][3] == -1.0f) {
                    this.pcols[n3][3] = 0.0f;
                }
                this.pcols[n3][4] = (float)this.getvalue(string2, 10 + n3 * 10) / 100.0f;
                if (this.pcols[n3][4] == -1.0f) {
                    this.pcols[n3][4] = 0.0f;
                }
                this.pcols[n3][5] = (float)this.getvalue(string2, 11 + n3 * 10) / 100.0f;
                if (this.pcols[n3][5] != -1.0f) continue;
                this.pcols[n3][5] = 0.0f;
            }
            n3 = 12 + (this.npo - 1) * 10;
            this.ngm = this.getvalue(string2, n3);
            if (this.ngm < 0) {
                this.ngm = 0;
            }
            n = 12;
            if (this.xt.lan) {
                n = 13;
            }
            for (n2 = 0; n2 < this.ngm; ++n2) {
                this.gnum[n2] = this.getvalue(string2, n3 + 1 + n2 * n);
                this.gstgn[n2] = this.getvalue(string2, n3 + 2 + n2 * n);
                this.gstgn[n2] = Math.abs(this.gstgn[n2]);
                if (this.gstgn[n2] > 100) {
                    this.gstgn[n2] = -2;
                }
                this.gstages[n2] = this.getSvalue(string2, n3 + 3 + n2 * n);
                this.gnlaps[n2] = this.getvalue(string2, n3 + 4 + n2 * n);
                this.mnpls[n2] = this.getvalue(string2, n3 + 5 + n2 * n);
                this.wait[n2] = this.getvalue(string2, n3 + 6 + n2 * n);
                this.gmaker[n2] = this.getSvalue(string2, n3 + 7 + n2 * n);
                this.gcrs[n2] = this.getvalue(string2, n3 + 8 + n2 * n);
                this.gclss[n2] = this.getvalue(string2, n3 + 9 + n2 * n);
                this.gfx[n2] = this.getvalue(string2, n3 + 10 + n2 * n);
                this.gntb[n2] = this.getvalue(string2, n3 + 11 + n2 * n);
                this.gplyrs[n2] = this.getSvalue(string2, n3 + 12 + n2 * n);
                if (this.gplyrs[n2].startsWith("#warb#")) {
                    this.gwarb[n2] = this.getHvalue(this.gplyrs[n2], 2);
                    this.gwarbnum[n2] = this.getHSvalue(this.gplyrs[n2], 3);
                    this.gameturn[n2] = this.getHvalue(this.gplyrs[n2], 4);
                    this.gaclan[n2] = this.getHSvalue(this.gplyrs[n2], 5);
                    this.gvclan[n2] = this.getHSvalue(this.gplyrs[n2], 6);
                    this.gascore[n2] = this.getHvalue(this.gplyrs[n2], 7);
                    this.gvscore[n2] = this.getHvalue(this.gplyrs[n2], 8);
                    this.gwtyp[n2] = this.getHvalue(this.gplyrs[n2], 9);
                    if (this.gwtyp[n2] < 1 || this.gwtyp[n2] > 5) {
                        this.gwtyp[n2] = 1;
                    }
                    this.gplyrs[n2] = "";
                } else {
                    this.gwarb[n2] = 0;
                    this.gwarbnum[n2] = "";
                    this.gameturn[n2] = 0;
                    this.gaclan[n2] = "";
                    this.gvclan[n2] = "";
                    this.gascore[n2] = 0;
                    this.gvscore[n2] = 0;
                    this.gwtyp[n2] = 0;
                }
                if (this.xt.lan) {
                    this.mnbts[n2] = this.getvalue(string2, n3 + 13 + n2 * n);
                }
                if (this.xt.playingame > -1 && this.xt.playingame == this.gnum[n2] && !this.xt.lan) {
                    this.ongame = this.gnum[n2];
                }
                if (n5 != this.gnum[n2] || this.wait[n2] != 0 || !this.lloaded || n5 == -1) continue;
                for (n8 = 0; n8 < this.npo; ++n8) {
                    if (this.pgames[n8] != this.gnum[n2] || !this.pnames[n8].equals(this.xt.nickname)) continue;
                    this.im = n8;
                    break;
                }
                this.conon = 2;
                this.gs.setCursor(new Cursor(3));
            }
            for (n2 = 0; n2 < this.ngm; ++n2) {
                this.npls[n2] = 0;
                for (n8 = 0; n8 < this.npo; ++n8) {
                    if (this.pgames[n8] != this.gnum[n2]) continue;
                    int n12 = n2;
                    this.npls[n12] = this.npls[n12] + 1;
                }
            }
            if (this.conon != 0 && this.xt.playingame != -1) {
                this.xt.playingame = -1;
            }
            if (this.ongame != -1) {
                n2 = 0;
                for (n8 = 0; n8 < this.ngm; ++n8) {
                    if (this.ongame != this.gnum[n8]) continue;
                    n2 = 1;
                }
                if (n2 == 0) {
                    this.britchl = -1;
                }
            }
            if (this.join > -1) {
                n2 = 0;
                for (n8 = 0; n8 < this.ngm; ++n8) {
                    if (this.join != this.gnum[n8]) continue;
                    n2 = 1;
                }
                if (n2 == 0) {
                    this.join = -1;
                }
            }
            for (n2 = 0; n2 < this.npo; ++n2) {
                if (this.pgames[n2] == -1) continue;
                n8 = 0;
                for (n7 = 0; n7 < this.ngm; ++n7) {
                    if (this.pgames[n2] != this.gnum[n7]) continue;
                    n8 = 1;
                }
                if (n8 != 0) continue;
                this.pgames[n2] = -1;
            }
            n3 = this.xt.lan ? (n3 += 14 + (this.ngm - 1) * 13) : (n3 += 13 + (this.ngm - 1) * 12);
            if (!this.xt.lan) {
                n2 = this.getvalue(string2, n3);
                n8 = this.getvalue(string2, n3 + 1);
                n3 += 2;
                if (this.updatec != n2 && this.updatec >= -2 && n8 == this.ongame) {
                    for (n7 = 0; n7 < 7; ++n7) {
                        this.cnames[n7] = this.getSvalue(string2, n3 + n7 * 2);
                        this.sentn[n7] = this.getSvalue(string2, n3 + 1 + n7 * 2);
                    }
                    this.updatec = n2;
                    if (this.ongame == -1) {
                        this.spos3 = 28;
                    }
                    n3 += 14;
                }
                if (this.ongame != -1) {
                    if (this.prevloaded != -1) {
                        this.prevloaded = -1;
                    }
                    n7 = 0;
                    for (n6 = 0; n6 < this.ngm; ++n6) {
                        if (this.ongame != this.gnum[n6] || this.wait[n6] > 0) continue;
                        n7 = 1;
                    }
                    if (n7 != 0) {
                        this.prevloaded = this.getvalue(string2, n3);
                        ++n3;
                        if (this.prevloaded == 1) {
                            this.prnpo = this.getvalue(string2, n3);
                            ++n3;
                            for (n6 = 0; n6 < this.prnpo; ++n6) {
                                this.prnames[n6] = this.getSvalue(string2, n3);
                                ++n3;
                            }
                            for (n6 = 0; n6 < this.prnpo; ++n6) {
                                this.ppos[n6] = this.getvalue(string2, n3);
                                ++n3;
                            }
                            for (n6 = 0; n6 < this.prnpo; ++n6) {
                                this.plap[n6] = this.getvalue(string2, n3);
                                ++n3;
                            }
                            for (n6 = 0; n6 < this.prnpo; ++n6) {
                                this.ppow[n6] = (int)((float)this.getvalue(string2, n3) / 9800.0f * 55.0f);
                                ++n3;
                            }
                            for (n6 = 0; n6 < this.prnpo; ++n6) {
                                int n13 = this.getvalue(string2, n3);
                                this.pdam[n6] = n13 != -17 ? (int)((float)n13 / 100.0f * 55.0f) : -17;
                                ++n3;
                            }
                            this.stuntname = this.getSvalue(string2, n3);
                            this.lapsname = this.getSvalue(string2, ++n3);
                            this.wastename = this.getSvalue(string2, ++n3);
                            ++n3;
                        }
                    }
                }
            } else {
                n2 = this.getvalue(string2, n3);
                this.lanlogged = n2 == 1;
                ++n3;
            }
            n2 = this.getvalue(string2, n3);
            if (n2 != -1) {
                n8 = 0;
                for (n7 = 0; n7 < this.ngm; ++n7) {
                    if (n2 != this.gnum[n7]) continue;
                    n8 = n7;
                }
                n7 = 0;
                if (this.gwarb[n8] != 0) {
                    if (this.xt.clan.toLowerCase().equals(this.gaclan[n8].toLowerCase()) || this.xt.clan.toLowerCase().equals(this.gvclan[n8].toLowerCase())) {
                        n7 = 1;
                    }
                } else {
                    n7 = 1;
                }
                if ((this.pgames[this.im] != this.ongame || this.ongame == -1) && n2 != this.ongame && this.chalngd == -1 && this.join == -1 && this.fase == 1 && this.wait[n8] > 0 && n7 != 0) {
                    this.chalngd = n2;
                    this.chalby = this.getSvalue(string2, n3 + 1);
                    this.cflk = 20;
                    this.ctime = 20;
                    this.ptime = 0L;
                    this.longame = this.ongame;
                    if (this.gs.rooms.open) {
                        this.gs.rooms.open = false;
                    }
                    if (this.ongame != -1) {
                        this.britchl = -1;
                    }
                }
                ++n3;
            }
            if (!this.xt.lan) {
                n8 = 1;
                for (n7 = 1; n7 < 6; ++n7) {
                    if (n7 == this.xt.servport - 7070 || (n6 = this.getvalue(string2, n3 + n7)) == -1) continue;
                    this.gs.rooms.sopts[n8] = "Room " + n7 + "  ::  " + n6 + "";
                    this.gs.rooms.opts[n8] = "";
                    this.gs.rooms.iroom[n8] = n7;
                    ++n8;
                }
                for (n7 = 0; n7 < this.lg.nservers; ++n7) {
                    if (this.xt.server.equals(this.lg.servers[n7]) || this.xt.delays[n7] >= 300) continue;
                    this.gs.rooms.sopts[n8] = ":: " + this.lg.snames[n7] + "";
                    this.gs.rooms.opts[n8] = "";
                    this.gs.rooms.iroom[n8] = 1000 + n7;
                    ++n8;
                }
                this.gs.rooms.no = n8;
            }
            if (this.join > -1) {
                n8 = 0;
                for (n7 = 0; n7 < this.ngm; ++n7) {
                    if (this.join != this.gnum[n7] || this.wait[n7] != 0) continue;
                    n8 = 1;
                }
                if (this.pgames[this.im] == this.join || n8 != 0) {
                    this.join = -1;
                    this.nflk = 3;
                }
            }
            if (this.join == -2) {
                n8 = 0;
                for (n7 = 0; n7 < this.ngm; ++n7) {
                    if (this.pgames[this.im] != this.gnum[n7] || this.wait[n7] != 0) continue;
                    n8 = 1;
                }
                if (this.pgames[this.im] == -1 || n8 != 0) {
                    this.join = -1;
                    if (n8 == 0) {
                        this.ongame = -1;
                    }
                }
            }
            if (this.chalngd == -5 && this.pgames[this.im] != -1) {
                this.ongame = this.pgames[this.im];
                this.chalngd = -1;
                if (!this.xt.lan && this.gplayers.equals("")) {
                    this.lg.gamealert();
                }
            }
            if (this.fstart && n4 != 0) {
                this.fstart = false;
            }
            this.rerr = 0;
            if (!this.lloaded) {
                this.gs.setCursor(new Cursor(0));
                this.lloaded = true;
            }
            if (!this.gb.domon) {
                this.gb.roomlogos(this.pnames, this.npo);
                if (this.ongame == -1) {
                    if (this.cntchkn == 5) {
                        this.lg.checkgamealerts();
                    }
                } else if (this.lg.gamec != -1) {
                    this.lg.gamec = -1;
                }
                if (this.cntchkn == 5) {
                    this.lg.checknotifcations();
                    this.cntchkn = 0;
                } else {
                    ++this.cntchkn;
                }
            } else if (this.lg.gamec != -1) {
                this.lg.gamec = -1;
            }
            date = new Date();
            long l2 = date.getTime();
            n6 = 700 - (int)(l2 - l);
            if (n6 < 50) {
                n6 = 50;
            }
            try {
                Thread.sleep(n6);
            }
            catch (InterruptedException interruptedException) {}
        }
        if (this.conon == 2) {
            int n14 = 20;
            this.xt.playingame = -1;
            while (n14 != 0) {
                String string6 = "2|" + n5 + "|";
                string = "";
                n4 = 0;
                try {
                    this.dout.println(string6);
                    string = this.din.readLine();
                    if (string == null) {
                        n4 = 1;
                    }
                }
                catch (Exception exception) {
                    n4 = 1;
                }
                if (n4 != 0) {
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
                        this.socket = new Socket(this.xt.server, this.xt.servport);
                        this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                        this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                        this.dout.println(string6);
                        string = this.din.readLine();
                        if (string != null) {
                            n4 = 0;
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (n4 != 0) {
                    try {
                        this.socket.close();
                        this.socket = null;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    this.conon = 0;
                    this.lg.exitfromlobby();
                    this.hideinputs();
                    this.connector.stop();
                }
                if (!this.xt.lan) {
                    this.xt.gameport = this.getvalue(string, 0);
                } else {
                    this.xt.gameport = -1;
                    this.xt.localserver = this.getSevervalue(string, 0);
                    if (!this.xt.localserver.equals("")) {
                        this.xt.gameport = 0;
                    }
                }
                if (this.xt.gameport != -1) {
                    int n15;
                    int n16;
                    int n17 = 0;
                    this.xt.im = -1;
                    this.xt.nplayers = this.getvalue(string, 1);
                    if (this.xt.nplayers < 1) {
                        this.xt.nplayers = 1;
                    }
                    if (this.xt.nplayers > 8) {
                        this.xt.nplayers = 8;
                    }
                    for (n16 = 0; n16 < this.xt.nplayers; ++n16) {
                        this.xt.plnames[n16] = this.getSvalue(string, 2 + n16);
                        if (!this.xt.nickname.equals(this.xt.plnames[n16])) continue;
                        this.xt.im = n16;
                    }
                    n16 = 2 + this.xt.nplayers;
                    for (n15 = 0; n15 < this.xt.nplayers; ++n15) {
                        String string7 = this.getSvalue(string, n16 + n15);
                        if (string7.startsWith("C")) {
                            if (!(string7 = string7.substring(1)).equals("")) {
                                n3 = 0;
                                for (n = 16; n < 56; ++n) {
                                    if (!string7.equals(this.cd.names[n])) continue;
                                    n3 = n;
                                    break;
                                }
                                while (n3 == 0 && n17 < 100) {
                                    n = 0;
                                    for (n2 = 0; n2 < this.cd.nl; ++n2) {
                                        if (!string7.equals(this.cd.loadnames[n2])) continue;
                                        n = 1;
                                    }
                                    if (n == 0 && this.cd.nl < 20) {
                                        this.cd.loadnames[this.cd.nl] = string7;
                                        ++this.cd.nl;
                                    }
                                    this.cd.sparkcarloader();
                                    try {
                                        Thread.sleep(100L);
                                    }
                                    catch (InterruptedException interruptedException) {
                                        // empty catch block
                                    }
                                    for (n2 = 16; n2 < 56; ++n2) {
                                        if (!string7.equals(this.cd.names[n2])) continue;
                                        n3 = n2;
                                    }
                                    ++n17;
                                }
                                if (n3 != 0) {
                                    this.xt.sc[n15] = n3;
                                    for (n = 0; n < this.npo; ++n) {
                                        if (!this.pcarnames[n].equals(string7)) continue;
                                        this.pcars[n] = n3;
                                    }
                                    continue;
                                }
                                this.xt.im = -1;
                                continue;
                            }
                            this.xt.im = -1;
                            continue;
                        }
                        this.xt.sc[n15] = this.getvalue(string, n16 + n15);
                        if (this.xt.sc[n15] != -1) continue;
                        this.xt.im = -1;
                    }
                    n16 += this.xt.nplayers;
                    for (n15 = 0; n15 < this.xt.nplayers; ++n15) {
                        this.xt.xstart[n15] = this.getvalue(string, n16 + n15);
                    }
                    n16 += this.xt.nplayers;
                    for (n15 = 0; n15 < this.xt.nplayers; ++n15) {
                        this.xt.zstart[n15] = this.getvalue(string, n16 + n15);
                    }
                    n16 += this.xt.nplayers;
                    for (n15 = 0; n15 < this.xt.nplayers; ++n15) {
                        for (int i = 0; i < 6; ++i) {
                            this.xt.allrnp[n15][i] = (float)this.getvalue(string, n16 + n15 * 6 + i) / 100.0f;
                        }
                    }
                    if (this.xt.im != -1) {
                        int n18;
                        this.xt.playingame = n5;
                        n15 = 0;
                        for (n18 = 0; n18 < this.ngm; ++n18) {
                            if (n5 != this.gnum[n18]) continue;
                            n15 = n18;
                        }
                        if (this.gwarb[n15] != 0) {
                            this.xt.clangame = this.gwtyp[n15];
                            this.xt.clanchat = true;
                            this.xt.gaclan = this.gaclan[n15];
                            for (n18 = 0; n18 < this.xt.nplayers; ++n18) {
                                for (n3 = 0; n3 < this.npo; ++n3) {
                                    if (!this.xt.plnames[n18].equals(this.pnames[n3]) || this.pgames[n3] != n5) continue;
                                    this.xt.pclan[n18] = this.pclan[n3];
                                }
                            }
                        } else {
                            this.xt.clangame = 0;
                        }
                    } else {
                        this.xt.playingame = -1;
                        this.xt.im = 0;
                    }
                    n14 = 0;
                } else {
                    --n14;
                }
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {}
            }
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
            if (this.xt.playingame != -1) {
                if (!this.xt.lan && !this.xt.logged) {
                    ++this.xt.nfreeplays;
                    try {
                        this.socket = new Socket(this.lg.servers[0], 7061);
                        this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                        this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                        this.dout.println("7|" + this.xt.nfreeplays + "|");
                        String string8 = this.din.readLine();
                        this.xt.hours = this.getvalue(string8, 0);
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
                this.hideinputs();
                this.xt.multion = 1;
                this.fase = 76;
                System.gc();
            } else {
                this.inishlobby();
            }
        }
        if (this.conon == 3) {
            int n19 = 20;
            this.xt.playingame = -1;
            while (n19 != 0) {
                String string9 = "4|" + this.ongame + "|";
                string = "";
                n4 = 0;
                try {
                    this.dout.println(string9);
                    string = this.din.readLine();
                    if (string == null) {
                        n4 = 1;
                    }
                }
                catch (Exception exception) {
                    n4 = 1;
                }
                if (n4 != 0) {
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
                        this.socket = new Socket(this.xt.server, this.xt.servport);
                        this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                        this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                        this.dout.println(string9);
                        string = this.din.readLine();
                        if (string != null) {
                            n4 = 0;
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (n4 != 0) {
                    try {
                        this.socket.close();
                        this.socket = null;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    this.conon = 0;
                    this.lg.exitfromlobby();
                    this.hideinputs();
                    this.connector.stop();
                }
                if (!this.xt.lan) {
                    this.xt.gameport = this.getvalue(string, 0);
                } else {
                    this.xt.gameport = -1;
                    this.xt.localserver = this.getSevervalue(string, 0);
                    if (!this.xt.localserver.equals("")) {
                        this.xt.gameport = 0;
                    }
                }
                if (this.xt.gameport != -1) {
                    int n20;
                    int n21;
                    int n22 = 0;
                    this.xt.nplayers = this.getvalue(string, 1);
                    if (this.xt.nplayers < 1) {
                        this.xt.nplayers = 1;
                    }
                    if (this.xt.nplayers > 8) {
                        this.xt.nplayers = 8;
                    }
                    this.xt.im = this.getvalue(string, 2) + this.xt.nplayers;
                    for (n21 = 0; n21 < this.xt.nplayers; ++n21) {
                        this.xt.plnames[n21] = this.getSvalue(string, 3 + n21);
                    }
                    n21 = 3 + this.xt.nplayers;
                    for (n20 = 0; n20 < this.xt.nplayers; ++n20) {
                        String string10 = this.getSvalue(string, n21 + n20);
                        if (string10.startsWith("C")) {
                            if (!(string10 = string10.substring(1)).equals("")) {
                                n3 = 0;
                                for (n = 16; n < 56; ++n) {
                                    if (!string10.equals(this.cd.names[n])) continue;
                                    n3 = n;
                                    break;
                                }
                                while (n3 == 0 && n22 < 100) {
                                    n = 0;
                                    for (n2 = 0; n2 < this.cd.nl; ++n2) {
                                        if (!string10.equals(this.cd.loadnames[n2])) continue;
                                        n = 1;
                                    }
                                    if (n == 0 && this.cd.nl < 20) {
                                        this.cd.loadnames[this.cd.nl] = string10;
                                        ++this.cd.nl;
                                    }
                                    this.cd.sparkcarloader();
                                    try {
                                        Thread.sleep(100L);
                                    }
                                    catch (InterruptedException interruptedException) {
                                        // empty catch block
                                    }
                                    for (n2 = 16; n2 < 56; ++n2) {
                                        if (!string10.equals(this.cd.names[n2])) continue;
                                        n3 = n2;
                                    }
                                    ++n22;
                                }
                                if (n3 != 0) {
                                    this.xt.sc[n20] = n3;
                                    for (n = 0; n < this.npo; ++n) {
                                        if (!this.pcarnames[n].equals(string10)) continue;
                                        this.pcars[n] = n3;
                                    }
                                    continue;
                                }
                                this.xt.im = -1;
                                continue;
                            }
                            this.xt.im = -1;
                            continue;
                        }
                        this.xt.sc[n20] = this.getvalue(string, n21 + n20);
                        if (this.xt.sc[n20] != -1) continue;
                        this.xt.im = -1;
                    }
                    n21 += this.xt.nplayers;
                    for (n20 = 0; n20 < this.xt.nplayers; ++n20) {
                        this.xt.xstart[n20] = this.getvalue(string, n21 + n20);
                    }
                    n21 += this.xt.nplayers;
                    for (n20 = 0; n20 < this.xt.nplayers; ++n20) {
                        this.xt.zstart[n20] = this.getvalue(string, n21 + n20);
                    }
                    n21 += this.xt.nplayers;
                    for (n20 = 0; n20 < this.xt.nplayers; ++n20) {
                        for (int i = 0; i < 6; ++i) {
                            this.xt.allrnp[n20][i] = (float)this.getvalue(string, n21 + n20 * 6 + i) / 100.0f;
                        }
                    }
                    if (this.xt.im >= this.xt.nplayers && this.xt.im < this.xt.nplayers + 3) {
                        int n23;
                        this.xt.playingame = this.ongame;
                        n20 = 0;
                        for (n23 = 0; n23 < this.ngm; ++n23) {
                            if (this.ongame != this.gnum[n23]) continue;
                            n20 = n23;
                        }
                        if (this.gwarb[n20] != 0) {
                            this.xt.clangame = this.gwtyp[n20];
                            this.xt.gaclan = this.gaclan[n20];
                            if (this.xt.clan.toLowerCase().equals(this.gaclan[n20].toLowerCase()) || this.xt.clan.toLowerCase().equals(this.gvclan[n20].toLowerCase())) {
                                this.xt.clanchat = true;
                            }
                            for (n23 = 0; n23 < this.xt.nplayers; ++n23) {
                                for (n3 = 0; n3 < this.npo; ++n3) {
                                    if (!this.xt.plnames[n23].equals(this.pnames[n3]) || this.pgames[n3] != this.ongame) continue;
                                    this.xt.pclan[n23] = this.pclan[n3];
                                }
                            }
                        } else {
                            this.xt.clangame = 0;
                        }
                    } else {
                        this.xt.playingame = -1;
                        this.xt.im = 0;
                    }
                    n19 = 0;
                } else {
                    --n19;
                }
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {}
            }
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
            if (this.xt.playingame != -1) {
                this.hideinputs();
                this.xt.multion = 3;
                this.fase = 76;
                System.gc();
            } else {
                this.inishlobby();
            }
        }
    }

    public void stopallnow() {
        this.conon = 0;
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
        if (this.connector != null) {
            this.connector.stop();
            this.connector = null;
        }
    }

    /*
     * Opcode count of 22597 triggered aggressive code reduction.  Override with --aggressivesizethreshold.
     */
    public void lobby(int n, int n2, boolean bl, int n3, CheckPoints checkPoints, Control control, ContO[] contOArray) {
        block951: {
            this.pre1 = false;
            this.pre2 = false;
            int n4 = 0;
            if (this.xt.warning == 0 || this.xt.warning == 210) {
                if (!this.regnow) {
                    if (this.onjoin == -1) {
                        int n5;
                        int n6;
                        int n7;
                        int n8;
                        int n9;
                        int n10;
                        int n11;
                        this.xt.mainbg(3);
                        if (this.britchl == -1) {
                            this.ongame = -1;
                            this.britchl = 0;
                        }
                        this.btn = 0;
                        this.pbtn = 0;
                        this.zeromsw = false;
                        int n12 = this.npo;
                        if (this.invo) {
                            n12 = 0;
                            for (n11 = 0; n11 < this.npo; ++n11) {
                                if (this.pgames[n11] != -1) continue;
                                ++n12;
                            }
                            n12 += 2;
                        }
                        n11 = (n12 - 11) * 30;
                        if (n11 < 0) {
                            n11 = 0;
                        }
                        int n13 = (int)((float)this.spos / 295.0f * (float)n11);
                        int n14 = 0;
                        int n15 = -1;
                        int n16 = -1;
                        if (this.conon == 1) {
                            if (!this.invo) {
                                for (n10 = 0; n10 < this.npo; ++n10) {
                                    if (this.pgames[n10] == -1) continue;
                                    n9 = 0;
                                    for (n8 = 0; n8 < this.ngm; ++n8) {
                                        if (this.pgames[n10] != this.gnum[n8]) continue;
                                        n9 = n8;
                                    }
                                    if (this.wait[n9] <= 0) continue;
                                    if (82 + 30 * n14 - n13 > 50 && 82 + 30 * (n14 - 1) - n13 < 415) {
                                        n8 = 0;
                                        if (n > 70 && n < 185 && n2 > 52 + 30 * n14 - n13 && n2 < 82 + 30 * n14 - n13) {
                                            if (this.pgames[this.im] == -1 && this.join == -1 && this.chalngd >= -1) {
                                                if (bl || this.mousonp == n10) {
                                                    this.rd.setColor(this.color2k(255, 255, 255));
                                                    this.mousonp = n10;
                                                    n16 = 52 + 30 * n14 - n13;
                                                    if (bl) {
                                                        if (this.cmonp == n10) {
                                                            this.ongame = this.pgames[n10];
                                                        }
                                                        this.chalngd = -1;
                                                    } else {
                                                        if (this.cmonp == -1) {
                                                            this.ongame = -1;
                                                            this.cmonp = n10;
                                                        }
                                                        if (this.ongame == this.pgames[n10]) {
                                                            this.mousonp = -1;
                                                        }
                                                    }
                                                } else {
                                                    this.rd.setColor(this.color2k(220, 220, 220));
                                                }
                                                this.rd.fillRect(70, 53 + 30 * n14 - n13, 116, 29);
                                                n15 = n10;
                                            }
                                            n8 = 1;
                                            if (control.handb) {
                                                this.gs.cmsg.setText(this.gs.cmsg.getText() + "" + this.pnames[n10]);
                                                control.handb = false;
                                            }
                                        }
                                        if (this.pgames[this.im] == -1 && this.join == -1 && this.chalngd >= -1) {
                                            this.rd.setColor(new Color(49, 79, 0));
                                        } else {
                                            this.rd.setColor(new Color(34, 55, 0));
                                        }
                                        n7 = this.gb.drawl(this.rd, this.pnames[n10], 68, 53 + 30 * n14 - n13, n8 != 0);
                                        if (n8 == 0 || n7 == 0) {
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString(this.pnames[n10], 127 - this.ftm.stringWidth(this.pnames[n10]) / 2, 66 + 30 * n14 - n13);
                                            this.rd.setFont(new Font("Arial", 0, 10));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString(this.pcarnames[n10], 127 - this.ftm.stringWidth(this.pcarnames[n10]) / 2, 78 + 30 * n14 - n13);
                                        }
                                        this.rd.setColor(this.color2k(150, 150, 150));
                                        this.rd.drawLine(70, 82 + 30 * n14 - n13, 185, 82 + 30 * n14 - n13);
                                    }
                                    ++n14;
                                }
                            }
                            n10 = -1;
                            if (this.invo) {
                                for (n9 = 0; n9 < this.ngm; ++n9) {
                                    if (this.gwarb[n9] == 0 || this.pgames[this.im] != this.gnum[n9]) continue;
                                    n10 = n9;
                                }
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                if (n10 == -1) {
                                    this.rd.drawString("Free Players", 127 - this.ftm.stringWidth("Free Players") / 2, 75 - n13);
                                } else {
                                    this.rd.drawString("Members of Clans", 127 - this.ftm.stringWidth("Members of Clans") / 2, 75 - n13);
                                }
                                this.rd.setFont(new Font("Arial", 0, 10));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("Click a player to invite:", 127 - this.ftm.stringWidth("Click a player to invite:") / 2, 92 - n13);
                                this.rd.setColor(this.color2k(150, 150, 150));
                                this.rd.drawLine(70, 112 - n13, 185, 112 - n13);
                                n14 += 2;
                            }
                            for (n9 = 0; n9 < this.npo; ++n9) {
                                n8 = 0;
                                if (this.invo) {
                                    if (this.im == n9) {
                                        n8 = 1;
                                    }
                                    for (n7 = 0; n7 < 7; ++n7) {
                                        if (!this.invos[n7].equals(this.pnames[n9]) || n8 != 0) continue;
                                        n8 = 1;
                                    }
                                    if (n10 != -1 && !this.pclan[n9].toLowerCase().equals(this.gaclan[n10].toLowerCase()) && !this.pclan[n9].toLowerCase().equals(this.gvclan[n10].toLowerCase())) {
                                        n8 = 1;
                                    }
                                }
                                if (this.pgames[n9] != -1 || n8 != 0) continue;
                                if (82 + 30 * n14 - n13 > 50 && 82 + 30 * (n14 - 1) - n13 < 415) {
                                    n7 = 0;
                                    if (n > 70 && n < 185 && n2 > 52 + 30 * n14 - n13 && n2 < 82 + 30 * n14 - n13) {
                                        if (this.invo) {
                                            if (bl) {
                                                this.rd.setColor(this.color2k(255, 255, 255));
                                                this.mousonp = n9;
                                            } else {
                                                this.rd.setColor(this.color2k(220, 220, 220));
                                                if (this.mousonp == n9) {
                                                    n6 = 0;
                                                    for (n5 = 0; n5 < 7 && n6 == 0; ++n5) {
                                                        if (!this.invos[n5].equals("")) continue;
                                                        this.invos[n5] = this.pnames[n9];
                                                        n6 = 1;
                                                    }
                                                    this.mousonp = -1;
                                                    this.invo = false;
                                                }
                                            }
                                            this.rd.fillRect(70, 53 + 30 * n14 - n13, 116, 29);
                                            n15 = n9;
                                        } else if (this.pgames[this.im] == -1 && this.join == -1 && this.chalngd >= -1) {
                                            n4 = 12;
                                            if (bl) {
                                                if (!this.gb.proname.equals(this.pnames[n9])) {
                                                    this.gb.proname = this.pnames[n9];
                                                    this.gb.loadedp = false;
                                                }
                                                this.gb.tab = 1;
                                                this.gb.open = 2;
                                                this.gb.upo = true;
                                            }
                                        }
                                        n7 = 1;
                                        if (control.handb) {
                                            this.gs.cmsg.setText(this.gs.cmsg.getText() + "" + this.pnames[n9]);
                                            control.handb = false;
                                        }
                                    }
                                    if (this.invo) {
                                        this.rd.setColor(new Color(62, 98, 0));
                                    } else {
                                        this.rd.setColor(new Color(0, 0, 0));
                                    }
                                    n5 = this.gb.drawl(this.rd, this.pnames[n9], 68, 53 + 30 * n14 - n13, n7 != 0) ? 1 : 0;
                                    if (n7 == 0 || n5 == 0) {
                                        this.rd.setFont(new Font("Arial", 1, 12));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.drawString(this.pnames[n9], 127 - this.ftm.stringWidth(this.pnames[n9]) / 2, 66 + 30 * n14 - n13);
                                        this.rd.setFont(new Font("Arial", 0, 10));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.drawString(this.pcarnames[n9], 127 - this.ftm.stringWidth(this.pcarnames[n9]) / 2, 78 + 30 * n14 - n13);
                                    }
                                    this.rd.setColor(this.color2k(150, 150, 150));
                                    this.rd.drawLine(70, 82 + 30 * n14 - n13, 185, 82 + 30 * n14 - n13);
                                }
                                ++n14;
                            }
                            if (this.invo && n14 == 2) {
                                this.invo = false;
                            }
                            if (!this.invo) {
                                for (n9 = this.npo - 1; n9 >= 0; --n9) {
                                    if (this.pgames[n9] == -1) continue;
                                    n8 = 0;
                                    for (n7 = 0; n7 < this.ngm; ++n7) {
                                        if (this.pgames[n9] != this.gnum[n7]) continue;
                                        n8 = n7;
                                    }
                                    if (this.wait[n8] > 0) continue;
                                    n7 = 0;
                                    for (n5 = 0; n5 < this.npo; ++n5) {
                                        if (n9 == n5 || !this.pnames[n9].equals(this.pnames[n5])) continue;
                                        if (this.pgames[n5] == -1) {
                                            n7 = 1;
                                            continue;
                                        }
                                        for (n6 = 0; n6 < this.ngm; ++n6) {
                                            if (this.pgames[n5] != this.gnum[n6] || this.wait[n6] <= 0) continue;
                                            n7 = 1;
                                        }
                                    }
                                    if (n7 != 0) continue;
                                    if (82 + 30 * n14 - n13 > 50 && 82 + 30 * (n14 - 1) - n13 < 415) {
                                        n5 = 0;
                                        if (n > 70 && n < 185 && n2 > 52 + 30 * n14 - n13 && n2 < 82 + 30 * n14 - n13) {
                                            if (this.pgames[this.im] == -1 && this.join == -1 && this.chalngd >= -1) {
                                                if (bl || this.mousonp == n9) {
                                                    this.rd.setColor(this.color2k(255, 255, 255));
                                                    this.mousonp = n9;
                                                    n16 = 52 + 30 * n14 - n13;
                                                    if (bl) {
                                                        if (this.cmonp == n9) {
                                                            this.ongame = this.pgames[n9];
                                                        }
                                                        this.chalngd = -1;
                                                    } else {
                                                        if (this.cmonp == -1) {
                                                            this.ongame = -1;
                                                            this.cmonp = n9;
                                                        }
                                                        if (this.ongame == this.pgames[n9]) {
                                                            this.mousonp = -1;
                                                        }
                                                    }
                                                } else {
                                                    this.rd.setColor(this.color2k(220, 220, 220));
                                                }
                                                this.rd.fillRect(70, 53 + 30 * n14 - n13, 116, 29);
                                                n15 = n9;
                                            }
                                            n5 = 1;
                                            if (control.handb) {
                                                this.gs.cmsg.setText(this.gs.cmsg.getText() + "" + this.pnames[n9]);
                                                control.handb = false;
                                            }
                                        }
                                        if (this.pgames[this.im] == -1 && this.join == -1 && this.chalngd >= -1) {
                                            if (this.wait[n8] == 0) {
                                                this.rd.setColor(new Color(117, 67, 0));
                                            } else {
                                                this.rd.setColor(this.color2k(0, 28, 102));
                                            }
                                        } else if (this.wait[n8] == 0) {
                                            this.rd.setColor(new Color(82, 47, 0));
                                        } else {
                                            this.rd.setColor(this.color2k(0, 20, 71));
                                        }
                                        n6 = this.gb.drawl(this.rd, this.pnames[n9], 68, 53 + 30 * n14 - n13, n5 != 0) ? 1 : 0;
                                        if (n5 == 0 || n6 == 0) {
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString(this.pnames[n9], 127 - this.ftm.stringWidth(this.pnames[n9]) / 2, 66 + 30 * n14 - n13);
                                            this.rd.setFont(new Font("Arial", 0, 10));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString(this.pcarnames[n9], 127 - this.ftm.stringWidth(this.pcarnames[n9]) / 2, 78 + 30 * n14 - n13);
                                        }
                                        this.rd.setColor(this.color2k(150, 150, 150));
                                        this.rd.drawLine(70, 82 + 30 * n14 - n13, 185, 82 + 30 * n14 - n13);
                                    }
                                    ++n14;
                                }
                            }
                        }
                        if (this.mousonp != n15) {
                            this.mousonp = -1;
                            this.cmonp = -1;
                        }
                        if (this.npo == 0) {
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString("|  Loading Players  |", 127 - this.ftm.stringWidth("|  Loading Players  |") / 2, 95);
                        }
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(65, 25, 145, 28);
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawLine(65, 50, 190, 50);
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(65, 413, 145, 12);
                        this.rd.setColor(this.color2k(150, 150, 150));
                        this.rd.drawLine(65, 415, 190, 415);
                        this.rd.setColor(this.color2k(205, 205, 205));
                        this.rd.fillRect(193, 53, 17, 360);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawLine(211, 25, 211, 425);
                        this.rd.drawImage(this.xt.roomp, 72, 30, null);
                        if (this.mscro == 131 || n11 == 0) {
                            if (n11 == 0) {
                                this.rd.setColor(this.color2k(205, 205, 205));
                            } else {
                                this.rd.setColor(this.color2k(215, 215, 215));
                            }
                            this.rd.fillRect(193, 53, 17, 17);
                        } else {
                            this.rd.setColor(this.color2k(220, 220, 220));
                            this.rd.fill3DRect(193, 53, 17, 17, true);
                        }
                        if (n11 != 0) {
                            this.rd.drawImage(this.xt.asu, 198, 59, null);
                        }
                        if (this.mscro == 132 || n11 == 0) {
                            if (n11 == 0) {
                                this.rd.setColor(this.color2k(205, 205, 205));
                            } else {
                                this.rd.setColor(this.color2k(215, 215, 215));
                            }
                            this.rd.fillRect(193, 396, 17, 17);
                        } else {
                            this.rd.setColor(this.color2k(220, 220, 220));
                            this.rd.fill3DRect(193, 396, 17, 17, true);
                        }
                        if (n11 != 0) {
                            this.rd.drawImage(this.xt.asd, 198, 403, null);
                        }
                        if (n11 != 0 && this.conon == 1) {
                            if (this.lspos != this.spos) {
                                this.rd.setColor(this.color2k(215, 215, 215));
                                this.rd.fillRect(193, 70 + this.spos, 17, 31);
                            } else {
                                if (this.mscro == 131) {
                                    this.rd.setColor(this.color2k(215, 215, 215));
                                }
                                this.rd.fill3DRect(193, 70 + this.spos, 17, 31, true);
                            }
                            this.rd.setColor(this.color2k(150, 150, 150));
                            this.rd.drawLine(198, 83 + this.spos, 204, 83 + this.spos);
                            this.rd.drawLine(198, 85 + this.spos, 204, 85 + this.spos);
                            this.rd.drawLine(198, 87 + this.spos, 204, 87 + this.spos);
                            if (this.mscro > 101 && this.lspos != this.spos) {
                                this.lspos = this.spos;
                            }
                            if (bl) {
                                if (this.mscro == 125 && n > 193 && n < 210 && n2 > 70 + this.spos && n2 < this.spos + 101) {
                                    this.mscro = n2 - this.spos;
                                }
                                if (this.mscro == 125 && n > 191 && n < 212 && n2 > 51 && n2 < 72) {
                                    this.mscro = 131;
                                }
                                if (this.mscro == 125 && n > 191 && n < 212 && n2 > 394 && n2 < 415) {
                                    this.mscro = 132;
                                }
                                if (this.mscro == 125 && n > 193 && n < 210 && n2 > 70 && n2 < 396) {
                                    this.mscro = 85;
                                    this.spos = n2 - this.mscro;
                                }
                                if ((n10 = 1350 / n11) < 1) {
                                    n10 = 1;
                                }
                                if (this.mscro == 131) {
                                    this.spos -= n10;
                                    if (this.spos > 295) {
                                        this.spos = 295;
                                    }
                                    if (this.spos < 0) {
                                        this.spos = 0;
                                    }
                                    this.lspos = this.spos;
                                }
                                if (this.mscro == 132) {
                                    this.spos += n10;
                                    if (this.spos > 295) {
                                        this.spos = 295;
                                    }
                                    if (this.spos < 0) {
                                        this.spos = 0;
                                    }
                                    this.lspos = this.spos;
                                }
                                if (this.mscro <= 101) {
                                    this.spos = n2 - this.mscro;
                                    if (this.spos > 295) {
                                        this.spos = 295;
                                    }
                                    if (this.spos < 0) {
                                        this.spos = 0;
                                    }
                                }
                                if (this.mscro == 125) {
                                    this.mscro = 225;
                                }
                            } else if (this.mscro != 125) {
                                this.mscro = 125;
                            }
                            if (n3 != 0 && n > 65 && n < 170 && n2 > 93 && n2 < 413) {
                                this.spos -= n3;
                                this.zeromsw = true;
                                if (this.spos > 295) {
                                    this.spos = 295;
                                    this.zeromsw = false;
                                }
                                if (this.spos < 0) {
                                    this.spos = 0;
                                    this.zeromsw = false;
                                }
                                this.lspos = this.spos;
                            }
                        }
                        if (this.ongame == -1) {
                            if (this.opengame >= 2) {
                                if (this.opengame >= 27) {
                                    this.opengame = 26;
                                }
                                n10 = 229 + this.opengame;
                                if (n10 > 255) {
                                    n10 = 255;
                                }
                                if (n10 < 0) {
                                    n10 = 0;
                                }
                                this.rd.setColor(this.color2k(n10, n10, n10));
                                this.rd.fillRoundRect(225, 59 - (int)((float)this.opengame * 2.23f), 495, 200 + this.opengame * 8, 20, 20);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawRoundRect(225, 59 - (int)((float)this.opengame * 2.23f), 495, 200 + this.opengame * 8, 20, 20);
                                if (!this.xt.lan) {
                                    this.rd.setColor(this.color2k(217, 217, 217));
                                    this.rd.fillRoundRect(225, 263 + this.opengame * 7, 495, 157, 20, 20);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawRoundRect(225, 263 + this.opengame * 7, 495, 157, 20, 20);
                                }
                                this.btn = 0;
                                if (this.prevloaded != -1) {
                                    this.prevloaded = -1;
                                }
                                if (this.updatec != -1) {
                                    this.updatec = -1;
                                }
                                if (this.gs.cmsg.isShowing()) {
                                    this.gs.cmsg.hide();
                                    this.gs.requestFocus();
                                }
                                this.opengame -= 2;
                                if (this.opengame == 0 && this.longame != -1 && this.chalngd == -1) {
                                    this.ongame = this.longame;
                                    this.longame = -1;
                                }
                                if (this.invo) {
                                    this.invo = false;
                                }
                                for (n9 = 0; n9 < 7; ++n9) {
                                    if (!this.invos[n9].equals("")) {
                                        this.invos[n9] = "";
                                    }
                                    if (this.dinvi[n9].equals("")) continue;
                                    this.dinvi[n9] = "";
                                }
                                if (this.fstart) {
                                    this.fstart = false;
                                }
                                for (n9 = 0; n9 < 9; ++n9) {
                                    if (this.cac[n9] == -1) continue;
                                    this.cac[n9] = -1;
                                }
                                if (this.dispcar != -1) {
                                    this.dispcar = -1;
                                }
                            } else {
                                int n17;
                                int n18;
                                if (!this.xt.lan) {
                                    this.drawSbutton(this.xt.cgame, 292, 42);
                                    this.drawSbutton(this.xt.ccar, 442, 42);
                                    this.rd.setFont(new Font("Arial", 1, 13));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.setColor(this.color2k(60, 60, 60));
                                    if (!this.gs.rooms.isShowing()) {
                                        this.gs.rooms.show();
                                    }
                                    this.gs.rooms.move(580 - this.gs.rooms.w / 2, 29);
                                    if (this.gs.rooms.sel != 0) {
                                        this.stopallnow();
                                        n10 = this.gs.rooms.iroom[this.gs.rooms.sel];
                                        if (n10 < 1000) {
                                            if (n10 >= 1 && n10 <= 5) {
                                                this.xt.servport = 7070 + n10;
                                            }
                                        } else if ((n10 -= 1000) >= 0 && n10 < this.lg.nservers) {
                                            this.xt.servport = 7071;
                                            this.xt.server = this.lg.servers[n10];
                                            this.xt.servername = this.lg.snames[n10];
                                        }
                                        this.inishlobby();
                                        this.gs.rooms.kmoused = 20;
                                    }
                                    if (this.gs.rooms.kmoused != 0) {
                                        n = -1;
                                        n2 = -1;
                                        bl = false;
                                        --this.gs.rooms.kmoused;
                                    }
                                } else {
                                    this.rd.drawImage(this.xt.lanm, 241, 31, null);
                                    if (this.npo <= 1) {
                                        this.drawSbutton(this.xt.cgame, 292, -1000);
                                        this.rd.setColor(new Color(0, 0, 0));
                                        if (this.ncnt == 0) {
                                            this.rd.setColor(new Color(188, 111, 0));
                                        }
                                        this.rd.setFont(new Font("Arial", 1, 13));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.drawString(this.lmsg, 472 - this.ftm.stringWidth(this.lmsg) / 2, 295);
                                        if (this.lmsg.equals(". . . | Searching/Waiting for other LAN Players | . . .") && this.ncnt == 0) {
                                            this.lmsg = "| Searching/Waiting for other LAN Players |";
                                            this.ncnt = 5;
                                        }
                                        if (this.lmsg.equals(". . | Searching/Waiting for other LAN Players | . .") && this.ncnt == 0) {
                                            this.lmsg = ". . . | Searching/Waiting for other LAN Players | . . .";
                                            this.ncnt = 5;
                                        }
                                        if (this.lmsg.equals(". | Searching/Waiting for other LAN Players | .") && this.ncnt == 0) {
                                            this.lmsg = ". . | Searching/Waiting for other LAN Players | . .";
                                            this.ncnt = 5;
                                        }
                                        if (this.lmsg.equals("| Searching/Waiting for other LAN Players |") && this.ncnt == 0) {
                                            this.lmsg = ". | Searching/Waiting for other LAN Players | .";
                                            this.ncnt = 5;
                                        }
                                        if (this.ncnt != 0) {
                                            --this.ncnt;
                                        }
                                        this.rd.setColor(this.color2k(70, 70, 70));
                                        this.rd.drawString("So far, you are the only player connected to this network!", 225, 325);
                                        this.rd.drawString("There needs to be at least one more player logged in here with your same", 225, 345);
                                        this.rd.drawString("internet connection...", 225, 365);
                                    } else if (!this.lanlogged) {
                                        this.rd.setColor(new Color(0, 0, 0));
                                        this.rd.setFont(new Font("Arial", 1, 13));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.drawString("You have played the allowed 3 LAN games per day!", 472 - this.ftm.stringWidth("You have played the allowed 3 LAN games per day!") / 2, 295);
                                        this.rd.setColor(this.color2k(70, 70, 70));
                                        this.rd.drawString("There needs to be at least one of the LAN players in the lobby with a registered", 225, 325);
                                        this.rd.drawString("account to be able to play LAN unlimitedly...", 225, 345);
                                        this.rd.drawString("Just one registered user allows everyone in the LAN game to play unlimitedly!", 225, 365);
                                        this.rd.drawString("Please register now!", 225, 385);
                                        this.drawSbutton(this.xt.register, 472, 395);
                                    } else {
                                        this.rd.setColor(this.color2k(90, 90, 90));
                                        this.rd.setFont(new Font("Arial", 1, 12));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.drawString("[  " + n14 + " Players Connected  ]", 472 - this.ftm.stringWidth("[  " + n14 + " Players Connected  ]") / 2, 295);
                                        this.drawSbutton(this.xt.cgame, 472, 325);
                                    }
                                    this.drawSbutton(this.xt.ccar, 442, -1000);
                                }
                                this.drawSbutton(this.xt.exit, 690, 42);
                                if (control.enter && !this.gs.cmsg.getText().equals("Type here...") && !this.gs.cmsg.getText().equals("")) {
                                    if (this.chalngd == -1) {
                                        this.pessd[2] = true;
                                    } else {
                                        this.pessd[5] = true;
                                    }
                                    control.enter = false;
                                    String string = this.gs.cmsg.getText().replace('|', ':');
                                    if (string.toLowerCase().indexOf(this.gs.tpass.getText().toLowerCase()) != -1) {
                                        string = " ";
                                    }
                                    if (!this.xt.msgcheck(string) && this.updatec > -12) {
                                        for (n9 = 0; n9 < 6; ++n9) {
                                            this.sentn[n9] = this.sentn[n9 + 1];
                                            this.cnames[n9] = this.cnames[n9 + 1];
                                        }
                                        this.sentn[6] = string;
                                        this.cnames[6] = this.pnames[this.im];
                                        this.updatec = this.updatec > -11 ? -11 : --this.updatec;
                                        this.spos3 = 28;
                                    } else {
                                        ++this.xt.warning;
                                    }
                                    this.gs.cmsg.setText("");
                                }
                                if (this.chalngd == -1) {
                                    this.rd.setColor(this.color2k(230, 230, 230));
                                    this.rd.fillRoundRect(225, 59, 495, 200, 20, 20);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawRoundRect(225, 59, 495, 200, 20, 20);
                                    if (this.britchl != 0) {
                                        this.britchl = 0;
                                    }
                                    n11 = (this.ngm - 5) * 24;
                                    if (n11 < 0) {
                                        n11 = 0;
                                    }
                                    n13 = (int)((float)this.spos2 / 82.0f * (float)n11 - 2.0f);
                                    int[] nArray = new int[this.ngm];
                                    int[] nArray2 = new int[this.ngm];
                                    for (n8 = 0; n8 < this.ngm; ++n8) {
                                        nArray[n8] = 0;
                                    }
                                    for (n8 = 0; n8 < this.ngm; ++n8) {
                                        for (n7 = n8 + 1; n7 < this.ngm; ++n7) {
                                            if (this.wait[n8] != this.wait[n7]) {
                                                if (this.wait[n8] <= 0 && this.wait[n7] <= 0) {
                                                    if (this.wait[n8] < this.wait[n7]) {
                                                        int n19 = n8;
                                                        nArray[n19] = nArray[n19] + 1;
                                                        continue;
                                                    }
                                                    int n20 = n7;
                                                    nArray[n20] = nArray[n20] + 1;
                                                    continue;
                                                }
                                                if ((this.wait[n8] > this.wait[n7] || this.wait[n8] <= 0) && this.wait[n7] > 0) {
                                                    int n21 = n8;
                                                    nArray[n21] = nArray[n21] + 1;
                                                    continue;
                                                }
                                                int n22 = n7;
                                                nArray[n22] = nArray[n22] + 1;
                                                continue;
                                            }
                                            if (n7 < n8) {
                                                int n23 = n8;
                                                nArray[n23] = nArray[n23] + 1;
                                                continue;
                                            }
                                            int n24 = n7;
                                            nArray[n24] = nArray[n24] + 1;
                                        }
                                        nArray2[nArray[n8]] = n8;
                                    }
                                    if (control.down) {
                                        ++this.opselect;
                                        n8 = 0;
                                        while (80 + 24 * this.opselect - n13 > 202 && n8 == 0) {
                                            ++this.spos2;
                                            if (this.spos2 > 82) {
                                                this.spos2 = 82;
                                                n8 = 1;
                                            }
                                            if (this.spos2 < 0) {
                                                this.spos2 = 0;
                                                n8 = 1;
                                            }
                                            n13 = (int)((float)this.spos2 / 82.0f * (float)n11 - 2.0f);
                                        }
                                        control.down = false;
                                    }
                                    if (control.up) {
                                        --this.opselect;
                                        n8 = 0;
                                        while (80 + 24 * this.opselect - n13 < 80 && n8 == 0) {
                                            --this.spos2;
                                            if (this.spos2 > 82) {
                                                this.spos2 = 82;
                                                n8 = 1;
                                            }
                                            if (this.spos2 < 0) {
                                                this.spos2 = 0;
                                                n8 = 1;
                                            }
                                            n13 = (int)((float)this.spos2 / 82.0f * (float)n11 - 2.0f);
                                        }
                                        control.up = false;
                                    }
                                    n8 = -1;
                                    if (this.mousonp != -1) {
                                        n7 = 0;
                                        for (n5 = 0; n5 < this.ngm; ++n5) {
                                            if (this.pgames[this.mousonp] != this.gnum[n5]) continue;
                                            n7 = n5;
                                        }
                                        n8 = 91 + 24 * nArray[n7] - n13;
                                        if (80 + 24 * nArray[n7] - n13 > 202) {
                                            n5 = 1000 / n11;
                                            if (n5 < 1) {
                                                n5 = 1;
                                            }
                                            this.spos2 += n5;
                                            n8 = -1;
                                        }
                                        if (80 + 24 * nArray[n7] - n13 < 80) {
                                            n5 = 1000 / n11;
                                            if (n5 < 1) {
                                                n5 = 1;
                                            }
                                            this.spos2 -= n5;
                                            n8 = -1;
                                        }
                                        if (this.spos2 > 82) {
                                            this.spos2 = 82;
                                        }
                                        if (this.spos2 < 0) {
                                            this.spos2 = 0;
                                        }
                                        n13 = (int)((float)this.spos2 / 82.0f * (float)n11 - 2.0f);
                                        this.opselect = nArray[n7];
                                    }
                                    if (this.opselect <= -1) {
                                        this.opselect = 0;
                                    }
                                    if (this.opselect >= this.ngm) {
                                        this.opselect = this.ngm - 1;
                                    }
                                    n7 = 0;
                                    for (n5 = 0; n5 < this.ngm; ++n5) {
                                        if (80 + 24 * n5 - n13 < 224 && 80 + 24 * n5 - n13 > 56) {
                                            if (this.opselect == n5) {
                                                if (80 + 24 * n5 - n13 >= 224) {
                                                    --this.opselect;
                                                }
                                                if (80 + 24 * n5 - n13 < 62) {
                                                    ++this.opselect;
                                                }
                                            }
                                            n6 = 0;
                                            n18 = 0;
                                            if (!this.gs.openm) {
                                                if (n > 241 && n < 692 && n2 > 92 + 24 * n5 - n13 && n2 < 110 + 24 * n5 - n13) {
                                                    if (this.lxm != n || this.lym != n2) {
                                                        this.opselect = n5;
                                                    }
                                                    n6 = 1;
                                                    if (bl) {
                                                        if (this.clicked == -1) {
                                                            this.clicked = nArray2[n5];
                                                        }
                                                    } else {
                                                        if (this.clicked == nArray2[n5]) {
                                                            this.ongame = this.gnum[nArray2[n5]];
                                                            this.opengame = 0;
                                                            if (n >= 641 && n <= 689 && n2 > 92 + 24 * n5 - n13 && n2 < 110 + 24 * n5 - n13 && this.wait[nArray2[n5]] > 0) {
                                                                n17 = 0;
                                                                if (this.gwarb[nArray2[n5]] == 0) {
                                                                    if (this.gplyrs[nArray2[n5]].equals("") || this.gplyrs[nArray2[n5]].indexOf(this.pnames[this.im]) != -1) {
                                                                        n17 = 1;
                                                                    }
                                                                } else if (this.xt.clan.toLowerCase().equals(this.gaclan[nArray2[n5]].toLowerCase()) || this.xt.clan.toLowerCase().equals(this.gvclan[nArray2[n5]].toLowerCase())) {
                                                                    n17 = 1;
                                                                }
                                                                if (n17 != 0) {
                                                                    this.join = this.gnum[nArray2[n5]];
                                                                    this.msg = "| Joining Game |";
                                                                    this.spos = 0;
                                                                }
                                                            }
                                                            this.clicked = -1;
                                                        }
                                                        ++n7;
                                                    }
                                                } else {
                                                    ++n7;
                                                }
                                                if (n >= 641 && n <= 689 && n2 > 92 + 24 * n5 - n13 && n2 < 110 + 24 * n5 - n13 && bl) {
                                                    n18 = 1;
                                                }
                                            }
                                            if (this.opselect == n5) {
                                                if (n6 != 0 && bl || control.enter) {
                                                    this.rd.setColor(this.color2k(200, 200, 200));
                                                    if (control.enter) {
                                                        this.ongame = this.gnum[nArray2[n5]];
                                                        this.opengame = 0;
                                                        control.enter = false;
                                                    }
                                                } else {
                                                    this.rd.setColor(this.color2k(255, 255, 255));
                                                }
                                                this.rd.fillRect(241, 92 + 24 * n5 - n13, 451, 18);
                                                if (n6 != 0) {
                                                    this.rd.setColor(this.color2k(150, 150, 150));
                                                    this.rd.drawRect(239, 90 + 24 * n5 - n13, 454, 21);
                                                }
                                            }
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString(this.gstages[nArray2[n5]], 382 - this.ftm.stringWidth(this.gstages[nArray2[n5]]) / 2, 105 + 24 * n5 - n13);
                                            this.rd.drawString("|", 525, 105 + 24 * n5 - n13);
                                            this.rd.drawString("|", 584, 105 + 24 * n5 - n13);
                                            if (this.wait[nArray2[n5]] > 0) {
                                                this.rd.drawString("" + this.npls[nArray2[n5]] + " / " + this.mnpls[nArray2[n5]] + "", 556 - this.ftm.stringWidth("" + this.npls[nArray2[n5]] + " / " + this.mnpls[nArray2[n5]] + "") / 2, 105 + 24 * n5 - n13);
                                                this.rd.setFont(new Font("Arial", 0, 12));
                                                this.rd.setColor(new Color(80, 128, 0));
                                                this.rd.drawString("Waiting", 593, 105 + 24 * n5 - n13);
                                                this.rd.setFont(new Font("Arial", 1, 12));
                                                this.ftm = this.rd.getFontMetrics();
                                                if (n18 == 0) {
                                                    this.rd.setColor(this.color2k(230, 230, 230));
                                                    this.rd.fill3DRect(641, 92 + 24 * n5 - n13, 48, 18, true);
                                                    this.rd.fill3DRect(642, 93 + 24 * n5 - n13, 46, 16, true);
                                                } else {
                                                    this.rd.setColor(this.color2k(230, 230, 230));
                                                    this.rd.fillRect(641, 92 + 24 * n5 - n13, 48, 18);
                                                }
                                                this.rd.setColor(new Color(0, 0, 0));
                                                n17 = 0;
                                                if (this.gwarb[nArray2[n5]] == 0) {
                                                    if (this.gplyrs[nArray2[n5]].equals("") || this.gplyrs[nArray2[n5]].indexOf(this.pnames[this.im]) != -1) {
                                                        n17 = 1;
                                                    }
                                                } else if (this.xt.clan.toLowerCase().equals(this.gaclan[nArray2[n5]].toLowerCase()) || this.xt.clan.toLowerCase().equals(this.gvclan[nArray2[n5]].toLowerCase())) {
                                                    n17 = 1;
                                                }
                                                if (n17 != 0) {
                                                    this.rd.drawString("Join", 665 - this.ftm.stringWidth("Join") / 2, 105 + 24 * n5 - n13);
                                                    continue;
                                                }
                                                this.rd.drawString("View", 665 - this.ftm.stringWidth("View") / 2, 105 + 24 * n5 - n13);
                                                continue;
                                            }
                                            this.rd.drawString("" + this.npls[nArray2[n5]] + "", 556 - this.ftm.stringWidth("" + this.npls[nArray2[n5]] + "") / 2, 105 + 24 * n5 - n13);
                                            this.rd.setFont(new Font("Arial", 0, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            if (this.wait[nArray2[n5]] == 0) {
                                                this.rd.setColor(new Color(128, 73, 0));
                                                this.rd.drawString("Started", 594, 105 + 24 * n5 - n13);
                                            } else {
                                                this.rd.setColor(this.color2k(100, 100, 100));
                                                this.rd.drawString("Finished", 590, 105 + 24 * n5 - n13);
                                            }
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            if (n18 == 0) {
                                                this.rd.setColor(this.color2k(230, 230, 230));
                                                this.rd.fill3DRect(641, 92 + 24 * n5 - n13, 48, 18, true);
                                            } else {
                                                this.rd.setColor(this.color2k(230, 230, 230));
                                                this.rd.fillRect(641, 92 + 24 * n5 - n13, 48, 18);
                                            }
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.drawString("View", 665 - this.ftm.stringWidth("View") / 2, 105 + 24 * n5 - n13);
                                            continue;
                                        }
                                        if (this.opselect == n5) {
                                            if (80 + 24 * n5 - n13 >= 224) {
                                                --this.opselect;
                                            }
                                            if (80 + 24 * n5 - n13 <= 56) {
                                                ++this.opselect;
                                            }
                                        }
                                        ++n7;
                                    }
                                    if (n7 == this.ngm && this.clicked != -1) {
                                        this.clicked = -1;
                                    }
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    if (this.ngm == 0) {
                                        if (!this.lloaded) {
                                            this.rd.drawString("|  Loading Games  |", 472 - this.ftm.stringWidth("|  Loading Games  |") / 2, 165);
                                        } else if (!this.xt.lan) {
                                            this.rd.drawString("No Games Created", 472 - this.ftm.stringWidth("No Games Created") / 2, 165);
                                        }
                                    }
                                    this.rd.setColor(this.color2k(205, 205, 205));
                                    this.rd.fillRect(235, 65, 480, 25);
                                    this.rd.drawImage(this.xt.games, 241, 69, null);
                                    this.rd.setColor(this.color2k(70, 70, 70));
                                    this.rd.drawString("Stage Name", 382 - this.ftm.stringWidth("Stage Name") / 2, 81);
                                    this.rd.drawString("|", 525, 81);
                                    this.rd.drawString("Players", 556 - this.ftm.stringWidth("Players") / 2, 81);
                                    this.rd.drawString("|", 584, 81);
                                    this.rd.drawString("Status", 641 - this.ftm.stringWidth("Status") / 2, 81);
                                    this.rd.setColor(this.color2k(150, 150, 150));
                                    this.rd.drawLine(235, 87, 696, 87);
                                    this.rd.setColor(this.color2k(205, 205, 205));
                                    this.rd.fillRect(235, 237, 480, 17);
                                    this.rd.setColor(this.color2k(150, 150, 150));
                                    this.rd.drawLine(235, 239, 696, 239);
                                    this.rd.setColor(this.color2k(205, 205, 205));
                                    this.rd.fillRect(698, 107, 17, 113);
                                    this.rd.setColor(this.color2k(205, 205, 205));
                                    this.rd.fillRect(231, 65, 4, 189);
                                    if (this.mscro2 == 141 || n11 == 0) {
                                        if (n11 == 0) {
                                            this.rd.setColor(this.color2k(205, 205, 205));
                                        } else {
                                            this.rd.setColor(this.color2k(215, 215, 215));
                                        }
                                        this.rd.fillRect(698, 90, 17, 17);
                                    } else {
                                        this.rd.setColor(this.color2k(220, 220, 220));
                                        this.rd.fill3DRect(698, 90, 17, 17, true);
                                    }
                                    if (n11 != 0) {
                                        this.rd.drawImage(this.xt.asu, 703, 96, null);
                                    }
                                    if (this.mscro2 == 142 || n11 == 0) {
                                        if (n11 == 0) {
                                            this.rd.setColor(this.color2k(205, 205, 205));
                                        } else {
                                            this.rd.setColor(this.color2k(215, 215, 215));
                                        }
                                        this.rd.fillRect(698, 220, 17, 17);
                                    } else {
                                        this.rd.setColor(this.color2k(220, 220, 220));
                                        this.rd.fill3DRect(698, 220, 17, 17, true);
                                    }
                                    if (n11 != 0) {
                                        this.rd.drawImage(this.xt.asd, 703, 226, null);
                                    }
                                    if (n11 != 0) {
                                        if (this.lspos2 != this.spos2) {
                                            this.rd.setColor(this.color2k(215, 215, 215));
                                            this.rd.fillRect(698, 107 + this.spos2, 17, 31);
                                        } else {
                                            if (this.mscro2 == 141) {
                                                this.rd.setColor(this.color2k(215, 215, 215));
                                            }
                                            this.rd.fill3DRect(698, 107 + this.spos2, 17, 31, true);
                                        }
                                        this.rd.setColor(this.color2k(150, 150, 150));
                                        this.rd.drawLine(703, 120 + this.spos2, 709, 120 + this.spos2);
                                        this.rd.drawLine(703, 122 + this.spos2, 709, 122 + this.spos2);
                                        this.rd.drawLine(703, 124 + this.spos2, 709, 124 + this.spos2);
                                        if (this.mscro2 > 138 && this.lspos2 != this.spos2) {
                                            this.lspos2 = this.spos2;
                                        }
                                        if (bl) {
                                            if (this.mscro2 == 145 && n > 698 && n < 715 && n2 > 107 + this.spos2 && n2 < this.spos2 + 138) {
                                                this.mscro2 = n2 - this.spos2;
                                            }
                                            if (this.mscro2 == 145 && n > 696 && n < 717 && n2 > 88 && n2 < 109) {
                                                this.mscro2 = 141;
                                            }
                                            if (this.mscro2 == 145 && n > 696 && n < 717 && n2 > 218 && n2 < 239) {
                                                this.mscro2 = 142;
                                            }
                                            if (this.mscro2 == 145 && n > 698 && n < 715 && n2 > 107 && n2 < 220) {
                                                this.mscro2 = 122;
                                                this.spos2 = n2 - this.mscro2;
                                            }
                                            if ((n5 = 400 / n11) < 1) {
                                                n5 = 1;
                                            }
                                            if (this.mscro2 == 141) {
                                                this.spos2 -= n5;
                                                if (this.spos2 > 82) {
                                                    this.spos2 = 82;
                                                }
                                                if (this.spos2 < 0) {
                                                    this.spos2 = 0;
                                                }
                                                this.lspos2 = this.spos2;
                                            }
                                            if (this.mscro2 == 142) {
                                                this.spos2 += n5;
                                                if (this.spos2 > 82) {
                                                    this.spos2 = 82;
                                                }
                                                if (this.spos2 < 0) {
                                                    this.spos2 = 0;
                                                }
                                                this.lspos2 = this.spos2;
                                            }
                                            if (this.mscro2 <= 138) {
                                                this.spos2 = n2 - this.mscro2;
                                                if (this.spos2 > 82) {
                                                    this.spos2 = 82;
                                                }
                                                if (this.spos2 < 0) {
                                                    this.spos2 = 0;
                                                }
                                            }
                                            if (this.mscro2 == 145) {
                                                this.mscro2 = 225;
                                            }
                                        } else if (this.mscro2 != 145) {
                                            this.mscro2 = 145;
                                        }
                                        if (n3 != 0 && n > 235 && n < 698 && n2 > 87 && n2 < 239) {
                                            this.spos2 -= n3;
                                            this.zeromsw = true;
                                            if (this.spos2 > 82) {
                                                this.spos2 = 82;
                                                this.zeromsw = false;
                                            }
                                            if (this.spos2 < 0) {
                                                this.spos2 = 0;
                                                this.zeromsw = false;
                                            }
                                            this.lspos2 = this.spos2;
                                        }
                                    }
                                    if (this.mousonp != -1 && n8 != -1) {
                                        this.rd.setColor(this.color2k(255, 255, 255));
                                        int[] nArray3 = new int[]{185, 241, 241, 185};
                                        int[] nArray4 = new int[]{n16, n8, n8 + 19, n16 + 30};
                                        this.rd.fillPolygon(nArray3, nArray4, 4);
                                        this.rd.setColor(this.color2k(150, 150, 150));
                                        this.rd.drawLine(185, n16, 241, n8);
                                        this.rd.drawLine(185, n16 + 30, 241, n8 + 19);
                                        this.rd.drawLine(241, n8, 692, n8);
                                        this.rd.drawLine(241, n8 + 19, 692, n8 + 19);
                                    }
                                } else {
                                    int n25 = 230 + this.britchl;
                                    if (n25 > 255) {
                                        n25 = 255;
                                    }
                                    if (n25 < 0) {
                                        n25 = 0;
                                    }
                                    this.rd.setColor(this.color2k(n25, n25, n25));
                                    this.rd.fillRoundRect(225, 59, 495, 200, 20, 20);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawRoundRect(225, 59, 495, 200, 20, 20);
                                    if (this.britchl < 25) {
                                        this.britchl += 5;
                                    }
                                    if (this.chalngd > -1) {
                                        n9 = 0;
                                        for (n8 = 0; n8 < this.ngm; ++n8) {
                                            if (this.chalngd != this.gnum[n8]) continue;
                                            n9 = n8;
                                        }
                                        if (this.cflk % 4 != 0 || this.cflk == 0) {
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.setFont(new Font("Arial", 1, 13));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString("You have been invited by " + this.chalby + " to join a game!", 472 - this.ftm.stringWidth("You have been invited by " + this.chalby + " to join a game!") / 2, 95);
                                            this.rd.setColor(new Color(117, 67, 0));
                                            this.rd.drawString(this.chalby, 472 - this.ftm.stringWidth("You have been invited by " + this.chalby + " to join a game!") / 2 + this.ftm.stringWidth("You have been invited by "), 95);
                                        }
                                        if (this.cflk != 0) {
                                            --this.cflk;
                                        }
                                        this.rd.setColor(new Color(0, 0, 0));
                                        this.rd.setFont(new Font("Arial", 1, 12));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.drawString("Stage:  " + this.gstages[n9] + " ,  Laps: " + this.gnlaps[n9] + "", 472 - this.ftm.stringWidth("Stage:  " + this.gstages[n9] + " ,  Laps: " + this.gnlaps[n9] + "") / 2, 130);
                                        this.rd.setColor(new Color(62, 98, 0));
                                        this.rd.drawString(this.gstages[n9], 472 - this.ftm.stringWidth("Stage:  " + this.gstages[n9] + " ,  Laps: " + this.gnlaps[n9] + "") / 2 + this.ftm.stringWidth("Stage:  "), 130);
                                        this.rd.drawString("" + this.gnlaps[n9] + "", 472 - this.ftm.stringWidth("Stage:  " + this.gstages[n9] + " ,  Laps: " + this.gnlaps[n9] + "") / 2 + this.ftm.stringWidth("Stage:  " + this.gstages[n9] + " ,  Laps: "), 130);
                                        this.rd.setColor(new Color(0, 0, 0));
                                        this.rd.drawString("Players:  " + this.mnpls[n9] + "", 472 - this.ftm.stringWidth("Players:  " + this.mnpls[n9] + "") / 2, 150);
                                        this.rd.setColor(new Color(62, 98, 0));
                                        this.rd.drawString("" + this.mnpls[n9] + "", 472 - this.ftm.stringWidth("Players:  " + this.mnpls[n9] + "") / 2 + this.ftm.stringWidth("Players:  "), 150);
                                        Date date = new Date();
                                        long l = date.getTime();
                                        if (this.ptime == 0L || l > this.ptime + 1000L) {
                                            if (this.ptime != 0L) {
                                                --this.ctime;
                                            }
                                            this.ptime = l;
                                        }
                                        this.rd.setColor(new Color(0, 0, 0));
                                        this.rd.setFont(new Font("Arial", 0, 12));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.drawString("( " + this.ctime + " )", 472 - this.ftm.stringWidth("( " + this.ctime + " )") / 2, 170);
                                        if (this.ctime == 0) {
                                            this.ongame = this.longame;
                                            this.chalngd = -1;
                                            this.longame = -1;
                                        }
                                        this.stringbutton("   View Game   ", 352, 215, 2);
                                        this.stringbutton("   Join Game   ", 462, 215, 2);
                                        this.stringbutton("   Decline X   ", 599, 215, 2);
                                    } else {
                                        if (this.chalngd != -5) {
                                            this.stringbutton(" Cancel X ", 669, 85, 2);
                                        }
                                        if (this.chalngd == -6) {
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.setFont(new Font("Arial", 1, 13));
                                            this.rd.drawString("This room already has a game that has started.", 288, 120);
                                            this.rd.drawString("Please switch to another room to create a new game.", 288, 140);
                                            this.rd.drawString("Or wait for the game to finish.", 288, 160);
                                            this.rd.drawString("Use the dropdown menu above to change room or server.", 288, 180);
                                            int[] nArray = new int[]{580, 569, 576, 576, 584, 584, 591};
                                            int[] nArray5 = new int[]{66, 77, 77, 102, 102, 77, 77};
                                            this.rd.fillPolygon(nArray, nArray5, 7);
                                            this.stringbutton("     OK     ", 472, 215, 1);
                                        }
                                        if (this.chalngd == -2) {
                                            boolean bl2 = false;
                                            boolean bl3 = false;
                                            if (!this.gs.wgame.isShowing()) {
                                                this.gs.wgame.show();
                                            }
                                            this.gs.wgame.move(236, 68);
                                            if (this.gs.wgame.getSelectedIndex() == 0) {
                                                if (this.inwab) {
                                                    this.inwab = false;
                                                    this.gs.warb.hide();
                                                    this.loadstage = 0;
                                                }
                                                this.rd.setColor(new Color(0, 0, 0));
                                                this.rd.setFont(new Font("Arial", 1, 13));
                                                this.ftm = this.rd.getFontMetrics();
                                                if (this.sflk % 4 != 0 || this.sflk == 0) {
                                                    this.rd.drawString("Select Stage", 472 - this.ftm.stringWidth("Select Stage") / 2, 85);
                                                }
                                                if (this.sflk != 0) {
                                                    --this.sflk;
                                                }
                                                n7 = 0;
                                                this.gs.sgame.setSize(139, 22);
                                                if (this.gs.sgame.getSelectedIndex() == 0) {
                                                    n7 = 472 - (this.gs.sgame.getWidth() + 6 + this.gs.snfmm.getWidth()) / 2;
                                                    this.sgflag = 0;
                                                }
                                                if (this.gs.sgame.getSelectedIndex() == 1) {
                                                    n7 = 472 - (this.gs.sgame.getWidth() + 6 + this.gs.snfm1.getWidth()) / 2;
                                                    this.sgflag = 1;
                                                }
                                                if (this.gs.sgame.getSelectedIndex() == 2) {
                                                    n7 = 472 - (this.gs.sgame.getWidth() + 6 + this.gs.snfm2.getWidth()) / 2;
                                                    this.sgflag = 2;
                                                }
                                                if (this.gs.sgame.getSelectedIndex() == 3) {
                                                    this.gs.mstgs.setSize(338, 22);
                                                    if (this.sgflag != 3) {
                                                        this.gstage = 0;
                                                        if (this.xt.logged) {
                                                            if (this.cd.msloaded != 1) {
                                                                this.gs.mstgs.removeAll();
                                                                this.gs.mstgs.add(this.rd, "Loading your stages now, please wait...");
                                                                this.gs.mstgs.select(0);
                                                                this.msload = 1;
                                                            }
                                                        } else {
                                                            this.gs.mstgs.removeAll();
                                                            this.gs.mstgs.add(this.rd, "You need to have a full account to access this.");
                                                            this.gs.mstgs.select(0);
                                                            this.cd.msloaded = 0;
                                                        }
                                                        this.sgflag = 3;
                                                    }
                                                    n7 = 472 - (this.gs.sgame.getWidth() + 6 + this.gs.mstgs.getWidth()) / 2;
                                                }
                                                if (this.gs.sgame.getSelectedIndex() == 4) {
                                                    this.gs.mstgs.setSize(338, 22);
                                                    if (this.sgflag != 4) {
                                                        this.gstage = 0;
                                                        if (this.xt.logged) {
                                                            if (this.cd.msloaded != 7) {
                                                                this.gs.mstgs.removeAll();
                                                                this.gs.mstgs.add(this.rd, "Loading your stages now, please wait...");
                                                                this.gs.mstgs.select(0);
                                                                this.msload = 7;
                                                            }
                                                        } else {
                                                            this.gs.mstgs.removeAll();
                                                            this.gs.mstgs.add(this.rd, "You need to have a full account to access this.");
                                                            this.gs.mstgs.select(0);
                                                            this.cd.msloaded = 0;
                                                        }
                                                        this.sgflag = 4;
                                                    }
                                                    n7 = 472 - (this.gs.sgame.getWidth() + 6 + this.gs.mstgs.getWidth()) / 2;
                                                }
                                                if (this.gs.sgame.getSelectedIndex() == 5) {
                                                    this.gs.mstgs.setSize(338, 22);
                                                    if (this.sgflag != 5) {
                                                        this.gstage = 0;
                                                        if (this.xt.logged) {
                                                            if (this.cd.msloaded != 3) {
                                                                this.gs.mstgs.removeAll();
                                                                this.gs.mstgs.add(this.rd, "Loading your stages now, please wait...");
                                                                this.gs.mstgs.select(0);
                                                                this.msload = 3;
                                                            }
                                                        } else {
                                                            this.gs.mstgs.removeAll();
                                                            this.gs.mstgs.add(this.rd, "You need to have a full account to access this.");
                                                            this.gs.mstgs.select(0);
                                                            this.cd.msloaded = 0;
                                                        }
                                                        this.sgflag = 5;
                                                    }
                                                    n7 = 472 - (this.gs.sgame.getWidth() + 6 + this.gs.mstgs.getWidth()) / 2;
                                                }
                                                if (this.gs.sgame.getSelectedIndex() == 6) {
                                                    this.gs.mstgs.setSize(338, 22);
                                                    if (this.sgflag != 6) {
                                                        this.gstage = 0;
                                                        if (this.xt.logged) {
                                                            if (this.cd.msloaded != 4) {
                                                                this.gs.mstgs.removeAll();
                                                                this.gs.mstgs.add(this.rd, "Loading your stages now, please wait...");
                                                                this.gs.mstgs.select(0);
                                                                this.msload = 4;
                                                            }
                                                        } else {
                                                            this.gs.mstgs.removeAll();
                                                            this.gs.mstgs.add(this.rd, "You need to have a full account to access this.");
                                                            this.gs.mstgs.select(0);
                                                            this.cd.msloaded = 0;
                                                        }
                                                        this.sgflag = 6;
                                                    }
                                                    n7 = 472 - (this.gs.sgame.getWidth() + 6 + this.gs.mstgs.getWidth()) / 2;
                                                }
                                                if (!this.gs.sgame.isShowing()) {
                                                    this.gs.sgame.show();
                                                    this.remstage = 0;
                                                    if (this.loadstage == 0) {
                                                        n5 = (int)(Math.random() * 3.0);
                                                        if (n5 == 3) {
                                                            n5 = 2;
                                                        }
                                                        this.gs.sgame.select(n5);
                                                    }
                                                }
                                                this.gs.sgame.move(n7, 105);
                                                n7 += this.gs.sgame.getWidth() + 6;
                                                if (this.gs.sgame.getSelectedIndex() == 0) {
                                                    if (!this.gs.snfmm.isShowing()) {
                                                        this.gs.snfmm.show();
                                                        if (this.loadstage == 0) {
                                                            this.gs.snfmm.select(0);
                                                        }
                                                    }
                                                    this.gs.snfmm.move(n7, 105);
                                                    if (this.gs.snfm1.isShowing()) {
                                                        this.gs.snfm1.hide();
                                                    }
                                                    if (this.gs.snfm2.isShowing()) {
                                                        this.gs.snfm2.hide();
                                                    }
                                                    if (this.gs.mstgs.isShowing()) {
                                                        this.gs.mstgs.hide();
                                                    }
                                                }
                                                if (this.gs.sgame.getSelectedIndex() == 0 && this.gs.snfmm.getSelectedIndex() != 0 && this.gstage != this.gs.snfmm.getSelectedIndex() + 27) {
                                                    this.gstage = this.loadstage = this.gs.snfmm.getSelectedIndex() + 27;
                                                    this.gs.requestFocus();
                                                }
                                                if (this.gs.sgame.getSelectedIndex() == 1) {
                                                    if (!this.gs.snfm2.isShowing()) {
                                                        this.gs.snfm2.show();
                                                        if (this.loadstage == 0) {
                                                            this.gs.snfm2.select(0);
                                                        }
                                                    }
                                                    this.gs.snfm2.move(n7, 105);
                                                    if (this.gs.snfmm.isShowing()) {
                                                        this.gs.snfmm.hide();
                                                    }
                                                    if (this.gs.snfm1.isShowing()) {
                                                        this.gs.snfm1.hide();
                                                    }
                                                    if (this.gs.mstgs.isShowing()) {
                                                        this.gs.mstgs.hide();
                                                    }
                                                }
                                                if (this.gs.sgame.getSelectedIndex() == 1 && this.gs.snfm2.getSelectedIndex() != 0 && this.gstage != this.gs.snfm2.getSelectedIndex() + 10) {
                                                    this.gstage = this.loadstage = this.gs.snfm2.getSelectedIndex() + 10;
                                                    this.gs.requestFocus();
                                                }
                                                if (this.gs.sgame.getSelectedIndex() == 2) {
                                                    if (!this.gs.snfm1.isShowing()) {
                                                        this.gs.snfm1.show();
                                                        if (this.loadstage == 0) {
                                                            this.gs.snfm1.select(0);
                                                        }
                                                    }
                                                    this.gs.snfm1.move(n7, 105);
                                                    if (this.gs.snfmm.isShowing()) {
                                                        this.gs.snfmm.hide();
                                                    }
                                                    if (this.gs.snfm2.isShowing()) {
                                                        this.gs.snfm2.hide();
                                                    }
                                                    if (this.gs.mstgs.isShowing()) {
                                                        this.gs.mstgs.hide();
                                                    }
                                                }
                                                if (this.gs.sgame.getSelectedIndex() == 2 && this.gs.snfm1.getSelectedIndex() != 0 && this.gstage != this.gs.snfm1.getSelectedIndex()) {
                                                    this.gstage = this.loadstage = this.gs.snfm1.getSelectedIndex();
                                                    this.gs.requestFocus();
                                                }
                                                if (this.gs.sgame.getSelectedIndex() == 3) {
                                                    if (!this.gs.mstgs.isShowing()) {
                                                        this.gs.mstgs.show();
                                                        if (this.loadstage == 0) {
                                                            this.gs.mstgs.select(0);
                                                        }
                                                    }
                                                    this.gs.mstgs.move(n7, 105);
                                                    if (this.gs.snfmm.isShowing()) {
                                                        this.gs.snfmm.hide();
                                                    }
                                                    if (this.gs.snfm1.isShowing()) {
                                                        this.gs.snfm1.hide();
                                                    }
                                                    if (this.gs.snfm2.isShowing()) {
                                                        this.gs.snfm2.hide();
                                                    }
                                                }
                                                if (this.remstage != 2) {
                                                    if (this.gs.sgame.getSelectedIndex() == 3 && this.gs.mstgs.getSelectedIndex() != 0) {
                                                        if (this.gstage != this.gs.mstgs.getSelectedIndex() + 100) {
                                                            this.gstage = this.loadstage = this.gs.mstgs.getSelectedIndex() + 100;
                                                            this.gs.requestFocus();
                                                            this.remstage = 0;
                                                        }
                                                        if (this.loadstage <= 0 && this.remstage == 0 && this.xt.drawcarb(true, null, "X", 674, 136, n, n2, bl)) {
                                                            this.remstage = 1;
                                                        }
                                                    } else if (this.remstage != 0) {
                                                        this.remstage = 0;
                                                    }
                                                }
                                                if (this.gs.sgame.getSelectedIndex() >= 4) {
                                                    if (!this.gs.mstgs.isShowing()) {
                                                        this.gs.mstgs.show();
                                                        if (this.loadstage == 0) {
                                                            this.gs.mstgs.select(0);
                                                        }
                                                    }
                                                    this.gs.mstgs.move(n7, 105);
                                                    if (this.gs.snfmm.isShowing()) {
                                                        this.gs.snfmm.hide();
                                                    }
                                                    if (this.gs.snfm1.isShowing()) {
                                                        this.gs.snfm1.hide();
                                                    }
                                                    if (this.gs.snfm2.isShowing()) {
                                                        this.gs.snfm2.hide();
                                                    }
                                                }
                                                if (this.gs.sgame.getSelectedIndex() >= 4 && this.gs.mstgs.getSelectedIndex() != 0 && this.gstage != this.gs.mstgs.getSelectedIndex() + 100) {
                                                    this.gstage = this.loadstage = this.gs.mstgs.getSelectedIndex() + 100;
                                                    this.gs.requestFocus();
                                                }
                                                if (this.loadstage > 0 && this.remstage == 0) {
                                                    this.rd.setColor(new Color(0, 0, 0));
                                                    this.rd.setFont(new Font("Arial", 1, 12));
                                                    this.ftm = this.rd.getFontMetrics();
                                                    this.rd.drawString("Loading stage, please wait...", 472 - this.ftm.stringWidth("Loading Stage, please wait...") / 2, 165);
                                                }
                                                if (this.gs.sgame.getSelectedIndex() >= 3 && !this.xt.logged) {
                                                    this.rd.setColor(new Color(0, 0, 0));
                                                    this.rd.setFont(new Font("Arial", 1, 12));
                                                    this.ftm = this.rd.getFontMetrics();
                                                    this.rd.drawString("You are currently using a trial account.", 472 - this.ftm.stringWidth("You are currently using a trial account.") / 2, 155);
                                                    this.rd.drawString("You need to upgrade your account to access and publish custom stages!", 472 - this.ftm.stringWidth("You need to upgrade your account to access and publish custom stages!") / 2, 175);
                                                    this.rd.setColor(this.color2k(200, 200, 200));
                                                    this.rd.fillRoundRect(382, 185, 180, 50, 20, 20);
                                                    this.drawSbutton(this.xt.upgrade, 472, 210);
                                                    if (this.gs.slaps.isShowing()) {
                                                        this.gs.slaps.hide();
                                                    }
                                                } else {
                                                    if (this.loadstage < 0 && this.remstage == 0) {
                                                        this.rd.setColor(new Color(0, 0, 0));
                                                        this.rd.setFont(new Font("Arial", 1, 12));
                                                        this.ftm = this.rd.getFontMetrics();
                                                        this.rd.drawString("" + this.gstagename + "  -  Laps:              ", 472 - this.ftm.stringWidth("" + this.gstagename + "  -  Laps:              ") / 2, 155);
                                                        if (!this.gs.slaps.isShowing()) {
                                                            this.gs.slaps.show();
                                                            this.gs.slaps.select(this.gstagelaps - 1);
                                                        }
                                                        this.gs.slaps.move(472 + this.ftm.stringWidth("" + this.gstagename + "  -  Laps:              ") / 2 - 35, 138);
                                                        if (this.gs.slaps.getSelectedIndex() != this.gstagelaps - 1) {
                                                            this.gstagelaps = this.gs.slaps.getSelectedIndex() + 1;
                                                            this.gs.requestFocus();
                                                        }
                                                        this.stringbutton("    Preview Stage    ", 472, 185, 2);
                                                    } else if (this.gs.slaps.isShowing()) {
                                                        this.gs.slaps.hide();
                                                    }
                                                    if (this.remstage == 3) {
                                                        if (this.loadstage < 0) {
                                                            this.stringbutton("    Preview Stage    ", 472, -160, 2);
                                                        }
                                                        this.rd.setColor(new Color(0, 0, 0));
                                                        this.rd.setFont(new Font("Arial", 1, 13));
                                                        this.ftm = this.rd.getFontMetrics();
                                                        this.xt.drawlprom(135, 75);
                                                        this.rd.drawString("Failed to remove stage, server error, please try again later.", 472 - this.ftm.stringWidth("Failed to remove stage, server error, please try again later.") / 2, 155);
                                                        if (this.xt.drawcarb(true, null, " OK ", 451, 175, n, n2, bl)) {
                                                            this.remstage = 0;
                                                            this.gs.mouses = 0;
                                                        }
                                                    }
                                                    if (this.remstage == 2) {
                                                        if (this.loadstage < 0) {
                                                            this.stringbutton("    Preview Stage    ", 472, -160, 2);
                                                        }
                                                        this.rd.setColor(new Color(0, 0, 0));
                                                        this.rd.setFont(new Font("Arial", 1, 13));
                                                        this.ftm = this.rd.getFontMetrics();
                                                        this.xt.drawlprom(135, 75);
                                                        this.rd.drawString("Removing stage from your account...", 472 - this.ftm.stringWidth("Removing stage from your account...") / 2, 175);
                                                        if (this.cd.staction == 0) {
                                                            this.gstage = 0;
                                                            this.loadstage = 0;
                                                            this.remstage = 0;
                                                        }
                                                        if (this.cd.staction == -1) {
                                                            this.remstage = 3;
                                                            this.gs.mouses = 0;
                                                        }
                                                    }
                                                    if (this.remstage == 1) {
                                                        if (this.loadstage < 0) {
                                                            this.stringbutton("    Preview Stage    ", 472, -160, 2);
                                                        }
                                                        this.xt.drawlprom(135, 75);
                                                        this.rd.setColor(new Color(0, 0, 0));
                                                        this.rd.setFont(new Font("Arial", 1, 13));
                                                        this.ftm = this.rd.getFontMetrics();
                                                        this.rd.drawString("Remove this stage from your account?", 472 - this.ftm.stringWidth("Remove this stage from your account?") / 2, 155);
                                                        if (this.xt.drawcarb(true, null, " Yes ", 426, 175, n, n2, bl)) {
                                                            this.remstage = 2;
                                                            this.cd.onstage = this.gs.mstgs.getSelectedItem();
                                                            this.cd.staction = 1;
                                                            this.cd.sparkstageaction();
                                                            this.gs.mouses = 0;
                                                        }
                                                        if (this.xt.drawcarb(true, null, " No ", 480, 175, n, n2, bl)) {
                                                            this.remstage = 0;
                                                            this.gs.mouses = 0;
                                                        }
                                                    }
                                                    this.stringbutton("   Next >   ", 472, 235, 1);
                                                }
                                            } else {
                                                if (!this.inwab) {
                                                    this.gs.sgame.hide();
                                                    this.gs.mstgs.hide();
                                                    this.gs.slaps.hide();
                                                    this.gs.snfm1.hide();
                                                    this.gs.snfmm.hide();
                                                    this.gs.snfm2.hide();
                                                }
                                                if (!this.xt.clan.equals("")) {
                                                    if (!this.inwab) {
                                                        this.rd.setColor(new Color(0, 0, 0));
                                                        this.rd.setFont(new Font("Arial", 1, 12));
                                                        this.ftm = this.rd.getFontMetrics();
                                                        this.rd.drawString("Loading your clan's wars and battles, please wait...", 472 - this.ftm.stringWidth("Loading your clan's wars and battles, please wait...") / 2, 155);
                                                        this.loadwarb = true;
                                                        this.warbsel = 0;
                                                        this.cancreate = 0;
                                                    } else {
                                                        if (!this.gs.warb.isShowing()) {
                                                            this.gs.warb.show();
                                                        }
                                                        this.gs.warb.move(472 - this.gs.warb.w / 2, 105);
                                                        if (this.gs.warb.sel != 0) {
                                                            if (this.gs.warb.sel != this.warbsel) {
                                                                this.gb.loadwbgames = 1;
                                                                this.rd.setColor(new Color(0, 0, 0));
                                                                this.rd.setFont(new Font("Arial", 1, 12));
                                                                this.ftm = this.rd.getFontMetrics();
                                                                this.rd.drawString("Loading scheduled games, please wait...", 472 - this.ftm.stringWidth("Loading scheduled games, please wait...") / 2, 165);
                                                                this.warbsel = this.gs.warb.sel;
                                                                this.gs.vnpls.sel = 0;
                                                                this.gs.vtyp.sel = 0;
                                                                this.pgamesel = 0;
                                                                this.cancreate = 0;
                                                            } else {
                                                                if (this.gb.loadwbgames == 7) {
                                                                    this.rd.setColor(new Color(0, 0, 0));
                                                                    this.rd.setFont(new Font("Arial", 1, 12));
                                                                    this.ftm = this.rd.getFontMetrics();
                                                                    this.rd.drawString("Redoing last game, please wait...", 472 - this.ftm.stringWidth("Redoing last game, please wait...") / 2, 155);
                                                                }
                                                                if (this.gb.loadwbgames == 2) {
                                                                    this.rd.setColor(new Color(0, 0, 0));
                                                                    this.rd.setFont(new Font("Arial", 1, 12));
                                                                    this.ftm = this.rd.getFontMetrics();
                                                                    if (this.gs.wgame.getSelectedIndex() == 1) {
                                                                        this.rd.drawString("[ " + this.gb.gameturndisp + " ]", 472 - this.ftm.stringWidth("[ " + this.gb.gameturndisp + " ]") / 2, 155);
                                                                        bl3 = true;
                                                                        n7 = 472 - (this.gs.vnpls.w + this.gs.vtyp.w + 10) / 2;
                                                                        this.gs.vnpls.move(n7, 168);
                                                                        this.gs.vtyp.move(n7 += this.gs.vnpls.w + 10, 168);
                                                                        if (!this.gb.lwbwinner.toLowerCase().equals(this.xt.clan.toLowerCase())) {
                                                                            if (this.gs.vnpls.sel != 0) {
                                                                                if (this.pgamesel != -this.gs.vnpls.sel) {
                                                                                    this.gstagename = this.gb.wbstages[this.gb.gameturn];
                                                                                    this.cancreate = 0;
                                                                                    this.pgamesel = -this.gs.vnpls.sel;
                                                                                }
                                                                                if (this.cancreate == 1) {
                                                                                    this.cancreate = 2;
                                                                                }
                                                                                if (this.cancreate == 0) {
                                                                                    if (this.gb.wbstage[this.gb.gameturn] == 101) {
                                                                                        this.cancreate = 2;
                                                                                    } else {
                                                                                        this.loadstage = this.gb.wbstage[this.gb.gameturn];
                                                                                        this.cancreate = 1;
                                                                                    }
                                                                                }
                                                                                if (this.cancreate == 1) {
                                                                                    this.rd.drawString("Loading...", 472 - this.ftm.stringWidth("Loading...") / 2, 235);
                                                                                }
                                                                                if (this.cancreate == 2) {
                                                                                    this.stringbutton("   Create Game   ", 472, 235, 1);
                                                                                }
                                                                            }
                                                                        } else {
                                                                            if (this.sflk != 0) {
                                                                                --this.sflk;
                                                                            } else {
                                                                                this.sflk = 4;
                                                                                this.rd.setColor(new Color(117, 67, 0));
                                                                            }
                                                                            this.rd.drawString("Your clan won the last game.  " + this.gb.vclan + " must create this game!", 472 - this.ftm.stringWidth("Your clan won the last game.  " + this.gb.vclan + " must create this game!") / 2, 211);
                                                                        }
                                                                        if (this.gb.canredo) {
                                                                            this.stringbutton(" Redo last game  < ", 644, 242, 1);
                                                                        }
                                                                    }
                                                                    if (this.gs.wgame.getSelectedIndex() == 2) {
                                                                        bl2 = true;
                                                                        this.gs.pgame.move(472 - this.gs.pgame.w / 2, 150);
                                                                        if (this.gs.pgame.sel != 0) {
                                                                            if (this.pgamesel != this.gs.pgame.sel) {
                                                                                this.gstagename = this.gb.wbstages[this.gs.pgame.sel - 1];
                                                                                this.cancreate = 0;
                                                                                this.pgamesel = this.gs.pgame.sel;
                                                                            }
                                                                            if (this.cancreate == 1) {
                                                                                this.cancreate = 2;
                                                                            }
                                                                            if (this.cancreate == 0) {
                                                                                if (this.gb.wbstage[this.gs.pgame.sel - 1] == 101) {
                                                                                    this.cancreate = 2;
                                                                                } else {
                                                                                    this.loadstage = this.gb.wbstage[this.gs.pgame.sel - 1];
                                                                                    this.cancreate = 1;
                                                                                }
                                                                            }
                                                                            if (this.cancreate == 1) {
                                                                                this.rd.drawString("Loading...", 472 - this.ftm.stringWidth("Loading...") / 2, 235);
                                                                            }
                                                                            if (this.cancreate == 2) {
                                                                                this.stringbutton("   Create Practice Game   ", 472, 235, 1);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                if (this.gb.loadwbgames == 3) {
                                                                    this.rd.drawString("Failed to load scheduled games, please try again later...", 472 - this.ftm.stringWidth("Failed to load scheduled games, please try again later...") / 2, 165);
                                                                }
                                                                if (this.gb.loadwbgames == 4) {
                                                                    this.rd.drawString("This war or battle was not found, it may have been expired.", 472 - this.ftm.stringWidth("This war or battle was not found, it may have been expired.") / 2, 165);
                                                                }
                                                                if (this.gb.loadwbgames == 6) {
                                                                    this.rd.drawString("Failed to undo the last game, please try again later...", 472 - this.ftm.stringWidth("Failed to undo the last game, please try again later...") / 2, 165);
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    this.rd.setColor(new Color(0, 0, 0));
                                                    this.rd.setFont(new Font("Arial", 1, 12));
                                                    this.ftm = this.rd.getFontMetrics();
                                                    this.rd.drawString("You must join a clan first to play wars and battles!", 472 - this.ftm.stringWidth("You must join a clan first to play wars and battles!") / 2, 145);
                                                    this.stringbutton("    Find a clan to join    ", 472, 185, 2);
                                                }
                                                if (!this.inwab) {
                                                    this.inwab = true;
                                                }
                                            }
                                            if (bl2) {
                                                this.gs.pgame.show();
                                            } else {
                                                this.gs.pgame.hide();
                                            }
                                            if (bl3) {
                                                this.gs.vnpls.show();
                                                this.gs.vtyp.show();
                                            } else {
                                                this.gs.vnpls.hide();
                                                this.gs.vtyp.hide();
                                            }
                                        }
                                        if (this.chalngd == -3) {
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.setFont(new Font("Arial", 1, 13));
                                            this.ftm = this.rd.getFontMetrics();
                                            if (this.sflk % 4 != 0 || this.sflk == 0) {
                                                this.rd.drawString("Select Number of Players", 472 - this.ftm.stringWidth("Select Number of Players") / 2, 85);
                                            }
                                            if (this.sflk != 0) {
                                                --this.sflk;
                                            }
                                            int n26 = 0;
                                            if (this.xt.lan) {
                                                n26 = 59;
                                            }
                                            this.rd.drawString("Players", 413 - n26, 122);
                                            if (!this.gs.snpls.isShowing()) {
                                                this.gs.snpls.show();
                                                this.gs.snpls.select(this.gnpls - 1);
                                            }
                                            this.gs.snpls.move(467 - n26, 105);
                                            boolean bl4 = false;
                                            if (this.gs.snpls.getSelectedIndex() != 0 && this.gs.snpls.getSelectedIndex() != this.gnpls - 1) {
                                                this.gnpls = this.gs.snpls.getSelectedIndex() + 1;
                                                bl4 = true;
                                                this.gs.swait.hide();
                                            }
                                            if (this.xt.lan) {
                                                this.rd.drawString("Bots", 490, 122);
                                                if (!this.gs.snbts.isShowing()) {
                                                    this.gs.snbts.show();
                                                    this.gs.snbts.select(0);
                                                    this.gnbts = 0;
                                                }
                                                this.gs.snbts.move(524, 105);
                                                if (this.gs.snbts.getSelectedIndex() != this.gnbts || bl4) {
                                                    this.gnbts = this.gs.snbts.getSelectedIndex();
                                                    while (this.gnbts + this.gnpls > 8) {
                                                        --this.gnbts;
                                                    }
                                                    this.gs.snbts.select(this.gnbts);
                                                }
                                            }
                                            this.rd.drawString("Wait", 414, 162);
                                            if (!this.gs.swait.isShowing()) {
                                                this.gs.swait.show();
                                                if (this.gwait == 0) {
                                                    this.gs.swait.select(1);
                                                }
                                            }
                                            this.gs.swait.move(451, 145);
                                            if ((this.gs.swait.getSelectedIndex() + 1) * 60 != this.gwait) {
                                                this.gwait = (this.gs.swait.getSelectedIndex() + 1) * 60;
                                            }
                                            this.rd.setColor(this.color2k(90, 90, 90));
                                            this.rd.setFont(new Font("Arial", 0, 11));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString("( Maximum time to wait for all players to `join. )", 472 - this.ftm.stringWidth("( Maximum time to wait for all players to join. )") / 2, 179);
                                            this.stringbutton("   < Back   ", 422, 235, 1);
                                            this.stringbutton("   Next >   ", 522, 235, 1);
                                        }
                                        if (this.chalngd == -4) {
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.setFont(new Font("Arial", 1, 13));
                                            this.ftm = this.rd.getFontMetrics();
                                            if (this.sflk % 4 != 0 || this.sflk == 0) {
                                                this.rd.drawString("Game Options", 472 - this.ftm.stringWidth("Game Options") / 2, 85);
                                            }
                                            if (this.sflk != 0) {
                                                --this.sflk;
                                            }
                                            int n27 = 472 - (this.gs.scars.getWidth() + this.gs.sclass.getWidth() + this.gs.sfix.getWidth() + 16) / 2;
                                            if (!this.gs.scars.isShowing()) {
                                                this.gs.scars.show();
                                                if (this.gcars >= 0 && this.gcars <= 2) {
                                                    this.gs.scars.select(this.gcars);
                                                } else {
                                                    this.gs.scars.select(0);
                                                }
                                            }
                                            this.gs.scars.move(n27, 105);
                                            n27 += this.gs.scars.getWidth() + 8;
                                            if (!this.gs.sclass.isShowing()) {
                                                this.gs.sclass.show();
                                                if (this.gclass >= 0 && this.gclass <= 5) {
                                                    this.gs.sclass.select(this.gclass);
                                                } else {
                                                    this.gs.sclass.select(0);
                                                }
                                            }
                                            this.gs.sclass.move(n27, 105);
                                            n27 += this.gs.sclass.getWidth() + 8;
                                            if (!this.gs.sfix.isShowing()) {
                                                this.gs.sfix.show();
                                                if (this.gfix >= 0 && this.gfix <= 5) {
                                                    this.gs.sfix.select(this.gfix);
                                                } else {
                                                    this.gs.sfix.select(0);
                                                }
                                            }
                                            this.gs.sfix.move(n27, 105);
                                            n27 += this.gs.sfix.getWidth();
                                            if (!this.gs.openm) {
                                                this.gs.movefield(this.gs.notp, n27 - 112, 131, 150, 17);
                                            } else {
                                                this.gs.movefield(this.gs.notp, n27 - 112, -2000, 150, 17);
                                            }
                                            if (!this.gs.notp.isShowing()) {
                                                this.gs.notp.show();
                                                if (this.gnotp == 0) {
                                                    this.gs.notp.setState(false);
                                                } else {
                                                    this.gs.notp.setState(true);
                                                }
                                            }
                                            if (this.xt.sc[0] < 16) {
                                                if (!this.gs.openm) {
                                                    this.gs.movefield(this.gs.mycar, 472 - (this.gs.scars.getWidth() + this.gs.sclass.getWidth() + this.gs.sfix.getWidth() + 16) / 2, 131, 150, 17);
                                                } else {
                                                    this.gs.movefield(this.gs.mycar, 410, -2000, 150, 17);
                                                }
                                                if (!this.gs.mycar.isShowing()) {
                                                    this.gs.mycar.show();
                                                    this.gs.mycar.setLabel("" + this.cd.names[this.xt.sc[0]] + " Game!");
                                                    if (this.gclass <= -2) {
                                                        this.gs.mycar.setState(true);
                                                    } else {
                                                        this.gs.mycar.setState(false);
                                                    }
                                                }
                                            } else if (this.gs.mycar.getState()) {
                                                this.gs.mycar.setState(false);
                                            }
                                            if (this.gs.mycar.getState()) {
                                                if (this.gs.sclass.isEnabled()) {
                                                    this.gs.sclass.disable();
                                                }
                                                if (this.gs.scars.isEnabled()) {
                                                    this.gs.scars.disable();
                                                }
                                            } else {
                                                if (!this.gs.sclass.isEnabled()) {
                                                    this.gs.sclass.enable();
                                                }
                                                if (!this.gs.scars.isEnabled()) {
                                                    this.gs.scars.enable();
                                                }
                                                if (this.gs.sclass.getSelectedIndex() != this.gclass) {
                                                    this.gclass = this.gs.sclass.getSelectedIndex();
                                                    this.gs.mycar.hide();
                                                }
                                                if (this.gs.scars.getSelectedIndex() != this.gcars) {
                                                    this.gcars = this.gs.scars.getSelectedIndex();
                                                }
                                            }
                                            if (this.gs.sfix.getSelectedIndex() != this.gfix) {
                                                this.gfix = this.gs.sfix.getSelectedIndex();
                                            }
                                            String string = "Public Game, anyone can join...";
                                            n7 = 0;
                                            for (n5 = 0; n5 < 7; ++n5) {
                                                if (this.invos[n5].equals("")) continue;
                                                ++n7;
                                            }
                                            if (n7 > 0) {
                                                string = "Players Allowed:  ";
                                                n5 = 0;
                                                for (n6 = 0; n6 < 7; ++n6) {
                                                    if (this.invos[n6].equals("")) continue;
                                                    string = string + this.invos[n6];
                                                    if (++n5 == n7) continue;
                                                    if (n5 == n7 - 1) {
                                                        string = string + " and ";
                                                        continue;
                                                    }
                                                    string = string + ", ";
                                                }
                                            }
                                            this.rd.setColor(new Color(0, 0, 0));
                                            if (n7 < this.gnpls - 1) {
                                                this.rd.setFont(new Font("Arial", 1, 13));
                                                this.rd.drawString("Private Game, only specific players allowed to join?  " + n7 + "/" + (this.gnpls - 1) + "", 330, 180);
                                                this.stringbutton("<   Select   ", 281, 180, 2);
                                                this.rd.setFont(new Font("Tahoma", 0, 11));
                                                this.ftm = this.rd.getFontMetrics();
                                                this.rd.drawString(string, 472 - this.ftm.stringWidth(string) / 2, 203);
                                            } else {
                                                this.rd.setFont(new Font("Arial", 1, 13));
                                                this.ftm = this.rd.getFontMetrics();
                                                this.rd.drawString("" + n7 + " Allowed Players Selected", 472 - this.ftm.stringWidth("" + n7 + " Allowed Players Selected") / 2, 180);
                                                this.rd.setFont(new Font("Tahoma", 0, 11));
                                                this.ftm = this.rd.getFontMetrics();
                                                this.rd.drawString(string, 472 - this.ftm.stringWidth(string) / 2, 203);
                                            }
                                            this.stringbutton("   < Back   ", 422, 235, 1);
                                            this.stringbutton("   Finish!   ", 522, 235, 1);
                                        }
                                        if (this.chalngd == -5) {
                                            if (this.fstart) {
                                                this.fstart = false;
                                            }
                                            this.rd.setFont(new Font("Arial", 1, 13));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString(this.msg, 472 - this.ftm.stringWidth(this.msg) / 2, 145);
                                            if (this.msg.equals(". . . | Creating Game | . . .") && this.ncnt == 0) {
                                                this.msg = "| Creating Game |";
                                                this.ncnt = 5;
                                            }
                                            if (this.msg.equals(". . | Creating Game | . .") && this.ncnt == 0) {
                                                this.msg = ". . . | Creating Game | . . .";
                                                this.ncnt = 5;
                                            }
                                            if (this.msg.equals(". | Creating Game | .") && this.ncnt == 0) {
                                                this.msg = ". . | Creating Game | . .";
                                                this.ncnt = 5;
                                            }
                                            if (this.msg.equals("| Creating Game |") && this.ncnt == 0) {
                                                this.msg = ". | Creating Game | .";
                                                this.ncnt = 5;
                                            }
                                            if (this.ncnt != 0) {
                                                --this.ncnt;
                                            }
                                        }
                                    }
                                }
                                if (!this.xt.lan) {
                                    int n28;
                                    this.rd.setColor(this.color2k(230, 230, 230));
                                    this.rd.fillRoundRect(225, 263, 495, 157, 20, 20);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawRoundRect(225, 263, 495, 157, 20, 20);
                                    String[] stringArray = new String[14];
                                    String[] stringArray2 = new String[14];
                                    boolean[] blArray = new boolean[14];
                                    for (n28 = 0; n28 < 14; ++n28) {
                                        stringArray[n28] = "";
                                        stringArray2[n28] = "";
                                        blArray[n28] = false;
                                    }
                                    n28 = 0;
                                    this.rd.setFont(new Font("Tahoma", 0, 11));
                                    this.ftm = this.rd.getFontMetrics();
                                    if (this.updatec != -1) {
                                        int n29;
                                        for (int i = 0; i < 7; ++i) {
                                            stringArray[n28] = "";
                                            stringArray2[n28] = this.cnames[i];
                                            n18 = 0;
                                            n17 = 0;
                                            int n30 = 0;
                                            int n31 = 0;
                                            for (int j = 0; j < this.sentn[i].length(); ++j) {
                                                String string = "" + this.sentn[i].charAt(j);
                                                if (string.equals(" ")) {
                                                    n18 = n17;
                                                    n30 = j;
                                                    ++n31;
                                                } else {
                                                    n31 = 0;
                                                }
                                                if (n31 > 1) continue;
                                                int n32 = n28;
                                                stringArray[n32] = stringArray[n32] + string;
                                                ++n17;
                                                if (this.ftm.stringWidth(stringArray[n28]) <= 367) continue;
                                                if (n18 != 0) {
                                                    stringArray[n28] = stringArray[n28].substring(0, n18);
                                                    if (n28 == 13) {
                                                        for (int k = 0; k < 13; ++k) {
                                                            stringArray[k] = stringArray[k + 1];
                                                            stringArray2[k] = stringArray2[k + 1];
                                                            blArray[k] = blArray[k + 1];
                                                        }
                                                        stringArray[n28] = "";
                                                        blArray[n28] = true;
                                                    } else {
                                                        stringArray2[++n28] = this.cnames[i];
                                                    }
                                                    j = n30;
                                                    n17 = 0;
                                                    n18 = 0;
                                                    continue;
                                                }
                                                stringArray[n28] = "";
                                                n17 = 0;
                                            }
                                            if (n28 == 13 && i != 6) {
                                                for (int j = 0; j < 13; ++j) {
                                                    stringArray[j] = stringArray[j + 1];
                                                    stringArray2[j] = stringArray2[j + 1];
                                                    blArray[j] = blArray[j + 1];
                                                }
                                                continue;
                                            }
                                            ++n28;
                                        }
                                        n11 = (n28 - 6) * 15;
                                        if (n11 < 0) {
                                            n11 = 0;
                                        }
                                        n13 = (int)((float)this.spos3 / 28.0f * (float)n11);
                                        String string = "";
                                        this.rd.setFont(new Font("Tahoma", 1, 11));
                                        this.ftm = this.rd.getFontMetrics();
                                        for (n29 = 0; n29 < n28; ++n29) {
                                            if (string.equals(stringArray2[n29])) continue;
                                            if (280 + n29 * 15 - n13 > 266 && 280 + n29 * 15 - n13 < 370) {
                                                this.rd.drawString(stringArray2[n29] + ":", 320 - this.ftm.stringWidth(stringArray2[n29] + ":"), 305 + n29 * 15 - n13);
                                            }
                                            string = stringArray2[n29];
                                        }
                                        this.rd.setFont(new Font("Tahoma", 0, 11));
                                        for (n29 = 0; n29 < n28; ++n29) {
                                            if (blArray[n29] && n29 == 0 && stringArray[n29].indexOf(" ") != -1) {
                                                stringArray[n29] = "..." + stringArray[n29].substring(stringArray[n29].indexOf(" "), stringArray[n29].length()) + "";
                                            }
                                            if (280 + n29 * 15 - n13 <= 266 || 280 + n29 * 15 - n13 >= 370) continue;
                                            this.rd.drawString(stringArray[n29], 325, 305 + n29 * 15 - n13);
                                        }
                                    } else {
                                        n11 = 0;
                                        n13 = 0;
                                        this.rd.drawString("Loading chat...", 465 - this.ftm.stringWidth("Loading chat...") / 2, 325);
                                    }
                                    this.rd.setColor(this.color2k(205, 205, 205));
                                    this.rd.fillRect(235, 269, 480, 25);
                                    this.rd.drawImage(this.xt.chat, 241, 273, null);
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.rd.setColor(this.color2k(120, 120, 120));
                                    this.rd.drawString("( Room Chat )", 299, 285);
                                    this.rd.setColor(this.color2k(150, 150, 150));
                                    this.rd.drawLine(235, 291, 696, 291);
                                    this.rd.setColor(this.color2k(205, 205, 205));
                                    this.rd.fillRect(235, 387, 480, 28);
                                    this.rd.setColor(this.color2k(150, 150, 150));
                                    this.rd.drawLine(235, 389, 696, 389);
                                    this.rd.setColor(this.color2k(205, 205, 205));
                                    this.rd.fillRect(698, 311, 17, 59);
                                    this.rd.setColor(this.color2k(205, 205, 205));
                                    this.rd.fillRect(231, 269, 4, 146);
                                    if (this.mscro3 == 351 || n11 == 0) {
                                        if (n11 == 0) {
                                            this.rd.setColor(this.color2k(205, 205, 205));
                                        } else {
                                            this.rd.setColor(this.color2k(215, 215, 215));
                                        }
                                        this.rd.fillRect(698, 294, 17, 17);
                                    } else {
                                        this.rd.setColor(this.color2k(220, 220, 220));
                                        this.rd.fill3DRect(698, 294, 17, 17, true);
                                    }
                                    if (n11 != 0) {
                                        this.rd.drawImage(this.xt.asu, 703, 300, null);
                                    }
                                    if (this.mscro3 == 352 || n11 == 0) {
                                        if (n11 == 0) {
                                            this.rd.setColor(this.color2k(205, 205, 205));
                                        } else {
                                            this.rd.setColor(this.color2k(215, 215, 215));
                                        }
                                        this.rd.fillRect(698, 370, 17, 17);
                                    } else {
                                        this.rd.setColor(this.color2k(220, 220, 220));
                                        this.rd.fill3DRect(698, 370, 17, 17, true);
                                    }
                                    if (n11 != 0) {
                                        this.rd.drawImage(this.xt.asd, 703, 376, null);
                                    }
                                    if (n11 != 0) {
                                        if (this.lspos3 != this.spos3) {
                                            this.rd.setColor(this.color2k(215, 215, 215));
                                            this.rd.fillRect(698, 311 + this.spos3, 17, 31);
                                        } else {
                                            if (this.mscro3 == 141) {
                                                this.rd.setColor(this.color2k(215, 215, 215));
                                            }
                                            this.rd.fill3DRect(698, 311 + this.spos3, 17, 31, true);
                                        }
                                        this.rd.setColor(this.color2k(150, 150, 150));
                                        this.rd.drawLine(703, 324 + this.spos3, 709, 324 + this.spos3);
                                        this.rd.drawLine(703, 326 + this.spos3, 709, 326 + this.spos3);
                                        this.rd.drawLine(703, 328 + this.spos3, 709, 328 + this.spos3);
                                        if (this.mscro3 > 342 && this.lspos3 != this.spos3) {
                                            this.lspos3 = this.spos3;
                                        }
                                        if (bl) {
                                            int n33;
                                            if (this.mscro3 == 345 && n > 698 && n < 715 && n2 > 311 + this.spos3 && n2 < this.spos3 + 342) {
                                                this.mscro3 = n2 - this.spos3;
                                            }
                                            if (this.mscro3 == 345 && n > 696 && n < 717 && n2 > 292 && n2 < 313) {
                                                this.mscro3 = 351;
                                            }
                                            if (this.mscro3 == 345 && n > 696 && n < 717 && n2 > 368 && n2 < 389) {
                                                this.mscro3 = 352;
                                            }
                                            if (this.mscro3 == 345 && n > 698 && n < 715 && n2 > 311 && n2 < 370) {
                                                this.mscro3 = 326;
                                                this.spos3 = n2 - this.mscro3;
                                            }
                                            if ((n33 = 100 / n11) < 1) {
                                                n33 = 1;
                                            }
                                            if (this.mscro3 == 351) {
                                                this.spos3 -= n33;
                                                if (this.spos3 > 28) {
                                                    this.spos3 = 28;
                                                }
                                                if (this.spos3 < 0) {
                                                    this.spos3 = 0;
                                                }
                                                this.lspos3 = this.spos3;
                                            }
                                            if (this.mscro3 == 352) {
                                                this.spos3 += n33;
                                                if (this.spos3 > 28) {
                                                    this.spos3 = 28;
                                                }
                                                if (this.spos3 < 0) {
                                                    this.spos3 = 0;
                                                }
                                                this.lspos3 = this.spos3;
                                            }
                                            if (this.mscro3 <= 342) {
                                                this.spos3 = n2 - this.mscro3;
                                                if (this.spos3 > 28) {
                                                    this.spos3 = 28;
                                                }
                                                if (this.spos3 < 0) {
                                                    this.spos3 = 0;
                                                }
                                            }
                                            if (this.mscro3 == 345) {
                                                this.mscro3 = 425;
                                            }
                                        } else if (this.mscro3 != 345) {
                                            this.mscro3 = 345;
                                        }
                                        if (n3 != 0 && n > 235 && n < 698 && n2 > 291 && n2 < 389) {
                                            this.spos3 -= n3 / 2;
                                            this.zeromsw = true;
                                            if (this.spos3 > 28) {
                                                this.spos3 = 28;
                                                this.zeromsw = false;
                                            }
                                            if (this.spos3 < 0) {
                                                this.spos3 = 0;
                                                this.zeromsw = false;
                                            }
                                            this.lspos3 = this.spos3;
                                        }
                                    }
                                    this.pre1 = true;
                                    this.stringbutton("Send Message", 657, 406, 3);
                                }
                                if ((this.chalngd == -1 || this.chalngd == -6) && this.lg.gamec != -1) {
                                    if (this.lg.cntgame >= 0 && this.lg.cntgame < 10) {
                                        this.rd.setComposite(AlphaComposite.getInstance(3, (float)this.lg.cntgame / 10.0f));
                                    }
                                    if (this.lg.cntgame > 390 && this.lg.cntgame < 400) {
                                        this.rd.setComposite(AlphaComposite.getInstance(3, (float)(400 - this.lg.cntgame) / 10.0f));
                                    }
                                    this.rd.setColor(this.color2k(245, 245, 245));
                                    this.rd.fillRoundRect(383, 242, 337, 46, 20, 20);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawRoundRect(383, 242, 337, 46, 20, 20);
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.drawString("" + this.lg.gmaker + " created a game in " + this.lg.gservern + " :: Room " + this.lg.groom + "", 551 - this.ftm.stringWidth("" + this.lg.gmaker + " created a game in " + this.lg.gservern + " :: Room " + this.lg.groom + "") / 2, 260);
                                    if (n > 488 && n < 614 && n2 > 264 && n2 < 287) {
                                        if (bl) {
                                            this.grprsd = true;
                                        } else if (this.grprsd) {
                                            for (int i = 0; i < this.lg.nservers; ++i) {
                                                if (!this.lg.gservern.equals(this.lg.snames[i])) continue;
                                                this.stopallnow();
                                                this.xt.server = this.lg.servers[i];
                                                this.xt.servername = this.lg.snames[i];
                                                this.xt.servport = 7070 + this.lg.groom;
                                                this.inishlobby();
                                                break;
                                            }
                                            this.grprsd = false;
                                        }
                                    } else if (this.grprsd) {
                                        this.grprsd = false;
                                    }
                                    if (!this.grprsd) {
                                        this.rd.setColor(this.color2k(230, 230, 230));
                                        this.rd.fill3DRect(490, 266, 122, 19, true);
                                        this.rd.setColor(new Color(0, 0, 0));
                                    } else {
                                        this.rd.setColor(this.color2k(230, 230, 230));
                                        this.rd.fillRect(490, 266, 122, 19);
                                        this.rd.setColor(this.color2k(60, 60, 60));
                                    }
                                    this.rd.drawString("View / Join Game", 551 - this.ftm.stringWidth("View / Join Game") / 2, 280);
                                    if (this.lg.cntgame >= 0 && this.lg.cntgame < 10 || this.lg.cntgame > 390 && this.lg.cntgame < 400) {
                                        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                                    }
                                    ++this.lg.cntgame;
                                    if (this.lg.cntgame == 400) {
                                        this.lg.gamec = -1;
                                    }
                                }
                            }
                        } else if (this.opengame < 26) {
                            n10 = 229 + this.opengame;
                            if (n10 > 255) {
                                n10 = 255;
                            }
                            if (n10 < 0) {
                                n10 = 0;
                            }
                            this.rd.setColor(this.color2k(n10, n10, n10));
                            this.rd.fillRoundRect(225, 59 - (int)((float)this.opengame * 2.23f), 495, 200 + this.opengame * 8, 20, 20);
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawRoundRect(225, 59 - (int)((float)this.opengame * 2.23f), 495, 200 + this.opengame * 8, 20, 20);
                            if (!this.xt.lan) {
                                this.rd.setColor(this.color2k(217, 217, 217));
                                this.rd.fillRoundRect(225, 263 + this.opengame * 7, 495, 157, 20, 20);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawRoundRect(225, 263 + this.opengame * 7, 495, 157, 20, 20);
                            }
                            this.btn = 0;
                            if (this.prevloaded != -1) {
                                this.prevloaded = -1;
                            }
                            if (this.updatec != -1) {
                                this.updatec = -1;
                            }
                            if (this.gs.cmsg.isShowing()) {
                                this.gs.cmsg.hide();
                                this.gs.requestFocus();
                            }
                            if (this.gs.rooms.isShowing()) {
                                this.gs.rooms.hide();
                            }
                            if (this.fstart) {
                                this.fstart = false;
                            }
                            for (n9 = 0; n9 < 9; ++n9) {
                                if (this.cac[n9] == -1) continue;
                                this.cac[n9] = -1;
                            }
                            if (this.dispcar != -1) {
                                this.dispcar = -1;
                            }
                            this.opengame += 2;
                        } else {
                            this.rd.setColor(this.color2k(255, 255, 255));
                            this.rd.fillRoundRect(225, 1, 495, 417, 20, 20);
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawRoundRect(225, 1, 495, 417, 20, 20);
                            if (this.join > -1 && this.pgames[this.im] != this.join || this.join == -2) {
                                if (this.join > -1 && this.pgames[this.im] != this.join) {
                                    this.rd.setFont(new Font("Arial", 1, 13));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.drawString(this.msg, 472 - this.ftm.stringWidth(this.msg) / 2, 195);
                                    if (this.msg.equals(". . . | Joining Game | . . .") && this.ncnt == 0) {
                                        this.msg = "| Joining Game |";
                                        this.ncnt = 5;
                                    }
                                    if (this.msg.equals(". . | Joining Game | . .") && this.ncnt == 0) {
                                        this.msg = ". . . | Joining Game | . . .";
                                        this.ncnt = 5;
                                    }
                                    if (this.msg.equals(". | Joining Game | .") && this.ncnt == 0) {
                                        this.msg = ". . | Joining Game | . .";
                                        this.ncnt = 5;
                                    }
                                    if (this.msg.equals("| Joining Game |") && this.ncnt == 0) {
                                        this.msg = ". | Joining Game | .";
                                        this.ncnt = 5;
                                    }
                                    if (this.ncnt != 0) {
                                        --this.ncnt;
                                    }
                                }
                                if (this.join == -2) {
                                    this.rd.setFont(new Font("Arial", 1, 13));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.drawString(this.msg, 472 - this.ftm.stringWidth(this.msg) / 2, 195);
                                    if (this.msg.equals(". . . | Leaving Game | . . .") && this.ncnt == 0) {
                                        this.msg = "| Leaving Game |";
                                        this.ncnt = 5;
                                    }
                                    if (this.msg.equals(". . | Leaving Game | . .") && this.ncnt == 0) {
                                        this.msg = ". . . | Leaving Game | . . .";
                                        this.ncnt = 5;
                                    }
                                    if (this.msg.equals(". | Leaving Game | .") && this.ncnt == 0) {
                                        this.msg = ". . | Leaving Game | . .";
                                        this.ncnt = 5;
                                    }
                                    if (this.msg.equals("| Leaving Game |") && this.ncnt == 0) {
                                        this.msg = ". | Leaving Game | .";
                                        this.ncnt = 5;
                                    }
                                    if (this.ncnt != 0) {
                                        --this.ncnt;
                                    }
                                }
                                if (this.gs.cmsg.isShowing()) {
                                    this.gs.cmsg.hide();
                                    this.gs.requestFocus();
                                }
                            } else {
                                int n34;
                                int n35;
                                int n36;
                                int n37;
                                int n38;
                                int n39;
                                int n40;
                                Object object;
                                n10 = 0;
                                for (n9 = 0; n9 < this.ngm; ++n9) {
                                    if (this.ongame != this.gnum[n9]) continue;
                                    n10 = n9;
                                }
                                this.rd.setFont(new Font("Arial", 1, 11));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.setColor(new Color(0, 0, 0));
                                n9 = 23;
                                n8 = 0;
                                if (this.gwarb[n10] != 0) {
                                    n9 = 28;
                                    n8 = 2;
                                    String string = "Clan war";
                                    if (this.gwarb[n10] == 2) {
                                        string = "Car battle";
                                    }
                                    if (this.gwarb[n10] == 3) {
                                        string = "Stage battle";
                                    }
                                    this.rd.drawString("" + string + " between " + this.gaclan[n10] + " and " + this.gvclan[n10] + "", 243, 14);
                                }
                                Object object2 = "";
                                String string = "";
                                String string2 = "";
                                if (this.conon == 1) {
                                    if (this.wait[n10] > 0) {
                                        String string3;
                                        if (this.gwarb[n10] == 0) {
                                            string3 = "";
                                            string3 = this.gplyrs[n10].equals("") ? "Public Game" : "Private Game";
                                            if (this.gfx[n10] == 1) {
                                                string3 = string3 + " | 4 Fixes";
                                            }
                                            if (this.gfx[n10] == 2) {
                                                string3 = string3 + " | 3 Fixes";
                                            }
                                            if (this.gfx[n10] == 3) {
                                                string3 = string3 + " | 2 Fixes";
                                            }
                                            if (this.gfx[n10] == 4) {
                                                string3 = string3 + " | 1 Fix";
                                            }
                                            if (this.gfx[n10] == 5) {
                                                string3 = string3 + " | No Fixing";
                                            }
                                            String string4 = "";
                                            if (this.gclss[n10] > -2) {
                                                if (this.gcrs[n10] == 1) {
                                                    string4 = "Custom Cars";
                                                }
                                                if (this.gcrs[n10] == 2) {
                                                    string4 = "Game Cars";
                                                }
                                                object = "";
                                                if (this.gclss[n10] == 1) {
                                                    object = "Class C";
                                                }
                                                if (this.gclss[n10] == 2) {
                                                    object = "Class B & C";
                                                }
                                                if (this.gclss[n10] == 3) {
                                                    object = "Class B";
                                                }
                                                if (this.gclss[n10] == 4) {
                                                    object = "Class A & B";
                                                }
                                                if (this.gclss[n10] == 5) {
                                                    object = "Class A";
                                                }
                                                string4 = !string4.equals("") && !((String)object).equals("") ? string4 + " | " + (String)object : string4 + (String)object;
                                            } else {
                                                string4 = "" + this.cd.names[Math.abs(this.gclss[n10] + 2)] + "";
                                            }
                                            if (string4.equals("")) {
                                                this.rd.drawString("Type:", 243, 23);
                                                this.rd.setColor(new Color(80, 128, 0));
                                                this.rd.drawString(string3, 279, 23);
                                            } else {
                                                this.rd.drawString("Type:", 243, 14);
                                                this.rd.drawString("Cars:", 244, 28);
                                                this.rd.setColor(new Color(80, 128, 0));
                                                this.rd.drawString(string3, 279, 14);
                                                this.rd.drawString(string4, 279, 28);
                                            }
                                        } else {
                                            string3 = "Game #" + this.gameturn[n10] + "";
                                            if (this.gcrs[n10] == 1) {
                                                string3 = string3 + " | Clan Cars";
                                            }
                                            if (this.gcrs[n10] == 2) {
                                                string3 = string3 + " | Game Cars";
                                            }
                                            if (this.gclss[n10] == 1) {
                                                string3 = string3 + " | Class C";
                                            }
                                            if (this.gclss[n10] == 2) {
                                                string3 = string3 + " | Class B & C";
                                            }
                                            if (this.gclss[n10] == 3) {
                                                string3 = string3 + " | Class B";
                                            }
                                            if (this.gclss[n10] == 4) {
                                                string3 = string3 + " | Class A & B";
                                            }
                                            if (this.gclss[n10] == 5) {
                                                string3 = string3 + " | Class A";
                                            }
                                            if (this.gfx[n10] == 1) {
                                                string3 = string3 + " | 4 Fixes";
                                            }
                                            if (this.gfx[n10] == 2) {
                                                string3 = string3 + " | 3 Fixes";
                                            }
                                            if (this.gfx[n10] == 3) {
                                                string3 = string3 + " | 2 Fixes";
                                            }
                                            if (this.gfx[n10] == 4) {
                                                string3 = string3 + " | 1 Fix";
                                            }
                                            if (this.gfx[n10] == 5) {
                                                string3 = string3 + " | No Fixing";
                                            }
                                            this.rd.setColor(new Color(80, 128, 0));
                                            this.rd.drawString(string3, 243, 28);
                                        }
                                    }
                                    if (this.wait[n10] == 0) {
                                        this.rd.drawString("Status:", 241 + n8, n9);
                                        this.rd.setColor(new Color(128, 73, 0));
                                        if (this.prevloaded == 0) {
                                            this.rd.drawString("Starting...", 286 + n8, n9);
                                        } else {
                                            this.rd.drawString("Started", 286 + n8, n9);
                                        }
                                    }
                                    if (this.wait[n10] == -1) {
                                        this.rd.drawString("Status:", 241 + n8, n9);
                                        this.rd.setColor(this.color2k(100, 100, 100));
                                        this.rd.drawString("Finished", 286 + n8, n9);
                                    }
                                } else {
                                    this.rd.drawString("Status:", 241 + n8, n9);
                                    this.rd.setColor(new Color(128, 73, 0));
                                    this.rd.drawString("Starting...", 286 + n8, n9);
                                }
                                if (this.gwarb[n10] == 0) {
                                    this.rd.setColor(new Color(0, 0, 0));
                                    if (this.gmaker[n10].equals(this.pnames[this.im])) {
                                        this.rd.drawString("Created by You", 449, 23);
                                    } else {
                                        this.rd.drawString("Created by", 449, 23);
                                        this.rd.drawString(":", 511, 23);
                                        this.rd.drawString(this.gmaker[n10], 520, 23);
                                    }
                                } else if (this.wait[n10] == 0 || this.wait[n10] == -1 || this.conon != 1) {
                                    this.rd.setColor(this.color2k(200, 200, 200));
                                    this.rd.drawRect(349, 16, 253, 16);
                                    this.rd.setFont(new Font("Arial", 0, 11));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.setColor(new Color(0, 0, 0));
                                    int n41 = 0;
                                    int n42 = 0;
                                    if (this.wait[n10] == -1) {
                                        object = new String[]{"", "", "", "", "", "", "", ""};
                                        for (n40 = 0; n40 < this.prnpo; ++n40) {
                                            for (n39 = 0; n39 < this.npo; ++n39) {
                                                if (!this.prnames[n40].equals(this.pnames[n39]) || this.pgames[n39] != this.gnum[n10]) continue;
                                                object[n40] = this.pclan[n39];
                                            }
                                        }
                                        for (n40 = 0; n40 < this.prnpo; ++n40) {
                                            if (this.ppos[n40] != 0) continue;
                                            object2 = object[n40];
                                            break;
                                        }
                                        if (!((String)object2).equals("")) {
                                            if (this.gwtyp[n10] == 2) {
                                                n40 = 0;
                                                for (n39 = 0; n39 < this.prnpo; ++n39) {
                                                    if (((String)object[n39]).toLowerCase().equals(((String)object2).toLowerCase()) || this.pdam[n39] >= 55 || this.pdam[n39] == -17) continue;
                                                    n40 = 1;
                                                    break;
                                                }
                                                if (n40 == 0) {
                                                    string2 = "" + (String)object2 + " should have raced in this game!";
                                                    object2 = "";
                                                }
                                            }
                                            if (this.gwtyp[n10] == 3) {
                                                n40 = 1;
                                                for (n39 = 0; n39 < this.prnpo; ++n39) {
                                                    if (((String)object[n39]).toLowerCase().equals(((String)object2).toLowerCase()) || this.pdam[n39] >= 55 || this.pdam[n39] == -17) continue;
                                                    n40 = 0;
                                                    break;
                                                }
                                                if (n40 == 0) {
                                                    string2 = "" + (String)object2 + " should have wasted in this game!";
                                                    object2 = "";
                                                }
                                            }
                                            if (this.gwtyp[n10] == 4) {
                                                if (((String)object2).toLowerCase().equals(this.gaclan[n10].toLowerCase())) {
                                                    n40 = 1;
                                                    for (n39 = 0; n39 < this.prnpo; ++n39) {
                                                        if (((String)object[n39]).toLowerCase().equals(((String)object2).toLowerCase()) || this.pdam[n39] >= 55 || this.pdam[n39] == -17) continue;
                                                        n40 = 0;
                                                        break;
                                                    }
                                                    if (n40 == 0) {
                                                        string2 = "" + (String)object2 + " should have wasted in this game!";
                                                        object2 = "";
                                                    }
                                                } else {
                                                    n40 = 0;
                                                    for (n39 = 0; n39 < this.prnpo; ++n39) {
                                                        if (((String)object[n39]).toLowerCase().equals(((String)object2).toLowerCase()) || this.pdam[n39] >= 55 || this.pdam[n39] == -17) continue;
                                                        n40 = 1;
                                                        break;
                                                    }
                                                    if (n40 == 0) {
                                                        string2 = "" + (String)object2 + " should have raced in this game!";
                                                        object2 = "";
                                                    }
                                                }
                                            }
                                            if (this.gwtyp[n10] == 5) {
                                                if (!((String)object2).toLowerCase().equals(this.gaclan[n10].toLowerCase())) {
                                                    n40 = 1;
                                                    for (n39 = 0; n39 < this.prnpo; ++n39) {
                                                        if (((String)object[n39]).toLowerCase().equals(((String)object2).toLowerCase()) || this.pdam[n39] >= 55 || this.pdam[n39] == -17) continue;
                                                        n40 = 0;
                                                        break;
                                                    }
                                                    if (n40 == 0) {
                                                        string2 = "" + (String)object2 + " should have wasted in this game!";
                                                        object2 = "";
                                                    }
                                                } else {
                                                    n40 = 0;
                                                    for (n39 = 0; n39 < this.prnpo; ++n39) {
                                                        if (((String)object[n39]).toLowerCase().equals(((String)object2).toLowerCase()) || this.pdam[n39] >= 55 || this.pdam[n39] == -17) continue;
                                                        n40 = 1;
                                                        break;
                                                    }
                                                    if (n40 == 0) {
                                                        string2 = "" + (String)object2 + " should have raced in this game!";
                                                        object2 = "";
                                                    }
                                                }
                                            }
                                        } else {
                                            string2 = "No one finished first - no one survived!";
                                        }
                                        if (((String)object2).toLowerCase().equals(this.gaclan[n10].toLowerCase())) {
                                            n41 = 1;
                                        }
                                        if (((String)object2).toLowerCase().equals(this.gvclan[n10].toLowerCase())) {
                                            n42 = 1;
                                        }
                                    }
                                    this.rd.drawString("" + this.gaclan[n10] + " : " + (this.gascore[n10] + n41) + "     |     " + this.gvclan[n10] + " : " + (this.gvscore[n10] + n42) + "", 474 - this.ftm.stringWidth("" + this.gaclan[n10] + " : " + this.gascore[n10] + "     |     " + this.gvclan[n10] + " : " + this.gvscore[n10] + "") / 2, 28);
                                    if (this.gwarb[n10] == 1) {
                                        if (this.gascore[n10] + n41 >= 5) {
                                            string = "" + this.gaclan[n10] + " wins the war!";
                                        }
                                        if (this.gvscore[n10] + n42 >= 5) {
                                            string = "" + this.gvclan[n10] + " wins the war!";
                                        }
                                    } else {
                                        if (this.gascore[n10] + n41 >= 3) {
                                            string = "" + this.gaclan[n10] + " wins the battle!";
                                        }
                                        if (this.gvscore[n10] + n42 >= 3) {
                                            string = "" + this.gvclan[n10] + " wins the battle!";
                                        }
                                    }
                                }
                                this.rd.setColor(this.color2k(200, 200, 200));
                                this.rd.drawLine(233, 32, 602, 32);
                                this.rd.drawLine(602, 7, 602, 32);
                                if (this.conon == 1) {
                                    if (this.pgames[this.im] == this.ongame) {
                                        this.stringbutton("Leave Game X", 660, 26, 0);
                                    } else {
                                        this.stringbutton("Close X", 679, 26, 0);
                                    }
                                }
                                this.rd.drawImage(this.xt.pls, 292, 39, null);
                                if (this.opengame != 27) {
                                    this.m.crs = true;
                                    this.m.x = -335;
                                    this.m.y = 0;
                                    this.m.z = -50;
                                    this.m.xz = 0;
                                    this.m.zy = 20;
                                    this.m.ground = -2000;
                                    this.pend = 0;
                                    this.pendb = false;
                                    this.ptime = 0L;
                                    this.opengame = 27;
                                }
                                int n43 = 0;
                                int n44 = -1;
                                for (n38 = 0; n38 < this.npo; ++n38) {
                                    if (this.pgames[n38] != this.ongame) continue;
                                    this.rd.setColor(this.color2k(240, 240, 240));
                                    if (n38 == this.im && this.wait[n10] != -1) {
                                        if (this.nflk == 0 || this.conon == 2) {
                                            this.rd.setColor(this.color2k(255, 255, 255));
                                            this.nflk = 3;
                                        } else {
                                            --this.nflk;
                                        }
                                    }
                                    this.rd.fillRect(237, 54 + n43 * 42, 170, 40);
                                    this.rd.setColor(this.color2k(200, 200, 200));
                                    if (this.gwarb[n10] != 0) {
                                        if (this.pclan[n38].toLowerCase().equals(this.gaclan[n10].toLowerCase())) {
                                            this.rd.setColor(new Color(255, 128, 0));
                                        }
                                        if (this.pclan[n38].toLowerCase().equals(this.gvclan[n10].toLowerCase())) {
                                            this.rd.setColor(new Color(0, 128, 255));
                                        }
                                        if (this.wait[n10] == -1 && this.prevloaded == 1 && ((String)object2).toLowerCase().equals(this.pclan[n38].toLowerCase()) && this.nflk == 0) {
                                            this.rd.setColor(new Color(0, 0, 0));
                                        }
                                    } else if (this.wait[n10] == -1 && this.prevloaded == 1) {
                                        for (n40 = 0; n40 < this.prnpo; ++n40) {
                                            if (!this.pnames[n38].equals(this.prnames[n40]) || this.ppos[n40] != 0 || this.nflk != 0) continue;
                                            this.rd.setColor(new Color(0, 0, 0));
                                        }
                                    }
                                    this.rd.drawRect(237, 54 + n43 * 42, 170, 40);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.drawString(this.pnames[n38], 282 - this.ftm.stringWidth(this.pnames[n38]) / 2, 72 + n43 * 42);
                                    this.rd.setFont(new Font("Arial", 0, 10));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.drawString(this.pcarnames[n38], 282 - this.ftm.stringWidth(this.pcarnames[n38]) / 2, 84 + n43 * 42);
                                    this.m.crs = true;
                                    this.m.x = -335;
                                    this.m.y = 0;
                                    this.m.z = -50;
                                    this.m.xz = 0;
                                    this.m.zy = 20;
                                    this.m.ground = -2000;
                                    if (this.pcars[n38] != -1) {
                                        for (n40 = 0; n40 < contOArray[this.pcars[n38]].npl; ++n40) {
                                            contOArray[this.pcars[n38]].p[n40].flx = 0;
                                            if (contOArray[this.pcars[n38]].p[n40].colnum == 1) {
                                                contOArray[this.pcars[n38]].p[n40].hsb[0] = this.pcols[n38][0];
                                                contOArray[this.pcars[n38]].p[n40].hsb[1] = this.pcols[n38][1];
                                                contOArray[this.pcars[n38]].p[n40].hsb[2] = 1.0f - this.pcols[n38][2];
                                            }
                                            if (contOArray[this.pcars[n38]].p[n40].colnum != 2) continue;
                                            contOArray[this.pcars[n38]].p[n40].hsb[0] = this.pcols[n38][3];
                                            contOArray[this.pcars[n38]].p[n40].hsb[1] = this.pcols[n38][4];
                                            contOArray[this.pcars[n38]].p[n40].hsb[2] = 1.0f - this.pcols[n38][5];
                                        }
                                        if (this.cac[n43] != this.pcars[n38]) {
                                            n39 = n40 = contOArray[this.pcars[n38]].p[0].oz[0];
                                            n36 = n37 = contOArray[this.pcars[n38]].p[0].oy[0];
                                            for (n35 = 0; n35 < contOArray[this.pcars[n38]].npl; ++n35) {
                                                for (n34 = 0; n34 < contOArray[this.pcars[n38]].p[n35].n; ++n34) {
                                                    if (contOArray[this.pcars[n38]].p[n35].oz[n34] < n40) {
                                                        n40 = contOArray[this.pcars[n38]].p[n35].oz[n34];
                                                    }
                                                    if (contOArray[this.pcars[n38]].p[n35].oz[n34] > n39) {
                                                        n39 = contOArray[this.pcars[n38]].p[n35].oz[n34];
                                                    }
                                                    if (contOArray[this.pcars[n38]].p[n35].oy[n34] < n37) {
                                                        n37 = contOArray[this.pcars[n38]].p[n35].oy[n34];
                                                    }
                                                    if (contOArray[this.pcars[n38]].p[n35].oy[n34] <= n36) continue;
                                                    n36 = contOArray[this.pcars[n38]].p[n35].oy[n34];
                                                }
                                            }
                                            this.cax[n43] = (n39 + n40) / 2;
                                            this.cay[n43] = (n36 + n37) / 2;
                                            this.cac[n43] = this.pcars[n38];
                                        }
                                        if (n > 327 && n < 402 && n2 > 57 + n43 * 42 && n2 < 91 + n43 * 42) {
                                            n4 = 12;
                                            n44 = n38;
                                            for (n40 = 0; n40 < contOArray[this.pcars[n38]].npl; ++n40) {
                                                contOArray[this.pcars[n38]].p[n40].flx = 77;
                                            }
                                        }
                                        contOArray[this.pcars[n38]].z = 2500 - n43 * 80;
                                        contOArray[this.pcars[n38]].y = 150 + 250 * n43 - this.cay[n43];
                                        contOArray[this.pcars[n38]].x = -145 - this.cax[n43];
                                        contOArray[this.pcars[n38]].zy = 0;
                                        contOArray[this.pcars[n38]].xz = -90;
                                        contOArray[this.pcars[n38]].xy = this.pend - n43 * 5;
                                        this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                                        contOArray[this.pcars[n38]].d(this.rd);
                                        this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                    } else {
                                        this.rd.setFont(new Font("Arial", 1, 11));
                                        this.rd.setColor(this.color2k(80, 80, 80));
                                        this.rd.drawString("Loading...", 339, 77 + n43 * 42);
                                    }
                                    ++n43;
                                }
                                if (!this.pendb) {
                                    this.pend += 2;
                                    if (this.pend > 80) {
                                        this.pendb = true;
                                    }
                                } else {
                                    this.pend -= 2;
                                    if (this.pend < -10) {
                                        this.pendb = false;
                                    }
                                }
                                if (n44 != -1) {
                                    if (bl) {
                                        this.mousedout = true;
                                    } else if (this.mousedout) {
                                        if (this.dispcar != n44 && n44 != -1 && this.cd.action != 6) {
                                            this.cd.action = 0;
                                            this.dispcar = n44;
                                            this.forcar = this.pcars[n44];
                                            this.dispco = null;
                                            System.gc();
                                            this.dispco = new ContO(contOArray[this.forcar], 0, 0, 0, 0);
                                        } else {
                                            this.dispcar = -1;
                                        }
                                        this.mousedout = false;
                                    }
                                } else if (this.mousedout) {
                                    this.mousedout = false;
                                }
                                for (n38 = 0; n38 < 7; ++n38) {
                                    for (n40 = 0; n40 < this.npo; ++n40) {
                                        if (this.pgames[n40] != this.ongame || !this.invos[n38].equals(this.pnames[n40]) || !this.dinvi[n38].equals(this.invos[n38])) continue;
                                        for (n39 = n38; n39 < 6; ++n39) {
                                            this.invos[n39] = this.invos[n39 + 1];
                                            this.dinvi[n39] = this.dinvi[n39 + 1];
                                        }
                                        this.invos[6] = "";
                                        this.dinvi[6] = "";
                                    }
                                }
                                if (this.wait[n10] > 0) {
                                    n38 = 0;
                                    for (n40 = n43; n40 < this.mnpls[n10]; ++n40) {
                                        this.rd.setColor(this.color2k(200, 200, 200));
                                        this.rd.drawRect(237, 54 + n40 * 42, 170, 40);
                                        n39 = 0;
                                        if (this.pgames[this.im] == this.ongame) {
                                            if (!this.gplyrs[n10].equals("")) {
                                                n39 = 1;
                                            }
                                        } else if (this.gwarb[n10] == 0) {
                                            if (!this.gplyrs[n10].equals("") && this.gplyrs[n10].indexOf(this.pnames[this.im]) == -1) {
                                                n39 = 1;
                                            }
                                        } else if (!this.xt.clan.toLowerCase().equals(this.gaclan[n10].toLowerCase()) && !this.xt.clan.toLowerCase().equals(this.gvclan[n10].toLowerCase())) {
                                            n39 = 1;
                                        }
                                        if (n > 237 && n < 407 && n2 > 54 + n40 * 42 && n2 < 94 + n40 * 42 && n39 == 0) {
                                            if (this.pgames[this.im] == this.ongame) {
                                                this.stringbutton("<     Invite Player      ", 322, 79 + n40 * 42, 0);
                                            } else {
                                                this.stringbutton("    Join Game    ", 322, 79 + n40 * 42, 0);
                                            }
                                            this.pbtn = 1;
                                        } else if (this.invos[n38].equals("")) {
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString("Empty", 322 - this.ftm.stringWidth("Empty") / 2, 72 + n40 * 42);
                                            this.rd.setFont(new Font("Arial", 0, 10));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString("Waiting for player...", 322 - this.ftm.stringWidth("Waiting for player...") / 2, 84 + n40 * 42);
                                        } else if (!this.dinvi[n38].equals(this.invos[n38])) {
                                            if (this.nflk != 0) {
                                                this.rd.setColor(new Color(0, 0, 0));
                                                this.rd.setFont(new Font("Arial", 0, 12));
                                                this.ftm = this.rd.getFontMetrics();
                                                this.rd.drawString("Inviting player...", 322 - this.ftm.stringWidth("Inviting player...") / 2, 79 + n40 * 42);
                                            }
                                        } else {
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.setFont(new Font("Arial", 0, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString("Player Invited!", 322 - this.ftm.stringWidth("Player Invited!") / 2, 71 + n40 * 42);
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString(this.invos[n38], 322 - this.ftm.stringWidth(this.invos[n38]) / 2, 87 + n40 * 42);
                                        }
                                        ++n38;
                                    }
                                }
                                if (this.xt.lan && this.mnbts[n10] != 0) {
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.setFont(new Font("Arial", 1, 12));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.drawString("Plus " + this.mnbts[n10] + " MadBots!", 322 - this.ftm.stringWidth("Plus " + this.mnbts[n10] + " MadBots!") / 2, 73 + this.mnpls[n10] * 42);
                                }
                                if (this.dispcar == -1 || this.conon != 1) {
                                    this.rd.drawImage(this.xt.sts, 537, 39, null);
                                    this.rd.setColor(this.color2k(200, 200, 200));
                                    this.rd.drawRect(415, 54, 293, 166);
                                    if (this.conon == 1) {
                                        if (this.wait[n10] > 0) {
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            if (this.gwarb[n10] == 0) {
                                                Object object3;
                                                if (this.wait[n10] > 30 || this.npls[n10] <= 1) {
                                                    object3 = "s";
                                                    if (this.mnpls[n10] - this.npls[n10] == 1) {
                                                        object3 = "";
                                                    }
                                                    this.rd.drawString("Waiting for " + (this.mnpls[n10] - this.npls[n10]) + " more player" + (String)object3 + " to join to start.", 561 - this.ftm.stringWidth("Waiting for " + (this.mnpls[n10] - this.npls[n10]) + " more player" + (String)object3 + " to join to start.") / 2, 98);
                                                    this.rd.setFont(new Font("Arial", 0, 12));
                                                    this.ftm = this.rd.getFontMetrics();
                                                    n40 = 134;
                                                    if (!this.gmaker[n10].equals("Coach Insano") && !this.gmaker[n10].equals(this.pnames[this.im])) {
                                                        n39 = 0;
                                                        for (n37 = 0; n37 < this.npo; ++n37) {
                                                            if (this.pgames[n37] != this.ongame || !this.gmaker[n10].equals(this.pnames[n37])) continue;
                                                            n39 = 1;
                                                        }
                                                        if (n39 != 0) {
                                                            n40 = 144;
                                                            this.rd.drawString("" + this.gmaker[n10] + " can start this game at anytime.", 561 - this.ftm.stringWidth("" + this.gmaker[n10] + " can start this game at anytime.") / 2, 124);
                                                        }
                                                    }
                                                    if (this.npls[n10] > 1) {
                                                        String string5 = "" + this.wait[n10] + " seconds";
                                                        if (this.wait[n10] > 60) {
                                                            string5 = "" + (float)((int)((float)this.wait[n10] / 60.0f * 100.0f)) / 100.0f + " minutes";
                                                        }
                                                        this.rd.drawString("( Waiting " + string5 + " maximum! )", 561 - this.ftm.stringWidth("( Waiting " + string5 + " maximum! )") / 2, n40);
                                                    }
                                                } else {
                                                    object3 = new Date();
                                                    long l = ((Date)object3).getTime();
                                                    if (this.ptime == 0L || l > this.ptime + 1500L) {
                                                        if (this.ptime != 0L) {
                                                            int n45 = n10;
                                                            this.wait[n45] = this.wait[n45] - 1;
                                                            if (this.wait[n10] < 1) {
                                                                this.wait[n10] = 1;
                                                            }
                                                        }
                                                        this.ptime = l;
                                                    }
                                                    if (this.pgames[this.im] == this.ongame || this.nflk != 0) {
                                                        this.rd.drawString("Game starts in " + this.wait[n10] + " seconds!", 561 - this.ftm.stringWidth("Game starts in " + this.wait[n10] + " seconds!") / 2, 124);
                                                        if (this.pgames[this.im] != this.ongame) {
                                                            --this.nflk;
                                                        }
                                                    } else if (this.pgames[this.im] != this.ongame) {
                                                        this.nflk = 3;
                                                    }
                                                }
                                                if (this.pgames[this.im] != this.ongame) {
                                                    if (this.gplyrs[n10].equals("") || this.gplyrs[n10].indexOf(this.pnames[this.im]) != -1) {
                                                        this.stringbutton("    Join this Game    ", 561, 182, 0);
                                                    } else {
                                                        this.rd.setFont(new Font("Arial", 1, 12));
                                                        this.ftm = this.rd.getFontMetrics();
                                                        this.rd.setColor(new Color(128, 73, 0));
                                                        this.rd.drawString("Private Game, only specific players allowed.", 561 - this.ftm.stringWidth("Private Game, only specific players allowed.") / 2, 180);
                                                        this.stringbutton("    Join this Game    ", 561, -1000, 0);
                                                    }
                                                } else if (this.gmaker[n10].equals(this.pnames[this.im])) {
                                                    if (this.npls[n10] > 1) {
                                                        if (!this.fstart) {
                                                            this.stringbutton("    Start this Game Now!    ", 561, 182, 0);
                                                        } else {
                                                            this.stringbutton("    Starting game now, one moment...    ", 561, 182, 0);
                                                        }
                                                    } else {
                                                        this.rd.setFont(new Font("Arial", 1, 12));
                                                        this.ftm = this.rd.getFontMetrics();
                                                        this.rd.setColor(new Color(128, 73, 0));
                                                        this.rd.drawString("You have created this game.", 561 - this.ftm.stringWidth("You have created this game.") / 2, 180);
                                                    }
                                                } else {
                                                    this.rd.setFont(new Font("Arial", 1, 12));
                                                    this.ftm = this.rd.getFontMetrics();
                                                    this.rd.setColor(new Color(80, 128, 0));
                                                    this.rd.drawString("You have joined this game.", 561 - this.ftm.stringWidth("You have joined this game.") / 2, 180);
                                                }
                                            } else {
                                                String string6 = "s";
                                                if (this.mnpls[n10] - this.npls[n10] == 1) {
                                                    string6 = "";
                                                }
                                                this.rd.drawString("Waiting for " + (this.mnpls[n10] - this.npls[n10]) + " clan member" + string6 + " to join to start.", 561 - this.ftm.stringWidth("Waiting for " + (this.mnpls[n10] - this.npls[n10]) + " clan member" + string6 + " to join to start.") / 2, 72);
                                                n40 = this.mnpls[n10] / 2;
                                                n39 = this.mnpls[n10] / 2;
                                                for (n37 = 0; n37 < this.npo; ++n37) {
                                                    if (this.pgames[n37] != this.gnum[n10]) continue;
                                                    if (this.pclan[n37].toLowerCase().equals(this.gaclan[n10].toLowerCase()) && --n40 < 0) {
                                                        n40 = 0;
                                                    }
                                                    if (!this.pclan[n37].toLowerCase().equals(this.gvclan[n10].toLowerCase()) || --n39 >= 0) continue;
                                                    n39 = 0;
                                                }
                                                this.rd.setFont(new Font("Arial", 0, 12));
                                                this.ftm = this.rd.getFontMetrics();
                                                this.rd.drawString("( " + n40 + " of " + this.gaclan[n10] + "  &  " + n39 + " of " + this.gvclan[n10] + " )", 561 - this.ftm.stringWidth("( " + n40 + " of " + this.gaclan[n10] + "  &  " + n39 + " of " + this.gvclan[n10] + " )") / 2, 87);
                                                this.rd.drawString(this.gaclan[n10], 491 - this.ftm.stringWidth(this.gaclan[n10]) / 2, 125);
                                                this.rd.drawString(this.gvclan[n10], 631 - this.ftm.stringWidth(this.gvclan[n10]) / 2, 125);
                                                this.rd.setFont(new Font("Arial", 1, 12));
                                                this.ftm = this.rd.getFontMetrics();
                                                String string7 = "War";
                                                if (this.gwarb[n10] > 1) {
                                                    string7 = "Battle";
                                                }
                                                this.rd.drawString("" + string7 + " Score", 561 - this.ftm.stringWidth("" + string7 + " Score") / 2, 107);
                                                this.rd.drawString("" + this.gascore[n10] + "", 491 - this.ftm.stringWidth("" + this.gascore[n10] + "") / 2, 139);
                                                this.rd.drawString("" + this.gvscore[n10] + "", 631 - this.ftm.stringWidth("" + this.gascore[n10] + "") / 2, 139);
                                                this.rd.drawRect(421, 111, 280, 33);
                                                this.rd.drawLine(561, 111, 561, 144);
                                                this.rd.setColor(new Color(255, 128, 0));
                                                this.rd.drawRect(422, 112, 138, 31);
                                                this.rd.setColor(new Color(0, 128, 255));
                                                this.rd.drawRect(562, 112, 138, 31);
                                                if (this.pgames[this.im] != this.ongame) {
                                                    if (this.xt.clan.toLowerCase().equals(this.gaclan[n10].toLowerCase()) || this.xt.clan.toLowerCase().equals(this.gvclan[n10].toLowerCase())) {
                                                        this.stringbutton("    Join this Game    ", 561, 200, 0);
                                                    } else {
                                                        this.rd.setColor(new Color(128, 73, 0));
                                                        this.rd.drawString("You must be a member of either clan to join.", 561 - this.ftm.stringWidth("You must be a member of either clan to join.") / 2, 198);
                                                    }
                                                } else {
                                                    if (this.gmaker[n10].equals(this.pnames[this.im]) && this.npls[n10] > 1) {
                                                        this.stringbutton("    Start this Game Now!    ", 561, -1000, 0);
                                                    }
                                                    this.rd.setColor(new Color(80, 128, 0));
                                                    this.rd.drawString("You have joined this game.", 561 - this.ftm.stringWidth("You have joined this game.") / 2, 198);
                                                }
                                                this.rd.setFont(new Font("Arial", 1, 12));
                                                this.ftm = this.rd.getFontMetrics();
                                                if (this.gwtyp[n10] == 1) {
                                                    this.rd.setColor(new Color(0, 0, 0));
                                                    this.rd.drawString("This is a normal clan game.", 561 - this.ftm.stringWidth("This is a normal clan game.") / 2, 161);
                                                    this.rd.setFont(new Font("Arial", 0, 12));
                                                    this.ftm = this.rd.getFontMetrics();
                                                    this.rd.setColor(new Color(0, 0, 0));
                                                    this.rd.drawString("Any clan can win by racing or wasting.", 561 - this.ftm.stringWidth("Any clan can win by racing or wasting.") / 2, 176);
                                                }
                                                if (this.gwtyp[n10] == 2) {
                                                    if (this.sflk != 0) {
                                                        --this.sflk;
                                                        this.rd.setColor(new Color(0, 0, 0));
                                                    } else {
                                                        this.sflk = 3;
                                                        this.rd.setColor(new Color(117, 67, 0));
                                                    }
                                                    this.rd.drawString("This is a racing only game!", 561 - this.ftm.stringWidth("This is a racing only game!") / 2, 161);
                                                    this.rd.setFont(new Font("Arial", 0, 12));
                                                    this.ftm = this.rd.getFontMetrics();
                                                    this.rd.setColor(new Color(0, 0, 0));
                                                    this.rd.drawString("A clan can only win by racing.", 561 - this.ftm.stringWidth("A clan can only win by racing.") / 2, 176);
                                                }
                                                if (this.gwtyp[n10] == 3) {
                                                    if (this.sflk != 0) {
                                                        --this.sflk;
                                                        this.rd.setColor(new Color(0, 0, 0));
                                                    } else {
                                                        this.sflk = 3;
                                                        this.rd.setColor(new Color(117, 67, 0));
                                                    }
                                                    this.rd.drawString("This is a wasting only game!", 561 - this.ftm.stringWidth("This is a wasting only game!") / 2, 161);
                                                    this.rd.setFont(new Font("Arial", 0, 12));
                                                    this.ftm = this.rd.getFontMetrics();
                                                    this.rd.setColor(new Color(0, 0, 0));
                                                    this.rd.drawString("A clan can only win by wasting.", 561 - this.ftm.stringWidth("A clan can only win by wasting.") / 2, 176);
                                                }
                                                if (this.gwtyp[n10] == 4) {
                                                    if (this.sflk != 0) {
                                                        --this.sflk;
                                                        this.rd.setColor(new Color(0, 0, 0));
                                                    } else {
                                                        this.sflk = 3;
                                                        this.rd.setColor(new Color(117, 67, 0));
                                                    }
                                                    this.rd.drawString("This is Racers VS Wasters game!", 561 - this.ftm.stringWidth("This is Racers VS Wasters game!") / 2, 161);
                                                    this.rd.drawString("" + this.gaclan[n10] + " wastes & " + this.gvclan[n10] + " races.", 561 - this.ftm.stringWidth("" + this.gaclan[n10] + " wastes & " + this.gvclan[n10] + " races.") / 2, 176);
                                                }
                                                if (this.gwtyp[n10] == 5) {
                                                    if (this.sflk != 0) {
                                                        --this.sflk;
                                                        this.rd.setColor(new Color(0, 0, 0));
                                                    } else {
                                                        this.sflk = 3;
                                                        this.rd.setColor(new Color(117, 67, 0));
                                                    }
                                                    this.rd.drawString("This is Racers VS Wasters game!", 561 - this.ftm.stringWidth("This is Racers VS Wasters game!") / 2, 161);
                                                    this.rd.drawString("" + this.gaclan[n10] + " races & " + this.gvclan[n10] + " wastes.", 561 - this.ftm.stringWidth("" + this.gaclan[n10] + " races & " + this.gvclan[n10] + " wastes.") / 2, 176);
                                                }
                                            }
                                        } else if (this.prevloaded == 1) {
                                            n38 = (int)(80.0 + (double)this.rerr / 1.243);
                                            if (n38 > 255) {
                                                n38 = 255;
                                            }
                                            if (n38 < 0) {
                                                n38 = 0;
                                            }
                                            n40 = (int)(128.0 + (double)this.rerr / 2.428);
                                            if (n40 > 255) {
                                                n40 = 255;
                                            }
                                            if (n40 < 0) {
                                                n40 = 0;
                                            }
                                            n39 = this.rerr;
                                            if (n39 > 255) {
                                                n39 = 255;
                                            }
                                            if (n39 < 0) {
                                                n39 = 0;
                                            }
                                            if (this.wait[n10] == 0) {
                                                this.rd.setColor(new Color(n38, n40, n39));
                                                this.rd.setFont(new Font("Arial", 1, 12));
                                                this.rd.drawString("Live Info!", 621, 51);
                                                this.rd.drawString("Live Info!", 451, 51);
                                            }
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.setFont(new Font("Tahoma", 1, 11));
                                            this.rd.drawString("Player       Position   Laps      Power        Damage", 439, 69);
                                            this.rd.setColor(this.color2k(200, 200, 200));
                                            this.rd.drawLine(489, 61, 489, 162);
                                            this.rd.drawLine(542, 61, 542, 162);
                                            this.rd.drawLine(579, 61, 579, 162);
                                            this.rd.drawLine(641, 61, 641, 162);
                                            this.rd.drawLine(422, 72, 702, 72);
                                            this.rd.drawLine(422, 163, 702, 163);
                                            n43 = 0;
                                            for (n37 = 0; n37 < this.prnpo; ++n37) {
                                                for (n36 = 0; n36 < this.prnpo; ++n36) {
                                                    if (this.ppos[n36] != n37) continue;
                                                    this.rd.setFont(new Font("Tahoma", 0, 11));
                                                    this.ftm = this.rd.getFontMetrics();
                                                    this.rd.setColor(new Color(0, 44, 124));
                                                    this.rd.drawString(this.prnames[n36], 455 - this.ftm.stringWidth(this.prnames[n36]) / 2, 83 + 11 * n43);
                                                    if (this.pdam[n36] < 55 && this.pdam[n36] != -17) {
                                                        this.rd.setColor(new Color(80, 128, 0));
                                                        String string8 = "th";
                                                        if (this.ppos[n36] == 0) {
                                                            string8 = "st";
                                                        }
                                                        if (this.ppos[n36] == 1) {
                                                            string8 = "nd";
                                                        }
                                                        if (this.ppos[n36] == 2) {
                                                            string8 = "rd";
                                                        }
                                                        this.rd.drawString("" + (this.ppos[n36] + 1) + "" + string8, 515 - this.ftm.stringWidth("" + (this.ppos[n36] + 1) + "" + string8) / 2, 83 + 11 * n43);
                                                        this.rd.setColor(new Color(128, 73, 0));
                                                        if (this.plap[n36] > this.gnlaps[n10] - 1) {
                                                            this.plap[n36] = this.gnlaps[n10] - 1;
                                                        }
                                                        this.rd.drawString("" + (this.plap[n36] + 1) + " / " + this.gnlaps[n10] + "", 560 - this.ftm.stringWidth("" + (this.plap[n36] + 1) + " / " + this.gnlaps[n10] + "") / 2, 83 + 11 * n43);
                                                        this.rd.setColor(new Color(0, 128, 255));
                                                        this.rd.drawRect(582, 76 + 11 * n43, 56, 6);
                                                        this.rd.fillRect(583, 79 + 11 * n43, this.ppow[n36], 3);
                                                        this.rd.setColor(new Color(128, 210, 255));
                                                        this.rd.fillRect(583, 77 + 11 * n43, this.ppow[n36], 2);
                                                        this.rd.setColor(new Color(255, 0, 0));
                                                        this.rd.drawRect(645, 76 + 11 * n43, 56, 6);
                                                        this.rd.fillRect(646, 79 + 11 * n43, this.pdam[n36], 3);
                                                        this.rd.setColor(new Color(255, 155, 64));
                                                        this.rd.fillRect(646, 77 + 11 * n43, this.pdam[n36], 2);
                                                    } else {
                                                        n38 = (int)(85.0 + (double)(this.rerr * 2) / 1.5);
                                                        if (n38 > 255) {
                                                            n38 = 255;
                                                        }
                                                        if (n38 < 0) {
                                                            n38 = 0;
                                                        }
                                                        this.rd.setColor(this.color2k(n38, n38, n38));
                                                        this.rd.fillRect(490, 75 + 11 * n43, 213, 9);
                                                        this.rd.setFont(new Font("Tahoma", 1, 11));
                                                        this.ftm = this.rd.getFontMetrics();
                                                        n38 = 255 - this.rerr * 2;
                                                        if (n38 > 255) {
                                                            n38 = 255;
                                                        }
                                                        if (n38 < 0) {
                                                            n38 = 0;
                                                        }
                                                        n40 = (int)(155.0 - (double)(this.rerr * 2) / 1.645);
                                                        if (n40 > 255) {
                                                            n40 = 255;
                                                        }
                                                        if (n40 < 0) {
                                                            n40 = 0;
                                                        }
                                                        n39 = (int)(64.0 - (double)(this.rerr * 2) / 3.984);
                                                        if (n39 > 255) {
                                                            n39 = 255;
                                                        }
                                                        if (n39 < 0) {
                                                            n39 = 0;
                                                        }
                                                        this.rd.setColor(new Color(n38, n40, n39));
                                                        if (this.pdam[n36] != -17) {
                                                            this.rd.drawString("=   =   =   =    W A S T E D    =   =   =   =", 597 - this.ftm.stringWidth("=   =   =   =    W A S T E D    =   =   =   =") / 2, 84 + 11 * n43);
                                                        } else {
                                                            this.rd.drawString("=   =   =   DISCONNECTED   =   =   =", 597 - this.ftm.stringWidth("=   =   =   DISCONNECTED   =   =   =") / 2, 84 + 11 * n43);
                                                        }
                                                    }
                                                    ++n43;
                                                }
                                            }
                                            if (this.wait[n10] == 0) {
                                                this.stringbutton("    Watch Live Now!    ", 561, 196, 0);
                                            } else {
                                                this.rd.setColor(new Color(0, 0, 0));
                                                this.rd.setFont(new Font("Arial", 1, 12));
                                                this.ftm = this.rd.getFontMetrics();
                                                n37 = 186;
                                                if (!(this.lapsname.equals("") || this.wastename.equals("") || this.stuntname.equals(""))) {
                                                    n37 = 183;
                                                }
                                                if (this.gwarb[n10] == 0) {
                                                    String string9 = "";
                                                    for (int i = 0; i < this.prnpo; ++i) {
                                                        if (this.ppos[i] != 0) continue;
                                                        string9 = this.prnames[i];
                                                        break;
                                                    }
                                                    if (string9.equals("")) {
                                                        this.rd.drawString("Game Finished!    Nobody Won!", 561 - this.ftm.stringWidth("Game Finished!    Nobody Won!") / 2, n37);
                                                    } else {
                                                        this.rd.drawString("Game Finished!    Winner:  " + string9 + "", 561 - this.ftm.stringWidth("Game Finished!    Winner:  " + string9 + "") / 2, n37);
                                                        if (this.nflk == 0) {
                                                            this.rd.setColor(new Color(255, 176, 67));
                                                            this.nflk = 3;
                                                        } else {
                                                            --this.nflk;
                                                        }
                                                        this.rd.drawString("" + string9 + "", 561 - this.ftm.stringWidth("Game Finished!    Winner:  " + string9 + "") / 2 + this.ftm.stringWidth("Game Finished!    Winner:  "), n37);
                                                    }
                                                    this.rd.setColor(new Color(0, 0, 0));
                                                    this.rd.setFont(new Font("Arial", 0, 11));
                                                    this.ftm = this.rd.getFontMetrics();
                                                    String string10 = "    ";
                                                    n34 = 0;
                                                    if (!this.lapsname.equals("")) {
                                                        string10 = string10 + "Fastest lap: " + this.lapsname + "    ";
                                                        ++n34;
                                                    }
                                                    if (!this.wastename.equals("")) {
                                                        string10 = string10 + "Deadliest waster: " + this.wastename + "    ";
                                                        ++n34;
                                                    }
                                                    if (n34 == 2) {
                                                        if (!this.stuntname.equals("")) {
                                                            this.rd.drawString(string10, 561 - this.ftm.stringWidth(string10) / 2, 199);
                                                            this.rd.drawString("Best stunt: " + this.stuntname + "", 561 - this.ftm.stringWidth("Best stunt: " + this.stuntname + "") / 2, 213);
                                                        } else {
                                                            this.rd.drawString(string10, 561 - this.ftm.stringWidth(string10) / 2, 206);
                                                        }
                                                    } else {
                                                        if (!this.stuntname.equals("")) {
                                                            string10 = string10 + "Best stunt: " + this.stuntname + "    ";
                                                        }
                                                        this.rd.drawString(string10, 561 - this.ftm.stringWidth(string10) / 2, 206);
                                                    }
                                                } else if (string.equals("")) {
                                                    if (((String)object2).equals("")) {
                                                        this.rd.drawString("Game Finished!    Nobody Won!", 561 - this.ftm.stringWidth("Game Finished!    Nobody Won!") / 2, 186);
                                                        this.rd.setFont(new Font("Arial", 1, 11));
                                                        this.ftm = this.rd.getFontMetrics();
                                                        this.rd.drawString(string2, 561 - this.ftm.stringWidth(string2) / 2, 206);
                                                    } else {
                                                        this.rd.drawString("Game Finished!    " + (String)object2 + "  Wins!", 561 - this.ftm.stringWidth("Game Finished!    " + (String)object2 + "  Wins!") / 2, 196);
                                                        if (this.nflk == 0) {
                                                            this.rd.setColor(new Color(255, 176, 67));
                                                            this.nflk = 3;
                                                        } else {
                                                            --this.nflk;
                                                        }
                                                        this.rd.drawString("" + (String)object2 + "", 561 - this.ftm.stringWidth("Game Finished!    " + (String)object2 + "  Wins!") / 2 + this.ftm.stringWidth("Game Finished!    "), 196);
                                                    }
                                                } else {
                                                    if (this.nflk == 0) {
                                                        this.rd.setColor(new Color(255, 176, 67));
                                                        this.nflk = 3;
                                                    } else {
                                                        --this.nflk;
                                                    }
                                                    this.rd.drawString(string, 561 - this.ftm.stringWidth(string) / 2, 196);
                                                }
                                            }
                                            this.rerr += 3;
                                        } else {
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            if (this.prevloaded == -1) {
                                                if (!this.xt.lan) {
                                                    this.rd.drawString("Loading Info...", 561 - this.ftm.stringWidth("Loading Info...") / 2, 134);
                                                } else if (this.pgames[this.im] == this.ongame) {
                                                    this.rd.drawString("About to Start...", 561 - this.ftm.stringWidth("About to Start...") / 2, 134);
                                                } else {
                                                    this.rd.drawString("Game Started", 561 - this.ftm.stringWidth("Game Started") / 2, 117);
                                                    this.stringbutton("    Watch this Game    ", 561, 154, 0);
                                                }
                                            } else {
                                                this.rd.drawString("About to Start...", 561 - this.ftm.stringWidth("About to Start...") / 2, 134);
                                            }
                                        }
                                    } else {
                                        this.rd.setColor(new Color(0, 0, 0));
                                        this.rd.setFont(new Font("Arial", 1, 12));
                                        this.ftm = this.rd.getFontMetrics();
                                        if (this.conon == 2) {
                                            this.rd.drawString("Starting Game Now!", 561 - this.ftm.stringWidth("Starting Game Now!") / 2, 124);
                                        }
                                        if (this.conon == 3) {
                                            this.rd.drawString("Opening Game Now!", 561 - this.ftm.stringWidth("Opening Game Now!") / 2, 124);
                                        }
                                        this.rd.setFont(new Font("Arial", 0, 12));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.drawString("Please Wait...", 561 - this.ftm.stringWidth("Please Wait...") / 2, 154);
                                    }
                                    this.rd.setColor(this.color2k(200, 200, 200));
                                    this.rd.drawRect(415, 222, 293, 40);
                                    this.rd.drawImage(this.xt.stg, 422, 227, null);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.setFont(new Font("Arial", 1, 10));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.drawString("Laps: " + this.gnlaps[n10] + "", 660, 235);
                                    if (n > 415 && n < 708 && n2 > 222 && n2 < 262 && (this.wait[n10] > 0 || this.gstgn[n10] < 0) && this.conon == 1) {
                                        this.stringbutton("       Preview Stage       ", 562, 247, 0);
                                        this.pbtn = 2;
                                    } else {
                                        if (this.gstgn[n10] > 0) {
                                            String string11 = "NFM 1";
                                            int n46 = this.gstgn[n10];
                                            if (this.gstgn[n10] > 10) {
                                                string11 = "NFM 2";
                                                n46 = this.gstgn[n10] - 10;
                                            }
                                            if (this.gstgn[n10] > 27) {
                                                string11 = "Multiplayer";
                                                n46 = this.gstgn[n10] - 27;
                                            }
                                            this.rd.drawString("" + string11 + " - Stage " + n46 + "", 562 - this.ftm.stringWidth("" + string11 + " - Stage " + n46 + "") / 2, 237);
                                        } else {
                                            this.rd.drawString("Custom Stage", 562 - this.ftm.stringWidth("Custom Stage") / 2, 237);
                                        }
                                        this.rd.setFont(new Font("Arial", 1, 12));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.drawString(this.gstages[n10], 562 - this.ftm.stringWidth(this.gstages[n10]) / 2, 254);
                                    }
                                    if (!this.xt.lan) {
                                        int n47 = 237;
                                        int n48 = 471;
                                        if (this.wait[n10] > 0) {
                                            if (this.mnpls[n10] > 5) {
                                                n47 = 415;
                                                n48 = 293;
                                            }
                                        } else if (this.npls[n10] > 5) {
                                            n47 = 415;
                                            n48 = 293;
                                        }
                                        this.rd.setColor(this.color2k(200, 200, 200));
                                        this.rd.drawRect(n47, 264, n48, 124);
                                        this.rd.setColor(this.color2k(240, 240, 240));
                                        this.rd.fillRect(n47 + 1, 265, n48 - 1, 21);
                                        this.rd.drawImage(this.xt.gmc, n47 + 7, 269, null);
                                        this.rd.setFont(new Font("Tahoma", 0, 11));
                                        this.rd.setColor(this.color2k(110, 110, 110));
                                        this.rd.drawString("( Game Chat )", n47 + 57, 278);
                                        this.rd.setColor(new Color(0, 0, 0));
                                        if (this.updatec != -1) {
                                            String[] stringArray = new String[7];
                                            String[] stringArray3 = new String[7];
                                            boolean[] blArray = new boolean[]{false, false, false, false, false, false, false};
                                            for (int i = 0; i < 7; ++i) {
                                                stringArray[i] = "";
                                                stringArray3[i] = this.cnames[i];
                                                int n49 = 0;
                                                int n50 = 0;
                                                int n51 = 0;
                                                int n52 = 0;
                                                for (n34 = 0; n34 < this.sentn[i].length(); ++n34) {
                                                    String string12 = "" + this.sentn[i].charAt(n34);
                                                    if (string12.equals(" ")) {
                                                        n49 = n50;
                                                        n51 = n34;
                                                        ++n52;
                                                    } else {
                                                        n52 = 0;
                                                    }
                                                    if (n52 > 1) continue;
                                                    int n53 = i;
                                                    stringArray[n53] = stringArray[n53] + string12;
                                                    ++n50;
                                                    if (this.ftm.stringWidth(stringArray[i]) <= n48 - 94) continue;
                                                    if (n49 != 0) {
                                                        stringArray[i] = stringArray[i].substring(0, n49);
                                                        for (int j = 0; j < i; ++j) {
                                                            stringArray[j] = stringArray[j + 1];
                                                            stringArray3[j] = stringArray3[j + 1];
                                                            blArray[j] = blArray[j + 1];
                                                        }
                                                        stringArray[i] = "";
                                                        blArray[i] = true;
                                                        n34 = n51;
                                                        n50 = 0;
                                                        n49 = 0;
                                                        continue;
                                                    }
                                                    stringArray[i] = "";
                                                    n50 = 0;
                                                }
                                            }
                                            String string13 = "";
                                            this.rd.setFont(new Font("Tahoma", 1, 11));
                                            this.ftm = this.rd.getFontMetrics();
                                            for (n34 = 0; n34 < 7; ++n34) {
                                                if (string13.equals(stringArray3[n34])) continue;
                                                this.rd.drawString(stringArray3[n34] + ":", n47 + 84 - this.ftm.stringWidth(stringArray3[n34] + ":"), 299 + n34 * 14);
                                                string13 = stringArray3[n34];
                                            }
                                            this.rd.setFont(new Font("Tahoma", 0, 11));
                                            for (n34 = 0; n34 < 7; ++n34) {
                                                if (blArray[n34] && n34 == 0 && stringArray[n34].indexOf(" ") != -1) {
                                                    stringArray[n34] = "..." + stringArray[n34].substring(stringArray[n34].indexOf(" "), stringArray[n34].length()) + "";
                                                }
                                                this.rd.drawString(stringArray[n34], n47 + 88, 299 + n34 * 14);
                                            }
                                        } else {
                                            this.rd.drawString("Loading chat...", n47 + n48 / 2 - this.ftm.stringWidth("Loading chat...") / 2, 315);
                                        }
                                        if (this.conon == 1) {
                                            this.pre2 = true;
                                        } else {
                                            this.hideinputs();
                                        }
                                        if (control.enter && !this.gs.cmsg.getText().equals("Type here...") && !this.gs.cmsg.getText().equals("")) {
                                            this.pessd[this.btn] = true;
                                            control.enter = false;
                                            String string14 = this.gs.cmsg.getText().replace('|', ':');
                                            if (string14.toLowerCase().indexOf(this.gs.tpass.getText().toLowerCase()) != -1) {
                                                string14 = " ";
                                            }
                                            if (!this.xt.msgcheck(string14) && this.updatec > -12) {
                                                for (int i = 0; i < 6; ++i) {
                                                    this.sentn[i] = this.sentn[i + 1];
                                                    this.cnames[i] = this.cnames[i + 1];
                                                }
                                                this.sentn[6] = string14;
                                                this.cnames[6] = this.pnames[this.im];
                                                this.updatec = this.updatec > -11 ? -11 : --this.updatec;
                                            } else {
                                                ++this.xt.warning;
                                            }
                                            this.gs.cmsg.setText("");
                                        }
                                        this.stringbutton("Send Message", 655, 405, 3);
                                    }
                                } else {
                                    if (this.gs.cmsg.isShowing()) {
                                        this.gs.cmsg.hide();
                                    }
                                    if (this.pcars[this.dispcar] == this.forcar && this.forcar != -1) {
                                        this.rd.drawImage(this.xt.crd, 517, 81, null);
                                        this.rd.setColor(new Color(16, 198, 255));
                                        this.rd.drawRect(415, 96, 293, 315);
                                        this.rd.setColor(this.color2k(240, 240, 240));
                                        this.rd.fillRect(416, 97, 4, 314);
                                        this.rd.fillRect(704, 97, 4, 314);
                                        this.rd.fillRect(416, 97, 292, 4);
                                        this.rd.fillRect(416, 407, 292, 4);
                                        if (this.flks >= 0) {
                                            this.rd.setColor(new Color(239, 234, 177));
                                            ++this.flks;
                                            if (this.flks > 3) {
                                                this.flks = -1;
                                            }
                                        } else {
                                            this.rd.setColor(new Color(224, 226, 176));
                                            --this.flks;
                                            if (this.flks < -4) {
                                                this.flks = 0;
                                            }
                                        }
                                        this.rd.fillRect(445, 120, 233, 127);
                                        this.rd.setColor(new Color(0, 0, 0));
                                        this.rd.setFont(new Font("Arial", 1, 13));
                                        this.ftm = this.rd.getFontMetrics();
                                        this.rd.drawString(this.cd.names[this.forcar], 561 - this.ftm.stringWidth(this.cd.names[this.forcar]) / 2, 117);
                                        for (n38 = 0; n38 < this.dispco.npl; ++n38) {
                                            if (contOArray[this.forcar].p[n38].colnum == 1) {
                                                this.dispco.p[n38].hsb[0] = this.pcols[this.dispcar][0];
                                                this.dispco.p[n38].hsb[1] = this.pcols[this.dispcar][1];
                                                this.dispco.p[n38].hsb[2] = 1.0f - this.pcols[this.dispcar][2];
                                            }
                                            if (contOArray[this.forcar].p[n38].colnum != 2) continue;
                                            this.dispco.p[n38].hsb[0] = this.pcols[this.dispcar][3];
                                            this.dispco.p[n38].hsb[1] = this.pcols[this.dispcar][4];
                                            this.dispco.p[n38].hsb[2] = 1.0f - this.pcols[this.dispcar][5];
                                        }
                                        this.m.cx = 561;
                                        this.dispco.z = 1200;
                                        this.dispco.y = 605 - this.dispco.grat;
                                        this.dispco.x = 225;
                                        this.dispco.zy = 0;
                                        this.dispco.xz = this.mrot;
                                        this.mrot -= 5;
                                        if (this.mrot < -360) {
                                            this.mrot += 360;
                                        }
                                        this.dispco.xy = 0;
                                        this.dispco.wzy -= 10;
                                        if (this.dispco.wzy < -45) {
                                            this.dispco.wzy += 45;
                                        }
                                        this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                                        this.dispco.d(this.rd);
                                        this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                        this.m.cx = 400;
                                        this.rd.setFont(new Font("Arial", 1, 11));
                                        n38 = 424;
                                        n40 = -55;
                                        this.rd.setColor(new Color(0, 63, 128));
                                        this.rd.drawString("Top Speed:", 30 + n38, 318 + n40);
                                        this.rd.drawImage(this.xt.statb, 97 + n38, 312 + n40, null);
                                        this.rd.drawString("Acceleration:", 20 + n38, 333 + n40);
                                        this.rd.drawImage(this.xt.statb, 97 + n38, 327 + n40, null);
                                        this.rd.setColor(this.color2k(255, 255, 255));
                                        float f = (float)(this.cd.swits[this.forcar][2] - 220) / 90.0f;
                                        if ((double)f < 0.2) {
                                            f = 0.2f;
                                        }
                                        this.rd.fillRect((int)(97.0f + 156.0f * f) + n38, 312 + n40, (int)(156.0f * (1.0f - f) + 1.0f), 7);
                                        f = this.cd.acelf[this.forcar][1] * this.cd.acelf[this.forcar][0] * this.cd.acelf[this.forcar][2] * this.cd.grip[this.forcar] / 7700.0f;
                                        if (f > 1.0f) {
                                            f = 1.0f;
                                        }
                                        this.rd.fillRect((int)(97.0f + 156.0f * f) + n38, 327 + n40, (int)(156.0f * (1.0f - f) + 1.0f), 7);
                                        this.rd.drawImage(this.xt.statbo, 97 + n38, 312 + n40, null);
                                        this.rd.drawImage(this.xt.statbo, 97 + n38, 327 + n40, null);
                                        n38 = 50;
                                        n40 = -25;
                                        this.rd.setColor(new Color(0, 63, 128));
                                        this.rd.drawString("Stunts:", 427 + n38, 318 + n40);
                                        this.rd.drawImage(this.xt.statb, 471 + n38, 312 + n40, null);
                                        this.rd.drawString("Strength:", 415 + n38, 333 + n40);
                                        this.rd.drawImage(this.xt.statb, 471 + n38, 327 + n40, null);
                                        this.rd.drawString("Endurance:", 405 + n38, 348 + n40);
                                        this.rd.drawImage(this.xt.statb, 471 + n38, 342 + n40, null);
                                        this.rd.setColor(this.color2k(255, 255, 255));
                                        f = ((float)this.cd.airc[this.forcar] * this.cd.airs[this.forcar] * this.cd.bounce[this.forcar] + 28.0f) / 139.0f;
                                        if (f > 1.0f) {
                                            f = 1.0f;
                                        }
                                        this.rd.fillRect((int)(471.0f + 156.0f * f) + n38, 312 + n40, (int)(156.0f * (1.0f - f) + 1.0f), 7);
                                        float f2 = 0.5f;
                                        f = (this.cd.moment[this.forcar] + f2) / 2.6f;
                                        if (f > 1.0f) {
                                            f = 1.0f;
                                        }
                                        this.rd.fillRect((int)(471.0f + 156.0f * f) + n38, 327 + n40, (int)(156.0f * (1.0f - f) + 1.0f), 7);
                                        f = this.cd.outdam[this.forcar];
                                        this.rd.fillRect((int)(471.0f + 156.0f * f) + n38, 342 + n40, (int)(156.0f * (1.0f - f) + 1.0f), 7);
                                        this.rd.drawImage(this.xt.statbo, 471 + n38, 312 + n40, null);
                                        this.rd.drawImage(this.xt.statbo, 471 + n38, 327 + n40, null);
                                        this.rd.drawImage(this.xt.statbo, 471 + n38, 342 + n40, null);
                                        this.rd.setColor(new Color(0, 0, 0));
                                        if (this.forcar < 16) {
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString("Created by Radicalplay.com", 561 - this.ftm.stringWidth("Created by Radicalplay.com") / 2, 347);
                                            String string15 = "Game Car";
                                            if (this.cd.cclass[this.forcar] == 0) {
                                                string15 = "Class C ,  Game Car";
                                            }
                                            if (this.cd.cclass[this.forcar] == 1) {
                                                string15 = "Class B & C ,  Game Car";
                                            }
                                            if (this.cd.cclass[this.forcar] == 2) {
                                                string15 = "Class B ,  Game Car";
                                            }
                                            if (this.cd.cclass[this.forcar] == 3) {
                                                string15 = "Class A & B ,  Game Car";
                                            }
                                            if (this.cd.cclass[this.forcar] == 4) {
                                                string15 = "Class A ,  Game Car";
                                            }
                                            this.rd.drawString(string15, 561 - this.ftm.stringWidth(string15) / 2, 367);
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.setFont(new Font("Arial", 0, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.drawString("You already have this car.", 561 - this.ftm.stringWidth("You already have this car.") / 2, 395);
                                        } else {
                                            this.rd.setFont(new Font("Arial", 1, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            if (!this.cd.createdby[this.forcar - 16].equals(this.xt.nickname)) {
                                                this.rd.drawString("Created by :  " + this.cd.createdby[this.forcar - 16] + "", 561 - this.ftm.stringWidth("Created by :  " + this.cd.createdby[this.forcar - 16] + "") / 2, 347);
                                                n36 = this.ftm.stringWidth(this.cd.createdby[this.forcar - 16]);
                                                n35 = 561 - this.ftm.stringWidth("Created by :  " + this.cd.createdby[this.forcar - 16] + "") / 2 + this.ftm.stringWidth("Created by :  " + this.cd.createdby[this.forcar - 16] + "") - n36;
                                                this.rd.drawLine(n35, 349, n35 + n36 - 2, 349);
                                                if (n > n35 - 2 && n < n35 + n36 && n2 > 334 && n2 < 351) {
                                                    if (bl) {
                                                        if (!this.gb.proname.equals(this.cd.createdby[this.forcar - 16])) {
                                                            this.gb.proname = this.cd.createdby[this.forcar - 16];
                                                            this.gb.loadedp = false;
                                                        }
                                                        this.gb.tab = 1;
                                                        this.gb.open = 2;
                                                        this.gb.upo = true;
                                                    }
                                                    n4 = 12;
                                                }
                                            } else {
                                                this.rd.drawString("Created by You", 561 - this.ftm.stringWidth("Created by You") / 2, 347);
                                            }
                                            this.rd.setColor(new Color(128, 73, 0));
                                            String string16 = "";
                                            if (this.cd.cclass[this.forcar] == 0) {
                                                string16 = "Class C ,  ";
                                            }
                                            if (this.cd.cclass[this.forcar] == 1) {
                                                string16 = "Class B & C ,  ";
                                            }
                                            if (this.cd.cclass[this.forcar] == 2) {
                                                string16 = "Class B ,  ";
                                            }
                                            if (this.cd.cclass[this.forcar] == 3) {
                                                string16 = "Class A & B ,  ";
                                            }
                                            if (this.cd.cclass[this.forcar] == 4) {
                                                string16 = "Class A ,  ";
                                            }
                                            if (this.cd.publish[this.forcar - 16] == 0) {
                                                string16 = string16 + "Private Car";
                                            }
                                            if (this.cd.publish[this.forcar - 16] == 1) {
                                                string16 = string16 + "Public Car";
                                                this.rd.setColor(new Color(0, 64, 128));
                                            }
                                            if (this.cd.publish[this.forcar - 16] == 2) {
                                                string16 = string16 + "Super Public Car";
                                                this.rd.setColor(new Color(0, 64, 128));
                                            }
                                            this.rd.drawString(string16, 561 - this.ftm.stringWidth(string16) / 2, 367);
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.setFont(new Font("Arial", 0, 12));
                                            this.ftm = this.rd.getFontMetrics();
                                            if (this.cd.publish[this.forcar - 16] == 1 || this.cd.publish[this.forcar - 16] == 2) {
                                                if (this.cd.action == -9) {
                                                    this.rd.drawString("Failed to add car!  Unknown error!", 561 - this.ftm.stringWidth("Failed to add car!  Unknown error!") / 2, 395);
                                                }
                                                if (this.cd.action == -8) {
                                                    this.rd.drawString("Failed!  You already have 20 cars!", 561 - this.ftm.stringWidth("Failed!  You already have 20 cars!") / 2, 395);
                                                }
                                                if (this.cd.action == 7) {
                                                    this.rd.drawString("" + this.cd.names[this.cd.ac] + " has been added to your cars!", 561 - this.ftm.stringWidth("" + this.cd.names[this.cd.ac] + " has been added to your cars!") / 2, 395);
                                                }
                                                if (this.cd.action == -7) {
                                                    this.rd.drawString("You already have this car.", 561 - this.ftm.stringWidth("You already have this car.") / 2, 395);
                                                }
                                                if (this.cd.action == 6) {
                                                    this.rd.drawString("Adding Car...", 561 - this.ftm.stringWidth("Adding Car...") / 2, 395);
                                                }
                                                if (this.cd.action == -6) {
                                                    String string17 = "Upgrade to a full account to add custom cars!";
                                                    n34 = 561 - this.ftm.stringWidth(string17) / 2;
                                                    int n54 = n34 + this.ftm.stringWidth(string17);
                                                    this.rd.drawString(string17, n34, 395);
                                                    if (this.waitlink != -1) {
                                                        this.rd.drawLine(n34, 396, n54, 396);
                                                    }
                                                    if (n > n34 && n < n54 && n2 > 384 && n2 < 397) {
                                                        if (this.waitlink != -1) {
                                                            n4 = 12;
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
                                                if (this.cd.action == 0 && this.xt.drawcarb(true, null, " Add to My Cars ", 503, 375, n, n2, bl)) {
                                                    if (this.xt.logged) {
                                                        if (this.cd.lastload != 2 || this.forcar >= 36) {
                                                            this.cd.action = 6;
                                                            this.cd.ac = this.forcar;
                                                            this.cd.sparkactionloader();
                                                        } else {
                                                            this.cd.action = -7;
                                                        }
                                                    } else {
                                                        this.cd.action = -6;
                                                        this.waitlink = 20;
                                                    }
                                                }
                                            } else {
                                                this.rd.drawString("Private Car.  Cannot be added to account.", 561 - this.ftm.stringWidth("Private Car.  Cannot be added to account.") / 2, 395);
                                            }
                                        }
                                        if (this.xt.drawcarb(true, null, "X", 682, 99, n, n2, bl)) {
                                            this.dispcar = -1;
                                        }
                                    } else {
                                        this.dispcar = -1;
                                    }
                                }
                            }
                        }
                    } else {
                        if (!this.jflexo) {
                            this.xt.jflexo();
                            this.jflexo = true;
                        }
                        this.btn = 0;
                        if (this.gs.cmsg.isShowing()) {
                            this.gs.cmsg.hide();
                            this.gs.requestFocus();
                        }
                        this.rd.setColor(this.color2k(255, 255, 255));
                        this.rd.fillRoundRect(155, 148, 490, 127, 20, 20);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawRoundRect(155, 148, 490, 127, 20, 20);
                        if (this.ontyp != 76) {
                            String string = "";
                            int n55 = 0;
                            if (this.ontyp >= 10) {
                                n55 = 10;
                                string = "Custom Cars";
                                if (this.ontyp > 10) {
                                    string = string + ", ";
                                }
                            }
                            if (this.ontyp >= 20) {
                                n55 = 20;
                                string = "Game Cars";
                                if (this.ontyp > 20) {
                                    string = string + ", ";
                                }
                            }
                            if (this.ontyp >= 30) {
                                n55 = 30;
                                string = "Clan Cars";
                                if (this.ontyp > 30) {
                                    string = string + ", ";
                                }
                            }
                            if (this.ontyp - n55 == 1) {
                                string = string + "Class C";
                            }
                            if (this.ontyp - n55 == 2) {
                                string = string + "Class B & C";
                            }
                            if (this.ontyp - n55 == 3) {
                                string = string + "Class B";
                            }
                            if (this.ontyp - n55 == 4) {
                                string = string + "Class A & B";
                            }
                            if (this.ontyp - n55 == 5) {
                                string = string + "Class A";
                            }
                            if (this.ontyp <= -2) {
                                string = Math.abs(this.ontyp + 2) == 13 ? " " + this.cd.names[Math.abs(this.ontyp + 2)] + "  Game" : "" + this.cd.names[Math.abs(this.ontyp + 2)] + " Game";
                            }
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.setFont(new Font("Arial", 1, 13));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString(": :   " + string + "   : :", 400 - this.ftm.stringWidth(": :   " + string + "   : :") / 2, 175);
                            string = "a";
                            if (this.ontyp - n55 == 1) {
                                string = "a class C";
                            }
                            if (this.ontyp - n55 == 2) {
                                string = "a class B or C";
                            }
                            if (this.ontyp - n55 == 3) {
                                string = "a class B";
                            }
                            if (this.ontyp - n55 == 4) {
                                string = "a class A or B";
                            }
                            if (this.ontyp - n55 == 5) {
                                string = "a class A";
                            }
                            if (n55 == 0) {
                                string = string + " car";
                            }
                            if (n55 == 10) {
                                string = string + " custom car";
                            }
                            if (n55 == 20) {
                                string = string + " game car";
                            }
                            if (n55 == 30) {
                                string = string + " clan car";
                            }
                            if (this.ontyp <= -2) {
                                string = Math.abs(this.ontyp + 2) == 13 ? " " + this.cd.names[Math.abs(this.ontyp + 2)] + " " : "" + this.cd.names[Math.abs(this.ontyp + 2)] + "";
                            }
                            this.rd.drawString("To join this game you need to have " + string + "!", 400 - this.ftm.stringWidth("To join this game you need to have " + string + "!") / 2, 206);
                            this.stringbutton("  Get " + string + " now  ", 400, 247, 0);
                            this.stringbutton("  Cancel X  ", 593, 259, 2);
                            if (this.gb.open > 0 && this.gb.upo) {
                                this.onjoin = -1;
                            }
                        } else {
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.setFont(new Font("Arial", 1, 13));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString(": :   Custom Stage   : :", 400 - this.ftm.stringWidth(": :   Custom Stage   : :") / 2, 175);
                            this.rd.drawString("You need to upgrade to a full account to join this game!", 400 - this.ftm.stringWidth("You need to upgrade to a full account to join this game!") / 2, 202);
                            this.rd.setColor(this.color2k(200, 200, 200));
                            this.rd.fillRoundRect(310, 215, 180, 50, 20, 20);
                            this.drawSbutton(this.xt.upgrade, 400, 240);
                            this.stringbutton("  Cancel X  ", 593, 259, 2);
                        }
                    }
                } else {
                    this.xt.mainbg(3);
                    this.btn = 0;
                    if (this.gs.cmsg.isShowing()) {
                        this.gs.cmsg.hide();
                        this.gs.requestFocus();
                    }
                    this.rd.setComposite(AlphaComposite.getInstance(3, 0.2f));
                    this.rd.drawImage(this.xt.bggo, 0, 0, null);
                    this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.fillRect(65, 425, 670, 25);
                    this.rd.fillRect(0, 0, 65, 450);
                    this.rd.fillRect(735, 0, 65, 450);
                    this.rd.fillRect(65, 0, 670, 25);
                    float f = 1.0f - (float)(this.lg.flipo - 10) / 80.0f;
                    if (f > 1.0f) {
                        f = 1.0f;
                    }
                    if (f < 0.0f) {
                        f = 0.0f;
                    }
                    this.rd.setComposite(AlphaComposite.getInstance(3, f));
                    if (this.lg.flipo > 10) {
                        this.rd.drawImage(this.xt.logomadnes, 97 + (int)(2.0 - Math.random() * 4.0), 36 + (int)(2.0 - Math.random() * 4.0), null);
                    } else {
                        this.rd.drawImage(this.xt.logomadnes, 97, 36, null);
                    }
                    ++this.lg.flipo;
                    if (this.lg.flipo > 50) {
                        this.lg.flipo = 0;
                    }
                    int n56 = 30;
                    this.rd.setComposite(AlphaComposite.getInstance(3, 0.25f));
                    this.rd.setColor(new Color(203, 227, 253));
                    this.rd.fillRoundRect(115, 57 + n56, 570, 307, 20, 20);
                    this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    this.rd.setColor(this.color2k(90, 90, 90));
                    this.rd.drawRoundRect(115, 57 + n56, 570, 307, 20, 20);
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(this.color2k(0, 0, 0));
                    this.rd.drawString("You are allowed 5 multiplayer turns per day to try the game with your trial account.", 135, 85 + n56);
                    this.rd.drawString("Upgrade your account to a full account to purchase and play the multiplayer game.", 135, 105 + n56);
                    this.drawSbutton(this.xt.upgrade, 400, 130 + n56);
                    this.rd.setColor(new Color(30, 70, 110));
                    this.rd.drawString("You can upgrade your account by just sharing the game & posting about it online!", 135, 165 + n56);
                    this.rd.drawString("Click 'Upgrade' for more details.", 135, 185 + n56);
                    this.rd.drawString("Or try the multiplayer again tomorrow.", 135, 205 + n56);
                    this.rd.setColor(this.color2k(0, 0, 0));
                    this.rd.drawString("For now to preview and try the multiplayer more, with your trial account you can:", 135, 245 + n56);
                    this.rd.setColor(new Color(30, 70, 110));
                    this.rd.drawString("-  Watch online multiplayer games.", 135, 265 + n56);
                    this.rd.drawString("-  Access the multiplayer dome.", 135, 285 + n56);
                    this.rd.drawString("-  Play LAN multiplayer games (unlimitedly).", 135, 305 + n56);
                    this.drawSbutton(this.xt.exit, 400, 336 + n56);
                }
            } else {
                this.xt.drawWarning();
                if (this.gs.cmsg.isShowing()) {
                    this.gs.cmsg.hide();
                    this.gs.requestFocus();
                }
                if (this.xt.warning > 220) {
                    this.conon = 0;
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
            if (n4 == this.pcurs) break block951;
            this.gs.setCursor(new Cursor(n4));
            this.pcurs = n4;
        }
    }

    public void preforma(int n, int n2) {
        boolean bl;
        if (this.pre1) {
            bl = false;
            if (!this.gs.openm && this.gb.open == 0) {
                if (this.conon != 0) {
                    bl = true;
                }
            } else if (this.gs.cmsg.isShowing()) {
                this.gs.cmsg.hide();
            }
            this.gs.movefieldd(this.gs.cmsg, 235, 390, 360, 22, bl);
            if (this.gs.cmsg.getText().equals("Type here...") && n > 234 && n < 603 && n2 > 385 && n2 < 417) {
                this.gs.cmsg.setText("");
            }
        }
        if (this.pre2) {
            bl = false;
            if (!this.gs.openm && this.gb.open == 0) {
                bl = true;
            } else if (this.gs.cmsg.isShowing()) {
                this.gs.cmsg.hide();
            }
            this.gs.movefieldd(this.gs.cmsg, 237, 390, 360, 22, bl);
            if (this.gs.cmsg.getText().equals("Type here...") && n > 232 && n < 601 && n2 > 385 && n2 < 417) {
                this.gs.cmsg.setText("");
            }
        }
        if (this.pre1 || this.pre2) {
            if (this.gs.cmsg.getText().length() > 100) {
                this.gs.cmsg.setText(this.gs.cmsg.getText().substring(0, 100));
                this.gs.cmsg.select(100, 100);
            }
            this.pre1 = false;
            this.pre2 = false;
        }
    }

    public void stageselect(CheckPoints checkPoints, Control control, int n, int n2, boolean bl) {
        int n3;
        Object object;
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.fillRect(0, 0, 65, 450);
        this.rd.fillRect(735, 0, 65, 450);
        this.rd.fillRect(65, 0, 670, 25);
        this.rd.fillRect(65, 425, 670, 25);
        this.btn = 0;
        int n4 = 0;
        this.rd.drawImage(this.xt.br, 65, 25, null);
        if (this.britchl == -1) {
            this.ongame = -1;
            this.britchl = 0;
        }
        int n5 = 0;
        for (int i = 0; i < this.ngm; ++i) {
            if (this.ongame != this.gnum[i]) continue;
            n5 = i;
        }
        if (this.chalngd != -2 && this.ongame != -1) {
            this.rd.setColor(this.color2k(20, 20, 20));
            this.rd.fillRect(80, 0, 640, 40);
            this.rd.setColor(this.color2k(70, 70, 70));
            this.rd.drawLine(80, 40, 720, 40);
            this.rd.drawLine(80, 40, 80, 0);
            this.rd.drawLine(720, 40, 720, 0);
            this.rd.setColor(new Color(193, 106, 0));
            this.rd.setFont(new Font("Arial", 1, 12));
            this.ftm = this.rd.getFontMetrics();
            if (this.wait[n5] > 0) {
                if (this.gwarb[n5] == 0) {
                    if (this.wait[n5] > 30 || this.npls[n5] <= 1) {
                        String string = "";
                        if (this.npls[n5] > 1) {
                            object = new Date();
                            long l = ((Date)object).getTime();
                            if (this.ptime == 0L || l > this.ptime + 1500L) {
                                if (this.ptime != 0L) {
                                    int n6 = n5;
                                    this.wait[n6] = this.wait[n6] - 1;
                                    if (this.wait[n5] < 1) {
                                        this.wait[n5] = 1;
                                    }
                                }
                                this.ptime = l;
                            }
                            string = " (waiting " + this.wait[n5] + " seconds maximum)";
                            if (this.wait[n5] > 60) {
                                string = " (waiting " + (float)((int)((float)this.wait[n5] / 60.0f * 100.0f)) / 100.0f + " minutes maximum)";
                            }
                        }
                        this.rd.drawString("Waiting for " + (this.mnpls[n5] - this.npls[n5]) + " more players to join to start this game" + string + "!", 95, 15);
                        this.rd.setFont(new Font("Arial", 0, 12));
                        if (!this.gmaker[n5].equals("Coach Insano") && !this.gmaker[n5].equals(this.pnames[this.im])) {
                            boolean bl2 = false;
                            for (int i = 0; i < this.npo; ++i) {
                                if (this.pgames[i] != this.ongame || !this.gmaker[n5].equals(this.pnames[i])) continue;
                                bl2 = true;
                            }
                            if (bl2) {
                                this.rd.drawString("" + this.gmaker[n5] + " can start this game at anytime (the game creator)...", 95, 31);
                            }
                        }
                    } else {
                        Date date = new Date();
                        long l = date.getTime();
                        if (this.ptime == 0L || l > this.ptime + 1500L) {
                            if (this.ptime != 0L) {
                                int n7 = n5;
                                this.wait[n7] = this.wait[n7] - 1;
                                if (this.wait[n5] < 1) {
                                    this.wait[n5] = 1;
                                }
                            }
                            this.ptime = l;
                        }
                        if (this.pgames[this.im] == this.ongame || this.nflk != 0) {
                            this.rd.drawString("Game starts in " + this.wait[n5] + " seconds!", 400 - this.ftm.stringWidth("Game starts in " + this.wait[n5] + " seconds!") / 2, 23);
                            if (this.pgames[this.im] != this.ongame) {
                                --this.nflk;
                            }
                        } else if (this.pgames[this.im] != this.ongame) {
                            this.nflk = 3;
                        }
                    }
                } else {
                    this.rd.drawString("Waiting for " + (this.mnpls[n5] - this.npls[n5]) + " clan members to join to start this game!", 95, 23);
                }
                if (this.pgames[this.im] != this.ongame) {
                    boolean bl3 = false;
                    if (this.gwarb[n5] == 0) {
                        if (this.gplyrs[n5].equals("") || this.gplyrs[n5].indexOf(this.pnames[this.im]) != -1) {
                            bl3 = true;
                        }
                    } else if (this.xt.clan.toLowerCase().equals(this.gaclan[n5].toLowerCase()) || this.xt.clan.toLowerCase().equals(this.gvclan[n5].toLowerCase())) {
                        bl3 = true;
                    }
                    if (bl3) {
                        this.stringbutton(" Join Game ", 660, 23, 2);
                    }
                }
            } else {
                this.rd.setColor(this.color2k(120, 120, 120));
                if (this.wait[n5] == 0) {
                    this.rd.drawString("Game Started", 400 - this.ftm.stringWidth("Game Started") / 2, 20);
                } else {
                    this.rd.drawString("Game Finished", 400 - this.ftm.stringWidth("Game Finished") / 2, 20);
                }
            }
        }
        this.rd.setFont(new Font("Arial", 1, 11));
        this.xt.ftm = this.rd.getFontMetrics();
        this.ftm = this.rd.getFontMetrics();
        String string = "";
        if (checkPoints.stage < 0) {
            string = "Custom Stage";
        }
        if (checkPoints.stage > 0 && checkPoints.stage <= 10) {
            string = "Stage " + checkPoints.stage + " NFM 1";
        }
        if (checkPoints.stage > 10 && checkPoints.stage <= 27) {
            string = "Stage " + (checkPoints.stage - 10) + " NFM 2";
        }
        if (checkPoints.stage > 27) {
            string = "Multiplayer Stage " + (checkPoints.stage - 27) + "";
        }
        this.xt.drawcs(85, "Previewing " + string + "  >", 255, 138, 0, 5);
        this.xt.drawcs(105, "| " + checkPoints.name + " |", 255, 176, 85, 5);
        this.rd.drawImage(this.xt.back[this.pback], 532, 285, null);
        if (this.plsndt == 0) {
            object = "Play Soundtrack >";
            int n8 = 562 - this.ftm.stringWidth((String)object) / 2;
            n3 = n8 + this.ftm.stringWidth((String)object);
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawString((String)object, n8 + 1, 326);
            this.rd.drawLine(n8 + 1, 328, n3 + 1, 328);
            this.rd.setColor(new Color(255, 138, 0));
            this.rd.drawString((String)object, n8, 325);
            this.rd.drawLine(n8, 327, n3, 327);
            if (n > n8 && n < n3 && n2 > 314 && n2 < 327) {
                n4 = 12;
                if (bl) {
                    this.plsndt = 1;
                }
            }
        }
        if (this.plsndt == 1) {
            this.xt.drawcs(190, "Loading Soundtrack, please wait...", 255, 138, 0, 5);
        }
        if (checkPoints.stage < 0) {
            this.rd.setColor(new Color(255, 138, 0));
            this.rd.drawString("Created by: " + checkPoints.maker + "", 85, 413);
            if (checkPoints.pubt > 0) {
                if (checkPoints.pubt == 2) {
                    this.xt.drawcs(413, "Super Public Stage", 41, 177, 255, 3);
                } else {
                    this.xt.drawcs(413, "Public Stage", 41, 177, 255, 3);
                }
                if (this.addstage == 0 && this.xt.drawcarb(true, null, " Add to My Stages ", 334, 420, n, n2, bl)) {
                    if (this.xt.logged) {
                        this.cd.onstage = checkPoints.name;
                        this.cd.staction = 2;
                        this.cd.sparkstageaction();
                        this.addstage = 2;
                    } else {
                        this.addstage = 1;
                        this.waitlink = 20;
                    }
                }
                if (this.addstage == 1) {
                    this.rd.setFont(new Font("Arial", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(new Color(193, 106, 0));
                    object = "Upgrade to a full account to add custom stages!";
                    int n9 = 400 - this.ftm.stringWidth((String)object) / 2;
                    n3 = n9 + this.ftm.stringWidth((String)object);
                    this.rd.drawString((String)object, n9, 435);
                    if (this.waitlink != -1) {
                        this.rd.drawLine(n9, 437, n3, 437);
                    }
                    if (n > n9 && n < n3 && n2 > 424 && n2 < 437) {
                        if (this.waitlink != -1) {
                            n4 = 12;
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
                if (this.addstage == 2) {
                    this.xt.drawcs(435, "Adding stage please wait...", 193, 106, 0, 3);
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
                    this.xt.drawcs(435, "Stage has been successfully added to your stages!", 193, 106, 0, 3);
                }
                if (this.addstage == 4) {
                    this.xt.drawcs(435, "You already have this stage!", 193, 106, 0, 3);
                }
                if (this.addstage == 5) {
                    this.xt.drawcs(435, "Cannot add more then 20 stages to your account!", 193, 106, 0, 3);
                }
                if (this.addstage == 6) {
                    this.xt.drawcs(435, "Failed to add stage, unknown error, please try again later.", 193, 106, 0, 3);
                }
            } else {
                this.xt.drawcs(435, "Private Stage", 193, 106, 0, 3);
            }
        }
        if (control.enter || this.conon == 2 || this.ongame == -1 && this.chalngd != -2) {
            this.m.trk = 0;
            if (this.xt.loadedt) {
                this.xt.strack.unload();
            }
            this.m.focus_point = 400;
            this.m.crs = true;
            this.m.x = -335;
            this.m.y = 0;
            this.m.z = -50;
            this.m.xz = 0;
            this.m.zy = 20;
            this.m.ground = -2000;
            this.fase = 1;
            control.enter = false;
        }
        if (n4 != this.pcurs) {
            this.gs.setCursor(new Cursor(n4));
            this.pcurs = n4;
        }
    }

    public void ctachm(int n, int n2, int n3, Control control) {
        int n4;
        int n5 = -1;
        if (this.fase == 1 || this.fase == 4) {
            for (n4 = 0; n4 < this.btn; ++n4) {
                this.pessd[n4] = Math.abs(n - this.bx[n4]) < this.bw[n4] / 2 + 12 && Math.abs(n2 - this.by[n4]) < 14 && (n3 == 1 || n3 == 11);
                if (Math.abs(n - this.bx[n4]) >= this.bw[n4] / 2 + 12 || Math.abs(n2 - this.by[n4]) >= 14 || n3 > -1) continue;
                n5 = n4;
                this.gs.mouses = 0;
            }
        }
        if (this.conon == 1) {
            if (!this.regnow) {
                if (this.onjoin == -1) {
                    if (this.fase == 4) {
                        this.pback = n > 532 && n < 592 && n2 > 285 && n2 < 306 && (n3 == 1 || n3 == 11) ? 1 : 0;
                        if (n > 532 && n < 592 && n2 > 285 && n2 < 306 && n3 <= -1) {
                            this.gs.mouses = 0;
                            this.m.trk = 0;
                            if (this.xt.loadedt) {
                                this.xt.strack.unload();
                            }
                            this.m.focus_point = 400;
                            this.m.crs = true;
                            this.m.x = -335;
                            this.m.y = 0;
                            this.m.z = -50;
                            this.m.xz = 0;
                            this.m.zy = 20;
                            this.m.ground = -2000;
                            this.fase = 1;
                        }
                        if (n5 == 0 && this.chalngd == -1) {
                            n5 = -1;
                            this.join = this.ongame;
                            this.msg = "| Joining Game |";
                            this.spos = 0;
                            this.m.trk = 0;
                            if (this.xt.loadedt) {
                                this.xt.strack.unload();
                            }
                            this.m.focus_point = 400;
                            this.m.crs = true;
                            this.m.x = -335;
                            this.m.y = 0;
                            this.m.z = -50;
                            this.m.xz = 0;
                            this.m.zy = 20;
                            this.m.ground = -2000;
                            this.fase = 1;
                        }
                    }
                    if (this.fase == 1) {
                        if (this.ongame == -1) {
                            int n6;
                            int n7;
                            if (n5 == 0) {
                                if (this.chalngd == -1) {
                                    if (this.xt.lan && !this.lanlogged) {
                                        this.gs.reglink();
                                    } else {
                                        n4 = 0;
                                        for (n7 = 0; n7 < this.ngm; ++n7) {
                                            if (this.wait[n7] != 0) continue;
                                            n4 = 1;
                                            break;
                                        }
                                        if (n4 == 0 || this.xt.lan) {
                                            this.loadstage = 0;
                                            this.remstage = 0;
                                            this.gstage = 0;
                                            this.gnpls = 8;
                                            this.gwait = 0;
                                            this.gnbts = 0;
                                            this.gclass = 0;
                                            this.gcars = 0;
                                            this.gfix = 0;
                                            this.gnotp = 0;
                                            this.gplayers = "";
                                            this.gs.wgame.select(0);
                                            this.chalngd = -2;
                                        } else {
                                            this.chalngd = -6;
                                        }
                                    }
                                } else if (this.chalngd != -5) {
                                    if (this.invo) {
                                        this.invo = false;
                                    }
                                    for (n4 = 0; n4 < 7; ++n4) {
                                        if (!this.invos[n4].equals("")) {
                                            this.invos[n4] = "";
                                        }
                                        if (this.dinvi[n4].equals("")) continue;
                                        this.dinvi[n4] = "";
                                    }
                                    this.gs.swait.hide();
                                    this.gs.snpls.hide();
                                    this.gs.snbts.hide();
                                    this.gs.sgame.hide();
                                    this.gs.wgame.hide();
                                    this.gs.pgame.hide();
                                    this.gs.vnpls.hide();
                                    this.gs.vtyp.hide();
                                    this.gs.warb.hide();
                                    this.gs.mstgs.hide();
                                    this.gs.slaps.hide();
                                    this.gs.snfm1.hide();
                                    this.gs.snfmm.hide();
                                    this.gs.snfm2.hide();
                                    this.gs.sclass.hide();
                                    this.gs.scars.hide();
                                    this.gs.sfix.hide();
                                    this.gs.mycar.hide();
                                    this.gs.notp.hide();
                                    this.gs.requestFocus();
                                    this.chalngd = -1;
                                }
                            }
                            if (n5 == 1) {
                                this.xt.fase = 23;
                            }
                            if (n5 == 2) {
                                this.conon = 0;
                                this.lg.exitfromlobby();
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
                                this.hideinputs();
                            }
                            int n8 = 3;
                            if (this.chalngd > -1) {
                                if (n5 == 3) {
                                    this.ongame = this.chalngd;
                                    this.chalngd = -1;
                                }
                                if (n5 == 4) {
                                    this.ongame = this.chalngd;
                                    this.join = this.chalngd;
                                    this.msg = "| Joining Game |";
                                    this.chalngd = -1;
                                    this.longame = -1;
                                }
                                if (n5 == 5) {
                                    this.ongame = this.longame;
                                    this.chalngd = -1;
                                    this.longame = -1;
                                }
                                n8 = 6;
                            } else {
                                if (this.chalngd != -1 && this.chalngd != -5 && n5 == 3) {
                                    if (this.invo) {
                                        this.invo = false;
                                    }
                                    for (n7 = 0; n7 < 7; ++n7) {
                                        if (!this.invos[n7].equals("")) {
                                            this.invos[n7] = "";
                                        }
                                        if (this.dinvi[n7].equals("")) continue;
                                        this.dinvi[n7] = "";
                                    }
                                    this.gs.swait.hide();
                                    this.gs.snpls.hide();
                                    this.gs.snbts.hide();
                                    this.gs.sgame.hide();
                                    this.gs.wgame.hide();
                                    this.gs.pgame.hide();
                                    this.gs.vnpls.hide();
                                    this.gs.vtyp.hide();
                                    this.gs.warb.hide();
                                    this.gs.mstgs.hide();
                                    this.gs.slaps.hide();
                                    this.gs.snfm1.hide();
                                    this.gs.snfmm.hide();
                                    this.gs.snfm2.hide();
                                    this.gs.sclass.hide();
                                    this.gs.scars.hide();
                                    this.gs.sfix.hide();
                                    this.gs.mycar.hide();
                                    this.gs.notp.hide();
                                    this.gs.requestFocus();
                                    this.chalngd = -1;
                                }
                                if (this.chalngd == -6 && n5 == 4) {
                                    this.chalngd = -1;
                                }
                                if (this.chalngd == -2) {
                                    if (this.gs.wgame.getSelectedIndex() == 0) {
                                        if (this.gs.sgame.getSelectedIndex() >= 3 && !this.xt.logged) {
                                            if (n5 == 4) {
                                                this.gs.editlink(this.xt.nickname, true);
                                            }
                                            n8 = 5;
                                        } else {
                                            if (n5 == 4) {
                                                if (this.loadstage < 0) {
                                                    this.rd.setColor(new Color(0, 0, 0));
                                                    this.rd.fillRect(0, 0, 670, 400);
                                                    this.gs.repaint();
                                                    this.gs.rooms.hide();
                                                    this.gs.cmsg.hide();
                                                    this.gs.sgame.hide();
                                                    this.gs.wgame.hide();
                                                    this.gs.warb.hide();
                                                    this.gs.pgame.hide();
                                                    this.gs.vnpls.hide();
                                                    this.gs.vtyp.hide();
                                                    this.gs.mstgs.hide();
                                                    this.gs.slaps.hide();
                                                    this.gs.snfm1.hide();
                                                    this.gs.snfmm.hide();
                                                    this.gs.snfm2.hide();
                                                    this.gs.requestFocus();
                                                    this.m.ptr = 0;
                                                    this.m.ptcnt = -10;
                                                    this.m.hit = 20000;
                                                    this.m.fallen = 0;
                                                    this.m.nrnd = 0;
                                                    this.m.ih = 25;
                                                    this.m.iw = 65;
                                                    this.m.h = 425;
                                                    this.m.w = 735;
                                                    this.m.trk = 4;
                                                    this.plsndt = 0;
                                                    this.addstage = 0;
                                                    this.fase = 4;
                                                } else {
                                                    this.sflk = 25;
                                                }
                                            }
                                            if (this.loadstage >= 0) {
                                                n8 = 5;
                                            } else {
                                                if (n5 == 5) {
                                                    if (this.gstage != 0) {
                                                        this.gs.sgame.hide();
                                                        this.gs.wgame.hide();
                                                        this.gs.pgame.hide();
                                                        this.gs.vnpls.hide();
                                                        this.gs.vtyp.hide();
                                                        this.gs.warb.hide();
                                                        this.gs.mstgs.hide();
                                                        this.gs.slaps.hide();
                                                        this.gs.snfm1.hide();
                                                        this.gs.snfmm.hide();
                                                        this.gs.snfm2.hide();
                                                        this.gs.requestFocus();
                                                        this.chalngd = -3;
                                                        n5 = -1;
                                                    } else {
                                                        this.sflk = 25;
                                                    }
                                                }
                                                n8 = 6;
                                            }
                                        }
                                    } else if (!this.xt.clan.equals("")) {
                                        n7 = 4;
                                        if (this.gs.warb.sel != 0 && this.gb.loadwbgames == 2) {
                                            if (this.gs.wgame.getSelectedIndex() == 1 && this.gs.vnpls.sel != 0 && this.cancreate == 2) {
                                                if (n5 == 4) {
                                                    if (this.invo) {
                                                        this.invo = false;
                                                    }
                                                    this.msg = "| Creating Game |";
                                                    this.gplayers = "#warb#" + this.gb.warb + "#" + this.gb.warbnum + "#" + (this.gb.gameturn + 1) + "#" + this.xt.clan + "#" + this.gb.vclan + "#" + this.gb.ascore + "#" + this.gb.vscore + "#" + (this.gs.vtyp.sel + 1) + "#";
                                                    this.gstage = this.gb.wbstage[this.gb.gameturn];
                                                    this.gstagelaps = this.gb.wblaps[this.gb.gameturn];
                                                    this.gcars = this.gb.wbcars[this.gb.gameturn] - 1;
                                                    this.gclass = this.gb.wbclass[this.gb.gameturn];
                                                    this.gfix = this.gb.wbfix[this.gb.gameturn];
                                                    this.gnpls = 8;
                                                    if (this.gs.vnpls.sel == 1) {
                                                        this.gnpls = 4;
                                                    }
                                                    if (this.gs.vnpls.sel == 2) {
                                                        this.gnpls = 6;
                                                    }
                                                    this.gwait = 120;
                                                    this.gnotp = 0;
                                                    this.gs.wgame.hide();
                                                    this.gs.pgame.hide();
                                                    this.gs.vnpls.hide();
                                                    this.gs.vtyp.hide();
                                                    this.gs.warb.hide();
                                                    this.gs.requestFocus();
                                                    this.chalngd = -5;
                                                }
                                                ++n7;
                                            }
                                            if (this.gs.wgame.getSelectedIndex() == 2 && this.gs.pgame.sel != 0 && n5 == 4) {
                                                if (this.invo) {
                                                    this.invo = false;
                                                }
                                                this.msg = "| Creating Game |";
                                                this.gstage = this.gb.wbstage[this.gs.pgame.sel - 1];
                                                this.gstagelaps = this.gb.wblaps[this.gs.pgame.sel - 1];
                                                this.gcars = this.gb.wbcars[this.gs.pgame.sel - 1] - 1;
                                                this.gclass = this.gb.wbclass[this.gs.pgame.sel - 1];
                                                this.gfix = this.gb.wbfix[this.gs.pgame.sel - 1];
                                                this.gnpls = 8;
                                                this.gwait = 120;
                                                this.gnotp = 0;
                                                this.gplayers = "";
                                                this.gs.wgame.hide();
                                                this.gs.pgame.hide();
                                                this.gs.vnpls.hide();
                                                this.gs.vtyp.hide();
                                                this.gs.warb.hide();
                                                this.gs.requestFocus();
                                                this.chalngd = -5;
                                            }
                                            if (this.gs.wgame.getSelectedIndex() == 1 && this.gb.canredo && n5 == n7) {
                                                this.gb.loadwbgames = 7;
                                            }
                                        }
                                    } else if (n5 == 4) {
                                        this.gb.tab = 3;
                                        this.gb.cfase = 2;
                                        this.gb.em = 1;
                                        this.gb.msg = "Clan Search";
                                        this.gb.smsg = "Listing clans with recent activity...";
                                        this.gb.nclns = 0;
                                        this.gb.spos5 = 0;
                                        this.gb.lspos5 = 0;
                                        this.gb.flko = 0;
                                        this.gb.open = 2;
                                        this.gb.upo = true;
                                    }
                                }
                                if (this.chalngd == -3) {
                                    if (n5 == 4) {
                                        this.chalngd = -2;
                                        this.gs.snpls.hide();
                                        this.gs.snbts.hide();
                                        this.gs.swait.hide();
                                        this.gs.requestFocus();
                                    }
                                    if (n5 == 5) {
                                        if (this.gnpls != 0 && this.gs.snpls.getSelectedIndex() != 0) {
                                            this.chalngd = -4;
                                            for (n7 = 0; n7 < 7; ++n7) {
                                                if (!this.invos[n7].equals("")) {
                                                    this.invos[n7] = "";
                                                }
                                                if (this.dinvi[n7].equals("")) continue;
                                                this.dinvi[n7] = "";
                                            }
                                            n5 = -1;
                                            this.gs.snpls.hide();
                                            this.gs.snbts.hide();
                                            this.gs.swait.hide();
                                            this.gs.requestFocus();
                                        } else {
                                            this.sflk = 25;
                                        }
                                    }
                                    n8 = 6;
                                }
                                if (this.chalngd == -4) {
                                    n8 = 7;
                                    n7 = 0;
                                    for (n6 = 0; n6 < 7; ++n6) {
                                        if (this.invos[n6].equals("")) continue;
                                        ++n7;
                                    }
                                    if (n7 < this.gnpls - 1) {
                                        if (n5 == 4) {
                                            this.invo = !this.invo;
                                        }
                                    } else {
                                        n8 = 6;
                                    }
                                    if (n5 == n8 - 2) {
                                        if (this.invo) {
                                            this.invo = false;
                                        }
                                        if (this.gs.mycar.getState() && this.xt.sc[0] < 16) {
                                            this.gclass = -(this.xt.sc[0] + 2);
                                            this.gcars = 0;
                                        }
                                        this.gnotp = this.gs.notp.getState() ? 1 : 0;
                                        this.gplayers = "";
                                        this.gs.sclass.hide();
                                        this.gs.scars.hide();
                                        this.gs.sfix.hide();
                                        this.gs.mycar.hide();
                                        this.gs.notp.hide();
                                        this.gs.requestFocus();
                                        this.chalngd = -3;
                                    }
                                    if (n5 == n8 - 1) {
                                        if (this.invo) {
                                            this.invo = false;
                                        }
                                        this.msg = "| Creating Game |";
                                        if (this.gs.mycar.getState() && this.xt.sc[0] < 16) {
                                            this.gclass = -(this.xt.sc[0] + 2);
                                            this.gcars = 0;
                                        }
                                        if (this.gclass != 0) {
                                            this.gwait = 120;
                                        }
                                        this.gnotp = this.gs.notp.getState() ? 1 : 0;
                                        this.gplayers = "";
                                        if (n7 != 0) {
                                            this.gnpls = n7 + 1;
                                            this.gplayers = "" + this.pnames[this.im] + "#";
                                            for (n6 = 0; n6 < n7; ++n6) {
                                                this.gplayers = this.gplayers + this.invos[n6] + "#";
                                            }
                                        }
                                        this.gs.sclass.hide();
                                        this.gs.scars.hide();
                                        this.gs.sfix.hide();
                                        this.gs.mycar.hide();
                                        this.gs.notp.hide();
                                        this.gs.requestFocus();
                                        this.chalngd = -5;
                                    }
                                }
                            }
                            if (!(n5 != n8 || this.xt.lan || this.gs.cmsg.getText().equals("Type here...") || this.gs.cmsg.getText().equals(""))) {
                                String string = this.gs.cmsg.getText().replace('|', ':');
                                if (string.toLowerCase().indexOf(this.gs.tpass.getText().toLowerCase()) != -1) {
                                    string = " ";
                                }
                                if (!this.xt.msgcheck(string) && this.updatec > -12) {
                                    for (n6 = 0; n6 < 6; ++n6) {
                                        this.sentn[n6] = this.sentn[n6 + 1];
                                        this.cnames[n6] = this.cnames[n6 + 1];
                                    }
                                    this.sentn[6] = string;
                                    this.cnames[6] = this.pnames[this.im];
                                    this.updatec = this.updatec > -11 ? -11 : --this.updatec;
                                    this.spos3 = 28;
                                } else {
                                    ++this.xt.warning;
                                }
                                this.gs.cmsg.setText("");
                            }
                        } else if (this.dispcar == -1) {
                            int n9;
                            n4 = 0;
                            for (n9 = 0; n9 < this.ngm; ++n9) {
                                if (this.ongame != this.gnum[n9]) continue;
                                n4 = n9;
                            }
                            n9 = 0;
                            if (this.gwarb[n4] == 0) {
                                if (!this.gplyrs[n4].equals("") && this.gplyrs[n4].indexOf(this.pnames[this.im]) == -1) {
                                    n9 = 1;
                                }
                            } else if (!this.xt.clan.toLowerCase().equals(this.gaclan[n4].toLowerCase()) && !this.xt.clan.toLowerCase().equals(this.gvclan[n4].toLowerCase())) {
                                n9 = 1;
                            }
                            if (control.enter && this.wait[n4] > 0 && this.pgames[this.im] == -1 && n9 == 0) {
                                this.join = this.ongame;
                                this.msg = "| Joining Game |";
                                this.spos = 0;
                                if (this.pbtn == 0) {
                                    this.pessd[1] = true;
                                }
                            }
                            if (this.wait[n4] == -1 && this.pgames[this.im] == -1 && control.enter) {
                                n5 = 0;
                                this.pessd[0] = true;
                            }
                            if (this.pgames[this.im] == -1 && control.exit) {
                                n5 = 0;
                                this.pessd[0] = true;
                            }
                            if (n5 == 0) {
                                if (this.pgames[this.im] == -1) {
                                    this.ongame = -1;
                                    this.chalngd = -1;
                                } else {
                                    this.join = -2;
                                    this.msg = "| Leaving Game |";
                                    this.longame = -1;
                                }
                            }
                            if (this.pbtn == 0) {
                                if (n5 == 1) {
                                    if (this.wait[n4] > 0) {
                                        if (this.pgames[this.im] == -1) {
                                            this.join = this.ongame;
                                            this.msg = "| Joining Game |";
                                            this.spos = 0;
                                        } else if (this.gmaker[n4].equals(this.pnames[this.im]) && this.npls[n4] > 1) {
                                            this.fstart = true;
                                        } else {
                                            n5 = 2;
                                        }
                                    } else {
                                        if (this.wait[n4] == 0 && this.prevloaded == 1) {
                                            this.laps = this.gnlaps[n4];
                                            this.stage = this.gstgn[n4];
                                            this.stagename = this.gstages[n4];
                                            this.nfix = this.gfx[n4];
                                            this.notb = this.gntb[n4] == 1;
                                            this.gs.setCursor(new Cursor(3));
                                            this.conon = 3;
                                        } else {
                                            n5 = 2;
                                        }
                                        if (this.wait[n4] == 0 && this.xt.lan) {
                                            this.laps = this.gnlaps[n4];
                                            this.stage = this.gstgn[n4];
                                            this.stagename = this.gstages[n4];
                                            this.nfix = this.gfx[n4];
                                            this.notb = this.gntb[n4] == 1;
                                            this.gs.setCursor(new Cursor(3));
                                            this.conon = 3;
                                        }
                                    }
                                }
                                if (!(n5 != 2 || this.xt.lan || this.gs.cmsg.getText().equals("Type here...") || this.gs.cmsg.getText().equals(""))) {
                                    String string = this.gs.cmsg.getText().replace('|', ':');
                                    if (string.toLowerCase().indexOf(this.gs.tpass.getText().toLowerCase()) != -1) {
                                        string = " ";
                                    }
                                    if (!this.xt.msgcheck(string) && this.updatec > -12) {
                                        for (int i = 0; i < 6; ++i) {
                                            this.sentn[i] = this.sentn[i + 1];
                                            this.cnames[i] = this.cnames[i + 1];
                                        }
                                        this.sentn[6] = string;
                                        this.cnames[6] = this.pnames[this.im];
                                        this.updatec = this.updatec > -11 ? -11 : --this.updatec;
                                    } else {
                                        ++this.xt.warning;
                                    }
                                    this.gs.cmsg.setText("");
                                }
                            }
                            if (this.pbtn == 1 && n5 == 1) {
                                if (this.pgames[this.im] == -1) {
                                    this.join = this.ongame;
                                    this.msg = "| Joining Game |";
                                    this.spos = 0;
                                } else {
                                    this.invo = !this.invo;
                                }
                            }
                            if (this.pbtn == 2 && (n5 == 1 || n5 == 2)) {
                                this.fase = 2;
                                this.m.ptr = 0;
                                this.m.ptcnt = -10;
                                this.m.hit = 20000;
                                this.m.fallen = 500;
                                this.m.nrnd = 0;
                                this.m.ih = 25;
                                this.m.iw = 65;
                                this.m.h = 425;
                                this.m.w = 735;
                                this.m.trk = 4;
                                this.plsndt = 0;
                                if (this.gs.cmsg.isShowing()) {
                                    this.gs.cmsg.hide();
                                    this.gs.requestFocus();
                                }
                            }
                        } else {
                            if (n5 == 0) {
                                if (this.pgames[this.im] == -1) {
                                    this.ongame = -1;
                                    this.chalngd = -1;
                                } else {
                                    this.join = -2;
                                    this.msg = "| Leaving Game |";
                                    this.longame = -1;
                                }
                            }
                            if (this.pbtn == 1 && n5 == 1) {
                                if (this.pgames[this.im] == -1) {
                                    this.join = this.ongame;
                                    this.msg = "| Joining Game |";
                                    this.spos = 0;
                                } else {
                                    this.invo = !this.invo;
                                }
                            }
                        }
                    }
                } else if (this.ontyp != 76) {
                    if (n5 == 0) {
                        if (this.ontyp < 30) {
                            this.xt.onjoin = this.onjoin;
                            this.xt.ontyp = this.ontyp;
                            this.xt.playingame = -101;
                            this.xt.fase = 23;
                        } else {
                            if (!this.gb.claname.equals(this.xt.clan)) {
                                this.gb.claname = this.xt.clan;
                                this.gb.loadedc = false;
                            }
                            this.gb.spos5 = 0;
                            this.gb.lspos5 = 0;
                            this.gb.cfase = 3;
                            this.gb.loadedcars = -1;
                            this.gb.loadedcar = 0;
                            this.gb.ctab = 2;
                            this.gb.tab = 3;
                            this.gb.open = 2;
                            this.gb.upo = true;
                            this.onjoin = -1;
                        }
                    }
                    if (n5 == 1) {
                        this.onjoin = -1;
                    }
                } else {
                    if (n5 == 0) {
                        this.gs.editlink(this.xt.nickname, true);
                        this.onjoin = -1;
                    }
                    if (n5 == 1) {
                        this.onjoin = -1;
                    }
                }
            } else {
                if (n5 == 0) {
                    this.gs.editlink(this.xt.nickname, true);
                }
                if (n5 == 1) {
                    this.regnow = false;
                }
            }
        }
        this.lxm = n;
        this.lym = n2;
        control.enter = false;
        control.exit = false;
    }

    public void hideinputs() {
        this.gs.cmsg.hide();
        this.gs.swait.hide();
        this.gs.snpls.hide();
        this.gs.snbts.hide();
        this.gs.sgame.hide();
        this.gs.wgame.hide();
        this.gs.pgame.hide();
        this.gs.vnpls.hide();
        this.gs.vtyp.hide();
        this.gs.warb.hide();
        this.gs.mstgs.hide();
        this.gs.snfm1.hide();
        this.gs.snfmm.hide();
        this.gs.slaps.hide();
        this.gs.snfm2.hide();
        this.gs.sclass.hide();
        this.gs.scars.hide();
        this.gs.sfix.hide();
        this.gs.mycar.hide();
        this.gs.notp.hide();
        this.gs.rooms.hide();
        this.gs.requestFocus();
    }

    public void drawSbutton(Image image, int n, int n2) {
        this.bx[this.btn] = n;
        this.by[this.btn] = n2;
        this.bw[this.btn] = image.getWidth(this.ob);
        if (!this.pessd[this.btn]) {
            this.rd.drawImage(image, n - this.bw[this.btn] / 2, n2 - image.getHeight(this.ob) / 2 - 1, null);
            this.rd.drawImage(this.xt.bols, n - this.bw[this.btn] / 2 - 15, n2 - 13, null);
            this.rd.drawImage(this.xt.bors, n + this.bw[this.btn] / 2 + 9, n2 - 13, null);
            this.rd.drawImage(this.xt.bot, n - this.bw[this.btn] / 2 - 9, n2 - 13, this.bw[this.btn] + 18, 3, null);
            this.rd.drawImage(this.xt.bob, n - this.bw[this.btn] / 2 - 9, n2 + 10, this.bw[this.btn] + 18, 3, null);
        } else {
            this.rd.drawImage(image, n - this.bw[this.btn] / 2 + 1, n2 - image.getHeight(this.ob) / 2, null);
            this.rd.drawImage(this.xt.bolps, n - this.bw[this.btn] / 2 - 15, n2 - 13, null);
            this.rd.drawImage(this.xt.borps, n + this.bw[this.btn] / 2 + 9, n2 - 13, null);
            this.rd.drawImage(this.xt.bob, n - this.bw[this.btn] / 2 - 9, n2 - 13, this.bw[this.btn] + 18, 3, null);
            this.rd.drawImage(this.xt.bot, n - this.bw[this.btn] / 2 - 9, n2 + 10, this.bw[this.btn] + 18, 3, null);
        }
        ++this.btn;
    }

    public void stringbutton(String string, int n, int n2, int n3) {
        this.rd.setFont(new Font("Arial", 1, 12));
        this.ftm = this.rd.getFontMetrics();
        this.bx[this.btn] = n;
        this.by[this.btn] = n2 - 5;
        this.bw[this.btn] = this.ftm.stringWidth(string);
        if (!this.pessd[this.btn]) {
            this.rd.setColor(this.color2k(220, 220, 220));
            this.rd.fillRect(n - this.bw[this.btn] / 2 - 10, n2 - (17 - n3), this.bw[this.btn] + 20, 25 - n3 * 2);
            this.rd.setColor(this.color2k(240, 240, 240));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 - (17 - n3), n + this.bw[this.btn] / 2 + 10, n2 - (17 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 - (18 - n3), n + this.bw[this.btn] / 2 + 10, n2 - (18 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 9, n2 - (19 - n3), n + this.bw[this.btn] / 2 + 9, n2 - (19 - n3));
            this.rd.setColor(this.color2k(200, 200, 200));
            this.rd.drawLine(n + this.bw[this.btn] / 2 + 10, n2 - (17 - n3), n + this.bw[this.btn] / 2 + 10, n2 + (7 - n3));
            this.rd.drawLine(n + this.bw[this.btn] / 2 + 11, n2 - (17 - n3), n + this.bw[this.btn] / 2 + 11, n2 + (7 - n3));
            this.rd.drawLine(n + this.bw[this.btn] / 2 + 12, n2 - (16 - n3), n + this.bw[this.btn] / 2 + 12, n2 + (6 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 + (7 - n3), n + this.bw[this.btn] / 2 + 10, n2 + (7 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 + (8 - n3), n + this.bw[this.btn] / 2 + 10, n2 + (8 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 9, n2 + (9 - n3), n + this.bw[this.btn] / 2 + 9, n2 + (9 - n3));
            this.rd.setColor(this.color2k(240, 240, 240));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 - (17 - n3), n - this.bw[this.btn] / 2 - 10, n2 + (7 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 11, n2 - (17 - n3), n - this.bw[this.btn] / 2 - 11, n2 + (7 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 12, n2 - (16 - n3), n - this.bw[this.btn] / 2 - 12, n2 + (6 - n3));
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawString(string, n - this.bw[this.btn] / 2, n2);
        } else {
            this.rd.setColor(this.color2k(210, 210, 210));
            this.rd.fillRect(n - this.bw[this.btn] / 2 - 10, n2 - (17 - n3), this.bw[this.btn] + 20, 25 - n3 * 2);
            this.rd.setColor(this.color2k(200, 200, 200));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 - (17 - n3), n + this.bw[this.btn] / 2 + 10, n2 - (17 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 - (18 - n3), n + this.bw[this.btn] / 2 + 10, n2 - (18 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 9, n2 - (19 - n3), n + this.bw[this.btn] / 2 + 9, n2 - (19 - n3));
            this.rd.drawLine(n + this.bw[this.btn] / 2 + 10, n2 - (17 - n3), n + this.bw[this.btn] / 2 + 10, n2 + (7 - n3));
            this.rd.drawLine(n + this.bw[this.btn] / 2 + 11, n2 - (17 - n3), n + this.bw[this.btn] / 2 + 11, n2 + (7 - n3));
            this.rd.drawLine(n + this.bw[this.btn] / 2 + 12, n2 - (16 - n3), n + this.bw[this.btn] / 2 + 12, n2 + (6 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 + (7 - n3), n + this.bw[this.btn] / 2 + 10, n2 + (7 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 + (8 - n3), n + this.bw[this.btn] / 2 + 10, n2 + (8 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 9, n2 + (9 - n3), n + this.bw[this.btn] / 2 + 9, n2 + (9 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 - (17 - n3), n - this.bw[this.btn] / 2 - 10, n2 + (7 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 11, n2 - (17 - n3), n - this.bw[this.btn] / 2 - 11, n2 + (7 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 12, n2 - (16 - n3), n - this.bw[this.btn] / 2 - 12, n2 + (6 - n3));
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawString(string, n - this.bw[this.btn] / 2 + 1, n2);
        }
        ++this.btn;
    }

    public Color color2k(int n, int n2, int n3) {
        Color color = new Color(n, n2, n3);
        float[] fArray = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), fArray);
        fArray[0] = 0.13f;
        fArray[1] = 0.35f;
        return Color.getHSBColor(fArray[0], fArray[1], fArray[2]);
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

    public int getHvalue(String string, int n) {
        int n2 = -1;
        try {
            int n3 = 0;
            int n4 = 0;
            String string2 = "";
            String string3 = "";
            for (int i = 0; i < string.length() && n4 != 2; ++i) {
                string2 = "" + string.charAt(i);
                if (string2.equals("#")) {
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

    public String getHSvalue(String string, int n) {
        String string2 = "";
        try {
            int n2 = 0;
            int n3 = 0;
            String string3 = "";
            String string4 = "";
            for (int i = 0; i < string.length() && n3 != 2; ++i) {
                string3 = "" + string.charAt(i);
                if (string3.equals("#")) {
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

    public String getSevervalue(String string, int n) {
        String string2 = "";
        if (!string.equals("")) {
            try {
                boolean bl = false;
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
                string2 = "";
            }
        }
        return string2;
    }
}

