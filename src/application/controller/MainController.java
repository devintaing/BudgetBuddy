package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private HBox mainBox;

    @FXML
    private SplitPane sidebar;

    @FXML
    private VBox viewSection; // The VBox containing the "View Accounts" buttons

    @FXML
    private VBox newSection; // The VBox containing the "New Account", "New Transaction", etc.
    
    @FXML 
    private VBox reportSection;
    
    @FXML
    private VBox editSection;
    
    @FXML
    private Button toggleViewButton; // Button that toggles "Views" section

    @FXML
    private Button toggleNewButton; // Button that toggles "New" section
    
    @FXML
    private Button toggleEditButton; // Button that toggles "Edit" section

    @FXML
    private Button toggleReportButton; // Button that toggles "Report" section
    
    @FXML
    public void initialize() {
        new CollapsibleSectionController(viewSection, toggleViewButton);
        new CollapsibleSectionController(newSection, toggleNewButton);
        new CollapsibleSectionController(reportSection, toggleReportButton);
        // Show the homepage on startup
        switchToHome();
    }

    // Loads the given URL and replaces the content on the right of the sidebar
    private void loadScene(String fxmlFile) {
        URL url = getClass().getResource(fxmlFile);

        try {
            AnchorPane pane = FXMLLoader.load(url);

            if (mainBox.getChildren().size() > 1)
                mainBox.getChildren().remove(1);

            mainBox.getChildren().add(pane);

        } catch (IOException e) {
            System.out.println("Error loading scene from " + fxmlFile);
            e.printStackTrace();
        }
    }

    // Switches to the home screen
    @FXML
    public void switchToHome() {
        loadScene("/view/homepage.fxml");
        sidebar.getStyleClass().setAll("default-sidebar");
    }

    // Switches to the "New Account" screen
    @FXML
    public void switchToNewAccount() {
        sidebar.getStyleClass().setAll("red-sidebar");
        loadScene("/view/newAccount.fxml");
    }

    // Switches to the "View Accounts" screen
    @FXML
    public void switchToViewAccounts() {
        sidebar.getStyleClass().setAll("green-sidebar");
        loadScene("/view/viewAccounts.fxml");
    }

    // Switches to the "New Transaction" screen
    @FXML
    public void switchToNewTransaction() {
        sidebar.getStyleClass().setAll("red-sidebar");
        loadScene("/view/newTransaction.fxml");
    }

    // Switches to the "New Transaction Type" screen
    @FXML
    public void switchToNewTransactionType() {
        sidebar.getStyleClass().setAll("red-sidebar");
        loadScene("/view/newTransactionType.fxml");
    }
    
	// Switches to the "New Scheduled Transaction" screen
	@FXML
	public void switchToNewScheduledTransaction() {
	    sidebar.getStyleClass().setAll("red-sidebar");
	    loadScene("/view/newScheduledTransaction.fxml");
	}
	
	// Switches to the "View Transactions" screen
	@FXML
	public void switchToViewTransactions() {
        sidebar.getStyleClass().setAll("green-sidebar");
		loadScene("/view/viewTransactions.fxml");
	}
	
	// Switches to the "View Scheduled Transactions" screen
	@FXML
	public void switchToViewScheduledTransactions() {
        sidebar.getStyleClass().setAll("green-sidebar");
		loadScene("/view/viewScheduledTransactions.fxml");
	}
	
	// Switches to the "Edit Transactions" screen
	@FXML
	public void switchToEditTransaction() {
        sidebar.getStyleClass().setAll("orange-sidebar");
		loadScene("/view/editTransaction.fxml");
	}
	
	// Switches to the "Edit Scheduled Transactions" screen
	@FXML
	public void switchToEditScheduledTransaction() {
        sidebar.getStyleClass().setAll("orange-sidebar");
		loadScene("/view/editScheduledTransaction.fxml");
	}
	// Switches to the "Account Report" screen
	@FXML
	public void switchToAccountReport() {
        sidebar.getStyleClass().setAll("orange-sidebar");
		loadScene("/view/viewAccountReport.fxml");
	}
	// Switches to the "Transaction type report" screen
	@FXML
	public void switchToTransactionReport() {
        sidebar.getStyleClass().setAll("orange-sidebar");
		loadScene("/view/viewTransactionTypeReport.fxml");
	}
}

