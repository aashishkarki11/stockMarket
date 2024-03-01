package hamro.stockmarket.stockmarket.Google.Exception;

import lombok.Getter;

/**
 * Exception class representing a resource error.
 * Author: [Aashish karki]
 */
@Getter
public class ErrorPerformingException extends RuntimeException {
    private final String reason;

    /**
     * Constructs a new ErrorPerformingException with the specified reason.
     *
     * @param reason The reason for the not found error.
     */
    public ErrorPerformingException(String reason) {
        super(reason);
        this.reason = reason;
    }
}