package hamro.stockmarket.stockmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) representing stock information.
 * This class encapsulates information about a stock, including its unique identifier,
 * UUID, stock details, and API key.
 * Author: [Aashish Karki]
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {
  private long id;
  private UUID uuid;
  private String stockDetails;
  private UUID apiKey;
}
