package bas.project;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.LinkedList;

import javax.swing.JCheckBox;

import bas.percent.IHttpPercent;
import bas.percent.PercentInfo;
import bas.project.group.IHttpProjectGroup;
import bas.project.group.ProjectGroupInfo;
import bean.IHttp;
import bean.Info;
import bean.base.BaseEditUI;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.SortType;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;

public class ProjectEditUI extends BaseEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conType = null;
	protected MyComboBox cmbType = null;
	
	protected LabelContainer conPrice = null;
	protected FormatInputField txtPrice = null;
	
	protected LabelContainer conPc = null;
	protected MyComboBox cmbPc = null;
	
	protected LabelContainer conRemark = null;
	protected FormatInputField txtRemark = null;
	
	protected JCheckBox chkIsEnable = null;
	
	public ProjectEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(690, 200);
		
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conName.setBounds(new Rectangle(340, 5, 300, 30));
		
		cmbType = new MyComboBox();
		conType = new LabelContainer(cmbType, 120, BorderLayout.WEST, true, "项目类型：");
		conType.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtPrice = new FormatInputField(FormatInputField.DT_DOUBLE);
		conPrice = new LabelContainer(txtPrice, 120, BorderLayout.WEST, true, "默认单价：");
		conPrice.setBounds(new Rectangle(340, 40, 300, 30));
		
		cmbPc = new MyComboBox();
		conPc = new LabelContainer(cmbPc, 120, BorderLayout.WEST, true, "提成比例：");
		conPc.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtRemark = new FormatInputField();
		conRemark = new LabelContainer(txtRemark, 120, BorderLayout.WEST, true, "备注：");
		conRemark.setBounds(new Rectangle(340, 75, 300, 30));
		
		chkIsEnable = new JCheckBox("是否在用");
		chkIsEnable.setBounds(new Rectangle(5, 110, 300, 30));
		
		jp.add(conType);
		jp.add(conPrice);
		jp.add(conPc);
		jp.add(conRemark);
		jp.add(chkIsEnable);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		FilterInfo ptfilter = new FilterInfo();
		ptfilter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		ptfilter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		ptfilter.setMkr("#0 or #1");
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		LinkedList<Info> ptrList = IHttpProjectGroup.getInstance().getInfoList(ptfilter, order);
		cmbType.addItems(ptrList.toArray(new ProjectGroupInfo[0]));
		
		FilterInfo pcfilter = new FilterInfo();
		pcfilter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		pcfilter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		pcfilter.setMkr("#0 or #1");
		LinkedList<Info> pcList = IHttpPercent.getInstance().getInfoList(pcfilter, order);
		cmbPc.addItems(pcList.toArray(new PercentInfo[0]));
		
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		
		ProjectGroupInfo pt = (ProjectGroupInfo) cmbType.getSelectedItem();
		PercentInfo pc = (PercentInfo) cmbPc.getSelectedItem();
		BigDecimal price = txtPrice.getBigDecimalValue();
		boolean isEnable = chkIsEnable.isSelected();
		String remark = txtRemark.getText();
		
		ProjectInfo info = (ProjectInfo) this.editData;
		info.setPtInfo(pt);
		info.setPrice(price);
		info.setPcInfo(pc);
		info.setIsEnable(isEnable);
		info.setRemark(remark);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		
		ProjectInfo info = (ProjectInfo) this.editData;
		cmbType.setSelectedItem(info.getPtInfo());
		cmbPc.setSelectedItem(info.getPcInfo());
		txtPrice.setBigDecimalValue(info.getPrice());
		chkIsEnable.setSelected(info.getIsEnable());
		txtRemark.setText(info.getRemark());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		ProjectInfo info = (ProjectInfo) this.editData;
		info.setIsEnable(true);
	}

	protected Class<?> getInfoClass() {
		return ProjectInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpProject.getInstance();
	}

	protected String getEditUITitle() {
		return "服务项目";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

}
