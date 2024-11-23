package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

import application.CommonObjs;
import application.DAOs.AccountDAO;
import application.DAOs.TransactionDAO;
import application.DAOs.TransactionTypeDAO;
import application.beans.TransactionBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class EditTransactionController {
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
    
    private ArrayList<String> accountNames = new ArrayList<>();
    private ArrayList<String> transactionTypes = new ArrayList<>();
    
    private TransactionBean currentTransaction;

    // Set the transaction details and populate the fields
    public void setTransaction(TransactionBean transaction) {
        this.currentTransaction = transaction;
        
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
        
    	// Populate the fields with the transaction details
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

	@FXML
	public void saveTransactionHandler() {
		Pair<Boolean, String> saveResult = saveTransaction();

		boolean success = saveResult.getKey();
		String message = saveResult.getValue();

		Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
		alert.setTitle(success ? "Transaction Saved" : "Save Failed");
		alert.setHeaderText(success ? "Transaction was successfully saved." : "Failed to save the transaction.");
		alert.setContentText(message);
		alert.showAndWait();
	}

	private Pair<Boolean, String> saveTransaction() {
		// Check if text entries required are present, if not alert user to fill them again and resubmit
		if (accountName.getValue() == null || transactionType.getValue() == null || transactionDate.getValue() == null || transactionDescription.getText().isEmpty() || (paymentAmount.getText().isEmpty() && depositAmount.getText().isEmpty())) {
			return new Pair<>(false, "Please fill in the required fields.");
		}
		
		String account = accountName.getValue().toString();
        String type = transactionType.getValue().toString();
        String date = transactionDate.getValue().toString();
        String description = transactionDescription.getText();
        String paymentStr = paymentAmount.getText();
        String depositStr = depositAmount.getText();
		
		// Now attempt to do entry
		try {
			ArrayList<String> newData = new ArrayList<>();
			newData.add(account);
			newData.add(type);
			newData.add(date);
			newData.add(description);
			
			if(!paymentStr.isEmpty()) {
				try {
					Double.parseDouble(paymentStr);
					newData.add(paymentStr);
				}
				catch(NumberFormatException e) {
					return new Pair<>(false, "Payment amount must be a number!");
				}
			}
			else {
				newData.add("0");
			}
			
			if(!depositStr.isEmpty()) {
				try {
					Double.parseDouble(depositStr);
					newData.add(depositStr);
				}
				catch(NumberFormatException e) {
					return new Pair<>(false, "Deposit amount must be a number!");
				}
			}
			else {
				newData.add("0");
			}

			// Attempt the edit on data record entry
			boolean success = TransactionDAO.editTransaction(this.currentTransaction, newData);

			if (success) {
				return new Pair<>(true, "Transaction successfully updated.");
			} else {
				return new Pair<>(false, "Failed to update the transaction. No changes were made.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Pair<>(false, "Error occurred when attempting to write data.");
		}
	}
}
