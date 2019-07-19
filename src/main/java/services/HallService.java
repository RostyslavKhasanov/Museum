package services;

import dtos.HallDto;
import exceptions.BadIdException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HallService {
  private Connection connection;

  private final String ID = "id";
  private final String WORKER_ID = "worker_id";
  private final String NAME = "name";
  private ExhibitService exhibitService;

  public HallService(Connection connection, ExhibitService exhibitService) {
    this.connection = connection;
    this.exhibitService = exhibitService;
  }

  public List<HallDto> findAll() throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement("select * from museum.hall");

    ResultSet resultSet = preparedStatement.executeQuery();

    List<HallDto> halls = new ArrayList<>();
    while (resultSet.next()) {
      halls.add(
          new HallDto(
              resultSet.getInt(ID),
              resultSet.getInt(WORKER_ID),
              resultSet.getString(NAME),
              exhibitService.findByHallId(resultSet.getInt(ID))));
    }
    return halls;
  }

  public HallDto findById(Integer id) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from museum.hall where " + ID + " = ?");
    preparedStatement.setInt(1, id);

    ResultSet resultSet = preparedStatement.executeQuery();

    if (resultSet.next()) {
      return new HallDto(
          resultSet.getInt(ID),
          resultSet.getInt(WORKER_ID),
          resultSet.getString(NAME),
          exhibitService.findByHallId(resultSet.getInt(ID)));
    } else throw new BadIdException("In hall no row with id " + id);
  }
}
