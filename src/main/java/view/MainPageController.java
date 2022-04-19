package view;

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
    	//Logic for figuring which group it actually is
    	this.model.showGroupView();
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
    	for(Integer i:this.model.getClientModel().getListOfGroupIDs())
    	{
    		GroupButton newButton = new GroupButton(i,"PlaceholderName");
    		//Button newButton = new Button();
    		newButton.setText("Group " + i);
    		newButton.setOnAction(e->this.onClickGroupButton(e));
    		groupButtonFlowPane.getChildren().add(newButton);
    	}
    }

}