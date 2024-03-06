package hamro.stockmarket.stockmarket.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hamro.stockmarket.stockmarket.Telegram.service.SendMessageService;
import hamro.stockmarket.stockmarket.dto.StockDto;
import hamro.stockmarket.stockmarket.exception.NotFoundException;
import hamro.stockmarket.stockmarket.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

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
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final Logger log = LoggerFactory.getLogger(SendMessageService.class);
  private final IpoNewsService ipoNewsService;
  private final StockService stockService;

  public StockScheduleService(SendMessageService sendMessageService,
      IpoNewsService ipoNewsService, StockService stockService) {
    this.sendMessageService = sendMessageService;
    this.ipoNewsService = ipoNewsService;
    this.stockService = stockService;
  }

  /**
   * Scheduled method to fetch high and low data periodically and send it to Telegram.
   * <p>
   * This method is annotated with {@code @Scheduled} to execute periodically with an
   * initial delay and fixed delay. It retrieves stock data for the configured stock
   * symbol and sends it to the specified Telegram service.
   * </p>
   *
   * @throws IOException if an I/O exception occurs during data retrieval
   */
  @Scheduled(cron = "0 0 15 ? * SUN-THU", zone = "Asia/Kathmandu")
  public void topGainerAndLoserData() throws IOException {
    String scrapedData = IpoNewsService.getTopGainersAndLosers();
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
      StockDto stockDto = new StockDto();
      Map<String, Map<String, String>> stockDataMap = ipoNewsService.getLiveMarketData();
      String liveData = objectMapper.writeValueAsString(stockDataMap);
      log.info("liveData : {}", liveData);

      stockDto.setStockDetails(liveData);
      stockDto.setLocalDateTime(LocalDateTime.now());
      stockService.createStock(stockDto);
    } catch (Exception e) {
      throw new NotFoundException("Error getting Live Market Data" + e.getMessage());
    }
  }
}
