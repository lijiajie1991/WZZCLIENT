package bean.base;

import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.ShopType;
import ui.base.ListUI;

public abstract class BaseListUI extends ListUI{
	private static final long serialVersionUID = 1L;

	public BaseListUI()
	{
		this("");
	}

	public BaseListUI(String name)
	{
		super(name);
	}

	protected FilterInfo getFilterInfo() {
		FilterInfo filter = new FilterInfo();
		
		if(BaseInfo.class.isAssignableFrom(this.getInfoClass()))
		{
			if(ContextUtil.getShopInfo().getSt() == ShopType.STORE)
			{
				filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
				filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
				filter.setMkr("#0 or #1");
			}
			else
			{
				filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
			}
		}
		
		FilterInfo supFilter = super.getFilterInfo();
		if(supFilter != null)
		{
			filter.mergeFilter(supFilter, " and ");
		}
		
		return filter;
	}
	
	protected Class<?> getQueryUI()
	{
		return BaseQueryUI.class;
	}
	
	public boolean isCanOpen() {
		return true;
	}
}
