package bill.rechargegive;

import java.math.BigDecimal;
import java.util.HashMap;

import bean.bill.IHttpBill;
import common.db.Context;
import common.util.ContextUtil;

public class IHttpRechargeGive extends IHttpBill {
	protected static IHttpRechargeGive instance = null;
	
    public IHttpRechargeGive() {
		super("IHttpRechargeGive");
	}

    public static IHttpRechargeGive getInstance()
    {
    	if(instance == null)
    		instance = new IHttpRechargeGive();
    	return instance;
    }

    public BigDecimal getGiveAmt(BigDecimal amt, String dateStr) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getGiveAmt");
		map.put("paramQty", "3");

		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());

		map.put("paramType_1", BigDecimal.class.getName());
		map.put("paramVal_1", amt);
		
		map.put("paramType_2", String.class.getName());
		map.put("paramVal_2", dateStr);

		Object obj = sendPost(baseUrl + path, map);
		if (obj != null && obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}

		return null;
	
    }
}
