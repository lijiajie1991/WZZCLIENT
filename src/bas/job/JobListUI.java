package bas.job;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.SortType;
import myswing.table.ColumnInfo;

public class JobListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public JobListUI()
	{
		super("员工职位");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编号", 100));
		colList.addLast(new ColumnInfo("name", "名称", 100));
	}

	protected Class<?> getEditUI() {
		return JobEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return JobInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpJob.getInstance();
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
