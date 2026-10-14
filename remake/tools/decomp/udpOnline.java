/*
 * Decompiled with CFR 0.152.
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class udpOnline
implements Runnable {
    Thread con;
    UDPMistro um;
    int gameport = 7001;
    InetAddress IPAddress;
    DatagramSocket dSocket;
    long sendat = 0L;
    boolean started = false;
    boolean errd = false;
    int nu = 0;

    public udpOnline(UDPMistro uDPMistro, String string, int n, int n2, int n3) {
        this.um = uDPMistro;
        this.gameport = n;
        this.nu = n2;
        try {
            this.dSocket = new DatagramSocket(7010 + n3 + this.nu);
            this.errd = false;
            this.IPAddress = InetAddress.getByName(string);
        }
        catch (Exception exception) {
            System.out.println("Error preparing for UDP Connection: " + exception);
        }
    }

    public void spark() {
        if (this.errd) {
            try {
                this.dSocket = new DatagramSocket(7020 + this.nu);
                this.errd = false;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        try {
            this.con = new Thread(this);
            this.con.start();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void closeSocket() {
        try {
            this.dSocket.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.dSocket = null;
        this.errd = true;
        if (this.con != null) {
            this.con.stop();
            this.con = null;
        }
        this.started = false;
    }

    public void stomp() {
        if (this.con != null) {
            this.con.stop();
            this.con = null;
        }
        this.started = false;
    }

    @Override
    public void run() {
        this.started = true;
        Date date = new Date();
        this.sendat = date.getTime();
        String string = "";
        if (!this.um.go) {
            string = "MAGNITUDE";
        }
        if (this.nu == 0 && this.um.diledelay == 0) {
            this.um.sendat = this.sendat;
            string = "" + this.sendat;
            this.um.sendcheck = string = string.substring(string.length() - 3, string.length());
            this.um.diledelay = 100;
        }
        try {
            int n;
            byte[] byArray = new byte[4];
            DatagramPacket datagramPacket = new DatagramPacket(byArray, byArray.length, this.IPAddress, this.gameport);
            String string2 = "" + string + "|" + this.um.im + "|" + this.um.frame[this.um.im][0] + "|" + this.um.info[this.um.im][0] + "|";
            byte[] byArray2 = string2.getBytes();
            datagramPacket.setData(byArray2);
            this.dSocket.send(datagramPacket);
            for (n = 0; n < this.um.nplayers - 1; ++n) {
                int n2;
                int n3;
                this.dSocket.receive(datagramPacket);
                String string3 = new String(datagramPacket.getData());
                if (!(this.nu != 0 && this.um.go || n != 0)) {
                    string = this.getSvalue(string3, 0);
                    if (!this.um.go && string.equals("1111111")) {
                        this.um.go = true;
                    }
                }
                if ((n3 = this.getvalue(string3, 1)) < 0 || n3 >= this.um.nplayers) continue;
                int n4 = this.getvalue(string3, 2);
                int n5 = 0;
                for (n2 = 0; n2 < 3; ++n2) {
                    if (n4 == this.um.frame[n3][n2]) continue;
                    ++n5;
                }
                if (n5 != 3) continue;
                for (n2 = 0; n2 < 3; ++n2) {
                    if (n4 <= this.um.frame[n3][n2]) continue;
                    for (int i = 2; i >= n2 + 1; --i) {
                        this.um.frame[n3][i] = this.um.frame[n3][i - 1];
                        this.um.info[n3][i] = this.um.info[n3][i - 1];
                    }
                    this.um.frame[n3][n2] = n4;
                    this.um.info[n3][n2] = this.getSvalue(string3, 3);
                    n2 = 3;
                }
            }
            if (this.nu == 0 && this.um.diledelay != 0 && this.um.sendcheck.equals(string)) {
                date = new Date();
                for (n = 4; n > 0; --n) {
                    this.um.ldelays[n] = this.um.ldelays[n - 1];
                }
                this.um.ldelays[0] = (int)(date.getTime() - this.um.sendat);
                this.um.delay = 0;
                for (n = 0; n < 5; ++n) {
                    if (this.um.ldelays[n] == 0 || this.um.delay != 0 && this.um.ldelays[n] >= this.um.delay) continue;
                    this.um.delay = this.um.ldelays[n];
                }
                this.um.diledelay = 0;
                if (this.um.diled != 10) {
                    ++this.um.diled;
                }
            }
        }
        catch (Exception exception) {
            try {
                this.dSocket.close();
            }
            catch (Exception exception2) {
                // empty catch block
            }
            this.dSocket = null;
            this.errd = true;
        }
        this.started = false;
        this.con = null;
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

