package hamro.stockmarket.stockmarket.repository;

import hamro.stockmarket.stockmarket.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRecordRepo extends JpaRepository<Stock, Long> {
}
