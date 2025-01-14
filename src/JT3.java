import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class JT3 extends JFrame implements ActionListener {
    Vector<String> columnNames;
    Vector<Vector> rowData;
    JTextField tf1;
    JTextField tf2;
    JTextField tf3;
    JTextField tfWord;
    JButton b1;
    JButton b2;
    JButton b3;
    JTable t;
    DefaultTableModel model;
    JT3DbHelper h = new JT3DbHelper();
    JComboBox cb;

    JT3() {
        this.columnNames = this.h.getCN();
        this.rowData = this.h.select();
    }

    void init() {
        Container var1 = this.getContentPane();
        JPanel var2 = new JPanel();
        var2.setLayout(new GridLayout(1, 2));
        this.cb = new JComboBox(this.columnNames);
        this.h.createPstmt4("DEPTNO");
        this.cb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                String var2 = JT3.this.cb.getSelectedItem().toString();
                JT3.this.h.createPstmt4(var2);
            }
        });
        this.tfWord = new JTextField();
        this.tfWord.addKeyListener(new KeyAdapter() {
            Vector<Vector> vv = null;

            public void keyReleased(KeyEvent var1) {
                String var2 = JT3.this.tfWord.getText();
                if (var2 != null) {
                    var2 = var2.trim();
                }

                if (var2.length() != 0) {
                    this.vv = JT3.this.h.select(var2);
                } else {
                    this.vv = JT3.this.h.select();
                }

                JT3.this.model.setDataVector(this.vv, JT3.this.columnNames);
            }
        });
        var2.add(this.cb);
        var2.add(this.tfWord);
        var1.add(var2, "North");
        this.model = new DefaultTableModel(this.rowData, this.columnNames);
        this.t = new JTable(this.model);
        this.t.addMouseListener(new MouseHandler(this));
        JScrollPane var3 = new JScrollPane(this.t);
        var1.add(var3, "Center");
        JPanel var4 = new JPanel();
        var4.setLayout(new GridLayout(2, 1));
        JPanel var5 = new JPanel();
        var5.setLayout(new GridLayout(1, 3));
        this.tf1 = new JTextField();
        this.tf2 = new JTextField();
        this.tf3 = new JTextField();
        var5.add(this.tf1);
        var5.add(this.tf2);
        var5.add(this.tf3);
        var4.add(var5);
        JPanel var6 = new JPanel();
        var6.setLayout(new GridLayout(1, 3));
        this.b1 = new JButton("추가");
        this.b2 = new JButton("수정");
        this.b3 = new JButton("삭제");
        var6.add(this.b1);
        var6.add(this.b2);
        var6.add(this.b3);
        this.b1.addActionListener(this);
        this.b2.addActionListener(this);
        this.b3.addActionListener(this);
        var4.add(var6);
        var1.add(var4, "South");
        this.setUI();
        this.addWindowListener(new WindowAdapter() {
            public void windowDeactivated(WindowEvent var1) {
                JT3.this.t.clearSelection();
                JT3.this.tf1.setText("");
                JT3.this.tf2.setText("");
                JT3.this.tf3.setText("");
                JT3.this.tf1.setEditable(true);
            }

            public void windowOpened(WindowEvent var1) {
                JT3.this.tfWord.requestFocus();
            }

            public void windowClosing(WindowEvent var1) {
                JT3.this.h.closeAll();
            }
        });
    }

    void setUI() {
        this.setTitle("JTable Test3");
        this.setSize(300, 400);
        this.setVisible(true);
        this.setLocation(500, 100);
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
    }

    public void actionPerformed(ActionEvent var1) {
        Object var2 = var1.getSource();
        if (var2 == this.b1) {
            String var3 = this.tf1.getText();
            String var4 = this.tf2.getText();
            String var5 = this.tf3.getText();
            int var6 = this.filter(var3);
            if (var6 == -1 || var6 == -2) {
                JOptionPane.showMessageDialog((Component)null, "잘못된 PK컬럼데이터입니다");
                this.tf1.setText("");
                this.tf1.requestFocus();
                return;
            }

            int var7 = this.h.insert(var6, var4, var5);
            if (var7 == -1) {
                JOptionPane.showMessageDialog((Component)null, var3 + "부서는 이미 존재합니다");
            }

            Vector var8 = this.h.select();
            this.model.setDataVector(var8, this.columnNames);
        } else if (var2 == this.b2) {
            String var9 = this.tf1.getText();
            String var11 = this.tf2.getText();
            String var13 = this.tf3.getText();
            int var15 = this.filter(var9);
            if (var15 == -1 || var15 == -2) {
                JOptionPane.showMessageDialog((Component)null, "잘못된 PK컬럼데이터입니다");
                this.tf1.setText("");
                this.tf1.requestFocus();
                return;
            }

            int var17 = this.h.update(var15, var11, var13);
            if (var17 == 0) {
                JOptionPane.showMessageDialog((Component)null, var9 + "란 부서는 존재하지 않습니다");
            }

            Vector var18 = this.h.select();
            this.model.setDataVector(var18, this.columnNames);
        } else {
            String var10 = this.tf1.getText();
            int var12 = this.filter(var10);
            if (var12 == -1 || var12 == -2) {
                JOptionPane.showMessageDialog((Component)null, "잘못된 PK컬럼데이터입니다");
                this.tf1.setText("");
                this.tf1.requestFocus();
                return;
            }

            int var14 = this.h.delete(var12);
            if (var14 == 0) {
                JOptionPane.showMessageDialog((Component)null, var10 + "란 부서는 존재하지 않습니다");
            }

            Vector var16 = this.h.select();
            this.model.setDataVector(var16, this.columnNames);
        }

        this.tf1.setText("");
        this.tf2.setText("");
        this.tf3.setText("");
    }

    int filter(String var1) {
        if (var1 != null) {
            var1 = var1.trim();
        }

        if (var1.length() == 0) {
            return -1;
        } else {
            try {
                int var2 = Integer.parseInt(var1);
                return var2;
            } catch (NumberFormatException var3) {
                return -2;
            }
        }
    }

    void pln(String var1) {
        System.out.println(var1);
    }

    public static void main(String[] var0) {
        JT3 var1 = new JT3();
        var1.init();
    }
}
