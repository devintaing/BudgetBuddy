package application.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import application.DAOs.AccountDAO;
import application.DAOs.ScheduledTransactionDAO;
import application.DAOs.TransactionDAO;
import application.beans.TransactionBean;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TransactionTableController implements Initializable {
	@FXML
	private TableView<TransactionBean> transactionTableView;
	
	@FXML
	private TableColumn<TransactionBean, String> accountCol;
	
	@FXML
	private TableColumn<TransactionBean, String> typeCol;
	
	@FXML
	private TableColumn<TransactionBean, String> dateCol;
	
	@FXML
	private TableColumn<TransactionBean, String> descCol;
	
	@FXML
	private TableColumn<TransactionBean, Double> payCol;
	
	@FXML
	private TableColumn<TransactionBean, Double> depositCol;
	
	ObservableList<TransactionBean> list;
			
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		accountCol.setCellValueFactory(new PropertyValueFactory<TransactionBean, String>("accountName"));
		typeCol.setCellValueFactory(new PropertyValueFactory<TransactionBean, String>("transactionType"));
		dateCol.setCellValueFactory(new PropertyValueFactory<TransactionBean, String>("transactionDate"));
		descCol.setCellValueFactory(new PropertyValueFactory<TransactionBean, String>("transactionDescription"));
		payCol.setCellValueFactory(new PropertyValueFactory<TransactionBean, Double>("paymentAmount"));
		depositCol.setCellValueFactory(new PropertyValueFactory<TransactionBean, Double>("depositAmount"));

		
		//sets how the balance column formats the cells
		payCol.setCellFactory(tc -> new TableCell<TransactionBean, Double>() {
		    @Override
		    protected void updateItem(Double balance, boolean empty) {
		        super.updateItem(balance, empty);
		        if (empty || balance == null) {
		            setText(null); // Clear the cell if it's empty or balance is null
		        } else {
		            // Round the balance to 2 decimal places and add a $ in front
		            BigDecimal roundedBalance = new BigDecimal(String.valueOf(balance)).setScale(2, RoundingMode.HALF_UP);
		            setText("$" + roundedBalance.toString());
		        }
		    }
		});
		//sets how the balance column formats the cells
		depositCol.setCellFactory(tc -> new TableCell<TransactionBean, Double>() {
		    @Override
		    protected void updateItem(Double balance, boolean empty) {
		        super.updateItem(balance, empty);
		        if (empty || balance == null) {
		            setText(null); // Clear the cell if it's empty or balance is null
		        } else {
		            // Round the balance to 2 decimal places and add a $ in front
		            BigDecimal roundedBalance = new BigDecimal(String.valueOf(balance)).setScale(2, RoundingMode.HALF_UP);
		            setText("$" + roundedBalance.toString());
		        }
		    }
		});

		list = TransactionDAO.getTransactions();
		transactionTableView.setItems(list);
	}
}
