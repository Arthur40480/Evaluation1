package fr.fms.entities;

public class Formation {
	private int id;
	private String name;
	private String description;
	private int duration;
	private boolean remote;
	private double unitaryPrice;
	private int category;
	private int quantity = 1;
	
	public Formation(int id, String name, String description, int duration, boolean remote, double unitaryPrice, int category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.remote = remote;
		this.unitaryPrice = unitaryPrice;
		this.category = category;
	}
	
	public Formation(int id, String name, String description, int duration, boolean remote, double unitaryPrice) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.remote = remote;
		this.unitaryPrice = unitaryPrice;
	}
	
	public Formation(String name, String description, int duration, boolean remote, double unitaryPrice) {
		this.name = name;
		this.description = description;
		this.remote = remote;
		this.unitaryPrice = unitaryPrice;
	}
	
	@Override
	public String toString() {
		return "Formation:" + getName() + ", " + getDescription() + ", durée: " + getDuration() + "jours, remote: " + isRemote()+ ", prix: " + getUnitaryPrice() + "€"; 
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isRemote() {
		return remote;
	}

	public void setRemote(boolean remote) {
		this.remote = remote;
	}

	public double getUnitaryPrice() {
		return unitaryPrice;
	}

	public void setUnitaryPrice(double unitaryPrice) {
		this.unitaryPrice = unitaryPrice;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

