/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月9日 下午12:47:28 
  */
package bean.bill;

import java.util.HashMap;

import bean.IHttpHead;
import common.db.Context;
import common.util.ContextUtil;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月9日 下午12:47:28 
  */
public abstract class IHttpBill extends IHttpHead{
	
	public IHttpBill(String path) {
		super(path);
	}
	
	 /**
     * @功能描述 获取新编码
     * @作者 黎嘉杰 
     * @日期 2016年9月11日 上午1:07:48 
     * @参数 
     * @返回
     */
   public String getNewNumber() throws Exception
   {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getNewNumber");
		map.put("paramQty", "1");
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof String)
		{
			return (String) obj;
		}
		
		return null;
   }

	/**
	  * @功能描述 无数据库连接的审核方法
	  * @作者 黎嘉杰 
	  * @日期 2016年8月27日 下午11:38:31 
	  * @参数 
	  * @返回
	  */
	public String setAuditTrue(BillInfo info) throws Exception
	{
		verifyToAuditTrue(info);
		
		HashMap<String, Object> map = new HashMap<String, Object>(); 
		map.put("methodName", "setAuditTrue");
		map.put("paramQty", "2");
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", this.getInfoClass().getName());
		map.put("paramVal_1", info);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof String)
		{
			return (String) obj;
		}
		
		return null;
	
	}
	

	
	/**
	  * @功能描述 无数据库连接的反审核方法
	  * @作者 黎嘉杰 
	  * @日期 2016年8月27日 下午11:39:39 
	  * @参数 
	  * @返回
	  */
	public String setAuditFalse(BillInfo info) throws Exception
	{
		verifyToAuditFalse(info);
		
		HashMap<String, Object> map = new HashMap<String, Object>(); 
		map.put("methodName", "setAuditFalse");
		map.put("paramQty", "2");
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", this.getInfoClass().getName());
		map.put("paramVal_1", info);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof String)
		{
			return (String) obj;
		}
		
		return null;
	
	
	}

	
	//----------------------------------以下为校验方法--------------------------------------------------
	/**
	 * @校验是否可以审核， 不可以就抛出异常， 异常信息里包含不能保存的原因
	 * @throws Exception
	 */
	protected void verifyToAuditTrue(BillInfo info) throws Exception
	{
		if(info.getIsAuditTrue())
			throw new Exception("当前单据已审核， 不能再次审核！");
	}
	
	/**
	 * @校验是否可以反审核， 不可以就抛出异常， 异常信息里包含不能保存的原因
	 * @throws Exception
	 */
	protected void verifyToAuditFalse(BillInfo info) throws Exception
	{
		if(!info.getIsAuditTrue())
			throw new Exception("当前单据未审核， 不能反审核！");
	}

	protected Class<?> getInfoClass() {
		return BillInfo.class;
	}
	
	
	
}
