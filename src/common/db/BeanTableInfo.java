package common.db;

import java.util.HashMap;

import bean.Info;

/**
  * @说明 用来存放bean对象的数据表等信息 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午3:14:38 
  */
public class BeanTableInfo {
	protected String clsName = null;  //类名称
	protected String controllerClsName = null;  //类名称
	protected String tableName = null; //数据表名称
	protected String pk = null;		//bean识别码
	
	protected HashMap<String, BeanTableInfo> entryMap = null;
	
	public BeanTableInfo()
	{
		
	}
	
	public BeanTableInfo(String clsName, String controllerClsName, String tableName, String pk) {
		super();
		this.clsName = clsName;
		this.controllerClsName = controllerClsName;
		this.tableName = tableName;
		this.pk = pk;
	}
	
	public String getClsName() {
		return clsName;
	}
	public void setClsName(String clsName) {
		this.clsName = clsName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public HashMap<String, BeanTableInfo> getEntryMap() {
		return entryMap;
	}

	public void setEntryMap(HashMap<String, BeanTableInfo> entryMap) {
		this.entryMap = entryMap;
	}
	
	
	
	public String getControllerClsName() {
		return controllerClsName;
	}

	public void setControllerClsName(String controllerClsName) {
		this.controllerClsName = controllerClsName;
	}

	public void addEntryBt(String name, Class<?> cls) throws Exception
	{
		entryMap = entryMap != null ? entryMap : new HashMap<String, BeanTableInfo>();
		Info info = (Info) cls.newInstance();
		BeanTableInfo bt = info.getBT();
		entryMap.put(name, bt);
	}
	
	public BeanTableInfo getEntryBt(String name)
	{
		if(entryMap.containsKey(name) && entryMap.get(name) != null)
			return entryMap.get(name);
		
		return null;
	}

	public boolean isHasEntry()
	{
		return entryMap != null && !entryMap.isEmpty();
	}

	public String toString() {
		return "BeanTableInfo [clsName=" + clsName + ", controllerClsName=" + controllerClsName + ", tableName="
				+ tableName + ", pk=" + pk + ", entryMap=" + entryMap + "]";
	}
}
