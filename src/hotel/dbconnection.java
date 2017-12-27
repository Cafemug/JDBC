package hotel;



import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import hotel.gui2;


public class dbconnection {
	static Connection DBconnection;
	private PreparedStatement stmt;
	private ResultSet rs;
	private PreparedStatement stmt1;
	private ResultSet rs1;
	private PreparedStatement stmt2;
	private ResultSet rs2;
	private PreparedStatement stmt3;
	private ResultSet rs3;
	private PreparedStatement stmt4;
	private ResultSet rs4;
	private PreparedStatement stmt5;
	private ResultSet rs5;
	public void connectDB(String id, String password) throws SQLException, ClassNotFoundException{				
		Class.forName("oracle.jdbc.OracleDriver");
		DBconnection = DriverManager.getConnection("jdbc:oracle:thin:" + "@localhost:1521:XE", id, password);
		createTable();
		//JOptionPane.showMessageDialog(null, "호텔 관리 시스템에 접속되었습니다.");	
     }
	
	//dbtable을 만드는 함수
	public void createTable() throws SQLException{	
		
			String customerQuery = "CREATE TABLE customer (\n"
		+ "customername	    varchar(10) not null,\n"
		+ "customergender		    varchar(10)  not null, \n"
		+ "customeraddress  	varchar(10)  not null, \n"
		+ "customerphone       varchar(10) not null, \n"
		+ "primary key(customername) \n"
				+ ")";	
	
	String managerQuery = "CREATE TABLE manager (\n"
		+ "managername	    varchar(10) not null,\n"
		+ "managergender		    varchar(10)  not null, \n"
		+ "manageraddress  	varchar(10)  not null, \n"
		+ "managerphone       varchar(10) not null, \n"
		+ "primary key(managername) \n"
				+ ")";		
			
	String roomQuery = "CREATE TABLE room (\n"
		+ "roomno	    Integer not null,\n"
		+ "ableno	    Integer not null,\n"
		+ "roomtype	    varchar(30) not null,\n"
		+ "status	Integer  not null, \n"
		+ "primary key(roomno) \n"
				+ ")";	
	
	String reservationQuery = "CREATE TABLE reservation (\n"
		+ "no	    Integer not null,\n"
		+ "roomno	    Integer not null,\n"
		+ "cname	varchar(10) not null,\n"
		+ "mname	    varchar(30) not null,\n"
		+ "startdate         varchar(15)	    not null, \n"
		+ "enddate         varchar(15)	    not null, \n"
		+ "day	Integer  not null \n"
		
				+ ")";	
	
	String sql = "";	
	//for문으로 table 생성을 위해 table이름, query 배열을 생성
	try{
		 String[] tablename = {"customer","manager","room","reservation"};
	 String[] query = {customerQuery, managerQuery, roomQuery,reservationQuery};
	 for(int i = 0; i<tablename.length; i++){		
		 //db에 이미 table이 생성이 되어있는지를 check, 없다면 생성, 있다면 생성하지 않음
			 if(dbtableexist(tablename[i]) == false){
				 //System.out.println(tablename[i]);
				 sql = query[i];
				 stmt = DBconnection.prepareStatement(sql);
		    	 rs=stmt.executeQuery();	         
		         DBconnection.commit();
				}			
		 }         
	}
	catch(SQLException e){	    		
			JOptionPane.showMessageDialog(null,"테이블생성에 문제가 발생했습니다 : " +  e);	 
		    	DBconnection.rollback();	    	
	    }
		finally{
	    	 stmt.close();
		     rs.close();
	    }
	}
	
	//createTable 함수 실행시 DB에 table의 존재 유무를 알려주는 함수	
	public boolean dbtableexist(String tablename) throws SQLException{
	    String sql = "Select TABLE_NAME from user_tables where table_name='"+tablename.toUpperCase()+"' ";  
	 boolean existTable = false;
	 stmt = DBconnection.prepareStatement(sql);
	 rs=stmt.executeQuery();
	 
	while (rs.next()){
	    if(rs.getString("table_name").equals(tablename.toUpperCase())){
		existTable = true;	// db에 이미 테이블 존재시 true : table 생성할 필요가 없음                            
	            }               
	        }
	        stmt.close();
	     	rs.close(); 
	        return existTable;
	}
	
