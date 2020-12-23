package softverskekomponente.userservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
public class CreditCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private User user;
	
	private String name;
	private String surname;
	private String cardNumber;
	private int cvcNumber;
	
	public CreditCard() {
		
	}
	public CreditCard(User user, String name, String surname, String cardNumber, int cvcNumber) {
		super();
		this.user = user;
		this.name = name;
		this.surname = surname;
		this.cardNumber = cardNumber;
		this.cvcNumber = cvcNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getCvcNumber() {
		return cvcNumber;
	}

	public void setCvcNumber(int cvcNumber) {
		this.cvcNumber = cvcNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
}
