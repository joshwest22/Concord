package concordTest;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;

import concord.Channel;
import concord.Database;
import concord.Group;
import concord.Message;
import concord.ReactionMessage;
import concord.Role;
import concord.User;

import java.io.File;
import java.lang.Integer;
class DatabaseTest
{
	static Database db;
	static URL url;
	static User overlord;
	static User josh;
	static User satan;
	static Role basic;
	static Role admin;
	
	@BeforeAll
	static void setUp() throws Exception
	{
		db = new Database();
		url = null;
		try
		{
			url = new File("concordLogo.png").toURI().toURL(); //thanks to stackoverflow for file to URL
		} 
			catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		db.createUser("jdubble","josh","password",42,url,"I like butterflies",false);
		josh = db.getUser(42);
		db.createUser("OVLawd","owen","overwatch22",555,url,"As above...",false);
		overlord = db.getUser(555);
		db.createUser("d3vil","lucifer","hellonearth",666,url,"I hate butterflies",false);
		satan = db.getUser(666);		
	}

	@Test
	void testCreateUser() throws MalformedURLException
	{
		ArrayList<Integer> blockedList = new ArrayList<Integer>();
		josh.setBlockedUsers(blockedList);
		assertEquals("jdubble",josh.getUsername());
		assertEquals("josh",josh.getRealname());
		assertEquals("password",josh.getPassword());
		assertEquals(42,josh.getUserID());
		assertEquals(url,josh.getUserPic());
		assertEquals("I like butterflies",josh.getUserBio());
		assertEquals(false,josh.getOnlineStatus());
		assertEquals(blockedList,josh.getBlockedUserIDs());
	}

	@Test
	void testCreateGroup()
	{
		db.createGroup(5, "testCreateGroup");
		Group group5 = db.getGroups().get(5);
		assertEquals(group5.getGroupID(),5);
		assertEquals(group5.getGroupName(),"testCreateGroup");
	}

	@Test
	void testCreateChannel()
	{
		db.createGroup(47,"testCreateChannelGroup");
		Group chgroup = db.getGroups().get(47);
		chgroup.createChannel("testChannel",chgroup);
		Channel ch = chgroup.getChannels().get(0);
		assertEquals(ch.getChannelName(),"testChannel");
		//confirm that 2 channels can be created in one group
		chgroup.createChannel("testChannel1",chgroup);
		Channel chan = chgroup.getChannels().get(1);
		assertEquals(chan.getChannelName(),"testChannel1");
		//Testing the db createChannel method
		//This puts the first all powerful user into the group
		chgroup.getRegisteredUsers().put(overlord, chgroup.admin); //DESIGN FLAW: How do we know which user created the group; That user should be overlord
		db.createChannel("testChannel2", overlord.getUserID(), chgroup.getGroupID());
		Channel dbchan = chgroup.getChannels().get(2);
		assertEquals(dbchan.getChannelName(),"testChannel2");
		//threat testing
		chgroup.addNewUser(overlord, satan, chgroup.basic);
		int channelSizeBefore = chgroup.getChannels().size();
		db.createChannel("testChannel3", satan.getUserID(), chgroup.getGroupID());
		//channel above should not be created so length of channels in chgroup should not change
		assertEquals(channelSizeBefore,chgroup.getChannels().size());
		
		//test role can lock channel
		assertEquals(false,dbchan.getIsLocked());
		db.getRole(chgroup.getGroupID(), overlord.getUserID()).lockChannel(dbchan.getChannelName(), overlord.getUserID());
		assertEquals(true,dbchan.getIsLocked());
		//test unlock channel
		db.getRole(chgroup.getGroupID(), overlord.getUserID()).unlockChannel(dbchan.getChannelName(), overlord.getUserID());
		//threat path test for lock channel
		assertEquals(false,dbchan.getIsLocked());
		db.getRole(chgroup.getGroupID(), satan.getUserID()).lockChannel(dbchan.getChannelName(), overlord.getUserID());
		assertEquals(false,dbchan.getIsLocked());
		//test db lock channel (uses methods above, but necessary for server)
		db.lockChannel(chgroup.getGroupID(), overlord.getUserID(), dbchan.getChannelName());
		assertEquals(true,dbchan.getIsLocked());
		//test db unlock channel
		db.unlockChannel(chgroup.getGroupID(), overlord.getUserID(), dbchan.getChannelName());
		assertEquals(false,dbchan.getIsLocked());
	}

	@Test
	void testMessageReceived()
	{
		db.createGroup(49, "msgGroup");
		Group msgGroup = db.getGroups().get(49);
		msgGroup.getRegisteredUsers().put(overlord,msgGroup.admin);
		msgGroup.addNewUser(overlord, josh, msgGroup.admin);
		msgGroup.addNewUser(overlord,satan, msgGroup.admin);
		db.createChannel("5Chan", overlord.getUserID(), msgGroup.getGroupID());
		Channel msgChan = msgGroup.getChannels().get(0);
		//empty channel msglog
		assertEquals(0,msgChan.getMessageLog().size());
		db.messageReceived(msgChan.getChannelName(), "Hello World!", overlord.getUserID(), msgGroup.getGroupID());
		// channel msglog increased
		assertEquals(1,msgChan.getMessageLog().size());
		//msg text is the same
		assertEquals("Hello World!",msgChan.getMessageLog().get(0).getText());
	}

