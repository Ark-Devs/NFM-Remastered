/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class UDPMistro
implements Runnable {
    Thread runner;
    int runon = 0;
    udpOnline[] udpc = new udpOnline[20];
    int porturn = 0;
    udpServe[] usrv = new udpServe[13];
    int diledelay = 0;
    long sendat = 0L;
    String sendcheck = "";
    int delay = 0;
    int[] ldelays = new int[5];
    int diled = 0;
    int rate = 30;
    float freg = 0.0f;
    boolean go = false;
    int im = 0;
    int nplayers = 0;
    String[][] info = new String[14][3];
    int[][] frame = new int[14][3];
    int[] lframe = new int[8];
    int[] force = new int[8];
    int[] lcframe = new int[8];
    boolean[] isbot = new boolean[8];
    Socket socket;
    BufferedReader din;
    PrintWriter dout;
    int[] gocnt = new int[8];
    int[] out = new int[8];
    String xtserver = "";
    int xtservport = 0;
    int pgame = 0;
    int wx = 0;

    public void UDPConnectOnline(String string, int n, int n2, int n3) {
        int n4;
        this.diledelay = 0;
        this.delay = 0;
        for (n4 = 0; n4 < 5; ++n4) {
            this.ldelays[n4] = 0;
        }
        this.diled = 0;
        this.go = false;
        this.freg = 0.0f;
        this.im = n3;
        this.nplayers = n2;
        for (n4 = 0; n4 < 8; ++n4) {
            for (int i = 0; i < 3; ++i) {
                this.frame[n4][i] = -1;
                this.info[n4][i] = "";
            }
            this.isbot[n4] = false;
            this.lframe[n4] = 0;
            this.force[n4] = 0;
            this.lcframe[n4] = 0;
        }
        for (n4 = 0; n4 < 20; ++n4) {
            this.udpc[n4] = new udpOnline(this, string, n + this.im, n4, this.porturn);
        }
        this.porturn = this.porturn == 0 ? 20 : 0;
        if (this.im >= this.nplayers) {
            this.frame[this.im][0] = 11111;
            this.info[this.im][0] = "watching";
            while (this.info[this.im][0].length() < 110) {
                String[] stringArray = this.info[this.im];
                stringArray[0] = stringArray[0] + "|";
            }
            ++this.nplayers;
            this.go = true;
        }
        this.runon = 1;
        this.runner = new Thread(this);
        this.runner.start();
    }

    public void UDPConnectLan(String string, int n, int n2) {
        int n3;
        this.diledelay = 0;
        this.delay = 0;
        for (n3 = 0; n3 < 5; ++n3) {
            this.ldelays[n3] = 0;
        }
        this.diled = 0;
        this.go = false;
        this.freg = 0.0f;
        this.im = n2;
        this.nplayers = n;
        for (n3 = 0; n3 < 8; ++n3) {
            for (int i = 0; i < 3; ++i) {
                this.frame[n3][i] = -1;
                this.info[n3][i] = "";
            }
            this.isbot[n3] = false;
            this.lframe[n3] = 0;
            this.force[n3] = 0;
            this.lcframe[n3] = 0;
        }
        if (this.im != 0) {
            for (n3 = 0; n3 < 20; ++n3) {
                this.udpc[n3] = new udpOnline(this, string, 7060 + this.im, n3, this.porturn);
            }
            this.porturn = this.porturn == 0 ? 20 : 0;
            if (this.im >= this.nplayers) {
                this.frame[this.im][0] = 11111;
                this.info[this.im][0] = "watching";
                while (this.info[this.im][0].length() < 110) {
                    String[] stringArray = this.info[this.im];
                    stringArray[0] = stringArray[0] + "|";
                }
                ++this.nplayers;
                this.go = true;
            }
            this.runon = 1;
        } else {
            try {
                this.socket = new Socket(this.xtserver, this.xtservport);
                this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.dout = new PrintWriter(this.socket.getOutputStream(), true);
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.runon = 4;
        }
        this.runner = new Thread(this);
        this.runner.start();
    }

    public void UDPLanServer(int n, String string, int n2, int n3) {
        int n4;
        this.xtserver = string;
        this.xtservport = n2;
        this.pgame = n3;
        for (n4 = 0; n4 < 8; ++n4) {
            this.gocnt[n4] = 3;
            this.out[n4] = 0;
            for (int i = 0; i < 3; ++i) {
                this.frame[n4][i] = -1;
                this.info[n4][i] = "";
            }
            this.lframe[n4] = 0;
            this.force[n4] = 0;
            this.lcframe[n4] = 0;
        }
        for (n4 = 0; n4 < this.nplayers + 5; ++n4) {
            this.usrv[n4] = new udpServe(this, n4 + 1);
        }
    }

    public void UDPquit() {
        if (this.runon == 1) {
            this.runon = 2;
        }
        if (this.runon == 4) {
            this.runon = 5;
        }
    }

    @Override
    public void run() {
        int n = 0;
        while (this.runon == 1) {
            long l;
            Date date;
            if (!this.udpc[0].started) {
                this.udpc[0].spark();
                n = 1;
            } else {
                date = new Date();
                if (n > this.delay / this.rate && date.getTime() - this.udpc[0].sendat > (long)(this.rate - 5)) {
                    this.udpc[0].spark();
                    n = 1;
                }
            }
            if (this.delay > this.rate && n <= this.delay / this.rate && n >= 1 && n < 20 && (l = (date = new Date()).getTime() - this.udpc[n - 1].sendat) >= (long)(this.rate - 5) && l < (long)(this.rate * 2)) {
                this.udpc[n].spark();
                ++n;
            }
            if (this.diled == 10) {
                for (int i = 0; i < 20; ++i) {
                    Date date2;
                    long l2;
                    if (!this.udpc[i].started || !((double)(l2 = (date2 = new Date()).getTime() - this.udpc[i].sendat) > (double)this.delay * 1.5) || l2 <= (long)this.rate) continue;
                    this.udpc[i].stomp();
                }
            }
            if (this.diledelay > 0) {
                --this.diledelay;
            }
            try {
                Thread.sleep(5L);
            }
            catch (InterruptedException interruptedException) {}
        }
        int n2 = 0;
        int n3 = 0;
        while (this.runon == 4) {
            for (int i = 0; i < this.nplayers; ++i) {
                String string;
                boolean bl = false;
                if (this.info[i][0].length() > 16 && !(string = "" + this.info[i][0].charAt(15)).equals("0")) {
                    bl = true;
                }
                if (!bl && this.out[i] == 77) {
                    this.out[i] = 0;
                }
                if (this.out[i] >= 76) continue;
                if (this.frame[i][0] > 6) {
                    if (this.lcframe[i] != this.frame[i][0] && !bl) {
                        this.lcframe[i] = this.frame[i][0];
                        this.out[i] = 0;
                        continue;
                    }
                    if (this.out[i] < 70) {
                        this.out[i] = 71;
                    }
                    int n4 = i;
                    this.out[n4] = this.out[n4] + 1;
                    if (bl) {
                        this.out[i] = 77;
                    }
                    if (this.out[i] != 76) continue;
                    this.info[i][0] = "disco";
                    int[] nArray = this.frame[i];
                    nArray[0] = nArray[0] + 10;
                    continue;
                }
                int n5 = i;
                this.out[n5] = this.out[n5] + 1;
                if (this.out[i] != 30) continue;
                this.frame[i][0] = 7;
            }
            if (n3 == 10) {
                String string = "3|" + this.pgame + "|alive|";
                String string2 = "";
                if (n2 == 0) {
                    try {
                        this.dout.println(string);
                        string2 = this.din.readLine();
                        if (string2 == null) {
                            n2 = 1;
                        }
                    }
                    catch (Exception exception) {
                        n2 = 1;
                    }
                }
                if (n2 == 1) {
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
                        this.socket = new Socket(this.xtserver, this.xtservport);
                        this.din = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                        this.dout = new PrintWriter(this.socket.getOutputStream(), true);
                        this.dout.println(string);
                        string2 = this.din.readLine();
                        if (string2 != null) {
                            n2 = 0;
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (n2 == 1) {
                    try {
                        this.socket.close();
                        this.socket = null;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    n2 = 2;
                }
                n3 = 0;
            } else {
                ++n3;
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {}
        }
        int n6 = 0;
        while (this.runon == 2) {
            int n7;
            int n8 = 0;
            for (n7 = 0; n7 < 20; ++n7) {
                if (this.udpc[n7].started) continue;
                ++n8;
            }
            if (n8 != 20) {
                ++n6;
            }
            if (n6 == 400 || n8 == 20) {
                for (n7 = 0; n7 < 20; ++n7) {
                    this.udpc[n7].closeSocket();
                    this.udpc[n7] = null;
                }
                this.runon = 3;
            }
            try {
                Thread.sleep(5L);
            }
            catch (InterruptedException interruptedException) {}
        }
        if (this.runon == 3) {
            System.gc();
            System.runFinalization();
            this.runon = 0;
            this.runner = null;
        }
        if (this.runon == 5) {
            for (int i = 0; i < this.nplayers + 2; ++i) {
                try {
                    this.usrv[i].stopServe();
                    this.usrv[i] = null;
                    continue;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            String string = "3|" + this.pgame + "|finish|";
            try {
                this.dout.println(string);
                String string3 = this.din.readLine();
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
            System.gc();
            System.runFinalization();
            this.runon = 0;
            this.runner = null;
        }
    }

    public void readinfo(Mad mad, ContO contO, Control control, int n, int[] nArray) {
        if (this.go && this.force[n] != 7 && !this.isbot[n]) {
            int n2;
            this.freg = (float)((double)this.freg + 0.05);
            int n3 = -1;
            if (n3 == -1) {
                for (n2 = 0; n2 < 3; ++n2) {
                    if (this.frame[n][n2] != this.lframe[n] + 1) continue;
                    n3 = n2;
                    if (n2 == 1) {
                        this.freg = (float)((double)this.freg - 0.1);
                    }
                    if (n2 != 2) continue;
                    this.freg = (float)((double)this.freg - 0.15);
                }
            }
            if (n3 == -1) {
                n2 = 0;
                for (int i = 0; i < 3; ++i) {
                    if (this.frame[n][i] <= this.lframe[n] + 1) continue;
                    ++n2;
                }
                if (n2 == 3) {
                    n3 = 2;
                }
            }
            if (n3 == -1 && this.force[n] == 1) {
                for (n2 = 0; n2 < 3; ++n2) {
                    if (this.frame[n][n2] < this.lframe[n]) continue;
                    n3 = n2;
                }
                if (n3 == -1) {
                    this.freg = (float)((double)this.freg + 0.2);
                }
            }
            if (this.freg < -15.0f) {
                this.freg = -15.0f;
            }
            if (this.freg > 0.0f) {
                this.freg = 0.0f;
            }
            if (n3 != -1) {
                this.force[n] = 0;
                String string = this.info[n][n3];
                String string2 = this.getSvalue(string, 0);
                if (string2.length() == 16) {
                    String string3 = "";
                    string3 = "" + string2.charAt(0);
                    control.left = !string3.equals("0");
                    string3 = "" + string2.charAt(1);
                    control.right = !string3.equals("0");
                    string3 = "" + string2.charAt(2);
                    control.up = !string3.equals("0");
                    string3 = "" + string2.charAt(3);
                    control.down = !string3.equals("0");
                    string3 = "" + string2.charAt(4);
                    control.handb = !string3.equals("0");
                    string3 = "" + string2.charAt(5);
                    mad.newcar = !string3.equals("0");
                    string3 = "" + string2.charAt(6);
                    mad.mtouch = !string3.equals("0");
                    string3 = "" + string2.charAt(7);
                    mad.wtouch = !string3.equals("0");
                    string3 = "" + string2.charAt(8);
                    mad.pushed = !string3.equals("0");
                    string3 = "" + string2.charAt(9);
                    mad.gtouch = !string3.equals("0");
                    string3 = "" + string2.charAt(10);
                    mad.pl = !string3.equals("0");
                    string3 = "" + string2.charAt(11);
                    mad.pr = !string3.equals("0");
                    string3 = "" + string2.charAt(12);
                    mad.pd = !string3.equals("0");
                    string3 = "" + string2.charAt(13);
                    mad.pu = !string3.equals("0");
                    string3 = "" + string2.charAt(14);
                    mad.dest = !string3.equals("0");
                } else if (string2.equals("disco")) {
                    nArray[n] = 3;
                    mad.hitmag = mad.cd.maxmag[mad.cn] + 100;
                    this.force[n] = 7;
                }
                if (this.force[n] != 7) {
                    int n4 = this.getncoms(string);
                    this.wx = 0;
                    if (n4 > 1) {
                        contO.x = this.getvalue(string, 1);
                    }
                    if (n4 > 2) {
                        contO.y = this.getvalue(string, 0);
                    }
                    if (n4 > 3) {
                        contO.z = this.getvalue(string, 0);
                    }
                    if (n4 > 4) {
                        contO.xz = this.getvalue(string, 0);
                    }
                    if (n4 > 5) {
                        contO.xy = this.getvalue(string, 0);
                    }
                    if (n4 > 6) {
                        contO.zy = this.getvalue(string, 0);
                    }
                    if (n4 > 7) {
                        mad.speed = (float)this.getvalue(string, 0) / 100.0f;
                    }
                    if (n4 > 8) {
                        mad.power = (float)this.getvalue(string, 0) / 100.0f;
                    }
                    if (n4 > 9) {
                        mad.mxz = this.getvalue(string, 0);
                    }
                    if (n4 > 10) {
                        mad.pzy = this.getvalue(string, 0);
                    }
                    if (n4 > 11) {
                        mad.pxy = this.getvalue(string, 0);
                    }
                    if (n4 > 12) {
                        mad.txz = this.getvalue(string, 0);
                    }
                    if (n4 > 13) {
                        mad.loop = this.getvalue(string, 0);
                    }
                    if (n4 > 14) {
                        contO.wxz = this.getvalue(string, 0);
                    }
                    if (n4 > 15) {
                        mad.pcleared = this.getvalue(string, 0);
                    }
                    if (n4 > 16) {
                        mad.clear = this.getvalue(string, 0);
                    }
                    if (n4 > 17) {
                        mad.nlaps = this.getvalue(string, 0);
                    }
                    if (n4 > 18) {
                        mad.hitmag = (int)((float)this.getvalue(string, 0) / 100.0f * (float)mad.cd.maxmag[mad.cn]);
                    }
                }
                this.lframe[n] = this.frame[n][n3];
            } else if (this.force[n] == 0) {
                int n5 = n;
                this.lframe[n5] = this.lframe[n5] + 1;
                this.force[n] = 1;
            }
        }
    }

    public void readContOinfo(ContO contO, int n) {
        if (this.go && this.force[n] == 1 && !this.isbot[n]) {
            int n2;
            int n3 = -2;
            if (n3 == -2) {
                for (n2 = 0; n2 < 3; ++n2) {
                    if (this.frame[n][n2] != this.lframe[n] + 1) continue;
                    n3 = -1;
                }
            }
            if (n3 == -1) {
                for (n2 = 0; n2 < 3; ++n2) {
                    if (this.frame[n][n2] != this.lframe[n]) continue;
                    n3 = n2;
                }
            }
            if (n3 > 0) {
                String string = this.info[n][n3];
                int n4 = this.getncoms(string);
                this.wx = 0;
                if (n4 > 1) {
                    contO.x = this.getvalue(string, 1);
                }
                if (n4 > 1) {
                    contO.y = this.getvalue(string, 0);
                }
                if (n4 > 1) {
                    contO.z = this.getvalue(string, 0);
                }
                if (n4 > 1) {
                    contO.xz = this.getvalue(string, 0);
                }
                if (n4 > 1) {
                    contO.xy = this.getvalue(string, 0);
                }
                if (n4 > 1) {
                    contO.zy = this.getvalue(string, 0);
                }
            }
        }
    }

    public void setinfo(Mad mad, ContO contO, Control control, int n, float f, boolean bl, int n2) {
        this.info[n2][0] = "";
        if (control.left) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (control.right) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (control.up) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (control.down) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (control.handb) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (mad.newcar) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (mad.mtouch) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (mad.wtouch) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (mad.pushed) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (mad.gtouch) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (mad.pl) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (mad.pr) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (mad.pd) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (mad.pu) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (mad.dest) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        if (bl) {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "1";
        } else {
            String[] stringArray = this.info[n2];
            stringArray[0] = stringArray[0] + "0";
        }
        String[] stringArray = this.info[n2];
        stringArray[0] = stringArray[0] + "," + contO.x + "," + contO.y + "," + contO.z + "," + contO.xz + "," + contO.xy + "," + contO.zy + "," + (int)(mad.speed * 100.0f) + "," + (int)(mad.power * 100.0f) + "," + mad.mxz + "," + mad.pzy + "," + mad.pxy + "," + mad.txz + "," + mad.loop + "," + contO.wxz + "," + mad.pcleared + "," + mad.clear + "," + mad.nlaps + "," + (int)(f * 100.0f) + "," + n + ",";
        while (this.info[n2][0].length() < 110) {
            String[] stringArray2 = this.info[n2];
            stringArray2[0] = stringArray2[0] + "|";
        }
        if (this.runon == 2) {
            mad.hitmag = mad.cd.maxmag[mad.cn] + 100;
        }
        int[] nArray = this.frame[n2];
        nArray[0] = nArray[0] + 1;
    }

    public int getvalue(String string, int n) {
        int n2 = -1;
        try {
            int n3 = 0;
            int n4 = 0;
            String string2 = "";
            String string3 = "";
            while (this.wx < string.length() && n4 != 2) {
                string2 = "" + string.charAt(this.wx);
                if (string2.equals(",")) {
                    if (n4 == 1 || ++n3 > n) {
                        n4 = 2;
                    }
                } else if (n3 == n) {
                    string3 = string3 + string2;
                    n4 = 1;
                }
                ++this.wx;
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
                if (string3.equals(",")) {
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

    public int getncoms(String string) {
        int n = 0;
        String string2 = "";
        for (int i = 0; i < string.length(); ++i) {
            string2 = "" + string.charAt(i);
            if (!string2.equals(",")) continue;
            ++n;
        }
        return n;
    }
}

