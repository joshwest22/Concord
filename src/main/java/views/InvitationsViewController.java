package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

ViewTransitionalModelInterface model;

public class InvitationsViewController {

    @FXML
    private Button AcceptButton;

    @FXML
    private Button IgnoreButton;

    @FXML
    private TextArea InvitationTextArea;

    @FXML
    void onAcceptClicked(ActionEvent event) 
    {
    	//take user to view of newly joined group
    	this.model.show(mainview); //change mainview to the view of desired group
    }

    @FXML
    void onIgnoreClicked(ActionEvent event) 
    {
    	//hide invitation
    }

}

