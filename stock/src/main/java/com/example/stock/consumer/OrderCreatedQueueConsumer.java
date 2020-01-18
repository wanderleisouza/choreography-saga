package com.example.stock.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.order.domain.Order;
import com.example.stock.service.StockService;

@Component
public class OrderCreatedQueueConsumer {

	@Autowired
	StockService stockService;

	@RabbitListener(queues = StockService.ORDER_CREATED_QUEUE_NAME)
	public void receive(@Payload Order order) {
		stockService.decrementStockUsingOrder(order);
	}

}