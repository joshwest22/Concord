package concord;

import java.io.Serializable;
import java.net.MalformedURLException;
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

	public Client() throws RemoteException
	{
		User user = new User();
		this.associatedUser = user;
		Server server = new Server();
		this.serverContact = server;
		ArrayList<Integer> myGroupIDs = new ArrayList<Integer>();
		this.associatedGroupIDs = myGroupIDs;
		this.clientName = "Name: 'client'";
	}

	private static final long serialVersionUID = -6394155878301235563L;
	
	String clientName = "Name: 'client'";
	
	@Override
	public String notifyFinished()
	{
		System.out.println(clientName+" was called");
		return clientName+" was called";
	}
	
	public void login(String username, String password)
	{
		serverContact.login(this, username, password);
		//set the online status to true?
		associatedUser.setOnlineStatus(true);
		try
		{
			serverContact.createUser(this.getAssociatedUser().getUsername(), this.getAssociatedUser().getRealname(), this.getAssociatedUser().getPassword(), this.getAssociatedUser().getUserID());
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		//this.serverContact.updateNewUser(groupID); //check if new user in one group or all groups?
	}
	
	public void updateNewMessage()
	{
		//serverContact.updateNewMessage(currentGroupID) //how to get current selected groupID
		System.out.println("New message was created.");
	}
	
	public void updateNewChannel()
	{
		//serverContact.updateNewChannel(currentGroupID) //how to get current selected groupID; when button selected
		System.out.println("New channel was created.");
	}
	
	public void updateNewInvite()
	{
		this.serverContact.updateInvite(associatedUser.getUserID());
	}
	
	//Helper methods for corresponding Server methods
	//TODO
	
	public static void main(String args[])
	{
		//GUI Code goes here
	}

}
