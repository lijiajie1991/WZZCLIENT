package bas.attendance;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.AttendanceDaysType;
import em.AttendanceType;
import em.RptAlign;
import em.SortType;
import myswing.table.ColumnInfo;

public class AttendanceListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public AttendanceListUI()
	{
		super("考勤策略");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 150));
		colList.addLast(new ColumnInfo("at", "类型", 100, AttendanceType.class));
		colList.addLast(new ColumnInfo("adt", "统计时间", 100, AttendanceDaysType.class));
		colList.addLast(new ColumnInfo("isEnable", "是否生效", 100, Boolean.class, RptAlign.RIGHT));
	}

	protected Class<?> getEditUI() {
		return AttendanceEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return AttendanceInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpAttendance.getInstance();
	}

	
	protected SortInfo getSortInfo() {
		SortInfo sortInfo = super.getSortInfo();
		if(sortInfo == null)
		{
			sortInfo = new SortInfo();
			sortInfo.addItem(new SortItemInfo("a.number", SortType.ASC));
		}
			
		return sortInfo;
	}

	
}
