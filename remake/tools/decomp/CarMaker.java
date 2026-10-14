/*
 * Decompiled with CFR 0.152.
 */
import java.applet.Applet;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Event;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.RenderingHints;
import java.awt.TextArea;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.JOptionPane;

public class CarMaker
extends Applet
implements Runnable,
ActionListener {
    Graphics2D rd;
    Image offImage;
    Thread thredo;
    boolean exwist = false;
    FontMetrics ftm;
    int apx = 0;
    int apy = 0;
    boolean focuson = true;
    Image[] btgame = new Image[2];
    Image logo;
    boolean onbtgame = false;
    int tab = 0;
    int tabed = -1;
    boolean loadedfile = false;
    String carname = "";
    String scar = "";
    String suser = "Horaks";
    String sfont = "Monospaced";
    int sthm = 0;
    int sfase = 0;
    Smenu slcar = new Smenu(2000);
    boolean tutok = false;
    int flk = 0;
    boolean changed = false;
    String lastedo = "";
    boolean prefs = false;
    boolean mirror = false;
    PopupMenu popupMenu;
    TextArea editor = new TextArea(20, 20);
    TextField srch = new TextField("", 15);
    TextField rplc = new TextField("", 15);
    Smenu fontsel = new Smenu(40);
    String cfont = "Monospaced";
    Smenu ctheme = new Smenu(40);
    Color defb;
    Color deff;
    int cthm = 0;
    int cntprf = 0;
    int cntpls = 0;
    int cntchk = 0;
    int npolys = 0;
    boolean tomany = false;
    int ox = 335;
    int oy = 40;
    int oz = 800;
    int oxz = -90;
    int oxy = 0;
    int ozy = 0;
    Medium m = new Medium();
    Trackers t = new Trackers();
    ContO o;
    ContO[] compo = new ContO[16];
    boolean right = false;
    boolean left = false;
    boolean up = false;
    boolean down = false;
    boolean rotl = false;
    boolean rotr = false;
    boolean plus = false;
    boolean minus = false;
    boolean in = false;
    boolean out = false;
    boolean pflk = false;
    boolean breakbond = false;
    int polynum = -1;
    int prflk = 0;
    int bfo = 0;
    int dtab = 0;
    int dtabed = -1;
    int mouseon = -1;
    String fcol = "";
    String ofcol = "";
    String scol = "";
    String oscol = "";
    float[] fhsb = new float[]{0.5f, 0.5f, 0.5f};
    float[] shsb = new float[]{0.5f, 0.5f, 0.5f};
    int[] scale = new int[]{100, 100, 100};
    int[] oscale = new int[]{100, 100, 100};
    Smenu compcar = new Smenu(40);
    int compsel = 0;
    int[] adna = new int[]{276, 276, 276, 276, 276, 276};
    boolean changed2 = false;
    TextField[] wv = new TextField[16];
    boolean defnow = false;
    String aply1 = "";
    String aply2 = "";
    boolean aplyd1 = false;
    boolean aplyd2 = false;
    boolean forwheels = false;
    Smenu cls = new Smenu(40);
    Smenu simcar = new Smenu(40);
    int[] stat = new int[]{100, 100, 100, 100, 100};
    int[] rstat = new int[]{0, 0, 0, 0, 0};
    int[][] carstat = new int[][]{{110, 81, 131, 98, 100}, {200, 200, 88, 16, 16}, {108, 80, 93, 114, 125}, {146, 119, 100, 83, 72}, {109, 85, 141, 96, 93}, {128, 98, 102, 109, 123}, {115, 139, 96, 117, 133}, {120, 81, 145, 126, 128}, {140, 122, 101, 113, 124}, {110, 144, 100, 154, 92}, {133, 122, 144, 115, 126}, {107, 96, 96, 192, 189}, {192, 200, 106, 92, 90}, {88, 104, 88, 200, 200}, {148, 150, 197, 95, 90}, {112, 128, 120, 192, 128}};
    int carsel = 0;
    int clsel = 0;
    boolean statdef = false;
    int pfase = 0;
    int[] phys = new int[]{50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
    int[] rphys = new int[]{50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
    int[] crash = new int[]{50, 50, 50};
    int[] rcrash = new int[]{50, 50, 50};
    String[] pname = new String[]{"Handbrake", "Turning Sensitivity", "Tire Grip", "Bouncing", "Empty", "Lifts Others", "Gets Lifted", "Pushes Others", "Gets Pushed", "Aerial Rotation Speed", "Aerial Control/Gliding"};
    int[] pnx = new int[]{62, 20, 76, 71, 60, 38, 44, 20, 33, 320, 324};
    String[] usage = new String[]{"Handbrake:\nThis defines the hand braking power of the car.\nThe more handbrake the car has the faster it brakes when you press Spacebar while driving.\nBut also the lesser the Handbrake the more the car can drift when you press Spacebar.\n\n", "Turning Sensitivity:\nThis defines how fast the car turns (or how fast the wheels respond to turning).\nThe more turning sensitive the faster the car turns and responds.\n\nWhen designing a fast car that is more racing oriented high turning sensitivity is     \nrecommended for the car to be able to take sharp and quick turns.\nHowever too much turning sensitivity can make the car hard to drive!\n\nWhen designing a slower and bigger car (like El King) lower turning sensitivity is\nrecommended for a more realistic effect.\n\n", "Tires Grip:\nThis defines the griping power of the car\u2019s wheels on the ground.\n\nThe more griping the more the cars sticks to track.\nThe less gripping the more the car drifts in the turns.\n\nSome drifting can be helpful as it makes the car drive smoother while less drifting can\nmake the car more irritable, it depends on how you like to drive the car and how it\nfeels for you.\n\n", "Bouncing:\nThis defines how the car bounces back when it hits the ground or obstacles.\n\nBouncing can help when performing stunts as when you land up side down\nif the car bounces it can be filliped over before landing again to avoid a 'bad landing'.\n\nHowever bouncing is not helpful in controlling the car and in racing.\n\n", "If you can read this then you are decompiling the code! Please don\u2019t bother trying to allow the car maker to give you max stats, the stats get check on the server before the car can be accepted. Just to save you sometime.", "Lifts Others:\nThis defines if the car lifts up other cars when it collides with them from the front and\nhow high it can lift them.\n\nDoes the car have a pointy nose like MAX Revenge, Radical One or La Vita Crab, a\npointy nose/front part that can go under the wheels of other cars and lift them?\nIf so then give it some Lifts Others.\n\nIf it has a nose/front part that is a block like most cars then give it 0 Lifts Others.\n\n", "Gets Lifted:\nThis defines if the car can get lifted over other cars when it collides with them and how\nhigh it can get lifted.\n\nIs the car higher off the ground like Wow Caninaro or has big wheels like Dr Monstaa,\nshould its jump over cars when it collides with them?\nIf so then give it some Gets Lifted depending on how high it should go.\n\nIf the car is lower to the ground like most cars then it should have 0 Gets Lifted\n\n", "Pushes Others:\nThis defines if the car pushes other cars away when it collides with them and how far it\ncan push them.\n\nIs the car a heavy car with a strong body like MASHEEN or El King, where when it\ncollides with other cars it pushes them away?\nOr does the car have special bumpers or body parts for pushing cars away like Sword of\nJustice has?\nIf so then give it some Pushes Others depending how strong you think it can push cars.\n\nIf it is a car like any other car, with an average weight and body strength then you should\ngive it 0 Pushes Others.\n\n", "Gets Pushed:\nThis defines if the car gets pushed away when it collides with other cars and how far it\ngets pushed away.\n\nIf the car is lighter then most cars, then it should get pushed away when it collides with\nothers cars.\nGetting pushed can be helpful if the car is week because it gets it away from the danger\n(from the car that hit it) faster, making it take lesser hits and escape better.\nHowever getting pushed is not helpful when racing.\n\n", "Aerial Rotation Speed:\nThis adjusts how fast the car can rotate and flip in the air when its performing a stunt.\n\nThis variable also depends on how much the \u2018Stunts\u2019 stat of the car is, if the car has a\nhigh Stunts stat then this variable will have a much bigger effect, if it has low Stunts stat\nthe variable will have a lower effect.\n\nIf you think the car is rotating too fast or too slow in the air when performing a stunt use\nthis variable to adjust that.\n\nIf the aerial rotation is too fast it can make the car hard to control in the air as it flips and\nhard to land upright.\n\nIf the car is a big and heavy car like MASHHEN or El King then it should have low\naerial rotation for a realistic effect.\n\n", "Aerial Control/Gliding:\nThis adjusts the cars ability to push itself in the air and glide when performing stunts!\n\nIf you don\u2019t know, in the game:\nBackward looping pushes the car upwards. \nForward looping pushes the car forwards. \nLeft and right rolling pushes the car left and right. \n\nThis variable adjust the power if this aerial push.\n\nThe variable also depends on how much the \u2018Stunts\u2019 stat of the car is, if the car has a\nhigh Stunts stat then this variable will have a much bigger effect, if it has low Stunts stat\nthe variable will have a lower effect.\n\nIf the car has some kind of wings or fins like Radical One or Kool Kat have then it should\nhave higher aerial control and gliding ability.\n\n", "Crash Look Test!\nThis defines how the car will look when it gets damaged.\nOr in other words what the car will look like as it gets damaged until it becomes wasted!\n\nIMPORTANT:\nYou need to perform a 'Normal Crash' test with a 'Roof Crash' test until the car gets totally destroyed (gets wasted and burns).\nYou need to also try a 'Normal Crash' test alone (without the roof crash) until the car gets wasted!\nA 'Roof Crash' happens significantly more when the car falls on its roof from a high jumps.\nA 'Normal Crash' is what happens as the car crashes normally with other cars and obstacles.\n\nClick any of adjustment variable names \u2018Radius\u2019, \u2018Magnitude\u2019 and \u2018Roof Destruction\u2019 to learn about their effect.\n\n>  You must perform the crash test more then once in order to make sure that this is how your want the car to look as it gets damaged\nuntil total destruction.", "Crash Radius:\nThe radius around the crash at which the polygons/pieces that lay inside it get\naffected.\n\nOr basically in other words the number of pieces that get affected on collision (the pieces\naround the crash location).\n\nIncreasing the radius will result in more pieces/polygons around the point of collision\ngetting crashed and distorted.\nDecreasing the radius means less pieces/polygons around the collision point getting\ndistorted and crashed.\n\n", "Crash Magnitude:\nThe magnitude of the distortion and indentation to occur on the effected pieces/polygons.\n\nOr basically in other words the amount of destruction that happens to each piece when\ncrashed.\n\nHigher magnitude means the piece gets more destructed from an amount of damage,\nlower magnitude means the piece gets less destructed from that same amount of damage.\n\n", "Roof Destruction:\nThe amount of destruction to occur on the car\u2019s top.\nThe length of indentation and destruction to happen from above.\n\nTo really see this variable's effect try crashing the roof alone (without a normal crash),\ntry more then once while fixing the car and changing the variable\u2019s value to see the\ndifference.\n\nThe roof crash normally happens in the game when the car lands upside down from a\njump or when a big car like Dr Monstaa steps on it.\n\n"};
    int hitmag = 0;
    int actmag = 0;
    int squash = 0;
    boolean crashok = false;
    boolean crashleft = false;
    soundClip[] crashs = new soundClip[3];
    soundClip[] lowcrashs = new soundClip[3];
    Smenu engine = new Smenu(40);
    soundClip[][] engs = new soundClip[5][5];
    int engsel = 0;
    boolean engon = false;
    Smenu witho = new Smenu(40);
    boolean tested = false;
    boolean rateh = false;
    int handling = 140;
    int logged = 0;
    TextField tnick = new TextField("", 15);
    TextField tpass = new TextField("", 15);
    Smenu pubitem = new Smenu(707);
    Smenu pubtyp = new Smenu(40);
    int nmc = 0;
    int roto = 0;
    String[] mycars = new String[20];
    String[] maker = new String[20];
    int[] pubt = new int[20];
    int[] clas = new int[20];
    String[][] addeda = new String[20][5000];
    int[] nad = new int[20];
    String justpubd = "";
    boolean[] pessd = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    int[] bx = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] by = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] bw = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int btn = 0;
    int mouses = 0;
    int xm = 0;
    int ym = 0;
    int sls = -1;
    int sle = -1;
    int crshturn = 0;
    boolean crashup = false;
    boolean openm = false;
    boolean mousdr = false;
    boolean waso = false;
    boolean objfacend = false;
    boolean multf10 = false;

    /*
     * Opcode count of 14060 triggered aggressive code reduction.  Override with --aggressivesizethreshold.
     * Unable to fully structure code
     */
    @Override
    public void run() {
        this.thredo.setPriority(10);
        this.btgame[0] = this.getImage("" + Madness.fpath + "data/backtogame1.gif");
        this.btgame[1] = this.getImage("" + Madness.fpath + "data/backtogame2.gif");
        this.logo = this.getImage("" + Madness.fpath + "data/carmakerlogo.gif");
        this.m.w = 700;
        this.m.cx = 350;
        this.m.y = -240;
        this.m.z = -400;
        this.m.zy = 4;
        this.m.focus_point = 800;
        this.m.fadfrom(8000);
        this.m.cfade[0] = 187;
        this.m.cfade[1] = 210;
        this.m.cfade[2] = 227;
        this.loadsounds();
        this.loadbase();
        this.m.loadnew = true;
        this.loadsettings();
        this.editor.setFont(new Font(this.cfont, 1, 14));
        this.srch.setFont(new Font(this.cfont, 1, 14));
        this.rplc.setFont(new Font(this.cfont, 1, 14));
        for (var1_1 = 0; var1_1 < 16; ++var1_1) {
            this.wv[var1_1].setFont(new Font(this.cfont, 1, 14));
        }
        this.setheme();
        if (Madness.testdrive != 0) {
            if (Madness.testcar.equals("Failx12")) {
                JOptionPane.showMessageDialog(null, "Failed to load car! Please make sure car is saved before Test Drive.", "Car Maker", 1);
                this.thredo.stop();
            } else {
                this.carname = Madness.testcar;
                this.loadfile();
                if (this.loadedfile) {
                    this.tested = true;
                    this.tab = 2;
                    this.dtab = 6;
                    this.witho.select(Madness.testdrive - 1);
                }
            }
            Madness.testcar = "";
            Madness.testdrive = 0;
        }
        var1_1 = 0;
        if (!this.carname.equals("")) {
            this.tutok = true;
            var1_1 = 1;
        }
        while (!this.exwist) {
            if (this.tab != this.tabed) {
                this.hidefields();
                if (this.tab == 1) {
                    this.editor.enable();
                } else {
                    this.editor.disable();
                }
                if (this.tabed == 2) {
                    if (!this.breakbond) {
                        if (!this.editor.getText().equals(this.lastedo)) {
                            this.editor.setText(this.lastedo);
                        }
                    } else {
                        this.breakbond = false;
                    }
                }
                this.setCursor(new Cursor(0));
            }
            this.rd.setColor(new Color(225, 225, 225));
            this.rd.fillRect(0, 0, 700, 550);
            this.rd.setColor(new Color(0, 0, 0));
            this.btn = 0;
            var2_2 = 50;
            if (this.tab == 0) {
                if (this.tabed != this.tab) {
                    this.slcar.removeAll();
                    this.slcar.maxl = 200;
                    this.slcar.add(this.rd, "Select a Car                      ");
                    var3_3 = new File("" + Madness.fpath + "mycars/").list();
                    if (var3_3 != null) {
                        for (var4_16 = 0; var4_16 < var3_3.length; ++var4_16) {
                            if (!var3_3[var4_16].toLowerCase().endsWith(".rad")) continue;
                            this.slcar.add(this.rd, var3_3[var4_16].substring(0, var3_3[var4_16].length() - 4));
                        }
                    }
                    if (this.carname.equals("")) {
                        this.slcar.select(0);
                    } else {
                        this.slcar.select(this.carname);
                        if (this.carname.equals(this.slcar.getSelectedItem())) {
                            this.loadfile();
                        }
                    }
                    this.mouseon = -1;
                    this.srch.setText("");
                    this.sfase = 0;
                }
                this.rd.setFont(new Font("Arial", 1, 13));
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawImage(this.logo, 214, 35, null);
                if (this.xm > 214 && this.xm < 485 && this.ym > 25 && this.ym < 104 && !this.openm) {
                    if (this.mouseon == -1) {
                        this.mouseon = 3;
                        this.setCursor(new Cursor(12));
                    }
                } else if (this.mouseon == 3) {
                    this.mouseon = -1;
                    this.setCursor(new Cursor(0));
                }
                if (this.mouseon == 3 && this.mouses == -1) {
                    this.openhlink();
                }
                var3_4 = 30;
                var4_16 = 0;
                if (this.tutok) {
                    var3_4 = 250;
                    var4_16 = -70;
                }
                if (this.xm > 76 && this.xm < 624 && this.ym > 84 + var3_4 && this.ym < 167 + var3_4 && !this.openm) {
                    if (this.mouseon == -1) {
                        this.mouseon = 1;
                        this.setCursor(new Cursor(12));
                    }
                } else if (this.mouseon == 1) {
                    this.mouseon = -1;
                    this.setCursor(new Cursor(0));
                }
                var5_29 = 0;
                if (!this.tutok && this.mouseon != 1 && var1_1 == 0) {
                    if (this.flk <= 0) {
                        this.rd.setColor(new Color(255, 0, 0));
                        --this.flk;
                        if (this.flk == -2) {
                            this.flk = 1;
                        }
                    } else {
                        this.rd.setColor(new Color(0, 0, 255));
                        var5_29 = 2;
                        ++this.flk;
                        if (this.flk == 3) {
                            this.flk = 0;
                        }
                    }
                }
                this.rd.drawLine(76 + var5_29, 84 + var3_4, 76 + var5_29, 167 + var3_4);
                this.rd.drawLine(76 + var5_29, 84 + var3_4, 95 + var5_29, 84 + var3_4);
                this.rd.drawLine(76 + var5_29, 167 + var3_4, 95 + var5_29, 167 + var3_4);
                this.rd.drawLine(624 - var5_29, 84 + var3_4, 624 - var5_29, 167 + var3_4);
                this.rd.drawLine(624 - var5_29, 84 + var3_4, 605 - var5_29, 84 + var3_4);
                this.rd.drawLine(624 - var5_29, 167 + var3_4, 605 - var5_29, 167 + var3_4);
                if (this.mouseon == 1) {
                    this.rd.setColor(new Color(0, 64, 128));
                } else {
                    this.rd.setColor(new Color(0, 0, 0));
                }
                this.rd.drawString("If this is your first time creating a car please follow the tutorial found at:", 106, 110 + var3_4);
                this.rd.setColor(new Color(0, 128, 255));
                this.rd.drawString("http://www.needformadness.com/developer/simplecar.html", 106, 130 + var3_4);
                if (this.mouseon == 1) {
                    this.rd.setColor(new Color(0, 128, 255));
                } else {
                    this.rd.setColor(new Color(0, 64, 128));
                }
                this.rd.drawLine(106, 131 + var3_4, 480, 131 + var3_4);
                if (this.mouseon == 1) {
                    this.rd.setColor(new Color(0, 64, 128));
                } else {
                    this.rd.setColor(new Color(0, 0, 0));
                }
                this.rd.drawString("It is highly recommended that you follow this tutorial before trying anything!", 106, 150 + var3_4);
                if (this.mouseon == 1 && this.mouses == -1) {
                    this.openlink();
                    var1_1 = 1;
                }
                if (this.xm > 200 && this.xm < 500 && this.ym > 467 && this.ym < 504 && !this.openm) {
                    if (this.mouseon == -1) {
                        this.mouseon = 2;
                        this.setCursor(new Cursor(12));
                    }
                } else if (this.mouseon == 2) {
                    this.mouseon = -1;
                    this.setCursor(new Cursor(0));
                }
                this.ftm = this.rd.getFontMetrics();
                if (this.mouseon == 2) {
                    this.rd.setColor(new Color(0, 64, 128));
                } else {
                    this.rd.setColor(new Color(0, 0, 0));
                }
                this.rd.drawString("For the Car Maker Homepage, Development Center and Forums :", 350 - this.ftm.stringWidth("For the Car Maker Homepage, Development Center and Forums :") / 2, 480);
                this.rd.setColor(new Color(0, 128, 255));
                var6_39 = "http://www.needformadness.com/developer/";
                this.rd.drawString(var6_39, 350 - this.ftm.stringWidth(var6_39) / 2, 500);
                if (this.mouseon == 2) {
                    this.rd.setColor(new Color(0, 128, 255));
                } else {
                    this.rd.setColor(new Color(0, 64, 128));
                }
                this.rd.drawLine(350 - this.ftm.stringWidth(var6_39) / 2, 501, 350 + this.ftm.stringWidth(var6_39) / 2, 501);
                if (this.mouseon == 2 && this.mouses == -1) {
                    this.openhlink();
                }
                var7_61 = 0;
                if (this.sfase == 3) {
                    var7_61 = 100;
                }
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawRect(177 - var7_61, 202 + var4_16, 346 + var7_61 * 2, 167 + var7_61 / 5);
                if (this.sfase == 0) {
                    this.rd.drawString("Select Car to Edit", 350 - this.ftm.stringWidth("Select Car to Edit") / 2, 230 + var4_16);
                    this.slcar.move(250, 240 + var4_16);
                    if (this.slcar.getWidth() != 200) {
                        this.slcar.setSize(200, 21);
                    }
                    if (!this.slcar.isShowing()) {
                        this.slcar.show();
                    }
                    this.stringbutton("    Make new Car    ", 430, 296 + var4_16, 0, true);
                    this.stringbutton("     Rename Car     ", 270, 296 + var4_16, 0, false);
                    this.stringbutton("      Delete Car      ", 270, 336 + var4_16, 0, false);
                    this.stringbutton("     Import & Export     ", 430, 336 + var4_16, 0, false);
                    if (this.slcar.getSelectedIndex() != 0) {
                        if (!this.carname.equals(this.slcar.getSelectedItem())) {
                            this.tomany = false;
                            this.carname = this.slcar.getSelectedItem();
                            this.loadfile();
                            this.editor.select(0, 0);
                            this.tested = false;
                            this.requestFocus();
                        }
                    } else {
                        this.carname = "";
                    }
                }
                if (this.sfase == 1) {
                    this.rd.drawString("Make a new Car", 350 - this.ftm.stringWidth("Make a new Car") / 2, 230 + var4_16);
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.rd.drawString("New car name :", 228, 266 + var4_16);
                    this.movefield(this.srch, 335, 250 + var4_16, 129, 22);
                    if (!this.srch.isShowing()) {
                        this.srch.show();
                        this.srch.requestFocus();
                    }
                    this.fixtext(this.srch);
                    this.stringbutton("    Make Car    ", 350, 306 + var4_16, 0, true);
                    this.stringbutton("  Cancel  ", 350, 346 + var4_16, 0, false);
                }
                if (this.sfase == 2) {
                    this.rd.drawString("Rename Car :  " + this.carname + "", 350 - this.ftm.stringWidth("Rename Car :  " + this.carname + "") / 2, 230 + var4_16);
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.rd.drawString("New name :", 239, 266 + var4_16);
                    this.movefield(this.srch, 316, 250 + var4_16, 129, 22);
                    if (!this.srch.isShowing()) {
                        this.srch.show();
                        this.srch.requestFocus();
                    }
                    this.fixtext(this.srch);
                    this.stringbutton("   Rename Car   ", 350, 306 + var4_16, 0, true);
                    this.stringbutton("  Cancel  ", 350, 346 + var4_16, 0, false);
                }
                if (this.sfase == 3) {
                    this.rd.drawString("Import a Wavefront OBJ 3D Model", 350 - this.ftm.stringWidth("Import a Wavefront OBJ 3D Model") / 2, 230 + var4_16);
                    if (this.xm > 116 && this.xm < 584 && this.ym > 246 + var4_16 && this.ym < 290 + var4_16) {
                        if (this.mouseon == -1) {
                            this.mouseon = 3;
                            this.setCursor(new Cursor(12));
                        }
                    } else if (this.mouseon == 3) {
                        this.mouseon = -1;
                        this.setCursor(new Cursor(0));
                    }
                    this.ftm = this.rd.getFontMetrics();
                    if (this.mouseon == 3) {
                        this.rd.setColor(new Color(0, 64, 128));
                    } else {
                        this.rd.setColor(new Color(0, 0, 0));
                    }
                    this.rd.drawString("Please read the important information about importing cars found at:", 350 - this.ftm.stringWidth("Please read the important information about importing cars, found here :") / 2, 260 + var4_16);
                    this.rd.setColor(new Color(0, 128, 255));
                    var6_39 = "http://www.needformadness.com/developer/extras.html";
                    this.rd.drawString(var6_39, 350 - this.ftm.stringWidth(var6_39) / 2, 280 + var4_16);
                    if (this.mouseon == 3) {
                        this.rd.setColor(new Color(0, 128, 255));
                    } else {
                        this.rd.setColor(new Color(0, 64, 128));
                    }
                    this.rd.drawLine(350 - this.ftm.stringWidth(var6_39) / 2, 281 + var4_16, 350 + this.ftm.stringWidth(var6_39) / 2, 281 + var4_16);
                    if (this.mouseon == 3 && this.mouses == -1) {
                        this.openelink();
                    }
                    this.stringbutton("     Import Car     ", 350, 326 + var4_16, 0, true);
                    this.stringbutton("  Export >  ", 550, 326 + var4_16, 0, false);
                    this.stringbutton("  Cancel  ", 350, 366 + var4_16, 0, false);
                }
                if (this.sfase == 4) {
                    this.rd.drawString("Select Car to Export", 350 - this.ftm.stringWidth("Select Car to Export") / 2, 230 + var4_16);
                    this.slcar.move(250, 240 + var4_16);
                    if (this.slcar.getWidth() != 200) {
                        this.slcar.setSize(200, 21);
                    }
                    if (!this.slcar.isShowing()) {
                        this.slcar.show();
                    }
                    this.stringbutton("     Export Car     ", 350, 306 + var4_16, 0, true);
                    this.stringbutton("  Cancel  ", 350, 346 + var4_16, 0, false);
                    if (this.slcar.getSelectedIndex() != 0) {
                        if (!this.carname.equals(this.slcar.getSelectedItem())) {
                            this.tomany = false;
                            this.carname = this.slcar.getSelectedItem();
                            this.loadfile();
                            this.editor.select(0, 0);
                            this.tested = false;
                            this.requestFocus();
                        }
                    } else {
                        this.carname = "";
                    }
                }
            }
            if (this.tab == 1) {
                if (this.tabed != this.tab) {
                    this.srch.setText("");
                    this.rplc.setText("");
                    this.cntchk = 1;
                    this.npolys = 0;
                    this.prefs = false;
                }
                this.movefield(this.editor, 5, 30, 690, 400);
                if (!this.openm) {
                    if (!this.editor.isShowing()) {
                        this.editor.show();
                        this.editor.requestFocus();
                    }
                } else if (this.editor.isShowing()) {
                    this.editor.hide();
                }
                this.rd.setFont(new Font("Arial", 1, 12));
                if (this.prefs) {
                    this.rd.drawString("Code Font:", 10, 446);
                    this.fontsel.move(76, 430);
                    if (!this.fontsel.isShowing()) {
                        this.fontsel.show();
                        this.fontsel.select(this.cfont);
                    }
                    if (!this.cfont.equals(this.fontsel.getSelectedItem())) {
                        this.cntprf = 0;
                        this.cfont = this.fontsel.getSelectedItem();
                        this.editor.setFont(new Font(this.cfont, 1, 14));
                        this.srch.setFont(new Font(this.cfont, 1, 14));
                        this.rplc.setFont(new Font(this.cfont, 1, 14));
                        for (var3_6 = 0; var3_6 < 16; ++var3_6) {
                            this.wv[var3_6].setFont(new Font(this.cfont, 1, 14));
                        }
                        this.editor.requestFocus();
                    }
                    this.rd.drawString("Code Theme:", 190, 446);
                    this.ctheme.move(271, 430);
                    if (!this.ctheme.isShowing()) {
                        this.ctheme.show();
                        this.ctheme.select(this.cthm);
                    }
                    if (this.cthm != this.ctheme.getSelectedIndex()) {
                        this.cntprf = 0;
                        this.cthm = this.ctheme.getSelectedIndex();
                        this.setheme();
                        this.editor.requestFocus();
                    }
                    this.stringbutton("<", 400, 446, 3, false);
                    ++this.cntprf;
                    if (this.cntprf == 200) {
                        this.prefs = false;
                    }
                } else {
                    this.stringbutton("Preferences", 52, 446, 3, false);
                    if (this.ctheme.isShowing()) {
                        this.ctheme.hide();
                    }
                    if (this.fontsel.isShowing()) {
                        this.fontsel.hide();
                    }
                    if (this.cntprf != 0) {
                        this.cntprf = 0;
                    }
                    if (this.cntchk == 0) {
                        this.npolys = 0;
                        var3_7 = 0;
                        var4_16 = 0;
                        while (var3_7 != -1 && this.mouses != 1) {
                            var3_7 = var4_16 == 0 ? this.editor.getText().indexOf("<p>", var3_7) : this.editor.getText().indexOf("</p>", var3_7);
                            if (var3_7 == -1) continue;
                            if (var4_16 == 0) {
                                var4_16 = 1;
                            } else {
                                var4_16 = 0;
                                ++this.npolys;
                            }
                            var3_7 += 3;
                        }
                        if (this.mouses == 1) {
                            this.npolys = 0;
                        }
                        this.cntchk = 30;
                    } else {
                        --this.cntchk;
                    }
                    if (this.npolys > 210) {
                        this.rd.setColor(new Color(255, 0, 0));
                    }
                    if (this.npolys != 0) {
                        this.rd.drawString("Number of Polygons :  " + this.npolys + " / 210", 200, 446);
                    }
                }
                if (!this.changed && !this.editor.getText().equals(this.lastedo)) {
                    this.changed = true;
                }
                this.stringbutton("  Save  ", 490, 455, 0, this.changed);
                this.stringbutton("  Save & Preview  >  ", 600, 455, 0, this.changed);
                this.mirror = false;
                this.polynum = -1;
                this.cntpls = 0;
                var3_8 = "";
                try {
                    var3_8 = this.editor.getSelectedText();
                }
                catch (Exception var4_17) {
                    // empty catch block
                }
                if (!var3_8.equals("")) {
                    var4_18 = var3_8.indexOf("<p>", 0);
                    while (var4_18 != -1 && var4_18 + 1 < var3_8.length()) {
                        if (!this.mirror && (var4_18 = var3_8.indexOf("</p>", var4_18 + 1)) != -1) {
                            this.mirror = true;
                            ++this.cntpls;
                        }
                        if (!this.mirror || (var4_18 = var3_8.indexOf("<p>", var4_18 + 1)) == -1) continue;
                        this.mirror = false;
                    }
                }
                if (!this.mirror) {
                    this.rd.setColor(new Color(170, 170, 170));
                    this.rd.drawRect(5, 474, 494, 70);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Text to find:", 18, 500);
                    this.movefield(this.srch, 91, 484, 129, 22);
                    if (!this.srch.isShowing()) {
                        this.srch.show();
                    }
                    var4_19 = false;
                    if (!this.srch.getText().equals("")) {
                        var4_19 = true;
                    }
                    this.stringbutton(" Find ", 117, 526, 2, var4_19);
                    this.rd.drawString("Replace with:", 255, 500);
                    this.movefield(this.rplc, 338, 484, 129, 22);
                    if (!this.rplc.isShowing()) {
                        this.rplc.show();
                    }
                    var4_19 = false;
                    if (!this.srch.getText().equals("") && !this.rplc.getText().equals("")) {
                        var4_19 = true;
                    }
                    this.stringbutton(" Replace ", 376, 526, 2, var4_19);
                } else {
                    if (this.srch.isShowing()) {
                        this.srch.hide();
                    }
                    if (this.rplc.isShowing()) {
                        this.rplc.hide();
                    }
                    this.rd.setColor(new Color(170, 170, 170));
                    this.rd.drawRect(5, 474, 450, 70);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Mirror [Selected] polygon(s) along:", 18, 490);
                    this.stringbutton(" Mirro Along X Axis ", 90, 525, 2, true);
                    this.stringbutton(" Mirro Along Y Axis ", 230, 525, 2, false);
                    this.stringbutton(" Mirro Along Z Axis ", 370, 525, 2, false);
                    this.rd.setColor(new Color(170, 170, 170));
                    this.rd.drawRect(465, 474, 230, 70);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Show [Selected] polygon(s):", 478, 490);
                    this.stringbutton(" Show in 3D  > ", 580, 523, 0, true);
                }
                if (this.npolys > 210 && !this.tomany) {
                    this.repaint();
                    JOptionPane.showMessageDialog(null, "Maximum number of polygons (pieces) allowed has been exceeded!\nThe maximum allowed is 210 polygons, please decrease.\n", "Car Maker", 1);
                    this.tomany = true;
                }
            }
            if (this.tab == 2) {
                if (this.tabed != this.tab) {
                    this.setupo();
                    this.dtabed = -1;
                }
                this.m.d(this.rd);
                this.o.d(this.rd);
                if (this.dtab == 2) {
                    if (this.compsel > 0 && this.compsel <= 16) {
                        this.compo[this.compsel - 1].x = this.o.x;
                        this.compo[this.compsel - 1].y = this.o.y;
                        this.compo[this.compsel - 1].z = this.o.z;
                        this.compo[this.compsel - 1].xz = this.o.xz;
                        this.compo[this.compsel - 1].xy = this.o.xy;
                        this.compo[this.compsel - 1].zy = this.o.zy;
                        this.rd.setComposite(AlphaComposite.getInstance(3, 0.4f));
                        this.compo[this.compsel - 1].d(this.rd);
                        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                    }
                    if (this.xm > 420 && this.xm < 690 && this.ym > 425 && this.ym < 540) {
                        var3_9 = new int[]{50 + this.adna[0], -50 - this.adna[1], 0, 0, 0, 0};
                        var4_20 = new int[]{0, 0, 50 + this.adna[2], -50 - this.adna[3], 0, 0};
                        var5_30 = new int[]{0, 0, 0, 0, 50 + this.adna[4], -50 - this.adna[5]};
                        var6_40 = 0;
                        while (var6_40 < 6) {
                            v0 = var6_40;
                            var3_9[v0] = var3_9[v0] + (this.o.x - this.m.x);
                            v1 = var6_40;
                            var4_20[v1] = var4_20[v1] + (this.o.y - this.m.y);
                            v2 = var6_40++;
                            var5_30[v2] = var5_30[v2] + (this.o.z - this.m.z);
                        }
                        this.rot(var3_9, var4_20, this.o.x - this.m.x, this.o.y - this.m.y, this.o.xy, 6);
                        this.rot(var4_20, var5_30, this.o.y - this.m.y, this.o.z - this.m.z, this.o.zy, 6);
                        this.rot(var3_9, var5_30, this.o.x - this.m.x, this.o.z - this.m.z, this.o.xz, 6);
                        this.rot(var3_9, var5_30, this.m.cx, this.m.cz, this.m.xz, 6);
                        this.rot(var4_20, var5_30, this.m.cy, this.m.cz, this.m.zy, 6);
                        var6_41 = new int[6];
                        var7_62 = new int[6];
                        for (var8_93 = 0; var8_93 < 6; ++var8_93) {
                            var6_41[var8_93] = this.xs(var3_9[var8_93], var5_30[var8_93]);
                            var7_62[var8_93] = this.ys(var4_20[var8_93], var5_30[var8_93]);
                        }
                        this.rd.setColor(new Color(0, 150, 0));
                        this.rd.drawString("X+", var6_41[0] - 7, var7_62[0] + 4);
                        this.rd.drawString("-X", var6_41[1] - 5, var7_62[1] + 4);
                        this.rd.drawLine(var6_41[0], var7_62[0], var6_41[1], var7_62[1]);
                        this.rd.setColor(new Color(150, 0, 0));
                        this.rd.drawString("Y+", var6_41[2] - 7, var7_62[2] + 4);
                        this.rd.drawString("-Y", var6_41[3] - 5, var7_62[3] + 4);
                        this.rd.drawLine(var6_41[2], var7_62[2], var6_41[3], var7_62[3]);
                        this.rd.setColor(new Color(0, 0, 150));
                        this.rd.drawString("Z+", var6_41[4] - 7, var7_62[4] + 4);
                        this.rd.drawString("-Z", var6_41[5] - 5, var7_62[5] + 4);
                        this.rd.drawLine(var6_41[4], var7_62[4], var6_41[5], var7_62[5]);
                        for (var8_93 = 0; var8_93 < 6; ++var8_93) {
                            if ((float)Math.abs(var7_62[var8_93] - 207) * 1.91f > (float)Math.abs(var6_41[var8_93] - 350)) {
                                if (Math.abs(Math.abs(var7_62[var8_93] - 207) - 170) > 10) {
                                    if (Math.abs(var7_62[var8_93] - 207) < 170) {
                                        v3 = var8_93;
                                        this.adna[v3] = this.adna[v3] + 10;
                                    } else {
                                        v4 = var8_93;
                                        this.adna[v4] = this.adna[v4] - 10;
                                    }
                                }
                            } else if (Math.abs(Math.abs(var6_41[var8_93] - 350) - 338) > 10) {
                                if (Math.abs(var6_41[var8_93] - 350) < 338) {
                                    v5 = var8_93;
                                    this.adna[v5] = this.adna[v5] + 10;
                                } else {
                                    v6 = var8_93;
                                    this.adna[v6] = this.adna[v6] - 10;
                                }
                            }
                            if (this.adna[var8_93] > 276) {
                                this.adna[var8_93] = 276;
                            }
                            if (this.adna[var8_93] >= 0) continue;
                            this.adna[var8_93] = 0;
                        }
                    }
                }
                this.rd.setColor(new Color(205, 200, 200));
                this.rd.fillRect(0, 390, 700, 20);
                this.rd.setColor(new Color(225, 225, 225));
                this.rd.fillRect(0, 410, 700, 140);
                this.rd.setFont(new Font("Arial", 1, 12));
                this.ftm = this.rd.getFontMetrics();
                var3_10 = new String[]{"3D Controls", "Color Edit", "Scale & Align", "Wheels", "Stats & Class", "Physics", "Test Drive"};
                var4_21 = new int[]{0, 0, 100, 90};
                var5_31 = new int[]{390, 410, 410, 390};
                for (var6_42 = 0; var6_42 < 7; ++var6_42) {
                    this.rd.setColor(new Color(170, 170, 170));
                    if (this.xm > var4_21[0] && this.xm < var4_21[3] && this.ym > 390 && this.ym < 410) {
                        this.rd.setColor(new Color(190, 190, 190));
                    }
                    if (this.dtab == var6_42) {
                        this.rd.setColor(new Color(225, 225, 225));
                    }
                    this.rd.fillPolygon(var4_21, var5_31, 4);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString(var3_10[var6_42], var6_42 * 100 + 47 - this.ftm.stringWidth(var3_10[var6_42]) / 2, 404);
                    if (this.xm > var4_21[0] && this.xm < var4_21[3] && this.ym > 390 && this.ym < 410 && this.mouses == -1) {
                        this.dtab = var6_42;
                    }
                    var7_63 = 0;
                    while (var7_63 < 4) {
                        v7 = var7_63++;
                        var4_21[v7] = var4_21[v7] + 100;
                    }
                }
                if (this.dtabed != this.dtab) {
                    if (this.dtabed != -1) {
                        if (!this.editor.getText().equals(this.lastedo)) {
                            this.editor.setText(this.lastedo);
                        }
                        this.setupo();
                    }
                    this.setCursor(new Cursor(0));
                    this.hidefields();
                    this.requestFocus();
                }
                this.rd.setColor(new Color(0, 0, 0));
                if (this.dtab == 0) {
                    this.rd.drawString("Rotate Car around its X & Z Axis using:  [ Keyboard Arrows ] ", 20, 440);
                    this.rd.drawString("Rotate Car fully around the Y Axis using:    [ < ]  &  [ > ]    or    [ A ]  &  [ D ]    or    [ 4 ]  &  [ 6 ]    Keys", 20, 465);
                    this.rd.drawString("Move Car Up and Down using:    [ - ]  &  [ + ]    Keys", 20, 490);
                    this.rd.drawString("Move Car Forwards and Backwards using:    [ W ]  &  [ S ]    or    [ 8 ]  &  [ 2 ]    or    [ Enter ]  &  [ Backspace ]    Keys", 20, 515);
                }
                if (this.dtab == 1) {
                    if (this.o.colok != 2) {
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("[  First & Second Color not defined yet  ]", 350 - this.ftm.stringWidth("[  First & Second Color not defined yet  ]") / 2, 450);
                        this.stringbutton(" Define 1st and 2nd Color ", 350, 490, 0, true);
                    } else {
                        if (this.dtabed != this.dtab) {
                            this.fcol = "(" + this.o.fcol[0] + "," + this.o.fcol[1] + "," + this.o.fcol[2] + ")";
                            this.srch.setText(this.fcol);
                            this.ofcol = this.fcol;
                            Color.RGBtoHSB(this.o.fcol[0], this.o.fcol[1], this.o.fcol[2], this.fhsb);
                            var6_43 = this.fhsb[1];
                            this.fhsb[1] = this.fhsb[2];
                            this.fhsb[2] = var6_43;
                            this.scol = "(" + this.o.scol[0] + "," + this.o.scol[1] + "," + this.o.scol[2] + ")";
                            this.rplc.setText(this.scol);
                            this.oscol = this.scol;
                            Color.RGBtoHSB(this.o.scol[0], this.o.scol[1], this.o.scol[2], this.shsb);
                            var6_43 = this.shsb[1];
                            this.shsb[1] = this.shsb[2];
                            this.shsb[2] = var6_43;
                            this.bfo = 51;
                            this.mouseon = -1;
                        }
                        if (this.mouses != 1) {
                            this.mouseon = -1;
                        }
                        this.rd.setColor(new Color(170, 170, 170));
                        this.rd.drawRect(20, 425, 320, 110);
                        this.rd.setColor(new Color(225, 225, 225));
                        this.rd.fillRect(141, 419, 77, 9);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("First Color", 151, 428);
                        this.rd.drawString("Hue:", 75, 450);
                        this.rd.drawString("Brightness:", 35, 470);
                        this.rd.drawString("Saturation:", 38, 490);
                        this.rd.drawString("RGB Value:", 38, 520);
                        this.movefield(this.srch, 106, 504, 129, 22);
                        if (this.srch.hasFocus()) {
                            this.focuson = false;
                        }
                        if (!this.srch.isShowing()) {
                            this.srch.show();
                        }
                        for (var6_44 = 0; var6_44 < 200; ++var6_44) {
                            this.rd.setColor(Color.getHSBColor((float)((double)var6_44 * 0.005), 1.0f, 1.0f));
                            this.rd.drawLine(110 + var6_44, 442, 110 + var6_44, 449);
                        }
                        for (var6_44 = 0; var6_44 < 200; ++var6_44) {
                            this.rd.setColor(Color.getHSBColor(0.0f, 0.0f, 0.2f + (float)((double)var6_44 * 0.004)));
                            this.rd.drawLine(110 + var6_44, 462, 110 + var6_44, 469);
                        }
                        for (var6_44 = 0; var6_44 < 200; ++var6_44) {
                            this.rd.setColor(Color.getHSBColor(this.fhsb[0], (float)((double)var6_44 * 0.005), this.fhsb[1]));
                            this.rd.drawLine(110 + var6_44, 482, 110 + var6_44, 489);
                        }
                        for (var6_44 = 0; var6_44 < 3; ++var6_44) {
                            this.rd.setColor(new Color(0, 0, 0));
                            var7_64 = this.fhsb[var6_44] * 200.0f;
                            if (var6_44 == 1) {
                                var7_64 = (this.fhsb[var6_44] - 0.2f) * 250.0f;
                            }
                            this.rd.drawLine((int)(110.0f + var7_64), 442 + var6_44 * 20, (int)(110.0f + var7_64), 449 + var6_44 * 20);
                            this.rd.drawLine((int)(111.0f + var7_64), 442 + var6_44 * 20, (int)(111.0f + var7_64), 449 + var6_44 * 20);
                            this.rd.fillRect((int)(109.0f + var7_64), 450 + var6_44 * 20, 4, 2);
                            this.rd.drawLine((int)(108.0f + var7_64), 452 + var6_44 * 20, (int)(113.0f + var7_64), 452 + var6_44 * 20);
                            if (this.xm > 107 && this.xm < 313 && this.ym > 439 + var6_44 * 20 && this.ym < 452 + var6_44 * 20 && this.mouses == 1 && this.mouseon == -1) {
                                this.mouseon = var6_44;
                            }
                            if (this.mouseon != var6_44) continue;
                            if (var6_44 == 1) {
                                this.fhsb[var6_44] = 0.2f + (float)(this.xm - 110) / 250.0f;
                                if ((double)this.fhsb[var6_44] < 0.2) {
                                    this.fhsb[var6_44] = 0.2f;
                                }
                            } else {
                                this.fhsb[var6_44] = (float)(this.xm - 110) / 200.0f;
                            }
                            if (this.fhsb[var6_44] > 1.0f) {
                                this.fhsb[var6_44] = 1.0f;
                            }
                            if (!(this.fhsb[var6_44] < 0.0f)) continue;
                            this.fhsb[var6_44] = 0.0f;
                        }
                        this.stringbutton(" Save ", 300, 520, 0, this.fcol.equals(this.ofcol) == false);
                        this.rd.setColor(new Color(170, 170, 170));
                        this.rd.drawRect(360, 425, 320, 110);
                        this.rd.setColor(new Color(225, 225, 225));
                        this.rd.fillRect(472, 419, 95, 9);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Second Color", 482, 428);
                        this.rd.drawString("Hue:", 415, 450);
                        this.rd.drawString("Brightness:", 375, 470);
                        this.rd.drawString("Saturation:", 378, 490);
                        this.rd.drawString("RGB Value:", 378, 520);
                        this.movefield(this.rplc, 446, 504, 129, 22);
                        if (this.rplc.hasFocus()) {
                            this.focuson = false;
                        }
                        if (!this.rplc.isShowing()) {
                            this.rplc.show();
                        }
                        for (var6_44 = 0; var6_44 < 200; ++var6_44) {
                            this.rd.setColor(Color.getHSBColor((float)((double)var6_44 * 0.005), 1.0f, 1.0f));
                            this.rd.drawLine(450 + var6_44, 442, 450 + var6_44, 449);
                        }
                        for (var6_44 = 0; var6_44 < 200; ++var6_44) {
                            this.rd.setColor(Color.getHSBColor(0.0f, 0.0f, 0.2f + (float)((double)var6_44 * 0.004)));
                            this.rd.drawLine(450 + var6_44, 462, 450 + var6_44, 469);
                        }
                        for (var6_44 = 0; var6_44 < 200; ++var6_44) {
                            this.rd.setColor(Color.getHSBColor(this.shsb[0], (float)((double)var6_44 * 0.005), this.shsb[2]));
                            this.rd.drawLine(450 + var6_44, 482, 450 + var6_44, 489);
                        }
                        for (var6_44 = 0; var6_44 < 3; ++var6_44) {
                            this.rd.setColor(new Color(0, 0, 0));
                            var7_65 = this.shsb[var6_44] * 200.0f;
                            if (var6_44 == 1) {
                                var7_65 = (this.shsb[var6_44] - 0.2f) * 250.0f;
                            }
                            this.rd.drawLine((int)(450.0f + var7_65), 442 + var6_44 * 20, (int)(450.0f + var7_65), 449 + var6_44 * 20);
                            this.rd.drawLine((int)(451.0f + var7_65), 442 + var6_44 * 20, (int)(451.0f + var7_65), 449 + var6_44 * 20);
                            this.rd.fillRect((int)(449.0f + var7_65), 450 + var6_44 * 20, 4, 2);
                            this.rd.drawLine((int)(448.0f + var7_65), 452 + var6_44 * 20, (int)(453.0f + var7_65), 452 + var6_44 * 20);
                            if (this.xm > 447 && this.xm < 653 && this.ym > 439 + var6_44 * 20 && this.ym < 452 + var6_44 * 20 && this.mouses == 1 && this.mouseon == -1) {
                                this.mouseon = var6_44 + 3;
                            }
                            if (this.mouseon != var6_44 + 3) continue;
                            if (var6_44 == 1) {
                                this.shsb[var6_44] = 0.2f + (float)(this.xm - 450) / 250.0f;
                                if ((double)this.shsb[var6_44] < 0.2) {
                                    this.shsb[var6_44] = 0.2f;
                                }
                            } else {
                                this.shsb[var6_44] = (float)(this.xm - 450) / 200.0f;
                            }
                            if (this.shsb[var6_44] > 1.0f) {
                                this.shsb[var6_44] = 1.0f;
                            }
                            if (!(this.shsb[var6_44] < 0.0f)) continue;
                            this.shsb[var6_44] = 0.0f;
                        }
                        this.stringbutton(" Save ", 640, 520, 0, this.scol.equals(this.oscol) == false);
                        if ((double)this.fhsb[1] < 0.2) {
                            this.fhsb[1] = 0.2f;
                        }
                        if ((double)this.shsb[1] < 0.2) {
                            this.shsb[1] = 0.2f;
                        }
                        for (var6_44 = 0; var6_44 < this.o.npl; ++var6_44) {
                            if (this.o.p[var6_44].colnum == 1) {
                                this.o.p[var6_44].hsb[0] = this.fhsb[0];
                                this.o.p[var6_44].hsb[1] = this.fhsb[2];
                                this.o.p[var6_44].hsb[2] = this.fhsb[1];
                            }
                            if (this.o.p[var6_44].colnum != 2) continue;
                            this.o.p[var6_44].hsb[0] = this.shsb[0];
                            this.o.p[var6_44].hsb[1] = this.shsb[2];
                            this.o.p[var6_44].hsb[2] = this.shsb[1];
                        }
                        var6_45 = "(" + Color.getHSBColor(this.fhsb[0], this.fhsb[2], this.fhsb[1]).getRed() + "," + Color.getHSBColor(this.fhsb[0], this.fhsb[2], this.fhsb[1]).getGreen() + "," + Color.getHSBColor(this.fhsb[0], this.fhsb[2], this.fhsb[1]).getBlue() + ")";
                        if (!this.fcol.equals(var6_45)) {
                            this.fcol = var6_45;
                            this.srch.setText(this.fcol);
                        }
                        if (!this.scol.equals(var6_45 = "(" + Color.getHSBColor(this.shsb[0], this.shsb[2], this.shsb[1]).getRed() + "," + Color.getHSBColor(this.shsb[0], this.shsb[2], this.shsb[1]).getGreen() + "," + Color.getHSBColor(this.shsb[0], this.shsb[2], this.shsb[1]).getBlue() + ")")) {
                            this.scol = var6_45;
                            this.rplc.setText(this.scol);
                        }
                        if (this.srch.getText().equals(this.fcol) && this.rplc.getText().equals(this.scol)) {
                            if (this.bfo < 50) {
                                ++this.bfo;
                            } else if (this.bfo == 50) {
                                this.requestFocus();
                                this.bfo = 51;
                            }
                        } else {
                            this.bfo = 0;
                            if (!this.srch.getText().equals(this.fcol)) {
                                this.fcol = this.srch.getText();
                                var7_66 = new int[3];
                                var8_93 = 1;
                                try {
                                    var9_101 = this.fcol.indexOf(",", 0);
                                    var10_110 = this.fcol.indexOf(",", var9_101 + 1);
                                    var7_66[0] = Integer.valueOf(this.fcol.substring(1, var9_101));
                                    if (var7_66[0] < 0) {
                                        var7_66[0] = 0;
                                    }
                                    if (var7_66[0] > 255) {
                                        var7_66[0] = 255;
                                    }
                                    var7_66[1] = Integer.valueOf(this.fcol.substring(var9_101 + 1, var10_110));
                                    if (var7_66[1] < 0) {
                                        var7_66[1] = 0;
                                    }
                                    if (var7_66[1] > 255) {
                                        var7_66[1] = 255;
                                    }
                                    var7_66[2] = Integer.valueOf(this.fcol.substring(var10_110 + 1, this.fcol.length() - 1));
                                    if (var7_66[2] < 0) {
                                        var7_66[2] = 0;
                                    }
                                    if (var7_66[2] > 255) {
                                        var7_66[2] = 255;
                                    }
                                }
                                catch (Exception var9_102) {
                                    var8_93 = 0;
                                }
                                if (var8_93 != 0) {
                                    Color.RGBtoHSB(var7_66[0], var7_66[1], var7_66[2], this.fhsb);
                                    var9_103 = this.fhsb[1];
                                    this.fhsb[1] = this.fhsb[2];
                                    this.fhsb[2] = var9_103;
                                }
                            }
                            if (!this.rplc.getText().equals(this.scol)) {
                                this.scol = this.rplc.getText();
                                var7_67 = new int[3];
                                var8_93 = 1;
                                try {
                                    var9_101 = this.scol.indexOf(",", 0);
                                    var10_110 = this.scol.indexOf(",", var9_101 + 1);
                                    var7_67[0] = Integer.valueOf(this.scol.substring(1, var9_101));
                                    if (var7_67[0] < 0) {
                                        var7_67[0] = 0;
                                    }
                                    if (var7_67[0] > 255) {
                                        var7_67[0] = 255;
                                    }
                                    var7_67[1] = Integer.valueOf(this.scol.substring(var9_101 + 1, var10_110));
                                    if (var7_67[1] < 0) {
                                        var7_67[1] = 0;
                                    }
                                    if (var7_67[1] > 255) {
                                        var7_67[1] = 255;
                                    }
                                    var7_67[2] = Integer.valueOf(this.scol.substring(var10_110 + 1, this.scol.length() - 1));
                                    if (var7_67[2] < 0) {
                                        var7_67[2] = 0;
                                    }
                                    if (var7_67[2] > 255) {
                                        var7_67[2] = 255;
                                    }
                                }
                                catch (Exception var9_104) {
                                    var8_93 = 0;
                                }
                                if (var8_93 != 0) {
                                    Color.RGBtoHSB(var7_67[0], var7_67[1], var7_67[2], this.shsb);
                                    var9_105 = this.shsb[1];
                                    this.shsb[1] = this.shsb[2];
                                    this.shsb[2] = var9_105;
                                }
                            }
                        }
                    }
                }
                if (this.dtab == 2) {
                    if (this.dtabed != this.dtab) {
                        this.lastedo = this.editor.getText();
                        this.scale[0] = 100;
                        var6_46 = this.editor.getText().indexOf("\nScaleX(", 0);
                        if (var6_46 != -1) {
                            ++var6_46;
                            try {
                                this.scale[0] = Integer.valueOf(this.editor.getText().substring(var6_46 + 7, this.editor.getText().indexOf(")", var6_46)));
                            }
                            catch (Exception var7_68) {
                                this.scale[0] = 100;
                            }
                        }
                        this.oscale[0] = this.scale[0];
                        this.scale[1] = 100;
                        var6_46 = this.editor.getText().indexOf("\nScaleY(", 0);
                        if (var6_46 != -1) {
                            ++var6_46;
                            try {
                                this.scale[1] = Integer.valueOf(this.editor.getText().substring(var6_46 + 7, this.editor.getText().indexOf(")", var6_46)));
                            }
                            catch (Exception var7_69) {
                                this.scale[1] = 100;
                            }
                        }
                        this.oscale[1] = this.scale[1];
                        this.scale[2] = 100;
                        var6_46 = this.editor.getText().indexOf("\nScaleZ(", 0);
                        if (var6_46 != -1) {
                            ++var6_46;
                            try {
                                this.scale[2] = Integer.valueOf(this.editor.getText().substring(var6_46 + 7, this.editor.getText().indexOf(")", var6_46)));
                            }
                            catch (Exception var7_70) {
                                this.scale[2] = 100;
                            }
                        }
                        this.oscale[2] = this.scale[2];
                        this.bfo = 0;
                        this.compsel = 0;
                        this.compcar.select(this.compsel);
                        this.changed2 = false;
                    }
                    this.rd.setColor(new Color(170, 170, 170));
                    this.rd.drawRect(9, 425, 270, 115);
                    this.rd.setColor(new Color(225, 225, 225));
                    this.rd.fillRect(119, 419, 51, 9);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Scale", 129, 428);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Scale X", 25, 450);
                    this.stringbutton(" - ", 92, 450, 4, false);
                    this.rd.drawString("" + (float)this.scale[0] / 100.0f + "", 126 - this.ftm.stringWidth("" + (float)this.scale[0] / 100.0f + "") / 2, 450);
                    this.stringbutton(" + ", 160, 450, 4, false);
                    this.rd.drawString("Scale Y", 25, 474);
                    this.stringbutton(" - ", 92, 474, 4, false);
                    this.rd.drawString("" + (float)this.scale[1] / 100.0f + "", 126 - this.ftm.stringWidth("" + (float)this.scale[1] / 100.0f + "") / 2, 474);
                    this.stringbutton(" + ", 160, 474, 4, false);
                    this.rd.drawString("Scale Z", 25, 498);
                    this.stringbutton(" - ", 92, 498, 4, false);
                    this.rd.drawString("" + (float)this.scale[2] / 100.0f + "", 126 - this.ftm.stringWidth("" + (float)this.scale[2] / 100.0f + "") / 2, 498);
                    this.stringbutton(" + ", 160, 498, 4, false);
                    this.rd.drawString("Scale ALL", 25, 527);
                    this.stringbutton(" - ", 106, 527, 2, true);
                    this.stringbutton(" + ", 146, 527, 2, true);
                    this.stringbutton("   Save   ", 230, 454, 0, this.oscale[0] != this.scale[0] || this.oscale[1] != this.scale[1] || this.oscale[2] != this.scale[2]);
                    this.stringbutton(" Reset ", 230, 493, 0, false);
                    this.rd.drawString("Compare scale and", 296, 440);
                    this.rd.drawString("alignment with:", 308, 455);
                    this.compcar.move(288, 462);
                    if (this.compcar.hasFocus()) {
                        this.focuson = false;
                        ++this.bfo;
                        if (this.bfo == 100) {
                            this.requestFocus();
                        }
                    } else {
                        this.bfo = 0;
                    }
                    if (!this.compcar.isShowing()) {
                        this.compcar.show();
                    }
                    if (this.compsel != this.compcar.getSelectedIndex()) {
                        this.compsel = this.compcar.getSelectedIndex();
                        this.requestFocus();
                    }
                    this.rd.setColor(new Color(170, 170, 170));
                    this.rd.drawRect(420, 425, 270, 115);
                    this.rd.setColor(new Color(225, 225, 225));
                    this.rd.fillRect(531, 419, 47, 9);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Align", 541, 428);
                    this.rd.drawString("Align in X", 433, 450);
                    this.stringbutton(" Rotate 90\u00b0 ", 535, 450, 4, false);
                    this.stringbutton("+10", 607, 450, 4, false);
                    this.stringbutton("-10", 656, 450, 4, false);
                    this.rd.drawString("Align in Y", 433, 474);
                    this.stringbutton(" Rotate 90\u00b0 ", 535, 474, 4, false);
                    this.stringbutton("+10", 607, 474, 4, false);
                    this.stringbutton("-10", 656, 474, 4, false);
                    this.rd.drawString("Align in Z", 433, 498);
                    this.stringbutton(" Rotate 90\u00b0 ", 535, 498, 4, false);
                    this.stringbutton("+10", 607, 498, 4, false);
                    this.stringbutton("-10", 656, 498, 4, false);
                    this.stringbutton(" Reset ", 490, 527, 0, false);
                    this.stringbutton("      Save      ", 607, 527, 0, this.changed2);
                }
                if (this.dtab == 3) {
                    if (this.dtabed != this.dtab) {
                        var6_47 = 45;
                        var7_71 = 45;
                        var8_93 = 15;
                        var9_101 = 15;
                        var10_110 = 76;
                        var11_117 = -76;
                        var12_124 = 26;
                        var13_126 = 26;
                        var14_129 = 20;
                        var15_130 = 20;
                        var16_132 = 18;
                        var17_133 = 18;
                        var18_134 = 10;
                        var19_136 = 10;
                        var20_138 = 0;
                        var21_139 = 0;
                        var22_140 = "(140,140,140)";
                        var23_141 = "(140,140,140)";
                        var24_142 = 0;
                        var25_143 = "" + this.editor.getText() + "\n";
                        var26_144 = 0;
                        var27_145 = var25_143.indexOf("\n", 0);
                        var28_146 = 0;
                        var29_147 = 15;
                        var30_148 = 20;
                        var31_149 = "(140,140,140)";
                        while (var27_145 != -1 && var26_144 < var25_143.length()) {
                            var32_150 = var25_143.substring(var26_144, var27_145).trim();
                            var26_144 = var27_145 + 1;
                            var27_145 = var25_143.indexOf("\n", var26_144);
                            try {
                                if (var32_150.startsWith("rims(")) {
                                    var31_149 = "(" + this.getvalue("rims", var32_150, 0) + "," + this.getvalue("rims", var32_150, 1) + "," + this.getvalue("rims", var32_150, 2) + ")";
                                    var29_147 = this.getvalue("rims", var32_150, 3);
                                    var30_148 = this.getvalue("rims", var32_150, 4);
                                }
                                if (var32_150.startsWith("gwgr(")) {
                                    var28_146 = this.getvalue("gwgr", var32_150, 0);
                                }
                                if (!var32_150.startsWith("w(")) continue;
                                var33_151 = this.getvalue("w", var32_150, 2);
                                if (var33_151 > 0) {
                                    var6_47 = Math.abs(this.getvalue("w", var32_150, 0));
                                    var8_93 = this.getvalue("w", var32_150, 1);
                                    var10_110 = var33_151;
                                    var12_124 = Math.abs(this.getvalue("w", var32_150, 4));
                                    var14_129 = Math.abs(this.getvalue("w", var32_150, 5));
                                    var22_140 = var31_149;
                                    var16_132 = var29_147;
                                    var18_134 = var30_148;
                                    var20_138 = var28_146;
                                } else {
                                    var7_71 = Math.abs(this.getvalue("w", var32_150, 0));
                                    var9_101 = this.getvalue("w", var32_150, 1);
                                    var11_117 = var33_151;
                                    var13_126 = Math.abs(this.getvalue("w", var32_150, 4));
                                    var15_130 = Math.abs(this.getvalue("w", var32_150, 5));
                                    var23_141 = var31_149;
                                    var17_133 = var29_147;
                                    var19_136 = var30_148;
                                    var21_139 = var28_146;
                                }
                                ++var24_142;
                            }
                            catch (Exception var33_152) {}
                        }
                        this.defnow = var24_142 != 4;
                        this.wv[0].setText("" + var7_71 + "");
                        this.wv[1].setText("" + var9_101 + "");
                        this.wv[2].setText("" + var11_117 + "");
                        this.wv[3].setText("" + var15_130 + "");
                        this.wv[4].setText("" + var13_126 + "");
                        this.srch.setText((String)var23_141);
                        this.wv[5].setText("" + var17_133 + "");
                        this.wv[6].setText("" + var19_136 + "");
                        this.wv[7].setText("" + var21_139 + "");
                        this.wv[8].setText("" + var6_47 + "");
                        this.wv[9].setText("" + var8_93 + "");
                        this.wv[10].setText("" + var10_110 + "");
                        this.wv[11].setText("" + var14_129 + "");
                        this.wv[12].setText("" + var12_124 + "");
                        this.rplc.setText((String)var22_140);
                        this.wv[13].setText("" + var16_132 + "");
                        this.wv[14].setText("" + var18_134 + "");
                        this.wv[15].setText("" + var20_138 + "");
                        this.aply1 = "" + this.wv[0].getText() + "" + this.wv[1].getText() + "" + this.wv[2].getText() + "" + this.wv[3].getText() + "" + this.wv[4].getText() + "" + this.srch.getText() + "" + this.wv[5].getText() + "" + this.wv[6].getText() + "" + this.wv[7].getText() + "";
                        this.aply2 = "" + this.wv[8].getText() + "" + this.wv[9].getText() + "" + this.wv[10].getText() + "" + this.wv[11].getText() + "" + this.wv[12].getText() + "" + this.rplc.getText() + "" + this.wv[13].getText() + "" + this.wv[14].getText() + "" + this.wv[15].getText() + "";
                        this.aplyd1 = false;
                        this.aplyd2 = false;
                        this.changed2 = false;
                        this.mouseon = -1;
                    }
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("BACK Wheels :", 12, 424);
                    this.rd.drawString("\u00b1X :", 12, 449);
                    this.movefield(this.wv[0], 35, 433, 40, 22);
                    this.rd.drawString("Y :", 86, 449);
                    this.movefield(this.wv[1], 101, 433, 40, 22);
                    this.rd.drawString("Z :", 151, 449);
                    this.movefield(this.wv[2], 166, 433, 40, 22);
                    this.rd.drawString("Height :", 12, 479);
                    this.movefield(this.wv[3], 56, 463, 40, 22);
                    this.rd.drawString("Width :", 107, 479);
                    this.movefield(this.wv[4], 148, 463, 40, 22);
                    this.rd.drawString("Rims RGB Color :", 12, 509);
                    this.movefield(this.srch, 109, 493, 129, 22);
                    this.rd.drawString("Rims Size :", 12, 539);
                    this.movefield(this.wv[5], 76, 523, 40, 22);
                    this.rd.drawString("Rims Depth :", 126, 539);
                    this.movefield(this.wv[6], 199, 523, 40, 22);
                    if (this.xm > 245 && this.xm < 336 && this.ym > 524 && this.ym < 541) {
                        this.rd.setColor(new Color(255, 0, 0));
                        this.rd.drawLine(248, 540, 279, 540);
                        this.rd.drawLine(327, 540, 334, 540);
                        if (this.mouseon == -1) {
                            this.mouseon = 1;
                            this.setCursor(new Cursor(12));
                        }
                    } else if (this.mouseon == 1) {
                        this.mouseon = -1;
                        this.setCursor(new Cursor(0));
                    }
                    this.rd.drawString("Hide :                ?", 249, 539);
                    this.movefield(this.wv[7], 282, 523, 40, 22);
                    this.stringbutton("   Apply   ", 300, 440, 0, this.aplyd1);
                    this.stringbutton("   Save   ", 300, 477, 0, this.changed2);
                    this.rd.drawString("FRONT Wheels :", 362, 424);
                    this.rd.drawString("\u00b1X :", 362, 449);
                    this.movefield(this.wv[8], 385, 433, 40, 22);
                    this.rd.drawString("Y :", 436, 449);
                    this.movefield(this.wv[9], 451, 433, 40, 22);
                    this.rd.drawString("Z :", 501, 449);
                    this.movefield(this.wv[10], 516, 433, 40, 22);
                    this.rd.drawString("Height :", 362, 479);
                    this.movefield(this.wv[11], 406, 463, 40, 22);
                    this.rd.drawString("Width :", 457, 479);
                    this.movefield(this.wv[12], 498, 463, 40, 22);
                    this.rd.drawString("Rims RGB Color :", 362, 509);
                    this.movefield(this.rplc, 459, 493, 129, 22);
                    this.rd.drawString("Rims Size :", 362, 539);
                    this.movefield(this.wv[13], 426, 523, 40, 22);
                    this.rd.drawString("Rims Depth :", 476, 539);
                    this.movefield(this.wv[14], 549, 523, 40, 22);
                    if (this.xm > 595 && this.xm < 686 && this.ym > 524 && this.ym < 541) {
                        this.rd.setColor(new Color(255, 0, 0));
                        this.rd.drawLine(598, 540, 629, 540);
                        this.rd.drawLine(677, 540, 684, 540);
                        if (this.mouseon == -1) {
                            this.mouseon = 2;
                            this.setCursor(new Cursor(12));
                        }
                    } else if (this.mouseon == 2) {
                        this.mouseon = -1;
                        this.setCursor(new Cursor(0));
                    }
                    this.rd.drawString("Hide :                ?", 599, 539);
                    this.movefield(this.wv[15], 632, 523, 40, 22);
                    this.stringbutton("   Apply   ", 650, 440, 0, this.aplyd2);
                    this.stringbutton("   Save   ", 650, 477, 0, this.changed2);
                    if (this.mouses == -1 && (this.mouseon == 1 || this.mouseon == 2)) {
                        JOptionPane.showMessageDialog(null, "Use this variable to hide the car wheels inside the car if you need to (if they are getting drawn over the car when they\nshould be drawn behind it).\n\nIf you have created a car model with specific places for the wheels go inside them (inside the car), if when you place the\nwheels there they don\u2019t get drawn inside the car (they get drawn over it instead), use this variable to adjust that.\n\nYou can also use this variable to do the opposite, to make the wheels get drawn over the car if they are getting drawn\nbehind it when they shouldn\u2019t.\n\nThe Hide variable takes values from -40 to 40:\nA +ve value from 1 to 40 makes the wheels more hidden, where 40 is the maximum the car wheel can be hidden.\nA -ve value from -1 to -40 does exactly the opposite and makes the wheels more apparent (this if the car wheels appear\ninside the car when they should be outside).\nA 0 value means do nothing.\nMost of the time you will need to use this variable, it will be to enter +ve values from 1-40 for hiding the car wheels.\n\n", "Car Maker", 1);
                    }
                    for (var6_48 = 0; var6_48 < 16; ++var6_48) {
                        if (this.wv[var6_48].hasFocus()) {
                            this.focuson = false;
                        }
                        if (this.wv[var6_48].isShowing()) continue;
                        this.wv[var6_48].show();
                    }
                    if (this.srch.hasFocus()) {
                        this.focuson = false;
                    }
                    if (!this.srch.isShowing()) {
                        this.srch.show();
                    }
                    if (this.rplc.hasFocus()) {
                        this.focuson = false;
                    }
                    if (!this.rplc.isShowing()) {
                        this.rplc.show();
                    }
                    if (!this.focuson) {
                        if (!this.aplyd1 && !this.aply1.equals("" + this.wv[0].getText() + "" + this.wv[1].getText() + "" + this.wv[2].getText() + "" + this.wv[3].getText() + "" + this.wv[4].getText() + "" + this.srch.getText() + "" + this.wv[5].getText() + "" + this.wv[6].getText() + "" + this.wv[7].getText() + "")) {
                            this.aplyd1 = true;
                        }
                        if (!this.aplyd2 && !this.aply2.equals("" + this.wv[8].getText() + "" + this.wv[9].getText() + "" + this.wv[10].getText() + "" + this.wv[11].getText() + "" + this.wv[12].getText() + "" + this.rplc.getText() + "" + this.wv[13].getText() + "" + this.wv[14].getText() + "" + this.wv[15].getText() + "")) {
                            this.aplyd2 = true;
                        }
                    }
                    this.rd.setColor(new Color(170, 170, 170));
                    this.rd.drawLine(350, 410, 350, 550);
                    this.rd.drawLine(300, 409, 400, 409);
                }
                if (this.dtab == 4) {
                    if (this.dtabed != this.dtab) {
                        this.changed2 = false;
                        this.statdef = false;
                        var6_49 = "" + this.editor.getText() + "\n";
                        var7_72 = 0;
                        var8_93 = var6_49.indexOf("\n", 0);
                        while (var8_93 != -1 && var7_72 < var6_49.length()) {
                            var9_106 = var6_49.substring(var7_72, var8_93).trim();
                            var7_72 = var8_93 + 1;
                            var8_93 = var6_49.indexOf("\n", var7_72);
                            try {
                                if (!var9_106.startsWith("stat(")) continue;
                                var10_110 = 0;
                                for (var11_117 = 0; var11_117 < 5; ++var11_117) {
                                    this.stat[var11_117] = this.getvalue("stat", var9_106, var11_117);
                                    if (this.stat[var11_117] > 200) {
                                        this.stat[var11_117] = 200;
                                    }
                                    if (this.stat[var11_117] < 16) {
                                        this.stat[var11_117] = 16;
                                    }
                                    var10_110 += this.stat[var11_117];
                                }
                                var11_117 = 0;
                                if (var10_110 > 680) {
                                    var11_117 = 680 - var10_110;
                                    this.changed2 = true;
                                }
                                if (var10_110 > 640 && var10_110 < 680) {
                                    var11_117 = 640 - var10_110;
                                    this.changed2 = true;
                                }
                                if (var10_110 > 600 && var10_110 < 640) {
                                    var11_117 = 600 - var10_110;
                                    this.changed2 = true;
                                }
                                if (var10_110 > 560 && var10_110 < 600) {
                                    var11_117 = 560 - var10_110;
                                    this.changed2 = true;
                                }
                                if (var10_110 > 520 && var10_110 < 560) {
                                    var11_117 = 520 - var10_110;
                                    this.changed2 = true;
                                }
                                if (var10_110 < 520) {
                                    var11_117 = 520 - var10_110;
                                    this.changed2 = true;
                                }
                                while (var11_117 != 0) {
                                    for (var12_124 = 0; var12_124 < 5; ++var12_124) {
                                        if (var11_117 > 0 && this.stat[var12_124] < 200) {
                                            v8 = var12_124;
                                            this.stat[v8] = this.stat[v8] + 1;
                                            --var11_117;
                                        }
                                        if (var11_117 >= 0 || this.stat[var12_124] <= 16) continue;
                                        v9 = var12_124;
                                        this.stat[v9] = this.stat[v9] - 1;
                                        ++var11_117;
                                    }
                                }
                                for (var12_124 = 0; var12_124 < 5; ++var12_124) {
                                    this.rstat[var12_124] = this.stat[var12_124];
                                }
                                this.statdef = true;
                            }
                            catch (Exception var10_111) {
                                this.statdef = false;
                            }
                        }
                        if (this.statdef) {
                            if (this.simcar.getItemCount() == 16) {
                                this.simcar.add(this.rd, "   ");
                            }
                        } else if (this.simcar.getItemCount() == 17) {
                            this.simcar.remove("   ");
                        }
                    }
                    this.rd.setColor(new Color(0, 0, 0));
                    if (!this.statdef) {
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("To start, please select the most similar NFM car to this car", 350 - this.ftm.stringWidth("To start, please select the most similar NFM car to this car") / 2, 450);
                        this.simcar.move(288, 460);
                        if (!this.simcar.isShowing()) {
                            this.simcar.show();
                        }
                        this.stringbutton("   OK   ", 350, 515, 0, true);
                    } else {
                        this.rd.drawString("Car Class", 54, 435);
                        this.cls.move(34, 440);
                        if (!this.cls.isShowing()) {
                            this.cls.show();
                        }
                        var6_50 = false;
                        var7_73 = 0;
                        for (var8_93 = 0; var8_93 < 5; ++var8_93) {
                            var7_73 += this.stat[var8_93];
                            if (this.stat[var8_93] == this.rstat[var8_93]) continue;
                            var6_50 = true;
                        }
                        if (this.clsel != this.cls.getSelectedIndex()) {
                            this.clsel = this.cls.getSelectedIndex();
                            var8_93 = (4 - this.clsel) * 40 + 520 - var7_73;
                            while (var8_93 != 0) {
                                for (var9_101 = 0; var9_101 < 5; ++var9_101) {
                                    if (var8_93 > 0 && this.stat[var9_101] < 200) {
                                        v10 = var9_101;
                                        this.stat[v10] = this.stat[v10] + 1;
                                        --var8_93;
                                    }
                                    if (var8_93 >= 0 || this.stat[var9_101] <= 16) continue;
                                    v11 = var9_101;
                                    this.stat[v11] = this.stat[v11] - 1;
                                    ++var8_93;
                                }
                            }
                        }
                        if (4 - (var7_73 - 520) / 40 != this.cls.getSelectedIndex()) {
                            this.clsel = 4 - (var7_73 - 520) / 40;
                            this.cls.select(this.clsel);
                        }
                        this.rd.drawString("Most Similar Car", 36, 490);
                        this.simcar.move(20, 495);
                        if (!this.simcar.isShowing()) {
                            this.simcar.show();
                        }
                        if (this.carsel != this.simcar.getSelectedIndex()) {
                            this.carsel = this.simcar.getSelectedIndex();
                            if (this.carsel != 16) {
                                for (var8_93 = 0; var8_93 < 5; ++var8_93) {
                                    this.stat[var8_93] = this.carstat[this.carsel][var8_93];
                                }
                            }
                            this.requestFocus();
                        }
                        var8_93 = 60;
                        var9_101 = 16;
                        for (var10_110 = 0; var10_110 < 16; ++var10_110) {
                            var11_117 = 0;
                            for (var12_124 = 0; var12_124 < 5; ++var12_124) {
                                var11_117 += Math.abs(this.carstat[var10_110][var12_124] - this.stat[var12_124]);
                            }
                            if (var11_117 >= var8_93) continue;
                            var9_101 = var10_110;
                            var8_93 = var11_117;
                        }
                        if (var9_101 != this.carsel) {
                            this.carsel = var9_101;
                            if (this.carsel < this.simcar.getItemCount()) {
                                this.simcar.select(this.carsel);
                            }
                        }
                        this.rd.drawString("Speed :", 196, 435);
                        this.rd.drawString("Acceleration :", 160, 459);
                        this.rd.drawString("Stunts :", 195, 483);
                        this.rd.drawString("Strength :", 183, 507);
                        this.rd.drawString("Endurance :", 171, 531);
                        for (var10_110 = 0; var10_110 < 5; ++var10_110) {
                            for (var11_117 = 0; var11_117 < this.stat[var10_110]; ++var11_117) {
                                this.rd.setColor(Color.getHSBColor((float)((double)var11_117 * 7.0E-4), 1.0f, 1.0f));
                                this.rd.drawLine(250 + var11_117, 426 + var10_110 * 24, 250 + var11_117, 434 + var10_110 * 24);
                            }
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawLine(249, 426 + var10_110 * 24, 249, 434 + var10_110 * 24);
                            this.rd.drawLine(450, 426 + var10_110 * 24, 450, 434 + var10_110 * 24);
                            this.rd.drawLine(249, 435 + var10_110 * 24, 450, 435 + var10_110 * 24);
                            for (var11_117 = 0; var11_117 < 7; ++var11_117) {
                                this.rd.drawLine(275 + var11_117 * 25, 434 + var10_110 * 24, 275 + var11_117 * 25, 430 + var10_110 * 24);
                            }
                            this.stringbutton(" - ", 480, 435 + var10_110 * 24, 4, false);
                            this.stringbutton(" + ", 520, 435 + var10_110 * 24, 4, false);
                        }
                        if (this.carsel < 16 && var8_93 != 0) {
                            this.stringbutton(" Reselect ", 80, 534, 4, true);
                        } else {
                            this.stringbutton(" Reselect ", 80, -1000, 4, true);
                        }
                        this.stringbutton("      Save      ", 620, 459, 0, var6_50 != false || this.changed2 != false);
                        this.stringbutton("   Reset   ", 620, 507, 0, false);
                    }
                }
                if (this.dtab == 5) {
                    if (this.dtabed != this.dtab) {
                        this.mouseon = -1;
                        this.pfase = 0;
                        if (this.o.keyz[0] <= 0 || this.o.keyx[0] >= 0) {
                            this.pfase = -1;
                        }
                        if (this.o.keyz[1] <= 0 || this.o.keyx[1] <= 0) {
                            this.pfase = -1;
                        }
                        if (this.o.keyz[2] >= 0 || this.o.keyx[2] >= 0) {
                            this.pfase = -1;
                        }
                        if (this.o.keyz[3] >= 0 || this.o.keyx[3] <= 0) {
                            this.pfase = -1;
                        }
                        this.crashok = false;
                        this.crashleft = !(Math.random() > Math.random());
                        this.engsel = 0;
                        if (this.pfase == 0) {
                            var6_51 = "" + this.editor.getText() + "\n";
                            var7_74 = 0;
                            var8_93 = var6_51.indexOf("\n", 0);
                            while (var8_93 != -1 && var7_74 < var6_51.length()) {
                                var9_107 = var6_51.substring(var7_74, var8_93).trim();
                                var7_74 = var8_93 + 1;
                                var8_93 = var6_51.indexOf("\n", var7_74);
                                try {
                                    if (!var9_107.startsWith("physics(")) continue;
                                    for (var10_110 = 0; var10_110 < 11; ++var10_110) {
                                        this.phys[var10_110] = this.getvalue("physics", var9_107, var10_110);
                                        if (this.phys[var10_110] > 100) {
                                            this.phys[var10_110] = 100;
                                        }
                                        if (this.phys[var10_110] >= 0) continue;
                                        this.phys[var10_110] = 0;
                                    }
                                    for (var10_110 = 0; var10_110 < 11; ++var10_110) {
                                        this.rphys[var10_110] = this.phys[var10_110];
                                    }
                                    for (var10_110 = 0; var10_110 < 3; ++var10_110) {
                                        this.crash[var10_110] = this.getvalue("physics", var9_107, var10_110 + 11);
                                        if (this.crash[var10_110] > 100) {
                                            this.crash[var10_110] = 100;
                                        }
                                        if (this.crash[var10_110] >= 0) continue;
                                        this.crash[var10_110] = 0;
                                    }
                                    for (var10_110 = 0; var10_110 < 3; ++var10_110) {
                                        this.rcrash[var10_110] = this.crash[var10_110];
                                    }
                                    this.engsel = this.getvalue("physics", var9_107, 14);
                                    if (this.engsel > 4) {
                                        this.engsel = 0;
                                    }
                                    if (this.engsel < 0) {
                                        this.engsel = 0;
                                    }
                                    this.crashok = true;
                                }
                                catch (Exception var10_112) {
                                    this.crashok = false;
                                }
                            }
                        }
                        this.engon = false;
                    }
                    var6_52 = -1;
                    if (this.pfase == 0) {
                        for (var7_75 = 0; var7_75 < 4; ++var7_75) {
                            this.rd.setColor(new Color(0, 0, 0));
                            if (this.xm > this.pnx[var7_75] && this.xm < 230 && this.ym > 433 + var7_75 * 24 && this.ym < 453 + var7_75 * 24) {
                                var6_52 = var7_75;
                                this.rd.setColor(new Color(176, 64, 0));
                                this.rd.drawLine(this.pnx[var7_75], 448 + var7_75 * 24, 128, 448 + var7_75 * 24);
                            }
                            this.rd.drawString("" + this.pname[var7_75] + " :", this.pnx[var7_75], 447 + var7_75 * 24);
                            this.rd.drawLine(140, 443 + var7_75 * 24, 230, 443 + var7_75 * 24);
                            for (var8_93 = 1; var8_93 < 10; ++var8_93) {
                                this.rd.drawLine(140 + 10 * var8_93, 443 - var8_93 + var7_75 * 24, 140 + 10 * var8_93, 443 + var8_93 + var7_75 * 24);
                            }
                            this.rd.setColor(new Color(255, 0, 0));
                            var8_93 = (int)((float)this.phys[var7_75] / 1.1111f / 10.0f);
                            this.rd.fillRect(138 + (int)((float)this.phys[var7_75] / 1.1111f), 443 - var8_93 + var7_75 * 24, 5, var8_93 * 2 + 1);
                            this.rd.setColor(new Color(255, 128, 0));
                            this.rd.drawRect(139 + (int)((float)this.phys[var7_75] / 1.1111f), 434 + var7_75 * 24, 2, 18);
                            this.stringbutton(" - ", 260, 447 + var7_75 * 24, 4, false);
                            this.stringbutton(" + ", 300, 447 + var7_75 * 24, 4, false);
                        }
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("<  Click on any variable name to learn about its function & use!", 333, 447);
                        this.stringbutton(" Random ", 380, 496, 0, false);
                        this.stringbutton(" Reset ", 455, 496, 0, false);
                        this.stringbutton("       Next  >       ", 570, 496, 0, true);
                    }
                    if (this.pfase == 1) {
                        for (var7_76 = 0; var7_76 < 4; ++var7_76) {
                            this.rd.setColor(new Color(0, 0, 0));
                            if (this.xm > this.pnx[var7_76 + 5] && this.xm < 211 && this.ym > 433 + var7_76 * 24 && this.ym < 453 + var7_76 * 24) {
                                var6_52 = var7_76 + 5;
                                this.rd.setColor(new Color(176, 64, 0));
                                this.rd.drawLine(this.pnx[var7_76 + 5], 448 + var7_76 * 24, 109, 448 + var7_76 * 24);
                            }
                            this.rd.drawString("" + this.pname[var7_76 + 5] + " :", this.pnx[var7_76 + 5], 447 + var7_76 * 24);
                            this.rd.drawLine(121, 443 + var7_76 * 24, 211, 443 + var7_76 * 24);
                            for (var8_93 = 1; var8_93 < 10; ++var8_93) {
                                this.rd.drawLine(121 + 10 * var8_93, 443 - var8_93 + var7_76 * 24, 121 + 10 * var8_93, 443 + var8_93 + var7_76 * 24);
                            }
                            this.rd.setColor(new Color(255, 0, 0));
                            var8_93 = (int)((float)this.phys[var7_76 + 5] / 1.1111f / 10.0f);
                            this.rd.fillRect(119 + (int)((float)this.phys[var7_76 + 5] / 1.1111f), 443 - var8_93 + var7_76 * 24, 5, var8_93 * 2 + 1);
                            this.rd.setColor(new Color(255, 128, 0));
                            this.rd.drawRect(120 + (int)((float)this.phys[var7_76 + 5] / 1.1111f), 434 + var7_76 * 24, 2, 18);
                            this.stringbutton(" - ", 241, 447 + var7_76 * 24, 4, false);
                            this.stringbutton(" + ", 281, 447 + var7_76 * 24, 4, false);
                        }
                        for (var7_76 = 0; var7_76 < 2; ++var7_76) {
                            this.rd.setColor(new Color(0, 0, 0));
                            if (this.xm > this.pnx[var7_76 + 9] && this.xm < 548 && this.ym > 433 + var7_76 * 24 && this.ym < 453 + var7_76 * 24) {
                                var6_52 = var7_76 + 9;
                                this.rd.setColor(new Color(176, 64, 0));
                                this.rd.drawLine(this.pnx[var7_76 + 9], 448 + var7_76 * 24, 446, 448 + var7_76 * 24);
                            }
                            this.rd.drawString("" + this.pname[var7_76 + 9] + " :", this.pnx[var7_76 + 9], 447 + var7_76 * 24);
                            this.rd.drawLine(458, 443 + var7_76 * 24, 548, 443 + var7_76 * 24);
                            for (var8_93 = 1; var8_93 < 10; ++var8_93) {
                                this.rd.drawLine(458 + 10 * var8_93, 443 - var8_93 + var7_76 * 24, 458 + 10 * var8_93, 443 + var8_93 + var7_76 * 24);
                            }
                            this.rd.setColor(new Color(255, 0, 0));
                            var8_93 = (int)((float)this.phys[var7_76 + 9] / 1.1111f / 10.0f);
                            this.rd.fillRect(456 + (int)((float)this.phys[var7_76 + 9] / 1.1111f), 443 - var8_93 + var7_76 * 24, 5, var8_93 * 2 + 1);
                            this.rd.setColor(new Color(255, 128, 0));
                            this.rd.drawRect(457 + (int)((float)this.phys[var7_76 + 9] / 1.1111f), 434 + var7_76 * 24, 2, 18);
                            this.stringbutton(" - ", 578, 447 + var7_76 * 24, 4, false);
                            this.stringbutton(" + ", 618, 447 + var7_76 * 24, 4, false);
                        }
                        this.stringbutton(" Random ", 361, 519, 0, false);
                        this.stringbutton(" Reset ", 436, 519, 0, false);
                        this.stringbutton(" <  Back ", 509, 519, 0, false);
                        this.stringbutton("       Next  >       ", 603, 519, 0, true);
                    }
                    if (this.pfase == 2) {
                        if (this.xm > 40 && this.xm < 670 && this.ym > 416 && this.ym < 436) {
                            var6_52 = 11;
                            this.rd.setColor(new Color(176, 64, 0));
                            this.rd.drawLine(596, 431, 669, 431);
                        }
                        this.rd.drawString("[   Crash Test,  Damage :                                       ]                                                     What is this?", 180, 430);
                        if (this.hitmag < 0) {
                            this.hitmag = 0;
                        }
                        if (this.hitmag > 17000) {
                            this.crashok = true;
                            this.hitmag = 17000;
                            for (var7_77 = 0; var7_77 < this.o.npl; ++var7_77) {
                                if (this.o.p[var7_77].wz != 0 && this.o.p[var7_77].gr != -17 && this.o.p[var7_77].gr != -16 || this.o.p[var7_77].embos != 0) continue;
                                this.o.p[var7_77].embos = 1;
                            }
                        }
                        this.rd.setColor(new Color(255, (int)(250.0f - (float)this.hitmag / 68.0f), 0));
                        this.rd.fillRect(322, 423, (int)((float)this.hitmag / 170.0f), 7);
                        this.rd.setColor(new Color(255, 0, 0));
                        this.rd.drawRect(322, 423, 100, 7);
                        if (var6_52 != 11) {
                            this.rd.setColor(new Color(170, 170, 170));
                        } else {
                            this.rd.setColor(new Color(176, 64, 0));
                        }
                        this.rd.drawString("Normal Crash", 39, 438);
                        this.rd.drawString("Roof Crash", 501, 438);
                        this.rd.drawLine(125, 426, 179, 426);
                        this.rd.drawLine(125, 426, 125, 440);
                        this.rd.drawLine(491, 426, 437, 426);
                        this.rd.drawLine(491, 426, 491, 440);
                        this.rd.drawRect(19, 440, 276, 91);
                        this.rd.drawRect(339, 440, 312, 67);
                        this.rd.setColor(new Color(0, 0, 0));
                        if (this.xm > 50 && this.xm < 195 && this.ym > 446 && this.ym < 466) {
                            var6_52 = 12;
                            this.rd.setColor(new Color(176, 64, 0));
                            this.rd.drawLine(50, 461, 94, 461);
                        }
                        this.rd.drawString("Radius :", 50, 460);
                        this.rd.drawLine(105, 456, 195, 456);
                        for (var7_78 = 1; var7_78 < 10; ++var7_78) {
                            this.rd.drawLine(105 + 10 * var7_78, 456 - var7_78, 105 + 10 * var7_78, 456 + var7_78);
                        }
                        this.rd.setColor(new Color(255, 0, 0));
                        var7_78 = (int)((float)this.crash[0] / 1.1111f / 10.0f);
                        this.rd.fillRect(103 + (int)((float)this.crash[0] / 1.1111f), 456 - var7_78, 5, var7_78 * 2 + 1);
                        this.rd.setColor(new Color(255, 128, 0));
                        this.rd.drawRect(104 + (int)((float)this.crash[0] / 1.1111f), 447, 2, 18);
                        this.stringbutton(" - ", 225, 460, 4, false);
                        this.stringbutton(" + ", 265, 460, 4, false);
                        this.rd.setColor(new Color(0, 0, 0));
                        if (this.xm > 30 && this.xm < 195 && this.ym > 470 && this.ym < 490) {
                            var6_52 = 13;
                            this.rd.setColor(new Color(176, 64, 0));
                            this.rd.drawLine(30, 485, 94, 485);
                        }
                        this.rd.drawString("Magnitude :", 30, 484);
                        this.rd.drawLine(105, 480, 195, 480);
                        for (var8_93 = 1; var8_93 < 10; ++var8_93) {
                            this.rd.drawLine(105 + 10 * var8_93, 480 - var8_93, 105 + 10 * var8_93, 480 + var8_93);
                        }
                        this.rd.setColor(new Color(255, 0, 0));
                        var7_78 = (int)((float)this.crash[1] / 1.1111f / 10.0f);
                        this.rd.fillRect(103 + (int)((float)this.crash[1] / 1.1111f), 480 - var7_78, 5, var7_78 * 2 + 1);
                        this.rd.setColor(new Color(255, 128, 0));
                        this.rd.drawRect(104 + (int)((float)this.crash[1] / 1.1111f), 471, 2, 18);
                        this.stringbutton(" - ", 225, 484, 4, false);
                        this.stringbutton(" + ", 265, 484, 4, false);
                        this.rd.setColor(new Color(0, 0, 0));
                        if (this.xm > 350 && this.xm < 551 && this.ym > 446 && this.ym < 466) {
                            var6_52 = 14;
                            this.rd.setColor(new Color(176, 64, 0));
                            this.rd.drawLine(350, 461, 450, 461);
                        }
                        this.rd.drawString("Roof Destruction :", 350, 460);
                        this.rd.drawLine(461, 456, 551, 456);
                        for (var8_93 = 1; var8_93 < 10; ++var8_93) {
                            this.rd.drawLine(461 + 10 * var8_93, 456 - var8_93, 461 + 10 * var8_93, 456 + var8_93);
                        }
                        this.rd.setColor(new Color(255, 0, 0));
                        var7_78 = (int)((float)this.crash[2] / 1.1111f / 10.0f);
                        this.rd.fillRect(459 + (int)((float)this.crash[2] / 1.1111f), 456 - var7_78, 5, var7_78 * 2 + 1);
                        this.rd.setColor(new Color(255, 128, 0));
                        this.rd.drawRect(460 + (int)((float)this.crash[2] / 1.1111f), 447, 2, 18);
                        this.stringbutton(" - ", 581, 460, 4, false);
                        this.stringbutton(" + ", 621, 460, 4, false);
                        this.stringbutton("    CRASH!    ", 143, 516, 0, true);
                        this.stringbutton("  Fix  ", 235, 516, 0, false);
                        this.stringbutton("    CRASH Roof!    ", 484, 492, 0, true);
                        this.stringbutton("  Fix  ", 591, 492, 0, false);
                        this.stringbutton(" Reset ", 435, 535, 0, false);
                        this.stringbutton(" <  Back ", 508, 535, 0, false);
                        this.stringbutton("       Next  >       ", 602, 535, 0, true);
                    }
                    if (this.pfase == 3) {
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Select the most suitable engine for your car :", 30, 440);
                        this.engine.move(293, 424);
                        if (!this.engine.isShowing()) {
                            this.engine.show();
                            this.engine.select(this.engsel);
                        }
                        if (this.engsel != this.engine.getSelectedIndex()) {
                            this.engsel = this.engine.getSelectedIndex();
                            for (var7_79 = 0; var7_79 < 5; ++var7_79) {
                                for (var8_93 = 0; var8_93 < 5; ++var8_93) {
                                    this.engs[var8_93][var7_79].stop();
                                    this.engs[var8_93][var7_79].checkopen();
                                }
                            }
                            this.engon = false;
                        }
                        if (this.engsel == 0) {
                            this.rd.drawString("Normal Engine:  Like Tornado Shark, Sword of Justice or Radical One's engine.", 30, 470);
                        }
                        if (this.engsel == 1) {
                            this.rd.drawString("V8 Engine:  High speed engine like Formula 7, Drifter X or Might Eight's engine.", 30, 470);
                        }
                        if (this.engsel == 2) {
                            this.rd.drawString("Retro Engine:  Like Wow Caninaro, Lead Oxide or Kool Kat\u2019s engine.", 30, 470);
                        }
                        if (this.engsel == 3) {
                            this.rd.drawString("Power Engine:  Turbo/super charged engine like Max Revenge, High Rider or Dr Monstaa\u2019s engine.", 30, 470);
                        }
                        if (this.engsel == 4) {
                            this.rd.drawString("Diesel Engine:  Big diesel powered engine for big cars like EL King or  M A S H E E N .", 30, 470);
                        }
                        this.rd.drawString("LISTEN :", 30, 500);
                        this.stringbutton(" Idle ", 108, 500, 0, true);
                        this.stringbutton(" RPM 1 ", 170, 500, 0, true);
                        this.stringbutton(" RPM 2 ", 240, 500, 0, true);
                        this.stringbutton(" RPM 3 ", 310, 500, 0, true);
                        this.stringbutton(" RPM MAX ", 389, 500, 0, true);
                        if (this.engon) {
                            this.stringbutton("          Stop Engine          ", 240, 535, 0, true);
                        } else {
                            this.stringbutton("          Stop Engine          ", 240, -2500, 0, true);
                        }
                        this.stringbutton(" <  Back ", 500, 525, 0, false);
                        this.stringbutton("     Save & Finish!     ", 610, 525, 0, true);
                    }
                    if (this.pfase == 4) {
                        this.rd.drawString("Testing & Setting up Physics...", 265, 470);
                        this.repaint();
                        try {
                            Thread.sleep(100L);
                        }
                        catch (InterruptedException var7_80) {
                            // empty catch block
                        }
                        for (var7_81 = 0; var7_81 < 4; ++var7_81) {
                            var8_93 = 0;
                            var9_101 = 4;
                            if (var7_81 == 1) {
                                var9_101 = 2;
                            }
                            if (var7_81 == 2) {
                                var8_93 = 2;
                            }
                            for (var10_113 = 0; var10_113 < 10; ++var10_113) {
                                this.setupo();
                                this.o.xy = 0;
                                this.hitmag = 0;
                                var11_118 = 0;
                                this.actmag = 0;
                                var12_124 = var8_93;
                                var13_126 = 0;
                                while (this.hitmag < 17000) {
                                    if (var13_126 != 0) {
                                        this.regx(var12_124, (int)(150.0 + 600.0 * Math.random()), true);
                                    } else {
                                        this.regz(var12_124, (int)(150.0 + 600.0 * Math.random()), true);
                                    }
                                    if (++var12_124 != var9_101) continue;
                                    this.o.xz += 45;
                                    this.o.zy += 45;
                                    var12_124 = 0;
                                    var13_126 = var13_126 != 0 ? 0 : 1;
                                    if (var11_118 == this.actmag) {
                                        this.crash[0] = this.crash[0] + 10;
                                    }
                                    var11_118 = this.actmag;
                                }
                            }
                            var10_114 = 0.0f;
                            for (var11_119 = 0; var11_119 < 10; ++var11_119) {
                                this.setupo();
                                this.o.xy = 0;
                                this.actmag = 0;
                                this.hitmag = 0;
                                var12_124 = var8_93;
                                var13_126 = 0;
                                while (this.hitmag < 17000) {
                                    if (var13_126 != 0) {
                                        this.regx(var12_124, (int)(150.0 + 600.0 * Math.random()), true);
                                    } else {
                                        this.regz(var12_124, (int)(150.0 + 600.0 * Math.random()), true);
                                    }
                                    if (++var12_124 != var9_101) continue;
                                    this.o.xz += 45;
                                    this.o.zy += 45;
                                    var12_124 = 0;
                                    if (var13_126 != 0) {
                                        var13_126 = 0;
                                        continue;
                                    }
                                    var13_126 = 1;
                                }
                                var10_114 += (float)this.actmag / (float)this.hitmag;
                            }
                            this.actmag = (int)((float)this.hitmag * (var10_114 /= 10.0f));
                            if (this.stat[4] > 200) {
                                this.stat[4] = 200;
                            }
                            if (this.stat[4] < 16) {
                                this.stat[4] = 16;
                            }
                            if ((double)(var11_120 = 0.9f + (float)(this.stat[4] - 90) * 0.01f) < 0.6) {
                                var11_120 = 0.6f;
                            }
                            if (this.stat[4] == 200 && this.stat[0] <= 88) {
                                var11_120 = 3.0f;
                            }
                            var12_124 = (int)((float)this.actmag * var11_120);
                            for (var13_126 = 0; var13_126 < 12; ++var13_126) {
                                this.setupo();
                                this.o.xy = 0;
                                this.o.xz = 90 * var13_126;
                                if (this.o.xz >= 360) {
                                    this.o.xz -= 360;
                                }
                                this.hitmag = 0;
                                var14_129 = 0;
                                this.actmag = 0;
                                var15_130 = var8_93;
                                var16_132 = 0;
                                while (this.actmag < var12_124) {
                                    if (var16_132 != 0) {
                                        this.regx(var15_130, (int)(150.0 + 600.0 * Math.random()), true);
                                    } else {
                                        this.regz(var15_130, (int)(150.0 + 600.0 * Math.random()), true);
                                    }
                                    if (++var15_130 != var9_101) continue;
                                    var16_132 = var16_132 != 0 ? 0 : 1;
                                    var15_130 = 0;
                                    if (var14_129 == this.actmag) {
                                        this.crash[0] = this.crash[0] + 10;
                                    }
                                    var14_129 = this.actmag;
                                }
                            }
                            if (var7_81 != 3) continue;
                            var10_114 = 0.0f;
                            for (var13_126 = 0; var13_126 < 10; ++var13_126) {
                                this.setupo();
                                this.o.xy = 0;
                                this.actmag = 0;
                                this.hitmag = 0;
                                var14_129 = var8_93;
                                var15_130 = 0;
                                while (this.hitmag < 17000) {
                                    if (var15_130 != 0) {
                                        this.regx(var14_129, (int)(150.0 + 600.0 * Math.random()), true);
                                    } else {
                                        this.regz(var14_129, (int)(150.0 + 600.0 * Math.random()), true);
                                    }
                                    if (++var14_129 != var9_101) continue;
                                    this.o.xz += 45;
                                    this.o.zy += 45;
                                    var14_129 = 0;
                                    if (var15_130 != 0) {
                                        var15_130 = 0;
                                        continue;
                                    }
                                    var15_130 = 1;
                                }
                                var10_114 += (float)this.actmag / (float)this.hitmag;
                            }
                            this.actmag = (int)((float)this.hitmag * (var10_114 /= 10.0f));
                        }
                        this.setupo();
                        var7_82 = "" + this.editor.getText() + "\n";
                        var8_94 = "";
                        var9_101 = 0;
                        var10_115 = var7_82.indexOf("\n", 0);
                        while (var10_115 != -1 && var9_101 < var7_82.length()) {
                            var11_121 = var7_82.substring(var9_101, var10_115).trim();
                            var9_101 = var10_115 + 1;
                            var10_115 = var7_82.indexOf("\n", var9_101);
                            if (!var11_121.startsWith("physics(")) {
                                var8_94 = var8_94 + "" + var11_121 + "\n";
                                continue;
                            }
                            var8_94 = var8_94.trim();
                            var8_94 = var8_94 + "\n";
                        }
                        var8_94 = var8_94.trim();
                        var8_94 = var8_94 + "\n\n\nphysics(" + this.phys[0] + "," + this.phys[1] + "," + this.phys[2] + "," + this.phys[3] + "," + this.phys[4] + "," + this.phys[5] + "," + this.phys[6] + "," + this.phys[7] + "," + this.phys[8] + "," + this.phys[9] + "," + this.phys[10] + "," + this.crash[0] + "," + this.crash[1] + "," + this.crash[2] + "," + this.engsel + "," + this.actmag + ")\n\n\n\n";
                        this.editor.setText(var8_94);
                        this.savefile();
                        for (var11_122 = 0; var11_122 < 11; ++var11_122) {
                            this.rphys[var11_122] = this.phys[var11_122];
                        }
                        for (var11_122 = 0; var11_122 < 3; ++var11_122) {
                            this.rcrash[var11_122] = this.crash[var11_122];
                        }
                        this.pfase = 5;
                    }
                    if (this.pfase == 5) {
                        this.rd.drawString("Car physics has been successfully set up!", 231, 450);
                        this.rd.drawString("Test drive your car to see the results...", 242, 490);
                    }
                    if (var6_52 != -1) {
                        if (this.mouseon == -1) {
                            this.mouseon = var6_52;
                            this.setCursor(new Cursor(12));
                        }
                    } else if (this.mouseon != -1) {
                        this.mouseon = -1;
                        this.setCursor(new Cursor(0));
                    }
                    if (this.mouses == -1 && var6_52 != -1) {
                        JOptionPane.showMessageDialog(null, this.usage[var6_52], "Car Maker", 1);
                    }
                }
                if (this.dtab == 6) {
                    if (this.dtab != this.dtabed) {
                        var6_53 = "" + this.editor.getText() + "\n";
                        var7_83 = 0;
                        var8_95 = var6_53.indexOf("\n", 0);
                        while (var8_95 != -1 && var7_83 < var6_53.length()) {
                            var9_108 = var6_53.substring(var7_83, var8_95).trim();
                            var7_83 = var8_95 + 1;
                            var8_95 = var6_53.indexOf("\n", var7_83);
                            if (!var9_108.startsWith("handling(")) continue;
                            try {
                                this.handling = this.getvalue("handling", var9_108, 0);
                                if (this.handling > 200) {
                                    this.handling = 200;
                                }
                                if (this.handling >= 50) continue;
                                this.handling = 50;
                            }
                            catch (Exception var10_116) {}
                        }
                        this.rateh = false;
                    }
                    if (!this.rateh) {
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Test Drive the Car", 350 - this.ftm.stringWidth("Test Drive the Car") / 2, 445);
                        this.witho.move(292, 455);
                        if (!this.witho.isShowing()) {
                            this.witho.show();
                        }
                        this.stringbutton("     TEST DRIVE!     ", 350, 505, 0, true);
                        if (this.tested) {
                            this.stringbutton("  Edit Stats & Class  ", 150, 471, 0, false);
                            this.stringbutton("  Edit Physics  ", 150, 505, 0, false);
                            this.stringbutton("     Rate Car Handling     ", 550, 471, 0, true);
                        }
                    } else {
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Based on you test drive(s), how do your rate " + this.carname + "'s handling?", 350 - this.ftm.stringWidth("Based on your test drive(s), how do you rate " + this.carname + "'s handling?") / 2, 445);
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.rd.drawString("Handling :", 183, 483);
                        for (var6_54 = 0; var6_54 < this.handling; ++var6_54) {
                            this.rd.setColor(Color.getHSBColor((float)((double)var6_54 * 7.0E-4), 1.0f, 1.0f));
                            this.rd.drawLine(250 + var6_54, 474, 250 + var6_54, 482);
                        }
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawLine(249, 474, 249, 482);
                        this.rd.drawLine(450, 474, 450, 482);
                        this.rd.drawLine(249, 483, 450, 483);
                        for (var6_54 = 0; var6_54 < 7; ++var6_54) {
                            this.rd.drawLine(275 + var6_54 * 25, 482, 275 + var6_54 * 25, 478);
                        }
                        this.stringbutton(" - ", 480, 483, 4, false);
                        this.stringbutton(" + ", 520, 483, 4, false);
                        this.stringbutton("       Save       ", 388, 525, 0, true);
                        this.stringbutton(" Cancel ", 298, 525, 0, false);
                    }
                }
                if (this.polynum >= 0 && this.cntpls > 0) {
                    for (var6_55 = 0; var6_55 < this.o.npl; ++var6_55) {
                        if (var6_55 >= this.polynum && var6_55 < this.polynum + this.cntpls) {
                            if (this.pflk) {
                                this.o.p[var6_55].hsb[2] = 1.0f;
                                continue;
                            }
                            this.o.p[var6_55].hsb[2] = 0.0f;
                            this.o.p[var6_55].hsb[0] = Math.abs(0.5f - this.o.p[var6_55].hsb[0]);
                            while (this.o.p[var6_55].hsb[0] > 1.0f) {
                                this.o.p[var6_55].hsb[0] = this.o.p[var6_55].hsb[0] - 1.0f;
                            }
                            continue;
                        }
                        this.o.p[var6_55].gr = this.prflk > 6 && this.prflk < 20 ? -13 : 1;
                    }
                    this.pflk = this.pflk == false;
                    if (this.prflk < 40) {
                        ++this.prflk;
                    }
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("[ Showing " + this.cntpls + " Polygons Selected ]", 350 - this.ftm.stringWidth("[ Showing " + this.cntpls + " Polygons Selected ]") / 2, 45);
                    this.stringbutton("  Stop  ", 350, 67, 5, false);
                }
                var2_2 = 50;
                if (this.rotr) {
                    this.o.xz -= 5;
                    var2_2 = 15;
                }
                if (this.rotl) {
                    this.o.xz += 5;
                    var2_2 = 15;
                }
                if (this.left) {
                    this.o.xy -= 5;
                    var2_2 = 15;
                }
                if (this.right) {
                    this.o.xy += 5;
                    var2_2 = 15;
                }
                if (this.up) {
                    this.o.zy -= 5;
                    var2_2 = 15;
                }
                if (this.down) {
                    this.o.zy += 5;
                    var2_2 = 15;
                }
                if (this.plus) {
                    this.o.y += 5;
                    var2_2 = 15;
                }
                if (this.minus) {
                    this.o.y -= 5;
                    var2_2 = 15;
                }
                if (this.in) {
                    this.o.z += 10;
                    var2_2 = 15;
                }
                if (this.out) {
                    this.o.z -= 10;
                    var2_2 = 15;
                }
                this.ox = this.o.x;
                this.oy = this.o.y;
                this.oz = this.o.z;
                this.oxz = this.o.xz;
                this.oxy = this.o.xy;
                this.ozy = this.o.zy;
                if (this.dtabed != this.dtab) {
                    this.dtabed = this.dtab;
                }
                if (this.dtab == 5 && this.pfase == -1) {
                    this.repaint();
                    JOptionPane.showMessageDialog(null, "Car Wheels not defined or not defined correctly.\nBefore defining the car Physics car Wheels must be defined correctly!\nPlease go to the \u2018Wheels\u2019 tab and use  [ Apply ]  and  [ Save ]  to define correctly.\n", "Car Maker", 1);
                    this.dtab = 3;
                }
            }
            if (this.tab == 3) {
                this.rd.setFont(new Font("Arial", 1, 13));
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawString("Publish Car :  [ " + this.carname + " ]", 30, 50);
                this.rd.drawString("Publishing Type :", 30, 80);
                this.pubtyp.move(150, 63);
                if (!this.pubtyp.isShowing()) {
                    this.pubtyp.show();
                    this.pubtyp.select(1);
                }
                this.stringbutton("       Publish  >       ", 102, 110, 0, true);
                this.pubitem.move(690 - this.pubitem.w, 96);
                if (!this.pubitem.isShowing()) {
                    this.pubitem.show();
                }
                if (this.pubitem.sel != 0) {
                    var3_11 = false;
                    for (var4_22 = 0; var4_22 < this.nmc; ++var4_22) {
                        if (!this.pubitem.getSelectedItem().equals(this.mycars[var4_22])) continue;
                        var3_11 = true;
                    }
                    if (!var3_11) {
                        this.logged = 2;
                    }
                }
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.setFont(new Font("Arial", 0, 12));
                if (this.pubtyp.getSelectedIndex() == 0) {
                    this.rd.drawString("Private :  This means only you can use your car and no one else can add", 268, 72);
                    this.rd.drawString("it to their account to play with it !", 268, 88);
                }
                if (this.pubtyp.getSelectedIndex() == 1) {
                    this.rd.drawString("Public :  This means anyone can add this car to their account to play with it,", 268, 72);
                    this.rd.drawString("but only you can download it to your Car Maker (no one else can get it\u2019s code).", 268, 88);
                }
                if (this.pubtyp.getSelectedIndex() == 2) {
                    this.rd.drawString("Super Public :  This means anyone can add this car to their account to play", 268, 72);
                    this.rd.drawString("with it and anyone can also download it to their Car Maker to get its code.", 268, 88);
                }
                this.rd.setFont(new Font("Arial", 1, 12));
                this.ftm = this.rd.getFontMetrics();
                this.rd.drawString("Car Name", 80 - this.ftm.stringWidth("Car Name") / 2, 138);
                this.rd.drawString("Car Class", 200 - this.ftm.stringWidth("Car Class") / 2, 138);
                this.rd.drawString("Created By", 300 - this.ftm.stringWidth("Created By") / 2, 138);
                this.rd.drawString("Added By", 400 - this.ftm.stringWidth("Added By") / 2, 138);
                this.rd.drawString("Publish Type", 500 - this.ftm.stringWidth("Publish Type") / 2, 138);
                this.rd.drawString("Options", 620 - this.ftm.stringWidth("Options") / 2, 138);
                this.rd.drawLine(150, 129, 150, 140);
                this.rd.drawLine(250, 129, 250, 140);
                this.rd.drawLine(350, 129, 350, 140);
                this.rd.drawLine(450, 129, 450, 140);
                this.rd.drawLine(550, 129, 550, 140);
                this.rd.drawRect(10, 140, 680, 402);
                if (this.logged == 0) {
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("Login to Retrieve your Account Cars", 350 - this.ftm.stringWidth("Login to Retrieve your Account Cars") / 2, 220);
                    this.rd.drawString("Nickname:", 326 - this.ftm.stringWidth("Nickname:") - 14, 266);
                    this.movefield(this.tnick, 326, 250, 129, 22);
                    if (!this.tnick.isShowing()) {
                        this.tnick.show();
                    }
                    this.rd.drawString("Password:", 326 - this.ftm.stringWidth("Password:") - 14, 296);
                    this.movefield(this.tpass, 326, 280, 129, 22);
                    if (!this.tpass.isShowing()) {
                        this.tpass.show();
                    }
                    this.stringbutton("       Login       ", 350, 340, 0, true);
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("Register a full account or if you have a trial account upgrade it!", 350 - this.ftm.stringWidth("Register a full account or if you have a trial account upgrade it!") / 2, 450);
                    this.stringbutton("   Register!   ", 290, 480, 0, true);
                    this.stringbutton("   Upgrade!   ", 410, 480, 0, true);
                    this.rd.setFont(new Font("Arial", 0, 12));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("You need a full account to publish your cars to the multiplayer game!", 350 - this.ftm.stringWidth("You need a full account to publish your cars to the multiplayer game!") / 2, 505);
                }
                if (this.logged == -1) {
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("Ready to publish...", 350 - this.ftm.stringWidth("Ready to publish...") / 2, 220);
                    this.rd.drawString("Click \u2018Publish\u2019 above to add car: '" + this.carname + "'.", 350 - this.ftm.stringWidth("Click \u2018Publish\u2019 above to add car: '" + this.carname + "'.") / 2, 280);
                }
                if (this.logged == 2) {
                    this.mycars[this.roto] = this.pubitem.getSelectedItem();
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(new Color(225, 225, 225));
                    this.rd.fillRect(50, 150, 600, 150);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Loading " + this.mycars[this.roto] + "\u2018s info...", 350 - this.ftm.stringWidth("Loading " + this.mycars[this.roto] + "\u2018s info...") / 2, 220);
                    this.repaint();
                    this.maker[this.roto] = "Unkown";
                    this.pubt[this.roto] = -1;
                    this.clas[this.roto] = 0;
                    this.nad[this.roto] = 0;
                    var3_12 = "";
                    try {
                        var4_23 = ("http://multiplayer.needformadness.com/cars/" + this.mycars[this.roto] + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "").replace(' ', '_');
                        var5_32 = new URL(var4_23);
                        var6_56 = new DataInputStream(var5_32.openStream());
                        while ((var3_12 = var6_56.readLine()) != null) {
                            var3_12 = "" + var3_12.trim();
                            if (!var3_12.startsWith("details")) continue;
                            this.maker[this.roto] = this.getSvalue("details", var3_12, 0);
                            this.pubt[this.roto] = this.getvalue("details", var3_12, 1);
                            this.clas[this.roto] = this.getvalue("details", var3_12, 2);
                            var7_84 = false;
                            while (!var7_84) {
                                this.addeda[this.roto][this.nad[this.roto]] = this.getSvalue("details", var3_12, 3 + this.nad[this.roto]);
                                if (this.addeda[this.roto][this.nad[this.roto]].equals("")) {
                                    var7_84 = true;
                                    continue;
                                }
                                v12 = this.roto;
                                this.nad[v12] = this.nad[v12] + 1;
                            }
                        }
                        ++this.nmc;
                        if (this.nmc > 20) {
                            this.nmc = 20;
                        }
                        ++this.roto;
                        if (this.roto >= 20) {
                            this.roto = 0;
                        }
                    }
                    catch (Exception var4_24) {
                        // empty catch block
                    }
                    this.logged = 3;
                }
                if (this.logged == 1) {
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("Loading your account car list...", 350 - this.ftm.stringWidth("Loading your account car list...") / 2, 220);
                    this.repaint();
                    this.pubitem.removeAll();
                    this.pubitem.add(this.rd, "Account Cars");
                    this.nmc = 0;
                    this.roto = 0;
                    var3_13 = 0;
                    var4_25 = "";
                    try {
                        var5_33 = new URL("http://multiplayer.needformadness.com/cars/lists/" + this.tnick.getText() + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "");
                        var6_57 = new DataInputStream(var5_33.openStream());
                        while ((var4_25 = var6_57.readLine()) != null) {
                            var4_25 = "" + var4_25.trim();
                            if (!var4_25.startsWith("mycars")) continue;
                            var7_85 = true;
                            while (var7_85 && var3_13 < 700) {
                                var8_96 = this.getSvalue("mycars", var4_25, var3_13);
                                if (var8_96.equals("")) {
                                    var7_85 = false;
                                    continue;
                                }
                                this.pubitem.add(this.rd, var8_96);
                                ++var3_13;
                            }
                        }
                        this.setCursor(new Cursor(0));
                        this.logged = -1;
                        var6_57.close();
                    }
                    catch (Exception var5_34) {
                        var6_57 = "" + var5_34;
                        if (var6_57.indexOf("FileNotFound") != -1) {
                            this.setCursor(new Cursor(0));
                            this.logged = -1;
                        }
                        this.logged = 0;
                        JOptionPane.showMessageDialog(null, "Unable to connect to server at this moment, please try again later.", "Car Maker", 1);
                    }
                    if (!this.justpubd.equals("")) {
                        this.pubitem.select(this.justpubd);
                        this.justpubd = "";
                    }
                }
                ** if (this.logged != 3) goto lbl2040
                for (var3_14 = 0; var3_14 < this.nmc; ++var3_14) {
                    block500: {
                        this.rd.setColor(new Color(235, 235, 235));
                        if (this.xm > 11 && this.xm < 689 && this.ym > 142 + var3_14 * 20 && this.ym < 160 + var3_14 * 20) {
                            this.rd.setColor(new Color(255, 255, 255));
                        }
                        this.rd.fillRect(11, 142 + var3_14 * 20, 678, 18);
                        this.rd.setFont(new Font("Arial", 0, 12));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString(this.mycars[var3_14], 80 - this.ftm.stringWidth(this.mycars[var3_14]) / 2, 156 + var3_14 * 20);
                        this.rd.setColor(new Color(155, 155, 155));
                        this.rd.drawLine(150, 145 + var3_14 * 20, 150, 157 + var3_14 * 20);
                        if (this.pubt[var3_14] != -1) {
                            this.rd.drawLine(250, 145 + var3_14 * 20, 250, 157 + var3_14 * 20);
                            this.rd.drawLine(350, 145 + var3_14 * 20, 350, 157 + var3_14 * 20);
                            this.rd.drawLine(450, 145 + var3_14 * 20, 450, 157 + var3_14 * 20);
                            this.rd.drawLine(550, 145 + var3_14 * 20, 550, 157 + var3_14 * 20);
                            this.rd.setColor(new Color(0, 0, 64));
                            var4_26 = "C";
                            if (this.clas[var3_14] == 1) {
                                var4_26 = "B & C";
                            }
                            if (this.clas[var3_14] == 2) {
                                var4_26 = "B";
                            }
                            if (this.clas[var3_14] == 3) {
                                var4_26 = "A & B";
                            }
                            if (this.clas[var3_14] == 4) {
                                var4_26 = "A";
                            }
                            this.rd.drawString("Class " + var4_26 + "", 200 - this.ftm.stringWidth("Class " + var4_26 + "") / 2, 156 + var3_14 * 20);
                            var5_35 = false;
                            if (this.maker[var3_14].toLowerCase().equals(this.tnick.getText().toLowerCase())) {
                                var5_35 = true;
                                this.rd.setColor(new Color(0, 64, 0));
                                this.rd.drawString("You", 300 - this.ftm.stringWidth("You") / 2, 156 + var3_14 * 20);
                            } else {
                                this.rd.drawString(this.maker[var3_14], 300 - this.ftm.stringWidth(this.maker[var3_14]) / 2, 156 + var3_14 * 20);
                            }
                            if (this.nad[var3_14] > 1) {
                                if (this.ovbutton("" + this.nad[var3_14] + " Players", 400, 156 + var3_14 * 20)) {
                                    var6_58 = "[ " + this.mycars[var3_14] + " ]  has been added by the following players to their accounts:     \n\n";
                                    var7_86 = 0;
                                    for (var8_97 = 0; var8_97 < this.nad[var3_14]; ++var8_97) {
                                        if (++var7_86 == 17) {
                                            var6_58 = var6_58 + "\n";
                                            var7_86 = 1;
                                        }
                                        var6_58 = var6_58 + this.addeda[var3_14][var8_97];
                                        if (var8_97 == this.nad[var3_14] - 1) continue;
                                        if (var8_97 != this.nad[var3_14] - 2) {
                                            var6_58 = var6_58 + ", ";
                                            continue;
                                        }
                                        if (var7_86 == 16) {
                                            var6_58 = var6_58 + "\nand ";
                                            var7_86 = 0;
                                            continue;
                                        }
                                        var6_58 = var6_58 + " and ";
                                    }
                                    var6_58 = var6_58 + "\n \n \n";
                                    JOptionPane.showMessageDialog(null, var6_58, "Car Maker", 1);
                                }
                            } else {
                                this.rd.setColor(new Color(0, 0, 64));
                                this.rd.drawString("None", 400 - this.ftm.stringWidth("None") / 2, 156 + var3_14 * 20);
                            }
                            if (this.pubt[var3_14] == 0) {
                                this.rd.setColor(new Color(0, 0, 64));
                                this.rd.drawString("Private", 500 - this.ftm.stringWidth("Private") / 2, 156 + var3_14 * 20);
                            }
                            if (this.pubt[var3_14] == 1) {
                                this.rd.setColor(new Color(0, 0, 64));
                                this.rd.drawString("Public", 500 - this.ftm.stringWidth("Public") / 2, 156 + var3_14 * 20);
                            }
                            if (this.pubt[var3_14] == 2) {
                                this.rd.setColor(new Color(0, 64, 0));
                                this.rd.drawString("Super Public", 500 - this.ftm.stringWidth("Super Public") / 2, 156 + var3_14 * 20);
                            }
                            if ((this.pubt[var3_14] == 2 || var5_35) && this.ovbutton("Download", 600, 156 + var3_14 * 20)) {
                                var6_59 = 0;
                                for (var7_87 = 0; var7_87 < this.slcar.getItemCount(); ++var7_87) {
                                    if (!this.mycars[var3_14].equals(this.slcar.getItem(var7_87))) continue;
                                    var6_59 = JOptionPane.showConfirmDialog(null, "Replace the local " + this.mycars[var3_14] + " in your 'mycars' folder with the published online copy?", "Car Maker", 0);
                                }
                                if (var6_59 == 0) {
                                    this.setCursor(new Cursor(3));
                                    this.rd.setFont(new Font("Arial", 1, 13));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.setColor(new Color(225, 225, 225));
                                    this.rd.fillRect(11, 141, 679, 401);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawString("Downloading car, please wait...", 350 - this.ftm.stringWidth("Downloading car, please wait...") / 2, 250);
                                    this.repaint();
                                    try {
                                        var7_88 = ("http://multiplayer.needformadness.com/cars/" + this.mycars[var3_14] + ".radq?reqlo=" + (int)(Math.random() * 1000.0) + "").replace(' ', '_');
                                        var8_98 = new URL(var7_88);
                                        var10_110 = var8_98.openConnection().getContentLength();
                                        var11_123 = new DataInputStream(var8_98.openStream());
                                        var12_125 = new byte[var10_110];
                                        var11_123.readFully(var12_125);
                                        if (var12_125[0] == 80 && var12_125[1] == 75 && var12_125[2] == 3) {
                                            var9_109 = new ZipInputStream(new ByteArrayInputStream(var12_125));
                                        } else {
                                            var13_127 = new byte[var10_110 - 40];
                                            for (var14_129 = 0; var14_129 < var10_110 - 40; ++var14_129) {
                                                var15_130 = 20;
                                                if (var14_129 >= 500) {
                                                    var15_130 = 40;
                                                }
                                                var13_127[var14_129] = var12_125[var14_129 + var15_130];
                                            }
                                            var9_109 = new ZipInputStream(new ByteArrayInputStream(var13_127));
                                        }
                                        var13_128 = var9_109.getNextEntry();
                                        if (var13_128 != null) {
                                            var15_131 = new byte[var14_129];
                                            var16_132 = 0;
                                            for (var14_129 = Integer.valueOf(var13_128.getName()).intValue(); var14_129 > 0; var14_129 -= var17_133) {
                                                var17_133 = var9_109.read(var15_131, var16_132, var14_129);
                                                var16_132 += var17_133;
                                            }
                                            var18_135 = new String(var15_131);
                                            var18_135 = var18_135 + "\n";
                                            var19_137 = "";
                                            var20_138 = 0;
                                            var21_139 = var18_135.indexOf("\n", 0);
                                            while (var21_139 != -1 && var20_138 < var18_135.length()) {
                                                var22_140 = var18_135.substring(var20_138, var21_139).trim();
                                                var20_138 = var21_139 + 1;
                                                var21_139 = var18_135.indexOf("\n", var20_138);
                                                if (!var22_140.startsWith("carmaker(") && !var22_140.startsWith("publish(")) {
                                                    var19_137 = var19_137 + "" + (String)var22_140 + "\n";
                                                    continue;
                                                }
                                                var19_137 = var19_137.trim();
                                                var19_137 = var19_137 + "\n";
                                            }
                                            var19_137 = var19_137.trim();
                                            var19_137 = var19_137 + "\n\n";
                                            var22_140 = new File("" + Madness.fpath + "mycars/");
                                            if (!var22_140.exists()) {
                                                var22_140.mkdirs();
                                            }
                                            var22_140 = new File("" + Madness.fpath + "mycars/" + this.mycars[var3_14] + ".rad");
                                            var23_141 = new BufferedWriter(new FileWriter((File)var22_140));
                                            var23_141.write(var19_137);
                                            var23_141.close();
                                            var23_141 = null;
                                            var9_109.close();
                                            if (this.carname.equals(this.mycars[var3_14])) {
                                                this.editor.setText(var19_137);
                                                this.lastedo = var19_137;
                                            }
                                            this.setCursor(new Cursor(0));
                                            JOptionPane.showMessageDialog(null, "" + this.mycars[var3_14] + " has been successfully downloaded!", "Car Maker", 1);
                                            break block500;
                                        }
                                        JOptionPane.showMessageDialog(null, "Unable to download car.  Unknown Error!     \nPlease try again later.", "Car Maker", 1);
                                    }
                                    catch (Exception var7_89) {
                                        JOptionPane.showMessageDialog(null, "Unable to download car.  Unknown Error!     \nPlease try again later.", "Car Maker", 1);
                                    }
                                }
                            }
                        } else {
                            this.rd.drawString("-    Error Loading this car's info!    -", 350 - this.ftm.stringWidth("-    Error Loading this car's info!    -") / 2, 156 + var3_14 * 20);
                        }
                    }
                    if (!this.ovbutton("X", 665, 156 + var3_14 * 20) || JOptionPane.showConfirmDialog(null, "Remove " + this.mycars[var3_14] + " from your account?", "Car Maker", 0) != 0) continue;
                    this.setCursor(new Cursor(3));
                    var4_27 = -1;
                    try {
                        var5_36 = new Socket("multiplayer.needformadness.com", 7061);
                        var6_60 = new BufferedReader(new InputStreamReader(var5_36.getInputStream()));
                        var7_90 = new PrintWriter(var5_36.getOutputStream(), true);
                        var7_90.println("9|" + this.tnick.getText() + "|" + this.tpass.getText() + "|" + this.mycars[var3_14] + "|");
                        var8_99 = var6_60.readLine();
                        if (var8_99 != null) {
                            var4_27 = this.servervalue(var8_99, 0);
                        }
                        var5_36.close();
                    }
                    catch (Exception var5_37) {
                        var4_27 = -1;
                    }
                    if (var4_27 == 0) {
                        this.logged = 1;
                        continue;
                    }
                    this.setCursor(new Cursor(0));
                    JOptionPane.showMessageDialog(null, "Failed to remove " + this.mycars[var3_14] + " from your account.  Unknown Error!     \nPlease try again later.", "Car Maker", 1);
lbl-1000:
                    // 2 sources

                    {
                        continue;
                    }
                }
            }
lbl2040:
            // 4 sources

            if (this.tabed != this.tab) {
                this.tabed = this.tab;
            }
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.fillRect(0, 0, 700, 25);
            if (!this.onbtgame) {
                this.rd.drawImage(this.btgame[0], 520, 0, null);
            } else {
                this.rd.drawImage(this.btgame[1], 520, 0, null);
            }
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
            var3_3 = new String[]{"Car", "Code Edit", "3D Edit", "Publish"};
            var4_15 = new int[]{0, 0, 100, 90};
            var5_28 = new int[]{0, 25, 25, 0};
            var6_38 = 4;
            if (this.carname.equals("") || !this.loadedfile || this.sfase != 0) {
                this.tab = 0;
                var6_38 = 1;
            }
            for (var7_91 = 0; var7_91 < var6_38; ++var7_91) {
                this.rd.setColor(new Color(170, 170, 170));
                if (this.xm > var4_15[0] && this.xm < var4_15[3] && this.ym > 0 && this.ym < 25) {
                    this.rd.setColor(new Color(200, 200, 200));
                }
                if (this.tab == var7_91) {
                    this.rd.setColor(new Color(225, 225, 225));
                }
                this.rd.fillPolygon(var4_15, var5_28, 4);
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawString(var3_3[var7_91], var7_91 * 100 + 45 - this.ftm.stringWidth(var3_3[var7_91]) / 2, 17);
                if (this.xm > var4_15[0] && this.xm < var4_15[3] && this.ym > 0 && this.ym < 25 && this.mouses == -1) {
                    if (this.tab != var7_91 && this.tab == 1) {
                        this.savefile();
                    }
                    this.tab = var7_91;
                }
                var8_100 = 0;
                while (var8_100 < 4) {
                    v13 = var8_100++;
                    var4_15[v13] = var4_15[v13] + 100;
                }
            }
            this.drawms();
            this.ctachm();
            this.repaint();
            if (this.exwist) continue;
            try {
                Thread.sleep(var2_2);
            }
            catch (InterruptedException var7_92) {}
        }
        this.rd.dispose();
        System.gc();
        if (Madness.endadv == 2) {
            Madness.advopen();
        }
    }

    public void ctachm() {
        int n;
        int n2;
        int n3;
        Object object;
        Object object2;
        int n4;
        Object object3;
        Object object4;
        Object object5;
        Object object6;
        int n5;
        int n6 = -1;
        for (n5 = 0; n5 < this.btn; ++n5) {
            this.pessd[n5] = Math.abs(this.xm - this.bx[n5]) < this.bw[n5] / 2 + 12 && Math.abs(this.ym - this.by[n5]) < 14 && this.mouses == 1;
            if (Math.abs(this.xm - this.bx[n5]) >= this.bw[n5] / 2 + 12 || Math.abs(this.ym - this.by[n5]) >= 14 || this.mouses != -1) continue;
            n6 = n5;
        }
        if (this.mouses == -1) {
            this.mouses = 0;
        }
        if (this.tab == 0) {
            if (this.sfase == 0) {
                if (n6 == 0) {
                    this.sfase = 1;
                    n6 = -1;
                    this.hidefields();
                }
                if (n6 == 1) {
                    if (!this.carname.equals("")) {
                        this.srch.setText(this.carname);
                        this.sfase = 2;
                        n6 = -1;
                        this.hidefields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Please Select a Car to Rename!\n", "Car Maker", 1);
                    }
                }
                if (n6 == 2) {
                    this.delcar(this.carname);
                }
                if (n6 == 3) {
                    this.sfase = 3;
                    n6 = -1;
                    this.hidefields();
                }
            }
            if (this.sfase == 1) {
                if (n6 == 0) {
                    this.newcar(this.srch.getText());
                    n6 = -1;
                }
                if (n6 == 1) {
                    this.srch.setText("");
                    this.sfase = 0;
                    n6 = -1;
                    this.hidefields();
                }
            }
            if (this.sfase == 2) {
                if (n6 == 0) {
                    this.rencar(this.srch.getText());
                    n6 = -1;
                }
                if (n6 == 1) {
                    this.srch.setText("");
                    this.sfase = 0;
                    n6 = -1;
                    this.hidefields();
                }
            }
            if (this.sfase == 3) {
                if (n6 == 0) {
                    File file = null;
                    object6 = new FileDialog(new Frame(), "Car Maker - Wavefront OBJ Import");
                    ((FileDialog)object6).setFile("*.obj");
                    ((FileDialog)object6).setMode(0);
                    ((Dialog)object6).setVisible(true);
                    try {
                        if (((FileDialog)object6).getFile() != null) {
                            file = new File("" + ((FileDialog)object6).getDirectory() + "" + ((FileDialog)object6).getFile() + "");
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if (file != null) {
                        block326: {
                            this.setCursor(new Cursor(3));
                            int n7 = 0;
                            if (this.tutok) {
                                n7 = -70;
                            }
                            this.rd.setColor(new Color(225, 225, 225));
                            this.rd.fillRect(116, 246 + n7, 468, 50);
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.setFont(new Font("Arial", 1, 13));
                            this.ftm = this.rd.getFontMetrics();
                            this.rd.drawString("Reading " + file.getName() + ", please wait...", 350 - this.ftm.stringWidth("Reading " + file.getName() + ", please wait...") / 2, 276 + n7);
                            this.repaint();
                            object5 = new int[6000];
                            object4 = new int[6000];
                            object3 = new int[6000];
                            n4 = 0;
                            object2 = new int[600][100];
                            object = new int[600];
                            n3 = 0;
                            if (file.exists()) {
                                Object object7;
                                try {
                                    boolean bl;
                                    object7 = new BufferedReader(new FileReader(file));
                                    String string = null;
                                    n2 = 0;
                                    boolean n8 = false;
                                    while ((string = ((BufferedReader)object7).readLine()) != null) {
                                        if (string.startsWith("v ")) {
                                            if (n4 < 6000) {
                                                this.multf10 = true;
                                                object5[n4] = this.objvalue(string, 0);
                                                object4[n4] = this.objvalue(string, 1);
                                                object3[n4] = this.objvalue(string, 2);
                                                ++n4;
                                            } else {
                                                n2 = 1;
                                            }
                                        }
                                        if (!string.startsWith("f ")) continue;
                                        if (n3 < 600) {
                                            this.multf10 = false;
                                            this.objfacend = false;
                                            object[n3] = false;
                                            while (!this.objfacend && object[n3] < 100) {
                                                object2[n3][object[n3]] = this.objvalue(string, (int)object[n3]);
                                                Object object8 = object;
                                                int n9 = n3;
                                                object8[n9] = object8[n9] + true;
                                            }
                                            ++n3;
                                            continue;
                                        }
                                        bl = true;
                                    }
                                    if (n2 != 0) {
                                        JOptionPane.showMessageDialog(null, "Warning!\nThe number of Vertices in file " + file.getName() + " exceeded the maximum of 6000 that the Car Maker can read!     \n\nPlease choose a simpler model to import.\n \n", "Car Maker", 0);
                                    }
                                    if (bl) {
                                        JOptionPane.showMessageDialog(null, "Warning!\nThe number of Faces in file " + file.getName() + " exceeded the maximum of 600 that the Car Maker can read!     \n\nPlease choose a simpler model to import.\n \n", "Car Maker", 0);
                                    }
                                    ((BufferedReader)object7).close();
                                    object7 = null;
                                }
                                catch (Exception exception) {
                                    JOptionPane.showMessageDialog(null, "Unable to load file! Error Deatials:\n" + exception, "Car Maker", 1);
                                }
                                this.rd.setColor(new Color(225, 225, 225));
                                this.rd.fillRect(116, 246 + n7, 468, 50);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.setFont(new Font("Arial", 1, 13));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("Importing " + file.getName() + ", please wait...", 350 - this.ftm.stringWidth("Importing " + file.getName() + ", please wait...") / 2, 276 + n7);
                                this.repaint();
                                this.carname = file.getName();
                                if (this.carname.endsWith(".obj")) {
                                    this.carname = this.carname.substring(0, this.carname.length() - 4);
                                }
                                object7 = "\n// imported car: " + this.carname + "\n---------------------\n\n// Please read the helpful information about importing cars found at:\n// http://www.needformadness.com/developer/extras.html\n\n\n";
                                for (n = 0; n < n3; ++n) {
                                    object7 = (String)object7 + "<p>\nc(200,200,220)\n\n";
                                    for (n2 = 0; n2 < object[n]; ++n2) {
                                        if (object2[n][n2] >= 6000) continue;
                                        Object object9 = object2[n][n2];
                                        object7 = (String)object7 + "p(" + (int)object5[object9] + "," + (int)(-object4[object9]) + "," + (int)object3[object9] + ")\n";
                                    }
                                    object7 = (String)object7 + "</p>\n\n";
                                }
                                object7 = (String)object7 + "\n\n\n\n";
                                file = new File("" + Madness.fpath + "mycars/");
                                if (!file.exists()) {
                                    file.mkdirs();
                                }
                                file = new File("" + Madness.fpath + "mycars/" + this.carname + ".rad");
                                n = 0;
                                if (file.exists()) {
                                    n = JOptionPane.showConfirmDialog(null, "Another car with the name '" + this.carname + "' already exists, replace it?      \n", "Car Maker", 0);
                                }
                                if (n == 0) {
                                    try {
                                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                                        bufferedWriter.write((String)object7);
                                        bufferedWriter.close();
                                        bufferedWriter = null;
                                        if (file.exists()) {
                                            this.sfase = 0;
                                            this.hidefields();
                                            this.tabed = -1;
                                            break block326;
                                        }
                                        this.carname = "";
                                        JOptionPane.showMessageDialog(null, "Failed to create car, unknown reason!\n", "Car Maker", 1);
                                    }
                                    catch (Exception exception) {
                                        this.carname = "";
                                        JOptionPane.showMessageDialog(null, "Unable to create file! Error Deatials:\n" + exception, "Car Maker", 1);
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Error, " + file.getName() + " was not found!", "Car Maker", 1);
                            }
                        }
                        this.setCursor(new Cursor(0));
                    }
                }
                if (n6 == 1) {
                    this.sfase = 4;
                    n6 = -1;
                }
                if (n6 == 2) {
                    this.sfase = 0;
                    n6 = -1;
                }
            }
            if (this.sfase == 4) {
                if (n6 == 0) {
                    File file = null;
                    object6 = new FileDialog(new Frame(), "Car Maker - Wavefront OBJ Import");
                    ((FileDialog)object6).setFile("" + this.carname + ".obj");
                    ((FileDialog)object6).setMode(1);
                    ((Dialog)object6).setVisible(true);
                    try {
                        if (((FileDialog)object6).getFile() != null) {
                            file = new File("" + ((FileDialog)object6).getDirectory() + "" + ((FileDialog)object6).getFile() + "");
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if (file != null) {
                        int n10 = 0;
                        if (file.exists()) {
                            n10 = JOptionPane.showConfirmDialog(null, "File " + file.getName() + " already exists, replace it?      \n", "Car Maker", 0);
                        }
                        if (n10 == 0) {
                            int n11;
                            this.setCursor(new Cursor(3));
                            this.setupo();
                            object5 = new int[6000];
                            object4 = new int[6000];
                            object3 = new int[6000];
                            n4 = 0;
                            object2 = "";
                            for (n11 = 0; n11 < this.o.npl; ++n11) {
                                for (n3 = 0; n3 < this.o.p[n11].n; ++n3) {
                                    boolean bl = false;
                                    for (n = 0; n < n4; ++n) {
                                        if (object5[n] != this.o.p[n11].ox[n3] || object4[n] != this.o.p[n11].oy[n3] || object3[n] != this.o.p[n11].oz[n3]) continue;
                                        bl = true;
                                    }
                                    if (bl || n4 >= 6000) continue;
                                    object5[n4] = this.o.p[n11].ox[n3];
                                    object4[n4] = this.o.p[n11].oy[n3];
                                    object3[n4] = this.o.p[n11].oz[n3];
                                    ++n4;
                                }
                            }
                            for (n11 = 0; n11 < n4; ++n11) {
                                object2 = (String)object2 + "v " + (float)object5[n11] / 10.0f + " " + (float)(-object4[n11]) / 10.0f + " " + (float)object3[n11] / 10.0f + "\n";
                            }
                            for (n11 = 0; n11 < this.o.npl; ++n11) {
                                if (this.o.p[n11].wz != 0) continue;
                                object2 = (String)object2 + "f";
                                for (n3 = 0; n3 < this.o.p[n11].n; ++n3) {
                                    object2 = (String)object2 + " ";
                                    for (int i = 0; i < n4; ++i) {
                                        if (object5[i] != this.o.p[n11].ox[n3] || object4[i] != this.o.p[n11].oy[n3] || object3[i] != this.o.p[n11].oz[n3]) continue;
                                        object2 = (String)object2 + "" + (i + 1);
                                    }
                                }
                                object2 = (String)object2 + "\n";
                            }
                            try {
                                object = new BufferedWriter(new FileWriter(file));
                                ((Writer)object).write((String)object2);
                                ((BufferedWriter)object).close();
                                object = null;
                                if (file.exists()) {
                                    JOptionPane.showMessageDialog(null, "Car has been successfully exported to:\n" + file.getAbsolutePath() + "          \n \n", "Car Maker", 1);
                                    this.sfase = 0;
                                    this.hidefields();
                                    this.tabed = -1;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Failed to export car, unknown reason!\n", "Car Maker", 1);
                                }
                            }
                            catch (Exception exception) {
                                JOptionPane.showMessageDialog(null, "Unable to create exported file! Error Deatials:\n" + exception, "Car Maker", 1);
                            }
                            this.setCursor(new Cursor(0));
                        }
                    }
                }
                if (n6 == 1) {
                    this.sfase = 0;
                    n6 = -1;
                }
            }
        }
        if (this.tab == 1) {
            if (n6 == 0) {
                this.prefs = !this.prefs;
            }
            if (n6 == 1 || n6 == 2) {
                this.savefile();
                if (n6 == 2) {
                    this.tab = 2;
                }
            }
            if (!this.mirror) {
                n5 = 0;
                if (n6 == 4) {
                    if (this.sls != -1 && this.sle != -1 && this.editor.getSelectedText().equals(this.srch.getText())) {
                        this.editor.replaceText(this.rplc.getText(), this.sls, this.sle);
                        this.sls = -1;
                        this.sle = -1;
                        n5 = 1;
                        try {
                            Thread.sleep(100L);
                        }
                        catch (InterruptedException interruptedException) {
                            // empty catch block
                        }
                    }
                    n6 = 3;
                }
                if (n6 == 3 && !this.srch.getText().equals("")) {
                    this.editor.requestFocus();
                    this.sls = this.editor.getText().indexOf(this.srch.getText(), this.editor.getSelectionEnd());
                    if (this.sls != -1) {
                        this.sle = this.sls + this.srch.getText().length();
                        this.editor.select(this.sls, this.sle);
                    } else if (n5 == 0) {
                        JOptionPane.showMessageDialog(null, "Cannot find  '" + this.srch.getText() + "'  from Cursor position    ", "Car Maker", 1);
                    }
                }
            } else {
                if (n6 == 3 || n6 == 4 || n6 == 5) {
                    String string = "" + this.editor.getSelectedText() + "\n";
                    object6 = "\n\n";
                    object6 = this.cntpls == 1 ? (String)object6 + "// Mirror of the polygon above along the " : (String)object6 + "// Mirror of the " + this.cntpls + " polygons above along the ";
                    if (n6 == 3) {
                        object6 = (String)object6 + "X axis:";
                    }
                    if (n6 == 4) {
                        object6 = (String)object6 + "Y axis:";
                    }
                    if (n6 == 5) {
                        object6 = (String)object6 + "Z axis:";
                    }
                    object6 = (String)object6 + "\n\n";
                    int n12 = 0;
                    int n13 = string.indexOf("\n", 0);
                    while (n13 != -1 && n12 < string.length()) {
                        int n14;
                        object4 = string.substring(n12, n13);
                        object4 = ((String)object4).trim();
                        n12 = n13 + 1;
                        n13 = string.indexOf("\n", n12);
                        if (((String)object4).startsWith("fs(-")) {
                            object4 = "fs(" + ((String)object4).substring(4, ((String)object4).length()) + "";
                        } else if (((String)object4).startsWith("fs(")) {
                            object4 = "fs(-" + ((String)object4).substring(3, ((String)object4).length()) + "";
                        }
                        if (n6 == 3) {
                            if (((String)object4).startsWith("p(-")) {
                                object4 = "p(" + ((String)object4).substring(3, ((String)object4).length()) + "";
                            } else if (((String)object4).startsWith("p(")) {
                                object4 = "p(-" + ((String)object4).substring(2, ((String)object4).length()) + "";
                            }
                        }
                        if (n6 == 4 && ((String)object4).startsWith("p(") && (n14 = ((String)object4).indexOf(",", 0)) >= 0) {
                            if (((String)object4).startsWith(",-", n14)) {
                                object4 = "" + ((String)object4).substring(0, n14) + "," + ((String)object4).substring(n14 + 2, ((String)object4).length()) + "";
                            } else if (((String)object4).startsWith(",", n14)) {
                                object4 = "" + ((String)object4).substring(0, n14) + ",-" + ((String)object4).substring(n14 + 1, ((String)object4).length()) + "";
                            }
                        }
                        if (n6 == 5 && ((String)object4).startsWith("p(")) {
                            int n15 = ((String)object4).indexOf(",", 0);
                            if ((n15 = ((String)object4).indexOf(",", n15 + 1)) >= 0) {
                                if (((String)object4).startsWith(",-", n15)) {
                                    object4 = "" + ((String)object4).substring(0, n15) + "," + ((String)object4).substring(n15 + 2, ((String)object4).length()) + "";
                                } else if (((String)object4).startsWith(",", n15)) {
                                    object4 = "" + ((String)object4).substring(0, n15) + ",-" + ((String)object4).substring(n15 + 1, ((String)object4).length()) + "";
                                }
                            }
                        }
                        object6 = (String)object6 + "" + (String)object4 + "\n";
                    }
                    object6 = (String)object6 + "\n// End of mirror";
                    this.editor.insertText((String)object6, this.editor.getSelectionEnd());
                }
                if (n6 == 6) {
                    this.polynum = 0;
                    boolean bl = false;
                    for (n5 = this.editor.getText().lastIndexOf("</p>", this.editor.getSelectionStart()); n5 >= 0; --n5) {
                        if (!bl) {
                            n5 = this.editor.getText().lastIndexOf("<p>", n5);
                            if (n5 == -1) continue;
                            bl = true;
                            ++this.polynum;
                            continue;
                        }
                        n5 = this.editor.getText().lastIndexOf("</p>", n5);
                        if (n5 == -1) continue;
                        bl = false;
                    }
                    this.prflk = 0;
                    this.tab = 2;
                }
            }
            n6 = -1;
        }
        if (this.tab == 2) {
            n5 = 0;
            if (this.dtab == 1) {
                if (this.o.colok != 2) {
                    if (n6 == 0) {
                        JOptionPane.showMessageDialog(null, "Car Maker will attempt now to find the first and second colors automatically.\nPlease make sure that they are the correct colors!\n\nPlease note that these are also the colors that will be editable in the multiplayer game.      ", "Car Maker", 1);
                        String string = "" + this.editor.getText() + "\n";
                        int n16 = 0;
                        int n17 = string.indexOf("\n", 0);
                        int n18 = 0;
                        object3 = "";
                        String string2 = "";
                        while (n17 != -1 && n16 < string.length() && n18 != 2) {
                            object2 = string.substring(n16, n17);
                            object2 = ((String)object2).trim();
                            n16 = n17 + 1;
                            n17 = string.indexOf("\n", n16);
                            if (!((String)object2).startsWith("c(")) continue;
                            object = ((String)object2).substring(1, ((String)object2).indexOf(")") + 1);
                            if (n18 == 1 && !((String)object).equals(string2)) {
                                object3 = (String)object3 + "2ndColor" + (String)object + "\n\n\n";
                                n18 = 2;
                            }
                            if (n18 != 0) continue;
                            string2 = object;
                            object3 = "1stColor" + (String)object + "\n";
                            n18 = 1;
                        }
                        if (n18 == 0) {
                            object3 = "1stColor(255,0,0)\n2ndColor(0,0,255)\n\n\n";
                            n18 = 2;
                        }
                        if (n18 == 1) {
                            object3 = (String)object3 + "2ndColor(0,0,255)\n\n\n";
                            n18 = 2;
                        }
                        int n19 = this.editor.getText().indexOf("<p>", 0);
                        this.editor.insertText((String)object3, n19);
                        this.editor.select(n19, n19 + ((String)object3).length() - 2);
                        this.breakbond = true;
                        this.tab = 1;
                    }
                    n5 = 1;
                } else {
                    if (n6 == 0) {
                        int n20;
                        this.ofcol = "(" + this.o.fcol[0] + "," + this.o.fcol[1] + "," + this.o.fcol[2] + ")";
                        int n21 = n20 = this.editor.getText().indexOf(this.ofcol, 0);
                        while (n20 != -1) {
                            this.editor.replaceText(this.fcol, n20, n20 + this.ofcol.length());
                            n20 = this.editor.getText().indexOf(this.ofcol, n20 + 1);
                        }
                        this.ofcol = this.fcol;
                        this.editor.select(n21 - 8, n21 - 8);
                        this.savefile();
                        this.o.fcol[0] = Color.getHSBColor(this.fhsb[0], this.fhsb[2], this.fhsb[1]).getRed();
                        this.o.fcol[1] = Color.getHSBColor(this.fhsb[0], this.fhsb[2], this.fhsb[1]).getGreen();
                        this.o.fcol[2] = Color.getHSBColor(this.fhsb[0], this.fhsb[2], this.fhsb[1]).getBlue();
                    }
                    if (n6 == 1) {
                        int n22;
                        this.oscol = "(" + this.o.scol[0] + "," + this.o.scol[1] + "," + this.o.scol[2] + ")";
                        int n23 = n22 = this.editor.getText().indexOf(this.oscol, 0);
                        while (n22 != -1) {
                            this.editor.replaceText(this.scol, n22, n22 + this.oscol.length());
                            n22 = this.editor.getText().indexOf(this.oscol, n22 + 1);
                        }
                        this.oscol = this.scol;
                        this.editor.select(n23 - 8, n23 - 8);
                        this.savefile();
                        this.o.scol[0] = Color.getHSBColor(this.shsb[0], this.shsb[2], this.shsb[1]).getRed();
                        this.o.scol[1] = Color.getHSBColor(this.shsb[0], this.shsb[2], this.shsb[1]).getGreen();
                        this.o.scol[2] = Color.getHSBColor(this.shsb[0], this.shsb[2], this.shsb[1]).getBlue();
                    }
                    n5 = 2;
                }
            }
            if (this.dtab == 2) {
                int n24;
                if (n6 == 9) {
                    this.scale[0] = 100;
                    this.scale[1] = 100;
                    this.scale[2] = 100;
                }
                if (n6 == 0 || n6 == 1 || n6 == 6 || n6 == 7 || n6 == 9) {
                    int n25;
                    if (n6 == 0 || n6 == 6) {
                        this.scale[0] = this.scale[0] - 5;
                    }
                    if (n6 == 1 || n6 == 7) {
                        this.scale[0] = this.scale[0] + 5;
                    }
                    if (this.scale[0] < 0) {
                        this.scale[0] = 0;
                    }
                    if ((n25 = this.editor.getText().indexOf("\nScaleX(", 0)) != -1) {
                        int n26 = this.editor.getText().indexOf(")", ++n25);
                        int n27 = this.editor.getText().indexOf("\n", n25);
                        if (n27 > n26) {
                            this.editor.replaceText("ScaleX(" + this.scale[0] + ")", n25, n26 + 1);
                        } else {
                            this.editor.replaceText("ScaleX(" + this.scale[0] + ")", n25, n27);
                        }
                    } else {
                        int n28 = this.editor.getText().indexOf("<p>", 0);
                        int n29 = this.editor.getText().indexOf("\nScale", 0);
                        if (n29 < n28 && n29 != -1) {
                            this.editor.insertText("\nScaleX(" + this.scale[0] + ")", n29);
                        } else {
                            this.editor.insertText("ScaleX(" + this.scale[0] + ")\n\n\n", n28);
                        }
                    }
                }
                if (n6 == 2 || n6 == 3 || n6 == 6 || n6 == 7 || n6 == 9) {
                    int n30;
                    if (n6 == 2 || n6 == 6) {
                        this.scale[1] = this.scale[1] - 5;
                    }
                    if (n6 == 3 || n6 == 7) {
                        this.scale[1] = this.scale[1] + 5;
                    }
                    if (this.scale[1] < 0) {
                        this.scale[1] = 0;
                    }
                    if ((n30 = this.editor.getText().indexOf("\nScaleY(", 0)) != -1) {
                        int n31 = this.editor.getText().indexOf(")", ++n30);
                        int n32 = this.editor.getText().indexOf("\n", n30);
                        if (n32 > n31) {
                            this.editor.replaceText("ScaleY(" + this.scale[1] + ")", n30, n31 + 1);
                        } else {
                            this.editor.replaceText("ScaleY(" + this.scale[1] + ")", n30, n32);
                        }
                    } else {
                        int n33 = this.editor.getText().indexOf("<p>", 0);
                        int n34 = this.editor.getText().indexOf("\nScale", 0);
                        if (n34 < n33 && n34 != -1) {
                            this.editor.insertText("\nScaleY(" + this.scale[1] + ")", n34);
                        } else {
                            this.editor.insertText("ScaleY(" + this.scale[1] + ")\n\n\n", n33);
                        }
                    }
                }
                if (n6 == 4 || n6 == 5 || n6 == 6 || n6 == 7 || n6 == 9) {
                    int n35;
                    if (n6 == 4 || n6 == 6) {
                        this.scale[2] = this.scale[2] - 5;
                    }
                    if (n6 == 5 || n6 == 7) {
                        this.scale[2] = this.scale[2] + 5;
                    }
                    if (this.scale[2] < 0) {
                        this.scale[2] = 0;
                    }
                    if ((n35 = this.editor.getText().indexOf("\nScaleZ(", 0)) != -1) {
                        int n36 = this.editor.getText().indexOf(")", ++n35);
                        int n37 = this.editor.getText().indexOf("\n", n35);
                        if (n37 > n36) {
                            this.editor.replaceText("ScaleZ(" + this.scale[2] + ")", n35, n36 + 1);
                        } else {
                            this.editor.replaceText("ScaleZ(" + this.scale[2] + ")", n35, n37);
                        }
                    } else {
                        int n38 = this.editor.getText().indexOf("<p>", 0);
                        int n39 = this.editor.getText().indexOf("\nScale", 0);
                        if (n39 < n38 && n39 != -1) {
                            this.editor.insertText("\nScaleZ(" + this.scale[2] + ")", n39);
                        } else {
                            this.editor.insertText("ScaleZ(" + this.scale[2] + ")\n\n\n", n38);
                        }
                    }
                }
                if (n6 == 0 || n6 == 1 || n6 == 2 || n6 == 3 || n6 == 4 || n6 == 5 || n6 == 6 || n6 == 7 || n6 == 9) {
                    this.setupo();
                }
                if (n6 == 8) {
                    this.savefile();
                    this.oscale[0] = this.scale[0];
                    this.oscale[1] = this.scale[1];
                    this.oscale[2] = this.scale[2];
                }
                if (n6 == 10 || n6 == 11 || n6 == 12 || n6 == 13 || n6 == 14 || n6 == 15 || n6 == 16 || n6 == 17 || n6 == 18) {
                    try {
                        String string = "" + this.editor.getText() + "\n";
                        String string3 = "";
                        int n40 = 0;
                        int n41 = string.indexOf("\n", 0);
                        while (n41 != -1 && n40 < string.length()) {
                            object3 = string.substring(n40, n41);
                            object3 = ((String)object3).trim();
                            n40 = n41 + 1;
                            n41 = string.indexOf("\n", n40);
                            if (((String)object3).startsWith("p(")) {
                                n4 = ((String)object3).indexOf(",", 0);
                                int n42 = ((String)object3).indexOf(",", n4 + 1);
                                int n43 = ((String)object3).indexOf(")", n42 + 1);
                                if (n4 != -1 && n42 != -1 && n43 != -1) {
                                    n3 = Float.valueOf(((String)object3).substring(2, n4)).intValue();
                                    int n44 = Float.valueOf(((String)object3).substring(n4 + 1, n42)).intValue();
                                    n = Float.valueOf(((String)object3).substring(n42 + 1, n43)).intValue();
                                    if (n6 == 10) {
                                        n2 = n44;
                                        n44 = n;
                                        n = -n2;
                                    }
                                    if (n6 == 11) {
                                        n3 += 10;
                                    }
                                    if (n6 == 12) {
                                        n3 -= 10;
                                    }
                                    if (n6 == 13) {
                                        n2 = n3;
                                        n3 = -n;
                                        n = n2;
                                    }
                                    if (n6 == 14) {
                                        n44 += 10;
                                    }
                                    if (n6 == 15) {
                                        n44 -= 10;
                                    }
                                    if (n6 == 16) {
                                        n2 = n44;
                                        n44 = -n3;
                                        n3 = n2;
                                    }
                                    if (n6 == 17) {
                                        n += 10;
                                    }
                                    if (n6 == 18) {
                                        n -= 10;
                                    }
                                    string3 = string3 + "p(" + n3 + "," + n44 + "," + n + ")" + ((String)object3).substring(n43 + 1, ((String)object3).length()) + "\n";
                                    continue;
                                }
                                string3 = string3 + "" + (String)object3 + "\n";
                                continue;
                            }
                            string3 = string3 + "" + (String)object3 + "\n";
                        }
                        this.editor.setText(string3);
                        this.setupo();
                        this.changed2 = true;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (n6 == 19) {
                    this.editor.setText(this.lastedo);
                    this.setupo();
                    this.changed2 = false;
                }
                if (n6 == 20 && this.changed2 && (n24 = JOptionPane.showConfirmDialog(null, "Saving now will permanently change the point locations & numbers entered in the code!      \n\nContinue?", "Car Maker", 0)) == 0) {
                    this.editor.setText(this.editor.getText().trim() + "\n\n\n\n");
                    this.savefile();
                    this.changed2 = false;
                }
                n5 = 21;
            }
            if (this.dtab == 3) {
                if (n6 == 0 || n6 == 2 || this.defnow) {
                    if (this.defnow) {
                        this.defnow = false;
                        this.repaint();
                        JOptionPane.showMessageDialog(null, "Car Maker will setup default Front and Back Wheels positions and adjustments.\n\nEnter the desired positions and adjustments then press ' Apply ' to view!\nDon't forget to press ' Save ' when finished!", "Car Maker", 1);
                    }
                    int n45 = 0;
                    try {
                        int n46 = Float.valueOf(this.wv[10].getText()).intValue();
                        if (n46 <= 0) {
                            n45 = 1;
                        }
                        if ((n46 = Float.valueOf(this.wv[2].getText()).intValue()) >= 0) {
                            n45 = 2;
                        }
                        if ((n46 = Float.valueOf(this.wv[8].getText()).intValue()) <= 0) {
                            n45 = 3;
                        }
                        if ((n46 = Float.valueOf(this.wv[0].getText()).intValue()) <= 0) {
                            n45 = 4;
                        }
                        if ((n46 = Float.valueOf(this.wv[15].getText()).intValue()) > 40) {
                            this.wv[15].setText("40");
                        }
                        if (n46 < -40) {
                            this.wv[15].setText("-40");
                        }
                        if ((n46 = Float.valueOf(this.wv[7].getText()).intValue()) > 40) {
                            this.wv[7].setText("40");
                        }
                        if (n46 < -40) {
                            this.wv[7].setText("-40");
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if (n45 == 1) {
                        JOptionPane.showMessageDialog(null, "ERROR:\nThe Z location value of the FRONT Wheels must be greater then zero! (it should have a +ve value)\nZ :  '" + this.wv[10].getText() + "'  is less or equal to zero, where it should have +ve value!", "Car Maker", 1);
                    }
                    if (n45 == 2) {
                        JOptionPane.showMessageDialog(null, "ERROR:\nThe Z location value of the BACK Wheels must be smaller then zero! (it should have a -ve value)\nZ :  '" + this.wv[2].getText() + "'  is bigger or equal to zero, where it should have -ve value!", "Car Maker", 1);
                    }
                    if (n45 == 3) {
                        JOptionPane.showMessageDialog(null, "ERROR:\nThe \u00b1X location value of the FRONT or BACK Wheels must be greater then zero! (it should have a +ve value)\n\u00b1X :  '" + this.wv[8].getText() + "'  is less or equal to zero, where it should have +ve value!", "Car Maker", 1);
                    }
                    if (n45 == 4) {
                        JOptionPane.showMessageDialog(null, "ERROR:\nThe \u00b1X location value of the FRONT or BACK Wheels must be greater then zero! (it should have a +ve value)\n\u00b1X :  '" + this.wv[0].getText() + "'  is less or equal to zero, whenr it should have +ve value!", "Car Maker", 1);
                    }
                    if (n45 == 0) {
                        String string = "" + this.editor.getText() + "\n";
                        object5 = "";
                        int n47 = 0;
                        int n48 = string.indexOf("\n", 0);
                        while (n48 != -1 && n47 < string.length()) {
                            String string4 = string.substring(n47, n48);
                            string4 = string4.trim();
                            n47 = n48 + 1;
                            n48 = string.indexOf("\n", n47);
                            if (!(string4.startsWith("rims(") || string4.startsWith("gwgr(") || string4.startsWith("w("))) {
                                object5 = (String)object5 + "" + string4 + "\n";
                                continue;
                            }
                            object5 = ((String)object5).trim();
                            object5 = (String)object5 + "\n";
                        }
                        object5 = ((String)object5).trim();
                        object5 = (String)object5 + "\n\n\ngwgr(" + this.wv[15].getText() + ")\n";
                        String string5 = "140,140,140";
                        if (this.rplc.getText().startsWith("(") && this.rplc.getText().endsWith(")")) {
                            string5 = this.rplc.getText().substring(1, this.rplc.getText().length() - 1);
                        }
                        object5 = (String)object5 + "rims(" + string5 + "," + this.wv[13].getText() + "," + this.wv[14].getText() + ")\n";
                        object5 = (String)object5 + "w(-" + this.wv[8].getText() + "," + this.wv[9].getText() + "," + this.wv[10].getText() + ",11," + this.wv[12].getText() + "," + this.wv[11].getText() + ")\n";
                        object5 = (String)object5 + "w(" + this.wv[8].getText() + "," + this.wv[9].getText() + "," + this.wv[10].getText() + ",11,-" + this.wv[12].getText() + "," + this.wv[11].getText() + ")\n";
                        object5 = (String)object5 + "\ngwgr(" + this.wv[7].getText() + ")\n";
                        string5 = "140,140,140";
                        if (this.srch.getText().startsWith("(") && this.srch.getText().endsWith(")")) {
                            string5 = this.srch.getText().substring(1, this.srch.getText().length() - 1);
                        }
                        object5 = (String)object5 + "rims(" + string5 + "," + this.wv[5].getText() + "," + this.wv[6].getText() + ")\n";
                        object5 = (String)object5 + "w(-" + this.wv[0].getText() + "," + this.wv[1].getText() + "," + this.wv[2].getText() + ",0," + this.wv[4].getText() + "," + this.wv[3].getText() + ")\n";
                        object5 = (String)object5 + "w(" + this.wv[0].getText() + "," + this.wv[1].getText() + "," + this.wv[2].getText() + ",0,-" + this.wv[4].getText() + "," + this.wv[3].getText() + ")\n\n\n\n";
                        this.editor.setText((String)object5);
                        this.forwheels = true;
                        this.setupo();
                        this.forwheels = false;
                        this.aply1 = "" + this.wv[0].getText() + "" + this.wv[1].getText() + "" + this.wv[2].getText() + "" + this.wv[3].getText() + "" + this.wv[4].getText() + "" + this.srch.getText() + "" + this.wv[5].getText() + "" + this.wv[6].getText() + "" + this.wv[7].getText() + "";
                        this.aply2 = "" + this.wv[8].getText() + "" + this.wv[9].getText() + "" + this.wv[10].getText() + "" + this.wv[11].getText() + "" + this.wv[12].getText() + "" + this.rplc.getText() + "" + this.wv[13].getText() + "" + this.wv[14].getText() + "" + this.wv[15].getText() + "";
                        this.aplyd1 = false;
                        this.aplyd2 = false;
                        this.changed2 = true;
                    }
                }
                if (n6 == 1 || n6 == 3) {
                    if (!this.o.errd) {
                        this.savefile();
                        this.changed2 = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "Unable to Save, press  [ Apply ]  to find out why!", "Car Maker", 1);
                    }
                }
                n5 = 4;
            }
            if (this.dtab == 4) {
                if (!this.statdef) {
                    if (n6 == 0) {
                        this.carsel = this.simcar.getSelectedIndex();
                        int n49 = 0;
                        for (int i = 0; i < 5; ++i) {
                            this.stat[i] = this.carstat[this.carsel][i];
                            this.rstat[i] = this.stat[i];
                            n49 += this.stat[i];
                        }
                        this.clsel = 4 - (n49 - 520) / 40;
                        this.cls.select(this.clsel);
                        if (this.simcar.getItemCount() == 16) {
                            this.simcar.add(this.rd, "   ");
                        }
                        this.statdef = true;
                        this.changed2 = true;
                    }
                    n5 = 1;
                } else {
                    int n50;
                    for (n50 = 0; n50 < 5; ++n50) {
                        int n51 = 0;
                        if (n6 == 1 + n50 * 2 && this.stat[n50] < 200 && (n51 = 200 - this.stat[n50]) > 4) {
                            n51 = 4;
                        }
                        if (n6 == n50 * 2 && this.stat[n50] > 16 && (n51 = 16 - this.stat[n50]) < -4) {
                            n51 = -4;
                        }
                        int n52 = 0;
                        while (n51 != 0 && n52 != 5) {
                            n52 = 0;
                            for (int i = 0; i < 5; ++i) {
                                if (!(n50 == i || this.stat[i] > 200 && n51 <= 0 || this.stat[i] < 16 && n51 >= 0 || n51 == 0)) {
                                    if (n51 > 0) {
                                        int n10 = n50;
                                        this.stat[n10] = this.stat[n10] + 1;
                                        int n11 = i;
                                        this.stat[n11] = this.stat[n11] - 1;
                                        --n51;
                                        continue;
                                    }
                                    int n12 = n50;
                                    this.stat[n12] = this.stat[n12] - 1;
                                    int n13 = i;
                                    this.stat[n13] = this.stat[n13] + 1;
                                    ++n51;
                                    continue;
                                }
                                ++n52;
                            }
                        }
                    }
                    if (n6 == 10) {
                        this.carsel = this.simcar.getSelectedIndex();
                        n50 = 0;
                        for (int i = 0; i < 5; ++i) {
                            this.stat[i] = this.carstat[this.carsel][i];
                            n50 += this.stat[i];
                        }
                        this.clsel = 4 - (n50 - 520) / 40;
                        this.cls.select(this.clsel);
                    }
                    if (n6 == 11) {
                        String string = "" + this.editor.getText() + "\n";
                        String string6 = "";
                        int n57 = 0;
                        int n58 = string.indexOf("\n", 0);
                        while (n58 != -1 && n57 < string.length()) {
                            String string7 = string.substring(n57, n58);
                            string7 = string7.trim();
                            n57 = n58 + 1;
                            n58 = string.indexOf("\n", n57);
                            if (!string7.startsWith("stat(")) {
                                string6 = string6 + "" + string7 + "\n";
                                continue;
                            }
                            string6 = string6.trim();
                            string6 = string6 + "\n";
                        }
                        string6 = string6.trim();
                        string6 = string6 + "\n\n\nstat(" + this.stat[0] + "," + this.stat[1] + "," + this.stat[2] + "," + this.stat[3] + "," + this.stat[4] + ")\n\n\n\n";
                        this.editor.setText(string6);
                        this.savefile();
                        for (int i = 0; i < 5; ++i) {
                            this.rstat[i] = this.stat[i];
                        }
                        this.changed2 = false;
                    }
                    if (n6 == 12) {
                        for (int i = 0; i < 5; ++i) {
                            this.stat[i] = this.rstat[i];
                        }
                    }
                    n5 = 13;
                }
            }
            if (this.dtab == 5) {
                if (this.pfase == 0) {
                    int n59;
                    for (n59 = 0; n59 < 4; ++n59) {
                        if (n6 == 1 + n59 * 2) {
                            int n14 = n59;
                            this.phys[n14] = this.phys[n14] + 2;
                            if (this.phys[n59] > 100) {
                                this.phys[n59] = 100;
                            }
                        }
                        if (n6 != n59 * 2) continue;
                        int n15 = n59;
                        this.phys[n15] = this.phys[n15] - 2;
                        if (this.phys[n59] >= 0) continue;
                        this.phys[n59] = 0;
                    }
                    if (n6 == 8) {
                        for (n59 = 0; n59 < 5; ++n59) {
                            this.phys[n59] = (int)(Math.random() * 100.0);
                        }
                    }
                    if (n6 == 9) {
                        for (n59 = 0; n59 < 5; ++n59) {
                            this.phys[n59] = this.rphys[n59];
                        }
                    }
                    if (n6 == 10) {
                        this.pfase = 1;
                        n6 = -1;
                    }
                    n5 = 11;
                }
                if (this.pfase == 1) {
                    int n62;
                    for (n62 = 0; n62 < 6; ++n62) {
                        if (n6 == 1 + n62 * 2) {
                            int n16 = n62 + 5;
                            this.phys[n16] = this.phys[n16] + 2;
                            if (this.phys[n62 + 5] > 100) {
                                this.phys[n62 + 5] = 100;
                            }
                        }
                        if (n6 != n62 * 2) continue;
                        int n17 = n62 + 5;
                        this.phys[n17] = this.phys[n17] - 2;
                        if (this.phys[n62 + 5] >= 0) continue;
                        this.phys[n62 + 5] = 0;
                    }
                    if (n6 == 12) {
                        for (n62 = 0; n62 < 6; ++n62) {
                            this.phys[n62 + 5] = (int)(Math.random() * 100.0);
                        }
                    }
                    if (n6 == 13) {
                        for (n62 = 0; n62 < 6; ++n62) {
                            this.phys[n62 + 5] = this.rphys[n62 + 5];
                        }
                    }
                    if (n6 == 14) {
                        this.pfase = 0;
                        n6 = -1;
                    }
                    if (n6 == 15) {
                        this.pfase = 2;
                        n6 = -1;
                    }
                    n5 = 16;
                }
                if (this.pfase == 2) {
                    int n65;
                    for (n65 = 0; n65 < 3; ++n65) {
                        if (n6 == 1 + n65 * 2) {
                            int n18 = n65;
                            this.crash[n18] = this.crash[n18] + 2;
                            if (this.crash[n65] > 100) {
                                this.crash[n65] = 100;
                            }
                        }
                        if (n6 != n65 * 2) continue;
                        int n19 = n65;
                        this.crash[n19] = this.crash[n19] - 2;
                        if (this.crash[n65] >= 0) continue;
                        this.crash[n65] = 0;
                    }
                    if (n6 == 6) {
                        n65 = (int)(150.0 + 600.0 * Math.random());
                        boolean bl = false;
                        boolean bl2 = false;
                        if (Math.random() > Math.random()) {
                            bl = true;
                        }
                        if (Math.random() > Math.random()) {
                            bl2 = true;
                        }
                        object4 = new int[]{-101, -101, -101, -101};
                        object4[0] = (int)(Math.random() * 4.0);
                        if (Math.random() > Math.random()) {
                            object4[1] = bl2 ? object4[0] + true : object4[0] - true;
                            if (Math.random() > Math.random()) {
                                object4[2] = bl2 ? object4[1] + true : object4[1] - true;
                                if (Math.random() > Math.random()) {
                                    object4[3] = bl2 ? object4[2] + true : object4[2] - true;
                                }
                            }
                        }
                        this.crashup = !(Math.random() > Math.random());
                        for (int i = 0; i < 4; ++i) {
                            if (object4[i] == -101) continue;
                            if (object4[i] >= 4) {
                                Object object10 = object4;
                                int n20 = i;
                                object10[n20] = object10[n20] - 4;
                            }
                            if (object4[i] <= -1) {
                                Object object11 = object4;
                                int n21 = i;
                                object11[n21] = object11[n21] + 4;
                            }
                            if ((n65 -= 50 * i) < 150) {
                                n65 = 150;
                            }
                            if (bl) {
                                this.regx((int)object4[i], n65, false);
                                continue;
                            }
                            this.regz((int)object4[i], n65, false);
                        }
                        if (this.hitmag < 17000) {
                            this.o.xz = this.crashleft ? (this.o.xz += 22) : (this.o.xz -= 22);
                        }
                    }
                    if (n6 == 8) {
                        this.crashup = !(Math.random() > Math.random());
                        this.roofsqsh((int)(230.0 + Math.random() * 80.0));
                    }
                    if (n6 == 9 || n6 == 7) {
                        this.setupo();
                        this.crashleft = !(Math.random() > Math.random());
                    }
                    if (n6 == 10) {
                        for (n65 = 0; n65 < 3; ++n65) {
                            this.crash[n65] = this.rcrash[n65];
                        }
                    }
                    if (n6 == 11) {
                        this.setupo();
                        this.pfase = 1;
                        n6 = -1;
                    }
                    if (n6 == 12) {
                        if (this.crashok) {
                            this.setupo();
                            this.pfase = 3;
                            n6 = -1;
                        } else {
                            JOptionPane.showMessageDialog(null, this.usage[11], "Car Maker", 1);
                        }
                    }
                    n5 = 13;
                }
                if (this.pfase == 3) {
                    for (int i = 0; i < 5; ++i) {
                        if (n6 == i) {
                            for (int j = 0; j < 5; ++j) {
                                for (int k = 0; k < 5; ++k) {
                                    this.engs[k][j].stop();
                                }
                            }
                            this.engs[this.engsel][i].loop();
                            this.engon = true;
                        }
                        if (n6 == 5) {
                            for (int j = 0; j < 5; ++j) {
                                for (int k = 0; k < 5; ++k) {
                                    this.engs[k][j].stop();
                                }
                            }
                            this.engon = false;
                        }
                        if (n6 == 6) {
                            this.pfase = 2;
                            n6 = -1;
                            this.engine.hide();
                        }
                        if (n6 != 7) continue;
                        this.pfase = 4;
                        n6 = -1;
                        this.engine.hide();
                    }
                    n5 = 8;
                }
            }
            if (this.dtab == 6) {
                if (!this.rateh) {
                    if (n6 == 0 && this.checko("Test Drive")) {
                        Madness.testcar = this.carname;
                        Madness.testdrive = this.witho.getSelectedIndex() + 1;
                        Madness.game();
                    }
                    n5 = 1;
                    if (this.tested) {
                        if (n6 == 1) {
                            this.dtab = 4;
                            n6 = -1;
                        }
                        if (n6 == 2) {
                            this.dtab = 5;
                            n6 = -1;
                        }
                        if (n6 == 3) {
                            this.rateh = true;
                            this.hidefields();
                        }
                        n5 = 4;
                    }
                } else {
                    if (n6 == 0) {
                        this.handling -= 2;
                        if (this.handling < 50) {
                            this.handling = 50;
                        }
                    }
                    if (n6 == 1) {
                        this.handling += 2;
                        if (this.handling > 200) {
                            this.handling = 200;
                        }
                    }
                    if (n6 == 2) {
                        String string = "" + this.editor.getText() + "\n";
                        String string8 = "";
                        int n70 = 0;
                        int n71 = string.indexOf("\n", 0);
                        while (n71 != -1 && n70 < string.length()) {
                            String string9 = string.substring(n70, n71);
                            string9 = string9.trim();
                            n70 = n71 + 1;
                            n71 = string.indexOf("\n", n70);
                            if (!string9.startsWith("handling(")) {
                                string8 = string8 + "" + string9 + "\n";
                                continue;
                            }
                            string8 = string8.trim();
                            string8 = string8 + "\n";
                        }
                        string8 = string8.trim();
                        string8 = string8 + "\n\n\nhandling(" + this.handling + ")\n\n\n\n";
                        this.editor.setText(string8);
                        this.savefile();
                        this.rateh = false;
                    }
                    if (n6 == 3) {
                        this.rateh = false;
                    }
                    n5 = 4;
                }
            }
            if (n6 == n5) {
                for (int i = 0; i < this.o.npl; ++i) {
                    Color.RGBtoHSB(this.o.p[i].c[0], this.o.p[i].c[1], this.o.p[i].c[2], this.o.p[i].hsb);
                    if (this.o.p[i].gr != -13) continue;
                    this.o.p[i].gr = 1;
                }
                this.polynum = -1;
            }
            n6 = -1;
        }
        if (this.tab == 3) {
            Object object11;
            if (n6 == 0) {
                if (this.logged == 0) {
                    JOptionPane.showMessageDialog(null, "Please login to retrieve your account first before publishing!", "Car Maker", 1);
                }
                if ((this.logged == 3 || this.logged == -1) && this.checko("Publishing")) {
                    int n72;
                    n5 = 0;
                    for (n72 = 0; n72 < this.pubitem.no; ++n72) {
                        if (!this.pubitem.opts[n72].equals(this.carname)) continue;
                        n5 = JOptionPane.showConfirmDialog(null, "Replace your already online car '" + this.carname + "' with this one?", "Car Maker", 0);
                    }
                    if (n5 == 0) {
                        this.setCursor(new Cursor(3));
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(225, 225, 225));
                        this.rd.fillRect(11, 141, 679, 401);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Connecting to Server...", 350 - this.ftm.stringWidth("Connecting to Server...") / 2, 250);
                        this.repaint();
                        n72 = 0;
                        object11 = "" + this.editor.getText() + "\n";
                        int n73 = 0;
                        int n74 = ((String)object11).indexOf("\n", 0);
                        while (n74 != -1 && n73 < ((String)object11).length()) {
                            n73 = n74 + 1;
                            n74 = ((String)object11).indexOf("\n", n73);
                            ++n72;
                        }
                        this.justpubd = this.carname;
                        int n75 = -1;
                        try {
                            Socket socket = new Socket("multiplayer.needformadness.com", 7061);
                            object2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            object = new PrintWriter(socket.getOutputStream(), true);
                            ((PrintWriter)object).println("10|" + this.tnick.getText() + "|" + this.tpass.getText() + "|" + this.carname + "|" + this.pubtyp.getSelectedIndex() + "|");
                            String string = ((BufferedReader)object2).readLine();
                            if (string != null) {
                                n75 = this.servervalue(string, 0);
                            }
                            if (n75 == 0) {
                                int n76 = 0;
                                object11 = "" + this.editor.getText() + "\n";
                                n73 = 0;
                                n74 = ((String)object11).indexOf("\n", 0);
                                while (n74 != -1 && n73 < ((String)object11).length()) {
                                    String string10 = ((String)object11).substring(n73, n74);
                                    string10 = string10.trim();
                                    ((PrintWriter)object).println(string10);
                                    n73 = n74 + 1;
                                    n74 = ((String)object11).indexOf("\n", n73);
                                    ++n76;
                                    this.rd.setColor(new Color(225, 225, 225));
                                    this.rd.fillRect(11, 141, 679, 401);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawString("Publishing Car...", 350 - this.ftm.stringWidth("Publishing Car...") / 2, 250);
                                    this.rd.setColor(new Color(119, 147, 191));
                                    this.rd.fillRect(250, 270, (int)((float)n76 / (float)n72 * 200.0f), 7);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawRect(250, 270, 200, 7);
                                    this.repaint();
                                    try {
                                        Thread.sleep(10L);
                                    }
                                    catch (InterruptedException interruptedException) {}
                                }
                                ((PrintWriter)object).println("QUITX1111");
                                this.rd.setColor(new Color(225, 225, 225));
                                this.rd.fillRect(11, 141, 679, 401);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawString("Creating the car online...", 350 - this.ftm.stringWidth("Creating the car online...") / 2, 250);
                                this.rd.drawString("This may take a couple of minutes, please wait...", 350 - this.ftm.stringWidth("This may take a couple of minutes, please wait...") / 2, 280);
                                this.repaint();
                                string = ((BufferedReader)object2).readLine();
                                if (string != null) {
                                    n75 = this.servervalue(string, 0);
                                }
                            }
                            socket.close();
                        }
                        catch (Exception exception) {
                            n75 = -1;
                        }
                        this.setCursor(new Cursor(0));
                        boolean bl = false;
                        if (n75 == 0) {
                            this.logged = 1;
                            bl = true;
                        }
                        if (n75 == 3) {
                            JOptionPane.showMessageDialog(null, "Unable to publish car.\nReason:\nCar name is too large.  Please rename your car.  Car name must be less then 15 characters.", "Car Maker", 1);
                            bl = true;
                        }
                        if (n75 == 4) {
                            JOptionPane.showMessageDialog(null, "Unable to publish car.\nReason:  Car name used (" + this.carname + ").\nThe name '" + this.carname + "' is already used by another published car.  Please rename your car.", "Car Maker", 1);
                            bl = true;
                        }
                        if (n75 == 6) {
                            JOptionPane.showMessageDialog(null, "Error Creating Car!\nReason:\nError loading 3D model!  Format maybe incorrect!", "Car Maker", 1);
                            bl = true;
                        }
                        if (n75 == 7) {
                            JOptionPane.showMessageDialog(null, "Error Creating Car!\nReason:\nFirst and Second colors not defined yet!\nPlease go to the 'Color Edit' tab to define the colors.", "Car Maker", 1);
                            bl = true;
                        }
                        if (n75 == 8) {
                            JOptionPane.showMessageDialog(null, "Error Creating Car!\nReason:\nCar Wheels not defined or not defined correctly!\nPlease go to the \u2018Wheels\u2019 tab and use  [ Apply ]  and  [ Save ]  to define correctly.", "Car Maker", 1);
                            bl = true;
                        }
                        if (n75 == 9) {
                            JOptionPane.showMessageDialog(null, "Error Creating Car!\nReason:\nNo car seems to be designed!\nYou have not built a car yet please go to the \u2018Car\u2019 tab to find the tutorial on how to build a car.", "Car Maker", 1);
                            bl = true;
                        }
                        if (n75 == 10) {
                            JOptionPane.showMessageDialog(null, "Error Creating Car!\nReason:\nCar contains too many polygons (pieces).\nNumber of polygons used need to be less then 210.\nPlease use the counter in the \u2018Code Edit\u2019 to decrease the number of polygons (pieces).", "Car Maker", 1);
                            bl = true;
                        }
                        if (n75 == 11) {
                            JOptionPane.showMessageDialog(null, "Error Creating Car!\nReason:\nCar scale size is too large!\nPlease use the \u2018Scale All\u2019 option in the \u2018Scale & Align\u2019 tab to resize your car to suitable size.       \nCompare it to other NFM cars using the \u2018Compare Car...\u2019 option.\nCurrently you car needs to be scaled down by " + (int)(((float)this.o.maxR / 400.0f - 1.0f) * 100.0f) + "%.\n", "Car Maker", 1);
                            bl = true;
                        }
                        if (n75 == 12) {
                            JOptionPane.showMessageDialog(null, "Error Creating Car!\nReason:\nCar scale size is too small!\nPlease use the \u2018Scale All\u2019 option in the \u2018Scale & Align\u2019 tab to resize your car to suitable size.       \nCompare it to other NFM cars using the \u2018Compare Car...\u2019 option.\nCurrently you car needs to be scaled up by " + (int)((120.0f / (float)this.o.maxR - 1.0f) * 100.0f) + "%.\n", "Car Maker", 1);
                            bl = true;
                        }
                        if (n75 == 13) {
                            JOptionPane.showMessageDialog(null, "Error Creating Car!\nReason:\nCar Stats & Class not defined correctly!\nPlease go to the 'Stats & Class' tab to define stats and don't forget to press  [ Save ]  when finished.\n", "Car Maker", 1);
                            bl = true;
                        }
                        if (n75 == 14) {
                            JOptionPane.showMessageDialog(null, "Error Creating Car!\nReason:\nCar Physics not defined correctly!\nPlease go to the 'Physics' tab and complete the car physics definition until it is saved.\n", "Car Maker", 1);
                            bl = true;
                        }
                        if (n75 == 15) {
                            JOptionPane.showMessageDialog(null, "Error Creating Car!\nReason:\nCar Handling not rated.\nPlease Test Drive your car to rate its handling before publishing!\n", "Car Maker", 1);
                            bl = true;
                        }
                        if (n75 > 15) {
                            JOptionPane.showMessageDialog(null, "Unable to publish car fully!  Unknown Error.  Please try again later.\n", "Car Maker", 1);
                            bl = true;
                        }
                        if (!bl) {
                            JOptionPane.showMessageDialog(null, "Unable to publish car!  Unknown Error.\n", "Car Maker", 1);
                        }
                    }
                }
            }
            if (this.logged == 0) {
                if (n6 == 1) {
                    this.setCursor(new Cursor(3));
                    n5 = -1;
                    try {
                        Socket socket = new Socket("multiplayer.needformadness.com", 7061);
                        object11 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        object5 = new PrintWriter(socket.getOutputStream(), true);
                        ((PrintWriter)object5).println("1|" + this.tnick.getText().toLowerCase() + "|" + this.tpass.getText() + "|");
                        object4 = ((BufferedReader)object11).readLine();
                        if (object4 != null) {
                            n5 = this.servervalue((String)object4, 0);
                        }
                        socket.close();
                    }
                    catch (Exception exception) {
                        n5 = -1;
                    }
                    if (n5 == 0 || n5 == 3 || n5 > 10) {
                        this.tnick.hide();
                        this.tpass.hide();
                        this.logged = 1;
                        this.savesettings();
                    }
                    if (n5 == 1 || n5 == 2) {
                        this.setCursor(new Cursor(0));
                        JOptionPane.showMessageDialog(null, "Sorry.  Incorrect Nickname or Password!", "Car Maker", 0);
                    }
                    if (n5 == -167) {
                        this.setCursor(new Cursor(0));
                        JOptionPane.showMessageDialog(null, "Sorry.  Your trial account cannot publish cars.  Please upgrade to a full account!   ", "Car Maker", 0);
                    }
                    if (n5 == -1) {
                        this.setCursor(new Cursor(0));
                        JOptionPane.showMessageDialog(null, "Unable to connect to server at this moment, please try again later.", "Car Maker", 1);
                    }
                }
                if (n6 == 2) {
                    Madness.openurl("http://multiplayer.needformadness.com/register.html?ref=game");
                }
                if (n6 == 3) {
                    Madness.openurl("http://multiplayer.needformadness.com/edit.pl");
                }
            }
        }
    }

    public void setupo() {
        this.o = new ContO(this.editor.getText().getBytes(), this.m, this.t);
        this.o.x = this.ox;
        this.o.y = this.oy;
        this.o.z = this.oz;
        this.o.xz = this.oxz;
        this.o.xy = this.oxy;
        this.o.zy = this.ozy;
        this.o.shadow = true;
        this.o.tnt = 0;
        this.o.disp = 0;
        this.o.disline = 7;
        this.o.grounded = 1.0f;
        this.o.noline = false;
        this.o.decor = false;
        if (this.o.errd && (!this.o.err.startsWith("Wheels Error:") || this.forwheels)) {
            JOptionPane.showMessageDialog(null, this.o.err, "Car Maker", 0);
        }
        if (this.o.maxR == 0) {
            this.o.maxR = 100;
        }
        this.squash = 0;
        this.hitmag = 0;
    }

    public void loadfile() {
        this.loadedfile = false;
        this.lastedo = "";
        try {
            File file = new File("" + Madness.fpath + "mycars/" + this.carname + ".rad");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String string = null;
            while ((string = bufferedReader.readLine()) != null) {
                this.lastedo = this.lastedo + "" + string + "\n";
            }
            this.loadedfile = true;
            bufferedReader.close();
            bufferedReader = null;
        }
        catch (Exception exception) {
            this.loadedfile = false;
            this.lastedo = "";
            JOptionPane.showMessageDialog(null, "Unable to load file! Error Deatials:\n" + exception, "Car Maker", 1);
        }
        this.editor.setText(this.lastedo);
    }

    public void savefile() {
        if (!this.editor.getText().equals("")) {
            try {
                File file = new File("" + Madness.fpath + "mycars/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File("" + Madness.fpath + "mycars/" + this.carname + ".rad");
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                bufferedWriter.write(this.editor.getText());
                bufferedWriter.close();
                bufferedWriter = null;
                this.changed = false;
                this.lastedo = this.editor.getText();
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Unable to save file! Error Deatials:\n" + exception, "Car Maker", 1);
            }
        }
        this.savesettings();
    }

    public void newcar(String string) {
        if (string.equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter a Car Name!\n", "Car Maker", 1);
        } else {
            String string2 = "\n// car: " + string + "\n---------------------\n\n// To start making you car you must start by reading the tutorial at:\n// http://www.needformadness.com/developer/simplecar.html\n\n\n<p>\nc(100,200,100)\n\np(-40,-50,80)\np(-40,-50,-70)\np(40,-50,-70)\np(40,-50,80)\n</p>\n\n<p>\nc(100,150,200)\n\np(-40,-20,-100)\np(-40,-50,-70)\np(40,-50,-70)\np(40,-20,-100)\n</p>\n\n\n\n";
            try {
                File file = new File("" + Madness.fpath + "mycars/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                this.carname = string;
                file = new File("" + Madness.fpath + "mycars/" + this.carname + ".rad");
                if (!file.exists()) {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                    bufferedWriter.write(string2);
                    bufferedWriter.close();
                    bufferedWriter = null;
                    if (file.exists()) {
                        this.sfase = 0;
                        this.hidefields();
                        this.tabed = -1;
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to create car, unknown reason!\n", "Car Maker", 1);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "A car with the name '" + this.carname + "' already exists, please choose another name!\n", "Car Maker", 1);
                }
            }
            catch (Exception exception) {
                this.carname = "";
                JOptionPane.showMessageDialog(null, "Unable to create file! Error Deatials:\n" + exception, "Car Maker", 1);
            }
        }
    }

    public void delcar(String string) {
        if (string.equals("")) {
            JOptionPane.showMessageDialog(null, "Please Select a Car to Delete!\n", "Car Maker", 1);
        } else {
            int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete car :  " + string + " ?  ", "Car Maker", 0);
            if (n == 0) {
                try {
                    File file = new File("" + Madness.fpath + "mycars/" + string + ".rad");
                    file.delete();
                    this.slcar.remove(string);
                    this.slcar.select(0);
                }
                catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Unable to delete file! Error Deatials:\n" + exception, "Car Maker", 1);
                }
            }
        }
    }

    public void rencar(String string) {
        if (string.equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter a New Car Name!\n", "Car Maker", 1);
        } else {
            try {
                File file = new File("" + Madness.fpath + "mycars/" + this.carname + ".rad");
                File file2 = new File("" + Madness.fpath + "mycars/" + string + ".rad");
                if (file.renameTo(file2)) {
                    this.carname = string;
                    this.sfase = 0;
                    this.hidefields();
                    this.tabed = -1;
                } else {
                    JOptionPane.showMessageDialog(null, "Unable to rename car to: '" + string + "', possible reason: Car name already used!\n", "Car Maker", 1);
                }
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Unable to rename file! Error Deatials:\n" + exception, "Car Maker", 1);
            }
        }
    }

    public void loadsettings() {
        try {
            File file = new File("" + Madness.fpath + "mycars/settings.data");
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String string = bufferedReader.readLine();
                if (string != null) {
                    this.carname = this.scar = string;
                }
                if ((string = bufferedReader.readLine()) != null) {
                    this.suser = string;
                    if (!this.suser.equals("Horaks")) {
                        this.tnick.setText(this.suser);
                    }
                }
                if ((string = bufferedReader.readLine()) != null) {
                    this.cfont = this.sfont = string;
                }
                if ((string = bufferedReader.readLine()) != null) {
                    this.cthm = this.sthm = Float.valueOf(string).intValue();
                }
                bufferedReader.close();
                bufferedReader = null;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void savesettings() {
        if (!(this.scar.equals(this.carname) && this.suser.equals(this.tnick.getText()) && this.sfont.equals(this.cfont) && this.cthm == this.sthm)) {
            String string = "" + this.carname + "\n" + this.tnick.getText() + "\n" + this.cfont + "\n" + this.cthm + "\n\n";
            this.scar = this.carname;
            this.suser = this.tnick.getText();
            this.sfont = this.cfont;
            this.sthm = this.cthm;
            try {
                File file = new File("" + Madness.fpath + "mycars/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File("" + Madness.fpath + "mycars/settings.data");
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                bufferedWriter.write(string);
                bufferedWriter.close();
                bufferedWriter = null;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public boolean checko(String string) {
        this.loadfile();
        this.setupo();
        if (this.o.colok < 2) {
            JOptionPane.showMessageDialog(null, "Car is not ready for " + string + "!\nReason:\nFirst and Second colors not defined yet!\nPlease go to the 'Color Edit' tab to define the colors.\n", "Car Maker", 1);
            return false;
        }
        boolean bl = true;
        if (this.o.keyz[0] <= 0 || this.o.keyx[0] >= 0) {
            bl = false;
        }
        if (this.o.keyz[1] <= 0 || this.o.keyx[1] <= 0) {
            bl = false;
        }
        if (this.o.keyz[2] >= 0 || this.o.keyx[2] >= 0) {
            bl = false;
        }
        if (this.o.keyz[3] >= 0 || this.o.keyx[3] <= 0) {
            bl = false;
        }
        if (!bl) {
            JOptionPane.showMessageDialog(null, "Car is not ready for " + string + "!\nReason:\nCar Wheels not defined or not defined correctly!\nPlease go to the \u2018Wheels\u2019 tab and use  [ Apply ]  and  [ Save ]  to define correctly.\n", "Car Maker", 1);
            return false;
        }
        if (this.o.npl <= 60) {
            JOptionPane.showMessageDialog(null, "Car is not ready for " + string + "!\nReason:\nNo car seems to be designed!\nYou have not built a car yet please go to the \u2018Car\u2019 tab to find the tutorial on how to build a car.\n", "Car Maker", 1);
            return false;
        }
        if (this.o.npl > 286) {
            JOptionPane.showMessageDialog(null, "Car is not ready for " + string + "!\nReason:\nCar contains too many polygons (pieces).\nNumber of polygons used need to be less then 210.\nPlease use the counter in the \u2018Code Edit\u2019 to decrease the number of polygons (pieces).\n", "Car Maker", 1);
            return false;
        }
        if (this.o.maxR > 400) {
            JOptionPane.showMessageDialog(null, "Car is not ready for " + string + "!\nReason:\nCar scale size is too large!\nPlease use the \u2018Scale All\u2019 option in the \u2018Scale & Align\u2019 tab to resize your car to suitable size.       \nCompare it to other NFM cars using the \u2018Compare Car...\u2019 option.\nCurrently you car needs to be scaled down by " + (int)(((float)this.o.maxR / 400.0f - 1.0f) * 100.0f) + "%.\n", "Car Maker", 1);
            return false;
        }
        if (this.o.maxR < 120) {
            JOptionPane.showMessageDialog(null, "Car is not ready for " + string + "!\nReason:\nCar scale size is too small!\nPlease use the \u2018Scale All\u2019 option in the \u2018Scale & Align\u2019 tab to resize your car to suitable size.       \nCompare it to other NFM cars using the \u2018Compare Car...\u2019 option.\nCurrently you car needs to be scaled up by " + (int)((120.0f / (float)this.o.maxR - 1.0f) * 100.0f) + "%.\n", "Car Maker", 1);
            return false;
        }
        String string2 = "" + this.editor.getText() + "\n";
        int n = 0;
        int n2 = string2.indexOf("\n", 0);
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        while (n2 != -1 && n < string2.length()) {
            int n3;
            String string3 = string2.substring(n, n2);
            string3 = string3.trim();
            n = n2 + 1;
            n2 = string2.indexOf("\n", n);
            if (string3.startsWith("stat(")) {
                bl2 = true;
                try {
                    n3 = 0;
                    for (int i = 0; i < 5; ++i) {
                        this.stat[i] = this.getvalue("stat", string3, i);
                        if (this.stat[i] > 200) {
                            bl2 = false;
                        }
                        if (this.stat[i] < 16) {
                            bl2 = false;
                        }
                        n3 += this.stat[i];
                    }
                    if (n3 != 680 && n3 != 640 && n3 != 600 && n3 != 560 && n3 != 520) {
                        bl2 = false;
                    }
                }
                catch (Exception exception) {
                    bl2 = false;
                }
            }
            if (string3.startsWith("physics(")) {
                bl3 = true;
                try {
                    for (n3 = 0; n3 < 11; ++n3) {
                        this.phys[n3] = this.getvalue("physics", string3, n3);
                        if (this.phys[n3] > 100) {
                            bl3 = false;
                        }
                        if (this.phys[n3] >= 0) continue;
                        bl3 = false;
                    }
                    for (n3 = 0; n3 < 3; ++n3) {
                        this.crash[n3] = this.getvalue("physics", string3, n3 + 11);
                        if (n3 != 0 && this.crash[n3] > 100) {
                            bl3 = false;
                        }
                        if (this.crash[n3] >= 0) continue;
                        bl3 = false;
                    }
                    this.engsel = this.getvalue("physics", string3, 14);
                    if (this.engsel > 4) {
                        bl3 = false;
                    }
                    if (this.engsel < 0) {
                        bl3 = false;
                    }
                }
                catch (Exception exception) {
                    bl3 = false;
                }
            }
            if (!string3.startsWith("handling(")) continue;
            bl4 = true;
            try {
                int n4 = this.getvalue("handling", string3, 0);
                if (n4 > 200) {
                    bl4 = false;
                }
                if (n4 >= 0) continue;
                bl4 = false;
            }
            catch (Exception exception) {
                bl4 = false;
            }
        }
        if (!bl2) {
            JOptionPane.showMessageDialog(null, "Car is not ready for " + string + "!\nReason:\nCar Stats & Class not defined correctly!\nPlease go to the 'Stats & Class' tab to define stats and don't forget to press  [ Save ]  when finished.\n", "Car Maker", 1);
            return false;
        }
        if (!bl3) {
            JOptionPane.showMessageDialog(null, "Car is not ready for " + string + "!\nReason:\nCar Physics not defined correctly!\nPlease go to the 'Physics' tab and complete the car physics definition until it is saved.\n", "Car Maker", 1);
            return false;
        }
        if (!bl4 && string.equals("Publishing")) {
            JOptionPane.showMessageDialog(null, "Car is not ready for " + string + "!\nReason:\nCar Handling not rated.\nPlease Test Drive your car to rate its handling before publishing!\n", "Car Maker", 1);
            return false;
        }
        return true;
    }

    public void regx(int n, float f, boolean bl) {
        this.hitmag = (int)((float)this.hitmag + f);
        if (!bl) {
            this.crash(f);
        }
        if (Math.abs(f *= 0.3f + (float)this.crash[1] * 0.005f) > 100.0f) {
            int n2 = (int)((double)(this.crash[0] * this.crash[0]) * 1.5);
            if (n2 < 1000) {
                n2 = 1000;
            }
            if (f > 100.0f) {
                f -= 100.0f;
            }
            if (f < -100.0f) {
                f += 100.0f;
            }
            for (int i = 0; i < this.o.npl; ++i) {
                float f2 = 0.0f;
                for (int j = 0; j < this.o.p[i].n; ++j) {
                    if (this.o.p[i].wz != 0 || this.py(this.o.keyx[n], this.o.p[i].ox[j], this.o.keyz[n], this.o.p[i].oz[j]) >= n2) continue;
                    f2 = f / 20.0f * this.m.random();
                    int n3 = j;
                    this.o.p[i].oz[n3] = (int)((float)this.o.p[i].oz[n3] - f2 * this.m.sin(this.o.xz) * this.m.cos(this.o.zy));
                    int n4 = j;
                    this.o.p[i].ox[n4] = (int)((float)this.o.p[i].ox[n4] + f2 * this.m.cos(this.o.xz) * this.m.cos(this.o.xy));
                    if (!bl) continue;
                    this.actmag = (int)((float)this.actmag + Math.abs(f2));
                }
                if (f2 == 0.0f) continue;
                if (Math.abs(f2) >= 1.0f) {
                    this.o.p[i].chip = 1;
                    this.o.p[i].ctmag = f2;
                }
                if (!this.o.p[i].nocol && this.o.p[i].glass != 1) {
                    if (this.o.p[i].bfase > 20 && (double)this.o.p[i].hsb[1] > 0.25) {
                        this.o.p[i].hsb[1] = 0.25f;
                    }
                    if (this.o.p[i].bfase > 25 && (double)this.o.p[i].hsb[2] > 0.7) {
                        this.o.p[i].hsb[2] = 0.7f;
                    }
                    if (this.o.p[i].bfase > 30 && (double)this.o.p[i].hsb[1] > 0.15) {
                        this.o.p[i].hsb[1] = 0.15f;
                    }
                    if (this.o.p[i].bfase > 35 && (double)this.o.p[i].hsb[2] > 0.6) {
                        this.o.p[i].hsb[2] = 0.6f;
                    }
                    if (this.o.p[i].bfase > 40) {
                        this.o.p[i].hsb[0] = 0.075f;
                    }
                    if (this.o.p[i].bfase > 50 && (double)this.o.p[i].hsb[2] > 0.5) {
                        this.o.p[i].hsb[2] = 0.5f;
                    }
                    if (this.o.p[i].bfase > 60) {
                        this.o.p[i].hsb[0] = 0.05f;
                    }
                    this.o.p[i].bfase = (int)((float)this.o.p[i].bfase + Math.abs(f2));
                    new Color(this.o.p[i].c[0], this.o.p[i].c[1], this.o.p[i].c[2]);
                    Color color = Color.getHSBColor(this.o.p[i].hsb[0], this.o.p[i].hsb[1], this.o.p[i].hsb[2]);
                    this.o.p[i].c[0] = color.getRed();
                    this.o.p[i].c[1] = color.getGreen();
                    this.o.p[i].c[2] = color.getBlue();
                }
                if (this.o.p[i].glass != 1) continue;
                this.o.p[i].gr = (int)((double)this.o.p[i].gr + Math.abs((double)f2 * 1.5));
            }
        }
    }

    public void regz(int n, float f, boolean bl) {
        this.hitmag = (int)((float)this.hitmag + f);
        if (!bl) {
            this.crash(f);
        }
        if (Math.abs(f *= 0.3f + (float)this.crash[1] * 0.005f) > 100.0f) {
            int n2 = (int)((double)(this.crash[0] * this.crash[0]) * 1.5);
            if (n2 < 1000) {
                n2 = 1000;
            }
            if (f > 100.0f) {
                f -= 100.0f;
            }
            if (f < -100.0f) {
                f += 100.0f;
            }
            for (int i = 0; i < this.o.npl; ++i) {
                float f2 = 0.0f;
                for (int j = 0; j < this.o.p[i].n; ++j) {
                    if (this.o.p[i].wz != 0 || this.py(this.o.keyx[n], this.o.p[i].ox[j], this.o.keyz[n], this.o.p[i].oz[j]) >= n2) continue;
                    f2 = f / 20.0f * this.m.random();
                    int n3 = j;
                    this.o.p[i].oz[n3] = (int)((float)this.o.p[i].oz[n3] + f2 * this.m.cos(this.o.xz) * this.m.cos(this.o.zy));
                    int n4 = j;
                    this.o.p[i].ox[n4] = (int)((float)this.o.p[i].ox[n4] + f2 * this.m.sin(this.o.xz) * this.m.cos(this.o.xy));
                    if (!bl) continue;
                    this.actmag = (int)((float)this.actmag + Math.abs(f2));
                }
                if (f2 == 0.0f) continue;
                if (Math.abs(f2) >= 1.0f) {
                    this.o.p[i].chip = 1;
                    this.o.p[i].ctmag = f2;
                }
                if (!this.o.p[i].nocol && this.o.p[i].glass != 1) {
                    if (this.o.p[i].bfase > 20 && (double)this.o.p[i].hsb[1] > 0.25) {
                        this.o.p[i].hsb[1] = 0.25f;
                    }
                    if (this.o.p[i].bfase > 25 && (double)this.o.p[i].hsb[2] > 0.7) {
                        this.o.p[i].hsb[2] = 0.7f;
                    }
                    if (this.o.p[i].bfase > 30 && (double)this.o.p[i].hsb[1] > 0.15) {
                        this.o.p[i].hsb[1] = 0.15f;
                    }
                    if (this.o.p[i].bfase > 35 && (double)this.o.p[i].hsb[2] > 0.6) {
                        this.o.p[i].hsb[2] = 0.6f;
                    }
                    if (this.o.p[i].bfase > 40) {
                        this.o.p[i].hsb[0] = 0.075f;
                    }
                    if (this.o.p[i].bfase > 50 && (double)this.o.p[i].hsb[2] > 0.5) {
                        this.o.p[i].hsb[2] = 0.5f;
                    }
                    if (this.o.p[i].bfase > 60) {
                        this.o.p[i].hsb[0] = 0.05f;
                    }
                    this.o.p[i].bfase = (int)((float)this.o.p[i].bfase + Math.abs(f2));
                    new Color(this.o.p[i].c[0], this.o.p[i].c[1], this.o.p[i].c[2]);
                    Color color = Color.getHSBColor(this.o.p[i].hsb[0], this.o.p[i].hsb[1], this.o.p[i].hsb[2]);
                    this.o.p[i].c[0] = color.getRed();
                    this.o.p[i].c[1] = color.getGreen();
                    this.o.p[i].c[2] = color.getBlue();
                }
                if (this.o.p[i].glass != 1) continue;
                this.o.p[i].gr = (int)((double)this.o.p[i].gr + Math.abs((double)f2 * 1.5));
            }
        }
    }

    public void roofsqsh(float f) {
        if (f > 100.0f) {
            this.crash(f);
            f -= 100.0f;
            int n = (int)(2.0 + (double)this.crash[2] / 7.6);
            int n2 = 0;
            int n3 = 1;
            for (int i = 0; i < this.o.npl; ++i) {
                float f2 = 0.0f;
                if (Math.random() > 0.9) {
                    f2 = f / 15.0f * this.m.random();
                }
                for (int j = 0; j < this.o.p[i].n; ++j) {
                    if (this.o.p[i].wz != 0 || Math.abs(this.o.p[i].oy[j] - this.o.roofat - this.squash) >= n * 3 && this.o.p[i].oy[j] >= this.o.roofat + this.squash || this.squash >= n) continue;
                    f2 = f / 15.0f * this.m.random();
                    int n4 = j;
                    this.o.p[i].oy[n4] = (int)((float)this.o.p[i].oy[n4] + f2);
                    n2 = (int)((float)n2 + f2);
                    ++n3;
                    this.hitmag = (int)((float)this.hitmag + Math.abs(f2));
                }
                if (!this.o.p[i].nocol && this.o.p[i].glass != 1) {
                    if (f2 != 0.0f) {
                        if (this.o.p[i].bfase > 20 && (double)this.o.p[i].hsb[1] > 0.25) {
                            this.o.p[i].hsb[1] = 0.25f;
                        }
                        if (this.o.p[i].bfase > 25 && (double)this.o.p[i].hsb[2] > 0.7) {
                            this.o.p[i].hsb[2] = 0.7f;
                        }
                        if (this.o.p[i].bfase > 30 && (double)this.o.p[i].hsb[1] > 0.15) {
                            this.o.p[i].hsb[1] = 0.15f;
                        }
                        if (this.o.p[i].bfase > 35 && (double)this.o.p[i].hsb[2] > 0.6) {
                            this.o.p[i].hsb[2] = 0.6f;
                        }
                        if (this.o.p[i].bfase > 40) {
                            this.o.p[i].hsb[0] = 0.075f;
                        }
                        if (this.o.p[i].bfase > 50 && (double)this.o.p[i].hsb[2] > 0.5) {
                            this.o.p[i].hsb[2] = 0.5f;
                        }
                        if (this.o.p[i].bfase > 60) {
                            this.o.p[i].hsb[0] = 0.05f;
                        }
                        this.o.p[i].bfase = (int)((float)this.o.p[i].bfase + f2);
                        new Color(this.o.p[i].c[0], this.o.p[i].c[1], this.o.p[i].c[2]);
                        Color color = Color.getHSBColor(this.o.p[i].hsb[0], this.o.p[i].hsb[1], this.o.p[i].hsb[2]);
                        this.o.p[i].c[0] = color.getRed();
                        this.o.p[i].c[1] = color.getGreen();
                        this.o.p[i].c[2] = color.getBlue();
                    }
                } else if (this.o.p[i].glass == 1) {
                    this.o.p[i].gr += 5;
                }
                if (!(Math.abs(f2) >= 1.0f)) continue;
                this.o.p[i].chip = 1;
                this.o.p[i].ctmag = f2;
            }
            this.squash += n2 / n3;
        }
    }

    public void crash(float f) {
        if (f > 100.0f) {
            f -= 100.0f;
        }
        if (f < -100.0f) {
            f += 100.0f;
        }
        if (Math.abs(f) > 25.0f && Math.abs(f) < 170.0f) {
            this.lowcrashs[this.crshturn].play();
        }
        if (Math.abs(f) >= 170.0f) {
            this.crashs[this.crshturn].play();
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

    public void setheme() {
        if (this.cthm == 0) {
            this.editor.setForeground(this.deff);
            this.editor.setBackground(this.defb);
        }
        if (this.cthm == 1) {
            this.editor.setForeground(new Color(0, 0, 0));
            this.editor.setBackground(new Color(192, 192, 192));
        }
        if (this.cthm == 2) {
            this.editor.setForeground(new Color(192, 192, 192));
            this.editor.setBackground(new Color(0, 0, 0));
        }
        if (this.cthm == 3) {
            this.editor.setForeground(new Color(0, 0, 0));
            this.editor.setBackground(new Color(50, 200, 0));
        }
        if (this.cthm == 4) {
            this.editor.setForeground(new Color(67, 255, 77));
            this.editor.setBackground(new Color(0, 0, 0));
        }
        if (this.cthm == 5) {
            this.editor.setForeground(new Color(0, 172, 255));
            this.editor.setBackground(new Color(210, 234, 255));
        }
        if (this.cthm == 6) {
            this.editor.setForeground(new Color(255, 230, 0));
            this.editor.setBackground(new Color(255, 77, 67));
        }
        if (this.cthm == 7) {
            this.editor.setForeground(new Color(0, 159, 255));
            this.editor.setBackground(new Color(9, 47, 104));
        }
    }

    public int py(int n, int n2, int n3, int n4) {
        return (n - n2) * (n - n2) + (n3 - n4) * (n3 - n4);
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
        if (n2 < this.m.cz) {
            n2 = this.m.cz;
        }
        return (n2 - this.m.focus_point) * (this.m.cx - n) / n2 + n;
    }

    public int ys(int n, int n2) {
        if (n2 < this.m.cz) {
            n2 = this.m.cz;
        }
        return (n2 - this.m.focus_point) * (this.m.cy - n) / n2 + n;
    }

    @Override
    public void init() {
        this.setBackground(new Color(0, 0, 0));
        this.offImage = this.createImage(700, 550);
        if (this.offImage != null) {
            this.rd = (Graphics2D)this.offImage.getGraphics();
        }
        this.rd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        boolean bl = true;
        if (System.getProperty("java.vendor").toLowerCase().indexOf("apple") != -1 || System.getProperty("java.vendor").toLowerCase().indexOf("sun") != -1) {
            bl = false;
        }
        this.setLayout(null);
        this.slcar.setFont(new Font("Arial", 1, 13));
        this.slcar.add(this.rd, "Select a Car...         ");
        this.slcar.setForeground(new Color(63, 80, 110));
        this.slcar.setBackground(new Color(209, 217, 230));
        this.fontsel.setFont(new Font("Arial", 1, 12));
        this.fontsel.add(this.rd, "Arial");
        this.fontsel.add(this.rd, "Dialog");
        this.fontsel.add(this.rd, "DialogInput");
        this.fontsel.add(this.rd, "Monospaced");
        this.fontsel.add(this.rd, "Serif");
        this.fontsel.add(this.rd, "SansSerif");
        this.fontsel.add(this.rd, "Verdana");
        this.fontsel.setBackground(new Color(63, 80, 110));
        this.fontsel.setForeground(new Color(209, 217, 230));
        this.ctheme.setFont(new Font("Arial", 1, 12));
        this.ctheme.add(this.rd, "Default");
        this.ctheme.add(this.rd, "Author");
        this.ctheme.add(this.rd, "Dos");
        this.ctheme.add(this.rd, "Green");
        this.ctheme.add(this.rd, "The Matrix");
        this.ctheme.add(this.rd, "Ice Age");
        this.ctheme.add(this.rd, "Fire");
        this.ctheme.add(this.rd, "Ocean");
        this.ctheme.setBackground(new Color(63, 80, 110));
        this.ctheme.setForeground(new Color(209, 217, 230));
        this.compcar.setFont(new Font("Arial", 1, 12));
        this.compcar.add(this.rd, "Compare Car...");
        this.compcar.add(this.rd, "Tornado Shark");
        this.compcar.add(this.rd, "Formula 7");
        this.compcar.add(this.rd, "Wow Caninaro");
        this.compcar.add(this.rd, "La Vita Crab");
        this.compcar.add(this.rd, "Nimi");
        this.compcar.add(this.rd, "MAX Revenge");
        this.compcar.add(this.rd, "Lead Oxide");
        this.compcar.add(this.rd, "Kool Kat");
        this.compcar.add(this.rd, "Drifter X");
        this.compcar.add(this.rd, "Sword of Justice");
        this.compcar.add(this.rd, "High Rider");
        this.compcar.add(this.rd, "EL KING");
        this.compcar.add(this.rd, "Mighty Eight");
        this.compcar.add(this.rd, "M A S H E E N");
        this.compcar.add(this.rd, "Radical One");
        this.compcar.add(this.rd, "DR Monstaa");
        this.compcar.add(this.rd, " -  None  - ");
        this.compcar.setBackground(new Color(63, 80, 110));
        this.compcar.setForeground(new Color(209, 217, 230));
        this.cls.setFont(new Font("Arial", 1, 12));
        this.cls.add(this.rd, "Class A");
        this.cls.add(this.rd, "Class A & B");
        this.cls.add(this.rd, "Class B");
        this.cls.add(this.rd, "Class B & C");
        this.cls.add(this.rd, "Class C");
        this.cls.setBackground(new Color(63, 80, 110));
        this.cls.setForeground(new Color(209, 217, 230));
        this.simcar.setFont(new Font("Arial", 1, 12));
        this.simcar.add(this.rd, "Tornado Shark");
        this.simcar.add(this.rd, "Formula 7");
        this.simcar.add(this.rd, "Wow Caninaro");
        this.simcar.add(this.rd, "La Vita Crab");
        this.simcar.add(this.rd, "Nimi");
        this.simcar.add(this.rd, "MAX Revenge");
        this.simcar.add(this.rd, "Lead Oxide");
        this.simcar.add(this.rd, "Kool Kat");
        this.simcar.add(this.rd, "Drifter X");
        this.simcar.add(this.rd, "Sword of Justice");
        this.simcar.add(this.rd, "High Rider");
        this.simcar.add(this.rd, "EL KING");
        this.simcar.add(this.rd, "Mighty Eight");
        this.simcar.add(this.rd, "M A S H E E N");
        this.simcar.add(this.rd, "Radical One");
        this.simcar.add(this.rd, "DR Monstaa");
        this.simcar.setBackground(new Color(63, 80, 110));
        this.simcar.setForeground(new Color(209, 217, 230));
        this.witho.setFont(new Font("Arial", 1, 12));
        this.witho.add(this.rd, "With other cars");
        this.witho.add(this.rd, "Alone");
        this.witho.setBackground(new Color(63, 80, 110));
        this.witho.setForeground(new Color(209, 217, 230));
        this.engine.setFont(new Font("Arial", 1, 12));
        this.engine.add(this.rd, "Normal Engine");
        this.engine.add(this.rd, "V8 Engine");
        this.engine.add(this.rd, "Retro Engine");
        this.engine.add(this.rd, "Power Engine");
        this.engine.add(this.rd, "Diesel Engine");
        this.engine.setBackground(new Color(63, 80, 110));
        this.engine.setForeground(new Color(209, 217, 230));
        MenuItem menuItem = new MenuItem("Cut");
        MenuItem menuItem2 = new MenuItem("Copy");
        MenuItem menuItem3 = new MenuItem("Paste");
        MenuItem menuItem4 = new MenuItem("Select All");
        this.popupMenu = new PopupMenu();
        this.popupMenu.add(menuItem);
        this.popupMenu.add(menuItem2);
        this.popupMenu.add(menuItem3);
        this.popupMenu.add(menuItem4);
        menuItem.addActionListener(this);
        menuItem2.addActionListener(this);
        menuItem3.addActionListener(this);
        menuItem4.addActionListener(this);
        this.add(this.popupMenu);
        for (int i = 0; i < 16; ++i) {
            this.wv[i] = new TextField("", 2);
            this.wv[i].setBackground(new Color(255, 255, 255));
            this.wv[i].setForeground(new Color(0, 0, 0));
            this.wv[i].setFont(new Font(this.cfont, 1, 14));
            if (bl) {
                this.wv[i].addMouseListener(new MouseHandler(this.popupMenu, i));
            }
            this.add(this.wv[i]);
        }
        this.tnick.setFont(new Font("Arial", 1, 13));
        this.tnick.setBackground(new Color(255, 255, 255));
        this.tnick.setForeground(new Color(0, 0, 0));
        this.tpass.setFont(new Font("Arial", 1, 13));
        this.tpass.setEchoCharacter('*');
        this.tpass.setBackground(new Color(255, 255, 255));
        this.tpass.setForeground(new Color(0, 0, 0));
        this.pubtyp.setFont(new Font("Arial", 1, 13));
        this.pubtyp.add(this.rd, "Private");
        this.pubtyp.add(this.rd, "Public");
        this.pubtyp.add(this.rd, "Super Public");
        this.pubtyp.setBackground(new Color(63, 80, 110));
        this.pubtyp.setForeground(new Color(209, 217, 230));
        this.pubitem.setFont(new Font("Arial", 1, 13));
        this.pubitem.add(this.rd, "Account Cars");
        this.pubitem.setBackground(new Color(209, 217, 230));
        this.pubitem.setForeground(new Color(63, 80, 110));
        this.srch.setBackground(new Color(255, 255, 255));
        this.srch.setForeground(new Color(0, 0, 0));
        if (bl) {
            this.srch.addMouseListener(new MouseHandler(this.popupMenu, 16));
        }
        this.rplc.setBackground(new Color(255, 255, 255));
        this.rplc.setForeground(new Color(0, 0, 0));
        if (bl) {
            this.rplc.addMouseListener(new MouseHandler(this.popupMenu, 17));
        }
        if (bl) {
            this.editor.addMouseListener(new MouseHandler(this.popupMenu, 18));
        }
        this.add(this.tnick);
        this.add(this.tpass);
        this.add(this.editor);
        this.add(this.srch);
        this.add(this.rplc);
        this.defb = new Color(255, 255, 255);
        this.deff = new Color(0, 0, 0);
        this.hidefields();
    }

    public void hidefields() {
        this.pubtyp.hide();
        this.pubitem.hide();
        this.tpass.hide();
        this.tnick.hide();
        this.slcar.hide();
        this.witho.hide();
        for (int i = 0; i < 16; ++i) {
            this.wv[i].hide();
        }
        this.simcar.hide();
        this.engine.hide();
        this.cls.hide();
        this.compcar.hide();
        this.editor.hide();
        this.fontsel.hide();
        this.ctheme.hide();
        this.srch.hide();
        this.rplc.hide();
    }

    public void movefield(Component component, int n, int n2, int n3, int n4) {
        if (component.getX() != (n += this.apx) || component.getY() != (n2 += this.apy) || component.getWidth() != n3 || component.getHeight() != n4) {
            component.setBounds(n, n2, n3, n4);
        }
    }

    public void drawms() {
        this.openm = false;
        if (this.pubtyp.draw(this.rd, this.xm, this.ym, this.mousdr, 550, false)) {
            this.openm = true;
        }
        if (this.pubitem.draw(this.rd, this.xm, this.ym, this.mousdr, 550, false)) {
            this.openm = true;
        }
        if (this.fontsel.draw(this.rd, this.xm, this.ym, this.mousdr, 550, true)) {
            this.openm = true;
        }
        if (this.ctheme.draw(this.rd, this.xm, this.ym, this.mousdr, 550, true)) {
            this.openm = true;
        }
        if (this.compcar.draw(this.rd, this.xm, this.ym, this.mousdr, 550, true)) {
            this.openm = true;
        }
        if (this.cls.draw(this.rd, this.xm, this.ym, this.mousdr, 550, true)) {
            this.openm = true;
        }
        if (this.simcar.draw(this.rd, this.xm, this.ym, this.mousdr, 550, true)) {
            this.openm = true;
        }
        if (this.engine.draw(this.rd, this.xm, this.ym, this.mousdr, 550, false)) {
            this.openm = true;
        }
        if (this.witho.draw(this.rd, this.xm, this.ym, this.mousdr, 550, true)) {
            this.openm = true;
        }
        if (this.slcar.draw(this.rd, this.xm, this.ym, this.mousdr, 550, false)) {
            this.openm = true;
        }
        if (this.openm) {
            this.waso = true;
            this.mouses = 0;
        }
    }

    public void openlink() {
        Madness.openurl("http://www.needformadness.com/developer/simplecar.html");
    }

    public void openhlink() {
        Madness.openurl("http://www.needformadness.com/developer/");
    }

    public void openelink() {
        Madness.openurl("http://www.needformadness.com/developer/extras.html");
    }

    @Override
    public void start() {
        if (this.thredo == null) {
            this.thredo = new Thread(this);
        }
        this.thredo.start();
    }

    @Override
    public void stop() {
        this.exwist = true;
    }

    @Override
    public void paint(Graphics graphics) {
        this.apx = this.getWidth() / 2 - 350;
        this.apy = this.getHeight() / 2 - 275;
        graphics.drawImage(this.offImage, this.apx, this.apy, this);
    }

    @Override
    public void update(Graphics graphics) {
        this.paint(graphics);
    }

    @Override
    public boolean mouseUp(Event event, int n, int n2) {
        this.xm = n - this.apx;
        this.ym = n2 - this.apy;
        if (this.waso) {
            this.waso = false;
        } else {
            this.mouses = -1;
        }
        this.mousdr = false;
        if (this.onbtgame) {
            Madness.game();
        }
        return false;
    }

    @Override
    public boolean mouseDown(Event event, int n, int n2) {
        this.xm = n - this.apx;
        this.ym = n2 - this.apy;
        this.mouses = 1;
        this.mousdr = true;
        if (this.tab != 1) {
            this.requestFocus();
        }
        return false;
    }

    @Override
    public boolean mouseMove(Event event, int n, int n2) {
        this.xm = n - this.apx;
        this.ym = n2 - this.apy;
        if (this.xm > 520 && this.xm < 674 && this.ym > 0 && this.ym < 23) {
            if (!this.onbtgame) {
                this.onbtgame = true;
                this.setCursor(new Cursor(12));
            }
        } else if (this.onbtgame) {
            this.onbtgame = false;
            this.setCursor(new Cursor(0));
        }
        return false;
    }

    @Override
    public boolean mouseDrag(Event event, int n, int n2) {
        this.mousdr = true;
        this.xm = n - this.apx;
        this.ym = n2 - this.apy;
        return false;
    }

    @Override
    public boolean lostFocus(Event event, Object object) {
        this.focuson = false;
        return false;
    }

    @Override
    public boolean gotFocus(Event event, Object object) {
        this.focuson = true;
        return false;
    }

    @Override
    public boolean keyDown(Event event, int n) {
        if (this.focuson) {
            if (n == 54 || n == 46 || n == 100 || n == 68) {
                this.rotr = true;
            }
            if (n == 52 || n == 44 || n == 97 || n == 65) {
                this.rotl = true;
            }
            if (n == 43 || n == 61) {
                this.plus = true;
            }
            if (n == 45) {
                this.minus = true;
            }
            if (n == 42 || n == 10 || n == 56 || n == 119 || n == 87) {
                this.in = true;
            }
            if (n == 47 || n == 8 || n == 50 || n == 115 || n == 83) {
                this.out = true;
            }
            if (n == 1006) {
                this.left = true;
            }
            if (n == 1007) {
                this.right = true;
            }
            if (n == 1005) {
                this.down = true;
            }
            if (n == 1004) {
                this.up = true;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(Event event, int n) {
        if (n == 54 || n == 46 || n == 100 || n == 68) {
            this.rotr = false;
        }
        if (n == 52 || n == 44 || n == 97 || n == 65) {
            this.rotl = false;
        }
        if (n == 43 || n == 61) {
            this.plus = false;
        }
        if (n == 45) {
            this.minus = false;
        }
        if (n == 42 || n == 10 || n == 56 || n == 119 || n == 97) {
            this.in = false;
        }
        if (n == 47 || n == 8 || n == 50 || n == 115 || n == 83) {
            this.out = false;
        }
        if (n == 1006) {
            this.left = false;
        }
        if (n == 1007) {
            this.right = false;
        }
        if (n == 1005) {
            this.down = false;
        }
        if (n == 1004) {
            this.up = false;
        }
        return false;
    }

    public void loadsounds() {
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
                for (n = 0; n < 3; ++n) {
                    if (!string.equals("crash" + (n + 1) + ".wav")) continue;
                    this.crashs[n] = new soundClip(byArray);
                }
                for (n = 0; n < 3; ++n) {
                    if (!string.equals("lowcrash" + (n + 1) + ".wav")) continue;
                    this.lowcrashs[n] = new soundClip(byArray);
                }
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

    public void loadbase() {
        String[] stringArray = new String[]{"2000tornados", "formula7", "canyenaro", "lescrab", "nimi", "maxrevenge", "leadoxide", "koolkat", "drifter", "policecops", "mustang", "king", "audir8", "masheen", "radicalone", "drmonster"};
        try {
            File file = new File("" + Madness.fpath + "data/models.zip");
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            byte[] byArray = null;
            while (zipEntry != null) {
                int n;
                int n2 = -1;
                for (n = 0; n < 16; ++n) {
                    if (!zipEntry.getName().startsWith(stringArray[n])) continue;
                    n2 = n;
                }
                if (n2 != -1) {
                    int n3;
                    byArray = new byte[n];
                    int n4 = 0;
                    for (n = (int)zipEntry.getSize(); n > 0; n -= n3) {
                        n3 = zipInputStream.read(byArray, n4, n);
                        n4 += n3;
                    }
                    this.compo[n2] = new ContO(byArray, this.m, this.t);
                    this.compo[n2].shadow = false;
                    this.compo[n2].noline = true;
                }
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.close();
        }
        catch (Exception exception) {
            System.out.println("Error Loading Models from Zip: " + exception);
        }
        System.gc();
    }

    public void fixtext(TextField textField) {
        String string = textField.getText();
        string = string.replace('\"', '#');
        String string2 = "\\";
        String string3 = "";
        int n = -1;
        for (int i = 0; i < string.length(); ++i) {
            String string4 = "" + string.charAt(i);
            if (string4.equals("|") || string4.equals(",") || string4.equals("(") || string4.equals(")") || string4.equals("#") || string4.equals(string2) || string4.equals("!") || string4.equals("?") || string4.equals("~") || string4.equals(".") || string4.equals("@") || string4.equals("$") || string4.equals("%") || string4.equals("^") || string4.equals("&") || string4.equals("*") || string4.equals("+") || string4.equals("=") || string4.equals(">") || string4.equals("<") || string4.equals("/") || string4.equals("'") || string4.equals(";") || string4.equals(":") || i > 15) {
                n = i;
                continue;
            }
            string3 = string3 + string4;
        }
        if (n != -1) {
            textField.setText(string3);
            textField.select(n, n);
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

    public int objvalue(String string, int n) {
        int n2 = 0;
        try {
            int n3;
            int n4 = 0;
            int n5 = 0;
            String string2 = "";
            String string3 = "";
            boolean bl = false;
            for (n3 = 2; n3 < string.length() && n5 != 2; ++n3) {
                string2 = "" + string.charAt(n3);
                if (string2.equals(" ")) {
                    if (bl) {
                        ++n4;
                        bl = false;
                    }
                    if (n5 != 1 && n4 <= n) continue;
                    n5 = 2;
                    continue;
                }
                if (n4 == n) {
                    string3 = string3 + string2;
                    n5 = 1;
                }
                bl = true;
            }
            if (n3 >= string.length()) {
                this.objfacend = true;
            }
            if (string3.equals("")) {
                string3 = "0";
            }
            if (this.multf10) {
                n2 = (int)(Float.valueOf(string3).floatValue() * 10.0f);
            } else {
                int n6 = string3.indexOf("/", 0);
                if (n6 != -1) {
                    string3 = string3.substring(0, n6);
                }
                if ((n2 = Float.valueOf(string3).intValue() - 1) < 0) {
                    n2 = 0;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return n2;
    }

    public Image getImage(String string) {
        Image image = Toolkit.getDefaultToolkit().createImage(string);
        MediaTracker mediaTracker = new MediaTracker(this);
        mediaTracker.addImage(image, 0);
        try {
            mediaTracker.waitForID(0);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return image;
    }

    public void stringbutton(String string, int n, int n2, int n3, boolean bl) {
        this.rd.setFont(new Font("Arial", 1, 12));
        this.ftm = this.rd.getFontMetrics();
        if (string.indexOf("Publish") != -1) {
            this.rd.setFont(new Font("Arial", 1, 13));
            this.ftm = this.rd.getFontMetrics();
        }
        this.bx[this.btn] = n;
        this.by[this.btn] = n2 - 5;
        this.bw[this.btn] = this.ftm.stringWidth(string);
        if (!this.pessd[this.btn]) {
            this.rd.setColor(new Color(220, 220, 220));
            if (bl) {
                this.rd.setColor(new Color(230, 230, 230));
            }
            this.rd.fillRect(n - this.bw[this.btn] / 2 - 10, n2 - (17 - n3), this.bw[this.btn] + 20, 25 - n3 * 2);
            this.rd.setColor(new Color(240, 240, 240));
            if (bl) {
                this.rd.setColor(new Color(255, 255, 255));
            }
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 - (17 - n3), n + this.bw[this.btn] / 2 + 10, n2 - (17 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 - (18 - n3), n + this.bw[this.btn] / 2 + 10, n2 - (18 - n3));
            this.rd.setColor(new Color(240, 240, 240));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 9, n2 - (19 - n3), n + this.bw[this.btn] / 2 + 9, n2 - (19 - n3));
            this.rd.setColor(new Color(200, 200, 200));
            if (bl) {
                this.rd.setColor(new Color(192, 192, 192));
            }
            this.rd.drawLine(n + this.bw[this.btn] / 2 + 10, n2 - (17 - n3), n + this.bw[this.btn] / 2 + 10, n2 + (7 - n3));
            this.rd.drawLine(n + this.bw[this.btn] / 2 + 11, n2 - (17 - n3), n + this.bw[this.btn] / 2 + 11, n2 + (7 - n3));
            this.rd.setColor(new Color(200, 200, 200));
            this.rd.drawLine(n + this.bw[this.btn] / 2 + 12, n2 - (16 - n3), n + this.bw[this.btn] / 2 + 12, n2 + (6 - n3));
            if (bl) {
                this.rd.setColor(new Color(192, 192, 192));
            }
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 + (7 - n3), n + this.bw[this.btn] / 2 + 10, n2 + (7 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 + (8 - n3), n + this.bw[this.btn] / 2 + 10, n2 + (8 - n3));
            this.rd.setColor(new Color(200, 200, 200));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 9, n2 + (9 - n3), n + this.bw[this.btn] / 2 + 9, n2 + (9 - n3));
            this.rd.setColor(new Color(240, 240, 240));
            if (bl) {
                this.rd.setColor(new Color(255, 255, 255));
            }
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 - (17 - n3), n - this.bw[this.btn] / 2 - 10, n2 + (7 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 11, n2 - (17 - n3), n - this.bw[this.btn] / 2 - 11, n2 + (7 - n3));
            this.rd.setColor(new Color(240, 240, 240));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 12, n2 - (16 - n3), n - this.bw[this.btn] / 2 - 12, n2 + (6 - n3));
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawString(string, n - this.bw[this.btn] / 2, n2);
        } else {
            this.rd.setColor(new Color(220, 220, 220));
            this.rd.fillRect(n - this.bw[this.btn] / 2 - 10, n2 - (17 - n3), this.bw[this.btn] + 20, 25 - n3 * 2);
            this.rd.setColor(new Color(192, 192, 192));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 - (17 - n3), n + this.bw[this.btn] / 2 + 10, n2 - (17 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 - (18 - n3), n + this.bw[this.btn] / 2 + 10, n2 - (18 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 9, n2 - (19 - n3), n + this.bw[this.btn] / 2 + 9, n2 - (19 - n3));
            this.rd.setColor(new Color(247, 247, 247));
            this.rd.drawLine(n + this.bw[this.btn] / 2 + 10, n2 - (17 - n3), n + this.bw[this.btn] / 2 + 10, n2 + (7 - n3));
            this.rd.drawLine(n + this.bw[this.btn] / 2 + 11, n2 - (17 - n3), n + this.bw[this.btn] / 2 + 11, n2 + (7 - n3));
            this.rd.drawLine(n + this.bw[this.btn] / 2 + 12, n2 - (16 - n3), n + this.bw[this.btn] / 2 + 12, n2 + (6 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 + (7 - n3), n + this.bw[this.btn] / 2 + 10, n2 + (7 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 + (8 - n3), n + this.bw[this.btn] / 2 + 10, n2 + (8 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 9, n2 + (9 - n3), n + this.bw[this.btn] / 2 + 9, n2 + (9 - n3));
            this.rd.setColor(new Color(192, 192, 192));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 10, n2 - (17 - n3), n - this.bw[this.btn] / 2 - 10, n2 + (7 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 11, n2 - (17 - n3), n - this.bw[this.btn] / 2 - 11, n2 + (7 - n3));
            this.rd.drawLine(n - this.bw[this.btn] / 2 - 12, n2 - (16 - n3), n - this.bw[this.btn] / 2 - 12, n2 + (6 - n3));
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawString(string, n - this.bw[this.btn] / 2 + 1, n2 + 1);
        }
        ++this.btn;
    }

    public boolean ovbutton(String string, int n, int n2) {
        this.rd.setFont(new Font("Arial", 0, 12));
        this.ftm = this.rd.getFontMetrics();
        if (string.equals("X") || string.equals("Download")) {
            this.rd.setFont(new Font("Arial", 1, 12));
            this.ftm = this.rd.getFontMetrics();
        }
        int n3 = this.ftm.stringWidth(string);
        int n4 = 4;
        boolean bl = false;
        boolean bl2 = false;
        bl = Math.abs(this.xm - n) < n3 / 2 + 12 && Math.abs(this.ym - n2 + 5) < 10 && this.mouses == 1;
        if (Math.abs(this.xm - n) < n3 / 2 + 12 && Math.abs(this.ym - n2 + 5) < 10 && this.mouses == -1) {
            this.mouses = 0;
            bl2 = true;
        }
        if (!bl) {
            this.rd.setColor(new Color(220, 220, 220));
            this.rd.fillRect(n - n3 / 2 - 10, n2 - (17 - n4), n3 + 20, 25 - n4 * 2);
            this.rd.setColor(new Color(240, 240, 240));
            this.rd.drawLine(n - n3 / 2 - 10, n2 - (17 - n4), n + n3 / 2 + 10, n2 - (17 - n4));
            this.rd.drawLine(n - n3 / 2 - 10, n2 - (18 - n4), n + n3 / 2 + 10, n2 - (18 - n4));
            this.rd.setColor(new Color(240, 240, 240));
            this.rd.drawLine(n - n3 / 2 - 9, n2 - (19 - n4), n + n3 / 2 + 9, n2 - (19 - n4));
            this.rd.setColor(new Color(200, 200, 200));
            this.rd.drawLine(n + n3 / 2 + 10, n2 - (17 - n4), n + n3 / 2 + 10, n2 + (7 - n4));
            this.rd.drawLine(n + n3 / 2 + 11, n2 - (17 - n4), n + n3 / 2 + 11, n2 + (7 - n4));
            this.rd.setColor(new Color(200, 200, 200));
            this.rd.drawLine(n + n3 / 2 + 12, n2 - (16 - n4), n + n3 / 2 + 12, n2 + (6 - n4));
            this.rd.drawLine(n - n3 / 2 - 10, n2 + (7 - n4), n + n3 / 2 + 10, n2 + (7 - n4));
            this.rd.drawLine(n - n3 / 2 - 10, n2 + (8 - n4), n + n3 / 2 + 10, n2 + (8 - n4));
            this.rd.setColor(new Color(200, 200, 200));
            this.rd.drawLine(n - n3 / 2 - 9, n2 + (9 - n4), n + n3 / 2 + 9, n2 + (9 - n4));
            this.rd.setColor(new Color(240, 240, 240));
            this.rd.drawLine(n - n3 / 2 - 10, n2 - (17 - n4), n - n3 / 2 - 10, n2 + (7 - n4));
            this.rd.drawLine(n - n3 / 2 - 11, n2 - (17 - n4), n - n3 / 2 - 11, n2 + (7 - n4));
            this.rd.setColor(new Color(240, 240, 240));
            this.rd.drawLine(n - n3 / 2 - 12, n2 - (16 - n4), n - n3 / 2 - 12, n2 + (6 - n4));
            this.rd.setColor(new Color(0, 0, 0));
            if (string.equals("X")) {
                this.rd.setColor(new Color(255, 0, 0));
            }
            if (string.equals("Download")) {
                this.rd.setColor(new Color(0, 64, 128));
            }
            this.rd.drawString(string, n - n3 / 2, n2);
        } else {
            this.rd.setColor(new Color(220, 220, 220));
            this.rd.fillRect(n - n3 / 2 - 10, n2 - (17 - n4), n3 + 20, 25 - n4 * 2);
            this.rd.setColor(new Color(192, 192, 192));
            this.rd.drawLine(n - n3 / 2 - 10, n2 - (17 - n4), n + n3 / 2 + 10, n2 - (17 - n4));
            this.rd.drawLine(n - n3 / 2 - 10, n2 - (18 - n4), n + n3 / 2 + 10, n2 - (18 - n4));
            this.rd.drawLine(n - n3 / 2 - 9, n2 - (19 - n4), n + n3 / 2 + 9, n2 - (19 - n4));
            this.rd.setColor(new Color(247, 247, 247));
            this.rd.drawLine(n + n3 / 2 + 10, n2 - (17 - n4), n + n3 / 2 + 10, n2 + (7 - n4));
            this.rd.drawLine(n + n3 / 2 + 11, n2 - (17 - n4), n + n3 / 2 + 11, n2 + (7 - n4));
            this.rd.drawLine(n + n3 / 2 + 12, n2 - (16 - n4), n + n3 / 2 + 12, n2 + (6 - n4));
            this.rd.drawLine(n - n3 / 2 - 10, n2 + (7 - n4), n + n3 / 2 + 10, n2 + (7 - n4));
            this.rd.drawLine(n - n3 / 2 - 10, n2 + (8 - n4), n + n3 / 2 + 10, n2 + (8 - n4));
            this.rd.drawLine(n - n3 / 2 - 9, n2 + (9 - n4), n + n3 / 2 + 9, n2 + (9 - n4));
            this.rd.setColor(new Color(192, 192, 192));
            this.rd.drawLine(n - n3 / 2 - 10, n2 - (17 - n4), n - n3 / 2 - 10, n2 + (7 - n4));
            this.rd.drawLine(n - n3 / 2 - 11, n2 - (17 - n4), n - n3 / 2 - 11, n2 + (7 - n4));
            this.rd.drawLine(n - n3 / 2 - 12, n2 - (16 - n4), n - n3 / 2 - 12, n2 + (6 - n4));
            this.rd.setColor(new Color(0, 0, 0));
            if (string.equals("X")) {
                this.rd.setColor(new Color(255, 0, 0));
            }
            if (string.equals("Download")) {
                this.rd.setColor(new Color(0, 64, 128));
            }
            this.rd.drawString(string, n - n3 / 2 + 1, n2 + 1);
        }
        return bl2;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object object;
        String string;
        TextComponent textComponent = this.wv[0];
        if (Madness.textid >= 0 && Madness.textid <= 15) {
            textComponent = this.wv[Madness.textid];
        }
        if (Madness.textid == 16) {
            textComponent = this.srch;
        }
        if (Madness.textid == 17) {
            textComponent = this.rplc;
        }
        if (Madness.textid == 18) {
            textComponent = this.editor;
        }
        if ((string = actionEvent.getActionCommand()).equals("Cut")) {
            object = new StringSelection(textComponent.getSelectedText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents((Transferable)object, null);
            if (Madness.textid == 18) {
                this.editor.replaceText("", this.editor.getSelectionStart(), this.editor.getSelectionEnd());
            } else {
                textComponent.setText(textComponent.getText().substring(0, textComponent.getSelectionStart()) + textComponent.getText().substring(textComponent.getSelectionEnd(), textComponent.getText().length()));
            }
        }
        if (string.equals("Copy")) {
            object = new StringSelection(textComponent.getSelectedText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents((Transferable)object, null);
        }
        if (string.equals("Paste")) {
            try {
                object = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                if (Madness.textid == 18) {
                    this.editor.replaceText((String)object, this.editor.getSelectionStart(), this.editor.getSelectionEnd());
                } else {
                    textComponent.setText(textComponent.getText().substring(0, textComponent.getSelectionStart()) + (String)object + textComponent.getText().substring(textComponent.getSelectionEnd(), textComponent.getText().length()));
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals("Select All")) {
            textComponent.selectAll();
        }
    }
}

