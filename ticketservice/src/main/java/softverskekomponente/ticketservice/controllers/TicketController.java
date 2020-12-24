package softverskekomponente.ticketservice.controllers;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/usersonFlight/{flightID}")
	public ResponseEntity<List<Integer>> usersOnFlight(@PathVariable int flightID){
		try {
			List<Ticket> ticketList = ticketRepository.findByIdFlight(flightID);
			
			List<Integer> response = new ArrayList<>();
			
			for (Ticket t : ticketList) {
				response.add( t.getIdUser());
			}
			
			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
			
			
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/TakenSeats/{flightID}")
	public ResponseEntity<Integer> isFlightFull(@PathVariable int flightID){
		try {
			return new ResponseEntity<>(ticketRepository.numberOfTakenPlaces(flightID), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	@GetMapping("/myTickets")
	public ResponseEntity<List<Ticket>> mySortedTickets(@RequestHeader(value = "Authorization") String token){
		try {
			ResponseEntity<Integer> responseUserID = UtilsMethods
					.sendGet("http://localhost:8762/userservice/findUserID/", token);
			
			List<Ticket> response = ticketRepository.findMineSorted(responseUserID.getBody());
			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	
	@PostMapping("/buyTicket")
	public ResponseEntity<Object> buyTicket(@RequestBody BuyTicketForm buyTicketForm,
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
			UtilsMethods.sendGet("http://localhost:8762/userservice/addKMToUser/" + responseKilometers.getBody(),
					token);

			ResponseEntity<Integer> responsePrice = UtilsMethods
					.sendGet("http://localhost:8762/flightservice/findPrice/" + buyTicketForm.getIdFlight(), token);

			long novaCena = responsePrice.getBody() - (responsePrice.getBody() * popust) / 100;

			ResponseEntity<Integer> responseUserID = UtilsMethods
					.sendGet("http://localhost:8762/userservice/findUserID/", token);

			Ticket ticket = new Ticket( responseUserID.getBody(), buyTicketForm.getIdFlight(), novaCena,
					new Date());
			
			ticketRepository.save(ticket);
			
			buyTicketForm.setDate(ticket.getDate());
			return new ResponseEntity<>(buyTicketForm, HttpStatus.ACCEPTED);

		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("ticketsFromUser")
	public ResponseEntity<List<Ticket>> allTicketsFromUser(@RequestHeader(value = "Authorization") String token){
		
		try {
			
			ResponseEntity<Integer> responseUserID = UtilsMethods
					.sendGet("http://localhost:8762/userservice/findUserID/", token);
			
			List<Ticket> response = ticketRepository.findByIdUser(responseUserID.getBody());
			return new ResponseEntity<>(response , HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("tickets")
	public ResponseEntity<List<Ticket>> allTickets(){
		
		try {
			List<Ticket> response = ticketRepository.findAll();
			return new ResponseEntity<>(response , HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

}
