package application.beans;

public class ScheduledTransactionBean {
	private int id;
	private String scheduleName;
	private String accountName;
	private String transactionType;
	private String transactionFreq;
	private int dueDate;
	private double paymentAmount;

	public ScheduledTransactionBean(int idNum, String schedName, String accName, String transType, String transFreq,
			int transDate, double payAmt) {
		id = idNum;
		scheduleName = schedName;
		accountName = accName;
		transactionType = transType;
		transactionFreq = transFreq;
		dueDate = transDate;
		paymentAmount = payAmt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionFreq() {
		return transactionFreq;
	}

	public void setTransactionFreq(String transactionFreq) {
		this.transactionFreq = transactionFreq;
	}

	public int getDueDate() {
		return dueDate;
	}

	public void setDueDate(int transactionDate) {
		this.dueDate = transactionDate;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	
	
	
}
