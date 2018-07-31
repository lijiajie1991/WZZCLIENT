package bill.invcheck;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;

import bas.material.IHttpMaterial;
import bas.material.MaterialInfo;
import bas.measureunit.IHttpMeasureUnit;
import bas.measureunit.MeasureUnitInfo;
import bean.IHttp;
import bean.Info;
import bean.bill.BillEditUI;
import bean.bill.BillInfo;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.SortType;
import exp.ExpHandle;
import listener.DataChangeListener;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;

public class InvCheckEditUI extends BillEditUI{
	private static final long serialVersionUID = 1L;
	
	protected MyComboBox column_cmbMaterial = null;
	protected MyComboBox column_cmbUnit = null;
	protected FormatInputField colunm_txtQty = null;
	
	protected BeanTable tblEntry = null;
	
	public InvCheckEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 550);
		
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(340, 5, 300, 30));
		
		column_cmbMaterial = new MyComboBox();
		DefaultCellEditor column_material = new DefaultCellEditor(column_cmbMaterial);
		
		column_cmbUnit = new MyComboBox();
		DefaultCellEditor column_unit = new DefaultCellEditor(column_cmbUnit);
		
		colunm_txtQty = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor colunm_qty = new DefaultCellEditor(colunm_txtQty);
		
		tblEntry = new BeanTable();
		LinkedList<ColumnInfo> colList = new LinkedList<ColumnInfo>(); 
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("materialInfo", "产品", 100, MaterialInfo.class));
		colList.addLast(new ColumnInfo("unitInfo", "单位", 100, MeasureUnitInfo.class));
		colList.addLast(new ColumnInfo("checkQty", "盘点数量", 100, BigDecimal.class));
		colList.addLast(new ColumnInfo("thisQty", "账面数量", 100, BigDecimal.class));
		colList.addLast(new ColumnInfo("lostQty", "盘亏数量", 100, BigDecimal.class));
		tblEntry.initTable(colList);
		tblEntry.setColumnEditor("materialInfo", column_material);
		tblEntry.setColumnEditor("unitInfo", column_unit);
		tblEntry.setColumnEditor("checkQty", colunm_qty);
		tblEntry.setColumnEditor("thisQty", colunm_qty);
		tblEntry.setColumnEditor("lostQty", colunm_qty);
		tblEntry.setColumEditAble(0, false);
		tblEntry.setColumEditAble(1, false);
		tblEntry.setColumEditAble(2, false);
		tblEntry.setColumEditAble(4, false);
		tblEntry.setColumEditAble(5, false);
		tblEntry.bindClass(InvCheckEntryInfo.class);
		tblEntry.setBounds(new Rectangle(5, 40, 640, 450));
		
		jp.add(tblEntry);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		filter.setMkr("#0 or #1");
		
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		
		LinkedList<Info> matList = IHttpMaterial.getInstance().getInfoList(filter, order);
		column_cmbMaterial.addItems(matList.toArray(new MaterialInfo[0]));
		
		LinkedList<Info> unitList = IHttpMeasureUnit.getInstance().getInfoList(new FilterInfo(), order);
		column_cmbUnit.addItems(unitList.toArray(new MeasureUnitInfo[0]));
	}

	public void addListener() throws Exception {
		super.addListener();
		
		pkBizdate.addDataChangeListener(new DataChangeListener() {
			public void DataChangeAction(Object obj) {
				try {
					loadStock();
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(InvCheckEditUI.this, err);
				}
			}
		});
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		InvCheckInfo info = (InvCheckInfo) this.editData;
		
		LinkedList<InvCheckEntryInfo> entryList = info.getEntryList();
		entryList.clear();
		entryList.addAll((Collection<? extends InvCheckEntryInfo>) tblEntry.storeFiles());
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		InvCheckInfo info = (InvCheckInfo) this.editData;
		tblEntry.loadFiles(info.getEntryList());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		loadStock();
	}

	protected Class<?> getInfoClass() {
		return InvCheckInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpInvCheck.getInstance();
	}

	protected String getEditUITitle() {
		return "盘点单";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}
	
	protected void setCompentStatus() {
		super.setCompentStatus();
		BillInfo info = (BillInfo) this.editData;
		boolean flag = !info.getIsAuditTrue();
		tblEntry.setEnable(flag);
	}
	
	
	
	private void loadStock() throws Exception
	{
		String shopId = ContextUtil.getShopId();
		String dateStr = pkBizdate.getDateStr();
		
		LinkedList<InvCheckEntryInfo> list = IHttpInvCheck.getInstance().getInvCheckEntryList(shopId, dateStr);
		
		InvCheckInfo info = (InvCheckInfo) this.getEditData();
		info.setEntryList(list);
		this.loadFiles();
	}
	
	
	
	
	
	
	
	
	
	
}
