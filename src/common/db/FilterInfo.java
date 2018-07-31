/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月13日 下午7:13:27 
  */
package common.db;

import java.io.Serializable;
import java.util.LinkedList;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月13日 下午7:13:27 
  */
public class FilterInfo  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected LinkedList<FilterInfo> filters = null;
	protected LinkedList<String> mkrs = null;
	
	protected LinkedList<FilterItemInfo> items = null;
	protected String mkr = "#0";
	
	public FilterInfo()
	{
		items = new LinkedList<FilterItemInfo>();
		filters = new LinkedList<FilterInfo>();
		mkrs = new LinkedList<String>();
	}
	
	public LinkedList<FilterItemInfo> getItems() {
		return items;
	}
	public void setItems(LinkedList<FilterItemInfo> items) {
		this.items = items;
	}
	public String getMkr() {
		return mkr;
	}
	public void setMkr(String mkr) {
		this.mkr = mkr;
	}
	
	public void addItem(FilterItemInfo item)
	{
		items.addLast(item);
	}
	
	public void mergeFilter(FilterInfo info, String merge)
	{
		this.filters.addLast(info);
		this.mkrs.addLast(merge);
	}
	
	public String toString()
	{
		StringBuffer filterStr = new StringBuffer();
		String str = this.mkr;
		int size = items.size();
		for(int i = 0; i < size; i++)
		{
			FilterItemInfo item = items.get(i);
			str = str.replace("#" + i, item.toString());
		}
		
		if(size > 0)
		{
			filterStr.append("(" + str.toString() + ")");
		}
		else 
		{
			filterStr.append(" (1 = 1) ");
		}
		
		size = filters.size();
		for(int i = 0; i < size; i++)
		{
			FilterInfo f = filters.get(i);
			String m = mkrs.get(i);
			String subStr = f.toString();
			if(subStr != null && !"".equals(subStr))
			{
				filterStr.append(m);
				filterStr.append("(" + subStr + ") ");
			}
		}
		
		return filterStr.toString();
	}
	
	public boolean isEmpty()
	{
		return this.items == null || this.items.isEmpty();
	}
}
