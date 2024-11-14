package application.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.CommonObjs;
import application.beans.ScheduledTransactionBean;
import application.beans.TransactionBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
											+"Frequency TEXT, Date INTEGER, PaymentAmount REAL)";
		Statement stmt = connection.createStatement();
		stmt.execute(createTablesql);
		
		}
		catch (SQLException e) {
			System.out.println("Error creating Scheduled Transactions Table");
			e.printStackTrace();
		}
	}

	public static void addScheduledTransaction(String scheduleNameStr, String account, String type, String frequencyStr, int date, double payment) {
		// TODO Auto-generated method stub
		try {
			//SQL query to insert a new account with placeholder for values
			String insertSQL = "INSERT INTO ScheduledTransactions(ScheduleName, AccountName, TransactionType, Frequency, Date, PaymentAmount) VALUES(?,?,?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(insertSQL);
			pstmt.setString(1, scheduleNameStr); // Set transaction name
			pstmt.setString(2, account); // Set Account name
			pstmt.setString(3, type); // Set transaction type
			pstmt.setString(4, frequencyStr); // Set transaction frequency
			pstmt.setInt(5, date); // Set due date
			pstmt.setDouble(6, payment); // Set payment amount
			pstmt.executeUpdate();
			
		}
		catch (SQLException e) {
	        System.out.printf("Saved a \"%s\" scheduled transaction for the account \"%s\" with due date %s with a name of \"%s\", a payment amount of %.2f.%n", type, account, date, scheduleNameStr, payment);
			e.printStackTrace();
		}
	}
	
	public static ObservableList<ScheduledTransactionBean> getScheduledTransactions(){
		ObservableList<ScheduledTransactionBean> list = FXCollections.observableArrayList();
		try {
			String sql = "SELECT * "+ 
						"FROM ScheduledTransactions " +
						"ORDER BY Date ASC";
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql); // Execute the query
			
			// Loops through the result set and add each account to the ObservableList
			while (result.next()) {
				String scheduleName = result.getString("ScheduleName");
				String accountName = result.getString("AccountName");
				String transType = result.getString("TransactionType");
				String transFreq = result.getString("Frequency");
				int dueDate = result.getInt("Date");
				double payAmt = result.getDouble("PaymentAmount");
				
				ScheduledTransactionBean bean = new ScheduledTransactionBean(scheduleName, accountName, transType, transFreq, dueDate, payAmt);
				list.add(bean);
			}
			
			
		} catch (Exception e) {
			System.out.println("Error getting Accounts");
			e.printStackTrace();
		}
		
		return list; // Returns the list of scheduled transactions
	}
	
	
}
