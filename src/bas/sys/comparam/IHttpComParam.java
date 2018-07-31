package bas.sys.comparam;

import bean.base.IHttpBase;

public class IHttpComParam extends IHttpBase {
	protected static IHttpComParam instance = null;
	
    public IHttpComParam() {
		super("IHttpComParam");
	}

    public static IHttpComParam getInstance()
    {
    	if(instance == null)
    		instance = new IHttpComParam();
    	return instance;
    }
	
}
