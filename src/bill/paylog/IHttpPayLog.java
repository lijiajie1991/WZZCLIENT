package bill.paylog;

import bean.bill.IHttpBill;

public class IHttpPayLog extends IHttpBill {
	protected static IHttpPayLog instance = null;
	
    public IHttpPayLog() {
		super("IHttpPayLog");
	}

    public static IHttpPayLog getInstance()
    {
    	if(instance == null)
    		instance = new IHttpPayLog();
    	return instance;
    }
    
}
