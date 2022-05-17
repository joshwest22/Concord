package concord;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javafx.collections.ObservableList;

public interface RMIObserver extends Remote
{
	public String notifyFinished() throws RemoteException;
	public Group getGroup(Integer groupID) throws RemoteException;
	public User getUserByUsername (String username) throws RemoteException; 
	public boolean login(String enteredName, String enteredPassword) throws RemoteException;
	public String createChannel(String channelName, Integer userID, Integer groupID) throws RemoteException;
	public User createUser(String username, String realname, String password) throws MalformedURLException, RemoteException;
	public Group createGroup(Integer groupID, String groupName) throws RemoteException;
	public void sendInvitation(Integer invitedUserID, Integer groupID) throws RemoteException;
	public void updateNewUser(Integer groupID) throws RemoteException;
	public void updateNewMessage(Integer groupID) throws RemoteException;
	
	public ArrayList<Group> getUserGroups(Integer userID) throws RemoteException;
	public String messageReceived(String channelName, String message, Integer userID, Integer groupID) throws RemoteException;
	public String messageReceiveReply (String channelName, String message, Integer userID, Integer groupID, Message ReplyTo) throws RemoteException; 
	public ArrayList<Message> viewChannelMessages (String channelName, Integer userID, Integer groupID) throws RemoteException;
	public String addUserToGroup (Integer groupID, Integer addingUserID, Integer addedUserID) throws RemoteException;
	public String removeUserFromGroup (Integer groupID, Integer removingUserID, Integer removedUserID) throws RemoteException; 
	public String lockChannel (Integer groupID, Integer userID, String channelName) throws RemoteException;
	public String unlockChannel (Integer groupID, Integer userID, String channelName) throws RemoteException; 
	public String leaveGroup (Integer groupID, Integer userID) throws RemoteException;
	public User viewUser (Integer userID) throws RemoteException;
	public Integer getUserIDByName (String username) throws RemoteException; 
	public Integer getUserCount(Integer groupID) throws RemoteException;
	public String addAllowedUser(String channelName, User adder, Integer addee, Integer groupID) throws RemoteException;
	public void blockUser(Integer blockingUserID, Integer blockedUserID) throws RemoteException; 
	public String pinMessage(String channelName, Integer userID, Integer groupID, Integer messageIndex) throws RemoteException; 
	public String assignNewRole(Integer changerID, Integer changerdID, Integer groupID, Boolean canKick, Boolean canLockChannel, Boolean canAssignRole,
			Boolean canCreateChannel) throws RemoteException;
	public ArrayList<User> getAllRegisteredUsers() throws RemoteException;
	public void sendInvitation(Integer invitedUserID, Invitation invite) throws RemoteException;
	public String updateNewChannel(Integer groupID) throws RemoteException;
	public void sendMessage(Message message, Integer groupID, String channelName) throws RemoteException;
	//public ObservableList<Group> getGroupList();
}
