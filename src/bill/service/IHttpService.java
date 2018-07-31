package bill.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.bill.BillInfo;
import bean.bill.IHttpBill;
import common.db.Context;
import common.util.ContextUtil;

public class IHttpService extends IHttpBill {
	protected static IHttpService instance = null;
	
    public IHttpService() {
		super("IHttpService");
	}

    public static IHttpService getInstance()
    {
    	if(instance == null)
    		instance = new IHttpService();
    	return instance;
    }

	protected void verifyToAuditTrue(BillInfo info) throws Exception {
		super.verifyToAuditTrue(info);
		
		
	}

	protected void verifyToAuditFalse(BillInfo info) throws Exception {
		super.verifyToAuditFalse(info);
	}


	public Map<String, List<Map<String, Object>>> getDailyDataList(HashMap<String, Object> pm) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getDailyDataList");
		map.put("paramQty", "2");

		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", HashMap.class.getName());
		map.put("paramVal_1", pm);

		Object obj = sendPost(baseUrl + path, map);
		if (obj != null && obj instanceof Map) {
			return (Map<String, List<Map<String, Object>>>) obj;
		}

		return null;
	
	}

	
	
	
	
}
