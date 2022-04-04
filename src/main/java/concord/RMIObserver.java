package concord;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIObserver extends Remote
{
	public void notifyFinished() throws RemoteException;
}
