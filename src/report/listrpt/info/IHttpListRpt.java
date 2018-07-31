package report.listrpt.info;

import report.base.bean.IHttpRpt;

public class IHttpListRpt extends IHttpRpt {
	protected static IHttpListRpt instance = null;
	
    public IHttpListRpt() {
		super("IHttpListRpt");
	}

    public static IHttpListRpt getInstance()
    {
    	if(instance == null)
    		instance = new IHttpListRpt();
    	return instance;
    }
}
