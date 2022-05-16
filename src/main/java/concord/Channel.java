package concord;

import java.util.ArrayList;

public class Channel
{
	String channelName;
	Group myGroup;
	Boolean isLocked = false;
	ArrayList<Integer> allowedUserIDs;
	ArrayList<ReactionMessage> messageLog;
	//ArrayList<ReactionMessage> reactionMessageLog;
	
	public Channel()
	{
		this.channelName = "default channel name";
		Group newGroup = new Group();
		this.myGroup = newGroup;
		this.isLocked = false;
		ArrayList<Integer> allowedUsers = new ArrayList<Integer>();
		this.allowedUserIDs = allowedUsers;
//		ArrayList<Message> msgs = new ArrayList<Message>();
//		this.messageLog = msgs;
		ArrayList<ReactionMessage> msgs = new ArrayList<ReactionMessage>();
		this.messageLog = msgs;
	}
	
	public Channel(String channelName, Group myGroup, Boolean isLocked, ArrayList<Integer> allowedUsers,
			ArrayList<ReactionMessage> messageLog)
	{
		this.channelName = channelName;
		this.myGroup = myGroup;
		this.isLocked = isLocked;
		this.allowedUserIDs = allowedUsers;
		this.messageLog = messageLog;
	}

	public Channel(String channelName, Group myGroup)
	{
		//a constructor that just assigns the channel name on creation and set everything else to defaults
		this.channelName = channelName;
		this.myGroup = myGroup;
		this.isLocked = false;
		this.allowedUserIDs = new ArrayList<Integer>();
		//this.messageLog = new ArrayList<Message>();
		this.messageLog = new ArrayList<ReactionMessage>();
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
		return allowedUserIDs;
	}

	public void setAllowedUsers(ArrayList<Integer> allowedUsers)
	{
		this.allowedUserIDs = allowedUsers;
	}

	public ArrayList<ReactionMessage> getMessageLog()
	{
		return messageLog;
	}

	public void setMessageLog(ArrayList<ReactionMessage> messageLog)
	{
		this.messageLog = messageLog;
	}
	
	public void sendNewMessage(Message m)
	{
		//convert m to reactionMessage
		ReactionMessage rm = new ReactionMessage();
		rm.setInReplyTo(m.getInReplyTo());
		rm.setIsPinned(m.getIsPinned());
		rm.setSentByUserID(m.getSentBy());
		rm.setText(m.getText());
		rm.setTimestamp(m.getTimestamp());
		//add Message to messageLog
		//messageLog.add(m);//pre reaction message code
		messageLog.add(rm);
	}
	public ArrayList<ReactionMessage> displayAllMessages(Integer userID)
	{
		return getMessageLog();
	}

	public void lockChannel(String channelName, Integer userID)
	{
		if (this.getChannelName() == channelName)
		{
			setIsLocked(true);
			//remove blacklisted users from allowedUsers
			allowedUserIDs.clear();
			//add only the user who created the locked channel
			allowedUserIDs.add(userID);			 
		}
		
	}

	public void unlockChannel(String channelName2, Integer userID)
	{
		if (this.getChannelName() == channelName)
		{
			setIsLocked(false);
			//reset allowedUsers list; not necessary, but ensures cleanliness
			allowedUserIDs.clear();			 
		}
		
	}

	public void addAllowedUser(User adder, Integer addee)
	{
		//adds user to allowedUsers for channel
		//check permission to do things in private channel
		if (myGroup.getRegisteredUsers().get(adder).getCanLockChannel()) //how do I get user from userID in group
		{
			//check whether user is in the channel they are adding someone to
			if (allowedUserIDs.contains(adder.getUserID()))
			{
				allowedUserIDs.add(addee);
			}
		}
		
	}
}


