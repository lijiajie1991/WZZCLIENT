package bas.wxpayset;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import bas.sys.shop.ShopInfo;
import bean.IHttp;
import bean.base.BaseEditUI;
import common.util.ContextUtil;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;

public class WxPaySetEditUI extends BaseEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conAppId = null;
	protected FormatInputField txtAppId = null;
	
	protected LabelContainer conMchID = null;
	protected FormatInputField txtMchID = null;
	
	protected LabelContainer conSubMchID = null;
	protected FormatInputField txtSubMchID = null;
	
	protected LabelContainer conCertLocalPath = null;
	protected FormatInputField txtCertLocalPath = null;
	
	protected LabelContainer conCertPassword = null;
	protected FormatInputField txtCertPassword = null;
	
	public WxPaySetEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(320, 250);
		
		txtNumber.setEditable(true);
		conNumber.setBounds(new Rectangle(1000, 5, 300, 30));
		conName.setBounds(new Rectangle(1000, 5, 300, 30));
		
		txtAppId = new FormatInputField();
		conAppId = new LabelContainer(txtAppId, 120, BorderLayout.WEST, true, "公众号ID：");
		conAppId.setBounds(new Rectangle(5, 5, 300, 30));
		
		txtMchID = new FormatInputField();
		conMchID = new LabelContainer(txtMchID, 120, BorderLayout.WEST, true, "商户号ID：");
		conMchID.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtSubMchID = new FormatInputField();
		conSubMchID = new LabelContainer(txtSubMchID, 120, BorderLayout.WEST, true, "子商户号ID：");
		conSubMchID.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtCertLocalPath = new FormatInputField();
		conCertLocalPath = new LabelContainer(txtCertLocalPath, 120, BorderLayout.WEST, true, "证书路径：");
		conCertLocalPath.setBounds(new Rectangle(5, 110, 300, 30));
		
		txtCertPassword = new FormatInputField();
		conCertPassword = new LabelContainer(txtCertPassword, 120, BorderLayout.WEST, true, "证书密码：");
		conCertPassword.setBounds(new Rectangle(5, 145, 300, 30));
		
		jp.add(conAppId);
		jp.add(conMchID);
		jp.add(conSubMchID);
		jp.add(conCertLocalPath);
		jp.add(conCertPassword);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		String appId = txtAppId.getText();
		String mchId = txtMchID.getText();
		String subMchId = txtSubMchID.getText();
		String path = txtCertLocalPath.getText();
		String pw = txtCertPassword.getText();
		
		WxPaySetInfo info = (WxPaySetInfo) this.editData;
		info.setAppId(appId);
		info.setMchID(mchId);
		info.setSubMchID(subMchId);
		info.setCertLocalPath(path);
		info.setCertPassword(pw);
		
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		WxPaySetInfo info = (WxPaySetInfo) this.editData;
		txtAppId.setText(info.getAppId());
		txtMchID.setText(info.getMchID());
		txtSubMchID.setText(info.getSubMchID());
		txtCertLocalPath.setText(info.getCertLocalPath());
		txtCertPassword.setText(info.getCertPassword());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		ShopInfo shopInfo = ContextUtil.getShopInfo();
		WxPaySetInfo info = (WxPaySetInfo) this.editData;
		info.setNumber(shopInfo.getNumber());
		info.setName(shopInfo.getName() + "微信支付设置");
	}

	protected Class<?> getInfoClass() {
		return WxPaySetInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpWxPaySet.getInstance();
	}

	protected String getEditUITitle() {
		return "微信支付设置";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

}
