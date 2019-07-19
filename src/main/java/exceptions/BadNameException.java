package exceptions;

public class BadNameException extends RuntimeException {
  public BadNameException(String message) {
    super(message);
  }
}
