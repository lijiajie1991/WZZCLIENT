/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.price;

import java.util.Date;
import java.util.LinkedList;

import bean.bill.BillInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class PriceInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected Date dateFrom = null;
	protected Date dateTo = null;
	
	protected LinkedList<ProjectPriceEntryInfo> projectList = null;
	protected LinkedList<MaterialPriceEntryInfo> materialList = null;
	
	public PriceInfo() {
		super();
		
		projectList = new LinkedList<ProjectPriceEntryInfo>();
		materialList = new LinkedList<MaterialPriceEntryInfo>();
	}
	
	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}



	public Date getDateTo() {
		return dateTo;
	}



	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}



	public LinkedList<ProjectPriceEntryInfo> getProjectList() {
		return projectList;
	}

	public void setProjectList(LinkedList<ProjectPriceEntryInfo> projectList) {
		this.projectList = projectList;
	}

	public LinkedList<MaterialPriceEntryInfo> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(LinkedList<MaterialPriceEntryInfo> materialList) {
		this.materialList = materialList;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(PriceInfo.class.getName());
		bt.setTableName("bill_price");
		bt.setPk("0016");
		bt.addEntryBt("projectList", ProjectPriceEntryInfo.class);
		bt.addEntryBt("materialList", MaterialPriceEntryInfo.class);
		return bt;
	}

}
