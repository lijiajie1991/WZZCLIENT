package report.base.ui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import bas.sys.menu.IHttpMenu;
import bas.sys.menu.MenuInfo;
import bas.sys.permission.IHttpPermission;
import bas.sys.permission.PermissionInfo;
import bas.sys.shop.IHttpShop;
import bas.sys.shop.ShopInfo;
import bean.Info;
import bean.bill.BillEditUI;
import bean.bill.BillInfo;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import em.CompareType;
import em.MenuType;
import em.OpenStateType;
import em.PermissionType;
import exp.ExpHandle;
import myswing.btn.ToolButton;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.msgbox.MsgBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;
import report.base.bean.RptColumnInfo;
import report.base.bean.RptConditionInfo;
import report.base.bean.RptInfo;
import report.base.bean.RptShopInfo;
import report.base.mj.RptAlign;
import report.base.mj.RptConditionType;
import report.base.mj.RptDataType;
import report.base.mj.RptType;

public abstract class RptEditUI extends BillEditUI{
	private static final long serialVersionUID = 1L;
	
	protected FormatInputField colunm_txtInteger = null;
	protected DefaultCellEditor colunm_Integer = null;
	
	protected FormatInputField colunm_txtStr = null;
	protected DefaultCellEditor colunm_Str = null;
	
	protected MyComboBox column_cmbAlign = null;
	protected DefaultCellEditor column_align = null;

	protected MyComboBox column_cmbDt = null;
	protected DefaultCellEditor column_dt = null;
	
	protected MyComboBox column_cmbCt = null;
	protected DefaultCellEditor column_ct = null;
	
	protected MyComboBox column_cmbShop = null;
	protected DefaultCellEditor column_shop = null;
	
	protected LabelContainer conName = null;
	protected FormatInputField txtName = null;
	
	protected LabelContainer conType = null;
	protected MyComboBox cmbType = null;
	
	protected JCheckBox chkIsFree = null;
	
	protected JTextArea txtSql = null;
	
	protected JTabbedPane tab = null;
	protected BeanTable tblShopEntry = null;
	protected BeanTable tblColEntry = null;
	protected BeanTable tblConEntry = null;
	
	protected ToolButton btnCreateMenu = null;
	protected ToolButton btnDeleteMenu = null;
	
	protected ToolButton btnCreatePermission= null;
	protected ToolButton btnDeletePermission= null;
	
	protected ToolButton btnExport = null;
	protected ToolButton btnImport = null;
	
	public RptEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height)
	{
		super.initUI(1024, 600);
		
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(3000, 5, 300, 30));
		
		txtName = new FormatInputField();
		conName = new LabelContainer(txtName, 60, BorderLayout.WEST, true, "名称");
		conName.setBounds(new Rectangle(340, 5, 300, 30));
		
		cmbType = new MyComboBox();
		cmbType.addItems(RptType.values());
		conType = new LabelContainer(cmbType, 60, BorderLayout.WEST, true, "类型");
		conType.setBounds(new Rectangle(650, 5, 300, 30));
		
		chkIsFree = new JCheckBox("是否免费");
		chkIsFree.setBounds(new Rectangle(5, 40, 300, 30));
		
		
		tab = new JTabbedPane(JTabbedPane.TOP);
		tab.setBounds(new Rectangle(5, 75, 1010, 470));
		
		JPanel jp_query = new JPanel(new BorderLayout());
		txtSql = new JTextArea();
		txtSql.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(txtSql);
		jp_query.add(jsp, BorderLayout.CENTER);
		tab.add(jp_query, "查询SQL");
		
		colunm_txtInteger = new FormatInputField(FormatInputField.DT_INTEGER);
		colunm_Integer = new DefaultCellEditor(colunm_txtInteger);
		
		colunm_txtStr = new FormatInputField();
		colunm_Str = new DefaultCellEditor(colunm_txtStr);
		
		column_cmbAlign = new MyComboBox();
		column_align = new DefaultCellEditor(column_cmbAlign);
		
		column_cmbDt = new MyComboBox();
		column_dt = new DefaultCellEditor(column_cmbDt);
		
		column_cmbCt = new MyComboBox();
		column_ct = new DefaultCellEditor(column_cmbCt);
		
		column_cmbShop = new MyComboBox();
		column_shop = new DefaultCellEditor(column_cmbShop);
		
		JPanel jp_shop = new JPanel(new BorderLayout());
		tblShopEntry = new BeanTable();
		LinkedList<ColumnInfo> shopList = new LinkedList<ColumnInfo>(); 
		shopList.addLast(new ColumnInfo("id", "id", 0));
