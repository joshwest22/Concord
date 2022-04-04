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
		
	}

	private static final long serialVersionUID = -6394155878301235563L;
	
	String clientName = "Clyde Client";
	
	@Override
	public void notifyFinished()
	{
		System.out.println(clientName+" was called");
	}
	
	public User login(String username, String password)
	{
		User myUser = serverContact.login(this, username, password);
		//for all existing users
//		for(User user:serverContact.getAllRegisteredUsers())
//		{
//			//if the username exists
//			if(user.getUsername().contains(username)) //users.getUsername().contains(username)
//			{
//				//AND the password matches (secure version would be if the hash of password matches)
//				if(serverContact.getUserByName(username).getPassword().equals(password))
//				{
//					//set the user to be returned and update associated user
//					associatedUser = user;
//				}
//			}
//		}
//		return associatedUser;
		return myUser;
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
		//TODO
	}
	
	public void updateNewMessage()
	{
		//TODO
	}
	
	public void updateNewChannel()
	{
		//TODO
	}
	
	public void updateNewInvite()
	{
		//TODO
	}
	
	//Helper methods for corresponding Server methods
	//TODO
	
	public static void main(String args[])
	{
		
	}

}
