package bas.vip;

import bean.base.IHttpBase;

public class IHttpVip extends IHttpBase {
	protected static IHttpVip instance = null;
	
    public IHttpVip() {
		super("IHttpVip");
	}

    public static IHttpVip getInstance()
    {
    	if(instance == null)
    		instance = new IHttpVip();
    	return instance;
    }
    
    protected Class<?> getInfoClass()
	{
		return VipInfo.class;
	}
	
}
