package listener;

import em.PayType;

public interface PayListener {
	public void success(String logId, PayType pt);
	public boolean isShowSuccess();
	public String getSuccessMsg();
	
	public void fail(String logId, PayType pt);
	public boolean isShowFail();
	public String getFailMsg(String msg);
	
}
