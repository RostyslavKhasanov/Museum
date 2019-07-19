package services;

import dto.ExcursionDto;
import exceptions.BadIdException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcursionService {

  private Connection connection;

  private final String ID = "id";
  private final String BEGIN = "begin";
  private final String END = "end";

  public ExcursionService(Connection connection) {
    this.connection = connection;
  }

  public List<ExcursionDto> findAll() throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from museum.excursion");

    ResultSet resultSet = preparedStatement.executeQuery();

    ArrayList<ExcursionDto> excursions = new ArrayList<>();
    while (resultSet.next()) {
      excursions.add(
          new ExcursionDto(resultSet.getInt(ID), resultSet.getTimestamp(BEGIN), resultSet.getTimestamp(END)));
    }
    return excursions;
  }

  public ExcursionDto findById(Integer id) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from museum.excursion" + " where" + ID + " = ?");
    preparedStatement.setInt(1, id);

    ResultSet resultSet = preparedStatement.executeQuery();

    if (resultSet.next()) {
      return new ExcursionDto(
          resultSet.getInt(ID), resultSet.getDate(BEGIN), resultSet.getDate(END));
    } else throw new BadIdException("In DB no row with id " + id);
  }

    public List<ExcursionDto> findByWorkerId(int id) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from excursion where worker_id= ?");
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<ExcursionDto> excursions = new ArrayList<>();
        return getExcursions(resultSet, excursions);
    }

    private List<ExcursionDto> getExcursions(ResultSet resultSet, ArrayList excursions) throws SQLException {
        while (resultSet.next()) {
            excursions.add(
                    new ExcursionDto(resultSet.getInt(ID), resultSet.getTimestamp(BEGIN), resultSet.getTimestamp(END)));
        }
        return excursions;
    }
}
