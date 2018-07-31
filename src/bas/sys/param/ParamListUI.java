package bas.sys.param;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.FilterInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.SortType;
import myswing.table.ColumnInfo;

public class ParamListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public ParamListUI()
	{
		super("参数设置");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 100));
		colList.addLast(new ColumnInfo("val", "参数值", 100));
	}

	protected Class<?> getEditUI() {
		return ParamEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return ParamInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpParam.getInstance();
	}

	protected FilterInfo getFilterInfo() {
		FilterInfo filter = new FilterInfo();
		return filter;
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