	//filetext를 통해 고객정보를 db에 저장해주는 함수
	public void regicustomer(String customername, String customergender, String customeraddress, String customerphone) throws SQLException{
		try{
			
	if(dbperson("customer",customername) == true){
	JOptionPane.showMessageDialog(null, customername + " : 이미 등록되어 있습니다.");
	}
	//직원, 또는 회원이 db에 없으면 >저장
	else if(dbperson("customer",customername) == false){
	String sql = "INSERT INTO customer " + " VALUES ('" + customername + "','" + customergender + "','" + customeraddress + "','" + customerphone + "')";
	//System.out.println(sql);
	stmt = DBconnection.prepareStatement(sql);
	stmt.executeUpdate();
	//JOptionPane.showMessageDialog(null,"customer : " + customername  + " 가 DB에 등록되었습니다.");
		DBconnection.commit();
		stmt.close(); 
	}
	
	
	}
	catch(SQLException e1){
		e1.getStackTrace();
		JOptionPane.showMessageDialog(null, "DB저장에 문제가 생겼습니다. 다시 시도해 주세요" + e1);
			stmt.close(); 
		}		 
	}
	
	//filetext를 통해 직원정보를 db에 저장해주는 함수
	public void regimanager(String managername, String managergender, String manageraddress, String managerphone) throws SQLException{
		try{
			
			//직원, 또는 회원이 이미 db에 존재하면 알려줌
	if(dbperson("manager",managername) == true){
	JOptionPane.showMessageDialog(null, managername + " : 이미 등록되어 있습니다.");
	}
	//직원, 또는 회원이 이미 db에 존재하지 않으면 db에 저장
	else if(dbperson("manager",managername) == false){
	String sql = "INSERT INTO manager " + " VALUES ('" + managername + "','" + managergender +"','" + manageraddress +"','" + managerphone+"')"; 
	stmt = DBconnection.prepareStatement(sql);
	stmt.executeUpdate();
	//System.out.println("customer : " + managername  + " 가 DB에 등록되었습니다.");
		DBconnection.commit();
		stmt.close();
	}
	
	
	}
	catch(SQLException e1){
		e1.getStackTrace();
		JOptionPane.showMessageDialog(null, "DB저장에 문제가 생겼습니다. 다시 시도해 주세요" + e1);
		}		 
	}
	
	//filetext를 통해 메뉴정보를 db에 저장해주는 함수
	public void regiroom(int roomno,int ableno,String roomtype) throws SQLException {
		try{
			if(dbroom(roomno) == true){
				JOptionPane.showMessageDialog(null, roomno + " : 이미 등록된 방번호입니다.");
	}
	else if(dbroom(roomno) == false){
		String sql = "INSERT INTO room VALUES (" + roomno+"," + ableno + ",'" + roomtype +"',"+0+ ")"; 
	System.out.println(sql);
	stmt = DBconnection.prepareStatement(sql);
	stmt.executeUpdate();
	//System.out.println("room : " + roomno + " 가 DB에 등록되었습니다.");   
		DBconnection.commit();
		stmt.close();
	}
	System.out.println("-----------------------------");
	}catch(SQLException e1){
		e1.getStackTrace();
		JOptionPane.showMessageDialog(null, "객실 등록에 실패했습니다.");
		}
	}
	
	//db에저장된 사람인지 체크하는 함수
	boolean dbperson(String tablename, String name) throws SQLException{
		boolean personis = false;
		String sql = "SELECT "+tablename+ "name from " + tablename + " where " +tablename+"name = '" +name+"'";
	//System.out.println("checkpersonexist : " + sql);
	
	 stmt = DBconnection.prepareStatement(sql);
	 rs=stmt.executeQuery();
	 
	 while (rs.next()){
		 if(rs.getString(tablename+"name").equals(name)){
	    		 personis  = true;	                            
	         }           
	     }
	     stmt.close();
		 rs.close(); 
	     return personis;
	}
	
