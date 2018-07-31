package bill.purchase.mutil;

import java.util.Date;

import bean.IHttp;
import bean.bill.BillListUI;
import bill.purchase.PurchaseQueryUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.RptAlign;
import em.SortType;
import myswing.table.ColumnInfo;

public class PurchaseMutilListUI extends BillListUI{
	private static final long serialVersionUID = 1L;

	public PurchaseMutilListUI()
	{
		super("采购单");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("bizdate", "采购日期", 100, Date.class));
		colList.addLast(new ColumnInfo("supplier_name", "供应商", 100));
		colList.addLast(new ColumnInfo("amt", "金额", 100));
		colList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
	}

	protected Class<?> getEditUI() {
		return PurchaseMutilEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return PurchaseMutilInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpPurchaseMutil.getInstance();
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

	protected Class<?> getQueryUI()
	{
		return PurchaseQueryUI.class;
	}
}
