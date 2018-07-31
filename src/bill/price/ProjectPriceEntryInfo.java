/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bill.price;

import java.math.BigDecimal;

import bas.project.ProjectInfo;
import bean.EntryInfo;
import common.db.BeanTableInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class ProjectPriceEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected ProjectInfo projectInfo = null;
	protected BigDecimal price = BigDecimal.ZERO;
	protected boolean isFloat = false;
	protected String remark = null;
	
	public ProjectPriceEntryInfo()
	{
		super();
	}


	public ProjectInfo getProjectInfo() {
		return projectInfo;
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




	public boolean getIsFloat() {
		return isFloat;
	}




	public void setIsFloat(boolean isFloat) {
		this.isFloat = isFloat;
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
		bt.setClsName(ProjectPriceEntryInfo.class.getName());
		bt.setTableName("bill_price_projectentry");
		bt.setPk("001601");
		return bt;
	}
}
