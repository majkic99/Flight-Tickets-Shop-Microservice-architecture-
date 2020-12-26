package flightTicketsShopUI.forms;

public class BuyTicketRequestForm {

	private int idFlight;
	private int idCard;

	public BuyTicketRequestForm(int idFlight, int idCard) {

		this.idFlight = idFlight;
		this.idCard = idCard;

	}

	public int getIdFlight() {
		return idFlight;
	}

	public void setIdFlight(int idFlight) {
		this.idFlight = idFlight;
	}

	public int getIdCard() {
		return idCard;
	}

	public void setIdCard(int idCard) {
		this.idCard = idCard;
	}

}
