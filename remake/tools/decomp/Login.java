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
import java.awt.TextField;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Date;

public class Login
implements Runnable {
    Graphics2D rd;
    xtGraphics xt;
    Medium m;
    FontMetrics ftm;
    ImageObserver ob;
    GameSparker gs;
    int nmsgs = 0;
    int nconf = 0;
    int nfreq = 0;
    int ncreq = 0;
    int fclan = 0;
    int fplayer = 0;
    String clanapv = "";
    boolean justlog = false;
    int cntgame = 0;
    int gamec = -1;
    int groom = 0;
    String gmaker = "";
    String gservern = "";
    Thread connector;
    int fase = 0;
    Socket socket;
    BufferedReader din;
    PrintWriter dout;
    boolean[] pessd = new boolean[]{false, false, false, false, false, false};
    int[] bx = new int[]{0, 0, 0, 0, 0, 0};
    int[] by = new int[]{0, 0, 0, 0, 0, 0};
    int[] bw = new int[]{0, 0, 0, 0, 0, 0};
    int btn = 0;
    int nflk = 0;
    int ncnt = 0;
    int errcnt = 0;
    int lrgfase = 0;
    String msg = "";
    String lnick = "";
    String lpass = "";
    String lemail = "";
    boolean onf = false;
    boolean nickero = false;
    boolean jflk = false;
    boolean ond = false;
    int opselect = 0;
    int trans = 0;
    int cntcl = 0;
    boolean contrb = false;
    int nservers = 2;
    String[] servers = new String[]{"multiplayer.needformadness.com", "avenger.needformadness.com", "ghostrider.needformadness.com"};
    InetAddress[] IPAddress = new InetAddress[3];
    DatagramSocket[] dSocket = new DatagramSocket[3];
    int[] serverdone = new int[]{-1, -1, -1};
    long[] servestart = new long[]{0L, 0L, 0L};
    String[] snames = new String[]{"Dominion", "Avenger", "Ghostrider"};
    boolean socketson = false;
    int srvtrn = 0;
    int[] rmps = new int[]{0, 0, 0, 0, 0};
    int[] rmwt = new int[]{0, 0, 0, 0, 0};
    int recom = 0;
    boolean resofaso = false;
    boolean checknote = false;
    int pend = 0;
    boolean pendb = false;
    boolean gotcai = false;
    int cax = 0;
    int cay = 0;
    boolean btroom = false;
    boolean showtf = false;
    int[] bgmy = new int[]{0, 400, 800};
    int flipo = 0;
    int xrl = 0;
    int xrr = 0;
    boolean onr = false;
    int oxm = 0;
    int oym = 0;
    int lxm = 0;
    int lym = 0;

    public Login(Medium medium, Graphics2D graphics2D, xtGraphics xtGraphics2, GameSparker gameSparker) {
        this.m = medium;
        this.rd = graphics2D;
        this.xt = xtGraphics2;
        this.gs = gameSparker;
        if (this.xt.playingame != -1) {
            this.fase = 18;
        }
        if (this.xt.nofull) {
            this.nservers = 1;
        }
    }

    public void inishmulti() {
        int n;
        this.gs.tnick.hide();
        this.gs.tnick.enable();
        this.gs.tnick.setForeground(new Color(0, 0, 0));
        this.gs.tnick.setBackground(this.color2k(240, 240, 240));
        this.gs.tpass.hide();
        this.gs.tpass.enable();
        this.gs.tpass.setForeground(new Color(0, 0, 0));
        this.gs.tpass.setBackground(this.color2k(240, 240, 240));
        this.gs.temail.hide();
        this.gs.temail.enable();
        this.gs.temail.setForeground(new Color(0, 0, 0));
        this.gs.temail.setBackground(this.color2k(240, 240, 240));
        this.gs.keplo.hide();
        this.gs.keplo.enable();
        this.gs.keplo.setForeground(new Color(0, 0, 0));
        this.gs.keplo.setBackground(new Color(193, 181, 142));
        this.gs.requestFocus();
        if (this.gs.tnick.getText().equals("")) {
            this.gs.tnick.setText("Nickname");
        }
        for (n = 0; n < 6; ++n) {
            this.pessd[n] = false;
        }
        this.nflk = 0;
        this.ncnt = 0;
        this.errcnt = 0;
        this.onf = false;
        this.ond = false;
        this.msg = "";
        this.btroom = false;
        this.gotcai = false;
        this.m.crs = true;
        this.m.x = -335;
        this.m.y = 0;
        this.m.z = -50;
        this.m.xz = 0;
        this.m.zy = 20;
        this.m.ground = -2000;
        this.pend = 0;
        this.pendb = false;
        this.resofaso = false;
        for (n = 0; n < this.nservers; ++n) {
            this.serverdone[n] = -1;
            this.servestart[n] = 0L;
        }
        this.checknote = false;
        if (this.xt.gotlog) {
            this.checknote = true;
            this.socketson = false;
            this.fase = 12;
            this.connector = new Thread(this);
            this.connector.start();
        } else {
            this.msg = "Login to access the multiplayer madness!";
            this.gs.tnick.setText(this.xt.nickname);
            this.fase = 3;
        }
    }

    public void exitfromlobby() {
        int n;
        this.opselect = !this.xt.lan ? 0 : 1;
        for (n = 0; n < this.nservers; ++n) {
            this.serverdone[n] = -1;
            this.servestart[n] = 0L;
        }
        for (n = 0; n < 6; ++n) {
            this.pessd[n] = false;
        }
        this.gotcai = false;
        this.btroom = false;
        this.m.crs = true;
        this.m.x = -335;
        this.m.y = 0;
        this.m.z = -50;
        this.m.xz = 0;
        this.m.zy = 20;
        this.m.ground = -2000;
        this.pend = 0;
        this.pendb = false;
        this.gamec = -1;
        this.socketson = false;
        if (!this.xt.lan) {
            this.msg = "| Connecting to Servers |";
            this.trans = 0;
            this.fase = 13;
            this.nflk = 0;
        } else {
            this.fase = 12;
        }
        System.gc();
        this.connector = new Thread(this);
        this.connector.start();
    }

    public void endcons() {
        for (int i = 0; i < this.nservers; ++i) {
            try {
                this.dSocket[i].close();
                this.dSocket[i] = null;
                continue;
            }
            catch (Exception exception) {
                // empty catch block
            }
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
    }

    public void checknotifcations() {
        int n;
        String string;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        block16: {
            n6 = 0;
            n5 = 0;
            n4 = 0;
            n3 = 0;
            n2 = 0;
            string = "";
            n = 0;
            try {
                URL uRL = new URL("http://multiplayer.needformadness.com/profiles/" + this.xt.nickname + "/notify.txt?req=" + (int)(Math.random() * 1000.0) + "");
                uRL.openConnection().setConnectTimeout(2000);
                String string2 = uRL.openConnection().getContentType();
                if (!string2.equals("text/plain")) break block16;
                DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                String string3 = "";
                for (int i = 0; (string3 = dataInputStream.readLine()) != null && i < 5; ++i) {
                    String string4;
                    string3 = string3.trim();
                    if (i == 0) {
                        string4 = this.getSvalue(string3, n6);
                        while (!string4.equals("")) {
                            if (string4.startsWith("clan: ")) {
                                ++n5;
                            } else if (!string4.startsWith("your clan")) {
                                ++n4;
                            }
                            string4 = this.getSvalue(string3, ++n6);
                        }
                    }
                    if (i == 1) {
                        int n7 = 0;
                        try {
                            n7 = Integer.valueOf(string3);
                        }
                        catch (Exception exception) {
                            n7 = 0;
                        }
                        n3 = n7;
                    }
                    if (i == 2) {
                        int n8 = 0;
                        try {
                            n8 = Integer.valueOf(string3);
                        }
                        catch (Exception exception) {
                            n8 = 0;
                        }
                        n2 = n8;
                    }
                    if (i == 3) {
                        string = this.getSvalue(string3, 0);
                    }
                    if (i != 4) continue;
                    string4 = this.getSvalue(string3, n);
                    while (!string4.equals("")) {
                        string4 = this.getSvalue(string3, ++n);
                    }
                }
                dataInputStream.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.nmsgs = n6;
        this.fclan = n5;
        this.fplayer = n4;
        this.nfreq = n3;
        this.nconf = n2;
        this.clanapv = string;
        this.ncreq = n;
    }

    public void gamealert() {
        try {
            this.socket = new Socket(this.servers[0], 7061);
            this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.dout = new PrintWriter(this.socket.getOutputStream(), true);
            this.dout.println("101|20|" + this.xt.nickname + "|" + this.xt.nickey + "|" + this.xt.servername + "|" + (this.xt.servport - 7070) + "|");
            String string = this.din.readLine();
            this.socket.close();
            this.din.close();
            this.dout.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void checkgamealerts() {
        try {
            int n;
            this.socket = new Socket(this.servers[0], 7061);
            this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.dout = new PrintWriter(this.socket.getOutputStream(), true);
            this.dout.println("101|21|");
            String string = this.din.readLine();
            if (string != null && (n = this.getvalue(string, 0)) != -1 && n != this.gamec) {
                String string2 = this.getSvalue(string, 2);
                int n2 = this.getvalue(string, 3);
                boolean bl = false;
                if (!string2.equals(this.xt.servername) || n2 != this.xt.servport - 7070) {
                    for (int i = 0; i < this.nservers; ++i) {
                        if (!string2.equals(this.snames[i]) || this.xt.delays[i] >= 300) continue;
                        bl = true;
                    }
                }
                if (bl) {
                    this.gmaker = this.getSvalue(string, 1);
                    if (this.gmaker.equals(this.xt.nickname)) {
                        this.gmaker = "You";
                    }
                    this.groom = n2;
                    this.gservern = string2;
                    this.gamec = n;
                    this.cntgame = 0;
                }
            }
            this.socket.close();
            this.din.close();
            this.dout.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void run() {
        String string;
        Object object;
        int n;
        int n2;
        if (this.checknote) {
            this.checknotifcations();
            this.checknote = false;
        }
        if (this.fase == 2) {
            this.gs.setCursor(new Cursor(3));
            n2 = -1;
            n = -1;
            try {
                this.socket = new Socket(this.servers[0], 7061);
                this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                this.dout.println("0|" + this.gs.tnick.getText() + "|");
                object = this.din.readLine();
                if (object != null && (n2 = this.getvalue((String)object, 0)) == 0) {
                    n = this.getvalue((String)object, 1);
                    this.xt.hours = this.getvalue((String)object, 2);
                    this.xt.nickey = this.getSvalue((String)object, 3);
                }
                this.socket.close();
                this.din.close();
                this.dout.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.gs.tnick.enable();
            if (n2 == -1) {
                this.msg = "Unable to connect to any server at this moment.  Please try again later.";
                this.fase = 1;
            }
            if (n2 == 0) {
                this.xt.nickname = this.gs.tnick.getText();
                if (n != -1) {
                    this.xt.nfreeplays = n;
                }
                this.gs.tnick.hide();
                this.gs.tpass.hide();
                this.gs.temail.hide();
                this.gs.keplo.hide();
                this.gs.requestFocus();
                this.xt.logged = false;
                this.fase = 12;
                System.gc();
            }
            if (n2 == 1) {
                this.msg = "This Nickname is being used by someone else right now.  Please use another.";
                this.nickero = true;
                this.gs.tnick.setForeground(new Color(255, 0, 0));
                this.gs.tnick.requestFocus();
                this.errcnt = 30;
                this.fase = 1;
            }
            if (n2 == 2) {
                this.msg = "Nickname registerd.  Please use another or click 'Login' bellow to login to this Nickname.";
                this.nickero = true;
                this.gs.tnick.setForeground(new Color(255, 0, 0));
                this.gs.tnick.requestFocus();
                this.errcnt = 30;
                this.fase = 1;
            }
            this.gs.setCursor(new Cursor(0));
        }
        if (this.fase == 4) {
            this.gs.setCursor(new Cursor(3));
            n2 = -1;
            n = -1;
            object = "";
            try {
                this.socket = new Socket(this.servers[0], 7061);
                this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                this.dout.println("1|" + this.gs.tnick.getText() + "|" + this.gs.tpass.getText() + "|");
                object = this.din.readLine();
                if (object != null && ((n2 = this.getvalue((String)object, 0)) == 0 || n2 == 3 || n2 > 10 || n2 == -167)) {
                    this.xt.nickey = this.getSvalue((String)object, 1);
                    if (n2 != -167) {
                        this.xt.clan = this.getSvalue((String)object, 2);
                        this.xt.clankey = this.getSvalue((String)object, 3);
                    } else {
                        this.xt.clan = "";
                        this.xt.clankey = "";
                        n = this.getvalue((String)object, 2);
                        this.xt.hours = this.getvalue((String)object, 3);
                    }
                }
                this.socket.close();
                this.din.close();
                this.dout.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.gs.tnick.enable();
            this.gs.tpass.enable();
            this.gs.keplo.enable();
            if (n2 == -1) {
                this.msg = "Unable to connect to server at this moment.  Please try again later.";
                this.fase = 3;
            }
            if (n2 == 0 || n2 == 3 || n2 > 10 || n2 == -167 || n2 == 111) {
                this.xt.nickname = this.gs.tnick.getText();
                this.showtf = false;
                this.gs.tnick.hide();
                this.gs.tpass.hide();
                this.gs.temail.hide();
                this.gs.keplo.hide();
                this.gs.requestFocus();
                this.gs.setloggedcookie();
                this.btroom = false;
                this.xt.logged = true;
                this.xt.gotlog = true;
                if (n2 == 0) {
                    this.xt.acexp = 0;
                }
                if (n2 > 10) {
                    this.xt.acexp = n2 - 10;
                }
                if (n2 == 3) {
                    this.xt.acexp = -1;
                }
                if (n2 == -167) {
                    this.xt.logged = false;
                    if (n != -1) {
                        this.xt.nfreeplays = n;
                    }
                }
                if (this.xt.logged) {
                    this.xt.backlog = this.xt.nickname;
                }
                this.fase = 12;
                this.justlog = true;
                this.checknotifcations();
                System.gc();
            }
            if (n2 == 1) {
                this.msg = "Sorry.  The Nickname you have entered is incorrect or does not exist.";
                this.gs.tnick.setForeground(new Color(255, 0, 0));
                this.gs.tnick.requestFocus();
                this.errcnt = 40;
                this.fase = 3;
            }
            if (n2 == 2) {
                this.msg = "Sorry.  The Password you have entered is incorrect.";
                this.gs.tpass.setForeground(new Color(255, 0, 0));
                this.gs.tpass.requestFocus();
                this.errcnt = 40;
                this.fase = 3;
            }
            this.gs.setCursor(new Cursor(0));
        }
        if (this.fase == 8) {
            this.gs.setCursor(new Cursor(3));
            n2 = -1;
            try {
                this.socket = new Socket(this.servers[0], 7061);
                this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                this.dout.println("2|" + this.gs.temail.getText().toLowerCase() + "|");
                String string2 = this.din.readLine();
                if (string2 != null) {
                    n2 = this.getvalue(string2, 0);
                }
                this.socket.close();
                this.din.close();
                this.dout.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.gs.temail.enable();
            if (n2 == -1) {
                this.msg = "Unable to connect to server at this moment.  Please try again later.";
                this.fase = 7;
            }
            if (n2 == 0) {
                this.showtf = false;
                this.gs.temail.hide();
                this.msg = "Please check your Email: " + this.gs.temail.getText() + " to login.";
                this.gs.temail.setText("");
                this.gs.tnick.setText("");
                this.gs.tpass.setText("");
                this.fase = 3;
            }
            if (n2 == 1) {
                this.msg = "Sorry.  This Email Address does not exist in our system!";
                this.gs.temail.setForeground(new Color(255, 0, 0));
                this.errcnt = 40;
                this.fase = 7;
            }
            this.gs.setCursor(new Cursor(0));
        }
        if (!(this.fase != 12 && this.fase != 13 && this.fase != 14 && this.fase != 15 || this.socketson)) {
            for (n2 = 0; n2 < this.nservers; ++n2) {
                try {
                    this.serverdone[n2] = -1;
                    this.IPAddress[n2] = InetAddress.getByName(this.servers[n2]);
                    this.dSocket[n2] = new DatagramSocket(7001 + n2);
                    continue;
                }
                catch (Exception exception) {
                    this.serverdone[n2] = 5;
                }
            }
            this.srvtrn = 0;
            this.socketson = true;
        }
        while (this.fase == 12 || this.fase == 13 || this.fase == 14 || this.fase == 15) {
            if (this.srvtrn < this.nservers) {
                while (this.serverdone[this.srvtrn] < this.xt.cntptrys) {
                    if (this.serverdone[this.srvtrn] == -1) {
                        this.serverdone[this.srvtrn] = 0;
                    }
                    Date date = new Date();
                    this.servestart[this.srvtrn] = date.getTime();
                    try {
                        byte[] byArray = new byte[4];
                        object = new DatagramPacket(byArray, byArray.length, this.IPAddress[this.srvtrn], 7000);
                        string = "" + this.xt.nickname + "|";
                        byte[] byArray2 = string.getBytes();
                        ((DatagramPacket)object).setData(byArray2);
                        this.dSocket[this.srvtrn].send((DatagramPacket)object);
                        this.dSocket[this.srvtrn].receive((DatagramPacket)object);
                        String string3 = new String(((DatagramPacket)object).getData());
                        if (string3.startsWith("OK") && (date = new Date()).getTime() - this.servestart[this.srvtrn] < (long)this.xt.delays[this.srvtrn]) {
                            this.xt.delays[this.srvtrn] = (int)(date.getTime() - this.servestart[this.srvtrn]);
                        }
                    }
                    catch (Exception exception) {
                        this.xt.delays[this.srvtrn] = 600;
                        this.serverdone[this.srvtrn] = 5;
                    }
                    int n3 = this.srvtrn;
                    this.serverdone[n3] = this.serverdone[n3] + 1;
                }
                ++this.srvtrn;
            } else if (this.fase == 13) {
                int n4 = -1;
                boolean bl = false;
                for (int i = 0; i < this.nservers; ++i) {
                    if (this.xt.delays[i] < n4 || n4 == -1) {
                        n4 = this.xt.delays[i];
                        this.opselect = i;
                    }
                    if (this.xt.delays[i] < 600) continue;
                    bl = true;
                }
                if (!bl) {
                    this.xt.cntptrys -= 2;
                    if (this.xt.cntptrys < 1) {
                        this.xt.cntptrys = 1;
                    }
                }
                this.fase = 14;
            }
            try {
                Thread.sleep(5L);
            }
            catch (InterruptedException interruptedException) {}
        }
        if (this.fase != 12 && this.fase != 13 && this.fase != 14 && this.fase != 15 && this.fase != 5 && this.socketson) {
            for (int i = 0; i < this.nservers; ++i) {
                try {
                    this.dSocket[i].close();
                    this.dSocket[i] = null;
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            this.socketson = false;
        }
        if (this.fase == 16 || this.fase == 17) {
            boolean bl = false;
            int n5 = 0;
            int n6 = -1;
            this.recom = 0;
            try {
                this.socket = new Socket(this.xt.server, 7067);
                this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.dout = new PrintWriter(this.socket.getOutputStream(), true);
            }
            catch (Exception exception) {
                // empty catch block
            }
            while ((this.fase == 16 || this.fase == 17) && n5 != 3) {
                string = "";
                if (!bl) {
                    String string4;
                    try {
                        this.dout.println("10|");
                        string4 = this.din.readLine();
                        if (string4 == null) {
                            bl = true;
                        } else {
                            string = string4;
                        }
                    }
                    catch (Exception exception) {
                        bl = true;
                    }
                    if (bl) {
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
                            this.socket = new Socket(this.xt.server, 7067);
                            this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                            this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                            this.dout.println("10|");
                            string4 = this.din.readLine();
                            if (string4 != null) {
                                bl = false;
                            } else {
                                string = string4;
                            }
                        }
                        catch (Exception exception) {
                            bl = true;
                        }
                    }
                    if (bl) {
                        try {
                            this.socket.close();
                            this.socket = null;
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                }
                if (!bl) {
                    int n7;
                    for (n7 = 0; n7 < 5; ++n7) {
                        this.rmps[n7] = this.getvalue(string, n7 * 2);
                        this.rmwt[n7] = this.getvalue(string, 1 + n7 * 2);
                    }
                    n7 = 1000;
                    for (int i = 0; i < 5; ++i) {
                        if (Math.abs(this.rmps[i] - 6) >= n7) continue;
                        this.recom = i;
                        n7 = Math.abs(this.rmps[i] - 6);
                    }
                    if (this.recom != n6) {
                        this.opselect = this.recom;
                        n6 = this.recom;
                    }
                    if (this.fase == 16) {
                        this.fase = 17;
                    }
                } else {
                    this.msg = "Failed to connect to this Server!";
                    ++n5;
                }
                if (n5 == 3) continue;
                try {
                    Thread.sleep(2000L);
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
            if (n5 == 3) {
                this.resofaso = true;
            }
        }
    }

    public void stopallnow() {
        if (this.connector != null) {
            this.connector.stop();
            this.connector = null;
        }
        this.endcons();
    }

    public void multimode(ContO[] contOArray) {
        Date date;
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        this.btn = 0;
        this.xt.mainbg(4);
        for (int i = 0; i < 3; ++i) {
            this.rd.drawImage(this.xt.bgmain, 65, this.bgmy[i], null);
            int n7 = i;
            this.bgmy[n7] = this.bgmy[n7] - 4;
            if (this.bgmy[i] > -400) continue;
            this.bgmy[i] = 800;
        }
        this.rd.setComposite(AlphaComposite.getInstance(3, 0.2f));
        this.rd.drawImage(this.xt.bggo, 0, 0, null);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.fillRect(65, 425, 670, 25);
        this.rd.fillRect(0, 0, 65, 450);
        this.rd.fillRect(735, 0, 65, 450);
        float f = 1.0f - (float)(this.flipo - 10) / 80.0f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.rd.setComposite(AlphaComposite.getInstance(3, f));
        if (this.flipo > 10) {
            this.rd.drawImage(this.xt.logomadnes, 96 + (int)(2.0 - Math.random() * 4.0), 11 + (int)(2.0 - Math.random() * 4.0), null);
        } else {
            this.rd.drawImage(this.xt.logomadnes, 96, 11, null);
        }
        ++this.flipo;
        if (this.flipo > 50) {
            this.flipo = 0;
        }
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        this.drawSbutton(this.xt.exit, 690, 17);
        this.rd.setComposite(AlphaComposite.getInstance(3, 0.25f));
        this.rd.setColor(new Color(203, 227, 253));
        this.rd.fillRoundRect(319, 83, 180, 96, 20, 20);
        this.rd.fillRoundRect(173, 83, 132, 32, 20, 20);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        this.rd.setColor(this.color2k(90, 90, 90));
        this.rd.drawRoundRect(319, 83, 180, 96, 20, 20);
        this.rd.drawRoundRect(173, 83, 132, 32, 20, 20);
        if (!this.gotcai) {
            n5 = n6 = contOArray[this.xt.sc[0]].p[0].oz[0];
            n3 = n4 = contOArray[this.xt.sc[0]].p[0].oy[0];
            for (n2 = 0; n2 < contOArray[this.xt.sc[0]].npl; ++n2) {
                for (n = 0; n < contOArray[this.xt.sc[0]].p[n2].n; ++n) {
                    if (contOArray[this.xt.sc[0]].p[n2].oz[n] < n6) {
                        n6 = contOArray[this.xt.sc[0]].p[n2].oz[n];
                    }
                    if (contOArray[this.xt.sc[0]].p[n2].oz[n] > n5) {
                        n5 = contOArray[this.xt.sc[0]].p[n2].oz[n];
                    }
                    if (contOArray[this.xt.sc[0]].p[n2].oy[n] < n4) {
                        n4 = contOArray[this.xt.sc[0]].p[n2].oy[n];
                    }
                    if (contOArray[this.xt.sc[0]].p[n2].oy[n] <= n3) continue;
                    n3 = contOArray[this.xt.sc[0]].p[n2].oy[n];
                }
            }
            this.cax = (n5 + n6) / 2;
            this.cay = (n3 + n4) / 2;
            this.gotcai = true;
        }
        contOArray[this.xt.sc[0]].z = 1500;
        contOArray[this.xt.sc[0]].y = 380 - this.cay;
        contOArray[this.xt.sc[0]].x = 100 - this.cax;
        contOArray[this.xt.sc[0]].zy = 0;
        contOArray[this.xt.sc[0]].xz = -90;
        contOArray[this.xt.sc[0]].xy = this.pend;
        this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        contOArray[this.xt.sc[0]].d(this.rd);
        this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
        this.rd.setFont(new Font("Arial", 1, 13));
        this.ftm = this.rd.getFontMetrics();
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.drawString(this.xt.nickname, 239 - this.ftm.stringWidth(this.xt.nickname) / 2, 105);
        this.rd.setColor(this.color2k(90, 90, 90));
        this.rd.drawString("" + this.xt.cd.names[this.xt.sc[0]] + "", 409 - this.ftm.stringWidth("" + this.xt.cd.names[this.xt.sc[0]] + "") / 2, 81);
        this.rd.drawString("Nickname", 239 - this.ftm.stringWidth("Nickname") / 2, 81);
        this.drawbutton(this.xt.change, 570, 98);
        this.drawSbutton(this.xt.logout, 239, 135);
        this.rd.setColor(new Color(98, 56, 0));
        this.rd.drawString("Edit my Account", 239 - this.ftm.stringWidth("Edit my Account") / 2, 168);
        if (this.ond) {
            this.rd.drawLine(239 - this.ftm.stringWidth("Edit my Account") / 2, 169, 239 - this.ftm.stringWidth("Edit my Account") / 2 + this.ftm.stringWidth("Edit my Account"), 169);
        }
        if ((this.fase == 12 || this.fase == 13 || this.fase == 14 || this.fase == 15) && (n6 = this.srvtrn) < this.nservers && this.serverdone[n6] != -1 && (date = new Date()).getTime() - this.servestart[n6] > 1500L) {
            if (this.connector != null) {
                this.connector.stop();
                this.connector = null;
            }
            this.xt.delays[n6] = 600;
            this.serverdone[n6] = 5;
            this.connector = new Thread(this);
            this.connector.start();
        }
        if (this.fase == 12) {
            if (this.xt.acexp == 0 || this.contrb) {
                this.rd.setComposite(AlphaComposite.getInstance(3, 0.25f));
                this.rd.setColor(new Color(203, 227, 253));
                this.rd.fillRoundRect(205, 225, 390, 120, 20, 20);
                this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                this.rd.setColor(this.color2k(90, 90, 90));
                this.rd.drawString("Multiplayer Mode", 400 - this.ftm.stringWidth("Multiplayer Mode") / 2, 220);
                this.rd.drawRoundRect(205, 225, 390, 120, 20, 20);
                if (this.opselect == 0 && !this.pessd[3]) {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 0.25f));
                    this.rd.setColor(new Color(203, 227, 253));
                    this.rd.fillRect(387 - this.xt.pon.getWidth(this.ob) / 2, 242, this.xt.pon.getWidth(this.ob) + 26, 26);
                    this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                }
                this.drawbutton(this.xt.pon, 400, 255);
                if (this.opselect == 1 && !this.pessd[4]) {
                    this.rd.setComposite(AlphaComposite.getInstance(3, 0.25f));
                    this.rd.setColor(new Color(203, 227, 253));
                    this.rd.fillRect(387 - this.xt.pln.getWidth(this.ob) / 2, 302, this.xt.pln.getWidth(this.ob) + 26, 26);
                    this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                }
                this.drawbutton(this.xt.pln, 400, 315);
                if (!this.xt.logged) {
                    this.rd.setColor(new Color(30, 70, 110));
                    this.rd.drawString("You can play 5 multiplayer turns per day to try the game with your trial account.", 400 - this.ftm.stringWidth("You can play 1 multiplayer turn per day to try the game with your trial account.") / 2, 368);
                    this.rd.drawString("Upgrade your account to purchase the game.", 400 - this.ftm.stringWidth("You can play 1 multiplayer turn per day to try the game with your trial account.") / 2, 385);
                    this.drawSbutton(this.xt.upgrade, 400, 406);
                }
            } else {
                this.rd.setComposite(AlphaComposite.getInstance(3, 0.25f));
                this.rd.setColor(new Color(203, 227, 253));
                this.rd.fillRoundRect(165, 219, 470, 135, 20, 20);
                this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                this.rd.setColor(this.color2k(90, 90, 90));
                this.rd.drawRoundRect(165, 219, 470, 135, 20, 20);
                if (this.xt.acexp > 0) {
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Dear " + this.xt.nickname + ",", 185, 245);
                    this.rd.drawString("Your account is due to expire in " + this.xt.acexp + " days.", 185, 265);
                    this.rd.drawString("Renew your registration soon!", 185, 295);
                    this.stringbutton("Renew my Account Registration now!", 345, 332, 0);
                    this.stringbutton("Renew Later", 524, 332, 0);
                }
                if (this.xt.acexp == -1) {
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Dear " + this.xt.nickname + ",", 185, 245);
                    this.rd.drawString("Your Need for Madness account registration has expired.", 185, 265);
                    this.rd.drawString("Please renew your registration.", 185, 295);
                    this.stringbutton("Renew my account registration now!", 362, 332, 0);
                    this.stringbutton("Cancel", 524, 332, 0);
                }
                if (this.xt.acexp == -2) {
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Dear " + this.xt.nickname + ",", 185, 245);
                    this.rd.drawString("Trial accounts are not allowed to access the downloaded game.", 185, 265);
                    this.rd.drawString("You can only play the game online using your trial account.", 185, 295);
                    this.stringbutton("Play the multiplayer online!", 362, 332, 0);
                    this.stringbutton("Cancel", 524, 332, 0);
                }
                if (this.xt.acexp == -3) {
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Some one else is already logged in the game with your account.", 185, 245);
                    this.rd.drawString("If you where just in the game then quitted it suddenly, it could just", 185, 265);
                    this.rd.drawString("be your 'ghost entry', if so please wait a few minutes and try again.", 185, 285);
                    this.rd.drawString("Otherwise please consider changing your password.", 185, 305);
                    this.stringbutton("Change Password", 332, 336, 0);
                    this.stringbutton("Try Again", 494, 336, 0);
                }
            }
        }
        if (this.fase == 13 || this.fase == 14 || this.fase == 16 || this.fase == 17) {
            if (this.trans < 40) {
                this.rd.drawImage(this.xt.pon, 400 - this.xt.pon.getWidth(this.ob) / 2, 255 - this.xt.pon.getHeight(this.ob) / 2 - 12 - this.trans, null);
            } else {
                this.rd.drawImage(this.xt.pon, 400 - this.xt.pon.getWidth(this.ob) / 2, 215 - this.xt.pon.getHeight(this.ob) / 2 - 12, null);
            }
            if (this.trans >= 40) {
                this.rd.setComposite(AlphaComposite.getInstance(3, 0.25f));
                this.rd.setColor(new Color(203, 227, 253));
                this.rd.fillRoundRect(165, 219, 470, 150, 20, 20);
                this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                this.rd.setColor(this.color2k(90, 90, 90));
                this.rd.drawRoundRect(165, 219, 470, 150, 20, 20);
                this.drawbutton(this.xt.cancel, 583, 395);
                this.rd.setFont(new Font("Arial", 1, 13));
                this.ftm = this.rd.getFontMetrics();
                if (this.fase == 13) {
                    this.rd.drawString(this.msg, 400 - this.ftm.stringWidth(this.msg) / 2, 295);
                    if (this.msg.equals(". . . | Connecting to Servers | . . .") && this.ncnt == 0) {
                        this.msg = "| Connecting to Servers |";
                        this.ncnt = 5;
                    }
                    if (this.msg.equals(". . | Connecting to Servers | . .") && this.ncnt == 0) {
                        this.msg = ". . . | Connecting to Servers | . . .";
                        this.ncnt = 5;
                    }
                    if (this.msg.equals(". | Connecting to Servers | .") && this.ncnt == 0) {
                        this.msg = ". . | Connecting to Servers | . .";
                        this.ncnt = 5;
                    }
                    if (this.msg.equals("| Connecting to Servers |") && this.ncnt == 0) {
                        this.msg = ". | Connecting to Servers | .";
                        this.ncnt = 5;
                    }
                    if (this.ncnt != 0) {
                        --this.ncnt;
                    }
                }
                if (this.fase == 16) {
                    this.rd.drawString(this.msg, 400 - this.ftm.stringWidth(this.msg) / 2, 295);
                    if (this.msg.equals(". . . | Finding Rooms | . . .") && this.ncnt == 0) {
                        this.msg = "| Finding Rooms |";
                        this.ncnt = 5;
                    }
                    if (this.msg.equals(". . | Finding Rooms | . .") && this.ncnt == 0) {
                        this.msg = ". . . | Finding Rooms | . . .";
                        this.ncnt = 5;
                    }
                    if (this.msg.equals(". | Finding Rooms | .") && this.ncnt == 0) {
                        this.msg = ". . | Finding Rooms | . .";
                        this.ncnt = 5;
                    }
                    if (this.msg.equals("| Finding Rooms |") && this.ncnt == 0) {
                        this.msg = ". | Finding Rooms | .";
                        this.ncnt = 5;
                    }
                    if (this.ncnt != 0) {
                        --this.ncnt;
                    }
                }
                if (this.fase == 14) {
                    this.msg = "";
                    n6 = 0;
                    if (!this.xt.nofull) {
                        for (n5 = 0; n5 < this.nservers; ++n5) {
                            if (this.xt.delays[n5] < 400) continue;
                            ++n6;
                        }
                    }
                    if (n6 != this.nservers) {
                        n5 = 0;
                        n4 = 0;
                        for (n3 = 0; n3 < this.nservers; ++n3) {
                            this.rd.setColor(new Color(0, 0, 0));
                            if (this.opselect == n3) {
                                this.rd.setColor(new Color(98, 56, 0));
                                this.rd.fillRoundRect(175, 230 + n3 * 20, 450, 20, 14, 14);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawRoundRect(175, 230 + n3 * 20, 450, 20, 14, 14);
                                this.rd.setColor(this.color2k(255, 255, 255));
                                if (this.xt.delays[n3] >= 400) {
                                    this.msg = "Your connection to this server is too slow!";
                                    n4 = 1;
                                }
                                if (this.xt.delays[n3] == 600) {
                                    this.msg = "This server is not responding!";
                                    n4 = 1;
                                }
                                if (this.xt.delays[n3] < 400) {
                                    for (n2 = 0; n2 < this.nservers; ++n2) {
                                        if (this.xt.delays[n2] >= this.xt.delays[n3] || n3 == n2) continue;
                                        n5 = 1;
                                    }
                                    if (n5 != 0) {
                                        this.msg = "It is recommended to choose the fastest server.";
                                    } else if (this.xt.delays[n3] >= 300) {
                                        this.msg = "Your connection speed is not perfect.  You may encounter delay!";
                                    }
                                }
                            }
                            this.rd.drawString("Server Name :", 195, 245 + n3 * 20);
                            this.rd.drawString("|   Delay/Speed :", 385, 245 + n3 * 20);
                            n2 = 0;
                            n = 0;
                            String string = "" + this.xt.delays[n3] + "/";
                            if (this.xt.delays[n3] < 75) {
                                string = string + "EXCELENT *****";
                                n2 = 62;
                                n = 100;
                            }
                            if (this.xt.delays[n3] >= 75 && this.xt.delays[n3] < 150) {
                                string = string + "Perfect ****";
                                n2 = 62;
                                n = 100;
                            }
                            if (this.xt.delays[n3] >= 150 && this.xt.delays[n3] < 250) {
                                string = string + "Good ***";
                                n2 = 81;
                                n = 100;
                            }
                            if (this.xt.delays[n3] >= 250 && this.xt.delays[n3] < 400) {
                                string = string + "Acceptable **";
                                n2 = 100;
                                n = 100;
                            }
                            if (this.xt.delays[n3] >= 400 && this.xt.delays[n3] < 600) {
                                string = string + "Poor";
                                n2 = 100;
                                n = 0;
                            }
                            if (this.xt.delays[n3] == 600) {
                                string = "Not Responding";
                            }
                            if (this.opselect == n3) {
                                n2 = (int)((float)n2 * 2.55f);
                                n = (int)((float)n * 2.55f);
                            }
                            if (n2 > 255) {
                                n2 = 255;
                            }
                            if (n2 < 0) {
                                n2 = 0;
                            }
                            if (n > 255) {
                                n = 255;
                            }
                            if (n < 0) {
                                n = 0;
                            }
                            this.rd.setColor(new Color(n2, n, 0));
                            this.rd.drawString("" + this.snames[n3] + "", 294, 245 + n3 * 20);
                            this.rd.drawString(string, 497, 245 + n3 * 20);
                        }
                        if (!this.xt.logged && this.xt.nfreeplays - this.xt.ndisco >= 5 && n4 == 0) {
                            this.msg = "You have played your 5 games today, please upgrade your account.";
                            n4 = 2;
                        }
                        if (this.xt.nofull) {
                            if (this.nflk % 4 != 0 || this.nflk == 0) {
                                this.rd.setFont(new Font("Arial", 0, 13));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.setColor(new Color(200, 0, 0));
                                this.rd.drawString("Warning! You did not allow the game full permissions when you started it.", 175, 275);
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawString("(You didn't click 'Run' at the prompt that came up at the start of the game).", 175, 292);
                                this.rd.drawString("Because of this you will be able to connect to ONLY the game's main server:", 175, 309);
                                this.rd.drawString("'" + this.snames[0] + "', which is not necessarily the fastest server you can connect to.", 175, 326);
                                this.rd.drawString("Please allow Java full permissions next time to be able to play on all servers!", 175, 343);
                                this.rd.setFont(new Font("Arial", 1, 13));
                                this.ftm = this.rd.getFontMetrics();
                            }
                        } else {
                            if (n4 == 0) {
                                this.rd.setColor(new Color(98, 56, 0));
                            }
                            if (n4 == 1) {
                                this.rd.setColor(new Color(200, 0, 0));
                            }
                            if (n4 == 2) {
                                this.rd.setColor(new Color(30, 70, 110));
                            }
                            if (this.nflk % 4 != 0 || this.nflk == 0) {
                                this.rd.drawString(this.msg, 400 - this.ftm.stringWidth(this.msg) / 2, 360);
                            }
                            if (this.nflk != 0) {
                                --this.nflk;
                            }
                        }
                    } else {
                        if (this.nflk % 4 != 0 || this.nflk == 0) {
                            this.rd.setColor(new Color(200, 0, 0));
                            this.rd.drawString("Sorry.  Your connection is currently not fast enough to play online!", 400 - this.ftm.stringWidth("Sorry.  Your connection is currently not fast enough to play online!") / 2, 242);
                        }
                        if (this.nflk != 0) {
                            --this.nflk;
                        }
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.setFont(new Font("Arial", 0, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Please make sure you or anyone else using this connection is not slowing", 181, 265);
                        this.rd.drawString("it down right now by downloading or streaming.", 181, 282);
                        this.rd.drawString("Also please make sure you don't have any other programs running on your", 181, 299);
                        this.rd.drawString("computer that maybe consuming your bandwidth.", 181, 316);
                        this.rd.drawString("Otherwise you may need to upgrade your connection speed to play!", 181, 333);
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Press 'Cancel' to try again or to try playing a Lan game instead.", 400 - this.ftm.stringWidth("Press 'Cancel' to try again or to try playing a Lan game instead.") / 2, 357);
                    }
                    this.drawbutton(this.xt.play, 400, 395);
                }
                if (this.fase == 17) {
                    n6 = 14;
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString(": :   " + this.xt.servername + "   : :", 400 - this.ftm.stringWidth(": :   " + this.xt.servername + "   : :") / 2, 239);
                    for (n5 = 0; n5 < 5; ++n5) {
                        if (this.opselect == n5) {
                            this.rd.setColor(new Color(98, 56, 0));
                            this.rd.fillRoundRect(300, 230 + n5 * 20 + n6, 200, 20, 14, 14);
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawRoundRect(300, 230 + n5 * 20 + n6, 200, 20, 14, 14);
                            this.rd.setColor(this.color2k(255, 255, 255));
                        }
                        this.rd.drawString("Room " + (n5 + 1) + " :", 329, 245 + n5 * 20 + n6);
                        this.rd.drawString("" + this.rmps[n5] + "  Players", 471 - this.ftm.stringWidth("" + this.rmps[n5] + "  Players"), 245 + n5 * 20 + n6);
                        if (n5 == this.recom) {
                            if (this.opselect != n5) {
                                this.rd.setColor(new Color(125, 200, 0));
                            } else {
                                this.rd.setColor(new Color(160, 255, 0));
                            }
                        }
                        this.rd.setColor(new Color(0, 0, 0));
                    }
                    this.drawbutton(this.xt.play, 400, 395);
                }
            } else {
                this.trans += 8;
            }
        }
        if (this.fase == 15) {
            if (this.trans < 100) {
                this.rd.drawImage(this.xt.pln, 400 - this.xt.pln.getWidth(this.ob) / 2, 315 - this.xt.pln.getHeight(this.ob) / 2 - 12 - this.trans, null);
            } else {
                this.rd.drawImage(this.xt.pln, 400 - this.xt.pln.getWidth(this.ob) / 2, 215 - this.xt.pln.getHeight(this.ob) / 2 - 12, null);
            }
            if (this.trans >= 100) {
                this.rd.setColor(this.color2k(255, 255, 255));
                this.rd.fillRoundRect(165, 219, 470, 150, 20, 20);
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.drawRoundRect(165, 219, 470, 150, 20, 20);
                this.rd.setFont(new Font("Arial", 1, 13));
                this.ftm = this.rd.getFontMetrics();
                if (this.xt.nofull) {
                    if (this.nflk % 4 != 0 || this.nflk == 0) {
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(200, 0, 0));
                        this.rd.drawString("Sorry. You did not allow the game full permissions when you started it.", 175, 242);
                        this.rd.setFont(new Font("Arial", 0, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("(You didn't click 'Run' at the prompt that came up at the start of the game).", 175, 262);
                        this.rd.drawString("Because of this the game will not be able to create LAN connections!", 175, 288);
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Please restart the game and allow Java full permissions to be able to", 175, 315);
                        this.rd.drawString("play LAN games!", 175, 332);
                        this.rd.setFont(new Font("Arial", 0, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("( Close ALL browser windows including this one then", 295, 332);
                        this.rd.drawString("start the game again but click 'Run' when asked to 'run this application'. )", 175, 349);
                    }
                    if (this.nflk != 0) {
                        --this.nflk;
                    }
                } else {
                    this.rd.drawString("Play a multiplayer game across your Local Area Network (LAN).", 179, 245);
                    this.rd.drawString("Experience the game live with zero delay and 100% real-time action!", 179, 262);
                    this.rd.setFont(new Font("Arial", 0, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("This is for if there is more then one computer connected to your network or", 179, 292);
                    this.rd.drawString("if you are in a computer lab or in an internet caf\u00e9.", 179, 309);
                    this.rd.drawString("You can also invite your friends to come over with their Laptop PCs or Macs", 179, 335);
                    this.rd.drawString("to log on to your internet connection/network and play with you!", 179, 352);
                }
                this.drawbutton(this.xt.cancel, 583, 395);
                this.drawbutton(this.xt.play, 400, 395);
            } else {
                this.trans += 10;
            }
        }
        if (this.resofaso) {
            this.resofaso = false;
            if (this.connector != null) {
                this.connector.stop();
                this.connector = null;
            }
            this.socketson = false;
            this.msg = "| Connecting to Servers |";
            this.fase = 13;
            this.connector = new Thread(this);
            this.connector.start();
        }
    }

    public void multistart(ContO[] contOArray, int n, int n2, boolean bl) {
        this.btn = 0;
        this.xt.mainbg(4);
        for (int i = 0; i < 3; ++i) {
            this.rd.drawImage(this.xt.bgmain, 65, this.bgmy[i], null);
            int n3 = i;
            this.bgmy[n3] = this.bgmy[n3] - 4;
            if (this.bgmy[i] > -400) continue;
            this.bgmy[i] = 800;
        }
        this.rd.setComposite(AlphaComposite.getInstance(3, 0.2f));
        this.rd.drawImage(this.xt.bggo, 0, 0, null);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        this.rd.setColor(new Color(0, 0, 0));
        this.rd.fillRect(65, 425, 670, 25);
        this.rd.fillRect(0, 0, 65, 450);
        this.rd.fillRect(735, 0, 65, 450);
        float f = 1.0f - (float)(this.flipo - 10) / 80.0f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.rd.setComposite(AlphaComposite.getInstance(3, f));
        if (this.flipo > 10) {
            this.rd.drawImage(this.xt.logomadnes, 96 + (int)(2.0 - Math.random() * 4.0), 11 + (int)(2.0 - Math.random() * 4.0), null);
        } else {
            this.rd.drawImage(this.xt.logomadnes, 96, 11, null);
        }
        ++this.flipo;
        if (this.flipo > 50) {
            this.flipo = 0;
        }
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        int n4 = 0;
        if (n != this.oxm || n2 != this.oym) {
            n4 = 1;
            this.oxm = n;
            this.oym = n2;
        }
        if (bl) {
            n4 = 2;
        }
        this.rd.setComposite(AlphaComposite.getInstance(3, 0.3f));
        this.rd.drawImage(this.xt.dude[n4], 87, 76, null);
        this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
        this.rd.drawImage(this.xt.redy, 445, 153, null);
        this.drawSbutton(this.xt.exit, 690, 17);
        this.rd.setFont(new Font("Arial", 1, 13));
        this.ftm = this.rd.getFontMetrics();
        if (this.fase != 5) {
            this.rd.setComposite(AlphaComposite.getInstance(3, 0.25f));
            this.rd.setColor(new Color(203, 227, 253));
            this.rd.fillRoundRect(246, 83, 180, 96, 20, 20);
            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
            this.rd.setColor(this.color2k(90, 90, 90));
            this.rd.drawString("" + this.xt.cd.names[this.xt.sc[0]] + "", 336 - this.ftm.stringWidth("" + this.xt.cd.names[this.xt.sc[0]] + "") / 2, 81);
            this.rd.drawRoundRect(246, 83, 180, 96, 20, 20);
            if (!this.gotcai) {
                int n5;
                int n6;
                int n7 = n6 = contOArray[this.xt.sc[0]].p[0].oz[0];
                int n8 = n5 = contOArray[this.xt.sc[0]].p[0].oy[0];
                for (int i = 0; i < contOArray[this.xt.sc[0]].npl; ++i) {
                    for (int j = 0; j < contOArray[this.xt.sc[0]].p[i].n; ++j) {
                        if (contOArray[this.xt.sc[0]].p[i].oz[j] < n6) {
                            n6 = contOArray[this.xt.sc[0]].p[i].oz[j];
                        }
                        if (contOArray[this.xt.sc[0]].p[i].oz[j] > n7) {
                            n7 = contOArray[this.xt.sc[0]].p[i].oz[j];
                        }
                        if (contOArray[this.xt.sc[0]].p[i].oy[j] < n5) {
                            n5 = contOArray[this.xt.sc[0]].p[i].oy[j];
                        }
                        if (contOArray[this.xt.sc[0]].p[i].oy[j] <= n8) continue;
                        n8 = contOArray[this.xt.sc[0]].p[i].oy[j];
                    }
                }
                this.cax = (n7 + n6) / 2;
                this.cay = (n8 + n5) / 2;
                this.gotcai = true;
            }
            contOArray[this.xt.sc[0]].z = 1500;
            contOArray[this.xt.sc[0]].y = 380 - this.cay;
            contOArray[this.xt.sc[0]].x = -170 - this.cax;
            contOArray[this.xt.sc[0]].zy = 0;
            contOArray[this.xt.sc[0]].xz = -90;
            contOArray[this.xt.sc[0]].xy = this.pend;
            this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            contOArray[this.xt.sc[0]].d(this.rd);
            this.rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
            this.drawbutton(this.xt.change, 497, 98);
        }
        if (this.fase == 1 || this.fase == 2) {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawString(this.msg, 400 - this.ftm.stringWidth(this.msg) / 2, 205);
            this.rd.drawString("Enter a Nickname:", 400 - this.ftm.stringWidth("Enter a Nickname:") - 14, 241);
            if (this.fase == 2) {
                if (this.msg.equals(". . . | Checking Nickname | . . .") && this.ncnt == 0) {
                    this.msg = "| Checking Nickname |";
                    this.ncnt = 5;
                }
                if (this.msg.equals(". . | Checking Nickname | . .") && this.ncnt == 0) {
                    this.msg = ". . . | Checking Nickname | . . .";
                    this.ncnt = 5;
                }
                if (this.msg.equals(". | Checking Nickname | .") && this.ncnt == 0) {
                    this.msg = ". . | Checking Nickname | . .";
                    this.ncnt = 5;
                }
                if (this.msg.equals("| Checking Nickname |") && this.ncnt == 0) {
                    this.msg = ". | Checking Nickname | .";
                    this.ncnt = 5;
                }
                if (this.ncnt != 0) {
                    --this.ncnt;
                }
                this.pessd[2] = true;
            }
            if (this.fase == 1 && !this.gs.tnick.isShowing()) {
                this.gs.tnick.show();
                this.gs.tnick.requestFocus();
                if (this.gs.tnick.getText().equals("Nickname")) {
                    this.gs.tnick.select(8, 8);
                }
            }
            if (this.errcnt != 0) {
                --this.errcnt;
                if (this.errcnt == 0) {
                    this.gs.tnick.setForeground(new Color(0, 0, 0));
                }
            }
            this.drawbutton(this.xt.play, 400, 285);
            if (this.nflk > 0) {
                if (this.gs.tnick.getText().equals("")) {
                    this.gs.tnick.setText("Nickname");
                    if (this.nflk == 1) {
                        this.gs.tnick.select(8, 8);
                    }
                } else {
                    this.gs.tnick.setText("");
                }
                --this.nflk;
            }
            this.drawbutton(this.xt.login, 400, 340);
            this.drawbutton(this.xt.register, 400, 395);
            this.gs.movefield(this.gs.tnick, 400, 225, 129, 23);
            while (this.ftm.stringWidth(this.gs.tnick.getText()) > 86) {
                this.gs.tnick.setText(this.gs.tnick.getText().substring(0, this.gs.tnick.getText().length() - 1));
                this.gs.tnick.select(this.gs.tnick.getText().length(), this.gs.tnick.getText().length());
            }
            if (!this.gs.tnick.getText().equals(this.lnick)) {
                this.fixtext(this.gs.tnick);
                this.lnick = this.gs.tnick.getText();
            }
            if (this.xt.msgcheck(this.gs.tnick.getText())) {
                this.gs.tnick.setText("");
            }
            if (this.gs.tnick.getText().toLowerCase().indexOf("madbot") != -1) {
                this.gs.tnick.setText("");
            }
        }
        if (this.fase == 3 || this.fase == 4) {
            this.rd.drawImage(this.xt.ntrg, 97, 388, null);
            this.rd.setComposite(AlphaComposite.getInstance(3, 0.25f));
            this.rd.setColor(new Color(203, 227, 253));
            this.rd.fillRoundRect(246, 212, 308, 142, 20, 20);
            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
            this.rd.setColor(this.color2k(90, 90, 90));
            this.rd.drawRoundRect(246, 212, 308, 142, 20, 20);
            this.rd.setColor(new Color(0, 0, 0));
            if (this.nflk % 4 != 0 || this.nflk == 0) {
                this.rd.drawString(this.msg, 400 - this.ftm.stringWidth(this.msg) / 2, 205);
            }
            if (this.nflk != 0) {
                --this.nflk;
            }
            this.rd.drawString("Nickname:", 376 - this.ftm.stringWidth("Nickname:") - 14, 237);
            this.rd.drawString("Password:", 376 - this.ftm.stringWidth("Password:") - 14, 267);
            if (this.fase == 4) {
                if (this.msg.equals(". . . | Logging In | . . .") && this.ncnt == 0) {
                    this.msg = "| Logging In |";
                    this.ncnt = 5;
                }
                if (this.msg.equals(". . | Logging In | . .") && this.ncnt == 0) {
                    this.msg = ". . . | Logging In | . . .";
                    this.ncnt = 5;
                }
                if (this.msg.equals(". | Logging In | .") && this.ncnt == 0) {
                    this.msg = ". . | Logging In | . .";
                    this.ncnt = 5;
                }
                if (this.msg.equals("| Logging In |") && this.ncnt == 0) {
                    this.msg = ". | Logging In | .";
                    this.ncnt = 5;
                }
                if (this.ncnt != 0) {
                    --this.ncnt;
                }
                this.pessd[2] = true;
            }
            if (this.fase == 3) {
                this.showtf = true;
                if (!this.gs.applejava) {
                    if (!this.gs.tpass.isShowing()) {
                        this.gs.tpass.show();
                        if (!this.gs.tnick.getText().equals("")) {
                            this.gs.tpass.requestFocus();
                        }
                    }
                    if (!this.gs.tnick.isShowing()) {
                        this.gs.tnick.show();
                        if (this.gs.tnick.getText().equals("")) {
                            this.gs.tnick.requestFocus();
                        }
                    }
                }
            }
            if (this.errcnt != 0) {
                --this.errcnt;
                if (this.errcnt == 0) {
                    this.gs.tpass.setText("");
                    this.gs.tnick.setForeground(new Color(0, 0, 0));
                    this.gs.tpass.setForeground(new Color(0, 0, 0));
                }
            }
            this.drawbutton(this.xt.login, 400, 315);
            if (this.onf) {
                this.rd.setColor(this.color2k(0, 72, 255));
            } else {
                this.rd.setColor(this.color2k(120, 120, 120));
            }
            this.rd.setFont(new Font("Arial", 1, 11));
            this.ftm = this.rd.getFontMetrics();
            this.rd.drawString("Forgot your nickname or password?", 400 - this.ftm.stringWidth("Forgot your nickname or password?") / 2, 345);
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.setFont(new Font("Arial", 1, 12));
            this.ftm = this.rd.getFontMetrics();
            String string = "Register a full account here!";
            this.xrl = 400 - this.ftm.stringWidth(string) / 2;
            this.xrr = this.xrl + this.ftm.stringWidth(string);
            this.rd.drawString(string, this.xrl, 371);
            this.rd.drawLine(this.xrl, 372, this.xrr, 372);
            this.drawbutton(this.xt.register, 400, 395);
            this.gs.movefieldd(this.gs.tnick, 376, 221, 129, 23, this.showtf);
            if (!this.gs.tnick.getText().equals(this.lnick)) {
                this.fixtext(this.gs.tnick);
                this.lnick = this.gs.tnick.getText();
            }
            this.gs.movefieldd(this.gs.tpass, 376, 251, 129, 23, this.showtf);
            if (!this.gs.tpass.getText().equals(this.lpass)) {
                this.fixtext(this.gs.tpass);
                this.lpass = this.gs.tpass.getText();
            }
            if (!(this.fase != 3 || (this.gs.tpass.getText().equals("") || this.gs.tnick.getText().equals("")) && this.gs.applejava || this.gs.keplo.isShowing())) {
                this.gs.keplo.show();
            }
            this.gs.movefield(this.gs.keplo, 376, 275, 129, 23);
        }
        if (this.fase == 5) {
            this.rd.setColor(new Color(0, 0, 0));
            this.rd.drawString("Nickname:", 376 - this.ftm.stringWidth("Nickname:") - 14, 141);
            this.gs.movefield(this.gs.tnick, 376, 125, 129, 23);
            while (this.ftm.stringWidth(this.gs.tnick.getText()) > 86) {
                this.gs.tnick.setText(this.gs.tnick.getText().substring(0, this.gs.tnick.getText().length() - 1));
                this.gs.tnick.select(this.gs.tnick.getText().length(), this.gs.tnick.getText().length());
            }
            if (!this.gs.tnick.getText().equals(this.lnick)) {
                this.fixtext(this.gs.tnick);
                this.lnick = this.gs.tnick.getText();
            }
            if (!this.gs.tnick.isShowing()) {
                this.gs.tnick.show();
            }
            this.drawbutton(this.xt.register, 400, 325);
            this.drawbutton(this.xt.cancel, 400, 375);
        }
        if (this.fase == 7 || this.fase == 8) {
            this.rd.setColor(new Color(0, 0, 0));
            if (this.nflk % 4 != 0 || this.nflk == 0) {
                this.rd.drawString(this.msg, 400 - this.ftm.stringWidth(this.msg) / 2, 205);
            }
            if (this.nflk != 0) {
                --this.nflk;
            }
            this.rd.drawString("Your Email:", 344 - this.ftm.stringWidth("Your Email:") - 14, 241);
            if (this.fase == 8) {
                if (this.msg.equals(". . . | Checking Email | . . .") && this.ncnt == 0) {
                    this.msg = "| Checking Email |";
                    this.ncnt = 5;
                }
                if (this.msg.equals(". . | Checking Email | . .") && this.ncnt == 0) {
                    this.msg = ". . . | Checking Email | . . .";
                    this.ncnt = 5;
                }
                if (this.msg.equals(". | Checking Email | .") && this.ncnt == 0) {
                    this.msg = ". . | Checking Email | . .";
                    this.ncnt = 5;
                }
                if (this.msg.equals("| Checking Email |") && this.ncnt == 0) {
                    this.msg = ". | Checking Email | .";
                    this.ncnt = 5;
                }
                if (this.ncnt != 0) {
                    --this.ncnt;
                }
                this.pessd[2] = true;
            }
            if (this.fase == 7) {
                this.showtf = true;
                if (!this.gs.applejava && !this.gs.temail.isShowing()) {
                    this.gs.temail.show();
                    this.gs.temail.requestFocus();
                }
            }
            if (this.errcnt != 0) {
                --this.errcnt;
                if (this.errcnt == 0) {
                    this.gs.temail.setForeground(new Color(0, 0, 0));
                }
            }
            this.drawbutton(this.xt.sdets, 400, 280);
            this.drawbutton(this.xt.cancel, 400, 375);
            this.gs.movefieldd(this.gs.temail, 344, 225, 199, 23, this.showtf);
            if (!this.gs.temail.getText().equals(this.lemail)) {
                this.fixtext(this.gs.temail);
                this.lemail = this.gs.temail.getText();
            }
        }
    }

    public void ctachm(int n, int n2, int n3, Control control, Lobby lobby) {
        int n4;
        int n5 = -1;
        if (this.fase != 2 && this.fase != 4 && this.fase != 6 && this.fase != 8 && this.fase != 9) {
            for (n4 = 0; n4 < this.btn; ++n4) {
                this.pessd[n4] = Math.abs(n - this.bx[n4]) < this.bw[n4] / 2 + 12 && Math.abs(n2 - this.by[n4]) < 14 && (n3 == 1 || n3 == 11);
                if (Math.abs(n - this.bx[n4]) < this.bw[n4] / 2 + 12 && Math.abs(n2 - this.by[n4]) < 14 && n3 <= -1) {
                    this.gs.mouses = 0;
                    n5 = n4;
                }
                if (this.fase != 12 || Math.abs(n - this.bx[n4]) >= this.bw[n4] / 2 + 12 || Math.abs(n2 - this.by[n4]) >= 14 || n4 != 3 && n4 != 4 || n == this.lxm && n2 == this.lym) continue;
                this.opselect = n4 - 3;
            }
        }
        if (n5 == 0) {
            this.gs.tnick.hide();
            this.gs.tpass.hide();
            this.gs.keplo.hide();
            this.gs.temail.hide();
            this.gs.requestFocus();
            this.xt.fase = 24;
        }
        if (n5 == 1 && this.fase != 5) {
            this.gs.tnick.hide();
            this.gs.tpass.hide();
            this.gs.keplo.hide();
            this.gs.temail.hide();
            this.gs.requestFocus();
            this.xt.fase = 23;
        }
        n4 = 2;
        if (this.fase == 12 || this.fase == 13 || this.fase == 14 || this.fase == 15 || this.fase == 16 || this.fase == 17) {
            if (n > 176 && n2 > 152 && n < 296 && n2 < 174) {
                if (!this.ond) {
                    this.ond = true;
                    this.gs.setCursor(new Cursor(12));
                }
            } else if (this.ond) {
                this.ond = false;
                this.gs.setCursor(new Cursor(0));
            }
            if (this.cntcl == 0) {
                if (this.ond && n3 == 11) {
                    this.gs.editlink(this.xt.nickname, false);
                    this.cntcl = 10;
                }
            } else {
                --this.cntcl;
            }
            if (n5 == n4) {
                n5 = -1;
                if (this.xt.sc[0] >= 16) {
                    this.xt.sc[0] = 15;
                    this.gotcai = false;
                }
                this.xt.logged = false;
                this.xt.gotlog = false;
                this.gs.keplo.setState(false);
                this.gs.setloggedcookie();
                this.xt.cd.msloaded = 0;
                this.xt.cd.lastload = 0;
                this.msg = "Login to access the multiplayer madness!";
                this.fase = 3;
            }
        }
        if (this.fase == 12) {
            if (this.xt.acexp == 0 || this.contrb) {
                if (control.up) {
                    --this.opselect;
                    if (this.opselect == -1) {
                        this.opselect = 1;
                    }
                    control.up = false;
                }
                if (control.down) {
                    ++this.opselect;
                    if (this.opselect == 2) {
                        this.opselect = 0;
                    }
                    control.down = false;
                }
                if (control.enter) {
                    n5 = this.opselect + 3;
                    control.enter = false;
                }
                if (n5 == n4 + 1) {
                    this.msg = "| Connecting to Servers |";
                    this.opselect = 0;
                    this.trans = 0;
                    this.fase = 13;
                    this.nflk = 0;
                    n5 = -1;
                }
                if (n5 == n4 + 2) {
                    this.trans = 0;
                    this.fase = 15;
                    n5 = -1;
                }
                if (!this.xt.logged && n5 == n4 + 3) {
                    this.gs.editlink(this.xt.nickname, true);
                }
            } else {
                if (this.xt.acexp > 0) {
                    if (n5 == n4 + 1 || control.enter) {
                        this.gs.editlink(this.xt.nickname, false);
                        n5 = -1;
                    }
                    if (n5 == n4 + 2) {
                        this.opselect = 0;
                        this.contrb = true;
                        n5 = -1;
                    }
                }
                if (this.xt.acexp == -1) {
                    if (n5 == n4 + 1 || control.enter) {
                        this.gs.editlink(this.xt.nickname, false);
                        n5 = -1;
                    }
                    if (n5 == n4 + 2) {
                        n5 = -1;
                        if (this.xt.sc[0] >= 16) {
                            this.xt.sc[0] = 15;
                            this.gotcai = false;
                        }
                        this.xt.logged = false;
                        this.xt.cd.lastload = 0;
                        this.msg = "Login to access the multiplayer madness!";
                        this.fase = 3;
                    }
                }
                if (this.xt.acexp == -2) {
                    if (n5 == n4 + 1 || control.enter) {
                        this.gs.multlink();
                        n5 = -1;
                    }
                    if (n5 == n4 + 2) {
                        n5 = -1;
                        if (this.xt.sc[0] >= 16) {
                            this.xt.sc[0] = 15;
                            this.gotcai = false;
                        }
                        this.xt.logged = false;
                        this.xt.cd.lastload = 0;
                        this.msg = "Login to access the multiplayer madness!";
                        this.fase = 3;
                    }
                }
                if (this.xt.acexp == -3) {
                    if (n5 == n4 + 1 || control.enter) {
                        this.gs.editlink(this.xt.nickname, false);
                        n5 = -1;
                    }
                    if (n5 == n4 + 2) {
                        n5 = -1;
                        if (this.xt.sc[0] >= 16) {
                            this.xt.sc[0] = 15;
                            this.gotcai = false;
                        }
                        this.xt.logged = false;
                        this.xt.cd.lastload = 0;
                        this.msg = "Login to access the multiplayer madness!";
                        this.fase = 3;
                    }
                }
            }
        }
        if (this.fase == 13 || this.fase == 14 || this.fase == 15 || this.fase == 16 || this.fase == 17) {
            if (control.exit) {
                n5 = 3;
            }
            if (n5 == n4 + 1) {
                this.opselect = this.fase == 15 ? 1 : 0;
                if (this.fase == 16 || this.fase == 17) {
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
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    this.fase = 12;
                    this.connector = new Thread(this);
                    this.connector.start();
                }
                if (this.fase == 14) {
                    if (this.connector != null) {
                        this.connector.stop();
                        this.connector = null;
                    }
                    for (int i = 0; i < this.nservers; ++i) {
                        try {
                            this.dSocket[i].close();
                            this.dSocket[i] = null;
                            continue;
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                    this.socketson = false;
                    this.fase = 12;
                    this.connector = new Thread(this);
                    this.connector.start();
                }
                this.fase = 12;
                this.gs.setCursor(new Cursor(0));
            }
        }
        if (this.fase == 14) {
            if (control.enter) {
                n5 = 4;
                this.pessd[4] = true;
            }
            if (control.up) {
                --this.opselect;
                if (this.opselect == -1) {
                    this.opselect = this.nservers - 1;
                }
                control.up = false;
            }
            if (control.down) {
                ++this.opselect;
                if (this.opselect == this.nservers) {
                    this.opselect = 0;
                }
                control.down = false;
            }
            for (int i = 0; i < this.nservers; ++i) {
                if (n <= 175 || n2 <= 230 + i * 20 || n >= 625 || n2 >= 250 + i * 20 || n3 != 1) continue;
                this.opselect = i;
            }
            if (n5 == n4 + 2) {
                if (this.xt.delays[this.opselect] >= 400 && !this.xt.nickname.equals("Fyre")) {
                    this.nflk = 30;
                } else {
                    this.xt.server = this.servers[this.opselect];
                    this.xt.servername = this.snames[this.opselect];
                    this.msg = "| Finding Rooms |";
                    this.opselect = 0;
                    this.nflk = 0;
                    n5 = -1;
                    this.fase = 16;
                }
            }
        }
        if (this.fase == 15) {
            if (control.enter) {
                n5 = 4;
                this.pessd[4] = true;
            }
            if (n5 == n4 + 2) {
                if (this.xt.nofull) {
                    this.nflk = 30;
                } else {
                    this.xt.server = this.servers[1];
                    this.xt.servername = this.snames[1];
                    this.xt.servport = 7067;
                    this.xt.lan = true;
                    n5 = -1;
                    this.fase = 18;
                    lobby.fase = 0;
                }
            }
        }
        if (this.fase == 17) {
            if (control.enter) {
                n5 = 4;
                this.pessd[4] = true;
            }
            if (control.up) {
                --this.opselect;
                if (this.opselect == -1) {
                    this.opselect = 4;
                }
                control.up = false;
            }
            if (control.down) {
                ++this.opselect;
                if (this.opselect == 5) {
                    this.opselect = 0;
                }
                control.down = false;
            }
            for (int i = 0; i < 5; ++i) {
                if (n <= 175 || n2 <= 230 + i * 20 + 14 || n >= 625 || n2 >= 250 + i * 20 + 14 || n3 != 1) continue;
                this.opselect = i;
            }
            if (n5 == n4 + 2) {
                this.xt.servport = 7071 + this.opselect;
                this.xt.lan = false;
                n5 = -1;
                this.fase = 18;
                lobby.fase = 0;
            }
        }
        if (this.fase == 3) {
            if (n > 295 && n2 > 334 && n < 505 && n2 < 348) {
                if (!this.onf) {
                    this.onf = true;
                    this.gs.setCursor(new Cursor(12));
                }
            } else if (this.onf) {
                this.onf = false;
                this.gs.setCursor(new Cursor(0));
            }
            if (this.onf && n3 == 11) {
                this.msg = "Please enter your Email Address to recover your account details.";
                this.gs.tnick.setForeground(new Color(0, 0, 0));
                this.gs.tpass.setForeground(new Color(0, 0, 0));
                this.gs.tnick.hide();
                this.gs.tpass.hide();
                this.gs.keplo.hide();
                this.onf = false;
                this.gs.setCursor(new Cursor(0));
                this.fase = 7;
            }
            if (n > this.xrl && n < this.xrr && n2 > 360 && n2 < 373) {
                if (!this.onr) {
                    this.onr = true;
                    this.gs.setCursor(new Cursor(12));
                }
            } else if (this.onr) {
                this.onr = false;
                this.gs.setCursor(new Cursor(0));
            }
            if (this.onr && n3 == 11) {
                this.gs.reglink();
                this.gs.mouses = 0;
            }
        }
        if (this.fase == 1) {
            if (control.enter) {
                n5 = 2;
                this.pessd[2] = true;
            }
            if (n5 == 2) {
                if (this.gs.tnick.getText().equals("Fyre") || this.gs.tnick.getText().equals("Nickname") || this.gs.tnick.getText().equals("")) {
                    this.msg = "Type in any Nickname to play...";
                    this.gs.tnick.setText("Nickname");
                    this.nflk = 30;
                } else {
                    this.msg = "| Checking Nickname |";
                    this.gs.tnick.disable();
                    this.fase = 2;
                    this.connector = new Thread(this);
                    this.connector.start();
                }
            }
            if (n5 == 3) {
                if (this.gs.tnick.getText().equals("Nickname") || this.msg.startsWith("This")) {
                    this.gs.tnick.setText("");
                }
                this.msg = "Login to access the multiplayer madness!";
                this.gs.tnick.setForeground(new Color(0, 0, 0));
                this.fase = 3;
                n5 = -1;
            }
            if (n5 == 4) {
                if (this.nickero || this.gs.tnick.getText().equals("Nickname")) {
                    this.gs.tnick.setText("");
                    this.nickero = false;
                }
                this.gs.tnick.setForeground(new Color(0, 0, 0));
                this.gs.reglink();
            }
        }
        if (this.fase == 3) {
            if (control.enter || this.xt.autolog) {
                n5 = 2;
                this.pessd[2] = true;
                this.xt.autolog = false;
            }
            if (control.exit) {
                n5 = 3;
            }
            if (n5 == 2) {
                if (this.gs.tnick.getText().equals("")) {
                    this.msg = "Enter your Nickname!";
                    this.nflk = 30;
                } else if (this.gs.tpass.getText().equals("")) {
                    this.msg = "Enter your Password!";
                    this.nflk = 30;
                } else {
                    this.msg = "| Logging In |";
                    this.gs.tnick.disable();
                    this.gs.tpass.disable();
                    this.gs.keplo.disable();
                    this.fase = 4;
                    this.connector = new Thread(this);
                    this.connector.start();
                }
            }
            if (n5 == 3) {
                this.gs.regnew();
            }
        }
        if (this.fase == 5) {
            if (control.enter) {
                n5 = 1;
                this.pessd[1] = true;
            }
            if (control.exit) {
                n5 = 2;
            }
            if (n5 == 1) {
                // empty if block
            }
            if (n5 == 2) {
                this.fase = this.lrgfase;
                if (this.fase == 12) {
                    this.gs.tnick.hide();
                    this.connector = new Thread(this);
                    this.connector.start();
                }
            }
        }
        if (this.fase == 7) {
            if (control.enter) {
                n5 = 2;
                this.pessd[2] = true;
            }
            if (control.exit) {
                n5 = 3;
            }
            if (n5 == 2) {
                this.nflk = 0;
                if (this.gs.temail.getText().equals("")) {
                    this.msg = "Please type in your Email Address!";
                    this.nflk = 30;
                }
                if (this.nflk == 0) {
                    String string = this.gs.temail.getText();
                    int n6 = 0;
                    for (int i = 0; i < string.length(); ++i) {
                        String string2 = "" + string.charAt(i);
                        if (string2.equals("@") && n6 == 0 && i != 0) {
                            n6 = 1;
                        }
                        if (!string2.equals(".") || n6 != 1 || i == string.length() - 1) continue;
                        n6 = 2;
                    }
                    if (n6 != 2) {
                        this.msg = "Please type in your Email Address correctly!";
                        this.nflk = 30;
                        this.errcnt = 40;
                        this.gs.temail.setForeground(new Color(255, 0, 0));
                    }
                }
                if (this.nflk == 0) {
                    this.msg = "| Checking Email |";
                    this.gs.temail.disable();
                    this.fase = 8;
                    this.connector = new Thread(this);
                    this.connector.start();
                }
            }
            if (n5 == 3) {
                this.inishmulti();
                this.gs.temail.setText("");
                this.gs.tpass.setText("");
            }
        }
        this.lxm = n;
        this.lym = n2;
        control.enter = false;
        control.exit = false;
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

    public void drawbutton(Image image, int n, int n2) {
        this.bx[this.btn] = n;
        this.by[this.btn] = n2;
        this.bw[this.btn] = image.getWidth(this.ob);
        if (!this.pessd[this.btn]) {
            this.rd.drawImage(image, n - this.bw[this.btn] / 2, n2 - image.getHeight(this.ob) / 2, null);
            this.rd.drawImage(this.xt.bol, n - this.bw[this.btn] / 2 - 15, n2 - 16, null);
            this.rd.drawImage(this.xt.bor, n + this.bw[this.btn] / 2 + 9, n2 - 16, null);
            this.rd.drawImage(this.xt.bot, n - this.bw[this.btn] / 2 - 9, n2 - 16, this.bw[this.btn] + 18, 3, null);
            this.rd.drawImage(this.xt.bob, n - this.bw[this.btn] / 2 - 9, n2 + 13, this.bw[this.btn] + 18, 3, null);
        } else {
            this.rd.drawImage(image, n - this.bw[this.btn] / 2 + 1, n2 - image.getHeight(this.ob) / 2 + 1, null);
            this.rd.drawImage(this.xt.bolp, n - this.bw[this.btn] / 2 - 15, n2 - 16, null);
            this.rd.drawImage(this.xt.borp, n + this.bw[this.btn] / 2 + 9, n2 - 16, null);
            this.rd.drawImage(this.xt.bob, n - this.bw[this.btn] / 2 - 9, n2 - 16, this.bw[this.btn] + 18, 3, null);
            this.rd.drawImage(this.xt.bot, n - this.bw[this.btn] / 2 - 9, n2 + 13, this.bw[this.btn] + 18, 3, null);
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

    public void fixtext(TextField textField) {
        String string = textField.getText();
        string = string.replace('\"', '#');
        String string2 = "\\";
        String string3 = "";
        int n = -1;
        for (int i = 0; i < string.length(); ++i) {
            String string4 = "" + string.charAt(i);
            if (string4.equals("|") || string4.equals(",") || string4.equals("(") || string4.equals(")") || string4.equals("#") || string4.equals(string2) || string4.equals("!") || string4.equals("?") || string4.equals(" ") || string4.equals("~") || string4.equals("$") || string4.equals("%") || string4.equals("^") || string4.equals("&") || string4.equals("*") || string4.equals("+") || string4.equals("=") || string4.equals(">") || string4.equals("<") || string4.equals("/") || string4.equals("'") || string4.equals(";") || string4.equals(":") || string4.equals("\u00a0")) {
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

