package hamro.stockmarket.stockmarket.service.impl;

import hamro.stockmarket.stockmarket.dto.StockDto;
import hamro.stockmarket.stockmarket.entity.Item;
import hamro.stockmarket.stockmarket.entity.Stock;
import hamro.stockmarket.stockmarket.repository.StockRecordRepo;
import hamro.stockmarket.stockmarket.service.StockService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

  /**
   * Retrieves an item from the cache or database by its ID. If the item is found in the
   * cache, it is returned directly. If not found in the cache, it is retrieved from the
   * database, stored in the cache, and then returned.
   *
   * @param itemId The ID of the item to retrieve.
   * @return The item corresponding to the given ID.
   */
  @Cacheable(value = "itemCache", key = "#itemId")
  public Item getItemById(String itemId) {
    Optional<Stock> stock = stockRecordRepo.findById(Long.valueOf(itemId));
    return new Item(itemId, String.valueOf(stock));
  }

  /**
   * Retrieves the latest stock data from the repository.
   *
   * @return The latest stock data.
   */
  @Override
  public Stock getLastData() {
    return stockRecordRepo.findLatestStock();
  }
}
