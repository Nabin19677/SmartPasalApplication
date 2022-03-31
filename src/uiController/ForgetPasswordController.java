package uiController;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.github.fxrouter.FXRouter;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.AlertService;
import services.RMIServices;
import utils.Helpers;

/**
 * Controller for ForgetPassword.fxml
 * @author anilk
 *
 */
public class ForgetPasswordController implements Initializable{

	 @FXML
	 private TextField emailField;

	 @FXML
	 private TextField userIdField;
	 
	 @FXML 
	 private Label errorLabel;
	    
	/**
	 * ResetButton Clicked Event Handler
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void onResetButtonClicked(ActionEvent event) throws IOException{
		
		
		AlertService.showConfirmationAlert("Resetting your password? Are you sure?","Check your email")
		.filter(response -> response == ButtonType.OK)
		.ifPresent(response -> {
			boolean reset = false;
			try {
				reset = RMIServices.getInstance().resetPassword(this.emailField.getText(), this.userIdField.getText() );
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(reset) {
				try {
					FXRouter.goTo("login");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
	       	 	this.emailField.clear();
	       	 	this.emailField.requestFocus();
				errorLabel.setText("Email or UserId \ndidn't match");
			}
		});
		
		
		

	}
	
	/**
	 * ForgetPasswordButton MouseClicked Event Handler
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void onCancelMouseClicked(Event event) throws IOException {
		FXRouter.goTo("login");
		System.out.print("forget it");
	}
	
	/**
	 * Initialize ForgetPasswordController
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
	}

}
