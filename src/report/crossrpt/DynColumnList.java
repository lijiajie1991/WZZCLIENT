package report.crossrpt;

import java.util.HashMap;
import java.util.LinkedList;

public class DynColumnList {
	protected LinkedList<String> keyList = null;
	protected HashMap<String, DynColumnInfo> keyMap = null;
	
	public DynColumnList()
	{
		keyList = new LinkedList<String>();
		keyMap = new HashMap<String, DynColumnInfo>();
	}
	
	public DynColumnInfo getDynColumnInfo(String key)
	{
		if(keyMap.containsKey(key))
			return keyMap.get(key);
		
		return null;
	}
	
	public void addDynColumnInfo(String key, DynColumnInfo cInfo)
	{
		keyList.addLast(key);
		keyMap.put(key, cInfo);
	}
	
	
	
	
	
	
	
	
	

	public LinkedList<String> getKeyList() {
		return keyList;
	}

	public void setKeyList(LinkedList<String> keyList) {
		this.keyList = keyList;
	}

	public HashMap<String, DynColumnInfo> getKeyMap() {
		return keyMap;
	}

	public void setKeyMap(HashMap<String, DynColumnInfo> keyMap) {
		this.keyMap = keyMap;
	}
	
	
}
