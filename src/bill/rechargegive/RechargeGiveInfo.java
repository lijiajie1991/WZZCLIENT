/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.rechargegive;

import java.util.Date;
import java.util.LinkedList;

import bean.bill.BillInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class RechargeGiveInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected String name = null;
	protected Date dateFrom = null;
	protected Date dateTo = null;
	
	protected LinkedList<RechargeGiveEntryInfo> giveList = null;
	
	public RechargeGiveInfo() {
		super();
		
		giveList = new LinkedList<RechargeGiveEntryInfo>();
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
	public LinkedList<RechargeGiveEntryInfo> getGiveList() {
		return giveList;
	}

	public void setGiveList(LinkedList<RechargeGiveEntryInfo> giveList) {
		this.giveList = giveList;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(RechargeGiveInfo.class.getName());
		bt.setTableName("bill_rechargegive");
		bt.setPk("0024");
		bt.addEntryBt("giveList", RechargeGiveEntryInfo.class);
		return bt;
	}

}