	//db에저장된 room인지 체크하는 함수
	boolean dbroom(int roomno) throws SQLException{
		 boolean check = false;
		 String room = "select roomno from room where roomno = '"+ roomno+"'";
	 
	 stmt = DBconnection.prepareStatement(room);
	 rs = stmt.executeQuery();
	 
	 while(rs.next()){
		 if(rs.getInt("roomno")==(roomno)){
				 check = true;		
			 }		 		
		 }
		 stmt.close();
	     rs.close(); 
		 return check;
	}
	
	//객실의 정보를 db에서 가져옴
	//객실정보에 있는 textarea에 보여주게 됨
	public void getroom(String roomno) throws SQLException {
		int roomnum=Integer.parseInt(roomno);
		 String query = "select * from room where roomno = " + roomnum +"";
		 String status="",no="",able="",type="",cname="",ccount="",mname="",mcount="";
	 //System.out.println(query);
	 stmt = DBconnection.prepareStatement(query);
	 rs = stmt.executeQuery();
	 if(rs.next()){
		 no=String.valueOf(rs.getInt("roomno"));
		 able=String.valueOf(rs.getInt("ableno"));	
		 type=rs.getString("roomtype");	
		 int s=rs.getInt("status");
		 if(s==0) {
			 status="비어있음";
		 }else if(s==1) {
			 status="투숙 중";
		 }
	 }
	 else{
		 JOptionPane.showMessageDialog(null,"없는 방번호입니다.");
		 } 
	 
	String query1 = "select max(cname),count(cname) from reservation group by " + roomnum +"";
	stmt1 = DBconnection.prepareStatement(query1);
	rs1 = stmt1.executeQuery();
	 if(rs1.next()){
		 cname=rs1.getString("max(cname)");
		 ccount=String.valueOf(rs1.getInt("count(cname)"));	
		 
	 }
	
	String query2 = "select max(mname),count(mname) from reservation group by " + roomnum +"";
	stmt2 = DBconnection.prepareStatement(query2);
	rs2 = stmt2.executeQuery();
	 if(rs2.next()){
		 mname=rs2.getString("max(mname)");
		 mcount=String.valueOf(rs2.getInt("count(mname)"));	
		 
	 }
	 if(cname=="" || ccount=="" || mname=="" || mcount=="") {
		 gui2.textarea_room.setText("방번호 : " + no + "\n"
				 + "수용인원 : " + able + "\n타입 : " +type + "\n상태 : " + status 
				 +"\n투숙고객(최다) :"+"없음\n"+
				 "객실전담직원(최다): "+"없음");
		 
	 }
	 else {
	 gui2.textarea_room.setText("방번호 : " + no + "\n"
	 + "수용인원 : " + able + "\n타입 : " +type + "\n상태 : " + status 
	 +"\n투숙고객(최다) :"+cname+"("+ccount+"회)\n"+
	 "객실전담직원(최다): "+mname+"("+mcount+"회)");
	}}

	public void getroomno() throws SQLException {
		 String query = "select roomno from room";
	 String no;
	 //System.out.println(query);
	 stmt = DBconnection.prepareStatement(query);
	 rs = stmt.executeQuery();
	 while(rs.next()){
		 no= String.valueOf(rs.getInt("roomno"));
		 gui2.roomno.add(no);
	 }
	 
		
	}
	public void currentroomno() throws SQLException {
		 String query = "select roomno from room where status="+0+"";
	 String no;
	 //System.out.println(query);
	 stmt = DBconnection.prepareStatement(query);
	 rs = stmt.executeQuery();
	 while(rs.next()){
		 no= String.valueOf(rs.getInt("roomno"));
		 gui2.roomnum.add(no);
	 }
	 
		
	}
	
