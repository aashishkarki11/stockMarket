package hamro.stockmarket.stockmarket.Google.controller;

import hamro.stockmarket.stockmarket.Google.exception.ErrorPerformingException;
import hamro.stockmarket.stockmarket.Google.exception.TokenGenerationException;
import hamro.stockmarket.stockmarket.Google.dto.GoogleResponseDto;
import hamro.stockmarket.stockmarket.Google.service.GoogleAuthService;
import hamro.stockmarket.stockmarket.service.UserService;
import hamro.stockmarket.stockmarket.util.ValidationUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling Google OAuth2 callback requests. Author: [Aashish karki]
 */
@RestController
@RequestMapping("/oauth2/callback")
public class GoogleController {
  private final GoogleAuthService googleOAuthService;
  private final UserService userService;

  /**
   * Constructor injection of GoogleAuthService.
   *
   * @param googleOAuthService The service responsible for handling Google OAuth
   *                           operations.
   * @param userService
   */
  public GoogleController(GoogleAuthService googleOAuthService, UserService userService) {
    this.googleOAuthService = googleOAuthService;
    this.userService = userService;
  }

  /**
   * Handles the callback from Google OAuth2 flow.
   *
   * @param code The authorization code received from Google after user authentication.
   * @return A success message if the access token is successfully generated.
   * @throws IllegalArgumentException If the code parameter is null or empty.
   * @throws ErrorPerformingException If an error occurs while handling the callback.
   */
  @GetMapping
  public String handleCallback(
      @RequestParam(value = "code", required = false) String code) {
    try {
      if (ValidationUtil.checkIsNullAndEmpty(code)) {
        throw new IllegalArgumentException("Code parameter is null or empty");
      }

      GoogleResponseDto googleResponseDto = googleOAuthService.getAccessToken(code);
      String accessToken = googleResponseDto.getAccessToken();
      userService.createUser(googleResponseDto.getIdToken());
      if (ValidationUtil.checkIsNullAndEmpty(accessToken)) {
        throw new TokenGenerationException("Access token is null");
      }
      return "success";
    } catch (Exception e) {
      throw new ErrorPerformingException(
          "Error occurred while handling callback : {}" + e.getMessage());
    }
  }
}
