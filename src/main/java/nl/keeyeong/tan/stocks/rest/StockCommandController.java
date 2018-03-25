package nl.keeyeong.tan.stocks.rest;

import javax.validation.ValidationException;

import nl.keeyeong.tan.stocks.model.dto.StockDto;
import nl.keeyeong.tan.stocks.service.StockService;
import nl.keeyeong.tan.stocks.transformer.StockDtoTransformer;

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
	private final StockDtoTransformer transformer;

	@PostMapping
	public ResponseEntity<StockDto> create(@RequestBody final StockDto stock) {
		return ResponseEntity.ok(transformer.entityToDto(service.create(transformer.dtoToEntity(stock))));
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<StockDto> update(@PathVariable final Long id, @RequestBody final StockDto stock) {
		if (!id.equals(stock.getId())) {
			throw new ValidationException("Invalid identifier");
		}
		return ResponseEntity.ok(transformer.entityToDto(service.update(transformer.dtoToEntity(stock))));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity delete(@PathVariable("id") final Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
