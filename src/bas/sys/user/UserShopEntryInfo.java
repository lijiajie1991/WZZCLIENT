/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bas.sys.user;

import bas.sys.shop.ShopInfo;
import bean.EntryInfo;
import common.db.BeanTableInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class UserShopEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected ShopInfo shopInfo = null;
	
	public UserShopEntryInfo()
	{
	}
	
	

	public ShopInfo getShopInfo() {
		return shopInfo;
	}



	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
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
		bt.setClsName(UserShopEntryInfo.class.getName());
		bt.setTableName("base_user_shopentry");
		bt.setPk("000102");
		return bt;
	}
}
