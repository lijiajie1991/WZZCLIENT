package ui.base;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFrame;

import barcode.BarcodeScanner;
import bill.paylog.IHttpPayLog;
import bill.paylog.PayLogInfo;
import cache.ParamData;
import common.util.ContextUtil;
import common.util.DateTimeUtil;
import common.util.ImageLoader;
import common.util.NetUtil;
import em.PayType;
import exp.BizException;
import exp.ExpHandle;
import listener.BarcodeListener;
import listener.PayListener;
import myswing.container.LabelContainer;
import myswing.editor.MyComboBox;
import myswing.msgbox.MsgBox;
import myswing.msgbox.MsgBoxUI;
import wxpay.WXPay;
import wxpay.common.Configure;
import wxpay.common.Util;
import wxpay.protocol.pay_protocol.ScanPayReqData;
import wxpay.protocol.pay_protocol.ScanPayResData;
import wxpay.protocol.pay_query_protocol.ScanPayQueryReqData;
import wxpay.protocol.pay_query_protocol.ScanPayQueryResData;
import wxpay.protocol.reverse_protocol.ReverseReqData;

public class PayTypeSelectUI extends JFrame implements BarcodeListener{
	private static final long serialVersionUID = 1L;
	
	protected JFrame ower = null;
	protected PayListener ls = null;
	protected PayLogInfo pl = null;
	
	protected LabelContainer conPayType = null;
	protected MyComboBox cmbPayType = null;
	protected JButton btnConfirm = null;
	
	
	public PayTypeSelectUI(JFrame ower, PayType pt, PayType[] pts,  PayLogInfo pl, PayListener ls)
	{
		super();
		this.ls = ls;
		this.ower = ower;
		this.pl = pl;
		
		this.ower.setEnabled(false);
		initUI(pt, pts);
	}
	
