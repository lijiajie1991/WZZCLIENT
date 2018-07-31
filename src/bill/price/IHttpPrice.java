package bill.price;

import java.util.Date;
import java.util.HashMap;

import bean.bill.BillInfo;
import bean.bill.IHttpBill;
import common.db.Context;
import common.util.ContextUtil;
import common.util.DateTimeUtil;
import exp.BizException;

public class IHttpPrice extends IHttpBill {
	protected static IHttpPrice instance = null;
	
    public IHttpPrice() {
		super("IHttpPrice");
	}

    public static IHttpPrice getInstance()
    {
    	if(instance == null)
    		instance = new IHttpPrice();
    	return instance;
    }

	protected void verifyToAuditTrue(BillInfo info) throws Exception {
		super.verifyToAuditTrue(info);
		
		PriceInfo pInfo = (PriceInfo) info;
		Date df = pInfo.getDateFrom();
		Date dt = pInfo.getDateTo();
		String dfStr = DateTimeUtil.getDateStr(df);
		String dtStr = DateTimeUtil.getDateStr(dt);
		String dStr = DateTimeUtil.getDateStr(new Date());
		if(dfStr.compareTo(dStr) <= 0)
			throw new BizException("当前开始日期小于或等于当天日期， 不能审核");
		
		if(!"2030-01-01".equals(dtStr))
			throw new BizException("存在更新的单价设置， 不能审核");
	}

	protected void verifyToAuditFalse(BillInfo info) throws Exception {
		super.verifyToAuditFalse(info);
		
		PriceInfo pInfo = (PriceInfo) info;
		Date df = pInfo.getDateFrom();
		Date dt = pInfo.getDateTo();
		String dfStr = DateTimeUtil.getDateStr(df);
		String dtStr = DateTimeUtil.getDateStr(dt);
		String dStr = DateTimeUtil.getDateStr(new Date());
		if(dfStr.compareTo(dStr) <= 0)
			throw new BizException("当前开始日期小于或等于当天日期， 不能反审核");
		
		if(!"2030-01-01".equals(dtStr))
			throw new BizException("存在更新的单价设置， 不能反审核");
	}
    
	/**
	 * @功能描述 获取指定产品单价
	 * @作者 黎嘉杰 
	 * @日期 2016年9月11日 上午1:07:48 
	 * @参数 
	 * @返回
	 */
   public MaterialPriceEntryInfo getMaterialPrice(String mId, String dateStr) throws Exception
   {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getMaterialPrice");
		map.put("paramQty", "3");
		
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", mId);
		
		map.put("paramType_2", String.class.getName());
		map.put("paramVal_2", dateStr);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof MaterialPriceEntryInfo)
		{
			return (MaterialPriceEntryInfo) obj;
		}
		
		return null;
   }
   
	/**
	 * @功能描述 获取指定产品单价
	 * @作者 黎嘉杰
	 * @日期 2016年9月11日 上午1:07:48
	 * @参数
	 * @返回
	 */
	public ProjectPriceEntryInfo getProjectPrice(String proId, String dateStr) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getProjectPrice");
		map.put("paramQty", "3");

		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());

		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", proId);

		map.put("paramType_2", String.class.getName());
		map.put("paramVal_2", dateStr);

		Object obj = sendPost(baseUrl + path, map);
		if (obj != null && obj instanceof ProjectPriceEntryInfo) {
			return (ProjectPriceEntryInfo) obj;
		}

		return null;
	}
	
	
	/**
	 * @功能描述 获取指定产品单价
	 * @作者 黎嘉杰 
	 * @日期 2016年9月11日 上午1:07:48 
	 * @参数 
	 * @返回
	 */
   public MaterialPriceEntryInfo getMaterialPrice(String cusId, String mId, String dateStr) throws Exception
   {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getMaterialPrice");
		map.put("paramQty", "4");
		
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", cusId);
		
		map.put("paramType_2", String.class.getName());
		map.put("paramVal_2", mId);
		
		map.put("paramType_3", String.class.getName());
		map.put("paramVal_3", dateStr);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof MaterialPriceEntryInfo)
		{
			return (MaterialPriceEntryInfo) obj;
		}
		
		return null;
   }
   
	/**
	 * @功能描述 获取指定产品单价
	 * @作者 黎嘉杰
	 * @日期 2016年9月11日 上午1:07:48
	 * @参数
	 * @返回
	 */
	public ProjectPriceEntryInfo getProjectPrice(String cusId, String proId, String dateStr) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getProjectPrice");
		map.put("paramQty", "4");

		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());

		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", cusId);
		
		map.put("paramType_2", String.class.getName());
		map.put("paramVal_2", proId);

		map.put("paramType_3", String.class.getName());
		map.put("paramVal_3", dateStr);

		Object obj = sendPost(baseUrl + path, map);
		if (obj != null && obj instanceof ProjectPriceEntryInfo) {
			return (ProjectPriceEntryInfo) obj;
		}

		return null;
	}
}