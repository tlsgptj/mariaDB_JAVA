import java.sql.*;

//동적커서(Dynamic Cursor)
class E {
    String maria = "org.mariadb.jdbc.Driver";
    String mariaUrl = "jdbc:mariadb://127.0.0.1:3306/java_schema";
    Connection con;
    Statement stmt;
    String sql = "select * from JDBCT order by NO desc";
    E(){
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
            forward(rs); // BOF -> EOF
            backward(rs); // EOF -> BOF
            rs.afterLast(); // EOF
            backward(rs); // EOF -> BOF

            forward(rs); // BOF
            rs.beforeFirst(); //BOF
            forward(rs); // BOF -> EOF
        }catch(SQLException se){

        }
    }
    void forward(ResultSet rs){
        pln("< 순방향 >");
        try{
            while(rs.next()){
                int no = rs.getInt(1);
                String name = rs.getString(2);
                Date rdate = rs.getDate(3);
                Time rtime = rs.getTime(3);
                pln(no+"\t"+name+"\t"+rdate+" "+ rtime);
            }
        }catch(SQLException se){}
    }
    void backward(ResultSet rs){
        pln("< 역방향 >");
        //로직
    }
    void pln(String str){
        System.out.println(str);
    }
    void p(String str){
        System.out.print(str);
    }
    public static void main(String[] args){
        new E();
    }
}