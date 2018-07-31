package bill.sale.mutil;

import java.util.Date;

import bean.IHttp;
import bean.bill.BillListUI;
import bill.sale.SaleQueryUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.PayType;
import em.RptAlign;
import em.SortType;
import myswing.table.ColumnInfo;

public class SaleMutilListUI extends BillListUI{
	private static final long serialVersionUID = 1L;

	public SaleMutilListUI()
	{
		super("销售单");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("bizdate", "销售日期", 100, Date.class));
		colList.addLast(new ColumnInfo("person_name", "销售员", 100));
		colList.addLast(new ColumnInfo("customer_name", "客户", 100));
		colList.addLast(new ColumnInfo("factamt", "收取金额", 100));
		colList.addLast(new ColumnInfo("pt", "付款方式", 100, PayType.class));
		colList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
	}

	protected Class<?> getEditUI() {
		return SaleMutilEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return SaleMutilInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpSaleMutil.getInstance();
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
		return SaleQueryUI.class;
	}
}
