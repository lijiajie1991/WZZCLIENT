package report.crossrpt.info;

import report.base.bean.IHttpRpt;

public class IHttpCrossRpt extends IHttpRpt {
	protected static IHttpCrossRpt instance = null;
	
    public IHttpCrossRpt() {
		super("IHttpCrossRpt");
	}

    public static IHttpCrossRpt getInstance()
    {
    	if(instance == null)
    		instance = new IHttpCrossRpt();
    	return instance;
    }
}
