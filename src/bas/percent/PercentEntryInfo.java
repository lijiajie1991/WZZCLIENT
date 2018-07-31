/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bas.percent;

import java.math.BigDecimal;

import bas.job.JobInfo;
import bean.EntryInfo;
import common.db.BeanTableInfo;
import em.PercentType;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class PercentEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected JobInfo jobInfo = null;
	protected PercentType pt = PercentType.NONE;
	protected BigDecimal val = BigDecimal.ZERO;
	protected BigDecimal otVal = BigDecimal.ZERO;
	protected BigDecimal specVal = BigDecimal.ZERO;
	protected String remark = null;
	
	public PercentEntryInfo()
	{
		super();
	}

	

	public JobInfo getJobInfo() {
		return jobInfo;
	}



	public void setJobInfo(JobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}

	


	public PercentType getPt() {
		return pt;
	}



	public void setPt(PercentType pt) {
		this.pt = pt;
	}



	public BigDecimal getVal() {
		return val;
	}



	public void setVal(BigDecimal val) {
		this.val = val;
	}



	public BigDecimal getOtVal() {
		return otVal;
	}



	public void setOtVal(BigDecimal otVal) {
		this.otVal = otVal;
	}



	public BigDecimal getSpecVal() {
		return specVal;
	}



	public void setSpecVal(BigDecimal specVal) {
		this.specVal = specVal;
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
		bt.setClsName(PercentEntryInfo.class.getName());
		bt.setTableName("base_percent_entry");
		bt.setPk("000901");
		return bt;
	}
}
