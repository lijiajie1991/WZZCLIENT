package bas.job;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import bean.IHttp;
import bean.base.BaseEditUI;
import common.util.ContextUtil;
import em.ProfessionType;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;

public class JobEditUI extends BaseEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conSalary = null;
	protected FormatInputField txtSalary = null;
	
	protected LabelContainer conBounty = null;
	protected FormatInputField txtBounty = null;
	
	public JobEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(320, 200);
		
		txtNumber.setEditable(true);
		
		txtSalary = new FormatInputField(FormatInputField.DT_DOUBLE);
		conSalary = new LabelContainer(txtSalary, 120, BorderLayout.WEST, true, "底工资：");
		conSalary.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtBounty = new FormatInputField(FormatInputField.DT_DOUBLE);
		conBounty = new LabelContainer(txtBounty, 120, BorderLayout.WEST, true, "补贴：");
		conBounty.setBounds(new Rectangle(5, 110, 300, 30));
		
		jp.add(conSalary);
		jp.add(conBounty);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		BigDecimal salary = txtSalary.getBigDecimalValue();
		BigDecimal bounty = txtBounty.getBigDecimalValue();
		JobInfo info = (JobInfo) this.editData;
		info.setSalary(salary);
		info.setBounty(bounty);
		
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		JobInfo info = (JobInfo) this.editData;
		txtSalary.setBigDecimalValue(info.getSalary());
		txtBounty.setBigDecimalValue(info.getBounty());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		JobInfo info = (JobInfo) this.editData;
		ProfessionType pft = ContextUtil.getAdminShopInfo().getPft();
		info.setPft(pft);
	}

	protected Class<?> getInfoClass() {
		return JobInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpJob.getInstance();
	}

	protected String getEditUITitle() {
		return "员工职位";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

}
