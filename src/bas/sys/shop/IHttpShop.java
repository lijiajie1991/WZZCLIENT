package bas.sys.shop;

import java.util.HashMap;
import java.util.LinkedList;

import bean.IHttpSys;
import common.db.Context;
import common.util.ContextUtil;

public class IHttpShop extends IHttpSys {
	protected static IHttpShop instance = null;
	
    public IHttpShop() {
        super("IHttpShop");
    }
    
    public static IHttpShop getInstance()
    {
    	if(instance == null)
    		instance = new IHttpShop();
    	return instance;
    }
    
    /**
      * @功能描述 获取指定店铺下的所有分店
      * @作者 黎嘉杰 
      * @日期 2016年9月10日 下午11:35:15 
      * @参数 
      * @返回
      */
    public LinkedList<ShopInfo> getChildrenShopList(String shopId) throws Exception
    {
 	   HashMap<String, Object> map = new HashMap<String, Object>();
 		map.put("methodName", "getChildrenShopList");
 		map.put("paramQty", "1");
 		map.put("paramType_0", String.class.getName());
 		map.put("paramVal_0", shopId);
 		
 		Object obj = sendPost(baseUrl + path, map);
 		if(obj != null && obj instanceof LinkedList)
 		{
 			return (LinkedList<ShopInfo>) obj;
 		}
 		
 		return null;
    }
    
    public void initShopData(String shopId) throws Exception
    {

  	   HashMap<String, Object> map = new HashMap<String, Object>();
  		map.put("methodName", "initShopData");
  		map.put("paramQty", "2");
  		
  		map.put("paramType_0", Context.class.getName());
  		map.put("paramVal_0", ContextUtil.getSimpleContext());
  		
  		map.put("paramType_1", String.class.getName());
  		map.put("paramVal_1", shopId);
  		
  		sendPost(baseUrl + path, map);
    }
}
