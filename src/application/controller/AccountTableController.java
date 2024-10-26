package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.beans.AccountBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AccountTableController implements Initializable {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TableView<AccountBean> accountTableView;
	
	@FXML
	private TableColumn<AccountBean, String> accountCol;
	
	@FXML
	private TableColumn<AccountBean, String> dateCol;
	
	@FXML
	private TableColumn<AccountBean, Double> balCol;
	
	ObservableList<AccountBean> list = FXCollections.observableArrayList(
		new AccountBean("testName", "10/24/24", 193),
		new AccountBean("testName2", "10/25/24", 201)
	);
			

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		accountCol.setCellValueFactory(new PropertyValueFactory<AccountBean, String>("accountName"));
		dateCol.setCellValueFactory(new PropertyValueFactory<AccountBean, String>("openingDate"));
		balCol.setCellValueFactory(new PropertyValueFactory<AccountBean, Double>("balance"));
		accountTableView.setItems(list);
	}
	
	
    private void loadScene(String fxmlFile, ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxmlFile));
        scene = new Scene(root);

        String css = getClass().getResource("/css/application.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        loadScene("/view/Homepage.fxml", event); // Use the loadScene method
    }
}
