package view;

import concord.Group;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    private ListView<Group> groupListView;

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
    	groupListView.setItems(model.getClientModel().getGroupList());
    	
    	//this has to work for groups to show up!!//TODO
    	//concurrent modification error => use Platform.runLater
    	Platform.runLater(()->
    	{
//        	//was getAssociatedGroupIDs; switch to observable list and let it update dynamically
//    		model.getClientModel()
//    		.getGroupList()
//    		.addListener((ListChangeListener<Group>)e ->
//        	{
//        		//clear list
//    			model.getClientModel().getGroupList().clear();
//        		for(Group group:model.getClientModel()
//        				.getGroupList())
//        		{
//            		
//        			//check through every group and update any changes
//        			model.getClientModel().getGroupList().add(group);
//            	}	
//        	});
    	});
    	
    	//after login re-draw buttons with the contents of the for loop
    	if (model.getClientModel().getAssociatedUser().getOnlineStatus() == true)
    	{
//    		model.getClientModel()
//    		.getGroupList()
//    		.addListener((ListChangeListener<Group>)e ->
//        	{
//        		//clear list
//    			model.getClientModel().getGroupList().clear();
//    			
//        		for(Group group:model.getClientModel()
//        				.getGroupList())
//        		{
//        			//check through every group and update any changes
//        			model.getClientModel().getGroupList().add(group);
//            	}	
//        	}); //was getAssociatedGroupIDs; switch to observable list and let it update dynamically
    	}
    }

}