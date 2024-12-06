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
    
    private String curAccountName;
  	
    public void switchToViewAccountReport() {
      	try {
  	        // Load the FXML for the edit transaction view
  	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewAccountReport.fxml"));
  	        AnchorPane pane = loader.load();

  	        // Get the controller for the edit view
  	        AccountReportController controller = loader.getController();

  	        // Pass the selected transaction to the controller
  	        controller.setAccount(curAccountName);

  	        // Replace the current view with the edit view
  	        if (mainBox.getChildren().size() > 1) {
  	            mainBox.getChildren().remove(1);
  	        }
  	        mainBox.getChildren().add(pane);

  	    } catch (IOException e) {
  	        System.out.println("Error returning to account report results.");
  	        e.printStackTrace();
  	    }
    }
    
    // Method to populate the labels with transaction details
    public void setTransactionDetails(TransactionBean transaction, String curAccountName) {
        //save state
    	this.curAccountName = curAccountName;
    	
    	type.setText(transaction.getTransactionType());
        date.setText(transaction.getTransactionDate());
        description.setText(transaction.getTransactionDescription());

        // Format payment and deposit amounts
        payment.setText(String.format("$%.2f", transaction.getPaymentAmount()));
        deposit.setText(String.format("$%.2f", transaction.getDepositAmount()));
    }

}
