/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月9日 下午1:27:49 
  */
package common.properties;

import java.io.FileInputStream;
import java.util.Properties;

/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月9日 下午1:27:49 
  */
public class UrlProperties {
	private static Properties pp = null;
	
	static{
		pp = new Properties();
		try {
			String path = UrlProperties.class.getClassLoader().getResource("").toURI().getPath();
			FileInputStream in = new FileInputStream(path + "/bean.properties");
			pp.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperties(String key)
	{
		return pp.getProperty(key);
	}

}
