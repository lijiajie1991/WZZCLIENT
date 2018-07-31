package bas.sys.shopparam;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.ShopType;
import em.SortType;
import myswing.table.ColumnInfo;

public class ShopParamListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public ShopParamListUI()
	{
		super("店铺参数设置");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 100));
		colList.addLast(new ColumnInfo("shopPhone", "店长手机号码", 150));
		colList.addLast(new ColumnInfo("isSaleEntry", "启用多分录销售单", 150, Boolean.class));
		colList.addLast(new ColumnInfo("isPurchaseEntry", "启用多分录采购单", 150, Boolean.class));
		colList.addLast(new ColumnInfo("isBossSms", "启用营业情况短信通知", 150, Boolean.class));
		colList.addLast(new ColumnInfo("isCustomerSms", "启用客户短信通知", 150, Boolean.class));
		colList.addLast(new ColumnInfo("isBarCode", "启用条码销售", 150, Boolean.class));
		colList.addLast(new ColumnInfo("isCustomerCard", "启用会员卡", 150, Boolean.class));
	}

	protected Class<?> getEditUI() {
		return ShopParamEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return ShopParamInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpShopParam.getInstance();
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
		return st == null || st == ShopType.STORE;
	}
	
}
