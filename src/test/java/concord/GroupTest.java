package concord;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GroupTest
{
	Group group1;
	Group group2;
	Group group3;
	User josh;
	User gus;
	User overlord;
	Role adminRole;
	Role basicRole;
	URL url;
	@BeforeEach
	void setUp() throws Exception
	{
		//test that all parameters of group were assigned correctly using short Group constructor
		group1 = new Group(1,"group1");
		
		
		ArrayList<Channel> channels = new ArrayList<Channel>();
		Channel channel1 = new Channel("channel1",group1);
		channels.add(channel1);
		HashMap<User,Role> registeredUsers = new HashMap<User,Role>();
		josh = new User("joshanator", "josh", "pass", 00, url, "bio", true);
		gus = new User("crow", "gus", "5678", 02, url, "gustav", true);
		overlord = new User("overlord","bill","xxxyyyzzz",123,url,"bio",true);
		Role adminRole = new Role("admin", group1, true, true, true, true);
		registeredUsers.put(josh, adminRole);
		registeredUsers.put(overlord, adminRole);
		group1.registeredUsers.put(overlord, adminRole);
		//test that all parameters of group were assigned correctly using long constructor
		group2 = new Group(channels, registeredUsers, "cool group", URL("http://logo.com"), "group2", 2);
		//create another group for testing purposes
		group3 = new Group(3,"group3");
		group3.registeredUsers.put(overlord, adminRole);
		
}

	private URL URL(String string) throws MalformedURLException
	{
		URL url = new URL(string);
		return url;
	}

	@Test
	void testGroupArrayListOfChannelHashMapOfUserRoleStringURLStringInteger() throws MalformedURLException
	{		
		//assertEquals(group2.getChannels(),channel1); //out of scope
		assertEquals(group2.getChannels().get(0).getChannelName(),"channel1");
		
		//assertEquals(group2.getRegisteredUsers(),registeredUsers); //out of scope
		assertEquals(group2.getRegisteredUsers().get(josh).getRoleName(),"admin");
		
		assertEquals(group2.getDescription(),"cool group");
		
		assertEquals(group2.getLogo().getHost(),"logo.com");
		
		assertEquals(group2.getGroupName(), "group2");
		
		assertEquals(group2.getGroupID(),2);
	}

	
	@Test
	void testGroupIntegerString() throws MalformedURLException
	{
		ArrayList<Channel> channels = new ArrayList<Channel>();
		HashMap<User,Role> regUsers = new HashMap<User,Role>();
		regUsers.put(overlord, adminRole);
		group1.logo = new URL("http://logo-url.com");
		assertEquals(group1.getGroupID(),1);
		assertEquals(group1.getGroupName(),"group1");
		assertEquals(group1.getDescription(),"default description; please set me");
		assertEquals(group1.getChannels(),channels);
		assertEquals(group1.getRegisteredUsers().size(),regUsers.size());
		assertEquals(group1.getLogo().getHost(),"logo-url.com");
		assertEquals(group1.getUserCount(),regUsers.size()); 
	}

	@Test
	void testAddNewUser()
	{
		HashMap<User,Role> testRegUsers = new HashMap<User,Role>();
		testRegUsers.put(overlord, adminRole);
		group1.addNewUser(overlord, josh, adminRole); //size = 1
		testRegUsers.put(josh,adminRole);
		//assertEquals(group1.getRegisteredUsers().get(josh).getRoleName(),testRegUsers.get(josh).getRoleName());
		assertEquals(group1.getRegisteredUsers().size(),testRegUsers.size());
		group1.addNewUser(overlord,gus, adminRole); // size = 2
		testRegUsers.put(gus, adminRole);
		assertEquals(group1.getRegisteredUsers().size(),testRegUsers.size());		
	}

	@Test
	void testRemoveUser() //this is also how a user leaves
	{
		group2.addNewUser(overlord,josh, adminRole);
		HashMap<User,Role> testRegUsers = new HashMap<User,Role>();
		testRegUsers.put(josh,adminRole);
		group2.addNewUser(overlord,gus, adminRole);
		testRegUsers.put(gus, adminRole);
		testRegUsers.remove(josh);
		assertEquals(group2.getRegisteredUsers().get(josh),testRegUsers.get(josh)); // might need to compare users
	}

	@Test
	void testGetUserCount()
	{
		HashMap<User,Role> testRegUsers = new HashMap<User,Role>();
		testRegUsers.put(overlord, adminRole);
		group3.addNewUser(overlord,gus,adminRole);
		testRegUsers.put(gus,adminRole);
		assertEquals(group3.getUserCount(),testRegUsers.size());
		group3.addNewUser(overlord,josh,adminRole);
		testRegUsers.put(josh,adminRole);
		assertEquals(group3.getUserCount(),testRegUsers.size());
		group3.addNewUser(overlord,gus,adminRole);
		testRegUsers.put(josh,adminRole);
		assertEquals(group3.getUserCount(),testRegUsers.size()); //count should not change; user already exists
	}

	@Test
	void testInviteUser()
	{
		assertEquals(group2.getRegisteredUsers().containsKey(gus),false);
		group2.inviteUser(overlord, gus, basicRole);
		assertEquals(group2.getRegisteredUsers().containsKey(gus),true);
	}

	@Test
	void testCreateChannel()
	{
		group1.addNewUser(overlord,josh,adminRole);
		ArrayList<Integer> allowedUserIDs = new ArrayList<Integer>();
		//allowedUserIDs.add(josh.getUserID());
		ArrayList<Message> log = new ArrayList<Message>();
		
		group1.createChannel("testChannel",group1);
		
		  assertEquals(group1.getChannels().get(0).getChannelName(),"testChannel");
		  assertEquals(group1.getChannels().get(0).getMyGroup(),group1);
		  assertEquals(group1.getChannels().get(0).getIsLocked(),false);
		  assertEquals(group1.getChannels().get(0).getAllowedUsers(),allowedUserIDs);
		  assertEquals(group1.getChannels().get(0).getMessageLog(),log);
		 
		//creating a second channel
		ArrayList<Message> anotherLog = new ArrayList<Message>();
		group1.createChannel("testAnotherChannel",group1);
		
		  assertEquals(group1.getChannels().get(1).getChannelName(),"testAnotherChannel");
		  assertEquals(group1.getChannels().get(1).getMyGroup(),group1);
		  assertEquals(group1.getChannels().get(1).getIsLocked(),false);
		  assertEquals(group1.getChannels().get(1).getAllowedUsers(),allowedUserIDs);
		  assertEquals(group1.getChannels().get(1).getMessageLog(),anotherLog);
		 
		//make sure both channels are in the group
		assertEquals(group1.getChannels().get(0).getChannelName(),"testChannel");
		assertEquals(group1.getChannels().get(1).getChannelName(),"testAnotherChannel");
		
		//test that a user can send a message in both channels
		group2.createChannel("anotherChannel",group2);
		Message msg0 = new Message("Message0",josh.getUserID());
		Message msg1 = new Message("Message1",josh.getUserID());
		group2.getRegisteredUsers().get(josh).sendMessage(msg0, group2.getChannels().get(0));
		group2.getRegisteredUsers().get(josh).sendMessage(msg1, group2.getChannels().get(1));
		//check if the message is in the channel and message text matches text sent
		assertEquals(group2.channels.get(0).getMessageLog().get(0).getText(),msg0.getText());
		assertEquals(group2.channels.get(1).getMessageLog().get(0).getText(),msg1.getText());
	}
	
	@Test
	void testViewAllMembers()
	{
		//Show that this is an alias method to getRegisteredUsers that allows viewing of all users
		assertEquals(group2.viewAllMembers(),group2.getRegisteredUsers()); 
	}

}
