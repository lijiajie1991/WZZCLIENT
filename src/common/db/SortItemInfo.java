/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月13日 下午7:47:39 
  */
package common.db;

import java.io.Serializable;

import em.SortType;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月13日 下午7:47:39 
  */
public class SortItemInfo  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected String keyStr = null;
	protected SortType st = SortType.ASC;
	
	public SortItemInfo() {
		super();
	}
	public SortItemInfo(String keyStr, SortType st) {
		super();
		this.keyStr = keyStr;
		this.st = st;
	}
	public String getKeyStr() {
		return keyStr;
	}
	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}
	public SortType getSt() {
		return st;
	}
	public void setSt(SortType st) {
		this.st = st;
	}
	
	public String toString()
	{
		StringBuffer str = new StringBuffer();
		str.append(keyStr.replace(".", ".f"));
		if(st == SortType.ASC)
			str.append(" asc");
		else if(st == SortType.DESC)
			str.append(" desc");
		
		return str.toString();
	}
}
