package exceptions;

/**
 * Exception when you call with bad name to DB
 *
 * @author Rostyslav Khasanov
 * @version 1.0
 */
public class BadNameException extends RuntimeException {
  public BadNameException(String message) {
    super(message);
  }
}
