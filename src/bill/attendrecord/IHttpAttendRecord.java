package bill.attendrecord;

import bean.bill.IHttpBill;

public class IHttpAttendRecord extends IHttpBill {
	protected static IHttpAttendRecord instance = null;
	
    public IHttpAttendRecord() {
		super("IHttpAttendRecord");
	}

    public static IHttpAttendRecord getInstance()
    {
    	if(instance == null)
    		instance = new IHttpAttendRecord();
    	return instance;
    }
}
