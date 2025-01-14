//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class JT3$3 extends WindowAdapter {
    private final JT3 this$0;

    JT3$3(JT3 var1) {
        this.this$0 = var1;
    }

    public void windowDeactivated(WindowEvent var1) {
        this.this$0.t.clearSelection();
        this.this$0.tf1.setText("");
        this.this$0.tf2.setText("");
        this.this$0.tf3.setText("");
        this.this$0.tf1.setEditable(true);
    }

    public void windowOpened(WindowEvent var1) {
        this.this$0.tfWord.requestFocus();
    }

    public void windowClosing(WindowEvent var1) {
        this.this$0.h.closeAll();
    }
}