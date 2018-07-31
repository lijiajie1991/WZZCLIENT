package common.util;

import bas.sys.shop.IHttpShop;
import bas.sys.shop.ShopInfo;
import bas.sys.user.UserInfo;
import common.db.Context;
import em.ShopType;

public class ContextUtil {
	private static Context ctx = new Context();
	
	public static void setShopInfo(ShopInfo info) throws Exception
	{
		ShopInfo pInfo = info.getParentInfo();
		if(pInfo != null && pInfo.getId() != null && !"".endsWith(pInfo.getId()))
			pInfo = (ShopInfo) IHttpShop.getInstance().getInfo(pInfo.getId());
		info.setParentInfo(pInfo);
		ctx.setShopInfo(info);
	}
	
	public static void setUserInfo(UserInfo info)
	{
		ctx.setUserInfo(info);
	}
	
	public static UserInfo getUserInfo()
	{
		return ctx.getUserInfo();
	}
	
	public static ShopInfo getShopInfo()
	{
		return ctx.getShopInfo();
	}
	
	public static ShopInfo getAdminShopInfo()
	{
		ShopInfo shoInfo = ctx.getShopInfo();
		if(shoInfo != null && shoInfo.getParentInfo() != null)
		{
			return shoInfo.getParentInfo();
		}
		return null;
	}
	
	public static String getUserId()
	{
		if(ContextUtil.getUserInfo() != null)
			return ContextUtil.getUserInfo().getId();
		return null;
	}
	
	public static String getShopId()
	{
		if(ContextUtil.getShopInfo() != null)
			return ContextUtil.getShopInfo().getId();
		return null;
	}
	
	public static String getAdminShopId()
	{
		if(ContextUtil.getAdminShopInfo() != null)
			return ContextUtil.getAdminShopInfo().getId();
		return null;
	}
	
	public static String getShopNumber()
	{
		if(ContextUtil.getShopInfo() != null)
			return ContextUtil.getShopInfo().getNumber();
		return null;
	}
	
	public static Context getSimpleContext()
	{
		Context simple = new Context();
		
		ShopInfo fullShopInfo = ctx.getShopInfo();
		ShopInfo shopInfo = new ShopInfo();
		shopInfo.setId(fullShopInfo.getId());
		shopInfo.setNumber(fullShopInfo.getNumber());
		shopInfo.setName(fullShopInfo.getName());
		shopInfo.setParentInfo(fullShopInfo.getParentInfo());
		simple.setShopInfo(shopInfo);
		
		UserInfo fullUserInfo = ctx.getUserInfo();
		UserInfo userInfo = new UserInfo();
		userInfo.setId(fullUserInfo.getId());
		userInfo.setNumber(fullUserInfo.getNumber());
		userInfo.setName(fullUserInfo.getName());
		simple.setUserInfo(userInfo);
		
		return simple;
	}
	
	public static ShopType getShopType()
	{
		ShopInfo shopInfo = ContextUtil.getShopInfo();
		if(shopInfo != null)
		{
			ShopType st = shopInfo.getSt();
			return st;
		}
		return null;
	}
}
