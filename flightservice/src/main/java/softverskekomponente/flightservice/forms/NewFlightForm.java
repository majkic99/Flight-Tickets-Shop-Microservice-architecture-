package softverskekomponente.flightservice.forms;

import javax.persistence.ManyToOne;

import softverskekomponente.flightservice.entities.Airplane;

public class NewFlightForm {
	
	private long airplaneID;
	private String start;
	private String end;
	private int lengthinKM;
	private int price;
	
	public NewFlightForm(long airplaneID, String start, String end, int lengthinKM, int price) {
		super();
		this.airplaneID = airplaneID;
		this.start = start;
		this.end = end;
		this.lengthinKM = lengthinKM;
		this.price = price;
	}
	
	public long getAirplaneID() {
		return airplaneID;
	}

	public void setAirplaneID(long airplaneID) {
		this.airplaneID = airplaneID;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
