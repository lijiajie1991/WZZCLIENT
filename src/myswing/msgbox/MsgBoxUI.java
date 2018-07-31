package myswing.msgbox;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import exp.HttpException;

public class MsgBoxUI extends JFrame{
	private static final long serialVersionUID = 1L;

	private boolean isInfo = false;
	
	private JFrame ower = null;
	private String msg = null;
	private String javaStack = null;
	
	private JTextArea jtaMsg = null;
	private JTextArea jtaInfo = null;
	
	private JButton btnOK = null;
	private JButton btnInfo = null;
	
	private JPanel jpMsg = null;
	private JPanel jpInfo = null;
	
	public MsgBoxUI(JFrame ower, Exception e)
			throws HeadlessException {
		super();
		
		this.ower = ower;
		if(e instanceof HttpException)
		{
			HttpException he = (HttpException) e;
			this.msg = he.getExpMsg();
			StackTraceElement[] stack = he.getExpSts();
			StringBuffer str = new StringBuffer();
			
			if(stack != null)
			{
				for(StackTraceElement s : stack)
				{
					str.append(s.toString() + "\n");
				}
			}
			
			str.append("");
			str.append(he.getCaseMsg());
			stack = he.getCaseSts();
			if(stack != null)
			{
				for(StackTraceElement s : stack)
				{
					str.append(s.toString() + "\n");
				}
			}
			
			this.javaStack = str.toString();
		}
		else
		{
			
			this.msg = e.getMessage();
			StackTraceElement[] stack = e.getStackTrace();
			
			StringBuffer str = new StringBuffer();
			for(StackTraceElement s : stack)
			{
				str.append(s.toString() + "\n");
			}
			
			this.javaStack = str.toString();
		}
		
		if(ower != null)
			ower.setEnabled(false);
		
		initUI();
		addListener();
	}
	
	private void initUI()
	{
		this.setSize(400, 150);
		
		jpMsg = new JPanel(new BorderLayout());
		
		Dimension jtaMsgSize = new Dimension(350, 50);
		jtaMsg = new JTextArea(msg);
		jtaMsg.setEditable(false);
		jtaMsg.setSize(jtaMsgSize);
		jtaMsg.setPreferredSize(jtaMsgSize);
		
		JPanel jpBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		Dimension btnSize = new Dimension(100, 30);
		btnOK = new JButton("确定");
		btnOK.setSize(btnSize);
		btnOK.setPreferredSize(btnSize);
		jpBtn.add(btnOK);
		
		btnInfo = new JButton("详细信息");
		btnInfo.setSize(btnSize);
		btnInfo.setPreferredSize(btnSize);
		jpBtn.add(btnInfo);
		
		jpMsg.add(jtaMsg, BorderLayout.CENTER);
		jpMsg.add(jpBtn, BorderLayout.SOUTH);
		
		
		jpInfo = new JPanel(new BorderLayout());
		jtaInfo = new JTextArea();
		jtaInfo.setEditable(false);
		jtaInfo.setText(msg + "\n" + javaStack);
		JScrollPane jspInfo = new JScrollPane(jtaInfo);
		jpInfo.add(jspInfo, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(jpMsg, BorderLayout.CENTER);
		
		setTitle("错误信息"); // 设置窗口标题
		setVisible(true); // 设置可见
		setLocationRelativeTo(getOwner()); // 居中
		
		
	}
	
	private void addListener()
	{
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showInfo();
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
	}
	
	private void showInfo()
	{
		if(isInfo)
		{
			this.setSize(400, 150);
			jpMsg.remove(jpInfo);
			jpMsg.add(jtaMsg, BorderLayout.CENTER);
		}
		else
		{
			this.setSize(400, 300);
			jpMsg.remove(jtaMsg);
			jpMsg.add(jpInfo, BorderLayout.CENTER);
		}
		
		isInfo = !isInfo;
		this.validate();
	}
	
	private void exit()
	{
		if(ower != null)
		{
			ower.setEnabled(true);
			ower.setVisible(true);
		}
		
		this.dispose();
	}
}
