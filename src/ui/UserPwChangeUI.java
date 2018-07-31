package ui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import bas.sys.user.IHttpUser;
import bas.sys.user.UserInfo;
import common.util.ContextUtil;
import common.util.ImageLoader;
import exp.ExpHandle;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.msgbox.MsgBox;

public class UserPwChangeUI extends JFrame{
	private static final long serialVersionUID = 1L;
	
	
	private JFrame ower = null;

	//账号
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	//密码
	private LabelContainer conPassword = null;
	private JPasswordField txtPassword = null;
	
	//密码
	private LabelContainer conConPassword = null;
	private JPasswordField txtConPassword = null;

	//登录按钮
	private JButton btnConfirm = null;
	
	
	public UserPwChangeUI(JFrame ower) throws Exception {
		super();
		
		this.ower = ower;
		if(ower != null)
		{
			ower.setEnabled(false);
		}
		
		initUI();
		loadData();
		addListener();
	}
	
	protected void initUI() 
	{
		JPanel jp = new JPanel();
		jp.setLayout(null);
		
		//账号
		txtNumber = new FormatInputField();
		conNumber = new LabelContainer(txtNumber, 60, BorderLayout.WEST, true, "账号");
		conNumber.setBounds(new Rectangle(15, 5, 280, 30));
		
		//密码
		txtPassword = new JPasswordField();
		conPassword = new LabelContainer(txtPassword, 60, BorderLayout.WEST, true, "密码");
		conPassword.setBounds(new Rectangle(15, 40, 280, 30));
		
		//密码
		txtConPassword = new JPasswordField();
		conConPassword = new LabelContainer(txtConPassword, 60, BorderLayout.WEST, true, "确认密码");
		conConPassword.setBounds(new Rectangle(15, 75, 280, 30));
		
		//按钮
		btnConfirm = new JButton("确定");
		btnConfirm.setBounds(new Rectangle(15, 110, 280, 30));

		jp.add(conNumber);
		jp.add(conPassword);
		jp.add(conConPassword);
		jp.add(btnConfirm);
		
		this.setLayout(new BorderLayout());
		this.add(jp, BorderLayout.CENTER);

		//设置窗口大小
		this.setSize(320, 200);
		this.setResizable(false);

		//设置图标标题
		this.setIconImage(ImageLoader.getIcon("my_24.png").getImage());
		setTitle("修改账号密码"); // 设置窗口标题
		setVisible(true); // 设置可见
		setLocationRelativeTo(getOwner()); // 居中
	}
	
	
	protected void loadData() throws Exception
	{
		UserInfo userInfo = (UserInfo) IHttpUser.getInstance().getInfo(ContextUtil.getUserId());
		txtNumber.setText(userInfo.getNumber());
	}
	
	/**
	  * @功能描述 添加监听事件
	  * @作者 黎嘉杰 
	  * @日期 2016年9月11日 下午11:10:20 
	  * @参数 
	  * @返回
	  */
	protected void addListener() {
		//登录按钮监听事件
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					actionConfirm(e);
				}catch(Exception err)
				{
					err.printStackTrace();
					new ExpHandle(UserPwChangeUI.this, err);
				}
				
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try{
					actionExist(null);
				}catch(Exception err)
				{
					err.printStackTrace();
					new ExpHandle(UserPwChangeUI.this, err);
				}
			}
		});
	}
	
	protected void actionConfirm(ActionEvent e) throws Exception
	{
		String number = txtNumber.getText().trim();
		String password1 = String.valueOf(txtPassword.getPassword()).trim();
		String password2 = String.valueOf(txtConPassword.getPassword()).trim();
		
		if("".equals(number))
		{
			MsgBox.showInfo(this, "账号不能为空！");
			return;
		}
		
		if("".equals(password1))
		{
			MsgBox.showInfo(this, "密码不能为空！");
			return;
		}
		
		if("".equals(password2))
		{
			MsgBox.showInfo(this, "确认密码不能为空！");
			return;
		}
		
		if(!password1.equals(password2))
		{
			MsgBox.showInfo(this, "两次输入的密码不一致！");
			return;
		}
		
		UserInfo userInfo = (UserInfo) IHttpUser.getInstance().getInfo(ContextUtil.getUserId());
		userInfo.setNumber(number);
		userInfo.setPassword(password1);
		IHttpUser.getInstance().save(userInfo);
		ContextUtil.setUserInfo(userInfo);
		MsgBox.showInfo(this, "修改成功！");
		actionExist(e);
	}
	
	protected void actionExist(ActionEvent e) throws Exception
	{
		this.dispose();
		if(ower != null)
		{
			ower.setEnabled(true);
			ower.setVisible(true);
		}
		
	}
}
