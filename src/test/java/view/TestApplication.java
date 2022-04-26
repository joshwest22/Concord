package view;

import java.io.IOException;
import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import concord.Client;
//import concord.ClientInterface;
//import concord.ClientSubstitute;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
		myClient.addGroupID(12);
		myClient.addGroupID(5);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Client.class.getResource("../view/LoginView.fxml"));
		try
		{
			BorderPane view;
			view = loader.load();
			LoginController controller = loader.getController();
			ViewTransitionalModel vm =new ViewTransitionalModel(view,myClient); 
			controller.setModel(vm);
			Scene s = new Scene(view);
			stage.setScene(s);
			stage.show();
		} catch (IOException e)
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
		Assertions.assertThat(robot.lookup("#groupButtonFlowPane").queryAs(FlowPane.class)).hasExactlyNumChildren(3);
	}
	
	public void testGroup(FxRobot robot)
	{
		//TODO
		//click on group
		
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
		
		//send message in channel
		
		//pin the message
		
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
		testLogin(robot);
		testGroup(robot);
	}
	
}
