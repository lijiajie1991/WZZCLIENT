package bill.sale.mutil;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PageFormat;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import barcode.BarcodeScanner;
import bas.customer.CustomerInfo;
import bas.customer.IHttpCustomer;
import bas.material.IHttpMaterial;
import bas.material.MaterialInfo;
import bas.measureunit.MeasureUnitInfo;
import bas.person.PersonInfo;
import bas.sys.shop.IHttpShop;
import bas.sys.shop.ShopInfo;
import bas.sys.shopparam.ShopParamInfo;
import bean.IHttp;
import bean.Info;
import bean.bill.BillEditUI;
import bean.bill.BillInfo;
import bill.paylog.PayLogInfo;
import bill.price.IHttpPrice;
import bill.price.MaterialPriceEntryInfo;
import cache.BaseData;
import cache.ParamData;
import card.CardReader;
import common.util.BigDecimalUtil;
import common.util.DateTimeUtil;
import em.PayType;
import exp.BizException;
import exp.ExpHandle;
import listener.BarcodeListener;
import listener.CardNumberListener;
import listener.DataChangeListener;
import listener.PayListener;
import listener.TableEditStopedListener;
import listener.event.TblEditStopedEvent;
import myswing.btn.ToolButton;
import myswing.container.LabelContainer;
import myswing.editor.AutoFilterComboBox;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;
import myswing.msgbox.MsgBox;
import myswing.table.BeanTable;
import myswing.table.ColumnInfo;
import ui.base.PayTypeSelectUI;

public class SaleMutilEditUI extends BillEditUI implements BarcodeListener{
	private static final long serialVersionUID = 1L;
	
	private boolean isLoad = false;
	private boolean isWaitBarcode = false;
	
	//缓存数据
	protected HashMap<String, MeasureUnitInfo> unitMap = null;  //单位缓存
	
	//工具栏按钮
	protected ToolButton btnTrunc = null;
	
	//界面控件
	protected LabelContainer conBarCode = null;
	protected FormatInputField txtBarCode = null;
	
	protected LabelContainer conPerson = null;
	protected MyComboBox cmbPerson = null;
	
	protected LabelContainer conCustomer = null;
	protected AutoFilterComboBox<CustomerInfo> cmbCustomer = null;
	
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
	
	public SaleMutilEditUI()
	{
		super();
		
		unitMap = new HashMap<String, MeasureUnitInfo>();
	}
	
	public void initUI(int width, int height) {
		super.initUI(650, 510);
		
		txtNumber.setEditable(true);
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		conBizdate.setBounds(new Rectangle(340, 5, 300, 30));
		conBizdate.setLabelText("销售日期");
		
		cmbPerson = new MyComboBox();
		conPerson = new LabelContainer(cmbPerson, 120, BorderLayout.WEST, true, "销售员：");
		conPerson.setBounds(new Rectangle(5, 40, 300, 30));
		
		cmbCustomer = new AutoFilterComboBox<CustomerInfo>(){
			private static final long serialVersionUID = 1L;
			
			//重载方法， 改写判断适配方式
			protected boolean isMatch(String txt, Object obj) {
				if(obj == null)
					return false;
				if(txt == null || "".equals(txt))
					return true;
				
				if(obj instanceof CustomerInfo)
				{
					CustomerInfo info = (CustomerInfo) obj;
					String val = null;
					
					val = info.getNumber() != null ? info.getNumber() : "";
					if(val.contains(txt))
						return true;
					
					val = info.getName() != null ? info.getName() : "";
					if(val.contains(txt))
						return true;
					
					val = info.getPhone() != null ? info.getPhone() : "";
					if(val.contains(txt))
						return true;
				}
				return false;
			}
		};
		conCustomer = new LabelContainer(cmbCustomer, 120, BorderLayout.WEST, true, "客户：");
		conCustomer.setBounds(new Rectangle(340, 40, 300, 30));
		
		txtAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtAmt.setEditable(false);
		conAmt = new LabelContainer(txtAmt, 120, BorderLayout.WEST, true, "产品金额：");
		conAmt.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtFactAmt = new FormatInputField(FormatInputField.DT_DOUBLE);
		txtFactAmt.setEditable(false);
		conFactAmt = new LabelContainer(txtFactAmt, 120, BorderLayout.WEST, true, "收取金额：");
		conFactAmt.setBounds(new Rectangle(340, 75, 300, 30));
		
		txtRemark = new FormatInputField();
		conRemark = new LabelContainer(txtRemark, 120, BorderLayout.WEST, true, "备注：");
		conRemark.setBounds(new Rectangle(5, 110, 300, 30));
		
		txtBarCode = new FormatInputField();
		conBarCode = new LabelContainer(txtBarCode, 120, BorderLayout.WEST, true, "条形码：");
		conBarCode.setBounds(new Rectangle(340, 110, 300, 30));
		conBarCode.setVisible(false);
		
		
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
		jp_tbl.setBounds(new Rectangle(5, 145, 630, 300));
		
		jp.add(conPerson);
		jp.add(conCustomer);
		jp.add(conAmt);
		jp.add(conFactAmt);
		jp.add(conRemark);
		jp.add(conBarCode);
		jp.add(jp_tbl);
		
		boolean isbc = ParamData.isBarCodeSale();
		conBarCode.setVisible(isbc);
		if(isbc)
			tblEntry.disableDetailBtn();
	}

