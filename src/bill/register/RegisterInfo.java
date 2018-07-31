/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.register;

import java.util.LinkedList;

import bean.bill.BillInfo;
import common.db.BeanTableInfo;
import em.ProfessionType;

/**
  * @说明 注册
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class RegisterInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected String shopName = null;
	protected String shopAddress = null;
	protected String shopPhone = null;
	protected ProfessionType pft = ProfessionType.EMPTY;
	
	protected int hour = 22;
	protected int day = 0;
	
	protected String smsPhone = null;
	protected boolean isSaleEntry = false;
	protected boolean isPurchaseEntry = false;
	protected boolean isBossSms = false;
	protected boolean isCustomerSms = false;
	
	
	protected String remark = null;
	
	protected LinkedList<RegisterEntryInfo> entryList = null;
	
	public RegisterInfo() {
		super();
		
		entryList = new LinkedList<RegisterEntryInfo>();
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

	public ProfessionType getPft() {
		return pft;
	}

	public void setPft(ProfessionType pft) {
		this.pft = pft;
	}
	
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	

	public String getSmsPhone() {
		return smsPhone;
	}

	public void setSmsPhone(String smsPhone) {
		this.smsPhone = smsPhone;
	}

	public LinkedList<RegisterEntryInfo> getEntryList() {
		return entryList;
	}

	public void setEntryList(LinkedList<RegisterEntryInfo> entryList) {
		this.entryList = entryList;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(RegisterInfo.class.getName());
		bt.setTableName("bill_register");
		bt.setPk("0032");
		bt.addEntryBt("entryList", RegisterEntryInfo.class);
		return bt;
	}
}
