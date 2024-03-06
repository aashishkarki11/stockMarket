package hamro.stockmarket.stockmarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration class for WebSocket setup in the application. This class enables
 * WebSocket support and configures message broker and STOMP endpoints. Author: [Aashish
 * Karki]
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  /**
   * Configures the message broker for WebSocket communication.
   *
   * @param config The message broker registry to configure.
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic", "/message");
    config.setApplicationDestinationPrefixes("/app");
  }

  /**
   * Registers STOMP endpoints for WebSocket communication.
   *
   * @param registry The STOMP endpoint registry to register endpoints.
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws").withSockJS();
  }
}
