package hamro.stockmarket.stockmarket.service.impl;

import hamro.stockmarket.stockmarket.Telegram.service.SendMessageService;
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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class responsible for retrieving New Ipo List and sending Message On Telegram.
 * Author: [Aashish karki]
 */
@Service
public class IpoNewsService {
  private static final Logger log = LoggerFactory.getLogger(IpoNewsService.class);
  private final SendMessageService sendMessageService;

  private final StockService stockService;

  public IpoNewsService(SendMessageService sendMessageService,
      StockService stockService) {
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
  public Map<String, Map<String, String>> getLiveMarketData() {
    String apiUrl = "https://merolagani.com/LatestMarket.aspx";

    try {
      Document document = Jsoup.connect(apiUrl).get();
      Element table = document.selectFirst(
          "div#ctl00_ContentPlaceHolder1_LiveTrading table.table-hover");

      if (table != null) {
        Elements rows = table.select("tbody tr");

        Map<String, Map<String, String>> stockDataMap = new HashMap<>();

        for (Element row : rows) {
          Elements cells = row.select("td");
          if (cells.isEmpty())
            continue;

          String symbol = cells.get(0).select("a").text();
          String cell1 = cells.get(1).text();
          String cell2 = cells.get(2).text();
          String cell3 = cells.get(3).text();
          String cell4 = cells.get(4).text();
          String cell5 = cells.get(5).text();
          String cell6 = cells.get(6).text();

          if (!symbol.isEmpty()) {
            Map<String, String> rowData = new HashMap<>();
            rowData.put("Symbol", symbol);
            rowData.put("LTP", cell1);
            rowData.put("% Change", cell2);
            rowData.put("Open", cell5);
            rowData.put("High", cell3);
            rowData.put("Low", cell4);
            rowData.put("Qty.", cell6);

            stockDataMap.put(symbol, rowData);
          }
        }

        log.info("stockData : {}", stockDataMap);

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