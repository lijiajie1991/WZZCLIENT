package bas.stock;

import bean.base.IHttpBase;

public class IHttpShopStock extends IHttpBase {
	protected static IHttpShopStock instance = null;
	
    public IHttpShopStock() {
		super("IHttpShopStock");
	}

    public static IHttpShopStock getInstance()
    {
    	if(instance == null)
    		instance = new IHttpShopStock();
    	return instance;
    }
    
}
