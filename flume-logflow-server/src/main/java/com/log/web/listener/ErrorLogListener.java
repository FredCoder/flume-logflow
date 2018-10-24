package com.log.web.listener;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import com.log.web.LogsWebSocket;
import com.log.web.conf.AmqpConfig;
import com.log.web.conf.DataFormatConfig;
import com.log.web.entity.LogFlow;

@Configuration

public class ErrorLogListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorLogListener.class);

	/**
	 * 设置交换机类型
	 * 
	 * DirectExchange:按照routingkey分发到指定队列 
	 * TopicExchange:多关键字匹配 
	 * FanoutExchange:
	 * 将消息分发到所有的绑定队列，无routingkey的概念 
	 * HeadersExchange ：通过添加属性key-value匹配
	 */
	@Bean
	public DirectExchange defaultExchange() {
		return new DirectExchange(AmqpConfig.FLUME_EXCHANGE);
	}

	@Bean
	public Queue flumeQueue() {
		return new Queue(AmqpConfig.FLUME_QUEUE);
	}

	/** 将队列绑定到交换机 */
	@Bean
	public Binding binding() {
		return BindingBuilder.bind(flumeQueue()).to(defaultExchange()).with(AmqpConfig.FLUME_ROUTINGKEY);
	}

	@RabbitListener(queues = AmqpConfig.FLUME_QUEUE)
	@RabbitHandler
	public void process(@Payload byte[] message) {
		String log = new String(message);
		LOGGER.info("Listener: " + log);
		com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
		mapper.setDateFormat(new DataFormatConfig(mapper.getDateFormat()));
		try {
			LogFlow lf = mapper.readValue(log, LogFlow.class);
			LogsWebSocket.sendInfo(mapper.writeValueAsString(lf),lf.getServiceName());
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e.toString());
		}
	}
}
