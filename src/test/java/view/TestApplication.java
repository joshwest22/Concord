package view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	Client myClient;
	ViewTransitionalModel vm;
	@Start
	private void start(Stage stage) throws RemoteException
	{
		myClient = new Client("TestUser","TestPass");
		//myClient.createGroup(12, "district12");
		//myClient.addGroupID(12); // might not need this since addGroupID was integrated into createGroup
		//Group testGroup = myClient.getGroup(12); 
		//hardcode channel
		//testGroup.createChannel("testChannel", testGroup);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Client.class.getResource("../view/LoginView.fxml"));
		try
		{
			BorderPane view;
			view = loader.load();
			LoginController controller = loader.getController();
			vm =new ViewTransitionalModel(view,myClient,stage); 
			controller.setModel(vm);
			myClient.login("TestUser", "TestPass");
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
		try
		{
			Thread.sleep(1500);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		try
		{
			Thread.sleep(1500);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		assertNumGroups(0,robot); //this will be 0 when groupPlaceholder removed
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
	
	@SuppressWarnings("unchecked")
	public void assertNumGroups(int number, FxRobot robot)
	{
		Assertions.assertThat(robot.lookup("#groupListView").queryAs(ListView.class)).hasExactlyNumItems(number);
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
		//create group
		robot.clickOn("#createGroupButton"); 
		robot.clickOn("#serverNameTextField");
		robot.write("myFirstGroup");
		robot.clickOn("#serverIDTextField");
		robot.write("12");
		robot.clickOn("#enterButton");
		try
		{
			assertTrue(myClient.getGroup(12)!= null);
		} catch (RemoteException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		assertEquals(1,myClient.getGroupList().size());
		assertNumGroups(1,robot);
		
		robot.clickOn("#createGroupButton"); 
		robot.clickOn("#serverNameTextField");
		robot.write("mySecondGroup");
		robot.clickOn("#serverIDTextField");
		robot.write("18");
		robot.clickOn("#enterButton");
		try
		{
			Thread.sleep(1500);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		robot.clickOn("#myProfileButton");
//		try
//		{
//			Thread.sleep(1000);
//		} catch (InterruptedException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		robot.clickOn("#viewProfileCloseButton");
		robot.clickOn("#viewInvitesButton");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		robot.clickOn("#backButton");
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//click on group; may need to change based on listview instead of button
		robot.clickOn("myFirstGroup"); //directly look for button by text
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
		//check that the message text is the same
		assertEquals("testMessage",messageList.getItems().get(0).getText());
		//pin the message
		//clickOn message and then click pinned icon OR go to pinned view and pick a message
		//open pinned view
		testPinned(robot);
		//click on locked channel
		robot.clickOn("lockedChannel");
		//send message in channel
		robot.clickOn("#sendMessageBoxTextField");
		robot.write("testMessage");
		robot.clickOn("#sendButton");
		Assertions.assertThat(channelList).hasExactlyNumItems(1);
		Assertions.assertThat(messageList).hasExactlyNumItems(1);
		//add reaction to message
		
		//remove reaction to message
		
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
		testCreateAccount(robot);
		testLogin(robot);
		testGroup(robot);
	}

	
	
}
