package bas.sys.param;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import bean.IHttp;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import ui.base.EditUI;

public class ParamEditUI extends EditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	protected LabelContainer conName = null;
	protected FormatInputField txtName = null;
	
	protected LabelContainer conVal = null;
	protected FormatInputField txtVal = null;
	
	public ParamEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(320, 200);
		
		txtNumber = new FormatInputField();
		conNumber = new LabelContainer(txtNumber, 120, BorderLayout.WEST, true, "编号：");
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		
		txtName = new FormatInputField();
		conName = new LabelContainer(txtName, 120, BorderLayout.WEST, true, "名称：");
		conName.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtVal = new FormatInputField();
		conVal = new LabelContainer(txtVal, 120, BorderLayout.WEST, true, "参数值：");
		conVal.setBounds(new Rectangle(5, 75, 300, 30));
		
		jp.add(conNumber);
		jp.add(conName);
		jp.add(conVal);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		String number = txtNumber.getText();
		String name = txtName.getText();
		String val = txtVal.getText();
		
		ParamInfo info = (ParamInfo) this.editData;
		info.setNumber(number);
		info.setName(name);
		info.setVal(val);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		ParamInfo info = (ParamInfo) this.editData;
		txtNumber.setText(info.getNumber());
		txtName.setText(info.getName());
		txtVal.setText(info.getVal());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
	}

	protected Class<?> getInfoClass() {
		return ParamInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpParam.getInstance();
	}

	protected String getEditUITitle() {
		return "参数设置";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

}
