package softverskekomponente.flightservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Airplane airplane;
	private String start;
	private String end;
	private int lengthinKM;
	private int price;
	private boolean canceled;
	
	
	public Flight() {
		
	}

	public Flight(Airplane airplane, String start, String end, int lengthinKM, int price) {
		super();
		this.airplane = airplane;
		this.start = start;
		this.end = end;
		this.lengthinKM = lengthinKM;
		this.price = price;
		this.canceled = false;
	}

	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getLengthinKM() {
		return lengthinKM;
	}

	public void setLengthinKM(int lengthinKM) {
		this.lengthinKM = lengthinKM;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	
	
	
	
	
}
