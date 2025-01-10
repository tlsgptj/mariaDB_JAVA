import java.sql.*;

//CRUD(Create Read Update Delete)
class B {
    String maria = "org.mariadb.jdbc.Driver";
    String mariaurl = "jdbc:mariadb://127.0.0.1:3307/java_schema";
    Connection con;
    Statement stmt;

    B(){
        try{
            Class.forName(maria);
            con = DriverManager.getConnection(mariaurl, "scott", "tiger");
            stmt = con.createStatement();
        }catch(ClassNotFoundException ce){
            pln("(1) 드라이버로딩 실패: " + ce);
        }catch(SQLException se){
            pln("(2) 연결실패: " + se);
        }

        //createT();
        //dropT();
        //insertD(10, "홍길동");
        //insertD(20, "이순신");
        //insertD(30, "강감찬");
        //insertD(40, "유관순");
        //updateD(30, "낙성대");
        //deleteD(20);

        //selectD(); //READ
        closeAll();
    }

    String tname = "JDBCT";
    void createT(){
        String sql = "create table if not exists " + tname
                + "(NO int primary key, NAME varchar(10), RDATE datetime)";
        try{
            stmt.execute(sql);
            pln(tname + " table created success!");
        }catch(SQLException se){
            pln(tname + " table created failed.");
        }
    }

    void dropT(){
        String sql = "drop table if exists " + tname;
        try{
            stmt.execute(sql);
            pln(tname + " table deleted success!");
        }catch(SQLException se){
            pln(tname + " table deleted fail.");
        }
    }

    //sql과 변수이름을 동일하게
    //executeUpdate : insert, update, delete
    void insertD(int no, String name){
        String sql = "insert into "+tname+" values("+no+", '"+name+"', now())";
        try{ //update라서 return값이 있음
            int i = stmt.executeUpdate(sql);
            if(i>0){
                pln(i+"개의 row 입력 성공");
            }else{
                pln("입력 실패");
            }
        }catch(SQLException se){
            pln("입력 실패: " + se);
        }
    }

    void updateD(int no, String name){
        String sql = "update "+tname+" set NAME='"+name+"', RDATE=now() where NO="+no;
        try{
            int i = stmt.executeUpdate(sql);
            if(i>0){
                pln(i+"개의 row 수정 성공");
            }else{
                pln("수정 실패");
            }
        }catch(SQLException se){
            pln("수정 실패: " + se);
        }
    }

    void deleteD(int no){
        String sql = "delete from "+tname+" where NO="+no;
        try{
            int i = stmt.executeUpdate(sql);
            if(i>0){
                pln(i+"개의 row 삭제 성공");
            }else{
                pln("삭제 실패");
            }
        }catch(SQLException se){
            pln("삭제 실패: " + se);
        }
    }

    //DQL : executeQuery
    void selectD(){
        String sql = "select * from "+tname+" ";
        ResultSet rs = null;
        try{
            int i = 0;
            rs = stmt.executeQuery(sql);
            pln("번호\t이름\t날짜");
            //rs.next가 끝날 때까지
            while(rs.next()){
                int no = rs.getInt(1);
                String name = rs.getString(2);
                Date rdate = rs.getDate(3);
                Time rtime = rs.getTime(3);
                pln(no+"\t"+name+"\t"+rdate+" "+rtime);
                i++;
            }
            pln("총 "+i+"개의 row가 검색됨");
        }catch(SQLException se){
            pln("selectD()예외: "+se);
        }finally{
            try{
                rs.close();
            }catch(SQLException se){}
        }
    }

    void closeAll() {
        try {
            stmt.close();
            con.close();
            pln("연결 객체를 닫기 성공");
        } catch (SQLException se) {

        }
    }

    void pln(String str){
        System.out.println(str);
    }
    public static void main(String[] args){
        new B();
    }
}