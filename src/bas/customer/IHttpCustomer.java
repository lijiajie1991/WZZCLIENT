package bas.customer;

import java.util.HashMap;
import java.util.LinkedList;

import bean.Info;
import bean.base.IHttpBase;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.ShopType;

public class IHttpCustomer extends IHttpBase {
	protected static IHttpCustomer instance = null;
	
    public IHttpCustomer() {
		super("IHttpCustomer");
	}

    public static IHttpCustomer getInstance()
    {
    	if(instance == null)
    		instance = new IHttpCustomer();
    	return instance;
    }
    
    protected Class<?> getInfoClass()
	{
		return CustomerInfo.class;
	}
    
    public CustomerInfo getCustomerInfo(String cardNumber) throws Exception
    {
    	HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getCustomerInfo");
		map.put("paramQty", "1");
		map.put("paramType_0", String.class.getName());
		map.put("paramVal_0", cardNumber);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof CustomerInfo)
		{
			return (CustomerInfo) obj;
		}
		
		return null;
    }
    
    public LinkedList<Info> getInfoList(FilterInfo filter, SortInfo order) throws Exception {
		
		FilterInfo defFilter = new FilterInfo();
	
		if(ContextUtil.getShopInfo().getSt() == ShopType.STORE)
		{
			defFilter .addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
			defFilter .addItem(new FilterItemInfo("a.adminshopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
			defFilter.setMkr("#0 or #1");
		}
		else
		{
			defFilter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
			defFilter.addItem(new FilterItemInfo("a.adminshopId", CompareType.EQUAL, ContextUtil.getShopId()));
			defFilter.setMkr("#0 or #1");
		}
		
		if(filter != null)
			defFilter.mergeFilter(filter, "and");
		

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getInfoList");
		map.put("paramQty", "2");
		map.put("paramType_0", FilterInfo.class.getName());
		map.put("paramType_1", SortInfo.class.getName());
		map.put("paramVal_0", defFilter);
		map.put("paramVal_1", order);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof LinkedList)
		{
			return (LinkedList<Info>) obj;
		}
		
		return null;
	
	
	}
}
