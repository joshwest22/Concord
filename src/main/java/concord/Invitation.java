package concord;

public class Invitation
{
	public Message inviteMsg;
	public Integer groupID;
	public Integer inviteUserID;
	public Boolean inviteAccepted;
	
	public Invitation()
	{
		
	}

	public Invitation(Message inviteMsg, Integer groupID, Integer inviteUserID, Boolean inviteAccepted)
	{
		this.inviteMsg = inviteMsg;
		this.groupID = groupID;
		this.inviteUserID = inviteUserID;
		this.inviteAccepted = inviteAccepted;
	}
	
}
