package com.example.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.stock.domain.Stock;
import com.example.stock.domain.StockHistory;
import com.example.stock.service.StockHistoryService;
import com.example.stock.service.StockService;

@RestController
public class StockController {

	@Autowired
	StockService stockService;

	@Autowired
	StockHistoryService stockHistoryService;
	
	@GetMapping("/stock/{id}")
	public Stock findById(@PathVariable final String id) {
		return stockService.findById(id);
	}

	@RequestMapping(path = "/stock", method = { RequestMethod.PUT, RequestMethod.POST })
	public Stock saveStock(@RequestBody final StockHistory stockHistory) {
		return stockHistoryService.saveStockHistory(stockHistory);
	}

}
