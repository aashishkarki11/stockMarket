package hamro.stockmarket.stockmarket.Google.service;

import hamro.stockmarket.stockmarket.Google.dto.GoogleResponseDto;

public interface GoogleAuthService {
  GoogleResponseDto getAccessToken (String code);
}