	public void setUIContext(HashMap<String, Object> ctx) throws Exception
	{
		super.setUIContext(ctx);
		if(ctx.containsKey("customer") && ctx.get("customer") != null)
		{
			CustomerInfo cusInfo = (CustomerInfo) ctx.get("customer");
			SaleMutilInfo info = (SaleMutilInfo) this.editData;
			info.setCustomerInfo(cusInfo);
			this.loadFiles();
		}
	}
	
	public void initToolBar() throws Exception
	{
		super.initToolBar();
		
		btnTrunc = new ToolButton("取整(角)", "btntrunc");
		btnList.add(4, btnTrunc);
	}
	
	protected void onLoad() throws Exception {
		super.onLoad();
		
		LinkedList<Info> cusList = BaseData.getInfoList(CustomerInfo.class);
		LinkedList<Info> perList = BaseData.getInfoList(PersonInfo.class);
		LinkedList<Info> matList = BaseData.getInfoList(MaterialInfo.class);
		LinkedList<Info> unitList = BaseData.getInfoList(MeasureUnitInfo.class);
		
		cmbPerson.addItems(perList.toArray(new PersonInfo[0]));
		cmbCustomer.addItems(cusList.toArray(new CustomerInfo[0]));
		column_cmbMaterial.addItems(matList.toArray(new MaterialInfo[0]));
		column_cmbUnit.addItems(unitList.toArray(new MeasureUnitInfo[0]));
		cmbPayType.addItems(PayType.values());
		
		for(Info info : unitList)
			unitMap.put(info.getId(), (MeasureUnitInfo) info);
		
		isWaitBarcode = true;
	}

