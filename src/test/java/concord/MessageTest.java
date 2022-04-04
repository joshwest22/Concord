package concord;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MessageTest
{		
	static Message m;
	static Integer user;
	@BeforeAll
	static void setUp() throws Exception
	{
		String str="2022-09-01 09:01:15";  
        Timestamp ts= Timestamp.valueOf(str);  
		
		m = new Message("HelloWorld",ts,false,user,null);
	}

	@Test
	void testMessage()
	{
		assertEquals(m.getText(),"HelloWorld");
		assertEquals(m.getTimestamp(),Timestamp.valueOf("2022-09-01 09:01:15"));
		assertEquals(m.getIsPinned(),false);
		assertEquals(m.getSentBy(),user);
		assertEquals(m.getInReplyTo(),null);
	}
}
