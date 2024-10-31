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
	
    private static HashSet<String> accountNames;
    
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
		accountNames = AccountDAO.getAccountNamesSet();
		
        String name = accountName.getText().trim();
        String balanceStr = openingBalance.getText().trim();
        LocalDate date = openingDate.getValue();
        
        
        // Prevents user from leaving the required fields empty
        if (name.isEmpty() || balanceStr.isEmpty() || date == null) {
            showAlert("All fields are required!");
            return;
        }
        
        // Check for duplicate account names
        if (accountNames.contains(name)) {
            showAlert("Account name already exists!");
            return;
        }
        try {
            balance = Double.parseDouble(balanceStr);
        } catch (NumberFormatException e) {
            showAlert("Opening balance must be a number!");
            return;
        }

        // Save information to be used on other pages or application.database
        saveAccount(name, date, balance);

        // Show success message
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Account successfully added!");
        alert.showAndWait();
	}
	
    private void saveAccount(String name, LocalDate date, double balance) {
        // Save account information
        System.out.printf("Saved %s with balance %.2f on %s%n", name, balance, date.toString());
        AccountDAO.addAccount(name, date.toString(), balance);
        
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
