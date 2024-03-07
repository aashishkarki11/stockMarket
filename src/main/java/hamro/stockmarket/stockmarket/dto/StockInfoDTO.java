package hamro.stockmarket.stockmarket.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) representing information about a stock. This class
 * encapsulates details such as symbol, last traded price, percent change, and quantity of
 * a stock. Author: [Aashish Karki]
 */
@Data
public class StockInfoDTO {
  private String symbol;
  private String lastTradedPrice;
  private String percentChange;
  private String quantity;

  /**
   * Constructs a new StockInfoDTO with the given details.
   *
   * @param symbol          The symbol of the stock.
   * @param lastTradedPrice The last traded price of the stock.
   * @param percentChange   The percent change in the stock's price.
   * @param quantity        The quantity of the stock.
   */
  public StockInfoDTO(String symbol, String lastTradedPrice, String percentChange,
      String quantity) {
    this.symbol = symbol;
    this.lastTradedPrice = lastTradedPrice;
    this.percentChange = percentChange;
    this.quantity = quantity;
  }

  /**
   * Overrides the toString method to provide a custom string representation of the
   * object.
   *
   * @return A string representation of the StockInfoDTO object.
   */
  @Override
  public String toString() {
    return String.format("%s | %s | %s | %s", padRight(symbol, 8),
        padRight(lastTradedPrice, 10), padRight(percentChange, 8),
        padRight(quantity, 10));
  }

  private String padRight(String s, int n) {
    return String.format("%-" + n + "s", s);
  }
}