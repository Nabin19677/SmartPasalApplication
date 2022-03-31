package uiController;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.fxrouter.FXRouter;

import application.Environment;
import bll.Category;
import bll.Product;
import bll.Venue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import services.AlertService;
import services.RMIServices;
import services.APIServices;

/**
 * Controller class for Home.fxml
 * @author anilk
 *
 */
public class HomeController implements Initializable {
	//FXML Properties
	@FXML
	private BorderPane bp;
	@FXML
	private AnchorPane dashboardAnchorPane;
	@FXML
	private Label pageLabel;
	@FXML
	private Label storeName;
	@FXML
	private Label storePhone;
	@FXML
	private Button dashboardButton;
    @FXML
    private Label salesLabel;
    @FXML
    private Label productsLabel;
    @FXML
    private Label highestTransactionLabel;
    @FXML
    private PieChart salesPieChart;
    @FXML
    private LineChart<String,Double> salesLineChart;
    @FXML
    private TableView outOfStockTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    
    //Stores
    @FXML
    private TableView<Venue> storesTable;

    @FXML
    private TableColumn<Venue, String> storeNameColumn;

    @FXML
    private TableColumn<Venue, String> storeAddressColumn;

    @FXML
    private TableColumn<Venue, String> storeCategoryColumn;
    
