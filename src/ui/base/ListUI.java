package ui.base;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import bean.IHttp;
import bean.Info;
import common.db.FilterInfo;
import common.db.SortInfo;
import common.util.PermissionUtil;
import em.OpenStateType;
import exp.ExpHandle;
import myswing.btn.ToolButton;
import myswing.msgbox.MsgBox;
import myswing.table.ColumnInfo;
import myswing.table.SimpleTable;


public abstract class ListUI extends BaseTabUI {
	private static final long serialVersionUID = 1L;
	
	protected LinkedList<ColumnInfo> colList = null;
	protected SimpleTable tblMain = null;
	protected QueryUI query = null;
	
	protected ToolButton btnAddNew = null;
	protected ToolButton btnEdit = null;
	protected ToolButton btnDelete = null;
	protected ToolButton btnQuery = null;
	protected ToolButton btnRefresh = null;
	protected ToolButton btnClose = null;
	
	public ListUI()
	{
		this("");
	}

	public ListUI(String name)
	{
		super(name);
		colList = new LinkedList<ColumnInfo>();
	}

	public void initUI(HashMap<String, Object> ctx) throws Exception
	{
		super.initUI(ctx);
		
		tblMain = new SimpleTable(false);
		tblMain.setEditenable(false);
		tblMain.initTable(colList);
		this.add(tblMain, BorderLayout.CENTER);
		
	}
	
	public void initToolBar() throws Exception
	{
		btnAddNew = new ToolButton("新增", "addnew");
		btnList.addLast(btnAddNew);
		
		btnEdit = new ToolButton("编辑", "edit");
		btnList.addLast(btnEdit);
		
		btnDelete = new ToolButton("删除", "delete");
		btnList.addLast(btnDelete);
		
		btnQuery = new ToolButton("查询", "");
		btnList.addLast(btnQuery);
		
		btnRefresh = new ToolButton("刷新");
		btnList.addLast(btnRefresh);
		
		btnClose = new ToolButton("关闭");
		btnList.addLast(btnClose);
	}
	
	public void addListener() throws Exception {
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddNew(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionEdit(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionDelete(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionQuery(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRefresh(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		HashSet<String> permissionSet = PermissionUtil.getBtnPermission(this.getInfoClass().getName());
		if(PermissionUtil.isSuperUser() || permissionSet.contains("edit"))
		{
			tblMain.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
				}
				
				public void mousePressed(MouseEvent e) {
				}
				
				public void mouseExited(MouseEvent e) {
				}
				
				public void mouseEntered(MouseEvent e) {
				}
				
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() != 2)
						return;
					try {
						actionEdit(null);
					} catch (Exception err) {
						err.printStackTrace();
						new ExpHandle(ower, err);
					}
				}
			});
		}
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionClose(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		
		if(query == null)
		{
			Class<?> queryCls = this.getQueryUI();
			query = (QueryUI) queryCls.newInstance();
			query.setOwer(ower);
			query.initUI(this.colList);
			query.addListener();
			query.setLs(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						actionRefresh(e);
					} catch (Exception err) {
						err.printStackTrace();
						new ExpHandle(ower, err);
					}
				}
			});
		}
	}

	protected void actionAddNew(ActionEvent e) throws Exception{
		this.ower.setEnabled(false);
		
		Class<?> cls = this.getEditUI();
		EditUI ui = (EditUI) cls.newInstance();
		ui.setQuitLs(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRefresh(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		ui.setOwer(ower);
		ui.setOst(OpenStateType.ADDNEW);
		ui.initUI(1024, 768);
		ui.initToolBar();
		ui.createToolBar();
		ui.addListener();
		ui.onLoad();
		ui.actionAddNew(e);
	}

	protected void actionEdit(ActionEvent e) throws Exception{
		int rowIndex = tblMain.getSelectedRow();
		if(rowIndex < 0)
			return;
		
		String id = tblMain.getValueAt(rowIndex, "id").toString();
		Info info = this.getHttpInstance().getInfo(id);
		
		this.ower.setEnabled(false);
		
		Class<?> cls = this.getEditUI();
		EditUI ui = (EditUI) cls.newInstance();
		ui.setQuitLs(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRefresh(e);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(ower, err);
				}
			}
		});
		ui.setOwer(ower);
		ui.setOst(OpenStateType.EDIT);
		ui.initUI(1024, 768);
		ui.initToolBar();
		ui.createToolBar();
		ui.addListener();
		ui.onLoad();
		ui.setEditData(info);
		ui.loadFiles();
		this.ower.setEnabled(false);
		
	}
	
	protected void actionDelete(ActionEvent e) throws Exception{
		int rowIndex = tblMain.getSelectedRow();
		if(rowIndex < 0)
			return;
		
		int choice = MsgBox.showConfirm(ower, "是否确认删除所选记录?");
		if(choice != JOptionPane.YES_OPTION)
			return;
		
		String id = tblMain.getValueAt(rowIndex, "id").toString();
		this.getHttpInstance().delete(id);
		showDeleteSuccess();
		actionRefresh(e);
	}
	
	protected void actionQuery(ActionEvent e) throws Exception
	{
		query.showUI(ower);
	}
	
	protected void actionRefresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
		FilterInfo filter = this.getFilterInfo();
		SortInfo order = this.getSortInfo();
		List<Map<String, Object>> list = this.getHttpInstance().getMapListData(filter, order);
		if(list == null)
			return;
		
		for(Map map : list)
		{
			tblMain.insertRowMap(tblMain.getRowCount(), map);
		}
		
	}
	
	protected void actionClose(ActionEvent e) throws Exception
	{
		this.quitLs.actionPerformed(e);
	}
	
	protected void showDeleteSuccess()
	{
	}
	
	protected FilterInfo getFilterInfo()
	{
		return query != null ? query.getFilter() : null;
	}
	
	protected SortInfo getSortInfo()
	{
		return query != null ? query.getSort() : null;
	}
	
	protected abstract Class<?> getEditUI();
	protected abstract IHttp getHttpInstance();
	protected abstract Class<?> getQueryUI();
}
