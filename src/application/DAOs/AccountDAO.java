package application.DAOs;

import java.net.URL;
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

public class AccountDAO {
	private static CommonObjs commonObjs = CommonObjs.getInstance();
	private static Connection connection = commonObjs.getConnection();
	
	public static void createAccountTable() {
		try {
		String createTablesql = "CREATE TABLE if not exists Accounts(AccountName TEXT, OpeningDate TEXT, AccountBalance REAL)";
		Statement stmt = connection.createStatement();
		stmt.execute(createTablesql);
		
		}
		catch (SQLException e) {
			System.out.println("Error creating Account Table");
			e.printStackTrace();
		}
	}
	public static void addAccount(String accountName, String openingDate, Double accountBalance ){
		try {
			String insertSQL = "INSERT INTO Accounts(AccountName, OpeningDate, AccountBalance) VALUES(?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(insertSQL);
			pstmt.setString(1, accountName);
			pstmt.setString(2, openingDate);
			pstmt.setDouble(3, accountBalance);
			pstmt.executeUpdate();
			
		}
		catch (SQLException e) {
			System.out.println("Error inserting account " + accountName);
			e.printStackTrace();
		}
		
	}
	
	public static ObservableList<AccountBean> getAccounts(){
		ObservableList<AccountBean> list = FXCollections.observableArrayList();
		try {
			Path filePath = Paths.get("resources","database", "queries", "AccountDateOrder_Query.sql");
			String sql = Files.readString(filePath).replace("\n", "");
			
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
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
		
		return list;
	}
	
	public static HashSet<String> getAccountNames(){
		HashSet<String> set = new HashSet<>();
		
		try {
			Path filePath = Paths.get("resources","database", "queries", "AccountNames_Query.sql");
			String sql = Files.readString(filePath).replace("\n", "");
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			while (result.next()) {
				String accountName = result.getString("AccountName");
				set.add(accountName);	
			}
			
		}
		catch (Exception e) {
			System.out.println("Error getting Account Names");
			e.printStackTrace();
		}
		
		return set;
	}
}
