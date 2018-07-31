/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年8月27日 下午9:36:40 
  */
package bas.wxpayset;

import bean.base.BaseInfo;
import common.db.BeanTableInfo;

/**
  * @说明 职位
  * @作者 黎嘉杰 
  * @日期 2016年9月10日 下午3:32:55 
  */
public class WxPaySetInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;
	
	protected String appId = null;
	protected String mchID = null;
	protected String subMchID = null;
	protected String certLocalPath = null;
	protected String certPassword = null;
	
	public WxPaySetInfo() {
		super();
	}
	
	
	
	public String getAppId() {
		return appId;
	}



	public void setAppId(String appId) {
		this.appId = appId;
	}



	public String getMchID() {
		return mchID;
	}



	public void setMchID(String mchID) {
		this.mchID = mchID;
	}



	public String getSubMchID() {
		return subMchID;
	}



	public void setSubMchID(String subMchID) {
		this.subMchID = subMchID;
	}



	public String getCertLocalPath() {
		return certLocalPath;
	}



	public void setCertLocalPath(String certLocalPath) {
		this.certLocalPath = certLocalPath;
	}



	public String getCertPassword() {
		return certPassword;
	}



	public void setCertPassword(String certPassword) {
		this.certPassword = certPassword;
	}



	public BeanTableInfo getBT() throws Exception {
		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(WxPaySetInfo.class.getName());
		bt.setTableName("base_wxpayset");
		bt.setPk("0044");
		return bt;
	}

}
