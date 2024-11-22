package application.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import application.CommonObjs;
import application.DAOs.TransactionDAO;
import application.beans.TransactionBean;
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
	
	@FXML
	private TextField transactionSearchTextBox;
	
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
  	
  	
  	public void switchToEditTransaction() {
  	    // Get the selected transaction
  	    TransactionBean selectedTransaction = transactionTableView.getSelectionModel().getSelectedItem();

  	    if (selectedTransaction == null) {
  	        showAlert("No transaction selected.");
  	        return;
  	    }

  	    try {
  	        // Load the FXML for the edit transaction view
  	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editTransaction.fxml"));
  	        AnchorPane pane = loader.load();

  	        // Get the controller for the edit view
  	        EditTransactionController controller = loader.getController();

  	        // Pass the selected transaction to the controller
  	        controller.setTransaction(selectedTransaction);

  	        // Replace the current view with the edit view
  	        if (mainBox.getChildren().size() > 1) {
  	            mainBox.getChildren().remove(1);
  	        }
  	        mainBox.getChildren().add(pane);

  	    } catch (IOException e) {
  	        System.out.println("Error loading edit transaction view.");
  	        e.printStackTrace();
  	    }
  	}

    
	ObservableList<TransactionBean> list;
			
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		list = TransactionDAO.getTransactions();
		
		accountCol.setCellValueFactory(new PropertyValueFactory<TransactionBean, String>("accountName"));
		typeCol.setCellValueFactory(new PropertyValueFactory<TransactionBean, String>("transactionType"));
		dateCol.setCellValueFactory(new PropertyValueFactory<TransactionBean, String>("transactionDate"));
		descCol.setCellValueFactory(new PropertyValueFactory<TransactionBean, String>("transactionDescription"));
		payCol.setCellValueFactory(new PropertyValueFactory<TransactionBean, Double>("paymentAmount"));
		depositCol.setCellValueFactory(new PropertyValueFactory<TransactionBean, Double>("depositAmount"));


		FilteredList<TransactionBean> filteredData = new FilteredList<>(list, b -> true);
		
		transactionSearchTextBox.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(TransactionBean -> {
					
					if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
						return true;
					}
					
					String searchKeyword = newValue.toLowerCase();
					
					if (TransactionBean.getTransactionDescription().toLowerCase().indexOf(searchKeyword) > -1) {
						return true;
						
					} else 
						return false;
				});
				
			});
		
		SortedList<TransactionBean> sortedData = new SortedList<>(filteredData);
		
		sortedData.comparatorProperty().bind(transactionTableView.comparatorProperty());
		
		transactionTableView.setItems(sortedData);
		
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
	}
    private void showAlert(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Error");
    	alert.setHeaderText(null);
    	alert.setContentText(message);
    	alert.showAndWait();
    }
}
