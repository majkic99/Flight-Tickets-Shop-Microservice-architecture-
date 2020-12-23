package softverskekomponente.userservice.forms;

public class RegistrationForm {
	private String ime;
	private String prezime;
	private String email;
	private String password;
	private String passportNumber;

	public RegistrationForm(String ime, String prezime, String email, String password, String passportNumber) {
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.password = password;
		this.passportNumber = passportNumber;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}


	
}
