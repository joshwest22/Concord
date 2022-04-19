package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mainapplication.ViewTransitionalModelInterface;

public class LoginController {
	
	ViewTransitionalModelInterface model;
	//Don't need a reference to the view, as we've got its injected components here
	//We indirectly know about it
	//Although I don't know if we need to know the client, or can/should get at it through the ViewTransitionalModel
	public void setModel(ViewTransitionalModelInterface newModel)
	{
		this.model = newModel;
	}

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginPassword;

    @FXML
    private TextField loginUsername;

    @FXML
    private Button makeAccountButton;
    
    @FXML
    private Label loginMainLabel;
    
    @FXML
    void onLoginClicked(ActionEvent event) 
    {
    	//Check if account exists
    	String enteredName = loginUsername.getText();
    	String enteredPassword = loginPassword.getText();
    	if(this.model.getClientModel().login(enteredName, enteredPassword) == true)
    	{
    		loginMainLabel.setText("Logged in!");
    		this.model.showMainView();
    	}
    	else
    	{
    		loginMainLabel.setText("Error logging in");
    		loginUsername.clear();
    		loginPassword.clear();
    	}
    }

    @FXML
    void onMakeAccountClicked(ActionEvent event) 
    {
    	//model.showCreateAccountView();
    	this.model.showCreateAccountView();
    }
    

}