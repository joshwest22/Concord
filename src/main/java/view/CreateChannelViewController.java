package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import mainapplication.ViewTransitionalModel;
import mainapplication.ViewTransitionalModelInterface;

public class CreateChannelViewController {
	ViewTransitionalModelInterface model;

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
    	this.model.showGroupView();
    }

	public void setModel(ViewTransitionalModel model)
	{
		this.model = model;
		
	}

}

