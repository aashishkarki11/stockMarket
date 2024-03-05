package hamro.stockmarket.stockmarket.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hamro.stockmarket.stockmarket.Telegram.service.SendMessageService;
import hamro.stockmarket.stockmarket.dto.StockDto;
import hamro.stockmarket.stockmarket.service.StockService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Service class responsible for retrieving New Ipo List and sending Message On Telegram.
 * Author: [Aashish karki]
 */
@Service
public class IpoNewsService {
  private static final Logger log = LoggerFactory.getLogger(IpoNewsService.class);
  private final SendMessageService sendMessageService;
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private  final StockService stockService;

  public IpoNewsService(SendMessageService sendMessageService, StockService stockService) {
    this.sendMessageService = sendMessageService;
    this.stockService = stockService;
  }

  /**
   * Retrieves IPO news from the specified URL and sends a message if today's date matches
   * the IPO date.
   *
   * @throws IOException If there is an error connecting to the URL or parsing the
   *                     document.
   */
  @Retryable(interceptor = "retryData", retryFor = IOException.class)
  public void getIpoNews() throws IOException {
    String apiUrl = "https://www.sharesansar.com/category/ipo-fpo-news";
    Document document = Jsoup.connect(apiUrl).get();
    Elements ipoEntries = document.select(".featured-news-list");
    log.info("ipoEntries: " + ipoEntries);
    for (Element entry : ipoEntries) {
      String ipoName = entry.select("h4.featured-news-title").text().trim();
      String date = entry.select("span.text-org").text().trim();
      log.info("ipoName: " + ipoName + " date: " + date);
      LocalDate todayDate = LocalDate.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
      String formattedDate = todayDate.format(formatter);
      String result = """
          Ipo Alert:
                    
          CompanyName: %s
          Date: %s
          """.formatted(ipoName, formattedDate);
      log.info("result: " + result);
      if (formattedDate.equals(date)) {
        sendMessageService.sendStockDetail(result);
      }
    }
  }

  /**
   * Retrieves live market data from the specified URL. The market data is scraped from
   * the HTML table on the webpage.
   *
   * @return A map containing stock symbols as keys and their corresponding data as inner
   * maps. The inner maps contain column headers as keys and respective data values as
   * values. If the data cannot be retrieved, an empty map is returned.
   */
  public  Map<String, Map<String, String>> getLiveMarketData() {
    StockDto stockDto = new StockDto();
    String apiUrl = "https://merolagani.com/LatestMarket.aspx";
    try {
      Document document = Jsoup.connect(apiUrl).get();
      Element table = document.selectFirst(
          "div#ctl00_ContentPlaceHolder1_LiveTrading table.table-hover");
      if (table != null) {
        Element tableHeader = table.selectFirst("thead");
        Element tableBody = table.selectFirst("tbody");
        List<String> headers = new ArrayList<>();
        if (tableHeader != null) {
          for (Element th : tableHeader.select("th")) {
            String headerText = th.text();
            if (!headerText.isEmpty()) {
              headers.add(headerText);
            }
          }
        }
        Map<String, Map<String, String>> stockDataMap = new HashMap<>();
        if (tableBody != null) {
          for (Element row : tableBody.select("tr")) {
            Map<String, String> rowData = new HashMap<>();
            String symbol = null;
            int index = 0;
            for (Element cell : row.select("td")) {
              if (index == 0) {
                symbol = cell.text();
              } else {
                rowData.put(headers.get(index), cell.text());
              }
              index++;
            }
            if (symbol != null) {
              stockDataMap.put(symbol, rowData);
            }
          }
        }
        log.info("stockData : {}", stockDataMap);
        String liveData = objectMapper.writeValueAsString(stockDataMap);
        stockDto.setStockDetails(liveData);
        stockDto.setLocalDateTime(LocalDateTime.now());
        stockService.createStock(stockDto);

        return stockDataMap;
      } else {
        log.error("Table not found on the page.");
      }
    } catch (IOException e) {
      log.error("Error fetching data from the URL: " + e.getMessage());
    }
    log.info("Empty Data sorry");
    return new HashMap<>();
  }
}