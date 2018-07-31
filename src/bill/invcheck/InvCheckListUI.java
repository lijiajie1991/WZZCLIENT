package bill.invcheck;

import java.util.Date;

import bean.IHttp;
import bean.bill.BillListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.RptAlign;
import em.SortType;
import myswing.table.ColumnInfo;

public class InvCheckListUI extends BillListUI{
	private static final long serialVersionUID = 1L;

	public InvCheckListUI()
	{
		super("产品盘点单");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("datefrom", "盘点日期", 100, Date.class));
		colList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
	}

	protected Class<?> getEditUI() {
		return InvCheckEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return InvCheckInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpInvCheck.getInstance();
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
