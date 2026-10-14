/*
 * Decompiled with CFR 0.152.
 */
import java.applet.Applet;
import java.awt.AlphaComposite;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GameSparker
extends Applet
implements Runnable {
    Graphics2D rd;
    Image offImage;
    Thread gamer;
    int mload = 1;
    boolean exwist = false;
    int apx = 0;
    int apy = 0;
    float apmult = 1.0f;
    float reqmult = 0.0f;
    int smooth = 1;
    int moto = 1;
    int lastw = 0;
    int lasth = 0;
    boolean onbar = false;
    boolean oncarm = false;
    boolean onstgm = false;
    boolean onfulls = false;
    Image sizebar;
    Image blb;
    Image fulls;
    Image[] chkbx = new Image[2];
    Image[] carmaker = new Image[2];
    Image[] stagemaker = new Image[2];
    int showsize = 0;
    Control[] u = new Control[8];
    int mouses = 0;
    int xm = 0;
    int ym = 0;
    int mousew = 0;
    boolean lostfcs = false;
    boolean moused = false;
    int fcscnt = 0;
    int nob = 0;
    int notb = 0;
    int view = 0;
    int mvect = 100;
    int lmxz = 0;
    int shaka = 0;
    boolean applejava = false;
    TextField tnick;
    TextField tpass;
    TextField temail;
    TextField cmsg;
    TextArea mmsg;
    Checkbox mycar;
    Checkbox notp;
    Checkbox keplo;
    boolean openm = false;
    Smenu sgame = new Smenu(8);
    Smenu wgame = new Smenu(4);
    Smenu warb = new Smenu(102);
    Smenu pgame = new Smenu(11);
    Smenu vnpls = new Smenu(5);
    Smenu vtyp = new Smenu(6);
    Smenu snfmm = new Smenu(12);
    Smenu snfm1 = new Smenu(12);
    Smenu snfm2 = new Smenu(19);
    Smenu mstgs = new Smenu(707);
    Smenu mcars = new Smenu(707);
    Smenu slaps = new Smenu(17);
    Smenu snpls = new Smenu(9);
    Smenu snbts = new Smenu(8);
    Smenu swait = new Smenu(6);
    Smenu sclass = new Smenu(7);
    Smenu scars = new Smenu(4);
    Smenu sfix = new Smenu(7);
    Smenu gmode = new Smenu(3);
    Smenu rooms = new Smenu(7);
    Smenu sendtyp = new Smenu(6);
    Smenu senditem = new Smenu(707);
    Smenu clanlev = new Smenu(8);
    Smenu clcars = new Smenu(707);
    Smenu datat = new Smenu(26);
    Smenu ilaps = new Smenu(18);
    Smenu icars = new Smenu(5);
    Smenu proitem = new Smenu(707);

    @Override
    public void run() {
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.fillRect(0, 0, 800, 450);
        this.repaint();
        this.requestFocus();
        if (System.getProperty("java.vendor").toLowerCase().indexOf("apple") != -1) {
            this.applejava = true;
        }
        Medium medium = new Medium();
        Trackers trackers = new Trackers();
        CheckPoints checkPoints = new CheckPoints();
        ContO[] contOArray = new ContO[124];
        CarDefine carDefine = new CarDefine(contOArray, medium, trackers, this);
        xtGraphics xtGraphics2 = new xtGraphics(medium, carDefine, this.rd, this);
        this.sizebar = xtGraphics2.getImage("data/sizebar.gif");
        this.blb = xtGraphics2.getImage("data/b.gif");
        this.fulls = xtGraphics2.getImage("data/fullscreen.gif");
        this.chkbx[0] = xtGraphics2.getImage("data/checkbox1.gif");
        this.chkbx[1] = xtGraphics2.getImage("data/checkbox2.gif");
        this.carmaker[0] = xtGraphics2.getImage("data/carmaker1.gif");
        this.carmaker[1] = xtGraphics2.getImage("data/carmaker2.gif");
        this.stagemaker[0] = xtGraphics2.getImage("data/stagemaker1.gif");
        this.stagemaker[1] = xtGraphics2.getImage("data/stagemaker2.gif");
        xtGraphics2.loaddata();
        Login login = null;
        Lobby lobby = null;
        Globe globe = null;
        boolean bl = false;
        UDPMistro uDPMistro = new UDPMistro();
        Record record = new Record(medium);
        this.loadbase(contOArray, medium, trackers, xtGraphics2, false);
        ContO[] contOArray2 = new ContO[610];
        Mad[] madArray = new Mad[8];
        for (int i = 0; i < 8; ++i) {
            madArray[i] = new Mad(carDefine, medium, record, xtGraphics2, i);
            this.u[i] = new Control(medium);
        }
        float f = 47.0f;
        this.readcookies(xtGraphics2, carDefine, contOArray);
        xtGraphics2.testdrive = Madness.testdrive;
        if (xtGraphics2.testdrive != 0) {
            if (xtGraphics2.testdrive <= 2) {
                xtGraphics2.sc[0] = carDefine.loadcar(Madness.testcar, 16);
                if (xtGraphics2.sc[0] != -1) {
                    xtGraphics2.fase = -9;
                } else {
                    Madness.testcar = "Failx12";
                    Madness.carmaker();
                }
            } else {
                checkPoints.name = Madness.testcar;
                xtGraphics2.fase = -9;
            }
        }
        xtGraphics2.stoploading();
        this.requestFocus();
        if (xtGraphics2.testdrive == 0 && xtGraphics2.firstime) {
            this.setupini();
        }
        System.gc();
        Date date = new Date();
        long l = 0L;
        long l2 = date.getTime();
        float f2 = 30.0f;
        boolean bl2 = false;
        int n = 30;
        int n2 = 530;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        boolean bl3 = false;
        while (true) {
            int n8;
            int n9;
            int n10;
            int[] nArray;
            int n11;
            date = new Date();
            long l3 = date.getTime();
            if (xtGraphics2.fase == 111) {
                if (this.mouses == 1) {
                    n5 = 800;
                }
                if (n5 < 800) {
                    xtGraphics2.clicknow();
                    ++n5;
                } else {
                    n5 = 0;
                    if (!this.exwist) {
                        xtGraphics2.fase = 9;
                    }
                    this.mouses = 0;
                    this.lostfcs = false;
                }
            }
            if (xtGraphics2.fase == 9) {
                if (n5 < 76) {
                    xtGraphics2.rad(n5);
                    this.catchlink();
                    if (this.mouses == 2) {
                        this.mouses = 0;
                    }
                    if (this.mouses == 1) {
                        this.mouses = 2;
                    }
                    ++n5;
                } else {
                    n5 = 0;
                    xtGraphics2.fase = 10;
                    this.mouses = 0;
                    this.u[0].falseo(0);
                }
            }
            if (xtGraphics2.fase == -9) {
                if (xtGraphics2.loadedt) {
                    xtGraphics2.mainbg(-101);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.fillRect(0, 0, 800, 450);
                    this.repaint();
                    xtGraphics2.strack.unload();
                    xtGraphics2.strack = null;
                    xtGraphics2.flexpix = null;
                    xtGraphics2.fleximg = null;
                    System.gc();
                    xtGraphics2.loadedt = false;
                }
                if (n5 < 2) {
                    xtGraphics2.mainbg(-101);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.fillRect(65, 25, 670, 400);
                    ++n5;
                } else {
                    this.checkmemory(xtGraphics2);
                    xtGraphics2.inishcarselect(contOArray);
                    n5 = 0;
                    xtGraphics2.fase = 7;
                    this.mvect = 50;
                    this.mouses = 0;
                }
            }
            if (xtGraphics2.fase == 8) {
                xtGraphics2.credits(this.u[0], this.xm, this.ym, this.mouses);
                xtGraphics2.ctachm(this.xm, this.ym, this.mouses, this.u[0]);
                if (xtGraphics2.flipo <= 100) {
                    this.catchlink();
                }
                if (this.mouses == 2) {
                    this.mouses = 0;
                }
                if (this.mouses == 1) {
                    this.mouses = 2;
                }
            }
            if (xtGraphics2.fase == 10) {
                this.mvect = 100;
                xtGraphics2.maini(this.u[0]);
                xtGraphics2.ctachm(this.xm, this.ym, this.mouses, this.u[0]);
                if (this.mouses == 2) {
                    this.mouses = 0;
                }
                if (this.mouses == 1) {
                    this.mouses = 2;
                }
            }
            if (xtGraphics2.fase == 102) {
                this.mvect = 100;
                if (xtGraphics2.loadedt) {
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.fillRect(0, 0, 800, 450);
                    this.repaint();
                    this.checkmemory(xtGraphics2);
                    xtGraphics2.strack.unload();
                    xtGraphics2.strack = null;
                    xtGraphics2.flexpix = null;
                    xtGraphics2.fleximg = null;
                    System.gc();
                    xtGraphics2.loadedt = false;
                }
                if (xtGraphics2.testdrive == 1 || xtGraphics2.testdrive == 2) {
                    Madness.carmaker();
                }
                if (xtGraphics2.testdrive == 3 || xtGraphics2.testdrive == 4) {
                    Madness.stagemaker();
                }
                xtGraphics2.maini2(this.u[0], this.xm, this.ym, this.mouses);
                xtGraphics2.ctachm(this.xm, this.ym, this.mouses, this.u[0]);
                if (this.mouses == 2) {
                    this.mouses = 0;
                }
                if (this.mouses == 1) {
                    this.mouses = 2;
                }
            }
            if (xtGraphics2.fase == -22) {
                checkPoints.name = Madness.testcar;
                checkPoints.stage = -1;
                this.loadstage(contOArray2, contOArray, medium, trackers, checkPoints, xtGraphics2, madArray, record);
                if (checkPoints.stage == -3) {
                    Madness.testcar = "Failx12";
                    Madness.stagemaker();
                }
            }
            if (xtGraphics2.fase == 11) {
                xtGraphics2.inst(this.u[0]);
                xtGraphics2.ctachm(this.xm, this.ym, this.mouses, this.u[0]);
                if (this.mouses == 2) {
                    this.mouses = 0;
                }
                if (this.mouses == 1) {
                    this.mouses = 2;
                }
            }
            if (xtGraphics2.fase == -5) {
                this.mvect = 100;
                xtGraphics2.finish(checkPoints, contOArray, this.u[0], this.xm, this.ym, this.moused);
                xtGraphics2.ctachm(this.xm, this.ym, this.mouses, this.u[0]);
                if (this.mouses == 2) {
                    this.mouses = 0;
                }
                if (this.mouses == 1) {
                    this.mouses = 2;
                }
            }
            if (xtGraphics2.fase == 7) {
                xtGraphics2.carselect(this.u[0], contOArray, madArray[0], this.xm, this.ym, this.moused);
                xtGraphics2.ctachm(this.xm, this.ym, this.mouses, this.u[0]);
                if (this.mouses == 2) {
                    this.mouses = 0;
                }
                if (this.mouses == 1) {
                    this.mouses = 2;
                }
                this.drawms();
            }
            if (xtGraphics2.fase == 6) {
                xtGraphics2.musicomp(checkPoints.stage, this.u[0]);
                xtGraphics2.ctachm(this.xm, this.ym, this.mouses, this.u[0]);
                if (this.mouses == 2) {
                    this.mouses = 0;
                }
                if (this.mouses == 1) {
                    this.mouses = 2;
                }
            }
            if (xtGraphics2.fase == 5) {
                this.mvect = 100;
                xtGraphics2.loadmusic(checkPoints.stage, checkPoints.trackname, checkPoints.trackvol);
            }
            if (xtGraphics2.fase == 4) {
                xtGraphics2.cantgo(this.u[0]);
                xtGraphics2.ctachm(this.xm, this.ym, this.mouses, this.u[0]);
                if (this.mouses == 2) {
                    this.mouses = 0;
                }
                if (this.mouses == 1) {
                    this.mouses = 2;
                }
            }
            if (xtGraphics2.fase == 3) {
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.fillRect(65, 25, 670, 400);
                this.repaint();
                xtGraphics2.inishstageselect(checkPoints);
            }
            if (xtGraphics2.fase == 2) {
                this.mvect = 100;
                xtGraphics2.loadingstage(checkPoints.stage, true);
                checkPoints.nfix = 0;
                checkPoints.notb = false;
                this.loadstage(contOArray2, contOArray, medium, trackers, checkPoints, xtGraphics2, madArray, record);
                this.u[0].falseo(0);
                uDPMistro.freg = 0.0f;
                this.mvect = 20;
            }
            if (xtGraphics2.fase == 1) {
                xtGraphics2.trackbg(false);
                this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                if (checkPoints.stage != -3) {
                    medium.aroundtrack(checkPoints);
                    if (medium.hit == 5000 && this.mvect < 40) {
                        ++this.mvect;
                    }
                    n11 = 0;
                    nArray = new int[1000];
                    for (int i = xtGraphics2.nplayers; i < this.notb; ++i) {
                        if (contOArray2[i].dist != 0) {
                            nArray[n11] = i;
                            ++n11;
                            continue;
                        }
                        contOArray2[i].d(this.rd);
                    }
                    int[] nArray2 = new int[n11];
                    for (n10 = 0; n10 < n11; ++n10) {
                        nArray2[n10] = 0;
                    }
                    for (n10 = 0; n10 < n11; ++n10) {
                        for (n9 = n10 + 1; n9 < n11; ++n9) {
                            if (contOArray2[nArray[n10]].dist != contOArray2[nArray[n9]].dist) {
                                if (contOArray2[nArray[n10]].dist < contOArray2[nArray[n9]].dist) {
                                    int n12 = n10;
                                    nArray2[n12] = nArray2[n12] + 1;
                                    continue;
                                }
                                int n13 = n9;
                                nArray2[n13] = nArray2[n13] + 1;
                                continue;
                            }
                            if (n9 > n10) {
                                int n14 = n10;
                                nArray2[n14] = nArray2[n14] + 1;
                                continue;
                            }
                            int n15 = n9;
                            nArray2[n15] = nArray2[n15] + 1;
                        }
                    }
                    for (n10 = 0; n10 < n11; ++n10) {
                        for (n9 = 0; n9 < n11; ++n9) {
                            if (nArray2[n9] != n10) continue;
                            contOArray2[nArray[n9]].d(this.rd);
                        }
                    }
                }
                if (!this.openm) {
                    xtGraphics2.ctachm(this.xm, this.ym, this.mouses, this.u[0]);
                }
                if (this.mouses == 2) {
                    this.mouses = 0;
                }
                if (this.mouses == 1) {
                    this.mouses = 2;
                }
                this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                xtGraphics2.stageselect(checkPoints, this.u[0], this.xm, this.ym, this.moused);
                this.drawms();
            }
            if (xtGraphics2.fase == 1177) {
                this.mvect = 100;
                if (!bl) {
                    if (xtGraphics2.loadedt) {
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.fillRect(0, 0, 800, 450);
                        this.repaint();
                        this.checkmemory(xtGraphics2);
                        xtGraphics2.strack.unload();
                        xtGraphics2.strack = null;
                        xtGraphics2.flexpix = null;
                        xtGraphics2.fleximg = null;
                        System.gc();
                        xtGraphics2.loadedt = false;
                    }
                    xtGraphics2.intertrack.unloadimod();
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.fillRect(65, 25, 670, 400);
                    if (this.mload > 0) {
                        this.rd.drawImage(xtGraphics2.mload, 259, 195, this);
                    }
                    this.repaint();
                    if (this.mload == 2) {
                        carDefine.loadready();
                        this.loadbase(contOArray, medium, trackers, xtGraphics2, true);
                        this.readcookies(xtGraphics2, carDefine, contOArray);
                        this.mload = -1;
                    }
                    System.gc();
                    login = new Login(medium, this.rd, xtGraphics2, this);
                    globe = new Globe(this.rd, xtGraphics2, medium, login, carDefine, checkPoints, contOArray, contOArray2, this);
                    lobby = new Lobby(medium, this.rd, login, globe, xtGraphics2, carDefine, this);
                    bl = true;
                }
                if (login.fase != 18) {
                    n11 = 0;
                    if (login.fase == 0) {
                        login.inishmulti();
                    }
                    if (login.fase >= 1 && login.fase <= 11) {
                        login.multistart(contOArray, this.xm, this.ym, this.moused);
                    }
                    if (login.fase >= 12 && login.fase <= 17) {
                        if (globe.open != 452) {
                            login.multimode(contOArray);
                        } else {
                            n11 = 1;
                        }
                        globe.dome(0, this.xm, this.ym, this.moused, this.u[0]);
                    }
                    if (login.justlog) {
                        if (!xtGraphics2.clan.equals("")) {
                            globe.itab = 2;
                        }
                        login.justlog = false;
                    }
                    if (n11 == 0) {
                        login.ctachm(this.xm, this.ym, this.mouses, this.u[0], lobby);
                        this.mvect = 50;
                    } else {
                        this.drawms();
                        this.mvect = 100;
                    }
                    if (this.mouses == 1) {
                        this.mouses = 11;
                    }
                    if (this.mouses <= -1) {
                        --this.mouses;
                        if (this.mouses == -4) {
                            this.mouses = 0;
                        }
                    }
                    if (this.mousew != 0) {
                        this.mousew = this.mousew > 0 ? --this.mousew : ++this.mousew;
                    }
                } else {
                    n11 = 0;
                    if (lobby.fase == 0) {
                        lobby.inishlobby();
                        this.mvect = 100;
                    }
                    if (lobby.fase == 1) {
                        if (globe.open >= 2 && globe.open < 452) {
                            this.openm = true;
                        }
                        if (globe.open != 452) {
                            lobby.lobby(this.xm, this.ym, this.moused, this.mousew, checkPoints, this.u[0], contOArray);
                        } else {
                            n11 = 1;
                        }
                        globe.dome(lobby.conon, this.xm, this.ym, this.moused, this.u[0]);
                        if (lobby.loadstage > 0) {
                            this.setCursor(new Cursor(3));
                            this.drawms();
                            this.repaint();
                            trackers.nt = 0;
                            if (this.loadstagePreview(lobby.loadstage, "", contOArray2, contOArray, medium, checkPoints)) {
                                lobby.gstagename = checkPoints.name;
                                lobby.gstagelaps = checkPoints.nlaps;
                                lobby.loadstage = -lobby.loadstage;
                            } else {
                                lobby.loadstage = 0;
                                checkPoints.name = "";
                            }
                            this.setCursor(new Cursor(0));
                        }
                        if (lobby.msload != 0) {
                            this.setCursor(new Cursor(3));
                            this.drawms();
                            this.repaint();
                            if (lobby.msload == 1) {
                                carDefine.loadmystages(checkPoints);
                            }
                            if (lobby.msload == 7) {
                                carDefine.loadclanstages(xtGraphics2.clan);
                            }
                            if (lobby.msload == 3 || lobby.msload == 4) {
                                carDefine.loadtop20(lobby.msload);
                            }
                            lobby.msload = 0;
                            this.setCursor(new Cursor(0));
                        }
                    }
                    if (lobby.fase == 3) {
                        xtGraphics2.trackbg(false);
                        medium.trk = 0;
                        medium.focus_point = 400;
                        medium.crs = true;
                        medium.x = -335;
                        medium.y = 0;
                        medium.z = -50;
                        medium.xz = 0;
                        medium.zy = 20;
                        medium.ground = -2000;
                        this.mvect = 100;
                        lobby.fase = 1;
                    }
                    if (lobby.fase == 4) {
                        this.mvect = 50;
                        this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                        medium.d(this.rd);
                        medium.aroundtrack(checkPoints);
                        int n16 = 0;
                        int[] nArray3 = new int[1000];
                        for (n10 = 0; n10 < this.nob; ++n10) {
                            if (contOArray2[n10].dist != 0) {
                                nArray3[n16] = n10;
                                ++n16;
                                continue;
                            }
                            contOArray2[n10].d(this.rd);
                        }
                        int[] nArray4 = new int[n16];
                        for (n9 = 0; n9 < n16; ++n9) {
                            nArray4[n9] = 0;
                        }
                        for (n9 = 0; n9 < n16; ++n9) {
                            for (n8 = n9 + 1; n8 < n16; ++n8) {
                                if (contOArray2[nArray3[n9]].dist != contOArray2[nArray3[n8]].dist) {
                                    if (contOArray2[nArray3[n9]].dist < contOArray2[nArray3[n8]].dist) {
                                        int n17 = n9;
                                        nArray4[n17] = nArray4[n17] + 1;
                                        continue;
                                    }
                                    int n18 = n8;
                                    nArray4[n18] = nArray4[n18] + 1;
                                    continue;
                                }
                                if (n8 > n9) {
                                    int n19 = n9;
                                    nArray4[n19] = nArray4[n19] + 1;
                                    continue;
                                }
                                int n20 = n8;
                                nArray4[n20] = nArray4[n20] + 1;
                            }
                        }
                        for (n9 = 0; n9 < n16; ++n9) {
                            for (n8 = 0; n8 < n16; ++n8) {
                                if (nArray4[n8] != n9) continue;
                                contOArray2[nArray3[n8]].d(this.rd);
                            }
                        }
                        this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        lobby.stageselect(checkPoints, this.u[0], this.xm, this.ym, this.moused);
                        if (lobby.plsndt == 1) {
                            this.mvect = 70;
                            this.repaint();
                            this.setCursor(new Cursor(3));
                            xtGraphics2.loadstrack(checkPoints.stage, checkPoints.trackname, checkPoints.trackvol);
                            xtGraphics2.strack.play();
                            lobby.plsndt = 2;
                            this.moused = false;
                            this.mouses = 0;
                        }
                    }
                    if (lobby.fase == 2) {
                        int n21;
                        int n22 = 0;
                        for (n21 = 0; n21 < lobby.ngm; ++n21) {
                            if (lobby.ongame != lobby.gnum[n21]) continue;
                            n22 = n21;
                        }
                        n21 = 0;
                        if (lobby.gstgn[n22] > 0) {
                            if (lobby.gstgn[n22] == -lobby.loadstage) {
                                n21 = 1;
                            }
                        } else if (lobby.gstages[n22].equals(checkPoints.name)) {
                            n21 = 1;
                        }
                        if (n21 != 0) {
                            lobby.fase = 4;
                            lobby.addstage = 0;
                        } else {
                            xtGraphics2.loadingstage(lobby.gstgn[n22], false);
                            trackers.nt = 0;
                            if (this.loadstagePreview(lobby.gstgn[n22], lobby.gstages[n22], contOArray2, contOArray, medium, checkPoints)) {
                                lobby.loadstage = -lobby.gstgn[n22];
                                lobby.fase = 4;
                                lobby.addstage = 0;
                            } else {
                                lobby.loadstage = 0;
                                checkPoints.name = "";
                                lobby.fase = 3;
                            }
                        }
                    }
                    if (lobby.fase == 76) {
                        checkPoints.nlaps = lobby.laps;
                        checkPoints.stage = lobby.stage;
                        checkPoints.name = lobby.stagename;
                        checkPoints.nfix = lobby.nfix;
                        checkPoints.notb = lobby.notb;
                        xtGraphics2.fase = 21;
                        this.u[0].multion = xtGraphics2.multion;
                    }
                    if (globe.loadwbgames == 7) {
                        this.repaint();
                        globe.redogame();
                    }
                    if (!this.openm) {
                        if (n11 == 0) {
                            lobby.ctachm(this.xm, this.ym, this.mouses, this.u[0]);
                        }
                    } else {
                        this.mouses = 0;
                    }
                    this.drawms();
                    if (lobby.fase == 1) {
                        lobby.preforma(this.xm, this.ym);
                    }
                    if (lobby.loadwarb) {
                        this.repaint();
                        globe.loadwarb();
                        lobby.loadwarb = false;
                    }
                    if (globe.loadwbgames == 1) {
                        this.repaint();
                        globe.loadwgames();
                    }
                    if (this.mouses == 1) {
                        this.mouses = 11;
                    }
                    if (this.mouses <= -1) {
                        --this.mouses;
                        if (this.mouses == -4) {
                            this.mouses = 0;
                        }
                    }
                    if (this.mousew != 0) {
                        this.mousew = this.mousew > 0 ? --this.mousew : ++this.mousew;
                        if (!lobby.zeromsw) {
                            this.mousew = 0;
                        }
                    }
                }
            }
            if (xtGraphics2.fase == 24) {
                login.endcons();
                login = null;
                lobby = null;
                globe = null;
                bl = false;
                System.gc();
                System.runFinalization();
                if (!xtGraphics2.mtop) {
                    xtGraphics2.fase = 102;
                    xtGraphics2.opselect = 2;
                } else {
                    xtGraphics2.fase = 10;
                    xtGraphics2.opselect = 1;
                }
            }
            if (xtGraphics2.fase == 23) {
                if (login.fase == 18) {
                    xtGraphics2.playingame = -101;
                }
                login.stopallnow();
                lobby.stopallnow();
                globe.stopallnow();
                login = null;
                lobby = null;
                globe = null;
                this.hidefields();
                bl = false;
                System.gc();
                System.runFinalization();
                xtGraphics2.fase = -9;
            }
            if (xtGraphics2.fase == 22) {
                this.loadstage(contOArray2, contOArray, medium, trackers, checkPoints, xtGraphics2, madArray, record);
                if (checkPoints.stage != -3) {
                    if (xtGraphics2.lan && xtGraphics2.im == 0) {
                        uDPMistro.UDPLanServer(xtGraphics2.nplayers, xtGraphics2.server, xtGraphics2.servport, xtGraphics2.playingame);
                    }
                    this.u[0].falseo(2);
                    this.requestFocus();
                } else {
                    xtGraphics2.fase = 1177;
                }
            }
            if (xtGraphics2.fase == 21) {
                login.endcons();
                login = null;
                lobby = null;
                globe = null;
                bl = false;
                System.gc();
                System.runFinalization();
                xtGraphics2.fase = 22;
            }
            if (xtGraphics2.fase == 0) {
                for (n11 = 0; n11 < xtGraphics2.nplayers; ++n11) {
                    if (!madArray[n11].newcar) continue;
                    int n23 = contOArray2[n11].xz;
                    int n24 = contOArray2[n11].xy;
                    int n25 = contOArray2[n11].zy;
                    contOArray2[n11] = new ContO(contOArray[madArray[n11].cn], contOArray2[n11].x, contOArray2[n11].y, contOArray2[n11].z, 0);
                    contOArray2[n11].xz = n23;
                    contOArray2[n11].xy = n24;
                    contOArray2[n11].zy = n25;
                    madArray[n11].newcar = false;
                }
                medium.d(this.rd);
                n11 = 0;
                nArray = new int[200];
                for (int i = 0; i < this.nob; ++i) {
                    if (contOArray2[i].dist != 0) {
                        nArray[n11] = i;
                        ++n11;
                        continue;
                    }
                    contOArray2[i].d(this.rd);
                }
                int[] nArray5 = new int[n11];
                int[] nArray6 = new int[n11];
                for (n9 = 0; n9 < n11; ++n9) {
                    nArray5[n9] = 0;
                }
                for (n9 = 0; n9 < n11; ++n9) {
                    for (n8 = n9 + 1; n8 < n11; ++n8) {
                        if (contOArray2[nArray[n9]].dist < contOArray2[nArray[n8]].dist) {
                            int n26 = n9;
                            nArray5[n26] = nArray5[n26] + 1;
                            continue;
                        }
                        int n27 = n8;
                        nArray5[n27] = nArray5[n27] + 1;
                    }
                    nArray6[nArray5[n9]] = n9;
                }
                for (n9 = 0; n9 < n11; ++n9) {
                    contOArray2[nArray[nArray6[n9]]].d(this.rd);
                }
                if (xtGraphics2.starcnt == 0) {
                    for (n9 = 0; n9 < xtGraphics2.nplayers; ++n9) {
                        for (n8 = 0; n8 < xtGraphics2.nplayers; ++n8) {
                            if (n8 == n9) continue;
                            madArray[n9].colide(contOArray2[n9], madArray[n8], contOArray2[n8]);
                        }
                    }
                    for (n9 = 0; n9 < xtGraphics2.nplayers; ++n9) {
                        madArray[n9].drive(this.u[n9], contOArray2[n9], trackers, checkPoints);
                    }
                    for (n9 = 0; n9 < xtGraphics2.nplayers; ++n9) {
                        record.rec(contOArray2[n9], n9, madArray[n9].squash, madArray[n9].lastcolido, madArray[n9].cntdest, 0);
                    }
                    checkPoints.checkstat(madArray, contOArray2, record, xtGraphics2.nplayers, xtGraphics2.im, 0);
                    for (n9 = 1; n9 < xtGraphics2.nplayers; ++n9) {
                        this.u[n9].preform(madArray[n9], contOArray2[n9], checkPoints, trackers);
                    }
                } else {
                    if (xtGraphics2.starcnt == 130) {
                        medium.adv = 1900;
                        medium.zy = 40;
                        medium.vxz = 70;
                        this.rd.setColor(new Color(255, 255, 255));
                        this.rd.fillRect(0, 0, 800, 450);
                    }
                    if (xtGraphics2.starcnt != 0) {
                        --xtGraphics2.starcnt;
                    }
                }
                if (xtGraphics2.starcnt < 38) {
                    if (this.view == 0) {
                        medium.follow(contOArray2[0], madArray[0].cxz, this.u[0].lookback);
                        xtGraphics2.stat(madArray[0], contOArray2[0], checkPoints, this.u[0], true);
                        if (madArray[0].outshakedam > 0) {
                            this.shaka = madArray[0].outshakedam / 20;
                            if (this.shaka > 25) {
                                this.shaka = 25;
                            }
                        }
                        this.mvect = 65 + Math.abs(this.lmxz - medium.xz) / 5 * 100;
                        if (this.mvect > 90) {
                            this.mvect = 90;
                        }
                        this.lmxz = medium.xz;
                    }
                    if (this.view == 1) {
                        medium.around(contOArray2[0], false);
                        xtGraphics2.stat(madArray[0], contOArray2[0], checkPoints, this.u[0], false);
                        this.mvect = 80;
                    }
                    if (this.view == 2) {
                        medium.watch(contOArray2[0], madArray[0].mxz);
                        xtGraphics2.stat(madArray[0], contOArray2[0], checkPoints, this.u[0], false);
                        this.mvect = 65 + Math.abs(this.lmxz - medium.xz) / 5 * 100;
                        if (this.mvect > 90) {
                            this.mvect = 90;
                        }
                        this.lmxz = medium.xz;
                    }
                    if (this.mouses == 1) {
                        this.u[0].enter = true;
                        this.mouses = 0;
                    }
                } else {
                    n9 = 3;
                    if (xtGraphics2.nplayers == 1) {
                        n9 = 0;
                    }
                    medium.around(contOArray2[n9], true);
                    this.mvect = 80;
                    if (this.u[0].enter || this.u[0].handb) {
                        xtGraphics2.starcnt = 38;
                        this.u[0].enter = false;
                        this.u[0].handb = false;
                    }
                    if (xtGraphics2.starcnt == 38) {
                        this.mouses = 0;
                        medium.vert = false;
                        medium.adv = 900;
                        medium.vxz = 180;
                        checkPoints.checkstat(madArray, contOArray2, record, xtGraphics2.nplayers, xtGraphics2.im, 0);
                        medium.follow(contOArray2[0], madArray[0].cxz, 0);
                        xtGraphics2.stat(madArray[0], contOArray2[0], checkPoints, this.u[0], true);
                        this.rd.setColor(new Color(255, 255, 255));
                        this.rd.fillRect(0, 0, 800, 450);
                    }
                }
            }
            if (xtGraphics2.fase == 7001) {
                for (n11 = 0; n11 < xtGraphics2.nplayers; ++n11) {
                    if (madArray[n11].newedcar != 0 || !madArray[n11].newcar) continue;
                    int n28 = contOArray2[n11].xz;
                    int n29 = contOArray2[n11].xy;
                    int n30 = contOArray2[n11].zy;
                    xtGraphics2.colorCar(contOArray[madArray[n11].cn], n11);
                    contOArray2[n11] = new ContO(contOArray[madArray[n11].cn], contOArray2[n11].x, contOArray2[n11].y, contOArray2[n11].z, 0);
                    contOArray2[n11].xz = n28;
                    contOArray2[n11].xy = n29;
                    contOArray2[n11].zy = n30;
                    madArray[n11].newedcar = 20;
                }
                medium.d(this.rd);
                n11 = 0;
                nArray = new int[200];
                for (int i = 0; i < this.nob; ++i) {
                    if (contOArray2[i].dist != 0) {
                        nArray[n11] = i;
                        ++n11;
                        continue;
                    }
                    contOArray2[i].d(this.rd);
                }
                int[] nArray7 = new int[n11];
                int[] nArray8 = new int[n11];
                for (n9 = 0; n9 < n11; ++n9) {
                    nArray7[n9] = 0;
                }
                for (n9 = 0; n9 < n11; ++n9) {
                    for (n8 = n9 + 1; n8 < n11; ++n8) {
                        if (contOArray2[nArray[n9]].dist < contOArray2[nArray[n8]].dist) {
                            int n31 = n9;
                            nArray7[n31] = nArray7[n31] + 1;
                            continue;
                        }
                        int n32 = n8;
                        nArray7[n32] = nArray7[n32] + 1;
                    }
                    nArray8[nArray7[n9]] = n9;
                }
                for (n9 = 0; n9 < n11; ++n9) {
                    if (nArray[nArray8[n9]] < xtGraphics2.nplayers && nArray[nArray8[n9]] != xtGraphics2.im) {
                        uDPMistro.readContOinfo(contOArray2[nArray[nArray8[n9]]], nArray[nArray8[n9]]);
                    }
                    contOArray2[nArray[nArray8[n9]]].d(this.rd);
                }
                if (xtGraphics2.starcnt == 0) {
                    if (xtGraphics2.multion == 1) {
                        n9 = 1;
                        for (n8 = 0; n8 < xtGraphics2.nplayers; ++n8) {
                            if (xtGraphics2.im == n8) continue;
                            uDPMistro.readinfo(madArray[n8], contOArray2[n8], this.u[n9], n8, checkPoints.dested);
                            ++n9;
                        }
                    } else {
                        for (n9 = 0; n9 < xtGraphics2.nplayers; ++n9) {
                            uDPMistro.readinfo(madArray[n9], contOArray2[n9], this.u[n9], n9, checkPoints.dested);
                        }
                    }
                    for (n9 = 0; n9 < xtGraphics2.nplayers; ++n9) {
                        for (n8 = 0; n8 < xtGraphics2.nplayers; ++n8) {
                            if (n8 == n9) continue;
                            madArray[n9].colide(contOArray2[n9], madArray[n8], contOArray2[n8]);
                        }
                    }
                    if (xtGraphics2.multion == 1) {
                        n9 = 1;
                        for (n8 = 0; n8 < xtGraphics2.nplayers; ++n8) {
                            if (xtGraphics2.im != n8) {
                                madArray[n8].drive(this.u[n9], contOArray2[n8], trackers, checkPoints);
                                ++n9;
                                continue;
                            }
                            madArray[n8].drive(this.u[0], contOArray2[n8], trackers, checkPoints);
                        }
                        for (n8 = 0; n8 < xtGraphics2.nplayers; ++n8) {
                            record.rec(contOArray2[n8], n8, madArray[n8].squash, madArray[n8].lastcolido, madArray[n8].cntdest, xtGraphics2.im);
                        }
                    } else {
                        for (n9 = 0; n9 < xtGraphics2.nplayers; ++n9) {
                            madArray[n9].drive(this.u[n9], contOArray2[n9], trackers, checkPoints);
                        }
                    }
                    checkPoints.checkstat(madArray, contOArray2, record, xtGraphics2.nplayers, xtGraphics2.im, xtGraphics2.multion);
                } else {
                    if (xtGraphics2.starcnt == 130) {
                        medium.adv = 1900;
                        medium.zy = 40;
                        medium.vxz = 70;
                        this.rd.setColor(new Color(255, 255, 255));
                        this.rd.fillRect(0, 0, 800, 450);
                        this.repaint();
                        if (xtGraphics2.lan) {
                            uDPMistro.UDPConnectLan(xtGraphics2.localserver, xtGraphics2.nplayers, xtGraphics2.im);
                            if (xtGraphics2.im == 0) {
                                xtGraphics2.setbots(uDPMistro.isbot, uDPMistro.frame);
                            }
                        } else {
                            uDPMistro.UDPConnectOnline(xtGraphics2.server, xtGraphics2.gameport, xtGraphics2.nplayers, xtGraphics2.im);
                        }
                        if (xtGraphics2.multion >= 2) {
                            xtGraphics2.im = (int)(Math.random() * (double)xtGraphics2.nplayers);
                            xtGraphics2.starcnt = 0;
                        }
                    }
                    if (xtGraphics2.starcnt == 50) {
                        uDPMistro.frame[uDPMistro.im][0] = 0;
                    }
                    if (xtGraphics2.starcnt != 39 && xtGraphics2.starcnt != 0) {
                        --xtGraphics2.starcnt;
                    }
                    if (uDPMistro.go && xtGraphics2.starcnt >= 39) {
                        xtGraphics2.starcnt = 38;
                        if (xtGraphics2.lan) {
                            n9 = checkPoints.stage;
                            if (n9 < 0) {
                                n9 = 33;
                            }
                            if (xtGraphics2.loadedt) {
                                xtGraphics2.strack.play();
                            }
                        }
                    }
                }
                if (xtGraphics2.lan && uDPMistro.im == 0) {
                    for (n9 = 2; n9 < xtGraphics2.nplayers; ++n9) {
                        if (!uDPMistro.isbot[n9]) continue;
                        this.u[n9].preform(madArray[n9], contOArray2[n9], checkPoints, trackers);
                        uDPMistro.setinfo(madArray[n9], contOArray2[n9], this.u[n9], checkPoints.pos[n9], checkPoints.magperc[n9], false, n9);
                    }
                }
                if (xtGraphics2.starcnt < 38) {
                    if (xtGraphics2.multion == 1) {
                        uDPMistro.setinfo(madArray[xtGraphics2.im], contOArray2[xtGraphics2.im], this.u[0], checkPoints.pos[xtGraphics2.im], checkPoints.magperc[xtGraphics2.im], xtGraphics2.holdit, xtGraphics2.im);
                        if (this.view == 0) {
                            medium.follow(contOArray2[xtGraphics2.im], madArray[xtGraphics2.im].cxz, this.u[0].lookback);
                            xtGraphics2.stat(madArray[xtGraphics2.im], contOArray2[xtGraphics2.im], checkPoints, this.u[0], true);
                            if (madArray[xtGraphics2.im].outshakedam > 0) {
                                this.shaka = madArray[xtGraphics2.im].outshakedam / 20;
                                if (this.shaka > 25) {
                                    this.shaka = 25;
                                }
                            }
                            this.mvect = 65 + Math.abs(this.lmxz - medium.xz) / 5 * 100;
                            if (this.mvect > 90) {
                                this.mvect = 90;
                            }
                            this.lmxz = medium.xz;
                        }
                        if (this.view == 1) {
                            medium.around(contOArray2[xtGraphics2.im], false);
                            xtGraphics2.stat(madArray[xtGraphics2.im], contOArray2[xtGraphics2.im], checkPoints, this.u[0], false);
                            this.mvect = 80;
                        }
                        if (this.view == 2) {
                            medium.watch(contOArray2[xtGraphics2.im], madArray[xtGraphics2.im].mxz);
                            xtGraphics2.stat(madArray[xtGraphics2.im], contOArray2[xtGraphics2.im], checkPoints, this.u[0], false);
                            this.mvect = 65 + Math.abs(this.lmxz - medium.xz) / 5 * 100;
                            if (this.mvect > 90) {
                                this.mvect = 90;
                            }
                            this.lmxz = medium.xz;
                        }
                    } else {
                        if (this.view == 0) {
                            medium.getaround(contOArray2[xtGraphics2.im]);
                            this.mvect = 80;
                        }
                        if (this.view == 1) {
                            medium.getfollow(contOArray2[xtGraphics2.im], madArray[xtGraphics2.im].cxz, this.u[0].lookback);
                            this.mvect = 65 + Math.abs(this.lmxz - medium.xz) / 5 * 100;
                            if (this.mvect > 90) {
                                this.mvect = 90;
                            }
                            this.lmxz = medium.xz;
                        }
                        if (this.view == 2) {
                            medium.watch(contOArray2[xtGraphics2.im], madArray[xtGraphics2.im].mxz);
                            this.mvect = 65 + Math.abs(this.lmxz - medium.xz) / 5 * 100;
                            if (this.mvect > 90) {
                                this.mvect = 90;
                            }
                            this.lmxz = medium.xz;
                        }
                        xtGraphics2.stat(madArray[xtGraphics2.im], contOArray2[xtGraphics2.im], checkPoints, this.u[0], true);
                    }
                    if (this.mouses == 1) {
                        if (xtGraphics2.holdit && xtGraphics2.exitm != 4 && xtGraphics2.multion == 1) {
                            this.u[0].enter = true;
                        }
                        this.mouses = 0;
                    }
                } else {
                    medium.around(contOArray2[xtGraphics2.im], true);
                    this.mvect = 80;
                    if (xtGraphics2.starcnt == 39) {
                        xtGraphics2.waitenter();
                    }
                    if (xtGraphics2.starcnt == 38) {
                        xtGraphics2.forstart = 0;
                        this.mouses = 0;
                        medium.vert = false;
                        medium.adv = 900;
                        medium.vxz = 180;
                        checkPoints.checkstat(madArray, contOArray2, record, xtGraphics2.nplayers, xtGraphics2.im, xtGraphics2.multion);
                        medium.follow(contOArray2[xtGraphics2.im], madArray[xtGraphics2.im].cxz, 0);
                        xtGraphics2.stat(madArray[xtGraphics2.im], contOArray2[xtGraphics2.im], checkPoints, this.u[0], true);
                        this.rd.setColor(new Color(255, 255, 255));
                        this.rd.fillRect(0, 0, 800, 450);
                    }
                }
                xtGraphics2.multistat(this.u[0], checkPoints, this.xm, this.ym, this.moused, uDPMistro);
            }
            if (xtGraphics2.fase == -1) {
                int n33;
                if (n4 == 0) {
                    for (n11 = 0; n11 < xtGraphics2.nplayers; ++n11) {
                        record.ocar[n11] = new ContO(contOArray2[n11], 0, 0, 0, 0);
                        contOArray2[n11] = new ContO(record.car[0][n11], 0, 0, 0, 0);
                    }
                }
                medium.d(this.rd);
                n11 = 0;
                nArray = new int[200];
                for (int i = 0; i < this.nob; ++i) {
                    if (contOArray2[i].dist != 0) {
                        nArray[n11] = i;
                        ++n11;
                        continue;
                    }
                    contOArray2[i].d(this.rd);
                }
                int[] nArray9 = new int[n11];
                for (n33 = 0; n33 < n11; ++n33) {
                    nArray9[n33] = 0;
                }
                for (n33 = 0; n33 < n11; ++n33) {
                    for (n9 = n33 + 1; n9 < n11; ++n9) {
                        if (contOArray2[nArray[n33]].dist != contOArray2[nArray[n9]].dist) {
                            if (contOArray2[nArray[n33]].dist < contOArray2[nArray[n9]].dist) {
                                int n34 = n33;
                                nArray9[n34] = nArray9[n34] + 1;
                                continue;
                            }
                            int n35 = n9;
                            nArray9[n35] = nArray9[n35] + 1;
                            continue;
                        }
                        if (n9 > n33) {
                            int n36 = n33;
                            nArray9[n36] = nArray9[n36] + 1;
                            continue;
                        }
                        int n37 = n9;
                        nArray9[n37] = nArray9[n37] + 1;
                    }
                }
                for (n33 = 0; n33 < n11; ++n33) {
                    for (n9 = 0; n9 < n11; ++n9) {
                        if (nArray9[n9] != n33) continue;
                        contOArray2[nArray[n9]].d(this.rd);
                    }
                }
                if (this.u[0].enter || this.u[0].handb || this.mouses == 1) {
                    n4 = 299;
                    this.u[0].enter = false;
                    this.u[0].handb = false;
                    this.mouses = 0;
                }
                for (n33 = 0; n33 < xtGraphics2.nplayers; ++n33) {
                    if (record.fix[n33] == n4) {
                        if (contOArray2[n33].dist == 0) {
                            contOArray2[n33].fcnt = 8;
                        } else {
                            contOArray2[n33].fix = true;
                        }
                    }
                    if (contOArray2[n33].fcnt == 7 || contOArray2[n33].fcnt == 8) {
                        contOArray2[n33] = new ContO(contOArray[madArray[n33].cn], 0, 0, 0, 0);
                        record.cntdest[n33] = 0;
                    }
                    if (n4 == 299) {
                        contOArray2[n33] = new ContO(record.ocar[n33], 0, 0, 0, 0);
                    }
                    record.play(contOArray2[n33], madArray[n33], n33, n4);
                }
                if (++n4 == 300) {
                    n4 = 0;
                    xtGraphics2.fase = -6;
                } else {
                    xtGraphics2.replyn();
                }
                medium.around(contOArray2[0], false);
            }
            if (xtGraphics2.fase == -2) {
                if (xtGraphics2.multion >= 2) {
                    record.hcaught = false;
                }
                this.u[0].falseo(3);
                if (record.hcaught && record.wasted == 0 && record.whenwasted != 229 && (checkPoints.stage == 1 || checkPoints.stage == 2) && xtGraphics2.looped != 0) {
                    record.hcaught = false;
                }
                if (record.hcaught) {
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.fillRect(0, 0, 800, 450);
                    this.repaint();
                }
                if (xtGraphics2.multion != 0) {
                    uDPMistro.UDPquit();
                    xtGraphics2.stopchat();
                    if (this.cmsg.isShowing()) {
                        this.cmsg.hide();
                    }
                    this.cmsg.setText("");
                    this.requestFocus();
                }
                if (record.hcaught) {
                    medium.vert = !((double)medium.random() > 0.45);
                    medium.adv = (int)(900.0f * medium.random());
                    medium.vxz = (int)(360.0f * medium.random());
                    n4 = 0;
                    xtGraphics2.fase = -3;
                    n5 = 0;
                    n6 = 0;
                } else {
                    n4 = -2;
                    xtGraphics2.fase = -4;
                }
            }
            if (xtGraphics2.fase == -3) {
                int n38;
                if (n4 == 0) {
                    if (record.wasted == 0) {
                        if (record.whenwasted == 229) {
                            n7 = 67;
                            medium.vxz += 90;
                        } else {
                            n7 = (int)(medium.random() * 4.0f);
                            if (n7 == 1 || n7 == 3) {
                                n7 = 69;
                            }
                            if (n7 == 2 || n7 == 4) {
                                n7 = 30;
                            }
                        }
                    } else if (record.closefinish != 0 && n6 != 0) {
                        medium.vxz += 90;
                    }
                    for (n11 = 0; n11 < xtGraphics2.nplayers; ++n11) {
                        contOArray2[n11] = new ContO(record.starcar[n11], 0, 0, 0, 0);
                    }
                }
                medium.d(this.rd);
                n11 = 0;
                nArray = new int[200];
                for (int i = 0; i < this.nob; ++i) {
                    if (contOArray2[i].dist != 0) {
                        nArray[n11] = i;
                        ++n11;
                        continue;
                    }
                    contOArray2[i].d(this.rd);
                }
                int[] nArray10 = new int[n11];
                for (n38 = 0; n38 < n11; ++n38) {
                    nArray10[n38] = 0;
                }
                for (n38 = 0; n38 < n11; ++n38) {
                    for (n9 = n38 + 1; n9 < n11; ++n9) {
                        if (contOArray2[nArray[n38]].dist != contOArray2[nArray[n9]].dist) {
                            if (contOArray2[nArray[n38]].dist < contOArray2[nArray[n9]].dist) {
                                int n39 = n38;
                                nArray10[n39] = nArray10[n39] + 1;
                                continue;
                            }
                            int n40 = n9;
                            nArray10[n40] = nArray10[n40] + 1;
                            continue;
                        }
                        if (n9 > n38) {
                            int n41 = n38;
                            nArray10[n41] = nArray10[n41] + 1;
                            continue;
                        }
                        int n42 = n9;
                        nArray10[n42] = nArray10[n42] + 1;
                    }
                }
                for (n38 = 0; n38 < n11; ++n38) {
                    for (n9 = 0; n9 < n11; ++n9) {
                        if (nArray10[n9] != n38) continue;
                        contOArray2[nArray[n9]].d(this.rd);
                    }
                }
                for (n38 = 0; n38 < xtGraphics2.nplayers; ++n38) {
                    if (record.hfix[n38] == n4) {
                        if (contOArray2[n38].dist == 0) {
                            contOArray2[n38].fcnt = 8;
                        } else {
                            contOArray2[n38].fix = true;
                        }
                    }
                    if (contOArray2[n38].fcnt == 7 || contOArray2[n38].fcnt == 8) {
                        contOArray2[n38] = new ContO(contOArray[madArray[n38].cn], 0, 0, 0, 0);
                        record.cntdest[n38] = 0;
                    }
                    record.playh(contOArray2[n38], madArray[n38], n38, n4, xtGraphics2.im);
                }
                if (n6 == 2 && n4 == 299) {
                    this.u[0].enter = true;
                }
                if (this.u[0].enter || this.u[0].handb) {
                    xtGraphics2.fase = -4;
                    this.u[0].enter = false;
                    this.u[0].handb = false;
                    n4 = -7;
                } else {
                    xtGraphics2.levelhigh(record.wasted, record.whenwasted, record.closefinish, n4, checkPoints.stage);
                    if (n4 == 0 || n4 == 1 || n4 == 2) {
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.fillRect(0, 0, 800, 450);
                    }
                    if (record.wasted != xtGraphics2.im) {
                        if (record.closefinish == 0) {
                            if (n5 == 9 || n5 == 11) {
                                this.rd.setColor(new Color(255, 255, 255));
                                this.rd.fillRect(0, 0, 800, 450);
                            }
                            if (n5 == 0) {
                                medium.around(contOArray2[xtGraphics2.im], false);
                            }
                            if (n5 > 0 && n5 < 20) {
                                medium.transaround(contOArray2[xtGraphics2.im], contOArray2[record.wasted], n5);
                            }
                            if (n5 == 20) {
                                medium.around(contOArray2[record.wasted], false);
                            }
                            if (n4 > record.whenwasted && n5 != 20) {
                                ++n5;
                            }
                            if ((n5 == 0 || n5 == 20) && ++n4 == 300) {
                                n4 = 0;
                                n5 = 0;
                                ++n6;
                            }
                        } else if (record.closefinish == 1) {
                            if (n5 == 0) {
                                medium.around(contOArray2[xtGraphics2.im], false);
                            }
                            if (n5 > 0 && n5 < 20) {
                                medium.transaround(contOArray2[xtGraphics2.im], contOArray2[record.wasted], n5);
                            }
                            if (n5 == 20) {
                                medium.around(contOArray2[record.wasted], false);
                            }
                            if (n5 > 20 && n5 < 40) {
                                medium.transaround(contOArray2[record.wasted], contOArray2[xtGraphics2.im], n5 - 20);
                            }
                            if (n5 == 40) {
                                medium.around(contOArray2[xtGraphics2.im], false);
                            }
                            if (n5 > 40 && n5 < 60) {
                                medium.transaround(contOArray2[xtGraphics2.im], contOArray2[record.wasted], n5 - 40);
                            }
                            if (n5 == 60) {
                                medium.around(contOArray2[record.wasted], false);
                            }
                            if (n4 > 160 && n5 < 20) {
                                ++n5;
                            }
                            if (n4 > 230 && n5 < 40) {
                                ++n5;
                            }
                            if (n4 > 280 && n5 < 60) {
                                ++n5;
                            }
                            if ((n5 == 0 || n5 == 20 || n5 == 40 || n5 == 60) && ++n4 == 300) {
                                n4 = 0;
                                n5 = 0;
                                ++n6;
                            }
                        } else {
                            if (n5 == 0) {
                                medium.around(contOArray2[xtGraphics2.im], false);
                            }
                            if (n5 > 0 && n5 < 20) {
                                medium.transaround(contOArray2[xtGraphics2.im], contOArray2[record.wasted], n5);
                            }
                            if (n5 == 20) {
                                medium.around(contOArray2[record.wasted], false);
                            }
                            if (n5 > 20 && n5 < 40) {
                                medium.transaround(contOArray2[record.wasted], contOArray2[xtGraphics2.im], n5 - 20);
                            }
                            if (n5 == 40) {
                                medium.around(contOArray2[xtGraphics2.im], false);
                            }
                            if (n5 > 40 && n5 < 60) {
                                medium.transaround(contOArray2[xtGraphics2.im], contOArray2[record.wasted], n5 - 40);
                            }
                            if (n5 == 60) {
                                medium.around(contOArray2[record.wasted], false);
                            }
                            if (n5 > 60 && n5 < 80) {
                                medium.transaround(contOArray2[record.wasted], contOArray2[xtGraphics2.im], n5 - 60);
                            }
                            if (n5 == 80) {
                                medium.around(contOArray2[xtGraphics2.im], false);
                            }
                            if (n4 > 90 && n5 < 20) {
                                ++n5;
                            }
                            if (n4 > 160 && n5 < 40) {
                                ++n5;
                            }
                            if (n4 > 230 && n5 < 60) {
                                ++n5;
                            }
                            if (n4 > 280 && n5 < 80) {
                                ++n5;
                            }
                            if ((n5 == 0 || n5 == 20 || n5 == 40 || n5 == 60 || n5 == 80) && ++n4 == 300) {
                                n4 = 0;
                                n5 = 0;
                                ++n6;
                            }
                        }
                    } else {
                        if (n7 == 67 && (n5 == 3 || n5 == 31 || n5 == 66)) {
                            this.rd.setColor(new Color(255, 255, 255));
                            this.rd.fillRect(0, 0, 800, 450);
                        }
                        if (n7 == 69 && (n5 == 3 || n5 == 5 || n5 == 31 || n5 == 33 || n5 == 66 || n5 == 68)) {
                            this.rd.setColor(new Color(255, 255, 255));
                            this.rd.fillRect(0, 0, 800, 450);
                        }
                        if (n7 == 30 && n5 >= 1 && n5 < 30) {
                            if (n5 % (int)(2.0f + medium.random() * 3.0f) == 0 && !bl3) {
                                this.rd.setColor(new Color(255, 255, 255));
                                this.rd.fillRect(0, 0, 800, 450);
                                bl3 = true;
                            } else {
                                bl3 = false;
                            }
                        }
                        if (n4 > record.whenwasted && n5 != n7) {
                            ++n5;
                        }
                        medium.around(contOArray2[xtGraphics2.im], false);
                        if ((n5 == 0 || n5 == n7) && ++n4 == 300) {
                            n4 = 0;
                            n5 = 0;
                            ++n6;
                        }
                    }
                }
            }
            if (xtGraphics2.fase == -4) {
                if (n4 == 0) {
                    xtGraphics2.sendwin(checkPoints);
                    if (xtGraphics2.winner && xtGraphics2.multion == 0 && xtGraphics2.gmode != 0 && checkPoints.stage != 27 && checkPoints.stage == xtGraphics2.unlocked[xtGraphics2.gmode - 1] + (xtGraphics2.gmode - 1) * 10) {
                        int n43 = xtGraphics2.gmode - 1;
                        xtGraphics2.unlocked[n43] = xtGraphics2.unlocked[n43] + 1;
                        this.setcarcookie(xtGraphics2.sc[0], carDefine.names[xtGraphics2.sc[0]], xtGraphics2.arnp, xtGraphics2.gmode, xtGraphics2.unlocked, false);
                        int n44 = xtGraphics2.gmode - 1;
                        xtGraphics2.unlocked[n44] = xtGraphics2.unlocked[n44] - 1;
                    }
                }
                if (n4 <= 0) {
                    this.rd.drawImage(xtGraphics2.mdness, 289, 30, null);
                    this.rd.drawImage(xtGraphics2.dude[0], 135, 10, null);
                }
                if (n4 >= 0) {
                    xtGraphics2.fleximage(this.offImage, n4, checkPoints.stage);
                }
                if (++n4 == 7) {
                    xtGraphics2.fase = -5;
                    this.rd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                }
            }
            if (xtGraphics2.fase == -6) {
                this.repaint();
                xtGraphics2.pauseimage(this.offImage);
                xtGraphics2.fase = -7;
                this.mouses = 0;
            }
            if (xtGraphics2.fase == -7) {
                xtGraphics2.pausedgame(checkPoints.stage, this.u[0], record);
                if (n4 != 0) {
                    n4 = 0;
                }
                xtGraphics2.ctachm(this.xm, this.ym, this.mouses, this.u[0]);
                if (this.mouses == 2) {
                    this.mouses = 0;
                }
                if (this.mouses == 1) {
                    this.mouses = 2;
                }
            }
            if (xtGraphics2.fase == -8) {
                xtGraphics2.cantreply();
                if (++n4 == 150 || this.u[0].enter || this.u[0].handb || this.mouses == 1) {
                    xtGraphics2.fase = -7;
                    this.mouses = 0;
                    this.u[0].enter = false;
                    this.u[0].handb = false;
                }
            }
            if (this.lostfcs && xtGraphics2.fase == 7001) {
                if (this.fcscnt == 0) {
                    if (this.u[0].chatup == 0) {
                        this.requestFocus();
                    }
                    this.fcscnt = 10;
                } else {
                    --this.fcscnt;
                }
            }
            this.repaint();
            if (xtGraphics2.im > -1 && xtGraphics2.im < 8) {
                n11 = 0;
                if (xtGraphics2.multion == 2 || xtGraphics2.multion == 3) {
                    n11 = xtGraphics2.im;
                    this.u[n11].mutem = this.u[0].mutem;
                    this.u[n11].mutes = this.u[0].mutes;
                }
                xtGraphics2.playsounds(madArray[xtGraphics2.im], this.u[n11], checkPoints.stage);
            }
            date = new Date();
            long l4 = date.getTime();
            if (xtGraphics2.fase == 0 || xtGraphics2.fase == -1 || xtGraphics2.fase == -3 || xtGraphics2.fase == 7001) {
                if (!bl2) {
                    n = 15;
                    f2 = f;
                    if (f2 < 30.0f) {
                        f2 = 30.0f;
                    }
                    bl2 = true;
                    n3 = 0;
                }
                if (n3 == 10) {
                    float f3 = ((float)n2 + uDPMistro.freg - (float)(l4 - l2)) / 20.0f;
                    if (f3 > 40.0f) {
                        f3 = 40.0f;
                    }
                    if (f3 < -40.0f) {
                        f3 = -40.0f;
                    }
                    if ((f2 += f3) < 5.0f) {
                        f2 = 5.0f;
                    }
                    medium.adjstfade(f2, f3, xtGraphics2.starcnt, this);
                    l2 = l4;
                    n3 = 0;
                } else {
                    ++n3;
                }
            } else {
                if (bl2) {
                    n = 30;
                    f = f2;
                    bl2 = false;
                    n3 = 0;
                }
                if (n3 == 10) {
                    if (l4 - l2 < 400L) {
                        f2 = (float)((double)f2 + 3.5);
                    } else if ((f2 = (float)((double)f2 - 3.5)) < 5.0f) {
                        f2 = 5.0f;
                    }
                    l2 = l4;
                    n3 = 0;
                } else {
                    ++n3;
                }
            }
            if (this.exwist) {
                this.rd.dispose();
                xtGraphics2.stopallnow();
                carDefine.stopallnow();
                uDPMistro.UDPquit();
                if (bl) {
                    lobby.stopallnow();
                    login.stopallnow();
                    globe.stopallnow();
                }
                System.gc();
                if (Madness.endadv == 2) {
                    Madness.advopen();
                }
                this.gamer.stop();
                this.gamer = null;
            }
            if ((l = (long)Math.round(f2) - (l4 - l3)) < (long)n) {
                l = n;
            }
            try {
                Thread.sleep(l);
            }
            catch (InterruptedException interruptedException) {
            }
        }
    }

    public void checkmemory(xtGraphics xtGraphics2) {
        if (this.applejava || Runtime.getRuntime().freeMemory() / 0x100000L < 50L) {
            xtGraphics2.badmac = true;
        }
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D)graphics;
        if (this.lastw != this.getWidth() || this.lasth != this.getHeight()) {
            this.lastw = this.getWidth();
            this.lasth = this.getHeight();
            this.showsize = 100;
            if (this.lastw <= 800 || this.lasth <= 550) {
                this.reqmult = 0.0f;
            }
            if (Madness.fullscreen) {
                this.apx = (int)((float)(this.getWidth() / 2) - 400.0f * this.apmult);
                this.apy = (int)((float)(this.getHeight() / 2) - 225.0f * this.apmult);
            }
        }
        int n = 0;
        int n2 = 0;
        if (this.moto == 1 && this.shaka > 0) {
            n = (int)(((double)(this.shaka * 2) * Math.random() - (double)this.shaka) * (double)this.apmult);
            n2 = (int)(((double)(this.shaka * 2) * Math.random() - (double)this.shaka) * (double)this.apmult);
            --this.shaka;
        }
        if (!Madness.fullscreen) {
            if (this.showsize != 0) {
                float f;
                if (this.showsize == 100 || this.showsize == 70) {
                    graphics2D.clearRect(0, 0, this.getWidth(), this.getHeight());
                }
                if ((f = (float)(this.getWidth() - 40) / 800.0f - 1.0f) > (float)(this.getHeight() - 70) / 450.0f - 1.0f) {
                    f = (float)(this.getHeight() - 70) / 450.0f - 1.0f;
                }
                if (f > 1.0f) {
                    f = 1.0f;
                }
                if (f < 0.0f) {
                    f = 0.0f;
                }
                this.apmult = 1.0f + f * this.reqmult;
                if (!this.oncarm) {
                    graphics2D.drawImage(this.carmaker[0], 50, 14, this);
                } else {
                    graphics2D.drawImage(this.carmaker[1], 50, 14, this);
                }
                if (!this.onstgm) {
                    graphics2D.drawImage(this.stagemaker[0], this.getWidth() - 208, 14, this);
                } else {
                    graphics2D.drawImage(this.stagemaker[1], this.getWidth() - 208, 14, this);
                }
                graphics2D.drawImage(this.sizebar, this.getWidth() / 2 - 230, 23, this);
                graphics2D.drawImage(this.blb, (int)((float)(this.getWidth() / 2 - 222) + 141.0f * this.reqmult), 23, this);
                graphics2D.drawImage(this.chkbx[this.smooth], this.getWidth() / 2 - 53, 23, this);
                graphics2D.setFont(new Font("Arial", 1, 11));
                graphics2D.setColor(new Color(74, 99, 125));
                graphics2D.drawString("Screen Size:", this.getWidth() / 2 - 224, 17);
                graphics2D.drawString("Smooth", this.getWidth() / 2 - 36, 34);
                graphics2D.drawImage(this.fulls, this.getWidth() / 2 + 27, 15, this);
                graphics2D.setColor(new Color(94, 126, 159));
                graphics2D.drawString("Fullscreen", this.getWidth() / 2 + 63, 30);
                graphics2D.drawImage(this.chkbx[Madness.anti], this.getWidth() / 2 + 135, 9, this);
                graphics2D.drawString("Antialiasing", this.getWidth() / 2 + 152, 20);
                graphics2D.drawImage(this.chkbx[this.moto], this.getWidth() / 2 + 135, 26, this);
                graphics2D.drawString("Motion Effects", this.getWidth() / 2 + 152, 37);
                graphics2D.setColor(new Color(0, 0, 0));
                graphics2D.fillRect(this.getWidth() / 2 - 153, 5, 80, 16);
                graphics2D.setColor(new Color(121, 135, 152));
                String string = "" + (int)(this.apmult * 100.0f) + "%";
                if (this.reqmult == 0.0f) {
                    string = "Original";
                }
                if (this.reqmult == 1.0f) {
                    string = "Maximum";
                }
                graphics2D.drawString(string, this.getWidth() / 2 - 150, 17);
                if (!this.oncarm && !this.onstgm) {
                    --this.showsize;
                }
                if (this.showsize == 0) {
                    graphics2D.setColor(new Color(0, 0, 0));
                    graphics2D.fillRect(this.getWidth() / 2 - 260, 0, 520, 40);
                    graphics2D.fillRect(50, 14, 142, 23);
                    graphics2D.fillRect(this.getWidth() - 208, 14, 158, 23);
                }
            }
            this.apx = (int)((float)(this.getWidth() / 2) - 400.0f * this.apmult);
            this.apy = (int)((float)(this.getHeight() / 2) - 225.0f * this.apmult - 50.0f);
            if (this.apy < 50) {
                this.apy = 50;
            }
            if (this.apmult > 1.0f) {
                if (this.smooth == 1) {
                    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    if (this.moto == 1) {
                        graphics2D.setComposite(AlphaComposite.getInstance(3, (float)this.mvect / 100.0f));
                        this.rd.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
                        graphics2D.drawImage(this.offImage, this.apx + n, this.apy + n2, (int)(800.0f * this.apmult), (int)(450.0f * this.apmult), this);
                        this.cropit(graphics2D, n, n2);
                    } else {
                        graphics2D.drawImage(this.offImage, this.apx, this.apy, (int)(800.0f * this.apmult), (int)(450.0f * this.apmult), this);
                    }
                } else if (this.moto == 1) {
                    graphics2D.setComposite(AlphaComposite.getInstance(3, (float)this.mvect / 100.0f));
                    this.rd.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
                    graphics2D.drawImage(this.offImage, this.apx + n, this.apy + n2, (int)(800.0f * this.apmult), (int)(450.0f * this.apmult), this);
                    this.cropit(graphics2D, n, n2);
                } else {
                    graphics2D.drawImage(this.offImage, this.apx, this.apy, (int)(800.0f * this.apmult), (int)(450.0f * this.apmult), this);
                }
            } else if (this.moto == 1) {
                graphics2D.setComposite(AlphaComposite.getInstance(3, (float)this.mvect / 100.0f));
                this.rd.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
                graphics2D.drawImage(this.offImage, this.apx + n, this.apy + n2, this);
                this.cropit(graphics2D, n, n2);
            } else {
                graphics2D.drawImage(this.offImage, this.apx, this.apy, this);
            }
        } else if (this.moto == 1) {
            graphics2D.setComposite(AlphaComposite.getInstance(3, (float)this.mvect / 100.0f));
            this.rd.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
            graphics2D.drawImage(this.offImage, this.apx + n, this.apy + n2, this);
            this.cropit(graphics2D, n, n2);
        } else {
            graphics2D.drawImage(this.offImage, this.apx, this.apy, this);
        }
    }

    public void cropit(Graphics2D graphics2D, int n, int n2) {
        if (n != 0 || n2 != 0) {
            graphics2D.setComposite(AlphaComposite.getInstance(3, 1.0f));
            graphics2D.setColor(new Color(0, 0, 0));
        }
        if (n != 0) {
            if (n < 0) {
                graphics2D.fillRect(this.apx + n, this.apy - (int)(25.0f * this.apmult), Math.abs(n), (int)(500.0f * this.apmult));
            } else {
                graphics2D.fillRect(this.apx + (int)(800.0f * this.apmult), this.apy - (int)(25.0f * this.apmult), n, (int)(500.0f * this.apmult));
            }
        }
        if (n2 != 0) {
            if (n2 < 0) {
                graphics2D.fillRect(this.apx - (int)(25.0f * this.apmult), this.apy + n2, (int)(850.0f * this.apmult), Math.abs(n2));
            } else {
                graphics2D.fillRect(this.apx - (int)(25.0f * this.apmult), this.apy + (int)(450.0f * this.apmult), (int)(850.0f * this.apmult), n2);
            }
        }
    }

    @Override
    public void update(Graphics graphics) {
        this.paint(graphics);
    }

    @Override
    public void init() {
        int n;
        this.setBackground(new Color(0, 0, 0));
        this.offImage = this.createImage(800, 450);
        if (this.offImage != null) {
            this.rd = (Graphics2D)this.offImage.getGraphics();
        }
        this.rd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.setLayout(null);
        this.tnick = new TextField("Nickbname");
        this.tnick.setFont(new Font("Arial", 1, 13));
        this.tpass = new TextField("");
        this.tpass.setFont(new Font("Arial", 1, 13));
        this.tpass.setEchoCharacter('*');
        this.temail = new TextField("");
        this.temail.setFont(new Font("Arial", 1, 13));
        this.cmsg = new TextField("");
        if (System.getProperty("java.vendor").toLowerCase().indexOf("oracle") != -1) {
            this.cmsg.addKeyListener(new KeyAdapter(){

                @Override
                public void keyReleased(KeyEvent keyEvent) {
                }

                @Override
                public void keyTyped(KeyEvent keyEvent) {
                }

                @Override
                public void keyPressed(KeyEvent keyEvent) {
                    if (keyEvent.getKeyCode() == 10 && GameSparker.this.u[0] != null) {
                        GameSparker.this.u[0].enter = true;
                    }
                }
            });
        }
        this.mmsg = new TextArea("", 200, 20, 3);
        this.cmsg.setFont(new Font("Tahoma", 0, 11));
        this.mmsg.setFont(new Font("Tahoma", 0, 11));
        this.mycar = new Checkbox("Sword of Justice Game!");
        this.notp = new Checkbox("No Trees & Piles");
        this.keplo = new Checkbox("Stay logged in");
        this.keplo.setState(true);
        this.add(this.tnick);
        this.add(this.tpass);
        this.add(this.temail);
        this.add(this.cmsg);
        this.add(this.mmsg);
        this.add(this.mycar);
        this.add(this.notp);
        this.add(this.keplo);
        this.sgame.setFont(new Font("Arial", 1, 13));
        this.wgame.setFont(new Font("Arial", 1, 13));
        this.warb.setFont(new Font("Arial", 1, 13));
        this.pgame.setFont(new Font("Arial", 1, 12));
        this.vnpls.setFont(new Font("Arial", 1, 13));
        this.vtyp.setFont(new Font("Arial", 1, 13));
        this.snfmm.setFont(new Font("Arial", 1, 13));
        this.snfm1.setFont(new Font("Arial", 1, 13));
        this.snfm2.setFont(new Font("Arial", 1, 13));
        this.mstgs.setFont(new Font("Arial", 1, 13));
        this.mcars.setFont(new Font("Arial", 1, 13));
        this.slaps.setFont(new Font("Arial", 1, 13));
        this.snpls.setFont(new Font("Arial", 0, 13));
        this.snbts.setFont(new Font("Arial", 0, 13));
        this.swait.setFont(new Font("Arial", 0, 12));
        this.sclass.setFont(new Font("Arial", 1, 12));
        this.scars.setFont(new Font("Arial", 1, 12));
        this.sfix.setFont(new Font("Arial", 1, 12));
        this.mycar.setFont(new Font("Arial", 1, 12));
        this.notp.setFont(new Font("Arial", 1, 12));
        this.keplo.setFont(new Font("Arial", 1, 12));
        this.gmode.setFont(new Font("Arial", 1, 13));
        this.rooms.setFont(new Font("Arial", 1, 13));
        this.sendtyp.setFont(new Font("Arial", 1, 12));
        this.senditem.setFont(new Font("Arial", 1, 12));
        this.datat.setFont(new Font("Arial", 1, 12));
        this.clanlev.setFont(new Font("Arial", 1, 12));
        this.clcars.setFont(new Font("Arial", 1, 12));
        this.clcars.alphad = true;
        this.ilaps.setFont(new Font("Arial", 1, 13));
        this.icars.setFont(new Font("Arial", 1, 12));
        this.proitem.setFont(new Font("Arial", 1, 12));
        this.sgame.add(this.rd, " NFM 1     ");
        this.sgame.add(this.rd, " NFM 2     ");
        this.sgame.add(this.rd, " My Stages ");
        this.sgame.add(this.rd, " Weekly Top20 ");
        this.sgame.add(this.rd, " Monthly Top20 ");
        this.sgame.add(this.rd, " All Time Top20 ");
        this.sgame.add(this.rd, " Stage Maker ");
        this.wgame.add(this.rd, " Normal Game");
        this.wgame.add(this.rd, " War / Battle");
        this.wgame.add(this.rd, " War / Battle - Practice");
        this.warb.add(this.rd, " Loading your clan's wars and battles, please wait...");
        this.pgame.add(this.rd, " Select the game you want to practice");
        this.vnpls.add(this.rd, "Players");
        this.vnpls.add(this.rd, " 2 VS 2");
        this.vnpls.add(this.rd, " 3 VS 3");
        this.vnpls.add(this.rd, " 4 VS 4");
        this.vtyp.add(this.rd, "Normal clan game");
        this.vtyp.add(this.rd, "Racing only");
        this.vtyp.add(this.rd, "Wasting only");
        this.vtyp.add(this.rd, "Racers VS Wasters - my clan wastes");
        this.vtyp.add(this.rd, "Racers VS Wasters - my clan races");
        this.snfmm.add(this.rd, "Select Stage");
        this.snfm1.add(this.rd, "Select Stage");
        this.snfm2.add(this.rd, "Select Stage");
        this.mstgs.add(this.rd, "Suddenly the King becomes Santa's Little Helper");
        this.mcars.add(this.rd, "Sword of Justice");
        this.snpls.add(this.rd, "Select");
        this.swait.add(this.rd, "1 Minute");
        this.ilaps.add(this.rd, "Laps");
        this.ilaps.add(this.rd, "1 Lap");
        for (n = 0; n < 5; ++n) {
            this.snfmm.add(this.rd, " Stage " + (n + 1) + "");
        }
        for (n = 0; n < 10; ++n) {
            this.snfm1.add(this.rd, " Stage " + (n + 1) + "");
        }
        for (n = 0; n < 17; ++n) {
            this.snfm2.add(this.rd, " Stage " + (n + 1) + "");
        }
        for (n = 0; n < 7; ++n) {
            this.snpls.add(this.rd, "    " + (n + 2) + "");
        }
        for (n = 0; n < 7; ++n) {
            this.snbts.add(this.rd, "    " + n + "    ");
        }
        for (n = 0; n < 2; ++n) {
            this.swait.add(this.rd, "" + (n + 2) + " Minutes");
        }
        for (n = 0; n < 15; ++n) {
            this.slaps.add(this.rd, "" + (n + 1) + "");
        }
        for (n = 0; n < 14; ++n) {
            this.ilaps.add(this.rd, "" + (n + 2) + " Laps");
        }
        this.sclass.add(this.rd, "All Classes");
        this.sclass.add(this.rd, "Class C Cars");
        this.sclass.add(this.rd, "Class B & C Cars");
        this.sclass.add(this.rd, "Class B Cars");
        this.sclass.add(this.rd, "Class A & B Cars");
        this.sclass.add(this.rd, "Class A Cars");
        this.scars.add(this.rd, "All Cars");
        this.scars.add(this.rd, "Custom Cars");
        this.scars.add(this.rd, "Game Cars");
        this.sfix.add(this.rd, "Unlimited Fixing");
        this.sfix.add(this.rd, "4 Fixes");
        this.sfix.add(this.rd, "3 Fixes");
        this.sfix.add(this.rd, "2 Fixes");
        this.sfix.add(this.rd, "1 Fix");
        this.sfix.add(this.rd, "No Fixing");
        this.icars.add(this.rd, "Type of Cars");
        this.icars.add(this.rd, "All Cars");
        this.icars.add(this.rd, "Clan Cars");
        this.icars.add(this.rd, "Game Cars");
        this.icars.w = 140;
        this.gmode.add(this.rd, " Normal Game ");
        this.gmode.add(this.rd, " Practice Game ");
        this.rooms.rooms = true;
        this.rooms.add(this.rd, "Ghostrider :: 1");
        this.sendtyp.add(this.rd, "Write a Message");
        this.sendtyp.add(this.rd, "Share a Custom Car");
        this.sendtyp.add(this.rd, "Share a Custom Stage");
        this.sendtyp.add(this.rd, "Send a Clan Invitation");
        this.sendtyp.add(this.rd, "Share a Relative Date");
        this.senditem.add(this.rd, "Suddenly the King becomes Santa's Little Helper");
        for (n = 0; n < 6; ++n) {
            this.clanlev.add(this.rd, "" + (n + 1) + "");
        }
        this.clanlev.add(this.rd, "7 - Admin");
        this.hidefields();
    }

    public void hidefields() {
        this.ilaps.hide();
        this.icars.hide();
        this.proitem.hide();
        this.clcars.hide();
        this.clanlev.hide();
        this.mmsg.hide();
        this.datat.hide();
        this.senditem.hide();
        this.sendtyp.hide();
        this.rooms.hide();
        this.mcars.hide();
        this.mstgs.hide();
        this.gmode.hide();
        this.sclass.hide();
        this.scars.hide();
        this.sfix.hide();
        this.mycar.hide();
        this.notp.hide();
        this.keplo.hide();
        this.tnick.hide();
        this.tpass.hide();
        this.temail.hide();
        this.cmsg.hide();
        this.sgame.hide();
        this.wgame.hide();
        this.pgame.hide();
        this.vnpls.hide();
        this.vtyp.hide();
        this.warb.hide();
        this.slaps.hide();
        this.snfm1.hide();
        this.snfmm.hide();
        this.snfm2.hide();
        this.snpls.hide();
        this.snbts.hide();
        this.swait.hide();
    }

    public void drawms() {
        this.openm = false;
        if (this.gmode.draw(this.rd, this.xm, this.ym, this.moused, 450, true)) {
            this.openm = true;
        }
        if (this.swait.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.slaps.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.snpls.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.snbts.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.scars.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.sgame.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.snfm1.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.snfm2.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.snfmm.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.mstgs.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.mcars.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.pgame.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.vnpls.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.vtyp.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.warb.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.wgame.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.rooms.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.sendtyp.draw(this.rd, this.xm, this.ym, this.moused, 450, true)) {
            this.openm = true;
        }
        if (this.senditem.draw(this.rd, this.xm, this.ym, this.moused, 450, true)) {
            this.openm = true;
        }
        if (this.datat.draw(this.rd, this.xm, this.ym, this.moused, 450, true)) {
            this.openm = true;
        }
        if (this.clanlev.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.clcars.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.ilaps.draw(this.rd, this.xm, this.ym, this.moused, 450, true)) {
            this.openm = true;
        }
        if (this.icars.draw(this.rd, this.xm, this.ym, this.moused, 450, true)) {
            this.openm = true;
        }
        if (this.proitem.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.sfix.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
        if (this.sclass.draw(this.rd, this.xm, this.ym, this.moused, 450, false)) {
            this.openm = true;
        }
    }

    public void movefield(Component component, int n, int n2, int n3, int n4) {
        if (n3 == 360 || n3 == 576) {
            n = (int)((float)n * this.apmult + (float)this.apx + (float)(component.getWidth() / 2) * (this.apmult - 1.0f));
            n2 = (int)((float)n2 * this.apmult + (float)this.apy + 12.0f * (this.apmult - 1.0f));
        } else {
            n = (int)((float)n * this.apmult + (float)this.apx);
            n2 = (int)((float)n2 * this.apmult + (float)this.apy + 12.0f * (this.apmult - 1.0f));
        }
        if (component.getX() != n || component.getY() != n2) {
            component.setBounds(n, n2, n3, n4);
        }
    }

    public void movefieldd(TextField textField, int n, int n2, int n3, int n4, boolean bl) {
        if (this.applejava) {
            if (bl) {
                if (this.xm > n && this.xm < n + n3 && this.ym > n2 && this.ym < n2 + n4 || !textField.getText().equals("")) {
                    if (!textField.isShowing()) {
                        textField.show();
                        textField.requestFocus();
                    }
                    if (n3 == 360 || n3 == 576) {
                        n = (int)((float)n * this.apmult + (float)this.apx + (float)(textField.getWidth() / 2) * (this.apmult - 1.0f));
                        n2 = (int)((float)n2 * this.apmult + (float)this.apy + 12.0f * (this.apmult - 1.0f));
                    } else {
                        n = (int)((float)n * this.apmult + (float)this.apx);
                        n2 = (int)((float)n2 * this.apmult + (float)this.apy + 12.0f * (this.apmult - 1.0f));
                    }
                    if (textField.getX() != n || textField.getY() != n2) {
                        textField.setBounds(n, n2, n3, n4);
                    }
                } else {
                    if (textField.isShowing()) {
                        textField.hide();
                        this.requestFocus();
                    }
                    this.rd.setColor(textField.getBackground());
                    this.rd.fillRect(n, n2, n3 - 1, n4 - 1);
                    this.rd.setColor(textField.getBackground().darker());
                    this.rd.drawRect(n, n2, n3 - 1, n4 - 1);
                }
            }
        } else {
            if (bl && !textField.isShowing()) {
                textField.show();
            }
            this.movefield(textField, n, n2, n3, n4);
        }
    }

    public void movefielda(TextArea textArea, int n, int n2, int n3, int n4) {
        if (this.applejava) {
            if (this.xm > n && this.xm < n + n3 && this.ym > n2 && this.ym < n2 + n4 || !textArea.getText().equals(" ")) {
                if (!textArea.isShowing()) {
                    textArea.show();
                    textArea.requestFocus();
                }
                if (n3 == 360 || n3 == 576) {
                    n = (int)((float)n * this.apmult + (float)this.apx + (float)(textArea.getWidth() / 2) * (this.apmult - 1.0f));
                    n2 = (int)((float)n2 * this.apmult + (float)this.apy + 12.0f * (this.apmult - 1.0f));
                } else {
                    n = (int)((float)n * this.apmult + (float)this.apx);
                    n2 = (int)((float)n2 * this.apmult + (float)this.apy + 12.0f * (this.apmult - 1.0f));
                }
                if (textArea.getX() != n || textArea.getY() != n2) {
                    textArea.setBounds(n, n2, n3, n4);
                }
            } else {
                if (textArea.isShowing()) {
                    textArea.hide();
                    this.requestFocus();
                }
                this.rd.setColor(textArea.getBackground());
                this.rd.fillRect(n, n2, n3 - 1, n4 - 1);
                this.rd.setColor(textArea.getBackground().darker());
                this.rd.drawRect(n, n2, n3 - 1, n4 - 1);
            }
        } else {
            if (!textArea.isShowing()) {
                textArea.show();
            }
            this.movefield(textArea, n, n2, n3, n4);
        }
    }

    public void loadstage(ContO[] contOArray, ContO[] contOArray2, Medium medium, Trackers trackers, CheckPoints checkPoints, xtGraphics xtGraphics2, Mad[] madArray, Record record) {
        if (xtGraphics2.testdrive == 2 || xtGraphics2.testdrive == 4) {
            xtGraphics2.nplayers = 1;
        }
        if (xtGraphics2.gmode == 1) {
            xtGraphics2.nplayers = 5;
            xtGraphics2.xstart[4] = 0;
            xtGraphics2.zstart[4] = 760;
        }
        trackers.nt = 0;
        this.nob = xtGraphics2.nplayers;
        this.notb = 0;
        checkPoints.n = 0;
        checkPoints.nsp = 0;
        checkPoints.fn = 0;
        checkPoints.trackname = "";
        checkPoints.haltall = false;
        checkPoints.wasted = 0;
        checkPoints.catchfin = 0;
        medium.resdown = 0;
        medium.rescnt = 5;
        medium.lightson = false;
        medium.noelec = 0;
        medium.ground = 250;
        medium.trk = 0;
        this.view = 0;
        int n = 0;
        int n2 = 100;
        int n3 = 0;
        int n4 = 100;
        xtGraphics2.newparts = false;
        String string = "";
        try {
            String string2;
            int n5;
            DataInputStream dataInputStream = null;
            if (xtGraphics2.multion == 0 && checkPoints.stage != -2) {
                String string3 = "stages/" + checkPoints.stage + "";
                if (checkPoints.stage == -1) {
                    string3 = "mystages/" + checkPoints.name + "";
                }
                File file = new File("" + Madness.fpath + "" + string3 + ".txt");
                dataInputStream = new DataInputStream(new FileInputStream(file));
            } else if (checkPoints.stage > 0) {
                URL uRL = new URL("http://multiplayer.needformadness.com/stages/" + checkPoints.stage + ".txt");
                dataInputStream = new DataInputStream(uRL.openStream());
            } else {
                int n6;
                int n7;
                Object object;
                ZipInputStream zipInputStream;
                String string4 = "http://multiplayer.needformadness.com/tracks/" + checkPoints.name + ".radq";
                string4 = string4.replace(' ', '_');
                URL uRL = new URL(string4);
                n5 = uRL.openConnection().getContentLength();
                DataInputStream dataInputStream2 = new DataInputStream(uRL.openStream());
                byte[] byArray = new byte[n5];
                dataInputStream2.readFully(byArray);
                if (byArray[0] == 80 && byArray[1] == 75 && byArray[2] == 3) {
                    zipInputStream = new ZipInputStream(new ByteArrayInputStream(byArray));
                } else {
                    object = new byte[n5 - 40];
                    for (n7 = 0; n7 < n5 - 40; ++n7) {
                        int n8 = 20;
                        if (n7 >= 500) {
                            n8 = 40;
                        }
                        object[n7] = byArray[n7 + n8];
                    }
                    zipInputStream = new ZipInputStream(new ByteArrayInputStream((byte[])object));
                }
                object = zipInputStream.getNextEntry();
                byte[] byArray2 = new byte[n7];
                int n9 = 0;
                for (n7 = Integer.valueOf(((ZipEntry)object).getName()).intValue(); n7 > 0; n7 -= n6) {
                    n6 = zipInputStream.read(byArray2, n9, n7);
                    n9 += n6;
                }
                zipInputStream.close();
                dataInputStream2.close();
                dataInputStream = new DataInputStream(new ByteArrayInputStream(byArray2));
            }
            while ((string2 = dataInputStream.readLine()) != null) {
                int n10;
                string = "" + string2.trim();
                if (string.startsWith("snap")) {
                    medium.setsnap(this.getint("snap", string, 0), this.getint("snap", string, 1), this.getint("snap", string, 2));
                }
                if (string.startsWith("sky")) {
                    medium.setsky(this.getint("sky", string, 0), this.getint("sky", string, 1), this.getint("sky", string, 2));
                    xtGraphics2.snap(checkPoints.stage);
                }
                if (string.startsWith("ground")) {
                    medium.setgrnd(this.getint("ground", string, 0), this.getint("ground", string, 1), this.getint("ground", string, 2));
                }
                if (string.startsWith("polys")) {
                    medium.setpolys(this.getint("polys", string, 0), this.getint("polys", string, 1), this.getint("polys", string, 2));
                }
                if (string.startsWith("fog")) {
                    medium.setfade(this.getint("fog", string, 0), this.getint("fog", string, 1), this.getint("fog", string, 2));
                }
                if (string.startsWith("texture")) {
                    medium.setexture(this.getint("texture", string, 0), this.getint("texture", string, 1), this.getint("texture", string, 2), this.getint("texture", string, 3));
                }
                if (string.startsWith("clouds")) {
                    medium.setcloads(this.getint("clouds", string, 0), this.getint("clouds", string, 1), this.getint("clouds", string, 2), this.getint("clouds", string, 3), this.getint("clouds", string, 4));
                }
                if (string.startsWith("density")) {
                    medium.fogd = (this.getint("density", string, 0) + 1) * 2 - 1;
                    if (medium.fogd < 1) {
                        medium.fogd = 1;
                    }
                    if (medium.fogd > 30) {
                        medium.fogd = 30;
                    }
                }
                if (string.startsWith("fadefrom")) {
                    medium.fadfrom(this.getint("fadefrom", string, 0));
                }
                if (string.startsWith("lightson")) {
                    medium.lightson = true;
                }
                if (string.startsWith("mountains")) {
                    medium.mgen = this.getint("mountains", string, 0);
                }
                if (string.startsWith("set")) {
                    int n11 = this.getint("set", string, 0);
                    if (xtGraphics2.nplayers == 8) {
                        if (n11 == 47) {
                            n11 = 76;
                        }
                        if (n11 == 48) {
                            n11 = 77;
                        }
                    }
                    boolean bl = true;
                    if (n11 >= 65 && n11 <= 75 && checkPoints.notb) {
                        bl = false;
                    }
                    if (bl) {
                        if (n11 == 49 || n11 == 64 || n11 >= 56 && n11 <= 61) {
                            xtGraphics2.newparts = true;
                        }
                        if ((checkPoints.stage < 0 || checkPoints.stage >= 28) && n11 >= 10 && n11 <= 25) {
                            medium.loadnew = true;
                        }
                        contOArray[this.nob] = new ContO(contOArray2[n11 += 46], this.getint("set", string, 1), medium.ground - contOArray2[n11].grat, this.getint("set", string, 2), this.getint("set", string, 3));
                        if (string.indexOf(")p") != -1) {
                            checkPoints.x[checkPoints.n] = this.getint("set", string, 1);
                            checkPoints.z[checkPoints.n] = this.getint("set", string, 2);
                            checkPoints.y[checkPoints.n] = 0;
                            checkPoints.typ[checkPoints.n] = 0;
                            if (string.indexOf(")pt") != -1) {
                                checkPoints.typ[checkPoints.n] = -1;
                            }
                            if (string.indexOf(")pr") != -1) {
                                checkPoints.typ[checkPoints.n] = -2;
                            }
                            if (string.indexOf(")po") != -1) {
                                checkPoints.typ[checkPoints.n] = -3;
                            }
                            if (string.indexOf(")ph") != -1) {
                                checkPoints.typ[checkPoints.n] = -4;
                            }
                            if (string.indexOf("out") != -1) {
                                System.out.println("out: " + checkPoints.n);
                            }
                            ++checkPoints.n;
                            this.notb = this.nob + 1;
                        }
                        ++this.nob;
                        if (medium.loadnew) {
                            medium.loadnew = false;
                        }
                    }
                }
                if (string.startsWith("chk")) {
                    int n12 = this.getint("chk", string, 0);
                    int n13 = medium.ground - contOArray2[n12 += 46].grat;
                    if (n12 == 110) {
                        n13 = this.getint("chk", string, 4);
                    }
                    contOArray[this.nob] = new ContO(contOArray2[n12], this.getint("chk", string, 1), n13, this.getint("chk", string, 2), this.getint("chk", string, 3));
                    checkPoints.x[checkPoints.n] = this.getint("chk", string, 1);
                    checkPoints.z[checkPoints.n] = this.getint("chk", string, 2);
                    checkPoints.y[checkPoints.n] = n13;
                    checkPoints.typ[checkPoints.n] = this.getint("chk", string, 3) == 0 ? 1 : 2;
                    checkPoints.pcs = checkPoints.n++;
                    contOArray[this.nob].checkpoint = checkPoints.nsp + 1;
                    ++checkPoints.nsp;
                    this.notb = ++this.nob;
                }
                if (checkPoints.nfix != 5 && string.startsWith("fix")) {
                    int n14 = this.getint("fix", string, 0);
                    contOArray[this.nob] = new ContO(contOArray2[n14 += 46], this.getint("fix", string, 1), this.getint("fix", string, 3), this.getint("fix", string, 2), this.getint("fix", string, 4));
                    checkPoints.fx[checkPoints.fn] = this.getint("fix", string, 1);
                    checkPoints.fz[checkPoints.fn] = this.getint("fix", string, 2);
                    checkPoints.fy[checkPoints.fn] = this.getint("fix", string, 3);
                    contOArray[this.nob].elec = true;
                    if (this.getint("fix", string, 4) != 0) {
                        checkPoints.roted[checkPoints.fn] = true;
                        contOArray[this.nob].roted = true;
                    } else {
                        checkPoints.roted[checkPoints.fn] = false;
                    }
                    checkPoints.special[checkPoints.fn] = string.indexOf(")s") != -1;
                    ++checkPoints.fn;
                    this.notb = ++this.nob;
                }
                if (!checkPoints.notb && string.startsWith("pile")) {
                    contOArray[this.nob] = new ContO(this.getint("pile", string, 0), this.getint("pile", string, 1), this.getint("pile", string, 2), medium, trackers, this.getint("pile", string, 3), this.getint("pile", string, 4), medium.ground);
                    ++this.nob;
                }
                if (xtGraphics2.multion == 0 && string.startsWith("nlaps")) {
                    checkPoints.nlaps = this.getint("nlaps", string, 0);
                    if (checkPoints.nlaps < 1) {
                        checkPoints.nlaps = 1;
                    }
                    if (checkPoints.nlaps > 15) {
                        checkPoints.nlaps = 15;
                    }
                }
                if (checkPoints.stage > 0 && string.startsWith("name")) {
                    checkPoints.name = this.getstring("name", string, 0).replace('|', ',');
                }
                if (string.startsWith("stagemaker")) {
                    checkPoints.maker = this.getstring("stagemaker", string, 0);
                }
                if (string.startsWith("publish")) {
                    checkPoints.pubt = this.getint("publish", string, 0);
                }
                if (string.startsWith("soundtrack")) {
                    checkPoints.trackname = this.getstring("soundtrack", string, 0);
                    checkPoints.trackvol = this.getint("soundtrack", string, 1);
                    if (checkPoints.trackvol < 50) {
                        checkPoints.trackvol = 50;
                    }
                    if (checkPoints.trackvol > 300) {
                        checkPoints.trackvol = 300;
                    }
                    xtGraphics2.sndsize[32] = this.getint("soundtrack", string, 2);
                }
                if (string.startsWith("maxr")) {
                    int n15;
                    int n16 = this.getint("maxr", string, 0);
                    n = n15 = this.getint("maxr", string, 1);
                    n5 = this.getint("maxr", string, 2);
                    for (int i = 0; i < n16; ++i) {
                        contOArray[this.nob] = new ContO(contOArray2[85], n15, medium.ground - contOArray2[85].grat, i * 4800 + n5, 0);
                        ++this.nob;
                    }
                    trackers.y[trackers.nt] = -5000;
                    trackers.rady[trackers.nt] = 7100;
                    trackers.x[trackers.nt] = n15 + 500;
                    trackers.radx[trackers.nt] = 600;
                    trackers.z[trackers.nt] = n16 * 4800 / 2 + n5 - 2400;
                    trackers.radz[trackers.nt] = n16 * 4800 / 2;
                    trackers.xy[trackers.nt] = 90;
                    trackers.zy[trackers.nt] = 0;
                    trackers.dam[trackers.nt] = 167;
                    trackers.decor[trackers.nt] = false;
                    trackers.skd[trackers.nt] = 0;
                    ++trackers.nt;
                }
                if (string.startsWith("maxl")) {
                    int n17;
                    int n18 = this.getint("maxl", string, 0);
                    n2 = n17 = this.getint("maxl", string, 1);
                    n5 = this.getint("maxl", string, 2);
                    for (int i = 0; i < n18; ++i) {
                        contOArray[this.nob] = new ContO(contOArray2[85], n17, medium.ground - contOArray2[85].grat, i * 4800 + n5, 180);
                        ++this.nob;
                    }
                    trackers.y[trackers.nt] = -5000;
                    trackers.rady[trackers.nt] = 7100;
                    trackers.x[trackers.nt] = n17 - 500;
                    trackers.radx[trackers.nt] = 600;
                    trackers.z[trackers.nt] = n18 * 4800 / 2 + n5 - 2400;
                    trackers.radz[trackers.nt] = n18 * 4800 / 2;
                    trackers.xy[trackers.nt] = -90;
                    trackers.zy[trackers.nt] = 0;
                    trackers.dam[trackers.nt] = 167;
                    trackers.decor[trackers.nt] = false;
                    trackers.skd[trackers.nt] = 0;
                    ++trackers.nt;
                }
                if (string.startsWith("maxt")) {
                    int n19;
                    int n20 = this.getint("maxt", string, 0);
                    n3 = n19 = this.getint("maxt", string, 1);
                    n5 = this.getint("maxt", string, 2);
                    for (int i = 0; i < n20; ++i) {
                        contOArray[this.nob] = new ContO(contOArray2[85], i * 4800 + n5, medium.ground - contOArray2[85].grat, n19, 90);
                        ++this.nob;
                    }
                    trackers.y[trackers.nt] = -5000;
                    trackers.rady[trackers.nt] = 7100;
                    trackers.z[trackers.nt] = n19 + 500;
                    trackers.radz[trackers.nt] = 600;
                    trackers.x[trackers.nt] = n20 * 4800 / 2 + n5 - 2400;
                    trackers.radx[trackers.nt] = n20 * 4800 / 2;
                    trackers.zy[trackers.nt] = 90;
                    trackers.xy[trackers.nt] = 0;
                    trackers.dam[trackers.nt] = 167;
                    trackers.decor[trackers.nt] = false;
                    trackers.skd[trackers.nt] = 0;
                    ++trackers.nt;
                }
                if (!string.startsWith("maxb")) continue;
                int n21 = this.getint("maxb", string, 0);
                n4 = n10 = this.getint("maxb", string, 1);
                n5 = this.getint("maxb", string, 2);
                for (int i = 0; i < n21; ++i) {
                    contOArray[this.nob] = new ContO(contOArray2[85], i * 4800 + n5, medium.ground - contOArray2[85].grat, n10, -90);
                    ++this.nob;
                }
                trackers.y[trackers.nt] = -5000;
                trackers.rady[trackers.nt] = 7100;
                trackers.z[trackers.nt] = n10 - 500;
                trackers.radz[trackers.nt] = 600;
                trackers.x[trackers.nt] = n21 * 4800 / 2 + n5 - 2400;
                trackers.radx[trackers.nt] = n21 * 4800 / 2;
                trackers.zy[trackers.nt] = -90;
                trackers.xy[trackers.nt] = 0;
                trackers.dam[trackers.nt] = 167;
                trackers.decor[trackers.nt] = false;
                trackers.skd[trackers.nt] = 0;
                ++trackers.nt;
            }
            dataInputStream.close();
            medium.newpolys(n2, n - n2, n4, n3 - n4, trackers, this.notb);
            medium.newclouds(n2, n, n4, n3);
            medium.newmountains(n2, n, n4, n3);
            medium.newstars();
            trackers.devidetrackers(n2, n - n2, n4, n3 - n4);
        }
        catch (Exception exception) {
            checkPoints.stage = -3;
            System.out.println("Error in stage " + checkPoints.stage);
            System.out.println("" + exception);
            System.out.println("At line: " + string);
        }
        if (checkPoints.nsp < 2) {
            checkPoints.stage = -3;
        }
        if (medium.nrw * medium.ncl >= 16000) {
            checkPoints.stage = -3;
        }
        if (checkPoints.stage != -3) {
            int n22;
            checkPoints.top20 = Math.abs(checkPoints.top20);
            medium.lightn = checkPoints.stage == 26 ? 0 : -1;
            medium.nochekflk = checkPoints.stage != 1 && checkPoints.stage != 11;
            for (n22 = 0; n22 < xtGraphics2.nplayers; ++n22) {
                this.u[n22].reset(checkPoints, xtGraphics2.sc[n22]);
            }
            xtGraphics2.resetstat(checkPoints.stage);
            checkPoints.calprox();
            for (n22 = 0; n22 < xtGraphics2.nplayers; ++n22) {
                if (xtGraphics2.fase == 22) {
                    xtGraphics2.colorCar(contOArray2[xtGraphics2.sc[n22]], n22);
                }
                contOArray[n22] = new ContO(contOArray2[xtGraphics2.sc[n22]], xtGraphics2.xstart[n22], 250 - contOArray2[xtGraphics2.sc[n22]].grat, xtGraphics2.zstart[n22], 0);
                madArray[n22].reseto(xtGraphics2.sc[n22], contOArray[n22], checkPoints);
            }
            if (xtGraphics2.fase == 2 || xtGraphics2.fase == -22) {
                medium.trx = (n2 + n) / 2;
                medium.trz = (n3 + n4) / 2;
                medium.ptr = 0;
                medium.ptcnt = -10;
                medium.hit = 45000;
                medium.fallen = 0;
                medium.nrnd = 0;
                medium.trk = 1;
                medium.ih = 25;
                medium.iw = 65;
                medium.h = 425;
                medium.w = 735;
                xtGraphics2.fase = 1;
                this.mouses = 0;
            }
            if (xtGraphics2.fase == 22) {
                medium.crs = false;
                xtGraphics2.fase = 5;
            }
            if (checkPoints.stage > 0) {
                n22 = checkPoints.stage;
                if (n22 > 27) {
                    n22 -= 27;
                } else if (n22 > 10) {
                    n22 -= 10;
                }
                xtGraphics2.asay = "Stage " + n22 + ":  " + checkPoints.name + " ";
            } else {
                xtGraphics2.asay = "Custom Stage:  " + checkPoints.name + " ";
            }
            record.reset(contOArray);
        } else if (xtGraphics2.fase == 2) {
            xtGraphics2.fase = 1;
        }
        System.gc();
    }

    public boolean loadstagePreview(int n, String string, ContO[] contOArray, ContO[] contOArray2, Medium medium, CheckPoints checkPoints) {
        int n2;
        boolean bl = true;
        if (n < 100) {
            checkPoints.stage = n;
            if (checkPoints.stage < 0) {
                checkPoints.name = string;
            }
        } else {
            checkPoints.stage = -2;
            if (this.sgame.getSelectedIndex() == 3 || this.sgame.getSelectedIndex() == 4) {
                checkPoints.name = this.mstgs.getSelectedItem();
            } else {
                n2 = this.mstgs.getSelectedItem().indexOf(" ") + 1;
                if (n2 > 0) {
                    checkPoints.name = this.mstgs.getSelectedItem().substring(n2);
                }
            }
        }
        this.nob = 0;
        checkPoints.n = 0;
        checkPoints.nsp = 0;
        checkPoints.fn = 0;
        checkPoints.haltall = false;
        checkPoints.wasted = 0;
        checkPoints.catchfin = 0;
        medium.ground = 250;
        this.view = 0;
        medium.trx = 0L;
        medium.trz = 0L;
        n2 = 0;
        int n3 = 100;
        int n4 = 0;
        int n5 = 100;
        String string2 = "";
        try {
            String string3;
            DataInputStream dataInputStream = null;
            if (checkPoints.stage > 0) {
                URL uRL = new URL("http://multiplayer.needformadness.com/stages/" + checkPoints.stage + ".txt");
                dataInputStream = new DataInputStream(uRL.openStream());
            } else {
                int n6;
                int n7;
                Object object;
                ZipInputStream zipInputStream;
                String string4 = "http://multiplayer.needformadness.com/tracks/" + checkPoints.name + ".radq";
                string4 = string4.replace(' ', '_');
                URL uRL = new URL(string4);
                int n8 = uRL.openConnection().getContentLength();
                DataInputStream dataInputStream2 = new DataInputStream(uRL.openStream());
                byte[] byArray = new byte[n8];
                dataInputStream2.readFully(byArray);
                if (byArray[0] == 80 && byArray[1] == 75 && byArray[2] == 3) {
                    zipInputStream = new ZipInputStream(new ByteArrayInputStream(byArray));
                } else {
                    object = new byte[n8 - 40];
                    for (n7 = 0; n7 < n8 - 40; ++n7) {
                        int n9 = 20;
                        if (n7 >= 500) {
                            n9 = 40;
                        }
                        object[n7] = byArray[n7 + n9];
                    }
                    zipInputStream = new ZipInputStream(new ByteArrayInputStream((byte[])object));
                }
                object = zipInputStream.getNextEntry();
                byte[] byArray2 = new byte[n7];
                int n10 = 0;
                for (n7 = Integer.valueOf(((ZipEntry)object).getName()).intValue(); n7 > 0; n7 -= n6) {
                    n6 = zipInputStream.read(byArray2, n10, n7);
                    n10 += n6;
                }
                zipInputStream.close();
                dataInputStream2.close();
                dataInputStream = new DataInputStream(new ByteArrayInputStream(byArray2));
            }
            while ((string3 = dataInputStream.readLine()) != null) {
                int n11;
                string2 = "" + string3.trim();
                if (string2.startsWith("snap")) {
                    medium.setsnap(this.getint("snap", string2, 0), this.getint("snap", string2, 1), this.getint("snap", string2, 2));
                }
                if (string2.startsWith("sky")) {
                    medium.setsky(this.getint("sky", string2, 0), this.getint("sky", string2, 1), this.getint("sky", string2, 2));
                }
                if (string2.startsWith("ground")) {
                    medium.setgrnd(this.getint("ground", string2, 0), this.getint("ground", string2, 1), this.getint("ground", string2, 2));
                }
                if (string2.startsWith("polys")) {
                    medium.setpolys(this.getint("polys", string2, 0), this.getint("polys", string2, 1), this.getint("polys", string2, 2));
                }
                if (string2.startsWith("fog")) {
                    medium.setfade(this.getint("fog", string2, 0), this.getint("fog", string2, 1), this.getint("fog", string2, 2));
                }
                if (string2.startsWith("texture")) {
                    medium.setexture(this.getint("texture", string2, 0), this.getint("texture", string2, 1), this.getint("texture", string2, 2), this.getint("texture", string2, 3));
                }
                if (string2.startsWith("clouds")) {
                    medium.setcloads(this.getint("clouds", string2, 0), this.getint("clouds", string2, 1), this.getint("clouds", string2, 2), this.getint("clouds", string2, 3), this.getint("clouds", string2, 4));
                }
                if (string2.startsWith("density")) {
                    medium.fogd = (this.getint("density", string2, 0) + 1) * 2 - 1;
                    if (medium.fogd < 1) {
                        medium.fogd = 1;
                    }
                    if (medium.fogd > 30) {
                        medium.fogd = 30;
                    }
                }
                if (string2.startsWith("fadefrom")) {
                    medium.fadfrom(this.getint("fadefrom", string2, 0));
                }
                if (string2.startsWith("lightson")) {
                    medium.lightson = true;
                }
                if (string2.startsWith("mountains")) {
                    medium.mgen = this.getint("mountains", string2, 0);
                }
                if (string2.startsWith("soundtrack")) {
                    checkPoints.trackname = this.getstring("soundtrack", string2, 0);
                    checkPoints.trackvol = this.getint("soundtrack", string2, 1);
                    if (checkPoints.trackvol < 50) {
                        checkPoints.trackvol = 50;
                    }
                    if (checkPoints.trackvol > 300) {
                        checkPoints.trackvol = 300;
                    }
                }
                if (string2.startsWith("set")) {
                    int n12 = this.getint("set", string2, 0);
                    contOArray[this.nob] = new ContO(contOArray2[n12 += 46], this.getint("set", string2, 1), medium.ground - contOArray2[n12].grat, this.getint("set", string2, 2), this.getint("set", string2, 3));
                    contOArray[this.nob].t.nt = 0;
                    if (string2.indexOf(")p") != -1) {
                        checkPoints.x[checkPoints.n] = this.getint("chk", string2, 1);
                        checkPoints.z[checkPoints.n] = this.getint("chk", string2, 2);
                        checkPoints.y[checkPoints.n] = 0;
                        checkPoints.typ[checkPoints.n] = 0;
                        if (string2.indexOf(")pt") != -1) {
                            checkPoints.typ[checkPoints.n] = -1;
                        }
                        if (string2.indexOf(")pr") != -1) {
                            checkPoints.typ[checkPoints.n] = -2;
                        }
                        if (string2.indexOf(")po") != -1) {
                            checkPoints.typ[checkPoints.n] = -3;
                        }
                        if (string2.indexOf(")ph") != -1) {
                            checkPoints.typ[checkPoints.n] = -4;
                        }
                        if (string2.indexOf("out") != -1) {
                            System.out.println("out: " + checkPoints.n);
                        }
                        ++checkPoints.n;
                    }
                    ++this.nob;
                }
                if (string2.startsWith("chk")) {
                    int n13 = this.getint("chk", string2, 0);
                    int n14 = medium.ground - contOArray2[n13 += 46].grat;
                    if (n13 == 110) {
                        n14 = this.getint("chk", string2, 4);
                    }
                    contOArray[this.nob] = new ContO(contOArray2[n13], this.getint("chk", string2, 1), n14, this.getint("chk", string2, 2), this.getint("chk", string2, 3));
                    checkPoints.x[checkPoints.n] = this.getint("chk", string2, 1);
                    checkPoints.z[checkPoints.n] = this.getint("chk", string2, 2);
                    checkPoints.y[checkPoints.n] = n14;
                    checkPoints.typ[checkPoints.n] = this.getint("chk", string2, 3) == 0 ? 1 : 2;
                    checkPoints.pcs = checkPoints.n++;
                    contOArray[this.nob].checkpoint = checkPoints.nsp + 1;
                    ++checkPoints.nsp;
                    ++this.nob;
                }
                if (string2.startsWith("fix")) {
                    int n15 = this.getint("fix", string2, 0);
                    contOArray[this.nob] = new ContO(contOArray2[n15 += 46], this.getint("fix", string2, 1), this.getint("fix", string2, 3), this.getint("fix", string2, 2), this.getint("fix", string2, 4));
                    checkPoints.fx[checkPoints.fn] = this.getint("fix", string2, 1);
                    checkPoints.fz[checkPoints.fn] = this.getint("fix", string2, 2);
                    checkPoints.fy[checkPoints.fn] = this.getint("fix", string2, 3);
                    contOArray[this.nob].elec = true;
                    if (this.getint("fix", string2, 4) != 0) {
                        checkPoints.roted[checkPoints.fn] = true;
                        contOArray[this.nob].roted = true;
                    } else {
                        checkPoints.roted[checkPoints.fn] = false;
                    }
                    checkPoints.special[checkPoints.fn] = string2.indexOf(")s") != -1;
                    ++checkPoints.fn;
                    ++this.nob;
                }
                if (string2.startsWith("nlaps")) {
                    checkPoints.nlaps = this.getint("nlaps", string2, 0);
                    if (checkPoints.nlaps < 1) {
                        checkPoints.nlaps = 1;
                    }
                    if (checkPoints.nlaps > 15) {
                        checkPoints.nlaps = 15;
                    }
                }
                if (checkPoints.stage > 0 && string2.startsWith("name")) {
                    checkPoints.name = this.getstring("name", string2, 0).replace('|', ',');
                }
                if (string2.startsWith("stagemaker")) {
                    checkPoints.maker = this.getstring("stagemaker", string2, 0);
                }
                if (string2.startsWith("publish")) {
                    checkPoints.pubt = this.getint("publish", string2, 0);
                }
                if (string2.startsWith("maxr")) {
                    int n16;
                    n2 = n16 = this.getint("maxr", string2, 1);
                }
                if (string2.startsWith("maxl")) {
                    int n17;
                    n3 = n17 = this.getint("maxl", string2, 1);
                }
                if (string2.startsWith("maxt")) {
                    int n18;
                    n4 = n18 = this.getint("maxt", string2, 1);
                }
                if (!string2.startsWith("maxb")) continue;
                n5 = n11 = this.getint("maxb", string2, 1);
            }
            dataInputStream.close();
            medium.newpolys(n3, n2 - n3, n5, n4 - n5, null, this.notb);
            medium.newclouds(n3, n2, n5, n4);
            medium.newmountains(n3, n2, n5, n4);
            medium.newstars();
        }
        catch (Exception exception) {
            bl = false;
            System.out.println("Error in stage " + checkPoints.stage);
            System.out.println("" + exception);
            System.out.println("At line: " + string2);
        }
        if (checkPoints.nsp < 2) {
            bl = false;
        }
        if (medium.nrw * medium.ncl >= 16000) {
            bl = false;
        }
        medium.trx = (n3 + n2) / 2;
        medium.trz = (n4 + n5) / 2;
        System.gc();
        return bl;
    }

    public void loadbase(ContO[] contOArray, Medium medium, Trackers trackers, xtGraphics xtGraphics2, boolean bl) {
        String[] stringArray = new String[]{"2000tornados", "formula7", "canyenaro", "lescrab", "nimi", "maxrevenge", "leadoxide", "koolkat", "drifter", "policecops", "mustang", "king", "audir8", "masheen", "radicalone", "drmonster"};
        String[] stringArray2 = new String[]{"road", "froad", "twister2", "twister1", "turn", "offroad", "bumproad", "offturn", "nroad", "nturn", "roblend", "noblend", "rnblend", "roadend", "offroadend", "hpground", "ramp30", "cramp35", "dramp15", "dhilo15", "slide10", "takeoff", "sramp22", "offbump", "offramp", "sofframp", "halfpipe", "spikes", "rail", "thewall", "checkpoint", "fixpoint", "offcheckpoint", "sideoff", "bsideoff", "uprise", "riseroad", "sroad", "soffroad", "tside", "launchpad", "thenet", "speedramp", "offhill", "slider", "uphill", "roll1", "roll2", "roll3", "roll4", "roll5", "roll6", "opile1", "opile2", "aircheckpoint", "tree1", "tree2", "tree3", "tree4", "tree5", "tree6", "tree7", "tree8", "cac1", "cac2", "cac3", "8sroad", "8soffroad"};
        int n = 0;
        xtGraphics2.dnload += 6;
        try {
            Object object;
            ZipInputStream zipInputStream = null;
            if (!bl) {
                object = new File("" + Madness.fpath + "data/models.zip");
                zipInputStream = new ZipInputStream(new FileInputStream((File)object));
            } else {
                object = new URL("http://multiplayer.needformadness.com/data/models.zip");
                zipInputStream = new ZipInputStream(((URL)object).openStream());
            }
            object = zipInputStream.getNextEntry();
            byte[] byArray = null;
            while (object != null) {
                int n2;
                int n3;
                int n4 = 0;
                for (n3 = 0; n3 < 16; ++n3) {
                    if (!((ZipEntry)object).getName().startsWith(stringArray[n3])) continue;
                    n4 = n3;
                }
                for (n3 = 0; n3 < 68; ++n3) {
                    if (!((ZipEntry)object).getName().startsWith(stringArray2[n3])) continue;
                    n4 = n3 + 56;
                }
                n += n3;
                byArray = new byte[n3];
                int n5 = 0;
                for (n3 = (int)((ZipEntry)object).getSize(); n3 > 0; n3 -= n2) {
                    n2 = zipInputStream.read(byArray, n5, n3);
                    n5 += n2;
                }
                contOArray[n4] = new ContO(byArray, medium, trackers);
                ++xtGraphics2.dnload;
                object = zipInputStream.getNextEntry();
            }
            zipInputStream.close();
        }
        catch (Exception exception) {
            System.out.println("Error Reading Models: " + exception);
        }
        System.gc();
        if (this.mload != -1 && n != 615671) {
            this.mload = 2;
        }
    }

    public int getint(String string, String string2, int n) {
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
        return Integer.valueOf(string3);
    }

    public String getstring(String string, String string2, int n) {
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
        return string3;
    }

    @Override
    public void start() {
        if (this.gamer == null) {
            this.gamer = new Thread(this);
        }
        this.gamer.start();
    }

    @Override
    public void stop() {
        if (this.exwist && this.gamer != null) {
            System.gc();
            this.gamer.stop();
            this.gamer = null;
        }
        this.exwist = true;
    }

    public void setcarcookie(int n, String string, float[] fArray, int n2, int[] nArray, boolean bl) {
        try {
            Closeable closeable;
            File file = new File("" + Madness.fpath + "data/user.data");
            String[] stringArray = new String[]{"", "", "", "", ""};
            if (file.exists()) {
                closeable = new BufferedReader(new FileReader(file));
                String string2 = null;
                for (int i = 0; (string2 = ((BufferedReader)closeable).readLine()) != null && i < 5; ++i) {
                    stringArray[i] = string2;
                }
                ((BufferedReader)closeable).close();
                closeable = null;
            }
            if (n2 == 0) {
                stringArray[1] = "lastcar(" + n + "," + (int)(fArray[0] * 100.0f) + "," + (int)(fArray[1] * 100.0f) + "," + (int)(fArray[2] * 100.0f) + "," + (int)(fArray[3] * 100.0f) + "," + (int)(fArray[4] * 100.0f) + "," + (int)(fArray[5] * 100.0f) + "," + string + ")";
            }
            if (n2 == 1) {
                stringArray[2] = "NFM1(" + n + "," + nArray[0] + ")";
            }
            if (n2 == 2) {
                stringArray[3] = "NFM2(" + n + "," + nArray[1] + ")";
            }
            stringArray[4] = "graphics(" + this.moto + "," + Madness.anti + ")";
            closeable = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < 5; ++i) {
                ((Writer)closeable).write(stringArray[i]);
                ((BufferedWriter)closeable).newLine();
            }
            ((BufferedWriter)closeable).close();
            closeable = null;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void setloggedcookie() {
        try {
            Closeable closeable;
            File file = new File("" + Madness.fpath + "data/user.data");
            String[] stringArray = new String[]{"", "", "", "", ""};
            if (file.exists()) {
                closeable = new BufferedReader(new FileReader(file));
                String string = null;
                for (int i = 0; (string = ((BufferedReader)closeable).readLine()) != null && i < 5; ++i) {
                    stringArray[i] = string;
                }
                ((BufferedReader)closeable).close();
                closeable = null;
            }
            stringArray[0] = this.keplo.getState() ? "lastuser(" + this.tnick.getText() + "," + this.tpass.getText() + ")" : "lastuser(" + this.tnick.getText() + ")";
            closeable = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < 5; ++i) {
                ((Writer)closeable).write(stringArray[i]);
                ((BufferedWriter)closeable).newLine();
            }
            ((BufferedWriter)closeable).close();
            closeable = null;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void readcookies(xtGraphics xtGraphics2, CarDefine carDefine, ContO[] contOArray) {
        xtGraphics2.nickname = "";
        try {
            int n;
            Object object;
            File file = new File("" + Madness.fpath + "data/user.data");
            String[] stringArray = new String[]{"", "", "", "", ""};
            if (file.exists()) {
                object = new BufferedReader(new FileReader(file));
                String string = null;
                for (n = 0; (string = ((BufferedReader)object).readLine()) != null && n < 5; ++n) {
                    stringArray[n] = string;
                }
                ((BufferedReader)object).close();
                object = null;
            }
            if (stringArray[0].startsWith("lastuser")) {
                xtGraphics2.nickname = this.getstring("lastuser", stringArray[0], 0);
                if (!xtGraphics2.nickname.equals("")) {
                    xtGraphics2.opselect = 1;
                }
                object = "";
                try {
                    object = this.getstring("lastuser", stringArray[0], 1);
                }
                catch (Exception exception) {
                    object = "";
                }
                if (!((String)object).equals("")) {
                    this.tnick.setText(xtGraphics2.nickname);
                    this.tpass.setText((String)object);
                    xtGraphics2.autolog = true;
                }
            }
            if (stringArray[2].startsWith("NFM1")) {
                int n2 = this.getint("NFM1", stringArray[2], 0);
                if (n2 >= 0 && n2 < 16) {
                    xtGraphics2.scm[0] = n2;
                    xtGraphics2.firstime = false;
                }
                if ((n2 = this.getint("NFM1", stringArray[2], 1)) >= 1 && n2 <= 11) {
                    xtGraphics2.unlocked[0] = n2;
                }
            }
            if (stringArray[3].startsWith("NFM2")) {
                int n3 = this.getint("NFM2", stringArray[3], 0);
                if (n3 >= 0 && n3 < 16) {
                    xtGraphics2.scm[1] = n3;
                    xtGraphics2.firstime = false;
                }
                if ((n3 = this.getint("NFM2", stringArray[3], 1)) >= 1 && n3 <= 17) {
                    xtGraphics2.unlocked[1] = n3;
                }
            }
            if (stringArray[4].startsWith("graphics")) {
                int n4 = this.getint("graphics", stringArray[4], 0);
                if (n4 >= 0 && n4 <= 1) {
                    this.moto = n4;
                }
                if ((n4 = this.getint("graphics", stringArray[4], 1)) >= 0 && n4 <= 1) {
                    Madness.anti = n4;
                }
            }
            if (stringArray[1].startsWith("lastcar")) {
                int n5 = this.getint("lastcar", stringArray[1], 0);
                carDefine.lastcar = this.getstring("lastcar", stringArray[1], 7);
                if (n5 >= 0 && n5 < 36) {
                    xtGraphics2.osc = n5;
                    xtGraphics2.firstime = false;
                }
                int n6 = 0;
                for (n = 0; n < 6; ++n) {
                    n5 = this.getint("lastcar", stringArray[1], n + 1);
                    if (n5 < 0 || n5 > 100) continue;
                    xtGraphics2.arnp[n] = (float)n5 / 100.0f;
                    ++n6;
                }
                if (n6 == 6 && xtGraphics2.osc >= 0 && xtGraphics2.osc <= 15) {
                    int n7;
                    Color color = Color.getHSBColor(xtGraphics2.arnp[0], xtGraphics2.arnp[1], 1.0f - xtGraphics2.arnp[2]);
                    Color color2 = Color.getHSBColor(xtGraphics2.arnp[3], xtGraphics2.arnp[4], 1.0f - xtGraphics2.arnp[5]);
                    for (n7 = 0; n7 < contOArray[xtGraphics2.osc].npl; ++n7) {
                        if (contOArray[xtGraphics2.osc].p[n7].colnum != 1) continue;
                        contOArray[xtGraphics2.osc].p[n7].c[0] = color.getRed();
                        contOArray[xtGraphics2.osc].p[n7].c[1] = color.getGreen();
                        contOArray[xtGraphics2.osc].p[n7].c[2] = color.getBlue();
                    }
                    for (n7 = 0; n7 < contOArray[xtGraphics2.osc].npl; ++n7) {
                        if (contOArray[xtGraphics2.osc].p[n7].colnum != 2) continue;
                        contOArray[xtGraphics2.osc].p[n7].c[0] = color2.getRed();
                        contOArray[xtGraphics2.osc].p[n7].c[1] = color2.getGreen();
                        contOArray[xtGraphics2.osc].p[n7].c[2] = color2.getBlue();
                    }
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void regprom() {
    }

    @Override
    public boolean mouseUp(Event event, int n, int n2) {
        if (!this.exwist) {
            if (this.mouses == 11) {
                this.xm = (int)((float)(n - this.apx) / this.apmult);
                this.ym = (int)((float)(n2 - this.apy) / this.apmult);
                this.mouses = -1;
            }
            this.moused = false;
        }
        if (!Madness.fullscreen) {
            if (n > this.getWidth() / 2 - 55 && n < this.getWidth() / 2 + 7 && n2 > 21 && n2 < 38 && !this.onbar) {
                this.smooth = this.smooth == 1 ? 0 : 1;
                this.showsize = 60;
            }
            if (n > this.getWidth() / 2 + 133 && n < this.getWidth() / 2 + 231 && n2 > 7 && n2 < 24 && !this.onbar) {
                Madness.anti = Madness.anti == 0 ? 1 : 0;
                this.showsize = 60;
            }
            if (n > this.getWidth() / 2 + 133 && n < this.getWidth() / 2 + 231 && n2 > 24 && n2 < 41 && !this.onbar) {
                this.moto = this.moto == 0 ? 1 : 0;
                this.showsize = 60;
            }
            if (this.oncarm) {
                Madness.carmaker();
            }
            if (this.onstgm) {
                Madness.stagemaker();
            }
            if (this.onfulls) {
                Madness.gofullscreen();
            }
            this.onbar = false;
        }
        return false;
    }

    @Override
    public boolean mouseDown(Event event, int n, int n2) {
        this.requestFocus();
        if (!this.exwist) {
            if (this.mouses == 0) {
                this.xm = (int)((float)(n - this.apx) / this.apmult);
                this.ym = (int)((float)(n2 - this.apy) / this.apmult);
                this.mouses = 1;
            }
            this.moused = true;
        }
        if (!Madness.fullscreen) {
            this.sizescreen(n, n2);
        }
        return false;
    }

    @Override
    public boolean mouseMove(Event event, int n, int n2) {
        if (!this.exwist && !this.lostfcs) {
            this.xm = (int)((float)(n - this.apx) / this.apmult);
            this.ym = (int)((float)(n2 - this.apy) / this.apmult);
        }
        if (!Madness.fullscreen) {
            if (this.showsize < 20) {
                this.showsize = 20;
            }
            if (n > 50 && n < 192 && n2 > 14 && n2 < 37) {
                if (!this.oncarm) {
                    this.oncarm = true;
                    this.setCursor(new Cursor(12));
                }
            } else if (this.oncarm) {
                this.oncarm = false;
                this.setCursor(new Cursor(0));
            }
            if (n > this.getWidth() - 208 && n < this.getWidth() - 50 && n2 > 14 && n2 < 37) {
                if (!this.onstgm) {
                    this.onstgm = true;
                    this.setCursor(new Cursor(12));
                }
            } else if (this.onstgm) {
                this.onstgm = false;
                this.setCursor(new Cursor(0));
            }
            if (n > this.getWidth() / 2 + 22 && n < this.getWidth() / 2 + 122 && n2 > 14 && n2 < 37) {
                if (!this.onfulls) {
                    this.onfulls = true;
                    this.setCursor(new Cursor(12));
                }
            } else if (this.onfulls) {
                this.onfulls = false;
                this.setCursor(new Cursor(0));
            }
        }
        return false;
    }

    @Override
    public boolean mouseDrag(Event event, int n, int n2) {
        if (!this.exwist && !this.lostfcs) {
            this.xm = (int)((float)(n - this.apx) / this.apmult);
            this.ym = (int)((float)(n2 - this.apy) / this.apmult);
        }
        if (!Madness.fullscreen) {
            this.sizescreen(n, n2);
        }
        return false;
    }

    @Override
    public boolean lostFocus(Event event, Object object) {
        if (!this.exwist && !this.lostfcs) {
            this.lostfcs = true;
            this.fcscnt = 10;
            if (this.u[0] != null) {
                if (this.u[0].multion == 0) {
                    this.u[0].falseo(1);
                } else if (this.u[0].chatup == 0) {
                    this.requestFocus();
                }
                this.setCursor(new Cursor(0));
            }
        }
        return false;
    }

    @Override
    public boolean gotFocus(Event event, Object object) {
        if (!this.exwist && this.lostfcs) {
            this.lostfcs = false;
        }
        return false;
    }

    public void mouseW(int n) {
        if (!this.exwist) {
            this.mousew += n * 4;
        }
    }

    public void sizescreen(int n, int n2) {
        if (n > this.getWidth() / 2 - 230 && n < this.getWidth() / 2 - 68 && n2 > 21 && n2 < 39 || this.onbar) {
            this.reqmult = (float)(n - (this.getWidth() / 2 - 222)) / 141.0f;
            if ((double)this.reqmult < 0.1) {
                this.reqmult = 0.0f;
            }
            if (this.reqmult > 1.0f) {
                this.reqmult = 1.0f;
            }
            this.onbar = true;
            this.showsize = 100;
        }
    }

    public void catchlink() {
        if (!this.lostfcs) {
            if (this.xm > 65 && this.xm < 735 && this.ym > 135 && this.ym < 194 || this.xm > 275 && this.xm < 525 && this.ym > 265 && this.ym < 284) {
                this.setCursor(new Cursor(12));
                if (this.mouses == 2) {
                    this.openurl("http://www.radicalplay.com/");
                }
            } else {
                this.setCursor(new Cursor(0));
            }
        }
    }

    public void musiclink() {
        this.openurl("http://multiplayer.needformadness.com/music.html");
    }

    public void reglink() {
        this.openurl("http://multiplayer.needformadness.com/register.html?ref=game");
    }

    public void madlink() {
        this.openurl("http://www.needformadness.com/");
    }

    public void editlink(String string, boolean bl) {
        String string2 = "";
        if (bl) {
            string2 = "?display=upgrade";
        }
        this.openurl("http://multiplayer.needformadness.com/edit.pl" + string2 + "#" + string + "");
    }

    public void regnew() {
        this.openurl("http://multiplayer.needformadness.com/registernew.pl");
    }

    public void multlink() {
        this.openurl("http://multiplayer.needformadness.com/");
    }

    public void setupini() {
        Madness.inisetup = true;
        try {
            File file = new File("" + Madness.fpath + "Madness.ini");
            if (file.exists()) {
                int n;
                String[] stringArray = new String[40];
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String string = null;
                for (n = 0; (string = bufferedReader.readLine()) != null && n < 40; ++n) {
                    stringArray[n] = string;
                    if (stringArray[n].startsWith("Class Path")) {
                        stringArray[n] = stringArray[n].indexOf("madapps.jar") != -1 ? "Class Path=\\data\\madapps.jar;" : "Class Path=\\data\\madapp.jar;";
                    }
                    if (!stringArray[n].startsWith("JRE Path")) continue;
                    stringArray[n] = "JRE Path=data\\jre\\";
                }
                bufferedReader.close();
                bufferedReader = null;
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                for (int i = 0; i < n; ++i) {
                    bufferedWriter.write(stringArray[i]);
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
                bufferedWriter = null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        Madness.inisetup = false;
    }

    public void openurl(String string) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(string));
            }
            catch (Exception exception) {}
        } else {
            try {
                Runtime.getRuntime().exec("" + Madness.urlopen() + " " + string + "");
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Override
    public boolean keyDown(Event event, int n) {
        if (!this.exwist) {
            if (this.u[0].multion < 2) {
                if (n == 1004) {
                    this.u[0].up = true;
                }
                if (n == 1005) {
                    this.u[0].down = true;
                }
                if (n == 1007) {
                    this.u[0].right = true;
                }
                if (n == 1006) {
                    this.u[0].left = true;
                }
                if (n == 32) {
                    this.u[0].handb = true;
                }
            }
            if (n == 10) {
                this.u[0].enter = true;
            }
            if (n == 27) {
                this.u[0].exit = true;
                if (this.u[0].chatup != 0) {
                    this.u[0].chatup = 0;
                }
            }
            if ((n == 67 || n == 99) && this.u[0].multion != 0 && this.u[0].chatup == 0) {
                this.u[0].chatup = 2;
                this.view = 0;
            }
            if (this.u[0].chatup == 0) {
                if (n == 120 || n == 88) {
                    this.u[0].lookback = -1;
                }
                if (n == 122 || n == 90) {
                    this.u[0].lookback = 1;
                }
                if (n == 77 || n == 109) {
                    this.u[0].mutem = !this.u[0].mutem;
                }
                if (n == 78 || n == 110) {
                    this.u[0].mutes = !this.u[0].mutes;
                }
                if (n == 97 || n == 65) {
                    this.u[0].arrace = !this.u[0].arrace;
                }
                if (n == 115 || n == 83) {
                    this.u[0].radar = !this.u[0].radar;
                }
                if (n == 118 || n == 86) {
                    ++this.view;
                    if (this.view == 3) {
                        this.view = 0;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(Event event, int n) {
        if (!this.exwist) {
            if (this.u[0].multion < 2) {
                if (n == 1004) {
                    this.u[0].up = false;
                }
                if (n == 1005) {
                    this.u[0].down = false;
                }
                if (n == 1007) {
                    this.u[0].right = false;
                }
                if (n == 1006) {
                    this.u[0].left = false;
                }
                if (n == 32) {
                    this.u[0].handb = false;
                }
            }
            if (n == 27) {
                this.u[0].exit = false;
                if (Madness.fullscreen) {
                    Madness.exitfullscreen();
                }
            }
            if (n == 120 || n == 88 || n == 122 || n == 90) {
                this.u[0].lookback = 0;
            }
        }
        return false;
    }
}

