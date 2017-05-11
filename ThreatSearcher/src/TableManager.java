import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class TableManager {
	public Stack<CSVTable> tables = new Stack<CSVTable>();
	private CSVTable activeTable;
	public enum Type { column, row, table, filter };
	private Window window;
	private CSVTable originalTable;
	
	public TableManager (Window window) {
		this.window = window;
		setTable(new CSVRunner().createCSVTable("logcsv.csv"));
		originalTable = activeTable;
	}
	
	public void initTable(Type type, AntlerCommand command) {
		CSVTable table = null;
		switch(type) {
			case column:
				table = sortColumn(command.data);
				break;
			case row:
				table = sortRow(command.data);
				break;
			case filter:
				table = filter(command.data);
				break;
		default:
			table = originalTable;
			break;
		}	
		setTable(table);
	}
	
	public void setTable (CSVTable table) {
		this.activeTable = table;
		activateTable();
	}
	
	public void resetTable() {
		tables.clear();
		setTable(originalTable);
	}
	
	public void lastTable () {
		if(tables.size() > 1) {
			tables.pop();
			setTable(tables.pop());
		}
	}
	
	private void activateTable () {
		window.updateTable(activeTable);
		tables.push(activeTable);
	}

	 private CSVTable sortColumn (String columnFilter) 
	 {
		ArrayList<String> tempColumns = new ArrayList<String>(Arrays.asList(columnFilter));
		ArrayList<ArrayList<Cell>> tempRows = new ArrayList<ArrayList<Cell>>();
		tempColumns.add(0, ""); //Adds an empty column for line numbers
    	for (ArrayList<Cell> row : activeTable.Rows) 
    	{
    		for(Cell cell : row) 
    		{
    			if(cell.column.contains(columnFilter))
    			{
					Cell lineNo = row.get(0);
					Cell newCell = new Cell(cell.column, cell.value);
					ArrayList<Cell> newRow = new ArrayList<Cell> ();
					newRow.add(lineNo);
					newRow.add(newCell);
    				tempRows.add(newRow);
    			}
    		}
    	}
    	return new CSVTable(tempRows, tempColumns, true);
	}
	
	private CSVTable filter (String filter) 
	{
		ArrayList<String> tempColumns = activeTable.Columns;
		ArrayList<ArrayList<Cell>> tempRows = new ArrayList<ArrayList<Cell>>();
    	for (ArrayList<Cell> row : activeTable.Rows) 
    	{
    		ArrayList<Cell> currentRow = row;
    		for(Cell cell : row) 
    		{
    			if(!tempRows.contains(row)) 
    			{
					if(cell.value.contains(filter)) 
					{
						tempRows.add(currentRow);
						break;
					}
    			}
    		}
    	}
    	System.out.println(tempRows.size());
    	return new CSVTable(tempRows, tempColumns, true);
	}
	
	private CSVTable sortRow (String rowNumber) {
		CSVTable activeTable = originalTable;
		ArrayList<ArrayList<Cell>> tempRows = new ArrayList<ArrayList<Cell>>();
		ArrayList<String> tempColumns = activeTable.Columns;
		tempRows.add(activeTable.Rows.get(Integer.parseInt(rowNumber)-1));
    	return new CSVTable(tempRows, tempColumns, false);
	}
}
