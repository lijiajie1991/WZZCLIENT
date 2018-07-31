package bill.purchase;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

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
import card.CardReader;
import exp.ExpHandle;
import listener.BarcodeListener;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;

public class PurchaseEditUI extends BillEditUI implements BarcodeListener{
	private static final long serialVersionUID = 1L;
	
	private boolean isLoad = false;
	private boolean isWaitBarcode = false;
	
	protected HashMap<String, MeasureUnitInfo> unitMap = null;
	
	protected LabelContainer conBarCode = null;
	protected FormatInputField txtBarCode = null;
	
	protected LabelContainer conPerson = null;
	protected MyComboBox cmbPerson = null;
	
	protected LabelContainer conSupplier = null;
	protected MyComboBox cmbSupplier = null;
	
	protected LabelContainer conMaterial = null;
	protected MyComboBox cmbMaterial = null;
	
	protected LabelContainer conUnit = null;
	protected MyComboBox cmbUnit = null;
	
	protected LabelContainer conQty = null;
	protected FormatInputField txtQty = null;
	
	protected LabelContainer conPrice = null;
	protected FormatInputField txtPrice = null;
	
	protected LabelContainer conAmt = null;
	protected FormatInputField txtAmt = null;
	
	protected LabelContainer conRemark = null;
	protected FormatInputField txtRemark = null;
	
	protected FormatInputField txtSourceBillId = null;
	
	public PurchaseEditUI()
	{
		super();
		
		unitMap = new HashMap<String, MeasureUnitInfo>();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 280);
		
