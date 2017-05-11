public class Cell 
{
	public String column;
	public String value;
	
	public Cell (String column, String value) {
		this.column = column;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Column: \""+this.column+"\", Value: \"" +this.value+'"';
	}
}