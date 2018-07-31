package bas.person;

import bas.job.JobInfo;
import bean.base.BaseInfo;
import common.db.BeanTableInfo;
import em.PersonStatusType;

public class PersonInfo extends BaseInfo{
	private static final long serialVersionUID = 1L;

	//身份证
	protected String personId = null;
	//生日
	protected String birth = null;
	//手机号码
	protected String phone = null;
	//职位
	protected JobInfo jobInfo = null;
	
	protected PersonStatusType pst = PersonStatusType.IN;

	public PersonInfo()
	{
		super();
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public JobInfo getJobInfo() {
		return jobInfo;
	}

	public void setJobInfo(JobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}
	
	public PersonStatusType getPst() {
		return pst;
	}

	public void setPst(PersonStatusType pst) {
		this.pst = pst;
	}

	/**
	  * @功能描述
	  * @作者 黎嘉杰 
	  * @日期 2016年9月24日 下午3:16:40 
	  * @参数 
	  * @返回
	  */
	public BeanTableInfo getBT() throws Exception {

		BeanTableInfo bt = new BeanTableInfo();
		bt.setClsName(PersonInfo.class.getName());
		bt.setTableName("base_person");
		bt.setPk("0007");
		return bt;
	
	}
}
