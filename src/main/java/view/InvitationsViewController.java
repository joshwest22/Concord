package view;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import mainapplication.ViewTransitionalModelInterface;

public class InvitationsViewController {
	
	ViewTransitionalModelInterface model;

    @FXML
    private Button AcceptButton;

    @FXML
    private Button IgnoreButton;

    @FXML
    private TextArea InvitationTextArea;

    @FXML
    private Button backButton;

    @FXML
    void onAcceptClicked(ActionEvent event) {

    }

    @FXML
    void onClickBackButton(ActionEvent event) 
    {
    	this.model.showMainView();
    }

    @FXML
    void onIgnoreClicked(ActionEvent event) {

    }
    
    public void setModel(ViewTransitionalModelInterface model)
    {
    	this.model = model;
    }

}

