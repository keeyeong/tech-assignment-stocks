package nl.keeyeong.tan.stocks.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import nl.keeyeong.tan.stocks.model.entity.Stock;
import nl.keeyeong.tan.stocks.repository.StockRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * @author Kee-Yeong Tan
 */
@Service
@AllArgsConstructor
public class StockService {

	private final StockRepository stockRepository;

	public Stock create(final Stock input) {
		input.setLastUpdate(LocalDateTime.now());
		return stockRepository.save(input);
	}

	public Stock update(final Long id, final Stock input) {
		input.setLastUpdate(LocalDateTime.now());
		return stockRepository.save(input);
	}

	public List<Stock> read() {
		return stockRepository.findAll();
	}

	public Stock read(final Long id) {
		return stockRepository.findById(id)
			.orElseThrow(EntityNotFoundException::new);
	}

	public void delete(final Long id) {
		stockRepository.deleteById(id);
	}
}
