package application.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import application.CommonObjs;

public class ScheduledTransactionDAO {
	// Get an instance of CommonOnjs and retrieve a shared database connection
	private static CommonObjs commonObjs = CommonObjs.getInstance();
	private static Connection connection = commonObjs.getConnection();
	
	// Method to create the Transactions table if it doesn't exist
	public static void createScheduledTransactionTable() {
		try {
		// SQLite query to create Accounts table with specified columns
		String createTablesql = "CREATE TABLE if not exists "
								+"ScheduledTransactions(ScheduleName TEXT,AccountName TEXT, TransactionType TEXT,  "
											+"Frequency TEXT, Date TEXT, PaymentAmount REAL)";
		Statement stmt = connection.createStatement();
		stmt.execute(createTablesql);
		
		}
		catch (SQLException e) {
			System.out.println("Error creating Scheduled Transactions Table");
			e.printStackTrace();
		}
	}
	
	// Method to add a new account to the Accounts table
	public static void addTransaction(String accountName, String transactionType, String date, String description, Double payment, Double deposit){
		try {
			//SQL query to insert a new account with placeholder for values
			String insertSQL = "INSERT INTO Transactions(AccountName, TransactionType, Date, Description, PaymentAmount, DepositAmount) VALUES(?,?,?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(insertSQL);
			pstmt.setString(1, accountName); // Set Account name
			pstmt.setString(2, transactionType); // Set transaction type
			pstmt.setString(3, date); // Set transaction date
			pstmt.setString(4, description); // Set transaction description
			pstmt.setDouble(5, payment); // Set payment amount
			pstmt.setDouble(6, deposit); // Set deposit amount
			pstmt.executeUpdate();
			
		}
		catch (SQLException e) {
	        System.out.printf("Error adding a \"%s\" transaction for the account \"%s\" on %s with a description of \"%s\", a payment amount of %.2f, and a deposit amount of %.2f.%n", transactionType, accountName, date, description, payment, deposit);
			e.printStackTrace();
		}
		
	}

	public static void addScheduledTransaction(String scheduleNameStr, String account, String type, String frequencyStr, String date, double payment) {
		// TODO Auto-generated method stub
		try {
			//SQL query to insert a new account with placeholder for values
			String insertSQL = "INSERT INTO ScheduledTransactions(ScheduleName, AccountName, TransactionType, Frequency, Date, PaymentAmount) VALUES(?,?,?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(insertSQL);
			pstmt.setString(1, scheduleNameStr); // Set transaction name
			pstmt.setString(2, account); // Set Account name
			pstmt.setString(3, type); // Set transaction type
			pstmt.setString(4, frequencyStr); // Set transaction frequency
			pstmt.setString(5, date); // Set due date
			pstmt.setDouble(6, payment); // Set payment amount
			pstmt.executeUpdate();
			
		}
		catch (SQLException e) {
	        System.out.printf("Saved a \"%s\" scheduled transaction for the account \"%s\" with due date %s with a name of \"%s\", a payment amount of %.2f.%n", type, account, date, scheduleNameStr, payment);
			e.printStackTrace();
		}
	}
	
	
}
