package uiController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.RMIServices;
import services.APIServices;

import com.github.fxrouter.FXRouter;

import application.Environment;
import bll.Auth;
import bll.User;
import dll.IAuthentication;
import dll.IProduct;

/**
 * Controller class for Login.fxml
 * @author anilk
 *
 */
public class LoginController implements Initializable{
	
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	@FXML private Label errorLabel;
	
	/**
	 * LoginButton OnAction Event Handler
	 * {@inheritDoc}
	 * @param event
	 * @throws IOException
	 */
	@FXML private void onLoginButtonClicked(ActionEvent event) throws IOException{
		if(usernameField.getText() == null || usernameField.getText().trim().isEmpty()) {
			errorLabel.setText("Please enter username.");
       	 	this.usernameField.clear();
       	 	this.usernameField.requestFocus();
			return;
		}
		
		if(passwordField.getText() == null || passwordField.getText().trim().isEmpty()) {
			errorLabel.setText("Please enter password.");
       	 	this.passwordField.clear();
       	 	this.passwordField.requestFocus();
			return;
		}
		
		String username = usernameField.getText();
		String password = passwordField.getText();
		
		 try {
             Boolean userOk = RMIServices.getInstance().login(new Auth(username, password));
             if(userOk) {
            	 System.out.println("UserOK");
            	 System.out.println(userOk);
            	 Environment.userName = username;
         		 FXRouter.goTo("home");
             } else {
            	 errorLabel.setText("Username or Password \nis not correct");
            	 this.passwordField.clear();
            	 this.passwordField.requestFocus();

             }
         }catch (RemoteException re){
             System.out.println();
             System.out.println("RemoteException");
             System.out.println(re);
         }
	}
	
	/**
	 * ForgetPassword MouseClicked Event Handler
	 * {@inheritDoc}
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void onForgetPasswordMouseClicked(Event event) throws IOException{
		FXRouter.goTo("forgetPassword");
	}
	
	/**
	 * Initialize LoginController
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {}

}
