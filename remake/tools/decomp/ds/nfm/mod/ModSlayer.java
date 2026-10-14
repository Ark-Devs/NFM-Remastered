/*
 * Decompiled with CFR 0.152.
 */
package ds.nfm.mod;

import ds.nfm.ModuleSlayer;
import ds.nfm.mod.Mod;
import ds.nfm.mod.ModInstrument;
import ds.nfm.mod.ModTrackInfo;
import java.io.IOException;

public class ModSlayer
extends ModuleSlayer {
    static final String VERSION = "1.1";
    static final String COPYRIGHT = "DragShot Software (c) 2014";
    static final int EFF_VOL_SLIDE = 1;
    static final int EFF_PORT_DOWN = 2;
    static final int EFF_PORT_UP = 4;
    static final int EFF_VIBRATO = 8;
    static final int EFF_ARPEGGIO = 16;
    static final int EFF_PORT_TO = 32;
    static final int EFF_TREMOLO = 64;
    static final int EFF_RETRIG = 128;
    static final int MIX_BUF_SIZE = 2048;
    static final int DEF_TEMPO_NTSC = 6;
    static final int DEF_TEMPO_PAL = 6;
    static final int DEF_BPM_NTSC = 125;
    static final int DEF_BPM_PAL = 145;
    static final int MIDCRATE = 8448;
    static final int MAX_SAMPLES = 100;
    static final int MAX_TRACKS = 32;
    static final int S3M_MAGIC1 = 4122;
    static final int S3M_MAGIC2 = Mod.FOURCC("SCRM");
    static final int S3M_INSTR2 = Mod.FOURCC("SCRS");
    static final int[] normal_vol_adj = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 63};
    static final int[] loud_vol_adj = new int[]{0, 0, 1, 2, 2, 3, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 47, 48, 49, 50, 51, 52, 53, 53, 54, 55, 55, 56, 56, 57, 57, 58, 58, 59, 59, 60, 60, 61, 61, 61, 62, 62, 62, 63, 63, 63, 63, 63, 63};
    static final int[] sintable = new int[]{0, 25, 50, 74, 98, 120, 142, 162, 180, 197, 212, 225, 236, 244, 250, 254, 255, 254, 250, 244, 236, 225, 212, 197, 180, 162, 142, 120, 98, 74, 50, 25};
    static final int[] period_set = new int[]{1712, 1616, 1525, 1440, 1359, 1283, 1211, 1143, 1078, 1018, 961, 907, 856, 808, 763, 720, 679, 641, 605, 571, 539, 509, 480, 453, 428, 404, 381, 360, 340, 321, 303, 286, 270, 254, 240, 227, 214, 202, 191, 180, 170, 160, 151, 143, 135, 127, 120, 113, 107, 101, 95, 90, 85, 80, 76, 71, 67, 64, 60, 57, 53, 50, 48, 45, 42, 40, 38, 36, 34, 32, 30, 28, 27, 25, 24, 22, 21, 20, 19, 18, 17, 16, 15, 14};
    static final int[] period_set_step = new int[]{1664, 1570, 1482, 1399, 1321, 1247, 1177, 1110, 1048, 989, 934, 881, 832, 785, 741, 699, 660, 623, 588, 555, 524, 494, 466, 440, 416, 392, 370, 350, 330, 312, 294, 278, 262, 247, 233, 220, 208, 196, 185, 175, 165, 155, 147, 139, 131, 123, 116, 110, 104, 98, 92, 87, 82, 78, 73, 69, 65, 62, 58, 55, 51, 49, 46, 43, 41, 39, 37, 35, 33, 31, 29, 27, 26, 24, 23, 21, 20, 19, 18, 17, 16, 15, 14, 14};
    int def_tempo;
    int def_bpm;
    byte[] vol_table;
    int[] vol_adj;
    int vol_shift;
    Mod mod;
    int order_pos;
    int tempo;
    int tempo_wait;
    int bpm;
    int row;
    int break_row;
    int bpm_samples;
    int pattofs;
    byte[] patt;
    int numtracks;
    ModTrackInfo[] tracks;
    int mixspeed;
    boolean mod_done = false;
    public boolean bit16;
    public int samplingrate;
    public int oversample;
    public int gain;
    public int nloops = 1;
    public boolean loud = false;
    private static final int ERROR_SHIFT = 12;
    private static final int ERROR_MASK = 4095;
    private static final long ratediv = 22748294283264L;
    int loopA = 0;
    int loopB = 0;
    int loops = 0;
    boolean onLoop = false;
    int jumpTo = -1;
    int jumpLocation = 0;
    boolean reverseJump = false;
    int[] patternOffsets;

    public ModSlayer(Mod pmod, int smpl, int gn, int bpmflex) {
        this.samplingrate = smpl;
        this.gain = gn;
        this.oversample = 1;
        this.mod = pmod;
        this.def_tempo = 6;
        this.def_bpm = bpmflex;
    }

    final void beattrack(ModTrackInfo modtrackinfo) {
        if (modtrackinfo.period_low_limit == 0) {
            modtrackinfo.period_low_limit = 1;
        }
        if ((modtrackinfo.effect & 1) != 0) {
            modtrackinfo.volume += modtrackinfo.vol_slide;
            if (modtrackinfo.volume < 0) {
                modtrackinfo.volume = 0;
            }
            if (modtrackinfo.volume > 64) {
                modtrackinfo.volume = 64;
            }
        }
        if ((modtrackinfo.effect & 2) != 0) {
            if ((modtrackinfo.period += modtrackinfo.port_down) > modtrackinfo.period_high_limit) {
                modtrackinfo.period = modtrackinfo.period_high_limit;
            }
            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.period;
        }
        if ((modtrackinfo.effect & 4) != 0) {
            if ((modtrackinfo.period -= modtrackinfo.port_up) < modtrackinfo.period_low_limit) {
                modtrackinfo.period = this.mod.s3m ? modtrackinfo.period_high_limit : modtrackinfo.period_low_limit;
            }
            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.period;
        }
        if ((modtrackinfo.effect & 0x20) != 0) {
            if (modtrackinfo.portto < modtrackinfo.period) {
                if ((modtrackinfo.period += modtrackinfo.port_inc) > modtrackinfo.portto) {
                    modtrackinfo.period = modtrackinfo.portto;
                }
            } else if (modtrackinfo.portto > modtrackinfo.period && (modtrackinfo.period -= modtrackinfo.port_inc) < modtrackinfo.portto) {
                modtrackinfo.period = modtrackinfo.portto;
            }
            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.period;
        }
        if ((modtrackinfo.effect & 8) != 0) {
            modtrackinfo.vibpos += modtrackinfo.vib_rate << 2;
            int i = sintable[modtrackinfo.vibpos >> 2 & 0x1F] * modtrackinfo.vib_depth >> 7;
            if ((modtrackinfo.vibpos & 0x80) != 0) {
                i = -i;
            }
            i += modtrackinfo.period;
            if (this.mod.s3m) {
                if (i < modtrackinfo.period_low_limit) {
                    i = modtrackinfo.period_low_limit;
                }
                if (i > modtrackinfo.period_high_limit) {
                    i = modtrackinfo.period_high_limit;
                }
            }
            i = i > 0 ? i : 1;
            modtrackinfo.pitch = modtrackinfo.finetune_rate / i;
        }
        if ((modtrackinfo.effect & 0x10) != 0) {
            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.arp[modtrackinfo.arpindex];
            ++modtrackinfo.arpindex;
            if (modtrackinfo.arpindex >= 3) {
                modtrackinfo.arpindex = 0;
            }
        }
    }

    final int get_track(ModTrackInfo modtrackinfo, byte[] is, int i) {
        int i_2_ = is[i] & 0xF0;
        int i_3_ = (is[i++] & 0xF) << 8;
        i_3_ |= is[i++] & 0xFF;
        int i_4_ = is[i] & 0xF;
        int n = i++;
        int i_5_ = is[i++];
        modtrackinfo.effect = 0;
        if ((i_2_ |= (is[n] & 0xF0) >> 4) != 0) {
            ModInstrument modinstrument = this.mod.insts[--i_2_];
            modtrackinfo.volume = modinstrument.volume;
            modtrackinfo.length = modinstrument.sample_length;
            modtrackinfo.repeat = modinstrument.repeat_point;
            modtrackinfo.replen = modinstrument.repeat_length;
            modtrackinfo.finetune_rate = modinstrument.finetune_rate;
            modtrackinfo.samples = modinstrument.samples;
            modtrackinfo.period_low_limit = modinstrument.period_low_limit;
            modtrackinfo.period_high_limit = modinstrument.period_high_limit;
        }
        modtrackinfo.notelimit = -1;
        modtrackinfo.noterestart -= this.tempo;
        if (modtrackinfo.noterestart < -1) {
            modtrackinfo.noterestart = -1;
        }
        if (i_3_ != 0) {
            modtrackinfo.portto = i_3_;
            if (i_4_ != 3 && i_4_ != 5) {
                modtrackinfo.start_period = modtrackinfo.period = i_3_;
                modtrackinfo.pitch = modtrackinfo.finetune_rate / i_3_;
                modtrackinfo.position = 0;
            }
        }
        if (i_4_ != 0 || i_5_ != 0) {
            block0 : switch (i_4_) {
                default: {
                    break;
                }
                case 0: {
                    int i_6_;
                    for (i_6_ = 12; i_6_ < 48 && modtrackinfo.period < period_set[i_6_]; ++i_6_) {
                    }
                    modtrackinfo.arp[0] = period_set[i_6_];
                    modtrackinfo.arp[1] = period_set[i_6_ + (i_5_ & 0xF)];
                    modtrackinfo.arp[2] = period_set[i_6_ + ((i_5_ & 0xF0) >> 4)];
                    modtrackinfo.arpindex = 0;
                    modtrackinfo.effect |= 0x10;
                    break;
                }
                case 1: {
                    modtrackinfo.effect |= 4;
                    if (i_5_ == 0) break;
                    modtrackinfo.port_up = i_5_;
                    break;
                }
                case 2: {
                    modtrackinfo.effect |= 2;
                    if (i_5_ == 0) break;
                    modtrackinfo.port_down = i_5_;
                    break;
                }
                case 3: {
                    if (i_5_ != 0) {
                        modtrackinfo.port_inc = i_5_ & 0xFF;
                    }
                    modtrackinfo.effect |= 0x20;
                    break;
                }
                case 4: {
                    if ((i_5_ & 0xF) != 0) {
                        modtrackinfo.vib_depth = i_5_ & 0xF;
                    }
                    if ((i_5_ & 0xF0) != 0) {
                        modtrackinfo.vib_rate = (i_5_ & 0xF0) >> 4;
                    }
                    if (i_3_ != 0) {
                        modtrackinfo.vibpos = 0;
                    }
                    modtrackinfo.effect |= 8;
                    break;
                }
                case 9: {
                    if (i_5_ == 0) {
                        i_5_ = modtrackinfo.oldsampofs;
                    }
                    modtrackinfo.oldsampofs = i_5_;
                    modtrackinfo.position = (i_5_ & 0xFF) << 8;
                    break;
                }
                case 5: {
                    modtrackinfo.effect |= 0x20;
                }
                case 6: {
                    if (i_4_ == 6) {
                        modtrackinfo.effect |= 8;
                    }
                }
                case 10: {
                    modtrackinfo.vol_slide = ((i_5_ & 0xF0) >> 4) - (i_5_ & 0xF);
                    modtrackinfo.effect |= 1;
                    break;
                }
                case 11: {
                    if (this.jumpLocation == this.order_pos && this.reverseJump) {
                        this.reverseJump = false;
                        break;
                    }
                    if (this.reverseJump || i_5_ >= 128 || i_5_ < 0) break;
                    this.jumpTo = i_5_;
                    this.jumpLocation = this.order_pos;
                    if (this.jumpTo >= this.order_pos) break;
                    this.reverseJump = true;
                    if (this.order_pos != this.mod.song_length_patterns) break;
                    if (i_5_ < this.order_pos - 2) {
                        this.rollBack = i_5_;
                        this.rollBackPos = this.patternOffsets[i_5_];
                        this.loopMark = true;
                    }
                    this.jumpTo = -1;
                    this.jumpLocation = 0;
                    break;
                }
                case 12: {
                    if (i_5_ > 64 || i_5_ < 0) {
                        modtrackinfo.volume = 64;
                        break;
                    }
                    modtrackinfo.volume = i_5_;
                    break;
                }
                case 13: {
                    this.break_row = ((i_5_ & 0xF0) >> 4) * 10 + (i_5_ & 0xF);
                    this.row = 64;
                    break;
                }
                case 14: {
                    int i_7_ = (i_5_ & 0xF0) >> 4;
                    i_5_ &= 0xF;
                    switch (i_7_) {
                        default: {
                            break;
                        }
                        case 1: {
                            modtrackinfo.period += i_5_;
                            if (modtrackinfo.period > modtrackinfo.period_high_limit) {
                                modtrackinfo.period = modtrackinfo.period_high_limit;
                            }
                            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.period;
                            break;
                        }
                        case 2: {
                            modtrackinfo.period -= i_5_;
                            if (modtrackinfo.period < modtrackinfo.period_low_limit) {
                                modtrackinfo.period = modtrackinfo.period_low_limit;
                            }
                            modtrackinfo.pitch = modtrackinfo.finetune_rate / modtrackinfo.period;
                            break;
                        }
                        case 6: {
                            if (this.loops != 0 || this.onLoop) break block0;
                            if (i_5_ <= 0) {
                                this.loopA = this.row;
                                break;
                            }
                            this.loopB = this.row;
                            this.loops = i_5_;
                            this.onLoop = true;
                            break;
                        }
                        case 9: {
                            modtrackinfo.noterestart = i_5_;
                            break;
                        }
                        case 10: {
                            modtrackinfo.volume += i_5_;
                            if (modtrackinfo.volume > 64) {
                                modtrackinfo.volume = 64;
                            }
                            if (modtrackinfo.volume >= 0) break block0;
                            modtrackinfo.volume = 0;
                            break;
                        }
                        case 11: {
                            modtrackinfo.volume -= i_5_;
                            if (modtrackinfo.volume > 64) {
                                modtrackinfo.volume = 64;
                            }
                            if (modtrackinfo.volume >= 0) break block0;
                            modtrackinfo.volume = 0;
                            break;
                        }
                        case 12: {
                            modtrackinfo.notelimit = i_5_;
                            break;
                        }
                    }
                    break;
                }
                case 15: {
                    if (i_5_ == 0) break;
                    if ((i_5_ &= 0xFF) <= 32) {
                        this.tempo = i_5_;
                        this.tempo_wait = i_5_;
                        break;
                    }
                    this.bpm = i_5_;
                    this.bpm_samples = this.samplingrate / (103 * i_5_ >> 8) * this.oversample;
                }
            }
        }
        return i;
    }

    final void make_vol_table8() {
        this.vol_table = new byte[16640];
        for (int i = 0; i < 16640; ++i) {
            this.vol_table[i] = (byte)(this.vol_adj[i >> 8] * (byte)i >> 8 + this.vol_shift);
        }
    }

    final void mixtrack_16_mono(ModTrackInfo modtrackinfo, int[] buffer, int buffpos, int bufflen) {
        byte[] samples = modtrackinfo.samples;
        int samplepos = modtrackinfo.position;
        int volume = this.vol_adj[modtrackinfo.volume] * this.gain >> this.vol_shift + 8;
        int error = modtrackinfo.error;
        int lopitch = modtrackinfo.pitch & 0xFFF;
        int hipitch = modtrackinfo.pitch >> 12;
        if (modtrackinfo.replen < 3) {
            int endtr = modtrackinfo.length;
            if (samplepos < endtr) {
                int buffend = buffpos + bufflen;
                if (modtrackinfo.pitch < 4096) {
                    while (samplepos < endtr && buffpos < buffend) {
                        if (modtrackinfo.notelimit == -1 || this.tempo - this.tempo_wait < modtrackinfo.notelimit) {
                            int n = buffpos++;
                            buffer[n] = buffer[n] + ((samples[samplepos] * (4096 - error) + samples[samplepos + 1] * error) * volume >> 12);
                        } else {
                            modtrackinfo.volume = 0;
                        }
                        samplepos += hipitch + ((error += lopitch) >> 12);
                        error &= 0xFFF;
                    }
                } else {
                    while (samplepos < endtr && buffpos < buffend) {
                        if (modtrackinfo.notelimit == -1 || this.tempo - this.tempo_wait < modtrackinfo.notelimit) {
                            int n = buffpos++;
                            buffer[n] = buffer[n] + samples[samplepos] * volume;
                        } else {
                            modtrackinfo.volume = 0;
                        }
                        samplepos += hipitch + ((error += lopitch) >> 12);
                        error &= 0xFFF;
                    }
                }
                modtrackinfo.error = error;
                if (modtrackinfo.noterestart == -1 || this.tempo - this.tempo_wait < modtrackinfo.noterestart) {
                    modtrackinfo.position = samplepos;
                } else {
                    modtrackinfo.position = 0;
                    modtrackinfo.noterestart = -1;
                }
            }
        } else {
            int endtr = modtrackinfo.replen + modtrackinfo.repeat;
            if (modtrackinfo.pitch < 4096) {
                while (bufflen > 0) {
                    if (samplepos >= endtr) {
                        samplepos -= modtrackinfo.replen;
                    }
                    if (samplepos < 0) {
                        samplepos = 0;
                    }
                    while (samplepos >= samples.length) {
                        samplepos -= samples.length;
                    }
                    if (modtrackinfo.notelimit == -1 || this.tempo - this.tempo_wait < modtrackinfo.notelimit) {
                        int n = buffpos++;
                        buffer[n] = buffer[n] + ((samples[samplepos] * (4096 - error) + samples[samplepos + 1] * error) * volume >> 12);
                    } else {
                        modtrackinfo.volume = 0;
                    }
                    samplepos += hipitch + ((error += lopitch) >> 12);
                    error &= 0xFFF;
                    --bufflen;
                }
            } else {
                while (bufflen > 0) {
                    if (samplepos >= endtr) {
                        samplepos -= modtrackinfo.replen;
                    }
                    if (samplepos < 0) {
                        samplepos = 0;
                    }
                    while (samplepos >= samples.length) {
                        samplepos -= samples.length;
                    }
                    if (modtrackinfo.notelimit == -1 || this.tempo - this.tempo_wait < modtrackinfo.notelimit) {
                        int n = buffpos++;
                        buffer[n] = buffer[n] + samples[samplepos] * volume;
                    } else {
                        modtrackinfo.volume = 0;
                    }
                    samplepos += hipitch + ((error += lopitch) >> 12);
                    error &= 0xFFF;
                    --bufflen;
                }
            }
            modtrackinfo.error = error;
            if (modtrackinfo.noterestart == -1 || this.tempo - this.tempo_wait < modtrackinfo.noterestart) {
                modtrackinfo.position = samplepos;
            } else {
                modtrackinfo.position = 0;
                modtrackinfo.noterestart = -1;
            }
        }
    }

    @Override
    public byte[] turnbytesNorm(boolean calvol) throws IOException {
        this.bit16 = true;
        this.startplaying(this.loud);
        int[] buf = new int[this.mixspeed];
        int[] emptybuf = new int[this.mixspeed];
        byte[] realbytes = new byte[18000000];
        this.patternOffsets = new int[this.mod.song_length_patterns];
        this.oln = 0;
        this.olav = 1;
        int olniu = 1;
        while (!this.mod_done) {
            if (--this.tempo_wait > 0) {
                for (int count = 0; count < this.numtracks; ++count) {
                    this.beattrack(this.tracks[count]);
                }
            } else {
                this.updatetracks();
            }
            if (!this.mod_done) {
                int i;
                if (this.row == 1 && this.tempo_wait == this.tempo) {
                    this.patternOffsets[this.order_pos - 1] = this.oln;
                }
                System.arraycopy(emptybuf, 0, buf, 0, this.bpm_samples);
                for (i = 0; i < this.numtracks; ++i) {
                    this.mixtrack_16_mono(this.tracks[i], buf, 0, this.bpm_samples);
                }
                int real_samples = this.bpm_samples;
                if (this.oversample > 1) {
                    int k = 0;
                    real_samples = this.bpm_samples / this.oversample;
                    if (this.oversample == 2) {
                        for (i = 0; i < real_samples; ++i) {
                            buf[i] = buf[k] + buf[k + 1] >> 1;
                            k += 2;
                        }
                    } else {
                        for (i = 0; i < real_samples; ++i) {
                            int sum = buf[k++];
                            for (int j = 1; j < this.oversample; ++j) {
                                sum += buf[k++];
                            }
                            buf[i] = sum / this.oversample;
                        }
                    }
                }
                if (this.oln + real_samples < 18000000) {
                    if (calvol) {
                        int niu = 0;
                        int cav = 0;
                        for (i = 0; i < real_samples; ++i) {
                            if (buf[i] <= 0) continue;
                            cav += buf[i];
                            ++niu;
                        }
                        if (niu != 0) {
                            this.olav += (cav /= niu);
                            ++olniu;
                        }
                    }
                    ModSlayer.intToBytes16(buf, realbytes, real_samples, this.oln);
                    this.oln += real_samples;
                }
            }
            if (!this.loopMark) continue;
            this.rollBackTrig = this.oln;
            this.loopMark = false;
        }
        if (calvol) {
            this.olav /= olniu;
        }
        ++this.oln;
        return realbytes;
    }

    public static void intToBytes16(int[] sample, byte[] buffer, int realsamples, int oln) {
        int byteOffset = oln;
        for (int i = 0; i < realsamples; ++i) {
            buffer[byteOffset++] = (byte)(sample[i] >> 8);
            buffer[byteOffset] = (byte)(sample[i] & 0xFF);
        }
    }

    final void startplaying(boolean flag) {
        this.vol_adj = flag ? loud_vol_adj : normal_vol_adj;
        this.mixspeed = this.samplingrate * this.oversample;
        this.order_pos = 0;
        this.tempo_wait = this.tempo = this.def_tempo;
        this.bpm = this.def_bpm;
        this.row = 64;
        this.break_row = 0;
        this.bpm_samples = this.samplingrate / (24 * this.bpm / 60) * this.oversample;
        this.numtracks = this.mod.numtracks;
        this.tracks = new ModTrackInfo[this.numtracks];
        for (int i = 0; i < this.tracks.length; ++i) {
            this.tracks[i] = new ModTrackInfo();
        }
        if (this.mod.s3m) {
            for (int j = 0; j < this.mod.insts.length; ++j) {
                ModInstrument modinstrument = this.mod.insts[j];
                modinstrument.finetune_rate = (int)(428L * (long)modinstrument.finetune_value << 8) / this.mixspeed;
                modinstrument.period_low_limit = 14;
                modinstrument.period_high_limit = 1712;
            }
        } else {
            for (int k = 0; k < this.mod.insts.length; ++k) {
                ModInstrument modinstrument1 = this.mod.insts[k];
                modinstrument1.finetune_rate = (int)(22748294283264L / (long)(this.mixspeed * (1536 - modinstrument1.finetune_value)));
                modinstrument1.period_low_limit = 113;
                modinstrument1.period_high_limit = 856;
            }
        }
        this.vol_shift = this.numtracks > 8 ? 2 : (this.numtracks > 4 ? 1 : 0);
        if (!this.bit16) {
            this.make_vol_table8();
        }
    }

    final void updatetracks() {
        this.tempo_wait = this.tempo;
        if (this.jumpTo != -1) {
            this.onLoop = false;
            this.loopA = this.row;
            this.row = this.break_row;
            this.break_row = 0;
            this.order_pos = this.jumpTo;
            this.patt = this.mod.patterns[this.mod.positions[this.order_pos]];
            this.pattofs = this.row * 4 * this.numtracks;
            ++this.order_pos;
            this.jumpTo = -1;
        }
        if (this.row >= 64) {
            this.onLoop = false;
            this.loopA = this.row;
            if (this.order_pos >= this.mod.song_length_patterns) {
                this.order_pos = 0;
                --this.nloops;
                if (this.nloops == 0) {
                    this.mod_done = true;
                }
            }
            this.row = this.break_row;
            this.break_row = 0;
            if (this.mod.positions[this.order_pos] == 255) {
                this.order_pos = 0;
                this.row = 0;
            }
            this.patt = this.mod.patterns[this.mod.positions[this.order_pos]];
            this.pattofs = this.row * 4 * this.numtracks;
            ++this.order_pos;
        } else {
            if (this.loops > 0 && this.loops > 0 && this.row == this.loopB) {
                this.row = this.loopA - 1;
                this.pattofs = this.row * 4 * this.numtracks;
                --this.loops;
            }
            if (this.loops == 0 && this.row == this.loopB + 1) {
                this.onLoop = false;
                this.loopA = this.row;
            }
        }
        ++this.row;
        for (int i = 0; i < this.numtracks; ++i) {
            this.pattofs = this.get_track(this.tracks[i], this.patt, this.pattofs);
        }
    }
}

