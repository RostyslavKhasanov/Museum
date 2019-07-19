package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for create connection with DB.
 *
 * @author Nazar Stasyuk
 * @version 1.0
 */
public class Connector {
  private static final String DB_LINK = "jdbc:mysql://localhost:3306/";
  private static final String DB_NAME = "museum";
  private static final String DB_USER = "root";
  private static final String DB_PASSWORD = "kate";

  /**
   * Method for creating connection with mysql DB.
   *
   * @return connection with DB
   * @exception ClassNotFoundException - java can`t find Driver.
   * @exception SQLException - error in sql query.
   */
  public static Connection getConnection() throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    return DriverManager.getConnection(
        DB_LINK + DB_NAME + "?useSSH=false&&serverTimezone=UTC", DB_USER, DB_PASSWORD);

  }
}