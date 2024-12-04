package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import application.beans.TransactionBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class AccountReportController {

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
	ChoiceBox<String> accountName;
	
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

    public void switchToViewAccountReport() {
        loadScene("/view/viewAccountReport.fxml");
    }
}
