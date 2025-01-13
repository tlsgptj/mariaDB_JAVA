import java.sql.*;

//CallableStatement
class E {
    String maria = "org.mariadb.jdbc.Driver";
    String mariaUrl = "jdbc:mariadb://127.0.0.1:3307/java_schema";
    Connection con;
    CallableStatement cstmt;
    String sql = "select * from JDBCT order by NO desc";

    E(){
        try{
            Class.forName(maria);
            con = DriverManager.getConnection(mariaUrl, "scott", "tiger");
            cstmt = con.prepareCall(sql);
        }catch(ClassNotFoundException ce){
            pln("드라이버로딩 실패: " + ce);
        }catch(SQLException se){
            pln("연결 실패: " + se);
        }
    }

    void call(int empno, int rate){
        try{
            cstmt.setInt(1, empno);
            cstmt.setInt(2, rate);
            cstmt.execute();
            pln("호출성공("+empno+"번 사원급여 인상완료)");
        }catch(SQLException se){
        }finally{
            try{
                cstmt.close();
                con.close();
            }catch(SQLException se){}
        }
    }
    void pln(String str){
        System.out.println(str);
    }
    void p(String str){
        System.out.print(str);
    }
    public static void main(String[] args) {
        D d = new D();
        d.call(7369, 10);
    }
}
