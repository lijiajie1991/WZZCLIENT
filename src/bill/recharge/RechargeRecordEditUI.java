package bill.recharge;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

import bas.customer.CustomerInfo;
import bas.customer.IHttpCustomer;
import bas.person.PersonInfo;
import bean.IHttp;
import bean.Info;
import bean.bill.BillEditUI;
import bean.bill.BillInfo;
import bill.paylog.PayLogInfo;
import bill.rechargegive.IHttpRechargeGive;
import cache.BaseData;
import card.CardReader;
import em.PayType;
import exp.ExpHandle;
import listener.CardNumberListener;
import listener.DataChangeListener;
import listener.PayListener;
import myswing.container.LabelContainer;
import myswing.editor.AutoFilterComboBox;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.msgbox.MsgBox;
import ui.base.PayTypeSelectUI;

public class RechargeRecordEditUI extends BillEditUI{
	private static final long serialVersionUID = 1L;
	
	protected MyComboBox cmbPayType = null;
	
	protected LabelContainer conPerson = null;
	protected MyComboBox cmbPerson = null;
	
	protected LabelContainer conCustomer = null;
	protected AutoFilterComboBox<CustomerInfo> cmbCustomer = null;
	
	protected LabelContainer conAmt = null;
	protected FormatInputField txtAmt = null;
	
	protected LabelContainer conGiveAmt = null;
	protected FormatInputField txtGiveAmt = null;
	
	protected LabelContainer conFactAmt = null;
	protected FormatInputField txtFactAmt = null;
	
	protected LabelContainer conRemark = null;
	protected FormatInputField txtRemark = null;
	
