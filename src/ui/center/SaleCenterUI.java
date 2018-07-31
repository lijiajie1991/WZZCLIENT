package ui.center;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import bas.customer.CustomerEditUI;
import bas.customer.CustomerInfo;
import bas.customer.IHttpCustomer;
import bas.material.MaterialInfo;
import bas.measureunit.MeasureUnitInfo;
import bas.person.PersonInfo;
import bas.vip.VipInfo;
import bean.Info;
import bill.recharge.RechargeRecordEditUI;
import cache.BaseData;
import card.CardReader;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.ShopType;
import em.SortType;
import exp.ExpHandle;
import listener.CardNumberListener;
import listener.TabNameChangeListener;
import myswing.btn.ToolButton;
import myswing.container.LabelContainer;
import myswing.container.MyTabbedPane;
import myswing.editor.AutoCompleteInputField;
import ui.base.BaseTabUI;
import ui.base.UiFactory;

public class SaleCenterUI extends BaseTabUI {
	private static final long serialVersionUID = 1L;
	
	protected CustomerInfo cusInfo = null;
	
	//缓存数据
	LinkedList<Info> perList = null;
	LinkedList<Info> cusList = null;
	LinkedList<Info> matList = null;
	LinkedList<Info> unitList = null;
	
	protected MyTabbedPane tab = null;
	
	protected ToolButton btnAddCustomer = null;
	protected ToolButton btnEditCustomer = null;
	protected ToolButton btnCharge = null;
	protected ToolButton btnAddSale = null;
	protected ToolButton btnClose = null;
	
	protected LabelContainer conCustomer = null;
	protected AutoCompleteInputField txtCustomer = null;
	protected JButton btnSearch = null;
	
	protected JLabel labCustomerNumber = null;
	protected JLabel labCustomerName = null;
	protected JLabel labCustomerVip = null;
	protected JLabel labCustomerBirth = null;
	protected JLabel labCustomerBalance = null;
	protected JLabel labCustomerpoints = null;
	
	public SaleCenterUI()
	{
		super("前台收银（销售）");
	}


	public void initUI(HashMap<String, Object> ctx) throws Exception {
		this.loadData();
		
		this.setLayout(new BorderLayout());
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		
		JPanel jpTop = new JPanel();
		jpTop.setLayout(new BorderLayout());
		jpTop.setPreferredSize(new Dimension(700, 130));
		
		txtCustomer = new AutoCompleteInputField(){
			private static final long serialVersionUID = 1L;

			public LinkedList<Object> getList(String txt) throws Exception
			{
				if(cusList == null)
				{
					cusList = BaseData.getInfoList(CustomerInfo.class);
				}
				
				LinkedList<Object> list = new LinkedList<Object>();
				for(Info info : cusList)
				{
					CustomerInfo cusInfo = (CustomerInfo) info;
					String cusId = cusInfo.getId() != null ? cusInfo.getId() : "";
					String cusNumber = cusInfo.getNumber() != null ? cusInfo.getNumber() : "";
					String cusName = cusInfo.getName() != null ? cusInfo.getName() : "";
					String cusPhone = cusInfo.getPhone() != null ? cusInfo.getPhone() : "";
					
					if(cusId.contains(txt) || cusNumber.contains(txt) || cusName.contains(txt) || cusPhone.contains(txt))
					{
						list.addLast(cusInfo);
					}
				}
				
				return list;
			}
			
		};
		
		conCustomer = new LabelContainer(txtCustomer, 150, BorderLayout.WEST, false, "输入会员编号或姓名：");
		conCustomer.setPreferredSize(new Dimension(400, 25));
		btnSearch = new JButton("查询");
		JPanel jpSearch = new JPanel();
		jpSearch.setLayout(new FlowLayout(FlowLayout.LEADING));
		jpSearch.add(conCustomer);
		jpSearch.add(btnSearch);
		jpSearch.setPreferredSize(new Dimension(700, 35));
		jpTop.add(jpSearch, BorderLayout.NORTH);
		
		labCustomerNumber = new JLabel("会员编号：");
		labCustomerName = new JLabel("会员姓名：");
		labCustomerVip = new JLabel("会员类型：");
		labCustomerBirth = new JLabel("会员生日：");
		labCustomerBalance = new JLabel("卡内余额：");
		labCustomerpoints = new JLabel("会员积分：");
		JPanel jpInfo = new JPanel();
		jpInfo.setLayout(new GridLayout(2, 3, 5, 5));
		jpInfo.add(labCustomerNumber);
		jpInfo.add(labCustomerName);
		jpInfo.add(labCustomerVip);
		jpInfo.add(labCustomerBirth);
		jpInfo.add(labCustomerBalance);
		jpInfo.add(labCustomerpoints);
		jpInfo.setPreferredSize(new Dimension(700, 80));
		jpTop.add(jpInfo, BorderLayout.CENTER);
		
		
		tab = new MyTabbedPane(JTabbedPane.TOP);
		
		
		jp.add(jpTop, BorderLayout.NORTH);
		jp.add(tab, BorderLayout.CENTER);
		this.add(jp, BorderLayout.CENTER);
	}
	
