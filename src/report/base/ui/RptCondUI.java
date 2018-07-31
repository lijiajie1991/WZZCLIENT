package report.base.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bas.sys.shop.ShopInfo;
import bas.sys.user.IHttpUser;
import bean.IHttp;
import bean.Info;
import common.db.SortInfo;
import common.util.ContextUtil;
import common.util.ImageLoader;
import myswing.container.LabelContainer;
import myswing.editor.DatePicker;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.msgbox.MsgBox;
import myswing.msgbox.MsgBoxUI;
import report.base.RptFilterInfo;
import report.base.bean.RptConditionInfo;
import report.base.bean.RptInfo;
import report.base.mj.RptConditionType;

public class RptCondUI extends JFrame{
	private static final long serialVersionUID = 1L;
	
	protected ActionListener ls = null;
	
	protected JFrame ower = null;
	protected RptUI rptList = null;
	
	protected HashMap<String, Object> ctx = null;
	
	protected RptInfo info = null; 
	protected HashMap<String, Object> cmp = null;
	protected HashMap<String, RptFilterInfo> cmp_filter = null;
	
	protected JButton btnOk = null;
	protected JButton btnCancle = null;
	
	public RptCondUI()
	{
		super();
	}

	public void initUI(HashMap<String, Object> ctx) {
		this.ctx = ctx;
		
		cmp = new HashMap<String, Object>();
		cmp_filter = new HashMap<String, RptFilterInfo>();
		
		Rectangle rect1 = new Rectangle(5, 5, 250, 30);
		Rectangle rect2 = new Rectangle(290, 5, 250, 30);
		
		info = (RptInfo) ctx.get("info");
		LinkedList<RptConditionInfo> conList = info.getConList();
		for(RptConditionInfo cInfo : conList)
		{
			RptConditionType type = cInfo.getType();
			if(type == RptConditionType.EMP)
				continue;
			
			String key = cInfo.getKeyStr();
			String name = cInfo.getName();
			int seq = cInfo.getSeq();
			
			Rectangle rect = null;
			if((seq + 1) % 2 == 0)
			{
				rect = new Rectangle(rect2);
				rect2.y += 40;
			}
			else
			{
				rect = new Rectangle(rect1);
				rect1.y += 40;
			}
			
			LabelContainer cont = null;
			Component comp = null;
			if(type == RptConditionType.WENBEN)
			{
				comp = new FormatInputField(); 
			}
			else if(type == RptConditionType.ZHENGSHU)
			{
				comp = new FormatInputField(FormatInputField.DT_INTEGER); 
			}
			else if(type == RptConditionType.SHUZI)
			{
				comp = new FormatInputField(FormatInputField.DT_DOUBLE); 
			}
			else if(type == RptConditionType.MEIJU)
			{
				MyComboBox cmb = new MyComboBox();
				cont = new LabelContainer(cmb, 100, BorderLayout.WEST, true, name);
				String itemStr = cInfo.getItems();
				
				String[] items = itemStr.split(";");
				for(String item : items)
					cmb.addItem(item);
				
				cmb.setSelectedIndex(-1);
				comp = cmb;
			}
			else if(type == RptConditionType.SHUJUYUAN)
			{
				try{
					String clsHttp = cInfo.getHttpcls();
					String filter = cInfo.getFilter();
					
					MyComboBox cmb = new MyComboBox();
					LinkedList<Info> infoList = null;
					if(filter == null || "".equals(filter) || !filter.contains(":"))
					{
						IHttp ihttp = (IHttp) Class.forName(clsHttp).getMethod("getInstance").invoke(null);
						infoList = ihttp.getInfoList(null, new SortInfo());
						cmb.addItems(infoList.toArray());
					}
					else
					{
						cmp_filter.put(key, new RptFilterInfo(key, filter));
					}				
					comp = cmb;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else if(type == RptConditionType.RIQI)
			{
				DatePicker pk = new DatePicker(); 
				comp = pk;
			}
			else if(type == RptConditionType.SHOP)
			{
				try{
					LinkedList<ShopInfo> shopList = IHttpUser.getInstance().getUserShopList(ContextUtil.getUserId());
					MyComboBox cmb = new MyComboBox();
					cmb.addItems(shopList.toArray());
					cmb.setSelectedItem(ContextUtil.getShopInfo());
					
					comp = cmb;
				}catch(Exception err)
				{
					err.printStackTrace();
					new MsgBoxUI(this, err);
				}
			
			}
			else
			{
				comp = new JLabel();
			}
			
			cont = new LabelContainer(comp, 120, BorderLayout.WEST, true, name);
			cont.setBounds(rect);
			this.add(cont);
			cmp.put(key, comp);
		}
		
		if(rect1.y > rect2.y)
			rect2.y = rect1.y;
		else
			rect1.y = rect2.y;
		
		ctx.put("width", 550);
		ctx.put("height", rect1.y + 80);
		
		int width = ctx.containsKey("width") ? ((Integer)ctx.get("width")).intValue() : 1024;
		int height = ctx.containsKey("height") ? ((Integer)ctx.get("height")).intValue() : 768;
		
		this.setLayout(null);
		this.setIconImage(ImageLoader.getIcon("pig_24.png").getImage());
		this.setSize(width,  height);
		setResizable(false);
		setVisible(true); // 设置可见
		setLocationRelativeTo(null);

		btnOk = new JButton("查询");
		btnCancle = new JButton("取消");
		addListener();
		
		btnOk.setBounds(rect1);
		btnCancle.setBounds(rect2);
		this.add(btnOk);
		this.add(btnCancle);
		
		this.setTitle(info.getName() + "过滤");
	}
	
	protected void addListener()
	{
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnOk_action(e);
				} catch (Exception err) {
					new MsgBoxUI(ower, err);
					err.printStackTrace();
				}
			}
		});
		
		btnCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnCancle_action(e);
				} catch (Exception err) {
					new MsgBoxUI(ower, err);
					err.printStackTrace();
				}
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					exit();
				} catch (Exception err) {
					new MsgBoxUI(ower, err);
					err.printStackTrace();
				}
			}
		});
		
		
		LinkedList<RptConditionInfo> conList = info.getConList();
		Set<String> cmpKeys = cmp.keySet();
		Iterator<String> it = cmpKeys.iterator();
		while(it.hasNext())
		{
			final LinkedList<RptConditionInfo> infoList = new LinkedList<RptConditionInfo>();
			final String key = it.next();
			for(RptConditionInfo cInfo : conList)
			{
				String filter = cInfo.getFilter();
				if(filter ==  null || "".equals(filter) || !filter.contains(":" + key))
					continue;

				infoList.addLast(cInfo);
			}
			
			if(!infoList.isEmpty())
			{
				Object cmpObj = cmp.get(key);
				if(cmpObj instanceof MyComboBox)
				{
					final MyComboBox cmp_cmb = (MyComboBox) cmpObj; 
					cmp_cmb.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							Object obj = cmp_cmb.getSelectedItem();
							String fVal = "";
							if(obj != null)
							{
								if(obj instanceof Info)
									fVal = ((Info)obj).getId();
								else 
									fVal = obj.toString();
							}
							
							
							for(RptConditionInfo cmp_info : infoList)
							{
								try{
									String infoKey = cmp_info.getKeyStr();
									RptFilterInfo ftInfo = cmp_filter.get(infoKey);
									ftInfo.setFilterVal(key, fVal);
									
									MyComboBox cmb_info = (MyComboBox) cmp.get(infoKey);
									
									String httpCls = cmp_info.getHttpcls();
									IHttp ihttp = (IHttp) Class.forName(httpCls).getMethod("getInstance").invoke(null);
									LinkedList<Info> itemList = ihttp.getInfoList(null, new SortInfo());
									cmb_info.addItems(itemList.toArray());
								}catch(Exception err)
								{
									err.printStackTrace();
								}
							}
							
							
						}
					});
				}
			}
		}
		
		
	}
	
	protected HashMap<Object, Object> getConditions() {
		HashMap<Object, Object> con = new HashMap<Object, Object>();
		LinkedList<RptConditionInfo> conList = info.getConList();
		for(RptConditionInfo cInfo : conList)
		{
			String key = cInfo.getKeyStr();
			RptConditionType type = cInfo.getType();
			if(type == RptConditionType.EMP)
				continue;
			
			if(!cmp.containsKey(key))
				continue;
			
			Object obj = cmp.get(key);
			if(type == RptConditionType.WENBEN)
			{
				FormatInputField txt = (FormatInputField) obj;
				String val = txt.getText();
				val = val != null ? val : "";
				con.put(key, val);
			}
			else if(type == RptConditionType.ZHENGSHU)
			{
				FormatInputField txt = (FormatInputField) obj;  
				int val = txt.getIntValue();
				con.put(key, val);
			}
			else if(type == RptConditionType.SHUZI)
			{
				FormatInputField txt = (FormatInputField) obj;  
				double val = txt.getDoubleValue();
				con.put(key, val);
			}
			else if(type == RptConditionType.MEIJU || type == RptConditionType.SHUJUYUAN || type == RptConditionType.SHOP)
			{
				MyComboBox cmb = (MyComboBox) obj;  
				Object itemObj = cmb.getSelectedItem();
				if(itemObj == null)
				{
					con.put(key, "");
				}			
				else if(itemObj instanceof Info)
				{
					Info itemInfo = (Info)itemObj;
					con.put(key, itemInfo.getId());
				}
				
				con.put(key + "_info", itemObj);
			}
			else if(type == RptConditionType.RIQI)
			{
				DatePicker pk = (DatePicker) obj;
				con.put(key, pk.getDateStr());
			}

		}
		
		return con;
	}
	
	public void setConditions(HashMap<Object, Object> con) throws Exception {

		LinkedList<RptConditionInfo> conList = info.getConList();
		for(RptConditionInfo cInfo : conList)
		{
			String key = cInfo.getKeyStr();
			RptConditionType type = cInfo.getType();
			if(type == RptConditionType.EMP)
				continue;
			
			if(!cmp.containsKey(key))
				continue;
			
			if(!con.containsKey(key) || con.get(key) == null)
				continue;
			
			Object obj = cmp.get(key);
			if(type == RptConditionType.WENBEN)
			{
				FormatInputField txt = (FormatInputField) obj;
				txt.setText((String) con.get(key));
			}
			else if(type == RptConditionType.ZHENGSHU || type == RptConditionType.SHUZI)
			{
				FormatInputField txt = (FormatInputField) obj;  
				txt.setText(con.get(key).toString());
			}
			else if(type == RptConditionType.MEIJU || type == RptConditionType.SHUJUYUAN || type == RptConditionType.SHOP)
			{
				MyComboBox cmb = (MyComboBox) obj;
				cmb.setSelectedItem(con.get(key + "_info"));
			}
			else if(type == RptConditionType.RIQI)
			{
				DatePicker pk = (DatePicker) obj;
				pk.setDate(con.get(key).toString());
			}
		}
		
	}
	
	protected String verifyInput() throws Exception {
		return null;
	}

	public JFrame getOwer() {
		return ower;
	}

	public void setOwer(JFrame ower) {
		this.ower = ower;
		
		if(this.ower != null)
		{
			this.ower.setEnabled(false);
			this.setEnabled(true);
			this.setVisible(true);
		}
	}

	public RptUI getRptList() {
		return rptList;
	}

	public void setRptList(RptUI rptList) {
		this.rptList = rptList;
	}
	
	private void exit() throws Exception
	{
		if(ower != null)
		{
			ower.setEnabled(true);
			ower.setVisible(true);
		}
		
		if(this.ls != null)
		{
			ls.actionPerformed(null);
			return;
		}
		
		if(rptList != null)
		{
			rptList.query(this.getConditions());
		}
	}
	
	protected boolean isCanQuery()
	{
		String rsl;
		try {
			rsl = verifyInput();
			if(rsl != null && !"".equals(rsl))
			{
				MsgBox.showInfo(null,  rsl);
				return false;
			}
			return true;
		} catch (Exception e) {
			new MsgBoxUI(RptCondUI.this, e);
		}
		
		return false;
	}
	
	private void btnOk_action(ActionEvent e) throws Exception {
		if(isCanQuery())
		{
			this.dispose();
			ower.setEnabled(true);
			ower.setVisible(true);
			rptList.query(getConditions());
		}
	}
	
	private void btnCancle_action(ActionEvent e) throws Exception{
		this.dispose();
		ower.setEnabled(true);
		ower.setVisible(true);
		
		if(this.ls != null)
			ls.actionPerformed(e);
	}

	public void addCancleListener(ActionListener ls)
	{
		this.ls = ls;
	}
}
