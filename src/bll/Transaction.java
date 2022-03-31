package bll;

import java.io.Serializable;

import javafx.scene.control.Button;

/**
 * This is Transaction bll class
 * 
 * @author anilk
 *
 */
public class Transaction implements Serializable{
	private String transId;
	private String total;
	private String transDateTime;
	
	/**
	 * Default Constructor for Transaction class.
	 * Creates a new Transaction instance with no initial value.
	 */
	public Transaction() {}
	
	/**
	 * Constructor for Transaction class
	 * Creates a new Transaction Instance with transId, total and transDateTime
	 * @param transId Transaction Id
	 * @param total Transaction Total Amount
	 * @param transDateTime Transaction Date and Time
	 */
	public Transaction(String transId, String total, String transDateTime) {
		this.transId = transId;
		this.total  = total;
		this.transDateTime = transDateTime;
	}

	/**
	 * @return the transId
	 */
	public String getTransId() {
		return transId;
	}

	/**
	 * @param transId the transId to set
	 */
	public void setTransId(String transId) {
		this.transId = transId;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the transDateTime
	 */
	public String getTransDateTime() {
		return transDateTime;
	}

	/**
	 * @param transDateTime the transDateTime to set
	 */
	public void setTransDateTime(String transDateTime) {
		this.transDateTime = transDateTime;
	}

}
