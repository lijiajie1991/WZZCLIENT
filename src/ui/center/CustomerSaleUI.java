package ui.center;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import bas.customer.CustomerInfo;
import bas.customer.IHttpCustomer;
import bas.material.MaterialInfo;
import bas.measureunit.MeasureUnitInfo;
import bas.person.PersonInfo;
import bean.Info;
import bean.bill.BillInfo;
import bean.bill.IHttpBill;
import bill.paylog.PayLogInfo;
import bill.price.IHttpPrice;
import bill.price.MaterialPriceEntryInfo;
import bill.sale.mutil.IHttpSaleMutil;
import bill.sale.mutil.SaleMutilEntryInfo;
import bill.sale.mutil.SaleMutilInfo;
import card.CardReader;
import common.util.BigDecimalUtil;
import common.util.ContextUtil;
import em.PayType;
import exp.BizException;
import exp.ExpHandle;
import listener.DataChangeListener;
import listener.PayListener;
import listener.TabNameChangeListener;
import listener.TableEditStopedListener;
import listener.event.TblEditStopedEvent;
import myswing.btn.ToolButton;
import myswing.container.LabelContainer;
import myswing.editor.DatePicker;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.msgbox.MsgBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;
import ui.base.PayTypeSelectUI;

public class CustomerSaleUI extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private boolean isLoad = false;
	
	//缓存数据
	protected HashMap<String, MeasureUnitInfo> unitMap = null;  //单位缓存
	protected JFrame ower = null;
	
	protected String tid = null;
	protected ActionListener closeLs = null;
	protected TabNameChangeListener tcLs = null;
	protected SaleMutilInfo editData = null;
	
	//工具栏按钮
	protected JToolBar toolBar = null;
	protected ToolButton btnSave = null;
	protected ToolButton btnAuditTrue = null;
	protected ToolButton btnAuditFalse = null;
	protected ToolButton btnDelete = null;
	protected ToolButton btnTrunc = null;
	protected ToolButton btnClose = null;
	
	//界面控件
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	protected LabelContainer conBizdate = null;
	protected DatePicker pkBizdate = null;
	
	protected JCheckBox chkIsAuditTrue = null;
	
	protected LabelContainer conPerson = null;
	protected MyComboBox cmbPerson = null;
	
	protected LabelContainer conCustomer = null;
	protected MyComboBox cmbCustomer = null;
	
	protected LabelContainer conAmt = null;
	protected FormatInputField txtAmt = null;
	
	protected LabelContainer conFactAmt = null;
	protected FormatInputField txtFactAmt = null;
	
	protected LabelContainer conRemark = null;
	protected FormatInputField txtRemark = null;
	
	protected MyComboBox cmbPayType = null;
	protected JCheckBox chkIsBalance = null;
	
	//分录控件
	protected MyComboBox column_cmbMaterial = null;
	protected MyComboBox column_cmbUnit = null;
	protected FormatInputField column_txtQty = null;
	protected FormatInputField column_txtPrice = null;
	protected FormatInputField column_txtAmt = null;
	protected FormatInputField column_txtFactAmt = null;
	protected FormatInputField column_txtRemark = null;
	protected BeanTable tblEntry = null;
	
	
	public CustomerSaleUI(String tid, JFrame ower, ActionListener closeLs, TabNameChangeListener tcLs) throws Exception {
		super();
		
		this.tid = tid;
		this.closeLs = closeLs;
		this.tcLs = tcLs;
		this.ower = ower;
		this.editData = new SaleMutilInfo();
		
		initUI();
		initToolBar();
		addListener();
	}
	
	protected void initUI() 
	{
		JPanel jp = new JPanel();
		jp.setLayout(null);
		
		chkIsAuditTrue = new JCheckBox("是否已确认");
		
		txtNumber = new FormatInputField();
		txtNumber.setEditable(false);
		conNumber = new LabelContainer(txtNumber, 120, BorderLayout.WEST, true, "编号：");
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		
		pkBizdate = new DatePicker();
		conBizdate = new LabelContainer(pkBizdate, 120, BorderLayout.WEST, true, "销售日期：");
		conBizdate.setBounds(new Rectangle(340, 5, 300, 30));

		cmbPerson = new MyComboBox();
		conPerson = new LabelContainer(cmbPerson, 120, BorderLayout.WEST, true, "销售员：");
		conPerson.setBounds(new Rectangle(675, 5, 300, 30));
		
		cmbCustomer = new MyComboBox();
		conCustomer = new LabelContainer(cmbCustomer, 120, BorderLayout.WEST, true, "客户：");
		conCustomer.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtAmt.setEditable(false);
		conAmt = new LabelContainer(txtAmt, 120, BorderLayout.WEST, true, "产品金额：");
		conAmt.setBounds(new Rectangle(340, 40, 300, 30));
		
		txtFactAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtFactAmt.setEditable(false);
		conFactAmt = new LabelContainer(txtFactAmt, 120, BorderLayout.WEST, true, "收取金额：");
		conFactAmt.setBounds(new Rectangle(675, 40, 300, 30));
		
		txtRemark = new FormatInputField();
		conRemark = new LabelContainer(txtRemark, 120, BorderLayout.WEST, true, "备注：");
		conRemark.setBounds(new Rectangle(5, 75, 970, 30));
		
		cmbPayType = new MyComboBox();
		chkIsBalance = new JCheckBox("是否已结算");
		
		//分录控件初始化
		column_cmbMaterial = new MyComboBox();
		DefaultCellEditor column_material = new DefaultCellEditor(column_cmbMaterial);
		
		column_cmbUnit = new MyComboBox();
		DefaultCellEditor column_unit = new DefaultCellEditor(column_cmbUnit);
		
		column_txtQty = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor column_qty = new DefaultCellEditor(column_txtQty);
		
		column_txtPrice = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor column_price = new DefaultCellEditor(column_txtPrice);
		
		column_txtAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor column_amt = new DefaultCellEditor(column_txtAmt);
		
		column_txtFactAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		DefaultCellEditor column_factAmt = new DefaultCellEditor(column_txtFactAmt);
		
		column_txtRemark = new FormatInputField();
		DefaultCellEditor column_remark = new DefaultCellEditor(column_txtRemark);
		
		tblEntry = new BeanTable();
		LinkedList<ColumnInfo> matColList = new LinkedList<ColumnInfo>(); 
		matColList.addLast(new ColumnInfo("id", "id", 0));
		matColList.addLast(new ColumnInfo("materialinfo", "产品", 100, MaterialInfo.class));
		matColList.addLast(new ColumnInfo("unitinfo", "计量单位", 100, MeasureUnitInfo.class));
		matColList.addLast(new ColumnInfo("price", "单价", 100, BigDecimal.class));
		matColList.addLast(new ColumnInfo("qty", "数量", 100, BigDecimal.class));
		matColList.addLast(new ColumnInfo("amt", "产品金额", 100, BigDecimal.class));
		matColList.addLast(new ColumnInfo("factamt", "实收金额", 100, BigDecimal.class));
		matColList.addLast(new ColumnInfo("remark", "说明", 200, String.class));
		tblEntry.initTable(matColList);
		tblEntry.setColumnEditor("materialinfo", column_material);
		tblEntry.setColumnEditor("unitinfo", column_unit);
		tblEntry.setColumnEditor("price", column_price);
		tblEntry.setColumnEditor("qty", column_qty);
		tblEntry.setColumnEditor("amt", column_amt);
		tblEntry.setColumnEditor("factamt", column_factAmt);
		tblEntry.setColumnEditor("remark", column_remark);
		tblEntry.enableDetailBtn();
		tblEntry.bindClass(SaleMutilEntryInfo.class);
		JPanel jp_tbl = tblEntry.toJpanel();
		jp_tbl.setBounds(new Rectangle(5, 110, 970, 290));
		
		jp.add(conNumber);
		jp.add(conBizdate);
		jp.add(conPerson);
		jp.add(conCustomer);
		jp.add(conAmt);
		jp.add(conFactAmt);
		jp.add(conRemark);
		jp.add(jp_tbl);
		
		this.setLayout(new BorderLayout());
		this.add(jp, BorderLayout.CENTER);
	}
	
	public void setUIContext(HashMap<String, Object> ctx) throws Exception
	{
		this.loadDefault();
		SaleMutilInfo info = (SaleMutilInfo) this.editData;
		if(ctx.containsKey("customer") && ctx.get("customer") != null)
		{
			CustomerInfo cusInfo = (CustomerInfo) ctx.get("customer");
			this.setName(cusInfo.getName());
			info.setCustomerInfo(cusInfo);
		}
		
		LinkedList<Info> perList = (LinkedList<Info>) ctx.get("perList");
		LinkedList<Info> cusList = (LinkedList<Info>) ctx.get("cusList");
		LinkedList<Info> matList = (LinkedList<Info>) ctx.get("matList");
		LinkedList<Info> unitList = (LinkedList<Info>) ctx.get("unitList");
		
		cmbPerson.addItems(perList.toArray(new PersonInfo[0]));
		cmbCustomer.addItems(cusList.toArray(new CustomerInfo[0]));
		column_cmbMaterial.addItems(matList.toArray(new MaterialInfo[0]));
		column_cmbUnit.addItems(unitList.toArray(new MeasureUnitInfo[0]));
		cmbPayType.addItems(PayType.values());
		
		unitMap = new HashMap<String, MeasureUnitInfo>();
		for(Info uInfo : unitList)
			unitMap.put(info.getId(), (MeasureUnitInfo) uInfo);
		
		this.loadFiles();
	}
	
	public void initToolBar() throws Exception
	{
		toolBar = new JToolBar(); 
		
		btnSave = new ToolButton("暂存");
		toolBar.add(btnSave);
		
		btnAuditTrue = new ToolButton("确认");
		toolBar.add(btnAuditTrue);
		
		btnAuditFalse = new ToolButton("改单");
		toolBar.add(btnAuditFalse);
		
		btnDelete = new ToolButton("删除");
		toolBar.add(btnDelete);
		
		btnTrunc = new ToolButton("取整(角)");
		toolBar.add(btnTrunc);
		
		btnClose = new ToolButton("关闭");
		toolBar.add(btnClose);
		
		toolBar.setFloatable(false);
		this.add(toolBar, BorderLayout.PAGE_START);
	}
	
	public void addListener() throws Exception {
		btnTrunc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String txt = btnTrunc.getText();
				BigDecimal amt = txtAmt.getBigDecimalValue();
				if(txt.contains("(角)"))
				{
					amt = amt != null ? amt : BigDecimal.ZERO;
					amt = amt.setScale(1, BigDecimal.ROUND_DOWN);
					
					btnTrunc.setText("取整(元)");
				}
				else if(txt.contains("(元)"))
				{
					amt = amt != null ? amt : BigDecimal.ZERO;
					amt = amt.setScale(0, BigDecimal.ROUND_DOWN);
					
					btnTrunc.setText("取整(十元)");
				}
				else if(txt.contains("(十元)"))
				{
					amt = amt != null ? amt : BigDecimal.ZERO;
					amt = amt.divide(new BigDecimal(10), 0, BigDecimal.ROUND_DOWN);
					amt = amt.multiply(new BigDecimal(10));
					
					btnTrunc.setText("取整(百元)");
				}
				else if(txt.contains("(百元)"))
				{
					amt = amt != null ? amt : BigDecimal.ZERO;
					amt = amt.divide(new BigDecimal(100), 0, BigDecimal.ROUND_DOWN);
					amt = amt.multiply(new BigDecimal(100));
					
					btnTrunc.setText("取整(角)");
				}
				txtFactAmt.setBigDecimalValue(amt);
				
				shareTrunc();
			}
		});
		
		btnClose.addActionListener(closeLs);
		
		pkBizdate.addDataChangeListener(new DataChangeListener() {
			public void DataChangeAction(Object obj) {
				try {
					bizdateChange();
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		cmbCustomer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					customerChangeAction(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		
		tblEntry.addEditeStopedActionListener(new TableEditStopedListener() {
			public void editStopedAction(TblEditStopedEvent e) throws Exception {
				tblEntry_editStoped(e);
			}
		});
		
	}

	protected void storeFiles() throws Exception {
		editData = editData != null ? editData : new SaleMutilInfo();
		this.loadDefault();
		
		PersonInfo pInfo = (PersonInfo) cmbPerson.getSelectedItem();
		CustomerInfo cusInfo = (CustomerInfo) cmbCustomer.getSelectedItem();
		PayType pt = (PayType) cmbPayType.getSelectedItem();
		BigDecimal amt = txtAmt.getBigDecimalValue();
		BigDecimal factamt = txtFactAmt.getBigDecimalValue();
		String remark = txtRemark.getText();
		boolean isBalance = chkIsBalance.isSelected();
		
		SaleMutilInfo info = this.editData;
		info.setNumber(txtNumber.getText());
		info.setBizdate(pkBizdate.getDate());
		info.setIsAuditTrue(chkIsAuditTrue.isSelected());
		info.setPersonInfo(pInfo);
		info.setCustomerInfo(cusInfo);
		info.setPt(pt);
		info.setAmt(amt);
		info.setFactAmt(factamt);
		info.setRemark(remark);
		info.setIsBalance(isBalance);
		
		LinkedList<SaleMutilEntryInfo> entryList = info.getEntryList();
		entryList.clear();
		entryList.addAll((Collection<? extends SaleMutilEntryInfo>) tblEntry.storeFiles());
	}

	protected void loadFiles() throws Exception {
		isLoad = false;
		SaleMutilInfo info = (SaleMutilInfo) this.editData;
		
		txtNumber.setText(info.getNumber());
		pkBizdate.setDate(info.getBizdate());
		chkIsAuditTrue.setSelected(info.getIsAuditTrue());
		cmbPerson.setSelectedItem(info.getPersonInfo());
		cmbCustomer.setSelectedItem(info.getCustomerInfo());
		cmbPayType.setSelectedItem(info.getPt());
		txtAmt.setBigDecimalValue(info.getAmt());
		txtFactAmt.setBigDecimalValue(info.getFactAmt());
		txtRemark.setText(info.getRemark());
		chkIsBalance.setSelected(info.getIsBalance());
		
		tblEntry.loadFiles(info.getEntryList());
		isLoad = true;
		
		setCompentStatus();
	}
	
	protected void loadDefault() throws Exception {
		SaleMutilInfo info = (SaleMutilInfo) this.editData;
		info.setIsBalance(false);
		info.setBizdate(new Date());
		info.setIsAuditTrue(false);
		info.setNumber(IHttpSaleMutil.getInstance().getNewNumber());
		info.setShopId(ContextUtil.getShopId());
	}
	
	protected void tblEntry_editStoped(TblEditStopedEvent e) throws Exception
	{
		if(!isLoad)
			return;
		
		isLoad = false;
		
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		
		if(rowIndex < 0 || colIndex < 0)
			return;
		
		String columnkey = e.getColumnKey();
		Object value = e.getValue();
		
		BigDecimal qty = BigDecimalUtil.getBigDecimal(tblEntry.getValueAt(rowIndex, "qty"));
		if(BigDecimal.ZERO.compareTo(qty) == 0)
		{
			tblEntry.setValueAt(BigDecimal.ONE, rowIndex, "qty");
		}
		
		if("materialinfo".equals(columnkey))
		{
			materialChangeAction(rowIndex, (MaterialInfo) value);
		}
		else if("unitinfo".equals(columnkey))
		{
			unitChangeAction(rowIndex, (MeasureUnitInfo) value);
		}
		else if("price".equals(columnkey))
		{
			priceChangeAction(rowIndex);
		}
		else if("qty".equals(columnkey))
		{
			qtyChangeAction(rowIndex);
		}
		
		isLoad = true;
	}

	protected void bizdateChange() throws Exception
	{
		if(!isLoad)
			return;
		
		reLoadPrice();
	}
	
	protected void customerChangeAction(ItemEvent e) throws Exception
	{
		if(e.getStateChange() != ItemEvent.SELECTED)
			return;
		
		if(!isLoad)
			return;
		
		CustomerInfo cusInfo = (CustomerInfo) cmbCustomer.getSelectedItem();
		if(cusInfo != null)
			tcLs.actionChange(tid, cusInfo.getName());
		
		reLoadPrice();
	}
	
	protected void reLoadPrice() throws Exception
	{
		int rowCount = tblEntry.getRowCount();
		for(int rowIndex = 0; rowIndex < rowCount; rowIndex++)
		{
			setPrice(rowIndex);
			priceChangeAction(rowIndex);
		}
	}
	
	protected void materialChangeAction(int rowIndex, MaterialInfo mInfo) throws Exception
	{
		if(mInfo != null && mInfo.getUnitInfo() != null)
		{
			MeasureUnitInfo unitInfo = mInfo.getUnitInfo();
			if(unitInfo.getId() != null && unitMap.containsKey(unitInfo.getId()))
			{
				unitInfo = unitMap.get(unitInfo.getId());
				tblEntry.setValueAt(unitInfo, rowIndex, "unitinfo");
			}
			
			setPrice(rowIndex);
			priceChangeAction(rowIndex);
		}
	}
	
	protected void unitChangeAction(int rowIndex, MeasureUnitInfo unitInfo) throws Exception
	{
		setPrice(rowIndex);
		priceChangeAction(rowIndex);
	}
	
	protected void qtyChangeAction(int rowIndex) throws Exception
	{
		reCalAmt(rowIndex);
	}
	
	protected void priceChangeAction(int rowIndex) throws Exception
	{
		reCalAmt(rowIndex);
	}
	
	protected void reCalAmt(int rowIndex)
	{
		BigDecimal qty = BigDecimalUtil.getBigDecimal(tblEntry.getValueAt(rowIndex, "qty"));
		BigDecimal price = BigDecimalUtil.getBigDecimal(tblEntry.getValueAt(rowIndex, "price"));
		BigDecimal amt = qty.multiply(price).setScale(2, BigDecimal.ROUND_DOWN);
		tblEntry.setValueAt(amt, rowIndex, "amt");
		tblEntry.setValueAt(amt, rowIndex, "factamt");
		
		BigDecimal totalAmt = BigDecimal.ZERO;
		int rowCount = tblEntry.getRowCount();
		for(rowIndex = 0; rowIndex < rowCount; rowIndex++)
		{
			amt = BigDecimalUtil.getBigDecimal(tblEntry.getValueAt(rowIndex, "amt"));
			tblEntry.setValueAt(amt, rowIndex, "factamt");
			
			totalAmt = totalAmt.add(amt);
		}
		txtAmt.setBigDecimalValue(totalAmt);
		txtFactAmt.setBigDecimalValue(totalAmt);
	}
	
	private void setPrice(int rowIndex) throws Exception
	{
		MaterialInfo mInfo = (MaterialInfo)tblEntry.getValueAt(rowIndex, "materialinfo");
		MeasureUnitInfo unitInfo = (MeasureUnitInfo)tblEntry.getValueAt(rowIndex, "unitinfo");
		String mId = mInfo != null ? mInfo.getId() : null;
		String unitId = unitInfo != null ? unitInfo.getId() : null;
		unitInfo = unitId != null && unitMap.containsKey(unitId) ? unitMap.get(unitId) : unitInfo;
		BigDecimal unitCoe = unitInfo != null ? unitInfo.getCoe() : null;
		unitCoe = unitCoe != null ? unitCoe : BigDecimal.ONE;
		
		tblEntry.setValueAt(BigDecimal.ZERO, rowIndex, "price");
		tblEntry.setCellEditable(rowIndex, "price", true);
		
		String dateStr = pkBizdate.getDateStr();
		if(mId != null && !"".equals(mId) && !"".equals(dateStr))
		{
			CustomerInfo cusInfo = (CustomerInfo) cmbCustomer.getSelectedItem();
			String cusId = cusInfo != null ? cusInfo.getId() : null;
			
			MaterialPriceEntryInfo entryInfo = null;
			if(cusId != null && !"".equals(cusId))
				entryInfo = IHttpPrice.getInstance().getMaterialPrice(cusId, mId, dateStr);
			else
				entryInfo = IHttpPrice.getInstance().getMaterialPrice(mId, dateStr);
			
			if(entryInfo != null)
			{			
				MeasureUnitInfo uInfo = entryInfo.getUnitInfo();
				uInfo = uInfo != null && unitMap.containsKey(uInfo.getId()) && unitMap.get(uInfo.getId()) != null ? unitMap.get(uInfo.getId()) : uInfo;
				
				BigDecimal price = entryInfo.getPrice();
				price = price != null ? price : BigDecimal.ONE;
				
				BigDecimal uCoe = uInfo != null ? uInfo.getCoe() : BigDecimal.ONE;
				uCoe = uCoe != null ? uCoe : BigDecimal.ONE;
				
				price.multiply(unitCoe).divide(uCoe, 2, BigDecimal.ROUND_HALF_UP);
				price = price.setScale(2, BigDecimal.ROUND_DOWN);
				tblEntry.setValueAt(price, rowIndex, "price");
				tblEntry.setCellEditable(rowIndex, "price", entryInfo.getIsFloat());
			}
			else
			{
				MeasureUnitInfo uInfo = mInfo.getUnitInfo();
				uInfo = uInfo != null && unitMap.containsKey(uInfo.getId()) && unitMap.get(uInfo.getId()) != null ? unitMap.get(uInfo.getId()) : uInfo;
				
				BigDecimal price = mInfo.getPrice();
				price = price != null ? price : BigDecimal.ONE;
				
				BigDecimal uCoe = uInfo != null ? uInfo.getCoe() : BigDecimal.ONE;
				uCoe = uCoe != null ? uCoe : BigDecimal.ONE;
				
				price.multiply(unitCoe).divide(uCoe, 2, BigDecimal.ROUND_HALF_UP);
				price = price.setScale(2, BigDecimal.ROUND_DOWN);
				tblEntry.setValueAt(price, rowIndex, "price");
				tblEntry.setCellEditable(rowIndex, "price", true);
			}
		}
	}
	
	protected void shareTrunc()
	{
		BigDecimal totalAmt = txtAmt.getBigDecimalValue();
		BigDecimal totalFactAmt = txtFactAmt.getBigDecimalValue();
		BigDecimal totalDiff = totalAmt.subtract(totalFactAmt);
		BigDecimal totalDiffLeft = totalDiff;
		
		int rowCount = tblEntry.getRowCount();
		for(int rowIndex = 0; rowIndex < rowCount; rowIndex++)
		{
			BigDecimal amt = BigDecimalUtil.getBigDecimal(tblEntry.getValueAt(rowIndex, "amt")).setScale(2, BigDecimal.ROUND_DOWN);
			BigDecimal diff = amt.divide(totalAmt, 10, BigDecimal.ROUND_HALF_UP).multiply(totalDiff).setScale(2, BigDecimal.ROUND_DOWN);
			if(rowIndex == rowCount - 1)
			{
				diff = totalDiffLeft;
			}
			totalDiffLeft = totalDiffLeft.subtract(diff);
			
			BigDecimal factAmt = amt.subtract(diff).setScale(2, BigDecimal.ROUND_DOWN);
			tblEntry.setValueAt(factAmt, rowIndex, "factamt");
		}
	}
	

	protected void actionAuditTrue(ActionEvent e) throws Exception {
		PayType pt = PayType.Cash;
		CustomerInfo cusInfo = (CustomerInfo) cmbCustomer.getSelectedItem();
		if(cusInfo != null && cusInfo.getId() != null && !"".equals(cusInfo.getId()))
			pt = PayType.Vip;
		
		BigDecimal amt = txtFactAmt.getBigDecimalValue();
		PayLogInfo pl = PayTypeSelectUI.createLog(amt);
		
		new PayTypeSelectUI(ower, pt, null, pl, new PayListener() {
			public void success(String logId, PayType pt){
				actionPay(pt);
			}
			public void fail(String logId, PayType pt){
			}
			
			public boolean isShowSuccess() {
				return false;
			}
			public String getSuccessMsg() {
				return null;
			}
			public boolean isShowFail() {
				return true;
			}
			public String getFailMsg(String msg) {
				return "支付失败！失败原因：" + msg;
			}
		});
	}
	
	protected void actionPay(PayType pt) 
	{
		try {
			cmbPayType.setSelectedItem(pt);
			
			this.storeFiles();
			this.verifyAuditTrue();
			SaleMutilInfo info = (SaleMutilInfo) this.editData;
			IHttpBill http = IHttpSaleMutil.getInstance();
			String id = http.setAuditTrue(info);
			this.editData = (SaleMutilInfo) http.getInfo(id);
			this.loadFiles();
		} catch (Exception e) {
			e.printStackTrace();
			new ExpHandle(ower, e);
		}
	}

	protected void verifyAuditTrue() throws Exception {
		SaleMutilInfo info = (SaleMutilInfo) this.editData;
		if(info.getPt() == PayType.Vip)
		{
			CustomerInfo cusInfo = info.getCustomerInfo();
			if(cusInfo == null || cusInfo.getId() == null)
				throw new BizException(PayType.Vip.getName() + "时， 客户不能为空！");
			
			cusInfo = (CustomerInfo) IHttpCustomer.getInstance().getInfo(cusInfo.getId());
			BigDecimal balance = cusInfo.getBalance();
			if(balance.compareTo(info.getFactAmt()) < 0)
				throw new BizException("客户余额不足！");
		}
	}

	protected void setCompentStatus() {
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		
		txtNumber.setEnabled(!flag);
		pkBizdate.setEnable(!flag);
		
		btnAuditTrue.setEnabled(!flag);
		btnAuditFalse.setEnabled(flag);
		btnDelete.setEnabled(!flag);
		
		btnTrunc.setEnabled(!flag);
		cmbPerson.setEnabled(!flag);
		cmbCustomer.setEnabled(!flag);
		cmbPayType.setEnabled(!flag);
		txtAmt.setEnabled(!flag);
		txtFactAmt.setEnabled(!flag);
		txtRemark.setEnabled(!flag);
		tblEntry.setEnable(!flag);
	}

	protected void actionClose(ActionEvent e) throws Exception {
		CardReader.removeListener(this.getClass().getName());
		closeLs.actionPerformed(e);
	}
	
	protected void setCustomer(CustomerInfo cusInfo)
	{
		cmbCustomer.setSelectedItem(cusInfo);
	}
	
	protected CustomerInfo getCustomerInfo()
	{
		CustomerInfo cusInfo = (CustomerInfo) cmbCustomer.getSelectedItem();
		return cusInfo;
	}
}
