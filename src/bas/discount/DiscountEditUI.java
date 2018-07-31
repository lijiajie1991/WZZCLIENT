package bas.discount;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;

import bas.material.IHttpMaterial;
import bas.material.MaterialInfo;
import bas.project.IHttpProject;
import bas.project.ProjectInfo;
import bas.vip.IHttpVip;
import bas.vip.VipInfo;
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
import exp.BizException;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;

public class DiscountEditUI extends BaseEditUI{
	private static final long serialVersionUID = 1L;
	
	LinkedList<Info> proList = null;
	LinkedList<Info> matList = null;
	
	protected MyComboBox column_cmbProject = null;
	protected FormatInputField colunm_txtProDis = null;
	protected FormatInputField colunm_txtProRemark = null;
	
	protected MyComboBox column_cmbMaterial = null;
	protected FormatInputField colunm_txtMatDis = null;
	protected FormatInputField colunm_txtMatRemark = null;
	
	protected LabelContainer conVip = null;
	protected MyComboBox cmbVip = null;
	
	protected JCheckBox chkIsEnable = null;
	
	protected JTabbedPane tab = null;
	protected BeanTable tblProjectEntry = null;
	protected BeanTable tblMaterialEntry = null;
	
	public DiscountEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 550);
		
		conName.setBounds(new Rectangle(5, 5, 300, 30));
		conName.setBounds(new Rectangle(340, 5, 300, 30));
		
		cmbVip = new MyComboBox();
		conVip = new LabelContainer(cmbVip, 120, BorderLayout.WEST, true, "会员类型：");
		conVip.setBounds(new Rectangle(5, 40, 300, 30));
		
		chkIsEnable = new JCheckBox("是否生效");
		chkIsEnable.setBounds(new Rectangle(340, 40, 300, 30));
		
		column_cmbProject = new MyComboBox();
		DefaultCellEditor column_project = new DefaultCellEditor(column_cmbProject);
		
		colunm_txtProDis = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor colunm_proDis = new DefaultCellEditor(colunm_txtProDis);
		
		colunm_txtProRemark = new FormatInputField();
		DefaultCellEditor colunm_proRemark = new DefaultCellEditor(colunm_txtProRemark);
		
		tblProjectEntry = new BeanTable();
		LinkedList<ColumnInfo> proColList = new LinkedList<ColumnInfo>(); 
		proColList.addLast(new ColumnInfo("id", "id", 0));
		proColList.addLast(new ColumnInfo("projectinfo", "项目", 100, ProjectInfo.class));
		proColList.addLast(new ColumnInfo("discount", "折扣%", 100, BigDecimal.class));
		proColList.addLast(new ColumnInfo("remark", "说明", 200, String.class));
		tblProjectEntry.initTable(proColList);
		tblProjectEntry.setColumnEditor("projectinfo", column_project);
		tblProjectEntry.setColumnEditor("discount", colunm_proDis);
		tblProjectEntry.setColumnEditor("remark", colunm_proRemark);
		tblProjectEntry.enableDetailBtn();
		tblProjectEntry.bindClass(ProjectDiscountEntryInfo.class);
		
		
		
		column_cmbMaterial = new MyComboBox();
		DefaultCellEditor column_Material = new DefaultCellEditor(column_cmbMaterial);
		
		colunm_txtMatDis = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor colunm_matDis = new DefaultCellEditor(colunm_txtMatDis);
		
		colunm_txtMatRemark = new FormatInputField();
		DefaultCellEditor colunm_matRemark = new DefaultCellEditor(colunm_txtMatRemark);
		
		
		tblMaterialEntry = new BeanTable();
		LinkedList<ColumnInfo> matColList = new LinkedList<ColumnInfo>(); 
		matColList.addLast(new ColumnInfo("id", "id", 0));
		matColList.addLast(new ColumnInfo("materialinfo", "产品", 100, MaterialInfo.class));
		matColList.addLast(new ColumnInfo("discount", "折扣%", 100, BigDecimal.class));
		matColList.addLast(new ColumnInfo("remark", "说明", 200, String.class));
		tblMaterialEntry.initTable(matColList);
		tblMaterialEntry.setColumnEditor("materialinfo", column_Material);
		tblMaterialEntry.setColumnEditor("discount", colunm_matDis);
		tblMaterialEntry.setColumnEditor("remark", colunm_matRemark);
		tblMaterialEntry.enableDetailBtn();
		tblMaterialEntry.bindClass(MaterialDiscountEntryInfo.class);
		
		
		tab = new JTabbedPane(JTabbedPane.TOP);
		tab.setBounds(new Rectangle(5, 75, 620, 400));
		tab.add("项目折扣", tblProjectEntry.toJpanel());
		tab.add("产品折扣", tblMaterialEntry.toJpanel());
		
		jp.add(conVip);
		jp.add(chkIsEnable);
		jp.add(tab);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		filter.setMkr("#0 or #1");
		
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		
		proList = IHttpProject.getInstance().getInfoList(filter, order);
		column_cmbProject.addItems(proList.toArray(new ProjectInfo[0]));
		
		matList = IHttpMaterial.getInstance().getInfoList(filter, order);
		column_cmbMaterial.addItems(matList.toArray(new MaterialInfo[0]));
		
		LinkedList<Info> vipList = IHttpVip.getInstance().getInfoList(filter, order);
		cmbVip.addItems(vipList.toArray(new VipInfo[0]));
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		DiscountInfo info = (DiscountInfo) this.editData;
		
		VipInfo vipInfo = (VipInfo) cmbVip.getSelectedItem();
		boolean isEnable = chkIsEnable.isSelected();
		
		info.setVipInfo(vipInfo);
		info.setIsEnable(isEnable);
		
		LinkedList<ProjectDiscountEntryInfo> infoProList = info.getProList();
		infoProList.clear();
		infoProList.addAll((Collection<? extends ProjectDiscountEntryInfo>) tblProjectEntry.storeFiles());
		
		LinkedList<MaterialDiscountEntryInfo> infoMatList = info.getMatList();
		infoMatList.clear();
		infoMatList.addAll((Collection<? extends MaterialDiscountEntryInfo>) tblMaterialEntry.storeFiles());
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		DiscountInfo info = (DiscountInfo) this.editData;
		cmbVip.setSelectedItem(info.getVipInfo());
		chkIsEnable.setSelected(info.getIsEnable());
		tblProjectEntry.loadFiles(info.getProList());
		tblMaterialEntry.loadFiles(info.getMatList());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		
		DiscountInfo info = (DiscountInfo) this.editData;
		info.setIsEnable(true);

		if(proList != null && !proList.isEmpty())
		{
			LinkedList<ProjectDiscountEntryInfo> infoProList = info.getProList();
			int size = proList.size();
			for(int i = 0; i < size; i++)
			{
				ProjectDiscountEntryInfo entryInfo = new ProjectDiscountEntryInfo();
				entryInfo.setProjectInfo((ProjectInfo) proList.get(i));
				entryInfo.setDiscount(new BigDecimal(100));
				infoProList.addLast(entryInfo);
			}
		}
		
		if(matList != null && !matList.isEmpty())
		{
			LinkedList<MaterialDiscountEntryInfo> infoMatList = info.getMatList();
			int size = matList.size();
			for(int i = 0; i < size; i++)
			{
				MaterialDiscountEntryInfo entryInfo = new MaterialDiscountEntryInfo();
				entryInfo.setMaterialInfo((MaterialInfo) matList.get(i));
				entryInfo.setDiscount(new BigDecimal(100));
				infoMatList.addLast(entryInfo);
			}
		}
	}

	protected Class<?> getInfoClass() {
		return DiscountInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpDiscount.getInstance();
	}

	protected String getEditUITitle() {
		return "折扣策略";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

	protected void verifyInput() throws Exception {
		super.verifyInput();
		DiscountInfo info = (DiscountInfo) this.editData;
		LinkedList<ProjectDiscountEntryInfo> infoProList = info.getProList();
		int proSize = infoProList.size();
		for(int i = 0; i < proSize; i++)
		{
			ProjectDiscountEntryInfo entryInfo = infoProList.get(i);
			BigDecimal val = entryInfo.getDiscount();
			val = val != null ? val : new BigDecimal(100);
			if(val.compareTo(new BigDecimal(100)) > 0)
				throw new BizException("项目折扣：第" + (i + 1) + "行， 折扣不能大于100");
			
			if(val.compareTo(BigDecimal.ZERO) < 0)
				throw new BizException("项目折扣：第" + (i + 1) + "行， 折扣不能小于0");
		}
		
		LinkedList<MaterialDiscountEntryInfo> infoMatList = info.getMatList();
		int matSize = infoMatList.size();
		for(int i = 0; i < matSize; i++)
		{
			MaterialDiscountEntryInfo entryInfo = infoMatList.get(i);
			BigDecimal val = entryInfo.getDiscount();
			val = val != null ? val : new BigDecimal(100);
			if(val.compareTo(new BigDecimal(100)) > 0)
				throw new BizException("产品折扣：第" + (i + 1) + "行， 折扣不能大于100");
			
			if(val.compareTo(BigDecimal.ZERO) < 0)
				throw new BizException("产品折扣：第" + (i + 1) + "行， 折扣不能小于0");
		}
	}
}
