package bean;

import java.util.LinkedList;

import bas.sys.shop.ShopInfo;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.util.ContextUtil;
import em.CompareType;
import em.ShopType;


/**
  * @说明 前台调用方法
  * @作者 黎嘉杰 
  * @日期 2016年9月11日 上午1:59:21 
  */
public abstract class IHttpHead extends IHttp{
	protected IHttpHead(String path) {
		super(path);
	}

	public LinkedList<Info> getInfoList(String filter, String order) throws Exception 
	{
		StringBuffer defFilter = new StringBuffer();
		ShopInfo shopInfo = ContextUtil.getShopInfo();
		if(shopInfo != null && shopInfo.getSt() == ShopType.STORE)
		{
			defFilter.append(" (a.fshopId = '" + ContextUtil.getShopId() + "' or a.fshopId = '" + ContextUtil.getAdminShopId() + "') ");
		}
		else if(shopInfo != null && shopInfo.getId() != null)
		{
			defFilter.append(" (a.fshopId = '" + ContextUtil.getShopId() + "' or a.fshopId = '" + ContextUtil.getShopId() + "') ");
		}
		
		if(filter == null || "".equals(filter))
			filter = defFilter.toString();
		else if(!"".equals(defFilter.toString()))
			filter = defFilter.toString() + " and (" + filter + ") ";
				
		return super.getInfoList(filter, order);
	
	}
	
	public LinkedList<Info> getInfoList(FilterInfo filter, SortInfo order) throws Exception {
		
		FilterInfo defFilter = new FilterInfo();
	
		if(ContextUtil.getShopInfo().getSt() == ShopType.STORE)
		{
			defFilter .addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
			defFilter .addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
			defFilter.setMkr("#0 or #1");
		}
		else
		{
			defFilter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
			defFilter.addItem(new FilterItemInfo("a.adminshopId", CompareType.EQUAL, ContextUtil.getShopId()));
			defFilter.setMkr("#0 or #1");
		}
		
		if(filter != null)
			defFilter.mergeFilter(filter, "and");
		
		return super.getInfoList(defFilter, order);
	}

	// --------------------------------------------数据库保存删除相关的方法-----------------------------------------------------
	/**
	 * @功能描述 保存方法
	 * @作者 黎嘉杰
	 * @throws Exception
	 */
	public String save(Info info) throws Exception {
		verifyToSave(info);
		
		HeadInfo hInfo = (HeadInfo) info;
		hInfo.setShopId(ContextUtil.getShopInfo().getId());
		
		return super.save(hInfo);
	}

	// --------------------------------------------以下为校验方法-----------------------------------------------------

	/**
	 * @校验是否可以保存， 不可以就抛出异常， 异常信息里包含不能保存的原因
	 * 
	 * @throws Exception
	 */
	protected void verifyToSave(Info info) throws Exception {
		HeadInfo hInfo = (HeadInfo) info;
		if(hInfo.getShopId() != null && !"".equals(hInfo.getShopId()))
		{
			String shopId = hInfo.getShopId();
			if(!ContextUtil.getShopInfo().getId().equals(shopId))
				throw new Exception("不允许修改其他店铺的单据！");
		}
	}

}
