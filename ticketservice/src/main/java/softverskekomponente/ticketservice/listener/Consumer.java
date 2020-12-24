package softverskekomponente.ticketservice.listener;

import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import softverskekomponente.ticketservice.entities.Ticket;
import softverskekomponente.ticketservice.repositories.TicketRepository;



@Component
public class Consumer {
	
	@Autowired
	TicketRepository ticketRepo;

	@JmsListener(destination = "CanceledFlightsTickets.queue")
	public void setFlightCanceled(String id) {
		
		
		try {
			List<Ticket> ticketList = ticketRepo.findByIdFlight(Integer.parseInt(id));
			
			for (Ticket t : ticketList) {
				t.setInfo("Canceled");
				ticketRepo.saveAndFlush(t);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
}