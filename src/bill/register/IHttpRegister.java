package bill.register;

import bean.bill.IHttpBill;

public class IHttpRegister extends IHttpBill {
	protected static IHttpRegister instance = null;
	
    public IHttpRegister() {
		super("IHttpRegister");
	}

    public static IHttpRegister getInstance()
    {
    	if(instance == null)
    		instance = new IHttpRegister();
    	return instance;
    }
}