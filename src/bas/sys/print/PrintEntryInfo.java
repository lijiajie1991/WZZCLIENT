/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bas.sys.print;

import bas.sys.shop.ShopInfo;
import bean.EntryInfo;
import common.db.BeanTableInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class PrintEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected ShopInfo shopInfo = null;
	protected boolean isDef = false;
	
	public PrintEntryInfo()
	{
	}
	
	

	public ShopInfo getShopInfo() {
		return shopInfo;
	}



	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}

	

	public boolean getIsDef() {
		return isDef;
	}



	public void setIsDef(boolean isDef) {
		this.isDef = isDef;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(PrintEntryInfo.class.getName());
		bt.setTableName("sys_print_entry");
		bt.setPk("004201");
		return bt;
	}
}
