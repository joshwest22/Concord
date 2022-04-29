package concord;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Client extends UnicastRemoteObject implements RMIObserver, Serializable
{	
	private User associatedUser;
	private Server serverContact;
	private ArrayList<Integer> associatedGroupIDs;
	
	private int currentSelectedGroupID;
	private String currentSelectedChannelName;
	private User currentSelectedUser;
	
	private ObservableList<Group> groupList;
	private ObservableList<Channel> channelList;
	//private ObservableList<User> userList;
	
	public Client(User associatedUser, Server serverContact, ArrayList<Integer> associatedGroupIDs, String name) throws RemoteException
	{
		this.associatedUser = associatedUser;
		this.serverContact = serverContact;
		this.associatedGroupIDs = associatedGroupIDs;
		this.clientName = name;
		//this.groupList = FXCollections.observableList(groupList); //not sure this is exactly the right syntax
		//this.channelList = FXCollections.observableList(channelList);
	}

	public Client() throws RemoteException
	{
		User user = new User();
		this.associatedUser = user;
		Server server = new Server();
		this.serverContact = server;
		ArrayList<Integer> myGroupIDs = new ArrayList<Integer>();
		this.associatedGroupIDs = myGroupIDs;
		this.clientName = "Name: 'client'";
		//this.groupList = FXCollections.observableList(groupList); //not sure this is exactly the right syntax
		//this.channelList = FXCollections.observableList(channelList);
	}
	
	public Client(String username, String password) throws RemoteException
	{
		User user = new User(username,password);
		this.associatedUser = user;
		Server server = new Server();
		this.serverContact = server;
		ArrayList<Integer> myGroupIDs = new ArrayList<Integer>();
		this.associatedGroupIDs = myGroupIDs;
		this.clientName = "Name: 'client'";
		//this.groupList = FXCollections.observableList(groupList); //not sure this is exactly the right syntax
		//this.channelList = FXCollections.observableList(channelList);
	}

	private static final long serialVersionUID = -6394155878301235563L;
	
	String clientName = "Name: 'client'";
	
	@Override
	public String notifyFinished()
	{
		System.out.println(clientName+" was called");
		return clientName+" was called";
	}
	
	@Override
	public boolean login(String enteredName, String enteredPassword)
	{
		String clientUsername = this.getAssociatedUser().getUsername();
		String clientPassword = this.getAssociatedUser().getPassword();
		//if login already exists
		
		if (enteredName.equals(clientUsername) && enteredPassword.equals(clientPassword))
		{
			//set the online status to true
			associatedUser.setOnlineStatus(true);
			//first time login, but don't create a new user for a wrong password
			serverContact.login(this, clientUsername, clientPassword);
			
			try
			{
				serverContact.createUser(this.getAssociatedUser().getUsername(), this.getAssociatedUser().getRealname(), this.getAssociatedUser().getPassword(), this.getAssociatedUser().getUserID());
				//should I call updateNewUser to alert the server to a change?
				
			} catch (MalformedURLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		else
		{
			//wrong password
			return false;
		}
		
	}
	
	public User getAssociatedUser()
	{
		return associatedUser;
	}

	public void setAssociatedUser(User associatedUser)
	{
		this.associatedUser = associatedUser;
	}

	public Server getServerContact()
	{
		return serverContact;
	}

	public void setServerContact(Server serverContact)
	{
		this.serverContact = serverContact;
	}

	public ArrayList<Integer> getAssociatedGroupIDs()
	{
		return associatedGroupIDs;
	}

	public void setAssociatedGroupIDs(ArrayList<Integer> associatedGroupIDs)
	{
		this.associatedGroupIDs = associatedGroupIDs;
	}

	public String getClientName()
	{
		return clientName;
	}

	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	public void updateNewUser()
	{
		this.serverContact.updateNewUser(currentSelectedGroupID); //check if new user in one group or all groups?
	}
	
	public void updateNewMessage() //client give the server the new message
	{
		this.serverContact.updateNewMessage(currentSelectedGroupID); //how to get current selected groupID
		System.out.println("New message was created.");
	}
	
	public void updateNewChannel()
	{
		this.serverContact.updateNewChannel(currentSelectedGroupID); //how to get current selected groupID; when button selected
		System.out.println("New channel was created.");
	}
	
	public void updateNewInvitation()
	{
		this.serverContact.updateInvite(associatedUser.getUserID());
	}
	
	//Helper methods for corresponding Server methods
	public void sendMessage(String message)
	{
		try
		{
			Message msg = this.getAssociatedUser().sendMessage(message);
			this.getServerContact().sendMessage(msg, currentSelectedGroupID, currentSelectedChannelName);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendInvitation(Integer invitedUserID, Integer groupID)
	{
		Message inviteMsg = new Message("Want to join my group?", associatedUser.getUserID());
		Invitation invite = new Invitation(inviteMsg,groupID,invitedUserID,false);
		
		try
		{
			serverContact.sendInvitation(invitedUserID, invite);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void addGroupID(int i)
	{
		this.getAssociatedGroupIDs().add(i);		
	}

	public int getCurrentSelectedGroupID()
	{
		return currentSelectedGroupID;
	}

	public void setCurrentSelectedGroupID(int currentSelectedGroupID)
	{
		this.currentSelectedGroupID = currentSelectedGroupID;
	}

	public String getCurrentSelectedChannelName()
	{
		return currentSelectedChannelName;
	}

	public void setCurrentSelectedChannelName(String currentSelectedChannelName)
	{
		this.currentSelectedChannelName = currentSelectedChannelName;
	}

	public User getCurrentSelectedUser()
	{
		return currentSelectedUser;
	}

	public void setCurrentSelectedUser(User currentSelectedUser)
	{
		this.currentSelectedUser = currentSelectedUser;
	}

	public ObservableList<Group> getSelectedGroup()
	{
		return groupList;
	}

	public void setSelectedGroup(ObservableList<Group> selectedGroup)
	{
		this.groupList = selectedGroup;
	}

	public ObservableList<Channel> getSelectedChannel()
	{
		return channelList;
	}

	public void setSelectedChannel(ObservableList<Channel> selectedChannel)
	{
		this.channelList = selectedChannel;
	}

	public ObservableList<Group> getGroupList()
	{
		return groupList;
	}

	public void setGroupList(ObservableList<Group> groupList)
	{
		this.groupList = groupList;
	}

	public ObservableList<Channel> getChannelList()
	{
		return channelList;
	}

	public void setChannelList(ObservableList<Channel> channelList)
	{
		this.channelList = channelList;
	}
	
	@Override
	public User createUser(String username, String realname, String password)
			throws MalformedURLException, RemoteException
	{
		serverContact.createUser(username, realname, password);
		User user = getUserByUsername(username);
		//userList.add(user); //add this back when observable lists figured out
		return user;
	}

	@Override
	public Group createGroup(Integer groupID, String groupName) throws RemoteException
	{
		serverContact.createGroup(groupID, groupName);
		//groupList.add(getGroup(groupID));
		return getGroup(groupID);
	}
	
	@Override
	public String createChannel(String channelName, Integer userID, Integer groupID) throws RemoteException
	{
		serverContact.createChannel(channelName, userID, groupID);
		//channelList.add(getServerContact().getGroup(groupID).getChannelByName(channelName));
		return channelName+"was created in group "+groupID+" on client";
	}
	

	@Override
	public void updateNewUser(Integer groupID) throws RemoteException
	{
		this.serverContact.updateNewUser(groupID);
		
	}

	@Override
	public void updateNewMessage(Integer groupID) throws RemoteException
	{
		this.serverContact.updateNewMessage(groupID);
		
	}

	@Override
	public Group getGroup(Integer groupID) throws RemoteException
	{
		return serverContact.getGroup(groupID);
	}

	@Override
	public User getUserByUsername(String username) throws RemoteException
	{
		return serverContact.getUserByUsername(username);
	}

	@Override
	public ArrayList<Group> getUserGroups(Integer userID) throws RemoteException
	{
		return serverContact.getUserGroups(userID);
	}

	@Override
	public String messageReceived(String channelName, String message, Integer userID, Integer groupID)
			throws RemoteException
	{
		return serverContact.messageReceived(channelName, message, userID, groupID);
	}

	@Override
	public String messageReceiveReply(String channelName, String message, Integer userID, Integer groupID,
			Message ReplyTo) throws RemoteException
	{
		return serverContact.messageReceiveReply(channelName, message, userID, groupID, ReplyTo);
	}

	@Override
	public ArrayList<Message> viewChannelMessages(String channelName, Integer userID, Integer groupID)
			throws RemoteException
	{
		return serverContact.viewChannelMessages(channelName, userID, groupID);
	}

	@Override
	public String addUserToGroup(Integer groupID, Integer addingUserID, Integer addedUserID) throws RemoteException
	{
		return serverContact.addUserToGroup(groupID, addingUserID, addedUserID);
	}

	@Override
	public String removeUserFromGroup(Integer groupID, Integer removingUserID, Integer removedUserID)
			throws RemoteException
	{
		return serverContact.removeUserFromGroup(groupID, removingUserID, removedUserID);
	}

	@Override
	public String lockChannel(Integer groupID, Integer userID, String channelName) throws RemoteException
	{
		return serverContact.lockChannel(groupID, userID, channelName);
	}

	@Override
	public String unlockChannel(Integer groupID, Integer userID, String channelName) throws RemoteException
	{
		return serverContact.unlockChannel(groupID, userID, channelName);
	}

	@Override
	public String leaveGroup(Integer groupID, Integer userID) throws RemoteException
	{
		return serverContact.leaveGroup(groupID, userID);
	}

	@Override
	public User viewUser(Integer userID) throws RemoteException
	{
		return serverContact.viewUser(userID);
	}

	@Override
	public Integer getUserIDByName(String username) throws RemoteException
	{
		return serverContact.getUserIDByName(username);
	}

	@Override
	public Integer getUserCount(Integer groupID) throws RemoteException
	{
		return serverContact.getUserCount(groupID);
	}

	@Override
	public String addAllowedUser(String channelName, User adder, Integer addee, Integer groupID) throws RemoteException
	{
		return serverContact.addAllowedUser(channelName, adder, addee, groupID);
	}

	@Override
	public void blockUser(Integer blockingUserID, Integer blockedUserID) throws RemoteException
	{
		serverContact.blockUser(blockingUserID, blockedUserID);
	}

	@Override
	public String pinMessage(String channelName, Integer userID, Integer groupID, Integer messageIndex)
			throws RemoteException
	{
		return serverContact.pinMessage(channelName, userID, groupID, messageIndex);
	}

	@Override
	public String assignNewRole(Integer changerID, Integer changerdID, Integer groupID, Boolean canKick,
			Boolean canLockChannel, Boolean canAssignRole, Boolean canCreateChannel) throws RemoteException
	{
		return serverContact.assignNewRole(changerID, changerdID, groupID, canKick, canLockChannel, canAssignRole, canCreateChannel);
	}

	@Override
	public ArrayList<User> getAllRegisteredUsers() throws RemoteException
	{
		return serverContact.getAllRegisteredUsers();
	}

	@Override
	public void sendInvitation(Integer invitedUserID, Invitation invite) throws RemoteException
	{
		serverContact.sendInvitation(invitedUserID, invite);
	}

	@Override
	public String updateNewChannel(Integer groupID) throws RemoteException
	{
		return serverContact.updateNewChannel(groupID);
	}

	@Override
	public void sendMessage(Message message, Integer groupID, String channelName) throws RemoteException
	{
		serverContact.sendMessage(message, groupID, channelName);
	}

	

}
