package concord;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Group
{
	ArrayList<Channel> channels = new ArrayList<Channel>();
	HashMap<User,Role> registeredUsers = new HashMap<User,Role>();
	String description;
	URL logo;
	String groupName;
	Integer groupID;
	
	//basic role with no permissions except sendMessage
	public Role basic = new Role("basic",this,false,false,false,false);
	//admin role with all permissions
	public Role admin = new Role("admin",this,true,true,true,true);
	
	public Group(ArrayList<Channel> channels, String description, URL logo,
			String groupName, Integer groupID)
	{
		this.description = description;
		this.logo = logo;
		this.groupName = groupName;
		this.groupID = groupID;
	}
	//alternate constructor
	public Group(Integer groupID, String groupName)
	{
		this.description = "default description; please set me";
		try
		{
			this.logo = new URL("http://google.com"); 
			
		}
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.groupID = groupID;
		this.groupName = groupName;
	}
	
	public Group()
	{
		this(0,"groupName");
	}
	
	@Override
	public String toString()
	{
		return this.groupName;
	}

	public ArrayList<Channel> getChannels()
	{
		return channels;
	}

	public void setChannels(ArrayList<Channel> channels)
	{
		this.channels = channels;
	}

	public HashMap<User, Role> getRegisteredUsers()
	{
		return registeredUsers;
	}

	public void setRegisteredUsers(HashMap<User, Role> registeredUsers)
	{
		this.registeredUsers = registeredUsers;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public URL getLogo()
	{
		return logo;
	}

	public void setLogo(URL logo)
	{
		this.logo = logo;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public Integer getGroupID()
	{
		return groupID;
	}

	public void setGroupID(Integer groupID)
	{
		this.groupID = groupID;
	}
	
	
	public void addNewUser(User adder, User addee, Role addeeRole)
	{
		//adds user to registeredUsers as key and role as value
		if (registeredUsers.get(adder).getCanAssignRole() == true)
		{
			registeredUsers.put(addee,addeeRole);
		}
		
	}
	public void removeUser(User user)
	{
		registeredUsers.remove(user); 
	}
	public Integer getUserCount() 
	{
		return registeredUsers.size();
	}
	public void inviteUser(User inviter, User invitee, Role role)
	{
		//Directly add user with basic permissions/role
		this.addNewUser(inviter, invitee, role);
	}
	public void createChannel(String channelName, Group myGroup)
	{
		Channel newChannel = new Channel(channelName, myGroup);
		channels.add(newChannel);
	}
	public HashMap<User,Role> viewAllMembers()
	{
		//allows users to see all members of a server; alias for getRegisteredUsers()
		return registeredUsers;
	}
	//add getUser method
	
	public Channel getChannelByName(String channelName)
	{
		for(Channel channel : this.channels)
		{
			if(channel.getChannelName().equals(channelName))
			{
				return channel;
			}
		}
		return null;
	}
	
	@Override
	public boolean equals(Object groupObj)
	{
		if(!this.getChannels().equals(((Group) groupObj).getChannels()))
		{
			return false;
		}
		if(!this.getRegisteredUsers().equals(((Group) groupObj).getRegisteredUsers()))
		{
			return false;
		}
		if(!this.getDescription().equals(((Group) groupObj).getDescription()))
		{
			return false;
		}
		if(!this.getLogo().equals(((Group) groupObj).getLogo()))
		{
			return false;
		}
		if(!this.getGroupName().equals(((Group) groupObj).getGroupName()))
		{
			return false;
		}
		if(!this.getGroupID().equals(((Group) groupObj).getGroupID()))
		{
			return false;
		}
	
		return true;
	}
}
