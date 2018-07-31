/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.service;

import java.math.BigDecimal;

import bas.customer.CustomerInfo;
import bas.person.PersonInfo;
import bas.project.ProjectInfo;
import bean.bill.BillInfo;
import common.db.BeanTableInfo;
import em.PayType;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class ServiceInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected PersonInfo personInfo = null;
	protected CustomerInfo customerInfo = null;
	protected ProjectInfo projectInfo = null;
	protected BigDecimal qty = BigDecimal.ZERO;
	protected BigDecimal price = BigDecimal.ZERO;
	protected BigDecimal amt = BigDecimal.ZERO;
	protected BigDecimal factamt = BigDecimal.ZERO;
	protected String remark = null;
	protected PayType pt = PayType.Cash;
	protected boolean isBalance = false;
	protected boolean isOt = false;
	protected boolean isSpec = false;
	
	public ServiceInfo() {
		super();
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

	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}
	
	public boolean getIsBalance() {
		return isBalance;
	}

	public void setIsBalance(boolean isBalance) {
		this.isBalance = isBalance;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	
	

	public BigDecimal getFactamt() {
		return factamt;
	}

	public void setFactamt(BigDecimal factamt) {
		this.factamt = factamt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public PayType getPt() {
		return pt;
	}

	public void setPt(PayType pt) {
		this.pt = pt;
	}

	public boolean getIsOt() {
		return isOt;
	}

	public void setIsOt(boolean isOt) {
		this.isOt = isOt;
	}

	public boolean getIsSpec() {
		return isSpec;
	}

	public void setIsSpec(boolean isSpec) {
		this.isSpec = isSpec;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(ServiceInfo.class.getName());
		bt.setTableName("bill_service");
		bt.setPk("0021");
		return bt;
	}

}
