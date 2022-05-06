package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import mainapplication.ViewTransitionalModel;
import mainapplication.ViewTransitionalModelInterface;

public class ProfilePreviewController {
	ViewTransitionalModelInterface model;

    @FXML
    private Label viewProfileBio;

    @FXML
    private Button viewProfileCloseButton;

    @FXML
    private ImageView viewProfileImage;

    @FXML
    private Label viewProfileOnline;

    @FXML
    private Label viewProfileRealName;

    @FXML
    private Label viewProfileUserID;

    @FXML
    private Label viewProfileUsername;

    @FXML
    void onClickedCloseButton(ActionEvent event) 
    {
    	this.model.showGroupView(model.getClientModel().getCurrentSelectedGroupID()); 
    }

	public void setModel(ViewTransitionalModel model)
	{
		this.model = model;
		
	}

}