	public void initToolBar() throws Exception {
		super.initToolBar();
		
		btnAddCustomer = new ToolButton("新增会员");
		btnList.addLast(btnAddCustomer);
		
		btnEditCustomer = new ToolButton("修改会员");
		btnList.addLast(btnEditCustomer);
		
		btnCharge = new ToolButton("会员充值");
		btnList.addLast(btnCharge);
		
		btnAddSale = new ToolButton("新增销售");
		btnList.addLast(btnAddSale);
		
		btnClose = new ToolButton("关闭收银", "");
		btnList.addLast(btnClose);
	}
	
	public void addListener() throws Exception {

		txtCustomer.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			
			public void keyReleased(KeyEvent e) {
			}
			
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					try {
						actionSearch(null);
					} catch (Exception err) {
						err.printStackTrace();
						new ExpHandle(ower, err);
					}
				}
				
			}
		});
		
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddCustomer(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnEditCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionEditCustomer(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnCharge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddRecharge(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionSearch(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		CardReader.addListener(this.getClass().getName(), new CardNumberListener() {
			public void actionPerformed(String number) throws Exception {
				actionReadCard(number);
			}
		});
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionClose(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnAddSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddSale(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
	
	}

	protected void actionAddCustomer(ActionEvent e) throws Exception
	{
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					cusInfo = (CustomerInfo) e.getSource();
					loadCustomer();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		UiFactory.openEditUI(ower, CustomerEditUI.class, qLs, null);
	}
	
	protected void actionEditCustomer(ActionEvent e) throws Exception
	{
		if(cusInfo == null || cusInfo.getId() == null)
			return;
		
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					loadCustomer();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		UiFactory.openEditUI(ower, CustomerEditUI.class, qLs, null, cusInfo);
	}
	
	protected void actionAddRecharge(ActionEvent e) throws Exception
	{
		HashMap<String, Object> uiCtx = new HashMap<String, Object>();
		uiCtx.put("customer", cusInfo);
		
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					loadCustomer();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		UiFactory.openEditUI(ower, RechargeRecordEditUI.class, qLs, uiCtx);
	}
	
	protected void actionSearch(ActionEvent e) throws Exception
	{
		String txt = txtCustomer.getText();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.number", CompareType.EQUAL, txt));
		filter.addItem(new FilterItemInfo("a.name", CompareType.EQUAL, txt));
		filter.setMkr("#0 or #1");
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		LinkedList<Info> infoList = IHttpCustomer.getInstance().getInfoList(filter, order);
		if(infoList != null && !infoList.isEmpty())
		{
			cusInfo = (CustomerInfo) infoList.get(0);
			loadCustomer();
		}
	}
	
	protected void actionReadCard(String cardNumber) throws Exception
	{
		CustomerInfo info = IHttpCustomer.getInstance().getCustomerInfo(cardNumber);
		this.cusInfo = info;
		this.loadCustomer();
		
		CustomerSaleUI jp = (CustomerSaleUI) tab.getSelectedComponent();
		if(jp == null)
			return;
		
		CustomerInfo prvCusInfo = jp.getCustomerInfo();
		if(prvCusInfo != null && info != null)
		{
			if(!prvCusInfo.getId().equals(info.getId()))
			{
				this.actionAddSale(null);
			}
		}
		else
		{
			jp.setCustomer(info);
		}
	}
	
	protected void actionAddSale(ActionEvent e) throws Exception
	{
		if(cusInfo != null && cusInfo.getId() != null)
		{
			int index = getTabByCustomer(cusInfo.getId());
			if(index != -1)
			{
				tab.setSelectedIndex(index);
				return;
			}
		}
	
		HashMap<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("perList", perList);
		ctx.put("cusList", cusList);
		ctx.put("matList", matList);
		ctx.put("unitList", unitList);
		ctx.put("customer", cusInfo);
		
		String tid = UUID.randomUUID().toString();
		
		CustomerSaleUI jp = new CustomerSaleUI(tid, ower, 
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						actionRemoveTab();
					}
				}, 
				
				new TabNameChangeListener() {
					public void actionChange(String tid, String name) {
						int index = tab.getIndexByTabId(tid);
						tab.setTitleAt(index, name);
					}
				}
				);
		jp.setUIContext(ctx);
		
		tab.add(tid, jp, jp.getName(), tab.getTabCount());
		tab.setSelectedIndex(tab.getTabCount() - 1);
	}
	
	protected void actionClose(ActionEvent e) throws Exception
	{
		CardReader.removeListener(this.getClass().getName());
		this.quitLs.actionPerformed(e);
	}
	
	protected void actionRemoveTab()
	{
		int index = tab.getSelectedIndex();
		tab.remove(index);
	}
	
	private void loadCustomer() throws Exception
	{
		if(cusInfo == null || cusInfo.getId() == null)
			return;
		
		cusInfo = (CustomerInfo) IHttpCustomer.getInstance().getInfo(cusInfo.getId());
		VipInfo vipInfo = cusInfo.getVipInfo();
		String vipName = vipInfo != null ? vipInfo.getName() : "";
		labCustomerNumber.setText("会员编号：" + cusInfo.getNumber());
		labCustomerName.setText("会员姓名：" + cusInfo.getName());
		labCustomerVip.setText("会员类型：" + vipName);
		labCustomerBirth.setText("会员生日：" + cusInfo.getBirth());
		labCustomerBalance.setText("卡内余额：" + cusInfo.getBalance());
		labCustomerpoints.setText("会员积分：" + cusInfo.getPoints());
		
	}
	
	protected void loadData() throws Exception
	{
		perList = BaseData.getInfoList(PersonInfo.class);
		cusList = BaseData.getInfoList(CustomerInfo.class);
		matList = BaseData.getInfoList(MaterialInfo.class);
		unitList = BaseData.getInfoList(MeasureUnitInfo.class);
	}

	protected Class<?> getInfoClass() {
		return SaleCenterUI.class;
	}

	public boolean isCanOpen() {
		ShopType st = ContextUtil.getShopType();
		return st == null || st == ShopType.STORE;
	}
	
	public int getTabByCustomer(String customerId)
	{
		int count = tab.getTabCount();
		for(int i = 0; i < count; i++)
		{
			CustomerSaleUI jp = (CustomerSaleUI) tab.getComponent(i);
			CustomerInfo jpCusInfo = jp.getCustomerInfo();
			if(jpCusInfo != null && jpCusInfo.getId() != null && customerId.equals(jpCusInfo.getId()))
				return i;
		}
		
		return -1;
	}

}
