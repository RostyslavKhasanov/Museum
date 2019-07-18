package services;

import dto.ExhibitDto;
import exceptions.BadIdException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExhibitService {

  private Connection connection;

  private final String ID = "id";
  private final String AUTHOR_ID = "author_id";
  private final String HALL_ID = "hall_id";
  private final String NAME = "name";
  private final String MATERIAL = "material";
  private final String TECHNOLOGY = "technology";

  public ExhibitService(Connection connection) {
    this.connection = connection;
  }

  public List<ExhibitDto> findAll() throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from museum.exhibit");

    ResultSet resultSet = preparedStatement.executeQuery();

    ArrayList<ExhibitDto> exhibits = new ArrayList<>();
    while (resultSet.next()) {
      exhibits.add(
          new ExhibitDto(
              resultSet.getInt(ID),
              resultSet.getInt(AUTHOR_ID),
              resultSet.getInt(HALL_ID),
              resultSet.getString(NAME),
              resultSet.getString(MATERIAL),
              resultSet.getString(TECHNOLOGY)));
    }
    return exhibits;
  }

  public ExhibitDto findById(Integer id) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from museum.exhibit where " + ID + " = ?");
    preparedStatement.setInt(1, id);

    ResultSet resultSet = preparedStatement.executeQuery();

    if (resultSet.next()) {
      return new ExhibitDto(
          resultSet.getInt(ID),
          resultSet.getInt(AUTHOR_ID),
          resultSet.getInt(HALL_ID),
          resultSet.getString(NAME),
          resultSet.getString(MATERIAL),
          resultSet.getString(TECHNOLOGY));
    } else throw new BadIdException("In DB no row with id " + id);
  }

  public ExhibitDto findByHall(Integer hall_id) throws SQLException {
    PreparedStatement preparedStatement =
            connection.prepareStatement("select * from museum.exhibit inner join museum.hall " +
                    "on exhibit.hall_id = hall.id where" + HALL_ID + " = ?");
    preparedStatement.setInt(3, hall_id);

    ResultSet resultSet = preparedStatement.executeQuery();

    if(resultSet.next()) {
      return new ExhibitDto(
              resultSet.getInt(ID),
              resultSet.getInt(AUTHOR_ID),
              resultSet.getInt(HALL_ID),
              resultSet.getString(NAME),
              resultSet.getString(MATERIAL),
              resultSet.getString(TECHNOLOGY));
    } else throw new BadIdException("In DB no row with id " + hall_id);
  }
}
