/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bill.register;

import bean.EntryInfo;
import common.db.BeanTableInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class RegisterEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected String shopName = null;
	protected String shopAddress = null;
	protected String shopPhone = null;
	
	public RegisterEntryInfo()
	{
		super();
	}

	public String getShopName() {
		return shopName;
	}



	public void setShopName(String shopName) {
		this.shopName = shopName;
	}



	public String getShopAddress() {
		return shopAddress;
	}



	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}



	public String getShopPhone() {
		return shopPhone;
	}



	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
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
		bt.setClsName(RegisterEntryInfo.class.getName());
		bt.setTableName("bill_register_entry");
		bt.setPk("003201");
		return bt;
	}
}
