package application;

import java.sql.Connection;

import javafx.scene.layout.HBox;

public class CommonObjs {
	
	private static CommonObjs commonObjs = new CommonObjs();
	
	private Connection connection;
	private HBox mainBox; 
	
	public HBox getMainBox() {
		return mainBox;
	}

	public void setMainBox(HBox mainBox) {
		this.mainBox= mainBox;
	}

	private CommonObjs() {}
	
	public static CommonObjs getInstance() {
		return commonObjs;
	}

	// Getter
	public Connection getConnection() {
		return connection;
	}

	// Setter
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
}
