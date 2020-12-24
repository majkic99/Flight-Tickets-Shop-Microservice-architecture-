package softverskekomponente.flightservice.controller;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import softverskekomponente.flightservice.entities.Airplane;
import softverskekomponente.flightservice.entities.Flight;
import softverskekomponente.flightservice.forms.NewAirplaneForm;
import softverskekomponente.flightservice.forms.NewFlightForm;
import softverskekomponente.flightservice.repositories.AirplaneRepository;
import softverskekomponente.flightservice.repositories.FlightRepository;
import softverskekomponente.flightservice.utils.UtilsMethods;

import static softverskekomponente.flightservice.security.SecurityConstants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("")
public class FlightController {
	
	private AirplaneRepository airplaneRepo;
	
	private FlightRepository flightRepo;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	Queue canceledFlightsQueueUsers;
	
	@Autowired
	Queue canceledFlightsQueueTickets;
	
	@Autowired
	public FlightController(AirplaneRepository airplaneRepo, FlightRepository flightRepo) {
		super();
		this.airplaneRepo = airplaneRepo;
		this.flightRepo = flightRepo;
	}
	
	
	@GetMapping("/findKilometers/{x}")
	public ResponseEntity<Integer> findKilometersByFlightID(@PathVariable int x){
		try {
			Optional<Flight> flight = flightRepo.findById(x);
			if (flight.isPresent()) {
				return new ResponseEntity<>(flight.get().getLengthinKM(),HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		
	}
	
	@GetMapping("/findPrice/{x}")
	public ResponseEntity<Integer> findPriceByFlightID(@PathVariable int x){
		try {
			Optional<Flight> flight = flightRepo.findById(x);
			if (flight.isPresent()) {
				return new ResponseEntity<>(flight.get().getPrice(),HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		
	}
	
	@GetMapping("/findCapacity/{x}")
	public ResponseEntity<Integer> findCapacityByFlightID(@PathVariable int x){
		try {
			Optional<Flight> flight = flightRepo.findById(x);
			if (flight.isPresent()) {
				return new ResponseEntity<>(flight.get().getAirplane().getCapacity(),HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		
	}
	
	@PostMapping("/addAirplane")
	public ResponseEntity<Airplane> addAirplane(@RequestBody NewAirplaneForm airplaneForm,@RequestHeader(value = HEADER_STRING) String token){
		
		ResponseEntity<String> isAdmin = UtilsMethods.sendGetString("http://localhost:8762/userservice/isAdmin", token);
		
		if (isAdmin.getBody().equals("YES ADMIN")) {
			Airplane airplane = new Airplane(airplaneForm.getName(), airplaneForm.getCapacity());
			airplaneRepo.saveAndFlush(airplane);
			return new ResponseEntity<>(airplane,HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
	}
	
	@GetMapping("deleteAirplane/{x}")
	public ResponseEntity<String> deleteAirplane(@PathVariable int x,@RequestHeader(value = HEADER_STRING) String token){
		
		ResponseEntity<String> isAdmin = UtilsMethods.sendGetString("http://localhost:8762/userservice/isAdmin", token);
		
		if (isAdmin.getBody().equals("YES ADMIN")) {
			
			if (!(flightRepo.existsAirplaneID(x))) {
				airplaneRepo.deleteById(x);
			}else {
				return new ResponseEntity<>("AIRPLANE IN USE",HttpStatus.FORBIDDEN);
			}
			
			
			
			return new ResponseEntity<>("DELETED AIRPLANE" , HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<>("NO ADMIN ACCESS", HttpStatus.FORBIDDEN);
		}
	}
	
	@PostMapping("addFlight")
	public ResponseEntity<Flight> addFlight(@RequestBody NewFlightForm flightForm,@RequestHeader(value = HEADER_STRING) String token) {
		ResponseEntity<String> isAdmin = UtilsMethods.sendGetString("http://localhost:8762/userservice/isAdmin", token);
		
		if (isAdmin.getBody().equals("YES ADMIN")) {
			Flight flight;
			Optional<Airplane> airplane = airplaneRepo.findById(flightForm.getAirplaneID());
			if (airplane.isPresent()) {
				
				flight = new Flight(airplane.get(), flightForm.getStart(), flightForm.getEnd(), flightForm.getLengthinKM(), flightForm.getPrice());
				flightRepo.saveAndFlush(flight);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			
			
			
			return new ResponseEntity<>(flight , HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
	}
	
	@GetMapping("deleteFlight/{x}")
	public ResponseEntity<String> deleteFlight(@PathVariable int x,@RequestHeader(value = HEADER_STRING) String token){
		
		ResponseEntity<String> isAdmin = UtilsMethods.sendGetString("http://localhost:8762/userservice/isAdmin", token);
		
		if (isAdmin.getBody().equals("YES ADMIN")) {
			Optional<Flight> flightOptinal = flightRepo.findById(x);
			
			if (flightOptinal.isPresent()) {
				Flight flight = flightOptinal.get();
				jmsTemplate.convertAndSend(canceledFlightsQueueUsers, String.valueOf(flight.getId()));
				jmsTemplate.convertAndSend(canceledFlightsQueueTickets, String.valueOf(flight.getId()));
				flight.setCanceled(true);
				flightRepo.save(flight);
				return new ResponseEntity<>("DELETED AIRPLANE" , HttpStatus.ACCEPTED);
			}
			
			
			
			return new ResponseEntity<>("FLIGHT NOT FOUND", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>("NO ADMIN ACCESS", HttpStatus.FORBIDDEN);
		}
	}
	
	@GetMapping("flights")
	public ResponseEntity<List<Flight>> allFlights(){
		
		try {
			List<Flight> list = flightRepo.findAll();
			List<Flight> response = new ArrayList<>();
			for (Flight flight : list) {
				/*
				ResponseEntity<Integer> filledNumber = UtilsMethods.sendGetInteger("http://localhost:8762/ticketservice/TakenSeats/"+flight.getId() );
				if (filledNumber.getBody() < flight.getAirplane().getCapacity()) {
					response.add(flight);
				}
				*/
				response.add(flight);
			}
			
			
			return new ResponseEntity<>(response , HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
