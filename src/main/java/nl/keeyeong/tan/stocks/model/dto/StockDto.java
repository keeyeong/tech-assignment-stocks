package nl.keeyeong.tan.stocks.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kee-Yeong Tan
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StockDto {
	private Long id;
	private String name;
	private BigDecimal currentPrice;
	private LocalDateTime lastUpdate;
}
