package services;

import dto.HallDto;
import exceptions.BadIdException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for serve Hall.
 *
 * @author Kateryna Horokh
 * @version 1.0
 */
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

  /**
   * Method that find and return all Halls from DB.
   *
   * @return list of HallDto.
   * @exception SQLException - error in sql query.
   */
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

  /**
   * Method that find Hall by id.
   *
   * @return HallDto
   * @exception SQLException - error in sql query.
   * @exception BadIdException - call with bad id to DB.
   */
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
