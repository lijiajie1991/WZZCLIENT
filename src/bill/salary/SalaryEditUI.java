package bill.salary;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import bas.person.IHttpPerson;
import bas.person.PersonInfo;
import bean.IHttp;
import bean.Info;
import bean.bill.BillEditUI;
import bean.bill.BillInfo;
import bill.service.ServiceEditUI;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import common.util.DateTimeUtil;
import em.CompareType;
import em.OpenStateType;
import em.SortType;
import exp.ExpHandle;
import listener.DataChangeListener;
import myswing.container.LabelContainer;
import myswing.editor.DatePicker;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.msgbox.MsgBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;

public class SalaryEditUI extends BillEditUI{
	private static final long serialVersionUID = 1L;
	
	private boolean isLoad = false;
	
	protected FormatInputField colunm_txtRemark = null;
	protected FormatInputField colunm_txtAmt = null;
	
	protected LabelContainer conPerson = null;
	protected MyComboBox cmbPerson = null;
	
	protected LabelContainer conDateFrom = null;
	protected DatePicker pkDateFrom = null;
	
	protected LabelContainer conDateTo = null;
	protected DatePicker pkDateTo = null;
	
	protected LabelContainer conSalary = null;
	protected FormatInputField txtSalary = null;
	
	protected LabelContainer conBounty = null;
	protected FormatInputField txtBounty = null;
	
	protected LabelContainer conPercentamt = null;
	protected FormatInputField txtPercentamt = null;
	
	protected LabelContainer conPunish = null;
	protected FormatInputField txtPunish = null;
	
	protected LabelContainer conAmt = null;
	protected FormatInputField txtAmt = null;
	
	protected LabelContainer conOtherAmt = null;
	protected FormatInputField txtOtherAmt = null;
	
	protected LabelContainer conRemark = null;
	protected FormatInputField txtRemark = null;
	
	protected JButton btnBal = null;
	
	protected BeanTable tblEntry = null;
	
