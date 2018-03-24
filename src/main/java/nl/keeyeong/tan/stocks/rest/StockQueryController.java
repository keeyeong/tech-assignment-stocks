package nl.keeyeong.tan.stocks.rest;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import nl.keeyeong.tan.stocks.model.entity.Stock;
import nl.keeyeong.tan.stocks.repository.StockRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * @author Kee-Yeong Tan
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/stocks")
public class StockQueryController {

	private final StockRepository stockRepository;

	@GetMapping
	public List<Stock> read() {
		return stockRepository.findAll();
	}

	@GetMapping("/{id}")
	public Stock read(@PathVariable final Long id) {
		return stockRepository.findById(id)
			.orElseThrow(EntityNotFoundException::new);
	}
}
