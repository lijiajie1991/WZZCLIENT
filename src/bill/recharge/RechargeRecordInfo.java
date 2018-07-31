/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.recharge;

import java.math.BigDecimal;

import bas.customer.CustomerInfo;
import bas.person.PersonInfo;
import bean.bill.BillInfo;
import common.db.BeanTableInfo;
import em.PayType;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class RechargeRecordInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected PersonInfo personInfo = null;
	protected CustomerInfo customerInfo = null;
	protected BigDecimal amt = BigDecimal.ZERO;
	protected BigDecimal giveAmt = BigDecimal.ZERO;
	protected BigDecimal factAmt = BigDecimal.ZERO;
	protected PayType pt = PayType.Cash;
	protected String remark = null;
	
	public RechargeRecordInfo() {
		super();
	}
	
	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getRemark() {
		return remark;
	}
	
	

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

	public BigDecimal getGiveAmt() {
		return giveAmt;
	}

	public void setGiveAmt(BigDecimal giveAmt) {
		this.giveAmt = giveAmt;
	}

	
	public BigDecimal getFactAmt() {
		return factAmt;
	}

	public void setFactAmt(BigDecimal factAmt) {
		this.factAmt = factAmt;
	}

	public PayType getPt() {
		return pt;
	}

	public void setPt(PayType pt) {
		this.pt = pt;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(RechargeRecordInfo.class.getName());
		bt.setTableName("bill_rechargerecord");
		bt.setPk("0023");
		return bt;
	}

}
