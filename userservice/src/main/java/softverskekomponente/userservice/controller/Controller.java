package softverskekomponente.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import softverskekomponente.userservice.forms.CreditCardFormOutput;
import softverskekomponente.userservice.forms.CreditCardFormWithID;
import softverskekomponente.userservice.forms.RegistrationForm;
import softverskekomponente.userservice.forms.UserInfoForm;
import softverskekomponente.userservice.forms.UserViewForm;
import softverskekomponente.userservice.repositories.AdminRepository;
import softverskekomponente.userservice.repositories.CreditCardsRepository;
import softverskekomponente.userservice.repositories.UserRepository;

import static softverskekomponente.userservice.security.SecurityConstants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	Queue deletedFlightEmailQueue;

	@Autowired
	public Controller(BCryptPasswordEncoder encoder, UserRepository userRepo, CreditCardsRepository ccRepo,
			AdminRepository adminRepo) {
		this.encoder = encoder;
		this.userRepo = userRepo;
		this.ccRepo = ccRepo;
		this.adminRepo = adminRepo;
	}

	@GetMapping("findUserID")
	public ResponseEntity<Integer> findUserID(@RequestHeader(value = HEADER_STRING) String token) {
		String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build().verify(token.replace(TOKEN_PREFIX, ""))
				.getSubject();
		User user = userRepo.findByEmail(email);
		if (user != null) {
			Integer response = (int) user.getId();
			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/addKMToUser/{x}")
	public ResponseEntity<Integer> addMilesToUser(@PathVariable int x,
			@RequestHeader(value = HEADER_STRING) String token) {
		synchronized (this) {
			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
			User user = userRepo.findByEmail(email);
			if (user != null) {
				user.setKilometersTraveled(user.getKilometersTraveled() + x);
				userRepo.saveAndFlush(user);
				return new ResponseEntity<>(user.getKilometersTraveled(), HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/benefitlevel")
	public ResponseEntity<String> getBenefitLevel(@RequestHeader(value = HEADER_STRING) String token) {
		String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build().verify(token.replace(TOKEN_PREFIX, ""))
				.getSubject();
		User user = userRepo.findByEmail(email);
		if (user != null) {
			switch (user.getRank()) {
			case GOLD:
				return new ResponseEntity<>("Gold", HttpStatus.ACCEPTED);

			case SILVER:
				return new ResponseEntity<>("Silver", HttpStatus.ACCEPTED);

			case BRONZE:
				return new ResponseEntity<>("Bronze", HttpStatus.ACCEPTED);

			}
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/creditcards")
	public ResponseEntity<List<CreditCardFormOutput>> usersCreditCards(
			@RequestHeader(value = HEADER_STRING) String token) {
		try {

			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
			User user = userRepo.findByEmail(email);
			if (user != null) {

				List<CreditCard> lista = ccRepo.getCreditCardsByUserID(user.getId());
				List<CreditCardFormOutput> response = new ArrayList<>();
				for (CreditCard cc : lista) {
					CreditCardFormOutput ccform = new CreditCardFormOutput(cc.getName(), cc.getSurname(),
							cc.getCardNumber());
					response.add(ccform);
				}

				return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			System.out.println("exception");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/creditcardsWithCVC")
	public ResponseEntity<List<CreditCardFormWithID>> usersCreditCardsWithCVC(
			@RequestHeader(value = HEADER_STRING) String token) {
		try {

			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
			User user = userRepo.findByEmail(email);
			if (user != null) {

				List<CreditCard> lista = ccRepo.getCreditCardsByUserID(user.getId());
				List<CreditCardFormWithID> response = new ArrayList<>();
				for (CreditCard cc : lista) {
					CreditCardFormWithID ccform = new CreditCardFormWithID(cc.getId(),cc.getName(), cc.getSurname(), cc.getCardNumber(),
							cc.getCvcNumber());
					response.add(ccform);
				}

				return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/isAdmin")
	public ResponseEntity<String> checkIfAdmin(@RequestHeader(value = HEADER_STRING) String token) {
		try {
			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			if (adminRepo.existsByEmail(email)) {
				return new ResponseEntity<>("YES ADMIN", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("NO ADMIN", HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegistrationForm registrationForm) {

		try {
			synchronized (this) {
				if (userRepo.existsByEmail(registrationForm.getEmail())) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

				// iscitavamo entitet iz registracione forme
				User user = new User(registrationForm.getIme(), registrationForm.getPrezime(),
						registrationForm.getEmail(), encoder.encode(registrationForm.getPassword()),
						registrationForm.getPassportNumber());

				// cuvamo u nasoj bazi ovaj entitet
				userRepo.saveAndFlush(user);

				jmsTemplate.convertAndSend(newAccountEmailQueue, registrationForm.getEmail());
				return new ResponseEntity<>("Succesfully registered user - ID:" + user.getId(), HttpStatus.ACCEPTED);
			}

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

	@GetMapping("/whoAmIAll")
	public ResponseEntity<UserViewForm> whoAmIAll(@RequestHeader(value = HEADER_STRING) String token) {
		try {

			// izvlacimo iz tokena subject koj je postavljen da bude email
			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			User user = userRepo.findByEmail(email);

			return new ResponseEntity<>(new UserViewForm(user.getIme(), user.getPrezime(), user.getEmail(),
					user.getPassportNumber(), user.getKilometersTraveled(), user.getRank().toString()),
					HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/addcc")
	public ResponseEntity<Integer> addCreditCard(@RequestBody CreditCardForm ccForm,
			@RequestHeader(value = HEADER_STRING) String token) {
		try {
			synchronized (token) {
				String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
						.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

				User user = userRepo.findByEmail(email);
				if (user != null) {
					CreditCard cc = new CreditCard(user, ccForm.getName(), ccForm.getSurname(), ccForm.getCardNumber(),
							ccForm.getCvcNumber());
					ccRepo.saveAndFlush(cc);
					return new ResponseEntity<>(cc.getId() ,HttpStatus.ACCEPTED);

				} else {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("deletecc/{x}")
	public ResponseEntity<String> deleteCreditCard(@PathVariable int x,
			@RequestHeader(value = HEADER_STRING) String token) {

		ccRepo.deleteById(x);

		return new ResponseEntity<>("DELETED CREDIT CARD", HttpStatus.ACCEPTED);

	}

	@PutMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody RegistrationForm regForm,
			@RequestHeader(value = HEADER_STRING) String token) {

		synchronized (this) {
			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			User user = userRepo.findByEmail(email);
			if (user != null) {
				if (regForm.getEmail() != null) {
					user.setEmail(regForm.getEmail());
					jmsTemplate.convertAndSend(changedAccountEmailQueue, regForm.getEmail());

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

			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		}

	}
	
	@GetMapping("cardUserNameByID/{id}")
	public ResponseEntity<String> cardUserNameByID(@PathVariable int id) {
		try {
			Optional<CreditCard> cc = ccRepo.findById(id);
			if (cc.isPresent()) {
				return new ResponseEntity<>(cc.get().getName() + " " + cc.get().getSurname(), HttpStatus.ACCEPTED);
			}
			
			throw new Exception();

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
