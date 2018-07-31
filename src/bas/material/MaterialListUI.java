package bas.material;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.SortType;
import myswing.table.ColumnInfo;

public class MaterialListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public MaterialListUI()
	{
		super("产品");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("name", "名称", 200));
		colList.addLast(new ColumnInfo("unit_name", "计量单位", 100));
		colList.addLast(new ColumnInfo("model", "规格", 100));
		colList.addLast(new ColumnInfo("price", "默认单价", 100));
	}

	protected Class<?> getEditUI() {
		return MaterialEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return MaterialInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpMaterial.getInstance();
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
