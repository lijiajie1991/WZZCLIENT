/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.project;

import java.math.BigDecimal;

import bas.percent.PercentInfo;
import bas.project.group.ProjectGroupInfo;
import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 权限项
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class ProjectInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected ProjectGroupInfo ptInfo = null;		//类型
	protected BigDecimal price = BigDecimal.ZERO;
	protected PercentInfo pcInfo = null;
	protected boolean isEnable = true;
	protected String remark = null;
	
	
	public ProjectInfo() {
		super();
	}

	public ProjectGroupInfo getPtInfo() {
		return ptInfo;
	}

	public void setPtInfo(ProjectGroupInfo ptInfo) {
		this.ptInfo = ptInfo;
	}
	
	
	public PercentInfo getPcInfo() {
		return pcInfo;
	}

	public void setPcInfo(PercentInfo pcInfo) {
		this.pcInfo = pcInfo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(ProjectInfo.class.getName());
		bt.setTableName("base_project");
		bt.setPk("0008");
		return bt;
	}

}
