package softverskekomponente.userservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import org.springframework.jms.core.JmsTemplate;

@Configuration
public class Config {
	@Value("${activemq.broker-url}")
	public String brokerUrl;

	@Bean
	public Queue newAccountEmailQueue() {
		return new ActiveMQQueue("NewAccountEmail.queue");
	}
	
	@Bean
	public Queue changedAccountEmailQueue() {
		return new ActiveMQQueue("AccountChanged.queue");
	}
	
	@Bean
	public Queue deletedFlightEmailQueue() {
		return new ActiveMQQueue("DeletedFlightEmail.queue");
	}
	

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		factory.setBrokerURL(brokerUrl);
		return factory;
	}
	
	@Bean
	public JmsTemplate jmsTemplate() {
		return new JmsTemplate(activeMQConnectionFactory());
	}
}
