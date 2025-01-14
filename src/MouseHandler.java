//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class MouseHandler extends MouseAdapter {
    JT3 jt;

    MouseHandler(JT3 var1) {
        this.jt = var1;
    }

    public void mouseClicked(MouseEvent var1) {
        int var2 = this.jt.t.getSelectedRow();
        this.jt.tf1.setText(this.jt.model.getValueAt(var2, 0).toString());
        this.jt.tf2.setText(this.jt.model.getValueAt(var2, 1).toString());
        this.jt.tf3.setText(this.jt.model.getValueAt(var2, 2).toString());
        this.jt.tf1.setEditable(false);
    }
}