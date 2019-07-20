package services;

import dto.WorkerDto;
import exceptions.BadIdException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WorkerService {

  private Connection connection;

  private ExcursionService excursionService;
  private HallService hallService;

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

  public WorkerService(
      Connection connection, ExcursionService excursionService, HallService hallService) {
    this.connection = connection;
    this.excursionService = excursionService;
    this.hallService = hallService;
  }



  public List<WorkerDto> findByWorkerPosition(int pos_id) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from worker where position_id = ?");
    preparedStatement.setInt(1, pos_id);

    ResultSet resultSet = preparedStatement.executeQuery();

    return getWorkers(resultSet);
  }

  public List<WorkerDto> findAll() throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement("select * from worker");

    ResultSet resultSet = preparedStatement.executeQuery();

    return getWorkers(resultSet);
  }

  public WorkerDto findById(int workerId) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from worker where id = ?");
    preparedStatement.setInt(1, workerId);

    ResultSet resultSet = preparedStatement.executeQuery();

    return getWorker(resultSet);
  }

  public WorkerDto findWorkerPost(int workerId) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "SELECT * FROM post p join worker w on w.position_id = p.id "
                + "where w.id ="
                + workerId
                + ";");
    preparedStatement.setInt(1, workerId);

    ResultSet resultSet = preparedStatement.executeQuery();

    return getWorker(resultSet);
  }

  public int findWorkerId(String name) throws SQLException {
    name = name.replaceAll("\"", "");
    String[] arr = name.split(" ");
    String firstName = arr[0];
    String lastName = arr[1];
    PreparedStatement preparedStatement =
            connection.prepareStatement(
                    "SELECT * FROM worker where fName = ? and sName = ?");
    preparedStatement.setString(1, firstName);
    preparedStatement.setString(2, lastName);

    ResultSet resultSet = preparedStatement.executeQuery();
    return getWorker(resultSet).getId();
  }

  public List<WorkerDto> findAllFreeGid() throws SQLException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//    LocalDateTime dateTime = LocalDateTime.now();
//    String formattedDateTime = dateTime.format(formatter);
    String formattedDateTime = "2019-07-14 11:00";
    PreparedStatement preparedStatement =
            connection.prepareStatement(
                    "select * from  worker w join excursion e on e.worker_id = w.id where e.begin > ?"
                            + "and e.end > ? group by w.fName");
    preparedStatement.setString(1,formattedDateTime);
    preparedStatement.setString(2,formattedDateTime);
    ResultSet resultSet = preparedStatement.executeQuery();
    return getWorkers(resultSet);
  }

  private List<WorkerDto> getWorkers(ResultSet resultSet) throws SQLException {
    ArrayList<WorkerDto> workers = new ArrayList<>();
    while (resultSet.next()) {
      workers.add(
          new WorkerDto(
              resultSet.getInt(id),
              resultSet.getInt(position_id),
              resultSet.getString(fName),
              resultSet.getString(sName),
              hallService.findByWorkerId(resultSet.getInt(id)),
              excursionService.findByWorkerId(resultSet.getInt(id)),
              findCountOfExcursion(resultSet.getInt(id)),
              findCountOfHour(resultSet.getInt(id))));
    }
    return workers;
  }

  public Integer findCountOfHour(int worker_id) throws SQLException {
    PreparedStatement preparedStatement =
            connection.prepareStatement(
                    "select sum(hour(timediff(e.begin, e.end))) as s from excursion e " +
                            "join worker w on w.id = e.worker_id where e.worker_id = ? group by e.worker_id;");
    preparedStatement.setInt(1,worker_id);
    ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
          return resultSet.getInt("S");
      } else return 0;
  }

  public Integer findCountOfExcursion(int worker_id) throws SQLException {
    PreparedStatement preparedStatement =
            connection.prepareStatement(
                    "select count(*) as c from excursion where worker_id = ? group by worker_id");
    preparedStatement.setInt(1,worker_id);
    ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
          return resultSet.getInt("c");
      } else return 0;
  }

  private WorkerDto getWorker(ResultSet resultSet) throws SQLException {
    if (resultSet.next()) {
      return new WorkerDto(
              resultSet.getInt(id),
              resultSet.getInt(position_id),
              resultSet.getString(fName),
              resultSet.getString(sName),
              hallService.findByWorkerId(resultSet.getInt(id)),
              excursionService.findByWorkerId(resultSet.getInt(id)),
              findCountOfExcursion(resultSet.getInt(id)),
              findCountOfHour(resultSet.getInt(id)));
    } else throw new BadIdException("Worker with entered id doesn't exist");
  }
}
