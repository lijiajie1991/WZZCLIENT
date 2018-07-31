package bas.project.group;

import bean.base.IHttpBase;

public class IHttpProjectGroup extends IHttpBase {
	protected static IHttpProjectGroup instance = null;
	
    public IHttpProjectGroup() {
		super("IHttpProjectGroup");
	}

    public static IHttpProjectGroup getInstance()
    {
    	if(instance == null)
    		instance = new IHttpProjectGroup();
    	return instance;
    }
    
}
