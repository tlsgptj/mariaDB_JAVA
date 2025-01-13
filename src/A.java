import java.sql.*;

//CRUD(Create Read Update Delete)
class B {
    String maria = "org.mariadb.jdbc.Driver";
    String mariaUrl = "jdbc:mariadb://127.0.0.1:3306/java_schema";
    Connection con;
    Statement stmt;
    B(){
        try{
            Class.forName(maria);
            con = DriverManager.getConnection(mariaUrl, "scott", "tiger");
            stmt = con.createStatement();
        }catch(ClassNotFoundException ce){
            pln("드라이버로딩 실패: " + ce);
        }catch(SQLException se){
            pln("연결 실패: " + se);
        }

        //createT();
        //dropT();

        //insertD(10, "홍길동");
        //insertD(20, "이순신");
        //insertD(30, "강감찬");
        //insertD(40, "유관순");

        //updateD(30, "낙성대");

        //deleteD(20);

        selectD();

        closeAll();
    }

    String tname = "JDBCT";
    void createT(){
        String sql = "create table if not exists "+tname
                +"(NO int primary key, NAME varchar(10), RDATE datetime)";
        try{
            stmt.execute(sql);
            pln(tname+" 테이블 생성 성공");
        }catch(SQLException se){
            pln(tname+" 테이블 생성 실패: " + se);
        }
    }
    void dropT(){
        String sql = "drop table if exists "+tname;
        try{
            stmt.execute(sql);
            pln(tname+" 테이블 삭제 성공");
        }catch(SQLException se){
            pln(tname+" 테이블 삭제 실패: " + se);
        }
    }
    void insertD(int no, String name){
        String sql = "insert into "+tname+" values("+no+",'"+name+"', now())";
        try{
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
        String sql = "update "+tname+" set NAME='"+name+"' where NO="+no;
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
    void selectD(){
        String sql = "select * from "+tname+" order by NO desc";
        ResultSet rs = null;
        try{
            int i = 0;
            rs = stmt.executeQuery(sql);
            pln("번호\t이름\t날짜");
            pln("------------------------------------");
            while(rs.next()){
                int no = rs.getInt(1);
                String name = rs.getString(2);
                Date rdate = rs.getDate(3);
                Time rtime = rs.getTime(3);
                pln(no+"\t"+name+"\t"+rdate+" "+ rtime);
                i++;
            }
            pln("------------------------------------");
            pln("총 "+i+"개의 row가 검색됨");
        }catch(SQLException se){
            pln("selectD()예외: " + se);
        }finally{
            try{
                rs.close();
            }catch(SQLException se){}
        }
    }
    void closeAll(){
        try{
            stmt.close();
            con.close();
            pln("연결 객체들 닫기 성공");
        }catch(SQLException se){}
    }
    void pln(String str){
        System.out.println(str);
    }
    public static void main(String[] args) {
        new B();
    }
}












