import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class Window implements ActionListener, KeyListener {
	
	private JTextField commandInput;
	private JScrollPane tableContainer;
	private AntlerCommandParser commandParser;
	private CommandExecutor commandExecutor;
	
	public Window ()
	{
		initializeWindow();
		this.commandParser = new AntlerCommandParser();
		this.commandExecutor = new CommandExecutor(this);
	}
	
	
	private JTable createTable (CSVTable table, boolean resizeable) {
    	Object[] ObjectColumns = table.Columns.toArray();
    	String[] GUIColumns = Arrays.copyOf(ObjectColumns, ObjectColumns.length, String[].class);
    	String [][] GUIRows = new String[table.Rows.size()][table.Rows.get(0).size()];
    	for (int i=0; i < table.Rows.size(); i++) {
    		for (int j=0; j < table.Rows.get(i).size(); j++) {
    			GUIRows[i][j] = table.Rows.get(i).get(j).value;
    		}
    	}
    	JTable newTable = new JTable(GUIRows, GUIColumns);
    	if(!resizeable) { 
    		newTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	} else {
    		newTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    	}
    	return newTable;
	}
	
	public void updateTable(CSVTable table) {
		System.out.println("UPDATING TABLE");
		JTable newTable = createTable(table, table.resizeable);
		tableContainer.getViewport().remove(0);
		tableContainer.getViewport().add(newTable);
	}
	
    private void initializeWindow () 
    {
        JFrame frame = new JFrame("Quinnipiac Threat Search");
        frame.setMinimumSize(new Dimension(640,480));

        JPanel mainPanel = new JPanel();
        JPanel commandPanel = new JPanel(); 
        JPanel panel = new JPanel();

        commandPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        panel.setLayout(new BorderLayout());

        commandInput = new JTextField(20);
        commandInput.addKeyListener(this);
        JButton executionButton = new JButton("Execute");
        executionButton.addActionListener(this);
        JTable table = new JTable();
        table.setName("table");
        tableContainer = new JScrollPane(new JTable(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        panel.add(tableContainer, BorderLayout.CENTER);

        commandPanel.add(commandInput);
        commandPanel.add(executionButton);
        mainPanel.add(commandPanel);
        mainPanel.add(panel);

        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent evt) {
    	String cmd = evt.getActionCommand();
    	if(cmd == "Execute") {
    		sendCommand();
    	}
    }
    
    private void sendCommand () {
		String command = commandInput.getText();
		String last = command.substring(command.length()-1, command.length());
		System.out.println("LAST: " +last);
		if(!last.equalsIgnoreCase("-")) command = command.concat("-");
		commandInput.setText("");
		boolean valid = commandParser.evaluateUserInput(command);
		if(valid) {
			commandExecutor.issueCommand(commandParser.userCommands.get(0));
		} else {
			//TODO: Show error via GUI
			System.out.println("INVALID INPUT");
		}
    }


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub;
		if(e.getKeyCode() == KeyEvent.VK_ENTER) 
		{
    		sendCommand();
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}