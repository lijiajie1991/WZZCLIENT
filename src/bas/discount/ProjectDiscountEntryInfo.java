/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bas.discount;

import java.math.BigDecimal;

import bas.project.ProjectInfo;
import bean.EntryInfo;
import common.db.BeanTableInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class ProjectDiscountEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected ProjectInfo projectInfo = null;
	protected BigDecimal discount = BigDecimal.ZERO;
	protected String remark = null;
	
	public ProjectDiscountEntryInfo()
	{
		super();
	}


	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}




	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}







	public BigDecimal getDiscount() {
		return discount;
	}


	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
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
		bt.setClsName(ProjectDiscountEntryInfo.class.getName());
		bt.setTableName("base_discount_projectentry");
		bt.setPk("002201");
		return bt;
	}
}
