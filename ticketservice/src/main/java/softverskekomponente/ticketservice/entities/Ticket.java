package softverskekomponente.ticketservice.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTicket;

	private long idUser;
	private long idFlight;
	private long paidFor;

	private Date date;

	public Ticket() {
	}

	public Ticket(long idUser, long idFlight, long paidFor, Date date) {
		
		super();
		this.idUser = idUser;
		this.idFlight = idFlight;
		this.date = date;
		this.paidFor = paidFor;
		
	}

	public long getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(long idTicket) {
		this.idTicket = idTicket;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public long getIdFlight() {
		return idFlight;
	}

	public void setIdFlight(long idFlight) {
		this.idFlight = idFlight;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getPaidFor() {
		return paidFor;
	}

	public void setPaidFor(long paidFor) {
		this.paidFor = paidFor;
	}

	
	

}
