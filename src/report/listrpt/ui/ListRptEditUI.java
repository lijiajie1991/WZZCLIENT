package report.listrpt.ui;

import bean.IHttp;
import report.base.ui.RptEditUI;
import report.listrpt.info.IHttpListRpt;
import report.listrpt.info.ListRptInfo;

public class ListRptEditUI extends RptEditUI{
	private static final long serialVersionUID = 1L;

	public ListRptEditUI()
	{
		super();
	}

	public void initUI(int width, int height) {
		super.initUI(width, height);
		conType.setVisible(false);
	}

	protected Class<?> getInfoClass() {
		return ListRptInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpListRpt.getInstance();
	}

	protected String getEditUITitle() {
		return "列表报表";
	}
	
	
}
