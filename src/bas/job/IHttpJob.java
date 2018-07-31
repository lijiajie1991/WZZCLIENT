package bas.job;

import bean.base.IHttpBase;

public class IHttpJob extends IHttpBase {
	protected static IHttpJob instance = null;
	
    public IHttpJob() {
		super("IHttpJob");
	}

    public static IHttpJob getInstance()
    {
    	if(instance == null)
    		instance = new IHttpJob();
    	return instance;
    }
	
}
