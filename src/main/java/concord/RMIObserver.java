package concord;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIObserver extends Remote
{
	public String notifyFinished() throws RemoteException;
}
