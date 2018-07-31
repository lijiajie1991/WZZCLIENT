package bas.sys.userrole;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.SortType;
import myswing.table.ColumnInfo;

public class UserRoleListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public UserRoleListUI()
	{
		super("用户角色");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "账号", 100));
		colList.addLast(new ColumnInfo("name", "名称", 200));
	}

	protected Class<?> getEditUI() {
		return UserRoleEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return UserRoleInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpUserRole.getInstance();
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
