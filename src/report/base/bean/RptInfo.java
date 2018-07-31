package report.base.bean;

import java.util.LinkedList;

import bean.bill.BillInfo;
import common.db.BeanTableInfo;
import report.base.mj.RptType;

public abstract class RptInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected String name = null;
	protected String sqlStr = null;
	protected RptType type = RptType.LIST;
	protected boolean isFree = false;	
	protected LinkedList<RptColumnInfo> colList = null;
	protected LinkedList<RptConditionInfo> conList = null;
	protected LinkedList<RptShopInfo> shopList = null;
	
	
	public RptInfo() {
		super();
		colList = new LinkedList<RptColumnInfo>();
		conList = new LinkedList<RptConditionInfo>();
		shopList = new LinkedList<RptShopInfo>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}
	
	public RptType getType() {
		return type;
	}

	public void setType(RptType type) {
		this.type = type;
	}
	
	

	public boolean getIsFree() {
		return isFree;
	}

	public void setIsFree(boolean isFree) {
		this.isFree = isFree;
	}

	public LinkedList<RptColumnInfo> getColList() {
		return colList;
	}

	public void setColList(LinkedList<RptColumnInfo> colList) {
		this.colList = colList;
	}

	public LinkedList<RptConditionInfo> getConList() {
		return conList;
	}

	public void setConList(LinkedList<RptConditionInfo> conList) {
		this.conList = conList;
	}
	
	public LinkedList<RptShopInfo> getShopList() {
		return shopList;
	}

	public void setShopList(LinkedList<RptShopInfo> shopList) {
		this.shopList = shopList;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.addEntryBt("colList", RptColumnInfo.class);
		bt.addEntryBt("conList", RptConditionInfo.class);
		bt.addEntryBt("shopList", RptShopInfo.class);
		return bt;
	}
}
