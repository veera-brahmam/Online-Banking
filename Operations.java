package OnlineBanking;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

//import DbCongiguration.DbConfigure;
public class Operations {
	static int balance;
	static int a;
	static int accno;
	static String str1 = "TO";
	static String str2 = "FROM";
	static String str3 = "peding";
	static int transactionid;
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	static SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
	static Date date = new Date();
	static Scanner obj = new Scanner(System.in);

	public static void moneytransaction(int accno) {
		System.out.println("TRSID   ACCNO  TYPETRS   TRSACCNO  TRSAMOUNT   DATE   TIME STATUS");
		try {
			ResultSet rs1 = DbConfigure.dbConnection()
					.executeQuery("select *from transactions where accno=" + accno + " and type='TO' or type='FROM'");
			while (rs1.next()) {
				System.out.println(
						rs1.getInt("transactionid") + "  | " + rs1.getInt("accno") + "  |  " + rs1.getString("type")
								+ "  |    " + rs1.getString("transaction") + "  | " + rs1.getInt("amount") + "|"
								+ rs1.getDate("date") + "|" + rs1.getTime("time") + "|" + rs1.getString("status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(
				"<-------*-----------------*------------------*----------------*-----------------------*--------->");
		try {
			ResultSet rs = DbConfigure.dbConnection().executeQuery(
					"select *from transactions where accno=" + accno + " and status='peding' and type='FROM'");
			while (rs.next()) {
				System.out.println(rs.getInt("transactionid") + "  | " + rs.getInt("accno") + "  |  "
						+ rs.getString("type") + "  |    " + rs.getString("transaction") + "  | " + rs.getInt("amount")
						+ "|" + rs.getDate("date") + "|" + rs.getTime("time") + "|" + rs.getString("status"));
				System.out.println("ENTER 1 FOR ACCEPT OTHER FOR REJECT");
				a = obj.nextInt();
				if (a == 1) {
					request(rs.getInt("transactionid"));
					DbConfigure.dbConnection()
							.executeUpdate("update transactions set status='accepted' where transactionid="
									+ rs.getInt("transactionid") + "");
				} else {
					DbConfigure.dbConnection()
							.executeUpdate("update transactions set status='rejected' where transactionid="
									+ rs.getInt("transactionid") + "");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void request(int transactionid) {
		try {
			ResultSet ms = DbConfigure.dbConnection()
					.executeQuery("select *from transactions where transactionid=" + transactionid + "");
			while (ms.next()) {
				accno = ms.getInt("accno");
				balance = search(accno);
				int amount = ms.getInt("amount");
				System.out.println(accno + " " + balance + " " + amount);
				if (ms.getString("type").compareTo("TO") == 0) {
					balance = balance - amount;
					System.out.println(balance);
					update(accno, balance);
				} else if (ms.getString("type").compareTo("FROM") == 0) {
					balance = balance + amount;
					System.out.println(balance);
					update(accno, balance);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int searchaccno(int accno) {
		try {
			ResultSet rs = DbConfigure.dbConnection().executeQuery("select *from customers where accno=" + accno + "");
			if (rs.next() == true) {
				a = 1;
			} else {
				a = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}

	public static void update(int accno, int balance) {
		try {
			DbConfigure.dbConnection()
					.executeUpdate("update customers set balance=" + balance + " where accno=" + accno + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int transfermation(int sac, int accno, int amount) {
		try {
			transactionid = (int) (Math.random() * (100000 - 1000 + 1) + 1000);
			if (sac == 1 || sac == 0) {
				str3 = "sucsess";
				if (sac == 0) {
					str1 = "withdraw";
				} else if (sac == 1) {
					str1 = "deposite";
				}
				DbConfigure.dbConnection().executeUpdate(
						"insert into transactions (transactionid,accno,type,amount,date,time,status) values("
								+ transactionid + "," + accno + ",'" + str1 + "'," + amount + ",'" + sdf.format(date)
								+ "','" + sdf1.format(date) + "','" + str3 + "')");
			} else {
				DbConfigure.dbConnection()
						.executeUpdate("insert into transactions values(" + transactionid + "," + sac + ",'" + str1
								+ "','" + Integer.toString(accno) + "'," + amount + ",'" + sdf.format(date) + "','"
								+ sdf1.format(date) + "','" + str3 + "')");
				DbConfigure.dbConnection()
						.executeUpdate("insert into transactions values(" + transactionid + "," + accno + ",'" + str2
								+ "','" + Integer.toString(sac) + "'," + amount + ",'" + sdf.format(date) + "','"
								+ sdf1.format(date) + "','" + str3 + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactionid;
	}

	public static int search(int accno) {
		try {
			ResultSet rs = DbConfigure.dbConnection().executeQuery("select *from customers where accno=" + accno + "");
			rs.next();
			balance = rs.getInt("balance");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}
}