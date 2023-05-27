package OnlineBanking;

import java.util.*;
//import DbCongiguration.DbConfigure;
import java.sql.*;
public class BankUser{
	private int applicationid;
	private String firstname;
	private String lastname;
	private int aadhar;
	private String city;
	private int phone;
	private String mail;
	private String psw;
	private String status="pending";
	private int accno;
	private int balance;
    static int transferid;
	int ch;
	static Scanner obj=new Scanner(System.in);
	public static  void transfermation() {
		transferid=(int)(Math.random()*(10000-100+1)+100);
		
	}
	public void accreation() {
		try {
			accno=(int)(Math.random()*(1000000-10000+1)+10000);
			ResultSet rs=DbConfigure.dbConnection().executeQuery("select *from applications where status='"+status+"'");
			rs.next();
			DbConfigure.dbConnection().executeUpdate("insert into customers values("+accno+",'"+rs.getString("firstname")+"','"+rs.getString("lastname")+"','"+rs.getInt("aadhar")+"','"+rs.getString("city")+"','"+rs.getInt("phone")+"',"+rs.getInt("balance")+",'"+rs.getString("mail")+"','"+rs.getString("psw")+"')");
		    DbConfigure.dbConnection().executeUpdate("insert into map values("+rs.getInt("applicationid")+","+accno+")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void accstatus() {
		 try {
			System.out.println("ENTER UR APPLICATIONID");
			applicationid=obj.nextInt();
			ResultSet rs1= DbConfigure.dbConnection().executeQuery("select *from applications where applicationid="+applicationid+"");
			boolean r=rs1.next();
			if(r==false) {
				System.out.println("U ARE NOT APPLIED FOR ACCOUNT");
			}
			else if(rs1.getString("status").compareTo("accepted")==0) {
			 ResultSet s=DbConfigure.dbConnection().executeQuery("select accno from map where applicationid="+applicationid+"");
			 s.next();
			 System.out.println("CONGRATULATIONS UR ACCOUNT CREATED SUCCESSFULLY\nPLEASE NOTE ACCNO"+s.getInt("accno"));
			}
			else {
				System.out.println(rs1.getInt("applicationid")+"|"+rs1.getString("firstname")+"|"+rs1.getString("lastname")+"|"+rs1.getInt("aadhar")+"|"+rs1.getString("city")+"|"+rs1.getInt("phone")+"|"+rs1.getString("mail")+"|"+rs1.getString("psw")+"|"+rs1.getString("status"));
				if(rs1.getString("status").compareTo("pending")==0) {
				System.out.println("UR APPLICATION STILL IN PROGRESS");
				}
				else {
					System.out.println("SORRY APPLICATION IS REJECTED UR MONEY REFOUND SHORTLY");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int existingUser(){
	    System.out.println("ENTER UR BANK ACCOUNT NUMBER");
		accno=obj.nextInt();
		System.out.println("ENTER UR FIRSTNAME");
	    firstname=obj.next();
	    System.out.println("ENTER UR LASTNAME");
	    lastname=obj.next();
	    System.out.println("ENTER UR GMAIL");
		mail=obj.next();
		System.out.println("ENTER UR PASSWORD");
		psw=obj.next();
		try {
			 ResultSet rs=DbConfigure.dbConnection().executeQuery("select *from customers where accno="+accno+"");
			 if(rs.next()==false) {
				 System.out.println("UR NOT HAVING ACCOUNT AT SBH BANK");
				 accno=0;
			 }
			 else if(accno==rs.getInt("accno")&&0==firstname.compareTo(rs.getString("firstname"))&&0==mail.compareTo(rs.getString("mail"))&&0==psw.compareTo(rs.getString("psw"))){
			  System.out.println("<----------UR LOGING SUCCESSFULLY------------>");
			  }
		      else {
		       System.out.println("U DONT HAVE ACCOUNT AT SBH");
		         accno=0;
		      }
			  }  
		   catch(SQLException e){
			    e.printStackTrace();
			   }
		return accno;
		}
	   public void newUser() {
			System.out.println("ENTER FIRST NAME");
		    firstname=obj.next();
		    System.out.println("ENTER LAST NAME");
		    lastname=obj.next();
			System.out.println("ENTER UR AADHAR");
			aadhar=obj.nextInt();
			System.out.println("ENTER UR CITY");
			city=obj.next();
			System.out.println("ENTER UR PH.NO");
			phone=obj.nextInt();
			System.out.println("ENTER UR MAIL");
			mail=obj.next();
			System.out.println("ENTER UR PASSWORD");
			psw=obj.next();
			System.out.println("CONFIRM UR PASSWORD");
			String psw1=obj.next();
			if(psw.compareTo(psw1)==0){
			System.out.println("ENTER MONEY FOR STARTING BALANCE MIN Rs.1000/-");
			balance=obj.nextInt();
			if(balance>=1000){
				applicationid=(int)(Math.random()*(1000000-50000+1)+50000);
				try {
					DbConfigure.dbConnection().executeUpdate("insert into applications values("+applicationid+",'"+firstname+"','"+lastname+"',"+aadhar+",'"+city+"',"+phone+",'"+mail+"','"+psw+"','"+status+"',"+balance+")");	
					System.out.println("#UR APLLICATION IS SUBMITTED TO EMAPLOYEE#");
					System.out.println("NOTE UR APPLICATIONID "+applicationid);
				     } catch (SQLException e) {
					     e.printStackTrace();
				      }
			 }
			 else {
				      System.out.println("MIN Rs.1000/-REQUIRED FOR OENING");
			       } 
			                    } 
			  else {
				   System.out.println("PASSWORD IS MISSMATCHING");
			        }
			}
}
