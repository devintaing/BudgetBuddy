package application.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import application.CommonObjs;
import application.DAOs.ScheduledTransactionDAO;
import application.beans.ScheduledTransactionBean;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

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
  	
  	
  	public void switchToEditScheduledTransaction() {
  	    // Get the selected transaction
  	    ScheduledTransactionBean selectedTransaction = schedTransTableView.getSelectionModel().getSelectedItem();

  	    if (selectedTransaction == null) {
  	        showAlert("No transaction selected.");
  	        return;
  	    }

  	    try {
  	        // Load the edit page
  	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editScheduledTransaction.fxml"));
  	        AnchorPane pane = loader.load();

  	        // Get the controller for the edit page
  	        EditScheduledTransactionController controller = loader.getController();

  	        // Pass the selected transaction to the controller
  	        controller.setTransaction(selectedTransaction);

  	        // Replace the current view
  	        if (mainBox.getChildren().size() > 1)
  	            mainBox.getChildren().remove(1);

  	        mainBox.getChildren().add(pane);

  	    } catch (IOException e) {
  	        System.err.println("Error loading editScheduledTransaction.fxml");
  	        e.printStackTrace();
  	    }
  	}

    
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
				
				if (ScheduledTransactionBean.getScheduleName().toLowerCase().indexOf(searchKeyword) > -1) {
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
    private void showAlert(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Error");
    	alert.setHeaderText(null);
    	alert.setContentText(message);
    	alert.showAndWait();
    }
}

