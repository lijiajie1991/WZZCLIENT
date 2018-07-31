package bas.sys.shopparam;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;

import bas.sys.shop.ShopInfo;
import bean.IHttp;
import common.util.ContextUtil;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import ui.base.EditUI;

public class ShopParamEditUI extends EditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	protected LabelContainer conName = null;
	protected FormatInputField txtName = null;
	
	protected LabelContainer conShopPhone = null;
	protected FormatInputField txtShopPhone = null;
	
	protected LabelContainer conHour = null;
	protected MyComboBox cmbHour = null;
	
	protected LabelContainer conDay = null;
	protected MyComboBox cmbDay = null;
	
	protected JCheckBox chkIsSaleEntry = null;
	protected JCheckBox chkIsPurchaseEntry = null;
	protected JCheckBox chkIsBossSms = null;
	protected JCheckBox chkIsCustomerSms = null;
	protected JCheckBox chkIsBarCode = null;
	protected JCheckBox chkIsCustomerCard = null;
	
	public ShopParamEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(320, 340);
		
		txtNumber = new FormatInputField();
		txtNumber.setEditable(false);
		conNumber = new LabelContainer(txtNumber, 120, BorderLayout.WEST, true, "店铺编号：");
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		
		txtName = new FormatInputField();
		txtName.setEditable(false);
		conName = new LabelContainer(txtName, 120, BorderLayout.WEST, true, "店铺名称：");
		conName.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtShopPhone = new FormatInputField();
		conShopPhone = new LabelContainer(txtShopPhone, 120, BorderLayout.WEST, true, "店长手机号：");
		conShopPhone.setBounds(new Rectangle(5, 75, 300, 30));
		
		cmbHour = new MyComboBox();
		conHour = new LabelContainer(cmbHour, 100, BorderLayout.WEST, true, "发送时间(时)");
		conHour.setBounds(new Rectangle(5, 110, 150, 30));
		
		cmbDay = new MyComboBox();
		conDay = new LabelContainer(cmbDay, 80, BorderLayout.WEST, true, "统计日期");
		conDay.setBounds(new Rectangle(155, 110, 150, 30));
		
		chkIsSaleEntry = new JCheckBox("启用多分录销售单");
		chkIsSaleEntry.setBounds(new Rectangle(5, 145, 150, 30));
		
		chkIsPurchaseEntry = new JCheckBox("启用多分录采购单");
		chkIsPurchaseEntry.setBounds(new Rectangle(155, 145, 150, 30));
		
		chkIsBossSms = new JCheckBox("启用营业情况短信");
		chkIsBossSms.setBounds(new Rectangle(5, 180, 150, 30));
		
		chkIsCustomerSms = new JCheckBox("启用客户短信");
		chkIsCustomerSms.setBounds(new Rectangle(155, 180, 150, 30));
		
		chkIsBarCode = new JCheckBox("启用条码销售");
		chkIsBarCode.setBounds(new Rectangle(5, 215, 150, 30));
		
		chkIsCustomerCard = new JCheckBox("启用会员卡");
		chkIsCustomerCard.setBounds(new Rectangle(155, 215, 150, 30));
		
		
		jp.add(conNumber);
		jp.add(conName);
		jp.add(conShopPhone);
		jp.add(conHour);
		jp.add(conDay);
		jp.add(chkIsSaleEntry);
		jp.add(chkIsPurchaseEntry);
		jp.add(chkIsBossSms);
		jp.add(chkIsCustomerSms);
		jp.add(chkIsBarCode);
		jp.add(chkIsCustomerCard);
		
		for(int i = 1; i <= 24; i++)
			cmbHour.addItem(i);	
		
		cmbDay.addItem("上一天");
		cmbDay.addItem("当天");
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		String number = txtNumber.getText();
		String name = txtName.getText();
		String shopPhone = txtShopPhone.getText();
		
		int hour = (Integer)cmbHour.getSelectedItem();
		int day = cmbDay.getSelectedIndex();
		
		boolean isSaleEntry = chkIsSaleEntry.isSelected();
		boolean isPurchaseEntry = chkIsPurchaseEntry.isSelected();
		boolean isBossSms = chkIsBossSms.isSelected();
		boolean isCustomerSms = chkIsCustomerSms.isSelected();
		boolean isSaleBarCode = chkIsBarCode.isSelected();
		boolean isCustomerCard = chkIsCustomerCard.isSelected();
		
		ShopParamInfo info = (ShopParamInfo) this.editData;
		info.setNumber(number);
		info.setName(name);
		info.setShopPhone(shopPhone);
		info.setHour(hour);
		info.setDay(day);
		info.setIsSaleEntry(isSaleEntry);
		info.setIsPurchaseEntry(isPurchaseEntry);
		info.setIsBossSms(isBossSms);
		info.setIsCustomerSms(isCustomerSms);
		info.setIsBarCode(isSaleBarCode);
		info.setIsCustomerCard(isCustomerCard);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		ShopParamInfo info = (ShopParamInfo) this.editData;
		txtNumber.setText(info.getNumber());
		txtName.setText(info.getName());
		txtShopPhone.setText(info.getShopPhone());
		chkIsSaleEntry.setSelected(info.getIsSaleEntry());
		chkIsPurchaseEntry.setSelected(info.getIsPurchaseEntry());
		chkIsBossSms.setSelected(info.getIsBossSms());
		chkIsCustomerSms.setSelected(info.getIsCustomerSms());
		chkIsBarCode.setSelected(info.getIsBarCode());
		chkIsCustomerCard.setSelected(info.getIsCustomerCard());
		
		cmbHour.setSelectedItem(info.getHour());
		cmbDay.setSelectedIndex(info.getDay());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		ShopParamInfo info = (ShopParamInfo) this.getEditData();
		ShopInfo shop = ContextUtil.getShopInfo();
		info.setNumber(shop.getNumber());
		info.setName(shop.getName());
		info.setHour(22);
		info.setDay(1);
		info.setIsBarCode(false);
	}

	protected Class<?> getInfoClass() {
		return ShopParamInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpShopParam.getInstance();
	}

	protected String getEditUITitle() {
		return "店铺参数设置";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

}
