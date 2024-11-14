package application.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import application.DAOs.ScheduledTransactionDAO;
import application.beans.ScheduledTransactionBean;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

	ObservableList<ScheduledTransactionBean> list;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		nameCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, String>("scheduleName"));
		accountCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, String>("accountName"));
		typeCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, String>("transactionType"));
		freqCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, String>("transactionFreq"));
		dateCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, Integer>("dueDate"));
		payCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, Double>("paymentAmount"));

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

		list = ScheduledTransactionDAO.getScheduledTransactions();
		schedTransTableView.setItems(list);
	}
}
