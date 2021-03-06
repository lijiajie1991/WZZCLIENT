package bas.project;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.SortType;
import myswing.table.ColumnInfo;

public class ProjectListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public ProjectListUI()
	{
		super("服务项目");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 100));
	}

	protected Class<?> getEditUI() {
		return ProjectEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return ProjectInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpProject.getInstance();
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
