package listener.event;

public class TblEditStopedEvent {
	protected int rowIndex = -1;
	protected int colIndex = -1;
	
	protected String columnKey = null;
	protected Object value = null;
	
	public TblEditStopedEvent(int rowIndex, int colIndex, String columnKey, Object value) {
		super();
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		this.columnKey = columnKey;
		this.value = value;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getColIndex() {
		return colIndex;
	}
	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}
	public String getColumnKey() {
		return columnKey;
	}
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}



}

