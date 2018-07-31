package bean.bill;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JCheckBox;

import bas.sys.print.IHttpPrint;
import bas.sys.print.PrintInfo;
import common.util.ContextUtil;
import em.OpenStateType;
import exp.BizException;
import exp.ExpHandle;
import myswing.btn.ToolButton;
import myswing.container.LabelContainer;
import myswing.editor.DatePicker;
import myswing.editor.FormatInputField;
import myswing.msgbox.MsgBox;
import ui.base.EditUI;

public abstract class BillEditUI extends EditUI{
	private static final long serialVersionUID = 1L;
	
	protected ToolButton btnAuditTrue = null;
	protected ToolButton btnAuditFalse = null;
	protected ToolButton btnPrint = null;
	
	protected LabelContainer conNumber = null;
	protected FormatInputField txtNumber = null;
	
	protected LabelContainer conBizdate = null;
	protected DatePicker pkBizdate = null;
	
	protected JCheckBox chkIsAuditTrue = null;
	
	public BillEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(width, height);
		
		txtNumber = new FormatInputField();
		txtNumber.setEditable(false);
		conNumber = new LabelContainer(txtNumber, 120, BorderLayout.WEST, true, "编号：");
		conNumber.setBounds(new Rectangle(5, 5, 300, 30));
		
		pkBizdate = new DatePicker();
		conBizdate = new LabelContainer(pkBizdate, 120, BorderLayout.WEST, true, "业务日期：");
		conBizdate.setBounds(new Rectangle(5, 40, 300, 30));
		
		chkIsAuditTrue = new JCheckBox("是否已确认");
		
