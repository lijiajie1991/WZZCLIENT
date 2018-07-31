package report.base.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.bill.IHttpBill;
import common.db.Context;
import common.util.ContextUtil;

public class IHttpRpt extends IHttpBill {
	protected static IHttpRpt instance = null;
	
    public IHttpRpt(String path) {
		super(path);
	}

    /**
     * @功能描述 获取查询sql数据集
     * @作者 黎嘉杰 
     * @日期 2016年9月11日 上午1:07:48 
     * @参数 
     * @返回
     */
   public List<Map<String, Object>> getRptData(String sql) throws Exception
   {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getRptData");
		map.put("paramQty", "2");
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", sql);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof List)
		{
			return (List<Map<String, Object>>) obj;
		}
		
		return null;
   }
}
