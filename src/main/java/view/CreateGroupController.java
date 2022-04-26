package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import mainapplication.ViewTransitionalModelInterface;
import concord.Server;

public class CreateGroupController {
	
	ViewTransitionalModelInterface model;
	Server server;

    @FXML
    private Button backButton;

    @FXML
    private Button enterButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField serverNameTextField;
    
    @FXML
    private TextField serverIDTextField;

    @FXML
    void onClickBackButton(ActionEvent event) 
    {
    	this.model.showMainView();
    }

    @FXML
    void onClickEnterButton(ActionEvent event) 
    {
    	//Logic for group making
    	//this logic is bad; create group in existing server
    	server.createGroup(Integer.valueOf(serverIDTextField.getText()), serverNameTextField.getText());
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