package bas.sys.param;

import bean.IHttpSys;

public class IHttpParam extends IHttpSys {
	protected static IHttpParam instance = null;
	
    public IHttpParam() {
		super("IHttpParam");
	}

    public static IHttpParam getInstance()
    {
    	if(instance == null)
    		instance = new IHttpParam();
    	return instance;
    }
	
}
