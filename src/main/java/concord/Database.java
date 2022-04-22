package concord;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Database implements Serializable
{
	private static final long serialVersionUID = -7839546588414426458L;
	transient HashMap<Integer, Group> groups;
	//iterable list of users for XML encoding //turns out this wasn't necessary as HashMaps can be XML encoded
	transient ArrayList<Group> listOfGroups;
	transient ArrayList<Integer> listOfGroupIDs;
		
	HashMap<Integer, User> users;
	//iterable list of users for XML encoding
	transient ArrayList<User> listOfUsers; //use transient to debug XML storage
	transient ArrayList<Integer> listOfUserIDs;
	
	//alternate constructor
	public Database(HashMap<Integer, Group> groups, HashMap<Integer, User> users)
	{
		this.groups = groups;
		
		//set userList to every user in users; thanks to GeeksforGeeks for conversion from hash to list
		Set<Integer> groupsKeySet = groups.keySet();
		ArrayList<Integer> groupsListOfKeys = new ArrayList<Integer>(groupsKeySet);
		Collection<Group> groupsValues = groups.values();
		ArrayList<Group> groupsListOfValues = new ArrayList<>(groupsValues);
		
		this.listOfGroups = groupsListOfValues;
		this.listOfGroupIDs = groupsListOfKeys;
		
		this.users = users;
		
		//set userList to every user in users; thanks to GeeksforGeeks for conversion from hash to list
		Set<Integer> keySet = users.keySet();
		ArrayList<Integer> listOfKeys = new ArrayList<Integer>(keySet);
		Collection<User> values = users.values();
		ArrayList<User> listOfValues = new ArrayList<>(values);
		
		this.listOfUsers = listOfValues;
		this.listOfUserIDs = listOfKeys;
	}
	
	public Database()
	{
		HashMap<Integer, Group> groups = new HashMap<Integer, Group>();
		HashMap<Integer, User> users = new HashMap<Integer, User>();
		this.groups = groups;
		
		//set userList to every user in users; thanks to GeeksforGeeks for conversion from hash to list
		Set<Integer> groupsKeySet = groups.keySet();
		ArrayList<Integer> groupsListOfKeys = new ArrayList<Integer>(groupsKeySet);
		Collection<Group> groupsValues = groups.values();
		ArrayList<Group> groupsListOfValues = new ArrayList<>(groupsValues);
		
		this.listOfGroups = groupsListOfValues;
		this.listOfGroupIDs = groupsListOfKeys;
		
		this.users = users;
		
		//set userList to every user in users; thanks to GeeksforGeeks for conversion from hash to list
		Set<Integer> keySet = users.keySet();
		ArrayList<Integer> listOfKeys = new ArrayList<Integer>(keySet);
		Collection<User> values = users.values();
		ArrayList<User> listOfValues = new ArrayList<>(values);
		
		this.listOfUsers = listOfValues;
		this.listOfUserIDs = listOfKeys;		
	}
	
	public HashMap<Integer, Group> getGroups()
	{
		return groups;
	}
	public void setGroups(HashMap<Integer, Group> groups)
	{
		this.groups = groups;
	}
	public HashMap<Integer, User> getUsers()
	{
		return users;
	}
	public void setUsers(HashMap<Integer, User> users)
	{
		this.users = users;
	}
	
	public ArrayList<Group> getListOfGroups()
	{
		return listOfGroups;
	}

	public void setListOfGroups(ArrayList<Group> listOfGroups)
	{
		this.listOfGroups = listOfGroups;
	}

	public ArrayList<Integer> getListOfGroupIDs()
	{
		return listOfGroupIDs;
	}

	public void setListOfGroupIDs(ArrayList<Integer> listOfGroupIDs)
	{
		this.listOfGroupIDs = listOfGroupIDs;
	}

	public ArrayList<User> getListOfUsers()
	{
		return listOfUsers;
	}

	public void setListOfUsers(ArrayList<User> listOfUsers)
	{
		this.listOfUsers = listOfUsers;
	}

	public ArrayList<Integer> getListOfUserIDs()
	{
		return listOfUserIDs;
	}

	public void setListOfUserIDs(ArrayList<Integer> listOfUserIDs)
	{
		this.listOfUserIDs = listOfUserIDs;
	}

	public void createUser(String username, String realname, String password, Integer userID, URL userPic, String userBio, Boolean onlineStatus)
	{
		//create User and add to users HashMap
		User user = new User(username,realname,password,userID,userPic,userBio,onlineStatus);
		users.put(userID, user);
		listOfUsers.add(user);
		listOfUserIDs.add(userID);
	}
	public void createUser(String username, String realname, String password) throws MalformedURLException
	{
		//create User and add to users HashMap
		User user = new User(username,realname,password);
		users.put(user.getUserID(), user);
		listOfUsers.add(user);
		listOfUserIDs.add(user.getUserID());
	}
	public void createGroup(Integer groupID, String groupName)
	{
		Group group = new Group(groupID, groupName);
		HashMap<User,Role> regUsers = new HashMap<User,Role>();
		group.registeredUsers = regUsers;
		groups.put(groupID, group);
		listOfGroups.add(group);
		listOfGroupIDs.add(groupID);
	}
	public void createChannel(String channelName, Integer userID, Integer groupID)
	{
		//check user contains userID
		if (users.containsKey(userID))
		{
			//check user's role through group's registeredUsers
			Boolean allowed = this.getRole(groupID, userID).getCanCreateChannel();
			if (allowed)
			{
				//call Group's createChannel()
				groups.get(groupID).createChannel(channelName, groups.get(groupID));
			}
		}
	}
	public void messageReceived(String channelName, String msg, Integer userID, Integer groupID)
	{
		//package message in Message and send to role and then to messageLog
		Message m = new Message(msg,userID);
		Role r = getRole(groupID,userID);
		for(Channel ch : r.getGroup().channels)
		{
			if (ch.channelName.equals(channelName))
			{
				r.sendMessage(m, ch);
			}
		}
	}
	public void messageReceivedReply(String channelName, String msg, Integer userID, Integer groupID, Message replyTo)
	{
		//package message in Message and send to role and then to messageLog
		Message m = new Message(msg,userID);
		Role r = getRole(groupID,userID);
		//set this new message as a reply to a previous message
		m.setInReplyTo(replyTo);
		for(Channel ch : r.getGroup().channels)
		{
			if (ch.channelName.equals(channelName))
			{
				r.sendMessage(m, ch);
			}
		}
	}
	public ArrayList<Message> viewChannel(String channelName, Integer userID, Integer groupID)
	{
		//check through all channels for the channel with channelName
		for (Channel c : groups.get(groupID).channels)
		{
			//call channel's displayAllMessages
			if (c.getChannelName() == channelName)
			{
				ArrayList<Message> msgLog = c.getMessageLog();
				ArrayList<Message> clearedMsgList = new ArrayList<Message>();
				  for (Message m : msgLog)
				  {
					  //if message not sent by blocked user, add it to clearedMsgList
					  if (!getUser(userID).getBlockedUserIDs().contains(m.sentByUserID))
					  {
						  clearedMsgList.add(m);
					  }
				  }
				  return clearedMsgList;
			}
		}
		ArrayList<Message> emptyList = new ArrayList<Message>();
		return emptyList;
	}
	public void blockUser(Integer blockerID, Integer blockeeID)
	{
		users.get(blockerID).blockUser(blockeeID);
	}
	public void unblockUser(Integer unblockerID, Integer unblockeeID)
	{
		users.get(unblockerID).unblockUser(unblockeeID);
	}
	public User getUser(Integer userID)
	{
		User user = users.get(userID);
		return user;
	}
	public Role getRole(Integer groupID, Integer userID)
	{
		Role role = groups.get(groupID).getRegisteredUsers().get(users.get(userID));
		return role;
	}
	public void lockChannel(Integer groupID, Integer userID, String channelName)
	{
		for (int i = 0; i<this.getGroups().get(groupID).getChannels().size();i++)
		{
			this.getGroups().get(groupID).getChannels().get(i).lockChannel(channelName, userID);
		}
	}
	public void unlockChannel(Integer groupID, Integer userID, String channelName)
	{
		for (int i = 0; i<this.getGroups().get(groupID).getChannels().size();i++)
		{
			this.getGroups().get(groupID).getChannels().get(i).unlockChannel(channelName, userID);
		}
	}
	
	/* XML Storage */
	public void storeToDisk()
	{
		XMLEncoder encoder=null;
		try
		{
			encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("ConcordDatabase.xml")));
		}
		catch(FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File ConcordDatabase.xml");
		}
		encoder.writeObject(this);//sub this/users/groups for one thing in file at a time
		encoder.close();
	}
	
	public static Database loadFromDisk()
	{
		XMLDecoder decoder=null;
		try 
		{
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("ConcordDatabase.xml")));
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("ERROR: File ConcordDatabase.xml not found");
		}
		Database d = (Database) decoder.readObject();
		return d;
	}
	
	public boolean equals(Database that)
	{
		//users
		if (users.size() != that.users.size())
		{
			return false;
		}
		for (User u : listOfUsers) 
		{
			if (!that.contains(u))
			{
				return false; 
			}
		}
		//groups
		if (groups.size() != that.groups.size())
		{
			return false;
		}
		for (Group g : listOfGroups)
		{
			if (!that.contains(g))
			{
				return false; //needed to add equals for group
			}
		}
		return true;
	}

	public boolean contains(User user)
	{
		for (User u: listOfUsers)
		{
			if (u.equals(user))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(Group group)
	{
		for (Group g: listOfGroups)
		{
			if (g.equals(group))
			{
				return true;
			}
		}
		return false;
	}

	public Group getGroup(Integer groupID)
	{
		Group group = groups.get(groupID);
		return group;
	}

	public void createUser(String username, String realname, String password, Integer userID)
	{
		//create User and add to users HashMap
		User user = new User(username,realname,password,userID);
		users.put(user.getUserID(), user);
		listOfUsers.add(user);
		listOfUserIDs.add(user.getUserID());
	}
}
