package bas.sys.print;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import bas.sys.shop.IHttpShop;
import bas.sys.shop.ShopInfo;
import bean.IHttp;
import bean.Info;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.ShopType;
import em.SortType;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;
import ui.base.EditUI;

public class PrintEditUI extends EditUI{
	private static final long serialVersionUID = 1L;
	
	protected MyComboBox column_cmbShops = null;
	protected JCheckBox column_chkIsdef = null;
	
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	protected LabelContainer conName = null;
	protected FormatInputField txtName = null;
	
	protected LabelContainer conPrintMethod = null;
	protected FormatInputField txtPrintMethod = null;
	
	protected LabelContainer conClaName = null;
	protected FormatInputField txtClsName = null;
	
	
	protected BeanTable tblShopEntry = null;
	
	public PrintEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 350);
		
		txtNumber = new FormatInputField();
		conNumber = new LabelContainer(txtNumber, 120, BorderLayout.WEST, true, "编号：");
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		
		txtName = new FormatInputField();
		conName = new LabelContainer(txtName, 120, BorderLayout.WEST, true, "名称：");
		conName.setBounds(new Rectangle(340, 5, 300, 30));
		
		txtClsName = new FormatInputField();
		conClaName = new LabelContainer(txtClsName, 120, BorderLayout.WEST, true, "单据路径：");
		conClaName.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtPrintMethod = new FormatInputField();
		conPrintMethod = new LabelContainer(txtPrintMethod, 120, BorderLayout.WEST, true, "打印方法：");
		conPrintMethod.setBounds(new Rectangle(340, 40, 300, 30));
		
		
		column_cmbShops = new MyComboBox();
		DefaultCellEditor column_shop = new DefaultCellEditor(column_cmbShops);
		
		column_chkIsdef = new JCheckBox();
		DefaultCellEditor column_isdef = new DefaultCellEditor(column_chkIsdef);
		
		tblShopEntry = new BeanTable();
		LinkedList<ColumnInfo> shopColList = new LinkedList<ColumnInfo>(); 
		shopColList.addLast(new ColumnInfo("id", "id", 0));
		shopColList.addLast(new ColumnInfo("shopinfo", "店铺", 150, ShopInfo.class));
		shopColList.addLast(new ColumnInfo("isdef", "是否默认", 100, Boolean.class));
		tblShopEntry.initTable(shopColList);
		tblShopEntry.setColumnEditor("shopinfo", column_shop);
		tblShopEntry.setColumnEditor("isdef", column_isdef);
		tblShopEntry.enableDetailBtn();
		tblShopEntry.bindClass(PrintEntryInfo.class);
		JPanel jp_tbl = tblShopEntry.toJpanel();
		jp_tbl.setBounds(new Rectangle(5, 75, 630, 200));
		
		jp.add(conNumber);
		jp.add(conName);
		jp.add(conClaName);
		jp.add(conPrintMethod);
		jp.add(jp_tbl);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.st", CompareType.EQUAL, ShopType.STORE));
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.longnumber", SortType.ASC));
		LinkedList<Info> parentList = IHttpShop.getInstance().getInfoList(filter, order);
		column_cmbShops.addItems(parentList.toArray(new ShopInfo[0]));
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		String number = txtNumber.getText();
		String name = txtName.getText();
		String clsName = txtClsName.getText();
		String printMethod = txtPrintMethod.getText();
		
		PrintInfo info = (PrintInfo) this.editData;
		info.setNumber(number);
		info.setName(name);
		info.setClsName(clsName);
		info.setPrintMethod(printMethod);
		
		LinkedList<PrintEntryInfo> shopList = info.getEntryList();
		shopList.clear();
		shopList.addAll((Collection<? extends PrintEntryInfo>) tblShopEntry.storeFiles());
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		PrintInfo info = (PrintInfo) this.editData;
		txtNumber.setText(info.getNumber());
		txtName.setText(info.getName());
		txtClsName.setText(info.getClsName());
		txtPrintMethod.setText(info.getPrintMethod());
		
		tblShopEntry.loadFiles(info.getEntryList());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
	}

	protected Class<?> getInfoClass() {
		return PrintInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpPrint.getInstance();
	}

	protected String getEditUITitle() {
		return "打印模版";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

}
