package concord;

public interface Reactable
{
	public void addReaction(ReactionMessage reactionMessage);
	public void removeReaction(ReactionMessage reactionMessage);
	public void retrieveClient(Client client);
}
