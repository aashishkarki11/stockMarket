package hamro.stockmarket.stockmarket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Service class to call telegram.
 * Author: [Aashish karki]
 */
@Service
public class TelegramService {
  private static final Logger log = LoggerFactory.getLogger(TelegramService.class);

  @Value("${telegram.apiKey}")
  private String TELEGRAM_BOT_TOKEN;

  @Value("${telegram.chatId}")
  private String TELEGRAM_CHAT_ID;

  /**
   * Sends stock details to specific channel bot.
   *
   * @param data Stock details to be sent.
   * @throws IOException if an I/O error occurs while sending the message.
   */
  public void sendStockDetail(String data) throws IOException {
    String urlString = String.format("https://api.telegram.org/bot%s/sendMessage",
        TELEGRAM_BOT_TOKEN);
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);

    String text = "Stock Details:\n" + data;
    String postData = String.format("chat_id=%s&text=%s", TELEGRAM_CHAT_ID, text);

    log.info("post Data : {}", postData);
    byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);

    try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
      wr.write(postDataBytes);
    }

    try (BufferedReader in = new BufferedReader(
        new InputStreamReader(connection.getInputStream()))) {
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = in.readLine()) != null) {
        response.append(line);
      }
      log.info("Response : {}", response);
    }
  }
}