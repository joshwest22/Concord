package concord;

import java.net.URL;

public interface Reactable
{
	public void addReaction(String emojiReaction);
	public void addReaction(URL customImg);
	public void removeReaction();
	//public void retrieveClient(Client client);
}
