package report.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class RptFilterInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected String key = null;
	protected String filter = null;
	protected HashMap<String, Object> valMap = null;
	
	public RptFilterInfo(String key, String filter)
	{
		this.key = key;
		this.filter = filter;
		valMap = new HashMap<String, Object>();
	}
	
	protected void analyze()
	{
		String[] items = filter.split(" ");
		for(String key : items)
		{
			key = key.trim();
			if(key.startsWith(":"))
			{
				key = key.substring(1);
				valMap.put(key, "");
			}
		}
	}
	
	public void setFilterVal(String key, Object val)
	{
		valMap.put(key, val);
	}
	
	public String toString()
	{
		String temp = this.filter;
		Iterator<Entry<String, Object>> it = valMap.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<String, Object> entry = it.next();
			String filterKey = entry.getKey();
			Object filterVal = entry.getValue();
			
			filterVal = filterVal != null ? filterVal : "";
			
			temp = temp.replaceAll(":" + filterKey, filterVal.toString());
		}
		
		return temp.toString();
	}
}
