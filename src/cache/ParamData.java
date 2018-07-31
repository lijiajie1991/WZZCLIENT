package cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import bas.sys.comparam.ComParamInfo;
import bas.sys.comparam.IHttpComParam;
import bas.sys.shopparam.IHttpShopParam;
import bas.sys.shopparam.ShopParamInfo;
import bas.wxpayset.IHttpWxPaySet;
import bas.wxpayset.WxPaySetInfo;
import bean.Info;
import common.util.ContextUtil;
import em.ComType;
import wxpay.WXPay;
import wxpay.common.Configure;

public class ParamData {
	protected static final String[] keys = new String[]{
			ShopParamInfo.class.getName(),
			ComParamInfo.class.getName(),
	};
	
	protected static HashMap<String, Object> paramMap = new HashMap<String, Object>();
	
	public static void loadParam(Class<?> cls)
	{
		try{
			String clsName = cls != null ? cls.getName() : null;
			if(clsName == null || clsName.equals(ShopParamInfo.class.getName()))
			{
				ShopParamInfo info = null;
				LinkedList<Info> infoList = IHttpShopParam.getInstance().getInfoList("a.fshopid = '" + ContextUtil.getShopId() + "'", "");
				if(infoList != null && !infoList.isEmpty())
				{
					info = (ShopParamInfo) infoList.get(0);
				}
				
				paramMap.put(ShopParamInfo.class.getName(), info);
			}
			
			if(clsName == null || clsName.equals(ComParamInfo.class.getName()))
			{
				HashMap<String, ComParamInfo> infoMap = new HashMap<String, ComParamInfo>();
				LinkedList<Info> infoList = IHttpComParam.getInstance().getInfoList("a.fshopid = '" + ContextUtil.getShopId() + "'", "");
				if(infoList != null && !infoList.isEmpty())
				{
					int size = infoList.size();
					for(int i = 0; i < size; i++)
					{
						ComParamInfo info = (ComParamInfo) infoList.get(i);
						infoMap.put(info.getCt().getValue() + "", info);
					}
				}
				paramMap.put(ComParamInfo.class.getName(), infoMap);
			}
			
			if(clsName == null || clsName.equals(WxPaySetInfo.class.getName()))
			{
				Configure.isEnable = false;
				
				LinkedList<Info> infoList = IHttpWxPaySet.getInstance().getInfoList("a.fshopid = '" + ContextUtil.getAdminShopId() + "'", "");
				if(infoList != null && !infoList.isEmpty())
				{
					WxPaySetInfo info = (WxPaySetInfo) infoList.get(0);
					String key = UUID.randomUUID().toString();
					WXPay.initSDKConfiguration(key, info.getAppId(), info.getMchID(), info.getSubMchID(), info.getCertLocalPath(), info.getCertPassword());
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static ShopParamInfo getShopParamInfo()
	{
		if(paramMap != null && paramMap.containsKey(ShopParamInfo.class.getName()))
			return (ShopParamInfo) paramMap.get(ShopParamInfo.class.getName());
		
		return null;
	}
	
	public static ComParamInfo getComParamInfo(ComType ct)
	{
		if(paramMap != null && paramMap.containsKey(ComParamInfo.class.getName()))
		{
			HashMap<String, ComParamInfo> infoMap = (HashMap<String, ComParamInfo>) paramMap.get(ComParamInfo.class.getName());
			if(infoMap.containsKey(ct.getValue() + ""))
				return infoMap.get(ct.getValue() + "");
		}
		
		return null;
	}
	

	public static boolean isBarCodeSale()
	{
		ShopParamInfo sp = getShopParamInfo();
		if(sp != null)
			return sp.getIsBarCode();
		return false;
	}
	

}
