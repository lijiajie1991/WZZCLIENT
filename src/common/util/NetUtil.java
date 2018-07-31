package common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class NetUtil {
	
	public static String getIP()
	{
		try{
			return getPublicIp();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try{
			return getLocalIp();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String getPublicIp() throws Exception
	{
        // 连接 http://1212.ip138.com/ic.asp  
        Connection con = Jsoup.connect("http://1212.ip138.com/ic.asp").timeout(10000);   
        Document doc = con.get();  
        String ip = null;
        
        // 获得包含本机ip的文本串：您的IP是：[xxx.xxx.xxx.xxx] 来自：YY  
        org.jsoup.select.Elements els = doc.body().select("center");  
        for (org.jsoup.nodes.Element el : els) {  
            ip = el.text();   
        }  
        // 从文本串过滤出ip，用正则表达式将非数字和.替换成空串""  
        ip = ip.replaceAll("[^0-9.]", "");   
          
        return ip;
	}
	
	public static String getLocalIp() throws UnknownHostException
	{
		return InetAddress.getLocalHost().getHostAddress(); 
	}
}
