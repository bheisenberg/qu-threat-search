import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.*;
    import org.antlr.v4.runtime.tree.*;

    public class CSVRunner 
    {
    	
    	private ArrayList<ArrayList<String>> CSV;
    	private boolean debug = false;
    	
    	public CSVTable createCSVTable (String fileName) {
    		CSV = new ArrayList<ArrayList<String>>();
            CSVTable CSVTable = new CSVTable(false);
    	    try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
    	    	int lineNo = 0;
    	    	String line;
    	    	while ((line = in.readLine()) != null) {
    	    		if (line.length() == 0)
    	    			continue;
    	    		if(lineNo == 0) {
    	    			ArrayList<String> headerLine = HeaderLine(line);
    	    			headerLine.add(0, "");
    	    			CSVTable.Columns = headerLine;
    	    		} else {
    	    			CSVTable.Rows.add(CreateRow(HeaderLine(line), CSVTable.Columns, lineNo));
    	    		}
    	    		lineNo++;
    	    	}
    	    } catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	    return CSVTable;
    	}
    	
    	TokenSource CSVTokenSource (String inputString) {
            ANTLRInputStream input = new ANTLRInputStream(inputString);
            CSVLexer lexer = new CSVLexer(input);
            BufferedTokenStream tokens = new BufferedTokenStream(lexer);
            CSVParser parser = new CSVParser(tokens);
            return tokens.getTokenSource();
    	}
    	
    	ArrayList<String> HeaderLine (String inputString) {
    		ArrayList<String> header = new ArrayList<String>();
    		TokenSource tokenSource = CSVTokenSource(inputString);
            Token currentToken = tokenSource.nextToken();
            Token prevToken = null;
            while(currentToken.getType() != Recognizer.EOF) {
            	if(prevToken == null || prevToken.getText().compareTo(",") == 0) {
            		if(currentToken.getText().compareTo(",") != 0) {
            			header.add(currentToken.getText());
            		} else {
            			header.add("");
            		}
            	}
            	prevToken = currentToken;
            	currentToken = tokenSource.nextToken();
            }
            if (currentToken.getType() == Recognizer.EOF && prevToken.getText().compareTo(",") == 0) {
            	header.add("");
            }
            return header;
    	}
    	
    	ArrayList<Cell> CreateRow (ArrayList<String> columns, ArrayList<String> columnNames, int lineNo) {
    		ArrayList<Cell> row = new ArrayList<Cell>();
    		for (int i = 0; i < columns.size(); i++) {
    			Cell newCell = new Cell (columnNames.get(i+1), columns.get(i));
    			row.add (newCell);
    			if(debug)System.out.println(newCell);
    		}
    		row.add(0, new Cell("Line", Integer.toString(lineNo)));
    		return row;
    	}
    }