package softverskekomponente.ticketservice.forms;

import java.util.Date;

public class BuyTicketForm {

	private long idFlight;
	private long idCard;
	

	public BuyTicketForm(long idFlight, long idCard, Date date) {

		this.idFlight = idFlight;
		this.idCard = idCard;
		

	}

	public long getIdFlight() {
		return idFlight;
	}

	public void setIdFlight(long idFlight) {
		this.idFlight = idFlight;
	}

	public long getIdCard() {
		return idCard;
	}

	public void setIdCard(long idCard) {
		this.idCard = idCard;
	}

	

}