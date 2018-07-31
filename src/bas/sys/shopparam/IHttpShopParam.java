package bas.sys.shopparam;

import bean.IHttpSys;

public class IHttpShopParam extends IHttpSys {
	protected static IHttpShopParam instance = null;
	
    public IHttpShopParam() {
		super("IHttpShopParam");
	}

    public static IHttpShopParam getInstance()
    {
    	if(instance == null)
    		instance = new IHttpShopParam();
    	return instance;
    }
	
}
