package common.util;

import cache.LoginData;

public class CacheUtil {
	public static void loadCache()
	{
		LoginData.loadData();
	}
}
