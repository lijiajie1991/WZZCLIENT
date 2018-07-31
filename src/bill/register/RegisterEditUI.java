package bill.register;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import bean.IHttp;
import bean.bill.BillEditUI;
import bean.bill.BillInfo;
import em.ProfessionType;
import exp.BizException;
import exp.ExpHandle;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.msgbox.MsgBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;

public class RegisterEditUI extends BillEditUI{
	private static final long serialVersionUID = 1L;
	
	protected FormatInputField column_txt = null;
	
	protected LabelContainer conName = null;
	protected FormatInputField txtName = null;
	
	protected LabelContainer conPft = null;
	protected MyComboBox cmbPft = null;
	
	protected LabelContainer conAddress = null;
	protected FormatInputField txtAddress = null;
	
	protected LabelContainer conPhone = null;
	protected FormatInputField txtPhone = null;
	
	protected LabelContainer conHour = null;
	protected MyComboBox cmbHour = null;
	
	protected LabelContainer conDay = null;
	protected MyComboBox cmbDay = null;
	
	protected JCheckBox chkIsSaleEntry = null;
	protected JCheckBox chkIsPurchaseEntry = null;
	protected JCheckBox chkIsBossSms = null;
	protected JCheckBox chkIsCustomerSms = null;
	
	protected LabelContainer conSmsPhone = null;
	protected FormatInputField txtSmsPhone = null;
	
	protected LabelContainer conRemark = null;
	protected FormatInputField txtRemark = null;
	
	protected BeanTable tblShopEntry = null;
	
