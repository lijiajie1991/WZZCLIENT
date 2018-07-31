package bill.recharge;

import bean.bill.IHttpBill;

public class IHttpRechargeRecord extends IHttpBill {
	protected static IHttpRechargeRecord instance = null;
	
    public IHttpRechargeRecord() {
		super("IHttpRechargeRecord");
	}

    public static IHttpRechargeRecord getInstance()
    {
    	if(instance == null)
    		instance = new IHttpRechargeRecord();
    	return instance;
    }

}
