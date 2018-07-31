package bill.advancerecord;

import bean.bill.IHttpBill;

public class IHttpAdvanceRecord extends IHttpBill {
	protected static IHttpAdvanceRecord instance = null;
	
    public IHttpAdvanceRecord() {
		super("IHttpAdvanceRecord");
	}

    public static IHttpAdvanceRecord getInstance()
    {
    	if(instance == null)
    		instance = new IHttpAdvanceRecord();
    	return instance;
    }
}
