package report.base.bean;

import bas.sys.shop.ShopInfo;
import bean.EntryInfo;
import common.db.BeanTableInfo;

public class RptShopInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected ShopInfo shopInfo = null;

	public RptShopInfo()
	{
		super();
	}

	public ShopInfo getShopInfo() {
		return shopInfo;
	}

	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	
	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(RptShopInfo.class.getName());
		bt.setTableName("sys_rpt_shop");
		bt.setPk("0035");
		return bt;
	}
	
}
