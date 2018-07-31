package bas.sys.shop;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;

import bean.IHttp;
import bean.Info;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.CompareType;
import em.ProfessionType;
import em.ShopType;
import em.SortType;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import ui.base.EditUI;

public class ShopEditUI extends EditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conParent = null;
	protected MyComboBox cmbParent = null;
	
	protected LabelContainer conType = null;
	protected MyComboBox cmbType = null;
	
	protected LabelContainer conPft = null;
	protected MyComboBox cmbPft = null;
	
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	protected LabelContainer conName = null;
	protected FormatInputField txtName = null;
	
	protected LabelContainer conAddress = null;
	protected FormatInputField txtAddress = null;
	
	protected LabelContainer conPhone = null;
	protected FormatInputField txtPhone = null;
	
	public ShopEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(700, 200);
		
		cmbParent = new MyComboBox();
		conParent = new LabelContainer(cmbParent, 120, BorderLayout.WEST, true, "上级店铺：");
		conParent.setBounds(new Rectangle(5, 5, 300, 30));
		
		cmbType = new MyComboBox();
		conType = new LabelContainer(cmbType, 120, BorderLayout.WEST, true, "店铺类型：");
		conType.setBounds(new Rectangle(340, 5, 300, 30));
		
		cmbPft = new MyComboBox();
		conPft = new LabelContainer(cmbPft, 120, BorderLayout.WEST, true, "经营类型：");
		conPft.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtNumber = new FormatInputField();
		txtNumber.setEditable(false);
		conNumber = new LabelContainer(txtNumber, 120, BorderLayout.WEST, true, "编号：");
		conNumber.setBounds(new Rectangle(340, 40, 300, 30));
		
		txtName = new FormatInputField();
		conName = new LabelContainer(txtName, 120, BorderLayout.WEST, true, "名称：");
		conName.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtAddress = new FormatInputField();
		conAddress = new LabelContainer(txtAddress, 120, BorderLayout.WEST, true, "地址：");
		conAddress.setBounds(new Rectangle(340, 75, 300, 30));
		
		txtPhone = new FormatInputField();
		conPhone = new LabelContainer(txtPhone, 120, BorderLayout.WEST, true, "联系电话：");
		conPhone.setBounds(new Rectangle(5, 110, 300, 30));
		
		jp.add(conParent);
		jp.add(conType);
		jp.add(conPft);
		jp.add(conNumber);
		jp.add(conName);
		jp.add(conAddress);
		jp.add(conPhone);
		
	}

	public void initToolBar() throws Exception {
		super.initToolBar();
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.st", CompareType.EQUAL, ShopType.ADMIN.getValue()));
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.longnumber", SortType.ASC));
		LinkedList<Info> parentList = IHttpShop.getInstance().getInfoList(filter, order);
		cmbParent.addItems(parentList.toArray(new ShopInfo[parentList.size()]));
		
		cmbType.addItems(ShopType.values());
		cmbPft.addItems(ProfessionType.values());
	}

	public void addListener() throws Exception {
		super.addListener();
		
		cmbParent.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				ShopInfo pInfo = (ShopInfo) e.getItem();
				if(pInfo != null)
				{
					cmbPft.setSelectedItem(pInfo.getPft());
				}
			}
		});
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		
		ShopInfo pInfo = (ShopInfo) cmbParent.getSelectedItem();
		ShopType st = (ShopType) cmbType.getSelectedItem();
		ProfessionType pft = (ProfessionType) cmbPft.getSelectedItem();
		String address = txtAddress.getText();
		String number = txtNumber.getText();
		String name = txtName.getText();
		String phone = txtPhone.getText();
		
		ShopInfo info = (ShopInfo) this.editData;
		info.setParentInfo(pInfo);
		info.setSt(st);
		info.setNumber(number);
		info.setName(name);
		info.setAddress(address);
		info.setPhone(phone);
		info.setPft(pft);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		
		ShopInfo info = (ShopInfo) this.editData;
		
		cmbParent.setSelectedItem(info.getParentInfo());
		cmbType.setSelectedItem(info.getSt());
		cmbPft.setSelectedItem(info.getPft());
		txtNumber.setText(info.getNumber());
		txtName.setText(info.getName());
		txtAddress.setText(info.getAddress());
		txtPhone.setText(info.getPhone());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		ShopInfo info = (ShopInfo) this.editData;
		info.setNumber(IHttpShop.getInstance().getNewNumber());
	}

	protected Class<?> getInfoClass() {
		return ShopInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpShop.getInstance();
	}

	protected String getEditUITitle() {
		return "店铺";
	}

	protected void actionInit() throws Exception
	{
		IHttpShop.getInstance().initShopData(this.editData.getId());
	}
}
