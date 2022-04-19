package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import mainapplication.ViewTransitionalModelInterface;

public class PinnedMessagesViewController {
	
	ViewTransitionalModelInterface model;

    @FXML
    private Button backButton;

    @FXML
    void onClickBackButton(ActionEvent event) 
    {
    	this.model.showGroupView();
    }
    
    public void setModel(ViewTransitionalModelInterface model)
    {
    	this.model = model;
    }

}