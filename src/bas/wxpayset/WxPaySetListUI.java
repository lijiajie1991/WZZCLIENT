package bas.wxpayset;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.ShopType;
import em.SortType;
import myswing.table.ColumnInfo;

public class WxPaySetListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public WxPaySetListUI()
	{
		super("微信支付设置");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编号", 100));
		colList.addLast(new ColumnInfo("name", "名称", 300));
	}

	protected Class<?> getEditUI() {
		return WxPaySetEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return WxPaySetInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpWxPaySet.getInstance();
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

	public boolean isCanOpen() {
		ShopType st = ContextUtil.getShopType();
		return st == null || st == ShopType.ADMIN;
	}
}