		txtNumber.setEditable(true);
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(340, 5, 300, 30));
		conBizdate.setLabelText("采购日期");
		
		cmbPerson = new MyComboBox();
		conPerson = new LabelContainer(cmbPerson, 120, BorderLayout.WEST, true, "采购员：");
		conPerson.setBounds(new Rectangle(5, 40, 300, 30));
		
		cmbSupplier = new MyComboBox();
		conSupplier = new LabelContainer(cmbSupplier, 120, BorderLayout.WEST, true, "供应商：");
		conSupplier.setBounds(new Rectangle(340, 40, 300, 30));
		
		cmbMaterial = new MyComboBox();
		conMaterial = new LabelContainer(cmbMaterial, 120, BorderLayout.WEST, true, "产品：");
		conMaterial.setBounds(new Rectangle(5, 75, 300, 30));
		
		cmbUnit = new MyComboBox();
		conUnit = new LabelContainer(cmbUnit, 120, BorderLayout.WEST, true, "计量单位：");
		conUnit.setBounds(new Rectangle(340, 75, 300, 30));
		
		txtQty = new FormatInputField(FormatInputField.DT_DOUBLE);
		conQty = new LabelContainer(txtQty, 120, BorderLayout.WEST, true, "数量：");
		conQty.setBounds(new Rectangle(5, 110, 300, 30));
		
		txtPrice = new FormatInputField(FormatInputField.DT_DOUBLE);
		conPrice = new LabelContainer(txtPrice, 120, BorderLayout.WEST, true, "单价：");
		conPrice.setBounds(new Rectangle(340, 110, 300, 30));
		
		txtAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtAmt.setEditable(false);
		conAmt = new LabelContainer(txtAmt, 120, BorderLayout.WEST, true, "金额：");
		conAmt.setBounds(new Rectangle(5, 145, 300, 30));
		
		txtRemark = new FormatInputField();
		conRemark = new LabelContainer(txtRemark, 120, BorderLayout.WEST, true, "备注：");
		conRemark.setBounds(new Rectangle(340, 145, 300, 30));
		
		txtBarCode = new FormatInputField();
		conBarCode = new LabelContainer(txtBarCode, 120, BorderLayout.WEST, true, "条形码：");
		conBarCode.setBounds(new Rectangle(5, 180, 630, 30));
		conBarCode.setVisible(false);
		
		txtSourceBillId = new FormatInputField();
		
		jp.add(conPerson);
		jp.add(conSupplier);
		jp.add(conMaterial);
		jp.add(conUnit);
		jp.add(conQty);
		jp.add(conPrice);
		jp.add(conAmt);
		jp.add(conRemark);
		
		
		ShopParamInfo spInfo = ParamData.getShopParamInfo();
		conBarCode.setVisible(false);
		if(spInfo != null)
			conBarCode.setVisible(spInfo.getIsBarCode());
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		LinkedList<Info> perList = BaseData.getInfoList(PersonInfo.class);
		LinkedList<Info> supList = BaseData.getInfoList(SupplierInfo.class);
		LinkedList<Info> matList = BaseData.getInfoList(MaterialInfo.class);
		LinkedList<Info> unitList = BaseData.getInfoList(MeasureUnitInfo.class);
		
		cmbPerson.addItems(perList.toArray(new PersonInfo[0]));
		cmbSupplier.addItems(supList.toArray(new SupplierInfo[0]));
		cmbMaterial.addItems(matList.toArray(new MaterialInfo[0]));
		cmbUnit.addItems(unitList.toArray(new MeasureUnitInfo[0]));
		
		for(Info info : unitList)
			unitMap.put(info.getId(), (MeasureUnitInfo) info);
		
		isWaitBarcode = true;
	}

	public void addListener() throws Exception {
		super.addListener();
		
		cmbMaterial.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					materialChangeAction(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(PurchaseEditUI.this, err);
				}
			}
		});
		
		txtQty.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				try {
					qtyChangeAction(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(PurchaseEditUI.this, err);
				}
			}
			public void focusGained(FocusEvent e) {
			}
		});
		
		txtPrice.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				try {
					priceChangeAction(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(PurchaseEditUI.this, err);
				}
			}
			public void focusGained(FocusEvent e) {
			}
		});
		
		txtBarCode.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			
			public void keyReleased(KeyEvent e) {
				int code = e.getKeyCode();
				if(code == KeyEvent.VK_ENTER)
					try {
						actionReadBarcode(txtBarCode.getText());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
			
			public void keyPressed(KeyEvent e) {
			}
		});
		
		BarcodeScanner.addListener(this.getClass().getName(), this);
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		PurchaseInfo info = (PurchaseInfo) this.editData;
		
		PersonInfo perInfo = (PersonInfo) cmbPerson.getSelectedItem();
		SupplierInfo supInfo = (SupplierInfo) cmbSupplier.getSelectedItem();
		MaterialInfo mInfo = (MaterialInfo) cmbMaterial.getSelectedItem();
		MeasureUnitInfo unitInfo = (MeasureUnitInfo) cmbUnit.getSelectedItem();
		BigDecimal qty = txtQty.getBigDecimalValue();
		BigDecimal price = txtPrice.getBigDecimalValue();
		BigDecimal amt = txtAmt.getBigDecimalValue();
		String remark = txtRemark.getText();
		String sourceBillId = txtSourceBillId.getText();
		
		info.setPersonInfo(perInfo);
		info.setSupplierInfo(supInfo);
		info.setMaterialInfo(mInfo);
		info.setUnitInfo(unitInfo);
		info.setQty(qty);
		info.setPrice(price);
		info.setAmt(amt);
		info.setRemark(remark);
		info.setSourceBillId(sourceBillId);
	}

	protected void loadFiles() throws Exception {
		isLoad = false;
		
		super.loadFiles();
		PurchaseInfo info = (PurchaseInfo) this.editData;
		
		cmbPerson.setSelectedItem(info.getPersonInfo());
		cmbSupplier.setSelectedItem(info.getSupplierInfo());
		cmbMaterial.setSelectedItem(info.getMaterialInfo());
		cmbUnit.setSelectedItem(info.getUnitInfo());
		txtQty.setBigDecimalValue(info.getQty());
		txtPrice.setBigDecimalValue(info.getPrice());
		txtAmt.setBigDecimalValue(info.getAmt());
		txtRemark.setText(info.getRemark());
		txtSourceBillId.setText(info.getSourceBillId());
		
		isLoad = true;
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
	}

	protected void materialChangeAction(ItemEvent e) throws Exception
	{
		if(!isLoad)
			return;
		
		if(e.getStateChange() != ItemEvent.SELECTED)
			return;
		
		MaterialInfo mInfo = (MaterialInfo) e.getItem();
		if(mInfo != null && mInfo.getUnitInfo() != null)
		{
			MeasureUnitInfo unitInfo = mInfo.getUnitInfo();
			if(unitInfo.getId() != null && unitMap.containsKey(unitInfo.getId()))
			{
				unitInfo = unitMap.get(unitInfo.getId());
				cmbUnit.setSelectedItem(unitInfo);
			}
		}
	}
	
	protected void qtyChangeAction(FocusEvent e) throws Exception
	{
		if(!isLoad)
			return;
		
		BigDecimal qty = txtQty.getBigDecimalValue();
		BigDecimal price = txtPrice.getBigDecimalValue();
		BigDecimal amt = qty.multiply(price);
		txtAmt.setBigDecimalValue(amt);
	}
	
	protected void priceChangeAction(FocusEvent e) throws Exception
	{
		if(!isLoad)
			return;
		
		BigDecimal qty = txtQty.getBigDecimalValue();
		BigDecimal price = txtPrice.getBigDecimalValue();
		BigDecimal amt = qty.multiply(price);
		txtAmt.setBigDecimalValue(amt);
	}
	
	protected Class<?> getInfoClass() {
		return PurchaseInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpPurchase.getInstance();
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
		cmbMaterial.setEnabled(!flag);
		cmbUnit.setEnabled(!flag);
		txtQty.setEnabled(!flag);
		txtPrice.setEnabled(!flag);
		txtAmt.setEnabled(!flag);
		txtRemark.setEnabled(!flag);
	}

	protected void actionClose(ActionEvent e) throws Exception {
		CardReader.removeListener(this.getClass().getName());
		super.actionClose(e);
	}
	
	public void actionReadBarcode(String barcode) throws Exception {
		if(!isWaitBarcode)
			return;
		isWaitBarcode = false;
		
		MaterialInfo mInfo = IHttpMaterial.getInstance().getMaterialInfo(barcode);
		if(mInfo != null && mInfo.getId() != null)
		{
			cmbMaterial.setSelectedItem(mInfo);
			
			isWaitBarcode = true;
			return;
		}
		
		isWaitBarcode = true;
	
	}
}
