package bas.sys.userrole;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import bean.base.IHttpBase;

public class IHttpUserRole extends IHttpBase {
	protected static IHttpUserRole instance = null;
	
    public IHttpUserRole() {
		super("IHttpUserRole");
	}

    public static IHttpUserRole getInstance()
    {
    	if(instance == null)
    		instance = new IHttpUserRole();
    	return instance;
    }

	/**
      * @功能描述  获取指定角色的所有UI路径
      * @作者 黎嘉杰 
      * @日期 2016年9月11日 上午1:07:48 
      * @参数 
      * @返回
      */
    public HashSet<String> getRoleUIPermissionSet(String roleId) throws Exception
    {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getRoleUIPermissionSet");
		map.put("paramQty", "1");
		map.put("paramType_0", String.class.getName());
		map.put("paramVal_0", roleId);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof LinkedList)
		{
			return (HashSet<String>) obj;
		}
		
		return null;
    }
}
