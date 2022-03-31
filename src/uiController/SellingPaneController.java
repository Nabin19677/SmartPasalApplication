package uiController;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.github.fxrouter.FXRouter;

import application.Environment;
import bll.Product;
import dll.IAuthentication;
import dll.IProduct;
import dll.ITransaction;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import services.AlertService;
import services.RMIServices;
import utils.Helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Controller Class for SellingPane.fxml
 * @author anilk
 *
 */
public class SellingPaneController implements Initializable {
	//FXML properties
	@FXML
	private TextField itemIdField;
	@FXML
	private TextField itemNameField;
	@FXML
	private TextField quantityField;
	@FXML
	private TextField rateField;
	@FXML
	private TextField discountField;
	@FXML
	private TextField totalAmountField;
	@FXML
	private TextField paidField;
	@FXML
	private Label totalPriceLabel1;
	@FXML
	private Label totalPriceLabel2;
	@FXML
	private Label availableItemQuantityLabel;
	@FXML
	private Label returnLabel;
	@FXML
	private Label errorLabel;
	@FXML
	private TableView<Product> itemsTableView;
	@FXML
	private TableColumn itemIdColumn;
	@FXML
	private TableColumn itemNameColumn;
	@FXML
	private TableColumn quantityColumn;
	@FXML
	private TableColumn rateColumn;
	@FXML
	private TableColumn totalItemPriceColumn;
	//FXML Ends
	
	//Properties
	private double totalItemPrice;
	private double totalPrice;
	private double totalAmount;
	private double returnAmount;
	private int selectedItemQuantity;
	private ArrayList<Product> fetchedProducts;
	
	
	/**
	 * Quantity Text Field Key Released Event Handler
	 * @param e
	 * @throws IOException
	 */
	@FXML
	private void onQuantityKeyReleased(Event e) throws IOException {
		if(!this.checkQuantity()) {
			this.quantityField.deletePreviousChar();
		} else {
			totalItemPriceUpdate();
		}
	}
	
	/**
	 * Rate Text Field Key Released Event Handler
	 * @param e
	 * @throws IOException
	 */
	@FXML
	private void onRateKeyReleased(Event e) throws IOException  {}
	
	/**
	 * ItemId Text Field Key Released Event Handler
	 * @param e
	 * @throws IOException
	 */
	@FXML 
	private void onItemIdKeyReleased(Event e) throws IOException{
		String itemId = itemIdField.getText();
		for(Product product: fetchedProducts) {
			if(product.getProductId().equals(itemId)) {
				this.itemNameField.setText(product.getProductName());
				this.itemNameField.setDisable(true);
				this.rateField.setText(String.valueOf(product.getRate()));
				this.rateField.setDisable(true);
				this.selectedItemQuantity = product.getQuantity();
				this.availableItemQuantityLabel.setText(String.valueOf(product.getQuantity()));
			}
		}
		
		if(this.itemIdField.getText().isEmpty()) {
			this.resetItemForm();
		}
	}
	
	/**
	 * Discount Text Field Key Released Event Handler
	 * @param e
	 * @throws IOException
	 */
	@FXML
	private void onDiscountKeyRelease(Event e) throws IOException  {
		addDiscount();
	}
	
	/**
	 * Paid Text Field Key Released Event Handler
	 * @param e
	 * @throws IOException
	 */
	@FXML 
	private void onPaidKeyRelease(Event e) throws IOException {
		calculateReturn();
	}
	
	/**
	 * AddItemButton MouseClicked Event Handler
	 * @param e
	 * @throws IOException
	 */
	@FXML
	private void onaddItemButtonMouseClicked(Event e) throws IOException{
		
		if(itemIdField.getText() == null || itemIdField.getText().trim().isEmpty()) {
       	 	this.itemIdField.clear();
       	 	this.itemIdField.requestFocus();
			return;
		}
		
		if(itemNameField.getText() == null || itemNameField.getText().trim().isEmpty()) {
       	 	this.itemNameField.clear();
			return;
		}
		
		if(rateField.getText() == null || rateField.getText().trim().isEmpty()) {
       	 	this.rateField.clear();
			return;
		}
		
		if(quantityField.getText() == null || quantityField.getText().trim().isEmpty()) {
       	 	this.quantityField.clear();
       	 	this.quantityField.requestFocus();
			return;
		}
			
		itemsTableView.getItems().add(new Product(itemIdField.getText(),itemNameField.getText(),Integer.parseInt(quantityField.getText()),Double.parseDouble(rateField.getText()),totalItemPrice));
		this.resetItemForm();
		this.totalPriceUpdate();	
	}
	
	/**
	 * CheckoutButton MouseClicked Event Handler
	 * @param e
	 * @throws IOException
	 */
	@FXML
	private void onCheckoutButtonMouseClicked(Event e) throws IOException{
		if(this.itemsTableView.getItems().isEmpty()) {
			this.errorLabel.setText("No items added!");
			this.itemIdField.clear();
       	 	this.itemIdField.requestFocus();
			return;
		}
		AlertService.showConfirmationAlert("Checking Out","Are you sure?", "Selling Pane")
			.filter(response -> response == ButtonType.OK)
			.ifPresent(response -> {
				ArrayList<Product> items = new ArrayList<Product>(this.itemsTableView.getItems());
				double totalAmount = this.totalAmount;
				double paid = Integer.parseInt(paidField.getText());

				try {
		           boolean ok = RMIServices.getInstance().checkout(items, totalAmount, paid);
		           if(ok) {
		        	   this.reset();
		           } else {
		        	   
		           }
		       }catch (RemoteException re){
		           System.out.println();
		           System.out.println("RemoteException");
		           System.out.println(re);
		       }
			});

	}
	
