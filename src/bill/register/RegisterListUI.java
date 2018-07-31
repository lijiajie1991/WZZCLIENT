package bill.register;

import bean.IHttp;
import bean.bill.BillListUI;
import common.db.FilterInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.PermissionUtil;
import em.ProfessionType;
import em.SortType;
import myswing.table.ColumnInfo;

public class RegisterListUI extends BillListUI{
	private static final long serialVersionUID = 1L;

	public RegisterListUI()
	{
		super("注册列表");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("pft", "类型", 100, ProfessionType.class));
		colList.addLast(new ColumnInfo("shopName", "店名", 100));
		colList.addLast(new ColumnInfo("shopPhone", "电话", 100));
		colList.addLast(new ColumnInfo("shopAddress", "地址", 300));
	}

	protected Class<?> getEditUI() {
		return RegisterEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return RegisterInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpRegister.getInstance();
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
		return null;
	}

	public boolean isCanOpen() {
		return PermissionUtil.isSuperUser();
	}

	
}
