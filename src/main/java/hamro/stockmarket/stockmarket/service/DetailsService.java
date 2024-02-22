package hamro.stockmarket.stockmarket.service;

import hamro.stockmarket.stockmarket.excpetion.NotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;


/**
 * Service class responsible for retrieving stock market details.
 * Author: [Aashish karki]
 */
@Service
public class DetailsService {
  private static final Logger log = LoggerFactory.getLogger(TelegramService.class);

  /**
   * Retrieves details of a stock based on the provided symbol from mero lagani.
   *
   * @param symbol The symbol of the stock to fetch details for.
   * @return A string containing various details of the stock.
   * @throws NotFoundException If the stock symbol is not found or an error occurs during the process.
   */
  public String getStockQuote(String symbol) {
    try {
      String apiUrl = String.format("http://merolagani.com/CompanyDetail.aspx?symbol=%s",
          symbol);
      Document document = Jsoup.connect(apiUrl).get();

      String title = document.title();
      System.out.println("Webpage Title: " + title);

      Element sectorElement = document.select("th:contains(Sector) + td").first();
      String sector = Objects.requireNonNull(sectorElement).text();
      System.out.println("Sector: " + sector);

      Element changeElement = document.select("th:contains(% Change) + td span").first();
      String change = Objects.requireNonNull(changeElement).text();
      System.out.println("% Change: " + change);

      Element weeksHighLowElement = document.select(
          "th:contains(52 Weeks High - Low) + td").first();
      String weeksHighLow = Objects.requireNonNull(weeksHighLowElement).text();
      System.out.println("52 Weeks High - Low: " + weeksHighLow);

      Element avg120DayElement = document.select("th:contains(120 Day Average) + td")
          .first();
      String avg120Day = Objects.requireNonNull(avg120DayElement).text();
      System.out.println("120 Day Average: " + avg120Day);

      Element peRatioElement = document.select("th:contains(P/E Ratio) + td").first();
      String peRatio = Objects.requireNonNull(peRatioElement).text();
      System.out.println("P/E Ratio: " + peRatio);

      Element bookValueElement = document.select("th:contains(Book Value) + td").first();
      String bookValue = Objects.requireNonNull(bookValueElement).text();
      System.out.println("Book Value: " + bookValue);

      Element pbvElement = document.select("th:contains(PBV) + td").first();
      String pbv = Objects.requireNonNull(pbvElement).text();
      System.out.println("PBV: " + pbv);

      Element marketCapElement = document.select(
          "th:contains(Market Capitalization) + td").first();
      String marketCap = Objects.requireNonNull(marketCapElement).text();
      System.out.println("Market Capitalization: " + marketCap);

      String result = "Sector: " + sector + "\n" + "percentage Change: " + change + "\n"
          + "52 Weeks High - Low: " + weeksHighLow + "\n" + "120 Day Average: "
          + avg120Day + "\n" + "P/E Ratio: " + peRatio + "\n" + "Book Value: " + bookValue
          + "\n" + "PBV: " + pbv + "\n" + "Market Capitalization: " + marketCap;

      log.info("result: {} ", result);
      return result;

    } catch (IOException e) {
      throw new NotFoundException("Stock symbol not found" + e.getMessage());
    }
  }
}
