package hamro.stockmarket.stockmarket.Telegram.exception;

import lombok.Getter;

/**
 * Exception class representing a resource not found error.
 * Author: [Aashish karki]
 */
@Getter
public class DataNotFoundException extends RuntimeException {
    private final String reason;

    /**
     * Constructs a new NotFoundException with the specified reason.
     *
     * @param reason The reason for the not found error.
     */
    public DataNotFoundException(String reason) {
        super(reason);
        this.reason = reason;
    }
}