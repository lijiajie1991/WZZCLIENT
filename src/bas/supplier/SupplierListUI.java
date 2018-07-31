package bas.supplier;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.SortType;
import myswing.table.ColumnInfo;

public class SupplierListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public SupplierListUI()
	{
		super("供应商");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 200));
		colList.addLast(new ColumnInfo("address", "地址", 200));
	}

	protected Class<?> getEditUI() {
		return SupplierEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return SupplierInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpSupplier.getInstance();
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
