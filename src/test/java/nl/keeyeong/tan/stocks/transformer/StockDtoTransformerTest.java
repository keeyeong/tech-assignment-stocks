package nl.keeyeong.tan.stocks.transformer;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import nl.keeyeong.tan.stocks.model.dto.StockDto;
import nl.keeyeong.tan.stocks.model.entity.Stock;

import org.junit.Before;
import org.junit.Test;

public class StockDtoTransformerTest {

	private StockDtoTransformer fixture;

	@Before
	public void setup() {
		fixture = new StockDtoTransformer();
	}

	@Test
	public void entityToDto() {
		final LocalDateTime thisMomentInTime = LocalDateTime.now();

		final StockDto result = fixture.entityToDto(Stock.builder()
			.id(100L)
			.name("ABC")
			.currentPrice(BigDecimal.TEN)
			.lastUpdate(thisMomentInTime)
			.build());

		assertThat(result.getId()).isEqualTo(100L);
		assertThat(result.getName()).isEqualTo("ABC");
		assertThat(result.getCurrentPrice()).isEqualTo(BigDecimal.TEN);
		assertThat(result.getLastUpdate()).isEqualTo(thisMomentInTime);
	}

	@Test
	public void dtoToEntity() {
		final LocalDateTime thisMomentInTime = LocalDateTime.now();

		final Stock result = fixture.dtoToEntity(StockDto.builder()
			.id(100L)
			.name("ABC")
			.currentPrice(BigDecimal.TEN)
			.lastUpdate(thisMomentInTime)
			.build());

		assertThat(result.getId()).isEqualTo(100L);
		assertThat(result.getName()).isEqualTo("ABC");
		assertThat(result.getCurrentPrice()).isEqualTo(BigDecimal.TEN);
	}
}