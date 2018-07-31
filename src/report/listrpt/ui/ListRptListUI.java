package report.listrpt.ui;

import bean.IHttp;
import report.base.ui.RptListUI;
import report.listrpt.info.IHttpListRpt;
import report.listrpt.info.ListRptInfo;

public class ListRptListUI extends RptListUI{
	private static final long serialVersionUID = 1L;

	public ListRptListUI() {
		super("列表报表");
	}
	
	protected Class<?> getInfoCls() {
		return ListRptInfo.class;
	}
	
	protected Class<?> getEditUICls() {
		return ListRptEditUI.class;
	}
	
	protected Class<?> getEditUI() {
		return ListRptEditUI.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpListRpt.getInstance();
	}

	protected Class<?> getInfoClass() {
		return ListRptInfo.class;
	}
}
