/*
 * Decompiled with CFR 0.152.
 */
package ds.nfm.xm;

import ds.nfm.Module;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IBXMod
extends Module {
    ibxm.Module refMod;

    public IBXMod(byte[] modf) {
        try {
            this.loadMod(new ByteArrayInputStream(modf));
            this.loaded = true;
        }
        catch (Exception e) {
            this.loaded = false;
        }
    }

    @Override
    public void loadMod(InputStream inputstream) throws IOException {
        this.refMod = new ibxm.Module(inputstream);
    }
}

