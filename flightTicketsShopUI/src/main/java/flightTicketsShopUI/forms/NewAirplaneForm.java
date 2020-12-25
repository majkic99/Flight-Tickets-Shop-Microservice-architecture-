package flightTicketsShopUI.forms;

public class NewAirplaneForm {

	private int id;

	private String name;
	private int capacity;

	public NewAirplaneForm() {
		
	}
	
	public NewAirplaneForm(String name, int capacity) {
		super();
		this.name = name;
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
