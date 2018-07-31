package bas.material;

import java.util.HashMap;

import bean.base.IHttpBase;

public class IHttpMaterial extends IHttpBase {
	protected static IHttpMaterial instance = null;
	
    public IHttpMaterial() {
		super("IHttpMaterial");
	}

    public static IHttpMaterial getInstance()
    {
    	if(instance == null)
    		instance = new IHttpMaterial();
    	return instance;
    }
    
    protected Class<?> getInfoClass()
	{
		return MaterialInfo.class;
	}
    
    public MaterialInfo getMaterialInfo(String barcode) throws Exception
    {
    	HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getMaterialInfo");
		map.put("paramQty", "1");
		map.put("paramType_0", String.class.getName());
		map.put("paramVal_0", barcode);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof MaterialInfo)
		{
			return (MaterialInfo) obj;
		}
		
		return null;
    }
}