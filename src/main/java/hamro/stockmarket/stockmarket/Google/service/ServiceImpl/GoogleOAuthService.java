package hamro.stockmarket.stockmarket.Google.service.ServiceImpl;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import hamro.stockmarket.stockmarket.Google.exception.TokenGenerationException;
import hamro.stockmarket.stockmarket.Google.dto.GoogleResponseDto;
import hamro.stockmarket.stockmarket.Google.service.GoogleAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Service class for handling Google OAuth authentication. Author: [Aashish karki]
 */
@Service
public class GoogleOAuthService implements GoogleAuthService {
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

  @Value("${google.clientId}")
  private String clientId;

  @Value("${google.clientSecret}")
  private String clientSecret;

  @Value("${google.scopes}")
  private String[] scopesArray;

  @Value("${google.redirectUrl}")
  private String redirectUrl;

  /**
   * Retrieves the access token and other relevant information using the authorization
   * code.
   *
   * @param code The authorization code obtained after the user authentication.
   * @return A {@link GoogleResponseDto} containing the access token, ID token, scope,
   * expiration duration in seconds, and token type.
   * @throws TokenGenerationException while accessToken is not generated.
   */
  public GoogleResponseDto getAccessToken(String code) {
    try {
      List<String> scopes = List.of(scopesArray);

      GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
          GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientId,
          clientSecret, scopes).setAccessType("offline").setApprovalPrompt("force")
          .build();

      GoogleTokenResponse response = flow.newTokenRequest(code)
          .setRedirectUri(redirectUrl).execute();

      return new GoogleResponseDto(response.getAccessToken(), response.getIdToken(),
          response.getScope(), Math.toIntExact(response.getExpiresInSeconds()),
          response.getTokenType());
    } catch (IOException | GeneralSecurityException e) {
      throw new TokenGenerationException("Error generating token : {}", e);
    }
  }
}
