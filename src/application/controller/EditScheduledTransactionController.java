package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import application.CommonObjs;
import application.DAOs.AccountDAO;
import application.DAOs.ScheduledTransactionDAO;
import application.DAOs.TransactionTypeDAO;
import application.beans.ScheduledTransactionBean;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class EditScheduledTransactionController {
	@FXML
	TextField scheduleName;
	
	@FXML
	ChoiceBox<String> accountName;

	@FXML
	ChoiceBox<String> transactionType;
	
	@FXML
	ChoiceBox<String> frequency;

	@FXML
	TextField dueDate;

	@FXML
	TextField paymentAmount;
	
	private ArrayList<String> accountNames = new ArrayList<>();
    private ArrayList<String> transactionTypes = new ArrayList<>();
	
    private ScheduledTransactionBean currentTransaction;
    private int id;

    // Set the transaction details and populate the fields
    public void setTransaction(ScheduledTransactionBean transaction) {
        this.currentTransaction = transaction;
        id = currentTransaction.getId();

        // Populate the fields with the transaction details
        scheduleName.setText(transaction.getScheduleName());
        
      //get all account names, set default value of dropdown to to current value
    	accountNames = AccountDAO.getAccountNamesList();
    	accountName.getItems().addAll(accountNames);
    	if (accountNames.size()>0)
    		accountName.setValue(transaction.getAccountName());
    	
    	//get all transaction types, set default value of dropdown to to current value
    	transactionTypes = TransactionTypeDAO.getTransactionTypesList();
    	transactionType.getItems().addAll(transactionTypes);
    	if (transactionTypes.size()>0)
    		transactionType.setValue(transaction.getTransactionType());
        
        frequency.setValue(transaction.getTransactionFreq());
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
		if (scheduleName.getText().isEmpty() || accountName.getValue() == null || transactionType.getValue() == null || frequency.getValue() == null || dueDate.getText().isEmpty() || paymentAmount.getText().isEmpty()) {
			return new Pair<>(false, "Please fill in the required fields.");
		}
		
		// Collect form information
        String dateStr = dueDate.getText();
        String paymentStr = paymentAmount.getText();
		
        // Payment amount validation
    	try {
    		Double.parseDouble(paymentStr);
    	} catch(NumberFormatException e) {
    		return new Pair<>(false, "Payment amount must be a number!");
    	}
        
        // Date validation
    	int date;
    	try {
    		date = Integer.parseInt(dateStr);
    	} catch(NumberFormatException e) {
    		return new Pair<>(false, "Due date must be an Integer!");
    	}
    	if (date < 1 || date > 31) {
    		return new Pair<>(false,"Due date must be a valid day of the month! (between 1-31)");
    	}
        
		// Duplicate name handling
		ObservableList<ScheduledTransactionBean> list = ScheduledTransactionDAO.getScheduledTransactions();
        for (ScheduledTransactionBean bean:list) {
        	if(bean.getScheduleName().toLowerCase().equals(scheduleName.getText().toLowerCase()) && bean.getId()!=id) {
            	return new Pair<>(false, "Transaction name already exists!");
            }
        }

		// Now attempt to do entry
		try {
			ArrayList<String> newData = new ArrayList<>();
			newData.add(scheduleName.getText()); // ScheduleName
			newData.add(accountName.getValue().toString()); // AccountName
			newData.add(transactionType.getValue().toString()); // TransactionType
			newData.add(frequency.getValue().toString()); // Frequency
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
