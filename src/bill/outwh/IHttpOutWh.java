package bill.outwh;

import bean.bill.IHttpBill;

public class IHttpOutWh extends IHttpBill {
	protected static IHttpOutWh instance = null;
	
    public IHttpOutWh() {
		super("IHttpOutWh");
	}

    public static IHttpOutWh getInstance()
    {
    	if(instance == null)
    		instance = new IHttpOutWh();
    	return instance;
    }
}
