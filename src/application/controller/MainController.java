package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
    private void loadScene(String fxmlFile, ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxmlFile));
        scene = new Scene(root);

        String css = getClass().getResource("/css/application.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    // Switches to the home screen
    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        loadScene("/view/Homepage.fxml", event); // Use the loadScene method
    }

    // Switches to the "New Account" screen
    @FXML
    public void switchToNewAccount(ActionEvent event) throws IOException {
        loadScene("/view/NewAccount.fxml", event); // Use the loadScene method
    }
    
    // Switches to the "View Accounts" screen
    @FXML
    public void switchToViewAccounts(ActionEvent event) throws IOException {
        loadScene("/view/viewAccounts.fxml", event); // Use the loadScene method
    }
    // Switches to the "New Transaction" screen
    @FXML
    public void switchToNewTransaction(ActionEvent event) throws IOException {
        loadScene("/view/newTransaction.fxml", event); // Use the loadScene method
    }


}
