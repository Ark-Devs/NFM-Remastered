/*
 * Decompiled with CFR 0.152.
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class udpServe
implements Runnable {
    Thread servo;
    DatagramSocket dSocket;
    UDPMistro um;
    int mport = 7060;
    int im = 0;
    int[] lsframe = new int[]{-1, -1, -1, -1, -1, -1, -1, -1};

    public udpServe(UDPMistro uDPMistro, int n) {
        this.um = uDPMistro;
        this.im = n;
        this.mport = 7060 + this.im;
        this.servo = new Thread(this);
        this.servo.start();
    }

    public void stopServe() {
        try {
            this.dSocket.close();
            this.dSocket = null;
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (this.servo != null) {
            this.servo.stop();
            this.servo = null;
        }
    }

    @Override
    public void run() {
        try {
            this.dSocket = new DatagramSocket(this.mport);
            byte[] byArray = new byte[128];
            block2: while (true) {
                int n;
                int n2;
                int n3;
                DatagramPacket datagramPacket = new DatagramPacket(byArray, byArray.length);
                this.dSocket.receive(datagramPacket);
                String string = new String(datagramPacket.getData());
                String string2 = this.getSvalue(string, 0);
                int n4 = this.getvalue(string, 1);
                if (n4 == this.im && this.im < this.um.nplayers && this.um.out[n4] != 76) {
                    int n5 = this.getvalue(string, 2);
                    n3 = 0;
                    for (n2 = 0; n2 < 3; ++n2) {
                        if (n5 == this.um.frame[n4][n2]) continue;
                        ++n3;
                    }
                    if (n3 == 3) {
                        for (n2 = 0; n2 < 3; ++n2) {
                            if (n5 <= this.um.frame[n4][n2]) continue;
                            for (n = 2; n >= n2 + 1; --n) {
                                this.um.frame[n4][n] = this.um.frame[n4][n - 1];
                                this.um.info[n4][n] = this.um.info[n4][n - 1];
                            }
                            this.um.frame[n4][n2] = n5;
                            this.um.info[n4][n2] = this.getSvalue(string, 3);
                            n2 = 3;
                        }
                    }
                    if (this.um.gocnt[n4] != 0) {
                        n2 = 0;
                        for (n = 0; n < this.um.nplayers; ++n) {
                            if (this.um.frame[n][0] < 0) continue;
                            ++n2;
                        }
                        if (n2 == this.um.nplayers) {
                            string2 = "1111111";
                            int n6 = n4;
                            this.um.gocnt[n6] = this.um.gocnt[n6] - 1;
                        }
                    }
                    if (!this.um.go) {
                        n2 = 0;
                        for (n = 0; n < this.um.nplayers; ++n) {
                            if (this.um.frame[n][0] < 0) continue;
                            ++n2;
                        }
                        if (n2 == this.um.nplayers) {
                            this.um.gocnt[0] = this.um.gocnt[0] - 1;
                        }
                        if (this.um.gocnt[0] <= 1) {
                            this.um.go = true;
                        }
                    }
                }
                InetAddress inetAddress = datagramPacket.getAddress();
                n3 = datagramPacket.getPort();
                n2 = 0;
                while (true) {
                    if (n2 >= this.um.nplayers) continue block2;
                    if (n2 != this.im) {
                        int n7;
                        n = -1;
                        for (n7 = 0; n7 < 3; ++n7) {
                            if (this.um.frame[n2][n7] != this.lsframe[n2] + 1) continue;
                            n = n7;
                        }
                        if (n == -1) {
                            for (n7 = 0; n7 < 3; ++n7) {
                                if (this.um.frame[n2][n7] <= this.lsframe[n2]) continue;
                                n = n7;
                            }
                        }
                        if (n == -1) {
                            n = 0;
                        }
                        this.lsframe[n2] = this.um.frame[n2][n];
                        String string3 = "" + string2 + "|" + n2 + "|" + this.um.frame[n2][n] + "|" + this.um.info[n2][n] + "|";
                        byte[] byArray2 = string3.getBytes();
                        DatagramPacket datagramPacket2 = new DatagramPacket(byArray2, byArray2.length, inetAddress, n3);
                        this.dSocket.send(datagramPacket2);
                    }
                    ++n2;
                }
                break;
            }
        }
        catch (Exception exception) {
            return;
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

