package bill.attendrecord;

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
import em.AttendanceDaysType;
import em.AttendanceType;
import em.CompareType;
import em.SortType;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;

public class AttendRecordEditUI extends BillEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conPerson = null;
	protected MyComboBox cmbPerson = null;
	
	protected LabelContainer conAt = null;
	protected MyComboBox cmbAt = null;
	
	protected LabelContainer conAdt = null;
	protected MyComboBox cmbAdt = null;
	
	protected LabelContainer conQty = null;
	protected FormatInputField txtQty = null;
	
	protected LabelContainer conRemark = null;
	protected FormatInputField txtRemark = null;
	
	protected JCheckBox chkIsBalance = null;
	
	public AttendRecordEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 220);
		
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(340, 5, 300, 30));
		conBizdate.setLabelText("考勤日期");
		
		cmbPerson = new MyComboBox();
		conPerson = new LabelContainer(cmbPerson, 120, BorderLayout.WEST, true, "员工：");
		conPerson.setBounds(new Rectangle(5, 40, 300, 30));
		
		cmbAt = new MyComboBox();
		conAt = new LabelContainer(cmbAt, 120, BorderLayout.WEST, true, "类型：");
		conAt.setBounds(new Rectangle(340, 40, 300, 30));
		
		cmbAdt = new MyComboBox();
		conAdt = new LabelContainer(cmbAdt, 120, BorderLayout.WEST, true, "统计时间：");
		conAdt.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtQty = new FormatInputField(FormatInputField.DT_INTEGER);
		conQty = new LabelContainer(txtQty, 120, BorderLayout.WEST, true, "次数：");
		conQty.setBounds(new Rectangle(340, 75, 300, 30));
		
		txtRemark = new FormatInputField();
		conRemark = new LabelContainer(txtRemark, 120, BorderLayout.WEST, true, "备注：");
		conRemark.setBounds(new Rectangle(5, 110, 300, 30));
		
		chkIsBalance = new JCheckBox("是否已结算");
		chkIsBalance.setBounds(new Rectangle(340, 110, 300, 30));
		
		
		jp.add(conPerson);
		jp.add(conAt);
		jp.add(conAdt);
		jp.add(conQty);
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
		cmbAt.addItems(AttendanceType.values());
		cmbAdt.addItems(AttendanceDaysType.values());
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		AttendRecordInfo info = (AttendRecordInfo) this.editData;
		
		PersonInfo pInfo = (PersonInfo) cmbPerson.getSelectedItem();
		AttendanceType at = (AttendanceType) cmbAt.getSelectedItem();
		AttendanceDaysType adt = (AttendanceDaysType) cmbAdt.getSelectedItem();
		boolean isBalance = chkIsBalance.isSelected();
		BigDecimal qty = txtQty.getBigDecimalValue();
		String remark = txtRemark.getText();
		
		info.setPersonInfo(pInfo);
		info.setAt(at);
		info.setAdt(adt);
		info.setIsBalance(isBalance);
		info.setQty(qty);
		info.setRemark(remark);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		AttendRecordInfo info = (AttendRecordInfo) this.editData;
		
		cmbPerson.setSelectedItem(info.getPersonInfo());
		cmbAt.setSelectedItem(info.getAt());
		cmbAdt.setSelectedItem(info.getAdt());
		txtQty.setBigDecimalValue(info.getQty());
		chkIsBalance.setSelected(info.getIsBalance());
		txtRemark.setText(info.getRemark());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		AttendRecordInfo info = (AttendRecordInfo) this.editData;
		info.setIsBalance(false);
		info.setAdt(AttendanceDaysType.FullDay);
		info.setQty(BigDecimal.ONE);
		
	}
	
	protected Class<?> getInfoClass() {
		return AttendRecordInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpAttendRecord.getInstance();
	}

	protected String getEditUITitle() {
		return "考勤记录";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

	protected void setCompentStatus() {
		super.setCompentStatus();
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		
		cmbPerson.setEnabled(!flag);
		cmbAt.setEnabled(!flag);
		cmbAdt.setEnabled(!flag);
		txtQty.setEnabled(!flag);
		chkIsBalance.setEnabled(false);
		txtRemark.setEnabled(!flag);
	}

	protected void actionClose(ActionEvent e) throws Exception {
		super.actionClose(e);
	}
}
