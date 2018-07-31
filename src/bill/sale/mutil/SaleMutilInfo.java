/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.sale.mutil;

import java.math.BigDecimal;
import java.util.LinkedList;

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
public class SaleMutilInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected PersonInfo personInfo = null;
	protected CustomerInfo customerInfo = null;
	protected String remark = null;
	protected PayType pt = PayType.Cash;
	protected BigDecimal amt = null;
	protected BigDecimal factAmt = null;
	protected boolean isBalance = false;
	
	protected LinkedList<SaleMutilEntryInfo> entryList = null;
	
	public SaleMutilInfo() {
		super();
		
		entryList = new LinkedList<SaleMutilEntryInfo>();
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






	public PayType getPt() {
		return pt;
	}



	public void setPt(PayType pt) {
		this.pt = pt;
	}

	public boolean getIsBalance() {
		return isBalance;
	}

	public void setIsBalance(boolean isBalance) {
		this.isBalance = isBalance;
	}
	
	
	
	public BigDecimal getAmt() {
		return amt;
	}



	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}



	public LinkedList<SaleMutilEntryInfo> getEntryList() {
		return entryList;
	}



	public void setEntryList(LinkedList<SaleMutilEntryInfo> entryList) {
		this.entryList = entryList;
	}



	public BigDecimal getFactAmt() {
		return factAmt;
	}



	public void setFactAmt(BigDecimal factAmt) {
		this.factAmt = factAmt;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(SaleMutilInfo.class.getName());
		bt.setTableName("bill_salemutil");
		bt.setPk("002001");
		bt.addEntryBt("entryList", SaleMutilEntryInfo.class);
		return bt;
	}

}
