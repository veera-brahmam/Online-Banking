package OnlineBanking;

import java.util.*;
import java.sql.*;

public class DbConfigure {
	static Connection con=null;
	static Statement st;
	public static Statement dbConnection() {
	Scanner obj=new Scanner(System.in);
	String url="jdbc:mysql://localhost:3306/onlinebanking";
	String user="root";
	String psw="veera";
	try {
	Class.forName("com.mysql.jdbc.Driver");
	con=DriverManager.getConnection(url,user,psw);
	st=con.createStatement();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	catch(ClassNotFoundException e) {
		e.printStackTrace();
	}
	return st;
	}
}