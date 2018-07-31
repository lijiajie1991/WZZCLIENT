package cache;

import java.util.HashMap;
import java.util.LinkedList;

import bean.Info;

public class BaseData {
	protected static HashMap<String, LinkedList<Info>> data = new HashMap<String, LinkedList<Info>>(); 

	public static boolean isHasCache(Class<?> cls)
	{
		if(cls == null)
			return false;
		
		String key = cls.getName();
		return data.containsKey(key);
	}
	
	public static LinkedList<Info> getInfoList(Class<?> cls)
	{
		if(data.containsKey(cls.getName()))
			return data.get(cls.getName());
		
		return null;
	}
	
	public static void setInfoList(String key, LinkedList<Info> list)
	{
		if(list != null)
			data.put(key, list);
	}
	
	public static void loadCache(Class<?> cls)
	{
		String key = cls != null ? cls.getName() : null;
		BaseDataCacheLoader t = new BaseDataCacheLoader(key);
		t.start();
	}
	
	
	
}
