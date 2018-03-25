package nl.keeyeong.tan.stocks.rest;

import java.util.List;
import java.util.stream.Collectors;

import nl.keeyeong.tan.stocks.model.dto.StockDto;
import nl.keeyeong.tan.stocks.service.StockService;
import nl.keeyeong.tan.stocks.transformer.StockDtoTransformer;

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
	private final StockDtoTransformer transformer;

	@CrossOrigin
	@GetMapping
	public List<StockDto> read() {
		return service.read().stream().map(transformer::entityToDto).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public StockDto read(@PathVariable final Long id) {
		return transformer.entityToDto(service.read(id));
	}
}