	//직원의 정보를 db에서 가져와 주는 함수
	//직원정보에 있는 textarea에 보여주게 됩니다.
	public void getmanagerInformation(String managername) throws SQLException {
		String query = "select * from manager where managername = '" + managername + "'";
		String name="",gender="",address="",phone="",cname="",ccname="",room="",croom="";
	 //System.out.println(query);
	 stmt = DBconnection.prepareStatement(query);
	 rs = stmt.executeQuery();
	 if(rs.next()){
		 name= rs.getString("managername");
		 gender= rs.getString("managergender");
		 address= rs.getString("manageraddress");
		 phone= rs.getString("managerphone");
	 
	 }
	 else{
		 JOptionPane.showMessageDialog(null,"없는 직원입니다.");
		 }
	 String query1 = "select max(cname),count(cname),max(roomno),count(roomno) from reservation where mname = '" + managername + "'"+"group by mname";
	 
	 //System.out.println(query);
	 stmt1 = DBconnection.prepareStatement(query1);
	 rs1 = stmt1.executeQuery();
	 if(rs1.next()){
		 cname= rs1.getString("max(cname)");;
		 ccname= String.valueOf(rs1.getInt("count(cname)"));
		 room= String.valueOf(rs1.getInt("max(roomno)"));
		 croom= String.valueOf(rs1.getInt("count(roomno)"));
		 
	 
		}
	 if(cname=="" || ccname=="" || room=="" || croom=="") {
		 gui2.Stafftextarea.setText("직원명 : " + name + "\n"
				 + "성별 : " + gender + "\n주소 : " +address + "\n연락처 : " + phone 
				 +"\n접대 고객(최다) : 없음\n"+
				 "관리객실(최다) : 없음");
		 
	 }
	 else {
	 gui2.Stafftextarea.setText("직원명 : " + name + "\n"
			 + "성별 : " + gender + "\n주소 : " +address + "\n연락처 : " + phone 
			 +"\n접대 고객(최다) :"+cname+"("+ccname+"회)\n"+
			 "관리객실(최다) : "+room+"("+croom+"회)");
	}
	}
	
	
	//회원의 정보를 db에서 가져와 주는 함수
	//회원정보에 있는 textarea에 보여주게 됩니다.
	public void getcustomerInformation(String customername) throws SQLException {
		String query = "select * from customer where customername = '" + customername + "'";
		String name="",gender="",address="",phone="",day="",cmname="",mname="",date="";
	// System.out.println(query);
	 stmt = DBconnection.prepareStatement(query);
	 rs = stmt.executeQuery();
	 if(rs.next()){
		 name= rs.getString("customername");
		 gender= rs.getString("customergender");
		 address= rs.getString("customeraddress");
		 phone= rs.getString("customerphone");
	 
		}
	 
	 else{
		 JOptionPane.showMessageDialog(null,"없는직원입니다.");
		 }
	 String query1 = "select sum(day),count(mname),max(mname) from reservation where cname = '" + customername + "' "+"group by cname";
	 
	 //System.out.println(query1);
	 stmt1 = DBconnection.prepareStatement(query1);
	 rs1 = stmt1.executeQuery();
	 
	 if(rs1.next()){
		 day= String.valueOf(rs1.getInt("sum(day)"));
		 cmname= String.valueOf(rs1.getInt("count(mname)"));
		 mname= rs1.getString("max(mname)");
		 
	 
		}
	 String query2 = "select startdate from reservation where cname = '" + customername + "' "+"order by no";
	 
	 //System.out.println(query2);
	 stmt2 = DBconnection.prepareStatement(query2);
	 rs2 = stmt2.executeQuery();
	 if(rs2.next()){
		 
		 date= rs2.getString("startdate");
		 
	 
		}
	 
	 if(mname=="" || cmname=="") {
		 gui2.TextArea.setText("고객명 : " + name + "\n"
				 + "성별 : " + gender + "\n주소 : " +address + "\n연락처 : " + phone 
				 +"\n총 투숙 기간 : 없음\n"+
				 "최근 투숙 기간: 없음\n"+
				 "객실전담직원(최다): 없음");
		 
	 }
	 else {
	 gui2.TextArea.setText("고객명 : " + name + "\n"
			 + "성별 : " + gender + "\n주소 : " +address + "\n연락처 : " + phone 
			 +"\n총 투숙 기간 :"+day+"\n"+
			 "최근 투숙 기간:"+date+"\n"+
			 "객실전담직원(최다): "+mname+"("+cmname+"회)");
	}
	}
	//예약 table 예약현황(색) 업데이트
	public void updatetable() throws SQLException{
		Calendar cDateCal = Calendar.getInstance();
		Calendar cstart=Calendar.getInstance();
		Calendar cend=Calendar.getInstance();
		Calendar current=Calendar.getInstance();
		for(int i=1; i<=20; i++){
		gui2.table[i].setBackground(Color.WHITE);
		}
		
		
	    cDateCal.add(Calendar.DATE, 0);
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    String result = format.format(cDateCal.getTime());
	    Date Dcuurent=format.parse(result, new ParsePosition(0));
	    current.setTime(Dcuurent);
	    int roomno;
	    int number;
		String sql3 = "Select startdate,enddate,roomno from reservation";  
		stmt5 = DBconnection.prepareStatement(sql3);
		rs5=stmt5.executeQuery();
		while (rs5.next()){
			    String start=rs5.getString("startdate");
			    String end=rs5.getString("enddate");
			    roomno=rs5.getInt("roomno");
			    Date Dstart=format.parse(start, new ParsePosition(0));
				Date Dend=format.parse(end, new ParsePosition(0));
				cstart.setTime(Dstart);
				cend.setTime(Dend);
				if((cstart.compareTo(current)==-1 || cstart.compareTo(current)==0)&& (cend.compareTo(current)==1 || cend.compareTo(current)==0)) {
					if (roomno>200) {
			       		number=roomno-200+10;
			       	}
			       	else {
			       		number=roomno-100;
			       	}
					gui2.table[number].setBackground(Color.YELLOW);
				}     
			        
			}
		
		
	}
	// 객실 예약 수정 함수
	public void modifyres(String cname,String startdate) throws SQLException{
		String date="";
		String start=startdate.substring(0, 4)+"-"+startdate.substring(4, 6)+"-"+startdate.substring(6);
		String query2="select startdate from reservation where cname='"+cname+"' and startdate='"+start+"'";
		stmt2 = DBconnection.prepareStatement(query2);
		rs2 = stmt2.executeQuery();
		if(rs2.next()){
			 date= rs2.getString("startdate");
			 
		 }
		if(date.equals("")) {
			JOptionPane.showMessageDialog(null, ""+start+"의 예약 정보가 없습니다");
		}
		else {
			
			String query3 ="delete from reservation where cname='"+cname+"' and startdate='"+start+"'";
			stmt3 = DBconnection.prepareStatement(query3);
			stmt3.executeUpdate();
			JOptionPane.showMessageDialog(null, ""+start+"의 예약이 취소 되었습니다.");
			updatetable();
			
		}
		
	}
	//객실 예약 함수
	public void reservation(String cname,String startdate,int day,int roomno) throws SQLException{
		if(dbperson("customer",cname) == false){
			JOptionPane.showMessageDialog(null, "등록 된 고객이 아닙니다.");
			}
		else {
		
		int oriroomno=0;
	
		String query="select managername from manager order by dbms_random.random";
		String mname=null,oristart=null,oriend=null;
		String start=startdate.substring(0, 4)+"-"+startdate.substring(4, 6)+"-"+startdate.substring(6);
		
		int no=0;
		System.out.println(start);
		Calendar test=Calendar.getInstance();
		Calendar coristart=Calendar.getInstance();
		Calendar coriend=Calendar.getInstance();
		Calendar cstart=Calendar.getInstance();
		Calendar cend=Calendar.getInstance();
		//Calendar test5=Calendar.getInstance();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date ex=format.parse(start, new ParsePosition(0));
		test.setTime(ex);
		test.add(Calendar.DATE,day);
		System.out.println(format.format(test.getTime()).toString());
		String end=format.format(test.getTime()).toString();
		stmt = DBconnection.prepareStatement(query);
		rs = stmt.executeQuery();
		if(rs.next()){
			 mname = rs.getString("managername");
		 }
		String query1="select no from reservation order by no desc";
		stmt1 = DBconnection.prepareStatement(query1);
		rs1 = stmt1.executeQuery();
		if(rs1.next()){
			 no= rs1.getInt("no");
			 
		 }
		
		String query2="select startdate,enddate,roomno from reservation where cname='"+cname+"' order by no";
		stmt2 = DBconnection.prepareStatement(query2);
		rs2 = stmt2.executeQuery();
		if(rs2.next()){
			 oristart= rs2.getString("startdate");
			 oriend=rs2.getString("enddate");
			 oriroomno=rs2.getInt("roomno");
		 }
		if(oristart==null || oriend==null) {
			no+=1;
			String query3 = "INSERT INTO reservation VALUES ("+no +","+ roomno+",'" + cname + "','" + mname +"','" + start +"','"+end+"',"+day+ ")";
			 
			 System.out.println(query3);
			 stmt3 = DBconnection.prepareStatement(query3);
			 stmt3.executeUpdate();
			 updatetable();
			 JOptionPane.showMessageDialog(null, "예약이 등록되었습니다.");  
		}
				
		else {
		Date Doristart=format.parse(oristart, new ParsePosition(0));
		Date Doriend=format.parse(oriend, new ParsePosition(0));
		Date Dstart=format.parse(start, new ParsePosition(0));
		Date Dend=format.parse(end, new ParsePosition(0));
		coristart.setTime(Doristart);
		coriend.setTime(Doriend);
		cstart.setTime(Dstart);
		cend.setTime(Dend);
		if(((coristart.compareTo(cstart)==-1 || coristart.compareTo(cstart)==0) && (coriend.compareTo(cstart)==1 || coriend.compareTo(cstart)==0)) || ((coristart.compareTo(cend)==-1 || coristart.compareTo(cend)==0) && (coriend.compareTo(cend)==1 || coriend.compareTo(cend)==0)))
		{
			String query3 ="delete from reservation where no="+no+"";
			stmt3 = DBconnection.prepareStatement(query3);
			stmt3.executeUpdate();
			String query4 = "INSERT INTO reservation VALUES ("+no +","+ roomno+",'" + cname + "','" + mname +"','" + start +"','"+end+"',"+day+ ")";
			 
			 System.out.println(query4);
			 stmt4 = DBconnection.prepareStatement(query4);
			 stmt4.executeUpdate();
			 updatetable();
			
			 JOptionPane.showMessageDialog(null, "예약이 수정되었습니다.");
		}
		else if((cstart.compareTo(coristart)==-1 || cstart.compareTo(coristart)==0 )&& (cend.compareTo(coriend)==1 || cend.compareTo(coriend)==0)){
			String query3 ="delete from reservation where no="+no+"";
			stmt3 = DBconnection.prepareStatement(query3);
			stmt3.executeUpdate();
			String query4 = "INSERT INTO reservation VALUES ("+no +","+ roomno+",'" + cname + "','" + mname +"','" + start +"','"+end+"',"+day+ ")";
			 
			 System.out.println(query4);
			 stmt4 = DBconnection.prepareStatement(query4);
			 stmt4.executeUpdate();
			 updatetable();
			 
			 JOptionPane.showMessageDialog(null, "예약이 수정되었습니다.");
		}
		else {
			no+=1;
		String query3 = "INSERT INTO reservation VALUES ("+no +","+ roomno+",'" + cname + "','" + mname +"','" + start +"','"+end+"',"+day+ ")";
		 
		 System.out.println(query3);
		 stmt3 = DBconnection.prepareStatement(query3);
		 stmt3.executeUpdate();
		 updatetable();
		 JOptionPane.showMessageDialog(null, "예약이 등록되었습니다.");  
		
		}
		}
		DBconnection.commit();
				

		
		}
		}
	
}
	



