/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.paylog;

import java.math.BigDecimal;
import java.util.Date;

import bean.bill.BillInfo;
import common.db.BeanTableInfo;
import em.PayType;

/**
  * @说明 支付日志
  * @作者 黎嘉杰 
  * @日期 2017年01月28日 下午3:32:55 
  */
public class PayLogInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected String subject = null;
	protected String payId = null;
	protected String macIp = null;
	protected String body = null;
	protected String attach = null;
	protected String tag = null;
	protected PayType pt = null;
	protected Date timeStart = null;
	protected Date timeExpire = null;
	protected BigDecimal amt = BigDecimal.ZERO;
	protected boolean isSuccess = false;
	protected String remark = null;
	protected String transactionId = null;
	
	public PayLogInfo() {
		super();
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public PayType getPt() {
		return pt;
	}

	public void setPt(PayType pt) {
		this.pt = pt;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getMacIp() {
		return macIp;
	}

	public void setMacIp(String macIp) {
		this.macIp = macIp;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(Date timeExpire) {
		this.timeExpire = timeExpire;
	}
	
	

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(PayLogInfo.class.getName());
		bt.setTableName("bill_paylog");
		bt.setPk("0043");
		return bt;
	}

}
