package hamro.stockmarket.stockmarket.Google.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents user details obtained from decoding the ID token received from Google
 * OAuth.
 * Author: [Aashish karki]
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsDto {

  private String iss;
  private String azp;
  private String aud;
  private String sub;
  private String email;

  @JsonProperty("email_verified")
  private Boolean emailVerified;

  @JsonProperty("at_hash")
  private String atHash;
  private String name;
  private String picture;

  @JsonProperty("given_name")
  private String givenName;

  @JsonProperty("family_name")
  private String familyName;
  private String locale;
  private Double iat;
  private Double exp;
}
