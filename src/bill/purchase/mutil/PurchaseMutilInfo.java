/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bill.purchase.mutil;

import java.math.BigDecimal;
import java.util.LinkedList;

import bas.person.PersonInfo;
import bas.supplier.SupplierInfo;
import bean.bill.BillInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class PurchaseMutilInfo extends BillInfo{
	private static final long serialVersionUID = 1L;
	
	protected SupplierInfo supplierInfo = null;
	protected PersonInfo personInfo = null;
	protected String remark = null;
	protected BigDecimal amt = null;
	protected LinkedList<PurchaseMutilEntryInfo> entryList = null;
	
	public PurchaseMutilInfo() {
		super();
		
		entryList = new LinkedList<PurchaseMutilEntryInfo>();
	}

	public SupplierInfo getSupplierInfo() {
		return supplierInfo;
	}

	public void setSupplierInfo(SupplierInfo supplierInfo) {
		this.supplierInfo = supplierInfo;
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public LinkedList<PurchaseMutilEntryInfo> getEntryList() {
		return entryList;
	}

	public void setEntryList(LinkedList<PurchaseMutilEntryInfo> entryList) {
		this.entryList = entryList;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(PurchaseMutilInfo.class.getName());
		bt.setTableName("bill_purchasemutil");
		bt.setPk("002701");
		bt.addEntryBt("entryList", PurchaseMutilEntryInfo.class);
		return bt;
	}

}
