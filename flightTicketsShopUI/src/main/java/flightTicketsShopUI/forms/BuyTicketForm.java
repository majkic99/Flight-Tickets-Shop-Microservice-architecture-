package flightTicketsShopUI.forms;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;

import flightTicketsShopUI.utils.HttpUtils;

public class BuyTicketForm {

	private int idTicket;
	private int idFlight;
	private int idUser;
	private String userName;
	private String flight;
	private Date date;
	private int paidFor;
	private String info;
	

	public BuyTicketForm() {

	}
	
	public BuyTicketForm(int idFlight, int idUser, Date date, int paidFor) {

		this.idFlight = idFlight;
		this.setIdUser(idUser);
		this.date = date;
		this.setPaidFor(paidFor);
		findFlight();
		findUserName();

	}
	
	public void findFlight() {
		ResponseEntity<String> response = HttpUtils
				.sendGetReturnString("http://localhost:8762/flightservice/flightByID/" + idFlight, "");

		if (response.getStatusCode() == HttpStatus.ACCEPTED)
			this.setFlight(response.getBody());

	}

	public void findUserName() {
		ResponseEntity<String> response = HttpUtils
				.sendGetReturnString("http://localhost:8762/userservice/whoAmI/", HttpUtils.getTOKEN());

		if (response.getStatusCode() == HttpStatus.ACCEPTED) {
			String s = response.getBody();
			Gson gson  = new Gson();
			NameSurnameForm f = gson.fromJson(s, NameSurnameForm.class);
			
			this.setUserName(f.getIme() + " " + f.getPrezime());
		}

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

	public int getPaidFor() {
		return paidFor;
	}

	public void setPaidFor(int paidFor) {
		this.paidFor = paidFor;
	}

	public String getFlight() {
		return flight;
	}

	public void setFlight(String flight) {
		this.flight = flight;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
