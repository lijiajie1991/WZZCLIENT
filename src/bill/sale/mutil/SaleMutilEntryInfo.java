/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bill.sale.mutil;

import java.math.BigDecimal;

import bas.material.MaterialInfo;
import bas.measureunit.MeasureUnitInfo;
import bean.EntryInfo;
import common.db.BeanTableInfo;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
public class SaleMutilEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected MaterialInfo materialInfo = null;
	protected MeasureUnitInfo unitInfo = null;
	protected BigDecimal price = BigDecimal.ZERO;
	protected BigDecimal qty = BigDecimal.ONE;
	protected BigDecimal amt = BigDecimal.ZERO;
	protected BigDecimal factAmt = BigDecimal.ZERO;
	protected String remark = null;
	
	public SaleMutilEntryInfo()
	{
		super();
	}


	public MaterialInfo getMaterialInfo() {
		return materialInfo;
	}



	public void setMaterialInfo(MaterialInfo materialInfo) {
		this.materialInfo = materialInfo;
	}



	public MeasureUnitInfo getUnitInfo() {
		return unitInfo;
	}



	public void setUnitInfo(MeasureUnitInfo unitInfo) {
		this.unitInfo = unitInfo;
	}



	public BigDecimal getPrice() {
		return price;
	}



	public void setPrice(BigDecimal price) {
		this.price = price;
	}



	public BigDecimal getQty() {
		return qty;
	}



	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}



	public BigDecimal getAmt() {
		return amt;
	}



	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}



	public BigDecimal getFactAmt() {
		return factAmt;
	}



	public void setFactAmt(BigDecimal factAmt) {
		this.factAmt = factAmt;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	  * @throws Exception 
	 * @功能描述
	  * @作者 黎嘉杰 
	  * @日期 2016年9月10日 下午12:56:18 
	  * @参数 
	  * @返回
	  */
	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(SaleMutilEntryInfo.class.getName());
		bt.setTableName("bill_salemutil_entry");
		bt.setPk("00200101");
		return bt;
	}
}
