package application.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import application.DAOs.AccountDAO;
import application.beans.AccountBean;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AccountTableController implements Initializable {
	@FXML
	private TableView<AccountBean> accountTableView;
	
	@FXML
	private TableColumn<AccountBean, String> accountCol;
	
	@FXML
	private TableColumn<AccountBean, String> dateCol;
	
	@FXML
	private TableColumn<AccountBean, Double> balCol;
	
	ObservableList<AccountBean> list;
			
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		accountCol.setCellValueFactory(new PropertyValueFactory<AccountBean, String>("accountName"));
		dateCol.setCellValueFactory(new PropertyValueFactory<AccountBean, String>("openingDate"));
		balCol.setCellValueFactory(new PropertyValueFactory<AccountBean, Double>("balance"));
		
		//sets how the balance column formats the cells
		balCol.setCellFactory(tc -> new TableCell<AccountBean, Double>() {
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

		list = AccountDAO.getAccounts();
		accountTableView.setItems(list);
	}
}
