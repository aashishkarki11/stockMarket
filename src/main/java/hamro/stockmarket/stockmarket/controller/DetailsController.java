package hamro.stockmarket.stockmarket.controller;

import hamro.stockmarket.stockmarket.exception.NotFoundException;
import hamro.stockmarket.stockmarket.service.MeroLaganiScrapperService;
import hamro.stockmarket.stockmarket.Telegram.service.SendMessageService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Controller class responsible for handling requests related to stock market details.
 * This class provides endpoints for retrieving stock market data and sending it via
 * Telegram.
 * Author: [Aashish karki]
 */
@RestController
public class DetailsController {
  private final MeroLaganiScrapperService meroLaganiScrapperService;
  private final SendMessageService sendMessageService;

  /**
   * Constructs a new DetailsController with the specified services.
   *
   * @param meroLaganiScrapperService The service for retrieving stock market details.
   * @param sendMessageService     The service for sending messages via Telegram.
   */
  public DetailsController(MeroLaganiScrapperService meroLaganiScrapperService,
      SendMessageService sendMessageService) {
    this.meroLaganiScrapperService = meroLaganiScrapperService;
    this.sendMessageService = sendMessageService;
  }

  /**
   * Retrieves stock market data for the provided symbol and sends it via Telegram.
   *
   * @param symbol The stock symbol for which data is requested.
   * @throws IOException If an error occurs during data retrieval or message sending.
   */
  @GetMapping("/data")
  public void getData(@RequestParam(value = "symbol") String symbol) throws IOException {
    if (checkNullAndEmpty(symbol)) {
      throw new NotFoundException("symbol was not found");
    }
    String scrapedData = meroLaganiScrapperService.getStockQuote(symbol);
    sendMessageService.sendStockDetail(scrapedData);
  }

  /**
   * Checks if the provided value is null or empty.
   *
   * @param value The value to be checked.
   * @return True if the value is null or empty, otherwise false.
   */
  Boolean checkNullAndEmpty(String value) {
    return value == null || value.isEmpty();
  }
}
