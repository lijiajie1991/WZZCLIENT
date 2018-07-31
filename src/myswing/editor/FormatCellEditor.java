package myswing.editor;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;

import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class FormatCellEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 1L;
	
	//double类型的正则表达式
	public static final String DT_DOUBLE = "-{0,1}[0-9]+.{0,1}[0-9]*";
	//int类型的正则表达式
	public static final String DT_INTEGER = "-{0,1}[0-9]+";
	
	//正则表达式
	private String regex = null;
		
	private int scale = 0;
	private JTextField ftf = null;
	
	public FormatCellEditor(String regex, int scale)
	{
		this(new JTextField(), regex, scale);
	}
	
	private FormatCellEditor(JTextField ftf, String regex, int scale) {
		super(ftf);
		this.ftf = ftf;
		this.scale = scale;
		this.regex = regex;
		
		ftf.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				ftf_focusLostAction();
			}
			
			public void focusGained(FocusEvent e) {
				ftf_focusGainedAction();
			}
		});
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		JTextField ftf =  (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
		return ftf;
	}

	public Object getCellEditorValue() {
		return getValue();
	}

	public BigDecimal getValue()
	{
		String valStr = ftf.getText();
		if(valStr.matches(regex))
		{
			BigDecimal val = (new BigDecimal(valStr));
			if(scale != -1)
				val = val.setScale(scale, BigDecimal.ROUND_HALF_UP);
			
			return val;
		}
		
		return BigDecimal.ZERO;
	}

	public int getIntValue()
	{
		return getValue().intValue();
	}
	
	public double getDoublValue()
	{
		return getValue().doubleValue();
	}
	
	public void setValue(int value)
	{
		BigDecimal val = (new BigDecimal(value));
		if(scale != -1)
			val = val.setScale(scale, BigDecimal.ROUND_HALF_UP);
		ftf.setText(val.toString());
	}
	
	public void setValue(double value)
	{
		BigDecimal val = (new BigDecimal(value));
		if(scale != -1)
			val = val.setScale(scale, BigDecimal.ROUND_HALF_UP);
		ftf.setText(val.toString());
	}
	
	public void ftf_focusLostAction()
	{
		String valStr = ftf.getText();
		if(!"".equals(valStr) && !valStr.matches(regex))
		{
			JOptionPane.showMessageDialog(null, "输入格式错误！", "提示", JOptionPane.INFORMATION_MESSAGE);
			valStr = "";
		}
		
		BigDecimal val = BigDecimal.ZERO;
		if(!"".equals(valStr))
		{
			val = new BigDecimal(valStr);
		}
		if(scale != -1)
			val = val.setScale(scale, BigDecimal.ROUND_HALF_UP);
		ftf.setText(val.toString());
	}
	
	public void ftf_focusGainedAction()
	{
		BigDecimal val = this.getValue();
		if(BigDecimal.ZERO.compareTo(val) == 0)
			ftf.setText("");
	}
	
}
