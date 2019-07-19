package services;

import dtos.ExcursionDto;
import exceptions.BadIdException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

    ResultSet resultSet = preparedStatement.executeQuery();

    if (resultSet.next()) {
      return new ExcursionDto(
          resultSet.getInt(ID),
          resultSet.getObject(BEGIN, LocalDateTime.class),
          resultSet.getObject(END, LocalDateTime.class),
          resultSet.getInt(WORKER_ID));
    } else throw new BadIdException("In excursion no row with id " + id);
  }
}
