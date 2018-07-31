package ui.center;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;

import bas.customer.CustomerInfo;
import bas.person.PersonInfo;
import bas.supplier.SupplierInfo;
import bean.Info;
import cache.BaseData;
import common.util.DateTimeUtil;
import common.util.ImageLoader;
import exp.ExpHandle;
import myswing.container.LabelContainer;
import myswing.editor.DatePicker;
import myswing.editor.MyComboBox;

public class NailCenterQueryUI extends JFrame{
	private static final long serialVersionUID = 1L;
	
	protected JFrame ower = null;
	protected HashMap<String, Object> paramMap = null;
	protected ActionListener queryLs = null;
	
	protected LabelContainer conPerson = null;
	protected MyComboBox cmbPerson = null;
	
	protected LabelContainer conCustomer = null;
	protected MyComboBox cmbCustomer = null;
	
	protected LabelContainer conSupplier = null;
	protected MyComboBox cmbSupplier = null;
	
	protected LabelContainer conDateFrom = null;
	protected DatePicker pkDateFrom = null;
	
	protected LabelContainer conDateTo = null;
	protected DatePicker pkDateTo = null;
	
	protected JButton btnQuery = null;
	protected JButton btnCancle = null;

	public NailCenterQueryUI(JFrame ower, ActionListener ls, int type)
	{
		super();
		this.ower = ower;
		this.queryLs = ls;
		
		try {
			initUI(type);
			initData();
			addListener();
		} catch (Exception e) {
			e.printStackTrace();
			new ExpHandle(this, e);
		}
	}
	
	protected void initUI(int type) throws Exception
	{
		this.setLayout(null);
		
		//设置图标标题
		this.setIconImage(ImageLoader.getIcon("my_24.png").getImage());
		this.setTitle("查询条件"); // 设置窗口标题
		this.setVisible(false); // 设置可见
		this.setResizable(false);
		
		this.setSize(315, 210);
		this.setMinimumSize(new Dimension(315, 210));
		this.setPreferredSize(new Dimension(315, 210));
		setLocationRelativeTo(getOwner()); // 居中
		
		
		pkDateFrom = new DatePicker();
		conDateFrom = new LabelContainer(pkDateFrom, 120, BorderLayout.WEST, true, "开始日期：");
		conDateFrom.setBounds(new Rectangle(5, 5, 300, 30));
		
		pkDateTo = new DatePicker();
		conDateTo = new LabelContainer(pkDateTo, 120, BorderLayout.WEST, true, "结束日期：");
		conDateTo.setBounds(new Rectangle(5, 40, 300, 30));
		
		cmbCustomer = new MyComboBox();
		conCustomer = new LabelContainer(cmbCustomer, 120, BorderLayout.WEST, true, "客户：");
		conCustomer.setBounds(new Rectangle(5, 75, 300, 30));
		conCustomer.setVisible(type == 0);
		
		cmbSupplier = new MyComboBox();
		conSupplier = new LabelContainer(cmbSupplier, 120, BorderLayout.WEST, true, "供应商：");
		conSupplier.setBounds(new Rectangle(5, 75, 300, 30));
		conSupplier.setVisible(type == 1);
		
		cmbPerson = new MyComboBox();
		conPerson = new LabelContainer(cmbPerson, 120, BorderLayout.WEST, true, "销售员：");
		conPerson.setBounds(new Rectangle(5, 110, 300, 30));
		
		btnQuery = new JButton("确定");
		btnQuery.setBounds(new Rectangle(5, 145, 150, 30));
		
		btnCancle = new JButton("取消");
		btnCancle.setBounds(new Rectangle(155, 145, 150, 30));
		
		this.add(conDateFrom);
		this.add(conDateTo);
		this.add(conCustomer);
		this.add(conSupplier);
		this.add(conPerson);
		this.add(btnQuery);
		this.add(btnCancle);
	}
	
