package ui.base;

import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JFrame;

import bean.Info;
import em.OpenStateType;

public class UiFactory {
	public static void openEditUI(JFrame ower, Class<?> cls, ActionListener quitLs, HashMap<String, Object> ctx) throws Exception
	{
		ower.setEnabled(false);
		EditUI ui = (EditUI) cls.newInstance();
		ui.setQuitLs(quitLs);
		ui.setOwer(ower);
		ui.setOst(OpenStateType.ADDNEW);
		ui.initUI(1024, 768);
		ui.initToolBar();
		ui.createToolBar();
		ui.addListener();
		ui.onLoad();
		ui.actionAddNew(null);
		ui.setUIContext(ctx);
	}
	
	public static void openEditUI(JFrame ower, Class<?> cls, ActionListener quitLs, HashMap<String, Object> ctx, Info info) throws Exception
	{
		ower.setEnabled(false);
		EditUI ui = (EditUI) cls.newInstance();
		ui.setQuitLs(quitLs);
		ui.setOwer(ower);
		ui.setOst(OpenStateType.EDIT);
		ui.initUI(1024, 768);
		ui.initToolBar();
		ui.createToolBar();
		ui.addListener();
		ui.onLoad();
		ui.setEditData(info);
		ui.loadFiles();
	}
}
