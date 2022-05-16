package mainapplication;

import java.io.IOException;

import concord.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.CreateAccountController;
import view.CreateChannelViewController;
import view.CreateGroupController;
import view.EditProfileViewController;
import view.GiveRoleViewController;
import view.GroupViewController;
import view.InvitationsViewController;
import view.LoginController;
import view.MainPageController;
import view.PinnedMessagesViewController;
import view.ProfilePreviewController;

public class ViewTransitionalModel implements ViewTransitionalModelInterface
{
	BorderPane mainview;
	Client client;
	Integer targetedGroupID;
	private Stage stage;
	//Add other targeted properties for message etc
	 
	
	public ViewTransitionalModel(BorderPane view, Client newClient, Stage stage)
	{
		this.mainview = view;
		this.client = newClient;
		this.stage = stage;
	}
	
	@Override
	public void showMainView()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewTransitionalModel.class.getResource("../view/MainPageView.fxml"));
		Pane view;
		try
		{
			view = loader.load();
			setSize(1000,600);
			mainview.setCenter(view);
			MainPageController controller = loader.getController();
			controller.setModel(this);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	

	@Override
	public void showCreateAccountView()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ViewTransitionalModel.class.getResource("../view/CreateAccountView.fxml"));
		Pane view;
		try
		{
			view = loader.load();
			setSize(500,300);
			mainview.setCenter(view);
			CreateAccountController controller = loader.getController();
			controller.setModel(this);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		
		@Override
		public void showEditProfileView()
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ViewTransitionalModel.class.getResource("../view/EditProfileView.fxml"));
			Pane view;
			try
			{
				view = loader.load();
				setSize(800,500);
				mainview.setCenter(view);
				EditProfileViewController controller = loader.getController();
				controller.setModel(this);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		
	}
}
		
		@Override
		public void showCreateGroupView()
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ViewTransitionalModel.class.getResource("../view/CreateGroupView2.fxml"));
			Pane view;
			try
			{
				view = loader.load();
				setSize(600,300);
				mainview.setCenter(view);
				//CreateGroupController controller = loader.getController();
				CreateGroupController controller = loader.getController();
				controller.setModel(this);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		
			}
		}
		
		@Override
		public void showGroupView(int groupID)
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ViewTransitionalModel.class.getResource("../view/GroupView.fxml"));
			Pane view;
			try
			{
				view = loader.load();
				mainview.setCenter(view);
				//CreateGroupController controller = loader.getController();
				GroupViewController controller = loader.getController();
				controller.setModel(this);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		
			
		}
	}
		
		@Override
		public void showInvitations()
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ViewTransitionalModel.class.getResource("../view/InvitationsView.fxml"));
			Pane view;
			try
			{
				view = loader.load();
				setSize(500,300);
				mainview.setCenter(view);
				//CreateGroupController controller = loader.getController();
				InvitationsViewController controller = loader.getController();
				controller.setModel(this);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		
			
		}
		}

		@Override
		public void showCreateChannelView()
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ViewTransitionalModel.class.getResource("../view/CreateChannelView.fxml"));
			Pane view;
			try
			{
				view = loader.load();
				mainview.setCenter(view);
				CreateChannelViewController controller = loader.getController();
				controller.setModel(this);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		}

		@Override
		public void showProfilePreviewView()
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ViewTransitionalModel.class.getResource("../view/ProfilePreviewView.fxml"));
			Pane view;
			try
			{
				view = loader.load();
				mainview.setCenter(view);
				ProfilePreviewController controller = loader.getController();
				controller.setModel(this);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
			
		}
		
		@Override
		public void showPinnedMessagesView()
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ViewTransitionalModel.class.getResource("../view/PinnedMessagesView.fxml"));
			Pane view;
			try
			{
				view = loader.load();
				setSize(500,300);
				mainview.setCenter(view);
				PinnedMessagesViewController controller = loader.getController();
				controller.setModel(this);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
		
		}
		}
		
		@Override
		public void showGiveRoleView()
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ViewTransitionalModel.class.getResource("../view/GiveRoleView2.fxml"));
			Pane view;
			try
			{
				view = loader.load();
				setSize(800,500);
				mainview.setCenter(view);
				GiveRoleViewController controller = loader.getController();
				controller.setModel(this);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
		
		}
		}
		
		@Override
		public void showLoginView()
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ViewTransitionalModel.class.getResource("../view/LoginView.fxml"));
			Pane view;
			try
			{
				view = loader.load();
				mainview.setCenter(view);
				LoginController controller = loader.getController();
				controller.setModel(this);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
		
		}
		}
		
		public Client getClientModel()
		{
			return this.client;
		}
		
		private void setSize(int width, int height)
		{
			stage.setWidth(width);
			stage.setHeight(height);
		}
}
