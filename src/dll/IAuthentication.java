package dll;

import java.rmi.Remote;
import java.rmi.RemoteException;

import bll.Auth;


/**
 * IAuthentication.java
 * Interface for authentication related method
 * @author anilk
 *
 */
public interface IAuthentication extends Remote{

	/**
	 * Authenticate's user Checking username and password
	 * @see bll.Auth
	 * @param auth Auth Object 
	 * @return boolean value if authenticated
	 * @throws RemoteException
	 */
	Boolean login(Auth auth) throws RemoteException;
	
	
	/**
	 * Reset's User password and send to email service provided by SendGrid Email Service
	 * @see services.SendGridEmailService
	 * @param email User Email
	 * @param userId User Id
	 * @return boolean value on password reset
	 * @throws RemoteException
	 */
	Boolean passwordReset(String email, String userId) throws RemoteException;
}
