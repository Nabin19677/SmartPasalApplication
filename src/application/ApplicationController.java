package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.github.fxrouter.FXRouter;
import java.io.IOException;	

/**
 * Controller class for Application.fxml
 *
 * @author anilk
 *
 */
public class ApplicationController implements Initializable {

	/**
	 * Handle Login Event
	 * @param event EventHandler
	 * @throws IOException
	 */
	@FXML
    private void handleLoginButton(ActionEvent event) throws IOException { 
        FXRouter.goTo("login"); 
    }
	
	/**
	 * {@inheritDoc}
 	 * Initialize ApplicationController
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
