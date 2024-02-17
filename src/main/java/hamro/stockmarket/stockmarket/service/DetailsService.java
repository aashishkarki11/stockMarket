package hamro.stockmarket.stockmarket.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DetailsService {
    public String getStockQuote(String symbol) {
        try {
            String apiUrl = String.format("http://merolagani.com/CompanyDetail.aspx?symbol=%s", symbol);
            Document document = Jsoup.connect(apiUrl).get();

            String title = document.title();
            System.out.println("Webpage Title: " + title);

            Element sectorElement = document.select("th:contains(Sector) + td").first();
            String sector = sectorElement.text();
            System.out.println("Sector: " + sector);

            Element changeElement = document.select("th:contains(% Change) + td span").first();
            String change = changeElement.text();
            System.out.println("% Change: " + change);

            Element weeksHighLowElement = document.select("th:contains(52 Weeks High - Low) + td").first();
            String weeksHighLow = weeksHighLowElement.text();
            System.out.println("52 Weeks High - Low: " + weeksHighLow);

            Element avg120DayElement = document.select("th:contains(120 Day Average) + td").first();
            String avg120Day = avg120DayElement.text();
            System.out.println("120 Day Average: " + avg120Day);

            Element peRatioElement = document.select("th:contains(P/E Ratio) + td").first();
            String peRatio = peRatioElement.text();
            System.out.println("P/E Ratio: " + peRatio);

            Element bookValueElement = document.select("th:contains(Book Value) + td").first();
            String bookValue = bookValueElement.text();
            System.out.println("Book Value: " + bookValue);

            Element pbvElement = document.select("th:contains(PBV) + td").first();
            String pbv = pbvElement.text();
            System.out.println("PBV: " + pbv);

            Element marketCapElement = document.select("th:contains(Market Capitalization) + td").first();
            String marketCap = marketCapElement.text();
            System.out.println("Market Capitalization: " + marketCap);

            return "scraped data";
        } catch (IOException e) {
            e.printStackTrace();
            return "Scraping failed";
        }
    }
}
