package bill.rechargegive;

import java.util.Date;

import bean.IHttp;
import bean.bill.BillListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.RptAlign;
import em.SortType;
import myswing.table.ColumnInfo;

public class RechargeGiveListUI extends BillListUI{
	private static final long serialVersionUID = 1L;

	public RechargeGiveListUI()
	{
		super("充值优惠");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("name", "名称", 100));
		colList.addLast(new ColumnInfo("datefrom", "开始日期", 100, Date.class));
		colList.addLast(new ColumnInfo("dateto", "结束日期", 100, Date.class));
		colList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
	}

	protected Class<?> getEditUI() {
		return RechargeGiveEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return RechargeGiveInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpRechargeGive.getInstance();
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