	public void addListener() throws Exception {
		super.addListener();
		
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
		
		pkBizdate.addDataChangeListener(new DataChangeListener() {
			public void DataChangeAction(Object obj) {
				try {
					bizdateChange();
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(SaleMutilEditUI.this, err);
				}
			}
		});
		
		cmbCustomer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					customerChangeAction(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(SaleMutilEditUI.this, err);
				}
			}
		});
		
		cmbPerson.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					personChangeAction(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(SaleMutilEditUI.this, err);
				}
			}
		});
		
		
		tblEntry.addEditeStopedActionListener(new TableEditStopedListener() {
			public void editStopedAction(TblEditStopedEvent e) throws Exception {
				tblEntry_editStoped(e);
			}
		});
		
		if(ParamData.isBarCodeSale())
			BarcodeScanner.addListener(this.getClass().getName(), this);
		
		CardReader.addListener(this.getClass().getName(), new CardNumberListener() {
			public void actionPerformed(String number) throws Exception {
				actionReadCard(number);
			}
		});
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		SaleMutilInfo info = (SaleMutilInfo) this.editData;
		
		PersonInfo pInfo = (PersonInfo) cmbPerson.getSelectedItem();
		CustomerInfo cusInfo = (CustomerInfo) cmbCustomer.getSelectedItem();
		PayType pt = (PayType) cmbPayType.getSelectedItem();
		BigDecimal amt = txtAmt.getBigDecimalValue();
		BigDecimal factamt = txtFactAmt.getBigDecimalValue();
		String remark = txtRemark.getText();
		boolean isBalance = chkIsBalance.isSelected();
		
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
		super.loadFiles();
		SaleMutilInfo info = (SaleMutilInfo) this.editData;
		
		cmbPerson.setSelectedItem(info.getPersonInfo());
		cmbCustomer.setSelectedItem(info.getCustomerInfo());
		cmbPayType.setSelectedItem(info.getPt());
		txtAmt.setBigDecimalValue(info.getAmt());
		txtFactAmt.setBigDecimalValue(info.getFactAmt());
		txtRemark.setText(info.getRemark());
		chkIsBalance.setSelected(info.getIsBalance());
		
		tblEntry.loadFiles(info.getEntryList());
		isLoad = true;
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		SaleMutilInfo info = (SaleMutilInfo) this.editData;
		info.setIsBalance(false);
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
	
	protected void personChangeAction(ItemEvent e) throws Exception
	{
		if(e.getStateChange() != ItemEvent.SELECTED)
			return;
		
		if(!isLoad)
			return;
		
	}
	
	protected void customerChangeAction(ItemEvent e) throws Exception
	{
		if(e.getStateChange() != ItemEvent.SELECTED)
			return;
		
		if(!isLoad)
			return;
		
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
	
	protected Class<?> getInfoClass() {
		return SaleMutilInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpSaleMutil.getInstance();
	}

	protected String getEditUITitle() {
		return "销售单";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}
	
	protected void actionAuditTrue(ActionEvent e) throws Exception {
		storeFiles();
		verifyInput();
		verifyAuditTrue();
		
		PayType pt = PayType.Cash;
		CustomerInfo cusInfo = (CustomerInfo) cmbCustomer.getSelectedItem();
		if(cusInfo != null && cusInfo.getId() != null && !"".equals(cusInfo.getId()))
			pt = PayType.Vip;
			
		BigDecimal amt = txtFactAmt.getBigDecimalValue();
		PayLogInfo pl = PayTypeSelectUI.createLog(amt);
		
		new PayTypeSelectUI(this, pt, null, pl, new PayListener() {
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
			super.actionAuditTrue(null);
		} catch (Exception e) {
			e.printStackTrace();
			new ExpHandle(SaleMutilEditUI.this, e);
		}
	}

	protected void verifyAuditTrue() throws Exception {
		super.verifyAuditTrue();
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
		super.setCompentStatus();
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		
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
		CardReader.removeListener(this.getClass().getName());
		super.actionClose(e);
	}

	protected void actionReadCard(String cardNumber) throws Exception
	{
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		if(!flag)
		{
			CustomerInfo cusInfo = IHttpCustomer.getInstance().getCustomerInfo(cardNumber);
			cmbCustomer.setSelectedItem(cusInfo);
		}
	}

	public void actionReadBarcode(String barcode) throws Exception {

		if(!isWaitBarcode)
			return;
		isWaitBarcode = false;
		
		CustomerInfo cusInfo = IHttpCustomer.getInstance().getCustomerInfo(barcode);
		if(cusInfo != null && cusInfo.getId() != null)
		{
			cmbCustomer.setSelectedItem(cusInfo);
			
			isWaitBarcode = true;
			return;
		}
		
		MaterialInfo mInfo = IHttpMaterial.getInstance().getMaterialInfo(barcode);
		if(mInfo != null && mInfo.getId() != null)
		{
			int rowIndex = tblEntry.getRowCount();
			tblEntry.insertRow(rowIndex, null);
			tblEntry.setValueAt(mInfo, rowIndex, "materialinfo");
			this.materialChangeAction(rowIndex, mInfo);
			isWaitBarcode = true;
			return;
		}
		
		isWaitBarcode = true;
	
	}
	
	
	protected boolean isNeedPrint()
	{
		return true;
	}
	
	//-----------------------------------------以下为打印方法， 每个方法对应一个模版-----------------------------------------
	public void actionPrint_sale(Graphics g, PageFormat pf, int page) throws Exception
	{
		SaleMutilInfo info = (SaleMutilInfo) this.getEditData();
		
		String shopId = info.getShopId();
		ShopInfo shopInfo = (ShopInfo) IHttpShop.getInstance().getInfo(shopId);
		String shopName = shopInfo.getName();
		
		Graphics2D g2d = (Graphics2D) g;  
        g2d.setFont(new Font("Default", Font.PLAIN, 10));  
        g2d.drawString(shopName, 50, 15);
        g2d.drawString("单号:" + info.getNumber(), 5, 30);
        g2d.drawString("日期:" + DateTimeUtil.getDateStr(info.getBizdate()), 5, 45); 
        g2d.drawString("负责人:" + info.getPersonInfo().getName(), 5, 60);  
        g2d.drawString("金额:" + info.getFactAmt(), 5, 75);
        g2d.drawString("付款方式:" + info.getPt().getName(), 5, 90);
        
        LinkedList<SaleMutilEntryInfo> entryList = info.getEntryList();
        int size = entryList.size();
        
        g2d.drawString("产品", 5, 105);
        g2d.drawString("数量", 70, 105);
        g2d.drawString("金额", 95, 105);
        for(int index = 0; index < size; index++)
        {
        	SaleMutilEntryInfo entryInfo = entryList.get(index);
        	g2d.drawString(entryInfo.getMaterialInfo().getName() , 5, 105 + 15 * (index + 1));
        	g2d.drawString(entryInfo.getQty().intValue() + "", 70, 105 + 15 * (index + 1));
        	g2d.drawString(entryInfo.getAmt() + "", 95, 105 + 15 * (index + 1));
        }
        
        g2d.setFont(new Font("Default", Font.PLAIN, 8));  
        g2d.drawString("打印时间:" + DateTimeUtil.getDateStr(new Date(), DateTimeUtil.secondFormat), 5, 120 + size * 15);
        
        g2d.setFont(new Font("Default", Font.PLAIN, 10));  
        g2d.drawString("----------------------------------------- ", 5, 120 + (size + 1) * 15);
        g2d.drawString("----------------------------------------- ", 5, 120 + (size + 4) * 15);
	}

}
