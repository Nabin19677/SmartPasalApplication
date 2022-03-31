package bll;

import java.io.Serializable;

/**
 * This is Product bll class
 * @author anilk
 *
 */
public class Product implements Serializable{
	
	private String productId;
	private String productName;
    private int quantity = 0;
    private double rate = 0.00;
    private double totalProductPrice = 0.00;
    private String categoryId;

    /**
     * Default Constructor for class Product
     * Creates a new Product instance.
     */
    public Product() {}

    /**
     * Constructor for Product class
     * Creates Product instance with productId, productName, quantity, rate, categoryId and totalProductPrice
     * @param productId Product Id
     * @param productName Product Name
     * @param quantity Product Quantity
     * @param rate Product Rate
     * @param categoryId Product's Category Id
     * @param totalProductPrice Product Total Amount
     */
    public Product(String productId, String productName, int quantity, double rate, String categoryId, double totalProductPrice) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.rate = rate;
		this.totalProductPrice = totalProductPrice;
		this.categoryId = categoryId;
	}
    
    /**
     * Constructor for Product class
     * Creates Product instance with productId, productName, quantity, rate, categoryId and totalProductPrice
     * @see Product(String, String, int, double, String, double)
     * @param productId Product Id
     * @param productName Product Name
     * @param quantity Product Quantity
     * @param rate Product Rate
     * @param categoryId Product's Category Id
     * @param totalProductPrice Product Total Amount
     */
    public Product(String productId, String productName, int quantity, double rate, String categoryId) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.rate = rate;
		this.categoryId = categoryId;
	}
    
    /**
     * Constructor for Product class
     * Creates Product instance with productId, productName, quantity, rate, categoryId and totalProductPrice
     * @see Product(String, String, int, double, String, double)
     * @param productId Product Id
     * @param productName Product Name
     * @param quantity Product Quantity
     * @param rate Product Rate
     * @param categoryId Product's Category Id
     * @param totalProductPrice Product Total Amount
     */
    public Product(String productId, String productName, int quantity, double rate, double totalProductPrice) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.rate = rate;
		this.totalProductPrice = totalProductPrice;
	}
    
    /**
     * Constructor for Product class
     * Creates Product instance with productId, productName, quantity, rate, and categoryId
     * @see Product(String, String, int, double, String, double)
     * @param productId Product Id
     * @param productName Product Name
     * @param quantity Product Quantity
     * @param rate Product Rate
     * @param categoryId Product's Category Id
     */
    public Product(String productName, int quantity, double rate, String categoryId) {
		super();
		this.productName = productName;
		this.quantity = quantity;
		this.rate = rate;
		this.categoryId = categoryId;
	}
    
    /**
     * Constructor for Product class
     * Creates Product instance with productId, productName, quantity, and rate
     * @see Product(String, String, int, double, String, double)
     * @param productId Product Id
     * @param productName Product Name
     * @param quantity Product Quantity
     * @param rate Product Rate
     */
    public Product(String productId, String productName, int quantity, double rate) {
    	this.productId = productId;
    	this.productName = productName;
    	this.quantity = quantity;
    	this.rate = rate;
    }
    
    /**
     * Constructor for Product class
     * Creates Product instance with productId, productName and quantity
     * @see Product(String, String, int, double, String, double)
     * @param productId Product Id
     * @param productName Product Name
     * @param quantity Product Quantity
     */
    public Product(String productId, String productName, int quantity) {
    	this.productId = productId;
    	this.productName = productName;
    	this.quantity = quantity;
    }
    
    /**
     * Constructor for Product class
     * Creates Product instance with productId and productName
     * @see Product(String, String, int, double, String, double)
     * @param productId Product Id
     * @param productName Product Name
     */
    public Product(String productId, String productName) {
    	this.productId = productId;
    	this.productName = productName;
    }

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * @return the totalProductPrice
	 */
	public double getTotalProductPrice() {
		return totalProductPrice;
	}

	/**
	 * @param totalProductPrice the totalProductPrice to set
	 */
	public void setTotalProductPrice(double totalProductPrice) {
		this.totalProductPrice = totalProductPrice;
	}


	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}


	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
    

}
