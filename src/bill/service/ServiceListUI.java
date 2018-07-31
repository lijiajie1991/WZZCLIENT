package bill.service;

import java.util.Date;

import bean.IHttp;
import bean.bill.BillListUI;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.PayType;
import em.RptAlign;
import em.SortType;
import myswing.table.ColumnInfo;

public class ServiceListUI extends BillListUI{
	private static final long serialVersionUID = 1L;

	public ServiceListUI()
	{
		super("服务单");
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("number", "编码", 100));
		colList.addLast(new ColumnInfo("bizdate", "业务日期", 100, Date.class));
		colList.addLast(new ColumnInfo("person_name", "服务员", 100));
		colList.addLast(new ColumnInfo("customer_name", "客户", 100));
		colList.addLast(new ColumnInfo("project_name", "项目", 100));
		colList.addLast(new ColumnInfo("price", "单价", 100));
		colList.addLast(new ColumnInfo("qty", "次数", 100));
		colList.addLast(new ColumnInfo("amt", "金额", 100));
		colList.addLast(new ColumnInfo("factamt", "收取金额", 100));
		colList.addLast(new ColumnInfo("isOt", "是否加班", 100, Boolean.class, RptAlign.RIGHT));
		colList.addLast(new ColumnInfo("isSpec", "是否点牌", 100, Boolean.class, RptAlign.RIGHT));
		colList.addLast(new ColumnInfo("pt", "付款方式", 100, PayType.class));
		colList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
	}

	protected Class<?> getEditUI() {
		return ServiceEditUI.class;
	}

	protected Class<?> getInfoClass() {
		return ServiceInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpService.getInstance();
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
	
	protected Class<?> getQueryUI()
	{
		return ServiceQueryUI.class;
	}
}
