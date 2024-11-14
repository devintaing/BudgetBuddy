package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import application.CommonObjs;
import application.DAOs.AccountDAO;
import application.DAOs.ScheduledTransactionDAO;
import application.DAOs.TransactionTypeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class NewScheduledTransactionController {
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
	
	double payment = 0;
	int date = 0;
	
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
    	
    	//add a monthly frequency 
    	frequency.getItems().add("Monthly");
    	frequency.setValue("Monthly");
    	
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
        if(scheduleName.getText().isEmpty() || accountName.getValue()==null || transactionType.getValue()==null || 
        	frequency.getValue()==null || dueDate.getText().isEmpty() || paymentAmount.getText().isEmpty()) {
        	
        	showAlert("Please fill in the required fields.");
        	return;
        }
        
        // Collect form information
        String scheduleNameStr = scheduleName.getText();
        String account = accountName.getValue().toString();
        String type = transactionType.getValue().toString();
        String frequencyStr = frequency.getValue().toString();
        String dateStr = dueDate.getText();
        String paymentStr = paymentAmount.getText();
        
        // Duplicate name handling
        HashSet<String> scheduledTransactionNames = ScheduledTransactionDAO.getScheduledTransactionsSet();
        if(scheduledTransactionNames.contains(scheduleNameStr.toLowerCase())) {
        	showAlert("Transaction name already exists!");
        	return;
        }
        
        // Payment amount validation
    	try {
    		payment = Double.parseDouble(paymentStr);
    	} catch(NumberFormatException e) {
    		showAlert("Payment amount must be a number!");
    		return;
    	}
        
        // Date validation
    	try {
    		date = Integer.parseInt(dateStr);
    	} catch(NumberFormatException e) {
    		showAlert("Due date must be an Integer!");
    		return;
    	}
    	if (date < 1 || date > 31) {
    		showAlert("Due date must be a valid day of the month! (between 1-31)");
    	}
        
        // Add scheduled transaction to DB
        ScheduledTransactionDAO.addScheduledTransaction(scheduleNameStr, account, type, frequencyStr, date, payment);
        System.out.printf("Successfully saved a scheduled transaction!%n - Name: %s%n - Account: %s%n - Type: %s%n - Frequency: %s%n - Due Date: %s%n - Payment: %s%n", scheduleNameStr, account, type, frequencyStr, dateStr, paymentStr);
        
        // Success pop-up
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("Scheduled Transaction successfully added");
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
