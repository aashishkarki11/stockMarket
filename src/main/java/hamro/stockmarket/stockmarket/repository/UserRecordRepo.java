package hamro.stockmarket.stockmarket.repository;

import hamro.stockmarket.stockmarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRecordRepo extends JpaRepository<User, Long> {
  User findByEmail(String email);
}
