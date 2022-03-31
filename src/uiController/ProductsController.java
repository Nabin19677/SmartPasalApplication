package uiController;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import bll.Category;
import bll.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.util.converter.*;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import services.AlertService;
import services.RMIServices;
import utils.Helpers;

/**
 * Controller class for Products.fxml
 * @author anilk
 *
 */
public class ProductsController implements Initializable {
	//Properties
	private ArrayList<Category> fetchedCategories;
	private ArrayList<Product> fetchedProducts;
	private int selectedProductRow = 0;
	
	private String product_name;
	private int product_quantity;
	private double product_rate;
	private Category category; 
	
	//FXML Properties
	@FXML
	private ChoiceBox categoryChoiceBox;
	@FXML
	private TextField productName;
	@FXML
	private TextField productQuantity;
	@FXML
	private TextField productRate;
	@FXML
	private TableView<Product> productsTableView;
	@FXML
	private TableColumn idColumn;
	@FXML
	private TableColumn nameColumn;
	@FXML
	private TableColumn quantityColumn;
	@FXML
	private TableColumn rateColumn;
	
	
	
	/**
	 * This is event handler for SaveButton onClicked
	 * @param e
	 * @throws IOException
	 */
	@FXML
	private void onSaveBtnClicked(Event e) throws IOException{
		this.category = (Category) categoryChoiceBox.getSelectionModel().getSelectedItem();
		this.product_name = productName.getText();
		this.product_quantity = Integer.parseInt(productQuantity.getText());
		this.product_rate = Integer.parseInt(productRate.getText());
		
		
		
		AlertService.showConfirmationAlert("Adding new Product", "Are you sure?")
		.filter(response -> response == ButtonType.OK)
		.ifPresent(response -> {
			try {
				RMIServices.getInstance().saveProduct(new Product(this.product_name,this.product_quantity, this.product_rate, category.getCategoryId()));
				this.resetAddProductForm();
				this.fetchProducts();
				this.loadProductsTable();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});
	}
	
	/**
	 * On Delete Mouse Released of Product
	 * @param e Event
	 * @throws IOException
	 */
	@FXML
	private void onDeleteMouseReleased(Event e) throws IOException{
		AlertService.showWarningAlert("Deleting Product : ", "Are you sure?", "Product")
		.filter(response -> response == ButtonType.OK)
		.ifPresent(response -> {
		String selectedProductId;
		if(this.selectedProductRow >= 0 ){
			selectedProductId = this.productsTableView.getItems().get(this.selectedProductRow).getProductId();
			try {	
				boolean deleted = RMIServices.getInstance().deleteProduct(selectedProductId);
				if(deleted) {
					this.fetchProducts();
					this.loadProductsTable();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		});	
	}
	
	/**
	 * Initialize ProductsController class
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.fetchCategories();
		this.fetchProducts();
		ObservableList<Category> ofetchedCategories = FXCollections.observableArrayList(this.fetchedCategories);
        this.categoryChoiceBox.setItems(ofetchedCategories);
        this.categoryChoiceBox.getSelectionModel().select(0);
        this.initTable();
        
        this.productsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	this.selectedProductRow = this.productsTableView.getSelectionModel().getSelectedIndex();
		    }
		});
        
	}
	
	/**
	 * initialize table
	 */
	private void initTable() {
		this.setValidationsOnTextField();
		this.initColumns();
		this.loadProductsTable();
	}
	
	/**
	 * initialize table view columns
	 */
	private void initColumns() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
	}
	
	/**
	 * load products table
	 */
	private void loadProductsTable() {
		this.resetProductsTable();
		this.productsTableView.getItems().addAll(this.fetchedProducts);
	}
	
	/**
	 * Fetch Products using rmi services
	 */
	private void fetchProducts() {
		try {
			this.fetchedProducts = RMIServices.getInstance().fetchProductsOnStock();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fetch Categories using rmi services
	 */
	private void fetchCategories() {
		try {
			this.fetchedCategories = RMIServices.getInstance().fetchCategories();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set Validation Regex Pattern on each Text Field
	 */
	private void setValidationsOnTextField() {
		this.productQuantity.setTextFormatter(new TextFormatter<>(change -> (change.getControlNewText().matches("([1-9][0-9]*)?")) ? change : null));
		this.productRate.setTextFormatter(new TextFormatter<>(change -> (change.getControlNewText().matches("([1-9][0-9]*\\.?[0-9]{0,2})?")) ? change : null));
	}
	
	/**
	 * reset ProductsTableView TableView
	 */
	private void resetAddProductForm() {
		this.productName.clear();
		this.productQuantity.clear();
		this.productRate.clear();
		this.categoryChoiceBox.getSelectionModel().select(0);
	}
	private void resetProductsTable() {
		this.productsTableView.getItems().clear();
	}
	
}
