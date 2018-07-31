package ui.base;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import bean.IHttp;
import bean.Info;
import common.util.ImageLoader;
import common.util.PermissionUtil;
import em.OpenStateType;
import exp.BizException;
import exp.ExpHandle;
import myswing.btn.ToolButton;
import myswing.msgbox.MsgBox;

public abstract class EditUI extends JFrame{
	private static final long serialVersionUID = 1L;
	protected LinkedList<ToolButton> btnList = null;
	
	protected ActionListener quitLs = null;
	
	protected Info editData = null;
	protected OpenStateType ost = OpenStateType.ADDNEW;
	protected JFrame ower = null;
	
	
	protected JToolBar toolBar = null;
	protected ToolButton btnAddNew = null;
	protected ToolButton btnSave = null;
	protected ToolButton btnDelete = null;
	protected ToolButton btnClose = null;
	
	protected JPanel jp = null;
	
	public EditUI()
	{
		super();
		btnList = new LinkedList<ToolButton>();
	}
	
	public void initUI(int width, int height)
	{
		this.setLayout(new BorderLayout());
		jp = new JPanel();
		jp.setLayout(null);
		this.add(jp, BorderLayout.CENTER);
		
		//设置图标标题
		this.setIconImage(ImageLoader.getIcon("my_24.png").getImage());
		this.setTitle(this.getEditUITitle()); // 设置窗口标题
		this.setVisible(true); // 设置可见
		this.setResizable(false);
		
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		setLocationRelativeTo(getOwner()); // 居中
	}
	
	public void setUIContext(HashMap<String, Object> ctx) throws Exception
	{
		
	}
	
	public void initToolBar() throws Exception
	{
		btnAddNew = new ToolButton("新增", "addnew");
		btnList.addLast(btnAddNew);
		
		btnSave = new ToolButton("保存", "save");
		btnList.addLast(btnSave);
		
		btnDelete = new ToolButton("删除", "delete");
		btnList.addLast(btnDelete);
		
		btnClose = new ToolButton("关闭");
		btnList.addLast(btnClose);
	}
	
	public void createToolBar() throws Exception
	{
		HashSet<String> permissionSet = PermissionUtil.getBtnPermission(this.getInfoClass().getName());
		
		toolBar = new JToolBar(); 
		int size = btnList.size();
		for(int i = 0; i < size; i++)
		{
			ToolButton btn = btnList.get(i);
			String key = btn.getPromissionKey();
			if(key != null && !"".equals(key))
				btn.setEnabled(PermissionUtil.isSuperUser() || permissionSet.contains(btn.getPromissionKey()));
			else
				btn.setEnabled(true);
			toolBar.add(btn);
		}
		
		toolBar.setFloatable(false);
		this.add(toolBar, BorderLayout.PAGE_START);
	}
	
	public void addListener() throws Exception {
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddNew(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(EditUI.this, err);
				}
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionSave(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(EditUI.this, err);
				}
			}
		});
		
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionDelete(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(EditUI.this, err);
				}
			}
		});
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionClose(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(EditUI.this, err);
				}
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					actionClose(null);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(EditUI.this, err);
				}
			}
		});
	}

	protected void actionAddNew(ActionEvent e) throws Exception{
		Class<?> cls = this.getInfoClass();
		editData = (Info) cls.newInstance();
		this.loadDefault();
		this.loadFiles();
	}
	
	protected void actionSave(ActionEvent e) throws Exception{
		this.storeFiles();
		this.verifyInput();
		String id = this.getHttpInstance().save(editData);
		this.editData = this.getHttpInstance().getInfo(id);
		this.loadFiles();
		showSaveSuccess();
	}
	
	protected void actionDelete(ActionEvent e) throws Exception{
		if(this.editData.getId() == null)
			throw new BizException("单据未保存， 不能删除！");
		
		int choice = MsgBox.showConfirm(this, "是否确认删除所选记录?");
		if(choice != JOptionPane.YES_OPTION)
			return;
		
		this.getHttpInstance().delete(this.editData.getId());
		showDeleteSuccess();
		this.actionAddNew(e);
	}
	
	protected void actionClose(ActionEvent e) throws Exception
	{
		if(this.quitLs != null)
			this.quitLs.actionPerformed(e);
		
		this.dispose();
		
		if(this.ower != null)
		{
			this.ower.setEnabled(true);
			this.ower.setVisible(true);
		}
		
	}
	
	protected void loadDefault() throws Exception
	{
		
	}
	
	protected void onLoad() throws Exception{};
	
	protected void storeFiles() throws Exception
	{
		Class<?> cls = this.getInfoClass();
		editData = editData != null ? editData : (Info) cls.newInstance();
		this.loadDefault();
	}
	
	protected void loadFiles() throws Exception
	{
		
	}
	
	protected void showSaveSuccess()
	{
		MsgBox.showInfo(this, "保存成功！");
	}
	
	protected void showDeleteSuccess()
	{
		//MsgBox.showInfo(this, "删除成功！");
	}
	
	protected abstract Class<?> getInfoClass();
	
	protected abstract IHttp getHttpInstance();
	
	protected abstract String getEditUITitle();

	public OpenStateType getOst() {
		return ost;
	}

	public void setOst(OpenStateType ost) {
		this.ost = ost;
	}

	public void setOwer(JFrame ower) {
		this.ower = ower;
	}

	public Info getEditData() {
		return editData;
	}

	public void setEditData(Info editData) {
		this.editData = editData;
	}

	public void setQuitLs(ActionListener quitLs) {
		this.quitLs = quitLs;
	}

	protected void verifyInput() throws Exception
	{
		
	}
}
