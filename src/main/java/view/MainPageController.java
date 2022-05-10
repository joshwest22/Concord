package view;

import concord.Group;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import mainapplication.ViewTransitionalModelInterface;

public class MainPageController {
	
	ViewTransitionalModelInterface model;

    @FXML
    private Button group1Button;
    
    @FXML
    private Button myProfileButton;
    
    @FXML
    private Button viewInvitesButton;
    
    @FXML
    private Button createGroupButton;
    
    @FXML
    private FlowPane groupButtonFlowPane;

    @FXML
    void onClickGroupButton(ActionEvent event) 
    {
    	//GroupButton now handles logic
    }
    
    @FXML
    void onClickViewInvites(ActionEvent event) 
    {
    	this.model.showInvitations();
    }
    
    @FXML
    void onClickMyProfile(ActionEvent event) 
    {
    	this.model.showEditProfileView();
    }
    
    @FXML
    void onClickCreateGroupButton(ActionEvent event) 
    {
    	this.model.showCreateGroupView();
    }
    
    public void setModel(ViewTransitionalModelInterface model)
    {
    	this.model = model;
    	this.model.getClientModel()
		.getGroupList()
		.addListener((ListChangeListener<Group>)e ->
    	{
    		for(Group group:this.model.getClientModel()
    				.getGroupList())
    		{
        		//clear list
    			this.model.getClientModel().getGroupList().clear();
    			//check through every group and update any changes
    			GroupButton newButton = new GroupButton(group.getGroupID(),"PlaceholderName", model);
        		groupButtonFlowPane.getChildren().add(newButton);
        	}	
    	}); //was getAssociatedGroupIDs; switch to observable list and let it update dynamically
    	
    	//after login re-draw buttons with the contents of the for loop
    }

}