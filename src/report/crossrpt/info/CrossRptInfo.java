package report.crossrpt.info;

import java.util.LinkedList;

import common.db.BeanTableInfo;
import report.base.bean.RptColumnInfo;
import report.base.bean.RptInfo;
import report.base.mj.RptType;

public class CrossRptInfo extends RptInfo{
	private static final long serialVersionUID = 1L;
	
	protected LinkedList<CrossSuffixInfo> suffixList = null;
	protected LinkedList<CrossSumInfo> csList = null;
	
	public CrossRptInfo()
	{
		super();
		suffixList = new LinkedList<CrossSuffixInfo>();
		csList = new LinkedList<CrossSumInfo>();
		this.setType(RptType.CROSS);
	}
	
	public LinkedList<CrossSumInfo> getCsList() {
		return csList;
	}

	public void setCsList(LinkedList<CrossSumInfo> csList) {
		this.csList = csList;
	}

	public LinkedList<CrossSuffixInfo> getSuffixList() {
		return suffixList;
	}

	public void setSuffixList(LinkedList<CrossSuffixInfo> suffixList) {
		this.suffixList = suffixList;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = super.getBT();
		bt.setClsName(RptColumnInfo.class.getName());
		bt.setTableName("sys_rpt_crossrpt");
		bt.setPk("0037");
		bt.addEntryBt("suffixList", CrossSuffixInfo.class);
		bt.addEntryBt("csList", CrossSumInfo.class);
		return bt;
	}
}
