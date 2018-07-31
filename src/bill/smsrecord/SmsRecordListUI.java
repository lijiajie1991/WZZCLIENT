package bill.smsrecord;

import java.util.Date;

import bean.IHttp;
import bean.bill.BillListUI;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.RptAlign;
import em.ShopType;
import em.SortType;
import myswing.table.ColumnInfo;

public class SmsRecordListUI extends BillListUI{
	private static final long serialVersionUID = 1L;

	public SmsRecordListUI()
	{
		super("短信记录");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("bizdate", "发送日期", 100, Date.class));
		colList.addLast(new ColumnInfo("shop_name", "计费店铺", 100));
		colList.addLast(new ColumnInfo("phone", "接收号码", 100));
		colList.addLast(new ColumnInfo("isaudittrue", "是否已发送", 100, Boolean.class, RptAlign.RIGHT));
		colList.addLast(new ColumnInfo("isSuccess", "是否发送成功", 100, Boolean.class, RptAlign.RIGHT));
	}

	protected Class<?> getEditUI() {
		return SmsRecordEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return SmsRecordInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpSmsRecord.getInstance();
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
	
	protected FilterInfo getFilterInfo() {
		return super.getFilterInfo();
	}
	
	public boolean isCanOpen() {
		return true;
	}
}
