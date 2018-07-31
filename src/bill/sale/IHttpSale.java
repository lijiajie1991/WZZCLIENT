package bill.sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.bill.BillInfo;
import bean.bill.IHttpBill;
import common.db.Context;
import common.util.ContextUtil;
import exp.BizException;

public class IHttpSale extends IHttpBill {
	protected static IHttpSale instance = null;
	
    public IHttpSale() {
		super("IHttpSale");
	}

    public static IHttpSale getInstance()
    {
    	if(instance == null)
    		instance = new IHttpSale();
    	return instance;
    }

	protected void verifyToAuditTrue(BillInfo info) throws Exception {
		super.verifyToAuditTrue(info);
		
		
	}

	protected void verifyToAuditFalse(BillInfo info) throws Exception {
		super.verifyToAuditFalse(info);
		SaleInfo pInfo  = (SaleInfo) info;
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
