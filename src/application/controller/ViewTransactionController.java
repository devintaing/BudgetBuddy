package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import application.CommonObjs;
import application.DAOs.AccountDAO;
import application.DAOs.TransactionTypeDAO;
import application.beans.TransactionBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class ViewTransactionController {
	
	@FXML
	ChoiceBox<String> accountName;

	@FXML
	ChoiceBox<String> transactionType;

	@FXML
	DatePicker transactionDate;

	@FXML
	TextField transactionDescription;

	@FXML
	TextField paymentAmount;

	@FXML
	TextField depositAmount;
    
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
    
    
    
    // Set the transaction details and populate the fields
    public void setTransactionDetails(TransactionBean transaction) {
        
        //get all account names, set default value of dropdown to to current value
    	accountName.setValue(transaction.getAccountName());
    	
    	//get all transaction types, set default value of dropdown to to current value
    	transactionType.setValue(transaction.getTransactionType());
        
    	// Populate the fields with the transaction details
        transactionDate.setValue(LocalDate.parse(transaction.getTransactionDate()));
        transactionDescription.setText(transaction.getTransactionDescription());
        paymentAmount.setText(String.valueOf(transaction.getPaymentAmount()));
        depositAmount.setText(String.valueOf(transaction.getDepositAmount()));

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
