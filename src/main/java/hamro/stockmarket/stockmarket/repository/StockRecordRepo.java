package hamro.stockmarket.stockmarket.repository;

import hamro.stockmarket.stockmarket.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRecordRepo extends JpaRepository<Stock, Long> {
  @Query("SELECT s FROM Stock s WHERE s.createdAt = (SELECT MAX(s.createdAt) FROM Stock s)")
  Stock findLatestStock();
}
