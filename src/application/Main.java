package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

import com.github.fxrouter.FXRouter;


public class Main extends Application {
	
	/**
	 * starting javafx application with primary stage and implementing FXRouter for routing
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setResizable(false); 
		primaryStage.setX(-4); // Set X location -4
		primaryStage.setY(0); // Set Y location 0
        FXRouter.bind(this, primaryStage, "SmartPasal",1920,1080); // 
        FXRouter.setAnimationType("fade", 600); // set animation to fade in 600 milliseconds
        
        //Routes
        FXRouter.when("start", "Application.fxml");
        FXRouter.when("login", "../view/Login.fxml");
        FXRouter.when("forgetPassword", "../view/ForgetPassword.fxml");
        FXRouter.when("home", "../view/Home.fxml");
        //Routes End here
        FXRouter.goTo("login"); //starting first scene
        
        String css = this.getClass().getResource("./Application.css").toExternalForm(); //getting css Application.css
        primaryStage.getScene().getStylesheets().add(css); //Adding css to the primaryStage
	}
	
	/**
	 * Application starts here
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
