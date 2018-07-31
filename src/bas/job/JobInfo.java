/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.job;

import java.math.BigDecimal;

import bean.base.BaseInfo;
import common.db.BeanTableInfo;
import em.ProfessionType;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class JobInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	protected ProfessionType pft = ProfessionType.EMPTY;		//类型
	protected BigDecimal salary = BigDecimal.ZERO;
	protected BigDecimal bounty = BigDecimal.ZERO;
	
	public JobInfo() {
		super();
	}
	
	public ProfessionType getPft() {
		return pft;
	}

	public void setPft(ProfessionType pft) {
		this.pft = pft;
	}



	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public BigDecimal getBounty() {
		return bounty;
	}

	public void setBounty(BigDecimal bounty) {
		this.bounty = bounty;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(JobInfo.class.getName());
		bt.setTableName("base_job");
		bt.setPk("0006");
		return bt;
	}

}
