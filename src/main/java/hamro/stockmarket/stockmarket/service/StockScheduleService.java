package hamro.stockmarket.stockmarket.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Service class responsible for scheduling periodic retrieval of stock data and sending
 * it to a designated Telegram service.
 * <p>
 * This class is annotated with {@code @Component} to be automatically detected and
 * registered as a Spring bean. It utilizes Spring's scheduling capabilities to
 * periodically fetch stock data using a provided symbol and sends it via a Telegram
 * service.
 * </p>
 * Author: [Aashish karki]
 */
@Component
public class StockScheduleService {
  private final OneWayTelegramService oneWayTelegramService;

  @Value("${telegram.symbol}")
  private String stockSymbol;

  public StockScheduleService(OneWayTelegramService oneWayTelegramService) {
    this.oneWayTelegramService = oneWayTelegramService;
  }

  /**
   * Scheduled method to fetch stock data periodically and send it to Telegram.
   * <p>
   * This method is annotated with {@code @Scheduled} to execute periodically with an
   * initial delay and fixed delay. It retrieves stock data for the configured stock
   * symbol and sends it to the specified Telegram service.
   * </p>
   *
   * @throws IOException if an I/O exception occurs during data retrieval
   */
  @Scheduled(initialDelay = 10000, fixedDelay = 10000)
  public void schedulerStockData() throws IOException {
    String scrapedData = MeroLaganiScrapperService.getStockQuote(stockSymbol);
    oneWayTelegramService.sendStockDetail(scrapedData);
  }
}
