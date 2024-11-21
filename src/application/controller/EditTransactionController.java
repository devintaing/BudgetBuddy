package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

import application.CommonObjs;
import application.DAOs.TransactionDAO;
import application.beans.TransactionBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class EditTransactionController {
	@FXML
	TextField accountName;

	@FXML
	TextField transactionType;

	@FXML
	DatePicker transactionDate;

	@FXML
	TextField transactionDescription;

	@FXML
	TextField paymentAmount;

	@FXML
	TextField depositAmount;

	private CommonObjs commonObjs = CommonObjs.getInstance();
    private HBox mainBox = commonObjs.getMainBox();

    private TransactionBean currentTransaction;

    // Set the transaction details and populate the fields
    public void setTransaction(TransactionBean transaction) {
        this.currentTransaction = transaction;

        // Populate the fields with the transaction details
        accountName.setText(transaction.getAccountName());
        transactionType.setText(transaction.getTransactionType());
        transactionDate.setValue(LocalDate.parse(transaction.getTransactionDate()));
        transactionDescription.setText(transaction.getTransactionDescription());
        paymentAmount.setText(String.valueOf(transaction.getPaymentAmount()));
        depositAmount.setText(String.valueOf(transaction.getDepositAmount()));

    }

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


    public void switchToViewTransactions() {
        loadScene("/view/viewTransactions.fxml"); // Use the loadScene method
    }

	@FXML
	public void saveTransactionHandler() {
		Pair<Boolean, String> saveResult = saveTransaction();

		boolean success = saveResult.getKey();
		String message = saveResult.getValue();

		Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
		alert.setTitle(success ? "Transaction Saved" : "Save Failed");
		alert.setHeaderText(success ? "Transaction was successfully saved." : "Failed to save the transaction.");
		alert.setContentText(message);
		alert.showAndWait();
	}

	private Pair<Boolean, String> saveTransaction() {
		// Check if text entries required are present, if not alert user to fill them again and resubmit
		if (accountName.getText().isEmpty() || transactionType.getText().isEmpty() || transactionDate.getValue() == null || transactionDescription.getText().isEmpty()) {
			return new Pair<>(false, "All required fields were not filled in!");
		}

		if (depositAmount.getText().isEmpty()){
			return new Pair<>(false, "You left the deposit amount empty so there will be no changes to this scheduled transaction.");
		}

		if (paymentAmount.getText().isEmpty()){
			return new Pair<>(false, "You left the payment amount empty so there will be no changes to this scheduled transaction.");
		}

		// Now attempt to do entry
		try {
			ArrayList<String> newData = new ArrayList<>();
			newData.add(accountName.getText());
			newData.add(transactionType.getText());
			newData.add(transactionDate.getValue().toString());
			newData.add(transactionDescription.getText());
			newData.add(paymentAmount.getText().isEmpty() ? null : paymentAmount.getText());
			newData.add(depositAmount.getText().isEmpty() ? null : depositAmount.getText());

			// Attempt the edit on data record entry
			boolean success = TransactionDAO.editTransaction(this.currentTransaction, newData);

			if (success) {
				return new Pair<>(true, "Transaction successfully updated.");
			} else {
				return new Pair<>(false, "Failed to update the transaction. No changes were made.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Pair<>(false, "Error occurred when attempting to write data.");
		}
	}
}
