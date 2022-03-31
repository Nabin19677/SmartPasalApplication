package services;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * This class contains Alert related methods
 * @author anilk
 *
 */
public class AlertService {
	private static String titleString = "Smart Pasal";
	
	/**
	 * Shows Confirmation Alert
	 * @param headerText Header Text
	 * @param contentText Content
	 * @return Optional<ButtonType> which can be filtered for alert respose
	 */
	public static Optional<ButtonType> showConfirmationAlert(String headerText, String contentText) {
		Alert dg = new Alert(Alert.AlertType.CONFIRMATION);
		dg.setTitle(titleString);
		dg.setHeaderText(headerText);
		dg.setContentText(contentText);
		return dg.showAndWait();
	}
	
	/**
	 * Shows Confirmation Alert
	 * @see AlertService.showConfirmationAlert(String, String)
	 * @param headerText Header Test
	 * @param contentText Content
	 * @param title Window Title
	 * @return Optional<ButtonType> which can be filtered for alert respose
	 */
	public static Optional<ButtonType> showConfirmationAlert(String headerText, String contentText, String title) {
		Alert dg = new Alert(Alert.AlertType.CONFIRMATION);
		dg.setTitle(title);
		dg.setHeaderText(headerText);
		dg.setContentText(contentText);
		return dg.showAndWait();
	}
	
	/**
	 * Shows Warning Alert
	 * @param headerText Header Test
	 * @param contentText Content
	 * @param title Window Title
	 * @return Optional<ButtonType> which can be filtered for alert respose
	 */
	public static Optional<ButtonType> showWarningAlert(String headerText, String contentText, String title) {
		Alert dg = new Alert(Alert.AlertType.WARNING);
		dg.setTitle(title);
		dg.setHeaderText(headerText);
		dg.setContentText(contentText);
		return dg.showAndWait();
	}
}
