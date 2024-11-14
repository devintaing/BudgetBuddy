package application.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import application.CommonObjs;

public class TransactionTypeDAO {
	// Get an instance of CommonOnjs and retrieve a shared database connection
	private static CommonObjs commonObjs = CommonObjs.getInstance();
	private static Connection connection = commonObjs.getConnection();
	
	// Method to create the Accounts table if it doesn't exist
	public static void createTransactionTypeTable() {
		try {
		// SQLite query to create Accounts table with specified columns
		String createTablesql = "CREATE TABLE if not exists TransactionTypes(TypeName TEXT)";
		Statement stmt = connection.createStatement();
		stmt.execute(createTablesql);
		
		}
		catch (SQLException e) {
			System.out.println("Error creating Transaction Type Table");
			e.printStackTrace();
		}
	}
	
	// Method to add a new account to the Accounts table
	public static void addTransactionType(String typeName){
		try {
			//SQL query to insert a new account with placeholder for values
			String insertSQL = "INSERT INTO TransactionTypes(TypeName) VALUES(?)";
			PreparedStatement pstmt = connection.prepareStatement(insertSQL);
			pstmt.setString(1, typeName); // Set transaction type name
			pstmt.executeUpdate();
			
		}
		catch (SQLException e) {
			System.out.println("Error inserting account " + typeName);
			e.printStackTrace();
		}
		
	}
	
	
	public static HashSet<String> getTransactionTypesSet(){
		HashSet<String> set = new HashSet<>();
		
		try {
			String sql = "SELECT TypeName "
					+ "FROM TransactionTypes;";
			Statement statement = connection.createStatement(); // Read and process the SQL query
			
			ResultSet result = statement.executeQuery(sql);
			
			// Loops through the result set and add each account name to the Hashset
			while (result.next()) {
				String typeName = result.getString("TypeName");
				set.add(typeName.toLowerCase());	
			}
			
		}
		catch (Exception e) {
			System.out.println("Error getting Transaction Types");
			e.printStackTrace();
		}
		
		return set; // Return the set of transaction types
	}
	
	public static ArrayList<String> getTransactionTypesList(){
		ArrayList<String> list = new ArrayList<>();
		
		try {
			//gets names in alphabetical order
			String sql = "SELECT TypeName "
					+ "FROM TransactionTypes "
					+ "ORDER BY TypeName COLLATE NOCASE;";
			Statement statement = connection.createStatement(); // Read and process the SQL query
			
			ResultSet result = statement.executeQuery(sql);
			
			// Loops through the result set and add each account name to the arraylist
			while (result.next()) {
				String typeName = result.getString("TypeName");
				list.add(typeName);	
			}
			
		}
		catch (Exception e) {
			System.out.println("Error getting Transaction Types");
			e.printStackTrace();
		}
		
		return list; // Return the set of transaction types
	}
}
