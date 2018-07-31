package bas.attendance;

import bean.base.IHttpBase;

public class IHttpAttendance extends IHttpBase {
	protected static IHttpAttendance instance = null;
	
    public IHttpAttendance() {
		super("IHttpAttendance");
	}

    public static IHttpAttendance getInstance()
    {
    	if(instance == null)
    		instance = new IHttpAttendance();
    	return instance;
    }
}
