package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import mainapplication.ViewTransitionalModelInterface;

public class GiveRoleViewController {
	
	ViewTransitionalModelInterface model;

    @FXML
    private Button backButton;

    @FXML
    private Button submitButton;

    @FXML
    void onClickBackButton(ActionEvent event) 
    {
    	//this.model.showGroupView(int groupID); //TODO
    }

    @FXML
    void onClickSubmitButton(ActionEvent event) 
    {
    	//this.model.showGroupView(int groupID); //TODO
    }
    
    public void setModel(ViewTransitionalModelInterface model)
    {
    	this.model = model;
    }

}