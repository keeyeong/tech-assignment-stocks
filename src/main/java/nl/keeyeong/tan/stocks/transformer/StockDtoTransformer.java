package nl.keeyeong.tan.stocks.transformer;

import nl.keeyeong.tan.stocks.model.dto.StockDto;
import nl.keeyeong.tan.stocks.model.entity.Stock;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

/**
 * @author Kee-Yeong Tan
 */
@Component
@AllArgsConstructor
public class StockDtoTransformer {

	public StockDto entityToDto(final Stock entity) {
		return StockDto.builder()
			.id(entity.getId())
			.name(entity.getName())
			.currentPrice(entity.getCurrentPrice())
			.lastUpdate(entity.getLastUpdate())
			.build();
	}

	public Stock dtoToEntity(final StockDto dto) {
		return Stock.builder()
			.id(dto.getId())
			.name(dto.getName())
			.currentPrice(dto.getCurrentPrice())
			.lastUpdate(dto.getLastUpdate())
			.build();
	}
}
