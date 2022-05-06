package view;

import concord.Channel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import mainapplication.ViewTransitionalModelInterface;

public class ChannelButton extends Button
{
	
	private int myGroupID;
	private int channelIndex;
	private String channelName;
	public Channel channel;
	public ViewTransitionalModelInterface model;
	
	public ChannelButton(int myGroupID, int channelIndex, String channelName, Channel channel,
			ViewTransitionalModelInterface model)
	{
		super();
		this.myGroupID = myGroupID;
		this.channelIndex = channelIndex;
		this.channelName = channelName;
		this.channel = channel;
		this.setText(channelName);
		this.setOnAction(e->this.onButtonPressed(e));
		this.model = model;
	}
	public ChannelButton()
	{
		super();
		this.myGroupID = channel.getMyGroup().getGroupID();
		//channelIndex
		this.channelName = channel.getChannelName();
		//channel
		this.setText(channelName);
		this.setOnAction(e->this.onButtonPressed(e));
	}
	public int getMyGroupID()
	{
		return myGroupID;
	}
	public void setMyGroupID(int myGroupID)
	{
		this.myGroupID = myGroupID;
	}
	public int getChannelIndex()
	{
		return channelIndex;
	}
	public void setChannelIndex(int channelIndex)
	{
		this.channelIndex = channelIndex;
	}
	public String getChannelName()
	{
		return channelName;
	}
	public void setChannelName(String channelName)
	{
		this.channelName = channelName;
	}
	public void onButtonPressed(ActionEvent event)
	{
		//TODO
	}
	
	
}
