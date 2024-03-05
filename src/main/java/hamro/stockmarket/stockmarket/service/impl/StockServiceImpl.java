package hamro.stockmarket.stockmarket.service.impl;

import hamro.stockmarket.stockmarket.dto.StockDto;
import hamro.stockmarket.stockmarket.entity.Stock;
import hamro.stockmarket.stockmarket.repository.StockRecordRepo;
import hamro.stockmarket.stockmarket.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the StockService interface providing methods to manage stocks.
 * Author: [Aashish Karki]
 */
@Service
public class StockServiceImpl implements StockService {
  private final StockRecordRepo stockRecordRepo;

  /**
   * Constructs a StockServiceImpl object with the specified StockRecordRepo.
   *
   * @param stockRecordRepo The repository for managing stock records.
   */
  public StockServiceImpl(StockRecordRepo stockRecordRepo) {
    this.stockRecordRepo = stockRecordRepo;
  }

  /**
   * Creates a new stock record based on the provided StockDto.
   *
   * @param stockDto The data transfer object containing information about the stock.
   */
  @Override
  @Transactional
  public void createStock(StockDto stockDto) {
    Stock stock = new Stock();
    stock.setStockDetails(stockDto.getStockDetails());
    stock.setCreatedAt(stockDto.getLocalDateTime());
    stockRecordRepo.save(stock);
  }

  @Override
  public Stock getLastData() {
   return stockRecordRepo.findLatestStock();
  }
}
