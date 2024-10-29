package application.DAOs;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import application.CommonObjs;
import application.beans.AccountBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TransactionTypesDAO {
	// Get an instance of CommonOnjs and retrieve a shared database connection
	private static CommonObjs commonObjs = CommonObjs.getInstance();
	private static Connection connection = commonObjs.getConnection();
	
	// Method to create the Accounts table if it doesn't exist
	public static void createAccountTable() {
		try {
		// SQLite query to create Accounts table with specified columns
		String createTablesql = "CREATE TABLE if not exists Accounts(AccountName TEXT, OpeningDate TEXT, AccountBalance REAL)";
		Statement stmt = connection.createStatement();
		stmt.execute(createTablesql);
		
		}
		catch (SQLException e) {
			System.out.println("Error creating Account Table");
			e.printStackTrace();
		}
	}
	
	// Method to add a new account to the Accounts table
	public static void addAccount(String accountName, String openingDate, Double accountBalance ){
		try {
			//SQL query to insert a new account with placeholder for values
			String insertSQL = "INSERT INTO Accounts(AccountName, OpeningDate, AccountBalance) VALUES(?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(insertSQL);
			pstmt.setString(1, accountName); // Set Account name
			pstmt.setString(2, openingDate); // Set opening date
			pstmt.setDouble(3, accountBalance); // Set account balance
			pstmt.executeUpdate();
			
		}
		catch (SQLException e) {
			System.out.println("Error inserting account " + accountName);
			e.printStackTrace();
		}
		
	}
	
	// Method that retrieves all accounts and returns them as an ObservableList
	public static ObservableList<AccountBean> getAccounts(){
		ObservableList<AccountBean> list = FXCollections.observableArrayList();
		try {
			// Path to the SQLite query file that retrieves accounts ordered by date
			Path filePath = Paths.get("resources","database", "queries", "AccountDateOrder_Query.sql");
			String sql = Files.readString(filePath).trim(); // Read and process the SQL query
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql); // Execute the query
			
			// Loops through the result set and add each account to the ObservableList
			while (result.next()) {
				String accountName = result.getString("AccountName");
				String openingDate = result.getString("OpeningDate");
				double balance = result.getDouble("AccountBalance");
				
				AccountBean bean = new AccountBean(accountName, openingDate, balance);
				list.add(bean);
			}
			
			
		} catch (Exception e) {
			System.out.println("Error getting Accounts");
			e.printStackTrace();
		}
		
		return list; // Returns the list of accounts
	}
	
	public static HashSet<String> getAccountNames(){
		HashSet<String> set = new HashSet<>();
		
		try {
			// Path to the SQL query file that retrieves account names
			Path filePath = Paths.get("resources","database", "queries", "AccountNames_Query.sql");
			String sql = Files.readString(filePath).trim();
			Statement statement = connection.createStatement(); // Read and process the SQL query
			
			ResultSet result = statement.executeQuery(sql);
			
			// Loops through the result set and add each account name to the Hashset
			while (result.next()) {
				String accountName = result.getString("AccountName");
				set.add(accountName);	
			}
			
		}
		catch (Exception e) {
			System.out.println("Error getting Account Names");
			e.printStackTrace();
		}
		
		return set; // Return the set of unique account names
	}
}
