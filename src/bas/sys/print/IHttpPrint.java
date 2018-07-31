package bas.sys.print;

import java.util.HashMap;
import java.util.LinkedList;

import bean.IHttpSys;
import common.db.Context;
import common.util.ContextUtil;
import em.ShopStatusType;

public class IHttpPrint extends IHttpSys {
	protected static IHttpPrint instance = null;
	
    public IHttpPrint() {
		super("IHttpPrint");
	}

    public static IHttpPrint getInstance()
    {
    	if(instance == null)
    		instance = new IHttpPrint();
    	return instance;
    }
    
    public LinkedList<PrintInfo> getPrintInfoList(String shopId, String clsName) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getPrintInfoList");
		map.put("paramQty", "3");
		
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", shopId);
		
		map.put("paramType_2", String.class.getName());
		map.put("paramVal_2", clsName);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof LinkedList)
		{
			return (LinkedList<PrintInfo>) obj;
		}
		
		return null;
   
	}
	
}
