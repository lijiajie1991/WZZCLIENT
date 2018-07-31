package bas.sys.menu;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.SortType;
import myswing.table.ColumnInfo;

public class MenuListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public MenuListUI()
	{
		super("菜单维护");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 100));
		colList.addLast(new ColumnInfo("seq", "序号", 100));
	}

	protected Class<?> getEditUI() {
		return MenuEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return MenuInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpMenu.getInstance();
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
