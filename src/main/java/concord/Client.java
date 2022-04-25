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
	
	public Client(String username, String password) throws RemoteException
	{
		User user = new User(username,password);
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
	
	public boolean login(String enteredName, String enteredPassword)
	{
		String clientUsername = this.getAssociatedUser().getUsername();
		String clientPassword = this.getAssociatedUser().getPassword();
		//if login already exists
		if (enteredName.equals(clientUsername) && enteredPassword.equals(clientPassword))
		{
			//set the online status to true
			associatedUser.setOnlineStatus(true);
			//first time login, but don't create a new user for a wrong password
			serverContact.login(this, clientUsername, clientPassword);
			
			try
			{
				serverContact.createUser(this.getAssociatedUser().getUsername(), this.getAssociatedUser().getRealname(), this.getAssociatedUser().getPassword(), this.getAssociatedUser().getUserID());
				//should I call updateNewUser to alert the server to a change?
				
			} catch (MalformedURLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		else
		{
			//wrong password
			return false;
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
	
	public void updateNewInvitation()
	{
		this.serverContact.updateInvite(associatedUser.getUserID());
	}
	
	//Helper methods for corresponding Server methods
	//TODO

	public void addGroupID(int i)
	{
		this.getAssociatedGroupIDs().add(i);		
	}

}
