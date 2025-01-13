import java.sql.*;

class G {
    String maria = "org.mariadb.jdbc.Driver";
    //JDBC 드라이버 클래스 이름
    String mariaUrl = "jdbc:mariadb://127.0.0.1:3307/java_schema";
    // 데이터베이스 연결 URL 127.0.0.1에서 java_schema 데이터베이스 연결
    Connection con;
    // 데이터베이스 연결을 위한 Connection 객체
    PreparedStatement pstmt1, pstmt2;
    // SQL 쿼리를 실행하기 위한 PreparedStatement 객체
    String sql1 = "update JDBCT set NAME=? where NO=?";
    // update 쿼리, NO컬럼의 값에 따라 NAME 값을 수정
    String sql2 = "nsert into JDBCT values(?,?,now())";
    // insert 쿼리, JDBCT 테이블에 값을 삽입

    G(){
        try{
            Class.forName(maria);
            // JDBC 드라이버를 로드
            con = DriverManager.getConnection(mariaUrl, "scott", "tiger");
            con.setAutoCommit(false); //중요!
            // 트랜잭션을 수동 커밋 모드로 설정
            pstmt1 = con.prepareStatement(sql1);
            // sql1과 sql2를 기반으로 준비된 SQL문 생성
            pstmt2 = con.prepareStatement(sql2);
            // update 작업을 수행
            up();
        }catch(ClassNotFoundException ce){
            pln("드라이버로딩 실패: " + ce);
        }catch(SQLException se){
            pln("연결 실패: " + se);
        }
    }

    Savepoint sp1;
    void up(){
        try{
            pstmt1.setString(1, "라멘");
            // SQL쿼리의 ?에 값을 바인딩
            pstmt1.setInt(2, 30);
            int i = pstmt1.executeUpdate();
            // update 쿼리를 실행, 영향을 받은 행 수를 반환
            if(i>0){
                pln("update 성공");
                sp1 = con.setSavepoint("sp1name");
                // update 작업이 성공하면 Savepoint를 설정하여 트랜잭션 롤백 지점을 저장
                in();
                // update 성공 시 insert 작업 실행
            }else{
                pln("update 실패");
            }
        }catch(SQLException se){
            pln("update 실패 se: " + se);
        }
    }

    void in(){
        try{
            pstmt2.setInt(1, 80);
            pstmt2.setString(2, "신선도");
            // pstmt2.setString: insert 쿼리의 ?에 값을 바인딩
            int i = pstmt2.executeUpdate();
            // insert 쿼리를 실행
            if(i>0){
                pln("insert 성공");
                con.commit();
                // insert 성공 시 트랜잭션을 커밋
            }else{
                pln("insert 실패");
                con.rollback(sp1);
                // insert 실패 시 Savepoint로 롤백
            }
        }catch(SQLException se){
            pln("insert 실패 se: " + se);
            try{
                con.rollback(sp1);
            }catch(SQLException se2){ }
        }
    }

    void pln(String str){
        System.out.println(str);
        //문자열을 출력
    }

    void p(String str){
        System.out.print(str);
        //줄바꿈 없이 문자열 출력
    }

    public static void main(String[] args) {
        new G(); //생성자를 호출하여 실행
    }
}