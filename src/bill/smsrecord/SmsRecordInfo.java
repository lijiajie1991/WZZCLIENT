/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.smsrecord;

import bean.bill.BillInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class SmsRecordInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected String ctx = null;
	protected String phone = null;
	protected String code = null;
	protected boolean isSuccess = false;
	
	public SmsRecordInfo() {
		super();
	}

	public String getCtx() {
		return ctx;
	}



	public void setCtx(String ctx) {
		this.ctx = ctx;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(SmsRecordInfo.class.getName());
		bt.setTableName("bill_smsrecord");
		bt.setPk("0031");
		return bt;
	}

}
