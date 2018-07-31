/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bas.attendance;

import java.math.BigDecimal;

import bas.job.JobInfo;
import bean.EntryInfo;
import common.db.BeanTableInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class AttendanceEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected JobInfo jobInfo = null;
	protected BigDecimal amt = BigDecimal.ZERO;
	protected String remark = null;
	
	public AttendanceEntryInfo()
	{
		super();
	}

	

	public JobInfo getJobInfo() {
		return jobInfo;
	}



	public void setJobInfo(JobInfo jobInfo) {
		this.jobInfo = jobInfo;
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



	public void setRemark(String remark) {
		this.remark = remark;
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
		bt.setClsName(AttendanceEntryInfo.class.getName());
		bt.setTableName("base_attendance_entry");
		bt.setPk("001501");
		return bt;
	}
}
