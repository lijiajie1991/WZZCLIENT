package bas.customer;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import bas.vip.IHttpVip;
import bas.vip.VipInfo;
import bean.IHttp;
import bean.Info;
import bean.base.BaseEditUI;
import bill.recharge.IHttpRechargeRecord;
import bill.recharge.RechargeRecordInfo;
import card.CardReader;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.BigDecimalUtil;
import common.util.ContextUtil;
import common.util.DateTimeUtil;
import em.CompareType;
import em.SortType;
import exp.BizException;
import exp.ExpHandle;
import listener.CardNumberListener;
import myswing.btn.ToolButton;
import myswing.container.LabelContainer;
import myswing.editor.DatePicker;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.msgbox.MsgBox;

public class CustomerEditUI extends BaseEditUI{
	private static final long serialVersionUID = 1L;
	
	protected CardNumberListener ls = null;
	
	protected ToolButton btnShowPhone = null;
	
	protected LabelContainer conVip = null;
	protected MyComboBox cmbVip = null;
	
	protected LabelContainer conPhone = null;
	protected FormatInputField txtPhone = null;
	
	protected FormatInputField txtCardId = null;
	
	protected LabelContainer conPoints = null;
	protected FormatInputField txtPoints = null;
	
	protected LabelContainer conBalance = null;
	protected FormatInputField txtBalance = null;
	
	protected LabelContainer conBirth = null;
	protected DatePicker pkBirth = null;
	
	protected JCheckBox chkIsEnable = null;
	
	public CustomerEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(690, 200);
		
		conName.setBounds(new Rectangle(5, 5, 300, 30));
		conName.setBounds(new Rectangle(340, 5, 300, 30));
		
		cmbVip = new MyComboBox();
		conVip = new LabelContainer(cmbVip, 120, BorderLayout.WEST, true, "类型：");
		conVip.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtCardId = new FormatInputField();
		
		txtPhone = new FormatInputField();
		conPhone = new LabelContainer(txtPhone, 120, BorderLayout.WEST, true, "手机号码：");
		conPhone.setBounds(new Rectangle(340, 40, 300, 30));
		
		txtPoints = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtPoints.setEditable(false);
		conPoints = new LabelContainer(txtPoints, 120, BorderLayout.WEST, true, "积分：");
		conPoints.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtBalance = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtBalance.setEditable(false);
		conBalance = new LabelContainer(txtBalance, 120, BorderLayout.WEST, true, "余额：");
		conBalance.setBounds(new Rectangle(340, 75, 300, 30));
		
		pkBirth = new DatePicker();
		conBirth = new LabelContainer(pkBirth, 120, BorderLayout.WEST, true, "生日：");
		conBirth.setBounds(new Rectangle(5, 110, 300, 30));
		
		chkIsEnable = new JCheckBox("是否生效");
		chkIsEnable.setBounds(new Rectangle(340, 110, 300, 30));
		
