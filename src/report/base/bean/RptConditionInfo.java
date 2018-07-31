package report.base.bean;

import bean.EntryInfo;
import common.db.BeanTableInfo;
import report.base.mj.RptConditionType;


public class RptConditionInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected String keyStr = null;
	protected String name = null;
	
	protected String items = null;
	protected String httpcls =  null;
	protected String filter =  null;
	protected RptConditionType type = RptConditionType.EMP;
	
	public RptConditionInfo()
	{
		super();
	}

	

	public String getKeyStr() {
		return keyStr;
	}



	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public RptConditionType getType() {
		return type;
	}

	public void setType(RptConditionType type) {
		this.type = type;
	}



	public String getHttpcls() {
		return httpcls;
	}



	public void setHttpcls(String httpcls) {
		this.httpcls = httpcls;
	}
	
	
	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(RptConditionInfo.class.getName());
		bt.setTableName("sys_rpt_condition");
		bt.setPk("0034");
		return bt;
	}
	
}
