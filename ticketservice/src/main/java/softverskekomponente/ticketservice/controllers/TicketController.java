package softverskekomponente.ticketservice.controllers;

import java.util.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import softverskekomponente.ticketservice.entities.Ticket;
import softverskekomponente.ticketservice.forms.BuyTicketForm;
import softverskekomponente.ticketservice.repositories.TicketRepository;
import softverskekomponente.ticketservice.utils.UtilsMethods;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("")
public class TicketController {

	private TicketRepository ticketRepository;

	@Autowired
	public TicketController(TicketRepository ticketRepository) {

		this.ticketRepository = ticketRepository;

	}
	
	@GetMapping("/test")
	public String string() {
		return "test";
	}
	
	@PostMapping("/buyTicket")
	public ResponseEntity<String> buyTicket(@RequestBody BuyTicketForm buyTicketForm,
			@RequestHeader(value = "Authorization") String token) {

		try {
			int popust = 0;

			ResponseEntity<Integer> responseCapacity = UtilsMethods
					.sendGet("http://localhost:8762/flightservice/findCapacity/" + buyTicketForm.getIdFlight(), token);

			List<Ticket> soldTickets = ticketRepository.findByIdFlight(buyTicketForm.getIdFlight());
			if (soldTickets.size() >= responseCapacity.getBody()) {
				return new ResponseEntity<>("Popunjeno!", HttpStatus.NOT_ACCEPTABLE);
			}

			ResponseEntity<String> responseRank = UtilsMethods
					.sendGetString("http://localhost:8762/userservice/benefitlevel", token);

			switch (responseRank.getBody()) {

			case ("Gold"):

				popust = 20;
				break;
			case ("Silver"):

				popust = 10;
				break;
			}

			ResponseEntity<Integer> responseKilometers = UtilsMethods.sendGet(
					"http://localhost:8762/flightservice/findKilometers/" + buyTicketForm.getIdFlight(), token);
			UtilsMethods.sendGet("http://localhost:8762/userservice/addMilesToUser/" + responseKilometers.getBody(),
					token);

			ResponseEntity<Integer> responsePrice = UtilsMethods
					.sendGet("http://localhost:8762/flightservice/findPrice/" + buyTicketForm.getIdFlight(), token);

			long novaCena = responsePrice.getBody() - (responsePrice.getBody() * popust) / 100;

			ResponseEntity<Integer> responseUserID = UtilsMethods
					.sendGet("http://localhost:8762/userservice/findUserID/", token);

			Ticket ticket = new Ticket((long) responseUserID.getBody(), buyTicketForm.getIdFlight(), novaCena,
					new Date());
			
			ticketRepository.save(ticket);

		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return null;
	}

}
