/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.supplier;

import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class SupplierInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected String phone = null;
	protected String address = null;
	protected String remark = null;
	protected boolean isEnable = true;
	
	public SupplierInfo() {
		super();
	}

	
	
	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public boolean getIsEnable() {
		return isEnable;
	}



	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}



	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(SupplierInfo.class.getName());
		bt.setTableName("base_supplier");
		bt.setPk("0014");
		return bt;
	}

}
