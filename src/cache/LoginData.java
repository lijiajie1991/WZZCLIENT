package cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import bas.sys.user.UserInfo;

public class LoginData {
	private static LinkedList<UserInfo> data = new LinkedList<UserInfo>();
	private static String fileName = "lg.dat";
	
	
	public static void loadData()
	{
		try {
			File file = new File(fileName);
			FileInputStream in = new FileInputStream(file);
			ObjectInputStream objIn = new ObjectInputStream(in);
			data = (LinkedList<UserInfo>) objIn.readObject();
			objIn.close();
		} catch (Exception e) {
			System.out.println("read object failed");
			e.printStackTrace();
		}
	}
	
	public static void saveData()
	{
		File file = new File(fileName);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(data);
			objOut.flush();
			objOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static LinkedList<UserInfo> getData()
	{
		return data;
	}
	
	public static void setData(LinkedList<UserInfo> d)
	{
		data = d;
	}
	

}
