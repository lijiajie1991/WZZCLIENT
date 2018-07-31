package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

import barcode.BarcodeScanner;
import bas.shopbalance.ShopBalanceListUI;
import bas.sys.menu.MenuInfo;
import bas.sys.menu.MenuListUI;
import bas.sys.param.ParamListUI;
import bas.sys.permission.PermissionListUI;
import bas.sys.print.PrintListUI;
import bas.sys.shop.ShopInfo;
import bas.sys.shop.ShopListUI;
import bas.sys.shopparam.ShopParamInfo;
import bas.sys.shopparam.ShopParamListUI;
import bas.sys.user.IHttpUser;
import bill.register.RegisterListUI;
import bill.shoppayment.ShopPayMentListUI;
import bill.smsrecord.SmsRecordListUI;
import cache.CacheData;
import cache.ParamData;
import card.CardReader;
import common.util.ContextUtil;
import common.util.ImageLoader;
import common.util.PermissionUtil;
import em.MenuType;
import em.ShopType;
import exp.BizException;
import exp.ExpHandle;
import myswing.msgbox.MsgBox;
import report.base.ui.RptUI;
import report.crossrpt.ui.CrossRptListUI;
import report.listrpt.ui.ListRptListUI;
import ui.base.BaseTabUI;
import ui.center.NailCenterUI;
import ui.menu.MenuItem;
import ui.menu.MenuNode;
import ui.menu.MenuUI;

public class WZZUI extends JFrame implements MouseListener, ActionListener{
	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tab = null;
	private MenuUI menu = null;
	
	private LinkedList<String> classList = null;
	private HashMap<String, Object> ctx = null;

	public WZZUI() throws Exception {
		classList = new LinkedList<String>();
		ctx = new HashMap<String, Object>();
		
		menu = new MenuUI(this);
		menu.initUI(ctx);
		menu.addListener();
		
		tab = new JTabbedPane(JTabbedPane.TOP);
		
		tab.add(menu, menu.getName());

		this.setLayout(new BorderLayout());
		this.add(tab, BorderLayout.CENTER);
		this.setVisible(true);
		this.setMinimumSize(new Dimension(1024, 768));
		this.setTitle(ContextUtil.getShopInfo().getName());
		
		this.setIconImage(ImageLoader.getIcon("my_24.png").getImage());
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		MenuBar();
		addDefTab();
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		
		
		
	}
	
