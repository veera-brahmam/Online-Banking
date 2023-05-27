package OnlineBanking;

import java.util.*;
//import BankUser.BankUser;
//import DbCongiguration.DbConfigure;
import java.sql.*;
public class BankEmployee {
    static Scanner obj=new Scanner(System.in);
    private static int id; 
    private static String name;
    private static String mail;
    private static String psw;
    static String pending="pending";
	static int ch;
 
    public static void employee(){
    	 BankUser O=new BankUser();
    	try {
			System.out.println("ENTER UR ID");
	    	id=obj.nextInt();
	    	System.out.println("ENTER UR NAME");
	    	name=obj.next();
	    	System.out.println("ENTER UR MAIL");
	    	mail=obj.next();
	    	System.out.println("ENTER UR PSW");
	    	psw=obj.next();
	    	ResultSet rs1=DbConfigure.dbConnection().executeQuery("select *from employee");
			rs1.next();
	    	if(id==rs1.getInt("id")&&0==name.compareTo(rs1.getString("name"))&&0==mail.compareTo(rs1.getString("mail"))&&0==psw.compareTo(rs1.getString("psw")))
	    	{
	    		System.out.println("UR LOGIN SUCCESSFULLY");
	        while(true) {
	        System.out.println("ENTER 1.APPLICATIONS 2.CUSTOMERS DETAILES 3.CUSTOMER TRANSACTIONS 4.EXIT");
	        ch=obj.nextInt();
	        Statement stm=DbConfigure.dbConnection();
	        switch(ch) {
	        case 1:
		    try {
		    	 ResultSet sr=stm.executeQuery("select *from applications");
		    	 System.out.println("applicationid  firstname lastname  aadhar city  phone       mail      psw    status balance");
		    	  while(sr.next()) {
		    		  System.out.println(sr.getInt("applicationid")+"      |   "+sr.getString("firstname")+"|"+sr.getString("lastname")+"|"+sr.getInt("aadhar")+"|"+sr.getString("city")+"|"+sr.getInt("phone")+"|"+sr.getString("mail")+"|"+sr.getString("psw")+"|"+sr.getString("status")+"|"+sr.getInt("balance")); 
		    	  }
		    	  System.out.println("<-------*-----------------*------------------*----------------*-----------------------*--------->");
			     ResultSet rs=stm.executeQuery("select *from applications where status='"+pending+"'");
			 while(rs.next()) {
			     System.out.println(rs.getInt("applicationid")+"      |   "+rs.getString("firstname")+"|"+rs.getString("lastname")+"|"+rs.getInt("aadhar")+"|"+rs.getString("city")+"|"+rs.getInt("phone")+"|"+rs.getString("mail")+"|"+rs.getString("psw")+"|"+rs.getString("status")+"|"+rs.getInt("balance"));
				 System.out.println("ENTER 1 FOR ACCEPT OTHER FOR REJECT");
				 int n=obj.nextInt();
				  if(n==1) {
					O.accreation();
					DbConfigure.dbConnection().executeUpdate("update applications set status='accepted' where applicationid="+rs.getInt("applicationid")+"");
				   }
				else {
					DbConfigure.dbConnection().executeUpdate("update applications set status='rejected' where applicationid="+rs.getInt("applicationid")+"");
				    }
			        }
		            } catch (SQLException e1) {
			        e1.printStackTrace();
		            }
		      break;
	          case 2:
		            try {
			        ResultSet rs=stm.executeQuery("select * from customers");
			        System.out.println("ACCNO  FIRSTNAME LASTNAME  AADHAR  CITY  PHONE BALANCE        EMAIL         PSW");
			        while(rs.next()) {
				    System.out.println(rs.getInt("accno")+"|"+rs.getString("firstname")+"|"+rs.getString("lastname")+"|"+rs.getInt("aadhar")+"|"+rs.getString("city")+"|"+rs.getInt("phone")+"|"+rs.getInt("balance")+"|"+rs.getString("mail")+"|"+rs.getString("psw"));
			        }
		            } catch (SQLException e) {
			        e.printStackTrace();
		            }
		       break;
	          case 3:
		        try {
			        ResultSet rs=stm.executeQuery("select *from transactions");
			        System.out.println("TRSID   ACCNO  TYPETRS   TRSACCNO  TRSAMOUNT   DATE   TIME");
			        while(rs.next()) {
				    System.out.println(rs.getInt("transactionid")+"  | "+rs.getInt("accno")+"  |  "+rs.getString("type")+"  |    "+rs.getString("transaction")+"  | "+rs.getInt("amount")+" | "+rs.getDate("date")+"|"+rs.getTime("time")+"|"+rs.getString("status")+"");	
			        }
		            } catch (SQLException e) {
			        e.printStackTrace();}
		       break;
	          case 4:
		             System.exit(0);
	         default:
	    	        System.out.println("ENTER VALID KEY");
	             }
	             }
	           	}
	          else {
   		           System.out.println("UR DETAILES NOT CORRECT");
   	               }
	               } catch (SQLException e){
		            e.printStackTrace();
	               }
}
}