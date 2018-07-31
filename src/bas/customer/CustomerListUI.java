package bas.customer;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.SortType;
import myswing.table.ColumnInfo;

public class CustomerListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public CustomerListUI()
	{
		super("客户");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 200));
		colList.addLast(new ColumnInfo("vip_name", "会员类型", 200));
		colList.addLast(new ColumnInfo("balance", "余额", 200));
		colList.addLast(new ColumnInfo("points", "积分", 200));
		colList.addLast(new ColumnInfo("isEnable", "是否生效", 100, Boolean.class));
	}

	protected Class<?> getEditUI() {
		return CustomerEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return CustomerInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpCustomer.getInstance();
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
