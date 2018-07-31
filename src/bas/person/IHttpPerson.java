package bas.person;

import bean.base.IHttpBase;

public class IHttpPerson extends IHttpBase {
	protected static IHttpPerson instance = null;
	
    public IHttpPerson() {
		super("IHttpPerson");
	}

    public static IHttpPerson getInstance()
    {
    	if(instance == null)
    		instance = new IHttpPerson();
    	return instance;
    }
    
    protected Class<?> getInfoClass()
	{
		return PersonInfo.class;
	}
    
}
