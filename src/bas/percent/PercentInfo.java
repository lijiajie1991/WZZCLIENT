/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.percent;

import java.util.LinkedList;

import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 提成比例
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class PercentInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected LinkedList<PercentEntryInfo> perList = null;
	
	public PercentInfo() {
		super();
		
		perList = new LinkedList<PercentEntryInfo>();
	}
	

	public LinkedList<PercentEntryInfo> getPerList() {
		return perList;
	}



	public void setPerList(LinkedList<PercentEntryInfo> perList) {
		this.perList = perList;
	}



	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(PercentInfo.class.getName());
		bt.setTableName("base_percent");
		bt.setPk("0009");
		bt.addEntryBt("perList", PercentEntryInfo.class);
		return bt;
	}

}
