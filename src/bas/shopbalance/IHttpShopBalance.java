package bas.shopbalance;

import java.util.HashMap;

import bean.base.IHttpBase;
import common.db.Context;
import common.util.ContextUtil;
import em.ShopStatusType;

public class IHttpShopBalance extends IHttpBase {
	protected static IHttpShopBalance instance = null;
	
    public IHttpShopBalance() {
		super("IHttpShopBalance");
	}

    public static IHttpShopBalance getInstance()
    {
    	if(instance == null)
    		instance = new IHttpShopBalance();
    	return instance;
    }
	
    public ShopStatusType getShopStatus(String shopId) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getShopStatus");
		map.put("paramQty", "2");
		
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", shopId);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof ShopStatusType)
		{
			return (ShopStatusType) obj;
		}
		
		return null;
   
	}
}
