package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import application.beans.TransactionBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class accountDetailController {
	
    @FXML
    private Label type;
    @FXML
    private Label date;
    @FXML
    private Label description;
    @FXML
    private Label payment;
    @FXML
    private Label deposit;
    
	private CommonObjs commonObjs = CommonObjs.getInstance();
    private HBox mainBox = commonObjs.getMainBox();
    
    private String prevAccountName;
    private String prevTransactionType;
    boolean accountReport;
  	
    public void switchToViewReport() {
      	try {
      		
      		//Decide which report to return to
      		String reportURL;
      		accountReport = false;
      		if(prevAccountName != null) {
      			reportURL = "/view/viewAccountReport.fxml";
      			accountReport = true;
      		}
      		else {
      			reportURL = "/view/viewTransactionTypeReport.fxml";
      		}
  	        // Load the FXML for the report view
  	        FXMLLoader loader = new FXMLLoader(getClass().getResource(reportURL));
  	        AnchorPane pane = loader.load();

  	        // Get the controller for the edit view
  	        // Pass the previous Account/Transaction to the controller
  	        if (accountReport) {
  	        	AccountReportController controller = loader.getController();
  	        	controller.setAccount(prevAccountName);
  	        }
  	        else {
  	        	TransactionTypeReportController controller = loader.getController();
  	        	controller.setTransactionType(prevTransactionType);
  	        }

  	        // Replace the current view with the report view
  	        if (mainBox.getChildren().size() > 1) {
  	            mainBox.getChildren().remove(1);
  	        }
  	        mainBox.getChildren().add(pane);

  	    } catch (IOException e) {
  	    	String reportType;
  	    	if (accountReport) {
  	    		reportType = "Account";
  	    	}
  	    	else {
  	    		reportType = "Transaction Type";
  	    	}
  	    	System.out.println("Error returning to "+ reportType +" report results.");
  	        e.printStackTrace();
  	    }
    }
    
    
    
    // Method to populate the labels with transaction details
    public void setTransactionDetails(TransactionBean transaction) {
    	
    	type.setText(transaction.getTransactionType());
        date.setText(transaction.getTransactionDate());
        description.setText(transaction.getTransactionDescription());

        // Format payment and deposit amounts
        payment.setText(String.format("$%.2f", transaction.getPaymentAmount()));
        deposit.setText(String.format("$%.2f", transaction.getDepositAmount()));
    }

	public void setPrevAccountName(String AccountName) {
		//save state
    	this.prevAccountName = AccountName;
	}
	public void setPrevTransactionType(String TransactionType) {
		//save state
    	this.prevTransactionType = TransactionType;
	}

}
