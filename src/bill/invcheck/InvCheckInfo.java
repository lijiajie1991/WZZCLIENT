/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.invcheck;

import java.util.LinkedList;

import bean.bill.BillInfo;
import common.db.BeanTableInfo;

/**
  * @说明 盘点单
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class InvCheckInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected LinkedList<InvCheckEntryInfo> entryList = null;
	
	public InvCheckInfo() {
		super();
		
		entryList = new LinkedList<InvCheckEntryInfo>();
	}
	
	
	
	public LinkedList<InvCheckEntryInfo> getEntryList() {
		return entryList;
	}



	public void setEntryList(LinkedList<InvCheckEntryInfo> entryList) {
		this.entryList = entryList;
	}



	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(InvCheckInfo.class.getName());
		bt.setTableName("bill_invcheck");
		bt.setPk("0038");
		bt.addEntryBt("entryList", InvCheckEntryInfo.class);
		return bt;
	}

}
