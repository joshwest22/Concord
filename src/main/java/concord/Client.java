package concord;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javafx.collections.ObservableList;

public class Client extends UnicastRemoteObject implements RMIObserver, Serializable
{	
	private User associatedUser;
	private Server serverContact;
	private ArrayList<Integer> associatedGroupIDs;
	
	private int currentSelectedGroupID;
	private User currentSelectedUser;
	
	private ObservableList<Group> selectedGroup;
	private ObservableList<Channel> selectedChannel;
	
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
	
	@Override
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
	
	public void updateNewMessage() //client give the server the new message
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
	public void sendMessage()
	{
		
	}
	
	@Override
	public void sendInvitation(Integer invitedUserID, Integer groupID)
	{
		Message inviteMsg = new Message("Want to join my group?", associatedUser.getUserID());
		Invitation invite = new Invitation(inviteMsg,groupID,invitedUserID,false);
		
		try
		{
			serverContact.sendInvitation(invitedUserID, invite);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void addGroupID(int i)
	{
		this.getAssociatedGroupIDs().add(i);		
	}

	public int getCurrentSelectedGroupID()
	{
		return currentSelectedGroupID;
	}

	public void setCurrentSelectedGroupID(int currentSelectedGroupID)
	{
		this.currentSelectedGroupID = currentSelectedGroupID;
	}

	public User getCurrentSelectedUser()
	{
		return currentSelectedUser;
	}

	public void setCurrentSelectedUser(User currentSelectedUser)
	{
		this.currentSelectedUser = currentSelectedUser;
	}

	public ObservableList<Group> getSelectedGroup()
	{
		return selectedGroup;
	}

	public void setSelectedGroup(ObservableList<Group> selectedGroup)
	{
		this.selectedGroup = selectedGroup;
	}

	public ObservableList<Channel> getSelectedChannel()
	{
		return selectedChannel;
	}

	public void setSelectedChannel(ObservableList<Channel> selectedChannel)
	{
		this.selectedChannel = selectedChannel;
	}

	@Override
	public String createChannel(String channelName, Integer userID, Integer groupID) throws RemoteException
	{
		serverContact.createChannel(channelName, userID, groupID);
		return channelName+"was created in group "+groupID+" on client";
	}

	@Override
	public void updateNewUser(Integer groupID) throws RemoteException
	{
		this.serverContact.updateNewUser(groupID);
		
	}

	@Override
	public void updateNewMessage(Integer groupID) throws RemoteException
	{
		this.serverContact.updateNewMessage(groupID);
		
	}

}
