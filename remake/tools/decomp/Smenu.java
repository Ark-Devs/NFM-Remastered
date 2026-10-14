/*
 * Decompiled with CFR 0.152.
 */
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Smenu {
    int sel = 0;
    String[] opts;
    String[] sopts;
    int no = 0;
    int x = 0;
    int y = 0;
    Font font = new Font("Arial", 1, 13);
    Color bcol = new Color(255, 255, 255);
    Color fcol = new Color(0, 0, 0);
    int w = 0;
    int h = 0;
    FontMetrics ftm;
    boolean show = false;
    boolean open = false;
    boolean dis = false;
    int maxl = 0;
    boolean rooms = false;
    int[] iroom;
    int kmoused = 0;
    boolean alphad = false;
    boolean revup = false;
    boolean carsel = false;
    boolean flksel = false;
    boolean om = false;
    boolean onsc = false;
    int scro = 0;
    int scra = 0;

    public Smenu(int n) {
        this.opts = new String[n];
        this.sopts = new String[n];
    }

    public void add(Graphics2D graphics2D, String string) {
        int n;
        graphics2D.setFont(this.font);
        this.ftm = graphics2D.getFontMetrics();
        if (!(this.rooms && this.no != 0 || this.ftm.stringWidth(string) + 30 <= this.w)) {
            this.w = this.ftm.stringWidth(string) + 30;
        }
        if (this.rooms) {
            this.iroom = new int[7];
            for (n = 0; n < 7; ++n) {
                this.iroom[n] = 0;
            }
        }
        this.opts[this.no] = string;
        if (this.maxl != 0) {
            n = string.length();
            while (this.ftm.stringWidth(string.substring(0, n)) + 30 > this.maxl) {
                --n;
            }
            if (n != string.length()) {
                string = string.substring(0, n - 3);
                string = string + "...";
            }
        }
        this.sopts[this.no] = string;
        if (this.no < this.opts.length - 1) {
            ++this.no;
        }
    }

    public void addw(String string, String string2) {
        this.w = 300;
        this.opts[this.no] = string2;
        this.sopts[this.no] = string;
        if (this.no < this.opts.length - 1) {
            ++this.no;
        }
    }

    public void addstg(String string) {
        if (this.ftm.stringWidth(string) + 30 > this.w) {
            this.w = this.ftm.stringWidth(string) + 30;
        }
        ++this.no;
        if (this.no > 701) {
            this.no = 701;
        }
        for (int i = this.no - 1; i > 1; --i) {
            this.opts[i] = this.opts[i - 1];
            this.sopts[i] = this.sopts[i - 1];
        }
        this.opts[1] = string;
        this.sopts[1] = string;
    }

    public void removeAll() {
        this.no = 0;
        this.w = 0;
        this.sel = 0;
    }

    public void select(int n) {
        if (n >= 0 && n < this.no) {
            this.sel = n;
        }
    }

    public void select(String string) {
        for (int i = 0; i < this.no; ++i) {
            if (!this.opts[i].equals(string)) continue;
            this.sel = i;
            break;
        }
    }

    public int getSelectedIndex() {
        return this.sel;
    }

    public String getSelectedItem() {
        return this.opts[this.sel];
    }

    public String getItem(int n) {
        String string = "";
        if (n >= 0 && n < this.no) {
            string = this.opts[n];
        }
        return string;
    }

    public int getItemCount() {
        return this.no;
    }

    public void remove(String string) {
        for (int i = 0; i < this.no; ++i) {
            if (!this.opts[i].equals(string)) continue;
            for (int j = i; j < this.no; ++j) {
                if (j == this.no - 1) continue;
                this.opts[j] = this.opts[j + 1];
                this.sopts[j] = this.sopts[j + 1];
            }
            --this.no;
            break;
        }
    }

    public void setSize(int n, int n2) {
        this.w = n;
        this.h = n2;
    }

    public int getWidth() {
        return this.w;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setBackground(Color color) {
        this.bcol = color;
    }

    public void setForeground(Color color) {
        this.fcol = color;
    }

    public Color getBackground() {
        return this.bcol;
    }

    public Color getForeground() {
        return this.fcol;
    }

    public void hide() {
        this.show = false;
        this.open = false;
    }

    public void show() {
        this.show = true;
    }

    public boolean isShowing() {
        return this.show;
    }

    public void move(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    public boolean hasFocus() {
        return false;
    }

    public void disable() {
        this.dis = true;
    }

    public void enable() {
        this.dis = false;
    }

    public boolean isEnabled() {
        return !this.dis;
    }

    public boolean draw(Graphics2D graphics2D, int n, int n2, boolean bl, int n3, boolean bl2) {
        boolean bl3 = false;
        if (this.revup) {
            bl2 = !bl2;
            this.revup = false;
        }
        if (this.show) {
            if (this.alphad) {
                graphics2D.setComposite(AlphaComposite.getInstance(3, 0.7f));
            }
            boolean bl4 = false;
            if (bl) {
                if (!this.om) {
                    this.om = true;
                    bl4 = true;
                }
            } else if (this.om) {
                this.om = false;
            }
            boolean bl5 = false;
            if ((this.bcol.getRed() + this.bcol.getGreen() + this.bcol.getBlue()) / 3 > (this.fcol.getRed() + this.fcol.getGreen() + this.fcol.getBlue()) / 3) {
                bl5 = true;
            }
            boolean bl6 = false;
            if (n > this.x && n < this.x + this.w && n2 > this.y + 1 && n2 < this.y + 22 && !this.open && !this.dis) {
                bl6 = true;
            }
            if (!this.open && bl6 && bl4 && !this.dis) {
                this.open = true;
                bl4 = false;
            }
            graphics2D.setFont(this.font);
            this.ftm = graphics2D.getFontMetrics();
            if (this.open) {
                int n4 = 4 + (this.ftm.getHeight() + 2) * this.no;
                if (!bl2) {
                    int n5;
                    int n6 = 0;
                    graphics2D.setColor(this.bcol);
                    graphics2D.fillRect(this.x, this.y + 23, this.w, n4);
                    graphics2D.setColor(new Color((this.fcol.getRed() + this.bcol.getRed()) / 2, (this.fcol.getGreen() + this.bcol.getGreen()) / 2, (this.fcol.getBlue() + this.bcol.getBlue()) / 2));
                    graphics2D.drawRect(this.x, this.y + 23, this.w, n4);
                    if (this.y + 23 + n4 > n3) {
                        graphics2D.drawLine(this.x + this.w - 18, this.y + 17, this.x + this.w - 18, n3);
                        if (bl5) {
                            graphics2D.setColor(new Color((this.bcol.getRed() + 510) / 3, (this.bcol.getGreen() + 510) / 3, (this.bcol.getBlue() + 510) / 3));
                        } else {
                            graphics2D.setColor(new Color((this.fcol.getRed() + 510) / 3, (this.fcol.getGreen() + 510) / 3, (this.fcol.getBlue() + 510) / 3));
                        }
                        graphics2D.fillRect(this.x + this.w - 15, this.y + 25 + this.scra, 13, 30);
                        graphics2D.setColor(this.fcol);
                        graphics2D.drawRect(this.x + this.w - 15, this.y + 25 + this.scra, 12, 30);
                        graphics2D.setColor(new Color(0, 0, 0));
                        graphics2D.drawLine(this.x + this.w - 12, this.y + 9 + 29 + this.scra, this.x + this.w - 12, this.y + 10 + 29 + this.scra);
                        graphics2D.drawLine(this.x + this.w - 11, this.y + 10 + 29 + this.scra, this.x + this.w - 11, this.y + 11 + 29 + this.scra);
                        graphics2D.drawLine(this.x + this.w - 10, this.y + 11 + 29 + this.scra, this.x + this.w - 10, this.y + 12 + 29 + this.scra);
                        graphics2D.drawLine(this.x + this.w - 9, this.y + 12 + 29 + this.scra, this.x + this.w - 9, this.y + 13 + 29 + this.scra);
                        graphics2D.drawLine(this.x + this.w - 8, this.y + 11 + 29 + this.scra, this.x + this.w - 8, this.y + 12 + 29 + this.scra);
                        graphics2D.drawLine(this.x + this.w - 7, this.y + 10 + 29 + this.scra, this.x + this.w - 7, this.y + 11 + 29 + this.scra);
                        graphics2D.drawLine(this.x + this.w - 6, this.y + 9 + 29 + this.scra, this.x + this.w - 6, this.y + 10 + 29 + this.scra);
                        n6 = -18;
                        if (bl) {
                            if (n > this.x + this.w - 18 && n < this.x + this.w && n2 > this.y + 25 && n2 < n3) {
                                bl4 = false;
                                this.onsc = true;
                            }
                        } else if (this.onsc) {
                            this.onsc = false;
                        }
                        if (this.onsc) {
                            this.scra = n2 - (this.y + 25) - 15;
                            if (this.scra < 0) {
                                this.scra = 0;
                            }
                            if (this.scra > (n5 = n3 - (this.y + 25) - 33)) {
                                this.scra = n5;
                            }
                            int n7 = this.no * (this.ftm.getHeight() + 2) - n5 - this.ftm.getHeight() / 2;
                            this.scro = -((int)((float)this.scra * ((float)n7 / (float)n5)));
                        }
                    }
                    for (n5 = 0; n5 < this.no; ++n5) {
                        if (Math.abs(this.scro) >= (n5 + 1) * (this.ftm.getHeight() + 2)) continue;
                        graphics2D.setColor(this.fcol);
                        if (n > this.x && n < this.x + this.w && n2 > this.y + 25 + this.scro + n5 * (this.ftm.getHeight() + 2) && n2 < this.y + 25 + this.scro + (n5 + 1) * (this.ftm.getHeight() + 2)) {
                            if (bl5) {
                                graphics2D.setColor(new Color((this.fcol.getRed() + this.bcol.getRed()) / 2, (this.fcol.getGreen() + this.bcol.getGreen()) / 2, (this.fcol.getBlue() + this.bcol.getBlue()) / 2));
                            } else {
                                graphics2D.setColor(this.fcol);
                            }
                            graphics2D.fillRect(this.x + 1, this.y + 25 + this.scro + n5 * (this.ftm.getHeight() + 2), this.w - 1 + n6, this.ftm.getHeight() + 2);
                            graphics2D.setColor(this.bcol);
                            if (bl4) {
                                if (!this.rooms || !this.opts[n5].equals("full")) {
                                    this.sel = n5;
                                    if (this.rooms && n5 != 0) {
                                        this.sopts[n5] = " ";
                                    }
                                } else {
                                    this.kmoused = 20;
                                }
                                this.open = false;
                            }
                        }
                        if (this.rooms && this.sopts[n5].indexOf("10 / 10") != -1) {
                            graphics2D.setColor(new Color(255, 0, 0));
                        }
                        graphics2D.drawString(this.sopts[n5], this.x + 4, this.y + 38 + this.scro + n5 * (this.ftm.getHeight() + 2));
                    }
                    if (n6 != 0) {
                        graphics2D.setColor(new Color((this.fcol.getRed() + this.bcol.getRed()) / 2, (this.fcol.getGreen() + this.bcol.getGreen()) / 2, (this.fcol.getBlue() + this.bcol.getBlue()) / 2));
                        graphics2D.drawLine(this.x, n3 - 1, this.x + this.w, n3 - 1);
                    }
                } else {
                    int n8;
                    int n9 = 0;
                    graphics2D.setColor(this.bcol);
                    graphics2D.fillRect(this.x, this.y - n4, this.w, n4);
                    graphics2D.setColor(new Color((this.fcol.getRed() + this.bcol.getRed()) / 2, (this.fcol.getGreen() + this.bcol.getGreen()) / 2, (this.fcol.getBlue() + this.bcol.getBlue()) / 2));
                    graphics2D.drawRect(this.x, this.y - n4, this.w, n4);
                    if (this.y - n4 < 0) {
                        graphics2D.drawLine(this.x + this.w - 18, 0, this.x + this.w - 18, this.y);
                        if (bl5) {
                            graphics2D.setColor(new Color((this.bcol.getRed() + 510) / 3, (this.bcol.getGreen() + 510) / 3, (this.bcol.getBlue() + 510) / 3));
                        } else {
                            graphics2D.setColor(new Color((this.fcol.getRed() + 510) / 3, (this.fcol.getGreen() + 510) / 3, (this.fcol.getBlue() + 510) / 3));
                        }
                        graphics2D.fillRect(this.x + this.w - 15, this.y - this.scra - 33, 13, 30);
                        graphics2D.setColor(this.fcol);
                        graphics2D.drawRect(this.x + this.w - 15, this.y - this.scra - 33, 12, 30);
                        graphics2D.setColor(new Color(0, 0, 0));
                        graphics2D.drawLine(this.x + this.w - 12, this.y + 13 - 29 - this.scra, this.x + this.w - 12, this.y + 12 - 29 - this.scra);
                        graphics2D.drawLine(this.x + this.w - 11, this.y + 12 - 29 - this.scra, this.x + this.w - 11, this.y + 11 - 29 - this.scra);
                        graphics2D.drawLine(this.x + this.w - 10, this.y + 11 - 29 - this.scra, this.x + this.w - 10, this.y + 10 - 29 - this.scra);
                        graphics2D.drawLine(this.x + this.w - 9, this.y + 10 - 29 - this.scra, this.x + this.w - 9, this.y + 9 - 29 - this.scra);
                        graphics2D.drawLine(this.x + this.w - 8, this.y + 11 - 29 - this.scra, this.x + this.w - 8, this.y + 10 - 29 - this.scra);
                        graphics2D.drawLine(this.x + this.w - 7, this.y + 12 - 29 - this.scra, this.x + this.w - 7, this.y + 11 - 29 - this.scra);
                        graphics2D.drawLine(this.x + this.w - 6, this.y + 13 - 29 - this.scra, this.x + this.w - 6, this.y + 12 - 29 - this.scra);
                        n9 = -18;
                        if (bl) {
                            if (n > this.x + this.w - 18 && n < this.x + this.w && n2 > 0 && n2 < this.y - 3) {
                                bl4 = false;
                                this.onsc = true;
                            }
                        } else if (this.onsc) {
                            this.onsc = false;
                        }
                        if (this.onsc) {
                            this.scra = this.y - 3 - 15 - n2;
                            if (this.scra < 0) {
                                this.scra = 0;
                            }
                            if (this.scra > (n8 = this.y - 35)) {
                                this.scra = n8;
                            }
                            int n10 = this.no * (this.ftm.getHeight() + 2) - n8 - this.ftm.getHeight() / 2;
                            this.scro = (int)((float)this.scra * ((float)n10 / (float)n8));
                        }
                    }
                    for (n8 = 0; n8 < this.no; ++n8) {
                        if (Math.abs(this.scro) >= (n8 + 1) * (this.ftm.getHeight() + 2)) continue;
                        graphics2D.setColor(this.fcol);
                        if (n > this.x && n < this.x + this.w && n2 < this.y - 18 + this.scro - (n8 - 1) * (this.ftm.getHeight() + 2) && n2 > this.y - 18 + this.scro - n8 * (this.ftm.getHeight() + 2)) {
                            if (bl5) {
                                graphics2D.setColor(new Color((this.fcol.getRed() + this.bcol.getRed()) / 2, (this.fcol.getGreen() + this.bcol.getGreen()) / 2, (this.fcol.getBlue() + this.bcol.getBlue()) / 2));
                            } else {
                                graphics2D.setColor(this.fcol);
                            }
                            graphics2D.fillRect(this.x + 1, this.y - 18 + this.scro - n8 * (this.ftm.getHeight() + 2), this.w - 1 + n9, this.ftm.getHeight() + 2);
                            graphics2D.setColor(this.bcol);
                            if (bl4) {
                                this.sel = n8;
                                this.open = false;
                            }
                        }
                        graphics2D.drawString(this.sopts[n8], this.x + 4, this.y - 5 + this.scro - n8 * (this.ftm.getHeight() + 2));
                    }
                    if (n9 != 0) {
                        graphics2D.setColor(new Color((this.fcol.getRed() + this.bcol.getRed()) / 2, (this.fcol.getGreen() + this.bcol.getGreen()) / 2, (this.fcol.getBlue() + this.bcol.getBlue()) / 2));
                        graphics2D.drawLine(this.x, 0, this.x + this.w, 0);
                    }
                }
                bl3 = true;
                if (bl4) {
                    this.open = false;
                }
            } else {
                if (this.scro != 0) {
                    this.scro = 0;
                }
                if (this.scra != 0) {
                    this.scra = 0;
                }
            }
            if (bl6) {
                if (bl5) {
                    graphics2D.setColor(new Color((this.fcol.getRed() + this.bcol.getRed()) / 2, (this.fcol.getGreen() + this.bcol.getGreen()) / 2, (this.fcol.getBlue() + this.bcol.getBlue()) / 2));
                } else {
                    graphics2D.setColor(this.fcol);
                }
            } else {
                graphics2D.setColor(this.bcol);
            }
            graphics2D.fillRect(this.x, this.y + 1, this.w, 21);
            graphics2D.setColor(new Color((this.fcol.getRed() + this.bcol.getRed()) / 2, (this.fcol.getGreen() + this.bcol.getGreen()) / 2, (this.fcol.getBlue() + this.bcol.getBlue()) / 2));
            graphics2D.drawRect(this.x, this.y + 1, this.w, 21);
            if (bl5) {
                graphics2D.setColor(new Color((this.bcol.getRed() + 255) / 2, (this.bcol.getGreen() + 255) / 2, (this.bcol.getBlue() + 255) / 2));
            } else {
                graphics2D.setColor(new Color((this.fcol.getRed() + 255) / 2, (this.fcol.getGreen() + 255) / 2, (this.fcol.getBlue() + 255) / 2));
            }
            graphics2D.drawRect(this.x + 1, this.y + 2, this.w - 2, 19);
            graphics2D.drawLine(this.x + this.w - 17, this.y + 3, this.x + this.w - 17, this.y + 20);
            graphics2D.setColor(this.fcol);
            if (this.dis) {
                graphics2D.setColor(new Color((this.fcol.getRed() + this.bcol.getRed() * 2) / 3, (this.fcol.getGreen() + this.bcol.getGreen() * 2) / 3, (this.fcol.getBlue() + this.bcol.getBlue() * 2) / 3));
            }
            graphics2D.drawRect(this.x + this.w - 16, this.y + 3, 14, 17);
            if (bl5) {
                graphics2D.setColor(new Color((this.bcol.getRed() + 510) / 3, (this.bcol.getGreen() + 510) / 3, (this.bcol.getBlue() + 510) / 3));
            } else {
                graphics2D.setColor(new Color((this.fcol.getRed() + 510) / 3, (this.fcol.getGreen() + 510) / 3, (this.fcol.getBlue() + 510) / 3));
            }
            graphics2D.fillRect(this.x + this.w - 15, this.y + 4, 13, 16);
            graphics2D.setColor(new Color(0, 0, 0));
            if (this.dis) {
                graphics2D.setColor(new Color((this.fcol.getRed() + this.bcol.getRed() * 2) / 3, (this.fcol.getGreen() + this.bcol.getGreen() * 2) / 3, (this.fcol.getBlue() + this.bcol.getBlue() * 2) / 3));
            }
            graphics2D.drawLine(this.x + this.w - 12, this.y + 9, this.x + this.w - 12, this.y + 10);
            graphics2D.drawLine(this.x + this.w - 11, this.y + 10, this.x + this.w - 11, this.y + 11);
            graphics2D.drawLine(this.x + this.w - 10, this.y + 11, this.x + this.w - 10, this.y + 12);
            graphics2D.drawLine(this.x + this.w - 9, this.y + 12, this.x + this.w - 9, this.y + 13);
            graphics2D.drawLine(this.x + this.w - 8, this.y + 11, this.x + this.w - 8, this.y + 12);
            graphics2D.drawLine(this.x + this.w - 7, this.y + 10, this.x + this.w - 7, this.y + 11);
            graphics2D.drawLine(this.x + this.w - 6, this.y + 9, this.x + this.w - 6, this.y + 10);
            if (bl6) {
                graphics2D.setColor(this.bcol);
            } else {
                graphics2D.setColor(this.fcol);
            }
            if (this.dis) {
                graphics2D.setColor(new Color((this.fcol.getRed() + this.bcol.getRed() * 2) / 3, (this.fcol.getGreen() + this.bcol.getGreen() * 2) / 3, (this.fcol.getBlue() + this.bcol.getBlue() * 2) / 3));
            }
            if (this.carsel && !bl6) {
                if (this.flksel) {
                    graphics2D.setColor(new Color(240, 240, 240));
                    this.flksel = false;
                } else {
                    this.flksel = true;
                }
            }
            graphics2D.drawString(this.sopts[this.sel], this.x + 4, this.y + (this.ftm.getHeight() + 2));
            if (this.alphad) {
                graphics2D.setComposite(AlphaComposite.getInstance(3, 1.0f));
            }
        } else {
            if (this.scro != 0) {
                this.scro = 0;
            }
            if (this.scra != 0) {
                this.scra = 0;
            }
        }
        return bl3;
    }
}

