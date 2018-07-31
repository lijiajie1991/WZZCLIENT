package bas.customer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;

import bas.person.PersonInfo;
import bean.Info;
import bill.recharge.IHttpRechargeRecord;
import bill.recharge.RechargeRecordInfo;
import bill.rechargegive.IHttpRechargeGive;
import cache.BaseData;
import common.util.ContextUtil;
import common.util.ImageLoader;
import exp.ExpHandle;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import ui.base.EditUI;

public class FirstChargeUI extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	protected JFrame ower = null;
	protected String dateStr = null;
	protected ActionListener ls = null;
	
	protected LabelContainer conPerson = null;
	protected MyComboBox cmbPerson = null;
	
	protected LabelContainer conAmt = null;
	protected FormatInputField txtAmt = null;
	
	protected LabelContainer conGiveAmt = null;
	protected FormatInputField txtGiveAmt = null;
	
	protected LabelContainer conFactAmt = null;
	protected FormatInputField txtFactAmt = null;
	
	protected JButton btnOK = null;
	protected JButton btnCancle = null;


	public FirstChargeUI(JFrame ower, String dateStr, ActionListener ls) throws Exception
	{
		super();
		this.ower = ower;
		this.dateStr = dateStr;
		this.ls = ls;
		initUI(320, 220);
		onLoad();
		addListener();
		
		if(ower != null)
			ower.setEnabled(false);
	}
	
	public void initUI(int width, int height) {
		this.setLayout(null);

		//设置图标标题
		this.setIconImage(ImageLoader.getIcon("my_24.png").getImage());
		this.setTitle("充值金额"); // 设置窗口标题
		this.setVisible(true); // 设置可见
		this.setResizable(false);
		
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		setLocationRelativeTo(getOwner()); // 居中
		
		cmbPerson = new MyComboBox();
		conPerson = new LabelContainer(cmbPerson, 120, BorderLayout.WEST, true, "负责人：");
		conPerson.setBounds(new Rectangle(5, 5, 300, 30));
		
		txtAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		conAmt = new LabelContainer(txtAmt, 120, BorderLayout.WEST, true, "充值金额：");
		conAmt.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtGiveAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtGiveAmt.setEditable(false);
		conGiveAmt = new LabelContainer(txtGiveAmt, 120, BorderLayout.WEST, true, "赠送金额：");
		conGiveAmt.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtFactAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtFactAmt.setEditable(false);
		conFactAmt = new LabelContainer(txtFactAmt, 120, BorderLayout.WEST, true, "实际存入：");
		conFactAmt.setBounds(new Rectangle(5, 110, 300, 30));
		
		btnOK = new JButton("确定");
		btnOK.setBounds(new Rectangle(100, 145, 100, 30));
		
		btnCancle = new JButton("取消");
		btnCancle.setBounds(new Rectangle(205, 145, 100, 30));
		
		this.add(conPerson);
		this.add(conAmt);
		this.add(conGiveAmt);
		this.add(conFactAmt);
		this.add(btnOK);
		this.add(btnCancle);
	}
	
	protected void onLoad() throws Exception {
		LinkedList<Info> perList = BaseData.getInfoList(PersonInfo.class);
		cmbPerson.addItems(perList.toArray(new PersonInfo[0]));
	}

	public void addListener() throws Exception {
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ls != null)
				{
					try{
						RechargeRecordInfo info = getRechargeInfo();
						HashMap<String, Object> src = new HashMap<String, Object>();
						src.put("info", info);
						src.put("src", e.getSource());
						e.setSource(src);
						actionFinish();
						ls.actionPerformed(e);
					}catch(Exception err)
					{
						err.printStackTrace();
						new ExpHandle(FirstChargeUI.this, err);
					}
				}
				else
				{
					actionFinish();
				}
			}
		});
		btnCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionFinish();
			}
		});
		
		txtAmt.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				try {
					amtChangeAction();
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(FirstChargeUI.this, err);
				}
			}
			public void focusGained(FocusEvent e) {
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				actionFinish();
			}
		});
	}

	public RechargeRecordInfo getRechargeInfo() throws Exception {
		IHttpRechargeRecord iHttpRechargeRecord = IHttpRechargeRecord.getInstance();
		RechargeRecordInfo info = new RechargeRecordInfo();
		
		info.setBizdate(new Date());
		info.setIsAuditTrue(false);
		info.setNumber(iHttpRechargeRecord.getNewNumber());
		info.setShopId(ContextUtil.getShopId());
		
		PersonInfo pInfo = (PersonInfo) cmbPerson.getSelectedItem();
		BigDecimal amt = txtAmt.getBigDecimalValue();
		BigDecimal giveAmt = txtGiveAmt.getBigDecimalValue();
		BigDecimal factAmt = txtFactAmt.getBigDecimalValue();
		
		info.setPersonInfo(pInfo);
		info.setAmt(amt);
		info.setGiveAmt(giveAmt);
		info.setFactAmt(factAmt);
		
		return info;
	}

	protected void amtChangeAction() throws Exception
	{
		BigDecimal amt = txtAmt.getBigDecimalValue();
		amt = amt != null ? amt : BigDecimal.ZERO;
		
		BigDecimal giveAmt = IHttpRechargeGive.getInstance().getGiveAmt(amt, dateStr);
		giveAmt = giveAmt != null ? giveAmt : BigDecimal.ZERO;
		BigDecimal factAmt = giveAmt.add(amt);
		
		txtGiveAmt.setBigDecimalValue(giveAmt);
		txtFactAmt.setBigDecimalValue(factAmt);
	}
	
	protected void actionFinish()
	{
		if(this.ower != null)
		{
			this.ower.setEnabled(true);
			this.ower.setVisible(true);
		}
		
		this.dispose();
	}
	
}
