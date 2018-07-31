package common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import bas.customer.IHttpCustomer;
import bas.material.IHttpMaterial;
import bas.measureunit.IHttpMeasureUnit;
import bas.person.IHttpPerson;
import bas.project.IHttpProject;
import bas.supplier.IHttpSupplier;
import bean.Info;
import bean.SysBaseInfo;
import bean.base.BaseInfo;
import bean.bill.BillInfo;
import common.db.BeanTableInfo;
import common.db.FilterInfo;
import common.db.FilterItemInfo;
import common.db.SortInfo;
import common.db.SortItemInfo;
import em.CompareType;
import em.SortType;

/**
 * @说明 该类存放Bean相关的静态方法
 * @作者 黎嘉杰
 * @日期 2016-08-27
 */
public class BeanUtil {

	public static LinkedList<Info> getCustomerInfoList() throws Exception
	{
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		filter.addItem(new FilterItemInfo("a.adminshopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		filter.setMkr("#0 or #1 or #2");
		
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		LinkedList<Info> cusList = IHttpCustomer.getInstance().getInfoList(filter, order);
		
		return cusList;
	}
	
	public static LinkedList<Info> getSupplierInfoList() throws Exception
	{
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		filter.addItem(new FilterItemInfo("a.adminshopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		filter.setMkr("#0 or #1 or #2");
		
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		LinkedList<Info> cusList = IHttpSupplier.getInstance().getInfoList(filter, order);
		
		return cusList;
	}
	
	public static LinkedList<Info> getProjectInfoList() throws Exception
	{
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		filter.setMkr("#0 or #1");
		
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		LinkedList<Info> cusList = IHttpProject.getInstance().getInfoList(filter, order);
		
		return cusList;
	}
	
	public static LinkedList<Info> getPersonInfoList() throws Exception
	{
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		filter.addItem(new FilterItemInfo("a.pst", CompareType.EQUAL, 0));
		filter.setMkr("#0 and #1");
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		LinkedList<Info> cusList = IHttpPerson.getInstance().getInfoList(filter, order);
		
		return cusList;
	}
	
	public static LinkedList<Info> getMaterialInfoList() throws Exception
	{
		FilterInfo filter = new FilterInfo();
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getShopId()));
		filter.addItem(new FilterItemInfo("a.shopId", CompareType.EQUAL, ContextUtil.getAdminShopId()));
		filter.setMkr("#0 or #1");
		
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		LinkedList<Info> cusList = IHttpMaterial.getInstance().getInfoList(filter, order);
		
		return cusList;
	}
	
	public static LinkedList<Info> getMeasureUnitInfoList() throws Exception
	{
		FilterInfo filter = new FilterInfo();
		SortInfo order = new SortInfo();
		order.addItem(new SortItemInfo("a.useCount", SortType.DESC));
		LinkedList<Info> cusList = IHttpMeasureUnit.getInstance().getInfoList(filter, order);
		
		return cusList;
	}
	
	
	/**
	 * @功能描述 根据map， 设置当前的bean字段值
	 * @作者 黎嘉杰
	 * @日期 2016-08-26
	 * @param infoMap
	 * @throws Exception
	 */
	public static Info getInfoFromMap(Class<?> cls, Map<String, Object> infoMap) throws Exception
	{
		Info info = (Info) cls.newInstance();
		
		//获取当前Bean所有公共方法， 以方法小写名称为key存放的Map
		HashMap<String, Method> mMap = BeanUtil.getMethods(cls);
		//获取当前Bean所有的字段
		LinkedList<Field> fList = BeanUtil.getFields(cls);
		
		//遍历所有字段
		for(Field f : fList)
		{
			String fName = f.getName().toLowerCase();  //字段的小写名称
			Class<?> ftCls = f.getType(); //字段类型的CLASS
			String ftName = ftCls.getName(); //字段类型的名称
			
			if(!infoMap.containsKey(fName))
				continue;
			Object val = infoMap.get(fName);
			if(val == null)
				continue;
			
			//如果字段属于基本数据类型或者字符串或者日期
			if(ftCls.isEnum() 
					|| "double".equalsIgnoreCase(ftName) 
					|| "int".equalsIgnoreCase(ftName) 
					|| "java.lang.String".equalsIgnoreCase(ftName) 
					|| "boolean".equalsIgnoreCase(ftName)
					|| "java.util.Date".equalsIgnoreCase(ftName)
					|| "java.math.BigDecimal".equalsIgnoreCase(ftName))
			{
				//从map中获取对应的值
				if("double".equalsIgnoreCase(ftName))
				{
					//做空处理并转换为对应的数据类型
					val = val != null ? Double.parseDouble(val.toString()) : 0.0;
				}
				else if("java.math.BigDecimal".equalsIgnoreCase(ftName))
				{
					//做空处理并转换为对应的数据类型， 空时范围为当前日期
					val = val != null ? new BigDecimal(val.toString()) : BigDecimal.ZERO;
				}
				else if("int".equalsIgnoreCase(ftName))
				{
					//做空处理并转换为对应的数据类型
					val = val != null ? Integer.parseInt(val.toString()) : 0;
				}
				else if("java.lang.String".equalsIgnoreCase(ftName))
				{
					//做空处理并转换为对应的数据类型
					val = val != null ? val.toString() : "";
				}
				else if("boolean".equalsIgnoreCase(ftName))
				{
					//做空处理
					val = val != null ? val.toString() : "";
					//转换为对应的数据类型
					val = "true".equalsIgnoreCase(val.toString()) || "1".equalsIgnoreCase(val.toString()) ? true : false;
				}
				else if("java.util.Date".equalsIgnoreCase(ftName))
				{
					//做空处理并转换为对应的数据类型， 空时范围为当前日期
					val = DateTimeUtil.parseStr(val);
				}
				
				//找到对应的方法体,调用方法设置当前bean的值
				Method m = mMap.get("set" + fName);
				if(m != null)
					m.invoke(info, val);
				else
					throw new Exception(cls.getName() + "没有为字段" + fName + "定义set方法");
			}
			else
			{
				//调用方法设置当前bean的字段
				Method m = mMap.get("set" + fName);
				if(m != null)
					m.invoke(info, val);
				else
					throw new Exception(cls.getName() + "没有为字段" + fName + "定义set方法");
			}
		}
		
		return info;
	}
	
	/**
	 * @功能描述 根据制定的Bean对象， 获取当前Bean对象的所有字段
	 * @param cls
	 * @return
	 */
	public static LinkedList<Field> getFields(Class<?> cls)
	{
		//新建列表
		LinkedList<Field> list = new LinkedList<Field>();
		//如果当前class为空， 直接返回
		if(cls == null)
			return list;
		
		//获取当前类对象定义的字段， 这个方法返回的是当前bean定义的字段，不包括父类的
		Field[] fs = cls.getDeclaredFields();
		//把所有字段添加到List中
		for(Field f : fs)
		{
			if(!"serialVersionUID".equalsIgnoreCase(f.getName()))
				list.addLast(f);
		}
		
		//获取父类
		cls = cls.getSuperclass();
		//如果存在父类， 则继续获取
		if(cls != null)
			//调用同一个方法获取父类的字段并添加到List中
			list.addAll(BeanUtil.getFields(cls));
		
		//返回list
		return list;
	}
	
	/**
	 * @功能描述 获取当前类的所有方法， 并存到map中
	 * @作者 黎嘉杰
	 * @param cls
	 * @return
	 */
	public static HashMap<String, Method> getMethods(Class<?> cls)
	{
		//创建map对象
		HashMap<String, Method> mMap = new HashMap<String, Method>(); 
		//获取当前bean的所有方法， 这个方法是获取所有的公共方法， 包括父类的方法
		Method[] mds = cls.getMethods();
		//遍历方法数组， 添加到map中
		for(Method m : mds)
		{
			//以方法名的小写为key
			mMap.put(m.getName().toLowerCase(), m);
		}
		
		return mMap;
	}

	/**
	 * @功能描述 生成当前bean对象的查询SQL
	 * @return 查询SQL
	 * @throws Exception
	 */
	public static String getQuerySql(Class<?> cls) throws Exception
	{
		Info info = (Info) cls.newInstance();
		
		//字段SQL
		StringBuffer fildSql = new StringBuffer();
		//链接SQL
		StringBuffer joinSql = new StringBuffer();
		
		//获取当前对象的数据表相关信息
		BeanTableInfo btInfo = info.getBT();
		
		//获取当前bean所有的字段
		LinkedList<Field> fList = BeanUtil.getFields(cls);
		
		//遍历所有字段
		for(Field f : fList)
		{
			String fName = f.getName().toLowerCase(); //字段名， 小写
			Class<?> ftCls = f.getType(); //字段类型的class
			String ftName = ftCls.getName(); //字段类型名称
			
			
			
			//如果字段类型为基本类型或者为字符串和日期
			if(ftCls.isEnum() 
					|| "double".equalsIgnoreCase(ftName) 
					|| "int".equalsIgnoreCase(ftName) 
					|| "java.lang.String".equalsIgnoreCase(ftName) 
					|| "boolean".equalsIgnoreCase(ftName)
					|| "java.util.Date".equalsIgnoreCase(ftName)
					|| "java.math.BigDecimal".equalsIgnoreCase(ftName))
			{
				//如果为日期的， 用DATE_FORMAT来吧日期转换为字符串
				if("java.util.Date".equalsIgnoreCase(ftName))
					fildSql.append("DATE_FORMAT(a.f" + fName + ", '%Y-%m-%d %T')  as " + fName + ",\n");
				//如果不是日期， 则直接获取
				else
					fildSql.append("a.f" + fName + " as " + fName + ",\n");
			}
			//如果字段为bean对象类型， 则需要链接到对应的数据表
			else if(Info.class.isAssignableFrom(ftCls))
			{
				//创建bean对象， 并获取别名
				Info fInfo = (Info) ftCls.newInstance();
				String alias = fName.substring(0, fName.length() - 4);
				
				fildSql.append(alias + ".fid as " + alias + "_id, \n");
				
				//如果是BaseInfo， 则需要获取编码和名称
				if(fInfo instanceof BaseInfo || fInfo instanceof SysBaseInfo)
				{
					fildSql.append(alias + ".fnumber as " + alias + "_number, \n");
					fildSql.append(alias + ".fname as " + alias + "_name, \n");
				}
				//如果是BillInfo， 则需要获取编码
				else if(fInfo instanceof BillInfo)
				{
					fildSql.append(alias + ".fnumber as " + alias + "_number, \n");
				}
				
				//获取bean的数据库相关信息
				BeanTableInfo fbtInfo = fInfo.getBT();
				//追加当前表格的链接
				joinSql.append("left join " + fbtInfo.getTableName() + " " + alias + " on " + alias + ".fid = a.f" + alias + "id \n");
			}
		}

		//生成真正的查询SQL
		StringBuffer sql = new StringBuffer();
		sql.append("select\n"); 
		sql.append(fildSql.toString()); //追加需要查询的字段
		sql.append("1 as f \n");  //由于最后一个查询字段不能有逗号， 所有要添加这一行
		sql.append("from " + btInfo.getTableName() + " a \n"); //添加主表
		sql.append(joinSql.toString()); //追加相关表的链接
		sql.append("where 1 = 1 \n"); //获取查询SQL语句后， 可能会在后面添加某些过滤条件， 添加这一行后， 追加过滤条件时只需要用and
		return sql.toString();
	}
	
	public static Map<String, Object> getMapInfo(Info info) throws Exception
	{
		Class<?> cls = info.getClass();
		
		Map<String, Object> map = new HashMap<String, Object>();
		//获取当前Bean所有公共方法， 以方法小写名称为key存放的Map
		HashMap<String, Method> mMap = BeanUtil.getMethods(cls);
		//获取当前Bean所有的字段
		LinkedList<Field> fList = BeanUtil.getFields(cls);
		

		//遍历所有字段
		for(Field f : fList)
		{
			String fName = f.getName().toLowerCase();  //字段的小写名称
			//找到对应的方法体,调用方法设置当前bean的值
			Method m = mMap.get("get" + fName);
			if(m != null)
			{
				Object val = m.invoke(info);
				map.put(fName, val);
			}
			else
				throw new Exception(cls.getName() + "没有为字段" + fName + "定义set方法");
		}
		
		return map;
	}

}
