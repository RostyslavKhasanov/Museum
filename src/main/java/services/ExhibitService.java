package services;

import dto.ExhibitDto;
import dto.ExhibitMaterialDto;
import dto.ExhibitTechnologyDto;
import exceptions.BadIdException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for serve Exhibit.
 *
 * @author Nazar Stasyuk
 * @version 1.0
 */
public class ExhibitService {

  private Connection connection;

  private final String ID = "id";
  private final String AUTHOR_ID = "author_id";
  private final String HALL_ID = "hall_Id";
  private final String NAME = "name";
  private final String MATERIAL = "material";
  private final String TECHNOLOGY = "technology";

  public ExhibitService(Connection connection) {
    this.connection = connection;
  }

  /**
   * Method that find and return all Exhibit from DB.
   *
   * @return list of ExhibitDto.
   * @exception SQLException - error in sql query.
   */
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

  /**
   * Method that find Exhibit by id.
   *
   * @return ExhibitDto
   * @exception SQLException - error in sql query.
   */
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
    } else {
      throw new BadIdException("In DB no row with id " + id);
    }
  }
  /**
   * Method that find Exhibit by Hall.
   *
   * @return List of ExhibitDto
   * @exception SQLException - error in sql query.
   */
  public List<ExhibitDto> findByHallId(Integer id) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "select * from museum.exhibit inner join museum.hall "
                + "on exhibit.hall_id = hall.id where "
                + HALL_ID
                + " = ?");
    preparedStatement.setInt(1, id);

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

  /**
   * Method that find Exhibit materialName for statistic.
   *
   * @return List of ExhibitMaterialDto
   * @exception SQLException - error in sql query.
   */
  public List<ExhibitMaterialDto> findExhibitMaterialStatistic() throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "select material, count(material) from exhibit group by material");
    ResultSet resultSet = preparedStatement.executeQuery();
    List<ExhibitMaterialDto> exhibitMaterials = new ArrayList<>();
    while (resultSet.next()) {
      exhibitMaterials.add(
          new ExhibitMaterialDto(resultSet.getString(MATERIAL), resultSet.getInt(2)));
    }
    return exhibitMaterials;
  }

  /**
   * Method that find Exhibit materialName for statistic.
   *
   * @return List of ExhibitTechnologyDto
   * @exception SQLException - error in sql query.
   */
  public List<ExhibitTechnologyDto> findExhibitTechnologyStatistic() throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "select technology, count(technology) from exhibit group by technology");
    ResultSet resultSet = preparedStatement.executeQuery();
    List<ExhibitTechnologyDto> exhibitTechnologies = new ArrayList<>();
    while (resultSet.next()) {
      exhibitTechnologies.add(
          new ExhibitTechnologyDto(resultSet.getString(TECHNOLOGY), resultSet.getInt(2)));
    }
    return exhibitTechnologies;
  }
}
