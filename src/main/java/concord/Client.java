package concord;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Client extends UnicastRemoteObject implements RMIObserver, Serializable
{	
	private User associatedUser;
	private Server serverContact;
	private ArrayList<Integer> associatedGroupIDs;
	
	public Client(User associatedUser, Server serverContact, ArrayList<Integer> associatedGroupIDs, String name) throws RemoteException
	{
		this.associatedUser = associatedUser;
		this.serverContact = serverContact;
		this.associatedGroupIDs = associatedGroupIDs;
		this.clientName = name;
	}

	protected Client() throws RemoteException
	{
		User user = new User();
		this.associatedUser = user;
		Server server = new Server();
		this.serverContact = server;
		ArrayList<Integer> myGroupIDs = new ArrayList<Integer>();
		this.associatedGroupIDs = myGroupIDs;
		this.clientName = "name";
	}

	private static final long serialVersionUID = -6394155878301235563L;
	
	String clientName = "Clyde Client";
	
	@Override
	public void notifyFinished()
	{
		System.out.println(clientName+" was called");
	}
	
	public void login(String username, String password)
	{
		serverContact.login(this, username, password);
	}
	
	public User getAssociatedUser()
	{
		return associatedUser;
	}

	public void setAssociatedUser(User associatedUser)
	{
		this.associatedUser = associatedUser;
	}

	public Server getServerContact()
	{
		return serverContact;
	}

	public void setServerContact(Server serverContact)
	{
		this.serverContact = serverContact;
	}

	public ArrayList<Integer> getAssociatedGroupIDs()
	{
		return associatedGroupIDs;
	}

	public void setAssociatedGroupIDs(ArrayList<Integer> associatedGroupIDs)
	{
		this.associatedGroupIDs = associatedGroupIDs;
	}

	public String getClientName()
	{
		return clientName;
	}

	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	public void updateNewUser()
	{
		System.out.println("New user was created.");
	}
	
	public void updateNewMessage()
	{
		System.out.println("New message was created.");
	}
	
	public void updateNewChannel()
	{
		System.out.println("New channel was created.");
	}
	
	public void updateNewInvite()
	{
		System.out.println("New invite was created.");
	}
	
	//Helper methods for corresponding Server methods
	//TODO
	
	public static void main(String args[])
	{
		//GUI Code goes here
	}

}
