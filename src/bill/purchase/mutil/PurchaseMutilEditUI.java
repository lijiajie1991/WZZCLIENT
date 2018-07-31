package bill.purchase.mutil;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;

import barcode.BarcodeScanner;
import bas.material.IHttpMaterial;
import bas.material.MaterialInfo;
import bas.measureunit.MeasureUnitInfo;
import bas.person.PersonInfo;
import bas.supplier.SupplierInfo;
import bas.sys.shopparam.ShopParamInfo;
import bean.IHttp;
import bean.Info;
import bean.bill.BillEditUI;
import bean.bill.BillInfo;
import cache.BaseData;
import cache.ParamData;
import common.util.BigDecimalUtil;
import listener.BarcodeListener;
import listener.TableEditStopedListener;
import listener.event.TblEditStopedEvent;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;

public class PurchaseMutilEditUI extends BillEditUI implements BarcodeListener{
	private static final long serialVersionUID = 1L;
	
	private boolean isLoad = false;
	private boolean isWaitBarcode = false;
	
	//缓存数据
	protected HashMap<String, MeasureUnitInfo> unitMap = null;  //单位缓存
	
	//界面控件
	protected LabelContainer conBarCode = null;
	protected FormatInputField txtBarCode = null;
	
	protected LabelContainer conPerson = null;
	protected MyComboBox cmbPerson = null;
	
	protected LabelContainer conSupplier = null;
	protected MyComboBox cmbSupplier = null;
	
	protected LabelContainer conAmt = null;
	protected FormatInputField txtAmt = null;
	
	protected LabelContainer conRemark = null;
	protected FormatInputField txtRemark = null;
	
	//分录控件
	protected MyComboBox column_cmbMaterial = null;
	protected MyComboBox column_cmbUnit = null;
	protected FormatInputField column_txtQty = null;
	protected FormatInputField column_txtPrice = null;
	protected FormatInputField column_txtAmt = null;
	protected FormatInputField column_txtRemark = null;
	protected BeanTable tblEntry = null;
	
	public PurchaseMutilEditUI()
	{
		super();
		
		unitMap = new HashMap<String, MeasureUnitInfo>();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 510);
		
