package bas.material.group;

import bean.base.IHttpBase;

public class IHttpMaterialGroup extends IHttpBase {
	protected static IHttpMaterialGroup instance = null;
	
    public IHttpMaterialGroup() {
		super("IHttpMaterialGroup");
	}

    public static IHttpMaterialGroup getInstance()
    {
    	if(instance == null)
    		instance = new IHttpMaterialGroup();
    	return instance;
    }

}
