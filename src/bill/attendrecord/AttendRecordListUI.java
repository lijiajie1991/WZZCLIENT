package bill.attendrecord;

import java.util.Date;

import bean.IHttp;
import bean.bill.BillListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.RptAlign;
import em.SortType;
import myswing.table.ColumnInfo;

public class AttendRecordListUI extends BillListUI{
	private static final long serialVersionUID = 1L;

	public AttendRecordListUI()
	{
		super("考勤记录");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("bizdate", "考勤日期", 100, Date.class));
		colList.addLast(new ColumnInfo("person_name", "员工", 100));
		colList.addLast(new ColumnInfo("qty", "次数", 100));
		colList.addLast(new ColumnInfo("isBalance", "是否已结算", 100, Boolean.class, RptAlign.RIGHT));
		colList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
	}

	protected Class<?> getEditUI() {
		return AttendRecordEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return AttendRecordInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpAttendRecord.getInstance();
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
