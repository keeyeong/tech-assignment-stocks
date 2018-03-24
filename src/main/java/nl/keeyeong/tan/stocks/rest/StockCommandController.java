package nl.keeyeong.tan.stocks.rest;

import nl.keeyeong.tan.stocks.model.entity.Stock;
import nl.keeyeong.tan.stocks.repository.StockRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * @author Kee-Yeong Tan
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/stocks")
public class StockCommandController {

	private final StockRepository stockRepository;

	@PostMapping
	public ResponseEntity create(@RequestBody final Stock stock) {
		final Stock result = stockRepository.save(stock);
		return ResponseEntity.ok(result.getId());
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity update(@PathVariable final Long id, @RequestBody final Stock stock) {
		final Stock result = stockRepository.save(stock);
		return ResponseEntity.ok(result.getId());
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity delete(@PathVariable("id") Long id) {
		stockRepository.deleteById(id);
		return ResponseEntity.ok(id);
	}
}
