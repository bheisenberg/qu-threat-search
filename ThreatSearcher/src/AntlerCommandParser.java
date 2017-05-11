import java.util.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class AntlerCommandParser 
{
	int numberOfCommands;
	public List<AntlerCommand> userCommands;
	public boolean isValid = true;
	private ArrayList<String> noSpaceCommands = new ArrayList<String>() {{ add("BACK"); }};

	public String activeCommand;
	
	public boolean evaluateUserInput(String string)
	{				
		this.numberOfCommands = getNumberOfCommands(string);
		this.userCommands = new LinkedList<AntlerCommand>(); 
		if((this.isValid = validateInput(string))) this.parseInput(string);
		return this.isValid;
	}
	
	private void parseInput(String _string)
	{
		System.out.println("Parsing");
		String workingString = _string; 
		char[] workingData = _string.toCharArray();
		String workingCommand;
		if(_string.contains(" ")) {
			this.activeCommand = workingString.substring(0, workingString.indexOf(" "));
		} 
		else
		{
			workingCommand = workingString.substring(0, workingString.indexOf("-"));
			if(noSpaceCommands.contains(workingCommand)) this.activeCommand = workingCommand;
		}
		
		System.out.println(this.activeCommand);
		/*if(this.activeCommand.equals("FINDCOL"))
		{
			for(int i = 0; i < this.numberOfCommands; i++)
			{
				if(i == 0)
				{
					// Obtain the index values used for parsing 
					int spaceIndex = workingString.indexOf(" ");
					int endOfCommand = workingString.indexOf("-"); 
					
					// Parse the corresponding id and data from the user input 
					String idData = workingString.substring(spaceIndex + 1, endOfCommand); 
					
					// Append the user command referencing the id string and it's corresponding data 
					this.userCommands.add(new AntlerCommand(activeCommand, idData));
					
					// Overwrite previous index values 
					workingData[endOfCommand] = '&';
					
					// Save any string manipulations 
					workingString = new String(workingData);
				}
				else
				{
					// Obtain the index values used for parsing 
					int ampersandIndex = workingString.indexOf("&");
					int endOfCommand = workingString.indexOf("-");
					
					// Parse the corresponding id and data from the user input
					String idData = workingString.substring(ampersandIndex + 1, endOfCommand); 
					
					// Append the user command referencing the id string and it's corresponding data 
					this.userCommands.add(new AntlerCommand(activeCommand, idData));
	
					// Overwrite previous index values 
					workingData[endOfCommand] = '&';
					
					// Save any string manipulations 
					workingString = new String(workingData);
				}
			}
		}*/
		if (this.activeCommand.equals("FINDROW") || this.activeCommand.equals("FINDCOL") || this.activeCommand.equals("FILTER"))
		{
			System.out.println("Running FINDROW command");
			System.out.println(workingString.substring(workingString.indexOf(" ") + 1, workingString.indexOf("-")));
			this.userCommands.add(new AntlerCommand(activeCommand, workingString.substring(workingString.indexOf(" ") + 1, workingString.indexOf("-"))));
		} 
		else if (this.activeCommand.equals("BACK"))
		{
			System.out.println("Running BACK command");
			this.userCommands.add(new AntlerCommand(activeCommand, null));
		}
	}
	
	private boolean validateInput (String inputString) 
	{
        ANTLRInputStream input = new ANTLRInputStream(inputString);
        CommandLexer lexer = new CommandLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CommandParser parser = new CommandParser(tokens);
        ParseTree tree = parser.r(); // begin parsing at rule 'r'
        int errors = parser.getNumberOfSyntaxErrors();
        System.out.println(tree.toStringTree(parser)); // print LISP-style tree
        return (errors == 0);
	}

	private int getNumberOfCommands(String _string)
	{
		int numberOfCommands = 0; 
		for(int i = 0; i < _string.length(); i++)
		{
			if(Main.debug) System.out.println("Char at " + i + " is " + _string.charAt(i));
			if(_string.charAt(i) == '-') numberOfCommands++; 
		}
		
		return numberOfCommands; 
	}
}

