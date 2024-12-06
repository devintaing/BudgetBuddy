package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import application.beans.TransactionBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class accountDetailController {
	
    @FXML
    private Label type;
    @FXML
    private Label date;
    @FXML
    private Label description;
    @FXML
    private Label payment;
    @FXML
    private Label deposit;
    
	private CommonObjs commonObjs = CommonObjs.getInstance();
    private HBox mainBox = commonObjs.getMainBox();
    
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
  	
    public void switchToViewAccountReport() {
        loadScene("/view/viewAccountReport.fxml");
    }
    
    // Method to populate the labels with transaction details
    public void setTransactionDetails(TransactionBean transaction) {
        type.setText(transaction.getTransactionType());
        date.setText(transaction.getTransactionDate());
        description.setText(transaction.getTransactionDescription());

        // Format payment and deposit amounts
        payment.setText(String.format("$%.2f", transaction.getPaymentAmount()));
        deposit.setText(String.format("$%.2f", transaction.getDepositAmount()));
    }

}
