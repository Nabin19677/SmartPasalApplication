package bll;

import java.io.Serializable;

/**
 * 
 * Auth class. Containing User Authentication 
 * 
 * @author anilk
 *
 */
public class Auth implements Serializable {

	private String username;
	private String password;
	
	/**
	 * Default Constructor of Auth Class,
	 * Creates a new Auth instance with no initial value.
	 */
	public Auth() {
		super();
	}
	
	/**
	 * Constructor for Auth Class
	 * Creates a new Auth instance with username and password
	 * @param username Username
	 * @param password Password
	 */
	public Auth(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
