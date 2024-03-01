package hamro.stockmarket.stockmarket.Google.dto;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing the response received from Google OAuth
 * authentication.
 * Author: [Aashish karki]
 */
@Getter
@Setter
@NoArgsConstructor
public class GoogleResponseDto {
  private String accessToken;
  private String idToken;
  private String scope;
  private Integer expireTime;
  private String tokenType;

  /**
   * Constructs a new {@code GoogleResponseDto} with the provided access token, ID token,
   * scope, expiration time, and token type.
   *
   * @param accessToken The access token obtained from Google OAuth.
   * @param idToken     The ID token obtained from Google OAuth.
   * @param scope       The scope of the access granted by the token.
   * @param expireTime  The expiration time of the access token in seconds.
   * @param tokenType   The type of token obtained (e.g., "Bearer").
   */
  public GoogleResponseDto(String accessToken, String idToken, String scope,
      Integer expireTime, String tokenType) {
    this.accessToken = accessToken;
    this.idToken = idToken;
    this.scope = scope;
    this.expireTime = expireTime;
    this.tokenType = tokenType;
  }
}
