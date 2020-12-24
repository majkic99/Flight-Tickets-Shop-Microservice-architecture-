package softverskekomponente.userservice.listener;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import softverskekomponente.userservice.entities.User;
import softverskekomponente.userservice.repositories.UserRepository;
import softverskekomponente.userservice.utils.UtilsMethods;


@Component
public class Consumer {

	

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	Queue deletedFlightEmailQueue;
	
	@JmsListener(destination = "CanceledFlightsUsers.queue")
	public void sendChangedEmail(String id) {
		
		try {
			ResponseEntity<Object> object = UtilsMethods.sendGetObject("http://localhost:8762/ticketsservice/usersonFlight/"+id);
			List<Integer> listInt = (List<Integer>) object.getBody();
			List<User> listUser = userRepo.findAllById(listInt);
			
			ResponseEntity<Integer> kilometersResponse = UtilsMethods.sendGetInt("http://localhost:8762/flightservice/findKilometers/"+id);
			int km = kilometersResponse.getBody();
			for (User user : listUser) {
				user.setKilometersTraveled(user.getKilometersTraveled() - km);
				jmsTemplate.convertAndSend(deletedFlightEmailQueue, user.getEmail());
				userRepo.save(user);
				
			}
			
			
			
			
			
			
			
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (JmsException e) {
			
			e.printStackTrace();
		}
		
		
	}
}