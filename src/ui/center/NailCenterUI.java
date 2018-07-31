package ui.center;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import bas.customer.CustomerEditUI;
import bas.customer.CustomerInfo;
import bas.customer.IHttpCustomer;
import bas.person.PersonInfo;
import bas.sys.shopparam.ShopParamInfo;
import bas.vip.VipInfo;
import bean.Info;
import bill.purchase.IHttpPurchase;
import bill.purchase.PurchaseEditUI;
import bill.purchase.mutil.IHttpPurchaseMutil;
import bill.purchase.mutil.PurchaseMutilEditUI;
import bill.recharge.IHttpRechargeRecord;
import bill.recharge.RechargeRecordEditUI;
import bill.sale.IHttpSale;
import bill.sale.SaleEditUI;
import bill.sale.mutil.IHttpSaleMutil;
import bill.sale.mutil.SaleMutilEditUI;
import bill.service.IHttpService;
import bill.service.ServiceEditUI;
import cache.BaseData;
import cache.ParamData;
import card.CardReader;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.PayType;
import em.RptAlign;
import em.ShopType;
import em.SortType;
import exp.ExpHandle;
import listener.CardNumberListener;
import myswing.btn.ToolButton;
import myswing.container.LabelContainer;
import myswing.editor.AutoCompleteInputField;
import myswing.table.ColumnInfo;
import myswing.table.SimpleTable;
import ui.base.BaseTabUI;
import ui.base.UiFactory;

public class NailCenterUI extends BaseTabUI {
	private static final long serialVersionUID = 1L;
	
	//查询条件设置界面
	protected NailCenterQueryUI serQueryUI = null;
	protected NailCenterQueryUI saleQueryUI = null;
	protected NailCenterQueryUI salemutilQueryUI = null;
	protected NailCenterQueryUI rechargeQueryUI = null;
	protected NailCenterQueryUI purQueryUI = null;
	protected NailCenterQueryUI purmutilQueryUI = null;
	
	
	protected CustomerInfo cusInfo = null;
	protected LinkedList<Info> cusList = null;
	
	protected JPanel jp = null;

	protected ToolButton btnAddCustomer = null;
	protected ToolButton btnEditCustomer = null;
	protected ToolButton btnCharge = null;
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
	
	protected JTabbedPane tab = null;

	protected JPanel jpService = null;
	protected SimpleTable tblService = null;
	protected SimpleTable tblServiceGroup = null;
	public JButton btnAddService = null;
	public JButton btnRefreshService = null;
	public JButton btnFilterService = null;
	
	protected JPanel jpSale = null;
	protected SimpleTable tblSale = null;
	protected SimpleTable tblSaleGroup = null;
	public JButton btnAddSale = null;
	public JButton btnRefreshSale = null;
	public JButton btnFilterSale = null;
	
	//多分录销售单
	protected JPanel jpSalemutil = null;
	protected SimpleTable tblSalemutil = null;
	protected SimpleTable tblSalemutilGroup = null;
	public JButton btnAddSalemutil = null;
	public JButton btnRefreshSalemutil = null;
	public JButton btnFilterSalemutil = null;
	
	protected JPanel jpRecharge = null;
	protected SimpleTable tblRecharge = null;
	public JButton btnAddRecharge = null;
	public JButton btnRefreshRecharge = null;
	public JButton btnFilterRecharge = null;
	
	//单分录销售单
	protected JPanel jpPurchase = null;
	protected SimpleTable tblPurchase = null;
	protected SimpleTable tblPurchaseGroup = null;
	public JButton btnAddPurchase = null;
	public JButton btnRefreshPurchase = null;
	public JButton btnFilterPurchase = null;
	
	//多分录销售单
	protected JPanel jpPurchasemutil = null;
	protected SimpleTable tblPurchasemutil = null;
	protected SimpleTable tblPurchasemutilGroup = null;
	public JButton btnAddPurchasemutil = null;
	public JButton btnRefreshPurchasemutil = null;
	public JButton btnFilterPurchasemutil = null;
	
	public NailCenterUI()
	{
		super("前台收银（综合）");
	}
	
