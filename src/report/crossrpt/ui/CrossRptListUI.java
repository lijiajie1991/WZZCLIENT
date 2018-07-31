package report.crossrpt.ui;

import bean.IHttp;
import report.base.ui.RptListUI;
import report.crossrpt.info.CrossRptInfo;
import report.crossrpt.info.IHttpCrossRpt;

public class CrossRptListUI extends RptListUI{
	
	public CrossRptListUI() {
		super("交叉报表");
	}

	private static final long serialVersionUID = 1L;

	protected Class<?> getEditUI() {
		return CrossRptEditUI.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpCrossRpt.getInstance();
	}

	protected Class<?> getInfoClass() {
		return CrossRptInfo.class;
	}

	protected Class<?> getQueryUI() {
		return null;
	}
}
