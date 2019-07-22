package exceptions;

/**
 * Exception when you call with bad id to DB.
 *
 * @author Nazar Stasyk
 * @version 1.0
 */
public class BadIdException extends RuntimeException {
  public BadIdException(String message) {
    super(message);
  }
}