	public void initUI(HashMap<String, Object> ctx) throws Exception {

		this.setLayout(new BorderLayout());
		
		ShopParamInfo sp = (ShopParamInfo) ParamData.getShopParamInfo();
		
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
			
			public boolean isPerfectMatch(String txt, Object obj) throws Exception{
				if(obj ==  null)
					return false;
				
				CustomerInfo cusInfo = (CustomerInfo) obj;
				if(txt.equals(cusInfo.getName()) || txt.equals(cusInfo.getPhone()) || txt.equals(cusInfo.getNumber()) || txt.equals(cusInfo.getCardId()))
					return true;
				
				return false;
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
		
		tab = new JTabbedPane(JTabbedPane.TOP);
		
		LinkedList<ColumnInfo> serviceList = new LinkedList<ColumnInfo>();
		serviceList.addLast(new ColumnInfo("id", "id", 0));
		serviceList.addLast(new ColumnInfo("number", "编码", 100));
		serviceList.addLast(new ColumnInfo("bizdate", "服务日期", 100, Date.class));
		serviceList.addLast(new ColumnInfo("person_name", "负责人", 100));
		serviceList.addLast(new ColumnInfo("customer_name", "客户", 100));
		serviceList.addLast(new ColumnInfo("project_name", "项目", 100));
		serviceList.addLast(new ColumnInfo("price", "单价", 100));
		serviceList.addLast(new ColumnInfo("qty", "次数", 100));
		serviceList.addLast(new ColumnInfo("amt", "金额", 100));
		serviceList.addLast(new ColumnInfo("factamt", "收取金额", 100));
		serviceList.addLast(new ColumnInfo("pt", "付款方式", 100, PayType.class));
		serviceList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
		
		jpService = new JPanel(new BorderLayout());
		btnAddService = new JButton("新增服务单");
		btnRefreshService = new JButton("刷新服务单");
		btnFilterService = new JButton("查询服务单");
		tblService = new SimpleTable(false);
		tblService.initTable(serviceList);
		tblService.setEditenable(false);
		
		
		LinkedList<ColumnInfo> serviceGroupList = new LinkedList<ColumnInfo>();
		serviceGroupList.addLast(new ColumnInfo("project_name", "项目", 100));
		serviceGroupList.addLast(new ColumnInfo("qty", "单数量", 80));
		serviceGroupList.addLast(new ColumnInfo("amt", "金额", 80));
		serviceGroupList.addLast(new ColumnInfo("factamt", "收取金额", 80));
		serviceGroupList.addLast(new ColumnInfo("isaudittrue", "是否确认", 80, Boolean.class, RptAlign.RIGHT));
		tblServiceGroup = new SimpleTable(true);
		tblServiceGroup.initTable(serviceGroupList);
		tblServiceGroup.setEditenable(false);

		JToolBar jpServiceToolBar = new JToolBar(); 
		jpServiceToolBar.add(btnAddService);
		jpServiceToolBar.add(btnRefreshService);
		jpServiceToolBar.add(btnFilterService);
		jpServiceToolBar.setFloatable(false);
		jpService.add(jpServiceToolBar, BorderLayout.PAGE_START);
		
		jpService.add(tblService, BorderLayout.CENTER);
		jpService.add(tblServiceGroup, BorderLayout.EAST);
		tab.add(jpService, "项目记录");
		
		
		
		jpSale = new JPanel(new BorderLayout());
		btnAddSale = new JButton("新增销售");
		btnRefreshSale = new JButton("刷新销售单");
		btnFilterSale = new JButton("查询销售单");
		
		LinkedList<ColumnInfo> saleList = new LinkedList<ColumnInfo>();
		saleList.addLast(new ColumnInfo("id", "id", 0));
		saleList.addLast(new ColumnInfo("number", "编码", 100));
		saleList.addLast(new ColumnInfo("bizdate", "销售日期", 100, Date.class));
		saleList.addLast(new ColumnInfo("customer_name", "客户", 100));
		saleList.addLast(new ColumnInfo("material_name", "产品", 100));
		saleList.addLast(new ColumnInfo("price", "单价", 100));
		saleList.addLast(new ColumnInfo("qty", "数量", 100));
		saleList.addLast(new ColumnInfo("amt", "金额", 100));
		saleList.addLast(new ColumnInfo("factamt", "收取金额", 100));
		saleList.addLast(new ColumnInfo("pt", "付款方式", 100, PayType.class));
		saleList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
		tblSale = new SimpleTable(false);
		tblSale.initTable(saleList);
		tblSale.setEditenable(false);
		
		LinkedList<ColumnInfo> saleGroupList = new LinkedList<ColumnInfo>();
		saleGroupList.addLast(new ColumnInfo("material_name", "产品", 100));
		saleGroupList.addLast(new ColumnInfo("qty", "数量", 80));
		saleGroupList.addLast(new ColumnInfo("amt", "金额", 80));
		saleGroupList.addLast(new ColumnInfo("factamt", "收取金额", 80));
		saleGroupList.addLast(new ColumnInfo("isaudittrue", "是否确认", 80, Boolean.class, RptAlign.RIGHT));
		tblSaleGroup = new SimpleTable(false);
		tblSaleGroup.initTable(saleGroupList);
		tblSaleGroup.setEditenable(false);
		
		JToolBar jpSaleToolBar = new JToolBar(); 
		jpSaleToolBar.add(btnAddSale);
		jpSaleToolBar.add(btnRefreshSale);
		jpSaleToolBar.add(btnFilterSale);
		jpSaleToolBar.setFloatable(false);
		jpSale.add(jpSaleToolBar, BorderLayout.PAGE_START);
		jpSale.add(tblSale, BorderLayout.CENTER);
		jpSale.add(tblSaleGroup, BorderLayout.EAST);
		if(!sp.getIsSaleEntry())
			tab.add(jpSale, "销售记录");
		
		//多分录销售单
		jpSalemutil = new JPanel(new BorderLayout());
		btnAddSalemutil = new JButton("新增销售");
		btnRefreshSalemutil = new JButton("刷新销售单");
		btnFilterSalemutil = new JButton("查询销售单");
		
		LinkedList<ColumnInfo> salemutilList = new LinkedList<ColumnInfo>();
		salemutilList.addLast(new ColumnInfo("id", "id", 0));
		salemutilList.addLast(new ColumnInfo("number", "编码", 100));
		salemutilList.addLast(new ColumnInfo("bizdate", "销售日期", 100, Date.class));
		salemutilList.addLast(new ColumnInfo("customer_name", "客户", 100));
		salemutilList.addLast(new ColumnInfo("material_name", "产品", 100));
		salemutilList.addLast(new ColumnInfo("price", "单价", 100));
		salemutilList.addLast(new ColumnInfo("qty", "数量", 100));
		salemutilList.addLast(new ColumnInfo("amt", "金额", 100));
		salemutilList.addLast(new ColumnInfo("factamt", "收取金额", 100));
		salemutilList.addLast(new ColumnInfo("pt", "付款方式", 100, PayType.class));
		salemutilList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
		tblSalemutil = new SimpleTable(false);
		tblSalemutil.initTable(saleList);
		tblSalemutil.setEditenable(false);
		
		LinkedList<ColumnInfo> salemutilGroupList = new LinkedList<ColumnInfo>();
		salemutilGroupList.addLast(new ColumnInfo("material_name", "产品", 100));
		salemutilGroupList.addLast(new ColumnInfo("qty", "数量", 80));
		salemutilGroupList.addLast(new ColumnInfo("amt", "金额", 80));
		salemutilGroupList.addLast(new ColumnInfo("factamt", "收取金额", 80));
		salemutilGroupList.addLast(new ColumnInfo("isaudittrue", "是否确认", 80, Boolean.class, RptAlign.RIGHT));
		tblSalemutilGroup = new SimpleTable(false);
		tblSalemutilGroup.initTable(salemutilGroupList);
		tblSalemutilGroup.setEditenable(false);
		
		JToolBar jpSalemutilToolBar = new JToolBar(); 
		jpSalemutilToolBar.add(btnAddSalemutil);
		jpSalemutilToolBar.add(btnRefreshSalemutil);
		jpSalemutilToolBar.add(btnFilterSalemutil);
		jpSalemutilToolBar.setFloatable(false);
		jpSalemutil.add(jpSalemutilToolBar, BorderLayout.PAGE_START);
		jpSalemutil.add(tblSalemutil, BorderLayout.CENTER);
		jpSalemutil.add(tblSalemutilGroup, BorderLayout.EAST);
		if(sp.getIsSaleEntry())
			tab.add(jpSalemutil, "销售记录");
		
		//充值纪录
		jpRecharge = new JPanel(new BorderLayout());
		btnAddRecharge = new JButton("新增充值记录");
		btnRefreshRecharge = new JButton("刷新充值记录");
		btnFilterRecharge = new JButton("查询充值记录");
		
		LinkedList<ColumnInfo> recList = new LinkedList<ColumnInfo>();
		recList.addLast(new ColumnInfo("id", "id", 0));
		recList.addLast(new ColumnInfo("number", "编码", 100));
		recList.addLast(new ColumnInfo("bizdate", "充值日期", 100, Date.class));
		recList.addLast(new ColumnInfo("customer_name", "客户", 100));
		recList.addLast(new ColumnInfo("amt", "充值金额", 100));
		recList.addLast(new ColumnInfo("giveamt", "赠送金额", 100));
		recList.addLast(new ColumnInfo("factamt", "实际金额", 100));
		recList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
		tblRecharge = new SimpleTable(true);
		tblRecharge.initTable(recList);
		tblRecharge.setEditenable(false);
		
		JToolBar jpRechargeToolBar = new JToolBar(); 
		jpRechargeToolBar.add(btnAddRecharge);
		jpRechargeToolBar.add(btnRefreshRecharge);
		jpRechargeToolBar.add(btnFilterRecharge);
		jpRechargeToolBar.setFloatable(false);
		jpRecharge.add(jpRechargeToolBar, BorderLayout.PAGE_START);
		jpRecharge.add(tblRecharge, BorderLayout.CENTER);
		tab.add(jpRecharge, "充值记录");
		
		
		
		
		
		
		
		
		jpPurchase = new JPanel(new BorderLayout());
		btnAddPurchase = new JButton("新增采购");
		btnRefreshPurchase = new JButton("刷新采购单");
		btnFilterPurchase = new JButton("查询采购单");
		
		LinkedList<ColumnInfo> PurchaseList = new LinkedList<ColumnInfo>();
		PurchaseList.addLast(new ColumnInfo("id", "id", 0));
		PurchaseList.addLast(new ColumnInfo("number", "编码", 100));
		PurchaseList.addLast(new ColumnInfo("bizdate", "采购日期", 100, Date.class));
		PurchaseList.addLast(new ColumnInfo("supplier_name", "供应商", 100));
		PurchaseList.addLast(new ColumnInfo("material_name", "产品", 100));
		PurchaseList.addLast(new ColumnInfo("price", "单价", 100));
		PurchaseList.addLast(new ColumnInfo("qty", "数量", 100));
		PurchaseList.addLast(new ColumnInfo("amt", "金额", 100));
		PurchaseList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
		tblPurchase = new SimpleTable(false);
		tblPurchase.initTable(PurchaseList);
		tblPurchase.setEditenable(false);
		
		LinkedList<ColumnInfo> PurchaseGroupList = new LinkedList<ColumnInfo>();
		PurchaseGroupList.addLast(new ColumnInfo("material_name", "产品", 100));
		PurchaseGroupList.addLast(new ColumnInfo("qty", "数量", 80));
		PurchaseGroupList.addLast(new ColumnInfo("amt", "金额", 80));
		PurchaseGroupList.addLast(new ColumnInfo("isaudittrue", "是否确认", 80, Boolean.class, RptAlign.RIGHT));
		tblPurchaseGroup = new SimpleTable(false);
		tblPurchaseGroup.initTable(PurchaseGroupList);
		tblPurchaseGroup.setEditenable(false);
		
		JToolBar jpPurchaseToolBar = new JToolBar(); 
		jpPurchaseToolBar.add(btnAddPurchase);
		jpPurchaseToolBar.add(btnRefreshPurchase);
		jpPurchaseToolBar.add(btnFilterPurchase);
		jpPurchaseToolBar.setFloatable(false);
		jpPurchase.add(jpPurchaseToolBar, BorderLayout.PAGE_START);
		jpPurchase.add(tblPurchase, BorderLayout.CENTER);
		jpPurchase.add(tblPurchaseGroup, BorderLayout.EAST);
		if(!sp.getIsPurchaseEntry())
			tab.add(jpPurchase, "采购记录");
		
		//多分录销售单
		jpPurchasemutil = new JPanel(new BorderLayout());
		btnAddPurchasemutil = new JButton("新增采购");
		btnRefreshPurchasemutil = new JButton("刷新采购单");
		btnFilterPurchasemutil = new JButton("查询采购单");
		
		LinkedList<ColumnInfo> PurchasemutilList = new LinkedList<ColumnInfo>();
		PurchasemutilList.addLast(new ColumnInfo("id", "id", 0));
		PurchasemutilList.addLast(new ColumnInfo("number", "编码", 100));
		PurchasemutilList.addLast(new ColumnInfo("bizdate", "采购bi日期", 100, Date.class));
		PurchasemutilList.addLast(new ColumnInfo("supplier_name", "供应商", 100));
		PurchasemutilList.addLast(new ColumnInfo("material_name", "产品", 100));
		PurchasemutilList.addLast(new ColumnInfo("price", "单价", 100));
		PurchasemutilList.addLast(new ColumnInfo("qty", "数量", 100));
		PurchasemutilList.addLast(new ColumnInfo("amt", "金额", 100));
		PurchasemutilList.addLast(new ColumnInfo("factamt", "收取金额", 100));
		PurchasemutilList.addLast(new ColumnInfo("pt", "付款方式", 100, PayType.class));
		PurchasemutilList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
		tblPurchasemutil = new SimpleTable(false);
		tblPurchasemutil.initTable(PurchaseList);
		tblPurchasemutil.setEditenable(false);
		
		LinkedList<ColumnInfo> PurchasemutilGroupList = new LinkedList<ColumnInfo>();
		PurchasemutilGroupList.addLast(new ColumnInfo("material_name", "产品", 100));
		PurchasemutilGroupList.addLast(new ColumnInfo("qty", "数量", 80));
		PurchasemutilGroupList.addLast(new ColumnInfo("amt", "金额", 80));
		PurchasemutilGroupList.addLast(new ColumnInfo("factamt", "收取金额", 80));
		PurchasemutilGroupList.addLast(new ColumnInfo("isaudittrue", "是否确认", 80, Boolean.class, RptAlign.RIGHT));
		tblPurchasemutilGroup = new SimpleTable(false);
		tblPurchasemutilGroup.initTable(PurchasemutilGroupList);
		tblPurchasemutilGroup.setEditenable(false);
		
		JToolBar jpPurchasemutilToolBar = new JToolBar(); 
		jpPurchasemutilToolBar.add(btnAddPurchasemutil);
		jpPurchasemutilToolBar.add(btnRefreshPurchasemutil);
		jpPurchasemutilToolBar.add(btnFilterPurchasemutil);
		jpPurchasemutilToolBar.setFloatable(false);
		jpPurchasemutil.add(jpPurchasemutilToolBar, BorderLayout.PAGE_START);
		jpPurchasemutil.add(tblPurchasemutil, BorderLayout.CENTER);
		jpPurchasemutil.add(tblPurchasemutilGroup, BorderLayout.EAST);
		if(sp.getIsPurchaseEntry())
			tab.add(jpPurchasemutil, "采购记录");
		
		
		
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
		
		btnAddService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddService(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnRefreshService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRefreshService(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnFilterService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(serQueryUI == null)
				{
					serQueryUI = new NailCenterQueryUI(ower, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								actionRefreshService(e);
							} catch (Exception err) {
								err.printStackTrace();
								new ExpHandle(ower, err);
							}
						}
					}, 0);
				}
				serQueryUI.setCustomerInfo(cusInfo);
				serQueryUI.showUI();
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
		
		btnRefreshSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRefreshSale(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnFilterSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(saleQueryUI == null)
				{
					saleQueryUI = new NailCenterQueryUI(ower, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								actionRefreshSale(e);
							} catch (Exception err) {
								err.printStackTrace();
								new ExpHandle(ower, err);
							}
						}
					}, 0);
				}
				saleQueryUI.setCustomerInfo(cusInfo);
				saleQueryUI.showUI();
			}
		});
		
		btnAddSalemutil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddSalemutil(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnRefreshSalemutil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRefreshSalemutil(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnFilterSalemutil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(salemutilQueryUI == null)
				{
					salemutilQueryUI = new NailCenterQueryUI(ower, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								actionRefreshSalemutil(e);
							} catch (Exception err) {
								err.printStackTrace();
								new ExpHandle(ower, err);
							}
						}
					}, 0);
				}
				salemutilQueryUI.setCustomerInfo(cusInfo);
				salemutilQueryUI.showUI();
			}
		});
		
		btnAddRecharge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddRecharge(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnRefreshRecharge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRefreshRecharge(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnFilterRecharge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rechargeQueryUI == null)
				{
					rechargeQueryUI = new NailCenterQueryUI(ower, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								actionRefreshRecharge(e);
							} catch (Exception err) {
								err.printStackTrace();
								new ExpHandle(ower, err);
							}
						}
					}, 0);
				}
				rechargeQueryUI.setCustomerInfo(cusInfo);
				rechargeQueryUI.showUI();
			}
		});
		
		
		
		
		
		
		btnAddPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddPurchase(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnRefreshPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRefreshPurchase(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnFilterPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(purQueryUI == null)
				{
					purQueryUI = new NailCenterQueryUI(ower, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								actionRefreshPurchase(e);
							} catch (Exception err) {
								err.printStackTrace();
								new ExpHandle(ower, err);
							}
						}
					}, 1);
				}
				purQueryUI.setCustomerInfo(cusInfo);
				purQueryUI.showUI();
			}
		});
		
		btnAddPurchasemutil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddPurchasemutil(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnRefreshPurchasemutil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRefreshPurchasemutil(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnFilterPurchasemutil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(purmutilQueryUI == null)
				{
					purmutilQueryUI = new NailCenterQueryUI(ower, new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								actionRefreshPurchasemutil(e);
							} catch (Exception err) {
								err.printStackTrace();
								new ExpHandle(ower, err);
							}
						}
					}, 1);
				}
				purmutilQueryUI.setCustomerInfo(cusInfo);
				purmutilQueryUI.showUI();
			}
		});
		
		
		
		
		
		
		
		tblService.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				try {
					actionClickedTblService(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		tblSale.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				try {
					actionClickedTblSale(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		tblSalemutil.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				try {
					actionClickedTblSalemutil(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		
		tblRecharge.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				try {
					actionClickedTblRecharge(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		
		tblPurchase.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				try {
					actionClickedTblPurchase(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		tblPurchasemutil.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				try {
					actionClickedTblPurchasemutil(e);
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
					actionRefreshRecharge(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		UiFactory.openEditUI(ower, RechargeRecordEditUI.class, qLs, uiCtx);
	}
	
	protected void actionAddService(ActionEvent e) throws Exception
	{
		this.ower.setEnabled(false);
		HashMap<String, Object> uiCtx = new HashMap<String, Object>();
		uiCtx.put("customer", cusInfo);
		
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					loadCustomer();
					actionRefreshService(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		UiFactory.openEditUI(ower, ServiceEditUI.class, qLs, uiCtx);
	}
	
	protected void actionRefreshService(ActionEvent e) throws Exception
	{
		HashMap<String, Object> paramMap = serQueryUI != null ? serQueryUI.getParamMap() : NailCenterQueryUI.getDefParam();
		
		Map<String, List<Map<String, Object>>> data = IHttpService.getInstance().getDailyDataList(paramMap);
		List<Map<String, Object>> list = data.get("list");
		List<Map<String, Object>> groupList = data.get("groupList");
		
		tblService.removeRows();
		if(list != null)
		{		
			for(Map map : list)
			{
				tblService.insertRowMap(tblService.getRowCount(), map);
			}
		}
		
		tblServiceGroup.removeRows();
		if(groupList != null)
		{		
			for(Map map : groupList)
			{
				tblServiceGroup.insertRowMap(tblServiceGroup.getRowCount(), map);
			}
		}
	}
	
	protected void actionAddSale(ActionEvent e) throws Exception
	{
		this.ower.setEnabled(false);
		HashMap<String, Object> uiCtx = new HashMap<String, Object>();
		uiCtx.put("customer", cusInfo);
		
		
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					loadCustomer();
					actionRefreshSale(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		UiFactory.openEditUI(ower, SaleEditUI.class, qLs, uiCtx);
	}
	
	protected void actionRefreshSale(ActionEvent e) throws Exception
	{
		HashMap<String, Object> paramMap = saleQueryUI != null ? saleQueryUI.getParamMap() : NailCenterQueryUI.getDefParam();
		
		Map<String, List<Map<String, Object>>> data = IHttpSale.getInstance().getDailyDataList(paramMap);
		List<Map<String, Object>> list = data.get("list");
		List<Map<String, Object>> groupList = data.get("groupList");
		
		tblSale.removeRows();
		if(list != null)
		{		
			for(Map map : list)
			{
				tblSale.insertRowMap(tblSale.getRowCount(), map);
			}
		}
		
		tblSaleGroup.removeRows();
		if(groupList != null)
		{		
			for(Map map : groupList)
			{
				tblSaleGroup.insertRowMap(tblSaleGroup.getRowCount(), map);
			}
		}
	}
	
	protected void actionAddSalemutil(ActionEvent e) throws Exception
	{
		this.ower.setEnabled(false);
		HashMap<String, Object> uiCtx = new HashMap<String, Object>();
		uiCtx.put("customer", cusInfo);
		
		
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					loadCustomer();
					actionRefreshSalemutil(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		UiFactory.openEditUI(ower, SaleMutilEditUI.class, qLs, uiCtx);
	}
	
	protected void actionRefreshSalemutil(ActionEvent e) throws Exception
	{
		HashMap<String, Object> paramMap = salemutilQueryUI != null ? salemutilQueryUI.getParamMap() : NailCenterQueryUI.getDefParam();
		
		Map<String, List<Map<String, Object>>> data = IHttpSaleMutil.getInstance().getDailyDataList(paramMap);
		List<Map<String, Object>> list = data.get("list");
		List<Map<String, Object>> groupList = data.get("groupList");
		
		tblSalemutil.removeRows();
		if(list != null)
		{		
			for(Map map : list)
			{
				tblSalemutil.insertRowMap(tblSalemutil.getRowCount(), map);
			}
		}
		
		tblSalemutilGroup.removeRows();
		if(groupList != null)
		{		
			for(Map map : groupList)
			{
				tblSalemutilGroup.insertRowMap(tblSalemutilGroup.getRowCount(), map);
			}
		}
	}
	
	protected void actionRefreshRecharge(ActionEvent e) throws Exception
	{
		HashMap<String, Object> paramMap = rechargeQueryUI != null ? rechargeQueryUI.getParamMap() : NailCenterQueryUI.getDefParam();
		
		tblRecharge.removeRows();
		
		PersonInfo pInfo = (PersonInfo) paramMap.get("person");
		CustomerInfo cInfo = (CustomerInfo) paramMap.get("customer");
		
		StringBuffer filterSql = new StringBuffer();
		filterSql.append("DATE_FORMAT(a.fbizdate, '%Y-%m-%d') >= '" + paramMap.get("df") + "'\n");
		filterSql.append("and DATE_FORMAT(a.fbizdate, '%Y-%m-%d') <= '" + paramMap.get("dt") + "'\n");
		filterSql.append("and a.fshopId = '" + ContextUtil.getShopId() + "'\n");
		filterSql.append("and ('all' = '" + pInfo.getId() + "' or person.fid = '" + pInfo.getId() + "')\n");
		filterSql.append("and ('all' = '" + cInfo.getId() + "' or customer.fid = '" + cInfo.getId() + "')\n");
		
		List<Map<String, Object>> list = IHttpRechargeRecord.getInstance().getMapListData(filterSql.toString(), "a.fbizdate");
		if(list == null)
			return;
		
		for(Map map : list)
		{
			tblRecharge.insertRowMap(tblRecharge.getRowCount(), map);
		}
	}
	
	
	

	protected void actionAddPurchase(ActionEvent e) throws Exception
	{
		this.ower.setEnabled(false);
		HashMap<String, Object> uiCtx = new HashMap<String, Object>();
		uiCtx.put("customer", cusInfo);
		
		
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					loadCustomer();
					actionRefreshPurchase(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		UiFactory.openEditUI(ower, PurchaseEditUI.class, qLs, uiCtx);
	}
	
	protected void actionRefreshPurchase(ActionEvent e) throws Exception
	{
		HashMap<String, Object> paramMap = purQueryUI != null ? purQueryUI.getParamMap() : NailCenterQueryUI.getDefParam();
		
		Map<String, List<Map<String, Object>>> data = IHttpPurchase.getInstance().getDailyDataList(paramMap);
		List<Map<String, Object>> list = data.get("list");
		List<Map<String, Object>> groupList = data.get("groupList");
		
		tblPurchase.removeRows();
		if(list != null)
		{		
			for(Map map : list)
			{
				tblPurchase.insertRowMap(tblPurchase.getRowCount(), map);
			}
		}
		
		tblPurchaseGroup.removeRows();
		if(groupList != null)
		{		
			for(Map map : groupList)
			{
				tblPurchaseGroup.insertRowMap(tblPurchaseGroup.getRowCount(), map);
			}
		}
	}
	
	protected void actionAddPurchasemutil(ActionEvent e) throws Exception
	{
		this.ower.setEnabled(false);
		HashMap<String, Object> uiCtx = new HashMap<String, Object>();
		uiCtx.put("customer", cusInfo);
		
		
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					loadCustomer();
					actionRefreshPurchasemutil(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		UiFactory.openEditUI(ower, PurchaseMutilEditUI.class, qLs, uiCtx);
	}
	
	protected void actionRefreshPurchasemutil(ActionEvent e) throws Exception
	{
		HashMap<String, Object> paramMap = purmutilQueryUI != null ? purmutilQueryUI.getParamMap() : NailCenterQueryUI.getDefParam();
		
		Map<String, List<Map<String, Object>>> data = IHttpPurchaseMutil.getInstance().getDailyDataList(paramMap);
		List<Map<String, Object>> list = data.get("list");
		List<Map<String, Object>> groupList = data.get("groupList");
		
		tblPurchasemutil.removeRows();
		if(list != null)
		{		
			for(Map map : list)
			{
				tblPurchasemutil.insertRowMap(tblPurchasemutil.getRowCount(), map);
			}
		}
		
		tblPurchasemutilGroup.removeRows();
		if(groupList != null)
		{		
			for(Map map : groupList)
			{
				tblPurchasemutilGroup.insertRowMap(tblPurchasemutilGroup.getRowCount(), map);
			}
		}
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
	
	protected void actionClickedTblService(MouseEvent e) throws Exception
	{
		if(e.getClickCount() != 2)
			return;
		
		int rowIndex = tblService.getSelectedRow();
		String id = (String) tblService.getValueAt(rowIndex, "id");
		if(id == null || "".equals(id))
			return;
		
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					loadCustomer();
					actionRefreshService(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		Info info = IHttpService.getInstance().getInfo(id);
		UiFactory.openEditUI(ower, ServiceEditUI.class, qLs, null, info);
	}
	
	protected void actionClickedTblSale(MouseEvent e) throws Exception
	{
		if(e.getClickCount() != 2)
			return;
		
		int rowIndex = tblSale.getSelectedRow();
		String id = (String) tblSale.getValueAt(rowIndex, "id");
		if(id == null || "".equals(id))
			return;
		
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					loadCustomer();
					actionRefreshSale(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		Info info = IHttpSale.getInstance().getInfo(id);
		UiFactory.openEditUI(ower, SaleEditUI.class, qLs, null, info);
	}
	
	protected void actionClickedTblSalemutil(MouseEvent e) throws Exception
	{
		if(e.getClickCount() != 2)
			return;
		
		int rowIndex = tblSalemutil.getSelectedRow();
		String id = (String) tblSalemutil.getValueAt(rowIndex, "id");
		if(id == null || "".equals(id))
			return;
		
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					loadCustomer();
					actionRefreshSalemutil(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		Info info = IHttpSaleMutil.getInstance().getInfo(id);
		UiFactory.openEditUI(ower, SaleMutilEditUI.class, qLs, null, info);
	}
	
	protected void actionClickedTblRecharge(MouseEvent e) throws Exception
	{
		if(e.getClickCount() != 2)
			return;
		
		int rowIndex = tblRecharge.getSelectedRow();
		String id = (String) tblRecharge.getValueAt(rowIndex, "id");
		if(id == null || "".equals(id))
			return;
		
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					loadCustomer();
					actionRefreshRecharge(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		Info info = IHttpRechargeRecord.getInstance().getInfo(id);
		UiFactory.openEditUI(ower, RechargeRecordEditUI.class, qLs, null, info);
	}
	
	
	protected void actionClickedTblPurchase(MouseEvent e) throws Exception
	{
		if(e.getClickCount() != 2)
			return;
		
		int rowIndex = tblPurchase.getSelectedRow();
		String id = (String) tblPurchase.getValueAt(rowIndex, "id");
		if(id == null || "".equals(id))
			return;
		
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					loadCustomer();
					actionRefreshPurchase(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		Info info = IHttpPurchase.getInstance().getInfo(id);
		UiFactory.openEditUI(ower, PurchaseEditUI.class, qLs, null, info);
	}
	
	protected void actionClickedTblPurchasemutil(MouseEvent e) throws Exception
	{
		if(e.getClickCount() != 2)
			return;
		
		int rowIndex = tblPurchasemutil.getSelectedRow();
		String id = (String) tblPurchasemutil.getValueAt(rowIndex, "id");
		if(id == null || "".equals(id))
			return;
		
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					loadCustomer();
					actionRefreshPurchasemutil(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		Info info = IHttpPurchaseMutil.getInstance().getInfo(id);
		UiFactory.openEditUI(ower, PurchaseMutilEditUI.class, qLs, null, info);
	}
	
	protected void actionReadCard(String cardNumber) throws Exception
	{
		CustomerInfo info = IHttpCustomer.getInstance().getCustomerInfo(cardNumber);
		this.cusInfo = info;
		this.loadCustomer();
	}
	
	protected void actionClose(ActionEvent e) throws Exception
	{
		CardReader.removeListener(this.getClass().getName());
		this.quitLs.actionPerformed(e);
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
	
	protected Class<?> getInfoClass() {
		return NailCenterUI.class;
	}

	public boolean isCanOpen() {
		ShopType st = ContextUtil.getShopType();
		return st == null || st == ShopType.STORE;
	}
	
	
}
