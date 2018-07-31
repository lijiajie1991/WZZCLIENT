/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.attendrecord;

import java.math.BigDecimal;

import bas.person.PersonInfo;
import bean.bill.BillInfo;
import common.db.BeanTableInfo;
import em.AttendanceDaysType;
import em.AttendanceType;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class AttendRecordInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected PersonInfo personInfo = null;
	protected AttendanceType at = AttendanceType.Leave;
	protected AttendanceDaysType adt = AttendanceDaysType.FullDay;
	protected BigDecimal qty = BigDecimal.ZERO;
	protected boolean isBalance = false;
	protected String remark = null;
	
	public AttendRecordInfo() {
		super();
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	
	

	public AttendanceType getAt() {
		return at;
	}

	public void setAt(AttendanceType at) {
		this.at = at;
	}

	public AttendanceDaysType getAdt() {
		return adt;
	}

	public void setAdt(AttendanceDaysType adt) {
		this.adt = adt;
	}
	
	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
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
		bt.setClsName(AttendRecordInfo.class.getName());
		bt.setTableName("bill_attendrecord");
		bt.setPk("0025");
		return bt;
	}

}
