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
	private HashMap<Integer, ArrayList<Client>> clientsInGroups;
	
	
	public Server(Database db, HashMap<Integer, ArrayList<Client>> clientsInGorups, ArrayList<RMIObserver> observers) throws RemoteException
	{
		this.db = db;
		this.clientsInGroups = clientsInGorups;
		this.observers = observers;
	}

	private static final long serialVersionUID = 2580829118905122035L;
	protected Server() throws RemoteException
	{
		super();
	}
	
	ArrayList<RMIObserver> observers = new ArrayList<RMIObserver>();
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
	
	public void notifyObservers()
	{
		for(RMIObserver o : observers)
		{
			try
			{
				o.notifyFinished();
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void makeDonuts()
	{
		//example rmi test method should be more complicated in implementation
		notifyObservers();
	}
	
	public User login(Client client, String username, String password)
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
				if(this.getUserByName(username).getPassword().equals(password))
				{
					//set the user to be returned and update associated user
					//client.setAssociatedUser(user);
					return user;
				}
			}
		}
		return null;
	}

	@Override
	public User createUser(String username, String realname, String password) throws MalformedURLException
	{
		db.createUser(username, realname, password);
		User user = getUserByName(username);
		return user;
	}

	@Override
	public Group getGroup(Integer groupID)
	{
		Group group = db.getGroup(groupID);
		return group;
	}

	@Override
	public ArrayList<Group> getUserGroups(Integer userID, String groupName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createChannel(String channelName, Integer userID, Integer groupID)
	{
		db.getGroup(groupID).getRegisteredUsers().get(db.getUser(userID)).createChannel(channelName);
		return channelName+" was created in "+db.getGroup(groupID).getGroupName()+" by "+db.getUser(userID).getUsername();
	}
	
	public void updateNewChannel(Integer groupID)
	{
		//TODO
	}
	
	public void updateNewMessage(Integer gorupID)
	{
		//TODO
	}

	@Override
	public String messageReceived(String channelName, String message, Integer userID, Integer groupID)
	{
		db.messageReceived(channelName, message, userID, groupID);
		return message+" has been received.";
	}

	@Override
	public String messageReceiveReply(String channelName, String message, Integer userID, Integer groupID,
			Message ReplyTo)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Message> viewChannelMessages(String channelName, Integer userID, Integer groupID)
	{
		ArrayList<Message> messages = db.getGroup(groupID).getChannelByName(channelName).displayAllMessages(userID);
		return messages;
	}
	
	public void updateNewUser(Integer groupID)
	{
		//TODO
	}

	@Override
	public String addUserToGroup(Integer groupID, Integer addingUserID, Integer addedUserID)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeUserFromGroup(Integer groupID, Integer removingUserID, Integer removedUserID)
	{
		db.getGroup(groupID).getRegisteredUsers().get(db.getUser(removedUserID)).kickUser(db.getUser(removedUserID));
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
	public User getUserByName(String username)
	{
		for (int i = 0;i<db.listOfUsers.size();i++)
		{
			if (db.listOfUsers.get(i).getUsername().equals(username))
			{
				User user = db.listOfUsers.get(i);
				return user;
			}
		}
		//username not found
		return null;
	}

	@Override
	public Integer getUserIDByName(String username)
	{
		for (int i = 0;i<db.listOfUsers.size();i++)
		{
			if (db.listOfUsers.get(i).getUsername().equals(username))
			{
				Integer userID = db.listOfUsers.get(i).getUserID();
				return userID;
			}
		}
		//username not found
		return null;
	}

	@Override
	public ArrayList<User> getAllUsers(Integer groupID)
	{
		//convert hash of registered users, role in group to list of users
		HashMap<User,Role> hashUsers = db.getGroups().get(groupID).getRegisteredUsers();
		Set<User> keySet = hashUsers.keySet();
		ArrayList<User> listOfKeys = new ArrayList<User>(keySet);
		//Collection<Role> values = hashUsers.values();
		//ArrayList<Role> listOfValues = new ArrayList<>(values);
		ArrayList<User> allUsers = listOfKeys;
		return allUsers;
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

	@Override
	public String pinMessage(String channelName, Integer userID, Integer groupID, Integer messageIndex)
	{
		Message m = db.getGroup(groupID).getChannelByName(channelName).getMessageLog().get(messageIndex);
		m.setIsPinned(true);
		return db.getUser(userID).getUsername()+" pinned '"+m+"' to "+channelName+" in "+db.getGroup(groupID).getGroupName();
	}

	@Override
	public String assignNewRole(Integer changerID, Integer changedID, Integer groupID, Boolean canKick,
			Boolean canLockChannel, Boolean canAssignRole, Boolean canCreateChannel)
	{
		Role newRole = db.getGroup(groupID).getRegisteredUsers().get(db.getUser(changerID)).createRole(changerID, "user generated role", db.getGroup(groupID), canKick,
				canLockChannel, canAssignRole, canCreateChannel); //create role with role params
		//assign role using chain above
		db.getGroup(groupID).getRegisteredUsers().get(db.getUser(changerID)).assignRole(db.getUser(changedID), newRole);
		return newRole.getRoleName()+" was assigned to "+db.getUser(changedID).getUsername()+" by "+db.getUser(changerID).getUsername();
	}
	
	public void updateInvite(Integer userID)
	{
		//TODO
	}

	@Override
	public ArrayList<User> getAllRegisteredUsers()
	{
		ArrayList<User> regUsers = db.listOfUsers;
		return regUsers;
	}
	
}
