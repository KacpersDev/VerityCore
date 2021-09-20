package xyz.cronu.veritycore.mysql;

import java.sql.*;
import java.util.HashMap;

public abstract class Database {

	static Connection connection;

	public Database(String host, String port, String database, String username, String password) throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false",
				username, password);
		if(this.isConnected()){
			this.createTable();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public boolean isConnected(){
		return (connection == null ? false : true);
	}

	public void disconnect(){
		if(isConnected()){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public abstract void createTable();

	public HashMap<String, String> parseResult(ResultSet rs) {
		HashMap<String, String> as = new HashMap<>();
		ResultSetMetaData resultSetMetaData;
		try {
			resultSetMetaData = rs.getMetaData();
			while (rs.next()) {
				for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
					as.put(resultSetMetaData.getColumnName(i), rs.getString(i));
				}
			}
			return as;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


}
