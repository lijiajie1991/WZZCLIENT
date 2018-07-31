package card;

import java.util.HashMap;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

import listener.CardNumberListener;

public class CardReader extends Thread
{
	public static String Number = "";
	public static HashMap<String, CardNumberListener> lsMap = new HashMap<String, CardNumberListener>();
	
	byte mode = 0x26;
	byte halt =0x00;
	Pointer p = null ;
	Pointer v = null ;

	public CardReader()
	{
		try {
			p = new Pointer(MemoryBlockFactory.createMemoryBlock(256));
		} catch (NativeException e1) {
		}
		
		try {
			v = new Pointer(MemoryBlockFactory.createMemoryBlock(256));
		} catch (NativeException e1) {
		}
	}

	public String getCarNumber()
	{
		int ret = Function.MF_Getsnr(mode, halt, p, v);
		StringBuffer carNum = new StringBuffer();
		if ( ret == 0)
		{
			for ( int i = 0 ; i < 4 ; i ++)
			{
				try {
					carNum.append(String.format("%02X", Function.b.getAsByte(i)));
				} catch (NativeException e1) {
					e1.printStackTrace();
				}
			}
		}
		return carNum.toString();
	}
	
	public void buzzer()
	{
		int int0 = 18;
		int int1 = 1;
		Function.ControlBuzzer(int0,int1);
	}
	
	public void led()
	{
		int int0 = 3;
		int int1 = 3;
		Function.ControlLED(int0,int1);
	}
	
	public static void addListener(String key, CardNumberListener ls)
	{
		removeListener(key);
		lsMap.put(key, ls);
	}
	
	public static void removeListener(String key)
	{
		if(lsMap.containsKey(key))
			lsMap.remove(key);
	}
	
	public void run()
	{
		while(true)
		{
			String cardNum = getCarNumber();
			cardNum = cardNum != null ? cardNum : "";
			if(!CardReader.Number.equals(cardNum))
			{
				if(!"".equals(cardNum) && !"".equals(cardNum.replace("0", "")))
				{
					buzzer();
					led();
					for(CardNumberListener ls : lsMap.values())
					{
						if(ls != null)
						{
							try {
								ls.actionPerformed(cardNum);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				CardReader.Number = cardNum;
			}
			
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
