package nl.keeyeong.tan.stocks.rest;

import java.util.List;

import nl.keeyeong.tan.stocks.model.entity.Stock;
import nl.keeyeong.tan.stocks.service.StockService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	private final StockService service;

	@CrossOrigin
	@GetMapping
	public List<Stock> read() {
		return service.read();
	}

	@GetMapping("/{id}")
	public Stock read(@PathVariable final Long id) {
		return service.read(id);
	}
}