	/**
	 * RemoveItem ButtonReleased Event Handler
	 * @param e
	 */
	@FXML
	private void onRemoveItemButtonReleased(Event e) {
		if(!this.itemsTableView.getItems().isEmpty()) {
			if(this.itemsTableView.getSelectionModel().getSelectedItem() != null) {
				AlertService.showConfirmationAlert("Removing added item","Are you sure?")
				.filter(response -> response == ButtonType.OK)
				.ifPresent(response -> {
					this.itemsTableView.getItems().remove(this.itemsTableView.getSelectionModel().getSelectedIndex());
					this.totalPriceUpdate();
				});
			} else {
				this.errorLabel.setText("Select item of table to remove");
			}
		} else {
			this.errorLabel.setText("Please add items to the table");
		}	
	}
	
	/**
	 * total Item price update and update related label
	 */
	private void totalItemPriceUpdate() {
			totalItemPrice = (Double.parseDouble(quantityField.getText())) * (Double.parseDouble(rateField.getText()));
			totalPriceLabel1.setText(String.valueOf(totalItemPrice));
	}
	
	/**
	 * total price update and update related label
	 */
	private void totalPriceUpdate() {
		double total = 0;
		ArrayList<ArrayList<String>> items = Helpers.getTableViewValues(itemsTableView);
		for (ArrayList<String> list : items)
		{
	        total += Double.parseDouble(list.get(4)); // fifth is totalItemPrice 
		}
		this.totalPrice = total;
		this.totalAmount = total;
		this.totalPriceLabel2.setText(String.valueOf(totalPrice));
		this.totalAmountField.setText(String.valueOf(this.totalAmount));
	}
	
	/**
	 * adding discount
	 */
	private void addDiscount() {
		this.totalAmount = this.totalPrice - (((Double.parseDouble(this.discountField.getText()))/100) * this.totalPrice);
		this.totalAmountField.setText(String.valueOf(this.totalAmount));
		this.calculateReturn();
	}
	
	/**
	 * calculate return
	 */
	private void calculateReturn() {
		double returnAmount = 0;
		try {
			returnAmount = (Double.parseDouble(paidField.getText())) - totalAmount;
		}catch(Exception e) {
			System.out.println(e);
		}
		this.returnLabel.setText(String.valueOf(returnAmount));
	}
	
	
	//Checkers
	
	/**
	 * Check Quantity Text Field
	 * @return boolean if ok
	 */
	private boolean checkQuantity() {
		if(Integer.parseInt(this.quantityField.getText()) <= this.selectedItemQuantity) return true;
		return false;
	}
	
	/**
	 * Check Return Text Field
	 * @return boolean if ok
	 */
	private boolean checkReturn() {
		if(Integer.parseInt(this.paidField.getText()) >= this.totalAmount) return true;
		return false;
	}
	
	/**
	 * Check Discount Text Field
	 * @return boolean if ok
	 */
	private boolean checkDiscount() {
		if(Integer.parseInt(this.discountField.getText()) <= 50) return true;
		return false;
	}
	
	
	/**
	 * Reset Item Form
	 */
	private void resetItemForm() {
		this.itemIdField.setText("");
		this.itemNameField.setText("");
		this.quantityField.setText("");
		this.rateField.setText("");
		this.totalItemPrice = 0;
		this.availableItemQuantityLabel.setText("0");
		this.totalPriceLabel1.setText(String.valueOf(0));
	}
	
	/**
	 * Reset page
	 */
	private void reset() {
		this.resetItemForm();
		this.itemsTableView.getItems().clear();
		this.totalPriceUpdate();
		this.paidField.clear();
		this.returnLabel.setText("0.00");
		this.discountField.clear();
	}

	/**
	 * Initialize SellingPaneController
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.setValidationsOnTextField();
		this.fetchProducts();
		this.initTable();
	}
	
	/**
	 * Fetch products using RMI services
	 */
	private void fetchProducts() {
		try {
			this.fetchedProducts = RMIServices.getInstance().fetchProductsOnStock();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initializing table
	 */
	private void initTable() {
		itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
		totalItemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalProductPrice"));
	}
	
	/**
	 * Set Validation Regex Pattern on each Text Field
	 */
	private void setValidationsOnTextField() {
		this.itemIdField.setTextFormatter(new TextFormatter<>(change -> (change.getControlNewText().matches("([1-9][0-9]*)?")) ? change : null));
		this.quantityField.setTextFormatter(new TextFormatter<>(change -> (change.getControlNewText().matches("([1-9][0-9]*)?")) ? change : null));
		this.discountField.setTextFormatter(new TextFormatter<>(change -> (change.getControlNewText().matches("[0-9]{0,2}")) ? change : null));
		this.paidField.setTextFormatter(new TextFormatter<>(change -> (change.getControlNewText().matches("([1-9][0-9]*)?")) ? change : null));
	}
	

}
