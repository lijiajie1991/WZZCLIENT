package bas.measureunit;

import bean.base.IHttpBase;

public class IHttpMeasureUnit extends IHttpBase {
	protected static IHttpMeasureUnit instance = null;
	
    public IHttpMeasureUnit() {
		super("IHttpMeasureUnit");
	}

    public static IHttpMeasureUnit getInstance()
    {
    	if(instance == null)
    		instance = new IHttpMeasureUnit();
    	return instance;
    }
	
    protected Class<?> getInfoClass()
	{
		return MeasureUnitInfo.class;
	}
}
