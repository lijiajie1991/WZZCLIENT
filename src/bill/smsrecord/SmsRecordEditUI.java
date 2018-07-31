package bill.smsrecord;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;

import bean.IHttp;
import bean.bill.BillEditUI;
import bean.bill.BillInfo;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;

public class SmsRecordEditUI extends BillEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conCtx = null;
	protected FormatInputField txtCtx = null;
	
	protected LabelContainer conPhone = null;
	protected FormatInputField txtPhone = null;
	
	protected LabelContainer conCode = null;
	protected FormatInputField txtCode = null;
	
	protected JCheckBox chkIsSuccess = null;
	
	public SmsRecordEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 220);
		
		conNumber.setBounds(new Rectangle(3000, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(5, 5, 300, 30));
		conBizdate.setLabelText("发送日期");
		
		txtPhone = new FormatInputField();
		conPhone = new LabelContainer(txtPhone, 120, BorderLayout.WEST, true, "接收号码：");
		conPhone.setBounds(new Rectangle(340, 5, 300, 30));
		
		txtCtx = new FormatInputField();
		conCtx = new LabelContainer(txtCtx, 120, BorderLayout.WEST, true, "短信内容：");
		conCtx.setBounds(new Rectangle(5, 40, 635, 30));
		
		txtCode = new FormatInputField();
		conCode = new LabelContainer(txtCode, 120, BorderLayout.WEST, true, "发送状态码：");
		conCode.setBounds(new Rectangle(5, 75, 635, 30));
		
		chkIsSuccess = new JCheckBox("是否发送成功");
		chkIsSuccess.setBounds(new Rectangle(5, 110, 300, 30));
		
		chkIsAuditTrue.setText("是否已发送");
		chkIsAuditTrue.setBounds(new Rectangle(340, 110, 300, 30));
		
		
		jp.add(conPhone);
		jp.add(conCtx);
		jp.add(conCode);
		jp.add(chkIsSuccess);
		jp.add(chkIsAuditTrue);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		SmsRecordInfo info = (SmsRecordInfo) this.editData;
		
		boolean isSuccess = chkIsSuccess.isSelected();
		String phone = txtPhone.getText();
		String ctx = txtCtx.getText();
		String code = txtCode.getText();
		
		info.setPhone(phone);
		info.setCtx(ctx);
		info.setIsSuccess(isSuccess);
		info.setCode(code);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		SmsRecordInfo info = (SmsRecordInfo) this.editData;
		
		chkIsSuccess.setSelected(info.getIsSuccess());
		txtPhone.setText(info.getPhone());
		txtCtx.setText(info.getCtx());
		txtCode.setText(info.getCode());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		
	}
	
	protected Class<?> getInfoClass() {
		return SmsRecordInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpSmsRecord.getInstance();
	}

	protected String getEditUITitle() {
		return "短信记录";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

	protected void setCompentStatus() {
		super.setCompentStatus();
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		
		chkIsSuccess.setEnabled(false);
		txtPhone.setEnabled(!flag);
		txtCtx.setEnabled(!flag);
		txtCode.setEditable(false);
	}

	protected void actionClose(ActionEvent e) throws Exception {
		super.actionClose(e);
	}
}
