package concord;

import static org.junit.jupiter.api.Assertions.*;

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
	@BeforeAll
	static void setUpBeforeClass() throws Exception
	{
		clientsInGroup = new HashMap<Integer, ArrayList<Client>>();
		server = new Server(db, clientsInGroup, observers);
		
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
	void testAddObserver()
	{
		fail("Not yet implemented");
	}

	@Test
	void testRemoveObserver()
	{
		fail("Not yet implemented");
	}

	@Test
	void testMakeDonuts()
	{
		fail("Not yet implemented");
	}

	@Test
	void testLogin()
	{
		fail("Not yet implemented");
	}

	@Test
	void testCreateUser()
	{
		fail("Not yet implemented");
	}

	@Test
	void testGetGroup()
	{
		//create group with an ID
		//show that the IDcan be used to get the group
		//assertEquals(server.getGroup(gID));
	}

	@Test
	void testGetUserGroups()
	{
		fail("Not yet implemented");
	}

	@Test
	void testCreateChannel()
	{
		fail("Not yet implemented");
	}

	@Test
	void testUpdateNewChannel()
	{
		fail("Not yet implemented");
	}

	@Test
	void testUpdateNewMessage()
	{
		fail("Not yet implemented");
	}

	@Test
	void testMessageReceived()
	{
		fail("Not yet implemented");
	}

	@Test
	void testMessageReceiveReply()
	{
		fail("Not yet implemented");
	}

	@Test
	void testViewChannelMessages()
	{
		fail("Not yet implemented");
	}

	@Test
	void testUpdateNewUser()
	{
		fail("Not yet implemented");
	}

	@Test
	void testAddUserToGroup()
	{
		fail("Not yet implemented");
	}

	@Test
	void testRemoveUserFromGroup()
	{
		fail("Not yet implemented");
	}

	@Test
	void testLockChannel()
	{
		fail("Not yet implemented");
	}

	@Test
	void testUnlockChannel()
	{
		fail("Not yet implemented");
	}

	@Test
	void testLeaveGroup()
	{
		fail("Not yet implemented");
	}

	@Test
	void testViewUser()
	{
		fail("Not yet implemented");
	}

	@Test
	void testGetUserByName()
	{
		fail("Not yet implemented");
	}

	@Test
	void testGetUserIDByName()
	{
		fail("Not yet implemented");
	}

	@Test
	void testGetAllUsers()
	{
		fail("Not yet implemented");
	}

	@Test
	void testGetUserCount()
	{
		fail("Not yet implemented");
	}

	@Test
	void testAddAllowedUser()
	{
		fail("Not yet implemented");
	}

	@Test
	void testBlockUser()
	{
		fail("Not yet implemented");
	}

	@Test
	void testPinMessage()
	{
		fail("Not yet implemented");
	}

	@Test
	void testAssignNewRole()
	{
		fail("Not yet implemented");
	}

	@Test
	void testUpdateInvite()
	{
		fail("Not yet implemented");
	}

	@Test
	void testGetAllRegisteredUsers()
	{
		fail("Not yet implemented");
	}

}
