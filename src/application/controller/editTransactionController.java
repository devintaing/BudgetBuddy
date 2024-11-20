package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import application.CommonObjs;
import application.beans.ScheduledTransactionBean;
import application.beans.TransactionBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class editTransactionController {
	@FXML
	TextField accountName;

	@FXML
	TextField transactionType;

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
    
    private TransactionBean currentTransaction;

    // Set the transaction details and populate the fields
    public void setTransaction(TransactionBean transaction) {
        this.currentTransaction = transaction;

        // Populate the fields with the transaction details
        accountName.setText(transaction.getAccountName());
        transactionType.setText(transaction.getTransactionType());
        transactionDate.setValue(LocalDate.parse(transaction.getTransactionDate()));
        transactionDescription.setText(transaction.getTransactionDescription());
        paymentAmount.setText(String.valueOf(transaction.getPaymentAmount()));
        depositAmount.setText(String.valueOf(transaction.getDepositAmount()));

    }
    
  	private void loadScene(String fxmlFile){
      	URL url = getClass().getResource(fxmlFile);
      	
      	try {
  			AnchorPane paneHome = (AnchorPane)FXMLLoader.load(url);
  			
  			if (mainBox.getChildren().size() >1)
  				mainBox.getChildren().remove(1);
  			
  			mainBox.getChildren().add(paneHome);
  			
  		} catch (IOException e) {
  			System.out.println("error loading scene from " + fxmlFile);
  			e.printStackTrace();
  		}
      }
  	
  	
    public void switchToViewTransactions() {
        loadScene("/view/viewTransactions.fxml"); // Use the loadScene method
    }

}
