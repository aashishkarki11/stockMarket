package hamro.stockmarket.stockmarket.Telegram.config;

import hamro.stockmarket.stockmarket.Telegram.service.PollingTelegramService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Configuration class for setting up a Telegram chat service.
 * This class initializes and registers a Telegram bot for handling chat interactions.
 * Author: [Aashish karki]
 */
@Service
public class TelegramChatConfig {

  /**
  /**
   * Constructor for the TelegramChatConfig class.
   * Initializes and registers a Telegram bot for handling chat interactions.
   * @throws TelegramApiException If an error occurs during the Telegram bot registration process.
   */
  public TelegramChatConfig() throws TelegramApiException {
    PollingTelegramService pollingTelegramService = new PollingTelegramService();
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    telegramBotsApi.registerBot(pollingTelegramService);
  }
}
