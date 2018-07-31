package bas.person;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.PersonStatusType;
import em.SortType;
import myswing.table.ColumnInfo;

public class PersonListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public PersonListUI()
	{
		super("员工");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 100));
		colList.addLast(new ColumnInfo("job_name", "职位", 100));
		colList.addLast(new ColumnInfo("pst", "状态", 100, PersonStatusType.class));
	}

	protected Class<?> getEditUI() {
		return PersonEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return PersonInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpPerson.getInstance();
	}

	
	protected SortInfo getSortInfo() {
		SortInfo sortInfo = super.getSortInfo();
		if(sortInfo == null)
		{
			sortInfo = new SortInfo();
			sortInfo.addItem(new SortItemInfo("a.pst", SortType.ASC));
		}
			
		return sortInfo;
	}

	
}
