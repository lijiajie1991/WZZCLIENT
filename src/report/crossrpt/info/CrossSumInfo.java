package report.crossrpt.info;

import common.db.BeanTableInfo;
import report.base.bean.RptColumnInfo;

public class CrossSumInfo  extends RptColumnInfo{
	private static final long serialVersionUID = 1L;

	public CrossSumInfo()
	{
		super();
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(CrossSumInfo.class.getName());
		bt.setTableName("sys_rpt_crosssum");
		bt.setPk("003702");
		return bt;
	}
}
