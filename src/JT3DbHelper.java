//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

class JT3DbHelper {
    Connection con;
    Statement stmt;
    PreparedStatement pstmt1;
    PreparedStatement pstmt2;
    PreparedStatement pstmt3;
    PreparedStatement pstmt4;
    String url;
    String usr;
    String pwd;
    String setFile = "C:\\demo\\MariaDB_Java\\src\\setting.txt";

    JT3DbHelper() {
        this.readSetting();
        String var1 = "insert into DEPT values(?,?,?)";
        String var2 = "update DEPT set DNAME=?, LOC=? where DEPTNO=?";
        String var3 = "delete from DEPT where DEPTNO=?";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.con = DriverManager.getConnection(this.url, this.usr, this.pwd);
            this.stmt = this.con.createStatement();
            this.pstmt1 = this.con.prepareStatement(var1);
            this.pstmt2 = this.con.prepareStatement(var2);
            this.pstmt3 = this.con.prepareStatement(var3);
        } catch (ClassNotFoundException var5) {
        } catch (SQLException var6) {
        }

    }

    void readSetting() {
        FileReader var1 = null;
        BufferedReader var2 = null;

        try {
            var1 = new FileReader(this.setFile);
            var2 = new BufferedReader(var1);
            this.url = var2.readLine();
            if (this.url != null) {
                this.url = this.url.trim();
            }

            this.usr = var2.readLine();
            if (this.usr != null) {
                this.usr = this.usr.trim();
            }

            this.pwd = var2.readLine();
            if (this.pwd != null) {
                this.pwd = this.pwd.trim();
            }
        } catch (FileNotFoundException var14) {
            System.out.println(this.setFile + "파일이 없음");
        } catch (IOException var15) {
        } finally {
            try {
                if (var2 != null) {
                    var2.close();
                }

                if (var1 != null) {
                    var1.close();
                }
            } catch (IOException var13) {
            }

        }

    }

    Vector<String> getCN() {
        ResultSet var1 = null;
        Object var2 = null;
        String var3 = "select * from DEPT order by DEPTNO";
        Vector var4 = new Vector();

        Vector var6;
        try {
            var1 = this.stmt.executeQuery(var3);
            ResultSetMetaData var18 = var1.getMetaData();
            int var5 = var18.getColumnCount();

            for(int var19 = 1; var19 <= var5; ++var19) {
                String var7 = var18.getColumnName(var19);
                var4.add(var7);
            }

            var6 = var4;
            return var6;
        } catch (SQLException var16) {
            var6 = null;
        } finally {
            try {
                if (var1 != null) {
                    var1.close();
                }
            } catch (SQLException var15) {
            }

        }

        return var6;
    }

    Vector<Vector> select() {
        ResultSet var1 = null;
        String var2 = "select * from DEPT order by DEPTNO";
        Vector var3 = new Vector();

        Object var5;
        try {
            var1 = this.stmt.executeQuery(var2);

            while(var1.next()) {
                Vector var4 = new Vector();
                String var19 = var1.getString(1);
                String var6 = var1.getString(2);
                String var7 = var1.getString(3);
                var4.add(var19);
                var4.add(var6);
                var4.add(var7);
                var3.add(var4);
            }

            Vector var18 = var3;
            return var18;
        } catch (SQLException var16) {
            var5 = null;
        } finally {
            try {
                if (var1 != null) {
                    var1.close();
                }
            } catch (SQLException var15) {
            }

        }

        return (Vector<Vector>)var5;
    }

    void createPstmt4(String var1) {
        String var2 = "select * from DEPT where " + var1 + " like ? order by DEPTNO";

        try {
            this.pstmt4 = this.con.prepareStatement(var2);
        } catch (SQLException var4) {
        }

    }

    Vector<Vector> select(String var1) {
        ResultSet var2 = null;
        Vector var3 = new Vector();

        Object var5;
        try {
            this.pstmt4.setString(1, "%" + var1 + "%");
            var2 = this.pstmt4.executeQuery();

            while(var2.next()) {
                Vector var4 = new Vector();
                String var19 = var2.getString(1);
                String var6 = var2.getString(2);
                String var7 = var2.getString(3);
                var4.add(var19);
                var4.add(var6);
                var4.add(var7);
                var3.add(var4);
            }

            Vector var18 = var3;
            return var18;
        } catch (SQLException var16) {
            var5 = null;
        } finally {
            try {
                if (var2 != null) {
                    var2.close();
                }
            } catch (SQLException var15) {
            }

        }

        return (Vector<Vector>)var5;
    }

    int insert(int var1, String var2, String var3) {
        try {
            this.pstmt1.setInt(1, var1);
            this.pstmt1.setString(2, var2);
            this.pstmt1.setString(3, var3);
            int var4 = this.pstmt1.executeUpdate();
            return var4;
        } catch (SQLException var5) {
            return -1;
        }
    }

    int update(int var1, String var2, String var3) {
        try {
            this.pstmt2.setString(1, var2);
            this.pstmt2.setString(2, var3);
            this.pstmt2.setInt(3, var1);
            int var4 = this.pstmt2.executeUpdate();
            return var4;
        } catch (SQLException var5) {
            return -1;
        }
    }

    int delete(int var1) {
        try {
            this.pstmt3.setInt(1, var1);
            int var2 = this.pstmt3.executeUpdate();
            return var2;
        } catch (SQLException var3) {
            return -1;
        }
    }

    void closeAll() {
        try {
            if (this.pstmt4 != null) {
                this.pstmt4.close();
            }

            if (this.pstmt3 != null) {
                this.pstmt3.close();
            }

            if (this.pstmt2 != null) {
                this.pstmt2.close();
            }

            if (this.pstmt1 != null) {
                this.pstmt1.close();
            }

            if (this.stmt != null) {
                this.stmt.close();
            }

            if (this.con != null) {
                this.con.close();
            }
        } catch (SQLException var2) {
        }

    }
}
