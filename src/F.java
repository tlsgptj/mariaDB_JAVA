import java.sql.*;

//ResultSetMetaData ( ResultSet 구조 정보 )
class F {
    String maria = "org.mariadb.jdbc.Driver";
    String mariaUrl = "jdbc:mariadb://127.0.0.1:3307/java_schema";
    Connection con;
    Statement stmt;
    String sql = "select * from DEPT";
    F(){
        try{
            Class.forName(maria);
            con = DriverManager.getConnection(mariaUrl, "scott", "tiger");
            stmt = con.createStatement();
        }catch(ClassNotFoundException ce){
            pln("드라이버로딩 실패: " + ce);
        }catch(SQLException se){
            pln("연결 실패: " + se);
        }
        createRs();
    }
    void createRs(){
        ResultSet rs = null;
        try{
            rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            int cc = rsmd.getColumnCount();
            for(int i=1; i<=cc; i++){
                String cn = rsmd.getColumnName(i);
                p(cn+"\t");
            }
            pln("");
            for(int i=1; i<=cc; i++){
                p("----------");
            }
            pln("");
            while(rs.next()){
                for(int i=1; i<=cc; i++){
                    String data = rs.getString(i);
                    p(data + "\t");
                }
                pln("");
            }
        }catch(SQLException se){
        }finally{
            try{
                if(rs != null) rs.close();
            }catch(SQLException se){}
        }
    }
    void pln(String str){
        System.out.println(str);
    }
    void p(String str){
        System.out.print(str);
    }
    public static void main(String[] args){
        new F();
    }
}