package bas.sys.permission;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.LinkedList;

import bean.IHttp;
import bean.Info;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import common.util.DateTimeUtil;
import em.CompareType;
import em.PermissionType;
import em.SortType;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import ui.base.EditUI;

public class PermissionEditUI extends EditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conParent = null;
	protected MyComboBox cmbParent = null;
	
	protected LabelContainer conType = null;
	protected MyComboBox cmbType = null;
	
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	protected LabelContainer conName = null;
	protected FormatInputField txtName = null;
	
	protected LabelContainer conUipath = null;
	protected FormatInputField txtUipath = null;
	
	protected LabelContainer conBtnpath = null;
	protected FormatInputField txtBtnpath = null;
	
	
	public PermissionEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(700, 200);
		
		cmbParent = new MyComboBox();
		conParent = new LabelContainer(cmbParent, 120, BorderLayout.WEST, true, "上级权限：");
		conParent.setBounds(new Rectangle(5, 5, 300, 30));
		
		cmbType = new MyComboBox();
		conType = new LabelContainer(cmbType, 120, BorderLayout.WEST, true, "权限类型：");
		conType.setBounds(new Rectangle(340, 5, 300, 30));
		
		txtNumber = new FormatInputField();
		conNumber = new LabelContainer(txtNumber, 120, BorderLayout.WEST, true, "编号：");
		conNumber.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtName = new FormatInputField();
		conName = new LabelContainer(txtName, 120, BorderLayout.WEST, true, "名称：");
		conName.setBounds(new Rectangle(340, 40, 300, 30));
		
		txtUipath = new FormatInputField();
		conUipath = new LabelContainer(txtUipath, 120, BorderLayout.WEST, true, "路径：");
		conUipath.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtBtnpath = new FormatInputField();
		conBtnpath = new LabelContainer(txtBtnpath, 120, BorderLayout.WEST, true, "按钮（操作）：");
		conBtnpath.setBounds(new Rectangle(340, 75, 300, 30));
		
		jp.add(conParent);
		jp.add(conType);
		jp.add(conNumber);
		jp.add(conName);
		jp.add(conUipath);
		jp.add(conBtnpath);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.pt", CompareType.NOTEQUAL, PermissionType.BTN.getValue()));
		filter.addItem(new FilterItemInfo("a.pt", CompareType.NOTEQUAL, PermissionType.ACTION.getValue()));
		filter.setMkr("#0 and #1");
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.longnumber", SortType.ASC));
		LinkedList<Info> parentList = IHttpPermission.getInstance().getInfoList(filter, order);
		cmbParent.addItems(parentList.toArray(new PermissionInfo[0]));
		cmbType.addItems(PermissionType.values());
	}

	public void addListener() throws Exception {
		super.addListener();
		
		cmbParent.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				PermissionInfo info = (PermissionInfo) e.getItem();
				String up = info.getUipath();
				txtUipath.setText(up);
			}
		});
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		
		PermissionInfo pInfo = (PermissionInfo) cmbParent.getSelectedItem();
		PermissionType pt = (PermissionType) cmbType.getSelectedItem();
		String uipath = txtUipath.getText();
		String number = txtNumber.getText();
		String name = txtName.getText();
		String btnpath = txtBtnpath.getText();
		
		PermissionInfo info = (PermissionInfo) this.editData;
		info.setParentInfo(pInfo);
		info.setPt(pt);
		info.setNumber(number);
		info.setName(name);
		info.setUipath(uipath);
		info.setBtnpath(btnpath);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		
		PermissionInfo info = (PermissionInfo) this.editData;
		cmbParent.setSelectedItem(info.getParentInfo());
		cmbType.setSelectedItem(info.getPt());
		txtNumber.setText(info.getNumber());
		txtName.setText(info.getName());
		txtUipath.setText(info.getUipath());
		txtBtnpath.setText(info.getBtnpath());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		PermissionInfo info = (PermissionInfo) this.editData;
		info.setNumber(ContextUtil.getShopNumber() + DateTimeUtil.getDateStr(new Date(), DateTimeUtil.numberFormat));
	}

	protected Class<?> getInfoClass() {
		return PermissionInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpPermission.getInstance();
	}

	protected String getEditUITitle() {
		return "权限项";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.pt", CompareType.NOTEQUAL, PermissionType.BTN.getValue()));
		filter.addItem(new FilterItemInfo("a.pt", CompareType.NOTEQUAL, PermissionType.ACTION.getValue()));
		filter.setMkr("#0 and #1");
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.longnumber", SortType.ASC));
		LinkedList<Info> parentList = IHttpPermission.getInstance().getInfoList(filter, order);
		cmbParent.removeAllItems();
		cmbParent.addItems(parentList.toArray(new PermissionInfo[0]));
	}

}
