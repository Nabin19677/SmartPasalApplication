package uiController;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import bll.Product;
import bll.Transaction;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.AlertService;
import services.RMIServices;

/**
 * Controller class for Transactions.fxml
 * @author anilk
 *
 */
public class TransactionsController implements Initializable{
	//FXML properties
	@FXML
	private TableView<Transaction> transTableView;
	@FXML
	private TableColumn<Transaction, String> transIdColumn;
	@FXML
	private TableColumn<Transaction, String> totalAmountColumn;
	@FXML
	private TableColumn<Transaction, String> dateTimeColumn;	
	@FXML
	private TableView<Product> productTableView;	
	@FXML
	private TableColumn<Product, String> productIdColumn;
	@FXML
	private TableColumn<Product, String> productNameColumn;
	@FXML
	private TableColumn<Product, Integer> quantityColumn;	
	@FXML
	private DatePicker datePicker;	
	@FXML
	private Button deleteTransactionButton;
	
	//Properties
	private ArrayList<Transaction> fetchedTransaction;
	private ArrayList<Product> fetchedTransactionDetail;
	private int selectedTransactionRow = 0;
	
	/**
	 * onDateChange datepicker Event Handler
	 * @param e
	 */
	@FXML 
	private void onDateChange(ActionEvent e) {
		this.fetchAllTransaction();
		this.loadTransactionTable();
		this.resetProductTable();
	}
	
	/**
	 * DeleteButton Mouse Released Event Handler
	 * @param e
	 */
	@FXML
	private void onDeleteButtonMouseReleased(Event e) {
		AlertService.showWarningAlert("Deleting Transaction", "Are you sure?", "Transaction")
		.filter(response -> response == ButtonType.OK)
		.ifPresent(response -> {
			this.deleteSelectedTransaction();
			this.resetProductTable();
		});
		
	}

	
	/**
	 * Fetch all transaction using rmi services and set to this.fetchedTransation
	 */
	private void fetchAllTransaction() {
		try {
			this.fetchedTransaction = RMIServices.getInstance().fetchAllTransactionOnDate(this.datePicker.getValue().atStartOfDay());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * fetch transaction details with product details  
	 */
	private void getTransactionDetail() {
		int selectedTransId;
		if(this.selectedTransactionRow >= 0 ){
			selectedTransId = Integer.parseInt(this.transTableView.getItems().get(this.selectedTransactionRow).getTransId());
			try {	
				this.fetchedTransactionDetail = RMIServices.getInstance().getTransactionDetail(selectedTransId);
				System.out.println(this.fetchedTransactionDetail);
				this.loadProductTable();	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * Get selected TransactionTableView Row and delete selected transaction from database and restock product
	 */
	private void deleteSelectedTransaction() {
		int selectedTransId;
		if(this.selectedTransactionRow >= 0 ){
			selectedTransId = Integer.parseInt(this.transTableView.getItems().get(this.selectedTransactionRow).getTransId());
			try {	
				boolean deleted = RMIServices.getInstance().deleteTransaction(selectedTransId);
				if(deleted) {
					this.fetchAllTransaction();
					this.loadTransactionTable();
					this.loadProductTable();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * Initialize TransactionsController Class
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDatePicker();
		fetchAllTransaction();
		this.initColumns();
		this.initTable();
		this.deleteTransactionButton.setDisable(true);
	}
	
	/**
	 * init date picker
	 */
	private void initDatePicker() {
		this.datePicker.setValue(LocalDate.now());
	}
	
	/**
	 * init tanstableview tableview
	 */
	private void initTable() {
		loadTransactionTable();
		this.transTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	this.selectedTransactionRow = this.transTableView.getSelectionModel().getSelectedIndex();
		    	this.deleteTransactionButton.setDisable(false);
			    this.getTransactionDetail();
		    }
		});
	}
	
	/**
	 * initialize table view columns
	 */
	private void initColumns() {
		//transId, total, transDateTime, deleteButton
		this.transIdColumn.setCellValueFactory(new PropertyValueFactory<>("transId"));
		this.totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
		this.dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("transDateTime"));
		
		//productId, productName, quantity
		this.productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
		this.productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		this.quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
	}
	
	/**
	 * load transTableVIew
	 */
	private void loadTransactionTable() {
		this.resetTransactionTable(); //reset table before loading....as per date changes
		this.transTableView.getItems().addAll(this.fetchedTransaction);
	}
	
	/**
	 * load productTableVIew
	 */
	private void loadProductTable() {
		this.resetProductTable();
		this.productTableView.getItems().addAll(this.fetchedTransactionDetail);
	}
	
	//Reset
	
	/**
	 * reset Transaction Table
	 */
	private void resetTransactionTable() {
		this.transTableView.getItems().clear();
	}
	
	/**
	 * reset Product Table
	 */
	private void resetProductTable() {
		this.productTableView.getItems().clear();
	}

}
