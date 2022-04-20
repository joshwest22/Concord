package concordTest;

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
			Group clientGroup = server.createGroup(27, "newClientGroup");
			assertEquals("newClientGroup",server.getGroup(27).getGroupName());
			clientGroup.createChannel("newClientChannel", clientGroup);
			Channel clientChannel = server.getDb().getGroup(clientGroup.getGroupID()).getChannelByName("newClientChannel");
			assertEquals("newClientChannel",clientChannel.getChannelName());
			Message clientMessage = new Message("hello client", client.getAssociatedUser().getUserID());
			clientChannel.sendNewMessage(clientMessage);
			assertEquals("hello client",clientChannel.getMessageLog().get(0).getText());
			//test pinned message
			clientMessage.setIsPinned(true);
			assertTrue(clientMessage.getIsPinned());
			//test send invitation
			
			//test receive invitation
			
			//test block user
			//add a user to be blocked to the server
			server.createUser("3v1l", "devil", "angerandPain12", 666); //this is not visible to the client; might need to refresh server contact
			User blockedUser = client.getServerContact().getAllRegisteredUsers().get(0); //0 index is clientAssocUser, but 1 is out of range
			client.getAssociatedUser().blockUser(blockedUser.getUserID());
			assertTrue(client.getAssociatedUser().getBlockedUserIDs().contains(blockedUser.getUserID()));
			assertEquals("devil",client.getServerContact().getDb().getUser(client.getAssociatedUser().getBlockedUserIDs().get(0)).getRealname());
			//test unblock user
			
			//test assign role/permissions
			
			
		} catch (MalformedURLException | RemoteException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("I failed");
		}
		
		
	}

}
