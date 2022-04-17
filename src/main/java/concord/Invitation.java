package concord;

public class Invitation
{
	public Message inviteMsg;
	public Integer groupID;
	public Integer inviteUserID;
	public Boolean inviteAccepted;
	
	public Invitation()
	{
		Message invMsg = new Message();
		this.inviteMsg = invMsg;
		Integer gID = -1;
		this.groupID = gID;
		Integer invUserID = -1;
		this.inviteUserID = invUserID;
		this.inviteAccepted = false;
	}

	public Invitation(Message inviteMsg, Integer groupID, Integer inviteUserID, Boolean inviteAccepted)
	{
		this.inviteMsg = inviteMsg;
		this.groupID = groupID;
		this.inviteUserID = inviteUserID;
		this.inviteAccepted = inviteAccepted;
	}

	public Message getInviteMsg()
	{
		return inviteMsg;
	}

	public void setInviteMsg(Message inviteMsg)
	{
		this.inviteMsg = inviteMsg;
	}

	public Integer getGroupID()
	{
		return groupID;
	}

	public void setGroupID(Integer groupID)
	{
		this.groupID = groupID;
	}

	public Integer getInviteUserID()
	{
		return inviteUserID;
	}

	public void setInviteUserID(Integer inviteUserID)
	{
		this.inviteUserID = inviteUserID;
	}

	public Boolean getInviteAccepted()
	{
		return inviteAccepted;
	}

	public void setInviteAccepted(Boolean inviteAccepted)
	{
		this.inviteAccepted = inviteAccepted;
	}
	
}