	@Test
	void testViewChannel()
	{
		//make new group
		db.createGroup(48, "testCreateChannelGroup1");
		Group chgroup1 = db.getGroups().get(48);
		//test getGroup
		assertEquals("testCreateChannelGroup1",db.getGroup(chgroup1.getGroupID()).getGroupName());
		chgroup1.getRegisteredUsers().put(overlord,chgroup1.admin);
		chgroup1.addNewUser(overlord, josh, chgroup1.admin);
		chgroup1.addNewUser(overlord,satan, chgroup1.admin);
		//test getUser
		assertEquals(josh.getUsername(),db.getUser(josh.getUserID()).getUsername());
		//test getRole
		String rolename = db.getRole(chgroup1.getGroupID(), josh.getUserID()).getRoleName();
		assertEquals(chgroup1.admin.getRoleName(),rolename);
		//have satan send msgs
		chgroup1.createChannel("channel1", chgroup1);
		Message m = new Message("Hell is freezing over.",666);
		chgroup1.admin.sendMessage(m,chgroup1.getChannels().get(0));
		//test messageReceived
		//db.messageReceived(rolename, rolename, null, null);
		//chgroup.channels.get(0).displayAllMessages(userID);
		//check length of msg log
		Channel channel1 = chgroup1.getChannels().get(0);
		int channelSize = channel1.getMessageLog().size();
		assertEquals(channelSize,1);
		
		//test reactions
		ReactionMessage r = new ReactionMessage();
		r.setText("reactionMessageText");
		r.setEmojiCode("😀");
		//r.setEmojiCode(EmojiParser.parseToUnicode(":grinning"));
		assertEquals("😀",EmojiParser.parseToUnicode(r.getEmojiCode()));
		//messing with emojis
		ArrayList<Emoji> allEmojis = (ArrayList<Emoji>) EmojiManager.getAll();
		Emoji emoji = EmojiManager.getForAlias("grinning");
		assertEquals("grinning",emoji.getAliases().get(0));
		assertEquals("😀",emoji.getUnicode());
		chgroup1.admin.sendMessage(r, channel1);
		assertEquals("reactionMessageText",channel1.getMessageLog().get(1).getText());
		//how to make messageLog contain reactionMessage?? Change messageLog to reactionMessage?
		//System.out.println("emoji"+EmojiParser.parseToAliases(channel1.getMessageLog().get(1).getEmojiCode()));
		//assertEquals(true,EmojiManager.isEmoji(channel1.getMessageLog().get(1).getEmojiCode()));
		//assertEquals("😀",EmojiManager.getByUnicode(channel1.getMessageLog().get(1).getEmojiCode()));
		//test that customImg can be set and sent correctly
		ReactionMessage custImg = new ReactionMessage();
		custImg.setText("customImage message");
		custImg.setCustomImg(url);
		chgroup1.admin.sendMessage(custImg, channel1);
		//assertEquals("concordLogo.png",channel1.getMessageLog().get(2).getCustomImg());
		//test that emojiList correct
		
		//test that customImgsList correct
		
		//block satan
		josh.blockUser(satan.getUserID());
		assertEquals(satan.getUserID(),josh.getBlockedUserIDs().get(0));
		//show length doesn't change
		Message secondMsg = new Message("Hell is really hot.",666);
		chgroup1.admin.sendMessage(secondMsg, channel1); //should not send bc blocked
		assertEquals(4,channel1.getMessageLog().size());
		assertEquals(josh.getBlockedUserIDs().get(0),satan.getUserID());
		//block a user using db method
		db.blockUser(overlord.getUserID(), satan.getUserID());
		assertEquals(satan.getUserID(),overlord.getBlockedUserIDs().get(0));
		//unblock a user using db method
		int blockedListSize = overlord.getBlockedUserIDs().size();
		db.unblockUser(overlord.getUserID(), satan.getUserID());
		assertEquals(blockedListSize - 1,overlord.getBlockedUserIDs().size());
		//view channel
		ArrayList<Message> msgs = db.viewChannel(chgroup1.getChannels().get(0).getChannelName(), satan.getUserID(), chgroup1.getGroupID());
		assertEquals(chgroup1.getChannels().get(0).getMessageLog().size(),msgs.size());
	}
	
	@Test
	void testListOfUsers() throws MalformedURLException
	{
		//check that existing number of users in db is same number in list of users
		assertEquals(db.getUsers().size(),db.getListOfUsers().size());
		//add a new user and check that the size of list of users is updated
		db.createUser("username", "realname", "password");
		assertEquals(db.getUsers().size(),db.getListOfUsers().size());
		//check that the value of the first user's username is correct in listOfUsers
		assertEquals("jdubble",db.getListOfUsers().get(0).getUsername());
	}
	
	@Test
	void testXMLStorage()
	{
		db.storeToDisk();
		Database diskDB = Database.loadFromDisk(); //XML doesn't match after listOfUsers added
		
		assertTrue(db.equals(diskDB));
	}

}