	public SalaryEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 600);
		
		conNumber.setBounds(new Rectangle(3000, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(3000, 5, 300, 30));
		conBizdate.setLabelText("考勤日期");
		
		cmbPerson = new MyComboBox();
		conPerson = new LabelContainer(cmbPerson, 120, BorderLayout.WEST, true, "员工：");
		conPerson.setBounds(new Rectangle(5, 5, 300, 30));
		
		txtAmt = new FormatInputField(FormatInputField.DT_INTEGER);
		txtAmt.setEditable(false);
		conAmt = new LabelContainer(txtAmt, 120, BorderLayout.WEST, true, "总金额：");
		conAmt.setBounds(new Rectangle(340, 5, 300, 30));
		
		pkDateFrom = new DatePicker();
		conDateFrom = new LabelContainer(pkDateFrom, 120, BorderLayout.WEST, true, "开始日期：");
		conDateFrom.setBounds(new Rectangle(5, 40, 300, 30));
		
		pkDateTo = new DatePicker();
		conDateTo = new LabelContainer(pkDateTo, 120, BorderLayout.WEST, true, "结束日期：");
		conDateTo.setBounds(new Rectangle(340, 40, 300, 30));
		
		txtSalary = new FormatInputField(FormatInputField.DT_INTEGER);
		txtSalary.setEditable(false);
		conSalary = new LabelContainer(txtSalary, 120, BorderLayout.WEST, true, "底工资：");
		conSalary.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtBounty = new FormatInputField(FormatInputField.DT_INTEGER);
		txtBounty.setEditable(false);
		conBounty = new LabelContainer(txtBounty, 120, BorderLayout.WEST, true, "补贴：");
		conBounty.setBounds(new Rectangle(340, 75, 300, 30));
		
		txtPercentamt = new FormatInputField(FormatInputField.DT_INTEGER);
		txtPercentamt.setEditable(false);
		conPercentamt = new LabelContainer(txtPercentamt, 120, BorderLayout.WEST, true, "提成金额：");
		conPercentamt.setBounds(new Rectangle(5, 110, 300, 30));
		
		txtPunish = new FormatInputField(FormatInputField.DT_INTEGER);
		txtPunish.setEditable(false);
		conPunish = new LabelContainer(txtPunish, 120, BorderLayout.WEST, true, "扣罚金额：");
		conPunish.setBounds(new Rectangle(340, 110, 300, 30));
		
		txtOtherAmt = new FormatInputField(FormatInputField.DT_INTEGER);
		conOtherAmt = new LabelContainer(txtOtherAmt, 120, BorderLayout.WEST, true, "其他金额：");
		conOtherAmt.setBounds(new Rectangle(5, 145, 300, 30));
		
		txtRemark = new FormatInputField();
		conRemark = new LabelContainer(txtRemark, 120, BorderLayout.WEST, true, "备注：");
		conRemark.setBounds(new Rectangle(3000, 145, 300, 30));
		
		btnBal = new JButton("结算工资");
		btnBal.setBounds(new Rectangle(340, 145, 100, 30));
		
		colunm_txtAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor colunm_amt = new DefaultCellEditor(colunm_txtAmt);
		
		colunm_txtRemark = new FormatInputField();
		DefaultCellEditor colunm_remark = new DefaultCellEditor(colunm_txtRemark);
		
		tblEntry = new BeanTable();
		LinkedList<ColumnInfo> proColList = new LinkedList<ColumnInfo>(); 
		proColList.addLast(new ColumnInfo("id", "id", 0));
		proColList.addLast(new ColumnInfo("name", "名称", 100));
		proColList.addLast(new ColumnInfo("remark", "说明", 200));
		proColList.addLast(new ColumnInfo("totalamt", "总金额", 150, BigDecimal.class));
		proColList.addLast(new ColumnInfo("amt", "产生金额", 150, BigDecimal.class));
		tblEntry.initTable(proColList);
		tblEntry.setColumnEditor("name", colunm_remark);
		tblEntry.setColumnEditor("remark", colunm_remark);
		tblEntry.setColumnEditor("totalamt", colunm_amt);
		tblEntry.setColumnEditor("amt", colunm_amt);
		tblEntry.setEditenable(false);
		tblEntry.bindClass(SalaryEntryInfo.class);
		
		tblEntry.setBounds(new Rectangle(5, 180, 620, 360));
		
		jp.add(tblEntry);
		jp.add(conPerson);
		jp.add(conAmt);
		jp.add(conDateFrom);
		jp.add(conDateTo);
		jp.add(conSalary);
		jp.add(conBounty);
		jp.add(conPercentamt);
		jp.add(conPunish);
		jp.add(conOtherAmt);
		jp.add(btnBal);
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
		
		pkDateFrom.addDataChangeListener(new DataChangeListener() {
			public void DataChangeAction(Object obj) {
				try {
					actionBizdateChange();
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(SalaryEditUI.this, err);
				}
			}
		});
		
		pkDateTo.addDataChangeListener(new DataChangeListener() {
			public void DataChangeAction(Object obj) {
				try {
					actionBizdateChange();
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(SalaryEditUI.this, err);
				}
			}
		});
		
		cmbPerson.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					actionPersonChange();
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(SalaryEditUI.this, err);
				}
			}
		});
		
		txtOtherAmt.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				try {
					otherAmtChangeAction(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(SalaryEditUI.this, err);
				}
			}
			public void focusGained(FocusEvent e) {
			}
		});
		
		btnBal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionCalBalance(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(SalaryEditUI.this, err);
				}
			}
		});
	}
	
	protected void otherAmtChangeAction(FocusEvent e) throws Exception
	{
		BigDecimal bounty = txtBounty.getBigDecimalValue();
		BigDecimal perAmt = txtPercentamt.getBigDecimalValue();
		BigDecimal punishAmt = txtPunish.getBigDecimalValue();
		BigDecimal salaryAmt = txtSalary.getBigDecimalValue();
		BigDecimal otherAmt = txtOtherAmt.getBigDecimalValue();
		
		BigDecimal amt = bounty.add(perAmt).subtract(punishAmt).add(salaryAmt).add(otherAmt);
		txtAmt.setBigDecimalValue(amt);
	}
	
	protected void actionBizdateChange() throws Exception
	{
		if(!isLoad)
			return;
		
		txtSalary.setBigDecimalValue(BigDecimal.ZERO);
		txtBounty.setBigDecimalValue(BigDecimal.ZERO);
		txtPercentamt.setBigDecimalValue(BigDecimal.ZERO);
		txtAmt.setBigDecimalValue(BigDecimal.ZERO);
		
		tblEntry.removeRows();
	}
	
	protected void actionPersonChange() throws Exception
	{
		if(!isLoad)
			return;
		
		txtSalary.setBigDecimalValue(BigDecimal.ZERO);
		txtBounty.setBigDecimalValue(BigDecimal.ZERO);
		txtPercentamt.setBigDecimalValue(BigDecimal.ZERO);
		txtAmt.setBigDecimalValue(BigDecimal.ZERO);
		
		tblEntry.removeRows();
		
		PersonInfo pInfo = (PersonInfo) cmbPerson.getSelectedItem();
		Date df = IHttpSalary.getInstance().getThisSalaryDateFrom(pInfo.getId());
		pkDateFrom.setDate(df);
	}
	
	protected void actionCalBalance(ActionEvent e) throws Exception
	{
		PersonInfo personInfo = (PersonInfo) cmbPerson.getSelectedItem();
		String df = pkDateFrom.getDateStr();
		String dt = pkDateTo.getDateStr();
		if(personInfo != null && df != null && dt != null)
		{
			Date minDf = IHttpSalary.getInstance().getThisSalaryDateFrom(personInfo.getId());
			String minDfStr = DateTimeUtil.getDateStr(minDf);
			if(minDfStr.compareTo(df) > 0)
			{
				int choice = MsgBox.showConfirm(this, df + "的工资已经结算过， 是否确认再次结算？");
				if(choice != JOptionPane.YES_OPTION)
					return;
			}
			
			SalaryInfo info = IHttpSalary.getInstance().getPersonSalary(personInfo.getId(), df, dt);
			Info oldInfo = this.getEditData();
			if(oldInfo != null && oldInfo.getId() != null)
				info.setId(oldInfo.getId());
			this.setEditData(info);
			this.loadFiles();
		}
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		SalaryInfo info = (SalaryInfo) this.editData;
		
		PersonInfo pInfo = (PersonInfo) cmbPerson.getSelectedItem();
		Date df = pkDateFrom.getDate();
		Date dt = pkDateTo.getDate();
		BigDecimal salary = txtSalary.getBigDecimalValue();
		BigDecimal bounty = txtBounty.getBigDecimalValue();
		BigDecimal percentAmt = txtPercentamt.getBigDecimalValue();
		BigDecimal punishAmt = txtPunish.getBigDecimalValue();
		BigDecimal amt = txtAmt.getBigDecimalValue();
		BigDecimal otherAmt = txtOtherAmt.getBigDecimalValue();
		String remark = txtRemark.getText();
		
		info.setPersonInfo(pInfo);
		info.setDateFrom(df);
		info.setDateTo(dt);
		info.setSalary(salary);
		info.setBounty(bounty);
		info.setPercentAmt(percentAmt);
		info.setPunishAmt(punishAmt);
		info.setOtherAmt(otherAmt);
		info.setAmt(amt);
		info.setRemark(remark);
		
		LinkedList<SalaryEntryInfo> proList = info.getEntryList();
		proList.clear();
		proList.addAll((Collection<? extends SalaryEntryInfo>) tblEntry.storeFiles());
		
	}

	protected void loadFiles() throws Exception {
		isLoad = false;
		super.loadFiles();
		SalaryInfo info = (SalaryInfo) this.editData;
		
		cmbPerson.setSelectedItem(info.getPersonInfo());
		pkDateFrom.setDate(info.getDateFrom());
		pkDateTo.setDate(info.getDateTo());
		txtSalary.setBigDecimalValue(info.getSalary());
		txtBounty.setBigDecimalValue(info.getBounty());
		txtPercentamt.setBigDecimalValue(info.getPercentAmt());
		txtPunish.setBigDecimalValue(info.getPunishAmt());
		txtAmt.setBigDecimalValue(info.getAmt());
		txtOtherAmt.setBigDecimalValue(info.getOtherAmt());
		txtRemark.setText(info.getRemark());
		
		tblEntry.loadFiles(info.getEntryList());
		isLoad = true;
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		SalaryInfo info = (SalaryInfo) this.editData;
		info.setOtherAmt(BigDecimal.ZERO);
		info.setDateTo(DateTimeUtil.getDateAfterDays(new Date(), -1));
		info.setDateFrom(DateTimeUtil.getLastDatethisDay(new Date()));
	}
	
	protected Class<?> getInfoClass() {
		return SalaryInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpSalary.getInstance();
	}

	protected String getEditUITitle() {
		return "员工工资单";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

	protected void setCompentStatus() {
		super.setCompentStatus();
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		boolean isView = OpenStateType.VIEW.equals(this.getOst());
		
		
		btnAddNew.setEnabled(!isView);
		cmbPerson.setEnabled(!flag && !isView);
		pkDateFrom.setEnabled(!flag && !isView);
		pkDateTo.setEnabled(!flag && !isView);
		txtSalary.setEnabled(false);
		txtBounty.setEnabled(false);
		txtPercentamt.setEnabled(false);
		txtAmt.setEnabled(false);
		txtPunish.setEnabled(false);
		txtRemark.setEnabled(!flag && !isView);
		btnBal.setEnabled(!flag && !isView);
		
		tblEntry.setEnable(false);
	}

	protected void actionClose(ActionEvent e) throws Exception {
		super.actionClose(e);
	}
}
