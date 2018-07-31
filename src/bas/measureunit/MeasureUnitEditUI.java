package bas.measureunit;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import bean.IHttp;
import bean.base.BaseEditUI;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;

public class MeasureUnitEditUI extends BaseEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conCoe = null;
	protected FormatInputField txtCoe = null;
	
	public MeasureUnitEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(320, 200);
		txtCoe = new FormatInputField(FormatInputField.DT_DOUBLE);
		conCoe = new LabelContainer(txtCoe, 120, BorderLayout.WEST, true, "转换系数：");
		conCoe.setBounds(new Rectangle(5, 75, 300, 30));
		jp.add(conCoe);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		BigDecimal coe = txtCoe.getBigDecimalValue();
		MeasureUnitInfo info = (MeasureUnitInfo) this.editData;
		info.setCoe(coe);
		
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		MeasureUnitInfo info = (MeasureUnitInfo) this.editData;
		txtCoe.setBigDecimalValue(info.getCoe());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
	}

	protected Class<?> getInfoClass() {
		return MeasureUnitInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpMeasureUnit.getInstance();
	}

	protected String getEditUITitle() {
		return "计量单位";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

}
