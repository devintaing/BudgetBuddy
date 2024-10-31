package application.controller;

import java.net.URL;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;


public class MainController {

	@FXML HBox mainBox;

    @FXML private AnchorPane sidebar;

    @FXML
	public void initialize() {
		// Show the homepage on startup
		switchToHome();
    }
	
	//loads the given URL, and replaces the 2nd child of main/ the content to the right of the sidebar
    private void loadScene(String fxmlFile) {
    	URL url = getClass().getResource(fxmlFile);
        String css = getClass().getResource("/css/application.css").toExternalForm();

        try {
			AnchorPane paneHome = (AnchorPane)FXMLLoader.load(url);

			if (mainBox.getChildren().size() >1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(paneHome);

		} catch (IOException e) {
			System.out.println("erorr loading scene from " + fxmlFile);
			e.printStackTrace();
		}
    }
    
    // Switches to the home screen
    @FXML
    public void switchToHome() {
        loadScene("/view/homepage.fxml"); // Use the loadScene method
        sidebar.getStyleClass().setAll("default-sidebar"); // Apply default gradient
    }

    // Switches to the "New Account" screen
    @FXML
    public void switchToNewAccount() {
        sidebar.getStyleClass().setAll("red-sidebar"); // Apply red gradient
        loadScene("/view/newAccount.fxml"); // Use the loadScene method
    }
    
    // Switches to the "View Accounts" screen
    @FXML
    public void switchToViewAccounts() {
        sidebar.getStyleClass().setAll("green-sidebar"); // Apply green gradient
        loadScene("/view/viewAccounts.fxml"); // Use the loadScene method
    }
    // Switches to the "New Transaction" screen
    @FXML
    public void switchToNewTransaction() {
        sidebar.getStyleClass().setAll("red-sidebar"); // Apply red gradient
        loadScene("/view/newTransaction.fxml"); // Use the loadScene method
    }
 // Switches to the "New Transaction Type" screen
    @FXML
    public void switchToNewTransactionType() {
        sidebar.getStyleClass().setAll("red-sidebar"); // Apply red gradient
        loadScene("/view/newTransactionType.fxml"); // Use the loadScene method
    }
}
