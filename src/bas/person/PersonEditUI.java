package bas.person;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

import bas.job.IHttpJob;
import bas.job.JobInfo;
import bean.IHttp;
import bean.Info;
import bean.base.BaseEditUI;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.PersonStatusType;
import em.SortType;
import myswing.container.LabelContainer;
import myswing.editor.DatePicker;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;

public class PersonEditUI extends BaseEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conJob = null;
	protected MyComboBox cmbJob = null;
	
	protected LabelContainer conPersonId = null;
	protected FormatInputField txtPersonId = null;
	
	protected LabelContainer conBirth = null;
	protected DatePicker pkBirth = null;
	
	protected LabelContainer conStatus = null;
	protected MyComboBox cmbStatus = null;
	
	public PersonEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(690, 200);
		
		conName.setBounds(new Rectangle(5, 5, 300, 30));
		conName.setBounds(new Rectangle(340, 5, 300, 30));
		
		cmbJob = new MyComboBox();
		conJob = new LabelContainer(cmbJob, 120, BorderLayout.WEST, true, "职位：");
		conJob.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtPersonId = new FormatInputField();
		conPersonId = new LabelContainer(txtPersonId, 120, BorderLayout.WEST, true, "身份证：");
		conPersonId.setBounds(new Rectangle(340, 40, 300, 30));
		
		pkBirth = new DatePicker();
		conBirth = new LabelContainer(pkBirth, 120, BorderLayout.WEST, true, "生日：");
		conBirth.setBounds(new Rectangle(5, 75, 300, 30));
		
		cmbStatus = new MyComboBox();
		conStatus = new LabelContainer(cmbStatus, 120, BorderLayout.WEST, true, "状态：");
		conStatus.setBounds(new Rectangle(340, 75, 300, 30));
		
		jp.add(conJob);
		jp.add(conPersonId);
		jp.add(conBirth);
		jp.add(conStatus);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		filter.setMkr("#0 or #1");
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		LinkedList<Info> jobList = IHttpJob.getInstance().getInfoList(filter, order);
		cmbJob.addItems(jobList.toArray(new JobInfo[0]));
		cmbStatus.addItems(PersonStatusType.values());
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		String personId = txtPersonId.getText();
		String birth = pkBirth.getDateStr();
		JobInfo jobInfo = (JobInfo) cmbJob.getSelectedItem();
		PersonStatusType pst = (PersonStatusType) cmbStatus.getSelectedItem();
		
		PersonInfo info = (PersonInfo) this.editData;
		info.setPersonId(personId);
		info.setBirth(birth);
		info.setJobInfo(jobInfo);
		info.setPst(pst);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		
		
		PersonInfo info = (PersonInfo) this.editData;
		txtPersonId.setText(info.getPersonId());
		pkBirth.setDate(info.getBirth());
		cmbJob.setSelectedItem(info.getJobInfo());
		cmbStatus.setSelectedItem(info.getPst());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		PersonInfo info = (PersonInfo) this.editData;
		info.setPst(PersonStatusType.IN);
	}

	protected Class<?> getInfoClass() {
		return PersonInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpPerson.getInstance();
	}

	protected String getEditUITitle() {
		return "员工";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

}
