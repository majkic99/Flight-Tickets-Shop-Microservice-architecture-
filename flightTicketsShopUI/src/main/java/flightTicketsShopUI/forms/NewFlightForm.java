package flightTicketsShopUI.forms;



public class NewFlightForm {
	
	private int airplaneID;
	private String start;
	private String end;
	private int lengthinKM;
	private int price;
	
	public NewFlightForm(int airplaneID, String start, String end, int lengthinKM, int price) {
		super();
		this.airplaneID = airplaneID;
		this.start = start;
		this.end = end;
		this.lengthinKM = lengthinKM;
		this.price = price;
	}
	
	public int getAirplaneID() {
		return airplaneID;
	}

	public void setAirplaneID(int airplaneID) {
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
