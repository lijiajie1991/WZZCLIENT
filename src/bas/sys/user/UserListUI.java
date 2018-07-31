package bas.sys.user;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.ShopType;
import em.SortType;
import myswing.table.ColumnInfo;

public class UserListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public UserListUI()
	{
		super("用户");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "账号", 100));
		colList.addLast(new ColumnInfo("name", "名称", 200));
		colList.addLast(new ColumnInfo("defShop_name", "所属店铺", 300));
	}

	protected Class<?> getEditUI() {
		return UserEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return UserInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpUser.getInstance();
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
		FilterInfo filter = new FilterInfo();
		if(ContextUtil.getShopInfo().getSt() == ShopType.STORE)
		{
			filter .addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		}
		else
		{
			filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
			filter.addItem(new FilterItemInfo("a.adminshopId", CompareType.EQUAL, ContextUtil.getShopId()));
			filter.setMkr("#0 or #1");
		}
		
		return filter;
	}

	
	
}
