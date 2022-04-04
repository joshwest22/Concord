package concord;

public class Role
{
	private String roleName;
	private Group myGroup;
	private Boolean canKick;
	private Boolean canLockChannel;
	private Boolean canAssignRole;
	private Boolean canCreateChannel;
	
	public Role(String roleName, Group group, Boolean canKick, Boolean canLockChannel, Boolean canAssignRole,
			Boolean canCreateChannel)
	{
		this.roleName = roleName;
		this.myGroup = group;
		this.canKick = canKick;
		this.canLockChannel = canLockChannel;
		this.canAssignRole = canAssignRole;
		this.canCreateChannel = canCreateChannel;
	}
	
	public String getRoleName()
	{
		return roleName;
	}
	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}
	public Group getGroup()
	{
		return myGroup;
	}
	public void setGroup(Group group)
	{
		this.myGroup = group;
	}
	public Boolean getCanKick()
	{
		return canKick;
	}
	public void setCanKick(Boolean canKick)
	{
		this.canKick = canKick;
	}
	public Boolean getCanLockChannel()
	{
		return canLockChannel;
	}
	public void setCanLockChannel(Boolean canLockChannel)
	{
		this.canLockChannel = canLockChannel;
	}
	public Boolean getCanAssignRole()
	{
		return canAssignRole;
	}
	public void setCanAssignRole(Boolean canAssignRole)
	{
		this.canAssignRole = canAssignRole;
	}
	public Boolean getCanCreateChannel()
	{
		return canCreateChannel;
	}
	public void setCanCreateChannel(Boolean canCreateChannel)
	{
		this.canCreateChannel = canCreateChannel;
	}
	
	
	public String kickUser(User user)
	{
		//check permissions
		if (this.canKick)
		{
			//Call Group removeUser(user)
			myGroup.removeUser(user); 
			return "User "+user.getUsername()+" has been kicked.";
		}
		else
		{
			return "You do not have permission to kick users.";
		}
	}
	public String lockChannel(String channelName, Integer userID)
	{
		if (this.canLockChannel)
		{
			//for every channel
			for (Channel c : myGroup.channels)
			{
				//check this is the correct channel
				if (c.getChannelName() == channelName)
				{
					c.setIsLocked(true); 
					//reset allowed users
					c.allowedUsers.clear();
					//only have user that locked channel in allowedList
					c.allowedUsers.add(userID);
					return "Channel "+c.getChannelName()+" been locked.";
				}
			}
		}
		return "Channel name not found.";
	}
	public String unlockChannel(String channelName, Integer userID)
	{
		if (this.canLockChannel)
		{
			//for every channel
			for (Channel c : myGroup.channels)
			{
				//check this is the correct channel
				if (c.getChannelName() == channelName)
				{
					c.setIsLocked(false); 
					//reset allowed users
					c.allowedUsers.clear();
					//add all users?
					//c.allowedUsers = //get all registered userIDs;
					return "Channel "+c.getChannelName()+" been locked.";
				}
			}
		}
		return "Channel name not found.";
	}
	public String sendMessage(Message m, Channel channel)
	{
		//pass along the message to the channel
		channel.sendNewMessage(m);
		String sentVerification = "Message "+m.getText()+" sent.";
		return sentVerification;
	}
	public String leaveGroup(User user, Integer groupID)
	{
		// user should only be the user calling this
		myGroup.removeUser(user);
		String leftNotice = user.getUsername() + " left the group.";
		return  leftNotice;
	}
	public String assignRole(User userAssigned, Role newRole)
	{
		//does user have permission to assign role
		if (this.canAssignRole)
		{
			myGroup.getRegisteredUsers().put(userAssigned,newRole);
			String roleMsg = "Role "+newRole.getRoleName()+" has been assigned to "+userAssigned;
			return roleMsg;
		}
		else
		{
			return "Role assignment failed. "+newRole.getRoleName()+" does not have permission to assign roles.";
		}
		
	}
	public String createChannel(String channelName)
	{
		if (this.canCreateChannel)
		{
			this.myGroup.createChannel(channelName, myGroup);
			return "Channel "+channelName+" was created.";
		}
		else
		{
			return "Channel creation failed. "+this.getRoleName()+" does not have permission to create channels.";
		}
		
	}
	
	public Role createRole(Integer creatorID, String roleName, Group group,  Boolean canKick,
			Boolean canLockChannel, Boolean canAssignRole, Boolean canCreateChannel)
	{
		if(this.canAssignRole)
		{
			Role newRole = new Role(roleName, group, canKick, canLockChannel, canAssignRole, canCreateChannel);
			return newRole;
		}
		return null;
	}
	//Might need a view channel
}
