/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.sys.user;

import java.util.LinkedList;

import bas.sys.shop.ShopInfo;
import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 用户信息
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
public class UserInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected ShopInfo defShopInfo = null;
	protected String password = null;  //密码
	protected LinkedList<UserRoleEntryInfo> roleList = null; //角色列表
	protected LinkedList<UserShopEntryInfo> shopList = null;	//店铺列表
	
	public UserInfo() {
		super();
		
		roleList = new LinkedList<UserRoleEntryInfo>();
		shopList = new LinkedList<UserShopEntryInfo>();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	public ShopInfo getDefShopInfo() {
		return defShopInfo;
	}

	public void setDefShopInfo(ShopInfo defShopInfo) {
		this.defShopInfo = defShopInfo;
	}

	public LinkedList<UserRoleEntryInfo> getRoleList() {
		return roleList;
	}

	public void setRoleList(LinkedList<UserRoleEntryInfo> roleList) {
		this.roleList = roleList;
	}

	public LinkedList<UserShopEntryInfo> getShopList() {
		return shopList;
	}

	public void setShopList(LinkedList<UserShopEntryInfo> shopList) {
		this.shopList = shopList;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(UserInfo.class.getName());
		bt.setTableName("base_user");
		bt.setPk("0001");
		
		bt.addEntryBt("roleList", UserRoleEntryInfo.class);
		bt.addEntryBt("shopList", UserShopEntryInfo.class);
		
		return bt;
	}

}
