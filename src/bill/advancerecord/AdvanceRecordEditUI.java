package bill.advancerecord;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.LinkedList;

import javax.swing.JCheckBox;

import bas.person.IHttpPerson;
import bas.person.PersonInfo;
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
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;

public class AdvanceRecordEditUI extends BillEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conPerson = null;
	protected MyComboBox cmbPerson = null;
	
	protected LabelContainer conAmt = null;
	protected FormatInputField txtAmt = null;
	
	protected LabelContainer conRemark = null;
	protected FormatInputField txtRemark = null;
	
	protected JCheckBox chkIsBalance = null;
	
	public AdvanceRecordEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 200);
		
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(340, 5, 300, 30));
		conBizdate.setLabelText("考勤日期");
		
		cmbPerson = new MyComboBox();
		conPerson = new LabelContainer(cmbPerson, 120, BorderLayout.WEST, true, "员工：");
		conPerson.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtAmt = new FormatInputField(FormatInputField.DT_INTEGER);
		conAmt = new LabelContainer(txtAmt, 120, BorderLayout.WEST, true, "金额：");
		conAmt.setBounds(new Rectangle(340, 40, 300, 30));
		
		txtRemark = new FormatInputField();
		conRemark = new LabelContainer(txtRemark, 120, BorderLayout.WEST, true, "备注：");
		conRemark.setBounds(new Rectangle(5, 75, 300, 30));
		
		chkIsBalance = new JCheckBox("是否已结算");
		chkIsBalance.setBounds(new Rectangle(340, 75, 300, 30));
		
		
		jp.add(conPerson);
		jp.add(conAmt);
		jp.add(conRemark);
		jp.add(chkIsBalance);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		
		FilterInfo personfilter = new FilterInfo();
		personfilter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		personfilter.addItem(new FilterItemInfo("a.pst", CompareType.EQUAL, "0"));
		personfilter.setMkr("#0 and #1");
		LinkedList<Info> perList = IHttpPerson.getInstance().getInfoList(personfilter, order);
		
		cmbPerson.addItems(perList.toArray(new PersonInfo[0]));
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		AdvanceRecordInfo info = (AdvanceRecordInfo) this.editData;
		
		PersonInfo pInfo = (PersonInfo) cmbPerson.getSelectedItem();
		boolean isBalance = chkIsBalance.isSelected();
		BigDecimal amt = txtAmt.getBigDecimalValue();
		String remark = txtRemark.getText();
		
		info.setPersonInfo(pInfo);
		info.setIsBalance(isBalance);
		info.setAmt(amt);
		info.setRemark(remark);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		AdvanceRecordInfo info = (AdvanceRecordInfo) this.editData;
		
		cmbPerson.setSelectedItem(info.getPersonInfo());
		txtAmt.setBigDecimalValue(info.getAmt());
		chkIsBalance.setSelected(info.getIsBalance());
		txtRemark.setText(info.getRemark());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		AdvanceRecordInfo info = (AdvanceRecordInfo) this.editData;
		info.setIsBalance(false);
		
	}
	
	protected Class<?> getInfoClass() {
		return AdvanceRecordInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpAdvanceRecord.getInstance();
	}

	protected String getEditUITitle() {
		return "预支记录";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

	protected void setCompentStatus() {
		super.setCompentStatus();
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		
		cmbPerson.setEnabled(!flag);
		txtAmt.setEnabled(!flag);
		chkIsBalance.setEnabled(false);
		txtRemark.setEnabled(!flag);
	}

	protected void actionClose(ActionEvent e) throws Exception {
		super.actionClose(e);
	}
}
