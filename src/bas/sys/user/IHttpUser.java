package bas.sys.user;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import bas.sys.shop.ShopInfo;
import bean.base.IHttpBase;

public class IHttpUser extends IHttpBase {
	protected static IHttpUser instance = null;
	
    public IHttpUser() {
		super("IHttpUser");
	}
    
    public static IHttpUser getInstance()
    {
    	if(instance == null)
    		instance = new IHttpUser();
    	return instance;
    }

	/**
     * @功能描述  获取用户的所有UI路径
     * @作者 黎嘉杰 
     * @日期 2016年9月11日 上午1:07:48 
     * @参数 
     * @返回
     */
   public HashSet<String> getUserUIPermissionSet(String userId) throws Exception
   {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getUserUIPermissionSet");
		map.put("paramQty", "1");
		map.put("paramType_0", String.class.getName());
		map.put("paramVal_0", userId);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof HashSet)
		{
			return (HashSet<String>) obj;
		}
		
		return null;
   }
	
   public HashSet<String> getUserBtnPermission(String userId, String clsName) throws Exception
   {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getUserBtnPermission");
		map.put("paramQty", "2");
		map.put("paramType_0", String.class.getName());
		map.put("paramType_1", String.class.getName());
		
		map.put("paramVal_0", userId);
		map.put("paramVal_1", clsName);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof HashSet)
		{
			return (HashSet<String>) obj;
		}
		
		return null;
  
   }
   
   public LinkedList<ShopInfo> getUserShopList(String userId) throws Exception
   {
	   HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getUserShopList");
		map.put("paramQty", "1");
		map.put("paramType_0", String.class.getName());
		map.put("paramVal_0", userId);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof LinkedList)
		{
			return (LinkedList<ShopInfo>) obj;
		}
		
		return null;
		
   }
   
   public void setPassWord(String userId, String password) throws Exception
   {
	   HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "setPassWord");
		map.put("paramQty", "2");
		map.put("paramType_0", String.class.getName());
		map.put("paramType_1", String.class.getName());

		map.put("paramVal_0", userId);
		map.put("paramVal_1", password);
		
		sendPost(baseUrl + path, map);
   }
   
}
