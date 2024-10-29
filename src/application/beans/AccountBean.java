package application.beans;

public class AccountBean {
	private String accountName;
	private String openingDate;
	private double balance;
	
	// Constructor to initialize an AccountBean
	public AccountBean(String accName, String openDate, double bal) {
		accountName = accName;
		openingDate = openDate;
		balance = bal;
	}
	
	// Setters
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	// Getters
	public String getAccountName() {
		return accountName;
	}
	
	public String getOpeningDate() {
		return openingDate;
	}
	
	
	public double getBalance() {
		return balance;
	}
	
	
}
