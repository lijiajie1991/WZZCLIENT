/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月11日 上午2:55:25 
  */
package common.db;

import java.io.Serializable;

import bas.sys.shop.ShopInfo;
import bas.sys.user.UserInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月11日 上午2:57:34 
  */
public class Context implements Serializable{
	private static final long serialVersionUID = 4L;
	
	private ShopInfo shopInfo = null;
	private UserInfo userInfo = null;
	
	public Context()
	{
		shopInfo = new ShopInfo();
		userInfo = new UserInfo();
	}
	
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public String getUserId()
	{
		return this.userInfo != null ? this.userInfo.getId() : "";
	}
	
	public String getShopId()
	{
		return this.shopInfo != null ? this.shopInfo.getId() : "";
	}

	public String getAdminShopId()
	{
		if(this.shopInfo != null && this.shopInfo.getParentInfo() != null)
			return this.shopInfo.getParentInfo().getId();
		return null;
	}
}
