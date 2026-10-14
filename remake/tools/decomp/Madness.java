/*
 * Decompiled with CFR 0.152.
 */
import java.applet.Applet;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterInputStream;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import javax.swing.JOptionPane;

public class Madness
extends Panel {
    static Frame frame;
    static Applet applet;
    static String fpath;
    static boolean fullscreen;
    static int anti;
    static GraphicsDevice myDevice;
    static DisplayMode defdisp;
    static DisplayMode fulldisp;
    static int testdrive;
    static String testcar;
    static int textid;
    static int updateon;
    static String upfile;
    static boolean inisetup;
    static int endadv;
    static long advtime;

    public static void main(String[] stringArray) {
        Object object;
        System.runFinalizersOnExit((boolean)true);
        frame = new Frame("Need for Madness");
        frame.setBackground(new Color(0, 0, 0));
        frame.setIgnoreRepaint(true);
        fpath = "";
        boolean bl = false;
        for (String string : stringArray) {
            if (!bl) {
                fpath = fpath + string;
                bl = true;
                continue;
            }
            fpath = fpath + " " + string;
        }
        if (!fpath.equals("")) {
            if (fpath.equals("manar")) {
                fpath = "";
                try {
                    object = new File("data/manar.ok");
                    if (!((File)object).exists()) {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter((File)object));
                        bufferedWriter.write("" + (int)(Math.random() * 1000.0) + "");
                        bufferedWriter.newLine();
                        bufferedWriter.close();
                        bufferedWriter = null;
                    }
                }
                catch (Exception exception) {}
            } else {
                object = new File("" + fpath + "data/models.zip");
                if (!((File)object).exists()) {
                    fpath = "";
                }
            }
        }
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage("" + fpath + "data/icon.png"));
        applet = new GameSparker();
        frame.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                Madness.exitsequance();
            }
        });
        frame.add("Center", applet);
        frame.show();
        frame.setMinimumSize(new Dimension(930, 586));
        frame.setSize(930, 586);
        frame.setExtendedState(6);
        applet.init();
        applet.start();
        object = GraphicsEnvironment.getLocalGraphicsEnvironment();
        myDevice = ((GraphicsEnvironment)object).getDefaultScreenDevice();
        defdisp = myDevice.getDisplayMode();
        Madness.checknupdate(36);
    }

    public static void gofullscreen() {
        int n;
        int n2;
        Object object;
        Object object2;
        int n3;
        int n4;
        int[] nArray;
        String[] stringArray;
        DisplayMode[] displayModeArray;
        block23: {
            displayModeArray = myDevice.getDisplayModes();
            stringArray = new String[100];
            nArray = new int[100];
            n4 = 0;
            float f = (float)defdisp.getWidth() / (float)defdisp.getHeight();
            float f2 = -1.0f;
            n3 = 0;
            for (int i = 0; i < displayModeArray.length; ++i) {
                if (displayModeArray[i].getWidth() < 800 || displayModeArray[i].getBitDepth() < 16 || n3 >= 100) continue;
                if (displayModeArray[i].getWidth() < 900) {
                    float f3 = (float)displayModeArray[i].getWidth() / (float)displayModeArray[i].getHeight();
                    if ((f3 = Math.abs(f - f3)) <= f2 || f2 == -1.0f) {
                        n4 = n3;
                        f2 = f3;
                    }
                }
                stringArray[n3] = "" + displayModeArray[i].getWidth() + " x " + displayModeArray[i].getHeight() + " Resolution   -   " + displayModeArray[i].getBitDepth() + " Bits   -   " + displayModeArray[i].getRefreshRate() + " Refresh Rate";
                nArray[n3] = i;
                ++n3;
            }
            if (f2 != -1.0f) {
                int n5 = n4;
                stringArray[n5] = stringArray[n5] + "     <  Recommended";
            }
            try {
                object2 = new File("" + fpath + "data/full_screen.data");
                if (!((File)object2).exists()) break block23;
                BufferedReader bufferedReader = new BufferedReader(new FileReader((File)object2));
                object = null;
                n2 = 0;
                while ((object = bufferedReader.readLine()) != null && n2 == 0) {
                    object = ((String)object).trim();
                    n = n4;
                    try {
                        n = Integer.valueOf((String)object);
                    }
                    catch (Exception exception) {
                        n = n4;
                    }
                    n4 = n;
                    if (n4 < 0) {
                        n4 = 0;
                    }
                    if (n4 > n3 - 1) {
                        n4 = n3 - 1;
                    }
                    n2 = 1;
                }
                bufferedReader.close();
                bufferedReader = null;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        object2 = new String[n3];
        for (int i = 0; i < n3; ++i) {
            object2[i] = stringArray[i];
        }
        Object[] objectArray = object2;
        object = JOptionPane.showInputDialog(null, "Choose a screen resolution setting below and click OK to try it.\nExit Fullscreen by pressing [Esc].\n\nIMPORTANT: If the game does not display properly in Fullscreen press [Esc]      \nand try a different resolution setting below,", "Fullscreen Options", 1, null, objectArray, objectArray[n4]);
        n2 = -1;
        if (object != null) {
            for (n = 0; n < n3; ++n) {
                if (!object.equals(objectArray[n])) continue;
                n2 = nArray[n];
                n4 = n;
                break;
            }
        }
        if (n2 != -1) {
            try {
                File file = new File("" + fpath + "data/full_screen.data");
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                bufferedWriter.write("" + n4 + "");
                bufferedWriter.newLine();
                bufferedWriter.close();
                bufferedWriter = null;
            }
            catch (Exception exception) {
                // empty catch block
            }
            fullscreen = true;
            frame.dispose();
            frame = new Frame("Fullscreen Need for Madness");
            frame.setBackground(new Color(0, 0, 0));
            frame.setUndecorated(true);
            frame.setResizable(false);
            frame.setExtendedState(6);
            frame.setIgnoreRepaint(true);
            frame.add("Center", applet);
            frame.show();
            if (myDevice.isFullScreenSupported()) {
                try {
                    myDevice.setFullScreenWindow(frame);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (myDevice.isDisplayChangeSupported()) {
                    try {
                        myDevice.setDisplayMode(displayModeArray[n2]);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
            applet.requestFocus();
        }
    }

    public static void exitfullscreen() {
        frame.dispose();
        frame = new Frame("Need for Madness");
        frame.setBackground(new Color(0, 0, 0));
        frame.setIgnoreRepaint(true);
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage("" + fpath + "data/icon.gif"));
        frame.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                Madness.exitsequance();
            }
        });
        frame.add("Center", applet);
        frame.show();
        if (myDevice.isFullScreenSupported()) {
            try {
                myDevice.setDisplayMode(defdisp);
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (myDevice.isDisplayChangeSupported()) {
                try {
                    myDevice.setFullScreenWindow(null);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        frame.setMinimumSize(new Dimension(930, 586));
        frame.setSize(800, 586);
        frame.setExtendedState(6);
        applet.requestFocus();
        fullscreen = false;
    }

    public static void exitsequance() {
        if (updateon == 0 || updateon == 3) {
            if (endadv == 1) {
                endadv = 2;
            }
            if (updateon != 3) {
                applet.stop();
            }
            frame.removeAll();
            try {
                Thread.sleep(200L);
            }
            catch (Exception exception) {
                // empty catch block
            }
            applet.destroy();
            applet = null;
            System.exit(0);
        }
    }

    public static void checknupdate(int n) {
        Object object;
        String string = "";
        boolean bl = true;
        boolean bl2 = false;
        String string2 = "";
        int n2 = 0;
        String[] stringArray = new String[100];
        String[] stringArray2 = new String[100];
        while (bl) {
            bl = false;
            try {
                URL uRL = new URL("http://multiplayer.needformadness.com/update/" + n + ".txt");
                uRL.openConnection().setConnectTimeout(5000);
                string = uRL.openConnection().getContentType();
                if (string.equals("text/plain")) {
                    object = new DataInputStream(uRL.openStream());
                    String string3 = "";
                    while ((string3 = ((DataInputStream)object).readLine()) != null) {
                        if ((string3 = string3.trim()).startsWith("maddapp")) {
                            string2 = Madness.getfuncSvalue("maddapp", string3, 0);
                            bl2 = true;
                            bl = true;
                        }
                        if (!string3.startsWith("file") || n2 >= 100) continue;
                        stringArray[n2] = Madness.getfuncSvalue("file", string3, 0);
                        stringArray2[n2] = Madness.getfuncSvalue("file", string3, 1);
                        ++n2;
                        bl = true;
                    }
                }
                ++n;
            }
            catch (Exception exception) {
                string = "";
            }
        }
        if (bl2 || n2 != 0) {
            Object object2;
            Object object3;
            Object object4;
            updateon = 1;
            frame.dispose();
            while (inisetup) {
            }
            applet.stop();
            applet.destroy();
            if (fullscreen && myDevice.isFullScreenSupported()) {
                try {
                    myDevice.setDisplayMode(defdisp);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (myDevice.isDisplayChangeSupported()) {
                    try {
                        myDevice.setFullScreenWindow(null);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
            frame = new Frame("Updating Need for Madness...");
            frame.setBackground(new Color(234, 240, 247));
            frame.addWindowListener(new WindowAdapter(){

                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    Madness.exitsequance();
                }
            });
            applet = new update();
            frame.add("Center", applet);
            frame.show();
            frame.setSize(800, 300);
            frame.setResizable(false);
            frame.setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 800.0) / 2.0), (int)((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 300.0) / 2.0));
            applet.init();
            applet.start();
            if (n2 != 0) {
                for (int i = 0; i < n2; ++i) {
                    try {
                        upfile = "Downloading and updating file: " + stringArray2[i] + "";
                        updateon = 2;
                        object = new URL(stringArray[i]);
                        int n3 = ((URL)object).openConnection().getContentLength();
                        object4 = new DataInputStream(((URL)object).openStream());
                        object3 = new byte[n3];
                        ((DataInputStream)object4).readFully((byte[])object3);
                        object2 = new File("" + fpath + "" + stringArray2[i] + "");
                        FileOutputStream fileOutputStream = new FileOutputStream((File)object2);
                        fileOutputStream.write((byte[])object3);
                        fileOutputStream.close();
                        fileOutputStream = null;
                        ((FilterInputStream)object4).close();
                        object4 = null;
                        continue;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }
            if (bl2) {
                try {
                    upfile = "Downloading and updating game's code";
                    updateon = 2;
                    URL uRL = new URL(string2);
                    int n4 = uRL.openConnection().getContentLength();
                    DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                    object4 = new byte[n4];
                    dataInputStream.readFully((byte[])object4);
                    object3 = new File("" + fpath + "madapp.jar");
                    if (((File)object3).exists()) {
                        object2 = new FileOutputStream((File)object3);
                        ((FileOutputStream)object2).write((byte[])object4);
                        ((FileOutputStream)object2).close();
                        object2 = null;
                    }
                    if (((File)(object3 = new File("" + fpath + "madapps.jar"))).exists()) {
                        object2 = new FileOutputStream((File)object3);
                        ((FileOutputStream)object2).write((byte[])object4);
                        ((FileOutputStream)object2).close();
                        object2 = null;
                    }
                    if (((File)(object3 = new File("" + fpath + "data/madapp.jar"))).exists()) {
                        object2 = new FileOutputStream((File)object3);
                        ((FileOutputStream)object2).write((byte[])object4);
                        ((FileOutputStream)object2).close();
                        object2 = null;
                    }
                    if (((File)(object3 = new File("" + fpath + "data/madapps.jar"))).exists()) {
                        object2 = new FileOutputStream((File)object3);
                        ((FileOutputStream)object2).write((byte[])object4);
                        ((FileOutputStream)object2).close();
                        object2 = null;
                    }
                    if (((File)(object3 = new File("" + fpath + "Madness.app/Contents/Java/madapp.jar"))).exists()) {
                        object2 = new FileOutputStream((File)object3);
                        ((FileOutputStream)object2).write((byte[])object4);
                        ((FileOutputStream)object2).close();
                        object2 = null;
                    }
                    object3 = new File("" + fpath + "Game.jar");
                    object2 = new FileOutputStream((File)object3);
                    ((FileOutputStream)object2).write((byte[])object4);
                    ((FileOutputStream)object2).close();
                    object2 = null;
                    dataInputStream.close();
                    dataInputStream = null;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            updateon = 3;
        }
    }

    public static void carmaker() {
        applet.stop();
        frame.removeAll();
        try {
            Thread.sleep(400L);
        }
        catch (Exception exception) {
            // empty catch block
        }
        applet.destroy();
        applet = null;
        System.gc();
        System.runFinalization();
        try {
            Thread.sleep(400L);
        }
        catch (Exception exception) {
            // empty catch block
        }
        applet = new CarMaker();
        frame.add("Center", applet);
        frame.show();
        applet.init();
        applet.start();
    }

    public static void stagemaker() {
        applet.stop();
        frame.removeAll();
        try {
            Thread.sleep(400L);
        }
        catch (Exception exception) {
            // empty catch block
        }
        applet.destroy();
        applet = null;
        System.gc();
        System.runFinalization();
        try {
            Thread.sleep(400L);
        }
        catch (Exception exception) {
            // empty catch block
        }
        applet = new StageMaker();
        frame.add("Center", applet);
        frame.show();
        applet.init();
        applet.start();
    }

    public static void game() {
        applet.stop();
        frame.removeAll();
        try {
            Thread.sleep(400L);
        }
        catch (Exception exception) {
            // empty catch block
        }
        applet.destroy();
        applet = null;
        System.gc();
        System.runFinalization();
        try {
            Thread.sleep(400L);
        }
        catch (Exception exception) {
            // empty catch block
        }
        applet = new GameSparker();
        frame.add("Center", applet);
        frame.show();
        applet.init();
        applet.start();
    }

    public static void advopen() {
        try {
            File file = new File("" + fpath + "data/user.data");
            if (file.exists()) {
                Date date = new Date();
                long l = date.getTime();
                if (advtime == 0L || l - advtime > 120000L) {
                    String string = System.getProperty("os.name").toLowerCase();
                    if (string.indexOf("win") != -1) {
                        Object object;
                        File file2 = new File("" + fpath + "data/adv.bat");
                        boolean bl = false;
                        if (!file2.exists()) {
                            bl = true;
                        } else if (file2.length() != 81L) {
                            bl = true;
                        }
                        if (bl) {
                            object = new BufferedWriter(new FileWriter(file2));
                            ((Writer)object).write("cd %programfiles%\\Internet Explorer");
                            ((BufferedWriter)object).newLine();
                            ((Writer)object).write("iexplore -k http://www.needformadness.com/");
                            ((BufferedWriter)object).newLine();
                            ((BufferedWriter)object).close();
                            object = null;
                        }
                        object = Runtime.getRuntime().exec(file2.getAbsolutePath());
                        DataInputStream dataInputStream = new DataInputStream(((Process)object).getInputStream());
                        while (dataInputStream.readLine() != null) {
                        }
                    } else {
                        Madness.openurl("http://www.needformadness.com/");
                    }
                    advtime = l;
                    endadv = 1;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static String urlopen() {
        String string = "explorer";
        String string2 = System.getProperty("os.name").toLowerCase();
        if (string2.indexOf("linux") != -1 || string2.indexOf("unix") != -1 || string2.equals("aix")) {
            string = "xdg-open";
        }
        if (string2.indexOf("mac") != -1) {
            string = "open";
        }
        return string;
    }

    public static void openurl(String string) {
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

    public static String getfuncSvalue(String string, String string2, int n) {
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

    static {
        fpath = "";
        fullscreen = false;
        anti = 1;
        testdrive = 0;
        testcar = "";
        textid = 0;
        updateon = 0;
        upfile = "";
        inisetup = false;
        endadv = 0;
        advtime = 0L;
    }
}

