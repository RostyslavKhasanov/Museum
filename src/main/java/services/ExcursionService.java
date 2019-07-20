package services;

import dto.ExcursionDto;
import exceptions.BadIdException;
import javafx.scene.input.DataFormat;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExcursionService {

  private Connection connection;

  private final String ID = "id";
  private final String BEGIN = "begin";
  private final String END = "end";
  private final String WORKER_ID = "worker_id";

  public ExcursionService(Connection connection) {
    this.connection = connection;
  }

  public List<ExcursionDto> findAll() throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from museum.excursion");

    ResultSet resultSet = preparedStatement.executeQuery();

    List<ExcursionDto> excursions = new ArrayList<>();
    while (resultSet.next()) {
      excursions.add(
          new ExcursionDto(
              resultSet.getInt(ID),
              resultSet.getObject(BEGIN, LocalDateTime.class),
              resultSet.getObject(END, LocalDateTime.class),
              resultSet.getInt(WORKER_ID)));
    }
    return excursions;
  }

  public ExcursionDto findById(Integer id) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from museum.excursion where " + ID + " = ?");
    preparedStatement.setInt(1, id);

    ResultSet resultSet = preparedStatement.executeQuery();

    if (resultSet.next()) {
      return new ExcursionDto(
          resultSet.getInt(ID),
          resultSet.getObject(BEGIN, LocalDateTime.class),
          resultSet.getObject(END, LocalDateTime.class),
          resultSet.getInt(WORKER_ID));
    } else throw new BadIdException("In excursion no row with id " + id);
  }

  public List<ExcursionDto> findByDate(String start, String end) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "select * from museum.excursion where " + BEGIN + "  >= ? and " + END + " <= ?");
    preparedStatement.setString(1, start);
    preparedStatement.setString(2, end);

    ResultSet resultSet = preparedStatement.executeQuery();

    List<ExcursionDto> excursions = new ArrayList<>();
    while (resultSet.next()) {
      excursions.add(
          new ExcursionDto(
              resultSet.getInt(ID),
              resultSet.getObject(BEGIN, LocalDateTime.class),
              resultSet.getObject(END, LocalDateTime.class),
              resultSet.getInt(WORKER_ID)));
    }
    return excursions;
  }

  //    public List<ExcursionDto> findByDate(LocalDateTime start, LocalDateTime end) throws
  // SQLException {
  //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  //        String startDate = start.format(formatter);
  //        String endDate = start.format(formatter);
  //        PreparedStatement preparedStatement =
  //                connection.prepareStatement(
  //                        "select * from museum.excursion where " + BEGIN + "  >= ? and " + END +
  // " <= ?");
  //        preparedStatement.setString(1, startDate);
  //        preparedStatement.setString(2, endDate);
  //
  //        ResultSet resultSet = preparedStatement.executeQuery();
  //
  //        List<ExcursionDto> excursions = new ArrayList<>();
  //        while (resultSet.next()) {
  //            excursions.add(
  //                    new ExcursionDto(
  //                            resultSet.getInt(ID),
  //                            resultSet.getObject(BEGIN, LocalDateTime.class),
  //                            resultSet.getObject(END, LocalDateTime.class),
  //                            resultSet.getInt(WORKER_ID)));
  //        }
  //        return excursions;
  //    }

  public int findCountByPeriod(String firstDate, String secondDate) throws SQLException {
    int count = 0;
    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "select count(museum.excursion.id) "
                + "from museum.excursion where "
                + BEGIN
                + "  >= ? and "
                + END
                + " <= ?");

    preparedStatement.setDate(1, java.sql.Date.valueOf(firstDate));
    preparedStatement.setDate(2, java.sql.Date.valueOf(secondDate));
    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
      count = resultSet.getInt(1);
    }
    return count;
  }
}
