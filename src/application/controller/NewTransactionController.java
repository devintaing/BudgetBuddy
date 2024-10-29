package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTransactionController {
	@FXML
	ChoiceBox accountName;

	@FXML
	ChoiceBox transactionType;

	@FXML
	DatePicker transactionDate;

	@FXML
	TextField transactionDescription;

	@FXML
	TextField paymentAmount;

	@FXML
	TextField depositAmount;
	
	double payment;
	double deposit;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    private void loadScene(String fxmlFile, ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxmlFile));
        scene = new Scene(root);

        String css = getClass().getResource("/css/application.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToHome(ActionEvent event) throws IOException {
        loadScene("/view/Homepage.fxml", event); // Use the loadScene method
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
        
        // If user does not input a deposit amount, validate payment amount
        if(depositStr.isEmpty()) {
        	try {
        		payment = Double.parseDouble(paymentStr);
        	} catch(NumberFormatException e) {
        		showAlert("Payment amount must be a number!");
        		return;
        	}
        }
        
        // If user does not input a payment amount, validate deposit amount
        if(paymentStr.isEmpty()) {
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
        System.out.printf("Saved a transaction of %.");
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
