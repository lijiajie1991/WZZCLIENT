package myswing.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;

import common.util.ImageLoader;
import listener.DataChangeListener;

public class DateCellEditor extends JPanel implements TableCellEditor{
	private static final long serialVersionUID = 1L;
	
	private JTextField txtValue = null;
	private DateChooser dcDate = null;
	private JButton btn = null;
	
	private EventListenerList listenerList = new EventListenerList(); 
	private ChangeEvent changeEvent = new ChangeEvent(this); 
	
	public DateCellEditor() throws SQLException
	{
		super();
		
		initUI();
	}
	
	private void initUI()
	{
		this.setLayout(new BorderLayout());
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = df.format(new Date());
		Font font = new Font("宋体",Font.PLAIN, 15);
		
		txtValue = new JTextField();
		txtValue.setText(dateStr);
		txtValue.setFont(font);
		
		dcDate = DateChooser.getInstance("yyyy-MM-dd");
		dcDate.register(txtValue);
		
		btn = new JButton();
		btn.setIcon(ImageLoader.getIcon("pig_24.png"));
		btn.setToolTipText("选择");
		
		btn.setPreferredSize(new Dimension(20,getHeight()));
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btn_action(e);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		txtValue.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				txtValue_FocusLostAction(e);
			}
			
			public void focusGained(FocusEvent e) {
				txtValue_focusGainedAction(e);
			}
		});
		
		this.add(txtValue, BorderLayout.CENTER);
		this.add(btn, BorderLayout.EAST);
	}
	
	private void btn_action(ActionEvent e) throws SQLException
	{
		dcDate.showDatePane();
	}
	
	public void setEditable(boolean param)
	{
		txtValue.setEditable(param);
	}
	
	public void addDataChangeListener(DataChangeListener ls)
	{
		dcDate.addDataChangeListener(ls);
	}
	
	private void txtValue_FocusLostAction(FocusEvent e)
	{}
	
	private void txtValue_focusGainedAction(FocusEvent e)
	{}
	
	//---------------------------------------TableCellEditor接口方法-----------------------------------

	public void addCellEditorListener(CellEditorListener ls) {
		 listenerList.add(CellEditorListener.class, ls); 
	}

	
	public void cancelCellEditing() {
	}
	
	public Object getCellEditorValue() {
		return txtValue.getText();
	}
	
	public boolean isCellEditable(EventObject e) {
		return true;
	}

	
	public void removeCellEditorListener(CellEditorListener ls) {
		listenerList.remove(CellEditorListener.class, ls); 
		
	}
	
	public boolean shouldSelectCell(EventObject e) {
		return true;
	}
	
	public boolean stopCellEditing() {
		  fireEditingStopped();//请求终止编辑操作从JTable获得 
		  return true; 
	}
	
	public Component getTableCellEditorComponent(JTable e, Object value,
			boolean isSelected, int row, int column) {
		return this; 
	}
	
	//-----------------------------------------------------
	private void fireEditingStopped(){ 
		  CellEditorListener listener; 
		  Object[]listeners = listenerList.getListenerList(); 
		  for(int i = 0; i < listeners.length; i++){ 
		   if(listeners[i]== CellEditorListener.class){ 
		    //之所以是i+1，是因为一个为CellEditorListener.class（Class对象）， 
		    //接着的是一个CellEditorListener的实例 
		    listener= (CellEditorListener)listeners[i+1]; 
		    //让changeEvent去通知编辑器已经结束编辑 
		    //          //在editingStopped方法中，JTable调用getCellEditorValue()取回单元格的值， 
		    //并且把这个值传递给TableValues(TableModel)的setValueAt() 
		    listener.editingStopped(changeEvent); 
		   } 
		  } 
		 } 
	
}