//		shopList.addLast(new ColumnInfo("seq", "序号", 100, Integer.class));
		shopList.addLast(new ColumnInfo("shopinfo", "店铺", 100, ShopInfo.class));
		tblShopEntry.initTable(shopList);
//		tblShopEntry.setColumnEditor("seq", colunm_Integer);
		tblShopEntry.setColumnEditor("shopinfo", column_shop);
		tblShopEntry.enableDetailBtn();
		tblShopEntry.bindClass(RptShopInfo.class);
		jp_shop.add(tblShopEntry.toJpanel(), BorderLayout.CENTER);
		tab.add(jp_shop, "引用店铺");
		
		JPanel jp_col = new JPanel(new BorderLayout());
		tblColEntry = new BeanTable();
		LinkedList<ColumnInfo> colList = new LinkedList<ColumnInfo>(); 
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("seq", "序号", 100, Integer.class));
		colList.addLast(new ColumnInfo("keystr", "关键字", 100, String.class));
		colList.addLast(new ColumnInfo("name", "名称", 100, String.class));
		colList.addLast(new ColumnInfo("val", "值", 100, String.class));
		colList.addLast(new ColumnInfo("width", "宽度", 100, Integer.class));
		colList.addLast(new ColumnInfo("align", "对齐方式", 100, RptAlign.class));
		colList.addLast(new ColumnInfo("dt", "数据类型", 100, RptDataType.class));
		tblColEntry.initTable(colList);
		tblColEntry.setColumnEditor("seq", colunm_Integer);
		tblColEntry.setColumnEditor("keyStr", colunm_Str);
		tblColEntry.setColumnEditor("name", colunm_Str);
		tblColEntry.setColumnEditor("val", colunm_Str);
		tblColEntry.setColumnEditor("width", colunm_Integer);
		tblColEntry.setColumnEditor("align", column_align);
		tblColEntry.setColumnEditor("dt", column_dt);
		tblColEntry.enableDetailBtn();
		tblColEntry.bindClass(RptColumnInfo.class);
		jp_col.add(tblColEntry.toJpanel(), BorderLayout.CENTER);
		tab.add(jp_col, "列");
		
		JPanel jp_condition = new JPanel(new BorderLayout());
		tblConEntry = new BeanTable();
		LinkedList<ColumnInfo> conList = new LinkedList<ColumnInfo>(); 
		conList.addLast(new ColumnInfo("id", "id", 0));
		conList.addLast(new ColumnInfo("seq", "序号", 100, Integer.class));
		conList.addLast(new ColumnInfo("keystr", "关键字", 100, String.class));
		conList.addLast(new ColumnInfo("name", "名称", 100, String.class));
		conList.addLast(new ColumnInfo("type", "类型", 100, RptConditionType.class));
		conList.addLast(new ColumnInfo("items", "值", 100, String.class));
		conList.addLast(new ColumnInfo("httpcls", "数据源接口", 300, String.class));
		conList.addLast(new ColumnInfo("filter", "过滤条件", 300, String.class));
		tblConEntry.initTable(conList);
		tblConEntry.setColumnEditor("seq", colunm_Integer);
		tblConEntry.setColumnEditor("keyStr", colunm_Str);
		tblConEntry.setColumnEditor("name", colunm_Str);
		tblConEntry.setColumnEditor("type", column_ct);
		tblConEntry.setColumnEditor("items", colunm_Str);
		tblConEntry.setColumnEditor("httpcls", colunm_Str);
		tblConEntry.setColumnEditor("filter", colunm_Str);
		tblConEntry.enableDetailBtn();
		tblConEntry.bindClass(RptConditionInfo.class);
		jp_condition.add(tblConEntry.toJpanel(), BorderLayout.CENTER);
		tab.add(jp_condition, "过滤条件");
		
		jp.add(conNumber);
		jp.add(conName);
		jp.add(conType);
		jp.add(chkIsFree);
		jp.add(tab);
		
		this.setTitle("报表");
	}
	
	public void initToolBar() throws Exception {
		super.initToolBar();
		
		btnCreateMenu = new ToolButton("发布菜单", "");
		btnList.add(3, btnCreateMenu);
		
		btnDeleteMenu = new ToolButton("删除菜单", "");
		btnList.add(4, btnDeleteMenu);
		
		btnCreatePermission = new ToolButton("添加权限", "");
		btnList.add(5, btnCreatePermission);
		
		btnDeletePermission = new ToolButton("删除权限", "");
		btnList.add(6, btnDeletePermission);
		
		btnExport = new ToolButton("导出", "");
		btnList.add(7, btnExport);
		
		btnImport = new ToolButton("导入", "");
		btnList.add(8, btnImport);
	}
	
	public void addListener() throws Exception {
		super.addListener();
		
		btnCreateMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionCreateMenu(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(RptEditUI.this, err);
				}
			}
		});
		
		btnDeleteMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionDeleteMenu(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(RptEditUI.this, err);
				}
			}
		});
		
		btnCreatePermission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionCreatePermission(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(RptEditUI.this, err);
				}
			}
		});
		
		btnDeletePermission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionDeletePermission(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(RptEditUI.this, err);
				}
			}
		});
		
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionExport(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionImport(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
	}
	
	protected void actionCreateMenu(ActionEvent e) throws Exception
	{
		RptInfo info = (RptInfo) this.getEditData();
		MenuInfo pmInfo = new MenuInfo();
		pmInfo.setId("0005-e79b22d4-5a82-413e-a059-953bf107587f");
		
		String rptCls = "";
		RptType rt = info.getType();
		if(rt == RptType.LIST)
			rptCls = "report.listrpt.ui.ListRptUI";
		else if(rt == RptType.CROSS)
			rptCls = "report.crossrpt.ui.CrossRptUI";
		
		MenuInfo mInfo = new MenuInfo();
		mInfo.setParentInfo(pmInfo);
		mInfo.setMt(MenuType.RPT);
		mInfo.setNumber(IHttpMenu.getInstance().getNewNumber());
		mInfo.setName(info.getName());
		mInfo.setClsName(rptCls);
		mInfo.setRptId(info.getId());
		mInfo.setSeq(0);
		mInfo.setIsVisable(true);
		IHttpMenu.getInstance().save(mInfo);
		MsgBox.showInfo(this, "发布成功！");
	}
	
	protected void actionDeleteMenu(ActionEvent e) throws Exception
	{
		RptInfo info = (RptInfo) this.getEditData();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("rptId", CompareType.EQUAL, info.getId()));
		IHttpMenu.getInstance().delete(filter);
		MsgBox.showInfo(this, "相关菜单已删除！");
				
	}
	
	protected void actionCreatePermission(ActionEvent e) throws Exception
	{
		RptInfo info = (RptInfo) this.getEditData();
		
		PermissionInfo pInfo = new PermissionInfo();
		pInfo.setNumber(IHttpPermission.getInstance().getNewNumber());
		RptType rt = info.getType();
		if(rt == RptType.LIST)
			pInfo.setUipath("report.listrpt.ui.ListRptUI");
		else if(rt == RptType.CROSS)
			pInfo.setUipath("report.crossrpt.ui.CrossRptUI");
		
		pInfo.setPt(PermissionType.RPT);
		pInfo.setName(info.getName());
		pInfo.setBtnpath(info.getId());
		IHttpPermission.getInstance().save(pInfo);
		MsgBox.showInfo(this, "已新增相关权限项！");
	}
	
	protected void actionDeletePermission(ActionEvent e) throws Exception
	{
		RptInfo info = (RptInfo) this.getEditData();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("btnpath", CompareType.EQUAL, info.getId()));
		IHttpPermission.getInstance().delete(filter);
		MsgBox.showInfo(this, "相关权限项已删除！");
	}
	

	/**
	 * 
	  * @功能描述 导出报表
	  * @作者 黎嘉杰 
	  * @日期 2016年12月1日 上午10:26:31 
	  * @参数 
	  * @返回
	 */
	protected void actionExport(ActionEvent e) throws Exception{
		
		
		JFileChooser jchoose = new JFileChooser();
		jchoose.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jchoose.setMultiSelectionEnabled(false);
		int state = jchoose.showSaveDialog(this);
		if(state != JFileChooser.APPROVE_OPTION)
			return;
		
		RptInfo info = (RptInfo) this.getEditData();
		File file = jchoose.getSelectedFile();
		FileOutputStream out = new FileOutputStream(file);
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		objOut.writeObject(info);
		objOut.flush();
		objOut.close();
	}
	
	/**
	 * 
	  * @功能描述 导入报表
	  * @作者 黎嘉杰 
	  * @日期 2016年12月1日 上午10:25:26 
	  * @参数 
	  * @返回
	 */
	protected void actionImport(ActionEvent e) throws Exception{
		JFileChooser jchoose = new JFileChooser();
		jchoose.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jchoose.setMultiSelectionEnabled(false);
		int state = jchoose.showOpenDialog(this);
		if(state != JFileChooser.APPROVE_OPTION)
			return;
		
		File file = jchoose.getSelectedFile();
		FileInputStream in = new FileInputStream(file);
		ObjectInputStream objIn = new ObjectInputStream(in);
		RptInfo info = (RptInfo) objIn.readObject();
		objIn.close();
		
		info.setId(null);
		String pk = this.getHttpInstance().save(info);
		this.editData = this.getHttpInstance().getInfo(pk);
		this.loadFiles();
	}

	public void onLoad() throws Exception{
		super.onLoad();
		
		column_cmbCt.addItems(RptConditionType.values());
		column_cmbAlign.addItems(RptAlign.values());
		column_cmbDt.addItems(RptDataType.values());
		
		LinkedList<Info> shopList = IHttpShop.getInstance().getInfoList("a.fst = 1", "");
		column_cmbShop.addItems(shopList.toArray(new ShopInfo[0]));
	}
	
	protected void loadFiles() throws Exception {
		super.loadFiles();
		
		RptInfo info = (RptInfo) this.getEditData();
		txtName.setText(info.getName());
		txtSql.setText(info.getSqlStr());
		cmbType.setSelectedItem(info.getType());
		chkIsFree.setSelected(info.getIsFree());
		
		tblShopEntry.loadFiles(info.getShopList());
		tblColEntry.loadFiles(info.getColList());
		tblConEntry.loadFiles(info.getConList());
		
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		RptInfo info = (RptInfo) this.getEditData();
		
		String name = txtName.getText();
		String sql = txtSql.getText();
		RptType type = (RptType) cmbType.getSelectedItem();
		info.setType(type);
		info.setName(name);
		info.setSqlStr(sql);
		info.setIsFree(chkIsFree.isSelected());
		
		LinkedList<RptShopInfo> shopList = info.getShopList();
		shopList.clear();
		shopList.addAll((Collection<? extends RptShopInfo>) tblShopEntry.storeFiles());
		
		LinkedList<RptColumnInfo> colList = info.getColList();
		colList.clear();
		colList.addAll((Collection<? extends RptColumnInfo>) tblColEntry.storeFiles());
		
		LinkedList<RptConditionInfo> conList = info.getConList();
		conList.clear();
		conList.addAll((Collection<? extends RptConditionInfo>) tblConEntry.storeFiles());
		
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
		tblShopEntry.removeRows();
		tblConEntry.removeRows();
		tblColEntry.removeRows();
	}

	protected void setCompentStatus() {
		super.setCompentStatus();
		
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		boolean isView = OpenStateType.VIEW.equals(this.getOst());
		
		txtName.setEnabled(!flag && !isView);
		cmbType.setEnabled(!flag && !isView);
		txtSql.setEnabled(!flag && !isView);
		chkIsFree.setEnabled(!flag && !isView);
		tblShopEntry.setEnable(!flag && !isView);
		tblColEntry.setEnable(!flag && !isView);
		tblConEntry.setEnable(!flag && !isView);
	}
}
