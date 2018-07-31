package report.base.bean;

import bean.EntryInfo;
import common.db.BeanTableInfo;
import report.base.mj.RptAlign;
import report.base.mj.RptDataType;

public class RptColumnInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected String keyStr = null;
	protected String name = null;
	protected String val = null;
	protected int width = 0;
	protected RptAlign align = RptAlign.CENTER;
	protected RptDataType dt = RptDataType.STR;
	
	protected boolean isSum = false;
	protected int level = 0;

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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public RptAlign getAlign() {
		return align;
	}

	public void setAlign(RptAlign align) {
		this.align = align;
	}

	public RptDataType getDt() {
		return dt;
	}

	public void setDt(RptDataType dt) {
		this.dt = dt;
	}

	public boolean getIsSum() {
		return isSum;
	}

	public void setIsSum(boolean isSum) {
		this.isSum = isSum;
	}
	
	

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(RptColumnInfo.class.getName());
		bt.setTableName("sys_rpt_column");
		bt.setPk("0033");
		return bt;
	}
}
