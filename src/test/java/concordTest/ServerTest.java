package concordTest;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import concord.Client;
import concord.Database;
import concord.Group;
import concord.RMIObserver;
import concord.Server;
import concord.User;

class ServerTest
{
	static Server server;
	//static Database database;
	//static Database testDB;
	static HashMap<Integer, ArrayList<Client>> clientsInGroup;
	//static ArrayList<RMIObserver> observers;
	static Client testClient;
	static URL url;
	static User ol;
	
	@BeforeAll
	static void setUp() throws Exception
	{
		clientsInGroup = new HashMap<Integer, ArrayList<Client>>();
		ArrayList<Client> clients = new ArrayList<Client>();
		testClient = new Client();
		clients.add(testClient);
		clientsInGroup.put(50,clients);		
		server = new Server(new Database(), clientsInGroup, new ArrayList<RMIObserver>());
		//Database debug = database; //gives variable that can be seen in debug
		url = new URL("http://image.com");
		server.getDb().createUser("overlord", "oliver", "underwatch32",00, url, "OP OL", false); //this is being seen as nul
		ol = server.getDb().getUser(00);		
		assertEquals("overlord",ol.getUsername());
		
		
		
		server.getDb().createGroup(50, "USA");
		server.getDb().getGroup(50).createChannel("patriotism", server.getDb().getGroup(50));
		
	}
	@AfterEach
	void tearDown() throws Exception
	{
	}

	@Test
	void testAddObserver() throws RemoteException
	{
		int ob_size = server.getObservers().size();
		assertEquals(ob_size,server.getObservers().size());
		server.addObserver(testClient);
		assertEquals(ob_size+1,server.getObservers().size());
	}

	@Test
	void testRemoveObserver() throws RemoteException
	{
		int ob_size = server.getObservers().size();
		assertEquals(ob_size,server.getObservers().size());
		server.removeObserver(testClient);
		assertEquals(ob_size-1,server.getObservers().size());
	}

	@Test
	void testMakeDonuts() throws RemoteException
	{
		fail("TBD");
	}

	@Test
	void testLogin() throws RemoteException
	{
		//client does not have an associated user
		assertEquals("username",testClient.getAssociatedUser().getUsername());
		server.login(testClient, ol.getUsername(), ol.getPassword());
		//client has an associated user
		assertEquals(ol.getUsername(),testClient.getAssociatedUser().getUsername());
		
	}

	@Test
	void testCreateUser() throws MalformedURLException, RemoteException
	{
		server.createUser("anonPhilanthropist", "Robin H.", "merrymen123");
		assertEquals("Robin H.",server.getUserByName("anonPhilanthropist"));
	}

	@Test
	void testGetGroup() throws RemoteException
	{
		Database testDB = new Database();
		//create group with an ID
		server.getDb().createGroup(42, "galaxy");
		testDB.createGroup(42, "galaxy");
		//show that the ID can be used to get the group in server in same way it can in db
		assertEquals(testDB.getGroup(42).getGroupName(),server.getGroup(42).getGroupName());
	}
	
	@Test
	void testCreateGroup() throws RemoteException
	{
		server.createGroup(422, "coolGroup");
		assertEquals("coolGroup",server.getGroup(422).getGroupName());
	}

	@Test
	void testGetUserGroups() throws RemoteException
	{
		//add user to 2 groups
		Group groupA = new Group();
		groupA.setGroupName("A");
		Group groupB = new Group();
		groupB.setGroupName("B");
		User u = new User();
		u.setUserID(111);
		groupA.addNewUser(ol, u, groupA.basic);
		groupB.addNewUser(ol, u, groupB.basic);
		//make a dummy list of those 2 groups
		ArrayList<Group> dummyGroups = new ArrayList<Group>();
		dummyGroups.add(groupA);
		dummyGroups.add(groupB);
		//assertEquals dummy list of groups with result
		assertEquals(dummyGroups,server.getUserGroups(u.getUserID()));
	}

	@Test
	void testCreateChannel() throws RemoteException
	{
		server.getDb().createGroup(12, "backbackersAnonymous");
		server.getDb().createChannel("hiker's cove",ol.getUserID(), server.getDb().getGroup(12).getGroupID());
		assertEquals("hiker's cove",server.getDb().getGroup(12).getChannelByName("hiker's cove"));
	}

