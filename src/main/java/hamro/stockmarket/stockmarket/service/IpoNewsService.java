package hamro.stockmarket.stockmarket.service;

import hamro.stockmarket.stockmarket.Telegram.service.SendMessageService;
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


/**
 * Service class responsible for retrieving New Ipo List and sending Message On Telegram.
 * Author: [Aashish karki]
 */
@Service
public class IpoNewsService {

  private static final Logger log = LoggerFactory.getLogger(IpoNewsService.class);
  private final SendMessageService sendMessageService;

  public IpoNewsService(SendMessageService sendMessageService) {
    this.sendMessageService = sendMessageService;
  }

  /**
   * Retrieves IPO news from the specified URL and sends a message if today's date matches the IPO date.
   * @throws IOException If there is an error connecting to the URL or parsing the document.
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
}
