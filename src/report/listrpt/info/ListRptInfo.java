package report.listrpt.info;

import common.db.BeanTableInfo;
import report.base.bean.RptColumnInfo;
import report.base.bean.RptInfo;
import report.base.mj.RptType;

public class ListRptInfo extends RptInfo{
	private static final long serialVersionUID = 1L;

	public ListRptInfo()
	{
		super();
		this.setType(RptType.LIST);
	}
	
	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = super.getBT();
		bt.setClsName(RptColumnInfo.class.getName());
		bt.setTableName("sys_rpt_listrpt");
		bt.setPk("0036");
		return bt;
	}
}
