package flightTicketsShopUI.forms;

import java.util.Date;

public class BuyTicketForm {

	private int idFlight;
	private int idCard;
	private Date date;

	public BuyTicketForm(int idFlight, int idCard, Date date) {

		this.idFlight = idFlight;
		this.idCard = idCard;
		this.date = date;
		

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	

}
