package bill.shoppayment;

import java.util.Date;

import bean.IHttp;
import bean.bill.BillListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.RptAlign;
import em.SortType;
import myswing.table.ColumnInfo;

public class ShopPayMentListUI extends BillListUI{
	private static final long serialVersionUID = 1L;

	public ShopPayMentListUI()
	{
		super("店铺充值单");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("bizdate", "业务日期", 100, Date.class));
		colList.addLast(new ColumnInfo("customershop_name", "店铺", 100));
		colList.addLast(new ColumnInfo("amt", "金额", 100));
		colList.addLast(new ColumnInfo("isSms", "是否开通短信服务", 100, Boolean.class, RptAlign.RIGHT));
		colList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
	}

	protected Class<?> getEditUI() {
		return ShopPayMentEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return ShopPayMentInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpShopPayMent.getInstance();
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
