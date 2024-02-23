package hamro.stockmarket.stockmarket.service;

import hamro.stockmarket.stockmarket.excpetion.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Service class representing a Telegram bot using long polling for message updates. This
 * bot responds to incoming messages with stock quotes obtained from
 * MeroLaganiScrapperService.
 * Author: [Aashish karki]
 */
@Component
public class PollingTelegramService extends TelegramLongPollingBot {
  private static final Logger log = LoggerFactory.getLogger(
      MeroLaganiScrapperService.class);

  /**
   * Constructor for PollingTelegramService class. Initializes the Telegram bot with the
   * provided bot token.
   */
  public PollingTelegramService() {
    super("XYZ");
  }

  /**
   * Retrieves the username of the Telegram bot.
   *
   * @return The username of the Telegram bot.
   */
  @Override
  public String getBotUsername() {
    return "XYZ";
  }

  /**
   * Callback method invoked when an update is received from Telegram. Processes the
   * received update and sends a response containing a stock quote.
   *
   * @param update The received update from Telegram.
   */
  @Override
  public void onUpdateReceived(Update update) {
    SendMessage sendMessage = new SendMessage();
    String command = update.getMessage().getText();
    sendMessage.setChatId(update.getMessage().getChatId());
    String response = MeroLaganiScrapperService.getStockQuote(command);
    log.info("Response : {}", response);
    try {
      sendMessage.setText(response);
      execute(sendMessage);
    } catch (Exception e) {
      log.error("Error sending message : {} ", e.getMessage());
      throw new NotFoundException("Message was not found: " + e.getMessage());
    }
  }
}