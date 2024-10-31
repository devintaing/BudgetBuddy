package application.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import application.CommonObjs;

public class TransactionDAO {
	// Get an instance of CommonOnjs and retrieve a shared database connection
	private static CommonObjs commonObjs = CommonObjs.getInstance();
	private static Connection connection = commonObjs.getConnection();
	
	// Method to create the Transactions table if it doesn't exist
	public static void createTransactionTable() {
		try {
		// SQLite query to create Accounts table with specified columns
		String createTablesql = "CREATE TABLE if not exists "
								+"Transactions(AccountName TEXT, TransactionType TEXT, Date TEXT, "
											+"Description TEXT,PaymentAmount REAL, DepositAmount REAL)";
		Statement stmt = connection.createStatement();
		stmt.execute(createTablesql);
		
		}
		catch (SQLException e) {
			System.out.println("Error creating Transactions Table");
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
	
	
}
