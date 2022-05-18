package concordTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import concord.Message;

class MessageTest
{		
	static Message m;
	static Integer userID;
	@BeforeAll
	static void setUp() throws Exception
	{
		String str="2022-09-01 09:01:15";  
        Timestamp ts= Timestamp.valueOf(str);
        userID = 42;
		
		m = new Message("HelloWorld",ts,false,userID,null);
	}

	@Test
	void testMessage()
	{
		assertEquals("HelloWorld",m.getText());
		assertEquals(Timestamp.valueOf("2022-09-01 09:01:15"),m.getTimestamp());
		assertEquals(false,m.getIsPinned());
		assertEquals(userID,m.getSentBy());
		assertEquals(null,m.getInReplyTo());
	}
	
	@Test
	void testToString()
	{
		assertEquals("UserID 42: HelloWorld",m.toString());
	}
}
