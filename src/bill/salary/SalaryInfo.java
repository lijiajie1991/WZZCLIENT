/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.salary;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;

import bas.person.PersonInfo;
import bean.bill.BillInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class SalaryInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected PersonInfo personInfo = null;
	protected Date dateFrom = null;
	protected Date dateTo = null;
	protected BigDecimal salary = BigDecimal.ZERO;
	protected BigDecimal bounty = BigDecimal.ZERO;
	protected BigDecimal percentAmt = BigDecimal.ZERO;
	protected BigDecimal punishAmt = BigDecimal.ZERO;
	protected BigDecimal otherAmt = BigDecimal.ZERO;
	protected BigDecimal amt = BigDecimal.ZERO;
	protected String remark = null;
	
	protected LinkedList<SalaryEntryInfo> entryList = null;
	
	public SalaryInfo() {
		super();
		
		entryList = new LinkedList<SalaryEntryInfo>();
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
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

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public BigDecimal getBounty() {
		return bounty;
	}

	public void setBounty(BigDecimal bounty) {
		this.bounty = bounty;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getOtherAmt() {
		return otherAmt;
	}

	public void setOtherAmt(BigDecimal otherAmt) {
		this.otherAmt = otherAmt;
	}

	public BigDecimal getPercentAmt() {
		return percentAmt;
	}

	public void setPercentAmt(BigDecimal percentAmt) {
		this.percentAmt = percentAmt;
	}

	public BigDecimal getPunishAmt() {
		return punishAmt;
	}

	public void setPunishAmt(BigDecimal punishAmt) {
		this.punishAmt = punishAmt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LinkedList<SalaryEntryInfo> getEntryList() {
		return entryList;
	}

	public void setEntryList(LinkedList<SalaryEntryInfo> entryList) {
		this.entryList = entryList;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(SalaryInfo.class.getName());
		bt.setTableName("bill_salary");
		bt.setPk("0026");
		bt.addEntryBt("entryList", SalaryEntryInfo.class);
		return bt;
	}

}
