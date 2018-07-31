package report.crossrpt;

import report.base.bean.RptColumnInfo;

public class DynColumnInfo {
	protected String key = null;
	protected RptColumnInfo colInfo = null;
	
	public DynColumnInfo()
	{
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public RptColumnInfo getColInfo() {
		return colInfo;
	}

	public void setColInfo(RptColumnInfo colInfo) {
		this.colInfo = colInfo;
	}
}
