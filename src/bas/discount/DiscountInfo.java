/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.discount;

import java.util.LinkedList;

import bas.vip.VipInfo;
import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 提成比例
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class DiscountInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected VipInfo vipInfo = null;
	protected boolean isEnable = true;
	protected LinkedList<ProjectDiscountEntryInfo> proList = null;
	protected LinkedList<MaterialDiscountEntryInfo> matList = null;
	
	public DiscountInfo() {
		super();
		
		proList = new LinkedList<ProjectDiscountEntryInfo>();
		matList = new LinkedList<MaterialDiscountEntryInfo>();
	}

	public VipInfo getVipInfo() {
		return vipInfo;
	}

	public void setVipInfo(VipInfo vipInfo) {
		this.vipInfo = vipInfo;
	}
	
	

	public boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public LinkedList<ProjectDiscountEntryInfo> getProList() {
		return proList;
	}

	public void setProList(LinkedList<ProjectDiscountEntryInfo> proList) {
		this.proList = proList;
	}

	public LinkedList<MaterialDiscountEntryInfo> getMatList() {
		return matList;
	}

	public void setMatList(LinkedList<MaterialDiscountEntryInfo> matList) {
		this.matList = matList;
	}


	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(DiscountInfo.class.getName());
		bt.setTableName("base_discount");
		bt.setPk("0022");
		bt.addEntryBt("proList", ProjectDiscountEntryInfo.class);
		bt.addEntryBt("matList", MaterialDiscountEntryInfo.class);
		return bt;
	}

}
