/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.sys.comparam;

import bean.base.BaseInfo;
import common.db.BeanTableInfo;
import em.ComType;

/**
  * @说明 店铺
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
public class ComParamInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected ComType ct = null;
	protected String ComName = null;			
	protected int BoudIndex = 9600;           
	protected int DataBits = 8;               
	protected int StopBits = 1;               
	protected int ParityV = 0;        
	
	public ComParamInfo() {
		super();
	}
	
	
	public String getComName() {
		return ComName;
	}



	public void setComName(String comName) {
		ComName = comName;
	}



	public int getBoudIndex() {
		return BoudIndex;
	}



	public void setBoudIndex(int boudIndex) {
		BoudIndex = boudIndex;
	}



	public int getDataBits() {
		return DataBits;
	}



	public void setDataBits(int dataBits) {
		DataBits = dataBits;
	}



	public int getStopBits() {
		return StopBits;
	}



	public void setStopBits(int stopBits) {
		StopBits = stopBits;
	}



	public int getParityV() {
		return ParityV;
	}



	public void setParityV(int parityV) {
		ParityV = parityV;
	}

	public ComType getCt() {
		return ct;
	}


	public void setCt(ComType ct) {
		this.ct = ct;
	}


	public String toString()
	{
		return this.getName();
	}

	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(ComParamInfo.class.getName());
		bt.setTableName("base_comparam");
		bt.setPk("0040");
		return bt;
	}

}