	protected void initData() throws Exception
	{
		LinkedList<Info> cusList = BaseData.getInfoList(CustomerInfo.class);
		LinkedList<Info> perList = BaseData.getInfoList(PersonInfo.class);
		LinkedList<Info> supList = BaseData.getInfoList(SupplierInfo.class);
		
		
		LinkedList<Info> cusTempList = new LinkedList<Info>();
		cusTempList.addAll(cusList);
		
		LinkedList<Info> perTempList = new LinkedList<Info>();
		perTempList.addAll(perList);
		
		LinkedList<Info> supTempList = new LinkedList<Info>();
		supTempList.addAll(supList);
		
		PersonInfo allPerson = new PersonInfo();
		allPerson.setId("all");
		allPerson.setNumber("all");
		allPerson.setName("全部");
		perTempList.addFirst(allPerson);
		
		CustomerInfo allCustomer = new CustomerInfo();
		allCustomer.setId("all");
		allCustomer.setNumber("all");
		allCustomer.setName("全部");
		cusTempList.addFirst(allCustomer);
		
		SupplierInfo allSupplier = new SupplierInfo();
		allSupplier.setId("all");
		allSupplier.setNumber("all");
		allSupplier.setName("全部");
		supTempList.addFirst(allSupplier);
		
		cmbPerson.addItems(perTempList.toArray(new PersonInfo[0]));
		cmbCustomer.addItems(cusTempList.toArray(new CustomerInfo[0]));
		cmbSupplier.addItems(supTempList.toArray(new SupplierInfo[0]));
		
		paramMap = new HashMap<String, Object>();
		paramMap.put("df", DateTimeUtil.getDateStr(new Date()));
		paramMap.put("dt", DateTimeUtil.getDateStr(new Date()));
		paramMap.put("customer", allCustomer);
		paramMap.put("supplier", allSupplier);
		paramMap.put("person", allPerson);
		
		pkDateFrom.setDate(new Date());
		pkDateTo.setDate(new Date());
		cmbPerson.setSelectedItem(allPerson);
		cmbCustomer.setSelectedItem(allCustomer);
		cmbSupplier.setSelectedItem(allSupplier);
	}
	
	public void addListener() throws Exception {
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionQuery(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(NailCenterQueryUI.this, err);
				}
			}
		});
		
		btnCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionQuit(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(NailCenterQueryUI.this, err);
				}
			}
		});
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					actionQuit(null);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(NailCenterQueryUI.this, err);
				}
			}
		});
	}
	
	protected void actionQuery(ActionEvent e)
	{
		String df = pkDateFrom.getDateStr();
		String dt = pkDateTo.getDateStr();
		Object cInfo = cmbCustomer.getSelectedItem();
		Object pInfo = cmbPerson.getSelectedItem();
		Object sInfo = cmbSupplier.getSelectedItem();
		
		paramMap.put("df", df);
		paramMap.put("dt", dt);
		paramMap.put("customer", cInfo);
		paramMap.put("supplier", sInfo);
		paramMap.put("person", pInfo);
		
		if(this.queryLs != null)
			this.queryLs.actionPerformed(e);
		
		if(this.ower != null)
		{
			this.ower.setVisible(true);
			this.ower.setEnabled(true);
		}
		
		this.setVisible(false);
	}
	
	protected void actionQuit(ActionEvent e)
	{
		if(this.ower != null)
		{
			this.ower.setVisible(true);
			this.ower.setEnabled(true);
		}
		
		this.setVisible(false);
	}

	public HashMap<String, Object> getParamMap() {
		return paramMap;
	}
	
	public void showUI()
	{
		if(this.ower != null)
		{
			this.ower.setVisible(true);
			this.ower.setEnabled(false);
		}
		
		this.setVisible(true);
	}
	
	public void setCustomerInfo(CustomerInfo cusInfo){
		if(cusInfo != null){
			paramMap.put("customer", cusInfo);
			cmbCustomer.setSelectedItem(cusInfo);
		}
	}
	
	public static HashMap<String, Object> getDefParam()
	{
		PersonInfo allPerson = new PersonInfo();
		allPerson.setId("all");
		allPerson.setNumber("all");
		allPerson.setName("全部");
		
		CustomerInfo allCustomer = new CustomerInfo();
		allCustomer.setId("all");
		allCustomer.setNumber("all");
		allCustomer.setName("全部");
		
		SupplierInfo allSupplier = new SupplierInfo();
		allSupplier.setId("all");
		allSupplier.setNumber("all");
		allSupplier.setName("全部");
		
		
		HashMap<String, Object> pm = new HashMap<String, Object>();
		pm.put("df", DateTimeUtil.getDateStr(new Date()));
		pm.put("dt", DateTimeUtil.getDateStr(new Date()));
		pm.put("customer", allCustomer);
		pm.put("supplier", allSupplier);
		pm.put("person", allPerson);
		
		return pm;
	}
	
	
}
