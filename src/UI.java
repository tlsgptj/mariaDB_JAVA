import javax.swing.*;
import java.awt.*;

public class UI extends JFrame{
    void init() {
        JTable t;
        Container cp = getContentPane();
        t = new JTable(ALLBITS, ABORT);
        JScrollPane sp = new JScrollPane(t);
        cp.add(sp);
        setUI();
    }
    void setUI() {
        setTitle("JTable Test1");
        setSize(500, 200);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String args[]) {
        new UI().init();
    }
}
