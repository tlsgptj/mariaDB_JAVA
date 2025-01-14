import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class JT3$1 implements ActionListener {
    private final JT3 this$0;

    JT3$1(JT3 var1) {
        this.this$0 = var1;
    }

    public void actionPerformed(ActionEvent var1) {
        String var2 = this.this$0.cb.getSelectedItem().toString();
        this.this$0.h.createPstmt4(var2);
    }
}
