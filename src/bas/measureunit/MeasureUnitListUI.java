package bas.measureunit;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.SortType;
import myswing.table.ColumnInfo;

public class MeasureUnitListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public MeasureUnitListUI()
	{
		super("计量单位");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 100));
		colList.addLast(new ColumnInfo("coe", "转换系数", 100));
	}

	protected Class<?> getEditUI() {
		return MeasureUnitEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return MeasureUnitInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpMeasureUnit.getInstance();
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
