package application.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.DAOs.ScheduledTransactionDAO;
import application.beans.ScheduledTransactionBean;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class HomepageController implements Initializable {
	@FXML
	private Text dateText;
	
	@FXML
	private Text numTransactionsDueText;
	
	@FXML
	private Text transactionsDueText;
	
	@FXML
	private Text noTransactionsDueText;
	
	@FXML
	private TableView<ScheduledTransactionBean> transactionsDueTableView;
	
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
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Get and set today's date
		String todaysDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
		dateText.setText(todaysDate);
		
		// Get and set number of transactions due
		ObservableList<ScheduledTransactionBean> list = ScheduledTransactionDAO.getScheduledTransactions();
		ObservableList<ScheduledTransactionBean> dueList = FXCollections.observableArrayList();
		int transactionsDue = 0;
		int dayOfMonth = LocalDate.now().getDayOfMonth();
		
		for(ScheduledTransactionBean bean : list) {
			if(bean.getDueDate() == dayOfMonth) {
				transactionsDue++;
				dueList.add(bean);
			}
		}
		numTransactionsDueText.setText(String.valueOf(transactionsDue));
		
		// If there is only 1 transaction due, fix grammar (transactions -> transaction)
		if(transactionsDue == 1) {
			transactionsDueText.setText("transaction due today.");
		}
		
		if(dueList.size() != 0) {
			noTransactionsDueText.setVisible(false);
			
			nameCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, String>("scheduleName"));
			accountCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, String>("accountName"));
			typeCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, String>("transactionType"));
			freqCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, String>("transactionFreq"));
			dateCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, Integer>("dueDate"));
			payCol.setCellValueFactory(new PropertyValueFactory<ScheduledTransactionBean, Double>("paymentAmount"));
			
			transactionsDueTableView.setItems(dueList);
			
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
		else {
			transactionsDueTableView.setVisible(false);
		}
	}
	
}
