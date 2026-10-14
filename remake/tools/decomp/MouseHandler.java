/*
 * Decompiled with CFR 0.152.
 */
import java.awt.PopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class MouseHandler
extends MouseAdapter {
    private PopupMenu popupMenu;
    int id;

    public MouseHandler(PopupMenu popupMenu, int n) {
        this.popupMenu = popupMenu;
        this.id = n;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.isPopupTrigger()) {
            this.popupMenu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
            Madness.textid = this.id;
            mouseEvent.consume();
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.isPopupTrigger()) {
            this.popupMenu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
            Madness.textid = this.id;
            mouseEvent.consume();
        }
    }
}

