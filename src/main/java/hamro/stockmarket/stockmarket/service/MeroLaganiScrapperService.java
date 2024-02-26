package hamro.stockmarket.stockmarket.service;

import hamro.stockmarket.stockmarket.exception.NotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

/**
 * Service class responsible for retrieving stock market details.
 * Author: [Aashish karki]
 */
@Service
public class MeroLaganiScrapperService {
  private static final Logger log = LoggerFactory.getLogger(
      MeroLaganiScrapperService.class);

  /**
   * Retrieves details of a stock based on the provided symbol from mero lagani.
   *
   * @param symbol The symbol of the stock to fetch details for.
   * @return A string containing various details of the stock.
   * @throws NotFoundException If the stock symbol is not found or an error occurs during
   *                           the process.
   */
  @Retryable(interceptor = "retryData", retryFor = IOException.class)
  public static String getStockQuote(String symbol) {
    try {
      String apiUrl = String.format("https://merolagani.com/CompanyDetail.aspx?symbol=%s",
          symbol);
      Document document = Jsoup.connect(apiUrl).get();

      String title = document.title();
      log.info("Webpage Title: {} ", title);

      Element sectorElement = document.select("th:contains(Sector) + td").first();
      String sector = Objects.requireNonNull(sectorElement).text();
      log.info("Sector: {}", sector);

      Element changeElement = document.select("th:contains(% Change) + td span").first();
      String change = Objects.requireNonNull(changeElement).text();
      log.info("percentage Change: {}", change);

      Element weeksHighLowElement = document.select(
          "th:contains(52 Weeks High - Low) + td").first();
      String weeksHighLow = Objects.requireNonNull(weeksHighLowElement).text();
      log.info("52 Weeks High - Low: {}", weeksHighLow);

      Element avg120DayElement = document.select("th:contains(120 Day Average) + td")
          .first();
      String avg120Day = Objects.requireNonNull(avg120DayElement).text();
      log.info("120 Day Average: {}", avg120Day);

      Element peRatioElement = document.select("th:contains(P/E Ratio) + td").first();
      String peRatio = Objects.requireNonNull(peRatioElement).text();
      log.info("P/E Ratio: {}", peRatio);

      Element bookValueElement = document.select("th:contains(Book Value) + td").first();
      String bookValue = Objects.requireNonNull(bookValueElement).text();
      log.info("Book Value: {}", bookValue);

      Element pbvElement = document.select("th:contains(PBV) + td").first();
      String pbv = Objects.requireNonNull(pbvElement).text();
      log.info("PBV: {}", pbv);

      Element marketCapElement = document.select(
          "th:contains(Market Capitalization) + td").first();
      String marketCap = Objects.requireNonNull(marketCapElement).text();
      log.info("Market Capitalization: {}", marketCap);

      String result = """
          Stock Details:
          
          Sector: %s
          Percentage Change: %s
          52 Weeks High - Low: %s
          120 Day Average: %s
          P/E Ratio: %s
          Book Value: %s
          PBV: %s
          Market Capitalization: %s
          """.formatted(sector, change, weeksHighLow, avg120Day, peRatio, bookValue, pbv,
          marketCap);

      if (sector.isEmpty()) {
        result = "Wrong symbol: " + symbol;
      }

      log.info("result: {} ", result);

      return result;
    } catch (IOException e) {
      throw new NotFoundException("Stock symbol not found" + e.getMessage());
    }
  }
}
