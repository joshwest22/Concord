package concord;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIObserved extends Remote
{
	public void addObserver(RMIObserver o) throws RemoteException;
	public void removeObserver(RMIObserver o) throws RemoteException;
	
	public User createUser(String username, String realname, String password) throws MalformedURLException;
	public Group getGroup(Integer groupID);
	public ArrayList<Group> getUserGroups(Integer userID, String groupName);
	public String createChannel(String channelName, Integer userID, Integer groupID);
	public String messageReceived(String channelName, String message, Integer userID, Integer groupID);
	public String messageReceiveReply (String channelName, String message, Integer userID, Integer groupID, Message ReplyTo); 
	public ArrayList<Message> viewChannelMessages (String channelName, Integer userID, Integer groupID);
	public String addUserToGroup (Integer groupID, Integer addingUserID, Integer addedUserID);
	public String removeUserFromGroup (Integer groupID, Integer removingUserID, Integer removedUserID); 
	public String lockChannel (Integer groupID, Integer userID, String channelName);
	public String unlockChannel (Integer groupID, Integer userID, String channelName); 
	public String leaveGroup (Integer groupID, Integer userID);
	public User viewUser (Integer userID);
	public User getUserByName (String username); 
	public Integer getUserIDByName (String username); 
	public ArrayList<User> getAllUsers(Integer groupID); 
	public Integer getUserCount(Integer groupID);
	public String addAllowedUser(String channelName, User adder, Integer addee, Integer groupID);
	public void blockUser(Integer blockingUserID, Integer blockedUserID); 
	public String pinMessage(String channelName, Integer userID, Integer groupID, Integer messageIndex); 
	public String assignNewRole(Integer changerID, Integer changerdID, Integer groupID, Boolean canKick, Boolean canLockChannel, Boolean canAssignRole,
			Boolean canCreateChannel);
	public ArrayList<User> getAllRegisteredUsers();

}
