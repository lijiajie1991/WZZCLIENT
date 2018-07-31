package bill.inwh;

import bean.bill.IHttpBill;

public class IHttpInWh extends IHttpBill {
	protected static IHttpInWh instance = null;
	
    public IHttpInWh() {
		super("IHttpInWh");
	}

    public static IHttpInWh getInstance()
    {
    	if(instance == null)
    		instance = new IHttpInWh();
    	return instance;
    }
}
