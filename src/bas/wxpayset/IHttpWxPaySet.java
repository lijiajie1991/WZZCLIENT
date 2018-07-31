package bas.wxpayset;

import bean.base.IHttpBase;

public class IHttpWxPaySet extends IHttpBase {
	protected static IHttpWxPaySet instance = null;
	
    public IHttpWxPaySet() {
		super("IHttpWxPaySet");
	}

    public static IHttpWxPaySet getInstance()
    {
    	if(instance == null)
    		instance = new IHttpWxPaySet();
    	return instance;
    }
	
}