	public void MenuBar() throws Exception {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("文件");
		// 文件菜单
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem fileExitMenuItem = new JMenuItem("退出", KeyEvent.VK_X);
		fileMenu.add(fileExitMenuItem);
		fileExitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});

		JMenuItem filePWItem = new JMenuItem("修改账号和密码");
		fileMenu.add(filePWItem);
		filePWItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					new UserPwChangeUI(WZZUI.this);
				}catch(Exception err)
				{
					err.printStackTrace();
					new ExpHandle(WZZUI.this, err);
				}
			}
		});
		menuBar.add(fileMenu);
		
		if(PermissionUtil.isSuperUser())
		{
			JMenu sysMenu = new JMenu("系统维护");
			menuBar.add(sysMenu);
			
			JMenuItem adminMenuItem = new JMenuItem("菜单维护");
			sysMenu.add(adminMenuItem);
			adminMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuInfo menuInfo = new MenuInfo();
					menuInfo.setClsName(MenuListUI.class.getName());
					MenuNode node = new MenuNode(menuInfo);
					actionAddTab(node);
				}
			});
			
			JMenuItem adminPermissionItem = new JMenuItem("权限项维护");
			sysMenu.add(adminPermissionItem);
			adminPermissionItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuInfo menuInfo = new MenuInfo();
					menuInfo.setClsName(PermissionListUI.class.getName());
					MenuNode node = new MenuNode(menuInfo);
					actionAddTab(node);
				}
			});
			
			JMenuItem adminSms = new JMenuItem("短信记录");
			sysMenu.add(adminSms);
			adminSms.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuInfo menuInfo = new MenuInfo();
					menuInfo.setClsName(SmsRecordListUI.class.getName());
					MenuNode node = new MenuNode(menuInfo);
					actionAddTab(node);
				}
			});
			
			JMenuItem adminParam = new JMenuItem("参数设置");
			sysMenu.add(adminParam);
			adminParam.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuInfo menuInfo = new MenuInfo();
					menuInfo.setClsName(ParamListUI.class.getName());
					MenuNode node = new MenuNode(menuInfo);
					actionAddTab(node);
				}
			});
			
			JMenuItem printParam = new JMenuItem("打印设置");
			sysMenu.add(printParam);
			printParam.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuInfo menuInfo = new MenuInfo();
					menuInfo.setClsName(PrintListUI.class.getName());
					MenuNode node = new MenuNode(menuInfo);
					actionAddTab(node);
				}
			});
			
			
			JMenu shopMenu = new JMenu("店铺维护");
			menuBar.add(shopMenu);
			
			JMenuItem register = new JMenuItem("注册记录");
			shopMenu.add(register);
			register.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuInfo menuInfo = new MenuInfo();
					menuInfo.setClsName(RegisterListUI.class.getName());
					MenuNode node = new MenuNode(menuInfo);
					actionAddTab(node);
				}
			});
			
			JMenuItem adminShopItem = new JMenuItem("店铺资料");
			shopMenu.add(adminShopItem);
			adminShopItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuInfo menuInfo = new MenuInfo();
					menuInfo.setClsName(ShopListUI.class.getName());
					MenuNode node = new MenuNode(menuInfo);
					actionAddTab(node);
				}
			});
			
			JMenuItem adminShopBalance = new JMenuItem("店铺余额");
			shopMenu.add(adminShopBalance);
			adminShopBalance.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuInfo menuInfo = new MenuInfo();
					menuInfo.setClsName(ShopBalanceListUI.class.getName());
					MenuNode node = new MenuNode(menuInfo);
					actionAddTab(node);
				}
			});
			
			JMenuItem adminShopPayMent = new JMenuItem("店铺充值");
			shopMenu.add(adminShopPayMent);
			adminShopPayMent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuInfo menuInfo = new MenuInfo();
					menuInfo.setClsName(ShopPayMentListUI.class.getName());
					MenuNode node = new MenuNode(menuInfo);
					actionAddTab(node);
				}
			});
			
			JMenuItem shopParamSet = new JMenuItem("店铺参数设置");
			shopMenu.add(shopParamSet);
			shopParamSet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuInfo menuInfo = new MenuInfo();
					menuInfo.setClsName(ShopParamListUI.class.getName());
					MenuNode node = new MenuNode(menuInfo);
					actionAddTab(node);
				}
			});
			
			JMenu reportMenu = new JMenu("报表维护");
			menuBar.add(reportMenu);
			
			JMenuItem listRpt = new JMenuItem("列表报表");
			reportMenu.add(listRpt);
			listRpt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuInfo menuInfo = new MenuInfo();
					menuInfo.setClsName(ListRptListUI.class.getName());
					MenuNode node = new MenuNode(menuInfo);
					actionAddTab(node);
				}
			});
			
			JMenuItem crossRpt = new JMenuItem("交叉报表");
			reportMenu.add(crossRpt);
			crossRpt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuInfo menuInfo = new MenuInfo();
					menuInfo.setClsName(CrossRptListUI.class.getName());
					MenuNode node = new MenuNode(menuInfo);
					actionAddTab(node);
				}
			});
		}
		
		
		
		JMenu shopMenu = new JMenu("切换店铺");
		LinkedList<ShopInfo> shopList = IHttpUser.getInstance().getUserShopList(ContextUtil.getUserInfo().getId());
		for(ShopInfo shop : shopList)
		{
			MenuItem shopMenuItem = new MenuItem(shop.getName(), shop);
			shopMenu.add(shopMenuItem);
			shopMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MenuItem item = (MenuItem) e.getSource();
					try {
						actionChangeShop((ShopInfo) item.getInfo());
					} catch (Exception err) {
						err.printStackTrace();
						new ExpHandle(WZZUI.this, err);
					}
				}
			});
		}
		
		menuBar.add(shopMenu);
		
		setJMenuBar(menuBar);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() != 2 && !e.isMetaDown())
			return;
		try{
			JTree tree = (JTree) e.getSource();
			Object c = tree.getLastSelectedPathComponent();
			if (c instanceof MenuNode) {
				actionAddTab((MenuNode) c);
			}
		}catch(Exception err)
		{
			new ExpHandle(WZZUI.this, err);
		}
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void actionPerformed(ActionEvent e) {
		int index = tab.getSelectedIndex();
		if(index != 0)
		{
			classList.remove(index - 1);
			tab.remove(index);
		}
		else
		{
			if (MsgBox.showConfirm(this, "是否退出系统？") == 0) {
				System.exit(0);
			}
		}
	}
	
	private BaseTabUI getPanel(String className) throws Exception
	{
		if(className != null && !"".equals(className))
		{
			BaseTabUI ui = (BaseTabUI) Class.forName(className).newInstance();
			if(!ui.isCanOpen())
			{
				throw new BizException("当前店铺不能操作此菜单， 请切换到实体店铺再操作！");
			}
			
			return ui;
		}
		
		throw new BizException("当前菜单没配置相应的界面");
	}

	private void actionChangeShop(ShopInfo info) throws Exception
	{
		if(JOptionPane.YES_OPTION == MsgBox.showConfirm(this, "将关闭所有窗口， 是否继续?"))
		{
			this.setTitle(info.getName());
			ContextUtil.setShopInfo(info);
			CacheData.loadCache(null);
			
			BarcodeScanner.startScanner();
			
			ShopParamInfo spInfo = ParamData.getShopParamInfo();
			if(spInfo != null)
			{
				if(spInfo.getIsCustomerCard())
					(new CardReader()).start();
			}
			
			int index = tab.getTabCount() - 1;
			while(index > 0)
			{
				classList.remove(index - 1);
				tab.remove(index);
				
				index--;
			}
		}
	}
	
	private void actionAddTab(MenuNode node)
	{
		try{
			MenuInfo mInfo = node.getMenuInfo();
			String clsName = mInfo.getClsName();
			String rptId = mInfo.getRptId();
			rptId = rptId != null ? rptId : "";
			ctx.put("rptId", rptId);
			
			if(clsName != null && !"".equals(clsName + rptId))
			{
				int index = 0;
				if(classList.contains(clsName + rptId))
				{
					index = classList.indexOf(clsName + rptId) + 1;
				}
				else
				{
					BaseTabUI jp = getPanel(clsName);
					if(jp != null)
					{
						try{
							index = tab.getComponentCount();
							BaseTabUI list = (BaseTabUI)jp;
							list.initUI(ctx);
							list.initToolBar();
							list.createToolBar();
							list.setQuitListener(this);
							list.addListener();
							list.setOwer(this);
							
							tab.add(jp, jp.getName(), index);
							classList.addLast(clsName + rptId);
							
							if(mInfo.getMt() == MenuType.RPT)
							{
								RptUI rpt = (RptUI) list;
								rpt.initCondition();
							}
						}catch(Exception err)
						{
							err.printStackTrace();
						}
					}
				}
				
				tab.setSelectedIndex(index);
			}
		}catch(Exception err)
		{
			new ExpHandle(WZZUI.this, err);
		}
	}
	
	protected void addDefTab()
	{
		ShopType st = ContextUtil.getShopType();
		if(st != null && st == ShopType.STORE)
		{
			MenuInfo menuInfo = new MenuInfo();
			menuInfo.setClsName(NailCenterUI.class.getName());
			MenuNode node = new MenuNode(menuInfo);
			actionAddTab(node);
		}
	}
	
	private void exit()
	{
		System.exit(0);
	}

}
