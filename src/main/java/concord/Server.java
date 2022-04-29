package concord;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Server extends UnicastRemoteObject implements RMIObserved
{
	private Database db;
	private HashMap<Integer, ArrayList<Client>> clientsInGroup;
	ArrayList<RMIObserver> observers = new ArrayList<RMIObserver>();
	
	public Server(Database db, HashMap<Integer, ArrayList<Client>> clientsInGorups, ArrayList<RMIObserver> observers) throws RemoteException
	{
		this.db = db;
		this.clientsInGroup = clientsInGorups;
		this.observers = observers;
	}

	private static final long serialVersionUID = 2580829118905122035L;
	public Server() throws RemoteException
	{
		Database database = new Database();
		this.db = database;
		HashMap<Integer, ArrayList<Client>> groupClients = new HashMap<Integer, ArrayList<Client>>();
		this.clientsInGroup = groupClients;
		ArrayList<RMIObserver> obsrvs = new ArrayList<RMIObserver>();
		this.observers = obsrvs;
	}
	
	
	@Override
	public void addObserver(RMIObserver o) throws RemoteException
	{
		observers.add(o);
	}

	@Override
	public void removeObserver(RMIObserver o) throws RemoteException
	{
		observers.remove(o);
	}
	
	public String notifyObservers()
	{
		for(RMIObserver o : observers)
		{
			try
			{
				return o.notifyFinished();
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String rmiConnected()
	{
		//example rmi test method should be more complicated in implementation
		return notifyObservers();
	}
	
	public Database getDb()
	{
		return db;
	}

	public void setDb(Database db)
	{
		this.db = db;
	}

	public HashMap<Integer, ArrayList<Client>> getClientsInGroup()
	{
		return clientsInGroup;
	}

	public void setClientsInGroup(HashMap<Integer, ArrayList<Client>> clientsInGroup)
	{
		this.clientsInGroup = clientsInGroup;
	}

	public ArrayList<RMIObserver> getObservers()
	{
		return observers;
	}

	public void setObservers(ArrayList<RMIObserver> observers)
	{
		this.observers = observers;
	}

	public void login(Client client, String username, String password)
	{
		//client is added to clientsInGroups during login
		//server verifies credentials
		//server sets client's associated user (until logoff? ad infinum? session timeout?)
		//for all existing users
		for(User user:this.getAllRegisteredUsers())
		{
			//if the username exists
			if(user.getUsername().contains(username)) //users.getUsername().contains(username)
			{
				//AND the password matches (secure version would be if the hash of password matches)
				if(this.getUserByUsername(username).getPassword().equals(password))
				{
					//set the user to be returned and update associated user
					client.setAssociatedUser(user);
					for(Integer currentGroupID = 0;currentGroupID < db.getGroups().size();currentGroupID++)
					{
						ArrayList<Client> groupClients = new ArrayList<Client>();
						//if the current client is not present in this list
						if (!groupClients.contains(client))
						{
							//add client to the clientGroup for each group client user is in
							
							groupClients.add(client);
							clientsInGroup.put(currentGroupID,groupClients); //this needs to have another parameter; should clientsInGroups be a list of all groups a user is in not just one?
						}
					}
					//return user;
				}
			}
		}
	}

	@Override
	public User createUser(String username, String realname, String password) throws MalformedURLException
	{
		db.createUser(username, realname, password);
		User user = getUserByUsername(username);
		return user;
	}
	
	public User createUser(String username, String realname, String password, Integer userID) throws MalformedURLException
	{
		db.createUser(username, realname, password, userID);
		User user = getUserByUsername(username);
		return user;
	}

	@Override
	public Group getGroup(Integer groupID)
	{
		Group group = db.getGroup(groupID);
		return group;
	}

	@Override
	public ArrayList<Group> getUserGroups(Integer userID)
	{
		ArrayList<Group> userGroups = new ArrayList<Group>();
		//look through db.groups to see if the user is in any of them by ID
		for(Integer groupID : db.getListOfGroupIDs()) 
		{
			//for every group, does the group's registeredUsers contain specific user?
			HashMap<User, Role> regUsers = db.getGroup(groupID).getRegisteredUsers();
			Set<User> keys = regUsers.keySet();
			for (User user : keys)
			{
				//if there is a user in registered users that matches user we want, add group to list
				if(user.getUserID().equals(userID))
				{
					Group myGroup = db.getGroup(groupID);
					userGroups.add(myGroup);
				}
			}
		}
		return userGroups; //user not found in groups returns a blank list
	}

	@Override
	public String createChannel(String channelName, Integer userID, Integer groupID)
	{
		db.getGroup(groupID).createChannel(channelName, db.getGroup(groupID));
		return channelName+" was created in "+db.getGroup(groupID).getGroupName()+" by "+db.getUser(userID).getUsername();
	}
	@Override
	public String updateNewChannel(Integer groupID)
	{
		//tell the client to update their channels
		for (RMIObserver o : observers)
		{
			try
			{
				o.updateNewChannel(groupID);
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "New channel created";
		
	}
	@Override
	public String updateNewMessage(Integer groupID)
	{
		//tell each client in a group to update their messageLog
		//TODO clientsInGroup.get(groupID).forEach((client) -> db.getGroup(groupID).getChannels().forEach((channel) -> channel.setMessageLog(db.getGroup(groupID).getChannels().get(i).getMessageLog())));
		for (RMIObserver o : observers)
		{
			try
			{
				o.updateNewMessage(groupID);
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "New message created";
	}

	@Override
	public String messageReceived(String channelName, String message, Integer userID, Integer groupID)
	{
		db.messageReceived(channelName, message, userID, groupID);
		return message+" has been received.";
	}

	@Override
	public String messageReceiveReply(String channelName, String message, Integer userID, Integer groupID,
			Message replyTo)
	{
		db.messageReceivedReply(channelName, message, userID, groupID, replyTo);
		return message+" has been receieved in reply to : "+replyTo.getText();
	}

	@Override
	public ArrayList<Message> viewChannelMessages(String channelName, Integer userID, Integer groupID)
	{
		ArrayList<Message> messages = db.getGroup(groupID).getChannelByName(channelName).displayAllMessages(userID);
		return messages;
	}
	
	@Override
	public String updateNewUser(Integer groupID)
	{
		for (RMIObserver o : observers)
		{
			try
			{
				o.updateNewUser(groupID);
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "New user was created";
	}

	@Override
	public String addUserToGroup(Integer groupID, Integer addingUserID, Integer addedUserID)
	{
		Group group = db.getGroup(groupID);
		group.addNewUser(db.getUser(addingUserID), db.getUser(addedUserID),group.basic);
		return db.getUser(addedUserID).getUsername()+" was added to "+db.getGroup(groupID);
	}

	@Override
	public String removeUserFromGroup(Integer groupID, Integer removingUserID, Integer removedUserID)
	{
		db.getGroup(groupID).getRegisteredUsers().get(db.getUser(removingUserID)).kickUser(db.getUser(removedUserID));
		return db.getUser(removedUserID).getUsername()+" was removed from "+db.getGroup(groupID).getGroupName() +" by "+db.getUser(removingUserID).getUsername();
	}

	@Override
	public String lockChannel(Integer groupID, Integer userID, String channelName)
	{
		db.lockChannel(groupID, userID, channelName);
		return channelName+" locked.";
	}

	@Override
	public String unlockChannel(Integer groupID, Integer userID, String channelName)
	{
		db.unlockChannel(groupID, userID, channelName);
		return channelName+" unlocked.";
	}

	@Override
	public String leaveGroup(Integer groupID, Integer userID)
	{
		db.getGroup(groupID).getRegisteredUsers().get(db.getUser(userID)).leaveGroup(db.getUser(userID),groupID);
		return db.getUser(userID).getUsername()+"left "+db.getGroup(groupID).getGroupName();
	}

	@Override
	public User viewUser(Integer userID)
	{
		User user = db.getUser(userID);
		return user;
	}

	@Override
	public User getUserByUsername(String username)
	{
		
		for (int i = 0;i<db.getListOfUsers().size();i++)
		{
			User user = db.getListOfUsers().get(i);
			if (user.getUsername() == username)
			{
				return user;
			}
		}
		//username not found
		return null;
	}

	@Override
	public Integer getUserIDByName(String username)
	{
		for (int i = 0;i<db.getListOfUsers().size();i++)
		{
			if (db.getListOfUsers().get(i).getUsername().equals(username))
			{
				Integer userID = db.getListOfUsers().get(i).getUserID();
				return userID;
			}
		}
		//username not found
		return null;
	}

	@Override
	public Integer getUserCount(Integer groupID)
	{
		Integer count = db.getGroups().get(groupID).getRegisteredUsers().size();
		return count;
	}

	@Override
	public String addAllowedUser(String channelName, User adder, Integer addeeID, Integer groupID)
	{
		db.getGroup(groupID).getChannelByName(channelName).addAllowedUser(adder, addeeID);
		return adder.getUsername()+" added "+db.getUser(addeeID).getUsername()+" to "+db.getGroup(groupID).getGroupName()+"'s channel "+channelName;
	}

	@Override
	public void blockUser(Integer blockingUserID, Integer blockedUserID)
	{
		db.blockUser(blockingUserID, blockedUserID);
	}
	
	public void unblockUser(Integer unblockingID, Integer unblockedID)
	{
		db.unblockUser(unblockingID, unblockedID);
		
	}

	@Override
	public String pinMessage(String channelName, Integer userID, Integer groupID, Integer messageIndex)
	{
		Message m = db.getGroup(groupID).getChannelByName(channelName).getMessageLog().get(messageIndex);
		m.setIsPinned(true);
		return db.getUser(userID).getUsername()+" pinned '"+m+"' to "+channelName+" in "+db.getGroup(groupID).getGroupName();
	}

	@Override
	public String assignNewRole(Integer changerUserID, Integer changedUserID, Integer groupID, Boolean canKick,
			Boolean canLockChannel, Boolean canAssignRole, Boolean canCreateChannel)
	{
		Role newRole = db.getGroup(groupID).getRegisteredUsers().get(db.getUser(changerUserID)).createRole(changerUserID, "user generated role", db.getGroup(groupID), canKick,
				canLockChannel, canAssignRole, canCreateChannel); //create role with role params
		//assign role using chain above
		db.getGroup(groupID).getRegisteredUsers().get(db.getUser(changerUserID)).assignRole(db.getUser(changedUserID), newRole);
		return newRole.getRoleName()+" was assigned to "+db.getUser(changedUserID).getUsername()+" by "+db.getUser(changerUserID).getUsername();
	}
	
	public String updateInvite(Integer userID)
	{
		//System.out.println("New invite was created.");
		return "New invite was created";
	}

	@Override
	public ArrayList<User> getAllRegisteredUsers()
	{
		//gets a list of all users known in the database
		ArrayList<User> regUsers = db.listOfUsers;
		return regUsers;
	}

	@Override
	public Group createGroup(Integer groupID, String groupName)
	{
		db.createGroup(groupID, groupName);
		Group newGroup = db.getGroup(groupID);
		return newGroup;
	}


	@Override
	public void sendInvitation(Integer invitedUserID, Invitation invite)
			throws RemoteException
	{
		db.getUser(invitedUserID).getPendingInvites().add(invite);
		
	}


	@Override
	public void sendMessage(Message message, Integer groupID, String channelName) throws RemoteException
	{
		//db.getUser(message.getSentBy()).sendMessage(message)
		db.getGroup(groupID).getChannelByName(channelName).getMessageLog().add(message);
		this.updateNewMessage(groupID);
	}
	
}
