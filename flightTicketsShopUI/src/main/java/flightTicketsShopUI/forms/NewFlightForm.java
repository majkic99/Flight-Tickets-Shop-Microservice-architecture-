package flightTicketsShopUI.forms;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import flightTicketsShopUI.utils.HttpUtils;

public class NewFlightForm {

	private int id;

	private String airplaneName;
	private NewAirplaneForm airplane;
	private String start;
	private String end;
	private int lengthinKM;
	private int price;
	private boolean canceled;

	public NewFlightForm() {

	}

	public NewFlightForm(String start, String end, int lengthinKM, int price, boolean canceled,
			NewAirplaneForm airplane) {
		super();

		this.setAirplane(airplane);
		this.start = start;
		this.end = end;
		this.lengthinKM = lengthinKM;
		this.price = price;
		this.setCanceled(canceled);
		findAirplaneName();
	}

	private void findAirplaneName() {

		ResponseEntity<String> response = HttpUtils
				.sendGetReturnString("http://localhost:8762/flightservice/airplaneNameByID/" + airplane.getId(), "");

		if (response.getStatusCode() == HttpStatus.ACCEPTED)
			this.airplaneName = response.getBody();

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

	public String getAirplaneName() {
		return airplaneName;
	}

	public void setAirplaneName(String airplaneName) {
		this.airplaneName = airplaneName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public NewAirplaneForm getAirplane() {
		return airplane;
	}

	public void setAirplane(NewAirplaneForm airplane) {
		this.airplane = airplane;
		findAirplaneName();
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	@Override
	public String toString() {
		return start + " - " + end;
	}

}
