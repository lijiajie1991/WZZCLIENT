package report.listrpt.ui;

import report.base.bean.IHttpRpt;
import report.base.ui.RptUI;
import report.listrpt.info.IHttpListRpt;
import report.listrpt.info.ListRptInfo;

public class ListRptUI extends RptUI {
	private static final long serialVersionUID = 1L;

	protected IHttpRpt getHttpInstance() {
		return IHttpListRpt.getInstance();
	}

	protected Class<?> getInfoClass() {
		return ListRptInfo.class;
	}
}
