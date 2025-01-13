import java.sql.*;

//PreparedStatement -> SQL과 결합이 되어버림 다른 SQL과 재사용 불가능
class C {
   String maria = "org.mariadb.jdbc.Driver";
   String mariaUrl = "jdbc:mariadb://127.0.0.1:3307/java_schema";
   Connection con;
   PreparedStatement pstmt1, pstmt2, pstmt3;
   String tname = "JDBCT";
   String sql1 = "insert into "+tname+" values(?,?,now())";
   String sql2 = "select * from "+tname+" order by NO desc";
   String sql3 = "select * from "+tname+" where NAME like ?";

   C(){
      try{
         Class.forName(maria);
         con = DriverManager.getConnection(mariaUrl, "scott", "tiger");
         pstmt1 = con.prepareStatement(sql1);
         pstmt2 = con.prepareStatement(sql2);
         pstmt3 = con.prepareStatement(sql3);
      }catch(ClassNotFoundException ce){
         pln("드라이버로딩 실패: " + ce);
      }catch(SQLException se){
         pln("연결 실패: " + se);
      }
   }
   void insertD(int no, String name){
      try{
         pstmt1.setInt(1, no);
         pstmt1.setString(2, name);
         int i = pstmt1.executeUpdate();
         if(i>0){
            pln(i+"개의 row 입력 성공");
         }else{
            pln("입력 실패");
         }
      }catch(SQLException se){
         pln("입력 실패: " + se);
      }
   }
   void selectD(){
      ResultSet rs = null;
      try{
         int i = 0;
         rs = pstmt2.executeQuery();
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
   void selectD(String na){
      ResultSet rs = null;
      try{
         int i = 0;
         pstmt3.setString(1, "%"+na+"%");
         rs = pstmt3.executeQuery();
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
         pstmt1.close();
         pstmt2.close();
         pstmt3.close();
         con.close();
      }catch(SQLException se){}
   }
   void pln(String str){
      System.out.println(str);
   }
   void p(String str){
      System.out.print(str);
   }
   public static void main(String[] args) {
      C c = new C();

      //c.insertD(50, "엄태정");
      //c.insertD(60, "신혜서");
      //c.insertD(70, "김혜서");

      //c.selectD();
      c.selectD("혜");
      c.closeAll();
   }
}