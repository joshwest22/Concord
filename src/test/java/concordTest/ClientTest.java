package concordTest;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import concord.Channel;
import concord.Client;
import concord.Group;
import concord.Message;
import concord.RMIObserved;
import concord.Role;
import concord.Server;
import concord.User;

class ClientTest
{
	
	Server server;
	
	@BeforeEach
	void setUp() throws Exception
	{
		server = new Server();
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.rebind("SERVER", server);
	}

	@Test
	void test()
	{
		RMIObserved observed;
		try
		{
			Client client = new Client();
			observed = (RMIObserved) Naming.lookup("rmi://127.0.0.1/SERVER");
			observed.addObserver(client);
			client.setClientName("Tony");
			
			//test rmi working
			server.rmiConnected();
			assertEquals("Tony was called",server.rmiConnected());
			//test login
			client.login(client.getAssociatedUser().getUsername(), client.getAssociatedUser().getPassword());
			assertTrue(client.getAssociatedUser().getOnlineStatus());
			//assertEquals(0,server.getClientsInGroup().get(client)); //make sure login is thoroughly tested
			//test group, channel, and send message
			Group clientGroup = client.getServerContact().createGroup(27, "newClientGroup");
			clientGroup.getRegisteredUsers().put(client.getAssociatedUser(), clientGroup.admin);
			assertEquals("newClientGroup",client.getServerContact().getGroup(27).getGroupName());
			clientGroup.createChannel("newClientChannel", clientGroup);
			Channel clientChannel = client.getServerContact().getDb().getGroup(clientGroup.getGroupID()).getChannelByName("newClientChannel");
			assertEquals("newClientChannel",clientChannel.getChannelName());
			Message clientMessage = new Message("hello client", client.getAssociatedUser().getUserID());
			clientChannel.sendNewMessage(clientMessage);
			assertEquals("hello client",clientChannel.getMessageLog().get(0).getText());
			//test send message directly from client rather than via channel
			client.setCurrentSelectedGroupID(clientGroup.getGroupID());
			client.setCurrentSelectedChannelName(clientChannel.getChannelName());
			client.sendMessage("hello again");
			assertEquals("hello again", clientChannel.getMessageLog().get(1).getText());
			//test pinned message
			clientMessage.setIsPinned(true);
			assertTrue(clientMessage.getIsPinned());
			//test send invitation
			client.getServerContact().createUser("gump", "forest", "run", 76);
			User gump = client.getServerContact().getUserByUsername("gump"); //be careful with distinction between server and serverContact
			assertEquals(0,gump.getPendingInvites().size());
			client.sendInvitation(gump.getUserID(), clientGroup.getGroupID());
			//test receive invitation
			assertEquals(1,gump.getPendingInvites().size());
			//check that the invite msg was sent by client
			assertEquals(client.getAssociatedUser().getUserID(),gump.getPendingInvites().get(0).getInviteMsg().getSentBy());
			//test block user
			//add a user to be blocked to the server
			client.getServerContact().createUser("3v1l", "devil", "angerandPain12", 666); //this is not visible to the client; might need to refresh server contact
			User blockedUser = client.getServerContact().getAllRegisteredUsers().get(2); 
			client.getAssociatedUser().blockUser(blockedUser.getUserID());
			assertTrue(client.getAssociatedUser().getBlockedUserIDs().contains(blockedUser.getUserID()));
			//make sure blockedUser index is correct for getBlockedUserIDs
			assertEquals("devil",client.getServerContact().getDb().getUser(client.getAssociatedUser().getBlockedUserIDs().get(0)).getRealname());
			//test unblock user
			int blockedUsersSize = client.getAssociatedUser().getBlockedUserIDs().size();
			client.getAssociatedUser().unblockUser(blockedUser.getUserID());
			assertFalse(client.getAssociatedUser().getBlockedUserIDs().contains(blockedUser.getUserID()));
			assertEquals(blockedUsersSize-1,client.getAssociatedUser().getBlockedUserIDs().size());
			//test assign role/permissions
			//create a role where only one permission changed
			Role chanCreate = new Role("chanCreate",server.getGroup(clientGroup.getGroupID()),false,false,false,true);
			clientGroup.getRegisteredUsers().get(client.getAssociatedUser()).assignRole(blockedUser, chanCreate);
			assertEquals(true,clientGroup.getRegisteredUsers().get(blockedUser).getCanCreateChannel());
			
		} catch (MalformedURLException | RemoteException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("I failed");
		}
		
		
	}

}
