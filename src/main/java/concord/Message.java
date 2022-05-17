package concord;

import java.sql.Timestamp;
import java.time.Instant;

public class Message
{
	String text;
	Timestamp timestamp;
	Boolean isPinned = false;
	Integer sentByUserID;
	Message inReplyTo;
	
	public Message(String text, Timestamp timestamp, Boolean isPinned, Integer sentBy, Message inReplyTo)
	{
		this.text = text;
		this.timestamp = timestamp;
		this.isPinned = isPinned;
		this.sentByUserID = sentBy;
		this.inReplyTo = inReplyTo;
	}
	//alternate constructor
	public Message(String msg, Integer sentByUserID)
	{
		this.text = msg;
		this.timestamp = Timestamp.from(Instant.now());
		this.sentByUserID = sentByUserID;
		this.isPinned = false;
		this.inReplyTo = null;
	}
	
	public Message()
	{
		this("msg",-1);
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public Timestamp getTimestamp()
	{
		return timestamp;
	}
	
	public void setTimestamp(Timestamp timestamp)
	{
		this.timestamp = timestamp;
	}
	
	public Boolean getIsPinned()
	{
		return isPinned;
	}

	public void setIsPinned(Boolean isPinned)
	{
		this.isPinned = isPinned;
	}

	public Integer getSentBy()
	{
		return sentByUserID;
	}

	public void setSentBy(Integer sentBY)
	{
		this.sentByUserID = sentBY;
	}

	public Message getInReplyTo()
	{
		return inReplyTo;
	}

	public void setInReplyTo(Message inReplyTo)
	{
		this.inReplyTo = inReplyTo;
	}
	
	@Override
	public String toString()
	{
		return "UserID "+this.getSentBy()+": "+this.getText();
		//return "UserID "+this.getSentBy()+": "+this.getText()+" @"+this.getTimestamp();
	}
	
	//reactions addition
	public String displayMessage()
	{
		return this.toString();
	}
}
