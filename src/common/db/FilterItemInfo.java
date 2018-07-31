/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月13日 下午7:13:40 
  */
package common.db;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

import em.CompareType;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月13日 下午7:13:40 
  */
public class FilterItemInfo  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected String key = null;
	protected CompareType ct = null;
	protected Object val = null;
	
	public FilterItemInfo()
	{
		
	}
	
	public FilterItemInfo(String key, CompareType ct, Object val) {
		super();
		this.key = key;
		this.ct = ct;
		this.val = val;
	}



	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public CompareType getCt() {
		return ct;
	}
	public void setCt(CompareType ct) {
		this.ct = ct;
	}
	public Object getVal() {
		return val;
	}
	public void setVal(Object val) {
		this.val = val;
	}
	
	public String toString()
	{
		String cmpStr = null;
		if(ct == CompareType.EQUAL)
			cmpStr = " = ";
		else if(ct == CompareType.NOTEQUAL)
			cmpStr = " <> ";
		else if(ct == CompareType.LESS)
			cmpStr = " < ";
		else if(ct == CompareType.LESSANDEQUAL)
			cmpStr = " <= ";
		else if(ct == CompareType.GRATER)
			cmpStr = " > ";
		else if(ct == CompareType.GRATERANDEQUAL)
			cmpStr = " >= ";
		else if(ct == CompareType.INCLUDE)
			cmpStr = " in  ";
		else if(ct == CompareType.NOTINCLUDE)
			cmpStr = " not in ";
		else if(ct == CompareType.ISNULL)
			cmpStr = " is null ";
		else if(ct == CompareType.ISNOTNULL)
			cmpStr = " is not null ";
		else if(ct == CompareType.LEFTLIKE || ct == CompareType.RIGHTLIKE || ct == CompareType.LIKE )
			cmpStr = " like ";
		
		StringBuffer str = new StringBuffer();
		if(key.indexOf(".") == -1)
			key = "a." + key;
		str.append(key.replace(".", ".f"));
		str.append(cmpStr);
		
		if(ct == CompareType.INCLUDE || ct == CompareType.NOTINCLUDE)
		{
			if(val instanceof HashSet)
			{
				str.append("(");
				@SuppressWarnings("unchecked")
				HashSet<Object> hs = (HashSet<Object>) val;
				Iterator<Object> it = hs.iterator();
				while(it.hasNext())
				{
					Object obj = it.next();
					str.append("'" + obj + "',");
				}
				str = str.replace(str.length() - 1, str.length(), ")");
			}
		}
		else if(ct == CompareType.LEFTLIKE)
		{
			str.append("'" + val.toString() + "%'");
		}
		else if(ct == CompareType.RIGHTLIKE)
		{
			str.append("'%" + val.toString() + "'");
		}
		else if(ct == CompareType.LIKE)
		{
			str.append("'%" + val.toString() + "%'");
		}
		else if(ct != CompareType.ISNULL && ct != CompareType.ISNOTNULL)
		{
			str.append("'" + val.toString() + "'");
		}
		
		return str.toString();
	}
}
