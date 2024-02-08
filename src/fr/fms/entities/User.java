package fr.fms.entities;

public class User {
	private int id;
	private String login;
	private String password;
	private boolean admin = false;
	
	public User(int id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
	}
	
	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public User(String login, String password, boolean admin) {
		this.login = login;
		this.password = password;
		this.admin = admin;
	}
	
	public User(int id, String login, String password, boolean admin) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.admin = admin;
	}
	
	@Override
	public String toString() {
		return "[User - Id: " + getId() + "   Login: " + getLogin() + "   Password: " + getPassword() + "   Admin: " + isAdmin() + "]";
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
