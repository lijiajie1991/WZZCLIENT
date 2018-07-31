package bill.service;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PageFormat;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JCheckBox;

import bas.customer.CustomerInfo;
import bas.customer.IHttpCustomer;
import bas.person.PersonInfo;
import bas.project.ProjectInfo;
import bas.sys.shop.IHttpShop;
import bas.sys.shop.ShopInfo;
import bean.IHttp;
import bean.Info;
import bean.bill.BillEditUI;
import bean.bill.BillInfo;
import bill.paylog.PayLogInfo;
import bill.price.IHttpPrice;
import bill.price.ProjectPriceEntryInfo;
import cache.BaseData;
import card.CardReader;
import common.util.DateTimeUtil;
import em.PayType;
import exp.BizException;
import exp.ExpHandle;
import listener.CardNumberListener;
import listener.DataChangeListener;
import listener.PayListener;
import myswing.btn.ToolButton;
import myswing.container.LabelContainer;
import myswing.editor.AutoFilterComboBox;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.msgbox.MsgBox;
import ui.base.PayTypeSelectUI;

public class ServiceEditUI extends BillEditUI{
	private static final long serialVersionUID = 1L;
	
	protected HashMap<String, CustomerInfo> cusMap = null;
	
	protected ToolButton btnTrunc = null;
	
	protected LabelContainer conPerson = null;
	protected MyComboBox cmbPerson = null;
	
	protected LabelContainer conCustomer = null;
	protected AutoFilterComboBox<CustomerInfo> cmbCustomer = null;
	
	protected LabelContainer conProject = null;
	protected MyComboBox cmbProject = null;
	
	protected MyComboBox cmbPayType = null;
	
	protected LabelContainer conQty = null;
	protected FormatInputField txtQty = null;
	
	protected LabelContainer conPrice = null;
	protected FormatInputField txtPrice = null;
	
	protected LabelContainer conAmt = null;
	protected FormatInputField txtAmt = null;
	
	protected LabelContainer conFactAmt = null;
	protected FormatInputField txtFactAmt = null;
	
	protected LabelContainer conRemark = null;
	protected FormatInputField txtRemark = null;
	
	protected JCheckBox chkIsBalance = null;
	protected JCheckBox chkIsOt = null;
	protected JCheckBox chkIsSpec = null;
	
	public ServiceEditUI()
	{
		super();
		
		cusMap = new HashMap<String, CustomerInfo>();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 290);
		