		txtNumber.setEditable(true);
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(340, 5, 300, 30));
		conBizdate.setLabelText("销售日期");
		
		cmbPerson = new MyComboBox();
		conPerson = new LabelContainer(cmbPerson, 120, BorderLayout.WEST, true, "采购员：");
		conPerson.setBounds(new Rectangle(5, 40, 300, 30));
		
		cmbSupplier = new MyComboBox();
		conSupplier = new LabelContainer(cmbSupplier, 120, BorderLayout.WEST, true, "供应商：");
		conSupplier.setBounds(new Rectangle(340, 40, 300, 30));
		
		txtAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtAmt.setEditable(false);
		conAmt = new LabelContainer(txtAmt, 120, BorderLayout.WEST, true, "产品金额：");
		conAmt.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtRemark = new FormatInputField();
		conRemark = new LabelContainer(txtRemark, 120, BorderLayout.WEST, true, "备注：");
		conRemark.setBounds(new Rectangle(340, 75, 300, 30));
		
		txtBarCode = new FormatInputField();
		conBarCode = new LabelContainer(txtBarCode, 120, BorderLayout.WEST, true, "条形码：");
		conBarCode.setBounds(new Rectangle(5, 110, 630, 30));
		conBarCode.setVisible(false);
		
		//分录控件初始化
		column_cmbMaterial = new MyComboBox();
		DefaultCellEditor column_material = new DefaultCellEditor(column_cmbMaterial);
		
		column_cmbUnit = new MyComboBox();
		DefaultCellEditor column_unit = new DefaultCellEditor(column_cmbUnit);
		
		column_txtQty = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor column_qty = new DefaultCellEditor(column_txtQty);
		
		column_txtPrice = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor column_price = new DefaultCellEditor(column_txtPrice);
		
		column_txtAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor column_amt = new DefaultCellEditor(column_txtAmt);
		
		column_txtRemark = new FormatInputField();
		DefaultCellEditor column_remark = new DefaultCellEditor(column_txtRemark);
		
		tblEntry = new BeanTable();
		LinkedList<ColumnInfo> matColList = new LinkedList<ColumnInfo>(); 
		matColList.addLast(new ColumnInfo("id", "id", 0));
		matColList.addLast(new ColumnInfo("materialinfo", "产品", 100, MaterialInfo.class));
		matColList.addLast(new ColumnInfo("unitinfo", "计量单位", 100, MeasureUnitInfo.class));
		matColList.addLast(new ColumnInfo("price", "单价", 100, BigDecimal.class));
		matColList.addLast(new ColumnInfo("qty", "数量", 100, BigDecimal.class));
		matColList.addLast(new ColumnInfo("amt", "产品金额", 100, BigDecimal.class));
		matColList.addLast(new ColumnInfo("remark", "说明", 200, String.class));
		tblEntry.initTable(matColList);
		tblEntry.setColumnEditor("materialinfo", column_material);
		tblEntry.setColumnEditor("unitinfo", column_unit);
		tblEntry.setColumnEditor("price", column_price);
		tblEntry.setColumnEditor("qty", column_qty);
		tblEntry.setColumnEditor("amt", column_amt);
		tblEntry.setColumnEditor("remark", column_remark);
		tblEntry.enableDetailBtn();
		tblEntry.bindClass(PurchaseMutilEntryInfo.class);
		JPanel jp_tbl = tblEntry.toJpanel();
		jp_tbl.setBounds(new Rectangle(5, 145, 630, 300));
		
		jp.add(conPerson);
		jp.add(conSupplier);
		jp.add(conAmt);
		jp.add(conRemark);
		jp.add(conBarCode);
		jp.add(jp_tbl);
		
		ShopParamInfo spInfo = ParamData.getShopParamInfo();
		conBarCode.setVisible(false);
		if(spInfo != null)
			conBarCode.setVisible(spInfo.getIsBarCode());
	}

	public void setUIContext(HashMap<String, Object> ctx) throws Exception
	{
		super.setUIContext(ctx);
	}
	
	public void initToolBar() throws Exception
	{
		super.initToolBar();
	}
	
	protected void onLoad() throws Exception {
		super.onLoad();
		LinkedList<Info> perList = BaseData.getInfoList(PersonInfo.class);
		LinkedList<Info> supList = BaseData.getInfoList(SupplierInfo.class);
		LinkedList<Info> matList = BaseData.getInfoList(MaterialInfo.class);
		LinkedList<Info> unitList = BaseData.getInfoList(MeasureUnitInfo.class);
		
		cmbPerson.addItems(perList.toArray(new PersonInfo[0]));
		cmbSupplier.addItems(supList.toArray(new SupplierInfo[0]));
		column_cmbMaterial.addItems(matList.toArray(new MaterialInfo[0]));
		column_cmbUnit.addItems(unitList.toArray(new MeasureUnitInfo[0]));
		
		for(Info info : unitList)
			unitMap.put(info.getId(), (MeasureUnitInfo) info);
		
		isWaitBarcode = true;
	}

	public void addListener() throws Exception {
		super.addListener();
		
		BarcodeScanner.addListener(this.getClass().getName(), this);
		
		tblEntry.addEditeStopedActionListener(new TableEditStopedListener() {
			public void editStopedAction(TblEditStopedEvent e) throws Exception {
				tblEntry_editStoped(e);
			}
		});
		
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		PurchaseMutilInfo info = (PurchaseMutilInfo) this.editData;
		
		PersonInfo pInfo = (PersonInfo) cmbPerson.getSelectedItem();
		SupplierInfo supInfo = (SupplierInfo) cmbSupplier.getSelectedItem();
		BigDecimal amt = txtAmt.getBigDecimalValue();
		String remark = txtRemark.getText();
		
		info.setPersonInfo(pInfo);
		info.setSupplierInfo(supInfo);
		info.setAmt(amt);
		info.setRemark(remark);
		
		LinkedList<PurchaseMutilEntryInfo> entryList = info.getEntryList();
		entryList.clear();
		entryList.addAll((Collection<? extends PurchaseMutilEntryInfo>) tblEntry.storeFiles());
	}

	protected void loadFiles() throws Exception {
		isLoad = false;
		super.loadFiles();
		PurchaseMutilInfo info = (PurchaseMutilInfo) this.editData;
		
		cmbPerson.setSelectedItem(info.getPersonInfo());
		cmbSupplier.setSelectedItem(info.getSupplierInfo());
		txtAmt.setBigDecimalValue(info.getAmt());
		txtRemark.setText(info.getRemark());
		
		tblEntry.loadFiles(info.getEntryList());
		isLoad = true;
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
	}
	
	protected void tblEntry_editStoped(TblEditStopedEvent e) throws Exception
	{
		if(!isLoad)
			return;
		
		isLoad = false;
		
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		
		if(rowIndex < 0 || colIndex < 0)
			return;
		
		String columnkey = e.getColumnKey();
		Object value = e.getValue();
		
		BigDecimal qty = BigDecimalUtil.getBigDecimal(tblEntry.getValueAt(rowIndex, "qty"));
		if(BigDecimal.ZERO.compareTo(qty) == 0)
		{
			tblEntry.setValueAt(BigDecimal.ONE, rowIndex, "qty");
		}
		
		if("materialinfo".equals(columnkey))
		{
			materialChangeAction(rowIndex, (MaterialInfo) value);
		}
		else if("price".equals(columnkey))
		{
			priceChangeAction(rowIndex);
		}
		else if("qty".equals(columnkey))
		{
			qtyChangeAction(rowIndex);
		}
		
		isLoad = true;
	}
	
	protected void materialChangeAction(int rowIndex, MaterialInfo mInfo) throws Exception
	{
		if(mInfo != null && mInfo.getUnitInfo() != null)
		{
			MeasureUnitInfo unitInfo = mInfo.getUnitInfo();
			if(unitInfo.getId() != null && unitMap.containsKey(unitInfo.getId()))
			{
				unitInfo = unitMap.get(unitInfo.getId());
				tblEntry.setValueAt(unitInfo, rowIndex, "unitinfo");
			}
		}
	}
	
	protected void qtyChangeAction(int rowIndex) throws Exception
	{
		reCalAmt(rowIndex);
	}
	
	protected void priceChangeAction(int rowIndex) throws Exception
	{
		reCalAmt(rowIndex);
	}
	
	protected void reCalAmt(int rowIndex)
	{
		BigDecimal qty = BigDecimalUtil.getBigDecimal(tblEntry.getValueAt(rowIndex, "qty"));
		BigDecimal price = BigDecimalUtil.getBigDecimal(tblEntry.getValueAt(rowIndex, "price"));
		BigDecimal amt = qty.multiply(price).setScale(2, BigDecimal.ROUND_DOWN);
		tblEntry.setValueAt(amt, rowIndex, "amt");
		
		BigDecimal totalAmt = BigDecimal.ZERO;
		int rowCount = tblEntry.getRowCount();
		for(rowIndex = 0; rowIndex < rowCount; rowIndex++)
		{
			amt = BigDecimalUtil.getBigDecimal(tblEntry.getValueAt(rowIndex, "amt"));
			totalAmt = totalAmt.add(amt);
		}
		txtAmt.setBigDecimalValue(totalAmt);
	}
	
	protected Class<?> getInfoClass() {
		return PurchaseMutilInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpPurchaseMutil.getInstance();
	}

	protected String getEditUITitle() {
		return "采购单";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}


	protected void setCompentStatus() {
		super.setCompentStatus();
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		
		cmbPerson.setEnabled(!flag);
		cmbSupplier.setEnabled(!flag);
		txtAmt.setEnabled(!flag);
		txtRemark.setEnabled(!flag);
		tblEntry.setEnable(!flag);
	}
	
	public void actionReadBarcode(String barcode) throws Exception {

		if(!isWaitBarcode)
			return;
		isWaitBarcode = false;
		
		MaterialInfo mInfo = IHttpMaterial.getInstance().getMaterialInfo(barcode);
		if(mInfo != null && mInfo.getId() != null)
		{
			int rowIndex = tblEntry.getRowCount();
			tblEntry.insertRow(rowIndex, null);
			tblEntry.setValueAt(mInfo, rowIndex, "materialinfo");
			this.materialChangeAction(rowIndex, mInfo);
			isWaitBarcode = true;
			return;
		}
		
		isWaitBarcode = true;
	
	}
	
}
