package bas.discount;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.SortType;
import myswing.table.ColumnInfo;

public class DiscountListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public DiscountListUI()
	{
		super("折扣策略");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 150));
		colList.addLast(new ColumnInfo("vip_name", "会员类型", 100));
		colList.addLast(new ColumnInfo("isEnable", "是否生效", 100, Boolean.class));
	}

	protected Class<?> getEditUI() {
		return DiscountEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return DiscountInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpDiscount.getInstance();
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
