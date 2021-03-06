package com.example.stock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.stock.domain.Stock;
import com.example.stock.exceptions.OutOfStockException;
import com.example.stock.exceptions.StockExceptions;
import com.example.stock.repository.StockRepository;

@Service
public class StockService {

	Logger logger = LoggerFactory.getLogger(StockService.class);

	public static final String ORDER_CREATED_QUEUE_NAME = "orderCreated";
	public static final String STOCK_UPDATED_QUEUE_NAME = "stockUpdated";
	public static final String OUT_OF_STOCK_QUEUE_NAME = "outOfStock";

	@Autowired
	StockRepository stockRepository;

	public Stock findById(String id) {
		return stockRepository.findById(id).orElseThrow(StockExceptions::new);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Stock saveStock(Stock stock) {

		Stock currentStock = stockRepository.findById(stock.getSku()).orElse(new Stock());
		Integer updatedStockAmount = currentStock.getAmount() + stock.getAmount();
		if (updatedStockAmount < 0) {
			throw new OutOfStockException();
		} else {
			stock.setAmount(updatedStockAmount);
			logger.info("saving stock {}", stock);
			stock = stockRepository.save(stock);
		}
		return stock;
	}

}
