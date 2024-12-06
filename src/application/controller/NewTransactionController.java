package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

import application.CommonObjs;
import application.DAOs.AccountDAO;
import application.DAOs.TransactionDAO;
import application.DAOs.TransactionTypeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class NewTransactionController {
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
	
	double payment = 0;
	double deposit = 0;
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
    private HBox mainBox = commonObjs.getMainBox();
	
    private ArrayList<String> accountNames = new ArrayList<>();
    private ArrayList<String> transactionTypes = new ArrayList<>();
    
    public void initialize() {
    	//get all account names, set default value of dropdown to first value in list
    	accountNames = AccountDAO.getAccountNamesList();
    	accountName.getItems().addAll(accountNames);
    	if (accountNames.size()>0)
    		accountName.setValue(accountNames.get(0));
    	
    	//get all transaction types, set default value of dropdown to first value in list
    	transactionTypes = TransactionTypeDAO.getTransactionTypesList();
    	transactionType.getItems().addAll(transactionTypes);
    	if (transactionTypes.size()>0)
    		transactionType.setValue(transactionTypes.get(0));
    	
    	//set default value of date picker to current date
		transactionDate.setValue(LocalDate.now());
	}
    
    //same loadScene as mainController, replaces 2nd child of mainBox
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
    
  	//switch to homepage if cancel button is clicked
    public void switchToHome() {
        loadScene("/view/homepage.fxml"); // Use the loadScene method
    }
    
    public void submitButton(ActionEvent event) {
        // Prevents user from leaving the required fields empty
        if(accountName.getValue()==null || transactionType.getValue()==null|| transactionDate.getValue()==null || 
        		transactionDescription.getText().isBlank() || (paymentAmount.getText().isBlank() && depositAmount.getText().isBlank())) {
        	
        	showAlert("Please fill in the required fields.");
        	return;
        }
        
        //get all information from the form
        String account = accountName.getValue().toString();
        String type = transactionType.getValue().toString();
        String date = transactionDate.getValue().toString();
        String description = transactionDescription.getText();
        String paymentStr = paymentAmount.getText();
        String depositStr = depositAmount.getText();
        
        // Validate payment amount
        if(!paymentStr.isEmpty()) {
        	try {
        		payment = Double.parseDouble(paymentStr);
        	} catch(NumberFormatException e) {
        		showAlert("Payment amount must be a number!");
        		return;
        	}
        }
        // previous bug: if paymentStr is empty, previous value of field is returned
        // overwrites previous value with 0 to fix
        else {
        	payment = 0;
        }
        
        // Validate deposit amount
        if(!depositStr.isEmpty()) {
        	try {
        		deposit = Double.parseDouble(depositStr);
        	} catch(NumberFormatException e) {
        		showAlert("Deposit amount must be a number!");
        		return;
        	}
        }
        else {
        	deposit = 0;
        }
        
        // Add transaction to DB
        TransactionDAO.addTransaction(account, type, date, description, payment, deposit);
        System.out.printf("Sucessfully saved a transaction!%n - Account: %s%n - Type: %s%n - Date: %s%n - Descriptiion: %s%n - Payment Amount: $%.2f%n - Deposit Amount: $%.2f%n", account, type, date, description, payment, deposit);
        
        // Success pop-up
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("Transaction successfully added");
        alert.showAndWait();
    }
    
    private void showAlert(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Error");
    	alert.setHeaderText(null);
    	alert.setContentText(message);
    	alert.showAndWait();
    }
}
