package softverskekomponente.userservice.controller;



import org.hibernate.boot.archive.spi.JarFileEntryUrlAdjuster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import softverskekomponente.userservice.entities.CreditCard;
import softverskekomponente.userservice.entities.User;
import softverskekomponente.userservice.forms.CreditCardForm;
import softverskekomponente.userservice.forms.RegistrationForm;
import softverskekomponente.userservice.forms.UserInfoForm;
import softverskekomponente.userservice.repositories.AdminRepository;
import softverskekomponente.userservice.repositories.CreditCardsRepository;
import softverskekomponente.userservice.repositories.UserRepository;
import static softverskekomponente.userservice.security.SecurityConstants.*;

import javax.jms.Queue;


@RestController
@RequestMapping("")
public class Controller {

	private BCryptPasswordEncoder encoder;
	private UserRepository userRepo;
	private CreditCardsRepository ccRepo;
	private AdminRepository adminRepo;
	
	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	Queue newAccountEmailQueue;
	
	@Autowired
	Queue changedAccountEmailQueue;

	@Autowired
	public Controller(BCryptPasswordEncoder encoder, UserRepository userRepo, CreditCardsRepository ccRepo, AdminRepository adminRepo) {
		this.encoder = encoder;
		this.userRepo = userRepo;
		this.ccRepo = ccRepo;
		this.adminRepo = adminRepo;
	}
	
	@GetMapping("/isAdmin")
	public ResponseEntity<String> checkIfAdmin(@RequestHeader(value = HEADER_STRING) String token){
		String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
				.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
		System.out.println(email);
		if (adminRepo.existsByEmail(email)) {
			return new ResponseEntity<>("YES ADMIN", HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<>("NO ADMIN", HttpStatus.FORBIDDEN);
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegistrationForm registrationForm) {

		try {
			if (userRepo.existsByEmail(registrationForm.getEmail())) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			// iscitavamo entitet iz registracione forme
			User user = new User(registrationForm.getIme(), registrationForm.getPrezime(), registrationForm.getEmail(),
					encoder.encode(registrationForm.getPassword()), registrationForm.getPassportNumber());

			// cuvamo u nasoj bazi ovaj entitet
			userRepo.saveAndFlush(user);
			//TODO SEND EMAIL
			jmsTemplate.convertAndSend(newAccountEmailQueue,registrationForm.getEmail());
			return new ResponseEntity<>("Succesfully registered user - ID:" + user.getId(), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/whoAmI")
	public ResponseEntity<UserInfoForm> whoAmI(@RequestHeader(value = HEADER_STRING) String token) {
		try {

			// izvlacimo iz tokena subject koj je postavljen da bude email
			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			User user = userRepo.findByEmail(email);

			return new ResponseEntity<>(new UserInfoForm(user.getIme(), user.getPrezime()), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/addcc")
	public ResponseEntity<String> addCreditCard(@RequestBody CreditCardForm ccForm, @RequestHeader(value = HEADER_STRING) String token ){
		try {
			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			User user = userRepo.findByEmail(email);
			if (user != null) {
				CreditCard cc = new CreditCard(user, ccForm.getName(), ccForm.getSurname(), ccForm.getCardNumber(), ccForm.getCvcNumber());
				ccRepo.saveAndFlush(cc);
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
				
			}else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
			
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody RegistrationForm regForm, @RequestHeader(value = HEADER_STRING) String token){
		String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
				.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

		User user = userRepo.findByEmail(email);
		if (user != null) {
			if (regForm.getEmail() != null) {
				user.setEmail(regForm.getEmail());
				jmsTemplate.convertAndSend(changedAccountEmailQueue,regForm.getEmail());
				
			}
			if (regForm.getIme() != null) {
				user.setIme(regForm.getIme());
			}
			if (regForm.getPrezime() != null) {
				user.setPrezime(regForm.getPrezime());
			}
			if (regForm.getPassportNumber() != null) {
				user.setPassportNumber(regForm.getPassportNumber());
			}
			if (regForm.getPassword() != null) {
				user.setPassword(encoder.encode(regForm.getPassword()));
			}
			userRepo.saveAndFlush(user);
			
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
			
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	
	
}

