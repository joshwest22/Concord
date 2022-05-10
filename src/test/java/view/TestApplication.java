package view;

import java.io.IOException;
import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import concord.Channel;
import concord.Client;
import concord.Group;
import concord.Message;
import javafx.collections.ObservableList;
//import concord.ClientInterface;
//import concord.ClientSubstitute;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import mainapplication.ViewTransitionalModel;


@ExtendWith(ApplicationExtension.class)
public class TestApplication
{
	@Start
	private void start(Stage stage) throws RemoteException
	{
		Client myClient = new Client("TestUser","TestPass");
		myClient.createGroup(12, "district12");
		myClient.addGroupID(12); // might not need this since addGroupID was integrated into createGroup
		Group testGroup = myClient.getGroup(12); 
		testGroup.createChannel("testChannel", testGroup);
		myClient.addGroupID(5);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Client.class.getResource("../view/LoginView.fxml"));
		try
		{
			BorderPane view;
			view = loader.load();
			LoginController controller = loader.getController();
			ViewTransitionalModel vm =new ViewTransitionalModel(view,myClient,stage); 
			controller.setModel(vm);
			myClient.login("TestUser", "TestPass");
			vm.showGroupView(testGroup.getGroupID());
			Scene s = new Scene(view);
			stage.setScene(s);
			stage.show();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void testCreateAccount(FxRobot robot)
	{
		//threat path
		robot.clickOn("#makeAccountButton");
		robot.clickOn("#createAccountUsername");
		robot.write("Username");
		robot.clickOn("#realNameTextField");
		robot.write("User Name");
		//robot.clickOn("#createAccountPassword");
		//robot.write("Password");
		robot.clickOn("#createAccountButton");
		Assertions.assertThat(robot.lookup("#createAccountLabel").queryAs(Label.class)).hasText("One or more field empty, try again");	
		robot.clickOn("#backButton");

		//happy path
		robot.clickOn("#makeAccountButton");
		robot.clickOn("#createAccountUsername");
		robot.write("Username");
		robot.clickOn("#realNameTextField");
		robot.write("User Name");
		robot.clickOn("#createAccountPassword");
		robot.write("Password");
		robot.clickOn("#createAccountButton");
		//The assert below works and was used to fix bugs, but causes the next test to crash if enabled as page switches too quickly for text to be read
		//Assertions.assertThat(robot.lookup("#createAccountLabel").queryAs(Label.class)).hasText("Welcome Username!");		
	}
	
	public void testLogin(FxRobot robot)
	{
		robot.clickOn("#loginUsername");
		robot.write("wrongName");
		robot.clickOn("#loginPassword");
		robot.write("wrongPass");
		robot.clickOn("#loginButton");
		Assertions.assertThat(robot.lookup("#loginMainLabel").queryAs(Label.class)).hasText("Error logging in");
		robot.clickOn("#loginUsername");
		robot.write("TestUser");
		robot.clickOn("#loginPassword");
		robot.write("TestPass");
		robot.clickOn("#loginButton");
		//Assertions.assertThat(robot.lookup("#loginMainLabel").queryAs(Label.class)).hasText("Logged in!");
		Assertions.assertThat(robot.lookup("#groupButtonFlowPane").queryAs(FlowPane.class)).hasExactlyNumChildren(3);
	}
	
	@SuppressWarnings("unchecked")
	 ListView<Channel> getChannelFromList(FxRobot robot)

	 {
	   return (ListView<Channel>) robot.lookup("#channelsListView")
	       .queryAll().iterator().next();
	 }
	
	@SuppressWarnings("unchecked")
	 ListView<Message> getMessageFromList(FxRobot robot)

	 {
	   return (ListView<Message>) robot.lookup("#messageListView")
	       .queryAll().iterator().next();
	 }
	
	public void testGroup(FxRobot robot)
	{
		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//click on group
		robot.clickOn("Group 12"); //directly look for button text
		//click on existing channel
		//robot.clickOn("testChannel");
		
		//create unlocked channel
		robot.clickOn("#createChannelButton");
		robot.clickOn("#createChannelText");
		robot.write("testChannel");
		robot.clickOn("#createChannelNo");
		robot.clickOn("#createChannelSubmitButton");
		//create locked channel
		robot.clickOn("#createChannelButton");
		robot.clickOn("#createChannelText");
		robot.write("testLockedChannel");
		robot.clickOn("#createChannelYes");
		robot.clickOn("#createChannelSubmitButton");
		//click on unlocked channel
		robot.clickOn("testChannel");
		//sanity check that channelList is empty
		ListView<Channel> channelList = getChannelFromList(robot);
		Assertions.assertThat(channelList).isEmpty();
		//sanity check that messageList is empty
		ListView<Message> messageList = getMessageFromList(robot);
		Assertions.assertThat(messageList).isEmpty();
		//send message in channel
		robot.clickOn("#sendMessageBoxTextField");
		robot.write("testMessage");
		robot.clickOn("#sendButton");
		Assertions.assertThat(channelList).hasExactlyNumItems(1);
		Assertions.assertThat(messageList).hasExactlyNumItems(1);
		//pin the message
		//clickOn message and then click pinned icon OR go to pinned view and pick a message
		//open pinned view
		testPinned(robot);
		//click on locked channel
		
		//send message in channel
		
		//invite another user (that user needs to exist in server db)
		testInvitation(robot);
		//have second user click on locked channel and be denied access
		
		//test back button
		robot.clickOn("#backButton");
		//click on same group
		
		//click on search bar
		robot.clickOn("#searchUsersTextField");
		//write second user's username
		
		//verify that selected user is the correct user
		
	}
	
	public void testPinned(FxRobot robot)
	{
		//TODO
	}
	
	public void testInvitation(FxRobot robot)
	{
		//TODO
		//click on invite button
		
		//click on invite user?? TBD
		
		//either logout of this user and login to another 
		//OR create another robot that poses as the second client to verify and accept invitation
		
		//verify that there is more than one user in the group; click on new user and make sure they make the user that was invited
	}
	
	@Test
	public void testApp(FxRobot robot)
	{
		//comment/uncomment to add or remove section of testing
		//testCreateAccount(robot);
		//testLogin(robot);
		testGroup(robot);
	}

	
	
}
