package flightTicketsShopUI.forms;

public class NameSurnameForm {

	private String ime;
	private String prezime;

	public NameSurnameForm() {
	}

	public NameSurnameForm(String ime, String prezime) {
		this.setIme(ime);
		this.setPrezime(prezime);
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

}
