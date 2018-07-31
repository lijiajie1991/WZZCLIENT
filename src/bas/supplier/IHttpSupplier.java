package bas.supplier;

import bean.base.IHttpBase;

public class IHttpSupplier extends IHttpBase {
	protected static IHttpSupplier instance = null;
	
    public IHttpSupplier() {
		super("IHttpSupplier");
	}

    public static IHttpSupplier getInstance()
    {
    	if(instance == null)
    		instance = new IHttpSupplier();
    	return instance;
    }
    
    protected Class<?> getInfoClass()
	{
		return SupplierInfo.class;
	}
}
