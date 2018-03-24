package nl.keeyeong.tan.stocks.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import nl.keeyeong.tan.stocks.model.entity.Stock;
import nl.keeyeong.tan.stocks.repository.StockRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {

	private StockService fixture;

	@Mock
	private StockRepository stockRepository;

	@Before
	public void setup() {
		fixture = new StockService(stockRepository);
	}

	@Test(expected = ValidationException.class)
	public void create_idProvided_exceptionThrown() {
		fixture.create(Stock.builder()
			.id(100L)
			.currentPrice(BigDecimal.TEN)
			.name("ABC")
			.lastUpdate(LocalDateTime.MIN)
			.build());
	}

	@Test
	public void create_dateProvided_dateOverwritten() {
		fixture.create(Stock.builder()
			.currentPrice(BigDecimal.TEN)
			.name("ABC")
			.lastUpdate(LocalDateTime.MIN)
			.build());

		final ArgumentCaptor<Stock> stockCaptor = ArgumentCaptor.forClass(Stock.class);
		verify(stockRepository, times(1)).save(stockCaptor.capture());

		final Stock targetRow = stockCaptor.getValue();
		assertThat(targetRow.getLastUpdate()).isAfter(LocalDateTime.MIN);
		assertThat(targetRow.getName()).isEqualTo("ABC");
		assertThat(targetRow.getCurrentPrice()).isEqualTo(BigDecimal.TEN);
	}

	@Test
	public void update_entityExists_dateOverwritten() {

		when(stockRepository.findById(anyLong()))
			.thenReturn(Optional.of(Stock.builder()
				.id(100L)
				.currentPrice(BigDecimal.ONE)
				.name("ABC")
				.lastUpdate(LocalDateTime.MIN)
				.build()));

		fixture.update(Stock.builder()
			.id(100L)
			.currentPrice(BigDecimal.ZERO)
			.name("DEF")
			.lastUpdate(LocalDateTime.MIN)
			.build());

		final ArgumentCaptor<Stock> stockCaptor = ArgumentCaptor.forClass(Stock.class);
		verify(stockRepository, times(1)).save(stockCaptor.capture());

		final Stock targetRow = stockCaptor.getValue();
		assertThat(targetRow.getLastUpdate()).isAfter(LocalDateTime.MIN);
		assertThat(targetRow.getName()).isEqualTo("DEF");
		assertThat(targetRow.getCurrentPrice()).isEqualTo(BigDecimal.ZERO);
	}

	@Test(expected = ValidationException.class)
	public void update_entityIdNull_exceptionThrown() {
		fixture.update(Stock.builder()
			.currentPrice(BigDecimal.TEN)
			.name("ABC")
			.lastUpdate(LocalDateTime.MIN)
			.build());
	}

	@Test(expected = EntityNotFoundException.class)
	public void update_entityDoesNotExist_exceptionThrown() {

		when(stockRepository.findById(anyLong())).thenReturn(Optional.empty());

		fixture.update(Stock.builder()
			.id(100L)
			.currentPrice(BigDecimal.TEN)
			.name("ABC")
			.lastUpdate(LocalDateTime.MIN)
			.build());
	}

	@Test
	public void read_single_found() {
		when(stockRepository.findById(anyLong()))
			.thenReturn(Optional.of(Stock.builder()
				.id(100L)
				.currentPrice(BigDecimal.ONE)
				.name("ABC")
				.lastUpdate(LocalDateTime.MIN)
				.build()));

		final Stock result = fixture.read(100L);
		assertThat(result.getId()).isEqualTo(100L);
		assertThat(result.getName()).isEqualTo("ABC");
	}

	@Test(expected = EntityNotFoundException.class)
	public void read_single_missing() {

		when(stockRepository.findById(anyLong()))
			.thenReturn(Optional.empty());

		fixture.read(100L);
	}

	@Test
	public void read_all() {

		when(stockRepository.findAll())
			.thenReturn(Collections.singletonList(Stock.builder()
				.id(100L)
				.currentPrice(BigDecimal.ONE)
				.name("ABC")
				.lastUpdate(LocalDateTime.MIN)
				.build()));

		final List<Stock> results = fixture.read();

		assertThat(results.size()).isEqualTo(1);
		assertThat(results.get(0).getId()).isEqualTo(100L);
	}

	@Test
	public void delete() {
		fixture.delete(100L);

		final ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
		verify(stockRepository, times(1)).deleteById(longCaptor.capture());

		final Long targetRowId = longCaptor.getValue();
		assertThat(targetRowId).isEqualTo(100L);
	}
}