package hamro.stockmarket.stockmarket.controller;

import hamro.stockmarket.stockmarket.Telegram.service.SendMessageService;
import hamro.stockmarket.stockmarket.entity.Stock;
import hamro.stockmarket.stockmarket.exception.NotFoundException;
import hamro.stockmarket.stockmarket.service.StockService;
import hamro.stockmarket.stockmarket.service.impl.MeroLaganiScrapperService;
import hamro.stockmarket.stockmarket.service.impl.StockScheduleService;
import hamro.stockmarket.stockmarket.util.ValidationUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Controller class responsible for handling requests related to stock market details.
 * This class provides endpoints for retrieving stock market data and sending it via
 * Telegram. Author: [Aashish karki]
 */
@Controller
public class DetailsController {
  private final SendMessageService sendMessageService;
  private final StockScheduleService stockScheduleService;
  private final StockService stockService;

  /**
   * Constructs a new DetailsController with the specified services.
   *
   * @param sendMessageService   The service for sending messages via Telegram.
   * @param stockScheduleService
   * @param stockService
   */
  public DetailsController(SendMessageService sendMessageService,
      StockScheduleService stockScheduleService, StockService stockService) {
    this.sendMessageService = sendMessageService;
    this.stockScheduleService = stockScheduleService;
    this.stockService = stockService;
  }

  /**
   * Retrieves stock market data for the provided symbol and sends it via Telegram.
   *
   * @param symbol The stock symbol for which data is requested.
   * @throws IOException If an error occurs during data retrieval or message sending.
   */
  @GetMapping("/data")
  public void getData(@RequestParam(value = "symbol") String symbol) throws IOException {
    if (ValidationUtil.checkIsNullAndEmpty(symbol)) {
      throw new NotFoundException("symbol was not found");
    }
    String scrapedData = MeroLaganiScrapperService.getStockQuote(symbol);
    sendMessageService.sendStockDetail(scrapedData);
  }

  @GetMapping("/liveData")
  public String getLiveData(Model model) {
    try {
      model.addAttribute("data", stockScheduleService.getStockDataLive());
      return "stock/live";
    } catch (Exception e) {
      throw new RuntimeException("hey error aayo");
    }
  }
}
