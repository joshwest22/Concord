package view;

import concord.Group;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
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
    
    @FXML
    void groupViewClicked(MouseEvent event) 
    {
    	Group group = groupListView.getSelectionModel().getSelectedItem();
    	System.out.println(group+ " was clicked");
    }
    
    public void setModel(ViewTransitionalModelInterface model)
    {
    	this.model = model;
    	groupListView.setItems(model.getClientModel().getGroupList());
    }

}