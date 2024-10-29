package application.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController {

	@FXML HBox mainBox;
	
	@FXML
	public void initialize() {
		try {
			switchToHome();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error loading homepage");
			e.printStackTrace();
		}
	}
	
    private void loadScene(String fxmlFile) throws IOException {
    	URL url = getClass().getResource(fxmlFile);
    	
    	try {
			AnchorPane paneHome = (AnchorPane)FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() >1)
				mainBox.getChildren().remove(1);
			
			mainBox.getChildren().add(paneHome);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    // Switches to the home screen
    @FXML
    public void switchToHome() throws IOException {
        loadScene("/view/homepage.fxml"); // Use the loadScene method
    }

    // Switches to the "New Account" screen
    @FXML
    public void switchToNewAccount() throws IOException {
        loadScene("/view/newAccount.fxml"); // Use the loadScene method
    }
    
    // Switches to the "View Accounts" screen
    @FXML
    public void switchToViewAccounts() throws IOException {
        loadScene("/view/viewAccounts.fxml"); // Use the loadScene method
    }
    // Switches to the "New Transaction" screen
    @FXML
    public void switchToNewTransaction() throws IOException {
        loadScene("/view/newTransaction.fxml"); // Use the loadScene method
    }


}
