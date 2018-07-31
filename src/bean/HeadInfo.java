/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月11日 上午4:56:15 
  */
package bean;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月11日 上午4:56:15 
  */
public abstract class HeadInfo extends SysInfo{
	private static final long serialVersionUID = 1L;
	
	protected String shopId = null;
	protected String adminShopId = null;
	
	protected int useCount = 0;
	
	public HeadInfo()
	{
		super();
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}

	public String getAdminShopId() {
		return adminShopId;
	}

	public void setAdminShopId(String adminShopId) {
		this.adminShopId = adminShopId;
	}
}
