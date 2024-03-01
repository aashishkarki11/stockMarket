package hamro.stockmarket.stockmarket.Google.Exception;

import lombok.Getter;

/**
 * Exception class representing a resource not found error.
 * Author: [Aashish karki]
 */
@Getter
public class NotFoundException extends RuntimeException {
    private final String reason;

    /**
     * Constructs a new NotFoundException with the specified reason.
     *
     * @param reason The reason for the not found error.
     */
    public NotFoundException(String reason) {
        super(reason);
        this.reason = reason;
    }
}