package hamro.stockmarket.stockmarket.service;

import hamro.stockmarket.stockmarket.dto.StockDto;
import hamro.stockmarket.stockmarket.entity.Stock;

public interface StockService {
  void createStock(StockDto stockDto);
   Stock getLastData();

}
