package bas.supplier;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import bean.IHttp;
import bean.base.BaseEditUI;
import exp.ExpHandle;
import myswing.btn.ToolButton;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;

public class SupplierEditUI extends BaseEditUI{
	private static final long serialVersionUID = 1L;
	
	protected ToolButton btnShowPhone = null;
	
	protected LabelContainer conPhone = null;
	protected FormatInputField txtPhone = null;
	
	protected LabelContainer conAddress = null;
	protected FormatInputField txtAddress = null;
	
	protected LabelContainer conRemark = null;
	protected FormatInputField txtRmark = null;
	
	protected JCheckBox chkIsEnable = null;
	
	public SupplierEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 220);
		
		conName.setBounds(new Rectangle(5, 5, 300, 30));
		conName.setBounds(new Rectangle(340, 5, 300, 30));
		
		txtPhone = new FormatInputField();
		conPhone = new LabelContainer(txtPhone, 120, BorderLayout.WEST, true, "联系电话：");
		conPhone.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtAddress = new FormatInputField();
		conAddress = new LabelContainer(txtAddress, 120, BorderLayout.WEST, true, "地址：");
		conAddress.setBounds(new Rectangle(340, 40, 300, 30));
		
		txtRmark = new FormatInputField();
		conRemark = new LabelContainer(txtRmark, 120, BorderLayout.WEST, true, "说明：");
		conRemark.setBounds(new Rectangle(5, 75, 635, 30));
		
		chkIsEnable = new JCheckBox("是否生效");
		chkIsEnable.setBounds(new Rectangle(5, 110, 300, 30));
		
		jp.add(conPhone);
		jp.add(conAddress);
		jp.add(chkIsEnable);
		jp.add(conRemark);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
	}

	public void initToolBar() throws Exception {
		super.initToolBar();
		
		btnShowPhone = new ToolButton("显示手机号码", "btnShowPhone");
		btnList.addLast(btnShowPhone);
	
		
	}

	public void addListener() throws Exception {
		super.addListener();
		
		btnShowPhone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionShowPhone(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(SupplierEditUI.this, err);
				}
			}
		});
	}
	
	protected void actionShowPhone(ActionEvent e) throws Exception{
		String str = btnShowPhone.getText();
		if("显示手机号码".equals(str))
		{
			SupplierInfo info = (SupplierInfo) this.editData;
			txtPhone.setText(info.getPhone());
			btnShowPhone.setText("隐藏手机号码");
		}
		else
		{
			SupplierInfo info = (SupplierInfo) this.editData;
			info.setPhone(txtPhone.getText());
			txtPhone.setText("***********");
			btnShowPhone.setText("显示手机号码");
		}
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		SupplierInfo info = (SupplierInfo) this.editData;
		
		String phone = txtPhone.getText();
		String address = txtAddress.getText();
		String remark = txtRmark.getText();
		boolean isEnable = chkIsEnable.isSelected();
		
		String str = btnShowPhone.getText();
		if("显示手机号码".equals(str))
		{
			if(phone.contains("*"))
				phone = info.getPhone();
		}
		
		info.setPhone(phone);
		info.setAddress(address);
		info.setRemark(remark);
		info.setIsEnable(isEnable);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		
		SupplierInfo info = (SupplierInfo) this.editData;
		txtPhone.setText(info.getPhone());
		txtAddress.setText(info.getAddress());
		txtRmark.setText(info.getRemark());
		chkIsEnable.setSelected(info.getIsEnable());
		
		String str = btnShowPhone.getText();
		if("显示手机号码".equals(str))
		{
			txtPhone.setText("***********");
		}
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		SupplierInfo info = (SupplierInfo) this.editData;
		info.setIsEnable(true);
	}

	protected Class<?> getInfoClass() {
		return SupplierInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpSupplier.getInstance();
	}

	protected String getEditUITitle() {
		return "供应商";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

}
