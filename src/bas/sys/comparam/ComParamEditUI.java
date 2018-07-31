package bas.sys.comparam;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;

import bas.sys.shop.ShopInfo;
import bean.IHttp;
import bean.base.BaseEditUI;
import common.util.ContextUtil;
import em.ComType;
import myswing.container.LabelContainer;
import myswing.editor.FormatInputField;
import myswing.editor.MyComboBox;

public class ComParamEditUI extends BaseEditUI{
	private static final long serialVersionUID = 1L;
	
	protected LabelContainer conCt = null;
	protected MyComboBox cmbCt = null;
	
	protected LabelContainer conComName = null;
	protected MyComboBox cmbComName = null;
	
	protected LabelContainer conBoudIndex = null;
	protected FormatInputField txtBoudIndex = null;
	
	protected LabelContainer conDataBits = null;
	protected FormatInputField txtDataBits = null;
	
	protected LabelContainer conStopBits = null;
	protected FormatInputField txtStopBits = null;
	
	protected LabelContainer conParityV = null;
	protected FormatInputField txtParityV = null;
	
	public ComParamEditUI()
	{
		super();
	}
	
	public void initUI(int width, int height) {
		super.initUI(320, 340);
		
		conNumber.setBounds(new Rectangle(3000, 5, 300, 30));
		conName.setBounds(new Rectangle(3000, 40, 300, 30));
		
		cmbCt = new MyComboBox();
		conCt = new LabelContainer(cmbCt, 120, BorderLayout.WEST, true, "类型：");
		conCt.setBounds(new Rectangle(5, 5, 300, 30));
		
		cmbComName = new MyComboBox();
		conComName = new LabelContainer(cmbComName, 120, BorderLayout.WEST, true, "串口：");
		conComName.setBounds(new Rectangle(5, 40, 300, 30));
		
		txtBoudIndex = new FormatInputField(FormatInputField.DT_INTEGER);
		conBoudIndex = new LabelContainer(txtBoudIndex, 120, BorderLayout.WEST, true, "比特率：");
		conBoudIndex.setBounds(new Rectangle(5, 75, 300, 30));
		
		txtDataBits = new FormatInputField(FormatInputField.DT_INTEGER);
		conDataBits = new LabelContainer(txtDataBits, 120, BorderLayout.WEST, true, "数据位：");
		conDataBits.setBounds(new Rectangle(5, 110, 300, 30));
		
		txtStopBits = new FormatInputField(FormatInputField.DT_INTEGER);
		conStopBits = new LabelContainer(txtStopBits, 120, BorderLayout.WEST, true, "停止位：");
		conStopBits.setBounds(new Rectangle(5, 145, 300, 30));
		
		txtParityV = new FormatInputField(FormatInputField.DT_INTEGER);
		conParityV = new LabelContainer(txtParityV, 120, BorderLayout.WEST, true, "校验位：");
		conParityV.setBounds(new Rectangle(5, 180, 300, 30));
		
		
		jp.add(conCt);
		jp.add(conComName);
		jp.add(conBoudIndex);
		jp.add(conDataBits);
		jp.add(conStopBits);
		jp.add(conParityV);
		
		cmbCt.addItems(ComType.values());
		
        Enumeration coms = CommPortIdentifier.getPortIdentifiers();
        while(coms.hasMoreElements()){
            CommPortIdentifier port = (CommPortIdentifier)coms.nextElement();
            if(port.getPortType()==CommPortIdentifier.PORT_SERIAL){
                cmbComName.addItem(port.getName());
            }
             
        }
	}

	protected void onLoad() throws Exception {
		super.onLoad();
		
	}

	public void addListener() throws Exception {
		super.addListener();
	}

	protected void storeFiles() throws Exception {
		super.storeFiles();
		
		ComType ct = (ComType) cmbCt.getSelectedItem();
		String comName = (String) cmbComName.getSelectedItem();
		int boudIndex = txtBoudIndex.getIntValue();
		int dataBits = txtDataBits.getIntValue();
		int stopBits = txtStopBits.getIntValue();
		int parityV = txtParityV.getIntValue();
		
		ComParamInfo info = (ComParamInfo) this.editData;
		info.setCt(ct);
		info.setComName(comName);
		info.setBoudIndex(boudIndex);
		info.setDataBits(dataBits);
		info.setStopBits(stopBits);
		info.setParityV(parityV);
	}

	protected void loadFiles() throws Exception {
		super.loadFiles();
		ComParamInfo info = (ComParamInfo) this.editData;
		cmbComName.addItem(info.getComName());
		cmbComName.setSelectedItem(info.getComName());
		cmbCt.setSelectedItem(info.getCt());
		txtBoudIndex.setIntValue(info.getBoudIndex());
		txtDataBits.setIntValue(info.getDataBits());
		txtStopBits.setIntValue(info.getStopBits());
		txtParityV.setIntValue(info.getParityV());
		
	}
	
	protected void loadDefault() throws Exception {
		super.loadDefault();
		ComParamInfo info = (ComParamInfo) this.getEditData();
		ShopInfo shop = ContextUtil.getShopInfo();
		info.setName(shop.getName() + "的com参数");
		info.setCt(ComType.Barcode);
		info.setComName(null);
		info.setBoudIndex(9600);
	}

	protected Class<?> getInfoClass() {
		return ComParamInfo.class;
	}

	protected IHttp getHttpInstance() {
		return IHttpComParam.getInstance();
	}

	protected String getEditUITitle() {
		return "串口参数设置";
	}
	
	protected void actionAddNew(ActionEvent e) throws Exception {
		super.actionAddNew(e);
	}

}
