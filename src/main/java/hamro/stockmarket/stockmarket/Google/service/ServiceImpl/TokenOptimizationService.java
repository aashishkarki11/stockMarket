package hamro.stockmarket.stockmarket.Google.service.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hamro.stockmarket.stockmarket.Google.Exception.NotFoundException;
import hamro.stockmarket.stockmarket.Google.dto.UserDetailsDto;
import hamro.stockmarket.stockmarket.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.StringTokenizer;

/**
 * Service class for decoding the ID token received from Google OAuth.
 * Author: [Aashish karki]
 */
@Service
public class TokenOptimizationService {

  private static final ObjectMapper objectMapper = new ObjectMapper();


  /**
   * Decodes the ID token and extracts the payload data.
   *
   * @param idToken The ID token received from Google OAuth.
   */
  public UserDetailsDto decodeIdToken(String idToken) {
    if (ValidationUtil.checkIsNullAndEmpty(idToken)) {
      throw new NotFoundException("ID token is null");
    }
    StringTokenizer tokenizer = new StringTokenizer(idToken, ".");
    tokenizer.nextToken();

    String payload = tokenizer.nextToken();

    byte[] payloadBytes = Base64.getUrlDecoder().decode(payload);
    String payloadData = new String(payloadBytes);

   return convertToUser(payloadData);
  }

  /**
   * Converts the JSON payload to a UserDetails object.
   *
   * @param payload The JSON payload representing user details.
   * @return UserDetails object representing the JSON data.
   * @throws NotFoundException if there is an error while converting the payload to
   *                           UserDetails.
   */
  public UserDetailsDto convertToUser(String payload) {
    try {
      return objectMapper.readValue(payload, UserDetailsDto.class);
    } catch (JsonProcessingException e) {
      throw new NotFoundException(
          "Error while converting payload to UserDetails" + e.getMessage());
    }
  }
}
