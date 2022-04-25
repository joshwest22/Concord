package concord;
//server should only need to be exposed to this
public interface ClientInterface
{
	boolean login(String username, String password);
	//update methods here
	void updateNewUser();
	void updateNewMessage();
	void updateNewChannel();
	void updateNewInvitation();
	
}
