package bill.price;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;

import bas.material.IHttpMaterial;
import bas.material.MaterialInfo;
import bas.measureunit.IHttpMeasureUnit;
import bas.measureunit.MeasureUnitInfo;
import bas.project.IHttpProject;
import bas.project.ProjectInfo;
import bean.IHttp;
import bean.Info;
import bean.bill.BillEditUI;
import bean.bill.BillInfo;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import common.util.ContextUtil;
import common.util.DateTimeUtil;
import em.CompareType;
import em.SortType;
import myswing.container.LabelContainer;
import myswing.editor.DatePicker;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;

public class PriceEditUI extends BillEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LinkedList<Info> proList = null;
	protected LinkedList<Info> matList = null;
	protected LinkedList<Info> unitList = null;
	
	protected MyComboBox column_cmbProject = null;
	protected FormatInputField colunm_txtProPrice = null;
	protected JCheckBox colunm_chkProIsFloat = null;
	protected FormatInputField colunm_txtProRemark = null;
	
	protected MyComboBox column_cmbMaterial = null;
	protected MyComboBox column_cmbUnit = null;
	protected FormatInputField colunm_txtMatPrice = null;
	protected JCheckBox colunm_chkMatIsFloat = null;
	protected FormatInputField colunm_txtMatRemark = null;
	
	protected LabelContainer conDateFrom = null;
	protected DatePicker pkDateFrom = null;
	
	protected LabelContainer conDateTo = null;
	protected DatePicker pkDateTo = null;
	
	protected JTabbedPane tab = null;
	protected BeanTable tblProjectEntry = null;
	protected BeanTable tblMaterialEntry = null;	
	
	public PriceEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 550);
		
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(3000, 5, 300, 30));
		
		pkDateFrom = new DatePicker();
		conDateFrom = new LabelContainer(pkDateFrom, 120, BorderLayout.WEST, true, "开始日期：");
		conDateFrom.setBounds(new Rectangle(5, 40, 300, 30));
		
		pkDateTo = new DatePicker();
		pkDateTo.setEnable(false);
		conDateTo = new LabelContainer(pkDateTo, 120, BorderLayout.WEST, true, "结束日期：");
		conDateTo.setBounds(new Rectangle(340, 40, 300, 30));
		
		column_cmbProject = new MyComboBox();
		DefaultCellEditor column_project = new DefaultCellEditor(column_cmbProject);
		
		colunm_txtProPrice = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor colunm_proPrice = new DefaultCellEditor(colunm_txtProPrice);
		
		colunm_chkProIsFloat = new JCheckBox();
		DefaultCellEditor colunm_proIsFloat = new DefaultCellEditor(colunm_chkProIsFloat);
		
		colunm_txtProRemark = new FormatInputField();
		DefaultCellEditor colunm_proRemark = new DefaultCellEditor(colunm_txtProRemark);
		
		tblProjectEntry = new BeanTable();
		LinkedList<ColumnInfo> proColList = new LinkedList<ColumnInfo>(); 
		proColList.addLast(new ColumnInfo("id", "id", 0));
		proColList.addLast(new ColumnInfo("projectinfo", "项目", 100, ProjectInfo.class));
		proColList.addLast(new ColumnInfo("price", "单价", 100, BigDecimal.class));
		proColList.addLast(new ColumnInfo("isfloat", "是否浮动", 100, Boolean.class));
		proColList.addLast(new ColumnInfo("remark", "说明", 200, String.class));
		tblProjectEntry.initTable(proColList);
		tblProjectEntry.setColumnEditor("projectinfo", column_project);
		tblProjectEntry.setColumnEditor("price", colunm_proPrice);
		tblProjectEntry.setColumnEditor("isfloat", colunm_proIsFloat);
		tblProjectEntry.setColumnEditor("remark", colunm_proRemark);
		tblProjectEntry.enableDetailBtn();
		tblProjectEntry.bindClass(ProjectPriceEntryInfo.class);
		
		
		
		column_cmbMaterial = new MyComboBox();
		DefaultCellEditor column_Material = new DefaultCellEditor(column_cmbMaterial);
		
		column_cmbUnit = new MyComboBox();
		DefaultCellEditor column_Unit = new DefaultCellEditor(column_cmbUnit);
		
		colunm_txtMatPrice = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor colunm_matPrice = new DefaultCellEditor(colunm_txtMatPrice);
		
		colunm_chkMatIsFloat = new JCheckBox();
		DefaultCellEditor colunm_matIsFloat = new DefaultCellEditor(colunm_chkMatIsFloat);
		
		colunm_txtMatRemark = new FormatInputField();
		DefaultCellEditor colunm_matRemark = new DefaultCellEditor(colunm_txtMatRemark);
		
		
		tblMaterialEntry = new BeanTable();
		LinkedList<ColumnInfo> matColList = new LinkedList<ColumnInfo>(); 
		matColList.addLast(new ColumnInfo("id", "id", 0));
		matColList.addLast(new ColumnInfo("materialinfo", "产品", 100, MaterialInfo.class));
		matColList.addLast(new ColumnInfo("unitinfo", "计量单位", 100, MeasureUnitInfo.class));
		matColList.addLast(new ColumnInfo("price", "单价", 100, BigDecimal.class));
		matColList.addLast(new ColumnInfo("isfloat", "是否浮动", 100, Boolean.class));
		matColList.addLast(new ColumnInfo("remark", "说明", 200, String.class));
		tblMaterialEntry.initTable(matColList);
		tblMaterialEntry.setColumnEditor("materialinfo", column_Material);
		tblMaterialEntry.setColumnEditor("unitinfo", column_Unit);
		tblMaterialEntry.setColumnEditor("price", colunm_matPrice);
		tblMaterialEntry.setColumnEditor("isfloat", colunm_matIsFloat);
		tblMaterialEntry.setColumnEditor("remark", colunm_matRemark);
		tblMaterialEntry.enableDetailBtn();
		tblMaterialEntry.bindClass(MaterialPriceEntryInfo.class);
		
		
		tab = new JTabbedPane(JTabbedPane.TOP);
		tab.setBounds(new Rectangle(5, 75, 620, 400));
		tab.add("项目单价", tblProjectEntry);
		tab.add("产品单价", tblMaterialEntry);
		
		jp.add(conDateFrom);
		jp.add(conDateTo);
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
		
		unitList = IHttpMeasureUnit.getInstance().getInfoList(new FilterInfo(), order);
		column_cmbUnit.addItems(unitList.toArray(new MeasureUnitInfo[0]));
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		PriceInfo info = (PriceInfo) this.editData;
		
		Date df = pkDateFrom.getDate();
		Date dt = pkDateTo.getDate();
		info.setDateFrom(df);
		info.setDateTo(dt);
		
		LinkedList<ProjectPriceEntryInfo> pList = info.getProjectList();
		pList.clear();
		pList.addAll((Collection<? extends ProjectPriceEntryInfo>) tblProjectEntry.storeFiles());
		
		LinkedList<MaterialPriceEntryInfo> mList = info.getMaterialList();
		mList.clear();
		mList.addAll((Collection<? extends MaterialPriceEntryInfo>) tblMaterialEntry.storeFiles());
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		PriceInfo info = (PriceInfo) this.editData;
		pkDateFrom.setDate(info.getDateFrom());
		pkDateTo.setDate(info.getDateTo());
		
		tblProjectEntry.loadFiles(info.getProjectList());
		tblMaterialEntry.loadFiles(info.getMaterialList());
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		PriceInfo info = (PriceInfo) this.editData;
		info.setDateTo(DateTimeUtil.parseStr("2030-01-01", DateTimeUtil.dayFormat));
		info.setDateFrom(DateTimeUtil.getDateAfterDays(new Date(), 1));
		
		if(proList != null && !proList.isEmpty())
		{
			LinkedList<ProjectPriceEntryInfo> pList = info.getProjectList();
			int size = proList.size();
			for(int i = 0; i < size; i++)
			{
				ProjectInfo proInfo = (ProjectInfo) proList.get(i);
				ProjectPriceEntryInfo entryInfo = new ProjectPriceEntryInfo();
				entryInfo.setProjectInfo(proInfo);
				entryInfo.setPrice(proInfo.getPrice());
				entryInfo.setIsFloat(false);
				pList.addLast(entryInfo);
			}
		}
		
		if(matList != null && !matList.isEmpty())
		{
			LinkedList<MaterialPriceEntryInfo> mList = info.getMaterialList();
			int size = matList.size();
			for(int i = 0; i < size; i++)
			{
				MaterialInfo mInfo = (MaterialInfo) matList.get(i);
				MeasureUnitInfo unitInfo = getUnitInfo(mInfo.getUnitInfo().getId());
				MaterialPriceEntryInfo entryInfo = new MaterialPriceEntryInfo();
				entryInfo.setMaterialInfo(mInfo);
				entryInfo.setUnitInfo(unitInfo);
				entryInfo.setPrice(mInfo.getPrice());
				entryInfo.setIsFloat(false);
				mList.addLast(entryInfo);
			}
		}
	}

	protected Class<?> getInfoClass() {
		return PriceInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpPrice.getInstance();
	}

	protected String getEditUITitle() {
		return "价目表";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}
	
	protected void setCompentStatus() {
		super.setCompentStatus();
		BillInfo info = (BillInfo) this.editData;
		boolean flag = !info.getIsAuditTrue();
		pkDateFrom.setEnable(flag);
		pkDateTo.setEnable(flag);
		tblProjectEntry.setEnable(flag);
		tblMaterialEntry.setEnable(flag);
	}

	private MeasureUnitInfo getUnitInfo(String id)
	{
		id = id != null ? id : "";
		int size = unitList.size();
		for(int i = 0; i < size; i++)
		{
			Info info = unitList.get(i);
			if(id.equals(info.getId()))
				return (MeasureUnitInfo) info;
		}
		return null;
	}
}