	public RechargeRecordEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 210);
		
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(340, 5, 300, 30));
		conBizdate.setLabelText("充值日期");
		
		cmbPerson = new MyComboBox();
		conPerson = new LabelContainer(cmbPerson, 120, BorderLayout.WEST, true, "负责人：");
		conPerson.setBounds(new Rectangle(5, 40, 300, 30));
		
		cmbCustomer = new AutoFilterComboBox<CustomerInfo>(){
			private static final long serialVersionUID = 1L;
			
			//重载方法， 改写判断适配方式
			protected boolean isMatch(String txt, Object obj) {
				if(obj == null)
					return false;
				if(txt == null || "".equals(txt))
					return true;
				
				if(obj instanceof CustomerInfo)
				{
					CustomerInfo info = (CustomerInfo) obj;
					String val = null;
					
					val = info.getNumber() != null ? info.getNumber() : "";
					if(val.contains(txt))
						return true;
					
					val = info.getName() != null ? info.getName() : "";
					if(val.contains(txt))
						return true;
					
					val = info.getPhone() != null ? info.getPhone() : "";
					if(val.contains(txt))
						return true;
				}
				return false;
			}
		};		
		conCustomer = new LabelContainer(cmbCustomer, 120, BorderLayout.WEST, true, "客户：");
		conCustomer.setBounds(new Rectangle(340, 40, 300, 30));
		
		txtAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		conAmt = new LabelContainer(txtAmt, 120, BorderLayout.WEST, true, "充值金额：");
		conAmt.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtGiveAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtGiveAmt.setEditable(false);
		conGiveAmt = new LabelContainer(txtGiveAmt, 120, BorderLayout.WEST, true, "赠送金额：");
		conGiveAmt.setBounds(new Rectangle(340, 75, 300, 30));
		
		txtFactAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtFactAmt.setEditable(false);
		conFactAmt = new LabelContainer(txtFactAmt, 120, BorderLayout.WEST, true, "实际存入：");
		conFactAmt.setBounds(new Rectangle(5, 110, 300, 30));
		
		txtRemark = new FormatInputField();
		conRemark = new LabelContainer(txtRemark, 120, BorderLayout.WEST, true, "备注：");
		conRemark.setBounds(new Rectangle(340, 110, 300, 30));
		
		cmbPayType = new MyComboBox();
		
		jp.add(conPerson);
		jp.add(conCustomer);
		jp.add(conAmt);
		jp.add(conGiveAmt);
		jp.add(conFactAmt);
		jp.add(conRemark);
	}

	public void initToolBar() throws Exception
	{
		super.initToolBar();
	}
	
	protected void onLoad() throws Exception {
		super.onLoad();
		LinkedList<Info> perList = BaseData.getInfoList(PersonInfo.class);
		LinkedList<Info> cusList = BaseData.getInfoList(CustomerInfo.class);
		
		cmbPerson.addItems(perList.toArray(new PersonInfo[0]));
		cmbCustomer.addItems(cusList.toArray(new CustomerInfo[0]));
		cmbPayType.addItems(PayType.values());
	}

	public void setUIContext(HashMap<String, Object> ctx) throws Exception
	{
		super.setUIContext(ctx);
		if(ctx != null && ctx.containsKey("customer") && ctx.get("customer") != null)
		{
			CustomerInfo cusInfo = (CustomerInfo) ctx.get("customer");
			RechargeRecordInfo info = (RechargeRecordInfo) this.editData;
			info.setCustomerInfo(cusInfo);
			this.loadFiles();
		}
	}
	
	public void addListener() throws Exception {
		super.addListener();
		
		pkBizdate.addDataChangeListener(new DataChangeListener() {
			public void DataChangeAction(Object obj) {
				try {
					bizdateChangeAction();
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(RechargeRecordEditUI.this, err);
				}
			}
		});
		
		
		txtAmt.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				try {
					amtChangeAction();
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(RechargeRecordEditUI.this, err);
				}
			}
			public void focusGained(FocusEvent e) {
			}
		});
	
		CardReader.addListener(this.getClass().getName(), new CardNumberListener() {
			public void actionPerformed(String number) throws Exception {
				actionReadCard(number);
			}
		});
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		RechargeRecordInfo info = (RechargeRecordInfo) this.editData;
		
		PersonInfo pInfo = (PersonInfo) cmbPerson.getSelectedItem();
		CustomerInfo cusInfo = (CustomerInfo) cmbCustomer.getSelectedItem();
		BigDecimal amt = txtAmt.getBigDecimalValue();
		BigDecimal giveAmt = txtGiveAmt.getBigDecimalValue();
		BigDecimal factAmt = txtFactAmt.getBigDecimalValue();
		String remark = txtRemark.getText();
		PayType pt = (PayType) cmbPayType.getSelectedItem();
		
		info.setPersonInfo(pInfo);
		info.setCustomerInfo(cusInfo);
		info.setAmt(amt);
		info.setGiveAmt(giveAmt);
		info.setFactAmt(factAmt);
		info.setRemark(remark);
		info.setPt(pt);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		RechargeRecordInfo info = (RechargeRecordInfo) this.editData;
		
		cmbPerson.setSelectedItem(info.getPersonInfo());
		cmbCustomer.setSelectedItem(info.getCustomerInfo());
		txtAmt.setBigDecimalValue(info.getAmt());
		txtGiveAmt.setBigDecimalValue(info.getGiveAmt());
		txtFactAmt.setBigDecimalValue(info.getFactAmt());
		txtRemark.setText(info.getRemark());
		cmbPayType.setSelectedItem(info.getPt());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
	}

	protected void amtChangeAction() throws Exception
	{
		setFactAmt();
	}
	
	protected void bizdateChangeAction() throws Exception
	{
		setFactAmt();
	}
	
	private void setFactAmt() throws Exception
	{
		String dateStr = pkBizdate.getDateStr();
		BigDecimal amt = txtAmt.getBigDecimalValue();
		amt = amt != null ? amt : BigDecimal.ZERO;
		
		BigDecimal giveAmt = IHttpRechargeGive.getInstance().getGiveAmt(amt, dateStr);
		giveAmt = giveAmt != null ? giveAmt : BigDecimal.ZERO;
		BigDecimal factAmt = giveAmt.add(amt);
		
		txtGiveAmt.setBigDecimalValue(giveAmt);
		txtFactAmt.setBigDecimalValue(factAmt);
	}
	
	protected Class<?> getInfoClass() {
		return RechargeRecordInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpRechargeRecord.getInstance();
	}

	protected String getEditUITitle() {
		return "充值记录";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}
	
	protected void setCompentStatus() {
		super.setCompentStatus();
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		
		cmbPerson.setEnabled(!flag);
		cmbCustomer.setEnabled(!flag);
		txtAmt.setEnabled(!flag);
		txtGiveAmt.setEnabled(!flag);
		txtFactAmt.setEnabled(!flag);
		txtRemark.setEnabled(!flag);
	}

	protected void actionClose(ActionEvent e) throws Exception {
		CardReader.removeListener(this.getClass().getName());
		super.actionClose(e);
	}

	protected void actionReadCard(String cardNumber) throws Exception
	{
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		if(!flag)
		{
			CustomerInfo cusInfo = IHttpCustomer.getInstance().getCustomerInfo(cardNumber);
			cmbCustomer.setSelectedItem(cusInfo);
		}
	}
	
	protected void actionAuditTrue(ActionEvent e) throws Exception {
		storeFiles();
		verifyInput();
		verifyAuditTrue();

		BigDecimal amt = txtFactAmt.getBigDecimalValue();
		PayLogInfo pl = PayTypeSelectUI.createLog(amt);
		
		new PayTypeSelectUI(this, PayType.Cash, new PayType[]{PayType.Vip}, pl, new PayListener() {
			public void success(String logId, PayType pt){
				actionPay(pt);
			}
			public void fail(String logId, PayType pt){
			}
			public boolean isShowSuccess() {
				return false;
			}
			public String getSuccessMsg() {
				return null;
			}
			public boolean isShowFail() {
				return true;
			}
			public String getFailMsg(String msg) {
				return "支付失败！失败原因：" + msg;
			}

		});
	}
	
	protected void actionPay(PayType pt) 
	{
		try {
			cmbPayType.setSelectedItem(pt);
			super.actionAuditTrue(null);
		} catch (Exception e) {
			e.printStackTrace();
			new ExpHandle(RechargeRecordEditUI.this, e);
		}
	}

	
	
}
