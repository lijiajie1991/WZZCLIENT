/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.advancerecord;

import java.math.BigDecimal;

import bas.person.PersonInfo;
import bean.bill.BillInfo;
import common.db.BeanTableInfo;

/**
  * @说明 预支
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class AdvanceRecordInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected PersonInfo personInfo = null;
	protected BigDecimal amt = BigDecimal.ZERO;
	protected boolean isBalance = false;
	protected String remark = null;
	
	public AdvanceRecordInfo() {
		super();
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	
	
	
	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public boolean getIsBalance() {
		return isBalance;
	}

	public void setIsBalance(boolean isBalance) {
		this.isBalance = isBalance;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(AdvanceRecordInfo.class.getName());
		bt.setTableName("bill_advancerecord");
		bt.setPk("0041");
		return bt;
	}

}
