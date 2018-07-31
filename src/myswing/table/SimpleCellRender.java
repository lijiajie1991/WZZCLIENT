package myswing.table;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class SimpleCellRender extends DefaultTableCellRenderer{
	private static final long serialVersionUID = 1L;
	protected SimpleTable tbl = null;
	
	public SimpleCellRender(SimpleTable tbl)
	{
		super();
		this.tbl = tbl;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		Class<?> cls = null;
		if(this.tbl != null)
			cls = this.tbl.getColumnClass(column);
		if(cls == null && value != null)
		{
			cls = value.getClass();
		}
		
		if(cls != null && Boolean.class.equals(cls))
		{
			Object val = value != null ? value : false;
			JCheckBox chk = new JCheckBox();
			chk.setSelected((Boolean) val);
			chk.setHorizontalAlignment(JLabel.CENTER);
			return chk;
		}
		
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

}
