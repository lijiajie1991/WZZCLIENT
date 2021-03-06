package bean;

import java.util.HashMap;

import common.db.Context;
import common.util.ContextUtil;



/**
  * @说明 前台调用方法
  * @作者 黎嘉杰 
  * @日期 2016年9月11日 上午1:59:21 
  */
public abstract class IHttpSys extends IHttp{
	
	protected IHttpSys(String path) {
		super(path);
	}
	
	/**
     * @功能描述 获取新编码
     * @作者 黎嘉杰 
     * @日期 2016年9月11日 上午1:07:48 
     * @参数 
     * @返回
     */
   public String getNewNumber() throws Exception
   {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getNewNumber");
		map.put("paramQty", "1");
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof String)
		{
			return (String) obj;
		}
		
		return null;
   }
}