	//Properties
	private ArrayList<String> quickDetails; // sales, products
	private ArrayList<Product> gettingOutOfStockData;
	private ArrayList<Venue> nearByStores = new ArrayList<>();
	private ObservableList<PieChart.Data> salesPieChartData = FXCollections.observableArrayList();
	private XYChart.Series<String,Double> salesLineChartData = new XYChart.Series();
    
  
	/**
	 * DashboardButton Clicked Event Handler
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void onDashboardButtonClicked(Event event) throws IOException{
		bp.setCenter(dashboardAnchorPane);
		pageLabel.setText("Dashboard");
		this.reset();
		this.init();
	}
	
	/**
	 * SellingPaneButton Clicked Event Handler
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void onSellingPaneButtonClicked(Event event) throws IOException{
		loadPage("SellingPane");
		pageLabel.setText("Selling Pane");
	}

	/**
	 * AddStockButton Clicked Event Handler
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void onAddStockButtonClicked(Event event) throws IOException{
		loadPage("AddStock");
		pageLabel.setText("Add Stock");
	}
	
	/**
	 * ProductsButton Clicked Event Handler
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void onProductsButtonClicked(Event event) throws IOException{
		loadPage("Products");
		pageLabel.setText("Products");
	}
	
	/**
	 * TransactionsButton Clicked Event Handler
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void onTransactionsButtonClicked(Event event) throws IOException{
		loadPage("Transactions");
		pageLabel.setText("Transactions");
	}
	
	/**
	 * VendorButton Clicked Event Handler
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void onVendorButtonClicked(Event event) throws IOException{
		loadPage("Vendor");
		pageLabel.setText("Vendor");
	}
	
	@FXML
	private void onLogoutButtonClicked(Event event) throws IOException{
		AlertService.showWarningAlert("Logging Out", "Are you sure?", "Logout")
		.filter(response -> response == ButtonType.OK)
		.ifPresent(response -> {
			this.logout();
		});

	}

	
	/**
	 * loads page on view and set to borderPane center
	 * @param page String of page name
	 */
	private void loadPage(String page) {
		try {
		      FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/"+page+".fxml"));
		      AnchorPane pane = (AnchorPane) loader.load();
			  bp.setCenter(pane);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	

	/**
	 * Logout
	 */
	private void logout() {	
		try {
			FXRouter.goTo("login");
			Environment.userName = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize HomeController class
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setOutOfStockQuantityColor();
		this.init();
	}
	
	/**
	 * Init Columns load all data and set all
	 */
	private void init() {
		this.initColumns();
		this.loadAllData();
		this.setAll();	
	}
	
	/**
	 * init table view columns
	 */
	private void initColumns() {
		this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		this.quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		
		this.storeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.storeAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		this.storeCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
	}
	
	/**
	 * calls other load methods
	 */
	private void loadAllData() {
		this.loadSalesLineData();
		this.loadQuickDetails();
		this.loadSalesPieData();
		this.loadGettingOutOfStock();
		this.loadProductTable();
		this.nearByStores = this.loadNearByStoresData();
		this.loadStorestable();
	}
	
	/**
	 * load quick details and set to this.quickDetails
	 */
	private void loadQuickDetails() {
		try {	
			this.quickDetails = RMIServices.getInstance().getQuickDetails();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * load sales data and add to this.salesPieChartData
	 */
	private void loadSalesPieData() {
		try {	
			ArrayList<ArrayList<String>> listData = RMIServices.getInstance().getSalesPieData();	
			for(ArrayList<String> data: listData ) {
				this.salesPieChartData.add(new PieChart.Data(data.get(1), Integer.parseInt(data.get(2))));
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * load sales data and add to this.salesLineChartData
	 */
	private void loadSalesLineData() {
		try {	
			ArrayList<ArrayList<String>> listData = RMIServices.getInstance().getSalesLineData();
			for(ArrayList<String> data: listData ) {
				this.salesLineChartData.getData().add(new XYChart.Data<String,Double>(data.get(0), Double.parseDouble(data.get(1))));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads NearByStores data save to variable
	 * @return
	 */
	private ArrayList<Venue> loadNearByStoresData() {
		try {
			String jsonNearbyVenues = APIServices.getInstance().getNearByVenues(); //JSON STRING
			
			JSONObject parsedJsonNearByVenues = new JSONObject(jsonNearbyVenues);
			
			JSONArray venuesJSONArray = parsedJsonNearByVenues.getJSONObject("response").getJSONArray("venues");
			
			int n = venuesJSONArray.length();
			ArrayList<Venue> venues = new ArrayList<>();
			
		    for (int i = 0; i < n; ++i) {
		      JSONObject venueObj = venuesJSONArray.getJSONObject(i);
		      JSONObject locationObj = venueObj.getJSONObject("location");
		      JSONArray categoryArray = venueObj.getJSONArray("categories");
		      
		      String venueName = venueObj.getString("name"); 
		      String venueAddress;
		      String venueCategory;
		      
		      if(!locationObj.has("address")) {
		    	  venueAddress = "No Address";    	  
		      } else {
		    	  venueAddress = locationObj.getString("address");
		      }
		      
		      venueCategory = categoryArray.getJSONObject(0).getString("name");
		      
		      venues.add(new Venue(venueName, venueAddress, venueCategory));
		    }
		    
		    return venues;
		} catch (IOException e) {
			e.printStackTrace();
		} catch(JSONException e) {
			e.printStackTrace();
		} 
		return nearByStores;
	}
	
	/**
	 * load products data and add to this.gettingOutOfStockData
	 */
	private void loadGettingOutOfStock() {
		try {	
			this.gettingOutOfStockData = RMIServices.getInstance().getGoingOutOfStockData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Other Set methods are called here
	 */
	private void setAll() {
		this.setQuickDetails();
		this.setSalesPieChart();
		this.setSalesLineChart();
	}
	
	/**
	 * set quick details to fxml controls
	 */
	private void setQuickDetails() {
		this.salesLabel.setText(this.quickDetails.get(0));
		this.productsLabel.setText(this.quickDetails.get(1));
		this.highestTransactionLabel.setText(this.quickDetails.get(2));
	}
	
	/**
	 * set sales data to pie chart
	 * 
	 */
	private void setSalesPieChart() {
		this.salesPieChart.setData(this.salesPieChartData);
		this.salesPieChart.setTitle("Most sold Product");
		this.salesPieChart.setLegendSide(Side.RIGHT);
		this.salesPieChart.setStyle("-fx-font-weight: bold");
		
	}
	
	/**
	 * set sales data to line chart
	 * 
	 */
	private void setSalesLineChart() {
		this.salesLineChart.setAnimated(false);
		this.salesLineChart.setLegendVisible(false);
		this.salesLineChart.setTitle("Sales Per Day");
		this.salesLineChart.getData().add(this.salesLineChartData);
		this.salesLineChart.setStyle("-fx-font-weight: bold");
	}
	
	/**
	 * load products table
	 */
	private void loadProductTable() {
		this.outOfStockTable.getItems().addAll(this.gettingOutOfStockData);
	}
	
	/**
	 * Load stores table
	 */
	private void loadStorestable() {
		this.storesTable.getItems().addAll(this.nearByStores);
	}
	
	/**
	 * Set Table Cell Color
	 */
	private void setOutOfStockQuantityColor() {
		this.quantityColumn.setCellFactory(column -> {
		    return new TableCell<Product, Integer>() {
		        @Override
		        protected void updateItem(Integer item, boolean empty) {
		        	
		        	super.updateItem(item, empty);

		            if (item == null || empty) {
		                setText(null);
		                setStyle("");
		            } else {
		                // Format date.
		                setText(item.toString()); //might change table cell property to String from Integer

		                // Style all dates in March with a different color.
		                if (item == 0) {
		                	setText("Product out of stock");
		                    setStyle("-fx-background-color: #d3070a; -fx-text-fill: white;");
		                } else if ( item == 1 || item == 2 ) {
		                    setStyle("-fx-background-color: #ff9934; -fx-text-fill: white;");
		                } else if ( item == 3 ) {
		                	setStyle("-fx-background-color: #ffff67;");
		                }else {
		                    setStyle("-fx-background-color: #c8f542;");
		                }
		            }
		        	
		        }
		    };
		});
	}
	
	/**
	 * reset whole anchorpane
	 */
	private void reset() {
		this.quickDetails = new ArrayList<>(); // sales, products
		this.salesPieChartData = FXCollections.observableArrayList();
		this.salesLineChartData.getData().clear();
		this.gettingOutOfStockData = new ArrayList<>();
		this.resetProductTable();
	}
	
	/**
	 * reset outOfStockTable TableView
	 */
	private void resetProductTable() {
		this.outOfStockTable.getItems().clear();
	}
//	

	
	
}
