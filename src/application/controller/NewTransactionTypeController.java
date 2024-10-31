package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

import application.CommonObjs;
import application.DAOs.TransactionTypeDAO;
import javafx.event.ActionEvent;
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
  		HashSet<String> transactionTypes = TransactionTypeDAO.getTransactionTypesSet();
  		
  		String typeName = TypeName.getText().trim();
  		
  		if(typeName.isEmpty()) {
  			showAlert("Transaction type name is required!");
  			return;
  		}
  		if(transactionTypes.contains(typeName)) {
  			showAlert("Transaction type already exists!");
  			return;
  		}
  		
  		System.out.println("Transaction type: "+typeName + " added.");
  		TransactionTypeDAO.addTransactionType(typeName);
  		
  		// Show success message
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
