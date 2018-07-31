package bas.shopbalance;

import java.math.BigDecimal;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.SortType;
import myswing.table.ColumnInfo;

public class ShopBalanceListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public ShopBalanceListUI()
	{
		super("店铺余额");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("customerShop_name", "店铺名称", 100));
		colList.addLast(new ColumnInfo("amt", "余额", 100, BigDecimal.class));
		colList.addLast(new ColumnInfo("isSms", "是否开通短信服务", 100, Boolean.class));
	}

	protected Class<?> getEditUI() {
		return ShopBalanceEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return ShopBalanceInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpShopBalance.getInstance();
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
