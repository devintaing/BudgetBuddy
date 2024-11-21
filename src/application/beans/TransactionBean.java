package application.beans;

public class TransactionBean {
	private String accountName;
	private String transactionType;
	private String transactionDate;
	private String transactionDescription;
	private double paymentAmount;
	private double depositAmount;
	
	public TransactionBean (String accName, String transType, String transDate, String transDesc, double paymentAmt, double depositAmt) {
		accountName = accName;
		transactionType = transType;
		transactionDate = transDate;
		transactionDescription = transDesc;
		paymentAmount = paymentAmt;
		depositAmount = depositAmt;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public double getDepositAmount() {
		return depositAmount;
	}
	
	
}