	@Test
	void testUpdateNewChannel() throws RemoteException
	{
		fail("Not yet implemented");
	}

	@Test
	void testUpdateNewMessage() throws RemoteException
	{
		fail("Not yet implemented");
	}

	@Test
	void testMessageReceived() throws RemoteException
	{
		//assertEquals(server.getDb().messageReceived("patriotism", "amber waves . . .", ol.getUserID(), db.getGroup(50).getGroupID()),server.messageReceived("patriotism", "amber waves . . .", ol.getUserID(), db.getGroup(50).getGroupID()));
		assertEquals(server.messageReceived("patriotism", "amber waves . . .",ol.getUserID(),50),"amber waves . . . has been received.");
	}

	@Test
	void testMessageReceiveReply() throws RemoteException
	{
		assertEquals("grain? has been received in reply to amber waves . . .",server.messageReceiveReply("patriotism", "grain?",ol.getUserID(),server.getDb().getGroup(50).getGroupID(),server.getDb().getGroup(50).getChannelByName("patriotism").getMessageLog().get(0)));
	}

	@Test
	void testViewChannelMessages() throws RemoteException
	{
		server.getDb().createGroup(12, "backbackersAnonymous");
		server.getDb().createChannel("channelName",ol.getUserID(), server.getDb().getGroup(12).getGroupID());
		assertEquals(server.getDb().viewChannel("channelName", ol.getUserID(), 12),server.viewChannelMessages("channelName", ol.getUserID(), 12));
	}

	@Test
	void testUpdateNewUser() throws RemoteException
	{
		fail("Not yet implemented");
	}

	@Test
	void testAddUserToGroup() throws RemoteException
	{
		fail("Not yet implemented");
	}

	@Test
	void testRemoveUserFromGroup() throws RemoteException
	{
		fail("Not yet implemented");
	}

	@Test
	void testLockChannel() throws RemoteException
	{
		assertEquals("patriotism locked.",server.lockChannel(50, ol.getUserID(), "patriotism"));
		assertEquals(true,server.getDb().getGroup(50).getChannelByName("patriotism").getIsLocked());
	}

	@Test
	void testUnlockChannel() throws RemoteException
	{
		assertEquals("patriotism unlocked.",server.unlockChannel(50, ol.getUserID(), "patriotism"));
		assertEquals(false,server.getDb().getGroup(50).getChannelByName("patriotism").getIsLocked());
	}

	@Test
	void testLeaveGroup() throws RemoteException
	{
		fail("Not yet implemented");
	}

	@Test
	void testViewUser() throws RemoteException
	{
		fail("Not yet implemented");
	}

	@Test
	void testGetUserByName() throws RemoteException, MalformedURLException
	{
		User user = server.createUser("leetBoss", "ethan leech", "leetithdeletith");
		assertEquals("leetBoss",user.getUsername());
	}

	@Test
	void testGetUserIDByName() throws RemoteException
	{
		User user = new User();
		user.setUsername("leetBoss");
		user.setUserID(1337);
		assertEquals(1337,server.getUserIDByName(user.getUsername()));
	}

	@Test
	void testGetAllUsers() throws RemoteException
	{
		//check that size of regUsers is the same as size of listOfUsers
		assertEquals(server.getDb().getGroup(12).getRegisteredUsers().size(),server.getAllUsers(12));
	}

	@Test
	void testGetUserCount() throws RemoteException
	{
		assertEquals(server.getDb().getGroup(50).getRegisteredUsers().keySet().size(),server.getUserCount(50));
	}

	@Test
	void testAddAllowedUser() throws RemoteException
	{
		fail("Not yet implemented");
	}

	@Test
	void testBlockUser() throws RemoteException
	{
		fail("Not yet implemented");
	}

	@Test
	void testPinMessage() throws RemoteException
	{
		assertEquals(server.getDb().getGroup(50).getChannelByName("patriotism").getMessageLog().get(0).getIsPinned(),server.pinMessage("patriotism", ol.getUserID(), 50, 0));
	}

	@Test
	void testAssignNewRole() throws RemoteException
	{
		fail("Not yet implemented");
	}

	@Test
	void testUpdateInvite() throws RemoteException
	{
		fail("Not yet implemented");
	}

	@Test
	void testGetAllRegisteredUsers() throws RemoteException
	{
		fail("Not yet implemented");
	}

}
