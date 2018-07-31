package cache;

public class CacheData {
	public static void loadCache(Class<?> cls)
	{
		if(cls != null)
		{
			if(BaseData.isHasCache(cls))
				BaseData.loadCache(cls);
			
			ParamData.loadParam(cls);
		}
		else
		{
			ParamData.loadParam(null);
			BaseData.loadCache(null);
		}
	}
}
