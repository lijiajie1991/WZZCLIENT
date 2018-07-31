package bill.shoppayment;

import bean.bill.IHttpBill;

public class IHttpShopPayMent extends IHttpBill {
	protected static IHttpShopPayMent instance = null;
	
    public IHttpShopPayMent() {
		super("IHttpShopPayMent");
	}

    public static IHttpShopPayMent getInstance()
    {
    	if(instance == null)
    		instance = new IHttpShopPayMent();
    	return instance;
    }
}
