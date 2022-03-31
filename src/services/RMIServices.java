package services;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import bll.Auth;
import bll.Category;
import bll.Product;
import bll.Transaction;
import bll.Vendor;
import dll.IAuthentication;
import dll.IDashboard;
import dll.IProduct;
import dll.ITransaction;
import dll.IVendor;

/**
 * This class is for client side rmi service
 * 
 * Singleton pattern is used for limiting to  only one instance of this class
 * 
 * @author anilk
 *
 */
public class RMIServices {

	private IProduct prod;
	private IAuthentication auth;
	private ITransaction trans;
	private IDashboard dash;
	private IVendor vendor;

	private static RMIServices instance;

	/**
	 * Private RMIServices constructor of initializing RMI connections
	 * 
	 */
	private RMIServices() {
		try {
			for (String name : Naming.list("rmi://localhost/")) {
				System.out.println(name);
			}
			this.prod = (IProduct) Naming.lookup("rmi://localhost/Product");
			this.auth = (IAuthentication) Naming.lookup("rmi://localhost/Auth");
			this.trans = (ITransaction) Naming.lookup("rmi://localhost/Transaction");
			this.vendor = (IVendor) Naming.lookup("rmi://localhost/Vendor");

			this.dash = (IDashboard) Naming.lookup("rmi://localhost/Dashboard");
		} catch (RemoteException re) {
			System.out.println();
			System.out.println("RemoteException");
			System.out.println(re);
		} catch (ArithmeticException ae) {
			System.out.println("java.lang.ArithematicException");
			System.out.println(ae);
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	};

	/**
	 * Return instance
	 * 
	 * if present return that instance
	 * 
	 * @return RMIServices instance
	 */
	public static RMIServices getInstance() {
		if (instance == null) {
			instance = new RMIServices();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param vendor Instance of Vendor
	 * @return
	 * @throws RemoteException
	 */
	public boolean VendorReg(Vendor vendor) throws RemoteException {
		return this.vendor.VendorReg(vendor);
	}
	
	/**
	 * 
	 * @param auth Instance of Auth
	 * @return
	 * @throws RemoteException
	 */
	public boolean login(Auth auth) throws RemoteException {
		return this.auth.login(auth);
	}
	
	
	/**
	 * 
	 * @param email Email
	 * @param userId User Id
	 * @return
	 * @throws RemoteException
	 */
	public boolean resetPassword(String email, String userId) throws RemoteException {
		return this.auth.passwordReset(email, userId);
	}

	/**
	 * 
	 * @return ArrayList of Product on stock
	 * @throws RemoteException
	 */
	public ArrayList<Product> fetchProductsOnStock() throws RemoteException {
		return this.prod.getAllProductsOnStock();
	}

	/**
	 * 
	 * @return ArrayList of Product
	 * @throws RemoteException
	 */
	public ArrayList<Product> fetchProducts() throws RemoteException {
		return this.prod.getAllProducts();
	}

	/**
	 * 
	 * @return ArrayList of Category
	 * @throws RemoteException
	 */
	public ArrayList<Category> fetchCategories() throws RemoteException {
		return this.prod.getAllCategory();
	}

	/**
	 * 
	 * @return ArrayList of Transaction
	 * @throws RemoteException
	 */
	public ArrayList<Transaction> fetchAllTransaction() throws RemoteException {
		return this.trans.getAllTransaction();
	}
	
	/**
	 * 
	 * @return ArrayList of Vendor
	 * @throws RemoteException
	 */
	public ArrayList<Vendor> fetchAllVendors() throws RemoteException {
		return this.vendor.VendorList();
	}

	/**
	 * 
	 * @param localDateTime LocalDateTime instance
	 * @return ArrayList of Transaction on specific date
	 * @throws RemoteException
	 */
	public ArrayList<Transaction> fetchAllTransactionOnDate(LocalDateTime localDateTime) throws RemoteException {
		return this.trans.getAllTransactionOnDate(localDateTime);
	}

	/**
	 * 
	 * @param transId Transaction Id
	 * @return ArrayList of Transaction details i.e. Products Details
	 * @throws RemoteException
	 */
	public ArrayList<Product> getTransactionDetail(Integer transId) throws RemoteException {
		return this.trans.getTransactionDetailById(transId);
	}

	/**
	 * 
	 * @param items ArrayList of Products
	 * @param totalAmount Total Amount
	 * @param paid Paid Amount
	 * @return boolean on checkout complete
	 * @throws RemoteException
	 */
	public boolean checkout(ArrayList<Product> items, double totalAmount, double paid) throws RemoteException {
		return this.trans.checkout(items, totalAmount, paid);
	}

	/**
	 * 
	 * @param items ArrayList of Product
	 * @return boolean on add stock complete
	 * @throws RemoteException
	 */
	public boolean addStock(ArrayList<Product> items) throws RemoteException {
		return this.trans.addStock(items);
	}
	
	/**
	 * 
	 * @param product Instance of Product
	 * @return
	 * @throws RemoteException
	 */
	public boolean saveProduct(Product product)
			throws RemoteException {
		return this.prod.saveProduct(product);
	}


	/**
	 * 
	 * @param category Instance of Category
	 * @return
	 * @throws RemoteException
	 */
	public boolean saveCategory(Category category) throws RemoteException {
		return this.prod.saveCategory(category);
	}

	/**
	 * 
	 * @param id Transaction Id
	 * @return on deleted Transaction this includes restocking products
	 * @throws RemoteException
	 */
	public boolean deleteTransaction(Integer id) throws RemoteException {
		return this.trans.deleteTransaction(id);
	}
	
	/**
	 * 
	 * @param id Product Id
	 * @return boolean on deleted Product
	 * @throws RemoteException
	 */
	public boolean deleteProduct(String id) throws RemoteException {
		return this.prod.deleteProduct(id);
	}
	
	/**
	 * 
	 * @param id Vendor Id
	 * @return Boolean on vendor deleted
	 * @throws RemoteException
	 */
	public boolean deleteVendor(String id) throws RemoteException {
		return this.vendor.deleteVendor(id);
	}
	
	

	// Dashboard
	
	/**
	 * 
	 * @return ArrayList of quick details used in dashboard
	 * @throws RemoteException
	 */
	public ArrayList<String> getQuickDetails() throws RemoteException {
		return this.dash.getQuickDetails();
	}

	/**
	 * 
	 * @return Multidimensional ArrayList of sales data used for pie chart in dashboard
	 * @throws RemoteException
	 */
	public ArrayList<ArrayList<String>> getSalesPieData() throws RemoteException {
		return this.dash.getSalePieChartData();
	}

	/**
	 * 
	 * @return Multidimensional ArrayList of sales data used for line chart in dashboard
	 * @throws RemoteException
	 */
	public ArrayList<ArrayList<String>> getSalesLineData() throws RemoteException {
		return this.dash.getSaleLineChartData();
	}

	/**
	 * 
	 * @return ArrayList of Products going out of stock soon
	 * @throws RemoteException
	 */
	public ArrayList<Product> getGoingOutOfStockData() throws RemoteException {
		return this.dash.getGoingOutOfStockData();
	}

}
