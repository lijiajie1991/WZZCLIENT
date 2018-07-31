package bas.material;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.LinkedList;

import javax.swing.JCheckBox;

import barcode.BarcodeScanner;
import bas.material.group.IHttpMaterialGroup;
import bas.material.group.MaterialGroupInfo;
import bas.measureunit.IHttpMeasureUnit;
import bas.measureunit.MeasureUnitInfo;
import bas.percent.IHttpPercent;
import bas.percent.PercentInfo;
import bean.IHttp;
import bean.Info;
import bean.base.BaseEditUI;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.SortType;
import listener.BarcodeListener;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;

public class MaterialEditUI extends BaseEditUI implements BarcodeListener{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conMg = null;
	protected MyComboBox cmbMg = null;
	
	protected LabelContainer conUnit = null;
	protected MyComboBox cmbUnit = null;
	
	protected LabelContainer conModel = null;
	protected FormatInputField txtModel = null;
	
	protected LabelContainer conPrice = null;
	protected FormatInputField txtPrice = null;
	
	protected LabelContainer conPc = null;
	protected MyComboBox cmbPc = null;
	
	protected LabelContainer conBarCode = null;
	protected FormatInputField txtBarCode = null;
	
	protected JCheckBox chkIsEnable = null;
	
	public MaterialEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 240);
		
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conName.setBounds(new Rectangle(340, 5, 300, 30));
		
		cmbMg = new MyComboBox();
		conMg = new LabelContainer(cmbMg, 120, BorderLayout.WEST, true, "产品类型：");
		conMg.setBounds(new Rectangle(5, 40, 300, 30));
		
		cmbUnit = new MyComboBox();
		conUnit = new LabelContainer(cmbUnit, 120, BorderLayout.WEST, true, "计量单位：");
		conUnit.setBounds(new Rectangle(340, 40, 300, 30));
		
		txtModel = new FormatInputField();
		conModel = new LabelContainer(txtModel, 120, BorderLayout.WEST, true, "规格：");
		conModel.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtPrice = new FormatInputField(FormatInputField.DT_DOUBLE);
		conPrice = new LabelContainer(txtPrice, 120, BorderLayout.WEST, true, "默认单价：");
		conPrice.setBounds(new Rectangle(340, 75, 300, 30));
		
		cmbPc = new MyComboBox();
		conPc = new LabelContainer(cmbPc, 120, BorderLayout.WEST, true, "提成比例：");
		conPc.setBounds(new Rectangle(5, 110, 300, 30));
		
		txtBarCode = new FormatInputField();
		conBarCode = new LabelContainer(txtBarCode, 120, BorderLayout.WEST, true, "条形码：");
		conBarCode.setBounds(new Rectangle(340, 110, 300, 30));
		
		chkIsEnable = new JCheckBox("是否生效");
		chkIsEnable.setBounds(new Rectangle(5, 145, 300, 30));
		
		jp.add(conMg);
		jp.add(conUnit);
		jp.add(conModel);
		jp.add(conPrice);
		jp.add(conPc);
		jp.add(conBarCode);
		jp.add(chkIsEnable);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		LinkedList<Info> unitList = IHttpMeasureUnit.getInstance().getInfoList(filter, order);
		cmbUnit.addItems(unitList.toArray(new MeasureUnitInfo[0]));
		
		FilterInfo pcfilter = new FilterInfo();
		pcfilter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		pcfilter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		pcfilter.setMkr("#0 or #1");
		LinkedList<Info> pcList = IHttpPercent.getInstance().getInfoList(pcfilter, order);
		LinkedList<Info> mgList = IHttpMaterialGroup.getInstance().getInfoList(pcfilter, order);
		cmbPc.addItems(pcList.toArray(new PercentInfo[0]));
		cmbMg.addItems(mgList.toArray(new MaterialGroupInfo[0]));
	}

	public void addListener() throws Exception {
		super.addListener();
		BarcodeScanner.addListener(this.getClass().getName(), this);
		
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		MaterialGroupInfo mgInfo = (MaterialGroupInfo) cmbMg.getSelectedItem();
		String model = txtModel.getText();
		BigDecimal price = txtPrice.getBigDecimalValue();
		MeasureUnitInfo unitInfo = (MeasureUnitInfo) cmbUnit.getSelectedItem();
		PercentInfo pcInfo = (PercentInfo) cmbPc.getSelectedItem();
		boolean isEnable = chkIsEnable.isSelected();
		String barCode = txtBarCode.getText();
		
		MaterialInfo info = (MaterialInfo) this.editData;
		info.setMgInfo(mgInfo);
		info.setModel(model);
		info.setUnitInfo(unitInfo);
		info.setPrice(price);
		info.setPcInfo(pcInfo);
		info.setBarCode(barCode);
		info.setIsEnable(isEnable);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		
		MaterialInfo info = (MaterialInfo) this.editData;
		cmbMg.setSelectedItem(info.getMgInfo());
		txtModel.setText(info.getModel());
		txtPrice.setBigDecimalValue(info.getPrice());
		cmbUnit.setSelectedItem(info.getUnitInfo());
		cmbPc.setSelectedItem(info.getPcInfo());
		chkIsEnable.setSelected(info.getIsEnable());
		txtBarCode.setText(info.getBarCode());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		MaterialInfo info = (MaterialInfo) this.editData;
		info.setIsEnable(true);
	}

	protected Class<?> getInfoClass() {
		return MaterialInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpMaterial.getInstance();
	}

	protected String getEditUITitle() {
		return "产品";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception{
		super.actionAddNew(e);
	}
	
	protected void actionClose(ActionEvent e) throws Exception {
		BarcodeScanner.removeListener(this.getClass().getName());
		super.actionClose(e);
	}

	public void actionReadBarcode(String barcode) throws Exception {
		txtBarCode.setText(barcode);
	}
}