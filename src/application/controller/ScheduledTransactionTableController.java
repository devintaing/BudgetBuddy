package application.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import application.DAOs.ScheduledTransactionDAO;
import application.beans.ScheduledTransactionBean;
import application.beans.TransactionBean;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScheduledTransactionTableController implements Initializable {
	@FXML
	private TableView<ScheduledTransactionBean> schedTransTableView;

	@FXML
	private TableColumn<ScheduledTransactionBean, String> nameCol;
	
	@FXML
	private TableColumn<ScheduledTransactionBean, String> accountCol;

	@FXML
	private TableColumn<ScheduledTransactionBean, String> typeCol;

	@FXML
	private TableColumn<ScheduledTransactionBean, String> freqCol;
	
	@FXML
	private TableColumn<ScheduledTransactionBean, Integer> dateCol;

	@FXML
	private TableColumn<ScheduledTransactionBean, Double> payCol;

	@FXML
	private TextField scheduledSearchTextBox;
	
	ObservableList<ScheduledTransactionBean> list;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		list = ScheduledTransactionDAO.getScheduledTransactions();

		nameCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, String>("scheduleName"));
		accountCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, String>("accountName"));
		typeCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, String>("transactionType"));
		freqCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, String>("transactionFreq"));
		dateCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, Integer>("dueDate"));
		payCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, Double>("paymentAmount"));

		FilteredList<ScheduledTransactionBean> filteredData = new FilteredList<>(list, b -> true);

		scheduledSearchTextBox.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(ScheduledTransactionBean -> {
				
				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
					return true;
				}
				
				String searchKeyword = newValue.toLowerCase();
				
				if (ScheduledTransactionBean.getAccountName().toLowerCase().indexOf(searchKeyword) > -1) {
					return true;
					
				} else if (ScheduledTransactionBean.getTransactionFreq().toLowerCase().indexOf(searchKeyword) > -1) {
					return true;
					
				} else if (ScheduledTransactionBean.getTransactionType().toLowerCase().indexOf(searchKeyword) > -1) {
					return true;
					
				} else if (ScheduledTransactionBean.getScheduleName().toLowerCase().indexOf(searchKeyword) > -1) {
					return true;
					
				} else 
					return false;
			});
			
		});
		SortedList<ScheduledTransactionBean> sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(schedTransTableView.comparatorProperty());
		
		schedTransTableView.setItems(sortedData);
		
		// sets how the balance column formats the cells
		payCol.setCellFactory(tc -> new TableCell<ScheduledTransactionBean, Double>() {
			@Override
			protected void updateItem(Double balance, boolean empty) {
				super.updateItem(balance, empty);
				if (empty || balance == null) {
					setText(null); // Clear the cell if it's empty or balance is null
				} else {
					// Round the balance to 2 decimal places and add a $ in front
					BigDecimal roundedBalance = new BigDecimal(String.valueOf(balance)).setScale(2,
							RoundingMode.HALF_UP);
					setText("$" + roundedBalance.toString());
				}
			}
		});
	}
}

