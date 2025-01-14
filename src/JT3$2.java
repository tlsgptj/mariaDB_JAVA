//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

class JT3$2 extends KeyAdapter {
    private final JT3 this$0;
    Vector<Vector> vv;

    JT3$2(JT3 var1) {
        this.this$0 = var1;
        this.vv = null;
    }

    public void keyReleased(KeyEvent var1) {
        String var2 = this.this$0.tfWord.getText();
        if (var2 != null) {
            var2 = var2.trim();
        }

        if (var2.length() != 0) {
            this.vv = this.this$0.h.select(var2);
        } else {
            this.vv = this.this$0.h.select();
        }

        this.this$0.model.setDataVector(this.vv, this.this$0.columnNames);
    }
}