		jp.add(conVip);
		jp.add(conPhone);
		jp.add(conPoints);
		jp.add(conBalance);
		jp.add(conBirth);
		jp.add(chkIsEnable);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		ls = new CardNumberListener() {
			public void actionPerformed(String number) throws Exception {
				txtCardId.setText(number);
			}
		};
		CardReader.addListener(this.getClass().getName(), ls);
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		filter.setMkr("#0 or #1");
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		LinkedList<Info> vipList = IHttpVip.getInstance().getInfoList(filter, order);
		cmbVip.addItems(vipList.toArray(new VipInfo[0]));
	}

	public void initToolBar() throws Exception {
		super.initToolBar();
		btnShowPhone = new ToolButton("显示手机号码", "btnShowPhone");
		btnList.addLast(btnShowPhone);
	}

	public void addListener() throws Exception {
		super.addListener();
		
		btnShowPhone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionShowPhone(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(CustomerEditUI.this, err);
				}
			}
		});
	}
	
	protected void actionShowPhone(ActionEvent e) throws Exception{
		String str = btnShowPhone.getText();
		if("显示手机号码".equals(str))
		{
			CustomerInfo info = (CustomerInfo) this.editData;
			txtPhone.setText(info.getPhone());
			btnShowPhone.setText("隐藏手机号码");
		}
		else
		{
			CustomerInfo info = (CustomerInfo) this.editData;
			info.setPhone(txtPhone.getText());
			txtPhone.setText("***********");
			btnShowPhone.setText("显示手机号码");
		}
	}
	
	protected void actionSave(ActionEvent e) throws Exception {
		storeFiles();
		verifyInput();
		
		CustomerInfo info = (CustomerInfo) this.editData;
		
		String name = txtName.getText();
		if(isCustomerExist(info.getId(), name))
		{
			int choice = MsgBox.showConfirm(this, "已存在名字为" + name + "的客户， 是否确认继续添加？");
			if(choice != JOptionPane.YES_OPTION)
				throw new BizException("请根据姓名查找相应的客户资料进行修改！");
		}
		
		if(info.getId() == null || "".equals(info.getId()))
		{
			BigDecimal amt = txtBalance.getBigDecimalValue();
			if(amt == null || BigDecimal.ZERO.compareTo(amt) == 0)
			{
				new FirstChargeUI(this, DateTimeUtil.getDateStr(new Date()), new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							finishSave(e);
						} catch (Exception err) {
							err.printStackTrace();
							new ExpHandle(CustomerEditUI.this, err);
						}
					}
				});
			}
			else
			{
				finishSave(e);
			}
		}
		else
		{
			finishSave(e);
		}
	}
	
	protected void finishSave(ActionEvent e) throws Exception
	{
		super.actionSave(e);
		Object srcObj = e.getSource();
		if(srcObj instanceof HashMap)
		{
			HashMap<String, Object>src = (HashMap<String, Object>) srcObj;
			RechargeRecordInfo rInfo = (RechargeRecordInfo) src.get("info");
			if(rInfo != null)
			{
				CustomerInfo cusInfo = (CustomerInfo) this.getEditData();
				rInfo.setCustomerInfo(cusInfo);
				IHttpRechargeRecord.getInstance().setAuditTrue(rInfo);
			}
		}
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		CustomerInfo info = (CustomerInfo) this.editData;
		
		String birth = pkBirth.getDateStr();
		String cardId = txtCardId.getText();
		String phone = txtPhone.getText();
		VipInfo vipInfo = (VipInfo) cmbVip.getSelectedItem();
		boolean isEnable = chkIsEnable.isSelected();
		
		if(cardId == null || "".equals(cardId))
		{
			int choice = MsgBox.showConfirm(this, "未检测到卡号，是否不办理会员卡？");
			if(choice != JOptionPane.YES_OPTION)
				throw new BizException("请刷卡后再保存！");
		}
		
		String str = btnShowPhone.getText();
		if("显示手机号码".equals(str))
		{
			if(phone.contains("*"))
				phone = info.getPhone();
		}
		
		info.setBirth(birth);
		info.setPhone(phone);
		info.setCardId(cardId);
		info.setVipInfo(vipInfo);
		info.setIsEnable(isEnable);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		
		CustomerInfo info = (CustomerInfo) this.editData;
		txtPhone.setText(info.getPhone());
		txtCardId.setText(info.getCardId());
		pkBirth.setDate(info.getBirth());
		cmbVip.setSelectedItem(info.getVipInfo());
		chkIsEnable.setSelected(info.getIsEnable());
		
		String str = btnShowPhone.getText();
		if("显示手机号码".equals(str))
		{
			txtPhone.setText("***********");
		}
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		CustomerInfo info = (CustomerInfo) this.editData;
		info.setIsEnable(true);
	}

	protected Class<?> getInfoClass() {
		return CustomerInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpCustomer.getInstance();
	}

	protected String getEditUITitle() {
		return "客户";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

	protected void actionClose(ActionEvent e) throws Exception {
		CardReader.removeListener(this.getClass().getName());

		if(this.quitLs != null)
		{
			e = new ActionEvent(this.getEditData(), 0, "");
			this.quitLs.actionPerformed(e);
		}
		
		this.dispose();
		
		if(this.ower != null)
		{
			this.ower.setEnabled(true);
			this.ower.setVisible(true);
		}
		
	
	}

	protected void verifyInput() throws Exception {
		super.verifyInput();
		CustomerInfo info = (CustomerInfo) this.editData;
		String cusName = info.getName();
		if(cusName == null || "".equals(cusName.trim()))
			throw new BizException("客户名称不能为空， 请检查！");
		
		String cardId = info.getCardId();
		if(cardId != null && !"".equals(cardId))
		{
			String customerId = info.getId();
			customerId = customerId != null ? customerId : " ";
			
			FilterInfo filter = new FilterInfo();
			filter.addItem(new FilterItemInfo("a.cardId", CompareType.EQUAL, cardId));
			filter.addItem(new FilterItemInfo("a.id", CompareType.NOTEQUAL, customerId));
			filter.setMkr("#0 and #1");
			SortInfo order = new SortInfo();
			order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
			LinkedList<Info> infoList = IHttpCustomer.getInstance().getInfoList(filter, order);
			if(infoList != null && !infoList.isEmpty())
			{
				throw new BizException("当前卡号已被使用， 请检查！");
			}
		}
	}
	
	protected boolean isCustomerExist(String id, String name) throws Exception
	{
		LinkedList<Info> nameList = IHttpCustomer.getInstance().getInfoList("a.fname = '" + name + "' and a.fadminshopId = '" + ContextUtil.getAdminShopId() + "' ", "");
		if(nameList != null && !nameList.isEmpty())	
		{
			id = id != null ? id : "";
			for(Info itm : nameList)
			{
				String itmId = itm.getId();
				if(!id.equals(itmId))
				{
					return true;
				}
			}
		}
		
		return false;
	}
}
