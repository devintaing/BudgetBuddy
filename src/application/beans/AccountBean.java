package application.beans;

public class AccountBean {
	private String accountName;
	private String openingDate;
	private double balance;
	
	public AccountBean(String accName, String openDate, double bal) {
		accountName = accName;
		openingDate = openDate;
		balance = bal;
	}
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getOpeningDate() {
		return openingDate;
	}
	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
}
