package sample;

public class Hotel {
	public Hotel() {
	}

	public Hotel(String id, String name, String city, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.city = city;
	}

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String description;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String city;

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String toString() {
		return "Hotel " + getId() + ": " + getName() + " (" + getCity() + ")";
	}
}
