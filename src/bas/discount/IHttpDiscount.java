package bas.discount;

import java.util.HashMap;

import bean.base.IHttpBase;
import bill.price.MaterialPriceEntryInfo;
import common.db.Context;
import common.util.ContextUtil;

public class IHttpDiscount extends IHttpBase {
	protected static IHttpDiscount instance = null;
	
    public IHttpDiscount() {
		super("IHttpDiscount");
	}

    public static IHttpDiscount getInstance()
    {
    	if(instance == null)
    		instance = new IHttpDiscount();
    	return instance;
    }
    
    
    /**
	 * @功能描述 获取指定产品单价
	 * @作者 黎嘉杰 
	 * @日期 2016年9月11日 上午1:07:48 
	 * @参数 
	 * @返回
	 */
   public MaterialDiscountEntryInfo getMaterialDiscount(String cusId, String matId) throws Exception
   {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getMaterialDiscount");
		map.put("paramQty", "3");
		
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", cusId);
		
		map.put("paramType_2", String.class.getName());
		map.put("paramVal_2", matId);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof MaterialPriceEntryInfo)
		{
			return (MaterialDiscountEntryInfo) obj;
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
	public ProjectDiscountEntryInfo getProjectDiscount(String cusId, String proId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getProjectDiscount");
		map.put("paramQty", "3");

		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());

		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", cusId);

		map.put("paramType_2", String.class.getName());
		map.put("paramVal_2", proId);

		Object obj = sendPost(baseUrl + path, map);
		if (obj != null && obj instanceof ProjectDiscountEntryInfo) {
			return (ProjectDiscountEntryInfo) obj;
		}

		return null;
	}
}
