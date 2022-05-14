package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import mainapplication.ViewTransitionalModelInterface;

import java.rmi.RemoteException;

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
    	try
		{
			this.model.getClientModel().createGroup(Integer.valueOf(serverIDTextField.getText()), serverNameTextField.getText());
		} catch (NumberFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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