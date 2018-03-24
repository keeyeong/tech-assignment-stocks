package nl.keeyeong.tan.stocks.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Kee-Yeong Tan
 */
@Entity
@Data
@Table(name = "stock")
public class Stock {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private BigDecimal currentPrice;

	private LocalDateTime lastUpdate;
}
