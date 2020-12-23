package softverskekomponente.userservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import softverskekomponente.userservice.entities.enums.Rank;

@Entity
public class User {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String ime;
	private String prezime;
	private String email;
	private String password;
	public String passportNumber;
	private int kilometersTraveled;

	public User() {

	}

	public User(String ime, String prezime, String email, String password, String passportNumber) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.password = password;
		this.passportNumber = passportNumber;
		this.kilometersTraveled = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getKilometersTraveled() {
		return kilometersTraveled;
	}

	public void setKilometersTraveled(int kilometersTraveled) {
		this.kilometersTraveled = kilometersTraveled;
	}

	public Rank getRank() {
		if (this.kilometersTraveled < 1000) {
			return Rank.BRONZE;
		}else if(this.kilometersTraveled < 10000) {
			return Rank.SILVER;
		}else {
			return Rank.GOLD;
		}
	}
	
	

}
