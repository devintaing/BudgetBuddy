package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;

import application.CommonObjs;
import application.DAOs.AccountDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class NewAccountController {
	@FXML
	TextField accountName;
	@FXML
	TextField openingBalance;
	@FXML
	DatePicker openingDate;
	
	
	double balance;
	String name;
    
    private CommonObjs commonObjs = CommonObjs.getInstance();
    private HBox mainBox = commonObjs.getMainBox();
	
	public void initialize() {
		openingDate.setValue(LocalDate.now());
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
	
    //handler for user submitting the form
	public void submitButton(ActionEvent event) {
		// Prevents user from leaving the required fields empty
        if (accountName.getText().isBlank() || openingBalance.getText().isBlank() || openingDate.getValue() == null) {
            showAlert("All fields are required!");
            return;
        }
		
        // Collect form information
        String name = accountName.getText().trim();
        String balanceStr = openingBalance.getText();
        String dateStr = openingDate.getValue().toString();
        
        // Duplicate name handling
        HashSet<String> accountNames = AccountDAO.getAccountNamesSet();
        if (accountNames.contains(name.toLowerCase())) {
            showAlert("Account name already exists!");
            return;
        }
        
        // Opening balance validation
        try {
            balance = Double.parseDouble(balanceStr);
        } catch (NumberFormatException e) {
            showAlert("Opening balance must be a number!");
            return;
        }

        // Add account to DB
        AccountDAO.addAccount(name, dateStr, balance);
        System.out.printf("Successfully saved an account!%n - Name: %s%n - Opening Balance: %.2f%n - Opening Date: %s%n", name, balance, dateStr);

        // Show success message
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Account successfully added!");
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