	protected void initUI(PayType pt, PayType[] pts)
	{
		//设置图标标题
		this.setIconImage(ImageLoader.getIcon("my_24.png").getImage());
		this.setTitle("付款方式"); // 设置窗口标题
		this.setVisible(true); // 设置可见
		this.setResizable(false);
		
		this.setMinimumSize(new Dimension(300, 120));
		this.setPreferredSize(new Dimension(300, 120));
		setLocationRelativeTo(getOwner()); // 居中
		
		
		cmbPayType = new MyComboBox();
		conPayType = new LabelContainer(cmbPayType, 100, BorderLayout.WEST, true, "付款方式：");
		conPayType.setBounds(new Rectangle(5, 5, 280, 30));
		cmbPayType.addItems(PayType.values());
		if(pts != null)
		{
			for(PayType p : pts)
			{
				cmbPayType.removeItem(p);
			}
		}
		
		cmbPayType.setSelectedItem(pt);
		
		btnConfirm = new JButton("确认");
		btnConfirm.setBounds(new Rectangle(5, 40, 280, 30));
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionConfirm();
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(PayTypeSelectUI.this, err);
				}
			}
		});
		
		this.setLayout(null);
		this.add(conPayType);
		this.add(btnConfirm);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					BarcodeScanner.removeListener(PayTypeSelectUI.class.getName());
					dispose();
					ower.setEnabled(true);
					ower.setVisible(true);
				} catch (Exception err) {
					err.printStackTrace();
					new ExpHandle(PayTypeSelectUI.this, err);
				}
			}
		});
	}
	
	protected void actionConfirm() throws Exception
	{
		PayType pt = (PayType) cmbPayType.getSelectedItem();
		this.pl.setPt(pt);
		if(Configure.isEnable && pt == PayType.Webbank)
		{
			btnConfirm.setEnabled(false);
			BarcodeScanner.addListener(this.getClass().getName(), this);
		}
		else
		{
			successToPay(pt);
		}
	}
	
	protected void successToPay(PayType pt) throws Exception{
		BarcodeScanner.removeListener(this.getClass().getName());
		
		this.pl.setRemark("支付成功");
		this.pl.setIsSuccess(true);
		IHttpPayLog.getInstance().setAuditTrue(pl);
		
		if(ls != null && ls.isShowSuccess())
			MsgBox.showInfo(this, ls.getSuccessMsg());
		
		this.dispose();
		this.ower.setEnabled(true);
		this.ower.setVisible(true);
		if(ls != null)
			ls.success("", pt);
	}
	
	protected void failToPay(PayType pt, String msg) throws Exception{
		BarcodeScanner.removeListener(this.getClass().getName());
		
		this.pl.setRemark("支付失败！原因：" + msg);
		this.pl.setIsSuccess(false);
		IHttpPayLog.getInstance().setAuditTrue(pl);
		
		
		if(ls != null && ls.isShowFail())
			MsgBox.showInfo(this, ls.getFailMsg(msg));
		
		this.dispose();
		this.ower.setEnabled(true);
		this.ower.setVisible(true);
		
		if( ls != null)
			ls.fail("", pt);
	}
	
	public static PayLogInfo createLog(BigDecimal amt)
	{
		String newId = UUID.randomUUID().toString();
		newId = newId.replace("-", "");
		if(newId.length() > 31)
			newId = newId.substring(0, 31);
		
		PayLogInfo plInfo = new PayLogInfo();
		plInfo.setCreateTime(new Date());
		plInfo.setCreaterId(ContextUtil.getUserId());
		plInfo.setLastUpdateTime(new Date());
		plInfo.setLastUpdateUserId(ContextUtil.getUserId());
		
		plInfo.setShopId(ContextUtil.getShopId());
		plInfo.setAdminShopId(ContextUtil.getAdminShopId());
		
		plInfo.setAttach("");
		plInfo.setTag("");
		plInfo.setBizdate(new Date());
		plInfo.setAmt(amt);
		
		plInfo.setMacIp(NetUtil.getIP());
		plInfo.setPayId(newId);
		plInfo.setTimeStart(new Date());
		plInfo.setTimeExpire(DateTimeUtil.getDateAfterMinutes(new Date(), 2));
		
		return plInfo;
		
	}

	/**
	  * @功能描述 微信支付操作
	  * @作者 黎嘉杰 
	  * @日期 2017年1月28日 下午3:20:25 
	  * @参数 扫描到的条形码
	  * @返回
	 */
	protected void action_wxpay(String barcode) throws Exception
	{
		BarcodeScanner.removeListener(this.getClass().getName());
		
		//设置开始时间和超时时间
		pl.setTimeStart(new Date());
		pl.setTimeExpire(DateTimeUtil.getDateAfterMinutes(new Date(), 2));
		
		//计算付款金额和格式化时间
		int fee = pl.getAmt().multiply(new BigDecimal(100)).intValue();
		String df = DateTimeUtil.getDateStr(pl.getTimeStart(), DateTimeUtil.numberFormat);
		String dt = DateTimeUtil.getDateStr(pl.getTimeExpire(), DateTimeUtil.numberFormat);
		
		//创建请求数据， 发起支付服务请求
		ScanPayReqData data = new ScanPayReqData(barcode, pl.getBody(), pl.getAttach(), pl.getPayId(), fee, pl.getShopId(), pl.getMacIp(), df, dt, pl.getTag());
		String payServiceResponseString = WXPay.requestScanPayService(data);
		ScanPayResData rs = (ScanPayResData) Util.getObjectFromXML(payServiceResponseString, ScanPayResData.class);
		
		pl.setTransactionId(rs.getTransaction_id());
		
		//开始处理支付请求返回的数据
		//如果状态码不是成功状态， 则表示支付失败。调用支付失败方法
		if(!"SUCCESS".equalsIgnoreCase(rs.getReturn_code()))
		{
			failToPay(pl.getPt(), rs.getReturn_msg());
		}
		//如果状态码为成功状态， 则要继续判断业务结果
		else
		{
			//如果业务结果为成功， 则表示支付成功。 调用支付成功方法
			if("SUCCESS".equalsIgnoreCase(rs.getResult_code()))
			{
				successToPay(pl.getPt());
			}
			//如果业务结果不成功， 则需要继续判断订单状态
			else
			{
				//如果订单状态不是为用户正在支付状态， 则表示支付失败。调用支付失败方法
				if(!"SYSTEMERROR".equalsIgnoreCase(rs.getErr_code()))
				{
					failToPay(pl.getPt(), rs.getErr_code_des());
				}
				//如果用户正在支付， 则继续查询订单状态
				else
				{
					//创建查询订单的请求数据
					ScanPayQueryReqData query = new ScanPayQueryReqData(rs.getTransaction_id(), rs.getOut_trade_no());
					
					//先等待5秒钟
					Thread.sleep(5000);
					
					//开始查询订单状态， 超时为30秒
					int second = 0;
					while(second <= 30)
					{
						//发送订单状态查询请求
						String query_response = WXPay.requestScanPayQueryService(query);
						ScanPayQueryResData query_rs = (ScanPayQueryResData) Util.getObjectFromXML(query_response, ScanPayQueryResData.class);
						
						//如果查询状态码为成功， 则继续判断订单支付状态
						if("SUCCESS".equalsIgnoreCase(query_rs.getReturn_code()))
						{
							//获取订单支付状态码， 如果为成功的， 则表示支付成功。 调用支付成功的方法并退出支付流程
							String payState = query_rs.getTrade_state();
							if(payState != null && "SUCCESS".equalsIgnoreCase(payState))
							{
								successToPay(pl.getPt());
								return;
							}
									
						}
						
						//如果订单状态明确为未支付成功的， 则等待10秒钟再重新查一次
						Thread.sleep(10000);
						//累加等待时间， 超过30秒则表示订单支付失败
						second += 10;
					}
					
					//如果订单支付失败， 先请求撤销订单服务。 如果用户支付失败， 则会关闭订单。 如果用户已经支付成功， 则按照支付金额返还用户
					ReverseReqData rever = new ReverseReqData(rs.getTransaction_id(), rs.getOut_trade_no());
					WXPay.requestReverseService(rever);
					
					//调用支付失败的方法
					failToPay(pl.getPt(), "支付超时");
				}
						
			}
		}
	}

	/**
	  * @功能描述 支付宝支付操作
	  * @作者 黎嘉杰 
	  * @日期 2017年2月16日 下午9:06:24 
	  * @参数 扫描到的条形码
	  * @返回
	 */
	protected void action_alpay(String barcode) throws Exception
	{
		
	}

	public void actionReadBarcode(String barcode) throws Exception {
		BarcodeScanner.removeListener(this.getClass().getName());
		
		final String bc = barcode;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				PayType pt = (PayType) cmbPayType.getSelectedItem();
				try{
					if(pt == PayType.Webbank){
						action_wxpay(bc);
					}
					else if(pt == PayType.ALiPay){
						action_alpay(bc);
					}
				}catch(Exception e)
				{
					e.printStackTrace();
					StringBuffer msg = new StringBuffer();
					msg.append("支付失败！请检查：\n");
					msg.append("1.网络是否连接正常！\n");
					if(pt == PayType.Webbank)
						msg.append("2.微信支付设置是否正确。");
					else if(pt == PayType.ALiPay)
						msg.append("2.支付宝支付设置是否正确。");
					MsgBox.showInfo(PayTypeSelectUI.this, msg.toString());
				}
			}
		});
		
	}
	
}
