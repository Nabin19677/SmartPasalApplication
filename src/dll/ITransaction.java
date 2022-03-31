package dll;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import bll.Product;
import bll.Transaction;

/**
 * ITransaction.java
 * Interface for method related to Transactions
 * @author anilk
 *
 */
public interface ITransaction extends Remote{

	/**
	 * checkout items and return boolean if ok
	 * @param items ArrayList of Product
	 * @see bll.Product
	 * @param totalAmount Total Amount of Transaction
	 * @param paid Total paid Amount
	 * @return boolean on checkout
	 * @throws RemoteException
	 */
	boolean checkout(ArrayList<Product> items, double totalAmount, double paid) throws RemoteException;
	
	/**
	 * Add Products to stock
	 * @param items ArrayList of Product
	 * @see bll.Product
	 * @return boolean true on added stock
	 * @throws RemoteException
	 */
	boolean addStock(ArrayList<Product> items) throws RemoteException;
	
	/**
	 * Delete transaction and restock product
	 * returns boolean if ok
	 * @param transId Transaction Id
	 * @return boolean true on deleted transaction
	 * @throws RemoteException
	 */
	boolean deleteTransaction(Integer transId) throws RemoteException;
	
	/**
	 * Gets transaction detail including product detail
	 * @param transId Transaction Id
	 * @return ArrayList of Product i.e. Transaction Detail by id
	 * @see bll.Product
	 * @throws RemoteException
	 */
	ArrayList<Product> getTransactionDetailById(Integer transId) throws RemoteException;
	
	/**
	 * Gets all transaction
	 * @return ArrayList of Transaction
	 * @see bll.Transaction
	 * @throws RemoteException
	 */
	ArrayList<Transaction> getAllTransaction() throws RemoteException;
	
	/**
	 * Gets Transaction of date
	 * @param localDateTime
	 * @return ArrayList of Transaction on specified date
	 * @see bll.Transaction
	 * @throws RemoteException
	 */
	ArrayList<Transaction> getAllTransactionOnDate(LocalDateTime localDateTime) throws RemoteException;
}
