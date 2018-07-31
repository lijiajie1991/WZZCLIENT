package bas.project;

import bean.base.IHttpBase;

public class IHttpProject extends IHttpBase {
	protected static IHttpProject instance = null;
	
    public IHttpProject() {
		super("IHttpProject");
	}

    public static IHttpProject getInstance()
    {
    	if(instance == null)
    		instance = new IHttpProject();
    	return instance;
    }
    
    protected Class<?> getInfoClass()
	{
		return ProjectInfo.class;
	}
    
}