	public RegisterEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 550);
		
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(3000, 5, 300, 30));
		
		cmbPft = new MyComboBox();
		conPft = new LabelContainer(cmbPft, 120, BorderLayout.WEST, true, "经营类型：");
		conPft.setBounds(new Rectangle(340, 5, 300, 30));
		
		txtName = new FormatInputField();
		conName = new LabelContainer(txtName, 120, BorderLayout.WEST, true, "店名：");
		conName.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtPhone = new FormatInputField();
		conPhone = new LabelContainer(txtPhone, 120, BorderLayout.WEST, true, "店铺电话：");
		conPhone.setBounds(new Rectangle(340, 40, 300, 30));
		
		txtAddress = new FormatInputField();
		conAddress = new LabelContainer(txtAddress, 120, BorderLayout.WEST, true, "地址：");
		conAddress.setBounds(new Rectangle(5, 75, 630, 30));
		
		
		chkIsSaleEntry = new JCheckBox("启用多分录销售单");
		chkIsSaleEntry.setBounds(new Rectangle(5, 110, 200, 30));
		
		chkIsPurchaseEntry = new JCheckBox("启用多分录采购单");
		chkIsPurchaseEntry.setBounds(new Rectangle(210, 110, 200, 30));
		
		chkIsBossSms = new JCheckBox("启用营业情况短信");
		chkIsBossSms.setBounds(new Rectangle(415, 145, 200, 30));
		
		chkIsCustomerSms = new JCheckBox("启用客户短信");
		chkIsCustomerSms.setBounds(new Rectangle(5, 145, 300, 30));
		
		txtSmsPhone = new FormatInputField();
		conSmsPhone = new LabelContainer(txtSmsPhone, 120, BorderLayout.WEST, true, "短信接收手机号码：");
		conSmsPhone.setBounds(new Rectangle(340, 145, 300, 30));
		
		
		cmbHour = new MyComboBox();
		conHour = new LabelContainer(cmbHour, 120, BorderLayout.WEST, true, "短信发送时间(时)");
		conHour.setBounds(new Rectangle(5, 180, 300, 30));
		
		cmbDay = new MyComboBox();
		conDay = new LabelContainer(cmbDay, 120, BorderLayout.WEST, true, "统计日期");
		conDay.setBounds(new Rectangle(340, 180, 300, 30));
		
		txtRemark = new FormatInputField();
		txtRemark.setEditable(false);
		conRemark = new LabelContainer(txtRemark, 120, BorderLayout.WEST, true, "注册信息：");
		conRemark.setBounds(new Rectangle(5, 215, 630, 30));
		
		
		column_txt = new FormatInputField();
		DefaultCellEditor column_txtEditor = new DefaultCellEditor(column_txt);
		
		tblShopEntry = new BeanTable();
		LinkedList<ColumnInfo> shopColList = new LinkedList<ColumnInfo>(); 
		shopColList.addLast(new ColumnInfo("id", "id", 0));
		shopColList.addLast(new ColumnInfo("shopname", "店铺名称", 150));
		shopColList.addLast(new ColumnInfo("shopphone", "店铺电话", 150));
		shopColList.addLast(new ColumnInfo("shopaddress", "店铺地址", 300));
		tblShopEntry.initTable(shopColList);
		tblShopEntry.setColumnEditor("shopname", column_txtEditor);
		tblShopEntry.setColumnEditor("shopphone", column_txtEditor);
		tblShopEntry.setColumnEditor("shopaddress", column_txtEditor);
		tblShopEntry.enableDetailBtn();
		tblShopEntry.bindClass(RegisterEntryInfo.class);
		
		tblShopEntry.getBtnAddLine().setText("添加店铺");
		tblShopEntry.getBtnInsert().setVisible(false);
		tblShopEntry.getBtnRemove().setText("减少店铺");
		
		JPanel jp_tbl = tblShopEntry.toJpanel();
		jp_tbl.setBounds(new Rectangle(5, 250, 630, 350));
		
		for(int i = 1; i <= 24; i++)
			cmbHour.addItem(i);	
		
		cmbDay.addItem("上一天");
		cmbDay.addItem("当天");
		
		jp.add(conPft);
		jp.add(conNumber);
		jp.add(conName);
		jp.add(conAddress);
		jp.add(conPhone);
		jp.add(conHour);
		jp.add(conDay);
		jp.add(chkIsSaleEntry);
		jp.add(chkIsPurchaseEntry);
		jp.add(chkIsBossSms);
		jp.add(chkIsCustomerSms);
		jp.add(conRemark);
		jp.add(jp_tbl);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		cmbPft.addItems(ProfessionType.values());
	}

	public void initToolBar() throws Exception {
		super.initToolBar();

		btnAuditFalse.setVisible(false);
		btnAuditTrue.setText("确认注册");
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		ProfessionType pft = (ProfessionType) cmbPft.getSelectedItem();
		String shopName = txtName.getText();
		String shopPhone = txtPhone.getText();
		String shopAddress = txtAddress.getText();
		String remark = txtRemark.getText();
		int hour = (Integer)cmbHour.getSelectedItem();
		int day = cmbDay.getSelectedIndex();
		
		String smsPhone = txtSmsPhone.getText();
		boolean isSaleEntry = chkIsSaleEntry.isSelected();
		boolean isPurchaseEntry = chkIsPurchaseEntry.isSelected();
		boolean isBossSms = chkIsBossSms.isSelected();
		boolean isCustomerSms = chkIsCustomerSms.isSelected();
		
		RegisterInfo info = (RegisterInfo) this.editData;
		info.setShopName(shopName);
		info.setShopPhone(shopPhone);
		info.setShopAddress(shopAddress);
		info.setRemark(remark);
		info.setPft(pft);
		info.setHour(hour);
		info.setDay(day);
		info.setIsSaleEntry(isSaleEntry);
		info.setIsPurchaseEntry(isPurchaseEntry);
		info.setIsBossSms(isBossSms);
		info.setIsCustomerSms(isCustomerSms);
		info.setSmsPhone(smsPhone);
		
		
		LinkedList<RegisterEntryInfo> list = info.getEntryList();
		list.clear();
		list.addAll((Collection<? extends RegisterEntryInfo>) tblShopEntry.storeFiles());
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		RegisterInfo info = (RegisterInfo) this.editData;
		cmbPft.setSelectedItem(info.getPft());
		txtName.setText(info.getShopName());
		txtPhone.setText(info.getShopPhone());
		txtAddress.setText(info.getShopAddress());
		txtRemark.setText(info.getRemark());
		chkIsSaleEntry.setSelected(info.getIsSaleEntry());
		chkIsPurchaseEntry.setSelected(info.getIsPurchaseEntry());
		chkIsBossSms.setSelected(info.getIsBossSms());
		chkIsCustomerSms.setSelected(info.getIsCustomerSms());
		txtSmsPhone.setText(info.getSmsPhone());
		
		cmbHour.setSelectedItem(info.getHour());
		cmbDay.setSelectedIndex(info.getDay());
		
		tblShopEntry.loadFiles(info.getEntryList());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		RegisterInfo info = (RegisterInfo) this.editData;
		info.setHour(22);
		info.setDay(1);
		info.setIsBossSms(true);
		info.setIsCustomerSms(true);
		info.setIsPurchaseEntry(true);
		info.setIsSaleEntry(true);
	}

	protected Class<?> getInfoClass() {
		return RegisterInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpRegister.getInstance();
	}

	protected String getEditUITitle() {
		return "注册";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}
	
	protected void setCompentStatus() {
		super.setCompentStatus();
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		cmbPft.setEnabled(!flag);
		txtName.setEnabled(!flag);
		txtPhone.setEnabled(!flag);
		txtAddress.setEnabled(!flag);
		txtPhone.setEnabled(!flag);
		tblShopEntry.setEnable(!flag);
	}

	protected void verifyAuditTrue() throws Exception {
		super.verifyAuditTrue();
		
		RegisterInfo info = (RegisterInfo) this.editData;
		LinkedList<RegisterEntryInfo> list = info.getEntryList();
		if(list == null || list.isEmpty())
		{
			throw new BizException("至少需要有一间店铺！");
		}
	}

	protected void showAuditTrueSuccess() {
		int choice = MsgBox.showConfirm(this, "是否保存到本地", txtRemark.getText());
		if(choice == JOptionPane.YES_OPTION)
		{
			try {
				FileOutputStream out = new FileOutputStream(new File("D:/铭洋.txt"));
				out.write(txtRemark.getText().getBytes());   
	            out.close(); 
	            
	            MsgBox.showInfo(this, "已保存到D:/铭洋.txt");
			} catch (Exception e) {
				new ExpHandle(this, e);
			}   
			 
		}
	}
	
	
}
