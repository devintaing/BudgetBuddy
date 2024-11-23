package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import application.CommonObjs;
import application.DAOs.ScheduledTransactionDAO;
import application.beans.ScheduledTransactionBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

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

	@FXML
	public void saveScheduledTransactionHandler() {
		Pair<Boolean, String> saveResult = saveScheduledTransaction();

		boolean success = saveResult.getKey();
		String message = saveResult.getValue();

		Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
		alert.setTitle(success ? "Scheduled Transaction Saved" : "Save Failed");
		alert.setHeaderText(success ? "Scheduled Transaction was successfully saved." : "Failed to save the transaction.");
		alert.setContentText(message);
		alert.showAndWait();
	}

	private Pair<Boolean, String> saveScheduledTransaction() {
		// Prevents user from leaving the required fields empty
		if (scheduleName.getText().isEmpty() || accountName.getText().isEmpty() || transactionType.getText().isEmpty() || frequency.getText().isEmpty() || dueDate.getText().isEmpty() || paymentAmount.getText().isEmpty()) {
			return new Pair<>(false, "Please fill in the required fields.");
		}
		
		// Duplicate name handling
		HashSet<String> scheduledTransactionNames = ScheduledTransactionDAO.getScheduledTransactionsSet();
        if(scheduledTransactionNames.contains(scheduleName.getText().toLowerCase())) {
        	return new Pair<>(false, "Transaction name already exists!");
        }

		// Now attempt to do entry
		try {
			ArrayList<String> newData = new ArrayList<>();
			newData.add(scheduleName.getText()); // ScheduleName
			newData.add(accountName.getText()); // AccountName
			newData.add(transactionType.getText()); // TransactionType
			newData.add(frequency.getText()); // Frequency
			newData.add(dueDate.getText()); // Date
			newData.add(paymentAmount.getText().isEmpty() ? null : paymentAmount.getText()); // PaymentAmount


			// Attempt the edit on data record entry
			boolean success = ScheduledTransactionDAO.editScheduledTransaction(this.currentTransaction, newData);

			if (success) {
				return new Pair<>(true, "Scheduled Transaction successfully updated.");
			} else {
				return new Pair<>(false, "Failed to update the transaction. No changes were made.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Pair<>(false, "Error occurred when attempting to write data.");
		}
	}
}