		txtNumber.setEditable(true);
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(340, 5, 300, 30));
		
		cmbPerson = new MyComboBox();
		conPerson = new LabelContainer(cmbPerson, 120, BorderLayout.WEST, true, "服务员：");
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
		
		cmbProject = new MyComboBox();
		conProject = new LabelContainer(cmbProject, 120, BorderLayout.WEST, true, "项目：");
		conProject.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtQty = new FormatInputField(FormatInputField.DT_DOUBLE);
		conQty = new LabelContainer(txtQty, 120, BorderLayout.WEST, true, "次数：");
		conQty.setBounds(new Rectangle(340, 75, 300, 30));
		
		txtPrice = new FormatInputField(FormatInputField.DT_DOUBLE);
		conPrice = new LabelContainer(txtPrice, 120, BorderLayout.WEST, true, "单价：");
		conPrice.setBounds(new Rectangle(5, 110, 300, 30));
		
		txtAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtAmt.setEditable(false);
		conAmt = new LabelContainer(txtAmt, 120, BorderLayout.WEST, true, "金额：");
		conAmt.setBounds(new Rectangle(340, 110, 300, 30));
		
		txtFactAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtFactAmt.setEditable(false);
		conFactAmt = new LabelContainer(txtFactAmt, 120, BorderLayout.WEST, true, "收取金额：");
		conFactAmt.setBounds(new Rectangle(5, 145, 300, 30));
		
		txtRemark = new FormatInputField();
		conRemark = new LabelContainer(txtRemark, 120, BorderLayout.WEST, true, "备注：");
		conRemark.setBounds(new Rectangle(340, 145, 300, 30));
		
		chkIsOt = new JCheckBox("是否为加班单");
		chkIsOt.setBounds(new Rectangle(5, 180, 300, 30));
		
		chkIsSpec = new JCheckBox("是否为点牌单");
		chkIsSpec.setBounds(new Rectangle(340, 180, 300, 30));
		
		cmbPayType = new MyComboBox();
		chkIsBalance = new JCheckBox("是否已结算");
		
		jp.add(conPerson);
		jp.add(conCustomer);
		jp.add(conProject);
		jp.add(conQty);
		jp.add(conPrice);
		jp.add(conAmt);
		jp.add(conFactAmt);
		jp.add(conRemark);
		jp.add(chkIsOt);
		jp.add(chkIsSpec);
	}

	public void setUIContext(HashMap<String, Object> ctx) throws Exception
	{
		super.setUIContext(ctx);
		if(ctx != null && ctx.containsKey("customer") && ctx.get("customer") != null)
		{
			CustomerInfo cusInfo = (CustomerInfo) ctx.get("customer");
			ServiceInfo info = (ServiceInfo) this.editData;
			info.setCustomerInfo(cusInfo);
			this.loadFiles();
		}
	}
	
	public void initToolBar() throws Exception
	{
		super.initToolBar();
		
		btnTrunc = new ToolButton("取整(角)", "btntrunc");
		btnList.add(4, btnTrunc);
	}
	
	protected void onLoad() throws Exception {
		super.onLoad();
		
		LinkedList<Info> cusList = BaseData.getInfoList(CustomerInfo.class);
		LinkedList<Info> proList = BaseData.getInfoList(ProjectInfo.class);
		LinkedList<Info> perList = BaseData.getInfoList(PersonInfo.class);
		
		cmbPerson.addItems(perList.toArray(new PersonInfo[0]));
		cmbCustomer.addItems(cusList.toArray(new CustomerInfo[0]));
		cmbProject.addItems(proList.toArray(new ProjectInfo[0]));
		cmbPayType.addItems(PayType.values());
		
		for(Info info : cusList)
			cusMap.put(info.getId(), (CustomerInfo) info);
	}

	public void addListener() throws Exception {
		super.addListener();
		
		btnTrunc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = btnTrunc.getText();
				BigDecimal amt = txtAmt.getBigDecimalValue();
				if(txt.contains("(角)"))
				{
					amt = amt != null ? amt : BigDecimal.ZERO;
					amt = amt.setScale(1, BigDecimal.ROUND_DOWN);
					
					btnTrunc.setText("取整(元)");
				}
				else if(txt.contains("(元)"))
				{
					amt = amt != null ? amt : BigDecimal.ZERO;
					amt = amt.setScale(0, BigDecimal.ROUND_DOWN);
					
					btnTrunc.setText("取整(十元)");
				}
				else if(txt.contains("(十元)"))
				{
					amt = amt != null ? amt : BigDecimal.ZERO;
					amt = amt.divide(new BigDecimal(10), 0, BigDecimal.ROUND_DOWN);
					amt = amt.multiply(new BigDecimal(10));
					
					btnTrunc.setText("取整(百元)");
				}
				else if(txt.contains("(百元)"))
				{
					amt = amt != null ? amt : BigDecimal.ZERO;
					amt = amt.divide(new BigDecimal(100), 0, BigDecimal.ROUND_DOWN);
					amt = amt.multiply(new BigDecimal(100));
					
					btnTrunc.setText("取整(角)");
				}
				txtFactAmt.setBigDecimalValue(amt);
			}
		});
		
		pkBizdate.addDataChangeListener(new DataChangeListener() {
			public void DataChangeAction(Object obj) {
				try {
					bizdateChange();
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ServiceEditUI.this, err);
				}
			}
		});
		
		cmbProject.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					projectChangeAction(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ServiceEditUI.this, err);
				}
			}
		});
		
		cmbCustomer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					customerChangeAction(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ServiceEditUI.this, err);
				}
			}
		});
		
		txtPrice.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				try {
					priceChangeAction(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ServiceEditUI.this, err);
				}
			}
			public void focusGained(FocusEvent e) {
			}
		});
		
		txtQty.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				try {
					qtyChangeAction(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ServiceEditUI.this, err);
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
		ServiceInfo info = (ServiceInfo) this.editData;
		
		PersonInfo pInfo = (PersonInfo) cmbPerson.getSelectedItem();
		CustomerInfo cusInfo = (CustomerInfo) cmbCustomer.getSelectedItem();
		ProjectInfo proInfo = (ProjectInfo) cmbProject.getSelectedItem();
		PayType pt = (PayType) cmbPayType.getSelectedItem();
		BigDecimal qty = txtQty.getBigDecimalValue();
		BigDecimal price = txtPrice.getBigDecimalValue();
		BigDecimal amt = txtAmt.getBigDecimalValue();
		BigDecimal factamt = txtFactAmt.getBigDecimalValue();
		String remark = txtRemark.getText();
		boolean isBalance = chkIsBalance.isSelected();
		boolean isOt = chkIsOt.isSelected();
		boolean isSpec = chkIsSpec.isSelected();
		
		info.setPersonInfo(pInfo);
		info.setCustomerInfo(cusInfo);
		info.setProjectInfo(proInfo);
		info.setPt(pt);
		info.setQty(qty);
		info.setPrice(price);
		info.setAmt(amt);
		info.setFactamt(factamt);
		info.setRemark(remark);
		info.setIsBalance(isBalance);
		info.setIsOt(isOt);
		info.setIsSpec(isSpec);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		ServiceInfo info = (ServiceInfo) this.editData;
		
		cmbPerson.setSelectedItem(info.getPersonInfo());
		cmbCustomer.setSelectedItem(info.getCustomerInfo());
		cmbProject.setSelectedItem(info.getProjectInfo());
		cmbPayType.setSelectedItem(info.getPt());
		txtQty.setBigDecimalValue(info.getQty());
		txtPrice.setBigDecimalValue(info.getPrice());
		txtAmt.setBigDecimalValue(info.getAmt());
		txtFactAmt.setBigDecimalValue(info.getFactamt());
		txtRemark.setText(info.getRemark());
		chkIsBalance.setSelected(info.getIsBalance());
		chkIsOt.setSelected(info.getIsOt());
		chkIsSpec.setSelected(info.getIsSpec());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		ServiceInfo info = (ServiceInfo) this.editData;
		info.setQty(BigDecimal.ONE);
		info.setIsBalance(false);
		info.setIsOt(false);
		info.setIsSpec(false);
	}

	protected void bizdateChange() throws Exception
	{
		setPrice();
		priceChangeAction(null);
	}
	
	protected void projectChangeAction(ItemEvent e) throws Exception
	{
		setPrice();
		priceChangeAction(null);
	}
	
	protected void customerChangeAction(ItemEvent e) throws Exception
	{
		if(e.getStateChange() != ItemEvent.SELECTED)
			return;
		
		setPrice();
		priceChangeAction(null);
	}
	
	protected void qtyChangeAction(FocusEvent e) throws Exception
	{
		BigDecimal qty = txtQty.getBigDecimalValue();
		BigDecimal price = txtPrice.getBigDecimalValue();
		BigDecimal amt = qty.multiply(price);
		txtAmt.setBigDecimalValue(amt);
		txtFactAmt.setBigDecimalValue(amt);
	}
	
	protected void priceChangeAction(FocusEvent e) throws Exception
	{
		BigDecimal qty = txtQty.getBigDecimalValue();
		BigDecimal price = txtPrice.getBigDecimalValue();
		BigDecimal amt = qty.multiply(price);
		txtAmt.setBigDecimalValue(amt);
		txtFactAmt.setBigDecimalValue(amt);
	}
	
	private void setPrice() throws Exception
	{
		ProjectInfo proInfo = (ProjectInfo) cmbProject.getSelectedItem();
		String proId = proInfo != null ? proInfo.getId() : null;
		
		txtPrice.setBigDecimalValue(BigDecimal.ZERO);
		txtPrice.setEditable(true);
		
		String dateStr = pkBizdate.getDateStr();
		if(proId != null && !"".equals(proId) && !"".equals(dateStr))
		{
			CustomerInfo cusInfo = (CustomerInfo) cmbCustomer.getSelectedItem();
			String cusId = cusInfo != null ? cusInfo.getId() : null;
			
			ProjectPriceEntryInfo entryInfo = null;
			if(cusId != null && !"".equals(cusId))
				entryInfo = IHttpPrice.getInstance().getProjectPrice(cusId, proId, dateStr);
			else
				entryInfo = IHttpPrice.getInstance().getProjectPrice(proId, dateStr);
				
			if(entryInfo != null)
			{
				BigDecimal price = entryInfo.getPrice();
				price = price != null ? price : BigDecimal.ONE;
				
				txtPrice.setBigDecimalValue(price);
				txtPrice.setEditable(entryInfo.getIsFloat());
			}
			else
			{
				BigDecimal price = proInfo != null ? proInfo.getPrice() : null;
				price = price != null ? price : BigDecimal.ONE;
				
				txtPrice.setBigDecimalValue(price);
				txtPrice.setEditable(true);
			}
		}
	}
	
	protected Class<?> getInfoClass() {
		return ServiceInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpService.getInstance();
	}

	protected String getEditUITitle() {
		return "服务单";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}
	
	protected void actionAuditTrue(ActionEvent e) throws Exception {
		storeFiles();
		verifyInput();
		verifyAuditTrue();
		
		ServiceInfo info = (ServiceInfo) this.getEditData();
		
		PayType pt = PayType.Cash;
		CustomerInfo cusInfo = (CustomerInfo) cmbCustomer.getSelectedItem();
		if(cusInfo != null && cusInfo.getId() != null && !"".equals(cusInfo.getId()))
			pt = PayType.Vip;
		
		BigDecimal amt = txtFactAmt.getBigDecimalValue();
		PayLogInfo pl = PayTypeSelectUI.createLog(amt);
		
		ProjectInfo proInfo = info.getProjectInfo();
		String proName = proInfo != null ? proInfo.getName() : "";
		pl.setSubject("支付：" + proName);
		
		new PayTypeSelectUI(this, pt, null, pl, new PayListener() {
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
			new ExpHandle(ServiceEditUI.this, e);
		}
	}

	protected void verifyAuditTrue() throws Exception {
		super.verifyAuditTrue();
		ServiceInfo info = (ServiceInfo) this.editData;
		
		ProjectInfo pjInfo = info.getProjectInfo();
		if(pjInfo == null || pjInfo.getId() == null || "".equals(pjInfo.getId())){
			throw new BizException("请选择项目！");
		}
		
		PersonInfo perInfo = info.getPersonInfo();
		if(perInfo == null || perInfo.getId() == null || "".equals(perInfo.getId())){
			throw new BizException("请选择负责人！");
		}
		
		if(info.getPt() == PayType.Vip)
		{
			CustomerInfo cusInfo = info.getCustomerInfo();
			if(cusInfo == null || cusInfo.getId() == null)
				throw new BizException(PayType.Vip.getName() + "时， 客户不能为空！");
			
			cusInfo = (CustomerInfo) IHttpCustomer.getInstance().getInfo(cusInfo.getId());
			BigDecimal balance = cusInfo.getBalance();
			if(balance.compareTo(info.getFactamt()) < 0)
				throw new BizException("客户余额不足！");
		}
	}

	protected void setCompentStatus() {
		super.setCompentStatus();
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		
		btnTrunc.setEnabled(!flag);
		cmbPerson.setEnabled(!flag);
		cmbCustomer.setEnabled(!flag);
		cmbProject.setEnabled(!flag);
		cmbPayType.setEnabled(!flag);
		txtQty.setEnabled(!flag);
		txtPrice.setEnabled(!flag);
		txtAmt.setEnabled(!flag);
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
	
	protected boolean isNeedPrint()
	{
		return true;
	}
	
	
	//-----------------------------------------以下为打印方法， 每个方法对应一个模版-----------------------------------------
	public void actionPrint_service(Graphics g, PageFormat pf, int page) throws Exception
	{
		ServiceInfo info = (ServiceInfo) this.getEditData();
		
		String shopId = info.getShopId();
		ShopInfo shopInfo = (ShopInfo) IHttpShop.getInstance().getInfo(shopId);
		String shopName = shopInfo.getName();
		
		
		Graphics2D g2d = (Graphics2D) g;  
        g2d.setFont(new Font("Default", Font.PLAIN, 10));  
        g2d.drawString(shopName, 50, 15);
        g2d.drawString("单号:" + info.getNumber(), 5, 30);
        g2d.drawString("日期:" + DateTimeUtil.getDateStr(info.getBizdate()), 5, 45); 
        g2d.drawString("负责人:" + info.getPersonInfo().getName(), 5, 60);  
        g2d.drawString("项目:" + info.getProjectInfo().getName(), 5, 75);
        g2d.drawString("次数:" + info.getQty(), 5, 90);
        g2d.drawString("金额:" + info.getFactamt(), 5, 105);
        g2d.drawString("付款方式:" + info.getPt().getName(), 5, 120);
        
        g2d.setFont(new Font("Default", Font.PLAIN, 8));  
        g2d.drawString("打印时间:" + DateTimeUtil.getDateStr(new Date(), DateTimeUtil.secondFormat), 5, 135);
        
        g2d.setFont(new Font("Default", Font.PLAIN, 10));  
        g2d.drawString("----------------------------------------- ", 5, 145);
        g2d.drawString("----------------------------------------- ", 5, 185);
	}
}
