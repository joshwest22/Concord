package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import mainapplication.ViewTransitionalModelInterface;

public class GroupButton extends Button
{
	private int associatedGroupID; 
	private String associatedGroupName;
	ViewTransitionalModelInterface model;
	
	public GroupButton(int associatedGroupID, String associatedGroupName, ViewTransitionalModelInterface model)
	{
		super();
		this.setAssociatedGroupID(associatedGroupID);
		this.setAssociatedGroupName(associatedGroupName);
		this.setText("Group " + this.getAssociatedGroupID());
		this.setOnAction(e->this.onButtonPressed(e));
		this.model = model;
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
	
	public void onButtonPressed(ActionEvent event)
	{
		this.model.getClientModel().setCurrentSelectedGroupID(associatedGroupID);
		this.model.showGroupView(this.getAssociatedGroupID());
	}
}
