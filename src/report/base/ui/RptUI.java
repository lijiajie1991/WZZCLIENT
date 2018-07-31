package report.base.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bean.Info;
import common.util.BigDecimalUtil;
import exp.ExpHandle;
import myswing.btn.ToolButton;
import myswing.table.ColumnInfo;
import myswing.table.SimpleTable;
import report.base.bean.IHttpRpt;
import report.base.bean.RptColumnInfo;
import report.base.bean.RptInfo;
import report.base.mj.RptDataType;
import ui.base.BaseTabUI;

public abstract class RptUI extends BaseTabUI{
	private static final long serialVersionUID = 1L;

	protected static final Font font = new Font("宋体",Font.PLAIN, 15);
	
	protected HashMap<Object, Object> con = null;
	protected HashMap<String, RptDataType> colDt = null;
	protected HashMap<String, Object> ctx = null;
	
	protected SimpleTable tblMain = null;
	
	protected ToolButton btnQuery = null;	
	protected ToolButton btnRefresh = null;	
	protected ToolButton btnClose = null;	
	
	protected RptInfo info = null;
	
	public RptUI()
	{
		super();
		colDt = new HashMap<String, RptDataType>();
		con = new HashMap<Object, Object>();
	}
	
	
	
	public void initUI(HashMap<String, Object> ctx) throws Exception
	{
		super.initUI(ctx);
		this.ctx = ctx;
		String rptId = (String) ctx.get("rptId");
		info = (RptInfo) this.getHttpInstance().getInfo(rptId);
		
		this.setName(info.getName());
		
		LinkedList<ColumnInfo> colList = new LinkedList<ColumnInfo>();
		LinkedList<RptColumnInfo> col = info.getColList();
		int size = col.size();
		for(int i = 0; i < size; i++)
		{
			RptColumnInfo info = col.get(i);
			colList.addLast(new ColumnInfo(info.getKeyStr(), info.getName(), info.getWidth(), this.getClsByDataType(info.getDt())));
		}
		
		
		tblMain = new SimpleTable(false);
		tblMain.setEditenable(false);
		tblMain.initTable(colList);
		this.add(tblMain, BorderLayout.CENTER);
		
	}
	
	public void initToolBar() throws Exception
	{
		btnQuery = new ToolButton("查询", "");
		btnList.addLast(btnQuery);
		
		btnRefresh = new ToolButton("刷新");
		btnList.addLast(btnRefresh);
		
		btnClose = new ToolButton("关闭");
		btnList.addLast(btnClose);
	}
	
