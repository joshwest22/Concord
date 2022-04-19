package view;

import javafx.scene.control.Button;

public class GroupButton extends Button
{
	private int associatedGroupID; 
	private String associatedGroupName;
	
	public GroupButton(int associatedGroupID, String associatedGroupName)
	{
		super();
		this.associatedGroupID = associatedGroupID;
		this.associatedGroupName = associatedGroupName;
	}
}
