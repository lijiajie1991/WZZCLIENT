package bill.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.bill.BillInfo;
import bean.bill.IHttpBill;
import common.db.Context;
import common.util.ContextUtil;
import exp.BizException;

public class IHttpPurchase extends IHttpBill {
	protected static IHttpPurchase instance = null;
	
    public IHttpPurchase() {
		super("IHttpPurchase");
	}

    public static IHttpPurchase getInstance()
    {
    	if(instance == null)
    		instance = new IHttpPurchase();
    	return instance;
    }

	protected void verifyToAuditFalse(BillInfo info) throws Exception {
		super.verifyToAuditFalse(info);
		PurchaseInfo pInfo  = (PurchaseInfo) info;
		if(pInfo.getSourceBillId() != null && !"".equals(pInfo.getSourceBillId()))
		{
			throw new BizException("当前单据由其他单据生成， 不能改单");
		}
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
