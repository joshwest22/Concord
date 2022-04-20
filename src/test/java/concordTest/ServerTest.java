package concordTest;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import concord.Client;
import concord.Database;
import concord.Group;
import concord.Invitation;
import concord.Message;
import concord.RMIObserver;
import concord.Server;
import concord.User;

class ServerTest
{
	static Server server;
	static HashMap<Integer, ArrayList<Client>> clientsInGroup;
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
		Group USAGroup = server.getGroup(50);
		server.getDb().getGroup(50).createChannel("patriotism", server.getDb().getGroup(50));
		USAGroup.getRegisteredUsers().put(ol, USAGroup.admin);
		
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
		assertEquals("Name: 'client' was called",server.rmiConnected());
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
		assertEquals("Robin H.",server.getUserByUsername("anonPhilanthropist").getRealname());
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
	void testGetUserGroups() throws RemoteException, MalformedURLException
	{
		//add user to 2 groups
		server.createGroup(01, "A");
		Group groupA = server.getGroup(01);
		server.createGroup(02, "B");
		Group groupB = server.getGroup(02);
		//add overlord to groups
		groupA.getRegisteredUsers().put(ol, groupA.admin);
		groupB.getRegisteredUsers().put(ol, groupB.admin);
		User u = server.createUser("usernomen", "Igor", "trollBridge659%", 111);
		server.addUserToGroup(groupA.getGroupID(), ol.getUserID(), u.getUserID());
		server.addUserToGroup(groupB.getGroupID(), ol.getUserID(), u.getUserID());
		//make a dummy list of those 2 groups
		ArrayList<Group> dummyGroups = new ArrayList<Group>();
		dummyGroups.add(groupA);
		dummyGroups.add(groupB);
		//check that group names in dummy list match group names in server list
		assertEquals(dummyGroups.get(0).getGroupName(),server.getUserGroups(u.getUserID()).get(0).getGroupName());
		assertEquals(dummyGroups.get(1).getGroupName(),server.getUserGroups(u.getUserID()).get(1).getGroupName());
	}

	@Test
	void testCreateChannel() throws RemoteException
	{
		server.createGroup(12, "backbackersAnonymous");
		server.createChannel("hiker's cove",ol.getUserID(), server.getGroup(12).getGroupID());
		assertEquals("hiker's cove",server.getGroup(12).getChannelByName("hiker's cove").getChannelName());
	}

	@Test
	void testUpdateNewChannel() throws RemoteException
	{
		assertEquals("New channel created",server.updateNewChannel(50));
	}

	@Test
	void testUpdateNewMessage() throws RemoteException
	{
		assertEquals("New message created",server.updateNewMessage(50));
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
		Message m = server.getGroup(50).getChannelByName("patriotism").getMessageLog().get(0);
		String s = "grain?";
		Message m2 = new Message(s, ol.getUserID());
		m2.setInReplyTo(m);
		assertEquals(s+" has been receieved in reply to : "+m.getText(),server.messageReceiveReply("patriotism", s,ol.getUserID(),server.getGroup(50).getGroupID(),server.getGroup(50).getChannelByName("patriotism").getMessageLog().get(0))); //see if I am checking the correct message
	}

	@Test
	void testViewChannelMessages() throws RemoteException
	{
		server.createGroup(12, "backbackersAnonymous");
		server.createChannel("channelName",ol.getUserID(), server.getDb().getGroup(12).getGroupID());
		assertEquals(server.getDb().viewChannel("channelName", ol.getUserID(), 12),server.viewChannelMessages("channelName", ol.getUserID(), 12));
	}

	@Test
	void testUpdateNewUser() throws RemoteException
	{
		assertEquals("New user was created",server.updateNewUser(50));
	}

	@Test
	void testAddUserToGroup() throws RemoteException, MalformedURLException
	{
		//create a new user
		User sam = server.createUser("sarah walker", "sam", "buymore");
		//add user to group
		String result = server.addUserToGroup(50, ol.getUserID(), sam.getUserID());
		//check that user is in group's registeredUsers
		assertEquals(server.getUserByUsername(sam.getUsername()).getUsername()+" was added to "+server.getGroup(50),result);
		assertEquals("basic",server.getGroup(50).getRegisteredUsers().get(sam).getRoleName());
		assertTrue(server.getGroup(50).getRegisteredUsers().containsKey(sam));
	}

