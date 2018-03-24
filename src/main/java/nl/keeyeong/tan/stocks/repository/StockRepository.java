package nl.keeyeong.tan.stocks.repository;

import nl.keeyeong.tan.stocks.model.entity.Stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kee-Yeong Tan
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
