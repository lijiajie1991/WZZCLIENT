package bas.percent;

import bean.base.IHttpBase;

public class IHttpPercent extends IHttpBase {
	protected static IHttpPercent instance = null;
	
    public IHttpPercent() {
		super("IHttpPercent");
	}

    public static IHttpPercent getInstance()
    {
    	if(instance == null)
    		instance = new IHttpPercent();
    	return instance;
    }
    
}
