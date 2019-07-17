package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
  private static final String DB_LINK = "jdbc:mysql://localhost:3306/";
  private static final String DB_NAME = "museum";
  private static final String DB_USER = "root";
  private static final String DB_PASSWORD = "***";

  public static java.sql.Connection getConnection() throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection connection = null;
    connection =
        DriverManager.getConnection(DB_LINK + DB_NAME + "?useSSH=false&&serverTimezone=UTC", DB_USER, DB_PASSWORD);
    return connection;
  }
}
