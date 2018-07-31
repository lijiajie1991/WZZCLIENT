package common.util;

import java.util.HashMap;
import java.util.HashSet;

import bas.sys.user.IHttpUser;

public class PermissionUtil {
	protected static HashMap<String, HashSet<String>> data = null;
	
	public static HashSet<String> getBtnPermission(String clsName) throws Exception
	{
		if(data == null)
			data = new HashMap<String, HashSet<String>>();
		
		if(data.containsKey(clsName) && data.get(clsName) != null)
			return data.get(clsName);
		
		HashSet<String> permissionSet = IHttpUser.getInstance().getUserBtnPermission(ContextUtil.getUserId(), clsName);
		
		if(permissionSet != null)
			data.put(clsName, permissionSet);
		
		return permissionSet != null ? permissionSet : new HashSet<String>();
	}
	
	public static boolean isSuperUser()
	{
		return "admin".equals(ContextUtil.getUserInfo().getNumber());
	}
}
