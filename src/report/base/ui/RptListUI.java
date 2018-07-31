package report.base.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import bean.bill.BillListUI;
import common.util.PermissionUtil;
import em.RptAlign;
import exp.ExpHandle;
import myswing.btn.ToolButton;
import myswing.table.ColumnInfo;
import report.base.bean.RptInfo;

public abstract class RptListUI extends BillListUI{
	private static final long serialVersionUID = 1L;
	
	protected ToolButton btnExport = null;
	protected ToolButton btnImport = null;

	public RptListUI(String title)
	{
		super(title);
		
		colList.addLast(new ColumnInfo("id", "id", 0));
		colList.addLast(new ColumnInfo("name", "名称", 400));
		colList.addLast(new ColumnInfo("isaudittrue", "是否确认", 100, Boolean.class, RptAlign.RIGHT));
	}
	
	public boolean isCanOpen() {
		return PermissionUtil.isSuperUser();
	}

	public void initToolBar() throws Exception {
		super.initToolBar();
		
		btnExport = new ToolButton("导出", "");
		btnList.add(4, btnExport);
		
		btnImport = new ToolButton("导入", "");
		btnList.add(5, btnImport);
	}

	public void addListener() throws Exception {
		super.addListener();
		
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionExport(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionImport(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
	}
	
	/**
	 * 
	  * @功能描述 导出报表
	  * @作者 黎嘉杰 
	  * @日期 2016年12月1日 上午10:26:31 
	  * @参数 
	  * @返回
	 */
	protected void actionExport(ActionEvent e) throws Exception{
		int[] rows = tblMain.getSelectedRows();
		if(rows == null || rows.length <= 0)
			return;
		
		JFileChooser jchoose = new JFileChooser();
		jchoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jchoose.setMultiSelectionEnabled(false);
		int state = jchoose.showSaveDialog(this);
		if(state != JFileChooser.APPROVE_OPTION)
			return;
		
		File dir = jchoose.getSelectedFile();
		for(int rowIndex : rows)
		{
			String id = tblMain.getValueAt(rowIndex, "id").toString();
			RptInfo info = (RptInfo) this.getHttpInstance().getInfo(id);
			
			File file = new File(dir.getAbsolutePath() + "/" + info.getName());
			FileOutputStream out = new FileOutputStream(file);
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(info);
			objOut.flush();
			objOut.close();
		}
	}
	
	/**
	 * 
	  * @功能描述 导入报表
	  * @作者 黎嘉杰 
	  * @日期 2016年12月1日 上午10:25:26 
	  * @参数 
	  * @返回
	 */
	protected void actionImport(ActionEvent e) throws Exception{
		JFileChooser jchoose = new JFileChooser();
		jchoose.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jchoose.setMultiSelectionEnabled(true);
		int state = jchoose.showOpenDialog(this);
		if(state != JFileChooser.APPROVE_OPTION)
			return;
		
		File[] fs = jchoose.getSelectedFiles();
		for(File file : fs)
		{
			FileInputStream in = new FileInputStream(file);
			ObjectInputStream objIn = new ObjectInputStream(in);
			RptInfo info = (RptInfo) objIn.readObject();
			objIn.close();
			
			info.setId(null);
			this.getHttpInstance().save(info);
		}
		
		this.actionRefresh(e);
	}
	
	
	
}
