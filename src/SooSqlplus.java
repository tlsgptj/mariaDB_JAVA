import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SooSqlplus {
    Connection con;
    Statement stmt;
    BufferedReader br;
    ResultSet rs;
    String line;
    String ip;
    String sid;
    String id;
    String pwd;

    public SooSqlplus() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
        this.line = "---------------";
        this.inputIp();
    }

    void inputIp() {
        this.p("Oracle IP(default:127.0.0.1) : ");

        try {
            String var1 = this.br.readLine();
            if (var1.length() == 0) {
                var1 = "127.0.0.1";
            }

            if (var1 != null) {
                var1 = var1.trim();
                this.ip = var1;
            }
        } catch (IOException var2) {
            this.inputIp();
        }

        this.inputSid();
    }

    void inputSid() {
        this.p("Oracle SID(default:JAVA) : ");

        try {
            String var1 = this.br.readLine();
            if (var1.length() == 0) {
                var1 = "JAVA";
            }

            if (var1 != null) {
                var1 = var1.trim();
                this.sid = var1;
            }
        } catch (IOException var2) {
            this.inputSid();
        }

        this.inputId();
    }

    void inputId() {
        this.p("ID(default:scott) : ");

        try {
            String var1 = this.br.readLine();
            if (var1.length() == 0) {
                var1 = "scott";
            }

            if (var1 != null) {
                var1 = var1.trim();
                this.id = var1;
            }
        } catch (IOException var2) {
            this.inputId();
        }

        this.inputPwd();
    }

    void inputPwd() {
        this.p("PWD(default:tiger) : ");

        try {
            String var1 = this.br.readLine();
            if (var1.length() == 0) {
                var1 = "tiger";
            }

            if (var1 != null) {
                var1 = var1.trim();
                this.pwd = var1;
            }
        } catch (IOException var2) {
            this.inputPwd();
        }

        this.init();
    }

    void init() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String var1 = "jdbc:oracle:thin:@" + this.ip + ":1521:" + this.sid;
            this.con = DriverManager.getConnection(var1, this.id, this.pwd);
            this.stmt = this.con.createStatement();
            this.input();
        } catch (ClassNotFoundException var6) {
            this.pln((Object)var6);
        } catch (SQLException var7) {
            this.pln("Oracle 접속 정보가 잘못됨.. 다시 입력하세요.");
            this.inputIp();
        } finally {
            this.finish();
        }

    }

    public void input() {
        String var3;
        try {
            do {
                this.p("SQL>");
                var3 = this.br.readLine();
                var3 = var3.trim();
                if (var3.endsWith(";")) {
                    var3 = var3.replaceAll(";", "");
                }

                if (var3.equalsIgnoreCase("SHOW_SCHEMA")) {
                    showSchema();
                } else {
                    this.run(var3);
                }
            } while (!var3.equalsIgnoreCase("quit") && !var3.equalsIgnoreCase("q") && !var3.equalsIgnoreCase("exit"));
        } catch (IOException var2) {
            this.input();
        }
    }

    public void run(String var1) {
        int var2 = var1.indexOf(" ");
        if (var2 != -1) {
            String var3 = var1.substring(0, var2);

            try {
                if (var3.equalsIgnoreCase("SELECT")) {
                    this.rs = this.stmt.executeQuery(var1);
                    ResultSetMetaData var4 = this.rs.getMetaData();
                    int var5 = var4.getColumnCount();

                    for (int var6 = 1; var6 <= var5; ++var6) {
                        String var7 = var4.getColumnName(var6);
                        this.p(var7 + "\t\t");
                    }

                    this.pln("");

                    for (int var10 = 1; var10 <= var5; ++var10) {
                        this.p(this.line);
                    }

                    this.pln("");

                    while (this.rs.next()) {
                        for (int var11 = 1; var11 <= var5; ++var11) {
                            String var12 = this.rs.getString(var11);
                            if (var12 != null) {
                                if (var12.length() > 15) {
                                    var12 = var12.substring(0, 15);
                                    var12 = var12 + "..";
                                    this.pln(var12 + " ");
                                } else if (var12.length() <= 7) {
                                    this.p(var12 + "\t\t");
                                } else {
                                    this.p(var12 + "\t");
                                }
                            } else {
                                this.p(var12 + "\t\t");
                            }
                        }

                        this.pln("");
                    }
                } else if (!var3.equalsIgnoreCase("INSERT") && !var3.equalsIgnoreCase("UPDATE") && !var3.equalsIgnoreCase("DELETE")) {
                    this.stmt.execute(var1);
                    this.pln(var3 + "문 성공");
                } else {
                    int var9 = this.stmt.executeUpdate(var1);
                    if (var9 >= 0) {
                        this.pln(var9 + "개의 row가 변경 완료");
                    } else {
                        this.pln("UPDATE 실패");
                    }
                }
            } catch (SQLException var8) {
                this.pln("잘못된 SQL문입니다.");
            }

        }
    }

    // Add a method to display database schema information
    void showSchema() {
        try {
            DatabaseMetaData meta = con.getMetaData();
            rs = meta.getTables(null, null, "%", new String[]{"TABLE"});
            System.out.println("Tables in the database:");

            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                System.out.println(tableName);
                showColumns(tableName);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving schema information.");
        }
    }

    // Add a method to display column information for a given table
    void showColumns(String tableName) {
        try {
            DatabaseMetaData meta = con.getMetaData();
            rs = meta.getColumns(null, null, tableName, "%");
            System.out.println("Columns in " + tableName + ":");

            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String columnType = rs.getString("TYPE_NAME");
                int columnSize = rs.getInt("COLUMN_SIZE");
                System.out.println("\t" + columnName + " - " + columnType + "(" + columnSize + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving column information for table " + tableName);
        }
    }

    public void finish() {
        try {
            if (this.rs != null) {
                this.rs.close();
            }

            this.stmt.close();
            this.con.close();
            this.br.close();
        } catch (SQLException var2) {
        } catch (IOException var3) {
        }

    }

    public void p(String var1) {
        System.out.print(var1);
    }

    public void pln(String var1) {
        System.out.println(var1);
    }

    public void pln(Object var1) {
        System.out.println(var1.toString());
    }

    public static void main(String[] var0) {
        new SooSqlplus();
    }
}
