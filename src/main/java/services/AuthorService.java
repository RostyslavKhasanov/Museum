package services;

import dto.AuthorDto;
import exceptions.BadIdException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for serve Author.
 *
 * @author Nazar Stasyuk
 * @version 1.0
 */
public class AuthorService {

  private Connection connection;

  private String id = "id";
  private String firstName = "fName";
  private String secondName = "sName";

  public AuthorService(Connection connection) {
    this.connection = connection;
  }

  /**
   * Method that find Author by id.
   *
   * @return AuthorDto
   * @exception SQLException - error in sql query.
   */
  public AuthorDto findById(Integer id) throws SQLException {
    PreparedStatement preparedStatement =
        connection.prepareStatement("select * from author where " + this.id + " = ?");
    preparedStatement.setInt(1, id);
    ResultSet resultSet = preparedStatement.executeQuery();

    if (resultSet.next()) {
      return new AuthorDto(
          resultSet.getInt(this.id),
          resultSet.getString(firstName),
          resultSet.getString(secondName));
    } else {
      throw new BadIdException("Bad author id " + id);
    }
  }

  /**
   * Method that find and return all Authors from DB.
   *
   * @return list of AuthorsDto.
   * @exception SQLException - error in sql query.
   */
  public List<AuthorDto> findAll() throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement("select * from author");
    ResultSet resultSet = preparedStatement.executeQuery();

    List<AuthorDto> authors = new ArrayList<>();

    while (resultSet.next()) {
      authors.add(
          new AuthorDto(
              resultSet.getInt(id),
              resultSet.getString(firstName),
              resultSet.getString(secondName)));
    }
    return authors;
  }
}
