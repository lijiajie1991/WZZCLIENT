/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bill.salary;

import java.math.BigDecimal;

import bean.EntryInfo;
import common.db.BeanTableInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class SalaryEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected String name = null;
	protected String remark = null;
	protected BigDecimal totalAmt = BigDecimal.ZERO;
	protected BigDecimal amt = BigDecimal.ZERO;
	
	public SalaryEntryInfo()
	{
		super();
	}
	
	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public BigDecimal getTotalAmt() {
		return totalAmt;
	}



	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}



	public BigDecimal getAmt() {
		return amt;
	}



	public void setAmt(BigDecimal amt) {
		this.amt = amt;
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
		bt.setClsName(SalaryEntryInfo.class.getName());
		bt.setTableName("bill_salary_entry");
		bt.setPk("002501");
		return bt;
	}
}
