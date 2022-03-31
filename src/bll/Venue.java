package bll;

/**
 * @author anilk
 */
public class Venue {

	private String name;
	private String address;
	private String category;
	
	/**
	 * Default Venue constructor
	 */
	public Venue() {}
	
	/**
	 * Venue Constructor
	 * @param name
	 * @param address
	 * @param category
	 */
	public Venue(String name, String address, String category) {
		this.name = name;
		this.address = address;
		this.category = category;
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
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
