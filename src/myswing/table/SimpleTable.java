package myswing.table;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import common.util.DateTimeUtil;
import exp.ExpHandle;
import listener.TableEditStopedListener;
import listener.event.TblEditStopedEvent;
import myswing.msgbox.MsgBox;

public class SimpleTable extends JScrollPane
{
	private static final long serialVersionUID = 1L;
	
	protected HashMap<String, ColumnInfo> colMap = null;
	protected LinkedList<ColumnInfo> colList = null;
	protected String[] colsKey = null;
	protected HashMap<String, Boolean> colsEditAble = null; 
	
	protected JTable table = null;
	protected Font font = null;
	protected DefaultTableModel model = null; 
	protected boolean editenable = true;
	protected boolean isSingleSel = false;
	
	protected JButton btnAddLine = null;
	protected JButton btnInsert = null;
	protected JButton btnRemove = null;
	
	protected JPopupMenu pMenu = null;
	
	public SimpleTable()
	{
		this(true);
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：默认构造函数
	 * */
	public SimpleTable(boolean sorteAble)
	{
		
		super(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		font = new Font("宋体",Font.PLAIN, 15);
		colsEditAble = new HashMap<String, Boolean>();
		
		table = new JTable();
		table.setFont(font);
		
		table.getTableHeader().setReorderingAllowed(false);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.setViewportView(table);
		
		model = new DefaultTableModel()
		{
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
				if(this.getColumnCount() > 0 && this.getRowCount() > 0)
				{
					Object obj = this.getValueAt(0, column);
					if(obj != null)
						return obj.getClass();
				}
				return String.class;
			}
			
			public boolean isCellEditable(int row, int column) 
			{ 
				if(colsEditAble.containsKey(row + ":" + column))
					return colsEditAble.get(row + ":" + column);
				
				if(colsEditAble.containsKey(column + ""))
					return colsEditAble.get(column + "");
				
			    return editenable;
			}
		};
		table.setModel(model);
		
		if(sorteAble)
		{
			//设置可排序
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel> (model);
			table.setRowSorter(sorter);
		}
		//创建右击菜单
		createPopupMenu();
		
		//表头鼠标右击事件
		tableHeaderMouseListener();
	}
	
	public void addKeyListener(KeyListener ls)
	{
		table.addKeyListener(ls);
	}
	
	public void addMouseListener(MouseListener ls)
	{
		table.addMouseListener(ls);
	}
	
	public void setName(String name)
	{
		super.setName(name);
		
		if(table != null)
			table.setName(name);
	}
	
	public int rowAtPoint(Point point)
	{
		return table.rowAtPoint(point);
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：创建右键菜单
	 * */
	protected void createPopupMenu()
	{
		pMenu = new JPopupMenu();
		
		JMenuItem jMenuExprot = new JMenuItem();
		jMenuExprot.setText("导出Excel文件");
		
		JMenuItem jMenuExportAndOpen = new JMenuItem();
		jMenuExportAndOpen.setText("导出Excel文件并打开");
		
		pMenu.add(jMenuExprot);
		pMenu.add(jMenuExportAndOpen);
		
		
		jMenuExprot.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						try {
							JFileChooser jfc=new JFileChooser();  
					        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);  
					        int retval = jfc.showSaveDialog(SimpleTable.this);  
				            if(retval == JFileChooser.APPROVE_OPTION) {
				            	File file = jfc.getSelectedFile();
				            	String fileName = file.getAbsolutePath();
				            	if(!fileName.endsWith(".xls"))
				            		fileName += ".xls";
				            	exportExcel(fileName, false);
				            }
					        
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				});
		
		jMenuExportAndOpen.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						try {
							File temp = File.createTempFile("EXCEL", ".xls");
							String fileName = temp.getAbsolutePath();
							exportExcel(fileName, true);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				});
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：设置表头右键弹出菜单
	 * */
	protected void tableHeaderMouseListener()
	{
		table.getTableHeader().addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				
				int mods = e.getModifiers();
				if((mods & MouseEvent.BUTTON3_MASK) != 0)
				{
					pMenu.show(table,e.getX(),e.getY());
					pMenu.setVisible(true);
				}
				else
				{
					pMenu.setVisible(false);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				
			}});
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：设置单元格显示位置
	 * */
	public void setAlign(int param)
	{
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
	    render.setHorizontalAlignment(param);
	    table.setDefaultRenderer(Object.class, render);   
	}
	
	public void setColumnEditable(boolean[] params)
	{
		colsEditAble.clear();
		for(int i = 0; i < params.length; i++)
		{
			colsEditAble.put(i + "", params[i]);
		}
	}
	
	public void setCellEditable(int rowIndex, int colIndex, boolean flag)
	{
		colsEditAble.put(rowIndex + ":" + colIndex, flag);
	}
	
	public void setCellEditable(int rowIndex, String colKey, boolean flag)
	{
		int colIndex = this.getColumnIndex(colKey);
		colsEditAble.put(rowIndex + ":" + colIndex, flag);
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：设置列是否可移动
	 * */
	public void setReorderingAllowed(boolean param)
	{
		table.getTableHeader().setReorderingAllowed(param);//不可整列移动 
	}
	
	public void setResizingAllowed(boolean param)
	{
		table.getTableHeader().setResizingAllowed(param); //不可拉动表格 
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：设置是否单选列
	 * */
	public void setSingleSel(boolean param)
	{
		isSingleSel = param;
		if(isSingleSel)
		{
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		else
		{
			table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：设置表头
	 * @param columns：表头数组
	 * */
	public void setColumnName(String[] cols) {
		
	    model.setColumnIdentifiers(cols);

		int columnLength = cols.length;
		int[] widthArray = new int[columnLength];
		for (int i = 0; i < columnLength; i++) {
			widthArray[i] = 150;
		}
		this.setColumnWidth(widthArray);
	}

	public void setColumnKey(String[] colsKey)
	{
		this.colsKey = colsKey;
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：删除行
	 * @param rowIndex:需要删除的行号
	 * */
	public void deleteRow(int rowIndex)
	{
		model.removeRow(rowIndex);
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：插入行
	 * @param rowIndex:行号，rowData:行数组
	 * */
	public void insertRow(int rowIndex, Object[] rowData)
	{
		model.insertRow(rowIndex, rowData);
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：插入行
	 * @param rowIndex:行号，rowData:行数组
	 * */
	public void insertRowMap(int rowIndex, Map<String, Object> map) throws Exception
	{
		LinkedList<Object> row = new LinkedList<Object>();
		for(String key : colsKey)
		{
			Object obj = map.containsKey(key) ? map.get(key) : "";
			
			
			if(colMap.containsKey(key))
			{
				ColumnInfo col = colMap.get(key);
				Class<?> cls = col.getCls();
				if(cls != null)
				{
					if(cls.isEnum() && !obj.getClass().isEnum())
					{
						//做空处理并转换为对应的数据类型
						obj = obj != null ? Integer.parseInt(obj.toString()) : 0;
						//调用枚举的静态valueOf方法获取相应的枚举
						Method m = cls.getMethod("valueOf", int.class);
						obj = m.invoke(this, obj);
					}
					else if(Boolean.class.equals(cls))
					{
						obj = obj == null || "0".equals(obj.toString()) || "false".equalsIgnoreCase(obj.toString()) ? Boolean.FALSE : Boolean.TRUE;
					}
					else if(Date.class.equals(cls))
					{
						try{
							obj = DateTimeUtil.getDateStr(DateTimeUtil.parseStr(obj));
						}catch(Exception e)
						{
							
						}
					}
				}
			}
			
			row.addLast(obj);
		}
		this.insertRow(rowIndex, row.toArray(new Object[0]));
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：插入行
	 * @param value：单元格值，rowIndex:行号，columnIndex:列号
	 * @throws Exception 
	 * */
	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		try{
			if(rowIndex >= model.getRowCount())
				throw new Exception("无效行号！！");
			if(columnIndex >= model.getColumnCount())
				throw new Exception("无效列号！！");
			model.setValueAt(value, rowIndex, columnIndex);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：删除所有行
	 * */
	public void removeRows()
	{
		int rowCount = model.getRowCount() - 1;
		while(rowCount >= 0)
		{
			model.removeRow(rowCount--);
		}
	}
	
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：获取行数据
	 * @返回：行数据数组
	 * */
	public Object[] getRow(int rowIndex)
	{
		int columnCount = model.getColumnCount();
		Object[] row = new Object[columnCount];
		for(int i = 0; i < columnCount; i++)
		{
			row[i] = getValueAt(rowIndex, i);
		}
		
		return row;
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：获取行数据
	 * @返回：行数据数组
	 * */
	public HashMap<String, Object> getRowMap(int rowIndex)
	{
		HashMap<String, Object> rowMap = new HashMap<String, Object>();
		if(this.colsKey == null || this.colsKey.length <= 0)
			return rowMap;
		
		Object[] rowData = getRow(rowIndex);
		int len = rowData.length;
		for(int colIndex = 0; colIndex < len; colIndex++)
		{
			rowMap.put(colsKey[colIndex], rowData[colIndex]);
		}
		
		return rowMap;
	}
	
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：获取单元格数据
	 * @返回：单元格数据
	 * */
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Object value = null;
		try{
			value = model.getValueAt(rowIndex, columnIndex);
		}catch(ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
		
		return value;
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：获取当前选中行
	 * */
	public int getSelectedRow()
	{
		int rowIndex = table.getSelectedRow();
		if(rowIndex < 0 || rowIndex >= this.getRowCount())
			return -1;
		return table.convertRowIndexToModel(rowIndex);
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：设置行宽
	 * */
	public void setColumnWidth(int[] wds)
	{
		TableColumnModel columns = table.getColumnModel();  
		for (int i = 0; i < wds.length; i++) {  
			TableColumn column = columns.getColumn(i);  
			column.setPreferredWidth(wds[i]);  
			
			if(wds[i] <= 0)
			{
				column.setMinWidth(0);
				column.setMaxWidth(0);
			}
		}  
		table.setColumnModel(columns);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：设置单元格显示位置
	 * */
	public void setAlign(int[] align)
	{
		int len = align.length;
		for(int i = 0; i < len; i++)
		{
			SimpleCellRender r = new SimpleCellRender(this);  
			r.setHorizontalAlignment(align[i]); 
            TableColumn col = table.getColumn(table.getColumnName(i));  
            col.setCellRenderer(r);  
        } 
	}
	
	public void setColumEditAble(int colIndex, boolean flag)
	{
		this.colsEditAble.put(colIndex + "", flag);
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：获取当前选中行
	 * */
	public int[] getSelectedRows()
	{
		int[] rowIndex = table.getSelectedRows();
		int[] selIndex = new int[rowIndex.length];
		
		for(int i = 0; i < rowIndex.length; i++)
			selIndex[i] = table.convertRowIndexToModel(rowIndex[i]);
		
		return selIndex;
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：表格添加键盘监听事件
	 * */
	public void addTblKeyListener(KeyListener listener)
	{
		if(table != null)
		{
			table.addKeyListener(listener);
		}
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：表格添加鼠标监听事件
	 * */
	public void addTblMouseListener(MouseListener listener)
	{
		if(table != null)
		{
			table.addMouseListener(listener);
		}
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：表格添加鼠标移动事件
	 * */
	public void addTblMouseMotionListener(MouseMotionListener listener)
	{
		if(table != null)
		{
			table.addMouseMotionListener(listener);
		}
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：表格添加鼠标滚动事件
	 * */
	public void addTblMouseWheelListener(MouseWheelListener listener)
	{
		if(table != null)
		{
			table.addMouseWheelListener(listener);
		}
	}
	
	/**
	 * @作者：乌卒卒
	 * @功能描述：获取行数
	 * */
	public int getRowCount()
	{
		return model.getRowCount();
	}

	public int getColumnIndex(String key)
	{
		if(colsKey == null || colsKey.length == 0)
			return -1;
		
		int len = colsKey.length;
		
		for(int index = 0; index < len; index++)
		{
			if(colsKey[index] != null && colsKey[index].equals(key))
				return index;
		}
		
		return -1;
	}
	
	public String getColumnKey(int colIndex)
	{
		if(colsKey == null || colsKey.length <= colIndex)
			return null;
		
		return colsKey[colIndex];
	}
	
	public void setValueAt(Object obj, int rowIndex, String colKey)
	{
		int colIndex = getColumnIndex(colKey);
		this.setValueAt(obj, rowIndex, colIndex);
	}
	
	public Object getValueAt(int rowIndex, String colKey)
	{
		int colIndex = getColumnIndex(colKey);
		return this.getValueAt(rowIndex, colIndex);
	}
	
	public void setColumnEditor(int colIndex, TableCellEditor editor)
	{
		TableColumn column = table.getColumnModel().getColumn(colIndex);
		column.setCellEditor(editor);
	}
	
	public void setColumnEditor(String colKey, TableCellEditor editor)
	{
		colKey = colKey.toLowerCase();
		int colIndex = this.getColumnIndex(colKey);
		TableColumn column = table.getColumnModel().getColumn(colIndex);
		column.setCellEditor(editor);
	}

	public TableCellEditor getColumnEditor(int colIndex)
	{
		TableColumn column = table.getColumnModel().getColumn(colIndex);
		return column.getCellEditor();
	}
	
	
	public boolean isEditenable() {
		return editenable;
	}

	public void setEditenable(boolean editenable) {
		this.editenable = editenable;
	}

	public void setEnable(boolean flag)
	{
		table.setEnabled(flag);
		if(btnAddLine != null)
			btnAddLine.setEnabled(flag);
		if(btnInsert != null)
			btnInsert.setEnabled(flag);
		if(btnRemove != null)
			btnRemove.setEnabled(flag);
	}
	
	public DefaultTableModel getModel() {
		return model;
	}

	public void setModel(DefaultTableModel model) {
		this.model = model;
	}
	
	/**
	 * @throws IOException 
	 * @作者：乌卒卒
	 * @功能描述：弹出文件保存对话框，调用导出EXCEL表格方法
	 * */
	@SuppressWarnings("unchecked")
	protected void exportExcel(String fileName, boolean isAutoOpen) throws IOException
	{
		Map data = new HashMap<String, String[]>();
		int colNum = model.getColumnCount();
		Object[] rowStr = new Object[colNum];
		for(int i = 0; i < colNum; i++)
		{
			rowStr[i] = model.getColumnName(i);
		}
		data.put(0, rowStr);
		
		int rowNum = model.getRowCount();
		for(int i = 0; i < rowNum; i++)
		{
			rowStr = this.getRow(i);
			
			if(rowStr == null)
				break;
			data.put(i + 1, rowStr);
		}
		
		String sheetName = table.getName();
		if(sheetName == null || "".equals(sheetName))
			sheetName = "sheet1";
		
		if(exportExcel(data, sheetName, fileName))
		{
			if(isAutoOpen)
				Desktop.getDesktop().open(new File(fileName));
		}
		else
		{
			MsgBox.showInfo(null, "成功失败");
		}
	}

	/**
	 * @作者：乌卒卒
	 * @功能描述：导出EXCEL表格
	 * @param data excel表格数据
	 * */
	protected boolean exportExcel(Map data, String sheetName, String fileName)
	{
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 

		HSSFCell cell = null;
		HSSFRow row = null;
		int colNum;
		int size = data.size();
		Object[] rowData = null;
		for(int i = 0; i < size; i++)
		{
			row = sheet.createRow(i);
			rowData = (Object[]) data.get(i);
			colNum = rowData.length;
			for(int j = 0; j < colNum; j++)
			{
				String valStr = "";
				Object val = rowData[j];
				if(val instanceof Boolean)
					valStr = "true".equalsIgnoreCase(val.toString()) ? "是" : "否";
				else 
					valStr = val.toString();
				
				cell = row.createCell(j);
				cell.setCellValue(valStr);
				cell.setCellStyle(style);
			}
		}
		try
		{
			FileOutputStream fout = new FileOutputStream(fileName);
			wb.write(fout);
			fout.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try {
				if(wb != null)
					wb.close();
			} catch (IOException err) {
				err.printStackTrace();
			}
			
			return false;
		}
		
		try {
			if(wb != null)
				wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		return true;
	}

	public void initTable(LinkedList<ColumnInfo> cols)
	{
		this.colList = cols;
		colMap = new HashMap<String, ColumnInfo>();
		
		int colSize = cols.size();
		String[] keys = new String[colSize];
		String[] names = new String[colSize];
		int[] wids = new int[colSize];
		int[] align = new int[colSize];
		
		
		for(int colIndex = 0; colIndex < colSize; colIndex++)
		{
			ColumnInfo col = cols.get(colIndex);
			
			keys[colIndex] = col.getKeyStr();
			names[colIndex] = col.getName();
			wids[colIndex] = col.getWidth();
			align[colIndex] = col.getAlign().getValue();
			
			colMap.put(col.getKeyStr(), col);
		}
		
		this.setColumnKey(keys);
		this.setColumnName(names);
		this.setColumnWidth(wids);
		this.setAlign(align);
	}
	
	public void enableDetailBtn()
	{
		btnAddLine = new JButton("新增行");
		btnAddLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertRow(getRowCount(), null);
			}
		});
		
		btnInsert = new JButton("插入行");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowIndex = getSelectedRow();
				if(rowIndex >= 0)
					insertRow(rowIndex, null);
				else
					insertRow(getRowCount(), null);
			}
		});
		
		btnRemove = new JButton("删除行");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowIndex = getSelectedRow();
				if(rowIndex >= 0)
					deleteRow(rowIndex);
			}
		});
	}
	
	public void disableDetailBtn()
	{
		if(btnAddLine != null)
			btnAddLine.setVisible(false);
		
		if(btnInsert != null)
			btnInsert.setVisible(false);
	}
	
	public JPanel toJpanel()
	{
		JPanel jp = new JPanel(new BorderLayout());
		JPanel jp_btn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jp_btn.add(btnAddLine);
		jp_btn.add(btnInsert);
		jp_btn.add(btnRemove);
		
		
		jp_btn.setPreferredSize(new Dimension(100, 30));
		jp.add(jp_btn, BorderLayout.NORTH);
		jp.add(this, BorderLayout.CENTER);
		return jp;
	}
	
	public Class<?> getColumnClass(int colIndex)
	{
		if(colList != null && colList.size() > colIndex)
		{
			ColumnInfo col = colList.get(colIndex);
			Class<?> cls = col.getCls();
			return cls;
		}
		return null;
	}

	
	public JButton getBtnAddLine() {
		return btnAddLine;
	}

	public void setBtnAddLine(JButton btnAddLine) {
		this.btnAddLine = btnAddLine;
	}

	public JButton getBtnInsert() {
		return btnInsert;
	}

	public void setBtnInsert(JButton btnInsert) {
		this.btnInsert = btnInsert;
	}

	public JButton getBtnRemove() {
		return btnRemove;
	}

	public void setBtnRemove(JButton btnRemove) {
		this.btnRemove = btnRemove;
	}

	public String[] getColsKey() {
		return colsKey;
	}
	
	public void addEditeStopedActionListener(final TableEditStopedListener ls)
	{
		this.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if(e.getType() == TableModelEvent.UPDATE)
				{
					try {
						int rowIndex = e.getFirstRow();
						int colIndex = e.getColumn();
						String columnKey = SimpleTable.this.getColumnKey(colIndex);
						Object value = SimpleTable.this.getValueAt(rowIndex, colIndex);
						
						TblEditStopedEvent event = new TblEditStopedEvent(rowIndex, colIndex, columnKey, value);
						ls.editStopedAction(event);
					} catch (Exception err) {
						err.printStackTrace();
						new ExpHandle(null, err);
					}
				}
			}
		});
	}
	
	
	
}
