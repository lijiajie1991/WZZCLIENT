package cache;

import bas.customer.CustomerInfo;
import bas.material.MaterialInfo;
import bas.measureunit.MeasureUnitInfo;
import bas.person.PersonInfo;
import bas.project.ProjectInfo;
import bas.supplier.SupplierInfo;
import common.util.BeanUtil;

public class BaseDataCacheLoader  extends Thread{

	protected String key = null;
	
	public BaseDataCacheLoader(String key)
	{
		super();
		this.key = key;
	}
	
	public void run()
	{
		try{
			if(key == null || CustomerInfo.class.getName().equals(key))
			{
				BaseData.setInfoList(CustomerInfo.class.getName(), BeanUtil.getCustomerInfoList());
			}
			
			if(key == null || PersonInfo.class.getName().equals(key))
			{
				BaseData.setInfoList(PersonInfo.class.getName(), BeanUtil.getPersonInfoList());
			}
			
			if(key == null || ProjectInfo.class.getName().equals(key))
			{
				BaseData.setInfoList(ProjectInfo.class.getName(), BeanUtil.getProjectInfoList());
			}
			
			if(key == null || MaterialInfo.class.getName().equals(key))
			{
				BaseData.setInfoList(MaterialInfo.class.getName(), BeanUtil.getMaterialInfoList());
			}
			
			if(key == null || SupplierInfo.class.getName().equals(key))
			{
				BaseData.setInfoList(SupplierInfo.class.getName(), BeanUtil.getSupplierInfoList());
			}
			
			if(key == null || MeasureUnitInfo.class.getName().equals(key))
			{
				BaseData.setInfoList(MeasureUnitInfo.class.getName(), BeanUtil.getMeasureUnitInfoList());
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
