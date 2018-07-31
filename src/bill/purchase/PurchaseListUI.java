package bill.purchase;

import java.util.Date;

import bean.IHttp;
import bean.bill.BillListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.RptAlign;
import em.SortType;
import myswing.table.ColumnInfo;

public class PurchaseListUI extends BillListUI{
	private static final long serialVersionUID = 1L;

	public PurchaseListUI()
	{
		super("采购单");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("bizdate", "采购日期", 100, Date.class));
		colList.addLast(new ColumnInfo("supplier_name", "供应商", 100));
		colList.addLast(new ColumnInfo("material_name", "产品", 100));
		colList.addLast(new ColumnInfo("price", "单价", 100));
		colList.addLast(new ColumnInfo("qty", "数量", 100));
		colList.addLast(new ColumnInfo("amt", "金额", 100));
		colList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
	}

	protected Class<?> getEditUI() {
		return PurchaseEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return PurchaseInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpPurchase.getInstance();
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
