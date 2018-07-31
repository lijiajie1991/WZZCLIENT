package common.util;

import javax.swing.ImageIcon;

public class ImageLoader {
	private static String icoPath = "";
	
	static
	{
		icoPath = System.getProperty("user.dir") + "\\" + "Image/";
		icoPath = icoPath.replace("\\", "/");
	}
	
	public static ImageIcon getIcon(String fileName)
	{
		return new ImageIcon(icoPath + fileName);
	}
}
