/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.shopbalance;

import java.math.BigDecimal;

import bas.sys.shop.ShopInfo;
import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class ShopBalanceInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected ShopInfo customerShopInfo = null;
	protected BigDecimal price = BigDecimal.ZERO;
	protected BigDecimal amt = BigDecimal.ZERO;
	protected boolean isSms = false;
	
	public ShopBalanceInfo() {
		super();
	}
	
	
	public ShopInfo getCustomerShopInfo() {
		return customerShopInfo;
	}



	public void setCustomerShopInfo(ShopInfo customerShopInfo) {
		this.customerShopInfo = customerShopInfo;
	}


	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public boolean getIsSms() {
		return isSms;
	}

	public void setIsSms(boolean isSms) {
		this.isSms = isSms;
	}
	
	

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(ShopBalanceInfo.class.getName());
		bt.setTableName("base_shopbalance");
		bt.setPk("0028");
		return bt;
	}

}
