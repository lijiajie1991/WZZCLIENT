package bas.sys.menu;

import bean.IHttpSys;


/**
  * @说明 
  * @作者 黎嘉杰 
  * @日期 2016年9月11日 上午2:00:06 
  */
public class IHttpMenu extends IHttpSys {
	protected static IHttpMenu instance = null;
    
    protected IHttpMenu() {
        super("IHttpMenu");
    }
    
    public static IHttpMenu getInstance()
    {
    	if(instance == null)
    		instance = new IHttpMenu();
    	return instance;
    }

}
