package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import application.beans.ScheduledTransactionBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class EditScheduledTransactionController {
	@FXML
	TextField scheduleName;
	
	@FXML
	TextField accountName;

	@FXML
	TextField transactionType;
	
	@FXML
	TextField frequency;

	@FXML
	TextField dueDate;

	@FXML
	TextField paymentAmount;
	
    private ScheduledTransactionBean currentTransaction;

    // Set the transaction details and populate the fields
    public void setTransaction(ScheduledTransactionBean transaction) {
        this.currentTransaction = transaction;

        // Populate the fields with the transaction details
        scheduleName.setText(transaction.getScheduleName());
        accountName.setText(transaction.getAccountName());
        transactionType.setText(transaction.getTransactionType());
        frequency.setText(transaction.getTransactionFreq());
        dueDate.setText(String.valueOf(transaction.getDueDate()));
        paymentAmount.setText(String.valueOf(transaction.getPaymentAmount()));
    }
    
	private CommonObjs commonObjs = CommonObjs.getInstance();
    private HBox mainBox = commonObjs.getMainBox();
    
    
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
  	
    public void switchToViewScheduledTransactions() {
        loadScene("/view/viewScheduledTransactions.fxml"); // Use the loadScene method
    }
}
