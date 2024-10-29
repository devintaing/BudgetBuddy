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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
	
	private void loadScene(String fxmlFile) throws IOException {
    	URL url = getClass().getResource(fxmlFile);
    	
    	try {
			AnchorPane paneHome = (AnchorPane)FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() >1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(paneHome);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void switchToHome() throws IOException {
        loadScene("/view/Homepage.fxml"); // Use the loadScene method
    }
	
	public void submitButton(ActionEvent event) {
		accountNames = AccountDAO.getAccountNames();
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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

        // If all validations pass, add account name to set
        accountNames.add(name);

        // Save information to be used on other pages or application.database
        saveAccount(name, date, balance);

        // Show success message
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
