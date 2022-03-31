package uiController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import bll.Category;
import bll.Product;
import bll.User;
import bll.Vendor;
import dll.IProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import services.AlertService;
import services.RMIServices;
import utils.Helpers;

/**
 * Controller for AddStock.fxml
 * @author anilk
 *
 */
public class AddStockController implements Initializable {
	//FXML properties
	@FXML
	private TableView itemsTableView;
	@FXML
	private ChoiceBox supplierChoiceBox;
	@FXML
	private TableColumn<Product, Integer> itemIdColumn;
	@FXML
	private TableColumn<Product, String> itemNameColumn;
	@FXML
	private TableColumn<Product, Integer> quantityColumn;
	@FXML
	private TextField categoryNameField;
	@FXML
	private TextField invoiceTextField;
	
	//Properties
	private ArrayList<Product> fetchedProducts;
	private ArrayList<Vendor> fetchedSuppliers;

	/**
	 * AddCategoryButton event handler
	 * {@inheritDoc}
	 * @param e
	 * @throws IOException
	 */
	@FXML
	private void AddCategoryBtn(Event e) throws IOException {
		String category_name = categoryNameField.getText();
		
		if(category_name != null) {
			AlertService.showConfirmationAlert("Adding Category : " + category_name, "Are you sure?")
			.filter(response -> response == ButtonType.OK)
			.ifPresent(response -> {
				try {
					RMIServices.getInstance().saveCategory(new Category(category_name));
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			});
		}
		
	}

	/**
	 * CompleteButton Action event handler 
	 * {@inheritDoc}
	 * @param e
	 * @throws IOException
	 */
	@FXML
	private void onCompleteButtonAction(ActionEvent e) throws IOException {
		ArrayList<Product> products = new ArrayList<Product>(this.itemsTableView.getItems());		
		
		AlertService.showConfirmationAlert("Adding Stock", "Are you sure?")
		.filter(response -> response == ButtonType.OK)
		.ifPresent(response -> {
			try {
				Boolean added = RMIServices.getInstance().addStock(products);
				if(added) {
					this.resetProductTable();
					this.loadProductsTable();
					this.supplierChoiceBox.getSelectionModel().select(0);
					this.invoiceTextField.setText("");
				} else {
					
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		});
		

	}



	/**
	 * fetch All Products with rmi services
	 */
	private void fetchProducts() {
		try {
			this.fetchedProducts = RMIServices.getInstance().fetchProducts();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fetch vendor list using rmi services
	 */
	private void fetchSuppliers() {
		try {
			this.fetchedSuppliers = RMIServices.getInstance().fetchAllVendors();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize AddStockController
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.fetchProducts();
		this.fetchSuppliers();
		this.initColumn();
		this.loadProductsTable();
		this.loadSupplierChoiceBox();
	}

	/**
	 * Initialize TableView Columns 
	 */
	private void initColumn() {
		itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
	}

	/**
	 * Loads Products Table
	 */
	private void loadProductsTable() {
		this.fetchedProducts.forEach(action -> {
			action.setQuantity(0);
		});
		this.itemsTableView.getItems().addAll(this.fetchedProducts);

		this.itemsTableView.setEditable(true);
		this.quantityColumn
				.setCellFactory(TextFieldTableCell.<Product, Integer>forTableColumn(new StringConverter<Integer>() {
					@Override
					public String toString(Integer integer) {
						return integer.toString();
					}

					@Override
					public Integer fromString(String string) {
						return Integer.valueOf(string);
					}
				}));
		this.quantityColumn.setOnEditCommit((e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setQuantity(e.getNewValue());
		}));
	}

	/**
	 * Load Supplier Choice Box
	 */
	private void loadSupplierChoiceBox() {
		this.supplierChoiceBox.setItems(FXCollections.observableArrayList(this.fetchedSuppliers));
		this.supplierChoiceBox.getSelectionModel().select(0);
	}
	
	/**
	 * reset itemsTableView
	 */
	private void resetProductTable() {
		this.itemsTableView.getItems().clear();
	}

}
