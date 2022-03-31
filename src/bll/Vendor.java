package bll;

import java.io.Serializable;

/**
 * This is Vendor bll class
 * @author anilk
 *
 */
public class Vendor implements Serializable{
	
	private String vendorId;
	private String name;
	private String address;
	private String email;
	private String phone;
	
	/**
	 * Default constructor for Vendor
	 * Creates Vendor instance
	 */
	public Vendor() {}
	
	/**
	 * Constructor for Vendor
	 * Constructs new Vendor instance with vendorId, name, address, email and phone
	 * @param vendorId Vendor Id
	 * @param name Vendor Name
	 * @param address Vendor Address
	 * @param email Vendor Email
	 * @param phone Vendor Phone
	 */
	public Vendor(String vendorId, String name, String address, String email, String phone) {
		this.vendorId = vendorId;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}
	
	/**
	 * Constructor for Vendor
	 * Constructs new Vendor instance with name, address, email and phone
	 * 
	 * @see Vendor(String, String, String, String, String)
	 * @param name Vendor Name
	 * @param address Vendor Address
	 * @param email Vendor Email
	 * @param phone Vendor Phone
	 */
	public Vendor(String name, String address, String email, String phone) {
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}
	
	/**
	 * @return the vendorId
	 */
	public String getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
