package ui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JToolBar;

import barcode.BarcodeScanner;
import bas.shopbalance.IHttpShopBalance;
import bas.sys.shop.IHttpShop;
import bas.sys.shop.ShopInfo;
import bas.sys.shopparam.ShopParamInfo;
import bas.sys.user.IHttpUser;
import bas.sys.user.UserInfo;
import bean.Info;
import bill.register.RegisterEditUI;
import cache.CacheData;
import cache.LoginData;
import cache.ParamData;
import card.CardReader;
import common.util.ContextUtil;
import common.util.ImageLoader;
import common.util.PermissionUtil;
import em.ShopStatusType;
import exp.ExpHandle;
import myswing.container.LabelContainer;
import myswing.editor.MyComboBox;
import myswing.msgbox.MsgBox;
import ui.base.UiFactory;

/**
  * @说明 登录界面
  * @作者 黎嘉杰 
  * @日期 2016年9月11日 下午11:09:15 
  */
public class LoginUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//账号
	protected LabelContainer conNumber = null;
	protected MyComboBox cmbNumber = null;
	
	//密码
	private LabelContainer conPassword = null;
	private JPasswordField txtPassword = null;

	//登录按钮
	private JButton btnLogin = null;
	private JButton btnRegister = null;
	
	public LoginUI() {
		
		initUI();
		initToolBar();
		addListener();
	}
	
	protected void initUI() 
	{
		JPanel jp = new JPanel();
		jp.setLayout(null);
		
		//账号
		cmbNumber = new MyComboBox(true);
		conNumber = new LabelContainer(cmbNumber, 60, BorderLayout.WEST, true, "账号");
		conNumber.setBounds(new Rectangle(15, 5, 280, 30));
		
		cmbNumber.addItems(LoginData.getData().toArray(new UserInfo[0]));
		
		//密码
		txtPassword = new JPasswordField();
		conPassword = new LabelContainer(txtPassword, 60, BorderLayout.WEST, true, "密码");
		conPassword.setBounds(new Rectangle(15, 40, 280, 30));
		
		//按钮
		btnLogin = new JButton("登录");
		btnLogin.setBounds(new Rectangle(15, 75, 280, 30));

		jp.add(conNumber);
		jp.add(conPassword);
		jp.add(btnLogin);
		
		this.setLayout(new BorderLayout());
		this.add(jp, BorderLayout.CENTER);

		//设置窗口大小
		this.setSize(320, 170);
		this.setResizable(false);

		//设置图标标题
		this.setIconImage(ImageLoader.getIcon("my_24.png").getImage());
		setTitle("铭洋管理系统"); // 设置窗口标题
		setVisible(true); // 设置可见
		setLocationRelativeTo(getOwner()); // 居中
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 关闭程序
	}
	
	public void initToolBar()
	{
		JToolBar toolBar = new JToolBar(); 
		toolBar.setFloatable(false);
		this.add(toolBar, BorderLayout.PAGE_START);

		btnRegister = new JButton("注册");
		toolBar.add(btnRegister);
	}
	
	/**
	  * @功能描述 添加监听事件
	  * @作者 黎嘉杰 
	  * @日期 2016年9月11日 下午11:10:20 
	  * @参数 
	  * @返回
	  */
	private void addListener() {

		//登录按钮监听事件
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					actionLogin(e);
				}catch(Exception err)
				{
					err.printStackTrace();
					new ExpHandle(LoginUI.this, err);
				}
				
			}
		});
		
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					actionRegister(e);
				}catch(Exception err)
				{
					err.printStackTrace();
					new ExpHandle(LoginUI.this, err);
				}
				
			}
		});
	}
	
	/**
	  * @功能描述 登录操作
	  * @作者 黎嘉杰 
	  * @日期 2016年9月11日 下午11:11:11 
	  * @参数 
	  * @返回
	  */
	protected void actionLogin(ActionEvent e) throws Exception
	{
		//获取输入的账号和密码
		String password = String.valueOf(txtPassword.getPassword());
		Object obj =  cmbNumber.getSelectedItem();
		
		String number = null;
		if(obj instanceof UserInfo)
		{
			number = ((UserInfo)obj).getNumber();
		}
		else
		{
			number = obj.toString();
		}
		
		//判断账号是否为空
		if(number == null || "".equals(number))
		{
			MsgBox.showInfo(this, "账号不能为空！");
			return;
		}
		
		//判断密码是否为空
		if(password == null || "".equals(password))
		{
			MsgBox.showInfo(this, "密码不能为空！");
			return;
		}
		
		//获取当前账号的用户， 如果为空则当前账号不存在
		LinkedList<Info> list = IHttpUser.getInstance().getInfoList("a.fnumber = '" + number + "'", "a.fnumber");
		if(list == null || list.isEmpty())
		{
			MsgBox.showInfo(this, "账号或密码错误！");
			return;
		}

		//获取当前账号的用户实体， 并判断密码是否正确
		UserInfo userInfo = (UserInfo) list.get(0);
		if(!password.equals(userInfo.getPassword()))
		{
			MsgBox.showInfo(this, "账号或密码错误！");
			return;
		}
		
		ShopInfo shopInfo = userInfo.getDefShopInfo();
		if(shopInfo == null || shopInfo.getId() == null)
		{
			MsgBox.showInfo(this, "当前用户无所属店铺， 请联系管理员！");
			return;
		}
				
		//获取当前用户所属店铺
		shopInfo = (ShopInfo) IHttpShop.getInstance().getInfo(shopInfo.getId());
		if(shopInfo == null || shopInfo.getId() == null)
		{
			MsgBox.showInfo(this, "当前用户无所属店铺， 请联系管理员！");
			return;
		}
		
		//设置当前上下文
		ContextUtil.setUserInfo(userInfo);
		ContextUtil.setShopInfo(shopInfo);
		
		if(!PermissionUtil.isSuperUser())
		{
			ShopStatusType stutas = IHttpShopBalance.getInstance().getShopStatus(shopInfo.getId());
			stutas = stutas != null ? stutas : ShopStatusType.ARREARS;
			if(stutas == ShopStatusType.ARREARS)
			{
				MsgBox.showInfo(this, "当前店铺已欠费， 请先充值！");
				return;
			}
			else if(stutas == ShopStatusType.WARNNING)
			{
				MsgBox.showInfo(this, "当前店铺余额不足以支付当月费用， 请及时充值！");
			}
		}
		
		UserInfo cacheUserInfo = new UserInfo();
		cacheUserInfo.setId(userInfo.getId());
		cacheUserInfo.setNumber(userInfo.getNumber());
		cacheUserInfo.setName(userInfo.getName());
		LinkedList<UserInfo> cache = LoginData.getData();
		if(cache.contains(cacheUserInfo))
			cache.remove(cacheUserInfo);
		cache.addFirst(cacheUserInfo);
		int index = cache.size() - 1;
		while(index >= 5)
		{
			cache.removeLast();
			index--;
		}
		LoginData.saveData();
		
		CacheData.loadCache(null);
		
		new WZZUI();
		this.dispose();
	
		BarcodeScanner.startScanner();
		ShopParamInfo spInfo = ParamData.getShopParamInfo();
		if(spInfo != null)
		{
			if(spInfo.getIsCustomerCard()) 
				(new CardReader()).start();
		}
	}

	protected void actionRegister(ActionEvent e) throws Exception
	{
		ActionListener qLs = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		
		UiFactory.openEditUI(this, RegisterEditUI.class, qLs, null);
	}
}
