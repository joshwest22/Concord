package concord;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIObserver extends Remote
{
	public String notifyFinished() throws RemoteException;
	public boolean login(String enteredName, String enteredPassword) throws RemoteException;
	public String createChannel(String channelName, Integer userID, Integer groupID) throws RemoteException;
	public void sendInvitation(Integer invitedUserID, Integer groupID) throws RemoteException;
	public void updateNewChannel() throws RemoteException;
	public void updateNewUser(Integer groupID) throws RemoteException;
	public void updateNewMessage(Integer groupID) throws RemoteException;
}
