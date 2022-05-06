package concord;

import java.util.ArrayList;
import java.util.Arrays;

public class Reaction implements Reactable
{
	String emojiCode;
	Integer sentByUserID;
	ArrayList<String> emojiList;
	
	public Reaction(String emojiCode, Integer sentByUserID)
	{
		this.emojiCode = emojiCode;
		this.sentByUserID = sentByUserID;
		this.emojiList = new ArrayList<String>(Arrays.asList("emoji1","emoji2","emoji3"));
	}
	
	public Reaction()
	{
		this.emojiCode = new String();
		this.sentByUserID = -1;
		this.emojiList = new ArrayList<String>(Arrays.asList("emoji1","emoji2","emoji3"));
	}

	@Override
	public void addReaction(Reaction reaction, Object o)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeReaction(Reaction reaction, Object o)
	{
		// TODO Auto-generated method stub
		
	}
}
