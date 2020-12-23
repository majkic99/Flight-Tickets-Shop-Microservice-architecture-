package softverskekomponente.userservice.forms;

public class CreditCardFormOutput {
	private String name;
	private String surname;
	private String cardNumber;
	public CreditCardFormOutput(String name, String surname, String cardNumber) {
		super();
		this.name = name;
		this.surname = surname;
		this.cardNumber = cardNumber;
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
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
}
