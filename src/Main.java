import java.sql.*;

class Main {
    String dbms = "Oracle"; //드라이버명
    //String oracle = "oracle.jdbc.driver.OracleDriver";
    Connection con;
    //String oracleUrl = "jdbc:oracle:thin:@127.0.0.1:1521:JAVA";
    String maria = "org.mariadb.jdbc.Driver";
    String mariaUrl = "jdbc:mariadb://127.0.0.1:3306/java_schema";

    Main(){
        //(1) 드라이버명을 이용해 메모리 로딩
        //A.class -> new A();
        try{
            Class.forName(maria); //객체를 만들지 않으면서 클래스 로딩시킴
            pln("드라이버로딩 성공");
            //con = DriverManager.getConnection(oracleUrl, "scott", "tiger");
            con = DriverManager.getConnection(mariaUrl, "scott", "tiger");
        }catch(ClassNotFoundException ce){
            pln("드라이버로딩 실패: " + ce);
        }catch(SQLException se){
            pln("(2)" + dbms + "연결실패" + se);
        }
    }
    void pln(String str){
        System.out.println(str);
    }
    public static void main(String[] args){
        new Main();
    }
}