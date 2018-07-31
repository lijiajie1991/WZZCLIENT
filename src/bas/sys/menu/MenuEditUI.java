package bas.sys.menu;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JCheckBox;

import bean.IHttp;
import bean.Info;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import common.util.DateTimeUtil;
import em.CompareType;
import em.MenuType;
import em.SortType;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import ui.base.EditUI;

public class MenuEditUI extends EditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conParent = null;
	protected MyComboBox cmbParent = null;
	
	protected LabelContainer conType = null;
	protected MyComboBox cmbType = null;
	
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	protected LabelContainer conName = null;
	protected FormatInputField txtName = null;
	
	protected LabelContainer conClsName = null;
	protected FormatInputField txtClsName = null;
	
	protected LabelContainer conSeq = null;
	protected FormatInputField txtSeq = null;
	
	protected LabelContainer conRpt = null;
	protected FormatInputField txtRpt = null;
	
	protected JCheckBox chkIsVisable = null;
	
	public MenuEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(700, 200);
		
		cmbParent = new MyComboBox();
		conParent = new LabelContainer(cmbParent, 100, BorderLayout.WEST, true, "上级菜单：");
		conParent.setBounds(new Rectangle(5, 5, 300, 30));
		
		cmbType = new MyComboBox();
		conType = new LabelContainer(cmbType, 100, BorderLayout.WEST, true, "菜单类型：");
		conType.setBounds(new Rectangle(340, 5, 300, 30));
		
		txtNumber = new FormatInputField();
		conNumber = new LabelContainer(txtNumber, 100, BorderLayout.WEST, true, "编号：");
		conNumber.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtName = new FormatInputField();
		conName = new LabelContainer(txtName, 100, BorderLayout.WEST, true, "名称：");
		conName.setBounds(new Rectangle(340, 40, 300, 30));
		
		txtClsName = new FormatInputField();
		conClsName = new LabelContainer(txtClsName, 100, BorderLayout.WEST, true, "路径：");
		conClsName.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtSeq = new FormatInputField(FormatInputField.DT_INTEGER);
		conSeq = new LabelContainer(txtSeq, 100, BorderLayout.WEST, true, "序号：");
		conSeq.setBounds(new Rectangle(340, 75, 300, 30));
		
		txtRpt = new FormatInputField();
		conRpt = new LabelContainer(txtRpt, 100, BorderLayout.WEST, true, "报表ID：");
		conRpt.setBounds(new Rectangle(5, 110, 300, 30));
		
		chkIsVisable = new JCheckBox("是否可见");
		chkIsVisable.setBounds(new Rectangle(340, 110, 300, 30));
		
		jp.add(conParent);
		jp.add(conType);
		jp.add(conNumber);
		jp.add(conName);
		jp.add(conClsName);
		jp.add(conSeq);
		jp.add(conRpt);
		jp.add(chkIsVisable);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.clsName", CompareType.EQUAL, ""));
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.number", SortType.ASC));
		LinkedList<Info> parentList = IHttpMenu.getInstance().getInfoList(filter, order);
		cmbParent.addItems(parentList.toArray(new MenuInfo[0]));
		
		cmbType.addItems(MenuType.values());
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		
		MenuInfo pInfo = (MenuInfo) cmbParent.getSelectedItem();
		MenuType mt = (MenuType) cmbType.getSelectedItem();
		String clsName = txtClsName.getText();
		String number = txtNumber.getText();
		String name = txtName.getText();
		String rptId = txtRpt.getText();
		int seq = txtSeq.getIntValue();
		boolean isVisable = chkIsVisable.isSelected();
		
		MenuInfo info = (MenuInfo) this.editData;
		info.setParentInfo(pInfo);
		info.setMt(mt);
		info.setNumber(number);
		info.setName(name);
		info.setClsName(clsName);
		info.setRptId(rptId);
		info.setSeq(seq);
		info.setIsVisable(isVisable);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		
		MenuInfo info = (MenuInfo) this.editData;
		cmbParent.setSelectedItem(info.getParentInfo());
		cmbType.setSelectedItem(info.getMt());
		txtNumber.setText(info.getNumber());
		txtName.setText(info.getName());
		txtClsName.setText(info.getClsName());
		txtRpt.setText(info.getRptId());
		txtSeq.setIntValue(info.getSeq());
		chkIsVisable.setSelected(info.getIsVisable());
		
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		MenuInfo info = (MenuInfo) this.editData;
		info.setNumber(ContextUtil.getShopNumber() + DateTimeUtil.getDateStr(new Date(), DateTimeUtil.numberFormat));
		info.setIsVisable(true);
	}

	protected Class<?> getInfoClass() {
		return MenuInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpMenu.getInstance();
	}

	protected String getEditUITitle() {
		return "菜单";
	}

}
