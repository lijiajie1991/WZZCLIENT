/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.shoppayment;

import java.math.BigDecimal;

import bas.sys.shop.ShopInfo;
import bean.bill.BillInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class ShopPayMentInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected ShopInfo customerShopInfo = null;
	protected BigDecimal amt = BigDecimal.ZERO;
	protected boolean isSms = false;
	
	public ShopPayMentInfo() {
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

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(ShopPayMentInfo.class.getName());
		bt.setTableName("bill_shoppayment");
		bt.setPk("0029");
		return bt;
	}

}
