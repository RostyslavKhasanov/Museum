package services;

import dtos.ExhibitDto;
import dtos.WorkerDto;
import exceptions.BadIdException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkerService {

  private Connection connection;

  private final String ID = "id";
  private final String AUTHOR_ID = "author_id";
  private final String HALL_ID = "hall_id";
  private final String NAME = "name";
  private final String MATERIAL = "material";
  private final String TECHNOLOGY = "technology";

  private final String id = "id";
  private final String position_id = "position_id";
  private final String excursion_id = "excursion_id";
  private final String fName = "fName";
  private final String sName = "sName";

  public WorkerService(Connection connection) {
    this.connection = connection;
  }

  public List<ExhibitDto> findExhibitByWorkerName(String name) throws SQLException {
    name = name.replaceAll("\"", "");
    String[] arr = name.split("_");
    String firstName = arr[0];
    String lastName = arr[1];
    System.out.println(arr[0]);
    System.out.println();
    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "select * from exhibit e join hall h on h.id = e.hall_id"
                + " where h.worker_id = (select id from worker where fName = ? and sName = ?)");
    preparedStatement.setString(1, firstName);
    preparedStatement.setString(2, lastName);

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

  public List<WorkerDto> findByWorkerPosition(int pos_id) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from worker where position_id = ?");
    preparedStatement.setInt(1, pos_id);

    ResultSet resultSet = preparedStatement.executeQuery();

    ArrayList<WorkerDto> workers = new ArrayList<>();
    while (resultSet.next()) {
      workers.add(
          new WorkerDto(
              resultSet.getInt(id),
              resultSet.getInt(position_id),
              resultSet.getInt(excursion_id),
              resultSet.getString(fName),
              resultSet.getString(sName)));
    }
    return workers;
  }

  public List<WorkerDto> findAll() throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement("select * from worker");

    ResultSet resultSet = preparedStatement.executeQuery();

    ArrayList<WorkerDto> workers = new ArrayList<>();
    while (resultSet.next()) {
      workers.add(
          new WorkerDto(
              resultSet.getInt(id),
              resultSet.getInt(position_id),
              resultSet.getInt(excursion_id),
              resultSet.getString(fName),
              resultSet.getString(sName)));
    }
    return workers;
  }

  public WorkerDto findById(int workerId) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from worker where id = ?");
    preparedStatement.setInt(1, workerId);

    ResultSet resultSet = preparedStatement.executeQuery();

    if (resultSet.next()) {
      return new WorkerDto(
          resultSet.getInt(id),
          resultSet.getInt(position_id),
          resultSet.getInt(excursion_id),
          resultSet.getString(fName),
          resultSet.getString(sName));
    } else throw new BadIdException("Worker with id" + workerId + "doesn't exist");
  }
}
