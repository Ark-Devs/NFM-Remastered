/*
 * Decompiled with CFR 0.152.
 */
package ds.nfm;

import ds.nfm.Module;
import ds.nfm.ModuleException;
import ds.nfm.ModuleSlayer;
import ds.nfm.mod.Mod;
import ds.nfm.mod.ModSlayer;
import ds.nfm.xm.IBXMod;
import ds.nfm.xm.IBXModSlayer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ModuleLoader {
    public static Module loadMod(String string) throws FileNotFoundException, IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(new File(string)));
        return ModuleLoader.loadMod(zipInputStream);
    }

    public static Module loadMod(URL uRL) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new DataInputStream(uRL.openStream()));
        return ModuleLoader.loadMod(zipInputStream);
    }

    public static Module loadMod(byte[] byArray) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(byArray));
        return ModuleLoader.loadMod(zipInputStream);
    }

    public static Module loadMod(ZipInputStream zipInputStream) throws IOException {
        byte[] byArray;
        Module module = null;
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        String string = zipEntry.getName();
        int n = (int)zipEntry.getSize();
        if (n == -1) {
            int n2;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ((n2 = zipInputStream.read()) != -1) {
                byteArrayOutputStream.write(n2);
            }
            byArray = byteArrayOutputStream.toByteArray();
        } else {
            byArray = new byte[n];
            int n3 = 0;
            while (n > 0) {
                int n4 = zipInputStream.read(byArray, n3, n);
                n3 += n4;
                n -= n4;
            }
        }
        zipInputStream.close();
        module = string.toLowerCase().endsWith(".xm") || string.toLowerCase().endsWith(".s3m") ? new IBXMod(byArray) : new Mod(byArray);
        return module;
    }

    public static ModuleSlayer prepareSlayer(Module module, int n, int n2, int n3) {
        if (module instanceof Mod) {
            return new ModSlayer((Mod)module, n, n2, n3);
        }
        if (module instanceof IBXMod) {
            return new IBXModSlayer((IBXMod)module, n, n2, n3);
        }
        throw new ModuleException("Unsuported module format: " + module.getName());
    }
}

