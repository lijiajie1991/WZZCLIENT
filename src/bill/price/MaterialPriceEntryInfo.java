/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午12:53:40 
  */
package bill.price;

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
public class MaterialPriceEntryInfo extends EntryInfo{
	private static final long serialVersionUID = 1L;
	
	protected MaterialInfo materialInfo = null;
	protected MeasureUnitInfo unitInfo = null;
	protected BigDecimal price = BigDecimal.ZERO;
	protected boolean isFloat = false;
	protected String remark = null;
	
	public MaterialPriceEntryInfo()
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



	public boolean getIsFloat() {
		return isFloat;
	}




	public void setIsFloat(boolean isFloat) {
		this.isFloat = isFloat;
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
		bt.setClsName(MaterialPriceEntryInfo.class.getName());
		bt.setTableName("bill_price_materialentry");
		bt.setPk("001602");
		return bt;
	}
}
