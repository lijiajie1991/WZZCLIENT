package bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cache.CacheData;
import common.db.Context;
import common.db.FilterInfo;
import common.db.SortInfo;
import common.util.ContextUtil;
import exp.BizException;
import exp.HttpException;


/**
  * @说明 前台调用方法
  * @作者 黎嘉杰 
  * @日期 2016年9月11日 上午1:59:21 
  */
public abstract class IHttp{
	//protected static final String baseUrl = "http://wuzuzu.duapp.com/";
	//protected static final String baseUrl = "http://10.30.82.61:8190/WZZ/";
	protected static final String baseUrl = "http://localhost:8190/WZZ/";
	//protected static final String baseUrl = "http://192.168.3.124:8080/WZZ/";
	protected String path = null;

	protected IHttp(String path) {
		super();
		this.path = path;
	}
	
	/**
	  * @功能描述
	  * @作者 黎嘉杰 
	  * @日期 2016年9月11日 上午1:56:59 
	  * @参数 
	  * @返回
	  */
	protected Object sendPost(String url, HashMap<String, Object> paramMap) throws Exception {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        
		try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new ObjectOutputStream(conn.getOutputStream());
            out.writeObject(paramMap);
            
            in = new ObjectInputStream(conn.getInputStream());
            Object obj = in.readObject();
            if(obj instanceof HttpException)
            {
            	throw (HttpException)obj;
            }
            else if(obj instanceof BizException)
            {
            	throw (BizException)obj;
            }
            return obj;
        } catch (Exception e) {
        	throw e;
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    } 

	// --------------------------------------------数据库查询相关的方法-----------------------------------------------------
	/**
	 * @功能描述 根据id初始化bean对象
	 * @param id
	 */
	public Info getInfo(String id) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getInfo");
		map.put("paramQty", "1");
		map.put("paramType_0", String.class.getName());
		map.put("paramVal_0", id);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof Info)
		{
			return (Info) obj;
		}
		
		return null;
	}

	public LinkedList<Info> getInfoList(String filter, String order) throws Exception 
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getInfoList");
		map.put("paramQty", "2");
		map.put("paramType_0", String.class.getName());
		map.put("paramType_1", String.class.getName());
		map.put("paramVal_0", filter);
		map.put("paramVal_1", order);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof LinkedList)
		{
			return (LinkedList<Info>) obj;
		}
		
		return null;
	
	}
	
	public LinkedList<Info> getInfoList(FilterInfo filter, SortInfo order) throws Exception 
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getInfoList");
		map.put("paramQty", "2");
		map.put("paramType_0", FilterInfo.class.getName());
		map.put("paramType_1", SortInfo.class.getName());
		map.put("paramVal_0", filter);
		map.put("paramVal_1", order);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof LinkedList)
		{
			return (LinkedList<Info>) obj;
		}
		
		return null;
	
	}
	
	public ResultSet getResultSet(FilterInfo filter, SortInfo order) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getResultSet");
		map.put("paramQty", "3");
		
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", FilterInfo.class.getName());
		map.put("paramVal_1", filter);
		
		map.put("paramType_2", SortInfo.class.getName());
		map.put("paramVal_2", order);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof ResultSet)
		{
			return (ResultSet) obj;
		}
		
		return null;
	}
	
	public List<Map<String, Object>> getMapListData(FilterInfo filter, SortInfo order) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getMapListData");
		map.put("paramQty", "3");
		
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", FilterInfo.class.getName());
		map.put("paramVal_1", filter);
		
		map.put("paramType_2", SortInfo.class.getName());
		map.put("paramVal_2", order);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof List)
		{
			return (List<Map<String, Object>>) obj;
		}
		
		return null;
	}
	
	public List<Map<String, Object>> getMapListData(String filter, String order) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("methodName", "getMapListData");
		map.put("paramQty", "3");
		
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", filter);
		
		map.put("paramType_2", String.class.getName());
		map.put("paramVal_2", order);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof List)
		{
			return (List<Map<String, Object>>) obj;
		}
		
		return null;
	}
	
	// --------------------------------------------数据库保存删除相关的方法-----------------------------------------------------
	/**
	 * @功能描述 保存方法
	 * @作者 黎嘉杰
	 * @throws Exception
	 */
	public String save(Info info) throws Exception {
		verifyToSave(info);
		
		HashMap<String, Object> map = new HashMap<String, Object>(); 
		map.put("methodName", "save");
		map.put("paramQty", "2");
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", Info.class.getName());
		map.put("paramVal_1", info);
		
		Object obj = sendPost(baseUrl + path, map);
		if(obj != null && obj instanceof String)
		{
			CacheData.loadCache(getInfoClass());
			return (String) obj;
		}
		
		return null;
	}

	/**
	 * @功能描述 删除方法
	 * @作者 黎嘉杰
	 * @日期 2016-08-26
	 * @throws Exception
	 */
	public void delete(String id) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>(); 
		map.put("methodName", "delete");
		map.put("paramQty", "2");
		
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", String.class.getName());
		map.put("paramVal_1", id);
		
		sendPost(baseUrl + path, map);
		
		CacheData.loadCache(getInfoClass());
	}
	
	/**
	 * @功能描述 删除方法
	 * @作者 黎嘉杰
	 * @日期 2016-08-26
	 * @throws Exception
	 */
	public void delete(FilterInfo filter) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>(); 
		map.put("methodName", "delete");
		map.put("paramQty", "2");
		
		map.put("paramType_0", Context.class.getName());
		map.put("paramVal_0", ContextUtil.getSimpleContext());
		
		map.put("paramType_1", FilterInfo.class.getName());
		map.put("paramVal_1", filter);
		
		sendPost(baseUrl + path, map);
		
		CacheData.loadCache(getInfoClass());
	}

	// --------------------------------------------以下为校验方法-----------------------------------------------------

	/**
	 * @校验是否可以保存， 不可以就抛出异常， 异常信息里包含不能保存的原因
	 * 
	 * @throws Exception
	 */
	protected void verifyToSave(Info info) throws Exception {

	}

	protected Class<?> getInfoClass()
	{
		return Info.class;
	}
}
