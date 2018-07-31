package bill.recharge;

import java.util.Date;

import bean.IHttp;
import bean.bill.BillListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.RptAlign;
import em.SortType;
import myswing.table.ColumnInfo;

public class RechargeRecordListUI extends BillListUI{
	private static final long serialVersionUID = 1L;

	public RechargeRecordListUI()
	{
		super("充值记录");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("bizdate", "充值日期", 100, Date.class));
		colList.addLast(new ColumnInfo("customer_name", "客户", 100));
		colList.addLast(new ColumnInfo("amt", "金额", 100));
		colList.addLast(new ColumnInfo("giveamt", "赠送金额", 100));
		colList.addLast(new ColumnInfo("factamt", "实际金额", 100));
		colList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
	}

	protected Class<?> getEditUI() {
		return RechargeRecordEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return RechargeRecordInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpRechargeRecord.getInstance();
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
