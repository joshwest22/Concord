package mainapplication;

import backend.ClientInterface;
import backend.ClientSubstitute;

public interface ViewTransitionalModelInterface
{
	public void showMainView();
	
	public void showCreateAccountView();
	
	public void showEditProfileView();
	
	public void showCreateGroupView();
	
	public void showGroupView();
	
	public void showInvitations();
	
	public void showCreateChannelView();
	
	public void showProfilePreviewView();
	
	public void showPinnedMessagesView();
	
	public void showGiveRoleView();
	
	public void showLoginView();
	
	public ClientSubstitute getClientModel();
	
}
