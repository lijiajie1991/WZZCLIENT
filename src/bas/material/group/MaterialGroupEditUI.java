package bas.material.group;

import java.awt.event.ActionEvent;

import bean.IHttp;
import bean.base.BaseEditUI;

public class MaterialGroupEditUI extends BaseEditUI{
	private static final long serialVersionUID = 1L;
	
	public MaterialGroupEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(320, 150);
	}

	protected void onLoad() throws Exception {
		super.onLoad();
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
	}

	protected Class<?> getInfoClass() {
		return MaterialGroupInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpMaterialGroup.getInstance();
	}

	protected String getEditUITitle() {
		return "产品类型";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

}
