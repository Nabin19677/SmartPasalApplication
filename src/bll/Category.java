package bll;

import java.io.Serializable;

/**
 * Category Class
 * @author anilk
 *
 */
public class Category implements Serializable {

	private String categoryId;
	private String categoryName;
	
	/**
	* Default Constructor of Category Class,
	* Creates a new Category instance with no initial value.
	*/
	public Category() {
		super();
	}
	
	/**
	 * Constructor for Category Class
	 * Creates a new Category Instance with categoryId and categoryName
	 * @param categoryId Category Id
	 * @param categoryName Category Name
	 */
	public Category(String categoryId, String categoryName) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	
	/**
	 * Constructor for Category Class
	 * Creates a new Category Instance with categoryName
	 * @param categoryName
	 */
	public Category(String categoryName) {
		super();
		this.categoryName = categoryName;
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
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@Override
	public String toString() {
		return categoryName;
	}
	
	
	
	
	
}
