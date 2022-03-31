package dll;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import bll.Product;

/**
 * IDashboard
 * Interface for method related to Dashboard
 * @author anilk
 *
 */
public interface IDashboard extends Remote {

	/**
	 * String contains data used is dashboard quick details field
	 * 
	 * @return ArrayList of String quick details
	 * @throws RemoteException
	 */
	ArrayList<String> getQuickDetails() throws RemoteException;
	
	/**
	 * This method returns sales data for pie chart
	 * 
	 * @return Multidimensional ArrayList of String sales data
	 * @throws RemoteException
	 */
	ArrayList<ArrayList<String>> getSalePieChartData() throws RemoteException;
	
	/**
	 * This method returns sales data for line chart
	 * 
	 * @return Multidimensional ArrayList of String sales data
	 * @throws RemoteException
	 */
	ArrayList<ArrayList<String>> getSaleLineChartData() throws RemoteException;
	
	/**
	 * This method returns products instance going out of stock soon
	 * 
	 * @return Multidimensional ArrayList of Product going out of stock
	 * @see bll.Product
	 * @throws RemoteException
	 */
	ArrayList<Product> getGoingOutOfStockData() throws RemoteException;
}
