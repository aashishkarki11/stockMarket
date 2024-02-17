package hamro.stockmarket.stockmarket.excpetion;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String reason;

    public NotFoundException(String reason) {
        super(reason);
        this.reason = reason;
    }
}