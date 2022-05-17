package concordTest;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import concord.Message;
import concord.ReactionMessage;

class ReactionMessageTest
{
	static ReactionMessage rm;
	@BeforeAll
	static void setUpBeforeClass() throws Exception
	{
		Message m = new Message("SoloSprint",23);
		rm = new ReactionMessage("😎",23,m);
		rm.setMessage(m);
		rm.setEmojiCode("😎");
	}

	@Test
	void testDisplayMessage()
	{
		assertEquals("UserID 23: SoloSprint Reaction: 😎",rm.displayMessage());
	}

	@Test
	void testGetMessage()
	{
		assertEquals("SoloSprint",rm.getMessage().getText());
	}

	@Test
	void testSetMessage()
	{
		assertEquals("SoloSprint",rm.getMessage().getText());
		assertEquals(23,rm.getMessage().getSentBy());
		Message originalM = rm.getMessage();
		Message m = new Message("newMessage",46);
		rm.setMessage(m);
		assertEquals("newMessage",rm.getMessage().getText());
		assertEquals(46,rm.getMessage().getSentBy());
		m = originalM;
		rm.setMessage(m);
		assertEquals("SoloSprint",rm.getMessage().getText());
		assertEquals(23,rm.getMessage().getSentBy());
	}

	@Test
	void testGetEmojiCode()
	{
		assertEquals("😎",rm.getEmojiCode());
	}

	@Test
	void testSetEmojiCode()
	{
		assertEquals("😎",rm.getEmojiCode());
		rm.setEmojiCode("😀");
		assertEquals("😀",rm.getEmojiCode());
		rm.setEmojiCode("😎");
	}

//	@Test
//	void testGetSentByUserID()
//	{
//		assertEquals(23,rm.getSentByUserID());
//	}
//
//	@Test
//	void testSetSentByUserID()
//	{
//		assertEquals(23,rm.getSentByUserID());
//		rm.setSentByUserID(42);
//		assertEquals(42,rm.getSentByUserID());
//		rm.setSentByUserID(23);
//	}
//
//	@Test
//	void testGetEmojiList()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetEmojiList()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetCustomImg()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetCustomImg()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetCustomImgsList()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetCustomImgsList()
//	{
//		fail("Not yet implemented");
//	}

	@Test
	void testAddReaction()
	{
		rm.setEmojiCode("😎");
		assertEquals("😎", rm.getEmojiCode());
		rm.addReaction("😐");
		assertEquals("😐", rm.getEmojiCode());
		URL url;
		try
		{
			url = new URL("https://na.com");
			rm.addReaction(url);
			assertEquals(url,rm.getCustomImg());
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testRemoveReaction()
	{
		assertEquals("😎", rm.getEmojiCode());
		rm.removeReaction();
		assertEquals("N/A", rm.getEmojiCode());
		URL url;
		try
		{
			url = new URL("https://images.com");
			rm.addReaction(url);
			assertEquals(url, rm.getCustomImg());
			rm.removeReaction();
			assertEquals("https://N/A.com",rm.getCustomImg().toString());
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rm.addReaction("😎");
		
	}

}
