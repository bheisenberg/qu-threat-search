import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JTable;

public class CSVTable {
	public ArrayList<String> Columns;
	public ArrayList<ArrayList<Cell>> Rows;
	public boolean resizeable;
	
	public CSVTable (boolean resizeable) {
		Rows = new ArrayList<ArrayList<Cell>>();
		Columns = new ArrayList<String>();
		this.resizeable = resizeable;
	}
	
	public CSVTable (ArrayList<ArrayList<Cell>> rows, ArrayList<String> columns, boolean resizeable) {
		Rows = rows;
		Columns = columns;
		this.resizeable = resizeable;
	}
}
