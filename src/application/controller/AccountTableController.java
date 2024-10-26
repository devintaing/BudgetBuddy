package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.beans.AccountBean;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AccountTableController implements Initializable {
	@FXML
	private TableView<AccountBean> accountTableView;
	
	@FXML
	private TableColumn<AccountBean, String> accountCol;
	
	@FXML
	private TableColumn<AccountBean, String> dateCol;
	
	@FXML
	private TableColumn<AccountBean, Double> balCol;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// TODO Auto-generated method stub
		accountCol.setCellValueFactory(new PropertyValueFactory<AccountBean, String>("accountName"));
		dateCol.setCellValueFactory(new PropertyValueFactory<AccountBean, String>("openingDate"));
		balCol.setCellValueFactory(new PropertyValueFactory<AccountBean, Double>("balance"));
		
	}
}
