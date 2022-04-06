package concord;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServerTest
{
	static Server server;
	static Database db;
	static HashMap<Integer, ArrayList<Client>> clientsInGroup;
	static ArrayList<RMIObserver> observers;
	static Client testClient;
	static User ol;
	@BeforeAll
	static void setUpBeforeClass() throws Exception
	{
		clientsInGroup = new HashMap<Integer, ArrayList<Client>>();
		Database db = new Database();
		server = new Server(db, clientsInGroup, observers);
		Client testClient = new Client();
		User ol = server.createUser("overlord", "oliver", "underwatch32");
		server.getDb().createGroup(50, "USA");
		server.getDb().getGroup(50).createChannel("patriotism", db.getGroup(50));
		
		
	}

	@BeforeEach
	void setUp() throws Exception
	{
		
	}
	@AfterEach
	void tearDown() throws Exception
	{
	}

	@Test
	void testAddObserver() throws RemoteException
	{
		int ob_size = server.observers.size();
		assertEquals(ob_size,server.observers.size());
		server.addObserver(testClient);
		assertEquals(ob_size+1,server.observers.size());
	}

	@Test
	void testRemoveObserver() throws RemoteException
	{
		int ob_size = server.observers.size();
		assertEquals(ob_size,server.observers.size());
		server.removeObserver(testClient);
		assertEquals(ob_size-1,server.observers.size());
	}

	@Test
	void testMakeDonuts() throws RemoteException
	{
		fail("TBD");
	}

	@Test
	void testLogin() throws RemoteException
	{
		server.login(testClient, ol.getUsername(), ol.getPassword());
		fail("not implemented");
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
		//create group with an ID
		db.createGroup(42, "galaxy");
		//show that the IDcan be used to get the group in server in same way it can in db
		assertEquals(db.getGroup(42),server.getGroup(42));
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
		db.createGroup(12, "backbackersAnonymous");
		db.createChannel("hiker's cove",ol.getUserID(), db.getGroup(12).getGroupID());
		assertEquals("hiker's cove",db.getGroup(12).getChannelByName("hiker's cove"));
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
		assertEquals("grain? has been received in reply to amber waves . . .",server.messageReceiveReply("patriotism", "grain?",ol.getUserID(),db.getGroup(50).getGroupID(),db.getGroup(50).getChannelByName("patriotism").getMessageLog().get(0)));
	}

	@Test
	void testViewChannelMessages() throws RemoteException
	{
		db.createGroup(12, "backbackersAnonymous");
		db.createChannel("channelName",ol.getUserID(), db.getGroup(12).getGroupID());
		assertEquals(db.viewChannel("channelName", ol.getUserID(), 12),server.viewChannelMessages("channelName", ol.getUserID(), 12));
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
		assertEquals(true,db.getGroup(50).getChannelByName("patriotism").getIsLocked());
	}

	@Test
	void testUnlockChannel() throws RemoteException
	{
		assertEquals("patriotism unlocked.",server.lockChannel(50, ol.getUserID(), "patriotism"));
		assertEquals(false,db.getGroup(50).getChannelByName("patriotism").getIsLocked());
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
	void testGetUserByName() throws RemoteException
	{
		User user = new User();
		user.setUsername("leetBoss");
		assertEquals("leetBoss",server.getUserByName(user.getUsername()));
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
		fail("Not yet implemented");
	}

	@Test
	void testGetUserCount() throws RemoteException
	{
		assertEquals(db.getGroup(50).registeredUsers.keySet().size(),server.getUserCount(50));
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
		assertEquals(db.getGroup(50).getChannelByName("patriotism").getMessageLog().get(0).getIsPinned(),server.pinMessage("patriotism", ol.getUserID(), 50, 0));
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
