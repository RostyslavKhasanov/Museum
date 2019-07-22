package services;

import dto.ExcursionDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for serve Excursion.
 *
 * @author Kateryna Horokh
 * @version 1.0
 */
public class ExcursionService {

  private Connection connection;

  private final String ID = "id";
  private final String BEGIN = "begin";
  private final String END = "end";
  private final String PRICE = "price";
  private final String WORKER_ID = "worker_id";

  public ExcursionService(Connection connection) {
    this.connection = connection;
  }

  /**
   * Method that find excursions at given period.
   *
   * @return List of ExcursionDto
   * @exception SQLException - error in sql query.
   */
  public List<ExcursionDto> findByDate(LocalDateTime start, LocalDateTime end) throws SQLException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "select *  from museum.excursion where begin >= '"
                + start.format(formatter)
                + "' and end <= '"
                + end.format(formatter)
                + "'");
    ResultSet resultSet = preparedStatement.executeQuery();

    return getExcursions(resultSet);
  }

  /**
   * Method that find count of excursions at given period.
   *
   * @return int - count of excursions at given period.
   * @exception SQLException - error in sql query.
   */
  public int findCountByPeriod(LocalDateTime start, LocalDateTime end) throws SQLException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "select count(*) as c "
                + "from museum.excursion where begin >= '"
                + start.format(formatter)
                + "' and end <= '"
                + end.format(formatter)
                + "'");

    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
      return resultSet.getInt("c");
    } else {
      return 0;
    }
  }

  /**
   * Method for find excursion particular worker.
   *
   * @return List of ExcursionDto
   * @exception SQLException - error in sql query.
   */
  public List<ExcursionDto> findByWorkerId(int worker_id) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from museum.excursion where worker_id = ?");
    preparedStatement.setInt(1, worker_id);

    ResultSet resultSet = preparedStatement.executeQuery();
    return getExcursions(resultSet);
  }

  /**
   * Method for get list of ExcursionDto by the result set. Almost this method for fix duplicate code.
   *
   * @return ExcursionDto
   * @exception SQLException - error in sql query
   */
  private List<ExcursionDto> getExcursions(ResultSet resultSet) throws SQLException {
    List<ExcursionDto> excursions = new ArrayList<>();
    while (resultSet.next()) {
      excursions.add(
          new ExcursionDto(
              resultSet.getInt(ID),
              resultSet.getObject(BEGIN, LocalDateTime.class),
              resultSet.getObject(END, LocalDateTime.class),
              resultSet.getDouble(PRICE),
              resultSet.getInt(WORKER_ID)));
    }
    return excursions;
  }
}
