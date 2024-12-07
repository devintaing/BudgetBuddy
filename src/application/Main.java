package application;
	
import application.DAOs.AccountDAO;
import application.DAOs.ScheduledTransactionDAO;
import application.DAOs.TransactionDAO;
import application.DAOs.TransactionTypeDAO;
import application.connection.SqliteConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.Objects;


public class Main extends Application {
	private final CommonObjs commonObjs = CommonObjs.getInstance();
	private Connection connection;

	@Override
	public void start(Stage primaryStage) {
		try {
			connection = SqliteConnection.Connector();
			//Keep a reference of the connection inside the commonObjs object
			commonObjs.setConnection(connection);
			AccountDAO.createAccountTable();
			TransactionTypeDAO.createTransactionTypeTable();
			TransactionDAO.createTransactionTable();
			ScheduledTransactionDAO.createScheduledTransactionTable();
		}
		catch (Exception e){
			System.out.println("Connection Error");
			e.printStackTrace();
		}
		
		try {
			// Load the main FXML file for the UI
			// Create a new scene using the loaded layout
			// Load and apply CSS stylesheet to the scene
			// Sets up the main stage (window)
			HBox mainBox = (HBox)FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/main.fxml")));
			
			Scene scene = new Scene(mainBox);
			
			String css = Objects.requireNonNull(this.getClass().getResource("/css/application.css")).toExternalForm();
			scene.getStylesheets().add(css);

			primaryStage.getIcons().add(new Image("/images/icon.png"));

			primaryStage.setTitle("Budget Buddy");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
			commonObjs.setMainBox(mainBox);
		} catch(Exception e) {
			System.out.println("Error loading main Class");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
