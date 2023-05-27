package OnlineBanking;

import java.util.*;
import java.util.logging.LogManager;
import java.sql.*;

public class Banking{
	static Scanner obj=new Scanner(System.in);
	static int amount;
	static int balance;
    static int accno;
    static int sac;
    static int ch;
	public static void checkBalance(int accno) {
		balance=Operations.search(accno);
		System.out.println("UR AVIALABLE BALANCE Rs"+balance);
	}
	public static void withdrawl(int accno) {
		System.out.println("ENTER THE AMOUT TO WITHDRAWL");
		amount=obj.nextInt();
		balance=Operations.search(accno);
		if(amount<=balance) {
			balance=balance-amount;
			    Operations.update(accno,balance);
			    sac=0;
			    Operations.transfermation(sac, accno,amount);
				System.out.println("UR WITHDRAW SUCESSFULL Rs"+amount+"/-");
				System.out.println("NOW AVAILABLE BALANCE Rs"+balance+"/-");
		}
		else {
			System.out.println("U NOT HAVE SUFFICENT BALANCE");
		}
	}
	public static void deposit(int accno) {
		System.out.println("ENTER THE AMOUNT TO DEPOSIT");
		amount=obj.nextInt();
		balance=balance+amount;
		Operations.update(accno,balance);
		sac=1;
		Operations.transfermation(sac, accno,amount);
		System.out.println("<------UR DEPOSIT IS SUCCESSFULL----->");
	}
	public static void transfer(int accno) {
		sac=accno;
		System.out.println("ENTER THE AMOUNT TO TRANSFER");
		amount=obj.nextInt();
		balance=Operations.search(accno);
		if(balance>=amount) {
		System.out.println("ENTER THE ACCOUNT NO TO TRANSFER");
		accno=obj.nextInt();
		int a=Operations.searchaccno(accno);
		if(a==1) {
		int transactionid=Operations.transfermation(sac,accno,amount);
		System.out.println("<-------NOTE UR TRANSACTIONID======"+transactionid);
		}
		else {
			System.out.println("THIS ACCNO IS NOT VALID");
		}
	    }
		else {
			System.out.println("U NOT SUFFICENT BALANCE");
		}
	}
	public static void main(String[] arguments) {
	    //Logger lg=LogManager.getLogger(Banking.class); 
		//lg.trace("");lg.info("");lg.debug("");lg.warn("");lg.error("");lg.fatal("");
	    BankUser O=new BankUser();
	    	while(true) {
	    	System.out.println("*******WELCOME TO SBH BANK********");
	    	System.out.println("1.EXISTING ACCOUNT 2.APPLY NEW ACCOUNT 3.ACCOUNT STATUS 4.EMPLOYEE LOGIN 5.EXIT");
	        ch=obj.nextInt();
	        switch(ch){
	        case 1:
	        	accno=O.existingUser();
	        	if(accno!=0)
	        	while(true) {
	        	System.out.println("ENTER SERVICE\n1.CHECK BALANCE\n2.DEPOSIT\n3.WITHDRWAL\n4.TRANSFERMONEY\n5.MONEYREQUEST\n6.EXIT");
	        	ch=obj.nextInt();
	        	switch(ch) {
	        	case 1:
	        		checkBalance(accno);
	        		break;
	        	case 2:
	        		deposit(accno);
	        		break;
	        	case 3:
	        		withdrawl(accno);
	        		break;
	        	case 4:
	        	    transfer(accno);
	        		break;
	        	case 5:
	        		 Operations.moneytransaction(accno);
	        		 break;
	        	case 6:
	        		System.exit(1);
	        		break;
	        	default:
	        		System.out.println("OOop's ENTER A VALID KEY");
	        	   }
	        	   }
	        	 break;
	        case 2:
	        	  O.newUser();
	        	  break;
	        case 3:
	        	  O.accstatus();
	        	  break;
	        case 4:
	        	   BankEmployee.employee();
	        	   break;
	        case 5:
	        	System.exit(0);
	        	break;
	        default:
	    		System.out.println("OOOP's YOU ENTER WRONG OPTION");
	  	         }
	            }
		}
}