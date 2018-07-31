/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月20日 下午9:28:21 
  */
package exp;

import java.io.IOException;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月20日 下午9:28:21 
  */
/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月20日 下午9:43:00 
  */
public class HttpException extends IOException{
	private static final long serialVersionUID = 1L;
	
	protected String expMsg = null;
	protected StackTraceElement[] expSts = null;
	
	protected String caseMsg = null;
	protected StackTraceElement[] caseSts = null;
	

	public HttpException(String expMsg, StackTraceElement[] expSts, String caseMsg, StackTraceElement[] caseSts) {
		super();
		this.expMsg = expMsg;
		this.expSts = expSts;
		this.caseMsg = caseMsg;
		this.caseSts = caseSts;
	}


	public String getExpMsg() {
		return expMsg;
	}


	public void setExpMsg(String expMsg) {
		this.expMsg = expMsg;
	}


	public StackTraceElement[] getExpSts() {
		return expSts;
	}


	public void setExpSts(StackTraceElement[] expSts) {
		this.expSts = expSts;
	}


	public String getCaseMsg() {
		return caseMsg;
	}


	public void setCaseMsg(String caseMsg) {
		this.caseMsg = caseMsg;
	}


	public StackTraceElement[] getCaseSts() {
		return caseSts;
	}


	public void setCaseSts(StackTraceElement[] caseSts) {
		this.caseSts = caseSts;
	}

	
}
