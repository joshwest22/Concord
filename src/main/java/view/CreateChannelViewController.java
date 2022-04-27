package view;

import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import mainapplication.ViewTransitionalModel;
import mainapplication.ViewTransitionalModelInterface;

public class CreateChannelViewController {
	ViewTransitionalModelInterface model;
	//channel needs to know its group
	int myGroupID = model.getClientModel().getCurrentSelectedGroupID();
    @FXML
    private Button createChannelButton;

    @FXML
    private RadioButton createChannelNo;

    @FXML
    private TextField createChannelText;

    @FXML
    private RadioButton createChannelYes;

    @FXML
    void onClickedCreateChannel(ActionEvent event) 
    {
    	try
		{
    		String channelName = createChannelText.getText();
			model.getClientModel().createChannel(channelName, model.getClientModel().getAssociatedUser().getUserID(), myGroupID);
			if (createChannelYes.isSelected())
			{
				model.getClientModel().getServerContact().getGroup(myGroupID).getChannelByName(channelName).setIsLocked(true);
			}
			else if (createChannelNo.isSelected())
			{
				model.getClientModel().getServerContact().getGroup(myGroupID).getChannelByName(channelName).setIsLocked(false);
			}
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.model.showGroupView(myGroupID); //TODO //Does this actually work?
    }

	public void setModel(ViewTransitionalModel model)
	{
		this.model = model;
		
	}

}

