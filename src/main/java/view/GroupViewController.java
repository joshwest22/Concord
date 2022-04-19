package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import mainapplication.ViewTransitionalModelInterface;

public class GroupViewController {
	
	ViewTransitionalModelInterface model;

	@FXML
    private Button backButton;

    @FXML
    private TextField channelNameTextField;

    @FXML
    private ListView<ChannelButton> channelsListView;

    @FXML
    private Button createChannelButton;

    @FXML
    private Button giveRoleButton;

    @FXML
    private Button pinnedMessageButton;

    @FXML
    private TextField searchUsersTextField;

    @FXML
    private Button sendButton;

    @FXML
    private TextField sendMessageBoxTextField;

    @FXML
    private ListView<UserButton> usersListView;


    @FXML
    void onClickBackButton(ActionEvent event) 
    {
    	this.model.showMainView();
    }
    
    @FXML
    void onClickedCreateChannel(ActionEvent event) 
    {
    	this.model.showCreateChannelView();
    }
    
    @FXML
    void onClickedSampleUser(ActionEvent event) 
    {
    	this.model.showProfilePreviewView();
    }
    
    @FXML
    void onClickPinnedMessageButton(ActionEvent event) 
    {
    	this.model.showPinnedMessagesView();
    }
    
    @FXML
    void onClickGiveRoleButton(ActionEvent event) 
    {
    	this.model.showGiveRoleView();
    }
    
   
    
    public void setModel(ViewTransitionalModelInterface model)
    {
    	this.model = model;
    }

}