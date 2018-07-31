package bas.material.group;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.SortType;
import myswing.table.ColumnInfo;

public class MaterialGroupListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public MaterialGroupListUI()
	{
		super("产品类型");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 100));
	}

	protected Class<?> getEditUI() {
		return MaterialGroupEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return MaterialGroupInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpMaterialGroup.getInstance();
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
