package nl.keeyeong.tan.stocks.rest;

import nl.keeyeong.tan.stocks.model.entity.Stock;
import nl.keeyeong.tan.stocks.repository.StockRepository;
import nl.keeyeong.tan.stocks.service.StockService;

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

	private final StockService service;

	@PostMapping
	public ResponseEntity<Stock> create(@RequestBody final Stock stock) {
		return ResponseEntity.ok(service.create(stock));
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<Stock> update(@PathVariable final Long id, @RequestBody final Stock stock) {
		return ResponseEntity.ok(service.update(id, stock));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity delete(@PathVariable("id") final Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
