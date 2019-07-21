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

/**
 * Class for serve Worker.
 *
 * @author Rostyslav Khasanov
 * @version 1.0
 */
public class WorkerService {

  private Connection connection;

  private ExcursionService excursionService;
  private HallService hallService;

  private final String id = "id";
  private final String positionId = "position_id";
  private final String firstName = "fName";
  private final String lastName = "sName";

  public WorkerService(
      Connection connection, ExcursionService excursionService, HallService hallService) {
    this.connection = connection;
    this.excursionService = excursionService;
    this.hallService = hallService;
  }

  /**
   * Method for find workers by their position
   *
   * @return List of WorkerDto
   * @exception SQLException - error in sql query
   */
  public List<WorkerDto> findByWorkerPosition(int posId) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from worker where position_id = ?");
    preparedStatement.setInt(1, posId);

    ResultSet resultSet = preparedStatement.executeQuery();

    return getWorkers(resultSet);
  }

  /**
   * Method for find all workers in DB
   *
   * @return List of WorkerDto
   * @exception SQLException - error in sql query
   */
  public List<WorkerDto> findAll() throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement("select * from worker");

    ResultSet resultSet = preparedStatement.executeQuery();

    return getWorkers(resultSet);
  }

  /**
   * Method for find worker by id
   *
   * @return WorkerDto
   * @exception SQLException - error in sql query
   */
  public WorkerDto findById(int workerId) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from worker where id = ?");
    preparedStatement.setInt(1, workerId);

    ResultSet resultSet = preparedStatement.executeQuery();

    return getWorker(resultSet);
  }

  /**
   * Method for find post name of worker
   *
   * @return String (post name)
   * @exception SQLException - error in sql query
   */
  public String findWorkerPost(int workerId) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "SELECT * FROM post p join worker w on w.position_id = p.id where w.id = ?;");
    preparedStatement.setInt(1, workerId);

    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
      return resultSet.getString("name");
    } else {
      return "";
    }
  }

  /**
   * Method for find worker id by name
   *
   * @return Integer (worker id)
   * @exception SQLException - error in sql query
   */
  public int findWorkerId(String name) throws SQLException {
    name = name.replaceAll("\"", "");
    String[] arr = name.split(" ");
    String firstName = arr[0];
    String lastName = arr[1];
    PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM worker where fName = ? and sName = ?");
    preparedStatement.setString(1, firstName);
    preparedStatement.setString(2, lastName);

    ResultSet resultSet = preparedStatement.executeQuery();
    return getWorker(resultSet).getId();
  }

  /**
   * Method for find all free guides in this time
   *
   * @return List of WorkerDto
   * @exception SQLException - error in sql query
   */
  public List<WorkerDto> findAllFreeGid() throws SQLException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    //    LocalDateTime dateTime = LocalDateTime.now();
    //    String formattedDateTime = dateTime.format(formatter);
    String formattedDateTime = "2019-07-14 11:00";
    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "select * from  worker w join excursion e on e.worker_id = w.id where e.begin > ?"
                + "and e.end > ? group by w.fName");
    preparedStatement.setString(1, formattedDateTime);
    preparedStatement.setString(2, formattedDateTime);
    ResultSet resultSet = preparedStatement.executeQuery();
    return getWorkers(resultSet);
  }

  /**
   * Method for find count of excursion a certain worker
   *
   * @return Integer (count of excursion)
   * @exception SQLException - error in sql query
   */
  public Integer findCountOfExcursion(int workerId) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "select count(*) as c from excursion where worker_id = ? group by worker_id");
    preparedStatement.setInt(1, workerId);
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
      return resultSet.getInt("c");
    } else {
      return 0;
    }
  }

  /**
   * Method for find count of hours that a particular worker has
   *
   * @return Integer (count of hours)
   * @exception SQLException - error in sql query
   */
  public Integer findCountOfHour(int workerId) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement(
            "select sum(hour(timediff(e.begin, e.end))) as s from excursion e "
                + "join worker w on w.id = e.worker_id where e.worker_id = ? group by e.worker_id;");
    preparedStatement.setInt(1, workerId);
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
      return resultSet.getInt("S");
    } else {
      return 0;
    }
  }

  /**
   * Method for get list of WorkerDto by the result set. Almost this method for fix duplicate code
   *
   * @return List of WorkerDto
   * @exception SQLException - error in sql query
   */
  private List<WorkerDto> getWorkers(ResultSet resultSet) throws SQLException {
    ArrayList<WorkerDto> workers = new ArrayList<>();
    while (resultSet.next()) {
      workers.add(
          new WorkerDto(
              resultSet.getInt(id),
              resultSet.getInt(positionId),
              resultSet.getString(firstName),
              resultSet.getString(lastName),
              hallService.findByWorkerId(resultSet.getInt(id)),
              excursionService.findByWorkerId(resultSet.getInt(id)),
              findCountOfExcursion(resultSet.getInt(id)),
              findCountOfHour(resultSet.getInt(id)),
              findWorkerPost(resultSet.getInt(id))));
    }
    return workers;
  }

  /**
   * Method for get WorkerDto by the result set.
   * Almost this method for fix duplicate code
   *
   * @return WorkerDto
   * @exception SQLException - error in sql query
   */
  private WorkerDto getWorker(ResultSet resultSet) throws SQLException {
    if (resultSet.next()) {
      return new WorkerDto(
          resultSet.getInt(id),
          resultSet.getInt(positionId),
          resultSet.getString(firstName),
          resultSet.getString(lastName),
          hallService.findByWorkerId(resultSet.getInt(id)),
          excursionService.findByWorkerId(resultSet.getInt(id)),
          findCountOfExcursion(resultSet.getInt(id)),
          findCountOfHour(resultSet.getInt(id)),
          findWorkerPost(resultSet.getInt(id)));
    } else {
      throw new BadIdException("Worker with entered id doesn't exist");
    }
  }
}
