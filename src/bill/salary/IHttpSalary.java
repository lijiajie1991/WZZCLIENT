package bill.salary;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import bean.bill.IHttpBill;
import common.db.Context;
import common.util.ContextUtil;

public class IHttpSalary extends IHttpBill {
	protected static IHttpSalary instance = null;
	
    public IHttpSalary() {
		super("IHttpSalary");
	}

    public static IHttpSalary getInstance()
    {
    	if(instance == null)
    		instance = new IHttpSalary();
    	return instance;
    }
    
    public Date getThisSalaryDateFrom(String personId) throws Exception
    {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getThisSalaryDateFrom");
		map.put("paramQty", "2");
		
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());

		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", personId);

		Object obj = sendPost(baseUrl + path, map);
		if (obj != null && obj instanceof Date) {
			return (Date) obj;
		}
		return new Date();
    }
    
    public SalaryInfo getPersonSalary(String personId, String df, String dt) throws Exception
    {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getPersonSalary");
		map.put("paramQty", "4");
		
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());

		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", personId);

		map.put("paramType_2", String.class.getName());
		map.put("paramVal_2", df);
		
		map.put("paramType_3", String.class.getName());
		map.put("paramVal_3", dt);

		Object obj = sendPost(baseUrl + path, map);
		if (obj != null && obj instanceof SalaryInfo) {
			return (SalaryInfo) obj;
		}
		return null;
    
    }
    
    public LinkedList<SalaryInfo> getPersonSalaryList(String df, String dt) throws Exception
    {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getPersonSalaryList");
		map.put("paramQty", "3");

		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());

		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", df);
		
		map.put("paramType_2", String.class.getName());
		map.put("paramVal_2", dt);

		Object obj = sendPost(baseUrl + path, map);
		if (obj != null && obj instanceof LinkedList) {
			return (LinkedList<SalaryInfo>) obj;
		}
		return null;
    }
    
    public LinkedList<SalaryInfo> getPersonSalaryList() throws Exception
    {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getPersonSalaryList");
		map.put("paramQty", "1");

		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		Object obj = sendPost(baseUrl + path, map);
		if (obj != null && obj instanceof LinkedList) {
			return (LinkedList<SalaryInfo>) obj;
		}
		return null;
    }
    
    public void setAllAuditTrue(LinkedList<SalaryInfo> list) throws Exception
    {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "setAllAuditTrue");
		map.put("paramQty", "2");

		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", LinkedList.class.getName());
		map.put("paramVal_1", list);

		sendPost(baseUrl + path, map);
    }
}
