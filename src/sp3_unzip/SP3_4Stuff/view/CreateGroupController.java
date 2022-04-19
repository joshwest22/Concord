package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import mainapplication.ViewTransitionalModelInterface;

public class CreateGroupController {
	
	ViewTransitionalModelInterface model;

    @FXML
    private Button backButton;

    @FXML
    private Button enterButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField serverNameTextField;

    @FXML
    void onClickBackButton(ActionEvent event) 
    {
    	this.model.showMainView();
    }

    @FXML
    void onClickEnterButton(ActionEvent event) 
    {
    	//Logic for group making
    	this.model.showMainView();
    }

    @FXML
    void onClickExitButton(ActionEvent event) 
    {
    	this.model.showMainView();
    }
    
    public void setModel(ViewTransitionalModelInterface model)
    {
    	this.model = model;
    }

}