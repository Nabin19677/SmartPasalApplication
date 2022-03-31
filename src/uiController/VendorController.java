package uiController;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bll.Category;
import bll.Product;
import bll.Vendor;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import services.AlertService;
import services.RMIServices;

/**
 * Controller Class for Vendor.fxml
 * @author anilk
 *
 */
public class VendorController implements Initializable{
	//Properties
	private ArrayList<Vendor> fetchedVendorList;
	private int selectedVendorRow = 0;
	
	//FXML Properties
	@FXML
	private TextField vendorNameField;
	@FXML
	private TextField addressField;	
	@FXML
	private TextField emailField;	
	@FXML
	private TextField phoneField;	
	@FXML
	private TableView<Vendor> vendorTableView;
	@FXML
	private TableColumn nameColumn;
	@FXML
	private TableColumn addressColumn;
	@FXML
	private TableColumn emailColumn;
	@FXML
	private TableColumn phoneColumn;
	
	/**
	 * CreateVendorButton MouseClicked Event Handler
	 * @param e
	 * @throws IOException
	 */
	@FXML
	private void onacreateVendorButtonMouseClicked(Event e) throws IOException{
		String vendorName = vendorNameField.getText();
		String address = addressField.getText();
		String email = emailField.getText();
		String phone = phoneField.getText();
		
		
		AlertService.showConfirmationAlert("Adding New Vendor : ", "Are you sure?")
		.filter(response -> response == ButtonType.OK)
		.ifPresent(response -> {
		try {
		       RMIServices.getInstance().VendorReg(new Vendor(vendorName, address, email, phone));
		       this.fetchVendorList();
		       this.loadVendorTable();
		   }catch (RemoteException re){
		       System.out.println();
		       System.out.println("RemoteException");
		       System.out.println(re);
		   }
		});	
	}
	
	@FXML
	private void onDeleteMouseReleased(Event e) throws IOException{
		AlertService.showWarningAlert("Deleting Vendor : ", "Are you sure?", "Vendor")
		.filter(response -> response == ButtonType.OK)
		.ifPresent(response -> {
		String selectedVendorId;
		if(this.selectedVendorRow >= 0 ){
			selectedVendorId = this.vendorTableView.getItems().get(this.selectedVendorRow).getVendorId();
			try {	
				boolean deleted = RMIServices.getInstance().deleteVendor(selectedVendorId);
				if(deleted) {
					this.fetchVendorList();
					this.loadVendorTable();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		});	
	}
	
	/**
	 * Initialize VendorController
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setValidationsOnTextField();
		this.initColumns();
		this.fetchVendorList();
		this.loadVendorTable();
		this.vendorTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	this.selectedVendorRow = this.vendorTableView.getSelectionModel().getSelectedIndex();
		    }
		});
	}
	
	/**
	 * fetch vendor list using rmi services
	 */
	private void fetchVendorList() {
		try {
			this.fetchedVendorList = RMIServices.getInstance().fetchAllVendors();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * initialize tableview columns
	 */
	private void initColumns() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
	}
	
	/**
	 * load vendor table
	 */
	private void loadVendorTable() {
		this.resetVendorTable();
		this.vendorTableView.getItems().addAll(this.fetchedVendorList);
	}
	
	/**
	 * Set Validation Regex Pattern on each Text Field
	 */
	private void setValidationsOnTextField() {
		this.phoneField.setTextFormatter(new TextFormatter<>(change -> (change.getControlNewText().matches("[0-9]{0,10}")) ? change : null));
	}
	
	/**
	 * reset vendor table
	 */
	private void resetVendorTable() {
		this.vendorTableView.getItems().clear();
	}

}
