package nl.keeyeong.tan.stocks;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import nl.keeyeong.tan.stocks.model.entity.Stock;

import org.assertj.core.data.Percentage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Kee-Yeong Tan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StocksApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

	@LocalServerPort
	private int port;

	private String url;

	private final TestRestTemplate restTemplate = new TestRestTemplate();

	@Before
	public void setup() {
		url = String.format("http://localhost:%d", port);
	}

	@Test
	public void stocksRestCrudTest() {

		// Check number of rows before we start
		Stock[] results = restTemplate.getForObject(getStockURL(), Stock[].class);

		final int initialSize = results.length;

		// Create new row
		final Stock newStock = restTemplate.postForObject(getStockURL(),
			Stock.builder()
				.name("MAS")
				.currentPrice(BigDecimal.ONE)
				.build(),
			Stock.class);

		assertThat(newStock.getName()).isEqualTo("MAS");
		assertThat(newStock.getCurrentPrice()).isCloseTo(BigDecimal.ONE, Percentage.withPercentage(0d)); // force numeric comparison

		results = restTemplate.getForObject(getStockURL(), Stock[].class);
		assertThat(results.length).isEqualTo(initialSize + 1); // one more row than before returned

		// Check the new row
		Stock result = restTemplate.getForObject(getStockURL(newStock.getId()), Stock.class);
		assertThat(result.getName()).isEqualTo("MAS");
		assertThat(result.getCurrentPrice()).isCloseTo(BigDecimal.ONE, Percentage.withPercentage(0d));

		final LocalDateTime insertionTimestamp = result.getLastUpdate();

		// Update the new row
		restTemplate.put(getStockURL(newStock.getId()),
			Stock.builder()
				.id(newStock.getId())
				.name("SIA")
				.currentPrice(BigDecimal.TEN)
				.build());

		results = restTemplate.getForObject(getStockURL(), Stock[].class);
		assertThat(results.length).isEqualTo(initialSize + 1); // number of rows remains the same

		// Check the updated row
		result = restTemplate.getForObject(getStockURL(newStock.getId()), Stock.class);
		assertThat(result.getName()).isEqualTo("SIA");
		assertThat(result.getCurrentPrice()).isCloseTo(BigDecimal.TEN, Percentage.withPercentage(0d));
		assertThat(result.getLastUpdate()).isAfter(insertionTimestamp);

		// Delete the row
		restTemplate.delete(getStockURL(newStock.getId()));

		results = restTemplate.getForObject(getStockURL(), Stock[].class);
		assertThat(results.length).isEqualTo(initialSize); // number of rows remains as before the creation

		final Optional<Stock> deletedRow = Arrays.stream(results).filter(r->r.getId().equals(newStock.getId())).findAny();
		assertThat(deletedRow).isEmpty();
	}

	private String getStockURL() {
		return getStockURL(null);
	}

	private String getStockURL(final Long id) {
		if (id != null) {
			return url + "/api/stocks/" + id;
		} else {
			return url + "/api/stocks";
		}
	}
}