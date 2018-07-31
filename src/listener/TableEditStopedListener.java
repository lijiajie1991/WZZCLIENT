package listener;

import listener.event.TblEditStopedEvent;

public interface TableEditStopedListener {
	public void editStopedAction(TblEditStopedEvent e) throws Exception;
}
