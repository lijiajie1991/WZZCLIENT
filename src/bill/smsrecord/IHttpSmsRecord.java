package bill.smsrecord;

import bean.bill.IHttpBill;

public class IHttpSmsRecord extends IHttpBill {
	protected static IHttpSmsRecord instance = null;
	
    public IHttpSmsRecord() {
		super("IHttpSmsRecord");
	}

    public static IHttpSmsRecord getInstance()
    {
    	if(instance == null)
    		instance = new IHttpSmsRecord();
    	return instance;
    }
    
}
