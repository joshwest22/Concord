package concord;

public class Invitation
{
	public Message inviteMsg;
	public Integer groupID;
	public Integer invitedUserID;
	public Boolean inviteAccepted;
	
	public Invitation()
	{
		Message invMsg = new Message();
		this.inviteMsg = invMsg;
		Integer gID = -1;
		this.groupID = gID;
		Integer invUserID = -1;
		this.invitedUserID = invUserID;
		this.inviteAccepted = false;
	}

	public Invitation(Message inviteMsg, Integer groupID, Integer invitedUserID, Boolean inviteAccepted)
	{
		this.inviteMsg = inviteMsg;
		this.groupID = groupID;
		this.invitedUserID = invitedUserID;
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

	public Integer getInvitedUserID()
	{
		return invitedUserID;
	}

	public void setInvitedUserID(Integer invitedUserID)
	{
		this.invitedUserID = invitedUserID;
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
