package bas.sys.comparam;

import bean.IHttp;
import bean.base.BaseListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.ComType;
import em.ShopType;
import em.SortType;
import myswing.table.ColumnInfo;

public class ComParamListUI extends BaseListUI{
	private static final long serialVersionUID = 1L;

	public ComParamListUI()
	{
		super("串口参数设置");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 0));
		colList.addLast(new ColumnInfo("name", "名称", 0));
		colList.addLast(new ColumnInfo("ct", "类型", 100, ComType.class));
		colList.addLast(new ColumnInfo("ComName", "串口", 100));
		colList.addLast(new ColumnInfo("BoudIndex", "比特率", 100, Integer.class));
		colList.addLast(new ColumnInfo("DataBits", "数据位", 100, Integer.class));
		colList.addLast(new ColumnInfo("StopBits", "停止位", 100, Integer.class));
		colList.addLast(new ColumnInfo("ParityV", "校验位", 100, Integer.class));
	}

	protected Class<?> getEditUI() {
		return ComParamEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return ComParamInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpComParam.getInstance();
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


	public boolean isCanOpen() {
		ShopType st = ContextUtil.getShopType();
		return st == null || st == ShopType.STORE;
	}
}
