package concord;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ReactionMessage extends Message implements Reactable
{
	Message message;
	String emojiCode;
	ArrayList<String> emojiList;
	URL customImg;
	ArrayList<URL> customImgsList;
	
	public ReactionMessage(String emojiCode, Integer sentByUserID, Message message)
	{
		super();
		this.message = message;
		this.emojiCode = emojiCode;
		this.emojiList = new ArrayList<String>(Arrays.asList("emoji1","emoji2","emoji3"));
		this.customImg = this.getClass().getResource("concordLogo.png");
		this.customImgsList = new ArrayList<URL> (Arrays.asList(this.customImg));
	}
	
	//alternate customImg constructor
	public ReactionMessage(URL customImg, Integer sentByUserID, Message message)
	{
		super();
		this.message = message;
		this.emojiCode = "smile_emoji";
		this.emojiList = new ArrayList<String>(Arrays.asList("emoji1","emoji2","emoji3"));
		this.customImg = this.getClass().getResource("concordLogo.png");
		this.customImgsList = new ArrayList<URL> (Arrays.asList(this.customImg));
	}
	
	//java beans constructor
	public ReactionMessage()
	{
		super();
		this.message = new Message();
		this.emojiCode = new String();
		this.emojiList = new ArrayList<String>(Arrays.asList("emoji1","emoji2","emoji3"));
		this.customImg = this.getClass().getResource("concordLogo.png");
		this.customImgsList = new ArrayList<URL> (Arrays.asList(this.customImg));
	}

	public Message getMessage()
	{
		return message;
	}

	public void setMessage(Message message)
	{
		this.message = message;
	}

	public String getEmojiCode()
	{
		return emojiCode;
	}

	public void setEmojiCode(String emojiCode)
	{
		this.emojiCode = emojiCode;
	}

	public Integer getSentByUserID()
	{
		return sentByUserID;
	}

	public void setSentByUserID(Integer sentByUserID)
	{
		this.sentByUserID = sentByUserID;
	}

	public ArrayList<String> getEmojiList()
	{
		return emojiList;
	}

	public void setEmojiList(ArrayList<String> emojiList)
	{
		this.emojiList = emojiList;
	}

	public URL getCustomImg()
	{
		return customImg;
	}

	public void setCustomImg(URL customImg)
	{
		this.customImg = customImg;
	}

	public ArrayList<URL> getCustomImgsList()
	{
		return customImgsList;
	}

	public void setCustomImgsList(ArrayList<URL> customImgsList)
	{
		this.customImgsList = customImgsList;
	}

	@Override
	public void addReaction(String emojiReaction)
	{
		this.setEmojiCode(emojiReaction);
		System.out.println("User ID: "+this.getSentBy()+" reacted to "+this.getMessage().getText()+" with "+this.getEmojiCode());
		//this has to either convert existing message into reactionMessage and then set emojiCode or customImg
		//OR just set the emojiCode and customImg properties if msg is already a reactionMessage
		
	}
	
	@Override
	public void addReaction(URL customImgURL)
	{
		this.setCustomImg(customImgURL);
		System.out.println("User ID: "+this.getSentBy()+" reacted to "+this.getMessage().getText()+" with "+this.getCustomImg());

	}

	@Override
	public void removeReaction()
	{
		this.setEmojiCode("N/A");
		URL imgurl;
		try
		{
			imgurl = new URL("https://N/A.com");
			this.setCustomImg(imgurl);
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("User ID: "+this.getSentBy()+" removed their reaction to "+this.getMessage().getText());
		
	}

	@Override
	public String displayMessage()
	{
		return "UserID "+this.getMessage().getSentBy()+": "+this.getMessage().getText()+" Reaction: "+this.getEmojiCode();
	}


}
