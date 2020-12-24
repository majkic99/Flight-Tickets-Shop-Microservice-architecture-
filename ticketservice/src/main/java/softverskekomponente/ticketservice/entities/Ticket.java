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
	private int idTicket;

	private int idUser;
	private int idFlight;
	private long paidFor;
	private String info;

	private Date date;

	public Ticket() {
	}

	public Ticket(int idUser, int idFlight, long paidFor, Date date) {
		
		super();
		this.idUser = idUser;
		this.idFlight = idFlight;
		this.date = date;
		this.paidFor = paidFor;
		
	}

	public int getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdFlight() {
		return idFlight;
	}

	public void setIdFlight(int idFlight) {
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	
	

}
