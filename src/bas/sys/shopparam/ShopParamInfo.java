/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.sys.shopparam;

import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 店铺
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
public class ShopParamInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected String shopPhone = null;
	
	protected int hour = 22;
	protected int day = 0;
	
	protected boolean isSaleEntry = false;
	protected boolean isPurchaseEntry = false;
	protected boolean isBossSms = false;
	protected boolean isCustomerSms = false;
	protected boolean isBarCode = false;
	protected boolean isCustomerCard = false;
	
	public ShopParamInfo() {
		super();
	}
	
	public String getShopPhone() {
		return shopPhone;
	}



	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}



	public boolean getIsSaleEntry() {
		return isSaleEntry;
	}



	public void setIsSaleEntry(boolean isSaleEntry) {
		this.isSaleEntry = isSaleEntry;
	}



	public boolean getIsPurchaseEntry() {
		return isPurchaseEntry;
	}



	public void setIsPurchaseEntry(boolean isPurchaseEntry) {
		this.isPurchaseEntry = isPurchaseEntry;
	}



	public boolean getIsBossSms() {
		return isBossSms;
	}



	public void setIsBossSms(boolean isBossSms) {
		this.isBossSms = isBossSms;
	}



	public boolean getIsCustomerSms() {
		return isCustomerSms;
	}



	public void setIsCustomerSms(boolean isCustomerSms) {
		this.isCustomerSms = isCustomerSms;
	}

	

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	

	public boolean getIsBarCode() {
		return isBarCode;
	}

	public void setIsBarCode(boolean isBarCode) {
		this.isBarCode = isBarCode;
	}
	
	

	public boolean getIsCustomerCard() {
		return isCustomerCard;
	}

	public void setIsCustomerCard(boolean isCustomerCard) {
		this.isCustomerCard = isCustomerCard;
	}

	public String toString()
	{
		return this.getName();
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(ShopParamInfo.class.getName());
		bt.setTableName("base_shopparam");
		bt.setPk("0039");
		return bt;
	}

}
