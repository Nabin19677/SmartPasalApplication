package bll;

import java.io.Serializable;

/**
 * This class is for User bll
 * @author anilk
 *
 */
public class User implements Serializable{

	private String userId;
	private String username;
	private String email;
	private String contact;

	/**
	 * Default User constructor
	 * Creates a new User instance.
	 */
	public User() {}
	
	/**
	 * Construct User class
	 * Creates a new User instance with userId, username, email and contact
	 * 
	 * @param userId User Id
	 * @param username User Name
	 * @param email Email
	 * @param contact Contact Number
	 */
	public User(String userId, String username, String email, String contact) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.contact = contact;
	}
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
}
