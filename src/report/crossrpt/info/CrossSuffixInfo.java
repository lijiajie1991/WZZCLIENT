package report.crossrpt.info;

import common.db.BeanTableInfo;
import report.base.bean.RptColumnInfo;

public class CrossSuffixInfo extends RptColumnInfo{
	private static final long serialVersionUID = 1L;

	public CrossSuffixInfo()
	{
		super();
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(CrossSuffixInfo.class.getName());
		bt.setTableName("sys_rpt_crossSuffix");
		bt.setPk("003701");
		return bt;
	}
}
