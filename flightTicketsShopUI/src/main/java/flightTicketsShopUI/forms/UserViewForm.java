package flightTicketsShopUI.forms;

public class UserViewForm {

	private int id;
	
	private String name;
	private String surname;
	private String email;
	private String passportNumber;
	private int traveled;
	private String rank;
	

	public UserViewForm() {
	}

	public UserViewForm(String name, String surname, String email, String passportNumber, int traveled, String rank) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.passportNumber = passportNumber;
		this.traveled = traveled;
		this.setRank(rank);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public int getTraveled() {
		return traveled;
	}

	public void setTraveled(int traveled) {
		this.traveled = traveled;
	}

	@Override
	public String toString() {
		return name + " " + surname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

}
