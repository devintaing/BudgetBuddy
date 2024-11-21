package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

import application.CommonObjs;
import application.DAOs.TransactionTypeDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class NewTransactionTypeController {
	
	@FXML
	TextField TypeName;
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
    private HBox mainBox = commonObjs.getMainBox();
	
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
  	public void submitButton() {
  		// Prevents user from leaving the required fields empty
  		if(TypeName.getText().isEmpty()) {
  			showAlert("Transaction type name is required!");
  			return;
  		}
  		
  		// Collect form information
  		String typeName = TypeName.getText().trim();
  		
  		// Duplicate type handling
  		HashSet<String> transactionTypes = TransactionTypeDAO.getTransactionTypesSet();
  		if(transactionTypes.contains(typeName.toLowerCase())) {
  			showAlert("Transaction type already exists!");
  			return;
  		}
  		
  		// Add transaction type to DB
  		TransactionTypeDAO.addTransactionType(typeName);
  		System.out.printf("Successfully saved a transaction type!%n - Name: %s%n", typeName);
  		
  		// Success pop-up
  		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Transaction type successfully added!");
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
