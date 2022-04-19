package view;

import javafx.scene.control.Button;

public class GroupButton extends Button
{
	private int associatedGroupID; 
	private String associatedGroupName;
	
	public GroupButton(int associatedGroupID, String associatedGroupName)
	{
		super();
		this.setAssociatedGroupID(associatedGroupID);
		this.setAssociatedGroupName(associatedGroupName);
	}

	public int getAssociatedGroupID()
	{
		return associatedGroupID;
	}

	public void setAssociatedGroupID(int associatedGroupID)
	{
		this.associatedGroupID = associatedGroupID;
	}

	public String getAssociatedGroupName()
	{
		return associatedGroupName;
	}

	public void setAssociatedGroupName(String associatedGroupName)
	{
		this.associatedGroupName = associatedGroupName;
	}
}
