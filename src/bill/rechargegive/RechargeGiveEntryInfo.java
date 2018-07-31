/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bill.rechargegive;

import java.math.BigDecimal;

import bean.EntryInfo;
import common.db.BeanTableInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class RechargeGiveEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected BigDecimal amt = BigDecimal.ZERO;
	protected BigDecimal giveAmt = BigDecimal.ZERO;
	protected String remark = null;
	
	public RechargeGiveEntryInfo()
	{
		super();
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getGiveAmt() {
		return giveAmt;
	}

	public void setGiveAmt(BigDecimal giveAmt) {
		this.giveAmt = giveAmt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	/**
	  * @throws Exception 
	 * @功能描述
	  * @作者 黎嘉杰 
	  * @日期 2016年9月10日 下午12:56:18 
	  * @参数 
	  * @返回
	  */
	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(RechargeGiveEntryInfo.class.getName());
		bt.setTableName("bill_rechargegive_entry");
		bt.setPk("002401");
		return bt;
	}
}
