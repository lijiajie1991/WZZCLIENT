package bas.sys.shop;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.ShopType;
import em.SortType;
import myswing.table.ColumnInfo;

public class ShopListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public ShopListUI()
	{
		super("店铺");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 300));
		colList.addLast(new ColumnInfo("st", "类型", 300, ShopType.class));
	}

	protected Class<?> getEditUI() {
		return ShopEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return ShopInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpShop.getInstance();
	}

	
	protected SortInfo getSortInfo() {
		SortInfo sortInfo = super.getSortInfo();
		if(sortInfo == null)
		{
			sortInfo = new SortInfo();
			sortInfo.addItem(new SortItemInfo("a.longnumber", SortType.ASC));
		}
			
		return sortInfo;
	}
}