	public void addListener() throws Exception {
		
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionQuery(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRefresh(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
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

	protected void actionQuery(ActionEvent e) throws Exception {
		RptCondUI editUI = new RptCondUI();
		ctx.put("info", info);
		editUI.initUI(ctx);
		editUI.setOwer(ower);
		editUI.setRptList(this);
		editUI.setConditions(con);
	}

	protected void actionRefresh(ActionEvent e) throws Exception
	{
		query(con);
	}
	
	protected void actionClose(ActionEvent e) throws Exception
	{
		this.quitLs.actionPerformed(e);
	}
	
	public void query(HashMap<Object, Object> con) throws Exception {
		
		List<Map<String, Object>> list = getQueryData(con);
		fillData(list, info.getColList());
	}
	
	public List<Map<String, Object>> getQueryData(HashMap<Object, Object> con) throws Exception
	{
		this.con.putAll(con);
		String sql = info.getSqlStr();
		Iterator<Entry<Object, Object>> it = con.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<Object, Object> entry = it.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			
			val = val != null ? val : "";
			if(val instanceof Info)
				continue;
			sql = sql.replaceAll(":" + key, val.toString());
		}
		
		// 执行SQL插入
		List<Map<String, Object>> list = this.getHttpInstance().getRptData(sql);
		
		return list;
	}
	
	protected void fillData(List<Map<String, Object>> data, LinkedList<RptColumnInfo> colList)
	{
		tblMain.removeRows();
		
		int maxLevel = 0;
		HashMap<String, LinkedList<RptColumnInfo>> formulaCols = new HashMap<String, LinkedList<RptColumnInfo>>();
		HashMap<String, BigDecimal> sumMap = new HashMap<String, BigDecimal>();
		
		for(RptColumnInfo col : colList)
		{
			int level = col.getLevel();
			if(level > 0)
			{
				if(!formulaCols.containsKey("" + level))
					formulaCols.put("" + level, new LinkedList<RptColumnInfo>());
				formulaCols.get("" + level).addLast(col);
				
				maxLevel = maxLevel < level ? level : maxLevel;
			}
		}
		
		for(Map<String, Object> map : data)
		{
			LinkedList<Object> row = new LinkedList<Object>();
			for(RptColumnInfo col : colList)
			{
				String key = col.getVal();
				RptDataType type = col.getDt();
				
				Object val = map.containsKey(key) ? map.get(key) : null;;
				if(val != null)
				{
					if(type == RptDataType.BE)
					{
						if("true".equalsIgnoreCase(val.toString()) || "1".equalsIgnoreCase(val.toString()))
							row.addLast(Boolean.TRUE);
						else
							row.addLast(Boolean.FALSE);
					}
					else if(type == RptDataType.QTY || type == RptDataType.AMT || type == RptDataType.RATE || type == RptDataType.WT)
					{
						int scale = 0;
						if(type == RptDataType.QTY)
							scale = 0;
						else if(type == RptDataType.AMT)
							scale = 2;
						else if(type == RptDataType.RATE)
							scale = 4;
						else if(type == RptDataType.WT)
							scale = 1;
						
						BigDecimal value = new BigDecimal(val.toString());
						value = value.setScale(scale, BigDecimal.ROUND_HALF_UP);
						row.addLast(value.toString());
						
						if(col.getIsSum())
						{
							if(!sumMap.containsKey(col.getKeyStr()))
								sumMap.put(col.getKeyStr(), BigDecimal.ZERO);
							
							sumMap.put(col.getKeyStr(), sumMap.get(col.getKeyStr()).add(value));
						}
					}
					else
					{
						row.addLast(val.toString());
					}
				}
				else
				{
					row.addLast(val);
				}
			}
			
			tblMain.insertRow(0, row.toArray());
		}
		
		String[] colsKey = tblMain.getColsKey();
		for(int level = 1; level <= maxLevel; level++)
		{
			LinkedList<RptColumnInfo> formulaColList = formulaCols.get(level + "");
			for(RptColumnInfo cInfo : formulaColList)
			{
				RptDataType type = cInfo.getDt();
				int scale = 0;
				if(type == RptDataType.QTY)
					scale = 0;
				else if(type == RptDataType.AMT)
					scale = 2;
				else if(type == RptDataType.RATE)
					scale = 4;
				else if(type == RptDataType.WT)
					scale = 1;
				
				String valStr = cInfo.getVal();
				if(valStr.contains(":"))
				{
					String[] valKeys = valStr.split(" ");
					
					int rowCount = tblMain.getRowCount();
					for(int rowIndex = 0; rowIndex < rowCount; rowIndex++)
					{
						String formualStr = valStr;
						HashMap<String, Object> rowMap = tblMain.getRowMap(rowIndex);
						for(String valKey : valKeys)
						{
							if(valKey.startsWith(":"))
							{
								valKey = valKey.replaceFirst(":", "");
								BigDecimal val = BigDecimal.ZERO;
								for(String colKey : colsKey)
								{
									if(colKey.startsWith(valKey))
									{
										Object obj = rowMap.get(colKey);
										BigDecimal bdVal = obj != null ? new BigDecimal(obj.toString()) : BigDecimal.ZERO;
										val = val.add(bdVal);
									}
								}
								
								formualStr = formualStr.replace(":" + valKey, val.toString());
							}
						}
						
						BigDecimal val = BigDecimalUtil.parseFormula(formualStr);
						val = val.setScale(scale, BigDecimal.ROUND_HALF_UP);
						tblMain.setValueAt(val, rowIndex, cInfo.getKeyStr());
						if(cInfo.getIsSum())
						{
							if(!sumMap.containsKey(cInfo.getKeyStr()))
								sumMap.put(cInfo.getKeyStr(), BigDecimal.ZERO);
							sumMap.put(cInfo.getKeyStr(), sumMap.get(cInfo.getKeyStr()).add(val));
						}
					}
				}
				else
				{
					BigDecimal val = BigDecimalUtil.parseFormula(valStr);
					val = val.setScale(scale, BigDecimal.ROUND_HALF_UP);
					int rowCount = tblMain.getRowCount();
					for(int rowIndex = 0; rowIndex < rowCount; rowIndex++)
					{
						tblMain.setValueAt(val, rowIndex, cInfo.getKeyStr());
						
						if(cInfo.getIsSum())
						{
							if(!sumMap.containsKey(cInfo.getKeyStr()))
								sumMap.put(cInfo.getKeyStr(), BigDecimal.ZERO);
							sumMap.put(cInfo.getKeyStr(), sumMap.get(cInfo.getKeyStr()).add(val));
						}
					}
				}
			}
		}

		
		if(sumMap.size() > 0)
		{
			LinkedList<Object> subRow = new LinkedList<Object>();
			for(RptColumnInfo col : colList)
			{
				String key = col.getKeyStr();
				if(sumMap.containsKey(key))
					subRow.addLast(sumMap.get(key));
				else
					subRow.addLast("");
			}
			
			subRow.set(1, "合计：");
			tblMain.insertRow(tblMain.getRowCount(), subRow.toArray());
		}
		
		
	}

	protected abstract IHttpRpt getHttpInstance();

	public boolean isCanOpen() {
		return true;
	}
	
	protected Class<?> getClsByDataType(RptDataType dt)
	{
		if(dt == null)
			return String.class;
		
		if(dt == RptDataType.STR)
			return String.class;
		
		if(dt == RptDataType.QTY)
			return Integer.class;
		
		if(dt == RptDataType.AMT || dt == RptDataType.RATE || dt == RptDataType.WT)
			return BigDecimal.class;
		
		if(dt == RptDataType.BE)
			return Boolean.class;
		
		return String.class;	
	}

	public void initCondition() throws Exception
	{
		RptCondUI editUI = new RptCondUI();
		ctx.put("info", info);
		editUI.initUI(ctx);
		editUI.setOwer(ower);
		editUI.setRptList(this);
		editUI.setConditions(con);
		editUI.addCancleListener(quitLs);
	}
}
