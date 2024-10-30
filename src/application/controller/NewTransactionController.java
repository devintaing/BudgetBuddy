package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import application.CommonObjs;
import application.DAOs.AccountDAO;
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
    	accountNames = AccountDAO.getAccountNamesList();
    	accountName.getItems().addAll(accountNames);
    	accountName.setValue(accountNames.get(0));
    	transactionTypes = TransactionTypeDAO.getTransactionTypesList();
    	transactionType.getItems().addAll(transactionTypes);
    	transactionType.setValue(transactionTypes.get(0));
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        String account = accountName.getValue().toString();
        String type = transactionType.getValue().toString();
        String date = transactionDate.getValue().toString();
        String description = transactionDescription.getText();
        String paymentStr = paymentAmount.getText();
        String depositStr = depositAmount.getText();
        
        // Prevents user from leaving the required fields empty
        if(account.isEmpty() || type.isEmpty() || date.isEmpty() || description.isEmpty() || (paymentStr.isEmpty() && depositStr.isEmpty())) {
        	showAlert("Please fill in the required fields.");
        	return;
        }
        
        // Validate payment amount
        if(!paymentStr.isEmpty()) {
        	try {
        		payment = Double.parseDouble(paymentStr);
        	} catch(NumberFormatException e) {
        		showAlert("Payment amount must be a number!");
        		return;
        	}
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
        
        saveTransaction(account, type, date, description, payment, deposit);
        
        alert.setTitle("Success!");
        alert.setHeaderText("Transaction successfully added");
        alert.showAndWait();
    }
    
    private void saveTransaction (String account, String type, String date, String description, Double payment, Double deposit) {
        //System.out.printf("Saved a transaction of %.");
        //TODO: ACTUALLY SAVE TRANSACTION INFORMATION
    }
    
    private void showAlert(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Error");
    	alert.setHeaderText(null);
    	alert.setContentText(message);
    	alert.showAndWait();
    }
}
