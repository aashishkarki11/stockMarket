package hamro.stockmarket.stockmarket.service.impl;

import hamro.stockmarket.stockmarket.Telegram.service.SendMessageService;
import hamro.stockmarket.stockmarket.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private final SendMessageService sendMessageService;
  @Value("${telegram.symbol}")
  private String stockSymbol;
  private static final Logger log = LoggerFactory.getLogger(SendMessageService.class);
  private final IpoNewsService ipoNewsService;

  public StockScheduleService(SendMessageService sendMessageService,
      IpoNewsService ipoNewsService) {
    this.sendMessageService = sendMessageService;
    this.ipoNewsService = ipoNewsService;
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
  @Scheduled(initialDelay = 10000000, fixedDelay = 10000000)
  public void schedulerStockData() throws IOException {
    String scrapedData = MeroLaganiScrapperService.getStockQuote(stockSymbol);
    sendMessageService.sendStockDetail(scrapedData);
  }

  /**
   * Scheduler to run the getIpoNews method at a one-day interval.
   */
  @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
  public void runIpoResultScheduler() {
    try {
      ipoNewsService.getIpoNews();
    } catch (IOException e) {
      log.error("IOException : {}", e.getMessage());
    }
  }

  /**
   * Scheduled method to run every Sunday to Thursday from 11 am to 3 pm with a delay of
   * 10 minutes.
   * <p>
   * This method is annotated with {@code @Scheduled} and uses a cron expression to
   * specify the schedule.
   * </p>
   */
  @Scheduled(cron = "0 0/10 11-15 * * SUN-THU", zone = "Asia/Kathmandu")
  public void getStockDataLive() {
    try {
      ipoNewsService.getLiveMarketData();
    } catch (Exception e) {
      throw new NotFoundException("Error getting Live Market Data" + e.getMessage());
    }
  }
}
