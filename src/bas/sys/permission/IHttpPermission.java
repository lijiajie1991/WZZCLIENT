package bas.sys.permission;

import java.util.HashMap;
import java.util.LinkedList;

import bean.IHttpSys;
import bean.Info;
import common.db.Context;
import common.util.ContextUtil;

public class IHttpPermission extends IHttpSys {
	protected static IHttpPermission instance = null;
	
    public IHttpPermission() {
        super("IHttpPermission");
    }
    
    public static IHttpPermission getInstance()
    {
    	if(instance == null)
    		instance = new IHttpPermission();
    	return instance;
    }
    
    /**
     * @功能描述 获取新编码
     * @作者 黎嘉杰 
     * @日期 2016年9月11日 上午1:07:48 
     * @参数 
     * @返回
     */
   public LinkedList<Info> getPermissionList() throws Exception
   {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getPermissionList");
		map.put("paramQty", "1");
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof LinkedList)
		{
			return (LinkedList<Info>) obj;
		}
		
		return null;
   }
    
}
