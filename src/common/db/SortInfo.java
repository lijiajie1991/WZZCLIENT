/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月13日 下午7:51:10 
  */
package common.db;

import java.io.Serializable;
import java.util.LinkedList;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月13日 下午7:51:10 
  */
public class SortInfo  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected LinkedList<SortItemInfo> items = null;
	
	public SortInfo()
	{
		items = new LinkedList<SortItemInfo>();
	}
	
	
	
	public LinkedList<SortItemInfo> getItems() {
		return items;
	}



	public void setItems(LinkedList<SortItemInfo> items) {
		this.items = items;
	}

	public void addItem(SortItemInfo item)
	{
		this.items.addLast(item);
	}

	public String toString()
	{
		StringBuffer str = new StringBuffer();
		int size = items.size();
		for(int i = 0; i < size; i++)
		{
			SortItemInfo item = items.get(i);
			str.append(item.toString());
			str.append(",");
			
		}
		if(str.length() > 0)
			str = str.replace(str.length() - 1, str.length(), "\n");
		return str.toString();
	}
	
	public boolean isEmpty()
	{
		return this.items == null || this.items.isEmpty();
	}

}
