package hamro.stockmarket.stockmarket.controller;

import hamro.stockmarket.stockmarket.Telegram.service.SendMessageService;
import hamro.stockmarket.stockmarket.entity.Item;
import hamro.stockmarket.stockmarket.exception.NotFoundException;
import hamro.stockmarket.stockmarket.service.impl.IpoNewsService;
import hamro.stockmarket.stockmarket.service.impl.MeroLaganiScrapperService;
import hamro.stockmarket.stockmarket.service.impl.StockServiceImpl;
import hamro.stockmarket.stockmarket.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Controller class responsible for handling requests related to stock market details.
 * This class provides endpoints for retrieving stock market data and sending it via
 * Telegram. Author: [Aashish karki]
 */
@Controller
@Slf4j
public class DetailsController {
  private final SendMessageService sendMessageService;
  private final IpoNewsService ipoNewsService;
  private final StockServiceImpl stockServiceImpl;

  /**
   * Constructs a new DetailsController with the specified services.
   *
   * @param sendMessageService The service for sending messages via Telegram.
   */
  public DetailsController(SendMessageService sendMessageService,
      IpoNewsService ipoNewsService, StockServiceImpl stockServiceImpl) {
    this.sendMessageService = sendMessageService;
    this.ipoNewsService = ipoNewsService;
    this.stockServiceImpl = stockServiceImpl;
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

  /**
   * Retrieves live market data and adds it to the model.
   *
   * @param model The model to add the live market data to.
   * @return The view name for displaying live market data.
   * @throws RuntimeException If an error occurs while fetching live market data.
   */
  @GetMapping("/liveData")
  public String getLiveData(Model model) {
    try {
      model.addAttribute("data", ipoNewsService.getLiveMarketData());
      return "stock/live";
    } catch (Exception e) {
      throw new RuntimeException("hey error aayo");
    }
  }

  /**
   * Retrieves an item (stock) by its ID.
   *
   * @param id The ID of the stock to retrieve.
   * @return The item corresponding to the given ID.
   */
  @GetMapping("/{id}")
  public Item getStock(@PathVariable String id) {
    log.info("inside the getStock controller");
    return stockServiceImpl.getItemById(id);
  }
}
