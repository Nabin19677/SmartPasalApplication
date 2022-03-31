package dll;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import bll.Category;
import bll.Product;
import bll.Vendor;

/**
 * IProduct.java
 * Interface for products related methods
 * @author anilk
 *
 */
public interface IProduct extends Remote {
	/**
	 * gets all products more than 0
	 * @return ArrayList of Product On Stock Products
	 * @see bll.Product
	 * @throws RemoteException
	 */
	ArrayList<Product> getAllProductsOnStock() throws RemoteException;
	
	/**
	 * gets all products 
	 * @return ArrayList of Product. All Products
	 * @see bll.Product
	 * @throws RemoteException
	 */
	ArrayList<Product> getAllProducts() throws RemoteException;
	
	/**
	 * get all category
	 * @return ArrayList of Category. All Category
	 * @see bll.Category
	 * @throws RemoteException
	 */
	ArrayList<Category> getAllCategory() throws RemoteException;
	
	/**
	 * save product to database
	 * @param product Instance of Product
	 * @see bll.Product
	 * @return boolean true if product is saved to database
	 * @throws RemoteException
	 */
	boolean saveProduct(Product product) throws RemoteException;
	
	/**
	 * save category to database
	 * @param category Instance of Category
	 * @see bll.Category
	 * @return boolean true if category is saved to database
	 * @throws RemoteException
	 */
	boolean saveCategory(Category category) throws RemoteException;
	
	/**
	 * Delete Product
	 * @param productId Product's Id
	 * @return boolean true if product is deleted
	 * @throws RemoteException
	 */
	boolean deleteProduct(String productId) throws RemoteException;
	
}