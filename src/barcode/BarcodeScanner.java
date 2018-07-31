package barcode;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

import bas.sys.comparam.ComParamInfo;
import cache.ParamData;
import em.ComType;
import listener.BarcodeListener;

public class BarcodeScanner implements SerialPortEventListener {
	protected static BarcodeScanner instance =  null;
	protected static HashMap<String, BarcodeListener> lsMap = new HashMap<String, BarcodeListener>();
	protected static LinkedList<BarcodeListener> lsList = new LinkedList<BarcodeListener>();
	
	protected boolean isClose = false;
    protected boolean isStop = false;
   
    protected SerialPort serialPort;
    protected CommPortIdentifier portId;
    protected InputStream inputStream;
    protected StringBuffer buf = new StringBuffer();
    protected String prvBarCode = "";

    public static void startScanner()
    {
    	if(instance == null)
    		instance = new BarcodeScanner();
    	else
    		instance.startScan();
    }
    
    public static void stopScanner()
    {
    	if(instance != null)
    	{
    		instance.ClosePort();
    	}
    }
    
    public static void addListener(String key, BarcodeListener ls){
		lsMap.put(key, ls);
		lsList.addLast(ls);
	}
	
	public static void removeListener(String key)
	{
		BarcodeListener ls = lsMap.get(key);
		lsList.remove(ls);
		lsMap.remove(key);
	}
	
	protected static void actionReadBarcode(String barcode) throws Exception
	{
		for(BarcodeListener ls : lsList)
		{
			ls.actionReadBarcode(barcode);
		}
	}
    
    
	
	
	
	
	
	private BarcodeScanner()
	{
		lsMap = new HashMap<String, BarcodeListener>();
		lsList = new LinkedList<BarcodeListener>();
		
		startScan();
	}
	
	protected void startScan() {
		if (serialPort != null)
			serialPort.close();
		
		InitPort();
	}

    public boolean checkPort()
    {
        if(serialPort!=null)
            return true;
        else
            return false;           
    }
    
    public void InitPort() 
    {
    	ComParamInfo paramInfo = ParamData.getComParamInfo(ComType.Barcode);
        if(paramInfo != null )
        {
        	try {
        		if(inputStream != null)
            		inputStream.close();
        		
            	if(serialPort!=null)
            	{
            		serialPort.notifyOnDataAvailable(false);
                    serialPort.removeEventListener();
            		serialPort.close();
            		serialPort = null;
            	}
            	
                portId = CommPortIdentifier.getPortIdentifier(paramInfo.getComName());
                serialPort = (SerialPort) portId.open("BarcodeScanner", 3000);// Serial_Communication
                serialPort.addEventListener(this);

                serialPort.setSerialPortParams(paramInfo.getBoudIndex(), paramInfo.getDataBits(), paramInfo.getStopBits(), paramInfo.getParityV());
                serialPort.notifyOnDataAvailable(true);
                
                serialPort.setInputBufferSize(200);
                
                inputStream = serialPort.getInputStream();
                isClose = false;
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
    }     
    
    public boolean ClosePort() 
    {
        isClose = true;
        if(inputStream != null)
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
    	if(serialPort!=null)
    	{
    		serialPort.notifyOnDataAvailable(false);
            serialPort.removeEventListener();
    		serialPort.close();
    		serialPort = null;
    	}
    	
    	return false;
    }

    public void serialEvent(SerialPortEvent evt) 
    {
        if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE) 
        {
            byte[] readBuffer = new byte[256];
            int numBytes = 0;
            try {
				numBytes = inputStream.read(readBuffer);    
				String chs = new String(readBuffer).substring(0, numBytes);
	            
				boolean isBarCode = true;
				if(chs.length() > 0)
				{
					int val = chs.charAt(chs.length() - 1);
					isBarCode = val == 13;
				}
				
				
				if(!isBarCode)
				{
					buf.append(chs);
				}
				else
				{
					String barCode = buf.toString();
					if(!isStop && !"".equals(barCode))
		            {
						System.out.println("生效的条形码：" + barCode);
		            	actionReadBarcode(barCode);
		            }
					
					prvBarCode = barCode;
					buf = new StringBuffer();
				}
				
				
				System.out.println("当前缓存：" + buf.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}
}
