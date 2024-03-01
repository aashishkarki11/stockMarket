package hamro.stockmarket.stockmarket.Google.Exception;

/**
 * Custom exception class for token generation errors during Google OAuth authentication.
 * Author: [Aashish karki]
 */
public class TokenGenerationException extends RuntimeException {

  /**
   * Constructs a new TokenGenerationException with the specified detail message.
   *
   * @param message the detail message
   */
  public TokenGenerationException(String message) {
    super(message);
  }

  /**
   * Constructs a new TokenGenerationException with the specified detail message and
   * cause.
   *
   * @param message the detail message
   * @param cause   the cause
   */
  public TokenGenerationException(String message, Throwable cause) {
    super(message, cause);
  }
}
