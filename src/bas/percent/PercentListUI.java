package bas.percent;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.SortType;
import myswing.table.ColumnInfo;

public class PercentListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public PercentListUI()
	{
		super("提成比例");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 300));
	}

	protected Class<?> getEditUI() {
		return PercentEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return PercentInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpPercent.getInstance();
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
