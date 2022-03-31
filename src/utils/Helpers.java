package utils;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * This class contains static methods for minor issues
 * @author anilk
 *
 */
public class Helpers {
	
	/**
	 * 
	 * @param data
	 * @param columnIndex
	 * @return ArrayList of each column data
	 */
	public static ArrayList<String> getColumnData(ArrayList<ArrayList<String>> data, int columnIndex){
		ArrayList<String> tempArrayList = new ArrayList<>();	
		for(ArrayList<String> dt: data) {
			tempArrayList.add(dt.get(columnIndex));
		}
		return tempArrayList;
	}
	
	/**
	 * 
	 * @param tableView
	 * @return Multidimensional ArrayList of String data fetched from a table view
	 */
	 public static ArrayList<ArrayList<String>> getTableViewValues(TableView tableView) {
		 ArrayList<ArrayList<String>> items = new ArrayList<>();
		 ObservableList<TableColumn> columns = tableView.getColumns(); 
		 ArrayList<String> values = new ArrayList<>();
		 int i = 0;
		 for (Object row : tableView.getItems()) {
			 for (TableColumn column : columns) {
				 values.add(
						 String.valueOf(column.
								 getCellObservableValue(row).
								 getValue()));
				 i++;
				 if(i == columns.size()) {
					 items.add(values);
					 i = 0;
					 values = new ArrayList<>();
				 }
			 }
		 }
	    return items;
	  }
	
}
