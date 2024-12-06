package application.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;

import application.CommonObjs;
import application.DAOs.TransactionTypeDAO;
import application.DAOs.TransactionDAO;
import application.beans.TransactionBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class TransactionTypeReportController {

    @FXML
    private TableView<TransactionBean> transactionTableView;

    @FXML
    private TableColumn<TransactionBean, String> accountCol;

    @FXML
    private TableColumn<TransactionBean, String> dateCol;

    @FXML
    private TableColumn<TransactionBean, String> descCol;

    @FXML
    private TableColumn<TransactionBean, Double> payCol;

    @FXML
    private TableColumn<TransactionBean, Double> depositCol;

    @FXML
    private ChoiceBox<String> transactionType;

    private ObservableList<TransactionBean> transactionList; // All transactions
    private ArrayList<String> transactionTypes; // Transaction types
    private String curTransactionType; // Current Transaction Type

    private CommonObjs commonObjs = CommonObjs.getInstance();
    private HBox mainBox = commonObjs.getMainBox(); // Ensure mainBox is correctly initialized

    private void loadScene(String fxmlFile) {
        URL url = getClass().getResource(fxmlFile);

        if (url == null) {
            System.out.println("Error: FXML file not found at " + fxmlFile);
            return;
        }

        try {
            // Load the FXML file into an AnchorPane
            AnchorPane paneHome = FXMLLoader.load(url);

            // Remove old content and add the new content
            if (mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }
            mainBox.getChildren().add(paneHome);

        } catch (IOException e) {
            System.out.println("Error loading scene from " + fxmlFile);
            e.printStackTrace();
        }
    }

    public void switchToTransactionTypeReport() {
        loadScene("/view/viewTransactionTypeReport.fxml");
    }

    @FXML
    public void initialize() {
        // Load transaction types
        transactionTypes = new ArrayList<>(TransactionTypeDAO.getTransactionTypesList());
        transactionType.getItems().addAll(transactionTypes);
        if (!transactionTypes.isEmpty()) {
            transactionType.setValue(transactionTypes.get(0)); // Set the default value
        }

        // Initialize TableView columns
        initializeTableColumns();

        // Load transactions from the database
        transactionList = TransactionDAO.getTransactions();

        // Populate TableView with default transaction type data
        if (!transactionTypes.isEmpty()) {
            updateTableView(transactionType.getValue());
        }

        // Add listener to update TableView when the ChoiceBox value changes
        transactionType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateTableView(newValue);
            }
        });
        handleRowDoubleClick();
    }

    private void initializeTableColumns() {
        accountCol.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("transactionDescription"));
        payCol.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        depositCol.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));

        // Format Payment Amount column
        payCol.setCellFactory(tc -> new TableCell<TransactionBean, Double>() {
            @Override
            protected void updateItem(Double balance, boolean empty) {
                super.updateItem(balance, empty);
                if (empty || balance == null) {
                    setText(null);
                } else {
                    BigDecimal roundedBalance = new BigDecimal(String.valueOf(balance)).setScale(2, RoundingMode.HALF_UP);
                    setText("$" + roundedBalance.toString());
                }
            }
        });

        // Format Deposit Amount column
        depositCol.setCellFactory(tc -> new TableCell<TransactionBean, Double>() {
            @Override
            protected void updateItem(Double balance, boolean empty) {
                super.updateItem(balance, empty);
                if (empty || balance == null) {
                    setText(null);
                } else {
                    BigDecimal roundedBalance = new BigDecimal(String.valueOf(balance)).setScale(2, RoundingMode.HALF_UP);
                    setText("$" + roundedBalance.toString());
                }
            }
        });
    }
    
    @FXML
    private void handleRowDoubleClick() {
        transactionTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Check for double click
                TransactionBean selectedTransaction = transactionTableView.getSelectionModel().getSelectedItem();
                if (selectedTransaction != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewTransaction.fxml"));
                        AnchorPane pane = loader.load();

                        // Get the controller for the account detail page
                        ViewTransactionController controller = loader.getController();

                        // Pass the selected transaction to the detail controller
                        controller.setTransactionDetails(selectedTransaction);
                        controller.setPrevTransactionType(curTransactionType);

                        // Switch to the account detail page
                        if (mainBox.getChildren().size() > 1) {
                            mainBox.getChildren().remove(1);
                        }
                        mainBox.getChildren().add(pane);
                    } catch (IOException e) {
                        System.out.println("Error loading transaction type detail view.");
                        e.printStackTrace();
                    }
                } else {
                    showAlert("No transaction selected.");
                }
            }
        });
    }

    private void updateTableView(String selectedTransactionType) {
        if (selectedTransactionType == null || selectedTransactionType.isEmpty()) {
            transactionTableView.setItems(FXCollections.emptyObservableList());
            return;
        }
        curTransactionType = selectedTransactionType;

        // Filter transactions by selected transaction type
        ObservableList<TransactionBean> filteredTransactions = transactionList.filtered(
                transaction -> transaction.getTransactionType().equalsIgnoreCase(selectedTransactionType));

        // Update the TableView with filtered data
        transactionTableView.setItems(filteredTransactions);
    }
    
    private void showAlert(String message) {
    	Alert alert = new Alert(Alert.AlertType.ERROR);
    	alert.setTitle("Error");
    	alert.setHeaderText(null);
    	alert.setContentText(message);
    	alert.showAndWait();
    }
    
    public void setTransactionType(String prevTransactionType) {
		updateTableView(prevTransactionType);
		transactionType.setValue(prevTransactionType);
		
	}
}
