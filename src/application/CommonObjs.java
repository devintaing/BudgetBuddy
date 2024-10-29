package application;

import java.sql.Connection;

public class CommonObjs {
	
	private static CommonObjs commonObjs = new CommonObjs();
	
	private Connection connection;
	
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
