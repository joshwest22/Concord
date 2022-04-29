package view;

import concord.Channel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import mainapplication.ViewTransitionalModelInterface;

public class GroupViewController {
	
	ViewTransitionalModelInterface model;

	@FXML
    private Button backButton;

    @FXML
    private TextField channelNameTextField;

    @FXML
    private ListView<Channel> channelsListView;

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
    	model.showMainView();
    }

    @FXML
    void onClickChannelListView(MouseEvent event) 
    {
    	
    }

	@FXML
    void onClickGiveRoleButton(ActionEvent event) 
    {
    	model.showGiveRoleView();
    }

    @FXML
    void onClickInvite(ActionEvent event) 
    {
    	model.showInvitations();
    }

    @FXML
    void onClickMessageListView(MouseEvent event) 
    {
    	
    }

    @FXML
    void onClickPinnedMessageButton(ActionEvent event) 
    {
    	model.showPinnedMessagesView();
    }

    @FXML
    void onClickSendButton(ActionEvent event) 
    {
    	model.getClientModel().sendMessage(sendMessageBoxTextField.getText());
    }

    @FXML
    void onClickUsersListView(MouseEvent event) 
    {
    	
    }

    @FXML
    void onClickedCreateChannel(ActionEvent event) 
    {
    	model.showCreateChannelView();
    }

    
    public void setModel(ViewTransitionalModelInterface model)
    {
    	this.model = model;
    	channelsListView.getSelectionModel().selectedItemProperty().addListener(e->{onSelectedChannel();}); //grocView is the actual list view
    	usersListView.getSelectionModel().selectedItemProperty().addListener(e->{onSelectedUser();});
    }

	private void onSelectedUser()
	{
		System.out.println("user selected");
		
	}

	private void onSelectedChannel()
	{
		System.out.println("channel selected");
		
	}

}