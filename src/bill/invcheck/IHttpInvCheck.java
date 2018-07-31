package bill.invcheck;

import java.util.HashMap;
import java.util.LinkedList;

import bean.bill.IHttpBill;
import common.db.Context;
import common.util.ContextUtil;

public class IHttpInvCheck extends IHttpBill {
	protected static IHttpInvCheck instance = null;
	
    public IHttpInvCheck() {
		super("IHttpInvCheck");
	}

    public static IHttpInvCheck getInstance()
    {
    	if(instance == null)
    		instance = new IHttpInvCheck();
    	return instance;
    }
    
    /**
	 * @功能描述 获取指定产品单价
	 * @作者 黎嘉杰 
	 * @日期 2016年9月11日 上午1:07:48 
	 * @参数 
	 * @返回
	 */
public LinkedList<InvCheckEntryInfo> getInvCheckEntryList(String shopId, String dateStr) throws Exception
   {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getInvCheckEntryList");
		map.put("paramQty", "3");
		
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", shopId);
		
		map.put("paramType_2", String.class.getName());
		map.put("paramVal_2", dateStr);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof LinkedList)
		{
			return (LinkedList<InvCheckEntryInfo>) obj;
		}
		
		return null;
   }
}