		jp.add(conNumber);
		jp.add(conBizdate);
	}
	
	public void initToolBar() throws Exception
	{
		super.initToolBar();
		
		btnSave.setVisible(false);
		
		btnAuditTrue = new ToolButton("确认", "audittrue");
		btnList.add(1, btnAuditTrue);
		
		btnAuditFalse = new ToolButton("改单", "auditfalse");
		btnList.add(2, btnAuditFalse);
		
		btnPrint = new ToolButton("打印", "print");
		btnList.add(3, btnPrint);
		btnPrint.setVisible(isNeedPrint());
		btnPrint.setEnabled(isNeedPrint());
	}
	
	public void addListener() throws Exception {
		super.addListener();
		btnAuditTrue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAuditTrue(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(BillEditUI.this, err);
				}
			}
		});
		
		btnAuditFalse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAuditFalse(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(BillEditUI.this, err);
				}
			}
		});
		
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionPrint(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(BillEditUI.this, err);
				}
			}
		});
	}
	
	protected void actionAuditTrue(ActionEvent e) throws Exception{
		this.storeFiles();
		this.verifyInput();
		this.verifyAuditTrue();
		BillInfo info = (BillInfo) this.editData;
		IHttpBill http = (IHttpBill)this.getHttpInstance();
		String id = http.setAuditTrue(info);
		this.editData = this.getHttpInstance().getInfo(id);
		this.loadFiles();
		showAuditTrueSuccess();
	}
	
	protected void actionAuditFalse(ActionEvent e) throws Exception{
		this.storeFiles();
		this.verifyInput();
		this.verifyAuditFalse();
		BillInfo info = (BillInfo) this.editData;
		IHttpBill http = (IHttpBill)this.getHttpInstance();
		
		String id = http.setAuditFalse(info);
		this.editData = this.getHttpInstance().getInfo(id);
		this.loadFiles();
		showAuditFalseSuccess();
	}
	
	protected void actionPrint(ActionEvent e) throws Exception{
		BillInfo info = (BillInfo) this.getEditData();
		if(info == null || info.getId() == null || "".equals(info.getId()))
			throw new BizException("单据未保存， 不能打印！");
		
		if(!info.getIsAuditTrue())
			throw new BizException("单据未确认， 不能打印！");
		
		LinkedList<PrintInfo> printList = IHttpPrint.getInstance().getPrintInfoList(ContextUtil.getShopId(), this.getInfoClass().getName());
		if(printList == null || printList.isEmpty())
			throw new BizException("没设置相应的打印模板， 请检查！");
		
		final PrintInfo pInfo = printList.getFirst();
		Printable pt = new Printable(){
			public int print(Graphics gh, PageFormat pf, int page) throws PrinterException {
				if (page > 0) {  
		            return NO_SUCH_PAGE;  
		        }
				try {
					actionPrint(gh, pf, page, pInfo);
				} catch (Exception e) {
					e.printStackTrace();
					throw new PrinterException(e.getMessage());
				}
				
				return PAGE_EXISTS;  
			}};
			
			
		 
		  
        // 通俗理解就是书、文档  
        Book book = new Book();  
  
        // 打印格式  
        PageFormat pf = new PageFormat();  
        pf.setOrientation(PageFormat.PORTRAIT);  
        
        double[] offsets = getPrintArea();
        double px = offsets[0];
        double py = offsets[1];
        double width = offsets[2];
        double height = offsets[3];
        
        int[] ps = getPaperSize();
        int pw = ps[0];
        int ph = ps[1];
  
        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
        Paper p = new Paper();  
        p.setSize(pw, ph);  
        p.setImageableArea(px, py, width, height);  
        pf.setPaper(p);  
  
        // 把 PageFormat 和 Printable 添加到书中，组成一个页面  
        book.append(pt, pf);  
  
        // 获取打印服务对象  
        PrinterJob job = PrinterJob.getPrinterJob();  
        job.setPageable(book);  
        job.print(); 
	}

	protected void actionDelete(ActionEvent e) throws Exception{
		if(this.editData.getId() == null)
			throw new BizException("单据未保存， 不能删除！");
		
		BillInfo info = (BillInfo) this.getHttpInstance().getInfo(this.editData.getId());
		if(info.getIsAuditTrue())
		{
			MsgBox.showInfo(this, "所选记录已经确认， 不能删除！");
			return;
		}
		
		super.actionDelete(e);
	}
		
	protected void storeFiles() throws Exception {
		super.storeFiles();
		BillInfo info = (BillInfo) this.editData;
		info.setNumber(txtNumber.getText());
		info.setBizdate(pkBizdate.getDate());
		info.setIsAuditTrue(chkIsAuditTrue.isSelected());
	}

	protected void loadFiles() throws Exception {
		BillInfo info = (BillInfo) this.editData;
		txtNumber.setText(info.getNumber());
		pkBizdate.setDate(info.getBizdate());
		chkIsAuditTrue.setSelected(info.getIsAuditTrue());
		setCompentStatus();
	}

	protected void loadDefault() throws Exception {
		super.loadDefault();
		IHttpBill http = (IHttpBill) this.getHttpInstance();
		
		BillInfo info = (BillInfo) this.editData;
		info.setBizdate(new Date());
		info.setIsAuditTrue(false);
		info.setNumber(http.getNewNumber());
		info.setShopId(ContextUtil.getShopId());
		
	}

	protected void verifyInput() throws Exception {
		super.verifyInput();
		BillInfo info = (BillInfo) this.editData;
		String num = info.getNumber();
		if(num == null || "".equals(num))
			throw new BizException("编码不能为空");
	}
	
	protected void verifyAuditTrue() throws Exception
	{
		
	}
	
	protected void verifyAuditFalse() throws Exception
	{
		
	}

	protected void showAuditTrueSuccess()
	{
	}
	
	protected void showAuditFalseSuccess()
	{
	}
	
	protected boolean isNeedPrint()
	{
		return false;
	}

	protected void setCompentStatus()
	{
		BillInfo info = (BillInfo) this.editData;
		boolean flag = info.getIsAuditTrue();
		boolean isView = OpenStateType.VIEW.equals(this.getOst());
		
		txtNumber.setEnabled(!flag && !isView);
		pkBizdate.setEnable(!flag && !isView);
		
		btnAuditTrue.setEnabled(!flag && !isView);
		btnAuditFalse.setEnabled(flag && !isView);
		btnDelete.setEnabled(!flag && !isView);
	}
	
	protected void actionPrint(Graphics g, PageFormat pf, int page, PrintInfo pInfo) throws Exception
	{
		String pm = pInfo.getPrintMethod();
		Method m = this.getClass().getMethod(pm, new Class<?>[]{Graphics.class, PageFormat.class, int.class});
		if(m != null)
		{
			m.invoke(this, new Object[]{g, pf, page});
		}
	}
	
	protected double[] getPrintArea()
	{
		//x, y, width, height
		return new double[]{
			5, 5, 400, 400	
		};
	}
	
	protected int[] getPaperSize()
	{
		//width, height
		return new int[]{
			400, 380	
		};
	}
}
