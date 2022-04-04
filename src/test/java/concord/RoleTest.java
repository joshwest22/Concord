package concord;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RoleTest
{
	static Role basic;
	static Role admin;
	static Group testGroup;
	static User noob;
	static User expert;
	static User overlord;
	static URL pfp;
	static Channel channel;
	static ArrayList<Message> msgLog;
	@BeforeAll
	static void setUp() throws Exception
	{
		testGroup = new Group (42,"testGroup");
		pfp = new URL("http://google.images");
		basic = new Role("basic",testGroup,false,false,false,false);
		admin = new Role("admin",testGroup,true,true,true,true);
		overlord = new User("OVLad", "owen", "pass123", 567, pfp, "bio", false);
		testGroup.registeredUsers.put(overlord, admin);
		noob = new User("n008", "noob", "123", 63, pfp, "I'm new here", false);
		expert = new User("Hexpert","Hector Spurt","asdfJkhlu124~",1337,pfp,"I run this place",false);
		testGroup.addNewUser(overlord,noob, basic);
		testGroup.addNewUser(overlord,expert, admin);
		channel = new Channel("testChannel", testGroup);
		msgLog = new ArrayList<Message>();
		channel.setMessageLog(msgLog);
	}

	@Test
	void testRole()
	{
		assertEquals("admin",admin.getRoleName());
		assertEquals(testGroup,admin.getGroup());
		assertEquals(true,admin.getCanKick());
		assertEquals(true,admin.getCanLockChannel());
		assertEquals(true,admin.getCanAssignRole());
		assertEquals(true,admin.getCanCreateChannel());
	}

	@Test
	void testKickUser()
	{
		assertEquals(basic.kickUser(expert),"You do not have permission to kick users.");
		assertEquals(admin.kickUser(noob),"User n008 has been kicked.");
	}

//	@Test
//	void testLockChannel()
//	{
//		channel.setIsLocked(false);
//		assertEquals(false,channel.getIsLocked());
//		admin.lockChannel(channel.getChannelName(), expert.getUserID()); //TODO
//		ArrayList<Channel> channelList = admin.getGroup().getChannels(); // why is this empty
//		//channel.lockChannel(channel.getChannelName(),expert.getUserID());
//		assertEquals(true,channel.getIsLocked());
//		//test when no permission
//		channel.setIsLocked(false);
//		assertEquals(false,channel.getIsLocked());
//		admin.lockChannel(channel.getChannelName(), noob.getUserID()); //TODO
//		//channel.lockChannel(channel.getChannelName(),noob.getUserID());
//		assertEquals(true,channel.getIsLocked());
//	}

	@Test
	void testSendMessage()
	{
		Message m = new Message("hello", noob.getUserID());
		basic.sendMessage(m, channel);
		assertEquals("hello",channel.messageLog.get(0).getText());
	}

	@Test
	void testLeaveGroup()
	{
		//leaving is allowed for any user of any permission, but if a user calls it for themselves
		assertEquals(basic.leaveGroup(noob,basic.getGroup().getGroupID()),"n008 left the group.");
		assertEquals(admin.leaveGroup(expert,admin.getGroup().getGroupID()),"Hexpert left the group.");
	}

	@Test
	void testAssignRole()
	{
		User bill = new User("bigbill", "william", "741aaa", 70, pfp, "I am Bill. Hear me roar.", false);
		testGroup.addNewUser(overlord,bill, basic);
		assertEquals("basic",testGroup.getRegisteredUsers().get(bill).getRoleName());
		//overlord assigns bill from basic to admin
		testGroup.registeredUsers.get(overlord).assignRole(bill, admin);
		String billRoleName = testGroup.getRegisteredUsers().get(bill).getRoleName(); 
		assertEquals("admin",billRoleName);
		//test when no permission to assign role
		User joe = new User("namoth12","joe","qwertyuiop",1217,pfp,"Everybody knows joe's mother; Joe Mama.",false);
		testGroup.addNewUser(overlord, joe, basic);
		//user with basic role attempts to assign
		assertEquals("Role assignment failed. basic does not have permission to assign roles.",testGroup.registeredUsers.get(joe).assignRole(bill, basic));
		//role assignment should fail and bill should have the role he had before
		assertEquals("admin",testGroup.getRegisteredUsers().get(bill).getRoleName());
	}
	@Test
	void testCreateChannel()
	{
		int originalSize = admin.getGroup().getChannels().size();
		admin.createChannel("testChannelBestChannel");
		assertEquals(originalSize + 1,admin.getGroup().getChannels().size());
		assertEquals("testChannelBestChannel",admin.getGroup().getChannels().get(0).getChannelName());
	}

}
