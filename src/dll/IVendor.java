package dll;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import bll.Vendor;

/**
 * IVendor.java
 * Interface for method related to Vendor
 * @author anilk
 *
 */
public interface IVendor extends Remote{

	/**
	 * Registers Vendor and return boolean if registered
	 * @param Vendor Instance of Vendor Class
	 * @see bll.Vendor
	 * @return boolean on vendor registration
	 * @throws RemoteException
	 */
	boolean VendorReg(Vendor vendor) throws RemoteException;
	
	/**
	 * Gets List of vendor's data
	 * @return ArrayList of Vendor
	 * @see bll.Vendor
	 * @throws RemoteException
	 */
	ArrayList<Vendor> VendorList() throws RemoteException;
	
	/**
	 * Deletes Vendor
	 * @param vendorId Vendor Id
	 * @see bll.Vendor
	 * @return boolean true on Vendor deleted
	 * @throws RemoteException
	 */
	boolean deleteVendor(String vendorId) throws RemoteException;
	
}