	@Test
	void testRemoveUserFromGroup() throws RemoteException, MalformedURLException
	{
		//create user
		server.createUser("retrop", "porter", "beeboop3234", 1570);
		User porter = server.getUserByUsername("retrop");
		//add a user to a group
		server.getGroup(50).addNewUser(ol, porter, server.getGroup(50).admin);
		//take the user out of the group and show that the size is one less
		int before_size = server.getGroup(50).getRegisteredUsers().size();
		assertEquals(before_size, server.getGroup(50).getRegisteredUsers().size());
		int after_size = before_size - 1;
		assertEquals("retrop was removed from USA by overlord",server.removeUserFromGroup(50, ol.getUserID(), porter.getUserID()));
		assertEquals(after_size,server.getGroup(50).getRegisteredUsers().size());
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
	void testLeaveGroup() throws RemoteException, MalformedURLException
	{
		//create new user
		server.createUser("greenthumb", "tom thumb", "forefinger12");
		User tom = server.getUserByUsername("greenthumb");
		//add user to group
		server.addUserToGroup(50, ol.getUserID(), tom.getUserID());
		//let user leave
		String result = server.leaveGroup(50, tom.getUserID());
		//check that leave group message is correct
		assertEquals(server.getUserByUsername(tom.getUsername()).getUsername()+"left "+server.getGroup(50).getGroupName(),result);
	}

	@Test
	void testViewUser() throws RemoteException, MalformedURLException
	{
		//viewUser is akin to getUser in db
		User bob = server.createUser("builder", "robert", "bobbuilds124");
		assertEquals("builder",server.viewUser(bob.getUserID()).getUsername());
		assertEquals("robert",server.viewUser(bob.getUserID()).getRealname());
		assertEquals("bobbuilds124",server.viewUser(bob.getUserID()).getPassword());
	}

	@Test
	void testGetUserByName() throws RemoteException, MalformedURLException
	{
		User user = server.createUser("leetBoss", "ethan leech", "leetithdeletith");
		assertEquals("leetBoss",user.getUsername());
	}

	@Test
	void testGetUserIDByName() throws RemoteException, MalformedURLException
	{
		User user = server.createUser("leetBoss","evan leech","leetTeam4Life",1337);
		assertEquals(user.getUserID(),server.getUserIDByName(user.getUsername()));
	}


	@Test
	void testGetUserCount() throws RemoteException
	{
		assertEquals(server.getDb().getGroup(50).getRegisteredUsers().keySet().size(),server.getUserCount(50));
	}

	@Test
	void testAddAllowedUser() throws RemoteException, MalformedURLException
	{
		//create User via server.create user
		server.createUser("taika", "watiti", "rflagmeansdeath");
		User pirate = server.getUserByUsername("taika"); 
		Group group = server.getGroup(50);
		group.addNewUser(ol, pirate, group.admin); 
		// add user to allowed user list (channel)
		String return_msg = server.addAllowedUser(group.getChannels().get(0).getChannelName(), ol, pirate.getUserID(), 50);
		assertEquals(return_msg, ol.getUsername()+" added "+pirate.getUsername()+" to "+group.getGroupName()+"'s channel "+group.getChannels().get(0).getChannelName());
		//check if userID in allowedList
		assertEquals(pirate.getUserID(),group.getChannels().get(0).getAllowedUsers().get(1));
	}
	

	@Test
	void testBlockUser() throws RemoteException, MalformedURLException
	{
		server.createUser("devil", "lucius pher", "666");
		server.blockUser(ol.getUserID(), 666);
		assertEquals(666,ol.getBlockedUserIDs().get(0));
	}
	
	@Test
	void testUnblockUser() throws RemoteException, MalformedURLException
	{
		server.createUser("devil", "lucius pher", "666");
		server.unblockUser(ol.getUserID(), 666);
		assertEquals(0,ol.getBlockedUserIDs().size());
	}

	@Test
	void testPinMessage() throws RemoteException
	{
		//check that there are no pinned messages
		Message m = new Message("god bless america",ol.getUserID());
		server.getGroup(50).getChannelByName("patriotism").sendNewMessage(m);
		assertEquals(false,server.getGroup(50).getChannelByName("patriotism").getMessageLog().get(0).getIsPinned());
		//pin message in a channel
		server.pinMessage("patriotism", ol.getUserID(), 50, 0);
		//check that the message's pinned boolean is true
		assertEquals(true,server.getDb().getGroup(50).getChannelByName("patriotism").getMessageLog().get(0).getIsPinned());
	}

	@Test
	void testAssignNewRole() throws RemoteException, MalformedURLException
	{
		//create new user and assign them default role by default
		server.createUser("bagelman", "einstein", "emc2");
		User einstein = server.getUserByUsername("bagelman");
		//add new user to group
		server.addUserToGroup(50, ol.getUserID(), einstein.getUserID());
		//allow them permission to create channel
		assertEquals(false,server.getGroup(50).getRegisteredUsers().get(einstein).getCanCreateChannel());
		server.assignNewRole(ol.getUserID(), einstein.getUserID(), 50, false, false, false, true);
		assertEquals(true,server.getGroup(50).getRegisteredUsers().get(einstein).getCanCreateChannel());
	}

	@Test
	void testUpdateInvite() throws RemoteException, MalformedURLException
	{
		server.createUser("who?", "james", "widowmain");
		User james = server.getUserByUsername("who?");
		Message m = new Message("want to join?", ol.getUserID());
		Invitation inv = new Invitation();
		inv.setInviteMsg(m);
		james.getPendingInvites().add(inv);
		assertEquals("New invite was created",server.updateInvite(james.getUserID()));
	}

	@Test
	void testGetAllRegisteredUsers() throws RemoteException
	{
		assertEquals(server.getDb().getListOfUsers(),server.getAllRegisteredUsers());
	}

}
