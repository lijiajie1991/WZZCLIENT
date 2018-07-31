/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.stock;

import java.math.BigDecimal;

import bas.material.MaterialInfo;
import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class ShopStockInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected String upTime = null;
	protected MaterialInfo materialInfo = null;
	protected BigDecimal qty = BigDecimal.ZERO;
	
	public ShopStockInfo() {
		super();
	}
	
	public String getUpTime() {
		return upTime;
	}

	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}

	

	public MaterialInfo getMaterialInfo() {
		return materialInfo;
	}

	public void setMaterialInfo(MaterialInfo materialInfo) {
		this.materialInfo = materialInfo;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(ShopStockInfo.class.getName());
		bt.setTableName("base_shopstock");
		bt.setPk("0017");
		return bt;
	}

}
