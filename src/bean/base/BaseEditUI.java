package bean.base;

import java.awt.BorderLayout;
import java.awt.Rectangle;

import common.util.ContextUtil;
import exp.BizException;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import ui.base.EditUI;

public abstract class BaseEditUI extends EditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	protected LabelContainer conName = null;
	protected FormatInputField txtName = null;
	
	
	public BaseEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(width, height);
		
		txtNumber = new FormatInputField();
		txtNumber.setEditable(false);
		conNumber = new LabelContainer(txtNumber, 120, BorderLayout.WEST, true, "编号：");
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		
		txtName = new FormatInputField();
		conName = new LabelContainer(txtName, 120, BorderLayout.WEST, true, "名称：");
		conName.setBounds(new Rectangle(5, 40, 300, 30));
		
		jp.add(conNumber);
		jp.add(conName);
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		BaseInfo info = (BaseInfo) this.editData;
		info.setNumber(txtNumber.getText());
		info.setName(txtName.getText());
	}

	protected void loadFiles() throws Exception {
		BaseInfo info = (BaseInfo) this.editData;
		txtNumber.setText(info.getNumber());
		txtName.setText(info.getName());
	}

	protected void loadDefault() throws Exception {
		super.loadDefault();
		IHttpBase http = (IHttpBase) this.getHttpInstance();
		
		BaseInfo info = (BaseInfo) this.editData;
		info.setNumber(http.getNewNumber());
		info.setShopId(ContextUtil.getShopId());
		info.setAdminShopId(ContextUtil.getAdminShopId());
	}

	protected void verifyInput() throws Exception {
		super.verifyInput();
		BaseInfo info = (BaseInfo) this.editData;
		String num = info.getNumber();
		String name = info.getName();
		if(num == null || "".equals(num))
			throw new BizException("编码不能为空");
		
		if(name == null || "".equals(name))
			throw new BizException("名称不能为空");
		
	}
}
