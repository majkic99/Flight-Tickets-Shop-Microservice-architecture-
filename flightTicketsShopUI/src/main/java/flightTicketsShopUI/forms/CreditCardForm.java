package flightTicketsShopUI.forms;

public class CreditCardForm {

	private String name;
	private String surname;
	private String cardNumber;
	private int cvcNumber;
	public CreditCardForm(String name, String surname, String cardNumber, int cvcNumber) {
		super();
		this.name = name;
		this.surname = surname;
		this.cardNumber = cardNumber;
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
	
	
	
}
