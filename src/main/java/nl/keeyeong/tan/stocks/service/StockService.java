package nl.keeyeong.tan.stocks.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

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
		if (input.getId() != null) {
			throw new ValidationException("Identifier should be null for creation");
		}
		input.setLastUpdate(LocalDateTime.now());
		return stockRepository.save(input);
	}

	public Stock update(final Stock input) {
		if (input.getId() == null) {
			throw new ValidationException("Identifier should not be null for update");
		}
		if (stockRepository.findById(input.getId()).isPresent()) {
			input.setLastUpdate(LocalDateTime.now());
			return stockRepository.save(input);
		} else {
			throw new EntityNotFoundException();
		}
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
