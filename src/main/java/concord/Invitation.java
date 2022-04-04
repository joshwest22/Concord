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
	
}
