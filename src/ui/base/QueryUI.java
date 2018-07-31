package ui.base;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import common.db.FilterInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ImageLoader;
import em.SortType;
import exp.ExpHandle;
import myswing.editor.MyComboBox;
import myswing.table.ColumnInfo;
import myswing.table.SimpleTable;

public abstract class QueryUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JFrame ower = null;
	
	protected ActionListener ls = null;
	
	protected FilterInfo filter = null;
	protected SortInfo sort = null;
	
	private JTabbedPane tab = null;
	protected JPanel jpFilter = null;
	protected SimpleTable tblSort = null;
	
	protected JButton btnOk = null;
	
	public QueryUI()
	{
		super();
	}
	
	public void initUI(LinkedList<ColumnInfo> cols) throws Exception
	{
		this.setLayout(new BorderLayout());
		
		JPanel jp = new JPanel();
		jp.setLayout(null);
		this.add(jp, BorderLayout.CENTER);
		
		tab = new JTabbedPane(JTabbedPane.TOP);
		
		
		tblSort = new SimpleTable(false);
		tblSort.setColumnName(new String[]{"关键字", "类型"});
		tblSort.setColumnKey(new String[]{"key", "st"});
		
		MyComboBox cmbKeys = new MyComboBox();
		cmbKeys.addItems(cols.toArray(new ColumnInfo[0]));
		DefaultCellEditor keyEditor = new DefaultCellEditor(cmbKeys);
		tblSort.setColumnEditor(0, keyEditor);
		
		MyComboBox cmbSort = new MyComboBox();
		cmbSort.addItems(SortType.values());
		DefaultCellEditor sortEditor = new DefaultCellEditor(cmbSort);
		tblSort.setColumnEditor(1, sortEditor);
		
		jpFilter = new JPanel(); 
		jpFilter.setLayout(null);
		tab.add(jpFilter, "过滤条件");
		
		JPanel jpSort = new JPanel(); 
		jpSort.setLayout(new BorderLayout());
		jpSort.add(tblSort, BorderLayout.CENTER);
		tab.add(jpSort, "排序");
		
		tab.setBounds(new Rectangle(5, 5, 500, 250));
		
		btnOk = new JButton("确认");
		btnOk.setBounds(new Rectangle(400, 260, 100, 30));
		
		jp.add(tab);
		jp.add(btnOk);
		
		this.setVisible(false);
		this.setMinimumSize(new Dimension(510, 350));
		this.setMaximumSize(new Dimension(510, 350));
		this.setPreferredSize(new Dimension(510, 350));
		this.setResizable(false);
		this.setTitle("查询");
		this.setIconImage(ImageLoader.getIcon("my_24.png").getImage());
		setLocationRelativeTo(getOwner()); // 居中
		
		
		tblSort.insertRow(0, null);
		tblSort.insertRow(0, null);
		tblSort.insertRow(0, null);
		tblSort.insertRow(0, null);
		tblSort.insertRow(0, null);
		tblSort.insertRow(0, null);
	}

	public void addListener() throws Exception {
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					actionQuery(e);
				}catch(Exception err)
				{
					err.printStackTrace();
					new ExpHandle(QueryUI.this, err);
				}
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(ower != null)
				{
					ower.setVisible(true);
					ower.setEnabled(true);
				}
			}
		});
		
		createFilterAndSort();
	}
	
	protected void actionQuery(ActionEvent e) throws Exception
	{
		createFilterAndSort();
		ower.setVisible(true);
		ower.setEnabled(true);
		this.dispose();
		
		if(ls != null)
			ls.actionPerformed(e);
	}
	
	protected void createFilterAndSort()
	{
		filter = new FilterInfo();
		
		sort = new SortInfo();
		int rowCount = tblSort.getRowCount();
		for(int rowIndex = 0; rowIndex < rowCount; rowIndex++)
		{
			HashMap<String, Object> map = tblSort.getRowMap(rowIndex);
			if(map != null)
			{
				ColumnInfo col = (ColumnInfo) map.get("key");
				SortType st = (SortType) map.get("st");
			
				if(col != null && st != null)
					sort.addItem(new SortItemInfo(col.getKeyStr(), st));
			}
		}
	}
	
	public FilterInfo getFilter() {
		return filter;
	}

	public SortInfo getSort() {
		return sort;
	}
	
	public void setLs(ActionListener ls) {
		this.ls = ls;
	}

	
	public void showUI(JFrame ower)
	{
		this.ower = ower;
		if(this.ower != null)
		{
			ower.setEnabled(false);
		}
		
		this.setEnabled(true);
		this.setVisible(true);
	}
	
	public JFrame getOwer() {
		return ower;
	}

	public void setOwer(JFrame ower) {
		this.ower = ower;
	}
	
	
}
