import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

import java.util.ArrayList;

public class CommandExecutor {

	public TableViewer tableViewer;
	public Hashtable <String, String> commandDict;
	public Stack<CSVTable> tables = new Stack<CSVTable>();
	public TableManager tableManager;
	
	public CommandExecutor (Window window) {
		initCommandDictionary();
		this.tableManager = new TableManager(window);
	}
	
	private void initCommandDictionary () {
		commandDict = new Hashtable<String, String>();
		commandDict.put("A", "Application");
		commandDict.put("T", "Time");
		commandDict.put("TL", "Time Logged");
		commandDict.put("G", "Generate Time");
		commandDict.put("SP", "Source Port");
		commandDict.put("D", "Destination");
	}
	
	
	public void issueCommand (AntlerCommand command) 
	{		
		switch(command.command) 
		{
			case "BACK": 
				tableManager.lastTable();
				break;
			case "HOME":
				tableManager.resetTable();
				break;
			case "FINDCOL":
				findColumn(command);
				break;
			case "FINDROW":
				findRow(command);
				break;
			case "FILTER":
				filter(command);
				break;
		}
	}
	
	private void findRow (AntlerCommand command)
	{
		tableManager.initTable(TableManager.Type.row, command);
	}
	
	private void findColumn (AntlerCommand command) {
		command.data = commandDict.get(command.data);
		tableManager.initTable(TableManager.Type.column, command);
	}
	
	private void filter (AntlerCommand command) {
		System.out.println("DATA: " +command.data);
		tableManager.initTable(TableManager.Type.filter, command);
	}
}
