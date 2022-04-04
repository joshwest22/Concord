package concord;

import java.util.ArrayList;

public class Channel
{
	String channelName;
	Group myGroup;
	Boolean isLocked = false;
	ArrayList<Integer> allowedUsers;
	ArrayList<Message> messageLog;
	
	public Channel(String channelName, Group myGroup, Boolean isLocked, ArrayList<Integer> allowedUsers,
			ArrayList<Message> messageLog)
	{
		this.channelName = channelName;
		this.myGroup = myGroup;
		this.isLocked = isLocked;
		this.allowedUsers = allowedUsers;
		this.messageLog = messageLog;
	}

	public Channel(String channelName, Group myGroup)
	{
		//a constructor that just assigns the channel name on creation and set everything else to defaults
		this.channelName = channelName;
		this.myGroup = myGroup;
		this.isLocked = false;
		this.allowedUsers = new ArrayList<Integer>();
		this.messageLog = new ArrayList<Message>();
	}

	public String getChannelName()
	{
		return channelName;
	}

	public void setChannelName(String channelName)
	{
		this.channelName = channelName;
	}

	public Group getMyGroup()
	{
		return myGroup;
	}

	public void setMyGroup(Group myGroup)
	{
		this.myGroup = myGroup;
	}

	public Boolean getIsLocked()
	{
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked)
	{
		this.isLocked = isLocked;
	}

	public ArrayList<Integer> getAllowedUsers()
	{
		return allowedUsers;
	}

	public void setAllowedUsers(ArrayList<Integer> allowedUsers)
	{
		this.allowedUsers = allowedUsers;
	}

	public ArrayList<Message> getMessageLog()
	{
		return messageLog;
	}

	public void setMessageLog(ArrayList<Message> messageLog)
	{
		this.messageLog = messageLog;
	}
	
	public void sendNewMessage(Message m)
	{
		//add Message to messageLog
		messageLog.add(m);
	}
	public ArrayList<Message> displayAllMessages(Integer userID)
	{
		return getMessageLog();
	}

	public void lockChannel(String channelName, Integer userID)
	{
		if (this.getChannelName() == channelName)
		{
			setIsLocked(true);
			//remove blacklisted users from allowedUsers
			allowedUsers.clear();
			//add only the user who created the locked channel
			allowedUsers.add(userID);			 
		}
		
	}

	public void unlockChannel(String channelName2, Integer userID)
	{
		if (this.getChannelName() == channelName)
		{
			setIsLocked(false);
			//reset allowedUsers list; not necessary, but ensures cleanliness
			allowedUsers.clear();			 
		}
		
	}

	public void addAllowedUser(User adder, Integer addee)
	{
		//adds user to allowedUsers for channel
		//check permission to do things in private channel
		if (myGroup.getRegisteredUsers().get(adder).getCanLockChannel()) //how do I get user from userID in group
		{
			//check whether user is in the channel they are adding someone to
			if (allowedUsers.contains(adder.getUserID()))
			{
				allowedUsers.add(addee);
			}
		}
		
	}
}


