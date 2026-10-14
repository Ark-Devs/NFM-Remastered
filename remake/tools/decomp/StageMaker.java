/*
 * Decompiled with CFR 0.152.
 */
import java.applet.Applet;
import java.awt.AlphaComposite;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;

public class StageMaker
extends Applet
implements Runnable {
    Graphics2D rd;
    Image offImage;
    Thread thredo;
    boolean exwist = false;
    FontMetrics ftm;
    int apx = 0;
    int apy = 0;
    String sstage = "";
    String suser = "Horaks";
    int tab = 0;
    int tabed = -1;
    Image[] btgame = new Image[2];
    Image logo;
    boolean onbtgame = false;
    boolean focuson = true;
    boolean overcan = false;
    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;
    boolean zoomi = false;
    boolean zoomo = false;
    String stagename = "";
    String tstage = "snap(0,0,0)\r\nsky(191,215,255)\r\nclouds(255,255,255,5,-1000)\r\nfog(195,207,230)\r\nground(192,194,202)\r\ntexture(0,0,0,50)\r\nfadefrom(5000)\r\ndensity(5)\n\rmountains(" + (int)(Math.random() * 100000.0) + ")\r\nnlaps(5)\r\n\r\n";
    String bstage = "set(47,0,0,0)\r\nmaxr(11,28500,-5600)\r\nmaxb(9,-8000,-12300)\r\nmaxl(11,-14700,-5600)\r\nmaxt(9,44800,-12300)\r\n";
    String[] undos = new String[5000];
    int nundo = 0;
    Medium m = new Medium();
    CheckPoints cp = new CheckPoints();
    Trackers t = new Trackers();
    ContO[] bco = new ContO[67];
    ContO[] co = new ContO[601];
    int nob = 0;
    int xnob = 0;
    int errd = 0;
    int origfade = 5000;
    int sfase = 0;
    Smenu slstage = new Smenu(2000);
    TextField srch = new TextField("", 38);
    Smenu strtyp = new Smenu(40);
    Smenu ptyp = new Smenu(40);
    Smenu part = new Smenu(40);
    int sptyp = 0;
    int spart = 0;
    int sp = 0;
    int lsp = -1;
    int seq = 0;
    boolean setcur = false;
    boolean epart = false;
    boolean arrng = false;
    int esp = -1;
    int hi = -1;
    int arrcnt = 0;
    int chi = -1;
    boolean seqn = false;
    int rot = 0;
    int adrot = 0;
    Image[] su = new Image[2];
    Image[] sl = new Image[2];
    Image[] sd = new Image[2];
    Image[] sr = new Image[2];
    Image[] zi = new Image[2];
    Image[] zo = new Image[2];
    boolean pgen = false;
    float pwd = 2L + Math.round(Math.random() * 4.0);
    float phd = 2L + Math.round(Math.random() * 4.0);
    int fgen = 0;
    int sx = 0;
    int sz = 1500;
    int sy = -10000;
    TextField fixh = new TextField("2000", 5);
    int hf = 2000;
    int[][] atp = new int[][]{{0, 2800, 0, -2800}, {0, 2800, 0, -2800}, {1520, 2830, -1520, -2830}, {-1520, 2830, 1520, -2830}, {0, -1750, 1750, 0}, {0, 2800, 0, -2800}, {0, 2800, 0, -2800}, {0, -1750, 1750, 0}, {0, 2800, 0, -2800}, {0, -1750, 1750, 0}, {0, 2800, 0, -2800}, {0, 2800, 0, -2800}, {0, 560, 0, -560}, {0, 0, 0, 0}, {0, 0, 0, 0}, {385, 980, 385, -980}, {0, 0, 0, -600}, {0, 0, 0, 0}, {0, 2164, 0, -2164}, {0, 2164, 0, -2164}, {0, 3309, 0, -1680}, {0, 1680, 0, -3309}, {350, 0, -350, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {1810, 980, 1810, -980}, {0, 0, 0, 0}, {0, 500, 0, -500}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 2800, 0, -2800}, {0, 2800, 0, -2800}, {0, 1680, 0, -3309}, {0, 2800, 0, -2800}, {0, 2800, 0, -2800}, {0, 2800, 0, -2800}, {700, 1400, 700, -1400}, {0, -1480, 0, -1480}, {0, 0, 0, 0}, {350, 0, -350, 0}, {0, 0, 0, 0}, {700, 0, -700, 0}, {0, 0, 0, 0}, {0, -2198, 0, 1482}, {0, -1319, 0, 1391}, {0, -1894, 0, 2271}, {0, -826, 0, 839}, {0, -1400, 0, 1400}, {0, -1400, 0, 1400}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    String[] discp = new String[]{"NormalRoad :  Basic asphalt road.\nAttaches correctly to the following other parts :\n\n'NormalRoad Turn',  'NormalRoad End',  'NormalRoad TwistedLeft',  'NormalRoad TwistedRight',  'NormalRoad Edged',\n'NormalRoad-Raised Ramp',  'Normal-Off-Road Blend'  and  'Halfpipe-Normal-Road Blend'\n\n", "NormalRoad Edged :  Asphalt road with edged side blocks (a destructive road).\nAttaches correctly to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad TwistedLeft',  'NormalRoad TwistedRight',\n'NormalRoad-Raised Ramp',  'Normal-Off-Road Blend'  and  'Halfpipe-Normal-Road Blend'\n\n", "NormalRoad TwistedRight :  Asphalt road twisted towards the right.\nAttaches correctly to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad Twistedleft',\n'NormalRoad-Raised Ramp',  'Normal-Off-Road Blend'  and  'Halfpipe-Normal-Road Blend'\n\n", "NormalRoad TwistedLeft :  Asphalt road twisted towards the left.\nAttaches correctly to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedRight',\n'NormalRoad-Raised Ramp',  'Normal-Off-Road Blend'  and  'Halfpipe-Normal-Road Blend'\n\n", "NormalRoad Turn :  Asphalt corner road turn.\nAttaches correctly to the following other parts :\n\n'NormalRoad',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft',  'NormalRoad TwistedRight',\n'NormalRoad-Raised Ramp', 'Normal-Off-Road Blend'  and  'Halfpipe-Normal-Road Blend'\n\n", "OffRoad :  Basic sandy dirt-road.\nAttaches correctly to the following other parts :\n\n'OffRoad Turn',  'OffRoad End',  'OffRoad BumpyGreen',  'OffRoad-BumpySides Start',  'Off-Halfpipe-Road Blend'\nand  'Normal-Off-Road Blend'\n\n", "OffRoad BumpyGreen :  Dirt-road with bumpy greenery in the middle.\nAttaches correctly to the following other parts :\n\n'OffRoad',  'OffRoad Turn',  'OffRoad End',  'OffRoad-BumpySides Start',  'Off-Halfpipe-Road Blend'\nand  'Normal-Off-Road Blend'\n\n", "OffRoad Turn :  Dirt-road corner turn.\nAttaches correctly to the following other parts :\n\n'OffRoad',  'OffRoad End',  'OffRoad BumpyGreen',  ' OffRoad-BumpySides Start',  'Off-Halfpipe-Road Blend'\nand 'Normal-Off-Road Blend'\n\n", "HalfpipeRoad :  Basic road for the half-pipe ramp.\nAttaches correctly to the following other parts :\n\n'Off-Halfpipe-Road Blend',  'HalfpipeRoad',  'HalfpipeRoad Turn',  'HalfpipeRoad-Ramp Filler'\nand  'Halfpipe-Normal-Road Blend'\n\n", "HalfpipeRoad Turn :  Half-pipe corner road turn.\nAttaches correctly to the following other parts :\n\n'HalfpipeRoad',  'Off-Halfpipe-Road Blend',  'HalfpipeRoad'  and  'Halfpipe-Normal-Road Blend'\n\n", "Normal-Off-Road Blend :  Road blend between the normal asphalt road and the dirt-road.\nAttaches correctly to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft',\n'NormalRoad TwistedRight',  'NormalRoad-Raised Ramp', 'Halfpipe-Normal-Road Blend' 'OffRoad',  'OffRoad Turn',\n'OffRoad End',  'OffRoad BumpyGreen',  ' OffRoad-BumpySides Start'  and  'Off-Halfpipe-Road Blend'\n\n", "Off-Halfpipe-Road Blend :  Road blend between the dirt-road and the half-pipe road.\nAttaches correctly to the following other parts :\n\n'OffRoad',  'OffRoad Turn',  'OffRoad End',  'OffRoad BumpyGreen',  'OffRoad-BumpySides Start',\n'HalfpipeRoad',  'HalfpipeRoad Turn',  'Halfpipe-Normal-Road Blend'  and  'Normal-Off-Road Blend'\n\n", "Halfpipe-Normal-Road Blend :  Road blend between the normal asphalt road and the half-pipe road.\nAttaches correctly to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft',\n'NormalRoad TwistedRight',  'NormalRoad-Raised Ramp',  'HalfpipeRoad',  'Off-Halfpipe-Road Blend',  'HalfpipeRoad'\nand  'Off-Halfpipe-Road Blend'\n\n", "NormalRoad End :  The end part of the normal asphalt road.\nAttaches correctly to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad Edged',  'NormalRoad TwistedLeft',  'NormalRoad TwistedRight',\n'NormalRoad-Raised Ramp',  'Normal-Off-Road Blend'  and  'Halfpipe-Normal-Road Blend'\n\n", "OffRoad End :  The end part of the dirt-road.\nAttaches correctly to the following other parts :\n\n'OffRoad',  'OffRoad Turn',  'OffRoad BumpyGreen',  ' OffRoad-BumpySides Start',  'Off-Halfpipe-Road Blend'\nand  'Normal-Off-Road Blend'\n\n", "HalfpipeRoad-Ramp Filler :  A part that gets placed between the half-pipe road and the half-pipe ramp to extend the distance in between.\nAttaches correctly to the following other parts :\n\n'HalfpipeRoad'  and  'Halfpipe'\n\n", "Basic Ramp :  Basic 30 degree asphalt ramp.\nAttaches correctly over and to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft'\nand  'NormalRoad TwistedRight'\n\n", "Crash Ramp :  A 35 degree ramp with big side blocks for crashing into.\nAttaches correctly over and to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft'\nand  'NormalRoad TwistedRight'\n\n", "Two-Way Ramp :  Two way 15 degree inclined ramp.\nAttaches correctly over and to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft'\nand  'NormalRoad TwistedRight'\n\n", "Two-Way High-Low Ramp :  Two way 15 degree inclined ramp, with peeked side for an optional higher car jump.\nAttaches correctly over and to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft'\nand  'NormalRoad TwistedRight'\n\n", "Landing Ramp :  A ramp that is both a landing inclination and an obstacle as well, it is usually placed just after another normal ramp.\nAttaches correctly over and to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft'\nand   'NormalRoad TwistedRight'\n\n", "Big-Takeoff Ramp:  A big takeoff ramp for getting huge heights with the cars.\nAttaches correctly over and to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft'\nand   'NormalRoad TwistedRight'\n\n", "Small Ramp :  A small ramp that can be placed on either side of the road.\nAttaches correctly over and to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft'\nand   'NormalRoad TwistedRight'\n\n", "Offroad Bump Ramp :  A small bump ramp that is to be placed over the off-road dirt tracks.\nAttaches correctly over and to the following other parts :\n\n'OffRoad',  'OffRoad Turn',  'OffRoad End',  'OffRoad BumpyGreen',  'OffRoad-BumpySides Start'\nand  'OffRoad-BumpySides'\n\n", "Offroad Big Ramp :  The big off-road dirt mountain like ramp!\nAttaches correctly over and to the following other parts :\n\n'OffRoad',  'OffRoad Turn',  'OffRoad End',  'OffRoad BumpyGreen',  'OffRoad-BumpySides Start'\nand  'OffRoad-BumpySides'\n\n", "Offroad Ramp :  Normal sized off-road dirt track ramp!\nAttaches correctly over and to the following other parts :\n\n'OffRoad',  'OffRoad Turn',  'OffRoad End',  'OffRoad BumpyGreen',  'OffRoad-BumpySides Start'\nand  'OffRoad-BumpySides'\n\n", "Halfpipe :  The Half-pipe ramp, two of these ramps opposite each other create a half-pipe for the cars!\nAttaches correctly over and to the following other parts :\n\n'HalfpipeRoad',  'HalfpipeRoad Turn'  and  'HalfpipeRoad-Ramp Filler'\n\n", "Spiky Pillars :  An obstacle that is usually placed after a ramp for the cars to crash onto if they did not jump high or far enough!\nAttaches correctly over following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft'\nand  'NormalRoad TwistedRight'\n\n", "Rail Doorway :  A rail doorway that works as an obstacle for cars flying above it or cars driving through it!\nAttaches correctly over following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft'\nand  'NormalRoad TwistedRight'\n\n", "The Wall", "Checkpoint :  The checkpoint part that ultimately decides how you stage is raced, place carefully with thought.\n(Any stage must have at least two checkpoints to work).\nMounts correctly over the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft',\n'NormalRoad TwistedRight',  'OffRoad',  'OffRoad Turn',  'OffRoad End',  'OffRoad BumpyGreen',\n'OffRoad-BumpySides Start',  'OffRoad-BumpySides',  'Rollercoaster Start/End'  and  'Rollercoaster Road 2,3,4 and 5'\n\n", "Fixing Hoop :  The fixing hoop that fixes a car when it flies through it! You can add a max of 5 fixing hoops per stage.\nPlace it anywhere in the stage at an height your choose, the only important thing is that it needs to be reachable by the cars.", "Checkpoint :  The checkpoint part that ultimately decides how you stage is raced, place carefully with thought.\n(Any stage must have at least two checkpoints to work).\nMounts correctly over the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft',\n'NormalRoad TwistedRight',  'OffRoad',  'OffRoad Turn',  'OffRoad End',  'OffRoad BumpyGreen',\n'OffRoad-BumpySides Start',  'OffRoad-BumpySides',  'Rollercoaster Start/End'  and  'Rollercoaster Road 2,3,4 and 5'\n\n", "OffRoad BumpySides :  Off-road dirt track with bumpy sandbar sides.\nAttaches correctly to the following other parts :\n\n'OffRoad-BumpySides Start'\n\n", "OffRoad-BumpySides Start: The start of the off-road dirt track with bumpy sandbar sides.\nAttaches correctly to the following other parts :\n\n'OffRoad',  'OffRoad Turn',  'OffRoad End',  'OffRoad BumpyGreen',  'OffRoad-BumpySides',\n'Off-Halfpipe-Road Blend'  and  'Normal-Off-Road Blend'\n\n", "NormalRoad-Raised Ramp:  The start of the raised above the ground road (NormalRoad Raised).\nAttaches correctly to the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft',\n'NormalRoad TwistedRight'  and  'NormalRoad Raised'\n\n", "NormalRoad Raised :  Normal road raised above the ground, cars must avoid falling off it when driving on it.\nAttaches correctly to the following other parts :\n\n'NormalRoad-Raised Ramp'\n\n", "The Start1", "The Start2", "Tunnel Side Ramp:  A ramp that can be used to create a tunnel like road with an open top or can be used as a wall ramp!\nAttaches correctly over only the 'NormalRoad' part.", "Launch Pad Ramp:  A ramp that launches your car fully upwards like a rocket, it also has sides to lock any car climbing it!\nAttaches correctly over following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad TwistedLeft'\nand  'NormalRoad TwistedRight'\n\n", "The Net:  An obstacle part that is to be placed in the center of the road right after a ramp, the idea is that the\ncars jumping the ramp should try to go over it or through it without getting caught crashing (without getting\ncaught in it, getting caught in the net!).\nAttaches correctly over following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft'\nand  'NormalRoad TwistedRight'\n\n", "Speed Ramp:  A ramp that is designed to have the perfect angle to catapult your car the furthest when doing forward loops, it is half the roads width.\nAttaches correctly over following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft'\nand  'NormalRoad TwistedRight'\n\n", "Offroad Hill Ramp:  An offroad hill ramp that has two different inclines from the front and back to jump.\nAttaches correctly over the following other parts :\n\n'OffRoad',  'OffRoad Turn',  'OffRoad End',  'OffRoad BumpyGreen',  'OffRoad-BumpySides Start'\nand  'OffRoad-BumpySides'\n\n", "Bump Slide:  A small bump obstacle that is to be placed on the sides of the road or in the center.\nAttaches correctly over the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft'\nand  'NormalRoad TwistedRight'\n\n", "Offroad Big Hill Ramp:  An offroad big hill ramp that has two different inclines from the front and back to jump.\nAttaches correctly over the following other parts :\n\n'OffRoad',  'OffRoad Turn',  'OffRoad End',  'OffRoad BumpyGreen',  'OffRoad-BumpySides Start'\nand  'OffRoad-BumpySides'\n\n", "Rollercoaster Start/End:  The ramp that starts the Rollercoaster Road and ends it.\nAttaches correctly over and to following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft',\n 'NormalRoad TwistedRight'  and  'Rollercoaster Start/End'\n\n", "Rollercoaster Road1\nAttaches correctly to only 'Rollercoaster Start/End', 'Rollercoaster Road2' and itself.\n\n", "Rollercoaster Road3\nAttaches correctly to only 'Rollercoaster Road2', 'Rollercoaster Road4' and itself.\n\n", "Rollercoaster Road4\nAttaches correctly to only 'Rollercoaster Road3', 'Rollercoaster Road5' and itself.\n\n", "Rollercoaster Road2\nAttaches correctly to only 'Rollercoaster Road1', 'Rollercoaster Road3' and itself.\n\n", "Rollercoaster Road5\nAttaches correctly to only 'Rollercoaster Road4' and itself.\n\n", "Offroad Dirt-Pile:  A dirt pile obstacle that is to be placed anywhere in the middle of the road.\nAttaches correctly over the following other parts :\n\n'OffRoad',  'OffRoad Turn',  'OffRoad End',  'OffRoad-BumpySides Start'  and  'OffRoad-BumpySides'\n\n", "Offroad Dirt-Pile:  A dirt pile obstacle that is to be placed anywhere in the middle of the road.\nAttaches correctly over the following other parts :\n\n'OffRoad',  'OffRoad Turn',  'OffRoad End',  'OffRoad-BumpySides Start'  and  'OffRoad-BumpySides'\n\n", "Checkpoint :  The checkpoint part that ultimately decides how you stage is raced, place carefully with thought.\n(Any stage must have at least two checkpoints to work).\nMounts correctly over the following other parts :\n\n'NormalRoad',  'NormalRoad Turn',  'NormalRoad End',  'NormalRoad Edged',  'NormalRoad TwistedLeft',\n'NormalRoad TwistedRight',  'OffRoad',  'OffRoad Turn',  'OffRoad End',  'OffRoad BumpyGreen',\n'OffRoad-BumpySides Start',  'OffRoad-BumpySides',  'Rollercoaster Start/End'  and  'Rollercoaster Road 2,3,4 and 5'\n\n", "Trees/Cactus are decorative stage parts that should be placed outside the race track on the ground and NEVER on any road part or ramp!\nTrees/Cactus are not to be used as obstacles of the race course!\nThey are to be used as out of path ground decoration only.\n\n", "Trees/Cactus are decorative stage parts that should be placed outside the race track on the ground and NEVER on any road part or ramp!\nTrees/Cactus are not to be used as obstacles of the race course!\nThey are to be used as out of path ground decoration only.\n\n", "Trees/Cactus are decorative stage parts that should be placed outside the race track on the ground and NEVER on any road part or ramp!\nTrees/Cactus are not to be used as obstacles of the race course!\nThey are to be used as out of path ground decoration only.\n\n", "Trees/Cactus are decorative stage parts that should be placed outside the race track on the ground and NEVER on any road part or ramp!\nTrees/Cactus are not to be used as obstacles of the race course!\nThey are to be used as out of path ground decoration only.\n\n", "Trees/Cactus are decorative stage parts that should be placed outside the race track on the ground and NEVER on any road part or ramp!\nTrees/Cactus are not to be used as obstacles of the race course!\nThey are to be used as out of path ground decoration only.\n\n", "Trees/Cactus are decorative stage parts that should be placed outside the race track on the ground and NEVER on any road part or ramp!\nTrees/Cactus are not to be used as obstacles of the race course!\nThey are to be used as out of path ground decoration only.\n\n", "Trees/Cactus are decorative stage parts that should be placed outside the race track on the ground and NEVER on any road part or ramp!\nTrees/Cactus are not to be used as obstacles of the race course!\nThey are to be used as out of path ground decoration only.\n\n", "Trees/Cactus are decorative stage parts that should be placed outside the race track on the ground and NEVER on any road part or ramp!\nTrees/Cactus are not to be used as obstacles of the race course!\nThey are to be used as out of path ground decoration only.\n\n", "Trees/Cactus are decorative stage parts that should be placed outside the race track on the ground and NEVER on any road part or ramp!\nTrees/Cactus are not to be used as obstacles of the race course!\nThey are to be used as out of path ground decoration only.\n\n", "Trees/Cactus are decorative stage parts that should be placed outside the race track on the ground and NEVER on any road part or ramp!\nTrees/Cactus are not to be used as obstacles of the race course!\nThey are to be used as out of path ground decoration only.\n\n", "Trees/Cactus are decorative stage parts that should be placed outside the race track on the ground and NEVER on any road part or ramp!\nTrees/Cactus are not to be used as obstacles of the race course!\nThey are to be used as out of path ground decoration only.\n\n", "Ground Piles are to be paced outside the race track on the ground and NEVER on any road part or ramp!\nThey are to be used as ground decoration and out of race course obstacles (ground obstacles)!\n\n"};
    String[] errlo = new String[]{"The maximum allocated memory for the stage's part's details has been exerted.\nPlease decrease the amount of parts in the stage that have more details then average.", "The maximum amount of road points allowed in the track has been exceeded.\nPlease remove some of the road parts that are in the circler path of the track (the parts that are between the checkpoints).\nOr try to remove some of the extra checkpoints in the track as well.", "The maximum allowed area for a track (the area in between its walls) has been exceeded.\nPlease try to place parts only inside the current allowed area, inside the area between the current maximum wall placements.", "The maximum number of parts allowed per stage has been exceeded.\nPlease remove some of the already extra parts placed in order to make space.", "The maximum number of Fixing Hoops allowed per stage is 5!\nPlease remove the extra Fixing Hoops from your stage to have only 5 main ones left.", "Unknown Error, please make sure the stage you are handling is saved correctly.\nPlease go to the 'Build' tab and press 'Save & Preview'.", "There needs to be at least 2 checkpoints in the Stage in order for the game to work.\nPlease go to the 'Build' tab and select 'Checkpoint' in the Part Selection menu to add more checkpoints.", "The name of the stage is too long!\nPlease go to the 'Stage' tab, click 'Rename Stage' and give your stage a shorter name."};
    int[] rcheckp = new int[]{0, 1, 2, 3, 4, 12, 13, 37};
    int[] ocheckp = new int[]{5, 6, 7, 11, 14, 33, 34, 38};
    boolean onoff = false;
    boolean onfly = false;
    int flyh = 0;
    int[] csky = new int[]{170, 220, 255};
    int[] cgrnd = new int[]{205, 200, 200};
    int[] cfade = new int[]{255, 220, 220};
    int[] texture = new int[]{0, 0, 0, 10};
    int[] cldd = new int[]{210, 210, 210, 1, -1000};
    TextField mgen = new TextField("", 10);
    int vxz = 0;
    int vx = 0;
    int vz = 0;
    int vy = 0;
    int dtab = 0;
    int dtabed = -1;
    int mouseon = -1;
    float[][] hsb = new float[][]{{0.5f, 0.875f, 0.5f}, {0.5f, 0.875f, 0.5f}, {0.5f, 0.875f, 0.5f}};
    Checkbox pfog = new Checkbox("Linked Blend");
    int[] snap = new int[]{50, 50, 50};
    int[] fogn = new int[]{60, 0};
    Smenu nlaps = new Smenu(40);
    Smenu tracks = new Smenu(2000);
    String trackname = "";
    String ltrackname = "";
    int trackvol = 200;
    int tracksize = 111;
    RadicalMod track = new RadicalMod();
    int avon = 0;
    Smenu witho = new Smenu(40);
    int logged = 0;
    TextField tnick = new TextField("", 15);
    TextField tpass = new TextField("", 15);
    Smenu pubitem = new Smenu(707);
    Smenu pubtyp = new Smenu(40);
    int nms = 0;
    int roto = 0;
    String[] mystages = new String[20];
    String[] maker = new String[20];
    int[] pubt = new int[20];
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
    int lxm = 0;
    int lym = 0;
    int cntout = 0;
    boolean preop = false;
    boolean mousdr = false;
    String ttstage = "";

    /*
     * Opcode count of 19604 triggered aggressive code reduction.  Override with --aggressivesizethreshold.
     */
    @Override
    public void run() {
        block754: {
            int n;
            this.thredo.setPriority(10);
            this.btgame[0] = this.getImage("" + Madness.fpath + "data/backtogame1.gif");
            this.btgame[1] = this.getImage("" + Madness.fpath + "data/backtogame2.gif");
            this.logo = this.getImage("" + Madness.fpath + "data/stagemakerlogo.gif");
            for (n = 0; n < 2; ++n) {
                this.su[n] = this.getImage("" + Madness.fpath + "data/su" + (n + 1) + ".gif");
                this.sl[n] = this.getImage("" + Madness.fpath + "data/sl" + (n + 1) + ".gif");
                this.sd[n] = this.getImage("" + Madness.fpath + "data/sd" + (n + 1) + ".gif");
                this.sr[n] = this.getImage("" + Madness.fpath + "data/sr" + (n + 1) + ".gif");
                this.zi[n] = this.getImage("" + Madness.fpath + "data/zi" + (n + 1) + ".gif");
                this.zo[n] = this.getImage("" + Madness.fpath + "data/zo" + (n + 1) + ".gif");
            }
            this.loadbase();
            this.loadsettings();
            if (Madness.testdrive != 0) {
                if (Madness.testcar.equals("Failx12")) {
                    JOptionPane.showMessageDialog(null, "Failed to load stage! Please make sure stage is saved properly before Test Drive.", "Stage Maker", 1);
                    this.thredo.stop();
                } else {
                    this.stagename = Madness.testcar;
                    this.errd = 0;
                    this.readstage(3);
                    if (this.errd == 0) {
                        this.tab = 2;
                        this.dtab = 6;
                        this.witho.select(Madness.testdrive - 3);
                    }
                }
                Madness.testcar = "";
                Madness.testdrive = 0;
            }
            this.requestFocus();
            while (!this.exwist) {
                int n2;
                int n3;
                this.rd.setColor(new Color(225, 225, 225));
                this.rd.fillRect(0, 25, 800, 525);
                this.rd.setColor(new Color(0, 0, 0));
                if (this.tab != this.tabed) {
                    this.hidefields();
                }
                if (this.tab == 0) {
                    int n4;
                    if (this.tabed != this.tab) {
                        this.slstage.removeAll();
                        this.slstage.maxl = 360;
                        this.slstage.add(this.rd, "Select a Stage                      ");
                        String[] stringArray = new File("" + Madness.fpath + "mystages/").list();
                        if (stringArray != null) {
                            for (n4 = 0; n4 < stringArray.length; ++n4) {
                                if (!stringArray[n4].toLowerCase().endsWith(".txt")) continue;
                                this.slstage.add(this.rd, stringArray[n4].substring(0, stringArray[n4].length() - 4));
                            }
                        }
                        if (this.stagename.equals("")) {
                            this.slstage.select(0);
                        } else {
                            this.slstage.select(this.stagename);
                            if (this.stagename.equals(this.slstage.getSelectedItem())) {
                                this.readstage(3);
                                this.sx = 0;
                                this.sz = 1500;
                                this.sy = -10000;
                            } else {
                                this.stagename = "";
                                this.slstage.select(0);
                            }
                        }
                        this.mouseon = -1;
                        this.sfase = 0;
                    }
                    this.rd.drawImage(this.logo, 261, 35, null);
                    if (this.xm > 261 && this.xm < 538 && this.ym > 35 && this.ym < 121) {
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
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    if (this.xm > 200 && this.xm < 550 && this.ym > 467 && this.ym < 504) {
                        if (this.mouseon == -1) {
                            this.mouseon = 2;
                            this.setCursor(new Cursor(12));
                        }
                    } else if (this.mouseon == 2) {
                        this.mouseon = -1;
                        this.setCursor(new Cursor(0));
                    }
                    if (this.mouseon == 2) {
                        this.rd.setColor(new Color(0, 64, 128));
                    } else {
                        this.rd.setColor(new Color(0, 0, 0));
                    }
                    this.rd.drawString("For the Stage Maker Homepage, Development Center and Forums :", 400 - this.ftm.stringWidth("For the Stage Maker Homepage, Development Center and Forums :") / 2, 480);
                    this.rd.setColor(new Color(0, 128, 255));
                    String string = "http://www.needformadness.com/developer/";
                    this.rd.drawString(string, 400 - this.ftm.stringWidth(string) / 2, 500);
                    if (this.mouseon == 2) {
                        this.rd.setColor(new Color(0, 128, 255));
                    } else {
                        this.rd.setColor(new Color(0, 64, 128));
                    }
                    this.rd.drawLine(400 - this.ftm.stringWidth(string) / 2, 501, 400 + this.ftm.stringWidth(string) / 2, 501);
                    if (this.mouseon == 2 && this.mouses == -1) {
                        this.openhlink();
                    }
                    n4 = -110;
                    if (this.xm > 150 && this.xm < 600 && this.ym > 467 + n4 && this.ym < 504 + n4) {
                        if (this.mouseon == -1) {
                            this.mouseon = 1;
                            this.setCursor(new Cursor(12));
                        }
                    } else if (this.mouseon == 1) {
                        this.mouseon = -1;
                        this.setCursor(new Cursor(0));
                    }
                    if (this.mouseon == 1) {
                        this.rd.setColor(new Color(0, 64, 128));
                    } else {
                        this.rd.setColor(new Color(0, 0, 0));
                    }
                    this.rd.drawString("For help and a detailed step by step description on how to use the Stage Maker :", 400 - this.ftm.stringWidth("For help and a detailed step by step description on how to use the Stage Maker :") / 2, 480 + n4);
                    this.rd.setColor(new Color(0, 128, 255));
                    string = "http://www.needformadness.com/developer/help.html";
                    this.rd.drawString(string, 400 - this.ftm.stringWidth(string) / 2, 500 + n4);
                    if (this.mouseon == 1) {
                        this.rd.setColor(new Color(0, 128, 255));
                    } else {
                        this.rd.setColor(new Color(0, 64, 128));
                    }
                    this.rd.drawLine(400 - this.ftm.stringWidth(string) / 2, 501 + n4, 400 + this.ftm.stringWidth(string) / 2, 501 + n4);
                    if (this.mouseon == 1 && this.mouses == -1) {
                        this.openlink();
                    }
                    n3 = -60;
                    n2 = 70;
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRect(227 - n2, 194 + n3, 346 + n2 * 2, 167 + n2 / 5);
                    if (this.sfase == 0) {
                        this.rd.drawString("Select Stage to Edit", 400 - this.ftm.stringWidth("Select Stage to Edit") / 2, 230 + n3);
                        this.slstage.move(220, 240 + n3);
                        if (this.slstage.getWidth() != 360) {
                            this.slstage.setSize(360, 21);
                        }
                        if (!this.slstage.isShowing()) {
                            this.slstage.show();
                        }
                        if (this.button("    Make new Stage    ", 400, 296 + n3, 0, true)) {
                            this.srch.setText("");
                            this.slstage.hide();
                            this.sfase = 1;
                        }
                        if (this.button("     Rename Stage     ", 325, 336 + n3, 0, false)) {
                            if (!this.stagename.equals("")) {
                                this.slstage.hide();
                                this.srch.setText(this.stagename);
                                this.sfase = 2;
                            } else {
                                JOptionPane.showMessageDialog(null, "Please select a stage to rename first.", "Stage Maker", 1);
                            }
                        }
                        if (this.button("      Delete Stage      ", 475, 336 + n3, 0, false)) {
                            if (!this.stagename.equals("")) {
                                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete this stage?\n\n" + this.stagename + "\n\n", "Stage Maker", 0) == 0) {
                                    this.delstage(this.stagename);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Please select a stage to delete first.", "Stage Maker", 1);
                            }
                        }
                        if (this.slstage.getSelectedIndex() != 0) {
                            if (!this.stagename.equals(this.slstage.getSelectedItem())) {
                                this.stagename = this.slstage.getSelectedItem();
                                this.readstage(3);
                                this.sx = 0;
                                this.sz = 1500;
                                this.sy = -10000;
                                this.requestFocus();
                            }
                        } else {
                            this.stagename = "";
                        }
                    }
                    if (this.sfase == 1) {
                        this.rd.drawString("Make a new Stage", 400 - this.ftm.stringWidth("Make a new Stage") / 2, 220 + n3);
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.rd.drawString("New stage name :", 200, 246 + n3);
                        this.movefield(this.srch, 310, 231 + n3, 290, 23);
                        if (!this.srch.isShowing()) {
                            this.srch.show();
                            this.srch.requestFocus();
                        }
                        this.fixtext(this.srch);
                        this.rd.drawString("Starting line type :", 293, 272 + n3);
                        this.strtyp.move(408, 256 + n3);
                        if (!this.strtyp.isShowing()) {
                            this.strtyp.show();
                        }
                        if (this.button("    Make Stage    ", 400, 311 + n3, 0, true)) {
                            this.newstage();
                        }
                        if (this.button("  Cancel  ", 400, 351 + n3, 0, false)) {
                            this.strtyp.hide();
                            this.srch.hide();
                            this.sfase = 0;
                        }
                    }
                    if (this.sfase == 2) {
                        this.rd.drawString("Rename Stage :  " + this.stagename + "", 400 - this.ftm.stringWidth("Rename Stage :  " + this.stagename + "") / 2, 230 + n3);
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.rd.drawString("New name :", 218, 266 + n3);
                        if (!this.srch.isShowing()) {
                            this.srch.show();
                            this.srch.requestFocus();
                        }
                        this.movefield(this.srch, 292, 251 + n3, 290, 23);
                        this.fixtext(this.srch);
                        if (this.button("    Rename Stage    ", 400, 306 + n3, 0, true)) {
                            this.renstage(this.srch.getText());
                        }
                        if (this.button("  Cancel  ", 400, 346 + n3, 0, false)) {
                            this.srch.hide();
                            this.sfase = 0;
                        }
                    }
                }
                if (this.tab == 1) {
                    int n5;
                    int n6;
                    int n7;
                    int n8;
                    int n9;
                    int n10;
                    int n11;
                    if (this.tabed != this.tab) {
                        this.m.trk = 2;
                        this.readstage(0);
                        if (this.sptyp == 0) {
                            this.partroads();
                        }
                        if (this.sptyp == 1) {
                            this.partramps();
                        }
                        if (this.sptyp == 2) {
                            this.partobst();
                        }
                        if (this.sptyp == 5) {
                            this.partrees();
                        }
                        this.onoff = false;
                        this.setCursor(new Cursor(0));
                        this.setcur = false;
                        this.epart = false;
                        this.arrng = false;
                        if (this.nob == 1) {
                            this.sptyp = 0;
                            this.spart = this.co[0].colok == 38 ? 9 : 0;
                        }
                        this.mouseon = -1;
                    }
                    if (this.sptyp == 0) {
                        if (this.spart == 0) {
                            this.sp = 0;
                        }
                        if (this.spart == 1) {
                            this.sp = 4;
                        }
                        if (this.spart == 2) {
                            this.sp = 13;
                        }
                        if (this.spart == 3) {
                            this.sp = 3;
                        }
                        if (this.spart == 4) {
                            this.sp = 2;
                        }
                        if (this.spart == 5) {
                            this.sp = 1;
                        }
                        if (this.spart == 6) {
                            this.sp = 35;
                        }
                        if (this.spart == 7) {
                            this.sp = 36;
                        }
                        if (this.spart == 8) {
                            this.sp = 10;
                        }
                        if (this.spart == 9) {
                            this.sp = 5;
                        }
                        if (this.spart == 10) {
                            this.sp = 7;
                        }
                        if (this.spart == 11) {
                            this.sp = 14;
                        }
                        if (this.spart == 12) {
                            this.sp = 6;
                        }
                        if (this.spart == 13) {
                            this.sp = 34;
                        }
                        if (this.spart == 14) {
                            this.sp = 33;
                        }
                        if (this.spart == 15) {
                            this.sp = 11;
                        }
                        if (this.spart == 16) {
                            this.sp = 8;
                        }
                        if (this.spart == 17) {
                            this.sp = 9;
                        }
                        if (this.spart == 18) {
                            this.sp = 15;
                        }
                        if (this.spart == 19) {
                            this.sp = 12;
                        }
                        if (this.spart == 20) {
                            this.sp = 46;
                        }
                        if (this.spart == 21) {
                            this.sp = 47;
                        }
                        if (this.spart == 22) {
                            this.sp = 50;
                        }
                        if (this.spart == 23) {
                            this.sp = 48;
                        }
                        if (this.spart == 24) {
                            this.sp = 49;
                        }
                        if (this.spart == 25) {
                            this.sp = 51;
                        }
                    }
                    if (this.sptyp == 1) {
                        if (this.spart == 0) {
                            this.sp = 16;
                        }
                        if (this.spart == 1) {
                            this.sp = 18;
                        }
                        if (this.spart == 2) {
                            this.sp = 19;
                        }
                        if (this.spart == 3) {
                            this.sp = 22;
                        }
                        if (this.spart == 4) {
                            this.sp = 17;
                        }
                        if (this.spart == 5) {
                            this.sp = 21;
                        }
                        if (this.spart == 6) {
                            this.sp = 20;
                        }
                        if (this.spart == 7) {
                            this.sp = 39;
                        }
                        if (this.spart == 8) {
                            this.sp = 42;
                        }
                        if (this.spart == 9) {
                            this.sp = 40;
                        }
                        if (this.spart == 10) {
                            this.sp = 23;
                        }
                        if (this.spart == 11) {
                            this.sp = 25;
                        }
                        if (this.spart == 12) {
                            this.sp = 24;
                        }
                        if (this.spart == 13) {
                            this.sp = 43;
                        }
                        if (this.spart == 14) {
                            this.sp = 45;
                        }
                        if (this.spart == 15) {
                            this.sp = 26;
                        }
                    }
                    if (this.sptyp == 2) {
                        if (this.spart == 0) {
                            this.sp = 27;
                        }
                        if (this.spart == 1) {
                            this.sp = 28;
                        }
                        if (this.spart == 2) {
                            this.sp = 41;
                        }
                        if (this.spart == 3) {
                            this.sp = 44;
                        }
                        if (this.spart == 4) {
                            this.sp = 52;
                        }
                        if (this.spart == 5) {
                            this.sp = 53;
                        }
                    }
                    if (this.sptyp == 3) {
                        this.sp = this.onfly ? 54 : (!this.onoff ? 30 : 32);
                    }
                    if (this.sptyp == 4) {
                        this.sp = 31;
                    }
                    if (this.sptyp == 5) {
                        if (this.spart == 0) {
                            this.sp = 55;
                        }
                        if (this.spart == 1) {
                            this.sp = 56;
                        }
                        if (this.spart == 2) {
                            this.sp = 57;
                        }
                        if (this.spart == 3) {
                            this.sp = 58;
                        }
                        if (this.spart == 4) {
                            this.sp = 59;
                        }
                        if (this.spart == 5) {
                            this.sp = 60;
                        }
                        if (this.spart == 6) {
                            this.sp = 61;
                        }
                        if (this.spart == 7) {
                            this.sp = 62;
                        }
                        if (this.spart == 8) {
                            this.sp = 63;
                        }
                        if (this.spart == 9) {
                            this.sp = 64;
                        }
                        if (this.spart == 10) {
                            this.sp = 65;
                        }
                    }
                    if (this.sptyp == 6) {
                        if (!this.pgen) {
                            n = (int)(10000.0 * Math.random());
                            if (this.fgen != 0) {
                                n = this.fgen;
                                this.fgen = 0;
                            }
                            this.bco[66] = new ContO(n, (int)this.pwd, (int)this.phd, this.m, this.t, 0, 0, 0);
                            this.bco[66].srz = n;
                            this.bco[66].srx = (int)this.pwd;
                            this.bco[66].sry = (int)this.phd;
                            this.pgen = true;
                            this.seq = 3;
                        }
                        this.sp = 66;
                        this.rot = 0;
                    } else if (this.pgen) {
                        this.pgen = false;
                        this.pwd = 2L + Math.round(Math.random() * 4.0);
                        this.phd = 2L + Math.round(Math.random() * 4.0);
                    }
                    if (this.sp == 30 || this.sp == 31 || this.sp == 32 || this.sp == 54) {
                        if (this.rot == -90) {
                            this.rot = 90;
                        }
                        if (this.rot == 180) {
                            this.rot = 0;
                        }
                    }
                    this.adrot = 0;
                    if (this.sp == 2) {
                        this.adrot = -30;
                    }
                    if (this.sp == 3) {
                        this.adrot = 30;
                    }
                    if (this.sp == 15) {
                        this.adrot = 90;
                    }
                    if (this.sp == 20) {
                        this.adrot = 180;
                    }
                    if (this.sp == 26) {
                        this.adrot = 90;
                    }
                    this.rd.setColor(new Color(200, 200, 200));
                    this.rd.fillRect(248, 63, 514, 454);
                    this.m.trk = 2;
                    this.m.zy = 90;
                    this.m.xz = 0;
                    this.m.iw = 248;
                    this.m.w = 762;
                    this.m.ih = 63;
                    this.m.h = 517;
                    this.m.cx = 505;
                    this.m.cy = 290;
                    this.m.x = this.sx - this.m.cx;
                    this.m.z = this.sz - this.m.cz;
                    this.m.y = this.sy;
                    n = 0;
                    int[] nArray = new int[200];
                    for (n3 = 0; n3 < this.nob; ++n3) {
                        if (this.co[n3].dist != 0) {
                            nArray[n] = n3;
                            ++n;
                            continue;
                        }
                        this.co[n3].d(this.rd);
                    }
                    int[] nArray2 = new int[n];
                    for (n2 = 0; n2 < n; ++n2) {
                        nArray2[n2] = 0;
                    }
                    for (n2 = 0; n2 < n; ++n2) {
                        for (n11 = n2 + 1; n11 < n; ++n11) {
                            if (this.co[nArray[n2]].dist != this.co[nArray[n11]].dist) {
                                if (this.co[nArray[n2]].dist < this.co[nArray[n11]].dist) {
                                    int n12 = n2;
                                    nArray2[n12] = nArray2[n12] + 1;
                                    continue;
                                }
                                int n13 = n11;
                                nArray2[n13] = nArray2[n13] + 1;
                                continue;
                            }
                            if (n11 > n2) {
                                int n14 = n2;
                                nArray2[n14] = nArray2[n14] + 1;
                                continue;
                            }
                            int n15 = n11;
                            nArray2[n15] = nArray2[n15] + 1;
                        }
                    }
                    for (n2 = 0; n2 < n; ++n2) {
                        for (n11 = 0; n11 < n; ++n11) {
                            Graphics2D graphics2D;
                            if (nArray2[n11] != n2) continue;
                            if (nArray[n11] == this.hi) {
                                this.m.trk = 3;
                            }
                            if (nArray[n11] == this.chi && !this.co[nArray[n11]].errd) {
                                n10 = this.m.cx + (int)((float)(this.co[nArray[n11]].x - this.m.x - this.m.cx) * this.m.cos(this.m.xz) - (float)(this.co[nArray[n11]].z - this.m.z - this.m.cz) * this.m.sin(this.m.xz));
                                n9 = this.m.cz + (int)((float)(this.co[nArray[n11]].x - this.m.x - this.m.cx) * this.m.sin(this.m.xz) + (float)(this.co[nArray[n11]].z - this.m.z - this.m.cz) * this.m.cos(this.m.xz));
                                n8 = this.m.cy + (int)((float)(this.co[nArray[n11]].y - this.m.y - this.m.cy) * this.m.cos(this.m.zy) - (float)(n9 - this.m.cz) * this.m.sin(this.m.zy));
                                n7 = this.m.cz + (int)((float)(this.co[nArray[n11]].y - this.m.y - this.m.cy) * this.m.sin(this.m.zy) + (float)(n9 - this.m.cz) * this.m.cos(this.m.zy));
                                n6 = 1000000 / Math.abs(this.sy);
                                graphics2D = this.rd;
                                graphics2D.setComposite(AlphaComposite.getInstance(3, 0.7f));
                                this.rd.setColor(new Color(0, 164, 255));
                                this.rd.fillOval(this.xs(n10, n7) - n6 / 2, this.ys(n8, n7) - n6 / 2, n6, n6);
                                graphics2D.setComposite(AlphaComposite.getInstance(3, 1.0f));
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("NO# " + (this.arrcnt + 1) + "", this.xs(n10, n7) - this.ftm.stringWidth("NO# " + (this.arrcnt + 1) + "") / 2, this.ys(n8, n7) - n6 / 2);
                            }
                            if (this.arrng && (this.co[nArray[n11]].colok == 30 || this.co[nArray[n11]].colok == 32 || this.co[nArray[n11]].colok == 54) && this.co[nArray[n11]].errd) {
                                n10 = this.m.cx + (int)((float)(this.co[nArray[n11]].x - this.m.x - this.m.cx) * this.m.cos(this.m.xz) - (float)(this.co[nArray[n11]].z - this.m.z - this.m.cz) * this.m.sin(this.m.xz));
                                n9 = this.m.cz + (int)((float)(this.co[nArray[n11]].x - this.m.x - this.m.cx) * this.m.sin(this.m.xz) + (float)(this.co[nArray[n11]].z - this.m.z - this.m.cz) * this.m.cos(this.m.xz));
                                n8 = this.m.cy + (int)((float)(this.co[nArray[n11]].y - this.m.y - this.m.cy) * this.m.cos(this.m.zy) - (float)(n9 - this.m.cz) * this.m.sin(this.m.zy));
                                n7 = this.m.cz + (int)((float)(this.co[nArray[n11]].y - this.m.y - this.m.cy) * this.m.sin(this.m.zy) + (float)(n9 - this.m.cz) * this.m.cos(this.m.zy));
                                n6 = 1000000 / Math.abs(this.sy);
                                graphics2D = this.rd;
                                graphics2D.setComposite(AlphaComposite.getInstance(3, 0.5f));
                                this.rd.setColor(new Color(255, 128, 0));
                                this.rd.fillOval(this.xs(n10, n7) - n6 / 2, this.ys(n8, n7) - n6 / 2, n6, n6);
                                graphics2D.setComposite(AlphaComposite.getInstance(3, 1.0f));
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.setFont(new Font("Arial", 1, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.drawString("NO# " + this.co[nArray[n11]].wh + "", this.xs(n10, n7) - this.ftm.stringWidth("NO# " + this.co[nArray[n11]].wh + "") / 2, this.ys(n8, n7) - n6 / 2);
                            }
                            this.co[nArray[n11]].d(this.rd);
                            if (this.m.trk != 3) continue;
                            this.m.trk = 2;
                        }
                    }
                    if (this.xm > 248 && this.xm < 762 && this.ym > 63 && this.ym < 517) {
                        if (!this.epart && !this.arrng) {
                            int n16;
                            this.bco[this.sp].x = (this.xm - 505) * (Math.abs(this.sy) / this.m.focus_point) + this.sx;
                            this.bco[this.sp].z = (290 - this.ym) * (Math.abs(this.sy) / this.m.focus_point) + this.sz;
                            this.bco[this.sp].y = this.m.ground - this.bco[this.sp].grat;
                            this.bco[this.sp].xz = this.rot + this.adrot;
                            n2 = 200;
                            n11 = 0;
                            n10 = 0;
                            int[] nArray3 = new int[]{this.bco[this.sp].x + this.atp[this.sp][0], this.bco[this.sp].x + this.atp[this.sp][2]};
                            int[] nArray4 = new int[]{this.bco[this.sp].z + this.atp[this.sp][1], this.bco[this.sp].z + this.atp[this.sp][3]};
                            this.rot(nArray3, nArray4, this.bco[this.sp].x, this.bco[this.sp].z, this.rot, 2);
                            n7 = 0;
                            this.onfly = false;
                            n6 = 500;
                            for (n16 = 0; n16 < this.nob; ++n16) {
                                int n17;
                                int n18;
                                int n19;
                                int[] nArray5 = new int[]{this.co[n16].x + this.atp[this.co[n16].colok][0], this.co[n16].x + this.atp[this.co[n16].colok][2]};
                                int[] nArray6 = new int[]{this.co[n16].z + this.atp[this.co[n16].colok][1], this.co[n16].z + this.atp[this.co[n16].colok][3]};
                                int n20 = this.co[n16].roofat;
                                if (this.co[n16].colok == 2) {
                                    n20 += 30;
                                }
                                if (this.co[n16].colok == 3) {
                                    n20 -= 30;
                                }
                                if (this.co[n16].colok == 15) {
                                    n20 -= 90;
                                }
                                if (this.co[n16].colok == 20) {
                                    n20 -= 180;
                                }
                                if (this.co[n16].colok == 26) {
                                    n20 -= 90;
                                }
                                this.rot(nArray5, nArray6, this.co[n16].x, this.co[n16].z, n20, 2);
                                if (this.sp <= 54) {
                                    n19 = this.py(nArray5[0], nArray3[0], nArray6[0], nArray4[0]);
                                    if (n19 < n2 && n19 != 0) {
                                        n2 = n19;
                                        n11 = nArray5[0] - nArray3[0];
                                        n10 = nArray6[0] - nArray4[0];
                                    }
                                    if ((n19 = this.py(nArray5[1], nArray3[0], nArray6[1], nArray4[0])) < n2 && n19 != 0) {
                                        n2 = n19;
                                        n11 = nArray5[1] - nArray3[0];
                                        n10 = nArray6[1] - nArray4[0];
                                    }
                                    if ((n19 = this.py(nArray5[1], nArray3[1], nArray6[1], nArray4[1])) < n2 && n19 != 0) {
                                        n2 = n19;
                                        n11 = nArray5[1] - nArray3[1];
                                        n10 = nArray6[1] - nArray4[1];
                                    }
                                    if ((n19 = this.py(nArray5[0], nArray3[1], nArray6[0], nArray4[1])) < n2 && n19 != 0) {
                                        n2 = n19;
                                        n11 = nArray5[0] - nArray3[1];
                                        n10 = nArray6[0] - nArray4[1];
                                    }
                                }
                                if (this.sptyp == 3 && this.py(nArray5[0], nArray3[0], nArray6[0], nArray4[0]) != 0 && this.py(nArray5[1], nArray3[0], nArray6[1], nArray4[0]) != 0) {
                                    for (n19 = 0; n19 < this.rcheckp.length; ++n19) {
                                        if (this.co[n16].colok != this.rcheckp[n19]) continue;
                                        if (this.py(nArray5[0], nArray3[0], nArray6[0], nArray4[0]) <= n7 || n7 == 0) {
                                            n7 = this.py(nArray5[0], nArray3[0], nArray6[0], nArray4[0]);
                                            this.onoff = false;
                                        }
                                        if (this.py(nArray5[1], nArray3[0], nArray6[1], nArray4[0]) > n7) continue;
                                        n7 = this.py(nArray5[1], nArray3[0], nArray6[1], nArray4[0]);
                                        this.onoff = false;
                                    }
                                    for (n19 = 0; n19 < this.ocheckp.length; ++n19) {
                                        if (this.co[n16].colok != this.ocheckp[n19]) continue;
                                        if (this.py(nArray5[0], nArray3[0], nArray6[0], nArray4[0]) <= n7 || n7 == 0) {
                                            n7 = this.py(nArray5[0], nArray3[0], nArray6[0], nArray4[0]);
                                            this.onoff = true;
                                        }
                                        if (this.py(nArray5[1], nArray3[0], nArray6[1], nArray4[0]) > n7) continue;
                                        n7 = this.py(nArray5[1], nArray3[0], nArray6[1], nArray4[0]);
                                        this.onoff = true;
                                    }
                                }
                                if (this.sp > 12 && this.sp < 33 || this.sp == 35 || this.sp == 36 || this.sp >= 39 && this.sp <= 54) {
                                    if (!(this.rot != 0 && this.rot != 180 && this.sp != 26 && this.sp != 15 || n20 != 0 && n20 != 180 && this.sp != 26 && this.sp != 15)) {
                                        if (Math.abs(nArray5[0] - nArray3[0]) < 200) {
                                            n11 = nArray5[0] - nArray3[0];
                                        }
                                        if (Math.abs(nArray5[0] - nArray3[1]) < 200) {
                                            n11 = nArray5[0] - nArray3[1];
                                        }
                                        if (Math.abs(nArray5[1] - nArray3[1]) < 200) {
                                            n11 = nArray5[1] - nArray3[1];
                                        }
                                        if (Math.abs(nArray5[1] - nArray3[0]) < 200) {
                                            n11 = nArray5[1] - nArray3[0];
                                        }
                                    }
                                    if (!(this.rot != 90 && this.rot != -90 && this.sp != 26 && this.sp != 15 || n20 != 90 && n20 != -90 && this.sp != 26 && this.sp != 15)) {
                                        if (Math.abs(nArray6[0] - nArray4[0]) < 200) {
                                            n10 = nArray6[0] - nArray4[0];
                                        }
                                        if (Math.abs(nArray6[0] - nArray4[1]) < 200) {
                                            n10 = nArray6[0] - nArray4[1];
                                        }
                                        if (Math.abs(nArray6[1] - nArray4[1]) < 200) {
                                            n10 = nArray6[1] - nArray4[1];
                                        }
                                        if (Math.abs(nArray6[1] - nArray4[0]) < 200) {
                                            n10 = nArray6[1] - nArray4[0];
                                        }
                                    }
                                }
                                if (this.sptyp != 3 || this.co[n16].colok < 46 || this.co[n16].colok > 51) continue;
                                int[] nArray7 = new int[]{2, 3, 5, 2, 3, 3};
                                if ((Math.abs(this.co[n16].roofat) == 180 || this.co[n16].roofat == 0) && this.rot == 0 && Math.abs(this.bco[this.sp].x - this.co[n16].x) < 500 && Math.abs(this.bco[this.sp].z - this.co[n16].z) < 3000) {
                                    for (n18 = 0; n18 < nArray7[this.co[n16].colok - 46]; ++n18) {
                                        for (n17 = 0; n17 < this.co[n16].p[n18].n; ++n17) {
                                            if (this.py(this.bco[this.sp].x, this.co[n16].x, this.bco[this.sp].z, this.co[n16].z + this.co[n16].p[n18].oz[n17]) >= n6) continue;
                                            n6 = this.py(this.bco[this.sp].x, this.co[n16].x, this.bco[this.sp].z, this.co[n16].z + this.co[n16].p[n18].oz[n17]);
                                            this.flyh = this.co[n16].p[n18].oy[n17] - 28 + this.m.ground;
                                            n11 = this.co[n16].x - this.bco[this.sp].x;
                                            n10 = this.co[n16].z + this.co[n16].p[n18].oz[n17] - this.bco[this.sp].z;
                                            this.onfly = true;
                                        }
                                    }
                                }
                                if (Math.abs(this.co[n16].roofat) != 90 || this.rot != 90 || Math.abs(this.bco[this.sp].z - this.co[n16].z) >= 500 || Math.abs(this.bco[this.sp].x - this.co[n16].x) >= 3000) continue;
                                for (n18 = 0; n18 < nArray7[this.co[n16].colok - 46]; ++n18) {
                                    for (n17 = 0; n17 < this.co[n16].p[n18].n; ++n17) {
                                        if (this.py(this.bco[this.sp].z, this.co[n16].z, this.bco[this.sp].x, this.co[n16].x + this.co[n16].p[n18].ox[n17]) >= n6) continue;
                                        n6 = this.py(this.bco[this.sp].z, this.co[n16].z, this.bco[this.sp].x, this.co[n16].x + this.co[n16].p[n18].ox[n17]);
                                        this.flyh = this.co[n16].p[n18].oy[n17] - 28 + this.m.ground;
                                        n10 = this.co[n16].z - this.bco[this.sp].z;
                                        n11 = this.co[n16].x + this.co[n16].p[n18].ox[n17] - this.bco[this.sp].x;
                                        this.onfly = true;
                                    }
                                }
                            }
                            this.bco[this.sp].x += n11;
                            this.bco[this.sp].z += n10;
                            n16 = this.bco[this.sp].xy;
                            int n21 = this.bco[this.sp].zy;
                            if (this.sp == 31) {
                                this.bco[this.sp].y = -this.hf;
                                if (this.bco[this.sp].y > -500) {
                                    this.bco[this.sp].y = -500;
                                }
                            } else {
                                this.bco[this.sp].xy = 0;
                            }
                            if (this.sp == 54) {
                                this.bco[this.sp].y = this.flyh;
                            }
                            this.bco[this.sp].zy = 0;
                            if (this.cntout == 0) {
                                if (this.mouseon == -1) {
                                    this.bco[this.sp].d(this.rd);
                                    if (!this.setcur) {
                                        this.setCursor(new Cursor(13));
                                        this.setcur = true;
                                    }
                                    if (this.mouses == -1) {
                                        if (this.nundo < 5000) {
                                            this.undos[this.nundo] = this.bstage;
                                            ++this.nundo;
                                        }
                                        if (this.bco[this.sp].xz == 270) {
                                            this.bco[this.sp].xz = -90;
                                        }
                                        if (this.bco[this.sp].xz == 360) {
                                            this.bco[this.sp].xz = 0;
                                        }
                                        this.errd = 0;
                                        boolean bl = false;
                                        if (this.xnob < 601) {
                                            if (this.sp != 31 && this.sp != 54 && this.sp != 66) {
                                                try {
                                                    this.co[this.nob] = new ContO(this.bco[this.sp], this.bco[this.sp].x, this.m.ground - this.bco[this.sp].grat, this.bco[this.sp].z, this.bco[this.sp].xz);
                                                    this.co[this.nob].roofat = this.bco[this.sp].xz;
                                                    this.co[this.nob].colok = this.sp;
                                                    ++this.nob;
                                                }
                                                catch (Exception exception) {
                                                    this.errd = 1;
                                                }
                                            }
                                            if (this.sp == 31) {
                                                if (this.cp.fn < 5) {
                                                    this.co[this.nob] = new ContO(this.bco[this.sp], this.bco[this.sp].x, this.bco[this.sp].y, this.bco[this.sp].z, this.bco[this.sp].xz);
                                                    this.co[this.nob].roofat = this.bco[this.sp].xz;
                                                    this.co[this.nob].colok = this.sp;
                                                    ++this.nob;
                                                    this.fixh.setText("" + Math.abs(this.bco[this.sp].y) + "");
                                                } else {
                                                    this.errd = 5;
                                                }
                                            }
                                            if (this.sp == 54) {
                                                try {
                                                    this.co[this.nob] = new ContO(this.bco[this.sp], this.bco[this.sp].x, this.bco[this.sp].y, this.bco[this.sp].z, this.bco[this.sp].xz);
                                                    this.co[this.nob].roofat = this.bco[this.sp].xz;
                                                    this.co[this.nob].colok = this.sp;
                                                    ++this.nob;
                                                }
                                                catch (Exception exception) {
                                                    this.errd = 1;
                                                }
                                            }
                                            if (this.sp == 66) {
                                                this.co[this.nob] = new ContO(this.bco[66].srz, this.bco[66].srx, this.bco[66].sry, this.m, this.t, this.bco[66].x, this.bco[66].z, this.bco[this.sp].y);
                                                this.co[this.nob].srz = this.bco[66].srz;
                                                this.co[this.nob].srx = this.bco[66].srx;
                                                this.co[this.nob].sry = this.bco[66].sry;
                                                this.co[this.nob].colok = this.sp;
                                                ++this.nob;
                                            }
                                        } else {
                                            this.errd = 4;
                                        }
                                        if (this.errd == 0) {
                                            this.sortstage();
                                            this.readstage(0);
                                            bl = true;
                                            if (this.sp == 66) {
                                                this.pgen = false;
                                            }
                                            if (this.sp == 52 || this.sp == 53 || this.sp >= 55 && this.sp <= 65) {
                                                this.seq = 3;
                                                this.bco[this.sp].xy = 0;
                                                this.bco[this.sp].zy = 0;
                                                boolean bl2 = false;
                                                if (this.rot == 0 && !bl2) {
                                                    this.rot = 90;
                                                    bl2 = true;
                                                }
                                                if (this.rot == 90 && !bl2) {
                                                    this.rot = 180;
                                                    bl2 = true;
                                                }
                                                if (this.rot == 180 && !bl2) {
                                                    this.rot = -90;
                                                    bl2 = true;
                                                }
                                                if (this.rot == -90 && !bl2) {
                                                    this.rot = 0;
                                                    bl2 = true;
                                                }
                                            }
                                        }
                                        if (this.errd != 0) {
                                            JOptionPane.showMessageDialog(null, "Error!  Unable to place part!\nReason:\n" + this.errlo[this.errd - 1] + "\n\n", "Stage Maker", 0);
                                            if (bl) {
                                                --this.nundo;
                                                this.bstage = this.undos[this.nundo];
                                                this.readstage(0);
                                            }
                                        }
                                        this.lxm = this.bco[this.sp].x;
                                        this.lym = this.bco[this.sp].z;
                                        this.cntout = 10;
                                    }
                                }
                            } else {
                                if (this.lxm != this.bco[this.sp].x && this.lxm != this.bco[this.sp].z) {
                                    --this.cntout;
                                }
                                if (this.setcur) {
                                    this.setCursor(new Cursor(0));
                                    this.setcur = false;
                                }
                            }
                            this.bco[this.sp].xy = n16;
                            this.bco[this.sp].zy = n21;
                        } else {
                            if (this.epart) {
                                if (this.esp == -1 && !this.overcan) {
                                    this.hi = -1;
                                    n2 = 0;
                                    for (n11 = 0; n11 < this.nob; ++n11) {
                                        n10 = this.m.cx + (int)((float)(this.co[n11].x - this.m.x - this.m.cx) * this.m.cos(this.m.xz) - (float)(this.co[n11].z - this.m.z - this.m.cz) * this.m.sin(this.m.xz));
                                        n9 = this.m.cz + (int)((float)(this.co[n11].x - this.m.x - this.m.cx) * this.m.sin(this.m.xz) + (float)(this.co[n11].z - this.m.z - this.m.cz) * this.m.cos(this.m.xz));
                                        n8 = this.m.cy + (int)((float)(this.co[n11].y - this.m.y - this.m.cy) * this.m.cos(this.m.zy) - (float)(n9 - this.m.cz) * this.m.sin(this.m.zy));
                                        n7 = this.m.cz + (int)((float)(this.co[n11].y - this.m.y - this.m.cy) * this.m.sin(this.m.zy) + (float)(n9 - this.m.cz) * this.m.cos(this.m.zy));
                                        if (this.xm <= this.xs(n10 - this.co[n11].maxR, n7) || this.xm >= this.xs(n10 + this.co[n11].maxR, n7) || this.ym <= this.ys(n8 - this.co[n11].maxR, n7) || this.ym >= this.ys(n8 + this.co[n11].maxR, n7) || this.co[n11].colok == 37 || this.co[n11].colok == 38) continue;
                                        if (this.hi == -1) {
                                            this.hi = n11;
                                            n2 = this.py(this.xm, this.xs(n10, n7), this.ym, this.ys(n8, n7));
                                            continue;
                                        }
                                        if (this.py(this.xm, this.xs(n10, n7), this.ym, this.ys(n8, n7)) > n2) continue;
                                        this.hi = n11;
                                        n2 = this.py(this.xm, this.xs(n10, n7), this.ym, this.ys(n8, n7));
                                    }
                                    if (this.hi != -1) {
                                        if (!this.setcur) {
                                            this.setCursor(new Cursor(13));
                                            this.setcur = true;
                                        }
                                        if (this.mouses == -1) {
                                            this.esp = this.hi;
                                            this.mouses = 0;
                                        }
                                    } else if (this.setcur) {
                                        this.setCursor(new Cursor(0));
                                        this.setcur = false;
                                    }
                                } else if (this.setcur) {
                                    this.setCursor(new Cursor(0));
                                    this.setcur = false;
                                }
                            }
                            if (this.arrng) {
                                this.chi = -1;
                                n2 = 5000;
                                for (n11 = 0; n11 < this.nob; ++n11) {
                                    if (this.co[n11].colok != 30 && this.co[n11].colok != 32 && this.co[n11].colok != 54 || this.co[n11].errd) continue;
                                    n10 = this.m.cx + (int)((float)(this.co[n11].x - this.m.x - this.m.cx) * this.m.cos(this.m.xz) - (float)(this.co[n11].z - this.m.z - this.m.cz) * this.m.sin(this.m.xz));
                                    n9 = this.m.cz + (int)((float)(this.co[n11].x - this.m.x - this.m.cx) * this.m.sin(this.m.xz) + (float)(this.co[n11].z - this.m.z - this.m.cz) * this.m.cos(this.m.xz));
                                    n8 = this.m.cy + (int)((float)(this.co[n11].y - this.m.y - this.m.cy) * this.m.cos(this.m.zy) - (float)(n9 - this.m.cz) * this.m.sin(this.m.zy));
                                    n7 = this.m.cz + (int)((float)(this.co[n11].y - this.m.y - this.m.cy) * this.m.sin(this.m.zy) + (float)(n9 - this.m.cz) * this.m.cos(this.m.zy));
                                    if (this.xm <= this.xs(n10 - this.co[n11].maxR, n7) || this.xm >= this.xs(n10 + this.co[n11].maxR, n7) || this.ym <= this.ys(n8 - this.co[n11].maxR, n7) || this.ym >= this.ys(n8 + this.co[n11].maxR, n7) || this.py(this.xm, this.xs(n10, n7), this.ym, this.ys(n8, n7)) > n2) continue;
                                    this.chi = n11;
                                    n2 = this.py(this.xm, this.xs(n10, n7), this.ym, this.ys(n8, n7));
                                }
                                if (this.chi != -1) {
                                    if (!this.setcur) {
                                        this.setCursor(new Cursor(13));
                                        this.setcur = true;
                                    }
                                    if (this.mouses == -1) {
                                        this.co[this.chi].wh = ++this.arrcnt;
                                        this.co[this.chi].errd = true;
                                        this.mouses = 0;
                                    }
                                } else if (this.setcur) {
                                    this.setCursor(new Cursor(0));
                                    this.setcur = false;
                                }
                            }
                        }
                    } else if (this.setcur) {
                        this.setCursor(new Cursor(0));
                        this.setcur = false;
                    }
                    if (this.epart && this.esp != -1) {
                        if (this.co[this.esp].dist != 0) {
                            this.m.cx = 505;
                            this.m.cy = 290;
                            this.m.x = this.sx - this.m.cx;
                            this.m.z = this.sz - this.m.cz;
                            this.m.y = this.sy;
                            n2 = this.m.cx + (int)((float)(this.co[this.esp].x - this.m.x - this.m.cx) * this.m.cos(this.m.xz) - (float)(this.co[this.esp].z - this.m.z - this.m.cz) * this.m.sin(this.m.xz));
                            n11 = this.m.cz + (int)((float)(this.co[this.esp].x - this.m.x - this.m.cx) * this.m.sin(this.m.xz) + (float)(this.co[this.esp].z - this.m.z - this.m.cz) * this.m.cos(this.m.xz));
                            n10 = this.m.cy + (int)((float)(this.co[this.esp].y - this.m.y - this.m.cy) * this.m.cos(this.m.zy) - (float)(n11 - this.m.cz) * this.m.sin(this.m.zy));
                            int n22 = this.m.cz + (int)((float)(this.co[this.esp].y - this.m.y - this.m.cy) * this.m.sin(this.m.zy) + (float)(n11 - this.m.cz) * this.m.cos(this.m.zy));
                            int n23 = this.xs(n2, n22);
                            n7 = this.ys(n10, n22);
                            this.rd.setColor(new Color(225, 225, 225));
                            this.rd.fillRect(n23, n7, 90, 88);
                            this.rd.setColor(new Color(138, 147, 160));
                            this.rd.drawRect(n23, n7, 90, 88);
                            if (this.button("   Edit   ", n23 + 45, n7 + 22, 3, false)) {
                                this.copyesp(true);
                                this.removesp();
                                this.lxm = 0;
                                this.lym = 0;
                                this.cntout = 2;
                                this.epart = false;
                            }
                            if (this.button(" Remove ", n23 + 45, n7 + 49, 3, false)) {
                                this.removesp();
                                this.esp = -1;
                                this.mouses = 0;
                            }
                            if (this.button("  Copy  ", n23 + 45, n7 + 76, 3, false)) {
                                this.copyesp(false);
                                this.lxm = 0;
                                this.lym = 0;
                                this.cntout = 2;
                                this.epart = false;
                            }
                            this.rd.setColor(new Color(255, 0, 0));
                            this.rd.drawString("x", n23 + 82, n7 - 2);
                            if (this.xm > 248 && this.xm < 762 && this.ym > 63 && this.ym < 517 && this.mouses == 1 && (this.xm < n23 || this.xm > n23 + 90 || this.ym < n7 || this.ym > n7 + 88)) {
                                this.esp = -1;
                                this.mouses = 0;
                            }
                        } else {
                            this.esp = -1;
                        }
                    }
                    this.rd.setColor(new Color(225, 225, 225));
                    this.rd.fillRect(248, 25, 514, 38);
                    this.rd.fillRect(0, 25, 248, 530);
                    this.rd.fillRect(248, 517, 514, 38);
                    this.rd.fillRect(762, 25, 38, 530);
                    if (this.sptyp == 6) {
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.rd.drawString("Radius:", 11, 97);
                        this.rd.drawString("Height:", 14, 117);
                        n2 = 0;
                        if (this.xm > 57 && this.xm < 204 && this.ym > 90 && this.ym < 99) {
                            n2 = 1;
                        }
                        this.rd.setColor(new Color(136, 148, 170));
                        if (n2 != 0 || this.mouseon == 1) {
                            this.rd.drawRect(57, 90, 147, 8);
                            this.rd.setColor(new Color(0, 0, 0));
                        }
                        this.rd.drawLine(57, 94, 204, 94);
                        if (this.mouseon == 1) {
                            this.pwd = (float)(this.xm - 57) / 36.75f + 2.0f;
                            if (this.pwd < 2.0f) {
                                this.pwd = 2.0f;
                            }
                            if (this.pwd > 6.0f) {
                                this.pwd = 6.0f;
                            }
                        }
                        this.rd.drawRect((int)(57.0f + (this.pwd - 2.0f) * 36.75f), 90, 2, 8);
                        n11 = 0;
                        if (this.xm > 57 && this.xm < 204 && this.ym > 110 && this.ym < 119) {
                            n11 = 1;
                        }
                        this.rd.setColor(new Color(136, 148, 170));
                        if (n11 != 0 || this.mouseon == 2) {
                            this.rd.drawRect(57, 110, 147, 8);
                            this.rd.setColor(new Color(0, 0, 0));
                        }
                        this.rd.drawLine(57, 114, 204, 114);
                        if (this.mouseon == 2) {
                            this.phd = (float)(this.xm - 57) / 36.75f + 2.0f;
                            if (this.phd < 2.0f) {
                                this.phd = 2.0f;
                            }
                            if (this.phd > 6.0f) {
                                this.phd = 6.0f;
                            }
                        }
                        this.rd.drawRect((int)(57.0f + (this.phd - 2.0f) * 36.75f), 110, 2, 8);
                        if (this.mouses == 1) {
                            if (n2 != 0) {
                                this.mouseon = 1;
                            }
                            if (n11 != 0) {
                                this.mouseon = 2;
                            }
                        } else {
                            if (this.mouseon == 1 || this.mouseon == 2) {
                                this.pgen = false;
                            }
                            this.mouseon = -1;
                        }
                    }
                    n2 = 0;
                    if (this.xm > 482 && this.xm < 529 && this.ym > 35 && this.ym < 61 || this.up) {
                        n2 = 1;
                        if (this.mouses == 1 || this.up) {
                            this.sz += 500;
                        }
                    }
                    this.rd.drawImage(this.su[n2], 482, 35, null);
                    n2 = 0;
                    if (this.xm > 482 && this.xm < 529 && this.ym > 519 && this.ym < 545 || this.down) {
                        n2 = 1;
                        if (this.mouses == 1 || this.down) {
                            this.sz -= 500;
                        }
                    }
                    this.rd.drawImage(this.sd[n2], 482, 519, null);
                    n2 = 0;
                    if (this.xm > 220 && this.xm < 246 && this.ym > 264 && this.ym < 311 || this.left) {
                        n2 = 1;
                        if (this.mouses == 1 || this.left) {
                            this.sx -= 500;
                        }
                    }
                    this.rd.drawImage(this.sl[n2], 220, 264, null);
                    n2 = 0;
                    if (this.xm > 764 && this.xm < 790 && this.ym > 264 && this.ym < 311 || this.right) {
                        n2 = 1;
                        if (this.mouses == 1 || this.right) {
                            this.sx += 500;
                        }
                    }
                    this.rd.drawImage(this.sr[n2], 764, 264, null);
                    n2 = 0;
                    if (this.xm > 616 && this.xm < 677 && this.ym > 30 && this.ym < 61 || this.zoomi) {
                        n2 = 1;
                        if (this.mouses == 1 || this.zoomi) {
                            this.sy += 500;
                            if (this.sy > -2500) {
                                this.sy = -2500;
                            }
                        }
                    }
                    this.rd.drawImage(this.zi[n2], 616, 30, null);
                    n2 = 0;
                    if (this.xm > 690 && this.xm < 751 && this.ym > 30 && this.ym < 61 || this.zoomo) {
                        n2 = 1;
                        if (this.mouses == 1 || this.zoomo) {
                            this.sy -= 500;
                            if (this.sy < -55000) {
                                this.sy = -55000;
                            }
                        }
                    }
                    this.rd.drawImage(this.zo[n2], 690, 30, null);
                    if ((this.epart || this.arrng) && this.sy < -36000) {
                        this.sy = -36000;
                    }
                    this.rd.setFont(new Font("Arial", 1, 11));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Part Selection", 11, 47);
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.ftm = this.rd.getFontMetrics();
                    this.ptyp.move(10, 50);
                    if (!this.ptyp.isShowing()) {
                        this.ptyp.show();
                        this.ptyp.select(this.sptyp);
                    }
                    if (this.sptyp != this.ptyp.getSelectedIndex()) {
                        this.sptyp = this.ptyp.getSelectedIndex();
                        if (this.sptyp == 0) {
                            this.partroads();
                            this.part.show();
                        }
                        if (this.sptyp == 1) {
                            this.partramps();
                            this.part.show();
                        }
                        if (this.sptyp == 2) {
                            this.partobst();
                            this.part.show();
                        }
                        if (this.sptyp == 5) {
                            this.partrees();
                            this.part.show();
                        }
                        this.spart = 0;
                        this.part.select(this.spart);
                        this.requestFocus();
                        this.fixh.setText("2000");
                        this.focuson = false;
                    }
                    this.part.move(10, 80);
                    this.part.setSize(200, 21);
                    if (this.sptyp == 0 || this.sptyp == 1 || this.sptyp == 2 || this.sptyp == 5) {
                        if (!this.part.isShowing()) {
                            this.part.show();
                            this.part.select(this.spart);
                        }
                    } else if (this.part.isShowing()) {
                        this.part.hide();
                    }
                    if (this.spart != this.part.getSelectedIndex()) {
                        this.spart = this.part.getSelectedIndex();
                        this.focuson = false;
                    }
                    if (this.sptyp == 3) {
                        this.rd.drawString("Checkpoint", 110 - this.ftm.stringWidth("Checkpoint") / 2, 120);
                    }
                    if (this.sptyp == 4) {
                        this.rd.drawString("Fixing Hoop", 110 - this.ftm.stringWidth("Fixing Hoop") / 2, 120);
                    }
                    if (this.lsp != this.sp) {
                        this.seq = 3;
                        this.bco[this.sp].xy = 0;
                        this.bco[this.sp].zy = 0;
                        this.lsp = this.sp;
                        this.epart = false;
                        this.arrng = false;
                    }
                    if (this.xm > 10 && this.xm < 210 && this.ym > 130 && this.ym < 334) {
                        if (this.seq >= 3) {
                            if (this.seq == 20 || !this.seqn) {
                                this.seq = 0;
                                this.bco[this.sp].xy = 0;
                                this.bco[this.sp].zy = 0;
                            } else {
                                ++this.seq;
                            }
                        }
                        this.seqn = true;
                        this.rd.setColor(new Color(210, 210, 210));
                    } else {
                        this.rd.setColor(new Color(200, 200, 200));
                        this.seqn = false;
                    }
                    this.rd.fillRect(10, 130, 200, 200);
                    if ((this.sp == 30 || this.sp == 32 || this.sp == 54) && this.button("  Rearrange Checkpoints  >  ", 110, 315, 2, true)) {
                        this.mouses = 0;
                        this.epart = false;
                        if (!this.arrng) {
                            this.arrcnt = 0;
                            for (n11 = 0; n11 < this.nob; ++n11) {
                                if (this.co[n11].colok != 30 && this.co[n11].colok != 32 && this.co[n11].colok != 54) continue;
                                this.co[n11].errd = false;
                            }
                            this.arrng = true;
                        } else {
                            this.arrng = false;
                        }
                    }
                    if (this.seqn && this.mouses == -1) {
                        if (this.sp != 66) {
                            n11 = 0;
                            if (this.rot == 0 && n11 == 0) {
                                this.rot = 90;
                                n11 = 1;
                            }
                            if (this.rot == 90 && n11 == 0) {
                                this.rot = 180;
                                n11 = 1;
                            }
                            if (this.rot == 180 && n11 == 0) {
                                this.rot = -90;
                                n11 = 1;
                            }
                            if (this.rot == -90 && n11 == 0) {
                                this.rot = 0;
                                n11 = 1;
                            }
                            if (this.sp == 30 || this.sp == 31 || this.sp == 32) {
                                if (this.rot == -90) {
                                    this.rot = 90;
                                }
                                if (this.rot == 180) {
                                    this.rot = 0;
                                }
                            }
                            this.seq = 5;
                            this.bco[this.sp].xy = 0;
                            this.bco[this.sp].zy = 0;
                            this.epart = false;
                            this.arrng = false;
                        } else {
                            this.pgen = false;
                            this.pwd = 2L + Math.round(Math.random() * 4.0);
                            this.phd = 2L + Math.round(Math.random() * 4.0);
                        }
                    }
                    if (this.sp == 31) {
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Height:", 62, 280);
                        this.movefield(this.fixh, 107, 266, 50, 20);
                        if (this.fixh.hasFocus()) {
                            this.focuson = false;
                        }
                        if (!this.fixh.isShowing()) {
                            this.fixh.show();
                        }
                        this.rd.setFont(new Font("Arial", 0, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("( Height off the ground... )", 110 - this.ftm.stringWidth("( Height off the ground... )") / 2, 300);
                        if (this.fixh.getText().equals("")) {
                            this.fixh.setText("0");
                            this.fixh.select(0, 0);
                        }
                        try {
                            this.hf = Integer.valueOf(this.fixh.getText());
                            if (this.hf > 8000) {
                                this.hf = 8000;
                                this.fixh.setText("8000");
                            }
                        }
                        catch (Exception exception) {
                            this.hf = 2000;
                            this.fixh.setText("2000");
                        }
                    } else if (this.fixh.isShowing()) {
                        this.fixh.hide();
                    }
                    this.m.trk = 2;
                    this.m.zy = 90;
                    this.m.xz = 0;
                    this.m.iw = 10;
                    this.m.w = 210;
                    this.m.ih = 130;
                    this.m.h = 330;
                    this.m.cx = 110;
                    this.m.cy = 230;
                    this.m.x = -110;
                    this.m.z = -230;
                    this.m.y = -15000;
                    if (this.sptyp == 1 && this.sp != 20 && this.sp != 21 && this.sp != 43 && this.sp != 45) {
                        this.m.y = -10000;
                    }
                    if (this.sptyp == 2 && this.sp != 41) {
                        this.m.y = -7600;
                    }
                    if (this.sptyp == 3 || this.sptyp == 4) {
                        this.m.y = -5000;
                    }
                    if (this.sptyp == 5) {
                        this.m.y = -3000;
                        this.m.z = 150;
                    }
                    if (this.sptyp == 6) {
                        this.m.y = -7600;
                    }
                    if (this.sp == 31) {
                        this.m.z = -500;
                        this.bco[this.sp].roted = this.rot != 0;
                    }
                    this.bco[this.sp].x = 0;
                    this.bco[this.sp].y = 0;
                    this.bco[this.sp].z = 0;
                    this.bco[this.sp].xz = this.rot + this.adrot;
                    this.bco[this.sp].d(this.rd);
                    int n24 = 1;
                    if (this.sptyp == 0 || this.sptyp == 1) {
                        if (this.sp != 26 && this.sp != 20) {
                            if (this.rot == -90 || this.rot == 0) {
                                n24 = -1;
                            }
                        } else {
                            if (this.sp == 26 && (this.rot == -90 || this.rot == 180)) {
                                n24 = -1;
                            }
                            if (this.sp == 20 && (this.rot == 90 || this.rot == 180)) {
                                n24 = -1;
                            }
                        }
                        if (this.seq == 2) {
                            this.bco[this.sp].xy -= 5 * n24;
                            if (this.bco[this.sp].xy == 0) {
                                this.seq = 3;
                            }
                        }
                        if (this.seq == 1) {
                            this.seq = 2;
                        }
                        if (this.seq == 0) {
                            this.bco[this.sp].xy += 5 * n24;
                            if (this.bco[this.sp].xy == 85 * n24) {
                                this.seq = 1;
                            }
                        }
                    }
                    if (this.sptyp == 2 || this.sptyp == 3 || this.sptyp == 4 || this.sptyp == 6) {
                        if (this.rot == -90 || this.rot == 180) {
                            n24 = -1;
                        }
                        if (this.seq == 2) {
                            this.bco[this.sp].zy += 5 * n24;
                            if (this.bco[this.sp].zy == 0) {
                                this.seq = 3;
                            }
                        }
                        if (this.seq == 1) {
                            this.seq = 2;
                        }
                        if (this.seq == 0) {
                            this.bco[this.sp].zy -= 5 * n24;
                            if (this.bco[this.sp].zy == -(85 * n24)) {
                                this.seq = 1;
                            }
                        }
                    }
                    if (this.sptyp == 5) {
                        if (this.rot == -90 || this.rot == 180) {
                            n24 = -1;
                        }
                        n10 = 0;
                        if (this.rot == -90 || this.rot == 90) {
                            n10 = 1;
                        }
                        if (n10 == 0) {
                            this.bco[this.sp].xy = 0;
                        } else {
                            this.bco[this.sp].zy = 0;
                        }
                        if (this.seq == 2) {
                            if (n10 == 0) {
                                this.bco[this.sp].zy += 5 * n24;
                                if (this.bco[this.sp].zy == 0) {
                                    this.seq = 3;
                                }
                            } else {
                                this.bco[this.sp].xy -= 5 * n24;
                                if (this.bco[this.sp].xy == 0) {
                                    this.seq = 3;
                                }
                            }
                        }
                        if (this.seq == 1) {
                            this.seq = 2;
                        }
                        if (this.seq == 0) {
                            if (n10 == 0) {
                                this.bco[this.sp].zy -= 5 * n24;
                                if (this.bco[this.sp].zy == -(85 * n24)) {
                                    this.seq = 1;
                                }
                            } else {
                                this.bco[this.sp].xy += 5 * n24;
                                if (this.bco[this.sp].xy == 85 * n24) {
                                    this.seq = 1;
                                }
                            }
                        }
                    }
                    if (this.sp != 66) {
                        if (this.button("  Rotate  ", 110, 348, 3, true)) {
                            n10 = 0;
                            if (this.rot == 0 && n10 == 0) {
                                this.rot = 90;
                                n10 = 1;
                            }
                            if (this.rot == 90 && n10 == 0) {
                                this.rot = 180;
                                n10 = 1;
                            }
                            if (this.rot == 180 && n10 == 0) {
                                this.rot = -90;
                                n10 = 1;
                            }
                            if (this.rot == -90 && n10 == 0) {
                                this.rot = 0;
                                n10 = 1;
                            }
                            if (this.sp == 30 || this.sp == 31 || this.sp == 32) {
                                if (this.rot == -90) {
                                    this.rot = 90;
                                }
                                if (this.rot == 180) {
                                    this.rot = 0;
                                }
                            }
                            this.seq = 3;
                            this.bco[this.sp].xy = 0;
                            this.bco[this.sp].zy = 0;
                            this.epart = false;
                            this.arrng = false;
                        }
                    } else if (this.button("  Generate New  ", 110, 348, 3, true)) {
                        this.pgen = false;
                        this.pwd = 2L + Math.round(Math.random() * 4.0);
                        this.phd = 2L + Math.round(Math.random() * 4.0);
                    }
                    if (this.button(">", 191, 348, 3, true) && (this.sptyp == 0 || this.sptyp == 1 || this.sptyp == 2 || this.sptyp == 5)) {
                        ++this.spart;
                        if (this.spart == this.part.getItemCount()) {
                            this.spart = 0;
                        }
                        this.part.select(this.spart);
                        this.epart = false;
                        this.arrng = false;
                    }
                    if (this.button("<", 28, 348, 3, true) && (this.sptyp == 0 || this.sptyp == 1 || this.sptyp == 2 || this.sptyp == 5)) {
                        --this.spart;
                        if (this.spart == -1) {
                            this.spart = this.part.getItemCount() - 1;
                        }
                        this.part.select(this.spart);
                        this.epart = false;
                        this.arrng = false;
                    }
                    if (this.button("   <  Undo   ", 204, 404, 0, true)) {
                        this.epart = false;
                        this.arrng = false;
                        if (this.nundo > 0) {
                            --this.nundo;
                            this.bstage = this.undos[this.nundo];
                            this.readstage(0);
                        }
                    }
                    if (this.button("   Remove / Edit  Part   ", 172, 454, 0, true)) {
                        this.epart = !this.epart;
                        this.arrng = false;
                        this.esp = -1;
                    }
                    if (this.button("   Go to >  Startline   ", 175, 504, 0, true)) {
                        this.sx = 0;
                        this.sz = 1500;
                    }
                    if (this.button(" About Part ", 164, 66, 3, false)) {
                        JOptionPane.showMessageDialog(null, this.discp[this.sp], "Stage Maker", 1);
                    }
                    if (this.button("  Keyboard Controls  ", 691, 536, 3, false)) {
                        JOptionPane.showMessageDialog(null, "Instead of clicking the triangular buttons around the Building Area to scroll, you can use:\n[ Keyboard Arrows ]\n\nYou can also zoom in and out using the following keys:\n[+] & [-]  or  [8] & [2]  or  [Enter] & [Backspace]\n\n", "Stage Maker", 1);
                    }
                    if (this.button("  Save  ", 280, 50, 0, false)) {
                        this.epart = false;
                        this.arrng = false;
                        this.savefile();
                    }
                    if (this.button("  Save & Preview  ", 380, 50, 0, false)) {
                        this.epart = false;
                        this.arrng = false;
                        this.savefile();
                        this.hidefields();
                        this.tab = 2;
                    }
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.setColor(new Color(0, 0, 0));
                    n10 = 0;
                    int n25 = 0;
                    n10 = n5 = (int)((float)this.xnob / 601.0f * 200.0f);
                    n7 = (int)((float)this.t.nt / 6700.0f * 200.0f);
                    if (n7 > n10) {
                        n10 = n7;
                        n25 = 1;
                    }
                    n6 = (int)((float)this.cp.n / 140.0f * 200.0f);
                    if (n6 > n10) {
                        n10 = n6;
                        n25 = 2;
                    }
                    int n26 = (int)((float)(this.m.nrw * this.m.ncl) / 16000.0f * 200.0f);
                    if (n26 > n10) {
                        n10 = n26;
                        n25 = 3;
                    }
                    if (n10 > 200) {
                        n10 = 200;
                    }
                    if (n10 <= 100) {
                        this.rd.setColor(new Color(100 + n10, 225, 30));
                    } else {
                        this.rd.setColor(new Color(200, 325 - n10, 30));
                    }
                    this.rd.fillRect(167, 531, n10, 9);
                    if (this.button("Memory Consumption :", 85, 540, 3, false)) {
                        JOptionPane.showMessageDialog(null, "Memory Consumption Details\n\nNumber of Parts:  " + n5 / 2 + " %\nPart's Details:  " + n7 / 2 + " %\nRoad Points:  " + n6 / 2 + " %\nStage Area:  " + n26 / 2 + " %\n \n", "Stage Maker", 1);
                    }
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawRect(167, 531, 200, 9);
                    String[] stringArray = new String[]{"Number of Parts", "Part's Details", "Road Points", "Stage Area"};
                    this.rd.drawString(stringArray[n25], 267 - this.ftm.stringWidth(stringArray[n25]) / 2, 540);
                    this.rd.drawString("" + n10 / 2 + " %  used", 375, 540);
                    if (this.overcan) {
                        this.overcan = false;
                    }
                    if (this.epart) {
                        if (this.esp == -1) {
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString("Click on any part to Edit >", 257, 454);
                            if (this.button(" Cancel ", 323, 474, 4, false)) {
                                this.epart = false;
                            }
                        }
                    } else {
                        if (this.hi != -1) {
                            this.hi = -1;
                        }
                        if (this.esp != -1) {
                            this.esp = -1;
                        }
                    }
                    if (this.arrng) {
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Click on Checkpoint NO# " + (this.arrcnt + 1) + "  >", 257, 80);
                        if (this.button(" Cancel ", 330, 100, 4, false)) {
                            this.arrng = false;
                        }
                        if (this.arrcnt == this.cp.nsp) {
                            this.sortstage();
                            JOptionPane.showMessageDialog(null, "Checkpoints Arranged!\nPress Save and Test Drive to check the new checkpoint order.\n", "Stage Maker", 1);
                            this.arrng = false;
                        }
                    } else if (this.chi != -1) {
                        this.chi = -1;
                    }
                }
                if (this.tab == 2) {
                    int n27;
                    if (this.tabed != this.tab) {
                        this.m.trk = 0;
                        this.readstage(1);
                        this.setCursor(new Cursor(0));
                        this.setcur = false;
                        this.vxz = 0;
                        this.vx = this.sx - 400;
                        this.vz = this.sz - this.m.cz - 8000;
                        this.vy = -1500;
                        this.dtabed = -1;
                    }
                    this.m.trk = 0;
                    this.m.zy = 6;
                    this.m.iw = 10;
                    this.m.w = 790;
                    this.m.ih = 35;
                    this.m.h = 445;
                    this.m.cx = 400;
                    this.m.cy = 215;
                    this.m.xz = this.vxz;
                    this.m.x = this.vx;
                    this.m.z = this.vz;
                    this.m.y = this.vy;
                    this.m.d(this.rd);
                    n = 0;
                    int[] nArray = new int[200];
                    for (n3 = 0; n3 < this.nob; ++n3) {
                        if (this.co[n3].dist != 0) {
                            nArray[n] = n3;
                            ++n;
                            continue;
                        }
                        this.co[n3].d(this.rd);
                    }
                    int[] nArray8 = new int[n];
                    for (n2 = 0; n2 < n; ++n2) {
                        nArray8[n2] = 0;
                    }
                    for (n2 = 0; n2 < n; ++n2) {
                        for (int i = n2 + 1; i < n; ++i) {
                            if (this.co[nArray[n2]].dist != this.co[nArray[i]].dist) {
                                if (this.co[nArray[n2]].dist < this.co[nArray[i]].dist) {
                                    int n28 = n2;
                                    nArray8[n28] = nArray8[n28] + 1;
                                    continue;
                                }
                                int n29 = i;
                                nArray8[n29] = nArray8[n29] + 1;
                                continue;
                            }
                            if (i > n2) {
                                int n30 = n2;
                                nArray8[n30] = nArray8[n30] + 1;
                                continue;
                            }
                            int n31 = i;
                            nArray8[n31] = nArray8[n31] + 1;
                        }
                    }
                    for (n2 = 0; n2 < n; ++n2) {
                        for (int i = 0; i < n; ++i) {
                            if (nArray8[i] != n2) continue;
                            if (nArray[i] == this.hi) {
                                this.m.trk = 3;
                            }
                            this.co[nArray[i]].d(this.rd);
                            if (this.m.trk != 3) continue;
                            this.m.trk = 2;
                        }
                    }
                    if (this.up) {
                        this.vz = (int)((float)this.vz + 500.0f * this.m.cos(this.m.xz));
                        this.vx = (int)((float)this.vx + 500.0f * this.m.sin(this.m.xz));
                    }
                    if (this.down) {
                        this.vz = (int)((float)this.vz - 500.0f * this.m.cos(this.m.xz));
                        this.vx = (int)((float)this.vx - 500.0f * this.m.sin(this.m.xz));
                    }
                    if (this.left) {
                        this.vxz -= 5;
                    }
                    if (this.right) {
                        this.vxz += 5;
                    }
                    if (this.zoomi) {
                        this.vy += 100;
                        if (this.vy > -500) {
                            this.vy = -500;
                        }
                    }
                    if (this.zoomo) {
                        this.vy -= 100;
                        if (this.vy < -5000) {
                            this.vy = -5000;
                        }
                    }
                    this.rd.setColor(new Color(225, 225, 225));
                    this.rd.fillRect(0, 25, 10, 525);
                    this.rd.fillRect(790, 25, 10, 525);
                    this.rd.fillRect(10, 25, 780, 10);
                    this.rd.fillRect(10, 445, 780, 105);
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    String[] stringArray = new String[]{"Controls", "Atmosphere", "Colors", "Scenery", "Laps", "Sound Track", "Test Drive"};
                    int[] nArray9 = new int[]{10, 10, 121, 111};
                    int[] nArray10 = new int[]{425, 445, 445, 425};
                    for (n27 = 0; n27 < 7; ++n27) {
                        this.rd.setColor(new Color(170, 170, 170));
                        if (this.xm > nArray9[0] && this.xm < nArray9[3] && this.ym > 425 && this.ym < 445) {
                            this.rd.setColor(new Color(190, 190, 190));
                        }
                        if (this.dtab == n27) {
                            this.rd.setColor(new Color(225, 225, 225));
                        }
                        this.rd.fillPolygon(nArray9, nArray10, 4);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString(stringArray[n27], n27 * 111 + 62 - this.ftm.stringWidth(stringArray[n27]) / 2, 439);
                        if (this.xm > nArray9[0] && this.xm < nArray9[3] && this.ym > 425 && this.ym < 445 && this.mouses == -1 && this.mouseon == -1) {
                            this.dtab = n27;
                        }
                        int n32 = 0;
                        while (n32 < 4) {
                            int n33 = n32++;
                            nArray9[n33] = nArray9[n33] + 111;
                        }
                    }
                    if (this.tabed == this.tab && this.dtab != this.dtabed) {
                        if (!this.ttstage.equals("")) {
                            this.tstage = this.ttstage;
                            this.ttstage = "";
                        }
                        this.readstage(1);
                        this.hidefields();
                    }
                    if (this.dtab == 0) {
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Use the [ Keyboard Arrows ] to navigate through the stage.", 20, 470);
                        this.rd.drawString("[Left] & [Right] arrows are for rotating.  [Up] & [Down] arrows are for moving forwards and backwards.", 20, 490);
                        this.rd.drawString("For moving vertically down and up use the following keys:  [+] & [-]  or  [8] & [2]  or  [Enter] & [Backspace]", 20, 520);
                    }
                    if (this.dtab == 2) {
                        if (this.dtabed != this.dtab) {
                            Color.RGBtoHSB(this.csky[0], this.csky[1], this.csky[2], this.hsb[0]);
                            Color.RGBtoHSB(this.cfade[0], this.cfade[1], this.cfade[2], this.hsb[1]);
                            Color.RGBtoHSB(this.cgrnd[0], this.cgrnd[1], this.cgrnd[2], this.hsb[2]);
                            for (n27 = 0; n27 < 3; ++n27) {
                                float f = this.hsb[n27][1];
                                this.hsb[n27][1] = this.hsb[n27][2];
                                this.hsb[n27][2] = f;
                            }
                            if (this.hsb[1][1] == (this.hsb[0][1] + this.hsb[2][1]) / 2.0f && this.hsb[1][0] == this.hsb[2][0] && this.hsb[1][2] == this.hsb[2][2]) {
                                this.pfog.setState(true);
                            } else {
                                this.pfog.setState(false);
                            }
                            this.ttstage = "";
                            this.mouseon = -1;
                        }
                        if (this.mouses != 1) {
                            if ((this.mouseon >= 6 || this.mouseon < 3) && this.mouseon != -1) {
                                if (this.ttstage.equals("")) {
                                    this.ttstage = this.tstage;
                                }
                                this.sortop();
                                this.readstage(1);
                            }
                            this.mouseon = -1;
                        }
                        String[] stringArray2 = new String[]{"Sky", "Dust / Fog", "Ground"};
                        for (int i = 0; i < 3; ++i) {
                            int n34;
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString(stringArray2[i], 107 + 195 * i - this.ftm.stringWidth(stringArray2[i]) / 2, 461);
                            for (n34 = 0; n34 < 150; ++n34) {
                                this.rd.setColor(Color.getHSBColor((float)((double)n34 * 0.006667), 1.0f, 1.0f));
                                this.rd.drawLine(32 + n34 + 195 * i, 467, 32 + n34 + 195 * i, 474);
                            }
                            for (n34 = 0; n34 < 150; ++n34) {
                                this.rd.setColor(Color.getHSBColor(0.0f, 0.0f, 0.5f + (float)n34 * 0.00333f));
                                this.rd.drawLine(32 + n34 + 195 * i, 483, 32 + n34 + 195 * i, 490);
                            }
                            for (n34 = 0; n34 < 150; ++n34) {
                                this.rd.setColor(Color.getHSBColor(this.hsb[i][0], 0.0f + (float)((double)n34 * 0.001667), this.hsb[i][1]));
                                this.rd.drawLine(32 + n34 + 195 * i, 499, 32 + n34 + 195 * i, 506);
                            }
                            for (n34 = 0; n34 < 3; ++n34) {
                                this.rd.setColor(new Color(0, 0, 0));
                                float f = this.hsb[i][n34] * 150.0f;
                                if (n34 == 1) {
                                    float f2 = 0.75f;
                                    if (i == 0) {
                                        f2 = 0.85f;
                                    }
                                    if (i == 1) {
                                        f2 = 0.8f;
                                    }
                                    f = (this.hsb[i][n34] - f2) / 0.001f;
                                }
                                if (n34 == 2) {
                                    f = this.hsb[i][n34] * 600.0f;
                                }
                                if (f < 0.0f) {
                                    f = 0.0f;
                                }
                                if (f > 150.0f) {
                                    f = 150.0f;
                                }
                                this.rd.drawLine((int)((float)(32 + 195 * i) + f), 467 + n34 * 16, (int)((float)(32 + 195 * i) + f), 474 + n34 * 16);
                                this.rd.drawLine((int)((float)(33 + 195 * i) + f), 467 + n34 * 16, (int)((float)(33 + 195 * i) + f), 474 + n34 * 16);
                                this.rd.fillRect((int)((float)(31 + 195 * i) + f), 475 + n34 * 16, 4, 2);
                                this.rd.drawLine((int)((float)(30 + 195 * i) + f), 477 + n34 * 16, (int)((float)(35 + 195 * i) + f), 477 + n34 * 16);
                                if (this.xm > 29 + 195 * i && this.xm < 185 + 195 * i && this.ym > 468 + n34 * 16 && this.ym < 477 + n34 * 16 && this.mouses == 1 && this.mouseon == -1) {
                                    this.mouseon = n34 + i * 3;
                                }
                                if (this.mouseon != n34 + i * 3) continue;
                                if (n34 == 0) {
                                    this.hsb[i][n34] = (float)(this.xm - (32 + 195 * i)) / 150.0f;
                                }
                                if (n34 == 1) {
                                    float f3 = 0.75f;
                                    if (i == 0) {
                                        f3 = 0.85f;
                                    }
                                    if (i == 1) {
                                        f3 = 0.8f;
                                    }
                                    this.hsb[i][n34] = f3 + (float)(this.xm - (32 + 195 * i)) * 0.001f;
                                    if (this.hsb[i][n34] < f3) {
                                        this.hsb[i][n34] = f3;
                                    }
                                    if (this.hsb[i][n34] > f3 + 0.15f) {
                                        this.hsb[i][n34] = f3 + 0.15f;
                                    }
                                }
                                if (n34 == 2) {
                                    this.hsb[i][n34] = (float)(this.xm - (32 + 195 * i)) / 600.0f;
                                    if ((double)this.hsb[i][n34] > 0.25) {
                                        this.hsb[i][n34] = 0.25f;
                                    }
                                }
                                if (this.hsb[i][n34] > 1.0f) {
                                    this.hsb[i][n34] = 1.0f;
                                }
                                if (!(this.hsb[i][n34] < 0.0f)) continue;
                                this.hsb[i][n34] = 0.0f;
                            }
                        }
                        this.movefield(this.pfog, 258, 511, 200, 23);
                        if (!this.pfog.isShowing()) {
                            this.pfog.show();
                        }
                        if (this.pfog.getState()) {
                            this.rd.setComposite(AlphaComposite.getInstance(3, 0.25f));
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.fillRect(215, 464, 175, 47);
                            this.rd.setComposite(AlphaComposite.getInstance(3, 1.0f));
                            this.hsb[1][1] = (this.hsb[0][1] + this.hsb[2][1]) / 2.0f;
                            this.hsb[1][0] = this.hsb[2][0];
                            this.hsb[1][2] = this.hsb[2][2];
                        }
                        Color color = Color.getHSBColor(this.hsb[0][0], this.hsb[0][2], this.hsb[0][1]);
                        this.m.setsky(color.getRed(), color.getGreen(), color.getBlue());
                        this.csky[0] = color.getRed();
                        this.csky[1] = color.getGreen();
                        this.csky[2] = color.getBlue();
                        color = Color.getHSBColor(this.hsb[1][0], this.hsb[1][2], this.hsb[1][1]);
                        this.m.setfade(color.getRed(), color.getGreen(), color.getBlue());
                        this.cfade[0] = color.getRed();
                        this.cfade[1] = color.getGreen();
                        this.cfade[2] = color.getBlue();
                        color = Color.getHSBColor(this.hsb[2][0], this.hsb[2][2], this.hsb[2][1]);
                        this.m.setgrnd(color.getRed(), color.getGreen(), color.getBlue());
                        this.cgrnd[0] = color.getRed();
                        this.cgrnd[1] = color.getGreen();
                        this.cgrnd[2] = color.getBlue();
                        if (this.button(" Reset ", 650, 510, 0, true)) {
                            if (!this.ttstage.equals("")) {
                                this.tstage = this.ttstage;
                                this.ttstage = "";
                            }
                            this.readstage(1);
                            this.dtabed = -2;
                        }
                        if (this.button("        Save        ", 737, 510, 0, true)) {
                            this.sortop();
                            this.ttstage = "";
                            this.savefile();
                        }
                    }
                    if (this.dtab == 3) {
                        int n35;
                        int n36;
                        if (this.dtabed != this.dtab) {
                            Color.RGBtoHSB(this.cldd[0], this.cldd[1], this.cldd[2], this.hsb[0]);
                            Color.RGBtoHSB(this.texture[0], this.texture[1], this.texture[2], this.hsb[1]);
                            this.mgen.setText("" + this.m.mgen + "");
                            this.mouseon = -1;
                            this.ttstage = "";
                        }
                        if (this.mouses != 1) {
                            if (this.mouseon == 0 || this.mouseon == 1 || this.mouseon == 2 || this.mouseon == 6) {
                                if (this.ttstage.equals("")) {
                                    this.ttstage = this.tstage;
                                }
                                this.sortop();
                                this.readstage(1);
                            }
                            this.mouseon = -1;
                        }
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Clouds", 32, 461);
                        for (n36 = 0; n36 < 150; ++n36) {
                            this.rd.setColor(Color.getHSBColor((float)n36 * 0.006667f, 1.0f, 1.0f));
                            this.rd.drawLine(32 + n36 + 0, 467, 32 + n36 + 0, 474);
                        }
                        for (n36 = 0; n36 < 150; ++n36) {
                            this.rd.setColor(Color.getHSBColor(0.0f, 0.0f, 0.75f + (float)n36 * 0.001667f));
                            this.rd.drawLine(32 + n36 + 0, 483, 32 + n36 + 0, 490);
                        }
                        for (n36 = 0; n36 < 150; ++n36) {
                            this.rd.setColor(Color.getHSBColor(this.hsb[0][0], (float)n36 * 0.003333f, this.hsb[0][2]));
                            this.rd.drawLine(32 + n36 + 0, 499, 32 + n36 + 0, 506);
                        }
                        this.rd.setFont(new Font("Arial", 0, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Blend:", 32, 529);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.fillRect(70, 522, 112, 2);
                        this.rd.fillRect(70, 528, 112, 2);
                        float f = 0.0f;
                        int n37 = 255;
                        for (n35 = 0; n35 < 112; ++n35) {
                            n37 = (int)(255.0f / (f + 1.0f));
                            if (n37 > 255) {
                                n37 = 255;
                            }
                            if (n37 < 0) {
                                n37 = 0;
                            }
                            f += 0.02f;
                            this.rd.setColor(new Color(n37, n37, n37));
                            this.rd.drawLine(70 + n35, 524, 70 + n35, 527);
                        }
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Height", 202 - this.ftm.stringWidth("Height") / 2, 461);
                        this.rd.drawLine(202, 467, 202, 530);
                        for (n35 = 0; n35 < 8; ++n35) {
                            this.rd.drawLine(202, 466 + n35 * 8, 202 + (8 - n35), 466 + n35 * 8);
                        }
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Ground Texture", 257, 471);
                        for (n35 = 0; n35 < 150; ++n35) {
                            this.rd.setColor(Color.getHSBColor((float)n35 * 0.006667f, 1.0f, 1.0f));
                            this.rd.drawLine(32 + n35 + 225, 477, 32 + n35 + 225, 484);
                        }
                        for (n35 = 0; n35 < 150; ++n35) {
                            this.rd.setColor(Color.getHSBColor(this.hsb[1][0], (float)n35 * 0.006667f, (float)n35 * 0.006667f));
                            this.rd.drawLine(32 + n35 + 225, 493, 32 + n35 + 225, 500);
                        }
                        this.rd.setFont(new Font("Arial", 0, 11));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Blend:", 257, 523);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.fillRect(295, 516, 112, 2);
                        this.rd.fillRect(295, 522, 112, 2);
                        f = 0.0f;
                        n37 = 255;
                        for (n35 = 0; n35 < 112; ++n35) {
                            n37 = (int)(255.0f / (f + 1.0f));
                            if (n37 > 255) {
                                n37 = 255;
                            }
                            if (n37 < 0) {
                                n37 = 0;
                            }
                            f += 0.02f;
                            this.rd.setColor(new Color(n37, n37, n37));
                            this.rd.drawLine(70 + n35 + 225, 518, 70 + n35 + 225, 521);
                        }
                        for (n35 = 0; n35 < 2; ++n35) {
                            int n38 = 3;
                            if (n35 == 1) {
                                n38 = 2;
                            }
                            for (int i = 0; i < n38; ++i) {
                                int n39 = i;
                                if (i == 1) {
                                    n39 = 2;
                                }
                                if (i == 2) {
                                    n39 = 1;
                                }
                                this.rd.setColor(new Color(0, 0, 0));
                                float f4 = this.hsb[n35][n39] * 150.0f;
                                if (i == 1 && n35 == 0) {
                                    float f5 = 0.75f;
                                    f4 = (this.hsb[n35][n39] - f5) / 0.001667f;
                                }
                                if (i == 2 && n35 == 0) {
                                    f4 = this.hsb[n35][n39] / 0.003333f;
                                }
                                if (f4 < 0.0f) {
                                    f4 = 0.0f;
                                }
                                if (f4 > 150.0f) {
                                    f4 = 150.0f;
                                }
                                this.rd.drawLine((int)((float)(32 + 225 * n35) + f4), 467 + i * 16 + 10 * n35, (int)((float)(32 + 225 * n35) + f4), 474 + i * 16 + 10 * n35);
                                this.rd.drawLine((int)((float)(33 + 225 * n35) + f4), 467 + i * 16 + 10 * n35, (int)((float)(33 + 225 * n35) + f4), 474 + i * 16 + 10 * n35);
                                this.rd.fillRect((int)((float)(31 + 225 * n35) + f4), 475 + i * 16 + 10 * n35, 4, 2);
                                this.rd.drawLine((int)((float)(30 + 225 * n35) + f4), 477 + i * 16 + 10 * n35, (int)((float)(35 + 225 * n35) + f4), 477 + i * 16 + 10 * n35);
                                if (this.xm > 29 + 225 * n35 && this.xm < 185 + 225 * n35 && this.ym > 468 + i * 16 + 10 * n35 && this.ym < 477 + i * 16 + 10 * n35 && this.mouses == 1 && this.mouseon == -1) {
                                    this.mouseon = i + n35 * 3;
                                }
                                if (this.mouseon != i + n35 * 3) continue;
                                this.hsb[n35][n39] = (float)(this.xm - (32 + 225 * n35)) * 0.006667f;
                                if (i == 1 && n35 == 1) {
                                    this.hsb[n35][1] = (float)(this.xm - (32 + 225 * n35)) * 0.006667f;
                                    if (this.hsb[n35][1] > 1.0f) {
                                        this.hsb[n35][1] = 1.0f;
                                    }
                                    if (this.hsb[n35][1] < 0.0f) {
                                        this.hsb[n35][1] = 0.0f;
                                    }
                                }
                                if (i == 1 && n35 == 0) {
                                    float f6 = 0.75f;
                                    this.hsb[n35][n39] = f6 + (float)(this.xm - (32 + 225 * n35)) * 0.001667f;
                                    if (this.hsb[n35][n39] < f6) {
                                        this.hsb[n35][n39] = f6;
                                    }
                                }
                                if (i == 2 && n35 == 0) {
                                    this.hsb[n35][n39] = (float)(this.xm - (32 + 225 * n35)) * 0.003333f;
                                    if ((double)this.hsb[n35][n39] > 0.5) {
                                        this.hsb[n35][n39] = 0.5f;
                                    }
                                }
                                if (this.hsb[n35][n39] > 1.0f) {
                                    this.hsb[n35][n39] = 1.0f;
                                }
                                if (!(this.hsb[n35][n39] < 0.0f)) continue;
                                this.hsb[n35][n39] = 0.0f;
                            }
                            this.rd.setColor(new Color(0, 0, 0));
                            float f7 = (float)(this.texture[3] - 20) * 2.8f;
                            if (n35 == 0) {
                                f7 = (float)this.cldd[3] * 11.2f;
                            }
                            if (f7 < 0.0f) {
                                f7 = 0.0f;
                            }
                            if (f7 > 112.0f) {
                                f7 = 112.0f;
                            }
                            this.rd.drawLine((int)((float)(70 + 225 * n35) + f7), 522 - 6 * n35, (int)((float)(70 + 225 * n35) + f7), 529 - 6 * n35);
                            this.rd.drawLine((int)((float)(71 + 225 * n35) + f7), 522 - 6 * n35, (int)((float)(71 + 225 * n35) + f7), 529 - 6 * n35);
                            this.rd.fillRect((int)((float)(69 + 225 * n35) + f7), 530 - 6 * n35, 4, 2);
                            this.rd.drawLine((int)((float)(68 + 225 * n35) + f7), 532 - 6 * n35, (int)((float)(73 + 225 * n35) + f7), 532 - 6 * n35);
                            if (this.xm <= 67 + 225 * n35 || this.xm >= 185 + 225 * n35 || this.ym <= 522 - 6 * n35 || this.ym >= 532 - 6 * n35 || this.mouses != 1 || this.mouseon != -1) continue;
                            this.mouseon = 6 + n35;
                        }
                        if (this.mouseon == 6) {
                            this.cldd[3] = (int)((float)(this.xm - 70) / 11.2f);
                            if (this.cldd[3] < 0) {
                                this.cldd[3] = 0;
                            }
                            if (this.cldd[3] > 10) {
                                this.cldd[3] = 10;
                            }
                        }
                        if (this.mouseon == 7) {
                            this.texture[3] = (int)((double)(this.xm - 70 - 225) / 2.8 + 20.0);
                            if (this.texture[3] < 20) {
                                this.texture[3] = 20;
                            }
                            if (this.texture[3] > 60) {
                                this.texture[3] = 60;
                            }
                        }
                        this.rd.setColor(new Color(0, 128, 255));
                        float f8 = (float)(1500 - Math.abs(this.cldd[4])) / 15.625f;
                        if (f8 > 64.0f) {
                            f8 = 64.0f;
                        }
                        if (f8 < 0.0f) {
                            f8 = 0.0f;
                        }
                        this.rd.drawRect(199, (int)(465.0f + f8), 12, 2);
                        if (this.xm > 197 && this.xm < 213 && this.ym > 463 && this.ym < 533 && this.mouses == 1 && this.mouseon == -1) {
                            this.mouseon = 8;
                        }
                        if (this.mouseon == 8) {
                            this.cldd[4] = -((int)((float)(530 - this.ym) * 15.625f + 500.0f));
                            if (this.cldd[4] > -500) {
                                this.cldd[4] = -500;
                            }
                            if (this.cldd[4] < -1500) {
                                this.cldd[4] = -1500;
                            }
                        }
                        Color color = Color.getHSBColor(this.hsb[0][0], this.hsb[0][1], this.hsb[0][2]);
                        this.m.setcloads(color.getRed(), color.getGreen(), color.getBlue(), this.cldd[3], this.cldd[4]);
                        this.cldd[0] = color.getRed();
                        this.cldd[1] = color.getGreen();
                        this.cldd[2] = color.getBlue();
                        color = Color.getHSBColor(this.hsb[1][0], this.hsb[1][1], this.hsb[1][2]);
                        this.m.setexture(color.getRed(), color.getGreen(), color.getBlue(), this.texture[3]);
                        this.texture[0] = color.getRed();
                        this.texture[1] = color.getGreen();
                        this.texture[2] = color.getBlue();
                        this.rd.setFont(new Font("Arial", 1, 12));
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Mountains", 452, 465);
                        this.rd.setFont(new Font("Arial", 0, 11));
                        this.rd.drawString("Mountain Generator Key:", 452, 480);
                        this.movefield(this.mgen, 452, 484, 120, 20);
                        if (this.mgen.hasFocus()) {
                            this.focuson = false;
                        }
                        if (!this.mgen.isShowing()) {
                            this.mgen.show();
                        }
                        if (this.button("  Generate New  ", 512, 525, 3, true)) {
                            this.m.mgen = (int)(Math.random() * 100000.0);
                            this.mgen.setText("" + this.m.mgen + "");
                            if (this.ttstage.equals("")) {
                                this.ttstage = this.tstage;
                            }
                            this.sortop();
                            this.readstage(1);
                        }
                        if (!this.mgen.getText().equals("" + this.m.mgen + "")) {
                            try {
                                int n40;
                                this.m.mgen = n40 = Integer.valueOf(this.mgen.getText()).intValue();
                                if (this.ttstage.equals("")) {
                                    this.ttstage = this.tstage;
                                }
                                this.sortop();
                                this.readstage(1);
                            }
                            catch (Exception exception) {
                                this.mgen.setText("" + this.m.mgen + "");
                            }
                        }
                        if (this.button(" Reset ", 650, 510, 0, true)) {
                            if (!this.ttstage.equals("")) {
                                this.tstage = this.ttstage;
                                this.ttstage = "";
                            }
                            this.readstage(1);
                            this.dtabed = -2;
                        }
                        if (this.button("        Save        ", 737, 510, 0, true)) {
                            this.sortop();
                            this.ttstage = "";
                            this.savefile();
                        }
                    }
                    if (this.dtab == 1) {
                        int n41;
                        if (this.dtabed != this.dtab) {
                            for (int i = 0; i < 3; ++i) {
                                this.snap[i] = (int)((float)this.m.snap[i] / 1.2f + 50.0f);
                            }
                            this.fogn[0] = (8 - ((this.m.fogd + 1) / 2 - 1)) * 20;
                            this.fogn[1] = (this.m.fade[0] - 5000) / 30;
                        }
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Atmosphere RGB Mask", 20, 461);
                        this.rd.setColor(new Color(128, 128, 128));
                        this.rd.drawLine(10, 457, 17, 457);
                        this.rd.drawLine(260, 457, 152, 457);
                        this.rd.drawLine(10, 457, 10, 546);
                        this.rd.drawLine(260, 457, 260, 527);
                        this.rd.drawLine(260, 527, 360, 527);
                        this.rd.drawLine(10, 546, 360, 546);
                        this.rd.drawLine(360, 527, 360, 546);
                        String[] stringArray3 = new String[]{"Red", "Green", "Blue"};
                        int[] nArray11 = new int[]{32, 20, 29};
                        int n42 = 38;
                        int n43 = -70;
                        for (n41 = 0; n41 < 3; ++n41) {
                            int n44;
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString("" + stringArray3[n41] + " :", nArray11[n41], 447 + n41 * 24 + n42);
                            this.rd.drawLine(140 + n43, 443 + (n41 * 24 + n42), 230 + n43, 443 + n41 * 24 + n42);
                            for (n44 = 1; n44 < 10; ++n44) {
                                this.rd.drawLine(140 + 10 * n44 + n43, 443 - n44 + n41 * 24 + n42, 140 + 10 * n44 + n43, 443 + n44 + n41 * 24 + n42);
                            }
                            this.rd.setColor(new Color(255, 0, 0));
                            n44 = (int)((float)this.snap[n41] / 1.1111f / 10.0f);
                            this.rd.fillRect(138 + (int)((float)this.snap[n41] / 1.1111f) + n43, 443 - n44 + n41 * 24 + n42, 5, n44 * 2 + 1);
                            this.rd.setColor(new Color(255, 128, 0));
                            this.rd.drawRect(139 + (int)((float)this.snap[n41] / 1.1111f) + n43, 434 + n41 * 24 + n42, 2, 18);
                            if (this.button(" - ", 260 + n43, 447 + n41 * 24 + n42, 4, false)) {
                                int n45 = n41;
                                this.snap[n45] = this.snap[n45] - 2;
                                if (this.snap[n41] < 0) {
                                    this.snap[n41] = 0;
                                }
                            }
                            if (!this.button(" + ", 300 + n43, 447 + n41 * 24 + n42, 4, false)) continue;
                            if (this.snap[0] + this.snap[1] + this.snap[2] > 200) {
                                for (int i = 0; i < 3; ++i) {
                                    if (i == n41) continue;
                                    int n46 = i;
                                    this.snap[n46] = this.snap[n46] - 1;
                                    if (this.snap[i] >= 0) continue;
                                    this.snap[i] = 0;
                                }
                            }
                            int n47 = n41;
                            this.snap[n47] = this.snap[n47] + 2;
                            if (this.snap[n41] <= 100) continue;
                            this.snap[n41] = 100;
                        }
                        if (this.m.snap[0] != (int)((float)this.snap[0] * 1.2f - 60.0f) || this.m.snap[1] != (int)((float)this.snap[1] * 1.2f - 60.0f) || this.m.snap[2] != (int)((float)this.snap[2] * 1.2f - 60.0f)) {
                            for (n41 = 0; n41 < 3; ++n41) {
                                this.m.snap[n41] = (int)((float)this.snap[n41] * 1.2f - 60.0f);
                            }
                            this.readstage(2);
                        }
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Car Lights :", 265, 541);
                        if (this.snap[0] + this.snap[1] + this.snap[2] > 110) {
                            this.rd.drawString("Off", 335, 541);
                            this.m.lightson = false;
                        } else {
                            this.rd.setColor(new Color(0, 200, 0));
                            this.rd.drawString("On", 335, 541);
                            this.m.lightson = true;
                        }
                        n41 = 33;
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Dust/Fog Properties", 280 + n41, 461);
                        this.rd.setColor(new Color(128, 128, 128));
                        this.rd.drawLine(270 + n41, 457, 277 + n41, 457);
                        this.rd.drawLine(540 + n41, 457, 393 + n41, 457);
                        this.rd.drawLine(270 + n41, 457, 270 + n41, 522);
                        this.rd.drawLine(540 + n41, 457, 540 + n41, 522);
                        this.rd.drawLine(270 + n41, 522, 540 + n41, 522);
                        String[] stringArray4 = new String[]{"Density", "Near / Far"};
                        int[] nArray12 = new int[]{292 + n41, 280 + n41};
                        int[] nArray13 = new int[]{20, 10};
                        n42 = 38;
                        n43 = 210 + n41;
                        for (int i = 0; i < 2; ++i) {
                            int n48;
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawString("" + stringArray4[i] + " :", nArray12[i], 447 + i * 24 + n42);
                            this.rd.drawLine(140 + n43, 443 + (i * 24 + n42), 230 + n43, 443 + i * 24 + n42);
                            for (n48 = 1; n48 < 10; ++n48) {
                                this.rd.drawLine(140 + 10 * n48 + n43, 443 - n48 + i * 24 + n42, 140 + 10 * n48 + n43, 443 + n48 + i * 24 + n42);
                            }
                            this.rd.setColor(new Color(255, 0, 0));
                            n48 = (int)((float)this.fogn[i] / 1.1111f / 10.0f);
                            this.rd.fillRect(138 + (int)((float)this.fogn[i] / 1.1111f) + n43, 443 - n48 + i * 24 + n42, 5, n48 * 2 + 1);
                            this.rd.setColor(new Color(255, 128, 0));
                            this.rd.drawRect(139 + (int)((float)this.fogn[i] / 1.1111f) + n43, 434 + i * 24 + n42, 2, 18);
                            if (this.button(" - ", 260 + n43, 447 + i * 24 + n42, 4, false)) {
                                int n49 = i;
                                this.fogn[n49] = this.fogn[n49] - nArray13[i];
                                if (this.fogn[i] < 0) {
                                    this.fogn[i] = 0;
                                }
                            }
                            if (!this.button(" + ", 300 + n43, 447 + i * 24 + n42, 4, false)) continue;
                            int n50 = i;
                            this.fogn[n50] = this.fogn[n50] + nArray13[i];
                            if (this.fogn[i] <= 100) continue;
                            this.fogn[i] = 100;
                        }
                        this.m.fogd = (8 - this.fogn[0] / 20 + 1) * 2 - 1;
                        this.m.fadfrom(5000 + this.fogn[1] * 30);
                        this.origfade = this.m.fade[0];
                        if (this.button(" Reset ", 650, 510, 0, true)) {
                            this.dtabed = -2;
                        }
                        if (this.button("        Save        ", 737, 510, 0, true)) {
                            this.sortop();
                            this.savefile();
                        }
                    }
                    if (this.dtab == 4) {
                        if (this.dtabed != this.dtab && this.cp.nlaps - 1 >= 0 && this.cp.nlaps - 1 <= 14) {
                            this.nlaps.select(this.cp.nlaps - 1);
                        }
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Set the number of laps for this stage:", 130, 496);
                        this.nlaps.move(348, 480);
                        if (!this.nlaps.isShowing()) {
                            this.nlaps.show();
                        }
                        if (this.cp.nlaps != this.nlaps.getSelectedIndex() + 1) {
                            this.cp.nlaps = this.nlaps.getSelectedIndex() + 1;
                            this.requestFocus();
                        }
                        if (this.button(" Reset ", 530, 496, 0, true)) {
                            this.dtabed = -2;
                        }
                        if (this.button("        Save        ", 617, 496, 0, true)) {
                            this.sortop();
                            this.savefile();
                        }
                    }
                    if (this.dtab == 5) {
                        if (this.dtabed != this.dtab) {
                            this.tracks.removeAll();
                            this.tracks.maxl = 200;
                            this.tracks.add(this.rd, "The Play List  -  MOD Tracks");
                            String[] stringArray5 = new File("" + Madness.fpath + "mystages/mymusic/").list();
                            if (stringArray5 != null) {
                                for (int i = 0; i < stringArray5.length; ++i) {
                                    if (!stringArray5[i].toLowerCase().endsWith(".zip")) continue;
                                    this.tracks.add(this.rd, stringArray5[i].substring(0, stringArray5[i].length() - 4));
                                }
                            }
                            if (this.ltrackname.equals("")) {
                                if (this.trackname.equals("")) {
                                    this.tracks.select(0);
                                } else {
                                    this.tracks.select(this.trackname);
                                }
                            } else {
                                this.tracks.select(this.ltrackname);
                            }
                            this.mouseon = -1;
                        }
                        this.tracks.move(10, 450);
                        if (this.tracks.getWidth() != 200) {
                            this.tracks.setSize(200, 21);
                        }
                        if (!this.tracks.isShowing()) {
                            this.tracks.show();
                        }
                        if (this.track.playing && this.track.loaded == 2) {
                            if (this.button("      Stop      ", 110, 495, 2, false)) {
                                this.track.stop();
                            }
                            if (!this.ltrackname.equals(this.tracks.getSelectedItem())) {
                                this.track.stop();
                            }
                            if (this.xm > 10 && this.xm < 210 && this.ym > 516 && this.ym < 534) {
                                if (this.mouses == 1) {
                                    this.mouseon = 1;
                                }
                                this.rd.setColor(new Color(0, 164, 242));
                            } else {
                                this.rd.setColor(new Color(120, 210, 255));
                            }
                            this.rd.drawRect(10, 516, 200, 18);
                            this.rd.setColor(new Color(200, 200, 200));
                            this.rd.drawLine(10, 523, 210, 523);
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawLine(10, 524, 210, 524);
                            this.rd.drawLine(10, 525, 210, 525);
                            this.rd.drawLine(10, 526, 210, 526);
                            this.rd.setColor(new Color(255, 255, 255));
                            this.rd.drawLine(10, 527, 210, 527);
                            int n51 = (int)((1.0f - (float)this.track.sClip.stream.available() / (float)this.avon) * 200.0f);
                            if (this.mouseon == 1) {
                                n51 = this.xm - 10;
                                if (n51 < 0) {
                                    n51 = 0;
                                }
                                if (n51 > 200) {
                                    n51 = 200;
                                }
                                if (this.mouses != 1) {
                                    this.track.sClip.stream.reset();
                                    this.track.sClip.stream.skip((long)((float)n51 / 200.0f * (float)this.avon));
                                    this.mouseon = -1;
                                }
                            }
                            this.rd.setColor(new Color(0, 0, 0));
                            this.rd.drawRect(8 + n51, 516, 4, 18);
                            this.rd.setColor(new Color(0, 164, 242));
                            this.rd.drawLine(10 + n51, 520, 10 + n51, 518);
                            this.rd.drawLine(10 + n51, 530, 10 + n51, 532);
                        } else if (this.tracks.getSelectedIndex() != 0 && this.button("      Play  >      ", 110, 495, 2, false)) {
                            if (!this.ltrackname.equals(this.tracks.getSelectedItem())) {
                                this.track.unload();
                                this.track = new RadicalMod("mystages/mymusic/" + this.tracks.getSelectedItem() + ".zip", 300, 8000, 125, true, false);
                                if (this.track.loaded == 2) {
                                    this.avon = this.track.sClip.stream.available();
                                    this.ltrackname = this.tracks.getSelectedItem();
                                } else {
                                    this.ltrackname = "";
                                }
                            }
                            if (!this.ltrackname.equals("")) {
                                this.track.play();
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to load '" + this.tracks.getSelectedItem() + "', please make sure it is a valid MOD Track!", "Stage Maker", 1);
                            }
                        }
                        if (this.tracks.getSelectedIndex() != 0) {
                            if (this.button("   Set as the stage's Sound Track  >   ", 330, 466, 2, false)) {
                                if (!this.ltrackname.equals(this.tracks.getSelectedItem())) {
                                    this.track.unload();
                                    this.track = new RadicalMod("mystages/mymusic/" + this.tracks.getSelectedItem() + ".zip", 300, 8000, 125, true, false);
                                    if (this.track.loaded == 2) {
                                        this.avon = this.track.sClip.stream.available();
                                        this.ltrackname = this.tracks.getSelectedItem();
                                    } else {
                                        this.ltrackname = "";
                                    }
                                }
                                if (!this.ltrackname.equals("")) {
                                    this.trackname = this.ltrackname;
                                    this.trackvol = (int)(220.0f / ((float)this.track.rvol / 3750.0f));
                                    try {
                                        File file = new File("" + Madness.fpath + "mystages/mymusic/" + this.trackname + ".zip");
                                        this.tracksize = (int)(file.length() / 1024L);
                                        if (this.tracksize > 700) {
                                            JOptionPane.showMessageDialog(null, "Cannot use '" + this.tracks.getSelectedItem() + "' as the sound track!\nIts file size is bigger then 700KB.\n\n", "Stage Maker", 1);
                                            this.trackname = "";
                                        }
                                    }
                                    catch (Exception exception) {
                                        this.tracksize = 111;
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Failed to load '" + this.tracks.getSelectedItem() + "', please make sure it is a valid MOD Track!", "Stage Maker", 1);
                                }
                            }
                            if (this.button("   X Delete   ", 258, 495, 2, false) && JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete this MOD Track from your Play List?\n\n" + this.tracks.getSelectedItem() + "\n\n>  If you delete this Track from the Play List you will not be able to use it for other stages as well!     \n\n", "Stage Maker", 0) == 0) {
                                this.deltrack();
                            }
                        }
                        if (this.button("      Add a new Track from a file . . .     ", 330, 530, 0, false) && JOptionPane.showConfirmDialog(null, "The game only accepts Module format music files for the game ('.mod', '.xm' and '.s3m' file extensions).\nA good place to find Module Tracks is the modarchive.com, all the current Module Tracks\nthat are distributed with the game are from the modarchive.com.\n\nTo find out more about Module Tracks and to learn how to compose & remix your own\nmusic, please read the section of the Stage Maker help about it.\n\nThe Stage Maker accepts only '.mod', '.xm' & '.s3m' files or a '.zip' file containing a Module file.\nThe file size of the Module must be less the 700KB (when compressed as a zip file).\n", "Stage Maker", 0) == 0) {
                            File file = null;
                            FileDialog fileDialog = new FileDialog(new Frame(), "Stage Maker - Add MOD Track file to stage sound track play list!");
                            fileDialog.setFile("*.mod;*.xm;*.s3m;*.zip");
                            fileDialog.setMode(0);
                            fileDialog.setVisible(true);
                            try {
                                if (fileDialog.getFile() != null) {
                                    file = new File("" + fileDialog.getDirectory() + "" + fileDialog.getFile() + "");
                                }
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            if (file != null) {
                                try {
                                    Object object;
                                    if (fileDialog.getFile().toLowerCase().endsWith(".mod") || fileDialog.getFile().toLowerCase().endsWith(".xm") || fileDialog.getFile().toLowerCase().endsWith(".s3m")) {
                                        int n52;
                                        File file2 = new File("" + Madness.fpath + "mystages/mymusic/");
                                        if (!file2.exists()) {
                                            file2.mkdirs();
                                        }
                                        String string = "" + Madness.fpath + "mystages/mymusic/" + file.getName().substring(0, file.getName().length() - 4) + ".zip";
                                        FileInputStream fileInputStream = new FileInputStream(file);
                                        object = new ZipOutputStream(new FileOutputStream(string));
                                        ZipEntry zipEntry = new ZipEntry("" + file.getName() + "");
                                        zipEntry.setSize(file.length());
                                        ((ZipOutputStream)object).putNextEntry(zipEntry);
                                        byte[] byArray = new byte[1024];
                                        while ((n52 = fileInputStream.read(byArray)) > 0) {
                                            ((ZipOutputStream)object).write(byArray, 0, n52);
                                        }
                                        ((ZipOutputStream)object).closeEntry();
                                        ((ZipOutputStream)object).close();
                                        fileInputStream.close();
                                        file2 = new File("" + Madness.fpath + "mystages/mymusic/" + file.getName() + ".zip");
                                        if (file2.length() / 1024L >= 700L) {
                                            JOptionPane.showMessageDialog(null, "The selected file is larger then 700KB in size when zipped and therefore cannot be added!", "Stage Maker", 1);
                                            file2.delete();
                                        }
                                    } else if (file.length() / 1024L < 700L) {
                                        int n53;
                                        File file3 = new File("" + Madness.fpath + "mystages/mymusic/");
                                        if (!file3.exists()) {
                                            file3.mkdirs();
                                        }
                                        file3 = new File("" + Madness.fpath + "mystages/mymusic/" + file.getName() + "");
                                        FileInputStream fileInputStream = new FileInputStream(file);
                                        FileOutputStream fileOutputStream = new FileOutputStream(file3);
                                        object = new byte[1024];
                                        while ((n53 = fileInputStream.read((byte[])object)) > 0) {
                                            fileOutputStream.write((byte[])object, 0, n53);
                                        }
                                        fileInputStream.close();
                                        fileOutputStream.close();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "The selected file is larger then 700KB in size and therefore cannot be added!", "Stage Maker", 1);
                                    }
                                    this.tracks.removeAll();
                                    this.tracks.add(this.rd, "Select MOD Track                      ");
                                    String[] stringArray6 = new File("" + Madness.fpath + "mystages/mymusic/").list();
                                    if (stringArray6 != null) {
                                        for (int i = 0; i < stringArray6.length; ++i) {
                                            if (!stringArray6[i].toLowerCase().endsWith(".zip")) continue;
                                            this.tracks.add(this.rd, stringArray6[i].substring(0, stringArray6[i].length() - 4));
                                        }
                                    }
                                    this.tracks.select(file.getName().substring(0, file.getName().length() - 4));
                                }
                                catch (Exception exception) {
                                    JOptionPane.showMessageDialog(null, "Unable to copy file! Error Deatials:\n" + exception, "Stage Maker", 1);
                                }
                            }
                        }
                        int n54 = 200;
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Sound Track", 280 + n54, 461);
                        String string = this.trackname;
                        if (string.equals("")) {
                            string = "No Sound Track set.";
                        } else if (this.button("   <  Remove Track   ", 378, 495, 2, false)) {
                            this.trackname = "";
                        }
                        this.rd.drawString(string, 629 - this.ftm.stringWidth(string) / 2, 482);
                        this.rd.setColor(new Color(128, 128, 128));
                        this.rd.drawLine(270 + n54, 457, 277 + n54, 457);
                        this.rd.drawLine(589 + n54, 457, 353 + n54, 457);
                        this.rd.drawLine(270 + n54, 457, 270 + n54, 497);
                        this.rd.drawLine(589 + n54, 457, 589 + n54, 497);
                        this.rd.drawLine(270 + n54, 497, 589 + n54, 497);
                        if (this.button(" Reset ", 576, 530, 0, true)) {
                            this.ltrackname = "";
                            this.dtabed = -2;
                        }
                        if (this.button("        Save        ", 663, 530, 0, true)) {
                            this.sortop();
                            this.savefile();
                        }
                    }
                    if (this.dtab == 6) {
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Test Drive the Stage", 400 - this.ftm.stringWidth("Test Drive the Stage") / 2, 470);
                        this.witho.move(342, 480);
                        if (!this.witho.isShowing()) {
                            this.witho.show();
                        }
                        if (this.button("     TEST DRIVE!     ", 400, 530, 0, true)) {
                            this.savefile();
                            this.errd = 0;
                            this.readstage(3);
                            if (this.cp.nsp < 2) {
                                this.errd = 7;
                            }
                            if (this.errd == 0) {
                                Madness.testcar = this.stagename;
                                Madness.testdrive = this.witho.getSelectedIndex() + 3;
                                Madness.game();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error!  This stage is not ready for a test drive!\nReason:\n" + this.errlo[this.errd - 1] + "\n\n", "Stage Maker", 0);
                            }
                        }
                    }
                    if (this.dtabed != this.dtab) {
                        this.dtabed = this.dtabed == -2 ? -1 : this.dtab;
                    }
                }
                if (this.tab == 3) {
                    this.rd.setFont(new Font("Arial", 1, 13));
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString("Publish Stage :  [ " + this.stagename + " ]", 30, 50);
                    this.rd.drawString("Publishing Type :", 30, 80);
                    this.pubtyp.move(150, 63);
                    if (!this.pubtyp.isShowing()) {
                        this.pubtyp.show();
                        this.pubtyp.select(1);
                    }
                    this.pubitem.move(790 - this.pubitem.w, 96);
                    if (!this.pubitem.isShowing()) {
                        this.pubitem.show();
                    }
                    if (this.pubitem.sel != 0) {
                        n = 0;
                        for (int i = 0; i < this.nms; ++i) {
                            if (!this.pubitem.getSelectedItem().equals(this.mystages[i])) continue;
                            n = 1;
                        }
                        if (n == 0) {
                            this.logged = 2;
                        }
                    }
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.setFont(new Font("Arial", 0, 12));
                    if (this.pubtyp.getSelectedIndex() == 0) {
                        this.rd.drawString("Private :  This means only you can have your stage in your account and no one else can add", 268, 72);
                        this.rd.drawString("it to their account to play it!", 268, 88);
                    }
                    if (this.pubtyp.getSelectedIndex() == 1) {
                        this.rd.drawString("Public :  This means anyone can add this stage to their account to play it, but only you can", 268, 72);
                        this.rd.drawString("download it to your Stage Maker and edit it (no one else but you can edit it).", 268, 88);
                    }
                    if (this.pubtyp.getSelectedIndex() == 2) {
                        this.rd.drawString("Super Public :  This means anyone can add this stage to their account to play it and can also", 268, 72);
                        this.rd.drawString("download it to their stage Maker, edit it and publish it.", 268, 88);
                    }
                    this.rd.setFont(new Font("Arial", 1, 12));
                    this.ftm = this.rd.getFontMetrics();
                    this.rd.drawString("Stage Name", 180 - this.ftm.stringWidth("Stage Name") / 2, 138);
                    this.rd.drawString("Created By", 400 - this.ftm.stringWidth("Created By") / 2, 138);
                    this.rd.drawString("Added By", 500 - this.ftm.stringWidth("Added By") / 2, 138);
                    this.rd.drawString("Publish Type", 600 - this.ftm.stringWidth("Publish Type") / 2, 138);
                    this.rd.drawString("Options", 720 - this.ftm.stringWidth("Options") / 2, 138);
                    this.rd.drawLine(350, 129, 350, 140);
                    this.rd.drawLine(450, 129, 450, 140);
                    this.rd.drawLine(550, 129, 550, 140);
                    this.rd.drawLine(650, 129, 650, 140);
                    this.rd.drawRect(10, 140, 780, 402);
                    if (this.button("       Publish  >       ", 102, 110, 0, true)) {
                        if (this.logged == 0) {
                            JOptionPane.showMessageDialog(null, "Please login to retrieve your account first before publishing!", "Stage Maker", 1);
                        }
                        if (this.logged == 3 || this.logged == -1) {
                            this.savefile();
                            this.errd = 0;
                            this.readstage(3);
                            if (this.cp.nsp < 2) {
                                this.errd = 7;
                            }
                            this.rd.setFont(new Font("Arial", 1, 12));
                            this.ftm = this.rd.getFontMetrics();
                            if (this.ftm.stringWidth(this.stagename) > 274) {
                                this.errd = 8;
                            }
                            if (this.errd == 0) {
                                int n55;
                                n = 0;
                                for (n55 = 0; n55 < this.pubitem.no; ++n55) {
                                    if (!this.pubitem.opts[n55].equals(this.stagename)) continue;
                                    n = JOptionPane.showConfirmDialog(null, "Replace your already online stage '" + this.stagename + "' with this one?", "Stage Maker", 0);
                                }
                                if (n == 0) {
                                    this.setCursor(new Cursor(3));
                                    this.rd.setFont(new Font("Arial", 1, 13));
                                    this.ftm = this.rd.getFontMetrics();
                                    this.rd.setColor(new Color(225, 225, 225));
                                    this.rd.fillRect(11, 141, 779, 401);
                                    this.rd.setColor(new Color(0, 0, 0));
                                    this.rd.drawString("Connecting to Server...", 400 - this.ftm.stringWidth("Connecting to Server...") / 2, 250);
                                    this.repaint();
                                    this.justpubd = this.stagename;
                                    n55 = -1;
                                    try {
                                        Socket socket = new Socket("multiplayer.needformadness.com", 7061);
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                                        DataOutputStream dataOutputStream = null;
                                        printWriter.println("20|" + this.tnick.getText() + "|" + this.tpass.getText() + "|" + this.stagename + "|" + this.pubtyp.getSelectedIndex() + "|");
                                        String string = bufferedReader.readLine();
                                        if (string != null) {
                                            n55 = this.servervalue(string, 0);
                                        }
                                        if (n55 == 0) {
                                            String string2 = " Publishing Stage ";
                                            String string3 = "" + this.tstage + "\r\n" + this.bstage + "";
                                            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(string3.getBytes()));
                                            String string4 = null;
                                            while ((string4 = dataInputStream.readLine()) != null) {
                                                string4 = string4.trim();
                                                printWriter.println(string4);
                                                this.rd.setColor(new Color(225, 225, 225));
                                                this.rd.fillRect(11, 141, 779, 401);
                                                this.rd.setColor(new Color(0, 0, 0));
                                                this.rd.drawString(string2, 400 - this.ftm.stringWidth(string2) / 2, 250);
                                                string2 = "| " + string2 + " |";
                                                if (string2.equals("| | | | | | | | | | | | | | | | | | | | | | | |  Publishing Stage  | | | | | | | | | | | | | | | | | | | | | | | |")) {
                                                    string2 = " Publishing Stage ";
                                                }
                                                this.repaint();
                                                try {
                                                    Thread.sleep(10L);
                                                }
                                                catch (InterruptedException interruptedException) {}
                                            }
                                            printWriter.println("QUITX1111");
                                            this.rd.setColor(new Color(225, 225, 225));
                                            this.rd.fillRect(11, 141, 779, 401);
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.drawString("Creating the stage online...", 400 - this.ftm.stringWidth("Creating the stage online...") / 2, 250);
                                            this.rd.drawString("This may take a couple of minutes, please wait...", 400 - this.ftm.stringWidth("This may take a couple of minutes, please wait...") / 2, 280);
                                            this.repaint();
                                            string = bufferedReader.readLine();
                                            n55 = string != null ? this.servervalue(string, 0) : -1;
                                            if (n55 == 0) {
                                                this.rd.setColor(new Color(225, 225, 225));
                                                this.rd.fillRect(11, 141, 779, 401);
                                                this.rd.setColor(new Color(0, 0, 0));
                                                this.rd.drawString("Uploading stage's sound track...", 400 - this.ftm.stringWidth("Uploading Stage's Sound Track...") / 2, 250);
                                                this.rd.drawString("This may take a couple of minutes, please wait...", 400 - this.ftm.stringWidth("This may take a couple of minutes, please wait...") / 2, 280);
                                                this.repaint();
                                                File file = new File("" + Madness.fpath + "mystages/mymusic/" + this.trackname + ".zip");
                                                if (!this.trackname.equals("") && file.exists()) {
                                                    int n56 = (int)file.length();
                                                    printWriter.println("track|" + this.trackname + "|" + n56 + "|");
                                                    string = bufferedReader.readLine();
                                                    n55 = string != null ? this.servervalue(string, 0) : -2;
                                                    if (n55 == 0) {
                                                        FileInputStream fileInputStream = new FileInputStream(file);
                                                        byte[] byArray = new byte[n56];
                                                        fileInputStream.read(byArray);
                                                        fileInputStream.close();
                                                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
                                                        dataOutputStream.write(byArray, 0, n56);
                                                        string = bufferedReader.readLine();
                                                        n55 = string != null ? this.servervalue(string, 0) : -2;
                                                    }
                                                    if (n55 == -67) {
                                                        n55 = 0;
                                                    }
                                                } else {
                                                    printWriter.println("END");
                                                    string = bufferedReader.readLine();
                                                }
                                            }
                                        }
                                        socket.close();
                                    }
                                    catch (Exception exception) {
                                        n55 = -1;
                                    }
                                    this.setCursor(new Cursor(0));
                                    n3 = 0;
                                    if (n55 == 0) {
                                        this.logged = 1;
                                        n3 = 1;
                                    }
                                    if (n55 == 3) {
                                        JOptionPane.showMessageDialog(null, "Unable to publish stage.\nReason:\n" + this.errlo[8] + "\n\n", "Stage Maker", 1);
                                        n3 = 1;
                                    }
                                    if (n55 == 4) {
                                        JOptionPane.showMessageDialog(null, "Unable to publish stage.\nReason:\nStage name used (" + this.stagename + ").\nThe name '" + this.stagename + "' is already used by another published stage.\nPlease rename your stage.\n\n", "Stage Maker", 1);
                                        n3 = 1;
                                    }
                                    if (n55 == 5) {
                                        JOptionPane.showMessageDialog(null, "Unable to create stage online!  Unknown Error.  Please try again later.", "Stage Maker", 1);
                                        n3 = 1;
                                    }
                                    if (n55 > 5) {
                                        JOptionPane.showMessageDialog(null, "Unable to publish stage fully!  Unknown Error.  Please try again later.", "Stage Maker", 1);
                                        n3 = 1;
                                    }
                                    if (n55 == -4) {
                                        this.logged = 1;
                                        JOptionPane.showMessageDialog(null, "Unable to upload sound track!\nReason:\nAnother MOD Track is already uploaded with the same name, please rename your Track.\nOpen your 'mystages' folder then open 'mymusic' to find your MOD Track to rename it.\n\n", "Stage Maker", 1);
                                        n3 = 1;
                                    }
                                    if (n55 == -3) {
                                        this.logged = 1;
                                        JOptionPane.showMessageDialog(null, "Unable to upload sound track!\nReason:\nYour MOD Track\u2019s file size is too large, Track file size must be less then 700KB to be accepted.\n\n", "Stage Maker", 1);
                                        n3 = 1;
                                    }
                                    if (n55 == -2) {
                                        this.logged = 1;
                                        JOptionPane.showMessageDialog(null, "Unable to upload sound track!  Unknown Error.  Please try again later.", "Stage Maker", 1);
                                        n3 = 1;
                                    }
                                    if (n3 == 0) {
                                        JOptionPane.showMessageDialog(null, "Unable to publish stage!  Unknown Error.", "Stage Maker", 1);
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Error!  This stage is not ready for publishing!\nReason:\n" + this.errlo[this.errd - 1] + "\n\n", "Stage Maker", 0);
                            }
                        }
                    }
                    if (this.logged == 3) {
                        for (n = 0; n < this.nms; ++n) {
                            block753: {
                                this.rd.setColor(new Color(235, 235, 235));
                                if (this.xm > 11 && this.xm < 789 && this.ym > 142 + n * 20 && this.ym < 160 + n * 20) {
                                    this.rd.setColor(new Color(255, 255, 255));
                                }
                                this.rd.fillRect(11, 142 + n * 20, 778, 18);
                                this.rd.setFont(new Font("Arial", 0, 12));
                                this.ftm = this.rd.getFontMetrics();
                                this.rd.setColor(new Color(0, 0, 0));
                                this.rd.drawString(this.mystages[n], 180 - this.ftm.stringWidth(this.mystages[n]) / 2, 156 + n * 20);
                                this.rd.setColor(new Color(155, 155, 155));
                                this.rd.drawLine(350, 145 + n * 20, 350, 157 + n * 20);
                                if (this.pubt[n] != -1) {
                                    this.rd.drawLine(450, 145 + n * 20, 450, 157 + n * 20);
                                    this.rd.drawLine(550, 145 + n * 20, 550, 157 + n * 20);
                                    this.rd.drawLine(650, 145 + n * 20, 650, 157 + n * 20);
                                    boolean bl = false;
                                    if (this.maker[n].toLowerCase().equals(this.tnick.getText().toLowerCase())) {
                                        bl = true;
                                        this.rd.setColor(new Color(0, 64, 0));
                                        this.rd.drawString("You", 400 - this.ftm.stringWidth("You") / 2, 156 + n * 20);
                                    } else {
                                        this.rd.setColor(new Color(0, 0, 64));
                                        this.rd.drawString(this.maker[n], 400 - this.ftm.stringWidth(this.maker[n]) / 2, 156 + n * 20);
                                    }
                                    if (this.nad[n] > 1) {
                                        if (this.ovbutton("" + this.nad[n] + " Players", 500, 156 + n * 20)) {
                                            String string = "[ " + this.mystages[n] + " ]  has been added by the following players to their accounts:     \n\n";
                                            int n57 = 0;
                                            for (int i = 0; i < this.nad[n]; ++i) {
                                                if (++n57 == 17) {
                                                    string = string + "\n";
                                                    n57 = 1;
                                                }
                                                string = string + this.addeda[n][i];
                                                if (i == this.nad[n] - 1) continue;
                                                if (i != this.nad[n] - 2) {
                                                    string = string + ", ";
                                                    continue;
                                                }
                                                if (n57 == 16) {
                                                    string = string + "\nand ";
                                                    n57 = 0;
                                                    continue;
                                                }
                                                string = string + " and ";
                                            }
                                            string = string + "\n \n \n";
                                            JOptionPane.showMessageDialog(null, string, "Stage Maker", 1);
                                        }
                                    } else {
                                        this.rd.setColor(new Color(0, 0, 64));
                                        this.rd.drawString("None", 500 - this.ftm.stringWidth("None") / 2, 156 + n * 20);
                                    }
                                    if (this.pubt[n] == 0) {
                                        this.rd.setColor(new Color(0, 0, 64));
                                        this.rd.drawString("Private", 600 - this.ftm.stringWidth("Private") / 2, 156 + n * 20);
                                    }
                                    if (this.pubt[n] == 1) {
                                        this.rd.setColor(new Color(0, 0, 64));
                                        this.rd.drawString("Public", 600 - this.ftm.stringWidth("Public") / 2, 156 + n * 20);
                                    }
                                    if (this.pubt[n] == 2) {
                                        this.rd.setColor(new Color(0, 64, 0));
                                        this.rd.drawString("Super Public", 600 - this.ftm.stringWidth("Super Public") / 2, 156 + n * 20);
                                    }
                                    if ((this.pubt[n] == 2 || bl) && this.ovbutton("Download", 700, 156 + n * 20)) {
                                        int n58 = 0;
                                        for (int i = 0; i < this.slstage.getItemCount(); ++i) {
                                            if (!this.mystages[n].equals(this.slstage.getItem(i))) continue;
                                            n58 = JOptionPane.showConfirmDialog(null, "Replace the local " + this.mystages[n] + " in your 'mystages' folder with the published online copy?", "Stage Maker", 0);
                                        }
                                        if (n58 == 0) {
                                            this.setCursor(new Cursor(3));
                                            this.rd.setFont(new Font("Arial", 1, 13));
                                            this.ftm = this.rd.getFontMetrics();
                                            this.rd.setColor(new Color(225, 225, 225));
                                            this.rd.fillRect(11, 141, 779, 401);
                                            this.rd.setColor(new Color(0, 0, 0));
                                            this.rd.drawString("Downloading stage, please wait...", 400 - this.ftm.stringWidth("Downloading stage, please wait...") / 2, 250);
                                            this.repaint();
                                            try {
                                                ZipInputStream zipInputStream;
                                                String string = ("http://multiplayer.needformadness.com/tracks/" + this.mystages[n] + ".radq?reqlo=" + (int)(Math.random() * 1000.0) + "").replace(' ', '_');
                                                URL uRL = new URL(string);
                                                int n59 = uRL.openConnection().getContentLength();
                                                DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                                                byte[] byArray = new byte[n59];
                                                dataInputStream.readFully(byArray);
                                                dataInputStream.close();
                                                if (byArray[0] == 80 && byArray[1] == 75 && byArray[2] == 3) {
                                                    zipInputStream = new ZipInputStream(new ByteArrayInputStream(byArray));
                                                } else {
                                                    byte[] byArray2 = new byte[n59 - 40];
                                                    for (int i = 0; i < n59 - 40; ++i) {
                                                        int n60 = 20;
                                                        if (i >= 500) {
                                                            n60 = 40;
                                                        }
                                                        byArray2[i] = byArray[i + n60];
                                                    }
                                                    zipInputStream = new ZipInputStream(new ByteArrayInputStream(byArray2));
                                                }
                                                ZipEntry zipEntry = zipInputStream.getNextEntry();
                                                if (zipEntry != null) {
                                                    Object object;
                                                    int n61;
                                                    int n62;
                                                    String string5 = "";
                                                    byte[] byArray3 = new byte[n62];
                                                    int n63 = 0;
                                                    for (n62 = Integer.valueOf(zipEntry.getName()).intValue(); n62 > 0; n62 -= n61) {
                                                        n61 = zipInputStream.read(byArray3, n63, n62);
                                                        n63 += n61;
                                                    }
                                                    String string6 = new String(byArray3);
                                                    string6 = string6 + "\n";
                                                    String string7 = "";
                                                    int n64 = 0;
                                                    int n65 = string6.indexOf("\n", 0);
                                                    while (n65 != -1 && n64 < string6.length()) {
                                                        object = string6.substring(n64, n65).trim();
                                                        n64 = n65 + 1;
                                                        n65 = string6.indexOf("\n", n64);
                                                        if (!((String)object).startsWith("stagemaker(") && !((String)object).startsWith("publish(")) {
                                                            string7 = string7 + "" + (String)object + "\r\n";
                                                        } else {
                                                            string7 = string7.trim();
                                                            string7 = string7 + "\r\n";
                                                        }
                                                        if (!((String)object).startsWith("soundtrack")) continue;
                                                        string5 = this.getstring("soundtrack", (String)object, 0);
                                                    }
                                                    string7 = string7.trim();
                                                    string7 = string7 + "\r\n\r\n";
                                                    object = new File("" + Madness.fpath + "mystages/");
                                                    if (!((File)object).exists()) {
                                                        ((File)object).mkdirs();
                                                    }
                                                    object = new File("" + Madness.fpath + "mystages/" + this.mystages[n] + ".txt");
                                                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter((File)object));
                                                    bufferedWriter.write(string7);
                                                    bufferedWriter.close();
                                                    bufferedWriter = null;
                                                    zipInputStream.close();
                                                    if (!string5.equals("")) {
                                                        try {
                                                            this.rd.setColor(new Color(0, 0, 0));
                                                            this.rd.drawString("Downloading stage's sound track...", 400 - this.ftm.stringWidth("Downloading stage's sound track...") / 2, 280);
                                                            this.repaint();
                                                            string = ("http://multiplayer.needformadness.com/tracks/music/" + string5 + ".zip").replace(' ', '_');
                                                            uRL = new URL(string);
                                                            n59 = uRL.openConnection().getContentLength();
                                                            object = new File("" + Madness.fpath + "mystages/mymusic/" + string5 + ".zip");
                                                            if (((File)object).exists()) {
                                                                n58 = ((File)object).length() == (long)n59 ? 1 : JOptionPane.showConfirmDialog(null, "Another track named '" + string5 + "' already exists in your Sound Tracks folder!\nReplace it with the one attached to this stage?", "Stage Maker", 0);
                                                            }
                                                            if (n58 == 0) {
                                                                dataInputStream = new DataInputStream(uRL.openStream());
                                                                byArray = new byte[n59];
                                                                dataInputStream.readFully(byArray);
                                                                dataInputStream.close();
                                                                FileOutputStream fileOutputStream = new FileOutputStream((File)object);
                                                                fileOutputStream.write(byArray);
                                                                fileOutputStream.close();
                                                                fileOutputStream = null;
                                                            }
                                                        }
                                                        catch (Exception exception) {
                                                            // empty catch block
                                                        }
                                                    }
                                                    this.setCursor(new Cursor(0));
                                                    JOptionPane.showMessageDialog(null, "" + this.mystages[n] + " has been successfully downloaded!", "Stage Maker", 1);
                                                    break block753;
                                                }
                                                JOptionPane.showMessageDialog(null, "Unable to download stage.  Unknown Error!     \nPlease try again later.", "Stage Maker", 1);
                                            }
                                            catch (Exception exception) {
                                                JOptionPane.showMessageDialog(null, "Unable to download stage.  Unknown Error!     \nPlease try again later.", "Stage Maker", 1);
                                            }
                                        }
                                    }
                                } else {
                                    this.rd.drawString("-    Error Loading this stage's info!    -", 550 - this.ftm.stringWidth("-    Error Loading this stage's info!    -") / 2, 156 + n * 20);
                                }
                            }
                            if (!this.ovbutton("X", 765, 156 + n * 20) || JOptionPane.showConfirmDialog(null, "Remove " + this.mystages[n] + " from your account?", "Stage Maker", 0) != 0) continue;
                            this.setCursor(new Cursor(3));
                            int n66 = -1;
                            try {
                                Socket socket = new Socket("multiplayer.needformadness.com", 7061);
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                                printWriter.println("19|" + this.tnick.getText() + "|" + this.tpass.getText() + "|" + this.mystages[n] + "|");
                                String string = bufferedReader.readLine();
                                if (string != null) {
                                    n66 = this.servervalue(string, 0);
                                }
                                socket.close();
                            }
                            catch (Exception exception) {
                                n66 = -1;
                            }
                            if (n66 == 0) {
                                this.logged = 1;
                                continue;
                            }
                            this.setCursor(new Cursor(0));
                            JOptionPane.showMessageDialog(null, "Failed to remove " + this.mystages[n] + " from your account.  Unknown Error!     \nPlease try again later.", "Stage Maker", 1);
                        }
                    }
                    if (this.logged == 2) {
                        this.mystages[this.roto] = this.pubitem.getSelectedItem();
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(225, 225, 225));
                        this.rd.fillRect(50, 150, 600, 150);
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Loading " + this.mystages[this.roto] + "\u2018s info...", 400 - this.ftm.stringWidth("Loading " + this.mystages[this.roto] + "\u2018s info...") / 2, 220);
                        this.repaint();
                        this.maker[this.roto] = "Unkown";
                        this.pubt[this.roto] = -1;
                        this.nad[this.roto] = 0;
                        String string = "";
                        try {
                            String string8 = ("http://multiplayer.needformadness.com/tracks/" + this.mystages[this.roto] + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "").replace(' ', '_');
                            URL uRL = new URL(string8);
                            DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
                            while ((string = dataInputStream.readLine()) != null) {
                                string = "" + string.trim();
                                if (!string.startsWith("details")) continue;
                                this.maker[this.roto] = this.getSvalue("details", string, 0);
                                this.pubt[this.roto] = this.getvalue("details", string, 1);
                                boolean bl = false;
                                while (!bl) {
                                    this.addeda[this.roto][this.nad[this.roto]] = this.getSvalue("details", string, 2 + this.nad[this.roto]);
                                    if (this.addeda[this.roto][this.nad[this.roto]].equals("")) {
                                        bl = true;
                                        continue;
                                    }
                                    int n67 = this.roto;
                                    this.nad[n67] = this.nad[n67] + 1;
                                }
                            }
                            ++this.nms;
                            if (this.nms > 20) {
                                this.nms = 20;
                            }
                            ++this.roto;
                            if (this.roto >= 20) {
                                this.roto = 0;
                            }
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        this.setCursor(new Cursor(0));
                        this.logged = 3;
                    }
                    if (this.logged == -1) {
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Ready to publish...", 400 - this.ftm.stringWidth("Ready to publish...") / 2, 220);
                        this.rd.drawString("Click \u2018Publish\u2019 above to add stage: '" + this.stagename + "'.", 400 - this.ftm.stringWidth("Click \u2018Publish\u2019 above to add stage: '" + this.stagename + "'.") / 2, 280);
                    }
                    if (this.logged == 1) {
                        Object object;
                        this.rd.setColor(new Color(225, 225, 225));
                        this.rd.fillRect(11, 141, 779, 401);
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.setColor(new Color(0, 0, 0));
                        this.rd.drawString("Loading your account's stage list...", 400 - this.ftm.stringWidth("Loading your account's stage list...") / 2, 220);
                        this.repaint();
                        this.pubitem.removeAll();
                        this.pubitem.add(this.rd, "Account Stage");
                        this.nms = 0;
                        this.roto = 0;
                        int n68 = 0;
                        String string = "";
                        try {
                            URL uRL = new URL("http://multiplayer.needformadness.com/tracks/lists/" + this.tnick.getText() + ".txt?reqlo=" + (int)(Math.random() * 1000.0) + "");
                            object = new DataInputStream(uRL.openStream());
                            while ((string = ((DataInputStream)object).readLine()) != null) {
                                string = "" + string.trim();
                                if (!string.startsWith("mystages")) continue;
                                boolean bl = true;
                                while (bl && n68 < 700) {
                                    String string9 = this.getSvalue("mystages", string, n68);
                                    if (string9.equals("")) {
                                        bl = false;
                                        continue;
                                    }
                                    this.pubitem.add(this.rd, string9);
                                    ++n68;
                                }
                            }
                            this.setCursor(new Cursor(0));
                            this.logged = -1;
                            ((FilterInputStream)object).close();
                        }
                        catch (Exception exception) {
                            object = "" + exception;
                            if (((String)object).indexOf("FileNotFound") != -1) {
                                this.setCursor(new Cursor(0));
                                this.logged = -1;
                            }
                            this.logged = 0;
                            JOptionPane.showMessageDialog(null, "Unable to connect to server at this moment, please try again later.", "Stage Maker", 1);
                        }
                        if (!this.justpubd.equals("")) {
                            this.pubitem.select(this.justpubd);
                            this.justpubd = "";
                        }
                    }
                    if (this.logged == 0) {
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Login to Retrieve your Account Stages", 400 - this.ftm.stringWidth("Login to Retrieve your Account Stages") / 2, 220);
                        this.rd.drawString("Nickname:", 376 - this.ftm.stringWidth("Nickname:") - 14, 266);
                        if (!this.tnick.isShowing()) {
                            this.tnick.show();
                        }
                        this.movefield(this.tnick, 376, 250, 129, 23);
                        this.rd.drawString("Password:", 376 - this.ftm.stringWidth("Password:") - 14, 296);
                        if (!this.tpass.isShowing()) {
                            this.tpass.show();
                        }
                        this.movefield(this.tpass, 376, 280, 129, 23);
                        if (this.button("       Login       ", 400, 340, 0, true)) {
                            this.setCursor(new Cursor(3));
                            int n69 = -1;
                            try {
                                Socket socket = new Socket("multiplayer.needformadness.com", 7061);
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                                printWriter.println("1|" + this.tnick.getText().toLowerCase() + "|" + this.tpass.getText() + "|");
                                String string = bufferedReader.readLine();
                                if (string != null) {
                                    n69 = this.servervalue(string, 0);
                                }
                                socket.close();
                            }
                            catch (Exception exception) {
                                n69 = -1;
                            }
                            if (n69 == 0 || n69 == 3 || n69 > 10) {
                                this.tnick.hide();
                                this.tpass.hide();
                                this.logged = 1;
                                this.savesettings();
                            }
                            if (n69 == 1 || n69 == 2) {
                                this.setCursor(new Cursor(0));
                                JOptionPane.showMessageDialog(null, "Sorry.  Incorrect Nickname or Password!", "Stage Maker", 0);
                            }
                            if (n69 == -167) {
                                this.setCursor(new Cursor(0));
                                JOptionPane.showMessageDialog(null, "Sorry.  Your trial account cannot publish stages.  Please upgrade to a full account!   ", "Stage Maker", 0);
                            }
                            if (n69 == -1) {
                                this.setCursor(new Cursor(0));
                                JOptionPane.showMessageDialog(null, "Unable to connect to server at this moment, please try again later.", "Stage Maker", 1);
                            }
                        }
                        this.rd.setFont(new Font("Arial", 1, 13));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("Register a full account or if you have a trial account upgrade it!", 400 - this.ftm.stringWidth("Register a full account or if you have a trial account upgrade it!") / 2, 450);
                        if (this.button("   Register!   ", 340, 480, 0, true)) {
                            Madness.openurl("http://multiplayer.needformadness.com/register.html?ref=game");
                        }
                        if (this.button("   Upgrade!   ", 460, 480, 0, true)) {
                            Madness.openurl("http://multiplayer.needformadness.com/edit.pl");
                        }
                        this.rd.setFont(new Font("Arial", 0, 12));
                        this.ftm = this.rd.getFontMetrics();
                        this.rd.drawString("You need a full account to publish your stages to the multiplayer game!", 400 - this.ftm.stringWidth("You need a full account to publish your stages to the multiplayer game!") / 2, 505);
                    }
                }
                if (this.tabed != this.tab) {
                    this.tabed = this.tabed == -2 ? -1 : this.tab;
                }
                this.rd.setColor(new Color(0, 0, 0));
                this.rd.fillRect(0, 0, 800, 25);
                if (!this.onbtgame) {
                    this.rd.drawImage(this.btgame[0], 620, 0, null);
                } else {
                    this.rd.drawImage(this.btgame[1], 620, 0, null);
                }
                this.rd.setFont(new Font("Arial", 1, 13));
                this.ftm = this.rd.getFontMetrics();
                String[] stringArray = new String[]{"Stage", "Build", "View & Edit", "Publish"};
                int[] nArray = new int[]{0, 0, 100, 90};
                int[] nArray14 = new int[]{0, 25, 25, 0};
                n2 = 4;
                if (this.stagename.equals("") || this.sfase != 0) {
                    this.tab = 0;
                    n2 = 1;
                }
                for (int i = 0; i < n2; ++i) {
                    this.rd.setColor(new Color(170, 170, 170));
                    if (this.xm > nArray[0] && this.xm < nArray[3] && this.ym > 0 && this.ym < 25) {
                        this.rd.setColor(new Color(200, 200, 200));
                    }
                    if (this.tab == i) {
                        this.rd.setColor(new Color(225, 225, 225));
                    }
                    this.rd.fillPolygon(nArray, nArray14, 4);
                    this.rd.setColor(new Color(0, 0, 0));
                    this.rd.drawString(stringArray[i], i * 100 + 45 - this.ftm.stringWidth(stringArray[i]) / 2, 17);
                    if (this.xm > nArray[0] && this.xm < nArray[3] && this.ym > 0 && this.ym < 25 && this.mouses == -1) {
                        this.tab = i;
                    }
                    int n70 = 0;
                    while (n70 < 4) {
                        int n71 = n70++;
                        nArray[n71] = nArray[n71] + 100;
                    }
                }
                if (this.mouses == -1) {
                    this.mouses = 0;
                }
                this.drawms();
                this.repaint();
                if (this.exwist) continue;
                try {
                    Thread.sleep(40L);
                }
                catch (InterruptedException interruptedException) {}
            }
            this.track.unload();
            this.track = null;
            this.rd.dispose();
            System.gc();
            if (Madness.endadv != 2) break block754;
            Madness.advopen();
        }
    }

    public void removesp() {
        if (this.nundo < 5000) {
            this.undos[this.nundo] = this.bstage;
            ++this.nundo;
        }
        String string = "";
        if (this.co[this.esp].colok != 30 && this.co[this.esp].colok != 31 && this.co[this.esp].colok != 32 && this.co[this.esp].colok != 66) {
            string = "set(" + (this.co[this.esp].colok + 10) + "," + this.co[this.esp].x + "," + this.co[this.esp].z + "," + this.co[this.esp].roofat + ")";
        }
        if (this.co[this.esp].colok == 31) {
            string = "fix(" + (this.co[this.esp].colok + 10) + "," + this.co[this.esp].x + "," + this.co[this.esp].z + "," + this.co[this.esp].y + "," + this.co[this.esp].roofat + ")";
        }
        if (this.co[this.esp].colok == 30 || this.co[this.esp].colok == 32) {
            string = "chk(" + (this.co[this.esp].colok + 10) + "," + this.co[this.esp].x + "," + this.co[this.esp].z + "," + this.co[this.esp].roofat + ")";
        }
        if (this.co[this.esp].colok == 54) {
            string = "chk(" + (this.co[this.esp].colok + 10) + "," + this.co[this.esp].x + "," + this.co[this.esp].z + "," + this.co[this.esp].roofat + "," + this.co[this.esp].y + ")";
        }
        if (this.co[this.esp].colok == 66) {
            string = "pile(" + this.co[this.esp].srz + "," + this.co[this.esp].srx + "," + this.co[this.esp].sry + "," + this.co[this.esp].x + "," + this.co[this.esp].z + ")";
        }
        int n = this.bstage.indexOf(string);
        int n2 = n + string.length();
        int n3 = -1;
        int n4 = this.bstage.indexOf("set", n2);
        if (n4 != -1) {
            n3 = n4;
        }
        if ((n4 = this.bstage.indexOf("chk", n2)) != -1 && n4 < n3) {
            n3 = n4;
        }
        if ((n4 = this.bstage.indexOf("fix", n2)) != -1 && n4 < n3) {
            n3 = n4;
        }
        if (n3 == -1 && (n3 = this.bstage.indexOf("\r\n", n2)) != -1) {
            ++n3;
        }
        if (n3 != -1) {
            n2 = n3;
        }
        if (n != -1) {
            this.bstage = "" + this.bstage.substring(0, n) + "" + this.bstage.substring(n2, this.bstage.length()) + "";
        }
        this.readstage(0);
    }

    public void copyesp(boolean bl) {
        this.sp = this.co[this.esp].colok;
        this.rot = this.co[this.esp].roofat;
        if (this.sp == 2) {
            this.rot -= 30;
        }
        if (this.sp == 3) {
            this.rot += 30;
        }
        if (this.sp == 15) {
            this.rot += 90;
        }
        if (this.sp == 20) {
            this.rot += 180;
        }
        if (this.sp == 26) {
            this.rot -= 90;
        }
        if (this.sp == 0) {
            this.sptyp = 0;
            this.spart = 0;
        }
        if (this.sp == 4) {
            this.sptyp = 0;
            this.spart = 1;
        }
        if (this.sp == 13) {
            this.sptyp = 0;
            this.spart = 2;
        }
        if (this.sp == 3) {
            this.sptyp = 0;
            this.spart = 3;
        }
        if (this.sp == 2) {
            this.sptyp = 0;
            this.spart = 4;
        }
        if (this.sp == 1) {
            this.sptyp = 0;
            this.spart = 5;
        }
        if (this.sp == 35) {
            this.sptyp = 0;
            this.spart = 6;
        }
        if (this.sp == 36) {
            this.sptyp = 0;
            this.spart = 7;
        }
        if (this.sp == 10) {
            this.sptyp = 0;
            this.spart = 8;
        }
        if (this.sp == 5) {
            this.sptyp = 0;
            this.spart = 9;
        }
        if (this.sp == 7) {
            this.sptyp = 0;
            this.spart = 10;
        }
        if (this.sp == 14) {
            this.sptyp = 0;
            this.spart = 11;
        }
        if (this.sp == 6) {
            this.sptyp = 0;
            this.spart = 12;
        }
        if (this.sp == 34) {
            this.sptyp = 0;
            this.spart = 13;
        }
        if (this.sp == 33) {
            this.sptyp = 0;
            this.spart = 14;
        }
        if (this.sp == 11) {
            this.sptyp = 0;
            this.spart = 15;
        }
        if (this.sp == 8) {
            this.sptyp = 0;
            this.spart = 16;
        }
        if (this.sp == 9) {
            this.sptyp = 0;
            this.spart = 17;
        }
        if (this.sp == 15) {
            this.sptyp = 0;
            this.spart = 18;
        }
        if (this.sp == 12) {
            this.sptyp = 0;
            this.spart = 19;
        }
        if (this.sp == 46) {
            this.sptyp = 0;
            this.spart = 20;
        }
        if (this.sp == 47) {
            this.sptyp = 0;
            this.spart = 21;
        }
        if (this.sp == 48) {
            this.sptyp = 0;
            this.spart = 23;
        }
        if (this.sp == 49) {
            this.sptyp = 0;
            this.spart = 24;
        }
        if (this.sp == 50) {
            this.sptyp = 0;
            this.spart = 22;
        }
        if (this.sp == 51) {
            this.sptyp = 0;
            this.spart = 25;
        }
        if (this.sp == 16) {
            this.sptyp = 1;
            this.spart = 0;
        }
        if (this.sp == 18) {
            this.sptyp = 1;
            this.spart = 1;
        }
        if (this.sp == 19) {
            this.sptyp = 1;
            this.spart = 2;
        }
        if (this.sp == 22) {
            this.sptyp = 1;
            this.spart = 3;
        }
        if (this.sp == 17) {
            this.sptyp = 1;
            this.spart = 4;
        }
        if (this.sp == 21) {
            this.sptyp = 1;
            this.spart = 5;
        }
        if (this.sp == 20) {
            this.sptyp = 1;
            this.spart = 6;
        }
        if (this.sp == 39) {
            this.sptyp = 1;
            this.spart = 7;
        }
        if (this.sp == 42) {
            this.sptyp = 1;
            this.spart = 8;
        }
        if (this.sp == 40) {
            this.sptyp = 1;
            this.spart = 9;
        }
        if (this.sp == 23) {
            this.sptyp = 1;
            this.spart = 10;
        }
        if (this.sp == 25) {
            this.sptyp = 1;
            this.spart = 11;
        }
        if (this.sp == 24) {
            this.sptyp = 1;
            this.spart = 12;
        }
        if (this.sp == 43) {
            this.sptyp = 1;
            this.spart = 13;
        }
        if (this.sp == 45) {
            this.sptyp = 1;
            this.spart = 14;
        }
        if (this.sp == 26) {
            this.sptyp = 1;
            this.spart = 15;
        }
        if (this.sp == 27) {
            this.sptyp = 2;
            this.spart = 0;
        }
        if (this.sp == 28) {
            this.sptyp = 2;
            this.spart = 1;
        }
        if (this.sp == 41) {
            this.sptyp = 2;
            this.spart = 2;
        }
        if (this.sp == 44) {
            this.sptyp = 2;
            this.spart = 3;
        }
        if (this.sp == 52) {
            this.sptyp = 2;
            this.spart = 4;
        }
        if (this.sp == 53) {
            this.sptyp = 2;
            this.spart = 5;
        }
        if (this.sp == 30 || this.sp == 32 || this.sp == 54) {
            this.sptyp = 3;
            this.spart = 0;
        }
        if (this.sp == 31) {
            this.sptyp = 4;
            this.spart = 0;
        }
        if (this.sp == 55) {
            this.sptyp = 5;
            this.spart = 0;
        }
        if (this.sp == 56) {
            this.sptyp = 5;
            this.spart = 1;
        }
        if (this.sp == 57) {
            this.sptyp = 5;
            this.spart = 2;
        }
        if (this.sp == 58) {
            this.sptyp = 5;
            this.spart = 3;
        }
        if (this.sp == 59) {
            this.sptyp = 5;
            this.spart = 4;
        }
        if (this.sp == 60) {
            this.sptyp = 5;
            this.spart = 5;
        }
        if (this.sp == 61) {
            this.sptyp = 5;
            this.spart = 6;
        }
        if (this.sp == 62) {
            this.sptyp = 5;
            this.spart = 7;
        }
        if (this.sp == 63) {
            this.sptyp = 5;
            this.spart = 8;
        }
        if (this.sp == 64) {
            this.sptyp = 5;
            this.spart = 9;
        }
        if (this.sp == 65) {
            this.sptyp = 5;
            this.spart = 10;
        }
        if (this.sp == 66) {
            this.fgen = bl ? this.co[this.esp].srz : 0;
            this.pwd = this.co[this.esp].srx;
            this.phd = this.co[this.esp].sry;
            this.pgen = false;
            this.sptyp = 6;
        }
        if (this.sptyp == 0) {
            this.partroads();
            this.part.show();
        }
        if (this.sptyp == 1) {
            this.partramps();
            this.part.show();
        }
        if (this.sptyp == 2) {
            this.partobst();
            this.part.show();
        }
        if (this.sptyp == 5) {
            this.partrees();
            this.part.show();
        }
        this.ptyp.select(this.sptyp);
        this.part.select(this.spart);
    }

    public void partrees() {
        this.part.removeAll();
        this.part.add(this.rd, "Tree 1");
        this.part.add(this.rd, "Tree 2");
        this.part.add(this.rd, "Tree 3");
        this.part.add(this.rd, "Tree 4");
        this.part.add(this.rd, "Tree 5");
        this.part.add(this.rd, "Palm Tree 1");
        this.part.add(this.rd, "Palm Tree 2");
        this.part.add(this.rd, "Palm Tree 3");
        this.part.add(this.rd, "Cactus 1");
        this.part.add(this.rd, "Cactus 2");
        this.part.add(this.rd, "Cactus 3");
    }

    public void partroads() {
        this.part.removeAll();
        this.part.add(this.rd, "NormalRoad");
        this.part.add(this.rd, "NormalRoad Turn");
        this.part.add(this.rd, "NormalRoad End");
        this.part.add(this.rd, "NormalRoad TwistedLeft");
        this.part.add(this.rd, "NormalRoad TwistedRight");
        this.part.add(this.rd, "NormalRoad Edged");
        this.part.add(this.rd, "NormalRoad-Raised Ramp");
        this.part.add(this.rd, "NormalRoad Raised");
        this.part.add(this.rd, "Normal-Off-Road Blend");
        this.part.add(this.rd, "OffRoad");
        this.part.add(this.rd, "OffRoad Turn");
        this.part.add(this.rd, "OffRoad End");
        this.part.add(this.rd, "OffRoad BumpyGreen");
        this.part.add(this.rd, "OffRoad-BumpySides Start");
        this.part.add(this.rd, "OffRoad BumpySides");
        this.part.add(this.rd, "Off-Halfpipe-Road Blend");
        this.part.add(this.rd, "HalfpipeRoad");
        this.part.add(this.rd, "HalfpipeRoad Turn");
        this.part.add(this.rd, "HalfpipeRoad-Ramp Filler");
        this.part.add(this.rd, "Halfpipe-Normal-Road Blend");
        this.part.add(this.rd, "Rollercoaster Start/End");
        this.part.add(this.rd, "Rollercoaster Road1");
        this.part.add(this.rd, "Rollercoaster Road2");
        this.part.add(this.rd, "Rollercoaster Road3");
        this.part.add(this.rd, "Rollercoaster Road4");
        this.part.add(this.rd, "Rollercoaster Road5");
    }

    public void partramps() {
        this.part.removeAll();
        this.part.add(this.rd, "Basic Ramp");
        this.part.add(this.rd, "Two-Way Ramp");
        this.part.add(this.rd, "Two-Way High-Low Ramp");
        this.part.add(this.rd, "Small Ramp");
        this.part.add(this.rd, "Crash Ramp");
        this.part.add(this.rd, "Big-Takeoff Ramp");
        this.part.add(this.rd, "Landing Ramp");
        this.part.add(this.rd, "Tunnel Side Ramp");
        this.part.add(this.rd, "Speed Ramp");
        this.part.add(this.rd, "Launch Pad Ramp");
        this.part.add(this.rd, "Offroad Bump Ramp");
        this.part.add(this.rd, "Offroad Ramp");
        this.part.add(this.rd, "Offroad Big Ramp");
        this.part.add(this.rd, "Offroad Hill Ramp");
        this.part.add(this.rd, "Offroad Big Hill Ramp");
        this.part.add(this.rd, "Halfpipe");
    }

    public void partobst() {
        this.part.removeAll();
        this.part.add(this.rd, "Spiky Pillars");
        this.part.add(this.rd, "Rail Doorway");
        this.part.add(this.rd, "The Net");
        this.part.add(this.rd, "Bump Slide");
        this.part.add(this.rd, "Offroad Dirt-Pile 1");
        this.part.add(this.rd, "Offroad Dirt-Pile 2");
    }

    @Override
    public void init() {
        this.setBackground(new Color(0, 0, 0));
        this.offImage = this.createImage(800, 550);
        if (this.offImage != null) {
            this.rd = (Graphics2D)this.offImage.getGraphics();
        }
        this.rd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        this.setLayout(null);
        this.slstage.setFont(new Font("Arial", 1, 13));
        this.slstage.add(this.rd, "Select a Stage...         ");
        this.slstage.setForeground(new Color(63, 80, 110));
        this.slstage.setBackground(new Color(209, 217, 230));
        this.srch.setFont(new Font("Arial", 1, 12));
        this.srch.setBackground(new Color(255, 255, 255));
        this.srch.setForeground(new Color(0, 0, 0));
        this.strtyp.setFont(new Font("Arial", 1, 12));
        this.strtyp.add(this.rd, "NormalRoad");
        this.strtyp.add(this.rd, "OffRoad");
        this.strtyp.setBackground(new Color(63, 80, 110));
        this.strtyp.setForeground(new Color(209, 217, 230));
        this.ptyp.setFont(new Font("Arial", 1, 12));
        this.ptyp.add(this.rd, "Roads");
        this.ptyp.add(this.rd, "Ramps");
        this.ptyp.add(this.rd, "Obstacles");
        this.ptyp.add(this.rd, "Checkpoint");
        this.ptyp.add(this.rd, "Fixing Hoop");
        this.ptyp.add(this.rd, "Trees");
        this.ptyp.add(this.rd, "Ground Pile");
        this.ptyp.setBackground(new Color(63, 80, 110));
        this.ptyp.setForeground(new Color(209, 217, 230));
        this.part.setFont(new Font("Arial", 1, 12));
        this.part.add(this.rd, "Halfpipe-Normal-Road Blend");
        this.part.setBackground(new Color(63, 80, 110));
        this.part.setForeground(new Color(209, 217, 230));
        this.fixh.setFont(new Font("Arial", 1, 12));
        this.fixh.setBackground(new Color(255, 255, 255));
        this.fixh.setForeground(new Color(0, 0, 0));
        this.mgen.setFont(new Font("Arial", 1, 12));
        this.mgen.setBackground(new Color(255, 255, 255));
        this.mgen.setForeground(new Color(0, 0, 0));
        this.pfog.setFont(new Font("Arial", 1, 12));
        this.pfog.setBackground(new Color(225, 225, 225));
        this.pfog.setForeground(new Color(0, 0, 0));
        this.nlaps.setFont(new Font("Arial", 1, 12));
        for (int i = 0; i < 15; ++i) {
            this.nlaps.add(this.rd, " " + (i + 1) + " ");
        }
        this.nlaps.setBackground(new Color(63, 80, 110));
        this.nlaps.setForeground(new Color(209, 217, 230));
        this.tracks.setFont(new Font("Arial", 1, 12));
        this.tracks.add(this.rd, "Select MOD Track");
        this.tracks.setForeground(new Color(63, 80, 110));
        this.tracks.setBackground(new Color(209, 217, 230));
        this.witho.setFont(new Font("Arial", 1, 12));
        this.witho.add(this.rd, "With other cars");
        this.witho.add(this.rd, "Alone");
        this.witho.setBackground(new Color(63, 80, 110));
        this.witho.setForeground(new Color(209, 217, 230));
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
        this.pubitem.add(this.rd, "Account Stages");
        this.pubitem.setBackground(new Color(209, 217, 230));
        this.pubitem.setForeground(new Color(63, 80, 110));
        this.add(this.tnick);
        this.add(this.tpass);
        this.add(this.srch);
        this.add(this.fixh);
        this.add(this.mgen);
        this.add(this.pfog);
        this.hidefields();
    }

    public void hidefields() {
        this.pubtyp.hide();
        this.pubitem.hide();
        this.tpass.hide();
        this.tnick.hide();
        this.witho.hide();
        this.strtyp.hide();
        this.srch.hide();
        this.slstage.hide();
        this.tracks.hide();
        this.nlaps.hide();
        this.pfog.hide();
        this.fixh.hide();
        this.mgen.hide();
        this.ptyp.hide();
        this.part.hide();
    }

    public void movefield(Component component, int n, int n2, int n3, int n4) {
        if (component.getX() != (n += this.apx) || component.getY() != (n2 += this.apy) || component.getWidth() != n3 || component.getHeight() != n4) {
            component.setBounds(n, n2, n3, n4);
        }
    }

    public void drawms() {
        boolean bl = false;
        if (this.pubtyp.draw(this.rd, this.xm, this.ym, this.mousdr, 550, false)) {
            bl = true;
        }
        if (this.pubitem.draw(this.rd, this.xm, this.ym, this.mousdr, 550, false)) {
            bl = true;
        }
        if (this.slstage.draw(this.rd, this.xm, this.ym, this.mousdr, 550, false)) {
            bl = true;
        }
        if (this.strtyp.draw(this.rd, this.xm, this.ym, this.mousdr, 550, false)) {
            bl = true;
        }
        int n = 0;
        if (this.preop) {
            n = -1000;
        }
        if (this.part.draw(this.rd, this.xm, this.ym + n, this.mousdr && !this.preop, 550, false)) {
            bl = true;
        }
        if (this.ptyp.draw(this.rd, this.xm, this.ym, this.mousdr, 550, false)) {
            bl = true;
            this.preop = true;
        } else {
            this.preop = false;
        }
        if (this.nlaps.draw(this.rd, this.xm, this.ym, this.mousdr, 550, true)) {
            bl = true;
        }
        if (this.tracks.draw(this.rd, this.xm, this.ym, this.mousdr, 550, true)) {
            bl = true;
        }
        if (this.witho.draw(this.rd, this.xm, this.ym, this.mousdr, 550, true)) {
            bl = true;
        }
        if (bl) {
            this.mouses = 0;
        }
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
        this.apx = this.getWidth() / 2 - 400;
        this.apy = this.getHeight() / 2 - 275;
        graphics.drawImage(this.offImage, this.apx, this.apy, this);
    }

    @Override
    public void update(Graphics graphics) {
        this.paint(graphics);
    }

    @Override
    public boolean mouseUp(Event event, int n, int n2) {
        this.mousdr = false;
        this.xm = n - this.apx;
        this.ym = n2 - this.apy;
        if (this.mouses == 1) {
            this.mouses = -1;
        }
        if (this.onbtgame) {
            Madness.game();
        }
        return false;
    }

    @Override
    public boolean mouseDown(Event event, int n, int n2) {
        this.mousdr = true;
        this.xm = n - this.apx;
        this.ym = n2 - this.apy;
        this.mouses = 1;
        this.requestFocus();
        this.focuson = true;
        return false;
    }

    @Override
    public boolean mouseMove(Event event, int n, int n2) {
        this.xm = n - this.apx;
        this.ym = n2 - this.apy;
        if (this.xm > 620 && this.xm < 774 && this.ym > 0 && this.ym < 23) {
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
    public boolean keyDown(Event event, int n) {
        if (this.focuson) {
            if (n == 42 || n == 10 || n == 56 || n == 119 || n == 87 || n == 43 || n == 61) {
                this.zoomi = true;
            }
            if (n == 47 || n == 8 || n == 50 || n == 115 || n == 83 || n == 45) {
                this.zoomo = true;
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
        if (n == 42 || n == 10 || n == 56 || n == 119 || n == 87 || n == 43 || n == 61) {
            this.zoomi = false;
        }
        if (n == 47 || n == 8 || n == 50 || n == 115 || n == 83 || n == 45) {
            this.zoomo = false;
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

    public void loadbase() {
        String[] stringArray = new String[]{"road", "froad", "twister2", "twister1", "turn", "offroad", "bumproad", "offturn", "nroad", "nturn", "roblend", "noblend", "rnblend", "roadend", "offroadend", "hpground", "ramp30", "cramp35", "dramp15", "dhilo15", "slide10", "takeoff", "sramp22", "offbump", "offramp", "sofframp", "halfpipe", "spikes", "rail", "thewall", "checkpoint", "fixpoint", "offcheckpoint", "sideoff", "bsideoff", "uprise", "riseroad", "sroad", "soffroad", "tside", "launchpad", "thenet", "speedramp", "offhill", "slider", "uphill", "roll1", "roll2", "roll3", "roll4", "roll5", "roll6", "opile1", "opile2", "aircheckpoint", "tree1", "tree2", "tree3", "tree4", "tree5", "tree6", "tree7", "tree8", "cac1", "cac2", "cac3"};
        try {
            File file = new File("" + Madness.fpath + "data/models.zip");
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            byte[] byArray = null;
            while (zipEntry != null) {
                int n;
                int n2 = -1;
                for (n = 0; n < 66; ++n) {
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
                    this.bco[n2] = new ContO(byArray, this.m, this.t);
                    for (int i = 0; i < this.bco[n2].npl; ++i) {
                        this.bco[n2].p[i].loadprojf();
                    }
                    if (n2 == 31) {
                        this.bco[n2].elec = true;
                    }
                }
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.close();
            this.bco[66] = new ContO((int)(10000.0 * Math.random()), (int)this.pwd, (int)this.phd, this.m, this.t, 0, 0, 0);
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Unable to load file 'data/models.zip'!\nError:\n" + exception, "Stage Maker", 1);
        }
        System.gc();
    }

    public void readstage(int n) {
        block85: {
            this.errd = 0;
            this.trackname = "";
            this.t.nt = 0;
            this.nob = 0;
            this.xnob = 0;
            this.cp.n = 0;
            this.cp.nsp = 0;
            this.cp.fn = 0;
            this.cp.haltall = false;
            this.cp.wasted = 0;
            this.cp.catchfin = 0;
            this.m.ground = 250;
            this.m.lightson = false;
            if (n == 0) {
                this.m.snap[0] = 0;
                this.m.snap[1] = 0;
                this.m.snap[2] = 0;
            }
            if (n == 3) {
                this.tstage = "";
                this.bstage = "";
            }
            String string = this.bstage;
            if (n == 1 || n == 2) {
                string = "" + this.tstage + "\r\n" + this.bstage + "";
            }
            int n2 = 0;
            int n3 = 100;
            int n4 = 0;
            int n5 = 100;
            boolean bl = true;
            boolean bl2 = true;
            String string2 = "";
            try {
                String string3;
                DataInputStream dataInputStream = null;
                if (n == 3) {
                    File file = new File("" + Madness.fpath + "mystages/" + this.stagename + ".txt");
                    dataInputStream = new DataInputStream(new FileInputStream(file));
                    this.nundo = 0;
                } else {
                    dataInputStream = new DataInputStream(new ByteArrayInputStream(string.getBytes()));
                }
                while ((string3 = dataInputStream.readLine()) != null) {
                    int n6;
                    int n7;
                    int n8;
                    string2 = "" + string3.trim();
                    if (string2.startsWith("sky")) {
                        this.csky[0] = this.getint("sky", string2, 0);
                        this.csky[1] = this.getint("sky", string2, 1);
                        this.csky[2] = this.getint("sky", string2, 2);
                        this.m.setsky(this.csky[0], this.csky[1], this.csky[2]);
                        if (n == 3) {
                            this.tstage = this.tstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("ground")) {
                        this.cgrnd[0] = this.getint("ground", string2, 0);
                        this.cgrnd[1] = this.getint("ground", string2, 1);
                        this.cgrnd[2] = this.getint("ground", string2, 2);
                        this.m.setgrnd(this.cgrnd[0], this.cgrnd[1], this.cgrnd[2]);
                        if (n == 3) {
                            this.tstage = this.tstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("polys")) {
                        this.m.setpolys(this.getint("polys", string2, 0), this.getint("polys", string2, 1), this.getint("polys", string2, 2));
                        if (n == 3) {
                            this.tstage = this.tstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("fog")) {
                        this.cfade[0] = this.getint("fog", string2, 0);
                        this.cfade[1] = this.getint("fog", string2, 1);
                        this.cfade[2] = this.getint("fog", string2, 2);
                        this.m.setfade(this.cfade[0], this.cfade[1], this.cfade[2]);
                        if (n == 3) {
                            this.tstage = this.tstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("texture")) {
                        this.texture[0] = this.getint("texture", string2, 0);
                        this.texture[1] = this.getint("texture", string2, 1);
                        this.texture[2] = this.getint("texture", string2, 2);
                        this.texture[3] = this.getint("texture", string2, 3);
                        this.m.setexture(this.texture[0], this.texture[1], this.texture[2], this.texture[3]);
                        if (n == 3) {
                            this.tstage = this.tstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("clouds")) {
                        this.cldd[0] = this.getint("clouds", string2, 0);
                        this.cldd[1] = this.getint("clouds", string2, 1);
                        this.cldd[2] = this.getint("clouds", string2, 2);
                        this.cldd[3] = this.getint("clouds", string2, 3);
                        this.cldd[4] = this.getint("clouds", string2, 4);
                        this.m.setcloads(this.cldd[0], this.cldd[1], this.cldd[2], this.cldd[3], this.cldd[4]);
                        if (n == 3) {
                            this.tstage = this.tstage + "" + string2 + "\r\n";
                        }
                    }
                    if (n != 2 && string2.startsWith("snap")) {
                        this.m.setsnap(this.getint("snap", string2, 0), this.getint("snap", string2, 1), this.getint("snap", string2, 2));
                        if (n == 3) {
                            this.tstage = this.tstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("density")) {
                        this.m.fogd = (this.getint("density", string2, 0) + 1) * 2 - 1;
                        if (this.m.fogd < 1) {
                            this.m.fogd = 1;
                        }
                        if (this.m.fogd > 30) {
                            this.m.fogd = 30;
                        }
                        if (n == 3) {
                            this.tstage = this.tstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("mountains")) {
                        this.m.mgen = this.getint("mountains", string2, 0);
                        if (n == 3) {
                            this.tstage = this.tstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("fadefrom")) {
                        this.m.fadfrom(this.getint("fadefrom", string2, 0));
                        this.origfade = this.m.fade[0];
                        if (n == 3) {
                            this.tstage = this.tstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("lightson")) {
                        this.m.lightson = true;
                        if (n == 3) {
                            this.tstage = this.tstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("nlaps")) {
                        this.cp.nlaps = this.getint("nlaps", string2, 0);
                        if (this.cp.nlaps < 1) {
                            this.cp.nlaps = 1;
                        }
                        if (this.cp.nlaps > 15) {
                            this.cp.nlaps = 15;
                        }
                        if (n == 3) {
                            this.tstage = this.tstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("soundtrack")) {
                        this.trackname = this.getstring("soundtrack", string2, 0);
                        this.trackvol = this.getint("soundtrack", string2, 1);
                        this.tracksize = this.getint("soundtrack", string2, 2);
                        if (n == 3) {
                            this.tstage = this.tstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("set")) {
                        int n9 = this.getint("set", string2, 0);
                        if (n9 >= 10 && n9 <= 25) {
                            this.m.loadnew = true;
                        }
                        this.co[this.nob] = new ContO(this.bco[n9 -= 10], this.getint("set", string2, 1), this.m.ground - this.bco[n9].grat, this.getint("set", string2, 2), this.getint("set", string2, 3));
                        this.co[this.nob].roofat = this.getint("set", string2, 3);
                        this.co[this.nob].colok = n9;
                        if (string2.indexOf(")p") != -1) {
                            this.cp.x[this.cp.n] = this.getint("chk", string2, 1);
                            this.cp.z[this.cp.n] = this.getint("chk", string2, 2);
                            this.cp.y[this.cp.n] = 0;
                            this.cp.typ[this.cp.n] = 0;
                            if (string2.indexOf(")pt") != -1) {
                                this.cp.typ[this.cp.n] = -1;
                            }
                            if (string2.indexOf(")pr") != -1) {
                                this.cp.typ[this.cp.n] = -2;
                            }
                            if (string2.indexOf(")po") != -1) {
                                this.cp.typ[this.cp.n] = -3;
                            }
                            if (string2.indexOf(")ph") != -1) {
                                this.cp.typ[this.cp.n] = -4;
                            }
                            ++this.cp.n;
                        }
                        ++this.xnob;
                        ++this.nob;
                        if (n == 3) {
                            if (bl2) {
                                this.bstage = this.bstage + "\r\n";
                                bl2 = false;
                            }
                            this.bstage = this.bstage + "" + string2 + "\r\n";
                        }
                        if (this.m.loadnew) {
                            this.m.loadnew = false;
                        }
                    }
                    if (string2.startsWith("chk")) {
                        int n10 = this.getint("chk", string2, 0);
                        n8 = this.m.ground - this.bco[n10 -= 10].grat;
                        if (n10 == 54) {
                            n8 = this.getint("chk", string2, 4);
                        }
                        this.co[this.nob] = new ContO(this.bco[n10], this.getint("chk", string2, 1), n8, this.getint("chk", string2, 2), this.getint("chk", string2, 3));
                        this.co[this.nob].roofat = this.getint("chk", string2, 3);
                        this.co[this.nob].colok = n10;
                        this.cp.x[this.cp.n] = this.getint("chk", string2, 1);
                        this.cp.z[this.cp.n] = this.getint("chk", string2, 2);
                        this.cp.y[this.cp.n] = n8;
                        this.cp.typ[this.cp.n] = this.getint("chk", string2, 3) == 0 ? 1 : 2;
                        this.cp.pcs = this.cp.n++;
                        this.co[this.nob].checkpoint = this.cp.nsp + 1;
                        if (string2.indexOf(")r") != -1) {
                            this.co[this.nob].wh = this.cp.nsp + 1;
                        }
                        ++this.cp.nsp;
                        ++this.xnob;
                        ++this.nob;
                        if (n == 3) {
                            if (bl2) {
                                this.bstage = this.bstage + "\r\n";
                                bl2 = false;
                            }
                            this.bstage = this.bstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("fix")) {
                        int n11 = this.getint("fix", string2, 0);
                        this.co[this.nob] = new ContO(this.bco[n11 -= 10], this.getint("fix", string2, 1), this.getint("fix", string2, 3), this.getint("fix", string2, 2), this.getint("fix", string2, 4));
                        this.co[this.nob].roofat = this.getint("fix", string2, 4);
                        this.co[this.nob].colok = n11;
                        this.cp.fx[this.cp.fn] = this.getint("fix", string2, 1);
                        this.cp.fz[this.cp.fn] = this.getint("fix", string2, 2);
                        this.cp.fy[this.cp.fn] = this.getint("fix", string2, 3);
                        this.co[this.nob].elec = true;
                        if (this.getint("fix", string2, 4) != 0) {
                            this.cp.roted[this.cp.fn] = true;
                            this.co[this.nob].roted = true;
                        } else {
                            this.cp.roted[this.cp.fn] = false;
                        }
                        this.cp.special[this.cp.fn] = string2.indexOf(")s") != -1;
                        ++this.cp.fn;
                        ++this.xnob;
                        ++this.nob;
                        if (n == 3) {
                            if (bl2) {
                                this.bstage = this.bstage + "\r\n";
                                bl2 = false;
                            }
                            this.bstage = this.bstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("pile")) {
                        this.co[this.nob] = new ContO(this.getint("pile", string2, 0), this.getint("pile", string2, 1), this.getint("pile", string2, 2), this.m, this.t, this.getint("pile", string2, 3), this.getint("pile", string2, 4), this.m.ground);
                        this.co[this.nob].srz = this.getint("pile", string2, 0);
                        this.co[this.nob].srx = this.getint("pile", string2, 1);
                        this.co[this.nob].sry = this.getint("pile", string2, 2);
                        this.co[this.nob].colok = 66;
                        ++this.xnob;
                        ++this.nob;
                        if (n == 3) {
                            if (bl2) {
                                this.bstage = this.bstage + "\r\n";
                                bl2 = false;
                            }
                            this.bstage = this.bstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("maxr")) {
                        int n12 = this.getint("maxr", string2, 0);
                        n2 = n8 = this.getint("maxr", string2, 1);
                        n7 = this.getint("maxr", string2, 2);
                        for (n6 = 0; n6 < n12; ++n6) {
                            this.co[this.nob] = new ContO(this.bco[29], n8, this.m.ground - this.bco[29].grat, n6 * 4800 + n7, 0);
                            if (n == 0) {
                                ++this.xnob;
                                continue;
                            }
                            ++this.nob;
                        }
                        if (n == 3) {
                            if (bl) {
                                this.bstage = this.bstage + "\r\n";
                                bl = false;
                            }
                            this.bstage = this.bstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("maxl")) {
                        int n13 = this.getint("maxl", string2, 0);
                        n3 = n8 = this.getint("maxl", string2, 1);
                        n7 = this.getint("maxl", string2, 2);
                        for (n6 = 0; n6 < n13; ++n6) {
                            this.co[this.nob] = new ContO(this.bco[29], n8, this.m.ground - this.bco[29].grat, n6 * 4800 + n7, 180);
                            if (n == 0) {
                                ++this.xnob;
                                continue;
                            }
                            ++this.nob;
                        }
                        if (n == 3) {
                            if (bl) {
                                this.bstage = this.bstage + "\r\n";
                                bl = false;
                            }
                            this.bstage = this.bstage + "" + string2 + "\r\n";
                        }
                    }
                    if (string2.startsWith("maxt")) {
                        int n14 = this.getint("maxt", string2, 0);
                        n4 = n8 = this.getint("maxt", string2, 1);
                        n7 = this.getint("maxt", string2, 2);
                        for (n6 = 0; n6 < n14; ++n6) {
                            this.co[this.nob] = new ContO(this.bco[29], n6 * 4800 + n7, this.m.ground - this.bco[29].grat, n8, 90);
                            if (n == 0) {
                                ++this.xnob;
                                continue;
                            }
                            ++this.nob;
                        }
                        if (n == 3) {
                            if (bl) {
                                this.bstage = this.bstage + "\r\n";
                                bl = false;
                            }
                            this.bstage = this.bstage + "" + string2 + "\r\n";
                        }
                    }
                    if (!string2.startsWith("maxb")) continue;
                    int n15 = this.getint("maxb", string2, 0);
                    n5 = n8 = this.getint("maxb", string2, 1);
                    n7 = this.getint("maxb", string2, 2);
                    for (n6 = 0; n6 < n15; ++n6) {
                        this.co[this.nob] = new ContO(this.bco[29], n6 * 4800 + n7, this.m.ground - this.bco[29].grat, n8, -90);
                        if (n == 0) {
                            ++this.xnob;
                            continue;
                        }
                        ++this.nob;
                    }
                    if (n != 3) continue;
                    if (bl) {
                        this.bstage = this.bstage + "\r\n";
                        bl = false;
                    }
                    this.bstage = this.bstage + "" + string2 + "\r\n";
                }
                dataInputStream.close();
                this.m.newpolys(n3, n2 - n3, n5, n4 - n5, this.t, this.nob);
                this.m.newclouds(n3, n2, n5, n4);
                this.m.newmountains(n3, n2, n5, n4);
                this.m.newstars();
            }
            catch (Exception exception) {
                System.out.println("Error in stage " + this.stagename);
                System.out.println("" + exception);
                System.out.println("At line: " + string2);
                this.errd = 6;
                if (this.cp.fn >= 5) {
                    this.errd = 5;
                }
                if (this.t.nt >= 6700) {
                    this.errd = 1;
                }
                if (this.cp.n >= 140) {
                    this.errd = 2;
                }
                if (this.nob < 601) break block85;
                this.errd = 4;
            }
        }
        if (this.m.nrw * this.m.ncl >= 16000) {
            this.errd = 3;
        }
        if (this.xnob >= 602) {
            this.errd = 4;
        }
        if (n == 3 && this.bstage.indexOf("set(47,0,0,0)") == -1 && this.bstage.indexOf("set(48,0,0,0)") == -1) {
            this.bstage = this.bstage + "set(47,0,0,0)\r\n";
        }
    }

    public void newstage() {
        if (!this.srch.getText().equals("")) {
            File file = new File("" + Madness.fpath + "mystages/" + this.srch.getText() + ".txt");
            if (!file.exists()) {
                this.stagename = this.srch.getText();
                this.tstage = "snap(0,0,0)\r\nsky(191,215,255)\r\nclouds(255,255,255,5,-1000)\r\nfog(195,207,230)\r\nground(192,194,202)\r\ntexture(0,0,0,50)\r\nfadefrom(5000)\r\ndensity(5)\r\nmountains(" + (int)(Math.random() * 100000.0) + ")\r\nnlaps(5)\r\n\r\n";
                this.bstage = this.strtyp.getSelectedIndex() == 1 ? "set(48,0,0,0)\r\n" : "set(47,0,0,0)\r\n";
                this.bstage = this.bstage + "\r\nmaxl(3,-7200,-4800)\r\nmaxb(3,-7200,-4800)\r\nmaxr(3,7200,-4800)\r\nmaxt(3,7200,-4800)\r\n";
                this.savefile();
                this.strtyp.hide();
                this.srch.hide();
                this.sfase = 0;
                this.tabed = -2;
            } else {
                JOptionPane.showMessageDialog(null, "A stage with that name already exists, please choose another name!", "Stage Maker", 1);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a stage name first!", "Stage Maker", 1);
        }
    }

    public void sortop() {
        this.tstage = "snap(" + this.m.snap[0] + "," + this.m.snap[1] + "," + this.m.snap[2] + ")\r\nsky(" + this.csky[0] + "," + this.csky[1] + "," + this.csky[2] + ")\r\nfog(" + this.cfade[0] + "," + this.cfade[1] + "," + this.cfade[2] + ")\r\nclouds(" + this.cldd[0] + "," + this.cldd[1] + "," + this.cldd[2] + "," + this.cldd[3] + "," + this.cldd[4] + ")\r\nground(" + this.cgrnd[0] + "," + this.cgrnd[1] + "," + this.cgrnd[2] + ")\r\ntexture(" + this.texture[0] + "," + this.texture[1] + "," + this.texture[2] + "," + this.texture[3] + ")\r\nfadefrom(" + this.origfade + ")\r\ndensity(" + ((this.m.fogd + 1) / 2 - 1) + ")\r\nmountains(" + this.m.mgen + ")\r\nnlaps(" + this.cp.nlaps + ")\r\n";
        if (!this.trackname.equals("")) {
            this.tstage = this.tstage + "soundtrack(" + this.trackname + "," + this.trackvol + "," + this.tracksize + ")\r\n";
        }
        for (int i = 0; i < 3; ++i) {
            this.snap[i] = (int)((float)this.m.snap[i] / 1.2f + 50.0f);
        }
        if (this.snap[0] + this.snap[1] + this.snap[2] <= 110) {
            this.tstage = this.tstage + "lightson()\r\n";
        }
        this.tstage = this.tstage + "\r\n";
    }

    public void sortstage() {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int[] nArray = new int[this.nob * 2];
        int[] nArray2 = new int[this.nob * 2];
        for (n9 = 0; n9 < this.nob; ++n9) {
            nArray[n9] = 0;
        }
        n9 = 0;
        int n10 = 0;
        nArray2[n10] = 0;
        ++n10;
        boolean bl = false;
        int n11 = 0;
        while (!bl) {
            int[] nArray3;
            int[] nArray4 = new int[]{this.co[n9].x + this.atp[this.co[n9].colok][0], this.co[n9].x + this.atp[this.co[n9].colok][2]};
            int[] nArray5 = new int[]{this.co[n9].z + this.atp[this.co[n9].colok][1], this.co[n9].z + this.atp[this.co[n9].colok][3]};
            n8 = this.co[n9].roofat;
            if (this.co[n9].colok == 2) {
                n8 += 30;
            }
            if (this.co[n9].colok == 3) {
                n8 -= 30;
            }
            if (this.co[n9].colok == 15) {
                n8 -= 90;
            }
            if (this.co[n9].colok == 20) {
                n8 -= 180;
            }
            if (this.co[n9].colok == 26) {
                n8 -= 90;
            }
            this.rot(nArray4, nArray5, this.co[n9].x, this.co[n9].z, n8, 2);
            n7 = -1;
            n6 = -1;
            if (n11 != 0) {
                for (n5 = 0; n5 < this.nob; ++n5) {
                    n4 = 0;
                    if (n10 == 2 && n5 == 0) {
                        n4 = 1;
                    }
                    if (n9 == n5 || n4 != 0 || nArray[n5] != 0 || this.co[n5].colok > 14 && this.co[n5].colok < 33 || this.co[n5].colok >= 39 && this.co[n5].colok < 46 || this.co[n5].colok >= 52) continue;
                    int n12 = 0;
                    if (this.co[n5].colok != 2 && this.co[n5].colok != 3 && this.co[n5].colok != 4 && this.co[n5].colok != 7 && this.co[n5].colok != 9) {
                        if (n11 == 1 && this.co[n5].z > this.co[n9].z && Math.abs(this.co[n5].x - this.co[n9].x) < 1000 && (this.co[n5].roofat == 180 || this.co[n5].roofat == 0)) {
                            n12 = 1;
                        }
                        if (n11 == 2 && this.co[n5].z < this.co[n9].z && Math.abs(this.co[n5].x - this.co[n9].x) < 1000 && (this.co[n5].roofat == 180 || this.co[n5].roofat == 0)) {
                            n12 = 1;
                        }
                        if (n11 == 3 && this.co[n5].x > this.co[n9].x && Math.abs(this.co[n5].z - this.co[n9].z) < 1000 && (this.co[n5].roofat == 90 || this.co[n5].roofat == -90)) {
                            n12 = 1;
                        }
                        if (n11 == 4 && this.co[n5].x < this.co[n9].x && Math.abs(this.co[n5].z - this.co[n9].z) < 1000 && (this.co[n5].roofat == 90 || this.co[n5].roofat == -90)) {
                            n12 = 1;
                        }
                    } else {
                        n12 = 2;
                    }
                    if (n12 == 0) continue;
                    nArray3 = new int[]{this.co[n5].x + this.atp[this.co[n5].colok][0], this.co[n5].x + this.atp[this.co[n5].colok][2]};
                    int[] nArray6 = new int[]{this.co[n5].z + this.atp[this.co[n5].colok][1], this.co[n5].z + this.atp[this.co[n5].colok][3]};
                    n8 = this.co[n5].roofat;
                    if (this.co[n5].colok == 2) {
                        n8 += 30;
                    }
                    if (this.co[n5].colok == 3) {
                        n8 -= 30;
                    }
                    if (this.co[n5].colok == 15) {
                        n8 -= 90;
                    }
                    if (this.co[n5].colok == 20) {
                        n8 -= 180;
                    }
                    if (this.co[n5].colok == 26) {
                        n8 -= 90;
                    }
                    this.rot(nArray3, nArray6, this.co[n5].x, this.co[n5].z, n8, 2);
                    n3 = 0;
                    if (!(n5 == 0 || (n3 = this.pyn(nArray3[0], nArray4[0], nArray6[0], nArray5[0])) < 0 || n3 >= 100 && n12 == 2 || n3 >= n7 && n7 != -1)) {
                        n7 = n3;
                        n6 = n5;
                    }
                    if (!((n3 = this.pyn(nArray3[1], nArray4[0], nArray6[1], nArray5[0])) < 0 || n3 >= 100 && n12 == 2 || n3 >= n7 && n7 != -1)) {
                        n7 = n3;
                        n6 = n5;
                    }
                    if (n9 == 0) continue;
                    if (n5 != 0 && (n3 = this.pyn(nArray3[0], nArray4[1], nArray6[0], nArray5[1])) >= 0 && (n3 < 100 || n12 != 2) && n3 < n7) {
                        n7 = n3;
                        n6 = n5;
                    }
                    if ((n3 = this.pyn(nArray3[1], nArray4[1], nArray6[1], nArray5[1])) < 0 || n3 >= 100 && n12 == 2 || n3 >= n7) continue;
                    n7 = n3;
                    n6 = n5;
                }
            }
            if (n6 == -1) {
                for (n5 = 0; n5 < this.nob; ++n5) {
                    n4 = 0;
                    if (n10 == 2 && n5 == 0) {
                        n4 = 1;
                    }
                    if (n9 == n5 || n4 != 0 || nArray[n5] != 0 || this.co[n5].colok > 14 && this.co[n5].colok < 33 || this.co[n5].colok >= 39 && this.co[n5].colok < 46 || this.co[n5].colok >= 52) continue;
                    int[] nArray7 = new int[]{this.co[n5].x + this.atp[this.co[n5].colok][0], this.co[n5].x + this.atp[this.co[n5].colok][2]};
                    nArray3 = new int[]{this.co[n5].z + this.atp[this.co[n5].colok][1], this.co[n5].z + this.atp[this.co[n5].colok][3]};
                    n8 = this.co[n5].roofat;
                    if (this.co[n5].colok == 2) {
                        n8 += 30;
                    }
                    if (this.co[n5].colok == 3) {
                        n8 -= 30;
                    }
                    if (this.co[n5].colok == 15) {
                        n8 -= 90;
                    }
                    if (this.co[n5].colok == 20) {
                        n8 -= 180;
                    }
                    if (this.co[n5].colok == 26) {
                        n8 -= 90;
                    }
                    this.rot(nArray7, nArray3, this.co[n5].x, this.co[n5].z, n8, 2);
                    int n13 = 0;
                    if (n5 != 0 && (n13 = this.pyn(nArray7[0], nArray4[0], nArray3[0], nArray5[0])) >= 0 && (n13 < n7 || n7 == -1)) {
                        n7 = n13;
                        n6 = n5;
                    }
                    if ((n13 = this.pyn(nArray7[1], nArray4[0], nArray3[1], nArray5[0])) >= 0 && (n13 < n7 || n7 == -1)) {
                        n7 = n13;
                        n6 = n5;
                    }
                    if (n9 == 0) continue;
                    if (n5 != 0 && (n13 = this.pyn(nArray7[0], nArray4[1], nArray3[0], nArray5[1])) >= 0 && n13 < n7) {
                        n7 = n13;
                        n6 = n5;
                    }
                    if ((n13 = this.pyn(nArray7[1], nArray4[1], nArray3[1], nArray5[1])) < 0 || n13 >= n7) continue;
                    n7 = n13;
                    n6 = n5;
                }
            }
            if (n6 != -1) {
                n11 = 0;
                if (this.co[n6].colok != 2 && this.co[n6].colok != 3 && this.co[n6].colok != 4 && this.co[n6].colok != 7 && this.co[n6].colok != 9) {
                    if ((this.co[n6].roofat == 180 || this.co[n6].roofat == 0) && this.co[n6].z > this.co[n9].z) {
                        n11 = 1;
                    }
                    if ((this.co[n6].roofat == 180 || this.co[n6].roofat == 0) && this.co[n6].z < this.co[n9].z) {
                        n11 = 2;
                    }
                    if ((this.co[n6].roofat == 90 || this.co[n6].roofat == -90) && this.co[n6].x > this.co[n9].x) {
                        n11 = 3;
                    }
                    if ((this.co[n6].roofat == 90 || this.co[n6].roofat == -90) && this.co[n6].x < this.co[n9].x) {
                        n11 = 4;
                    }
                }
                nArray[n6] = this.co[n6].colok == 4 || this.co[n6].colok == 7 || this.co[n6].colok == 9 ? 2 : 1;
                if (this.co[n6].colok >= 46 && this.co[n6].colok <= 51) {
                    nArray[n6] = 6;
                }
                n9 = n6;
                if (n6 == 0) {
                    nArray[0] = 1;
                    bl = true;
                    continue;
                }
                nArray2[n10] = n6;
                ++n10;
                continue;
            }
            nArray[0] = 1;
            bl = true;
        }
        for (n2 = 0; n2 < this.nob; ++n2) {
            if (nArray[n2] != 0 || this.co[n2].colok > 14 && this.co[n2].colok < 33 || this.co[n2].colok >= 39 && this.co[n2].colok < 46 || this.co[n2].colok >= 52) continue;
            nArray2[n10] = n2;
            ++n10;
        }
        for (n2 = 0; n2 < n10; ++n2) {
            if (this.co[nArray2[n2]].colok < 46 || this.co[nArray2[n2]].colok > 51) continue;
            for (int i = n2 + 1; i < n10; ++i) {
                n8 = this.pyn(this.co[nArray2[n2]].x, this.co[nArray2[i]].x, this.co[nArray2[n2]].z, this.co[nArray2[i]].z);
                if (n8 < 0 || this.co[nArray2[i]].colok >= 46 && this.co[nArray2[n2]].colok <= 51 || n8 >= (this.co[nArray2[n2]].maxR + this.co[nArray2[i]].maxR) / 100 * ((this.co[nArray2[n2]].maxR + this.co[nArray2[i]].maxR) / 100)) continue;
                n7 = nArray2[i];
                for (n6 = i; n6 > n2; --n6) {
                    nArray2[n6] = nArray2[n6 - 1];
                }
                nArray2[n2] = n7;
                nArray[nArray2[n2]] = 0;
                ++n2;
            }
        }
        n2 = 1;
        for (n = 0; n < this.cp.nsp; ++n) {
            for (n8 = 0; n8 < this.nob; ++n8) {
                if (this.co[n8].wh != n + 1 || this.co[n8].colok != 30 && this.co[n8].colok != 32 && this.co[n8].colok != 54) continue;
                n7 = -1;
                n6 = -1;
                for (n5 = n2; n5 < n10; ++n5) {
                    if (this.co[nArray2[n5]].colok == 30 || this.co[nArray2[n5]].colok == 32 || this.co[nArray2[n5]].colok == 54 || (n4 = this.pyn(this.co[n8].x, this.co[nArray2[n5]].x, this.co[n8].z, this.co[nArray2[n5]].z)) < 0 || n4 >= n7 && n7 != -1) continue;
                    n7 = n4;
                    n6 = n5;
                }
                if (n6 != -1) {
                    nArray[nArray2[n6]] = 0;
                    for (n5 = n10; n5 > n6; --n5) {
                        nArray2[n5] = nArray2[n5 - 1];
                    }
                    nArray2[n6 + 1] = n8;
                    n2 = n6 + 1;
                    ++n10;
                    continue;
                }
                nArray2[n10] = n8;
                n2 = n10++;
            }
        }
        for (n = 0; n < this.nob; ++n) {
            if (this.co[n].wh != 0 || this.co[n].colok != 30 && this.co[n].colok != 32 && this.co[n].colok != 54) continue;
            n8 = -1;
            n7 = -1;
            for (n6 = n2; n6 < n10; ++n6) {
                if (this.co[nArray2[n6]].colok == 30 || this.co[nArray2[n6]].colok == 32 || this.co[nArray2[n6]].colok == 54 || (n5 = this.pyn(this.co[n].x, this.co[nArray2[n6]].x, this.co[n].z, this.co[nArray2[n6]].z)) < 0 || n5 >= n8 && n8 != -1) continue;
                n8 = n5;
                n7 = n6;
            }
            if (n7 != -1) {
                nArray[nArray2[n7]] = 0;
                for (n6 = n10; n6 > n7; --n6) {
                    nArray2[n6] = nArray2[n6 - 1];
                }
                nArray2[n7 + 1] = n;
                ++n10;
                continue;
            }
            nArray2[n10] = n;
            ++n10;
        }
        for (n = 0; n < this.nob; ++n) {
            if (this.co[n].colok != 31) continue;
            n8 = -1;
            n7 = -1;
            for (n6 = 0; n6 < n10; ++n6) {
                n5 = this.pyn(this.co[n].x, this.co[nArray2[n6]].x, this.co[n].z, this.co[nArray2[n6]].z);
                if (n5 < 0 || n5 >= n8 && n8 != -1) continue;
                n8 = n5;
                n7 = n6;
            }
            if (n7 != -1) {
                for (n6 = n10; n6 > n7; --n6) {
                    nArray2[n6] = nArray2[n6 - 1];
                }
                nArray2[n7] = n;
                ++n10;
                continue;
            }
            nArray2[n10] = n;
            ++n10;
        }
        for (n = 0; n < this.nob; ++n) {
            if (this.co[n].colok != 15 && this.co[n].colok != 27 && this.co[n].colok != 28 && this.co[n].colok != 41 && this.co[n].colok != 44 && this.co[n].colok != 52 && this.co[n].colok != 53) continue;
            n8 = -1;
            for (n7 = 0; n7 < n10; ++n7) {
                if (this.co[nArray2[n7]].colok > 14 && this.co[nArray2[n7]].colok < 33 || this.co[nArray2[n7]].colok >= 39 || (n6 = this.pyn(this.co[n].x, this.co[nArray2[n7]].x, this.co[n].z, this.co[nArray2[n7]].z)) < 0 || n6 >= (this.co[n].maxR + this.co[nArray2[n7]].maxR) / 100 * ((this.co[n].maxR + this.co[nArray2[n7]].maxR) / 100)) continue;
                n8 = n7;
            }
            if (n8 != -1) {
                for (n7 = n10; n7 > n8; --n7) {
                    nArray2[n7] = nArray2[n7 - 1];
                }
                nArray2[n8 + 1] = n;
                ++n10;
                continue;
            }
            nArray2[n10] = n;
            ++n10;
        }
        for (n = 0; n < this.nob; ++n) {
            if ((this.co[n].colok < 16 || this.co[n].colok > 25) && this.co[n].colok != 40 && this.co[n].colok != 42 && this.co[n].colok != 43 && this.co[n].colok != 45) continue;
            n8 = -1;
            for (n7 = 0; n7 < n10; ++n7) {
                if (this.co[nArray2[n7]].colok > 14 && this.co[nArray2[n7]].colok < 33 || this.co[nArray2[n7]].colok >= 39 || (n6 = this.pyn(this.co[n].x, this.co[nArray2[n7]].x, this.co[n].z, this.co[nArray2[n7]].z)) < 0 || n6 >= (this.co[n].maxR + this.co[nArray2[n7]].maxR) / 100 * ((this.co[n].maxR + this.co[nArray2[n7]].maxR) / 100)) continue;
                if (nArray[nArray2[n7]] != 0) {
                    nArray[nArray2[n7]] = 0;
                    nArray[n] = this.co[n].colok != 20 ? 3 : 5;
                }
                n8 = n7;
            }
            if (n8 == -1) {
                // empty if block
            }
            if (n8 != -1) {
                for (n7 = n10; n7 > n8; --n7) {
                    nArray2[n7] = nArray2[n7 - 1];
                }
                nArray2[n8 + 1] = n;
                ++n10;
                continue;
            }
            nArray2[n10] = n;
            ++n10;
        }
        for (n = 0; n < this.nob; ++n) {
            if (this.co[n].colok != 26 && this.co[n].colok != 39) continue;
            n8 = 0;
            if (Math.random() > Math.random()) {
                n8 = 1;
                if (this.co[n].colok == 39) {
                    if (Math.random() > Math.random()) {
                        n8 = 0;
                    } else if (Math.random() > Math.random()) {
                        n8 = 0;
                    }
                }
            }
            n7 = -1;
            for (n6 = 0; n6 < n10; ++n6) {
                if (this.co[nArray2[n6]].colok > 14 && this.co[nArray2[n6]].colok < 33 || this.co[nArray2[n6]].colok >= 39 || (n5 = this.pyn(this.co[n].x, this.co[nArray2[n6]].x, this.co[n].z, this.co[nArray2[n6]].z)) < 0 || n5 >= (this.co[n].maxR + this.co[nArray2[n6]].maxR) / 100 * ((this.co[n].maxR + this.co[nArray2[n6]].maxR) / 100)) continue;
                n4 = 0;
                if (this.co[n].colok == 26) {
                    if (this.co[n].roofat == 90 && this.co[nArray2[n6]].x > this.co[n].x) {
                        n4 = 1;
                    }
                    if (this.co[n].roofat == -90 && this.co[nArray2[n6]].x < this.co[n].x) {
                        n4 = 1;
                    }
                    if (this.co[n].roofat == 0 && this.co[nArray2[n6]].z < this.co[n].z) {
                        n4 = 1;
                    }
                    if (this.co[n].roofat == 180 && this.co[nArray2[n6]].z > this.co[n].z) {
                        n4 = 1;
                    }
                }
                if (this.co[n].colok == 39) {
                    if (this.co[n].roofat == 90 && this.co[nArray2[n6]].z > this.co[n].z) {
                        n4 = 1;
                    }
                    if (this.co[n].roofat == -90 && this.co[nArray2[n6]].z < this.co[n].z) {
                        n4 = 1;
                    }
                    if (this.co[n].roofat == 0 && this.co[nArray2[n6]].x > this.co[n].x) {
                        n4 = 1;
                    }
                    if (this.co[n].roofat == 180 && this.co[nArray2[n6]].x < this.co[n].x) {
                        n4 = 1;
                    }
                }
                if (n4 == 0) continue;
                if (nArray[nArray2[n6]] == 1 && n8 != 0) {
                    nArray[nArray2[n6]] = 0;
                    nArray[n] = 4;
                }
                n7 = n6;
            }
            if (n7 != -1) {
                for (n6 = n10; n6 > n7; --n6) {
                    nArray2[n6] = nArray2[n6 - 1];
                }
                nArray2[n7 + 1] = n;
                ++n10;
                continue;
            }
            nArray2[n10] = n;
            ++n10;
        }
        for (n = 0; n < this.nob; ++n) {
            if ((this.co[n].colok < 55 || this.co[n].colok > 65) && this.co[n].colok != 66) continue;
            nArray2[n10] = n;
            ++n10;
        }
        n = 0;
        n8 = 0;
        n7 = 0;
        n6 = 0;
        this.bstage = "";
        for (n5 = 0; n5 < n10; ++n5) {
            String string;
            if (this.co[nArray2[n5]].colok != 30 && this.co[nArray2[n5]].colok != 31 && this.co[nArray2[n5]].colok != 32 && this.co[nArray2[n5]].colok != 54 && this.co[nArray2[n5]].colok != 66) {
                string = "";
                if (nArray[nArray2[n5]] == 1) {
                    string = "p";
                }
                if (nArray[nArray2[n5]] == 2) {
                    string = "pt";
                }
                if (nArray[nArray2[n5]] == 3) {
                    string = "pr";
                }
                if (nArray[nArray2[n5]] == 4) {
                    string = "ph";
                }
                if (nArray[nArray2[n5]] == 5) {
                    string = "pl";
                }
                if (nArray[nArray2[n5]] == 6) {
                    string = "pr";
                }
                this.bstage = this.bstage + "set(" + (this.co[nArray2[n5]].colok + 10) + "," + this.co[nArray2[n5]].x + "," + this.co[nArray2[n5]].z + "," + this.co[nArray2[n5]].roofat + ")" + string + "\r\n";
            }
            if (this.co[nArray2[n5]].colok == 30 || this.co[nArray2[n5]].colok == 32) {
                if (this.co[nArray2[n5]].roofat == 180) {
                    this.co[nArray2[n5]].roofat = 0;
                }
                string = "";
                if (this.co[nArray2[n5]].wh != 0) {
                    string = "r";
                }
                this.bstage = this.bstage + "chk(" + (this.co[nArray2[n5]].colok + 10) + "," + this.co[nArray2[n5]].x + "," + this.co[nArray2[n5]].z + "," + this.co[nArray2[n5]].roofat + ")" + string + "\r\n";
            }
            if (this.co[nArray2[n5]].colok == 54) {
                if (this.co[nArray2[n5]].roofat == 180) {
                    this.co[nArray2[n5]].roofat = 0;
                }
                string = "";
                if (this.co[nArray2[n5]].wh != 0) {
                    string = "r";
                }
                this.bstage = this.bstage + "chk(" + (this.co[nArray2[n5]].colok + 10) + "," + this.co[nArray2[n5]].x + "," + this.co[nArray2[n5]].z + "," + this.co[nArray2[n5]].roofat + "," + this.co[nArray2[n5]].y + ")" + string + "\r\n";
            }
            if (this.co[nArray2[n5]].colok == 31) {
                this.bstage = this.bstage + "fix(" + (this.co[nArray2[n5]].colok + 10) + "," + this.co[nArray2[n5]].x + "," + this.co[nArray2[n5]].z + "," + this.co[nArray2[n5]].y + "," + this.co[nArray2[n5]].roofat + ")\r\n";
            }
            if (this.co[nArray2[n5]].colok == 66) {
                this.bstage = this.bstage + "pile(" + this.co[nArray2[n5]].srz + "," + this.co[nArray2[n5]].srx + "," + this.co[nArray2[n5]].sry + "," + this.co[nArray2[n5]].x + "," + this.co[nArray2[n5]].z + ")\r\n";
            }
            if (this.co[nArray2[n5]].x + this.co[nArray2[n5]].maxR > n) {
                n = this.co[nArray2[n5]].x + this.co[nArray2[n5]].maxR;
            }
            if (this.co[nArray2[n5]].x - this.co[nArray2[n5]].maxR < n7) {
                n7 = this.co[nArray2[n5]].x - this.co[nArray2[n5]].maxR;
            }
            if (this.co[nArray2[n5]].z + this.co[nArray2[n5]].maxR > n8) {
                n8 = this.co[nArray2[n5]].z + this.co[nArray2[n5]].maxR;
            }
            if (this.co[nArray2[n5]].z - this.co[nArray2[n5]].maxR >= n6) continue;
            n6 = this.co[nArray2[n5]].z - this.co[nArray2[n5]].maxR;
        }
        n5 = n7 - 0;
        int n14 = n + 0;
        int n15 = (int)((float)(n14 - n5) / 4800.0f) + 1;
        int n16 = (n15 * 4800 - (n14 - n5)) / 2;
        n14 += n16;
        int n17 = (n5 -= n16) + 2400;
        n3 = n6 - 0;
        int n18 = n8 + 0;
        int n19 = (int)((float)(n18 - n3) / 4800.0f) + 1;
        n16 = (n19 * 4800 - (n18 - n3)) / 2;
        int n20 = (n3 -= n16) + 2400;
        this.bstage = this.bstage + "\r\nmaxl(" + n19 + "," + n5 + "," + n20 + ")\r\nmaxb(" + n15 + "," + n3 + "," + n17 + ")\r\nmaxr(" + n19 + "," + n14 + "," + n20 + ")\r\nmaxt(" + n15 + "," + (n18 += n16) + "," + n17 + ")\r\n";
    }

    public void savefile() {
        try {
            File file = new File("" + Madness.fpath + "mystages/");
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File("" + Madness.fpath + "mystages/" + this.stagename + ".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(this.tstage);
            bufferedWriter.write(this.bstage);
            bufferedWriter.close();
            bufferedWriter = null;
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Unable to save file! Error Deatials:\n" + exception, "Stage Maker", 1);
        }
        this.savesettings();
    }

    public void renstage(String string) {
        if (string.equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter a New Stage Name!\n", "Stage Maker", 1);
        } else {
            try {
                File file = new File("" + Madness.fpath + "mystages/" + this.stagename + ".txt");
                File file2 = new File("" + Madness.fpath + "mystages/" + string + ".txt");
                if (file.renameTo(file2)) {
                    this.stagename = string;
                    this.sfase = 0;
                    this.hidefields();
                    this.tabed = -2;
                } else {
                    JOptionPane.showMessageDialog(null, "Unable to rename stage to: '" + string + "', possible reason: stage name already used!\n", "Stage Maker", 1);
                }
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Unable to rename file! Error Deatials:\n" + exception, "Stage Maker", 1);
            }
        }
    }

    public void delstage(String string) {
        try {
            File file = new File("" + Madness.fpath + "mystages/" + string + ".txt");
            file.delete();
            this.slstage.remove(string);
            this.slstage.select(0);
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Unable to delete file! Error Deatials:\n" + exception, "Stage Maker", 1);
        }
    }

    public void deltrack() {
        try {
            File file = new File("" + Madness.fpath + "mystages/mymusic/" + this.tracks.getSelectedItem() + ".zip");
            file.delete();
            if (this.trackname.equals(this.tracks.getSelectedItem())) {
                this.trackname = "";
                this.sortop();
                this.savefile();
            }
            this.tracks.remove(this.tracks.getSelectedItem());
            this.tracks.select(0);
        }
        catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Unable to delete file! Error Deatials:\n" + exception, "Stage Maker", 1);
        }
    }

    public void loadsettings() {
        try {
            File file = new File("" + Madness.fpath + "mystages/settings.data");
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String string = bufferedReader.readLine();
                if (string != null) {
                    this.stagename = this.sstage = string;
                }
                if ((string = bufferedReader.readLine()) != null) {
                    this.suser = string;
                    if (!this.suser.equals("Horaks")) {
                        this.tnick.setText(this.suser);
                    }
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
        if (!this.sstage.equals(this.stagename) || !this.suser.equals(this.tnick.getText())) {
            String string = "" + this.stagename + "\n" + this.tnick.getText() + "\n\n";
            this.sstage = this.stagename;
            this.suser = this.tnick.getText();
            try {
                File file = new File("" + Madness.fpath + "mystages/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File("" + Madness.fpath + "mystages/settings.data");
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

    public void fixtext(TextField textField) {
        String string = textField.getText();
        string = string.replace('\"', '#');
        String string2 = "\\";
        String string3 = "";
        int n = -1;
        this.rd.setFont(new Font("Arial", 1, 12));
        this.ftm = this.rd.getFontMetrics();
        for (int i = 0; i < string.length(); ++i) {
            String string4 = "" + string.charAt(i);
            if (string4.equals("|") || string4.equals(",") || string4.equals("(") || string4.equals(")") || string4.equals("#") || string4.equals(string2) || string4.equals("!") || string4.equals("?") || string4.equals("~") || string4.equals(".") || string4.equals("@") || string4.equals("$") || string4.equals("%") || string4.equals("^") || string4.equals("&") || string4.equals("*") || string4.equals("+") || string4.equals("=") || string4.equals(">") || string4.equals("<") || string4.equals("/") || string4.equals(";") || string4.equals(":") || this.ftm.stringWidth(string3) > 274) {
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

    public int py(int n, int n2, int n3, int n4) {
        return (int)Math.sqrt((n - n2) * (n - n2) + (n3 - n4) * (n3 - n4));
    }

    public int pyn(int n, int n2, int n3, int n4) {
        return (n - n2) / 100 * ((n - n2) / 100) + (n3 - n4) / 100 * ((n3 - n4) / 100);
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

    public boolean button(String string, int n, int n2, int n3, boolean bl) {
        this.rd.setFont(new Font("Arial", 1, 12));
        this.ftm = this.rd.getFontMetrics();
        int n4 = this.ftm.stringWidth(string);
        boolean bl2 = false;
        boolean bl3 = false;
        if (string.equals(" Cancel ") && this.epart && Math.abs(this.xm - n) < n4 / 2 + 12 && Math.abs(this.ym - n2 + 5) < 10) {
            this.overcan = true;
        }
        bl2 = Math.abs(this.xm - n) < n4 / 2 + 12 && Math.abs(this.ym - n2 + 5) < 10 && this.mouses == 1;
        if (Math.abs(this.xm - n) < n4 / 2 + 12 && Math.abs(this.ym - n2 + 5) < 10 && this.mouses == -1) {
            this.mouses = 0;
            bl3 = true;
        }
        boolean bl4 = false;
        if (bl) {
            if (this.tab == 0) {
                this.rd.setColor(new Color(207, 207, 207));
            }
            if (this.tab == 1) {
                this.rd.setColor(new Color(200, 200, 200));
            }
            if (this.tab == 2) {
                this.rd.setColor(new Color(170, 170, 170));
            }
            if (this.tab != 3) {
                this.rd.drawRect(n - n4 / 2 - 15, n2 - (22 - n3), n4 + 29, 34 - n3 * 2);
                if (n3 == 2 && this.tab == 1) {
                    this.rd.setColor(new Color(220, 220, 220));
                    this.rd.fillRect(n - n4 / 2 - 15, n2 - (22 - n3), n4 + 29, 34 - n3 * 2);
                }
            } else {
                bl4 = true;
            }
        }
        if (!bl2) {
            this.rd.setColor(new Color(220, 220, 220));
            if (bl4) {
                this.rd.setColor(new Color(230, 230, 230));
            }
            this.rd.fillRect(n - n4 / 2 - 10, n2 - (17 - n3), n4 + 20, 25 - n3 * 2);
            this.rd.setColor(new Color(240, 240, 240));
            if (bl4) {
                this.rd.setColor(new Color(255, 255, 255));
            }
            this.rd.drawLine(n - n4 / 2 - 10, n2 - (17 - n3), n + n4 / 2 + 10, n2 - (17 - n3));
            this.rd.drawLine(n - n4 / 2 - 10, n2 - (18 - n3), n + n4 / 2 + 10, n2 - (18 - n3));
            this.rd.setColor(new Color(240, 240, 240));
            this.rd.drawLine(n - n4 / 2 - 9, n2 - (19 - n3), n + n4 / 2 + 9, n2 - (19 - n3));
            this.rd.setColor(new Color(200, 200, 200));
            if (bl4) {
                this.rd.setColor(new Color(192, 192, 192));
            }
            this.rd.drawLine(n + n4 / 2 + 10, n2 - (17 - n3), n + n4 / 2 + 10, n2 + (7 - n3));
            this.rd.drawLine(n + n4 / 2 + 11, n2 - (17 - n3), n + n4 / 2 + 11, n2 + (7 - n3));
            this.rd.setColor(new Color(200, 200, 200));
            if (bl4) {
                this.rd.setColor(new Color(192, 192, 192));
            }
            this.rd.drawLine(n + n4 / 2 + 12, n2 - (16 - n3), n + n4 / 2 + 12, n2 + (6 - n3));
            this.rd.drawLine(n - n4 / 2 - 10, n2 + (7 - n3), n + n4 / 2 + 10, n2 + (7 - n3));
            this.rd.drawLine(n - n4 / 2 - 10, n2 + (8 - n3), n + n4 / 2 + 10, n2 + (8 - n3));
            this.rd.setColor(new Color(200, 200, 200));
            this.rd.drawLine(n - n4 / 2 - 9, n2 + (9 - n3), n + n4 / 2 + 9, n2 + (9 - n3));
            this.rd.setColor(new Color(240, 240, 240));
            if (bl4) {
                this.rd.setColor(new Color(255, 255, 255));
            }
            this.rd.drawLine(n - n4 / 2 - 10, n2 - (17 - n3), n - n4 / 2 - 10, n2 + (7 - n3));
            this.rd.drawLine(n - n4 / 2 - 11, n2 - (17 - n3), n - n4 / 2 - 11, n2 + (7 - n3));
            this.rd.setColor(new Color(240, 240, 240));
            this.rd.drawLine(n - n4 / 2 - 12, n2 - (16 - n3), n - n4 / 2 - 12, n2 + (6 - n3));
            this.rd.setColor(new Color(0, 0, 0));
            if (string.equals("  Keyboard Controls  ")) {
                this.rd.setColor(new Color(100, 100, 100));
            }
            this.rd.drawString(string, n - n4 / 2, n2);
        } else {
            this.rd.setColor(new Color(220, 220, 220));
            this.rd.fillRect(n - n4 / 2 - 10, n2 - (17 - n3), n4 + 20, 25 - n3 * 2);
            this.rd.setColor(new Color(192, 192, 192));
            this.rd.drawLine(n - n4 / 2 - 10, n2 - (17 - n3), n + n4 / 2 + 10, n2 - (17 - n3));
            this.rd.drawLine(n - n4 / 2 - 10, n2 - (18 - n3), n + n4 / 2 + 10, n2 - (18 - n3));
            this.rd.drawLine(n - n4 / 2 - 9, n2 - (19 - n3), n + n4 / 2 + 9, n2 - (19 - n3));
            this.rd.setColor(new Color(247, 247, 247));
            this.rd.drawLine(n + n4 / 2 + 10, n2 - (17 - n3), n + n4 / 2 + 10, n2 + (7 - n3));
            this.rd.drawLine(n + n4 / 2 + 11, n2 - (17 - n3), n + n4 / 2 + 11, n2 + (7 - n3));
            this.rd.drawLine(n + n4 / 2 + 12, n2 - (16 - n3), n + n4 / 2 + 12, n2 + (6 - n3));
            this.rd.drawLine(n - n4 / 2 - 10, n2 + (7 - n3), n + n4 / 2 + 10, n2 + (7 - n3));
            this.rd.drawLine(n - n4 / 2 - 10, n2 + (8 - n3), n + n4 / 2 + 10, n2 + (8 - n3));
            this.rd.drawLine(n - n4 / 2 - 9, n2 + (9 - n3), n + n4 / 2 + 9, n2 + (9 - n3));
            this.rd.setColor(new Color(192, 192, 192));
            this.rd.drawLine(n - n4 / 2 - 10, n2 - (17 - n3), n - n4 / 2 - 10, n2 + (7 - n3));
            this.rd.drawLine(n - n4 / 2 - 11, n2 - (17 - n3), n - n4 / 2 - 11, n2 + (7 - n3));
            this.rd.drawLine(n - n4 / 2 - 12, n2 - (16 - n3), n - n4 / 2 - 12, n2 + (6 - n3));
            this.rd.setColor(new Color(0, 0, 0));
            if (string.equals("  Keyboard Controls  ")) {
                this.rd.setColor(new Color(100, 100, 100));
            }
            this.rd.drawString(string, n - n4 / 2 + 1, n2 + 1);
        }
        return bl3;
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

    public void openlink() {
        Madness.openurl("http://www.needformadness.com/developer/help.html");
    }

    public void openhlink() {
        Madness.openurl("http://www.needformadness.com/developer/");
    }
}

