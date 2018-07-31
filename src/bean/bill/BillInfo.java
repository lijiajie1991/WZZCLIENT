package bean.bill;

import java.util.Date;

import bean.HeadInfo;

/**
  * @说明 业务单据基类， 继承Info；有新增、保存、删除、审核、反审核操作， 用于保存业务性的数据（销售单、付款单等）
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午2:46:05 
  */
public abstract class BillInfo  extends HeadInfo{
	private static final long serialVersionUID = 002L;
	
	protected String number = null;
	protected Date bizdate = null;
	protected boolean isAuditTrue = false;
	
	public BillInfo()
	{
		super();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getBizdate() {
		return bizdate;
	}

	public void setBizdate(Date bizdate) {
		this.bizdate = bizdate;
	}

	public boolean getIsAuditTrue() {
		return isAuditTrue;
	}

	public void setIsAuditTrue(boolean isAuditTrue) {
		this.isAuditTrue = isAuditTrue;
	}
	
	public String toString()
	{
		return this.getNumber();
	}
}
