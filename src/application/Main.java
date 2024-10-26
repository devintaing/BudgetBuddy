package application;
	
import application.DAOs.AccountDAO;
import application.connection.SqliteConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;


public class Main extends Application {
	Connection connection;
	@Override
	public void start(Stage primaryStage) {
		try {
			connection = SqliteConnection.Connector();
			//Keep a reference of the connection inside the commonObjs object
			CommonObjs commonObjs = CommonObjs.getInstance();
			commonObjs.setConnection(connection);
			
			AccountDAO.createAccountTable();
		}
		catch (Exception e){
			System.out.println("Connection Error");
			e.printStackTrace();
		}
		
		try {
			// Load the main FXML file for the UI
			AnchorPane root = FXMLLoader.load(getClass().getResource("/view/homepage.fxml"));
			
			// Create a new scene using the loaded layout
			Scene scene = new Scene(root);
			
			// Load and apply CSS stylesheet to the scene
			String css = this.getClass().getResource("/css/application.css").toExternalForm();
			scene.getStylesheets().add(css);
			
			// Sets up the main stage (window)
			primaryStage.setTitle("Budget Buddy");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
